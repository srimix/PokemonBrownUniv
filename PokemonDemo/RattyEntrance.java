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
public class RattyEntrance extends PokePanel2 {
	private BufferedImage _roomO;
	public RattyEntrance(GameBoyScreen gbs){
		super(gbs);
		this.createBaseRoom();
		_xSpace = -28;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	}

	public RattyEntrance(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		this.createBaseRoom();
	}
	
	public void addTrainers(){

		this._movingTrainers = new Vector<Trainer>();
		
		try{
			if(!_gbs.getPlayer().isGymLeaderDefeated(1)){
				
				Trainer line0 = new Trainer.Text();
				line0.createHome(1, 0, 0, 0, "", "");
				_movingTrainers.add(line0);
				
				Trainer line1 = new Trainer.GreenDress(null);
				line1.getDialogue().add("So close...I can see the food...Please, just let me in!");
				line1.createHome(1, 1, 0, 0, "freeze", "freeze");
				line1.addDest(1, 0);
				line1.setFirstDest();
				line1.setDirectionAndImage(FACESOUTH);
				_movingTrainers.add(line1);
				
				Trainer line2 = new Trainer.BlueGirl(null);
				_movingTrainers.add(line2);
				
				Trainer line3 = new Trainer.Female1(null);
				_movingTrainers.add(line3);
				
				Trainer line4 = new Trainer.GlassesGirl(null);
				_movingTrainers.add(line4);
				
				Trainer line5 = new Trainer.GreenHat(null);
				_movingTrainers.add(line5);
				
				Trainer line6 = new Trainer.GlassesGuy1(null);
				line6.getDialogue().add("I would just go to the Ivy Room, but I ran out of Flex Points...");
				_movingTrainers.add(line6);
				
				Trainer line7 = new Trainer.BlueBro(null);
				_movingTrainers.add(line7);
				
				for(int i = 2; i < _movingTrainers.size(); i++){
					_movingTrainers.get(i).setCurrentImage(_movingTrainers.get(i).getBackImage());
					_movingTrainers.get(i).createHome(1, i, 0, 0, "freeze", "freeze");
					_movingTrainers.get(i).addDest(1, i-1);
					_movingTrainers.get(i).setFirstDest();
					_movingTrainers.get(i).setStationary(false);
				}
			}
		}
		catch(IOException ioe){
			
		}
		
	}

	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this._roomNum = 9;
		this.xConstant=12;
		this.yConstant=14;
		this._mapX=172;
		this._mapY=319;
		
		
		this.addTrainers();
		this.setBikeAllow(false);
		_textVisible = new boolean[0];
		this.description = "Ratty Entrance";
		this.song = M2.WRISTON;
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/RattyEntrance/RattyEntrance.png"));
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(11,9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/RattyEntrance.cmap"));
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
		
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		this.drawBox(g2);

		this.drawAllGenerics(g2);
	}

	public void enterRoom(int xInd, int yInd){
		
		
		//To Wriston/Patriots Court Side
		if((xInd == 9||xInd==10) && (yInd == 8)){
			super.enterRoom(_gbs.WRISTON_QUAD, 21, 10, FACESOUTH);
//			_gbs.setCurrentPanel(6);
//			PokePanel2 current = _gbs.getCurrentPanel();
//			current.createBaseRoom();
//			
//			current.setIndices(21,10);
//			current.setLocation(-248,-53);
//
//			current._darkness = 250;
//			current._fadeUp.start();
//			current.resetNTimer();
//			_gbs.startNTimer();
//			
//			current.Down(); current.Down();
		}
		
		if((xInd == 0 || xInd == 1) && yInd == 8){
			super.enterRoom(_gbs.WRISTON_QUAD, 17, 5, FACEWEST);
		}
		
		//To Ivy Room
		if((xInd == 3||xInd == 4||xInd == 6||xInd == 7) && (yInd == 3)){
			super.enterRoom(_gbs.IVY_ROOM, 9, 9, FACENORTH);
//			_gbs.setCurrentPanel(10);
//			PokePanel2 current = _gbs.getCurrentPanel();
//			
//			current.setIndices(9,9);
//			current.setLocation(-(256-190),-(262-190));
//
//			current._darkness = 250;
//			current._fadeUp.start();
//			current.resetNTimer();
//			_gbs.startNTimer();
//			
//			current.Up(); current.Up();
		}

		//To Ratty/Right Side
		if((xInd == 9 || xInd == 10) && yInd == 0){
			super.enterRoom(_gbs.RATTY, 19, 24, FACENORTH);
//			_gbs.setCurrentPanel(8);
//			PokePanel2 current = _gbs.getCurrentPanel();
//			current.createBaseRoom();
//			
//			current.setIndices(19,24);
//			current.setLocation(-207,-313);
//
//			current._fadeUp.start();
//			current.resetNTimer();
//			_gbs.startNTimer();
//			
//			current.Up(); current.Up();
		}
		
		//To Ratty/Left Side
		if((xInd == 0 || xInd == 1) && yInd == 0){
			super.enterRoom(_gbs.RATTY, 3, 24, FACENORTH);
//			_gbs.setCurrentPanel(8);
//			PokePanel2 current = _gbs.getCurrentPanel();
//			current.createBaseRoom();
//			
//			current.setIndices(3,24);
//			current.setLocation(113,-313);
//
//			current._fadeUp.start();
//			current.resetNTimer();
//			_gbs.startNTimer();
//			
//			current.Up(); current.Up();
		}
		
		
	}

	public void A_Button(){
		if(!_menuVisible  && !this.textTimer.isRunning()){

			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}
		
			super.A_Button();
	
	}
}