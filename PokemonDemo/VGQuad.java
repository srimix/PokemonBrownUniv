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
public class VGQuad extends PokePanel2 {
	private BufferedImage _roomO;
	
	public VGQuad(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
	
	}

	public VGQuad(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
					Trainer grill = new Trainer.Grill(_gbs.getPlayer(), _gbs.VG_QUAD, this);
					grill.createHome(11, 11);
					_movingTrainers.add(grill);
					
					Trainer grillBro = new Trainer.BlackDude(null);
					grillBro.createHome(12, 11, 0, 0, "freeze", "freeze");
					grillBro.setDirectionAndImage(FACEEAST);
					grillBro.setStationary(false);
					grillBro.addDest(11, 11);
					grillBro.setFirstDest();
					grillBro.setStationary(false);
					grillBro.getDialogue().add("Welcome to the bro-becue!");
					grillBro.getDialogue().add("Help yourself to some food.");
					grillBro.getDialogue().add("Your Pokemon might like it too!");
					_movingTrainers.add(grillBro);
					
					Trainer newdorm = new Trainer.BrownMediumHair(null);
					newdorm.createHome(8, 15);
					newdorm.getDialogue().add("Right now you're in Vartan-Gregorian quad.");
					newdorm.getDialogue().add("But everyone just calls it 'New Dorm'.");
					_movingTrainers.add(newdorm);
				
					Trainer firstpick = new Trainer.PurpleHatGirl(null);
					firstpick.createHome(6, 12);
					firstpick.setDirectionAndImage(FACEWEST);
					firstpick.getDialogue().add("Legend has it that Brown used to have a First-Pick competition.");
					firstpick.getDialogue().add("If you won, you could choose first in the Housing Lottery.");
					firstpick.getDialogue().add("Apparently the last people to win it lived here.");
					_movingTrainers.add(firstpick);
					
					Trainer lateToJoes = new Trainer.GraySkirt(null);
					lateToJoes.createHome(7, 7, 0, 0, "drunk", "drunk");
					lateToJoes.addDest(14,18);
					lateToJoes.addDest(7,7);
					lateToJoes.setFirstDest();
					lateToJoes.setStationary(false);
					lateToJoes.getDialogue().clear();
					lateToJoes.getDialogue().add("...Must...*hiccup*...get...spicy...with...");
					lateToJoes.getDialogue().add("...Did I...forget my ID? *hiccup*...Crap.");
					_movingTrainers.add(lateToJoes);
					
					
					Trainer powerSt = new Trainer.BlueBro(null);
					powerSt.createHome(14, 21);
					powerSt.setDirectionAndImage(FACEEAST);
					powerSt.getDialogue().add("South of here is Power st. garage.");
					powerSt.getDialogue().add("It's kind of scary, plus I heard there's a really");
					powerSt.getDialogue().add("...rare but dangerous Pokemon there.");
					_movingTrainers.add(powerSt);
					
					if(_gbs.getPlayer().isGymLeaderDefeated(6) && !MainGreen.springWeekend){
						//14,7 -> 14,8
						Trainer barr = new Trainer.Barricade();
						barr.createHome(14, 7);
						_movingTrainers.add(barr);
						
						Trainer dude = new Trainer.GreenHat(null);
						dude.createHome(14, 8);
						dude.setDirectionAndImage(FACEEAST);
						dude.getDialogue().add("Sorry man. Jo's is closed until Spring Weekend is over.");
						dude.getDialogue().add("All of our cashiers are at the concert.");
						_movingTrainers.add(dude);
					}
					
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.THAYER;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=180;
		this.yConstant=346;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX = 214;
		this._mapY = 315;
		
		////CRUCIAL: Uncomment if there is a pkmn center here.
		this._pkmnCentX=11;
		this._pkmnCentY=12;
		
		//Uncomment if this place is outdoors.
		this._outdoors=true;
		
		//Uncomment if this is a key location that you can fly to.
		this._flyX=11;
		this._flyY=12;

		this.setBikeAllow(true);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "VG Quad";
		this._roomNum = _gbs.VG_QUAD;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/VGQuad/VGquad.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/VGQuad/VGquadO.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(20,25);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/VGQuad.cmap"));
		for(int i = 0; i < 25; i++){
			for(int i2 = 0; i2 < 20; i2++){
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
		if(xInd == 15){
			super.enterRoom(_gbs.JOES, 0, yInd - 4, FACEEAST);
		}
	}
	public void enterRoomSeamless(int xInd, int yInd){
		if(xInd==2 && WEST){
			super.enterRoomSeamless(_gbs.THAYER_SOUTH, xInd+16-2, yInd+86-7, FACEWEST);
		}
		
		if(yInd==24 && SOUTH){
			super.enterRoomSeamless(_gbs.THAYER_SOUTH, 17,102, FACESOUTH);
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