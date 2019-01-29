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
public class Sayles extends PokePanel2 {
	private BufferedImage _roomO;
	private ImageIcon _battle,_puff1, _puff2, _puff3, _puff4, _puff5, _puff6, _puff7,_puff8;
	
	public Sayles(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
	
	}

	public Sayles(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer res1 = new Trainer.RedAndYellow(null);
				res1.createHome(9, 5);
				res1.setDirectionAndImage(FACESOUTH);
				res1.getDialogue().add("Please step away from the stage.");
				res1.getDialogue().add("We are currently in the middle of the new First-Pick competition.");
				_movingTrainers.add(res1);
				
				Trainer res2 = new Trainer.RedBandanaGirl(null);
				res2.createHome(2, 5);
				res2.setDirectionAndImage(FACESOUTH);
				res2.getDialogue().add("Please step away from the stage.");
				res2.getDialogue().add("We are currently in the middle of the new First-Pick competition.");
				_movingTrainers.add(res2);
				
				Trainer res3 = new Trainer.RedOveralls(null);
				res3.createHome(10, 5);
				res3.setDirectionAndImage(FACESOUTH);
				res3.getDialogue().add("Please step away from the stage.");
				res3.getDialogue().add("We are currently in the middle of the new First-Pick competition.");
				_movingTrainers.add(res3);
								
				Trainer sayles1 = new Trainer.GraySkirt(null);
				sayles1.createHome(1, 4);
				sayles1.getDialogue().add("Ohmigod I can't decide...");
				sayles1.getDialogue().add("New Dorm or Young O? New Dorm or Young O? New Dorm or Young O?!?");
				_movingTrainers.add(sayles1);
				
				Trainer sayles2 = new Trainer.BlueBro(null);
				sayles2.createHome(1, 3);
				sayles2.getDialogue().add("I'm gonna get the epic New Pembroke single with a patio.");
				_movingTrainers.add(sayles2);
				
				Trainer zip = new Trainer.NiceHair(null);
				zip.createHome(1, 2);
				if(_gbs.getPlayer().getAllItems().get(Item.ZIPCAR_CARD).getRemaining() == 0){
					zip.getDialogue().add("Hey man, can you do me a favor?");
					zip.getDialogue().add("I'm hoping to get a Young O suite...");
					zip.getDialogue().add("...and I'll need a car to move all my stuff over there.");
					zip.getDialogue().add("Do you think you could go get my ZipCar pass for me?");
					zip.getDialogue().add("It's in the mail room. My box number is 7084.");
				}
				else{
					zip.getDialogue().add("Never mind. I got Slater instead...");
				}
				_movingTrainers.add(zip);
				
				Trainer sayles3 = new Trainer.GlassesGirl(null);
				sayles3.createHome(3, 4);
				sayles3.getDialogue().add("I live in Tech House, so I don't have to worry about housing.");
				_movingTrainers.add(sayles3);
				
				Trainer sayles4 = new Trainer.Backpacker(null);
				sayles4.createHome(5, 4);
				sayles4.getDialogue().add("Keeney singles row FTW.");
				_movingTrainers.add(sayles4);
				
				Trainer sayles7 = new Trainer.BlueGirl(null);
				sayles7.createHome(7, 4);
				sayles7.getDialogue().add("Slater could be really cool, living right on the Main Green...");
				_movingTrainers.add(sayles7);
				
				Trainer sayles5 = new Trainer.Pirate(null);
				sayles5.createHome(6, 3);
				sayles5.getDialogue().add("Sssh, don't tell anyone but I'm really a senior.");
				sayles5.getDialogue().add("I snuck in here to play the No-Show drinking game...");
				_movingTrainers.add(sayles5);
				
				Trainer sayles6 = new Trainer.ChubbyGuy(null);
				sayles6.createHome(4, 3);
				sayles6.getDialogue().add("NO SHOW!!!");
				_movingTrainers.add(sayles6);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		//this.song = M2.SAYLES;
		this.song = M2.SALOMON;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=51;
		this.yConstant=154;
		this._mapX=138;
		this._mapY=295;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Sayles Hall";
		this._roomNum = _gbs.SAYLES;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Sayles/Sayles Background.png"));
			}
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Sayles/Sayles Over.png"));
				_puff1 = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/Puff1.gif"));
				_puff2 = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/Puff2.gif"));
				_battle = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/battle2.gif"));
				_puff3 = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/Puff3.gif"));
				_puff4 = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/Puff4.gif"));
				_puff5 = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/Puff5.gif"));
				_puff6 = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/Puff6.gif"));
				_puff7 = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/Puff7.gif"));
				_puff8 = new ImageIcon(this.getClass().getResource("/PokemonFiles/Sayles/Puff8.gif"));	
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(12,8);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Sayles.cmap"));
		for(int i = 0; i < 8; i++){
			for(int i2 = 0; i2 < 12; i2++){
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
		_battle.paintIcon(this, g2, this._xSpace+0+this.xConstant+70, this._ySpace+0+this.yConstant+110);
		_puff4.paintIcon(this, g2, this._xSpace+20+this.xConstant+20, this._ySpace+0+this.yConstant-40);
		_puff1.paintIcon(this, g2, this._xSpace+40+this.xConstant+20, this._ySpace+0+this.yConstant-40);
		_puff7.paintIcon(this, g2, this._xSpace+60+this.xConstant+20, this._ySpace+0+this.yConstant-40);
		_puff3.paintIcon(this, g2, this._xSpace+80+this.xConstant+20, this._ySpace+0+this.yConstant-40);
		_puff5.paintIcon(this, g2, this._xSpace+120+this.xConstant+20, this._ySpace+0+this.yConstant-40);
		_puff6.paintIcon(this, g2, this._xSpace+140+this.xConstant+20, this._ySpace+0+this.yConstant-40);
		_puff2.paintIcon(this, g2, this._xSpace+160+this.xConstant+20, this._ySpace+0+this.yConstant-40);
		_puff8.paintIcon(this, g2, this._xSpace+180+this.xConstant+20, this._ySpace+0+this.yConstant-40);
		
		this.drawBox(g2);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Main Green
 		if((xInd == 5 || xInd==6) && (yInd == 0)){
 			super.enterRoom(_gbs.MAIN_GREEN, 32, 20, FACESOUTH);
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