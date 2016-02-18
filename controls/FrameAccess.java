package controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.media.Buffer;
import javax.media.Codec;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.PrefetchCompleteEvent;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.ResourceUnavailableEvent;
import javax.media.UnsupportedPlugInException;
import javax.media.control.TrackControl;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JFrame;
import javax.swing.JLabel;

import design.SubsequenceFrames;

public class FrameAccess extends JFrame implements ControllerListener {

	private static final long serialVersionUID = 1L;
	Processor p;
	Object waitSync = new Object();
	boolean stateTransitionOK = true;
	boolean flaf;
	String pLoc = null;
	String stopSize = "";
	String fileName;
	int imcnt = 0;
	int inputCnt = 0;
	boolean duflg = false;
	Comparitor comp = new Comparitor();
	Vector<String> inputmatch = new Vector<String>();
	Vector<String> dbmatch = new Vector<String>();
	JLabel label;
	int inVcnt = 0;
	int totC = 0;
	boolean cng;

	public boolean open(MediaLocator ml) {

		try {
			p = Manager.createProcessor(ml);
		} catch (Exception e) {
			System.err
					.println("Failed to create a processor from the given url: "
							+ e);
			return false;
		}

		p.addControllerListener(this);

		// Put the Processor into configured state.
		p.configure();
		if (!waitForState(p.Configured)) {
			System.err.println("Failed to configure the processor.");
			return false;
		}

		// So I can use it as a player.
		p.setContentDescriptor(null);

		// Obtain the track controls.
		TrackControl tc[] = p.getTrackControls();

		if (tc == null) {
			System.err
					.println("Failed to obtain track controls from the processor.");
			return false;
		}

		// Search for the track control for the video track.
		TrackControl videoTrack = null;

		for (int i = 0; i < tc.length; i++) {
			if (tc[i].getFormat() instanceof VideoFormat) {
				videoTrack = tc[i];
				break;
			}
		}

		if (videoTrack == null) {
			System.err
					.println("The input media does not contain a video track.");
			return false;
		}
		String videoFormat = videoTrack.getFormat().toString();
		System.err.println("Video format11111: " + videoFormat);
		Dimension videoSize = parseVideoSize(videoFormat);
		System.out.println("Video Size...." + videoSize);
		System.err.println("Video format: " + videoTrack.getFormat());

		try {
			Codec codec[] = { new PreAccessCodec(),
					new PostAccessCodec(videoSize) };
			videoTrack.setCodecChain(codec);
		} catch (UnsupportedPlugInException e) {
			System.err.println("The process does not support effects.");
		}

		p.prefetch();
		if (!waitForState(p.Prefetched)) {
			System.err.println("Failed to realize the processor.");
			return false;
		}

		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Component cc;

		Component vc;
		if ((vc = p.getVisualComponent()) != null) {
			// vc.setBounds(0,10,300,250);
			add("Center", vc);
		}

		if ((cc = p.getControlPanelComponent()) != null) {
			// cc.setBounds(0,260,300,20);
			add("South", cc);
		}
		label = new JLabel("Normal Frames");
		label.setForeground(Color.BLUE);
		// label.setBounds(0,0,150,30);
		add("South", label);

		// Start the processor.
		p.start();

		// setSize(300,285);
		setVisible(true);

		return true;
	}

	public Dimension parseVideoSize(String videoSize) {
		int x = 500, y = 600;
		StringTokenizer strtok = new StringTokenizer(videoSize, ", ");
		strtok.nextToken();
		String size = strtok.nextToken();

		StringTokenizer sizeStrtok = new StringTokenizer(size, "x");
		try {
			x = Integer.parseInt(sizeStrtok.nextToken());
			System.out.println("X SIZE...." + x);
			y = Integer.parseInt(sizeStrtok.nextToken());
			System.out.println("Y SIZE...." + y);
		} catch (NumberFormatException e) {
			System.out
					.println("unable to find video size, assuming default of 300x200");
		}
		System.out.println("Image width  = " + String.valueOf(x)
				+ "\nImage height = " + String.valueOf(y));
		return new Dimension(x, y);
	}

	public void addNotify() {
		super.addNotify();
		pack();
	}

	/**
	 * Block until the processor has transitioned to the given state. Return
	 * false if the transition failed.
	 */
	boolean waitForState(int state) {
		synchronized (waitSync) {
			try {
				while (p.getState() != state && stateTransitionOK)
					waitSync.wait();
			} catch (Exception e) {
			}
		}
		return stateTransitionOK;
	}

	/**
	 * Controller Listener.
	 */
	public void controllerUpdate(ControllerEvent evt) {

		if (evt instanceof ConfigureCompleteEvent
				|| evt instanceof RealizeCompleteEvent
				|| evt instanceof PrefetchCompleteEvent) {
			synchronized (waitSync) {
				stateTransitionOK = true;
				waitSync.notifyAll();
			}
		} else if (evt instanceof ResourceUnavailableEvent) {
			synchronized (waitSync) {
				stateTransitionOK = false;
				waitSync.notifyAll();
			}
		} else if (evt instanceof EndOfMediaEvent) {
			p.close();
			System.out.println("End of Media Event");
			if (start)
				new SubsequenceFrames().init(inputmatch, dbmatch);
			// System.exit(0);
		}
	}

