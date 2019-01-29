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
public class SlaterMat extends PokePanel2 {
	private BufferedImage  _roomO;
	
	public SlaterMat(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	
	}

	public SlaterMat(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {

				Vector<Pokemon> matBelt = new Vector<Pokemon>();
				matBelt.add(new Pokemon.Poliwrath().setWildLevel(58));
				matBelt.add(new Pokemon.Dewgong().setWildLevel(58));
				matBelt.add(new Pokemon.Lapras().setWildLevel(59));
				matBelt.add(new Pokemon.Cloyster().setWildLevel(57));
				matBelt.add(new Pokemon.Blastoise().setWildLevel(60));
				
				Trainer mat = new Trainer.Mat(matBelt);
				mat.createHome(4, 4);
				mat.getDialogue().add("Oh hai! I'm Mat Reiss, the creator of Pokemon: Brown.");
				mat.getDialogue().add("Pokemon: Brown is my gift to you, a Brown student and lover of Pokemon.");
				mat.getDialogue().add("But don't think that means I'll go easy on you.");
				mat.getDialogue().add("You'll have to prove your worth to call yourself Pokemon Champion!");
				mat.setDefeatDialogue("Flawless Victory!");
				mat.getPostBattleDialogue().add("Congratulations on your victory.");
				mat.getPostBattleDialogue().add("Now I have good news, bad news, and horrible news.");
				mat.getPostBattleDialogue().add("The good news is that you've defeated the Elite Four!");
				mat.getPostBattleDialogue().add("The bad news is that your roommate did too.");
				mat.getPostBattleDialogue().add("Like, five minutes before you got here. LOL.");
				mat.getPostBattleDialogue().add("Before you call yourself Champion, you must go defeat him.");
				mat.getPostBattleDialogue().add("Oh, the horrible news is that Toledo is closed.");
				mat.getPostBattleDialogue().add("I could REALLY go for some Pizza-in-a-cone right now...");
				mat.setType("Elite 4:");
				mat.setName("Mat");
				_movingTrainers.add(mat);
				
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
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setEliteBattleTrue(this.MAT);
		
		this.setBattleBG(NewBattleScreen.ELITE);
		
		this.setBackground(Color.BLACK);
		this.description = "Elite 4: Mat";
		this._roomNum = _gbs.SLATER_MAT;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/Mat/Room.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Slater/Mat/RoomO.png"));
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
			this.enterRoom(_gbs.SLATER_CHAMP, 5,14,FACENORTH); //TODO - Dunno if it's 9,4
			
			this._room._roomGrid[4][1] = 'N';
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