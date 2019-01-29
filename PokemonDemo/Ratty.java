package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Ratty extends PokePanel2 {
	private BufferedImage _roomO;
	private Trainer jose, gail;
	private Timer _postGymBlackout;
	public int _timeCount=0;
	private PokePanel2 _this;
	private int _pgboSpeed=10;
	private boolean _reverted=false;
	@SuppressWarnings("unused")
	private final int GAIL=0, JOSE=1;
	
	public Ratty(GameBoyScreen gbs){
		super(gbs);
		_this=this;
		this.initializeEventVector(3);
		PostGymBlackoutTimer pgbo = new PostGymBlackoutTimer();
		_postGymBlackout = new Timer(_pgboSpeed, pgbo);
		
		this.createBaseRoom();
		_xSpace = -28;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	}

	public Ratty(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		_this=this;
		this.initializeEventVector(3);
		PostGymBlackoutTimer pgbo = new PostGymBlackoutTimer();
		_postGymBlackout = new Timer(_pgboSpeed, pgbo);
		
		this.createBaseRoom();
	}

	public void addTrainers(){
		try{
			_movingTrainers = new Vector<Trainer>();
			
			if(!_gbs.getPlayer().isGymLeaderDefeated(1) || !_reverted){
				Trainer t = new Trainer.Text();
				t.createHome(3, 24, 0, 0, "", "");
				t.getDialogue().add("The doors are shut, and there is any angry crowd on the other side.");
				t.getDialogue().add("Probably best not to open them...");
				
				Trainer t2 = new Trainer.Text();
				t2.createHome(2, 24, 0, 0, "", "");
				t2.getDialogue().add("The doors are shut, and there is any angry crowd on the other side.");
				t2.getDialogue().add("Probably best not to open them...");
				
				//BROKEN, FIX ME....
				
				Vector<Pokemon> gailBelt = new Vector<Pokemon>();
				gailBelt.add(new Pokemon.Bellsprout().setWildLevel(12));
				gailBelt.add(new Pokemon.Gloom().setWildLevel(14));
				gail = new Trainer.Gail(gailBelt);
				gail.createHome(11, 1, -1, -1, "freeze", "freeze");
				gail.setStationary(true);
				gail.setLOS(0);
				gail.getDialogue().add("Hiiiiiiiiiiiiii!!!");
				gail.setDefeatDialogue("Now THAT was a challenge! Very well, I'll resume card-swiping.");
				gail.getPostBattleDialogue().add("That was a refreshing battle.");
				gail.getPostBattleDialogue().add("You deserve this!");
				//Cue automatic "ASH received the Roots and Shoots Badge!"
				gail.getPostBattleDialogue().add(_gbs.getPlayer().getPlayerName() + " received the Roots'n'Shoots Badge!");
				gail.getPostBattleDialogue().add("You're welcome back here anytime. Also take this...");
				gail.getPostItemDialogue().add("That's TM06, Toxic! A Ratty secret recipe.");
				gail.getPostItemDialogue().add("It badly poisons the enemy and causes more damage each turn!");
				gail.setBelt(gailBelt);
				
				Vector<Pokemon> joseBelt = new Vector<Pokemon>();
				joseBelt.add(new Pokemon.Nidoran_M().setWildLevel(11));
				joseBelt.add(new Pokemon.Venonat().setWildLevel(11));
				jose = new Trainer.Jose(joseBelt);
				jose.createHome(12, 3, -1, -4, "freeze", "freeze");
				jose.setStationary(true);
				jose.setLOS(4);
				jose.setDirectionAndImage(FACEEAST);
				jose.getDialogue().add("Is that a fake ID? *flipflipflipflipflip*");
				jose.setDefeatDialogue("Hope the wife and kids are well.");
				jose.getPostBattleDialogue().add("Have a nice day!");
				jose.setBelt(joseBelt);
				
				Trainer sign = new Trainer.Sign();
				sign.getDialogue().add("The back cave.");
				sign.getDialogue().add("Gym Leader: Gail");
				sign.getDialogue().add("The Roots and Shoots Pokemon Master!");
				sign.createHome(9, 8, 0, 0, "","");
				sign.setStationary(true);
	
				Trainer champ = new Trainer.ChampGuy();
				champ.createHome(10, 6, -1, -4, "", "");
				champ.getDialogue().add("Yo, Champ in the making!");
				champ.getDialogue().add("I'm your Meiklejohn peer advisor, and I'm gonna help you succeed!");
				champ.getDialogue().add("I'll be waiting for you at every gym to give you advice on how to");
				champ.getDialogue().add("defeat the gym leader.");
				champ.getDialogue().add("Gail won't swipe anybody in to the Ratty until somebody defeats her.");
				champ.getDialogue().add("She uses Grass and Poison type Pokemon, so try to use Bug or Fire.");
				champ.getDialogue().add("There are a lot of hungry people counting on you! You can do it!");
				champ.setStationary(true);
				
				
				_movingTrainers.add(gail); //DO NOT MOVE HER FROM THIS POSITION.
				_movingTrainers.add(jose); //1
				_movingTrainers.add(t);
				_movingTrainers.add(t2);
				_movingTrainers.add(sign);
				_movingTrainers.add(champ);
			}
			if(_reverted){
				Trainer one = new Trainer.GlassesGuy1(null);
				one.setStationary(true);
				one.setDirectionAndImage(FACEEAST);
				one.createHome(18,19);
				one.getDialogue().add("Has BCA released the Spring Weekend lineup yet?");
				one.getDialogue().add("I heard Gambino is coming.");
				
				Trainer two = new Trainer.GlassesGuy1(null);
				two.setStationary(false);
				two.setDirectionAndImage(FACEEAST);
				two.createHome(5,21,0,0,"wander","wander");
				two.addDest(9,19);
				two.addDest(5, 21);
				two.setFirstDest();
				two.getDialogue().add("Ever use a Repel? They prevent wild pokemon fights for some time.");
				//two.getDialogue().add("But then it wears off and I feel afraid to walk down Brook at night...");
				two.getDialogue().add("You can find them in certain PokeMarts.");
				
				Trainer three = new Trainer.Ponytail(null);
				three.setStationary(true);
				three.setDirectionAndImage(FACENORTH);
				three.createHome(17,18);
				
				Trainer four = new Trainer.GraySkirt(null);
				four.setStationary(false);
				four.setDirectionAndImage(FACEWEST);
				four.createHome(8,23,0,0,"wander","wander");
				four.addDest(4,21);
				four.addDest(8,23);
				four.setFirstDest();
				four.getDialogue().add("I'm going to stay here from 7:30 am to 7:30 pm. I can do it!");
				
				Trainer five = new Trainer.BlondeDude(null);
				five.setStationary(true);
				five.setDirectionAndImage(FACEEAST);
				five.createHome(15,15);
				five.getDialogue().add("I'm actually off meal plan.");
				five.getDialogue().add("...No, of course my backpack isn't full of Ratty cereal and fruit.");
				five.getDialogue().add("(Inserts six kiwis into jacket.)");
				
				Trainer six = new Trainer.GlassesGirl(null);
				six.setStationary(true);
				six.setDirectionAndImage(FACEEAST);				
				six.createHome(17,15);
				six.getDialogue().add("");
				
				Trainer seven = new Trainer.BlackGirl(null);
				seven.setStationary(true);
				seven.setDirectionAndImage(FACEEAST);
				seven.createHome(16,15,0,0,"wander","wander");
				
				Trainer eight = new Trainer.BlackDude(null);
				eight.setStationary(true);
				eight.setDirectionAndImage(FACEWEST);
				eight.createHome(7,19);
				eight.getDialogue().add("Sshhh...I'm trying to take some Ratty trays for mudsliding.");
				
				Trainer nine = new Trainer.ChubbyGuy(null);
				nine.setStationary(true);
				nine.setDirectionAndImage(FACEWEST);
				nine.createHome(2,19);
				nine.getDialogue().add("I can't tell if this table is taken...");
				nine.getDialogue().add("Well, it's mine now. Oh...were you sitting here?");
				
				Trainer ten = new Trainer.BlueBro(null);
				ten.setStationary(true);
				ten.setDirectionAndImage(FACEEAST);
				ten.createHome(3,10);
				ten.getDialogue().add("Wait. The RATTY has chicken fingers? Preposterous.");
				
				Trainer eleven = new Trainer.RedAndYellow(null);
				eleven.setStationary(true);
				eleven.setDirectionAndImage(FACENORTH);
				eleven.createHome(8,9);
				eleven.getDialogue().add("You know what I miss sometimes? Roba Dolce.");
				eleven.getDialogue().add("Ah well, Chipotle is great too.");
				
				Trainer twelve = new Trainer.DirtyBlondeGirl(null);
				twelve.setStationary(true);
				twelve.setDirectionAndImage(FACEWEST);
				twelve.createHome(15,11);
				twelve.getDialogue().add("I have 236 flex points left...");
				twelve.getDialogue().add("I think I'll head to the Campus Market and buy 100 Powerades.");
				
				Trainer thirteen = new Trainer.GlassesGuy1(null);
				thirteen.setStationary(true);
				thirteen.setDirectionAndImage(FACENORTH);
				thirteen.createHome(19,12);
				thirteen.getDialogue().add("Plate of fries, every day. Plain or sweet potato, I can't get enough.");
				thirteen.getDialogue().add("Freshman 15? Come at me bro.");
				
				
				Trainer fourteen = new Trainer.GlassesGuy1(null);
				fourteen.setStationary(false);
				fourteen.setDirectionAndImage(FACENORTH);
				fourteen.createHome(11,3,0,0,"wander","wander");
				fourteen.addDest(11, 3);
				fourteen.addDest(11, 4);
				fourteen.addDest(10, 3);
				fourteen.addDest(11, 2);
				fourteen.setFirstDest();
				fourteen.setPause(false);
				fourteen.getDialogue().add("I drank too much of the vending cappuccino.");
				fourteen.getDialogue().add("So. Dang. Hyper...");
				
				Trainer niceGail = new Trainer.NiceGail(null);
				niceGail.createHome(2,21,-5,-5,"","");
				niceGail.getDialogue().add("That was a wonderful battle.");
				niceGail.getDialogue().add("Please do come again!");
				niceGail.getDialogue().add("And I hope you have a nice day :D");
				
				this._movingTrainers.add(one);
				this._movingTrainers.add(two);
				this._movingTrainers.add(three);
				this._movingTrainers.add(four);
				this._movingTrainers.add(five);
				this._movingTrainers.add(six);
				this._movingTrainers.add(seven);
				this._movingTrainers.add(eight);
				this._movingTrainers.add(nine);
				this._movingTrainers.add(ten);
				this._movingTrainers.add(eleven);
				this._movingTrainers.add(twelve);
				this._movingTrainers.add(thirteen);
				this._movingTrainers.add(fourteen);
				this._movingTrainers.add(niceGail);
			}
		}
		catch(IOException ioe){
			
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0)==1 && jose!=null){
				jose.defeat();
				jose.getDialogue().clear();
				jose.getDialogue().add(jose.getPostBattleDialogue().get(0));
			}
			if(this.getCheckList().get(1)==1 && gail!=null){
				gail.defeat();
				gail.setGift(null);
				gail.getDialogue().clear();
				gail.getDialogue().add(gail.getPostItemDialogue().get(0));
			}
			
			if(this.getCheckList().get(2)==1){
				_reverted=true;
				this.addTrainers(); //Do this again, else it doesn't change.
			}
			else{
				_reverted=false;
			}
		}
	}

	
	public void scanForAllEvents(){
		if(jose.isDefeated()){
			this.getCheckList().set(0,1);
		}
		else{
			this.getCheckList().set(0,0);
		}
		
		if(gail.isDefeated()){
			this.getCheckList().set(1,1);
		}
		else{
			this.getCheckList().set(1,0);
		}
		if(_reverted){
			this.getCheckList().set(2,1);
		}
		else{
			this.getCheckList().set(2,0);
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Ratty";
		this.xConstant=5;
		this.yConstant=0;
		this._mapX=172;
		this._mapY=319;
		
		this._roomNum = 8;
		this.addTrainers();
	
		this.loadAllEvents();
		
		this.song = M2.GYM;
		
		this.setBattleBG(NewBattleScreen.LIGHT_CAVE);
		_textVisible = new boolean[1];
		try{
			if(GameBoyScreen.finishedLoading){
				if((!_gbs.getPlayer().isGymLeaderDefeated(1) || !_reverted))
					_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Ratty/ratty1.png"));
				else
					_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Ratty/ratty3.png"));
			}
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(22,25);
		Scanner scan;
		if(!_gbs.getPlayer().isGymLeaderDefeated(1) || !_reverted)
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Ratty1.cmap"));
		else
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Ratty2.cmap"));
				
		for(int i = 0; i < 25; i++){
			for(int i2 = 0; i2 < 22; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}

public class PostGymBlackoutTimer implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			
			if(_timeCount == 1){
				_busy=true;
				_reverted=true;
				_this.scanForAllEvents();
			}
			else if(_timeCount > 1 && _timeCount<240){
				_darkLevel++;
			}			
			else if(_timeCount == 240){
				_this.createBaseRoom();
			}
			else if(_timeCount > 240 && _timeCount <480){
				_darkLevel--;
			}
			
			else if(_timeCount == 480){
				_busy=false;
				_timeCount=0;
				_postGymBlackout.stop();
				_this.completionCheck=false;
			}
			_this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, xConstant, yConstant, _movingTrainers);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		
		this.drawAllGenerics(g2);
		if(_postGymBlackout.isRunning()){
			this.drawText(g2, "The Ratty returned to its usual", "crowded environment...");
		}
	}

	public void enterRoom(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		
	//	PokePanel2 current;
		
		//Ratty Entrance Right
		if((xInd == 18 || xInd == 19) && yInd == 24){
//			if(_gbs.getPlayer().isGymLeaderDefeated(1)){
//				_busy = true;
//				JFrame gameover = new JFrame("THANK YOU FOR PLAYING!");
//				JLabel nl = new JLabel("We hoped you enjoyed it. Please send any feedback,");
//				JLabel n2 = new JLabel("questions, or comments to PokemonBrownUniversity@gmail.com");
//				gameover.setLayout(new GridLayout(2,1));
//				gameover.add(nl);
//				gameover.add(n2);
//				gameover.pack();
//				gameover.setVisible(true);
//			}
			super.enterRoom(_gbs.RATTY_ENTRANCE, 9, 0, FACESOUTH);
		
		}
		//Ratty Entrance Left
		if((xInd == 2 || xInd == 3) && yInd == 24){
//			if(_gbs.getPlayer().isGymLeaderDefeated(1)){
//				_busy = true;
//				JFrame gameover = new JFrame("THANK YOU FOR PLAYING!");
//				JLabel nl = new JLabel("We hoped you enjoyed it. Please send any feedback,");
//				JLabel n2 = new JLabel("questions, or comments to PokemonBrownUniversity@gmail.com");
//				gameover.setLayout(new GridLayout(2,1));
//				gameover.add(nl);
//				gameover.add(n2);
//				gameover.pack();
//				gameover.setVisible(true);
//			}

			super.enterRoom(_gbs.RATTY_ENTRANCE, 0, 0, FACESOUTH);
		}
	
	}
	
	public void afterBattle(){
		super.afterBattle();
		//Gail is first trainer in the vector
		if (_interruptedTrainer==0){
			_gbs.getPlayer().defeatGymLeader(1);
			this.getMovingTrainers().get(JOSE).defeat();
		}
	}
	
	public void Down(){
		if((this._xIndex==2 || this._xIndex==3) && this._yIndex==23 && this._NPCpage==0 && !_gbs.getPlayer().isGymLeaderDefeated(1)){
			this.A_Button();
		}
		else if(_yIndex==5 && _gbs.getPlayer().isGymLeaderDefeated(1) && !_reverted && !this._menuVisible ){
			this._postGymBlackout.start();
		}
		else{
			super.Down();
		}
	}
	
	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning()){

			if(!_busy){
				this.completionCheck = false;
			}
			
			if(this._interruptedTrainer==GAIL && this.getMovingTrainers().get(GAIL).isDefeated() && this.getMovingTrainers().get(GAIL).getGift()!=null && (_NPCpage==1)){
				M2.playFX(M2.BADGE);
			}
			
			this.repaint();
		}
		
		super.A_Button();
		
	}
	
	public void Select(){
		//No Bike indoors.
	}
}