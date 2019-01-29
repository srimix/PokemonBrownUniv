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
public class BookstoreBasement extends PokePanel2 {
	private BufferedImage _roomO;
	private final int MAFIA_DON = 0;
	public static boolean corrupt = true;
	
	public BookstoreBasement(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(21); //21
		
		this.createBaseRoom();
		
	}

	public BookstoreBasement(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(21); //21
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {

//				//Add Mafia x13
				//Add items x8
				//Add signs x13
				Vector<Pokemon> donBelt = new Vector<Pokemon>();
				donBelt.add(new Pokemon.Onix().setWildLevel(25));
				donBelt.add(new Pokemon.Rhyhorn().setWildLevel(24));
				donBelt.add(new Pokemon.Kangaskhan().setWildLevel(29));
				Trainer don = new Trainer.MafiaDon(donBelt);
				don.createHome(2, 1);
				don.setMoney(2871);
				don.getDialogue().add("So you're the annoying pest interfering in my plans.");
				don.getDialogue().add("This little operation with textbooks is quite the cash cow.");
				don.getDialogue().add("I won't let the likes of you stop us!");
				don.getDialogue().add("Regardless of our battle, though, you should know one thing.");
				don.getDialogue().add("Not only is it difficult to catch Don...");
				don.setDefeatDialogue("...it's impossible.");
				don.setType("Mafia");
				don.setName("Don");
				don.setVanishing(true);
				_movingTrainers.add(don); //0 = DON
				
				Trainer text0 = new Trainer.Text();
				text0.createHome(3, 2);
				text0.getDialogue().add("Economics Textbooks.");
				text0.getDialogue().add("If Supply = Demand, then demand to be supplied with cheaper books!");
				
				Vector<Pokemon> thug1Belt = new Vector<Pokemon>();
				thug1Belt.add(new Pokemon.Ekans().setWildLevel(23));
				thug1Belt.add(new Pokemon.Sandshrew().setWildLevel(23));
				thug1Belt.add(new Pokemon.Arbok().setWildLevel(23));
				Trainer thug1 = new Trainer.MafiaMagenta(thug1Belt);
				thug1.createHome(7, 1);
				thug1.setMoney(672);
				thug1.setLOS(4);
				thug1.setVanishing(true);
				thug1.setType("Mafia");
				thug1.setName("Thug");
				thug1.getDialogue().add("You've made it this far, but could you handle our boss?");
				thug1.setDefeatDialogue("I'll let the boss take care of you!");
				_movingTrainers.add(thug1);
				
				Trainer text1 = new Trainer.Text();
				text1.createHome(8, 2);
				text1.getDialogue().add("Archaeology Textbooks.");
				text1.getDialogue().add("Most of them are old and a little dusty.");
				
				Vector<Pokemon> thug2Belt = new Vector<Pokemon>();
				thug2Belt.add(new Pokemon.Sandshrew().setWildLevel(23));
				thug2Belt.add(new Pokemon.Ekans().setWildLevel(23));
				thug2Belt.add(new Pokemon.Sandslash().setWildLevel(23));
				Trainer thug2 = new Trainer.MafiaWhite(thug2Belt);
				thug2.createHome(12,1);
				thug2.setMoney(706);
				thug2.setLOS(4);
				thug2.getDialogue().add("Don't interrupt! I'm studying volleyball.");
				thug2.setDefeatDialogue("Hmmph, better find somewhere else to study!");
				thug2.setVanishing(true);
				thug2.setType("Mafia");
				thug2.setName("Thug");
				_movingTrainers.add(thug2);
				
				Trainer text2 = new Trainer.Text();
				text2.createHome(13, 2);
				text2.getDialogue().add("Portuguese and Brazilian Studies Textbooks.");
				text2.getDialogue().add("Oooh, there's a book on the 2016 Pokemon-Olympics!");
				
				Vector<Pokemon> thug3Belt = new Vector<Pokemon>();
				thug3Belt.add(new Pokemon.Koffing().setWildLevel(21));
				thug3Belt.add(new Pokemon.Zubat().setWildLevel(21));
				Trainer thug3 = new Trainer.MafiaMagenta(thug3Belt);
				thug3.createHome(17, 1);
				thug3.setMoney(672);
				thug3.setLOS(4);
				thug3.setVanishing(true);
				thug3.setType("Mafia");
				thug3.setName("Thug");
				thug3.getDialogue().add("Time for a performance you'll never forget!");
				thug3.setDefeatDialogue("Alas, the show must go on...");
				_movingTrainers.add(thug3);
				
				Trainer text3 = new Trainer.Text();
				text3.createHome(18, 2);
				text3.getDialogue().add("Theatre Arts and Performance Studies Textbooks.");
				text3.getDialogue().add("Most of them will tell you that books can't teach you how to act.");
				
				//------------------------------
				
				Vector<Pokemon> thug4Belt = new Vector<Pokemon>();
				thug4Belt.add(new Pokemon.Rattata().setWildLevel(20));
				thug4Belt.add(new Pokemon.Raticate().setWildLevel(20));
				thug4Belt.add(new Pokemon.Drowzee().setWildLevel(20));
				Trainer thug4 = new Trainer.MafiaMagenta(thug4Belt);
				thug4.createHome(2,5);
				thug4.setMoney(706);
				thug4.setLOS(4);
				thug4.setVanishing(true);
				thug4.setType("Mafia");
				thug4.setName("Thug");
				thug4.getDialogue().add("Is what we're doing unethical?");
				thug4.setDefeatDialogue("Whatever, I'll let that Nick O'Machus guy figure it out.");
				_movingTrainers.add(thug4);
				
				Trainer text4 = new Trainer.Text();
				text4.createHome(3, 6);
				text4.getDialogue().add("Philosophy Textbooks.");
				text4.getDialogue().add("Does this textbook section even truly exist?");
				
				Vector<Pokemon> thug5Belt = new Vector<Pokemon>();
				thug5Belt.add(new Pokemon.Machop().setWildLevel(21));
				thug5Belt.add(new Pokemon.Machop().setWildLevel(21));
				Trainer thug5 = new Trainer.MafiaWhite(thug5Belt);
				thug5.createHome(7,5);
				thug5.setMoney(672);
				thug5.setLOS(4);
				thug5.setVanishing(true);
				thug5.setType("Mafia");
				thug5.setName("Thug");
				thug5.getDialogue().add("My efforts will be integral to our success!");
				thug5.setDefeatDialogue("I didn't think I would be derived so easily...");
				_movingTrainers.add(thug5);
				
				Trainer text5 = new Trainer.Text();
				text5.createHome(8, 6);
				text5.getDialogue().add("Math and Applied-Math Textbooks.");
				text5.getDialogue().add("There aren't any numbers here, just Greek symbols...Super abstract.");
				
				Vector<Pokemon> thug6Belt = new Vector<Pokemon>();
				thug6Belt.add(new Pokemon.Zubat().setWildLevel(17));
				thug6Belt.add(new Pokemon.Koffing().setWildLevel(17));
				thug6Belt.add(new Pokemon.Grimer().setWildLevel(17));
				thug6Belt.add(new Pokemon.Zubat().setWildLevel(17));
				thug6Belt.add(new Pokemon.Raticate().setWildLevel(17));
				Trainer thug6 = new Trainer.MafiaMagenta(thug6Belt);
				thug6.createHome(12,5);
				thug6.setMoney(706);
				thug6.setLOS(4);
				thug6.setVanishing(true);
				thug6.setType("Mafia");
				thug6.setName("Thug");
				thug6.getDialogue().add("I just read some cool study about child-development");
				thug6.getDialogue().add("According to K. et all, kids with Pokemon develop faster!");
				thug6.getDialogue().add("Let's see how developed you are!");
				thug6.setDefeatDialogue("Hmm. Must have had lots of Pokemon growing up...");
				_movingTrainers.add(thug6);
				
				Trainer text6 = new Trainer.Text();
				text6.createHome(13, 6);
				text6.getDialogue().add("Cognitive Science Textbooks.");
				text6.getDialogue().add("A lot of the book covers have optical illusions on them.");
				text6.getDialogue().add("...");
				text6.getDialogue().add("...");
				text6.getDialogue().add("...");
				text6.getDialogue().add("Now your brain hurts.");
				
				Vector<Pokemon> thug7Belt = new Vector<Pokemon>();
				thug7Belt.add(new Pokemon.Grimer().setWildLevel(22));
				thug7Belt.add(new Pokemon.Koffing().setWildLevel(22));
				Trainer thug7 = new Trainer.MafiaWhite(thug7Belt);
				thug7.createHome(17,5);
				thug7.setMoney(672);
				thug7.setLOS(4);
				thug7.setVanishing(true);
				thug7.setType("Mafia");
				thug7.setName("Thug");
				thug7.getDialogue().add("Itadakimasu!");
				thug7.setDefeatDialogue("Gah! We weren't eating, we were battling!");
				_movingTrainers.add(thug7);
				
				Trainer text7 = new Trainer.Text();
				text7.createHome(18, 6);
				text7.getDialogue().add("East Asian Studies Textbooks.");
				text7.getDialogue().add("The 'Beginner's Guide to Kanji' book is 3,000 pages!");
				
				//-----------------------------------------
				
				Vector<Pokemon> thug8Belt = new Vector<Pokemon>();
				thug8Belt.add(new Pokemon.Grimer().setWildLevel(20));
				thug8Belt.add(new Pokemon.Koffing().setWildLevel(20));
				thug8Belt.add(new Pokemon.Koffing().setWildLevel(20));
				Trainer thug8 = new Trainer.MafiaWhite(thug8Belt);
				thug8.createHome(2,9);
				thug8.setMoney(672);
				thug8.setLOS(4);
				thug8.setVanishing(true);
				thug8.setType("Mafia");
				thug8.setName("Thug");
				thug8.getDialogue().add("Voulez-vous Pokemon-combattre avec moi, ce soir?");
				thug8.setDefeatDialogue("Better study more. I'm going abroad next semester.");
				_movingTrainers.add(thug8);
				
				Trainer text8 = new Trainer.Text();
				text8.createHome(3, 10);
				text8.getDialogue().add("French Studies Textbooks.");
				text8.getDialogue().add("Some language books, along with political commentaries.");
				
				Vector<Pokemon> thug9Belt = new Vector<Pokemon>();
				thug9Belt.add(new Pokemon.Rattata().setWildLevel(19));
				thug9Belt.add(new Pokemon.Raticate().setWildLevel(19));
				thug9Belt.add(new Pokemon.Raticate().setWildLevel(19));
				thug9Belt.add(new Pokemon.Rattata().setWildLevel(19));
				Trainer thug9 = new Trainer.MafiaMagenta(thug9Belt);
				thug9.createHome(7,9);
				thug9.setMoney(706);
				thug9.setLOS(4);
				thug9.setVanishing(true);
				thug9.setType("Mafia");
				thug9.setName("Thug");
				thug9.getDialogue().add("I know EVERYTHING about Contemporary Architecture!");
				thug9.setDefeatDialogue("Argh, got the iClicker question wrong.");
				_movingTrainers.add(thug9);
				
				Trainer text9 = new Trainer.Text();
				text9.createHome(8, 10);
				text9.getDialogue().add("History of Art and Architecture Textbooks.");
				text9.getDialogue().add("One of the books has a geodesic Pokeball on the cover."); //Bucky Fuller
				
				Vector<Pokemon> thug10Belt = new Vector<Pokemon>();
				thug10Belt.add(new Pokemon.Raticate().setWildLevel(21));
				thug10Belt.add(new Pokemon.Raticate().setWildLevel(21));
				Trainer thug10 = new Trainer.MafiaWhite(thug10Belt);
				thug10.createHome(12,9);
				thug10.setMoney(672);
				thug10.setLOS(4);
				thug10.setVanishing(true);
				thug10.setType("Mafia");
				thug10.setName("Thug");
				thug10.getDialogue().add("Particle-in-a-box? Trivial.");
				thug10.setDefeatDialogue("I guess I can't solve the Pokemon-in-a-battle problem :(");
				_movingTrainers.add(thug10);
				
				Trainer text10 = new Trainer.Text();
				text10.createHome(13, 10);
				text10.getDialogue().add("Physics Textbooks.");
				text10.getDialogue().add("These books are expensive but reliable. Physics doesn't change.");
				
				Vector<Pokemon> thug11Belt = new Vector<Pokemon>();
				thug11Belt.add(new Pokemon.Drowzee().setWildLevel(21));
				thug11Belt.add(new Pokemon.Machop().setWildLevel(21));
				Trainer thug11 = new Trainer.MafiaMagenta(thug11Belt);
				thug11.createHome(17,9);
				thug11.setMoney(706);
				thug11.setLOS(4);
				thug11.setVanishing(true);
				thug11.setType("Mafia");
				thug11.setName("Thug");
				thug11.getDialogue().add("But soft, what battle through yonder basement breaks?");
				thug11.setDefeatDialogue("Not to be.");
				_movingTrainers.add(thug11);
				
				Trainer text11 = new Trainer.Text();
				text11.createHome(18, 10);
				text11.getDialogue().add("English Textbooks");
				text11.getDialogue().add("The 17th edition is $100 cheaper...");
				text11.getDialogue().add("...but all the answers will be posted according to the 18th edition.");
				text11.getDialogue().add("*sigh*");
				
				Vector<Pokemon> thug12Belt = new Vector<Pokemon>();
				thug12Belt.add(new Pokemon.Rattata().setWildLevel(18));
				thug12Belt.add(new Pokemon.Sandshrew().setWildLevel(18));
				Trainer thug12 = new Trainer.MafiaWhite(thug12Belt);
				thug12.createHome(22,9);
				thug12.setMoney(672);
				thug12.setLOS(4);
				thug12.setVanishing(true);
				thug12.setType("Mafia");
				thug12.setName("Thug");
				thug12.getDialogue().add("The mafia book has my mugshot in it!");
				thug12.setDefeatDialogue("I don't care, I'm famous!");
				_movingTrainers.add(thug12);
				
				Trainer text12 = new Trainer.Text();
				text12.createHome(23, 10);
				text12.getDialogue().add("Urban Studies Textbooks.");
				text12.getDialogue().add("Wonder if any of them talk about the local mafia?");
				
				//ITEMS======================
				Trainer item1 = new Trainer.ItemObject(new Item.Nugget());
				item1.createHome(13, 1);
				Trainer item2 = new Trainer.ItemObject(new Item.TM07_HornDrill());
				item2.createHome(8, 1);
				Trainer item3 = new Trainer.ItemObject(new Item.MoonStone());
				item3.createHome(18, 1);
				Trainer item4 = new Trainer.ItemObject(new Item.SuperPotion());
				item4.createHome(2, 5);
				Trainer item5 = new Trainer.ItemObject(new Item.TM10_DoubleEdge());
				item5.createHome(8, 5);
				Trainer item6 = new Trainer.ItemObject(new Item.RareCandy());
				item6.createHome(13, 9);
				Trainer item7 = new Trainer.ItemObject(new Item.HPUp());
				item7.createHome(18, 9);
				Trainer item8 = new Trainer.ItemObject(new Item.Iron());
				item8.createHome(23, 9);
				
				//8 items.
				this.getMovingTrainers().add(item1);
				this.getMovingTrainers().add(item2);
				this.getMovingTrainers().add(item3);
				this.getMovingTrainers().add(item4);
				this.getMovingTrainers().add(item5);
				this.getMovingTrainers().add(item6);
				this.getMovingTrainers().add(item7);
				this.getMovingTrainers().add(item8); //20, 21st item
				
				//===========================
				
				if(Bookstore.corrupt){
					Trainer cop2 = new Trainer.Cop(null);
					cop2.createHome(21, 7,0,0,"freeze","freeze");
					//cop2.setLOS(3);
					cop2.setDirectionAndImage(FACEWEST);
					cop2.getDialogue().add("Good, you're here.");
					cop2.getDialogue().add("I hope my partner upstairs told you everything.");
					cop2.getDialogue().add("Remember, you have to defeat all of them in one go.");
					cop2.getDialogue().add("Otherwise, they'll just recruit more flunkies.");
					cop2.getDialogue().add("There's a huge reward in it for you if you catch the Don.");
					cop2.getDialogue().add("I know I just met you, and this is crazy, but I feel strangely at...");
					cop2.getDialogue().add("...ease with letting a random kid take over my investigation.");
					cop2.getDialogue().add("Good luck.");
					_movingTrainers.add(cop2);
				}
				
				_movingTrainers.add(text0);
				_movingTrainers.add(text1);
				_movingTrainers.add(text2);
				_movingTrainers.add(text3);
				_movingTrainers.add(text4);
				_movingTrainers.add(text5);
				_movingTrainers.add(text6);
				_movingTrainers.add(text7);
				_movingTrainers.add(text8);
				_movingTrainers.add(text9);
				_movingTrainers.add(text10);
				_movingTrainers.add(text11);
				_movingTrainers.add(text12);
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.MAFIA_BASEMENT;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=2;
		this.yConstant=30;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=172;
		this._mapY=270;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		//this._pkmnCentX=;
		//this._pkmnCentY=;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;
		
		this.setBattleBG(NewBattleScreen.BASEMENT);

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Bookstore Basement";
		this._roomNum = _gbs.BOOKSTORE_BASEMENT;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Bookstore/bookstoreBasement.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(28,13);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BookstoreBasement.cmap"));
		for(int i = 0; i < 13; i++){
			for(int i2 = 0; i2 < 28; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		if(Bookstore.corrupt){
			SysOut.print("Corrupt!");
			this._room._roomGrid[22][3] = 'N';
			this._room._roomGrid[23][3] = 'N';
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
		if((xInd == 23 || xInd == 24) && yInd == 4){
			this.enterRoom(_gbs.BOOKSTORE,22,3,FACESOUTH);
		}
	}
	
	public void blackout(){
		super.blackout();
		if(this.getCheckList() != null){
			for(int i = 0; i < 13; i++){
				this.getCheckList().set(i,0);
			}
		}
		Bookstore.corrupt = true;
	}
	
	public void afterBattle(){
		super.afterBattle();
		if(this._interruptedTrainer == MAFIA_DON){
			Bookstore.corrupt = false;
			_gbs.getPanel(_gbs.BOOKSTORE).getCheckList().set(0, 1);
			createGrid();
		}
	}
	
	public void scanForAllEvents(){
		for(int i = 0; i < 21; i++){
			if(_movingTrainers.get(i).isDefeated())
				this.getCheckList().set(i,1);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < this.getCheckList().size(); i++){
				if(this.getCheckList().get(i)==1)
					_movingTrainers.get(i).defeatAndPostBattle();
			}
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