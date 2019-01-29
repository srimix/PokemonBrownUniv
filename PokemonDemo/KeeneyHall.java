package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class KeeneyHall extends PokePanel2{

	private BufferedImage /*_keeneyHall,*/ _keeneyHallOver;
	private boolean _doorClosed=true;
	private Trainer _meik, _female;
	private ImageIcon i;
	
	//Coordinates of Meik
	
	public KeeneyHall(GameBoyScreen gbs){
		super(gbs);
		
		this.createBaseRoom
		();
		//this._darkness =  250;
		

	}
	
	public KeeneyHall(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.createBaseRoom();
		
//		try {
//			_meik = new Trainer.Meiklejohn(null);
//			_female = new Trainer.Female1(null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this._movingTrainers =new Vector<Trainer>();
//		
//		_female.getDialogue().add("Ooh, me next!");
//		_female.setStationary(true);
//		_female.createHome(4,2,0,0,"freeze", "freeze");
//		
//
//		_meik.setVanishing(true);
//		_meik.defeat();
//		this._movingTrainers.add(_meik);	
//		this._movingTrainers.add(_female);
		

	}
	
	public void addTrainers(){
		try {
			_meik = new Trainer.Meiklejohn(null);
			_female = new Trainer.Female1(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this._movingTrainers =new Vector<Trainer>();

		_female.getDialogue().add("Ooh, me next!");
		_female.setStationary(true);
		_female.createHome(4,2,0,0,"freeze","freeze");
		
		this._movingTrainers.add(_meik);
		this._movingTrainers.add(_female);
		if(_gbs.getPlayer().getAllActive().size() == 0){
			SysOut.print("THIS IS TRUE!!!");
			_meik.createHome(12,1,0,0,"vert","horiz");
			_meik.getDialogue().add("You can't go outside without a Pokemon.  It's dangerous!");
			_meik.getDialogue().add("Come into my room to get your first Pokemon!");
			_meik.setDirectionAndImage(0);
			
			_meik.setVanishing(true);
			_meik.defeat();
			_meik.setPause(false);
			//For following into the door.
			this.setPlayerDest(12, 1);
			_gbs.getPlayer().setAvoidMethod("horiz");
			_gbs.getPlayer().setIgnoring(true);			
			
		}
		else{
			_meik.setVanishing(true);
			_meik.defeat();
			_doorClosed = false;
		}
	}
	
	public void createBaseRoom(){
		this._approached = false;
		this.setBackground(Color.BLACK);
		this.description = "Keeney Hall";
		this._roomNum = 3;
		this.xConstant=83;
		this.yConstant=119;
		this._mapX=113;
		this._mapY=339;
		
		this.addTrainers();
		
		//Once you have your starter, you should always be allowed to enter.
		if(_gbs.getPlayer().getAllPokemon().size()>0){
			_doorClosed=false;
		}
		
		this.song = M2.KEENEY;
		
		_textVisible = new boolean[8];
		
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/KeeneyHall/KeeneyHallNew.png"));
				//_keeneyHall = _gbs.bgArray[_gbs.KEENEY_HALL];
				_keeneyHallOver = ImageIO.read(this.getClass().getResource("/PokemonFiles/KeeneyHall/KeeneyHallOver.png"));
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}

		
		this.createGrid();
	}
	
	public void createGrid(){
		
		this._room = new Room(17,5);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/KeeneyHall.cmap"));
		for(int i = 0; i < 5; i++){
			for(int i2 = 0; i2 < 17; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
//		if(_gbs.getPlayer()._activePokemon.size() > 0 || !_doorClosed){
//			//Door to meik is locked
//			_room._roomGrid[12][1] = 'D';
//		}
		if(_doorClosed){
		//Door to meik is locked
		_room._roomGrid[12][1] = 'N';
		}
		
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		//g2.drawImage(_keeneyHall, null, this._xSpace-30, this._ySpace+1);		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace-70, this._ySpace+1);
		
		if(_doorClosed && _gbs.getPlayer().getAllActive().size() == 0){
			g2.drawImage(_keeneyHallOver, null, this._xSpace-70, this._ySpace+1);
		}
		
		if(i == null)
			i = new ImageIcon(this.getClass().getResource("/PokemonFiles/KeeneyHall/KeeneyBeerPong.gif"));
		i.paintIcon(this, g2, this._xSpace+102, this._ySpace+177);
		
		if(_textVisible[0])
			this.drawText(g2, "You knock, but no response.");
		
		if(_textVisible[1])
			this.drawText(g2, "Not now chief, I'm in the zone...");
		
		if(_textVisible[2])
			this.drawText(g2, "Party room! Party Room!", "");
		
		if(_textVisible[3])
			this.drawText(g2, "WAIT!!", "");
			
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		
		this.drawPlayer(g2);
		this.drawAllGenerics(g2);
		
	}
	
	public void Down(){
		if(this._menuVisible){super.Down(); return;}
		
		else{
			if(!_busy && this._yIndex == 3 && (this._xIndex == 7 || this._xIndex == 8) && _gbs.getPlayer()._activePokemon.size() == 0){
				
				if(!this._textVisible[3]){
					this._textVisible[3] = true;
					this._doorClosed=false;
					this._room._roomGrid[12][1]='D';
					M2.playFX(M2.RA_INTRO);
				}
				
				this.repaint();
			}
			else{
				super.Down();
			}
			
				this.repaint();
			}
		}
	
	public void enterRoom(int xInd, int yInd){
		if((xInd == 9) && (yInd == 1)){
			super.enterRoom(_gbs.KEENEY_ROOM, 6, 1, FACESOUTH);
		}
		
		if((xInd == 7 || xInd == 8) && (yInd == 4)){
			super.enterRoom(_gbs.KEENEY_QUAD, 6, 1, FACESOUTH);
		}
		
		if(xInd == 12 && yInd == 1){		
			this._approachTimer.stop();
			super.enterRoom(_gbs.MEIK_ROOM, 4, 10, FACENORTH);
		}
		
	}
	
	public void A_Button(){
		
		if(_xIndex == 12 && _yIndex == 2 && NORTH && _doorClosed){
			_textVisible[0] = !_textVisible[0];
			_busy = !_busy;
			
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
			return;
		}
		
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingNPC() && !_approachTimer.isRunning()){
			if((_xIndex == 6 && _yIndex == 2 && NORTH)||((_xIndex == 12 && _yIndex == 2) && NORTH && _gbs.getPlayer()._activePokemon.size() == 0)){
				_textVisible[0] = !_textVisible[0];
				_busy = !_busy;
			}
			
			if((_xIndex == 6 && _yIndex == 3 && WEST) || (_xIndex == 5 && _yIndex == 2 && SOUTH)){
				_textVisible[1] = !_textVisible[1];
				_busy = !_busy;
			}
			
			
			if(_xIndex == 13 && _yIndex == 3 && EAST){
				_textVisible[2] = !_textVisible[2];
				_busy = !_busy;
			}
			
			if(this._yIndex == 3 && (this._xIndex == 7 || this._xIndex == 8) && _textVisible[3] && !_busy){
				_textVisible[3]= false;
				
				_busy = true;
				this._interruptedTrainer=0;
				
				_meik.unDefeat();
				
				_meik.addDest(this._xIndex+1,this._yIndex); //Set to in front of you.
				_meik.setFirstDest();
				_meik.setAvoidMethod(_meik.getApproachMethod());
				this._gbs.getPlayer().faceRight();
				this._approached=true;
				this._approachTimer.start();
				_busy = false;
				SysOut.print("APPROACH?");
				//M.playSong(M.RA_WALK, false);
			}
			
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}
			
		if(!_approachTimer.isRunning()){
			super.A_Button();
		}
	
		if(this._returnTrip){
			M2.playBG(M2.RA_WALK);
		}
	}

	public void Select(){
		//No Bike indoors.
	}
}
