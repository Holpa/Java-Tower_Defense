import java.applet.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.JFrame;

public class Frame extends JFrame {
	public static String title = "Game0002";
	public static Dimension size = new Dimension(700,550);
	public static AudioClip AU;

	public Frame() {
		setTitle(title);
		setSize(size);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		init();
	}


	public void init() {


		setLayout(new GridLayout(1,1,0,0));

		Screen screen = new Screen(this);

		add(screen);

		setVisible(true);

	//	AU= Applet.newAudioClip("shingeki no kyojin opening op guren no yumiya linked horizon extended ver.au");
	//	AU.play();

	}

	public URL Get_Location(String filename)
	{
		URL url = null;
		try{
			url= this.getClass().getResource(filename);
		}catch(Exception e){}
		return url;
	}


	public static void main (String args[]) {


		Frame frame = new Frame();	

	}

}


