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
public class WatsonGym extends PokePanel2 {
	private final int
	MINH=1,
	AX1=19, 
	AY1=32,
	AX2=33,
	AY2=32,
	BX1=35,
	BY1=19,
	BX2=33,
	BY2=34,
	CX1=35,
	CY1=34,
	CX2=34,
	CY2=2,
	DX1=3,
	DY1=33,
	DX2=35,
	DY2=32,
	EX1=5,
	EY1=2,
	EX2=2,
	EY2=19,
	FX1=36,
	FY1=20,
	FX2=19,
	FY2=1,
	GX1=32,
	GY1=20,
	GX2=31,
	GY2=2,
	HX1=19,
	HY1=4,
	HX2=33,
	HY2=19,
	IX1=3,
	IY1=2,
	IX2=17,
	IY2=4,
	JX1=31,
	JY1=4,
	JX2=1,
	JY2=35,
	KX1=3,
	KY1=35,
	KX2=34,
	KY2=4,
	LX1=1,
	LY1=33,
	LX2=5,
	LY2=4,
	MX1=4,
	MY1=17,
	MX2=17,
	MY2=1,
	NX1=4,
	NY1=19,
	NX2=3,
	NY2=4,
	OX1=20,
	OY1=18,
	OX2=2,
	OY2=17,
	HOME_X=18,
	HOME_Y=18;
	
			
	
