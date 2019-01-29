package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class CITHall extends PokePanel2 {

//	private BufferedImage bg;
	private boolean rDefeat = false, gDefeat = false, bDefeat = false;
//	public static int office = 0;
//	public static final int RED = 0, GREEN = 1, BLUE = 2;
	
	public CITHall(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
	}
	
	public CITHall(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
	}
	
	public void createBaseRoom(){
		
		this.setBattleBG(NewBattleScreen.CIT);
		
		if(GameBoyScreen.finishedLoading){
			if(_gbs.getPanel(_gbs.CIT_OFFICE_JOVIAN).getCheckList().get(0) == 1){
				rDefeat = true;
			}
			if(_gbs.getPanel(_gbs.CIT_OFFICE_NABEEL).getCheckList().get(0) == 1){
				gDefeat = true;
			}
			if(_gbs.getPanel(_gbs.CIT_OFFICE_HAYLEY).getCheckList().get(0) == 1){
				bDefeat = true;
			}
		}
		
		this.setBackground(Color.BLACK);
		this.description = "CS15 TA Offices";
		this._roomNum = _gbs.CIT_HALL;
		
		this.xConstant = 2;
		this.yConstant = 5;
		
		this._mapX=218;
		this._mapY=295;
		
		this.addTrainers();
		
		this.loadAllEvents();
		
		this.song = M2.GYM;
		
		this.setBattleBG(NewBattleScreen.CIT);
		
		if(GameBoyScreen.finishedLoading){
			try{
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/CITGymHall/cithall.png"));
			}
			catch(IOException ioe){ioe.printStackTrace();}
		}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(20, 6);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/CIThall.cmap"));
		for(int i = 0; i < 6; i++){
			for(int i2 = 0; i2 < 20; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		if(rDefeat && gDefeat && bDefeat){
			this._room._roomGrid[18][0] = 'D';
			SysOut.print("Time for Andy!");
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		
		Trainer guy = new Trainer.ChampGuy();
		guy.createHome(1,2,0,0,"","");
		if(_gbs.getPlayer().isGymLeaderDefeated(3)){
			guy.getDialogue().add("Sparks were flying! That was incredible! Good job.");
		}
		else{
			guy.getDialogue().add("Yo Champ! How's it going?");
			guy.getDialogue().add("Ready to take on the legendary Andy and his army of CS15 TAs?");
			guy.getDialogue().add("They all focus on Electric-type Pokemon...");
			guy.getDialogue().add("Stay grounded, and avoid water so you don't short-circuit.");
			guy.getDialogue().add("Good luck!");
		}
		_movingTrainers.add(guy);
		
		Trainer text = new Trainer.Text();
		text.createHome(17, 0, 0, 0, "", "");
		if(!rDefeat && !gDefeat && !bDefeat){
			text.getDialogue().add("R: ?, G: ?, B: ?...");
			text.getDialogue().add("The screen has inputs for an RGB color combination.");
			text.getDialogue().add("Wonder what the right color is to open the door?");
			
			Trainer textDoor = new Trainer.Text();
			textDoor.createHome(18, 0, 0, 0, "", "");
			textDoor.getDialogue().add("The door is electronically locked.");
			textDoor.getDialogue().add("It seems to be connected to the panel on the left.");
			_movingTrainers.add(textDoor);
		}
		else{
			if(rDefeat && gDefeat && bDefeat){
				text.getDialogue().add("R: 50, G: 20, B: 20...");
				text.getDialogue().add("The screen turned Brown!");
				text.getDialogue().add("The door unlocked!!!");
			}
			else{
				Trainer textDoor = new Trainer.Text();
				textDoor.createHome(18, 0, 0, 0, "", "");
				textDoor.getDialogue().add("The door is electronically locked.");
				textDoor.getDialogue().add("It seems to be connected to the panel on the left.");
				_movingTrainers.add(textDoor);
			}
			if(rDefeat && !gDefeat && !bDefeat){
				text.getDialogue().add("R: 50, G: ?, B: ?...");
				text.getDialogue().add("The screen turned Dark Red.");
			}
			if(!rDefeat && gDefeat && !bDefeat){
				text.getDialogue().add("R: ?, G: 20, B: ?...");
				text.getDialogue().add("The screen turned Very Dark Green.");
			}
			if(!rDefeat && !gDefeat && bDefeat){
				text.getDialogue().add("R: ?, G: ?, B: 20...");
				text.getDialogue().add("The screen turned Very Dark Blue.");
			}
			if(rDefeat && gDefeat && !bDefeat){
				text.getDialogue().add("R: 50, G: 20, B: ?...");
				text.getDialogue().add("The screen turned Light Brown.");
			}
			if(rDefeat && !gDefeat && bDefeat){
				text.getDialogue().add("R: 50, G: ?, B: 20...");
				text.getDialogue().add("The screen turned Dark Purple.");
			}
			if(!rDefeat && gDefeat && bDefeat){
				text.getDialogue().add("R: ?, G: 20, B: 20...");
				text.getDialogue().add("The screen turned Dark Aqua.");
			}
		}
		_movingTrainers.add(text);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 2 && yInd == 5){
			super.enterRoom(_gbs.CIT_LOBBY, 9, 0, FACESOUTH);
		}
		if(xInd == 6 && yInd == 2){
//			office = RED;
			_gbs.setOld(true);
			super.enterRoom(_gbs.CIT_OFFICE_JOVIAN,2,4,FACENORTH);
//			_gbs.getCurrentPanel().scanForAllEvents();
//			_gbs.getCurrentPanel().loadAllEvents();
		}
		if(xInd == 10 && yInd == 2){
//			office = GREEN;
			_gbs.setOld(true);
			super.enterRoom(_gbs.CIT_OFFICE_NABEEL,2,4,FACENORTH);		
//			_gbs.getCurrentPanel().scanForAllEvents();
//			_gbs.getCurrentPanel().loadAllEvents();
		}
		if(xInd == 14 && yInd == 2){
//			office = BLUE;
			_gbs.setOld(true);
			super.enterRoom(_gbs.CIT_OFFICE_HAYLEY,2,4,FACENORTH);
//			_gbs.getCurrentPanel().scanForAllEvents();
//			_gbs.getCurrentPanel().loadAllEvents();
		}
		if(xInd == 18 && yInd == 0){
			_gbs.setOld(true);
			super.enterRoom(_gbs.CIT_ANDY, 2, 5, FACENORTH);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawPlayer(g2);
		
		this.drawAllGenerics(g2);
	}
}
