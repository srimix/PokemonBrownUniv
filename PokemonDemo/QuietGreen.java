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
public class QuietGreen extends PokePanel2 {
	private BufferedImage _roomO, _roomFence, _openGate, _sideGate, _closedGate;
	private boolean sideGateOpen;
	public static boolean champion=false;
	private final int VW1=11, VW2=12, VW3=13, VW4=14, VW5=15, UNIV1=7, UNIV2=8, SLATER1=9, SLATER2=10, TOWER=1, RIVAL =16, ITEM1=17, ITEM2=18, ITEM3=19, ITEM4=20;
	
	public QuietGreen(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(5);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public QuietGreen(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(5);
		
		this.createBaseRoom();
		
	}
	
	public void addWilds(){
		this._wildPokemon = new Vector<Pokemon>();
		for(int i = 3; i <= 7; i++){
			this._wildPokemon.add(new Pokemon.Farfetchd().setWildLevel(i));
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
		
			try{
				Trainer book=new Trainer.BookReadBoy(null);
				book.createHome(14, 9);
				book.setDirectionAndImage(FACENORTH);
				book.getDialogue().add("I love reading on the green under a tree.");
				book.getDialogue().add("This \"Ulysses\" book is really giving me a headache though.");
				this._movingTrainers.add(book); //0
				
				Trainer tower = new Trainer.Text();
				tower.createHome(4, 11);
				tower.getDialogue().add("There's a logo of a scary bear with a ring around it over the door.");
				tower.getDialogue().add("It looks dark and dangerous inside...");
				tower.getDialogue().add("Probably shouldn't enter without a large and menacing Pokemon.");
								
				if(_gbs.getPlayer().getPokedex()!=null && _gbs.getPlayer().getPokedex().hasCaught(217)){
					tower.setVanishing(true);
					tower.defeat();
				}
				this._movingTrainers.add(tower); //1
				
				Trainer bagpipe = new Trainer.Lorelei(null);
				bagpipe.createHome(27, 9, 0, 0, "wander", "wander");
				bagpipe.addDest(33, 11);
				bagpipe.addDest(27, 9);
				bagpipe.setStationary(false);
				bagpipe.getDialogue().add("Hannah: Wanna play frisbee? How about football?");
				bagpipe.getDialogue().add("P.S. - Oregon FTW");
				//bagpipe.getDialogue().add("");
				bagpipe.setVanishing(true);
				if(champion)
					bagpipe.defeat(); //She walks in front of you when you exit Slater as Champion,
									  // and the walking algorithm doesn't check.
					this._movingTrainers.add(bagpipe); //2				

				Trainer elisa = new Trainer.BlackHair(null);
				elisa.createHome(16, 29);
				elisa.getDialogue().add("Out of my way, I have to bring these cookies to Prof. Neumann!!!");
				_movingTrainers.add(elisa);
					
				Trainer hill= new Trainer.Backpacker(null);
				hill.setDirectionAndImage(FACESOUTH);
				hill.createHome(16, 26);
				hill.getDialogue().add("I'm so close. But I'm too tired to walk up the rest of the hill...");
				this._movingTrainers.add(hill); //3
				
				Trainer rock = new Trainer.ChubbyGuy(null);
				rock.createHome(23, 19);
				rock.setDirectionAndImage(FACENORTH);
				rock.getDialogue().add("Hm? Oh, this is the Rock.");
				rock.getDialogue().add("I really can't comprehend why so many students like studying here.");
				rock.getDialogue().add("It can't even be comfortable...");
				this._movingTrainers.add(rock); //4
				
				
				
				Trainer niceDay = new Trainer.WhiteBeltBike(null);
				niceDay.createHome(23, 7, 0, 0, "drunk", "drunk");
				niceDay.addDest(17, 10);
				niceDay.addDest(23, 7);
				niceDay.setFirstDest();
				niceDay.setStationary(false);
				niceDay.getDialogue().add("It's so nice outside! I hope Spring Weekend is warm too.");
				this._movingTrainers.add(niceDay); //5
				
				Trainer legendaries= new Trainer.BlueGirl(null);
				legendaries.setDirectionAndImage(FACEEAST);
				legendaries.createHome(34, 5);
				legendaries.getDialogue().add("There's an old Brown legend that Bruno awakens when you catch...");
				legendaries.getDialogue().add("...the other five legendary Pokemon. But I don't think he'll...");
				legendaries.getDialogue().add("...show up if you defeat them instead.");
				this._movingTrainers.add(legendaries); //6
				
				
				
				
			
				//univ and slater
			Trainer univ1 = new Trainer.Text();
			univ1.createHome(16, 4);
			univ1.getDialogue().add("It seems to be locked from this side.");
			this._movingTrainers.add(univ1); //7
			
			Trainer univ2 = new Trainer.Text();
			univ2.createHome(22, 4);
			univ2.getDialogue().add("It seems to be locked from this side.");
			this._movingTrainers.add(univ2);//8
			
			
			
			Trainer slater1 = new Trainer.Text();
			slater1.createHome(28, 3);
			slater1.getDialogue().add("It seems to be locked from this side.");
			this._movingTrainers.add(slater1);//9
			
			Trainer slater2 = new Trainer.Text();
			slater2.createHome(31, 3);
			slater2.getDialogue().add("It seems to be locked from this side.");
			this._movingTrainers.add(slater2);//10
			
			
			//Left and Right gates dependent on 5th gym 
			Trainer VW1 = new Trainer.Text();
			VW1.createHome(16, 13);
			VW1.getDialogue().add("It seems to be locked from this side.");
			
			Trainer VW2 = new Trainer.Text();
			VW2.createHome(22, 13);
			VW2.getDialogue().add("It seems to be locked from this side.");
			if(sideGateOpen){
				VW1.setVanishing(true);
				VW1.defeat();
				VW2.setVanishing(true);
				VW2.defeat();
			}
	
			this._movingTrainers.add(VW1);//11
			this._movingTrainers.add(VW2);//12
			
			
			//Center Gates dependent on 8th gym.
			Trainer VW3 = new Trainer.Text();
			VW3.createHome(18, 13);
			VW3.getDialogue().add("It seems to be locked from this side.");
			
			Trainer VW4 = new Trainer.Text();
			VW4.createHome(19, 13);
			VW4.getDialogue().add("It seems to be locked from this side.");
			
			Trainer VW5 = new Trainer.Text();
			VW5.createHome(20, 13);
			VW5.getDialogue().add("It seems to be locked from this side.");
			
			if(champion){
				VW3.setVanishing(true);
				VW3.defeat();
				VW4.setVanishing(true);
				VW4.defeat();
				VW5.setVanishing(true);
				VW5.defeat();
			}
	
			this._movingTrainers.add(VW3);//13
			this._movingTrainers.add(VW4);//14
			this._movingTrainers.add(VW5);//15

			
			///You can go downtown from this direction starting after the 5th gym leader.
			///But you can't fight mew until >5 gyms are defeated, so this is ok.

			Vector<Pokemon> rivalBelt = new Vector<Pokemon>();
			Pokemon r1 = new Pokemon.Pidgey().setWildLevel(9);
			rivalBelt.add(r1);
			int rivStarter = _gbs.getRivalStarterNum();
//			if(_gbs.getRival() != null && _gbs.getRival().getBelt() != null)
//				rivStarter = _gbs.getRival().getBelt().get(0).getDexNum();
			if(rivStarter == 1){
				Pokemon r4 = new Pokemon.Bulbasaur().setWildLevel(8);
				rivalBelt.add(r4);
			}
			else if(rivStarter == 4){
				Pokemon r4 = new Pokemon.Charmander().setWildLevel(8);
				rivalBelt.add(r4);
			}
			else{
				Pokemon r4 = new Pokemon.Squirtle().setWildLevel(8);
				rivalBelt.add(r4);
			}
			
			//rival at 13 12, facing up, los 11
			Trainer rival = new Trainer.Rival(rivalBelt);
			rival.createHome(26, 5, 0, 0, "avoid","avoid");
			rival.getDialogue().clear();
			rival.getDialogue().add(_gbs.getPlayer().getPlayerName() + "! I was just checking out the gates.");
			rival.getDialogue().add("Why don't we show off our Pokemon to the students hanging out here?");
			rival.getDialogue().add("And let me win, because I think that cute girl is watching.");
			rival.setDefeatDialogue("I think this is a breach of our roommate contract.");
			rival.setStationary(true);
			rival.setDirectionAndImage(FACESOUTH);
			rival.setLOS(11);
			rival.setMoney(280);
			rival.setName(_gbs.getRival().getTrueName());
			if(_gbs.getPlayer().isGymLeaderDefeated(2)){
				rival.setVanishing(true);
				rival.defeat();
			}
			this.getMovingTrainers().add(rival); //16
			
			Trainer item1 = new Trainer.ItemObject(new Item.Potion());
			item1.createHome(24, 25);
			Trainer item2 = new Trainer.ItemObject(new Item.Potion());
			item2.createHome(37, 27);
			Trainer item3 = new Trainer.ItemObject(new Item.Potion());
			item3.createHome(8, 29);
			Trainer item4 = new Trainer.ItemObject(new Item.Potion());
			item4.createHome(36, 4);
			this.getMovingTrainers().add(item1); //17
			this.getMovingTrainers().add(item2); //18
			this.getMovingTrainers().add(item3); //19
			this.getMovingTrainers().add(item4); //20
			
			}
			catch(IOException ioe){}
			
	}	

	
	public void afterBattle(){
		super.afterBattle();
		if(_interruptedTrainer==RIVAL){
			SysOut.print("AfterBattle called");
			this.scanForAllEvents();
			Trainer rival= this.getMovingTrainers().get(RIVAL);
			rival.unDefeat();
			rival.setVanishing(true);
			rival.setHomeIndices(25, 1);
			_returnTrip=true;
			this._approachTimer.start();
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(RIVAL).setVanishing(true);
				this.getMovingTrainers().get(RIVAL).defeat();
			}
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(ITEM1).defeat();
			}	
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(ITEM2).defeat();
			}	
			if(this.getCheckList().get(3)==1){
				this.getMovingTrainers().get(ITEM3).defeat();
			}	
			if(this.getCheckList().get(4)==1){
				this.getMovingTrainers().get(ITEM4).defeat();
			}	
		}
	}

	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(RIVAL).isDefeated()){
			this.getCheckList().set(0,1);
		}
		if(this.getMovingTrainers().get(ITEM1).isDefeated()){
			this.getCheckList().set(1,1);
		}
		if(this.getMovingTrainers().get(ITEM2).isDefeated()){
			this.getCheckList().set(2,1);
		}
		if(this.getMovingTrainers().get(ITEM3).isDefeated()){
			this.getCheckList().set(3,1);
		}
		if(this.getMovingTrainers().get(ITEM4).isDefeated()){
			this.getCheckList().set(4,1);
		}
		
	}
	
	
	public void createBaseRoom(){
		//Has to be first thing.
		if(_gbs.getPlayer().isGymLeaderDefeated(5)){
			sideGateOpen=true;
		}
		else{
			sideGateOpen=false;
		}
		

		
		
		this.addTrainers();
		this.addWilds();
		this.loadAllEvents();
		
		if(QuietGreen.champion){
			this.addGradTrainers();
		}
		
		if(QuietGreen.champion){
			this.song = M2.GRADUATION;
		}
		else{
			this.song = M2.QUIET_GREEN;
		}
		
		this.xConstant=11;
		this.yConstant=-10;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=130;
		this._mapY=299;
		
		this._flyX=28;
		this._flyY=4;
		
		//Uncomment if this place is outdoors.
		this._outdoors=true;

		this.setBikeAllow(true);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Quiet Green";
		this._roomNum = _gbs.QUIET_GREEN;
		try{
			//if(_roomSource == null && _roomO == null && _roomFence == null && _sideGate == null && _openGate== null && _closedGate == null){
				System.out.println("START");
				if(GameBoyScreen.finishedLoading)
					_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/QuietGreen/QuietGreen Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/QuietGreen/QuietGreen Over.png"));
				_roomFence = ImageIO.read(this.getClass().getResource("/PokemonFiles/QuietGreen/QuietGreen Fence.png"));
				_sideGate = ImageIO.read(this.getClass().getResource("/PokemonFiles/QuietGreen/QuietGreen SideGate.png"));
				_openGate = ImageIO.read(this.getClass().getResource("/PokemonFiles/QuietGreen/QuietGreen OpenGate.png"));
				_closedGate = ImageIO.read(this.getClass().getResource("/PokemonFiles/QuietGreen/QuietGreen ClosedGate.png"));
				System.out.println("END");
			//}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
		this.createGrid();
	}
	
	public void addGradTrainers(){
		//_movingTrainers.clear();
		_movingTrainers = new Vector<Trainer>();
		try{
			Trainer grad1 = new Trainer.Backpacker(null);
			grad1.createHome(22,6);
			_movingTrainers.add(grad1);
			//22,6
		
			Trainer grad2 = new Trainer.BlueBro(null);
			grad2.createHome(20, 6);
			_movingTrainers.add(grad2);
			//20, 6
			
			Trainer grad3 = new Trainer.DirtyBlondeGirl(null);
			grad3.createHome(18, 6);
			_movingTrainers.add(grad3);
			//18, 6

			Trainer grad4 = new Trainer.GraySkirt(null);
			grad4.createHome(19, 8);
			_movingTrainers.add(grad4);
			//19,8
		
			Trainer grad5 = new Trainer.MaroonHat(null);
			grad5.createHome(17, 9);
			_movingTrainers.add(grad5);
			//17, 9
		
			Trainer grad6 = new Trainer.StrawHat(null);
			grad6.createHome(13, 3);
			_movingTrainers.add(grad6);
			//13, 3
		
			Trainer hazel = new Trainer.Hazeltine(null);
			hazel.createHome(13, 6);
			hazel.setDirectionAndImage(FACEWEST);
			_movingTrainers.add(hazel);
			//13, 6 (FACEWEST)
		
			Trainer grad7 = new Trainer.RedBandanaGirl(null);
			grad7.createHome(15,10);
			_movingTrainers.add(grad7);
		//15, 10
		
			Trainer grad8 = new Trainer.Pirate(null);
			grad8.createHome(18, 14);
			_movingTrainers.add(grad8);
		//18,14
		
			Trainer grad9 = new Trainer.Pigtails(null);
			grad9.createHome(18, 17);
			_movingTrainers.add(grad9);
		//18, 17
		
		
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public void createGrid(){
		this._room = new Room(40,33);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/QuietGreen.cmap"));
		for(int i = 0; i < 33; i++){
			for(int i2 = 0; i2 < 40; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		if(random < 100.0){
			randomEnemy = _wildPokemon.get(0 + (int)(5*Math.random()));
		}
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		if(!champion)
			this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		else{
			this.drawAllTrainersGraduating(g2, this.xConstant, this.yConstant, this._movingTrainers);
		}
		this.drawOptimalImage(g2, _roomO);
		this.drawOptimalImage(g2, _roomFence);
		
		if(!sideGateOpen){
			this.drawOptimalImage(g2, _sideGate);
		}
		
		if(champion){
			this.drawOptimalImage(g2, _openGate);
		}
		
		else{
			this.drawOptimalImage(g2, _closedGate);
		}


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//This needs to be set false. 
		//champion=false;
		
		//MainGreen
 		if((xInd == 12|| xInd==13||xInd==25||xInd==26) && (yInd == 1)){
 			super.enterRoom(_gbs.MAIN_GREEN, 0, 22, FACEEAST);
 		}

 		if(xInd==4 && yInd==11){
 			super.enterRoom(_gbs.CARRIE_TOWER, 2, 13, FACENORTH);
 		}
 		
 		//To waterman/thayer
 		if((xInd == 0) && (yInd == 15 || yInd == 16 || yInd == 17 || yInd == 18)){
			super.enterRoom(_gbs.ROUTE_2, 2, 54, FACENORTH);
		}
 		
 		//To Waterfire
 		if((xInd == 16||xInd == 17|| xInd == 18|| xInd == 19 || xInd ==20 || xInd == 21|| xInd == 22) && (yInd == 32)){
 			super.enterRoom(_gbs.WATERFIRE, 19, 26, FACENORTH);
 			if(champion){
 				_gbs.getPlayer().clearDestinations();
	 			_gbs.getPlayer().addDest(20, 7);
				_gbs.getPlayer().addDest(52, 7); //to train station;
				_gbs.getPanel(_gbs.WATERFIRE)._approachTimer.start();
				_gbs.getPlayer().setAvoidMethod("avoid");
				_gbs.getPlayer().setIgnoring(false);
	 		}
 		}
	}
	
	public void Up(){
		if(!_menuVisible && NORTH && _yIndex == 5 && !_dialogueVisible){
			if(_xIndex == 16 && !this.getMovingTrainers().get(UNIV1).isDefeated()){
				this.A_Button();
				return;
			}
			if(_xIndex == 22 && !this.getMovingTrainers().get(UNIV2).isDefeated()){
				this.A_Button();
				return;
			}
		}
		
		if(!_menuVisible && NORTH && _yIndex == 4 && !_dialogueVisible){
			if(_xIndex == 28 && !this.getMovingTrainers().get(SLATER1).isDefeated()){
				this.A_Button();
				return;
			}
			if(_xIndex == 31 && !this.getMovingTrainers().get(SLATER2).isDefeated()){
				this.A_Button();
				return;
			}
		}
		
		if(!_menuVisible && NORTH && _yIndex == 12 && !_dialogueVisible){
			if(_xIndex == 4 && !this.getMovingTrainers().get(TOWER).isDefeated()){
				this.A_Button();
				return;
			}
		}
		super.Up();
	}
	
	public void Down(){
		if(!_menuVisible && SOUTH && _yIndex == 12 && !_dialogueVisible){
			if(_xIndex == 16 && !this.getMovingTrainers().get(VW1).isDefeated()){
				this.A_Button();
				return;
			}
			if(_xIndex == 22 && !this.getMovingTrainers().get(VW2).isDefeated()){
				this.A_Button();
				return;
			}
			if(_xIndex == 18 && !this.getMovingTrainers().get(VW3).isDefeated()){
				this.A_Button();
				return;
			}
			if(_xIndex == 19 && !this.getMovingTrainers().get(VW4).isDefeated()){
				this.A_Button();
				return;
			}
			if(_xIndex == 20 && !this.getMovingTrainers().get(VW5).isDefeated()){
				this.A_Button();
				return;
			}
		}
		
		super.Down();
	}
	
	public void Start(){
		if(!champion){
			super.Start();
		}
	}
	public void Select(){
		if(!champion){
			super.Select();
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