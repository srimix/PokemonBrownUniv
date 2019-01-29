package PokemonDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;



@SuppressWarnings("serial")
public class GameBoyScreen extends JPanel {

	public BufferedImage BACKGROUND;
	
	public static int rpTick=0;
	public static boolean finishedLoading = false;
	
	private JPanel _label;

	public PokemonBeltScreen2 pbs;
	public PlayerInfoScreen pis;
	public PokedexScreen pds;
	public MapScreen map;
	public BannerScreen bannerScreen;
	public PCBoxScreen pcBoxScreen;
	public ItemInventory2 ii;
	public MartMenu mart;
	public EvolutionScreen ev;
	
	private JavaBoyEmulator jbe;
	
	//private Vector<PokePanel2> _panelList = new Vector<PokePanel2>();

	private PokePanel2 _current;
	private GameBoyScreen _this;
	private NewBattleScreen _battle;
	private LogoScreen _logo;
	private Player _playerControl;
	private Trainer _rival;
	private int _rivalStarterNum=-1;
	private BufferedImage _playerImage, _grassImage;
	private MyKeyListener _myKey;
	private boolean _muted = false;
	boolean _intro = false;
	private boolean _moveMode, _surfing, _spinning;
	public static final int WAV = 0, BG = 1, FX = 2, ALL = 4;
	private Font _font;
	private int _numSeconds = 0;
	private Vector<Pokemon> evolutionList;
	private boolean _battleVis = false, isOld = false;
	private Timer _globalTime, nTimer, npcTimer, t;
	public int notifyX = 385, ntick = 0;
	public int npctick=0;
	//Fly/teleport/dig/etc
		public boolean _timeToFly= false;
		public boolean _timeToTeleport= false;
		public boolean _timeToDig= false;
		public boolean _escapeRope=false;
		
	public int numberOfTimesEliteFourHasBeenDefeated = 0;
		
	//Map coordinates
	public final int 
		BARHOL_MAPX = 270,
		BARHOL_MAPY = 305,
		
		KEENEY_MAPX = 0,
		KEENEY_MAPY = 0,
		
		MAIN_GREEN_MAPX = 0,
		MAIN_GREEN_MAPY = 0,
		
		SCILI_MAPX = 0,
		SCILI_MAPY = 0,
		
		CIT_MAPX = 0,
		CIT_MAPY = 0,
		
		BIOMED_MAPX = 0,
		BIOMED_MAPY = 0,
		
		SIDNEY_FRANK_MAPX = 0,
		SIDNEY_FRANK_MAPY = 0;
	
		
	public static int _defaultNPCSpeed=20;
	private Vector<Trainer> _currentMoving = new Vector<Trainer>(), _nextMoving = null;
	
	private SaveMessageBox smb2;
	
	public final int KEENEY_ROOM = 2,
		 KEENEY_HALL = 3,
		 KEENEY_QUAD = 4,
		 ROUTE_1 = 5,
		 WRISTON_QUAD = 6,
		 PATRIOTS_COURT = 7,
		 RATTY = 8,
		 RATTY_ENTRANCE = 9,
		 IVY_ROOM = 10,
		 GIS = 11,
		 MEIK_ROOM = 12,
		 MAIN_GREEN = 13,
		 CIT_ENTRANCE = 14,
		 CIT_LOBBY = 15,
		 CIT_2nd_FLOOR = 16,
		 WAYLAND_ARCH = 17,
		 ROUTE_2 = 18,
		 ROUTE_3 = 19,
		 J_WALTER_WILSON = 20,
		 HILLEL = 21,
		 THIRD_WORLD_CENTER = 22,
		 OLIVE_PIT_MAIN = 23,
		 OLIVE_PIT_1to2 = 24,
		 OLIVE_PIT_3to4 = 25,
		 OLIVE_PIT_4to5 = 26,
		 OLIVE_PIT_EXIT = 27,
		 BIOMED_1 = 28,
		 BIOMED_2 = 29,
		 PARADISO_GYM = 30,
		 GLASS_TUNNEL = 31,
		 SIDNEY_FRANK = 32,
		 POKECENTER_ROUTE_3 = 33,
		 SALOMON = 34,
		 LINCOLN_FIELD = 35,
		 ASHAMU = 36,
		 METCALF = 37,
		 VENLAB = 38,
		 LEEDS = 39,
		 LITTLEFIELD = 40,
		 REDHOUSE =41,
		 REGISTRAR = 42,
		 WILSON_LOBBY = 43,
		 SAYLES = 44,
		 BLUE_ROOM = 45,
		 FAUNCE_BASEMENT = 46,
		 THAYER_SOUTH =47,
		 PEMBROKE =48,
		 VDUB =49,
		 VDUB_DINING_HALL=50,
		 WOOLLEY_STAIR =51,
		 WOOLLEY_HALL=52,
		 WOOLLEY_ROOM=53,
		 SPIRITUS =54,
		 PEMBROKE_BACK=55,
		 ALUMNAE_HALL=56,
		 SATELLITE_GYM=57,
		 GREENHOUSE = 58,
		 SCIENCE_QUAD = 59,
		 OUTSIDE_BARHOL = 60,
		 BARHOL_LOBBY = 61,
		 CIT_HALL = 62,
		 CIT_OFFICE_JOVIAN = 63,
		 CIT_ANDY = 64,
		 BARHOL_BASEMENT  = 65,
		 BARHOL_GENERATOR = 66,
		 OUTSIDE_FISHCO = 67,
		 BARHOL_HALLWAY = 68,
		 BARHOL_GYM = 69,
		 FISHCO = 70,
		 BARHOL_LAB = 71,
		 SCILI_LOBBY = 72,
		 SCILI_ROOF = 73,
		 RITES_REASON=74,
		 PRINCE_LAB = 75,
		 BUS_TUNNEL = 76,
		 WATSON = 77,
		 VG_QUAD = 78,
		 JOES = 79,
		 QUIET_GREEN = 80,
		 SCILI_7 = 81,
		 SCILI_8 = 82,
		 SCILI_9 = 83,
		 SCILI_11 = 84,
		 SCILI_13 = 85,
		 SCILI_14 = 86,
		 WATERFIRE = 87,
		 MOTOROLA = 88,
		 BOOKSTORE = 89,
		 WATSON_GYM = 90,
		 BOOKSTORE_BASEMENT = 91,
		 SUGGS_GYM=92,
		 RUTH_GYM=93,
		 SLATER_MIKE = 94,
		 SLATER_DAVID = 95,
		 SLATER_SRI = 96,
		 SLATER_MAT = 97,
		 SLATER_CHAMP = 98,
		 CREDITS = 99,
		 CARRIE_TOWER=100,
		 UNIVERSITY_HALL_LOBBY=101,
		 MACMILLAN_LOBBY=102,
		 VIAVIA_LOBBY=103,
		 VIA_VIA=104,
		 CIT_OFFICE_NABEEL = 105,
		 CIT_OFFICE_HAYLEY = 106,
		 BAMBOO_GARDEN = 107,
		 THAYER_NORTH = 108,
		 SLATER_BASEMENT = 109,
		 METRO_MART = 110,
		 TEDESCHI = 111,
		 ANTONIOS = 112,
		 NICE_SLICE = 113,
		 KABOB_AND_CURRY = 114,
		 BBC = 115;
		
	
//	public BufferedImage bgArray[] = new BufferedImage[101];
		
	//FIXME - numRooms is currently lower than correct.
	public int numRooms = 116, roomsLoaded = -1;
	private PokePanel2[] array = new PokePanel2[numRooms];
	
	private int[] visitedArray = new int[numRooms];
	//private ProgressFrame progressFrame = new ProgressFrame();
	
	public void setProgressVisible(){
//		progressFrame.setAlwaysOnTop(true);
//		progressFrame.setVisible(true);
	}
	
	public void incrementRooms(){
		roomsLoaded++;
//		if(roomsLoaded > 0){
//			progressFrame.update((double)roomsLoaded/numRooms);
//			SysOut.print("PROGRESS: " + (double)roomsLoaded/numRooms);
//		}
	}

	public int getNumRoomsLoaded(){
		return roomsLoaded;
	}
	
	public int getNumRoomsTotal(){
		return numRooms;
	}
	
	public boolean isFlying(){
		return _timeToFly;
	}
	public boolean isTeleporting(){
		return _timeToTeleport;
	}
	public boolean isDigging(){
		return _timeToDig;
	}
	
	public void setFlying(boolean b){
		_timeToFly=b;
	}
	public void setTeleport(boolean b){
		_timeToTeleport=b;
	}
	public void setDigging(boolean b){
		_timeToDig=b;
	}
	
	/**
	 * Constructor for the object. Calls four basic methods that
	 * all contribute to giving it full functionality.
	 */
	public GameBoyScreen(JavaBoyEmulator jabe){
		super();
		
		jbe = jabe;
		
		new Thread(){
			public void run(){
				Repainter rp = new Repainter();
				Timer repaintTimer = new Timer(1, rp); //Repaints 60 FPS.
				repaintTimer.start();				
			}
		}.start();

		
		PImg.initialize(); //THIS NEEDS TO GO FIRST
		TImg.initialize();
		
		//initializePanelBGs();
		
		_this = this;
		
		this.colorLabel();
		
		this.createFrame();
		
		this.addPlayerAndRival();
		
		this.addMenuScreens();
		System.gc();
		this.addPanels();
		System.gc();
		this.addTime();
		
		this.addControls();
		
		this.checkMotion();
	}
	
	public void focusSelf(){
		jbe.requestFocus();
	}
	
	public void initializePanelBGs(){
//		for(int i = 2; i < 101; i++){
//			try{
//				SysOut.print("Load: " + i);
//				bgArray[i] = ImageIO.read(this.getClass().getResource("/PokemonFiles/BG/"+i+".png"));
//			}
//			catch(IOException ioe){
//				//ioe.printStackTrace();
//			}
//			catch(IllegalArgumentException iae){
//				
//			}
//		}
	}
	
