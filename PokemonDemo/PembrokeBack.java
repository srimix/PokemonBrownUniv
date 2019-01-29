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
public class PembrokeBack extends PokePanel2 {
	private BufferedImage  _roomO;
	
	public PembrokeBack(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public PembrokeBack(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try{
			Trainer alumSign = new Trainer.Sign();
			alumSign.createHome(6,4,-3,0,"","");
			alumSign.getDialogue().clear();
			alumSign.getDialogue().add("Alumnae Hall");
			
			this._movingTrainers.add(alumSign);
			
			Vector<Pokemon> belt = new Vector<Pokemon>();
			belt.add(new Pokemon.Clefairy().setWildLevel(22));
			belt.add(new Pokemon.Clefairy().setWildLevel(22));
			Trainer cop = new Trainer.Cop(belt);
			cop.createHome(7,1,0,0,"freeze","freeze");
			cop.setLOS(7);
			cop.setDirectionAndImage(FACENORTH);
			cop.getDialogue().add("You cannot enter SPG if you are visibly intoxicated!");
			cop.setDefeatDialogue("See you at 4/20.");
			cop.setMoney(330);
			cop.setType("Cop");
			cop.setName("Cristian");
			
			this.getMovingTrainers().add(cop);
			
			Trainer item = new Trainer.ItemObject(new Item.FullRestore());
			item.createHome(1, 2);
			
			this.getMovingTrainers().add(item);
			}
			
			catch(IOException ioe){}

	}
	
	public void scanForAllEvents(){
		this.standardTrainerScan(this.getMovingTrainers().size());
	}
	public void loadAllEvents(){
		this.standardTrainerLoad(this.getMovingTrainers().size());
	}

	public void createBaseRoom(){
		
		this.song = M2.ROUTE_2;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.addWilds();
		
		this.xConstant=77;
		this.yConstant=125;
		this._mapX=135;
		this._mapY=235;
		this._outdoors=true;

		this.setBikeAllow(true);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Pembroke Back";
		this._roomNum = 55;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/PembrokeBack/PembrokeBack Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/PembrokeBack/PembrokeBack Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(11,9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/PembrokeBack.cmap"));
		for(int i = 0; i < 9; i++){
			for(int i2 = 0; i2 < 11; i2++){
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
			this._wildPokemon.add(new Pokemon.Grimer().setWildLevel(i));
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
		//VDub
 		if((xInd == 9) && (yInd == 8)){
 			super.enterRoom(_gbs.VDUB, 19, 1, FACESOUTH);
 		}
 		//SexPowerGod
 		if((xInd == 5) && (yInd == 7)){
 			super.enterRoom(_gbs.ALUMNAE_HALL, 2, 12, FACENORTH);
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