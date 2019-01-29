package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class EvolutionScreen extends JPanel{

	private GameBoyScreen _gbs;
	private Graphics2D _g2;
	private Pokemon _base, _evolved;
	private Timer evolTimer, t;
	private boolean evolVis = false;
	private boolean naturalBirth=true;
	private int tick = 0;
	private BufferedImage _center;
	
	private TextPanel2 _text;
	private String s1 = "", s2 = "";
	
	private Vector<Pokemon> vector;
	 
	public EvolutionScreen(GameBoyScreen gbs){
		_gbs = gbs;
		
		this.setPreferredSize(new Dimension(380,360));
		this.setBackground(Color.BLACK);
			
		EvolTimer ev = new EvolTimer();
		evolTimer = new Timer(400,ev);
		
		_text = new TextPanel2();
		
		this.setVisible(false);
		
		this.setLayout(new BorderLayout());
		this.add(_text, BorderLayout.SOUTH);
	}
	
	public void setPokemonToEvolve(Vector<Pokemon> p){
		vector = p;
		SysOut.print("SETTING POKEMON TO EVOLVE: " + p.get(0).getName());
		_base = p.get(0);
		tick = 0;
		_center = _base.getFrontImage();
		_evolved = Pokemon.evolve(p.get(0),p.get(0).evStage);
		_text.displayText("What? " + _base.getName() + " is evolving!", "");
	//	_gbs.playSound(M.EVOLVING);
		M2.playFX(M2.EVOLVING);
		EvolTimer ev = new EvolTimer();
		evolTimer = new Timer(400,ev);
		evolTimer.start();
		t.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		_g2 = (Graphics2D) g; 
		
		_g2.drawImage(_center, null, (int)((this.getWidth()-_center.getWidth())/2.0), (int)((this.getHeight()-_center.getHeight())/2.0)); 
	}
	
	
	public void A_Button(){
		SysOut.print("WTF: ");// + (evolTimer.isRunning()) + ", " + t.isRunning());
		
		if (!evolTimer.isRunning() && !t.isRunning()){
			this.setVisible(false);
			_gbs.getCurrentPanel()._busy = false;
			_gbs.getCurrentPanel().repaint();
			
			if(naturalBirth){
				_gbs.getCurrentPanel().afterBattle();
			}
			
		}
	}
	
	public void B_Button(){
		if(evolTimer.isRunning()){
			evolTimer.stop();
			_center = _base.getFrontImage();
			_text.displayText("..." + _base.getName() + " did not evolve!", "");
			
			vector.remove(0);
			if(vector.size() > 0){
				SysOut.print("Size>?" + vector.size());
				setPokemonToEvolve(vector);
			}
		}
	}
	
	private class EvolTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			tick++;
			if(tick > 5){
				if(!evolVis){
					_center = _evolved.getFrontImage();
					evolVis = true;
				}
				else{
					_center = _base.getFrontImage();
					evolVis = false;
				}
			
				if(tick == 10 || tick == 30){
					evolTimer.setDelay((int)(evolTimer.getDelay()/2.0));
				}
				

			
				if(tick == 50){
					evolTimer.stop();
					//Pokemon.evolve(_base, 1);
					SysOut.print("_base"  + _base.getName());
					_gbs.getPlayer().getAllActive().set(_base.getBelt()-1, Pokemon.generateNewStats(Pokemon.evolve(_base, _base.evStage)));
					SysOut.print("_base"  + _base.getName());
					
					//if(_base == null) SysOut.print("base is null");
					if(_gbs.getPlayer().getPokedex()!= null)
						_gbs.getPlayer().getPokedex().addToCatchList(_base.getDexNum()+_base.evStage);
					SysOut.print("====");
					SysOut.print("====");
					SysOut.print("====");
					M2.playFX(M2.EVOLVED);
					String substring = _evolved.getName();
					//substring = substring.substring(substring.indexOf('$')+1);
					_text.displayText(_base.getName() + " evolved into " + substring + "!", "");
					tick = 0;
					
					vector.remove(0);
					if(vector.size() > 0){
						SysOut.print("Size>?" + vector.size());
						setPokemonToEvolve(vector);
					}
				}	
			}
		}
	}
	
	private class TextPanel2 extends JPanel{
		
		private Font _textFont;
		private Rectangle2D.Double _textOutline;

		public TextPanel2(){
			super();
			
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

			_g2.drawString(s1, 20, 20);
			_g2.drawString(s2, 20, 40);
			
		}
		
		public void setCurrentText(String firstLine, String secondLine){
			s1 = firstLine;
			s2 = secondLine;
		}
		
		public void displayText(String firstLine, String secondLine){
		//	_g2.drawString(firstLine, 20, 20);
			
			this.setCurrentText("", "");
			
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
}


