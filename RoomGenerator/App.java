package RoomGenerator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import PokemonDemo.Pokemon;
import PokemonDemo.Trainer;

public class App extends JFrame{

	private Vector<Trainer> trainers;
	private Vector<Pokemon> wildPokemon;
	private char[][] map;
	private BufferedImage bg, over;
	private String fileName, roomDesc;
	
	public static void main(String[] args) {
		App a = new App("Pokemon Level Generator");
	}

	public App(String s){
		super(s);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(600,600));
		
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(11,1));
		
		this.pack();
		this.setVisible(true);
	}
	
	private class FileNamePanel extends JPanel{
		
	}
}
