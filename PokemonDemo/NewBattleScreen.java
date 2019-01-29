package PokemonDemo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Timer;



@SuppressWarnings("serial")
public class NewBattleScreen extends PokePanel2{//JPanel implements TestPanel{

	private BufferedImage playerSprite, rivalSprite, bg, statsBG;
	private Pokemon ally, enemy;
	private Rectangle2D.Double allyHP, allyHPO, enemyHP, enemyHPO, allyXP, allyXPO;
	private Graphics2D g2;
	private boolean rootMenu, itemMenu, pkmnMenu, attackMenu, battleType, itemSelectMenu=false, statPanel;
	public static final int NO_BG = 0, GRASS = 1, WATER = 2, INDOORS = 3, MEIK = 4, FOREST = 5, LIGHT_CAVE =6, PATCHY_GRASS=7, PARADISO=8, GARDEN=9, URBAN=10, DARK_CAVE=11, VENLAB=12, BARHOL=13, NIGHT=14, SCIENCE_QUAD=15, DARK_WATER=16, WATERFIRE=17, BARHOL_GYM=18, CIT=19, FISHCO=20, SCILI=21, BASEMENT=22, VIAVIA=23, ELITE = 24;
	private Vector<Pokemon> rivalBelt; 
	private static final boolean WILD = false, TRAINER = true;
	private Ellipse2D.Double cursor;
	public static boolean forceFight=false;
	
	private int playerX = 10, rivalX = 230;
	private Ellipse2D.Double deadSlot;
	private BufferedImage pokeBall;
	private Timer battleStartTimer, battleEndTimer;
	private Player player;
	private Trainer trainer;
	private boolean allyVis, enemyVis;
	private boolean cantRun;
	private boolean allyIsConfused = false, enemyIsConfused = false;
	private boolean allyHasLeechSeed = false, enemyHasLeechSeed = false;
	private String escape = "";
	private int prevNum;
	private Vector<Integer> _foughtBelt = new Vector<Integer>();
	private int _foughtIndex = 0;
	private double _foughtFraction;
	
	//For GBTimer
	public Timer GBTimer;
	private int GBTick=0;
	private int GBAnimIndex=0, GB_XOffset=0, GB_YOffset=0;
	private double GB_Scale=0;
	public int GBSelect=0;
	public final int GB_FAINT=166;
	private int allyFaintY=0, enemyFaintY=0;
	private boolean hideStats=false;
	private int GB_AllyX=0, GB_AllyY=0, GB_AllyW=0, GB_AllyH=0, GB_EnemyX=0, GB_EnemyY=0, GB_EnemyW=0, GB_EnemyH=0; 
	private String GBAnimDir="NullCheck";
	public int GB_User=0; //0 is ally, 1 is enemy.
	private boolean GB_Rotates=false;
	
	//For items.
	private int _deltaHP=0;
	private int _fixedStatus=0;
	private int _deltaPP=0;
	private int itemPkmn=0;
	private int PPshift=45;
	private int runTurns=0;
	private Pokemon selected;
	private int selectedAttack = 0;
	private String affectedPkmn;
	private boolean _allyTurnComplete=false;
	
	//Forgetting attacks.
	private int attackToForget=0;
	private boolean forgettingAttack;
	private boolean currentRequest;
	private int denials=0;
	
	//Switching after wild
	private boolean wildSwitch;
	
	private double previousItemCoordY,previousItemCoordX, previousPkmnCoordX, previousPkmnCoordY;
	
	private Timer t_text, t_hp, t_xp;
	private CharListener cl;
	private HPChangeListener hpcl;
	private XPChangeListener xpcl;
	private char[] cfirst, csecond;
	private boolean completionCheck;
	private String s1 = "", s2 = "";
	private NewBattleScreen _this;
	private Font font = new Font("Courier New", Font.BOLD, 16);
	
	private Rectangle2D.Double menuBG;
	private final int p_1st = 20, p_2nd = 72, p_3rd = 124, p_4th = 176, p_5th = 228, p_6th = 280;
	private Item currentItem;
	
	@SuppressWarnings("unused")
	private boolean first, activeTurn = false;
	private static final boolean ALLY_FIRST = true, ENEMY_FIRST = false;
	private NewTurn turn;
	@SuppressWarnings("unused")
	private NewTurnAction nextTurn, enemyNextTurn;
	
	@SuppressWarnings("unused")
	private boolean missed;
	private int battleLoc;
	
	public int allyAtkStage, allyDefStage, allySpAtkStage, allySpDefStage, allySpdStage, allyAccStage, allyEvaStage,
				enemyAtkStage, enemyDefStage, enemySpAtkStage, enemySpDefStage, enemySpdStage, enemyAccStage, enemyEvaStage;
	
	//Store Probabilities up here, makes it easier to modify.
	public int SnapConfProb=25;
	public int HurtsSelfProb=25;
	public int escapeProb=50; //Should be different for each pkmn actually. Create set and get methods.
	
	private Vector<Item> items;
	private boolean pageTwo = false;
	
	private Pokemon fighter = null;
	
	private boolean allyFlying = false, enemyFlying = false, 
					allyFlinch = false, enemyFlinch = false, 
					allyDug = false, enemyDug = false,
					allyRaging = false, enemyRaging = false,
					allyMinimized = false, enemyMinimized = false,
					allyBound = false, enemyBound = false,
					allyMisty = false, enemyMisty = false,
					allyLScreened = false, enemyLScreened = false,
					allyReflected = false, enemyReflected = false;
	
	private int payday = 0, allyDisabled = -1, aDisabledRemaining = 0, eDisabledRemaining = 0, enemyDisabled = -1, allyMultiTurn = -1, enemyMultiTurn = -1,
				allyLScreenedTurns = 0, enemyLScreenedTurns = 0,
				allyReflectedTurns = 0, enemyReflectedTurns = 0;
	
	//Mimic
	private Attack allyMimicAttack = null, enemyMimicAttack = null;
	private int allyMimicIndex = -1, enemyMimicIndex = -1;
	
	//Mirror Move
	private Attack allyLastAttack = null, enemyLastAttack = null;
	
	//Transform
	private Pokemon allyTransform = null, enemyTransform = null;
	private int allyTransformBelt = -1, enemyTransformBelt = -1;
	
	private int allyReceivedDamage = -1, enemyReceivedDamage = -1;
	