	public void splitToFrame(String path, String pLoc) {
		this.pLoc = pLoc;
		System.out.println("this.pLoc:" + this.pLoc);
		System.out.println(start);
		String url = "file:\\" + path;

		MediaLocator ml;

		if ((ml = new MediaLocator(url)) == null) {
			System.err.println("Cannot build media locator from: " + url);
			System.exit(0);
		}

		if (!open(ml)) {
			System.out.println("Closed");
			System.exit(0);
		}
	}

	boolean start;

	public void compare(boolean start, String path2, String pLoc) {
		this.pLoc = pLoc;
		this.start = start;
		String url = "file:\\" + path2;

		MediaLocator ml;

		if ((ml = new MediaLocator(url)) == null) {
			System.err.println("Cannot build media locator from: " + url);
			System.exit(0);
		}

		if (!open(ml)) {
			System.out.println("Closed");
			System.exit(0);
		}
	}


	class PreAccessCodec implements Codec {

		/**
		 * Callback to access individual video frames.
		 */
		void accessFrame(Buffer frame) {

			// For demo, we'll just print out the frame #, time &
			// data length.

			long t = (long) (frame.getTimeStamp() / 10000000f);

			System.err.println("Pre: frame #: " + frame.getSequenceNumber()
					+ ", time: " + ((float) t) / 100f + ", len: "
					+ frame.getLength());
		}

		/**
		 * The code for a pass through codec.
		 */

		// We'll advertize as supporting all video formats.
		protected Format supportedIns[] = new Format[] { new VideoFormat(null) };

		// We'll advertize as supporting all video formats.
		protected Format supportedOuts[] = new Format[] { new VideoFormat(null) };

		Format input = null, output = null;

		public String getName() {
			return "Pre-Access Codec";
		}

		// No op.
		public void open() {
		}

		// No op.
		public void close() {
		}

		// No op.
		public void reset() {
		}

		public Format[] getSupportedInputFormats() {
			return supportedIns;
		}

		public Format[] getSupportedOutputFormats(Format in) {
			if (in == null)
				return supportedOuts;
			else {
				// If an input format is given, we use that input format
				// as the output since we are not modifying the bit stream
				// at all.
				Format outs[] = new Format[1];
				outs[0] = in;
				return outs;
			}
		}

		public Format setInputFormat(Format format) {
			input = format;
			return input;
		}

		public Format setOutputFormat(Format format) {
			output = format;
			return output;
		}

		public int process(Buffer in, Buffer out) {

			// This is the "Callback" to access individual frames.
			//
			accessFrame(in);

			// Swap the data between the input & output.
			Object data = in.getData();
			in.setData(out.getData());
			out.setData(data);

			// Copy the input attributes to the output
			out.setFormat(in.getFormat());
			out.setLength(in.getLength());
			out.setOffset(in.getOffset());

			return BUFFER_PROCESSED_OK;
		}

		public Object[] getControls() {
			return new Object[0];
		}

		public Object getControl(String type) {
			return null;
		}
	}

	class PostAccessCodec extends PreAccessCodec {

		private Dimension size;

		public PostAccessCodec(Dimension size) {
			supportedIns = new Format[] { new RGBFormat() };
			this.size = size;
		}

		void accessFrame(Buffer frame) {

			BufferToImage stopBuffer = new BufferToImage((VideoFormat) frame
					.getFormat());
			Image stopImage = stopBuffer.createImage(frame);
			File f;
			try {
				BufferedImage outImage = new BufferedImage(size.width,
						size.height, BufferedImage.TYPE_INT_RGB);

				Graphics og = outImage.getGraphics();
				og.setFont(new Font("ArialBlack", 2, 10));
				og.setColor(Color.black);

				og.drawImage(stopImage, 0, 0, size.width, size.height, null);

				Iterator<ImageWriter> writers = ImageIO
						.getImageWritersByFormatName("jpg");
				ImageWriter writer = (ImageWriter) writers.next();
				System.out.println("pLoc:" + pLoc);
				f = new File(pLoc + "/" + "Segment" + (imcnt++) + ".jpg");
				ImageOutputStream ios = ImageIO.createImageOutputStream(f);
				writer.setOutput(ios);

				if (stopSize.contains(f.getName())) {
					try {
						this.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				stopSize += f.getName() + ",";

				writer.write(outImage);
				ios.close();

				if (start) {
					Thread.sleep(70);

					
					totC = new File("Q_Videos").list().length-1;
					if (inVcnt < totC) {
						String cmpdata = "Q_Videos/Segment" + inputCnt + ".jpg";
						File file = new File(cmpdata);
						System.out.print("MF1:"+ file.getName());
						System.out.println("=MF2:"+ f.getName());
						if ((comp.compare(file.getAbsolutePath(), f
								.getAbsolutePath()) == 0)) {
							dbmatch.add(f.getAbsolutePath());
							inputmatch.add(file.getAbsolutePath());
							cng = true;

							label.setText("Subsequence Detected");
							label.setForeground(Color.RED);
						}
					} 
					if (cng) {
						inVcnt++;
						inputCnt++;
					}
				}
				long t = (long) (frame.getTimeStamp() / 10000000f);

				System.err.println("Post: frame #: "
						+ frame.getSequenceNumber() + ", time: " + ((float) t)
						/ 100f + ", len: " + frame.getLength());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public String getName() {
			return "Post-Access Codec";
		}
	}

}