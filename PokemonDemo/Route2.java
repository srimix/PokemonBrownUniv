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
public class Route2 extends PokePanel2 {
	private BufferedImage _roomO;
	private final int LAPRAS=1, STUCK_ITEM=9, LEDGE_ITEM=10, CORNER_ITEM=11, 
					  URBAN_1 = 31, URBAN_2 = 32, URBAN_3 = 33, URBAN_4 = 34, 
					  GARDEN_1 = 35, GARDEN_2 = 36, GARDEN_3 = 37, GARDEN_4 = 38, TM04=46;
	
	public Route2(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(13);
		
		this.createBase();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Route2(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(13);
		
		this.createBase();
		
	}
	
	public void addTrainers(){
			
		this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer truck = new Trainer.ChubbyGuy(null);
				truck.createHome(40, 10, -1, 0, "wander", "wander");
				truck.addDest(33, 10);
				truck.addDest(44, 11);
				truck.addDest(38, 11);
				truck.addDest(40, 10);
				truck.setFirstDest();
				truck.setPause(true);
				truck.setStationary(false);
				truck.getDialogue().clear();
				truck.getDialogue().add("Sorry kid, these trucks are not for rent.");
				truck.getDialogue().add("Hm, you want a faster way around campus?");
				truck.getDialogue().add("You should check out the bike shop at Littlefield.");
				
				
				Trainer lapras = new Trainer.HillelLapras(new Pokemon.Lapras(), _gbs);
				lapras.createHome(39, 23, -5, 0, "freeze", "freeze");
				lapras.addDest(43, 23);
				lapras.addDest(39, 23);
				lapras.setFirstDest();
				lapras.setPause(true);
				lapras.setStationary(false);
				lapras.setVanishing(true);
				lapras.getDialogue().clear();
				
				
				int totalBadges = 0;
				for(int i=1; i<9; i++){
					if(_gbs.getPlayer().isGymLeaderDefeated(i)){
						totalBadges++;
					}
				}

				lapras.getDialogue().add("Lapras is staring at you questioningly.");
				lapras.getDialogue().add("She notices your " + totalBadges + " badges. ");
				
				if (totalBadges<3){
					lapras.getDialogue().add("She walks away, duly unimpressed.");
				}
				else{
					lapras.getDialogue().add("She has taken a liking to you!");
					if(_gbs.getPlayer().getAllActive().size()==6){
						lapras.getDialogue().add("There's no room in your belt for Lapras!");
					}
					else{
						lapras.getDialogue().add("Lapras joined your party!");
					}
				}
				
				
				this._movingTrainers.add(truck); //0
				this._movingTrainers.add(lapras); //1
				
				
				//Add all cut bushes.
				Trainer cut1 = new Trainer.CutCan(_gbs.getPlayer(), _gbs.ROUTE_2, this);
				cut1.createHome(5, 30, 0, 2, "", "");
				Trainer cut2 = new Trainer.CutCan(_gbs.getPlayer(), _gbs.ROUTE_2, this);
				cut2.createHome(45, 33, 0, 2, "", "");
				Trainer cut3 = new Trainer.CutCan(_gbs.getPlayer(), _gbs.ROUTE_2, this);
				cut3.createHome(5, 16, 0, 2, "", "");
				Trainer cut4 = new Trainer.CutCan(_gbs.getPlayer(), _gbs.ROUTE_2, this);
				cut4.createHome(45, 19, 0, 2, "", "");
				
				this._movingTrainers.add(cut1);//2
				this._movingTrainers.add(cut2);//3
				this._movingTrainers.add(cut3);//4
				this._movingTrainers.add(cut4);//5
				
				Trainer rocket = new Trainer.RocketTruck(_gbs.getPlayer(), this._roomNum, this);
				rocket.createHome(19, 16, 0, 0, "", "");
				rocket.setStationary(true);
				
				this._movingTrainers.add(rocket);//6
				
				Trainer blonde = new Trainer.BlondeDude(null);
				blonde.createHome(18, 17, -1, 0, "wander", "wander");
				blonde.addDest(20, 17);
				blonde.addDest(17, 19);
				blonde.addDest(18, 17);
				blonde.setFirstDest();
				blonde.setStationary(false);
				blonde.getDialogue().clear();
				blonde.getDialogue().add("Team Rocket went out of business a while back.");
				blonde.getDialogue().add("They're no longer a crime ring, but their food truck is great.");
			
				this._movingTrainers.add(blonde); //7
				
				
				//Fones alley warning.
				Trainer fonesWarning = new Trainer.DirtyBlondeGirl(null);
				fonesWarning.createHome(44, 42, 0, 0, "avoid", "avoid");
				fonesWarning.setLOS(3);
				fonesWarning.setStationary(true);
				fonesWarning.getDialogue().clear();
				fonesWarning.getDialogue().add("Careful of the Fones Alley roads!");
				fonesWarning.getDialogue().add("Wild pokemon are all around here.");
				fonesWarning.getDialogue().add("Stay on the purple brick and you'll be safe.");
				fonesWarning.getDialogue().add("There are also lots of trainers here, so keep your eyes peeled.");
				
				this._movingTrainers.add(fonesWarning);//8
				
				Trainer stuckItem = new Trainer.ItemObject(new Item.RareCandy());
				stuckItem.setStationary(true);
				stuckItem.createHome(44,41,2,6,"","");
				
				this._movingTrainers.add(stuckItem); //9
				
				Trainer ledgeItem = new Trainer.ItemObject(new Item.OrganicPotion());
				ledgeItem.setStationary(true);
				ledgeItem.createHome(18,39,2,6,"","");
				
				this._movingTrainers.add(ledgeItem);//10
				
				Trainer cornerItem = new Trainer.ItemObject(new Item.PokeBall());
				cornerItem.setStationary(true);
				cornerItem.createHome(6,37,2,6,"","");
				
				this._movingTrainers.add(cornerItem);//11
				
				
				//ledge warning.
				Trainer ledgeWarning = new Trainer.GreenBike(null);
				ledgeWarning.createHome(48, 51, -3, 0, "drunk", "drunk");
				ledgeWarning.addDest(50, 48);
				ledgeWarning.addDest(48, 51);
				ledgeWarning.setFirstDest();
				ledgeWarning.setStationary(false);
				ledgeWarning.getDialogue().clear();
				ledgeWarning.getDialogue().add("There are a lot of one-way ledges on this path.");
				ledgeWarning.getDialogue().add("Think twice before you jump a ledge...");
				
				
				this._movingTrainers.add(ledgeWarning);//12
				
				Trainer cutExists = new Trainer.GlassesGirl(null);
				cutExists.createHome(41, 31, 0, 0, "wander", "wander");
				cutExists.addDest(37,32);
				cutExists.addDest(41,31);
				cutExists.setFirstDest();
				cutExists.setStationary(false);
				cutExists.getDialogue().add("Maneuvering around those ledges in the road can be annoying.");
				cutExists.getDialogue().add("But if you had HM: Cut, you could slash down those trash cans.");
				
				this._movingTrainers.add(cutExists);//13
				
				//Random signs.
				Trainer sign1 = new Trainer.Text();
				sign1.createHome(46, 2, 0,0,"","");
				sign1.getDialogue().clear();
				sign1.getDialogue().add("Brown St. and Meeting St.");
				this._movingTrainers.add(sign1);//14
				
				Trainer sign2 = new Trainer.Text();
				sign2.createHome(46, 16, 0,0,"","");
				sign2.getDialogue().clear();
				sign2.getDialogue().add("Brown St. and Olive St.");
				this._movingTrainers.add(sign2);//14
				
				Trainer sign3 = new Trainer.Text();
				sign3.createHome(46, 30, 0,0,"","");
				sign3.getDialogue().clear();
				sign3.getDialogue().add("Brown St. and Angell St.");
				this._movingTrainers.add(sign3);//15
				
				Trainer sign4 = new Trainer.Text();
				sign4.createHome(46, 42, 0,0,"","");
				sign4.getDialogue().clear();
				sign4.getDialogue().add("Fones Alley");
				this._movingTrainers.add(sign4);//16
				
				Trainer sign5 = new Trainer.Text();
				sign5.createHome(46, 53, 0,0,"","");
				sign5.getDialogue().clear();
				sign5.getDialogue().add("Brown St. and Waterman St.");
				sign5.getDialogue().add("--To Main Green");
				this._movingTrainers.add(sign5);//17
				
				Trainer signtwc = new Trainer.Text();
				signtwc.createHome(38, 49, 0,0,"","");
				signtwc.getDialogue().clear();
				signtwc.getDialogue().add("Third World Center");
				this._movingTrainers.add(signtwc);//18
				
				Trainer signpitexit = new Trainer.Text();
				signpitexit.createHome(33, 2, 0,0,"","");
				signpitexit.getDialogue().clear();
				signpitexit.getDialogue().add("Olive Pit Exit");
				this._movingTrainers.add(signpitexit);//19
				
				Trainer signpitenter = new Trainer.Text();
				signpitenter.createHome(11, 16, 0,0,"","");
				signpitenter.getDialogue().clear();
				signpitenter.getDialogue().add("Olive Pit Entrance");
				this._movingTrainers.add(signpitenter);//20
				
				//It's locked
				Trainer locked1 = new Trainer.Text();
				locked1.createHome(26, 48, 0,0,"","");
				locked1.getDialogue().clear();
				locked1.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked1); //21
				
				Trainer locked2 = new Trainer.Text();
				locked2.createHome(30, 48, 0,0,"","");
				locked2.getDialogue().clear();
				locked2.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked2);//22
				