	private Vector<Pokemon> evolvers;
	
	
	public NewBattleScreen(GameBoyScreen gbs){
		super(gbs);
		
		_this = this;
		this._roomNum = -1;
		
		this._darkLevel=0;
		_gbs = gbs;
		
		evolvers = new Vector<Pokemon>();
		
		missed = false;
		
		this.setLayout(new BorderLayout());
		
		this.setBackground(Color.WHITE);
		
		items = new Vector<Item>();
		
		cl = new CharListener();
		t_text = new Timer(35, cl);
		
		hpcl = new HPChangeListener();
		t_hp = new Timer(150, hpcl);
		
		xpcl = new XPChangeListener();
		t_xp = new Timer(2, xpcl);
		
		BattleStartListener bst = new BattleStartListener();
		battleStartTimer = new Timer(5, bst);
		
		BattleEndListener bet = new BattleEndListener();
		battleEndTimer = new Timer(5, bet);
		
		GeneralBattleTimer gbt = new GeneralBattleTimer();
		GBTimer = new Timer(1, gbt);
		
		cursor = new Ellipse2D.Double();
		cursor.height = 5;
		cursor.width = 5;
		cursor.x = 261;
		cursor.y = 327;
		
		menuBG = new Rectangle2D.Double();
		menuBG.height = 323;
		menuBG.width = 500;
		menuBG.x = -10;
		menuBG.y = -10;
		
		deadSlot = new Ellipse2D.Double();
		deadSlot.height = 20;
		deadSlot.width = 20;
		
		allyHPO = new Rectangle2D.Double();
		allyHPO.height = 15;
		allyHPO.width = 100;
		
		allyHP = new Rectangle2D.Double();
		allyHP.height = 13;
		
		allyXPO = new Rectangle2D.Double();
		allyXPO.height = 7;
		allyXPO.width = 100;
		
		allyXP = new Rectangle2D.Double();
		allyXP.height = 5;
		
		enemyHP = new Rectangle2D.Double();
		enemyHP.height = 13;
		
		enemyHPO = new Rectangle2D.Double();
		enemyHPO.height = 15;
		enemyHPO.width = 100;
		
		player = _gbs.getPlayer();
		
		try {
			playerSprite = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/battlesprite.png"));
			pokeBall = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/Item/pokeball.png"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		this.repaint();
	}
	
	public Vector<Pokemon> getEvolutionVector(){
		return evolvers;
	}
	
	public void setFightingAlly(Pokemon p){
		fighter = p;
	}
	
	public Pokemon getFightingAlly(){
		return fighter;
	}
	
	public boolean isWild(){
		if(battleType == WILD){
			return true;
		}
		return false;
	}
	
	public boolean isTrainer(){
		if(battleType == TRAINER){
			return true;
		}
		return false;
	}
	
	public int getEnemyAttackChoice(){
		SysOut.print("enemy selection");
		if(battleType == WILD){
			if(enemyDisabled == -1){
				if(enemyRaging){
					int rage = -1;
					for(int i = 0; i < enemy.getAttacks().size(); i++){
						if(enemy.getAttacks().get(i).getAttackNum() == 99){
							rage = i;
						}
					}
					if(rage != -1) return rage;
				}
				return (int) (Math.random()*(enemy.getAttacks().size()));
			}
			else{
				SysOut.print("while?");
				if(enemy.getAttacks().size() > 1){
					
					int choice = (int)(Math.random()*(enemy.getAttacks().size()));
					while(choice == enemyDisabled){
						choice = (int)(Math.random()*(enemy.getAttacks().size()));
					}
					return choice;
				}
				else{
					return -1;
				}
			}
		}
		if(battleType == TRAINER){
			if(enemyDisabled == -1)
				return (int) (Math.random()*(enemy.getAttacks().size()));
			else{
				if(enemy.getAttacks().size() > 1){
					int choice = (int)(Math.random()*(enemy.getAttacks().size()));
					while(choice == enemyDisabled){
						choice = (int)(Math.random()*(enemy.getAttacks().size()));
					}
					return choice;
				}
				else{
					return -1;
				}
			}
		}
		return 0;
	}
	
	public void pickBattleBG(int battleLocation){
		SysOut.print("BATTLE! " + battleLocation);
		try{
			switch(_gbs.getPanel(battleLocation).getBattleBG()){
//			case NO_BG: bg = null;
//				break;
			case GRASS: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/grassBG.png"));
				break;
			case MEIK: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/meikBG.png"));
				break;
			case PARADISO: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/paradisoBG.png"));
				break;
			case LIGHT_CAVE: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/caveBG.png"));
				break;
			case FOREST: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/patriotsBG.png"));
				break;
			case PATCHY_GRASS: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/patchyBG.png"));
				break;
			case GARDEN: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/gardenBG.png"));
				break;
			case URBAN: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/urbanBG.png"));
				break;
			case DARK_CAVE: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/darkCaveBG.png"));
				break;
			case VENLAB: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/VENLabBG.png"));
				break;
			case BARHOL: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/BarHolBG.png"));
				break;
			case BARHOL_GYM: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/BarHolGymBG.png"));
				break;
			case NIGHT: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/NightBG.png"));
				break;
			case WATER: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/WaterBG.png"));
				break;
			case DARK_WATER: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/DarkWaterBG.png"));
				break;
			case WATERFIRE: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/WaterFireBG.png"));
				break;
			case SCIENCE_QUAD: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/ScienceQuadBG.png"));
				break;
			case CIT: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/CITBG.png"));
				break;
			case FISHCO: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/FishCoBG.png"));
				break;
			case SCILI: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/SciLiBG.png"));
					break;
			case BASEMENT: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/RocketBasementBG.png"));
				break;
			case INDOORS: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/indoorBG.png"));
				break;
			case VIAVIA: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/viaviaBG.png"));
				break;
			case ELITE: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/eliteBG.png"));
				break;
			default: bg = ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/indoorBG.png"));
			}
		}
		catch(IOException ioe){bg = null;}
	}
	
	public GameBoyScreen getGBS(){
		return _gbs;
	}
	
	public void setAllyFlying(boolean fly){
		allyFlying = fly;
	}
	
	public boolean isAllyFlying(){
		return allyFlying;
	}
	
	public void setEnemyFlying(boolean fly){
		enemyFlying = fly;
	}
	
	public boolean isEnemyFlying(){
		return enemyFlying;
	}
	
	public void setAllyDug(boolean dig){
		allyDug = dig;
	}
	
	public boolean isAllyDug(){
		return allyDug;
	}
	
	public void setEnemyDug(boolean dig){
		enemyDug = dig;
	}
	
	public boolean isEnemyDug(){
		return enemyDug;
	}
	
	public void setAllyFlinch(boolean f){
		allyFlinch = f;
	}
	
	public void setEnemyFlinch(boolean f){
		enemyFlinch = f;
	}
	
	public boolean getAllyFlinch(){
		boolean temp = allyFlinch;
		allyFlinch = false;
		return temp;
	}
	
	public boolean getEnemyFlinch(){
		boolean temp = enemyFlinch;
		enemyFlinch = false;
		return temp;
	}
	
	public void setAllyRaging(boolean r){
		allyRaging = r;
	}
	
	public void setEnemyRaging(boolean r){
		enemyRaging = r;
	}
	
	public boolean isEnemyRaging(){
		return enemyRaging;
	}
	
	public boolean isAllyRaging(){
		return allyRaging;
	}
	
	public void setAllyMinimized(boolean m){
		allyMinimized = m;
	}
	
	public void setEnemyMinimized(boolean m){
		enemyMinimized = m;
	}
	
	public boolean isAllyMinimized(){
		return allyMinimized;
	}
	
	public boolean isEnemyMinimized(){
		return enemyMinimized;
	}
	
	public void setAllyDisabled(int num){
		allyDisabled = num;
	}
	
	public void setEnemyDisabled(int num){
		enemyDisabled = num;
	}
	
	public boolean isAllyDisabled(){
		return (allyDisabled != -1);
	}
	
	public boolean isEnemyDisabled(){
		return (enemyDisabled != -1);
	}
	
	public void setAllyDisabledRemaining(int remain){
		aDisabledRemaining = remain;
	}
	
	public void setEnemyDisabledRemaining(int remain){
		eDisabledRemaining = remain;
	}

	public void decrementADR(){
		aDisabledRemaining--;
		if(aDisabledRemaining == 0){
			allyDisabled = -1;
			SysOut.print("Ally no longer disabled");
		}
	}
	
	public void decrementEDR(){
		eDisabledRemaining--;
		if(eDisabledRemaining == 0){
			enemyDisabled = -1;
			SysOut.print("Enemy no longer disabled");
		}
	}
	
	public void decrementALS(){
		allyLScreenedTurns--;
		if(allyLScreenedTurns == 0){
			allyLScreened = false;
		}
	}
	
	public void decrementELS(){
		enemyLScreenedTurns--;
		if(enemyLScreenedTurns == 0){
			enemyLScreened = false;
		}
	}
	
	public void decrementAR(){
		allyReflectedTurns--;
		if(allyReflectedTurns == 0){
			allyReflected = false;
		}
	}
	
	public void decrementER(){
		enemyReflectedTurns--;
		if(enemyReflectedTurns == 0){
			enemyReflected = false;
		}
	}
	
	public void incrementPayDay(int coin){
		payday += coin;
	}
	
	public void setNumAllyMultiTurn(int multi){
		allyMultiTurn = multi;
		SysOut.print("Should only last " + multi + " turns.");
	} 
	
	public int getNumAllyMultiTurn(){
		return allyMultiTurn;
	}
	
	public void setNumEnemyMultiTurn(int multi){
		enemyMultiTurn = multi;
	}
	
	public int getNumEnemyMultiTurn(){
		return enemyMultiTurn;
	}
	
	public void setAllyBound(boolean bind){
		allyBound = bind;
	}
	
	public void setEnemyBound(boolean bind){
		enemyBound = bind;
	}
	
	public boolean isAllyBound(){
		return allyBound;
	}
	
	public boolean isEnemyBound(){
		return enemyBound;
	}
	
	public void setAllyLightScreened(boolean lScreen){
		allyLScreened = lScreen;
		if(allyLScreened){
			allyLScreenedTurns = 5;
		}
	}
	
	public void setEnemyLightScreened(boolean lScreen){
		enemyLScreened = lScreen;
		if(enemyLScreened){
			enemyLScreenedTurns = 5;
		}
	}
	
	public boolean isAllyLightScreened(){
		return allyLScreened;
	}
	
	public boolean isEnemyLightScreened(){
		return enemyLScreened;
	}
	
	public boolean isAllyReflected(){
		return allyReflected;
	}
	
	public boolean isEnemyReflected(){
		return enemyReflected;
	}
	
	public void setAllyReflected(boolean reflect){
		allyReflected = reflect;
	}
	
	public void setEnemyReflected(boolean reflect){
		enemyReflected = reflect;
	}
	
	public boolean isAllyMisty(){
		return allyMisty;
	}
	
	public boolean isEnemyMisty(){
		return enemyMisty;
	}
	
	public void setAllyMisty(boolean mist){
		allyMisty = mist;
	}
	
	public void setEnemyMisty(boolean mist){
		enemyMisty = mist;
	}
	
	//TODO
	public void setAllyTransform(){
		allyTransform = Pokemon.clone(ally);
		allyTransformBelt = ally.getBelt()-1;
		
		ally.setType(enemy.getType1());
		ally.setAtkStat(enemy.getAtkStat());
		ally.setDefStat(enemy.getDefStat());
		ally.setSpAtkStat(enemy.getSpAtkStat());
		ally.setSpDefStat(enemy.getSpDefStat());
		ally.setSpeed(enemy.getSpeed());
		ally.setAccuracy(enemy.getAccuracy());
		ally.setEvasion(enemy.getEvasion());
		ally.setImages(enemy.getDexNum());
		
		ally.getAttacks().clear();
		for(int i = 0; i < enemy.getAttacks().size(); i++){
			//ally.getAttacks().add(enemy.getAttacks().get(i));
			ally.addNewAttackNum(enemy.getAttacks().get(i).getAttackNum());
			ally.getAttacks().get(i).setCurrentPP(5);
		}
		
		this.repaint();
	}
	
	public void setEnemyTransform(){
		
		enemyTransform = Pokemon.clone(enemy);
		enemyTransformBelt = 0;
		
		enemy.setType(ally.getType1());
		enemy.setAtkStat(ally.getAtkStat());
		enemy.setDefStat(ally.getDefStat());
		enemy.setSpAtkStat(ally.getSpAtkStat());
		enemy.setSpDefStat(ally.getSpDefStat());
		enemy.setSpeed(ally.getSpeed());
		enemy.setAccuracy(ally.getAccuracy());
		enemy.setEvasion(ally.getEvasion());
		enemy.setImages(ally.getDexNum());
		
		enemy.getAttacks().clear();
		for(int i = 0; i < ally.getAttacks().size(); i++){
			enemy.addNewAttackNum(ally.getAttacks().get(i).getAttackNum());
			enemy.getAttacks().get(i).setCurrentPP(5);
		}
		
		this.repaint();
	}
	
	public void clearAllyTransform(){
		if(allyTransformBelt != -1){
			player.getAllActive().set(allyTransformBelt, allyTransform);
			allyTransformBelt = -1;
			allyTransform = null;
		}
	}
	
	public void clearEnemyTransform(){
		if(enemyTransformBelt != -1){
			enemy = enemyTransform;
			enemyTransform = null;
			enemyTransformBelt = -1;
		}
	}
	
	public boolean isAllyTransformed(){
		if(allyTransformBelt != -1){
			return true;
		}
		return false;
	}
	
	public boolean isEnemyTransformed(){
		if(enemyTransform != null){
			return true;
		}
		return false;
	}
	
	public void endAllyMimicry(){
		if(allyMimicIndex != -1){
			ally.getAttacks().set(allyMimicIndex, allyMimicAttack);
			allyMimicIndex = -1;
			allyMimicAttack = null;
		}
	}
	
	public void endEnemyMimicry(){
		if(enemyMimicIndex != -1){
			enemy.getAttacks().set(enemyMimicIndex, enemyMimicAttack);
			enemyMimicIndex = -1;
			enemyMimicAttack = null;
		}
	}
	
	public Attack getAllyLastAttack(){
		return allyLastAttack;
	}
	
	public Attack getEnemyLastAttack(){
		return enemyLastAttack;
	}
	
	public void setAllyLastAttack(Attack a){
		if(a!=null){
			allyLastAttack = Attack.getAttackByNum(a.getAttackNum());
		}
		else{
			allyLastAttack=null;
		}
	}
	
	public void setEnemyLastAttack(Attack e){
		if(e != null)
			enemyLastAttack = Attack.getAttackByNum(e.getAttackNum());
		else
			enemyLastAttack = null;
	}
	
	public static boolean canEscape(int allySpeed, int enemySpeed, int runTurns){
		if(forceFight){return false;}
		double eS=enemySpeed/4.0;
		if(eS>255) {eS=eS-255;}
		else {}
		double nutsInMyMouth=(allySpeed/eS*32.0+30.0*runTurns)/255.0*100.0;
		SysOut.print("are they in my mouth:"+nutsInMyMouth);
		String result=NewTurnAction.Bernoulli(nutsInMyMouth);
		if (result=="Yes"){return true;}
		
		return false;
	}
	public void resetAllyStages(){
		allyAtkStage = 0;
		allyDefStage = 0;
		allySpAtkStage = 0;
		allySpDefStage = 0; 
		allySpdStage = 0;
		allyAccStage = 0;
		allyEvaStage = 0;
	}
	
	public void resetEnemyStages(){
		enemyAtkStage = 0;
		enemyDefStage = 0;
		enemySpAtkStage = 0;
		enemySpDefStage = 0;
		enemySpdStage = 0;
		enemyAccStage = 0;
		enemyEvaStage = 0;
	}
	
	public void resetAllMenus(){
		rootMenu=false;
		attackMenu=false;
		itemMenu=false;
		pkmnMenu=false;
		itemSelectMenu=false;
	}
	
	public void setAllyConfusion(boolean b){
		allyIsConfused = b;
	}
	public void setEnemyConfusion(boolean b){
		enemyIsConfused = b;
	}
	public boolean isAllyConfused(){
		return allyIsConfused;
	}
	public boolean isEnemyConfused(){
		return enemyIsConfused;
	}
	
	public void setAllyLeechSeed(boolean b){
		allyHasLeechSeed = b;
	}
	public void setEnemyLeechSeed(boolean b){
		enemyHasLeechSeed = b;
	}
	public boolean isAllyLeechSeeded(){
		return allyHasLeechSeed;
	}
	public boolean isEnemyLeechSeeded(){
		return enemyHasLeechSeed;
	}
	
	public int getLastAllyDamageReceived(){
		return allyReceivedDamage;
	}
	
	public int getLastEnemyDamageReceived(){
		return enemyReceivedDamage;
	}
	
	public double getMainStatMultiplier(int stage){
		switch(stage){
		case -6: return 0.25;
		case -5: return 0.285;
		case -4: return 0.33;
		case -3: return 0.4;
		case -2: return 0.5;
		case -1: return 0.66;
		case 0: return 1;
		case 1: return 1.5;
		case 2: return 2;
		case 3: return 2.5;
		case 4: return 3;
		case 5: return 3.5;
		case 6: return 4;
		default: return 1;
		}
	}
	
	public double getAccuracyMultiplier(int stage){
		switch(stage){
		case -6: return 0.33;
		case -5: return 0.375;
		case -4: return 0.428;
		case -3: return 0.5;
		case -2: return 0.6;
		case -1: return 0.75;
		case 0: return 1;
		case 1: return 1.33;
		case 2: return 1.66;
		case 3: return 2;
		case 4: return 2.33;
		case 5: return 2.66;
		case 6: return 3;
		default: return 1;
		}
	}
	
	public double getEvasionMultiplier(int stage){
		switch(stage){
		case -6: return 3;
		case -5: return 2.66;
		case -4: return 2.33;
		case -3: return 2;
		case -2: return 1.66;
		case -1: return 1.33;
		case 0: return 1;
		case 1: return 0.75;
		case 2: return 0.6;
		case 3: return 0.5;
		case 4: return 0.428;
		case 5: return 0.375;
		case 6: return 0.33;
		default: return 1;
		}
	}
	
	public void endAllyTurnNow(){
		//To exit out of the item menu post-item-usage.
		if(_deltaHP>0 || _fixedStatus>0 || _deltaPP>0 || _allyTurnComplete){
			_deltaHP = 0;
			_fixedStatus=0;
			_deltaPP = 0;
			if(_allyTurnComplete){
				SysOut.print("IM HERE");
			}
			_allyTurnComplete=false;
			itemMenu = false;
			itemSelectMenu = false;
			pkmnMenu = false;
			attackMenu=false;
			
			if(cantRun){
				runTurns++;
			cantRun=false;
			}
			
			escape="";
			//rootMenu = true;
			this.resetRootMenuCursor();
			SysOut.print("Item screen false");
			//turn.nextStage();
			turn = new NewTurn();
			
			int choice = this.getEnemyAttackChoice();
			if(choice != -1){
				NewTurnAction ntaEnemy = new NewTurnAction.TA_BasicAttack(this, enemy, ally, "Enemy ",enemy.getAttacks().get(choice));
				if(enemy.getAttacks().get(choice).getNonDamageType() != 0)
					ntaEnemy = new NewTurnAction.TA_NonDamagingAttack(this, enemy, ally, "Enemy ", enemy.getAttacks().get(choice));
				turn.addTurnAction(ntaEnemy);
			}
			else{
				NewTurnAction ntaStrug = new NewTurnAction.TA_Struggle(this, enemy, ally);
				turn.addTurnAction(ntaStrug);
			}
			this.A_Button();
		}
	}
	
	
	public void resetRootMenuCursor(){
		cursor.x = 261;
		cursor.y = 327;
	}
	
	public Font getFont(){
		return font;
	}
	
	public NewTurn getActiveTurn(){
		return turn;
	}
	
		
	public void C_Button(){
		if(turn != null){
			for(int i = 0; i < turn.getListSize(); i++){
				SysOut.print(i+": " + turn.getList().get(i).getLine1()+ ", " + turn.getList().get(i).getLine2());
			}
		}
	}
	
	public void setNextTurnAction(NewTurnAction nta){
		nextTurn = nta;
	}
	
	public NewTurnAction getNextTurnAction(){
		NewTurnAction temp = nextTurn;
		//nextTurn = null; // I COMMENTED THIS OUT< MAYBE IT WILL HELP< MAT
		return temp;
	}
	
	public NewTurnAction acquireFollowingEvent(){
		return turn.getList().get(turn.getCurrentStage());
	}
	
	public void setNextEnemyTurnAction(NewTurnAction nta){
		enemyNextTurn = nta;
	//	SysOut.print(nta.line1);
	}
	
	public Pokemon getAlly(){
		return ally;
	}
	
	public void setAlly(Pokemon ally){
		this.ally = ally;
	}
	
	public void setAllyVis(boolean visibility){
		allyVis = visibility;
	}
	
	public Pokemon getEnemy(){
		return enemy;
	}
	
	public Pokemon getSelectedPkmn(){
		return selected;
	}
	
	public void setSelectedPkmn(Pokemon pkmn){
		this.selected = pkmn;
	}
	
	public void startBattleEnd(){
		battleEndTimer.start();
	}
	
	public void setHPListenerChange(Pokemon p, int damage){
		hpcl.setChange(p, damage);
	}
	
	public void setHPListenerChange(Pokemon victim, int damage, Pokemon predator, int health){
		hpcl.setChange(victim, damage, predator, health);
	}
	
	public void setXPListenerChange(Pokemon p, int xpchange){
		xpcl.setChange(p, xpchange);
	}
	
	public void setEnemy(Pokemon enemy){
		this.enemy = enemy;
		
		if(_gbs.getPlayer().getPokedex() != null && !_gbs.getPlayer().getPokedex().hasSeen(enemy.getDexNum())){
			_gbs.getPlayer().getPokedex().addToWatchList(enemy);
		}
	}
	
	public void setEnemyVis(boolean vis){
		enemyVis = vis;
	}
	
	public void setPkmnScreenVis(boolean b){
		if(b){
			rootMenu = false;
			pkmnMenu = true;
			this.displayAutoWrapText("Choose a Pokemon to switch into battle.");
			cursor.x = 7;
			cursor.y = p_1st;
		}
		else{
			pkmnMenu = false;
		}
		this.repaint();
	}
	public void setItemMenuVis(boolean b){
		itemMenu = b;
		this.repaint();
	}
	
	public void setStatPanelVis(boolean b){
		statPanel = b;
		this.repaint();
	}
	
	public void setAttackMenuVis(boolean b){
		attackMenu = b;
		cursor.y = 327;
		cursor.x = 12;
		this.repaint();
	}
	public void setForgettingAttack(boolean b){
		forgettingAttack=b;
	}
	public void setCurrentRequest(boolean b){
		currentRequest=b;
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
	
	public boolean getWildSwitch(){
		return wildSwitch;
	}
	public void setWildSwitch(boolean b){
		wildSwitch=b;
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
	
	public void setItemSelectMenuVis(boolean b){
		itemSelectMenu = b;
		this.repaint();
	}
	public void setAllyTurnComplete(boolean b){
		_allyTurnComplete=b;
	}
	public boolean isAllyTurnComplete(){
		return _allyTurnComplete;
	}
	
	public int getNBSItemIndex(){
		//This is the cursor index.
		int index =(int)(cursor.y/18.0)+prevNum;
		
		return index;
	}
	public int getPlayerItemIndex(){
		//This is the cursor index. But this is not the real index of the items.
		int index =(int)(cursor.y/18.0)+prevNum;
		
		//This equation only works if everything is present
		/*
		 * get list
		 * get numbers remaining
		 * find indices of those numbers
		 * make vector
		 * index of that vector.
		 * */
		Vector<Integer> indices = new Vector<Integer>();
		for (int i=0; i<this.getPlayer().getAllItems().size(); i++){
			//If there's more than one, add it.
			if (this.getPlayer().getItem(i).getRemaining()>0 && this.getPlayer().getItem(i).isBattleAppropriate()){
				indices.add(i);
			}		
		}
		
		//Here is the correct index.
		int adjustedIndex = (Integer) indices.get(index);

		return adjustedIndex;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Trainer getEnemyTrainer(){
		return trainer;
	}
	
	public Vector<Integer> getFoughtBelt(){
		return _foughtBelt;
	}
	
	public static int getToNextLevel(Pokemon subject){
		//return 100000000; //FIXME
		
		int NextLevel = (subject.getLevel() + 1) * (subject.getLevel() + 1) * (subject.getLevel() + 1);
		int CurrentLevel = (subject.getLevel()) * (subject.getLevel()) * (subject.getLevel());
		return (int) (subject.getExpMult() * (NextLevel - CurrentLevel)); 
	}
	
	public static double GeometricToBernoulli(int status){
		//Convert Geometric to Bernoulli
		//Expected Value = 1/Prob
		//Thus, Prob= 1/Turns.
		
		double Turns=1; //Badly poisoned, need Turns=1 not 0 else /0.
		double prob=1;
		switch(status){
		case 1:
			//Sleep
			Turns=5;
//			prob= 100*(1/Turns);
			//prob=0.2;
			break;
		case 2: 
			//Poison
			//The point is, you can't get over this status midbattle.
			Turns=1;
			//prob= 0;
			break;
		case 3: 
			//Paralyze
			Turns=2;
			//prob=0;
			break;
		case 4: 
			//Burned
			Turns=1;
			//prob=0;
			break;
		case 5: 
			//Frozen
			Turns=5;
			//prob= 100*(1/Turns);
			//prob=0.2;
			break;
		}

		prob= 100*(1/Turns);
		return prob;
	}
	
	public static double ProbOfAttackWithStatus(int status){
		switch(status){
		//Asleep, can't attack.
		case 1: return 0;
		//Paralysis, 75% chance of attacking
		case 3: return 75;
		//Frozen, can't attack.
		case 5: return 0;
		default: return 100;
		}
	}
	
	public void determineWhoGoesFirst(Pokemon ally, Pokemon enemy){
		int allySpeed=ally.getSpeed();
		int enemySpeed=enemy.getSpeed();
		
		//For cutting speed in 1/4 due to paralysis.
		if (ally.getStatusAcro()=="PAR"){
			allySpeed=(int)(allySpeed/4.0);
		}
		if (enemy.getStatusAcro()=="PAR"){
			enemySpeed=(int)(enemySpeed/4.0);
		}
		
		if ((allySpeed*this.getMainStatMultiplier(allySpdStage))>enemySpeed*this.getMainStatMultiplier(enemySpdStage)){
			first = ALLY_FIRST; 
		}
		if ((allySpeed*this.getMainStatMultiplier(allySpdStage))<enemySpeed*this.getMainStatMultiplier(enemySpdStage)){
			first = ENEMY_FIRST;
		}
		//If THAT is equal, make it random.
		if ((allySpeed*this.getMainStatMultiplier(allySpdStage))==enemySpeed*this.getMainStatMultiplier(enemySpdStage)){
			first = ((int)(Math.random()*2) == 0 ? ALLY_FIRST : ENEMY_FIRST);
		}
	}
	
	public void executeAllyTurn(NewTurnAction nta1, NewTurnAction nta2){
		//Reset Status?
		
		NewTurnAction ntaAllyReset=null;
		if (ally.getStatus()>0 && (ally.getStatus() == 1 || ally.getStatus() == 3 || ally.getStatus() == 5)){ 
			if (NewTurnAction.Bernoulli(GeometricToBernoulli(ally.getStatus()))=="Yes"){
				ntaAllyReset = new NewTurnAction.TA_ResetStatus(this, ally, "", ally.getStatus());
				turn.addTurnAction(ntaAllyReset);
			}
			else{
				if(ally.getStatus() == 1){
					NewTurnAction ntaSleep = new NewTurnAction.TA_DisplayEffect(this, ally.getName() + " is fast asleep!", "");
					turn.addTurnAction(ntaSleep);
					return;
				}
				if(ally.getStatus() == 3){
					NewTurnAction ntaSleep = new NewTurnAction.TA_DisplayEffect(this, ally.getName() + " is fully paralyzed!", "");
					NewTurnAction ntaSleep2 = new NewTurnAction.TA_DisplayEffect(this, ally.getName() + " has no somatosensation!", "");
					turn.addTurnAction(ntaSleep);
					turn.addTurnAction(ntaSleep2);
					return;
				}
				if(ally.getStatus() == 5){
					NewTurnAction ntaSleep = new NewTurnAction.TA_DisplayEffect(this, ally.getName() + " is frozen solid!", "");
					turn.addTurnAction(ntaSleep);
					return;
				}
			}
		}
		
		if(allyBound){
			NewTurnAction ntaCantMove = new NewTurnAction.TA_DisplayEffect(this, ally.getName() + " can't move!", "");
			turn.addTurnAction(ntaCantMove);
			return;
		}
		
		//For Ally, PAR, SLP, FRZ. FIXME
//		if (ntaAllyReset!=null && (ally.getStatus()==1 || ally.getStatus()==3 || ally.getStatus()==5)){
//			if (NewTurnAction.Bernoulli(this.ProbOfAttackWithStatus(ally.getStatus()))=="Yes"){
//				
//				//Maintain the order below. Approx 25% chance of snapping out of confusion 
//				if(allyIsConfused && NewTurnAction.Bernoulli(SnapConfProb)=="Yes"){
//					NewTurnAction ntaSnapped = new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName() + " snapped out of confusion!");
//					turn.addTurnAction(ntaSnapped);
//					this.setAllyConfusion(false);
//				}
//				if(allyIsConfused){
//					NewTurnAction ntaIsConfused = new NewTurnAction.TA_DisplayEffect(this, ally.getName() + " is confused!","");
//					turn.addTurnAction(ntaIsConfused);
//					//Hurts itself?
//					if (NewTurnAction.Bernoulli(HurtsSelfProb)=="Yes"){
//					NewTurnAction ntaHIIC = new NewTurnAction.TA_HurtItselfInConfusion(this, ally, "");
//					turn.addTurnAction(ntaHIIC);
//					}
//					else{
//						turn.addTurnAction(nta1);
//						if (nta2!=null){turn.addTurnAction(nta2);}
//						
//					}
//				}
//				else{
//					turn.addTurnAction(nta1);
//					if (nta2!=null){turn.addTurnAction(nta2);}
//				}
//				
//			}
////			else{
////				NewTurnAction ntaUnable= new NewTurnAction.TA_UnableToAttack(this, ally, "", ally.getStatus());
////				turn.addTurnAction(ntaUnable);
////			}
//		}
		//else{
			//Maintain the order below. Approx 25% chance of snapping out of confusion 
			if(allyIsConfused && NewTurnAction.Bernoulli(SnapConfProb)=="Yes"){
				NewTurnAction ntaSnapped = new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName() + " snapped out of confusion!");
				turn.addTurnAction(ntaSnapped);
				this.setAllyConfusion(false);
			}
			if(allyIsConfused){
				NewTurnAction ntaIsConfused = new NewTurnAction.TA_DisplayEffect(this, ally.getName() + " is confused!","");
				turn.addTurnAction(ntaIsConfused);
				//Hurts itself?
				if (NewTurnAction.Bernoulli(HurtsSelfProb)=="Yes"){
				NewTurnAction ntaHIIC = new NewTurnAction.TA_HurtItselfInConfusion(this, ally, "");
				turn.addTurnAction(ntaHIIC);
				}
				else{
					turn.addTurnAction(nta1);
					if (nta2!=null){turn.addTurnAction(nta2);}
				}
			}
			else{
				turn.addTurnAction(nta1);
				if (nta2!=null){turn.addTurnAction(nta2);}
			}
		//}
	
	}

	public void executeEnemyTurn(NewTurnAction nta1, NewTurnAction nta2){
		//If there is a status issue, calculate the probability and determine if it gets over the issue.
		//Can't be poisoned, nothing can be done about that.
		

		
		NewTurnAction ntaEnemyReset=null;
		if (enemy.getStatus()>0 && (enemy.getStatus() == 1 || enemy.getStatus() == 3 || enemy.getStatus() == 5)){
			//Geometric to Bernoulli calculates the probability that the status REMAINS. 
			//Thus, the 'if' statement equates to "No". 
			if (NewTurnAction.Bernoulli(GeometricToBernoulli(enemy.getStatus()))=="Yes"){
				ntaEnemyReset = new NewTurnAction.TA_ResetStatus(this, enemy, "Enemy ", enemy.getStatus());
				turn.addTurnAction(ntaEnemyReset);
			}
			else{
				if(enemy.getStatus() == 1){
					NewTurnAction ntaSleep = new NewTurnAction.TA_DisplayEffect(this, "Enemy " + enemy.getName() + " is fast asleep!", "");
					turn.addTurnAction(ntaSleep);
					return;
				}
				if(enemy.getStatus() == 3){
					NewTurnAction ntaSleep = new NewTurnAction.TA_DisplayEffect(this, "Enemy " + enemy.getName() + " is fully paralyzed!", "");
					NewTurnAction ntaSleep2 = new NewTurnAction.TA_DisplayEffect(this, enemy.getName() + " has no somatosensation!", "");
					turn.addTurnAction(ntaSleep);
					turn.addTurnAction(ntaSleep2);
					return;
				}
				if(enemy.getStatus() == 5){
					NewTurnAction ntaSleep = new NewTurnAction.TA_DisplayEffect(this, "Enemy " + enemy.getName() + " is frozen solid!", "");
					turn.addTurnAction(ntaSleep);
					return;
				}
			}
		}

		if(enemyBound){
			NewTurnAction ntaCantMove = new NewTurnAction.TA_DisplayEffect(this, "Enemy " +  enemy.getName() + " can't move!", "");
			turn.addTurnAction(ntaCantMove);
			return;
		}
		
		//For Enemy PAR, SLP, FRZ.
//		if (ntaEnemyReset==null && (enemy.getStatus()==1 || enemy.getStatus()==3 || enemy.getStatus()==5)){
//			if (NewTurnAction.Bernoulli(this.ProbOfAttackWithStatus(enemy.getStatus()))=="Yes"){
//				//Maintain the order below. Approx 25% chance of snapping out of confusion 
//				if(enemyIsConfused && NewTurnAction.Bernoulli(SnapConfProb)=="Yes"){
//					NewTurnAction ntaSnapped = new NewTurnAction.TA_DisplayAutoWrapEffect(this, "Enemy " + enemy.getName() + " snapped out of confusion!");
//					turn.addTurnAction(ntaSnapped);
//					this.setEnemyConfusion(false);
//				}
//				if(enemyIsConfused){
//					NewTurnAction ntaIsConfused = new NewTurnAction.TA_DisplayEffect(this, "Enemy " + enemy.getName() + " is confused!","");
//					turn.addTurnAction(ntaIsConfused);
//					//Hurts itself?
//					if (NewTurnAction.Bernoulli(HurtsSelfProb)=="Yes"){
//					NewTurnAction ntaHIIC = new NewTurnAction.TA_HurtItselfInConfusion(this, enemy, "Enemy ");
//					turn.addTurnAction(ntaHIIC);
//					}
//					else{
//						turn.addTurnAction(nta1);
//						if (nta2!=null){turn.addTurnAction(nta2);}
//					}
//				}
//				else{
//					turn.addTurnAction(nta1);
//					if (nta2!=null){turn.addTurnAction(nta2);}
//				}
//				
//			}
//			else{
//				NewTurnAction ntaUnable= new NewTurnAction.TA_UnableToAttack(this, enemy, "Enemy ", enemy.getStatus());
//				turn.addTurnAction(ntaUnable);
//			}
//		}
//		else{
			//No problem? Then just add the attack.
			//Maintain the order below. Approx 25% chance of snapping out of confusion 
			if(enemyIsConfused && NewTurnAction.Bernoulli(SnapConfProb)=="Yes"){
				NewTurnAction ntaSnapped = new NewTurnAction.TA_DisplayAutoWrapEffect(this, "Enemy " + enemy.getName() + " snapped out of confusion!");
				turn.addTurnAction(ntaSnapped);
				this.setEnemyConfusion(false);
			}
			if(enemyIsConfused){
				NewTurnAction ntaIsConfused = new NewTurnAction.TA_DisplayEffect(this, "Enemy " + enemy.getName() + " is confused!","");
				turn.addTurnAction(ntaIsConfused);
				//Hurts itself?
				if (NewTurnAction.Bernoulli(HurtsSelfProb)=="Yes"){
				NewTurnAction ntaHIIC = new NewTurnAction.TA_HurtItselfInConfusion(this, enemy, "Enemy ");
				turn.addTurnAction(ntaHIIC);
				}
				else{
					turn.addTurnAction(nta1);
					if (nta2!=null){turn.addTurnAction(nta2);}
				}
			}
			else{
				turn.addTurnAction(nta1);
				if (nta2!=null){turn.addTurnAction(nta2);}
			}
//		}
	}
	

	
	private class BattleStartListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(playerX == 10){
				if(rivalX < 385)
					rivalX++;
				else{
					SysOut.print("ROOT: " + rootMenu);
					SysOut.print("ALLY VIS: " + allyVis);
					SysOut.print("ENEMY VIS: " + true);
					SysOut.print("Player X: " + playerX);
					SysOut.print("Rival X: " + rivalX);
					enemyVis = true;
					//TODO=============
					M2.setBattleCry(enemy.getDexNum());
					M2.playFX(M2.BATTLE_CRY);
					SysOut.print("Called");
					//=============
					battleStartTimer.stop();
				}	
			}
			else{
				if(playerX > -145)
					playerX--;
				else{
					SysOut.print("ROOT: " + rootMenu);
					SysOut.print("ALLY VIS: " + allyVis);
					SysOut.print("ENEMY VIS: " + true);
					SysOut.print("Player X: " + playerX);
					SysOut.print("Rival X: " + rivalX);
					allyVis = true;
					//TODO=============
					M2.setBattleCry(ally.getDexNum());
					M2.playFX(M2.BATTLE_CRY);
					//=============
					battleStartTimer.stop();
					rootMenu = true;
				}
			}
			_this.repaint();
		}
	}
	
	private class BattleEndListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			enemyVis = false;
			if(rivalX > 231){
				rivalX--;
			}
			else{
				battleEndTimer.stop();
			}
			_this.repaint();
		}
	}
	
	private class GeneralBattleTimer implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//Sand-Attack
			if(GBSelect==28){
				_this.GB_SandAttack();
				if(GBAnimDir!="NullCheck"){
					_this.repaint();
				}
			}
			//Tackle
			else if(GBSelect==33){
				_this.GB_Tackle();
				_this.repaint();
			}
			//Fainting
			else if(GBSelect==166){
				_this.GB_Faint();
				_this.repaint();
			}
			else{
				//If it doesn't match an attack, just stop.
				GBTimer.stop();
			}
			
		}
	}
	
	public void GB_Faint(){
		if(GBTick==0){
			M2.playFXNoPause(M2.CUT);
		}
		
		if(ally.getCurrentHP()==0){
			if(allyFaintY<ally.getBackImage().getHeight()){
				allyFaintY+=2;
			}
			if(allyFaintY>=ally.getBackImage().getHeight()){
				allyFaintY=ally.getBackImage().getHeight();
				GBTick=0;
				allyFaintY=0;
				_this.setAllyVis(false);
				GBTimer.stop();
			}
		}
		else if(enemy.getCurrentHP()==0){
			if(enemyFaintY<enemy.getFrontImage().getHeight()){
				enemyFaintY+=2;
			}
			if(enemyFaintY>=enemy.getFrontImage().getHeight()){
				enemyFaintY=enemy.getFrontImage().getHeight();
				enemyFaintY=0;
				_this.setEnemyVis(false);
				GBTick=0;
				GBTimer.stop();
			}
		}
		
		GBTick++;
	}
	
	public void GB_SandAttack(){
		if(GBTick==0){
			M2.playFXNoPause(M2.CUT);
			hideStats=true;
			GBAnimDir="SandAttack";
			GB_XOffset=20;
			GB_YOffset=100;
			GB_Scale=1;
			GBAnimIndex=0;
			_this.repaint();
		}
		
		//Divisible by 7
		if( (GBTick/10.0)-Math.floor(GBTick/10.0)==0){
			System.out.println("anim: " + GBTick);
			hideStats=true;
			GBAnimIndex++;
			
		}
		
		if(GBAnimIndex>=19){
			GBTick=0;
			GBAnimIndex=0;
			hideStats=false;
			GBTimer.stop();
		}		
		GBTick++;
	}
	
	public void GB_Blink(double rate, int iterations, int user){
		if(!GBTimer.isRunning()){
			GBTimer.start();
		}
		if( (GBTick/rate)-Math.floor(GBTick/rate)==0){
			if(user==0){
				//if user is 0, victim is enemy.
				enemyVis=!enemyVis;
			}
			else if(user==1){
				allyVis=!allyVis;
			}
		}
		if(GBTick>=rate*iterations){
			GBTick=0;
			if(user==0){
				//if user is 0, victim is enemy.
				enemyVis=true;
			}
			else if(user==1){
				allyVis=true;
			}
			GBTimer.stop();
			return;
		}
		GBTick++;
	}
	
	public void GB_Pause(int length){
		GBTick++;
		if(GBTick>length){
			GBTick=0;
			GBTimer.stop();
			return;
		}
	}
	
	public void GB_HorizShift(double rate, int iterations, int unitDirection, int user){
		if(!GBTimer.isRunning()){
			GBTimer.start();
		}
		if(rate>=1){
			//To slowly move
			if( (GBTick/rate)-Math.floor(GBTick/rate)==0){
				if(user==0){
					GB_AllyX=GB_AllyX+unitDirection;
				}
				else if(user==1){
					GB_EnemyX=GB_EnemyX+unitDirection;
				}
			}
			//To stop
			if(GBTick>=rate*iterations){
				GBTick=0;
				GBTimer.stop();
				return;
			}
			
			GBTick++;
		}
		else if (rate<1){
			//To advance in bigger jumps
			int rateInv=Math.round((float)(1.0/rate));
			if(user==0){
				GB_AllyX=GB_AllyX+unitDirection*rateInv;
			}
			else if(user==1){
				GB_EnemyX=GB_EnemyX+unitDirection*rateInv;
			}
			
			//To stop
			if(GBTick>iterations){
				GBTick=0;
				GBTimer.stop();
				return;
			}
			GBTick++;
		}
	}
	
	
	public void GB_Tackle(){
		M2.playFXNoPause(M2.CUT);
		this.GB_HorizShift(10, 15, 1, GB_User);
		this.GB_HorizShift(10, 15, -1, GB_User);
		this.GB_Pause(1000);
		M2.playFXNoPause(M2.CUT);
		this.GB_Blink(35, 4, GB_User);
		}
	
	
	public int getBattleLoc(){
		return battleLoc;
	}
	
	public void initializeBattle(Player p, Pokemon wild, int battleLocation){
		this._busy = true;	
	//	this._t.stop();
		
		int i = 0;
		while(ally == null || ally.getCurrentHP() == 0){
			ally = p.getAllActive().get(i);
			i++;
		}
		enemy = wild;
		
		if(_gbs.getPlayer().getPokedex() != null && !_gbs.getPlayer().getPokedex().hasSeen(wild.getDexNum())){
			_gbs.getPlayer().getPokedex().addToWatchList(wild);
		}
		
		battleType = WILD;
		enemyVis = true;
		this.resetAllMenus();
		
		battleLoc = battleLocation;
		
		turn = null;
		this.resetAllyStages();
		this.resetEnemyStages();
		
		this.pickBattleBG(battleLocation);
		
		try{
		statsBG=ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/StatsBG2.png"));
		}
		catch(IOException ioe){}
		
		this.displayText("Wild " + wild.getName() + " appeared!","");	
		
		M2.setBattleCry(this.getEnemy().getDexNum());
		M2.playFX(M2.BATTLE_CRY);
	}
	
	public void initializeBattle(Player p, Trainer t, int battleLocation) throws IOException{
		this._busy = true;
	//	this._t.stop();
		
		rivalBelt = t.getBelt();
		
		int i = 0;
		while(ally == null || ally.getCurrentHP() == 0){
			ally = p.getAllActive().get(i);
			i++;
		}
		enemy = rivalBelt.get(0);
		
		if(_gbs.getPlayer().getPokedex() != null && !_gbs.getPlayer().getPokedex().hasSeen(enemy.getDexNum())){
			_gbs.getPlayer().getPokedex().addToWatchList(enemy);
		}
		
		trainer = t;
		this.resetAllMenus();
		
		rivalX = 230;
		playerX = 10;
		
		battleLoc = battleLocation;
		
		battleType = TRAINER;
		
		turn = null;
		this.resetAllyStages();
		this.resetEnemyStages();
		this.pickBattleBG(battleLoc);
		try{
			statsBG=ImageIO.read(this.getClass().getResource("/PokemonFiles/BattleBG/StatsBG1.png"));
			}
		catch(IOException ioe){}
		rivalSprite = t.getBattleImage();
		
		String trainerName = t.getName();
		if(trainerName.indexOf("Rival") != -1){
			trainerName = t.getTrueName();
		}
		
		this.displayAutoWrapText(trainerName + " wants to fight!");				
	}
	
	

	public void A_Button(){
		if(!GBTimer.isRunning()){
			//Continually check for Pkmn who have fought.
			if(_foughtBelt != null && !_foughtBelt.contains(ally.getBelt())){
				_foughtBelt.add(ally.getBelt());
			}
			if(_gbs.getPlayer().getAllItems().get(Item.EXP_ALL).getRemaining() > 0){
				_foughtBelt.clear();
				for(int i = 0; i < _gbs.getPlayer().getAllActive().size();i++){
					_foughtBelt.add(_gbs.getPlayer().getActivePokemon(i).getBelt());
				}
			}
			
			//Immediately turns off stat panel upon A Button.
			//Only occurs during level up, so this is fine.
			if(statPanel && !t_text.isRunning()){
				this.setStatPanelVis(false);
			}
			
			if(turn == null){
				if(t_text.isRunning() || t_hp.isRunning() || t_xp.isRunning()){
					if(t_text.isRunning())
						t_text.setDelay(5);
				}
				else{
					if(playerX == 10 && !battleStartTimer.isRunning()){
						if(battleType == TRAINER){
							if(rivalX == 230){
								battleStartTimer.start();
								String trainerName = (trainer.getName() == "Rival" ? player._rivalName : trainer.getName());
								this.displayAutoWrapText(trainerName + " sent out " + trainer.getBelt().get(0).getName() + "!");
								
								//TODO=============
								//M2.setBattleCry(trainer.getBelt().get(0).getDexNum());
								//M2.playFX(M2.BATTLE_CRY);
								//=============
							}
							else{
								playerX = 9;
								battleStartTimer.start();
								this.displayAutoWrapText("Go, " + ally.getName() + "!");
								//TODO=============
								//M2.setBattleCry(ally.getDexNum());
								//M2.playFX(M2.BATTLE_CRY);
								//=============
							}
						}	
						else{
							//SysOut.print("WTF");
							playerX = 9;
							battleStartTimer.start();
							this.displayAutoWrapText("Go, " + ally.getName() + "!");
							//TODO=============
							//M2.setBattleCry(ally.getDexNum());
							//M2.playFX(M2.BATTLE_CRY);
							//=============
						}
					}
					else{
						if(rootMenu && !itemMenu && !pkmnMenu && !attackMenu){
							
							
					//ATTACK AND ITEMS
							if(cursor.x == 261){ //Work on struggle. If all PP is 0, automatically set player's turn as StruggleAttack
								if(cursor.y == 327){
									if(nextTurn == null){
										int noPP = 0;
										for(int i = 0; i < ally.getAttacks().size(); i++){
											if(ally.getAttacks().get(i).getCurrentPP() == 0) noPP++;
										}
										if(noPP < ally.getAttacks().size()){
											attackMenu = true;
											cursor.x = 12;
										}	
										else{
									//	STRUGGLE
											attackMenu = false;
											rootMenu = false;
											cursor.y = 327;
											
											//Ally Struggle
											NewTurnAction struggle = new NewTurnAction.TA_DisplayEffect(this, ally.getName() + " has no moves left!","");
											NewTurnAction struggle2 = new NewTurnAction.TA_Struggle(this, ally, enemy);
											
											//Enemy Turns												
											int choice = this.getEnemyAttackChoice();
											//FIXM
											NewTurnAction ntaEnemy = null;
											if(choice != -1){
												ntaEnemy = new NewTurnAction.TA_BasicAttack(this, enemy, ally, "Enemy ",enemy.getAttacks().get(choice));
												if(enemy.getAttacks().get(choice).getPower() == 0)
													ntaEnemy = new NewTurnAction.TA_NonDamagingAttack(this, enemy, ally, "Enemy ", enemy.getAttacks().get(choice));
											}
											else{
												ntaEnemy = new NewTurnAction.TA_Struggle(this, enemy, ally);
											}
											//Who goes first?
											this.determineWhoGoesFirst(ally, enemy);
																					
											turn = new NewTurn();
											
											if(first == ALLY_FIRST){
												this.executeAllyTurn(struggle, struggle2);
												this.executeEnemyTurn(ntaEnemy,null);
											}
											if(first == ENEMY_FIRST){
												this.executeEnemyTurn(ntaEnemy,null);
												this.executeAllyTurn(struggle, struggle2);
											}
											
											turn.nextStage();
										}
									}
									else{
											
										turn = new NewTurn();
	
										//If equal, consider next their Pokemon speed.
										this.determineWhoGoesFirst(ally, enemy);
									
										int choice = this.getEnemyAttackChoice();
										
										NewTurnAction ntaEnemy = null;
										if(choice != -1){
											ntaEnemy = new NewTurnAction.TA_BasicAttack(this, enemy, ally, "Enemy ",enemy.getAttacks().get(choice));
											if(enemy.getAttacks().get(choice).getPower() == 0)
												ntaEnemy = new NewTurnAction.TA_NonDamagingAttack(this, enemy, ally, "Enemy ", enemy.getAttacks().get(choice));
										}
										else{
											ntaEnemy = new NewTurnAction.TA_Struggle(this, enemy, ally);
										}
										if(first == ALLY_FIRST){
											this.executeAllyTurn(nextTurn, null);
											this.executeEnemyTurn(ntaEnemy, null);
										}
										if(first == ENEMY_FIRST){
											this.executeEnemyTurn(ntaEnemy, null);
											this.executeAllyTurn(nextTurn, null);										
										}
										
										rootMenu = false;
										cursor.y = 327;
	
										turn.nextStage();
									}
								}
								else{
								//ITEM
									//FIXM - new Turn: Switch out new Pokemon & enemy choice
									
									
									itemMenu = true;
									itemSelectMenu = false;
									rootMenu = false;
									cursor.x = 10;
									cursor.y = 10;
									
									//THIS DOESNT WORK. Yet.
									
								}
							}
							
					//PKMN and RUN
							else{
								if(cursor.y == 327){
								//	PKMN
								//	SysOut.print("PKMN");
									rootMenu = false;
									pkmnMenu = true;
									this.displayAutoWrapText("Choose a Pokemon to switch into battle.");
									cursor.x = 7;
									cursor.y = p_1st;
								}
								else{
								//	RUN
									if(battleType == TRAINER){
										rootMenu = false;
										cantRun = true;
										this.displayAutoWrapText("You can't run from a trainer battle!");
									}
									else{
	
								//		SysOut.print("Got Away Safely OR Can't Escape!");
									//	Calculate chance of escape, set cantEscape to true or false
									//	End of each "Turn" iteration sets cantEscape back to true?
										if(escape == ""){
											//escape = NewTurnAction.Bernoulli(escapeProb);
											if(NewBattleScreen.canEscape(ally.getSpeed(), enemy.getSpeed(), runTurns)){
												escape = "Yes";
											}
											else{
												escape = "No";
											}
										}
										SysOut.print("escape=" + escape);
										if(escape == "Yes"){
											rootMenu = false;
											cantRun = true;
											this.displayText("Got away safely...","");
										}
										else{
											rootMenu = false;
											cantRun = true;
											this.displayText("Can't escape!", "");
											this.setAllyTurnComplete(true);
											this.endAllyTurnNow();
										}
									}
	
								}
							}
						}
						else{
							
							if(itemMenu){
								
	//							//To exit out of the item menu post-item-usage.
								this.endAllyTurnNow();
								
								//Restore PP
								if(attackMenu){
									//Reset delta's value
									_deltaPP=0;
									
									//Find out the attack we're restoring.
									selectedAttack = 0;
									
									if(cursor.y == 327-PPshift){
										if(cursor.x == 12) selectedAttack = 0;
										else selectedAttack = 1;
									}
									else{
										if(cursor.x == 12) selectedAttack = 2;
										else selectedAttack = 3;
									}
	
									Attack PPattack = selected.getAttacks().get(selectedAttack); 
									
									if (PPattack.getCurrentPP()<PPattack.getMaxPP()){
										//Get information about delta HP and its user.
										if ( (PPattack.getMaxPP()-PPattack.getCurrentPP()) < currentItem.getEffect()){
											_deltaPP=(PPattack.getMaxPP()-PPattack.getCurrentPP());
										}
										else{
											_deltaPP=currentItem.getEffect();
										}
										affectedPkmn = selected.getName();
										
										currentItem.useItem(this,selected,enemy,selectedAttack);
										
									}
									else{
										//Signifies to the PaintC0mponent to throw a "full PP" warning.
										_deltaPP=-1;
									}
								}
								
								
								if(pkmnMenu && !attackMenu){								
									
									switch((int)cursor.y){
								
									case p_1st: 
										itemPkmn=0;
										break;
								
									case p_2nd:
										itemPkmn=1;
										break;
								
									case p_3rd: 
										itemPkmn=2;
										break;
								
									case p_4th:
										itemPkmn=3;
							 			break;
								
									case p_5th: 
										itemPkmn=4;
							 			break;
								
									case p_6th: 
										itemPkmn=5;
							 			break; 
									}
									
									Pokemon p = player.getAllActive().get(itemPkmn); //-> Based on where cursor is
									
									if (currentItem.getType()=="PP"){
										//Open Attack Menu
										if(!attackMenu){
											attackMenu=true;
											previousPkmnCoordY=cursor.y;
											previousPkmnCoordX=cursor.x;
											cursor.x = 12;
											cursor.y = 327-PPshift;
										}
									}
									
									if (currentItem.getType()=="Status"){
										_fixedStatus=0;
										//Either it must match the status, or it has to be a full heal.
										if (p.getStatus()==currentItem.getEffect() || currentItem.getEffect()==9000){
											//Determine effect FIRST
											_fixedStatus = p.getStatus();
											
											affectedPkmn=p.getName();
											
											//THEN, use the item
											currentItem.useItem(this,p,enemy,0);
											
										}
										else{
											this.displayText("It won't have any effect!", "");
										}
									}
									if (currentItem.getType()=="Elixir"){
										_deltaHP=0;
										_deltaPP=0;
										
										//Affects all PP moves, so if all moves have full PP, no effect.
										int Total=p.getAttacks().size();
										int runningTotal=0;
										for (int i=0; i<Total;i++){
											if(p.getAttacks().get(i).getCurrentPP()==p.getAttacks().get(i).getMaxPP()){
												runningTotal++;
											}
										}
										
										if (runningTotal<Total){
											//It doesn't matter what the value is, as long as it's nonzero. 										
											_deltaPP = 9000;
											_deltaHP = 9000;										
											
											affectedPkmn=p.getName();
											
											//Use the item
											currentItem.useItem(this,p,enemy,0);
											
										}
										else{
											this.displayText("It won't have any effect!", "");
										}
									}
									if (currentItem.getType()=="HP"){
										//Reset delta's value
										_deltaHP=0;
										if (p.getCurrentHP()!=p.getMaxHP() && p.getCurrentHP()!=0){
											//Get information about delta HP and its user.
											if ( (p.getMaxHP()-p.getCurrentHP()) < currentItem.getEffect()){
												_deltaHP=(p.getMaxHP()-p.getCurrentHP());
											}
											else{
												_deltaHP=currentItem.getEffect();
											}
											affectedPkmn = p.getName();
											
											currentItem.useItem(this,p,enemy,0);
											
										}
										else{
											this.displayAutoWrapText(p.getName()+ " is already at full HP!");
										}
										
										if (p.getCurrentHP()==0){
											this.displayText("It won't have any effect!", "");
										}
									}
									
									if (currentItem.getType()=="FullRestore"){
										_fixedStatus=0;
										//Full Restore.
										if ((p.getCurrentHP()!=p.getMaxHP() && p.getCurrentHP()!=0) || p.getStatus()!=0){
											//It doesn't matter what the value is, as long as it's nonzero. 										
											_fixedStatus = 9000;
											_deltaHP = 9000;										
											
											affectedPkmn=p.getName();
											
											//Use the item
											currentItem.useItem(this,p,enemy,0);
											
										}
										else{
											this.displayText("It won't have any effect!", "");
										}
									}
									
									if (currentItem.getType()=="Revive"){
										//Reset delta's value
										_deltaHP=0;
										if (p.getCurrentHP()==0){
											//Get information about delta HP and its user.
											_deltaHP=(int)(p.getMaxHP()*(currentItem.getEffect()/2.0));
											
											affectedPkmn = p.getName();
											
											currentItem.useItem(this,p,enemy,0);
											
										}
										else{
											this.displayText("It won't have any effect!", "");
										}
									}
								}
								if(itemSelectMenu && !pkmnMenu && !attackMenu){
									//SRI_ITEM_MENU
									if(currentItem.isBattleAppropriate()){
										if(currentItem.canUseInstantly()){
											//Create a NewTurnAction and use item right away (e.g. - pokeball)
											
											if (currentItem.getType()=="Boost"){
												turn = new NewTurn();
												
												int boostEffect=currentItem.getEffect();
												boolean maxedOut=false;
												
												switch(boostEffect){
												case 1:
													//accuracy
													if (this.allyAccStage==6){
														NewTurnAction ntaAccBoost= new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName()+"'s accuracy is already maxed out!");
														turn.addTurnAction(ntaAccBoost);
														maxedOut=true;
													}
													break;
												case 2:
													//attack
													if (this.allyAtkStage==6){
														NewTurnAction ntaAtkBoost= new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName()+"'s attack is already maxed out!");
														turn.addTurnAction(ntaAtkBoost);
														maxedOut=true;
													}
													break;
												case 3:
													//defense
													if (this.allyDefStage==6){
														NewTurnAction ntaDefBoost= new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName()+"'s defense is already maxed out!");
														turn.addTurnAction(ntaDefBoost);
														maxedOut=true;
													}
													break;
												case 4:
													//special
													if (this.allySpAtkStage==6){
														NewTurnAction ntaSpAtkBoost= new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName()+"'s special attack is already maxed out!");
														turn.addTurnAction(ntaSpAtkBoost);
														maxedOut=true;
													}
													break;
												case 5:
													//speed
													if (this.allySpdStage==6){
														NewTurnAction ntaSpdBoost= new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName()+"'s speed is already maxed out!");
														turn.addTurnAction(ntaSpdBoost);
														maxedOut=true;
													}
													break;
												case 6:
													//focus
													if(ally.isDireHit()){
														NewTurnAction ntaDireHit= new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName()+"'s focus is already maxed out!");
														turn.addTurnAction(ntaDireHit);
														maxedOut=true;
													}
													break;
												case 7:
													//focus
													if(ally.isGuardSpec()){
														NewTurnAction ntaGuardSpec= new NewTurnAction.TA_DisplayAutoWrapEffect(this, ally.getName()+"is already guarded!");
														turn.addTurnAction(ntaGuardSpec);
														maxedOut=true;
													}
													break;
												}
												
												if (!maxedOut){
													NewTurnAction ntaBoost= new NewTurnAction.TA_ItemPrecursor(this, this.getPlayer(),this.getPlayerItemIndex());
													turn.addTurnAction(ntaBoost);
												}
												itemMenu=false;
												itemSelectMenu=false;
												this.setAllyTurnComplete(true);
												this.A_Button();
											}
											if (currentItem.getType()=="Ball"){
												turn = new NewTurn();
												
												if (!isTrainer()){
													NewTurnAction ntaPokeball= new NewTurnAction.TA_ItemPrecursor(this, this.getPlayer(),this.getPlayerItemIndex());
													turn.addTurnAction(ntaPokeball);
												}
												else{
													NewTurnAction ntaCantCatch= new NewTurnAction.TA_UseItem(this, this.getPlayer(), this.getPlayerItemIndex(), ally, enemy);
													turn.addTurnAction(ntaCantCatch);
												}
												
												itemMenu=false;
												itemSelectMenu=false;
												this.setAllyTurnComplete(true);
												this.A_Button();
											}
										}
										else{
											//set pkmn menu to true (while keeping item menu true),
											// so you can choose the pokemon to use the item on (e.g. - potion)
											if(!pkmnMenu){
											pkmnMenu=true;
											cursor.x = 7;
											cursor.y = p_1st;
											}
										}
									}
									else{
										this.displayText("RA: Now isn't the time to use that!", "");
									}
								
								}
							
								
								else{
								//Switch to pkmnMenu with current item to be used on first Pokemon selected (if possible)
									
									prevNum = 0;
								
									if(cursor.x == 200){
										prevNum = 17;
									}
									
									if(cursor.x == 11){
										prevNum = 34;
									}
									if(cursor.x == 201){
										prevNum = 51;
									}
									
									if(items.size() > 0 && !pkmnMenu){
									
									//	SysOut.print("***********************");
									//	SysOut.print("Item Selected (" + this.getNBSItemIndex() + "): " + items.get(this.getNBSItemIndex()).getName());
									//	SysOut.print("***********************");
									
										if(items.size() > this.getNBSItemIndex())
											currentItem = items.get(this.getNBSItemIndex());
										//This is to return to the same cursor.y after going through pkmnmenu.
										previousItemCoordY=cursor.y;
										previousItemCoordX=cursor.x;
										if(!itemSelectMenu){
											itemSelectMenu=true;
										}
									}
								}
							}
						
							if(pkmnMenu && !itemMenu && !attackMenu){
								int switchedPkmn=0;
								
								switch((int)cursor.y){
							
								case p_1st: 
									switchedPkmn=0;
									break;
							
								case p_2nd: // SysOut.print("NEW POKEMON SELECTED -> 2");
									switchedPkmn=1;
									break;
							
								case p_3rd: 
									switchedPkmn=2;
									break;
							
								case p_4th:
									switchedPkmn=3;
						 			break;
							
								case p_5th: 
									switchedPkmn=4;
						 			break;
							
								case p_6th: 
									switchedPkmn=5;
						 			break; 
								}
								
					 			if(ally.getBelt() == switchedPkmn+1){
					 				this.displayText(ally.getName() +"'s already out!", "");
					 			}
					 			else{ 
									if(player.getAllActive().get(switchedPkmn).getCurrentHP() == 0){
										this.displayText("There's no will to fight!", "");
									}
									else{
										//Switch out New Pokemon
										NewTurnAction ntaSwitch = new NewTurnAction.TA_SwitchPokemon1(this, ally, player.getAllActive().get(switchedPkmn));
	
										//Enemy Choice
										int choice = this.getEnemyAttackChoice();
										
										NewTurnAction ntaEnemy = null;
										if(choice != -1){
											ntaEnemy = new NewTurnAction.TA_BasicAttack(this, enemy, ally, "Enemy ",enemy.getAttacks().get(choice));
											if(enemy.getAttacks().get(choice).getNonDamageType() != 0)
												ntaEnemy = new NewTurnAction.TA_NonDamagingAttack(this, enemy, ally, "Enemy ", enemy.getAttacks().get(choice));
										}
										else{
											ntaEnemy = new NewTurnAction.TA_Struggle(this, enemy, ally);
										}
										turn = new NewTurn();
								
										//Ally always goes first if switching.
										this.executeAllyTurn(ntaSwitch, null);
										this.executeEnemyTurn(ntaEnemy, null);
										
										
										pkmnMenu = false;
										cursor.y = 327;
										turn.nextStage();
									}
								}
								
							}
	
							if(attackMenu){
								selectedAttack = 0;
								//PP items.
								if(cursor.y == 327-PPshift){
									if(cursor.x == 12) selectedAttack = 0;
									else selectedAttack = 1;
								}
								else{
									if(cursor.x == 12) selectedAttack = 2;
									else selectedAttack = 3;
								}
	
		
								//If I didn't open the attack menu to use a PP item or learn new attack, then execute Turn.
								if (!itemMenu && !forgettingAttack){
									if(cursor.y == 327){
										if(cursor.x == 12) selectedAttack = 0;
										else selectedAttack = 1;
									}
									else{
										if(cursor.x == 12) selectedAttack = 2;
										else selectedAttack = 3;
									}								
									
								activeTurn = true;
								rootMenu = false;
								attackMenu = false;
								turn = new NewTurn();
								
								if(ally.getAttacks().get(selectedAttack).getCurrentPP() == 0){
									NewTurnAction ntaNoPP = new NewTurnAction.TA_DisplayEffect(this, "There's no PP left for this move!","");
									turn.addTurnAction(ntaNoPP);
								}
								else if(allyDisabled == selectedAttack){
									NewTurnAction ntaDis = new NewTurnAction.TA_DisplayEffect(this, "That attack is disabled!","");
									turn.addTurnAction(ntaDis);
								}
								else{
									//Ally attacks.
									NewTurnAction ntaAlly = new NewTurnAction.TA_BasicAttack(this, ally, enemy, "", ally.getAttacks().get(selectedAttack));
										//Crucial - Do NOT use the getDamage() formula as this has CONSEQUENCES
									if(ally.getAttacks().get(selectedAttack).getPower() == 0){
										ntaAlly = new NewTurnAction.TA_NonDamagingAttack(this, ally, enemy, "", ally.getAttacks().get(selectedAttack));
									}
									
									//Enemy attacks.
									int choice = this.getEnemyAttackChoice();
									
									NewTurnAction ntaEnemy = null;
									if(choice != -1){
										ntaEnemy = new NewTurnAction.TA_BasicAttack(this, enemy, ally, "Enemy ",enemy.getAttacks().get(choice));
										if(enemy.getAttacks().get(choice).getNonDamageType() != 0)
											ntaEnemy = new NewTurnAction.TA_NonDamagingAttack(this, enemy, ally, "Enemy ", enemy.getAttacks().get(choice));
									}
									else{
										ntaEnemy = new NewTurnAction.TA_Struggle(this, enemy, ally);
									}
									
									if(ally.getAttacks().get(selectedAttack).getPriority() > enemy.getAttacks().get(choice).getPriority()){first = ALLY_FIRST;}
									if(enemy.getAttacks().get(choice).getPriority() > ally.getAttacks().get(selectedAttack).getPriority()){first = ENEMY_FIRST;} 
	
									if(ally.getAttacks().get(selectedAttack).getPriority() == enemy.getAttacks().get(choice).getPriority()){
										this.determineWhoGoesFirst(ally, enemy);
									} 
									
									if(first == ALLY_FIRST){
										this.executeAllyTurn(ntaAlly, null);
										this.executeEnemyTurn(ntaEnemy, null);
									}
									if(first == ENEMY_FIRST){
										this.executeEnemyTurn(ntaEnemy, null);
										this.executeAllyTurn(ntaAlly, null);
									}
								}
								
								turn.nextStage();
							}
						}
							
							if(cantRun){
								if(escape == "Yes"){
									this.resetBattle();
									cantRun = false;
									_gbs.setCurrentPanel(battleLoc);
									_gbs.getCurrentPanel()._darkLevel = 0;
									
									if(_gbs.getSurf()){
										M2.playBG(M2.SURF);
									}
									else{
										if(!_gbs.getMode())
											M2.playBG(_gbs.getCurrentPanel().song);
										else
											M2.playBG(M2.BIKE);
									}
										
									_gbs.repaint();
								}
								else{
									SysOut.print("NO NO NO RUN AWAY");
									rootMenu = true;
									cantRun = false;
									//this.setAllyTurnComplete(true);
								}
							}
						}
					}
				}
			}
			else{
				//Learning a new attack.
				if (attackMenu && forgettingAttack){
					if(cursor.y == 327){
						if(cursor.x == 12) attackToForget = 0;
						else attackToForget = 1;
					}
					else{
						if(cursor.x == 12) attackToForget = 2;
						else attackToForget = 3;
					}
				}
				
				if(t_text.isRunning() || t_hp.isRunning()){
					if(t_text.isRunning())
						t_text.setDelay(5);
				}
				else{
					if(turn.getCurrentStage() < (turn.getListSize()-1)){
						if(!t_xp.isRunning()){
							turn.nextStage();
						}
					}
					else{
						if(ally.getCurrentHP() != 0 && enemy.getCurrentHP() != 0){
						
							//To exit out of the item menu post-item-usage.
							if(_deltaHP>0 || _fixedStatus>0 || _deltaPP>0 || _allyTurnComplete){
								this.endAllyTurnNow();
							}
							
							else{						
								turn = null;
								if(allyRaging){
	//								turn = new NewTurn();
									NewTurnAction rager = new NewTurnAction.TA_BasicAttack(this, ally, enemy, "", ally.getAttacks().get(selectedAttack));
									int choice = this.getEnemyAttackChoice();
									NewTurnAction enemyNTA = null;
									if(enemy.getAttacks().get(choice).getPower() != 0)
										enemyNTA = new NewTurnAction.TA_BasicAttack(this, enemy, ally, "Enemy ", this.getEnemy().getAttacks().get(choice));
									else
										enemyNTA = new NewTurnAction.TA_NonDamagingAttack(this, enemy, ally, "Enemy ", enemy.getAttacks().get(choice));
									if(first == ALLY_FIRST){
										this.executeAllyTurn(rager, null);
										this.executeEnemyTurn(enemyNTA, null);
									}
									else{
										this.executeEnemyTurn(enemyNTA, null);
										this.executeAllyTurn(rager, null);
									}
									turn.nextStage();
								}
								else{
									rootMenu = true;
									activeTurn = false;
									
									this.displayText("", "");
									escape = "";
									
									this.resetRootMenuCursor();								
								}
	
								
							
	
							}
	
						}
						else{
							if(enemy.getCurrentHP() == 0 && !pkmnMenu){// && turn.getList().get(turn.getCurrentStage()).getLine1()=="Would you like to switch Pokemon?"){
								this.setPkmnScreenVis(true);
								this.repaint();
							}
							else
	
							if(pkmnMenu){
								//
								
								//=========================================================
								int pkmnSelected=0;
								
								switch((int)cursor.y){
								
								case p_1st: 
									pkmnSelected=0;
									break;
							
								case p_2nd:
									pkmnSelected=1; 
									break;
							
								case p_3rd: 
									pkmnSelected=2;
									break;
							
								case p_4th:
									pkmnSelected=3;
						 			break;
							
								case p_5th: 
									pkmnSelected=4;
						 			break;
							
								case p_6th: 
									pkmnSelected=5;
						 			break; 
						 			
								}
								//Choose selected pkmn
								if(player.getAllActive().get(pkmnSelected).getCurrentHP() == 0 || player.getAllActive().get(pkmnSelected).getBelt() == ally.getBelt()){
									if(player.getAllActive().get(pkmnSelected).getCurrentHP() == 0)
										this.displayText("There's no will to fight!", "");
									else
										this.displayText(player.getAllActive().get(pkmnSelected).getName() + "'s already out!", "");
								}
								else{
									//- new Turn: Switch out new Pokemon & enemy choice
							
										NewTurnAction ntanext = new NewTurnAction.TA_SwitchPokemon2(_this, player.getAllActive().get(pkmnSelected));
										turn.addTurnAction(ntanext);
											
										NewTurnAction nta2 = null;
										
										if(enemy.getCurrentHP() == 0){
											for(int i = 0; i < trainer.getBelt().size(); i++){
												if(trainer.getBelt().get(i).getCurrentHP() != 0){
													nta2 = new NewTurnAction.TA_EnemySwitching(this, trainer, trainer.getBelt().get(i));
													break;
												}
											}
											turn.addTurnAction(nta2);
											
											pkmnMenu = false;
											turn.nextStage();
										}
										else{
											pkmnMenu = false;
											rootMenu = false;
											cursor.y = 327;
											turn.nextStage();
										}
								}
							}
						}
					}
				}
			}
			this.repaint();
		}
	}
	
	public void B_Button(){
		if(!GBTimer.isRunning()){
			if(itemMenu && _deltaHP==0 && _fixedStatus==0 && _deltaPP<=0){
				//Reset PP warning.
				_deltaPP=0;
				
				if(!itemSelectMenu && !pkmnMenu){
					itemMenu = false;
					rootMenu = true;
					pageTwo = false;
					this.resetRootMenuCursor();
				}
				else{
					if(itemSelectMenu && !pkmnMenu){
						itemSelectMenu=false;
					}
					if(pkmnMenu && !attackMenu){//
						pkmnMenu=false;
						cursor.y=previousItemCoordY;
						cursor.x=previousItemCoordX;
					}
					if (attackMenu){
					attackMenu=false;
					cursor.y=previousPkmnCoordY;
					cursor.x=previousPkmnCoordX;
					}
					
				}
					
					//
			}
				
			if(!t_text.isRunning() && !itemMenu && !pkmnMenu && turn!=null && turn.getList() != null && turn.getCurrentStage() >= 0 && turn.getCurrentStage() < turn.getList().size() && turn.getList().get(turn.getCurrentStage()).getLine1()=="Would you like to switch Pokemon?"){
				
				if(battleType == TRAINER){
				
					NewTurnAction nta2 = null;
					for(int i = 0; i < trainer.getBelt().size(); i++){
						if(trainer.getBelt().get(i).getCurrentHP() != 0){
							nta2 = new NewTurnAction.TA_EnemySwitching(this, trainer, trainer.getBelt().get(i));
							break;
						}
					}
					turn.addTurnAction(nta2);
					
					//SysOut.print("Hit B: Do not switch pokemon");
					
					turn.nextStage();
					pkmnMenu = false;
					turn.nextStage();
					return;
				}
	
			}
				
			if(!itemMenu){		
				if(!t_text.isRunning() && pkmnMenu && ally.getCurrentHP() != 0 && enemy.getCurrentHP() != 0){
					pkmnMenu = false;
					rootMenu = true;
					t_text.stop();
					this.resetRootMenuCursor();
				}
				else{
					if(!t_text.isRunning() && pkmnMenu && ally.getCurrentHP() != 0){
						pkmnMenu = false;
						
						turn = new NewTurn();
						
						NewTurnAction nta2 = null;
						
						for(int i = 0; i < trainer.getBelt().size(); i++){
							if(trainer.getBelt().get(i).getCurrentHP() != 0){
								nta2 = new NewTurnAction.TA_EnemySwitching(this, trainer, trainer.getBelt().get(i));
								turn.addTurnAction(nta2);
								break;
							}
						}
						
						turn.nextStage();
					}
				}
			}
			if(attackMenu && !itemMenu && !forgettingAttack){
				attackMenu = false;
				rootMenu = true;
				this.resetRootMenuCursor();
			}
	
			
			//Specific Case for when an enemy faints.
			if (!t_text.isRunning()&& turn!= null && turn.getList() != null && turn.getCurrentStage() != -1 && wildSwitch){
				
				if(battleType == TRAINER){
				//Just erase the current turn action, its as if it never asked you.
					turn = new NewTurn();
				
					NewTurnAction nta2 = null;
				
					for(int i = 0; i < trainer.getBelt().size(); i++){
						if(trainer.getBelt().get(i).getCurrentHP() != 0){
							nta2 = new NewTurnAction.TA_EnemySwitching(this, trainer, trainer.getBelt().get(i));
							turn.addTurnAction(nta2);
							break;
						}
					}
				
					turn.nextStage();
				}
				if(battleType == WILD){
					if(escape == "")
						if(NewBattleScreen.canEscape(ally.getSpeed(), enemy.getSpeed(), runTurns)) escape = "Yes";
						else escape = "No";
						//escape = NewTurnAction.Bernoulli(escapeProb);
					if(escape == "Yes"){
						rootMenu = false;
						cantRun = true;
						this.displayText("Got away safely...","");
						this.setWildSwitch(false);
						//this.resetBattle();
					}
					else{
						this.displayText("Can't escape!", "");
					}
					
					
				}
			}
			
			//FORGETTING ATTACKS.
			if(!t_text.isRunning() && turn != null && turn.getList() != null && turn.getCurrentStage() != -1 && forgettingAttack && currentRequest){
				this.setDenials(this.getDenials()+1);
				this.A_Button();
			}
			
			this.repaint();
		}
	}
	
	public void Up(){
			if(!GBTimer.isRunning()){
			if(rootMenu && !itemMenu && !pkmnMenu && cursor.y != 327){
				cursor.y = 327;
			}
			else{
				if(attackMenu && _deltaPP<=0){
					cursor.y = 327;
					if(itemMenu){
						cursor.y = 327-PPshift;
					}
				}
				if(pkmnMenu && _deltaHP==0){
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
				if(itemMenu){
					if (!pkmnMenu && !itemSelectMenu && !attackMenu){
						if(cursor.x == 200 || cursor.x == 201){
							if(cursor.y > 10)
								cursor.y -= 18;
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
								cursor.y -= 18;
						}
					}
				}
			}
			this.repaint();
		}
	}
	
	public void Left(){
		if(!GBTimer.isRunning()){
			if(rootMenu && !itemMenu && !pkmnMenu && !attackMenu && cursor.x != 261){
				cursor.x = 261;
			}
			else{
				if(attackMenu && _deltaPP<=0){
					cursor.x = 12;
				}
				if(itemMenu){
					if (!pkmnMenu && !itemSelectMenu && !attackMenu){
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
				}
			}
			this.repaint();
		}
	}
	
	public void Right(){
		if(!GBTimer.isRunning()){
			if(rootMenu && !itemMenu && !pkmnMenu && !attackMenu && cursor.x == 261){
				cursor.x = 329;
			}
			else{
				if(attackMenu && _deltaPP<=0){
					if((cursor.y == 327 && ally.getAttacks().size() > 1) || (cursor.y == 347 && ally.getAttacks().size() > 3)){
						cursor.x = 202;
						}
					
					if(itemMenu){
						if((cursor.y == 327-PPshift && selected.getAttacks().size() > 1) || (cursor.y == 347-PPshift && selected.getAttacks().size() > 3)){
							cursor.x = 202;
							}
					}
									
				}
				if(itemMenu){
					if (!pkmnMenu && !itemSelectMenu && !attackMenu){
						if(cursor.x == 10){
							if(cursor.y <= (items.size()-17)*18)
								cursor.x = 200;
						}
						else{
							if(!pageTwo && items.size() > 34){
								cursor.x = 11;
								cursor.y = 10;
								pageTwo = true;
							}
							else{
								if(cursor.y <= (items.size()-51)*18)
									cursor.x = 201;
							}
						}
					}
				}
			}
			this.repaint();
		}
	}
	
	public void Down(){
		if(!GBTimer.isRunning()){
			if(rootMenu && !itemMenu && !pkmnMenu && !attackMenu && cursor.y == 327){
				cursor.y = 347;
			}
			else{
				if(attackMenu){
					if((cursor.x == 12 && ally.getAttacks().size() > 2) || (cursor.x == 202 && ally.getAttacks().size() > 3))
						cursor.y = 347;
					
					if(itemMenu && _deltaPP<=0){
						if((cursor.x == 12 && selected.getAttacks().size() > 2) || (cursor.x == 202 && selected.getAttacks().size() > 3))
							cursor.y = 347-PPshift;
					}
					
				}
				if(pkmnMenu && _deltaHP==0){
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
				if(itemMenu){
					if (!pkmnMenu && !itemSelectMenu && !attackMenu){
						if(cursor.x == 10){
							if(cursor.y < 298){
								if(cursor.y <= (items.size()-1)*18)
									cursor.y += 18;
							}
							else{
								cursor.x = 200;
								cursor.y = 10;
							}
						}
						else{
							if(cursor.y <= 298){
								if(!pageTwo){
									if(cursor.y <= (items.size()-18)*18)
										if(cursor.y != 298)
											cursor.y += 18;
								}
								else{
		
									if(cursor.y == 298 && cursor.x == 11){
										cursor.x = 201;
										cursor.y = 10;
									}
									else{
										if(cursor.x == 11){
											if(cursor.y <= (items.size()-35)*18){
												cursor.y += 18;
											
											}
										}
										else{
											if(cursor.y <= (items.size()-52)*18){
												cursor.y += 18;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			this.repaint();
		}
	}
	
	public void drawAttackAnim(Graphics2D g2,int index, String dir, int x, int y, double s){
		
		BufferedImage resizedAnim;
		try {
			resizedAnim = ImageIO.read(this.getClass().getResource("/PokemonFiles/AttackImages/"+ dir+"/"+ index +".png"));
			int newW = (int)(resizedAnim.getWidth() * s);
			int newH = (int)(resizedAnim.getHeight() * s);
			g2.drawImage(resizedAnim, x, y, newW, newH, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g2 = (Graphics2D) g;
		
		if(bg != null){
			g2.drawImage(bg, null, 0, 0);
			if(!hideStats){
				g2.drawImage(statsBG, null, -3, 0);
			}
		}
		
		if(GBTimer.isRunning() && GBSelect!=GB_FAINT){
			this.drawAttackAnim(g2, GBAnimIndex,GBAnimDir,GB_XOffset, GB_YOffset, GB_Scale);
		}
		
		if(!rootMenu && !itemMenu && !pkmnMenu && !attackMenu)
			this.drawText(g2,s1,s2);
		else{
			this.drawText(g2,"","");
			if(rootMenu){
				this.drawRootMenu(g2);
			}
			g2.setFont(this.getFont());
			
			
			
			if(itemMenu){
				g2.setColor(Color.WHITE);
				g2.fill(menuBG);

				g2.setColor(Color.BLACK);
				
				items.clear();
				
				for(int i = 0; i < player.getAllItems().size(); i++){
					if(player.getAllItems().get(i).getRemaining() != 0 && player.getAllItems().get(i).isBattleAppropriate()){
						items.add(player.getAllItems().get(i));
					}
				}
	
				if(!pageTwo){
					for(int i = 0; i < items.size(); i++){
						if(i < 17)
							g2.drawString(items.get(i).getName(), 30, (18*i)+17);
						else
							if(i < 34)
								g2.drawString(items.get(i).getName(), 220, (18*(i-17))+17);
					}
				}
				else{
					for(int i = 34; i < items.size(); i++){
						if(i < 51)
							g2.drawString(items.get(i).getName(), 30, (18*(i-34))+17);
						else
							if(i < 68)
								g2.drawString(items.get(i).getName(), 220, (18*(i-51))+17);
					}
				}
				
				g2.draw(cursor);
				
				//Description Menu.
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
			}
			
					
			
			if(pkmnMenu){
				g2.setColor(Color.WHITE);
				g2.fill(menuBG);
				
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
					
					Rectangle2D.Double outline = new Rectangle2D.Double();
					outline.height = 15;
					outline.width = 100;
					outline.x = 170;
					outline.y = 15 + (52*i);
					g2.draw(outline);
					
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
					if(focus.getCurrentHP() != 0)
						g2.drawString(focus.getStatusAcro(), 345, 27 + (52*i));
					else
						g2.drawString("FNT", 345, 27 + (52*i));
				}
				
				g2.draw(cursor);
			}
			
			if(attackMenu){					
				Rectangle2D.Double textBG = new Rectangle2D.Double();
				textBG.x = 0;
				textBG.y = 314;
				textBG.width = this.getWidth();
				textBG.height = 50;
				
				g2.setColor(Color.WHITE);
				g2.fill(textBG);
				
				int	atkL=20;
				int	atkR=210;
				int atkU=334;
				int atkD=354;
				
				Rectangle2D.Double outline = new Rectangle2D.Double();
				outline.x = 5;
				outline.y = 319;
				outline.width = this.getWidth() - 10;
				outline.height = 40;
				
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(2));
				
				//For PP Menu.
				
				selected = ally;
				
				Rectangle2D.Double PPoutline = new Rectangle2D.Double();
				PPoutline.x = 5;
				PPoutline.y = 319-PPshift;
				PPoutline.width = this.getWidth() - 10;
				PPoutline.height = 40;
				if(itemMenu){ 
					atkD=atkD-PPshift;
					atkU=atkU-PPshift;
					outline.y =outline.y;
					g2.draw(PPoutline);
					selected=player.getAllActive().get(itemPkmn);
				}
				
				g2.draw(outline);
				
				switch(selected.getAttacks().size()){
				case 4: if(selected.getAttacks().get(3).getCurrentPP() == 0) 
							g2.setColor(Color.RED);
						if(allyDisabled == 3) g2.setColor(new Color(128,0,128));
						//g2.drawString(ally.getAttacks().get(3).getName() + " (" + ally.getAttacks().get(3).getCurrentPP() + "/" + ally.getAttacks().get(3).getMaxPP() + ")", 195, 354);
						g2.drawString(selected.getAttacks().get(3).getName(), atkR, atkD);
						
						g2.setFont(new Font("Courier New", Font.BOLD, 10));
						String pp4 = selected.getAttacks().get(3).getCurrentPP() + "/" + selected.getAttacks().get(3).getMaxPP();
						g2.drawString(pp4, 352-(pp4.length()*5), atkD);
						g2.setFont(this.getFont());
						
						g2.setColor(Color.BLACK);
						
				case 3:	if(selected.getAttacks().get(2).getCurrentPP() == 0) 
							g2.setColor(Color.RED);
						if(allyDisabled == 2) g2.setColor(new Color(128,0,128));
					//	g2.drawString(ally.getAttacks().get(2).getName() + " (" + ally.getAttacks().get(2).getCurrentPP() + "/" + ally.getAttacks().get(2).getMaxPP() + ")", 25, 354);
						g2.drawString(selected.getAttacks().get(2).getName(), atkL, atkD);
						
						g2.setFont(new Font("Courier New", Font.BOLD, 10));
						String pp3 = selected.getAttacks().get(2).getCurrentPP() + "/" + selected.getAttacks().get(2).getMaxPP();
						g2.drawString(pp3, 170-(pp3.length()*5), atkD);
						g2.setFont(this.getFont());
						
						g2.setColor(Color.BLACK);
						
				case 2: if(selected.getAttacks().get(1).getCurrentPP() == 0) 
							g2.setColor(Color.RED);
					if(allyDisabled == 1) g2.setColor(new Color(128,0,128));
						//g2.drawString(ally.getAttacks().get(1).getName() + " (" + ally.getAttacks().get(1).getCurrentPP() + "/" + ally.getAttacks().get(1).getMaxPP() + ")", 195, 334);
						g2.drawString(selected.getAttacks().get(1).getName(), atkR, atkU);
						
						g2.setFont(new Font("Courier New", Font.BOLD, 10));
						String pp2 = selected.getAttacks().get(1).getCurrentPP() + "/" + selected.getAttacks().get(1).getMaxPP();
						g2.drawString(pp2, 352-(pp2.length()*5), atkU);
						g2.setFont(this.getFont());
						
						g2.setColor(Color.BLACK);
						
				case 1: if(selected.getAttacks().get(0).getCurrentPP() == 0)
							g2.setColor(Color.RED);
						if(allyDisabled == 0) 
							g2.setColor(new Color(128,0,128));
						//g2.drawString(ally.getAttacks().get(0).getName() + " (" + ally.getAttacks().get(0).getCurrentPP() + "/" + ally.getAttacks().get(0).getMaxPP() + ")", 25, 334);
						g2.drawString(selected.getAttacks().get(0).getName(), atkL, atkU);
						
						g2.setFont(new Font("Courier New", Font.BOLD, 10));
						String pp1 = selected.getAttacks().get(0).getCurrentPP() + "/" + selected.getAttacks().get(0).getMaxPP();
						g2.drawString(pp1, 170-(pp1.length()*5), atkU);
						g2.setFont(this.getFont());
						
						g2.setColor(Color.BLACK);
					break;
				}
				
				g2.draw(cursor);
			}
		}
	
		int offset = 180; 
		
		if(allyVis && !pkmnMenu && !itemMenu && ally != null){
			BufferedImage resizedAlly;
			if(allyFaintY>0){
				resizedAlly=ally.getBackImage().getSubimage(0, 0, ally.getBackImage().getWidth(), ally.getBackImage().getHeight()-allyFaintY);
			}
			else{
				resizedAlly=ally.getBackImage();
			}
			double scale=1.5;
			if(allyMinimized){ scale = 0.5;
//				SysOut.print("scaled down:" 
//						+scale);
			}
			int newW = (int)(resizedAlly.getWidth() * scale);
			int newH = (int)(resizedAlly.getHeight() * scale);
			
			if(!allyFlying && !allyDug){
				//EDITED: Equation in place to make bottom edge flush with menu.
				g2.drawImage(resizedAlly, 35+GB_AllyX, 280-resizedAlly.getHeight()+GB_AllyY+15, newW+GB_AllyW, newH+GB_AllyH, null);
			}
			if(!hideStats){
				//g2.drawImage(ally.getBackImage(), null, 50, 237);
				g2.drawString(ally.getName() + (ally.getStatus()== 0 ? " Lv." + ally.getLevel() : " (" + ally.getStatusAcro() + ")"), 15+offset, 30+offset);
				
				allyHP.x = offset + 16;
				allyHP.y = offset+41;
				allyHP.width = (int) (98 * ((double) ally.getCurrentHP() / ally.getMaxHP()));
				
				if ((double) ally.getCurrentHP() / ally.getMaxHP() > .5) {
					g2.setColor(Color.GREEN);
				} else {
					if ((double) ally.getCurrentHP() / ally.getMaxHP() > .25) {
						g2.setColor(Color.ORANGE);
					} else {
						g2.setColor(Color.RED);
					}
				}
				
				g2.fill(allyHP);
				
				g2.setColor(Color.BLACK);
				
				allyHPO.x = offset+15;
				allyHPO.y = offset+40;
				g2.draw(allyHPO);
				
				g2.setFont(new Font("Courier New", Font.BOLD, 12));
				g2.drawString("HP: " + ally.getCurrentHP() + "/" + ally.getMaxHP(), offset+125, offset+52);
				g2.setFont(this.getFont());
				
				allyXP.x = offset + 16;
				allyXP.y = offset  + 61;
				allyXP.width = (int) (98 * ((double) (ally.getExp()) / (NewBattleScreen.getToNextLevel(ally))));
				
				g2.setColor(new Color(0, 139, 139));
				g2.fill(allyXP);
				g2.setColor(Color.BLACK);
				
				allyXPO.x = offset+15;
				allyXPO.y = offset+60;
				g2.draw(allyXPO);
			}
			

		}
		else{
		//?	
		}
			
		offset = 0;
		
		if(enemyVis && !pkmnMenu && !itemMenu && enemy != null){
			BufferedImage resizedEnemy;
			if(enemyFaintY>0){
				resizedEnemy=enemy.getFrontImage().getSubimage(0, 0, enemy.getFrontImage().getWidth(), enemy.getFrontImage().getHeight()-enemyFaintY);
				System.out.println("height: "+ (enemy.getFrontImage().getHeight()-enemyFaintY));
			}
			else{
				resizedEnemy=enemy.getFrontImage();
			}
			double scale=1.5;
			if(enemyMinimized) scale = 0.5;
			int newW = (int)(resizedEnemy.getWidth() * scale);
			int newH = (int)(resizedEnemy.getHeight() * scale);
			
			if(!enemyFlying && !enemyDug){
				g2.drawImage(resizedEnemy, 220+GB_EnemyX, 42+enemyFaintY+GB_EnemyY, newW+GB_EnemyW, newH+GB_EnemyH, null);
			}
			
			
			if(!hideStats){
				g2.drawString(enemy.getName() + (enemy.getStatus()== 0 ? " Lv." + enemy.getLevel() : " (" + enemy.getStatusAcro() + ")"), 15+offset, 30+offset);
				enemyHP.x = offset + 16;
				enemyHP.y = offset+41;
				enemyHP.width = (int) (98 * ((double) enemy.getCurrentHP() / enemy.getMaxHP()));
				
				if ((double) enemy.getCurrentHP() / enemy.getMaxHP() > .5) {
					g2.setColor(Color.GREEN);
				} else {
					if ((double) enemy.getCurrentHP() / enemy.getMaxHP() > .25) {
						g2.setColor(Color.ORANGE);
					} else {
						g2.setColor(Color.RED);
					}
				}
				
				g2.fill(enemyHP);
				
				g2.setColor(Color.BLACK);
				
				enemyHPO.x = offset+15;
				enemyHPO.y = offset+40;
				g2.draw(enemyHPO);
				
				g2.setFont(new Font("Courier New", Font.BOLD, 12));
				g2.drawString("HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP(), offset+125, offset+52);
				g2.setFont(this.getFont());
			}
		}
		else{
		//?	
		}
			
		
		g2.drawImage(playerSprite, null, playerX, 150);

		
		if(battleType == TRAINER){
			
			if(rivalSprite!=null){
				
				double scale=160.0/((double)rivalSprite.getHeight());
				int newW = (int)(rivalSprite.getWidth() * scale);
				int newH = (int)(rivalSprite.getHeight() * scale);
				
				g2.drawImage(rivalSprite, rivalX, 10, newW, newH, null);
			}
			else{
				//Un-resized
				g2.drawImage(rivalSprite, null, rivalX, 10);
			}
		}
		
		if(playerX == 10){
			for(int i = 0; i < player.getAllActive().size(); i++){
				g2.drawImage(pokeBall, null, 200+(30*i), 212);
				if(player.getAllActive().get(i).getCurrentHP() > 0){
					if(player.getAllActive().get(i).getStatus() != 0){
						deadSlot.x = 201 + (30*i);
						deadSlot.y = 213;
						g2.fill(deadSlot);
					}
				}
				else{
					Font f = new Font("Courier New", Font.BOLD, 32);
					g2.setFont(f);
					g2.drawString("X", 203+(30*i), 234);
				}
			}
			for(int i2 = 0; i2 < 6; i2++){
				deadSlot.x = 352 - (30*i2);
				deadSlot.y = 215;
				g2.draw(deadSlot);
			}
		}
		
		if(battleType == TRAINER && rivalX == 230){
			for(int i = 0; i < rivalBelt.size(); i++){
				g2.drawImage(pokeBall, null, 160-(30*i), 30);
				if(rivalBelt.get(i).getCurrentHP() > 0){
					if(rivalBelt.get(i).getStatus() != 0){
						deadSlot.x = 21 + (30*i);
						deadSlot.y = 31;
						g2.fill(deadSlot);
					}
				}
				else{
					Font f = new Font("Courier New", Font.BOLD, 32);
					g2.setFont(f);
					g2.drawString("X", 33+(30*i), 71);
				}
			}
			for(int i2 = 0; i2 < 6;i2++){ //- rivalBelt.size(); i2++){
				deadSlot.x = 162 - (30*i2);
				deadSlot.y = 32;
				g2.draw(deadSlot);
			}
		}
		if(pkmnMenu && !t_text.isRunning() && !t_hp.isRunning()){
			if(itemSelectMenu){
				if (attackMenu && _deltaPP==0){
					g2.drawString("Restore PP to which move?", 20, 335);
				}
				else if(_deltaHP!=0 || _fixedStatus!=0 || _deltaPP!=0){
						//PP Items
						if(_deltaPP!=0 && _deltaHP==0 && _fixedStatus==0){
							if (_deltaPP<0){
								g2.drawString(selected.getAttacks().get(selectedAttack).getName()+" is already at full PP!", 20, 335);
							}
							else{
								g2.drawString("PP was restored to "+selected.getAttacks().get(selectedAttack).getName()+"!", 20, 335);
							}
						}
						//Full Restore.
						if(_deltaHP!=0 && _fixedStatus!=0 && _deltaPP==0){
							g2.drawString(affectedPkmn + " regained full health!", 20, 335);
						}
						//HP Items
						if(_deltaHP!=0 && _fixedStatus==0 && _deltaPP==0){
							g2.drawString(affectedPkmn + " recovered " + _deltaHP +"HP!", 20, 335);
						}
						//Elixir Items
						if(_deltaHP!=0 && _deltaPP!=0 && _fixedStatus==0){
							g2.drawString(affectedPkmn+"'s PP was restored.", 20, 335);
						}
						//Status Items
						if(_fixedStatus!=0 && _deltaHP==0 && _deltaPP==0){
							String _fixedStatusString;
							switch (_fixedStatus){
							case 0:
								_fixedStatusString= "";
								break;
							case 1:
								_fixedStatusString= " woke up!";
								break;
							case 2:
								_fixedStatusString= " is no longer poisoned!";
								break;
							case 3:
								_fixedStatusString= " can feel touch again!";
								break;
							case 4:
								_fixedStatusString= " was healed of its burn!";
								break;
							case 5:
								_fixedStatusString= " thawed out!";
								break;
							case 6:
								_fixedStatusString= " is no longer poisoned!";
								break;
							default:
								_fixedStatusString= "";
								break;
							}
							g2.drawString(affectedPkmn + _fixedStatusString, 20, 335);
						}
				}
				else{
					g2.drawString("Use item on which Pokemon?", 20, 335);
				}
			}
			else{
			g2.drawString("Choose a Pokemon to switch", 20, 335);
			g2.drawString("into battle.", 20, 355);
			}
			
		}
		if(itemMenu && !t_text.isRunning() && !t_hp.isRunning() && !pkmnMenu){
			if(items.size() != 0){
				
				g2.setColor(Color.BLACK);
				
				String s = "Select item to use.";
				String sArrow = "";
				
				if(items.size() > 34){
					s += " (Page 1/2)";
					sArrow = "-->";
				}
				
				if(cursor.x == 10 || cursor.x == 200){
					g2.drawString(s, 20, 335);
					g2.drawString(sArrow, 335, 351);
				}
				if(cursor.x == 11 || cursor.x == 201){
					s = "Select item to use. (Page 2/2)";
					sArrow = "<--";
					g2.drawString(sArrow, 20, 351);
				}
			//	if(cursor.x == 12 || cursor.x == 202)
			//		g2.drawString("Select item to use. (Page 3)", 20, 335);
			//	if(cursor.x == 13 || cursor.x == 203)
			//		g2.drawString("Select item to use. (Page 4)", 20, 335);
				g2.drawString(s, 20, 335);
			}
			else{
				g2.setColor(Color.RED);
				g2.drawString("You have no items!", 20, 335);
			}
	//		SysOut.print("item?");
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
	}
	
	//==============================
	
	public void drawRootMenu(Graphics2D g2){
		Rectangle2D.Double rootBG = new Rectangle2D.Double();
		rootBG.x = 244;
		rootBG.y = 314;
		rootBG.width = 140;
		rootBG.height = 50;
		
		g2.setColor(Color.WHITE);
		g2.fill(rootBG);
		
		Rectangle2D.Double outline = new Rectangle2D.Double();
		outline.x = 249;
		outline.y = 319;
		outline.width = 130;
		outline.height = 40;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.draw(outline);
		
		g2.setFont(font);
		
		g2.drawString("FIGHT", 274, 334);
		g2.drawString("ITEM", 274, 354);
		g2.drawString("RUN", 342, 354);
		
		g2.setFont(new Font("Courier New", Font.BOLD, 12));
		g2.drawString("P M", 342, 332);
		g2.drawString("K N", 349, 336);
		
		g2.draw(cursor);
	}
	
	public void drawText(Graphics2D g2, String line1){
		this.drawText(g2, line1, "");
	}

	public void drawText(Graphics2D g2, String line1, String line2){
		
		Rectangle2D.Double textBG = new Rectangle2D.Double();
		textBG.x = 0;
		textBG.y = 314;
		textBG.width = this.getWidth();
		textBG.height = 50;
		
		g2.setColor(Color.WHITE);
		g2.fill(textBG);
		
		Rectangle2D.Double outline = new Rectangle2D.Double();
		outline.x = 5;
		outline.y = 319;
		outline.width = this.getWidth() - 10;
		outline.height = 40;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.draw(outline);
		
		g2.setFont(this.getFont());
		
		completionCheck = false;
		
		if(cfirst != null && csecond != null){
			completionCheck = (s1.equals(line1) &&  s2.equals(line2));
		}
		
		if(!t_text.isRunning() && !t_hp.isRunning() && !completionCheck){
			this.displayText(line1, line2);
		}
		
		g2.setFont(font);
		
		g2.drawString(s1, 20, 335);
		g2.drawString(s2, 20, 355);

	}
	
	public void displayText(String firstLine, String secondLine){
		cfirst = firstLine.toCharArray();
		csecond = null;

		s1 = ""; s2 = "";
		if(secondLine != null)
			csecond = secondLine.toCharArray();
		
		if(t_text != null && !t_text.isRunning() && !t_hp.isRunning()){

			t_text.start();
		}
	}
	
	public void displayAutoWrapText(String desc){
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
		
		cfirst = firstLine.toCharArray();
		csecond = null;

		s1 = ""; s2 = "";
		if(secondLine != null)
			csecond = secondLine.toCharArray();
		
		if(t_text != null && !t_text.isRunning() && !t_hp.isRunning()){

			t_text.start();
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
	
	private class HPChangeListener implements ActionListener{
		private Pokemon subject, subject2 = null;
		private int hp_change = 0, hp_change2 = 0, previousChange;
		private boolean ohko = false;
		public void setChange(Pokemon subject, int change){
			this.subject = subject;
			
			if(change < -1*this.subject.getCurrentHP()){
				this.hp_change = -1*(this.subject.getCurrentHP()+1);
				SysOut.print("damage > current HP");
			}
			else{
				this.hp_change = change;
				SysOut.print("HP? " + hp_change);
			}
			this.previousChange = 0;
				
			t_hp.setDelay((int) (1000.0/Math.abs(hp_change)));
			
			if(subject == ally){
				allyReceivedDamage = hp_change;
			}
			else{
				enemyReceivedDamage = hp_change;
			}
			
			SysOut.print("Subject Name: " + subject.getName() + ", HP Change: " + hp_change);
			t_hp.start();
		}
		
		public void setChange(Pokemon ally, int ally_HP_change, Pokemon enemy, int enemy_HP_change){
			this.subject = ally;
			this.subject2 = enemy;
			//this.hp_change = ally_HP_change;
			//this.hp_change2 = enemy_HP_change;
			this.previousChange = 0;
			
			if(ally_HP_change < -1*this.subject.getCurrentHP()){
				this.hp_change = -1*(this.subject.getCurrentHP()+1);
			}
			else{
				this.hp_change = ally_HP_change;
			}
			
			if(enemy_HP_change < -1*this.subject2.getCurrentHP()){
				this.hp_change2 = -1*(this.subject2.getCurrentHP()+1);
			}
			else{
				this.hp_change2 = enemy_HP_change;
			}

			t_hp.setDelay((int) (1000.0/Math.abs(hp_change2)));
			
			SysOut.print("Subject Name: " + subject.getName() + ", HP Change: " + hp_change);
			SysOut.print("Subject2 Name: " + subject2.getName() + ", HP Change: " + hp_change2);
			t_hp.start();
		}
		
		public void actionPerformed(ActionEvent e){
	//		SysOut.print("Action performed - HP");
			if(subject!= null){
	//			SysOut.print("Action performed - subject NOT null");
				if(hp_change >= 0){
	//				SysOut.print("Action performed - HP changed");
					if(hp_change > 0)
						
						//Sri: I added the below, but I think there's a problem again if you cause 
						//the enemy to faint AND you gain 100% HP back.
						if ((subject2 != null && hp_change2 == subject2.getCurrentHP()) || subject.getCurrentHP()==subject.getMaxHP()){
							t_hp.stop();							
						}
						if(subject.getCurrentHP() + 1 <= subject.getMaxHP()){
							subject.setCurrentHP(subject.getCurrentHP()+1);
							previousChange++;
						}
				}
				else{
		//			SysOut.print("Action performed - HP decremented");
					if(subject.getCurrentHP()-1 >= 0){
		//				SysOut.print("Action performed - HP current called:  " + subject.getCurrentHP());
						subject.setCurrentHP(subject.getCurrentHP()-1);
		//				SysOut.print("Action performed - HP current called:  " + subject.getCurrentHP());
						previousChange--;
					}
				}
				if(hp_change == previousChange || subject.getCurrentHP() == 0){
		//			SysOut.print("Action performed - time stop");
					t_hp.stop();
					if(subject.getCurrentHP() == 0){
						//create fainted nta
						
						/**
						 * TODO
						 * If last attack was a OHKO, that needs to be displayed before enemy faints.
						 * Also, if last attack had RECOIL but caused the enemy to faint, it still needs to do damage to user.
						 */
						turn = new NewTurn();
						nextTurn = null;
						enemyNextTurn = null;
						
		//				SysOut.print("Cleared list!!!!!!!!");
						if(battleType == NewBattleScreen.WILD){
		//					SysOut.print("endedndnendned");

							
							
							if(subject == enemy){
								SysOut.print("DEAD ENEMY");
								NewTurnAction ntafaint = new NewTurnAction.TA_WildPokemonFaints(_this, subject);
								turn.addTurnAction(ntafaint);
								
								_foughtIndex = 0;
								_foughtFraction= 1.0 / _foughtBelt.size();
								SysOut.print("Fought fraction");
								
								
								for(int f = 0; f < _foughtBelt.size(); f++){
									SysOut.print((_foughtBelt.get(f)-1) +" : fought!");
									if(_foughtIndex == ally.getBelt() && _gbs.getPlayer().getAllItems().get(Item.EXP_ALL).getRemaining() > 0){
										_foughtFraction *= 2.0;
									}
									if(player.getAllActive().get(_foughtBelt.get(f)-1).getCurrentHP() != 0){
										SysOut.print((_foughtBelt.get(f)-1) +" : fainted!");
										NewTurnAction ntagain = new NewTurnAction.TA_AllyGainsExperience(_this, player.getAllActive().get(_foughtBelt.get(_foughtIndex)-1), enemy, _foughtFraction);
										turn.addTurnAction(ntagain);
									}
									_foughtIndex++;
									
									if(f == _foughtBelt.size() - 1){
										_foughtIndex = 0;
										SysOut.print("Foguth reset");
									}
								}
								
								
								//if(player.getAllActive().get(_foughtBelt.get(_foughtIndex)-1).getCurrentHP()!=0){
								
								//NewTurnAction ntagain = new NewTurnAction.TA_AllyGainsExperience(_this, player.getAllActive().get(_foughtBelt.get(_foughtIndex)-1), enemy, _foughtFraction);
								//ADD TO LEVEL UP METHOD!
								//turn.addTurnAction(ntagain);
								//}
								//_foughtIndex++;
								
								if(payday > 0){
									NewTurnAction ntapayday = new NewTurnAction.TA_DisplayEffect(_this, "Picked up $" + payday + " in change!", "");
									turn.addTurnAction(ntapayday);
									player.incMoney(payday);
									payday = 0;
								}
								
								
								NewTurnAction ntaend = new NewTurnAction.TA_EndBattle(_this);
								turn.addTurnAction(ntaend);
								
							}
							if(subject == ally){
								NewTurnAction ntafaint = new  NewTurnAction.TA_AllyFaints(_this, subject);
								turn.addTurnAction(ntafaint);
								
								int numFainted = 0;
								
								for(int i = 0; i < player.getAllActive().size(); i++){
									if(player.getActivePokemon(i).getCurrentHP() == 0)
										numFainted++;
								}
								
								if(numFainted == player.getAllActive().size()){
									NewTurnAction ntablack1 = new NewTurnAction.TA_Blackout1(_this, player);
									turn.addTurnAction(ntablack1);
									
									NewTurnAction ntablack2 = new NewTurnAction.TA_Blackout2(_this, player);
									turn.addTurnAction(ntablack2);
									
									NewTurnAction ntaend = new NewTurnAction.TA_Blackout3(_this);
									turn.addTurnAction(ntaend);
								}
								else if(player.getAllActive().size() > 1){
									//
									setWildSwitch(true);
									NewTurnAction ntaCoward = new NewTurnAction.TA_DisplayEffect(_this, "Would you like to switch Pokemon?", "A=Yes          B=No");
									turn.addTurnAction(ntaCoward);
									
									NewTurnAction ntaswitch = new NewTurnAction.TA_SwitchNext(_this);
									turn.addTurnAction(ntaswitch);
									
								}
							}													
							
						
						}
						if(battleType == NewBattleScreen.TRAINER){
							
							if(subject == enemy){
								NewTurnAction ntafaint = new NewTurnAction.TA_WildPokemonFaints(_this, enemy);
								turn.addTurnAction(ntafaint);
							
								
								_foughtIndex = 0;
								_foughtFraction= 1.0 / _foughtBelt.size();
//								
//								for(int f = 0; f < _foughtBelt.size(); f++){
//									if(player.getAllActive().get(_foughtBelt.get(f)-1).getCurrentHP() != 0){
//										NewTurnAction ntagain = new NewTurnAction.TA_AllyGainsExperience(_this, player.getAllActive().get(_foughtBelt.get(_foughtIndex)-1), enemy, _foughtFraction);
//										turn.addTurnAction(ntagain);
//									}
//									_foughtIndex++;
//									
//									if(f == _foughtBelt.size() - 1){
//										_foughtIndex = 0;
//									}
//								}
								
								
								
								if(player.getAllActive().get(_foughtBelt.get(_foughtIndex)-1).getCurrentHP()!=0){
									if(_foughtIndex == ally.getBelt() && _gbs.getPlayer().getAllItems().get(Item.EXP_ALL).getRemaining() > 0){
										_foughtFraction *= 2.0;
									}
								NewTurnAction ntagain = new NewTurnAction.TA_AllyGainsExperience(_this, player.getAllActive().get(_foughtBelt.get(_foughtIndex)-1), enemy, _foughtFraction);
								//ADD TO LEVEL UP METHOD!
								SysOut.print("TIME TO GAIN EXPERIENCE");
								turn.addTurnAction(ntagain);
								}
								_foughtIndex++;
								
								int numFainted = 0;
								
								for(int i = 0; i < trainer.getBelt().size(); i++){
									if(trainer.getBelt().get(i).getCurrentHP() == 0){
										numFainted++;
									}
									else{
										
										if(player.getAllActive().size() != 1){
										
											NewTurnAction ntThisOpportunityComes = new NewTurnAction.TA_DisplayAutoWrapEffect(_this, trainer.getName() + " is about to send out " + trainer.getBelt().get(i).getName() + ".");
											turn.addTurnAction(ntThisOpportunityComes);
										
											NewTurnAction ntOnceInALifetime = new NewTurnAction.TA_ChanceToSwitch(_this);
											turn.addTurnAction(ntOnceInALifetime);
										}
										else{
											NewTurnAction nta2 = new NewTurnAction.TA_EnemySwitching(_this, trainer, trainer.getBelt().get(i));
											turn.addTurnAction(nta2);
										}
										
										break;
									}
								}
								if(numFainted == trainer.getBelt().size()){
								//you defeated...
									NewTurnAction ntadef = new NewTurnAction.TA_DefeatTrainer(_this, player, trainer);
									turn.addTurnAction(ntadef);
								
									NewTurnAction ntadialogue2 = new NewTurnAction.TA_TrainerDialogue2(_this, trainer);
									turn.addTurnAction(ntadialogue2);
								
									NewTurnAction ntamoney = new NewTurnAction.TA_GetMoney(_this, player, trainer);
									turn.addTurnAction(ntamoney);
									
									if(payday > 0){
										NewTurnAction ntapayday = new NewTurnAction.TA_DisplayEffect(_this, "Picked up $" + payday + " in change!", "");
										turn.addTurnAction(ntapayday);
										player.incMoney(payday);
										payday = 0;
									}
									
//									if(evolvers.size() != 0){
//										_this.setEvolutionScreenVisible(true);
//									}
								
									NewTurnAction ntaend = new NewTurnAction.TA_EndBattle(_this);
									turn.addTurnAction(ntaend);
								}
							}
							
							if(subject == ally){
				//				SysOut.print("allydead!");
								
								
								NewTurnAction ntafaint = new  NewTurnAction.TA_AllyFaints(_this, subject);
								turn.addTurnAction(ntafaint);
								
								int numFainted = 0;
								
								for(int i = 0; i < player.getAllActive().size(); i++){
									if(player.getActivePokemon(i).getCurrentHP() == 0)
										numFainted++;
								}
								
								if(numFainted == player.getAllActive().size()){
									NewTurnAction ntablack1 = new NewTurnAction.TA_Blackout1(_this, player);
									turn.addTurnAction(ntablack1);
									
									NewTurnAction ntablack2 = new NewTurnAction.TA_Blackout2(_this, player);
									turn.addTurnAction(ntablack2);
									
									NewTurnAction ntaend = new NewTurnAction.TA_Blackout3(_this);
									turn.addTurnAction(ntaend);
								}
								else{
									
									NewTurnAction ntaswitch = new NewTurnAction.TA_SwitchNext(_this);
									turn.addTurnAction(ntaswitch);
								}
							}
						}
					//	turn.nextStage();
					}
				}
			}
			
			if(!t_hp.isRunning() && subject2 != null && hp_change2 != 0){
				this.setChange(subject2, hp_change2);
				subject2 = null;
				hp_change2 = 0;
				t_hp.start();
			}
			
			_this.repaint();
		}
	}
	
	private class XPChangeListener implements ActionListener{
		private Pokemon subject;
		private int xp_change, previousChange, startLevel;
		public void setChange(Pokemon subject, int change){
			this.subject = subject;
			this.startLevel = subject.getLevel();
			this.xp_change = change;
			previousChange = 0;
			
//			if(NewBattleScreen.getToNextLevel(subject) != 0)
//				t_xp.setDelay((int) (change/NewBattleScreen.getToNextLevel(subject)));
			
			
			t_xp.start();
		}
		
		public void actionPerformed(ActionEvent e){
			subject.setExp(subject.getExp()+1);
			previousChange++;
			if(previousChange == xp_change){
				t_xp.stop();
				
				
				
				if(_foughtBelt.size()-1>=_foughtIndex){
					if(player.getAllActive().get(_foughtBelt.get(_foughtIndex)-1).getCurrentHP()!=0){
						
						NewTurnAction ntagain = new NewTurnAction.TA_AllyGainsExperience(_this, player.getAllActive().get(_foughtBelt.get(_foughtIndex)-1), enemy, _foughtFraction);
						turn.getList().add(turn.getCurrentStage()+(subject.getLevel()-startLevel)+1,ntagain);
					}
				}
				_foughtIndex++;
				
			}
			
			if(subject.getExp() >= NewBattleScreen.getToNextLevel(subject)){
				/*
				 * ALL OF THE LEVELLING UP CODE,
				 * including learning new attacks and setting
				 * it to evolve post battle.
				 */
			
				
				NewTurnAction nta = new NewTurnAction.TA_LevelUp(_this, subject);
				turn.getList().add(turn.getCurrentStage()+(subject.getLevel()-startLevel), nta);
				
				
			}
		/*	//!t_xp.isRunning() && 
			if(subject.getLevel() > startLevel){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(_this, ally.getName() + " grew to level " + ally.getLevel() + "!");
				turn.getList().add(turn.getCurrentStage()+1, nta);
				
				
			}*/
			_this.repaint();
		}
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
			
	public void resetBattle(){
		
		_foughtBelt.clear();
		_foughtIndex=0;
		
		forceFight=false;
		
		ally.revertType();
		enemy.revertType();
		
		setAllyRaging(false);
		setEnemyRaging(false);
		
		setAllyLeechSeed(false);
		setEnemyLeechSeed(false);
		
		setAllyConfusion(false);
		setEnemyConfusion(false);
		
		setAllyDug(false);
		setEnemyDug(false);
		
		setAllyFlying(false);
		setEnemyFlying(false);
		
		setAllyFlinch(false);
		setEnemyFlinch(false);
		
		setAllyMinimized(false);
		setEnemyMinimized(false);
		
		allyMultiTurn = 0;
		enemyMultiTurn = 0;
		
		setAllyMisty(false);
		setEnemyMisty(false);
		
		allyDisabled = -1;
		enemyDisabled = -1;
		
		allyReceivedDamage = -1;
		enemyReceivedDamage = -1;
		
		clearAllyTransform();
		clearEnemyTransform();
		
		endAllyMimicry();
		endEnemyMimicry();
		
		enemy.heal();
		
		for(int i = 0; i < player.getAllActive().size(); i++){
			if(player.getActivePokemon(i).getStatus() == 6)
				player.getActivePokemon(i).setStatus(2);
		}
		
		ally = null;
		enemy = null;

		this.resetRootMenuCursor();
		turn = null;
		nextTurn = null;
		enemyNextTurn = null;
		escape = "";
		playerX = 10;
		rivalX = 230;
		allyVis = false;
		enemyVis = false;
		_deltaPP=0;
		_allyTurnComplete=false;
		_deltaHP=0;
		_fixedStatus=0;
		cantRun=false;
		escape="";
		runTurns=0;
		completionCheck=false;
		
		allyFaintY=0;
		enemyFaintY=0;
		GBTick=0;
		GBTimer.stop();
			
		this.resetAllMenus();
		this.resetAllyStages();
		this.resetEnemyStages();
		
		//reset sprites.
		this.getGBS().setOld(false);
	}
}
