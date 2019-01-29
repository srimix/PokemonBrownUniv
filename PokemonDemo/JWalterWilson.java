package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class JWalterWilson extends PokePanel2 {
	private final int HIDDEN_TM=1, MAIL1=5, MAIL2=6, MAIL3=7, MAIL4=8;
	
	public JWalterWilson(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(9);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public JWalterWilson(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(9);
		
		this.createBaseRoom();
		
	}
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
				//Trainer truck = new Trainer.ChubbyGuy(null);
				//sign, pc, mart, advice? TM
			try {				
				
				Trainer center = new Trainer.PokemonCenter(_gbs.getPlayer(), this._roomNum, this);
				center.createHome(26, 6, 0, 0, "", "");
				center.setStationary(true);
				
				
				//This is not the real TM number, just a filler.
			
				Trainer item = new Trainer.ItemObject(new Item.TM22_Solar_Beam());
				//It's drawn at 1000 1000 so its virtually invisible.
				item.createHome(10, 7, 1000, 1000, "", "");
				item.getDialogue().clear();
				item.getDialogue().add(" ");
				item.setStationary(true);
				item.setVanishing(true);
				
				Trainer text = new Trainer.Text();
				text.createHome(2, 6, 0, 0, "", "");
				text.getDialogue().clear();
				text.getDialogue().add("...he seems to be on the phone.");
				text.setStationary(true);
				
				Trainer pc = new Trainer.PC(_gbs);
				pc.createHome(20,3,0,0,"","");
				
				//Switched, zipcar bug fixed. 
				Trainer gil = new Trainer.Mailbox(this, MAIL1,MAIL3,MAIL2,MAIL4);
				
				//trainer
				//1764 trainer -1
				//7084 trainer -2
				//8017 trainer -3
				//9001 trainer -4
				gil.createHome(25, 6, 0, 0, "", "");
				
				Trainer mail1 = new Trainer.ItemObject(new Item.MasterBall());
				mail1.createHome(24, 5,-4,6,"","");
				mail1.setVanishing(true);
				mail1.defeat();
				
				Trainer mail2 = new Trainer.ItemObject(new Item.Zipcar_Card());
				mail2.createHome(24, 4,-4,6,"","");
				mail2.setVanishing(true);
				mail2.defeat();
				
				Trainer mail3 = new Trainer.ItemObject(new Item.Potion());
				mail3.createHome(27, 6, 2,6,"","");
				mail3.setVanishing(true);
				mail3.defeat();
				
				Trainer mail4 = new Trainer.ItemObject(new Item.Potion());
				mail4.createHome(28, 6, 2,6,"","");
				mail4.setVanishing(true);
				mail4.defeat();
				
				this._movingTrainers.add(center); //0
				this._movingTrainers.add(item); //1 (see the final int above)
				this._movingTrainers.add(text); //2
				this._movingTrainers.add(pc); //3
				this._movingTrainers.add(gil); //4
				this._movingTrainers.add(mail1); //5
				this._movingTrainers.add(mail2); //6
				this._movingTrainers.add(mail3); //7
				this._movingTrainers.add(mail4); //8
			} 
				

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void createBaseRoom(){
		
		this.song = M2.POKECENTER;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=89;
		this.yConstant=60;
		this._mapX=171;
		this._mapY=287;
		this._pkmnCentX=26;
		this._pkmnCentY=7;
		
		this.setBikeAllow(false);
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "J Walter Wilson";
		this._roomNum = 20;
		try{
			if(GameBoyScreen.finishedLoading)
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/JWW/JWW bg.png"));
						
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void createGrid(){
		this._room = new Room(31,11);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/JWW.cmap"));
		for(int i = 0; i < 11; i++){
			for(int i2 = 0; i2 < 31; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	public void loadAllEvents(){
		if(this.getCheckList().size()!= 0){
			if(this.getCheckList().get(0)==1){
				this.getMovingTrainers().get(HIDDEN_TM).defeat();
			}
			//Else's are necessary.
			if(this.getCheckList().get(1)==1){
				this.getMovingTrainers().get(MAIL1).unDefeat();
			}
			else{
				this.getMovingTrainers().get(MAIL1).defeat();
			}
			if(this.getCheckList().get(2)==1){
				this.getMovingTrainers().get(MAIL2).unDefeat();
			}
			else{
				this.getMovingTrainers().get(MAIL2).defeat();
			}
			if(this.getCheckList().get(3)==1){
				this.getMovingTrainers().get(MAIL3).unDefeat();
			}
			else{
				this.getMovingTrainers().get(MAIL3).defeat();
			}
			if(this.getCheckList().get(4)==1){
				this.getMovingTrainers().get(MAIL4).unDefeat();
			}
			else{
				this.getMovingTrainers().get(MAIL4).defeat();
			}
			
		}
	}

	public void scanForAllEvents(){
		
		//Is the TM found?
		if(this.getMovingTrainers().get(HIDDEN_TM).isDefeated()){
			this.getCheckList().set(0,1);	
		}
		
		//Defeat of mails. ****NOTICE THE INVERSION OF 0 and 1****
		//Else's are necessary.
		if(this.getMovingTrainers().get(MAIL1).isDefeated()){
			this.getCheckList().set(1,0);	
		}
		else{
			this.getCheckList().set(1,1);
		}
		
		if(this.getMovingTrainers().get(MAIL2).isDefeated()){
			this.getCheckList().set(2,0);	
		}
		else{
			this.getCheckList().set(2,1);
		}
		
		if(this.getMovingTrainers().get(MAIL3).isDefeated()){
			this.getCheckList().set(3,0);	
		}
		else{
			this.getCheckList().set(3,1);
		}
		
		if(this.getMovingTrainers().get(MAIL4).isDefeated()){
			this.getCheckList().set(4,0);	
		}
		else{
			this.getCheckList().set(4,1);
		}

	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_gbs.BACKGROUND, null, this._xSpace, this._ySpace);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);
		this.drawAllGenerics(g2);
		
		
		//Draw the Box #!
		if(mailboxOpen){
			Rectangle2D.Double mailBG = new Rectangle2D.Double();
			mailBG.x = 0;
			mailBG.y = 265;
			mailBG.width = 140;
			mailBG.height = 50;
			Rectangle2D.Double outline = new Rectangle2D.Double();
			outline.x = mailBG.x+5;
			outline.y = mailBG.y+5;
			outline.width = mailBG.width-10;
			outline.height = mailBG.height-10;
			g2.setColor(Color.WHITE);
			g2.fill(mailBG);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2));
			g2.draw(outline);
			
			//Draw Box number
			g2.drawString("Box #: "+ this.mailNumber, 10, 294);
		}
	}
	
	public void enterRoom(int xInd, int yInd){
		
		//Route 3
		if(((xInd == 25)||(xInd == 26)) && (yInd == 10)){
			super.enterRoom(_gbs.ROUTE_3, 7, 46, FACESOUTH);
		}
		//Registrar
		if((xInd == 2) && (yInd == 1)){
			super.enterRoom(_gbs.REGISTRAR, 8, 1, FACESOUTH);
		}
		
	}
	public void blackout(){
		this.blackout(this._pkmnCentX,this._pkmnCentY,FACESOUTH);
		_gbs.getPlayer().healAllActive();
	}

	public void A_Button(){
		if(!textTimer.isRunning()){
			//Don't allow use until 4 numbers are hit.
			if(this.mailboxOpen && this.mailNumber.length()==4){
				mailboxOpen=false;
			}
			if(!this.mailboxOpen){
				super.A_Button();
			}
	
			if(this._NPCpage==0 && this.getMovingTrainers().get(4).isInterrupted() && !this.mailboxOpen){
				this.mailNumber="";
			}
		}
		else{
			super.A_Button();
		}
	}
	
	
	public void Start(){
		//Don't allow use until 4 numbers are hit.
		if(!textTimer.isRunning()){
			if(this.mailboxOpen && this.mailNumber.length()==4){
				this.A_Button();
			}
			if(!this.mailboxOpen){
				super.Start();
			}
		}
	}
	
	public void Number_Button(int i){
		if(!textTimer.isRunning()){
			if(this.mailboxOpen && this.mailNumber.length()<4){
				this.mailNumber = this.mailNumber+ Integer.toString(i);
			}
			this.repaint();
		}
	}
	
	
}