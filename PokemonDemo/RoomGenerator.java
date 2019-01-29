package PokemonDemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RoomGenerator{
	
	private Vector<Objectz> objList;
	private Vector<Door> doorList;
	private FileNamePanel fnp;
	private FileSourcePanel fsp;
	private MapSourcePanel msp;
	private RoomSizePanel rsp;
	private TextAddPanel tap;
	private FileSourcePanelO fspo;
	private DoorPanel dp;
	private NPCPanel npc;
	private ItemzPanel ip;
	
	public static void main(String[] args){
		
		@SuppressWarnings("unused")
		RoomGenerator rg = new RoomGenerator("Pokemon: Brown - Room Generator");
	}
	
	public RoomGenerator(String s){
		JFrame jf = new JFrame(s);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel jp = new JPanel();
		jf.setPreferredSize(new Dimension(600, 600));
		
		objList = new Vector<Objectz>();
		doorList = new Vector<Door>();
		
		jp.setLayout(new GridLayout(11,1));
		
		fnp = new FileNamePanel();
		jp.add(fnp);
		
		fsp = new FileSourcePanel();
		jp.add(fsp);
		
		fspo = new FileSourcePanelO();
		jp.add(fspo);
		
		msp = new MapSourcePanel();
		jp.add(msp);
		
		rsp = new RoomSizePanel();
		jp.add(rsp);
		
		tap = new TextAddPanel();
		jp.add(tap);
		
		dp = new DoorPanel();
		jp.add(dp);
		
		npc = new NPCPanel();
		jp.add(npc);
		
		ip = new ItemzPanel();
		jp.add(ip);
		
		JPanel jpblank = new JPanel();
	//	jp.add(jpblank);
		
		GeneratorPanel gp = new GeneratorPanel();
		jp.add(gp);
		
		jf.add(jp);
		jf.pack();
		jf.setVisible(true);
	}
	
	public class FileNamePanel extends JPanel{
		private JTextField jtf;
		public FileNamePanel(){
			JLabel fname = new JLabel("Name of Room: ");
			jtf = new JTextField(20);
			
			this.add(fname);
			this.add(jtf);
		}
		public String getFileName(){
			return jtf.getText();
		}
	}
	
	public class FileSourcePanel extends JPanel{
		private JLabel _name;
		private JPanel _this;
		public FileSourcePanel(){
			_this = this;
			JLabel roomBG = new JLabel("Room BG Image: ");
			_name = new JLabel("_________________________");
			JButton bgButton = new JButton("Choose File");
			
			ButtonListener bl = new ButtonListener();
			bgButton.addActionListener(bl);
			
			this.add(roomBG);
			this.add(_name);
			this.add(bgButton);
		}
		public String getFileSourceName(){
			return  _name.getText();
		}
		private class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				JFileChooser fileChoose = new JFileChooser();
				if(fileChoose.showOpenDialog(_this) == JFileChooser.APPROVE_OPTION){
					File file = fileChoose.getSelectedFile();
					String name = file.getPath();
			//		name = name.substring(name.lastIndexOf("Pokemon"));
			//		name = name.replace('\\', '/');
					
					_name.setText(name);
				}
			}
		}
	}
	
	public class FileSourcePanelO extends JPanel{
		private JLabel _name;
		private JPanel _this;
		public FileSourcePanelO(){
			_this = this;
			JLabel roomBG = new JLabel("Room Over Image: ");
			_name = new JLabel("_________________________");
			JButton bgButton = new JButton("Choose File");
			
			ButtonListener bl = new ButtonListener();
			bgButton.addActionListener(bl);
			
			this.add(roomBG);
			this.add(_name);
			this.add(bgButton);
		}
		public String getFileSourceName(){
			return  _name.getText();
		}
		private class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				JFileChooser fileChoose = new JFileChooser();
				if(fileChoose.showOpenDialog(_this) == JFileChooser.APPROVE_OPTION){
					File file = fileChoose.getSelectedFile();
					String name = file.getPath();
				//	name = name.substring(name.lastIndexOf("Pokemon"));
				//	name = name.replace('\\', '/');
					
					_name.setText(name);
				}
			}
		}
	}
	
	public class MapSourcePanel extends JPanel{
		private JLabel _name;
		private JPanel _this;
		public MapSourcePanel(){
			_this = this;
			JLabel map = new JLabel("Map File: ");
			_name = new JLabel("_________________________");
			JButton mButton = new JButton("Choose File");
			
			ButtonListener bl = new ButtonListener();
			mButton.addActionListener(bl);
			
			this.add(map);
			this.add(_name);
			this.add(mButton);
		}
		public String getFileSourceName(){
			return  _name.getText();
		}
		private class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				JFileChooser fileChoose = new JFileChooser();
				if(fileChoose.showOpenDialog(_this) == JFileChooser.APPROVE_OPTION){
					File file = fileChoose.getSelectedFile();
					String name = file.getPath();
					
				//	name = name.substring(name.lastIndexOf("Pokemon"));
				//	name = name.replace('\\', '/');
					
					_name.setText(name);
				}
			}
		}
	}
	
	public class RoomSizePanel extends JPanel{
		private JTextField jtf, jtf2, jnum;
		public RoomSizePanel(){
			JLabel num = new JLabel("Room Number: ");
			jnum = new JTextField(3);
			JLabel x = new JLabel("Width: ");
			jtf = new JTextField(3);
			
			JLabel _ = new JLabel("---");
			JLabel _2 = new JLabel("---");
			
			JLabel y = new JLabel("Height: ");
			jtf2 = new JTextField(3);
			
			this.add(num);
			this.add(jnum);
			this.add(_2);
			this.add(x);
			this.add(jtf);
			this.add(_);
			this.add(y);
			this.add(jtf2);
		}
		public int getXHA(){
			int xH;
			if(jtf.getText() != "")
				xH = Integer.parseInt(jtf.getText());
			else
				xH = -1;
			return xH;
		}
		public int getYHA(){
			int yH;
			if(jtf2.getText() != "")
				yH = Integer.parseInt(jtf2.getText());
			else
				yH = -1;
			return yH;
		}
		public int getRoomNum(){
			return Integer.parseInt(jnum.getText());
		}
	}
	
	public class TextAddPanel extends JPanel{
		private JTextField  jtfWOW;
		private JPanel _this;
		public int a3;
		public JButton b;
		public TextAddPanel(){
			_this = this;
			JLabel j = new JLabel("Number of Objects");
			jtfWOW = new JTextField(3);
			b = new JButton("Add");
			ButtonListener bl = new ButtonListener();
			b.addActionListener(bl);
			
			this.add(j);
			this.add(jtfWOW);
			this.add(b);
		}
		private class ButtonListener implements ActionListener{

			public void actionPerformed(ActionEvent e){
				int in = Integer.parseInt(jtfWOW.getText());
				a3 = in;
				
				for(int i = 0; i < in; i++){
					JFrame j = new JFrame("Text Input");
					JPanel j2 = new JPanel();
					j2.setLayout(new GridLayout(4,1));
					JTextField jtf1 = new JTextField(15);
					JTextField jtf2 = new JTextField(15);
					
					JPanel jp = new JPanel();
					JLabel x = new JLabel("X: ");
					JLabel y = new JLabel("Y: ");
					JTextField jtfx = new JTextField(3);
					JTextField jtfy = new JTextField(3);
					JLabel dirS = new JLabel("Dir: ");
					String[] dir = {"NORTH", "EAST","SOUTH","WEST"};
					JComboBox jcb = new JComboBox(dir);
					
					jp.add(x);
					jp.add(jtfx);
					jp.add(y);
					jp.add(jtfy);
					jp.add(dirS);
					jp.add(jcb);
					
					Objectz o = null;
					JButton ad = new JButton("Add Object");
					StringListener sl = new StringListener(j, jtf1, jtf2, jtfx, jtfy, jcb);
					ad.addActionListener(sl);
					
					j2.add(jtf1);
					j2.add(jtf2);
					j2.add(jp);
					j2.add(ad);
					
					j.add(j2);
					j.pack();
					j.setVisible(true);
					
				}
			}
			private class StringListener implements ActionListener{
				private JFrame jf;
				private JTextField _line1, _line2, _x, _y;
				private Objectz obj;
				private JComboBox jcb;
			
				public StringListener(JFrame j, JTextField line1, JTextField line2, JTextField x, JTextField y, JComboBox jb){
					jf = j;
					_line1 = line1;
					_line2 = line2;
					_x = x;
					_y = y;
					jcb = jb;
				
					
				}
				public void actionPerformed(ActionEvent e){
					jf.setVisible(false);
					a3--;

					int xP = Integer.parseInt(_x.getText());
					int yP = Integer.parseInt(_y.getText());
					String s = (String)jcb.getSelectedItem();
					
					obj = new Objectz(xP, yP, s, _line1.getText(), _line2.getText());
					objList.add(obj);
					
					if(a3 == 0){
						b.setEnabled(false);
					}
				}
			}
		}
	}
	
	public class Objectz{
		int _x, _y;
		String dir, first, second;
		public Objectz(int x, int y, String direction, String firstLine, String secondLine){
			_x = x;
			_y = y;
			dir = direction;
			first = firstLine;
			second = secondLine;
			
			SysOut.print("X: " + _x);
			SysOut.print("Y: " + _y);
			SysOut.print("Direction: " + dir);
			SysOut.print("=====");
			SysOut.print(first);
			SysOut.print(second);
			SysOut.print("=====");
		}
		public int getX(){
			return _x;
		}
		public int getY(){
			return _y;
		}
		public int getDirection(){
			if(dir == "NORTH"){
				return 0;
			}
			if(dir == "EAST"){
				return 1;
			}
			if(dir == "SOUTH"){
				return 2;
			}
			if(dir == "WEST"){
				return 3;
			}
			return 4;
		}
	}
	
	public class DoorPanel extends JPanel{
		private JTextField jtf;
		private JButton b;
		private int in;
		private JFrame jf;
		public DoorPanel(){
			JLabel lab = new JLabel("Number of Doors");
			jtf = new JTextField(3);
			b = new JButton("Add");
			
			ButtonListener bl = new ButtonListener();
			b.addActionListener(bl);
			
			this.add(lab);
			this.add(jtf);
			this.add(b);
		}
		private class ButtonListener implements ActionListener{
			JTextField xT, yT, rT, jt, jy, jtg, yog;
			JComboBox jcb;
			public void actionPerformed(ActionEvent e){
				in = Integer.parseInt(jtf.getText());
				
				for(int i = 0; i < in; i++){
					jf = new JFrame("Door Input");
					JPanel jp = new JPanel();
					jp.setLayout(new GridLayout(6,1));
					
					JPanel dLoc = new JPanel();
					JLabel ind = new JLabel("Indices of Door: ");
					JLabel xLoc = new JLabel("X");
					xT = new JTextField(3);
					JLabel yLoc = new JLabel(" Y");
					yT = new JTextField(3);
					dLoc.add(ind);
					dLoc.add(xLoc);
					dLoc.add(xT);
					dLoc.add(yLoc);
					dLoc.add(yT);
					jp.add(dLoc);
					
					JPanel rNum = new JPanel();
					JLabel rm = new JLabel("Room To Be Entered (GBS Number): ");
					rT = new JTextField(3);
					rNum.add(rm);
					rNum.add(rT);
					jp.add(rNum);
					
					JPanel L = new JPanel(); 
					JLabel xL = new JLabel("New Location: ");
					JLabel xxL = new JLabel("X");
					jt = new JTextField(3);
					JLabel yL = new JLabel(" Y");
					jy = new JTextField(3);
					L.add(xL);
					L.add(xxL);
					L.add(jt);
					L.add(yL);
					L.add(jy);
					jp.add(L);
					
					JPanel g = new JPanel();
					JLabel gL = new JLabel("New Indices: ");
					JLabel xgL = new JLabel("X");
					jtg = new JTextField(3);
					JLabel ygL = new JLabel(" Y");
					yog = new JTextField(3);
					g.add(gL);
					g.add(xgL);
					g.add(jtg);
					g.add(ygL);
					g.add(yog);
					jp.add(g);
					
					JPanel nl = new JPanel();
					JLabel dir = new JLabel("New Direction: ");
					String[] dir2 = {"NORTH", "EAST","SOUTH","WEST"};
					jcb = new JComboBox(dir2);
					nl.add(dir);
					nl.add(jcb);
					jp.add(nl);
					
					JPanel bu = new JPanel();
					JButton jbu = new JButton("Add Door");
					DoorListener dl = new DoorListener(xT, yT, rT, jt, jy, jtg, yog, jcb);
					jbu.addActionListener(dl);
					bu.add(jbu);
					jp.add(bu);
					
					jf.add(jp);
					jf.setVisible(true);
					jf.pack();
				}
			}
			private class DoorListener implements ActionListener{
				private JTextField xLoc, yLoc, rNum, nxInd, nyInd, nxLoc, nyLoc;
				private JComboBox jcb;
				public DoorListener(JTextField xLoc, JTextField yLoc, JTextField rNum, JTextField nxInd, JTextField nyInd, JTextField nxLoc, JTextField nyLoc, JComboBox dir){
					this.xLoc = xLoc;
					this.yLoc = yLoc;
					this.rNum = rNum;
					this.nxInd = nxInd;
					this.nyInd = nyInd;
					this.nxLoc = nxLoc;
					this.nyLoc = nyLoc;
					this.jcb = dir;
				}
				public void actionPerformed(ActionEvent e){
					jf.setVisible(false);
					in--;

					Door d = new Door(xLoc, yLoc, rNum, nxInd, nyInd, nxLoc, nyLoc, jcb);
					doorList.add(d);
					
					if(in == 0){
						b.setEnabled(false);
					}
				}
			}
		}
	}
	
	public class Door{
		private JTextField xLoc, yLoc, rNum, nxInd, nyInd, nxLoc, nyLoc;
		private JComboBox jcb;
		public Door(JTextField xLoc, JTextField yLoc, JTextField rNum, JTextField nxInd, JTextField nyInd, JTextField nxLoc, JTextField nyLoc, JComboBox dir){
			this.xLoc = xLoc;
			this.yLoc = yLoc;
			this.rNum = rNum;
			this.nxInd = nxInd;
			this.nyInd = nyInd;
			this.nxLoc = nxLoc;
			this.nyLoc = nyLoc;
			this.jcb = dir;
		}
		public int getXLoc(){
			int xL = Integer.parseInt(xLoc.getText());
			return xL;
		}
		public int getYLoc(){
			int yL = Integer.parseInt(yLoc.getText());
			return yL;
		}
		public int getRoomNum(){
			int r = Integer.parseInt(rNum.getText());
			return r;
		}
		public int getNewXInd(){
			int x = Integer.parseInt(nxInd.getText());
			return x;
		}
		public int getNewYInd(){
			int y = Integer.parseInt(nyInd.getText());
			return y;
		}
		public int getNewXLoc(){
			int x = Integer.parseInt(nxLoc.getText());
			return x;
		}
		public int getNewYLoc(){
			int y = Integer.parseInt(nyLoc.getText());
			return y;
		}
		public int getDirection(){
			if((String)jcb.getSelectedItem() == "NORTH"){
				return 0;
			}
			if((String)jcb.getSelectedItem() == "EAST"){
				return 1;
			}
			if((String)jcb.getSelectedItem() == "SOUTH"){
				return 2;
			}
			if((String)jcb.getSelectedItem() == "WEST"){
				return 3;
			}
			return 4;
		}
	}

	public class NPCPanel extends JPanel{
		private JTextField jtf;
		private JButton b;
		private int in;
		private JFrame jf;
		public NPCPanel(){
			JLabel lab = new JLabel("Number of NPCs");
			jtf = new JTextField(3);
			b = new JButton("Add");
			
			//ButtonListener bl = new ButtonListener();
			//b.addActionListener(bl);
			
			this.add(lab);
			this.add(jtf);
			this.add(b);
		}
	}

	public class  NPC{
		public NPC(){
			
		}
	}
	
	public class ItemzPanel extends JPanel{
		private JTextField jtf;
		private JButton b;
		private int in;
		private JFrame jf;
		public ItemzPanel(){
			JLabel lab = new JLabel("Number of Items");
			jtf = new JTextField(3);
			b = new JButton("Add");
			
			//ButtonListener bl = new ButtonListener();
			//b.addActionListener(bl);
			
			this.add(lab);
			this.add(jtf);
			this.add(b);
		}
	}
	
	public class Itemz{
		public Itemz(){
			
		}
	}
	
	
	private class GeneratorPanel extends JPanel{
		public GeneratorPanel(){
	
			Icon ico = new ImageIcon(this.getClass().getResource("/PokemonImages/Icon/1.gif"));
			JButton genesis = new JButton("Generate Room", ico);

			//genesis.setBackground(Color.WHITE);
			//genesis.setForeground(Color.BLACK);
			CreationListener cl = new CreationListener();
			genesis.addActionListener(cl);
			this.add(genesis);
		}
		private class CreationListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				File f;
				BufferedWriter out;

				
				if(fnp.getName() != "" ){
					f = new File("PokemonDemo/"+fnp.getFileName()+".java");
					File f2 = new File("PokemonFiles/"+fnp.getFileName());
					f2.mkdir();
				
					File f3 = new File(fsp.getFileSourceName());
					f3.renameTo(new File("PokemonFiles/"+fnp.getFileName()+"/"+f3.getName()));
					
					File f4 = null;
					if(fspo.getFileSourceName() != "_________________________"){
						f4 = new File(fspo.getFileSourceName());
						f4.renameTo(new File("PokemonFiles/"+fnp.getFileName()+"/"+f4.getName()));
					}
					
					File f5 = new File(msp.getFileSourceName());
					f5.renameTo(new File("PokemonMaps/"+f5.getName()));
					
					try {
						out = new BufferedWriter(new FileWriter(f.getPath()));
						
						out.write("package PokemonDemo;");	out.newLine(); out.newLine();
						
						out.write("import java.awt.Graphics;");	out.newLine();
						out.write("import java.awt.Graphics2D;");	out.newLine();
						out.write("import java.awt.Color;"); out.newLine();
						out.write("import java.awt.image.BufferedImage;");	out.newLine();
						out.write("import java.io.File;");	out.newLine();
						out.write("import java.io.IOException;");	out.newLine();
						out.write("import java.util.Scanner;");	out.newLine();
						out.write("import javax.imageio.ImageIO;"); out.newLine(); out.newLine(); out.newLine();
						
						
						out.write("public class " + fnp.getFileName() + " extends PokePanel {"); out.newLine();
							out.write("\tprivate BufferedImage _roomSource, _roomO;"); out.newLine();
							
							out.write("\tpublic " + fnp.getFileName() + "(GameBoyScreen gbs){"); out.newLine();
								out.write("\t\tsuper(gbs);"); out.newLine();
								out.write("\t\tthis.createBaseRoom();"); out.newLine();
								out.write("\t\t_xSpace = -28;"); out.newLine();
								out.write("\t\t_ySpace = 0;"); out.newLine();
								out.write("\t\t_xIndex = 3;"); out.newLine();
								out.write("\t\t_yIndex = 4;"); out.newLine();
							out.write("\t}"); out.newLine(); out.newLine();
						
							out.write("\tpublic " + fnp.getFileName() + "(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){"); out.newLine();
								out.write("\t\tsuper(gbs, xSpace, ySpace, xInd, yInd, direction);"); out.newLine();
								out.write("\t\tthis.createBaseRoom();"); out.newLine();
							out.write("\t}"); out.newLine(); out.newLine();
						
							out.write("\tpublic void createBaseRoom(){"); out.newLine();
								out.write("\t\tthis.setBackground(Color.BLACK);"); out.newLine();
								out.write("\t\tthis._roomNum = "+rsp.getRoomNum()+";"); out.newLine();
								out.write("\t\t_textVisible = new boolean[" + objList.size() + "];"); out.newLine();
								out.write("\t\ttry{"); out.newLine();
									String s = fsp.getFileSourceName();
									s = s.substring(s.lastIndexOf("Pokemon"));
									s = s.replace('\\', '/');
									out.write("\t\t\t_roomSource = ImageIO.read(new File(\"PokemonFiles/"+fnp.getFileName()+"/"+f3.getName() +"\"));"); out.newLine();
									if(fspo.getFileSourceName() != "_________________________"){
										SysOut.print("Huh? " + fspo.getFileSourceName());
										s = fspo.getFileSourceName();
										s = s.substring(s.lastIndexOf("Pokemon"));
										s = s.replace('\\','/');
										out.write("\t\t\t_roomO = ImageIO.read(new File(\""+s+"\"));"); out.newLine();
									}
								out.write("\t\t} catch(IOException ioe){"); out.newLine();
									out.write("\t\t\tioe.printStackTrace();"); out.newLine();
									out.write("\t\t\tSystem.exit(0);"); out.newLine();
								out.write("\t\t}"); out.newLine();
								out.write("\t\tthis.createGrid();"); out.newLine();
							out.write("\t}"); out.newLine(); out.newLine();
						
							out.write("\tpublic void createGrid(){"); out.newLine();
								out.write("\t\tthis._room = new Room(" + rsp.getXHA() + "," + rsp.getYHA() + ");"); out.newLine();
								out.write("\t\ttry{"); out.newLine();
									s = msp.getFileSourceName();
									s = s.substring(s.lastIndexOf("Pokemon"));
									s = s.replace('\\','/');
									out.write("\t\t\tScanner scan = new Scanner(new File(\"" + s +"\"));"); out.newLine();
									out.write("\t\t\tfor(int i = 0; i < " + rsp.getYHA() + "; i++){"); out.newLine();
										out.write("\t\t\t\tfor(int i2 = 0; i2 < " + rsp.getXHA() + "; i2++){"); out.newLine();
											out.write("\t\t\t\t\tthis._room._roomGrid[i2][i] = Integer.parseInt(scan.next());"); out.newLine();
										out.write("\t\t\t\t}"); out.newLine();
									out.write("\t\t\t}"); out.newLine();
								out.write("\t\t}"); out.newLine();
								out.write("\t\tcatch(IOException ioe){"); out.newLine();
									out.write("\t\t\tioe.printStackTrace();"); out.newLine();
									out.write("\t\t\tSystem.exit(0);"); out.newLine();
								out.write("\t\t}"); out.newLine();
							out.write("\t}"); out.newLine(); out.newLine();
							
							out.write("\tpublic void paintComponent(Graphics g){"); out.newLine();
								out.write("\t\tsuper.paintComponent(g);"); out.newLine();
								out.write("\t\tGraphics2D g2 = (Graphics2D) g;"); out.newLine();
								out.write("\t\tg2.drawImage(_roomSource, null, this._xSpace, this._ySpace);"); out.newLine();
								out.write("\t\tthis.drawPlayer(g2);"); out.newLine();
								if(fspo.getFileSourceName() != "")
									out.write("\t\tg2.drawImage(_roomO, null, this._xSpace, this._ySpace);"); out.newLine();
								out.write("\t\tthis.drawBox(g2);"); out.newLine(); out.newLine();
								
								for(int i = 0; i < objList.size(); i++){
									out.write("\t\tif(_textVisible["+i+"]){"); out.newLine();
										out.write("\t\t\tthis.drawText(g2, \"" + objList.get(i).first + "\", \"" + objList.get(i).second + "\");"); out.newLine();
									out.write("\t\t}"); out.newLine(); out.newLine();
								}
								
								out.write("\t\tif(this._menuVisible)"); out.newLine();
									out.write("\t\t\tthis.drawMenu(g2);"); out.newLine(); out.newLine();
									
								out.write("\t\tif(this._pcVisible)"); out.newLine();
									out.write("\t\t\tthis.drawPC(g2);"); out.newLine();
							out.write("\t}"); out.newLine(); out.newLine();
							
							out.write("\tpublic void enterRoom(int xInd, int yInd){"); out.newLine();
							out.write("\t}"); out.newLine(); out.newLine();
								
								for(int i = 0; i < doorList.size(); i++){
									out.write("\t\tif((xInd == " + doorList.get(i).getXLoc() + ") && (yInd == " + doorList.get(i).getYLoc() + ")){"); out.newLine();
										out.write("\t\t\t_gbs.setCurrentPanel("+doorList.get(i).getRoomNum()+");"); out.newLine();
										out.write("\t\t\tPokePanel current = _gbs.getCurrentPanel();"); out.newLine();
										out.write("\t\t\tcurrent.setIndices("+doorList.get(i).getNewXLoc()+","+doorList.get(i).getNewYLoc()+");"); out.newLine();
										out.write("\t\t\tcurrent.setLocation("+doorList.get(i).getNewXInd()+","+doorList.get(i).getNewYInd()+");"); out.newLine();
										
										int a = doorList.get(i).getDirection();
										switch(a){
										case 0: out.write("\t\t\tcurrent.Up(); current.Up();"); out.newLine();
											break;
										case 1: out.write("\t\t\tcurrent.Right(); current.Right();"); out.newLine();
											break;
										case 2: out.write("\t\t\tcurrent.Down(); current.Down();"); out.newLine();
											break;
										case 3: out.write("\t\t\tcurrent.Left(); current.Left();"); out.newLine();
											break;
										}
								out.write("\t\t}"); out.newLine();
							out.write("\t}"); out.newLine(); out.newLine();
								}
							
							// blackout method
							
							out.write("\tpublic void A_Button(){"); out.newLine();
								out.write("\t\tif(!_menuVisible && !_pcVisible){"); out.newLine(); out.newLine();
									
								for(int i = 0; i < objList.size(); i++){
									out.write("\t\t\tif(_xIndex == " + objList.get(i)._x + " && _yIndex == " + objList.get(i)._y + " && " + objList.get(i).dir+"){"); out.newLine();
										out.write("\t\t\t\t_textVisible["+i+"] = !_textVisible["+i+"];"); out.newLine();
										out.write("\t\t\t\t_busy = !_busy;"); out.newLine();
									out.write("\t\t\t}"); out.newLine(); out.newLine();
								}
								
									out.write("\t\t\tthis.repaint();"); out.newLine();
								out.write("\t\t}"); out.newLine();
								out.write("\t\telse{"); out.newLine();
									out.write("\t\t\tsuper.A_Button();"); out.newLine();
								out.write("\t\t}"); out.newLine();
							out.write("\t}"); out.newLine();
								
						out.write("}");
						
						
						out.close();
						SysOut.print("File Successfully Generated");
						System.exit(0);
						
	/*					
						JFrame jiff = new JFrame("Preview");
						
						BufferedImage b = ImageIO.read(new File("PokemonFiles/"+fnp.getFileName()+"/"+f3.getName()));
						BufferedImage o;

						RoomPreview rp = new RoomPreview(b, null, "PokemonMaps/"+f5.getName());
						jiff.add(rp);
						jiff.setVisible(true);
						jiff.pack();
		*/				
					} catch (IOException e1) {
							e1.printStackTrace();
					}
					//SysOut.print("File generated");
				}
				//SysOut.print("Button Pressed");
			}
		}
	}
	
	public class RoomPreview extends JPanel{
		private BufferedImage bg, o;
		private String s;
		public RoomPreview(BufferedImage bg, BufferedImage o, String mapFileName){
			this.setPreferredSize(new Dimension(bg.getWidth(),bg.getHeight()));
			this.bg = bg;
			this.setBackground(Color.BLACK);
			this.o = o;
			s = mapFileName;
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			g2.drawImage(bg, null, 0,0);

	//		if(o!=null)
	//			g2.drawImage(o,null,0,0);

			g2.setColor(Color.WHITE);
			int s2;
			
			try{
				Scanner scan = new Scanner(new File("PokemonMaps/KeeneyRoom.map"));
				for(int i = 0; i < rsp.getYHA(); i++){
					for(int i2 = 0; i < rsp.getXHA(); i2++){
						if(scan.hasNextInt())
							s2 = scan.nextInt();
						g2.drawString("0", i2*5, i*5);
					}
				}
			} catch(IOException ioe7){}
		}
	}
}
