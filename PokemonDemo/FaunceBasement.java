package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class FaunceBasement extends PokePanel2 {

	private BufferedImage roomO;
	
	public FaunceBasement(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		this.createBaseRoom();
	}
	
	public FaunceBasement(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		this.createBaseRoom();
	}
	
	public void addTrainers(){
	
		try{
			_movingTrainers = new Vector<Trainer>();
			
			Trainer mart = new Trainer.MartMenu("Campus Market", this);
			mart.createHome(4, 3, 0, 0, "", "");
			_movingTrainers.add(mart);
			
			this._thisMartContains.clear();
			this._thisMartContains.add(22); //pokeball
			this._thisMartContains.add(28); //antidote
			this._thisMartContains.add(27); //awakening
			this._thisMartContains.add(32); //burn heal
			this._thisMartContains.add(30); //paralz heal
			this._thisMartContains.add(0); //potion
			this._thisMartContains.add(47); //escape rope
			this._thisMartContains.add(48); //repel
			
			Trainer martPerson = new Trainer.GraySkirt(null);
			martPerson.createHome(10, 3, 0, 0, "", "");
			martPerson.setDirectionAndImage(FACEEAST);
			martPerson.getDialogue().add("This place is great if you wanna grab a snack.");
			martPerson.getDialogue().add("They have snacks, microwave meals, and chips & dip, mmmmm...");
			_movingTrainers.add(martPerson);
			
			Trainer indecide = new Trainer.Ileana(null);
			indecide.createHome(7, 2, 0, 0, "", "");
			indecide.setDirectionAndImage(FACESOUTH);
			indecide.setStationary(false);
			indecide.addDest(7, 1);
			indecide.setFirstDest();
			indecide.getDialogue().add("Which 50c candy do I want to get???");
			indecide.getDialogue().add("There are so many choices!");
			_movingTrainers.add(indecide);
			
			Trainer pool1 = new Trainer.MafiaMagenta(null); //greek
			pool1.createHome(3, 8, 0, 0, "freeze", "freeze");
			pool1.setStationary(false);
			pool1.addDest(4, 8);
			pool1.setFirstDest();
			pool1.setDirectionAndImage(FACEWEST);
			pool1.getDialogue().add("8-ball, corner pocket...");
			_movingTrainers.add(pool1);
			
			Trainer pool2 = new Trainer.MafiaWhite(null); //greek
			pool2.createHome(5, 6, 0, 0, "freeze", "freeze");
			pool2.setStationary(false);
			pool2.addDest(5, 7);
			//pool2.addDest(5,6);
			pool2.setFirstDest();
			pool2.setDirectionAndImage(FACENORTH);
			pool2.getDialogue().add("...aaaaaand scratched on the 8-ball. Rack them, my serf!");
			_movingTrainers.add(pool2);
			
			Trainer pool3 = new Trainer.Rival(null);
			pool3.createHome(7, 9, 0, 0, "", "");
			pool3.setDirectionAndImage(FACESOUTH);
			pool3.getDialogue().add("Sometimes, you just gotta unwind with some pool.");
			pool3.getDialogue().add("I'm playing straight pool, since somebody stole the 8-ball again...");
			_movingTrainers.add(pool3);
			
			Trainer study = new Trainer.RedstripeShirt(null);
			study.createHome(11, 8, 0, -1, "", "");
			study.setDirectionAndImage(FACENORTH);
			study.getDialogue().add("I like studying here, I enjoy the 'clak-clak' of the pool tables.");
			_movingTrainers.add(study);
		}
		catch(IOException ioe){ioe.printStackTrace();}
	}
	
	public void createBaseRoom(){
		this.song = M2.FAUNCE;
		
		this.addTrainers();
		this.loadAllEvents();
		this.setMartVisible(false);
		this.xConstant = -16;
		this.yConstant = -20;
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Faunce Basement";
		this._roomNum = 46;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Faunce/fauncebasement.png"));
				roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Faunce/fauncebasementO.png"));
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		if(this._room == null){
			this._room = new Room(16, 14);
			Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/FaunceBasement.cmap"));
			for(int i = 0; i < 14; i++){
				for(int i2 = 0; i2 < 16; i2++){
					this._room._roomGrid[i2][i] = scan.next().charAt(0);
				}
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		g2.drawImage(roomO, null, this._xSpace, this._ySpace);
		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(yInd == 3){
			if(xInd == 13){
				super.enterRoom(_gbs.BLUE_ROOM, 24, 4, FACESOUTH);
			}
			else if(xInd == 14){
				super.enterRoom(_gbs.BLUE_ROOM, 25, 4, FACESOUTH);
			}
		}
	}
	
}
