package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class PrinceLab extends PokePanel2 {

	private BufferedImage _roomO;
	
	public PrinceLab(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(2);
		this.createBaseRoom();
	}
	
	public PrinceLab(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);

		this.initializeEventVector(2);
		this.createBaseRoom();
	}
	
	public void scanForAllEvents(){
		if(_movingTrainers.get(0).isDefeated()){
			this.getCheckList().set(0, 1);
		}
		if(_movingTrainers.get(1).isDefeated()){
			this.getCheckList().set(1, 1);
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0) == 1){
				_movingTrainers.get(0).defeat(); //Old Amber
			}
			if(this.getCheckList().get(1) == 1){
				_movingTrainers.get(1).defeatAndPostItemize(); //Henry
				_movingTrainers.get(1).setGift(null);
			}
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		try {
			Trainer aero = new Trainer.ItemObject(new Item.Old_Amber());
			aero.createHome(3, 4, 2,6,"","");
			_movingTrainers.add(aero);
			
			Vector<Pokemon> hbBelt = new Vector<Pokemon>();
			hbBelt.add(new Pokemon.Zubat().setWildLevel(30));
			hbBelt.add(new Pokemon.Zubat().setWildLevel(30));
			hbBelt.add(new Pokemon.Pidgeotto().setWildLevel(32));
			Trainer henry = new Trainer.HenryBruce(hbBelt);
			henry.createHome(1, 4,0,0,"freeze","freeze");
			henry.setDirectionAndImage(FACEEAST);
			henry.setGift(new Item.HM02_Fly());
			henry.addDest(0, 4);
			henry.setFirstDest();
			henry.getDialogue().add("Hey! Here to check out the bat lab?");
			henry.getDialogue().add("Ah, I see you're here to pick up the HM my colleague requested.");
			henry.getDialogue().add("Well, first I need to make sure you can handle it.");
			henry.getDialogue().add("I've spent countless hours analyzing flight in Pokemon.");
			henry.getDialogue().add("Let's see if you've got what it takes!");
			henry.setDefeatDialogue("Hmmm, better run more tests.");
			henry.getPostBattleDialogue().add("Well done! As promised, here it is.");
			henry.getPostItemDialogue().add("HM:02 contains Fly. Your Pokemon will soar into the sky...");
			henry.getPostItemDialogue().add("...then swoop down and attack from above.");
			henry.getPostItemDialogue().add("Outside of battle, you can use it to revisit places you've been.");
			henry.getPostItemDialogue().add("Now if you don't mind, I need to get back to my thesis...");
			henry.setType("Researcher");
			henry.setName("Henry");
			henry.setMoney(2012);
			_movingTrainers.add(henry);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}

public void createBaseRoom(){
	
	this.song = M2.PRINCE;
	
	this.addTrainers();
	this.loadAllEvents();
	
	this.xConstant=4;
	this.yConstant=0;
	this.setBattleBG(NewBattleScreen.BARHOL_GYM);
	
	
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
	this.description = "Prince Labs";
	this._roomNum = _gbs.PRINCE_LAB;
	try{
		if(GameBoyScreen.finishedLoading){
			_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/PrinceLab/prince.png"));
			_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/PrinceLab/princeO.png"));
		}
		
	} catch(IOException ioe){
		ioe.printStackTrace();
		System.exit(0);
	}
	this.createGrid();
}

public void createGrid(){
	this._room = new Room(21,11);
	Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/PrinceLab.cmap"));
	for(int i = 0; i < 11; i++){
		for(int i2 = 0; i2 < 21; i2++){
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

	if(yInd == 10){
		this.enterRoom(_gbs.OUTSIDE_BARHOL, 3, 6, FACESOUTH);
	}
	if(xInd == 20){
		this.enterRoom(_gbs.BARHOL_HALLWAY, 10, 1, FACEEAST);
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
