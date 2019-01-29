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
public class ViaViaLobby extends PokePanel2 {
	private BufferedImage _roomO, _roomT;
	public static boolean rescued = false;
	
	public ViaViaLobby(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public ViaViaLobby(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void scanForAllEvents(){
		if(rescued){
			this.getCheckList().set(0,1);
		}
		else{
			this.getCheckList().set(0,0);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList() != null && this.getCheckList().get(0) == 1){
			rescued = true;
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try { 		
				Trainer menu1 = new Trainer.Text(); 
				menu1.createHome(3,3);
				menu1.getDialogue().add("Welcome to ViaVia 4. What would you like to order?");
				
				Trainer menu2 = new Trainer.Text(); 
				menu2.createHome(4,3);
				menu2.getDialogue().add("Try our calamari. It's dastardly.");
				menu2.getDialogue().add("Dastardly delicious.");
				
				Trainer menu3 = new Trainer.Text(); 
				menu3.createHome(5,3);
				menu3.getDialogue().add("I highly suggest the Amazing Chicken.");
				menu3.getDialogue().add("It's the sauce that makes it Amazing!");
				
				Trainer guard = new Trainer.MafiaMagenta(null);
				guard.createHome(8,3);
				guard.setDirectionAndImage(FACENORTH);
				guard.getDialogue().add("Where do you think you're going?");
				guard.getDialogue().add("I don't think so. Take a step back.");
				
				
				if((_gbs.getPlayer().isGymLeaderDefeated(7))){
					menu1.setVanishing(true);
					menu2.setVanishing(true);
					menu3.setVanishing(true);
					guard.setVanishing(true);
					menu1.defeat();
					menu2.defeat();
					menu3.defeat();
					guard.defeat();
				}
				
				this.getMovingTrainers().add(menu1); //0
				this.getMovingTrainers().add(menu2);//1
				this.getMovingTrainers().add(menu3);//2
				this.getMovingTrainers().add(guard);//3

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.VIAVIA_LOBBY;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-13;
		this.yConstant=-20;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=135;
		this._mapY=235;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "ViaVia IV";
		this._roomNum = _gbs.VIAVIA_LOBBY;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/ViaViaLobby/VVLobby Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/ViaViaLobby/VVLobby Over.png"));
				_roomT = ImageIO.read(this.getClass().getResource("/PokemonFiles/ViaViaLobby/VVLobby Trainer.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(12,10);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/VVLobby.cmap"));
		for(int i = 0; i < 10; i++){
			for(int i2 = 0; i2 < 12; i2++){
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
		if(!_gbs.getPlayer().isGymLeaderDefeated(7)){
			this.drawOptimalImage(g2, _roomT);
		}


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 3
 		if((xInd == 3) && (yInd == 9)){
 			super.enterRoom(_gbs.ROUTE_3, 28, 8, FACESOUTH);
 		}
 		if((xInd == 8) && (yInd == 2)){
 			super.enterRoom(_gbs.VIA_VIA, 1, 9, FACESOUTH);
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