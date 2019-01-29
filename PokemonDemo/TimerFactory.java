package PokemonDemo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Timer;

	public class TimerFactory{
		
		private Vector<Timer> timerList = new Vector<Timer>();
		private TimerGroup _timerGroup;
		
		public Vector<Timer> getTimerList(){
			return timerList;
		}
		
		public class TimerGroup{
			private NPCUpTimer up;
			private NPCDownTimer down;
			private NPCLeftTimer left;
			private NPCRightTimer right;
			
			@SuppressWarnings("unused")
			private int _NPCTimerCount = 0;
			
			public TimerGroup(Trainer t){
				up = new NPCUpTimer(t);
				down = new NPCDownTimer(t);
				left = new NPCLeftTimer(t);
				right = new NPCRightTimer(t);
				
				t.addTimerGroup(this);
			}
			
			public NPCUpTimer getUpTimer(){
				return up;
			}
			public NPCDownTimer getDownTimer(){
				return down;
			}
			public NPCLeftTimer getLeftTimer(){
				return left;
			}
			public NPCRightTimer getRightTimer(){
				return right;
			}
			
			private class NPCUpTimer implements ActionListener{
				private Trainer t;
				public NPCUpTimer(Trainer t){
					this.t = t;
				}
				public void actionPerformed(ActionEvent e){
					t._NPCTimeCount++;
					t.spaceUp();
					
					if(t._NPCTimeCount == 1){
				//		t.indexUp();
						SysOut.print("Trainer Indicees: " + t.getXIndex() + ", " + t.getYIndex());
						SysOut.print("Trainer Coordinates: " + t.getXSpace() + ", " + t.getYSpace());
						t.setDirectionAndImage(2);
					}
					
					if(t._NPCTimeCount == 5){
						t.stepUp1();
					}
					if(t._NPCTimeCount == 10){
						t.faceUp();
					}
					if(t._NPCTimeCount == 15){
						t.stepUp2();
					}
					if(t._NPCTimeCount == 20){
						t.faceUp();
						t._NPCWalkTimer.stop();
						t._NPCTimeCount = 0;
						SysOut.print("STOPPED");
					}
				}
			}
			private class NPCDownTimer implements ActionListener{
				private Trainer t;
				public NPCDownTimer(Trainer t){
					this.t = t;
				}
				public void actionPerformed(ActionEvent e){
					t._NPCTimeCount++;
					t.spaceDown();
					
					if(t._NPCTimeCount == 1){
						//t.indexDown();
						SysOut.print("Trainer Indicees: " + t.getXIndex() + ", " + t.getYIndex());

						t.setDirectionAndImage(0);
					}
					
					if(t._NPCTimeCount == 5){
						t.stepDown1();
					}
					if(t._NPCTimeCount == 10){
						t.faceDown();
					}
					if(t._NPCTimeCount == 15){
						t.stepDown2();
					}
					if(t._NPCTimeCount == 20){
						t.faceDown();
						t._NPCWalkTimer.stop();
						t._NPCTimeCount = 0;
						SysOut.print("STOPPED");
					}
				}
			}
			private class NPCLeftTimer implements ActionListener{
				private Trainer t;
				public NPCLeftTimer(Trainer t){
					this.t = t;
				}
				public void actionPerformed(ActionEvent e){
					t._NPCTimeCount++;
					t.spaceLeft();
					
					if(t._NPCTimeCount == 1){
			//			t.indexLeft();
						SysOut.print("Trainer Indicees: " + t.getXIndex() + ", " + t.getYIndex());

						t.setDirectionAndImage(1);
					}
					if(t._NPCTimeCount == 5){
						t.stepLeft1();
					}
					if(t._NPCTimeCount == 10){
						t.faceLeft();
					}
					if(t._NPCTimeCount == 15){
						t.stepLeft2();
					}
					if(t._NPCTimeCount == 20){
						t.faceLeft();
						t._NPCWalkTimer.stop();
						t._NPCTimeCount = 0;
						SysOut.print("STOPPED");
					}
					//_this.repaint();
				}
			}
			private class NPCRightTimer implements ActionListener{
				private Trainer t;
				public NPCRightTimer(Trainer t){
					this.t = t;
				}
				public void actionPerformed(ActionEvent e){
					t._NPCTimeCount++;
					t.spaceRight();
					
					if(t._NPCTimeCount == 1){
			//			t.indexRight();
						SysOut.print("Trainer Indicees: " + t.getXIndex() + ", " + t.getYIndex());
						t.setDirectionAndImage(3);
					}
					if(t._NPCTimeCount == 5){
						t.stepRight1();
					}
					if(t._NPCTimeCount == 10){
						t.faceRight();
					}
					if(t._NPCTimeCount == 15){
						t.stepRight2();
					}
					if(t._NPCTimeCount == 20){
						t.faceRight();
						t._NPCWalkTimer.stop();
						t._NPCTimeCount = 0;
						SysOut.print("STOPPED");
					}
					//_this.repaint();
				}
			}
		}
		
		public void addNewNPCTimer(Trainer mover){
			_timerGroup = new TimerGroup(mover);
			
			Timer t = new Timer(20, _timerGroup.getDownTimer());
			mover.setTimer(t);
			timerList.add(t);
		}
		
		public void startAllTimers(){
			for(int i=0; i<timerList.size(); i++){
	//			timerList.get(i).start();
			}
		}
		
		public void clearTimerList(){
			timerList = new Vector<Timer>();
		}
	}