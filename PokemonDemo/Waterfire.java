package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


@SuppressWarnings("serial")
public class Waterfire extends PokePanel2 {
	private BufferedImage over, barrier, filter, bridge, champBarrier;
	public static boolean waterfireDone=false;
	private ImageIcon fire;
	private final int RISD=1, MAGMAR_FIRST=10, MAGMAR_LAST=22, COP=23, COP2=24, SURF=25, CHAMP=26, FIGHT_FIRST=27, FIGHT_LAST=33, ITEM_FIRST=34, ITEM_LAST=35;
	
	public Waterfire(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(25);
		
		this.createBase();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Waterfire(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(25);
		
		this.createBase();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			this._NPCpage=0;
			this._interruptedTrainer=0;
			/**
			 *mob
			 */
			
			try{
				Trainer court = new Trainer.Text();
				court.createHome(25, 5);
				court.getDialogue().add("\"Court is in session.\"");
				court.getDialogue().add("Best not to interrupt.");
				this.getMovingTrainers().add(court); //0
				
				Trainer risd = new Trainer.RISDSwiper(_gbs.getPlayer(), this);
				risd.createHome(13, 5,0,2000,"","");
				this.getMovingTrainers().add(risd); //1
				
				Trainer waterplace = new Trainer.Text();
				waterplace.createHome(48, 21);
				waterplace.getDialogue().add("I love the WaterPlace restaurant!");
				waterplace.getDialogue().add("It's especially romantic during WaterFire.");
				this.getMovingTrainers().add(waterplace); //2
				
				Trainer hypno = new Trainer.Text();
				hypno.createHome(24, 21);
				hypno.getDialogue().add("Hypno is staring deep into the back of your mind.");
				hypno.getDialogue().add("You start to feel very drowsy...");
				this.getMovingTrainers().add(hypno); //3
				
				Trainer cent = new Trainer.WaterfireCenter(_gbs.getPlayer(),this._roomNum,this);
				cent.createHome(23, 21);
				this.getMovingTrainers().add(cent); //4
				
				Trainer arc = new Trainer.Text();
				arc.createHome(31, 21);
				arc.getDialogue().add("Arcanine: AARRRFFFF!");
				arc.getDialogue().add("Its name tag says: \"Chief of Fire Operations.\"");
				this.getMovingTrainers().add(arc); //5

				Trainer gold = new Trainer.Text();
				gold.createHome(30, 21);
				gold.getDialogue().add("Golduck: GOLLLLDUCK!");
				gold.getDialogue().add("Its name tag says: \"Chief of Water Operations.\"");
				this.getMovingTrainers().add(gold); //6
				
				this._thisMartContains.clear();
				this._thisMartContains.add(Item.GREAT_BALL);
				this._thisMartContains.add(Item.FRESH_WATER);
				this._thisMartContains.add(Item.BUBBLE_TEA);
				this._thisMartContains.add(Item.SODA_POP);
				this._thisMartContains.add(Item.REVIVE);
				this._thisMartContains.add(Item.SUPER_REPEL);
				this._thisMartContains.add(Item.ICE_HEAL);
				this._thisMartContains.add(Item.BURN_HEAL);
				
				Trainer mart1 = new Trainer.MartMenu("WaterFire", this);
				mart1.createHome(13, 21);
				this.getMovingTrainers().add(mart1); //7
				
				Trainer mart2 = new Trainer.Text();
				mart2.createHome(12, 21);
				mart2.getDialogue().add("Come take a look at our merchandise!");
				mart2.getDialogue().add("Donate today to Keep the Fires Burning!");
				this.getMovingTrainers().add(mart2); //8
				
				Trainer risdSignTrainer = new Trainer.Sign();
				risdSignTrainer.createHome(12, 6);
				risdSignTrainer.getDialogue().add("RISD: Fleet Library");
				this.getMovingTrainers().add(risdSignTrainer); //9
				
				//Magmars
				Trainer mag1 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(19), "Magmar: Maaagmaaaaar...", "P-Magmar", M2.MAGMAR);
				mag1._canSurf=true;
				mag1.createHome(7, 15);
				mag1.setDirectionAndImage(FACEWEST);
				this.getMovingTrainers().add(mag1); //10
				
				Trainer mag2 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(20), "Magmar: Grrrrrr...", "P-Magmar", M2.MAGMAR);
				mag2.createHome(12, 15); 
				mag2.setDirectionAndImage(FACENORTH);
				this.getMovingTrainers().add(mag2); //11
				
				Trainer mag3 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(21), "Magmar: Maagmaaar?", "P-Magmar", M2.MAGMAR);
				mag3.createHome(22, 15); 
				mag3.setDirectionAndImage(FACEWEST);
				this.getMovingTrainers().add(mag3); //12
				
