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
public class UniversityHall extends PokePanel2 {
	private BufferedImage  _roomO;
	
	public UniversityHall(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
	
	}

	public UniversityHall(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {
				Trainer secretary = new Trainer.GreenDress(null);
				secretary.createHome(1, 1, -2, -4,"","");
				
				Trainer text = new Trainer.Text();
				text.createHome(4, 0);
				text.getDialogue().add("Secretary: Excuse me, but you aren't allowed in there right now.");
				
				Trainer champ = new Trainer.ChampGuy();
				champ.createHome(3, 1, -1, -4, "","");
				champ.getDialogue().add("Hey man! Champ-in-the-making!!!");
				champ.getDialogue().add("Everyone's super-impressed you rescued President Simmons.");
				champ.getDialogue().add("As thanks, she agreed to hold open Pokemon office-hours.");
				champ.getDialogue().add("This is your one and only chance to earn the 8th badge!");
				champ.getDialogue().add("It won't be easy though.");
				champ.getDialogue().add("Ruth's Pokemon are incredibly rare and strong.");
				champ.getDialogue().add("Your starter Pokemon might not be too much help with this one.");
				champ.getDialogue().add("That's all I can say for now, good luck!");
				
				if(ViaViaLobby.rescued){
					if(_gbs.getPlayer().isGymLeaderDefeated(8)){
						secretary.getDialogue().add("I'm sorry, President Paxson is busy right now.");
						secretary.getDialogue().add("You'll probably get a chance to see her in the sequel though...");
						secretary.getDialogue().add("Generation 2, Pokemon: BrownER Edition!");
						secretary.getDialogue().add("Tee-hee, jk, jk...");
					}
					else{
						secretary.getDialogue().add("The President will see you now. Go right ahead.");
						
						_movingTrainers.add(champ);						
					}

				}
				else if(_gbs.getPlayer().isGymLeaderDefeated(7)){
					secretary.getDialogue().add("Ummm...President Simmons isn't...here right now...");
					secretary.getDialogue().add("...She isn't answering her phone either...");
					secretary.getDialogue().add("Just come back later!");
					
					_movingTrainers.add(text);
				}
				else{
					secretary.getDialogue().add("I'm sorry, but President Simmons is busy.");
					secretary.getDialogue().add("Please try again later.");
					
					_movingTrainers.add(text);
				}
				
				_movingTrainers.add(secretary);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void createBaseRoom(){
		
		this.song = M2.UNIVERSITY_HALL;
		
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=3;
		this.yConstant=1;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=130;
		this._mapY=299;
		
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
		this.description = "University Hall";
		this._roomNum = _gbs.UNIVERSITY_HALL_LOBBY;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/UniversityHall/lobby.png"));
				_roomO = ImageIO.read(this.getClass().getResource("/PokemonFiles/UniversityHall/lobbyO.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(6,6);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/UHall.cmap"));
		for(int i = 0; i < 6; i++){
			for(int i2 = 0; i2 < 6; i2++){
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

		if(xInd == 1 && yInd == 5){
			this.enterRoom(_gbs.MAIN_GREEN, 6, 20, FACESOUTH);
		}
		
		if(xInd == 4 && yInd == 0){
			this.enterRoom(_gbs.RUTH_GYM, 10, 21, FACENORTH);
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