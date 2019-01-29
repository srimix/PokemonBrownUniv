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
public class OlivePit45 extends PokePanel2{

	//private BufferedImage _olivePit;
	private final int FOSSIL =1;
	//private Trainer
	
	public OlivePit45(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public OlivePit45(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		//Cave
		for(int i = 7; i <= 11; i++)
			this._wildPokemon.add(new Pokemon.Zubat().setWildLevel(i));
		for(int i = 7; i <= 9; i++)
			this._wildPokemon.add(new Pokemon.Geodude().setWildLevel(i));
		
		this._wildPokemon.add(new Pokemon.Paras().setWildLevel(10));
		this._wildPokemon.add(new Pokemon.Clefairy().setWildLevel(9));
		
	}
	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(FOSSIL).defeat();
			}

		}
	}

	public void scanForAllEvents(){
		SysOut.print("Size:" +this.getCheckList().size());
		if(this.getMovingTrainers().get(FOSSIL).isDefeated()){
			this.getCheckList().set(0,1);	
		}
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
		this._movingTrainers = new Vector<Trainer>();
		
		Trainer rabbi = new Trainer.Rabbi(_gbs.getPlayer(), this._roomNum, this);
		rabbi.createHome(15, 4, 0, 0, "", "");
		rabbi.setStationary(true);
		
		try{
			Trainer rabbiWife = new Trainer.RedAndYellow(null);
			rabbiWife.createHome(6, 8, 0, 0, "avoid", "avoid");
			rabbiWife.setLOS(4);
			rabbiWife.setDirectionAndImage(FACESOUTH);
			rabbiWife.getDialogue().add("Oh my goodness, you look so tired!");
			rabbiWife.getDialogue().add("Go speak with my husband, he'll let you recover.");
			rabbiWife.setStationary(true);
			rabbiWife.setPause(false);
			
			this._movingTrainers.add(rabbiWife); //0
			
			Trainer fossil = new Trainer.ItemObject(new Item.Dome_Fossil());
			fossil.createHome(10, 2, 10000, 10000, "", "");
			fossil.setStationary(true);
			
			this._movingTrainers.add(fossil); //1
		}
		catch(IOException ioe){}
		this._movingTrainers.add(rabbi);//2
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Olive Pit";
		this._roomNum = 26;
		this.xConstant = -16;
		this.yConstant = -20;
		this._mapX=134;
		this._mapY=270;
		this._caveX=13;
		this._caveY=16;
		this._pkmnCentX=15;
		this._pkmnCentY=5;
		this.PROBABILITY=PokePanel2.CAVE_PROB;//FIXME
		this._caveEntranceNum=_gbs.ROUTE_2;

		this._outdoors = false;
		this.addTrainers();
		this.setBikeAllow(true);
		
		this.loadAllEvents();
		
		this.song = M2.OLIVE_PIT;
		
		this.setBattleBG(NewBattleScreen.DARK_CAVE);
		this.setBikeAllow(true);
		this.addWilds();
		
		
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/OlivePit/olivepit4.png"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(22, 10);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/OlivePit4.cmap"));
		for(int i = 0; i < 10; i++){
			for(int i2 = 0; i2 < 22; i2++){
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
		if(xInd == 3 && yInd == 2){
			super.enterRoom(_gbs.OLIVE_PIT_MAIN, 20, 9, FACENORTH);
		}
		else if(xInd == 19 && yInd == 7){
			super.enterRoom(_gbs.OLIVE_PIT_3to4, 2, 2, FACESOUTH);
			
		}
	}
}
