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
public class SlaterMike extends PokePanel2 {
	private BufferedImage _roomO;
	
	public SlaterMike(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public SlaterMike(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {

				Vector<Pokemon> mikeBelt = new Vector<Pokemon>();
				mikeBelt.add(new Pokemon.Onix().setWildLevel(53));
				mikeBelt.add(new Pokemon.Dugtrio().setWildLevel(53));
				mikeBelt.add(new Pokemon.Golem().setWildLevel(53));
				mikeBelt.add(new Pokemon.Primeape().setWildLevel(54));
				mikeBelt.add(new Pokemon.Machamp().setWildLevel(56));
				
				Trainer mike = new Trainer.Mike(mikeBelt);
				mike.createHome(4, 4);
				mike.getDialogue().add("So there's a Pokemon on my left, and a Pokemon on my right...");
				mike.getDialogue().add("What? You aren't interested in my story?");
				mike.getDialogue().add("Very well then, I'll introduce myself.");
				mike.getDialogue().add("My name is Mike, the first in the Elite Four of Brown University.");
				mike.getDialogue().add("I'm all about my Pokemon.");
				mike.getDialogue().add("I've done all the research there is to do on the internet.");
				mike.setDefeatDialogue("Better check Wikipedia...");
				mike.getPostBattleDialogue().add("Very well, you may proceed to the next member.");
				mike.getPostBattleDialogue().add("This was just a taste of the Elite Four...");
				mike.setType("Elite 4:");
				mike.setName("Mike");
				_movingTrainers.add(mike);
				
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
		
		this.setEliteBattleTrue(MIKE);
		
		this.setBattleBG(NewBattleScreen.ELITE);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Elite 4: Mike";
		this._roomNum = _gbs.SLATER_MIKE;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/Mike/Room.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/Mike/RoomO.png"));
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
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);

		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 4 && yInd == 1){
			this.enterRoom(_gbs.SLATER_DAVID, 4,9,FACENORTH);
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