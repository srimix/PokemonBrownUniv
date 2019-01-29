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
public class Route1 extends PokePanel2 {
	private BufferedImage _roomO, roomSW;
	private Trainer organic;
	
	public Route1(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Route1(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
		try{
			this._movingTrainers = new Vector<Trainer>();
			
			if(!_gbs.getPlayer().isGymLeaderDefeated(1)){
				Trainer tourguide = new Trainer.GlassesGuy1(null);
				tourguide.createHome(12, 2, 0, -1, "", "");
				tourguide.setStationary(true);
				tourguide.setDirectionAndImage(FACENORTH);
				
				Trainer text = new Trainer.Text();
				text.createHome(11, 2, 0, 0, "", "");
				text.setStationary(true);
				
				Trainer parent1 = new Trainer.Female1(null);
				parent1.createHome(11, 3, -1, 0, "freeze", "freeze");
				parent1.addDest(11, 2);
				parent1.setFirstDest();
				parent1.setStationary(false);
				parent1.getDialogue().add("Brown seems like such an interesting school!");
				parent1.setDirectionAndImage(FACESOUTH);
				//parent1.setStationary(true);
				
				Trainer parent2 = new Trainer.BlueBro(null);
				parent2.createHome(12, 3, -1, 0, "freeze", "freeze");
				parent2.addDest(12, 2);
				parent2.setStationary(false);
				parent2.setFirstDest();
				parent2.getDialogue().add("I want to be an Engineer!");
				parent2.setDirectionAndImage(FACESOUTH);
				//parent2.setStationary(true);
				
				Trainer parent3 = new Trainer.BlueGirl(null);
				parent3.createHome(13, 3, -1, 0, "freeze", "freeze");
				parent3.addDest(12, 3);
				parent3.setStationary(false);
				parent3.setFirstDest();
				parent3.getDialogue().add("Ssshhh! I can't hear what the tour guide is saying!");
				parent3.setDirectionAndImage(FACESOUTH);
				//parent3.setStationary(true);
				
				_movingTrainers.add(tourguide);
				_movingTrainers.add(text);
				_movingTrainers.add(parent1);
				_movingTrainers.add(parent2);
				_movingTrainers.add(parent3);
			}
			else{
				Trainer late = new Trainer.RedAndYellow(null);
				late.createHome(10, 5, -1, 0, "wander", "wander");
				late.addDest(11, 5);
				late.addDest(12, 6);
				late.addDest(11, 8);
				late.addDest(10, 9);
				late.setFirstDest();
				late.setPause(true);
				late.setStationary(false);
				late.getDialogue().clear();
				late.getDialogue().add("...you defeated Gail? She's swiping us into the Ratty again?");
				late.getDialogue().add("Ah whatever, I skipped my class anyways.");	

				_movingTrainers.add(late);
			}
			
			organic = new Trainer.BlackGirl(null);
			organic.createHome(4, 28, -1, 0, "wander", "wander");
			organic.addDest(4, 31);
			organic.addDest(3, 31);
			organic.addDest(3, 28);
			organic.addDest(4, 28);
			organic.setFirstDest();
			organic.setPause(true);
			organic.setStationary(false);
			
			organic.getDialogue().clear();
			if(this.getCheckList().get(0)==0){
				organic.setGift(new Item.OrganicPotion());
				organic.getDialogue().add("It's Wednesday! The farmer's market is open on Wriston Quad.");
				organic.getDialogue().add("You can get PokeBalls, as well as potions and status heals.");
				organic.getDialogue().add("I hear they're a bit expensive though...");
				organic.getDialogue().add("Here, take one of mine! It'll do for now.");
				organic.getPostItemDialogue().add("Have you heard of the Ivy Room? It's the nearest PokeMart.");
			}
			else{
				organic.getDialogue().add("Have you heard of the Ivy Room? It's the nearest PokeMart.");	
			}

			_movingTrainers.add(organic);
			
			Trainer surfguy = new Trainer.Pirate(null);
			surfguy.createHome(15, 36, -1, 0, "avoid", "avoid");
			surfguy.setPause(true);
			surfguy.setStationary(true);
			surfguy.getDialogue().clear();
			surfguy.getDialogue().add("My goal is to reach the distant Perkins Hall!");
			surfguy.getDialogue().add("But first, I gotta teach my Seadra how to Surf...");
			
			
			Trainer seadra = new Trainer.Seadra(this);
			seadra.createHome(15, 37, -2, 0, "drunk", "freeze");
			seadra.clearDestinations();
			seadra.addDest(16, 37);
			seadra.addDest(15, 37);
			seadra.setFirstDest();
			seadra.setPause(true);
			seadra.setStationary(false);
			seadra.getDialogue().clear();
			seadra.getDialogue().add("Seadra: Krooooo!");
			seadra.getDialogue().add("Seadra is pacing in mild frustration.");
			
			_movingTrainers.add(surfguy);
			_movingTrainers.add(seadra);

			//Remove this later.
			Trainer cut = new Trainer.CutBush(_gbs.getPlayer(), _gbs.ROUTE_1, this);
			cut.createHome(10, 33, 0, 2, "", "");
			_movingTrainers.add(cut);
			
			Trainer ledge = new Trainer.YellowHatBoy(null);
			ledge.createHome(9, 28, -1, 0, "wander", "wander");
			ledge.addDest(11, 28);
			ledge.addDest(10, 29);
			ledge.addDest(9, 28);
			ledge.setFirstDest();
			ledge.setPause(true);
			ledge.setStationary(false);
			ledge.getDialogue().clear();
			ledge.getDialogue().add("See those thick ledges in your way?");
			ledge.getDialogue().add("You can jump down them in one direction, but not the other.");
			
			this._movingTrainers.add(ledge);
			
			Trainer closedText = new Trainer.Text();
			closedText.createHome(4, 3);
			
			
			if(MainGreen.springWeekend){
				//6,3 to 10,3
				//2,3 secret tickets
				Trainer secret = new Trainer.Backpacker(null);
				secret.createHome(2,3);
				Trainer mart = new Trainer.MartMenu("BCA", this);
				mart.createHome(2, 3);
				_movingTrainers.add(mart);
				_movingTrainers.add(secret);
				this._thisMartContains.clear();
				this._thisMartContains.add(Item.SPRING_WEEKEND_TICKET);
				
				Trainer lols = new Trainer.RedBandanaGirl(null);
				lols.createHome(1, 4);
				lols.setDirectionAndImage(FACEWEST);
				lols.getDialogue().add("So people don't realize that there's two lines for tickets.");
				lols.getDialogue().add("Lols.");
				_movingTrainers.add(lols);
				
				Trainer bca = new Trainer.Backpacker(null);
				bca.createHome(6, 3);
				bca.setDirectionAndImage(FACEWEST);
				Trainer bcaText = new Trainer.Text();
				bcaText.createHome(6, 3);
				bcaText.getDialogue().add("Now everybody just calm down...");
				bcaText.getDialogue().add("There's only one ticket left, but don't freak out.");
				bcaText.getDialogue().add("At least (and at most) ONE of you is going to get a ticket.");
				_movingTrainers.add(bcaText);
				_movingTrainers.add(bca);
				
				Trainer outrage1 = new Trainer.BlackHairBlueDress(null);
				outrage1.createHome(7, 3,0,0,"freeze,","freeze");
				outrage1.getDialogue().add("I'VE BEEN HERE FOR FIVE HOURS. JUST GIVE ME THE TICKET.");
				outrage1.setDirectionAndImage(FACEEAST);
				outrage1.addDest(6, 3);
				outrage1.setFirstDest();
				outrage1.setStationary(false);
				_movingTrainers.add(outrage1);
				
				
				Trainer outrage2 = new Trainer.BlondeDude(null);
				outrage2.createHome(8, 3,0,0,"freeze,","freeze");
				outrage2.getDialogue().add("Come on man, I skipped lab just to wait in line.");
				outrage2.setDirectionAndImage(FACEEAST);
				outrage2.addDest(7, 3);
				outrage2.setFirstDest();
				outrage2.setStationary(false);
				_movingTrainers.add(outrage2);
				
				
				Trainer outrage3 = new Trainer.BlueBro(null);
				outrage3.createHome(9, 3,0,0,"freeze,","freeze");
				outrage3.getDialogue().add("First they said the website would work this time.");
				outrage3.getDialogue().add("Then they said if you got here early, you could def get tix.");
				outrage3.getDialogue().add("WHY THIS KOLAVERI DI?!?");
				outrage3.setDirectionAndImage(FACEEAST);
				outrage3.addDest(8, 3);
				outrage3.setFirstDest();
				outrage3.setStationary(false);
				_movingTrainers.add(outrage3);
				
				
				Trainer outrage4 = new Trainer.Meiklejohn(null);
				outrage4.createHome(10, 3,-2,-2,"freeze,","freeze");
				outrage4.getDialogue().add("FREAKING BCA!");
				outrage4.getDialogue().add("Oh...hi there " + _gbs.getPlayer().getPlayerName() + ". Didn't see you there.");
				outrage4.getDialogue().add("Even RAs need to relax.");
				outrage4.getDialogue().add("This is rather infuriating though. I've been here for 2 hours already.");
				outrage4.getDialogue().add("I heard there might be another line on the other side of the shed.");
				outrage4.getDialogue().add("...but I didn't want to lose my place in this one.");
				outrage4.getDialogue().add("You should go check it out.");
				outrage4.setDirectionAndImage(FACEEAST);
				outrage4.addDest(9, 3);
				outrage4.setFirstDest();
				outrage4.setStationary(false);
				_movingTrainers.add(outrage4);
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
	}

	public void createBaseRoom(){

		
		this.song = M2.ROUTE_1;
		
		this.addTrainers();
		this.loadAllEvents();
		this.addWaterWilds();
		
		this.xConstant=40;
		this.yConstant=154;
		this._mapX=134;
		this._mapY=331;
		this._outdoors=true;
		
		this.setMartVisible(false);
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(true);
		this._wildPokemon = new Vector<Pokemon>();
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		for(int i = 2; i <= 5; i++)
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));

		for(int i = 2; i <= 5; i++)
			this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(i));
		
		for(int i = 4; i <= 5; i++)
			this._wildPokemon.add(new Pokemon.Caterpie().setWildLevel(i));
		
		for(int i = 4; i <= 5; i++)
			this._wildPokemon.add(new Pokemon.Weedle().setWildLevel(i));
		
		this.setBackground(Color.BLACK);
		this.description = "Brown St. - South";
		this._roomNum = 5;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route1/Route1BackgroundRedo.png"));
			//	_roomSource = _gbs.bgArray[_gbs.ROUTE_1];
			
			_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route1/Route1Over.png"));
			roomSW = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route1/Route1SW.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(28,46);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Route1.cmap"));
		for(int i = 0; i < 46; i++){
			for(int i2 = 0; i2 < 28; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		if(MainGreen.springWeekend){
			this._room._roomGrid[3][3] = 'N';
			this._room._roomGrid[4][3] = 'N';
			this._room._roomGrid[5][3] = 'N';
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			if(this.getCheckList().get(0)==1){
				organic.setGift(null);
				organic.getDialogue().clear();
				organic.getDialogue().add("Have you heard of the Ivy Room? It's the nearest PokeMart.");
			}
		}
	}

	public void scanForAllEvents(){
		if(organic.getGift() == null){
			this.getCheckList().set(0,1);
		}
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawPlayer(g2);

	
		this.drawOptimalImage(g2, _roomO);
		if(MainGreen.springWeekend)
			g2.drawImage(roomSW, null, this._xSpace+20, this._ySpace); 
		
		this.drawBox(g2);

		this.drawAllGenerics(g2);
	}

	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		if((xInd == 11 || xInd == 12) && yInd == 1 && NORTH){
			if(xInd == 11){
				super.enterRoomSeamless(_gbs.MAIN_GREEN, 27, 45, FACENORTH);
			}
			if(xInd == 12){
				super.enterRoomSeamless(_gbs.MAIN_GREEN, 28, 45, FACENORTH);
			}
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		
		//KeeneyQuad
		if((xInd == 0) && (yInd == 33)){
			super.enterRoom(_gbs.KEENEY_QUAD, 12, 9, FACEWEST);
		}
		
		//Wayland Arch
		if((xInd == 19) && (yInd == 12)){
			super.enterRoom(_gbs.WAYLAND_ARCH, 0, 2, FACEEAST);
		}
		
 		//To Thayer
 		if((xInd ==24) && ((yInd<=45) &&(yInd >=42))){
 			super.enterRoom(_gbs.THAYER_SOUTH, 24, yInd+104-42, FACEEAST);
 		}
		
	}
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally,randomEnemy,false,this._roomNum);
		_gbs.setCurrentPanel(-1);
	}
	
	public void addWaterWilds(){
		this._wildSurf = new Vector<Pokemon>();
		for(int i = 21; i <= 23; i++){
			this._wildSurf.add(new Pokemon.Seel().setWildLevel(i));
		}
	}

	public void waterWild(){
		Pokemon randomEnemy = _wildSurf.get(0);
		double random = 101*Math.random();
		if(random < 100.0){
			randomEnemy = _wildSurf.get(0 + (int)(3*Math.random()));
		}
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
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