package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


@SuppressWarnings("serial")
public class RuthGym extends PokePanel2 {
	private BufferedImage intro, start, sub, door, main;
	private ImageIcon doorGif, subGif, mainGif;
	private final int RUTH=0, BERG=1, LASS=2,
			AX1=5,
			AY1=6,
			AX2=15,
			AY2=6,
			BX1=5,
			BY1=10,
			BX2=13,
			BY2=10;

	public static boolean gymStart=false;
	
	public RuthGym(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(4);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public RuthGym(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(4);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Vector <Pokemon> ruthBelt = new Vector<Pokemon>();
				ruthBelt.add(new Pokemon.Dragonair().setWildLevel(43));
				ruthBelt.add(new Pokemon.Dragonair().setWildLevel(45));
				ruthBelt.add(new Pokemon.Dragonair().setWildLevel(47));
				ruthBelt.add(new Pokemon.Dragonite().setWildLevel(50));
				
				ruthBelt.get(0).getAttacks().set(0, new Attack.Thunderbolt());
				ruthBelt.get(1).getAttacks().set(0, new Attack.IceBeam());
				ruthBelt.get(2).getAttacks().set(0, new Attack.Surf());
				ruthBelt.get(3).getAttacks().set(0, new Attack.HyperBeam());
				
				Trainer ruth = new Trainer.RuthGymLeader(ruthBelt);
				
				ruth.createHome(10, 7);
				ruth.setDirectionAndImage(FACENORTH);
				ruth.getDialogue().add("Ruth Simmons: Good afternoon " + _gbs.getPlayer().getPlayerName() + ".");
				ruth.getDialogue().add("From the day you walked in through the Van Wickle Gates,");
				ruth.getDialogue().add("I have watched you work hard and grow with your friends.");
				ruth.getDialogue().add("You have proven yourself by facing the 7 gym leaders...");
				ruth.getDialogue().add("...showing us all that you are among Brown's finest.");
				ruth.setDefeatDialogue("Excellent. I applaud your victory.");
				ruth.getPostBattleDialogue().add("Very well done, " + _gbs.getPlayer().getPlayerName() + ".");
				ruth.getPostBattleDialogue().add("I proudly present you with your Bachelor's Degree.");
				//Get badge
				ruth.setGift(new Item.TM23_Dragon_Rage());
				ruth.getPostBattleDialogue().add(_gbs.getPlayer().getPlayerName() + " received the Diploma Badge!");
				ruth.getPostBattleDialogue().add("In addition, please take this, it will help you in your career.");
				ruth.getPostItemDialogue().add("That is TM23, Dragon Rage.");
				ruth.getPostItemDialogue().add("You will be expected to be a reliable employee in the real world.");
				ruth.getPostItemDialogue().add("That TM delivers the same damage every time.");
				ruth.getPostItemDialogue().add("*sigh*");
				ruth.getPostItemDialogue().add(_gbs.getPlayer().getPlayerName() +", after my battle with you,");
				ruth.getPostItemDialogue().add("I think its finally time for me to step down as president.");
				ruth.getPostItemDialogue().add("I recently decided that this is the ideal time both for Brown");
				ruth.getPostItemDialogue().add("and for me personally to begin the process...");
				ruth.getPostItemDialogue().add("...of transitioning to new leadership.");
				ruth.getPostItemDialogue().add("President Paxson will be taking over in the fall.");
				ruth.getPostItemDialogue().add("I've had an incredible time here at Brown...");
				ruth.getPostItemDialogue().add("...and I'm honored to be graduating with the class of 2012.");

				ruth.setMoney(4000);
				ruth.setVanishing(false);
				
				
				Vector <Pokemon> bergBelt = new Vector<Pokemon>();
				bergBelt.add(new Pokemon.Horsea().setWildLevel(42));
				bergBelt.add(new Pokemon.Seadra().setWildLevel(44));
				
				Trainer berg = new Trainer.DirtyBlondeGirl(bergBelt);
				berg.setLOS(2);
				berg.getDialogue().add("What are your plans for after Brown?");
				berg.getDialogue().add("Before you begin to look too far into the future...");
				berg.getDialogue().add("...try to stay focused on the present.");
				berg.setDefeatDialogue("Don't forget to take the Senior Exit Survey!");
				berg.setType("Dean");
				berg.setName("Bergeron");
				berg.setMoney(1000);
				if(gymStart){
					berg.createHome(6, 4, 0, 0,"","");
				}
				else{
					berg.createHome(6, 4, 0, 2000,"","");
				}
				//2200 dollars
				
				Vector <Pokemon> lassBelt = new Vector<Pokemon>();
				lassBelt.add(new Pokemon.Dratini().setWildLevel(43));
				lassBelt.add(new Pokemon.Dratini().setWildLevel(45));
				Trainer lassonde = new Trainer.GlassesProfessor(lassBelt);
				lassonde.setLOS(2);
				lassonde.getDialogue().add("If you lose too many trainer battles...");
				lassonde.getDialogue().add("...you'll be put on academic warning.");
				lassonde.setDefeatDialogue("Well, I guess you don't need to be worried, then.");
				lassonde.setType("Dean");
				lassonde.setName("Lassonde");
				lassonde.setMoney(1500);
				if(gymStart){
					lassonde.createHome(14, 4, 0, 0,"","");
				}
				else{
					lassonde.createHome(14, 4, 0, 2000,"","");
				}
				this.getMovingTrainers().add(ruth); //0
				this.getMovingTrainers().add(berg); //1
				this.getMovingTrainers().add(lassonde); //2
				//2200 dollars
				
				//RUTH
				//berg
				//lassonde
				//if not gymstart, draw them 2000 pixels below.
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void afterBattle(){
		super.afterBattle();
		
		if(_interruptedTrainer == RUTH){
			_gbs.getPlayer().defeatGymLeader(8);
			gymStart = true;
			this.createGrid();
		}
	}
	
	
	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(RUTH).isDefeated()){
			this.getCheckList().set(0, 1);
		}
		if(this.getMovingTrainers().get(BERG).isDefeated()){
			this.getCheckList().set(1, 1);
		}
		if(this.getMovingTrainers().get(LASS).isDefeated()){
			this.getCheckList().set(2, 1);
		}
		if(gymStart){
			this.getCheckList().set(3, 1);
		}
		else{
			this.getMovingTrainers().get(BERG).createHome(6, 4, 0, 2000,"","");
			this.getMovingTrainers().get(LASS).createHome(14, 4, 0, 2000,"","");
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList()!=null){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(RUTH).defeatAndPostItemize();
				this.getMovingTrainers().get(BERG).defeatAndPostBattle();
				this.getMovingTrainers().get(LASS).defeatAndPostBattle();
			}
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(BERG).defeatAndPostBattle();
			}
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(LASS).defeatAndPostBattle();
			}
			if(this.getCheckList().get(3)==1){
				gymStart=true;
			}
			else{
				gymStart=false;
			}
		}
		
	}
	
	public void createBaseRoom(){
		
		
		this.addTrainers();
		this.loadAllEvents();
		
		if(gymStart){
			this.song = M2.RUTH_GYM;
		}
		else{
			this.song = M2.SILENCE;
		}
		
		this.xConstant=210;
		this.yConstant=147;
		
		this.setBattleBG(NewBattleScreen.NIGHT);
		
		this._mapX=130;
		this._mapY=299;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "President's Office";
		this._roomNum = _gbs.RUTH_GYM;
		try{
			if(intro == null){
				intro = ImageIO.read(this.getClass().getResource("/PokemonFiles/RuthGym/RuthGym Intro.png"));
				door = ImageIO.read(this.getClass().getResource("/PokemonFiles/RuthGym/RuthGym DoorField.png"));
				sub = ImageIO.read(this.getClass().getResource("/PokemonFiles/RuthGym/RuthGym Sublasers.png"));
				main = ImageIO.read(this.getClass().getResource("/PokemonFiles/RuthGym/RuthGym MainField.png"));
				start = ImageIO.read(this.getClass().getResource("/PokemonFiles/RuthGym/RuthGym Start.png"));
				doorGif = new ImageIcon(this.getClass().getResource("/PokemonFiles/RuthGym/door.gif"));
				subGif = new ImageIcon(this.getClass().getResource("/PokemonFiles/RuthGym/sub.gif"));
				mainGif = new ImageIcon(this.getClass().getResource("/PokemonFiles/RuthGym/main.gif"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(20,22);
		Scanner scan;
		if(gymStart){
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/RuthGymAfter.cmap"));
		}
		else{
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/RuthGymBefore.cmap"));
		}
		for(int i = 0; i < 22; i++){
			for(int i2 = 0; i2 < 20; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		if(!_gbs.getPlayer().isGymLeaderDefeated(8)){
			this._room._roomGrid[9][2]='N';
			this._room._roomGrid[10][2]='N';
			this._room._roomGrid[11][2]='N';
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if(gymStart){
			g2.drawImage(start, null, this._xSpace, this._ySpace);
			

			
			

			this.drawPlayer(g2);
			this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
			
			g2.drawImage(sub, null, this._xSpace, this._ySpace);
			//subGif.paintIcon(this, g2, this._xSpace, this._ySpace);
			if(!this.getMovingTrainers().get(RUTH).isDefeated()){
				g2.drawImage(door, null, this._xSpace, this._ySpace);
			//doorGif.paintIcon(this, g2, this._xSpace, this._ySpace);
			}
			g2.drawImage(main, null, this._xSpace, this._ySpace);
			//mainGif.paintIcon(this, g2, this._xSpace, this._ySpace);
		}
		else{
			g2.drawImage(intro, null, this._xSpace, this._ySpace);
			this.drawPlayer(g2);
			this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		}




		this.drawAllGenerics(g2);
		
	}
	
	public void enterRoom(int xInd, int yInd){
		//Univ Hall
 		if((xInd == 10) && (yInd == 21)){
 			super.enterRoom(_gbs.UNIVERSITY_HALL_LOBBY, 4, 0, FACESOUTH);
 		}
		//Quiet Green
 		if((xInd == 10) && (yInd == 1)){
 			super.enterRoom(_gbs.QUIET_GREEN, 22, 4, FACESOUTH);
 		}
		
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		if((xInd == AX1) && (yInd == AY1)){
			this.customTeleportTo(this._roomNum, AX2, AY2);
		}
		if((xInd == AX2) && (yInd == AY2)){
			this.customTeleportTo(this._roomNum, AX1, AY1);
		}
		
		
		if((xInd == BX1) && (yInd == BY1)){
			this.customTeleportTo(this._roomNum, BX2, BY2);
		}
		if((xInd == BX2) && (yInd == BY2)){
			this.customTeleportTo(this._roomNum, BX1, BY1);
		}
	}
	
	public void Up(){
		if(!_menuVisible){
			if(this._yIndex == 12 && !gymStart){
				gymStart = true;
				this.scanForAllEvents(); //necessary.
				this.createBaseRoom();
				this.song = M2.RUTH_GYM;
				M2.playBG(M2.RUTH_GYM);
			}
		}
		super.Up();
		
		
	}
	

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			if(this._interruptedTrainer==RUTH && this.getMovingTrainers().get(RUTH).isDefeated() && this.getMovingTrainers().get(RUTH).getGift()!=null && (_NPCpage==1)){
				M2.playFX(M2.BADGE);
			}
			
			this.repaint();
		}

		super.A_Button();
	}
}