package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class ItemInventory2 extends JPanel{

	private Graphics2D g2;
	private Ellipse2D.Double cursor;
	
	private Player player;
	private GameBoyScreen _gbs;
	private int itemPkmn = 0;
	private Pokemon selected;
	
	private Timer t_text, t_hp;
	private CharListener cl;
	private HPChangeListener hpcl;
	private char[] cfirst, csecond;
	private Rectangle2D.Double pkmnMenuBG;
	private String s1="", s2="";

	private final int p_1st = 20, p_2nd = 72, p_3rd = 124, p_4th = 176, p_5th = 228, p_6th = 280;
	private Item currentItem;
	
	private Vector<Item> itemList;
	private boolean pageTwo = false;
	private boolean itemSelectMenu = false, pkmnMenu = false, attackMenu=false, statPanel=false;
	
	private ItemInventory2 _this;
	private Rectangle2D.Double outline;
	
	//For items.
	private int _deltaHP=0;
	private int _fixedStatus=0;
	private int _deltaPP=0;
	private int PPshift=45;
	private int selectedAttack = 0;
	private boolean _itemUseComplete;
	private int prevNum;
	private double previousItemCoordY,previousItemCoordX, previousPkmnCoordX, previousPkmnCoordY;
	private Font font = new Font("Courier New", Font.BOLD, 10);
	
	//Forgetting attacks.
	private int attackToForget=0;
	private int _atkPage=-3;
	private boolean forgettingAttack;
	private boolean currentRequest;
	private int denials=0;
	private boolean _forbidB;
	
	public ItemInventory2(GameBoyScreen gbs){
		super();
		
		_this = this;
		this.setFont(gbs.getFont());
		
		player = gbs.getPlayer();
		_gbs=gbs;
		
		pkmnMenuBG = new Rectangle2D.Double();
		pkmnMenuBG.x = -10;
		pkmnMenuBG.y = -10;
		pkmnMenuBG.width = 400;
		pkmnMenuBG.height = 320;
		
		outline = new Rectangle2D.Double();
		
		itemList = new Vector<Item>();	
		
		cursor = new Ellipse2D.Double();
		cursor.height = 5;
		cursor.width = 5;
		cursor.x = 10;
		cursor.y = 10;
		
		cl = new CharListener();
		t_text = new Timer(35,cl);
		
		hpcl = new HPChangeListener();
		t_hp = new Timer(150, hpcl);
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(380,360));
		
		this.repaint();
	}
	
	public GameBoyScreen getGBS(){
		return _gbs;
	}
	
	public void setStatPanelVis(boolean b){
		statPanel = b;
		this.repaint();
	}
	
	public void setAttackMenuVis(boolean b){
		attackMenu = b;
		if (attackMenu){
		cursor.y = 327-PPshift;
		cursor.x = 12;
		}
		this.repaint();
	}
	public void setForgettingAttack(boolean b){
		forgettingAttack=b;
	}
	public void setCurrentRequest(boolean b){
		currentRequest=b;
		if(!currentRequest){
			this.setForbidB(true);
		}
		else{
			this.setForbidB(false);
			}
	}
	public void setItemUseComplete(boolean b){
		_itemUseComplete=b;
	}
	public boolean isItemUseComplete(){
		return _itemUseComplete;
	}
	public Player getPlayer(){
		return player;
	}
	public int getSizeMult(int I){
		if (I<10){
			return 1;
		}
		else if (I>=10 && I<100){
			return 2;
		}
		else if (I>=100){
			return 3;
		}
		return 1;
	}
	
	public int getAttacktoForget(){
		return attackToForget;
	}
	
	public int getDenials(){
		return denials;
	}
	public void setDenials(int d){
		denials=d;
	}
	
	public int getMenuItemIndex(){
		//This is the cursor index.
		int index =(int)(cursor.y/12.0)+prevNum;
		
		return index;
	}
	
	
	public void Up(){
		if(!t_text.isRunning() && !t_hp.isRunning() && denials==0 && !this.isItemUseComplete()){
			if (!itemSelectMenu){
				if(cursor.x == 200 || cursor.x == 201){
					if(cursor.y > 10)
						cursor.y -= 12;
					else{
						if(cursor.x == 200)
							cursor.x = 10;
						if(cursor.x == 201)
							cursor.x = 11;
						cursor.y = 298;
					}
				}
				else{
					if(cursor.y > 10)
						cursor.y -= 12;
				}
			}
			else{
				if(attackMenu && _deltaPP<=0){
					cursor.y = 327-PPshift;
				}
				if(pkmnMenu && _deltaHP==0 && _fixedStatus==0 && !attackMenu){
					switch((int)cursor.y){
					case p_1st: break;
					case p_2nd: cursor.y = p_1st; this.repaint();
						break;
					case p_3rd: cursor.y = p_2nd; this.repaint();
						break;
					case p_4th: cursor.y = p_3rd; this.repaint();
						break;
					case p_5th: cursor.y = p_4th; this.repaint();
						break;
					case p_6th: cursor.y = p_5th; this.repaint();
						break;
					}
				}
			}
		}
		this.repaint();
	}
	
	public void Down(){
		if(!t_text.isRunning() && !t_hp.isRunning() && denials==0 && !this.isItemUseComplete()){
			if (!itemSelectMenu){
				if(cursor.x == 10){
					if(cursor.y < 298){
						if(cursor.y <= (itemList.size()-1)*12)
							cursor.y += 12;
					}
					else{
						cursor.x = 200;
						cursor.y = 10;
					}
				}
				else{
					if(cursor.y <= 298){
						if(!pageTwo){
							if(cursor.y <= (itemList.size()-18)*12)
								if(cursor.y != 298)
									cursor.y += 12;
						}
						else{
							
							if(cursor.y == 298 && cursor.x == 11){
								cursor.x = 201;
								cursor.y = 10;
							}
							else{
								if(cursor.x == 11){
									if(cursor.y <= (itemList.size()-35)*18){
										cursor.y += 12;
										
									}
								}
								else{
									if(cursor.y <= (itemList.size()-52)*18){
										cursor.y += 12;
									}
								}
							}
						}
					}
				}
			}
			else{
				if(attackMenu && _deltaPP<=0){
						if((cursor.x == 12 && selected.getAttacks().size() > 2) || (cursor.x == 202 && selected.getAttacks().size() > 3))
							cursor.y = 347-PPshift;
				}
				if(pkmnMenu && _deltaHP==0 && _fixedStatus==0 && !attackMenu){
					int num = player.getAllActive().size();
					
					switch((int) cursor.y){
					case p_1st: if(num > 1) {cursor.y = p_2nd; this.repaint();}
						break;
					case p_2nd: if(num > 2) { cursor.y = p_3rd; this.repaint();}
						break;
					case p_3rd: if(num > 3) {cursor.y = p_4th; this.repaint();}
						break;
					case p_4th: if(num > 4) { cursor.y = p_5th; this.repaint();}
						break;
					case p_5th: if(num > 5) {cursor.y = p_6th; this.repaint();}
						break;
					case p_6th: break;
					}
				}
			}
		}
		this.repaint();
	}
	
	public void Left(){
		if(!t_text.isRunning() && !t_hp.isRunning()){
			if (!itemSelectMenu){
				SysOut.print("Left and not itemSelect");

				if(cursor.x == 11){
					cursor.x = 10;
					cursor.y = 10;
					pageTwo = false;
				}
				
				if(cursor.x == 200)
					cursor.x = 10;
				if(cursor.x == 201)
					cursor.x = 11;
			}
			else{
				if(attackMenu && _deltaPP<=0){
					cursor.x = 12;
				}
			}
		}
		this.repaint();
	}
	
	public void Right(){
		if(!t_text.isRunning() && !t_hp.isRunning()){
			if (!itemSelectMenu){
				SysOut.print("Right and not itemSelect");
				if(cursor.x == 10){
					SysOut.print("Right and cursor = 10");
					if(cursor.y <= (itemList.size()-17)*18)
						cursor.x = 200;
				}
				else{
					if(!pageTwo && itemList.size() > 34){
						SysOut.print("Right and switch to page 2");
						cursor.x = 11;
						cursor.y = 10;
						pageTwo = true;
					}
					else{
						if(cursor.y <= (itemList.size()-51)*18)
							cursor.x = 201;
					}
				}
			}
			else{
				if(attackMenu && _deltaPP<=0){
					if((cursor.y == 327-PPshift && selected.getAttacks().size() > 1) || (cursor.y == 347-PPshift && selected.getAttacks().size() > 3)){
						cursor.x = 202;
					}
				}
			}
		}
		this.repaint();
	}
	
	public void A_Button(){
		if(!t_text.isRunning() && !t_hp.isRunning()){
			if(!itemSelectMenu){
				if(itemList.size()!=0){
					prevNum = 0;
				
					if(cursor.x == 200){
						prevNum = 25;
					}
				
					if(cursor.x == 11){
						prevNum = 50;
					}
					if(cursor.x == 201){
						prevNum = 75;
					}
					
					if(itemList.size() > 0 && this.getMenuItemIndex() < itemList.size())
						currentItem = itemList.get(this.getMenuItemIndex());
					previousItemCoordY=cursor.y;
					previousItemCoordX=cursor.x;
					itemSelectMenu = true;
				}
			}
			else{
				if(!pkmnMenu && !t_text.isRunning() && !attackMenu && !this.isItemUseComplete()){
					String type = currentItem.getType();
					if(type == "HP" 
						|| type == "PP" 
							|| type == "Status" 
								|| type == "Elixir" 
									||type == "FullRestore" 
										|| type == "Revive" 
											|| type == "HM" 
												|| type == "TM" 
													|| type == "Stone" 
														|| type == "StatUp" 
															|| type == "RareCandy" 
																|| type == "PPUp"){
						pkmnMenu = true;
						cursor.x = 7;
						cursor.y = p_1st;
						if (currentItem.getType()=="TM" || currentItem.getType()=="HM"){
							this.displayText("Choose a Pokemon to learn", currentItem.getName() + ".");
						}
						else{
							this.displayText("Choose a Pokemon to use", currentItem.getName() + " on.");
						}
					}
					else{
						if(type == "Boost" || type == "Ball"){
							this.displayText("RA: Now isn't the time to use that!", "");
						}
						else{
							if(type == "Misc"){
								
							}
							else{
								if(type == "Bike"){
									if(!this.getGBS().getMode()){
										if(this.getGBS().getCurrentPanel().canBikeHere()){
											this.displayText(player.getPlayerName() + " got on the bike.", "");
											this.getGBS().setMode(true);
											M2.playBG(M2.BIKE);
											this.setItemUseComplete(true);
											
											switch(this.getGBS().getCurrentPanel().getPlayerDirection()){
											case 0:
												this.getGBS().getPlayer().faceUp();
												break;
											case 1:
												this.getGBS().getPlayer().faceLeft();
												break;
											case 2:
												this.getGBS().getPlayer().faceDown();
												break;
											case 3:
												this.getGBS().getPlayer().faceRight();
												break;
											}
											
											this.getGBS().repaint();
											_fixedStatus=1;
											
										}
										else{
											this.displayText("RA: Now's not the time to use that!", "");
										}
									}
									else{
										this.displayText(player.getPlayerName() + " got off the bike.", "");
										this.getGBS().setMode(false);
										M2.playBG(this.getGBS().getCurrentPanel().getSong());
										this.setItemUseComplete(true);
										this.getGBS().repaint();
										_fixedStatus=1;
									}
								}
								if(currentItem.getType()=="EscapeRope"){
									if(!_gbs.getCurrentPanel()._outdoors && _gbs.getCurrentPanel().canBikeHere()){
										this.B_Button();
										this.pkmnMenu=false;
										this.itemSelectMenu=false;
										this.attackMenu=false;
										this.statPanel=false;
										_gbs._escapeRope=true;
										_gbs.setDigging(true);
										_gbs.pbs.setVisible(false);
										_gbs.ii.setVisible(false);
										_gbs.pis.setVisible(false);
										_gbs.pds.setVisible(false);
										_gbs.getCurrentPanel().setMenuVisible(false);
										_gbs.getCurrentPanel().escapeTimerLeave.start();
										currentItem.setRemaining(currentItem.getRemaining()-1);
									}
									else{
										this.displayText("RA: Now's not the time to use that!", "");
									}
								}
								if(currentItem.getType()=="Repel"){
									this.displayText(_gbs.getPlayer().getPlayerName()+" used a " + currentItem.getName() +"!", "");
									_gbs.getCurrentPanel().repelCount=currentItem.getEffect();
									_gbs.getCurrentPanel().repelling=true;
									currentItem.setRemaining(currentItem.getRemaining()-1);
									this.setItemUseComplete(true);
								}
							}
						}
					}					
				}
				//For resetting menus after use.
				else if((this.isItemUseComplete() || statPanel) && !t_text.isRunning() && !t_hp.isRunning()){
					this.setStatPanelVis(false);
					this.setItemUseComplete(false);
					_deltaHP=0;
					_deltaPP=0;
					_fixedStatus=0;
					this.setDenials(0);
					this._atkPage=-3;
					this.setForbidB(false);
					
					cursor.x=previousItemCoordX;
					cursor.y=previousItemCoordY;
					
					this.setVisible(false);
					if(currentItem.getType()!="Bike"){
						this.setVisible(true);
					}
					this.pkmnMenu=false;
					this.itemSelectMenu=false;
					this.attackMenu=false;
					this.statPanel=false;
					
					//In case you use the last item in a list
					if(this.getMenuItemIndex()==itemList.size()){
						this.Up();
						//If that didn't work:
						
					SysOut.print("corrected");
						if(cursor.y==previousItemCoordY){
							this.Left();
							SysOut.print("correctedAgain");
						}
					}
					
					this.repaint();
					this.displayText("", "");
					
				}
				else{
					if(pkmnMenu){
				//	Try to use the item on a pokemon
						
						if(!attackMenu){
							switch((int)cursor.y){
							case p_1st:
								SysOut.print("WHAT THE HELL?");
								selected = player.getActivePokemon(0);
								break;
								
							case p_2nd:
								SysOut.print("CALM DOWN!");
								selected = player.getActivePokemon(1);
								break;
								
							case p_3rd:
								selected = player.getActivePokemon(2);
								break;
								
							case p_4th:
								selected = player.getActivePokemon(3);
								break;
								
							case p_5th:
								selected = player.getActivePokemon(4);
								break;
								
							case p_6th:
								selected = player.getActivePokemon(5);
								break;
								
							default:
								this.B_Button();
							}
						}
						
						if(currentItem.getType()=="PP" || currentItem.getType()=="PPUp" || forgettingAttack){
							if(!attackMenu){
								attackMenu=true;
								previousPkmnCoordX=cursor.x;
								previousPkmnCoordY=cursor.y;
								cursor.y = 327-PPshift;
								cursor.x = 12;
								
								if(currentItem.getType()=="PP" || currentItem.getType()=="PPUp"){
									this.displayText("Use item on which attack?","");
									//selected=player.getAllActive().get(itemPkmn);
								}
								else if(forgettingAttack){
									this.displayText("Which move should be forgotten?","");
									this.setCurrentRequest(true);
								}
							}
							else if(attackMenu){
								selectedAttack = 0;
								
								if(cursor.y == 327-PPshift){
									if(cursor.x == 12) selectedAttack = 0;
									else selectedAttack = 1;
								}
								else{
									if(cursor.x == 12) selectedAttack = 2;
									else selectedAttack = 3;
								}
								
								//Add PP to that move.
								if (currentItem.getType()=="PP"){
									SysOut.print(selected.getAttacks().get(selectedAttack).getName());
									if(selected.getAttacks().get(selectedAttack).getCurrentPP()==selected.getAttacks().get(selectedAttack).getMaxPP()){
										this.displayText(selected.getAttacks().get(selectedAttack).getName()+ "is already at maximum PP!","");
									}
									else{
										currentItem.useOOBItem(_this, selected, selectedAttack);
										this.displayText(selected.getAttacks().get(selectedAttack).getName()+ "'s PP was restored.","");
										this.setItemUseComplete(true);
									}
								}
								else if(currentItem.getType()=="PPUp"){
									selected.getAttacks().get(selectedAttack).setMaxPP(selected.getAttacks().get(selectedAttack).getMaxPP()+1);
									this.displayText(selected.getAttacks().get(selectedAttack).getName()+ "'s PP rose!","");
									this.setItemUseComplete(true);
								}
								else if(forgettingAttack){
									if(!selected.getAttacks().get(selectedAttack).isHM()){
										
										if (this.getDenials()==0){
											switch(_atkPage){
												case 1:
													this.displayText("Forget "+ selected.getAttacks().get(selectedAttack).getName()+"?", "A=Yes          B=No");
													this.setCurrentRequest(true);
													break;
												case 2:
													this.displayText("1....2....and........poof!","");
													this.setCurrentRequest(false);
													break;
												case 3:
													this.displayText(selected.getName() + " forgot " + selected.getAttacks().get(selectedAttack).getName() + "!","");
													break;
												case 4:
													this.displayText("And......",selected.getName() + " learned " + currentItem.getName().substring(6) + "!");
													selected.addNewAttackNum(currentItem.getEffect());
													selected.getAttacks().remove(selectedAttack);
													//HM's don't decrement in number.
													if(currentItem.getType()=="TM"){
														currentItem.setRemaining(currentItem.getRemaining()-1);
													}
													this.setItemUseComplete(true);
													this.setForgettingAttack(false);
													break;
											}
											_atkPage++;
										}										
									}
									else{
										this.displayText("HM moves cannot be forgotten!","");
									}
								}	
							}
						}
						//For items that don't open the attack menu.
						else{
							/**
							 * TODO:
							 * RareCandy: force evolving and attacks via rare candy
							 * Stones
							 * Repel
							 * Escape Rope
							 */
						
							if(currentItem.getType()=="HP"){
								if(selected.getCurrentHP()!=selected.getMaxHP() && selected.getCurrentHP()!=0){
									_deltaHP=currentItem.getEffect();
									currentItem.useOOBItem(_this, selected, 0);
									this.setItemUseComplete(true);
								}
								else{
									this.displayText("It won't have any effect!", "");
								}
							}
							if(currentItem.getType()=="Revive"){
								if(selected.getCurrentHP()==0){
									_deltaHP=currentItem.getEffect();
									currentItem.useOOBItem(_this, selected, 0);
									this.setItemUseComplete(true);
								}
							}
							if(currentItem.getType()=="FullRestore"){
								if((selected.getCurrentHP()!=selected.getMaxHP() && selected.getCurrentHP()!=0)||(selected.getStatus()!=0)){
									_deltaHP=currentItem.getEffect();
									currentItem.useOOBItem(_this, selected, 0);
									this.setItemUseComplete(true);
								}
							}
							if(currentItem.getType()=="Status"){
								if(selected.getStatus()==currentItem.getEffect()){
									_fixedStatus=currentItem.getEffect();
									currentItem.useOOBItem(_this, selected, 0);
									this.setItemUseComplete(true);
									switch(currentItem.getEffect()){
									case 1: this.displayText(selected.getName() + " woke up!", "");
										break;
									case 2: this.displayText(selected.getName() + " is no ", "longer poisoned!");
										break;
									case 3: this.displayText(selected.getName() + " is no ","longer paralyzed!");
										break;
									case 4: this.displayText(selected.getName() + " is no ", "longer burned!");
										break;
									case 5: this.displayText(selected.getName() + " thawed out!","");
										break;
									}
								}
								else{
									this.displayText("It won't have any effect!", "");
								}
							}
							if (currentItem.getType()=="Elixir"){
								boolean maxedOut=false;
								//Check to see if the attacks are all at max PP.
								for(int i = 0; i<selected.getAttacks().size(); i++){
									if(selected.getAttacks().get(i).getCurrentPP()==selected.getAttacks().get(i).getMaxPP()){
										maxedOut=true;
									}
									else{
										maxedOut=false;
									}
								}
								
								if(maxedOut){
									this.displayText(selected.getName()+ "'s attacks have full PP!","");
								}
								else{
									currentItem.useOOBItem(_this, selected, 0);
									this.displayText("PP was restored to "+ selected.getName()+"attacks.","");
									this.setItemUseComplete(true);
								}
							}
							if(currentItem.getType()=="StatUp"){
								//THERE IS ANOTHER CONDITION FOR THIS TO PREVENT CHEAP STATS.
								//Find out what it is.
								_fixedStatus=currentItem.getEffect();
								currentItem.useOOBItem(_this, selected, 0);
								switch(currentItem.getEffect()){
								case 1: this.displayText(selected.getName() + "'s Sp. Atk rose!", "");
									break;
								case 2: this.displayText(selected.getName() + "'s Speed rose!", "");
									break;
								case 3: this.displayText(selected.getName() + "'s Defense rose!", "");
									break;
								case 4: this.displayText(selected.getName() + "'s Attack rose!", "");
									break;
								case 5: this.displayText(selected.getName() + "'s Sp. Def rose!", "");
									break;
								case 6: this.displayText(selected.getName() + "'s HP rose!", "");
									break;
								}
								this.statPanel=true;
								this.setItemUseComplete(true);
							}
							
							if(currentItem.getType()=="Stone"){
								//TODO
								if(!currentItem.isCompatibleWith(selected)){
									this.displayText("It won't have any effect.","");
								}
								else{
									String pre = selected.getName();
									if(selected.getDexNum() == 133){
										if(currentItem.getIndex() == Item.WATERSTONE){
											_gbs.getPlayer().getAllActive().set(selected.getBelt()-1, Pokemon.generateNewStats(Pokemon.evolve(selected, 1)));
											String post = _gbs.getPlayer().getAllActive().get(selected.getBelt()-1).getName();
											this.displayText(pre + " evolved into " + post + "!!!","");
											_gbs.getPlayer().getPokedex().addToCatchList(selected.getDexNum()+1);
											M2.playFX(M2.EVOLVED);
											this.setItemUseComplete(true);
											currentItem.setRemaining(currentItem.getRemaining()-1);
										}
										else if(currentItem.getIndex() == Item.THUNDERSTONE){
											_gbs.getPlayer().getAllActive().set(selected.getBelt()-1, Pokemon.generateNewStats(Pokemon.evolve(selected, 2)));
											String post = _gbs.getPlayer().getAllActive().get(selected.getBelt()-1).getName();
											this.displayText(pre + " evolved into " + post + "!!!","");
											_gbs.getPlayer().getPokedex().addToCatchList(selected.getDexNum()+2);
											M2.playFX(M2.EVOLVED);
											this.setItemUseComplete(true);
											currentItem.setRemaining(currentItem.getRemaining()-1);
										}
										else if(currentItem.getIndex() == Item.FIRESTONE){
											_gbs.getPlayer().getAllActive().set(selected.getBelt()-1, Pokemon.generateNewStats(Pokemon.evolve(selected, 3)));
											String post = _gbs.getPlayer().getAllActive().get(selected.getBelt()-1).getName();
											this.displayText(pre + " evolved into " + post + "!!!","");
											_gbs.getPlayer().getPokedex().addToCatchList(selected.getDexNum()+3);
											M2.playFX(M2.EVOLVED);
											this.setItemUseComplete(true);
											currentItem.setRemaining(currentItem.getRemaining()-1);
										}
										else{
											this.displayText("It won't have any effect.","");
										}
									}
									else{
										_gbs.getPlayer().getAllActive().set(selected.getBelt()-1, Pokemon.generateNewStats(Pokemon.evolve(selected, 1)));
										String post = _gbs.getPlayer().getAllActive().get(selected.getBelt()-1).getName();
										this.displayText(pre + " evolved into " + post + "!!!","");
										_gbs.getPlayer().getPokedex().addToCatchList(selected.getDexNum()+1);
										M2.playFX(M2.EVOLVED);
										this.setItemUseComplete(true);
										currentItem.setRemaining(currentItem.getRemaining()-1);
									}
								}
							}
							
							if(currentItem.getType()=="TM" || currentItem.getType()=="HM"){
								if(selected.hasAttack(currentItem.getEffect())){
									this.displayText(selected.getName()+" already knows "+ currentItem.getName().substring(6) +"!","");
								}
								else if(!currentItem.isCompatibleWith(selected)){
									this.displayText(selected.getName()+" can't learn " + currentItem.getName().substring(6) + "!" , "");
								}
								else if(currentItem.isCompatibleWith(selected) && selected.getAttacks().size()<4){
									selected.addNewAttackNum(currentItem.getEffect());
									this.displayText(selected.getName()+" learned " + currentItem.getName().substring(6) + "!", "");
									this.setItemUseComplete(true);
									if(currentItem.getType()=="TM"){
										currentItem.setRemaining(currentItem.getRemaining()-1);
									}
								}
								else if(selected.getAttacks().size()==4){
									if (this.getDenials()==0){
										switch(_atkPage){
											case -3:
												this.displayText(selected.getName() + " is trying to learn ", currentItem.getName().substring(6)+".");
												this.setCurrentRequest(false);
												break;
											case -2:
												this.displayText("...but " + selected.getName() + " already knows ", "four attacks!");
												break;
											case -1:
												this.setCurrentRequest(true);
												this.displayText("Should " + selected.getName() + " forget an attack?", "A=Yes          B=No");
												break;
											case 0:
												this.setForgettingAttack(true);
												this.A_Button();
												break;
										}
										_atkPage++;
									}
									else{
										//MAINTAIN THIS ORDER. It's important.
										if (this.getDenials()==2){
											this.displayText(selected.getName() + " did not learn ", currentItem.getName().substring(6)+".");
											this.setCurrentRequest(false);
											this.setItemUseComplete(true);
										}
										if(this.getDenials()==1){
											this.displayText("Give up on learning " +currentItem.getName().substring(6)+"?", "A=Yes          B=No");
										}
										denials++;
									}
									
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void setForbidB(boolean b) {
		_forbidB=b;
	}
	private boolean ForbiddenB() {
		return _forbidB;
	}

	public void B_Button(){
		if(!t_text.isRunning() && !t_hp.isRunning() && !this.isItemUseComplete() && !this.ForbiddenB()){
			//Forgetting attacks.
			if(currentRequest){
				//If you don't give it a chance
				if(_atkPage<=0){
					if(this.getDenials()==0){
						this.setDenials(1);
						this.A_Button();
					}
					else if(this.getDenials()>=1){
						this.setDenials(0);
						this._atkPage=-3;
						this.setCurrentRequest(false);
						this.A_Button();
					}
				}
				//Letting it try to remove an attack first, then starting over.
				else{
					if(this.getDenials()==0){
						this.setDenials(1);
						this.forgettingAttack=false;
						this.setAttackMenuVis(false);
						cursor.x=previousPkmnCoordX;
						cursor.y=previousPkmnCoordY;
						this.repaint();
						this.A_Button();
					}
					else if(this.getDenials()>=1){
						this.setDenials(0);
						this._atkPage=-3;
						this.setCurrentRequest(false);
						this.A_Button();
					}
				}
			}
			
			//Everything else.
			else{
				if (attackMenu){
					attackMenu=false;
					cursor.y=previousPkmnCoordY;
					cursor.x=previousPkmnCoordX;
				}
				else if(this.pkmnMenu && !this.attackMenu){
					pkmnMenu=false;
					cursor.y=previousItemCoordY;
					cursor.x=previousItemCoordX;
					this.displayText("","");
				}
				else if(this.itemSelectMenu && !this.pkmnMenu){
					itemSelectMenu=false;
				}
				else{
					this.setVisible(false);
				}
			}
		}
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		g2.setFont(_gbs.getFont());
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		
		itemList.clear();
		
		for(int i=0; i < player.getAllItems().size(); i++){
			if(player.getItem(i).getRemaining() > 0){
				itemList.add(player.getItem(i));
			}
		}
		
		g2.setFont(font);
		
		if(!pageTwo){
			for(int i=0; i < itemList.size(); i++){
				if(i < 25){ //17,35 - 51, 68
					g2.drawString(itemList.get(i).getName(), 30, (12*i)+15);
				}
				else{
					if(i < 50){
						g2.drawString(itemList.get(i).getName(), 220, (12*(i-26)+28));
					}
				}
			}
		}
		else{
			for(int i=50; i < itemList.size(); i++){
				if(i < 75){
					g2.drawString(itemList.get(i).getName(), 30, (12*(i-52)+39));
				}
				else{
					if(i < 100){
						g2.drawString(itemList.get(i).getName(), 220, (12*(i-78)+50));
					}
				}
			}
		}
		
		g2.setFont(_gbs.getFont());
		
		if(itemSelectMenu){
			Rectangle2D.Double itemSelectBG = new Rectangle2D.Double();
			itemSelectBG.x = 5;
			itemSelectBG.y = 80;
			itemSelectBG.width = this.getWidth() - 10;
			itemSelectBG.height = 140;
			g2.setColor(Color.WHITE);
			g2.fill(itemSelectBG);
			g2.setColor(Color.BLACK);
			g2.draw(itemSelectBG);
			
			//Draw Title
			g2.drawString(currentItem.getName(), 90, 110);
			
			
			//Draw Icon
			g2.setColor(new Color(56,56,56));
			g2.fillArc(-58, 17, 126, 126, 270, 90);
			
			g2.setColor(Color.BLACK);
			BufferedImage resizedIcon=currentItem.getIcon();
			double scale=1.6;
			int newW = (int)(resizedIcon.getWidth() * scale);
			int newH = (int)(resizedIcon.getHeight() * scale);
			g2.drawImage(resizedIcon, 5, 80, newW, newH, null);

			
			//Draw Description
			String desc=currentItem.getDesc();
			int wrap=35;
			if (desc.length()<=wrap){
				g2.drawString(desc, 20, 160);
			}
			else{
				String firstHalf=desc.substring(0,wrap);
				int breakPoint=firstHalf.lastIndexOf(" ");
				g2.drawString(desc.substring(0, breakPoint), 20, 160);
				g2.drawString(desc.substring(breakPoint+1), 20, 180);
			    
			}
			
			//Draw multiplier
			if(currentItem.getType()!="HM" && currentItem.getType()!="KeyItem" && currentItem.getType()!="Bike"){
				g2.drawString("x" + currentItem.getRemaining(), 300, 110);
			}
			
			//For battle unusable items.
			if(currentItem.isOOBInappropriate()){
				g2.setColor(Color.RED);
				g2.drawString("Cannot be used outside of battle.", 50, 140);
				g2.setColor(Color.BLACK);
			}
			
			if(currentItem.getType()=="HM"){
				g2.setColor(Color.BLUE);
				if(currentItem.getName().contains("Cut")){
					g2.drawString("Use to cut down thin trees. ", 50, 140);
				}
				else if(currentItem.getName().contains("Fly")){
					g2.drawString("Use to fly to any familiar town.", 50, 140);
				}
				else if(currentItem.getName().contains("Surf")){
					g2.drawString("Use to cross bodies of water.", 50, 140);
				}
				else if(currentItem.getName().contains("Strength")){
					g2.drawString("Use to move heavy objects.", 50, 140);
				}
				else if(currentItem.getName().contains("Flash")){
					g2.drawString("Use to illuminate dark areas.", 50, 140);
				}
				g2.setColor(Color.BLACK);
			}
			
			if(currentItem.getType()!="KeyItem"){
				g2.drawString("A = Use", 20, 200);
			}
			
			g2.drawString("B = Return to Item Menu", 125, 200);
		}
		
		if(!t_text.isRunning() && !t_hp.isRunning() && !itemSelectMenu){
			if(itemList.size() != 0){
				g2.setColor(Color.BLACK);
				String s = "Select item to use.";
				String sArrow = "";
				
				if(itemList.size() > 34){
					s += " (Page 1/2)";
					sArrow ="-->";
				}
				
				if(cursor.x == 10 || cursor.x == 200){
					g2.drawString(sArrow, 335, 351);
				}
				if(cursor.x == 11 || cursor.x == 201){
					s = "Select item to use. (Page 2/2)";
					sArrow = "<--";
					g2.drawString(sArrow, 20, 351);
				}
				g2.drawString(s, 20, 335);
			}
			else{
				g2.setColor(Color.RED);
				g2.drawString("You have no items!", 20, 335);
			}
		}
		else{
			g2.drawString(s1, 20, 335);
			g2.drawString(s2, 20, 351);
		}
		
		if(pkmnMenu){
			g2.setColor(Color.WHITE);
			g2.fill(pkmnMenuBG);
			g2.setColor(Color.BLACK);
			
			for(int i = 0; i < player.getAllActive().size(); i++){
				Pokemon focus = player.getActivePokemon(i);
				
				Rectangle2D.Double iconBG = new Rectangle2D.Double();
				iconBG.height = 50;
				iconBG.width = 50;
				iconBG.x = 15;
				iconBG.y = 5+(52*i);
				g2.setColor(Color.WHITE);
				g2.fill(iconBG);
				
				if(focus.getCurrentHP() != 0){
					focus.getIcon().paintIcon(this, g2, 15, 5 + (52*i));
				}
				else{
					g2.drawImage(focus.getDeadIcon(), null, 15, 5+(52*i));
				}
				
				g2.setColor(Color.BLACK);
				
				g2.drawString(focus.getName(), 50, 25 + (52*i));
				
				Rectangle2D.Double outlineHP = new Rectangle2D.Double();
				outlineHP.height = 15;
				outlineHP.width = 100;
				outlineHP.x = 170;
				outlineHP.y = 15 + (52*i);
				g2.draw(outlineHP);
				
				Rectangle2D.Double health = new Rectangle2D.Double();
				health.height = 13;
				health.width = (int)(98*((double)focus.getCurrentHP()/focus.getMaxHP()));
				health.x = 171;
				health.y = 16 + (52*i);
				
				if((double)focus.getCurrentHP()/focus.getMaxHP() > .5){
					g2.setColor(Color.GREEN);
				}
				else{
					if((double)focus.getCurrentHP()/focus.getMaxHP() > .25){
						g2.setColor(Color.ORANGE);
					}
					else{
						g2.setColor(Color.RED);
					}
				}
				g2.fill(health);
				g2.setColor(Color.BLACK);
				
				g2.drawString(focus.getCurrentHP() + "/" + focus.getMaxHP(), 280, 27 +(52*i));
				if(currentItem.getType() != "Stone" && currentItem.getType() != "TM" && currentItem.getType() != "HM"){
					if(focus.getCurrentHP() != 0)
						g2.drawString(focus.getStatusAcro(), 345, 27 + (52*i));
					else
						g2.drawString("FNT", 345, 27 + (52*i));
				}
				else{
					//Stone compatibility
					if(currentItem.isCompatibleWith(focus)){
						g2.setColor(Color.BLUE);
						g2.drawString("ABLE", 345, 27 + (52*i));
						g2.setColor(Color.BLACK);
					}
					else{
						g2.setColor(Color.RED);
						g2.drawString("NOT", 345, 27 + (52*i));
						g2.setColor(Color.BLACK);
					}
				}
			}
		}
		
		if(attackMenu){					
			//For PP Menu.
			int	atkL=20;
			int	atkR=210;
			int atkU=334;
			int atkD=354;
			Rectangle2D.Double PPoutline = new Rectangle2D.Double();
			PPoutline.x = 5;
			PPoutline.y = 319-PPshift;
			PPoutline.width = this.getWidth() - 10;
			PPoutline.height = 40;
			atkD=atkD-PPshift;
			atkU=atkU-PPshift;
			outline.y =outline.y;
			g2.setColor(Color.WHITE);
			g2.fill(PPoutline);
			g2.setColor(Color.BLACK);
			g2.draw(PPoutline);
			//selected=player.getAllActive().get(itemPkmn);
			
			switch(selected.getAttacks().size()){
			case 4: if(selected.getAttacks().get(3).getCurrentPP() == 0) 
						g2.setColor(Color.RED);
					//g2.drawString(ally.getAttacks().get(3).getName() + " (" + ally.getAttacks().get(3).getCurrentPP() + "/" + ally.getAttacks().get(3).getMaxPP() + ")", 195, 354);
					g2.drawString(selected.getAttacks().get(3).getName(), atkR, atkD);
					String pp4 = selected.getAttacks().get(3).getCurrentPP() + "/" + selected.getAttacks().get(3).getMaxPP();
					g2.drawString(pp4, 352-(pp4.length()*5), atkD);
					g2.setColor(Color.BLACK);
					
			case 3:	if(selected.getAttacks().get(2).getCurrentPP() == 0) 
						g2.setColor(Color.RED);
				//	g2.drawString(ally.getAttacks().get(2).getName() + " (" + ally.getAttacks().get(2).getCurrentPP() + "/" + ally.getAttacks().get(2).getMaxPP() + ")", 25, 354);
					g2.drawString(selected.getAttacks().get(2).getName(), atkL, atkD);
					String pp3 = selected.getAttacks().get(2).getCurrentPP() + "/" + selected.getAttacks().get(2).getMaxPP();
					g2.drawString(pp3, 170-(pp3.length()*5), atkD);
					g2.setColor(Color.BLACK);
					
			case 2: if(selected.getAttacks().get(1).getCurrentPP() == 0) 
						g2.setColor(Color.RED);
					//g2.drawString(ally.getAttacks().get(1).getName() + " (" + ally.getAttacks().get(1).getCurrentPP() + "/" + ally.getAttacks().get(1).getMaxPP() + ")", 195, 334);
					g2.drawString(selected.getAttacks().get(1).getName(), atkR, atkU);
					String pp2 = selected.getAttacks().get(1).getCurrentPP() + "/" + selected.getAttacks().get(1).getMaxPP();
					g2.drawString(pp2, 352-(pp2.length()*5), atkU);
					g2.setColor(Color.BLACK);
					
			case 1: if(selected.getAttacks().get(0).getCurrentPP() == 0)
						g2.setColor(Color.RED);
					//g2.drawString(ally.getAttacks().get(0).getName() + " (" + ally.getAttacks().get(0).getCurrentPP() + "/" + ally.getAttacks().get(0).getMaxPP() + ")", 25, 334);
					g2.drawString(selected.getAttacks().get(0).getName(), atkL, atkU);
					String pp1 = selected.getAttacks().get(0).getCurrentPP() + "/" + selected.getAttacks().get(0).getMaxPP();
					g2.drawString(pp1, 170-(pp1.length()*5), atkU);
					g2.setColor(Color.BLACK);
				break;
			}
			
			g2.draw(cursor);
		}
		
		if(statPanel){
			Rectangle2D.Double statOutline = new Rectangle2D.Double();
			statOutline.x = 244;
			statOutline.y = 120;
			statOutline.width = 135;
			statOutline.height = 140;
			
			Rectangle2D.Double statBG = new Rectangle2D.Double();
			statBG.x = statOutline.x-5;
			statBG.y = statOutline.y-5;
			statBG.width = statOutline.width+10;
			statBG.height = statOutline.height+10;
			g2.setColor(Color.WHITE);
			g2.fill(statBG);
			g2.setColor(Color.BLACK);
			g2.draw(statOutline);

			
			g2.setFont(font);
			g2.drawString("Max. HP", 246, 140);
			g2.drawString(""+selected.getMaxHP(), 242+135-getSizeMult(selected.getMaxHP())*10, 140);
			g2.drawString("Attack", 246, 160);
			g2.drawString(""+selected.getAtkStat(), 242+135-getSizeMult(selected.getAtkStat())*10, 160);
			g2.drawString("Defense", 246, 180);
			g2.drawString(""+selected.getDefStat(), 242+135-getSizeMult(selected.getDefStat())*10, 180);
			g2.drawString("Sp. Atk", 246, 200);
			g2.drawString(""+selected.getSpAtkStat(), 242+135-getSizeMult(selected.getSpAtkStat())*10, 200);
			g2.drawString("Sp. Def", 246, 220);
			g2.drawString(""+selected.getSpDefStat(), 242+135-getSizeMult(selected.getSpDefStat())*10, 220);
			g2.drawString("Speed", 246, 240);
			g2.drawString(""+selected.getSpeed(), 242+135-getSizeMult(selected.getSpeed())*10, 240);

			
		}
	

		outline.x = 5;
		outline.y = 320;
		outline.width = this.getWidth() - 10;
		outline.height = 40;
		
		g2.setColor(Color.BLACK);
		g2.draw(outline);
		
		if(pkmnMenu || !itemSelectMenu)
			g2.draw(cursor);
	}
	
	private class CharListener implements ActionListener{
		private int charTick = 0;
		
		public void actionPerformed(ActionEvent e){
			if(charTick < cfirst.length){
				char f1 = cfirst[charTick];
				_this.setString(f1, true);
			}
			else{
				if(charTick < cfirst.length+csecond.length){
					char f2 = csecond[charTick-cfirst.length];
					_this.setString(f2, false);
				}
				else{
					charTick = 0;
					t_text.stop();
					t_text = new Timer(35, cl);
					charTick = 0;
					_this.setCurrentText(s1,s2);
					return;
				}
			}
			charTick++;
			_this.repaint();
		}
	}
	
	public void setCurrentText(String first, String second){
		s1= first;
		s2 = second;
	}
	
	public void setString(char c, boolean oneORtwo){
		if(oneORtwo){
			s1 = s1 + c;
		}
		else{
			s2 = s2 + c;
		}	
	}
	
	public void displayText(String firstLine, String secondLine){
		cfirst = firstLine.toCharArray();
		csecond = null;

		s1 = ""; s2 = "";
		if(secondLine != null)
			csecond = secondLine.toCharArray();
		
		if(t_text != null && !t_text.isRunning() && !t_hp.isRunning()){
			SysOut.print("TEXT STARTED");
			t_text.start();
		}
	}
	
	public void setHPListenerChange(Pokemon p, int damage){
		hpcl.setChange(p, damage);
	}
	
	private class HPChangeListener implements ActionListener{
		private Pokemon subject;
		private int hp_change = 0, previousChange;
		public void actionPerformed(ActionEvent e){
			if(subject.getCurrentHP() == subject.getMaxHP() || previousChange==hp_change){
				t_hp.stop();
				_this.displayText(subject.getName() + " gained " + previousChange + "HP.","");
			}
			else{
				subject.setCurrentHP(subject.getCurrentHP()+1);
				previousChange++;
			}
		}
		public void setChange(Pokemon subject, int change){
			this.subject = subject;
			this.hp_change = change;
			this.previousChange = 0;
			t_hp.start();
		}
	}
}
