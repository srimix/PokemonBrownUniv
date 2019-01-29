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
public class WoolleyHall extends PokePanel2 {
	
	public WoolleyHall(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public WoolleyHall(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
	}

	public void createBaseRoom(){
		
		this.song = M2.VDUB_LOBBY;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=3;
		this.yConstant=1;
		this._mapX=135;
		this._mapY=235;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Woolley 1st Floor";
		this._roomNum = 52;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/WoolleyHall/WoolleyHall.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(6,3);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WoolleyHall.cmap"));
		for(int i = 0; i < 3; i++){
			for(int i2 = 0; i2 < 6; i2++){
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
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Stairs
 		if((xInd == 0) && (yInd == 1)){
 			super.enterRoom(_gbs.WOOLLEY_STAIR, 5, 3, FACEWEST);
 		}
 		//Room
 		if((xInd == 3) && (yInd == 0)){
 			super.enterRoom(_gbs.WOOLLEY_ROOM, 8, 1, FACESOUTH);
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