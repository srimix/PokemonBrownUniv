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
public class BioMed2nd extends PokePanel2 {
//	private BufferedImage _roomSource;
	private boolean _champNotified=false;
	private boolean _paradisoGone=false;
	private final int CHAMP = 0, TEXT = 1, ITEM=3, GRAD1=4, GRAD2=5;
	
	public BioMed2nd(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
	
	}

	public BioMed2nd(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(3);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			//two grad students studying an item they get angry (maybe even challenge) when you take the item
			//a cool item
			
			try {
				
			Trainer champ = new Trainer.ChampGuy();
			
			if(!_champNotified && !_paradisoGone){
				champ.createHome(1, 5, -2, 0, "avoid", "avoid");
				champ.setDirectionAndImage(FACEEAST);
				champ.getDialogue().clear();
				champ.getDialogue().add("Meik: Whoa, that's a big drop to the first floor.");
				champ.getDialogue().add("...if Paradiso doesn't want to come over, try telling");
				champ.getDialogue().add("him he has office hours now. I'll wait inside.");
				champ.getDialogue().add(" ");
			}
			//If defeated paradiso.
			else if(_gbs.getPlayer().isGymLeaderDefeated(2)){
				champ.createHome(0, 0, 1000, 1000, "", "");
				champ.getDialogue().clear();
			}
			
			champ.setStationary(true);
			champ.setVanishing(true);
			
			
			Trainer text = new Trainer.Text();
			text.createHome(2, 1, 0, 0, "", "");
			text.setStationary(true);
			text.setVanishing(true);
			text.getDialogue().clear();
			text.getDialogue().add("It's locked.");
			

			Trainer psyduck = new Trainer.Psyduck(this);

			psyduck.createHome(1, 8, 0, 0, "", "");
			psyduck.setDirectionAndImage(FACEEAST);
			psyduck.setStationary(true);
			psyduck.getDialogue().clear();
			psyduck.getDialogue().add("Psyduck: ....Psyyyyyduck!");
			psyduck.getDialogue().add("It looks like he has a serious caffeine headache.");
			psyduck.setVanishing(false);
			
			Trainer item = new Trainer.ItemObject(new Item.Nugget());
			item.createHome(3, 9, 2, 6, "","");
			
			Trainer grad1 = new Trainer.GlassesGirl(null);
			grad1.createHome(2, 9,0,0,"vert","vert");
			grad1.setStationary(true);
			grad1.getDialogue().clear();
			grad1.setLOS(0);
			grad1.getDialogue().add("What an intruiging specimen. I shall run some Western Blots.");
			grad1.setDirectionAndImage(FACEWEST);
			
			Trainer grad2 = new Trainer.GlassesGuy1(null);
			grad2.createHome(4, 9,0,0,"freeze","freeze");
			grad2.setStationary(true);
			grad2.getDialogue().clear();
			grad2.getDialogue().add("Let's put this in the mass spectrometer and make hypotheses.");
			grad2.setDirectionAndImage(FACEEAST);
			
			this.getMovingTrainers().add(champ); //0 final int
			this.getMovingTrainers().add(text); //1 final int
			this.getMovingTrainers().add(psyduck); //2
			this._movingTrainers.add(item); //3, final int
			this._movingTrainers.add(grad1); // 4, final int
			this._movingTrainers.add(grad2); //5, final int
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void loadAllEvents(){
			Vector <Integer> firstFloor=_gbs.getPanel(_gbs.BIOMED_1).getCheckList();
			if(firstFloor.size()!= 0){

				if(firstFloor.get(0)==1){
					
					this.getMovingTrainers().get(CHAMP).defeat();
					this.getMovingTrainers().get(CHAMP).getDialogue().clear();
					_champNotified=true;
				}
				if(firstFloor.get(1)==1){
					
					this.getMovingTrainers().get(TEXT).defeat();
					_paradisoGone=true;
					Trainer champ=this.getMovingTrainers().get(CHAMP);
					champ.unDefeat();
					champ.createHome(1, 2, 0, 0, "avoid", "avoid");
					champ.setDirectionAndImage(FACENORTH);
					champ.setVanishing(false);
					champ.getDialogue().clear();
					champ.getDialogue().add("Yo, Champ in the making!");
					champ.getDialogue().add("Paradiso is the neuroscience prof, and he uses Psychic type!");
					champ.getDialogue().add("He knows all sorts of illusions to use on you and your Pokemon!");
					champ.getDialogue().add("Try not to use Fighting type, but Bug and Ghost types are useful.");
					champ.getDialogue().add("People are still waiting on their midterm grades, you can do it!");
					champ.getDialogue().add("...err, I couldn't stay in there, it's a bit dizzying inside.");
				}
			}
			
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(ITEM).defeat();
				Trainer grad1=this.getMovingTrainers().get(GRAD1);
				Trainer grad2=this.getMovingTrainers().get(GRAD2);
				grad2.getDialogue().clear();
				grad2.getDialogue().add("Not cool bro.");
				
				grad1.getDialogue().clear();
				grad1.getDialogue().add("Did you just take that research sample from us?");
				grad1.setDefeatDialogue("URGH. I'm gonna go write more grants now.");
				grad1.setType("GradStudent");
				grad1.setName("Kim");
				Pokemon Mime = new Pokemon.Mr_Mime();
				Mime.setWildLevel(12);
				grad1.setBelt(new Vector<Pokemon>());
				grad1.getBelt().add(Mime);
				grad1.setLOS(2);
				grad1.setDirectionAndImage(FACESOUTH);
			}
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(GRAD1).setVanishing(true);
				this.getMovingTrainers().get(GRAD1).defeat();
				this.getMovingTrainers().get(GRAD1).getDialogue().clear();
				this.getMovingTrainers().get(GRAD1).getDialogue().add("URGH. I'm gonna go write more grants now.");
				this.getMovingTrainers().get(GRAD2).getDialogue().clear();
				this.getMovingTrainers().get(GRAD2).getDialogue().add("Not cool bro.");
				
			}
	}
	

