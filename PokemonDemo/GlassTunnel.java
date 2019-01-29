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
public class GlassTunnel extends PokePanel2 {
	private BufferedImage _roomOver, _roomGlass;
	private final int ONE=0, TWO=1, THREE=2, FOUR=3, FIVE=4, MAFIA=5, FAKE=6;
	
	public GlassTunnel(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(8);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public GlassTunnel(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(8);
		
		this.createBaseRoom();
		
	}
	
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				
				Vector<Pokemon> unobelt = new Vector<Pokemon>();
				unobelt.add(new Pokemon.Weedle().setWildLevel(14));
				unobelt.add(new Pokemon.Caterpie().setWildLevel(14));
				Trainer uno = new Trainer.BlackGirl(unobelt);
				uno.createHome(4,3,-1,0,"avoid","avoid");
				uno.setDirectionAndImage(FACESOUTH);
				uno.setStationary(true);
				uno.setLOS(3);
				uno.getDialogue().clear();
				uno.getDialogue().add("You've come to the Glass Bridge! But you'll go no further.");
				uno.getDialogue().add("You'll need to beat all of us to get to Sidney Frank!");
				uno.setDefeatDialogue("I did my best, I have no regrets.﻿");
				uno.setMoney(140);
				uno.setType("Undergrad");
				uno.setName("Elise");
				
				Vector<Pokemon> dosbelt = new Vector<Pokemon>();
				dosbelt.add(new Pokemon.Pidgey().setWildLevel(14));
				dosbelt.add(new Pokemon.Nidoran_F().setWildLevel(14));
				Trainer dos = new Trainer.RedstripeShirt(dosbelt);
				dos.createHome(5,1,-1,0,"avoid","avoid");
				dos.setStationary(true);
				dos.setLOS(3);
				dos.getDialogue().clear();
				dos.getDialogue().add("You think you're hot stuff? I'm a senior, I run this campus.");
				dos.setDirectionAndImage(FACENORTH);
				dos.setDefeatDialogue("I did my best, I have no regrets.﻿");
				dos.setMoney(210);
				dos.setVanishing(false);
				dos.setType("Senior");
				dos.setName("Jesse");
				
				Vector<Pokemon> tresbelt = new Vector<Pokemon>();
				tresbelt.add(new Pokemon.Rattata().setWildLevel(14));
				tresbelt.add(new Pokemon.Ekans().setWildLevel(14));
				tresbelt.add(new Pokemon.Zubat().setWildLevel(14));
				Trainer tres = new Trainer.BlondeDude(tresbelt);
				tres.createHome(6,3,-1,0,"avoid","avoid");
				tres.setStationary(true);
				tres.setLOS(3);
				tres.getDialogue().clear();
				tres.getDialogue().add("Hegemony.");
				tres.setDirectionAndImage(FACESOUTH);
				tres.setDefeatDialogue("I did my best, I have no regrets.﻿");
				tres.setMoney(210);
				tres.setType("Hipster");
				tres.setName("Stefan");
				
				Vector<Pokemon> quatrobelt = new Vector<Pokemon>();
				quatrobelt.add(new Pokemon.Pidgey().setWildLevel(16));
				quatrobelt.add(new Pokemon.Nidoran_F().setWildLevel(16));
				Trainer quatro = new Trainer.DirtyBlondeGirl(quatrobelt);
				quatro.createHome(7,1,-1,0,"avoid","avoid");
				quatro.setStationary(true);
				quatro.setLOS(3);
				quatro.getDialogue().clear();
				quatro.getDialogue().add("Summer housing waitlist. I'm gonna take my anger out on you!");
				quatro.setDirectionAndImage(FACENORTH);
				quatro.setDefeatDialogue("I did my best, so I've no regrets.﻿");
				quatro.setMoney(240);
				quatro.setType("Undergrad");
				quatro.setName("Jackie");
				
				Vector<Pokemon> cincobelt = new Vector<Pokemon>();
				cincobelt.add(new Pokemon.Mankey().setWildLevel(18));
				Trainer cinco = new Trainer.BlackHair(cincobelt);
				cinco.createHome(8,3,-1,0,"avoid","avoid");
				cinco.setStationary(true);
				cinco.setLOS(3);
				cinco.getDialogue().clear();
				cinco.getDialogue().add("Aren't you the one I torched in beer pong last night?");
				cinco.setDirectionAndImage(FACESOUTH);
				cinco.setDefeatDialogue("I did my best, I have no regrets.");
				cinco.setMoney(360);
				cinco.setType("Frat Boy");
				cinco.setName("Tomas");
				
				Vector<Pokemon> mafiabelt = new Vector<Pokemon>();
				mafiabelt.add(new Pokemon.Ekans().setWildLevel(15));
				mafiabelt.add(new Pokemon.Zubat().setWildLevel(15));
				Trainer mafia = new Trainer.MafiaWhite(mafiabelt);
				mafia.createHome(9,1,-1,0,"avoid","avoid");
				mafia.setStationary(true);
				mafia.setLOS(3);
				mafia.getDialogue().clear();
				mafia.getDialogue().add("Wanna buy an Orgo textbook? I'll give it to you for $500.");
				mafia.getDialogue().add("...no? Ugh, you penny-pinching adolescents.");
				mafia.setDefeatDialogue("I'm outta here. Wait 'til the boss hears about this...");
				mafia.setVanishing(true);
				mafia.setDirectionAndImage(FACENORTH);
				mafia.setMoney(450);
				mafia.setType("Mafia");
				mafia.setName("Thug");
				
				Trainer fake = new Trainer.ItemObject(new Item.FakeID());
				fake.createHome(9,1);
				fake.defeat();
				
				this.getMovingTrainers().add(uno); //0
				this.getMovingTrainers().add(dos); //1
				this.getMovingTrainers().add(tres); //2
				this.getMovingTrainers().add(quatro); //3
				this.getMovingTrainers().add(cinco); //4
				this.getMovingTrainers().add(mafia); //5
				this.getMovingTrainers().add(fake); //6
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			


	}

	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(ONE).defeat();
				SysOut.print("loaded1");
			}
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(TWO).defeat();
				SysOut.print("loaded2");
			}
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(THREE).defeat();
				SysOut.print("loaded3");
			}
			if(this.getCheckList().get(3)==1){
				this.getMovingTrainers().get(FOUR).defeat();
				SysOut.print("loaded4");
			}
			if(this.getCheckList().get(4)==1){
				this.getMovingTrainers().get(FIVE).defeat();
				SysOut.print("loaded5");
			}
			if(this.getCheckList().get(5)==1){
				this.getMovingTrainers().get(MAFIA).defeat();
				if(!_gbs.getPlayer().hasItem(Player.FAKE_ID)){
					this.getMovingTrainers().get(FAKE).unDefeat();
				}
				
				SysOut.print("loaded6");
			}
			if(this.getCheckList().get(6)==1){
				this.getMovingTrainers().get(FAKE).defeat();
				SysOut.print("loaded7");
			}

		}
	}

	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(ONE).isDefeated()){
			this.getCheckList().set(0,1);
			SysOut.print("1");
		}
		if(this.getMovingTrainers().get(TWO).isDefeated()){
			this.getCheckList().set(1,1);
			SysOut.print("2");
		}
		if(this.getMovingTrainers().get(THREE).isDefeated()){
			this.getCheckList().set(2,1);
			SysOut.print("3");
		}
		if(this.getMovingTrainers().get(FOUR).isDefeated()){
			this.getCheckList().set(3,1);
			SysOut.print("4");
		}
		if(this.getMovingTrainers().get(FIVE).isDefeated()){
			this.getCheckList().set(4,1);
			SysOut.print("5");
		}
		if(this.getMovingTrainers().get(MAFIA).isDefeated()){
			this.getCheckList().set(5,1);
			if(!_gbs.getPlayer().hasItem(Player.FAKE_ID)){
				this.getMovingTrainers().get(FAKE).unDefeat();
				SysOut.print("undefeated");
			}
			SysOut.print("6");
		}
		if(this.getMovingTrainers().get(FAKE).isDefeated()){
			if(!_gbs.getPlayer().hasItem(Player.FAKE_ID) && this.getMovingTrainers().get(MAFIA).isDefeated()){
				SysOut.print("faketrue");
				this.getCheckList().set(6,1);
			}
			SysOut.print("7");
		}
	}
	
	public void afterBattle(){
		super.afterBattle();
		
		if(this.getMovingTrainers().get(MAFIA).isDefeated()){
			this.getMovingTrainers().get(FAKE).unDefeat();
		}
	}
	
	public void createBaseRoom(){
		
		this.song = M2.BIOMED;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=14;
		this.yConstant=60;
		this._mapX=155;
		this._mapY=240;
		
		this.setBattleBG(NewBattleScreen.MEIK);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Glass Bridge";
		this._roomNum = 31;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/GlassTunnel Background.png"));
			if(_roomGlass == null)
				_roomGlass = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/GlassTunnel Glass.png"));
			if(_roomOver == null)
				_roomOver = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/GlassTunnel Over.png"));
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(12,5);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/GlassTunnel.cmap"));
		for(int i = 0; i < 5; i++){
			for(int i2 = 0; i2 < 12; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		g2.drawImage(_roomOver, null, this._xSpace, this._ySpace);
		g2.drawImage(_roomGlass, null, this._xSpace, this._ySpace);
		
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Biomed
 		if((xInd == 2) && (yInd == 1 || yInd == 2 || yInd == 3)){
 			super.enterRoom(_gbs.BIOMED_1, 19, 2, FACEWEST);
 		}
 		//Sidney Frank
 		if((xInd == 10) && (yInd == 1 || yInd == 2 || yInd == 3)){
 			super.enterRoom(_gbs.SIDNEY_FRANK, 0, 3, FACEEAST);
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