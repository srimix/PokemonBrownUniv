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
public class Leeds extends PokePanel2 {
	private BufferedImage _roomO;
	private final int ITEM=3;
	
	public Leeds(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Leeds(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer mime = new Trainer.Mr_Mime(null);
				mime.createHome(2, 2, 0, 0, "wander", "wander");				
				mime.setDirectionAndImage(FACEWEST);
				mime.getDialogue().add("Mr. Mime is mimicking a box, while staring right at you.");
				mime.getDialogue().add("Because maintaining the fourth wall is totally mainstream...");
				_movingTrainers.add(mime);
				
				Trainer hipster1 = new Trainer.RedOveralls(null);
				hipster1.createHome(7, 2);
				hipster1.getDialogue().add("Man, I need a smoke. Got any American Gastlys?");
				hipster1.setDirectionAndImage(FACENORTH);
				_movingTrainers.add(hipster1);
				
				Trainer hipster2 = new Trainer.PurpleHatGirl(null);
				hipster2.createHome(5, 3);
				hipster2.setDirectionAndImage(FACEEAST);
				hipster2.getDialogue().add("Ahhhh, PBR is the best...");
				hipster2.getDialogue().add("Pokemon BRown University :)");
				_movingTrainers.add(hipster2);
				
				Trainer ticket = new Trainer.Text();
				ticket.createHome(4, 1);
				ticket.getDialogue().add("TO THE BACK OF THE LINE! Oh sorry, I thought you were trying to cut.");
				ticket.getDialogue().add("Stupid freshman think they can cut the line because tickets are free.");
				ticket.getDialogue().add("So I throw them on the GROUND! I'm not a part of their SYSTEM!");
				ticket.getDialogue().add("You should give it a try! Show that frosh who's boss.");
				ticket.setGift(new Item.TM19_Seismic_Toss());
				ticket.getPostItemDialogue().add("It does damage equal to your level. Do the math: Senior>Freshmen.");
				_movingTrainers.add(ticket);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.LEEDS;
		
		this.addTrainers();
		this.loadAllEvents();
		this.xConstant=7;
		this.yConstant=38;
		this._mapX=172;
		this._mapY=299;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Leeds Breezeway";
		this._roomNum = 39;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/LeedsBreezeway/LeedsBreezeway.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(9,6);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Leeds.cmap"));
		for(int i = 0; i < 6; i++){
			for(int i2 = 0; i2 < 9; i2++){
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
		//Lincoln Field
 		if((xInd == 1 || xInd==2) && (yInd == 5)){
 			super.enterRoom(_gbs.LINCOLN_FIELD, 8, 13, FACESOUTH);
 		}
 		if((xInd == 6 || xInd==7) && (yInd == 5)){
 			super.enterRoom(_gbs.LINCOLN_FIELD, 14, 13, FACESOUTH);
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