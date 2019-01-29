package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class BarHolBasement extends PokePanel2 {

	private BufferedImage black;
	
	public BarHolBasement(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(4);
		this.createBaseRoom();
	}
	
	public BarHolBasement(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		
		this.initializeEventVector(4);
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList() != null){
			if(this.getCheckList().get(0)==1 && !BarHolLobby.powerOn){
				_movingTrainers.get(0).defeatAndPostBattle();
			}
			if(this.getCheckList().get(1)==1 && !BarHolLobby.powerOn){
				_movingTrainers.get(1).defeatAndPostBattle();
			}
			if(this.getCheckList().get(2)==1 && !BarHolLobby.powerOn){
				_movingTrainers.get(2).defeat();
			}
			if(this.getCheckList().get(3)==1 && !BarHolLobby.powerOn){
				_movingTrainers.get(3).defeat();
			}
		}
	}
	
	public void scanForAllEvents(){
		if(!BarHolLobby.powerOn && _movingTrainers.get(0).isDefeated()){
			this.getCheckList().set(0, 1);
		}
		if(!BarHolLobby.powerOn && _movingTrainers.get(1).isDefeated()){
			this.getCheckList().set(1, 1);
		}
		if(!BarHolLobby.powerOn && _movingTrainers.get(2).isDefeated()){
			this.getCheckList().set(2,1);
		}
		if(!BarHolLobby.powerOn && _movingTrainers.get(3).isDefeated()){
			this.getCheckList().set(3,1);
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		
		try{
			if(!BarHolLobby.powerOn){
				Vector<Pokemon>  anishBelt = new Vector<Pokemon>();
				anishBelt.add(new Pokemon.Magneton().setWildLevel(21));
				anishBelt.add(new Pokemon.Hypno().setWildLevel(21));
				Trainer anish = new Trainer.BrownGuy(anishBelt);
				anish.createHome(3,6,0,0,"freeze","freeze");
				anish.setMoney(2012);
				anish.setLOS(3);
				anish.setType("Brain Researcher");
				anish.setName("Anish");
				anish.getDialogue().add("Are Psychic and Electric Pokemon even different?");
				anish.setDefeatDialogue("More research is needed...");
				anish.setBattleImage(TImg.M_MAGICIAN);
				_movingTrainers.add(anish);
				
				Vector<Pokemon> willBelt = new Vector<Pokemon>();
				willBelt.add(new Pokemon.Jolteon().setWildLevel(18));
				willBelt.add(new Pokemon.Scyther().setWildLevel(18));
				willBelt.add(new Pokemon.Raichu().setWildLevel(19));
				Trainer will = new Trainer.RedHairBike(willBelt);
				will.createHome(7,4,0,0,"freeze","freeze");
				will.setDirectionAndImage(FACEEAST);
				will.setLOS(6);
				will.setMoney(2012);
				will.setType("Quantum Nano-Engineer");
				will.setName("Will");
				will.getDialogue().add("Sup.");
				will.setDefeatDialogue("...");
				will.setVanishing(true);
				will.setBattleImage(TImg.M_REDHEAD);
				_movingTrainers.add(will);
				
				Trainer greatBall = new Trainer.ItemObject(new Item.GreatBall());
				greatBall.createHome(10, 7);
				_movingTrainers.add(greatBall);
				
				Vector<Pokemon> brentBelt = new Vector<Pokemon>();
				brentBelt.add(new Pokemon.Growlithe().setWildLevel(20));
				brentBelt.add(new Pokemon.Squirtle().setWildLevel(20));
				Trainer brent = new Trainer.YellowHatBoy(brentBelt);
				brent.createHome(8, 5, 0, 0, "freeze", "freeze");
				brent.setDirectionAndImage(FACEEAST);
				brent.setLOS(6);
				brent.setType("Computer Engineer");
				brent.setName("Brent");
				brent.getDialogue().add("Who needs fancy Pokemon? I go with the classics!");
				brent.setDefeatDialogue("Why would you do this to me.");
				brent.setMoney(2012);
				brent.setBattleImage(TImg.M_SNOW_PATROL);
				_movingTrainers.add(brent);
				
				
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this._roomNum = _gbs.BARHOL_BASEMENT;
		this.description = "B&H Basement";
		this.setBattleBG(NewBattleScreen.BARHOL);
		this._darkRoom = true;
		this._mapX = 270;
		this._mapY = 305;
		this.xConstant=3;
		this.yConstant=0;
		
		this.PROBABILITY = PokePanel2.CAVE_PROB;

		this.addTrainers();
		this.loadAllEvents();
		
		
		this._caveEntranceNum = _gbs.OUTSIDE_BARHOL;
		this._caveX = 18;
		this._caveY = 14;
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
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolBasement/BarHolBasement.png"));
				black = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLobby/BH Black General.png"));
			}
		}
		catch(IOException ioe){}
		
		this.createGrid();
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		
		for(int i = 20; i <= 22; i++){
			this._wildPokemon.add(new Pokemon.Drowzee().setWildLevel(i));
		}
		for(int i = 17; i <= 20; i++){
			this._wildPokemon.add(new Pokemon.Mankey().setWildLevel(i));
		}
		for(int i = 17; i <= 20; i++){
			this._wildPokemon.add(new Pokemon.Meowth().setWildLevel(i));
		}
		for(int i = 19; i <= 22; i++){
			this._wildPokemon.add(new Pokemon.Grimer().setWildLevel(i));
		}
		for(int i = 19; i <= 22; i++){
			this._wildPokemon.add(new Pokemon.Koffing().setWildLevel(i));
		}
		for(int i = 15; i <= 26; i++){
			this._wildPokemon.add(new Pokemon.Abra().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Ekans().setWildLevel(20));
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
		//_gbs.repaint();
	}

	public void createGrid(){
		this._room = new Room(11,10);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BHBasement.cmap"));
		for(int i = 0; i <10; i++){
			for(int i2 = 0; i2 < 11; i2++){
				char f_OR_x = scan.next().charAt(0);
				
				if(!BarHolLobby.powerOn && f_OR_x == 'F'){
					this._room._roomGrid[i2][i] = 'X';
				}
				else{
					this._room._roomGrid[i2][i] = f_OR_x;
				}
			}
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 0 && yInd == 8){
			super.enterRoom(_gbs.BARHOL_LOBBY, 0, 8, FACENORTH);
		}
		if(xInd == 4 && yInd ==3){
			super.enterRoom(_gbs.BARHOL_GENERATOR, 1, 5, FACENORTH);
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);

		if(!BarHolLobby.powerOn && !_flashOn){
			g2.drawImage(black, null, this._xSpace, this._ySpace);
		}
		this.drawAllGenerics(g2);
	}
}
