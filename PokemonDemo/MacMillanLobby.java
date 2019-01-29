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
public class MacMillanLobby extends PokePanel2 {
	
	public MacMillanLobby(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public MacMillanLobby(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				/*NEW TRAINER TEMPLATE
				 * 		
		Vector<Pokemon> belt = new Vector<Pokemon>();
		Trainer = new Trainer(belt);
		.createHome(0,0,0,0,"freeze","freeze");
		.setLOS();
		.setStationary();
		.addDest();
		.getDialogue().add();
		.getDialogue().add();
		.setDefeatDialogue();
		.setMoney();
		.setType();
		.setName();
		this.getMovingTrainers().add();
		*/

			Trainer geochem = new Trainer.GeoChemProf(null);
			geochem.createHome(6, 2);
			geochem.setGift(new Item.Potion());
			_movingTrainers.add(geochem);
			
			Trainer rando = new Trainer.Backpacker(null);
			rando.createHome(7,4);
			rando.setDirectionAndImage(FACEEAST);
			rando.getDialogue().add("Every year the Geology Dept has a IM softball team...");
			rando.getDialogue().add("...called the Rolling Stones.");
			_movingTrainers.add(rando);
				
			if(_gbs.getPlayer().getAllItems().get(Item.SUGGS_POTION).getRemaining() == 0){
				Trainer hess = new Trainer.Hess(null);
				hess.createHome(1, 1);
				hess.getDialogue().add("I'm sorry, but Professor Suggs isn't accepting challenges right now.");
				hess.getDialogue().add("He's quite angry that some of his chemicals have gone missing.");
				hess.getDialogue().add("I heard a rumor that they may have ended up in the wrong hands at Sex Power God.");
				hess.getDialogue().add("When I tried to check out Alumnae Hall though, there was a huge Snorlax in the way.");
				hess.getDialogue().add("Could you do me a favor and go to Media Services, get a Pokeflute...");
				hess.getDialogue().add("...and find those chemicals? I'm sure Prof. Suggs would agree...");
				hess.getDialogue().add("...to challenge you then.");
				_movingTrainers.add(hess);
			}
			else{
				Trainer champ = new Trainer.ChampGuy();
				champ.createHome(0, 2);
				champ.getDialogue().add("Hey there champ, I see you're finally ready for Orgo!");
				champ.getDialogue().add("Prof. Suggs is a MASTER of Grass-type Pokemon.");
				champ.getDialogue().add("And with his PhD in organic chemistry, he's incredibly smart.");
				champ.getDialogue().add("He likes to challenge students with quizzes in his gym.");
				champ.getDialogue().add("Answer correctly and move on. Answer wrong and a trainer battle awaits!");
				champ.getDialogue().add("Get through all of this quizzes to challenge Suggs himself.");
				champ.getDialogue().add("Oh, and " + _gbs.getPlayer().getPlayerName() +"...");
				champ.getDialogue().add("No worries if you can't beat him on the first try.");
				champ.getDialogue().add("Lots of people end up having to battle him twice.");
				_movingTrainers.add(champ);
			}
			
			Trainer pc = new Trainer.PC(_gbs);
			pc.createHome(8, 4);
			this._movingTrainers.add(pc);
			
			Trainer pc2 = new Trainer.PC(_gbs);
			pc2.createHome(9,4);
			this._movingTrainers.add(pc2);
			
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.BIOMED;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=4;
		this.yConstant=0;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX = 214;
		this._mapY = 295;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		this._pkmnCentX=1;
		this._pkmnCentY=2;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "MacMillan/Geochem.";
		this._roomNum = _gbs.MACMILLAN_LOBBY;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/MacMillanLobby/lobby.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(10,7);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/MacMillanLobby.cmap"));
		for(int i = 0; i < 7; i++){
			for(int i2 = 0; i2 < 10; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);

		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
 		if((xInd == 1) && (yInd == 1)){
 			super.enterRoom(_gbs.SUGGS_GYM, 8,13, FACENORTH);
 		}
		if((xInd == 4) && (yInd == 6)){
 			super.enterRoom(_gbs.THAYER_SOUTH, 31, 41, FACESOUTH);
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