package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class MainGreen extends PokePanel2 {
	private BufferedImage _roomO;
	public static boolean springWeekend = false;
	private boolean springWeekendDefeated = false;
	private PokePanel2 _this;
	public int _timeCount=0;
	private Timer _postGymBlackout;
	
	private final int BEAR=14, GAMBINO=20;
	public MainGreen(GameBoyScreen gbs){
		super(gbs);
		_this = this;
		this.initializeEventVector(15);
		this.createBase();
		
		PostGymBlackoutTimer pgbo = new PostGymBlackoutTimer();
		_postGymBlackout = new Timer(10, pgbo);
		
		_xSpace = -(657-190);
		_ySpace = -(1065-190);
		_xIndex = 27;
		_yIndex = 46;
	}

	public MainGreen(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		_this = this;
		this.initializeEventVector(15);
		this.createBase();
		
		PostGymBlackoutTimer pgbo = new PostGymBlackoutTimer();
		_postGymBlackout = new Timer(10, pgbo);
	}

	
	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(BEAR).isDefeated()){
			this.getCheckList().set(0, 1);
		}
		if(springWeekend){
			this.getCheckList().set(1,1);
			for(int i = 17; i <= 28; i++){
				if(_movingTrainers.get(i).isDefeated()){
					this.getCheckList().set(i-14, 1);
				}
			}
		}
		else{
			this.getCheckList().set(1,0);
		}
		if(springWeekendDefeated){
			this.getCheckList().set(2,1);
		}

	}
	public void loadAllEvents(){
		if(this.getCheckList()!=null){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(BEAR).setVanishing(true);
				this.getMovingTrainers().get(BEAR).defeat();
				this.getMovingTrainers().get(BEAR+1).getDialogue().clear(); //Bear+1 is description on statue
				this.getMovingTrainers().get(BEAR+1).getDialogue().add("Inscribed on the statue: Dedicated to Bruno, the legendary Brown Bear.");
				this.getMovingTrainers().get(BEAR+1).getDialogue().add("What's this?");
				this.getMovingTrainers().get(BEAR+1).getDialogue().add("There's something etched on the statue underneath...");
				this.getMovingTrainers().get(BEAR+1).getDialogue().add("'You managed to catch my pet. You are now prepared to face me'.");
				this.getMovingTrainers().get(BEAR+1).getDialogue().add("'I will be waiting for you in the Tower...");
				this.getMovingTrainers().get(BEAR+1).getDialogue().add("It's signed \"The Founder\". Wonder what it means?");
			}
			if(this.getCheckList().get(1)==1){
				springWeekend = true;
				for(int i = 17; i <= 28; i++){
					if(this.getCheckList().get(i-14) == 1){
						if(_movingTrainers.size() > i)
							_movingTrainers.get(i).defeatAndPostBattle();
					}
				}
			}
			if(this.getCheckList().get(2)==1){
				springWeekendDefeated = true;
			}

		}
	}
	
