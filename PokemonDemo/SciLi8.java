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
public class SciLi8 extends PokePanel2 {
	
	public SciLi8(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(5);
		
		this.createBaseRoom();
	
	}

	public SciLi8(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(5);
		
		this.createBaseRoom();
		
	}
	
	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(0).isDefeated()){
			this.getCheckList().set(0,1);
		}
		if(this.getMovingTrainers().get(1).isDefeated()){
			this.getCheckList().set(1,1);
		}
		if(this.getMovingTrainers().get(2).isDefeated()){
			this.getCheckList().set(2,1);
		}
		if(this.getMovingTrainers().get(3).isDefeated()){
			this.getCheckList().set(3,1);
		}
		if(this.getMovingTrainers().get(4).isDefeated()){
			this.getCheckList().set(4,1);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(0).defeatAndPostItemize();
				this.getMovingTrainers().get(0).setGift(null);
			}
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(1).defeatAndPostBattle();
			}
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(2).defeatAndPostBattle();
			}
			if(this.getCheckList().get(3)==1){
				this.getMovingTrainers().get(3).defeatAndPostBattle();
			}
			if(this.getCheckList().get(4)==1){
				this.getMovingTrainers().get(4).defeatAndPostBattle();
			}
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
			
				Vector<Pokemon> caffBelt = new Vector<Pokemon>();
				caffBelt.add(new Pokemon.Gastly().setWildLevel(23));
				Trainer caffeine = new Trainer.PurpleHatGirl(caffBelt);
				caffeine.createHome(12, 11, 0, 0, "freeze", "freeze");
				caffeine.setLOS(2);
				caffeine.setMoney(690);
				caffeine.setType("All-Nighter");
				caffeine.setName("Hope");
				caffeine.setDirectionAndImage(FACEWEST);
				caffeine.getDialogue().add("Huh? What'd you say?");
				caffeine.setDefeatDialogue("ZzzzZzzzZzzz...");
				caffeine.setGift(new Item.TM42_DreamEater());
				caffeine.getPostItemDialogue().add("ZzzzZzzzZzzz...");
				_movingTrainers.add(caffeine);
				
				Vector<Pokemon> dudeBelt = new Vector<Pokemon>();
				dudeBelt.add(new Pokemon.Gastly().setWildLevel(22));
				Trainer dude = new Trainer.GlassesGuy1(dudeBelt);
				dude.createHome(7, 2, 0, 0, "freeze", "freeze");
				dude.setLOS(4);
				dude.setMoney(660);
				dude.setType("TA");
				dude.setName("Patrick");
				dude.getDialogue().add("Are you here for my section?");
				dude.setDefeatDialogue("Maybe I told them to meet at the Rock instead?");
				dude.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(dude);
		
				Vector<Pokemon> strawBelt = new Vector<Pokemon>();
				strawBelt.add(new Pokemon.Gastly().setWildLevel(24));
				Trainer straw = new Trainer.StrawHat(strawBelt);
				straw.createHome(6, 17, 0,0	, "freeze", "freeze");
				straw.setLOS(5);
				straw.setMoney(720);
				straw.setType("All-Nighter");
				straw.setName("Carl");
				straw.getDialogue().add("I tried studying upstairs, then a bunch of naked people showed up.");
				straw.setDefeatDialogue("I guess it wasn't so bad. They did give me a donut.");
				straw.setDirectionAndImage(FACESOUTH);
				_movingTrainers.add(straw);
				
				
				Trainer elix2 = new Trainer.ItemObject(new Item.Elixir());
				elix2.createHome(6,2);
				_movingTrainers.add(elix2);
				
				Trainer hp = new Trainer.ItemObject(new Item.HPUp());
				hp.createHome(9,18);
				_movingTrainers.add(hp);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void createBaseRoom(){
		
		this.song = M2.SCILI_STACKS;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=204;
		this.yConstant=200;
		
		this.addWilds();
		
		this.PROBABILITY = PokePanel2.CAVE_PROB;
		this.setBattleBG(NewBattleScreen.SCILI);
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=134;
		this._mapY=283;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		//this._pkmnCentX=;
		//this._pkmnCentY=;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "SciLi 8th Floor";
		this._roomNum = _gbs.SCILI_8;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Scili/scili8.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(20,20);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SciLi8.cmap"));
		for(int i = 0; i < 20; i++){
			for(int i2 = 0; i2 < 20; i2++){
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
	
	public void enterRoom(int xInd, int yInd){

		//TO 7th FLOOR
		if(xInd == 18 && yInd == 11){
			super.enterRoom(_gbs.SCILI_7, 18,11,FACESOUTH);
		}
		
		//TO 9th FLOOR
		if(xInd == 1 && yInd == 11){
			super.enterRoom(_gbs.SCILI_9, 1, 11, FACESOUTH);
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
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		for(int i = 23; i <= 29; i++){
			this._wildPokemon.add(new Pokemon.Gastly().setWildLevel(i));
		}
		for(int i = 22; i <= 24; i++){
			this._wildPokemon.add(new Pokemon.Cubone().setWildLevel(i));
		}
		for(int i = 28; i <= 30; i++){
			this._wildPokemon.add(new Pokemon.Haunter().setWildLevel(i));
		}
	}

	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		if(random < 80.0){
			randomEnemy = _wildPokemon.get(0 + (int)(7*Math.random()));
		}
		else if(random < 90.0){
			randomEnemy = _wildPokemon.get(7 + (int)(3*Math.random()));
		}
		else if(random < 100.0){
			randomEnemy = _wildPokemon.get(10 + (int)(3*Math.random()));
		}
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
}