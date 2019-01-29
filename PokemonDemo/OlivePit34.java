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
public class OlivePit34 extends PokePanel2{

	//private BufferedImage _olivePit;
	//private Trainer
	
	public OlivePit34(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(6);
		
		this.createBaseRoom();
		
	}
	
	public OlivePit34(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(6);
		
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
		this._wildPokemon = new Vector<Pokemon>();
		//Cave
		for(int i = 9; i <= 12; i++)
			this._wildPokemon.add(new Pokemon.Zubat().setWildLevel(i));
		for(int i = 9; i <= 10; i++)
			this._wildPokemon.add(new Pokemon.Geodude().setWildLevel(i));
		for(int i = 10; i <= 12; i++)
			this._wildPokemon.add(new Pokemon.Paras().setWildLevel(i));
		for(int i = 10; i <= 12; i++)
			this._wildPokemon.add(new Pokemon.Clefairy().setWildLevel(i));
		
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		
		int random = (int) (101*Math.random());
			
			if(random < 54){
				randomEnemy = _wildPokemon.get((int)(4*Math.random()));
			}
			else if(random < 79){
				randomEnemy = _wildPokemon.get(4+(int)(2*Math.random()));
			}
			else if(random < 94){
				randomEnemy = _wildPokemon.get(6+(int)(3*Math.random()));
			}
			else if(random < 100){
				randomEnemy = _wildPokemon.get(9+(int)(3*Math.random()));
			}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		
		this.setupBattle(ally, randomEnemy, false, _roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		
		try{
			
			Vector<Pokemon> belt = new Vector<Pokemon>();
			Pokemon tough1 = new Pokemon.Sandshrew().setWildLevel(11);
			Pokemon tough2 = new Pokemon.Rattata().setWildLevel(11);
			Pokemon tough3 = new Pokemon.Zubat().setWildLevel(11);
			belt.add(tough1);
			belt.add(tough2);
			belt.add(tough3);
			Trainer toughGirl = new Trainer.DirtyBlondeGirl(belt);
			toughGirl.createHome(1, 4, 0, 0, "avoid", "avoid");
			toughGirl.setLOS(5);
			toughGirl.setDirectionAndImage(FACEWEST);
			toughGirl.getDialogue().add("Can you handle the rough terrain?");
			toughGirl.setDefeatDialogue("I guess you can.");
			toughGirl.getPostBattleDialogue().add("I love cave-diving. I always keep an escape rope though,");
			toughGirl.getPostBattleDialogue().add("just in case.");
			toughGirl.setPause(true);
			toughGirl.setType("BOLT Leader");
			toughGirl.setName("Catherine");
			toughGirl.setMoney(330);
			toughGirl.setVanishing(false);
			toughGirl.setStationary(true);
			toughGirl.setBattleImage(TImg.F_GREEN_SHAWL);
			
			_movingTrainers.add(toughGirl); //0
			
			Vector<Pokemon> nerdBelt = new Vector<Pokemon>();
			Pokemon nerd1 = new Pokemon.Grimer().setWildLevel(12);
			Pokemon nerd2 = new Pokemon.Voltorb().setWildLevel(12);
			Pokemon nerd3 = new Pokemon.Koffing().setWildLevel(12);
			nerdBelt.add(nerd1);
			nerdBelt.add(nerd2);
			nerdBelt.add(nerd3);
			Trainer nerd = new Trainer.GlassesGuy1(nerdBelt);
			nerd.createHome(7, 5, 0, 0, "avoid", "avoid");
			nerd.setLOS(3);
			nerd.setDirectionAndImage(FACENORTH);
			nerd.getDialogue().add("They used to send canaries down into mines to make sure...");
			nerd.getDialogue().add("...that there were no poisonous gases.");
			nerd.setDefeatDialogue("I guess there's none here!");
			nerd.getPostBattleDialogue().add("What was I thinking? This is a tunnel, not a mine.");
			nerd.setPause(true);
			nerd.setType("Tech Guy");
			nerd.setName("Jovian");
			nerd.setMoney(300);
			nerd.setVanishing(false);
			nerd.setStationary(true);
			nerd.setBattleImage(TImg.M_TECHNICIAN);
			
			_movingTrainers.add(nerd); //1
			
			Vector<Pokemon> fratBelt = new Vector<Pokemon>();
			Pokemon frat1 = new Pokemon.Raticate().setWildLevel(13);
			fratBelt.add(frat1);
			Trainer frat = new Trainer.ChubbyGuy(fratBelt);
			frat.createHome(14, 8, 0,0, "avoid", "avoid");
			frat.setLOS(3);
			frat.setDirectionAndImage(FACEEAST);
			frat.getDialogue().add("Thought you could sneak by me, huh?");
			frat.setDefeatDialogue("I guess I couldn't see how strong you were in the dark.");
			frat.getPostBattleDialogue().add("Maybe I should get night vision goggles?");
			frat.setPause(true);
			frat.setType("Frat Boy");
			frat.setName("Marcus");
			frat.setMoney(480);
			frat.setVanishing(false);
			frat.setStationary(true);
			
			_movingTrainers.add(frat); //2
			
			Trainer ether = new Trainer.ItemObject(new Item.Ether());
			ether.createHome(3,8);
			this._movingTrainers.add(ether); //3
			
			Trainer hp = new Trainer.ItemObject(new Item.HPUp());
			hp.createHome(13,6);
			this._movingTrainers.add(hp); //4
			
			Trainer moon2 = new Trainer.ItemObject(new Item.MoonStone());
			moon2.createHome(4,1);
			this._movingTrainers.add(moon2); //5
			
			
		}
		catch(IOException ioe){}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Olive Pit";
		this._roomNum = 25;
		this.xConstant = -16;
		this.yConstant = -20;
		this._mapX=134;
		this._mapY=270;
		this._caveX=13;
		this._caveY=16;
		this.PROBABILITY=PokePanel2.CAVE_PROB;//FIXME
		this._caveEntranceNum=_gbs.ROUTE_2;
		this.addTrainers();

		this._outdoors = false;
		this.setBikeAllow(true);
		
		this.loadAllEvents();
		
		this.song = M2.OLIVE_PIT;
		
		this.setBattleBG(NewBattleScreen.DARK_CAVE);
		this.setBikeAllow(true);
		this.addWilds();
		
		
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/OlivePit/olivepit3.png"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(16, 15);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/OlivePit3.cmap"));
		for(int i = 0; i < 15; i++){
			for(int i2 = 0; i2 < 16; i2++){
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
		if(xInd == 2 && yInd == 2){
			super.enterRoom(_gbs.OLIVE_PIT_4to5, 19, 7, FACEWEST);
		}
		else if(xInd == 12 && yInd == 12){
			super.enterRoom(_gbs.OLIVE_PIT_EXIT, 2, 6, FACEEAST);
		}
	}
}
