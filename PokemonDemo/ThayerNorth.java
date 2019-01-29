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
public class ThayerNorth extends PokePanel2 {
	private BufferedImage _roomO;
	private ImageIcon v,h,r;
	
	public ThayerNorth(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public ThayerNorth(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				/*NEW TRAINER TEMPLATE
				 * 		*/
		Vector<Pokemon> belt = new Vector<Pokemon>();
		Trainer s= new Trainer.Ambika(belt);
		/*
		.createHome(0,0,0,0,"freeze","freeze");
		.setLOS();
		.setStationary();
		.addDest();
		.getDialogue().add();
		.getDialogue().add();
		.setDefeatDialogue();
		.setMoney();
		.setType();
		.setName();
		this.getMovingTrainers().add();
		*/
				
				/*
				 * 		//A 	TM 03
		//B 	Calcium
		//A 	HP Up
		//B 	X Accuracy
		//A 	Card Key
		//B 	Protein
		//A 	Carbos
		//B 	Rare Candy
		//C 	TM 26
		//A 	TM 36*/
				

		Trainer tol = new Trainer.Text();
		tol.createHome(21, 25);
		tol.getDialogue().add("\"Toledo: Pizza In a Cone!\"");
		tol.getDialogue().add("Hmm, guess it's out of business..");
		this.getMovingTrainers().add(tol);
		
				//22,28, item
				//23,34 item
				//8, 37 location of crazy man
				//14,37 locaiton of crazy man competitor
				//12,25 location of avon block
				//15,46 item
				//24,45 item
				//26,56 item
				//

		
		//Last
		Trainer cop = new Trainer.Cop(null);
		cop.createHome(6, 54);
		cop.setDirectionAndImage(FACEWEST);
		cop.getDialogue().add("Sorry, you can't go this way. Buses only.");
		this.getMovingTrainers().add(cop);
		if(Waterfire.waterfireDone){
			cop.setVanishing(true);
			cop.defeat();
		}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.THAYER;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=1697;
		this.yConstant=-15;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=134;
		this._mapY=283;
		this._mapX=182;
		this._mapY=321;
		this._outdoors=true;
		this.setBikeAllow(true);
		this.PROBABILITY=PokePanel2.CAVE_PROB;

		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Thayer St. North";
		this._roomNum = _gbs.THAYER_NORTH;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/Route2.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/Route2O.png"));
				v = new ImageIcon(this.getClass().getResource("/PokemonFiles/Route2/floodV.gif"));
				h = new ImageIcon(this.getClass().getResource("/PokemonFiles/Route2/floodH.gif"));
				r = new ImageIcon(this.getClass().getResource("/PokemonFiles/Route2/rain8080.gif"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(28,67);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/ThayerNorth.cmap"));
		for(int i = 0; i < 67; i++){
			for(int i2 = 0; i2 < 28; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		for (int i =0; i<16;i++){
			v.paintIcon(this, g2, this._xSpace+this.xConstant+331, this._ySpace+this.yConstant+(i+1)*60);
		}
		for (int i =0; i<9;i++){
			h.paintIcon(this, g2, this._xSpace+this.xConstant-i*28+301, this._ySpace+this.yConstant+1050);
		}
		this.drawPlayer(g2);
	
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawOptimalImage(g2, _roomO);

		for (int i =0; i<5;i++){
			for (int j =0; j<5;j++){
				r.paintIcon(this, g2, 80*i,80*j);
			}
		}

		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Bookstore
 		if((xInd == 12 || xInd == 9) && (yInd == 36)){
 			super.enterRoom(_gbs.BOOKSTORE, 18, 12, FACENORTH);
 		}
		//BusTunnel
 		if((xInd == 3) && ((yInd<=55) &&(yInd >=53))){
 			super.enterRoom(_gbs.BUS_TUNNEL, 29,3, FACEWEST);
 		}
 		//metro
 		if((xInd == 22) && (yInd == 53)){
 			super.enterRoom(_gbs.METRO_MART, 0, 1, FACEEAST);
 		}
 		//24
 		if((xInd == 21) && (yInd == 31)){
 			super.enterRoom(_gbs.TEDESCHI, 0, 1, FACEEAST);
 		}
 		//k and c
 		if((xInd == 21) && (yInd == 20)){
 			super.enterRoom(_gbs.KABOB_AND_CURRY, 0, 1, FACEEAST);
 		}
 		//nice slice
 		if((xInd == 21) && (yInd == 17)){
 			super.enterRoom(_gbs.NICE_SLICE, 0, 1, FACEEAST);
 		}
 		//antonios
 		if((xInd == 14) && (yInd == 18)){
 			super.enterRoom(_gbs.ANTONIOS, 0, 1, FACEWEST);
 		}
	}
	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		
		//near via
		if(xInd == 11 && WEST){
			if(yInd==12){
				super.enterRoomSeamless(_gbs.PEMBROKE, 36, 11, FACEWEST);
			}
			else{
				super.enterRoomSeamless(_gbs.ROUTE_3, xInd+21, yInd-12, FACEWEST);
			}
		}
		//above bookstore
		if(xInd == 8 && yInd<35 && WEST){
			super.enterRoomSeamless(_gbs.ROUTE_3, xInd+21, yInd-12, FACEWEST);
		}
		//Below Bookstore
		if((xInd == 7&& WEST)){
			super.enterRoomSeamless(_gbs.ROUTE_3, xInd+21, yInd-12, FACEWEST);
		}
		//Hallway
		if(yInd == 46 && SOUTH){
			super.enterRoomSeamless(_gbs.ROUTE_3, xInd+21, yInd-12, FACESOUTH);
		}
		//Thayer
		if(yInd == 55 && SOUTH){
			super.enterRoomSeamless(_gbs.THAYER_SOUTH, xInd-16+10, 1, FACESOUTH);
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