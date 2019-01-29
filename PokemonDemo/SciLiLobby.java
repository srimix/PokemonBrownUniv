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
public class SciLiLobby extends PokePanel2 {
	private BufferedImage _roomO;
	
	public SciLiLobby(GameBoyScreen gbs){
		super(gbs);
		
		SysOut.print("SCILI LOBBY?");
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		

	}

	public SciLiLobby(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0) == 1){
				this._movingTrainers.get(0).defeat();
			}
			if(this.getCheckList().get(1) == 1){
				this._movingTrainers.get(1).defeat();
			}
			if(this.getCheckList().get(2) ==1){
				this._movingTrainers.get(3).defeat();
			}
		}
	}
	
	public void scanForAllEvents(){
		if(this._movingTrainers.get(0).isDefeated()){
			this.getCheckList().set(0,1);
		}
		if(this._movingTrainers.get(1).isDefeated()){
			this.getCheckList().set(1,1);
		}
		if(this._movingTrainers.get(3).isDefeated()){
			this.getCheckList().set(2,1);
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try{
				
				Trainer i1 = new Trainer.ItemObject(new Item.FullRestore());
				i1.createHome(10, 4, -1000, -1000, "", "");
				_movingTrainers.add(i1);
				
				Trainer i2 = new Trainer.ItemObject(new Item.XSpecial());
				i2.createHome(14, 11, -1000, -1000, "", "");
				_movingTrainers.add(i2);
				
				Trainer guard = new Trainer.Cop(null);
				guard.createHome(6,6,-1,-5,"freeze","freeze");
				guard.addDest(6, 7);
				guard.setFirstDest();
				
				if(_gbs.getPlayer().getAllItems().get(84).getRemaining() == 0){
					guard.getDialogue().add("Thank goodness you've arrived!");
					guard.getDialogue().add("Ghosts have taken over the SciLi...");
					guard.getDialogue().add("...and students have no access to Media Services on the top floor!");
					if(!_gbs.getPlayer().isGymLeaderDefeated(5)){
						guard.getDialogue().add("Oh, it doesn't look like you have many badges...");
						guard.getDialogue().add("Why don't you go find somebody stronger?");
					} else{
						guard.getDialogue().add("Please hurry! I'll swipe you in...");
					}
				}
				else{
					guard.getDialogue().add("Thank you so much for helping us with those meddlesome ghosts!");
					guard.getDialogue().add("What? It was the mafia the whole time?");
					guard.getDialogue().add("I'll have DPS look into it...");
				}
				
				
				if(Bookstore.corrupt){
					Trainer librarian = new Trainer.GlassesGirl(null);
					librarian.createHome(6, 1);
					librarian.getDialogue().add("I'm afraid you can't enter the SciLi right now.");
					librarian.getDialogue().add("There are too many students in here.");
					librarian.getDialogue().add("Apparently nobody can afford Bookstore books anymore...");
					librarian.getDialogue().add("So everyone comes here instead.");
					librarian.getDialogue().add("If you want to get in, find out what's going on...");
					librarian.getDialogue().add("...at the Brown Bookstore up Thayer street.");
					_movingTrainers.add(librarian);
					
					Trainer barr = new Trainer.Barricade();
					barr.createHome(7,1);
					_movingTrainers.add(barr);
				}
				
				Trainer gText = new Trainer.Text();
				gText.createHome(6, 7);
				for(int i = 0; i < guard.getDialogue().size(); i++){
					gText.getDialogue().add(guard.getDialogue().get(i));
				}
				_movingTrainers.add(guard); //0
				//_movingTrainers.add(gText); //1
				
				Trainer swiper = new Trainer.Swiper(_gbs.getPlayer(), this, 5, 4);
				swiper.createHome(8, 6);
				_movingTrainers.add(swiper); //1
				
				Trainer barricade = new Trainer.Barricade();
				barricade.createHome(4, 6);
				_movingTrainers.add(barricade); //2
				
				if(Bookstore.corrupt){
					Trainer librarian = new Trainer.GlassesGirl(null);
					librarian.createHome(6, 1);
					librarian.getDialogue().add("I'm afraid I can't let anyone past this point right now.");
					librarian.getDialogue().add("Prices at the Bookstore have skyrocketed,");
					librarian.getDialogue().add("So students have been flooding in here.");
					librarian.getDialogue().add("The library is already almost full.");
					librarian.getDialogue().add("I can't let you past until this Bookstore nonsense is cleared up.");
					librarian.getDialogue().add("Ghosts? I don't know what you're talking about.");
					_movingTrainers.add(librarian);
					
					Trainer barr = new Trainer.Barricade();
					barr.createHome(7,1);
					_movingTrainers.add(barr);
				}
				
				Trainer kid = new Trainer.Text();
				kid.createHome(4,15);
				kid.getDialogue().add("I'm waiting for the Physical Sciences tour to start.");
				kid.getDialogue().add("This library seems pretty cool.");
				_movingTrainers.add(kid);
				
				Trainer elev = new Trainer.Text();
				elev.createHome(2,4);
				elev.getDialogue().add("The elevators are out of order...");
				elev.getDialogue().add("Please take the stairs.");
				_movingTrainers.add(elev);
				
				Trainer map = new Trainer.Map(this);
				map.createHome(4, 0);
				_movingTrainers.add(map);
				
				Trainer sit1 = new Trainer.DirtyBlondeGirl(null);
				sit1.createHome(12,14,0,0,"freeze","freeze");
				sit1.addDest(11, 14);
				sit1.setFirstDest();
				sit1.setDirectionAndImage(FACEEAST);
				sit1.getDialogue().add("Stupid ghosts making WAY TOO MUCH NOISE in the 00 decibel section...");
				sit1.getDialogue().add("Now I have to study in the Lobby, ugh...");
				_movingTrainers.add(sit1);
				
				Trainer benF = new Trainer.Pirate(null);
				benF.createHome(10, 11, 0, 0, "freeze", "freeze");
				benF.setLOS(10); //THERE IS NO ESCAPE
				benF.setDirectionAndImage(FACEEAST);
				benF.getDialogue().add("Hey, would you like to take my survey?");
				_movingTrainers.add(benF);
				
				Trainer martMenu = new Trainer.MartMenu("SciLi Cafe", this);
				martMenu.createHome(18, 11);
				_movingTrainers.add(martMenu);
				this._thisMartContains.clear();
				this._thisMartContains.add(Item.POKEBALL);
				this._thisMartContains.add(Item.ANTIDOTE);
				this._thisMartContains.add(Item.AWAKENING);
				this._thisMartContains.add(Item.REPEL);
				this._thisMartContains.add(Item.SUPER_POTION);
				this._thisMartContains.add(Item.PARALYZ_HEAL);
				this._thisMartContains.add(Item.ICE_HEAL);
				
				Trainer martPerson = new Trainer.GreenDress(null);
				martPerson.createHome(19, 11);
				martPerson.setDirectionAndImage(FACEEAST);
				_movingTrainers.add(martPerson);
				
				Trainer pc = new  Trainer.PC(_gbs);
				pc.createHome(14, 5);
				_movingTrainers.add(pc);
				
				//TODO Need at least one or two more trainers. Big lobby.
				
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
			
			//4, 15 text-kid on couch
			//2,4 text-elevators broken due to ghosts
			//6,6 cop/security guard
			//8,6 swiper (for access)
			//4,6 barricade (connected to swiper)
			//
	}

	public void createBaseRoom(){
		
		this.song = M2.SCILI;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=183;
		this.yConstant=181;
		this._mapX=134;
		this._mapY=283;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "SciLi Lobby";
		this._roomNum = _gbs.SCILI_LOBBY;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Scili/scililobby.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Scili/scililobbyO.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(22,22);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SciLiLobby.cmap"));
		for(int i = 0; i < 22; i++){
			for(int i2 = 0; i2 < 22; i2++){
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
	}
	
	public void enterRoom(int xInd, int yInd){
		if((xInd == 5 || xInd == 6 || xInd ==7) && yInd == 21){
			super.enterRoom(_gbs.SCIENCE_QUAD, 9, 1, FACESOUTH);
		}
		//7th floor
		if((xInd == 6 || xInd == 7) && yInd == 0){
			super.enterRoom(_gbs.SCILI_7, 1, 11, FACESOUTH);
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