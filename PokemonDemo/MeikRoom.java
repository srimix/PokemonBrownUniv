package PokemonDemo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class MeikRoom extends PokePanel2 {

	private boolean statVisible = false, _firstBattle=false, allyLost=false;
	private Trainer _meik, charmander, squirtle, bulbasaur;
	private final int MEIK=0;
	

	public MeikRoom(GameBoyScreen gbs) {
		super(gbs);

		this.createBaseRoom();
	}

	public MeikRoom(GameBoyScreen gbs, int xSpace, int ySpace, int xInd,
			int yInd, int direction) {
		super(gbs, xSpace, ySpace, xInd, yInd, direction);

		this.createBaseRoom();
	}

	public void createBaseRoom() {
		this.setBackground(Color.BLACK);
		this.description = "RA's Room";
		this._roomNum = 12;
		this.xConstant=123; //TODO - I changed these -1 each (Mat, Feb 28)
		this.yConstant=127;
		this._mapX=113;
		this._mapY=339;
		
		this.setBattleBG(NewBattleScreen.MEIK);
		
		this.song = M2.RA_ROOM;
		
		_textVisible = new boolean[20];

		try {
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/MeikRoom/meikroom.png"));
			
			_meik = new Trainer.Meiklejohn(null);

			this._movingTrainers = new Vector<Trainer>();

			_meik.createHome(4, 3, -2, -1,"", "");
			_meik.setStationary(true);

			Trainer rival = _gbs.getRival();
			rival.createHome(4, 10, 0, 0, "horiz", "horiz");
			rival.setPause(false);
			rival.setStationary(true);
			rival.setVanishing(true);
			rival.defeat();
			rival.setMoney(175);
			
			
			this._movingTrainers.add(_meik); //0
			
			if(_gbs.getPlayer().getAllActive().size() == 0)
				this._movingTrainers.add(rival);

			
			_meik.getDialogue().clear();
			
		//	SysOut.print("BASE");
			if(_gbs.getPlayer().getAllActive().size() == 0){
				_meik.getDialogue().add("Hey there! I have 3 new Pokemon for you to choose from.");
				_meik.getDialogue().add("Just go over to the table and choose one.");
			}
			else if(_gbs.getPlayer().getAllItems().get(Player.PACKAGE).getRemaining() == 1){
				//SysOut.print("PACKAGE CHECK");
				_meik.getDialogue().add("Oh, is that a package for me from ResLife?");
				_meik.getDialogue().add("Fantastic! This is the new orientation packet.");
				_meik.getDialogue().add("There's a Campus Map in here for you, as well as...");
				_meik.getDialogue().add("!!!");
				_meik.getDialogue().add("THIS IS A POKEDEX!!!");
				_meik.getDialogue().add("This must be the new edition for you freshmen.");
				_meik.getDialogue().add("Hey, " + _gbs.getPlayer().getPlayerName() + ", how would you like to have this one?");
				_meik.getDialogue().add(_gbs.getPlayer().getPlayerName() + " received a Pokedex!");
				_meik.getDialogue().add(_gbs.getPlayer().getPlayerName() + " received a Campus Map!");
				_meik.getDialogue().add("That Pokedex will record all the Pokemon you encounter...");
				_meik.getDialogue().add("...and that Campus Map will help you find your way around.");
				_meik.getDialogue().add("You know what " + _gbs.getPlayer().getPlayerName()+ "?");
				_meik.getDialogue().add("I think you should take on the Gym Leader Challenge.");
				_meik.getDialogue().add("There are 8 gyms spread across Brown's campus.");
				_meik.getDialogue().add("Defeat a gym leader, and you'll get a badge to mark your progress.");
				_meik.getDialogue().add("When you've gathered all 8, come back and see me.");
				_meik.getDialogue().add("And if you ever need help, just ask me where to go next.");
				_meik.getDialogue().add("Oh my goodness, is it lunch time already?");
				_meik.getDialogue().add("You should head through Wayland Arch to the Ratty Dining Hall.");
			}
			else{
				if(_gbs.getPlayer().getPokedex() == null){
					_meik.getDialogue().add("Made your choice? I would head over to Wayland Arch.");
					_meik.getDialogue().add("The ResLife office should help you get oriented.");
				}
				else if(!_gbs.getPlayer().isGymLeaderDefeated(1)){
					_meik.getDialogue().add("Oh my goodness, is it lunch time already?");
					_meik.getDialogue().add("You should head through Wayland Arch to the Ratty Dining Hall.");
				}
				else if(!_gbs.getPlayer().isGymLeaderDefeated(2)){
					_meik.getDialogue().add("You should shop a lot of classes to find out what you want to take.");
					_meik.getDialogue().add("Brown is known for its Neuroscience department.");
					_meik.getDialogue().add("Why don't you head North to BioMed, and see if a professor...");
					_meik.getDialogue().add("...will let you sit in on his class?");
				}
				else if(!_gbs.getPlayer().isGymLeaderDefeated(3)){
					_meik.getDialogue().add("A popular class is CS15.");
					_meik.getDialogue().add("You learn Java and get to make cool animations and games.");
					_meik.getDialogue().add("Head East to the CIT and check it out!");
				}
				else if(!_gbs.getPlayer().isGymLeaderDefeated(4)){
					_meik.getDialogue().add("Another really popular class is ENGN 90, taught by...");
					_meik.getDialogue().add("...Prof. Hazeltine, who's been at Brown for over 50 years!");
					_meik.getDialogue().add("Although it's a business class, it's in the Engineering school,");
					_meik.getDialogue().add("so you should head further East to Barus and Holly.");
				}
				else if(!_gbs.getPlayer().isGymLeaderDefeated(5)){
					_meik.getDialogue().add("Have you shopped Great Pokemon and Empires yet?");
					_meik.getDialogue().add("If you haven't, you should totally head South on Thayer.");
					_meik.getDialogue().add("Just look for the Watson Institute.");
				}
				else if(!_gbs.getPlayer().isGymLeaderDefeated(6)){
					_meik.getDialogue().add("Wow, " + _gbs.getPlayer().getPlayerName() + ", you already have 5 badges?!?");
					_meik.getDialogue().add("I think you're ready for one of Brown's toughest classes...");
					_meik.getDialogue().add("...Organic Chemistry!!!");
					_meik.getDialogue().add("Head back over near the Science Quad and find your way into...");
					_meik.getDialogue().add("...MacMillan hall. That's where Prof. Suggs will be.");
				}
				else if(!_gbs.getPlayer().isGymLeaderDefeated(7)){
					_meik.getDialogue().add("Oh man, I'm starving. It seems like lunch was forever ago!");
					_meik.getDialogue().add("Good thing Jo's is open.");
					_meik.getDialogue().add("What? You don't know about Jo's?!?");
					_meik.getDialogue().add("It's a late-night dining hall right by New Dorm.");
					_meik.getDialogue().add("Go check it out, and get a Spicy-with.");
				}
				else if(!_gbs.getPlayer().isGymLeaderDefeated(8)){
					_meik.getDialogue().add("Oh, " + _gbs.getPlayer().getPlayerName() + ", thank goodness you're here!");
					_meik.getDialogue().add("I just received TERRIBLE news.");
					_meik.getDialogue().add("President Ruth Simmons has been kidnapped by the mafia!");
					_meik.getDialogue().add("As fast as you can, head to Via-Via (their HQ), and save her!");
				}
				else{
					_meik.getDialogue().add("Well, " + _gbs.getPlayer().getPlayerName() + ", I must admit I'm impressed.");
					_meik.getDialogue().add("You've successfully collected all of the badges at Brown.");
					_meik.getDialogue().add("There's only one challenge that awaits you.");
					_meik.getDialogue().add("The Elite Four!");
					_meik.getDialogue().add("These are four of the strongest Pokemon trainers Brown has.");
					_meik.getDialogue().add("If you think you can handle the challenge, head back over to...");
					_meik.getDialogue().add("...the Main Green, and enter Slater dormitory, next to University Hall.");
					_meik.getDialogue().add("Good luck! I know you can do it!");
				}
			}
			
			if(_gbs.getPlayer().getAllActive().size() == 0){
				SysOut.print("NEW POKEMONS!");
				charmander = new Trainer.StarterPokeBall(new Pokemon.Charmander(), _gbs);
				charmander.createHome(1, 2, 2, 5,"", "");
				charmander.setStationary(true);
				charmander.setVanishing(true);
			
				squirtle = new Trainer.StarterPokeBall(new Pokemon.Squirtle(), _gbs);
				squirtle.createHome(2, 2, 2, 5,"", "");
				squirtle.setStationary(true);
				squirtle.setVanishing(true);
			
				bulbasaur = new Trainer.StarterPokeBall(new Pokemon.Bulbasaur(), _gbs);
				bulbasaur.createHome(3, 2, 2, 5,"", "");
				bulbasaur.setStationary(true);
				bulbasaur.setVanishing(true);
		
				
				this._movingTrainers.add(charmander);
				this._movingTrainers.add(squirtle);
				this._movingTrainers.add(bulbasaur);
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		this.createGrid();
	}

	public void B_Button() {
		SysOut.print("X: " + this._xIndex + ", Y: " + this._yIndex
				+ ", Xspace: " + this._xSpace + ", Yspace: " + this._ySpace);
		if (!_textVisible[5] && !_textVisible[6] && !_textVisible[7]) {
			super.B_Button();
			SysOut.print("B?");
		} else {
			_textVisible[5] = false;
			_textVisible[6] = false;
			_textVisible[7] = false;
			statVisible = false;
			_busy = false;
			completionCheck = false;
		}
		this.repaint();
	}
	
	public void Start(){
		if(_gbs.getRival().getBelt() != null && _gbs.getPlayer().getAllActive() != null && _gbs.getRival().getBelt().size() == 1 && _gbs.getPlayer().getAllActive().size() == 1 && !_firstBattle){
			
		}
		else
			super.Start();
	}

	public void A_Button() {
		if (!_menuVisible && !this.textTimer.isRunning()) {

			if(this._interruptedTrainer==MEIK && _gbs.getPlayer().getAllItems().get(Player.PACKAGE).getRemaining() == 1 && (_NPCpage==6 || _NPCpage==7)){
				M2.playFX(M2.ITEM_RECEIVED);
			}
			
			if((this._yIndex == 7 && this._xIndex == 6 && NORTH) || (this._yIndex == 6 && this._xIndex == 5 && EAST)){
				_textVisible[19] = !_textVisible[19];
				_busy = !_busy;
			}
			
			if (_textVisible[10]) {
				_textVisible[10] = false;
				_textVisible[8] = false;
				
				if(_gbs.getPlayer().hasPokemon(1)){
					_gbs.getRival().addDest(1,3);
					_gbs.getRival().setFirstDest();
				}
				else if(_gbs.getPlayer().hasPokemon(4)){
					_gbs.getRival().addDest(2,3);
					_gbs.getRival().setFirstDest();
				}
				else if(_gbs.getPlayer().hasPokemon(7)){
					_gbs.getRival().addDest(3,3);
					_gbs.getRival().setFirstDest();
				}
				_oneShot=true;
				this._interruptedTrainer=1;
				_gbs.getRival().setDirectionAndImage(2);
				_approachTimer.start();
			}
			
			else if(_gbs.getRival().getYIndex()==3 && _gbs.getRival().getBelt()==null){
				_gbs.getRival().setBelt(new Vector<Pokemon>());
				SysOut.print("Rival statement called when choosing");
				switch(_gbs.getRival().getXIndex()){
				case 1:
					SysOut.print("charmander?");
					Pokemon Charmander = new Pokemon.Charmander();
					Charmander.setWildLevel(5);
					Charmander.getAttacks().add(Attack.getAttackByNum(Charmander.getAttacks().get(1).getAttackNum()));
					//Charmander.getAttacks().add(Attack.getAttackByNum(Charmander.getAttacks().get(1).getAttackNum()));
					_gbs.getRival().getBelt().add(Charmander);
					charmander.defeat();
					charmander = null;
					_gbs.setRivalStarterNum(4);
					squirtle.setVanishing(false);
					squirtle.getDialogue().clear();
					squirtle.defeat();
					break; //charmander
				case 2:
					Pokemon Squirtle = new Pokemon.Squirtle();
					Squirtle.setWildLevel(5);
					Squirtle.getAttacks().add(Attack.getAttackByNum(Squirtle.getAttacks().get(1).getAttackNum()));
					//Squirtle.getAttacks().add(Attack.getAttackByNum(Squirtle.getAttacks().get(1).getAttackNum()));
					_gbs.getRival().getBelt().add(Squirtle);
					squirtle.defeat();
					squirtle = null;
					_gbs.setRivalStarterNum(7);
					bulbasaur.setVanishing(false);
					bulbasaur.getDialogue().clear();;
					bulbasaur.defeat();
					break; //squirtle
				case 3:
					Pokemon Bulbasaur = new Pokemon.Bulbasaur();
					Bulbasaur.setWildLevel(5);
					Bulbasaur.getAttacks().add(Attack.getAttackByNum(Bulbasaur.getAttacks().get(1).getAttackNum()));
					//Bulbasaur.getAttacks().add(Attack.getAttackByNum(Bulbasaur.getAttacks().get(1).getAttackNum()));
					_gbs.getRival().getBelt().add(Bulbasaur);
					bulbasaur.defeat();
					bulbasaur = null;
					_gbs.setRivalStarterNum(1);
					charmander.setVanishing(false);
					charmander.getDialogue().clear();
					charmander.defeat();
					break; //bulbasaur
				}
				M2.interrupt();
				
				_gbs.getRival().setVanishing(false);
				_gbs.getRival().defeat();
				_gbs.getRival().clearDestinations();
				_approached=false;
				_returnTrip=false;
				_oneShot=false;				
			}
			
			else if(_gbs.getRival().getYIndex()==3 && _gbs.getRival().getBelt()!=null && this.facingNPC()){
				SysOut.print("Chit chat");
				_gbs.getRival().getDialogue().clear();
				_gbs.getRival().getDialogue().add(_gbs.getPlayer().getAllActive().get(0).getName()+"s are so mainstream...");
				_gbs.getRival().getDialogue().add("I was into " + _gbs.getRival().getBelt().get(0).getName()+"s before they were cool.");
				this._movingTrainers.get(0).getDialogue().clear();
				this._movingTrainers.get(0).getDialogue().add("Made your choice? I would head over to Wayland Arch.");
				this._movingTrainers.get(0).getDialogue().add("The ResLife office should help you get oriented.");
			}

			else if (_textVisible[9]) {
				_textVisible[9] = false;
				_textVisible[10] = true;
			}
			
			else if(!_gbs.getRival().isDefeated() && _gbs.getRival().getYIndex()==4 && _gbs.getRival().getXIndex()==4){
				_textVisible[9]=true;
				_gbs.getRival().setDirectionAndImage(FACEEAST);

			}

			else if (_textVisible[8]) {
				_textVisible[8] = false;
				_gbs.getPlayer().faceDown();
				_gbs.getRival().unDefeat();
				//_gbs.getRival().setVanishing(true);
				_gbs.getRival().addDest(4,4);
				_gbs.getRival().setFirstDest();
				_oneShot=true;
				this._interruptedTrainer=1;
				_gbs.getRival().setDirectionAndImage(2);
				_approachTimer.start();
				_gbs.getRival().setVanishing(false);
			//	rivalTimer.start();
			//	rivalTimer.start();
			}

			else if (_textVisible[4]) {
				SysOut.print("6");
				_textVisible[4] = false;
				_textVisible[8]=true;
				if(M2.getCurrentSong()!=M2.RIVAL_MUSIC && M2.getCurrentFX() != M2.RIVAL_MUSIC){
					M2.playFX(M2.RIVAL_MUSIC);
				}
			}

			else 
			if (_yIndex == 3 && NORTH && _gbs.getPlayer()._activePokemon.size() == 0) {
				if (!_textVisible[0] && !_textVisible[1] && !_textVisible[2]
						&& !_textVisible[5] && !_textVisible[6]
						&& !_textVisible[7]) {
					switch (_xIndex) {
					case 1:
						_textVisible[0] = true;
						break;
					case 2:
						_textVisible[1] = true;
						break;
					case 3:
						_textVisible[2] = true;
						break;
					}
					this._busy = true;
					statVisible = true;
				} else {
					if ((_textVisible[0] || _textVisible[1] || _textVisible[2]) && _gbs.getPlayer().getAllActive().size() == 0) {
						if (_textVisible[0]) {
							_textVisible[0] = false;
							_textVisible[5] = true;
						}
						if (_textVisible[1]) {
							_textVisible[1] = false;
							_textVisible[6] = true;
						}
						if (_textVisible[2]) {
							_textVisible[2] = false;
							_textVisible[7] = true;
						}
					} else {
						if(_gbs.getPlayer().getAllActive().size() == 0){
							statVisible = false;
						
							if (_textVisible[5]) {
								_textVisible[5] = false;
								if(_gbs.getPlayer().getAllActive().size() == 0){
									charmander.getGift();
									charmander = null;
								}
							}
							if (_textVisible[6]) {
								_textVisible[6] = false;
								if(_gbs.getPlayer().getAllActive().size() == 0){
									squirtle.getGift();
									squirtle = null;
								}
							}
							if (_textVisible[7]) {
								_textVisible[7] = false;
								if(_gbs.getPlayer().getAllActive().size() == 0){
									bulbasaur.getGift();
									bulbasaur = null;
								}
							}
							if(_gbs.getPlayer().getAllActive().size() != 0){
								_textVisible[4] = true;
							}
							///////////
							//Set the previous pkmn center as your room. This is CRUCIAL. 
							_gbs.getPlayer().setPkmnCenter(_gbs.KEENEY_ROOM);
							//////////
							
							M2.playFX(M2.RECEIVED_POKEMON);
							SysOut.print("RECEIVED!!!");
						//	_gbs.playSound(M.RECEIVED_POKEMON);
						}
					}

				}
			}

			else if (_textVisible[12]) {
				_textVisible[12] = false;
				_gbs.getCurrentPanel().trainer = 1;
				_gbs.getCurrentPanel()._attack.start();
				_gbs.getRival().getDialogue().clear();	
				_gbs.getRival().setDefeatDialogue("What?!? How could I have picked the weaker Pokemon?");
				_approachTimer.stop();
			}
			
			else if (_textVisible[11]) {
				_textVisible[11] = false;
				if(M2.getCurrentSong()!=M2.RIVAL_MUSIC && M2.getCurrentFX() != M2.RIVAL_MUSIC){
					M2.playFX(M2.RIVAL_MUSIC);
				}
				_gbs.getRival().addDest(this._xIndex,this._yIndex-1);
				_gbs.getRival().setFirstDest();
				this._interruptedTrainer=1;
				_gbs.getRival().setAvoidMethod("avoid");
				_gbs.getRival().setVanishing(false);
				_gbs.getRival().unDefeat();
				_oneShot=true;
				_gbs.getRival().setDirectionAndImage(FACENORTH);
				_gbs.getRival().getDialogue().clear();
				this._approachTimer.start();
				PokePanel2.setFirstBattle(true);
				//_busy=false;
			}
			
			else if((_gbs.getRival().getXIndex()== 3 || _gbs.getRival().getXIndex()==4) && _gbs.getRival().getYIndex()== this._yIndex-1 && this._yIndex>5 && !_firstBattle ){
				//Requiring my yIndex>5 fixes bed bug.
				_textVisible[12] = true;
				_firstBattle=true;
			}
			

			else if (_textVisible[13]) {

				_textVisible[13] = false;
				if(_xIndex==4 && _yIndex==9){
					_gbs.getRival().setHomeIndices(3,10);
				}
				
				_gbs.getRival().sendHome();
				_gbs.getRival().setFirstDest();
				
				this._interruptedTrainer=1;
				_gbs.getRival().setAvoidMethod("avoid");
				_gbs.getRival().setVanishing(true);
				_returnTrip=true;
				_gbs.getRival().setDirectionAndImage(FACENORTH);
				_gbs.getRival().getDialogue().clear();
				this._approachTimer.start();
				//rivalExitTimer.start();
			}

			else if (_textVisible[14]) {
				_textVisible[14] = false;
				_gbs.getPlayer().clearDestinations();
				_gbs.getPlayer().addDest(this._xIndex,this._yIndex-3);
				_gbs.getPlayer().setIgnoring(false);
				_gbs.getPlayer().setAvoidMethod("vert");
				this._approachTimer.start();
				_busy = false;
			}
			
			else if((_gbs.getPlayer().getPokedex() == null || !_gbs.getPlayer().hasTownMap()) && _gbs.getPlayer().getItem(Player.PACKAGE).getRemaining() == 1 && this._NPCpage == 6){
				_busy = true;
				SysOut.print("CONVERSATION CONTINUED");
				
				//_textVisible[15] = true;
				if(_gbs.getPlayer().getPokedex() == null){
					_gbs.getPlayer().givePokedex();
					_gbs.getPlayer().getPokedex().initialize();
				}	
				
				if(!_gbs.getPlayer().hasTownMap())
					_gbs.getPlayer().giveTownMap();
				
				if(_gbs.getPlayer().getItem(Player.PACKAGE).getRemaining() > 0)
					_gbs.getPlayer().getItem(Player.PACKAGE).setRemaining(0);
				//TODO: MUSIC
			}
			else if(this._NPCpage == 18){
				this.createBaseRoom();
				SysOut.print("NPC RESET");
			}
			if (!_busy) {
				this.completionCheck = false;
			}

			// Choosing a pokemon
		}
	
			super.A_Button();
		
		this.repaint();
	}

	public void Up(){
		if(this.getPlayerDirection() == this.FACENORTH && this._yIndex ==7 && (this._xIndex>2) && (this._xIndex<6)&& _gbs.getPlayer().getAllActive().size() == 0 && !this._menuVisible){
			_gbs.getPlayer().clearDestinations();
			_gbs.getPlayer().addDest(4,4);
			_gbs.getPlayer().setAvoidMethod("horiz");
			SysOut.print("Up");
			this._interruptedTrainer = 0;
			this._approachTimer.start();
		}
		else{
			super.Up();
		}
	}
	
	public void Down() {
		//Rival Challenge
		if (this._yIndex + 1 == 10 && (this._xIndex == 3 || this._xIndex == 4)
				&& _gbs.getRival().getCurrentImage() != null
				&& _gbs.getPlayer().getAllActive().size() == 1 && _gbs.getRival().getYIndex()<5 && !_firstBattle ) {
			_textVisible[11]=true;
			//_busy=true;
			_gbs.getRival().setDirectionAndImage(0);
			_gbs.getPlayer().faceUp();
			this.repaint();
			
		} 
		else {
			//Can't leave yet!
			if (this._yIndex + 1 == 10
					&& (this._xIndex == 3 || this._xIndex == 4)
					&& _gbs.getPlayer().getAllActive().size() == 0 && !this._menuVisible) {
				_busy = true;
				_meik.faceDown();
				_textVisible[14] = true;
				this.repaint();
			}
			super.Down();
		}
	}

	public void createGrid() {
		this._room = new Room(8, 11);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/MeikRoom.cmap"));
		for (int i = 0; i < 11; i++) {
			for (int i2 = 0; i2 < 8; i2++) {
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);

		if (_textVisible[0]) {
			this.drawText(g2, "Charmander, the fire-lizard Pokemon.");
		}
		if (_textVisible[5]) {
			this.drawText(g2, "Do you want Charmander?", "A=Yes    B=No");
		}

		if (_textVisible[1]) {
			this.drawText(g2, "Squirtle, the water Pokemon.");
		}
		if (_textVisible[6]) {
			this.drawText(g2, "Do you want Squirtle?", "A=Yes    B=No");
		}

		if (_textVisible[2]) {
			this.drawText(g2, "Bulbasaur, the plant Pokemon.");
		}
		if (_textVisible[7]) {
			this.drawText(g2, "Do you want Bulbasaur?", "A=Yes    B=No");
		}

		if (_textVisible[4]) {
			this.drawText(g2, _gbs.getPlayer().getPlayerName() + " received a "
					+ _gbs.getPlayer()._activePokemon.get(0).getName() + "!");
		}

		if (_textVisible[8]) {
			this.drawText(g2, "DAMN! I overslept!!!");
		}

		if (_textVisible[9]) {
			this.drawText(g2, "What?!? You already chose your",
					"first Pokemon?");
		}

		if (_textVisible[10]) {
			this.drawText(g2, "Whatever, I'll just (ironically)",
					"choose the one better than yours.");
		}

		if (_textVisible[11]) {
			this.drawText(g2, "Where do you think you're going?", "");
		}

		if (_textVisible[12]) {
			this.drawText(g2, "Let's see who picked the", "stronger Pokemon!");
		}

		if (_textVisible[13] && allyLost) {
			this.drawText(g2, "Oh, you mad bro?",
					"Better luck next time...");
		}
		if (_textVisible[13] && !allyLost) {
			this.drawText(g2, "You got lucky this time, but",
					"I'll make my Pokemon tougher!");
		}

		if (_textVisible[14]) {
			this.drawText(g2, "Wait! You can't leave without",
					"choosing a Pokemon!");
		}
		
		if(_textVisible[19]){
			this.drawText(g2, "A GameCube. Wonder if he has", "Super Smash Bros?");
		}

		this.drawPlayer(g2);
		this.drawBox(g2);

		// Text Visible

		this.drawAllTrainers(g2, this.xConstant, this.yConstant,
				_movingTrainers);
		this.drawAllGenerics(g2);

		if (statVisible) {
			Rectangle2D.Double statBG = new Rectangle2D.Double();
			statBG.height = 200;
			statBG.width = 200;
			statBG.x = 90;
			statBG.y = 60;

			Rectangle2D.Double statOut = new Rectangle2D.Double();
			statOut.height = 198;
			statOut.width = 198;
			statOut.x = 91;
			statOut.y = 61;

			g2.setColor(Color.WHITE);
			g2.fill(statBG);

			g2.setColor(Color.BLACK);
			g2.draw(statOut);

			Pokemon statFocus = null;
			switch (_xIndex) {
			case 1:
				Pokemon Charmander = new Pokemon.Charmander();
				Charmander.setWildLevel(5);
				statFocus = Charmander;
				break;
			case 2:
				Pokemon Squirtle = new Pokemon.Squirtle();
				Squirtle.setWildLevel(5);
				statFocus = Squirtle;
				break;
			case 3:
				Pokemon Bulbasaur = new Pokemon.Bulbasaur();
				Bulbasaur.setWildLevel(5);
				statFocus = Bulbasaur;
				break;
			}

			g2.drawString(statFocus.getName(), 95, 75);
			g2.drawString("LVL: " + statFocus.getLevel(), 95, 95);

			g2.drawString(
					"EXP: "
							+ statFocus.getExp()
							+ "/"
							+ (NewBattleScreen.getToNextLevel(statFocus) + statFocus
									.getExp()), 95, 115);

			g2.drawImage(statFocus.getFrontImage(), null, 190, 60);

			RoundRectangle2D.Double t1BG = new RoundRectangle2D.Double();
			t1BG.height = 20;
			t1BG.width = 60;
			t1BG.x = 94;
			t1BG.y = 145;
			t1BG.archeight = 10;
			t1BG.arcwidth = 10;

			if (statFocus.getType2() == Types.NONE) {
				g2.drawString("Type:", 95, 135);
				g2.setColor(statFocus.getType1().getColor());
				g2.fill(t1BG);
				g2.setFont(new Font("Courier New", Font.PLAIN, 12));

				String s = statFocus.getType1().toString();

				float sw = (float) g2.getFont().getStringBounds(s,
						g2.getFontRenderContext()).getWidth();
				LineMetrics lm = g2.getFont().getLineMetrics(s,
						g2.getFontRenderContext());
				float sh = lm.getAscent() + lm.getDescent();

				float sx = (float) (t1BG.x + (t1BG.width - sw) / 2);
				float sy = (float) (t1BG.y + (t1BG.height + sh) / 2)
						- lm.getDescent();

				g2.setColor(Color.DARK_GRAY);
				g2.drawString(s, sx + 1, sy + 1);

				g2.setColor(Color.WHITE);
				g2.drawString(s, sx, sy);
			} else {
				RoundRectangle2D.Double t2BG = new RoundRectangle2D.Double();
				t2BG.height = 20;
				t2BG.width = 60;
				t2BG.x = 156;
				t2BG.y = 145;
				t2BG.archeight = 10;
				t2BG.arcwidth = 10;

				g2.drawString("Types: ", 95, 135);
				g2.setColor(statFocus.getType1().getColor());
				g2.fill(t1BG);
				g2.setColor(statFocus.getType2().getColor());
				g2.fill(t2BG);
				g2.setFont(new Font("Courier New", Font.PLAIN, 12));

				String s1 = statFocus.getType1().toString();
				String s2 = statFocus.getType2().toString();

				float sw1 = (float) g2.getFont().getStringBounds(s1,
						g2.getFontRenderContext()).getWidth();
				LineMetrics lm1 = g2.getFont().getLineMetrics(s1,
						g2.getFontRenderContext());
				float sh1 = lm1.getAscent() + lm1.getDescent();

				float sx1 = (float) (t1BG.x + (t1BG.width - sw1) / 2);
				float sy1 = (float) (t1BG.y + (t1BG.height + sh1) / 2)
						- lm1.getDescent();

				float sw2 = (float) g2.getFont().getStringBounds(s2,
						g2.getFontRenderContext()).getWidth();
				LineMetrics lm2 = g2.getFont().getLineMetrics(s2,
						g2.getFontRenderContext());
				float sh2 = lm2.getAscent() + lm2.getDescent();

				float sx2 = (float) (t2BG.x + (t2BG.width - sw2) / 2);
				float sy2 = (float) (t2BG.y + (t2BG.height + sh2) / 2)
						- lm2.getDescent();

				g2.setColor(Color.DARK_GRAY);
				g2.drawString(s1, sx1 + 1, sy1 + 1);
				g2.drawString(s2, sx2 + 1, sy2 + 1);

				g2.setColor(Color.WHITE);
				g2.drawString(s1, sx1, sy1);
				g2.drawString(s2, sx2, sy2);
			}
			g2.setFont(_gbs.getFont());
			g2.setColor(Color.BLACK);

			g2.drawString("------------------", 95, 178);

			int count = 0;
			for (int i = 0; i < statFocus.getAttacks().size(); i++) {
				g2.drawString(statFocus.getAttacks().get(i).getName(), 95,
						193 + (20 * i));
				g2.drawString(statFocus.getAttacks().get(i).getCurrentPP()
						+ "/" + statFocus.getAttacks().get(i).getMaxPP(), 225,
						193 + (20 * i));
				count++;
			}
			for (int i = count; i < 4; i++) {
				g2.drawString("---", 95, 193 + (20 * i));
			}
		}

	}

	public void enterRoom(int xInd, int yInd) {
//		super.enterRoom(xInd, yInd);
//
//		_gbs.setCurrentPanel(3);
//
//		PokePanel2 current = _gbs.getCurrentPanel();
//
//		current.createBaseRoom();
//
//		current.setLocation(-148, 28);
//		current.setIndices(12, 1);
//		current._darkness = 250;
//		current._fadeUp.start();
//
//		_gbs.playSong(M.KEENEY);
//
//		current.resetNTimer();
//		_gbs.startNTimer();
//
//		current.Down();
//		current.Down();
//
//		// Reset RA.
//		_meik.resetDirection();
		super.enterRoom(_gbs.KEENEY_HALL, 12, 1, FACESOUTH);
	}

	public void blackout(){
		SysOut.print("BLACK OUT IN MEIK ROOM");
		_gbs.getPlayer().getAllActive().get(0).heal();
		allyLost=true;
		_textVisible[13] = true;
		_approachTimer.stop();
		M2.playBG(M2.RA_ROOM);
	}
	
	public void afterBattle() {
		_textVisible[13] = true;
		_gbs.getPlayer().getAllActive().get(0).heal();
		_approachTimer.stop();
	}
}
