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
public class OlivePit12 extends PokePanel2{

	//private BufferedImage _olivePit;
	//private Trainer
	
	public OlivePit12(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
	}
	
	public OlivePit12(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < this._movingTrainers.size(); i++){
				if(this.getCheckList().get(i) == 1){
					Trainer moving = this._movingTrainers.get(i);
					moving.defeatAndPostBattle();
				}
			}
		}
	}
	
	public void scanForAllEvents(){
		for(int i = 0; i < this._movingTrainers.size(); i++){
			if(this._movingTrainers.get(i).isDefeated()){
				this.getCheckList().set(i,1);
			}
			else{
				this.getCheckList().set(i,0);
			}
		}
	}
	public void addWilds(){
		SysOut.print("WILDS IN OLIVE PIT 12");
		this._wildPokemon = new Vector<Pokemon>();
		//Cave
		for(int i = 7; i <= 11; i++)
			this._wildPokemon.add(new Pokemon.Zubat().setWildLevel(i));
		for(int i = 7; i <= 9; i++)
			this._wildPokemon.add(new Pokemon.Geodude().setWildLevel(i));
		
		this._wildPokemon.add(new Pokemon.Paras().setWildLevel(10));
		this._wildPokemon.add(new Pokemon.Clefairy().setWildLevel(9));
		
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		
		int random = (int) (101*Math.random());
			
			if(random < 60){
				randomEnemy = _wildPokemon.get((int)(5*Math.random()));
			}
			else if(random < 86){
				randomEnemy = _wildPokemon.get(5+(int)(3*Math.random()));
			}
			else if(random < 96){
				randomEnemy = _wildPokemon.get(8);
			}
			else if(random < 100){
				randomEnemy = _wildPokemon.get(9);
			}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		
		this.setupBattle(ally, randomEnemy, false, _roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void addTrainers(){
		try{
			this._movingTrainers = new Vector<Trainer>();
			
			Vector<Pokemon> grayBelt = new Vector<Pokemon>();
			Pokemon gr1 = new Pokemon.Oddish().setWildLevel(11);
			Pokemon gr2 = new Pokemon.Bellsprout().setWildLevel(11);
			grayBelt.add(gr1);
			grayBelt.add(gr2);
			Trainer gray = new Trainer.GraySkirt(grayBelt);
			gray.createHome(6, 6, 0, 0, "avoid", "avoid");
			gray.setLOS(6);
			gray.setDirectionAndImage(FACEEAST);
			gray.getDialogue().add("What do you think of my clothes? I got them at the Vault.");
			gray.setDefeatDialogue("Whatever, caring what other people think is so mainstream.");
			gray.getPostBattleDialogue().add("Maybe I should have gone more retro...");
			gray.setPause(true);
			gray.setType("Hiptser Girl");
			gray.setName("Maria");
			gray.setMoney(165);
			gray.setVanishing(false);
			gray.setStationary(true);
			
			this._movingTrainers.add(gray); //0
			
			Trainer escape = new Trainer.ItemObject(new Item.EscapeRope());
			escape.createHome(5,7);
			this._movingTrainers.add(escape); //1
			
			Trainer tm01 = new Trainer.ItemObject(new Item.TM01_MegaPunch());
			tm01.createHome(3,7);
			this._movingTrainers.add(tm01); //2
			
			
		}
		catch(IOException ioe){}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Olive Pit";
		this._roomNum = 24;
		this.xConstant = -17;
		this.yConstant = -20;
		this._mapX=134;
		this._mapY=270;
		this._caveX=13;
		this._caveY=16;
		this.PROBABILITY=PokePanel2.CAVE_PROB;//FIXME
		this._caveEntranceNum=_gbs.ROUTE_2;
		this.addTrainers();
		this.addWilds();
		this._outdoors = false;
		this.setBikeAllow(true);
		
		this.loadAllEvents();
		
		this.song = M2.OLIVE_PIT;
		
		this.setBattleBG(NewBattleScreen.DARK_CAVE);
		this.setBikeAllow(true);
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/OlivePit/olivepit2.png"));	
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(9, 14);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/OlivePit2.cmap"));
		for(int i = 0; i < 14; i++){
			for(int i2 = 0; i2 < 9; i2++){
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
		this.drawAllTrainers(g2, xConstant, yConstant, _movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 2 && yInd == 3){
			super.enterRoom(_gbs.OLIVE_PIT_MAIN, 2, 3, FACESOUTH);
		}
		else if(xInd == 2 && yInd == 11){
			super.enterRoom(_gbs.OLIVE_PIT_MAIN, 2, 11, FACEEAST);
		}
	}
}
