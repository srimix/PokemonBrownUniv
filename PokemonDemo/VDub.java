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
public class VDub extends PokePanel2 {
	private BufferedImage _roomO;
	private final int SNORLAX=3;
	
	public VDub(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public VDub(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			try{
				this._movingTrainers = new Vector<Trainer>();
				Trainer center = new Trainer.PokemonCenter(_gbs.getPlayer(), this._roomNum, this);
				center.createHome(18, 21, 0, 0, "", "");
				this._movingTrainers.add(center); //0
				
				Trainer pc = new Trainer.PC(_gbs);
				pc.createHome(14, 6, 0, 0, "", "");
				this._movingTrainers.add(pc); //1
				
				//many randos
				//pembroke jokes
				//gate jokes
				//pokeflute? where? naked guy
				
				
				Trainer snorlaxAsleep = new Trainer.SnorlaxAsleep(this);
				snorlaxAsleep.createHome(19, 6);
				snorlaxAsleep.getDialogue().add("ZzzzZZZzzzzZz...");
				snorlaxAsleep.getDialogue().add("It's a giant Snorlax, sound asleep.");
				snorlaxAsleep.getDialogue().add("He seems to have feasted heavily on chicken fingers.");
				if(_gbs.getPlayer().getItem(Item.POKEFLUTE).getRemaining()>0){
					snorlaxAsleep.setVanishing(true);
					snorlaxAsleep.defeat();
				}
		
				Trainer snorlaxAwake = new Trainer.LegendaryOrWild(_gbs, new Pokemon.Snorlax().setWildLevel(30),"Snorlax: Yaaaaawn!", "P-Snorlax", M2.SNORLAX);
				snorlaxAwake.createHome(19,6);
				if(_gbs.getPlayer().getItem(Item.POKEFLUTE).getRemaining()==0){
					snorlaxAwake.setVanishing(true);
					snorlaxAwake.defeat();
				}
				
				this.getMovingTrainers().add(snorlaxAsleep); //2
				this.getMovingTrainers().add(snorlaxAwake); //3
				
				Trainer naked = new Trainer.NakedGuy(null);
				naked.createHome(11, 11, 0, 0, "wander", "wander");
				naked.addDest(13, 13);
				naked.addDest(11, 11);
				naked.setFirstDest();
				naked.setStationary(false);
				naked.getDialogue().add("Why am I naked? Why aren't YOU naked?");
				naked.getDialogue().add("It's time for SPG to start. Sadly, there's a Snorlax in the way.");
				naked.getDialogue().add("I can't seem to wake him up, but I hear a Pokeflute would work.");
				naked.getDialogue().add("Media Services at the top of the SciLi rents them out.");
				
				this.getMovingTrainers().add(naked);
				
				Trainer read = new Trainer.BookReadBoy(null);
				read.createHome(9, 21);
				read.getDialogue().add("My exam is in 15 minutes!");
				read.getDialogue().add("MUST. CRAM.");
				
				this.getMovingTrainers().add(read);
				
				Trainer pemb = new Trainer.Ponytail(null);
				pemb.createHome(7, 23, 0, 0, "wander", "wander");
				pemb.addDest(14, 25);
				pemb.addDest(7, 23);
				pemb.setFirstDest();
				pemb.setStationary(false);
				pemb.getDialogue().add("Living in Champlin is great! The VDub and gym are right here.");
				pemb.getDialogue().add("I'd never want to live in Kee-nasty.");
				this.getMovingTrainers().add(pemb);
				
				Trainer bl = new Trainer.BlondeDude(null);
				bl.createHome(14, 22, 0, 0, "wander", "wander");
				bl.addDest(18, 26);
				bl.addDest(14, 22);
				bl.setFirstDest();
				bl.setStationary(false);
				if(_gbs.getPlayer().isGymLeaderDefeated(6) && !_gbs.getPlayer().isGymLeaderDefeated(7)){
					bl.getDialogue().add("They're selling Spring Weekend tickets on Route 1!");
					bl.getDialogue().add("You should go buy one now.");
				}
				else if (_gbs.getPlayer().isGymLeaderDefeated(7)){
					bl.getDialogue().add("How was the concert? I heard Gambino and Glitch Mob were great.");
				}
				else{
					bl.getDialogue().add("They're going to run out of Spring Weekend tickets online.");
					bl.getDialogue().add("You should get them in person later on Route 1.");
					bl.getDialogue().add("They haven't started selling yet though...");	
				}
				this.getMovingTrainers().add(bl);
				
			}
			catch(IOException ioe){}
		
		}

	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(SNORLAX).isDefeated() && _gbs.getPlayer().getItem(Item.POKEFLUTE).getRemaining()>0){
			this.getCheckList().set(0, 1);
		}
	}
	public void loadAllEvents(){
		if(this.getCheckList()!=null){
			if(this.getCheckList().get(0)==1){
				//Defeat both snorlaxes AND the naked guy
				this.getMovingTrainers().get(SNORLAX).setVanishing(true);
				this.getMovingTrainers().get(SNORLAX).defeat();
				this.getMovingTrainers().get(SNORLAX-1).setVanishing(true);
				this.getMovingTrainers().get(SNORLAX-1).defeat();
				this.getMovingTrainers().get(SNORLAX+1).setVanishing(true);
				this.getMovingTrainers().get(SNORLAX+1).defeat();
			}
		}
	}
	
	public void createBaseRoom(){
		
		this.song = M2.VDUB_LOBBY;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-8;
		this.yConstant=-20;
		this._mapX=135;
		this._mapY=235;
		this._pkmnCentX=18;
		this._pkmnCentY=22;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);

		this.setBattleBG(NewBattleScreen.INDOORS);
		this.setBackground(Color.BLACK);
		this.description = "VDub Lobby";
		this._roomNum = 49;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/VDub/VDub Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/VDub/VDub Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(22,28);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/VDub.cmap"));
		for(int i = 0; i < 28; i++){
			for(int i2 = 0; i2 < 22; i2++){
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
	}
	
	public void enterRoom(int xInd, int yInd){
		//Pembroke
 		if((xInd == 10 || xInd==11) && (yInd == 27)){
 			super.enterRoom(_gbs.PEMBROKE, 24, 7, FACESOUTH);
 		}
 		//Back of Pembroke
 		if((xInd == 19) && (yInd == 1)){
 			super.enterRoom(_gbs.PEMBROKE_BACK, 9, 8, FACENORTH);
 		}
 		//Gym of Pembroke
 		if((xInd == 1) && (yInd == 24)){
 			super.enterRoom(_gbs.SATELLITE_GYM, 5, 12, FACENORTH);
 		}
 		//Dining Hall
 		if((xInd == 20) && (yInd == 8 || yInd ==9)){
 			super.enterRoom(_gbs.VDUB_DINING_HALL, 8, 13, FACENORTH);
 		}
 		//Woolley Stair
 		if((xInd == 7) && (yInd == 20)){
 			super.enterRoom(_gbs.WOOLLEY_STAIR, 5, 1, FACEWEST);
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
		if(this.getMovingTrainers().get(SNORLAX).isInterrupted() && M2.getCurrentFX()==M2.POKEFLUTE && M2.bgPlayer.isPaused()){
			SysOut.print("Wait...");
			return;}

		super.A_Button();
	}
}