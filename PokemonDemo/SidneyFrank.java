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
public class SidneyFrank extends PokePanel2 {
	private BufferedImage _roomO, _redON, _redOFF, _greenON, _greenOFF, _blueON, _blueOFF, _exit, _item, _ken, _first, _second;
	private boolean red=false, blue=false, green=false; 
	private final int REDt=0, GREENt=1, BLUEt=2, ITEM=3, KEN =4;
	
	public SidneyFrank(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(5);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public SidneyFrank(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(5);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			
			try {
			Trainer red = new Trainer.Text();
			red.createHome(4, 3, 0, 0, "", "");
			Trainer green = new Trainer.Text();
			green.createHome(4, 4, 0, 0, "", "");
			Trainer blue = new Trainer.Text();
			blue.createHome(4, 5, 0, 0, "", "");
			
			
			red.setStationary(true);
			green.setStationary(true);
			blue.setStationary(true);
			
			Trainer item = new Trainer.ItemObject(new Item.Potion());
			item.createHome(8, 4, 2,6, "","");
			
			Trainer ken = new Trainer.KenMiller(new Pokemon.Eevee(), _gbs);
			ken.createHome(12,4,0,0,"wander","wander");
			
			ken.addDest(11, 4);
			ken.addDest(13, 4);
			ken.addDest(12, 5);
			ken.addDest(12, 4);
			ken.setFirstDest();
			ken.setStationary(false);
			ken.setVanishing(false);
			ken.setDefeatAfterItem(true);
			
			ken.getDialogue().clear();
			ken.getDialogue().add("Your name is "+ _gbs.getPlayer().getPlayerName() + "?");
			ken.getDialogue().add("I'm Ken Miller, professor of biology and evolution theorist.");
			ken.getDialogue().add("I've worked for years spreading the word to the public about");
			ken.getDialogue().add("evolution, and this is the lab I perform my experiments in.");
			ken.getDialogue().add("My latest project is currently at the Creative Arts Center,");
			ken.getDialogue().add("but it requires high-level access authority. ");
			ken.getDialogue().add("If you're interested in evolution, you can test out my side project.");
			ken.getDialogue().add("Take this Eevee! She has a very complex evolution behavior.");
			ken.getPostItemDialogue().clear();
			ken.getPostItemDialogue().add("Find me later at the CAC. We'll talk again soon.");
			if(_gbs.getPlayer().getAllActive().size()==6){
				ken.getDialogue().add("There's no room in your belt for Eevee!");
			}
			else{
				ken.getDialogue().add("Eevee joined your party!");
			}
			//ken.setGift(null);

			
			this._movingTrainers.add(red); //0 assigned
			this._movingTrainers.add(green); //1 to 
			this._movingTrainers.add(blue); //2  final ints
			this._movingTrainers.add(item);//3 final int
			this._movingTrainers.add(ken);//4 final int
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			if(this.getCheckList().get(0)==1){
				red=true;
				SysOut.print("loaded");
			}
			if(this.getCheckList().get(1)==1){
				green=true;	
				SysOut.print("loaded");
			}
			if(this.getCheckList().get(2)==1){
				blue=true;
				SysOut.print("loaded");
			}
			if(this.getCheckList().get(3)==1){
				this.getMovingTrainers().get(ITEM).defeat();
				SysOut.print("loaded");
			}
			if(this.getCheckList().get(4)==1){
				this.getMovingTrainers().get(KEN).defeatAndPostItemize();
			}
		}
		
	}

	public void scanForAllEvents(){
		//Save the switches so you don't get locked out/in.
		if(red){
			this.getCheckList().set(0,1);
			SysOut.print("saved");
		}
		else{
			this.getCheckList().set(0,0);
		}
		if(blue){
			this.getCheckList().set(1,1);
			SysOut.print("saved");
		}
		else{
			this.getCheckList().set(1,0);
		}
		
		if(green){
			this.getCheckList().set(2,1);
			SysOut.print("saved");
		}
		else{
			this.getCheckList().set(2,0);
		}
		
		if(this.getMovingTrainers().get(ITEM).isDefeated()){
			this.getCheckList().set(3,1);
		}
		if(this.getMovingTrainers().get(KEN).isDefeated()){
			this.getCheckList().set(4,1);
		}
		
		//this.getCheckList().set(0,1);
	}

	public void createBaseRoom(){
		
		this.song = M2.BIOMED;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=60;
		this.yConstant=8;
		this._mapX=172;
		this._mapY=240;
		
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Sidney Frank";
		this._roomNum = 32;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Background.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Over.png"));
				_redON= ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Red On.png"));
				_redOFF=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Red Off.png"));
				_greenON=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Green On.png"));
				_greenOFF=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Green Off.png"));
				_blueON=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Blue On.png"));
				_blueOFF=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Blue Off.png"));
				_exit=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Exit Switch.png"));
				_item=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Item Switch.png"));
				_ken=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Ken Switch.png"));
				_first=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank First Switch.png"));
				_second=ImageIO.read(this.getClass().getResource("/PokemonFiles/SidneyFrank/SidneyFrank Second Switch.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
		this.refreshGrid();
	}

	public void createGrid(){
		this._room = new Room(16,12);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/SidneyFrank.cmap"));
		for(int i = 0; i < 12; i++){
			for(int i2 = 0; i2 < 16; i2++){
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
		
		if(red){
			g2.drawImage(_redON, null, this._xSpace, this._ySpace);
		}
		else{
			g2.drawImage(_redOFF, null, this._xSpace, this._ySpace);
		}
		
		if(green){
			g2.drawImage(_greenON, null, this._xSpace, this._ySpace);
		}
		else{
			g2.drawImage(_greenOFF, null, this._xSpace, this._ySpace);
		}
		
		if(blue){
			g2.drawImage(_blueON, null, this._xSpace, this._ySpace);
		}
		else{
			g2.drawImage(_blueOFF, null, this._xSpace, this._ySpace);
		}
		
		//Switch Logic
		//000 kei open
		if(!red && !green && !blue){
			g2.drawImage(_first, null, this._xSpace, this._ySpace);
			g2.drawImage(_second, null, this._xSpace, this._ySpace);
		}
		//001 all locked
		if(!red && !green && blue){
			g2.drawImage(_first, null, this._xSpace, this._ySpace);
			g2.drawImage(_second, null, this._xSpace, this._ySpace);
			g2.drawImage(_ken, null, this._xSpace, this._ySpace);
			g2.drawImage(_item, null, this._xSpace, this._ySpace);
			g2.drawImage(_exit, null, this._xSpace, this._ySpace);
		}
		//010 12e open
		if(!red && green && !blue){
			g2.drawImage(_ken, null, this._xSpace, this._ySpace);
			g2.drawImage(_item, null, this._xSpace, this._ySpace);
		}
		//100 12 open
		if(red && !green && !blue){
			g2.drawImage(_ken, null, this._xSpace, this._ySpace);
			g2.drawImage(_item, null, this._xSpace, this._ySpace);
			g2.drawImage(_exit, null, this._xSpace, this._ySpace);
		}
		//110 1ei open 
		if(red && green && !blue){
			g2.drawImage(_second, null, this._xSpace, this._ySpace);
			g2.drawImage(_ken, null, this._xSpace, this._ySpace);
		}
		//101 2ke open
		if(red && !green && blue){
			g2.drawImage(_first, null, this._xSpace, this._ySpace);
			g2.drawImage(_item, null, this._xSpace, this._ySpace);
		}
		//011 12k open
		if(!red && green && blue){
			g2.drawImage(_item, null, this._xSpace, this._ySpace);
			g2.drawImage(_exit, null, this._xSpace, this._ySpace);
		}
		//111 12i open
		if(red && green && blue){
			g2.drawImage(_ken, null, this._xSpace, this._ySpace);
			g2.drawImage(_exit, null, this._xSpace, this._ySpace);
		}
		

		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
	}
	
	public void firstOpen(boolean open){
		if(open){
			this._room._roomGrid[4][7] = 'F';
		}
		else{
			this._room._roomGrid[4][7] = 'N';
		}
	}
	
	public void secondOpen(boolean open){
		if(open){
			this._room._roomGrid[6][8] = 'F';
		}
		else{
			this._room._roomGrid[6][8] = 'N';
		}
			
	}
	public void itemOpen(boolean open){
		if(open){
			this._room._roomGrid[8][6] = 'F';
		}
		else{
			this._room._roomGrid[8][6] = 'N';
		}
		
	}
	public void kenOpen(boolean open){
		if(open){
			this._room._roomGrid[12][6] = 'F';
		}
		else{
			this._room._roomGrid[12][6] = 'N';
		}
		
	}
	public void exitOpen(boolean open){
		if(open){
			this._room._roomGrid[12][8] = 'F';
		}
		else{
			this._room._roomGrid[12][8] = 'N';
		}
	}
	
	
	public void refreshGrid(){
		
		//Switch Logic
		//000 kei open
		if(!red && !green && !blue){
			firstOpen(false);
			secondOpen(false);
			kenOpen(true);
			itemOpen(true);
			exitOpen(true);
		}
		//001 all locked
		if(!red && !green && blue){
			firstOpen(false);
			secondOpen(false);
			kenOpen(false);
			itemOpen(false);
			exitOpen(false);
		}
		//010 12e open
		if(!red && green && !blue){
			firstOpen(true);
			secondOpen(true);
			kenOpen(false);
			itemOpen(false);
			exitOpen(true);
		}
		//100 12 open
		if(red && !green && !blue){
			firstOpen(true);
			secondOpen(true);
			kenOpen(false);
			itemOpen(false);
			exitOpen(false);
		}
		//110 1ei open 
		if(red && green && !blue){
			firstOpen(true);
			secondOpen(false);
			kenOpen(false);
			itemOpen(true);
			exitOpen(true);
		}
		//101 2ke open
		if(red && !green && blue){
			firstOpen(false);
			secondOpen(true);
			kenOpen(true);
			itemOpen(false);
			exitOpen(true);
		}
		//011 12k open
		if(!red && green && blue){
			firstOpen(true);
			secondOpen(true);
			kenOpen(true);
			itemOpen(false);
			exitOpen(false);
		}
		//111 12i open
		if(red && green && blue){
			firstOpen(true);
			secondOpen(true);
			kenOpen(false);
			itemOpen(true);
			exitOpen(false);
		}
		
		
	}
	
	public void enterRoom(int xInd, int yInd){
		//Route3
 		if((xInd == 13 || xInd ==14) && (yInd == 11)){
 			super.enterRoom(_gbs.ROUTE_3, 18, 12, FACESOUTH);
 		}
 		//Glass Tunnel
 		if((xInd == 0) && (yInd == 3 || yInd == 4)){
 			super.enterRoom(_gbs.GLASS_TUNNEL, 10, 2, FACEWEST);
 		}
		
	}
	

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}
		
		
		
		//Special case for switches.
		if(facingNPC() && _interruptedTrainer==REDt){
			red=!red;
			//Play sound effect.
		}
		else if(facingNPC() && _interruptedTrainer==GREENt){
			green=!green;	
			//Play sound effect.
		}
		else if(facingNPC() && _interruptedTrainer==BLUEt){
			blue=!blue;	
			//Play sound effect.
		}
		else{
			//Here, the else is warranted. (otherwise, player is frozen)
			super.A_Button();
		}
		
		refreshGrid(); // Do not remove or displace.
	}
}