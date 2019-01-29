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
public class WoolleyRoom extends PokePanel2 {
	
	public WoolleyRoom(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public WoolleyRoom(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		//TIM, who gives you the kitchen key
		try{
		Trainer tim = new Trainer.BlackHair(null);
		tim.createHome(4,5,0,0,"drunk","drunk");
		tim.addDest(7,6);
		tim.addDest(4,5);
		tim.setFirstDest();
		tim.getDialogue().add("Tim: Yo, what up?");
		tim.getDialogue().add("Hm? Actually, Sri doesn't live here anymore.");
		tim.getDialogue().add("But take this, it'll help get you to him.");
		tim.setGift(new Item.KitchenKey());
		tim.getPostItemDialogue().add("I found it on the floor last time we had dinner.");
		
		Trainer books1 = new Trainer.Text();
		books1.createHome(11, 2);
		books1.getDialogue().add("This side of the shelf is full of Applied Math books.");
		books1.getDialogue().add("\"Numerical Linear Algebra and Applications.\"");
		books1.getDialogue().add("Lame.");
		
		Trainer books2 = new Trainer.Text();
		books2.createHome(12, 2);
		books2.getDialogue().add("This side of the shelf is full of Neuroscience books.");
		books2.getDialogue().add("\"Fundamentals of Computational Neuroscience.\"");
		books2.getDialogue().add("Hmm.");
		
		this.getMovingTrainers().add(tim);
		this.getMovingTrainers().add(books1);
		this.getMovingTrainers().add(books2);
		
		}
		catch(IOException ioe){}
		
	}
	
	public void scanForAllEvents(){
		this.standardTrainerScan(1);
	}
	public void loadAllEvents(){
		this.standardTrainerLoad(1);
	}

	public void createBaseRoom(){
		
		this.song = M2.VDUB_LOBBY;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=0;
		this.yConstant=3;
		this._mapX=135;
		this._mapY=235;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Room 103";
		this._roomNum = 53;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/WoolleyRoom/WoolleyRoom.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(14,9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WoolleyRoom.cmap"));
		for(int i = 0; i < 9; i++){
			for(int i2 = 0; i2 < 14; i2++){
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
		//Hall
 		if((xInd == 8) && (yInd == 1)){
 			super.enterRoom(_gbs.WOOLLEY_HALL, 3, 0, FACESOUTH);
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