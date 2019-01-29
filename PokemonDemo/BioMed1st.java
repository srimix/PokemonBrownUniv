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
public class BioMed1st extends PokePanel2 {
	private BufferedImage _roomO;
	private int _timeCount=0, _teleNPCTick=0;
	private Timer _teleportTimer;
	private PokePanel2 _this;
	private boolean _gymNotified=false;
	private boolean _paradisoGone=false;
	private boolean _rivalDefeated=false;
	private final int PARADISO=0, BARRICADE=4, SWIPER=5, RIVAL =6;
	
	public BioMed1st(GameBoyScreen gbs){
		super(gbs);
		_this=this;
		this.initializeEventVector(4);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public BioMed1st(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		_this=this;
		this.initializeEventVector(4);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				
				Trainer paradiso = new Trainer.ParadisoTeleport();
				paradiso.createHome(3, 4, 0, 0, "wander", "wander");
				paradiso.addDest(6, 3);
				paradiso.addDest(3, 4);
				paradiso.setFirstDest();
				paradiso.setStationary(false);
				paradiso.setVanishing(true);
				
				if(!_gymNotified){
					paradiso.getDialogue().clear();
					paradiso.getDialogue().add("Prof. Paradiso: So, you're interested in neuroscience?");
					paradiso.getDialogue().add("You should come to my class on Tuesday at 1pm.");
					paradiso.getDialogue().add("...Gym Leader? I have no idea what you're talking about.");	
				}
				
				Trainer trees = new Trainer.Ponytail(null);
				trees.createHome(7, 7, 0, 0, "drunk", "drunk");
				trees.addDest(12,8);
				trees.addDest(7,7);
				trees.setStationary(false);
				trees.setFirstDest();
				trees.getDialogue().clear();
				trees.getDialogue().add("See the bright and oddly colored trees?");
				trees.getDialogue().add("They're genetic hybrids. What a fun experiment!");
				trees.getDialogue().add("But not just genetics, many types of research labs are here.");
				trees.getDialogue().add("Supposedly Mewtwo resulted from a project done next door.");
				
				Trainer psy = new Trainer.RedstripeShirt(null);
				psy.createHome(16, 3, -2, 0, "drunk", "drunk");
				psy.addDest(16, 5);
				psy.setFirstDest();
				psy.setStationary(false);
				psy.getDialogue().clear();
				psy.getDialogue().add("Have you seen my Psyduck? I brought him here for an MRI scan...");
			
				Trainer center = new Trainer.PokemonCenter(_gbs.getPlayer(), this._roomNum, this);
				center.createHome(5, 2,0,0,"","");
			
				
				Trainer barricade = new Trainer.Barricade();
				barricade.createHome(18, 1, 0, 0, "", "");
				
				//Opens if paradiso is defeated.
				Trainer swiper = new Trainer.Swiper(_gbs.getPlayer(), this, 2, BARRICADE);
				swiper.createHome(18, 2, 0,-3,"","");
				
				Vector<Pokemon> rivalBelt = new Vector<Pokemon>();
				Pokemon r1 = new Pokemon.Pidgeotto().setWildLevel(18);
				Pokemon r2 = new Pokemon.Rattata().setWildLevel(15);
				Pokemon r3 = new Pokemon.Abra().setWildLevel(15);
				rivalBelt.add(r1);
				rivalBelt.add(r2);
				rivalBelt.add(r3);
				int rivStarter = _gbs.getRivalStarterNum();
//				if(_gbs.getRival() != null && _gbs.getRival().getBelt() != null)
//					rivStarter = _gbs.getRival().getBelt().get(0).getDexNum();
				if(rivStarter == 1){
					Pokemon r4 = new Pokemon.Bulbasaur().setWildLevel(17);
					rivalBelt.add(r4);
				}
				else if(rivStarter == 4){
					Pokemon r4 = new Pokemon.Charmander().setWildLevel(17);
					rivalBelt.add(r4);
				}
				else{
					Pokemon r4 = new Pokemon.Squirtle().setWildLevel(17);
					rivalBelt.add(r4);
				}
				Trainer rival = new Trainer.Rival(rivalBelt);
				rival.createHome(18, 9, 0, 0, "avoid","avoid");
				rival.getDialogue().clear();
				rival.getDialogue().add("Hey " + _gbs.getPlayer().getPlayerName() + "! It's been a while.");
				rival.getDialogue().add("I just talked with Prof. Miller, who told me all kinds of cool...");
				rival.getDialogue().add("...stuff about Pokemon evolution.");
				rival.getDialogue().add("One of my Pokemon has already evolved, how about yours?");
				//rival.setDefeatDialogue("Grrrr! I guess I need to study more!");
				//rival.getPostBattleDialogue().add("Whatever, I need to go fill out a concentration form anyways...");
				rival.setDefeatDialogue("Whatever, I need to go fill out a concentration form anyways...");
				rival.setStationary(true);
				rival.setVanishing(true);
				rival.defeat();
				rival.setDirectionAndImage(FACESOUTH);
				rival.setLOS(15);
				rival.setMoney(595);
				rival.setName(_gbs.getRival().getTrueName());

				
				Trainer mart = new Trainer.MartMenu("Life Sciences", this);
				mart.createHome(4, 2, 0,0,"","");
				this._thisMartContains.clear();
				this._thisMartContains.add(22); //pokeball
				this._thisMartContains.add(47); //escape rope
				this._thisMartContains.add(48); //repel
				
				Trainer pc = new Trainer.PC(_gbs);
				pc.createHome(9,1,0,0,"","");
				
				
			this._movingTrainers.add(paradiso); //0, see final int above.
			this._movingTrainers.add(trees); //1
			this._movingTrainers.add(psy); //2
			this._movingTrainers.add(center); //3
			this._movingTrainers.add(barricade);//4, final int
			this._movingTrainers.add(swiper);//5, final int
			this._movingTrainers.add(rival); //6, final int
			this._movingTrainers.add(mart); //7
			this._movingTrainers.add(pc); //8

			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}

	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			
			if(this.getCheckList().get(0)==1){
				_gymNotified=true;
				Trainer paradiso = this.getMovingTrainers().get(PARADISO);
				paradiso.getDialogue().clear();
				paradiso.getDialogue().add("Prof. Paradiso: ...it's my office hours?");
				paradiso.getDialogue().add("Ah alright, I'll see you upstairs.");
				paradiso.getDialogue().add("Kadabra, use teleport!");
				paradiso.getDialogue().add(" "); //This space is ABSOLUTELY NECESSARY. Do not erase.
			}
			
			if(this.getCheckList().get(1)==1){
				_paradisoGone=true;
				this.getMovingTrainers().get(PARADISO).defeat();
			}
			
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(SWIPER).defeat();
				this.getMovingTrainers().get(BARRICADE).defeat();
			}
			
