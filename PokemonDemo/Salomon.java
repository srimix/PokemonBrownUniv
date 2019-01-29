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
public class Salomon extends PokePanel2 {
	private BufferedImage _roomO, _fade;
	private Trainer _prof;
	private ImageIcon _i;
	
	public Salomon(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Salomon(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
			Trainer _prof = new Trainer.GlassesProfessor(null);
			_prof.createHome(5, 1, 0, 0, "wander", "wander");
			_prof.addDest(6, 1);
			_prof.addDest(5, 1);
			_prof.setFirstDest();
			_prof.setStationary(false);
			
			_prof.getDialogue().add("Hello. My name is Professor Neumann.");
			_prof.getDialogue().add("Welcome to Contemporary Architecture.");
				
			int todaysLecture = (int)(Math.random()*4);
			switch(todaysLecture){
			case 0: _prof.getDialogue().add("Structuralism is a theoretical paradigm that emphasizes that elements of culture...");
				break;
			case 1: _prof.getDialogue().add("Frank Gehry, perhaps best known for the Guggenheim Museum in Spain, is a Canadian American...");
				break;
			case 2: _prof.getDialogue().add("Deconstructivism is characterised by ideas of fragmentation which serve to distort...");
				break;
			case 3: _prof.getDialogue().add("Bucky Fuller was most famous for his lattice shell structures - geodesic domes...");
				break;
			}
				
			
			
				this._movingTrainers.add(_prof);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.SALOMON;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=72;
		this.yConstant=73;
		this._mapX=138;
		this._mapY=294;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Salomon";
		this._roomNum = 34;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Salomon/Salomon Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Salomon/Salomon Over.png"));
				_fade = ImageIO.read(this.getClass().getResource("/PokemonFiles/Salomon/Salomon Fade.png"));
				_i = new ImageIcon(this.getClass().getResource("/PokemonFiles/Salomon/Lecture.gif"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(15,12);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Salomon.cmap"));
		for(int i = 0; i < 12; i++){
			for(int i2 = 0; i2 < 15; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		_i.paintIcon(this, g2, this._xSpace+0, this._ySpace+0);
		this.drawBox(g2);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(_fade, null, this._xSpace, this._ySpace);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route 2
 		if((xInd == 3 || xInd== 4|| xInd == 10|| xInd == 11) && (yInd == 10)){
 			super.enterRoom(_gbs.MAIN_GREEN, 33, 12, FACESOUTH);
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