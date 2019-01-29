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
public class SlaterBasement extends PokePanel2 {
	private BufferedImage _roomO;
	
	public SlaterBasement(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();

	
	}

	public SlaterBasement(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			Trainer champ = new Trainer.ChampGuy();
			champ.createHome(4,4);
			champ.getDialogue().add("You know, I've been calling you 'Champ-in-the-making' for a reason.");
			champ.getDialogue().add("I believed you were capable of going the distance.");
			champ.getDialogue().add("And you have!");
			champ.getDialogue().add("Welcome to Slater Hall, home of Brown's top Pokemon trainers...");
			champ.getDialogue().add("The Elite Four!");
			champ.getDialogue().add("You'll have to take them all on, one after another.");
			champ.getDialogue().add("Now would be a great time to heal, stock up on Potions...");
			champ.getDialogue().add("...and save your game. *cough*meta*cough*");
			champ.getDialogue().add("Some people, the programmers included, weren't sure you could make it this far.");
			champ.getDialogue().add("(Don't take that personally, they kind of assumed the game would crash before now).");
			champ.getDialogue().add("Prove them all wrong, and become Champion of Brown!!!");
			_movingTrainers.add(champ);
			
			Trainer mart = new Trainer.MartMenu("Slater", this);
			mart.createHome(7, 1);
			this._thisMartContains.add(Item.GREAT_BALL);
			this._thisMartContains.add(Item.ULTRA_BALL);
			this._thisMartContains.add(Item.HYPER_POTION);
			this._thisMartContains.add(Item.MAX_POTION);
			this._thisMartContains.add(Item.FULL_RESTORE);
			this._thisMartContains.add(Item.FULL_HEAL);
			this._thisMartContains.add(Item.REVIVE);
			this._thisMartContains.add(Item.MAX_REPEL);
			_movingTrainers.add(mart);
			
			Trainer pokeCenter = new Trainer.PokemonCenter(_gbs.getPlayer(), _gbs.SLATER_BASEMENT, this);
			pokeCenter.createHome(5, 1);
			_movingTrainers.add(pokeCenter);
			
			Trainer pc = new Trainer.PC(_gbs);
			pc.createHome(0, 5);
			_movingTrainers.add(pc);
			
			Trainer pc2 = new Trainer.PC(_gbs);
			pc2.createHome(1, 5);
			_movingTrainers.add(pc2);
			
	}

	public void createBaseRoom(){
		
		this.song = M2.GYM;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.setMartVisible(false);
		
		this.xConstant=2;
		this.yConstant=1;
		
		
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=134;
		this._mapY=283;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		this._pkmnCentX=5;
		this._pkmnCentY=2;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Slater Basement";
		this._roomNum = _gbs.SLATER_BASEMENT;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/SlaterBasement/Lobby.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(10,8);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SlaterBasement.cmap"));
		for(int i = 0; i < 8; i++){
			for(int i2 = 0; i2 < 10; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);

		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){

		if((xInd == 4 || xInd == 5) && yInd == 8){
			this.enterRoom(_gbs.MAIN_GREEN, 8,30,FACESOUTH);
		}
		
		if(yInd == 0){
			this.enterRoom(_gbs.SLATER_MIKE, 4,9,FACENORTH);
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