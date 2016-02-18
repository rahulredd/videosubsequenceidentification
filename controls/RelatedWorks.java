package controls;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JPanel;

public class RelatedWorks {

	Player p = null;

	public void mediaPlayer(final String path, final JPanel panel) {
		new Thread() {
			public void run() {
				try {
					p = Manager.createRealizedPlayer(new File(path).toURL());
					Component ctrlpanel = p.getControlPanelComponent();
					Component player = p.getVisualComponent();
					player.setBounds(10, 20, 330, 200);
					ctrlpanel.setBounds(10, 221, 330, 20);
					panel.add(player);

					panel.add(ctrlpanel);
					panel.repaint();
					p.start();
					System.out.println(" Player Started");
				} catch (NoPlayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotRealizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void findDuplicate(String path1, String path2) {
		// TODO Auto-generated method stub

		new FrameAccess().splitToFrame(path1, "Q_Videos");
		// new FrameAccess().splitToFrame(path2,"DB_Images");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new FrameAccess().compare(true, path2, "S_Video");

	}

	public void clear() {
		String[] cFiles={"Q_Videos","S_Video"};
		for (int n = 0; n < cFiles.length; n++) {
			File f = new File(cFiles[n]);
			String[] tempfiles = null;
			if (f.isDirectory()) {
				tempfiles = f.list();
			}
			while (f.list().length != 0) {

				for (int i = 0; i < tempfiles.length; i++) {
					new File(f.getAbsoluteFile() + "\\" + tempfiles[i])
							.delete();
				}
			}
		}

	}
}
