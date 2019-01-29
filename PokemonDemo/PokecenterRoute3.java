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
public class PokecenterRoute3 extends PokePanel2 {
	
	public PokecenterRoute3(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public PokecenterRoute3(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			Trainer center = new Trainer.PokemonCenter(_gbs.getPlayer(), this._roomNum, this);
			center.createHome(8,2);
			center.setStationary(true);
			this._movingTrainers.add(center);
			
			try{
				Trainer man = new Trainer.RedstripeShirt(null);
				man.createHome(4,5);
				man.getDialogue().add("I really like this place.");
				man.getDialogue().add("Nice and quiet, and out of the way.");
				man.getDialogue().add("You'd have no idea that an extremely dangerous Pokemon...");
				man.getDialogue().add("...was housed just a block north of here.");
				_movingTrainers.add(man);
				
				Trainer pc = new Trainer.PC(_gbs);
				pc.createHome(12, 0);
				_movingTrainers.add(pc);
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.POKECENTER;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-10;
		this.yConstant=23;
		this._mapX=175;
		this._mapY=277;
		this._pkmnCentX=8;
		this._pkmnCentY=3;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "PokeCenter";
		this._roomNum = 33;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/PokecenterRoute3/Pokecenter.png"));
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(17,9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/PokecenterRoute3.cmap"));
		for(int i = 0; i < 9; i++){
			for(int i2 = 0; i2 < 17; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawBox(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 3
 		if((xInd == 8) && (yInd == 8)){
 			super.enterRoom(_gbs.ROUTE_3, 12, 38, FACESOUTH);
 		}
		
	}
	
	public void blackout(){
		this.blackout(this._pkmnCentX,this._pkmnCentY,FACESOUTH);
		_gbs.getPlayer().healAllActive();
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