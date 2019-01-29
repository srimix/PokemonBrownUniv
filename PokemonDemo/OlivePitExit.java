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
public class OlivePitExit extends PokePanel2{

	private BufferedImage  _olivePitOver;
	//private Trainer
	
	public OlivePitExit(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public OlivePitExit(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Olive Pit";
		this._roomNum = 27;
		this.xConstant = -16;
		this.yConstant = -20;
		this._mapX=134;
		this._mapY=270;
		this._caveX=13;
		this._caveY=16;
		this.PROBABILITY=PokePanel2.CAVE_PROB;//FIXME
		this._caveEntranceNum=_gbs.ROUTE_2;
		//this.addTrainers();

		this._outdoors = false;
		this.setBikeAllow(true);
		
		this.loadAllEvents();
		
		this.song = M2.OLIVE_PIT;
		
		this.setBattleBG(NewBattleScreen.DARK_CAVE);
		this.setBikeAllow(true);
		this._wildPokemon = new Vector<Pokemon>();
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/OlivePit/olivepit5.png"));
			_olivePitOver = ImageIO.read(this.getClass().getResource("/PokemonFiles/OlivePit/olivepit5Over.png"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(13, 9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/OlivePit5.cmap"));
		for(int i = 0; i < 9; i++){
			for(int i2 = 0; i2 < 13; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		
		
		//this.drawAllTrainers(g2, xConstant, yConstant, dynaTrainers)
		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		g2.drawImage(_olivePitOver, null, this._xSpace, this._ySpace);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 2 && yInd == 6){
			super.enterRoom(_gbs.OLIVE_PIT_3to4, 12, 12, FACENORTH);
		}
		else if(xInd == 6 && yInd == 1){
			super.enterRoom(_gbs.ROUTE_2, 35, 1, FACESOUTH);
		}
	}
	
}
