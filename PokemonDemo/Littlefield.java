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
public class Littlefield extends PokePanel2 {
	private BufferedImage _roomO;
	private final int BIKE = 0;
	
	public Littlefield(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Littlefield(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer owner = new Trainer.Text();
				owner.createHome(2,3,0,0,"","");
				owner.setGift(new Item.Bicycle());
				owner.getDialogue().clear();
				owner.getDialogue().add("Welcome to Bikes at Brown! We've opened a new branch in Littlefield.");
				owner.getDialogue().add("Normally, we would rent out the bikes to you, but we need publicity.");
				owner.getDialogue().add("Will you help advertise our new location by riding around campus?");
				owner.getPostItemDialogue().clear();
				owner.getPostItemDialogue().add("You can access the bike from your item menu.");
				owner.getPostItemDialogue().add("But you can also use the shortcut \"C\" button on your keyboard.");
				owner.setVanishing(false);
				owner.setDefeatAfterItem(true);
				
				this.getMovingTrainers().add(owner);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.LITTLEFIELD;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=34;
		this.yConstant=11;
		this._mapX=152;
		this._mapY=299;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Littlefield";
		this._roomNum = 40;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Littlefield/Littlefield Background.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(BIKE).defeatAndPostItemize();
			}
		}
	}
	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(BIKE).isDefeated()){
			this.getCheckList().set(0, 1);
			SysOut.print("here.");
		}
	}

	public void createGrid(){
		this._room = new Room(11,9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Littlefield.cmap"));
		for(int i = 0; i < 9; i++){
			for(int i2 = 0; i2 < 11; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		
		this.drawBox(g2);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Lincoln Field
 		if((xInd == 7) && (yInd == 1)){
 			super.enterRoom(_gbs.LINCOLN_FIELD, 7, 37, FACESOUTH);
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