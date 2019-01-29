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
public class SciLi9 extends PokePanel2 {
	
	public SciLi9(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(4);
		
		this.createBaseRoom();
	
	}

	public SciLi9(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(4);
		
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
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0)==1){
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
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
			
				Vector<Pokemon> n1Belt = new Vector<Pokemon>();
				n1Belt.add(new Pokemon.Gastly().setWildLevel(23));
				n1Belt.add(new Pokemon.Gastly().setWildLevel(23));
				Trainer n1 = new Trainer.NakedGuy(n1Belt);
				n1.createHome(9, 9, 0, 0, "freeze", "freeze");
				n1.setDirectionAndImage(FACESOUTH);
				n1.setLOS(9);
				n1.setGift(new Item.Donut());
				n1.setMoney(706);
				n1.getDialogue().add("As a matter of fact, I AM cold. Why do you ask?");
				n1.setDefeatDialogue("Maybe I'll microwave a donut to warm up...");
				n1.setType("Naked Donut Runner");
				n1.setName("Morgan");
				_movingTrainers.add(n1);
				
				
				Vector<Pokemon> n2Belt = new Vector<Pokemon>();
				n2Belt.add(new Pokemon.Gastly().setWildLevel(22));
				Trainer n2 = new Trainer.NakedGirl(n2Belt);
				n2.createHome(9, 13, 0, 0, "freeze", "freeze");
				n2.setDirectionAndImage(FACENORTH);
				n2.setLOS(6);
				n2.setGift(new Item.Donut());
				n2.setMoney(736);
				n2.getDialogue().add("Don't stare at my tattoo!");
				n2.setDefeatDialogue("I guess I can't really pretend to be shy.");
				n2.setType("Naked Donut Runner");
				n2.setName("Rachel");
				_movingTrainers.add(n2);
				
				Vector<Pokemon> timBelt = new Vector<Pokemon>();
				timBelt.add(new Pokemon.Haunter().setWildLevel(24));
				Trainer tim = new Trainer.NakedGuy2(timBelt);
				tim.createHome(16, 10, 0, 0, "freeze", "freeze");
				tim.setDirectionAndImage(FACENORTH);
				tim.setLOS(10);
				tim.setMoney(800);
				tim.setGift(new Item.Donut());
				tim.setType("Naked Donut Runner");
				tim.setName("Tim");
				tim.getDialogue().add("Naked Donut Runs are a part of Brown tradition!");
				tim.setDefeatDialogue("So is science trivia. Don't forget to check out science trivia!");
				_movingTrainers.add(tim);

				Trainer item = new Trainer.ItemObject(new Item.Donut());
				item.createHome(3,3);
				_movingTrainers.add(item);
				
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
		this.description = "SciLi 9th Floor";
		this._roomNum = _gbs.SCILI_9;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Scili/scili9.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(20,20);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SciLi9.cmap"));
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

		//TO 11th FLOOR
		if(xInd == 18 && yInd == 11){
			super.enterRoom(_gbs.SCILI_11, 18,11,FACESOUTH);
		}
		
		//TO 8th FLOOR
		if(xInd == 1 && yInd == 11){
			super.enterRoom(_gbs.SCILI_8, 1, 11, FACESOUTH);
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