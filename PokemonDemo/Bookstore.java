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
public class Bookstore extends PokePanel2 {
	private BufferedImage /*_roomSource,*/ _roomO;
	
	public static boolean corrupt = true;
	
	public Bookstore(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public Bookstore(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		
		this.createBaseRoom();
		
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size() != 0){
			if(this.getCheckList().get(0)==1){
				corrupt = false;
			}
		}
	}
	
	public void scanAllEvents(){
		if(!corrupt){
			this.getCheckList().set(0,1);
		}
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
		
				Trainer poke = new Trainer.PokemonCenter(_gbs.getPlayer(), _gbs.BOOKSTORE, this);
				poke.createHome(11, 5);
				_movingTrainers.add(poke);
				
				Trainer girl = new Trainer.BlueBro(null);
				girl.createHome(11, 4, 2, 0, "","");
				_movingTrainers.add(girl);

				//Saffron City Mart
				this._thisMartContains.clear();
//				this._thisMartContains.add(Item.GREAT_BALL);
//				this._thisMartContains.add(Item.PARALYZ_HEAL);
//				this._thisMartContains.add(Item.REVIVE);
//				this._thisMartContains.add(Item.MAX_REPEL);
//				this._thisMartContains.add(Item.HYPER_POTION);
//				this._thisMartContains.add(Item.FULL_HEAL);
//				this._thisMartContains.add(Item.ESCAPE_ROPE);
				this._thisMartContains.add(Item.FIRESTONE);
				this._thisMartContains.add(Item.THUNDERSTONE);
				this._thisMartContains.add(Item.LEAFSTONE);
				this._thisMartContains.add(Item.MOONSTONE);
				this._thisMartContains.add(Item.WATERSTONE);
				Trainer mart = new Trainer.MartMenu("Brown Bookstore: Class Rings",this);
				mart.createHome(18, 4);
				_movingTrainers.add(mart);
				
				Trainer martPerson = new Trainer.Elder(null);
				martPerson.createHome(17,4);
				martPerson.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(martPerson);
				
				
				Trainer blueState = new Trainer.BlueGirl(null);
				blueState.createHome(11, 0,0,0,"avoid","avoid");
				blueState.addDest(11, 2);
				blueState.addDest(11,1);
				blueState.addDest(12, 1);
				blueState.addDest(11,1);
				blueState.addDest(11,0);
				blueState.setPause(false);
				blueState.setStationary(false);
				blueState.setFirstDest();
				_movingTrainers.add(blueState);
				
				Trainer cop1 = new Trainer.Cop(null);
				cop1.createHome(21, 6,0,0,"freeze","freeze");
				cop1.setDirectionAndImage(FACEWEST);
				if(corrupt){
					cop1.setLOS(3);
					cop1.getDialogue().add("Hey, you can't just walk in here. This is a stakeout!");
					cop1.getDialogue().add("Oh, I see. You're here to help.");
					cop1.getDialogue().add("My partner and I have been investigating this crime ring.");
					cop1.getDialogue().add("Go downstairs, defeat the mafioso thugs, and end this scandal!");
					cop1.getDialogue().add("Oh and you need to defeat them all at once.");
					cop1.getDialogue().add("My partner will explain more. I'll send word to him you're coming.");
					cop1.getDialogue().add("Better make sure all your Pokemon are ready to go first.");
				}
				else{
					cop1.getDialogue().add("Good work kid! Unfortunately it turns out the textbooks...");
					cop1.getDialogue().add("...are just mad expensive anyways. Sorry about that.");
				}
				_movingTrainers.add(cop1);
				
				Trainer pc1 = new Trainer.PC(_gbs);
				pc1.createHome(14, 6);
				_movingTrainers.add(pc1);
				
				Trainer pc2 = new Trainer.PC(_gbs);
				pc2.createHome(15,6);
				_movingTrainers.add(pc2);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.FAUNCE;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=2;
		this.yConstant=30;
		
		this.setMartVisible(false);
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=192;
		this._mapY=270;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		this._pkmnCentX=11;
		this._pkmnCentY=6;
		
		//Uncomment if this place is outdoors.
		//this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		//this._flyX=;
		//this._flyY=;

		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Brown Bookstore";
		this._roomNum = _gbs.BOOKSTORE;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Bookstore/bookstoreLobby.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Bookstore/bookstoreLobbyO.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(28,13);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/Bookstore.cmap"));
		for(int i = 0; i < 13; i++){
			for(int i2 = 0; i2 < 28; i2++){
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
		if((xInd == 22 || xInd == 23) && yInd == 3){
			this.enterRoom(_gbs.BOOKSTORE_BASEMENT,23,4,FACESOUTH);
			_gbs.getPlayer().setPkmnCenter(_gbs.BOOKSTORE);
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