package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



@SuppressWarnings("serial")
public class PokePanel2 extends JPanel{
	
	protected boolean NORTH;
	protected boolean SOUTH;
	protected boolean EAST;
	protected boolean WEST;
	private static final char FREE='F', NO='N', TRAINER='T', GRASS='G', TRAINERONLY='O', CAVE='X', DOOR='D', SEAMLESS='S', SURF='U', CUT='C', VERTICAL='V', RIGHT_LEDGE='R', LEFT_LEDGE='L', ROPE='+', SLIDE_LEFT='l', SLIDE_RIGHT='r', SLIDE_UP='u', SLIDE_DOWN='d';
	//MAT. YOURE CRAZY. LOOK WHAT YOU MADE ME DO.
	protected final int MOVEUP=0, MOVEDOWN=1, MOVERIGHT=2, MOVELEFT=3;
	protected final int FACENORTH=0, FACEEAST=1, FACESOUTH=2, FACEWEST=3;
	
	private PokePanel2 _this;
	private boolean _roomJustEntered;
	protected GameBoyScreen _gbs;
	protected boolean _busy, completionCheck, _menuVisible = false, _bannerVisible, _myCoursesVisible, _pcVisible = false, /*stop = false,*/ _martMenuVisible = false, _itemSelectMenu=false;
	protected boolean _canBike, _outdoors;
	protected int _canSurf=0;
	private String _hmSlaveName;
	protected Room _room;
	protected Vector<Pokemon> _wildPokemon, _wildSurf, _wildFish;
	protected Timer _walkTimer, _attack;
	protected Timer _heal, _cutTimer ,_fadeToBlack, _fadeUp, psnTimer, _approachTimer, escapeTimerLeave, escapeTimerEnter;
	public boolean cutCan=false, cutBush=false;
	private int _timeCount;//, _NPCTimeCount, _approachedTimeCount;
	public int _bikeCycle=1;
//	protected int _darkness;
	protected int _xSpace, _ySpace, _xIndex, _yIndex, _roomNum;
	protected Rectangle2D.Double _blackBox;
	protected Rectangle2D.Double _whiteBox;
	private Rectangle2D.Double _notifyOutline, _notifyFill;
	private boolean psnTextVisible, hmTextVisible;
	protected Vector<Trainer> _movingTrainers = new Vector<Trainer>();
	protected Vector<Integer> _thisMartContains = new Vector<Integer>();
	protected Vector<Item> _fullMartVector = new Vector<Item>();
	public int _thisTrainer;
	protected boolean[] _textVisible;
	private Ellipse2D.Double _menuCursor, _pcCursor, _martCursor;

	private Item currentItem;
	private JPanel _currentMenu;
	protected Graphics2D g2;
	private int _SpeedTimer; //Bike vs. Walk.
	protected boolean[] _menuTextVisible;
	protected String description;
	private int psnStep = 0;
	private int _direction=2;
	protected int PROBABILITY = 8;
	public static final int CAVE_PROB = 16, WATER_PROB = 16;
	protected String s1 = "", s2 = "";
	protected Timer textTimer;
	private char[] cfirst,csecond;
	private CharListener cl;
	protected boolean wild = false, legendary=false;
	public static boolean repelling = false, repelVisible=false; 
	protected int trainer = 0;
	//For NPC movement.
	public int move1x, move1y;
	public int move2x, move2y;
	public int move3x, move3y;
	public int move4x, move4y;
	public boolean _upForbid=false;
	public boolean _downForbid=false;
	public boolean _leftForbid=false;
	public boolean _rightForbid=false;
	public int _NPCpage=0;
	public boolean _dialogueVisible, _receivedItem;
	public boolean blocked1, blocked2, blocked3, blocked4; 
	protected int xObs, yObs;
	//private Timer _movingNPC;
	protected int xConstant=0, yConstant=0;

	protected int _interruptedTrainer=0, _playerDestX=-1, _playerDestY=-1; 
	public int _LOSTrainer, _alertTick=0;
	public boolean _approached, _returnTrip, _followNPC, _oneShot, _seenByNPC, _paintAlert, _tooCloseWait, _tooCloseTrigger;
	//protected int npctick;

	protected int _battleBG = 1;
	
	protected boolean mapVisible;
	public BufferedImage mapImage, alertImage, flashImage;
	
	private UpTimer upTime;
	private DownTimer downTime;
	private LeftTimer leftTime;
	private RightTimer rightTime;
	
	private String receivedGiftName;
	
	private int yLedge = 0;
	private boolean ledge = false, prevLedge = false;
	private Ellipse2D.Double shadow = new Ellipse2D.Double();
	
	private boolean _gettingDark = true, _gettingBright = false;
	protected int _darkLevel = 0;
	public int _whiteLevel = 0;
	protected int _cutTick = 0, _digTick=0, _teleTick=0;
	public static int repelCount=0;
	protected int _mapX=0, _flyX=0, _pkmnCentX=-1, _caveX=0;
	protected int _mapY=0, _flyY=0, _pkmnCentY=0, _caveY=0, _caveEntranceNum=0; 
	
	public boolean buyMenu = false, sellMenu = false;
	
	protected Vector<Integer> _eventCheckList = new Vector<Integer>();

	public boolean _healed = false;
	
	private static boolean firstBattle = false;
	
	private JFrame errThanks;
	public BufferedImage _playerReflection =null;
	protected M2 song = null;
	//Flash
	public boolean _flashOn = false;
	protected boolean _darkRoom = false;
	
	private int psnVictim =-1;
	private boolean poisonVisible=false;
	
	private ImageIcon flyImage;
	private int flyImageY=160, flyImageX=167;
	private int flyDest=0;
	public boolean customTeleporting;
	public int customTeleportX=0, customTeleportY=0, customTeleportRoomNum=0;
	protected int grassType=0;
	public boolean mailboxOpen=false;
	public String mailNumber="";
	
	private boolean gymBattle = false, normalBattle = true;
	private int customType = 0;
	public final int MIKE = 1, DAVID = 2, SRI = 3, MAT = 4, RIVAL = 5, GLITCH = 6, GAMBINO = 7;
	
	
	private static BufferedImage gradRobes;
	protected int imgW=0, imgH=0;
	
	public void makeErrThanks(){
		errThanks = new JFrame("Sorry about that!");
//		errThanks.setPreferredSize(new Dimension(600,200));
//		JLabel text = new JLabel("Thank you for reporting this bug. A save file has been generated automatically so hopefully");
//		JLabel text2 = new JLabel(" you haven't lost any progress.\n"); 
//		JLabel text3 = new JLabel("Please email the generated error log (located in the 'Error Logs' folder) to PokemonBrownUniversity@gmail.com,\n");
//		JLabel text4 = new JLabel(" with the subject title 'Pokemon Brown Error'.");
//		JLabel text5 = new JLabel("Please also include your save file, and a description of how you caused the bug to appear.");
//		JLabel text6 = new JLabel("Thank you, and have a nice day.");
//		JPanel pan = new JPanel();
//		pan.setLayout(new GridLayout(6,1));
//		pan.add(text);
//		pan.add(text2);
//		pan.add(text3);
//		pan.add(text4);
//		pan.add(text5);
//		pan.add(text6);
//		errThanks.add(pan);
//		errThanks.setAlwaysOnTop(true);
//		
//		int screenXCenter = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-600)/2.0);
//		int screenYCenter = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight()-200)/2.0);
//		
//		errThanks.setLocation(screenXCenter,screenYCenter);
//		
//		errThanks.pack();
	}
	
