package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class LogoScreen extends PokePanel2 {

	private GameBoyScreen _gbs;
	private Timer _time;
	private BufferedImage overlay, color;
	private int x = -1000, tick = 0, _whiteOut = 0;
	private LogoScreen _this;
	private Rectangle2D.Double white = new Rectangle2D.Double();
	
	public LogoScreen(GameBoyScreen gbs){
		super(gbs);
		
		_gbs = gbs;
		_this = this;
		
		this.setBackground(Color.WHITE);
		
		try{
			overlay = ImageIO.read(this.getClass().getResource("/PokemonFiles/JavaBoyLogo/javaboy.png"));
			color = ImageIO.read(this.getClass().getResource("/PokemonFiles/JavaBoyLogo/javaboyBG.png"));
		}
		catch(IOException ioe){}
		
		ColorTimer ct = new ColorTimer();
		_time = new Timer(2, ct);

		white.setFrame(-10,-10,400,400);

	}
	
	public void startTimer(){
		_time.start();
	}
	
	public void A_Button2(){
		M2.playBG(M2.TITLE_SCREEN);
		_gbs.setCurrentPanel(0);
		_gbs.repaint();
		_gbs.getCurrentPanel().repaint();
	}
	
	public void Up(){}
	public void Down(){}
	public void A_Button(){}
	public void Left(){}
	public void Right(){}
	public void B_Button(){}
	public void Start(){}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(color, null, x, 100);
		g2.drawImage(overlay, null, 0, 0);
		
		g2.setColor(new Color(255,255,255,_whiteOut));
		g2.fill(white);
	}
	
	private class ColorTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			x++;
			tick++;
			
			if(x > 0){
				x = 0;
			}
			
			if(tick == 200){
				M2.playFX(M2.JAVABOY_INTRO);
			}
			
			if(tick > 1000){
				_whiteOut++;
				if(_whiteOut > 250){_whiteOut = 255;}
			}
			
			if(tick == 1600){
				_this.A_Button2();
				_time.stop();
			}
			
			_this.repaint();
			
		}
	}
}