	public void addMenuScreens(){

		pbs = new PokemonBeltScreen2(this);
		
		pis = new PlayerInfoScreen(this);
		
		ii = new ItemInventory2(this);
		
		ev = new EvolutionScreen(this);
		
		pds = new PokedexScreen(this);
		
		map = new MapScreen(this);
		
		mart = new MartMenu(this);
		
		bannerScreen = new BannerScreen(this);
		pcBoxScreen = new PCBoxScreen(this);

	}
	
	public void setNextMoving(Vector<Trainer> vt){
		_nextMoving = vt;
	}
	
	public void setCurrentMoving(Vector<Trainer> vt){
		_currentMoving = vt;
	}
	
	public void addTime(){
		GlobalTimeListener gtl = new GlobalTimeListener();
		_globalTime = new Timer(1, gtl);
		_globalTime.start();
		
		NotifyTimer nt = new NotifyTimer();
		nTimer = new Timer(10, nt);
		
		NPCTimer npcTime = new NPCTimer();
		npcTimer = new Timer(20, npcTime);
		
	}
	
	public void stopNTimer(){
		nTimer.stop();
	}
	
	public void startNTimer(){
		nTimer.start();
	}
	
	public boolean isNTimerRunning(){
		return nTimer.isRunning();
	}
	
	public boolean isGlobalTimerIsRunning(){
		return _globalTime.isRunning();
	}
	
	/**
	 * This method is responsible for creating the special
	 * nested JPanel that displays the "JAVABOY COLOR" JLabel.
	 * This is intended to add to the fact that the GameBoyScreen
	 * is supposed to resemble the screen of an actual Nintendo
	 * Gameboy. 
	 */
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
	
	/**
	 * Creates the outer frame of nested JPanels that surround
	 * the BorderLayout.CENTER position. This includes the specially
	 * labeled JPanel created in colorLabel() in the 
	 * BorderLayout.SOUTH position.
	 */
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
		
