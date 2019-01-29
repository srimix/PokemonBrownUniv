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
public class RedHouse extends PokePanel2 {
	private BufferedImage _roomSource, _roomO;
	
	public RedHouse(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public RedHouse(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer RedHouse1 = new Trainer.GraySkirt(null);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.REDHOUSE;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-16;
		this.yConstant=-3;
		this._mapX=171;
		this._mapY=274;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "RedHouse";
		this._roomNum = 41;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/RedHouse/RedHouse Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/RedHouse/RedHouse Over.png"));
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(11,7);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/RedHouse.cmap"));
		for(int i = 0; i < 7; i++){
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
		//Route 3
 		if((xInd == 5) && (yInd == 6)){
 			super.enterRoom(_gbs.ROUTE_3, 11, 33, FACESOUTH);
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