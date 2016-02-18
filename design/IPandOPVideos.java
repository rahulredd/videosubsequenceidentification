package design;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import controls.RelatedWorks;

public class IPandOPVideos extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	public IPandOPVideos() {
		initComponents();
		tools.clear();
	}

	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		panelDB = new javax.swing.JPanel();
		panelInput = new javax.swing.JPanel();
		txtInput = new javax.swing.JTextField();
		btnDuplicate = new javax.swing.JButton();
		btnBrowse = new javax.swing.JButton();
		chkSpecify = new javax.swing.JCheckBox();
		txtSpceify = new javax.swing.JTextField();
		btnSpecifyBrowse = new javax.swing.JButton();

		setDefaultCloseOperation(3);
		getContentPane().setLayout(null);

		jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 13));
		jLabel1.setForeground(new java.awt.Color(51, 51, 255));
		jLabel1.setText("Query Processing for Video Subsequence Identification");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(200, 30, 450, 30);

		panelDB.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Database Video"));
		panelDB.setLayout(null);
		getContentPane().add(panelDB);
		panelDB.setBounds(400, 80, 350, 250);

		panelInput.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Input Video"));
		getContentPane().add(panelInput);
		panelInput.setBounds(30, 80, 350, 250);

		getContentPane().add(txtInput);
		txtInput.setBounds(40, 370, 250, 19);

		btnDuplicate.setText("Subsequence Identification");
		btnDuplicate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDuplicateActionPerformed(evt);
			}
		});
		getContentPane().add(btnDuplicate);
		btnDuplicate.setBounds(320, 420, 150, 40);

		btnBrowse.setText("Browse");
		btnBrowse.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnBrowseActionPerformed(evt);
			}
		});
		getContentPane().add(btnBrowse);
		btnBrowse.setBounds(300, 370, 80, 23);

		chkSpecify.setText("Specify Database Video");
		// getContentPane().add(chkSpecify);
		chkSpecify.setBounds(410, 340, 150, 23);
		getContentPane().add(txtSpceify);
		txtSpceify.setBounds(410, 370, 250, 19);

		btnSpecifyBrowse.setText("Browse");
		btnSpecifyBrowse.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSpecifyBrowseActionPerformed(evt);
			}
		});
		getContentPane().add(btnSpecifyBrowse);
		btnSpecifyBrowse.setBounds(670, 370, 80, 23);

		setSize(780, 520);
		setTitle("Query Processing for Video Subsequence Identification::");
		setResizable(false);
		setVisible(true);
	}// </editor-fold>

	// GEN-END:initComponents

	private void browse(JTextField txtInput) {
		// TODO Auto-generated method stub
		JFileChooser jfr = new JFileChooser();
		int check = jfr.showOpenDialog(this);
		try {
			if (check == JFileChooser.APPROVE_OPTION) {
				String path = jfr.getSelectedFile().getPath();
				txtInput.setText(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void btnSpecifyBrowseActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		browse(txtSpceify);
		panelDB.removeAll();
		tools.mediaPlayer(txtSpceify.getText(), panelDB);
		// tools.mediaPlayer(txtSpceify.getText(), panelDB);
	}

	private void btnDuplicateActionPerformed(java.awt.event.ActionEvent evt) {

		tools.findDuplicate(txtInput.getText(), txtSpceify.getText());
	}

	private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		browse(txtInput);
		panelInput.removeAll();
		tools.mediaPlayer(txtInput.getText(), panelInput);
		// tools.mediaPlayer(txtInput.getText(), panelInput);
	}

	RelatedWorks tools = new RelatedWorks();

	// GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnBrowse;
	private javax.swing.JButton btnDuplicate;
	private javax.swing.JButton btnSpecifyBrowse;
	private javax.swing.JCheckBox chkSpecify;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel panelDB;
	private javax.swing.JPanel panelInput;
	private javax.swing.JTextField txtInput;
	private javax.swing.JTextField txtSpceify;
	// End of variables declaration//GEN-END:variables

}