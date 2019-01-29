
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
public class LincolnField extends PokePanel2 {
	private BufferedImage _roomO;
	
	public LincolnField(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(7);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public LincolnField(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(7);
		
		this.createBaseRoom();
		
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < 7; i++){
				if(this.getCheckList().get(i) == 1){
					Trainer moving = this._movingTrainers.get(i);
					moving.defeat();
					moving.getDialogue().clear();
					moving.getDialogue().add(moving.getDefeatDialogue());
					
					if(i == 4){
						moving.setGift(null);
						moving.getDialogue().clear();
						for(int d = 0; d < moving.getPostItemDialogue().size(); d++){
							moving.getDialogue().add(moving.getPostItemDialogue().get(d));
						}
					}
				}
			}
		}
	}
	
	public void scanForAllEvents(){
		for(int i = 0; i < 7; i++){
			if(this._movingTrainers.get(i).isDefeated()){
				this.getCheckList().set(i,1);
			}
			else{
				this.getCheckList().set(i,0);
			}
		}
	}
	
	public void addTrainers(){
		
			this._movingTrainers = new Vector<Trainer>();
			
			try{
			//items remaining to add
			
			Trainer nugget=new Trainer.ItemObject(new Item.Nugget());
			nugget.createHome(7,31);
			this.getMovingTrainers().add(nugget); //0
			
			//goku
			Vector<Pokemon> ekBelt = new Vector<Pokemon>();
			ekBelt.add(new Pokemon.Weedle().setWildLevel(16));
			ekBelt.add(new Pokemon.Caterpie().setWildLevel(16));
			ekBelt.add(new Pokemon.Weedle().setWildLevel(16));
			Trainer ek = new Trainer.Goku(ekBelt);
			ek.createHome(18,16,0,-10,"wander","wander");
			ek.addDest(16, 19);
			ek.addDest(18, 16);
			ek.setLOS(3);
			ek.setFirstDest();
			ek.setStationary(false);
			ek.getDialogue().add("Ka...me...ha...me....");
			ek.setDefeatDialogue("I should have gone Super Saiyan...");
			ek.setMoney(160);
			ek.setType("LARPer");
			ek.setName("Goku");
			
			//sasuke
			Vector<Pokemon> dhoBelt = new Vector<Pokemon>();
			dhoBelt.add(new Pokemon.Rattata().setWildLevel(16));
			dhoBelt.add(new Pokemon.Pikachu().setWildLevel(16));
			dhoBelt.add(new Pokemon.Cubone().setWildLevel(20));
			Trainer dho = new Trainer.Sasuke(dhoBelt);
			dho.createHome(26,18,0,0,"avoid","avoid");
			dho.setDirectionAndImage(FACEEAST);
			dho.getDialogue().add("I must avenge my family!");
			dho.setDefeatDialogue("Grr...you're a pest.");
			dho.setMoney(160);
			dho.setVanishing(false);
			dho.setType("LARPer");
			dho.setName("Sasuke");
			
			//ninetails
			Vector<Pokemon> theenBelt = new Vector<Pokemon>();
			theenBelt.add(new Pokemon.Spearow().setWildLevel(16));
			theenBelt.add(new Pokemon.Raticate().setWildLevel(16));
			Trainer theen = new Trainer.Ninetails(theenBelt);
			theen.createHome(25,18,-10,-2,"avoid","avoid");
			theen.setDirectionAndImage(FACEWEST);
			theen.getDialogue().add("Kage Bushin No Jutsu!");
			theen.setDefeatDialogue("Uy. I just want some ramen now...");
			theen.setMoney(380);
			theen.setType("LARPer");
			theen.setName("Naruto");
			
			
			//Final boss
			Vector<Pokemon> chaarBelt = new Vector<Pokemon>();
			chaarBelt.add(new Pokemon.Squirtle().setWildLevel(20));
			chaarBelt.add(new Pokemon.Weepinbell().setWildLevel(16));
			Trainer chaar = new Trainer.Link(chaarBelt);
			chaar.createHome(31,17,0,0,"avoid","avoid");
			chaar.setLOS(5);
			chaar.getDialogue().add("Halt! You'll go no further without a fight.");
			chaar.setDefeatDialogue("A worthy opponent! Join us on Lincoln more often.");
			chaar.setDirectionAndImage(FACENORTH);
			chaar.setMoney(320);
			chaar.getPostBattleDialogue().add("You have proven your worth. Our guild of LARPers offers a gift.");
			chaar.getPostItemDialogue().add("That's the HM Cut! You can use it to cut down small trees and...");
			chaar.getPostItemDialogue().add("...white trashcans. But remember, once you teach it to a Pokemon...");
			chaar.getPostItemDialogue().add("...it can't be forgotten, unless you talk to the registrar in JWW.");
			chaar.setGift(new Item.HM01_Cut());
			chaar.setType("LARPer");
			chaar.setName("Link");
			
			//random guy
			Vector<Pokemon> paanchBelt = new Vector<Pokemon>();
			paanchBelt.add(new Pokemon.Butterfree().setWildLevel(20));
			Trainer paanch = new Trainer.Backpacker(paanchBelt);
			paanch.createHome(23,20,0,0,"wander","wander");
			paanch.addDest(19, 19);
			paanch.addDest(23, 20);
			paanch.setLOS(4);
			paanch.getDialogue().add("A new challenger approaches!");
			paanch.setDefeatDialogue("Wish I wore my costume, but it's at the dry cleaners.");
			paanch.setMoney(200);
			paanch.setType("LARPer");
			paanch.setName("David");

			//mario	
			Vector<Pokemon> cheBelt = new Vector<Pokemon>();
			cheBelt.add(new Pokemon.Pidgey().setWildLevel(16));
			cheBelt.add(new Pokemon.Pidgey().setWildLevel(16));
			cheBelt.add(new Pokemon.Pidgey().setWildLevel(16));
			Trainer che = new Trainer.Mario(cheBelt);
			che.createHome(29,15,0,0,"freeze","freeze");
			che.getDialogue().add("It's a-me! Mario.");
			che.setDefeatDialogue("Mamma mia...");
			che.setLOS(4);
			che.setDirectionAndImage(FACEEAST);
			che.setMoney(320);
			che.setType("LARPer");
			che.setName("Mario");
			
			this.getMovingTrainers().add(ek); //1
			this.getMovingTrainers().add(dho); //2
			this.getMovingTrainers().add(theen); //3
			this.getMovingTrainers().add(chaar); //4
			this.getMovingTrainers().add(paanch); //5
			this.getMovingTrainers().add(che); //6

			
			
//			additional people at: 
			
			Trainer larp = new Trainer.Ponytail(null);
			larp.createHome(26,3,0,0,"avoid","avoid");
			larp.addDest(29,5);
			larp.addDest(26,3);
			larp.setFirstDest();
			larp.setStationary(false);
			larp.getDialogue().add("There are a bunch of LARPers on Lincoln Field. I'd watch out.");
			larp.getDialogue().add("LARPing? Live Action Role Playing, highly dangerous...");
			larp.getDialogue().add("...individuals trained in combat. They practice on Lincoln often.");
			this.getMovingTrainers().add(larp); //7
			
			Trainer npc1 = new Trainer.PurpleHatGirl(null);
			npc1.createHome(8,18,0,0,"avoid","avoid");
			npc1.addDest(10,21);
			npc1.addDest(8,18);
			npc1.setFirstDest();
			npc1.setStationary(false);
			npc1.getDialogue().add("I'm not a fan of the Ratty, so I usually go to the VDub.");
			npc1.getDialogue().add("Unfortunately, there was a recent kitchen fire, and they won't let");
			npc1.getDialogue().add("... anyone near it. Maybe someone could put it out with their Pokemon...");
			this.getMovingTrainers().add(npc1); //8
			
			Trainer npc2 = new Trainer.BlackHairBlueDress(null);
			npc2.createHome(3,36,0,0,"avoid","avoid");
			npc2.addDest(5,39);
			npc2.addDest(3,36);
			npc2.setFirstDest();
			npc2.setStationary(false);
			npc2.getDialogue().add("We don't have any big department stores on Thayer, but there are several small stores...");
			npc2.getDialogue().add("... with the same purpose! You should explore and get the vitamins for your...");
			npc2.getDialogue().add("...Pokemon grow to their full maximum ability!");
			this.getMovingTrainers().add(npc2); //9
			
			Trainer npc3 = new Trainer.ShadyGlasses(null);
			npc3.createHome(26,24,0,0,"avoid","avoid");
			npc3.addDest(21,26);
			npc3.addDest(26,24);
			npc3.setFirstDest();
			npc3.setStationary(false);
			npc3.getDialogue().add("The water on Thayer seems to drain at a particular place.");
			npc3.getDialogue().add("I wonder where it leads to...");
			this.getMovingTrainers().add(npc3); //8

			Trainer cut1 = new Trainer.CutBush(_gbs.getPlayer(), this._roomNum, this);
			cut1.createHome(1, 15);
			this.getMovingTrainers().add(cut1);
			Trainer cut2 = new Trainer.CutBush(_gbs.getPlayer(), this._roomNum, this);
			cut2.createHome(1, 24);
			this.getMovingTrainers().add(cut2);
			
			Trainer cut3 = new Trainer.CutCan(_gbs.getPlayer(), this._roomNum, this);
			cut3.createHome(16, 25);
			this.getMovingTrainers().add(cut3);
			
			Trainer cut4 = new Trainer.CutCan(_gbs.getPlayer(), this._roomNum, this);
			cut4.createHome(15, 25);
			this.getMovingTrainers().add(cut4);
			
			Trainer cut5 = new Trainer.CutCan(_gbs.getPlayer(), this._roomNum, this);
			cut5.createHome(33, 24);
			this.getMovingTrainers().add(cut5);
			
			}
			catch(IOException ioe){}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.MAIN_GREEN;
		
		this.addTrainers();
		this.loadAllEvents();
		
		//POKEMON
		this._wildPokemon = new Vector<Pokemon>();
		//int running = 0;
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Oddish().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Bellsprout().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		for(int i = 15; i <= 17; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		for(int i = 10; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Mankey().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		for(int i = 10; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Meowth().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		for(int i = 14; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		this._wildPokemon.add(new Pokemon.Abra().setWildLevel(7));
		//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		for(int i = 3; i <= 7; i++){
			this._wildPokemon.add(new Pokemon.Jigglypuff().setWildLevel(i));
			//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		}
		this._wildPokemon.add(new Pokemon.Pidgeotto().setWildLevel(17));
		//SysOut.print("Total " + (running++) + ": "+ this._wildPokemon.get(running-1).getName() + ", " + this._wildPokemon.get(running-1).getLevel());
		
		this.xConstant=194;
		this.yConstant=56;
		this._mapX=172;
		this._mapY=299;
		this._outdoors=true;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(true);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Lincoln Field";
		this._roomNum = 35;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/LincolnField/LincolnField Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/LincolnField/LincolnField Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(35,46);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/LincolnField.cmap"));
		for(int i = 0; i < 46; i++){
			for(int i2 = 0; i2 < 35; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawOptimalImage(g2, _roomO);
		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 3
 		if((xInd == 23) && (yInd == 0)){
 			super.enterRoom(_gbs.ROUTE_3, 16, 53, FACENORTH);
 		}
 		//Ashamu
 		if((xInd == 16) && (yInd == 12)){
 			super.enterRoom(_gbs.ASHAMU, 2, 6, FACENORTH);
 		}
 		//Metcalf
 		if((xInd == 32) && (yInd == 11)){
 			super.enterRoom(_gbs.METCALF, 7, 9, FACENORTH);
 		}
 		
 		//LeedsLeft
 		if((xInd == 8) && (yInd == 13)){
 			super.enterRoom(_gbs.LEEDS, 2, 5, FACENORTH);
 		}
 		//LeedsRight
 		if((xInd == 14) && (yInd == 13)){
 			super.enterRoom(_gbs.LEEDS, 6, 5, FACENORTH);
 		}

 		//Littlefield
 		if((xInd == 7) && (yInd == 37)){
 			super.enterRoom(_gbs.LITTLEFIELD, 7, 1, FACESOUTH);
 		}
 		//Greenhouse
 		if((xInd == 17) && (yInd == 6)){
 			super.enterRoom(_gbs.GREENHOUSE, 4, 6, FACENORTH);
 		}
 		//Thayer
 		if((xInd == 32) && (yInd<=21 && yInd>=18)){
 			super.enterRoom(_gbs.THAYER_SOUTH, 9, yInd-18+21, FACEEAST);
 		}
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
 		if((xInd == 0) && (yInd == 15)){
 			super.enterRoomSeamless(_gbs.MAIN_GREEN, 35, 14, FACEWEST);
 		}
 		if((xInd == 0) && (yInd == 24)){
 			super.enterRoomSeamless(_gbs.MAIN_GREEN, 35, 23, FACEWEST);
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
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		
		if(random < 13.3){
			randomEnemy = _wildPokemon.get((int)(4*Math.random())); //oddish
		}
		else if(random < 26.6){
			randomEnemy = _wildPokemon.get(4+(int)(4*Math.random())); //bellsprout
		}
		else if(random < 38){
			randomEnemy = _wildPokemon.get(8+(int)(4*Math.random())); //pidgey x2
		}
		else if(random < 50){
			randomEnemy = _wildPokemon.get(12+(int)(4*Math.random()));
		}
		else if(random < 60){
			randomEnemy = _wildPokemon.get(16+(int)(3*Math.random())); //pidgey
		}
		else if(random < 68.3){
			randomEnemy = _wildPokemon.get(19+(int)(7*Math.random())); //mankey
		}
		else if(random < 77){
			randomEnemy = _wildPokemon.get(26+(int)(7*Math.random())); //meowth
		}
		else if(random < 86){
			randomEnemy = _wildPokemon.get(33+(int)(3*Math.random())); //rattata
		}
		else if(random < 91){
			randomEnemy = _wildPokemon.get(36); //abra
		}
		else if(random < 94){
			randomEnemy = _wildPokemon.get(37+(int)(4*Math.random())); //jigglypuff
		}
		else{
			randomEnemy = _wildPokemon.get(41); //pidgeotto
		}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);

		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	public void Right(){
	//	SysOut.print("WTF");
		super.Right();
	}
}