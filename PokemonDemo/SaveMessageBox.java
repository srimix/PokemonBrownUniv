package PokemonDemo;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SaveMessageBox extends JFrame {

	public SaveMessageBox(int code){
		super("Saving Game...");
		
		Dimension frame = new Dimension (100,50);
		
		this.setSize(frame);
		this.setLocation(150,150);
		JPanel text = new JPanel();
		JLabel message;
		if(code == 0){
			message = new JLabel("Saving game, please wait...", JLabel.CENTER);
		}
		else{
			message = new JLabel("Game Saved!", JLabel.CENTER);
		}
		text.add(message);
		this.add(text);
		
		this.pack();
	}
}
