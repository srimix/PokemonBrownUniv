package PokemonDemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlayerInfoScreen extends JPanel{

	private GameBoyScreen _gbs;
	private Graphics2D _g2;
	private BufferedImage _bg;
	private BufferedImage[] faces = new BufferedImage[8], badges = new BufferedImage[8];

	public PlayerInfoScreen(GameBoyScreen gbs){
		SysOut.print("New Player Info Screen created...");
		_gbs = gbs;
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(380,360));
		
		try {
			_bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/playerMenu.png"));
			
			for(int i = 0; i < faces.length; i++){
				faces[i] = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerMenu/f"+(i+1)+".png"));
				badges[i] = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerMenu/badge"+(i+1)+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTimeString(){
		int timeSec = _gbs.getTimeInSeconds();
		String timeString = "";
		
        int hours = (int)timeSec / 3600;
        int minutes = (int)timeSec / 60 - hours*60;
        int seconds = (int)timeSec - minutes*60 - hours*3600;
		
        DecimalFormat timeFmt = new DecimalFormat ("00");
        
        timeString = hours+":"+timeFmt.format(minutes)+":"+timeFmt.format(seconds);
        
		return timeString;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		_g2 = (Graphics2D) g;
		
		_g2.drawImage(_bg, null, 0, 0);
		
		_g2.setFont(new Font("Lucida Console", Font.PLAIN, 22));
		_g2.setColor(Color.BLACK);
		
		_g2.drawString(_gbs.getPlayer().getPlayerName(), 140, 56);
		int m = _gbs.getPlayer().getMoney();
		DecimalFormat  f = new DecimalFormat("###,###,###");
		String s = f.format(m);
		_g2.drawString(s, 175, 97);
		
		_g2.drawString(this.getTimeString(), 140, 138);
		
		for(int i = 0; i < 8; i++){
			if(_gbs.getPlayer().isGymLeaderDefeated(i+1)){
				if(i < 4)
					_g2.drawImage(badges[i], null, 60+(77*i), 230);
				else
					_g2.drawImage(badges[i], null, 60+(77*(i-4)), 292);
			}
			else{
				if(i < 4)
					_g2.drawImage(faces[i], null, 60+(77*i), 230);
				else
					_g2.drawImage(faces[i], null, 60+(77*(i-4)), 292);
			}
		}	
	}
}