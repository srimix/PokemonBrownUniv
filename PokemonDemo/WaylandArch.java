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
public class WaylandArch extends PokePanel2 {
	private BufferedImage _roomOpen, _roomClosed;
	private Graphics2D _g2;
	private boolean _doorOpen=true;
	private Trainer _resLifeGuy;
	public WaylandArch(GameBoyScreen gbs){
		super(gbs);
		this.createBaseRoom();
		_xSpace = -28;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
		
		try {
			_resLifeGuy = new Trainer.GlassesGuy1(null);
			//This is the initial dialogue.
			_resLifeGuy.getDialogue().add("Hey! You're a first-year in Keeney Hall, right?");
			_resLifeGuy.getDialogue().add("I have a package for your RA. Can you please drop it off for me?");
			_resLifeGuy.getDialogue().add(_gbs.getPlayer().getPlayerName()+ " received RA Package!");
			
			//By default, she faces down when you enter the room. This should reset
			//if you ever leave the room and return.
			_resLifeGuy.setCurrentImage(_resLifeGuy.getFrontImage());
			_resLifeGuy.createHome(4,0,0,2, "vert", "vert");
			_resLifeGuy.setStationary(true);
			_resLifeGuy.setVanishing(true);
			_resLifeGuy.setPause(false);
			_resLifeGuy.defeat();
			
			this._movingTrainers = new Vector<Trainer>();
			this._movingTrainers.add(_resLifeGuy);
			_gbs.startNPCTimer();
			
			Trainer map = new Trainer.Map(this);
			map.createHome(6, 0, 0, 0, "", "");
			map.setStationary(true);
			Trainer map2 = new Trainer.Map(this);
			map2.createHome(7, 0, 0, 0, "", "");
			map2.setStationary(true);
			
			this._movingTrainers.add(map);
			this._movingTrainers.add(map2);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WaylandArch(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		this.createBaseRoom();
		try {
			_resLifeGuy = new Trainer.GlassesGuy1(null);	
			//By default, she faces down when you enter the room. This should reset
			//if you ever leave the room and return.
			_resLifeGuy.setCurrentImage(_resLifeGuy.getFrontImage());
			_resLifeGuy.createHome(4,0,0,2, "vert", "vert");
			//_resLifeGuy.setYSpace(_resLifeGuy.getYSpace()+2);
			_resLifeGuy.setStationary(true);
			_resLifeGuy.setVanishing(true);
			_resLifeGuy.setPause(false);
			_resLifeGuy.defeat();
			
			this._movingTrainers =new Vector<Trainer>();
			this._movingTrainers.add(_resLifeGuy);
			_gbs.startNPCTimer();
			
			Trainer map = new Trainer.Map(this);
			map.createHome(6, 0, 0, 0, "", "");
			map.setStationary(true);
			Trainer map2 = new Trainer.Map(this);
			map2.createHome(7, 0, 0, 0, "", "");
			map2.setStationary(true);
			
			this._movingTrainers.add(map);
			this._movingTrainers.add(map2);
			SysOut.print("rom?");

		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addTrainers(){
		if(_gbs.getPlayer().isGymLeaderDefeated(1)){
			try{
				Trainer becca = new Trainer.BlackGirl(null);
				becca.createHome(5, 1, 0, 0, "freeze", "freeze");
				becca.addDest(6, 1);
				becca.setFirstDest();
				becca.getDialogue().add("Becca: oooh, ahhh....Rumor has it...");
				becca.getDialogue().add("Do you mind? We're rehearsing.");
				becca.setDirectionAndImage(FACEWEST);
				
				Trainer jigg = new Trainer.Jigglypuff(this);
				jigg.createHome(6, 1, 0, 3, "freeze", "freeze");
				jigg.addDest(5,1);
				jigg.setFirstDest();
				jigg.getDialogue().add("Jigglypuff: Jiiiiigalypuufffff...");
				jigg.getDialogue().add("Jigglypuff is singing to you!");
				jigg.getDialogue().add("It sounds nice, but it's making you kind of sleepy...");
				jigg.setDirectionAndImage(FACEEAST);
				
				this._movingTrainers.add(becca);
				this._movingTrainers.add(jigg);
			}
			catch(IOException ioe){}
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Wayland Arch";
		this._roomNum = 17;
		_textVisible = new boolean[0];
		this.setBikeAllow(true);
		this.xConstant=4;
		this.yConstant=73; //Changed y -1
		this._mapX=144;
		this._mapY=319;
		this.addTrainers();
		
		this.song = M2.WAYLAND_ARCH;
		
		if(_gbs.getPlayer().hasTownMap()){
			_doorOpen=false;
		}
		
		try{
			if(_roomOpen == null){
				_roomOpen = ImageIO.read(this.getClass().getResource("/PokemonFiles/WaylandArch/WaylandArch.png"));
				_roomClosed = ImageIO.read(this.getClass().getResource("/PokemonFiles/WaylandArch/WaylandArchClosed.png"));
			}
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(10,4);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WaylandArch.cmap"));
		for(int i = 0; i < 4; i++){
			for(int i2 = 0; i2 < 10; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
				System.out.print(_room._roomGrid[i2][i] + " ");
			}
			SysOut.print();
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		_g2 = (Graphics2D) g;
		
		if(_doorOpen){
		_g2.drawImage(_roomOpen, null, this._xSpace, this._ySpace);
		}
		else{
			_g2.drawImage(_roomClosed, null, this._xSpace, this._ySpace);
		}
		this.drawAllTrainers(_g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawPlayer(_g2);

		this.drawAllGenerics(_g2);
	}

	public void enterRoom(int xInd, int yInd){

		//To Wriston Quad
		if((xInd == 8 && yInd == 1)||(xInd == 8 && yInd == 2)){
			super.enterRoom(_gbs.WRISTON_QUAD, 1, 7, FACEEAST);
		}
		
		//To Route 1;
		if((xInd == 0 && yInd == 1)||(xInd == 0 && yInd == 2)){
			super.enterRoom(_gbs.ROUTE_1, 19, 12, FACEWEST);
		}
		
		this.repaint();
	}

	
	public void Right(){
		if(this.SOUTH){super.Right(); return;}
		if((_gbs.getPlayer().getPokedex() != null)){
			_doorOpen=false;
			super.Right();
			return;
		}
				
			if(_resLifeGuy != null && this._xIndex == 3 && !_busy){
				_resLifeGuy.getDialogue().clear();
				
				if(_gbs.getPlayer().getAllItems().get(37).getRemaining()==0){
					_resLifeGuy.getDialogue().add("Hey! You're a first-year in Keeney Hall, right?");
					_resLifeGuy.getDialogue().add("I have a package for your RA. Can you please drop it off for me?");
					try {
						_resLifeGuy.setGift(new Item.SpecialPackage());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					_resLifeGuy.getDialogue().add("Please deliver that package to your Keeney RA!");
				}
				
				_resLifeGuy.unDefeat();
				_resLifeGuy.addDest(this._xIndex+1,this._yIndex); //Set to in front of you.
				this._gbs.getPlayer().faceRight();
				SysOut.print(""+this.getPlayerDirection());
				_resLifeGuy.setFirstDest();
				this._approached=true;
				this._interruptedTrainer=0;
				this._approachTimer.start();

				
	

				this.repaint();
			}
			else{
				super.Right();
			}

		
	}
	

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingNPC()){
			if(!_busy){
				this.completionCheck = false;
			}
			this.repaint();
		}
		else{
			super.A_Button();
		}
	}
}