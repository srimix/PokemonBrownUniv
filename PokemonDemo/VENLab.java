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
public class VENLab extends PokePanel2 {
	private BufferedImage _roomO;
	
	public VENLab(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public VENLab(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	

	public void createBaseRoom(){
		
		this.song = M2.VENLAB;
		this.PROBABILITY=7; //Change this later.
		this.loadAllEvents();
		
		this.xConstant=199;
		this.yConstant=190;
		this._mapX=180;
		this._mapY=295;
		
		this.setBattleBG(NewBattleScreen.VENLAB);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "VENLab";
		this._roomNum = 38;
		
		this.addWilds();
		
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/MetcalfLab/VENLab Background.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(11,11);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/VENLab.cmap"));
		for(int i = 0; i < 11; i++){
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
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		
		this.drawBox(g2);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		for(int i = 14; i <= 15; i++){
			this._wildPokemon.add(new Pokemon.Abra().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Kadabra().setWildLevel(16));
		for(int i = 14; i <= 15; i++){
			this._wildPokemon.add(new Pokemon.Drowzee().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Mr_Mime().setWildLevel(15));
		for(int i = 15; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Jynx().setWildLevel(i));
		}
	}

	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		if(random < 40.0){
			randomEnemy = _wildPokemon.get(0 + (int)(2*Math.random()));
		}
		else if(random < 50.0){
			randomEnemy = _wildPokemon.get(2);
		}
		else if(random < 80.0){
			randomEnemy = _wildPokemon.get(3 + (int)(2*Math.random()));
		}
		else if(random < 90.0){
			randomEnemy = _wildPokemon.get(5);
		}
		else if(random < 100.0){
			randomEnemy = _wildPokemon.get(6 + (int)(2*Math.random()));
		}
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void enterRoom(int xInd, int yInd){
 		//VENLab
 		if((xInd == 5) && (yInd == 5)){
 			super.enterRoom(_gbs.METCALF, 12, 1, FACESOUTH);
 		}
		
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		if((xInd == 0) && WEST){
			this.enterRoomSeamless(_gbs.VENLAB, 10, yInd, FACEWEST);
		}
		if((xInd == 10) && EAST){
			this.enterRoomSeamless(_gbs.VENLAB, 0, yInd, FACEEAST);
		}
		if((yInd == 0) && NORTH){
			this.enterRoomSeamless(_gbs.VENLAB, xInd, 10, FACENORTH);
		}
		if((yInd == 10) && SOUTH){
			this.enterRoomSeamless(_gbs.VENLAB, xInd, 0, FACESOUTH);
		}
	}


	
	public void enterRoomSeamless(int roomDest, int newXIndex, int newYIndex, int direction){
		this.setIndices(newXIndex, newYIndex);
		this.setLocation(-this.xConstant-(20*this._xIndex)+176, -this.yConstant-(20*this._yIndex)+168);
		switch(direction){
		case 2:
			this.Down(); this.Down(); break;
		case 0:
			this.Up(); this.Up(); break;
		case 1:
			this.Right(); this.Right(); break;
		case 3:
			this.Left(); this.Left(); break;
		default:
			this.Down(); this.Down(); break;
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