			if(this.getCheckList().get(3)==1){
				this.getMovingTrainers().get(RIVAL).defeat();
				_rivalDefeated=true;
			}
			else if(this.getCheckList().get(3)==0){
				//This means you haven't beaten the rival yet, so the swiper needs to return.
				_rivalDefeated=false;
				this.getMovingTrainers().get(SWIPER).unDefeat();
				this.getMovingTrainers().get(BARRICADE).unDefeat();
			}
				
		}
	}

	public void scanForAllEvents(){
		//Is champ in the making guy notified?
		if(_gymNotified){
			this.getCheckList().set(0,1);
		}
		if(_paradisoGone || this.getMovingTrainers().get(PARADISO).isDefeated()){
			this.getCheckList().set(1,1);
		}
		if(this.getMovingTrainers().get(SWIPER).isDefeated()){
			this.getCheckList().set(2,1);
			
			if(!_rivalDefeated){
				this.getMovingTrainers().get(RIVAL).unDefeat();
				
				if(_xIndex!=18){
					_gbs.getPlayer().addDest(_xIndex+1, _yIndex);
					this._approachTimer.start();
				}
			}
		}
		
		if(this.getMovingTrainers().get(RIVAL).isDefeated() && _rivalDefeated){
			this.getCheckList().set(3,1);
		}
		
		
		System.out.println("event0:" + this.getCheckList().get(0));
		System.out.println("event1:" + this.getCheckList().get(1));
		System.out.println("event2:" + this.getCheckList().get(2));
	}
	
	public void createBaseRoom(){
		
		this.song = M2.BIOMED;
		
		this.addTrainers();
		this.loadAllEvents();
		this.setMartVisible(false);
		
		this.xConstant=46;
		this.yConstant=42;
		this._mapX=137;
		this._mapY=240;
		this._pkmnCentX=5;
		this._pkmnCentY=3;
		
		TeleportTimer teleport = new TeleportTimer();
		_teleportTimer = new Timer(25, teleport);
		
		this.setBattleBG(NewBattleScreen.PARADISO);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "BioMed 1st Floor";
		this._roomNum = 28;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BioMed/BioMed 1stFloor Background.png"));
				SysOut.print("ROOM LOADED");
			}
			if(_roomO == null)
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/BioMed/BioMed 1stFloor Over.png"));
						
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		if(this._room == null){
			this._room = new Room(21,11);
			Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BioMed1stFloor.cmap"));
			for(int i = 0; i < 11; i++){
				for(int i2 = 0; i2 < 21; i2++){
					this._room._roomGrid[i2][i] = scan.next().charAt(0);
				}
			}
		}
	}
	
	public void drawTeleNPCTick(int tick) throws IOException{
		int x = this.getMovingTrainers().get(_interruptedTrainer).getXSpace();
		int y = this.getMovingTrainers().get(_interruptedTrainer).getYSpace();
		BufferedImage teleImage=null;
	
		switch(tick){
			case 0: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/1.png"));
				break;
			case 1: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/2.png"));
				break;
			case 2: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/3.png"));
				break;
			case 3: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/4.png"));
				break;
			case 4: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/5.png"));
				break;
			case 5: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/6.png"));
				break;
			case 6: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/7.png"));
				break;
			case 7: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/8.png"));
				break;
			case 8: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/9.png"));
				break;
			case 9: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/10.png"));
				break;
			case 10: teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Paradiso/Teleport/11.png"));
				break;
		}
		
			
		g2.drawImage(teleImage, null, x+xConstant+this._xSpace,y+yConstant+this._ySpace);
		this.repaint();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawPlayer(g2);
		
		if(this._teleportTimer.isRunning()){
			try {
				this.drawTeleNPCTick(_teleNPCTick);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		
		
		
		this.drawBox(g2);		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 2
 		if((xInd == 16 || xInd == 17) && (yInd == 10)){
 			super.enterRoom(_gbs.ROUTE_3, 5, 12, FACESOUTH);
 		}
 		//BioMed 2nd Floor
 		if((xInd == 19) && (yInd == 8)){
 			super.enterRoom(_gbs.BIOMED_2, 9, 6, FACEEAST);
 		}
 		
 		
 		//Glass Tunnel
 		if((xInd == 19) && (yInd == 1 || yInd == 2)){
 			super.enterRoom(_gbs.GLASS_TUNNEL, 2, 2, FACEEAST);
 		}
		
	}
	
	public void blackout(){
		this.blackout(this._pkmnCentX,this._pkmnCentY,FACESOUTH);
		_gbs.getPlayer().healAllActive();
	}
	public void afterBattle(){
		super.afterBattle();
		if(_interruptedTrainer==RIVAL){
			SysOut.print("AfterBattle called");
			_rivalDefeated=true;
			this.scanForAllEvents();
			Trainer rival= this.getMovingTrainers().get(RIVAL);
			rival.unDefeat();
			rival.setVanishing(true);
			rival.setHomeIndices(17, 10);
			_returnTrip=true;
			this._approachTimer.start();
		}
	}
	
	
public class TeleportTimer implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			
			if(_timeCount == 1){
				_busy=true;
			}
			
			//Tick every 2 ms.
			if(Math.ceil(_timeCount/2.0)-Math.floor(_timeCount/2.0)==0){
				_teleNPCTick++;
				_this.repaint();
			}
			
			//There are 58 total ticks.
			if(_timeCount == 24){
				_teleportTimer.stop();
				_busy=false;
				_timeCount = 0;
				_teleNPCTick=0;
				if(_this.getMovingTrainers()!=null && _this.getMovingTrainers().get(_interruptedTrainer)!=null){
					Trainer moving=_this.getMovingTrainers().get(_interruptedTrainer);
					_this.completionCheck=false;
					_this._dialogueVisible=false;
					moving.setInterrupted(false);
					_this._NPCpage=0;
					_this.A_Button(); //This is to make the events save.
					_paradisoGone=true;
				}
				
				_this.repaint();
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
		if(facingNPC() && this._NPCpage == 3 && this._interruptedTrainer==PARADISO && _gymNotified){
			this.getMovingTrainers().get(this._interruptedTrainer).defeat();
			this._teleportTimer.start();
		}
		
	}
}




