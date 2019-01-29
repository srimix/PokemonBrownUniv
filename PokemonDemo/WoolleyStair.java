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
public class WoolleyStair extends PokePanel2 {
	private BufferedImage  _roomO;
	
	public WoolleyStair(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public WoolleyStair(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void scanForAllEvents(){
		this.standardTrainerScan(this.getMovingTrainers().size());
	}
	public void loadAllEvents(){
		this.standardTrainerLoad(this.getMovingTrainers().size());
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		Trainer item;
		try {
			item = new Trainer.ItemObject(new Item.MaxRevive());
			item.createHome(1,2);
			this.getMovingTrainers().add(item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.VDUB_LOBBY;;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-18;
		this.yConstant=-18;
		this._mapX=135;
		this._mapY=235;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "EmWool Stairs";
		this._roomNum = 51;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/WoolleyStair/WoolleyStair Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/WoolleyStair/WoolleyStair Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(7,5);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WoolleyStair.cmap"));
		for(int i = 0; i < 5; i++){
			for(int i2 = 0; i2 < 7; i2++){
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

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//VDub
 		if((xInd == 5) && (yInd == 1)){
 			super.enterRoom(_gbs.VDUB, 7, 20, FACESOUTH);
 		}
 		//Woolley Hall
 		if((xInd == 5) && (yInd == 3)){
 			super.enterRoom(_gbs.WOOLLEY_HALL, 0, 1, FACEEAST);
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