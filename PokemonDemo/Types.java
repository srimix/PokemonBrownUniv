package PokemonDemo;

import java.awt.Color;

/**
 * Special enum used for various types of Pokemon,
 * such as those below (Lightning, Flying, Normal),
 * but also for those not in the demo (Grass, Water, etc.)
 * 
 * @author mreiss
 */
public enum Types {
	ELECTRIC,
	//Super Effective Against: 
	//Not Very Effective Against:
	//Doesn't Affect:
	
	FLYING, 
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:

	NORMAL, 
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	GHOST, 
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	DRAGON, 
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	GRASS,
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	FIRE,
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	WATER, 
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	ROCK,
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	GROUND,
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	BUG,
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	POISON, 
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	ICE,
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	FIGHTING, 
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	PSYCHIC,
	//Super Effective Against:
	//Not Very Effective Against:
	//Doesn't Affect:
	
	STEEL,
	DARK,
	
	NONE;

	public Color getColor(){
		switch(this){
		case ELECTRIC: return new Color(248, 208, 48);
		case FLYING: return new Color(168, 144, 240);
		case NORMAL: return new Color(168, 168, 120);
		case GHOST: return new Color(112, 88, 152);
		case DRAGON: return new Color(112, 56, 248);
		case GRASS: return new Color(120, 200, 80);
		case FIRE: return new Color(240, 128, 48);
		case WATER: return new Color(104, 144, 240);
		case ROCK: return new Color(184, 160, 56);
		case GROUND: return new Color(224, 192, 104);
		case BUG: return new Color(168, 184, 32);
		case POISON: return new Color(160, 64, 160);
		case ICE: return new Color(152, 216, 216);
		case FIGHTING: return new Color(192, 48, 40);
		case PSYCHIC: return new Color(248, 88, 136);
		}
		
		
		return Color.WHITE;
	}
}
