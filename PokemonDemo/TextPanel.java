package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class TextPanel extends JPanel{
	
	private Font _textFont;
	private Graphics2D _g2;
	private String s1="", s2="";
	private Timer t;
	private Rectangle2D.Double _textOutline;
	private TextPanel _text;
	private boolean[] stage;
	private String[] stageText1, stageText2;

	//private int x = 0, y = 0;
	
	public TextPanel(GameBoyScreen _gbs, int numStages, String[] firstLines, String[] secondLines){
		super();
	
		stage = new boolean[numStages];
		stage[0] = true;
		stageText1 = new String[numStages];
		stageText2 = new String[numStages];
		
		for(int i = 0; i < numStages; i++){
			stageText1[i] = firstLines[i];
			stageText2[i] = secondLines[i];
		}
		
		_text = this;
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(200, 50));
		
		_textFont = _gbs.getFont();
		_textOutline = new Rectangle2D.Double();
		_textOutline.height = 45;
		_textOutline.width = 370;
		_textOutline.x = 5;
		_textOutline.y = 1;	
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		_g2 = (Graphics2D) g;	
		
		_g2.setStroke(new BasicStroke(2));
		_g2.setColor(Color.BLACK);
		_g2.draw(_textOutline);
		
		_g2.setFont(_textFont);

		if(t == null || !t.isRunning())
		for(int i = 0; i < stage.length; i++){
			if(stage[i]){	
				this.displayText(stageText1[i], stageText2[i]);
	//			this.setCurrentText(stageText1[i], stageText2[i]);
			}
		}
		
		_g2.drawString(s1, 20, 20);
		_g2.drawString(s2, 20, 40);
	}
	
	public void setCurrentText(String firstLine, String secondLine){
		s1 = firstLine;
		s2 = secondLine;
		this.repaint();
	}
	
	public void displayText(String firstLine, String secondLine){
	//	_g2.drawString(firstLine, 20, 20);
		
		char[] first = firstLine.toCharArray();
		
		char[] second = null;
		if(secondLine != null)
			second = secondLine.toCharArray();	
	//				_g2.drawString(secondLine, 20, 40);
	
		if(t != null)
			t.stop();
		
		CharListener cl = new CharListener(first, second);
		t = new Timer(35, cl); //FIXME
		t.start();
		SysOut.print("T STARTED");
	}
	
	public void setString(char c, boolean oneORtwo){
		if(oneORtwo)
			s1 = s1 + c;
		else
			s2 = s2 + c;
		this.repaint();
	}
	
	private class CharListener implements ActionListener{
		private char[] first, second;
		private int tick = 0;
		public CharListener(char[] one, char[] two){
			first = one;
			second = two;
		}
		public void actionPerformed(ActionEvent e){
			SysOut.print("Text check");
			if(tick < first.length){
			char f1 = first[tick];
			_text.setString(f1, true);
			SysOut.print("."+f1);


			}
			else{
				if(tick < first.length+second.length){
					char f2 = second[tick-first.length];
					_text.setString(f2, false);
					SysOut.print("."+f2);
				}
				else{
					t.stop();
				}
			}
			tick++;
			
		}
	}
}

