package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PCBoxScreen extends JPanel {
	
	private GameBoyScreen _gbs;
	private Graphics2D _g2;
	public Ellipse2D.Double _cursor;
	private String[] pkmnNames;
	private int ha;
	public int[] yLocs;
	private boolean firstPage = true;
	public boolean warningDisplayed = false, pcEmpty = false;
	private Rectangle2D.Double _back, _outline, _warningOutline, _warningFill;
	private Vector<Pokemon> storedPokemon;
	
	public PCBoxScreen(GameBoyScreen gbs){
		_gbs = gbs;
		
		this.setPreferredSize(new Dimension(380,360));
		this.setBackground(Color.WHITE);

		storedPokemon = new Vector<Pokemon>();
		
		_cursor = new Ellipse2D.Double();
		_cursor.height = 5;
		_cursor.width = 5;
		_cursor.x = 8;
		_cursor.y = 8;
		
		_warningFill = new Rectangle2D.Double();
		_warningFill.height = 50;
		_warningFill.width = 200;
		_warningFill.x = 80;
		_warningFill.y = 145;
		
		_warningOutline = new Rectangle2D.Double();
		_warningOutline.height = 48;
		_warningOutline.width = 198;
		_warningOutline.x = 81;
		_warningOutline.y = 146;
		
		_back = new Rectangle2D.Double();
		_back.height = 400;
		_back.width = 400;
		_back.x = - 20;
		_back.y = - 20;
		
		_outline = new Rectangle2D.Double();
		_outline.height = 360;
		_outline.width = 375;
		_outline.x = 2;
		_outline.y = 2;
		
		ha = 0;
		
		this.refreshList();
	}
	
	public void Up(){
		if(!warningDisplayed && !pcEmpty){
		if(!(_cursor.y == 8 && _cursor.x == 8)){
			_cursor.y = _cursor.y -26;
			if(_cursor.y ==  -18 && _cursor.x == 188){
				_cursor.x = 8;
				_cursor.y = 346;
			}
			if(_cursor.x == 188 && _cursor.y == 320){
				if(firstPage)
					_cursor.y = 294;
			}
			if(_cursor.x == 8 && _cursor.y == 34){
				if(!firstPage)
					_cursor.y = 8;
			}
			this.repaint();
		}
		}
		else{
			//warning stuff
		}
	}
	
	public void Down(){
		if(!warningDisplayed && !pcEmpty){
	//	SysOut.print("Cursor Down: "+ ((_cursor.y+26)-8)/26.0 + ", Items: "+itemsInMenu.size());
		if((firstPage && _cursor.x == 8 && ((_cursor.y+26)-8)/26.0 != storedPokemon.size()) ||
				(firstPage && _cursor.x == 188 && (((_cursor.y+26)-8)/26.0)+14 != storedPokemon.size()) ||
				(!firstPage && _cursor.x == 8 && (((_cursor.y+26)-8)/26.0)+24 != storedPokemon.size()) ||
				(!firstPage && _cursor.x == 188 && (((_cursor.y+26)-8)/26.0)+38 != storedPokemon.size())){
		if(!(_cursor.y == 346 && _cursor.x == 188)){
			_cursor.y = _cursor.y+26;
			if(_cursor.y ==  372 && _cursor.x == 8){
				_cursor.x = 188;
				_cursor.y = 8;
			}
			if(_cursor.x == 188 && _cursor.y == 320){
				if(firstPage)
					_cursor.y = 346;
			}
			if(_cursor.x == 8 && _cursor.y == 34){
				if(!firstPage)
					_cursor.y = 60;
			}
			this.repaint();
		}
		}
		}
		else{
			//warning stuff
		}
	}

	public void A_Button(){	
		if(!warningDisplayed && !pcEmpty){
			
			
			
		if(firstPage && _cursor.x == 188 && _cursor.y == 346){
			firstPage = false;
			
			_cursor.x = 8;
			_cursor.y = 8;
			
			this.repaint();
		}
		else{
			if(!firstPage && _cursor.x == 8 && _cursor.y == 8){
				firstPage = true;
				_cursor.x = 188;
				_cursor.y = 346;
				
				this.repaint();
			}
			else{ // Below is all options, not page changes
				
				if(_gbs.getPlayer()._activePokemon.size() == 6){
					warningDisplayed = true;
				}
				else{
				
				if(firstPage){
					if(_cursor.x == 8){
						int choice = (int)((_cursor.y-8)/26.0);
						if(choice < storedPokemon.size()){
							_gbs.getPlayer().withdrawPokemon(choice, storedPokemon.get(choice));
						}
					}
					else{
						int choice = 14+(int)((_cursor.y-8)/26.0);
						if(choice < storedPokemon.size()){
							_gbs.getPlayer().withdrawPokemon(choice, storedPokemon.get(choice));
						}
					}
				}
				else{
					if(_cursor.x == 8){
						int choice = 24+(int)((_cursor.y-8)/26.0);
						if(choice  < storedPokemon.size()){
							_gbs.getPlayer().withdrawPokemon(choice, storedPokemon.get(choice));
						}
					}
					else{
						int choice = 38+(int)((_cursor.y-8)/26.0);
						if(choice < storedPokemon.size()){
							_gbs.getPlayer().withdrawPokemon(choice, storedPokemon.get(choice));
						}
					}
				}
				
				
			}
			}
		}
		}
		else{
			//warning stuff
			warningDisplayed = false;
		}
		
		this.refreshList();
		this.repaint();
	}
	
	public void refreshList(){
		int x = _gbs.getPlayer()._PCPokemon.size();
		
		if(x > 0){
			pkmnNames = new String[x];
			yLocs = new int[x];
			int y = 0;
			storedPokemon.clear();
		
			for(int i = 0; i < x; i++){
				storedPokemon.add(_gbs.getPlayer()._PCPokemon.get(i));
				pkmnNames[i] = _gbs.getPlayer()._PCPokemon.get(i).getName();//+ ", Lvl." + _gbs.getPlayer()._PCPokemon.get(i).getLevel();
				yLocs[i] = 15+(26*y);
				y++;
			
			}
		
			firstPage = true;
			_cursor.x = 8;
			_cursor.y = 8;
			pcEmpty = false;
		}
		else{
			pcEmpty = true;
		}
	}
	
	public void paintComponent(Graphics g){
		_g2 = (Graphics2D) g;
		_g2.setColor(Color.WHITE);
		_g2.fill(_back);
		
		_g2.setColor(Color.BLACK);
		_g2.setFont(new Font("Courier New", Font.BOLD, 16));
		

		if(firstPage){
			if(!pcEmpty){
				int y = 0;
				for(int i = 0; i < pkmnNames.length; i++){
					if(pkmnNames[i] != null){
						if(y < 14){
							_g2.drawString(pkmnNames[i], 20, yLocs[i]);
							if((_cursor.y+7) == yLocs[i] && _cursor.x == 8){
								_gbs.getPlayer()._PCPokemon.get(i).getIcon().paintIcon(this, g, 150, yLocs[i]-25);
								//SysOut.print("PELASE BE FITRST LINE:>? " + y);
							}
						}
						else{
							if(y < 26){
								_g2.drawString(pkmnNames[i], 200, yLocs[i]-364);
								if((_cursor.y+7) == yLocs[i]-364 && _cursor.x == 188){
									_gbs.getPlayer()._PCPokemon.get(i).getIcon().paintIcon(this, g, 330, yLocs[i]-364-25);
									//SysOut.print("EPLAESE BE SECOND");
								}
								ha = i;
							}
							else{
								if(_cursor.x == 188 && _cursor.y == 346){_g2.setColor(Color.RED);}
									_g2.drawString("   ===Page 2===>", 200, yLocs[i]-338);
									_g2.setColor(Color.BLACK);
									break;
							}
						}
				
						y++;
					}
				}
			}
			
			else{
				_g2.setColor(Color.WHITE);
				_g2.fill(_warningFill);
				
				_g2.setColor(Color.BLACK);
				_g2.draw(_warningOutline);
				
				_g2.setColor(Color.RED);
				_g2.drawString("You have no Pokemon", 87, 165);
				_g2.drawString(" in the PC.", 125, 185);
			}
		}
		else{
			int y = 0;
			if(_cursor.x == 8 && _cursor.y == 8){_g2.setColor(Color.RED);}
			_g2.drawString("<===Page 1===", 30, 15);
			_g2.setColor(Color.BLACK);
			if(!pcEmpty){
				for(int i = ha+1; i < pkmnNames.length; i++){
					if(pkmnNames[i] != null){
						if(y < 12){
							SysOut.print("I: " + i);
							_g2.drawString(pkmnNames[i], 30, yLocs[i]-624);
						}
						else{
							if(y < 24){
								_g2.drawString(pkmnNames[i], 200, yLocs[i]-988);
							}
							else{
								_g2.drawString("   ===Page 3===>", 200, yLocs[i]-962);
								break;
							}
						}
						y++;
					}
				}
			}
		}
		_g2.setStroke(new BasicStroke(2));
		_g2.draw(_cursor);
		_g2.draw(_outline);
		
		if(warningDisplayed){
			//warning stuff
			_g2.setColor(Color.WHITE);
			_g2.fill(_warningFill);
			
			_g2.setColor(Color.BLACK);
			_g2.draw(_warningOutline);
			
			_g2.setColor(Color.RED);
			_g2.drawString("You already have", 100, 165);
			_g2.drawString("6 Pokemon!", 125, 185);
		}
	}

}
