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
public class ThayerSouth extends PokePanel2 {
	private BufferedImage _roomO;
	private final int MAC_SWIPER = 0;
	
	public ThayerSouth(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(18);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public ThayerSouth(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(18);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
		try{
			Trainer macswipe = new Trainer.Swiper(_gbs.getPlayer(), this, 5, -1);
			macswipe.createHome(31,41,1000,1000,"","");
			this.getMovingTrainers().add(macswipe); //0, final int
			
			//mama kims line 16,60, 10 to 14, 61
			Vector <Pokemon> lBelt = new Vector<Pokemon>();
			lBelt.add(new Pokemon.Vaporeon().setWildLevel(15));
			Trainer line1= new Trainer.BlackDude(lBelt);
			line1.createHome(10,61,0,0,"freeze","freeze");
			line1.setDirectionAndImage(FACEWEST);
			line1.getDialogue().add("Raghava: Man, this line is always so long.");
			line1.getDialogue().add("But I love the food too much...");
			line1.getDialogue().add("Let's battle! It's gonna be chill...and sick.");
			line1.setDefeatDialogue("Oy, that's not fun.");
			line1.setMoney(450);
			line1.setType("Mama Kim's Fanatic");
			line1.setType("Raghava");
			this.getMovingTrainers().add(line1); //1
			
			
			Trainer line2 = new Trainer.Mat(null);
			line2.createHome(13,61,0,0,"freeze","freeze");
			line2.getDialogue().add("I hope they have Ddukboki today!");
			line2.getDialogue().add("It's not on the menu, *wink*.");
			line2.setDirectionAndImage(FACEWEST);
			this.getMovingTrainers().add(line2); //2
			
			Trainer line3= new Trainer.Lorelei(null);
			line3.createHome(12,59,0,0,"freeze","freeze");
			line3.getDialogue().add("I can't be seen eating from here.");
			line3.getDialogue().add("This food truck is too mainstream.");
			line3.setDirectionAndImage(FACEWEST);
			this.getMovingTrainers().add(line3); //3
			
			Trainer line4= new Trainer.BlackHairBlueDress(null);
			line4.createHome(12,61,0,0,"freeze","freeze");
			line4.getDialogue().add("Michelle: I'll get what Rosemary's getting!");
			line4.setDirectionAndImage(FACEWEST);
			this.getMovingTrainers().add(line4); //4
			
			Trainer line5= new Trainer.BlackHairBlueDress(null);
			line5.createHome(11,61,0,0,"freeze","freeze");
			line5.getDialogue().add("Rosemary: I'll get what Michelle's getting!");
			line5.setDirectionAndImage(FACEWEST);
			this.getMovingTrainers().add(line5); //5
			
			Trainer line6= new Trainer.BrownGuy(null);
			line6.createHome(16,60,0,0,"freeze","freeze");
			line6.getDialogue().add("The truck smells so good.");
			line6.getDialogue().add("I'd be content just sitting here and inhaling.");
			line6.setDirectionAndImage(FACEEAST);
			this.getMovingTrainers().add(line6); //6
			//Get rid of line.
			if(_gbs.getPlayer().isGymLeaderDefeated(4)){
				line1.setVanishing(true);
				line2.setVanishing(true);
				line3.setVanishing(true);
				line4.setVanishing(true);
				line5.setVanishing(true);
				line6.setVanishing(true);
				line1.defeat();
				line2.defeat();
				line3.defeat();
				line4.defeat();
				line5.defeat();
				line6.defeat();
			}
			
			//tells you to surf
			Trainer milan = new Trainer.GlassesGuy1(null);
			milan.createHome(9, 8);
			milan.addDest(14,8);
			milan.addDest(9, 8);
			milan.setFirstDest();
			milan.setStationary(false);
			milan.getDialogue().add("Milan: I'd want to eat on Thayer, but its flooded.");
			milan.getDialogue().add("I'd need a Pokemon that knows Surf to get around.");
			milan.getDialogue().add("I hear a chef put out the VDub fire recently...");
			milan.getDialogue().add("I wonder how he did it?");
			
			if(!_gbs.getPlayer().isGymLeaderDefeated(5)){
				milan.setVanishing(true);
				milan.defeat();
			};
			
			this.getMovingTrainers().add(milan); //7
			
			Trainer mk = new Trainer.MamaKims(_gbs.getPlayer(), this._roomNum, this);
			mk.createHome(14, 60);
			this.getMovingTrainers().add(mk); //8
			

			
			//taming of sandshrew  24,51  27,51  27,50 
			Trainer ts1 = new Trainer.StrawHat(null);
			ts1.createHome(24,51);
			ts1.getDialogue().add("Thou must be captured by no man but me...");
			ts1.getDialogue().add("For I am he am born to tame you.");
			ts1.getDialogue().add("And bring you from a WILD Kate, to a Kate...");
			ts1.getDialogue().add("...to a Kate catchable as other household Kates.");
			ts1.setDirectionAndImage(FACEWEST);
			this.getMovingTrainers().add(ts1);  //9
			
			Trainer ts2 = new Trainer.GraySkirt(null);
			ts2.createHome(27,51);
			ts2.getDialogue().add("'Tis a wonder, by your leave, that she will be tamed so...");
			ts2.setDirectionAndImage(FACEEAST);
			this.getMovingTrainers().add(ts2); //10
			
			Trainer tss = new Trainer.Sandshrew(this);
			tss.createHome(27,50);
			tss.getDialogue().add("Sandshrew: Crroooo!!!");
			tss.setDirectionAndImage(FACENORTH);
			this.getMovingTrainers().add(tss); //11
				
			
			Trainer l1 = new Trainer.Text();
			l1.createHome(16, 56);
			l1.getDialogue().add("It's locked.");
			this.getMovingTrainers().add(l1); //12
			 
			Trainer l2 = new Trainer.Text();
			l2.createHome(18, 50);
			l2.getDialogue().add("It's locked.");
			this.getMovingTrainers().add(l2); //13
			
			Trainer bst = new Trainer.Text();
			bst.createHome(13, 57);
			bst.getDialogue().add("Benevolent St.");
			this.getMovingTrainers().add(bst); //14
			
			Trainer fp = new Trainer.FenceSwiper(_gbs.getPlayer(), this, 8);
			fp.createHome(32, 74);
			this.getMovingTrainers().add(fp); //15
			
			Trainer id = new Trainer.ItemObject(new Item.RISD_ID());
			id.createHome(30,70);
			this.getMovingTrainers().add(id);  //16
			
			//32 54, item
			Trainer item = new Trainer.ItemObject(new Item.UltraBall());
			item.createHome(32, 54);
			this.getMovingTrainers().add(item); //17
			
		}
		catch(IOException ioe) {}
	}

	public void createBaseRoom(){
		
		this.song = M2.THAYER;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=152; //298
		this.yConstant=156; //258
		this._mapX=182;
		this._mapY=321;
		this._outdoors=true;
		this.setBikeAllow(true);
		
		
		this._pkmnCentX=14;
		this._pkmnCentY=61;
		
		this._flyX=14;
		this._flyY=68;
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Thayer St. South";
		this._roomNum = _gbs.THAYER_SOUTH;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Thayer/Thayer South Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/Thayer/Thayer South Over.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void scanForAllEvents(){
		this.standardTrainerScan(this.getMovingTrainers().size());
	}
	public void loadAllEvents(){
		this.standardTrainerLoad(this.getMovingTrainers().size());
	}
	
	
	public void createGrid(){
		this._room = new Room(45,109);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/ThayerSouth.cmap"));
		for(int i = 0; i < 109; i++){
			for(int i2 = 0; i2 < 45; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawOptimalImage(g2, _roomO);

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Lincoln Field
 		if((xInd == 9) && ((yInd<=28) &&(yInd >=25))){
 			super.enterRoom(_gbs.LINCOLN_FIELD, 32, yInd-20+18, FACEWEST);
 		}
 		
		//Watson
 		if((xInd == 17) && ((yInd<=69) &&(yInd >=68))){
 			super.enterRoom(_gbs.WATSON, 11,8, FACENORTH);
 		}
 		//MacMillan
 		if((xInd == 31) && (yInd==41)){
 			super.enterRoom(_gbs.MACMILLAN_LOBBY, 4, 6, FACENORTH);
 		}
		
		//Brook Benev
 		if((xInd ==44) && ((yInd<=60) &&(yInd >=58))){
 			//super.enterRoom(_gbs.BROOK, 32, yInd-20+18, FACEWEST);
 		}
 		
 		//Brook George
 		if((xInd ==44) && ((yInd<=58) &&(yInd >=60))){
 			//super.enterRoom(_gbs.BROOK, 32, yInd-20+18, FACEWEST);
 		}
 		
 		//Charlesfield
 		if((xInd ==33) && ((yInd<=78) &&(yInd >=75))){
 			//super.enterRoom(_gbs.BROOK, 32, yInd-20+18, FACEWEST);
 		}
 		
 		//Power
 		if((xInd ==33) && ((yInd<=107) &&(yInd >=104))){
 			//super.enterRoom(_gbs.BROOK, 32, yInd-20+18, FACEWEST);
 		}
		//nice
 		if((xInd == 17) && yInd==3){
 			super.enterRoom(_gbs.BBC, 0, 1, FACEEAST);
 		}
 		
 		//To Keeney
 		if((xInd ==9) && ((yInd<=107) &&(yInd >=104))){
 			super.enterRoom(_gbs.ROUTE_1, 24, yInd-104+42, FACEWEST);
 		}
 		
 		
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		SysOut.print("X: "+ this._xSpace + ", Y: "+ this._ySpace);
		
		//Science Quad
		if(xInd == 14 && EAST && yInd <= 30 && yInd>=20){
			super.enterRoomSeamless(_gbs.SCIENCE_QUAD, 2, yInd-13, FACEEAST);
		}
		
		//VG QUAD
		if(xInd == 16 && yInd>80 && EAST){
			super.enterRoomSeamless(_gbs.VG_QUAD, xInd-16+2, yInd-86+7, FACEEAST);
		}
		//VG QUAD SOUTH
		if(yInd==102 && NORTH){
			super.enterRoomSeamless(_gbs.THAYER_NORTH, 13,24, FACENORTH);
		}
		//Waterman
		if(xInd == 0 && WEST && yInd < 10){
			super.enterRoomSeamless(_gbs.ROUTE_3, 27, yInd-6+48, FACEWEST);
		}
		
		//Thayer
		if(yInd == 1 && NORTH){
			super.enterRoomSeamless(_gbs.THAYER_NORTH, xInd-10+16, 55, FACENORTH);
		}
	}
	
	public void Up(){
		if(!_menuVisible && NORTH && (_xIndex == 31) && _yIndex == 42 && !_dialogueVisible && !this.getMovingTrainers().get(MAC_SWIPER).isDefeated()){
			this.A_Button();
			return;
		}
		super.Up();
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