package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BannerScreen extends JPanel{
	
	private JPanel _belt;
	private Graphics2D _g2;
	private int _beltNum;
	public final int _1st = 20, _2nd = 72, _3rd = 124, _4th = 176, _5th = 228, _6th = 280;
	private GameBoyScreen _gbs;
	public Ellipse2D.Double _cursor;
	private BeltInfo[] _blt = new BeltInfo[6];
	private TextPanel _text;
	public boolean _depTextVisible = false, _oneLeft;
	private Pokemon _temp;
	
	public BannerScreen(GameBoyScreen gbs){
		
		_gbs = gbs;
		this.setPreferredSize(new Dimension(380,360));
		this.setLayout(new BorderLayout());

		_cursor = new Ellipse2D.Double();
		_cursor.height = 5;
		_cursor.width = 5;
		_cursor.x = 7;
		_cursor.y = _1st;
		
		_belt = new JPanel();
		this.setBackground(Color.WHITE);
		_belt.setBackground(Color.WHITE);
		_belt.setLayout(new GridLayout(6,1));
		
		this.add(_belt, BorderLayout.CENTER);
		
		_text = new TextPanel();
		this.add(_text, BorderLayout.SOUTH);
		
		_depTextVisible = false;
		
		this.checkBelt();
	}
	
	public void checkBelt(){
		for(int i = 0; i < _gbs.getPlayer().getAllActive().size(); i++){
			_blt[i] = new BeltInfo(_gbs.getPlayer().getActivePokemon(i), i);
			this.add(_blt[i]);
		}	
	}

	public void clearBelt(){
		_belt.removeAll();
		_beltNum = 0;	
	}
	
	public void add(BeltInfo bi){
		if(_beltNum < 6){
			_belt.add(bi);
			_beltNum++;
		}
	}
	
	public void resetCursor(){
		_cursor.y = _1st;
	}
	
	public void A_Button(){
		if(_gbs.getPlayer()._activePokemon.size() == 0) 
			return;
		
		if(_gbs.getPlayer()._activePokemon.size() == 1){
			_oneLeft = true;
			_depTextVisible = false;
		}
		else{
			_oneLeft = false;
			_depTextVisible = false;
		
			if(!_depTextVisible && !_oneLeft){
				_depTextVisible = true;
				switch((int)_cursor.y){
				case _1st:
					_temp = _gbs.getPlayer().getActivePokemon(0);
					_gbs.getPlayer().depositPokemon(_gbs.getPlayer().getActivePokemon(0));
					break;
				case _2nd:
					_temp = _gbs.getPlayer().getActivePokemon(1);
					_gbs.getPlayer().depositPokemon(_gbs.getPlayer().getActivePokemon(1));
					break;
				case _3rd:
					_temp = _gbs.getPlayer().getActivePokemon(2);
					_gbs.getPlayer().depositPokemon(_gbs.getPlayer().getActivePokemon(2));
					break;
				case _4th:
					_temp = _gbs.getPlayer().getActivePokemon(3);
					_gbs.getPlayer().depositPokemon(_gbs.getPlayer().getActivePokemon(3));
					break; 
				case _5th:
					_temp = _gbs.getPlayer().getActivePokemon(4);
					_gbs.getPlayer().depositPokemon(_gbs.getPlayer().getActivePokemon(4));
					break;
				case _6th:
					_temp = _gbs.getPlayer().getActivePokemon(5);
					_gbs.getPlayer().depositPokemon(_gbs.getPlayer().getActivePokemon(5));
					break;
				}
				
				_cursor.y = _1st;
		
				for(int i = 0; i < _blt.length; i++){
					if(_blt[i]!=null)
						_blt[i].repaint();
				}
			}
			else{
				if(_depTextVisible)
					_depTextVisible = false;
				if(_oneLeft)
					_oneLeft = false;
			}
		}	
		this.clearBelt();
		this.checkBelt();
		this.repaint();
		_text.repaint();
	}
	
	public void Up(){
		if(_cursor.y != _1st)
			_cursor.y -= 52;
		_depTextVisible = false;
		_oneLeft = false;
		this.repaint();
	}
	
	public void Down(){
		switch(_beltNum){
		case 1: break;
		case 2: if(_cursor.y != _2nd) _cursor.y += 52; break;
		case 3: if(_cursor.y != _3rd) _cursor.y += 52; break;
		case 4: if(_cursor.y != _4th) _cursor.y += 52; break;
		case 5: if(_cursor.y != _5th) _cursor.y += 52; break;
		case 6: if(_cursor.y != _6th) _cursor.y += 52; break;
		}
		_depTextVisible = false;
		_oneLeft = false;
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		_g2 = (Graphics2D) g;
		_g2.setStroke(new BasicStroke(2));
		_g2.setColor(Color.BLACK);
		_g2.draw(_cursor);
		
	}
	
	//==================================================
	//GAP==================================================
	//==================================================
	
	private class BeltInfo extends JPanel{
		
		private Pokemon _focus;
		private Rectangle2D.Double _outline, _health;
		private ImageIcon icon;
		private int number;
		
		public BeltInfo(Pokemon focus, int num){
			_focus = focus;
			
			number = num;
			
			icon = _focus.getIcon();
			
			_outline = new Rectangle2D.Double();
			_health = new Rectangle2D.Double();
			
			_outline.height = 15;
			_outline.width = 100;
			_outline.x = 160;
			_outline.y = 15;
			
			_health.height = 13;
			_health.x = 161;
			_health.y = 16;
		}
		
		public void paintComponent(Graphics g){
			_g2 = (Graphics2D) g;

			icon.paintIcon(this, g, 15, 5);

			_g2.setFont(_gbs.getFont());
			_g2.setColor(Color.BLACK);
			_g2.drawString(_focus.getName(), 60, 25);
			
			_g2.setStroke(new BasicStroke(2));

			_g2.draw(_outline);
			
			_health.width = (int)(98*((double)_focus.getCurrentHP()/_focus.getMaxHP()));
			
			if((double)_focus.getCurrentHP()/_focus.getMaxHP() > .5)
				_g2.setColor(Color.GREEN);
			else{
				if((double)_focus.getCurrentHP()/_focus.getMaxHP() > .25)
					_g2.setColor(Color.ORANGE);
				else
					_g2.setColor(Color.RED);
			}
			
			_g2.fill(_health);
			
			_g2.setColor(Color.BLACK);
			_g2.drawString(_focus.getCurrentHP() + "/" + _focus.getMaxHP(), 275, 27);
			
			_g2.drawString(_focus.getStatusAcro(), 340, 27);
		
		}
		
 	}
		
	private class TextPanel extends JPanel{
		private Rectangle2D.Double _textOutline;
		
		public TextPanel(){
			super();
			
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(200, 50));
			
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
			
			_g2.setFont(_gbs.getFont());
			
			if(_gbs.getPlayer().getAllActive().size() == 0){
				_g2.setColor(Color.RED);
				this.displayText("You don't have any Pokemon!","");
			}
			else{
				if(!_depTextVisible && !_oneLeft){
					this.displayText("Choose a POKEMON: " + (int)(((_cursor.y-20)/52.0)+1), "");
				}
				else{
					if(_depTextVisible)
						this.displayText("Deposited " + _temp.getName(),"");
					if(_oneLeft){
						_g2.setColor(Color.RED);
						this.displayText("Cannot deposit last Pokemon!","");
					}
				}
			}
		}
		
		public void displayText(String firstLine, String secondLine){
			_g2.drawString(firstLine, 20, 20);
			
			if(secondLine != null)
				_g2.drawString(secondLine, 20, 40);
		}
	}
}

	

