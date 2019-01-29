package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class BusTunnel extends PokePanel2 {
	private BufferedImage _roomO, busTop, busBottom;
	private int busTopX = 600, busBottomX = -2400;
	
	public BusTunnel(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
		BusTimer bt = new BusTimer();
		Timer busMove = new Timer(10, bt);
		busMove.start();
	}

	public BusTunnel(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
		BusTimer bt = new BusTimer();
		Timer busMove = new Timer(10, bt);
		busMove.start();
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
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
			
				Vector<Pokemon> mBelt = new Vector<Pokemon>();
				mBelt.add(new Pokemon.Cubone().setWildLevel(23));
				mBelt.add(new Pokemon.Slowpoke().setWildLevel(23));
				Trainer benK = new Trainer.MaroonHat(mBelt);
				benK.createHome(4, 3,0,0,"freeze","freeze");
				benK.setLOS(2);
				benK.setDirectionAndImage(FACENORTH);
				benK.setMoney(1250);
				benK.getDialogue().add("In order to pledge, you gotta do a lap in the tunnel.");
				benK.setType("Frat Bro");
				benK.setName("Ben");
				benK.setDefeatDialogue("One more lap!");
				_movingTrainers.add(benK);
				
				Vector<Pokemon> broBelt = new Vector<Pokemon>();
				broBelt.add(new Pokemon.Slowbro().setWildLevel(25));
				Trainer guy = new Trainer.BlueBro(broBelt);
				guy.createHome(18, 2, 0, 0, "freeze", "freeze");
				guy.setLOS(3);
				guy.setMoney(1520);
				guy.setType("Bro");
				guy.setName("Will");
				guy.getDialogue().add("Come at me bro!");
				guy.setDefeatDialogue("Don't taze me bro!");
				_movingTrainers.add(guy);
				
				Trainer helix = new Trainer.ItemObject(new Item.Helix_Fossil());
				helix.createHome(13, 4);
				_movingTrainers.add(helix);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void createBaseRoom(){
		
		this.song = M2.OLIVE_PIT;
		
		this.addTrainers();
		this.loadAllEvents();
		this.addWilds();
		this.xConstant=2;
		this.yConstant=0;
		this.PROBABILITY=PokePanel2.CAVE_PROB;
		this.setBattleBG(NewBattleScreen.LIGHT_CAVE);
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=175;
		this._mapY=277;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		//this._pkmnCentX=;
		//this._pkmnCentY=;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;
		
		//TODO
		this._caveEntranceNum = _gbs.WATERFIRE;
		this._caveX = 2;
		this._caveY = 25;

		this.setBikeAllow(true);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Bus Tunnel";
		this._roomNum = _gbs.BUS_TUNNEL;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BusTunnel/Tunnel.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/BusTunnel/TunnelO.png"));
				busTop = ImageIO.read(this.getClass().getResource("/PokemonFiles/BusTunnel/Buses Top.png"));
				busBottom = ImageIO.read(this.getClass().getResource("/PokemonFiles/BusTunnel/Buses Bottom.png"));
			}
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		for(int i = 18; i <= 21; i++){
			this._wildPokemon.add(new Pokemon.Zubat().setWildLevel(i));
		}
		for(int i = 17; i <= 20; i++){
			this._wildPokemon.add(new Pokemon.Geodude().setWildLevel(i));
		}
		for(int i = 17; i <= 21; i++){
			this._wildPokemon.add(new Pokemon.Diglett().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Onix().setWildLevel(19));
	}

	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		if(random < 55.0){
			randomEnemy = _wildPokemon.get(0 + (int)(4*Math.random()));
		}
		else if(random < 80.0){
			randomEnemy = _wildPokemon.get(4 + (int)(4*Math.random()));
		}
		else if(random < 95.0){
			randomEnemy = _wildPokemon.get(8 + (int)(5*Math.random()));
		}
		else if(random < 100.0){
			randomEnemy = _wildPokemon.get(13);
		}
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	private class BusTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			busTopX--;
			busBottomX++;
			repaint();
			if(busTopX == -2400){
				busTopX = 600;
				busBottomX = -2400;
			}
		}
	}

	public void createGrid(){
		this._room = new Room(30,7);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BusTunnel.cmap"));
		for(int i = 0; i < 7; i++){
			for(int i2 = 0; i2 < 30; i2++){
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
		
		g2.drawImage(busTop, null, busTopX+this._xSpace, this._ySpace);
		g2.drawImage(busBottom, null, busBottomX+this._xSpace, this._ySpace);

		g2.drawImage(_roomO,null, this._xSpace-200, this._ySpace);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Waterfire
 		if((xInd == 0) && (yInd == 2||yInd == 3||yInd == 4)){
 			super.enterRoom(_gbs.WATERFIRE, 2, 26, FACENORTH);
 		}
 		
		//Thayer
 		if((xInd == 29) && (yInd == 2||yInd == 3||yInd == 4)){
 			super.enterRoom(_gbs.THAYER_NORTH, 3, 54, FACEEAST);
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