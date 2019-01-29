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
public class Spiritus extends PokePanel2 {
	
	public Spiritus(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Spiritus(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
		try{
			this._movingTrainers = new Vector<Trainer>();

			this._thisMartContains.clear();
			this._thisMartContains.add(73); // vodka -potion
			this._thisMartContains.add(74); // coffee- awake
			this._thisMartContains.add(75); // tonic-antidote
			this._thisMartContains.add(76); // jager-prz heal
			this._thisMartContains.add(77); // creme - burn heal
			this._thisMartContains.add(78); // moonshine - iceheal
			this._thisMartContains.add(79); // smirnoff
			
			Trainer mart = new Trainer.MartMenu("Spiritus", this);
			mart.createHome(9, 4, 0,0,"","");
			this.getMovingTrainers().add(mart); //0
			
			Trainer shopper = new Trainer.GreenHat(null);
			shopper.createHome(2, 4, 0,0,"wander","wander");
			shopper.addDest(5, 5);
			shopper.addDest(2,4);
			shopper.setFirstDest();
			shopper.getDialogue().add("My bros want me to get them 30's for the weekend...");
			shopper.getDialogue().add("but secretly all I want are Smirnoff Ices.");
			shopper.setStationary(false);
			
			this.getMovingTrainers().add(shopper); //1
		}
		catch(IOException ioe){}
	}

	public void createBaseRoom(){
		this.setMartVisible(false);
		this.song = M2.SPIRITUS;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=2;
		this.yConstant=0;
		this._mapX=135;
		this._mapY=235;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Spiritus";
		this._roomNum = 54;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Spiritus/Spiritus.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(11,9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Spiritus.cmap"));
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

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Pembroke
 		if((xInd == 6 || xInd ==7) && (yInd == 8)){
 			super.enterRoom(_gbs.PEMBROKE, 32, 8, FACEWEST);
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