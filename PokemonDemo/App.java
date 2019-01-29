 
package PokemonDemo;

/**
 * This is Pokemon: Brown Edition. It is a java program
 * that seeks to emulate the popular Nintendo Gameboy games
 * based on the world of Pokemon. In this version, however,
 * the game takes place at Brown University. The physical
 * appearance of the game is meant to resemble a Gameboy screen,
 * and the game itself follows a similar progression to the
 * original Pokemon Red and Blue games.
 * 
 * @author mreiss
 */
public class App {

	public App(){
		SysOut.print("SUP");
		@SuppressWarnings("unused")
		SplashScreen ss = new SplashScreen();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		App app = new App();
	}
}