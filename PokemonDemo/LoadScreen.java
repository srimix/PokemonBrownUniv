package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This subclass of PokePanel is responsible for
 * determining whether or not the user wishes to
 * start a new game or continue from a previously
 * saved game. It returns the user's choice back to
 * the GameBoyScreen, where appropriate action is taken.
 * 
 * @author mreis
 */
@SuppressWarnings("serial")
public class LoadScreen extends PokePanel2{
	
	private Ellipse2D.Double _select;
	private Graphics2D _g2;
	private BufferedImage _loadMenu;
	private GameBoyScreen _gbs;
	
	private Rectangle2D.Double _outline, _progress;
	
	public LoadScreen(GameBoyScreen gbs){
		super(gbs);
		
		_gbs = gbs;
		
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		
		this._darkLevel = 0;
		
		_select = new Ellipse2D.Double();
		_select.height = 5;
		_select.width = 5;
		_select.x = 35;
		_select.y = 32;
		
		_outline = new Rectangle2D.Double();
		_outline.height = 15;
		_outline.width = 100;
		_outline.x = 155;
		_outline.y = 155;
		
		_progress = new Rectangle2D.Double();
		_progress.height = 13;
		_progress.x = 156;
		_progress.y = 156;
		
		_loadMenu = null;
		
		try{
			_loadMenu = ImageIO.read(this.getClass().getResource("/PokemonFiles/SaveLoadScreen/LoadBox1.png"));
		}
		catch(IOException ioe){}
		
	}
	
	public void Up(){
		_select.y = 32;
		this.repaint();
	}
	
	public void Down(){
		_select.y = 53;
		this.repaint();
	}
	
	public void Left(){}
	public void Right(){}
	
	public void A_Button(){
		if(_select.y == 32){
			_gbs.loadGame();
		}
		if(_select.y == 53){
			//_gbs.playSong(M.RUTH_INTRO);
			//_gbs.createGame();
			_gbs.runGame();
			M2.playBG(M2.RUTH_INTRO);
		}
	}
	
	public void B_Button(){
		
	}
	
	public void Start(){
		this.A_Button();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		_g2 = (Graphics2D) g;
		
		_g2.drawImage(_loadMenu, null, 10,10);
		_g2.setColor(Color.BLACK);
		_g2.fill(_select);
//		_g2.setStroke(new BasicStroke(2));
		
//		if(_loading){
//			SysOut.print("LOADING");
//			int loadProgress = (int)((100.0*_gbs.roomsLoaded)/_gbs.numRooms);
//			_g2.setColor(Color.BLACK);
//			_g2.draw(_outline);
//			
//			if(loadProgress < 25)
//				_g2.setColor(Color.RED);
//			else if(loadProgress < 50)
//				_g2.setColor(Color.ORANGE);
//			else
//				_g2.setColor(Color.GREEN);
//			
//			_progress.width = loadProgress;
//			_g2.draw(_progress);
//		}
	}
}
