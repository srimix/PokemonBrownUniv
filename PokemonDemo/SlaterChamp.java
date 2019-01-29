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
public class SlaterChamp extends PokePanel2 {
//	private BufferedImage _roomSource;
	
	public SlaterChamp(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	
	}

	public SlaterChamp(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {

				Vector<Pokemon> champBelt = new Vector<Pokemon>();
				champBelt.add(new Pokemon.Pidgeot().setWildLevel(61));
				champBelt.add(new Pokemon.Alakazam().setWildLevel(59));
				champBelt.add(new Pokemon.Rhydon().setWildLevel(61));
				int rivalChoice = _gbs.getRivalStarterNum();
//				if(_gbs.getRival() != null && _gbs.getRival().getBelt() != null && _gbs.getRival().getBelt().get(0) != null)
//					rivalChoice = _gbs.getRival().getBelt().get(0).getDexNum();
//		
				if(rivalChoice == 1){ //Bulbasaur
					champBelt.add(new Pokemon.Arcanine().setWildLevel(63));
					champBelt.add(new Pokemon.Gyarados().setWildLevel(61));
					champBelt.add(new Pokemon.Venusaur().setWildLevel(65));
				} else if(rivalChoice == 4){ //Charmander
					champBelt.add(new Pokemon.Gyarados().setWildLevel(63));
					champBelt.add(new Pokemon.Exeggutor().setWildLevel(61));
					champBelt.add(new Pokemon.Charizard().setWildLevel(65));
				} else{ //Squirtle
					champBelt.add(new Pokemon.Exeggutor().setWildLevel(63));
					champBelt.add(new Pokemon.Gyarados().setWildLevel(61));
					champBelt.add(new Pokemon.Blastoise().setWildLevel(65));
				}
				Trainer champion = new Trainer.Rival(champBelt);
				champion.createHome(5, 6);
				champion.setType("Champion");
				champion.setName(_gbs.getPlayer().getRivalName());
				champion.getDialogue().add("Hey man! Long time no see.");
				champion.getDialogue().add("They say that college roommates share an unbreakable bond.");
				champion.getDialogue().add("This battle was destined to happen.");
				champion.getDialogue().add("Finally, we will determine who is the true Pokemon Champion!");
				champion.setDefeatDialogue("After all this time, I thought finally I could win...");
				champion.getPostBattleDialogue().add("Very well, " + _gbs.getPlayer().getPlayerName() + "...");
				champion.getPostBattleDialogue().add("You have earned the right to call yourself Champion of Brown.");
				champion.getPostBattleDialogue().add("If you're ever up for a rematch or anything though, I'll be waiting.");
				champion.getPostBattleDialogue().add("See ya 'round...buddy.");
				_movingTrainers.add(champion);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.GYM;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=2;
		this.yConstant=10;
		
		
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

		this.setEliteBattleTrue(this.RIVAL);
		this.setBattleBG(NewBattleScreen.ELITE);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Elite 4 Champion";
		this._roomNum = _gbs.SLATER_CHAMP;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/Champ/Room.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(11,16);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Elite4Champion.cmap"));
		for(int i = 0; i < 16; i++){
			for(int i2 = 0; i2 < 11; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		
		//this._room._roomGrid[5][1] = 'D'; //REMOVE ME!
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);

		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 5 && yInd == 1){
			this.enterRoom(_gbs.QUIET_GREEN, 28, 3, FACESOUTH);
			QuietGreen.champion=true;
			_gbs.getPlayer().clearDestinations();
			_gbs.getPlayer().addDest(19, 8);
			_gbs.getPlayer().addDest(19, 13);
			_gbs.getPlayer().addDest(19, 32); //to waterfire;
			_gbs.getPanel(_gbs.QUIET_GREEN)._approachTimer.start();
			_gbs.getPlayer().setIgnoring(false);
			_gbs.getPlayer().setAvoidMethod("avoid");
			
			//CLEAR ALL EVENTS HERE.
		}
	}
	
	
	public void afterBattle(){
		super.afterBattle();
		
		if(this._interruptedTrainer == 0){
			_gbs.saveHallOfFame();
			this._room._roomGrid[5][1] = 'D';
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
