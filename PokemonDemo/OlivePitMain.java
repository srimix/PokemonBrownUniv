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
public class OlivePitMain extends PokePanel2{

	private BufferedImage _olivePitOver;
	//private Trainer
	
	public OlivePitMain(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(10);
		
		this.createBaseRoom();
		
	}
	
	public OlivePitMain(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(10);
		
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < this._movingTrainers.size(); i++){
				if(this.getCheckList().get(i) == 1){
					Trainer moving = this._movingTrainers.get(i);
					moving.defeat();
					moving.getDialogue().clear();
					moving.getDialogue().add(moving.getDefeatDialogue());
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
		for(int i = 6; i <= 11; i++)
			this._wildPokemon.add(new Pokemon.Zubat().setWildLevel(i));
		for(int i = 8; i <= 10; i++)
			this._wildPokemon.add(new Pokemon.Geodude().setWildLevel(i));
		
		this._wildPokemon.add(new Pokemon.Paras().setWildLevel(8));
		this._wildPokemon.add(new Pokemon.Sandshrew().setWildLevel(12));
		this._wildPokemon.add(new Pokemon.Clefairy().setWildLevel(11));
		
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		
		int random = (int) (101*Math.random());
			
			if(random < 75){
				randomEnemy = _wildPokemon.get((int)(6*Math.random()));
			}
			else if(random < 90){
				randomEnemy = _wildPokemon.get(6+(int)(3*Math.random()));
			}
			else if(random < 95){
				randomEnemy = _wildPokemon.get(9);
			}
			else if(random < 99){
				randomEnemy = _wildPokemon.get(10);
			}
			else if(random < 100){
				randomEnemy = _wildPokemon.get(11); //lololol clefairy
			}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		
		this.setupBattle(ally, randomEnemy, false, _roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}

	public void addTrainers(){
/*
 * 
OLIVE PIT MAIN ROOM

9, 14, FACENORTH

4, 11, FACENORTH

3,1, FACENORTH

3,2, (ITEM)

9, 3, FACEWEST

17, 7, FACEWEST

20, 14, (ITEM)
 */
		try{
			this._movingTrainers = new Vector<Trainer>();
			
			Vector<Pokemon> blueBelt = new Vector<Pokemon>();
			Pokemon b1 = new Pokemon.Weedle().setWildLevel(11);
			Pokemon b2 = new Pokemon.Kakuna().setWildLevel(11);
			blueBelt.add(b1);
			blueBelt.add(b2);
			Trainer blue = new Trainer.BlueBro(blueBelt);
			blue.createHome(9, 14, 0, 0, "avoid", "avoid");
			blue.setLOS(5);
			blue.setDirectionAndImage(FACENORTH);
			blue.getDialogue().add("There are always lots of scary bugs hidden in caves!");
			blue.setDefeatDialogue("I guess you aren't afraid of bugs...");
			blue.getPostBattleDialogue().add("I should look for some bigger, scarier bugs.");
			blue.setType("Bug Catcher");
			blue.setName("Ken");
			blue.setMoney(110);
			blue.setVanishing(false);
			blue.setStationary(true);
			blue.setBattleImage(TImg.M_BUG_CATCHER);
			this._movingTrainers.add(blue); //0
			
			Vector<Pokemon> blackBelt = new  Vector<Pokemon>();
			Pokemon black1 = new Pokemon.Rattata().setWildLevel(10);
			Pokemon black2 = new Pokemon.Rattata().setWildLevel(10);
			Pokemon black3 = new Pokemon.Zubat().setWildLevel(10);
			blackBelt.add(black1);
			blackBelt.add(black2);
			blackBelt.add(black3);
			Trainer black = new Trainer.BlackHair(blackBelt);
			black.createHome(4, 11, 0, 0, "avoid", "avoid");
			black.setLOS(5);
			black.setDirectionAndImage(FACENORTH);
			black.getDialogue().add("What's more annoying than Rattata?");
			black.getDialogue().add("Two Rattata!");
			black.getDialogue().add("What's more annoying than that?");
			black.setDefeatDialogue("Oh, I guess you've heard this one before.");
			black.getPostBattleDialogue().add("Zubats! Everywhere!");
			black.setPause(true);
			black.setType("Pre-frosh");
			black.setName("Josh");
			black.setMoney(150);
			black.setVanishing(false);
			black.setStationary(true);
			
			this._movingTrainers.add(black); //1
			
			Vector<Pokemon> hikeBelt = new Vector<Pokemon>();
			Pokemon hike1 = new Pokemon.Geodude().setWildLevel(10);
			Pokemon hike2 = new Pokemon.Geodude().setWildLevel(10);
			Pokemon hike3 = new Pokemon.Onix().setWildLevel(10);
			hikeBelt.add(hike1);
			hikeBelt.add(hike2);
			hikeBelt.add(hike3);
			Trainer hiker = new Trainer.Pirate(hikeBelt);
			hiker.createHome(3, 1, 0, 0, "avoid", "avoid");
			hiker.setLOS(4);
			hiker.setDirectionAndImage(FACENORTH);
			hiker.getDialogue().add("Aaaah, nothing like the great outdoors to keep you relaxed!");
			hiker.setDefeatDialogue("I must have been too relaxed!");
			hiker.getPostBattleDialogue().add("Oh well, time to drink my own piss.");
			hiker.setPause(true);
			hiker.setType("BOLT Leader");
			hiker.setName("Howard");
			hiker.setMoney(350);
			hiker.setVanishing(false);
			hiker.setStationary(true);
			hiker.setBattleImage(TImg.M_HIKER);
			
			this._movingTrainers.add(hiker); //2
			
			Vector<Pokemon> clefBelt = new Vector<Pokemon>();
			Pokemon clef1 = new Pokemon.Clefairy().setWildLevel(14);
			clefBelt.add(clef1);
			Trainer clef = new Trainer.GreenDress(clefBelt);
			clef.createHome(9, 3, 0, 0, "avoid", "avoid");
			clef.setLOS(6);
			clef.setDirectionAndImage(FACEWEST);
			clef.getDialogue().add("Clefairy are sososo cute! Don't you think so?");
			clef.setDefeatDialogue("Still cute!");
			clef.getPostBattleDialogue().add("Would you say that Clefairy was the cutest Pokemon ever?");
			clef.setPause(true);
			clef.setType("Librarian");
			clef.setName("Miriam");
			clef.setMoney(210);
			clef.setVanishing(false);
			clef.setStationary(true);
			
			this._movingTrainers.add(clef); //3
			
			Vector<Pokemon> nerdBelt = new Vector<Pokemon>();
			Pokemon nerd1 = new Pokemon.Magnemite().setWildLevel(11);
			Pokemon nerd2 = new Pokemon.Voltorb().setWildLevel(11);
			nerdBelt.add(nerd1);
			nerdBelt.add(nerd2);
			Trainer nerd = new Trainer.GlassesGirl(nerdBelt);
			nerd.createHome(17, 7, 0, 0, "avoid", "avoid");
			nerd.setLOS(5);
			nerd.setDirectionAndImage(FACEWEST);
			nerd.getDialogue().add("There are lots of minerals deep in caves that are quite valuable.");
			nerd.getDialogue().add("Have you found any rare earth magnets?");
			nerd.setDefeatDialogue("Let me know if you find any!");
			nerd.getPostBattleDialogue().add("I'll have to keep looking...FOR SCIENCE!");
			nerd.setPause(true);
			nerd.setType("Tech Girl");
			nerd.setName("Iris");
			nerd.setMoney(275);
			nerd.setVanishing(false);
			nerd.setStationary(true);
			
			this._movingTrainers.add(nerd); //4
			
			Trainer tm12 = new Trainer.ItemObject(new Item.TM12_WaterGun());
			tm12.createHome(19,1);
			this._movingTrainers.add(tm12);  //5
			
			Trainer potion1 = new Trainer.ItemObject(new Item.Potion());
			potion1.createHome(2,1);
			this._movingTrainers.add(potion1); //6
			
			Trainer potion2 = new Trainer.ItemObject(new Item.Potion());
			potion2.createHome(1,13);
			this._movingTrainers.add(potion2); //7
			
			Trainer rare = new Trainer.ItemObject(new Item.RareCandy());
			rare.createHome(1,8);
			this._movingTrainers.add(rare); //8
			
			Trainer moon = new Trainer.ItemObject(new Item.MoonStone());
			moon.createHome(20,14);
			this._movingTrainers.add(moon); //9
			
			
			
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Olive Pit";
		this._roomNum = 23;
		this.xConstant = 4;
		this.yConstant = 0;
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
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/OlivePit/olivepitMain.png"));
				_olivePitOver = ImageIO.read(this.getClass().getResource("/PokemonFiles/OlivePit/olivepitMain Over.png"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(23, 18);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/OlivePitMain.cmap"));
		for(int i = 0; i < 18; i++){
			for(int i2 = 0; i2 < 23; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		

		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		
		g2.drawImage(_olivePitOver, null, this._xSpace, this._ySpace);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 12 && yInd == 17){
			super.enterRoom(_gbs.ROUTE_2, 13, 15, FACESOUTH);
		}
		else if(xInd == 2 && yInd == 3){
			super.enterRoom(_gbs.OLIVE_PIT_1to2, 2, 3, FACEEAST);
		}
		else if(xInd == 2 && yInd == 11){
			super.enterRoom(_gbs.OLIVE_PIT_1to2, 2, 11, FACEEAST);
		}
		else if(xInd == 20 && yInd == 9){
			super.enterRoom(_gbs.OLIVE_PIT_4to5, 3, 2, FACESOUTH);
		}
	}
}
