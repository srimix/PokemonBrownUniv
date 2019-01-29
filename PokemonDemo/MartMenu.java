package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MartMenu extends JPanel {

	private GameBoyScreen _gbs;
	private int martOption = -1, current = 0, quantity=1;
	private double previousCursorX, previousCursorY;
	private boolean confirm, notify, rejected, cursorVisible;
	private Vector<Item> buyList = new Vector<Item>();
	private Vector<Item> sellList = new Vector<Item>();
	private Vector<Integer> realIndex = new Vector<Integer>(); 
	private Graphics2D g2;
	private Ellipse2D.Double cursor;
	private double confirmX=300, confirmY=280;
	private double listX=12, listY=12;
	
	public static final int BUY = 0, SELL = 1;
	
	public MartMenu(GameBoyScreen gbs){
		_gbs = gbs;
	
		cursor = new Ellipse2D.Double();
		cursor.height = 5;
		cursor.width = 5;
		cursor.x = listX;
		cursor.y = listY;
		previousCursorX=cursor.x;
		previousCursorY=cursor.y;
		cursorVisible=true;
		
		this.setFont(_gbs.getFont());
		
		this.setPreferredSize(new Dimension(360, 360));
		this.setBackground(Color.WHITE);
	}
	
	public void initialize(int martOpt){
		martOption = martOpt;
		
		if(martOption == BUY){
			//Create the mart first.
			_gbs.getCurrentPanel().generateMart();
			Vector<Item> fullMart=_gbs.getCurrentPanel().getMartVector();
			buyList.clear();
			realIndex.clear();
			current=0;
			quantity=1;
			cursorVisible=true;
			
			for(int i=0;i<fullMart.size();i++){
				if(fullMart.get(i).getRemaining()>0){
					//Store the item and the actual index.
					buyList.add(fullMart.get(i));
					realIndex.add(i);
					SysOut.print("ITEMS: " + fullMart.get(i).getName() + " at " + fullMart.get(i).getBuyPrice());
				}
			}
			
			cursor.x = listX;
			cursor.y = listY;
			
		}

		else if(martOption == SELL){
			sellList.clear();
			realIndex.clear();
			current=0;
			quantity=1;
			Vector<Item> playerList = _gbs.getPlayer().getAllItems();
			for (int i=0; i<playerList.size();i++){
				if(playerList.get(i).getRemaining()>0){
					//Store the item and the actual index.
					sellList.add(playerList.get(i));
					realIndex.add(i);
					SysOut.print("ITEMS: " + playerList.get(i).getName() + " at " + playerList.get(i).getSellPrice());
				}
			}
			cursor.x=listX;
			cursor.y=listY;
			
			//You can't sell what you don't have.
			if(sellList.size()==0){
				cursorVisible=false;
			}
				
		}
	}
	
	public void Up(){
		if(confirm && !rejected){
			if(cursor.y==confirmY){
				cursor.y=cursor.y-20;
			}
			this.repaint();
		}
		else if (!confirm && !notify && !rejected){
			if(current>0){
		
			current--;
			cursor.y=cursor.y-20;
			}
		}
		this.repaint();
	}
	
	public void Down(){
		if(confirm && !rejected){
			if(cursor.y==confirmY-20){
				cursor.y=cursor.y+20;
			}
			this.repaint();
		}
		else if (!confirm && !notify && !rejected){
			if(martOption==BUY && current<buyList.size()-1){
		
			current++;
			cursor.y=cursor.y+20;
			}
			if(martOption==SELL && current<sellList.size()-1){
				
				current++;
				cursor.y=cursor.y+20;
			}
		}
		this.repaint();
	}
	
	public void Left(){
		if (!confirm && !notify && !rejected){
			if(quantity>1){
				quantity--;
			}
		}
		this.repaint();
	}
	
	public void Right(){
			if (!confirm && !notify && !rejected){
				if(martOption == BUY && quantity<99){
					quantity++;
				}
				if(martOption == SELL && sellList.size()>0&& quantity<sellList.get(current).getRemaining()){
					quantity++;
				}
			}
			this.repaint();
	}
	
	public void A_Button(){
		if(!confirm && !notify && !rejected){
			if(martOption ==BUY &&_gbs.getPlayer().getMoney()>=buyList.get(current).getBuyPrice()*quantity){
				confirm=true;
				previousCursorX=cursor.x;
				previousCursorY=cursor.y;
				
				//Move the cursor to the confirmation.
				cursor.x=confirmX;
				cursor.y=confirmY;
				this.repaint();
			}
			//FIXXX
			else if(martOption == SELL && sellList.size()>0){
				confirm=true;
				previousCursorX=cursor.x;
				previousCursorY=cursor.y;
				
				//Move the cursor to the confirmation.
				cursor.x=confirmX;
				cursor.y=confirmY;
				this.repaint();
			}
			else if(martOption == SELL && sellList.size()==0){
				this.B_Button();
				this.repaint();
			}
			else{
				rejected=true;
				previousCursorX=cursor.x;
				previousCursorY=cursor.y;
				cursorVisible=false;
			}
		}
		
		else if(confirm && !notify && !rejected){
			//Yes
			if(martOption ==BUY && cursor.y==confirmY-20.0){
				confirm=false;
				notify=true;
				cursorVisible=false;
				//Buy the object.
				_gbs.getPlayer().getAllItems().get(realIndex.get(current)).increaseByi(quantity);
				_gbs.getPlayer().setMoney(_gbs.getPlayer().getMoney()-buyList.get(current).getBuyPrice()*quantity);
			}
			else if(martOption ==SELL && cursor.y==confirmY-20.0){
				confirm=false;
				notify=true;
				cursorVisible=false;
				//Sell the object.
				_gbs.getPlayer().getAllItems().get(realIndex.get(current)).increaseByi(-quantity);
				_gbs.getPlayer().setMoney(_gbs.getPlayer().getMoney()+sellList.get(current).getSellPrice()*quantity);
			}
			//No
			else if(cursor.y==confirmY){
				//Go back to the list of things.
				confirm=false;
				cursor.x=previousCursorX;
				cursor.y=previousCursorY;
			}
		}
		else if(notify || rejected){
			notify=false;
			rejected=false;
			cursorVisible=true;
			if(martOption==BUY){
				cursor.x=previousCursorX;
				cursor.y=previousCursorY;
			}
			else if(martOption==SELL){
				this.initialize(SELL);
			}
			quantity=1;
		}
		this.repaint();
	}

	public void B_Button(){
		//This is to close the mart.
		if(!confirm && !notify && !rejected){
		//This line is necessary. Call it every time you leave the mart.
			SysOut.print("Mart Destroyed.");
			_gbs.getCurrentPanel().destroyMart();
			_gbs.getCurrentPanel().buyMenu=false;
			_gbs.getCurrentPanel().sellMenu=false;
			_gbs.getCurrentPanel()._martMenuVisible=false;
			this.setVisible(false);
		}
		else if (confirm && !notify && !rejected){
			//Go back to the list of things.
			confirm=false;
			cursor.x=previousCursorX;
			cursor.y=previousCursorY;
		}
		else if (notify || rejected){
			//Now it doesn't matter, the item is bought. just close the notification.
			this.A_Button();
		}
		
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g2 = (Graphics2D) g;
		g2.setFont(this.getFont());
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		
		//Draw a border around it.
		Rectangle2D.Double border = new Rectangle2D.Double();
		border.width = this.getWidth() - 10;
		border.height = this.getHeight() -10;
		border.x=5;
		border.y=5;
		g2.setColor(Color.BLACK);
		g2.draw(border);
		
		if(martOption == BUY){
			int iconX=50;
			int iconY=200;
			
			if(cursorVisible){
				g2.draw(cursor);
			}
			for(int i = 0; i < buyList.size(); i++){
				g2.drawString(buyList.get(i).getName(), 30, 20+(20*i));
			}
			Item currentItem=buyList.get(current);
			
			g2.drawString(_gbs.getPlayer().getPlayerName(), 250, 90);
			g2.drawString("$" + _gbs.getPlayer().getMoney(), 250, 110);
			
			g2.drawImage(currentItem.getIcon(), null, iconX, iconY);
			if(!confirm && !notify && !rejected){
				g2.drawString("Quantity: " + quantity, iconX, iconY+50);
			}
			
			String s1 = "";
			if(quantity > 0) s1="<";
			g2.drawString("Price: $" + currentItem.getBuyPrice() + " X " + s1 + quantity + ">", iconX, iconY+70);
			g2.drawString("= $" + currentItem.getBuyPrice()*quantity, iconX, iconY+90);
			if(_gbs.getPlayer().getAllItems().get(realIndex.get(current)).getRemaining()>0){
				g2.drawString("You already have: "+_gbs.getPlayer().getAllItems().get(realIndex.get(current)).getRemaining(), iconX+50, iconY+18);
			}
		
		
			
			//Draw Description & Border
			Rectangle2D.Double descborder = new Rectangle2D.Double();
			descborder.width = this.getWidth() - 10;
			descborder.height = 62 -10;
			descborder.x=5;
			descborder.y=305;
			g2.setColor(Color.BLACK);
			g2.draw(descborder);
			
			String desc=currentItem.getDesc();
			int wrap=35;
			if (desc.length()<=wrap){
				g2.drawString(desc, 12, 325);
			}
			else{
				String firstHalf=desc.substring(0,wrap);
				int breakPoint=firstHalf.lastIndexOf(" ");
				g2.drawString(desc.substring(0, breakPoint), 12, 325);
				g2.drawString(desc.substring(breakPoint+1), 12, 345);
			}
			
			
			if(confirm){
				g2.drawString("Yes", (int)confirmX+7, (int)confirmY+7-20);
				g2.drawString("No", (int)confirmX+7, (int)confirmY+7);
				g2.drawString("Purchase "+ quantity +" "+ buyList.get(current).getName() + "?", iconX, (int)confirmY+10-40);
			}
			if(notify){
				g2.drawString("Thank you! Here are your items.",  iconX, (int)confirmY+10-40);
			}
			if(rejected){
				g2.setColor(Color.RED);
				g2.drawString("You don't have enough money.", iconX, (int)confirmY+10-40);
				g2.setColor(Color.BLACK);
			}
			
		}
		else if(martOption == SELL){
			int iconX=50;
			int iconY=100;
			
			if(cursorVisible){
				g2.draw(cursor);
			}
			
			if (sellList.size()>0){
				for(int i = 0; i < sellList.size(); i++){
					g2.drawString(sellList.get(i).getName(), 30, 20+(20*i));
				}
				Item currentItem=sellList.get(current);
				
				g2.drawString(_gbs.getPlayer().getPlayerName(), 250, 90);
				g2.drawString("$" + _gbs.getPlayer().getMoney(), 250, 110);
				
				g2.drawImage(currentItem.getIcon(), null, iconX, iconY);
				g2.drawString("Quantity: " + quantity, iconX, iconY+50);
				String s1 = "";
				if(quantity > 0) s1="<";
				g2.drawString("Sell at: $" + currentItem.getSellPrice() + " X " + s1 + quantity + ">", iconX, iconY+70);
				g2.drawString("= $" + currentItem.getSellPrice()*quantity, iconX, iconY+90);
				if(_gbs.getPlayer().getAllItems().get(realIndex.get(current)).getRemaining()>0){
					g2.drawString("You have: "+_gbs.getPlayer().getAllItems().get(realIndex.get(current)).getRemaining(), iconX, iconY+110);
				}
			
		
				//Draw Description & Border
				Rectangle2D.Double descborder = new Rectangle2D.Double();
				descborder.width = this.getWidth() - 10;
				descborder.height = 62 -10;
				descborder.x=5;
				descborder.y=305;
				g2.setColor(Color.BLACK);
				g2.draw(descborder);
				
				String desc=currentItem.getDesc();
				int wrap=35;
				if (desc.length()<=wrap){
					g2.drawString(desc, 12, 325);
				}
				else{
					String firstHalf=desc.substring(0,wrap);
					int breakPoint=firstHalf.lastIndexOf(" ");
					g2.drawString(desc.substring(0, breakPoint), 12, 325);
					g2.drawString(desc.substring(breakPoint+1), 12, 345);
				}
			
				if(confirm){
					g2.drawString("Yes", (int)confirmX+7, (int)confirmY+7-20);
					g2.drawString("No", (int)confirmX+7, (int)confirmY+7);
					g2.drawString("Sell "+ quantity +" "+ sellList.get(current).getName() + "?", iconX, (int)confirmY+7-40);
				}
				
				if(notify){
					g2.drawString("Thank you! Here is your money.", iconX, (int)confirmY+7-40);
				}
			}
			else if (rejected || sellList.size()==0){
					g2.setColor(Color.RED);
					g2.drawString("You have no items to sell!", iconX, (int)confirmY+7-40);
					g2.setColor(Color.BLACK);
			}
		}
	}
	
}
