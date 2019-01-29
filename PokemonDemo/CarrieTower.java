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
public class CarrieTower extends PokePanel2 {
	private BufferedImage _roomO;
	
	public CarrieTower(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public CarrieTower(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				
				Vector<Pokemon> nickBelt = new Vector<Pokemon>();
				for (int i=0; i<6 ; i++){
					nickBelt.add(Pokemon.getPokemonByDexNumber((int)(Math.random()*151)+1).setWildLevel((int)(Math.random()*6)+75));
				}
				Trainer nick = new Trainer.NicholasBrown(nickBelt);
				nick.createHome(1, 1, 0, 0, "wander", "wander");
				nick.addDest(3, 1);
				nick.addDest(1, 1);
				nick.setFirstDest();
				nick.setStationary(false);
				nick.getDialogue().add("Nicholas: ...");
				nick.setDefeatDialogue("...");
				nick.getPostBattleDialogue().add("Nicholas gives a small, satisfied smile.");
				nick.getPostBattleDialogue().add("You start to feel a little faint...");
				nick.setMoney(16800);
				nick.setName("Nicholas Brown Sr.");
				nick.setType("Founder");
				this.getMovingTrainers().add(nick);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.CARRIE;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=48;
		this.yConstant=9;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=130;
		this._mapY=299;
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Carrie Tower";
		this._roomNum = _gbs.CARRIE_TOWER;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/CarrieTower/CarrieTower Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/CarrieTower/CarrieTower Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(5,14);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/CarrieTower.cmap"));
		for(int i = 0; i < 14; i++){
			for(int i2 = 0; i2 < 5; i2++){
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
		//Qgreen
 		if((xInd == 2) && (yInd == 13)){
 			super.enterRoom(_gbs.QUIET_GREEN, 4, 11, FACESOUTH);
 		}
		
	}
	

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}
		
		if(_NPCpage==0 && this.getMovingTrainers().get(0).isDefeated() && this.getMovingTrainers().get(0).isInterrupted()){
			this.customTeleportTo(_gbs.KEENEY_ROOM, 8, 5);
		}

		super.A_Button();
	}
}