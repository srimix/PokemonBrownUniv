package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class CITLobby extends PokePanel2{
	
	private BufferedImage _lobbyO;
	
	public CITLobby(GameBoyScreen gbs){
		super(gbs);
		this.initializeEventVector(0);
		this.createBaseRoom();

	}
	
	public CITLobby(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int dir){
		super(gbs, xSpace, ySpace, xInd, yInd, dir);
		this.initializeEventVector(0);
		this.createBaseRoom();
	}

	public void enterRoom(int xInd, int yInd){
		if(xInd == 9 && yInd == 0){
			super.enterRoom(_gbs.CIT_HALL, 2, 5, FACENORTH);
		}
		if(xInd == 14 && yInd == 0){
			//Motorola Room
			super.enterRoom(_gbs.MOTOROLA, 2,8,FACENORTH);
		}
		if((xInd == 7 || xInd == 8) && yInd == 13){
			super.enterRoom(_gbs.CIT_ENTRANCE, xInd-4, 3, FACESOUTH);
		}
	}
	
	public void addTrainers(){
		this._movingTrainers = new Vector<Trainer>();
		Trainer center = new Trainer.PokemonCenter(_gbs.getPlayer(), this._roomNum, this);
		center.createHome(3, 0);
		this.getMovingTrainers().add(center);
		
		try{
			Trainer lazy = new Trainer.ChubbyGuy(null);
			lazy.createHome(3, 11);
			lazy.setDirectionAndImage(FACENORTH);
			lazy.getDialogue().add("...I've...been here...since 4pm...yesterday.");
			lazy.getDialogue().add("...wait...what day is it today?");
			_movingTrainers.add(lazy);
			
			Trainer assassin = new Trainer.ShadyGlasses(null);
			assassin.createHome(6, 5, 0, 0, "freeze", "freeze");
			assassin.setLOS(6);
			assassin.setDirectionAndImage(FACEWEST);
			assassin.getDialogue().add("GOTCHA! Oh wait, you aren't playing in our assassin's game...");
			_movingTrainers.add(assassin);
			
			Trainer anam = new Trainer.BlondeDude(null);
			anam.createHome(15, 11, 0, 0, "", "");
			anam.setDirectionAndImage(FACEEAST);
			anam.getDialogue().add("Usually these computer clusters are available for student use.");
			anam.getDialogue().add("Right now, they're already full of people trying to buy tickets.");
			int numBadges = 0;
			for(int i = 1; i <= 8; i++){
				if(_gbs.getPlayer().isGymLeaderDefeated(i))
					numBadges++;
			}
			if(numBadges < 6){
				int daysLeft = 6-numBadges; 
				if(daysLeft > 1)
					anam.getDialogue().add("Don't you know? Spring Weekend starts in like, " + (daysLeft) + " days!");
				else
					anam.getDialogue().add("Don't you know? Spring Weekend starts in like, 1 day!");
			}
			else if(numBadges > 6)
				anam.getDialogue().add("People liked Spring Weekend so much, they're already trying for next year.");
			else //numBadges == 6
				anam.getDialogue().add("IT'S SPRING WEEKEND! WOOOOOO!");
			_movingTrainers.add(anam);
			
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void createBaseRoom(){
		this.setBackground(Color.BLACK);
		this.description = "CIT Lobby";
		this.song = M2.CIT;
		this._roomNum = 15;
		this._mapX=218;
		this._mapY=295;
		this.xConstant = 2;
		this.yConstant = 20;
		this.addTrainers();
		this._pkmnCentX=3;
		this._pkmnCentY=1;
		this._flyX=3;
		this._flyY=1;
		_textVisible = new boolean[1];
		this.setBattleBG(NewBattleScreen.CIT);
		
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/CITLobby/lobby.png"));
			if(_lobbyO == null)
				_lobbyO = ImageIO.read(this.getClass().getResource("/PokemonFiles/CITLobby/lobbyO.png"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		
		this.createGrid();
	}
	
	public void createGrid(){
		this._room = new Room(17, 14);
		
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/CITlobby.cmap"));
		for(int i = 0; i < 14; i++){
			for(int i2 = 0; i2 < 17; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawPlayer(g2);
		
		g2.drawImage(_lobbyO, null, this._xSpace, this._ySpace);

		this.drawAllGenerics(g2);
	}
}