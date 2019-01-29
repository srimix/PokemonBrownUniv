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
public class SlaterDavid extends PokePanel2 {
	private BufferedImage _roomO;
	
	public SlaterDavid(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	
	}

	public SlaterDavid(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {

				Vector<Pokemon> davidBelt = new Vector<Pokemon>();
				davidBelt.add(new Pokemon.Kabutops().setWildLevel(55));
				davidBelt.add(new Pokemon.Omastar().setWildLevel(55));
				davidBelt.add(new Pokemon.Aerodactyl().setWildLevel(57));
				
				Trainer david = new Trainer.DongJoo(davidBelt);
				david.createHome(4, 4);
				david.getDialogue().add("Hello there. My name is Dong Joo David Kim.");
				david.getDialogue().add("I am a master of the ancient Pokemon.");
				david.getDialogue().add("These Pokemon used to roam the world, pwning n00bs.");
				david.getDialogue().add("Shotty bitches.");
				david.setDefeatDialogue("You spun my head right round...Right round.");
				david.getPostBattleDialogue().add("I will return. Remember that, for yours is the first head I will seek.");
				david.getPostBattleDialogue().add("But seriously, you may proceed to the next room to face Sri.");
				david.getPostBattleDialogue().add("He's co-creator of Pokemon: Brown. I don't think he's slept for weeks...");
				david.setType("Elite 4:");
				david.setName("David");
				_movingTrainers.add(david);
				
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
		
		
		this.setEliteBattleTrue(DAVID);
		this.setBattleBG(NewBattleScreen.ELITE);
		
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
		this.description = "Elite 4: David";
		this._roomNum = _gbs.SLATER_DAVID;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/David/Room.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/David/RoomO.png"));
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
			this.enterRoom(_gbs.SLATER_SRI, 4,9,FACENORTH);
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