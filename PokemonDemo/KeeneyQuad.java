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
public class KeeneyQuad extends PokePanel2 {

	private BufferedImage _keeneyQuad, _keeneyQuadOver;
	private Graphics2D _g2;	
	
	public KeeneyQuad(GameBoyScreen gbs){
		super(gbs);
		
		this.createBase();
		//this._darkness = 250;
		

	}
	
	public KeeneyQuad(GameBoyScreen gbs, int xSpace, int ySpace, int xIndex, int yIndex, int direction){
		super(gbs, xSpace, ySpace, xIndex, yIndex, direction);
		
		this.createBase();
	}
	
	public void addTrainers(){
		
		try{
			this._movingTrainers = new Vector<Trainer>();

			
			if(_gbs.getPlayer().getPokedex() != null){
				Trainer instructor = new Trainer.GlassesGuy1(null);
				instructor.getDialogue().add("Today I'll be teaching you how to catch wild Pokemon.");
				//instructor.getDialogue().add("Oh, darn. I guess I ran out of Pokeballs. Try again later...");
				instructor.getDialogue().add("First, you need to weaken the wild Pokemon.");
				instructor.getDialogue().add("If they are asleep or confused, that helps too.");
				instructor.getDialogue().add("Then, throw a PokeBall and hope for the best.");
				instructor.getDialogue().add("Just make sure you don't try to catch other Trainer's Pokemon!");
				instructor.getDialogue().add("Good Luck! And feel free to ask me again if you need a refresher.");
				instructor.createHome(4, 6, 0, 0, "", "");
				instructor.setStationary(true);
				instructor.setFirstDest();
				_movingTrainers.add(instructor);
			}
			
			Trainer dude = new Trainer.MaroonHat(null);
			dude.createHome(7, 3, 0, 0, "avoid", "avoid");
			dude.getDialogue().clear();
			dude.getDialogue().add("Keeney Hall is the best!");
			dude.getDialogue().add("I live in Mead, 2nd floor. How about you?");
			dude.getDialogue().add("By the way, if you ever need to restore your Pokemon's HP, you can");
			dude.getDialogue().add("go take a quick nap in your bed.");
			dude.getDialogue().add("You can also use any of the free Pokemon Centers on campus.");
			dude.clearDestinations();
			dude.addDest(9, 3);
			dude.addDest(7, 6);
			dude.addDest(7, 3);
			dude.setFirstDest();
			dude.setPause(true);
			dude.setStationary(false);
			_movingTrainers.add(dude);
		}
		catch (IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		
	}
	
	public void createBaseRoom(){
		this.addTrainers();
	}
	
	public void createBase(){
		this.setBackground(Color.BLACK);
		this.addTrainers();
		this.description = "Keeney Quad";
		this._roomNum = 4;
		this.xConstant=466;
		this.yConstant=372;
		this._mapX=113;
		this._mapY=339;
		this._flyX=6;
		this._flyY=2;
		this._outdoors=true;
		this.setBattleBG(NewBattleScreen.GRASS);
		
		this.song = M2.KEENEY;
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);

		_textVisible = new boolean[3];
		this.setBikeAllow(true);
		this._wildPokemon = new Vector<Pokemon>();
		
//			for(int i = 2; i <= 5; i++)
//				this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
//
//			for(int i = 2; i <= 4; i++)
//				this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(i));
		
		this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(1));
		
		try{
			//if(GameBoyScreen.finishedLoading){
				_keeneyQuad = ImageIO.read(this.getClass().getResource("/PokemonFiles/KeeneyQuad/draft3.png"));
				//_keeneyQuad = _gbs.bgArray[_gbs.KEENEY_QUAD];
				_keeneyQuadOver = ImageIO.read(this.getClass().getResource("/PokemonFiles/KeeneyQuad/draftO2.png"));
			//}
		}
		catch (IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		
		
		
		this.createGrid();
	}
	
	public void Coord(){
		super.Coord();
		this._busy = false;
	}
	
	public void createGrid(){
		this._room = new Room(14, 12);
		
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/KeeneyQuad.cmap"));
		for(int i = 0; i < 12; i++){
			for(int i2 = 0; i2 < 14; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
				//System.out.print(_room._roomGrid[i2][i] + " ");
			}
			//SysOut.print();
		}
	}
	
	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning()){
			
			if(_xIndex == 10 && _yIndex == 9 && NORTH){
				int visNum = -1;
				for(int i = 0; i < 1; i++){
					if(_textVisible[i])
						visNum = i;
				}
				switch(visNum){
				case -1: _textVisible[0] = true;
						 _busy = true;
					 	break;
				case 0: _textVisible[0] = false;
						_busy = false;
						break;
				}
			}
			
			if(!_busy){
				this.completionCheck = false;
			}
			this.repaint();
			
		}
		
		super.A_Button();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		_g2 = (Graphics2D) g;
		
		this.drawOptimalImage(g2,_keeneyQuad);
		
		//this.drawAllTrainers(_g2, this.xConstant, this.yConstant, this._movingTrainers);
		
		this.drawPlayer(_g2);
		
		this.drawAllTrainers(_g2, this.xConstant, this.yConstant, this._movingTrainers);
		
		this.drawOptimalImage(_g2, _keeneyQuadOver);
		
		if(_textVisible[0])
			this.drawText(_g2, "Route 1: Brown St. South","");
		

		this.drawAllGenerics(_g2);

	}
	
	public void enterRoom(int xInd, int yInd){
		if((xInd == 6) && (yInd == 1)){		
			super.enterRoom(_gbs.KEENEY_HALL, 7, 4, FACENORTH);
		}
	
		if((xInd == 12) && (yInd == 9)){
			super.enterRoom(_gbs.ROUTE_1, 0, 33, FACEEAST);
		}
	}
	
	public void wild(){
		
		int random = (int) (this._wildPokemon.size()*Math.random());
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		Pokemon randomEnemy = this._wildPokemon.get(random);
		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		
		Vector<Pokemon> belt = new Vector<Pokemon>();
		belt.add(randomEnemy);
		
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	

}
