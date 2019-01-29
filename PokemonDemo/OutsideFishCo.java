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

import PokemonDemo.BioMed1st.TeleportTimer;


@SuppressWarnings("serial")
public class OutsideFishCo extends PokePanel2 {
	private BufferedImage car, over;
	public static int carX=0;
	public static Timer busTimer;
	public int busTick=0;
	public static boolean entering=false, leaving=false;
	public final int BOUNCER=0, TM44=1, RARE=2;
	PokePanel2 _this;
	
	public OutsideFishCo(GameBoyScreen gbs){
		super(gbs);
		_this=this;
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public OutsideFishCo(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		_this=this;
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size()!=0){
			if(this.getCheckList().get(0)==1){
				Trainer bouncer = this.getMovingTrainers().get(BOUNCER);
				bouncer.setLOS(0);
				bouncer.getDialogue().clear();
				bouncer.getDialogue().add("........Go right ahead...McLovin.");
				this.getCheckList().set(0, 1);
			}
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(TM44).defeat();
			}
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(RARE).defeat();
			}
		}
	}
	
	public void scanForAllEvents(){
		//leave event 0 alone, thats for bouncer.
		if(this.getMovingTrainers().get(TM44).isDefeated()){
			this.getCheckList().set(1, 1);
		}
		if(this.getMovingTrainers().get(RARE).isDefeated()){
			this.getCheckList().set(2, 1);
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try{
				Trainer bouncer = new Trainer.MafiaMagenta(null);
				bouncer.createHome(5, 7, 0,0,"","");
				bouncer.setDirectionAndImage(FACEEAST);
				bouncer.setLOS(2);
				bouncer.getDialogue().add("Only 21+ allowed in here.");
				bouncer.getDialogue().add("(" + _gbs.getPlayer().getPlayerName()+" presents Fake ID.)");
				bouncer.getDialogue().add("................................");
				bouncer.getDialogue().add("...Go right ahead...McLovin.");
				
				this.getMovingTrainers().add(bouncer); //0, final int
				
				Trainer tm44 = new Trainer.ItemObject(new Item.TM44_Rest());
				tm44.createHome(1, 8);
				Trainer rareCandy = new Trainer.ItemObject(new Item.RareCandy());
				rareCandy.createHome(7, 7);
				this.getMovingTrainers().add(tm44);//1, final int
				this.getMovingTrainers().add(rareCandy);//2, final int
				
			}
			catch(IOException ioe){}
			
		
	}

	public void createBaseRoom(){
		
		this.song = M2.OUTSIDE_FISHCO;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=110;
		this.yConstant=60;
		this._mapX=120;
		this._mapY=325;

		this.setBikeAllow(true);
		this._outdoors=true;
		
		BusTimer bus = new BusTimer();
		busTimer = new Timer(25, bus);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "FishCo Entrance";
		this._roomNum = _gbs.OUTSIDE_FISHCO;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/OutsideFishCo/OutsideFishCo Background.png"));
			car = ImageIO.read(this.getClass().getResource("/PokemonFiles/OutsideFishCo/OutsideFishCo CarOver.png"));
			over = ImageIO.read(this.getClass().getResource("/PokemonFiles/OutsideFishCo/OutsideFishCo BlackOver.png"));
			
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(9,14);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/OutsideFishCo.cmap"));
		for(int i = 0; i < 14; i++){
			for(int i2 = 0; i2 < 9; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
public class BusTimer implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			busTick++;
			if(entering){	
				if(busTick == 1){
					_busy=true;
					carX=100;
				}
				
				//Tick every 2 ms.
				if(busTick>1 && busTick<=100){
						carX--;
				}
				
				//There are 58 total ticks.
				if(busTick == 101){
					busTimer.stop();
					_busy=false;
					busTick = 0;
					carX=0;
					_this.Up(); 
					_this.Up();
					_this.repaint();
					entering=false;
					leaving=false;
				}
			}
			else if(leaving){
				if(busTick == 1){
					_busy=true;
					carX=0;
					M2.playFX(M2.DOOR);
				}
				
				//Tick every 2 ms.
				if(busTick>1 && busTick<=100){
						carX--;
				}
				
				if(busTick==75){
					_this._fadeToBlack.start();
				}
				
				//There are 58 total ticks.
				if(busTick == 101){
					busTimer.stop();
					_busy=false;
					busTick = 0;
					carX=0;
					_this.repaint();
					entering=false;
					leaving=false;
					_this.enterRoom(_gbs.OUTSIDE_BARHOL, 15, 18, FACESOUTH,false);
				}
			}
			
			
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		if(carX==0){
			this.drawPlayer(g2);
		}
		
		g2.drawImage(car, null, this._xSpace+carX, this._ySpace);
		g2.drawImage(over, null, this._xSpace, this._ySpace);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//FISHCO
 		if((xInd == 4) && (yInd == 6)){
 			super.enterRoom(_gbs.FISHCO, 9, 10, FACENORTH);
 		}
		
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		//Outside BH
 		if((xInd == 4) && (yInd == 9)){
 			leaving=true;
 			busTimer.start();
 		}
	}
	
	public void Coord(){
		super.Coord();
		SysOut.print("OUTSIDE FISH CO - FLASH RECEIVED!!!");
		_gbs.getPlayer().getAllItems().get(66).setRemaining(1);
	}
	

	public void A_Button(){
		if(_NPCpage==3 && _interruptedTrainer==BOUNCER && this.getMovingTrainers().get(_interruptedTrainer).isInterrupted()){
			Trainer bouncer = this.getMovingTrainers().get(BOUNCER);
			bouncer.setLOS(0);
			bouncer.getDialogue().clear();
			bouncer.getDialogue().add("...Go right ahead...McLovin.");
			this.getCheckList().set(0, 1);
		}
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}

		super.A_Button();
	}
}