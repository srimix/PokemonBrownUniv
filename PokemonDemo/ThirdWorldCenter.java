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
public class ThirdWorldCenter extends PokePanel2 {
	private BufferedImage  _roomO;
	
	public ThirdWorldCenter(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public ThirdWorldCenter(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {

//					
				Trainer anjali = new Trainer.BlackGirl(null);
				anjali.createHome(9, 3, -1, 0, "drunk", "drunk");
				anjali.setStationary(true);
				anjali.getDialogue().clear();
				anjali.getDialogue().add("Anjali: Welcome to the Third World Center!");
				anjali.getDialogue().add("I don't work here any longer, I'm just visiting old friends.");
				
				
				Trainer MG = new Trainer.GreenDress(null);
				MG.createHome(4, 2);
				MG.getDialogue().add("Mary Grace: How are you doing, " + _gbs.getPlayer().getPlayerName() + "? ");
				MG.getDialogue().add("Be honest -- this is a brave space!");
				
				
				Trainer mpc = new Trainer.YellowHatBoy(null);
				mpc.createHome(2,4);
				mpc.setDirectionAndImage(FACEWEST);
				mpc.getDialogue().add("I'm applying to be MPCCFCFCC... ");
			    mpc.getDialogue().add("I think that's what it's called now, anyways.");
				
				
				Trainer tim = new Trainer.BlackHair(null);
				tim.createHome(7, 2, 0, 0, "drunk", "drunk");
				tim.addDest(9, 4);
				tim.addDest(7, 2);
				tim.setFirstDest();
				tim.setPause(true);
				tim.setStationary(false);
				tim.getDialogue().clear();
				tim.getDialogue().add("Tim: " + _gbs.getPlayer().getPlayerName() + "!! Are you going to convocation?");
				tim.getDialogue().add("...Ah alright, I guess you should take on the gyms first.");
				
				Trainer amit = new Trainer.BlackDude(null);
				amit.createHome(3, 3, -1, -2, "drunk", "drunk");
				amit.addDest(6, 4);
				amit.addDest(3, 3);
				amit.setFirstDest();
				amit.setPause(true);
				amit.setStationary(false);
				amit.getDialogue().clear();
				amit.getDialogue().add("Amit: I think the -isms stole my keys.");
				
				this._movingTrainers.add(anjali);
				this._movingTrainers.add(MG);
				this._movingTrainers.add(mpc);
				this._movingTrainers.add(tim);
				this._movingTrainers.add(amit);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.TWC;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-10;
		this.yConstant=21;
		this._mapX=131;
		this._mapY=286;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "ThirdWorldCenter";
		this._roomNum = 22;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/ThirdWorldCenter/TWC Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/ThirdWorldCenter/TWC Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(11,7);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/ThirdWorldCenter.cmap"));
		for(int i = 0; i < 7; i++){
			for(int i2 = 0; i2 < 11; i2++){
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
		
		this.drawBox(g2);

		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 2
 		if((xInd == 8 || xInd== 9) && (yInd == 6)){
 			super.enterRoom(_gbs.ROUTE_2, 39, 48, FACESOUTH);
 		}
		
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