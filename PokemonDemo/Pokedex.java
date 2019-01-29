package PokemonDemo;

import java.io.IOException;

public class Pokedex {

	private int[] list;
	private int numCaught = 0, numSeen = 0;
	private Player _player;
	
	public Pokedex(Player p){
		_player = p;
		list = new int[218];
		for(int i = 0; i < list.length; i++){
			list[i] = 0;
		}
	}
	
	/* When a new Pokemon is seen, the value
	 * changes from 0 to 1.
	 */
	public void addToWatchList(Pokemon p){
		if(list[p.getDexNum()] == 0){
			list[p.getDexNum()] = 1;
		}
		numSeen++;
	}
	
	public void addToWatchList(int i){
		if(list[i] == 0){
			list[i] = 1;
		}
		numSeen++;
	}
	
	/* When a new Pokemon is caught, the value
	 * changes from 0 or 1 to 2. Returns true or
	 * false so that when a Pokemon is caught, the
	 * NBS will display "Data added to Pokedex" for
	 * newly caught Pokemon.
	 */
	public boolean addToCatchList(Pokemon p){
		if(list[p.getDexNum()] == 0 || list[p.getDexNum()] == 1){
			list[p.getDexNum()] = 2;
			numCaught++;
			return true;
		}
		return false;
	}
	
	public boolean addToCatchList(int i){
		if(list[i] == 0 || list[i] == 1){
			list[i] = 2;
			numCaught++;
			return true;
		}
		return false;
	}
	
	/* 0 indicates not prior encounters with
	 * Pokemon, while any other value (1 or 2) does.
	 */
	public boolean hasSeen(Pokemon p){
		if(list[p.getDexNum()] == 0)
			return false;
		return true;
	}
	
	public boolean hasSeen(int i){
		if(i == -1) return false;
		if(list[i] == 0)
			return false;
		return true;
	}
	
	public void initialize(){
		try{
			switch(_player.getActivePokemon(0).getDexNum()){
			case 1:
				this.addToCatchList(_player.getActivePokemon(0));
				this.addToWatchList(Pokemon.getPokemonByDexNumber(4));
				break;
			case 4:
				this.addToCatchList(_player.getActivePokemon(0));
				this.addToWatchList(Pokemon.getPokemonByDexNumber(7));
				break;
			case 7:
				this.addToCatchList(_player.getActivePokemon(0));
				this.addToWatchList(Pokemon.getPokemonByDexNumber(1));
				break;
		}
		}
		catch(IOException ioe){}
	}
	
	/* Only 2 indicates Pokemon has been caught
	 * at least once.
	 */
	public boolean hasCaught(Pokemon p){
		if(list[p.getDexNum()] == 2)
			return true;
		return false;
	}
	
	public boolean hasCaught(int i){
		if(i == -1) return false;
		if(list[i] == 2)
			return true;
		return false;
	}
	
	public int getNumCaught(){
		return numCaught;
	}
	
	public int getNumSeen(){
		return numSeen;
	}
	
	public int[] getList(){
		return list;
	}
}
