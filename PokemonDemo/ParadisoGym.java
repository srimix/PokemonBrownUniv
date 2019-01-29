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
public class ParadisoGym extends PokePanel2 {
	//private BufferedImage _roomSource;
	private Trainer paradiso, trainer1, trainer2, trainer3;
	private final int TRAINER1=0, TRAINER2=1, TRAINER3=2, PARADISO=3;
	
	public ParadisoGym(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(4);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public ParadisoGym(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(4);
		
		this.createBaseRoom();
		
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < 3; i++){
				if(this.getCheckList().get(i) == 1){
					Trainer moving = this._movingTrainers.get(i);
					moving.defeat();
					moving.getDialogue().clear();
					moving.getDialogue().add(moving.getDefeatDialogue());
				}			
			}
			
			//Paradiso specifically
			if(this.getCheckList().get(3) == 1){
				Trainer paradiso = this.getMovingTrainers().get(3);
				paradiso.defeat();
				paradiso.setGift(null);
				paradiso.getDialogue().clear();
				paradiso.getDialogue().add(paradiso.getPostItemDialogue().get(0));
				
				this.getMovingTrainers().get(0).defeat();
				this.getMovingTrainers().get(1).defeat();
				this.getMovingTrainers().get(2).defeat();
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
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Vector<Pokemon> paradisoBelt = new Vector<Pokemon>();
				paradisoBelt.add(new Pokemon.Drowzee().setWildLevel(18));
				paradisoBelt.add(new Pokemon.Kadabra().setWildLevel(21));
				paradiso = new Trainer.Paradiso(paradisoBelt);
				paradiso.createHome(0, 4, 0, -1, "freeze", "freeze");
				paradiso.setStationary(true);
				paradiso.setLOS(0);
				paradiso.getDialogue().add("Prof. Paradiso: Welcome " + _gbs.getPlayer().getPlayerName() + " to the Neuro department!");
				paradiso.getDialogue().add("Here we study topics of the brain, ranging from neurons to cognition.");
				paradiso.getDialogue().add("Since the discovery of Psychic Pokemon, we have been very...");
				paradiso.getDialogue().add("...intrigued by the power of the mind and will.");
				paradiso.getDialogue().add("Allow me to show you the true strength of the brain!");
				paradiso.setDefeatDialogue("A very intriguing result.");
				paradiso.getPostBattleDialogue().add("I gave it my best, but you have shown your mind is robust!");
				paradiso.getPostBattleDialogue().add("You earned this, a mark of your accomplishment.");
				//Get badge
				paradiso.getPostBattleDialogue().add(_gbs.getPlayer().getPlayerName() + " received the Cortex Badge!");
				paradiso.getPostBattleDialogue().add("I'm very impressed, but in lieu of a recommendation letter:");
				paradiso.getPostItemDialogue().add("That's TM46, Psywave! A key finding of our latest publication.");
				paradiso.getPostItemDialogue().add("We don't fully understand it yet, but it deals random damage.");
				paradiso.setBelt(paradisoBelt);
				paradiso.setMoney(2100);
				paradiso.setVanishing(false);
				
				
				//Eventually set their LOS to 1.
				Vector<Pokemon> trainer1Belt = new Vector<Pokemon>();
				trainer1Belt.add(new Pokemon.Jynx().setWildLevel(16));
				trainer1Belt.add(new Pokemon.Jynx().setWildLevel(16));
				
				trainer1 = new Trainer.DirtyBlondeGirl(trainer1Belt);
				trainer1.createHome(2, 11, 0, 0, "", "");
				trainer1.setStationary(true);
				trainer1.setDirectionAndImage(FACEWEST);
				trainer1.setLOS(2);
				trainer1.getDialogue().clear();
				trainer1.getDialogue().add("I study twin-development! Aren't they cute?");
				trainer1.setDefeatDialogue("Did you know that kids with Pokemon develop faster?");
				trainer1.setType("Undergrad");
				trainer1.setName("Kate");
				trainer1.setMoney(80);
				trainer1.setVanishing(false);
				trainer1.setBattleImage(TImg.F_BLONDIE);
				
				Vector<Pokemon> trainer2Belt = new Vector<Pokemon>();
				trainer2Belt.add(new Pokemon.Abra().setWildLevel(16));
				trainer2Belt.get(0).getAttacks().add(new Attack.Psywave()); //To balance it, plus its the gym's TM
				trainer2 = new Trainer.Scientist(trainer2Belt);
				trainer2.createHome(2, 10, 0, 0, "", "");
				trainer2.setStationary(true);
				trainer2.setDirectionAndImage(FACEEAST);
				trainer2.setLOS(2);
				trainer2.getDialogue().clear();
				trainer2.getDialogue().add("You're not a master until you've recorded from Aplysia.");
				trainer2.getDialogue().add("Don't consider this field child's play!");
				trainer2.setDefeatDialogue("Well...come check out my spin class at the OMAC later.");
				trainer2.setName("Stein");
				trainer2.setType("Prof.");
				trainer2.setMoney(460);
				trainer2.setVanishing(false);
				trainer2.setBattleImage(TImg.M_PSYCHIC);
				
				Vector<Pokemon> trainer3Belt = new Vector<Pokemon>();
				trainer3Belt.add(new Pokemon.Slowbro().setWildLevel(19));
				trainer3 = new Trainer.BlackDude(trainer3Belt);
				trainer3.createHome(2, 9, 0, 0, "", "");
				trainer3.setStationary(true);
				trainer3.setDirectionAndImage(FACEWEST);
				trainer3.setLOS(2);
				trainer3.getDialogue().clear();
				trainer3.getDialogue().add("Yo, wanna hit up the GCB after I defeat you?");
				trainer3.setDefeatDialogue("Sigh. Back to my thesis...");
				trainer3.setName("Rohan");
				trainer3.setType("Undergrad");
				trainer3.setMoney(380);
				trainer3.setVanishing(false);
				trainer3.setBattleImage(TImg.M_PSYCHIC);
				
				this._movingTrainers.add(trainer1);//0
				this._movingTrainers.add(trainer2);//1
				this._movingTrainers.add(trainer3);//2
				this._movingTrainers.add(paradiso);//3  FINAL INT
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.GYM;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=122;
		this.yConstant=0;
		this._mapX=137;
		this._mapY=240;
		
		this.setBattleBG(NewBattleScreen.PARADISO);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Paradiso's Gym";
		this._roomNum = 30;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/ParadisoGym/ParadisoGym.png"));						
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(4,20);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/ParadisoGym.cmap"));
		for(int i = 0; i < 20; i++){
			for(int i2 = 0; i2 < 4; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawPlayerCustom(g2, 176-100, 168);
		this.drawPlayerCustom(g2, 176+100, 168);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Biomed 2nd floor
 		if(((xInd == 1)||(xInd == 2)) && (yInd == 19)){
 			super.enterRoom(_gbs.BIOMED_2, 2, 1, FACESOUTH);
 		}
	}
	
	public void afterBattle(){
		super.afterBattle();
		//Gail is first trainer in the vector
		if (_interruptedTrainer==PARADISO){
			_gbs.getPlayer().defeatGymLeader(2);
			this.getMovingTrainers().get(TRAINER1).defeat();
			this.getMovingTrainers().get(TRAINER2).defeat();
			this.getMovingTrainers().get(TRAINER3).defeat();
			
		}
	}

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			if(this._interruptedTrainer==PARADISO && this.getMovingTrainers().get(PARADISO).isDefeated() && this.getMovingTrainers().get(PARADISO).getGift()!=null && (_NPCpage==1)){
				M2.playFX(M2.BADGE);
			}
			
			this.repaint();
		}

		super.A_Button();
	}
}