package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class BarHolGym extends PokePanel2{

	//private BufferedImage bg;
	private final int HAZELTINE=3;
	
	public BarHolGym(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(4);
		this.createBaseRoom();
	}
	
	public BarHolGym(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		
		this.initializeEventVector(4);
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		SysOut.print("Loaded?");
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < 4; i++){
				SysOut.print("Trainer " + i + " loaded...");
				if(this.getCheckList().get(i) == 1){
					_movingTrainers.get(i).defeat();
					
					SysOut.print("Trainer " + i + " was previously defeated.");
					_movingTrainers.get(i).getDialogue().clear();
					_movingTrainers.get(i).getDialogue().add(_movingTrainers.get(i).getDefeatDialogue());
					_movingTrainers.get(i).setGift(null);
				}
			}
		}
	}
	
	public void scanForAllEvents(){
		SysOut.print("Scanned?");
		for(int i = 0; i < 4; i++){
			SysOut.print("Trainer " + i + " scanned...");
			if(this._movingTrainers.get(i).isDefeated()){
				this.getCheckList().set(i,1);
				SysOut.print("Trainer " + i + " defeated.");
			}
			else{
				this.getCheckList().set(i,0);
				SysOut.print("Trainer " + i + " not defeated.");
			}
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		
		try{
			Vector<Pokemon> mattBelt = new Vector<Pokemon>();
			mattBelt.add(new Pokemon.Machop().setWildLevel(21));
			mattBelt.add(new Pokemon.Machop().setWildLevel(21));
			mattBelt.add(new Pokemon.Mankey().setWildLevel(21));
			mattBelt.add(new Pokemon.Mankey().setWildLevel(21));
			Trainer matt = new Trainer.NiceHair(mattBelt);
			matt.createHome(1,10,0,0,"","");
			matt.setDirectionAndImage(FACEWEST);
			matt.setType("ENGN 90 TA");
			matt.setName("Matt");
			matt.setLOS(5);
			matt.setMoney(840);
			matt.getDialogue().add("Did you miss section last week? What case did we cover?");
			matt.setDefeatDialogue("Nope, I see you're all caught up!");
		//	matt.getPostBattleDialogue().add("");
			_movingTrainers.add(matt); //0
		
			Vector<Pokemon> biddleBelt = new Vector<Pokemon>();
			biddleBelt.add(new Pokemon.Hitmonchan().setWildLevel(23));
			biddleBelt.add(new Pokemon.Hitmonlee().setWildLevel(23));
			Trainer biddle = new Trainer.BlondeDude(biddleBelt);
			biddle.createHome(4, 6, 0, -4, "freeze", "freeze");
			biddle.setDirectionAndImage(FACEEAST);
			biddle.setLOS(5);
			biddle.setType("Businessman");
			biddle.setName("Charlie");
			biddle.setMoney(1820);
			biddle.getDialogue().add("HOLD THE PHONE!");
			biddle.getDialogue().add("What if someone made athletic gear for Pokemon?");
			biddle.setDefeatDialogue("Better head to BetaSpring...");
		//	biddle.getPostBattleDialogue().add("");
			_movingTrainers.add(biddle); //1
		
			Vector<Pokemon> edBelt = new Vector<Pokemon>();
			edBelt.add(new Pokemon.Poliwrath().setWildLevel(26));
			Trainer edjola = new Trainer.BlackGirl(edBelt);
			edjola.createHome(7,4,0,0,"","");
			edjola.setDirectionAndImage(FACESOUTH);
			edjola.setLOS(2);
			edjola.setMoney(1470);
			edjola.setType("ENGN 90 Head TA");
			edjola.setName("Edjola");
			edjola.getDialogue().add("Welcome to ENGN 90! Can I see your business plan?");
			edjola.setDefeatDialogue("Hmmm, it's no MunchCard, but it'll do!");
			_movingTrainers.add(edjola); //2
		
		
			Vector<Pokemon> hazelBelt = new Vector<Pokemon>();
			hazelBelt.add(new Pokemon.Primeape().setWildLevel(29));
			hazelBelt.add(new Pokemon.Hitmonchan().setWildLevel(24));
			hazelBelt.add(new Pokemon.Machoke().setWildLevel(29));
			Trainer hazeltine = new Trainer.Hazeltine(hazelBelt);
			hazeltine.createHome(7, 5, 0, 0, "", "");
			hazeltine.setDirectionAndImage(FACENORTH);
			hazeltine.getDialogue().add("The world of business is a battlefield,");
			hazeltine.getDialogue().add("where only the strongest succeed.");
			hazeltine.getDialogue().add("You have to be tough, mentally and physically to overcome challenges.");
			hazeltine.getDialogue().add("I train with Fighting Pokemon who can handle real-world combat.");
			hazeltine.getDialogue().add("...Am I making any god-damned sense?");
			hazeltine.setDefeatDialogue("Are we still friends?");
			hazeltine.getPostBattleDialogue().add("I can see you're going to be a great Pokemon manager...err, master.");
			hazeltine.getPostBattleDialogue().add("Here's something to show people that you defeated me.");
			hazeltine.getPostBattleDialogue().add(_gbs.getPlayer().getPlayerName() + " received the Nectar Badge!");
			hazeltine.getPostBattleDialogue().add("And here's a TM I put together back in my days as an engineer.");
			hazeltine.getPostItemDialogue().add("That's TM01, MEGA-PUNCH!");
			hazeltine.getPostItemDialogue().add("It'll knock some sense into your students...err, I mean opponents.");
			
		
			_movingTrainers.add(hazeltine); //3
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void createBaseRoom(){
		this._roomNum = _gbs.BARHOL_GYM;
		this.setBackground(Color.BLACK);
		this.description = "ENGN 90 Classroom";
		
		this._mapX = 270;
		this._mapY = 305;
		
		this.xConstant = 3;
		this.setBattleBG(NewBattleScreen.BARHOL_GYM);
		this.addTrainers();
		
		this.loadAllEvents();
		
		this.song = M2.GYM;
		
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolGym/BarHolGym.png"));
			}
		}
		catch(IOException ioe){ioe.printStackTrace();}
		
		this.createGrid();
		
	}
	
	public void createGrid(){
		this._room = new Room(10, 16);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BHGym.cmap"));
		for(int i = 0; i < 16; i++){
			for(int i2 = 0; i2 < 10; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		if(yInd == 15){
			//DONT CHANGE THE X-INDEX FOR ENTRY
			if(xInd == 1 || xInd == 2){
				super.enterRoom(_gbs.BARHOL_HALLWAY, 8, 7, FACENORTH);
			}
			else if(xInd == 7 || xInd == 8){
				super.enterRoom(_gbs.BARHOL_HALLWAY, 8, 7, FACENORTH);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void afterBattle(){
		super.afterBattle();
		if(_interruptedTrainer == 3){
			_gbs.getPlayer().defeatGymLeader(4);
		}
	}
	
	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			if(this._interruptedTrainer==HAZELTINE && this.getMovingTrainers().get(HAZELTINE).isDefeated() && this.getMovingTrainers().get(HAZELTINE).getGift()!=null && (_NPCpage==1)){
				M2.playFX(M2.BADGE);
			}
			
			this.repaint();
		}

		super.A_Button();
	}
}
