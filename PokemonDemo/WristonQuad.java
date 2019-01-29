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
public class WristonQuad extends PokePanel2 {
	private BufferedImage  _roomO;
	public WristonQuad(GameBoyScreen gbs){
		super(gbs);
		_xSpace = 0;
		_ySpace = 0;
		_xIndex = 1;
		_yIndex = 7;
		
		this.createBaseRoom();
	}

	public WristonQuad(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.createBaseRoom();
	}
	
	public void addTrainers(){
		try{
			
			this._movingTrainers = new Vector<Trainer>();
			
			this._thisMartContains.clear();
			this._thisMartContains.add(1); //organic potion
			this._thisMartContains.add(23); //FairTradePokeBall
			this._thisMartContains.add(29); //Holistic Antidote
			this._thisMartContains.add(31); //Holistic Paralyz Heal
			
			if(!_gbs.getPlayer().isGymLeaderDefeated(1)){
				Trainer line1 = new Trainer.BlueGirl(null);
				line1.getDialogue().add("Uggghhhh, I've been waiting here FOREVER!");
				line1.setCurrentImage(line1.getRightImage());
				line1.createHome(16, 5, 0, 2, "freeze", "freeze");
				line1.addDest(17, 5);
				line1.setFirstDest();
				line1.setStationary(false);
				
				Trainer line2 = new Trainer.BlueBro(null);
				line2.getDialogue().add("I don't think Gail is swiping people into the Ratty.");
				line2.getDialogue().add("She yelled something about wanting a decent challenge and stormed off.");
				line2.setCurrentImage(line2.getRightImage());
				line2.createHome(15, 5, 0, 2, "freeze", "freeze");
				line2.addDest(16, 5);
				line2.setFirstDest();
				line2.setStationary(false);
				
				Trainer line3 = new Trainer.RedAndYellow(null);
				line3.getDialogue().add("C'mon...I have class in 5 minutes!");
				line3.setCurrentImage(line3.getRightImage());
				line3.createHome(14, 5, 0, 2, "freeze", "freeze");
				line3.addDest(15, 5);
				line3.setFirstDest();
				line3.setStationary(false);
				
				Trainer ivyguy = new Trainer.BlackGirl(null);
				ivyguy.getDialogue().add("Can I just...excuse me...to please...");
				ivyguy.getDialogue().add("I just want to go the Ivy Room, but I can't get through!");
				ivyguy.setCurrentImage(ivyguy.getBackImage());
				ivyguy.setStationary(false);
				ivyguy.createHome(14, 6, 0, 2, "avoid", "avoid");
				ivyguy.addDest(14, 5);
				ivyguy.setFirstDest();
				
				Trainer helpful = new Trainer.GlassesGuy1(null);
				helpful.getDialogue().add("Nobody can get into the Ratty from Wriston.");
				helpful.getDialogue().add("I heard there was another entrance on the South side of the building.");
				helpful.getDialogue().add("But I'm too scared to go through Patriot's Court...");
				helpful.createHome(13, 8, 0, 2, "", "");
				helpful.setStationary(true);
				
				Trainer sign = new Trainer.Sign();
				sign.createHome(8, 8, 0, 0, "", "");
				sign.getDialogue().add("Farmers Market!");
				sign.getDialogue().add("Organic and Fair-Trade products available at our Mart.");
				sign.getDialogue().add("Free Holistic Massages for your Pokemon at our Massage Center.");

				Trainer lock = new Trainer.Text();
				lock.createHome(17, 8, 0, 0, "", "");
				lock.getDialogue().add("The door is locked.");
				
				Trainer mart = new Trainer.MartMenu("Wriston Farmer's Market", this);
				mart.createHome(6, 7, 0, 0, "", "");
				
				Trainer pkmnCenter = new Trainer.PokemonCenter(_gbs.getPlayer(), _gbs.WRISTON_QUAD, this);
				pkmnCenter.createHome(7, 7, 0, 0, "", "");
				
				_movingTrainers.add(line1);
				_movingTrainers.add(line2);
				_movingTrainers.add(line3);
				_movingTrainers.add(ivyguy);
				_movingTrainers.add(helpful);
				_movingTrainers.add(sign);
				_movingTrainers.add(lock);
				_movingTrainers.add(mart);
				_movingTrainers.add(pkmnCenter);

			}
			else if(_gbs.getPlayer().isGymLeaderDefeated(1)){
				//Add only the mart.
				if(_gbs.getPlayer().getPkmnCenter()==this._roomNum){
					Trainer mart = new Trainer.MartMenu("Wriston Farmer's Market", this);
					mart.createHome(6, 7, 0, 0, "", "");
					
					Trainer pkmnCenter = new Trainer.PokemonCenter(_gbs.getPlayer(), _gbs.WRISTON_QUAD, this);
					pkmnCenter.createHome(7, 7, 0, 0, "", "");
					
					Trainer sign = new Trainer.Sign();
					sign.createHome(8, 8, 0, 0, "", "");
					sign.getDialogue().add("Farmers Market!");
					sign.getDialogue().add("Organic and Fair-Trade products available at our Mart.");
					sign.getDialogue().add("Free Holistic Massages for your Pokemon at our Massage Center.");
					
					_movingTrainers.add(mart);
					_movingTrainers.add(pkmnCenter);
					_movingTrainers.add(sign);
				}
				else{
					Trainer sadgirl = new Trainer.BlackGirl(null);
					sadgirl.createHome(7,7,0,0,"avoid","avoid");
					sadgirl.getDialogue().add("Oh, you defeated Gail?");
					sadgirl.getDialogue().add("Thank you, but I already grabbed lunch from the farmer's market.");
					sadgirl.addDest(8, 8);
					sadgirl.addDest(6, 6);
					sadgirl.addDest(7,7);
					sadgirl.setFirstDest();
					sadgirl.setStationary(false);
					_movingTrainers.add(sadgirl);
				}
				
				Trainer frat = new Trainer.ChubbyGuy(null);
				frat.createHome(17, 10, 0, 0, "", "");
				frat.getDialogue().add("Ahhh, I feel much better.");
				frat.getDialogue().add("Nothing like Ratty food to cure what ails ya!");
				frat.setStationary(true);
				_movingTrainers.add(frat);
				
				
				Trainer pledger1 = new Trainer.BlondeDude(null);
				pledger1.createHome(9, 4, 0, -2, "freeze","freeze");
				pledger1.setCurrentImage(pledger1.getLeftImage());
				pledger1.addDest(8, 4);
				pledger1.setFirstDest();
				pledger1.setStationary(false);
				pledger1.getDialogue().add("I think I want to join a frat.");
				pledger1.getDialogue().add("Which is the cool one?");
				
				Trainer pledger2 = new Trainer.MaroonHat(null);
				pledger2.createHome(8, 4, 0, 0, "freeze","freeze");
				pledger2.setCurrentImage(pledger2.getRightImage());
				pledger2.addDest(9, 4);
				pledger2.setFirstDest();
				pledger2.getDialogue().add("Are you rushing D Tau?");
				pledger2.setStationary(false);
				
				Trainer wanderer = new Trainer.Pirate(null);
				wanderer.createHome(5, 8, 0, 0, "wander", "wander");
				wanderer.addDest(7, 6);
				wanderer.addDest(5, 8);
				wanderer.setFirstDest();
				wanderer.getDialogue().add("ARRRRRRRch sings are a lot of fun!");
				wanderer.getDialogue().add("A Capella groups on campus perform in Wayland Arch for free.");
				wanderer.setStationary(false);
				
				_movingTrainers.add(pledger1);
				_movingTrainers.add(pledger2);
				_movingTrainers.add(wanderer);
			}
			
			
			Trainer patriotSign = new Trainer.Sign();
			patriotSign.createHome(10, 11, 0, 0, "", "");
			patriotSign.getDialogue().add("SOUTH: Patriot's Court.");
			patriotSign.getDialogue().add("Caution! Frat boys and wild Pokemon!");
			patriotSign.setStationary(true);
			_movingTrainers.add(patriotSign);
		}
		catch(IOException ioe){}
		
	}

	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Wriston Quad";
		this._roomNum = 6;
		this.addTrainers();
		this.setMartVisible(false);
		this.song = M2.WRISTON;
		this._outdoors=true;

		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.xConstant = 3;
		this.yConstant = 19;
		this._mapX=172;
		this._mapY=321;
		this._flyX=16;
		this._flyY=8;
		this._pkmnCentX=7;
		this._pkmnCentY=8;
		
