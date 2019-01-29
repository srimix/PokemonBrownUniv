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
public class SlaterSri extends PokePanel2 {
	private BufferedImage  _roomO, black;
	private ImageIcon wave;
	
	public SlaterSri(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	
	}

	public SlaterSri(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {

				Vector<Pokemon> sriBelt = new Vector<Pokemon>();
				//Should be levels 58-59
				sriBelt.add(new Pokemon.Haunter().setWildLevel(56));
				sriBelt.add(new Pokemon.Clefable().setWildLevel(57));
				sriBelt.add(new Pokemon.Snorlax().setWildLevel(55));
				sriBelt.add(new Pokemon.Hypno().setWildLevel(57));
				sriBelt.add(new Pokemon.Gengar().setWildLevel(58));
				
				Trainer sri = new Trainer.Sri(sriBelt);
				sri.createHome(4, 4);
				sri.getDialogue().add("Good noon. My name is Sri");
				sri.getDialogue().add("Imagine how powerful and productive one could be...");
				sri.getDialogue().add("...if they never slept! I have pushed myself to the limit");
				sri.getDialogue().add("of sleeplessness to master the realm of Pokemon training.");
				sri.getDialogue().add("Prepare to meet your worst nightmare!");
				sri.setDefeatDialogue("My blood sugar must be low. Better head to K&C.");
				sri.getPostBattleDialogue().add("Very well, you seem to have awoken your true potential.");
				sri.getPostBattleDialogue().add("But can you handle the fourth and final member of the Elite Four?");
				sri.getPostBattleDialogue().add("...and can you tell Mat to shut his f***ing window?");
				sri.getPostBattleDialogue().add("It's FREEZING in here...");
				sri.setType("Elite 4:");
				sri.setName("Sri");
				_movingTrainers.add(sri);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.GYM;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=17;
		this.yConstant=0;
		
		this.setBattleBG(NewBattleScreen.ELITE);
		
		this.setEliteBattleTrue(SRI);
		
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
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Elite 4: Sri";
		this._roomNum = _gbs.SLATER_SRI;
		try{
			if(GameBoyScreen.finishedLoading){
				black=ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/Sri/RoomBlack.png"));
				wave = new ImageIcon(this.getClass().getResource("/PokemonFiles/Slater/Sri/wave.gif"));
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/Sri/RoomSri.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/Sri/RoomO.png"));
				
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(9,10);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Elite4.cmap"));
		for(int i = 0; i < 10; i++){
			for(int i2 = 0; i2 < 9; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		
		this._room._roomGrid[4][1] = 'N';
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(black, null, this._xSpace, this._ySpace);
		wave.paintIcon(this, g2, this._xSpace, this._ySpace);
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);

		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 4 && yInd == 1){
			this.enterRoom(_gbs.SLATER_MAT, 4,9,FACENORTH);
		}
	}
	

	public void afterBattle(){
		super.afterBattle();
		
		this._room._roomGrid[4][1] = 'D';
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