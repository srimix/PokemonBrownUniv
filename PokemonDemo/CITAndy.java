package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class CITAndy extends PokePanel2{
	
	//private BufferedImage bg;
	
	public CITAndy(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
	}
	
	public CITAndy(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
	}

	public void createBaseRoom(){
		this._roomNum = _gbs.CIT_ANDY;
		this.song = M2.GYM;
		
		this.setBackground(new Color(50,20,20));
		this.description = "Andy's Office";
		
		this.xConstant =3;
		
		this._mapX = 218;
		this._mapY = 295;
		
		this.addTrainers();
		this.setBattleBG(NewBattleScreen.CIT);
		this.loadAllEvents();
		
		//this.setBattleBG(NewBattleScreen.CS);
		
		if(GameBoyScreen.finishedLoading){
			try{
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/CITGymHall/andyGym.png"));
			}
			catch(IOException ioe){ioe.printStackTrace();}
		}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(5,6);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/CITandy.cmap"));
		for(int i=0; i < 6; i++){
			for(int i2=0; i2 < 5; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		try{
			Vector<Pokemon> andyBelt = new Vector<Pokemon>();
			andyBelt.add(new Pokemon.Voltorb().setWildLevel(21));
			andyBelt.add(new Pokemon.Magnemite().setWildLevel(18));
			andyBelt.add(new Pokemon.Raichu().setWildLevel(24));
			Trainer andy = new Trainer.AndyVanDam(andyBelt, _gbs);
			
			andy.createHome(2, 2, -1000, -1000, "", "");
			andy.setLOS(0);
			andy.getDialogue().add("A new challenger? Very well, time to start 'class'.");
			andy.getDialogue().add("All attempts to beat me will be 'null' and 'void'.");
			andy.getDialogue().add("Oh, you still want to try? I can see your ambition 'isVisible'.");
			andy.getDialogue().add("Very well then, time to 'swing' into action!");
			andy.setDefeatDialogue("Alas, 'Andy.isDefeated()' returns 'true'...");
			andy.getPostBattleDialogue().add("Excellent. You should sign up to be a TA!");
			andy.getPostBattleDialogue().add(_gbs.getPlayer().getPlayerName() + " received the Java Badge!");
			andy.getPostBattleDialogue().add("This qualifies you to be a TA if you'd like.");
			andy.getPostBattleDialogue().add("Here's a little something I give all my TAs...");
			andy.getPostItemDialogue().add("That's TM24, Thunderbolt! It'll fry any Pokemon with a power surge!");
			
			_movingTrainers.add(andy);
		}
		catch(IOException ioe){ioe.printStackTrace();}
	}
	
	public void enterRoom(int xInd, int yInd){
		if(yInd == 5){
			super.enterRoom(_gbs.CIT_HALL, 18, 0, FACESOUTH);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0) == 1){
				_movingTrainers.get(0).defeat();
				_movingTrainers.get(0).setGift(null);
				_movingTrainers.get(0).getDialogue().clear();
				_movingTrainers.get(0).getDialogue().add(_movingTrainers.get(0).getPostItemDialogue().get(0));
			}
		}
	}
	
	public void scanForAllEvents(){
		if(_movingTrainers.get(0).isDefeated()){
			this.getCheckList().set(0,1);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		this.drawPlayer(g2);
		
		this.drawAllGenerics(g2);
	}
	
	public void afterBattle(){
		super.afterBattle();
		if(_interruptedTrainer == 0){
			_gbs.getPlayer().defeatGymLeader(3);
		}
	}
	
	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			if(this._interruptedTrainer==0 && this.getMovingTrainers().get(0).isDefeated() && this.getMovingTrainers().get(0).getGift()!=null && (_NPCpage==1)){
				M2.playFX(M2.BADGE);
			}
			
			this.repaint();
		}

		super.A_Button();
	}
}
