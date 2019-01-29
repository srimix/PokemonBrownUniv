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
public class CITAtrium extends PokePanel2 {

	private BufferedImage _atriumO;
	
	public CITAtrium(GameBoyScreen gbs){
		super(gbs);
		
		this.createBaseRoom();

	}
	
	public CITAtrium(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.createBaseRoom();
	
	}
	
	public void addTrainers(){
		
		//7,1 FACENORTH
		//1,6, FACEWEST
		
		try{
			_movingTrainers = new Vector<Trainer>();
			
			Trainer doors = new Trainer.GraySkirt(null);
			doors.createHome(7, 1);
			doors.setDirectionAndImage(FACENORTH);
			doors.getDialogue().add("");
			_movingTrainers.add(doors);
			
			Trainer guide = new Trainer.BrownGuy(null);
			guide.createHome(1,6);
			guide.setDirectionAndImage(FACEWEST);
			guide.getDialogue().add("");
			_movingTrainers.add(guide);
			
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "CIT Entrance";
		this.song = M2.CIT;
		this._roomNum = 14;
		this._mapX=218;
		this._mapY=295;
		
		this.xConstant = 2;
		this.yConstant = 0;
		
		_textVisible = new boolean[1];
	
		this.addTrainers();
		
		this.setBattleBG(NewBattleScreen.CIT);
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/CITAtrium/atrium.png"));
			if(_atriumO == null)
				_atriumO = ImageIO.read(this.getClass().getResource("/PokemonFiles/CITAtrium/atriumO.png"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(8,9);
		
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/CITatrium.cmap"));
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
		g2.drawImage(_atriumO, null, this._xSpace, this._ySpace);
		
		if(_textVisible[0]){
			this.drawText(g2, "The door is locked. Must be","after 5pm");
		}
		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
	
		if((xInd == 3 || xInd == 4) && yInd == 3 && NORTH){
			super.enterRoom(_gbs.CIT_LOBBY, xInd+4, 13, FACENORTH);
		}
		if((xInd == 3 || xInd == 4) && yInd == 8){
			super.enterRoom(_gbs.SCIENCE_QUAD, 23, 3, FACESOUTH);
		}
	}
	
	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning()){
			
			if((_xIndex == 3 || _xIndex == 4) && _yIndex == 1 && NORTH){
				_textVisible[0] = !_textVisible[0];
				_busy = !_busy;
			}
			
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
			
		}
		else{
			super.A_Button();
		}
	}
}
