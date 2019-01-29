package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameIntroductionScreen extends PokePanel2{

	private Graphics2D _g2;
	private BufferedImage ruth, poke, player, rival, pNameSelect, rNameSelect;
	private int playerX = 125, rivalX = 125;
	private Rectangle2D.Double whiteOut, letterCursor, playerMenu, confirmName;
	private Ellipse2D.Double _cursor;
	private int whiteFade = 0;
	private Timer introTime, rtp, ptp, ptriv, rivtp, pname, rname;
	private boolean[] stage;
	private String[] stageText1;
	private String[] stageText2;
	private final static int NUM = 16;
	private String playerDefaultName = "", rivalDefaultName = "";
	private boolean fading = true, playerMenuVisible = false, rivalMenuVisible = false, cursorVis = false, playerNameSelection = false, rivalNameSelection = false, letterVis = false, confirmVis = false;
	private boolean ruthVis = true, pokeVis = false, playerVis = false, rivalVis = false, confirmChoice = false;
	private String currentInput = "";
	private PokePanel2 _this;

	public GameIntroductionScreen(GameBoyScreen gbs){
		super(gbs);
		
		_this = this;

		this.setBackground(Color.WHITE);
		
		confirmName = new Rectangle2D.Double();
		confirmName.height = 23;
		confirmName.width = 70;
		confirmName.x = 280;
		confirmName.y = 262;
		
		whiteOut = new Rectangle2D.Double();
		whiteOut.x = -10;
		whiteOut.y = -10;
		whiteOut.width = 400;
		whiteOut.height = 400;
		
		_cursor = new Ellipse2D.Double();
		_cursor.x = 10;
		_cursor.y = 75;
		_cursor.height = 5;
		_cursor.width = 5;
		
		letterCursor = new Rectangle2D.Double();
		letterCursor.x = 68;
		letterCursor.y = 166;
		letterCursor.width = 19;
		letterCursor.height = 23;
		
		playerMenu = new Rectangle2D.Double();
		playerMenu.x = 25;
		playerMenu.y = 25;
		playerMenu.width = 100;
		playerMenu.height = 150;
		
		this._darkLevel = 0;
		
		IntroTimer it = new IntroTimer();
		introTime = new Timer(10, it);
		
		RuthToPokemon r2p = new RuthToPokemon();
		rtp = new Timer(10, r2p);
		
		PokemonToPlayer p2p = new PokemonToPlayer();
		ptp = new Timer(10, p2p);
		
		PlayerToRival p2riv = new PlayerToRival();
		ptriv = new Timer(10, p2riv);
		
		RivalToPlayer riv2p = new RivalToPlayer();
		rivtp = new Timer(10, riv2p);
		
		PlayerNameChoice pnc = new PlayerNameChoice();
		pname = new Timer(10, pnc);
		
		RivalNameChoice rnc = new RivalNameChoice();
		rname = new Timer(10, rnc);
		
		stage = new boolean[NUM];
		stage[0] = true;
		
		stageText1 = new String[NUM];
		stageText2 = new String[NUM];
		
					  //"Your roommate, passed out on his bed,"	
		              
		stageText1[0] = "Hello there!";
		stageText2[0] = "Welcome to BROWN UNIVERSITY!";
		
		//openingD[0] = new Dialogue("Hello there! Welcome to BROWN UNIVERSITY!");
		
		stageText1[1] = "My name is RUTH!";
		stageText2[1] = "I'm the PRESIDENT OF BROWN!";
				
		stageText1[2] = "BROWN has some of the best POKEMON";
		stageText2[2] = "trainers in the world.";
		
		stageText1[3] = "Our open curriculum allows students";
		stageText2[3] = "to study all kinds of POKEMON.";
		
		stageText1[4] = "I study...";
		stageText2[4] = "";
		
		stageText1[5] = "Well, you'll find out eventually.";
		stageText2[5] = "";
		
		stageText1[6] = "First, what is your name?";
		stageText2[6] = "";
		
		stageText1[7] = "<Choose Your Name>";
		stageText2[7] = "";
		
		stageText1[8] = "Right! So your name is";
		stageText2[8] = playerDefaultName + ".";
		
		stageText1[9] = "This is your new hipster roommate.";
		stageText2[9] = "He just finished moving in.";
		
		stageText1[10] = "...Erm, what is his name again?";
		stageText2[10] = "";
		
		stageText1[11] = "<Choose Your Rival's Name>";
		stageText2[11] = "";
		
		stageText1[12] = "That's right! I remember now! His";
		stageText2[12] = "name is " + rivalDefaultName + ".";
		
		stageText1[13] = playerDefaultName + "!";
		stageText2[13] = "";
		
		stageText1[14] = "Your very own BROWN UNIVERSITY";
		stageText2[14] = "experience is about to unfold.";
		
		stageText1[15] = "A world of dreams and adventures";
		stageText2[15] = "with POKEMON awaits! Let's go!";
		
		//=============
		
		try{
			ruth = ImageIO.read(this.getClass().getResource("/PokemonFiles/RuthIntro/ruth3.png"));
			poke = ImageIO.read(this.getClass().getResource("/PokemonImages/Front/28BIG.png"));
			player = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/standBIG.png"));
			rival = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rivalBIG.png"));
			pNameSelect = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/playerName.png"));
			rNameSelect = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rivalName.png"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		//===============
	
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		_g2 = (Graphics2D) g;
		
 		if(ruthVis)
			_g2.drawImage(ruth, null, 125, 75);
		if(pokeVis)
			_g2.drawImage(poke, null, 85, 75);
		if(playerVis)
			_g2.drawImage(player, null, playerX, 75);
		if(rivalVis)
			_g2.drawImage(rival, null, rivalX, 75);
		
		_g2.setColor(Color.BLACK);
		if(playerMenuVisible || rivalMenuVisible){
			_g2.setStroke(new BasicStroke(2));
			_g2.draw(playerMenu);
			_g2.setFont(_gbs.getFont());
			if(playerMenuVisible){
				_g2.drawString("NEW NAME", 40, 50);
				_g2.drawString("ASH", 40, 80);
				_g2.drawString("BROWN", 40, 110);
				_g2.drawString("BRANDAN", 40, 140);
			}
			if(rivalMenuVisible){
				_g2.drawString("NEW NAME", 40, 50);
				_g2.drawString("GARY", 40, 80);
				_g2.drawString("CRIMSON", 40, 110);
				_g2.drawString("TOBY", 40, 140);
			}
		}
		
		if(cursorVis)
			_g2.draw(_cursor);
		
		_g2.setColor(new Color(255, 255, 255, 255-whiteFade));
		_g2.fill(whiteOut);
		
		_g2.setColor(Color.BLACK);
		_g2.setFont(_gbs.getFont());
		_g2.setStroke(new BasicStroke(2));
		
		if(currentInput.toCharArray().length ==7){
			letterVis = false;
		}
		
		if(playerNameSelection){
			_g2.drawImage(pNameSelect, null, 0,0);
			if(letterVis)
				_g2.draw(letterCursor);
			_g2.drawString(currentInput, 200, 120);
		}
		if(rivalNameSelection){
			_g2.drawImage(rNameSelect, null, 0,0);
			if(letterVis)
				_g2.draw(letterCursor);
			_g2.drawString(currentInput, 200, 120);
		}
		if(currentInput.toCharArray().length == 7 || confirmVis){
			_g2.setColor(Color.BLACK);
			_g2.draw(confirmName);
		}
		
		if(playerNameSelection || rivalNameSelection){
			_g2.setColor(Color.DARK_GRAY);
			_g2.drawString("ENTER", 291,281);
			_g2.setColor(Color.WHITE);
			_g2.drawString("ENTER", 290, 280);
			_g2.setColor(Color.BLACK);
		}
	
		//====================
		
		
		_g2.setStroke(new BasicStroke(2));
		_g2.setColor(Color.BLACK);
		
		_g2.setFont(_gbs.getFont());

		if(!playerNameSelection && !rivalNameSelection){
			
			for(int i = 0; i < stage.length; i++){
				if(stage[i]){
					if(i == 8 || i == 12){
						stageText2[8] = playerDefaultName + ".";
						stageText2[12] = "name is " + rivalDefaultName + ".";
						stageText1[13] = playerDefaultName + "!";
					}
						_this.drawText(_g2, stageText1[i], stageText2[i]);
				}
			}
		}
	}
	
	public void startFade(){
		introTime.start();
	}
	
	@Override
//	public void Select(){
//		M.playSoundEffect(M.JAVABOY_INTRO);
//		SysOut.print("POISON");
//	}
	
	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning()){
		if(!rivalNameSelection && !playerNameSelection){
			if(playerMenuVisible || rivalMenuVisible){
				if(playerMenuVisible){	
					switch((int)_cursor.y){
					case 45: playerNameSelection = true;
								letterVis = true;
						break;
					case 75: playerDefaultName = "ASH";
						break;
					case 105: playerDefaultName = "BROWN";
						break;
					case 135: playerDefaultName = "BRANDAN";
						break;
					}
					_gbs.getPlayer().setPlayerName(playerDefaultName);
					playerMenuVisible = false;
					_cursor.y = 75;
					cursorVis = false;
					if(!playerNameSelection)
						this.A_Button();
				}
				if(rivalMenuVisible){
					switch((int)_cursor.y){
					case 45: rivalNameSelection = true;
							 letterVis = true;
						break;
					case 75: rivalDefaultName = "GARY";
						break;
					case 105: rivalDefaultName = "CRIMSON";
						break;
					case 135: rivalDefaultName = "TOBY";
						break;
					}
					_gbs.getPlayer().setRivalName(rivalDefaultName);
					_gbs.getRival().setName(rivalDefaultName);
					rivalMenuVisible = false;
					_cursor.y = 75;
					cursorVis = false;
					if(!rivalNameSelection)
						this.A_Button();
				}
			}
			else{
	/*			if(t != null && t.isRunning()){
					t.setDelay(5);
					return;
				}
				else{
					this.setCurrentText("", "");
					if(t != null)
						t.setDelay(35);
		*/			for(int i = 0; i < stage.length; i++){
						if(stage[i]){
							stage[i] = false;
							if(i != NUM-1)
								stage[i+1] = true;
							else{
								_gbs.setIntroComplete();
								_gbs.setCurrentPanel(2);
								if(_gbs.BACKGROUND != null)
									_gbs.BACKGROUND.flush();
								_gbs.getCurrentPanel().createBaseRoom();
								_gbs.getCurrentPanel()._darkLevel = 0;
								M2.playBG(M2.KEENEY);
								//SysOut.print("KEENEY MUSIC???");
								//_gbs.playSong(M.KEENEY);
								return;
							}
					
							switch(i){
							case 1: rtp.start();
								break;
							case 5: ptp.start();
								break;
							case 6: pname.start();
								break;
							case 7: playerMenuVisible = false;
								break;
							case 8: ptriv.start();
								break;
							case 10: rname.start();
								break;
							case 11: rivalMenuVisible = false;
								break;
							case 12: rivtp.start();
								break;
							}
					
							i = 15;
						}	
					}
				
			}
			this.repaint();
		}
		else{
			if(currentInput.toCharArray().length < 7){
				switch((int)letterCursor.x){
				
				case 68:
					switch((int)letterCursor.y){
					case 166: currentInput = currentInput + "A";
						break;
					case 198: currentInput = currentInput + "G";
						break;
					case 230: currentInput = currentInput + "M";
						break;
					case 262: currentInput = currentInput + "T";
						break;
					}
					break;
				
				case 92:
					switch((int)letterCursor.y){
					case 166: currentInput = currentInput + "B";
						break;
					case 198: currentInput = currentInput + "H";
						break;
					case 230: currentInput = currentInput + "N";
						break;
					case 262: currentInput = currentInput + "U";
						break;
					}
					break;
				
				case 116:
					switch((int)letterCursor.y){
					case 166: currentInput = currentInput + "C";
						break;
					case 198: currentInput = currentInput + "I";
						break;
					case 230: currentInput = currentInput + "O";
						break;
					case 262: currentInput = currentInput + "V";
						break;
					}
					break;
				
				case 180:
					switch((int)letterCursor.y){
					case 166: currentInput = currentInput + "D";
						break;
					case 198: currentInput = currentInput + "J";
						break;
					case 230: currentInput = currentInput + "P";
						break;
					case 262: currentInput = currentInput + "W";
						break;
					}
					break;
				
				case 204:
					switch((int)letterCursor.y){
					case 166: currentInput = currentInput + "E";
						break;
					case 198: currentInput = currentInput + "K";
						break;
					case 230: currentInput = currentInput + "Q";
						break;
					case 262: currentInput = currentInput + "X";
						break;
					}
					break;
				
				case 228:
					switch((int)letterCursor.y){
					case 166: currentInput = currentInput + "F";
						break;
					case 198: currentInput = currentInput + "L";
						break;
					case 230: currentInput = currentInput + "R";
						break;
					case 262: currentInput = currentInput + "Y";
						break;
					}
					break;
				
				case 252:
					if(letterCursor.y == 230)
						currentInput = currentInput + "S";
					else
						currentInput = currentInput + "Z";
					break;
				
				case 318:
					if(letterCursor.y == 166)
						currentInput = currentInput + ".";
					else
						currentInput = currentInput + "-";
					break;
				}
				if(confirmVis)
					this.Start();
			}
			else{
				this.Start();
			}
		}
		this.repaint();
		}
		else{
			super.A_Button();
		}
	}
	
	public void Up(){
		if(!playerNameSelection && !rivalNameSelection){
			if(_cursor.y != 45){
				_cursor.y -= 30;
			}
		}
		else{
			if(letterCursor.y != 166 && !(letterCursor.x == 252 && letterCursor.y == 230))
				letterCursor.y -= 32;
		}
		this.repaint();
	}
	public void Down(){
		if(!playerNameSelection && !rivalNameSelection){
			if(_cursor.y != 135){
				_cursor.y += 30;
			}
		}
		else{
			if(letterCursor.y != 262 && !(letterCursor.x == 318 && letterCursor.y == 198))
				letterCursor.y += 32;
		}
		this.repaint();
	}
	public void Left(){
		if(playerNameSelection || rivalNameSelection){
			if(letterCursor.x != 68)
				letterCursor.x -= 24;
				if(letterCursor.x == 156){
					letterCursor.x = 116;
				}
				if(letterCursor.x == 294){
					letterCursor.x = 228;
				}
				if(confirmVis){
					confirmVis = false;
					letterVis = true;
					confirmChoice = false;
					letterCursor.x = 252;
					letterCursor.y = 262;
				}
		}
		this.repaint();
	}
	public void Right(){
		if(playerNameSelection || rivalNameSelection){
			if(letterCursor.x != 318 && !(letterCursor.x == 252 && letterCursor.y == 230)){
				letterCursor.x += 24;
				if(letterCursor.x == 140){
					letterCursor.x = 180;
				}
				if(letterCursor.x == 252 && (letterCursor.y == 166 || letterCursor.y == 198)){
					letterCursor.x = 318;
				}
				if(letterCursor.x == 276 && letterCursor.y == 262 && !confirmVis){
					letterVis = false;
					confirmVis = true;
					confirmChoice = true;
				}
			}
		}
		this.repaint();
	}
	public void Start(){
		if(currentInput.toCharArray().length != 0){
			if(playerNameSelection){
				playerDefaultName = currentInput;
				_gbs.getPlayer().setPlayerName(playerDefaultName);
				currentInput = "";
				playerNameSelection = false;
				letterVis = false;
				confirmVis = false;
				letterCursor.x = 68;
				letterCursor.y = 166;
				stage[7] = true;
				stage[8] = false;
			}
			if(rivalNameSelection){
				rivalDefaultName = currentInput;
				_gbs.getPlayer().setRivalName(rivalDefaultName);
				_gbs.getRival().setName(rivalDefaultName);
				currentInput = "";
				rivalNameSelection = false;
				letterVis = false;
				confirmVis = false;
				letterCursor.x = 68;
				letterCursor.y = 166;
				stage[11] = true;
				stage[12] = false;
			}

			this.A_Button();
		}
	}
	public void B_Button(){
		if(playerNameSelection || rivalNameSelection){
			if(currentInput.toCharArray().length > 0){
				char[] array = currentInput.toCharArray();
				currentInput = "";
				for(int i = 0; i < array.length - 1; i++){
					currentInput = currentInput + array[i];
				}
				if(!confirmChoice){
					confirmVis = false;
					letterVis = true;
				}
				this.repaint();
			}
		}
	}
	
	private class IntroTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(whiteFade < 250){
				whiteFade+=5;
			}
			else{
				whiteFade = 250;
				introTime.stop();
			}
			_this.repaint();
		}
	}
	
	private class RuthToPokemon implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(fading){
				if(whiteFade > 0){
					whiteFade-=5;
				}
				else{
					fading = false;
					ruthVis = false;
					pokeVis = true;
				}
			}
			else{
				if(whiteFade < 250){
					whiteFade+=5;
				}
				else{
					whiteFade = 250;
					fading = true;
					rtp.stop();
				}
			}
			_this.repaint();
		}
	}
	
	private class PokemonToPlayer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(fading){
				if(whiteFade > 0){
					whiteFade-=5;
				}
				else{
					fading = false;
					pokeVis = false;
					playerVis = true;
				}
			}
			else{
				if(whiteFade < 250){
					whiteFade+=5;
				}
				else{
					whiteFade = 250;
					fading = true;
					ptp.stop();
				}
			}
			_this.repaint();
		}
	}
	
	private class PlayerToRival implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(fading){
				if(whiteFade > 0){
					whiteFade-=5;
				}
				else{
					fading = false;
					playerVis = false;
					rivalVis = true;
				}
			}
			else{
				if(whiteFade < 250){
					whiteFade+=5;
				}
				else{
					whiteFade = 250;
					fading = true;
					ptriv.stop();
				}
			}
			_this.repaint();
		}
	}
	
	private class RivalToPlayer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(fading){
				if(whiteFade > 0){
					whiteFade-=5;
				}
				else{
					fading = false;
					rivalVis = false;
					playerVis = true;
				}
			}
			else{
				if(whiteFade < 250){
					whiteFade+=5;
				}
				else{
					whiteFade = 250;
					fading = true;
					rivtp.stop();
				}
			}
			_this.repaint();
		}
	}
	
	private class PlayerNameChoice implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(playerX < 200)
				playerX++;
			else{
				playerMenuVisible = true;
				cursorVis = true;
				pname.stop();
			}
			
			_this.repaint();
		}
	}
	
	private class RivalNameChoice implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(rivalX < 200)
				rivalX++;
			else{
				playerX = 125;
				rivalMenuVisible = true;
				cursorVis = true;
				rname.stop();
			}
			
			_this.repaint();
		}
	}
}
