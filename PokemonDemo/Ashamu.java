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
public class Ashamu extends PokePanel2 {
	private BufferedImage /*_roomBG,*/ _accent, _reflection, _mirror, _overlay;
	private int startX=0, startY=0;
	private int highestBadge=0;
	private Trainer blackSwan;
	
	public Ashamu(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(8);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Ashamu(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(8);
		
		this.createBaseRoom();
		
	}
	
	public void scanForAllEvents(){
		//This actually does nothing in this room.
		for (int i=0; i<this.getCheckList().size();i++){
			SysOut.print(""+(i+1)+ ": " + this.getCheckList().get(i));
		}
	}
	
	public void loadAllEvents(){
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer one = new Trainer.Ponytail(null);
				one.createHome(3, 4, 0,0,"avoid","avoid");
				one.getDialogue().add("Michelle: Welcome to Ashamu!");
				one.getDialogue().add("This studio belongs to the Dance department.");
				one.getDialogue().add("Every time you pass a landmark in your academic career you...");
				one.getDialogue().add("...are allowed to challenge yourself at the mirror.");
				this.getMovingTrainers().add(one);
				
				Trainer two = new Trainer.Ileana(null);
				two.createHome(6, 1, 0,0,"avoid","avoid");
				two.addDest(6,3);
				two.addDest(8,3);
				two.addDest(8,2);
				two.addDest(9,1);
				two.addDest(5,1);
				two.addDest(6,1);
				two.setFirstDest();
				two.setPause(true);
				two.setStationary(false);
				two.getDialogue().add("Jacqueline: You're messing up the formation!");
				two.getDialogue().add("Or you could fill in at the back window spot.");
				this.getMovingTrainers().add(two);
				
				Trainer three = new Trainer.BlackHairBlueDress(null);
				three.createHome(8, 1, 0,0,"avoid","avoid");
				three.addDest(8,3);
				three.addDest(10,3);
				three.addDest(10,2);
				three.addDest(9,3);
				three.addDest(5,3);
				three.addDest(8,1);
				three.setFirstDest();
				three.setStationary(false);
				three.setPause(true);
				three.getDialogue().add("Susan: aaah you're in the way!");
				three.getDialogue().add("Susan: ....yes, I \"see you\" too.");
				this.getMovingTrainers().add(three);
				
				Trainer four = new Trainer.BlackGirl(null);
				four.createHome(7, 3, 0,0,"avoid","avoid");
				four.addDest(7,1);
				four.addDest(5,1);
				four.addDest(5,2);
				four.addDest(5,1);
				four.addDest(5,2);
				four.addDest(9,2);
				four.addDest(7,3);
				four.setFirstDest();
				four.setPause(true);
				four.setStationary(false);
				four.getDialogue().add("Deepa: This is a collaboration piece of 4 different dance teams.");
				four.getDialogue().add("Badmaash, Attitude, Fusion, and Lion Dance.");
				four.getDialogue().add("I'm not sure what the resultant dance style is, but it's unique.");
				this.getMovingTrainers().add(four);
				
				Trainer five = new Trainer.Gyarados(null);
				five.createHome(9, 3, 0,0,"avoid","avoid");
				five.addDest(9,1);
				five.addDest(7,1);
				five.addDest(7,2);
				five.addDest(7,3);
				five.addDest(7,2);
				five.addDest(11,2);
				five.addDest(9,3);
				five.setPause(true);
				five.setFirstDest();
				five.setStationary(false);
				five.getDialogue().add("Dave: Dude, you gotta move!");
				this.getMovingTrainers().add(five);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.ASHAMU;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=-16;
		this.yConstant=163;
		this._mapX=172;
		this._mapY=299;
		
		
		
		this.setBattleBG(NewBattleScreen.PARADISO);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Ashamu Studio";
		this._roomNum = 36;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Ashamu/Ashamu Background.png"));
			_accent = ImageIO.read(this.getClass().getResource("/PokemonFiles/Ashamu/Ashamu Accents.png"));
			_mirror = ImageIO.read(this.getClass().getResource("/PokemonFiles/Ashamu/Ashamu Mirror.png"));
			_reflection = ImageIO.read(this.getClass().getResource("/PokemonFiles/Ashamu/Ashamu Reflections.png"));
			_overlay = ImageIO.read(this.getClass().getResource("/PokemonFiles/Ashamu/Ashamu Overlay.png"));
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(14,7);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Ashamu.cmap"));
		for(int i = 0; i < 7; i++){
			for(int i2 = 0; i2 < 14; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//DO NOT CHANGE ORDER.
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		
		this.drawPlayer(g2);
		
		
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(_reflection, null, this._xSpace, this._ySpace);
		for (int i=0;i<this._movingTrainers.size(); i++){
			this.drawNPCVertReflection(g2, this._movingTrainers.get(i).getXSpace()+xConstant+this._xSpace,40+(-22)-(_movingTrainers.get(i).getYSpace())+yConstant+this._ySpace, this._movingTrainers.get(i));
		}
		this.drawPlayerVertReflection(g2, 176, 200+(-22)+2*this._ySpace); //200-22+2y
		g2.drawImage(_mirror, null, this._xSpace, this._ySpace);
		g2.drawImage(_overlay, null, this._xSpace, this._ySpace);
		g2.drawImage(_accent, null, this._xSpace, this._ySpace);
		
		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Lincoln Field
 		if((xInd == 1 || xInd ==2) && (yInd == 6)){
 			super.enterRoom(_gbs.LINCOLN_FIELD, 16, 12, FACEEAST);
 		}
		
	}
	

	public boolean canFightMirror(){
		//Find highest badge number.
		highestBadge=0;
		for (int i=0; i<8; i++){
			if(_gbs.getPlayer().isGymLeaderDefeated(i+1)){
				highestBadge=i+1;
			}
		}
		
		//Have you fought before? if not, lets fight.
		if(this.getCheckList().get(highestBadge-1)==0){
			return true;
		}
		
		return false;
	}
	
	public void generatePlayerClone(){
		Player player= _gbs.getPlayer();
		try{
			Vector<Pokemon> belt= new Vector<Pokemon>();
			for (int i =0; i<player.getAllActive().size();i++){
				belt.add(Pokemon.clone(player.getActivePokemon(i)));
				belt.get(i).heal();
			}
			blackSwan=new Trainer.InvisTrainer(belt);
			blackSwan.createHome(_xIndex, _yIndex-1, 0,0,"","");
			blackSwan.setVanishing(true);
			blackSwan.setName(player.getPlayerName());
			blackSwan.setType("Shadow");
			blackSwan.setMoney(highestBadge*500);
			blackSwan.getDialogue().add("A shadowy figure stares back at you.");
			blackSwan.setDefeatDialogue("...");
			blackSwan.setBattleImage(ImageIO.read(this.getClass().getResource(("/PokemonFiles/TrainerImages/InvisTrainer/battle.png"))));
			
			this.getMovingTrainers().add(blackSwan);
		}
		catch(IOException ioe){}
		
	}
	
	public void A_Button(){
		if(_yIndex==1 && NORTH){
			if(this.canFightMirror()){
			//creates a trainer instantaneously in front of you.
			this.generatePlayerClone();
			this.getCheckList().set(highestBadge-1, 1);
			}
		}
		
		
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}

		super.A_Button();
	}
	
}