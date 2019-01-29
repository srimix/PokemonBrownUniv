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
public class RitesReason extends PokePanel2 {
	private BufferedImage  _roomO;
	private final int TM45=1;
	
	public RitesReason(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public RitesReason(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer ayoosh = new Trainer.BlackDude(null);
				ayoosh.createHome(3, 3,0,0,"drunk", "drunk");
				ayoosh.addDest(6,5);
				ayoosh.addDest(3, 3);
				ayoosh.setFirstDest();
				ayoosh.setStationary(false);
				ayoosh.getDialogue().add("Ayoosh: I'm practicing my latest spoken word piece.");
				ayoosh.getDialogue().add("\"Confession: I am a terrible hand holder...\"");
				
				//Gives you tm45
				Trainer nicole = new Trainer.GraySkirt(null);
				nicole.createHome(3, 5, 0, 0, "drunk", "drunk");
				nicole.addDest(6, 3);
				nicole.addDest(3, 5);
				nicole.setVanishing(false);
				nicole.setFirstDest();
				nicole.setStationary(false);
				nicole.getDialogue().add("Nicole: My poems send shivers down people's spines.");
				nicole.getDialogue().add("Quite literally. Here, take this!");
				nicole.setGift(new Item.TM45_ThunderWave());
				nicole.getPostItemDialogue().add("That'll stun the enemy and keep them still for you.");
				
				this.getMovingTrainers().add(ayoosh); //0
				
				this.getMovingTrainers().add(nicole); //1, FINAL INT
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(TM45).isDefeated()){
			this.getCheckList().set(0, 1);
		}
	}
	public void loadAllEvents(){
		if(this.getCheckList().size()>0){
			if(this.getCheckList().get(0)==1){
				Trainer tm45= this.getMovingTrainers().get(TM45);
				tm45.defeat();
				tm45.setGift(null);
				tm45.getDialogue().clear();
				tm45.getDialogue().add(tm45.getPostItemDialogue().get(0));
			}
		}
	}
	
	
	public void createBaseRoom(){
		
		this.song = M2.WAYLAND_ARCH;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=5;
		this.yConstant=0;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=175;
		this._mapY=277;
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Rites and Reason";
		this._roomNum = _gbs.RITES_REASON;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/RitesReason/RitesReason Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/RitesReason/RitesReason Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(8,8);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/RitesReason.cmap"));
		for(int i = 0; i < 8; i++){
			for(int i2 = 0; i2 < 8; i2++){
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
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 3
 		if((xInd == 3 || xInd == 4) && (yInd == 7)){
 			super.enterRoom(_gbs.ROUTE_3, 24, 36, FACESOUTH);
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