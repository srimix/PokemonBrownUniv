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
public class FishCo extends PokePanel2 {
	private BufferedImage over, filter;
	private final int FLASH=25, VOMIT=18;
	private ImageIcon vom;
	private Trainer vomiter;
	
	public FishCo(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(26);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public FishCo(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(26);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
		
			try{
			
			Vector<Pokemon> oneBelt = new Vector<Pokemon>();
			oneBelt.add(new Pokemon.Growlithe().setWildLevel(18));
			oneBelt.add(new Pokemon.Growlithe().setWildLevel(18));
			Trainer one = new Trainer.ChubbyGuy(oneBelt);
			one.createHome(12,9);
			one.setDirectionAndImage(FACEEAST);
			one.setLOS(2);
			one.setMoney(1260);
			one.getDialogue().add("It's way too crowded in here. Outta my way!");
			one.setDefeatDialogue("Sigh. Guess I'll just stand here and people watch.");
			one.setName("Josh");
			one.setType("Townie");
			
			
			
			Vector<Pokemon> twoBelt = new Vector<Pokemon>();
			twoBelt.add(new Pokemon.Nidoran_M().setWildLevel(19));
			twoBelt.add(new Pokemon.Nidoran_F().setWildLevel(19));
			Trainer two = new Trainer.BlackHair(twoBelt);
			two.createHome(14, 9);
			two.setDirectionAndImage(FACESOUTH);
			two.setMoney(1330);
			two.getDialogue().add("I'd play you a game of pool, but I can't find any pool cues.");
			two.getDialogue().add("How about a quick battle instead? I need to train more.");
			two.setDefeatDialogue("Screw you I'll do it myself.");
			two.setName("Socrates");
			two.setType("Pool Master");
			
		
			Vector<Pokemon> threeBelt= new Vector<Pokemon>();
			threeBelt.add(new Pokemon.Pidgey().setWildLevel(18));
			threeBelt.add(new Pokemon.Nidoran_F().setWildLevel(18));
			Trainer three = new Trainer.RedBandanaGirl(threeBelt);
			three.createHome(14, 3);
			three.setLOS(3);
			three.setDirectionAndImage(FACENORTH);
			three.setMoney(270);
			three.getDialogue().add("Have you seen my jacket? DID YOU TAKE IT?");
			three.setDefeatDialogue("I just wanna find my jacket...");
			three.setName("Meredith");
			three.setType("Undergrad");
		
		
			Vector<Pokemon> fourBelt = new Vector<Pokemon>();
			fourBelt.add(new Pokemon.Nidoran_M().setWildLevel(21));
			Trainer four = new Trainer.GlassesGirl(fourBelt);
			four.createHome(13, 4);
			four.setDirectionAndImage(FACEEAST);
			four.setMoney(315);
			four.setVanishing(true);
			four.getDialogue().add("Man, I'm sick of this techno nonsense.");
			four.getDialogue().add("All this thumping is getting me irritated.");
			four.setDefeatDialogue("Ergh. That does it. I'm leaving.");
			four.setName("Sarah");
			four.setType("Townie");
			
			Vector<Pokemon> fiveBelt = new Vector<Pokemon>();
			fiveBelt.add(new Pokemon.Machop().setWildLevel(17));
			fiveBelt.add(new Pokemon.Tentacool().setWildLevel(17));
			Trainer five = new Trainer.DongJoo(fiveBelt);
			five.createHome(8, 2);
			five.setDirectionAndImage(FACEWEST);
			five.setMoney(510);
			five.getDialogue().add("This doesn't measure up to a KASA party, but it'll do for now.");
			five.getDialogue().add("How about we battle and pregame?");
			five.setDefeatDialogue("A good challenge. Come find me in Slater a little later.");
			five.setName("Dong Joo");
			five.setType("Party Time");
			
			//Rachel, add specific dialogue
			Vector<Pokemon> sixBelt = new Vector<Pokemon>();
			sixBelt.add(new Pokemon.Machop().setWildLevel(18));
			sixBelt.add(new Pokemon.Shellder().setWildLevel(18));
			Trainer six = new Trainer.BlackGirl(sixBelt);
			six.createHome(9,4);
			six.setDirectionAndImage(FACENORTH);
			six.setLOS(2);
			six.setMoney(540);
			six.getDialogue().add("One more shot of victory!");
			six.setDefeatDialogue("Congrats, next round is on me.");
			six.setName("Rachel");
			six.setType("Undergrad");
			

			Vector<Pokemon> sevenBelt = new Vector<Pokemon>();
			sevenBelt.add(new Pokemon.Horsea().setWildLevel(17));
			sevenBelt.add(new Pokemon.Shellder().setWildLevel(17));
			sevenBelt.add(new Pokemon.Tentacool().setWildLevel(17));
			Trainer seven = new Trainer.MafiaWhite(sevenBelt);
			seven.createHome(6, 5);
			seven.setDirectionAndImage(FACENORTH);
			seven.setMoney(510);
			seven.getDialogue().add("I'm enjoying the drinks at this bar.");
			seven.getDialogue().add("We should just buy this place. Good for business.");
			seven.setDefeatDialogue("Drats. Persistent little undergrads...");
			seven.setName("Thug");
			seven.setType("Mafia");
			
			
			Vector<Pokemon> eightBelt = new Vector<Pokemon>();
			eightBelt.add(new Pokemon.Horsea().setWildLevel(17));
			eightBelt.add(new Pokemon.Horsea().setWildLevel(17));
			eightBelt.add(new Pokemon.Horsea().setWildLevel(17));
			Trainer eight = new Trainer.MafiaMagenta(eightBelt);
			eight.createHome(6, 9);
			eight.setDirectionAndImage(FACEEAST);
			eight.setLOS(2);
			eight.setMoney(510);
			eight.getDialogue().add("The family is taking a night off from our grand plans.");
			eight.getDialogue().add("Which means I have enough time to dispatch of you.");
			eight.setDefeatDialogue("I better inform the others; there's a kid getting curious.");
			eight.setName("Elder");
			eight.setType("Mafia");
			
			Vector<Pokemon> nineBelt = new Vector<Pokemon>();
			nineBelt.add(new Pokemon.Shellder().setWildLevel(21));
			Trainer nine = new Trainer.BlueBro(nineBelt);
			nine.createHome(4, 3);
			nine.setDirectionAndImage(FACENORTH);
			nine.setLOS(2);
			nine.setMoney(595);
			nine.getDialogue().add("Gotta make our new frat pledges drink they blackout.");
			nine.getDialogue().add("It's tra-douche-tion.");
			nine.setDefeatDialogue("Looks like I blacked out instead...");
			nine.setName("Marcus");
			nine.setType("Frat Bro");
			
			
			Vector<Pokemon> tenBelt= new Vector<Pokemon>();
			tenBelt.add(new Pokemon.Tentacool().setWildLevel(18));
			tenBelt.add(new Pokemon.Staryu().setWildLevel(18));
			Trainer ten = new Trainer.Female1(tenBelt);
			ten.createHome(2, 3);
			ten.setDirectionAndImage(FACESOUTH);
			ten.setMoney(540);
			ten.getDialogue().add("I'm talking to DJ Pauly D, go away.");
			ten.getDialogue().add("I want him to announce my birthday.");
			ten.setDefeatDialogue("Losing on my birthday, now that's a sobering feeling.");
			ten.setName("Sammie");
			ten.setType("Birthday Girl");
			
			Vector<Pokemon> elevenBelt= new Vector<Pokemon>();
			elevenBelt.add(new Pokemon.Tentacool().setWildLevel(17));
			elevenBelt.add(new Pokemon.Staryu().setWildLevel(17));
			elevenBelt.add(new Pokemon.Shellder().setWildLevel(17));
			Trainer eleven = new Trainer.Ileana(elevenBelt);
			eleven.createHome(12, 2,0,0,"freeze","freeze");
			eleven.setDirectionAndImage(FACEEAST);
			eleven.setLOS(5);
			eleven.setMoney(595);
			eleven.getDialogue().add("You going around, taking items from the club?");
			eleven.getDialogue().add("I ought to report you to the bouncers.");
			eleven.setDefeatDialogue("I guess people should just leave their valuables at home.");
			eleven.setName("Eliza");
			eleven.setType("Undergrad");
			
			Vector<Pokemon> twelveBelt= new Vector<Pokemon>();
			twelveBelt.add(new Pokemon.Machop().setWildLevel(20));
			Trainer twelve = new Trainer.BrownMediumHair(twelveBelt);
			twelve.createHome(3, 5);
			twelve.setDirectionAndImage(FACENORTH);
			twelve.setMoney(600);
			twelve.getDialogue().add("I'm trying to stop weird guys from dancing with my friend.");
			twelve.getDialogue().add("As in, guys like you.");
			twelve.setDefeatDialogue("Urgh. I guess she can fend the guys off by herself.");
			twelve.setType("Undergrad");
			twelve.setName("Adriana");
			
			Vector<Pokemon> t13Belt= new Vector<Pokemon>();
			t13Belt.add(new Pokemon.Tentacool().setWildLevel(18));
			t13Belt.add(new Pokemon.Staryu().setWildLevel(18));
			Trainer t13 = new Trainer.MaroonHat(t13Belt);
			t13.createHome(5, 6);
			t13.setDirectionAndImage(FACEWEST);
			t13.setMoney(540);
			t13.setVanishing(false);
			t13.getDialogue().add("I'm having a strawberry daquiri.");
			t13.getDialogue().add("And you will not judge me for it.");
			t13.setDefeatDialogue("Hey. It shows girls I'm sensitive.");
			t13.setName("Kevin");
			t13.setType("Frat Bro");
			
			
			Vector<Pokemon> t14Belt= new Vector<Pokemon>();
			t14Belt.add(new Pokemon.Tentacool().setWildLevel(17));
			t14Belt.add(new Pokemon.Staryu().setWildLevel(17));
			t14Belt.add(new Pokemon.Shellder().setWildLevel(17));
			Trainer t14 = new Trainer.BlackHairBlueDress(t14Belt);
			t14.createHome(4, 6,0,0,"freeze","freeze");
			t14.addDest(4, 7);
			t14.addDest(4, 6);
			t14.setFirstDest();
			t14.setStationary(false);
			t14.setMoney(595);
			t14.setLOS(2);
			t14.getDialogue().add("Ohmygawd take a shot with meee!");
			t14.setDefeatDialogue("Ughhh, how ruuude!");
			t14.setType("\'Drunkest Girl At The Party\'");
			t14.setName("Alice");
			
			Vector<Pokemon> t15Belt= new Vector<Pokemon>();
			t15Belt.add(new Pokemon.Growlithe().setWildLevel(17));
			t15Belt.add(new Pokemon.Ponyta().setWildLevel(17));				
			Trainer t15 = new Trainer.BlueGirl(t15Belt);
			t15.createHome(3, 7);
			t15.setDirectionAndImage(FACESOUTH);
			t15.setMoney(1150);
			t15.getDialogue().add("I'll take it personally if you don't dance with me.");
			t15.setDefeatDialogue("Wow! So strong.");
			t15.setName("Molly");
			t15.setType("Freshman");
			
			Vector<Pokemon> t16Belt= new Vector<Pokemon>();
			t16Belt.add(new Pokemon.Rattata().setWildLevel(18));
			t16Belt.add(new Pokemon.Pikachu().setWildLevel(18));
			t16Belt.add(new Pokemon.Jigglypuff().setWildLevel(20));
			Trainer t16 = new Trainer.Cop(t16Belt);
			t16.createHome(2, 9);
			t16.setDirectionAndImage(FACESOUTH);
			t16.setLOS(2);
			t16.setMoney(315);
			t16.getDialogue().add("Stop! You look a little young. Show me your ID.");
			t16.setDefeatDialogue("Very well, be on your way.");
			t16.setName("Derek");
			t16.setType("Officer");
			
			Trainer d = new Trainer.Ponytail(null);
			d.createHome(3, 6);
			d.setDirectionAndImage(FACEWEST);
			d.getDialogue().add("The air in here is just far too...sweaty.");
			
			vomiter = new Trainer.VomitGuy(null);
			vomiter.createHome(5, 3,-3,0,"freeze","freeze");
			vomiter.setDirectionAndImage(FACEWEST);
			vomiter.addDest(6, 3);
			vomiter.setFirstDest();
			vomiter.setStationary(false);
			vomiter.setVanishing(false);
			vomiter.getDialogue().add("YEAHH, MOAR BEER!!");
			vomiter.getDialogue().add("(Proceeds to continue vomiting.)");
			
			Trainer c= new Trainer.PaulyD(_gbs.getPlayer(), this._roomNum, this);
			c.createHome(3, 2);
			
			Vector<Pokemon> rivalBelt = new Vector<Pokemon>();
			Pokemon r1 = new Pokemon.Pidgeotto().setWildLevel(19);
			Pokemon r2 = new Pokemon.Raticate().setWildLevel(16);
			Pokemon r3 = new Pokemon.Kadabra().setWildLevel(18);
			rivalBelt.add(r1);
			rivalBelt.add(r2);
			rivalBelt.add(r3);
			int rivStarter = 1;
			if(_gbs.getRival() != null && _gbs.getRival().getBelt() != null)
				rivStarter = _gbs.getRival().getBelt().get(0).getDexNum();
			if(rivStarter == 1){
				Pokemon r4 = new Pokemon.Bulbasaur().setWildLevel(20);
				rivalBelt.add(r4);
			}
			else if(rivStarter == 4){
				Pokemon r4 = new Pokemon.Charmander().setWildLevel(20);
				rivalBelt.add(r4);
			}
			else{
				Pokemon r4 = new Pokemon.Squirtle().setWildLevel(20);
				rivalBelt.add(r4);
			}
			
			Trainer rival = new Trainer.Rival(rivalBelt);
			rival.createHome(1,6,0,1000,"","");
			rival.setDirectionAndImage(FACEWEST);
			rival.setLOS(3);
			rival.getDialogue().add(_gbs.getPlayer().getRivalName()+": Fancy seeing you here, "+_gbs.getPlayer().getPlayerName()+".");
			rival.getDialogue().add("I'm sure you're preoccupied in your failing attempts to get ladies.");
			rival.getDialogue().add("...but I too am little too busy to battle you right now.");
			rival.getDialogue().add("I'm hosting this pole dance competition on stage...");
			rival.getDialogue().add("...and I frankly can't be bothered. But let's be honest...");
			rival.getDialogue().add("...nothing gets their engines roaring like a battle champion.");
			rival.setDefeatDialogue("Losing on stage. Embarrassing...");
			rival.getPostBattleDialogue().add("Whatever. Some girl around here flashed me, and ever since...");
			rival.getPostBattleDialogue().add("...my Pokemon can too. I think I can call tonight a victory.");
			rival.setName(_gbs.getRival().getTrueName());
			rival.setMoney(1330);
			

			Trainer ether = new Trainer.ItemObject(new Item.Ether());
			ether.createHome(14, 7);
			Trainer tm08 = new Trainer.ItemObject(new Item.TM08_Body_Slam());
			tm08.createHome(11, 2);
			Trainer gball = new Trainer.ItemObject(new Item.GreatBall());
			gball.createHome(2, 6);
			Trainer maxPotion = new Trainer.ItemObject(new Item.MaxPotion());
			maxPotion.createHome(7, 5);
			Trainer maxEther = new Trainer.ItemObject(new Item.MaxEther());
			maxEther.createHome(5, 7);
			
			Trainer flash = new Trainer.PurpleHatGirl(null);
			flash.createHome(4, 9);
			flash.setDirectionAndImage(FACEWEST);
			flash.getDialogue().add("(Girl flashes "+_gbs.getPlayer().getPlayerName() +".)");
			flash.getDialogue().add("("+_gbs.getPlayer().getPlayerName()+ " has seen the light.)");
			flash.setGift(new Item.HM05_Flash());
			flash.getPostItemDialogue().add("Tee hee hee.");

			Trainer bar = new Trainer.MartMenu("FishCo Bar", this); 
			bar.createHome(8,7);
			
			this.getMovingTrainers().add(one); //0
			this.getMovingTrainers().add(two);//1
			this.getMovingTrainers().add(three); //2
			this.getMovingTrainers().add(four);//3
			this.getMovingTrainers().add(five);//4
			this.getMovingTrainers().add(six);//5
			this.getMovingTrainers().add(seven);//6
			this.getMovingTrainers().add(eight);//7
			this.getMovingTrainers().add(nine);//8
			this.getMovingTrainers().add(ten);//9
			this.getMovingTrainers().add(eleven);//10
			this.getMovingTrainers().add(twelve);//11
			this.getMovingTrainers().add(t13);//12
			this.getMovingTrainers().add(t14);//13
			this.getMovingTrainers().add(t15);//14
			this.getMovingTrainers().add(t16);//15
			this.getMovingTrainers().add(c);//16
			this.getMovingTrainers().add(d);//17
			this.getMovingTrainers().add(vomiter);//18
			this.getMovingTrainers().add(rival);//19
			this.getMovingTrainers().add(ether);//20
			this.getMovingTrainers().add(tm08);//21
			this.getMovingTrainers().add(gball);//22
			this.getMovingTrainers().add(maxPotion);//23
			this.getMovingTrainers().add(maxEther);//24
			this.getMovingTrainers().add(flash); //25  //The events end here, 26th.
			this.getMovingTrainers().add(bar); //26
			
			}
			catch(IOException ioe){
				
			}
	}
	public void loadAllEvents(){
		if(this.getCheckList().size()!=0){
			for(int i =0 ; i<26; i++){
				if(this.getCheckList().get(i)==1){
					this.getMovingTrainers().get(i).defeatAndPostBattle();
				}
				//FOR FLASH.
				if(i==FLASH && this.getCheckList().get(FLASH)==1){
					this.getMovingTrainers().get(FLASH).defeatAndPostItemize();
				}
			}	
		}
	}
	
	public void scanForAllEvents(){
		//leave event 0 alone, thats for bouncer.
		for(int i =0 ; i<26; i++){
			if(this.getMovingTrainers().get(i).isDefeated()){
				this.getCheckList().set(i, 1);
			}
		}
		if(this.getMovingTrainers().get(FLASH).getGift()==null){
			this.getCheckList().set(FLASH, 1);
		}
	}
	
	public void createBaseRoom(){
		this.setMartVisible(false);
		this._martMenuVisible=false;
		this._thisMartContains.clear();
		this._thisMartContains.add(73); // vodka -potion
		this._thisMartContains.add(74); // coffee- awake
		this._thisMartContains.add(75); // tonic-antidote
		this._thisMartContains.add(76); // jager-prz heal
		this._thisMartContains.add(77); // creme - burn heal
		this._thisMartContains.add(78); // moonshine - iceheal
		this._thisMartContains.add(79); // smirnoff

		this.song = M2.FISHCO;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.setBattleBG(NewBattleScreen.FISHCO);
		
		this.xConstant=2;
		this.yConstant=0;
		this._mapX=120;
		this._mapY=325;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "FishCo";
		this._roomNum = _gbs.FISHCO;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/FishCo/FishCo Background.png"));
				over = ImageIO.read(this.getClass().getResource("/PokemonFiles/FishCo/FishCo Over.png"));
				filter = ImageIO.read(this.getClass().getResource("/PokemonFiles/FishCo/FishCo FilterOver.png"));
				vom = new ImageIcon(this.getClass().getResource("/PokemonFiles/TrainerImages/VomitGuy/vomit.gif"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(16,11);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/FishCo.cmap"));
		for(int i = 0; i < 11; i++){
			for(int i2 = 0; i2 < 16; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		g2.drawImage(over, null, this._xSpace, this._ySpace);


		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		
		if(!vomiter.isInterrupted() && vomiter.getDirection()==FACEWEST && vomiter.getCurrentImage()==vomiter.getRightImage()){
			vom.paintIcon(this, g2,vomiter.getXSpace()+xConstant+this._xSpace,vomiter.getYSpace()+yConstant+this._ySpace);
		}
		g2.drawImage(filter, null, this._xSpace, this._ySpace);
		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Outside
 		if((xInd == 9) && (yInd == 10)){
 			OutsideFishCo.carX=0;
 			super.enterRoom(_gbs.OUTSIDE_FISHCO, 4, 6, FACESOUTH);
 			
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