				Trainer locked3 = new Trainer.Text();
				locked3.createHome(35, 48, 0,0,"","");
				locked3.getDialogue().clear();
				locked3.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked3);//23
				
				Trainer locked4 = new Trainer.Text();
				locked4.createHome(14, 40, 0,0,"","");
				locked4.getDialogue().clear();
				locked4.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked4);//24
				
				Trainer locked5 = new Trainer.Text();
				locked5.createHome(28, 1, 0,0,"","");
				locked5.getDialogue().clear();
				locked5.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked5);//25
				
				Trainer locked6 = new Trainer.Text();
				locked6.createHome(6, 1, 0,0,"","");
				locked6.getDialogue().clear();
				locked6.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked6);//27
				
				Trainer locked7 = new Trainer.Text();
				locked7.createHome(12, 1, 0,0,"","");
				locked7.getDialogue().clear();
				locked7.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked7);//28
				
				Trainer locked8 = new Trainer.Text();
				locked8.createHome(17, 1, 0,0,"","");
				locked8.getDialogue().clear();
				locked8.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked8); //29
				
				Trainer locked9 = new Trainer.Text();
				locked9.createHome(23, 1, 0,0,"","");
				locked9.getDialogue().clear();
				locked9.getDialogue().add("It's locked.");
				this._movingTrainers.add(locked9);//30
				
				
				
				/*
				 * URBAN TRAINER NOMINATED SPOTS

					40, 44, FACENORTH

					26, 44, FACESOUTH

					8, 47, FACEEAST

					20, 43, FACENORTH
					
				*/
				
				Vector<Pokemon> u1 = new Vector<Pokemon>();
				Pokemon u1_1 = new Pokemon.Pidgey().setWildLevel(9);
				Pokemon u1_2 = new Pokemon.Pidgey().setWildLevel(9);
				u1.add(u1_1);
				u1.add(u1_2);
				Trainer urban1 = new Trainer.Ponytail(u1);
				urban1.createHome(40, 44, 0, 0, "avoid", "avoid");
				urban1.setLOS(5);
				urban1.setDirectionAndImage(FACESOUTH);
				urban1.getDialogue().clear();
				urban1.getDialogue().add("I caught a couple of wild Pidgeys in the alley over there.");
				urban1.getDialogue().add("Want to help me test their strength?");
				urban1.setDefeatDialogue("Guess I should have expected as much.");
				urban1.getPostBattleDialogue().add("I'll just have to train harder! I know they have potential!");
				urban1.setPause(true);
				urban1.setType("Janus Fellow");
				urban1.setName("Janine");
				urban1.setMoney(135);
				urban1.setVanishing(false);
				urban1.setStationary(true);
				
				_movingTrainers.add(urban1); //31
				
				Vector<Pokemon> u2 = new Vector<Pokemon>();
				Pokemon u2_1 = new Pokemon.Rattata().setWildLevel(11);
				Pokemon u2_2 = new Pokemon.Ekans().setWildLevel(11);
				u2.add(u2_1);
				u2.add(u2_2);
				Trainer urban2 = new Trainer.BlondeDude(u2);
				urban2.createHome(26, 44, 0, 0, "avoid", "avoid");
				urban2.setLOS(4);
				urban2.setDirectionAndImage(FACENORTH);
				urban2.getDialogue().clear();
				urban2.getDialogue().add("HsSsSsSs...");
				urban2.getDialogue().add("I'm a snaaaaaake!");
				urban2.setDefeatDialogue("HSSSS!!!");
				urban2.getPostBattleDialogue().add("I'll just sssslither away...");
				urban2.setPause(true);
				urban2.setType("Student");
				urban2.setName("Sammy");
				urban2.setMoney(165);
				urban2.setVanishing(false);
				urban2.setStationary(true);
				
				_movingTrainers.add(urban2); //32
				
				Vector<Pokemon> u3 = new Vector<Pokemon>();
				Pokemon u3_1 = new Pokemon.Spearow().setWildLevel(14);
				u3.add(u3_1);
				Trainer urban3 = new Trainer.RedHairBike(u3);
				urban3.createHome(8, 47, 0, 0, "avoid", "avoid");
				urban3.setLOS(3);
				urban3.setDirectionAndImage(FACEWEST);
				urban3.getDialogue().clear();
				urban3.getDialogue().add("I love biking! But I hate Providence roads...");
				urban3.setDefeatDialogue("Fine! I'll just go bike around East Providence instead.");
				urban3.getPostBattleDialogue().add("The roads are just too choppy to pick up good speed...");
				urban3.setPause(true);
				urban3.setType("Biker");
				urban3.setName("Ceanne");
				urban3.setMoney(210);
				urban3.setVanishing(false);
				urban3.setStationary(true);
				
				_movingTrainers.add(urban3); //33
				
				Vector<Pokemon> u4 = new Vector<Pokemon>();
				Pokemon u4_1 = new Pokemon.Rattata().setWildLevel(10);
				Pokemon u4_2 = new Pokemon.Nidoran_M().setWildLevel(10);
				u4.add(u4_1);
				u4.add(u4_2);
				Trainer urban4 = new Trainer.GlassesGuy1(u4);
				urban4.createHome(20, 43, 0, 0, "avoid", "avoid");
				urban4.setLOS(5);
				urban4.setDirectionAndImage(FACESOUTH);
				urban4.getDialogue().clear();
				urban4.getDialogue().add("You've almost made it to the end of the alley!");
				urban4.getDialogue().add("But...(and I've always wanted to say this)...");
				urban4.getDialogue().add("YOU. SHALL. NOT. PASS!!!");
				urban4.setDefeatDialogue("Noooo! Very well, you can pass :)");
				urban4.getPostBattleDialogue().add("Oh, I just love LotR...");
				urban4.setPause(true);
				urban4.setType("LARPer");
				urban4.setName("'Gandalf'");
				urban4.setMoney(150);
				urban4.setVanishing(false);
				urban4.setStationary(true);
				
				_movingTrainers.add(urban4); //34
				/*
					GARDEN

					25,28, FACENORTH

					28, 24, FACESOUTH

					8,25, FACEEAST

					19, 21, FACEWEST
				 */
				
				Vector<Pokemon> g1 = new Vector<Pokemon>();
				Pokemon g1_1 = new Pokemon.Caterpie().setWildLevel(10);
				Pokemon g1_2 = new Pokemon.Weedle().setWildLevel(10);
				Pokemon g1_3 = new Pokemon.Caterpie().setWildLevel(10);
				g1.add(g1_1);
				g1.add(g1_2);
				g1.add(g1_3);
				Trainer garden1 = new Trainer.YellowHatBoy(g1);
				garden1.createHome(25, 28, 0, 0, "avoid", "avoid");
				garden1.setLOS(5);
				garden1.setDirectionAndImage(FACESOUTH);
				garden1.getDialogue().clear();
				garden1.getDialogue().add("There are so many bugs in this garden!");
				garden1.setDefeatDialogue("...I guess none of these bugs are very strong.");
				garden1.getPostBattleDialogue().add("Aren't bugs just super cute?");
				garden1.setPause(true);
				garden1.setName("Corbin");
				garden1.setType("Student");
				garden1.setMoney(100);
				garden1.setVanishing(false);
				garden1.setStationary(true);
				
				_movingTrainers.add(garden1); //35
				
				Vector<Pokemon> ga2 = new Vector<Pokemon>();
				Pokemon g2_1 = new Pokemon.Weedle().setWildLevel(9);
				Pokemon g2_2 = new Pokemon.Kakuna().setWildLevel(9);
				Pokemon g2_3 = new Pokemon.Caterpie().setWildLevel(9);
				Pokemon g2_4 = new Pokemon.Metapod().setWildLevel(9);
				ga2.add(g2_1);
				ga2.add(g2_2);
				ga2.add(g2_3);
				ga2.add(g2_4);
				Trainer garden2 = new Trainer.GreenHat(ga2);
				garden2.createHome(28, 24, 0, 0, "avoid", "avoid");
				garden2.setLOS(5);
				garden2.setDirectionAndImage(FACENORTH);
				garden2.getDialogue().clear();
				garden2.getDialogue().add("Bugs are so cool! They evolve faster than other Pokemon!");
				garden2.setDefeatDialogue("I guess mine haven't evolved enough yet...");
				garden2.getPostBattleDialogue().add("If I train more, maybe they'll evolve again!");
				garden2.setType("Good Guy");
				garden2.setPause(true);
				garden2.setName("Greg");
				garden2.setMoney(90);
				garden2.setVanishing(false);
				garden2.setStationary(true);
				garden2.setBattleImage(TImg.M_BUG_CATCHER);
				
				_movingTrainers.add(garden2); //36
				
				Vector<Pokemon> g3 = new Vector<Pokemon>();
				Pokemon g3_1 = new Pokemon.Metapod().setWildLevel(11);
				Pokemon g3_2 = new Pokemon.Metapod().setWildLevel(11);
				g3.add(g3_1);
				g3.add(g3_2);
				Trainer garden3 = new Trainer.BlackHair(g3);
				garden3.createHome(8, 25, 0, 0, "avoid", "avoid");
				garden3.setLOS(5);
				garden3.setDirectionAndImage(FACEWEST);
				garden3.getDialogue().clear();
				garden3.getDialogue().add("My Pokemon are so Meta...");
				garden3.setDefeatDialogue("Whatever. Don't be so Pokenormative...");
				//garden3.getPostBattleDialogue().add("Ok, so I don't have a Butterfree YET, but I will someday!");
				garden3.setMoney(110);
				garden3.setType("Hipster");
				garden3.setName("James");
				garden3.setVanishing(false);
				garden3.setStationary(true);
				garden3.setBattleImage(TImg.M_BUG_CATCHER);
				
				_movingTrainers.add(garden3); //37
				
				Vector<Pokemon> g4 = new Vector<Pokemon>();
				Pokemon g4_1 = new Pokemon.Jigglypuff().setWildLevel(14);
				g4.add(g4_1);
				Trainer garden4 = new Trainer.DirtyBlondeGirl(g4);
				garden4.createHome(19, 21, 0, 0, "avoid", "avoid");
				garden4.setLOS(4);
				garden4.setDirectionAndImage(FACEEAST);
				garden4.getDialogue().clear();
				garden4.getDialogue().add("Huh? What? Oh I must have fallen asleep in the shade...");
				garden4.setDefeatDialogue("I guess I must have dozed off again mid-battle");
				garden4.getPostBattleDialogue().add("ZzZzZzzzzz....");
				garden4.setMoney(210);
				garden4.setType("Hipster");
				garden4.setName("Robin");
				garden4.setVanishing(false);
				garden4.setStationary(true);
				
				_movingTrainers.add(garden4); //38
				
				Trainer jogger = new Trainer.YellowHatBoy(null);
				jogger.createHome(4,11,0,0,"freeze","freeze");
				jogger.setPause(false);
				jogger.addDest(4,44);
				jogger.addDest(4,11);
				jogger.setFirstDest();
				jogger.setStationary(false);
				jogger.getDialogue().add("Just going for a jog! I like the inclines around here.");
				this._movingTrainers.add(jogger); //39
				
				Trainer dual = new Trainer.DirtyBlondeGirl(null);
				dual.createHome(1,17,0,0,"wander","wander");
				dual.getDialogue().add("I'm a RISD dual degree student! Neuroscience and Painting.");
				dual.getDialogue().add("It's my second year, so I'm at Brown for now.");
				dual.addDest(1,17);
				dual.addDest(4,18);
				dual.setFirstDest();
				dual.setStationary(false);
				this._movingTrainers.add(dual); //40
				
				Trainer rain = new Trainer.BrownGuy(null); 
				rain.createHome(1,28,0,0,"drunk", "drunk");
				rain.addDest(1,34);
				rain.addDest(1,28);
				rain.setFirstDest();
				rain.getDialogue().add("I hear it's gonna rain today. Can't waaaait.");
				rain.getDialogue().add("Sometimes, Thayer even floods and my shoes get soaked.");
				rain.setStationary(false);
				
				this._movingTrainers.add(rain); //41
				
				Trainer PM = new Trainer.BlackHair(null);
				PM.createHome(1,1,0,0,"wander","wander");
				PM.addDest(4,5);
				PM.addDest(1,1);
				PM.setFirstDest();
				PM.setStationary(false);
				PM.getDialogue().add("Welcome to Prospect and Meeting!");
				PM.getDialogue().add("We match you up with your crushes and set you two up to meet!");
				PM.getDialogue().add("Oh, you're not interested? Alright then...");
				
				this._movingTrainers.add(PM); //42
				
				Trainer bb = new Trainer.BlueBro(null);
				bb.createHome(14,32,0,0,"wander","wander");
				bb.addDest(18,33);
				bb.addDest(14,32);
				bb.setFirstDest();
				bb.setStationary(false);
				bb.getDialogue().add("My parents found out I posted on BrownBares!");
				bb.getDialogue().add("...What am I gonna do?!");
				
				this._movingTrainers.add(bb); //43
				
				Trainer lej = new Trainer.BlackGirl(null);
				lej.createHome(47,11,0,0,"wander", "wander");
				lej.addDest(48,18);
				lej.addDest(47, 11);
				lej.setFirstDest();
				lej.setStationary(false);
				lej.getDialogue().add("Have you found any of the legendary Pokemon?");
				lej.getDialogue().add("They used to roam free, but they keep themselves isolated now.");
				lej.getDialogue().add("I'm sure I'll get to see one someday...");
				
				this._movingTrainers.add(lej);//44
				
				Trainer doors = new Trainer.GreenDress(null);
				doors.createHome(16, 4, 0,0,"wander","wander");
				doors.addDest(27,3);
				doors.addDest(16,4);
				doors.setFirstDest();
				doors.setStationary(false);
				doors.getDialogue().add("The doors of some buildings can open at the right situation.");
				doors.getDialogue().add("But others stay locked, like the doors of these houses.");
				doors.getDialogue().add("It would just be rude to turn up out of the blue, uninvited.");

				this._movingTrainers.add(doors); //45
				
				Trainer tm04 = new Trainer.ItemObject(new Item.TM04_Whirlwind());
				tm04.createHome(14, 1);
				this.getMovingTrainers().add(tm04); //46
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		
		
		//Urban
		for(int i = 8; i <= 12; i++)
			this._wildPokemon.add(new Pokemon.Spearow().setWildLevel(i));
		for(int i = 10; i <= 12; i++)
			this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(i));
		for(int i = 8; i <= 10; i++)
			this._wildPokemon.add(new Pokemon.Sandshrew().setWildLevel(i));
		this._wildPokemon.add(new Pokemon.Mankey().setWildLevel(9));
		
		//Garden
		for(int i = 6; i <= 8; i++)
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
		for(int i = 5; i <= 8; i++)
			this._wildPokemon.add(new Pokemon.Spearow().setWildLevel(i));
		for(int i = 3; i <= 7; i++)
			this._wildPokemon.add(new Pokemon.Jigglypuff().setWildLevel(i));
		
	}

	public int getBattleBG(){
		if(_yIndex >= 32){
			return NewBattleScreen.URBAN;
		}
		else if(_yIndex < 32){
			return NewBattleScreen.GARDEN;
		}
		
		return NewBattleScreen.GRASS;
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		
		int random = (int) (101*Math.random());
		
		//Urban
		if(_yIndex>=32){
			
			this.setBattleBG(NewBattleScreen.URBAN);
			
			if(random < 55){
				randomEnemy = _wildPokemon.get((int)(5*Math.random()));
			}
			else if(random < 70){
				randomEnemy = _wildPokemon.get(5+(int)(3*Math.random()));
			}
			else if(random < 85){
				randomEnemy = _wildPokemon.get(8+(int)(3*Math.random()));
			}
			else if(random < 100){
				randomEnemy = _wildPokemon.get(11);
			}
		}
		//Garden
		else if(_yIndex<32){
			this.setBattleBG(NewBattleScreen.GARDEN);
			
			if(random < 50){
				randomEnemy = _wildPokemon.get(12+(int)(3*Math.random()));
			}
			else if(random < 90){
				randomEnemy = _wildPokemon.get(12+3+(int)(4*Math.random()));
			}
			else if(random < 100){
				randomEnemy = _wildPokemon.get(12+7+(int)(5*Math.random()));
			}
		}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		
		this.setupBattle(ally, randomEnemy, false, _roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void createBaseRoom(){
		createBase();
		try{
			if(GameBoyScreen.finishedLoading){
				SysOut.print("route 2 loaded?!?");
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/Route2O.png"));
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Route2/Route2.png"));
			}
		} 
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
	}
	
	public void createBase(){
		this.song = M2.ROUTE_2;
		
		this.addTrainers();
		
		this.loadAllEvents();
		
		this.xConstant=276;
		this.yConstant=185;
		this._outdoors=true;
		
		this._mapX=134;
		if(this._yIndex>5){
			this._mapY=255;
		}
		else{
			this._mapY=283;
		}
		this._pkmnCentX=19;
		this._pkmnCentY=17;
		
		this.setBikeAllow(true);
		
		if(this._wildPokemon == null)
			this.addWilds();
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Brown St.-NorWest";
		this._roomNum = 18;
		
		if(_room == null){
			this.createGrid();}
	}
	
	public void createGrid(){
		this._room = new Room(53,56);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Route2.cmap"));
		for(int i = 0; i < 56; i++){
			for(int i2 = 0; i2 < 53; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(LAPRAS).defeat();
			}
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(STUCK_ITEM).defeat();
			}
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(CORNER_ITEM).defeat();
			}
			if(this.getCheckList().get(3)==1){
				this.getMovingTrainers().get(LEDGE_ITEM).defeat();
			}
			if(this.getCheckList().get(4)==1){
				this.getMovingTrainers().get(URBAN_1).defeatAndPostBattle();
			}
			if(this.getCheckList().get(5)==1){
				this.getMovingTrainers().get(URBAN_2).defeatAndPostBattle();
			}
			if(this.getCheckList().get(6)==1){
				this.getMovingTrainers().get(URBAN_3).defeatAndPostBattle();
			}
			if(this.getCheckList().get(7)==1){
				this.getMovingTrainers().get(URBAN_4).defeatAndPostBattle();
			}
			if(this.getCheckList().get(8)==1){
				this.getMovingTrainers().get(GARDEN_1).defeatAndPostBattle();
			}
			if(this.getCheckList().get(9)==1){
				this.getMovingTrainers().get(GARDEN_2).defeatAndPostBattle();
			}
			if(this.getCheckList().get(10)==1){
				this.getMovingTrainers().get(GARDEN_3).defeatAndPostBattle();
			}
			if(this.getCheckList().get(11)==1){
				this.getMovingTrainers().get(GARDEN_4).defeatAndPostBattle();
			}
			if(this.getCheckList().get(12)==1){
				this.getMovingTrainers().get(TM04).defeat();
			}
		}
		
	}

	public void scanForAllEvents(){
		SysOut.print("Size:" +this.getCheckList().size());
		//First event, is lapras defeated?
		if(this.getMovingTrainers().get(LAPRAS).isDefeated()){
			this.getCheckList().set(0,1);	
		}
		if(this.getMovingTrainers().get(STUCK_ITEM).isDefeated()){
			this.getCheckList().set(1,1);	
		}
		if(this.getMovingTrainers().get(CORNER_ITEM).isDefeated()){
			this.getCheckList().set(2,1);	
		}
		if(this.getMovingTrainers().get(LEDGE_ITEM).isDefeated()){
			this.getCheckList().set(3,1);	
		}
		if(this.getMovingTrainers().get(URBAN_1).isDefeated()){
			this.getCheckList().set(4,1);	
		}
		if(this.getMovingTrainers().get(URBAN_2).isDefeated()){
			this.getCheckList().set(5,1);	
		}
		if(this.getMovingTrainers().get(URBAN_3).isDefeated()){
			this.getCheckList().set(6,1);	
		}
		if(this.getMovingTrainers().get(URBAN_4).isDefeated()){
			this.getCheckList().set(7,1);	
		}
		if(this.getMovingTrainers().get(GARDEN_1).isDefeated()){
			this.getCheckList().set(8,1);	
		}
		if(this.getMovingTrainers().get(GARDEN_2).isDefeated()){
			this.getCheckList().set(9,1);	
		}
		if(this.getMovingTrainers().get(GARDEN_3).isDefeated()){
			this.getCheckList().set(10,1);	
		}
		if(this.getMovingTrainers().get(GARDEN_4).isDefeated()){
			this.getCheckList().set(11,1);	
		}
		if(this.getMovingTrainers().get(TM04).isDefeated()){
			this.getCheckList().set(12,1);	
		}
		
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawOptimalImage(g2, _roomO);

		this.drawBox(g2);
		this.drawAllGenerics(g2);
	}

	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		if(xInd == 51 && EAST){
			super.enterRoomSeamless(_gbs.ROUTE_3, 1, yInd-2, FACEEAST);
		}
		if(xInd == 47 && EAST){
			super.enterRoomSeamless(_gbs.PEMBROKE, 1, yInd-1+10, FACEEAST);
		}
		if(yInd == 2 && NORTH){
			super.enterRoomSeamless(_gbs.PEMBROKE, xInd-47+1, 11, FACENORTH);
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		
		//Olive Pit Main
		if((xInd == 13) && (yInd == 15)){
			super.enterRoom(_gbs.OLIVE_PIT_MAIN, 12, 17, FACENORTH);
		}
		
		//Olive Pit Exit
		if((xInd == 35) && (yInd == 1)){
			super.enterRoom(_gbs.OLIVE_PIT_EXIT, 6, 1, FACESOUTH);
		}
		
		//TWC
		if((xInd == 39) && (yInd == 48)){
			super.enterRoom(_gbs.THIRD_WORLD_CENTER, 8, 6, FACENORTH);
		}
		
		//Faunce
		if(((xInd == 47)||(xInd == 48)) && (yInd == 54)){
			super.enterRoom(_gbs.MAIN_GREEN, 27, 2, FACESOUTH);
		}
		
		//Hillel
		if((xInd == 42) && (yInd == 29)){
			super.enterRoom(_gbs.HILLEL, 5, 10, FACENORTH);
		}

		//Waterfire Path
		if(((xInd == 1) ||(xInd == 2) )&& (yInd == 54)){
			super.enterRoom(_gbs.QUIET_GREEN, 0, 16, FACEEAST);
		}
		
	}
	

	public void blackout(){
		this.blackout(this._pkmnCentX,this._pkmnCentY,FACESOUTH);
		_gbs.getPlayer().healAllActive();
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