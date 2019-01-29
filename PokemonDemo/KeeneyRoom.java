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
public class KeeneyRoom extends PokePanel2 {
	
	private BufferedImage _keeneyRoom;
	private Trainer map, bed1, pc1, bed2, pc2;
	
	public KeeneyRoom(GameBoyScreen gbs){
		super(gbs);
		
		this.createBaseRoom();
		
		//Initialize variables
		_xSpace = 12;
		_ySpace = 0;
		
		_xIndex = 5;
		_yIndex = 4;
		
		xObs = 5;
		yObs = 4;
			
	}
	
	public KeeneyRoom(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.createBaseRoom();
	
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		
		map = new Trainer.Map(this);
		map.createHome(7, 1, 0, 0, "", "");
		map.setStationary(true);
		
		bed1 = new Trainer.Bed(_gbs.getPlayer(), _gbs.KEENEY_ROOM, this);
		bed1.createHome(9, 5, 0, 0, "", "");
		bed1.setStationary(true);
		bed2 = new Trainer.Bed(_gbs.getPlayer(), _gbs.KEENEY_ROOM, this);
		bed2.createHome(9, 6, 0, 0, "", "");
		bed2.setStationary(true);
		
		pc1 = new Trainer.PC(_gbs);
		pc1.createHome(6, 5, 0, 0, "", "");
		pc1.setStationary(true);
		pc2 = new Trainer.PC(_gbs);
		pc2.createHome(7, 5, 0, 0, "", "");
		pc2.setStationary(true);
		
		this._movingTrainers.add(map);
		if (_gbs.getPlayer().getAllActive().size()>0){
			this._movingTrainers.add(bed1);
			this._movingTrainers.add(bed2);
		}
		this._movingTrainers.add(pc1);
		this._movingTrainers.add(pc2);
	}

	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "Keeney Room";
		this._roomNum = 2;
		_textVisible = new boolean[9];
		this.addTrainers();
		this.xConstant = 64;
		this.yConstant = 88;
		this._pkmnCentX=8;
		this._pkmnCentY=5;
		
		this._mapX=113;
		this._mapY=339;

		
		this.song = M2.KEENEY;
		
		try {
			if(GameBoyScreen.finishedLoading){
				if(_gbs.getPlayer()._activePokemon.size() == 0)
					_keeneyRoom = ImageIO.read(this.getClass().getResource("/PokemonFiles/KeeneyRoom/bedroomMap4.png"));
			//	_keeneyRoom = _gbs.bgArray[2];
				else
					_keeneyRoom = ImageIO.read(this.getClass().getResource("/PokemonFiles/KeeneyRoom/bedroomMap3.png"));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(0);
		}
		
		this.createGrid();
		
	}


	public void createGrid(){
		this._room = new Room(12,9);
		
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/KeeneyRoom.cmap"));
		for(int i = 0; i < 9; i++){
			for(int i2 = 0; i2 < 12; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);				
			}
		}
		
	}
	
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_keeneyRoom, null, this._xSpace, this._ySpace);

		this.drawPlayer(g2);
		
		this.drawBox(g2);
		
		if(_textVisible[0]){
			this.drawText(g2, "Your Pokemon are now fully healed.", "");
			_gbs.getPlayer().setPkmnCenter(this._roomNum);
		}
		
		if(_textVisible[1])
			this.drawText(g2, "It's your computer. You're currently",
					          "working on Tetris for CS 15.");
		
		
		if(_textVisible[2]){
			this.drawText(g2, "It's a map of Brown's campus.","");
		}
		
		if(_textVisible[3])
			this.drawText(g2, "It's a bookshelf, filled with", "overpriced textbooks.");
		
		if(_textVisible[4])
			this.drawText(g2, _gbs.getPlayer().getPlayerName() + " turned on the PC.");
		
		if(_textVisible[5])
			this.drawText(g2, "It's " + _gbs.getPlayer().getRivalName() + "'s computer...");
		
		if(_textVisible[6])
			this.drawText(g2, "It looks like he was doing homework", 
					          "or something before he fell asleep.");
		
		if(_textVisible[7])
			this.drawText(g2, _gbs.getPlayer().getRivalName() +", passed out on his bed,",
					          "as usual.");
		
		if(_textVisible[8])
			this.drawText(g2, "A map of Brown's campus.");
		
		this.drawAllGenerics(g2);
		this.drawAllTrainers(g2, xConstant, yConstant, this._movingTrainers);
	
	}
	
	
	public void enterRoom(int xInd, int yInd){
		super.enterRoom(_gbs.KEENEY_HALL, 9, 1, this.FACESOUTH);
	}
	
	public void blackout(){
		this.blackout(this._pkmnCentX,this._pkmnCentY,FACEEAST);
		_gbs.getPlayer().healAllActive();
	}
	
	
	public void A_Button(){
		//super.A_Button();
		
		if(!_menuVisible && !_pcVisible && !this.textTimer.isRunning()){
		
//		if(!_heal.isRunning() && _xIndex == 8 && (_yIndex == 5 || _yIndex == 6) && this.EAST && !_textVisible[0] && _gbs.getPlayer()._activePokemon.size() > 0){
//			_busy = true;
//			_heal.start();
//		}
		
//		if(_xIndex == 7 && _yIndex == 2 && NORTH){
//			if(!_textVisible[8] && !this.mapVisible){
//				_textVisible[8] = true;
//				this._busy = true;
//			}
//			else if (!this.mapVisible){
//				_textVisible[8] = false;
//				this.mapVisible =  true;
//			}
//		}
		
		if((_xIndex == 9 || _xIndex == 10) && _yIndex == 2 && NORTH){
			_textVisible[3] = !_textVisible[3];
			_busy = !_busy;
		}
		
		if(_textVisible[0]){
			_textVisible[0] = false;
			_busy = false;
		}
		
		if((_xIndex == 1 || _xIndex == 2) && _yIndex == 3 && NORTH){
			if(!_textVisible[5] && !_textVisible[6]){
				_textVisible[5] = true;
				_busy = !_busy;
			}
			else{
				if(!_textVisible[6]){
					_textVisible[5] = false;
					_textVisible[6] = true;
				}
				else{
					_textVisible[6] = false;
					_busy = !_busy;
				}
			}
		}
		
		if(_xIndex == 3 && (_yIndex == 5 || _yIndex == 6) && WEST && _gbs.getPlayer()._activePokemon.size() == 0){
			_textVisible[7] = !_textVisible[7];
			_busy = !_busy;
		}
		
		if(!_busy){
			this.completionCheck = false;
		}
		
		this.repaint();
		
		}
		super.A_Button();
//		
	}
	
	public void Select(){
		//No Bike indoors.
	}
}