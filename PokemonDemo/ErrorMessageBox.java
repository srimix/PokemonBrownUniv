package PokemonDemo;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ErrorMessageBox extends JFrame {

	private JButton _quit, _tryAgain;
	private GameBoyScreen _gbs;
	private ErrorMessageBox _this;
	
	public ErrorMessageBox(GameBoyScreen gbs, String errorMessage){
		super("Error");
		
		_this = this;
		_gbs = gbs;
		
		Dimension frame = new Dimension(100,50);
		
		this.setSize(frame);
		
		this.setLocation(150, 150);
		
		this.setLayout(new GridLayout(2,1));
		
		JPanel textPanel = new JPanel();
		JLabel message = new JLabel(errorMessage, JLabel.CENTER);
		textPanel.add(message);
		
		JPanel buttonsPanel = new JPanel();
		
		_tryAgain = new JButton("Try Again?");
		JPanel tryPanel = new JPanel();
		tryPanel.add(_tryAgain);
		
		_quit = new JButton("Quit");
		JPanel cancelPanel = new JPanel();
		cancelPanel.add(_quit);
		
		TryAgainOrQuitListener taoqListen = new TryAgainOrQuitListener();
		_tryAgain.addActionListener(taoqListen);
		_quit.addActionListener(taoqListen);
		
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(tryPanel);
		buttonsPanel.add(cancelPanel);
		
		this.add(textPanel);
		this.add(buttonsPanel);
		
		this.pack();
	}
	
	private class TryAgainOrQuitListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			
			Object source = e.getSource();
			
			if(source == _tryAgain){
				_gbs.loadGame();
				_this.setVisible(false);
			}
			
			if(source == _quit)
				System.exit(0);
		}
	}
}
