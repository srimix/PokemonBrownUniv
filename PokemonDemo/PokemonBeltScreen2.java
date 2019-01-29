package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PokemonBeltScreen2 extends JPanel{

	private GameBoyScreen _gbs;
	private Ellipse2D.Double _cursor, _cursor2;
	private final int _1st = 20, _2nd = 72, _3rd = 124, _4th = 176, _5th = 228, _6th = 280;
	private final int _main = 7, _option = 270, stat = 190, swap = 210, hm1 = 230, hm2=250, hm3 = 270, hm4=290;
	private TextPanel _text;
	private Graphics2D _g2;
	protected BeltPanel belt;
	private boolean statVisible = false, statVisible2 = false, optionVisible = false, swapVisible = false, notifyVisible=false;
	private boolean _doubleCheck=false;
	private boolean _flyUsed = false, _flashUsed = false, _flyForbidden=false, _teleportUsed = false, _digUsed = false, _digForbidden=false;
	private Pokemon statFocus;
	private Rectangle2D.Double beltBG;
	private int numberOfHMs=0;
	private PokemonBeltScreen2 _this;
	private Vector<Integer> iHM = new Vector<Integer>();
	
	public PokemonBeltScreen2(GameBoyScreen gbs){
		super();
		_gbs = gbs;
		_this = this;
		this.setPreferredSize(new Dimension(380,360));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		
		_cursor = new Ellipse2D.Double();
		_cursor.height = 5;
		_cursor.width = 5;
		_cursor.x = _main;
		_cursor.y = _1st;
		
		_cursor2  = new Ellipse2D.Double();
		_cursor2.height = 5;
		_cursor2.width = 5;
		_cursor2.x = _option;
		_cursor2.y = stat;
		
		_text = new TextPanel();
		this.add(_text, BorderLayout.SOUTH);
		
		_text.string1 = "Choose a POKEMON.";
		_text.string2 = "";
		
		belt = new BeltPanel();
		this.add(belt, BorderLayout.CENTER);
		
		beltBG = new Rectangle2D.Double();
		beltBG.x=-10;
		beltBG.y=-10;
		beltBG.width=500;
		beltBG.height=500;
	}
	
	
	public int howManyHMs(){
		int i=0;
		iHM.clear();
		
		if(statFocus.hasAttackNamed("Fly")){
			i++;
			iHM.add(statFocus.whichAttackIsNamed("Fly"));
		}
		if(statFocus.hasAttackNamed("Flash")){
			i++;
			iHM.add(statFocus.whichAttackIsNamed("Flash"));
		}
		if(statFocus.hasAttackNamed("Softboiled")){
			i++;
			iHM.add(statFocus.whichAttackIsNamed("Softboiled"));
		}
		if(statFocus.hasAttackNamed("Teleport")){
			i++;
			iHM.add(statFocus.whichAttackIsNamed("Teleport"));
		}
		if(statFocus.hasAttackNamed("Dig")){
			i++;
			iHM.add(statFocus.whichAttackIsNamed("Dig"));
		}
		return i;
	}
	
	public void Up(){
		if(_gbs.map.isVisible()){
			_gbs.map.Up();
			return;
		}
		
		if(!optionVisible && !statVisible && !statVisible2 && !swapVisible && !notifyVisible){
		switch((int)_cursor.y){
		case _1st: break;
		case _2nd: _cursor.y = _1st; this.repaint();
			break;
		case _3rd: _cursor.y = _2nd; this.repaint();
			break;
		case _4th: _cursor.y = _3rd; this.repaint();
			break;
		case _5th: _cursor.y = _4th; this.repaint();
			break;
		case _6th: _cursor.y = _5th; this.repaint();
			break;
		}
		}
		else if(optionVisible && !swapVisible && !notifyVisible){
			switch((int)_cursor2.y){
			case hm1: _cursor2.y = swap;
				break;
			case hm2: _cursor2.y = hm1;
				break;
			case hm3: _cursor2.y = hm2;
				break;
			case hm4: _cursor2.y = hm3;
				break;
			case swap: _cursor2.y = stat;
				break;
			}
		}
		else if(swapVisible && !notifyVisible){
			switch((int)_cursor2.y){
			case _1st: break;
			case _2nd:
				if(_cursor.y != _1st) _cursor2.y = _1st;
				break;
			case _3rd:
				if(_cursor.y != _2nd) _cursor2.y = _2nd;
				else _cursor2.y = _1st;
				break;
			case _4th:
				if(_cursor.y != _3rd) _cursor2.y = _3rd;
				else _cursor2.y = _2nd;
				break;
			case _5th:
				if(_cursor.y != _4th) _cursor2.y = _4th;
				else _cursor2.y = _3rd;
				break;
			case _6th:
				if(_cursor.y != _5th) _cursor2.y = _5th;
				else _cursor2.y = _4th;
				break;
			}
		}
	}
	
	public void Down(){
		if(_gbs.map.isVisible()){
			_gbs.map.Down();
			return;
		}
		
		int num = _gbs.getPlayer().getAllActive().size();
		
		if(!optionVisible && !statVisible && !statVisible2 && !swapVisible && !notifyVisible){
			
			switch((int)_cursor.y){
			case _1st: if(num > 1) {_cursor.y = _2nd; this.repaint();}
				break;
			case _2nd: if(num > 2) { _cursor.y = _3rd; this.repaint();}
				break;
			case _3rd: if(num > 3) {_cursor.y = _4th; this.repaint();}
				break;
			case _4th: if(num > 4) { _cursor.y = _5th; this.repaint();}
				break;
			case _5th: if(num > 5) {_cursor.y = _6th; this.repaint();}
				break;
			case _6th: break;
			}
		}
		else if(optionVisible && !swapVisible && !notifyVisible){
			switch((int)_cursor2.y){
			case stat: _cursor2.y = swap;
				break;
			case swap: if(this.howManyHMs()>0) _cursor2.y = hm1;
				break;
			case hm1: if(this.howManyHMs()>1) _cursor2.y = hm2;
				break;
			case hm2: if(this.howManyHMs()>2) _cursor2.y = hm3;
				break;
			case hm3: if(this.howManyHMs()>3) _cursor2.y = hm4;
				break;
			}
		}
		else if(swapVisible && !notifyVisible){
			switch((int) _cursor2.y){
			case _1st: 
				if(num > 1 && _cursor.y != _2nd) _cursor2.y = _2nd;
				else if(num > 2 && _cursor.y == _2nd) _cursor2.y = _3rd;		
				break;
			case _2nd:
				if(num > 2 && _cursor.y != _3rd) _cursor2.y = _3rd;
				else if(num > 3 && _cursor.y == _3rd) _cursor2.y = _4th;
				break;
			case _3rd:
				if(num > 3 && _cursor.y != _4th) _cursor2.y = _4th;
				else if(num > 4 && _cursor.y == _4th) _cursor2.y = _5th;
				break;
			case _4th:
				if(num > 4 && _cursor.y != _5th) _cursor2.y = _5th;
				else if(num > 5 && _cursor.y == _5th) _cursor2.y = _6th;
				break;
			case _5th:
				if(num > 5 && _cursor.y != _6th) _cursor2.y = _6th;
				break;
			case _6th:
				break;
			}
		}
	}
	
	public void A_Button(){		
		if(_gbs.map.isVisible()){
			_gbs.map.A_Button();
			return;
		}
		
		if(!optionVisible && !statVisible && !statVisible2 && !swapVisible && !notifyVisible){
			optionVisible = true;
			SysOut.print("OPTION?");
			switch((int)_cursor.y){
			case _1st: statFocus = _gbs.getPlayer().getActivePokemon(0); break;
			case _2nd: statFocus = _gbs.getPlayer().getActivePokemon(1); break;
			case _3rd: statFocus = _gbs.getPlayer().getActivePokemon(2); break;
			case _4th: statFocus = _gbs.getPlayer().getActivePokemon(3); break;
			case _5th: statFocus = _gbs.getPlayer().getActivePokemon(4); break;
			case _6th: statFocus = _gbs.getPlayer().getActivePokemon(5); break;
			}
		}
		else{
			if(!statVisible && !statVisible2 && !swapVisible && !notifyVisible){
				this.howManyHMs(); //Generate the iHM vector;
				switch((int) _cursor2.y){
					case stat: 
						optionVisible = false;
						statVisible = true;
						break;
					case swap: //TODO
						swapVisible = true;
						_cursor2.x = _main;
						_cursor2.y = _1st;
						if(_cursor.y == _1st){
							_cursor2.y = _2nd;
						}
						break;
					case hm1:
						this.firstStepHM(0);
						break;
					case hm2:
						this.firstStepHM(1);
						break;
					case hm3:
						this.firstStepHM(2);
						break;
					case hm4:
						this.firstStepHM(3);
						break;
				}
			}
			else if(!statVisible2 && !swapVisible  && !notifyVisible){
				statVisible = false;
				statVisible2 = true;
			}
			else if(!swapVisible  && !notifyVisible){
				statVisible2 = false;
			}
			else if(swapVisible && _gbs.getPlayer().getAllActive().size() > 1  && !notifyVisible){
				for(int i = 0; i < _gbs.getPlayer().getAllActive().size(); i++){
					SysOut.print(_gbs.getPlayer().getActivePokemon(i).getName() + " @ " + _gbs.getPlayer().getActivePokemon(i).getBelt());
				}
				swapVisible = false;
				SysOut.print("SWITCHED WITH: " + _cursor2.y);
				Pokemon temp = null;
				switch((int)_cursor2.y){
				case _1st: 
					temp = _gbs.getPlayer().getActivePokemon(0);
					break;
				case _2nd:
					temp = _gbs.getPlayer().getActivePokemon(1);
					break;
				case _3rd:
					temp = _gbs.getPlayer().getActivePokemon(2);
					break;
				case _4th:
					temp = _gbs.getPlayer().getActivePokemon(3);
					break;
				case _5th:
					temp = _gbs.getPlayer().getActivePokemon(4);
					break;
				case _6th:
					temp = _gbs.getPlayer().getActivePokemon(5);
					break;
				}
				int b = statFocus.getBelt();
				statFocus.setBelt(temp.getBelt());
				temp.setBelt(b);
				_gbs.getPlayer().swapPokemon(b-1, statFocus.getBelt()-1);
				for(int i = 0; i < _gbs.getPlayer().getAllActive().size(); i++){
					SysOut.print(_gbs.getPlayer().getActivePokemon(i).getName() + " @ " + _gbs.getPlayer().getActivePokemon(i).getBelt());
				}
				this.B_Button();
				this.repaint();
			}
			else if (notifyVisible){
				
				switch((int)_cursor2.y){
					case hm1:
						this.secondStepHM(0);						
						break;
					case hm2: 
						this.secondStepHM(1);
						break;
					case hm3: 
						this.secondStepHM(2);
						break;
					case hm4: 
						this.secondStepHM(3);
						break;
				}
				
				//DO NOT move this If clause.
				if(!_doubleCheck){
					notifyVisible=false;
					_text.string1="Choose a POKEMON.";
					_text.string2="";
					
				}
			}
		}
		
		
//		if(!optionVisible && !statVisible && !statVisible2){
//			optionVisible = true;
//			
//			switch((int)_cursor.y){
//			case _1st: statFocus = _gbs.getPlayer().getActivePokemon(0); break;
//			case _2nd: statFocus = _gbs.getPlayer().getActivePokemon(1); break;
//			case _3rd: statFocus = _gbs.getPlayer().getActivePokemon(2); break;
//			case _4th: statFocus = _gbs.getPlayer().getActivePokemon(3); break;
//			case _5th: statFocus = _gbs.getPlayer().getActivePokemon(4); break;
//			case _6th: statFocus = _gbs.getPlayer().getActivePokemon(5); break;
//			}
//		}
//		else if(_cursor2.y == stat && !statVisible && !statVisible2){
//			optionVisible = false;
//			statVisible = true;
//			
//			_cursor2.y = stat;
//		
//		}
//		else if(statVisible && !statVisible2){
//			statVisible = false;
//			statVisible2 = true;
//		}
//		else if(statVisible2){
//			statVisible2 = false;
//		}
		
		this.repaint();
	}
	
	
	public void firstStepHM(int i){
		notifyVisible=true;
		
		if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Fly"){
			if(_gbs.getCurrentPanel().isOutdoors()){
				_flyUsed=true;
				_flyForbidden=false;
			}
			else{
				_flyForbidden=true;
				_flyUsed=false;
			}
		}
		else if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Teleport"){
			_teleportUsed=true;
			_doubleCheck=false;
		}
		else if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Dig"){
			//This uniquely defines caves:
			if(!_gbs.getCurrentPanel().isOutdoors() && _gbs.getCurrentPanel().canBikeHere()){
				_digUsed=true;
				_digForbidden=false;
			}
			else{
				_digForbidden=true;
				_digUsed=false;
			}
		}
		else if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Softboiled"){}
		else if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Flash"){
			_flashUsed=true;
		}
	}
	
	public void secondStepHM(int i){
		if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Fly"){this.A_for_Fly();}
		else if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Teleport"){
			if(!_doubleCheck){
				_doubleCheck=true;
			}
			else{
				this.A_for_Teleport();
				_doubleCheck=false;
			}
		}
		else if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Dig"){this.A_for_Dig();}
		else if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Softboiled"){this.A_for_Softboiled();}
		else if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Flash"){this.A_for_Flash();}
	}
	
	
	public void A_for_Fly(){
		if(_gbs.getCurrentPanel().isOutdoors()){
			_gbs.setFlying(true);
			if(!_gbs.map.isVisible()){
				SysOut.print("Hi Map!");
				  _gbs.getCurrentPanel().setCurrentMenu(_gbs.map);
				  _gbs.getCurrentPanel().getCurrentMenu().setVisible(true);
				  _gbs.pbs.setVisible(false);
				  _gbs.ii.setVisible(false);
				  _gbs.pis.setVisible(false);
				  _gbs.pds.setVisible(false);
				  _gbs.getCurrentPanel().setMenuVisible(false);
				  _gbs.getCurrentPanel().add(_gbs.getCurrentPanel().getCurrentMenu(), BorderLayout.CENTER);
			}
			SysOut.print("FLY AWAY");
			_flyUsed=false;
			optionVisible=false;
			_cursor2.y = stat;
		}
		else{
			_flyUsed=false;
			_flyForbidden=false;
		}
	}
	public void A_for_Teleport(){
		SysOut.print("Teleporting...");
		_teleportUsed=false;
		optionVisible=false;
		_cursor2.y = stat;
		this.B_Button();
		_gbs.setTeleport(true);
		_gbs.setFlying(false);
		_gbs.setDigging(false);
		 _gbs.pbs.setVisible(false);
		 _gbs.ii.setVisible(false);
		  _gbs.pis.setVisible(false);
		  _gbs.pds.setVisible(false);
		  _gbs.getCurrentPanel().setMenuVisible(false);
		_gbs.getCurrentPanel().escapeTimerLeave.start();
	}
	
	public void A_for_Flash(){
		if(_gbs.getCurrentPanel().isDarkRoom()){
			_gbs.getCurrentPanel().setFlash(true);
		}
		 _gbs.pbs.setVisible(false);
		 _gbs.ii.setVisible(false);
		  _gbs.pis.setVisible(false);
		  _gbs.pds.setVisible(false);
		  _gbs.getCurrentPanel().setMenuVisible(false);
		_gbs.getCurrentPanel().B_Button();
		_gbs.getCurrentPanel().B_Button();
		_gbs.getCurrentPanel().B_Button();
		_flashUsed=false;
		optionVisible=false;
		_cursor2.y = stat;
		_gbs.getCurrentPanel()._busy=false;
		SysOut.print("USED FLASH");
		

	}
	public void A_for_Softboiled(){}
	public void A_for_Dig(){
		
		if(!_gbs.getCurrentPanel().isOutdoors() && _gbs.getCurrentPanel().canBikeHere()){
			_digUsed=false;
			_digForbidden=false;
			optionVisible=false;
			_cursor2.y = stat;
			this.B_Button();
			_gbs.setDigging(true);
			 _gbs.pbs.setVisible(false);
			 _gbs.ii.setVisible(false);
			  _gbs.pis.setVisible(false);
			  _gbs.pds.setVisible(false);
			  _gbs.getCurrentPanel().setMenuVisible(false);
			_gbs.getCurrentPanel().escapeTimerLeave.start();
		}
		else{
			_digUsed=false;
			_digForbidden=false;
		}
		
	}
	
	
	public void B_Button(){
		if(_gbs.map.isVisible()){
			if(optionVisible){
				optionVisible = false;
				_cursor2.y = stat;
				_cursor2.x = _option;
			}
			_flyUsed=false;
			_flyForbidden=false;
			_gbs.setFlying(false);
			_gbs.map.B_Button();
			return;

		}
		
		//////////DONT CHANGE THE IF STATEMENT ORDER//////////
		if(notifyVisible){
			if (_teleportUsed && !_doubleCheck){
				_doubleCheck=false;
				_teleportUsed=false;
				notifyVisible=false;
				_text.string1="Choose a POKEMON.";
				_text.string2="";
				
			}
			else{
				this.A_Button();
				SysOut.print("A used.");
			}
		}
		else if(swapVisible){
			swapVisible = false;
			_cursor2.y = stat;
			_cursor2.x = _option;
		}
		else if(statVisible || statVisible2){
			statVisible = false;
			statVisible2 = false;
		}
		else if(optionVisible){
			optionVisible = false;
			_cursor2.y = stat;
			_cursor2.x = _option;
		}
		else{
			_cursor.y = _1st;
			this.setVisible(false);
		}
		this.repaint();
	}
	
	public void Left(){
		if(_gbs.map.isVisible()){
			_gbs.map.Left();
			return;
		}
	}
	
	public void Right(){
		if(_gbs.map.isVisible()){
			_gbs.map.Right();
			return;
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		belt.repaint();
		_text.repaint();
	}

	protected class BeltPanel extends JPanel{
		
		public BeltPanel(){
			super();
			
			this.setBackground(Color.WHITE);
		}
		
		public void paintHMIndex(int i){
			if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Fly"){
				_g2.drawString("FLY", 280, 235+20*i);
			}
			if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Flash"){
				_g2.drawString("FLASH", 280, 235+20*i);
			}
			if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Teleport"){
				_g2.drawString("TELEPORT", 280, 235+20*i);
			}
			if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Softboiled"){
				_g2.drawString("SOFTBOILED", 280, 235+20*i);
			}
			if(statFocus.getAttacks().get(iHM.get(i)).getName()=="Dig"){
				_g2.drawString("DIG", 280, 235+20*i);
			}
		}
		public void paintHMOptions(){
			if(_this.howManyHMs()==0){
				//NOTHING.
			}
			if(_this.howManyHMs()==1){
				this.paintHMIndex(0);
			}
			if(_this.howManyHMs()==2){
				this.paintHMIndex(0);
				this.paintHMIndex(1);
			}
			if(_this.howManyHMs()==3){
				this.paintHMIndex(0);
				this.paintHMIndex(1);
				this.paintHMIndex(2);
			}
			if(_this.howManyHMs()==4){
				this.paintHMIndex(0);
				this.paintHMIndex(1);
				this.paintHMIndex(2);
				this.paintHMIndex(3);
			}
			
		}
		
		public void paintComponent(Graphics g){
			_g2 = (Graphics2D) g;
			
			_g2.setStroke(new BasicStroke(2));
			_g2.setFont(_gbs.getFont());
			_g2.setColor(Color.WHITE);
			_g2.fill(beltBG);
						
			for(int i = 0; i < _gbs.getPlayer().getAllActive().size(); i++){
				Pokemon focus = _gbs.getPlayer().getActivePokemon(i);
				
				Rectangle2D.Double iconBG = new Rectangle2D.Double();
				iconBG.height = 50;
				iconBG.width = 50;
				iconBG.x = 15;
				iconBG.y = 5+(52*i);
				_g2.setColor(Color.WHITE);
				_g2.fill(iconBG);
				
				if(focus.getCurrentHP() != 0){
					focus.getIcon().paintIcon(this, _g2, 15, 5 + (52*i));
				}
				else{
					_g2.drawImage(focus.getDeadIcon(), null, 15, 5+(52*i));
				}
				
				_g2.setColor(Color.BLACK);
				
				_g2.drawString(focus.getName(), 50, 25 + (52*i));
				
				Rectangle2D.Double outline = new Rectangle2D.Double();
				outline.height = 15;
				outline.width = 100;
				outline.x = 160;
				outline.y = 15 + (52*i);
				_g2.draw(outline);
				
				Rectangle2D.Double health = new Rectangle2D.Double();
				health.height = 13;
				health.width = (int)(98*((double)focus.getCurrentHP()/focus.getMaxHP()));
				health.x = 161;
				health.y = 16 + (52*i);
				
				if((double)focus.getCurrentHP()/focus.getMaxHP() > .5){
					_g2.setColor(Color.GREEN);
				}
				else{
					if((double)focus.getCurrentHP()/focus.getMaxHP() > .25){
						_g2.setColor(Color.ORANGE);
					}
					else{
						_g2.setColor(Color.RED);
					}
				}
				_g2.fill(health);
				_g2.setColor(Color.BLACK);
				
				_g2.drawString(focus.getCurrentHP() + "/" + focus.getMaxHP(), 267, 27 +(52*i));
				
				if(focus.getStatus() != 0)
					_g2.drawString(focus.getStatusAcro(), 342, 27 + (52*i));
				else
					_g2.drawString("Lv:" + focus.getLevel(), 332, 27 + (52*i));
			}
			
			_g2.draw(_cursor);
			
			if(optionVisible){
				Rectangle2D.Double optBG = new Rectangle2D.Double();
				if(_this.howManyHMs()==4){
					optBG.height = 125;
				}
				else if(_this.howManyHMs()==3){
					optBG.height = 105;
				}
				else if(_this.howManyHMs()==2){
					optBG.height = 85;
				}
				else if(_this.howManyHMs()==1){
					optBG.height = 65;
				}
				else{
					optBG.height = 45;
				}
				
				optBG.width = 120;
				optBG.x = 260;
				optBG.y = 180;
				
				_g2.setColor(Color.LIGHT_GRAY);
				_g2.fill(optBG);
				
				_g2.setColor(Color.DARK_GRAY);
				_g2.draw(_cursor2);
				
				_g2.drawString("STATS", 280, stat+5);
				_g2.drawString("SWITCH", 280, swap+5);
				
				
				if(_this.howManyHMs()>0){
					_g2.setColor(Color.BLUE);
					this.paintHMOptions();
				}
				
					
				if(notifyVisible){
					_g2.setColor(Color.BLACK);
									
					if(_flyUsed){
						_text.string1=""+ statFocus.getName()+ " used Fly!";
					}
					
					else if(_flyForbidden){
						_text.string1="Can't use Fly in here!";
					}
					else if(_flashUsed){
						_text.string1=""+ statFocus.getName()+ " used Flash!";
						if(_gbs.getCurrentPanel().isDarkRoom()){
							_text.string2="Light diffuses into the room.";
						}
						else{
							_text.string2="But the room is already lit.";
						}
					}
					else if(_digUsed){
						_text.string1=""+ statFocus.getName()+ " used Dig!";
					}
					else if(_digForbidden){
						_text.string1="Can't use Dig in here!";
					}
					else if(_teleportUsed && !_doubleCheck){
						_text.string1="Teleport to most recent PokeCenter?";
						_text.string2="A = Yes       B = No";
					}
					else if(_teleportUsed && _doubleCheck){
						_text.string1=""+ statFocus.getName()+ " used Teleport!";
						_text.string2="";
					}

				}
					
			}	
			
			if(statVisible || statVisible2){
				Rectangle2D.Double statBG = new Rectangle2D.Double();
				statBG.height = 200;
				statBG.width = 200;
				statBG.x = 90;
				statBG.y = 60;
				
				Rectangle2D.Double statOut = new Rectangle2D.Double();
				statOut.height = 198;
				statOut.width = 198;
				statOut.x = 91;
				statOut.y = 61;
				
				_g2.setColor(Color.WHITE);
				_g2.fill(statBG);
				
				_g2.setColor(Color.BLACK);
				_g2.draw(statOut);
				
				_g2.drawString(statFocus.getName(), 95, 75);
				_g2.drawString("LVL: " + statFocus.getLevel(), 95, 95);
				
				_g2.drawString("EXP: " + statFocus.getExp() + "/" + (NewBattleScreen.getToNextLevel(statFocus)), 95, 115);
				
				if(statFocus.getGIF()!=null){
					statFocus.getGIF().paintIcon(this, _g2, 230, 82);
					
				}
				else{
					_g2.drawImage(statFocus.getFrontImage(), null, 190, 60);
				}
				
				
				
				RoundRectangle2D.Double t1BG = new RoundRectangle2D.Double();
				t1BG.height= 20;
				t1BG.width = 60;
				t1BG.x = 94;
				t1BG.y = 145;
				t1BG.archeight = 10;
				t1BG.arcwidth = 10;
				
				if(statFocus.getType2() == Types.NONE){
					_g2.drawString("Type:", 95, 135);
					_g2.setColor(statFocus.getType1().getColor());
					_g2.fill(t1BG);
					_g2.setFont(new Font("Courier New", Font.PLAIN, 12));
					
					String s = statFocus.getType1().toString();
					
					float sw = (float)_g2.getFont().getStringBounds(s, _g2.getFontRenderContext()).getWidth();
					LineMetrics lm = _g2.getFont().getLineMetrics(s, _g2.getFontRenderContext());
					float sh = lm.getAscent() + lm.getDescent();
					
					float sx = (float) (t1BG.x + (t1BG.width - sw)/2);
					float sy = (float) (t1BG.y + (t1BG.height + sh)/2) - lm.getDescent();
					
					_g2.setColor(Color.DARK_GRAY);
					_g2.drawString(s, sx+1, sy+1);
					
					_g2.setColor(Color.WHITE);
					_g2.drawString(s, sx, sy);
				}
				else{
					RoundRectangle2D.Double t2BG = new RoundRectangle2D.Double();
					t2BG.height = 20;
					t2BG.width = 60;
					t2BG.x = 156;
					t2BG.y = 145;
					t2BG.archeight = 10;
					t2BG.arcwidth = 10;
					
					_g2.drawString("Types: ", 95, 135);
					_g2.setColor(statFocus.getType1().getColor());
					_g2.fill(t1BG);
					_g2.setColor(statFocus.getType2().getColor());
					_g2.fill(t2BG);
					_g2.setFont(new Font("Courier New", Font.PLAIN, 12));
					
					String s1 = statFocus.getType1().toString();
					String s2 = statFocus.getType2().toString();
					
					float sw1 = (float)_g2.getFont().getStringBounds(s1, _g2.getFontRenderContext()).getWidth();
					LineMetrics lm1 = _g2.getFont().getLineMetrics(s1, _g2.getFontRenderContext());
					float sh1 = lm1.getAscent() + lm1.getDescent();
					
					float sx1 = (float) (t1BG.x + (t1BG.width - sw1)/2);
					float sy1 = (float) (t1BG.y + (t1BG.height + sh1)/2) - lm1.getDescent();
					
					float sw2 = (float)_g2.getFont().getStringBounds(s2, _g2.getFontRenderContext()).getWidth();
					LineMetrics lm2 = _g2.getFont().getLineMetrics(s2, _g2.getFontRenderContext());
					float sh2 = lm2.getAscent() + lm2.getDescent();
					
					float sx2 = (float) (t2BG.x + (t2BG.width - sw2)/2);
					float sy2 = (float) (t2BG.y + (t2BG.height + sh2)/2) - lm2.getDescent();
					
					_g2.setColor(Color.DARK_GRAY);
					_g2.drawString(s1, sx1+1, sy1+1);
					_g2.drawString(s2, sx2+1, sy2+1);
					
					_g2.setColor(Color.WHITE);
					_g2.drawString(s1, sx1, sy1);
					_g2.drawString(s2, sx2, sy2);
				}
				_g2.setFont(_gbs.getFont());
				_g2.setColor(Color.BLACK);
					
				_g2.drawString("------------------", 95, 178);
				
				if(statVisible){
					int count = 0;
					for(int i = 0; i < statFocus.getAttacks().size(); i++){
						_g2.drawString(statFocus.getAttacks().get(i).getName(), 95, 193 + (20*i));
						_g2.drawString(statFocus.getAttacks().get(i).getCurrentPP() + "/" + statFocus.getAttacks().get(i).getMaxPP(), 225, 193 + (20*i));
						count++;
					}
					for(int i = count; i < 4; i++){
						_g2.drawString("---", 95, 193 + (20*i));
					}
				}
				if(statVisible2){
					_g2.drawString("ATK: " + statFocus.getAtkStat(), 95, 188);
					_g2.drawString("DEF: " + statFocus.getDefStat(), 95, 205);
					_g2.drawString("SP ATK: " + statFocus.getSpAtkStat(), 95, 222);
					_g2.drawString("SP DEF: " + statFocus.getSpDefStat(), 95, 239);
					_g2.drawString("SPEED: " + statFocus.getSpeed(), 95, 256);
				}
				
				
			}
		}
	}
	
	private class TextPanel extends JPanel{
		private Rectangle2D.Double _textOutline;
		private Font _textFont;
		private String string1, string2;
		
		public TextPanel(){
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(200,50));
			
		//	_textFont = _gbs.getFont();
			_textFont = new Font("Courier New", Font.BOLD, 16);
			_textOutline = new Rectangle2D.Double();
			_textOutline.height = 45;
			_textOutline.width = 370;
			_textOutline.x = 5;
			_textOutline.y = 1;

			string1 = "Choose a POKEMON.";
			string2 = "";
			
			this.repaint();

		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			_g2 = (Graphics2D) g;
			
			_g2.setStroke(new BasicStroke(2));
			_g2.setColor(Color.BLACK);
			_g2.draw(_textOutline);
			_g2.setFont(_textFont);
			
			if(_flyUsed || _flashUsed || _digUsed || _teleportUsed){
				_g2.setColor(Color.BLUE);
			}
			else if (_flyForbidden || _digForbidden){
				_g2.setColor(Color.RED);
			}
			else{
				_g2.setColor(Color.BLACK);
			}
			this.displayText(string1, string2);
		}
		
		public void displayText(String firstline, String secondline){
			_g2.drawString(firstline, 20, 20);
			
			if(secondline != null)
				_g2.drawString(secondline, 20, 40);
		}
		public void displayTextCustomY(String firstline, String secondline, int Y){
			_g2.drawString(firstline, 20, Y);
			
			if(secondline != null)
				_g2.drawString(secondline, 20, Y+20);
		}
	}
}
