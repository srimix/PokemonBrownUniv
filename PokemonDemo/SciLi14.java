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
public class SciLi14 extends PokePanel2 {
	
	public SciLi14(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(4);
		
		this.createBaseRoom();
	
	}

	public SciLi14(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
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
		if(this.getMovingTrainers().get(3).getGift() == null){
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
				this.getMovingTrainers().get(3).setGift(null);
			}
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
			
				Vector<Pokemon> m1Belt = new Vector<Pokemon>();
				m1Belt.add(new Pokemon.Zubat().setWildLevel(25));
				m1Belt.add(new Pokemon.Zubat().setWildLevel(25));
				m1Belt.add(new Pokemon.Golbat().setWildLevel(25));
				Trainer maf1 = new Trainer.MafiaMagenta(m1Belt);
				maf1.createHome(10, 11, 0, 0, "freeze", "freeze");
				maf1.setLOS(3);
				maf1.setDirectionAndImage(FACEEAST);
				maf1.setMoney(830);
				maf1.setType("Mafia Thug");
				maf1.setName("Dan");
				maf1.getDialogue().add("Don't get in our way!");
				maf1.setDefeatDialogue("Grrr, I'll tell my boss about this...");
				_movingTrainers.add(maf1);
				
				Vector<Pokemon> m2Belt = new Vector<Pokemon>();
				m2Belt.add(new Pokemon.Koffing().setWildLevel(26));
				m2Belt.add(new Pokemon.Hypno().setWildLevel(26));
				Trainer maf2 = new Trainer.MafiaMagenta(m2Belt);
				maf2.createHome(9, 9, 0, 0, "freeze","freeze");
				maf2.setMoney(830);
				maf2.setLOS(3);
				maf2.setType("Mafia Thug");
				maf2.setName("Charlie");
				maf2.getDialogue().add("Stop interfering in our evil plans!");
				maf2.setDefeatDialogue("I only assume that our plans our evil. I don't actually know.");
				maf2.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(maf2);
				
				
				Vector<Pokemon> m3Belt = new Vector<Pokemon>();
				m3Belt.add(new Pokemon.Zubat().setWildLevel(23));
				m3Belt.add(new Pokemon.Rattata().setWildLevel(23));
				m3Belt.add(new Pokemon.Raticate().setWildLevel(23));
				m3Belt.add(new Pokemon.Zubat().setWildLevel(23));
				Trainer maf3 = new Trainer.MafiaWhite(m3Belt);
				maf3.createHome(10, 7, 0, 0, "freeze", "freeze");
				maf3.setMoney(840);
				maf3.setType("Mafia Thug");
				maf3.setName("Michael");
				maf3.getDialogue().add("There's a ton of cool stuff to steal in this library.");
				maf3.getDialogue().add("I even found some kid's Raticate wandering around on its own.");
				maf3.setDefeatDialogue("Whatever, I'm still keeping the Raticate.");
				maf3.setLOS(3);
				maf3.setDirectionAndImage(FACEEAST);
				_movingTrainers.add(maf3);
				
				Trainer media = new Trainer.Female1(null);
				media.createHome(10, 2);
				media.getDialogue().add("Thank you so much for saving me!");
				media.getDialogue().add("In exchange for your heroics, take this...");
				media.setGift(new Item.PokeFlute());
				media.getPostItemDialogue().add("It's a special flute that can awaken sleeping Pokemon.");
				media.getPostItemDialogue().add("Just make sure to have it returned by Sunday at 4pm.");
				_movingTrainers.add(media);
				
				
				Trainer text = new Trainer.Text();
				text.createHome(9, 4);
				text.getDialogue().add("Welcome to Media Services!");
				_movingTrainers.add(text);
				
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
		this.description = "SciLi 14th Floor";
		this._roomNum = _gbs.SCILI_14;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Scili/scili14.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(20,20);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SciLi14.cmap"));
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

		//TO 13th FLOOR
		if(xInd == 18 && yInd == 11){
			super.enterRoom(_gbs.SCILI_13, 18,11,FACESOUTH);
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