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
public class Metcalf extends PokePanel2 {
	private BufferedImage _roomO;
	
	public Metcalf(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Metcalf(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		try {
			Trainer one = new Trainer.Scientist(null);
			one.createHome(5, 3, 0,0,"wander","wander");
			one.addDest(4,2);
			one.addDest(8,4);
			one.addDest(5,3);
			one.setFirstDest();
			one.setStationary(false);
			one.getDialogue().add("Feel free to enter the virtual space using the door on the right.");
			one.getDialogue().add("Be aware of wild psychic Pokemon, and keep your grasp on reality.");
			this._movingTrainers.add(one);
			
			Trainer two = new Trainer.NiceHair(null);
			two.createHome(6, 6, 0,0,"","");
			two.setDirectionAndImage(FACESOUTH);
			two.getDialogue().add("Welcome to Metcalf! You're currently in the VENLab lobby.");
			two.getDialogue().add("Here, we study psychic Pokemon using virtual reality.");
			this._movingTrainers.add(two);
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
	public void createBaseRoom(){
		
		this.song = M2.METCALF;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=39;
		this.yConstant=20;
		this._mapX=180;
		this._mapY=295;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Metcalf Lab";
		this._roomNum = 37;
		try{
			if(GameBoyScreen.finishedLoading){
				//SysOut.print("This should not load on startup");
				//System.exit(0);
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/MetcalfLab/MetcalfLab Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/MetcalfLab/MetcalfLab Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(15,10);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Metcalf.cmap"));
		for(int i = 0; i < 10; i++){
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
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Lincoln Field
 		if((xInd == 7) && (yInd == 9)){
 			super.enterRoom(_gbs.LINCOLN_FIELD, 32, 11, FACESOUTH);
 		}
 		//VENLab
 		if((xInd == 12) && (yInd == 1)){
 			super.enterRoom(_gbs.VENLAB, 5, 5, FACENORTH);
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