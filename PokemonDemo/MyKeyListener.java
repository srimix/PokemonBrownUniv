package PokemonDemo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyListener extends KeyAdapter{
		public boolean up = false, down = false, left = false, right = false;
		private GameBoyScreen _gbs;
		
		public MyKeyListener(GameBoyScreen gbs){
			_gbs = gbs;
		}
		
		public void keyPressed(KeyEvent ke){	
			if(!_gbs.getCurrentPanel()._approachTimer.isRunning() && !_gbs.getCurrentPanel().escapeTimerLeave.isRunning() && !_gbs.getCurrentPanel().escapeTimerEnter.isRunning()){
				if(!_gbs.getCurrentPanel()._menuVisible && !_gbs.getCurrentPanel()._pcVisible && !_gbs.map.isVisible() && !_gbs._intro && !_gbs.getCurrentPanel()._martMenuVisible && _gbs.getCurrentPanel()._roomNum != -1){
				//	if(!up && !right && !left && !down){
						if(ke.getKeyCode() == 38 || ke.getKeyCode() == 87){
							up = true;
							right = false;
							left = false;
							down = false;
						}

						else if(ke.getKeyCode() == 39 || ke.getKeyCode() == 68){
							right = true;
							up = false;
							down = false;
							left = false;
						}
						
						else if(ke.getKeyCode() == 37 || ke.getKeyCode() == 65){
							left = true;
							right = false;
							down = false;
							up = false;
						}
					
						else if(ke.getKeyCode() == 40 || ke.getKeyCode() == 83){
							down = true;
							up = false;
							left = false;
							right = false;
						}

				//	}			
				}
				else{
					if(ke.getKeyCode() == 38 || ke.getKeyCode() == 87)
						_gbs.getCurrentPanel().Up();
				
					else if(ke.getKeyCode() == 39 || ke.getKeyCode() == 68)
						_gbs.getCurrentPanel().Right();
				
					else if(ke.getKeyCode() == 37 || ke.getKeyCode() == 65)
						_gbs.getCurrentPanel().Left();
				
					else if(ke.getKeyCode() == 40 || ke.getKeyCode() == 83)
						_gbs.getCurrentPanel().Down();

				}
			}
		}
		
		public void keyReleased(KeyEvent ke){

				if(!_gbs.getCurrentPanel()._menuVisible && !_gbs.getCurrentPanel()._pcVisible && !_gbs._intro){
				
					if(ke.getKeyCode() == 38 || ke.getKeyCode() == 87)
						up = false;
				
					else if(ke.getKeyCode() == 39 || ke.getKeyCode() == 68)
						right = false;
				
					else if(ke.getKeyCode() == 37 || ke.getKeyCode() == 65)
						left = false;
				
					else if(ke.getKeyCode() == 40 || ke.getKeyCode() == 83)
						down = false;

				}
			
		}
	}