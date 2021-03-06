/*
 * DuplicateFrames.java
 *
 * Created on __DATE__, __TIME__
 */

package design;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * 
 * @author __USER__
 */
public class SubsequenceFrames extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form DuplicateFrames */
	public SubsequenceFrames() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		listDupli = new javax.swing.JList();
		lblF1 = new javax.swing.JLabel();
		lblF2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);

		jScrollPane1.setViewportView(listDupli);

		getContentPane().add(jScrollPane1);
		jScrollPane1.setBounds(20, 60, 210, 460);

		getContentPane().add(lblF1);
		lblF1.setBounds(270, 70, 280, 220);

		getContentPane().add(lblF2);
		lblF2.setBounds(270, 290, 280, 220);

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
		jLabel3.setForeground(new java.awt.Color(0, 0, 255));
		jLabel3.setText("Video Subsequence Frames");
		getContentPane().add(jLabel3);
		jLabel3.setBounds(180, 20, 190, 17);

		listDupli.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getSource() == listDupli) {
					try {
						lblF1.removeAll();
						lblF2.removeAll();
						int sel = listDupli.getSelectedIndex();
						BufferedImage image = ImageIO.read(new File(inputmatch
								.get(sel)));
						Image icon = image.getScaledInstance(280, 220,
								Image.SCALE_SMOOTH);
						ImageIcon im = new ImageIcon(icon);
						lblF1.setIcon(im);

						image = ImageIO.read(new File(dbmatch.get(sel)));
						icon = image.getScaledInstance(280, 220,
								Image.SCALE_SMOOTH);
						im = new ImageIcon(icon);
						lblF2.setIcon(im);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});

		setSize(650, 600);
		setTitle("Query Processing for Video Subsequence Identification::");
		setResizable(false);
		setVisible(true);
	}// </editor-fold>

	// GEN-END:initComponents

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SubsequenceFrames().setVisible(true);
			}
		});
	}

	// GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JLabel jLabel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblF1;
	private javax.swing.JLabel lblF2;
	private javax.swing.JList listDupli;
	Vector<String> inputmatch;
	Vector<String> dbmatch;

	// End of variables declaration//GEN-END:variables
	public void init(Vector<String> inputmatch, Vector<String> dbmatch) {
		this.inputmatch = inputmatch;
		this.dbmatch = dbmatch;
		Vector<String> vec = new Vector<String>();
		for (int i = 0; i < inputmatch.size(); i++) {
			String v1 = inputmatch.get(i);
			v1 = v1.substring(v1.lastIndexOf("\\") + 1, v1.length());
			String v2 = dbmatch.get(i);
			v2 = v2.substring(v2.lastIndexOf("\\") + 1, v2.length());
			vec.add("[ " + v1 + " == " + v2 + " ]");
		}
		listDupli.setListData(vec);

	}

}