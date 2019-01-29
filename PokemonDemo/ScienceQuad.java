package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class ScienceQuad extends PokePanel2{
	
	private BufferedImage over;
	
	public ScienceQuad(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(0);
		
		this.createBaseRoom();
	}
	
	public ScienceQuad(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
	
		this.initializeEventVector(0);
		
		this.createBaseRoom();
	}
	
	public void addTrainers(){
		//Add PEOPLE.
		//add X Special and Full Restore
		this._movingTrainers = new Vector<Trainer>();
		
		try{
			Trainer k1 = new Trainer.BlackHairBlueDress(null);
			k1.createHome(18, 4, 0, 0, "freeze", "freeze");
			k1.addDest(19, 4);
			k1.setFirstDest();
			k1.setDirectionAndImage(FACEWEST);
			k1.setStationary(false);
			k1.getDialogue().add("\"Ne, deul-eoss geodeun. Geugeos ui ileum-eun 'Porygon' ibnida.\"");
			k1.getDialogue().add("...");
			k1.getDialogue().add("She appears to be speaking Korean.");
			k1.getDialogue().add("Wonder what a 'Porygon' is?");
			_movingTrainers.add(k1);
			
			Trainer k2 = new Trainer.BlackHair(null);
			k2.createHome(19, 4, 0, 0, "freeze", "freeze");
			k2.addDest(18, 4);
			k2.setFirstDest();
			k2.setDirectionAndImage(FACEEAST);
			k2.setStationary(false);
			k2.getDialogue().add("\"Dangsin-i saeloun Pokemon sosig deul-eu syeossjiyo?\"");
			k2.getDialogue().add("...");
			k2.getDialogue().add("He appears to be speaking Korean.");
			_movingTrainers.add(k2);
			
			Trainer scili = new Trainer.PurpleHatGirl(null);
			scili.createHome(7, 3, 0, 0, "", "");
			scili.setDirectionAndImage(FACENORTH);
			scili.getDialogue().add("'Cuz you're at the SciiiLiii...");
			scili.getDialogue().add("Concrete building that's made of coooncrete...");
			scili.getDialogue().add("Doo doo dee doo...");
			_movingTrainers.add(scili);
			
			Trainer fall = new Trainer.Backpacker(null);
			fall.createHome(3, 15, 0, -1, "avoid", "avoid");
			fall.addDest(23, 15);
			fall.addDest(23,20);
			fall.addDest(27,20);
			fall.addDest(25,13);
			fall.addDest(9,9);
			fall.addDest(3,9);
			fall.addDest(3,15);
			fall.setFirstDest();
			fall.getDialogue().add("I just love fall at Brown.");
			fall.getDialogue().add("All the leaves change colors, and everything looks so nice.");
			fall.getDialogue().add("It's definitely my second favorite thing about Brown...");
			
			if(_gbs.getPlayer().isGymLeaderDefeated(3)){
				fall.getDialogue().add("What's my first favorite thing?");
				fall.getDialogue().add("Pokemon, of course!");
			}
			
			fall.setStationary(false);
			_movingTrainers.add(fall);
			
			Trainer medidate = new Trainer.RedOveralls(null);
			medidate.createHome(13,22,92,-3,"","");
			_movingTrainers.add(medidate);
			
			Trainer text1 = new Trainer.Text();
			text1.createHome(17, 22);
			text1.getDialogue().add("A guy medidating on the rock...");
			text1.getDialogue().add("Don't wanna interrupt his zen, better leave him alone.");
			_movingTrainers.add(text1);
			
			Trainer text2 = new Trainer.Text();
			text2.createHome(18, 22);
			text2.getDialogue().add("A guy medidating on the rock...");
			text2.getDialogue().add("Don't wanna interrupt his zen, better leave him alone.");
			_movingTrainers.add(text2);
//			
//			Trainer text3 = new Trainer.Text();
//			text3.createHomeBasic(17, 21);
//			text3.getDialogue().add("A guy medidating on the rock...");
//			text3.getDialogue().add("Don't wanna interrupt his zen, better leave him alone.");
//			_movingTrainers.add(text3);
//			
//			Trainer text4 = new Trainer.Text();
//			text4.createHomeBasic(18, 21);
//			text4.getDialogue().add("A guy medidating on the rock...");
//			text4.getDialogue().add("Don't wanna interrupt his zen, better leave him alone.");
//			_movingTrainers.add(text4);
			
			Trainer russ = new Trainer.BrownGuy(null);
			russ.createHome(29, 11, -1, 0, "", "");
			russ.getDialogue().add("In Soviet Russia, Pokemon catch you!");
			_movingTrainers.add(russ);
			
			Trainer tChill = new Trainer.Text();
			tChill.createHome(24, 18);
			tChill.getDialogue().add("ZzzzZzzzZzzz...");
			_movingTrainers.add(tChill);	
			Trainer chill = new Trainer.Ponytail(null);
			chill.createHome(24,18);
			chill.setDirectionAndImage(FACEWEST);
			_movingTrainers.add(chill);
			


			if(!_gbs.getPlayer().isGymLeaderDefeated(4)){
				Trainer guide = new Trainer.Mat(null);
				guide.createHome(30, 23, 0, 0, "", "");
				guide.setDirectionAndImage(FACEEAST);
				guide.getDialogue().add("We shoot them 80 ft straight into the air.");
				_movingTrainers.add(guide);
				
				Trainer parent1 = new Trainer.BlueGirl(null);
				parent1.createHome(28, 23, 0, 0, "freeze","freeze");
				parent1.getDialogue().add("What happens to all of the chemicals?");
				parent1.setDirectionAndImage(FACEWEST);
				parent1.addDest(29, 23);
				parent1.setStationary(false);
				parent1.setFirstDest();
				_movingTrainers.add(parent1);
				
				Trainer stop = new Trainer.Text();
				stop.createHome(29,23);
				_movingTrainers.add(stop);
				
				Trainer kid = new Trainer.BlueBro(null);
				kid.createHome(28,24);
				kid.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(kid);
				
				Trainer kid2 = new Trainer.GreenHat(null);
				kid2.createHome(27, 23);
				kid2.addDest(28, 23);
				kid2.setFirstDest();
				kid2.setStationary(false);
				kid2.setDirectionAndImage(FACEWEST);
				kid2.getDialogue().add("MacMillan hall seems really cool.");
				_movingTrainers.add(kid2);
				
				Trainer parent2 = new Trainer.GlassesGuy1(null);
				parent2.createHome(27, 24);
				parent2.setDirectionAndImage(FACEWEST);
				_movingTrainers.add(parent2);
				
				Trainer kid3 = new Trainer.RedAndYellow(null);
				kid3.createHome(26, 24, 0, 0, "freeze", "freeze");
				kid3.addDest(27, 24);
				kid3.setFirstDest();
				kid3.setDirectionAndImage(FACEWEST);
				kid3.setStationary(false);
				kid3.getDialogue().add("Hey, so I heard that Orgo is really hard.");
				kid3.getDialogue().add("Is it true that nobody defeats the first time?");
				_movingTrainers.add(kid3);
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		//18,4 FACEWEST (korean1)
		//19,4 FACERAST (korean2)
		//7, 3 FACENORTH (outside scili)
		//3,15 -> 23,15 -> 23,20 -> 27,20 -> 25, 13 -> 9,9 -> 3,9 -> 3,15 (fall)
		//13,22,90, -5, "","" (meditating on rock)
		//17, 22 (text)
		//17, 21 (text)
		//18, 21 (text)
		//18, 22 (text)
		//29, 11 (russian)
		//24, 9 (chill-out)
		//TOUR GROUP
		//30, 23 FACEEAST (nicehair, tour guide)
		//28, 23 FACEWEST (parent)
		//28,24 FACEWEST (kid)
		//27, 23 FACEWEST
		//27, 24 FACEWEST
		//26, 24 FACEWEST
		
		
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Sciences Quad";
		this._roomNum = _gbs.SCIENCE_QUAD;
		
		this.xConstant = 203; //212
		this.yConstant = 403; //412
		
		this._mapX = 214;
		this._mapY = 295;
		this._flyX=23;
		this._flyY=4;
		
		this.addTrainers();
		this._martMenuVisible = false;
		
		this.loadAllEvents();
		this.grassType=1; //FALL LEAVES
		this.song = M2.SCIENCE_QUAD;
		this._outdoors=true;
		
		this.setBattleBG(NewBattleScreen.SCIENCE_QUAD);
		
		this.setBikeAllow(true);
	
		//POKEMON
		this._wildPokemon = new Vector<Pokemon>();
		
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Oddish().setWildLevel(i));
		}
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Bellsprout().setWildLevel(i));
		}
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
		}
		for(int i = 13; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
		}
		for(int i = 15; i <= 17; i++){
			this._wildPokemon.add(new Pokemon.Pidgey().setWildLevel(i));
		}
		for(int i = 10; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Mankey().setWildLevel(i));
		}
		for(int i = 10; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Meowth().setWildLevel(i));
		}
		for(int i = 14; i <= 16; i++){
			this._wildPokemon.add(new Pokemon.Rattata().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Abra().setWildLevel(7));
		for(int i = 3; i <= 7; i++){
			this._wildPokemon.add(new Pokemon.Jigglypuff().setWildLevel(i));
		}
		this._wildPokemon.add(new Pokemon.Pidgeotto().setWildLevel(17));
		
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/ScienceQuad/sciencequad.png"));
				over = ImageIO.read(this.getClass().getResource("/PokemonFiles/ScienceQuad/sciencequadO.png"));
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(35, 26);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/ScienceQuad.cmap"));
		for(int i = 0; i < 26; i++){
			for(int i2 = 0; i2 < 35; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	
	public void wild(){
		Pokemon randomEnemy = _wildPokemon.get(0);
		double random = 101*Math.random();
		
		if(random < 13.3){
			randomEnemy = _wildPokemon.get((int)(4*Math.random())); //oddish
		}
		else if(random < 26.6){
			randomEnemy = _wildPokemon.get(4+(int)(4*Math.random())); //bellsprout
		}
		else if(random < 38){
			randomEnemy = _wildPokemon.get(8+(int)(4*Math.random())); //pidgey x2
		}
		else if(random < 50){
			randomEnemy = _wildPokemon.get(12+(int)(4*Math.random()));
		}
		else if(random < 60){
			randomEnemy = _wildPokemon.get(16+(int)(3*Math.random())); //pidgey
		}
		else if(random < 68.3){
			randomEnemy = _wildPokemon.get(19+(int)(7*Math.random())); //mankey
		}
		else if(random < 77){
			randomEnemy = _wildPokemon.get(26+(int)(7*Math.random())); //meowth
		}
		else if(random < 86){
			randomEnemy = _wildPokemon.get(33+(int)(3*Math.random())); //rattata
		}
		else if(random < 91){
			randomEnemy = _wildPokemon.get(36); //abra
		}
		else if(random < 94){
			randomEnemy = _wildPokemon.get(37+(int)(4*Math.random())); //jigglypuff
		}
		else{
			randomEnemy = _wildPokemon.get(41); //pidgeotto
		}
		
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);

		this.setupBattle(ally, randomEnemy, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, _movingTrainers);
		g2.drawImage(over, null, this._xSpace, this._ySpace);
		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		if(xInd == 33 && (yInd == 14 || yInd == 15)){
			super.enterRoom(_gbs.OUTSIDE_BARHOL, 0, yInd-5, FACEEAST);
		}
		if(xInd == 23 && yInd == 3){
			super.enterRoom(_gbs.CIT_ENTRANCE, 3, 8, FACENORTH);
		}
		if(xInd == 9 && yInd == 1){
			super.enterRoom(_gbs.SCILI_LOBBY, 6, 21, FACENORTH);
		}
	}
	
	public void enterRoomSeamless(int xInd, int yInd){
		super.enterRoom(xInd, yInd);
		
		if(xInd == 1 && (yInd >= 7 && yInd <= 17)){
			super.enterRoomSeamless(_gbs.THAYER_SOUTH, 13, yInd+13, FACEWEST);
		}
	}
	
	public void Left(){
		if(!_menuVisible && !mapVisible && (_xIndex == 23 && (_yIndex <= 12 && _yIndex >=6)) || (_xIndex == 11 && _yIndex == 6) || (_xIndex == 8 && _yIndex == 6)){
			_gbs.getPlayer().faceLeft();
			return;
		}
		super.Left();
	}
	
	public void Right(){
		if(!_menuVisible && !mapVisible && (_xIndex == 22 && (_yIndex <= 12 && _yIndex >=6)) || (_xIndex == 7 && _yIndex == 6) || ( _xIndex == 10 && _yIndex == 6)){
			_gbs.getPlayer().faceRight();
			return;
		}
		super.Right();
	}
	
	
	
}
