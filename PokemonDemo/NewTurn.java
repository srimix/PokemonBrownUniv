package PokemonDemo;

import java.util.Vector;

public class NewTurn {

	private Vector<NewTurnAction> listActions;
	private int current = -1;
	
	public NewTurn(){
		listActions = new Vector<NewTurnAction>();
	}
	
	public void addTurnAction(NewTurnAction nta){
		listActions.add(nta);
	}
	public void addTurnActionAt(int index, NewTurnAction nta){
		listActions.add(index, nta);
	}
	
	public int getListSize(){
		return listActions.size();
	}
	
	public Vector<NewTurnAction> getList(){
		return listActions;
	}
	
	public int getCurrentStage(){
		return current;
	}
	
	public void nextStage(){
		current++;
		listActions.get(current).execute();
	}
}
	

