package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class BarHolGenerator extends PokePanel2{
	
	private BufferedImage black;
	
	public BarHolGenerator(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(3);
		this.createBaseRoom();
	}
	
	public BarHolGenerator(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		
		this.initializeEventVector(3);
		this.createBaseRoom();
	}

	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this._roomNum = _gbs.BARHOL_GENERATOR;
		this.description = "Generator Room";
		this._darkRoom = true;
		this._mapX = 270;
		this._mapY = 305;
		this.xConstant=3;
		this.yConstant=0;
		this.setBattleBG(NewBattleScreen.BARHOL);
		this.addTrainers();
		
		this.loadAllEvents();
		
		if(!BarHolLobby.powerOn){
			this.song = M2.BH_DARK;
		}
		else{
			this.song = M2.BH_LIGHT;
		}
		
		if(GameBoyScreen.finishedLoading){
			try{
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolGenerator/BHgenerator.png"));
				black = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLobby/BH Black General.png"));
			}
			catch(IOException ioe){}
		}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(3,6);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BHGenerator.cmap"));
		for(int i = 0; i < 6; i++){
			for(int i2 = 0; i2 < 3; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void addTrainers(){
		SysOut.print("GEN ADDED");
		this._movingTrainers = new Vector<Trainer>();
		Trainer gen = new Trainer.Generator(this);
		gen.createHome(2,1);
		gen.getDialogue().add(_gbs.getPlayer().getPlayerName() + " rebooted the generator...");
		gen.getDialogue().add("");
		//gen.getDialogue().add("The power returned to normal!");
		//gen.setDefeatDialogue("The power is on and running smoothly :)");
		_movingTrainers.add(gen);
		
		Trainer elec0 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Electrode().setWildLevel(20), "BZZZZTT!", "Electrode", M2.ELECTRODE);
		elec0.createHome(1, 2);
		this.getMovingTrainers().add(elec0);

		Trainer elec1 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Electrode().setWildLevel(20), "BZZZZTT!", "Electrode", M2.ELECTRODE);
		elec1.createHome(2, 2);
		this.getMovingTrainers().add(elec1);

		
	}
	
	public void loadAllEvents(){
		SysOut.print("loaded");
		if(this.getCheckList() != null){
			SysOut.print("(LOAD) checklist not null");
			if(this.getCheckList().get(0) == 1){
				SysOut.print("(LOAD)GEN ON");
				_movingTrainers.get(0).defeat();
			}
			if(this.getCheckList().get(1) == 1){
				SysOut.print("Electrode 1 defeated");
				_movingTrainers.get(1).defeat();
			}
			if(this.getCheckList().get(2) == 1){
				SysOut.print("Electrode 2 defeated");
				_movingTrainers.get(2).defeat();
			}
		}
	}
	
	public void scanForAllEvents(){
		if(this._movingTrainers.get(0).isDefeated()){
			SysOut.print("(SCAN)Check -> Gen ON");
			this.getCheckList().set(0,1);
		}
		if(this._movingTrainers.get(1).isDefeated()){
			SysOut.print("Scan: Electrode is defeated.");
			this.getCheckList().set(1,1);
		}
		if(this._movingTrainers.get(2).isDefeated()){
			SysOut.print("Scan: Electrode is defeated");
			this.getCheckList().set(2,1);
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		if((xInd == 1 || xInd == 2) && yInd == 5){
			super.enterRoom(_gbs.BARHOL_BASEMENT, 4, 3, FACESOUTH);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		if(!BarHolLobby.powerOn && !_flashOn){
			g2.drawImage(black, null, this._xSpace, this._ySpace);
		}
		this.drawAllGenerics(g2);
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