				Trainer mag4 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(22), "Magmar: Mmmmmm. Rrrrrr.", "P-Magmar", M2.MAGMAR);
				mag4.createHome(27, 15); 
				mag4.setDirectionAndImage(FACESOUTH);
				this.getMovingTrainers().add(mag4); //13
				
				Trainer mag5 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(23), "Magmar: Magmar.", "P-Magmar", M2.MAGMAR);
				mag5.createHome(32, 15); 
				mag5.setDirectionAndImage(FACEEAST);
				this.getMovingTrainers().add(mag5); //14
				
				Trainer mag6 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(24), "Magmar: Mag. Mar.", "P-Magmar", M2.MAGMAR);
				mag6.createHome(36, 15); 
				mag6.setDirectionAndImage(FACESOUTH);
				this.getMovingTrainers().add(mag6); //15
				
				Trainer mag7 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(25), "Magmar: Maaaaa...", "P-Magmar", M2.MAGMAR);
				mag7.createHome(37, 12); 
				mag7.setDirectionAndImage(FACESOUTH);
				this.getMovingTrainers().add(mag7); //16
				
				Trainer mag8 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(26), "Magmar: Mmmmrrr...", "P-Magmar", M2.MAGMAR);
				mag8.createHome(40, 11); 
				mag8.setDirectionAndImage(FACEWEST);
				this.getMovingTrainers().add(mag8); //17
				
				Trainer mag9 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(27), "Magmar: ......", "P-Magmar", M2.MAGMAR);
				mag9.createHome(43, 12); 
				mag9.setDirectionAndImage(FACEWEST);
				this.getMovingTrainers().add(mag9); //18
				
				Trainer mag10 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(28), "Magmar: Magmarmagmar.", "P-Magmar", M2.MAGMAR);
				mag10.createHome(44, 15); 
				mag10.setDirectionAndImage(FACENORTH);
				this.getMovingTrainers().add(mag10); //19
				
				Trainer mag11 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(29), "Magmar: *blank stare*", "P-Magmar", M2.MAGMAR);
				mag11.createHome(43, 18); 
				mag11.setDirectionAndImage(FACENORTH);
				this.getMovingTrainers().add(mag11); //20
				
				Trainer mag12 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(30), "Magmar: Maaaagmar.", "P-Magmar", M2.MAGMAR);
				mag12.createHome(40, 19);
				mag12.setDirectionAndImage(FACEEAST);
				this.getMovingTrainers().add(mag12); //21
				
				Trainer mag13 = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Magmar().setWildLevel(31), "Magmar: MAAAAAGMAAAR.", "P-Magmar", M2.MAGMAR);
				mag13.createHome(37, 18); 
				mag13.setDirectionAndImage(FACEEAST);
				this.getMovingTrainers().add(mag13); //22
				
				Trainer cop = new Trainer.Cop(null);
				cop.createHome(2, 24);
				cop.setDirectionAndImage(FACEWEST);
				cop.getDialogue().add("Cop: I'm sorry, the bus tunnel is closed for WaterFire.");
				cop.getDialogue().add("There are simply too many people out on the streets.");
				
				this.getMovingTrainers().add(cop); //23
				
				Trainer cop2 = new Trainer.SurfingCopOnLapras(null);
				cop2.createHome(3, 15, cop2._xCorrect, cop2._yCorrect, "wander","wander");
				cop2.addDest(6,15);
				cop2.addDest(3, 15);
				cop2.setStationary(true);
				cop2.setFirstDest();
				cop2.getDialogue().add("Cop: The Magmars are the sources of the fire, and WaterFire will...");
				cop2.getDialogue().add("...continue until they are out of energy.");
				this.getMovingTrainers().add(cop2); //24
				
				Trainer surf = new Trainer.SurfGal(_gbs.getPlayer(), _gbs, this);
				surf.createHome(5, 18);
				surf.setLOS(2);
				surf.setDirectionAndImage(FACEWEST);
				if(_gbs.getPlayer().isGymLeaderDefeated(5))
				{
					surf.setVanishing(true);
					surf.defeat();
				}
				this.getMovingTrainers().add(surf); //25
				
				
				Trainer champ = new Trainer.Scientist(null);
				champ.createHome(49, 8);
				if(!QuietGreen.champion){
				champ.getDialogue().add("Only Brown Alumni may continue past.");
				}
				else{
				champ.getDialogue().add("Go right ahead, " + _gbs.getPlayer().getPlayerName() + "!");
				}
				champ.setDirectionAndImage(FACESOUTH);
				this.getMovingTrainers().add(champ); //26
				
				
				
				///FIGHTERS
				Vector<Pokemon> oneB= new Vector<Pokemon>();
				oneB.add(new Pokemon.Gyarados().setWildLevel(23));
				Trainer one = new Trainer.Elder(oneB);
				one.createHome(33, 19);
				one.setDirectionAndImage(FACEEAST);
				one.getDialogue().add("I remember back when I was a young RISD student.");
				one.getDialogue().add("Don't be fooled, I've still got my power!");
				one.setDefeatDialogue("Sigh, you kids and your iPhones...");
				one.setMoney(1020);
				one.setLOS(4);
				one.setName("Bernard");
				one.setType("Elder");
				one.setBattleImage(TImg.M_ARTIST);
				this.getMovingTrainers().add(one); //27
				
				
				
				Vector<Pokemon> twoB= new Vector<Pokemon>();
				twoB.add(new Pokemon.Poliwag().setWildLevel(20));
				twoB.add(new Pokemon.Seel().setWildLevel(20));
				twoB.add(new Pokemon.Ponyta().setWildLevel(20));
				Trainer two = new Trainer.PurpleHatGirl(twoB);
				two.createHome(15, 6);
				two.setDirectionAndImage(FACENORTH);
				two.getDialogue().add("Architecture Design Principles is so much work.");
				two.getDialogue().add("I'm gonna take out my frustrations on you.");
				two.setDefeatDialogue("Thank goodness for Winter Session.");
				two.setMoney(200);
				two.setLOS(4);
				two.setType("Chain Smoker");
				two.setName("Mina");
				two.setBattleImage(TImg.F_BLACK_TRENCHCOAT_BLONDE_HAIR);
				this.getMovingTrainers().add(two); //28
	
				Vector<Pokemon> threeB= new Vector<Pokemon>();
				threeB.add(new Pokemon.Farfetchd().setWildLevel(19));
				threeB.add(new Pokemon.Jigglypuff().setWildLevel(19));
				threeB.add(new Pokemon.Shellder().setWildLevel(19));
				threeB.add(new Pokemon.Sandshrew().setWildLevel(19));
				Trainer three = new Trainer.Swimmer(threeB);
				three.createHome(22, 11);
				three.setDirectionAndImage(FACENORTH);
				three.getDialogue().add("I'm diving for treasure at the bottom of the river!");
				three.setDefeatDialogue("Nothing but ashes down there...");
				three.setMoney(380);
				three.setLOS(0);
				three.setType("Swimmer");
				three.setName("James");
				three.setBattleImage(TImg.M_SWIMMER);
				this.getMovingTrainers().add(three); //29
				
				Vector<Pokemon> fourB= new Vector<Pokemon>();
				fourB.add(new Pokemon.Staryu().setWildLevel(21));
				fourB.add(new Pokemon.Krabby().setWildLevel(21));
				Trainer four = new Trainer.Backpacker(fourB);
				four.createHome(27, 25);
				four.setDirectionAndImage(FACESOUTH);
				four.getDialogue().add("Hey! Wouldn't it be cool to battle here?");
				four.setDefeatDialogue("Losing is not as cool...");
				four.setMoney(735);
				four.setLOS(5);
				four.setType("Townie");
				four.setName("Kevin");
				this.getMovingTrainers().add(four); //30
				
				Vector<Pokemon> fiveB= new Vector<Pokemon>();
				fiveB.add(new Pokemon.Growlithe().setWildLevel(20));
				fiveB.add(new Pokemon.Charmander().setWildLevel(20));
				fiveB.add(new Pokemon.Growlithe().setWildLevel(20));
				Trainer five = new Trainer.GlassesGirl(fiveB);
				five.createHome(21, 20);
				five.setDirectionAndImage(FACEEAST);
				five.getDialogue().add("I'm only here for the Fire part.");
				five.getDialogue().add("I think the water is just not as interesting.");
				five.setDefeatDialogue("All out of fuel!");
				five.setMoney(700);
				five.setLOS(2);
				five.setName("Katie");
				five.setType("Amateur Pyromaniac");
				this.getMovingTrainers().add(five); //31
				
				Vector<Pokemon> sixB= new Vector<Pokemon>();
				sixB.add(new Pokemon.Clefairy().setWildLevel(22));
				sixB.add(new Pokemon.Exeggcute().setWildLevel(22));
				Trainer six = new Trainer.BrownMediumHair(sixB);
				six.createHome(14, 10);
				six.addDest(17, 10);
				six.addDest(14, 10);
				six.setFirstDest();
				six.setStationary(false);
				six.getDialogue().add("Get back in the fiery water!");
				six.setDefeatDialogue("Sorry...Dane Cook joke, middle school memories.");
				six.setMoney(440);
				six.setLOS(3);
				six.setName("Emily");
				six.setType("Undergrad");
				this.getMovingTrainers().add(six); //32
				
				
				Vector<Pokemon> sevenB= new Vector<Pokemon>();
				sevenB.add(new Pokemon.Diglett().setWildLevel(19));
				sevenB.add(new Pokemon.Doduo().setWildLevel(19));
				sevenB.add(new Pokemon.Paras().setWildLevel(19));
				sevenB.add(new Pokemon.Psyduck().setWildLevel(19));
				Trainer seven = new Trainer.BlackDude(sevenB);
				seven.createHome(25, 9);
				seven.addDest(29, 11);
				seven.addDest(25, 9);
				seven.setFirstDest();
				seven.setStationary(false);
				seven.getDialogue().add("Psh, I can play better WaterFire music than this.");
				seven.getDialogue().add("Quikhanz to the rescue!");
				seven.setDefeatDialogue("Alright, I see you've got skills too.");
				seven.setMoney(580);
				seven.setLOS(4);
				seven.setName("Undergrad");
				seven.setType("Eric");
				this.getMovingTrainers().add(seven); //33
				
	Trainer item1 = new Trainer.ItemObject(new Item.MaxEther());
	item1.createHome(23, 6);
	this.getMovingTrainers().add(item1);  //34
	
	Trainer item2 = new Trainer.ItemObject(new Item.SuperPotion());
	item2.createHome(42, 7);
	this.getMovingTrainers().add(item2); //35			
				
				
				//MUST BE LAST.
				//Mob, only exists before 5th gym leader.
				if(!_gbs.getPlayer().isGymLeaderDefeated(5)){
					//Add the mob.
					
					Trainer barnaby = new Trainer.Elder(null);
					barnaby.createHome(19, 22);
					barnaby.setDirectionAndImage(FACEEAST);
					barnaby.getDialogue().add("Barnaby: Thanks so much for visiting WaterFire!");
					barnaby.getDialogue().add("Please come again! And do please donate.");
					this.getMovingTrainers().add(barnaby);
					
					Trainer prov = new Trainer.GlassesGuy1(null);
					prov.createHome(17, 24);
					prov.setDirectionAndImage(FACEWEST);
					if(!waterfireDone){
						prov.getDialogue().add("So...aside from WaterFire, is there anything cool in Providence?");
					}
					else{
						prov.getDialogue().add("So...now what? Maybe I'll hit up the mall over there.");
					}
					this.getMovingTrainers().add(prov);
					
					Trainer fire = new Trainer.GraySkirt(null);
					fire.createHome(16, 25);
					fire.setDirectionAndImage(FACEEAST);
					if(!waterfireDone){
						fire.getDialogue().add("WaterFire? Why not FireWater?");
					}
					else{
						fire.getDialogue().add("Well I guess it's just Water now...");
					}
					this.getMovingTrainers().add(fire);
					
					Trainer beautiful = new Trainer.StrawHat(null);
					beautiful.createHome(18, 25);
					beautiful.setDirectionAndImage(FACESOUTH);
					if(!waterfireDone){
						beautiful.getDialogue().add("This place is so beautiful! I'd come every weekend if I could.");
					}
					else{
						beautiful.getDialogue().add("Aw, it's over.");
					}
					this.getMovingTrainers().add(beautiful);
					
					//19 25
					Trainer quads = new Trainer.BlondeDude(null);
					quads.createHome(19, 25);
					quads.setDirectionAndImage(FACEWEST);
					if(!waterfireDone){
						quads.getDialogue().add("I've never been down the hill before! Now to walk back up...");
					}
					else{
						quads.getDialogue().add("Ugh...guess I gotta work those quad muscles.");
					}
					this.getMovingTrainers().add(quads);
					
					//20 24
					Trainer island = new Trainer.ChampGuy();
					island.createHome(20, 24);
					island.setDirectionAndImage(FACENORTH);
					if(!waterfireDone){
						island.getDialogue().add("Yo, Champ in the making!");
						island.getDialogue().add("...actually, this is my day off.");
					}
					else{
						island.getDialogue().add("Well, back to work.");
					}
					this.getMovingTrainers().add(island);
					
					//22 25
					Trainer newport = new Trainer.Lorelei(null);
					newport.createHome(22, 25);
					newport.setDirectionAndImage(FACESOUTH);
					if(!waterfireDone){
						newport.getDialogue().add("I came all the way from Newport to see this.");
					}
					else{
						newport.getDialogue().add("Well, time to get back on bus 60.");
					}
					this.getMovingTrainers().add(newport);
					
					//14 24 18 22
					Trainer risdWant = new Trainer.DirtyBlondeGirl(null);
					risdWant.createHome(14, 24,risdWant._xCorrect,risdWant._yCorrect,"avoid", "avoid");
					risdWant.addDest(18, 22);
					risdWant.addDest(14, 24);
					risdWant.setFirstDest();
					risdWant.setStationary(false);
					if(!waterfireDone){
						risdWant.getDialogue().add("I wanna be a RISD student! I'm sure it's great doing art all day.");
					}
					else{
						risdWant.getDialogue().add("Just talked to a RISD student and got a reality check.");
						risdWant.getDialogue().add("No sleep in sight for those guys.");
					}
					this.getMovingTrainers().add(risdWant);
					
					
					//8 22
					Trainer music = new Trainer.BlueBro(null);
					music.createHome(8,22);
					music.setDirectionAndImage(FACESOUTH);
					if(!waterfireDone){
						music.getDialogue().add("This place always has interesting music choices.");
					}
					else{
						music.getDialogue().add("There should be WaterFire dance parties.");
					}
					this.getMovingTrainers().add(music);
					
					//12 25
					Trainer gondola = new Trainer.GreenHat(null);
					gondola.createHome(12, 25);
					gondola.setDirectionAndImage(FACEWEST);
					if(!waterfireDone){
						gondola.getDialogue().add("Wanna take a ride on my gondola?");
						gondola.getDialogue().add("I mean...in. In my gondola.");
					}
					else{
						gondola.getDialogue().add("The offer still stands.");
					}
					this.getMovingTrainers().add(gondola);
					
					//30 23
					Trainer id = new Trainer.RedHairBike(null);
					id.createHome(30, 23);
					id.setDirectionAndImage(FACEEAST);
					if(!waterfireDone){
						id.getDialogue().add("I wonder if Brown students can get into the RISD library.");
					}
					else{
						id.getDialogue().add("Maybe you need an ID?");
					}
					this.getMovingTrainers().add(id);
					//32 25
					Trainer smell = new Trainer.BrownGuy(null);
					smell.createHome(32, 25);
					smell.setDirectionAndImage(FACENORTH);
					if(!waterfireDone){
						smell.getDialogue().add("As weird as this is...WaterFire smells kinda good.");
					}
					else{
						smell.getDialogue().add("The pleasant aroma lingers on.");
					}
					this.getMovingTrainers().add(smell);
					
					//13,11 photographer
					Trainer photo = new Trainer.Photographer(null);
					photo.createHome(13, 11);
					photo.setDirectionAndImage(FACEWEST);
					if(!waterfireDone){
						photo.getDialogue().add("He's taking photos. Probably shouldn't disturb.");
					}
					else{
						photo.getDialogue().add("Hm...the river doesn't seem too photogenic without the fire.");
					}
					this.getMovingTrainers().add(photo);
				}
				
			}
			catch(IOException ioe){
				
			}
			 
		
	}

	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(RISD).isDefeated()){
			this.getCheckList().set(0,1);
		}
		//the next 13 events, 1 through 13
		for (int i=MAGMAR_FIRST; i<=MAGMAR_LAST; i++){
			if(this.getMovingTrainers().get(i).isDefeated()){
				this.getCheckList().set(i-MAGMAR_FIRST+1,1);
			}
		}
		//Event 14: completion.
		int magCount=0;
		for (int i=MAGMAR_FIRST; i<=MAGMAR_LAST; i++){
			if(this.getMovingTrainers().get(i).isDefeated()){
				magCount++;
			};
		}
		if(magCount==13){
			waterfireDone=true;
			this.getCheckList().set(14,1);
			this.getMovingTrainers().get(COP).setVanishing(true);
			this.getMovingTrainers().get(COP).defeat();
			this.getMovingTrainers().get(COP2).setVanishing(true);
			this.getMovingTrainers().get(COP2).defeat();
			if(this.getSong() != M2.getCurrentSong())
				M2.playBG(this.getSong());
		}
		else{
			waterfireDone=false;
			this.getCheckList().set(14,0);
			this.getMovingTrainers().get(COP).setVanishing(false);
			this.getMovingTrainers().get(COP).unDefeat();
			this.getMovingTrainers().get(COP2).setVanishing(false);
			this.getMovingTrainers().get(COP2).unDefeat();
		}
		
		//Remaining events.
		if(_movingTrainers.get(SURF).getLOS() ==0){
			this.getCheckList().set(15, 1);
		}
		//Fighting trainers:
		for (int i=FIGHT_FIRST; i<=FIGHT_LAST; i++){
			if(this.getMovingTrainers().get(i).isDefeated()){
				this.getCheckList().set(16+i-FIGHT_FIRST,1);
			}
		}
		//Item trainers
		for (int i=ITEM_FIRST; i<=ITEM_LAST; i++){
			if(this.getMovingTrainers().get(i).isDefeated()){
				this.getCheckList().set(FIGHT_LAST-FIGHT_FIRST+i-ITEM_FIRST,1);
			}
		}
		
	}
	
	public void loadAllEvents(){
		if (this.getCheckList()!=null){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(RISD).defeat();
			}
			//the next 13 events, 1 through 13
			for (int i=MAGMAR_FIRST; i<=MAGMAR_LAST; i++){
				if(this.getCheckList().get(i-MAGMAR_FIRST+1)==1){
					this.getMovingTrainers().get(i).defeat();
				}
			}			
			//Event 14: completion
			if(this.getCheckList().get(14)==1){
				waterfireDone=true;
				this.getMovingTrainers().get(COP).setVanishing(true);
				this.getMovingTrainers().get(COP).defeat();
				this.getMovingTrainers().get(COP2).setVanishing(true);
				this.getMovingTrainers().get(COP2).defeat();
			}
			else{
				waterfireDone=false;
			}
			
			//Remaining Events
			if(this.getCheckList().get(15) == 1){
				this.getMovingTrainers().get(SURF).setLOS(0);
			}
			
			//Fighting trainers:
			for (int i=FIGHT_FIRST; i<=FIGHT_LAST; i++){
				if(this.getCheckList().get(i-FIGHT_FIRST+1)==1){
					this.getMovingTrainers().get(i).defeatAndPostBattle();
				}
			}
			//Item trainers
			for (int i=ITEM_FIRST; i<=ITEM_LAST; i++){
				if(this.getCheckList().get(i-FIGHT_FIRST+1)==1){
					this.getMovingTrainers().get(i).defeat();
				}
			}
		}


	}
	public void addWilds(){
		this._wildSurf = new Vector<Pokemon>();
		for(int i = 17; i <= 18; i++){
			this._wildSurf.add(new Pokemon.Ponyta().setWildLevel(i));
		}
		this._wildSurf.add(new Pokemon.Growlithe().setWildLevel(17));
		for(int i = 15; i <= 16; i++){
			this._wildSurf.add(new Pokemon.Vulpix().setWildLevel(i));
		}
		for(int i = 15; i <= 16; i++){
			this._wildSurf.add(new Pokemon.Psyduck().setWildLevel(i));
		}
		for(int i = 14; i <= 17; i++){
			this._wildSurf.add(new Pokemon.Poliwag().setWildLevel(i));
		}
		for(int i = 10; i <= 15; i++){
			this._wildSurf.add(new Pokemon.Magikarp().setWildLevel(i));
		}
	}

	public void waterWild(){
		Pokemon randomEnemy = _wildSurf.get(0);
		double random = 101*Math.random();
		if(random < 10.0){
			randomEnemy = _wildSurf.get(0 + (int)(2*Math.random()));
		}
		else if(random < 20.0){
			randomEnemy = _wildSurf.get(2);
		}
		else if(random < 35.0){
			randomEnemy = _wildSurf.get(3 + (int)(2*Math.random()));
		}
		else if(random < 45.0){
			randomEnemy = _wildSurf.get(5 + (int)(2*Math.random()));
		}
		else if(random < 60.0){
			randomEnemy = _wildSurf.get(7 + (int)(4*Math.random()));
		}
		else if(random < 100.0){
			randomEnemy = _wildSurf.get(11 + (int)(6*Math.random()));
		}
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void createBase(){
		this.description = "WaterFire";
		if(QuietGreen.champion){
			this.song=M2.GRADUATION;
		}
		else{
			if(!waterfireDone){
				this.song = M2.WATERFIRE_NIGHT;
			}
			else{
				this.song = M2.WATERFIRE_DAY;
			}
		}
		
		this.setMartVisible(false);
		this._martMenuVisible=false;
		this.addTrainers();
		this.addWilds();
		this.loadAllEvents();
		this.PROBABILITY=PokePanel2.WATER_PROB;
		
		this.xConstant=-13;
		this.yConstant=29;
		
		if(!waterfireDone){
			this.setBattleBG(NewBattleScreen.WATERFIRE);
		}
		else{
			this.setBattleBG(NewBattleScreen.WATER);
		}
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=110;
		this._mapY=299;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		this._pkmnCentX=23;
		this._pkmnCentY=22;
		
		//Uncomment if this place is outdoors.
		this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		this._flyX=23;
		this._flyY=22;

		this.setBikeAllow(true);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this._roomNum = _gbs.WATERFIRE;
		this.createGrid();
	}
	public void createBaseRoom(){		
		this.createBase();
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Waterfire/Waterfire Background.png"));
			}
			if(over == null){
				over = ImageIO.read(this.getClass().getResource("/PokemonFiles/Waterfire/Waterfire Over.png"));
			}
			if(bridge==null){
				bridge = ImageIO.read(this.getClass().getResource("/PokemonFiles/Waterfire/Waterfire Bridge.png"));
			}
			if(!waterfireDone && barrier == null){
				barrier = ImageIO.read(this.getClass().getResource("/PokemonFiles/Waterfire/Waterfire Barrier.png"));
			}
			if(!QuietGreen.champion && champBarrier == null){
				champBarrier = ImageIO.read(this.getClass().getResource("/PokemonFiles/Waterfire/Waterfire Barrier2.png"));
			}
			if(!waterfireDone && filter == null){
				filter = ImageIO.read(this.getClass().getResource("/PokemonFiles/Waterfire/Waterfire Night.png"));
			}
			if(fire==null){
				fire = new ImageIcon(this.getClass().getResource("/PokemonFiles/Waterfire/fire.gif"));
			}
		}
		catch(IOException ioe){}
	}

	public void createGrid(){
		this._room = new Room(54,28);
		Scanner scan;
		if(!_gbs.getSurf()){
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WaterfireLand.cmap"));
		}
		else{
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/WaterfireSurf.cmap"));
		}
		for(int i = 0; i < 28; i++){
			for(int i2 = 0; i2 < 54; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		
		//Block the barrier.
		if(!waterfireDone){
			for(int i = 1; i <= 4; i++){
				this._room._roomGrid[i][25] = 'N';
			}
		}
		if(!QuietGreen.champion){
			for(int i = 7; i <= 8; i++){
				this._room._roomGrid[51][i] = 'N';
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		if(!waterfireDone){
			this.drawOptimalImage(g2, barrier);
		}		
		if(!QuietGreen.champion){
			this.drawOptimalImage(g2, champBarrier);
		}
		if(!_gbs.getSurf()){
			this.drawOptimalImage(g2, bridge);
			this.drawPlayer(g2);
		}
		else{
			this.drawPlayer(g2);
			this.drawOptimalImage(g2, bridge);
		}
		
		for (int i=MAGMAR_FIRST; i<=MAGMAR_LAST; i++){
			if(!this.getMovingTrainers().get(i).isDefeated()){
				fire.paintIcon(this, g2,_movingTrainers.get(i).getXSpace()+xConstant+this._xSpace-4,_movingTrainers.get(i).getYSpace()+yConstant+this._ySpace+1);
			}
		}
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		
		this.drawOptimalImage(g2, over);
		
		if(!waterfireDone){
			this.drawOptimalImage(g2, filter);
		}

		this.drawAllGenerics(g2);
		
	}
	
	public void enterRoom(int xInd, int yInd){
		//Bus Tunnel
 		if((xInd == 1|| xInd == 2|| xInd == 3|| xInd ==4) && (yInd == 26)){
 			super.enterRoom(_gbs.BUS_TUNNEL, 0, 3, FACEEAST);
 		}
 			
 		//Quiet Green
 		if((xInd == 17|| xInd == 18|| xInd == 19) && (yInd == 26)){
 			super.enterRoom(_gbs.QUIET_GREEN, 19, 32, FACENORTH);
 		}
 		
 		//Credits
 		if(xInd == 52 && (yInd == 7||yInd == 8)){
			this.enterRoom(_gbs.CREDITS, 0, 0, FACEEAST);
		}

	}
	
	public void Up(){
		if(!_menuVisible && NORTH && _yIndex == 6 && !_dialogueVisible){
			if(_xIndex == 13 && !this.getMovingTrainers().get(RISD).isDefeated()){
				this.A_Button();
				return;
			}
			if(_xIndex == 25){
				this.A_Button();
				return;
			}
		}
		if(_yIndex==17){
			this.createGrid();
		}
		super.Up();
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
	
	public void Down(){
		if(_yIndex==17){
			this.createGrid();
		}
		super.Down();
	}
	
	public void Right(){
		super.Right();
	}
}