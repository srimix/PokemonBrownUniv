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
public class SciLi13 extends PokePanel2 {
	
	public SciLi13(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(5);
		
		this.createBaseRoom();
	
	}

	public SciLi13(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
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
				this.getMovingTrainers().get(0).setGift(null);
				this.getMovingTrainers().get(0).defeatAndPostBattle();
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

				Vector<Pokemon> susBelt = new Vector<Pokemon>();
				susBelt.add(new Pokemon.Gastly().setWildLevel(22));
				susBelt.add(new Pokemon.Gastly().setWildLevel(22));
				susBelt.add(new Pokemon.Gastly().setWildLevel(22));
				Trainer sus = new Trainer.NakedGirl(susBelt);
				sus.createHome(3, 18, 0, 0, "freeze", "freeze");
				sus.setDirectionAndImage(FACESOUTH);
				sus.setLOS(2);
				sus.setMoney(704);
				sus.getDialogue().add("Donuts? What are you talking about?");
				sus.getDialogue().add("I'm looking for " + _gbs.getPlayer().getRivalName()+". He just ran off.");
				sus.setDefeatDialogue("If you see him, let him know you saw me.");
				sus.setType(_gbs.getPlayer().getRivalName()+"'s Girlfriend");
				sus.setName("Megan");
				sus.getPostBattleDialogue().add("I guess I won't be needing this.");
				sus.setGift(new Item.EscapeRope());
				sus.getPostItemDialogue().add("If you see him, let him know you saw me.");
				_movingTrainers.add(sus);
				
				Vector<Pokemon> _13belt = new Vector<Pokemon>();
				_13belt.add(new Pokemon.Gastly().setWildLevel(24));
				Trainer _13top = new Trainer.Backpacker(_13belt);
				_13top.createHome(9, 4, 0, 0, "freeze", "freeze");
				_13top.getDialogue().add("Ghosts exist! They're real!!!");
				_13top.setDefeatDialogue("Of course ghosts are real. I have one...");
				_13top.setLOS(8);
				_13top.setDirectionAndImage(FACENORTH);
				_13top.setType("Conspiracy Theorist");
				_13top.setName("Craig");
				_13top.setMoney(756);
				_movingTrainers.add(_13top);
				
				
				Vector<Pokemon> scaredBelt = new Vector<Pokemon>();
				scaredBelt.add(new Pokemon.Gastly().setWildLevel(13));
				scaredBelt.add(new Pokemon.Gastly().setWildLevel(13));
				scaredBelt.add(new Pokemon.Gastly().setWildLevel(13));
				scaredBelt.add(new Pokemon.Gastly().setWildLevel(13));
				scaredBelt.add(new Pokemon.Gastly().setWildLevel(13));
				scaredBelt.add(new Pokemon.Gastly().setWildLevel(13));
				Trainer scared = new Trainer.GlassesGirl(scaredBelt);
				scared.setMoney(757);
				scared.createHome(13, 17, 0, 0, "freeze", "freeze");
				scared.getDialogue().add("Some people think 13 is actually a lucky number.");
				scared.setDefeatDialogue("Guess it isn't MY lucky number...");
				scared.setLOS(4);
				scared.setType("Supserstitious Girl");
				scared.setName("Lisa");
				scared.setVanishing(false);
				scared.setDirectionAndImage(FACESOUTH);
				_movingTrainers.add(scared);
				
				Trainer rare = new Trainer.ItemObject(new Item.RareCandy());
				rare.createHome(9,16);
				_movingTrainers.add(rare);
				
				Trainer xacc = new Trainer.ItemObject(new Item.XAccuracy());
				xacc.createHome(3, 11);
				_movingTrainers.add(xacc);
				
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
		this.description = "SciLi 13th Floor";
		this._roomNum = _gbs.SCILI_13;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Scili/scili13.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(20,20);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SciLi13.cmap"));
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

		//TO 14th FLOOR
		if(xInd == 18 && yInd == 11){
			super.enterRoom(_gbs.SCILI_14, 18,11,FACESOUTH);
		}
		
		//TO 11th FLOOR
		if(xInd == 1 && yInd == 11){
			super.enterRoom(_gbs.SCILI_11, 1, 11, FACESOUTH);
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