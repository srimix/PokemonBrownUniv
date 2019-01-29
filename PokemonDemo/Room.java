package PokemonDemo;

/**
 * This class is the Room object that is
 * essentially the blueprint of any room a
 * Player can walk through, including spaces
 * that are walkable (0, 3, 4) and non-walkable
 * (1, 2). Used to determine allowable movement,
 * similarly to Tetris.
 * 
 * @author mreiss
 */
public class Room {
	
	public char[][] _roomGrid;
	public int _width, _height;

	public Room(int x, int y){
		_width = x;
		_height = y;
		_roomGrid = new char[_width][_height];
	}
	
	public int getX(){
		return _width;
	}
	
	public int getY(){
		return _height;
	}
	
	public void setX(int x){
		_width = x;
	}
	
	public void setY(int y){
		_height = y;
	}
}
