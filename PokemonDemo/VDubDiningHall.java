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
public class VDubDiningHall extends PokePanel2 {
	private BufferedImage _roomO;
	
	public VDubDiningHall(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public VDubDiningHall(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
		this._movingTrainers=new Vector<Trainer>();
		try{
	 		Vector cbelt = new Vector<Pokemon>();
	 		cbelt.add(new Pokemon.Meowth().setWildLevel(24));
	 		cbelt.add(new Pokemon.Meowth().setWildLevel(24));
	 		cbelt.add(new Pokemon.Meowth().setWildLevel(24));
			Trainer cereal = new Trainer.BlackHairBlueDress(cbelt);
			cereal.createHome(17,3);
			cereal.setDirectionAndImage(FACESOUTH);
			cereal.getDialogue().add("Aah leave me alone, I'm stealing cereal!");
			cereal.getDialogue().add("I don't wanna get caught!");
			cereal.setDefeatDialogue("Busted!");
			cereal.setMoney(360);
			cereal.setType("Undergrad");
			cereal.setName("Jessica");
			
			this.getMovingTrainers().add(cereal);//0
			
			Vector<Pokemon> wBelt = new Vector<Pokemon>();
			wBelt.add(new Pokemon.Dratini().setWildLevel(30));
			wBelt.add(new Pokemon.Dodrio().setWildLevel(30));
			Trainer waff = new Trainer.GlassesGirl(wBelt);
			waff.createHome(11,7,0,0,"freeze","freeze");
			waff.setLOS(3);
			waff.setStationary(false);
			waff.addDest(9, 5);
			waff.addDest(11,7);
			waff.setFirstDest();
			waff.getDialogue().add("Oh no! I didn't spray enough oil, so now my waffle...");
			waff.getDialogue().add("...is stuck to the grill. I'm so angry!!");
			waff.setDefeatDialogue("Sigh. Time to go find a butter knife...");
			waff.setMoney(780);
			waff.setType("Freshman");
			waff.setName("Sarah");
			this.getMovingTrainers().add(waff); //1
	
			Vector<Pokemon> sbelt = new Vector<Pokemon>();
			sbelt.add(new Pokemon.Poliwag().setWildLevel(22));
			sbelt.add(new Pokemon.Poliwag().setWildLevel(22));
			sbelt.add(new Pokemon.Poliwhirl().setWildLevel(22));
			Trainer swipe = new Trainer.YellowHatBoy(sbelt);
			swipe.createHome(10,9,0,0,"freeze","freeze");
			swipe.setLOS(3);
			swipe.setDirectionAndImage(FACEEAST);
			swipe.getDialogue().add("Only one takeout container per person, per entry!");
			swipe.setDefeatDialogue("Ah, who am I kidding, I don't really care. Take 4 boxes.");
			swipe.setMoney(1540);
			swipe.setType("BUDS Worker");
			swipe.setName("Jeff");
			
			this.getMovingTrainers().add(swipe);//2
			
			
			//7,4 sous chef los 4, looking down
			Vector<Pokemon> scbelt = new Vector<Pokemon>();
			scbelt.add(new Pokemon.Goldeen().setWildLevel(20));
			scbelt.add(new Pokemon.Seaking().setWildLevel(25));
			scbelt.add(new Pokemon.Krabby().setWildLevel(20));
			scbelt.add(new Pokemon.Kingler().setWildLevel(25));
			Trainer sc = new Trainer.Chef(scbelt);
			sc.createHome(7,4,0,0,"freeze","freeze");
			sc.setLOS(4);
			sc.setDirectionAndImage(FACENORTH);
			sc.getDialogue().add("Welcome to the VDub! Glad to know you're enjoying the scrod!");
			sc.getDialogue().add(".....wait you're not enjoying the scrod?");
			sc.setDefeatDialogue("Fine. Cajun Chicken Pasta for dinner it is.");
			sc.setMoney(500);
			sc.setType("Sous Chef");
			sc.setName("Emelio");
			
			this.getMovingTrainers().add(sc);//3
	
			Trainer surf = new Trainer.Chef(null);
			surf.createHome(2,5,0,0,"freeze","freeze");
			surf.setDirectionAndImage(FACEWEST);
			surf.getDialogue().add("Sorry about the puddles on the floor!");
			surf.getDialogue().add("Earlier there was a kitchen fire and no one could enter.");
			surf.getDialogue().add("But with my trusty Seadra and her ability to Surf, we were able to...");
			surf.getDialogue().add("...wash out the whole building with a huge wave. Dinner is served!");
			surf.getDialogue().add("I think you could make good use of this.");
			surf.setGift(new Item.HM03_Surf());
			surf.getPostItemDialogue().add("I heard Thayer is being particularly bad with its flooding.");
			surf.getPostItemDialogue().add("Why don't you give it a try there?");
			
			this.getMovingTrainers().add(surf);//4
			
			///NO EVENTS:
	
	
	
			// 15 9, 13 9 // doesn't it always feel a little damp in here?
			Trainer scrod= new Trainer.Backpacker(null);
			scrod.createHome(12,4,0,0,"wander","wander");
			scrod.setStationary(false);
			scrod.addDest(20,6);
			scrod.addDest(12,4);
			scrod.setFirstDest();
			scrod.getDialogue().add("Ugh, scrod. I'd rather eat the Tofu Nuggets...");
			scrod.getDialogue().add(".......Tofuggets?");
			this.getMovingTrainers().add(scrod);//5 
			
			Trainer damp = new Trainer.RedAndYellow(null);
			damp.createHome(15,9,0,0,"wander","wander");
			damp.setStationary(false);
			damp.addDest(13,9);
			damp.addDest(15,9);
			damp.setFirstDest();
			damp.getDialogue().add("Doesn't it always feel a little damp in here?");
			this.getMovingTrainers().add(damp);//6
	
			Trainer sea = new Trainer.Seadra(this);
			sea.createHome(4,4,0,0,"freeze","freeze");
			sea.setStationary(false);
			sea.addDest(4,6);
			sea.addDest(2,6);
			sea.addDest(4,4);
			sea.setFirstDest();
			sea.getDialogue().add("Seadra: Kroo.");
			sea.getDialogue().add("Seadra looks proud.");
			this.getMovingTrainers().add(sea);
		}
		catch(IOException ioe){}

		
	}

	public void createBaseRoom(){
		
		this.song = M2.VDUB_DINING_HALL;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-16;
		this.yConstant=-7;
		this._mapX=135;
		this._mapY=235;

		this.setBikeAllow(false);
		this.setBattleBG(NewBattleScreen.INDOORS);
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "VDub Dining Hall";
		this._roomNum = 50;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/VDub/VDub Dining Hall Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/VDub/VDub Dining Hall Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(24,14);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/VDubDiningHall.cmap"));
		for(int i = 0; i < 14; i++){
			for(int i2 = 0; i2 < 24; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 2
 		if((xInd == 8 || xInd == 9) && (yInd == 13)){
 			super.enterRoom(_gbs.VDUB, 20, 8, FACEWEST);
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