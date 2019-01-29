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
import javax.swing.ImageIcon;
import javax.swing.Timer;

import PokemonDemo.BioMed1st.TeleportTimer;


@SuppressWarnings("serial")
public class AlumnaeHall extends PokePanel2 {
	private BufferedImage /*_roomSource,*/ _roomO, _roomDark, _blue, _red, _green, _yellow, _lights;
	private boolean _partyON=false;
	private boolean drugged=false;
	private Timer _lightsTimer;
	private int _lightsTick=0;
	private PokePanel2 _this;
	private final int DJ=1;
	
	public AlumnaeHall(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(10);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public AlumnaeHall(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(10);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
			
		try{
			
			Trainer dealer = new Trainer.ShadyGlasses(null);
			dealer.createHome(1, 5,0,0,"avoid","avoid");
			dealer.setDirectionAndImage(FACEWEST);
			dealer.setLOS(12);
			if(_partyON){
				dealer.getDialogue().add("Sick beats man. Here, take this!");
				dealer.setGift(new Item.SuggsPotion());
				dealer.getPostItemDialogue().add("Whoa, you got some on your hand.");
				dealer.getPostItemDialogue().add("...you don't look too great. You alright?");
			}
			else{
				dealer.getDialogue().add("Whoa....I am rolling boulders right now.");
				dealer.getDialogue().add("You lookin' to get faded?");
				dealer.getDialogue().add("If you can get the party started, come and see me.");
				dealer.getDialogue().add("I've got a rare potion for you.");
			}
			if(drugged){
				dealer.setVanishing(true);
				dealer.defeat();
			}
			this.getMovingTrainers().add(dealer); //0
			
			Vector<Pokemon> djBelt = new Vector<Pokemon>();
			djBelt.add(new Pokemon.Alakazam().setWildLevel(33));
			Trainer dj = new Trainer.BlackDude(djBelt);
			dj.createHome(7,1,0,0,"freeze","freeze");
			dj.setDirectionAndImage(FACENORTH);
			dj.getDialogue().add("Get the party started? Says who?");
			dj.getDialogue().add("SexPowerGod starts when I decide.");
			dj.setDefeatDialogue("Alright, I'll start up my set.");
			dj.getPostBattleDialogue().add("Hope you're enjoying the party!");
			dj.setMoney(1100);
			dj.setType("DJ");
			dj.setName("Abe");
			this._movingTrainers.add(dj); //1 ,first event based person.
			

			

			if(_partyON){
				Vector<Pokemon> oneBelt = new Vector<Pokemon>();
				oneBelt.add(new Pokemon.Machop().setWildLevel(31));
				oneBelt.add(new Pokemon.Mankey().setWildLevel(31));
				oneBelt.add(new Pokemon.Primeape().setWildLevel(31));
				Trainer oneF = new Trainer.NakedGuy(oneBelt);
				oneF.createHome(4,6,0,0,"avoid","avoid");
				oneF.setLOS(3);
				oneF.setStationary(false);
				oneF.addDest(2,8);
				oneF.addDest(4,6);
				oneF.setFirstDest();
				oneF.getDialogue().add("WOO, PANTS OFF DANCE OFF!");
				oneF.setDefeatDialogue("Mmm. Embarrasing.");
				oneF.setMoney(775);
				oneF.setType("Party Boy");
				oneF.setName("Thrilliam");
				this.getMovingTrainers().add(oneF);
	
				Vector<Pokemon> twoBelt = new Vector<Pokemon>();
				twoBelt.add(new Pokemon.Beedrill().setWildLevel(32));
				twoBelt.add(new Pokemon.Dewgong().setWildLevel(32));
				Trainer twoF = new Trainer.BrownMediumHair(twoBelt);
				twoF.createHome(3,7,0,0,"drunk","drunk");
				twoF.setLOS(3);
				twoF.setStationary(false);
				twoF.addDest(7,6);
				twoF.addDest(3,7);
				twoF.setFirstDest();
				twoF.getDialogue().add("I can't find my shoes.");
				twoF.getDialogue().add("Oh wait, I entered barefoot.");
				twoF.setDefeatDialogue("Wait, that's not right, I have one shoe on...");
				twoF.setMoney(800);
				twoF.setType("Rather Intoxicated");
				twoF.setName("Monica");
				this.getMovingTrainers().add(twoF);
				
				Vector<Pokemon> threeBelt = new Vector<Pokemon>();
				threeBelt.add(new Pokemon.Vaporeon().setWildLevel(36));
				Trainer threeF = new Trainer.NakedGirl(threeBelt);
				threeF.createHome(9,9,0,0,"drunk","drunk");
				threeF.setLOS(3);
				threeF.setStationary(false);
				threeF.addDest(10,8);
				threeF.getDialogue().add("I am expressing myself and my comfort of my body.");
				threeF.getDialogue().add("Take that Bill O'Reilly!");
				threeF.setDefeatDialogue("Looks like someone's compensating for something.");
				threeF.setMoney(900);
				threeF.setType("Carefree");
				threeF.setName("Jen");
				this.getMovingTrainers().add(threeF);
				
				Vector<Pokemon> fourBelt = new Vector<Pokemon>();
				fourBelt.add(new Pokemon.Arbok().setWildLevel(31));
				fourBelt.add(new Pokemon.Mankey().setWildLevel(31));
				fourBelt.add(new Pokemon.Pinsir().setWildLevel(31));
				Trainer fourF= new Trainer.NakedGuy2(fourBelt);
				fourF.createHome(6,10,0,0,"drunk","drunk");
				fourF.setLOS(3);
				fourF.setStationary(false);
				fourF.addDest(8,4);
				fourF.addDest(6,10);
				fourF.setFirstDest();
				fourF.getDialogue().add("Wanna play strip poker?");
				fourF.getDialogue().add("I've got nothing to lose.");
				fourF.setDefeatDialogue("Except for my dignity.");
				fourF.setMoney(775);
				fourF.setType("Body Hair");
				fourF.setName("Brad");
				this.getMovingTrainers().add(fourF);
				
				
				Vector<Pokemon> fiveBelt = new Vector<Pokemon>();
				fiveBelt.add(new Pokemon.Mr_Mime().setWildLevel(15));
				fiveBelt.add(new Pokemon.Ditto().setWildLevel(36));
				Trainer fiveF= new Trainer.GreenHat(fiveBelt);
				fiveF.createHome(13,9,0,0,"wander","wander");
				fiveF.setLOS(3);
				fiveF.setStationary(false);
				fiveF.addDest(5,1);
				fiveF.addDest(13,5);
				fiveF.addDest(5,6);
				fiveF.addDest(7,7);
				fiveF.addDest(13,9);
				fiveF.setFirstDest();
				fiveF.getDialogue().add("There's a unicorn standing next to you.");
				fiveF.setDefeatDialogue("I'm really craving dumplings right now.");
				fiveF.setMoney(925);
				fiveF.setType("\"It's probably 'shrooms\"");
				fiveF.setName("Evan");
				this.getMovingTrainers().add(fiveF);

				Trainer mk1 = new Trainer.Swimmer(null);
				mk1.createHome(8,8);
				mk1.setDirectionAndImage(FACENORTH);
				mk1.setStationary(false);
				mk1.addDest(8, 9);
				mk1.setFirstDest();
				mk1.getDialogue().add("Yo, leave us alone!");
				
				this.getMovingTrainers().add(mk1);
				
				Trainer mk2 = new Trainer.Swimmer(null);
				mk2.createHome(8,9);
				mk2.setDirectionAndImage(FACESOUTH);
				mk2.addDest(8, 8);
				mk2.setFirstDest();
				mk1.getDialogue().add("Why you staring? Find your own hookup.");
				
				this.getMovingTrainers().add(mk2);
				
			
			}
			else{
				Trainer oneF = new Trainer.GlassesGuy1(null);
				oneF.createHome(4,6,0,0,"avoid","avoid");
				oneF.setDirectionAndImage(FACEEAST);
				oneF.getDialogue().add("This is lame. We need music and lights.");
				this.getMovingTrainers().add(oneF);
	
				Trainer twoF = new Trainer.BrownMediumHair(null);
				twoF.createHome(3,7,0,0,"drunk","drunk");
				twoF.setDirectionAndImage(FACENORTH);
				twoF.getDialogue().add("The DJ is refusing to play music. Can you talk to him?");
				this.getMovingTrainers().add(twoF);
				
				
				Trainer threeF = new Trainer.DirtyBlondeGirl(null);
				threeF.createHome(9,9,0,0,"drunk","drunk");
				threeF.setDirectionAndImage(FACESOUTH);
				threeF.getDialogue().add("Well, while we're here, let's play bowl game?");
				this.getMovingTrainers().add(threeF);
				
				
				Trainer fourF= new Trainer.BlueBro(null);
				fourF.createHome(6,10,0,0,"drunk","drunk");
				fourF.setDirectionAndImage(FACEWEST);
				fourF.getDialogue().add("Wow, I paid $20 for this? We need some dance beats.");
				this.getMovingTrainers().add(fourF);
				
				
				Trainer fiveF= new Trainer.GreenHat(null);
				fiveF.createHome(13,9,0,0,"wander","wander");
				fiveF.setDirectionAndImage(FACENORTH);
				fiveF.getDialogue().add("Man, where is the music?!");
				fiveF.getDialogue().add("Yaaawn...I'm already getting sleepy.");
				this.getMovingTrainers().add(fiveF);
				
			}
			
		}
		catch(IOException ioe){}
		
	}

	public void scanForAllEvents(){
		if(_gbs.getPlayer().getItem(Item.SUGGS_POTION).getRemaining()>0){
			drugged=true;
		}
		if(_partyON){
			this.getCheckList().set(0, 1);
		}
		else{
			this.getCheckList().set(0, 0);
		}
		this.standardTrainerScan(1,this.getMovingTrainers().size());
		

	}
	public void loadAllEvents(){
		if(this.getCheckList()!=null){
			if(this.getCheckList().get(0)==1){
				this._partyON=true;
			}
			else{
				this._partyON=false;
			}
		}
		this.standardTrainerLoad(1, this.getMovingTrainers().size());
		
		
	}
	
	public void createBaseRoom(){
		if(_gbs.getPlayer().getItem(Item.SUGGS_POTION).getRemaining()>0){
			drugged=true;
		}
		else{
			drugged=false;
		}
		this.setBattleBG(NewBattleScreen.FISHCO);
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-14;
		this.yConstant=27;
		this._mapX=135;
		this._mapY=235;
		_this=this;

		LightsTimer lights = new LightsTimer();
		_lightsTimer = new Timer(400, lights);
		
		
		if(_partyON){
			this.song = M2.SPG;
			_lightsTick=0;
			_lightsTimer.start();
		}
		else{
			this.song = M2.SILENCE;
			_lightsTimer.stop();
			_lightsTick=0;
		}
		
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Alumnae Hall";
		this._roomNum = 56;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/AlumnaeHall/AlumnaeHall Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/AlumnaeHall/AlumnaeHall Over.png"));
				_roomDark = ImageIO.read(this.getClass().getResource("/PokemonFiles/AlumnaeHall/AlumnaeHall Dark.png"));
				_red = ImageIO.read(this.getClass().getResource("/PokemonFiles/AlumnaeHall/Red.png"));
				_blue = ImageIO.read(this.getClass().getResource("/PokemonFiles/AlumnaeHall/Blue.png"));
				_green = ImageIO.read(this.getClass().getResource("/PokemonFiles/AlumnaeHall/Green.png"));
				_yellow = ImageIO.read(this.getClass().getResource("/PokemonFiles/AlumnaeHall/Yellow.png"));
				
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(15,13);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/AlumnaeHall.cmap"));
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
		g2.drawImage(_roomDark, null, this._xSpace, this._ySpace);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		
		if(_partyON || _lightsTimer.isRunning()){
			g2.drawImage(_lights, null, this._xSpace, this._ySpace);
		}

		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Pembroke
 		if(((xInd==2||xInd==3||xInd==11||xInd==12) && yInd==12)){
 			super.enterRoom(_gbs.PEMBROKE_BACK, 5, 7, FACENORTH);
 		}
 		//Pembroke Roof
 		if(((xInd==10) && yInd==0)){
 			super.enterRoom(_gbs.PEMBROKE, 16, 2, FACENORTH);
 		}
		
	}
	public void afterBattle(){
		super.afterBattle();
		if(this._interruptedTrainer == DJ){
			this._partyON=true;
			M2.playBG(M2.SPG);
			_lightsTick=0;
			_lightsTimer.start();
			this.scanForAllEvents();
			this.createBaseRoom();
		}
	}
	
public class LightsTimer implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			_lightsTick++;
			
			if(_lightsTick == 1){
				_lights=_red;
			}
			
			if(_lightsTick == 2){
				_lights=_blue;
			}
			
			if(_lightsTick == 3){
				_lights=_green;
			}
			
			if(_lightsTick == 4){
				_lights=_yellow;
				_lightsTick=0;
			}
			//_this.repaint();
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
	
	public void Up(){
		if(!_menuVisible && drugged && NewTurnAction.Bernoulli(80)=="Yes" && this._room._roomGrid[_xIndex][_yIndex]!='D'){
			super.Left();
		}
		else{super.Up();}
	}
	public void Down(){
		if(!_menuVisible && drugged && NewTurnAction.Bernoulli(80)=="Yes" && this._room._roomGrid[_xIndex][_yIndex]!='D'){
			super.Right();
		}
		else{super.Down();}
	}
	public void Left(){
		if(!_menuVisible && drugged && NewTurnAction.Bernoulli(80)=="Yes"){
			super.Down();
		}
		else{super.Left();}
	}
	public void Right(){
		if(!_menuVisible && drugged && NewTurnAction.Bernoulli(80)=="Yes"){
			super.Up();
		}
		else{super.Right();}
	}
	
	
	
	
	
	
}