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
public class IvyRoom extends PokePanel2 {
	private BufferedImage  _roomO;
	
	public IvyRoom(GameBoyScreen gbs){
		super(gbs);
		this.createBaseRoom();
		_xSpace = -28;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	}

	public IvyRoom(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		this.createBaseRoom();
	}

	public void addTrainers(){
		try{
			this._movingTrainers = new Vector<Trainer>();
			
			//IS THIS ACCURATE? Check bulbapedia
			this._thisMartContains.clear();
			this._thisMartContains.add(0); //potion
			this._thisMartContains.add(22); //pokeball
			this._thisMartContains.add(28); //antidote
			
			Trainer mart = new Trainer.MartMenu("Ivy Room", this);
			mart.createHome(2, 4, 0, 0, "", "");
			_movingTrainers.add(mart);
			
			Trainer pkmn = new Trainer.PokemonCenter(_gbs.getPlayer(), this._roomNum, this);
			pkmn.createHome(4, 1, 0, 0, "", "");
			_movingTrainers.add(pkmn);

			Trainer chansey = new Trainer.Chansey(this);
			chansey.createHome(4, 5, -2, 0, "drunk", "freeze");
			chansey.clearDestinations();
			chansey.addDest(6, 2);
			chansey.addDest(2, 7);
			chansey.setFirstDest();
			chansey.setPause(true);
			chansey.setStationary(false);
			chansey.getDialogue().clear();
			chansey.getDialogue().add("Chansey: Chanseyyyy!");
			chansey.getDialogue().add("Chansey is handling its egg with care.");
			
			_movingTrainers.add(chansey);


		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this._roomNum = 10;
		this.xConstant=62;
		this.yConstant=60;
		this._mapX=172;
		this._mapY=319;
		this._pkmnCentX=4;
		this._pkmnCentY=2;
		
		this.addTrainers();
		this._martMenuVisible=false;
		_textVisible = new boolean[4];
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/IvyRoom/IvyRoom.png"));
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
		this.description="Ivy Room";
		this.song = M2.IVY_ROOM;
	}

	public void createGrid(){
		this._room = new Room(11,10);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/IvyRoom.cmap"));
		for(int i = 0; i < 10; i++){
			for(int i2 = 0; i2 < 11; i2++){
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
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);

		
		if(_textVisible[0]){
			this.drawText(g2, "Sorry kid, only BUDS workers are","allowed past here.");
		}

		if(_textVisible[1]){
			this.drawText(g2, "...We can't use meal credits at", "lunch? Ughhhhh.....");
		}
		
		if(_textVisible[2]){
			this.drawText(g2, _gbs.getPlayer().getPlayerName() + " turned on the PC.");
		}
		
		if(_textVisible[3]){
			this.drawText(g2, "Welcome to the Ivy Room PokeMart!", "How can I help you?");
		}

		this.drawAllGenerics(g2);
	}

	public void enterRoom(int xInd, int yInd){

		super.enterRoom(xInd, yInd);
		
		if((xInd == 9 || xInd == 8) && (yInd == 9)){
			super.enterRoom(_gbs.RATTY_ENTRANCE, 6, 3, FACESOUTH);
		}
	}
	
	public void blackout(){
		this.blackout(this._pkmnCentX,this._pkmnCentY,FACENORTH);
		this.A_Button();
	}

	public void A_Button(){
		
		if(!_menuVisible && !_pcVisible && !this.textTimer.isRunning() && !_martMenuVisible){
			if(_xIndex == 8 && _yIndex == 4 && EAST){
				_textVisible[1] = !_textVisible[1];
				_busy = !_busy;
			}

			if(_xIndex == 3 && _yIndex == 2 && WEST){
				_textVisible[0] = !_textVisible[0];
				_busy = !_busy;
			}
			
			if(_xIndex == 8 && _yIndex == 1 && NORTH){
				if(!_textVisible[2]){
					_textVisible[2] = true;
					_busy = true;
				}
				else{
					this._pcVisible = true;
					_textVisible[2] = false;
				}
			}
			
//			if(_xIndex == 3 && _yIndex == 4 && WEST){
//				if(!_textVisible[3]){
//					_textVisible[3] = true;
//					_busy = true;
//				}
//				else{
//					this._martMenuVisible = true;
//					_textVisible[3] = false;
//				}
//			}
//			
		//if(in front of PkmnCenter){
		//	_gbs.getPlayer().setPkmnCenter(this._roomNum);
		//}
			if(!_busy){
				this.completionCheck = false;
			}
			
			this.repaint();
		}
		
		super.A_Button();
	
	}
}