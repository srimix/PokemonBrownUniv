package PokemonDemo;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PImg {

	public static BufferedImage[] front, back,  faint;
	public static ImageIcon[] icon, anim;
	
	public static void initialize(){
		try{
		
			front = new BufferedImage[218];
			back =  new BufferedImage[218];
			faint = new BufferedImage[218];
			
			icon = new ImageIcon[218];
			anim = new ImageIcon[218];
			
			for(int i = 1; i < 153; i++){
				front[i] = ImageIO.read(PImg.class.getResource("/PokemonImages/Front/"+i+".png"));
				back[i] = ImageIO.read(PImg.class.getResource("/PokemonImages/Back/"+i+".png"));
				faint[i] = ImageIO.read(PImg.class.getResource("/PokemonImages/Icon/"+i+".gif"));
				
				icon[i] = new ImageIcon(PImg.class.getResource("/PokemonImages/Icon/"+i+".gif"));
				anim[i] = new ImageIcon(PImg.class.getResource("/PokemonImages/Animation/"+i+".gif"));
			}
			front[217] = ImageIO.read(PImg.class.getResource("/PokemonImages/Front/"+217+".png"));
			back[217] = ImageIO.read(PImg.class.getResource("/PokemonImages/Back/"+217+".png"));
			faint[217] = ImageIO.read(PImg.class.getResource("/PokemonImages/Icon/"+217+".gif"));
			
			icon[217] = new ImageIcon(PImg.class.getResource("/PokemonImages/Icon/"+217+".gif"));
			anim[217] = new ImageIcon(PImg.class.getResource("/PokemonImages/Animation/"+217+".gif"));
			
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
	}
}
