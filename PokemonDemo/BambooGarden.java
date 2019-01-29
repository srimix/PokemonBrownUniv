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
public class BambooGarden extends PokePanel2 {
	private BufferedImage _roomO;
	
	public BambooGarden(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(8);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public BambooGarden(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(8);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				
				Trainer skullbash =new Trainer.ItemObject(new Item.TM40_SkullBash());
				skullbash.createHome(4, 4);
				this.getMovingTrainers().add(skullbash);
				
				Trainer doubleteam = new Trainer.ItemObject(new Item.TM32_DoubleTeam()); 
				doubleteam.createHome(3, 8);
				this.getMovingTrainers().add(doubleteam);
				
				Trainer one= new Trainer.ItemObject(new Item.Carbos());
				one.createHome(1,6);
				this.getMovingTrainers().add(one);
				
				Trainer two=new Trainer.ItemObject(new Item.MaxPotion());
				two.createHome(10,1);
				this.getMovingTrainers().add(two);
				
				Trainer three=new Trainer.ItemObject(new Item.FullRestore());
				three.createHome(9,7);
				this.getMovingTrainers().add(three);
				
				Trainer four=new Trainer.ItemObject(new Item.Protein());
				four.createHome(6,4);
				this.getMovingTrainers().add(four);
				
				Trainer five=new Trainer.ItemObject(new Item.MaxRevive());
				five.createHome(4,9);
				this.getMovingTrainers().add(five);
				
				Trainer six=new Trainer.ItemObject(new Item.Nugget());
				six.createHome(1,2);
				this.getMovingTrainers().add(six);
								
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void scanForAllEvents(){
		this.standardTrainerScan(this.getMovingTrainers().size());
	}
	public void loadAllEvents(){
		this.standardTrainerLoad(this.getMovingTrainers().size());
	}
	
	public void createBaseRoom(){
		
		this.song = M2.BAMBOO_GARDEN;
		
		this.addTrainers();
		this.loadAllEvents();
		this.addWilds();
		this.addWaterWilds();
		this.xConstant=0;
		this.yConstant=0;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX = 214;
		this._mapY = 335;
		
		//Uncomment if this place is outdoors.
		this._outdoors=true;

		this.setBikeAllow(true);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Starr Plaza";
		this._roomNum = _gbs.BAMBOO_GARDEN;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BambooGarden/BambooGarden Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/BambooGarden/BambooGarden Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(12,13);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BambooGarden.cmap"));
		for(int i = 0; i < 13; i++){
			for(int i2 = 0; i2 < 12; i2++){
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
		this.drawOptimalImage(g2, _roomO);


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Watson
 		if((xInd == 5 || xInd == 6) && (yInd == 12)){
 			super.enterRoom(_gbs.WATSON, xInd+6, 2, FACESOUTH);
 		}
		
	}
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		this._wildPokemon.add(new Pokemon.Scyther().setWildLevel(23));
		this._wildPokemon.add(new Pokemon.Pinsir().setWildLevel(23));
		for(int i = 24; i <= 25; i++){
			this._wildPokemon.add(new Pokemon.Exeggcute().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Chansey().setWildLevel(23));
		this._wildPokemon.add(new Pokemon.Rhyhorn().setWildLevel(25));
		for(int i = 28; i <= 33; i++){
			this._wildPokemon.add(new Pokemon.Kangaskhan().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Tauros().setWildLevel(28));
		this._wildPokemon.add(new Pokemon.Doduo().setWildLevel(20));
	}

	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		if(random < 10.0){
			randomEnemy = _wildPokemon.get(0);
		}
		else if(random < 17.0){
			randomEnemy = _wildPokemon.get(1);
		}
		else if(random < 37.0){
			randomEnemy = _wildPokemon.get(2 + (int)(2*Math.random()));
		}
		else if(random < 44.0){
			randomEnemy = _wildPokemon.get(4);
		}
		else if(random < 59.0){
			randomEnemy = _wildPokemon.get(5);
		}
		else if(random < 79.0){
			randomEnemy = _wildPokemon.get(6 + (int)(6*Math.random()));
		}
		else if(random < 85.0){
			randomEnemy = _wildPokemon.get(12);
		}
		else if(random < 100.0){
			randomEnemy = _wildPokemon.get(13);
		}
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void addWaterWilds(){
		this._wildSurf = new Vector<Pokemon>();
		this._wildSurf.add(new Pokemon.Psyduck().setWildLevel(15));
		this._wildSurf.add(new Pokemon.Slowpoke().setWildLevel(15));
		this._wildSurf.add(new Pokemon.Dratini().setWildLevel(15));
		for(int i = 5; i <= 10; i++){
			this._wildSurf.add(new Pokemon.Magikarp().setWildLevel(i));
		}
	}

	public void waterWild(){
		Pokemon randomEnemy = _wildSurf.get(0);
		double random = 101*Math.random();
		if(random < 25.0){
			randomEnemy = _wildSurf.get(0);
		}
		else if(random < 50.0){
			randomEnemy = _wildSurf.get(1);
		}
		else if(random < 65.0){
			randomEnemy = _wildSurf.get(2);
		}
		else if(random < 100.0){
			randomEnemy = _wildSurf.get(3 + (int)(6*Math.random()));
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