public void addTrainers(){
	try {
		this._movingTrainers = new Vector<Trainer>();
		
		Trainer rope = new Trainer.BlueBro(null);
		rope.createHome(25, 38, -1, 0, "vert", "vert");
		rope.addDest(25, 42);
		rope.addDest(25, 38);
		rope.setFirstDest(); //0
		rope.setPause(false);
		//rope.setStationary(false);
		rope.getDialogue().clear();
		rope.getDialogue().add("Give slacklining a try! It's a great workout.");
		
		Trainer rope2 = new Trainer.RedstripeShirt(null);
		rope2.createHome(22, 38, -1, 0, "wander", "wander");
		rope2.addDest(22, 42);
		rope2.addDest(23, 38);
		rope2.addDest(22, 38);
		rope2.setFirstDest(); //1
		rope2.setPause(true);
		rope2.setStationary(false);
		rope2.getDialogue().clear();
		rope2.getDialogue().add("....I'm too scared to try the rope! What if I fall...");
		
		Trainer bearDesc = new Trainer.Text();
		bearDesc.createHome(33,4,0,0,"","");
		bearDesc.setStationary(true);
		bearDesc.getDialogue().clear();
		bearDesc.getDialogue().add("Inscribed on the statue: Dedicated to Bruno, the legendary Brown Bear.");
		if(_gbs.getPlayer().getPokedex() != null && !_gbs.getPlayer().getPokedex().hasSeen(217)){
			bearDesc.getDialogue().add("\"Long ago I roamed Main Green with the other legendary Pokemon.");
			//FOR CURRENT RELEASE
			bearDesc.getDialogue().add("Prove yourself worthy by defeating the Elites in Slater Hall...");
			bearDesc.getDialogue().add("...and I shall appear before you.\"");
		//	bearDesc.getDialogue().add("\"Gather my five friends, and I will appear before you.\"");
		//	bearDesc.getDialogue().add("\"The smartest one lives where his power is needed.\""); //zapdos
		//	bearDesc.getDialogue().add("\"The angriest lives in a place too hot for most people's taste.\""); //moltres
		//	bearDesc.getDialogue().add("\"The loneliest resides in the coldest realms of campus.\""); //articuno
		//	bearDesc.getDialogue().add("\"The strongest likes architecture as eclectic as itself.\""); //mewtwo
		//	bearDesc.getDialogue().add("\"And the rarest won't be found on this side of the Gates.\""); //mew
		}
		else{
			
			if(_gbs.getPlayer().getPokedex() != null && _gbs.getPlayer().getPokedex().hasCaught(217)){
					bearDesc.getDialogue().add("What's this?");
					bearDesc.getDialogue().add("There's something etched on the statue underneath...");
					bearDesc.getDialogue().add("'You managed to catch my pet. You are now prepared to face me.'");
					bearDesc.getDialogue().add("'I will be waiting for you in the Tower...");
					bearDesc.getDialogue().add("It's signed \"The Founder\". Wonder what it means?");
			}
		}		
		this._movingTrainers.add(rope); //0
		this._movingTrainers.add(rope2); //1
		 //2
		
		Trainer hipstairs = new Trainer.RedBandanaGirl(null);
		hipstairs.createHome(21, 4, 0, 0, "", "");
		hipstairs.setDirectionAndImage(FACENORTH);
		hipstairs.getDialogue().add("Whatever it's probably going to rain later...");
		_movingTrainers.add(hipstairs); //2
		
		Trainer hipstairs2 = new Trainer.RedAndYellow(null);
		hipstairs2.createHome(20, 4, 0, 0, "", "");
		hipstairs2.setDirectionAndImage(FACEWEST);
		hipstairs2.getDialogue().add("Got a light? I left my Charmander in my dorm.");
		_movingTrainers.add(hipstairs2); //3
		
		Trainer statue = new Trainer.BlackGirl(null);
		statue.createHome(17, 9, 0, 0, "", "");
		statue.setDirectionAndImage(FACENORTH);
		statue.getDialogue().add("I just love this statue. It so clearly portrays...");
		statue.getDialogue().add("...something. I don't really know what.");
		_movingTrainers.add(statue); //4
		
		Trainer football1 = new Trainer.BlondeDude(null);
		football1.createHome(22, 21, 0, 0, "", "");
		football1.setDirectionAndImage(FACEEAST);
		football1.getDialogue().add("Hut-hut, hut-hut, HIKE!");
		_movingTrainers.add(football1); //5
		
		Trainer football2 = new Trainer.GreenHat(null);
		football2.createHome(23, 20, 0, 0, "", "");
		football2.setDirectionAndImage(FACEEAST);
		football2.getDialogue().add("Touch football on the Main Green is the best!");
		_movingTrainers.add(football2);//6
		
		Trainer football3 = new Trainer.Chansey(this);
		football3.createHome(16, 21, 0, 0, "", "");
		football3.setDirectionAndImage(FACEWEST);
		football3.getDialogue().add("CHAN-seyyy...!");
		_movingTrainers.add(football3); //7
		
		Trainer football4 = new Trainer.BlueBro(null);
		football4.createHome(17, 20, 0, 0, "", "");
		football4.setDirectionAndImage(FACENORTH);
		football4.getDialogue().add("Chansey is actually really good at football.");
		football4.getDialogue().add("She just treats the football like an egg.");
		_movingTrainers.add(football4); //8
		
		Trainer walk1 = new Trainer.GreenHat(null);
		walk1.createHome(33, 31, 0, 0, "avoid", "avoid");
		walk1.addDest(28, 31);
		walk1.addDest(31, 13);
		walk1.addDest(6, 12);
		walk1.addDest(12, 14);
		walk1.addDest(10, 31);
		walk1.addDest(33, 31);
		walk1.setFirstDest();
		walk1.setPause(false);
		walk1.setStationary(false);
		walk1.getDialogue().add("Not now, I'm late to class!!!");
		_movingTrainers.add(walk1); //9
		
		Trainer walk2 = new Trainer.GraySkirt(null);
		walk2.createHome(6, 6, 0, 0, "avoid", "avoid");
		walk2.addDest(6,5);
		walk2.addDest(10, 5);
		walk2.addDest(8, 40);
		walk2.addDest(6, 6);
		walk2.setFirstDest();
		walk2.setPause(false);
		walk2.setStationary(false);
		walk2.getDialogue().add("I HOPE I'm not late...");
		_movingTrainers.add(walk2); //10
		
		Trainer walk3 = new Trainer.Psyduck(null);
		walk3.createHome(36, 3, 0, 0, "drunk", "drunk");
		walk3.addDest(5, 31);
		walk3.addDest(36, 3);
		walk3.setFirstDest();
		walk3.setPause(false);
		walk3.setStationary(false);
		walk3.getDialogue().add("'PsyYyYduuuck?'");
		walk3.getDialogue().add("Psyduck looks confused. Maybe it's a freshman?");
		_movingTrainers.add(walk3); //11
		
		Trainer walk4 = new Trainer.RedHairBike(null);
		walk4.createHome(12,2, 0, 0, "avoid", "avoid");
		walk4.addDest(27, 36);
		walk4.addDest(24, 17);
		walk4.addDest(20, 6);
		walk4.addDest(12, 2);
		walk4.setFirstDest();
		walk4.setPause(false);
		walk4.setStationary(false);
		walk4.getDialogue().add("Tour de Franzia!!!");
		_movingTrainers.add(walk4); //12
		
		Trainer bench = new Trainer.BrownGuy(null);
		bench.createHome(11, 27, 0, 0, "", "");
		bench.getDialogue().add("I really want to live in Slater hall next year...");
		bench.getDialogue().add("...but they only let students with 8 badges live there :(");
		_movingTrainers.add(bench); //13
		
		if(springWeekend){
			for(int i=0; i < 14; i++){
				_movingTrainers.get(i).setVanishing(true);
				_movingTrainers.get(i).defeat();
			}
		}
		
		Trainer bear = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Ursaring().setWildLevel(75), "Bruno: ROAAAARRR!!", "P-Ursaring", M2.URSARING);
		bear.createHome(33, 4,-5,3,"freeze","freeze");
		bear.setDirectionAndImage(FACENORTH);
		if(_gbs.getPlayer().getPokedex()!=null){
			//Pokedex dex=_gbs.getPlayer().getPokedex();
			if(_gbs.numberOfTimesEliteFourHasBeenDefeated > 0){//dex.hasSeen(144)&&dex.hasSeen(145)&&dex.hasSeen(146)&&dex.hasSeen(150)&&dex.hasSeen(151)
				//appears.
			}
			else{
				bear.setVanishing(true);
				bear.defeat();
			}
		}
		this.getMovingTrainers().add(bear); //14
		this._movingTrainers.add(bearDesc); //15
		
		
		//SPRING WEEKEND TRAINERS:
		if(springWeekend){
			Trainer henry = new Trainer.Photographer(null);
			henry.createHome(23, 16);
			henry.getDialogue().add("Hey man, can you help me out?");
			henry.getDialogue().add("I wanna get some cool pictures of the performers battling.");
			henry.getDialogue().add("Just borrow my press-pass to get up there, and look cool!");
			henry.setLOS(2);
			_movingTrainers.add(henry); //16
			
			Vector<Pokemon> glitchBelt = new Vector<Pokemon>();
			glitchBelt.add(new Pokemon.Dugtrio().setWildLevel(28));
			glitchBelt.add(new Pokemon.Dodrio().setWildLevel(28));
			glitchBelt.add(new Pokemon.Magneton().setWildLevel(28));
			glitchBelt.add(new Pokemon.MissingNo().setWildLevel(30));
			Trainer glitch = new Trainer.GlitchMob(glitchBelt);
			glitch.createHome(22, 16);
			glitch.setLOS(2);
			glitch.getDialogue().add("We ArE tEh GlItCh MoB!!!");
			glitch.getDialogue().add("We ArE uNsToPpAbLe PoKeMoN mAsTeRs!!!");
			glitch.setDefeatDialogue("Our battle strategy has a few bugs in it...");
			glitch.setMoney(3000);
			glitch.setType("Spring Weekend Band");
			glitch.setName("GlItCh MoB");
			
			_movingTrainers.add(glitch); //17
			
			Trainer glitch2 = new Trainer.Text();
			glitch2.createHome(21, 16);
			glitch2.getDialogue().add("Our battle strategy has a few bugs in it...");
			_movingTrainers.add(glitch2); //18
			
			Trainer glitch3 = new Trainer.Text();
			glitch3.createHome(20, 16);
			glitch3.getDialogue().add("Our battle strategy has a few bugs in it...");
			_movingTrainers.add(glitch3); //19
			
			Vector<Pokemon> gambinoBelt = new Vector<Pokemon>();
			gambinoBelt.add(new Pokemon.Raichu().setWildLevel(32));
			Trainer gambino = new Trainer.Gambino(gambinoBelt);
			gambino.createHome(18, 16);
			gambino.getDialogue().add("Hey there, my name is Donald Glover, a.k.a. Childish Gambino.");
			gambino.getDialogue().add("I strive to be #1 in all things; singing, writing, Pokemon.");
			gambino.getDialogue().add("I've done so much work to prepare for all of it.");
			gambino.getDialogue().add("I've worked hard to improve my Pokemon strategies.");
			gambino.getDialogue().add("Let's see if I've done enough work to defeat you!");
			gambino.setDefeatDialogue("I guess winning an Emmy just wasn't enough.");
			gambino.getPostBattleDialogue().add("BCA: THANK YOU EVERYONE FOR A FANTASTIC SPRING WEEKEND!");
			gambino.getPostBattleDialogue().add("BCA: SEE YOU ALL NEXT YEAR!");
			//gambino.setLOS(2);
			gambino.setMoney(2500);
			gambino.setType("Spring Weekend Performer");
			gambino.setName("Childish Gambino");
			_movingTrainers.add(gambino); //20
			
			
			/* CAN BATTLE THESE TRAINERS */
			//22,19 FACEWEST (GHM Event Staff)
			//1 	Jr. Trainer 	Money: 580
			//Pikachu 	LV29
			//Raichu 	LV29
			//Pidgey 	LV29
			//Pidgeotto 	LV29
			Vector<Pokemon> ghm1Belt = new Vector<Pokemon>();
			ghm1Belt.add(new Pokemon.Pikachu().setWildLevel(29));
			ghm1Belt.add(new Pokemon.Pidgey().setWildLevel(29));
			ghm1Belt.add(new Pokemon.Pidgeotto().setWildLevel(29));
			Trainer ghm1 = new Trainer.GreenHat(ghm1Belt);
			ghm1.createHome(22, 19);
			ghm1.setDirectionAndImage(FACEWEST);
			ghm1.setLOS(2);
			ghm1.setMoney(580);
			ghm1.setType("GHM Event Staff");
			ghm1.setName("Rick");
			ghm1.getDialogue().add("Hey, you aren't allowed past here under any circumstances!");
			ghm1.setDefeatDialogue("Very well, you may pass.");
			ghm1.getPostBattleDialogue().add("Because being good at Pokemon is totally...");
			ghm1.getPostBattleDialogue().add("...a legitimate reason to let you onto the stage.");
			_movingTrainers.add(ghm1); //21
			
			//20,20 FACENORTH
			/*		
			2 	Beauty 	Money: 2030
				Pidgeotto 	LV29
				Wigglytuff 	LV29 */
			Vector<Pokemon> frontBelt = new Vector<Pokemon>();
			frontBelt.add(new Pokemon.Pidgeotto().setWildLevel(29));
			frontBelt.add(new Pokemon.Wigglytuff().setWildLevel(29));
			Trainer frontGirl = new Trainer.RedAndYellow(frontBelt);
			frontGirl.createHome(20, 20);
			frontGirl.setLOS(2);
			frontGirl.setMoney(2030);
			frontGirl.setType("Crowd Surfer");
			frontGirl.setName("Belanie");
			frontGirl.getDialogue().add("Hey, don't try to shove past me!");
			frontGirl.setDefeatDialogue("Rude.");
			_movingTrainers.add(frontGirl); //22
			
			//17,18 FACENORTH
			/*3 	Biker 	Money: 500
			Koffing 	LV25
			Koffing 	LV25
			Weezing 	LV25
			Koffing 	LV25
			Grimer 	LV25 */
			Vector<Pokemon> sideBelt = new Vector<Pokemon>();
			sideBelt.add(new Pokemon.Koffing().setWildLevel(25));
			sideBelt.add(new Pokemon.Koffing().setWildLevel(25));
			sideBelt.add(new Pokemon.Weezing().setWildLevel(25));
			sideBelt.add(new Pokemon.Koffing().setWildLevel(25));
			sideBelt.add(new Pokemon.Grimer().setWildLevel(25));
			Trainer techie = new Trainer.ShadyGlasses(sideBelt);
			techie.setLOS(2);
			techie.setMoney(500);
			techie.createHome(17, 18);
			techie.setType("Special FX Technician");
			techie.setName("Leo");
			techie.getDialogue().add("It's my job to add all of the smoke-effects.");
			techie.setDefeatDialogue("*cough* *cough*");
			_movingTrainers.add(techie); //23
			
			//17,22 FACEEAST
			/*4 	Biker 	Money: 560
			Koffing 	LV28
			Grimer 	LV28
			Weezing 	LV28*/
			Vector<Pokemon> audioTech = new Vector<Pokemon>();
			audioTech.add(new Pokemon.Koffing().setWildLevel(28));
			audioTech.add(new Pokemon.Grimer().setWildLevel(28));
			audioTech.add(new Pokemon.Weezing().setWildLevel(28));
			Trainer audio = new Trainer.BrownGuy(audioTech);
			audio.createHome(17, 22);
			audio.setDirectionAndImage(FACEEAST);
			audio.setMoney(560);
			audio.setLOS(2);
			audio.setType("Mosh Pit");
			audio.setName("Mike");
			audio.getDialogue().add("This crowd's way too dense, can't get any good movement.");
			audio.setDefeatDialogue("Maybe I'm at the wrong concert?");
			audio.getPostBattleDialogue().add("You're too intense for me man, you can have this.");
			audio.getPostItemDialogue().add("WOOOO!!! YEAH DUBSTEP!");
			//audio.setVanishing(true);
			audio.setGift(new Item.TM20_Rage());
			_movingTrainers.add(audio); //24
			
			
			
			//19,25 FACENORTH
			/*	5 	Beauty 	Money: 2030
			Bulbasaur 	LV29
			Ivysaur 	LV29*/
			Vector<Pokemon> girlBelt = new Vector<Pokemon>();
			girlBelt.add(new Pokemon.Bulbasaur().setWildLevel(29));
			girlBelt.add(new Pokemon.Ivysaur().setWildLevel(29));
			Trainer girl = new Trainer.BlackHairBlueDress(girlBelt);
			girl.createHome(21, 24); //19,25
			girl.getDialogue().add("I'm not gonna Raichu a love song!!!");
			girl.setDefeatDialogue("WOOOOOOOOOOOOOOOOO!!!");
			girl.setMoney(2030);
			girl.setType("Woo Girl");
			girl.setName("Tiffany");
			girl.setLOS(2);
			_movingTrainers.add(girl); //25
			
			//22,23 FACEWEST
			/*6 	Jr. Trainer	Money: 560
			Gloom 	LV28
			Oddish 	LV28
			Oddish 	LV28 */
			Vector<Pokemon> shroomBelt = new Vector<Pokemon>();
			shroomBelt.add(new Pokemon.Oddish().setWildLevel(28));
			shroomBelt.add(new Pokemon.Oddish().setWildLevel(28));
			shroomBelt.add(new Pokemon.Gloom().setWildLevel(28));
			Trainer shroom = new Trainer.Ponytail(shroomBelt);
			shroom.createHome(22, 23);
			shroom.setDirectionAndImage(FACEWEST);
			shroom.setMoney(560);
			shroom.setLOS(2);
			shroom.setType("'High-as-a-kite'");
			shroom.setName("Kelly");
			shroom.getDialogue().add("Yeah 4/20!!!");
			shroom.setDefeatDialogue("Kelly has no remaining Pokemon. Kelly greened out. Tee-hee.");
			_movingTrainers.add(shroom); //26
			
			//24, 26 FACEEAST
			Vector<Pokemon> birdBelt = new Vector<Pokemon>();
			birdBelt.add(new Pokemon.Doduo().setWildLevel(28));
			birdBelt.add(new Pokemon.Doduo().setWildLevel(28));
			birdBelt.add(new Pokemon.Dodrio().setWildLevel(28));
			Trainer birdMan = new Trainer.StrawHat(birdBelt);
			birdMan.createHome(24, 26);
			birdMan.setDirectionAndImage(FACEEAST);
			birdMan.setLOS(2);
			birdMan.setMoney(725);
			birdMan.setType("'High-as-a-kite'");
			birdMan.setName("Dave");
			birdMan.getDialogue().add("I'm gonna get so high on my Bird-Pokemon!");
			birdMan.setDefeatDialogue("Doduos can't fly :(");
			_movingTrainers.add(birdMan); //27
			
			//17, 28 FACEWEST
			/*9 	Birdkeeper 	Money: 650
			Pidgeotto 	LV26
			Farfetch'd 	LV26
			Doduo 	LV26
			Pidgey 	LV26
			*/
			Vector<Pokemon> flightBelt = new Vector<Pokemon>();
			flightBelt.add(new Pokemon.Pidgey().setWildLevel(26));
			flightBelt.add(new Pokemon.Doduo().setWildLevel(26));
			flightBelt.add(new Pokemon.Farfetchd().setWildLevel(26));
			flightBelt.add(new Pokemon.Pidgeotto().setWildLevel(26));
			Trainer dude = new Trainer.Goku(flightBelt);
			dude.createHome(17, 28);
			dude.setDirectionAndImage(FACEWEST);
			dude.setLOS(2);
			dude.setMoney(650);
			dude.setType("Gambino Fanatic");
			dude.setName("Billy");
			dude.getDialogue().add("Everything I'm saying, I'm super-saiyan, like Goku!");
			dude.setDefeatDialogue("If only I battled more like Goku...");
			_movingTrainers.add(dude); //28
			
			
			/* RANDOM PEOPLE */ //Can't do Straw Hat, Pigtails, BlackHairBlueDress,
								//Brown guy, ShadyGlasses, RedAndYellow,GreenHat
			//16,28
			Trainer rand1 = new Trainer.Backpacker(null);
			rand1.createHome(16, 28);
			rand1.setDirectionAndImage(FACESOUTH);
			rand1.getDialogue().add("WOOOOOOOO!!!!");
			_movingTrainers.add(rand1); //29
			//17,27
			Trainer rand2 = new Trainer.BlackDude(null);
			rand2.createHome(17, 27);
			rand2.setDirectionAndImage(FACESOUTH);
			rand2.getDialogue().add("SPRING WEEKEND 2012 WASSUP!");
			_movingTrainers.add(rand2); //30
			//17,26
			Trainer rand3 = new Trainer.BlackGirl(null);
			rand3.createHome(17, 26);
			rand3.setDirectionAndImage(FACESOUTH);
			rand3.getDialogue().add("YEEEAAAHH GAMBINO!!!");
			_movingTrainers.add(rand3); //31
			//18,26
			Trainer rand4 = new Trainer.BlackHair(null);
			rand4.createHome(18, 26);
			rand4.setDirectionAndImage(FACESOUTH);
			rand4.getDialogue().add("I thoroughly enjoyed Sepalcure. I wish they would come back onstage.");
			rand4.getDialogue().add(".................................LOL. JK.");
			_movingTrainers.add(rand4); //32
			//19,26
			Trainer rand5 = new Trainer.BlondeDude(null);
			rand5.createHome(19, 26);
			rand5.setDirectionAndImage(FACESOUTH);
			rand5.getDialogue().add("4/20 + Spring Weekend? What the HELL were they thinking?!?");
			_movingTrainers.add(rand5); //33
			//21,26
			Trainer rand6 = new Trainer.BlueBro(null);
			rand6.createHome(21, 26);
			rand6.setDirectionAndImage(FACESOUTH);
			rand6.getDialogue().add("WOOOOOOOO!");
			_movingTrainers.add(rand6); //34
			//22,26
			Trainer rand7 = new Trainer.BlueGirl(null);
			rand7.createHome(22, 26);
			rand7.setDirectionAndImage(FACESOUTH);
			rand7.getDialogue().add("!!!!!!!!!!!!!");
			_movingTrainers.add(rand7); //35
			//24,27
			Trainer rand8 = new Trainer.BrownMediumHair(null);
			rand8.createHome(24, 27);
			rand8.setDirectionAndImage(FACESOUTH);
			rand8.getDialogue().add("Twin shadow? More like Twin pedo. What a creeper.");
			_movingTrainers.add(rand8); //36
			//24,25
			Trainer rand9 = new Trainer.ChubbyGuy(null);
			rand9.createHome(24, 25);
			rand9.getDialogue().add("Yeeaaah!! We buried a keg out here earlier.");
			_movingTrainers.add(rand9); //37
			//24,24
			Trainer rand10 = new Trainer.Cop(null);
			rand10.createHome(24, 24);
			rand10.getDialogue().add("NO CROWD SURFING! PUT HER DOWN RIGHT NOW!");
			rand10.setDirectionAndImage(FACEEAST);
			_movingTrainers.add(rand10); //38
			//24,22
			Trainer rand11 = new Trainer.DirtyBlondeGirl(null);
			rand11.createHome(24, 22);
			rand11.setDirectionAndImage(FACESOUTH);
			rand11.getDialogue().add("TWIN SHADOW! TWIN SHADOW! ENCORE!!!");
			_movingTrainers.add(rand11); //39
			//24,21
			Trainer rand12 = new Trainer.Ambika(null);
			rand12.createHome(24, 21);
			rand12.setDirectionAndImage(FACESOUTH);
			rand12.getDialogue().add("YEEEAAAAHHHH!!!!");
			_movingTrainers.add(rand12); //40
			//24,20
			Trainer rand13 = new Trainer.GlassesGirl(null);
			rand13.createHome(24, 20);
			rand13.getDialogue().add("He's so cute when he's over-protective.");
			_movingTrainers.add(rand13); //41
			//23,21
			Trainer rand14 = new Trainer.GlassesGuy1(null);
			rand14.createHome(23, 21);
			rand14.getDialogue().add("Hey! Don't shove my girlfriend!");
			rand14.setDirectionAndImage(FACESOUTH);
			_movingTrainers.add(rand14); //42
			//22,22
			Trainer rand15 = new Trainer.GraySkirt(null);
			rand15.createHome(22, 22);
			rand15.setDirectionAndImage(FACESOUTH);
			rand15.getDialogue().add("YEAH!");
			_movingTrainers.add(rand15); //43
			//21,20
			Trainer rand16 = new Trainer.GreenDress(null);
			rand16.createHome(21, 20);
			rand16.setDirectionAndImage(FACESOUTH);
			rand16.getDialogue().add("WOOOOOOOOOOOOOOOO!!!");
			_movingTrainers.add(rand16); //44
			//19,20
			Trainer rand17 = new Trainer.Lorelei(null);
			rand17.createHome(19, 20);
			rand17.setDirectionAndImage(FACESOUTH);
			rand17.getDialogue().add("WOOOOOOOOOOO!!!!");
			_movingTrainers.add(rand17); //45
			//19,19
			Trainer rand18 = new Trainer.MaroonHat(null);
			rand18.createHome(19, 19);
			rand18.setDirectionAndImage(FACESOUTH);
			rand18.getDialogue().add("WOOOOOOO!!!");
			_movingTrainers.add(rand18); //46
			//20,19
			Trainer rand19 = new Trainer.NiceHair(null);
			rand19.createHome(20, 19);
			rand19.setDirectionAndImage(FACESOUTH);
			rand19.getDialogue().add("YEEEAAAAHHH!!!");
			_movingTrainers.add(rand19); //47
			//21,19
			Trainer rand20 = new Trainer.Pirate(null);
			rand20.createHome(21, 19);
			rand20.setDirectionAndImage(FACESOUTH);
			rand20.getDialogue().add("Are you going to the Dave Binder concert tomorrow?");
			_movingTrainers.add(rand20); //48
			//16,18
			Trainer rand21 = new Trainer.Ponytail(null);
			rand21.createHome(16, 18);
			rand21.setDirectionAndImage(FACESOUTH);
			rand21.getDialogue().add("YEAH SPRING WEEKEND!");
			_movingTrainers.add(rand21); //49
			//17,20
			Trainer rand22 = new Trainer.PurpleHatGirl(null);
			rand22.createHome(17, 20);
			rand22.setDirectionAndImage(FACESOUTH);
			rand22.getDialogue().add("WoOoOoOoOoO!!!!");
			_movingTrainers.add(rand22); //50
			//17,21
			Trainer rand23 = new Trainer.RedBandanaGirl(null);
			rand23.createHome(17, 21);
			rand23.setDirectionAndImage(FACESOUTH);
			rand23.getDialogue().add("WOOOOOO!!!!!!!!");
			_movingTrainers.add(rand23); //51
			//18,22
			Trainer rand24 = new Trainer.RedOveralls(null);
			rand24.createHome(18, 22);
			rand24.setDirectionAndImage(FACESOUTH);
			rand24.getDialogue().add("YAAAAAA!!!");
			_movingTrainers.add(rand24); //52
			//18,24
			Trainer rand25 = new Trainer.RedstripeShirt(null);
			rand25.createHome(18, 24);
			rand25.setDirectionAndImage(FACESOUTH);
			rand25.getDialogue().add("YEAH GLITCH MOB!");
			_movingTrainers.add(rand25); //53
			//17,24
			Trainer rand26 = new Trainer.YellowHatBoy(null);
			rand26.createHome(17, 24);
			rand26.setDirectionAndImage(FACESOUTH);
			rand26.getDialogue().add("WOOOOOOO!!!");
			_movingTrainers.add(rand26); //54
			
			if(_gbs.getPlayer().getAllItems().get(Item.SPRING_WEEKEND_TICKET).getRemaining() == 0){
				Trainer cop = new Trainer.GreenHat(null);
				cop.createHome(20, 27);
				cop.getDialogue().add("I'm sorry, but I can't let you past without a ticket.");
				cop.getDialogue().add("You can purchase yours on Brown St. (South).");
				_movingTrainers.add(cop); //55
			}
			
		
	/*
	A 	TM 20
	*/			
			}
		
		Trainer cut1 = new Trainer.CutBush(_gbs.getPlayer(), this._roomNum, this);
		cut1.createHome(36, 14);
		this.getMovingTrainers().add(cut1);
		Trainer cut2 = new Trainer.CutBush(_gbs.getPlayer(), this._roomNum, this);
		cut2.createHome(36, 23);
		this.getMovingTrainers().add(cut2);
		
		Trainer swipe1 = new Trainer.Swiper(_gbs.getPlayer(), this, 8, _movingTrainers.size()+1);
		swipe1.createHome(8, 30, -1000, -1000, "","");
		_movingTrainers.add(swipe1);
		Trainer barr1 = new Trainer.Barricade();
		barr1.createHome(5, 30, -1000, -1000, "","");
		_movingTrainers.add(barr1);

		Trainer cut3 = new Trainer.CutCan(_gbs.getPlayer(), this._roomNum, this);
		cut3.createHome(14, 3);
		this.getMovingTrainers().add(cut3);
		
		Trainer cut4 = new Trainer.CutCan(_gbs.getPlayer(), this._roomNum, this);
		cut4.createHome(29, 27);
		this.getMovingTrainers().add(cut4);
		
		Trainer cut5 = new Trainer.CutCan(_gbs.getPlayer(), this._roomNum, this);
		cut5.createHome(26, 43);
		this.getMovingTrainers().add(cut5);
		
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	public void createBase(){
		this.setBackground(Color.BLACK);
		this.description = "Main Green";
		this._roomNum = 13;
		_textVisible = new boolean[0];
		this.xConstant = 104;//102
		this.yConstant = 124;//118,110
		this._mapX=130;
		this._mapY=299;
		this._flyX=22;
		this._flyY=4;
		this._outdoors=true;
		
		this.setBikeAllow(true);
		
	//	if(this._movingTrainers == null){
			
			this.loadAllEvents();
			this.addTrainers();
	//	}
		
		if(springWeekend && !this.getMovingTrainers().get(GLITCH).isDefeated()){
			this.song = M2.GLITCH_MOB_BATTLE;
		}
		else if(springWeekend && this.getMovingTrainers().get(GLITCH).isDefeated()){
			this.song = M2.GAMBINO_BATTLE;
		}
		else{
			this.song = M2.MAIN_GREEN;
		}
		
	this._wildPokemon = new Vector<Pokemon>();
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		for(int i = 3; i <= 6; i++)
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));

		for(int i = 3; i <= 6; i++)
			this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(i));
		
		for(int i = 5; i <= 6; i++)
			this._wildPokemon.add(new Pokemon.Caterpie().setWildLevel(i));
		
		for(int i = 5; i <= 5; i++)
			this._wildPokemon.add(new Pokemon.Weedle().setWildLevel(i));
	
		
		this.createGrid();
	}
	
	public void createBaseRoom(){
		this.createBase();
		try{
			if(!springWeekend){
				SysOut.print("Not spring weekend");
				if(((GameBoyScreen.finishedLoading))){
					_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/MainGreen/MainGreenB1.png"));
					_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/MainGreen/MainGreenB1O.png"));
				}
			}
			else{
				SysOut.print("Yes. Spring Weekend");
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/MainGreen/MainGreenSW.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/MainGreen/MainGreenSWO.png"));
			}
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}

	}

	public void createGrid(){
		this._room = new Room(39,47);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/MainGreen1.cmap"));
		if(springWeekend){
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/MainGreenSW.cmap"));
		}
		for(int i = 0; i < 47; i++){
			for(int i2 = 0; i2 < 39; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		//this.tileImage(g2, _roomSource);
		
		this.drawAllTrainers(g2, xConstant, yConstant, _movingTrainers);
		this.drawPlayer(g2);
		this.drawOptimalImage(g2, _roomO);
		//this.tileImage(g2, _roomO);
		this.drawBox(g2);

		this.drawAllGenerics(g2);
	}

	public void enterRoom(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		
		if(((xInd == 27)||(xInd == 28)) && (yInd == 2)){
			super.enterRoom(_gbs.ROUTE_2, 48, 54, FACENORTH);
		}
		if(((xInd == 33)) && (yInd == 12)){
			super.enterRoom(_gbs.SALOMON, 3, 10, FACENORTH);
		}
		
		if(((xInd == 33)) && (yInd == 29)){
			super.enterRoom(_gbs.WILSON_LOBBY, 3, 13, FACENORTH);
		}
		
		if(((xInd == 32)) && (yInd == 20)){
			super.enterRoom(_gbs.SAYLES, 5, 0, FACESOUTH);
		}
		
		if(yInd == 3){
			if(xInd == 17){
				super.enterRoom(_gbs.BLUE_ROOM, 8, 12, FACENORTH);
			}
			else if(xInd == 18){
				super.enterRoom(_gbs.BLUE_ROOM, 9, 12, FACENORTH);
			}
			else if(xInd == 22){
				super.enterRoom(_gbs.BLUE_ROOM, 24, 12, FACENORTH);
			}
			else if(xInd == 23){
				super.enterRoom(_gbs.BLUE_ROOM, 25, 12, FACENORTH);
			}
		}
		
		//QuietGreen
 		if((xInd == 0) && (yInd == 22)){
 			super.enterRoom(_gbs.QUIET_GREEN, 26, 1, FACESOUTH);
 		}
 		
 		if((xInd == 8 || xInd == 5) && yInd == 30){
 			super.enterRoom(_gbs.SLATER_BASEMENT, 5,7,FACENORTH);
 		}
 		
 		//Goes to Univ Hall.
 		if((xInd == 6) && (yInd == 20)){
 			super.enterRoom(_gbs.UNIVERSITY_HALL_LOBBY, 1, 5, FACENORTH);
 		}
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		if((xInd == 27 || xInd == 28) && yInd == 46 && SOUTH){
			if(xInd == 27){
				super.enterRoomSeamless(_gbs.ROUTE_1, 11, 2, FACESOUTH);
			}
			if(xInd == 28){
				super.enterRoomSeamless(_gbs.ROUTE_1, 12, 2, FACESOUTH);
			}
		}
		
		//LINCOLN FIELD
		if((xInd == 37) && (yInd == 14 ||yInd ==23) && EAST){
			if(yInd == 14){
				super.enterRoomSeamless(_gbs.LINCOLN_FIELD, 2, 15, FACEEAST);
			}
			if(yInd == 23){
				super.enterRoomSeamless(_gbs.LINCOLN_FIELD, 2, 24, FACEEAST);
			}
		}
		
		if((xInd == 23 && yInd == 17 && springWeekendDefeated)){
			//super.enterRoom(_gbs.MAIN_GREEN, 23, 17, FACEEAST);
		}
	}
	
	public void afterBattle(){
		super.afterBattle();
		SysOut.print("WHY NOT BEAR??? " + _interruptedTrainer + " ?= " + BEAR);
		if(_interruptedTrainer == BEAR){
			this.getMovingTrainers().get(BEAR+1).getDialogue().clear(); //Bear+1 is description on statue
			this.getMovingTrainers().get(BEAR+1).getDialogue().add("Inscribed on the statue: Dedicated to Bruno, the legendary Brown Bear.");
			this.getMovingTrainers().get(BEAR+1).getDialogue().add("What's this?");
			this.getMovingTrainers().get(BEAR+1).getDialogue().add("There's something etched on the statue underneath...");
			this.getMovingTrainers().get(BEAR+1).getDialogue().add("'You managed to catch my pet. You are now prepared to face me'.");
			this.getMovingTrainers().get(BEAR+1).getDialogue().add("'I will be waiting for you in the Tower...'");
			this.getMovingTrainers().get(BEAR+1).getDialogue().add("It's signed \"The Founder\". Wonder what it means?");
		}
		if(springWeekend && _interruptedTrainer == GAMBINO){
			SysOut.print("Gambino defeated");
			//springWeekend = false;
			springWeekendDefeated = true;
			for(int i = 21; i < _movingTrainers.size()-4; i++){
				if(i ==47){
					_movingTrainers.get(i).addDest(_movingTrainers.get(i).getXIndex()+2, _movingTrainers.get(i).getYIndex()+1);
				}
				if(i==48){
					_movingTrainers.get(i).addDest(_movingTrainers.get(i).getXIndex()+2, _movingTrainers.get(i).getYIndex()+1);
				}
				if(i==22){
					_movingTrainers.get(i).addDest(_movingTrainers.get(i).getXIndex()+2, _movingTrainers.get(i).getYIndex());
				}
				
				if(i==44){
					_movingTrainers.get(i).addDest(_movingTrainers.get(i).getXIndex()-4, _movingTrainers.get(i).getYIndex());
				}
				_movingTrainers.get(i).addDest(_movingTrainers.get(i).getXIndex(), _movingTrainers.get(i).getYIndex()+11);
				_movingTrainers.get(i).setApproachMethod("avoid");
				_movingTrainers.get(i).setPause(false);
				_movingTrainers.get(i).setDefeatAtFinalDest(true);
				_movingTrainers.get(i).setVanishing(true);
				_movingTrainers.get(i).setAvoidMethod("avoid");
				_movingTrainers.get(i).setReturnMethod("avoid");
				_movingTrainers.get(i).setFirstDest();
				_movingTrainers.get(i).setStationary(false);
			}
		}
	}
	
	public void wild(){

		Pokemon randomEnemy = _wildPokemon.get(0);
		
		int random = (int) (101*Math.random());
		
		if(random < 45){
			randomEnemy = _wildPokemon.get((int)(5*Math.random()));
		}
		else{
			if(random < 90){
				randomEnemy = _wildPokemon.get(4+(int)(5*Math.random()));
			}
			else{
				if(random < 95){
					randomEnemy = _wildPokemon.get(8+(int)(2*Math.random()));
				}
				else{
					if(random < 100){
						randomEnemy = _wildPokemon.get(10+(int)(2*Math.random()));
					}
				}
			}
		}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);

		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}

	public void A_Button(){
		if(!_menuVisible && !_pcVisible){

			this.repaint();
		}
		super.A_Button();
	}
	
	public void Right(){ //22.16
		if(this._xIndex == 22 && this._yIndex == 17 && springWeekendDefeated && springWeekend && !this._menuVisible){
			springWeekend = false;
			this._postGymBlackout.start();
		}
		else{
			super.Right();
		}
	}
	
	public class PostGymBlackoutTimer implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			
			if(_timeCount == 1){
				_busy = true;
				_this.scanForAllEvents();
			}
			else if(_timeCount > 1 && _timeCount < 240){
				_darkLevel++;
			}
			else if(_timeCount == 240){
				_this.createBaseRoom();
			}
			else if(_timeCount > 240 && _timeCount < 480){
				_darkLevel--;
			}
			else if(_timeCount == 480){
				_busy = false;
				_timeCount = 0;
				_postGymBlackout.stop();
				_this.completionCheck=false;
			}
			_this.repaint();
		}
	}
}