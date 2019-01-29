package PokemonDemo;

import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;


public class BlueRoom extends PokePanel2 {

//	private BufferedImage roomBG;
	private final int FRIDGE=0, CASHIER = 1;
	
	public BlueRoom(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(1);
		this.createBaseRoom();
		
	}
	
	public BlueRoom(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(1);
		this.createBaseRoom();
	}
	
	public void loadAllEvents(){
		if(this.getCheckList()!=null){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(FRIDGE).defeat();
				this.getMovingTrainers().get(CASHIER).getDialogue().add("As thanks, why don't you go grab a free drink from the fridge?");
			}
		}
	}
	public void scanForAllEvents(){
		if(this.getMovingTrainers().get(FRIDGE).isDefeated()){
			this.getCheckList().set(0, 1);
		}
	}
	
	public void addTrainers(){
	//	if(this._movingTrainers == null){
			try{
				this._movingTrainers = new Vector<Trainer>();
				
				Trainer fridgeItem = new Trainer.ItemObject(new Item.SodaPop());
				fridgeItem.createHome(1, 2, 10000, 10000, "", "");
				_movingTrainers.add(fridgeItem); //0 final int
				
				Trainer cash2 = new Trainer.BlackDude(null);
				cash2.createHome(6, 10, 0, 0, "freeze", "freeze");
				cash2.setDirectionAndImage(FACEEAST);
				cash2.addDest(5, 10);
				cash2.setFirstDest();
				cash2.getDialogue().add("Hey, I recognize you!");
				cash2.getDialogue().add("You're the guy that defeated Gail, and let everyone into the Ratty!");
				cash2.getDialogue().add("I'm so glad you did that, we were totally swamped earlier.");
				_movingTrainers.add(cash2); //So you don't feel bad about stealing the drink, LOL
				
				
				Trainer eddie = new Trainer.Text();
				eddie.createHome(28, 3, 0, 0, "", "");
				eddie.getDialogue().add("Eddie: Hey man, if you're here for a tour just relax.");
				eddie.getDialogue().add("A tour guide will probably show up soon...");
				_movingTrainers.add(eddie);
				
				Trainer jenna = new Trainer.Text();
				jenna.createHome(28, 4, 0, 0, "", "");
				jenna.getDialogue().add("Jenna: OHMYGODWHEREARETHETOURGUIDES?!?!?");
				_movingTrainers.add(jenna);
				
				Trainer comp = new Trainer.PC(_gbs);
				comp.createHome(32, 1, 0, 0, "", "");
				_movingTrainers.add(comp);
				
				/* PARENTS AND PROSPECTIVE STUDENTS */
				
				Trainer parent1 = new Trainer.GraySkirt(null);
				parent1.createHome(31, 6, 0, 0, "", "");
				parent1.setDirectionAndImage(FACEEAST);
				parent1.getDialogue().add("Are all classes taught by Pokemon masters?");
				_movingTrainers.add(parent1);
				
				Trainer parent2 = new Trainer.GlassesGuy1(null);
				parent2.createHome(30, 5, 0, 0, "", "");
				parent2.setDirectionAndImage(FACENORTH);
				parent2.getDialogue().add("I used to go to Brown...");
				parent2.getDialogue().add("...but then I took an arrow to the knee.");
				parent2.getDialogue().add("...It's a long story.");
				_movingTrainers.add(parent2);
				
				Trainer parent3 = new Trainer.Ponytail(null);
				parent3.createHome(28, 7, 0, 0, "", "");
				parent3.setDirectionAndImage(FACENORTH);
				parent3.getDialogue().add("What's the difference between Math and Applied Math?");
				parent3.getDialogue().add("Can my son study BOOOOOTH?");
				_movingTrainers.add(parent3);
				
				Trainer kid = new Trainer.BlueBro(null);
				kid.createHome(29, 7, 0,0, "", "");
				kid.setDirectionAndImage(FACESOUTH);
				kid.getDialogue().add("I have LOTS of AP credit, so I can graduate in three years, right?");
				_movingTrainers.add(kid);
				
				Trainer kid2 = new Trainer.BlueGirl(null);
				kid2.createHome(30, 4, 0, 0, "", "");
				kid2.setDirectionAndImage(FACEWEST);
				kid2.getDialogue().add("What's partying like on campus? Sssh don't let my parents hear you...");
				_movingTrainers.add(kid2);
				
				Trainer colby = new Trainer.MaroonHat(null);
				colby.createHome(29, 9, 0, 0, "", "");
				colby.setDirectionAndImage(FACESOUTH);
				colby.getDialogue().add("Hey, my name is Colby. Ready to start the tour?");
				_movingTrainers.add(colby);
				
				/**/
				
				Trainer studier = new Trainer.Female1(null);
				studier.createHome(22, 11, 0, 0, "", "");
				studier.setDirectionAndImage(FACEEAST);
				studier.getDialogue().add("I love studying here! Although it gets noisy sometimes...");
				_movingTrainers.add(studier);
				
				Trainer sign = new Trainer.Text();
				sign.createHome(7, 7, 0, 0, "", "");
				sign.getDialogue().add("Welcome to the Blue Room!");
				sign.getDialogue().add("Serving sandwiches, pastries, and sushi.");
				sign.getDialogue().add("Today's Special: Chicken Tikka Masala from Kabob and Curry!");
				_movingTrainers.add(sign);
				
				Trainer cash1 = new Trainer.BlackDude(null);
				cash1.createHome(6, 5, 0, 0, "freeze", "freeze");
				cash1.setDirectionAndImage(FACEEAST);
				cash1.addDest(5, 5);
				cash1.setFirstDest();
				cash1.getDialogue().add("Sorry, we only take meal credits after 4pm.");
				_movingTrainers.add(cash1);
				
				Trainer pastry = new Trainer.GreenHat(null);
				pastry.createHome(1, 9, 0, 0, "", "");
				pastry.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(pastry);
				
				Trainer pastryText = new Trainer.Text();
				pastryText.createHome(2, 9, 0, 0, "", "");
				pastryText.getDialogue().add("Would you like a Red Velvet whoopie pie?");
				pastryText.getDialogue().add("Or perhaps a pistachio muffin?");
				_movingTrainers.add(pastryText);
				
				Trainer sammich = new Trainer.BlueBro(null);
				sammich.createHome(1, 6, 0, 0, "", "");
				sammich.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(sammich);
				
				Trainer line1 = new Trainer.BrownGuy(null);
				line1.createHome(3, 6, 0, 0, "freeze", "freeze");
				line1.setDirectionAndImage(FACEEAST);
				line1.addDest(2, 6);
				line1.setFirstDest();
				line1.setStationary(false);
				line1.getDialogue().add("I'd like a bagel...no wait, I want a sandwich...");
				line1.getDialogue().add("...");
				line1.getDialogue().add("BAGEL SANDWICH!!!");
				_movingTrainers.add(line1);
				
				Trainer line2 = new Trainer.BlondeDude(null);
				line2.createHome(3, 5, 0, 0, "freeze", "freeze");
				line2.setDirectionAndImage(FACENORTH);
				line2.addDest(3,6);
				line2.setFirstDest();
				line2.setStationary(false);
				line2.getDialogue().add("The guy in front of me can't seem to make up his mind.");
				_movingTrainers.add(line2);
				
				Trainer line3 = new Trainer.ChubbyGuy(null);
				line3.createHome(3, 4, 0, 0, "freeze", "freeze");
				line3.setDirectionAndImage(FACENORTH);
				line3.addDest(3, 5);
				line3.setFirstDest();
				line3.setStationary(false);
				line3.getDialogue().add("Oh, I just love Odwalla. I wish they still had Mango-Limeade...");
				_movingTrainers.add(line3);
				
				Trainer line4 = new Trainer.GreenDress(null);
				line4.createHome(3, 3, 0, 0, "freeze", "freeze");
				line4.setDirectionAndImage(FACENORTH);
				line4.addDest(3, 4);
				line4.setFirstDest();
				line4.setStationary(false);
				line4.getDialogue().add("Ugh, this line is taking FOREVER. Maybe I'll just get sushi...");
				_movingTrainers.add(line4);
				
				Trainer poolGuy = new Trainer.YellowHatBoy(null);
				poolGuy.createHome(24, 7, 0, 0, "", "");
				poolGuy.setDirectionAndImage(FACENORTH);
				poolGuy.getDialogue().add("Playing on the pool tables downstairs is fun, but I'm worn out.");
				poolGuy.getDialogue().add("Maybe I should grab a soda from the Campus Market.");
				_movingTrainers.add(poolGuy);
				
				Trainer lookingOutWindow = new Trainer.RedBandanaGirl(null);
				lookingOutWindow.createHome(13, 1, 0, 0, "freeze", "freeze");
				lookingOutWindow.setDirectionAndImage(FACESOUTH);
				lookingOutWindow.addDest(13, 0);
				lookingOutWindow.setFirstDest();
				lookingOutWindow.getDialogue().add("Hmmm? Not now, I'm people-watching.");
				_movingTrainers.add(lookingOutWindow);
				
				Trainer walker = new Trainer.RedstripeShirt(null);
				walker.createHome(10, 8, 0, 0, "wander", "wander");
				walker.addDest(21, 8);
				walker.addDest(10, 8);
				walker.setDirectionAndImage(FACEWEST);
				walker.setFirstDest();
				walker.setStationary(false);
				walker.getDialogue().add("It's super lame they don't take meal credits until 4.");
				walker.getDialogue().add("I'll just have to wait it out.");
				_movingTrainers.add(walker);
				
				Trainer water = new Trainer.Text();
				water.createHome(21, 9, 0, 0, "", "");
				water.getDialogue().add("A bottle refill station, courtesy of Beyond the Bottle!");
				_movingTrainers.add(water);
				
				Trainer rando = new Trainer.BlackGirl(null);
				rando.createHome(18,11, 0, 0, "freeze", "freeze");
				rando.addDest(17,11);
				rando.setFirstDest();
				rando.setDirectionAndImage(FACEWEST);
				rando.setStationary(false);
				rando.getDialogue().add("I have class in Smitty B, but that's SO far away :(");
				_movingTrainers.add(rando);
				
				Trainer rando2 = new Trainer.Scientist(null);
				rando2.createHome(17, 11, 0, 0, "freeze", "freeze");
				rando2.addDest(18,11);
				rando2.setFirstDest();
				rando2.setDirectionAndImage(FACEEAST);
				rando2.setStationary(false);
				rando2.getDialogue().add("She just needs to get a Pokemon that knows CUT...");
				rando2.getDialogue().add("...then she could bypass Olive Pit.");
				_movingTrainers.add(rando2);
				
				
			}
			catch(IOException ioe){ioe.printStackTrace();}
	//	}
	}
	
	public void createBaseRoom(){
		
		this.song = M2.FAUNCE;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant = 2;
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Blue Room";
		this._roomNum = 45;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/Faunce/faunceBG.png"));
				
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
			this._room = new Room(35, 14);
			Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BlueRoom.cmap"));
			for(int i = 0; i < 14; i++){
				for(int i2 = 0; i2 < 35; i2++){
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
		//g2.drawImage(roomO, null, this._xSpace, this._ySpace);
		
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(yInd == 12){
			if(xInd == 8){
				super.enterRoom(_gbs.MAIN_GREEN, 17, 3, FACESOUTH);
			}
			else if(xInd == 9){
				super.enterRoom(_gbs.MAIN_GREEN, 18, 3, FACESOUTH);
			}
			else if(xInd == 24){
				super.enterRoom(_gbs.MAIN_GREEN, 22, 3, FACESOUTH); 
			}
			else if(xInd == 25){
				super.enterRoom(_gbs.MAIN_GREEN, 23, 3, FACESOUTH);
			}
		}
		else if(yInd == 4){
			if(xInd == 24){
				super.enterRoom(_gbs.FAUNCE_BASEMENT, 13, 3, FACESOUTH);
			}
			else if(xInd == 25){
				super.enterRoom(_gbs.FAUNCE_BASEMENT, 14, 3, FACESOUTH);
			}
		}
	}
}
