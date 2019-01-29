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
public class WilsonLobby extends PokePanel2 {
	private BufferedImage _roomO1, _roomO2;
	
	public WilsonLobby(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public WilsonLobby(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void scanForAllEvents(){
		if(_movingTrainers.get(8).getGift() == null){
			this.getCheckList().set(0,1);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList() != null){
			if(this.getCheckList().get(0) == 1){
				_movingTrainers.get(8).defeatAndPostItemize();
			}
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				
				Trainer w1 = new Trainer.Text();
				w1.createHome(6, 10);
				w1.getDialogue().add("Just kind of standing there, waiting to go to class.");
				w1.getDialogue().add("Looks kind of like a zombie...");
				_movingTrainers.add(w1);
				
				Trainer w2 = new Trainer.Text();
				w2.createHome(5, 10);
				w2.getDialogue().add("Just kind of standing there, waiting to go to class.");
				w2.getDialogue().add("Looks kind of like a zombie...");
				_movingTrainers.add(w2);
				
				Trainer w3 = new Trainer.Text();
				w3.createHome(5, 9);
				w3.getDialogue().add("Just kind of standing there, waiting to go to class.");
				w3.getDialogue().add("Looks kind of like a zombie...");
				_movingTrainers.add(w3);
				
				Trainer w4 = new Trainer.Text();
				w4.createHome(5, 8);
				w4.getDialogue().add("Just kind of standing there, waiting to go to class.");
				w4.getDialogue().add("Looks kind of like a zombie...");
				_movingTrainers.add(w4);
				
				Trainer w5 = new Trainer.Text();
				w5.createHome(4, 7);
				w5.getDialogue().add("Just kind of standing there, waiting to go to class.");
				w5.getDialogue().add("Looks kind of like a zombie...");
				_movingTrainers.add(w5);
				
				Trainer w6 = new Trainer.Text();
				w6.createHome(3, 6);
				w6.getDialogue().add("Just kind of standing there, waiting to go to class.");
				w6.getDialogue().add("Looks kind of like a zombie...");
				_movingTrainers.add(w6);
				
				Trainer w7 = new Trainer.Text();
				w7.createHome(2, 6);
				w7.getDialogue().add("Just kind of standing there, waiting to go to class.");
				w7.getDialogue().add("Looks kind of like a zombie...");
				_movingTrainers.add(w7);
				
				Trainer w8 = new Trainer.Text();
				w8.createHome(1, 5);
				w8.getDialogue().add("Just kind of standing there, waiting to go to class.");
				w8.getDialogue().add("Looks kind of like a zombie...");
				_movingTrainers.add(w8);
				
				Trainer tele = new Trainer.GlassesGuy1(null);
				tele.createHome(2, 9);
				tele.getDialogue().add("Look at all these guys just waiting in line.");
				tele.getDialogue().add("It's WAY faster if you just teleport into your class...");
				tele.getDialogue().add("Here, you give it a try.");
				tele.setGift(new Item.TM30_Teleport());
				tele.getPostItemDialogue().add("It'll take you to the last Pokemon Center you went to.");
				_movingTrainers.add(tele);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		//this.song = M2.WILSON_LOBBY;
		this.song = M2.SALOMON;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-2;
		this.yConstant=-17;
		this._mapX=138;
		this._mapY=296;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Wilson Hall";
		this._roomNum = 43;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/WilsonLobby/WilsonLobby Background.png"));
				_roomO1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/WilsonLobby/WilsonLobby Over1.png"));
				_roomO2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/WilsonLobby/WilsonLobby Over2.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(8,14);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WilsonLobby.cmap"));
		for(int i = 0; i < 14; i++){
			for(int i2 = 0; i2 < 8; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		g2.drawImage(_roomO1, null, this._xSpace, this._ySpace);
		g2.drawImage(_roomO2, null, this._xSpace, this._ySpace);
		
		this.drawBox(g2);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 2
 		if((xInd == 3 || xInd==4) && (yInd == 13)){
 			super.enterRoom(_gbs.MAIN_GREEN, 33, 29, FACESOUTH);
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