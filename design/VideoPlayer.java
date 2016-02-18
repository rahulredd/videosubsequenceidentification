
package design;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.Player;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class VideoPlayer implements ActionListener{

	JFrame frame;
	Component ctrlpanel=null,player=null;
	JPanel panel1;
	JButton btnClose;
	Container c;
	Player p ;
	
	public VideoPlayer() {   
	   
    }
	public void VideoAnnotate_Method()
	{
	    //JFrame.setDefaultLookAndFeelDecorated(true);
	    frame=new JFrame("Media Player");
	    
		panel1=new JPanel();
		btnClose=new JButton("Close");	
		btnClose.setBounds(280,390,80,20);
		btnClose.addActionListener(this);
		panel1.add(btnClose);
		c=frame.getContentPane();
		c.setLayout(null);
		c.setBackground(Color.LIGHT_GRAY);
	
		panel1.setLayout(null);
		panel1.setBackground(Color.LIGHT_GRAY);
		panel1.setBounds(0,0,600,430);

		c.add(panel1);
				
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 760)/2, (Toolkit.getDefaultToolkit().getScreenSize().height - 580)/2);
		frame.setSize(630,500);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
	    if(e.getSource()==btnClose)
	    {
	        frame.setVisible(false);
	    }	
	}

	
	public void MediaPlayer(String playPath)throws java.io.IOException,
    java.net.MalformedURLException,
    javax.media.MediaException{

 	String fff=playPath;
    if(fff!=null)
    { 
    File f = new File (fff);
    	  
     p = Manager.createRealizedPlayer(f.toURL());
  	 ctrlpanel=p.getControlPanelComponent();
     player = p.getVisualComponent();
     
     player.setBounds(120,50,400,300);
     ctrlpanel.setBounds(120,352,400,20);
     panel1.add (player);
     panel1.add(ctrlpanel);
     p.start();
     System.out.println(" Player Started");	
    }
	}
	
}