	public WatsonGym(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(9);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public WatsonGym(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(9);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try{
			Trainer champ = new Trainer.ChampGuy();
			champ.createHome(18,34);
			champ.getDialogue().add("Hey, Champ in the making!");
			champ.getDialogue().add("Minh Luong has traveled the world...");
			champ.getDialogue().add("...and obtained Flying and Water Pokemon from all over.");
			champ.getDialogue().add("Stick with Electric-type, and you should be all set.");
			champ.getDialogue().add("But be careful in his gym.");
			champ.getDialogue().add("He uses teleporter pads to make you travel almost as much as he has!");
			
			
			Vector<Pokemon> minhB = new Vector<Pokemon>();
			minhB.add(new Pokemon.Gyarados().setWildLevel(38)); //water
			minhB.add(new Pokemon.Fearow().setWildLevel(37)); //flying
			minhB.add(new Pokemon.Dodrio().setWildLevel(38)); //flying
			minhB.add(new Pokemon.Kingler().setWildLevel(43)); //water
			Trainer minh = new Trainer.Scientist(minhB);
			minh.createHome(19,17);
			minh.getDialogue().add("Pokemon have been studied throughout history.");
			minh.getDialogue().add("Empires have risen and fallen over time, but one fact remains true.");
			minh.getDialogue().add("The strongest Pokemon trainers become legendary!");
			minh.setDefeatDialogue("Well, well. Let's see what history says about YOU!");
			minh.getPostBattleDialogue().add("The greatest leaders know when to humble...");
			minh.getPostBattleDialogue().add("...themselves before a new commander. I am impressed!");
			minh.getPostBattleDialogue().add("Wear this proudly, a mark of your victory.");
			//Get badge
			minh.getPostBattleDialogue().add(_gbs.getPlayer().getPlayerName() + " received the Empire Badge!");
			minh.getPostBattleDialogue().add("To guide you on your path, take this TM.");
			minh.setGift(new Item.TM43_SkyAttack()); //Sky Attack  OR Razor Wind.
			minh.getPostItemDialogue().add("That's TM43, Sky Attack!");
			minh.getPostItemDialogue().add("A fierce move worthy of the strongest bird Pokemon.");
						
			
			Vector<Pokemon> oneB = new Vector<Pokemon>();
			oneB.add(new Pokemon.Slowpoke().setWildLevel(33));
			oneB.add(new Pokemon.Slowpoke().setWildLevel(33));
			oneB.add(new Pokemon.Slowbro().setWildLevel(33));
			Trainer one = new Trainer.BlondeDude(oneB);
			one.createHome(2, 3);
			one.setDirectionAndImage(FACEWEST);
			one.getDialogue().add("This is the International Relations building!");
			one.getDialogue().add(" Leave your upside down maps and granola outside!");
			one.setDefeatDialogue("Oh, all right - I'll start studying Africa.");
			one.setLOS(6);
			one.setMoney(330);
			one.setType("IR Concentrator");
			one.setName("Andrew");
			one.setBattleImage(TImg.M_INDIANA_JONES);
			
			
			Vector<Pokemon> twoB = new Vector<Pokemon>();
			twoB.add(new Pokemon.Fearow().setWildLevel(34));
			twoB.add(new Pokemon.Dodrio().setWildLevel(34));
			Trainer two = new Trainer.BlackHairBlueDress(twoB);
			two.createHome(3, 16);
			two.setDirectionAndImage(FACENORTH);
			two.getDialogue().add("Schiller's last speech made me cry.");
			two.setDefeatDialogue("That's totally unconstitutional.");
			two.setLOS(5);
			two.setMoney(340);
			two.setType("Poli Sci Concentrator");
			two.setName("Sachi");
			two.setBattleImage(TImg.F_PINK_SKIRT_WHITE_HAT);
			
			
			Vector<Pokemon> threeB = new Vector<Pokemon>();
			threeB.add(new Pokemon.Starmie().setWildLevel(38));
			Trainer three = new Trainer.Ambika(threeB);
			three.createHome(35, 3);
			three.setDirectionAndImage(FACEEAST);
			three.getDialogue().add("You must have an appointment in order to see the advisor!");
			three.setDefeatDialogue("Oh look - an appointment just opened up for you.");
			three.setLOS(5);
			three.setMoney(1140);
			three.setType("IR Concentrator");
			three.setName("Ambika");
			
			Vector<Pokemon> foureB = new Vector<Pokemon>();
			foureB.add(new Pokemon.Pidgeot().setWildLevel(38));
			Trainer foure = new Trainer.BrownGuy(foureB);
			foure.createHome(18, 5);
			foure.setDirectionAndImage(FACESOUTH);
			foure.getDialogue().add("You're not taking my seat in Great Pokemon and Empires!");
			foure.setDefeatDialogue("I guess I'll take a Schiller class instead...");
			foure.setLOS(5);
			foure.setMoney(380);
			foure.setType("Poli Sci Concentrator");
			foure.setName("Ben");
			foure.setBattleImage(TImg.M_RANGER);
			
			Vector<Pokemon> fiveB = new Vector<Pokemon>();
			fiveB.add(new Pokemon.Seel().setWildLevel(34));
			fiveB.add(new Pokemon.Dratini().setWildLevel(34));
			Trainer five = new Trainer.RedOveralls(fiveB);
			five.createHome(34,7);
			five.setDirectionAndImage(FACENORTH);
			five.getDialogue().add("I hear the professor helped out the undercover agencies!");
			five.getDialogue().add("Who knows what kind of tactics he picked up from them...");
			five.setDefeatDialogue("Maybe I should try the CIA summer internship...");
			five.setLOS(5);
			five.setMoney(1020);
			five.setType("Poli Sci Concentrator");
			five.setName("Shawn");
			five.setBattleImage(TImg.M_RANGER);
			

			Vector<Pokemon> sixB = new Vector<Pokemon>();
			sixB.add(new Pokemon.Pidgeotto().setWildLevel(33));
			sixB.add(new Pokemon.Farfetchd().setWildLevel(33));
			sixB.add(new Pokemon.Spearow().setWildLevel(33));
			Trainer six = new Trainer.DirtyBlondeGirl(sixB);
			six.createHome(2, 32);
			six.setDirectionAndImage(FACENORTH);
			six.getDialogue().add("OMG, are you in Prof. Tomasi's class?");
			six.getDialogue().add("He looks JUST like Richard Gere!");
			six.setDefeatDialogue("I'm going to see if I can get into his discussion section.");
			six.setLOS(5);
			six.setMoney(990);
			six.setType("Poli Sci Concentrator");
			six.setName("Alexa");
			six.setBattleImage(TImg.F_BLONDIE);
			
			Vector<Pokemon> sevenB = new Vector<Pokemon>();
			sevenB.add(new Pokemon.Staryu().setWildLevel(31));
			sevenB.add(new Pokemon.Krabby().setWildLevel(31));
			sevenB.add(new Pokemon.Seel().setWildLevel(31));
			sevenB.add(new Pokemon.Wartortle().setWildLevel(31));
			Trainer seven = new Trainer.GlassesGuy1(sevenB);
			seven.createHome(34, 31);
			seven.setDirectionAndImage(FACENORTH);
			seven.getDialogue().add("I'm a realist! I believe pokemon need to be strong...");
			seven.getDialogue().add("...and rely on only themselves to succeed.");
			seven.getDialogue().add("None of that constructivism nonsense!");
			seven.setDefeatDialogue("I lost? Perhaps time to make friends with some other trainers...");
			seven.setLOS(5);
			seven.setMoney(310);
			seven.setType("IR Concentrator");
			seven.setName("Mike Roh");
			seven.setBattleImage(TImg.M_CONSTRUCTION);
			
			
			this.getMovingTrainers().add(champ); //0
			this.getMovingTrainers().add(minh); //1
			this.getMovingTrainers().add(one); //2
			this.getMovingTrainers().add(two); //3
			this.getMovingTrainers().add(three); //4
			this.getMovingTrainers().add(foure); //5
			this.getMovingTrainers().add(five); //6
			this.getMovingTrainers().add(six); //7
			this.getMovingTrainers().add(seven); //8
			
			}
			catch(IOException ioe){
				
			}
	
	}

	public void scanForAllEvents(){
		for(int i=0; i<this._movingTrainers.size();i++){
			if(this._movingTrainers.get(i).isDefeated()){
				this.getCheckList().set(i, 1);
			}
			else{this.getCheckList().set(i, 0);}
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList()!=null)
		for(int i=0; i<this._movingTrainers.size();i++){
			if(this.getCheckList().get(i)==1){
				this._movingTrainers.get(i).defeatAndPostBattle();
			}
		}
	}
	
	public void createBaseRoom(){
		
		this.song = M2.GYM;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=63;
		this.yConstant=18;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX = 214;
		this._mapY = 335;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		//this._pkmnCentX=;
		//this._pkmnCentY=;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;

		this.setBikeAllow(false);
		_gbs.getPlayer().setIgnoring(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Watson Gym";
		this._roomNum = _gbs.WATSON_GYM;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/WatsonGym/WatsonGym.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(38,37);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WatsonGym.cmap"));
		for(int i = 0; i < 37; i++){
			for(int i2 = 0; i2 < 38; i2++){
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


		this.drawAllGenerics(g2);
	}
	
	public void afterBattle(){
		super.afterBattle();
		//MINH is first trainer in the vector
		if (_interruptedTrainer==1){
			_gbs.getPlayer().defeatGymLeader(5);
			this.getMovingTrainers().get(1).defeat();
			this.getMovingTrainers().get(2).defeat();
			this.getMovingTrainers().get(3).defeat();
			this.getMovingTrainers().get(4).defeat();
			this.getMovingTrainers().get(5).defeat();
			this.getMovingTrainers().get(6).defeat();
			this.getMovingTrainers().get(7).defeat();
			this.getMovingTrainers().get(8).defeat();
		}
	}
	
	public void enterRoom(int xInd, int yInd){
 		if((xInd == 19) && (yInd == 36)){
 			Watson.onSecondFloor=true;
 			_gbs.getPlayer().setIgnoring(true);
 			_gbs.getPanel(_gbs.WATSON).getCheckList().set(0, 1);
 			super.enterRoom(_gbs.WATSON, 15, 1, FACESOUTH);
 		}
	}


	public void enterRoomSeamless(int xInd, int yInd){
		if((xInd == AX1) && (yInd == AY1)){
			this.customTeleportTo(this._roomNum, AX2, AY2);
		}
		if((xInd == AX2) && (yInd == AY2)){
			this.customTeleportTo(this._roomNum, AX1, AY1);
		}
		
		
		if((xInd == BX1) && (yInd == BY1)){
			this.customTeleportTo(this._roomNum, BX2, BY2);
		}
		if((xInd == BX2) && (yInd == BY2)){
			this.customTeleportTo(this._roomNum, BX1, BY1);
		}
		
		
		if((xInd == CX1) && (yInd == CY1)){
			this.customTeleportTo(this._roomNum, CX2, CY2);
		}
		if((xInd == CX2) && (yInd == CY2)){
			this.customTeleportTo(this._roomNum, CX1, CY1);
		}
		
		
		if((xInd == DX1) && (yInd == DY1)){
			this.customTeleportTo(this._roomNum, DX2, DY2);
		}
		if((xInd == DX2) && (yInd == DY2)){
			this.customTeleportTo(this._roomNum, DX1, DY1);
		}
		
		
		if((xInd == EX1) && (yInd == EY1)){
			this.customTeleportTo(this._roomNum, EX2, EY2);
		}
		if((xInd == EX2) && (yInd == EY2)){
			this.customTeleportTo(this._roomNum, EX1, EY1);
		}
		
		
		if((xInd == FX1) && (yInd == FY1)){
			this.customTeleportTo(this._roomNum, FX2, FY2);
		}
		if((xInd == FX2) && (yInd == FY2)){
			this.customTeleportTo(this._roomNum, FX1, FY1);
		}
		
		
		if((xInd == GX1) && (yInd == GY1)){
			this.customTeleportTo(this._roomNum, GX2, GY2);
		}
		if((xInd == GX2) && (yInd == GY2)){
			this.customTeleportTo(this._roomNum, GX1, GY1);
		}
		
		
		if((xInd == HX1) && (yInd == HY1)){
			this.customTeleportTo(this._roomNum, HX2, HY2);
		}
		if((xInd == HX2) && (yInd == HY2)){
			this.customTeleportTo(this._roomNum, HX1, HY1);
		}
		
		
		if((xInd == IX1) && (yInd == IY1)){
			this.customTeleportTo(this._roomNum, IX2, IY2);
		}
		if((xInd == IX2) && (yInd == IY2)){
			this.customTeleportTo(this._roomNum, IX1, IY1);
		}
		
		
		if((xInd == JX1) && (yInd == JY1)){
			this.customTeleportTo(this._roomNum, JX2, JY2);
		}
		if((xInd == JX2) && (yInd == JY2)){
			this.customTeleportTo(this._roomNum, JX1, JY1);
		}
		
		
		if((xInd == KX1) && (yInd == KY1)){
			this.customTeleportTo(this._roomNum, KX2, KY2);
		}
		if((xInd == KX2) && (yInd == KY2)){
			this.customTeleportTo(this._roomNum, KX1, KY1);
		}
		

		if((xInd == LX1) && (yInd == LY1)){
			this.customTeleportTo(this._roomNum, LX2, LY2);
		}
		if((xInd == LX2) && (yInd == LY2)){
			this.customTeleportTo(this._roomNum, LX1, LY1);
		}
		
		if((xInd == MX1) && (yInd == MY1)){
			this.customTeleportTo(this._roomNum, MX2, MY2);
		}
		if((xInd == MX2) && (yInd == MY2)){
			this.customTeleportTo(this._roomNum, MX1, MY1);
		}
		
		if((xInd == NX1) && (yInd == NY1)){
			this.customTeleportTo(this._roomNum, NX2, NY2);
		}
		if((xInd == NX2) && (yInd == NY2)){
			this.customTeleportTo(this._roomNum, NX1, NY1);
		}
		
		if((xInd == OX1) && (yInd == OY1)){
			this.customTeleportTo(this._roomNum, OX2, OY2);
		}
		if((xInd == OX2) && (yInd == OY2)){
			this.customTeleportTo(this._roomNum, OX1, OY1);
		}
		
		if((xInd == HOME_X) && (yInd == HOME_Y)){
			this.customTeleportTo(this._roomNum, 19, 34);
		}
		
	}	
	

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			if(this._interruptedTrainer==MINH && this.getMovingTrainers().get(MINH).isDefeated() && this.getMovingTrainers().get(MINH).getGift()!=null && (_NPCpage==2)){
				M2.playFX(M2.BADGE);
			}
			this.repaint();
		}

		super.A_Button();
	}
}