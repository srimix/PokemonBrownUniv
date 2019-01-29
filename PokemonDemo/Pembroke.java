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
public class Pembroke extends PokePanel2 {
	private BufferedImage _roomO, _roomC;
	private final int ROOF_ITEM=2, ALLEY_ITEM=3, TRUCK_BALL=7;
	
	public Pembroke(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Pembroke(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
		this._movingTrainers=new Vector<Trainer>();
		try{
		Trainer alumBlock=new Trainer.Text();
		alumBlock.createHome(16, 7, 0, 0, "", "");
		alumBlock.getDialogue().clear();
		alumBlock.getDialogue().add("\"Alumnae Hall\"");
		alumBlock.getDialogue().add("It seems to be locked from this side.");
		
		this._movingTrainers.add(alumBlock); //0
		
		Trainer sign = new Trainer.Text();
		sign.createHome(0, 11, 0,0,"","");
		sign.getDialogue().clear();
		sign.getDialogue().add("Brown St. and Meeting St.");
		this._movingTrainers.add(sign); //1
	

		Trainer roofItem= new Trainer.ItemObject(new Item.Potion());
		roofItem.createHome(15, 0, 2, 6, "", "");
		this._movingTrainers.add(roofItem); //2, final int.
		
		Trainer alleyItem= new Trainer.ItemObject(new Item.XSpecial());
		alleyItem.createHome(19, 4, 1, 6, "", "");
		this._movingTrainers.add(alleyItem); //3, final int.
		
		
		Trainer cop = new Trainer.Cop(null);
		cop.createHome(24,8,0,0,"false","false");
		cop.setDirectionAndImage(FACENORTH);
		cop.getDialogue().add("I'm sorry, you can't enter the VDub right now.");
		cop.getDialogue().add("There's been a fire in the dining hall.");
		this._movingTrainers.add(cop); //4
	
		if(_gbs.getPlayer().isGymLeaderDefeated(5)){
			cop.setVanishing(true);
			cop.defeat();
		}
		
		
		Trainer via = new Trainer.MaroonHat(null);
		via.createHome(31, 9, 0,0,"wander","wander");
		via.addDest(29,7);
		via.addDest(31,9);
		via.setFirstDest();
		via.setStationary(false);
		via.getDialogue().add("There's something a little off about that ViaVia place.");
		via.getDialogue().add("I've tried probing but they aren't revealing anything.");

		this._movingTrainers.add(via); //5
		

		
		
		Trainer jww = new Trainer.NiceHair(null);
		jww.createHome(2, 10, 0,0,"wander","wander");
		jww.addDest(17,10);
		jww.addDest(2,10);
		jww.setFirstDest();
		jww.setStationary(false);
		jww.getDialogue().add("I just showered in the JWW bathroom.");
		jww.getDialogue().add("...no, it's NOT weird.");
		
		this._movingTrainers.add(jww); //6
		
		Trainer truck = new Trainer.NiceHair(null);
		truck.createHome(35, 3, 0,0,"wander","wander");
		truck.addDest(34,4);
		truck.addDest(35,3);
		truck.setFirstDest();
		truck.setStationary(false);
		truck.getDialogue().add("I found this around these trucks, but I don't really want it.");
		truck.getDialogue().add("Here, you take it!");
		truck.setGift(new Item.UltraBall());
		truck.getPostItemDialogue().add("I'm gonna keep scouring this place for goodies.");
		
		this._movingTrainers.add(truck); //7  final int
		
		}
		catch(IOException ioe){
			
		}
		
		
	}

	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			if(this.getCheckList().get(0)==1){
				this._movingTrainers.get(ROOF_ITEM).defeat();
			}
			if(this.getCheckList().get(1)==1){
				this._movingTrainers.get(ALLEY_ITEM).defeat();
			}
			if(this.getCheckList().get(2)==1){
				this._movingTrainers.get(TRUCK_BALL).setGift(null);
				this._movingTrainers.get(TRUCK_BALL).getDialogue().clear();
				this._movingTrainers.get(TRUCK_BALL).getDialogue().add(this._movingTrainers.get(TRUCK_BALL).getPostItemDialogue().get(0));
			}
		}
	}

	public void scanForAllEvents(){
		if(this._movingTrainers.get(ROOF_ITEM).isDefeated()){
			this.getCheckList().set(0,1);
		}
		if(this._movingTrainers.get(ALLEY_ITEM).isDefeated()){
			this.getCheckList().set(1,1);
		}
		if(this._movingTrainers.get(TRUCK_BALL).getGift() == null){
			this.getCheckList().set(2,1);
		}
	}
	
	public void createBaseRoom(){
		
		this.song = M2.ROUTE_2;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.addWilds();
		
		this.xConstant=1196;
		this.yConstant=5;
		this._mapX=135;
		this._mapY=235;
		this._flyX=24;
		this._flyY=9;
		this._outdoors=true;
		this.setBikeAllow(true);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Pembroke";
		this._roomNum = _gbs.PEMBROKE;
		if(_gbs.BACKGROUND==null){
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

		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(40,13);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Pembroke.cmap"));
		for(int i = 0; i < 13; i++){
			for(int i2 = 0; i2 < 40; i2++){
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
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawOptimalImage(g2, _roomO);
		
		if(!( (_xIndex>=15 && _xIndex<=17) && (_yIndex>=0 && _yIndex<=3) ) ){
			g2.drawImage(_roomC, null, this._xSpace+1400, this._ySpace+3);
		}

		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
 		//VDub
 		if((xInd == 24) && (yInd == 7)){
 			super.enterRoom(_gbs.VDUB, 10, 27, FACENORTH);
 		}
 		//Spiritus
 		if((xInd == 32) && (yInd == 8)){
 			super.enterRoom(_gbs.SPIRITUS, 6, 8, FACENORTH);
 		}
 		
 		//Alumnae From Roof
 		if((xInd == 16) && (yInd == 2)){
 			super.enterRoom(_gbs.ALUMNAE_HALL, 10, 0, FACESOUTH);
 		}
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		if(yInd == 12 && xInd>=5 && SOUTH){
			super.enterRoomSeamless(_gbs.ROUTE_3, xInd-13+12-3, 1, FACESOUTH);
		}
		if(yInd == 12 && xInd<5 && SOUTH){
			super.enterRoomSeamless(_gbs.ROUTE_2, xInd-3+49, 3, FACESOUTH);
		}
		if(xInd == 0 && WEST){
			super.enterRoomSeamless(_gbs.ROUTE_2, 46, yInd-10+1, FACEWEST);
		}
		if(xInd == 37 && yInd==11 && EAST){
			super.enterRoomSeamless(_gbs.THAYER_NORTH, 12, 12, FACEEAST);
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