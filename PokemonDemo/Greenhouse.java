package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Greenhouse extends PokePanel2{
	
	private BufferedImage gHouseO;
	private final int tmSolar=0;
	
	public Greenhouse(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		this.createBaseRoom();
	}
	
	public Greenhouse(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		this.createBaseRoom();
	}

	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		
		try{
			Trainer tmSolar = new Trainer.ItemObject(new Item.TM37_EggBomb());
			tmSolar.createHome(7, 1, 0, 0, "", "");
			tmSolar.setStationary(true);
			
			_movingTrainers.add(tmSolar); //0, final int
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size()!=0){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(tmSolar).defeat();
			}
		}
	}
	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(tmSolar).isDefeated()){
			this.getCheckList().set(0,1);
		}
	}
	
	public void createBaseRoom(){
		
		this.song = M2.WAYLAND_ARCH;
		this.addTrainers();
		this.addWilds();
		this.loadAllEvents();
		this._mapX=172;
		this._mapY=299;
		this.xConstant=0;
		this.yConstant=0;
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Greenhouse";
		this._roomNum = 58;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Greenhouse/greenhouse.png"));
			gHouseO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Greenhouse/greenhouseO.png"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(8, 7);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Greenhouse.cmap"));
		for(int i = 0; i < 7; i++){
			for(int i2 = 0; i2 < 8; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		
		_wildPokemon.add(new Pokemon.Exeggcute().setWildLevel(18)); //Exeggcute, 18
//		_wildPokemon.get(0).getAttacks().clear();
//		_wildPokemon.get(0).getAttacks().add(new Attack.Dig());
	}
	
	public void Coord(){
		_gbs.saveHallOfFame();
	}
	
	public void wild(){
		Pokemon enemy = _wildPokemon.get(0);
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);

		this.setupBattle(ally, enemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(gHouseO, null, this._xSpace, this._ySpace);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if((xInd == 3 || xInd == 4) && yInd == 6){
			super.enterRoom(_gbs.LINCOLN_FIELD, 17, 6, FACESOUTH);
		}
	}
}