		_font = new Font("Courier New", Font.BOLD, 16);
	}
	
	public void addPlayerAndRival(){
		_playerControl = new Player(this);
		
		try {
			_rival = new Trainer.Rival(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Instantiates and adds to the PokePanel Vector the
	 * three PokePanels common to every game of Pokemon:Brown,
	 * regardless of whether or not the game is a previously saved
	 * one or a new one. Displays the TitleScreen as the first one.
	 */
	public void addPanels(){
		
		_logo = new LogoScreen(this);
		
		TitleScreen ts = new TitleScreen(this);
		//_panelList.add(ts);
		array[0] = ts;
		
		LoadScreen sls = new LoadScreen(this);
		//_panelList.add(sls);
		array[1] = sls;
	/*	SysOut.print(_panelList.size() + ", size");
		for(int i = 0; i < _panelList.size(); i++){
			if(_panelList.get(i) == sls)
				SysOut.print("MATCH: " + i);
		}
*/
		_battle = new NewBattleScreen(this);
		
		this.setCurrentPanel(-2);
		
	}
	
	public int[] getVisitedArray() {
		return visitedArray;
	}

	public void setVisitedArray(int[] visitedArray) {
		this.visitedArray = visitedArray;
	}

	public boolean isBattling(){
		return _battleVis;
	}
	
	public Trainer getRival(){
		return _rival;
	}
	
	public Vector<Pokemon> getEvolutionList(){
		return evolutionList;
	}
	
	public void setEvolutionList(Vector<Pokemon> newEvList){
		evolutionList = newEvList;
	}
	
	public void setMute(boolean muted){
		_muted = muted;
	}
	
	public boolean isMuted(){
		return _muted;
	}
	
	public Font getFont(){
		return _font;
	}
	

	
	
	/* yield does nothing
	 * wait throws an error
	 * stop does stop the music
	 * suspend and resume WORKS
	 * sleep needs the interrupted exception, 
	 * interrupt and notify throw an error, play sound while song is playing
	 */
	
	
	public void setIntroComplete(){
		_intro = false;
		_current.repaint();
	}

	public void addControls(){
		
		_myKey = new MyKeyListener(this);
		this.addKeyListener(_myKey);
		
		Action start = new StartAction();
		Action a = new A_ButtonAction();
		Action b = new B_ButtonAction();
		//Temporary key to discover coordinates.
		Action coord = new CoordAction();
		Action select = new SelectAction();
		Action reset = new ResetAction();
		Action complete = new CompleteEventsAction();
		Action number1 = new Number1Action();
		Action number2 = new Number2Action();
		Action number3 = new Number3Action();
		Action number4 = new Number4Action();
		Action number5 = new Number5Action();
		Action number6 = new Number6Action();
		Action number7 = new Number7Action();
		Action number8 = new Number8Action();
		Action number9 = new Number9Action();
		Action number0 = new Number0Action();
		
		this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "E");
		this.getActionMap().put("E", start);
		
		this.getInputMap().put(KeyStroke.getKeyStroke("Z"), "Z");
		this.getActionMap().put("Z", a);
		
		this.getInputMap().put(KeyStroke.getKeyStroke("X"), "X");
		this.getActionMap().put("X", b);
		
		this.getInputMap().put(KeyStroke.getKeyStroke("C"), "C");
		this.getActionMap().put("C", select);
		this.getInputMap().put(KeyStroke.getKeyStroke("Q"), "Q");
		this.getActionMap().put("Q", coord);
		
		//Resets all events in the room.
		this.getInputMap().put(KeyStroke.getKeyStroke("R"), "R");
		this.getActionMap().put("R", reset);
		//Completes all events in the room
		this.getInputMap().put(KeyStroke.getKeyStroke("T"), "T");
		this.getActionMap().put("T", complete);
		
		//Numbers
		this.getInputMap().put(KeyStroke.getKeyStroke("1"), "1");
		this.getActionMap().put("1", number1);
		this.getInputMap().put(KeyStroke.getKeyStroke("2"), "2");
		this.getActionMap().put("2", number2);
		this.getInputMap().put(KeyStroke.getKeyStroke("3"), "3");
		this.getActionMap().put("3", number3);
		this.getInputMap().put(KeyStroke.getKeyStroke("4"), "4");
		this.getActionMap().put("4", number4);
		this.getInputMap().put(KeyStroke.getKeyStroke("5"), "5");
		this.getActionMap().put("5", number5);
		this.getInputMap().put(KeyStroke.getKeyStroke("6"), "6");
		this.getActionMap().put("6", number6);
		this.getInputMap().put(KeyStroke.getKeyStroke("7"), "7");
		this.getActionMap().put("7", number7);
		this.getInputMap().put(KeyStroke.getKeyStroke("8"), "8");
		this.getActionMap().put("8", number8);
		this.getInputMap().put(KeyStroke.getKeyStroke("9"), "9");
		this.getActionMap().put("9", number9);
		this.getInputMap().put(KeyStroke.getKeyStroke("0"), "0");
		this.getActionMap().put("0", number0);
	}
	
	public MyKeyListener getKeyListener(){
		return _myKey;
	}
	
	private class StartAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			if(!_current.isApproaching())
				_current.Start();
		}
	}
	
	private class A_ButtonAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			if(!_current.isApproaching())
				_current.A_Button();
		}
	}
	
	private class B_ButtonAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			if(!_current.isApproaching())
				_current.B_Button();
		}
	}
	private class SelectAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			if(!_current.isApproaching()){
				_current.Select();
				_current.repaint();
			}
		}
	}
	private class CoordAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Coord();
		}
	}
	
	private class ResetAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.ResetEvents();
		}
	}
	private class CompleteEventsAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.CompleteEvents();
		}
	}
	
	private class Number1Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(1);
		}
	}
	private class Number2Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(2);
		}
	}
	private class Number3Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(3);
		}
	}
	private class Number4Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(4);
		}
	}
	private class Number5Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(5);
		}
	}
	private class Number6Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(6);
		}
	}
	private class Number7Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(7);
		}
	}
	private class Number8Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(8);
		}
	}
	private class Number9Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(9);
		}
	}
	private class Number0Action extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			_current.Number_Button(0);
		}
	}
	
	public LogoScreen getLS(){
		return _logo;
	}
	
	public PokePanel2 getPanel(int panelNum){
		//FIXME return _panelList.get(panelNum);
		return array[panelNum];
	}
	
	public PokePanel2 getCurrentPanel(){
		return _current;
	}
	
	public NewBattleScreen getBattlePanel(){
		return _battle;
	}
	
	public Player getPlayer(){
		return _playerControl;
	}
	
	public int getRivalStarterNum(){
		return _rivalStarterNum;
	}
	
	public void setRivalStarterNum(int b){
		_rivalStarterNum=b;
	}
	
	//Bike Functions
	public void setMode(boolean mode){
		_moveMode=mode;
	}
	public boolean getMode(){
		return _moveMode;
	}
	
	public void switchMode(){
		_moveMode=!_moveMode;
	}
	
	public void setSurf(boolean mode){
		_surfing=mode;
	}
	
	public boolean getSurf(){
		return _surfing;
	}

	public void setSpinning(boolean mode){
		_spinning=mode;
	}
	
	public boolean isSpinning(){
		return _spinning;
	}
	
	public void setCurrentPanel(int panelNum){
	
		if(_current != null)
			_current.setVisible(false);
		if(panelNum != -1 && panelNum != -2){
			_current = this.getPanel(panelNum);
			//Turn off bike when entering indoor rooms.
			if(!_current.canBikeHere()){
				this.setMode(false);
			}
			
			_battleVis = false;
		}
		else{
			if(panelNum == -1){
				_current = _battle;
				_battleVis = true;
			}
			if(panelNum == -2)
				_current = _logo;
		}
		
		//Have you visited this room?
		if(panelNum>=0 && getVisitedArray()[panelNum]==0){
			getVisitedArray()[panelNum]=1;
			SysOut.print("You just visited: " +panelNum + " for the first time.");
		}
		
		
		_current.enterRoom(panelNum);
		_current.setVisible(true);
		
		this.add(_current, BorderLayout.CENTER);
		//this.repaint();
		//this.repaint(0,0, 500, 500);
	}
	
	public void setOld(boolean old){
		isOld = old;
	}
	
	public boolean isOld(){
		return isOld;
	}
	
	public BufferedImage getPlayerImage(){
		return _playerImage;
	}
	
	public void setPlayerImage(BufferedImage image){
		_playerImage = image;
	}
	
	public BufferedImage getGrassImage(){
		return _grassImage;
	}
	
	public void setGrassImage(PokePanel2 p, int phase){
		if(this.getCurrentPanel()!=null){
			if (phase==1){
				if(this.getCurrentPanel().grassType==0){
					_grassImage = Player.getGrass1();	
				}
				if(this.getCurrentPanel().grassType==1){
					_grassImage = Player.getFall1();
				}
				if(this.getCurrentPanel().grassType==2){
					_grassImage = Player.getSnow1();
				}
			}
			else if (phase==2){
				if(this.getCurrentPanel().grassType==0){
					_grassImage = Player.getGrass2();	
				}
				if(this.getCurrentPanel().grassType==1){
					_grassImage = Player.getFall2();	
				}
				if(this.getCurrentPanel().grassType==2){
					_grassImage = Player.getSnow1();	
				}
				
			}
			else if (phase==3){
				if(this.getCurrentPanel().grassType==0){
					_grassImage = Player.getGrass3();	
				}
				if(this.getCurrentPanel().grassType==1){
					_grassImage = Player.getFall3();	
				}
				if(this.getCurrentPanel().grassType==2){
					_grassImage = Player.getSnow3();	
				}
			}
		}
		
	}
	
	public void createGame(){
		//KeeneyRoom is 2

		this.setProgressVisible();
		SysOut.print("START NEWING");
		
		new Thread(){
			public void run(){
				

		array[KEENEY_ROOM] = new KeeneyRoom(_this);
		array[KEENEY_HALL] = new KeeneyHall(_this);
	
		
		//KeeneyQuad kq
		array[KEENEY_QUAD] = new KeeneyQuad(_this);
		
		//Route1 R1 
		array[ROUTE_1] = new Route1(_this);
		
		//WristonQuad WQ 
		array[WRISTON_QUAD]= new WristonQuad(_this);
		
		//PatriotsCourt pc 
		array[PATRIOTS_COURT]= new  PatriotsCourt(_this);
		
		//Ratty r 
		array[RATTY]= new Ratty(_this);
				
		//RattyEntrance re 
		array[RATTY_ENTRANCE]= new RattyEntrance(_this);
		
		//IvyRoom IvyR 
		array[IVY_ROOM]= new IvyRoom (_this);
		
		//GameIntroductionScreen gis 
		array[GIS] = new GameIntroductionScreen(_this);
		//System.out.println("GIS CREATED");
		
		//MeikRoom mr 
		array[MEIK_ROOM]= new MeikRoom(_this);
		
		//MainGreen mg 
		array[MAIN_GREEN]= new MainGreen(_this);
		
		//CITAtrium cita 
		array[CIT_ENTRANCE]= new CITAtrium(_this);
		
		//CITLobby citl 
		array[CIT_LOBBY]= new CITLobby(_this);
		
	//	System.gc();
	//	CIT2ndFloor cit2 = new CIT2ndFloor(_this);
		//WaylandArch WArch 
		array[WAYLAND_ARCH]= new WaylandArch(_this);
		
		//Route2 r2 
		array[ROUTE_2]= new Route2(_this);
		//Route3 r3 
		array[ROUTE_3]= new Route3(_this);
		//JWalterWilson jww 
		array[J_WALTER_WILSON]= new JWalterWilson(_this);
		//Hillel hillel 
		array[HILLEL]= new Hillel(_this);
		//ThirdWorldCenter twc 
		array[THIRD_WORLD_CENTER]= new ThirdWorldCenter(_this);
		//OlivePitMain opm 
		array[OLIVE_PIT_MAIN]= new OlivePitMain(_this);
		//OlivePit12 op12 
		array[OLIVE_PIT_1to2]= new OlivePit12(_this);
		//OlivePit34 op34 
		array[OLIVE_PIT_3to4]= new OlivePit34(_this);
		//OlivePit45 op45 
		array[OLIVE_PIT_4to5]= new OlivePit45(_this);
		//OlivePitExit opExit 
		array[OLIVE_PIT_EXIT]= new OlivePitExit(_this);
		//BioMed1st bm1 
		array[BIOMED_1]= new BioMed1st(_this);
		//BioMed2nd bm2 
		array[BIOMED_2]= new BioMed2nd(_this);
		//ParadisoGym pg 
		array[PARADISO_GYM]= new ParadisoGym(_this);
		//GlassTunnel gt 
		array[GLASS_TUNNEL]= new GlassTunnel(_this);
		//SidneyFrank sf 
		array[SIDNEY_FRANK]= new SidneyFrank(_this);
		//PokecenterRoute3 pcr3 
		array[POKECENTER_ROUTE_3]= new PokecenterRoute3(_this);
		//Salomon sal 
		array[SALOMON]= new Salomon(_this);
		System.gc();
		//LincolnField lf 
		array[LINCOLN_FIELD]= new LincolnField(_this);
		//Ashamu ash 
		array[ASHAMU]= new Ashamu(_this);
		//Metcalf metcalf 
		array[METCALF]= new Metcalf(_this);
		//VENLab ven 
		array[VENLAB]= new VENLab(_this);
		//Leeds leeds 
		array[LEEDS]= new Leeds(_this);
		//Littlefield little 
		array[LITTLEFIELD]= new Littlefield(_this);
		//RedHouse red 
		array[REDHOUSE]= new RedHouse(_this);
		//Registrar regis
		array[REGISTRAR]= new Registrar(_this);
		//Wilson
		array[WILSON_LOBBY]= new WilsonLobby(_this);
		//Sayles
		//System.gc();
		array[SAYLES]= new Sayles(_this);
		
		array[BLUE_ROOM] = new BlueRoom(_this);
		
		array[FAUNCE_BASEMENT] = new FaunceBasement(_this);
		
		array[THAYER_SOUTH] = new ThayerSouth(_this);
		
		array[PEMBROKE] = new Pembroke(_this);
		
		array[VDUB] = new VDub(_this);
		
		array[VDUB_DINING_HALL] = new VDubDiningHall(_this);

		array[WOOLLEY_STAIR] = new WoolleyStair(_this);
		
		array[WOOLLEY_HALL] = new WoolleyHall(_this);
		
		array[WOOLLEY_ROOM] = new WoolleyRoom(_this);
		
		array[SPIRITUS] = new Spiritus(_this);
		
		array[PEMBROKE_BACK] = new PembrokeBack(_this);
		
		array[ALUMNAE_HALL] = new AlumnaeHall(_this);
		
		array[SATELLITE_GYM] = new SatelliteGym(_this);
		
		array[GREENHOUSE] = new Greenhouse(_this);
		//System.gc();
		array[SCIENCE_QUAD] = new ScienceQuad(_this);
		
		array[OUTSIDE_BARHOL] = new OutsideBarHol(_this);
		
		array[BARHOL_LOBBY] = new BarHolLobby(_this);
		
		array[CIT_HALL] = new CITHall(_this);
		
		array[CIT_OFFICE_JOVIAN] = new CITOfficeJovian(_this);
		
		array[CIT_ANDY] = new CITAndy(_this);
		
		array[BARHOL_BASEMENT] = new BarHolBasement(_this);
		
		array[BARHOL_GENERATOR] = new BarHolGenerator(_this);
		
		array[OUTSIDE_FISHCO] = new OutsideFishCo(_this);
		
		array[BARHOL_HALLWAY] = new BarHolHallway(_this);
		
		array[BARHOL_GYM] = new BarHolGym(_this);
		
		array[FISHCO] = new FishCo(_this);
		
		array[BARHOL_LAB] = new BarHolLab(_this);
		
		array[SCILI_LOBBY] = new SciLiLobby(_this);
		
		array[SCILI_ROOF] = new SciLiRoof(_this);
		
		array[RITES_REASON] = new RitesReason(_this);
		
		array[PRINCE_LAB] = new PrinceLab(_this);
		//System.gc();
		array[BUS_TUNNEL] = new BusTunnel(_this);
		
		array[WATSON] = new Watson(_this);
		
		array[VG_QUAD] = new VGQuad(_this);
		
		array[JOES] = new Jos(_this);
		
		array[QUIET_GREEN] = new QuietGreen(_this);
		
		array[SCILI_7] = new SciLi7(_this);
		array[SCILI_8] = new SciLi8(_this);
		array[SCILI_9] = new SciLi9(_this);
		array[SCILI_11] = new SciLi11(_this);
		array[SCILI_13] = new SciLi13(_this);
		array[SCILI_14] = new SciLi14(_this);
		//System.gc();
		
		array[WATERFIRE] = new Waterfire(_this);
		
		array[MOTOROLA] = new Motorola(_this);
		
		array[BOOKSTORE] = new Bookstore(_this);
		
		array[WATSON_GYM] = new WatsonGym(_this);
		
		array[BOOKSTORE_BASEMENT] = new BookstoreBasement(_this);
		
		array[SUGGS_GYM] = new SuggsGym(_this);
		//System.gc();
		array[RUTH_GYM] = new RuthGym(_this);
		
		array[SLATER_MIKE] = new SlaterMike(_this);
		
		array[SLATER_DAVID] = new SlaterDavid(_this);
		
		array[SLATER_SRI] = new SlaterSri(_this);
		
		array[SLATER_MAT] = new SlaterMat(_this);
		
		array[SLATER_CHAMP] = new SlaterChamp(_this);
		
		array[CREDITS] = new Credits(_this);
		
		array[CARRIE_TOWER] = new CarrieTower(_this);
		
		array[UNIVERSITY_HALL_LOBBY] = new UniversityHall(_this);
		
		array[MACMILLAN_LOBBY] = new MacMillanLobby(_this);
		
		array[VIAVIA_LOBBY] = new ViaViaLobby(_this);
		
		array[VIA_VIA] = new ViaVia(_this);
		
		array[CIT_OFFICE_NABEEL] = new CITOfficeNabeel(_this);
		
		array[CIT_OFFICE_HAYLEY] = new CITOfficeHayley(_this);
		
		array[THAYER_NORTH] = new ThayerNorth(_this);
		      
		array[SLATER_BASEMENT] = new SlaterBasement(_this);
		
		array[METRO_MART] = new MetroMart(_this);
		
		array[TEDESCHI] = new Tedeschi(_this);
		
		array[ANTONIOS] = new Antonios(_this);
		
		array[NICE_SLICE] = new NiceSlice(_this);
		
		array[KABOB_AND_CURRY] = new Kabob(_this);
		
		array[BBC] = new BBC(_this);
		
//		 METRO_MART = 110,
//		 TEDESCHI = 111,
//		 ANTONIOS = 112,
//		 NICE_SLICE = 113,
//		 KABOB_AND_CURRY = 114,
//		 BBC = 115;
//		System.gc();
		finishedLoading = true;
		SysOut.print("END NEWING");

			}
		}.start();
/*		
		this.addPanel(kr);
		this.addPanel(kh);
		this.addPanel(kq);
		this.addPanel(R1);
		this.addPanel(WQ);
		this.addPanel(pc);
		this.addPanel(r);
		this.addPanel(re);
		this.addPanel(IvyR);
		this.addPanel(gis);
	//	System.out.println("GIS CREATED");
		this.addPanel(mr);
		this.addPanel(mg);
		this.addPanel(cita);
		this.addPanel(citl);
		this.addPanel(citl);
		this.addPanel(WArch);
		this.addPanel(r2);
		this.addPanel(r3);
		this.addPanel(jww);
		this.addPanel(hillel);
		this.addPanel(twc);
		this.addPanel(opm);
		this.addPanel(op12);
		this.addPanel(op34);
		this.addPanel(op45);
		this.addPanel(opExit);
		this.addPanel(bm1);
		this.addPanel(bm2);
		this.addPanel(pg);
		this.addPanel(gt);
		this.addPanel(sf);
		this.addPanel(pcr3);
		this.addPanel(sal);
		this.addPanel(lf);
		this.addPanel(ash);
		this.addPanel(metcalf);
		this.addPanel(ven);
		this.addPanel(leeds);
		this.addPanel(little);
		this.addPanel(red);
		this.addPanel(regis);
*/		
//		for(int i = 0; i < 100; i++){
//			RedHouse red2 = new RedHouse(this);
//			this.addPanel(red2);
//		}
		
	}

	public void runGame(){
		this.setCurrentPanel(11);
		((GameIntroductionScreen)this.getCurrentPanel()).startFade();
		_intro = true;
		
		_currentMoving = _current._movingTrainers;
		
		GameTimer gt = new GameTimer();
		t = new Timer(1000, gt);
		t.start();	
	}
	
