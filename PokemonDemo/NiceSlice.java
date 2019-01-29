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
public class NiceSlice extends PokePanel2 {
	private BufferedImage _roomO;
	
	public NiceSlice(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public NiceSlice(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				/*NEW TRAINER TEMPLATE
				 * 		
		Vector<Pokemon> belt = new Vector<Pokemon>();
		Trainer = new Trainer(belt);
		.createHome(0,0,0,0,"freeze","freeze");
		.setLOS();
		.setStationary();
		.addDest();
		.setFirstDest();
		.getDialogue().add();
		.getDialogue().add();
		.setDirectionAndImage();
		.setDefeatDialogue();
		.setMoney();
		.setType();
		.setName();
		this.getMovingTrainers().add();
		*/
				
				Trainer mart = new Trainer.MartMenu("Nice Slice", this);
				mart.createHome(2, 1);
				this._thisMartContains.add(Item.TM32_DOUBLE_TEAM);
				this._thisMartContains.add(Item.TM04_WHIRLWIND);
				this._thisMartContains.add(Item.TM07_HORN_DRILL);
				this._thisMartContains.add(Item.TM37_EGG_BOMB);
				this._thisMartContains.add(Item.TM01_MEGAPUNCH);
				this._thisMartContains.add(Item.TM09_TAKEDOWN);
				this._thisMartContains.add(Item.TM19_SEISMIC_TOSS);
				_movingTrainers.add(mart);
				
				Trainer man = new Trainer.BlackHairBlueDress(null);
				man.createHome(3, 1);
				man.setDirectionAndImage(FACEEAST);
				_movingTrainers.add(man);
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.HILLEL;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=0;
		this.yConstant=30;
		
		
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

		this.setMartVisible(false);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Nice Slice";
		this._roomNum = _gbs.NICE_SLICE;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Thayer Shops/NiceSlice.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(5,4);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/ThayerShopEast.cmap"));
		for(int i = 0; i < 4; i++){
			for(int i2 = 0; i2 < 5; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawOptimalImage(g2, _roomO);


		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//nice
 		if((xInd == 0)){
 			super.enterRoom(_gbs.THAYER_NORTH, 21, 17, FACEWEST);
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