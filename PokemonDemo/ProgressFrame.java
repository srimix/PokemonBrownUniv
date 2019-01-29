package PokemonDemo;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class ProgressFrame extends JFrame{
	
	private JProgressBar bar;
	
	public ProgressFrame(){
		super("Game Loading...");
		bar = new JProgressBar(0, 100);
		bar.setValue(0);
		bar.setStringPainted(true);
		
		this.setPreferredSize(new Dimension(200,100));
		this.setLocation(150, 400);
		this.setResizable(false);
		
		this.add(bar);
		this.pack();
		
	}
	
	public void update(double progress){
		
		bar.setIndeterminate(false);
		bar.setValue((int) (100.0*progress));

		this.paintComponents(this.getGraphics());
			
		if(bar.getValue() == 100)
			this.setVisible(false);
	}

}
