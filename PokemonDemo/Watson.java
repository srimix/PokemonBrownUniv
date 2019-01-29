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
public class Watson extends PokePanel2 {
	public static boolean onSecondFloor = false;
	private BufferedImage carpet, shadows, fences;
	
	public Watson(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Watson(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try{
			Trainer safari = new Trainer.StrawHat(null);
			safari.createHome(11, 4);
			safari.setDirectionAndImage(FACENORTH);
			safari.getDialogue().add("This door leads to the Starr Plaza Safari Zone!");
			safari.getDialogue().add("It's a bamboo garden filled with exotic pokemon...");
			safari.getDialogue().add("...not found elsewhere. You should take a look!");
			if(!_gbs.getPlayer().isGymLeaderDefeated(5)){
			 safari.setVanishing(true);
			 safari.defeat();
			}
			this.getMovingTrainers().add(safari); //0
			
			Trainer sblock1 = new Trainer.Text();
			sblock1.createHome(11, 2);
			sblock1.setDirectionAndImage(FACENORTH);
			sblock1.getDialogue().add("The door to Starr Plaza is currently closed.");
			if(_gbs.getPlayer().isGymLeaderDefeated(5)){
			 sblock1.setVanishing(true);
			 sblock1.defeat();
			}
			this.getMovingTrainers().add(sblock1); //1
			
			Trainer sblock2 = new Trainer.Text();
			sblock2.createHome(12, 2);
			sblock2.setDirectionAndImage(FACENORTH);
			sblock2.getDialogue().add("The door to Starr Plaza is currently closed.");
			if(_gbs.getPlayer().isGymLeaderDefeated(5)){
			 sblock2.setVanishing(true);
			 sblock2.defeat();
			}
			this.getMovingTrainers().add(sblock2); //2
			
			
			//Items
			
			//More random trainers with things to say.
			Trainer jou = new Trainer.BrownMediumHair(null);
			jou.createHome(14, 5);
			jou.setDirectionAndImage(FACEWEST);
			jou.getDialogue().add("Do you know where the Joukowsky Forum is?");
			jou.getDialogue().add("I was told it was near this end....");
			this.getMovingTrainers().add(jou); //3
			
			}
			catch(IOException ioe){}
		
	}

	public void scanForAllEvents(){
		if(onSecondFloor){
			this.getCheckList().set(0, 1);
			_gbs.getPlayer().setIgnoring(true);
		}
		else{
			this.getCheckList().set(0, 0);
			_gbs.getPlayer().setIgnoring(false);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList()!=null){
			if(this.getCheckList().get(0)==1){
				onSecondFloor=true;
				_gbs.getPlayer().setIgnoring(true);
			}
			else{
				onSecondFloor=false;
				_gbs.getPlayer().setIgnoring(false);
			}
		}
	}
	
	public void createBaseRoom(){
		
		this.song = M2.WATSON;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=40;
		this.yConstant=2;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX = 214;
		this._mapY = 335;
		

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Watson Institute";
		this._roomNum = _gbs.WATSON;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Watson/Watson Background-1.png"));
				 shadows = ImageIO.read(this.getClass().getResource("/PokemonFiles/Watson/Watson Shadows-2.png"));
				 carpet = ImageIO.read(this.getClass().getResource("/PokemonFiles/Watson/Watson Carpet-3.png"));
				 fences = ImageIO.read(this.getClass().getResource("/PokemonFiles/Watson/Watson FencesOver-4.png"));
				 
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(18,10);
		Scanner scan;
		if(onSecondFloor){
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WatsonSecond.cmap"));
		}
		else{
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WatsonFirst.cmap"));
		}
		for(int i = 0; i < 10; i++){
			for(int i2 = 0; i2 < 18; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if(!onSecondFloor){
			g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
			g2.drawImage(shadows, null, this._xSpace, this._ySpace);
			this.drawPlayer(g2);
			this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
			g2.drawImage(carpet, null, this._xSpace, this._ySpace);
			g2.drawImage(fences, null, this._xSpace, this._ySpace);
		}
		else{
			g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
			g2.drawImage(shadows, null, this._xSpace, this._ySpace);
			this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
			g2.drawImage(carpet, null, this._xSpace, this._ySpace);
			this.drawPlayer(g2);
			g2.drawImage(fences, null, this._xSpace, this._ySpace);
		
		}
		
		


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		_gbs.getPlayer().setIgnoring(false);
		//Gym
 		if((xInd == 15) && (yInd == 1)){
 			this.scanForAllEvents();
 			super.enterRoom(_gbs.WATSON_GYM, 19, 36, FACENORTH);
 			
 		}

 		if((xInd == 11 || xInd == 12) && (yInd == 2)){
 			super.enterRoom(_gbs.BAMBOO_GARDEN, xInd-6, 12, FACENORTH);
 		}
 		
 		if((xInd == 11 || xInd == 12) && (yInd == 8)){
 			super.enterRoom(_gbs.THAYER_SOUTH,17,68, FACEWEST);
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
	
	public void Up(){
		if(!_menuVisible && (_xIndex >= 3 && _xIndex <= 8) && (_yIndex ==5)){
			_gbs.getPlayer().faceUp();
			return;
		}
		if(!_menuVisible && (_xIndex >= 4 && _xIndex <= 8) && (_yIndex == 6)){
			_gbs.getPlayer().faceUp();
			return;
		}
		if(!_menuVisible && (_xIndex >= 3 && _xIndex <= 7) && (_yIndex == 7)){
			_gbs.getPlayer().faceUp();
			return;
		}
		if(!_menuVisible && NORTH && _yIndex == 3 && !_dialogueVisible){
			if((_xIndex == 11||_xIndex==12) && !_gbs.getPlayer().isGymLeaderDefeated(5)){
				this.A_Button();
				return;
			}
		}
		super.Up();
	}
	
	public void Down(){
		if(!_menuVisible && (_xIndex >= 3 && _xIndex <= 8) && (_yIndex ==4)){
			_gbs.getPlayer().faceDown();
			return;
		}
		if(!_menuVisible && (_xIndex >= 4 && _xIndex <= 8) && (_yIndex ==5)){
			_gbs.getPlayer().faceDown();
			return;
		}
		if(!_menuVisible && (_xIndex >= 3 && _xIndex <= 7) && (_yIndex == 6)){
			_gbs.getPlayer().faceDown();
			return;
		}
		super.Down();
	}
	
	public void Left(){
		if(!_menuVisible && (_xIndex == 3) && (_yIndex == 5 || _yIndex ==6)){
			_gbs.getPlayer().faceLeft();
			return;
		}
		if(!_menuVisible && (_xIndex == 9) && (_yIndex == 5) && !onSecondFloor){
			_gbs.getPlayer().faceLeft();
			return;
		}
		if(!_menuVisible && (_xIndex == 5) && (_yIndex == 5)){
			onSecondFloor=false;
			_gbs.getPlayer().setIgnoring(false);
			this.createGrid();
		}
		super.Left();
	}
	
	public void Right(){
		if(!_menuVisible && (_xIndex == 2) && (_yIndex == 5 || _yIndex ==6)){
			_gbs.getPlayer().faceRight();
			return;
		}
		if(!_menuVisible && (_xIndex == 5) && (_yIndex == 5)){
			onSecondFloor=true;
			_gbs.getPlayer().setIgnoring(true);
			this.createGrid();
		}
		
		super.Right();
	}
	
	
}