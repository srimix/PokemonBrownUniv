package PokemonDemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PokedexScreen extends JPanel{

	private GameBoyScreen _gbs;
	private int index = 1;
	private Graphics2D _g2;
	private Rectangle2D.Double selected = new Rectangle2D.Double();
	private BufferedImage catchBall, dexBG, map;
	private boolean descOrMap = true;
	
	public PokedexScreen(GameBoyScreen gbs){
		super();
		_gbs = gbs;
		
		selected.x=25;
		selected.y=146;
		selected.height=20;
		selected.width=138;
		
		this.setBackground(Color.DARK_GRAY);
		this.setPreferredSize(new Dimension(380,360));
		
		try {
			catchBall = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Item/item.png"));
			dexBG = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Item/dexBG.png"));
			map = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Item/map.png"));
		} catch (IOException e) {}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		_g2 = (Graphics2D) g;
		_g2.setFont(new Font("Courier New", Font.BOLD, 12));
		
		_g2.drawImage(dexBG, null, 0, 0);
		
		_g2.draw(selected);
		
		try{
			for(int i = -2; i < 3; i++){
				if((index+i > 0) && (index+i < 152) && _gbs.getPlayer().getPokedex().hasSeen((index+i))){
					_g2.drawString((index+i) + " " + Pokemon.getPokemonNameByDexNumber(index+i), 45, 160+(20*i));
					if(_gbs.getPlayer().getPokedex().hasCaught((index+i))){
						_g2.drawImage(catchBall, null, 30, 150+(20*i));
					}
				}	
				else{
					if(index+i > 0 && index+i < 152)
						_g2.drawString((index+i) + " -------", 45, 160+(20*i));
					else
						if(index+i == 152){
							if(_gbs.getPlayer().getPokedex().hasSeen((216))){
								_g2.drawString(216 + " Teddiursa", 45, 160+(20*i));
								if(_gbs.getPlayer().getPokedex().hasCaught((216))){
									_g2.drawImage(catchBall, null, 30, 150+(20*i));
								}
							}
							else{
								//_g2.drawString(216 + " -------", 45, 160+(20*i));
							}
						}
						if(index+i == 153){
							if(_gbs.getPlayer().getPokedex().hasSeen((217))){
								_g2.drawString(217 + " Ursaring", 45, 160+(20*i));
								if(_gbs.getPlayer().getPokedex().hasCaught((217))){
									_g2.drawImage(catchBall, null, 30, 150+(20*i));
								}
							}
							else{
								//_g2.drawString(217 + " -------", 45, 160+(20*i));
							}
						}
				}
			}
		
			if((index != 152 && index!= 153)){
				if(_gbs.getPlayer().getPokedex().hasSeen(index))
					_g2.drawImage(Pokemon.getPokemonByDexNumber(index).getFrontImage(), null, 245, 127);
			}
			else{
				if(index == 152)
					if(_gbs.getPlayer().getPokedex().hasSeen(216))
						_g2.drawImage(Pokemon.getPokemonByDexNumber(216).getFrontImage(), null, 245, 127);
				if(index == 153)
					if(_gbs.getPlayer().getPokedex().hasSeen(217))
						_g2.drawImage(Pokemon.getPokemonByDexNumber(217).getFrontImage(), null, 245, 127);
			}
			
			_g2.drawString("---------------", 240, 220);
			
			if(descOrMap){
				_g2.setFont(new Font("Courier New", Font.BOLD, 10)); //FIXME - font size
				if(index != 152 && index != 153){
					if(_gbs.getPlayer().getPokedex().hasCaught(index)){
						_g2.drawString(Pokemon.getLine1ByDexNum(index), 232, 231);
						_g2.drawString(Pokemon.getLine2ByDexNum(index), 232, 246);
						_g2.drawString(Pokemon.getLine3ByDexNum(index), 232, 261);
						_g2.drawString(Pokemon.getLine4ByDexNum(index), 232, 276);
					}
				}
				if(index == 152 && _gbs.getPlayer().getPokedex().hasCaught(216)){
					_g2.drawString(Pokemon.getLine1ByDexNum(index), 232, 231);
					_g2.drawString(Pokemon.getLine2ByDexNum(index), 232, 246);
					_g2.drawString(Pokemon.getLine3ByDexNum(index), 232, 261);
					_g2.drawString(Pokemon.getLine4ByDexNum(index), 232, 276);
				}
				if(index == 153 && _gbs.getPlayer().getPokedex().hasCaught(217)){
					_g2.drawString(Pokemon.getLine1ByDexNum(index), 232, 231);
					_g2.drawString(Pokemon.getLine2ByDexNum(index), 232, 246);
					_g2.drawString(Pokemon.getLine3ByDexNum(index), 232, 261);
					_g2.drawString(Pokemon.getLine4ByDexNum(index), 232, 276);
				}
			}
			else{
				if((index != 152 && index != 153 && _gbs.getPlayer().getPokedex().hasCaught(index)) ||
					index == 152 && _gbs.getPlayer().getPokedex().hasCaught(216) ||
					index == 153 && _gbs.getPlayer().getPokedex().hasCaught(217)){
						
					_g2.drawImage(map, null, 256, 220);
					//_g2.drawString("MAP", 232, 231);
				}
			}
		}
		catch(IOException ioe){}
		
	}
	
	public void A_Button(){
		
	}
	
	public void Up(){
		if(index > 1){
			index--;
		}
		descOrMap = true;
//		SysOut.print("INDEX: " + index);
		this.repaint();
	}
	
	public void Down(){
		if(index < 151 || (index < 152 && _gbs.getPlayer().getPokedex().hasSeen(216)) || (index < 153 && _gbs.getPlayer().getPokedex().hasSeen(217))){
			index++;
		}
		descOrMap = true;
//		SysOut.print("INDEX: " + index);
		this.repaint();
	}
	
	public void Left(){
		descOrMap = true;
		this.repaint();
	}
	
	public void Right(){
//		descOrMap = false;
//		this.repaint();
	}
}