		_textVisible = new boolean[4];
		this.setBikeAllow(true);
		try{
			if(GameBoyScreen.finishedLoading){
				if(_gbs.getPlayer().isGymLeaderDefeated(1)){
					_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/WristonQuad/wriston1New.png"));
				//	_roomSource = _gbs.bgArray[_gbs.WRISTON_QUAD];
					_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/WristonQuad/wriston1NewO.png"));
				}
				else if(_gbs.getPlayer().isGymLeaderDefeated(1) && _gbs.getPlayer().getPkmnCenter()==this._roomNum){
					_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/WristonQuad/wriston3New.png"));
					_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/WristonQuad/wriston1NewO.png"));
				}
				else{
					_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/WristonQuad/wriston2New.png"));
					_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/WristonQuad/wriston2NewO.png"));
				}
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(24,14);
		Scanner scan;
		if(!_gbs.getPlayer().isGymLeaderDefeated(1)){
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/wriston1.cmap"));
		}
		else if(_gbs.getPlayer().isGymLeaderDefeated(1) && _gbs.getPlayer().getPkmnCenter()==this._roomNum){
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/wriston3.cmap"));
		}
		else{
			scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/wriston2.cmap"));
		}
		for(int i = 0; i < 14; i++){
			for(int i2 = 0; i2 < 24; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);		g2.drawImage(_roomO, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		//this.drawBox(g2);

		if(_textVisible[0]){
			this.drawText(g2, "A mysterious one-armed ", "Pokemon statue...");
		}

		if(_textVisible[1]){
			this.drawText(g2, "uuuuggggghhh. I feel TERRIBLE...", "");
		}
		
//		if(_textVisible[2]){
//			this.drawText(g2, "Patriot's Court:", "Frat boys and wild encounters.");
//		}
		
		if(_textVisible[3]){
			this.drawText(g2, "uuuuggggghhh. I STILL feel terrible.", "");
		}

		this.drawAllGenerics(g2);
		
	}

	public void blackout(){
		this.blackout(_pkmnCentX,_pkmnCentY,FACENORTH);	
		_gbs.getPlayer().healAllActive();
	}
	
	public void enterRoom(int xInd, int yInd){

		//Wayland Arch
		if((xInd == 1) && (yInd == 7)){
			super.enterRoom(_gbs.WAYLAND_ARCH, 8, 2, FACEWEST);
		}
		
		//Patriots entrance
		if(((xInd == 10) || (xInd == 11)) && (yInd == 13)){
			super.enterRoom(_gbs.PATRIOTS_COURT, 3, 0, FACESOUTH);
		}
		
		//Patriots Exit
		if( (xInd == 21||xInd == 20||xInd == 22) && yInd == 13){
			super.enterRoom(_gbs.PATRIOTS_COURT, 19, 0, FACESOUTH);
		}
		
		
		//Ratty Entrance - Pre Gail
		if(xInd == 21 && yInd == 10){
			super.enterRoom(_gbs.RATTY_ENTRANCE, 9, 8, FACENORTH);
		}
		
		//Ratty Entrance - Post Gail
		if(xInd == 17 && yInd == 8){
			super.enterRoom(_gbs.RATTY_ENTRANCE, 9, 8, FACENORTH);
		}
		
		if(xInd == 17 && yInd == 5){
			super.enterRoom(_gbs.RATTY_ENTRANCE, 1, 8, FACENORTH);
		}
		
	}

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning()){

			if(_xIndex == 11 && _yIndex == 8 && NORTH){
				_textVisible[0] = !_textVisible[0];
				_busy = !_busy;
			}
			if(_xIndex == 16 && _yIndex == 9 && SOUTH){
				_textVisible[1] = !_textVisible[1];
				_busy = !_busy;
			}			
//			if((_xIndex == 10 && _yIndex == 10 && SOUTH)||(_xIndex == 11 && _yIndex == 11 && WEST)||(_xIndex == 10 && _yIndex == 12 && SOUTH)){
//				_textVisible[2] = !_textVisible[2];
//				_busy = !_busy;
//			}
			if(_xIndex == 16 && _yIndex == 11 && NORTH){
				_textVisible[3] = !_textVisible[3];
				_busy = !_busy;
			}			
			
			
			
			if(!_busy){
				this.completionCheck = false;
			}

			this.repaint();
		}
		
			super.A_Button();
		
	}
}