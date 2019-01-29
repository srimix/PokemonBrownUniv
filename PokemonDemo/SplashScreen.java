package PokemonDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;


/**
 * This is the SplashScreen. It displays a JWindow
 * with the Pokemon:Brown logo on it for several
 * seconds while the JavaBoyEmulator is instantiated
 * (effectively, while the rest of the program loads).
 * After a few seconds, it disappears, revealing the
 * JavaBoyEmulator.
 * 
 * @author mreiss
 */
public class SplashScreen {

	private Timer _loadTime; 
	private JWindow _splash;
	private JFrame _instructions;
	private JavaBoyEmulator _jbe;
	@SuppressWarnings("unused")
	private int tick = 0;
	private BufferedImage _splashImage;
	
	public void dontLetSriPlay(){
		while(true){
			SysOut.print("SRI, GET BACK TO GRAD SCHOOL APPLICATIONS!");
		}
	}
	
	public SplashScreen(){
		
		//dontLetSriPlay();
		
		_splash = new JWindow();
		//_splash.setSize(600,310);

		
		_splashImage = null;
		
		try{
			_splashImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/SplashScreen/KaranB.png"));
			_splash.setSize(_splashImage.getWidth(), _splashImage.getHeight()+24);
		}
		catch(IOException ioe){
			_splash.setVisible(false);
		}
		
		int screenXCenter = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-_splash.getWidth())/2.0);
		int screenYCenter = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight()-_splash.getHeight())/2.0);
		_splash.setLocation(screenXCenter,screenYCenter);
	//	_splash.setAlwaysOnTop(true);
		
		_instructions = new JFrame("Controls");
		JLabel lin3 = new JLabel ("Gameboy ==> Keyboard");
		JLabel line1 = new JLabel("'A' Button --> 'Z' Key");
		JLabel line4 = new JLabel("'B' Button --> 'X' Key");
		JLabel line5 = new JLabel("'Analog' --> 'Up/Down/Left/Right");
		JLabel line6 = new JLabel("'Start' Button --> 'Enter' Key");
		JLabel lineq = new JLabel();
		JLabel linef = new JLabel("Press the 'Q' Key if you run into a bug.");
		_instructions.setLayout(new GridLayout(8, 1));
		_instructions.add(lin3);
		_instructions.add(line1);
		_instructions.add(line4);
		_instructions.add(line5);
		_instructions.add(line6);
		_instructions.add(lineq);
		_instructions.add(linef);
		
		_instructions.pack();
		
		_jbe = new JavaBoyEmulator("Javaboy Color");
		
		new Thread(){
			public void run(){
				_jbe.gbs.createGame();
			}
		}.start();
		
		_splash.setLayout(new BorderLayout());
		JLabel label = new JLabel(new ImageIcon(_splashImage));
		//label.setOpaque(false);
		label.setBackground(new Color(50,20,20));
		_splash.add(label, BorderLayout.CENTER);
		
		final JProgressBar bar = new JProgressBar(0, 100);
		bar.setValue(0);
		bar.setStringPainted(true);
		
		_splash.add(bar, BorderLayout.SOUTH);
		
		_splash.setBackground(new Color(50,20,20));
		
		_splash.setVisible(true);

		//M.playSong(M.BATTLE_START, false);
		
	//	_jbe.setAlwaysOnTop(true);
		
		new Thread(){
			public void run(){
				boolean b = true;
				double val = 0;
				double prevVal = -1;
				while(b){
					bar.setIndeterminate(false);
					//val = 100.0*(double)_jbe.gbs.getNumRoomsLoaded()/(double)_jbe.gbs.getNumRoomsTotal();
					//if(val != prevVal){
					//	SysOut.print("VALUE: " + val);
					//	prevVal = val;
					//}
					bar.setValue((int)(100.0*(double)_jbe.gbs.getNumRoomsLoaded()/(double)_jbe.gbs.getNumRoomsTotal()));
					if(bar.getValue() >= 98){
						b = false;
						_jbe.gbs.getLS().startTimer();
				//TODO		_instructions.setVisible(true);
						_jbe.setVisible(true);
						_splash.setVisible(false);
					}
				}
			}
		}.start();
		
		
		TickListener tick = new TickListener();
		_loadTime = new Timer((5000), tick);
		//_loadTime.start();
		

	}
	
	/**
	 * The ActionListener that controls how long the
	 * splash image is displayed. After being called once,
	 * the splash disappears and reveals the JavaBoyEmulator.
	 * 
	 * @author mreiss
	 */
	private class TickListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			_loadTime.stop();
			//_jbe = new JavaBoyEmulator("Javaboy Color");
			_jbe.gbs.getLS().startTimer();
			//tick++;
			_instructions.setVisible(true);
			_jbe.setVisible(true);

			_splash.setVisible(false);
			//M.playSong(M.JAVABOY_INTRO, false);
			//System.exit(0);
		
			//if(tick == 30) System.exit(0);
		}
	}
}
