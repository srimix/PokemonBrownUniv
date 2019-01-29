package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Credits extends PokePanel2{

	private BufferedImage train;
	
	public Credits(GameBoyScreen gbs){
		super(gbs);
		this.initializeEventVector(0);
	
	}
	
	public void Start(){
		
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		
		this.song = M2.CREDITS;
		this.yConstant=-5;
	
		this._movingTrainers = new Vector<Trainer>();
		Trainer tex = new Trainer.Text();
		tex.createHome(0,0);
		_movingTrainers.add(tex);
		this._roomNum=_gbs.CREDITS;
		
		this.description = "Credits";
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Credits/credits.png"));
			train = ImageIO.read(this.getClass().getResource(  "/PokemonFiles/Credits/train.png"));
		}
		catch(IOException ioe){
			_gbs.BACKGROUND = null;
			ioe.printStackTrace();
		}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(100,1);
		this._room._roomGrid = new char[100][1];
		for(int i = 0; i < 100; i++){
			this._room._roomGrid[i][0] = 'r';
		}
		
		this._room._roomGrid[98][0] = 'D';
		this._room._roomGrid[99][0] = 'F';
	}
	
	public void enterRoom(int xInd, int yInd){
		this.enterRoom(_gbs.KEENEY_ROOM, 6,3,FACESOUTH);
		_gbs.saveGame("PostEliteFour.pkmn");
		QuietGreen.champion = false;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, 0);
		
		//this.drawPlayer(g2);
		g2.drawImage(train, null, 110, 168);
	}
	
}