//	public void addPanel(PokePanel2 p){
//		_panelList.add(p);	
//	}
	
//	public Vector<PokePanel2> getPanelList() {
//		return _panelList;
//	}

//	public void setPanelList(Vector<PokePanel2> _panelList) {
//		this._panelList = _panelList;
//	}

	public void saveHallOfFame(){
		numberOfTimesEliteFourHasBeenDefeated++;
		
		File hof = new File("HALL OF FAME");
		hof.mkdir();
		
		File record = new File(hof, "Hall of Fame Record " + numberOfTimesEliteFourHasBeenDefeated);
		String path = record.getPath();
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write("POKEMON: BROWN EDITION -- HALL OF FAME");
			out.newLine();
			out.write("======================================");
			out.newLine();
			out.write("Player Name: " + _playerControl.getPlayerName());
			out.newLine();
			out.write("====================");
			out.newLine();
			out.write("Champion Pokemon");
			out.newLine();
			out.write("--------------------");
			out.newLine();
			for(int i = 0; i < _playerControl.getAllActive().size(); i++){
				out.write(_playerControl.getAllActive().get(i).getName() + ", Lvl. " + _playerControl.getAllActive().get(i).getLevel());
				out.newLine();
			}
			out.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveGame(String filename){
		if(smb2!= null){
			smb2.setVisible(false);
		}
		SaveMessageBox smb = new SaveMessageBox(0);
		if(!filename.equalsIgnoreCase("AUTO_SAVE.pkmn")){
			smb.setVisible(true);
		}
		
		File dir = new File("SAVE FILES");
		dir.mkdir();
		
		File file = new File(dir, filename);
		String pathName = file.getPath();
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(pathName));
			out.write(_current._roomNum + "");
			out.newLine();
			out.write(_current._xSpace + "");
			out.newLine();
			out.write(_current._ySpace + "");
			out.newLine();
			out.write(_current._xIndex + "");
			out.newLine();
			out.write(_current._yIndex + "");
			out.newLine();
			
			if(_playerControl.getPokedex() == null){
				out.write("0");
			}
			else{
				out.write("1");
			}
			out.newLine();
			
			//ADD POKEDEX DATA HERE
			for(int i = 1; i < 154; i++){
				if(_playerControl.getPokedex() != null){
					if(i != 152 && i != 153){
						if(_playerControl.getPokedex().hasCaught((i))){
							out.write("2 ");
						}
						else if(_playerControl.getPokedex().hasSeen((i))){
							out.write("1 ");
						}
						else{
							out.write("0 ");
						}
					}
					else{
						if(i == 152){
							if(_playerControl.getPokedex().hasCaught((216))){
								out.write("2 ");
							}
							else if(_playerControl.getPokedex().hasSeen((216))){
								out.write("1 ");
							}
							else{
								out.write("0 ");
							}
						}
						if(i == 153){
							if(_playerControl.getPokedex().hasCaught((217))){
								out.write("2 ");
							}
							else if(_playerControl.getPokedex().hasSeen((217))){
								out.write("1 ");
							}
							else{
								out.write("0 ");
							}
						}
					}
				}
				else{
					out.write("# ");
				}
			}
			out.newLine();
			
			if(_current.NORTH)
				out.write("0");
			
			if(_current.EAST)
				out.write("1");
			
			if(_current.SOUTH)
				out.write("2");
			
			if(_current.WEST)
				out.write("3");
			
			if(!_current.NORTH && !_current.SOUTH && !_current.EAST && !_current.WEST)
				out.write("2");
			
			out.newLine();
			
			if (this.isMuted())
				out.write("1");
			else
				out.write("0");
			out.newLine();
			/*******************************************/
			//Saving Pokemon. 
			
			//First write the total number of pokemon that the user has including PC. 
			//This will come in use for quick loading.
			
			_playerControl.combinePokemonVectors(); //This is crucial.
			
			out.write(_playerControl.getAllPokemon().size()+"");
			out.newLine();
			
			//Write every jth pokemon and its required info. Each value is separated by a space, 
			//which will be read by scanner.
			//_number _name currentHP maxHP _level _exp _status _belt _attacks _currentPP _maxPP per attack.
			for (int j =0; j < _playerControl.getAllPokemon().size(); j++){
			
			//Pokedex Number. This is in case some idiot nicknames their Pidgey as "Pikachu".
				out.write(_playerControl.getPokemon(j).getDexNum()+" ");

			//Name, in case it has been nicknamed.	
				out.write(_playerControl.getPokemon(j).getName()+" ");
			//CurrentHP.	
				out.write(_playerControl.getPokemon(j).getCurrentHP()+" ");
			//MaxHP.	
				out.write(_playerControl.getPokemon(j).getMaxHP()+" ");
			//Level.	
				out.write(_playerControl.getPokemon(j).getLevel()+" ");
			//Experience.	
				out.write(_playerControl.getPokemon(j).getExp()+" ");
			//Status.	
				out.write(_playerControl.getPokemon(j).getStatus()+" ");
			
			//Position on Belt. 1-6 is active, 7 is PC.
			/**This is important.**/
				out.write(_playerControl.getPokemon(j).getBelt()+" ");
			
			//Stats.
				out.write(_playerControl.getPokemon(j).getAtkStat()+" ");
				out.write(_playerControl.getPokemon(j).getDefStat()+" ");
				out.write(_playerControl.getPokemon(j).getSpAtkStat()+" ");
				out.write(_playerControl.getPokemon(j).getSpDefStat()+" ");
				out.write(_playerControl.getPokemon(j).getSpeed()+" ");
				out.write(_playerControl.getPokemon(j).getAccuracy()+" ");
				out.write(_playerControl.getPokemon(j).getEvasion()+" ");
				out.write(_playerControl.getPokemon(j).getAtkIV()+" ");
				out.write(_playerControl.getPokemon(j).getDefIV()+" ");
				out.write(_playerControl.getPokemon(j).getSpAtkIV()+" ");
				out.write(_playerControl.getPokemon(j).getSpDefIV()+" ");
				out.write(_playerControl.getPokemon(j).getSpdIV()+" ");
				out.write(_playerControl.getPokemon(j).getHPIV()+" ");
			
			
			//Attacks. Dependent on the number of attacks it knows.
			//Attack #1	
				out.write(_playerControl.getPokemon(j).getAttacks().get(0).getAttackNum()+" ");
			//currentPP #1
				out.write(_playerControl.getPokemon(j).getAttacks().get(0).getCurrentPP()+" ");
			//maxPP #1
				out.write(_playerControl.getPokemon(j).getAttacks().get(0).getMaxPP()+" ");
			
				if(_playerControl.getPokemon(j).getAttacks().size()>1){
			//Attack #2	
					out.write(_playerControl.getPokemon(j).getAttacks().get(1).getAttackNum()+" ");
			//currentPP #2
					out.write(_playerControl.getPokemon(j).getAttacks().get(1).getCurrentPP()+" ");
			//maxPP #2
					out.write(_playerControl.getPokemon(j).getAttacks().get(1).getMaxPP()+" ");
				}
			
				if(_playerControl.getPokemon(j).getAttacks().size()>2){
			//Attack #3
					out.write(_playerControl.getPokemon(j).getAttacks().get(2).getAttackNum()+" ");
			//currentPP #3
					out.write(_playerControl.getPokemon(j).getAttacks().get(2).getCurrentPP()+" ");
			//maxPP #3
					out.write(_playerControl.getPokemon(j).getAttacks().get(2).getMaxPP()+" ");
				}
			
				if(_playerControl.getPokemon(j).getAttacks().size()>3){
			//Attack #4	
					out.write(_playerControl.getPokemon(j).getAttacks().get(3).getAttackNum()+" ");
			//currentPP #4
					out.write(_playerControl.getPokemon(j).getAttacks().get(3).getCurrentPP()+" ");
			//maxPP #4
					out.write(_playerControl.getPokemon(j).getAttacks().get(3).getMaxPP()+" ");
				}
			
			//On to the next one.
				out.newLine();
			}
			/******************************************/

			//Write the player's name.
			out.write(_playerControl.getPlayerName());
			out.newLine();
			out.write(_playerControl.getRivalName());
			out.newLine();
			//Rival Starter
			out.write(this.getRivalStarterNum()+"");
			out.newLine();
			
			//How much money you have.
			out.write(_playerControl.getMoney() +"");
			out.newLine();
			
			/*
			 * Item quantity. In comparison to the pokemon vector, I will store every item
			 * with its respective quantity. There are many less items than pokemon.
			 */ 
			for (int i = 0; i < _playerControl.getAllItems().size(); i++){
				out.write(_playerControl.getItem(i).getRemaining()+" ");
			}
			out.newLine();
			
			//Current Movement mode: Bike, etc.
			if(this.getMode()==false)
				out.write("0");
			else
				out.write("1");
			
			//Are you surfing?
			out.newLine();
			if(this.getSurf()==false)
				out.write("0");
			else
				out.write("1");
			
//			//KQuad line
//			out.newLine();
//			out.write("0");
			
			/*
			 * ROOM EVENTS SAVED
			 */
			int plSize = array.length;
			for(int i = 0; i < plSize; i++){
				out.newLine();
				if(array[i] != null && array[i].getCheckList() != null && array[i].getCheckList().size() > 0){
					SysOut.print("Room " + i + ", Checklist size = " + array[i].getCheckList().size());
					for(int j = 0; j < array[i].getCheckList().size(); j++){
						SysOut.print("Room number "+ i+ "," +array[i].getCheckList().get(j));
						if(array[i].getCheckList().get(j)==1){
							out.write("1 ");
						}
						else{
							out.write("0 ");
						}
					}
					if(array[i].description==null){
						SysOut.print("THIS IS WHATS TRIPPIN YOU: "+ i);
					}
					out.write(array[i].description);
				}
				else{
					SysOut.print("Room number "+ i+ " has no events.");
					out.write("-1");
				}
				
			}
			
			out.newLine();
			out.write(_numSeconds+"");

			out.newLine();
			for(int i = 0; i < 8; i++){
				if(this.getPlayer().isGymLeaderDefeated(i+1))
					out.write("1 ");
				else
					out.write("0 ");
			}
			
			//Trainer positions
			out.newLine();
			if(this.getCurrentPanel()._movingTrainers.size()==0){
				out.write("-1");
			}
			else{
				for(int i=0; i<this.getCurrentPanel()._movingTrainers.size();i++){
					out.write(""+ this.getCurrentPanel()._movingTrainers.get(i).getXIndex() +" ");
				}
			}
			
			out.newLine();
			
			if(this.getCurrentPanel()._movingTrainers.size()==0){
				out.write("-1");
			}
			else{
				for(int i=0; i<this.getCurrentPanel()._movingTrainers.size();i++){
					out.write(""+ this.getCurrentPanel()._movingTrainers.get(i).getYIndex() +" ");
				}
			}
			
			//Visited Rooms.
			out.newLine();
			for(int i = 0; i < numRooms; i++){
				if(this.getVisitedArray()[i]==1){
					out.write("1 ");
				}
				else{
					out.write("0 ");
				}
			}
			
			
			//Repel
			out.newLine();
			this.getCurrentPanel();
			out.write(""+PokePanel2.repelCount);
			
			out.newLine();
			out.write(_playerControl.getPkmnCenter()+"");
			
			out.newLine();
			out.write(numberOfTimesEliteFourHasBeenDefeated + "");
			
			out.close();
		
			/**
			this.encrypt(file);
			 **/
			
			if(!filename.equalsIgnoreCase("AUTO_SAVE.pkmn")){
				smb.setVisible(false);
				smb2 = new SaveMessageBox(1);
				smb2.setVisible(true);
				this.requestFocus();
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void saveGame(){
		JFileChooser fileChoose = new JFileChooser();
		
		File dir = new File("SAVE FILES");
		dir.mkdir();
		
		fileChoose.setCurrentDirectory(dir);
		
		FileFilter filter = new ExtensionFilter("Pokemon Save File", ".pkmn");
		fileChoose.addChoosableFileFilter(filter);
		fileChoose.setFileFilter(filter);
		
		if(fileChoose.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			File file = fileChoose.getSelectedFile();
			String name = file.getName();

			if(name.indexOf('.')==-1){
				name += ".pkmn";
				file = new File(file.getParentFile(), name);
			}
			//String pathName = file.getPath();
			
			saveGame(name);
		}
	}
	
	public void encrypt(File file){
		String name = file.getName();
		if(name.endsWith(".pkmn")){
			String pathName = file.getPath();
			
			try{
				BufferedReader read = new BufferedReader(new FileReader(pathName));
				
				String string = ".";
				Vector<byte[]> fileBytes = new Vector<byte[]>();
				do{
					string = read.readLine();
					if(string != null && fileBytes != null && string.getBytes() != null)
						fileBytes.add(string.getBytes());
				}
				while(string != null);
				
				for(int i=0; i < fileBytes.size(); i++){
					byte[] codeBytes= new byte[fileBytes.get(i).length];
					for(int j=0; j < fileBytes.get(i).length; j++){
						codeBytes[j] = ((byte) (fileBytes.get(i)[j]+((i+1)*(j+1))%30)); 
					}
					fileBytes.set(i, codeBytes);					
				}
				
				BufferedWriter out = new BufferedWriter(new FileWriter(pathName));
				
				for(int k=0; k < fileBytes.size(); k++){
					out.write(new String(fileBytes.get(k)));
					out.newLine();
				}
				
				out.close();				
			}
			catch(NumberFormatException nfe){
				nfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	public File decrypt(File file){
		String name = file.getName();
		if(name.endsWith(".pkmn")){
			String pathName = file.getPath();
			try{
				BufferedReader read = new BufferedReader(new FileReader(pathName));
				
				String string = ".";
				Vector<byte[]> fileBytes = new Vector<byte[]>();
				do{
					string = read.readLine();
					if(string != null && fileBytes != null && string.getBytes() != null)
						fileBytes.add(string.getBytes());
				}
				while(string != null);
				
				for(int i=0; i < fileBytes.size(); i++){
					byte[] codeBytes= new byte[fileBytes.get(i).length];
					for(int j=0; j < fileBytes.get(i).length; j++){
						codeBytes[j] = ((byte) (fileBytes.get(i)[j]-((i+1)*(j+1))%30)); 
					}
					fileBytes.set(i, codeBytes);					
				}
				
				BufferedWriter out = new BufferedWriter(new FileWriter(pathName));
				
				for(int k=0; k < fileBytes.size(); k++){
					out.write(new String(fileBytes.get(k)));
					out.newLine();
				}
				
				out.close();
				
			}
			catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		
		return file;
	}
	
	public void loadGame(){
		JFileChooser fileChoose = new JFileChooser();
		
		File dir = new File("SAVE FILES");
		dir.mkdir();
		
		fileChoose.setCurrentDirectory(dir);
		
		FileFilter filter = new ExtensionFilter("Pokemon Save File", ".pkmn");
		fileChoose.addChoosableFileFilter(filter);
		fileChoose.setFileFilter(filter);

		if(fileChoose.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			File file = fileChoose.getSelectedFile();
			
			
		//	file = this.decrypt(file);
			
			
			String name = file.getName();
			
			if(name.endsWith(".pkmn")){
				String pathName = file.getPath();
				
				try{
					BufferedReader read = new BufferedReader(new FileReader(pathName));
					int roomType = Integer.parseInt(read.readLine());
					int xSpace = Integer.parseInt(read.readLine());
					int ySpace = Integer.parseInt(read.readLine());
					int xIndex = Integer.parseInt(read.readLine());
					int yIndex = Integer.parseInt(read.readLine());
					
					if(Integer.parseInt(read.readLine()) == 1){
						_playerControl.givePokedex();
						_playerControl.giveTownMap();
					}
					
					String s = read.readLine();
					if(s.charAt(0) != '#'){
						Scanner ScanDex = new Scanner(s).useDelimiter("\\s* \\s*");
						for(int i = 1; i < 154; i++){
							int status = ScanDex.nextInt();
							if(i != 152 && i != 153){
								if(status == 2){
									_playerControl.getPokedex().addToCatchList(i);
									//SysOut.print("Caught: " + i);
								}
								if(status == 1){
									_playerControl.getPokedex().addToWatchList(i);
									//SysOut.print("Seen: " + i);
								}
							}
							else{
								if(i == 152){
									if(status == 2){
										_playerControl.getPokedex().addToCatchList(216);
										//SysOut.print("CAUGHT: TEDDIURSA");
									}
									if(status == 1){
										_playerControl.getPokedex().addToWatchList(216);
									}
								}
								if(i == 153){
									if(status == 2){
										_playerControl.getPokedex().addToCatchList((217));
									}
									if(status == 1){
										_playerControl.getPokedex().addToWatchList((217));
									}
								}
							}
						}
					}
					
					int direction = Integer.parseInt(read.readLine());
					int muted = Integer.parseInt(read.readLine());
					/*****LOADING POKEMON.*********************/ 
					//First clear default pokemon so we don't have duplicates.
					_playerControl.getAllPokemon().clear();
					
					int TotalPkmn = Integer.parseInt(read.readLine());
					for (int j=0; j < TotalPkmn; j++){
						//_number _name currentHP maxHP _level _exp _status _belt _attacks _currentPP _maxPP per attack.
						String Pkmn = read.readLine();
					    Scanner ScannedPkmn = new Scanner(Pkmn).useDelimiter("\\s* \\s*");
						//Keep reading as long as there are things to read.	
							//Number. This part is going to suck, but is also read first
					    	//so I can add attributes directly after.
					    	//Maybe think of a more elegant solution.
					    	int PokeNum= ScannedPkmn.nextInt();
					    	
					    	_playerControl._allPokemon.add(Pokemon.getPokemonByDexNumber(PokeNum));
					    	
					    	//Name of jth pokemon.
					    	_playerControl.getPokemon(j).setName(ScannedPkmn.next());
					    	
					    	//CurrentHP
					    	//SysOut.print("HP: " + _playerControl.getPokemon(j).getCurrentHP());
					    	_playerControl.getPokemon(j).setNoAnimateCurrentHP(ScannedPkmn.nextInt());
					    	//SysOut.print("HP Post: " + _playerControl.getPokemon(j).getCurrentHP());
					    	
					    	//MaxHP
					    	//SysOut.print("HP pre max: " + _playerControl.getPokemon(j).getCurrentHP());
					    	_playerControl.getPokemon(j).setMaxHP(ScannedPkmn.nextInt());
					    	//SysOut.print("HP post max: " + _playerControl.getPokemon(j).getCurrentHP());
					    	
					    	//Level
					    	_playerControl.getPokemon(j).setLevel(ScannedPkmn.nextInt());
					    	
					    	//Experience
					    	_playerControl.getPokemon(j).setExp(ScannedPkmn.nextInt());
					    	
					    	//Status
					    	_playerControl.getPokemon(j).setStatus(ScannedPkmn.nextInt());
					    	
					    	//Belt Position
					    	_playerControl.getPokemon(j).setBelt(ScannedPkmn.nextInt());
					    	
					    	//Stats.
					    	_playerControl.getPokemon(j).setAtkStat(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setDefStat(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setSpAtkStat(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setSpDefStat(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setSpeed(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setAccuracy(ScannedPkmn.nextDouble());
					    	_playerControl.getPokemon(j).setEvasion(ScannedPkmn.nextDouble());
					    	_playerControl.getPokemon(j).setAtkIV(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setDefIV(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setSpAtkIV(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setSpDefIV(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setSpdIV(ScannedPkmn.nextInt());
					    	_playerControl.getPokemon(j).setHPIV(ScannedPkmn.nextInt());
					    	
					    	
					    	//SysOut.print("HP post stats: " + _playerControl.getPokemon(j).getCurrentHP());
					    	
					    	//First clear default attacks so we don't have duplicates.
							_playerControl.getPokemon(j).getAttacks().clear();
					    	
					    	//1st Attack (This will require another clever trick.
					    	int attack1 = ScannedPkmn.nextInt();
					    	_playerControl.getPokemon(j).addNewAttackNum(attack1);
					    	//1st Current PP
					    	_playerControl.getPokemon(j).getAttacks().get(0).setCurrentPP(ScannedPkmn.nextInt());
					    	//1st Max PP
					    	_playerControl.getPokemon(j).getAttacks().get(0).setMaxPP(ScannedPkmn.nextInt());
					    	
					    	//2nd Attack if it exists.
						    if (ScannedPkmn.hasNext()){
						    	//1st Attack (This will require another clever trick.
						    	int attack2 = ScannedPkmn.nextInt();
						    	_playerControl.getPokemon(j).addNewAttackNum(attack2);
						    	//1st Current PP
						    	_playerControl.getPokemon(j).getAttacks().get(1).setCurrentPP(ScannedPkmn.nextInt());
						    	//1st Max PP
						    	_playerControl.getPokemon(j).getAttacks().get(1).setMaxPP(ScannedPkmn.nextInt());
						    		
						    }
						    //3rd Attack if it exists.
						    if (ScannedPkmn.hasNextInt()){
						    	//1st Attack (This will require another clever trick.
						    	int attack3 = ScannedPkmn.nextInt();
						    	_playerControl.getPokemon(j).addNewAttackNum(attack3);
						    	//1st Current PP
						    	_playerControl.getPokemon(j).getAttacks().get(2).setCurrentPP(ScannedPkmn.nextInt());
						    	//1st Max PP
						    	_playerControl.getPokemon(j).getAttacks().get(2).setMaxPP(ScannedPkmn.nextInt());
						    		
						    }
						    //4th Attack if it exists
						    if (ScannedPkmn.hasNext()){
						    	//1st Attack (This will require another clever trick.
						    	int attack4 = ScannedPkmn.nextInt();
						    	_playerControl.getPokemon(j).addNewAttackNum(attack4);
						    	//1st Current PP
						    	_playerControl.getPokemon(j).getAttacks().get(3).setCurrentPP(ScannedPkmn.nextInt());
						    	//1st Max PP
						    	_playerControl.getPokemon(j).getAttacks().get(3).setMaxPP(ScannedPkmn.nextInt());
						    		
						    }
						    //SysOut.print("HP end: " + _playerControl.getPokemon(j).getCurrentHP());
					}
					//Last of all, Filter out the 1-6 pokemon into the Active Temp pokemon vector.
					this._playerControl.sortActivePokemon();		
										
					/***********************************/
					
					//Read Player's Name  
					
					String playername = read.readLine();
					Scanner ScannedPlayer = new Scanner(playername).useDelimiter("\\s* \\s*");
					_playerControl.setPlayerName(ScannedPlayer.next());
					String rivalname = read.readLine();
					Scanner ScannedRival = new Scanner(rivalname).useDelimiter("\\s* \\s*");
					_playerControl.setRivalName(ScannedRival.next());
					//Set his starter.
					this._rivalStarterNum = Integer.parseInt(read.readLine());
					
					//Read in how much money you have.
					_playerControl.setMoney(Integer.parseInt(read.readLine()));
					
				
					//To parse item save data into each respective item.
					String item = read.readLine();
				    Scanner ScannedItems = new Scanner(item).useDelimiter("\\s* \\s*");
						for (int i = 0; i < _playerControl.getAllItems().size(); i++){
							if(ScannedItems.hasNextInt()){
								_playerControl.getItem(i).setRemaining(ScannedItems.nextInt());
							}
							else{
								_playerControl.getItem(i).setRemaining(0);
							}
							
						}
						
					//Bike
					int mode = Integer.parseInt(read.readLine());
					
					//Surf
					int surf = Integer.parseInt(read.readLine());
					
//					//Keeney Quad line.
//					String KQuad = read.readLine();
//				    Scanner ScannedKQuad = new Scanner(KQuad).useDelimiter("\\s* \\s*");
//					int trainer = ScannedKQuad.nextInt();				
					

					
				//	this.createGame();
					_intro = false;
					
					PokePanel2 loadedRoom = null;
					switch(roomType){
					
						case KEENEY_ROOM: loadedRoom = new KeeneyRoom(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case KEENEY_HALL: loadedRoom = new KeeneyHall(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case KEENEY_QUAD: loadedRoom = new KeeneyQuad(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case ROUTE_1: loadedRoom = new Route1(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case WRISTON_QUAD: loadedRoom = new WristonQuad(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case PATRIOTS_COURT: loadedRoom = new PatriotsCourt(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case RATTY: loadedRoom = new Ratty(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case RATTY_ENTRANCE: loadedRoom = new RattyEntrance(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case IVY_ROOM: loadedRoom = new IvyRoom(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case MAIN_GREEN: loadedRoom = new MainGreen(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case MEIK_ROOM: loadedRoom = new MeikRoom(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case CIT_ENTRANCE: loadedRoom = new CITAtrium(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case CIT_LOBBY: loadedRoom = new CITLobby(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case CIT_2nd_FLOOR: loadedRoom = new CITLobby(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case WAYLAND_ARCH: loadedRoom = new WaylandArch(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case ROUTE_2: loadedRoom = new Route2(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case ROUTE_3: loadedRoom = new Route3(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case J_WALTER_WILSON: loadedRoom = new JWalterWilson(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case HILLEL: loadedRoom = new Hillel(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case THIRD_WORLD_CENTER: loadedRoom = new ThirdWorldCenter(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case OLIVE_PIT_MAIN: loadedRoom = new OlivePitMain(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case OLIVE_PIT_1to2: loadedRoom = new OlivePit12(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case OLIVE_PIT_3to4: loadedRoom = new OlivePit34(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case OLIVE_PIT_4to5: loadedRoom = new OlivePit45(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case OLIVE_PIT_EXIT: loadedRoom = new OlivePitExit(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BIOMED_1: loadedRoom = new BioMed1st(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BIOMED_2: loadedRoom = new BioMed2nd(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case PARADISO_GYM: loadedRoom = new ParadisoGym(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case GLASS_TUNNEL: loadedRoom = new GlassTunnel(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SIDNEY_FRANK: loadedRoom = new SidneyFrank(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case POKECENTER_ROUTE_3: loadedRoom = new PokecenterRoute3(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SALOMON: loadedRoom = new Salomon(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case LINCOLN_FIELD: loadedRoom = new LincolnField(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case ASHAMU: loadedRoom = new Ashamu(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case METCALF: loadedRoom = new Metcalf(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case VENLAB: loadedRoom = new VENLab(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case LEEDS: loadedRoom = new Leeds(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case LITTLEFIELD: loadedRoom = new Littlefield(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case REDHOUSE: loadedRoom = new RedHouse(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case REGISTRAR: loadedRoom = new Registrar(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case WILSON_LOBBY: loadedRoom = new WilsonLobby(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SAYLES: loadedRoom = new Sayles(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BLUE_ROOM: loadedRoom = new BlueRoom(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case FAUNCE_BASEMENT: loadedRoom = new FaunceBasement(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case THAYER_SOUTH: loadedRoom = new ThayerSouth(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case PEMBROKE: loadedRoom = new Pembroke(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case VDUB: loadedRoom = new VDub(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case VDUB_DINING_HALL: loadedRoom = new VDubDiningHall(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case WOOLLEY_STAIR: loadedRoom = new WoolleyStair(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case WOOLLEY_HALL: loadedRoom = new WoolleyHall(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case WOOLLEY_ROOM: loadedRoom = new WoolleyRoom(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SPIRITUS: loadedRoom = new Spiritus(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case PEMBROKE_BACK: loadedRoom = new PembrokeBack(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case ALUMNAE_HALL: loadedRoom = new AlumnaeHall(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SATELLITE_GYM: loadedRoom = new SatelliteGym(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case GREENHOUSE: loadedRoom = new Greenhouse(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SCIENCE_QUAD: loadedRoom = new ScienceQuad(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case OUTSIDE_BARHOL: loadedRoom = new OutsideBarHol(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BARHOL_LOBBY: loadedRoom = new BarHolLobby(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case CIT_HALL: loadedRoom = new CITHall(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case CIT_OFFICE_JOVIAN: loadedRoom = new CITOfficeJovian(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case CIT_ANDY: loadedRoom = new CITAndy(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BARHOL_BASEMENT: loadedRoom = new BarHolBasement(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BARHOL_GENERATOR: loadedRoom = new BarHolGenerator(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case OUTSIDE_FISHCO: loadedRoom = new OutsideFishCo(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BARHOL_HALLWAY: loadedRoom = new BarHolHallway(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BARHOL_GYM: loadedRoom = new BarHolGym(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case FISHCO: loadedRoom = new FishCo(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BARHOL_LAB: loadedRoom = new BarHolLab(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SCILI_LOBBY: loadedRoom = new SciLiLobby(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SCILI_ROOF: loadedRoom = new SciLiRoof(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case RITES_REASON: loadedRoom = new RitesReason(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case PRINCE_LAB: loadedRoom = new PrinceLab(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BUS_TUNNEL: loadedRoom = new BusTunnel(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case WATSON: loadedRoom = new Watson(this,xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case VG_QUAD: loadedRoom = new VGQuad(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case JOES: loadedRoom = new Jos(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case QUIET_GREEN: loadedRoom = new QuietGreen(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SCILI_7: loadedRoom = new SciLi7(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SCILI_8: loadedRoom = new SciLi8(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SCILI_9: loadedRoom = new SciLi9(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SCILI_11: loadedRoom = new SciLi11(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SCILI_13: loadedRoom = new SciLi13(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SCILI_14: loadedRoom = new SciLi14(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case WATERFIRE: loadedRoom = new Waterfire(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case MOTOROLA: loadedRoom = new Motorola(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case BOOKSTORE: loadedRoom = new Bookstore(this, xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case WATSON_GYM: loadedRoom = new WatsonGym(this, xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case BOOKSTORE_BASEMENT: loadedRoom = new BookstoreBasement(this, xSpace, ySpace,  xIndex, yIndex, direction);
							break;
						case SUGGS_GYM: loadedRoom = new SuggsGym(this, xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case RUTH_GYM: loadedRoom = new RuthGym(this, xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SLATER_MIKE: loadedRoom = new SlaterMike(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SLATER_DAVID: loadedRoom = new SlaterDavid(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SLATER_SRI: loadedRoom = new SlaterSri(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SLATER_MAT: loadedRoom = new SlaterMat(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case SLATER_CHAMP: loadedRoom = new SlaterChamp(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case CREDITS: loadedRoom = new Credits(this);
							break;
						case CARRIE_TOWER: loadedRoom = new CarrieTower(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case UNIVERSITY_HALL_LOBBY: loadedRoom = new UniversityHall(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case MACMILLAN_LOBBY: loadedRoom = new MacMillanLobby(this, xSpace,ySpace, xIndex,yIndex,direction);
							break;
						case VIAVIA_LOBBY: loadedRoom = new ViaViaLobby(this, xSpace,ySpace, xIndex,yIndex,direction);
							break;
						case VIA_VIA: loadedRoom = new ViaVia(this, xSpace,ySpace, xIndex,yIndex,direction);
							break;
						case CIT_OFFICE_HAYLEY: loadedRoom = new CITOfficeHayley(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case CIT_OFFICE_NABEEL: loadedRoom = new CITOfficeNabeel(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case BAMBOO_GARDEN: loadedRoom = new BambooGarden(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case THAYER_NORTH: loadedRoom = new ThayerNorth(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case SLATER_BASEMENT: loadedRoom = new SlaterBasement(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case METRO_MART: loadedRoom = new MetroMart(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case TEDESCHI: loadedRoom = new MetroMart(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case ANTONIOS: loadedRoom = new Antonios(this, xSpace,ySpace, xIndex,yIndex,direction);
							break;
						case NICE_SLICE: loadedRoom = new NiceSlice(this, xSpace, ySpace, xIndex, yIndex, direction);
							break;
						case KABOB_AND_CURRY: loadedRoom = new Kabob(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						case BBC: loadedRoom = new BBC(this,xSpace,ySpace,xIndex,yIndex,direction);
							break;
						default: loadedRoom = new KeeneyRoom(this);
					}
					
					//Set Player movement mode.
					if(mode==0)
						setMode(false);
					else
						setMode(true);

					//Set Player surf
					if(surf==0)
						setSurf(false);
					else
						setSurf(true);
					
					//Set Player facing direction
					this.getCurrentPanel().setPlayerDirection(direction);
					if(direction==0){
						this.getCurrentPanel().NORTH=true;
				    	try{
							if (_moveMode){
								setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/bikeupFace.png")));
							}
							else
								if (_surfing)
									setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/surfU1.png")));
								else{
									setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/upFace.png")));
									this.getCurrentPanel()._playerReflection=this.getPlayer().getDownFaceImage();
								}
						}
						catch(IOException ioe){
							ioe.printStackTrace();
						}
				    }
					
				    if(direction==3){
				    	this.getCurrentPanel().WEST=true;
				    	try{
							if (_moveMode)
								setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/bikeleftFace.png")));
							else
								if (_surfing)
									setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/surfL1.png")));
								else{
									setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/leftFace.png")));
									this.getCurrentPanel()._playerReflection=this.getPlayer().getRightFaceImage();
								}
						}
						catch(IOException ioe){
							ioe.printStackTrace();
						}
				    }
				    
				    if(direction==1){
				    	this.getCurrentPanel().EAST=true;
				    	try{
							if (_moveMode)
								setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/bikerightFace.png")));
							else
								if (_surfing)
									setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/surfR1.png")));
								else{
									setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/rightFace.png")));
									this.getCurrentPanel()._playerReflection=this.getPlayer().getLeftFaceImage();
								}
						}
						catch(IOException ioe){
							ioe.printStackTrace();
						}
				    }
				    if(direction==2){
				    	this.getCurrentPanel().SOUTH=true;
				    	try{
							if (_moveMode)
								setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/bikedownFace.png")));
							else if (_surfing)
									setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/surfD1.png")));
							else{
								setPlayerImage(ImageIO.read(new File("PokemonFiles/PlayerImages/downFace.png")));
								this.getCurrentPanel()._playerReflection=this.getPlayer().getUpFaceImage();
							}
						}
						catch(IOException ioe){
							ioe.printStackTrace();
						}
				    }
					
					
					if (muted==0)
						this.setMute(false);
					else
						this.setMute(true);
					
					
					
					//_panelList.removeElementAt(roomType);
					//_panelList.add(roomType, loadedRoom);
					array[roomType] = loadedRoom;
					
//					if(trainer == 0)
//						_panelList.get(4).defeat(0);
					
					/*
					 * ROOM EVENTS LOADING
					 */
					String event = "";
					Scanner ScannedEvents;
					for(int i = 0; i < array.length; i++){
						event = read.readLine();
						ScannedEvents = new Scanner(event).useDelimiter("\\s* \\s*");
						Vector<Integer> list = new Vector<Integer>();
						while(ScannedEvents.hasNextInt()){
							list.add(ScannedEvents.nextInt());
						}
						
						//If the event exists, load it.
						if(list.get(0)!=-1){
							array[i].setCheckList(list);
							array[i].loadAllEvents();
						}
						
					}
		
					_numSeconds = Integer.parseInt(read.readLine());
					//SysOut.print(""+_numSeconds);
					
					Scanner ScanGymLeaders = new Scanner(read.readLine());
					for(int i = 0; i < 8; i++){
						if(ScanGymLeaders.hasNextInt()){
							int NextBadge= ScanGymLeaders.nextInt();
								//SysOut.print("Badge:"+NextBadge);
							if(NextBadge == 1){
								this.getPlayer().defeatGymLeader(i+1);
							}
						}
					}
					
					
					//Load Trainer Locations
					Vector<Integer> TrainerX= new Vector<Integer>();
					Vector<Integer> TrainerY= new Vector<Integer>();
					Scanner ScanTrainerX = new Scanner(read.readLine()).useDelimiter("\\s* \\s*");
					while(ScanTrainerX.hasNextInt()){
						int nextX=ScanTrainerX.nextInt();
						if (nextX>=0){
							TrainerX.add(nextX);
						}
					}
					
					Scanner ScanTrainerY = new Scanner(read.readLine()).useDelimiter("\\s* \\s*");
					while(ScanTrainerY.hasNextInt()){
						int nextY=ScanTrainerY.nextInt();
						if (nextY>=0){
							TrainerY.add(nextY);
						}
					}
					
					//Visited Rooms
					Scanner ScanVisitedRooms = new Scanner(read.readLine());
					for(int i = 0; i < numRooms; i++){
						if(ScanVisitedRooms.hasNextInt()){
							int NextRoom= ScanVisitedRooms.nextInt();
							this.getVisitedArray()[i]=NextRoom;
							//SysOut.print("R: " + visitedArray[i]);
						}
					}
					
					//Repel
					PokePanel2.repelCount=Integer.parseInt(read.readLine());
					if (PokePanel2.repelCount>0){
						PokePanel2.repelling=true;
					}
					
					//Pkmn Center
					_playerControl.setPkmnCenter(Integer.parseInt(read.readLine()));
								
					
					numberOfTimesEliteFourHasBeenDefeated = Integer.parseInt(read.readLine());
					
					//These have to be the last things you do, else events don't load right.
					this.setCurrentPanel(roomType);
					this.getCurrentPanel().createBaseRoom();
					
					//Prepare Loaded Trainer Locations
					if(TrainerX.size()>0){
						for (int i=0; i<TrainerX.size();i++){
							this.getCurrentPanel().loadTrainerLocations(i, TrainerX.get(i), TrainerY.get(i));
						}
					}
					
					this.setNextMoving(this.getCurrentPanel()._movingTrainers);
					
					
					
					//Surf Music
					if(this.getSurf()){
						M2.playBG(M2.SURF);
					}
					else{
						//Bike music
						if(!this.getMode()){
							M2.playBG(this.getCurrentPanel().getSong());
						}
						else{
							M2.playBG(M2.BIKE);
						}
					}
					
					GameTimer gt = new GameTimer();
					t = new Timer(1000, gt);
					t.start();
					
					//this.repaint();
					//this.repaint(0,0, 500, 500);

				}
				catch(Exception e){
					this.throwError(this, "Could not read selected file. It may be corrupt.");
					e.printStackTrace();
				}
			}
			else{
				this.throwError(this, "Incorrect File Format. Please use '.pkmn' files only.");
			}
		}
	}
	
	public void throwError(GameBoyScreen gbs, String message){
		ErrorMessageBox emb = new ErrorMessageBox(this, message);
		emb.setVisible(true);
	}
	
	public int getTimeInSeconds(){
		return _numSeconds;
	}

	private class GameTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			_numSeconds++;

			if(_current.getPIS().isVisible())
				_current.getPIS().repaint();
			
			if(_numSeconds%600 == 0){
				new Thread(){
					public void run(){
					
						if(!_battleVis && getCurrentPanel()._roomNum >= 2 ){
							//Autosave current
							while(_current.getTimeCount() != 0){}
								saveGame("AUTO_SAVE.pkmn");
								//SysOut.print("autosaved");
						}
						else{
							//Complicated. Find some way to save in previous room maybe?
						}
						SysOut.print("Autosave: " + System.currentTimeMillis());
					}
				}.start();
			}
		}
	}
	
	private class GlobalTimeListener implements ActionListener{
		private int tick = 0;
		public void actionPerformed(ActionEvent e){
			
			//FIXME: needs to halt movement instantly.
			
			if(_myKey != null && !_current._menuVisible && !_current._pcVisible && !_current._martMenuVisible && !_current.inLineOfSight() && !_current._approachTimer.isRunning()){
				if(_myKey.up){
					_current.Up();
				}
				if(_myKey.down){
					_current.Down();
				}
				if(_myKey.left){
					_current.Left();
				}
				if(_myKey.right){
					_current.Right();
				}
			}
			
			
			
			/*if(_current.getMovingTrainers() != null){
				for (int i=0; i<_current._movingTrainers.size();i++){
					//Deal with one trainer at a time.
					_current._thisTrainer=i;
					_current.repaint();
					if(!_current._movingTrainers.get(i).hasJustMoved()){
						if(!_current._movingTrainers.get(i).isInterrupted()){
							_current.moveNPCToTarget(_current._movingTrainers.get(i).getAvoidMethod(),_current._movingTrainers.get(i).getCurrentDestX(),_current._movingTrainers.get(i).getCurrentDestY());
							if(_current._movingTrainers.get(i).hasReachedDest()){
								_current._movingTrainers.get(i).prepareNextDest();
							}
						}	
					}
					else{
						if(tick == 20){
							_current._movingTrainers.get(i).setJustMoved(false);
						}
					}					
				}
				
				//Start all timers.
				for (int i=0; i<_current._movingTrainers.size();i++){
					if(_current._movingTrainers.get(i).getCanMove()){
						_current._movingTrainers.get(i).NPCMove(_current._movingTrainers.get(i).getNextDir(), _current._movingTrainers.get(i).getWalkSpeed());
					}
				}
				
				if(tick==20){
					_current.inLineOfSight();
				}
			}*/
			
			tick++;
			if(tick == 11) tick = 0;
		}
	}
	
	public void startNPCTimer(){
		npcTimer.start();
	}
	
//	public Timer getNPCTimer(){
//		return npcTimer;
//	}
	
			
	public class NPCTimer implements ActionListener{
		public void actionPerformed(ActionEvent t){
			npctick++;

			
			if(_currentMoving != null)
				for(int i=0; i<_currentMoving.size(); i++){
					Trainer moving = _currentMoving.get(i);
					_current._thisTrainer=i;
					
					
					if(!moving.isInterrupted() && !moving.isStationary() && !moving.hasJustMoved() && moving.getCanMove()){
						if(npctick == 1)
							_current.moveNPCToTarget(moving.getAvoidMethod(),moving.getCurrentDestX(),moving.getCurrentDestY());	
						if(moving.getCanMove()){
							switch(moving.getNextDir()){
							case 0:
								moving.currentAction = Trainer.MOVE_UP;
								break;
							case 1:
								moving.currentAction = Trainer.MOVE_DOWN;
								break;
							case 2:
								moving.currentAction = Trainer.MOVE_RIGHT;
								break;
							case 3:
								moving.currentAction = Trainer.MOVE_LEFT;
								break;
							case 4:
								moving.currentAction = Trainer.FACE_UP;
								break;
							case 5:
								moving.currentAction = Trainer.FACE_DOWN;
								break;
							case 6:
								moving.currentAction = Trainer.FACE_RIGHT;
								break;
							case 7:
								moving.currentAction = Trainer.FACE_LEFT;
								break;
							default: moving.currentAction = moving.getDirection()+4;
							}
						
							
							moving.advance(npctick);
						}
					
					}
					else{
						if((moving.isInterrupted() || !moving.getCanMove()) && npctick == 20){
							switch(moving.getDirection()){
							case 0:
								moving.currentAction = Trainer.FACE_UP;
								break;
							case 1: 
								moving.currentAction = Trainer.FACE_DOWN;
								break;
							case 2:
								moving.currentAction = Trainer.FACE_RIGHT;
								break;
							case 3:
								moving.currentAction = Trainer.FACE_LEFT;
								break;
							default: moving.currentAction = Trainer.FACE_DOWN;
							}
						}

						if(moving.hasJustMoved()){
							moving.setMoving(false);
						}
					}
				}
			if(npctick == 20){
				npctick = 0;
				for(int i=0; i<_currentMoving.size(); i++){
					if(_currentMoving.get(i).hasReachedDest()){
						_currentMoving.get(i).prepareNextDest();
					}
					_currentMoving.get(i).setCanMove(true);
					if(!_currentMoving.get(i).hasJustMoved() && (_currentMoving.get(i).doesPause() || _currentMoving.get(i).isInterrupted())){
						_currentMoving.get(i).setJustMoved(true);
					}
					else{
						_currentMoving.get(i).setJustMoved(false);
					}
				}
				
				if(_nextMoving != null){
					//SysOut.print("SWITCHED");
					_currentMoving = _nextMoving;
					_nextMoving = null;
				}
			}
			
			//_current.repaint(Math.max(0,_current._xSpace), Math.max(0,_current._ySpace), 500, 500);
			//_current.repaint();
		}
	}
	
	
	public class NotifyTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ntick++;
			_current.repaint();
			
			if(ntick < 50){
				notifyX = notifyX-3;
			}
			else{
				if(ntick >= 50 && ntick < 100){
					notifyX = notifyX-1;
				}
				else{
					if(ntick >= 100 && ntick < 200){
						
					}
					else{
						if(ntick >= 200 && ntick < 250){
							notifyX = notifyX+1;
						}
						else{
							if(ntick >= 250 && ntick < 300){
								notifyX = notifyX+3;
							}
							else{
								notifyX = 385;
								ntick = 0;
								nTimer.stop();
							}
						}
					}
				}
			}
					
			//_this.repaint(Math.max(0,_current._xSpace), Math.max(0,_current._ySpace), 500, 500);
			//_this.repaint();
		}
	}
	
	public void checkMotion(){
	//	MotionCheck mc = new MotionCheck();
	//	Timer tmc = new Timer(1, mc);
	//	tmc.start();
	}

	@SuppressWarnings("unused")
	private class MotionCheck implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(_current != null && _current._walkTimer != null && !_current._walkTimer.isRunning() && _current._approachTimer.isRunning()){
				SysOut.print("WALK TIMER: " + _current.getTimeCount());
				SysOut.print("APPROACH: " + _this.npctick);
			}
		}
	}
	
	private class Repainter implements ActionListener{
		public void actionPerformed(ActionEvent e){
			rpTick++;
			//SysOut.print("RPTICK: " + rpTick);
			if(rpTick==10){
				rpTick=1;
				if(_current!= null)
					_current.repaint();
				//SysOut.print("Repainted");
			}
		}
	}
}