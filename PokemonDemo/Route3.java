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
public class Route3 extends PokePanel2 {
	private BufferedImage _roomO, _roomC;
	private final int MAFIA_DIG=8, ITEM1= 9, ETHER=10, ONE=11, TWO=12, THREE=13, FOUR=14, FIVE=15, SIX=16, SEVEN=17,EIGHT=18;
	
	public Route3(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(11);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Route3(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(11);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try{
			Trainer cut1 = new Trainer.CutCan(_gbs.getPlayer(), _gbs.ROUTE_3, this);
			cut1.createHome(14, 47, 0, 2, "", "");
			
			Trainer cut2 = new Trainer.CutCan(_gbs.getPlayer(), _gbs.ROUTE_3, this);
			cut2.createHome(13, 27, 0, 2, "", "");
			
			this._movingTrainers.add(cut1); //0
			this._movingTrainers.add(cut2); //1
			
			Trainer lockBio1 = new Trainer.Text();
			lockBio1.createHome(18, 12);
			lockBio1.getDialogue().add("The door seems to be locked from this side.");
			
			Trainer lockBio2 = new Trainer.Text();
			lockBio2.createHome(22, 12);
			lockBio2.getDialogue().add("The door seems to be locked from this side.");
			
			Trainer signSF = new Trainer.Text();
			signSF.createHome(20, 13);
			signSF.getDialogue().add("Sidney Frank Hall for Life Sciences");
			
			Trainer signBM = new Trainer.Text();
			signBM.createHome(3, 13);
			signBM.getDialogue().add("BioMed Center");
			
			Trainer signLinc = new Trainer.Text();
			signLinc.createHome(19, 51);
			signLinc.getDialogue().add("--To Lincoln Field");
			
			Trainer signCAC = new Trainer.Text();
			signCAC.createHome(23, 25);
			signCAC.getDialogue().add("Creative Arts Center");
			
			
			this._movingTrainers.add(lockBio1); //2
			this._movingTrainers.add(lockBio2); //3
			this._movingTrainers.add(signSF); //4
			this._movingTrainers.add(signBM); //5
			this._movingTrainers.add(signLinc); //6
			this._movingTrainers.add(signCAC); //7
			

			/////IF YOU ADD TRAINERS THAT YOU FIGHT, ADD THEM HERE, before the tour group.
			
			//Mafia guy that gives you dig.
			Vector<Pokemon> mafiaBelt = new Vector<Pokemon>();
			mafiaBelt.add(new Pokemon.Machop().setWildLevel(17));
			mafiaBelt.add(new Pokemon.Drowzee().setWildLevel(17));
			Trainer mafia = new Trainer.MafiaMagenta(mafiaBelt);
			mafia.createHome(32, 38, 0, 0, "avoid", "avoid");
			mafia.setDirectionAndImage(FACENORTH);
			mafia.getDialogue().clear();
			mafia.getDialogue().add("I'm trying to dig a tunnel from here to the headquarters.");
			mafia.getDialogue().add("But I guess I can take a break to dispatch of you.");
			mafia.setDefeatDialogue("How irritating.");
			mafia.getPostBattleDialogue().add("Why don't you take this? Consider it a...gift.");
			mafia.getPostItemDialogue().add("One day when you join us, you can repay the favor by helping us dig.");
			mafia.setPause(true);
			mafia.setGift(new Item.TM28_Dig());
			mafia.setType("Mafia");
			mafia.setName("Thug");
			mafia.setMoney(545);
			mafia.setVanishing(false);
			mafia.setStationary(true);
			
			_movingTrainers.add(mafia); //8
			
			//items remaining to add
			Trainer tm45 = new Trainer.ItemObject(new Item.GuardSpec());
			tm45.createHome(22, 22);
			
			_movingTrainers.add(tm45); //9
			
			Trainer ether = new Trainer.ItemObject(new Item.Ether());
			ether.createHome(15, 46);
			
			_movingTrainers.add(ether); //10
		
			Vector <Pokemon> oneBelt = new Vector<Pokemon>();
			oneBelt.add(new Pokemon.Geodude().setWildLevel(13));
			oneBelt.add(new Pokemon.Geodude().setWildLevel(13));
			oneBelt.add(new Pokemon.Machop().setWildLevel(14));
			oneBelt.add(new Pokemon.Geodude().setWildLevel(14));
			Trainer one = new Trainer.RedHairBike(oneBelt);
			one.createHome(16,47,0,0,"avoid","avoid");
			one.setLOS(5);
			one.setDirectionAndImage(FACESOUTH);
			one.setType("Townie");
			one.setName("Audrey");
			one.setMoney(525);
			one.getDialogue().add("I came to Thayer in search of food, but I can't get to anywhere.");
			one.getDialogue().add("I forgot my water Pokemon. Maybe I'll beat you and take one of yours!");
			one.setDefeatDialogue("Argh, I'm just so hungry. Even Iron Wok is out of commission.");
			

			Vector <Pokemon> twoBelt = new Vector<Pokemon>();
			twoBelt.add(new Pokemon.Onix().setWildLevel(17));
			Trainer two = new Trainer.BlueGirl(twoBelt);
			two.createHome(15,41,0,0,"avoid","avoid");
			two.setLOS(5);
			two.setDirectionAndImage(FACENORTH);
			two.setMoney(595);
			two.getDialogue().add("You think you're strong? I carry my stuff uphill all day.");
			two.setDefeatDialogue("Ugh. This is worse than using Comic Sans.");
			two.setType("RISD Student");
			two.setName("Melanie");
			
			
			Vector <Pokemon> threeBelt = new Vector<Pokemon>();
			threeBelt.add(new Pokemon.Spearow().setWildLevel(15));
			threeBelt.add(new Pokemon.Rattata().setWildLevel(15));
			Trainer three = new Trainer.BlackHair(threeBelt);
			three.createHome(23,29,0,0,"avoid","avoid");
			three.setLOS(3);
			three.addDest(15,30);
			three.addDest(23,29);
			three.setFirstDest();
			three.setStationary(false);
			three.setMoney(225);
			three.getDialogue().add("Need some career advice? Ehh, I'm on a coffee break.");
			three.getDialogue().add("Perhaps I can give you a push you in the right direction.");
			three.setDefeatDialogue("You seem to have a strong sense of motivation.");
			three.setType("Advisor");
			three.setName("Ron");
			
			
			Vector <Pokemon> fourBelt = new Vector<Pokemon>();
			fourBelt.add(new Pokemon.Slowpoke().setWildLevel(17));
			Trainer four = new Trainer.RedOveralls(fourBelt);
			four.setLOS(6);
			four.setDirectionAndImage(FACEWEST);
			four.createHome(15,27,0,0,"avoid","avoid");
			four.setMoney(255);
			four.getDialogue().add("I'm taking this whole Pokemon thing S/NC.");
			four.setDefeatDialogue("Well that's embarrasing. Looks like I'll just drop it...");
			four.setType("Slacker");
			four.setName("Evan");
			

			Vector <Pokemon> fiveBelt = new Vector<Pokemon>();
			fiveBelt.add(new Pokemon.Nidoran_M().setWildLevel(15));
			fiveBelt.add(new Pokemon.Nidoran_F().setWildLevel(15));
			Trainer five = new Trainer.GreenDress(fiveBelt);
			five.createHome(24,32,0,0,"avoid","avoid");
			five.setLOS(3);
			five.setVanishing(false);
			five.addDest(20,36);
			five.addDest(24,32);
			five.setFirstDest();
			five.setStationary(false);
			five.setMoney(255);
			five.getDialogue().add("I can't decide who to save my Moon Stone for.");
			five.getDialogue().add("I'll just pick whoever evolves first after beating you!");
			five.setDefeatDialogue("I should just sell my Moon Stone and buy TM07.");
			five.setType("Undergrad");
			five.setName("Natasha");
			
			
			
			Vector <Pokemon> sixBelt = new Vector<Pokemon>();
			sixBelt.add(new Pokemon.Rattata().setWildLevel(14));
			sixBelt.add(new Pokemon.Ekans().setWildLevel(14));
			Trainer six = new Trainer.GreenHat(sixBelt);
			six.createHome(22,51,0,0,"avoid","avoid");
			six.setLOS(3);
			six.addDest(16,48);
			six.addDest(22,51);
			six.setFirstDest();
			six.setStationary(false);
			six.setMoney(280);
			six.setVanishing(false);
			six.getDialogue().add("I'm shopping 11 classes. That's OK right?");
			six.setDefeatDialogue("You know what, I'll just vagabond the rest.");
			six.setType("Sophomore");
			six.setName("Ben");
			
					
			Vector <Pokemon> sevenBelt = new Vector<Pokemon>();
			sevenBelt.add(new Pokemon.Ekans().setWildLevel(14));
			sevenBelt.add(new Pokemon.Sandshrew().setWildLevel(14));
			Trainer seven = new Trainer.Ponytail(sevenBelt);
			seven.createHome(16,35,0,0,"avoid","avoid");
			seven.setLOS(0);
			seven.setMoney(210);
			seven.getDialogue().add(".....you interrupted my Angry Birds game!");
			seven.setDefeatDialogue("Siri, teach me to cope with defeat.");
			seven.setType("iPhone Addict");
			seven.setName("Zara");
			
			Vector <Pokemon> eightBelt = new Vector<Pokemon>();
			eightBelt.add(new Pokemon.Oddish().setWildLevel(13));
			eightBelt.add(new Pokemon.Pidgey().setWildLevel(13));
			eightBelt.add(new Pokemon.Oddish().setWildLevel(13));
			Trainer eight = new Trainer.RedBandanaGirl(eightBelt);
			eight.setLOS(5);
			eight.setDirectionAndImage(FACESOUTH);
			eight.createHome(14,32,0,0,"avoid","avoid");
			eight.setMoney(195);
			eight.getDialogue().add("My organic Pokemon will end you.");
			eight.getDialogue().add("And I mean that in the kindest way possible.");
			eight.setDefeatDialogue("I guess I'll let them grow in the Greenhouse more...");
			eight.setType("UEL Staff");
			eight.setName("Karyn");
			
			_movingTrainers.add(one); //11
			_movingTrainers.add(two); //12
			_movingTrainers.add(three); //13
			_movingTrainers.add(four); //14
			_movingTrainers.add(five); //15
			_movingTrainers.add(six); //16
			_movingTrainers.add(seven); //17
			_movingTrainers.add(eight); //18



			Trainer asha = new Trainer.StrawHat(null);
			asha.createHome(12, 15, 0,0,"wander","wander");
			asha.addDest(15,4);
			asha.addDest(12,15);
			asha.setFirstDest();
			asha.setStationary(false);
			asha.getDialogue().add("Want a real challenge? Head to Ashamu on Lincoln Field.");
			asha.getDialogue().add("Your worst enemy is no one but yourself.");
			_movingTrainers.add(asha);

			
			Trainer jwwSign = new Trainer.Text();
			jwwSign.createHome(5, 47);
			jwwSign.getDialogue().add("J. Walter Wilson");
			this._movingTrainers.add(jwwSign);
			
			if(_gbs.getPlayer().getAllItems().get(Item.FAKE_ID).getRemaining() == 0){

					
					Trainer mob1 = new Trainer.NiceHair(null);
					mob1.createHome(15,20);
					mob1.setDirectionAndImage(FACENORTH);
					mob1.getDialogue().add("What's the food like at Brown? ");
					//Has Dialogue.
					
					Trainer mob2 = new Trainer.StrawHat(null);
					mob2.createHome(15,21);
					mob2.setDirectionAndImage(FACENORTH);
					
					Trainer mob3 = new Trainer.BlueBro(null);
					mob3.createHome(16,20);
					mob3.setDirectionAndImage(FACENORTH);
					mob3.getDialogue().add("Our tour guide, Sachi, is so good! She knows so much about Brown.");
					//Has Dialogue.
					
					Trainer mob4 = new Trainer.GlassesGuy1(null);
					mob4.createHome(17,21);
					mob4.setDirectionAndImage(FACENORTH);
					mob4.getDialogue().add("Sshh, I'm trying to listen to the tour guide!");
					//Has Dialogue.
					
					Trainer mob5 = new Trainer.GraySkirt(null);
					mob5.createHome(18,20);
					mob5.setDirectionAndImage(FACENORTH);
					mob5.getDialogue().add("Sarah: Sssh, I'm really a current student.");
					mob5.getDialogue().add("I just want the tour guide to talk about dance teams at Brown.");
					mob5.getDialogue().add("'Attitude' wussup!!!");
					//Has Dialogue.
					
					Trainer mob6 = new Trainer.RedOveralls(null);
					mob6.createHome(18,21);
					mob6.setDirectionAndImage(FACENORTH);
					
					Trainer mob7 = new Trainer.GlassesProfessor(null);
					mob7.createHome(19,20);
					mob7.setDirectionAndImage(FACENORTH);
					
					Trainer mob8 = new Trainer.BlondeDude(null);
					mob8.createHome(20,20);
					mob8.setDirectionAndImage(FACENORTH);
					mob8.getDialogue().add("Where's the parking lot? I need to add more money to the meter.");

					
					Trainer mob9 = new Trainer.RedstripeShirt(null);
					mob9.createHome(20,22);
					mob9.setDirectionAndImage(FACEEAST);
					
					Trainer mob10 = new Trainer.RedAndYellow(null);
					mob10.createHome(15,23);
					mob10.setDirectionAndImage(FACEWEST);
					
					Trainer mob11 = new Trainer.BrownGuy(null);
					mob11.createHome(19,19);
					mob11.setDirectionAndImage(FACENORTH);
					mob11.getDialogue().add("...wait what did he say? Bad-what?");
					//Has Dialogue.
					
					
					this._movingTrainers.add(mob1);
					this._movingTrainers.add(mob2);
					this._movingTrainers.add(mob3);
					this._movingTrainers.add(mob4);
					this._movingTrainers.add(mob5);
					this._movingTrainers.add(mob6);
					this._movingTrainers.add(mob7);
					this._movingTrainers.add(mob8);
					this._movingTrainers.add(mob9);
					this._movingTrainers.add(mob10);
					this._movingTrainers.add(mob11);
					
					Trainer sachi = new Trainer.BlackGirl(null);
					sachi.createHome(17, 23);
					sachi.setDirectionAndImage(FACESOUTH);
					this._movingTrainers.add(sachi);
				
			}

			//CAC Swiper
			Trainer swipe = new Trainer.Swiper(_gbs.getPlayer(), this, 8, -1);
			swipe.createHome(25,24,1000,1000,"","");
			_movingTrainers.add(swipe);
			
			
			//ViaVia block
			if(_gbs.getPlayer().isGymLeaderDefeated(8)){
				Trainer vvb = new Trainer.Text();
				vvb.createHome(28,8);
				vvb.getDialogue().add("These premises are off-limits by court order.");
				vvb.getDialogue().add("A criminal investigation is in place.");
				this.getMovingTrainers().add(vvb);
			}
			
			}
			catch(IOException ioe){}
			
			
	}

	public void createBaseRoom(){
		this.song = M2.ROUTE_2;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=1276;
		this.yConstant=225;
		this._mapX=172;
		this._mapY=270;
		this._flyX=5;
		this._flyY=13;
		this._outdoors=true;
		
		this.addWilds();
		
		this.setBattleBG(NewBattleScreen.PATCHY_GRASS);
		this.setBikeAllow(true);
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		this.setBackground(Color.BLACK);
		this.description = "Brown St.-NorEast";
		this._roomNum = _gbs.ROUTE_3;
		if(GameBoyScreen.finishedLoading){
			try{
				if(GameBoyScreen.finishedLoading){
					_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/Route2O.png"));
					_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/Route2.png"));
					_roomC = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/Route2 Chimney.png"));
				}
			} 
			catch(IOException ioe){
				ioe.printStackTrace();
				System.exit(0);
			}
		}
		
		if(_room == null)
			this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(35,54);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Route3.cmap"));
		for(int i = 0; i < 54; i++){
			for(int i2 = 0; i2 < 35; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		this._wildPokemon.add(new Pokemon.Weedle().setWildLevel(7));
		this._wildPokemon.add(new Pokemon.Caterpie().setWildLevel(7));
		for(int i = 12; i <= 14; i++){
			this._wildPokemon.add(new Pokemon.Oddish().setWildLevel(i));
		}
		for(int i = 12; i <= 14; i++){
			this._wildPokemon.add(new Pokemon.Bellsprout().setWildLevel(i));
		}
		for(int i = 12; i <= 13; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
		}
		for(int i = 13; i <= 17; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Kakuna().setWildLevel(8));
		this._wildPokemon.add(new Pokemon.Metapod().setWildLevel(8));
		for(int i = 8; i <= 12; i++){
			this._wildPokemon.add(new Pokemon.Abra().setWildLevel(i));
		}
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Venonat().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Pidgeotto().setWildLevel(17));
	}

	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		if(random < 8.3){
			randomEnemy = _wildPokemon.get(01);
		}
		else if(random < 16.6){
			randomEnemy = _wildPokemon.get(11);
		}
		else if(random < 56.6){
			randomEnemy = _wildPokemon.get(2 + (int)(3*Math.random()));
		}
		else if(random < 73.2){
			randomEnemy = _wildPokemon.get(5 + (int)(3*Math.random()));
		}
		else if(random < 86.5){
			randomEnemy = _wildPokemon.get(8 + (int)(2*Math.random()));
		}
		else if(random < 96.5){
			randomEnemy = _wildPokemon.get(10 + (int)(5*Math.random()));
		}
		else if(random < 101.5){
			randomEnemy = _wildPokemon.get(151);
		}
		else if(random < 106.5){
			randomEnemy = _wildPokemon.get(161);
		}
		else if(random < 116.5){
			randomEnemy = _wildPokemon.get(17 + (int)(5*Math.random()));
		}
		else if(random < 119.5){
			randomEnemy = _wildPokemon.get(22 + (int)(4*Math.random()));
		}
		else if(random < 120.5){
			randomEnemy = _wildPokemon.get(261);
		}
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(MAFIA_DIG).defeatAndPostItemize();
			}
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(ITEM1).defeatAndPostBattle();
			}
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(ETHER).defeatAndPostBattle();
			}
			if(this.getCheckList().get(3)==1){
				this.getMovingTrainers().get(ONE).defeatAndPostBattle();
			}
			if(this.getCheckList().get(4)==1){
				this.getMovingTrainers().get(TWO).defeatAndPostBattle();
			}
			if(this.getCheckList().get(5)==1){
				this.getMovingTrainers().get(THREE).defeatAndPostBattle();
			}
			if(this.getCheckList().get(6)==1){
				this.getMovingTrainers().get(FOUR).defeatAndPostBattle();
			}
			if(this.getCheckList().get(7)==1){
				this.getMovingTrainers().get(FIVE).defeatAndPostBattle();
			}
			if(this.getCheckList().get(8)==1){
				this.getMovingTrainers().get(SIX).defeatAndPostBattle();
			}
			if(this.getCheckList().get(9)==1){
				this.getMovingTrainers().get(SEVEN).defeatAndPostBattle();
			}
			if(this.getCheckList().get(10)==1){
				this.getMovingTrainers().get(EIGHT).defeatAndPostBattle();
			}
			
		}
	}

	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(MAFIA_DIG).isDefeated()){
			this.getCheckList().set(0,1);	
		}
		if(this.getMovingTrainers().get(ITEM1).isDefeated()){
			this.getCheckList().set(1,1);	
		}
		if(this.getMovingTrainers().get(ETHER).isDefeated()){
			this.getCheckList().set(2,1);	
		}
		if(this.getMovingTrainers().get(ONE).isDefeated()){
			this.getCheckList().set(3,1);	
		}
		if(this.getMovingTrainers().get(TWO).isDefeated()){
			this.getCheckList().set(4,1);	
		}
		if(this.getMovingTrainers().get(THREE).isDefeated()){
			this.getCheckList().set(5,1);	
		}
		if(this.getMovingTrainers().get(FOUR).isDefeated()){
			this.getCheckList().set(6,1);	
		}
		if(this.getMovingTrainers().get(FIVE).isDefeated()){
			this.getCheckList().set(7,1);	
		}
		if(this.getMovingTrainers().get(SIX).isDefeated()){
			this.getCheckList().set(8,1);	
		}
		if(this.getMovingTrainers().get(SEVEN).isDefeated()){
			this.getCheckList().set(9,1);	
		}
		if(this.getMovingTrainers().get(EIGHT).isDefeated()){
			this.getCheckList().set(10,1);	
		}
		
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawOptimalImage(g2, _roomO);
		g2.drawImage(_roomC, null, this._xSpace+1400, this._ySpace+3);
		
		this.drawAllGenerics(g2);
	}

	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		if(xInd == 1 && WEST){
			super.enterRoomSeamless(_gbs.ROUTE_2, 51, yInd+2, FACEWEST);
		}
		
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		if(yInd == 0 && NORTH){
			super.enterRoomSeamless(_gbs.PEMBROKE, xInd-12+13+3, 11, FACENORTH);
		}
		
		//near via
		if(xInd == 33 && EAST){
			super.enterRoomSeamless(_gbs.THAYER_NORTH, xInd-21, yInd+12, FACEEAST);
		}
		//above bookstore
		if(xInd == 29 && yInd<20 && EAST){
			super.enterRoomSeamless(_gbs.THAYER_NORTH, xInd-21, yInd+12, FACEEAST);
		}
		//Below Bookstore
		if((xInd == 28 && yInd<40 &&EAST)){
			super.enterRoomSeamless(_gbs.THAYER_NORTH, xInd-21, yInd+12, FACEEAST);
		}
		//Waterman
		if((xInd == 27 && yInd>40 && EAST)){
			super.enterRoomSeamless(_gbs.THAYER_SOUTH, 0, yInd-48+6, FACEEAST);
		}
		//Hallway
		if(yInd == 34 && NORTH){
			super.enterRoomSeamless(_gbs.THAYER_NORTH, xInd-21, yInd+12, FACENORTH);
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		
		//JWalterWilson
 		if((xInd == 7) && (yInd == 46)){
 			super.enterRoom(_gbs.J_WALTER_WILSON, 25, 10, FACENORTH);
 		}
        
 		//Route 2
 		if((xInd == 5) && (yInd == 12)){
 			super.enterRoom(_gbs.BIOMED_1, 16, 10, FACENORTH);
 		}
 		
		//sidney frank
 		if((xInd == 18 || xInd ==22) && (yInd == 12)){
 			super.enterRoom(_gbs.SIDNEY_FRANK, 13, 11, FACENORTH);
 		}
 		
 		//Pokecenter Route 3
 		if((xInd == 12) && (yInd == 38)){
 			super.enterRoom(_gbs.POKECENTER_ROUTE_3, 8, 8, FACENORTH);
 		}
 		
 		//Lincoln Field
 		if((xInd <= 18 && xInd>=15 ) && (yInd == 53)){
 			super.enterRoom(_gbs.LINCOLN_FIELD, 23, 0, FACESOUTH);
 		}
 		
 		//Red House
 		if((xInd ==11 ) && (yInd == 33)){
 			super.enterRoom(_gbs.REDHOUSE, 5, 6, FACENORTH);
 		}
 		
        //RitesReason
 		if((xInd ==24) && (yInd == 36)){
 			super.enterRoom(_gbs.RITES_REASON, 3, 7, FACENORTH);
 		}
 		
        //ViaVia
 		if((xInd ==28) && (yInd == 8)){
 			super.enterRoom(_gbs.VIAVIA_LOBBY, 3, 9, FACENORTH);
 		}
        //CAC
 		if((xInd ==24) && (yInd == 36)){
 			//super.enterRoom(_gbs.CAC, 3, 7, FACENORTH);
 		}
		
	}

	
	public void Down(){
		if(!_menuVisible && !mapVisible && (_xIndex == 21 || _xIndex== 22) && (_yIndex ==23)){
			_gbs.getPlayer().faceDown();
			return;
		}
		super.Down();
	}
	
	public void Up(){
		if(!_menuVisible && !mapVisible && (_xIndex == 21 || _xIndex== 22) && (_yIndex ==22)){
			_gbs.getPlayer().faceUp();
			return;
		}
		super.Up();
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