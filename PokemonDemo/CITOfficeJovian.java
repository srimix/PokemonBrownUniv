package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class CITOfficeJovian extends PokePanel2{

	public CITOfficeJovian(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.createBaseRoom();
	}
	
	public CITOfficeJovian(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		
		this.initializeEventVector(1);
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList() != null){
			if(this.getCheckList().get(0) == 1){
				_movingTrainers.get(0).defeatAndPostBattle();
			}
		}
	}
	
	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(0).isDefeated()){
			this.getCheckList().set(0, 1);
		}
	}

	public void enterRoom(int xInd, int yInd){
		if((xInd == 1 || xInd == 2) && yInd == 4){
			_gbs.setOld(false);
			super.enterRoom(_gbs.CIT_HALL, 6,2,FACESOUTH);
		}
	}
	
	public void addTrainers(){
		_movingTrainers = new Vector<Trainer>();
		try{
			Vector<Pokemon> jov = new Vector<Pokemon>();
			jov.add(new Pokemon.Voltorb().setWildLevel(20));
			jov.add(new Pokemon.Magnemite().setWildLevel(20));
			jov.add(new Pokemon.Voltorb().setWildLevel(20));
			Trainer jovian = new Trainer.GlassesGuy1(jov);
			jovian.createHome(2,1);
			jovian.setType("CS15 TA");
			jovian.setName("Jovian");
			jovian.getDialogue().add("Hi, do you need some help with Sketchy, or maybe Othello?");
			jovian.setDefeatDialogue("My code wasn't done compiling!");
			
			_movingTrainers.add(jovian);
		}catch(IOException ioe){}
	}
	
	public void createBaseRoom(){

		this.addTrainers();
		this.loadAllEvents();
		this._roomNum = _gbs.CIT_OFFICE_JOVIAN;
		this.setBattleBG(NewBattleScreen.CIT);
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/CITGymHall/smallRoom.png"));
		}
		catch(IOException ioe){ioe.printStackTrace();}
		
		this.setBackground(Color.RED);
		this.description = "Jovian's Office";

		this._mapX=218;
		this._mapY=295;
		
		this.song=M2.GYM;
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(4,5);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/CITsmallroom.cmap"));
		for(int i = 0; i < 5; i++){
			for(int i2 = 0; i2 < 4; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		this.drawAllTrainers(g2, xConstant, yConstant, _movingTrainers);
		this.drawPlayer(g2);
		
		this.drawAllGenerics(g2);
		//g2.drawImage(over, null, this._xSpace, this._ySpace);
	}
}
