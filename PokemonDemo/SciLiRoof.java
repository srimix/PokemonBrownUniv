package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class SciLiRoof extends PokePanel2 {
	private BufferedImage _roomO;
	
	public SciLiRoof(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public SciLiRoof(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			Trainer t1 = new Trainer.Text();
			t1.createHome(11, 1);
			t1.getDialogue().add("Zipline: Courtesy of Brown Outing Club");
			this.getMovingTrainers().add(t1);
			
			Trainer t2 = new Trainer.Text();
			t2.createHome(16, 1);
			t2.getDialogue().add("Unfortunately locked...");
			this.getMovingTrainers().add(t2);
		
	}

	public void createBaseRoom(){
		
		this.song = M2.SCILI_ROOF;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=230;
		this.yConstant=220;
		this._mapX=134;
		this._mapY=283;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "SciLi Roof?!?";
		this._roomNum = _gbs.SCILI_ROOF;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/SciLi Roof/SciLi Roof Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/SciLi Roof/SciLi Roof Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(19,5);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SciLi Roof.cmap"));
		for(int i = 0; i < 5; i++){
			for(int i2 = 0; i2 < 19; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);

		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if((xInd==1) &&(yInd==2)){
			super.enterRoom(_gbs.WATERFIRE, 18, 23, FACENORTH);
			_gbs.getPlayer().setPkmnCenter(_gbs.WATERFIRE);
		}
	}
	
	public void Start(){
		//this.customTeleportTo(this._roomNum, 13,2);
	}

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}

		super.A_Button();
	}
	
}