	public void scanForAllEvents(){
		//Is champ in the making guy notified?
		if(this._movingTrainers.get(CHAMP).isDefeated() && !_paradisoGone){
			_gbs.getPanel(28).getCheckList().set(0, 1);
		}
		if(this.getMovingTrainers().get(ITEM).isDefeated()){
			this.getCheckList().set(1,1);
			
			if(!this.getMovingTrainers().get(GRAD1).isDefeated() && this.getMovingTrainers().get(GRAD1).getBelt()==null){
				Trainer grad1=this.getMovingTrainers().get(GRAD1);
				Trainer grad2=this.getMovingTrainers().get(GRAD2);
				grad2.getDialogue().clear();
				grad2.getDialogue().add("Not cool bro.");
				
				grad1.getDialogue().clear();
				grad1.getDialogue().add("Did you just take that research sample from us?");
				grad1.setDefeatDialogue("URGH. I'm gonna go write more grants now.");
				grad1.setType("GradStudent");
				grad1.setName("Kim");
				Pokemon Mime = new Pokemon.Mr_Mime();
				Mime.setWildLevel(12);
				grad1.setBelt(new Vector<Pokemon>());
				grad1.getBelt().add(Mime);
				grad1.setLOS(2);
				grad1.setDirectionAndImage(FACESOUTH);
				
			}
		}
		if(this.getMovingTrainers().get(GRAD1).isDefeated()){
			this.getCheckList().set(2,1);
		}
		System.out.println("event0:" + this.getCheckList().get(0));
	}

	public void createBaseRoom(){
		
		this.song = M2.BIOMED;
		
		this.addTrainers();
		this.loadAllEvents();
		_dialogueVisible=false;
		
		this.xConstant=208;
		this.yConstant=40;
		this._mapX=137;
		this._mapY=240;
		
		this.setBattleBG(NewBattleScreen.PARADISO);
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "BioMed 2nd Floor";
		this._roomNum = 29;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/BioMed/BioMed 2ndFloor Background.png"));
						
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(12,11);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/BioMed2ndFloor.cmap"));
		for(int i = 0; i < 11; i++){
			for(int i2 = 0; i2 < 12; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawPlayer(g2);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
 		//BioMed 1st Floor
 		if((xInd == 9) && (yInd == 6)){
 			super.enterRoom(_gbs.BIOMED_1, 19, 8, FACEWEST);
 		}
 		
 		//Paradiso
 		if(xInd == 2 && yInd == 1){
 			super.enterRoom(_gbs.PARADISO_GYM, 2, 19, FACENORTH);
 		}
		
	}
	
	public void Up(){
		if(_xIndex==2 && _yIndex==2 && !_dialogueVisible && !_paradisoGone){
			_interruptedTrainer=TEXT;
			this.setPlayerDirection(FACENORTH);
			this.getMovingTrainers().get(TEXT).unDefeat();
			this.A_Button();
		}
		else{
			super.Up();
		}
	}
	

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}
		
		Trainer champ = this.getMovingTrainers().get(CHAMP);
		
		if(champ.getXIndex()==2 && champ.getYIndex()==1){
			champ.defeat();
			this._approachTimer.stop();

			Trainer text = this.getMovingTrainers().get(TEXT);
			text.createHome(2, 1,0,0,"","");
			champ.createHome(1,1,0,0,"avoid","avoid");
			text.unDefeat();
		}

		super.A_Button();
		
		if(!champ.isDefeated() && !_champNotified && !_paradisoGone && this._NPCpage==3 && _interruptedTrainer==CHAMP && facingNPC()){
			System.out.println("MOTION STARTED");
			
			Trainer text = this.getMovingTrainers().get(TEXT);
			champ.getDialogue().clear();
			champ.setInterrupted(false);
			champ.setPause(false);
			champ.addDest(2,1);
			champ.setFirstDest();
			champ.setStationary(false);
			this._approached=true;
			_busy=false;
			text.createHome(1,1,0,0,"","");
			text.setVanishing(true);
			text.defeat();
			this._approachTimer.start();
			_NPCpage=0;
		}
	}
}