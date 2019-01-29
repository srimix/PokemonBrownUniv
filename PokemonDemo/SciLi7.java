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
public class SciLi7 extends PokePanel2 {
	private final int RIVAL = 0;
	
	public SciLi7(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
	
	}

	public SciLi7(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Vector<Pokemon> rivalBelt = new Vector<Pokemon>();
				Pokemon r1 = new Pokemon.Pidgeotto().setWildLevel(25);
				Pokemon r5 = new Pokemon.Kadabra().setWildLevel(20);
				rivalBelt.add(r1);
				
				int rivStarter = _gbs.getRivalStarterNum();
//				if(_gbs.getRival() != null && _gbs.getRival().getBelt() != null)
//					rivStarter = _gbs.getRival().getBelt().get(0).getDexNum();
				if(rivStarter == 1){
					Pokemon r2 = new Pokemon.Gyarados().setWildLevel(23);
					Pokemon r3 = new Pokemon.Growlithe().setWildLevel(22);
					Pokemon r4 = new Pokemon.Ivysaur().setWildLevel(25);
					rivalBelt.add(r2);
					rivalBelt.add(r3);
					rivalBelt.add(r5);
					rivalBelt.add(r4);
				}
				else if(rivStarter == 4){
					Pokemon r2 = new Pokemon.Exeggcute().setWildLevel(23);
					Pokemon r3 = new Pokemon.Gyarados().setWildLevel(22);
					Pokemon r4 = new Pokemon.Charmeleon().setWildLevel(25);
					rivalBelt.add(r2);
					rivalBelt.add(r3);
					rivalBelt.add(r5);
					rivalBelt.add(r4);
				}
				else{
					Pokemon r2 = new Pokemon.Growlithe().setWildLevel(23);
					Pokemon r3 = new Pokemon.Exeggcute().setWildLevel(22);
					Pokemon r4 = new Pokemon.Wartortle().setWildLevel(25);
					rivalBelt.add(r2);
					rivalBelt.add(r3);
					rivalBelt.add(r5);
					rivalBelt.add(r4);
				}
			
				Trainer rival = new Trainer.Rival(rivalBelt);
				rival.createHome(3, 13, 0, 0, "avoid", "avoid");
				rival.getDialogue().clear();
				rival.getDialogue().add("Yo " + _gbs.getPlayer().getPlayerName() + "! Welcome to the SciLi!");
				rival.getDialogue().add("There's a lot of books and stuff in here.");
				rival.getDialogue().add("But there's also tons of caffeinated students and ghost Pokemon.");	
				rival.getDialogue().add("Not to mention, each floor is a confusing maze of bookshelves.");
				rival.getDialogue().add("I even lost one of my Pokemon somehow. He must have wandered off.");
				rival.getDialogue().add("Oh well. I can still beat you even without him...");
				rival.setDefeatDialogue("Maybe I should look for him? .........................Nah.");
				rival.setStationary(true);
				rival.setDirectionAndImage(FACESOUTH);
				rival.setLOS(3);
				rival.setMoney(1625);
				rival.setName(_gbs.getRival().getTrueName());
				_movingTrainers.add(rival);
				
				
				Trainer awake = new Trainer.ItemObject(new Item.Awakening());
				awake.createHome(9,17);
				_movingTrainers.add(awake);
				
				Trainer rope = new Trainer.ItemObject(new Item.EscapeRope());
				rope.createHome(12,2);
				_movingTrainers.add(rope);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void afterBattle(){
		super.afterBattle();
		if(_interruptedTrainer==RIVAL){
			this.scanForAllEvents();
			Trainer rival = this.getMovingTrainers().get(RIVAL);
			rival.unDefeat();
			rival.setVanishing(true);
			rival.setHomeIndices(1, 11);
			_returnTrip=true;
			this._approachTimer.start();	
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0) == 1){
				this.getMovingTrainers().get(RIVAL).setVanishing(true);
				this.getMovingTrainers().get(RIVAL).defeat();
			}
			if(this.getCheckList().get(1) == 1){
				this.getMovingTrainers().get(1).defeat();
			}
			if(this.getCheckList().get(2) == 1){
				this.getMovingTrainers().get(2).defeat();
			}
		}
	}
	
	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(RIVAL).isDefeated()){
			this.getCheckList().set(0,1);
		}
		if(this.getMovingTrainers().get(1).isDefeated()){
			this.getCheckList().set(1,1);
		}
		if(this.getMovingTrainers().get(2).isDefeated()){
			this.getCheckList().set(2,1);
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
		this.description = "SciLi 7th Floor";
		this._roomNum = _gbs.SCILI_7;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Scili/scili7.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(20,20);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SciLi7.cmap"));
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

		//TO 8th FLOOR
		if(xInd == 18 && yInd == 11){
			super.enterRoom(_gbs.SCILI_8, 18,11,FACESOUTH);
		}
		
		//TO LOBBY
		if(xInd == 1 && yInd == 11){
			super.enterRoom(_gbs.SCILI_LOBBY, 6, 0, FACESOUTH);
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