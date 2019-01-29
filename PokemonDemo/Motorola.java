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
public class Motorola extends PokePanel2 {
	private BufferedImage _roomO;
	
	public Motorola(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();

	
	}

	public Motorola(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				
				Trainer k3 = new Trainer.KoreanPorygon(new Pokemon.Porygon(), _gbs);
				k3.createHome(3, 0);
				k3.getDialogue().add("\"Na gyeolgug sueob saibeo deulab haess-eo .\"");
				k3.getDialogue().add("\"Nan deo isang pil-yohaji anhseubnida.\"");
				k3.getDialogue().add("\"Dangsin-eun geugeos-eul hal su issseubnida.\"");
				if(_gbs.getPlayer().getAllActive().size()==6){
					k3.getDialogue().add("...");
					k3.getDialogue().add("She appears to be speaking Korean.");
					k3.getDialogue().add("It looks like she's trying to give you a PokeBall.");
				}
				else{
					k3.getDialogue().add("Porygon joined your party!");
					k3.getDialogue().add("Kamsahamnida!");
				}
				
				_movingTrainers.add(k3);
				
				Trainer k1 = new Trainer.BlackHair(null);
				k1.createHome(6, 4);
				k1.setDirectionAndImage(FACESOUTH);
				k1.getDialogue().add("\"2 jeomman deo bad-eumyeon 'A' inde... 'B' nawass-eo\"");
				k1.getDialogue().add("...");
				k1.getDialogue().add("He appears to be speaking Korean.");
				_movingTrainers.add(k1);
				
				Trainer k2 = new Trainer.BlackHairBlueDress(null);
				k2.createHome(1, 6);
				k2.setDirectionAndImage(FACESOUTH);
				k2.getDialogue().add("\"KISA moim-eseo hangug-eumsig junde! gat-i gallae?\"");
				k2.getDialogue().add("...");
				k2.getDialogue().add("She appears to be speaking Korean.");
				_movingTrainers.add(k2);
				
				Trainer whitey = new Trainer.NiceHair(null);
				whitey.createHome(0, 4);
				whitey.setDirectionAndImage(FACESOUTH);
				whitey.getDialogue().add("I'm trying to learn Korean!");
				whitey.getDialogue().add("\"Nae hoebuhkeurapeuteuneun changuhro kadeuk cha isseyo!\"");
				whitey.getDialogue().add("...");
				whitey.getDialogue().add("He appears to be speaking Korean...badly.");
				_movingTrainers.add(whitey);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.MOTOROLA;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=2;
		this.yConstant=30;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX = 218;
		this._mapY = 295;
		

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Motorola Room";
		this._roomNum = _gbs.MOTOROLA;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Motorola/motorola.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Motorola/motorolaO.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(8,9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Motorola.cmap"));
		for(int i = 0; i < 9; i++){
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
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
 		if((xInd == 1 || xInd == 2) && (yInd == 8)){
 			super.enterRoom(_gbs.CIT_LOBBY, 14, 0, FACESOUTH);
 		}
	}
	
	public void scanForAllEvents(){
		if(_movingTrainers.get(0).isDefeated()){
			this.getCheckList().set(0, 1);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0)==1){
				_movingTrainers.get(0).defeatAndPostItemize();
			}
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