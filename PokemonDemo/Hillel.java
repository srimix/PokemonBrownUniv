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
public class Hillel extends PokePanel2 {
	private BufferedImage _roomO;
	
	public Hillel(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Hillel(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				/*NEW TRAINER TEMPLATE
				 * 		
		Vector<Pokemon> belt = new Vector<Pokemon>();
		Trainer = new Trainer(belt);
		.createHome(0,0,0,0,"freeze","freeze");
		.setLOS();
		.setStationary();
		.addDest();
		.setFirstDest();
		.getDialogue().add();
		.getDialogue().add();
		.setDirectionAndImage();
		.setDefeatDialogue();
		.setMoney();
		.setType();
		.setName();
		this.getMovingTrainers().add();
		*/
				
				Trainer hillel1 = new Trainer.GraySkirt(null);
				hillel1.createHome(7, 5, -1, 0, "drunk", "drunk");
				hillel1.addDest(6, 8);
				hillel1.addDest(7, 5);
				hillel1.setFirstDest();
				hillel1.setPause(true);
				hillel1.setStationary(false);
				hillel1.getDialogue().clear();
				hillel1.getDialogue().add("Welcome to Brown/RISD Hillel! Feel free to walk around.");
				
				Trainer hillel2 = new Trainer.MaroonHat(null);
				hillel2.createHome(2, 7, 0, 0, "drunk", "drunk");
				hillel2.addDest(1, 4);
				hillel2.addDest(5, 7);
				hillel2.setFirstDest();
				hillel2.setPause(true);
				hillel2.setStationary(false);
				hillel2.getDialogue().clear();
				hillel2.getDialogue().add("Um...there's a Lapras out back that won't leave.");
				hillel2.getDialogue().add("None of us can catch her. We can't catch shellfish Pokemon.");
				hillel2.getDialogue().add("Maybe go see what she thinks of you?");
				
				this._movingTrainers.add(hillel1);
				this._movingTrainers.add(hillel2);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.HILLEL;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=2;
		this.yConstant=18;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=134;
		this._mapY=283;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		//this._pkmnCentX=;
		//this._pkmnCentY=;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Hillel";
		this._roomNum = 21;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Hillel/Hillel Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Hillel/Hillel Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(11,12);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Hillel.cmap"));
		for(int i = 0; i < 12; i++){
			for(int i2 = 0; i2 < 11; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawOptimalImage(g2, _roomO);


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 2
 		if((xInd == 5) && (yInd == 10)){
 			super.enterRoom(_gbs.ROUTE_2, 42, 29, FACESOUTH);
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