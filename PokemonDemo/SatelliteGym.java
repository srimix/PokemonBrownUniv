package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


@SuppressWarnings("serial")
public class SatelliteGym extends PokePanel2 {
	private BufferedImage _roomO;
	private ImageIcon _runner1, _runner2, _runner3, _biker1, _biker2;
	
	public SatelliteGym(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(7);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public SatelliteGym(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(7);
		
		this.createBaseRoom();
		
	}
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		for(int i = 2; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Mankey().setWildLevel(i));
		}
		for(int i = 5; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Machop().setWildLevel(i));
		}
	}


	public void addTrainers(){
		this._movingTrainers=new Vector<Trainer>();

		try{
			Trainer lee = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Hitmonlee().setWildLevel(25), "(Hitmonlee stares you in the eye.)", "P-Hitmonlee", M2.HITMONLEE);
			lee.createHome(4,2,0,0,"freeze","freeze");
			lee.setLOS(2);
			lee.setStationary(true);
			this.getMovingTrainers().add(lee); //0
			
			Trainer chan = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Hitmonchan().setWildLevel(25), "(Hitmonchan puts his hands up and ready.)", "P-Hitmonchan", M2.HITMONCHAN);
			chan.createHome(5,2,0,0,"freeze","freeze");
			chan.setLOS(2);
			chan.setStationary(true);
			this.getMovingTrainers().add(chan); //1

			Vector<Pokemon> ibelt = new Vector<Pokemon>();
			ibelt.add(new Pokemon.Hitmonchan().setWildLevel(24));
			ibelt.add(new Pokemon.Hitmonlee().setWildLevel(24));
			Trainer ileana = new Trainer.Ileana(ibelt);
			ileana.createHome(6,5,0,0,"freeze","freeze");
			ileana.setLOS(4);
			ileana.setDirectionAndImage(FACEEAST);
			ileana.getDialogue().add("Ileana: You look lost. Know what you're doing here?");
			ileana.getDialogue().add("This is the EmWool Gym, where the toughest students...");
			ileana.getDialogue().add("...work out and get ripped. Our Pokemon are our personal...");
			ileana.getDialogue().add("...trainers, so prepare to get beat down!");
			ileana.setDefeatDialogue("I'm impressed. You might be my next project.");
			ileana.getPostBattleDialogue().add("You've shown yourself worthy of this.");
			ileana.setGift(new Item.TM09_Take_Down());
			ileana.getPostItemDialogue().add("That's Take Down. Careful, you might hurt yourself using it.");
			ileana.getPostItemDialogue().add("Hitmonlee and Hitmonchan are looking for companions.");
			ileana.getPostItemDialogue().add("Go on ahead and talk to them.");
			ileana.setMoney(1680);
			ileana.setType("Fighting Master");
			ileana.setName("Ileana");
			
			this.getMovingTrainers().add(ileana); //2
			
			//eddie
			Vector<Pokemon> ebelt = new Vector<Pokemon>();
			ebelt.add(new Pokemon.Grimer().setWildLevel(22));
			ebelt.add(new Pokemon.Muk().setWildLevel(22));
			ebelt.add(new Pokemon.Grimer().setWildLevel(22));
			Trainer eddie = new Trainer.BlackHair(ebelt);
			eddie.createHome(1,8,0,0,"freeze","freeze");
			eddie.setDirectionAndImage(FACESOUTH);
			eddie.getDialogue().add("\"WHY BE NASTY WHY BE DIRTY?\"");
			eddie.getDialogue().add("It's from that Rusko song. Dubstep pumps up my workout.");
			eddie.getDialogue().add("What do you mean those lyrics are wrong? LIAR.");
			eddie.setDefeatDialogue("Oh, it says \"Grimy-nasty-dirty-dirty.\" Shoot.");
			eddie.setMoney(550);
			eddie.setType("Undergrad");
			eddie.setName("Eddie");
			
			this.getMovingTrainers().add(eddie); //3
			
			Vector<Pokemon> fbelt = new Vector<Pokemon>();
			fbelt.add(new Pokemon.Machoke().setWildLevel(26));
			Trainer fred = new Trainer.NiceHair(fbelt);
			fred.createHome(1,4,0,0,"freeze","freeze");
			fred.setStationary(false);
			fred.addDest(3,4);
			fred.addDest(1,4);
			fred.setFirstDest();
			fred.getDialogue().add("No one squats more than me.");
			fred.getDialogue().add("NOBODY.");
			fred.setDefeatDialogue("Too much weight! I need a spotter!");
			fred.setMoney(650);
			fred.setType("Gym Addict");
			fred.setName("Fred");
			this.getMovingTrainers().add(fred); //4
			
			//beth
			Vector<Pokemon> btbelt = new Vector<Pokemon>();
			btbelt.add(new Pokemon.Mankey().setWildLevel(23));
			btbelt.add(new Pokemon.Nidorina().setWildLevel(23));
			Trainer beth= new Trainer.BlackGirl(btbelt);
			beth.createHome(10,4,0,0,"freeze","freeze");
			beth.setStationary(false);
			beth.addDest(11,9);
			beth.addDest(10,4);
			beth.setFirstDest();
			beth.getDialogue().add("Move! I'm doing Zumba, and you're cramping my style.");
			beth.setDefeatDialogue("Whoa, so much groove.");
			beth.setMoney(345);
			beth.setType("Zumba Enthusiast");
			beth.setName("Beth");
			
			this.getMovingTrainers().add(beth);	//6
			
			Trainer item = new Trainer.ItemObject(new Item.Zinc());
			item.createHome(13, 2);
			this.getMovingTrainers().add(item);	//7
			
			//Non fighting trainers.
			
			Trainer textbike1 = new Trainer.Text();
			textbike1.createHome(12, 3);
			textbike1.getDialogue().add("He is sweating quite a bit.");
			
			Trainer textbike2 = new Trainer.Text();
			textbike2.createHome(12, 4);
			textbike2.getDialogue().add("She's reading a Brown Noser while biking.");

			this._movingTrainers.add(textbike1);
			this._movingTrainers.add(textbike2);
			
		}
		catch(IOException ioe){
			
		}

	}

	
	public void scanForAllEvents(){
		this.standardTrainerScan(7);
	}
	public void loadAllEvents(){
		this.standardTrainerLoad(7);
	}
	
	public void createBaseRoom(){
		
		this.song = M2.VDUB_LOBBY;
		
		this.addTrainers();
		this.loadAllEvents();
		this.addWilds();
		this.PROBABILITY=PokePanel2.CAVE_PROB;
		
		this.xConstant=-16;
		this.yConstant=-18;
		this._mapX=135;
		this._mapY=235;

		this.setBikeAllow(false);
		this.setBattleBG(NewBattleScreen.MEIK);
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Satellite Gym";
		this._roomNum = _gbs.SATELLITE_GYM;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/SatelliteGym/SatelliteGym Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/SatelliteGym/SatelliteGym Over.png"));
				_runner1 = new ImageIcon(this.getClass().getResource("/PokemonFiles/SatelliteGym/Runner1.gif"));
				_runner2 = new ImageIcon(this.getClass().getResource("/PokemonFiles/SatelliteGym/Runner2.gif"));
				_runner3 = new ImageIcon(this.getClass().getResource("/PokemonFiles/SatelliteGym/Runner3.gif"));
				_biker1 = new ImageIcon(this.getClass().getResource("/PokemonFiles/SatelliteGym/Biker1.gif"));
				_biker2 = new ImageIcon(this.getClass().getResource("/PokemonFiles/SatelliteGym/Biker2.gif"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(15,13);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SatelliteGym.cmap"));
		for(int i = 0; i < 13; i++){
			for(int i2 = 0; i2 < 15; i2++){
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
		
		_runner1.paintIcon(this, g2, this._xSpace+233, this._ySpace+101);
		_runner2.paintIcon(this, g2, this._xSpace+233, this._ySpace+141);
		_runner3.paintIcon(this, g2, this._xSpace+233, this._ySpace+181);
		_biker1.paintIcon(this, g2, this._xSpace+221, this._ySpace+61);
		_biker2.paintIcon(this, g2, this._xSpace+221, this._ySpace+41);
		
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		
		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//VDub
 		if((xInd == 4|| xInd ==5) && (yInd == 12)){
 			super.enterRoom(_gbs.VDUB, 1, 24, FACEEAST);
 		}
		
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		if(random < 60.0){
			randomEnemy = _wildPokemon.get(0 + (int)(15*Math.random()));
		}
		else if(random < 100.0){
			randomEnemy = _wildPokemon.get(15 + (int)(12*Math.random()));
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