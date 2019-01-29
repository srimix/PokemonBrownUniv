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
public class Jos extends PokePanel2 {
	private boolean inGym = false;
	public Jos(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(6);
		
		this.createBaseRoom();
	
	}

	public Jos(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(6);
		
		this.createBaseRoom();
		
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < 6; i++){
				if(this.getCheckList().get(i) ==1){
					_movingTrainers.get(i).defeatAndPostBattle();
				}
			}
		}
	}
	
	public void scanForAllEvents(){
		for(int i = 0; i < 6; i++){
			if(_movingTrainers.get(i).isDefeated()){
				this.getCheckList().set(i,1);
			}
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				
				Vector<Pokemon> jBelt = new Vector<Pokemon>();
				jBelt.add(new Pokemon.Magmar().setWildLevel(42));
				jBelt.add(new Pokemon.Flareon().setWildLevel(40));
				jBelt.add(new Pokemon.Rapidash().setWildLevel(42));
				jBelt.add(new Pokemon.Arcanine().setWildLevel(47));
				Trainer josiah = new Trainer.Josiah(jBelt);
				josiah.createHome(38, 0, 0, 0, "", "");
				josiah.getDialogue().add("Hello there. My name is Josiah S. Carberry.");
				josiah.getDialogue().add("I'm an Emeritus Professor of Psychoceramics.");
				josiah.getDialogue().add("In order to make highly crack-able pots, you need to use");
				josiah.getDialogue().add("an incredibly hot kiln, which can also be used to grill chicken.");
				josiah.getDialogue().add("All the best things in life are spicy.");
				josiah.getDialogue().add("Think you can handle the heat?");
				josiah.setDefeatDialogue("Bravo! A Spicy-with for you!");
				josiah.getPostBattleDialogue().add("An excellent battle, one of the best I've ever had.");
				josiah.getPostBattleDialogue().add("In return for your hot-blooded passion for Pokemon, take this...");
				josiah.getPostItemDialogue().add("That's a special TM I use to light my kiln.");
				josiah.getPostItemDialogue().add("Careful though, every now and again you may burn yourself!");
				
				_movingTrainers.add(josiah);
				
				
				Vector<Pokemon> tobyBelt = new Vector<Pokemon>();
				tobyBelt.add(new Pokemon.Vulpix().setWildLevel(36));
				tobyBelt.add(new Pokemon.Vulpix().setWildLevel(36));
				tobyBelt.add(new Pokemon.Ninetales().setWildLevel(36));
				Trainer freidah = new Trainer.RedOveralls(tobyBelt);
				freidah.createHome(25, 3, 0, 0, "freeze", "freeze");
				freidah.setLOS(3);
				freidah.setType("\"Pissed Off Kid at Jo's\"");
				freidah.setName("Andrew");
				freidah.getDialogue().add("THE LIDS TO THE CUPS DON'T FIT!!!");
				freidah.setDefeatDialogue("You lose like, an ounce of soda every time...Just saying.");
				freidah.setMoney(3240);
				freidah.setBattleImage(TImg.M_BOY_NERD);
				_movingTrainers.add(freidah);
				
				
				Vector<Pokemon> bitchBelt = new Vector<Pokemon>();
				bitchBelt.add(new Pokemon.Ponyta().setWildLevel(41));
				Trainer black = new Trainer.Text();
				black.setBelt(bitchBelt);
				black.createHome(29, 2, 0, -20, "freeze", "freeze");
				black.setLOS(3);
				black.setGender('F');
				black.setType("Jo's Worker");
				black.setName("Jacqueline");
				black.getDialogue().add("SAY PLEASE.");
				black.setDefeatDialogue("You're welcome...");
				black.setMoney(3690);
				black.setBattleImage(TImg.F_GARDENER_WITH_BUCKET);
				_movingTrainers.add(black);
				
				Vector<Pokemon> cookieBelt = new Vector<Pokemon>();
				cookieBelt.add(new Pokemon.Growlithe().setWildLevel(36));
				cookieBelt.add(new Pokemon.Vulpix().setWildLevel(36));
				cookieBelt.add(new Pokemon.Ninetales().setWildLevel(36));
				Trainer lineCookie = new Trainer.Ponytail(cookieBelt);
				lineCookie.createHome(32, 6, 0, 0, "freeze", "freeze");
				lineCookie.setLOS(3);
				lineCookie.setType("Thief");
				lineCookie.setName("Brandy");
				lineCookie.getDialogue().add("Excuse me, I need to grab a line-cookie.");
				lineCookie.setDefeatDialogue("Ok, FINE. I'll pay for it...");
				lineCookie.setMoney(1025);
				_movingTrainers.add(lineCookie);
				
				Vector<Pokemon> quesoBelt = new Vector<Pokemon>();
				quesoBelt.add(new Pokemon.Ponyta().setWildLevel(34));
				quesoBelt.add(new Pokemon.Charmander().setWildLevel(34));
				quesoBelt.add(new Pokemon.Vulpix().setWildLevel(34));
				quesoBelt.add(new Pokemon.Growlithe().setWildLevel(34));
				Trainer queso = new Trainer.NiceHair(quesoBelt);
				queso.setMoney(900);
				queso.createHome(35, 8, 0, 0, "freeze", "freeze");
				queso.setDirectionAndImage(FACESOUTH);
				queso.setLOS(4);
				queso.setType("Frat Bro");
				queso.setName("Mickey");
				queso.getDialogue().add("Oh man, the quesadilla line is taking forever.");
				queso.getDialogue().add("I guess a battle will help pass the time.");
				queso.setDefeatDialogue("Oh no! I lost my place in line!");
				_movingTrainers.add(queso);
				
				
				Vector<Pokemon> saladBelt = new Vector<Pokemon>();
				saladBelt.add(new Pokemon.Rapidash().setWildLevel(41));
				Trainer salad = new Trainer.Text();
				salad.setBelt(saladBelt);
				salad.setMoney(1250);
				salad.setGender('M');
				salad.createHome(37, 4, -20, 0, "freeze", "freeze");
				salad.getDialogue().add("Did you put a piece of chicken in your salad?");
				salad.setLOS(3);
				salad.setType("BUDS Worker");
				salad.setName("Ian");
				salad.setDirectionAndImage(FACEEAST);
				salad.setDefeatDialogue("You know that'll cost extra...");
				_movingTrainers.add(salad);
				
				Trainer line1 = new Trainer.GlassesGirl(null);
				line1.createHome(36, 8);
				line1.setDirectionAndImage(FACEWEST);
				line1.getDialogue().add("She just called quesadilla #1.");
				line1.getDialogue().add("I'm quesadilla #18...");
				line1.getDialogue().add(":(");
				_movingTrainers.add(line1);
				
				Trainer line2 = new Trainer.StrawHat(null);
				line2.createHome(34, 8,0,0,"freeze","freeze");
				line2.addDest(37, 8);
				line2.setFirstDest();
				line2.setDirectionAndImage(FACEWEST);
				line2.setStationary(false);
				line2.getDialogue().add("I can't tell if this guy is in line or not.");
				line2.getDialogue().add("If he moves, I'm walking forward...");
				_movingTrainers.add(line2);
				
				Trainer quesaLady = new Trainer.GraySkirt(null);
				quesaLady.createHome(38, 8);
				quesaLady.setDirectionAndImage(FACEEAST);
				_movingTrainers.add(quesaLady);
				
				Trainer mart = new Trainer.MartMenu("Little Jo's", this);
				mart.createHome(0, 8);
				
				Trainer martGuy = new Trainer.BlackHairBlueDress(null);
				martGuy.createHome(0,8);
				martGuy.setDirectionAndImage(FACESOUTH);
				_movingTrainers.add(mart);
				_movingTrainers.add(martGuy);
				this._thisMartContains.clear();
				this._thisMartContains.add(Item.ULTRA_BALL);
				this._thisMartContains.add(Item.HYPER_POTION);
				this._thisMartContains.add(Item.FULL_HEAL);
				this._thisMartContains.add(Item.MAX_REPEL);
				this._thisMartContains.add(Item.GREAT_BALL);
				this._thisMartContains.add(Item.REVIVE);
				this._thisMartContains.add(Item.ESCAPE_ROPE);
				
				Trainer lonely = new Trainer.DirtyBlondeGirl(null);
				lonely.createHome(11, 9, 0, 0, "freeze", "freeze");
				lonely.addDest(12,9);
				lonely.setFirstDest();
				lonely.setStationary(false);
				lonely.getDialogue().add("I'm waiting for my friends to get here.");
				lonely.getDialogue().add("But my quesadilla is getting cold...");
				lonely.getDialogue().add("I hope they didn't want to meet at the Gate instead.");
				lonely.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(lonely);
				
				Trainer tv1 = new Trainer.Backpacker(null);
				tv1.createHome(9, 2, 0, 0, "freeze", "freeze");
				tv1.addDest(8, 2);
				tv1.setFirstDest();
				tv1.setStationary(false);
				tv1.setDirectionAndImage(FACEEAST);
				tv1.getDialogue().add("Not now, I'm watching the game.");
				_movingTrainers.add(tv1);
				
				Trainer tv2 = new Trainer.Backpacker(null);
				tv2.createHome(9, 1);
				tv2.setDirectionAndImage(FACEEAST);
				_movingTrainers.add(tv1);
				
				Trainer ketchup = new Trainer.RedAndYellow(null);
				ketchup.createHome(20, 7, 0, 0, "freeze", "freeze");
				ketchup.addDest(19, 7);
				ketchup.setFirstDest();
				ketchup.setStationary(false);
				ketchup.getDialogue().add("MAYBE. IF. I. JUST. PUMP. A. LITTLE. HARDER...");
				ketchup.getDialogue().add("It's no use. They're all out of ketchup :(");
				_movingTrainers.add(ketchup);
				
				Trainer theater = new Trainer.Ileana(null);
				theater.createHome(17, 2, 0, 0, "freeze", "freeze");
				theater.addDest(16, 2);
				theater.setFirstDest();
				theater.setStationary(false);
				theater.setDirectionAndImage(FACEEAST);
				theater.getDialogue().add("Hey man, do you know what time it is?");
				theater.getDialogue().add("Chill-even o' clock at night!");
				_movingTrainers.add(theater);
				
				Trainer theater2 = new Trainer.BlackDude(null);
				theater2.createHome(14, 1,5,0,"","");
				theater2.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(theater2);
				
				Trainer champ = new Trainer.ChampGuy();
				champ.createHome(22, 5, 0, 0, "", "");
				champ.setDirectionAndImage(FACENORTH);
				champ.getDialogue().add("Yo, how's it going champ?");
				champ.getDialogue().add("Believe it or not, you've actually stumbled into the 7th gym.");
				champ.getDialogue().add("Nobody knows who the gym leader is though.");
				champ.getDialogue().add("Some former Professor apparently.");
				champ.getDialogue().add("Either way, this gym is famous for it's incredible heat.");
				champ.getDialogue().add("Spicy chicken sandwiches, Sriracha mayo, and a whole lot of Fire-types.");
				champ.getDialogue().add("Hope you've got some water Pokemon to dowse the flames. Good luck!");
				_movingTrainers.add(champ);

				
				Trainer cashier1 = new Trainer.ShadyGlasses(null);
				cashier1.createHome(25, 6);
				cashier1.setDirectionAndImage(FACEWEST);
				cashier1.getDialogue().add("Credit and points?");
				_movingTrainers.add(cashier1);
				
				Trainer cashier2 = new Trainer.Sasuke(null);
				cashier2.createHome(26,7);
				cashier2.setDirectionAndImage(FACEWEST);
				cashier2.getDialogue().add("Even ninjas need part-time jobs. Work for BUDS!");
				_movingTrainers.add(cashier2);
				
				Trainer becca = new Trainer.BlackHair(null);
				becca.createHome(28, 6);
				becca.getDialogue().add("There are no more choco-tacos left.");
				becca.getDialogue().add(".............................");
				becca.getDialogue().add("KAAAAAAAAAAAHHHHNNN!!!");
				_movingTrainers.add(becca);
				
				Trainer guard = new Trainer.Cop(null);
				guard.createHome(23, 8,0,0,"freeze","freeze");
				guard.setDirectionAndImage(FACEWEST);
				guard.getDialogue().add("Don't interrupt me, I need to make sure students aren't taking food!");
				guard.addDest(24, 8);
				guard.setFirstDest();
				guard.setStationary(false);
				_movingTrainers.add(guard);
				
				Trainer meh = new Trainer.Text();
				meh.createHome(24, 8);
				meh.getDialogue().add("A bunch of confiscated BBQ and Asian sauces...");
				_movingTrainers.add(meh);
				
				Trainer indecide = new Trainer.ChubbyGuy(null);
				indecide.createHome(32, 4, 0, 0, "horiz", "horiz");
				indecide.addDest(31,4);
				indecide.addDest(32,4);
				indecide.setDirectionAndImage(FACEEAST);
				indecide.setFirstDest();
				indecide.setStationary(false);
				indecide.getDialogue().add("Odwalla or Naked Juice? I can never make up my mind...");
				indecide.getDialogue().add("Maybe I'll just go with a Nantucket Nectar again.");
				_movingTrainers.add(indecide);
				
				Trainer realSalad = new Trainer.GreenHat(null);
				realSalad.createHome(38, 4, 0, 0, "", "");
				realSalad.setDirectionAndImage(FACEEAST);
				_movingTrainers.add(realSalad);
				
				Trainer saladDecoy = new Trainer.MaroonHat(null);
				saladDecoy.createHome(35, 4);
				saladDecoy.setDirectionAndImage(FACEWEST);
				saladDecoy.getDialogue().add("You go ahead, I don't know what kind of lettuce I want yet.");
				_movingTrainers.add(saladDecoy);
				
				Trainer mozz = new Trainer.RedAndYellow(null);
				mozz.createHome(33, 3);
				mozz.setDirectionAndImage(FACESOUTH);
				mozz.getDialogue().add("Mozz sticks with marinara sauce are the best!");
				if(_gbs.getPlayer().isGymLeaderDefeated(7)){
					mozz.getDialogue().add("Good thing they just got more sauce!");
				}
				else{
					mozz.getDialogue().add("Unfortunately, they're out of marinara sauce AGAIN.");
				}
				_movingTrainers.add(mozz);
				
				Trainer quesoStall = new Trainer.GlassesGirl(null);
				quesoStall.createHome(34, 7);
				quesoStall.getDialogue().add("Are quesadillas even good? I thought today was sandwich day.");
				_movingTrainers.add(quesoStall);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void afterBattle(){
		super.afterBattle();
		if(_interruptedTrainer == 0){
			_gbs.getPlayer().defeatGymLeader(7);
		}
		//M2.playBG(M2.GYM);
	}

	public void createBaseRoom(){
		this.setMartVisible(false);
		this._martMenuVisible=false;
		this.song = M2.JOS;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=2;
		this.yConstant=-2;
		
		this.setBattleBG(NewBattleScreen.FISHCO);
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=134;
		this._mapY=283;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		//this._pkmnCentX=;
		//this._pkmnCentY=;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Jo's";  //IT'S NOT JOE'S. it's jo's, short for josiah's.//
		this._roomNum = _gbs.JOES;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Joes/joes.png"));
				//_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Hillel/Hillel Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(40,10);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Joes.cmap"));
		for(int i = 0; i < 10; i++){
			for(int i2 = 0; i2 < 40; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void Right(){
		super.Right();
	
		if(!_menuVisible){
			if(this._xIndex== 22 && !inGym){
				inGym = true;
				this.song=M2.GYM;
				M2.playBG(M2.GYM);
			}
		}
	}
	
	public void Left(){
		super.Left();
		
		if(!_menuVisible){
			if(this._xIndex == 23 && inGym){
				inGym = false;
				this.song = M2.OUTSIDE_FISHCO;
				M2.playBG(M2.OUTSIDE_FISHCO);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawPlayer(g2);
		//g2.drawImage(_roomO, null, this._xSpace, this._ySpace);


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){

		if(xInd == 0 && yInd < 6){
			super.enterRoom(_gbs.VG_QUAD, 15, yInd + 4, FACEWEST);
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