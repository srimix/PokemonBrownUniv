package PokemonDemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * This is the JavaBoyEmulator. It is the JFrame that
 * contains both graphically and logically the
 * GameBoyScreen, a special JPanel that controls the
 * majority of the program's functionality.1
 * 
 * @author mreiss
 */
@SuppressWarnings("serial")
public class JavaBoyEmulator extends JFrame{
	public GameBoyScreen gbs;
	private JWindow backdrop;
	/**
	 * Constructor for the JavaBoyEmulator JFrame.
	 * Sets it to the appropriate size and location,
	 * and instantiates the GameBoyScreen.
	 * 
	 * @param s
	 */
	public JavaBoyEmulator(String s){
		super(s);
		

		Dimension frame = new Dimension(490,530);
		//Dimension frame = new Dimension(480,320);
		//Dimension frame = new Dimension(320,480);
		
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		backdrop = new JWindow();
		backdrop.setSize(d);
		backdrop.setLocation(0,0);


		try {
			final BufferedImage scene = ImageIO.read(this.getClass().getResource("/PokemonFiles/SplashScreen/backdrop2.jpg"));
			backdrop.add(new JPanel(){
				public void paintComponent(Graphics g){
					((Graphics2D) g).drawImage(scene, 0, 0, d.width, d.height, null);
				}
			});
		} catch (IOException e1) {}
		
	//	backdrop.setVisible(true);

		
		this.addWindowListener(new WindowAdapter(){

	          public void windowIconified(WindowEvent e){
	        	  setDropVis(false);
	          }
	          
	          public void windowDeiconified(WindowEvent e){
	  //      	  setDropVis(true);
	        	  setVisible(true);
	          }
	    });
		
		this.setMinimumSize(frame);
		this.setPreferredSize(frame);
		this.setMaximumSize(frame);
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		
		this.requestFocus();
		
		int screenXCenter = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-this.getWidth())/2.0);
		int screenYCenter = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight()-this.getHeight())/2.0);
		
		this.setLocation(screenXCenter,screenYCenter);
		this.setAlwaysOnTop(true);
		
		gbs = new GameBoyScreen(this);
		this.add(gbs);

		this.pack();
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	public void setDropVis(boolean b){
		backdrop.setVisible(b);
	}
}
