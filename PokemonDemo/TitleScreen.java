package PokemonDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This subclass of PokePanel is an imitation of
 * the intial screen whenever an actual game of Pokemon
 * was started, displaying the word "POKEMON" in large
 * letters above the iconic Pokemon of the game (in this
 * case, obviously a Bear-Pokemon)
 * 
 * @author mreiss
 */
@SuppressWarnings("serial")
public class TitleScreen extends PokePanel2{
	
	private GameBoyScreen _gbs;
	private boolean _textVisible;
	private JLabel _startLabel;
	private Timer _time;
	
	public TitleScreen(GameBoyScreen gbs){
		super(gbs);
		
		_gbs = gbs;
		
		this.setBackground(Color.WHITE);
		
		BufferedImage pokemonLogo = null;
		BufferedImage characterLogo = null;
		_textVisible = false;
		
		try{
			pokemonLogo = ImageIO.read(this.getClass().getResource("/PokemonFiles/TitleScreen/penn_logo.png"));
			characterLogo = ImageIO.read(this.getClass().getResource("/PokemonFiles/TitleScreen/NGGPsyduck.png"));
		}
		catch(IOException ioe){}
		
		JLabel pokemonLabel = new JLabel(new ImageIcon(pokemonLogo), JLabel.CENTER);
		
		_startLabel = new JLabel("Press ENTER", new ImageIcon(characterLogo), JLabel.CENTER);
		_startLabel.setVerticalTextPosition(JLabel.BOTTOM);
		_startLabel.setHorizontalTextPosition(JLabel.CENTER);
		
		JPanel title = new JPanel();
		title.setBackground(Color.WHITE);
		title.add(pokemonLabel);
		title.add(_startLabel);
		title.setVisible(true);
		this.add(title, BorderLayout.CENTER);
		
	//	if(!_gbs.isMuted()){
	//	_m = _gbs.getMusic();
	//	SysOut.print("Huh? " + _gbs.isMuted());
	//	_m.background.play(_m.musicCollection[0], false);
	//	}
		
		BlinkListener blink = new BlinkListener();
		_time = new Timer(1000, blink);
		_time.start();
	}

	public void Start(){
		_gbs.setCurrentPanel(1);
	}
	
	public void A_Button(){
		_gbs.setCurrentPanel(1);
	}
	
	public void B_Button(){
		
	}
	
	public void Up(){}
	public void Down(){}
	public void Left(){}
	public void Right(){}
	
	private class BlinkListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(!_textVisible)
				_startLabel.setText("");
			else
				_startLabel.setText("Press ENTER");
			
			//_gbs.focusSelf();
			
			_textVisible = !_textVisible;
		}
	}
}
