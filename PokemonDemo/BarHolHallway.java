package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class BarHolHallway extends PokePanel2 {

	private BufferedImage over, black;
	private boolean sciGuyPlea = false, sciGuyForced=false;
	private final int PLEA=0, TEXT=1, FORCE=2;
	
	public BarHolHallway(GameBoyScreen gbs){
		super(gbs);
	
		this.initializeEventVector(3);
		this.createBaseRoom();
	}
	
	public BarHolHallway(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		
		this.initializeEventVector(3);
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0) == 1){
				sciGuyPlea = true;
			}
			if(this.getCheckList().get(1) == 1){
				sciGuyForced = true;
			}
			if(this.getCheckList().get(2)==1 && !BarHolLobby.powerOn){
				_movingTrainers.get(0).defeatAndPostBattle();
			}
		}
	}
	
	public void scanForAllEvents(){
		if(sciGuyPlea){
			this.getCheckList().set(0, 1);
		}
		if(sciGuyForced){
			this.getCheckList().set(1, 1);
		}
		if(!BarHolLobby.powerOn && _movingTrainers.get(0).isDefeated()){
			this.getCheckList().set(2, 1);
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Stemmler Hall";
		this._darkRoom = true;
		this._mapX = 270;
		this._mapY = 305;
		//this.xConstant = 10;
		this.setBattleBG(NewBattleScreen.BARHOL);
		this._roomNum = _gbs.BARHOL_HALLWAY;
		this.xConstant = -17;
		this.yConstant = -10;
		
		this._caveEntranceNum = _gbs.OUTSIDE_BARHOL;
		this._caveX = 18;
		this._caveY = 14;
		
		this.PROBABILITY = PokePanel2.CAVE_PROB;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.addWilds();
		
		if(!BarHolLobby.powerOn){
			this.song = M2.BH_DARK;
			this.setBikeAllow(true);
		}
		else{
			this.song = M2.BH_LIGHT;
			this.setBikeAllow(false);
		}
		
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolHallway/hallway.png"));
				over = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolHallway/hallwayO.png"));
				black = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLobby/BH Black General.png"));
			}
		}
		catch(IOException ioe){ioe.printStackTrace();}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(11, 19);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BHHallway.cmap"));
		for(int i = 0; i < 19; i++){
			for(int i2 = 0; i2 < 11; i2++){
				char f_OR_x = scan.next().charAt(0);
				
				if(BarHolLobby.powerOn && f_OR_x == 'F'){
					this._room._roomGrid[i2][i] = 'X';
				}
				else{
					this._room._roomGrid[i2][i] = f_OR_x;
				}
			}
		}
		if(!BarHolLobby.powerOn){
			this._room._roomGrid[7][7] = 'N';
			this._room._roomGrid[8][7] = 'N';
		}
		if(!_gbs.getPlayer().isGymLeaderDefeated(4)){
			//this._room._roomGrid[10][1] = 'N';
			this._room._roomGrid[0][4] = 'N';
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		_gbs.getPlayer().setIgnoring(false);
		if(xInd == 0){
			if(yInd == 4){
				super.enterRoom(_gbs.PRINCE_LAB, 20, 8, FACEWEST);
			}
			else{
				super.enterRoom(_gbs.BARHOL_LOBBY, 15, yInd-4, FACEWEST);
			}
		}
		if((xInd == 7 || xInd == 8) && yInd == 7){
			super.enterRoom(_gbs.BARHOL_GYM, 9-xInd, 15, FACENORTH);
		}
		if(xInd == 10 && yInd == 1){
			super.enterRoom(_gbs.BARHOL_LAB, 9, 8, FACENORTH);
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		if(BarHolLobby.powerOn){
			
			try{
				Trainer scientist = new Trainer.GlassesProfessor(null);
				scientist.createHome(0, 0,1000,1000,"",""); //will be overridden later.
				
				Trainer text = new Trainer.Text();
				Trainer sciForce = new Trainer.GlassesProfessor(null);
				sciForce.createHome(0, 0, 1000, 1000, "", "");
				
				if(!_gbs.getPlayer().isGymLeaderDefeated(4)){	
					scientist.setVanishing(true);
					if(!sciGuyPlea){
						scientist.createHome(6,3,0,0,"horiz","horiz");
						scientist.setDirectionAndImage(FACENORTH);
						scientist.setLOS(5);
						scientist.getDialogue().add("Hey there! I'm kind of in a bind, and was hoping you could help.");
						scientist.getDialogue().add("My experiment is almost completed, but I've run out of funding...");
						scientist.getDialogue().add("If you could maybe speak to Hazeltine on my behalf?");
						scientist.getDialogue().add("Great! Thank you so much! I'll get back to work finishing the device...");
						scientist.getDialogue().add(" ");
						scientist.setStationary(true);
					}
					else{
						scientist.setVanishing(true);
						scientist.defeat();
						scientist.createHome(0, 0,1000,1000,"","");
					}
					
					
					text.createHome(10, 1, 0, 0, "", "");
					text.getDialogue().add("The lab is locked.");
					text.getDialogue().add("You hear various machine and tinkering noises...");
					
				}
				
				else if(_gbs.getPlayer().isGymLeaderDefeated(4)){

					sciForce.setVanishing(true);
					if(!sciGuyForced){
						sciForce.createHome(8,3,0,0,"avoid","avoid");
						sciForce.setDirectionAndImage(FACENORTH);
						sciForce.setLOS(5);
						sciForce.getDialogue().add("Hey, me again! I'm happy you talked to Hazeltine for me.");
						sciForce.getDialogue().add(" ");
						sciForce.setStationary(true);
					}
					else{
						sciForce.setVanishing(true);
						sciForce.defeat();
						sciForce.createHome(0, 0, 1000, 1000, "","");
					}
				}
				
				
				_movingTrainers.add(scientist); //MUST BE .get(0);
				_movingTrainers.add(text);
				_movingTrainers.add(sciForce); //MUST BE .get(3);

				Trainer bob = new Trainer.MaroonHat(null);
				bob.createHome(3, 13, 0, 0, "avoid", "avoid");
				bob.setDirectionAndImage(FACESOUTH);
				bob.addDest(5, 3);
				bob.addDest(3, 13);
				bob.setFirstDest();
				bob.setStationary(false);
				bob.getDialogue().add("Just had hour-and-a-half long lecture on thermodynamics...");
				bob.getDialogue().add("Need...liquid-form Hydrogen Dioxide...");
				_movingTrainers.add(bob);
				
			}
			
			
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		else{
			try{
				Vector<Pokemon> randBelt = new Vector<Pokemon>();
				randBelt.add(new Pokemon.Poliwag().setWildLevel(16));
				randBelt.add(new Pokemon.Horsea().setWildLevel(16));
				randBelt.add(new Pokemon.Bellsprout().setWildLevel(16));
				randBelt.add(new Pokemon.Oddish().setWildLevel(16));
				randBelt.add(new Pokemon.Growlithe().setWildLevel(16));
				randBelt.add(new Pokemon.Vulpix().setWildLevel(16));
				Trainer rando = new Trainer.MaroonHat(randBelt);
				rando.createHome(4, 13, 0, 0, "freeze", "freeze");
				rando.setDirectionAndImage(FACEEAST);
				rando.setLOS(4);
				rando.setMoney(1492);
				rando.setType("Over-Achiever");
				rando.setName("Bill");
				rando.getDialogue().add("I'm a materials engineer, so I have to study all kinds of Pokemon.");
				rando.setDefeatDialogue("Hmmm, perhaps I need to expand my research...");
				_movingTrainers.add(rando);
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		for(int i = 17; i <= 20; i++){
			this._wildPokemon.add(new Pokemon.Primeape().setWildLevel(i));
		}
//		for(int i = 20; i <= 22; i++){
//			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
//		}
//		for(int i = 17; i <= 20; i++){
//			this._wildPokemon.add(new Pokemon.Mankey().setWildLevel(i));
//		}
//		for(int i = 17; i <= 20; i++){
//			this._wildPokemon.add(new Pokemon.Meowth().setWildLevel(i));
//		}
//		for(int i = 19; i <= 22; i++){
//			this._wildPokemon.add(new Pokemon.Oddish().setWildLevel(i));
//		}
//		for(int i = 19; i <= 22; i++){
//			this._wildPokemon.add(new Pokemon.Bellsprout().setWildLevel(i));
//		}
//		for(int i = 15; i <= 26; i++){
//			this._wildPokemon.add(new Pokemon.Abra().setWildLevel(i));
//		}
//		this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(20));
//		for(int i = 18; i <= 20; i++){
//			this._wildPokemon.add(new Pokemon.Growlithe().setWildLevel(i));
//		}
//		for(int i = 18; i <= 20; i++){
//			this._wildPokemon.add(new Pokemon.Vulpix().setWildLevel(i));
//		}
//		this._wildPokemon.add(new Pokemon.Pidgeotto().setWildLevel(24));
//		for(int i = 19; i <= 24; i++){
//			this._wildPokemon.add(new Pokemon.Jigglypuff().setWildLevel(i));
//		}
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		
		int random = (int)(101*Math.random());
		
		if(random < 100){
			randomEnemy = _wildPokemon.get((int)(Math.random()*3));
		}
//		else if(random < 45){
//			randomEnemy = _wildPokemon.get(3+(int)(Math.random()*4));
//		}
//		else if(random < 55){
//			randomEnemy = _wildPokemon.get(7+(int)(Math.random()*4));
//		}
//		else if(random < 63){
//			randomEnemy = _wildPokemon.get(11+(int)(Math.random()*4));
//		}
//		else if(random < 71){
//			randomEnemy = _wildPokemon.get(15+(int)(Math.random()*4));
//		}
//		else if(random < 79){
//			randomEnemy = _wildPokemon.get(19+(int)(Math.random()*12));
//		}
//		else if(random < 84){
//			randomEnemy = _wildPokemon.get(31);	
//		}
//		else if(random < 88){
//			randomEnemy = _wildPokemon.get(32+(int)(Math.random()*3));
//		}
//		else if(random < 92){
//			randomEnemy = _wildPokemon.get(35+(int)(Math.random()*3));
//		}
//		else if(random < 96){
//			randomEnemy = _wildPokemon.get(38);
//		}
//		else{
//			randomEnemy = _wildPokemon.get(39+(int)(Math.random()*6));
//		}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		
		this.setupBattle(ally, randomEnemy, false, _roomNum);
		_gbs.setCurrentPanel(-1);
//		_gbs.repaint();
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(over, null, this._xSpace, this._ySpace);
		if(!BarHolLobby.powerOn && !_flashOn){
			g2.drawImage(black, null, this._xSpace, this._ySpace);
			//this.drawPlayer(g2);
		}
		this.drawAllGenerics(g2);
	}
	
	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning()){
			if(!_busy){
				this.completionCheck = false;
			}
			this.repaint();
		}		
		
		super.A_Button();
			
		if(!BarHolLobby.powerOn){
			return;
		}
		
		if(xObs==10 && yObs==1){
			this.getMovingTrainers().get(FORCE).defeat();
		}
		
		if(this._movingTrainers != null && !_gbs.getPlayer().isGymLeaderDefeated(4)){
			Trainer sci = this.getMovingTrainers().get(PLEA);
			Trainer tex = this.getMovingTrainers().get(TEXT);
			
			if(sci.getXIndex() == 10 && sci.getYIndex() == 1){
				sci.defeat();
				sciGuyPlea = true;
				sci.createHome(0,0);
				this._approachTimer.stop();
				completionCheck=false;
				_NPCpage=0;
				this._dialogueVisible=false;
				_interruptedTrainer=0;
				tex.createHome(10, 1, 0, 0, "", "");
				tex.unDefeat();
			}
			
			if(!sci.isDefeated() && this._NPCpage == 4 && _interruptedTrainer == 0 && facingNPC()){
				sci.getDialogue().clear();
				sci.setInterrupted(false);
				sci.setPause(false);
				sci.addDest(10, 1);
				sci.setFirstDest();
				sci.setStationary(false);
				
				
				tex.createHome(1, 1, 0, 0, "", "");
				tex.setVanishing(true);
				tex.defeat();
				this._approached=true;
				_busy=false;
				this._approachTimer.start();
				_NPCpage=0;
			}
		}
		
		if(this._movingTrainers != null && _gbs.getPlayer().isGymLeaderDefeated(4)){
			Trainer sciF = this.getMovingTrainers().get(FORCE);
			if(!sciGuyForced && this._NPCpage == 1 && _interruptedTrainer == FORCE && sciF.isInterrupted()){
			SysOut.print("APPROACHING!!!!!");
			sciGuyForced=true;
			sciF.setInterrupted(false);
			sciF.setPause(false);
			sciF.addDest(8, 5);
			sciF.setFirstDest();
			sciF.getDialogue().clear();
			sciF.setVanishing(true);
			sciF.setDefeatAfterReturnTrip(true);
			sciF.getDialogue().add("Come to my lab, I've got something to show you.");
			//sciF.defeat();
			this._dialogueVisible=false;
			this._NPCpage=0;
			sciF.setHomeIndices(10, 1);
			this.setPlayerDest(10, 1);
			_gbs.getPlayer().setAvoidMethod("avoid");
			_gbs.getPlayer().setIgnoring(true);
			
			this._approached=true;
			//this._returnTrip=true;
			this._approachTimer.start();  
			}		
		}
		
		
	}
	
	
	
}
