package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class BarHolLobby extends PokePanel2 {

	private BufferedImage over, black;
	public static boolean powerOn;
	private Trainer print;
	private final int LOBBY1 = 0, LOBBY2 = 1, LOBBY3 = 2;
	
	public BarHolLobby(GameBoyScreen gbs){
		super(gbs);
		this.initializeEventVector(4);
		this.createBaseRoom();
	}
	
	public BarHolLobby(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		this.initializeEventVector(4);
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0) == 1 && print != null){
				print.setGift(null);
				print.getDialogue().clear();
				print.getDialogue().add("Use that Exp.All, and you'll level up your whole team!");
			}
			if(this.getCheckList().get(1) == 1 && !powerOn){
				_movingTrainers.get(LOBBY1).defeatAndPostBattle();
			}
			if(this.getCheckList().get(2) == 1 && !powerOn){
				_movingTrainers.get(LOBBY2).defeatAndPostBattle();
			}
			if(this.getCheckList().get(3) == 1 && !powerOn){
				_movingTrainers.get(LOBBY3).defeatAndPostBattle();
			}
		}
	}
	
	public void scanForAllEvents(){
		if(print!=null && print.getGift() == null){
			this.getCheckList().set(0,1);
		}
		if(!powerOn && _movingTrainers.get(LOBBY1).isDefeated()){
			this.getCheckList().set(1,1);
		}
		if(!powerOn && _movingTrainers.get(LOBBY2).isDefeated()){
			this.getCheckList().set(2,1);
		}
		if(!powerOn && _movingTrainers.get(LOBBY3).isDefeated()){
			this.getCheckList().set(3,1);
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		

		
		
		if(powerOn){
			
			Trainer posterSign = new Trainer.Text();
			posterSign.createHome(3,24,0,0,"","");
			posterSign.getDialogue().add("Welcome to our Physics poster session!");
			_movingTrainers.add(posterSign);
			
			Trainer cat = new Trainer.Text();
			cat.createHome(1, 22);
			cat.getDialogue().add("WANTED: Schrödinger's Cat, Dead AND Alive!");
			_movingTrainers.add(cat);
			
			Trainer heis = new Trainer.Text();
			heis.createHome(1,24);
			heis.getDialogue().add("This is a poster about Heisenberg.");
			heis.getDialogue().add("\"Why couldn't Heisenberg find his car keys?");
			heis.getDialogue().add("Because he knew too much about their momentum!\"");
			_movingTrainers.add(heis);
			
			Trainer mart = new Trainer.MartMenu("B&H Lobby", this);
			mart.createHome(15,20);
			_movingTrainers.add(mart);
			this._thisMartContains.clear();
			this._thisMartContains.add(24);
			this._thisMartContains.add(2);
			this._thisMartContains.add(13);
			this._thisMartContains.add(28);
			this._thisMartContains.add(32);
			this._thisMartContains.add(33);
			this._thisMartContains.add(27);
			this._thisMartContains.add(30);
			this._thisMartContains.add(49);
			
			Trainer pc = new Trainer.PC(_gbs);
			pc.createHome(6, 12);
			_movingTrainers.add(pc);
			
			try{
				Trainer lateHW = new Trainer.RedOveralls(null);
				lateHW.createHome(10, 17, 0, 0, "freeze", "freeze");
				lateHW.setStationary(false);
				lateHW.addDest(10, 18);
				lateHW.setFirstDest();
				lateHW.getDialogue().add("My homework isn't done, and class starts in two minutes!");
				lateHW.getDialogue().add("Quick, help me prove Fermat's Last Theorem...");
				lateHW.getDialogue().add("What do you mean you don't know it?!? YOU'RE USELESS!");
				_movingTrainers.add(lateHW);
				
				Trainer sleepText = new Trainer.Text();
				sleepText.createHome(14, 16, 0, 0, "", "");
				sleepText.getDialogue().add("ZzZzZzZzzz...");
				sleepText.getDialogue().add("He appears to be sleeping. Hope he isn't missing lab.");
				_movingTrainers.add(sleepText);
				
				Trainer sleep = new Trainer.ChubbyGuy(null);
				sleep.createHome(14, 16, 0, 0, "", "");
				sleep.setDirectionAndImage(FACEEAST);
				_movingTrainers.add(sleep);
				
				Trainer chat = new Trainer.WhiteBeltBike(null);
				chat.createHome(2, 16, 0, 0, "avoid", "avoid");
				chat.setStationary(false);
				chat.addDest(3, 14);
				chat.addDest(1, 13);
				chat.addDest(2, 18);
				chat.addDest(5, 15);
				chat.addDest(2,16);
				chat.setFirstDest();
				chat.getDialogue().add("What do you think of my fold-up bike?");
				chat.getDialogue().add("I just ride into work, fold it up, and leave it in my lab!");
				_movingTrainers.add(chat);
				
				Trainer grad1 = new Trainer.GlassesGirl(null);
				grad1.createHome(11, 24, 0, -3, "freeze", "freeze");
				grad1.setDirectionAndImage(FACEWEST);
				grad1.addDest(12,24);
				grad1.setFirstDest();
				grad1.setStationary(false);
				grad1.getDialogue().add("If you simply follow Bohr's research, it follows that...");
				_movingTrainers.add(grad1);
				
				Trainer grad2 = new Trainer.GlassesGuy1(null);
				grad2.createHome(12, 24, 0, 0, "freeze", "freeze");
				grad2.setDirectionAndImage(FACEEAST);
				grad2.addDest(11, 24);
				grad2.setFirstDest();
				grad2.setStationary(false);
				grad2.getDialogue().add("CLEARLY the Copenhagen interpretation is incorrect!");
				_movingTrainers.add(grad2);
				
				Trainer help = new Trainer.Female1(null);
				help.createHome(4,20,0,0,"","");
				help.getDialogue().add("The screen next to me is a directory of all of the Professors.");
				help.getDialogue().add("If you're looking for Prof. Hazeltine, he's usually in Room 166.");
				_movingTrainers.add(help);
				
				print = new  Trainer.StrawHat(null);
				print.createHome(7, 3, 0, 0, "", "");
				print.setDirectionAndImage(FACEWEST);
				
			//	if(this.getCheckList().get(0) == 0){
					print.getDialogue().add("Free printing for Engineers is the best.");
					print.getDialogue().add("I just printed the entire Pokemon: Brown Edition manual!");
					print.getDialogue().add("Appendix A, Pokedex...");
					print.getDialogue().add("Let me take a look at yours and see how you're doing...");
					
					if(_gbs.getPlayer().getPokedex().getNumCaught() >= 50){
						print.getDialogue().add("Great job! Keep up the good work.");
						print.getDialogue().add("Come find me after a couple of game updates for a gift.");
			//			print.getDialogue().add("Great, looks like you're making good progress!");
			//			print.getDialogue().add("Here's something I threw together in my Circuit Design class...");
			//			print.setGift(new Item.EXPAll());
			//			print.getPostItemDialogue().add("Use that Exp.All, and you'll level up your whole team!");
					}
					else{
						print.getDialogue().add("Not quite there yet, but I'm sure you'll do it!");
			//			print.getDialogue().add("Looks like you're getting there slowly but surely.");
			//			print.getDialogue().add("Come back and see me when you've caught more Pokemon.");
					}
			//	}
			//	else{
			//		print.getDialogue().add("Use that Exp.All, and you'll level up your whole team!");
			//	}
				_movingTrainers.add(print);

			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		else if(!powerOn){
			//Lobby1
			try{
				Vector<Pokemon> lobby1Belt = new Vector<Pokemon>();
				lobby1Belt.add(new Pokemon.Magnemite().setWildLevel(18));
				lobby1Belt.add(new Pokemon.Magnemite().setWildLevel(18));
				lobby1Belt.add(new Pokemon.Magneton().setWildLevel(18));
				Trainer lobby1 = new Trainer.ShadyGlasses(lobby1Belt);
				lobby1.createHome(7, 23, 0, 0, "freeze", "freeze");
				lobby1.setDirectionAndImage(FACEEAST);
				lobby1.setMoney(900);
				lobby1.setLOS(7);
				lobby1.setType("Electrical Engineer");
				lobby1.setName("Andrew");
				lobby1.getDialogue().add("I can see everything with my homemade nightvision goggles!");
				lobby1.setDefeatDialogue("I didn't see that coming!");
				_movingTrainers.add(lobby1);
			
			//	Lobby2
				Vector<Pokemon> lobby2Belt = new Vector<Pokemon>();
				lobby2Belt.add(new Pokemon.Sandslash().setWildLevel(19));
				lobby2Belt.add(new Pokemon.Golbat().setWildLevel(19));
				Trainer lobby2 = new Trainer.BlueBro(lobby2Belt);
				lobby2.createHome(11, 12, 0, 0, "freeze", "freeze");
				lobby2.setLOS(3);
				lobby2.setMoney(304);
				lobby2.setType("Pre-Frosh");
				lobby2.setName("Jason");
				lobby2.getDialogue().add("Hey, maybe you can help. Where does the Engineering tour start?");
				lobby2.setDefeatDialogue("I'll just go on a Physical Sciences tour instead!");
				_movingTrainers.add(lobby2);
				
			//	Lobby3
				
				Vector<Pokemon> girlBelt = new Vector<Pokemon>();
				girlBelt.add(new Pokemon.Nidoran_F().setWildLevel(19));
				girlBelt.add(new Pokemon.Nidorina().setWildLevel(20));
				Trainer eChick = new Trainer.RedBandanaGirl(girlBelt);
				eChick.createHome(8, 2, 0, 0, "freeze", "freeze");
				eChick.setLOS(5);
				eChick.setMoney(1004);
				eChick.setType("WiSE Mentor");
				eChick.setName("Katie");
				eChick.getDialogue().add("We can battle, or we can play kings.");
				eChick.getDialogue().add("You want to battle? Fine.");
				eChick.getDialogue().add("If my math is correct, I'll win this quickly and go drinking!");
				eChick.setDefeatDialogue("Half right!");
				//eChick.setVanishing(true);
				_movingTrainers.add(eChick);
				
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
			
			Trainer h1 = new Trainer.Barricade();
			h1.getDialogue().clear();
			h1.getDialogue().add("An emergency barricade. Must have gone up when the power went out.");
			h1.getDialogue().add("Better find another way around...");
			h1.createHome(2,10,0,0,"","");
			_movingTrainers.add(h1);
			
			Trainer h2 = new Trainer.Barricade();
			h2.getDialogue().clear();
			h2.getDialogue().add("An emergency barricade. Must have gone up when the power went out.");
			h2.getDialogue().add("Better find another way around...");
			h2.createHome(3,10,0,0,"","");
			_movingTrainers.add(h2);
			
			
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "B&H Lobby";

		if(!powerOn){
			this._darkRoom = true;
		}
	
		this.PROBABILITY = PokePanel2.CAVE_PROB;
		
		this._roomNum = _gbs.BARHOL_LOBBY;
		this._mapX = 270;
		this._mapY = 305; //???
		this.xConstant = 4;
		this.yConstant = 20;
		this.setBattleBG(NewBattleScreen.BARHOL);

		this._caveEntranceNum = _gbs.OUTSIDE_BARHOL;
		this._caveX = 18;
		this._caveY = 14;
		
		this.setMartVisible(false);
		this.addTrainers();		
		this.loadAllEvents();	

		if(!powerOn){
			this.song = M2.BH_DARK;
			this.addWilds();
			this.setBikeAllow(true);
		}
		else{
			this.song = M2.BH_LIGHT;
		}
		
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLobby/BarHolLobby.png"));
				over = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLobby/BarHolLobbyO.png"));
				black = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLobby/BarHolLobby Black.png"));
			}
		}
		catch(IOException ioe){ioe.printStackTrace();}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(16, 27);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BHLobby.cmap"));
		for(int i = 0; i <27; i++){
			for(int i2 = 0; i2 < 16; i2++){
				char f_OR_x = scan.next().charAt(0);
				
				if(!powerOn && f_OR_x == 'F'){
					this._room._roomGrid[i2][i] = 'X';
				}
				else{
					this._room._roomGrid[i2][i] = f_OR_x;
				}
			}
		}
		
		if(_gbs.getPlayer().isGymLeaderDefeated(4)){
			this._room._roomGrid[1][3] = 'D';
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		if((xInd == 4 || xInd == 5) && yInd == 26){
			super.enterRoom(_gbs.OUTSIDE_BARHOL, xInd+14, 12, FACESOUTH);
		}
		if(xInd == 0 && yInd == 8){
			super.enterRoom(_gbs.BARHOL_BASEMENT, 0, 8, FACEEAST);
		}
		if(xInd == 15){
			super.enterRoom(_gbs.BARHOL_HALLWAY, 0, yInd+4, FACEEAST);
		}
		if(xInd == 1 && yInd == 3){
			super.enterRoom(_gbs.OUTSIDE_BARHOL, 15, 10, FACESOUTH);
		}
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		
		for(int i = 20; i <= 22; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
		}
		for(int i = 17; i <= 20; i++){
			this._wildPokemon.add(new Pokemon.Mankey().setWildLevel(i));
		}
		for(int i = 17; i <= 20; i++){
			this._wildPokemon.add(new Pokemon.Meowth().setWildLevel(i));
		}
		for(int i = 19; i <= 22; i++){
			this._wildPokemon.add(new Pokemon.Oddish().setWildLevel(i));
		}
		for(int i = 19; i <= 22; i++){
			this._wildPokemon.add(new Pokemon.Bellsprout().setWildLevel(i));
		}
		for(int i = 15; i <= 26; i++){
			this._wildPokemon.add(new Pokemon.Abra().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(20));
		for(int i = 18; i <= 20; i++){
			this._wildPokemon.add(new Pokemon.Growlithe().setWildLevel(i));
		}
		for(int i = 18; i <= 20; i++){
			this._wildPokemon.add(new Pokemon.Vulpix().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Pidgeotto().setWildLevel(24));
		for(int i = 19; i <= 24; i++){
			this._wildPokemon.add(new Pokemon.Jigglypuff().setWildLevel(i));
		}
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		
		int random = (int)(101*Math.random());
		
		if(random < 35){
			randomEnemy = _wildPokemon.get((int)(Math.random()*3));
		}
		else if(random < 45){
			randomEnemy = _wildPokemon.get(3+(int)(Math.random()*4));
		}
		else if(random < 55){
			randomEnemy = _wildPokemon.get(7+(int)(Math.random()*4));
		}
		else if(random < 63){
			randomEnemy = _wildPokemon.get(11+(int)(Math.random()*4));
		}
		else if(random < 71){
			randomEnemy = _wildPokemon.get(15+(int)(Math.random()*4));
		}
		else if(random < 79){
			randomEnemy = _wildPokemon.get(19+(int)(Math.random()*12));
		}
		else if(random < 84){
			randomEnemy = _wildPokemon.get(31);	
		}
		else if(random < 88){
			randomEnemy = _wildPokemon.get(32+(int)(Math.random()*3));
		}
		else if(random < 92){
			randomEnemy = _wildPokemon.get(35+(int)(Math.random()*3));
		}
		else if(random < 96){
			randomEnemy = _wildPokemon.get(38);
		}
		else{
			randomEnemy = _wildPokemon.get(39+(int)(Math.random()*6));
		}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		
		this.setupBattle(ally, randomEnemy, false, _roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(over, null, this._xSpace, this._ySpace);
		if(!powerOn && !_flashOn){
			g2.drawImage(black, null, this._xSpace, this._ySpace);
		}
		
		
		this.drawAllGenerics(g2);
	}
	
	public void Up(){
		if(!_menuVisible && _xIndex == 5 && (_yIndex == 20 || _yIndex ==18)){
			this.setPlayerDirection(FACENORTH);
			return;
		}
		super.Up();
	}
	
	public void Down(){
		if(!_menuVisible && _xIndex == 5 && (_yIndex == 17 || _yIndex ==19)){
			this.setPlayerDirection(FACESOUTH);
			return;
		}
		super.Down();
	}
}
