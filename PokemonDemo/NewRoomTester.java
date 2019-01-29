package PokemonDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class NewRoomTester extends JPanel{
	
	private NewBattleScreen testRoom;
	private JPanel _label;
	//private Font font;
	
	public NewRoomTester(){
		super();
		
		SysOut.print("NewRoomTester created.");
		
		this.colorLabel();
		this.createFrame();
		this.addControls();
	}
	
	public void colorLabel(){
		
		this.setBackground(Color.WHITE);
		
		JLabel jbLabel = new JLabel("J A V A B O Y  ");
		jbLabel.setFont(new Font("Gill Sans", Font.BOLD + Font.ITALIC, 14));
		jbLabel.setForeground(Color.LIGHT_GRAY);
		
		JLabel c = new JLabel("C");
		c.setForeground(Color.MAGENTA);
		
		JLabel o1 = new JLabel("O");
		o1.setForeground(new Color(175,89,163));
		
		JLabel l = new JLabel("L");
		l.setForeground(Color.GREEN);
		
		JLabel o2 = new  JLabel("O");
		o2.setForeground(Color.YELLOW);
		
		JLabel r = new JLabel("R");
		r.setForeground(Color.CYAN);
		
		_label = new JPanel();
		_label.setBackground(Color.DARK_GRAY);
		_label.setPreferredSize(new Dimension(400, 50));
		
		_label.add(jbLabel);
		_label.add(c);
		_label.add(o1);
		_label.add(l);
		_label.add(o2);
		_label.add(r);
	}

	public void createFrame(){
		
		this.setLayout(new BorderLayout());
	
		JPanel top = new JPanel();
		top.setBackground(Color.DARK_GRAY);
		top.setPreferredSize(new Dimension(400, 50));
		
		JPanel left = new JPanel();
		left.setBackground(Color.DARK_GRAY);
		left.setPreferredSize(new Dimension(50,300));
	
		JPanel right = new JPanel();
		right.setBackground(Color.DARK_GRAY);
		right.setPreferredSize(new Dimension(50, 300));
	
		this.add(top, BorderLayout.NORTH);
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
		this.add(_label, BorderLayout.SOUTH);
	
		//font = new Font("Courier New", Font.BOLD, 16);
	
	}

	public void addControls(){
		this.setFocusable(true); //KAAAAAAHHHHHNNNNN
		this.requestFocus();
		MyKeyListener mKey = new MyKeyListener();
		this.addKeyListener(mKey);
		SysOut.print("MyKeyListener created...");
	}
	
	public class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			switch(ke.getKeyCode()){
			case 38: testRoom.Up();
				break;
			case 39: testRoom.Right();
				break;
			case 37: testRoom.Left();
				break;
			case 40: testRoom.Down();
				break;
			case 90: testRoom.A_Button();
				break;
		//	case 88: testRoom.B_Button();
		//		break;
			case 88: testRoom.B_Button();
				break;
			}
		}
		//public void keyReleased(KeyEvent ke){SysOut.print("Key Released");}
		//public void keyTyped(KeyEvent ke){}
	}
	
	public void addTestRoom(NewBattleScreen nbs){
		this.testRoom = nbs;
		this.testRoom.setVisible(true);
		this.add(this.testRoom, BorderLayout.CENTER);
		this.repaint();
		
		SysOut.print("TestRoom added...");
	}

	public static void main(String[] args){
		/*NewRoomTester nrt = new NewRoomTester();

		NewBattleScreen nbs = new NewBattleScreen();
		nrt.addTestRoom(nbs);
		
		JFrame jf = new JFrame("New Room Test Screen");
		
		Dimension frame = new Dimension(490,490);
		jf.setMinimumSize(frame);
		jf.setPreferredSize(frame);
		jf.setMaximumSize(frame);
		
		jf.add(nrt);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);
		*/
	}
}
