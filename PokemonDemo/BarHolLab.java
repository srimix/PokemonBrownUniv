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
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class BarHolLab extends PokePanel2 {

	private BufferedImage over, overPostAnim;
	private ImageIcon overAnim;
	private boolean locked = false, hasTeleported = false, animating = false, doneAnimating = false;
	private Trainer scientist;
	private final int PORTAL = 0;
	private Timer sequenceTimer;
	private int tick = 0;
	
	public BarHolLab(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		this.createBaseRoom();
	}
	
	public BarHolLab(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex,direction);
		this.initializeEventVector(1);
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0) == 1){
				hasTeleported = true;
			}
		}
	}
	
	public void scanForAllEvents(){
		if(hasTeleported){
			this.getCheckList().set(0, 1);
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		
		try{
			Trainer portal = new Trainer.Text();
			portal.createHome(6, 1, -40, +100, "avoid", "avoid");
			portal.setLOS(3);
			portal.setDirectionAndImage(FACENORTH);
			portal.defeat();
			portal.getDialogue().add("Oh god, it's just like Black Mesa all over again!!!");
			_movingTrainers.add(portal);
			
			Trainer text2 = new Trainer.Text(); //Books on desk
			text2.createHome(9, 5);
			text2.getDialogue().add("It's a thesis paper, titled \"Exploring the Possibility of");
			text2.getDialogue().add("Teleportation Using Parabolic Calculus and Quantum Mechanics\"");
			text2.getDialogue().add("It's written by somebody named Dr C. Johnson...");
			
			scientist = new Trainer.GlassesProfessor(null);
			
			if(!hasTeleported){
				//Pre-teleport. Excitedly asks you to get into the Teleporter.
				text2.getDialogue().add("...........................");
				text2.getDialogue().add("Looks legit!");
				
				scientist.setDirectionAndImage(FACESOUTH);
				scientist.addDest(4, 5);
				scientist.setFirstDest();
				scientist.setStationary(false);
				scientist.createHome(4, 6, 0, 0, "freeze", "freeze");
				scientist.getDialogue().add("Great, thanks for the help!");
				scientist.getDialogue().add("I've just finished triple-checking all my calculations!");
				scientist.getDialogue().add("Just hop into that teleporter on the right, and it'll ");
				scientist.getDialogue().add("teleport you into the one the left...");
				scientist.getDialogue().add("...hopefully.");
				_movingTrainers.add(scientist);
			}
			else{
				//Normal, apologizes, tells you to go to Prince Lab.
				text2.getDialogue().add("There are lots of red ink marks all over the paper.");
				text2.getDialogue().add("Guess it wasn't very reliable...");
				
				Trainer dont = new Trainer.Text();
				dont.createHome(6,2);
				dont.getDialogue().add("Things didn't go so well last time...");
				dont.getDialogue().add("Better not go in there again.");
				_movingTrainers.add(dont);
				
				scientist.createHome(7,5,0,0,"freeze","freeze");
				scientist.addDest(8, 5);
				scientist.setDirectionAndImage(FACEWEST);
				scientist.setFirstDest();
				scientist.setStationary(false);
				scientist.getDialogue().add("I'm so sorry again about what happened...");
				scientist.getDialogue().add("I've arranged for a colleague of mine");
				scientist.getDialogue().add("to give you something for me.");
				scientist.getDialogue().add("He's waiting for you in Prince Labs.");
				scientist.getDialogue().add("His method of travel is a little outdated, I think...");
				scientist.getDialogue().add("BUT it is much more rigorously proven.");
				_movingTrainers.add(scientist);
			}
			
			Trainer text1 = new Trainer.Text(); //Chalkboard
			text1.createHome(4, 0);
			text1.getDialogue().add("A chalkboard with a diagram on it.");
			text1.getDialogue().add("'Little is known about how Psychic Pokemon like Abra can teleport.'");
			text1.getDialogue().add("'This experiment uses 'portal' technology to allow humans to");
			text1.getDialogue().add(" teleport as well.'");
			text1.getDialogue().add("The diagram shows an Abra teleporting, and a human with a '?'.");
			_movingTrainers.add(text1);
			
			_movingTrainers.add(text2);
			
			Trainer text3 = new Trainer.Text(); //Computer with Half-Life logo
			text3.createHome(8, 4);
			text3.getDialogue().add("A computer...The desktop background is an orange Lamba.");
			_movingTrainers.add(text3);
			
			if(!hasTeleported){
				Trainer text4 = new Trainer.Text();
				text4.createHome(9, 8, 0, 0, "", "");
				text4.getDialogue().add("Wait! Please help me out my experiment first!");
				_movingTrainers.add(text4);
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "The Lab."; //Period is intentional for comedic effect.
		this._roomNum = _gbs.BARHOL_LAB;
		
		this._mapX = 270;
		this._mapY = 305;
		this.yConstant = 2;
		this.xConstant = -16;
		this.setBattleBG(NewBattleScreen.BARHOL);
		this.loadAllEvents();
		this.addTrainers();
		
		BeginSequence bs = new BeginSequence();
		sequenceTimer = new Timer(500, bs);
		
		this.song = M2.LAB;
		
		if(GameBoyScreen.finishedLoading){
			try{
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLab/lab.png"));
				over = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLab/overPre.png"));
				overAnim = new ImageIcon(this.getClass().getResource("/PokemonFiles/BarHolLab/c.gif"));
				if(overAnim == null){
					SysOut.print("GAH WTF");
					System.exit(0);
				}
				overPostAnim = ImageIO.read(this.getClass().getResource("/PokemonFiles/BarHolLab/overPost.png"));
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
			
			this.createGrid();
		}
	}
	
	public void createGrid(){
		this._room = new Room(12,9);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BHLab.cmap"));
		for(int i = 0; i < 9; i++){
			for(int i2 = 0; i2 < 12; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
		if(hasTeleported){
			SysOut.print("!HAS TELEPORTED");
			this._room._roomGrid[6][2] = 'N';
		}
		else{
			this._room._roomGrid[9][8] = 'N';
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 9 && yInd == 8){
			super.enterRoom(_gbs.BARHOL_HALLWAY, 10, 1, FACESOUTH);
		}
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		if(xInd == 6 && yInd == 2){
			//Change this index to whatever you want for sci li roof.
			//_busy = true;
			SysOut.print("Entered door?");
			//_busy = true;
			sequenceTimer.start();
			//this.customTeleportTo(_gbs.BARHOL_HALLWAY, 3, 3);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_gbs.BACKGROUND,null,this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		if(animating){
			if(overAnim==null){
				overAnim = new ImageIcon(this.getClass().getResource("/PokemonFiles/BarHolLab/c.gif"));
			}
			overAnim.paintIcon(this, g2, this._xSpace, this._ySpace);
			
		}
		else{
			if(!doneAnimating)
				g2.drawImage(over, null, this._xSpace, this._ySpace);
			else
				g2.drawImage(overPostAnim, null, this._xSpace, this._ySpace);
		}
		
		this.drawAllGenerics(g2);
	}
	
	public class BeginSequence implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(tick == 0){
				animating = true;
				SysOut.print("animated");
				
			}
			
			if(tick == 3){
				//alert scientist.
				M2.playBG(M2.ALARM);
				getMovingTrainers().get(PORTAL).unDefeat();
				_paintAlert=true;
				Up();
				A_Button();
			}
			
			if(tick == 8){
				animating = false;
				doneAnimating = true;
				//SysOut.print("TRAINER?");
				sequenceTimer.stop();
				hasTeleported = true;
				customTeleportTo(_gbs.SCILI_ROOF,13, 2);
				_paintAlert=false;
			}
			
			tick++;
			repaint();
			
		}
	}
	
	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning()){

			if(!_busy){
				this.completionCheck = false;
			}
			//this.repaint();
		}
		
		super.A_Button();
		
		if(!hasTeleported && this._interruptedTrainer == PORTAL && this.getMovingTrainers().get(PORTAL).isInterrupted()){
			//FIXME?
		}
	}
	
	//Forbid movement during timer.
	public void Down(){
		if(this.sequenceTimer.isRunning()){
			return;
		}
		if(!_menuVisible && SOUTH && _xIndex == 9 && _yIndex == 7 && !hasTeleported && !_dialogueVisible){
			this.A_Button();
			return;
		}
		super.Down();
	}
	public void Up(){
		if(this.sequenceTimer.isRunning()){
			return;
		}
		super.Up();
	}
	public void Left(){
		if(this.sequenceTimer.isRunning()){
			return;
		}
		super.Left();
	}
	public void Right(){
		if(this.sequenceTimer.isRunning()){
			return;
		}
		super.Right();
	}
	public void Start(){
		if(this.sequenceTimer.isRunning()){
			return;
		}
		super.Start();
	}
	public void Select(){
		if(this.sequenceTimer.isRunning()){
			return;
		}
		super.Select();
	}
	public void B_Button(){
		if(this.sequenceTimer.isRunning()){
			return;
		}
		super.B_Button();
	}
}
