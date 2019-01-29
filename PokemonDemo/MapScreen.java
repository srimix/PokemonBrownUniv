package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapScreen extends JPanel{
	
	private BufferedImage _map, _hereUnder, _hereFlyUnder;
	private ImageIcon _here, _hereFly;
	private int cursor = 1;
	private String location="";
	private GameBoyScreen _gbs;
	private boolean _readyForTakeoff=false;
	//private final int KEENEY=1, ROUTE1=2, WRISTON=3, PATRIOTS=4, MAIN_GREEN=5, ROUTE2=6, BIOMED=7, SIDNEY=8, ROUTE3=9, LINCOLN=10, SCIENCE=11;
	
	public MapScreen(GameBoyScreen gbs){
		super();
		_gbs = gbs;
		
		SysOut.print("fly: " + _gbs.isFlying());
		try {
			_hereFly = new ImageIcon(this.getClass().getResource("/PokemonFiles/Map/Fly.gif"));
			_hereFlyUnder=ImageIO.read(this.getClass().getResource("/PokemonFiles/Map/FlyStatic.png"));
			_here = new ImageIcon(this.getClass().getResource("/PokemonFiles/Map/Here.gif"));
			_hereUnder=ImageIO.read(this.getClass().getResource("/PokemonFiles/Map/HereStatic.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Run this instead so the string is initialized.
		updateMap();
		
		this.setPreferredSize(new Dimension(380,360));
		this.setVisible(false);
	}

	public void Up(){
		this.Right();
	}
	
	public void Left(){
		if(_gbs.isFlying()){
			this.toNextVisitedRoom(-1);
		}
		else{
			SysOut.print("MAP Left-DOWN");
			cursor--;
			if(cursor < 1) cursor = 12;
		}
		updateMap();
	}
	
	public void Down(){
		this.Left();
	}
	
	public void Right(){
		if(_gbs.isFlying()){
			this.toNextVisitedRoom(1);
		}
		else{
			SysOut.print("MAP Right-UP");
			cursor++;
			if(cursor > 12) cursor = 1;
		}
		updateMap();
	}
	
	public void B_Button(){
		if(this.isVisible()){
			this.setVisible(false);
			_gbs.getCurrentPanel()._busy=false;
			cursor = 1;
			_readyForTakeoff=false;
			_gbs.setFlying(false);
		}
	}

	public void B_ButtonCustom(){
		if(this.isVisible()){
			this.setVisible(false);
			_gbs.getCurrentPanel()._busy=false;
			cursor = 1;
		}
	}
	
	public void A_Button(){
		if(_gbs.isFlying() && !_readyForTakeoff){
			_readyForTakeoff=true;
		}
		else if(_gbs.isFlying() && _readyForTakeoff){
			_readyForTakeoff=false;
			_gbs.getCurrentPanel().setFlyDest(this.cursorToRoomNum());
			
			_gbs.getCurrentPanel().escapeTimerLeave.start();
			this.B_ButtonCustom();
			//_gbs.getCurrentPanel()._busy=false;
		}
		else{
			this.B_Button();
		}
	}
	
	
	public int cursorToRoomNum(){
		switch(cursor){
		case 1:
			//"Keeney Quad";
			return _gbs.KEENEY_QUAD;
		case 2:
			//"Wriston Quad";
			return _gbs.WRISTON_QUAD;
		case 3:
			//"Main Green";
			return _gbs.MAIN_GREEN;
		case 4:
			//"BioMedical Center";
			return _gbs.ROUTE_3;
		case 5:
			//"Sidney Frank";
			return 1000;
		case 6:
			//"Lincoln Field";
			return 1000;
		case 7: 
			//"Science Quad";
			return _gbs.SCIENCE_QUAD;
		case 8: 
			//"Barus and Holley";
			return _gbs.OUTSIDE_BARHOL;
		case 9: 
			//"Downtown Canal";
			return _gbs.WATERFIRE;
		case 10: 
			//"VDub Dining Hall";
			return _gbs.PEMBROKE;
		case 11: 
			//"Watson Institute";
			return _gbs.THAYER_SOUTH;
		case 12: 
			//"Josiah's";
			return _gbs.VG_QUAD;			
		}
		return 1000;
	}
	
	
	public boolean checkValidRoom(int cursor){
		if(this.cursorToRoomNum()<500){
			if(_gbs.getVisitedArray()[cursorToRoomNum()]==1){
				SysOut.print("you have visited:" + cursorToRoomNum());
				return true;
			}
		}
		
		return false;
	}
	
	public int toNextVisitedRoom(int directionToSearch){
		//Check to see if you have visited the room and also if you can even fly to that location. Some places are indoors.
		int prevCursor = cursor;
		
		
		for (int i=1;i<13;i++){
			//Search Forwards
			if (directionToSearch>0){
				cursor++;
				//Boundary Condition
				if (cursor>12){
					cursor=1;
				}
				
				//Is this a room that you can visit AND one that you have visited before?
				if(checkValidRoom(cursor)){
					return cursor;
				}
			}
			
			//Search Back
			if (directionToSearch<0){
				cursor--;
				//Boundary Condition
				if (cursor<1){
					cursor=12;
				}
				
				//Is this a room that you can visit AND one that you have visited before?
				if(checkValidRoom(cursor)){
					return cursor;
				}
			}
		}
		
		
		//Nothing found? Recover previous location.
		cursor=prevCursor;
		return prevCursor;		
		
	}
	
	public void updateMap(){
		try{
			_map = ImageIO.read(this.getClass().getResource("/PokemonFiles/Map/Map-" + cursor + ".png"));
			switch(cursor){
			case 1:
				location="Keeney Quad";
				break;
			case 2:
				location="Wriston Quad";
				break;
			case 3:
				location="Main Green";
				break;
			case 4:
				location="BioMedical Center";
				break;
			case 5:
				location="Sidney Frank";
				break;
			case 6:
				location="Lincoln Field";
				break;
			case 7: 
				location="Science Quad";
				break;
			case 8: 
				location="Barus and Holley";
				break;
			case 9: 
				location="Downtown Canal";
				break;
			case 10: 
				location="VDub Dining Hall";
				break;
			case 11: 
				location="Watson Institute";
				break;
			case 12: 
				location="Josiah's";
				break;			
			}
			this.repaint();
		}
		catch(IOException ioe){}
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		
		g2.drawImage(_map,null, -10, -38);
		g2.drawImage(_map,null, -10, -38);
		
		
		Rectangle2D.Double textBG = new Rectangle2D.Double();
		Rectangle2D.Double outline = new Rectangle2D.Double();
		
		//For flying vs not:
		if(_gbs.isFlying()){
			textBG.x = 0;
			textBG.y = 0;
			textBG.width = 204+180;
			textBG.height = 40;
			
			g2.setColor(Color.WHITE);
			g2.fill(textBG);
			outline.x = 5;
			outline.y = 5;
			outline.width = textBG.getWidth() - 10;
			outline.height = textBG.getHeight()-10;
			
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			g2.draw(outline);
			
			g2.setFont(new Font("Courier New", Font.BOLD, 16));
			g2.drawString("Fly to where?", 10, 23);
			g2.drawString(location, 200, 23);
		}
		else{			
			textBG.x = 180;
			textBG.y = 0;
			textBG.width = 204;
			textBG.height = 40;
			
			g2.setColor(Color.WHITE);
			g2.fill(textBG);
			outline.x = 185;
			outline.y = 5;
			outline.width = textBG.getWidth() - 10;
			outline.height = textBG.getHeight()-10;
			
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			g2.draw(outline);
			
			g2.setFont(new Font("Courier New", Font.BOLD, 16));
			g2.drawString(location, 200, 23);
		}
		
		
		
		//Draw the here icon.
		if(_gbs.isFlying()){
			g2.drawImage(_hereFlyUnder,null, _gbs.getCurrentPanel().getMapX()-9, _gbs.getCurrentPanel().getMapY()-110);
			_hereFly.paintIcon(this, g2, _gbs.getCurrentPanel().getMapX()-9, _gbs.getCurrentPanel().getMapY()-110);
			_hereFly.paintIcon(this, g2, _gbs.getCurrentPanel().getMapX()-9, _gbs.getCurrentPanel().getMapY()-110);
		}
		else{
			g2.drawImage(_hereUnder,null, _gbs.getCurrentPanel().getMapX(), _gbs.getCurrentPanel().getMapY()-100);
			_here.paintIcon(this, g2, _gbs.getCurrentPanel().getMapX(), _gbs.getCurrentPanel().getMapY()-100);
			//_here.paintIcon(this, g2, _gbs.getCurrentPanel().getMapX(), _gbs.getCurrentPanel().getMapY()-100);
		}
		
		
	}

}
