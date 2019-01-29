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
public class PatriotsCourt extends PokePanel2 {
	private BufferedImage _roomO;
	
	public PatriotsCourt(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(7);
		
		this.createBaseRoom();
		_xSpace = -28;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
		
		
		
	}

	public PatriotsCourt(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(7);
		
		this.createBaseRoom();

	}

	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < 4; i++){
				if(this.getCheckList().get(i)==1){
					Trainer moving= this._movingTrainers.get(i);
					moving.defeatAndPostBattle();
				}
			}
		}
	}
	

	
	public void scanForAllEvents(){
		for(int i = 0; i < 4; i++){
			if(this._movingTrainers.get(i).isDefeated()){
				this.getCheckList().set(i,1);
			}
			else{
				this.getCheckList().set(i,0);
			}
		}
	}
		
	
	
	public void addTrainers(){
		
		try{
			this._movingTrainers = new Vector<Trainer>();
			
			Trainer maroon = new Trainer.MaroonHat(null);
			maroon.createHome(0, 20, 0, 0, "avoid", "avoid");
			maroon.setLOS(5);
			maroon.setDirectionAndImage(FACEWEST);
			maroon.getDialogue().clear();
			maroon.getDialogue().add("Welcome to Patriot's Court. Think you can handle the challenge?");
			maroon.setDefeatDialogue("Ha, you may have defeated me, but my brothers will avenge me!");
			maroon.setPause(true);
			maroon.setType("Frat Boy");
			maroon.setName("Joe");
			maroon.setMoney(100);
			maroon.setVanishing(false);
			maroon.setStationary(true);
			//Belt
			Pokemon maroon1 = new Pokemon.Machop().setWildLevel(6);
			Vector<Pokemon> maroonBelt = new Vector<Pokemon>();
			maroonBelt.add(maroon1);
			maroon.setBelt(maroonBelt);
			
			_movingTrainers.add(maroon);
			
			Trainer blue = new Trainer.BlueBro(null);
			blue.createHome(1, 14, 0, 0, "avoid", "avoid");
			blue.setLOS(5);
			blue.setDirectionAndImage(FACEWEST);
			blue.getDialogue().clear();
			blue.getDialogue().add("We're a co-ed frat!");
			blue.setDefeatDialogue("Nidorans are so cute...I don't know which one I like more!");
			
			blue.setPause(true);
			blue.setType("Frat Boy");
			blue.setName("Alex");
			blue.setMoney(60);
			blue.setVanishing(false);
			blue.setStationary(true);
			//Belt
			Pokemon blue1 = new Pokemon.Nidoran_M().setWildLevel(6);
			Pokemon blue2 = new Pokemon.Nidoran_F().setWildLevel(6);
			Vector<Pokemon> blueBelt = new Vector<Pokemon>();
			blueBelt.add(blue1);
			blueBelt.add(blue2);
			blue.setBelt(blueBelt);
			
			_movingTrainers.add(blue);
			
			Trainer glasses = new Trainer.GlassesGirl(null);
			glasses.createHome(18, 22, -1, 0, "avoid", "avoid");
			glasses.setLOS(3);
			glasses.setDirectionAndImage(FACESOUTH);
			glasses.getDialogue().clear();
			glasses.getDialogue().add("I <3 Pokemon!");
			glasses.setDefeatDialogue("Next time, I'll study harder!");
			glasses.setPause(true);
			glasses.setType("Tech Girl");
			glasses.setName("Melissa");
			glasses.setMoney(90);
			glasses.setVanishing(false);
			glasses.setStationary(true);
			//Belt
			Pokemon glasses1 = new Pokemon.Weedle().setWildLevel(7);
			Pokemon glasses2 = new Pokemon.Caterpie().setWildLevel(7);
			Pokemon glasses3 = new Pokemon.Pikachu().setWildLevel(8);
			Vector<Pokemon> glassesBelt = new Vector<Pokemon>();
			glassesBelt.add(glasses1);
			glassesBelt.add(glasses2);
			glassesBelt.add(glasses3);
			glasses.setBelt(glassesBelt);
			
			_movingTrainers.add(glasses);
			
			
			Trainer green = new Trainer.GreenHat(null);
			green.createHome(21, 19, 0, 0, "avoid", "avoid");
			green.setLOS(4);
			green.setDirectionAndImage(FACEEAST);
			green.getDialogue().clear();
			green.getDialogue().add("Prepare to get Theted!");
			green.setDefeatDialogue("Losing means you have to do a naked lap :(");
			green.setPause(true);
			green.setType("Frat Boy");
			green.setName("Moe");
			green.setMoney(80);
			green.setVanishing(false);
			green.setStationary(true);
			//Belt
			Pokemon green1 = new Pokemon.Mankey().setWildLevel(9);
			Vector<Pokemon> greenBelt = new Vector<Pokemon>();
			greenBelt.add(green1);
			green.setBelt(greenBelt);
			
			_movingTrainers.add(green);
			
			Trainer antidote = new Trainer.ItemObject(new Item.Antidote());
			antidote.createHome(21,9, 2, 6, "", "");
			antidote.setVanishing(true);
			antidote.setStationary(true);
			_movingTrainers.add(antidote);
			
			Trainer potion = new Trainer.ItemObject(new Item.Potion());
			potion.createHome(9,9, 2, 6, "", "");
			potion.setVanishing(true);
			potion.setStationary(true);
			_movingTrainers.add(potion);
			
			Trainer ball= new Trainer.ItemObject(new Item.PokeBall());
			ball.createHome(7,20, 2, 6, "", "");
			ball.setVanishing(true);
			ball.setStationary(true);
			_movingTrainers.add(ball);
			
		}
		catch (IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		
	}
	
	public void createBaseRoom(){
		
		this.setBackground(Color.BLACK);
		this.description = "Patriot's Court";
		this._roomNum = 7;
		this.xConstant=24;
		this.yConstant=-2;
		this._mapX=162;
		this._mapY=339;
		
		this.addTrainers();
		this._martMenuVisible=false;
		
		this.loadAllEvents();
		
		this.song = M2.PATRIOTS;

		this.setBattleBG(NewBattleScreen.FOREST);
		
		_textVisible = new boolean[0];
		this.setBikeAllow(true);
		this._wildPokemon = new Vector<Pokemon>();
		
			for(int i = 3; i <= 5; i++)
				this._wildPokemon.add(new Pokemon.Caterpie().setWildLevel(i));
			
			for(int i = 3; i <= 5; i++)
				this._wildPokemon.add(new Pokemon.Weedle().setWildLevel(i));
			
			this._wildPokemon.add(new Pokemon.Kakuna().setWildLevel(5));
			
			this._wildPokemon.add(new Pokemon.Metapod().setWildLevel(5));
				
			this._wildPokemon.add(new Pokemon.Pikachu().setWildLevel(4));
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/PatriotsCourt/patriotsCourt.png"));
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(25,24);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/patriots.cmap"));
		for(int i = 0; i < 24; i++){
			for(int i2 = 0; i2 < 25; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		
		this.drawAllGenerics(g2);
		this.drawBox(g2);
	}

	public void enterRoom(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		//Wriston Quad
		if(xInd == 3 && yInd == 0){
			super.enterRoom(_gbs.WRISTON_QUAD, 11, 13, FACENORTH);
		}
		
		//Wriston to Ratty
		if(xInd == 19 && yInd == 0){
			super.enterRoom(_gbs.WRISTON_QUAD, 21, 13, FACENORTH);
		}
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		
		int random = (int) (101*Math.random());
		
		if(random < 40){
			randomEnemy = _wildPokemon.get((int)(3*Math.random()));
		}
		else{
			if(random < 80){
				randomEnemy = _wildPokemon.get(3+(int)(3*Math.random()));
			}
			else{
				if(random < 87){
					randomEnemy = _wildPokemon.get(6);
				}
				else{
					if(random < 95){
						randomEnemy = _wildPokemon.get(7);
					}
					else{
						if(random < 100){
							randomEnemy = _wildPokemon.get(8);
						}
					}
				}
			}
		}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		
		this.setupBattle(ally, randomEnemy, false, _roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning()){

			
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}
		
		super.A_Button();
	}
}