	public PokePanel2(GameBoyScreen gbs){
		super();
		
		//System.out.println("Room #: " + _roomNum + ", with " + Runtime.getRuntime().freeMemory());
		
		this.setDoubleBuffered(true);
		
		gbs.incrementRooms();
		
		if(gradRobes == null){
			try{
				gradRobes = ImageIO.read(PokePanel2.class.getResource("/PokemonFiles/TrainerImages/F-Grad.png"));
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		
		_darkLevel = 240;
		_flashOn=false;
		_gbs = gbs;
		_this = this;
	//	this.npctick = 0;
		_busy = false;
		
		try{
			if(alertImage==null)
				alertImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/alert.png"));
			if(flashImage==null)
				flashImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/Flash.png"));
			if(flyImage==null)
				flyImage = new ImageIcon(this.getClass().getResource("/PokemonFiles/Map/fastFly.gif"));
			System.gc();
		//	mapImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/map - Copy.png"));
		}
		catch(IOException ioe){ioe.printStackTrace();}
		catch(Exception e){e.printStackTrace();}

		//FIXME: POSSIBLE PROBLEM
		upTime = new UpTimer();
		downTime = new DownTimer();
		leftTime = new LeftTimer();
		rightTime = new RightTimer();
		
		_blackBox = new Rectangle2D.Double();
		_blackBox.setFrame(-10,-10,400,550);
		
		_whiteBox = new Rectangle2D.Double();
		_whiteBox.setFrame(-10,-10,400,400);
		
		_menuCursor = new Ellipse2D.Double();
		_menuCursor.height = 5;
		_menuCursor.width = 5;
		_menuCursor.x = 290;
		_menuCursor.y = 23;
		
		_pcCursor = new Ellipse2D.Double();
		_pcCursor.height = 5;
		_pcCursor.width = 5;
		_pcCursor.x = 12;
		_pcCursor.y = 123;
		
		_martCursor = new Ellipse2D.Double();
		_martCursor.height = 5;
		_martCursor.width = 5;
		_martCursor.x = 15;
		_martCursor.y = 15;
		
		_notifyFill = new Rectangle2D.Double();
		_notifyFill.height = 25;
		_notifyFill.width = 175;
		_notifyFill.y = 50;
		_notifyFill.x = _gbs.notifyX;
		
		_notifyOutline = new Rectangle2D.Double();
		_notifyOutline.height = 22;
		_notifyOutline.width = 172;
		_notifyOutline.y = 51;
		_notifyOutline.x = _gbs.notifyX + 1;
	
		//=============================
		
		//=============================
		
		_timeCount = 0;
		
		//========
		_menuTextVisible = new boolean[2];
		//===========
		
		
		this.setLayout(new BorderLayout());
		
		setCurrentMenu(gbs.pbs);
		getCurrentMenu().setVisible(false);
		
		shadow.x = 170;
		shadow.y = 170;

		this.initializeAllTimers();
		
		this.makeErrThanks();
		
		this.repaint();
	}
	

	public PokePanel2(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super();
		
		this.setDoubleBuffered(true);
		
		_gbs = gbs;
		
		_this = this;
	//	this.npctick = 0;
		_busy = false;
		_timeCount = 0;
		
		_darkLevel = 0;
		
		try{
			alertImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/alert.png"));
			flashImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/Flash.png"));
			flyImage = new ImageIcon(this.getClass().getResource("/PokemonFiles/Map/fastFly.gif"));
		//	mapImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/map - Copy.png"));
		}
		catch(IOException ioe){ioe.printStackTrace();}
		catch(Exception e){e.printStackTrace();}
		
		_blackBox = new Rectangle2D.Double();
		_blackBox.setFrame(-10,-10,400,400);
		
		_whiteBox = new Rectangle2D.Double();
		_whiteBox.setFrame(-10,-10,400,400);
		
		_menuCursor = new Ellipse2D.Double();
		_menuCursor .height = 5;
		_menuCursor.width = 5;
		_menuCursor.x = 290;
		_menuCursor.y = 23;
		
		_pcCursor = new Ellipse2D.Double();
		_pcCursor.height = 5;
		_pcCursor.width = 5;
		_pcCursor.x = 12;
		_pcCursor.y = 123;
		
		_martCursor = new Ellipse2D.Double();
		_martCursor.height = 5;
		_martCursor.width = 5;
		_martCursor.x = 15;
		_martCursor.y = 15;
		
		
		
		_notifyFill = new Rectangle2D.Double();
		_notifyFill.height = 25;
		_notifyFill.width = 175;
		_notifyFill.y = 50;
		_notifyFill.x = _gbs.notifyX;
		
		_notifyOutline = new Rectangle2D.Double();
		_notifyOutline.height = 22;
		_notifyOutline.width = 172;
		_notifyOutline.y = 51;
		_notifyOutline.x = _gbs.notifyX + 1;

		//========
		_menuTextVisible = new boolean[2];
		//===========
		
		//Set variables to match parameters
		_xSpace = xSpace;
		_ySpace = ySpace;
		_xIndex = xInd;
		xObs = _xIndex;
		_yIndex = yInd;
		yObs = _yIndex;
		
		NORTH = SOUTH = WEST = EAST = false;
		
		if(direction == 0){
			setDirection(0);
		//	this.Up();
		}
		
		if(direction == 1){
			setDirection(1);
		//	this.Right();
		}
		
		if(direction == 2){
			setDirection(2);
		//	this.Down();
		}
		
		if(direction == 3){
			setDirection(4);
		//	this.Left();
		}
		
		setCurrentMenu(gbs.pbs);
		getCurrentMenu().setVisible(false);
		
		this.initializeAllTimers();
		
		this.makeErrThanks();
		
		this.repaint();
	}
	
	public int getMapX() {
		return _mapX;
	}
	public int getMapY() {
		return _mapY;
	}
	
	public int getFlyX() {
		return _flyX;
	}
	public int getFlyY() {
		return _flyY;
	}
	
	public static boolean firstBattle(){
		return firstBattle;
	}
	
	public static void setFirstBattle(boolean b){
		firstBattle = b;
	}
	
	public Vector<Integer> getCheckList(){
		////SysOut.print("POKEPANEL");
		////SysOut.print("Room number "+ this._roomNum+ "," +this._eventCheckList.size());
		return this._eventCheckList;
	}
	
	public void setCheckList(Vector<Integer> b){
		////this._eventCheckList.clear();
		int minSize=0;
		if(b.size()<this._eventCheckList.size()){
			minSize=b.size();
		}
		else{
			minSize=this._eventCheckList.size();
		}
		
		for(int i=0;i<minSize;i++){
			this._eventCheckList.set(i, b.get(i));	
		}
		//FIXME changed .add() to .set()
	}
	
	public void completeEvent (int eventNum){
		if(eventNum < this.getCheckList().size()){
			this.getCheckList().set(eventNum, 1);
		}
	}
	
	public void setFlash(boolean b){
		_flashOn=b;
	}
	
	public boolean isDarkRoom(){
		return _darkRoom;
	}
	
	public int getTimeCount(){
		return _timeCount;
	}
	public int getBikeCycle(){
		return _bikeCycle;
	}
	
	public Vector<Item> getMartVector(){
		return this._fullMartVector;
	}
	
	public void generateMart(){
		this._fullMartVector.clear();
			
		try{
			this._fullMartVector.add(new Item.Potion());		//0
			this._fullMartVector.add(new Item.OrganicPotion()); //1
			this._fullMartVector.add(new Item.SuperPotion());	//2
			
			this._fullMartVector.add(new Item.HyperPotion()); 	//3
			this._fullMartVector.add(new Item.MaxPotion());	//4
			this._fullMartVector.add(new Item.BubbleTea());	//5
			this._fullMartVector.add(new Item.FreshWater());	//6
			this._fullMartVector.add(new Item.SodaPop());		//7
			this._fullMartVector.add(new Item.FullRestore());	//8
			
			this._fullMartVector.add(new Item.Elixir());		//9
			this._fullMartVector.add(new Item.Ether());		//10
			this._fullMartVector.add(new Item.MaxElixir());	//11
			this._fullMartVector.add(new Item.MaxEther());		//12
			
			this._fullMartVector.add(new Item.Revive());		//13
			this._fullMartVector.add(new Item.MaxRevive());	//14

			this._fullMartVector.add(new Item.XAccuracy());	//15
			this._fullMartVector.add(new Item.XAttack());		//16
			this._fullMartVector.add(new Item.XDefend());		//17
			this._fullMartVector.add(new Item.XSpecial());		//18
			this._fullMartVector.add(new Item.XSpeed());		//19
			this._fullMartVector.add(new Item.DireHit());		//20
			this._fullMartVector.add(new Item.GuardSpec());	//21
			
			this._fullMartVector.add(new Item.PokeBall());		//22
			this._fullMartVector.add(new Item.FairTradePokeBall()); //23		
			this._fullMartVector.add(new Item.GreatBall());	//24
			this._fullMartVector.add(new Item.UltraBall());	//25
			this._fullMartVector.add(new Item.MasterBall());	//26
		
			this._fullMartVector.add(new Item.Awakening());	//27
			this._fullMartVector.add(new Item.Antidote());		//28
			this._fullMartVector.add(new Item.HolisticAntidote()); //29
			this._fullMartVector.add(new Item.ParalyzHeal());	//30
			this._fullMartVector.add(new Item.HolisticParalyzHeal()); //31
			this._fullMartVector.add(new Item.BurnHeal());		//32
			this._fullMartVector.add(new Item.IceHeal());		//33
			this._fullMartVector.add(new Item.FullHeal());		//34
			
			this._fullMartVector.add(new Item.Bicycle());		//35
			this._fullMartVector.add(new Item.SpringWeekendTicket()); //36
			this._fullMartVector.add(new Item.SpecialPackage());//37
			this._fullMartVector.add(new Item.EXPAll());		//38
			
			this._fullMartVector.add(new Item.Calcium());		//39
			this._fullMartVector.add(new Item.Carbos());		//40
			this._fullMartVector.add(new Item.Iron());			//41
			this._fullMartVector.add(new Item.Protein());		//42
			this._fullMartVector.add(new Item.Zinc());			//43
			this._fullMartVector.add(new Item.HPUp());			//44
			this._fullMartVector.add(new Item.PPUp());			//45
			this._fullMartVector.add(new Item.RareCandy());	//46
			this._fullMartVector.add(new Item.EscapeRope());	//47
			this._fullMartVector.add(new Item.Repel());		//48
			this._fullMartVector.add(new Item.SuperRepel());	//49
			this._fullMartVector.add(new Item.MaxRepel());		//50
			this._fullMartVector.add(new Item.Nugget());		//51
			
			this._fullMartVector.add(new Item.MoonStone());	//52
			this._fullMartVector.add(new Item.FireStone());	//53
			this._fullMartVector.add(new Item.LeafStone());	//54
			this._fullMartVector.add(new Item.WaterStone());	//55
			this._fullMartVector.add(new Item.Thunderstone());	//56
			this._fullMartVector.add(new Item.FakeID());//57  HAS A FINAL INT.
			this._fullMartVector.add(new Item.TM06_Toxic());	//58
			this._fullMartVector.add(new Item.TM14_Blizzard());//59
			this._fullMartVector.add(new Item.TM22_Solar_Beam());//60
			this._fullMartVector.add(new Item.TM46_Psywave());//61
			this._fullMartVector.add(new Item.HM01_Cut());//62
			this._fullMartVector.add(new Item.HM02_Fly());//63
			this._fullMartVector.add(new Item.HM03_Surf());//64
			this._fullMartVector.add(new Item.HM04_Strength());//65
			this._fullMartVector.add(new Item.HM05_Flash());//66
			
			this._fullMartVector.add(new Item.Dome_Fossil()); //67
			this._fullMartVector.add(new Item.Helix_Fossil()); //68
			this._fullMartVector.add(new Item.Old_Amber()); //69
			
			this._fullMartVector.add(new Item.TM04_Whirlwind()); //70
			this._fullMartVector.add(new Item.TM01_MegaPunch());//71
			this._fullMartVector.add(new Item.TM12_WaterGun()); //72
			
			this._fullMartVector.add(new Item.VodkaFifth()); //73
			this._fullMartVector.add(new Item.Kahlua()); //74
			this._fullMartVector.add(new Item.TonicWater()); //75
			this._fullMartVector.add(new Item.JagerBomb()); //76
			this._fullMartVector.add(new Item.CremeDeMenthe()); //77
			this._fullMartVector.add(new Item.Moonshine()); //78
			this._fullMartVector.add(new Item.SmirnoffIce()); //79
			this._fullMartVector.add(new Item.TM24_Thunderbolt()); //80
			this._fullMartVector.add(new Item.Apple()); //81
			this._fullMartVector.add(new Item.TM28_Dig()); //82
			this._fullMartVector.add(new Item.TM45_ThunderWave()); //83
			this._fullMartVector.add(new Item.PokeFlute()); //84
			this._fullMartVector.add(new Item.TM19_Seismic_Toss()); //85
			this._fullMartVector.add(new Item.TM44_Rest()); //86
			this._fullMartVector.add(new Item.TM08_Body_Slam()); //87
			this._fullMartVector.add(new Item.TM38_Fireblast()); //88
			this._fullMartVector.add(new Item.Donut()); //89
			this._fullMartVector.add(new Item.RISD_ID()); //90
			this._fullMartVector.add(new Item.TM42_DreamEater()); //91
			this._fullMartVector.add(new Item.TM43_SkyAttack()); //92
			this._fullMartVector.add(new Item.KitchenKey()); //93
			this._fullMartVector.add(new Item.Zipcar_Card()); //94
			this._fullMartVector.add(new Item.Ladder()); //95
			this._fullMartVector.add(new Item.TM07_HornDrill());//96
			this._fullMartVector.add(new Item.TM10_DoubleEdge());//97
			this._fullMartVector.add(new Item.TM09_Take_Down());//98
			this._fullMartVector.add(new Item.SuggsPotion());//99
			this._fullMartVector.add(new Item.TM20_Rage());//100
			this._fullMartVector.add(new Item.StuffedDino());//101
			this._fullMartVector.add(new Item.TM21_MegaDrain()); //102
			this._fullMartVector.add(new Item.TM37_EggBomb()); //103
			this._fullMartVector.add(new Item.TM32_DoubleTeam()); //104
			this._fullMartVector.add(new Item.TM40_SkullBash()); //105
			
			this._fullMartVector.add(new Item.TM30_Teleport()); //106
			this._fullMartVector.add(new Item.TM23_Dragon_Rage()); //107
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		
		for(int i = 0; i < _thisMartContains.size(); i++){
			_fullMartVector.get(_thisMartContains.get(i)).setRemaining(1);
		}
		
		
	}
	
	public void destroyMart(){
		_fullMartVector.clear();
	}
	
	
	public void setEvolutionScreenVisible(boolean b){
		if(b){
			this.setCurrentMenu(_gbs.ev);
			this.add(getCurrentMenu(), BorderLayout.CENTER);
			getCurrentMenu().setVisible(true);
			_gbs.ev.setPokemonToEvolve(_gbs.getBattlePanel().getEvolutionVector());
		}
		else{
			getCurrentMenu().setVisible(false);
		}
	}
	public void initializeAllTimers(){
		int timeC = 32;
		FadeToBlack black = new FadeToBlack();
		_fadeToBlack = new Timer (timeC, black);
		
		FadeUpTimer up = new FadeUpTimer();
		_fadeUp = new Timer(timeC, up);
		
		psnTimer = new Timer(5, black);
		
		AttackTimer att = new AttackTimer();
		_attack = new Timer(timeC, att);
		
		ApproachTimer AppT = new ApproachTimer();
		_approachTimer = new Timer(20, AppT);
		
		HealTimer heal = new HealTimer();
		_heal = new Timer(timeC*4, heal);
		
		CutTimer cut = new CutTimer();
		_cutTimer = new Timer(5, cut);
		
		EscapeTimerLeave escapeT = new EscapeTimerLeave();
		escapeTimerLeave = new Timer(5, escapeT);
		
		EscapeTimerEnter escapeL = new EscapeTimerEnter();
		escapeTimerEnter = new Timer(5, escapeL);
		
		cl = new CharListener();
		textTimer = new Timer(50, cl);
	}
	
	public PlayerInfoScreen getPIS(){
		return _gbs.pis;
	}
	
	public JPanel getCurrentMenu() {
		return _currentMenu;
	}

	public void setCurrentMenu(JPanel _currentMenu) {
		this._currentMenu = _currentMenu;
	}

	public void setMenuVisible(boolean b){
		_menuVisible=b;
	}
	
	public int getFlyDest(){
		return flyDest;
	}
	
	public void setFlyDest(int dest){
		flyDest=dest;
	}
	
	public boolean canBikeHere(){
		return _canBike;
	}
	
	public boolean isOutdoors(){
		return _outdoors;
	}
	
	//: Eventually, generalize this to "player has HM"
	public void playerHasHM(String hmName){
		Player p = this._gbs.getPlayer();
		Pokemon subject;
		//Return on first find. Doesn't matter who it is.
		for (int i=0; i<p.getAllActive().size();i++){
			subject= p.getActivePokemon(i);
			for (int j=0; j<subject.getAttacks().size();j++){
				if(hmName=="Surf"){
					if(subject.getAttacks().get(j).getName()==hmName){
						_hmSlaveName=subject.getName();
						_canSurf=1;
						return;
					}
				}
			}
		}
		
		if(hmName=="Surf"){
		//Otherwise, it's false, which equates to negative values here.;
		_canSurf=-1;
		}
		
	}
	
	public void setBikeAllow(boolean b){
		_canBike=b;
	}
	

//	public void enterRoom(int xInd, int yInd, int roomnum){
//		_gbs.setCurrentPanel(roomnum);
//		PokePanel2 current = _gbs.getCurrentPanel();
//		current.createBaseRoom();
//		current._darkness = 250;
//		current._fadeUp.start();
//		
//		current.resetNTimer();
//		_gbs.startNTimer();
//		
//	
//	}
	
	public void enterRoom(){
		_darkLevel = 240;
		_fadeUp.start();
		
		resetNTimer();
		_gbs.startNTimer();
	}
	
	public void enterRoom(int xInd, int yInd){
		
	}

	public void enterRoomSeamless(int xInd, int yInd){
		
	}
	
	public void enterRoom(int roomDest, int newXIndex, int newYIndex, int direction){
		this.enterRoom(roomDest, newXIndex, newYIndex, direction, true);
	}
	
	public void enterRoom(int roomDest, int newXIndex, int newYIndex, int direction, boolean doorSoundOn){
		_gbs.getCurrentPanel().setMartVisible(false);
		_gbs.getCurrentPanel().setPCVisible(false);
		
		//Reset Flash if not dark-dark entrance.
		if(_flashOn && _gbs.getCurrentPanel()._darkRoom && _gbs.getPanel(roomDest)._darkRoom){
			_gbs.getPanel(roomDest)._flashOn=true;
		}
		else{
			_gbs.getPanel(roomDest)._flashOn=false;
		}
		String prevDescript = _gbs.getCurrentPanel().description;
		_gbs.setCurrentPanel(roomDest);

		PokePanel2 current = _gbs.getCurrentPanel();

		if(doorSoundOn){
			M2.playFXNoPause(M2.DOOR);
		}
		
		_roomJustEntered=true;	
		_gbs.getPlayer().setIgnoring(false);
		_gbs.getPlayer().clearDestinations();
		if(_gbs.BACKGROUND != null)
			_gbs.BACKGROUND.flush();	
		current.createBaseRoom();
		
		if(!_gbs.getMode()){
			SysOut.print("Next Room: " + current.getSong().getFileName());
			SysOut.print("Currently Playing: " + M2.getCurrentSong().getFileName());
			if(current.getSong() != M2.getCurrentSong())
				M2.playBG(current.getSong());
		}
		
		current.setIndices(newXIndex, newYIndex);
		current.setLocation(-current.xConstant-(20*current._xIndex)+176, -current.yConstant-(20*current._yIndex)+168);
		current._darkLevel = 240;
		current._fadeUp.start();
		current.resetNTimer();
		if(prevDescript != current.description)
			_gbs.startNTimer();
		
		switch(direction){
		case FACESOUTH:
			current.Down(); current.Down(); break;
		case FACENORTH:
			current.Up(); current.Up(); break;
		case FACEEAST:
			current.Right(); current.Right(); break;
		case FACEWEST:
			current.Left(); current.Left(); break;
		default:
			current.Down(); current.Down(); break;
		}
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		//TODO: MUZAK
	}
	
	
	public void stationaryEnterRoom(int roomDest, int newXIndex, int newYIndex, int direction){
		_gbs.getCurrentPanel().setMartVisible(false);
		_gbs.getCurrentPanel().setPCVisible(false);
		String prevDescript = _gbs.getCurrentPanel().description;
		_gbs.setCurrentPanel(roomDest);
		
		
		PokePanel2 current = _gbs.getCurrentPanel();
		_gbs.setCurrentPanel(roomDest);
		System.out.println("Pre " + Runtime.getRuntime().freeMemory());
		if(_gbs.BACKGROUND != null)
			_gbs.BACKGROUND.flush();
		current.createBaseRoom();
		current.setIndices(newXIndex, newYIndex);
		current.setLocation(-current.xConstant-(20*current._xIndex)+176, -current.yConstant-(20*current._yIndex)+168);
		current._darkLevel=0;
		
		
		switch(direction){
		case FACESOUTH:
			current.Down(); 
			_gbs.getPlayer().faceDown();
			break;
			
		case FACENORTH:
			current.Up();
			_gbs.getPlayer().faceUp();
			break;
		case FACEEAST:
			current.Right();
			_gbs.getPlayer().faceRight();
			break;
		case FACEWEST:
			current.Left();  
			_gbs.getPlayer().faceLeft();
			break;
		default:
			current.Down();
			_gbs.getPlayer().faceDown();
			break;
		}

		current.resetNTimer();
		if(prevDescript != current.description)
			_gbs.startNTimer();
		
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		if(!_gbs.getMode()){
			SysOut.print("Next Room: " + current.getSong().getFileName());
			SysOut.print("Currently Playing: " + M2.getCurrentSong().getFileName());
			if(current.getSong() != M2.getCurrentSong())
				M2.playBG(current.getSong());
		}

		
		//TODO: MUZAK
	}
	
	public void enterRoom(int roomNum){
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
	}
	
	public void roomEntered(){

	}
	
	public void enterRoomSeamless(int roomDest, int newXIndex, int newYIndex, int direction){
		_gbs.getCurrentPanel().setMartVisible(false);
		_gbs.getCurrentPanel().setPCVisible(false);
		
		//Reset Flash if not dark-dark entrance.
		if(_flashOn && _gbs.getCurrentPanel()._darkRoom && _gbs.getPanel(roomDest)._darkRoom){
			this._flashOn=true;
		}
		else{
			this._flashOn=false;
		}
		
		_gbs.setCurrentPanel(roomDest);
		PokePanel2 current = _gbs.getCurrentPanel();
		System.out.println("Pre " + Runtime.getRuntime().freeMemory());
		if(_gbs.BACKGROUND != null)
			_gbs.BACKGROUND.flush();
		current.createBaseRoom();
		
		if(!_gbs.getMode()){
			if(current.getSong() != M2.getCurrentSong())
				M2.playBG(current.getSong());
		}
		
		current._darkLevel = 0;
		
		current.setIndices(newXIndex, newYIndex);
		current.setLocation(-current.xConstant-(20*current._xIndex)+176, -current.yConstant-(20*current._yIndex)+168);
		current.resetNTimer();
		_gbs.startNTimer();
		
		switch(direction){
		case FACESOUTH:
			current.Down(); current.Down(); break;
		case FACENORTH:
			current.Up(); current.Up(); break;
		case FACEEAST:
			current.Right(); current.Right(); break;
		case FACEWEST:
			current.Left(); current.Left(); break;
		default:
			current.Down(); current.Down(); break;
		}
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
	}
	

	public void createBaseRoom(){
		
	}

	public void createGrid(){
		
	}
	
	public void toggleMapVisible(){
		if(_gbs.map.isVisible()){
			_gbs.map.setVisible(false);
			mapVisible = false;
			getCurrentMenu().setVisible(false);
			
			if(this.facingNPC() && this.getMovingTrainers()!=null && this.getMovingTrainers().get(_interruptedTrainer)!=null && this.getMovingTrainers().get(_interruptedTrainer).getName()=="Map"){
				_menuVisible=false;
			}

			_busy = false;
		}
	}
	
	public void setMartVisible(boolean b){
		this._martMenuVisible=b;
	}
	
	public void setPCVisible(boolean b){
		this._pcVisible=b;
	}

	public boolean facingWater(){
		try{ //If you are facing the edge of a room and hit A-button
			if(this._room!=null){
				if (SOUTH && this._room._roomGrid[_xIndex][_yIndex + 1] == SURF){
					return true;}
				if (NORTH && this._room._roomGrid[_xIndex][_yIndex - 1] == SURF){
					return true;}
				if (EAST && this._room._roomGrid[_xIndex + 1][_yIndex] == SURF){
					return true;}
				if (WEST && this._room._roomGrid[_xIndex - 1][_yIndex] == SURF){
					return true;}
			}
		}
		catch(ArrayIndexOutOfBoundsException aioobe){}
		return false;	
	}
	
	public boolean facingNO(){
		try{ //If you are facing the edge of a room and hit A-button
			if(this._room!=null){
				if (SOUTH && this._room._roomGrid[_xIndex][_yIndex + 1] == NO){
					return true;}
				if (NORTH && this._room._roomGrid[_xIndex][_yIndex - 1] == NO){
					return true;}
				if (EAST && this._room._roomGrid[_xIndex + 1][_yIndex] == NO){
					return true;}
				if (WEST && this._room._roomGrid[_xIndex - 1][_yIndex] == NO){
					return true;}
			}
		}
		catch(ArrayIndexOutOfBoundsException aioobe){}
		return false;	
	}
	
	
	public boolean facingNPC(){
		int xNPC, yNPC;
		for(int i=0; i<this._movingTrainers.size();i++){
			Trainer moving=this._movingTrainers.get(i);
			xNPC=moving.getXIndex();
			yNPC=moving.getYIndex();
			////SysOut.print("XIn: "+ xNPC + " YIn: "+ yNPC);
			
			if(moving.getName().length()>0 && moving.getName().charAt(0) == '#' && _gbs.getPlayer().getAllActive().size() == 1){
				////SysOut.print("Facing Starter Pkmn.");
				return false;
			}
			
			//RETURN FALSE TO PREVENT GLIDING WHILE HOLDING A_BUTTON()
			if (SOUTH && _xIndex==xNPC && _yIndex+1==yNPC && (!moving.isMoving()||(moving.isMoving() && !moving.doesPause())) && !(moving.getVanishing() && moving.isDefeated())){
				////SysOut.print("Facing.");

				this._interruptedTrainer=i;
				//_movingTrainers.get(i).setStationary(true);
				return true;
			}
			if (NORTH && _xIndex==xNPC && _yIndex-1==yNPC && (!moving.isMoving()||(moving.isMoving() && !moving.doesPause())) && !(moving.getVanishing() && moving.isDefeated())){
				////SysOut.print("Facing.");
				this._interruptedTrainer=i;
				//_movingTrainers.get(i).setStationary(true);
				return true;
			}
			if (WEST && _xIndex-1==xNPC && _yIndex==yNPC && (!moving.isMoving()||(moving.isMoving() && !moving.doesPause())) && !(moving.getVanishing() && moving.isDefeated())){
				////SysOut.print("Facing.");
				this._interruptedTrainer=i;
				//_movingTrainers.get(i).setStationary(true);
				return true;
			}
			if (EAST && _xIndex+1==xNPC && _yIndex==yNPC && (!moving.isMoving()||(moving.isMoving() && !moving.doesPause())) && !(moving.getVanishing() && moving.isDefeated())){
				////SysOut.print("Facing.");
				this._interruptedTrainer=i;
				//_movingTrainers.get(i).setStationary(true);
				return true;
			}
		}
		SysOut.print("NOPE.");
		return false;	
	}
	
	public boolean inLineOfSight(){
		//For all trainers
		//boolean isSeen=false;
		
		boolean keepGoing=true;
		
		for (int i=0; i<this._movingTrainers.size(); i++){
			this._LOSTrainer=i;
			keepGoing=true;
			//within their line of sight
			if(this._movingTrainers.get(i)!=null && !this._movingTrainers.get(i).isDefeated()){
				for (int j=0; j<this._movingTrainers.get(i).getLOS(); j++){
					if(keepGoing){
						int tx=this._movingTrainers.get(i).getXIndex();
						int ty=this._movingTrainers.get(i).getYIndex();
						int jx=0;
						int jy=0;
						
						//facing the right way
						switch(this._movingTrainers.get(i).getDirection()){
							case 0:
								jx=0;
								jy=1*j;
								break;
							case 2:
								jx=0;
								jy=-1*j;
								break;
							case 3:
								jx=1*j;
								jy=0;
								break;
							case 1:
								jx=-1*j;
								jy=0;
								break;
						}
						//What's in the way? if nothing and they see the player, return true.
						if((tx+jx)>=0 && (ty+jy)>=0){
							char next= this._room._roomGrid[tx + jx][ty + jy];
							if(tx+jx==xObs && ty+jy==yObs){
								this._interruptedTrainer=this._LOSTrainer;
								Trainer interrupted = this._movingTrainers.get(this._interruptedTrainer);
								//_busy=true;
								
								//These two statements are for moving trainers who can interrupt. 
								if(!interrupted.isStationary() && _gbs.npctick!=0){
									return false;
								}
								
								while(!interrupted.isStationary() && !this._approachTimer.isRunning()){
									interrupted.setAvoidMethod("freeze");
									interrupted.setStationary(true);
									SysOut.print("Frozen");
								}
								////////////////
								
								
								if(interrupted.getXIndex()>this.xObs){
									interrupted.clearDestinations();
									if(xObs+1==interrupted.getXIndex() && _tooCloseTrigger){
										_tooCloseWait=true;
										_tooCloseTrigger=false;
									}
									else{
										_tooCloseTrigger=false;
									}
									interrupted.addDest(xObs+1, yObs);
									interrupted.setFirstDest();
									interrupted.setPause(false);
	
								}
								else if (interrupted.getXIndex()<this.xObs){
									interrupted.clearDestinations();
									if(xObs-1==interrupted.getXIndex() && _tooCloseTrigger){
										_tooCloseWait=true;
										_tooCloseTrigger=false;
									}
									else{
										_tooCloseTrigger=false;
									}
									interrupted.addDest(xObs-1, yObs);
									interrupted.setFirstDest();
									interrupted.setPause(false);
								}
								else if (interrupted.getYIndex()>this.yObs){
									interrupted.clearDestinations();
									if(yObs+1==interrupted.getYIndex() && _tooCloseTrigger){
										_tooCloseWait=true;
										_tooCloseTrigger=false;
									}
									else{
										_tooCloseTrigger=false;
									}
									interrupted.addDest(xObs, yObs+1);
									interrupted.setFirstDest();
									interrupted.setPause(false);
								}
								else if (interrupted.getYIndex()<this.yObs){
									interrupted.clearDestinations();
									if(yObs-1==interrupted.getYIndex() && _tooCloseTrigger){
										_tooCloseWait=true;
										_tooCloseTrigger=false;
									}
									else{
										_tooCloseTrigger=false;
									}
									interrupted.addDest(xObs, yObs-1);
									interrupted.setFirstDest();
									interrupted.setPause(false);
								}
								
								////SysOut.print("HE SEES YOU WHEN YOU'RE SLEEPING: "+ _interruptedTrainer);
								//End the loop, return true.
								if(interrupted.getLOS()==0 || !interrupted.isStationary()){
									this._paintAlert=false;
								}
								else if (interrupted.getLOS()>0){
									this._paintAlert=true;
								}
								
								
								
	
								if(!this._approachTimer.isRunning()){
									this._seenByNPC=true;
									if(interrupted.getGender() == 'M' && interrupted.getBelt()!=null)
										M2.playBG(M2.TRAINER_APPROACH_M);
									else if(interrupted.getGender() == 'F'&& interrupted.getBelt()!=null)
										M2.playBG(M2.TRAINER_APPROACH_F);
									else if(interrupted.getGender() == 'R'&& interrupted.getBelt()!=null)
										M2.playBG(M2.RIVAL_MUSIC);
									this._approachTimer.start();
									this._tooCloseTrigger=true;
								}
								
								return true;
							}
							else if((next==SURF && !_gbs.getSurf() && !this._movingTrainers.get(this._LOSTrainer).canSurf())|| (next==SURF && _gbs.getSurf() && !this._movingTrainers.get(this._LOSTrainer).canSurf()) || (next==SURF && !_gbs.getSurf() && this._movingTrainers.get(this._LOSTrainer).canSurf()) || (next==NO && j!=0) || next==CUT){
								////SysOut.print("Something is in the way: "+ _interruptedTrainer);
								//Stop this iteration, move on.
								keepGoing = false;
							}
							
							for (int k=0; k<this._movingTrainers.size(); k++){
								if(tx+jx==this._movingTrainers.get(k).getXIndex() && ty+jy==this._movingTrainers.get(k).getYIndex() && j>0){
									////SysOut.print("Another trainer is in the way:" + _interruptedTrainer);
									//Stop this iteration, move on.
									if(this._movingTrainers.get(k).isDefeated() && this._movingTrainers.get(k).getVanishing()){
										
									}
									else{
										keepGoing = false;
									}
									
								}
							}
						}
					}
				}
			}
		}
		////SysOut.print("They don't see you.");
		return false;
	}
	
	public void setPlayerDest(int tx, int ty){
		_playerDestX=tx;
		_playerDestY=ty;
	} 
	
	public int getXObstacle(){
		return xObs;
	}
	
	public int getYObstacle(){
		return yObs;
	}
	public boolean hasPlayerReachedDest(){
		if(_gbs.getPlayer()._destVectorX.size()>0 && _gbs.getPlayer()._destVectorY.size()>0){
			if(_xIndex==_gbs.getPlayer()._destVectorX.get(0) && _yIndex==_gbs.getPlayer()._destVectorY.get(0)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
	public void movePlayerToTarget(String method, int tx, int ty, boolean ignore){
		String who="Player";
		//Find distance to target.
		int distx= tx - _xIndex;
		int disty= ty - _yIndex;
		
		//Squared distance.
		if (distx*distx+disty*disty!=0){
			//Find angle to target.
			double angle = (Math.atan2(disty,-distx)/Math.PI*180);
			if (angle<0){angle=angle+360;}
			
			this.getMoveRank(angle);
			
			//If you want them to ignore obstacles
			if (!ignore){ 
				this.checkObstacles(who);
			}
			
			this.makeBestMove(method, angle, who);
		}
	}
	
	public void moveNPCToTarget(String method, int tx, int ty){
		String who="Trainer";
		//Find distance to target.
		if(this._movingTrainers.size() > _thisTrainer){
			int distx= tx - this._movingTrainers.get(_thisTrainer).getXIndex();
			int disty= ty - this._movingTrainers.get(_thisTrainer).getYIndex();
			this._movingTrainers.get(_thisTrainer).setCanMove(true);
		
		//	Squared distance.
			if (distx*distx+disty*disty!=0){
			//	Find angle to target.
				double angle = (Math.atan2(disty,-distx)/Math.PI*180);
				if (angle<0){angle=angle+360;}
			
				this.getMoveRank(angle);
				this.checkObstacles(who);
				this.makeBestMove(method, angle, who);
			}
			else{
				switch(this._movingTrainers.get(_thisTrainer).getDirection()){
					case 0:
						this._movingTrainers.get(_thisTrainer).setNextDir(5);
						break;
					case 1:
						this._movingTrainers.get(_thisTrainer).setNextDir(7);
						break;
					case 2: 
						this._movingTrainers.get(_thisTrainer).setNextDir(4);
						break;
					case 3:
						this._movingTrainers.get(_thisTrainer).setNextDir(6);
						break;
				}
			}
		}
	}
	
	public void getMoveRank(double angle){
		 if (angle>=0 && angle<=45){
	        move1x=-1; move1y=0;
	        move2x=0; move2y=1;
	        move3x=0; move3y=-1;
	        move4x=1; move4y=0;
	     }
	    else if (angle>45 && angle<=2*45){
	        move1x=0; move1y=1;
	        move2x=-1; move2y=0;
	        move3x=1; move3y=0;
	        move4x=0; move4y=-1;
	    }
	    else if (angle>2*45 && angle<=3*45){
	    	move1x=0; move1y=1;
	        move2x=1; move2y=0;
	        move3x=-1; move3y=0;
	        move4x=0; move4y=-1;
	    }
	    else if (angle>3*45 && angle<=4*45){
	    	move1x=1; move1y=0;
	        move2x=0; move2y=1;
	        move3x=0; move3y=-1;
	        move4x=-1; move4y=0;
	    }
	    else if (angle>4*45 && angle<=5*45){
	        move1x=1; move1y=0;
	        move2x=0; move2y=-1;
	        move3x=0; move3y=1;
	        move4x=-1; move4y=0;
	    }
	    else if (angle>5*45 && angle<=6*45){
	        move1x=0; move1y=-1;
	        move2x=1; move2y=0;
	        move3x=-1; move3y=0;
	        move4x=0; move4y=1;
	    }
	    else if (angle>6*45 && angle<=7*45){
	        move1x=0; move1y=-1;
	        move2x=-1; move2y=0;
	        move3x=1; move3y=0;
	        move4x=0; move4y=1;
	    }
	    else if (angle>7*45 && angle<=360){
	        move1x=-1; move1y=0;
	        move2x=0; move2y=-1;
	        move3x=0; move3y=1;
	        move4x=1; move4y=0;
	    }
	    
	}
	
	
	public void checkObstacles(String who){
		blocked1=false;
		blocked2=false;
		blocked3=false;
		blocked4=false;		
		
		if (who == "Trainer"){
			int xIndex=this._movingTrainers.get(_thisTrainer).getXIndex();
			int yIndex=this._movingTrainers.get(_thisTrainer).getYIndex();
						
			//If within the bounds, but facing obstacle, set true. If out of bounds, set blocked true as well.
			if(xIndex + move1x<this._room.getX() && yIndex + move1y<this._room.getY() && xIndex + move1x>=0 && yIndex + move1y>=0){
				  char next1=this._room._roomGrid[xIndex + move1x][yIndex + move1y];
				  if(next1== NO || next1 == TRAINER || next1 == CUT || next1==VERTICAL || next1 == RIGHT_LEDGE || next1 == LEFT_LEDGE ||((next1==DOOR || next1== SEAMLESS) && !this._approachTimer.isRunning())|| (next1== SURF && !this._movingTrainers.get(_thisTrainer).canSurf()) || ((next1== FREE || next1 == GRASS) && this._movingTrainers.get(_thisTrainer).canSurf()) || (xIndex + move1x==this.xObs && yIndex + move1y==this.yObs)){
			          blocked1=true;
			      }
				  for (int j =0; j<this._movingTrainers.size();j++){
						if(xIndex + move1x == this._movingTrainers.get(j).getXIndex() && yIndex + move1y== this._movingTrainers.get(j).getYIndex()){
							if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
							else{
								blocked1=true;
							}
						}
				  }
			}
			else{
				blocked1=true;
			}
			
			if(xIndex + move2x<this._room.getX() && yIndex + move2y<this._room.getY() && xIndex + move2x>=0 && yIndex + move2y>=0){
				  char next2=this._room._roomGrid[xIndex + move2x][yIndex + move2y];
				  if(next2== NO || next2==TRAINER || next2 == CUT || next2==VERTICAL || next2 == RIGHT_LEDGE || next2 == LEFT_LEDGE|| ((next2==DOOR || next2== SEAMLESS) && !this._approachTimer.isRunning())|| next2==SURF && !this._movingTrainers.get(_thisTrainer).canSurf()|| ((next2== FREE || next2 == GRASS) && this._movingTrainers.get(_thisTrainer).canSurf())|| (xIndex + move2x==this.xObs && yIndex + move2y==this.yObs)){
			          blocked2=true;
			      }
				  for (int j =0; j<this._movingTrainers.size();j++){
						if(xIndex + move2x == this._movingTrainers.get(j).getXIndex() && yIndex + move2y== this._movingTrainers.get(j).getYIndex()){
							if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
							else{
							blocked2=true;
							}
						}
				  }
			}
			else{
				blocked2=true;
			}
			
			
			if(xIndex + move3x<this._room.getX() && yIndex + move3y<this._room.getY() && xIndex + move3x>=0 && yIndex + move3y>=0){
				  char next3=this._room._roomGrid[xIndex + move3x][yIndex + move3y];
				  if(next3==NO || next3==TRAINER || next3 == CUT || next3==VERTICAL || next3 == RIGHT_LEDGE || next3 == LEFT_LEDGE|| ((next3==DOOR || next3== SEAMLESS) && !this._approachTimer.isRunning())|| next3==SURF && !this._movingTrainers.get(_thisTrainer).canSurf() || ((next3== FREE || next3 == GRASS) && this._movingTrainers.get(_thisTrainer).canSurf()) || (xIndex + move3x==this.xObs && yIndex + move3y==this.yObs)){
			          blocked3=true;
			      }
				  for (int j =0; j<this._movingTrainers.size();j++){
						if(xIndex + move3x == this._movingTrainers.get(j).getXIndex() && yIndex + move3y== this._movingTrainers.get(j).getYIndex()){
							if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
							else{
							blocked3=true;
							}
						}
				  }
			}
			else{
				blocked3=true;
			}
			
			if(xIndex + move4x<this._room.getX() && yIndex + move4y<this._room.getY() && xIndex + move4x>=0 && yIndex + move4y>=0){
				  char next4=this._room._roomGrid[xIndex + move4x][yIndex + move4y];
				  if(next4==NO || next4==TRAINER || next4 == CUT || next4==VERTICAL || next4 == RIGHT_LEDGE || next4 == LEFT_LEDGE|| ((next4==DOOR || next4== SEAMLESS) && !this._approachTimer.isRunning())|| next4==SURF && !this._movingTrainers.get(_thisTrainer).canSurf() || ((next4== FREE || next4 == GRASS) && this._movingTrainers.get(_thisTrainer).canSurf())|| (xIndex + move4x ==this.xObs && yIndex+move4y==this.yObs)){
			          blocked4=true;
			      }
				  for (int j =0; j<this._movingTrainers.size();j++){
						if(xIndex + move4x == this._movingTrainers.get(j).getXIndex() && yIndex + move4y== this._movingTrainers.get(j).getYIndex()){
							if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
							else{
								blocked4=true;
							}
						}
				  }
			}
			else{
				blocked4=true;
			}
		}
		
		
		//Also  make player unable to move to where a trainer is.
		else if(who =="Player"){
			//If within the bounds, but facing obstacle, set true. If out of bounds, set blocked true as well.
			if(_xIndex + move1x<=this._room.getX() && _yIndex + move1y<=this._room.getY() && _xIndex + move1x>=0 && _yIndex + move1y>=0){
				  char next1=this._room._roomGrid[_xIndex + move1x][_yIndex + move1y];
				  if(next1== NO || next1==TRAINER || next1 == CUT || (next1== SURF && !_gbs.getSurf()) || ((next1== FREE || next1 == GRASS) && _gbs.getSurf())){
			          blocked1=true;
			      }
				  
				  for(int j=0; j<this._movingTrainers.size();j++){
					  if(_xIndex + move1x ==this._movingTrainers.get(_thisTrainer).getXIndex() && _yIndex + move1y ==this._movingTrainers.get(_thisTrainer).getYIndex()){
						  if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
						  else{
							  blocked1=true;
						  }
					  }
				  }
			}
			else{
				blocked1=true;
			}
			
			if(_xIndex + move2x<=this._room.getX() && _yIndex + move2y<=this._room.getY() && _xIndex + move2x>=0 && _yIndex + move2y>=0){
				  char next2=this._room._roomGrid[_xIndex + move2x][_yIndex + move2y];
				  if(next2== NO || next2==TRAINER || next2 == CUT || (next2== SURF && !_gbs.getSurf()) || ((next2== FREE || next2 == GRASS) && _gbs.getSurf())){
			          blocked2=true;
			      }
				  for(int j=0; j<this._movingTrainers.size();j++){
					  if(_xIndex + move2x ==this._movingTrainers.get(_thisTrainer).getXIndex() && _yIndex + move2y ==this._movingTrainers.get(_thisTrainer).getYIndex()){
						  if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
						  else{
						  blocked2=true;
						  }
					  }
				  }
			}
			else{
				blocked2=true;
			}
			
			
			if(_xIndex + move3x<=this._room.getX() && _yIndex + move3y<=this._room.getY() && _xIndex + move3x>=0 && _yIndex + move3y>=0){
				  char next3=this._room._roomGrid[_xIndex + move3x][_yIndex + move3y];
				  if(next3==NO || next3==TRAINER || next3 == CUT || (next3== SURF && !_gbs.getSurf()) || ((next3== FREE || next3 == GRASS) && _gbs.getSurf())){
			          blocked3=true;
			      }
				  for(int j=0; j<this._movingTrainers.size();j++){
					  if(_xIndex + move3x ==this._movingTrainers.get(_thisTrainer).getXIndex() && _yIndex + move3y ==this._movingTrainers.get(_thisTrainer).getYIndex()){
						  if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
						  else{
						  blocked3=true;
						  }
					  }
				  }
			}
			else{
				blocked3=true;
			}
			
			if(_xIndex + move4x<=this._room.getX() && _yIndex + move4y<=this._room.getY() && _xIndex + move4x>=0 && _yIndex + move4y>=0){
				  char next4=this._room._roomGrid[_xIndex + move4x][_yIndex + move4y];
				  if(next4==NO || next4==TRAINER || next4 == CUT || (next4== SURF && !_gbs.getSurf()) || ((next4== FREE || next4 == GRASS) && _gbs.getSurf())){
			          blocked4=true;
			      }
				  for(int j=0; j<this._movingTrainers.size();j++){
					  if(_xIndex + move4x ==this._movingTrainers.get(_thisTrainer).getXIndex() && _yIndex + move4y ==this._movingTrainers.get(_thisTrainer).getYIndex()){
						  if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
						  else{
							  blocked4=true;
						  }
					  }
				  }
			}
			else{
				blocked4=true;
			}
		}
	}
	
	
	
	public void makeBestMove(String method, double angle, String who){
		/**Current available methods:
		 * "avoid" - dodges all obstacles to 1st order avoidance. 
		 * "freeze" - stays still if faced with an obstacle.
		 * "vert" - vertical dominant, moves only vertically until colinear with target.
		 * "horiz" - horizontal dominant, moves only laterally until colinear with target.
		 * "drunk" - random component to create meandering.
		 *  "best" - 2nd order avoidance, will take a contorted path to target if absolutely necessary.
		 *  "wander" - random like drunk, but not nearly as hopeless.  
		 */
		
		//Uses ranking system to move around obstacles.
		if (method=="avoid"){
			if (!blocked1){
				this.convertToMotion(move1x,move1y, who);
			}
			else{
				if (!blocked2){
					this.convertToMotion(move2x,move2y, who);
				}
				else{
					if (!blocked3){
						this.convertToMotion(move3x,move3y, who);
					}
					else{
						if (!blocked4){
							this.convertToMotion(move4x,move4y, who);
						}
						else{
							if(who=="Trainer"){
								this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
							}
						}
					}
				}
			}
		}
		
		
		//Dependent on most dominant vector component. Else, NPC stays put.
		if (method=="freeze"){
			if (!blocked1){
				this.convertToMotion(move1x,move1y, who);
			}
			else{
				if (who=="Trainer"){
					if(move1x==0 && move1y==1){
						this._movingTrainers.get(_thisTrainer).faceDown();
						this._movingTrainers.get(_thisTrainer).setNextDir(1+4);
						}
					else if(move1x==0 && move1y==-1){
						this._movingTrainers.get(_thisTrainer).faceUp();
						this._movingTrainers.get(_thisTrainer).setNextDir(0+4);
						}
					else if(move1x==1 && move1y==0){
						this._movingTrainers.get(_thisTrainer).faceRight();
						this._movingTrainers.get(_thisTrainer).setNextDir(2+4);
						}
					else if(move1x==-1 && move1y==0){
						this._movingTrainers.get(_thisTrainer).faceLeft();
						this._movingTrainers.get(_thisTrainer).setNextDir(3+4);
						}
					this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					
					this.repaint();
				}
			}
		}
		
		if(method=="vert"){
			int moveVx=0;
			int moveVy=0;
			int xIndex;
			int yIndex;
			
			if (who=="Trainer"){
				xIndex=this._movingTrainers.get(_thisTrainer).getXIndex();
				yIndex=this._movingTrainers.get(_thisTrainer).getYIndex();
			}
			else{
				xIndex=this._xIndex;
				yIndex=this._yIndex;
			}
			boolean blockedV=false;
			
			if (angle>0 && angle<180){
				moveVy=1;
				moveVx=0;
			}
			if (angle>180 && angle<360){
				moveVy=-1;
				moveVx=0;
			}
			if (angle==0 || angle==360){
				moveVx=-1;
				moveVy=0;
			}
			if (angle==180){
				moveVx=1;
				moveVy=0;
			}	
			
			
			if((!_gbs.getPlayer().isIgnoring() && who == "Player") || who == "Trainer"){
				if(xIndex + moveVx<=this._room.getX() && yIndex + moveVy<=this._room.getY() && xIndex + moveVx>=0 && yIndex + moveVy>=0){
					  char nextV=this._room._roomGrid[xIndex + moveVx][yIndex + moveVy];
					  if(nextV==NO || nextV==TRAINER || nextV == CUT || nextV==VERTICAL || nextV == RIGHT_LEDGE || nextV == LEFT_LEDGE|| ((nextV==DOOR || nextV== SEAMLESS) && !this._approachTimer.isRunning())|| (nextV== SURF && !this._movingTrainers.get(_thisTrainer).canSurf()) || ((nextV== FREE || nextV == GRASS) && this._movingTrainers.get(_thisTrainer).canSurf()) || (xIndex + moveVx ==this.xObs && yIndex+moveVy==this.yObs)){
				          blockedV=true;
				      }
					  for(int j=0; j<this._movingTrainers.size();j++){
						  if(xIndex + moveVx ==this._movingTrainers.get(_thisTrainer).getXIndex() && yIndex + moveVy ==this._movingTrainers.get(_thisTrainer).getYIndex()){
							  if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
							  else{
								  blockedV=true;
							  }
						  }
					  }
				}
				else{
					blockedV=true;
				}
			}

			if (!blockedV){
				this.convertToMotion(moveVx,moveVy, who);
			}
			else{
				if(who=="Trainer"){
					this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					if(xIndex + moveVx==_xIndex && yIndex + moveVy==_yIndex){
						this.getMovingTrainers().get(_thisTrainer).facePlayer(this._xIndex, this._yIndex);
					}
				}
			}
			
		}
		
		if(method=="horiz"){
			int moveHx=0;
			int moveHy=0;
			int xIndex;
			int yIndex;
			
			if (who=="Trainer"){
			xIndex=this._movingTrainers.get(_thisTrainer).getXIndex();
			yIndex=this._movingTrainers.get(_thisTrainer).getYIndex();
			}
			else{
				xIndex=this._xIndex;
				yIndex=this._yIndex;
			}
			
			boolean blockedH=false;

			if (angle>90 && angle<270){
				moveHy=0;
				moveHx=1;
			}
			if ((angle>270 && angle <=360)||(angle>=0 && angle<90)){
				moveHy=0;
				moveHx=-1;
			}	
			if (angle==90){
				moveHx=0;
				moveHy=1;
			}
			if (angle==270){
				moveHx=0;
				moveHy=-1;
			}

			
			if((!_gbs.getPlayer().isIgnoring() && who == "Player") || who == "Trainer"){
				if(xIndex + moveHx<=this._room.getX() && yIndex + moveHy<=this._room.getY() && xIndex + moveHx>=0 && yIndex + moveHy>=0){
					  char nextH=this._room._roomGrid[xIndex + moveHx][yIndex + moveHy];
					  if(nextH==NO || nextH==TRAINER || nextH == CUT || nextH==VERTICAL || nextH == RIGHT_LEDGE || nextH == LEFT_LEDGE|| ((nextH==DOOR || nextH== SEAMLESS) && !this._approachTimer.isRunning())|| (nextH== SURF && !this._movingTrainers.get(_thisTrainer).canSurf()) || ((nextH== FREE || nextH == GRASS) && this._movingTrainers.get(_thisTrainer).canSurf()) || (xIndex + moveHx ==this.xObs && yIndex+moveHy==this.yObs)){
				          blockedH=true;
				      }
					  for(int j=0; j<this._movingTrainers.size();j++){
						  if(xIndex + moveHx ==this._movingTrainers.get(_thisTrainer).getXIndex() && yIndex + moveHy ==this._movingTrainers.get(_thisTrainer).getYIndex()){
							  if(this._movingTrainers.get(j).isDefeated() && this._movingTrainers.get(j).getVanishing()){}
							  else{
								  blockedH=true;
							  }
						  }
					  }
				}
				else{
					blockedH=true;
				}
			}
			
			if (!blockedH){
				this.convertToMotion(moveHx,moveHy, who);
			}
			else{
				if(who=="Trainer"){
					this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
				}
			}
		}
		
		if(method=="drunk"){
			Random r = new Random();
			int rand = r.nextInt(100);
			
			//Probabilities:
			//Best move = 50%
			int p1=50;
			//Second best = 15%
			int p2=65;
			//Third best = 10%
			int p3=75;
			//Worst move = 10%
			int p4=85;
			//Doesn't move at all. = 15%
			int p5=100;
			
			if(rand<=p1){
				if(!blocked1){
					this.convertToMotion(move1x, move1y,who);
				}
				else{
					if(who=="Trainer"){
						this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					}
				}
			}
			else if(rand<=p2){
				if(!blocked2){
					this.convertToMotion(move2x, move2y, who);
				}
				else{
					if(who=="Trainer"){
						this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					}
				}
			}
			else if(rand<=p3){
				if(!blocked3){
					this.convertToMotion(move3x, move3y,who);
				}
				else{
					if(who=="Trainer"){
						this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					}
				}
			}
			else if(rand<=p4){
				if(!blocked4){
					this.convertToMotion(move4x, move4y,who);
				}
				else{
					if(who=="Trainer"){
						this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					}
				}
			}
			else if(rand<=p5){
				if(who=="Trainer"){
					this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
				}
			}
			
		}
		
		if(method=="wander"){
			Random r = new Random();
			int rand = r.nextInt(100);
			
			//Probabilities:
			//Best move = 65%
			int p1=65;
			//Second best = 10%
			int p2=75;
			//Third best = 5%
			int p3=80;
			//Worst move = 5%
			int p4=85;
			//Doesn't move at all. = 15%
			int p5=100;
			
			if(rand<=p1){
				if(!blocked1){
					this.convertToMotion(move1x, move1y,who);
				}
				else{
					if(who=="Trainer"){
						this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					}
				}
			}
			else if(rand<=p2){
				if(!blocked2){
					this.convertToMotion(move2x, move2y, who);
				}
				else{
					if(who=="Trainer"){
						this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					}
				}
			}
			else if(rand<=p3){
				if(!blocked3){
					this.convertToMotion(move3x, move3y,who);
				}
				else{
					if(who=="Trainer"){
						this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					}
				}
			}
			else if(rand<=p4){
				if(!blocked4){
					this.convertToMotion(move4x, move4y,who);
				}
				else{
					if(who=="Trainer"){
						this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
					}
				}
			}
			else if(rand<=p5){
				if(who=="Trainer"){
					this.getMovingTrainers().get(_thisTrainer).setCanMove(false);
				}
			}
			
		}
			
	}
	
	public void convertToMotion(int movex, int movey, String who){
		if(who=="Player"){
			this._busy = true;
			
			if(movex==0 && movey==1){
				//Down
				this.Move(MOVEDOWN);

			}
			if(movex==1 && movey==0){
				//Right
				this.Move(MOVERIGHT);

			}
			if(movex==0 && movey==-1){
				//Up
				this.Move(MOVEUP);

			}
			if(movex==-1 && movey==0){
				//Left
				this.Move(MOVELEFT);
			}
		}
		if(who=="Trainer"){
			this.getMovingTrainers().get(_thisTrainer).setCanMove(true);
			
			if(this._room._roomGrid[this._movingTrainers.get(_thisTrainer).getXIndex()+movex][this._movingTrainers.get(_thisTrainer).getYIndex()+movey]==GRASS){
				this._movingTrainers.get(_thisTrainer)._onGrass=true;
			}
			else{
				this._movingTrainers.get(_thisTrainer)._onGrass=false;
			}
			
			if(movex==0 && movey==1){
				this._movingTrainers.get(_thisTrainer).setNextDir(1);
			}
			if(movex==1 && movey==0){
				this._movingTrainers.get(_thisTrainer).setNextDir(2);
			}
			if(movex==0 && movey==-1){
				this._movingTrainers.get(_thisTrainer).setNextDir(0);
			}
			if(movex==-1 && movey==0){
				this._movingTrainers.get(_thisTrainer).setNextDir(3);
			}
			
		}
	}
	

	public void avoidTheNPC(int x, int y){
		if(!_gbs.getPlayer()._ignoreObstacles){
			_upForbid=false;
			_downForbid=false;
			_leftForbid=false;
			_rightForbid=false;
			for(int j=0; j<this._movingTrainers.size();j++){
				if(_xIndex + x ==this._movingTrainers.get(j).getXIndex() && _yIndex + y ==this._movingTrainers.get(j).getYIndex() && ((this._movingTrainers.get(j).getVanishing() && !this._movingTrainers.get(j).isDefeated())||(!this._movingTrainers.get(j).getVanishing()))  ){
					if(x==0 && y==1){_downForbid=true;}
					if(x==0 && y==-1){_upForbid=true;}
					if(x==1 && y==0){_rightForbid=true;}
					if(x==-1 && y==0){_leftForbid=true;}
				}
			}
		}
	}
	
	public void slidePlayer(char next){
		
		//Determine where to stop
		//Start the approach timer.
		//Set spinning on.
		
		
		if (next==SLIDE_LEFT){
			//Left
			for(int j=-1; j>=0-_xIndex; j--){
				int nextX=_xIndex+j;
				int nextY=_yIndex;
				char nextStop= this._room._roomGrid[nextX][nextY];
				if (nextStop == FREE || nextStop == SLIDE_UP || nextStop == SLIDE_DOWN || nextStop == SLIDE_RIGHT || nextStop == SEAMLESS){
					_gbs.getPlayer().addDest(nextX,nextY);
					SysOut.print("DEST ADDED");
					_gbs.getPlayer().setAvoidMethod("freeze");
					_gbs.getPlayer().setIgnoring(true);
					this._approachTimer.start();
					return;
				}
			}
		}
		else if (next==SLIDE_DOWN){
			//Down
			for(int j=1; j<=this._room._height-_yIndex-1; j++){
				int nextX=_xIndex;
				int nextY=_yIndex+j;
				char nextStop= this._room._roomGrid[nextX][nextY];
				if (nextStop == FREE || nextStop == SLIDE_UP || nextStop == SLIDE_LEFT || nextStop == SLIDE_RIGHT || nextStop == SEAMLESS){
					_gbs.getPlayer().addDest(nextX,nextY);
					SysOut.print("DEST ADDED");
					_gbs.getPlayer().setAvoidMethod("freeze");
					_gbs.getPlayer().setIgnoring(true);
					_gbs.setSpinning(true);
					this._approachTimer.start();
					return;
				}	
			}
		}
		
		
		else if (next==SLIDE_UP){
			//Up
			for(int j=-1; j>=0-_yIndex; j--){
				int nextX=_xIndex;
				int nextY=_yIndex+j;
				char nextStop= this._room._roomGrid[nextX][nextY];
				if (nextStop == FREE || nextStop == SLIDE_LEFT || nextStop == SLIDE_DOWN || nextStop == SLIDE_RIGHT || nextStop == SEAMLESS){
					_gbs.getPlayer().addDest(nextX,nextY);
					SysOut.print("DEST ADDED");
					_gbs.getPlayer().setAvoidMethod("freeze");
					_gbs.getPlayer().setIgnoring(true);
					_gbs.setSpinning(true);
					this._approachTimer.start();
					return;
				}
			}
		}
		else if (next==SLIDE_RIGHT){
			//Right
			for(int j=1; j<=this._room._width-_xIndex-1; j++){
				int nextX=_xIndex+j;
				int nextY=_yIndex;
				char nextStop= this._room._roomGrid[nextX][nextY];
				if (nextStop == FREE || nextStop == SLIDE_UP || nextStop == SLIDE_DOWN || nextStop == SLIDE_LEFT || nextStop == SEAMLESS){
					_gbs.getPlayer().addDest(nextX,nextY);
					SysOut.print("DEST ADDED");
					_gbs.getPlayer().setAvoidMethod("freeze");
					_gbs.getPlayer().setIgnoring(true);
					_gbs.setSpinning(true);
					this._approachTimer.start();
					return;
				}
			}
		}
	}

	
	public int getPlayerDirection(){
		return getDirection();
	}
	
	public void setPlayerDirection(int direction){
		setDirection(direction);
		NORTH = WEST = EAST = SOUTH =false;
		if(getDirection()==0){NORTH=true;}
		if(getDirection()==1){EAST=true;}
		if(getDirection()==2){SOUTH=true;}
		if(getDirection()==3){WEST=true;}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g2 = (Graphics2D) g;
		//menu code
		
		if(_menuVisible || _bannerVisible)
			this.drawMenu(g2);
		
		if(ledge || prevLedge){
			this.drawShadow(g2);
		}
		
		

		if(!(_roomNum == 0 || _roomNum == -1)){
			this.drawPlayer(g2);
		}
		
		//this.drawAllGenerics(g2);
		
		if(psnTextVisible){
			this.drawText(g2, _gbs.getPlayer().whoDiedFromPSN() + " fainted!");
		}

	}
	
	public void scanForAllEvents(){
		
	}
	
	public void loadAllEvents(){
		
	}
	
	public void standardTrainerScan(int totalNumber){
		if(this._movingTrainers!=null){
			for(int i = 0; i < totalNumber; i++){
				if(this._movingTrainers.get(i).isDefeated())
					this.getCheckList().set(i,1);
			}
		}
	}
	public void standardTrainerScan(int start, int totalNumber){
		if(this._movingTrainers!=null){
			for(int i = start; i < totalNumber; i++){
				if(this._movingTrainers.get(i).isDefeated())
					this.getCheckList().set(i,1);
			}
		}
	}
	
	public void standardTrainerLoad(int totalNumber){
		if(this.getCheckList().size() != 0){
			for(int i = 0; i < totalNumber; i++){
				if(this.getCheckList().get(i)==1){
					_movingTrainers.get(i).defeatAndPostBattle();
					if(_movingTrainers.get(i).getPostItemDialogue().size()>0){
						_movingTrainers.get(i).defeatAndPostItemize();
					}
				}
			}
		}
	}
	
	public void standardTrainerLoad(int start, int totalNumber){
		if(this.getCheckList().size() != 0){
			for(int i = start; i < totalNumber; i++){
				if(this.getCheckList().get(i)==1)
					_movingTrainers.get(i).defeatAndPostBattle();
			}
		}
	}

	public void loadTrainerLocations(int i, int xnew, int ynew){
		try{
			Trainer loaded = this._movingTrainers.get(i);
			loaded.setHomeIndices(xnew, ynew);
			loaded.resetHome();			
		}
		catch(ArrayIndexOutOfBoundsException aioobe){
			SysOut.print("ARRAY INDEX OUT OF BOUNDS!!!");
		}
		
	}
	
	public void initializeEventVector(int size){
		this._eventCheckList = new Vector<Integer>();
		this._eventCheckList.clear();
		//this._eventCheckList.setSize(size);
		
		//String list = "";
		for(int i=0; i<size ;i++){
			this._eventCheckList.add(0);
			//list += this._eventCheckList.get(i) + " ";
		}
		//SysOut.print("Events in room " + this.getClass().getName() + ": " + list);
	}

	public void Number_Button(int i){
		//Empty for now, but overwrite in JWW.
	}
	
	public void A_Button(){
		
		if(!_healed && _heal.isRunning()){
			return;
		}
		
		if(_gbs.map.isVisible()){
			_gbs.map.A_Button();
			return;
		}
		if(_pcVisible){
			this._movingTrainers.get(_interruptedTrainer).setInterrupted(false);
			_NPCpage=0;
			_dialogueVisible=false;
			
			if(_pcCursor.y == 123){
				//Banner
				if(!this._menuTextVisible[0] && !_bannerVisible){
					this._menuTextVisible[0] = true;
				}
				else{
					

					if(!_bannerVisible){
						this._menuTextVisible[0] = false;
						_bannerVisible = true;
						_gbs.bannerScreen.clearBelt();
						_gbs.bannerScreen.checkBelt();
						setCurrentMenu(_gbs.bannerScreen);
						getCurrentMenu().setVisible(true);
						this.add(getCurrentMenu(), BorderLayout.CENTER);
					}
					else{
						_gbs.bannerScreen.A_Button();
					}

				}
				
			}
			if(_pcCursor.y == 148){
				//MyCourse
				if(!this._menuTextVisible[1] && !_myCoursesVisible){
					this._menuTextVisible[1] = true;
					this.repaint();
				}
				else{
					if(!_myCoursesVisible){
						this._menuTextVisible[1] = false;
						_myCoursesVisible = true;
						_gbs.pcBoxScreen.refreshList();
						setCurrentMenu(_gbs.pcBoxScreen);
						getCurrentMenu().setVisible(true);
						this.add(getCurrentMenu(), BorderLayout.CENTER);
					}
					else{
						_gbs.pcBoxScreen.A_Button();
					}
				}
				
			}
			if(_pcCursor.y == 173){
				
				_pcVisible = false;
				_pcCursor.y = 123;
				_busy = false;
				completionCheck = false;
			}
			return;
			
		}
		
		if(poisonVisible && !textTimer.isRunning()){
			poisonVisible=false;
			_busy=false;
			this.repaint();
			completionCheck = false;
		}
		
		if(repelVisible && !textTimer.isRunning()){
			repelVisible=false;
			_busy=false;
			repelling=false;
			this.repaint();
			completionCheck = false;
		}
		
		
		//SysOut.print("A_ BUTTON?");
		//Interrupting a trainer.
		if(this.facingNPC() && !_menuVisible && !_pcVisible && !_martMenuVisible && !textTimer.isRunning() && !_approachTimer.isRunning() && !escapeTimerEnter.isRunning() && !escapeTimerLeave.isRunning()){
			if(((this.facingWater() || this.facingNO()) && _gbs.getSurf()) || (!this.facingWater()) && !_gbs.getSurf()){
				//Interrupt them, hear what they have to say.
				Trainer moving = this._movingTrainers.get(this._interruptedTrainer);
				//SysOut.print("Trainer name: " + moving.getName());
				
				if(moving.getName() == "Map"){
					if(!_gbs.map.isVisible()){
						SysOut.print("Hi Map!");
						setCurrentMenu(_gbs.map);
						getCurrentMenu().setVisible(true);
						_busy = true;
						this.add(getCurrentMenu(), BorderLayout.CENTER);
					}
					return;
				}
			
				if(moving.getVanishing() && !moving.isDefeated()||(!moving.getVanishing())){
					if(!_busy){
						switch(moving.currentAction){
						case Trainer.MOVE_UP:
						case Trainer.FACE_UP: moving.currentAction = Trainer.FACE_UP;
							break;
						case Trainer.MOVE_DOWN:
						case Trainer.FACE_DOWN: moving.currentAction = Trainer.FACE_DOWN;
							break;
						case Trainer.MOVE_LEFT:
						case Trainer.FACE_LEFT: moving.currentAction = Trainer.FACE_LEFT;
							break;
						case Trainer.MOVE_RIGHT:
						case Trainer.FACE_RIGHT: moving.currentAction = Trainer.FACE_RIGHT;
							break;
						}
						
						//SysOut.print("interrupted the trainer.");
						moving.setInterrupted(true);
						moving.setLOS(0);
						moving.setDirectionAndImage(getDirection());
						
						if(moving.getGender() == 'M' && moving.getBelt()!=null && !moving.isDefeated() && M2.TRAINER_APPROACH_M != M2.getCurrentSong())
							M2.playBG(M2.TRAINER_APPROACH_M);
						else if(moving.getGender() == 'F'&& moving.getBelt()!=null && !moving.isDefeated() && M2.TRAINER_APPROACH_F != M2.getCurrentSong())
							M2.playBG(M2.TRAINER_APPROACH_F);
						else if(moving.getGender() == 'R'&& moving.getBelt()!=null && !moving.isDefeated() && M2.RIVAL_MUSIC != M2.getCurrentSong())
							M2.playBG(M2.RIVAL_MUSIC);
						
						_NPCpage=0;
						_busy=true;
						_dialogueVisible=true;
						
						//This situation is specifically for Item balls.
						if(moving.getName()=="Item" && moving.getGift()!=null){
							System.out.println("ITEM TRIGGERED");
							_receivedItem=true;
							_gbs.getPlayer().getAllItems().get(moving.getGift().getIndex()).increaseByi(1);
							M2.playFX(M2.ITEM_RECEIVED);
							this.receivedGiftName=moving.getGift().getName();
						}
					}
					else if(_busy && _dialogueVisible){
						if(moving.getDialogue() != null && _NPCpage+1<moving.getDialogue().size()){
							//SysOut.print("MOAR DIALOG PLZ");
							_NPCpage++;
						}
						else{
							
							if(moving.getBelt()!=null && !moving.isDefeated()){
								this.trainer=_interruptedTrainer;
								if(this._roomNum == _gbs.MAIN_GREEN && _movingTrainers.get(_interruptedTrainer).getName().equalsIgnoreCase("Gambino")){
									this.setEliteBattleTrue(GAMBINO);
								}
								this._attack.start();
							}
							//if()
							
							
							if(moving.getGift()!=null && !_receivedItem && ((moving.getBelt()!=null && moving.isDefeated()) || moving.getBelt()==null)){
								_gbs.getPlayer().getAllItems().get(moving.getGift().getIndex()).increaseByi(1);
								this.receivedGiftName=moving.getGift().getName();
								_receivedItem=true;
								M2.playFX(M2.ITEM_RECEIVED);
	
								//TODO: MUSIC
							}
							else{
								
								
								if(_receivedItem){
									//Set gift to null
									moving.setGift(null);
									
									if (moving.getPostItemDialogue()!=null){
										moving.getDialogue().clear();
										for(int i =0; i<moving.getPostItemDialogue().size();i++){
											moving.getDialogue().add(moving.getPostItemDialogue().get(i));
										}
									}
									else{
										moving.getDialogue().clear();
										moving.getDialogue().add(moving.getDefeatDialogue());
									}
									
									if(moving.defeatAfterItem()){
										moving.defeat();
										SysOut.print("Actually im here.");
									}
									
									_receivedItem=false;
									_NPCpage=-1;
									this.A_Button();
								}
								else{
									_NPCpage=0;
									_busy=false;
									completionCheck=false;
									_dialogueVisible=false;
									moving.setInterrupted(false);
									if(moving.getName()=="Item" || moving.defeatAfterItem()){
										moving.defeat();
										if(this._approachTimer.isRunning()){
											this._approachTimer.stop();
										}
										SysOut.print("justkidding.");
									}
									
								}
								
								if(_approached || _oneShot || _seenByNPC){							
									
									if(_approached){
										this._approachTimer.start();
										_approached=false;
										_returnTrip=true;
										moving.setAvoidMethod(moving.getReturnMethod());
									}
									else if (_oneShot || _seenByNPC){
										_oneShot=false;
										
										if(_seenByNPC){
											_seenByNPC=false;
											if(moving.getBelt() != null){
												this.trainer=_interruptedTrainer;
												if(this._roomNum == _gbs.MAIN_GREEN && _movingTrainers.get(_interruptedTrainer).getName().equalsIgnoreCase("Gambino")){
													this.setEliteBattleTrue(GAMBINO);
												}
												this._attack.start();
											}
										}
									}
									
									
									if(_playerDestX>=0){
										//SysOut.print("Destination added");
										_gbs.getPlayer().addDest(_playerDestX, _playerDestY);
									}
									
								}
							}
						}
					}
				}
			}
		}
		
		//HM's
		if(this.facingWater() && !_gbs.getSurf() && !this.facingNPC() && !_menuVisible && !_pcVisible && !_martMenuVisible && !textTimer.isRunning()){
			if(!hmTextVisible){
				hmTextVisible=true;
				_busy=true;
			}
			else if(hmTextVisible && _canSurf==0 && !textTimer.isRunning()){
				//Do you have surf?
				this.playerHasHM("Surf");
			}
			
			else if(hmTextVisible && _canSurf==-1 && !textTimer.isRunning()){
				//Player doesn't have surf.
				_busy=false;
				completionCheck=false;
				hmTextVisible=false;
				_canSurf=0;
			}

			else if(hmTextVisible && _canSurf==1 && !textTimer.isRunning()){
				//Blank used Surf!
				_canSurf=2;
			}
			else if(hmTextVisible && _canSurf==2 && !textTimer.isRunning()){
				//Surf!
				this._gbs.setSurf(true);
				this._gbs.setMode(false);
				if(NORTH){
					this.Move(MOVEUP);
				}
				else if(EAST){
					this.Move(MOVERIGHT);
				}
				else if(SOUTH){
					this.Move(MOVEDOWN);
				}
				else if(WEST){
					this.Move(MOVELEFT);
				}
				M2.playBG(M2.SURF);
				_busy=false;
				completionCheck=false;
				hmTextVisible=false;
				_canSurf=0;
			}
		}
		
		if(_menuVisible){
			if(!getCurrentMenu().isVisible()){
			
			
			switch((int)_menuCursor.y){
			
			case 23:
				if(_gbs.getPlayer().getPokedex() != null){
					setCurrentMenu(_gbs.pds);
					getCurrentMenu().setVisible(true);
					_gbs.pbs.setVisible(false);
					_gbs.ii.setVisible(false);
					_gbs.pis.setVisible(false);
					this.add(getCurrentMenu(), BorderLayout.CENTER);
				}
				break;
			case 48: 
				if(_gbs.getPlayer()._activePokemon.size() > 0){
					 	setCurrentMenu(_gbs.pbs);
					 	getCurrentMenu().setVisible(true);
					 	_gbs.ii.setVisible(false);
					 	_gbs.pis.setVisible(false);
					 	_gbs.pds.setVisible(false);
					 	_gbs.map.setVisible(false);
					 	this.add(getCurrentMenu(), BorderLayout.CENTER);
				}
				break;
			case 73: //Can't use items while you don't have Pokemon. Prevents an error.
				if(_gbs.getPlayer().getAllActive().size() > 0){
					_gbs.ii.repaint();
					 setCurrentMenu(_gbs.ii);
					 getCurrentMenu().setVisible(true);
					 _gbs.pbs.setVisible(false);
					 _gbs.pis.setVisible(false);
					 _gbs.pds.setVisible(false);
					 _gbs.map.setVisible(false);
					 this.add(getCurrentMenu(), BorderLayout.CENTER);
				}
				break;
			case 98: setCurrentMenu(_gbs.pis);
				 	 getCurrentMenu().setVisible(true);
				 	_gbs.pbs.setVisible(false);
				 	_gbs.ii.setVisible(false);
				 	_gbs.pds.setVisible(false);
				 	_gbs.map.setVisible(false);
					 this.add(getCurrentMenu(), BorderLayout.CENTER);
				break;
			case 123: _gbs.saveGame();
				break;
			case 148: 
				if(_gbs.getPlayer().hasTownMap()){
					  setCurrentMenu(_gbs.map);
					  getCurrentMenu().setVisible(true);
					  _gbs.pbs.setVisible(false);
					  _gbs.ii.setVisible(false);
					  _gbs.pis.setVisible(false);
					  _gbs.pds.setVisible(false);
					  _menuVisible = false;
					  this.add(getCurrentMenu(), BorderLayout.CENTER);
				}
				break;
			case 173: this.Start();
				break;
			}
			}
			else{
				if(_gbs.pds.isVisible()){
					_gbs.pds.A_Button();
				}
				if(_gbs.pbs.isVisible()){
					_gbs.pbs.A_Button();
				}
				if(_gbs.ii.isVisible()){
					_gbs.ii.A_Button();
				}
				if(_gbs.map.isVisible()){
					_gbs.map.A_Button();
				}
	
			}
		}
		
		if(_gbs.ev != null && _gbs.ev.isVisible()){
			_gbs.ev.A_Button();
		}
		
		
		if(textTimer != null && textTimer.isRunning()){
			textTimer.setDelay(10);
		}
		
		if(_martMenuVisible){
			if(!buyMenu && !sellMenu){
				//TODO: mart 
				switch((int)_martCursor.y){
				case 15: //SysOut.print("BUY!!!");
					buyMenu = true;
					_gbs.mart.initialize(MartMenu.BUY);
					//mart.setGoods(martGoods);
					setCurrentMenu(_gbs.mart);
					getCurrentMenu().setVisible(true);
					this.add(getCurrentMenu(), BorderLayout.CENTER);
					//_martCursor.x = 200;
					break;
				case 35: //SysOut.print("SELL!!!");
					sellMenu = true;
					_gbs.mart.initialize(MartMenu.SELL);
					//mart.setGoods(martGoods);
					setCurrentMenu(_gbs.mart);
					getCurrentMenu().setVisible(true);
					this.add(getCurrentMenu(), BorderLayout.CENTER);
					break;
				case 55: _martMenuVisible = false;
						getCurrentMenu().setVisible(false);
						 _busy = false;
						 completionCheck = false;
						 _martCursor.y = 15;
						 _dialogueVisible = false;
					break;
				}
			}
			else if(buyMenu || sellMenu){
				_gbs.mart.A_Button();
			}
		}
		
		//HAS TO HAPPEN AT THE END
		//SysOut.print("the letnghae "+ this.getCheckList().size());
		if(this.getCheckList().size() > 0){
			this.scanForAllEvents();
		}
		
		getCurrentMenu().repaint();
		this.repaint();
		
	}
	

	/**
	 * Used to call the saveGame() method of the GameBoyScreen. Allows
	 * the user to save their game at any time (except in battle). 
	 */
	public void B_Button(){
		if(_martMenuVisible && !buyMenu && !sellMenu){
			this.Down();
			this.Down();
			this.A_Button();
		}
		
		if(this.facingNPC() && this._movingTrainers.get(this._interruptedTrainer).isInterrupted() && !_menuVisible && !_pcVisible && !_martMenuVisible && !textTimer.isRunning() && !_approachTimer.isRunning() && !escapeTimerEnter.isRunning() && !escapeTimerLeave.isRunning()){
			if((this.facingWater() && _gbs.getSurf()) || (!this.facingWater()) && !_gbs.getSurf()){
				//Interrupt them, hear what they have to say.
				Trainer moving = this._movingTrainers.get(this._interruptedTrainer);
				//SysOut.print("Trainer name: " + moving.getName());
				
				//Specifically for Cut and Strength
				if(moving.getName() == "CutBush"||moving.getName() == "CutCan" || moving.getName()=="Swiper"){
					//SysOut.print("Chose not to use Cut!");
					_NPCpage=0;
					_busy=false;
					completionCheck=false;
					_dialogueVisible=false;
					moving.setInterrupted(false);
					return;
				}
				if(moving.getName() == "Strength"){
					//SysOut.print("Chose not to use Strength!");
					_NPCpage=0;
					_busy=false;
					completionCheck=false;
					_dialogueVisible=false;
					moving.setInterrupted(false);
					return;
				}
			}
		}
		
		
		if(hmTextVisible && _canSurf==1 && !textTimer.isRunning() && !_menuVisible && !_pcVisible && !_martMenuVisible){
			_busy=false;
			completionCheck=false;
			hmTextVisible=false;
			_canSurf=0;
			this.repaint();
		}
		
		if(_menuVisible && !getCurrentMenu().isVisible())
			this.Start();
		
		if(_gbs.map.isVisible()){
			_gbs.map.B_Button();
			_busy=false;
			return;
		}
		
		if(getCurrentMenu().isVisible()){
			if(_gbs.pbs.isVisible() || _gbs.ii.isVisible() || _gbs.ev.isVisible() || _gbs.map.isVisible() || _martMenuVisible){
				if(_gbs.pbs.isVisible())
					_gbs.pbs.B_Button();
				if(_gbs.ii.isVisible())
					_gbs.ii.B_Button();
				if(_gbs.ev.isVisible())
					_gbs.ev.B_Button();
				if(_martMenuVisible){
					if(buyMenu || sellMenu){
						_gbs.mart.B_Button();
					}
				}
			}
			else{
				getCurrentMenu().setVisible(false);
			}
			this.repaint();
		}
		
		if(_pcVisible && !_menuTextVisible[0] && !_menuTextVisible[1]){
			//SysOut.print("PC IS VISIBLE");
			if(!_bannerVisible && !_myCoursesVisible){
				_busy = false;
				completionCheck = false;
				_pcCursor.y = 173;
				this.A_Button(); //Effectively moves the cursor to Quit and hits A button. Do this method.
				
			}
			else{
				//if(_bannerVisible){
					_bannerVisible = false;
					_gbs.bannerScreen._depTextVisible = false;
					_gbs.bannerScreen._oneLeft = false;
					_gbs.bannerScreen._cursor.y = _gbs.bannerScreen._1st;
					_menuTextVisible[0] = false;
				//}
				//if(_myCoursesVisible){
					_myCoursesVisible = false;
					_gbs.pcBoxScreen._cursor.y = 8;
					_menuTextVisible[1] = false;
					_gbs.pcBoxScreen.warningDisplayed = false;
				//}
			}
			this.repaint();
		}
		
	}
	
	/**
	 * Currently an effectively empty method, but will eventually be used
	 * to pull up a functional "Start" menu.
	 */
	public void Start(){
		
		if(!_martMenuVisible && !_pcVisible && !completionCheck && textTimer !=null && !textTimer.isRunning() && !_approachTimer.isRunning() && !escapeTimerEnter.isRunning() && !escapeTimerLeave.isRunning()){
			if(!_busy && !_menuVisible){
				_busy = true;
				_menuVisible = true;
			}
			else{
				if(!getCurrentMenu().isVisible()){
					_busy = false;
					completionCheck = false;
					_menuVisible = false;
					_menuCursor.y = 23;
				}
			}
		}
		
		
		this.repaint();
	}
	

	public void defeat(int trainerNum){
		
	}
	public void blackout(){
		_gbs.getPlayer().healAllActive();
		this.blackout(_pkmnCentX, _pkmnCentY, FACESOUTH);
	}
	
	public void blackout(int newXIndex, int newYIndex, int direction){
		PokePanel2 current = _gbs.getCurrentPanel();
		M2.playBG(current.getSong());
		current.createBaseRoom();
		_gbs.getCurrentPanel().setMartVisible(false);
		current.setIndices(newXIndex, newYIndex);
		current.setLocation(-current.xConstant-(20*current._xIndex)+176, -current.yConstant-(20*current._yIndex)+168);
		current._darkLevel = 240;
		current._fadeToBlack.stop();
		current._fadeUp.start();
		current.resetNTimer();
		_gbs.startNTimer();
		
		switch(direction){
		case FACESOUTH:
			current.Down(); break;
		case FACENORTH:
			current.Up(); break;
		case FACEEAST:
			current.Right(); break;
		case FACEWEST:
			current.Left(); break;
		default:
			current.Down(); break;
		}
		
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		this.repaint();
		
	}
	public void Select(){
		
//		for(int i = 0; i < _gbs.getPlayer().getPokedex().getList().length; i++){
//			//SysOut.print("Player has: " + _gbs.getPlayer().getPokedex().getList()[i] + " a " + Pokemon.getPokemonNameByDexNumber(i) + ", " + i);
//			
//		}
		
		for(int i = 0; i < _gbs.getPlayer().getAllActive().size(); i++){
			//SysOut.print(_gbs.getPlayer().getActivePokemon(i).getName() + "'s EXP: " + _gbs.getPlayer().getActivePokemon(i).getExp());
		}
		
		if(!_menuVisible && !_busy){
			if(this._canBike && !_gbs.getSurf()){
			_gbs.switchMode();
				if(_gbs.getMode()){
					M2.playBG(M2.BIKE);
				}
				else{
					M2.playBG(this.getSong());
				}
			}
		}
		
	    if(NORTH)
	    {
	    	try{
				if (_gbs.getMode()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikeupFace.png")));}
				else if (_gbs.getSurf()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfU1.png")));}
				else{_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/upFace.png")));}
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
	    }
	    if(WEST)
	    {
	    	try{
				if (_gbs.getMode()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikeleftFace.png")));}
				else if (_gbs.getSurf()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfL1.png")));}
				else{_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftFace.png")));}
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
	    }
	    if(EAST)
	    {
	    	try{
				if (_gbs.getMode()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikerightFace.png")));}
				else if (_gbs.getSurf()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfR1.png")));}
				else{_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rightFace.png")));}
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
	    }
	    if(SOUTH)
	    {
	    	try{
				if (_gbs.getMode()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikedownFace.png")));}
				else if (_gbs.getSurf()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfD1.png")));}
				else{_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/downFace.png")));}
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
	    }
		
		
		else{
			//Nothing while menu is open.
		}
		this.repaint();
	}
	
	public void ResetEvents(){
		for(int i = 0; i<this.getCheckList().size(); i++){
			this.getCheckList().set(i,0);
		}
		this.createBaseRoom();
	}
	
	public void CompleteEvents(){
		for(int i = 0; i<this.getCheckList().size(); i++){
			this.getCheckList().set(i,1);
		}
		this.createBaseRoom();
	}
	
	public void Coord(){
		
		char[][] room = this._room._roomGrid;
		
		for(int i = 0; i < room[0].length; i++){
			for(int i2 = 0; i2 < room.length; i2++){
				if(i == _yIndex && i2 == _xIndex){
					System.out.print("P ");
				}
				else{
					Trainer tr = null;
					int index = -1;
					for(int t = 0; t < _movingTrainers.size(); t++){
						if( _movingTrainers.get(t).getXIndex() == i2 && _movingTrainers.get(t).getYIndex() == i){
							tr = _movingTrainers.get(t);
							index = t;
							break;
						}
					}
					if(tr == null)
						System.out.print(room[i2][i] + " ");
					else
						System.out.print(index + " ");
				}
			}
			System.out.println();
		}
		
		for(int tra = 0; tra < _movingTrainers.size(); tra++){
			System.out.println("Trainer " + tra + ": " + _movingTrainers.get(tra).getName());
		}
		
		
//		for(int i = 1; i <= 8; i++){
//			if(!_gbs.getPlayer().isGymLeaderDefeated(i)){
//				_gbs.getPlayer().defeatGymLeader(i);
//				SysOut.print("Defeated: " + i);
//				return;
//			}
//		}
//		
//		
		SysOut.print("BUSY:" + _busy);
		SysOut.print("Approach: " + this._approachTimer.isRunning());
//		SysOut.print("Xc:" + _xSpace + " Yc:" + _ySpace);
//		SysOut.print("RoomNum: " + this._roomNum);
//		//SysOut.print("XObs:" + this.xObs + " YObs:" + this.yObs);
//		////SysOut.print("Xscreen:" + _xSpace + "Yscreen:" + _ySpace);
		SysOut.print("Xi:" + _xIndex + " Yi:" + _yIndex);
//		if(this._room!=null && this._room._roomGrid!=null){
//				SysOut.print("MAP CHAR:"+ this._room._roomGrid[_xIndex][_yIndex]);
//		}
//		//SysOut.print("Screen Width:" + this.getWidth()+ " Screen Height:" + this.getHeight());
////		//SysOut.print("Active=======================");
////		for (int i=0; i < _gbs.getPlayer().getAllActive().size(); i++){
////		//	//SysOut.print(_gbs.getPlayer().getActivePokemon(i).getName() + " "+ _gbs.getPlayer().getActivePokemon(i).getBelt());
////		}
//		//SysOut.print("TRAINERS================");
//		for (int i=0; i < _this._movingTrainers.size(); i++){
//			//SysOut.print(_this._movingTrainers.get(i).getName());
//			//SysOut.print("Index ("+_this._movingTrainers.get(i).getXIndex()+","+_this._movingTrainers.get(i).getYIndex()+")");
//			//SysOut.print("Space ("+_this._movingTrainers.get(i).getXSpace()+","+_this._movingTrainers.get(i).getYSpace()+")");
//		}
		//SysOut.print("========================");
	//	_gbs.getPlayer().addDest(12, 1);
	//	_approachTimer.start();
	//	_gbs.getPlayer().reconfigureAllPokemon();
		
//		_gbs.saveGame();
		
//		File errFolder = new File("Error Logs");
//		errFolder.mkdir();
//		File errLog = new File("Error Log " + System.nanoTime() + ".txt");
//		try {
//			BufferedWriter out = new BufferedWriter(new FileWriter(errLog.getPath()));
//			out.write(errLog.getName()); out.newLine();
//			out.write("============================================="); out.newLine();
//			out.write("Current Room: " + this._roomNum); out.newLine();
//			out.write("Current Index Location: " + this._xIndex + "," + this._yIndex); out.newLine();
//			out.newLine();
//			out.write("Player Name: " + _gbs.getPlayer().getPlayerName()); out.newLine();
//			out.write("Rival Name: " + _gbs.getRival().getName()); out.newLine();
//			int numB = 0;
//			for(int i = 1; i <= 8; i++){
//				if(_gbs.getPlayer().isGymLeaderDefeated(i)) numB++;
//			}
//			out.write("Player has " + numB + " out of 8 badges"); out.newLine();
//			out.newLine();
//			if(_menuVisible){
//				out.write("Menu was visible"); out.newLine();
//				if(getCurrentMenu().isVisible()){
//					if(_gbs.pds.isVisible()){ out.write("Pokedex was visible"); out.newLine();}
//					if(_gbs.pbs.isVisible()){ out.write("Belt was visible"); out.newLine();}
//					if(_gbs.ii.isVisible()){ out.write("Item Menu was visible"); out.newLine();}
//					if(_gbs.map.isVisible()){ out.write("Map was visible"); out.newLine();}
//				}
//				out.newLine();
//			}
//			if(_pcVisible){
//				out.write("PC was visible"); out.newLine(); out.newLine();
//			}
//			out.write("Player last spoke with trainer number: " + _interruptedTrainer); out.newLine();
//			
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		errLog.renameTo(new File(errFolder, errLog.getName()));
//		
//		errThanks.setVisible(true);
	}

public void Move(int direction){
		
		if((_walkTimer == null || !_walkTimer.isRunning()) && !this.escapeTimerEnter.isRunning() && !this.escapeTimerLeave.isRunning()){
			
			//Adjust speed for bike vs. walk. Lowered _Speed=faster.
			if(!_approachTimer.isRunning()){ //7,13
				if (_gbs.getMode()){_SpeedTimer=7;} //FOR THE LOVE OF GOD, FIXME!!!
				else{_SpeedTimer=13;}
			}
			
			 if(direction == 3){
					setDirection(3);
					if(leftTime == null)
						leftTime = new LeftTimer();
					_walkTimer = new Timer(_SpeedTimer, leftTime);
			}
			 else if(direction == 0){
				setDirection(0);
				if(upTime == null)
					upTime = new UpTimer();
				_walkTimer = new Timer(_SpeedTimer, upTime);
			}
			else if(direction == 1){
				setDirection(1);
				if(downTime == null)
					downTime = new DownTimer();
				_walkTimer = new Timer(_SpeedTimer, downTime);
			}
			else if(direction == 2){
				setDirection(2);
				if(rightTime == null)
					rightTime = new RightTimer();
				_walkTimer = new Timer(_SpeedTimer, rightTime);
			}
			
			
			_walkTimer.start();
			
			if(_paintAlert){
				_paintAlert = false;
			}
				
		}


	}

	public void Up(){
		if(!_martMenuVisible){
			
			if(_gbs.map.isVisible()){
				_gbs.map.Up();
				return;
			}
			
			if(!_pcVisible && !completionCheck && !_attack.isRunning() && textTimer !=null && !textTimer.isRunning() && !_approachTimer.isRunning() && !escapeTimerEnter.isRunning() && !escapeTimerLeave.isRunning()){
		
				if(!_menuVisible){
		
					if(!_busy){
						this.avoidTheNPC(0, -1);
						if(NORTH && !_upForbid && _yIndex!=0){
			
							
							if(this._room._roomGrid != null){ 
								char next=this._room._roomGrid[_xIndex][_yIndex - 1];
									if((next == FREE || next == DOOR || next == GRASS || next == SEAMLESS || next == ROPE || next == CAVE || next==SLIDE_LEFT ||next==SLIDE_DOWN ||next==SLIDE_UP ||next==SLIDE_RIGHT)){
					
										if(_gbs.getSurf()){
											M2.playBG(this.getSong());
										}
										this.Move(MOVEUP);
										this._gbs.setSurf(false);				
									}
							}
							
							//FOR SURFING.
							if(this._room._roomGrid != null && this._gbs.getSurf() && this._room._roomGrid[_xIndex][_yIndex - 1] == SURF && !this._gbs.getMode()){
								
								this.Move(MOVEUP);
								this._gbs.setSurf(true);
					
							}

						}
						else{
							this._gbs.getPlayer().faceUp();
							this.repaint();
							NORTH = true;
							SOUTH = EAST = WEST = false;
						}
					}
				}
		
				else{
					if(getCurrentMenu() != null && !(getCurrentMenu().isVisible())){
						if(_menuCursor.y != 23){
							_menuCursor.y = _menuCursor.y - 25;
							this.repaint();
						}
			
					}
					else{
						if(_gbs.pbs.isVisible()){
							_gbs.pbs.Up();
						}
						if(_gbs.ii.isVisible()){
							_gbs.ii.Up();
						}
						if(_gbs.pds.isVisible()){
							_gbs.pds.Up();
						}
					}
				}
			}
			else{
				boolean b = false;
				for(int i = 0; i < _menuTextVisible.length; i++){
					if(_menuTextVisible[i] || _bannerVisible || _myCoursesVisible){
						b = true;
					}
				}
				if(!b){
			
					if(_pcCursor.y != 123){
						_pcCursor.y = _pcCursor.y - 25;
				
						this.repaint();
					}
				}
				else{
					if(_bannerVisible){
						_gbs.bannerScreen.Up();
					}
					if(_myCoursesVisible){
						_gbs.pcBoxScreen.Up();
					}
				}
			
			
			}
		}
		else{
			if(buyMenu || sellMenu){
				_gbs.mart.Up();
			}
			else{
				if(_martCursor.y>15){
					_martCursor.y=_martCursor.y-20;
				}
			}
		}
	}
	

	public void Down(){
		if(!_martMenuVisible){

			if(_gbs.map.isVisible()){
				_gbs.map.Down();
				return;
			}
			
			if(!_pcVisible && !completionCheck && !_attack.isRunning() && textTimer !=null && !textTimer.isRunning() && _yIndex+1!=this._room._roomGrid[0].length && !_approachTimer.isRunning() && !escapeTimerEnter.isRunning() && !escapeTimerLeave.isRunning()){
		
				if(!_menuVisible){
					if(!_busy){
						this.avoidTheNPC(0, 1);
						if(SOUTH && !_downForbid){
							
							
							if(this._room._roomGrid != null){
								char next=this._room._roomGrid[_xIndex][_yIndex + 1];
									if((next == FREE || next == DOOR || next == GRASS || next == VERTICAL || next == SEAMLESS || next == ROPE || next == CAVE || next==SLIDE_LEFT ||next==SLIDE_DOWN ||next==SLIDE_UP ||next==SLIDE_RIGHT)){
								
										if(_gbs.getSurf()){
											M2.playBG(this.getSong());
										}
										this.Move(MOVEDOWN);
										this._gbs.setSurf(false);
									}
							}
							
							//FOR SURFING.
							if(this._room._roomGrid != null && this._gbs.getSurf() && this._room._roomGrid[_xIndex][_yIndex + 1] == SURF && !this._gbs.getMode()){
								
								this.Move(MOVEDOWN);
								this._gbs.setSurf(true);
								
					
							}
						}
						else{
							this._gbs.getPlayer().faceDown();
							this.repaint();
							SOUTH = true;
							NORTH = EAST = WEST = false;
						}
					}
				}
				else{
					if(getCurrentMenu() != null && !(getCurrentMenu().isVisible())){
						if(_menuCursor.y != 173){
							_menuCursor.y += 25;
							this.repaint();
			
			
						}
				
					}
					else{
						if(_gbs.pbs.isVisible()){
							_gbs.pbs.Down();
						}
						if(_gbs.ii.isVisible()){
							_gbs.ii.Down();
						}
						if(_gbs.pds.isVisible()){
							_gbs.pds.Down();
						}
					}
				}
			}
			else{
		
			
				boolean b = false;
				for(int i = 0; i < _menuTextVisible.length; i++){
					if(_menuTextVisible[i] || _bannerVisible || _myCoursesVisible){
						b = true;
					}
				}
				if(!b){
			
					if(_pcCursor.y != 173){
						_pcCursor.y += 25;
				
						this.repaint();
					}
				}
				else{
					if(_bannerVisible){
						_gbs.bannerScreen.Down();
					}
					if(_myCoursesVisible){
						_gbs.pcBoxScreen.Down();
					}
				}
			
			
			
			}
		}
		else{
			if(buyMenu || sellMenu){
				_gbs.mart.Down();
			}
			else{
				if(_martCursor.y<55){
					_martCursor.y=_martCursor.y+20;
				}
			}
		}
	}

	public void Right(){
		if(!_martMenuVisible){
			
			if(_gbs.map.isVisible()){
				_gbs.map.Right();
				return;
			}
			
			if(!_menuVisible && !completionCheck&& !_attack.isRunning() && textTimer !=null && !textTimer.isRunning() && !_approachTimer.isRunning() && !escapeTimerEnter.isRunning() && !escapeTimerLeave.isRunning()){
				if(!_busy){
					this.avoidTheNPC(1, 0);
					if(EAST && !_rightForbid && _xIndex+1!=this._room._roomGrid.length){
						
						
						if(this._room._roomGrid != null){
							char next=this._room._roomGrid[_xIndex+1][_yIndex];
							if((next == FREE || next == DOOR || next == GRASS || next == RIGHT_LEDGE|| next == SEAMLESS || next == ROPE || next == CAVE || next==SLIDE_LEFT ||next==SLIDE_DOWN ||next==SLIDE_UP ||next==SLIDE_RIGHT)){
								if(_gbs.getSurf()){
									M2.playBG(this.getSong());
								}
								this.Move(MOVERIGHT);
								this._gbs.setSurf(false);
							}
						
						}
						
						//FOR SURFING.
						if(this._room._roomGrid != null && this._gbs.getSurf() && this._room._roomGrid[_xIndex + 1][_yIndex] == SURF && !this._gbs.getMode()){
							
							this.Move(MOVERIGHT);
							this._gbs.setSurf(true);
				
						}
					}
					else{
						this._gbs.getPlayer().faceRight();
						this.repaint();
						EAST = true;
						NORTH = SOUTH = WEST = false;
					}
				}
			}
			
			else{
				if(getCurrentMenu() != null && getCurrentMenu().isVisible()){
					if(_gbs.ii.isVisible()){
						_gbs.ii.Right();
					}
					if(_gbs.pds.isVisible()){
						_gbs.pds.Right();
					}
				}
			}
		}
		else{
			if(buyMenu || sellMenu){
				_gbs.mart.Right();
			}
		}
	}

	public void Left(){
		if(!_martMenuVisible){
			
			if(_gbs.map.isVisible()){
				_gbs.map.Left();
				return;
			}
			
			if(!_menuVisible && !completionCheck && !_attack.isRunning()&& textTimer !=null && !textTimer.isRunning() && !_approachTimer.isRunning() && !escapeTimerEnter.isRunning() && !escapeTimerLeave.isRunning()){
				if(!_busy){
					
					this.avoidTheNPC(-1, 0);
					if(WEST && !_leftForbid && _xIndex!=0){
						
	
						if(this._room._roomGrid != null){
							char next=this._room._roomGrid[_xIndex-1][_yIndex];
							if((next == FREE || next == DOOR || next == GRASS || next == LEFT_LEDGE || next == SEAMLESS || next == ROPE || next == CAVE || next==SLIDE_LEFT ||next==SLIDE_DOWN ||next==SLIDE_UP ||next==SLIDE_RIGHT)){
						
								if(_gbs.getSurf()){
									M2.playBG(this.getSong());
								}
								this.Move(MOVELEFT);
								this._gbs.setSurf(false);
							}
						}
						
						//FOR SURFING.
						if(this._room._roomGrid != null && this._gbs.getSurf() && this._room._roomGrid[_xIndex - 1][_yIndex] == SURF && !this._gbs.getMode()){
							
							this.Move(MOVELEFT);
							this._gbs.setSurf(true);
				
						}
					}
					else{
						this._gbs.getPlayer().faceLeft();
						this.repaint();
						WEST = true;
						NORTH = SOUTH = EAST = false;
					}
				}
			}
			
			else{
				if(getCurrentMenu() != null && getCurrentMenu().isVisible()){
					if(_gbs.ii.isVisible()){
						_gbs.ii.Left();
					}
					else if(_gbs.pds.isVisible()){
						_gbs.pds.Left();
					}					
				}	
			}
		}
		else{
			if(buyMenu || sellMenu){
				_gbs.mart.Left();
			}
		}
	}
	

	public void drawCutTick(int tick) throws IOException{
		int x = this.getMovingTrainers().get(_interruptedTrainer).getXSpace();
		int y = this.getMovingTrainers().get(_interruptedTrainer).getYSpace();
		BufferedImage cutImage=null;
	
		if(tick != 0){
			if(this.getMovingTrainers().get(_interruptedTrainer).getName()=="CutCan"){
				cutImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/CutCan/Animation/" + tick + ".png"));
			}
			else if(this.getMovingTrainers().get(_interruptedTrainer).getName()=="CutBush"){
				cutImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/CutBush/Animation/" + tick + ".png"));
			}
			
		}

		g2.drawImage(cutImage, null, x+xConstant+this._xSpace,y+yConstant+this._ySpace);
		//this.repaint();
	}
	
	
	public void drawTeleTick(int tick) throws IOException{
		BufferedImage teleImage=null;
	
		if(tick != 0){
			teleImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/Teleport/" + tick + ".png"));	
		}

		g2.drawImage(teleImage, null, 176,168);
		//this.repaint();
	}
	
	public void drawDigTick(int tick) throws IOException{
		BufferedImage digImage=null;
	
		if(tick != 0){
			if(_gbs._escapeRope){
				digImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/EscapeRope/" + tick + ".png"));
			}
			else{
				digImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/Dig/" + tick + ".png"));
			}
		}

		g2.drawImage(digImage, null, 175,168);
		//this.repaint();
	}
	
	
	
	
	public void drawPlayer(Graphics2D g2){
		if(ledge || prevLedge){
			shadow.x = 177;
			shadow.y = 180;
			this.drawShadow(g2);
		}
		
		////MAINTAIN THIS ORDER.
		if((escapeTimerLeave.isRunning()||escapeTimerEnter.isRunning()) && (_gbs.isFlying() || _gbs.isTeleporting() || _gbs.isDigging())){
			//Actually, you don't do anything here. You let "drawAllGenerics" take care of it.
		}
		else if(this._gbs.getSurf()){
			g2.drawImage(_gbs.getPlayerImage(), null, 170, 168);
			//g2.drawImage(_gbs.getGrassImage(), null, 167, 168);
		}
		else{
			g2.drawImage(_gbs.getPlayerImage(), null, 176, 168-yLedge);
			
			if(yLedge==0 && this._room._roomGrid[this.xObs][this.yObs]==GRASS){
				if(_gbs.getMode()){
					g2.drawImage(_gbs.getGrassImage(), null, 175, 174);
				}
				else{
					g2.drawImage(_gbs.getGrassImage(), null, 174, 171);
				}
			}
		}	
	}
	
	public void drawShadow(Graphics2D g2){
		if(ledge && _timeCount%2 == 0 && shadow.height <= 10){
			shadow.height++;
			shadow.width++;
			////SysOut.print("GROW " + shadow.height);
		}
		if(prevLedge && _timeCount%2 == 0 && shadow.height > 0){
			shadow.height--;
			shadow.width--;
			////SysOut.print("SHRINK " + shadow.height);
		}
		
		g2.setColor(Color.BLACK);
		g2.fill(shadow);
	}
	
	public void drawPlayerCustom(Graphics2D g2, int x,int y){
		g2.drawImage(_gbs.getPlayerImage(), null, x, y);
	}
	
	public void drawPlayerVertReflection(Graphics2D g2, int x,int y){
		//int direction=this._direction;
		g2.drawImage(_playerReflection, null, x, y);
		//North
		if(NORTH && ((_timeCount>=0 && _timeCount<5)||(_timeCount>=10 && _timeCount<15))){
			_playerReflection=_gbs.getPlayer().getDownFaceImage();
		}
		else if(NORTH && _timeCount>=5 && _timeCount<10){
			_playerReflection=_gbs.getPlayer().getDownStep2Image();
		}
		else if(NORTH && _timeCount>=15 && _timeCount<=20){
			_playerReflection=_gbs.getPlayer().getDownStep1Image();
		}
		//East
		else if(EAST && ((_timeCount>=0 && _timeCount<5)||(_timeCount>=10 && _timeCount<15))){
			_playerReflection=_gbs.getPlayer().getRightFaceImage();
		}
		else if(EAST && _timeCount>=5 && _timeCount<10){
			_playerReflection=_gbs.getPlayer().getRightStep2Image();
		}
		else if(EAST && _timeCount>=15 && _timeCount<=20){
			_playerReflection=_gbs.getPlayer().getRightStep1Image();
		}
		//South
		else if(SOUTH && ((_timeCount>=0 && _timeCount<5)||(_timeCount>=10 && _timeCount<15))){
			_playerReflection=_gbs.getPlayer().getUpFaceImage();
		}
		else if(SOUTH && _timeCount>=5 && _timeCount<10){
			_playerReflection=_gbs.getPlayer().getUpStep2Image();
		}
		else if(SOUTH && _timeCount>=15 && _timeCount<=20){
			_playerReflection=_gbs.getPlayer().getUpStep1Image();
		}
		//West
		else if(WEST && ((_timeCount>=0 && _timeCount<5)||(_timeCount>=10 && _timeCount<15))){
			_playerReflection=_gbs.getPlayer().getLeftFaceImage();
		}
		else if(WEST && _timeCount>=5 && _timeCount<10){
			_playerReflection=_gbs.getPlayer().getLeftStep2Image();
		}
		else if(WEST && _timeCount>=15 && _timeCount<=20){
			_playerReflection=_gbs.getPlayer().getLeftStep1Image();
		}
		
	}
	
	public void drawNPCVertReflection(Graphics2D g2, int x,int y, Trainer trainer){
		BufferedImage _NPCReflection=null;
		int direction=trainer.getDirection();
		int npctick=_gbs.npctick;
		
		//if the trainer is moving. else just stationary pics.
		if(trainer.isMoving()){
		//North
			if(direction==2 && ((npctick>=0 && npctick<5)||(npctick>=10 && npctick<15))){
				_NPCReflection = trainer.getFrontImage();
			}
			else if(direction==2 && npctick>=5 && npctick<10){
				_NPCReflection = trainer.getMoveFrontImage2();
			}
			else if(direction==2 && npctick>=15 && npctick<=20){
				_NPCReflection = trainer.getMoveFrontImage1();
			}
		
		//North
			else if(direction==1 && ((npctick>=0 && npctick<5)||(npctick>=10 && npctick<15))){
				_NPCReflection = trainer.getLeftImage();
			}
			else if(direction==1 && npctick>=5 && npctick<10){
				_NPCReflection = trainer.getMoveLeftImage2();
			}
			else if(direction==1 && npctick>=15 && npctick<=20){
				_NPCReflection = trainer.getMoveLeftImage1();
			}
			
			//North
			else if(direction==0 && ((npctick>=0 && npctick<5)||(npctick>=10 && npctick<15))){
				_NPCReflection = trainer.getBackImage();
			}
			else if(direction==0 && npctick>=5 && npctick<10){
				_NPCReflection = trainer.getMoveBackImage2();
			}
			else if(direction==0 && npctick>=15 && npctick<=20){
				_NPCReflection = trainer.getMoveBackImage1();
			}
			
			//North
			else if(direction==3 && ((npctick>=0 && npctick<5)||(npctick>=10 && npctick<15))){
				_NPCReflection = trainer.getRightImage();
			}
			else if(direction==3 && npctick>=5 && npctick<10){
				_NPCReflection = trainer.getMoveRightImage2();
			}
			else if(direction==3 && npctick>=15 && npctick<=20){
				_NPCReflection = trainer.getMoveRightImage1();
			}
		}
		else{
			//If they're not currently walking.
			if(direction==2){
				_NPCReflection = trainer.getFrontImage();
			}
			else if(direction==3){
				_NPCReflection = trainer.getRightImage();
			}
			else if(direction==0){
				_NPCReflection = trainer.getBackImage();
			}
			else if(direction==1){
				_NPCReflection = trainer.getLeftImage();
			}
		}
		
		g2.drawImage(_NPCReflection, null, x, y);
	}
	
	
	public void drawAllTrainers(Graphics2D g2, int xConstant, int yConstant, Vector<Trainer> _movingTrainers){
		for (int i=0; i<_movingTrainers.size();i++){
			//If it isn't defeated OR if it is defeated but doesn't vanish:
			if(_movingTrainers.get(i) != null)
				if( ( !_movingTrainers.get(i).isDefeated() ) || (_movingTrainers.get(i).isDefeated() && !_movingTrainers.get(i).getVanishing()) ){
					g2.drawImage(_movingTrainers.get(i).getCurrentImage(),null,_movingTrainers.get(i).getXSpace()+xConstant+this._xSpace,_movingTrainers.get(i).getYSpace()+yConstant+this._ySpace);
					if(_movingTrainers.get(i)._onGrass){
						g2.drawImage(_movingTrainers.get(i).getGrassImage(), null, _movingTrainers.get(i).getXSpace()+xConstant+this._xSpace+0,_movingTrainers.get(i).getYSpace()+yConstant+this._ySpace+3);
					}
				}
		}
	}
	
	//May not look good with all trainers
	public void drawAllTrainersGraduating(Graphics2D g2, int xConstant, int yConstant, Vector<Trainer> _movingTrainers){
		for(int i = 0; i < _movingTrainers.size(); i++){
			g2.drawImage(_movingTrainers.get(i).getCurrentImage(), null, _movingTrainers.get(i).getXSpace()+xConstant+this._xSpace, _movingTrainers.get(i).getYSpace()+yConstant+this._ySpace);
			g2.drawImage(gradRobes, null, _movingTrainers.get(i).getXSpace()+xConstant+this._xSpace, _movingTrainers.get(i).getYSpace()+yConstant+this._ySpace);
		}
	}
	
	public Vector<Trainer> getMovingTrainers(){
		return this._movingTrainers;
	}
	
	
	public void drawPCMenuText(Graphics2D g2){
		if(_menuTextVisible[0]){
			this.drawText(g2, "Logged into Banner...");
		}
		if(_menuTextVisible[1]){
			this.drawText(g2, "Logged into MyCourses...","");
		}
	}
	
	public void drawNotification(Graphics2D g2){
	
		if(this.description != null){
			g2.setColor(Color.WHITE);
			_notifyFill.x = _gbs.notifyX;
			g2.fill(_notifyFill);
			
			g2.setColor(Color.BLACK);
			_notifyOutline.x = _gbs.notifyX + 1;
			g2.draw(_notifyOutline);
			
			g2.setFont(new Font("Courier New", Font.BOLD, 16));
			g2.drawString(this.description, _gbs.notifyX+ 5, 65);
		}
		
	}
	
	public void drawOptimalImage(Graphics2D g2, BufferedImage bi){
		g2.drawImage(bi,null,this._xSpace,this._ySpace);
//		try{
//			if(bi != null){
//				if(bi.getWidth()<=400 && bi.getHeight()<=400){
//					g2.drawImage(bi, null, this._xSpace, this._ySpace);	
//				}
//				else{
//					if(imgW == 0 && imgH == 0){
//						imgW = bi.getWidth();
//						imgH = bi.getHeight();
//						SysOut.print("OPTIMAL FOR " + _roomNum + " at " + imgW + "," + imgH);
//					}
//					g2.drawImage(bi.getSubimage(Math.max(0,-this._xSpace), Math.max(0,-this._ySpace), Math.min((imgW+this._xSpace), 500), Math.min((imgH+this._ySpace), 500)), null, Math.max(0,this._xSpace), Math.max(0,this._ySpace));
//					//g2.drawImage(bi, Math.max(0,-this._xSpace), Math.max(0,-this._ySpace), Math.min((imgW+this._xSpace), 500), Math.min((imgH+this._ySpace), 500), this);
//					//g2.drawImage(bi, this._xSpace, this._ySpace, this);
//				}
//			}
//			else{
//				g2.drawImage(bi, null, this._xSpace, this._ySpace);
//			}
//		}
//		catch(NullPointerException npe){
//			g2.drawImage(bi, null, this._xSpace, this._ySpace);
//			SysOut.print("NULL POINT EXCP THROWN.");			
//		}
//		catch(RasterFormatException rfe){
//			g2.drawImage(bi, null, this._xSpace, this._ySpace);
//			SysOut.print("RASTER EXCP THROWN.");
//		}
	}
	
	public void drawAllGenerics(Graphics2D g2){
		
		//This gives a null Pointer exception. Figure it out.
//		if(this._movingTrainers!=null){
//			this.drawAllTrainers(g2,this.xConstant,this.yConstant,this._movingTrainers);
//		}
		//Flash dim.
			if(this._flashOn){
				g2.drawImage(flashImage, null, -10, 0);
			}
			
			if(this._paintAlert && this.xObs==this._xIndex && this.yObs==this._yIndex){
				g2.drawImage(alertImage, null, this._movingTrainers.get(_interruptedTrainer).getXSpace()+this.xConstant+this._xSpace, this._movingTrainers.get(_interruptedTrainer).getYSpace()+this.yConstant+this._ySpace-15);
			}
			
			if(this._cutTimer.isRunning()){
				try {
					this.drawCutTick(_cutTick);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if((escapeTimerLeave.isRunning()||escapeTimerEnter.isRunning()) && (_gbs.isFlying()|| _gbs.isTeleporting() || _gbs.isDigging())){
				
				
				if(_gbs.isTeleporting()){
					try {
						this.drawTeleTick(_teleTick);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				else if(_gbs.isDigging()){
					try {
						this.drawDigTick(_digTick);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
				else if(_gbs.isFlying()){
					flyImage.paintIcon(this, g2, this.flyImageX, this.flyImageY);
				}
			}
			
			if(this._cutTimer.isRunning()){
				
			}
			
			
			
		if(poisonVisible){
			this.drawAutoWrapText(g2, _gbs.getPlayer().getActivePokemon(psnVictim).getName() + " was cured of its Poison status!");
		}
		
		if(repelVisible){
			this.drawAutoWrapText(g2, "Repel's effects wore off.");
		}
		
		if(this._menuVisible)
			this.drawMenu(g2);
		else
			this.drawNotification(g2);
		
		if(this._pcVisible){
			this.drawPC(g2);
		}

		this.drawPCMenuText(g2);
		
		if(this._martMenuVisible)
			this.drawMartMenu(g2);
	
		if(this.hmTextVisible){
			this.drawHMnotify(g2);
		}
		
		if(_dialogueVisible){
			this.drawDialogue(g2);
		}

		if(this.mapVisible)	
			g2.drawImage(mapImage, null, 0, 0);
		
		
		
		
		//This needs to be last.
		this.drawBox(g2);
		
		
		//g2.dispose();
			
	}
	
	public void resetNTimer(){
		_gbs.notifyX = 385;
		_gbs.ntick = 0;
		_gbs.stopNTimer();
	}
	
	public void drawText(Graphics2D g2, String line1){
		this.drawText(g2, line1, "");
	}
	
	public void drawAutoWrapText(Graphics2D g2, String desc){
			//Draw Description
			int wrap=35;
			String firstLine="";
			String secondLine="";
			
			if (desc.length()<=wrap){
				firstLine=desc;
				secondLine="";
			}
			else{
				String firstHalf=desc.substring(0,wrap);
				int breakPoint=firstHalf.lastIndexOf(" ");
				firstLine=desc.substring(0, breakPoint);
				secondLine=(desc.substring(breakPoint+1));
			    
			}
			
			this.drawText(g2, firstLine, secondLine);
	}
	
	public void drawText(Graphics2D g2, String line1, String line2){
		
		Rectangle2D.Double textBG = new Rectangle2D.Double();
		textBG.x = 0;
		textBG.y = 315;
		textBG.width = this.getWidth();
		textBG.height = 50;
		
		g2.setColor(Color.WHITE);
		g2.fill(textBG);
		
		Rectangle2D.Double outline = new Rectangle2D.Double();
		outline.x = 5;
		outline.y = 320;
		outline.width = this.getWidth() - 10;
		outline.height = 40;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.draw(outline);
		
		g2.setFont(_gbs.getFont());
		
		completionCheck = false;
		
		if(cfirst != null && csecond != null){			
			completionCheck = (s1.equals(line1) &&  s2.equals(line2));
		}
		
		if(!textTimer.isRunning() && !completionCheck){
			this.displayText(line1, line2);
		}

		g2.drawString(s1, 20, 335);
		g2.drawString(s2, 20, 355);

	}
	
	public M2 getSong(){
		return song;
	}
	
	public void displayText(String firstLine, String secondLine){
		cfirst = firstLine.toCharArray();
		csecond = null;

		s1 = ""; s2 = "";
		if(secondLine != null)
			csecond = secondLine.toCharArray();
		
		if(textTimer != null && !textTimer.isRunning()){

			textTimer.start();
		}
	}
	
	public void setCurrentText(String firstLine, String secondLine){
		s1 = firstLine;
		s2 = secondLine;
	}
	
	public void setString(char c, boolean oneORtwo){
		if(oneORtwo)
			s1 = s1 + c;
		else
			s2 = s2 + c;
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
					
					textTimer.stop();

					//t = new Timer(35, cl);
					
					charTick = 0;
					textTimer.setDelay(50);
					return;
				}
			}

			charTick++;
			//_this.repaint();
		}
	}
	
	public void drawKorean(Graphics2D g2, BufferedImage kText){
			g2.drawImage(kText, null, 0, 315);	
	}
	
	public void drawHMnotify(Graphics2D g2){
		if (_canSurf==0){
			this.drawText(g2, "The water is a deep blue color.", "");
		}
		if (_canSurf==-1){
			this.drawText(g2, "A Pokemon may be able to surf on it.", "");
		}
		if (_canSurf==1){
			this.drawText(g2, "Would you like to use Surf?", "A = Yes      B = No ");
		}
		if (_canSurf==2){
			this.drawText(g2, _hmSlaveName + " used Surf!", "");
		}

		
	}
	
	public void drawDialogue(Graphics2D g2){
		if(this._movingTrainers.get(this._interruptedTrainer).getDialogue()!=null && _NPCpage<this._movingTrainers.get(this._interruptedTrainer).getDialogue().size()){
			
			String desc=this._movingTrainers.get(this._interruptedTrainer).getLineOfDialogue(_NPCpage);//getDialogue().get(_NPCpage);
			if(_receivedItem){
				if(this._movingTrainers.get(this._interruptedTrainer).getName()=="Item"){
					this.drawText(g2,_gbs.getPlayer().getPlayerName()+ " found a "+ this.receivedGiftName+"!","");	
				}
				else{
					this.drawText(g2,_gbs.getPlayer().getPlayerName()+ " received "+ this.receivedGiftName+"!","");
				}
			}
			else{
				//Draw Description
				int wrap=35;
				if (desc.length()<=wrap){
					this.drawText(g2,desc,"");
				}
				else{
					String firstHalf=desc.substring(0,wrap);
					int breakPoint=firstHalf.lastIndexOf(" ");
					this.drawText(g2,desc.substring(0, breakPoint),desc.substring(breakPoint+1));
				}
			}
		}
	}
	
	public void drawMenu(Graphics2D g2){
		
		Rectangle2D.Double mainMenu = new Rectangle2D.Double();
		mainMenu.x = 275;
		mainMenu.y = 0;
		mainMenu.width = 110;
		mainMenu.height = 200;
		
		g2.setColor(Color.WHITE);
		g2.fill(mainMenu);
		
		Rectangle2D.Double outline = new Rectangle2D.Double();
		outline.x = 280;
		outline.y = 5;
		outline.width = 100;
		outline.height = 190;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new  BasicStroke(2));
		g2.draw(outline);
		
		g2.draw(_menuCursor);
		
		g2.setFont(_gbs.getFont());
		if(_gbs.getPlayer().getPokedex() != null)
			g2.drawString("POKeDEX", 305, 10+20);
		
		if(_gbs.getPlayer()._activePokemon.size() > 0){
			g2.drawString("POKeMON", 305, 35+20);
			g2.drawString("ITEM", 305, 60+20);
		}
		g2.drawString(_gbs.getPlayer().getPlayerName(), 305, 85+20);
		g2.drawString("SAVE", 305, 110+20);
		
		if(_gbs.getPlayer().hasTownMap())
			g2.drawString("MAP", 305, 135+20);
			
		g2.drawString("EXIT", 305, 160+20);
	}
	
	public void drawItemSelectMenu(Graphics2D g2){
		
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
			g2.drawString("x" + currentItem.getRemaining(), 300, 110);
			
			//For battle unusable items.
			if(!currentItem.isBattleAppropriate()){
				g2.setColor(Color.RED);
				g2.drawString("Cannot be used in battle.", 60, 140);
				g2.setColor(Color.BLACK);
			}
			else{
				//No warning thrown.
			}
			
			//Compatible with battle?
			
			//Commands, A yes, B no.
			g2.drawString("A = Use     B = Return to Item Menu", 20, 200);
			
	}
	
	public void drawMartMenu(Graphics2D g2){
		Rectangle2D.Double martMenu = new Rectangle2D.Double();
		martMenu.width = 100;
		martMenu.height = 70;
		martMenu.x = 0;
		martMenu.y = 0;
		
		g2.setColor(Color.WHITE);
		g2.fill(martMenu);
		
		Rectangle2D.Double martMenuO = new Rectangle2D.Double();
		martMenuO.width = 90;
		martMenuO.height = 60;
		martMenuO.x = 5;
		martMenuO.y = 5;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.draw(martMenuO);
		
		g2.draw(_martCursor);
		
		g2.setFont(_gbs.getFont());
		
		g2.drawString("BUY", 25, 21);
		g2.drawString("SELL", 25, 41);
		g2.drawString("QUIT", 25, 61);
		
		
		Rectangle2D.Double moneyDisplay = new Rectangle2D.Double();
		moneyDisplay.height = 30;
		moneyDisplay.width = 100;
		moneyDisplay.x = 20;
		moneyDisplay.y = 150; 
		
		g2.setColor(Color.WHITE);
		g2.fill(moneyDisplay);
		
		Rectangle2D.Double moneyDisplayO = new Rectangle2D.Double();
		moneyDisplayO.height = 20;
		moneyDisplayO.width = 90;
		moneyDisplayO.x = 25;
		moneyDisplayO.y = 155;
		
		g2.setColor(Color.BLACK);
		g2.draw(moneyDisplayO);
		
		g2.drawString("$" + _gbs.getPlayer()._money, 30, 170);
	}
	
	public void drawPC(Graphics2D g2){
		
		Rectangle2D.Double pcMain = new Rectangle2D.Double();
		pcMain.width = 170;
		pcMain.height = 100;
		pcMain.x = 0;
		pcMain.y = 100;
		
		g2.setColor(Color.WHITE);
		g2.fill(pcMain);
		
		Rectangle2D.Double pcOut = new Rectangle2D.Double();
		pcOut.width = 160;
		pcOut.height = 90;
		pcOut.x = 5;
		pcOut.y = 105;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.draw(pcOut);
		
		g2.draw(_pcCursor);
		
		g2.setFont(new Font("Courier New", Font.BOLD, 16));
		g2.drawString("Deposit PKMN", 25, 130);
		g2.drawString("Withdraw PKMN", 25, 155);
		g2.drawString("LOG OFF", 25, 180);
	}
	

	public void drawBox(Graphics2D g2){	
		if(_darkLevel < 0) _darkLevel = 0;
		if(_darkLevel > 240) _darkLevel = 240;
	
		g2.setColor(new Color(0,0,0,_darkLevel));
		g2.fill(_blackBox);
		
		
		if(_whiteLevel < 0) _whiteLevel = 0;
		if(_whiteLevel > 240) _whiteLevel = 240;
		g2.setColor(new Color(255,255,255,_whiteLevel));
		g2.fill(_whiteBox);
	}
	
	public void setLocation(int xSpace, int ySpace){
		this._xSpace = xSpace;
		this._ySpace = ySpace;
	}

	public void setIndices(int xInd, int yInd){
		this._xIndex = xInd;
		this._yIndex = yInd;
		this.xObs = xInd;
		this.yObs = yInd;
	}

	public void setupBattle(Pokemon ally, Pokemon enemy, boolean battleType, int battleLoc){
			
		//Give the wild pokemon a genetic identity :\
		Pokemon.generateIV(enemy);
		Pokemon.generateNewStats(enemy);
	
		_gbs.getBattlePanel().initializeBattle(_gbs.getPlayer(), enemy, battleLoc);
		
		_gbs.getBattlePanel().repaint();
	}
	
	public void setupBattle(Trainer t, int location, int battleLoc){
		
		for(int i = 0; i < t.getBelt().size(); i++){
			Pokemon.generateIV(t.getBelt().get(i));
			Pokemon.generateNewStats(t.getBelt().get(i));
		}
		
		try {
			_gbs.getBattlePanel().initializeBattle(_gbs.getPlayer(), t, battleLoc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_gbs.getBattlePanel().repaint();
	}
	

	public void wild(){
		// Will eventually use flaXshing black screen
	}
	public void waterWild(){
		// Will eventually use flaXshing black screen
		this.wild(); //In case this method is not overridden.
	}

	public void trainerBattle(int trainerNum){
		//SysOut.print("TRAINER BATTLE!!!");
		//SysOut.print("Battle BG:" + this.getBattleBG());
		this.setupBattle(this._movingTrainers.get(trainerNum), this.getBattleBG(), this._roomNum);
		_gbs.setCurrentPanel(-1);

		_gbs.repaint();
	}
	
	public void legendaryBattle(int trainerNum){
		Pokemon ally = _gbs.getPlayer().getActivePokemon(0);
		this.setupBattle(ally, this._movingTrainers.get(trainerNum).legendary, false, this._roomNum);
		_gbs.setCurrentPanel(-1);
		_gbs.repaint();
	}
	
	public void afterBattle(){
		Trainer moving=this._movingTrainers.get(_interruptedTrainer);
		
		SysOut.print("Step 2 is called. " + _interruptedTrainer);
		if(moving.getGift()!=null || (moving.getGift()==null && moving.getPostBattleDialogue().size()>0)){
			//Post Battle dialogue.
			if (moving.getPostBattleDialogue().size()>0){
				moving.getDialogue().clear();
				for(int i =0; i<moving.getPostBattleDialogue().size();i++){
					moving.getDialogue().add(moving.getPostBattleDialogue().get(i));
				}
			}
			else{
				moving.getDialogue().clear();
				moving.getDialogue().add(moving.getDefeatDialogue());
			}
			
			moving.defeat();
			this.A_Button();
			
			SysOut.print("Step 3 is called, BUT SHOULD NOT BE.");
		}
		else if (moving.getPostBattleDialogue()==null || moving.getGift()==null){
			//Escape the trap if theres nothing to talk about.
			moving.defeat();
			moving.getDialogue().clear();
			moving.getDialogue().add(moving.getDefeatDialogue());
			SysOut.print("Step 4 is called, we're good to go.");
		}
		
		//If shit in post-battle events starts going awry, delete this.
		this.scanForAllEvents();
	}
	
	
	public int getBattleBG(){
		
		return _battleBG;
	}
	
	public void setBattleBG(int bg){
		
		_battleBG=bg;
	}
	
	public void customTeleportTo(int roomNum, int xIndex, int yIndex){
		customTeleporting=true;
		customTeleportX=xIndex;
		_gbs.setTeleport(true);
		customTeleportY=yIndex;
		customTeleportRoomNum=roomNum;
		this.escapeTimerLeave.start();
		
		//TODO CHANGE TO SCILI ROOF
		if(roomNum == _gbs.BARHOL_HALLWAY){
			_gbs.getPanel(_gbs.BARHOL_LAB).A_Button();
			_gbs.getPanel(_gbs.BARHOL_LAB)._NPCpage = 0;
			_gbs.getPanel(_gbs.BARHOL_LAB).repaint();
			SysOut.print("Text cleared???");
		}
	}
	
	public void checkFloorTile(){
		_walkTimer.stop();
		_timeCount = 0;
		psnStep++;
		
		_busy = false;
		char here=_this._room._roomGrid[_xIndex][_yIndex];
		
		if(here == DOOR){
			_this.enterRoom(_xIndex, _yIndex);
		}
		
		else if(here==SLIDE_LEFT ||here==SLIDE_DOWN ||here==SLIDE_UP ||here==SLIDE_RIGHT){
			_this.slidePlayer(here);
		}

		else if(here == SEAMLESS){
			_this.enterRoomSeamless(_xIndex, _yIndex);
		}
		
		else if(here == GRASS || here == CAVE || here == SURF){
			SysOut.print("Probability = " + this.PROBABILITY);
			int random = (int)(this.PROBABILITY*Math.random());
			if(random  == 0 && !this.inLineOfSight() && repelCount==0 && !repelVisible){
				_this._busy = true;
				wild = true;
				_attack.start();
				if(!_gbs.isMuted()){
//					_gbs.playSong("Music/Background/battle.mid", GameBoyScreen.BG, false);
				}
			}
		}
		

	}

	private class UpTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			_this._ySpace++;
			_this._busy = true;
			
			if(_timeCount == 1){
				yObs--;
				if(_bikeCycle==1){
					_bikeCycle=2;
				}
				else{
					_bikeCycle=1;
				}
				
				
				if(_this._room._roomGrid[_xIndex][_yIndex-1] == DOOR){
					_darkLevel = 0;
					_fadeToBlack.start();
				}
				
				if(psnStep == 4 && _gbs.getPlayer().isAnyPSN()){
					if(!_gbs.isMuted()){
						M2.playFX(M2.POISON_DAMAGE);
					}
				}
			}
			
			if(_timeCount == 5){
				_gbs.getPlayer().stepUp1();
			}
			if(_timeCount == 5 && psnStep == 4 && _gbs.getPlayer().isAnyPSN()){
				psnTimer.start();
				//if(music...
			}
			if(_timeCount == 10){
				_gbs.getPlayer().faceUp();
			}
			if(_timeCount == 15 && psnStep == 4){
				psnStep = 0;
				if(psnTimer.isRunning()){
					psnTimer.stop();
					_darkLevel = 0;
					psnVictim =_gbs.getPlayer().damageAllPSN();
					if(psnVictim>=0){
						poisonVisible=true;
						//repaint();
					}
				}
			}
			if(_timeCount == 15){
				_gbs.getPlayer().stepUp2();
			}
			if(_timeCount == 20){
				_gbs.getPlayer().faceUp();
				_yIndex--;
				
				checkFloorTile();
				if(repelCount>0){
					repelCount--;
					SysOut.print("Repel:" + repelCount);
				}
				if(repelCount==0 && repelling){
					repelVisible=true;
					repelling = false;
				}
				
				//autoSave();
			}
			
		
		//	_this.repaint();
		}
	}
	
	private class DownTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			_this._ySpace--;
			_this._busy = true;
			
			if(prevLedge && _timeCount%2 == 0){
				yLedge--;
				if(_timeCount == 20){
					prevLedge = false;
				}
			}
			
			if(ledge && _timeCount%2 == 0){
					yLedge++;
					if(_timeCount == 20){
						ledge = false;
						prevLedge = true;
					}
			}
			
			if(_timeCount == 1){
				yObs++;
				if(_bikeCycle==1){
					_bikeCycle=2;
				}
				else{
					_bikeCycle=1;
				}
				
				if(_this._room._roomGrid[_xIndex][_yIndex+1] == DOOR){
					_darkLevel = 0;
					_fadeToBlack.start();
				}
				
				if(_this._room._roomGrid[_xIndex][_yIndex+1] == VERTICAL || _this._room._roomGrid[_xIndex][_yIndex+1] == ROPE){
					ledge = true;
					M2.playFX(M2.LEDGE);
				}
				
				if(psnStep == 4 && _gbs.getPlayer().isAnyPSN()){
					if(!_gbs.isMuted()){
						M2.playFX(M2.POISON_DAMAGE);
					}
				}
			}
			
			
			if(_timeCount == 5){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().stepDown1();
				else if(ledge){
					_gbs.getPlayer().jumpDown();
				}
			}
			if(_timeCount == 5 && psnStep == 4 && _gbs.getPlayer().isAnyPSN()){
				psnTimer.start();
				//if(music...
			}
			if(_timeCount == 10){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().faceDown();
			}
			if(_timeCount == 15 && psnStep == 4){
				psnStep = 0;
				if(psnTimer.isRunning()){
					psnTimer.stop();
					_darkLevel = 0;
					psnVictim =_gbs.getPlayer().damageAllPSN();
					if(psnVictim>=0){
						poisonVisible=true;
					//	repaint();
					}
					
				}
			}
			if(_timeCount == 15){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().stepDown2();
			}
			if(_timeCount == 20){
				_gbs.getPlayer().faceDown();
				_yIndex++;
				
				checkFloorTile();
				if(repelCount>0){
					repelCount--;
					SysOut.print("Repel:" + repelCount);
				}
				if(repelCount==0 && repelling){
					repelVisible=true;
					repelling = false;
				}
				
				if(prevLedge){
					//_this.Down();
					_this.Down();
				}
				
				//autoSave();
			}
			

		//	_this.repaint();
		}
	}
	
	
	private class RightTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			_this._xSpace--;
			_this._busy = true;
			
			if(prevLedge && _timeCount%2 == 0){
				yLedge--;
				if(_timeCount == 20){
					prevLedge = false;
				}
			}
			
			if(ledge && _timeCount%2 == 0){
					yLedge++;
					if(_timeCount == 20){
						ledge = false;
						prevLedge = true;
					}
			}
			
			if(_timeCount == 1){
				xObs++;
				if(_bikeCycle==1){
					_bikeCycle=2;
				}
				else{
					_bikeCycle=1;
				}
				
				if(_this._room._roomGrid[_xIndex+1][_yIndex] == DOOR){
					_darkLevel = 0;
					_fadeToBlack.start();
				}
				
				if(_this._room._roomGrid[_xIndex+1][_yIndex] == RIGHT_LEDGE || _this._room._roomGrid[_xIndex+1][_yIndex] == ROPE){
					ledge = true;
					//SysOut.print("ledge");
					M2.playFX(M2.LEDGE);
				}
			
				
				if(psnStep == 4 && _gbs.getPlayer().isAnyPSN()){
					if(!_gbs.isMuted()){
						M2.playFX(M2.POISON_DAMAGE);
					}
				}
			}
			
			if(_timeCount == 5){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().stepRight1();
				else if(ledge){
					_gbs.getPlayer().jumpRight();
				}
			}
			if(_timeCount == 5 && psnStep == 4 && _gbs.getPlayer().isAnyPSN()){
				psnTimer.start();
				//if(music...
			}
			if(_timeCount == 10){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().faceRight();
			}
			if(_timeCount == 15 && psnStep == 4){
				psnStep = 0;
				if(psnTimer.isRunning()){
					psnTimer.stop();
					_darkLevel = 0;
					psnVictim =_gbs.getPlayer().damageAllPSN();
					if(psnVictim>=0){
						poisonVisible=true;
						//repaint();
					}
				}
			}
			if(_timeCount == 15){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().stepRight2();
			}
			if(_timeCount == 20){
				_gbs.getPlayer().faceRight();
				_xIndex++;
				
				checkFloorTile();
				if(repelCount>0){
					repelCount--;
					SysOut.print("Repel:" + repelCount);
				}
				if(repelCount==0 && repelling){
					repelVisible=true;
					repelling = false;
				}
				
				if(prevLedge){
					//_this.Down();
					_this.Right();
				}
				
				//autoSave();
			}
			

		//	_this.repaint();
		}
	}
	
	//
	private class LeftTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			_this._xSpace++;
			_this._busy = true;

			if(prevLedge && _timeCount%2 == 0){
				yLedge--;
				if(_timeCount == 20){
					prevLedge = false;
				}
			}
			
			if(ledge && _timeCount%2 == 0){
					yLedge++;
					if(_timeCount == 20){
						ledge = false;
						prevLedge = true;
					}
			}
			
			if(_timeCount == 1){
				xObs--;
				
				if(_bikeCycle==1){
					_bikeCycle=2;
				}
				else{
					_bikeCycle=1;
				}
				
				
				if(_this._room._roomGrid[_xIndex-1][_yIndex] == DOOR){
					_darkLevel = 0;
					_fadeToBlack.start();
					
				}
				
				if(_this._room._roomGrid[_xIndex-1][_yIndex] == LEFT_LEDGE || _this._room._roomGrid[_xIndex-1][_yIndex] == ROPE){
					ledge = true;
					M2.playFX(M2.LEDGE);
				}
				
	
				
				if(psnStep == 4 && _gbs.getPlayer().isAnyPSN()){
					if(!_gbs.isMuted()){
						M2.playFX(M2.POISON_DAMAGE);
					}
				}
			}
			
			if(_timeCount == 5){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().stepLeft1();
				else if(ledge){
					_gbs.getPlayer().jumpLeft();
				}
			}
			if(_timeCount == 5 && psnStep == 4 && _gbs.getPlayer().isAnyPSN()){
				psnTimer.start();				
			}
			if(_timeCount == 10){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().faceLeft();
			}
			if(_timeCount == 15 && psnStep == 4){
				psnStep = 0;
				if(psnTimer.isRunning()){
					psnTimer.stop();
					_darkLevel = 0;
					psnVictim =_gbs.getPlayer().damageAllPSN();
					if(psnVictim>=0){
						poisonVisible=true;
					//	repaint();
					}
				}
			}
			if(_timeCount == 15){
				if(!ledge && !prevLedge)
					_gbs.getPlayer().stepLeft2();
			}
			if(_timeCount == 20){
				_gbs.getPlayer().faceLeft();
				_xIndex--;
				
				checkFloorTile();
				if(repelCount>0){
					repelCount--;
					//SysOut.print("Repel:" + repelCount);
				}
				if(repelCount==0 && repelling){
					repelVisible=true;
					repelling = false;
				}
				
				
				if(prevLedge){
					//_this.Down();
					_this.Left();
				}
				
				//autoSave();
			}
			

		//	_this.repaint();
		}
	}
	
	public void flash(){
		//TODO: Fix Flash
		if(_gettingDark){
			_darkLevel+=24;
			if(_darkLevel >= 240){
				_darkLevel = 240;
				_gettingDark = false;
				_gettingBright = true;
			}
		}
		else if(_gettingBright){
			_darkLevel-=24;
			if(_darkLevel <= 0){
				_darkLevel = 0;
				_gettingDark = true;
				_gettingBright = false;
			}
		}
		
		//repaint();
	}
	
	public class FadeToBlack implements ActionListener{
		public void actionPerformed(ActionEvent e){			
			_this.flash();
			
			if(_darkLevel == 240){
				_fadeToBlack.stop();
			}

			////SysOut.print("Fading to black:" + _darkLevel);
		}
	}
	
	public class FadeUpTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			_this.flash();
			////SysOut.print("Fading up: " + _darkLevel);
			////SysOut.print("Fading up @ time: " + System.currentTimeMillis());
			if(_darkLevel == 0){
				_fadeUp.stop();
			}
		}
	}
	
	public class HealTimer implements ActionListener{
	
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			_healed = false;
			
			if(_timeCount == 1 && M2.HEALING != M2.getCurrentFX()){
				M2.playFX(M2.HEALING);
			}
			
			_this.flash();
			
			if(_timeCount == 10 || _darkLevel == 0){
				_healed = true;
				_gbs.getPlayer().healAllActive();
				_gbs.getPlayer().setPkmnCenter(_this._roomNum);
				_heal.stop();
//				_darkening = true;
//				_darkness = 0;
//				
				_timeCount = 0;
				A_Button();
//				if(_roomNum == 2)
//					_textVisible[0] = true;
//				
			//	_this.repaint();
			}
		}
	}
	
	public class CutTimer implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			
			if(_timeCount == 1){
				SysOut.print("CUT START");
				_busy=true;
				M2.playFX(M2.CUT);
			}
			
			//Tick every 2 ms.
			if(Math.ceil(_timeCount/2.0)-Math.floor(_timeCount/2.0)==0){
				_cutTick++;
			//	_this.repaint();
			}
			
			//There are 58 total ticks for bush, 64 for can.
			if((_timeCount == 58 && cutBush) || (_timeCount == 64 && cutCan)){
				_cutTimer.stop();
				SysOut.print("CUT ENDS");
				_busy=false;
				_timeCount = 0;
				cutBush=false;
				cutCan=false;
				_cutTick=0;
				if(_this.getMovingTrainers()!=null && _this.getMovingTrainers().get(_interruptedTrainer)!=null){
					Trainer moving=_this.getMovingTrainers().get(_interruptedTrainer);
					_this.completionCheck=false;
					_this._dialogueVisible=false;
					moving.setInterrupted(false);
					_this._NPCpage=0;
				}
				
		//		_this.repaint();
			}
		}
	}
	
	public class EscapeTimerLeave implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			
			if(_gbs.isFlying()){
				//turn into a bird
				//fly upwards and whiteout
				//enterroom
				//fly down and white in
				//back to a person.
				
				
				if(_timeCount == 1){
					_busy=true;
					//Simply by starting the timer, you are now a bird. 
					
					//M2.playFX(M2.FLY);
					_whiteLevel=0;
					_darkLevel=0;
				}
				
				if (_timeCount>=2 && _timeCount<102){
					//nothing, just let the image play out for 100 ticks
				}
				
				if(_timeCount==102){
					M2.playFX(M2.FLY);
				}
				
				if (_timeCount>=102 && _timeCount<352){
					//250 ticks.
					_whiteLevel++;
					flyImageY--;
				}
				
				if(_timeCount==352){
					int thisRoom = _this._roomNum;
					PokePanel2 nextRoom = _gbs.getPanel(flyDest);
					
					
					_gbs.getCurrentPanel().stationaryEnterRoom(flyDest, nextRoom.getFlyX(), nextRoom.getFlyY(), nextRoom.FACESOUTH);
					_gbs.getCurrentPanel().escapeTimerEnter.start();
					_gbs.getPanel(thisRoom).escapeTimerLeave.stop();
					
					_timeCount = 0;
	
					//Modify latest location
					_gbs.getCurrentPanel().flyImageY=160-250;
					_gbs.getCurrentPanel()._whiteLevel=250;
					_gbs.getCurrentPanel()._busy=true; // Unfreeze this room.
					
					//But reset the last one:
					if(thisRoom!=flyDest){
						_gbs.getPanel(thisRoom).flyImageY=160;
						_gbs.getPanel(thisRoom)._whiteLevel=0;
						_gbs.getPanel(thisRoom)._busy=false;
					}
				}
			}
			
			else if(_gbs.isTeleporting()){
				//teleport
				//white out and in
				//reteleport
				
				
				if(_timeCount == 1){
					_busy=true;
					//Simply by starting the timer, you are now a bird. 
					
					M2.playFX(M2.TELEPORT_AWAY);
					_whiteLevel=0;
					_darkLevel=0;
					_teleTick=0;
				}
				
				if (_timeCount>=2 && _timeCount<62){
					if(_timeCount%4==0){
						_teleTick++;
					}
				//	_this.repaint();
				}
				
				if (_timeCount>=62 && _timeCount<187){
					//250 ticks, 2xSpeed.
					if(_whiteLevel<250){
						_whiteLevel++;
						_whiteLevel++;
					}
				}
				
				if(_timeCount==187){
					int thisRoom = _this._roomNum;
					PokePanel2 nextRoom = _gbs.getPanel(_gbs.getPlayer().getPkmnCenter());
					
					if(customTeleporting){
						_gbs.getCurrentPanel().stationaryEnterRoom(customTeleportRoomNum, customTeleportX, customTeleportY, nextRoom.FACESOUTH);
					}
					else{
						_gbs.getCurrentPanel().stationaryEnterRoom(_gbs.getPlayer().getPkmnCenter(), nextRoom._pkmnCentX, nextRoom._pkmnCentY, nextRoom.FACESOUTH);
					}
					_gbs.getCurrentPanel().escapeTimerEnter.start();
					_gbs.getPanel(thisRoom).escapeTimerLeave.stop();
					
					_timeCount = 0;
					_teleTick=15;
					
					//Modify latest location
					_gbs.getCurrentPanel()._whiteLevel=250;
					_gbs.getCurrentPanel()._busy=true; // Unfreeze this room.
					
					//But reset the last one:
					if(thisRoom!=_gbs.getPlayer().getPkmnCenter()){
						_gbs.getPanel(thisRoom)._whiteLevel=0;
						_gbs.getPanel(thisRoom)._busy=false;
					}
				}
			}
			
			else if(_gbs.isDigging()){
				//dig
				//black out and in
				//dig back
				
				if(_timeCount == 1){
					_busy=true;
					//Simply by starting the timer, you are now a bird. 
					
					M2.playFX(M2.DIG);
					_whiteLevel=0;
					_darkLevel=0;
					_digTick=0;
				}
				
				if (_timeCount>=2 && _timeCount<90){
					if(!_gbs._escapeRope && _timeCount%8==0){
						_digTick++;
					}
					else if(_gbs._escapeRope && _timeCount%4==0){
						_digTick++;
					}
				//	_this.repaint();
				}
				
				if (_timeCount>=90 && _timeCount<215){
					//250 ticks, 2xSpeed.
					if(_darkLevel<250){
						_darkLevel++;
						_darkLevel++;
					}
				}
				
				if(_timeCount==215){
					int thisRoom = _this._roomNum;
					PokePanel2 nextRoom = _gbs.getPanel(_gbs.getCurrentPanel()._caveEntranceNum);
					
					
					_gbs.getCurrentPanel().stationaryEnterRoom(_gbs.getCurrentPanel()._caveEntranceNum, _gbs.getCurrentPanel()._caveX, _gbs.getCurrentPanel()._caveY, nextRoom.FACESOUTH);
					_gbs.getCurrentPanel().escapeTimerEnter.start();
					_gbs.getPanel(thisRoom).escapeTimerLeave.stop();
					
					_timeCount = 0;
					if(_gbs._escapeRope){
						_digTick=22;
					}
					else{
						_digTick=11;
					}
					
					//Modify latest location
					_gbs.getCurrentPanel()._darkLevel=250;
					_gbs.getCurrentPanel()._busy=true; // Unfreeze this room.
					
					//But reset the last one:
					if(thisRoom!=nextRoom._roomNum){
						_gbs.getPanel(thisRoom)._darkLevel=0;
						_gbs.getPanel(thisRoom)._busy=false;
					}
				}
			}
			//_this.repaint();
		}
	}
	
public class EscapeTimerEnter implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			_timeCount++;
			
			
			if(_gbs.isFlying()){
				if(_timeCount <=1){
					_busy=true; //Freeze the current room.
					//Simply by starting the timer, you are now a bird. 
					
					M2.playFX(M2.FLY);
					_whiteLevel=250;
					_darkLevel=0;
					flyImageY=160-250;
				}
				
				if (_timeCount>=2 && _timeCount<252){
					//Another 250 ticks.
					
					_whiteLevel--;
					flyImageY++;
				}
				
				if (_timeCount>=252 && _timeCount<352){
					//Another gif buffer.
				}
			
				if(_timeCount == 352){
					escapeTimerEnter.stop();
					_busy=false;
					_timeCount = 0;
					_whiteLevel=0;
					_darkLevel=0;
					_gbs.setFlying(false);
					_gbs.setSurf(false);
				}
			}
			
			else if(_gbs.isTeleporting()){
				if(_timeCount <=1){
					_busy=true; //Freeze the current room.
					//Simply by starting the timer, you are now a bird. 
					
					//M2.playFX(M2.FLY);
					_whiteLevel=250;
					_darkLevel=0;
					_teleTick=15;
				}
				
				
				if (_timeCount>=2 && _timeCount<127){
					//Another 250 ticks.
					
					if(_whiteLevel>2){
						_whiteLevel--;
						_whiteLevel--;
					}

				}
				
				if(_timeCount==127){
					M2.playFX(M2.TELEPORT_BACK);
				}
				
				if (_timeCount>=127 && _timeCount<187){
					_whiteLevel=0;
					if(_timeCount%4==1){
						_teleTick--;
					}
					//Another gif buffer.
				}
			
				if(_timeCount == 187){
					escapeTimerEnter.stop();
					_busy=false;
					_timeCount = 0;
					_darkLevel=0;
					_teleTick=0;
					customTeleporting=false;
					_gbs.setTeleport(false);
					_gbs.setSurf(false);
				}
			}
			
			else if(_gbs.isDigging()){
				if(_timeCount <=1){
					_busy=true; //Freeze the current room.
					//Simply by starting the timer, you are now a bird. 
					
					_darkLevel=250;
					_whiteLevel=0;
					if(_gbs._escapeRope){
						_digTick=22;
					}
					else{
						_digTick=11;
					}

				}
				
				
				if (_timeCount>=2 && _timeCount<127){
					//Another 250 ticks.
					
					if(_darkLevel>2){
						_darkLevel--;
						_darkLevel--;
					}

				}
				
				if(_timeCount==127){
					M2.playFX(M2.DIG);
				}
				
				if (_timeCount>=127 && _timeCount<215){
					_darkLevel=0;
					if(!_gbs._escapeRope && _timeCount%8==1){
						_digTick--;
					}
					else if(_gbs._escapeRope && _timeCount%4==1){
						_digTick--;
					}
					//Another gif buffer.
				}
			
				if(_timeCount == 215){
					escapeTimerEnter.stop();
					_busy=false;
					_timeCount = 0;
					_darkLevel = 0;
					_digTick=0;
					_gbs.setDigging(false);
					_this.repaint();
					_gbs.setSurf(false);
					_gbs._escapeRope=false;
				}
			}
			//_this.repaint();
		}
	}

	public boolean isNormalBattle(){
		return normalBattle;
	}
	
	public boolean isGymBattle(){
		return gymBattle;
	}
	
	public void setGymLeaderBattleTrue(){
		normalBattle= false;
		gymBattle = true;
	}
	
	public void setNormalBattleTrue(){
		normalBattle = true;
		gymBattle = false;
	}
	
	public void setEliteBattleTrue(int type){
		normalBattle = false;
		gymBattle = false;
		customType = type;
	}
	
	public class AttackTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(_timeCount == 0){
				////SysOut.print("?");
				M2.playFX(M2.BATTLE_START);
				if(_movingTrainers.get(_interruptedTrainer).isGymLeader()){
					setGymLeaderBattleTrue();
				}
			}
			////SysOut.print("TIME COUNT: " + _timeCount);
			_timeCount++;
			_busy = true;
			
			_this.flash();
			
			if(_timeCount == 80){
				if(isNormalBattle()){
					SysOut.print("lame music");
					M2.playBG(M2.BATTLE);
				}
				else if(isGymBattle()){
					SysOut.print("gym music");
					M2.playBG(M2.GYM_BATTLE);
				}
				else{
					SysOut.print("Custom music");
					switch(customType){
					case MIKE: M2.playBG(M2.MIKE);
						break;
					case DAVID: M2.playBG(M2.DAVID);
						break;
					case SRI: M2.playBG(M2.SRI);
						break;
					case MAT: M2.playBG(M2.MAT);
						break;
					case RIVAL: M2.playBG(M2.CHAMPION);
						break;
					case GLITCH: M2.playBG(M2.GLITCH_MOB_BATTLE);
						break;
					case GAMBINO: M2.playBG(M2.GAMBINO_BATTLE);
						break;
					default: M2.playBG(M2.BATTLE);
					}
				}
			}
			
			if(_timeCount == 100){
				
				_attack.stop();

				_timeCount = 0;
				_busy = false;
				
				if(wild){
					if(_this._room._roomGrid[_xIndex][_yIndex] == SURF){
						_this.waterWild();
					}
					else{
						_this.wild();
					}
					wild = false;
				}
				else if(legendary){
					//if(_interruptedTrainer != -1){
						_this.legendaryBattle(_interruptedTrainer);
						//legendary=false;
						_this.getMovingTrainers().get(_interruptedTrainer).defeat();
					//}
				}
				else{
					//if(_interruptedTrainer != -1){
						_this.trainerBattle(_interruptedTrainer);
					//}
				}
			//	_this.repaint();
			}
		}
	}
	
	public boolean isApproaching(){
		return _approachTimer.isRunning();
	}
	
	public int getDirection() {
		return _direction;
	}

	public void setDirection(int _direction) {
		this._direction = _direction;
	}
	


	public class ApproachTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
		//	SysOut.print("Approaching: ");
			Trainer moving=_movingTrainers.get(_interruptedTrainer);
			
			if(moving.hasADestination() || _returnTrip || _tooCloseWait){
				//Maintain this order.
				int tickCheck = 0;
				//Return Mechanics
				if (_returnTrip && _gbs.npctick==tickCheck){
					//Only allow it to move if the tick is ==0
					moving.setAvoidMethod(moving.getReturnMethod());
					moving.sendHome();
					_returnTrip=false;
					_followNPC=true;
				}				
				
				//Approach mechanics
				if(!moving.hasReachedDest() || _tooCloseWait){
					if(_paintAlert && _seenByNPC){
						if(_alertTick>50){
							_alertTick=0;
							_paintAlert=false;
							_tooCloseWait=false;
						//	System.out.print("happened.");
							_tooCloseTrigger=false;
						//	_this.repaint();
							moving.setStationary(false);
							
							
							if(moving.getXIndex()>_this.xObs){
								_gbs.getPlayer().faceRight();
							}
							else if(moving.getXIndex()<_this.xObs){
								_gbs.getPlayer().faceLeft();
							}
							else if(moving.getYIndex()>_this.yObs){
								_gbs.getPlayer().faceDown();
							}
							else if(moving.getYIndex()<_this.yObs){
								_gbs.getPlayer().faceUp();
							}
							
							
						}
						else{
							_alertTick++;
				//			SysOut.print("Tick:" + _alertTick);
						}
					}
					
					else if (_gbs.npctick==tickCheck && !_paintAlert){
						moving.setStationary(false);
						
						if(!_gbs.getPlayer().hasADestination()){
							if(moving.getXIndex()>_this.xObs && moving.getYIndex()==_this.yObs){
								_gbs.getPlayer().faceRight();
							}
							else if(moving.getXIndex()<_this.xObs && moving.getYIndex()==_this.yObs){
								_gbs.getPlayer().faceLeft();
							}
							else if(moving.getYIndex()>_this.yObs && moving.getXIndex()==_this.xObs){
								_gbs.getPlayer().faceDown();
							}
							else if(moving.getYIndex()<_this.yObs && moving.getXIndex()==_this.xObs){
								_gbs.getPlayer().faceUp();
							}
						}
					}
					
					
				}
				
				
				//Stopping.
				else if(_gbs.npctick==tickCheck && !_tooCloseWait){
					
					//Don't stop the timer unless the player has nowhere to go.
					if(_gbs.getPlayer()._destVectorX.size()==0){
						_approachTimer.stop();
						SysOut.print("THIS ONE");
						_busy=false;
						if(moving.getDefeatAtFinalDest()){
							moving.defeat();
						}
					}
					
					moving.clearDestinations();
					moving.setStationary(true);
					moving.setMoving(false);
					
					//If this is the first time theyre approaching you, have them talk to you. Else, they will leave.
					if(_approached || _oneShot || _seenByNPC){
						if(moving.getLOS()>0){
							moving.setLOS(0);
						}
						
						//_tooCloseTrigger=true;
						_this.A_Button();

					}
					else{
						//Careful with this defeat statement.
						if(moving.getVanishing()){
							moving.defeat();
							SysOut.print("THIeeS ONE");
							moving.resetHome();

						}
						if(moving.getDefeatAtFinalDest()){
							SysOut.print("THAT ONE");
							moving.defeat();
						}
					}
					
					//_this.repaint();
				}
			}
			
			else if(!moving.hasADestination() && _gbs.getPlayer().hasADestination()){
				_followNPC=true;
				
				if(moving.getDefeatAtFinalDest() || moving.getDefeatAfterReturnTrip()){
					moving.setVanishing(true);
					moving.defeat();
					SysOut.print("ASDFA ONE");
				}

			}
		
			
			//Additional arguments if controlling the player as well.
			if(_gbs.getPlayer()._destVectorX.size()!=0 && _followNPC){
				_SpeedTimer = 20;
				_this.movePlayerToTarget(_gbs.getPlayer().getAvoidMethod(),_gbs.getPlayer()._destVectorX.get(0),_gbs.getPlayer()._destVectorY.get(0), _gbs.getPlayer().isIgnoring());
				if(_this.hasPlayerReachedDest()){
					_busy=false;
					_followNPC=false;
					_approachTimer.stop();
					_walkTimer.stop();
					if(_gbs.getPlayer()._destVectorX.size()>1){
						_gbs.getPlayer()._destVectorX.remove(0);
						_gbs.getPlayer()._destVectorY.remove(0);
						_this._approachTimer.start();
					}
					else if(_roomJustEntered){
						_gbs.setSpinning(false);
						_gbs.getPlayer().setIgnoring(false);
						_gbs.getPlayer().clearDestinations();
						_approachTimer.stop();
						_roomJustEntered=false;
						_this.A_Button();
					}
					else{
						_gbs.setSpinning(false);
						_gbs.getPlayer().setIgnoring(false);
						_gbs.getPlayer().clearDestinations();
						_this.A_Button();
					}
				}
			//	_this.repaint();
			}	
			
		}
	}

}

