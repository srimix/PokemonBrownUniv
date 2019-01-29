package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class OutsideBarHol extends PokePanel2{

	private BufferedImage  over, cars, bus;
	private final int LOBBY_SWIPER1 = 0, LOBBY_SWIPER2 = 1, PRINCE_SWIPER = 7, APPLE = 3, PROF=8;
	private boolean canFlash=false;
	
	public OutsideBarHol(GameBoyScreen gbs){
		super(gbs);
		this.initializeEventVector(5);
		this.createBaseRoom();
	}
	
	public OutsideBarHol(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		this.initializeEventVector(5);
		this.createBaseRoom();
	}
	
	public void checkCanFlash(){
		canFlash = false;
		for(int i = 0; i < _gbs.getPlayer().getAllActive().size(); i++){
			for(int a = 0; a < _gbs.getPlayer().getActivePokemon(i).getAttacks().size(); a++){
				if(_gbs.getPlayer().getActivePokemon(i).getAttacks().get(a).getAttackNum() == 148)
					canFlash = true;
			}
		}
	}
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		Trainer bar1 = new Trainer.Swiper(_gbs.getPlayer(), this, 3, 1);
		bar1.createHome(18, 12, 0, 1000, "", "");
		_movingTrainers.add(bar1); //0
		
		Trainer bar2 = new Trainer.Swiper(_gbs.getPlayer(), this, 3, 0);
		bar2.createHome(19, 12, 0, 1000, "", "");
		_movingTrainers.add(bar2); //1
		try{
			Trainer prince = new Trainer.NiceHair(null);
			prince.createHome(5, 7, 0, 0, "", "");
			prince.getDialogue().add("This building is Prince Labs.");
			prince.getDialogue().add("The door is locked from this side though...");
			prince.setDirectionAndImage(FACENORTH);
			_movingTrainers.add(prince); //2
			
			Trainer tree = new Trainer.Text(); //3
			tree.createHome(14, 15, 0, 0, "", "");
			tree.getDialogue().add("There's a plaque. It reads:");
			tree.getDialogue().add("'This tree is descendant from the apple tree in Isaac Newton's estate'");
			tree.getDialogue().add("That's so cool!!!");
			tree.setVanishing(false);
			tree.setGift(new Item.Apple());
			//tree.setDefeatAfterItem(true);
			tree.getPostItemDialogue().add("No more apples on the tree!");
			//tree.getPostItemDialogue().add("'This tree is descendant from the apple tree in Isaac Newton's estate'");
			//tree.getPostItemDialogue().add("That's so cool!!!");
			_movingTrainers.add(tree);
			
			Trainer lolDoor = new Trainer.BlackHair(null);
			lolDoor.createHome(12, 8, 0, 0, "","");
			lolDoor.getDialogue().add("LOL, those doors never work.");
			lolDoor.getDialogue().add("It has to be almost exactly 75 degrees outside.");
			lolDoor.getDialogue().add("Else the curvature of the doors makes it impossible to open.");
			lolDoor.getDialogue().add("Ironic that it's the engineering building right?");
			lolDoor.setDirectionAndImage(FACENORTH);
			_movingTrainers.add(lolDoor); //4
			
			Trainer apma = new Trainer.BlackDude(null);
			apma.createHome(9, 19, 0, -2, "", "");
			apma.getDialogue().add("This is the Applied Math building.");
			apma.getDialogue().add("Doesn't it look like an epic castle?!?");
			apma.setDirectionAndImage(FACEEAST);
			_movingTrainers.add(apma); //5
				
			Trainer doorSign = new Trainer.Text();
			doorSign.createHome(15,10, 0, 0, "","");
			doorSign.getDialogue().add("\"Door temporarily broken.\"");
			doorSign.getDialogue().add("\"Please enter through the Lobby.\"");
			if(_gbs.getPlayer().isGymLeaderDefeated(4)){
				doorSign.createHome(0,0);
			}
			_movingTrainers.add(doorSign); //6
			
			
			Trainer princeBar = new Trainer.Swiper(_gbs.getPlayer(), this, 4, 0);
			princeBar.createHome(3, 6, -1000, -1000, "", "");
			_movingTrainers.add(princeBar); //7
			
			Trainer powerOutage = new Trainer.GlassesProfessor(null);
			if(!canFlash && !BarHolLobby.powerOn && _gbs.getPlayer().isGymLeaderDefeated(3)){			
				powerOutage.createHome(17, 13,0,0,"freeze","freeze");
				powerOutage.setDirectionAndImage(FACENORTH);
				powerOutage.setLOS(4);
				powerOutage.getDialogue().add("There's been another power-outage.");
				powerOutage.getDialogue().add("I'm afraid you won't be able to see inside without...");
				powerOutage.getDialogue().add("...a Pokemon that knows the move Flash.");
				powerOutage.getDialogue().add("One of my lab assistants had it, but she's on break.");
				powerOutage.getDialogue().add("She hopped on that blue bus down there, maybe you should follow.");
			}
			else{
				powerOutage.setVanishing(true);
				powerOutage.defeat();
			}
			_movingTrainers.add(powerOutage); //8
			
			Trainer grad = new Trainer.ShadyGlasses(null);
			grad.createHome(20, 13, 0, 0, "freeze", "freeze");
			grad.setStationary(false);
			grad.addDest(24, 13);
			grad.addDest(20,13);
			grad.setFirstDest();
			grad.setPause(false);
			grad.getDialogue().add("A grad student, pacing furiously and smoking a cigarette.");
			grad.getDialogue().add("...best not to interrupt.");
			_movingTrainers.add(grad); //9
			
			Trainer student = new Trainer.RedBandanaGirl(null);
			student.createHome(2, 10, -3, 0, "wander", "wander");
			student.addDest(12, 9);
			student.addDest(2,10);
			student.setDirectionAndImage(FACEWEST);
			student.setFirstDest();
			student.setStationary(false);
			student.getDialogue().add("I LOVE Barus and Holley! I wish I could take ALL my classes here!");
			student.getDialogue().add("...stupid ABET humanities requirements...");
			_movingTrainers.add(student); //10
		}
		catch(IOException ioe){}

	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Barus and Holley";
		this._roomNum = _gbs.OUTSIDE_BARHOL;
		
		this.xConstant = 2;
		this.yConstant = 0;
		
		this._mapX = 270;
		this._mapY = 305; //???
		this._flyX=18;
		this._flyY=13;
		this._outdoors=true;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.song = M2.BIOMED;
		
		this.setBikeAllow(true);
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/OutsideBarHol/outsideBH.png"));
			over = ImageIO.read(this.getClass().getResource("/PokemonFiles/OutsideBarHol/outsideBHOver.png"));
			cars = ImageIO.read(this.getClass().getResource("/PokemonFiles/OutsideBarHol/outsideBHCars.png"));
			bus = ImageIO.read(this.getClass().getResource("/PokemonFiles/OutsideBarHol/outsideBHBus.png"));
			
		}
		catch(IOException ioe){ioe.printStackTrace();}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(26, 21);
		
		Scanner scan;
		
		//Only between gym 3 and 4.
		if(!_gbs.getPlayer().isGymLeaderDefeated(4) && _gbs.getPlayer().isGymLeaderDefeated(3)){
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/OutsideBarHolBefore.cmap"));
		}
		else{
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/OutsideBarHolAfter.cmap"));
		}
		for(int i = 0; i < 21; i++){
			for(int i2 = 0; i2 < 26; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		
		//Only in the specific case between gym leader 3 and 4.
		if(!_gbs.getPlayer().isGymLeaderDefeated(4) && _gbs.getPlayer().isGymLeaderDefeated(3)){
			g2.drawImage(bus, null, this._xSpace, this._ySpace);
		}
		else{
			g2.drawImage(cars, null, this._xSpace, this._ySpace);
		}
		
		this.drawPlayer(g2);
		g2.drawImage(over, null, this._xSpace, this._ySpace);
		
		
		
		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 0 && (yInd == 10 || yInd == 9)){
			super.enterRoom(_gbs.SCIENCE_QUAD, 33, yInd+5, FACEWEST);
		}
		if((xInd == 18 || xInd == 19) && yInd == 12){
			super.enterRoom(_gbs.BARHOL_LOBBY, xInd-14, 26, FACENORTH);
		}
		if(xInd == 15 && yInd == 10){
			super.enterRoom(_gbs.BARHOL_LOBBY, 1, 3, FACESOUTH);
		}
		if(xInd == 3 && yInd == 6){
			super.enterRoom(_gbs.PRINCE_LAB, 1,10,FACENORTH);
		}
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		if(xInd == 15 && yInd == 18){
			this._fadeToBlack.start();
			OutsideFishCo.entering=true;
			this.busEnterRoom(4, 9, FACENORTH);
		}
	}
	
	public void busEnterRoom(int newXIndex, int newYIndex, int direction){
		int roomDest=_gbs.OUTSIDE_FISHCO;
		_gbs.getCurrentPanel().setMartVisible(false);
		_gbs.getCurrentPanel().setPCVisible(false);
		
		String prevDescript = _gbs.getCurrentPanel().description;
		_gbs.setCurrentPanel(roomDest);
		PokePanel2 current = _gbs.getCurrentPanel();

		M2.playFXNoPause(M2.DOOR);
		
		current.createBaseRoom();
		
		if(!_gbs.getMode()){
			SysOut.print("Next Room: " + current.getSong().getFileName());
			SysOut.print("Currently Playing: " + M2.getCurrentSong().getFileName());
			if(current.getSong() != M2.getCurrentSong())
				M2.playBG(current.getSong());
		}
		
		current.setIndices(newXIndex, newYIndex);
		current.setLocation(-current.xConstant-(20*current._xIndex)+176, -current.yConstant-(20*current._yIndex)+168);
		current._darkLevel = 240;
		current._fadeUp.start();
		current.resetNTimer();
		if(prevDescript != current.description)
			_gbs.startNTimer();
		
		OutsideFishCo.busTimer.start();
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		
		
		//TODO: MUZAK
	}
	
	public void scanForAllEvents(){
		this.checkCanFlash();

		if(_movingTrainers.get(LOBBY_SWIPER1).isDefeated()){
			this.getCheckList().set(0,1);
		}
		if(_movingTrainers.get(LOBBY_SWIPER2).isDefeated()){
			this.getCheckList().set(1,1);
		}
		if(_movingTrainers.get(PRINCE_SWIPER).isDefeated()){
			this.getCheckList().set(2,1);
		}
		if(_movingTrainers.get(APPLE).getGift() == null){
			this.getCheckList().set(3, 1);
			_movingTrainers.get(APPLE).unDefeat();
		}
		if(_movingTrainers.get(PROF).getLOS() ==0){
			this.getCheckList().set(4, 1);
		}
		
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0) == 1){
				_movingTrainers.get(LOBBY_SWIPER1).defeat();
				_movingTrainers.get(LOBBY_SWIPER2).defeat();
			}
			if(this.getCheckList().get(1) == 1){
				_movingTrainers.get(LOBBY_SWIPER1).defeat();
				_movingTrainers.get(LOBBY_SWIPER2).defeat();
			}
			if(this.getCheckList().get(2) == 1){
				_movingTrainers.get(PRINCE_SWIPER).defeat();
			}
			if(this.getCheckList().get(3) == 1){
				_movingTrainers.get(APPLE).setGift(null);
			}
			if(this.getCheckList().get(4) == 1){
				_movingTrainers.get(PROF).setLOS(0);
			}
			
		}
	}
	public void Up(){
		if(!_menuVisible && NORTH && (_xIndex == 18 || _xIndex==19) && _yIndex == 13 && !_dialogueVisible && !this.getMovingTrainers().get(LOBBY_SWIPER1).isDefeated()){
			this.A_Button();
			return;
		}
		if(!_menuVisible && NORTH && (_xIndex == 3) && _yIndex == 7 && !_dialogueVisible && !this.getMovingTrainers().get(PRINCE_SWIPER).isDefeated()){
			this.A_Button();
			return;
		}
		super.Up();
	}
}
