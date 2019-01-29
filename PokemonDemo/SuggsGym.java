package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class SuggsGym extends PokePanel2 {
	private BufferedImage _roomO;
	private final int SUGGS=1, Q1=2, Q2=3, Q3=4, Q4=5;
	private boolean alreadyLost = false;
	
	public SuggsGym(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(8);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public SuggsGym(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(8);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer champ = new Trainer.ChampGuy(); 
				champ.createHome(9,11);
				champ.getDialogue().add("Yo, Champ in the making! Good to see you again. ");
				champ.getDialogue().add("Suggs is the Organic Chemistry II professor,");
				champ.getDialogue().add("and his class is known for its brutal grades.");
				champ.getDialogue().add("Light up your bunsen burner, it's time for some Chemistry!");
				champ.getDialogue().add("The students here like to give quizzes. Hope you studied!");
				champ.getDialogue().add("His specialty is Grass type Pokemon, so you'd do best...");
				champ.getDialogue().add("...with Bug, Fire, Poison, Flying, or Ice.");
				champ.getDialogue().add("Grass types are not very resilient, but will plague you...");
				champ.getDialogue().add("with Status effects and HP absorbs.");
				champ.getDialogue().add("You've got it! And don't worry if it's tough, ");
				champ.getDialogue().add("No one ever passes Orgo the first time around anyways.");
			
				//0
				//Champ guy talks about quiz, and final sentence is "don't worry, no one ever passes Orgo the first time."
				
				
				//Suggs dialogue:
				Trainer suggs; //1
				if(!alreadyLost){ //You are screwed
					Vector<Pokemon> omgwtfBelt = new Vector<Pokemon>();
					omgwtfBelt.add(new Pokemon.Venusaur().setWildLevel(99));
					omgwtfBelt.add(new Pokemon.Scyther().setWildLevel(99));
					omgwtfBelt.add(new Pokemon.Tangela().setWildLevel(99));
					omgwtfBelt.add(new Pokemon.Venusaur().setWildLevel(99));
					omgwtfBelt.add(new Pokemon.Venusaur().setWildLevel(99));
					suggs = new Trainer.Suggs(omgwtfBelt);
					suggs.createHome(10, 2, 0, -1, "freeze", "freeze");
					suggs.setStationary(true);
					suggs.setLOS(0);
					suggs.getDialogue().add("Prof. Suggs: Please be seated and put away all...");
					suggs.getDialogue().add("...electronic devices. Your final exam begins now!");
					suggs.setDefeatDialogue("Impossible.");
					suggs.getPostBattleDialogue().add("NO WAY. You hacked the save file.");
					suggs.getPostBattleDialogue().add("But here's the badge.");
					//Get badge
					suggs.getPostBattleDialogue().add(_gbs.getPlayer().getPlayerName() + " received the Spectra Badge!");
					suggs.getPostBattleDialogue().add("");
					suggs.getPostItemDialogue().add("That's Mega Drain, returns the damage you deal as HP absorbed.");
					suggs.getPostItemDialogue().add("Now that's a high yield reaction.");
					suggs.setMoney(20000);
					suggs.setVanishing(false);
				}
				else{
					Vector<Pokemon> suggsBelt = new Vector<Pokemon>();
					suggsBelt.add(new Pokemon.Victreebel().setWildLevel(29));
					suggsBelt.add(new Pokemon.Tangela().setWildLevel(24));
					suggsBelt.add(new Pokemon.Vileplume().setWildLevel(29));
					suggs = new Trainer.Suggs(suggsBelt);
					suggs.createHome(10, 2, 0, -1, "freeze", "freeze");
					suggs.setStationary(true);
					suggs.setLOS(0);
					suggs.getDialogue().add("Prof. Suggs: Back to retake the course, eh?");
					suggs.getDialogue().add("Since you came into office hours, I'll be more merciful.");
					suggs.getDialogue().add("Pre-med students have complained for years about Orgo.");
					suggs.getDialogue().add("\"Why take Orgo? What's the point?\"");
					suggs.getDialogue().add("It builds CHARACTER and STAMINA.");
					suggs.getDialogue().add("You'll come in with your ambitions of getting an A...");
					suggs.getDialogue().add("and my Grass type Pokemon will grind your grade into the ground.");
					suggs.getDialogue().add("Prepare to see your confidence dissolve before you.");
					suggs.setDefeatDialogue("Astounding. What a solid grasp of the subject.");
					suggs.getPostBattleDialogue().add("You've somehow beaten the grading curve with an A.");
					suggs.getPostBattleDialogue().add("If that wasn't enough, you have also earned this:");
					//Get badge
					suggs.getPostBattleDialogue().add(_gbs.getPlayer().getPlayerName() + " received the Spectra Badge!");
					suggs.getPostBattleDialogue().add("I see good things in your future. I think you could use this.");
					suggs.getPostItemDialogue().add("That's Mega Drain, returns the damage you deal as HP absorbed.");
					suggs.getPostItemDialogue().add("Now that's a high yield reaction.");
					suggs.setMoney(3180);
					suggs.setVanishing(false);
				}
				
				//Blackout puts you right at the door, then defeats all trainers in the room except suggs.
				//See if you can make it say "one year later..." 
				//Ready to try again?
			
				
				Vector<Pokemon> q1Belt= new Vector<Pokemon>();
				q1Belt.add(new Pokemon.Bulbasaur().setWildLevel(24));
				q1Belt.add(new Pokemon.Ivysaur().setWildLevel(24));
				Trainer q1 = new Trainer.OrgoQuestioner(this, "M-StrawHat", "1", q1Belt); //2
				q1.createHome(7, 9);
				q1.setDirectionAndImage(FACEEAST);
				q1.setLOS(3);
				q1.setType("Chem Student");
				q1.setName("Phil");
				q1.setMoney(1820);
				
				Vector<Pokemon> q2Belt= new Vector<Pokemon>();
				q2Belt.add(new Pokemon.Bellsprout().setWildLevel(21));
				q2Belt.add(new Pokemon.Bellsprout().setWildLevel(21));
				q2Belt.add(new Pokemon.Oddish().setWildLevel(21));
				q2Belt.add(new Pokemon.Oddish().setWildLevel(21));
				Trainer q2 = new Trainer.OrgoQuestioner(this, "M-RedOveralls", "1", q2Belt); //3
				q2.createHome(5, 5);
				q2.setLOS(3);
				q2.setDirectionAndImage(FACENORTH);
				q2.setType("Orgo TA");
				q2.setName("Jonah");
				q2.setMoney(760);
				
				
				Vector<Pokemon> q3Belt= new Vector<Pokemon>();
				q3Belt.add(new Pokemon.Exeggcute().setWildLevel(26));
				Trainer q3 = new Trainer.OrgoQuestioner(this, "F-BrownMediumHair", "1", q3Belt); //4
				q3.createHome(6, 5);
				q3.setLOS(3);
				q3.setDirectionAndImage(FACENORTH);		
				q3.setType("Senior");
				q3.setName("Rebecca");
				q3.setMoney(700);
				
				
				Vector<Pokemon> q4Belt= new Vector<Pokemon>();
				q1Belt.add(new Pokemon.Oddish().setWildLevel(23));
				q1Belt.add(new Pokemon.Gloom().setWildLevel(23));
				Trainer q4 = new Trainer.OrgoQuestioner(this, "M-Scientist", "1", q4Belt); //5
				q4.createHome(1, 2);
				q4.setDirectionAndImage(FACENORTH);
				q4.setLOS(3);
				q4.setType("Faculty");
				q4.setName("Prof. Zimmt");
				q4.setMoney(740);
				
				//This one is Kathleen Hess
				Vector<Pokemon> hBelt= new Vector<Pokemon>();
				hBelt.add(new Pokemon.Weepinbell().setWildLevel(24));
				hBelt.add(new Pokemon.Gloom().setWildLevel(24));
				hBelt.add(new Pokemon.Ivysaur().setWildLevel(24));
				Trainer kh = new Trainer.Hess(hBelt);
				kh.createHome(7,2);
				kh.setLOS(3);
				kh.getDialogue().add("You must attend every pre-lab! It is crucial.");
				kh.setDefeatDialogue("I guess you still got by...");
				kh.setType("Lab Faculty");
				kh.setName("Dr. Hess");
				kh.setMoney(1470);
				kh.setDirectionAndImage(FACENORTH);
				
				
				this.getMovingTrainers().add(champ);//0
				this.getMovingTrainers().add(suggs);//1
				this.getMovingTrainers().add(q1);//2
				this.getMovingTrainers().add(q2);//3
				this.getMovingTrainers().add(q3);//4
				this.getMovingTrainers().add(q4);//5
				this.getMovingTrainers().add(kh); //6
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void scanForAllEvents(){
		this.standardTrainerScan(this.getMovingTrainers().size());
		if(alreadyLost){
			this.getCheckList().set(7, 1);
		}
		else{
			this.getCheckList().set(7, 0);
		}
	}
	public void loadAllEvents(){
		this.standardTrainerLoad(this.getMovingTrainers().size());
	}
	
	public void createBaseRoom(){
		
		this.song = M2.GYM;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-18;
		this.yConstant=0;
		
		
		this._mapX = 214;
		this._mapY = 295;
		
		this.setBattleBG(NewBattleScreen.FOREST);
		////CRUCIAL: Uncomment if there is a pkmn center here.
		//this._pkmnCentX=;
		//this._pkmnCentY=;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "MacMillan Gym";
		this._roomNum = _gbs.SUGGS_GYM;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/SuggsGym/SuggsGym Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/SuggsGym/SuggsGym Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(12,14);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SuggsGym.cmap"));
		for(int i = 0; i < 14; i++){
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
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);


		this.drawAllGenerics(g2);
		
		//Draw the Box #!
				if(mailboxOpen){
					Rectangle2D.Double mailBG = new Rectangle2D.Double();
					mailBG.x = 0;
					mailBG.y = 265;
					mailBG.width = 140;
					mailBG.height = 50;
					Rectangle2D.Double outline = new Rectangle2D.Double();
					outline.x = mailBG.x+5;
					outline.y = mailBG.y+5;
					outline.width = mailBG.width-10;
					outline.height = mailBG.height-10;
					g2.setColor(Color.WHITE);
					g2.fill(mailBG);
					g2.setColor(Color.BLACK);
					g2.setStroke(new BasicStroke(2));
					g2.draw(outline);
					
					//Draw Box number
					g2.drawString("Answer: "+ this.mailNumber, 10, 294);
				}
	}
	
	public void enterRoom(int xInd, int yInd){
		//Geochem
 		if((xInd == 8 || xInd==7) && (yInd == 13)){
 			super.enterRoom(_gbs.MACMILLAN_LOBBY, 1,1, FACESOUTH);
 		}
		
	}
	
	public void afterBattle(){
		super.afterBattle();
		
		//Gym leader stuff
		
		if(_interruptedTrainer == SUGGS && alreadyLost){
			MainGreen.springWeekend = true;
			_gbs.getPlayer().defeatGymLeader(6);
			for(int i =2; i<7;i++){
				this.getMovingTrainers().get(i).defeat();
			}
		}
	}
	
	public void A_Button(){
		if(!textTimer.isRunning()){
			//Don't allow use until 4 numbers are hit.
			if(this.mailboxOpen && this.mailNumber.length()==1){
				mailboxOpen=false;
			}
			if(!this.mailboxOpen){
				super.A_Button();
			}
	
			if(this._NPCpage==0 && (this.getMovingTrainers().get(Q1).isInterrupted() || this.getMovingTrainers().get(Q2).isInterrupted()|| this.getMovingTrainers().get(Q3).isInterrupted()|| this.getMovingTrainers().get(Q4).isInterrupted()) && !this.mailboxOpen){
				this.mailNumber="";
			}
			if(this._interruptedTrainer==SUGGS && !this.getMovingTrainers().get(SUGGS).isDefeated()){
				_gbs.getPlayer().setPkmnCenter(_gbs.MACMILLAN_LOBBY);
				alreadyLost=true;
			}
			if(this._interruptedTrainer==SUGGS && this.getMovingTrainers().get(SUGGS).isDefeated() && this.getMovingTrainers().get(SUGGS).getGift()!=null && (_NPCpage==1)){
				M2.playFX(M2.BADGE);
			}
		}
		else{
			super.A_Button();
		}
	}
	
	
	public void Start(){
		//Don't allow use until 1 number is hit.
		if(!textTimer.isRunning()){
			if(this.mailboxOpen && this.mailNumber.length()==1){
				this.A_Button();
			}
			if(!this.mailboxOpen){
				super.Start();
			}
		}
	}
	
	public void Number_Button(int i){
		if(!textTimer.isRunning()){
			if(this.mailboxOpen && this.mailNumber.length()<1){
				this.mailNumber = this.mailNumber+ Integer.toString(i);
			}
			this.repaint();
		}
	}


}