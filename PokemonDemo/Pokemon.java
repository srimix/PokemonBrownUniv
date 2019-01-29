package PokemonDemo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 * This is the superclass for the Pokemon objects of 
 * the game. It contains a lot of functionality itself
 * and as a result none of the methods except the constructor
 * are overridden (the constructor is overridden to teach
 * Pokemon certain Attacks from the beginning).
 * 
 * @author mreiss
 */
public class Pokemon	 {

//	public static final int LIGHTNING = 0, NORMAL = 1, FLYING = 2, GRASS = 3, ROCK=4, GROUND=5, WATER=6, DRAGON=7;
	private int _maxHP, _currentHP, _level, _exp, _status, _belt, _dexNum, _rate, _atkStat, _defStat, _spAtkStat, _spDefStat, _speed;
	private int _atkIV, _defIV, _spAtkIV, _spDefIV, _spdIV, _HPIV;
	protected int _baseAtk, _baseDef, _baseSpAtk, _baseSpDef, _baseSpd, _baseHP, _baseExp;
	protected double _expMult = 1.0;
	private double _accuracy, _evasion;
	private Types _type1, _type2, originalType1;
	private Vector<Attack> _attacks; // - eventually, should be array
	public static Attack _newAttack;
	public static boolean [] _evolve;
	private boolean focus, dire, guarded;
	private String _name, _iconText;
	private ImageIcon _icon, _animation;
	private BufferedImage _frontImage, _backImage, _deadIcon;
	@SuppressWarnings("unused")
	private Vector<Integer> locations;
	protected boolean _hasEvolution = true;
	protected int _evolLvl = 101;
	protected boolean _isEvolving;
	@SuppressWarnings("unused")
	private Attack fly = new Attack.Gust();
	
	//Used so that it can be changed for Eevee.
	public int evStage = 1;
	
	//Used for all learned attacks. FIXME
	protected Attack[] attackTree = new Attack[101];
	
	/**
	 * Constructor for the Pokemon superclass. Most of this
	 * class' functionality simply occurs by initializing
	 * certain variables in the constructor, such as Name, Type, 
	 * HP, image, etc.
	 * 
	 * @param name
	 * @param type
	 * @param maxHP
	 * @param image
	 */
	public Pokemon(int dexNum, String name, Types type1, Types type2, int maxHP, int level, int exp, int status, int belt, int rate, int atkStat, int defStat, int spAtkStat, int spDefStat, int speed, int atkIV, int defIV, int spAtkIV, int spDefIV, int spdIV, int HPIV, double accuracy, double evasion){
		
		//this._attacks.add(new Attack.Tackle());
		
		_name = name;
		_type1 = type1;
		_type2 = type2;
		originalType1 = type1;
		
//		Thread t = new Thread(){
//			public void run(){
//				//setImages(dexNum);
//			}
//		};
//		t.start();
		this.setImages(dexNum);
		
		_iconText = "PokemonFiles/Icon/"+dexNum+".gif";
		
		//_icon = new ImageIcon(icon);


		_currentHP = _maxHP = maxHP;
		
		_attacks = new Vector<Attack>();
		

		
	//	_frontImage = frontimage;
	//	_backImage = backimage;
		_dexNum = dexNum;
		
		_level = level;
		_status = status;
		_exp= exp;
		
		_belt= belt;
		_rate = rate;
		_atkIV=atkIV;
		_defIV=defIV;
		_spAtkIV=spAtkIV;
		_spDefIV=spDefIV;
		_spdIV=spdIV;
		_HPIV=HPIV;

		_baseAtk=1;
		_baseDef=1;
		_baseSpAtk=1;
		_baseSpDef=1;
		_baseSpd=1;
		_baseHP=1; 
		
		_accuracy = accuracy;
		_evasion = evasion;
		
		_evolve = new boolean[6];
		
		focus = false;
		dire = false;
		
		this._evolLvl=this.getEvolLvl(this._dexNum);
		
	}
	
	public boolean isFocused(){
		if(focus){
			focus = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setFocusedTrue(){
		focus = true;
	}
	
	public boolean isDireHit(){
		if(dire){
			dire = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setDireHitTrue(){
		dire = true;
	}
	
	public boolean isGuardSpec(){
		if(guarded){
			guarded = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setGuardSpecTrue(){
		guarded = true;
	}

	//This is all for level up stats
	public int getBaseAtk(){
		return _baseAtk;
	}
	public int getBaseDef(){
		return _baseDef;
	}
	public int getBaseSpAtk(){
		return _baseSpAtk;
	}
	public int getBaseSpDef(){
		return _baseSpDef;
	}
	public int getBaseSpd(){
		return _baseSpd;
	}
	public int getBaseHP(){
		return _baseHP;
	}
	public int getAtkIV(){
		return _atkIV;
	}
	public int getDefIV(){
		return _defIV;
	}
	public int getSpAtkIV(){
		return _spAtkIV;
	}
	public int getSpDefIV(){
		return _spDefIV;
	}
	public int getSpdIV(){
		return _spdIV;
	}
	public int getHPIV(){
		return _HPIV;
	}
	public void setAtkIV(int IV){
		_atkIV=IV;
	}
	public void setDefIV(int IV){
		_defIV=IV;
	}
	public void setSpAtkIV(int IV){
		_spAtkIV=IV;
	}
	public void setSpDefIV(int IV){
		_spDefIV=IV;
	}
	public void setSpdIV(int IV){
		_spdIV=IV;
	}
	public void setHPIV(int IV){
		_HPIV=IV;
	}
	
	public static int randIntBetween(int min, int max){
		int r=min + (int)(Math.random() * ((max - min) + 1));
		return r;
	}
	
	public boolean hasAttackNamed(String name){
		for (int j=0; j<this.getAttacks().size();j++){
			if(this.getAttacks().get(j).getName()==name){
				return true;
			}
		}
		return false;
	}
	
	public int whichAttackIsNamed(String name){
		for (int j=0; j<this.getAttacks().size();j++){
			if(this.getAttacks().get(j).getName()==name){
				return j;
			}
		}
		return -1;
	}
	
	
	public static void generateIV(Pokemon p){
		//Generate values between 0 and 31.
		Random r = new Random();
		p.setAtkIV(r.nextInt(31));
		p.setDefIV(r.nextInt(31));
		p.setSpAtkIV(r.nextInt(31));
		p.setSpDefIV(r.nextInt(31));
		p.setSpdIV(r.nextInt(31));
		p.setHPIV(r.nextInt(31));
		
	}
	
	public static Pokemon generateNewStats(Pokemon p){
		//Hit Points:

		int newHP=(int)( (p.getHPIV() + 2 * p.getBaseHP() ) * (p.getLevel()/100.0) ) + 10 + p.getLevel();
		double ratio=(double)newHP/p.getMaxHP();
		p.setMaxHP(newHP);
		p.setNoAnimateCurrentHP((int)(p.getCurrentHP()*ratio)+1);
		if(p.getCurrentHP()>p.getMaxHP()) p.setNoAnimateCurrentHP(p.getMaxHP());

	//Attack, Defense, Speed, Sp. Attack, Sp. Defense:
	    int newAtk=(int)(((p.getAtkIV() + 2 * p.getBaseAtk() ) * (p.getLevel()/100.0)) + 5) ;
	    int newDef=(int)(((p.getDefIV() + 2 * p.getBaseDef() ) * (p.getLevel()/100.0) ) + 5) ;
	    int newSpAtk=(int)(((p.getSpAtkIV() + 2 * p.getBaseSpAtk() ) * (p.getLevel()/100.0) ) + 5) ;
	    int newSpDef=(int)(((p.getSpDefIV() + 2 * p.getBaseSpDef() ) * (p.getLevel()/100.0) ) + 5) ;
	    int newSpd=(int)(((p.getSpdIV() + 2 * p.getBaseSpd() ) * (p.getLevel()/100.0) ) + 5) ;
	    
	    p.setAtkStat(newAtk);
	    p.setDefStat(newDef);
	    p.setSpAtkStat(newSpAtk);
	    p.setSpDefStat(newSpDef);
	    p.setSpeed(newSpd);

	    return p;
	}
	
	public static void generateTrainerPkmn(Pokemon p, int level, int rank){
		
		p.setLevel(level);
		
		//Generate values between 0 and 31.
		if (rank==0){
			//Low. Easy to defeat. 0-8
			p.setAtkIV(randIntBetween(0,8));
			p.setDefIV(randIntBetween(0,8));
			p.setSpAtkIV(randIntBetween(0,8));
			p.setSpDefIV(randIntBetween(0,8));
			p.setSpdIV(randIntBetween(0,8));
			p.setHPIV(randIntBetween(0,8));
		}
		else if (rank==1){
			//Mid range. This should be most. 9-23
			p.setAtkIV(randIntBetween(9,23));
			p.setDefIV(randIntBetween(9,23));
			p.setSpAtkIV(randIntBetween(9,23));
			p.setSpDefIV(randIntBetween(9,23));
			p.setSpdIV(randIntBetween(9,23));
			p.setHPIV(randIntBetween(9,23));
		}
		else if (rank==2){
			//Hard to kill. 24-31
			p.setAtkIV(randIntBetween(24,31));
			p.setDefIV(randIntBetween(24,31));
			p.setSpAtkIV(randIntBetween(24,31));
			p.setSpDefIV(randIntBetween(24,31));
			p.setSpdIV(randIntBetween(24,31));
			p.setHPIV(randIntBetween(24,31));
		}
		else{
			//Full range.
		Random r = new Random();
			p.setAtkIV(r.nextInt(31));
			p.setDefIV(r.nextInt(31));
			p.setSpAtkIV(r.nextInt(31));
			p.setSpDefIV(r.nextInt(31));
			p.setSpdIV(r.nextInt(31));
			p.setHPIV(r.nextInt(31));
		}
		
		

		
	}
	
	public boolean hasEvolution(){
		return _hasEvolution;
	}
	
	public Attack levelUp (){

		//Increase Level and Stats
		if(_level < 100){
			this.incLevel(1);
			generateNewStats(this);
		}
			
		if((this.getLevel() < 100) && attackTree[this.getLevel()] != null){
			return attackTree[this.getLevel()];
		}
		
		if(this.getLevel() >= this._evolLvl){
			this._isEvolving = true;
		}
		
		return null;
	}
	
	public static String[] getDescriptionByDexNum(int dexNum){
		String[] desc = {"","","",""};
		//This Pokemon is a - RUBRIK
		switch(dexNum){
		case 1:  
			desc[0]="A strange seed was"; 
			desc[1]="planted on its back";
			desc[2]="at birth. Its full"; 
			desc[3]="of nutrients.";
			break;
		case 2:
			desc[0]="The bulb on its "; 
			desc[1]="back grows by";
			desc[2]="absorbing "; 
			desc[3]="sunlight.";
			break;
		case 3: 
			desc[0]="The plant blooms"; 
			desc[1]="when it absorbs";
			desc[2]="enough solar"; 
			desc[3]="energy.";
			break;
		case 4: 
			desc[0]="Obviously prefers"; 
			desc[1]="hot places. Steam";
			desc[2]="rises from its tail"; 
			desc[3]="when it rains.";
			break;
		case 5: 
			desc[0]="When it swings its"; 
			desc[1]="burning tail, it";
			desc[2]="raises the temp to"; 
			desc[3]="over 9000\u00B0 F.";
			break;
		case 6: 
			desc[0]="It spits fire that"; 
			desc[1]="is hot enough to";
			desc[2]="melt boulders. Can"; 
			desc[3]="cause forest fires.";
			break;
		case 7: 
			desc[0]="The tiny turtle"; 
			desc[1]="Pokemon. It is";
			desc[2]="notoriously shy."; 
			desc[3]="";
			break;
		case 8: 
			desc[0]="To swim faster,"; 
			desc[1]="it will move its";
			desc[2]="ears to maintain"; 
			desc[3]="its balance.";
			break;
		case 9: 
			desc[0]="A brutal Pokemon"; 
			desc[1]="with pressurized";
			desc[2]="water jets on its"; 
			desc[3]="shell.";
			break;
		case 10: 
			desc[0]="Its short feet are"; 
			desc[1]="tipped with suction";
			desc[2]="pads to help it"; 
			desc[3]="climb trees.";
			break;
		case 11: 
			desc[0]="Hardens its shell"; 
			desc[1]="to protect itself";
			desc[2]="The insides are"; 
			desc[3]="very tender.";
			break;
		case 12: 
			desc[0]="In battle, it flaps"; 
			desc[1]="its wings at high";
			desc[2]="speed, releasing a"; 
			desc[3]="toxic dust.";
			break;
		case 13: 
			desc[0]="It has a sharp,"; 
			desc[1]="venomous stinger";
			desc[2]="on its head. Dont"; 
			desc[3]="step on it!";
			break;
		case 14: 
			desc[0]="Almost incapable"; 
			desc[1]="of moving. All it";
			desc[2]="can do is harden"; 
			desc[3]="its shell.";
			break;
		case 15: 
			desc[0]="Flies at high"; 
			desc[1]="speeds, attacking";
			desc[2]="with its three"; 
			desc[3]="venomous stingers.";
			break;
		case 16: 
			desc[0]="Very docile. If"; 
			desc[1]="attacked, it will";
			desc[2]="often kick up sand"; 
			desc[3]="to protect itself.";
			break;
		case 17: 
			desc[0]="Very protective"; 
			desc[1]="of its territory.";
			desc[2]="Will fiercely peck"; 
			desc[3]="at any intruder.";
			break;
		case 18: 
			desc[0]="This Pokemon flies"; 
			desc[1]="at Mach 2 speed,";
			desc[2]="seeking its prey."; 
			desc[3]="Beware the talons!";
			break;
		case 19: 
			desc[0]="Bites anything"; 
			desc[1]="when it attacks.";
			desc[2]="Small and quick,"; 
			desc[3]="it is very common.";
			break;
		case 20: 
			desc[0]="It uses its"; 
			desc[1]="whiskers to keep";
			desc[2]="its balance. Has"; 
			desc[3]="large fangs.";
			break;
		case 21: 
			desc[0]="Inept at flying"; 
			desc[1]="high. However,";
			desc[2]="it can fly low to"; 
			desc[3]="the ground quickly.";
			break;
		case 22: 
			desc[0]="With its huge and"; 
			desc[1]="magnificent wings,";
			desc[2]="it can stay aloft"; 
			desc[3]="for days at a time.";
			break;
		case 23: 
			desc[0]="Moves silently"; 
			desc[1]="and stealthily.";
			desc[2]="Eats the eggs of"; 
			desc[3]="bird Pokemon whole.";
			break;
		case 24: 
			desc[0]="If surprised, it"; 
			desc[1]="rears up high,";
			desc[2]="exposing the scary"; 
			desc[3]="markings on its body.";
			break;
		case 25: 
			desc[0]="Stores electricity"; 
			desc[1]="in the pouches in";
			desc[2]="its cheeks."; 
			desc[3]="";
			break;
		case 26: 
			desc[0]="Its long tail acts"; 
			desc[1]="as a ground to";
			desc[2]="protect itself from"; 
			desc[3]="its own high voltage.";
			break;
		case 27: 
			desc[0]="Burrows deep"; 
			desc[1]="underground. Only";
			desc[2]="emerges to hunt for"; 
			desc[3]="food.";
			break;
		case 28: 
			desc[0]="Curls up into a"; 
			desc[1]="spikey ball when";
			desc[2]="threatened."; 
			desc[3]="";
			break;
		case 29: 
			desc[0]="Although small, its"; 
			desc[1]="venomous barbs";
			desc[2]="make this a dangerous"; 
			desc[3]="Pokemon.";
			break;
		case 30: 
			desc[0]="The females horn"; 
			desc[1]="developers slowly.";
			desc[2]="Prefers physical"; 
			desc[3]="attacks, like Bite.";
			break;
		case 31: 
			desc[0]="Its hard scales"; 
			desc[1]="provide protection.";
			desc[2]="Uses its heft bulk"; 
			desc[3]="to slam opponents.";
			break;
		case 32: 
			desc[0]="Stiffens its ears"; 
			desc[1]="to sense danger.";
			desc[2]="The bigger the horns,"; 
			desc[3]="the more poisonous.";
			break;
		case 33: 
			desc[0]="An aggressuve"; 
			desc[1]="Pokemon that is";
			desc[2]="quick to attack."; 
			desc[3]="";
			break;
		case 34: 
			desc[0]="Uses its powerful"; 
			desc[1]="tail to smash";
			desc[2]="opponents in battle."; 
			desc[3]="";
			break;
		case 35: 
			desc[0]="Its magical and"; 
			desc[1]="cute appearance";
			desc[2]="has many admirers."; 
			desc[3]="Very rare.";
			break;
		case 36: 
			desc[0]="A timid fairy"; 
			desc[1]="Pokemon that is";
			desc[2]="rarely seen. Runs"; 
			desc[3]="and hides if found.";
			break;
		case 37: 
			desc[0]="At birth, it has"; 
			desc[1]="only one tail. The";
			desc[2]="tail splits from"; 
			desc[3]="its tip as it grows.";
			break;
		case 38: 
			desc[0]="Smart and vengeful."; 
			desc[1]="Grabbing one of its";
			desc[2]="tails causes a"; 
			desc[3]="1000-year curse.";
			break;
		case 39: 
			desc[0]="It its huge eyes"; 
			desc[1]="light up, it sings";
			desc[2]="a soothing melody."; 
			desc[3]="";
			break;
		case 40: 
			desc[0]="The body is soft"; 
			desc[1]="and rubbery. When";
			desc[2]="angry, inhales and"; 
			desc[3]="puffs itself up.";
			break;
		case 41: 
			desc[0]="Forms colonies in"; 
			desc[1]="perpetually dark";
			desc[2]="places. Uses echo-"; 
			desc[3]="location to hunt.";
			break;
		case 42: 
			desc[0]="Once it strikes,"; 
			desc[1]="it won't stop";
			desc[2]="draining energy"; 
			desc[3]="from its victim.";
			break;
		case 43: 
			desc[0]="During the day,"; 
			desc[1]="it keeps its face";
			desc[2]="buried in the soil."; 
			desc[3]="Wanders at night.";
			break;
		case 44: 
			desc[0]="The fluid that oozes"; 
			desc[1]="from its mouth isn't";
			desc[2]="drool, it's nectar"; 
			desc[3]="to attract prey.";
			break;
		case 45: 
			desc[0]="The larger its"; 
			desc[1]="petals, the more";
			desc[2]="toxic the pollen"; 
			desc[3]="it contains.";
			break;
		case 46: 
			desc[0]="Burrows to feed"; 
			desc[1]="off tree roots.";
			desc[2]="The mushrooms on its"; 
			desc[3]="back are symbiotic,";
			break;
		case 47: 
			desc[0]="A host-parasite"; 
			desc[1]="pair in which the";
			desc[2]="mushroom has taken"; 
			desc[3]="over the bug.";
			break;
		case 48: 
			desc[0]="Lives in the shadows"; 
			desc[1]="of tall trees where";
			desc[2]="it eats bugs. Large"; 
			desc[3]="eyes act as radars.";
			break;
		case 49: 
			desc[0]="The dustlike scales"; 
			desc[1]="on the wings are color";
			desc[2]="coded to indicate"; 
			desc[3]="the type of venom.";
			break;
		case 50: 
			desc[0]="Lives about 1 yd"; 
			desc[1]="underground, where";
			desc[2]="it feeds on plant"; 
			desc[3]="roots.";
			break;
		case 51: 
			desc[0]="A team of Diglett"; 
			desc[1]="triplets. Triggers";
			desc[2]="huge earthquakes by"; 
			desc[3]="burrowing 60 mi deep.";
			break;
		case 52: 
			desc[0]="Adores shiny things."; 
			desc[1]="Wanders the streets";
			desc[2]="at night looking"; 
			desc[3]="for loose change.";
			break;
		case 53: 
			desc[0]="Although its fur"; 
			desc[1]="has many admirers,";
			desc[2]="it is a tough pet"; 
			desc[3]="because it is fickle.";
			break;
		case 54: 
			desc[0]="Always tormented"; 
			desc[1]="by headaches. It";
			desc[2]="uses psychic powers,"; 
			desc[3]="maybe by accident.";
			break;
		case 55: 
			desc[0]="Often seen"; 
			desc[1]="swimming elegantly";
			desc[2]="by lakeshores."; 
			desc[3]="";
			break;
		case 56: 
			desc[0]="Extremely quick"; 
			desc[1]="to anger. May be";
			desc[2]="docile one moment"; 
			desc[3]="and violent the next.";
			break;
		case 57: 
			desc[0]="Always furious"; 
			desc[1]="and tenacious to";
			desc[2]="boot. Never gives"; 
			desc[3]="up chasing its prey.";
			break;
		case 58: 
			desc[0]="Very protective"; 
			desc[1]="of its territory.";
			desc[2]="Will bark and bite"; 
			desc[3]="to scare intruders.";
			break;
		case 59: 
			desc[0]="A legendary Pokemon,"; 
			desc[1]="it has been admired";
			desc[2]="since ancient times"; 
			desc[3]="for its speed and grace.";
			break;
		case 60: 
			desc[0]="Its newly grown legs"; 
			desc[1]="prevent it from";
			desc[2]="walking well."; 
			desc[3]="Prefers swimming.";
			break;
		case 61: 
			desc[0]="Can live in or"; 
			desc[1]="out of water.";
			desc[2]="Constantly sweats"; 
			desc[3]="to stay slimy.";
			break;
		case 62: 
			desc[0]="Swims powerfully"; 
			desc[1]="using its muscles.";
			desc[2]="Faster than any"; 
			desc[3]="human swimmer."; //Challenge issued, Michael Phelps
			break;
		case 63: 
			desc[0]="Using its ability"; 
			desc[1]="to read minds, it";
			desc[2]="will teleport at"; 
			desc[3]="any sign of danger.";
			break;
		case 64: 
			desc[0]="It emits special"; 
			desc[1]="alpha waves that";
			desc[2]="induce headaches"; 
			desc[3]="just by being close.";
			break;
		case 65: 
			desc[0]="Its brain can"; 
			desc[1]="outperform a";
			desc[2]="supercomputer."; 
			desc[3]="Its IQ is 5,000.";
			break;
		case 66: 
			desc[0]="Loves to lift"; 
			desc[1]="weights and build";
			desc[2]="muscle. Knows"; 
			desc[3]="all martial arts."; //MACHOP
			break;
		case 67: 
			desc[0]="Its muscular body"; 
			desc[1]="is so powerful,";
			desc[2]="it must wear a"; 
			desc[3]="power-save belt.";
			break;
		case 68: 
			desc[0]="It throws powerful"; 
			desc[1]="punches that can";
			desc[2]="send victims clear"; 
			desc[3]="over the horizon.";
			break;
		case 69: 
			desc[0]="A carnivorous"; 
			desc[1]="Pokemon that traps";
			desc[2]="and eats bugs with"; 
			desc[3]="its long vines.";
			break;
		case 70: 
			desc[0]="It spits out a"; 
			desc[1]="poisonous powder";
			desc[2]="to immobilize its"; 
			desc[3]="enemies.";
			break;
		case 71: 
			desc[0]="Swallows its prey"; 
			desc[1]="whole, having";
			desc[2]="lured it with"; 
			desc[3]="the scent of honey.";
			break;
		case 72: 
			desc[0]="Drifts in shallow"; 
			desc[1]="seas. Anglers who";
			desc[2]="hook them by"; 
			desc[3]="mistake get stung.";
			break;
		case 73: 
			desc[0]="Its tentacles stretch"; 
			desc[1]="freely to wrap around";
			desc[2]="its prey, weakening"; 
			desc[3]="it with poison.";
			break;
		case 74: 
			desc[0]="Found in mountains,"; 
			desc[1]="it is often";
			desc[2]="mistaken for rocks"; 
			desc[3]="and stepped on.";
			break;
		case 75: 
			desc[0]="Rolls over all"; 
			desc[1]="obstacles without";
			desc[2]="slowing down or"; 
			desc[3]="changing direction.";
			break;
		case 76: 
			desc[0]="Its boulder-like"; 
			desc[1]="body is extremely";
			desc[2]="hard. Can withstand"; 
			desc[3]="dynamite blasts.";
			break;
		case 77: 
			desc[0]="Its hooves are 10"; 
			desc[1]="times harder than";
			desc[2]="diamond. Can"; 
			desc[3]="trample anything.";
			break;
		case 78: 
			desc[0]="Very competitve."; 
			desc[1]="Will chase anything";
			desc[2]="that moves in the"; 
			desc[3]="hopes of racing.";
			break;
		case 79: 
			desc[0]="Incredibly slow"; 
			desc[1]="and dopey. It";
			desc[2]="takes 5 seconds"; 
			desc[3]="to feel any pain.";
			break;
		case 80: 
			desc[0]="Lives lazily by"; 
			desc[1]="the sea. If the";
			desc[2]="Shellder detaches,"; 
			desc[3]="reverts to Slowpoke.";
			break;
		case 81: 
			desc[0]="Uses antigravity"; 
			desc[1]="to float by";
			desc[2]="electromagnetic"; 
			desc[3]="waves it emits.";
			break;
		case 82: 
			desc[0]="Generates strange"; 
			desc[1]="radio signals.";
			desc[2]="Formed by three"; 
			desc[3]="linked Magenemites.";
			break;
		case 83: 
			desc[0]="It uses the sprig"; 
			desc[1]="of green onion it";
			desc[2]="carries as a weapon,"; 
			desc[3]="much like a sword.";
			break;
		case 84: 
			desc[0]="A bird that makes"; 
			desc[1]="up for its poor";
			desc[2]="flying ability"; 
			desc[3]="with awesome speed.";
			break;
		case 85: 
			desc[0]="Uses its three"; 
			desc[1]="brains to form";
			desc[2]="and execute"; 
			desc[3]="complex plans.";
			break;
		case 86: 
			desc[0]="The protruding"; 
			desc[1]="horn on its head";
			desc[2]="is very hard. Used"; 
			desc[3]="to smash ice.";
			break;
		case 87: 
			desc[0]="Resistant to all"; 
			desc[1]="cold, can swim at";
			desc[2]="8 knots even in"; 
			desc[3]="below-freezing.";
			break;
		case 88: 
			desc[0]="Appears in filth."; 
			desc[1]="Thrives by";
			desc[2]="sucking up"; 
			desc[3]="polluted sludge.";
			break;
		case 89: 
			desc[0]="Covered in thick,"; 
			desc[1]="vile sludge. So";
			desc[2]="toxic, it oozes"; 
			desc[3]="a poisonous goo.";
			break;
		case 90: 
			desc[0]="Its hard shell"; 
			desc[1]="repels any attack.";
			desc[2]="Its defenses rise"; 
			desc[3]="if it withdraws.";
			break;
		case 91: 
			desc[0]="When attacked, it"; 
			desc[1]="launches its horns";
			desc[2]="in quick volleys."; 
			desc[3]="";
			break;
		case 92: 
			desc[0]="Almost invisible,"; 
			desc[1]="this gaseous ";
			desc[2]="Pokemon can induce"; 
			desc[3]="sleep without warning.";
			break;
		case 93: 
			desc[0]="It is said to be"; 
			desc[1]="from another";
			desc[2]="dimension. It can"; 
			desc[3]="move through walls.";
			break;
		case 94: 
			desc[0]="This Pokemon likes"; 
			desc[1]="to mimic peoples";
			desc[2]="shadows and laugh"; 
			desc[3]="at their fright.";
			break;
		case 95: 
			desc[0]="As it grows, the"; 
			desc[1]="stone portions of";
			desc[2]="its body harden"; 
			desc[3]="close to diamond.";
			break;
		case 96: 
			desc[0]="Puts enemies"; 
			desc[1]="to sleep, then";
			desc[2]="eats their dreams."; 
			desc[3]="";
			break;
		case 97: 
			desc[0]="Avoid eye contact"; 
			desc[1]="or it will use";
			desc[2]="its psychic power"; 
			desc[3]="to hypnotize you.";
			break;
		case 98: 
			desc[0]="Its pincers are"; 
			desc[1]="not only powerful";
			desc[2]="weapons; they are"; 
			desc[3]="used for balance.";
			break;
		case 99:
			desc[0]="The larger claw"; 
			desc[1]="has 10,000 HP";
			desc[2]="crushing force."; 
			desc[3]="";
			break;
		case 100: 
			desc[0]="Easily mistaken"; 
			desc[1]="for a Poke Ball,";
			desc[2]="it will zap you"; 
			desc[3]="if you touch it.";
			break;
		case 101: 
			desc[0]="Stores electricity"; 
			desc[1]="at high pressure.";
			desc[2]="Known to explode"; 
			desc[3]="randomly.";
			break;
		case 102: 
			desc[0]="Stays in groups,"; 
			desc[1]="if provoked will";
			desc[2]="gather and"; 
			desc[3]="attack in swarms.";
			break;
		case 103: 
			desc[0]="Very poorly"; 
			desc[1]="coordinated, as";
			desc[2]="each of its heads"; 
			desc[3]="think independently.";
			break;
		case 104: 
			desc[0]="Wears the skull"; 
			desc[1]="of its deceased";
			desc[2]="mother. Noone has"; 
			desc[3]="seen its real face.";
			break;
		case 105: 
			desc[0]="Skillfully uses"; 
			desc[1]="its Bone Club as";
			desc[2]="a boomerang to"; 
			desc[3]="attack enemies.";
			break;
		case 106: 
			desc[0]="The kicking master."; 
			desc[1]="Can extend and ";
			desc[2]="retract its legs"; 
			desc[3]="at will.";
			break;
		case 107: 
			desc[0]="Can fire punches"; 
			desc[1]="lightning fast,";
			desc[2]="and can punch"; 
			desc[3]="through concrete.";
			break;
		case 108: 
			desc[0]="Its tongue can be"; 
			desc[1]="extended like a";
			desc[2]="chameleons tail."; 
			desc[3]="";
			break;
		case 109:  
			desc[0]="Because it stores"; 
			desc[1]="volatile gases in";
			desc[2]="itself,  it tends"; 
			desc[3]="to explode.";
			break;
		case 110: 
			desc[0]="Two Koffings fuse"; 
			desc[1]="together to form";
			desc[2]="a new kind of "; 
			desc[3]="toxic Pokemon.";
			break;
		case 111: 
			desc[0]="Its massive bones"; 
			desc[1]="are 100 times";
			desc[2]="harder than"; 
			desc[3]="human bones.";
			break;
		case 112: 
			desc[0]="Protected by"; 
			desc[1]="armor-like skin,";
			desc[2]="is capable of"; 
			desc[3]="living in volcanoes.";
			break;
		case 113: 
			desc[0]="A rare and"; 
			desc[1]="elusive Pokemon,";
			desc[2]="said to bring"; 
			desc[3]="happiness and luck.";
			break;
		case 114: 
			desc[0]="The entire body"; 
			desc[1]="is covered in";
			desc[2]="vines similar to"; 
			desc[3]="seaweed.";
			break;
		case 115: 
			desc[0]="The infant rarely"; 
			desc[1]="ventures out of";
			desc[2]="its mothers"; 
			desc[3]="protective pouch.";
			break;
		case 116: 
			desc[0]="Known to shoot"; 
			desc[1]="down flying bugs";
			desc[2]="with precision"; 
			desc[3]="blasts of ink.";
			break;
		case 117: 
			desc[0]="Capable of"; 
			desc[1]="swimming backwards";
			desc[2]="by flapping its"; 
			desc[3]="wing-like fins.";
			break;
		case 118: 
			desc[0]="Its tail fin"; 
			desc[1]="billows like an,";
			desc[2]="elegant ballroom"; 
			desc[3]="dress.";
			break;
		case 119: 
			desc[0]="It uses its mighty"; 
			desc[1]="horn to bore holes";
			desc[2]="in boulders to"; 
			desc[3]="make its nest.";
			break;
		case 120: 
			desc[0]="An enigmatic"; 
			desc[1]="Pokemon that can";
			desc[2]="regenerate any"; 
			desc[3]="limb it loses.";
			break;
		case 121: 
			desc[0]="Its central core"; 
			desc[1]="glows with the";
			desc[2]="seven colors"; 
			desc[3]="of the rainbow.";
			break;
		case 122: 
			desc[0]="An expert at"; 
			desc[1]="miming that loves";
			desc[2]="to perform."; 
			desc[3]="";
			break;
		case 123: 
			desc[0]="With ninja-like"; 
			desc[1]="agility, it can";
			desc[2]="create an illusion"; 
			desc[3]="of being everywhere.";
			break;
		case 124: 
			desc[0]="It seductively"; 
			desc[1]="wiggles its hips";
			desc[2]="as it walks, "; 
			desc[3]="almost like dancing.";
			break;
		case 125: 
			desc[0]="Normally found"; 
			desc[1]="near power plants,";
			desc[2]="they eat electricity"; 
			desc[3]="and cause blackouts.";
			break;
		case 126: 
			desc[0]="Its body always"; 
			desc[1]="burns with an ";
			desc[2]="orange glow, letting"; 
			desc[3]="it hide in flames.";
			break;
		case 127: 
			desc[0]="Grips its prey in"; 
			desc[1]="its pincers and";
			desc[2]="attempts to crush it."; 
			desc[3]="";
			break;
		case 128: 
			desc[0]="Once it locks on"; 
			desc[1]="to an enemy,";
			desc[2]="it will charge"; 
			desc[3]="furiously.";
			break;
		case 129: 
			desc[0]="It is believed"; 
			desc[1]="that in ancient";
			desc[2]="times, it used to"; 
			desc[3]="be less useless.";
			break;
		case 130: 
			desc[0]="Brutally vicious"; 
			desc[1]="and destructive.";
			desc[2]="Known to destroy"; 
			desc[3]="entire cities.";
			break;
		case 131: 
			desc[0]="A gentle soul that"; 
			desc[1]="can read the minds";
			desc[2]="of people as it"; 
			desc[3]="ferries them.";
			break;
		case 132: 
			desc[0]="Capable of copying"; 
			desc[1]="an enemy's genetic";
			desc[2]="code to instantly"; 
			desc[3]="transform itself.";
			break;
		case 133: 
			desc[0]="A rare Pokemon with"; 
			desc[1]="an irreguler genetic";
			desc[2]="code."; 
			desc[3]="";
			break;
		case 134: 
			desc[0]="Lives close to water,"; 
			desc[1]="it is often mistaken";
			desc[2]="by fisherman for a"; 
			desc[3]="mermaid.";
			break;
		case 135: 
			desc[0]="It accumulates"; 
			desc[1]="negative ions";
			desc[2]="in the atmosphere"; 
			desc[3]="in its fur.";
			break;
		case 136: 
			desc[0]="When storing"; 
			desc[1]="thermal energy,";
			desc[2]="internal body temp"; 
			desc[3]="can exceed 1,600\u00B0.";
			break;
		case 137: 
			desc[0]="A Pokemon consisting"; 
			desc[1]="entirely of code.";
			desc[2]="Can move freely in"; 
			desc[3]="cyberspace.";
			break;
		case 138: 
			desc[0]="Although long"; 
			desc[1]="extinct, it can";
			desc[2]="be resurrected"; 
			desc[3]="from fossils.";
			break;
		case 139: 
			desc[0]="A prehistoric"; 
			desc[1]="Pokemon that died";
			desc[2]="out when its shell"; 
			desc[3]="got too heavy to move.";
			break;
		case 140: 
			desc[0]="A Pokemon that"; 
			desc[1]="was resurrected from";
			desc[2]="a fossil found in"; 
			desc[3]="the ocean floor.";
			break;
		case 141: 
			desc[0]="Its sleek shape is"; 
			desc[1]="perfect for swimming.";
			desc[2]="It slashes its prey"; 
			desc[3]="using its claws.";
			break;
		case 142: 
			desc[0]="A ferocious"; 
			desc[1]="prehistoric Pokemon";
			desc[2]="that attacks with its"; 
			desc[3]="serrated fangs.";
			break;
		case 143: 
			desc[0]="Very lazy. Just"; 
			desc[1]="eats and sleeps.";
			desc[2]=""; 
			desc[3]="Seriosly.";
			break;
		case 144: 
			desc[0]="A Legendary bird"; 
			desc[1]="Pokemon said to";
			desc[2]="appear to skiers"; 
			desc[3]="lost in the snow.";
			break;
		case 145: 
			desc[0]="A Legendary bird"; 
			desc[1]="Pokemon said to";
			desc[2]="appear in the clouds"; 
			desc[3]="shooting lightning.";
			break;
		case 146: 
			desc[0]="A Legendary bird"; 
			desc[1]="Pokemon. Every flap";
			desc[2]="of its red wings"; 
			desc[3]="illuminates the sky.";
			break;
		case 147: 
			desc[0]="Long considered"; 
			desc[1]="a mythical Pokemon,";
			desc[2]="until a small colony"; 
			desc[3]="was discovered.";
			break;
		case 148: 
			desc[0]="A mystical Pokemon"; 
			desc[1]="that exudes a gentle";
			desc[2]="aura. Has the ability"; 
			desc[3]="to change the climate.";
			break;
		case 149: 
			desc[0]="An extremely rare"; 
			desc[1]="Pokemon. It is said";
			desc[2]="to be smarter than"; 
			desc[3]="most humans.";
			break;
		case 150: 
			desc[0]="It was created by"; 
			desc[1]="a scientist after";
			desc[2]="years of horrific"; 
			desc[3]="DNA engineering.";
			break;
		case 151: 
			desc[0]="So rare that some"; 
			desc[1]="still don't believe";
			desc[2]="it exists. Contains"; 
			desc[3]="the DNA of all Pokemon.";
			break;
		}
		return desc;
	}
	
	public static String getLine1ByDexNum(int dexNum){
		return Pokemon.getDescriptionByDexNum(dexNum)[0];
	}
	
	public static String getLine2ByDexNum(int dexNum){
		return Pokemon.getDescriptionByDexNum(dexNum)[1];
	}
	
	public static String getLine3ByDexNum(int dexNum){
		return Pokemon.getDescriptionByDexNum(dexNum)[2];
	}
	
	public static String getLine4ByDexNum(int dexNum){
		return Pokemon.getDescriptionByDexNum(dexNum)[3];
	}
	
	public static String getPokemonNameByDexNumber(int dexNumber){
		switch(dexNumber){
		case 1: return "Bulbasaur";
		 
		case 2: return "Ivysaur"; //return (new Pokemon.Ivysaur());
	 
		case 3: return "Venusaur";//return (new Pokemon.Venusaur());
	 
		case 4: return "Charmander";
	 
		case 5: return "Charmeleon";//return (new Pokemon.Charmeleon());
	 
		case 6: return "Charizard";//return (new Pokemon.Charizard());
	 
		case 7: return "Squirtle";
	 
		case 8: return "Wartortle";//return (new Pokemon.Wartortle());
	 
		case 9: return "Blastoise";//return (new Pokemon.Blastoise());
	 
		case 10: return "Caterpie";
	 
		case 11: return "Metapod";
	 
		case 12: return "Butterfree";
	 
		case 13: return "Weedle";//return (new Pokemon.Weedle());
	 
		case 14: return "Kakuna";//return (new Pokemon.Kakuna());
	 
		case 15: return "Beedrill";//return (new Pokemon.Beedrill());
	 
		case 16: return "Pidgey";
	 
		case 17: return "Pidgeotto";//return (new Pokemon.Pidgeotto());
	 
		case 18: return "Pidgeot"; //return (new Pokemon.Pidgeot());
	 
		case 19: return "Rattata";
	 
		case 20: return "Raticate";
	 
		case 21: return "Spearow";
		
		case 22: return "Fearow";
	 
		case 23: return "Ekans";
	 
		case 24: return "Arbok";
	 
		case 25: return "Pikachu";
	 
		case 26: return "Raichu";
	 
		case 27: return "Sandshrew";
	 
		case 28: return "Sandslash";
	 
		case 29: return "Nidoran \u2640";
	 
		case 30: return "Nidorina";
	 
		case 31: return "Nidoqueen";
	 
		case 32: return "Nidoran \u2642";
	 
		case 33: return "Nidorino";
	 
		case 34: return "Nidoking";
	 
		case 35: return "Clefairy";
	 
		case 36: return "Clefable";
	 
		case 37: return "Vulpix";
	 
		case 38: return "Ninetales";
	 
		case 39: return "Jigglypuff";
	 
		case 40: return "Wigglytuff";
	 
		case 41: return "Zubat";
	 
		case 42: return "Golbat";
	 
		case 43: return "Oddish";
	 
		case 44: return "Gloom";
	 
		case 45: return "Vileplume";
	 
		case 46: return "Paras";
	 
		case 47: return "Parasect";
	 
		case 48: return "Venonat";
	 
		case 49: return "Venomoth";
	 
		case 50: return "Diglett";
	 
		case 51: return "Dugtrio";
	 
		case 52: return "Meowth";
	 
		case 53: return "Persian";
	 
		case 54: return "Psyduck";
	 
		case 55: return "Golduck";
	 
		case 56: return "Mankey";
		
		case 57: return "Primate";
		
		case 58: return "Growlithe";
	 
		case 59: return "Arcanine";
	 
		case 60: return "Poliwag";
	 
		case 61: return "Poliwhirl";

		case 62: return "Poliwrath";
	 
		case 63: return "Abra";
	 
		case 64: return "Kadabra";
	 
		case 65: return "Alakazam";
	 
		case 66: return "Machop";
	 
		case 67: return "Machoke";
	 
		case 68: return "Machamp";
	 
		case 69: return "Bellsprout";
	 
		case 70: return "Weepinbell";
	 
		case 71: return "Victreebell";

		case 72: return "Tentacool";
		
		case 73: return "Tentacruel";
		
		case 74: return "Geodude";
		
		case 75: return "Graveller";
	 
		case 76: return "Golem";
	 
		case 77: return "Ponyta";
		
		case 78: return "Rapidash";

		case 79: return "Slowpoke";

		case 80: return "Slowbro";
	 
		case 81: return "Magnemite";
	 
		case 82: return "Magneton";
		
		case 83: return "Farfetch'd";
		
		case 84: return "Doduo";

		case 85: return "Dodrio";
	 
		case 86: return "Seel";
	 
		case 87: return "Dewgong";

		case 88: return "Grimer";
	 
		case 89: return "Muk";
	 
		case 90: return "Shellder";
	 
		case 91: return "Cloyster";
	 
		case 92: return "Gastly";
	 
		case 93: return "Haunter";
		 
		case 94: return "Gengar";
	 
		case 95: return "Onix";
	 
		case 96: return "Drowzee";
	 
		case 97: return "Hypno";
		 
		case 98: return "Krabby";
		 
		case 99: return "Kingler";
	 
		case 100: return "Voltorb";
	 
		case 101: return "Electrode";
	 
		case 102: return "Eggxecute";
	 
		case 103: return "Eggxecutor";
	 
		case 104: return "Cubone";
	 
		case 105: return "Marowak";
	 
		case 106: return "Hitmonlee";
	 
		case 107: return "Hitmonchan";
	 
		case 108: return "Lickitung";
	 
		case 109: return "Koffing";
	 
		case 110: return "Weezing";
	 
		case 111: return "Rhyhorn";
	 
		case 112: return "Rhydon";
	 
		case 113: return "Chansey";
	 
		case 114: return "Tangela";
	 
		case 115: return "Kangaskhan";
	 
		case 116: return "Horsea";
	 
		case 117: return "Seadra";
 
		case 118: return "Goldeen";
 
		case 119: return "Seaking";
 
		case 120: return "Staryu";
 
		case 121: return "Starmie";
 
		case 122: return "Mr. Mime";
 
		case 123: return "Scyther";
 
		case 124: return "Jynx";
 
		case 125: return "Electabuzz";
 
		case 126: return "Magmar";
 
		case 127: return "Pinsir";
 
		case 128: return "Tauros";
 
		case 129: return "Magikarp";
 
		case 130: return "Gyrados";
 
		case 131: return "Lapras";
 
		case 132: return "Ditto";
 
		case 133: return "Eevee";
 
		case 134: return "Vaporeon";
 
		case 135: return "Jolteon";
 
		case 136: return "Flareon";
 
		case 137: return "Porygon";
 
		case 138: return "Omanyte";
 
		case 139: return "Omastar";
 
		case 140: return "Kabuto";
		
		case 141: return "Kabutops";
 
		case 142: return "Aerodactyl";
 
		case 143: return "Snorlax";
		
		case 144: return "Articuno";

		case 145: return "Zapdos";
 
		case 146: return "Moltres";

		case 147: return "Dratini";
 
		case 148: return "Dragonair";
 
		case 149: return "Dragonite";
 
		case 150: return "Mewtwo";
 
//Mew, obviously.
		case 151: return "Mew";
		
		case 152: return "MissingNo.";
 
//Teddiursa, for historical purposes.
		case 216: return "Teddiursa";
 
//The bear, if you somehow catch it.
		case 217: return "Bruno";
 
		default: return "";
		}
	}
	
	public static Pokemon getPokemonByDexNumber(int dexNumber) throws IOException{
		
		switch(dexNumber){
	
			case 1: return (new Pokemon.Bulbasaur());
		 
			case 2: return (new Pokemon.Ivysaur());
		 
			case 3: return (new Pokemon.Venusaur());
		 
			case 4: return (new Pokemon.Charmander());
		 
			case 5: return (new Pokemon.Charmeleon());
		 
			case 6: return (new Pokemon.Charizard());
		 
			case 7: return (new Pokemon.Squirtle());
		 
			case 8: return (new Pokemon.Wartortle());
		 
			case 9: return (new Pokemon.Blastoise());
		 
			case 10: return (new Pokemon.Caterpie());
		 
			case 11: return (new Pokemon.Metapod());
		 
			case 12: return (new Pokemon.Butterfree());
		 
			case 13: return (new Pokemon.Weedle());
		 
			case 14: return (new Pokemon.Kakuna());
		 
			case 15: return (new Pokemon.Beedrill());
		 
			case 16: return (new Pokemon.Pidgey());
		 
			case 17: return (new Pokemon.Pidgeotto());
		 
			case 18: return (new Pokemon.Pidgeot());
		 
			case 19: return (new Pokemon.Rattata());
		 
			case 20: return (new Pokemon.Raticate());
		 
			case 21: return (new Pokemon.Spearow());
			
			case 22: return (new Pokemon.Fearow());
		 
			case 23: return (new Pokemon.Ekans());
		 
			case 24: return (new Pokemon.Arbok());
		 
			case 25: return (new Pokemon.Pikachu());
		 
			case 26: return (new Pokemon.Raichu());
		 
			case 27: return (new Pokemon.Sandshrew());
		 
			case 28: return (new Pokemon.Sandslash());
		 
			case 29: return (new Pokemon.Nidoran_F());
		 
			case 30: return (new Pokemon.Nidorina());
		 
			case 31: return (new Pokemon.Nidoqueen());
		 
			case 32: return (new Pokemon.Nidoran_M());
		 
			case 33: return (new Pokemon.Nidorino());
		 
			case 34: return (new Pokemon.Nidoking());
		 
			case 35: return (new Pokemon.Clefairy());
		 
			case 36: return (new Pokemon.Clefable());
		 
			case 37: return (new Pokemon.Vulpix());
		 
			case 38: return (new Pokemon.Ninetales());
		 
			case 39: return (new Pokemon.Jigglypuff());
		 
			case 40: return (new Pokemon.Wigglytuff());
		 
			case 41: return (new Pokemon.Zubat());
		 
			case 42: return (new Pokemon.Golbat());
		 
			case 43: return (new Pokemon.Oddish());
		 
			case 44: return (new Pokemon.Gloom());
		 
			case 45: return (new Pokemon.Vileplume());
		 
			case 46: return (new Pokemon.Paras());
		 
			case 47: return (new Pokemon.Parasect());
		 
			case 48: return (new Pokemon.Venonat());
		 
			case 49: return (new Pokemon.Venomoth());
		 
			case 50: return (new Pokemon.Diglett());
		 
			case 51: return (new Pokemon.Dugtrio());
		 
			case 52: return (new Pokemon.Meowth());
		 
			case 53: return (new Pokemon.Persian());
		 
			case 54: return (new Pokemon.Psyduck());
		 
			case 55: return (new Pokemon.Golduck());
		 
			case 56: return (new Pokemon.Mankey());
			
			case 57: return (new Pokemon.Primeape());
			
			case 58: return (new Pokemon.Growlithe());
		 
			case 59: return (new Pokemon.Arcanine());
		 
			case 60: return (new Pokemon.Poliwag());
		 
			case 61: return (new Pokemon.Poliwhirl());
	
			case 62: return (new Pokemon.Poliwrath());
		 
			case 63: return (new Pokemon.Abra());
		 
			case 64: return (new Pokemon.Kadabra());
		 
			case 65: return (new Pokemon.Alakazam());
		 
			case 66: return (new Pokemon.Machop());
		 
			case 67: return (new Pokemon.Machoke());
		 
			case 68: return (new Pokemon.Machamp());
		 
			case 69: return (new Pokemon.Bellsprout());
		 
			case 70: return (new Pokemon.Weepinbell());
		 
			case 71: return (new Pokemon.Victreebel());
	
			case 72: return (new Pokemon.Tentacool());
			
			case 73: return (new Pokemon.Tentacruel());
			
			case 74: return (new Pokemon.Geodude());
			
			case 75: return (new Pokemon.Graveler());
		 
			case 76: return (new Pokemon.Golem());
		 
			case 77: return (new Pokemon.Ponyta());
			
			case 78: return (new Pokemon.Rapidash());
	
			case 79: return (new Pokemon.Slowpoke());
	
			case 80: return (new Pokemon.Slowbro());
		 
			case 81: return (new Pokemon.Magnemite());
		 
			case 82: return (new Pokemon.Magneton());
			
			case 83: return (new Pokemon.Farfetchd());
			
			case 84: return (new Pokemon.Doduo());
	
			case 85: return (new Pokemon.Dodrio());
		 
			case 86: return (new Pokemon.Seel());
		 
			case 87: return (new Pokemon.Dewgong());
	
			case 88: return (new Pokemon.Grimer());
		 
			case 89: return (new Pokemon.Muk());
		 
			case 90: return (new Pokemon.Shellder());
		 
			case 91: return (new Pokemon.Cloyster());
		 
			case 92: return (new Pokemon.Gastly());
		 
			case 93: return (new Pokemon.Haunter());
			 
			case 94: return (new Pokemon.Gengar());
		 
			case 95: return (new Pokemon.Onix());
		 
			case 96: return (new Pokemon.Drowzee());
		 
			case 97: return (new Pokemon.Hypno());
			 
			case 98: return (new Pokemon.Krabby());
			 
			case 99: return (new Pokemon.Kingler());
		 
			case 100: return (new Pokemon.Voltorb());
		 
			case 101: return (new Pokemon.Electrode());
		 
			case 102: return (new Pokemon.Exeggcute());
		 
			case 103: return (new Pokemon.Exeggutor());
		 
			case 104: return (new Pokemon.Cubone());
		 
			case 105: return (new Pokemon.Marowak());
		 
			case 106: return (new Pokemon.Hitmonlee());
		 
			case 107: return (new Pokemon.Hitmonchan());
		 
			case 108: return (new Pokemon.Lickitung());
		 
			case 109: return (new Pokemon.Koffing());
		 
			case 110: return (new Pokemon.Weezing());
		 
			case 111: return (new Pokemon.Rhyhorn());
		 
			case 112: return (new Pokemon.Rhydon());
		 
			case 113: return (new Pokemon.Chansey());
		 
			case 114: return (new Pokemon.Tangela());
		 
			case 115: return (new Pokemon.Kangaskhan());
		 
			case 116: return (new Pokemon.Horsea());
		 
			case 117: return (new Pokemon.Seadra());
	 
			case 118: return (new Pokemon.Goldeen());
	 
			case 119: return (new Pokemon.Seaking());
	 
			case 120: return (new Pokemon.Staryu());
	 
			case 121: return (new Pokemon.Starmie());
	 
			case 122: return (new Pokemon.Mr_Mime());
	 
			case 123: return (new Pokemon.Scyther());
	 
			case 124: return (new Pokemon.Jynx());
	 
			case 125: return (new Pokemon.Electabuzz());
	 
			case 126: return (new Pokemon.Magmar());
	 
			case 127: return (new Pokemon.Pinsir());
	 
			case 128: return (new Pokemon.Tauros());
	 
			case 129: return (new Pokemon.Magikarp());
	 
			case 130: return (new Pokemon.Gyarados());
	 
			case 131: return (new Pokemon.Lapras());
	 
			case 132: return (new Pokemon.Ditto());
	 
			case 133: return (new Pokemon.Eevee());
	 
			case 134: return (new Pokemon.Vaporeon());
	 
			case 135: return (new Pokemon.Jolteon());
	 
			case 136: return (new Pokemon.Flareon());
	 
			case 137: return (new Pokemon.Porygon());
	 
			case 138: return (new Pokemon.Omanyte());
	 
			case 139: return (new Pokemon.Omastar());
	 
			case 140: return (new Pokemon.Kabuto());
			
			case 141: return (new Pokemon.Kabutops());
	 
			case 142: return (new Pokemon.Aerodactyl());
	 
			case 143: return (new Pokemon.Snorlax());
			
			case 144: return (new Pokemon.Articuno());
	
			case 145: return (new Pokemon.Zapdos());
	 
			case 146: return (new Pokemon.Moltres());
	
			case 147: return (new Pokemon.Dratini());
	 
			case 148: return (new Pokemon.Dragonair());
	 
			case 149: return (new Pokemon.Dragonite());
	 
			case 150: return (new Pokemon.Mewtwo());
	 
	//Mew, obviously.
			case 151: return (new Pokemon.Mew());
	 
			case 152: return (new Pokemon.MissingNo());
			
	//Teddiursa, for historical purposes.
			case 216: return (new Pokemon.Teddiursa());
	 
	//The bear, if you somehow catch it.
			case 217: return (new Pokemon.Ursaring());
	 
		
			default: return (new Pokemon.Magikarp());
		}
	}
	
	public static int getEvolLvl(int dexNumber){
		switch(dexNumber){
		case 1: return 16;
		case 2: return 32;
		case 3: return 1000;
		case 4: return 16;
		case 5: return 36;
		case 6: return 1000;
		case 7: return 16;
		case 8: return 36;
		case 9: return 1000;
		case 10: return 7;
		case 11: return 10;
		case 12: return 1000;
		case 13: return 7;
		case 14: return 10;
		case 15: return 1000;
		case 16: return 18;
		case 17: return 36;
		case 18: return 1000;
		case 19: return 20;
		case 20: return 1000;
		case 21: return 20;	
		case 22: return 1000;
		case 23: return 22;
		case 24: return 1000;
		case 25: return 1000;
		case 26: return 1000;
		case 27: return 22;
		case 28: return 1000;
		case 29: return 16;
		case 30: return 1000;
		case 31: return 1000;
		case 32: return 16;
		case 33: return 1000;
		case 34: return 1000;
		case 35: return 1000;
		case 36: return 1000;
		case 37: return 1000;
		case 38: return 1000;
		case 39: return 1000;
		case 40: return 1000;
		case 41: return 22;
		case 42: return 1000;
		case 43: return 21;
		case 44: return 1000; 
		case 45: return 1000;
		case 46: return 24;
		case 47: return 1000;
		case 48: return 31;
		case 49: return 1000;
		case 50: return 26;
		case 51: return 1000;
		case 52: return 28;
		case 53: return 1000;
		case 54: return 33;
		case 55: return 1000;
		case 56: return 28;
		case 57: return 1000;
		case 58: return 1000;
		case 59: return 1000;
		case 60: return 25;
		case 61: return 1000;
		case 62: return 1000;
		case 63: return 16;
		case 64: return 43;
		case 65: return 1000;
		case 66: return 28;
		case 67: return 53;
		case 68: return 1000;
		case 69: return 21;
		case 70: return 1000;
		case 71: return 1000;
		case 72: return 30;
		case 73: return 1000;
		case 74: return 25;
		case 75: return 44;
		case 76: return 1000;
		case 77: return 40;
		case 78: return 1000;
		case 79: return 37;
		case 80: return 1000;
		case 81: return 30;
		case 82: return 1000;
		case 83: return 1000;
		case 84: return 31;
		case 85: return 1000;
		case 86: return 34;
		case 87: return 1000;
		case 88: return 38;
		case 89: return 1000;
		case 90: return 1000;
		case 91: return 1000;
		case 92: return 25;
		case 93: return 39;
		case 94: return 1000;
		case 95: return 1000;
		case 96: return 26;
		case 97: return 1000;
		case 98: return 28;
		case 99: return 1000;
		case 100: return 30;
		case 101: return 1000;
		case 102: return 1000;
		case 103: return 1000;
		case 104: return 28; 
		case 105: return 1000;
		case 106: return 1000;
		case 107: return 1000;
		case 108: return 1000;
		case 109: return 35;
		case 110: return 1000;
		case 111: return 42;
		case 112: return 1000;
		case 113: return 1000;
		case 114: return 1000;
		case 115: return 1000;
		case 116: return 32;
		case 117: return 1000;
		case 118: return 33;
		case 119: return 1000;
		case 120: return 1000; 
		case 121: return 1000;
		case 122: return 1000;
		case 123: return 1000;
		case 124: return 1000;
		case 125: return 1000;
		case 126: return 1000;
		case 127: return 1000;
		case 128: return 1000;
		case 129: return 20; 
		case 130: return 1000;
		case 131: return 1000;
		case 132: return 1000;
		case 133: return 1000;
		case 134: return 1000;
		case 135: return 1000;
		case 136: return 1000;
		case 137: return 1000;
		case 138: return 40; 
		case 139: return 1000;
		case 140: return 40;
		case 141: return 1000;
		case 142: return 1000;
		case 143: return 1000;
		case 144: return 1000;
		case 145: return 1000; 
		case 146: return 1000;
		case 147: return 30;
		case 148: return 55;
		case 149: return 1000;
		case 150: return 1000;
		case 151: return 1000;
		case 152: return 1000;
		case 216: return 1000;
		case 217: return 1000;
 
		default: return 1000;
		}
	}
	
	public static Pokemon evolve(Pokemon user, int inc){
		//Discover the identity of the new pokemon.
		int PokeNum=user.getDexNum()+inc;
		Pokemon evolution;
		try {
			evolution = Pokemon.getPokemonByDexNumber(PokeNum);
			
//			if(user.getName() != user.getClass().getName()){
//				evolution.setName(user.getName());
//			}
//			SysOut.print("NAMES: " + user.getName() + "," + user.getClass().getName() + "," + evolution.getName() + "," + evolution.getClass().getName());
//			
			//Add all its important qualities.
			evolution.setLevel(user.getLevel());
			evolution.setExp(user.getExp());
			evolution.setStatus(user.getStatus());
			evolution.setBelt(user.getBelt());
			//Alter these quantities, since these change based on evolution.
			evolution.setNoAnimateCurrentHP(user.getCurrentHP());
			evolution.setMaxHP(user.getMaxHP());
			evolution.setAtkStat(user.getAtkStat());
			evolution.setDefStat(user.getDefStat());
			evolution.setSpAtkStat(user.getSpAtkStat());
			evolution.setSpeed(user.getSpeed());
			evolution.setAccuracy(user.getAccuracy());
			evolution.setEvasion(user.getEvasion());
			
			//Attacks
			evolution.getAttacks().clear();
			evolution.getAttacks().add(user.getAttacks().get(0));
			if (user.getAttacks().size()>1){
				evolution.getAttacks().add(user.getAttacks().get(1));	
				}
			if (user.getAttacks().size()>2){
				evolution.getAttacks().add(user.getAttacks().get(2));
				}
			if (user.getAttacks().size()>3){
				evolution.getAttacks().add(user.getAttacks().get(3));
				}
			
			//if(user.getName() != Pokemon.getPokemonByDexNumber(user.getDexNum()).getName()){
			//	evolution.setName(user.getName());
			//}
			
			
			return evolution;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String getName(){
		return _name;
	}
	
	//Used for nicknaming a pokemon.
	public void setName(String name){
		_name =name;
	}
	
	public void setType(Types t){
		_type1 = t;
	}
	
	public void revertType(){
		_type1 = originalType1;
	}

	public Types getType1(){
		return _type1;
	}
	public Types getType2(){
		return _type2;
	}


	public BufferedImage getFrontImage(){
		return _frontImage;
	}
	
	public BufferedImage getBackImage(){
		return _backImage;
	}
	public ImageIcon getGIF(){
		return _animation;
	}
	public BufferedImage getGIFasImage(){
		try {
			return ImageIO.read(this.getClass().getResource("/PokemonImages/Animation/"+this.getDexNum()+".gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public ImageIcon getIcon(){
		return _icon;
	}
	
	public String getIconText(){
		return _iconText;
	}
	public BufferedImage getDeadIcon(){
		return _deadIcon;
	}
	
	
	public int getMaxHP(){
		return _maxHP;
	}
	
	public int getLevel(){
		return _level;
	}
	
	public int getExp(){
		return _exp;
	}
	public double getExpMult(){
		//SysOut.print("Get Exp: " + _expMult);
		return _expMult;
	}
	public int getBaseExp(){
		return _baseExp;
	}
	
	public int getRate(){
		return _rate;
	}
	public int getAtkStat(){
		return _atkStat;
	}
	public int getDefStat(){
		return _defStat;
	}
	public int getSpAtkStat(){
		return _spAtkStat;
	}
	public int getSpDefStat(){
		return _spDefStat;
	}
	public int getSpeed(){
		return _speed;
	}
	public double getAccuracy(){
		return _accuracy;
	}
	public double getEvasion(){
		return _evasion;
	}
	
	//For use in levelling up, both setting and increasing values:
	public void setAtkStat(int inc){
		_atkStat = inc;
	}
	public void setDefStat(int inc){
		_defStat = inc;
	}
	public void setSpAtkStat(int inc){
		_spAtkStat = inc;
	}
	public void setSpDefStat(int inc){
		_spDefStat = inc;
	}
	public void setSpeed(int inc){
		_speed = inc;
	}
	public void setAccuracy(double inc){
		_accuracy = inc;
	}
	public void setEvasion(double inc){
		_evasion = inc;
	}
	
	public void incMaxHP(int inc){
		_maxHP=_maxHP+inc;
	}
	
	public void incLevel(int inc){
		_level=_level+inc;
	}
	
	public void incExp(int inc){
		_exp = _exp+inc;
	}
	
	/**For item use and battle use.
	 * 0=nothing, 1=asleep 2=poisoned 3=paralyzed 4=burned 5=frozen 6=badly poison
	*/
	public int getStatus(){
		return _status;
	}
	public String getStatusAcro(){
		switch (_status){
			case 0:
				return "";
			case 1:
				return "SLP";
			case 2:
				return "PSN";
			case 3:
				return "PAR";
			case 4:
				return "BRN";
			case 5:
				return "FRZ";
			case 6:
				return "PSN";
			default:
				return "";
		}
	}
	
	
	public String getStatusWord(){
		switch (_status){
			case 0:
				return "";
			case 1:
				return "asleep";
			case 2:
				return "poisoned";
			case 3:
				return "paralyzed";
			case 4:
				return "burned";
			case 5:
				return "frozen";
			case 6:
				return "badly poisoned";
			default:
				return "";
		}
	}
	
	public void setStatus(int newval){
		if((newval == 4 && (this.getType1() == Types.FIRE || this.getType2() == Types.FIRE))){return;}
		if((newval == 5 && (this.getType1() == Types.ICE || this.getType2() == Types.ICE))){return;}
		_status = newval;
	}
	
	//For use in creating trainer and wild Pokemon.
	public void setMaxHP(int newval){
		_maxHP=newval;
	}
	
	public void setLevel(int newval){
		if(newval < 100)
			_level=newval;
		else
			_level = 100;
	}
	
	public void setCHLevel(int newval){
		_level = newval;
	}
	
	public Pokemon setWildLevel(int newLevel){
		_level = newLevel;
		
		Pokemon.generateNewStats(this);
		
		if(attackTree != null){
			this.getAttacks().clear();
			for(int i = _level; i > 0; i--){
				if(this.getAttacks().size() == 4) break;
				
				if(attackTree[i] != null) {
					boolean copy =false;
					for(int j = 0; j<this.getAttacks().size();j++){
						if(attackTree[i].getName()==this.getAttacks().get(j).getName()){
							copy=true;
						}
					}
					
					if(!copy){
						this.getAttacks().add(attackTree[i]);
					}
				};
			}
		}
		
		Vector<Attack> temp = new Vector<Attack>();
		for(int i = 0; i < this.getAttacks().size(); i++){
			temp.add(this.getAttacks().get(this.getAttacks().size() - 1 - i));
		}
		this.getAttacks().clear();
		for(int i = 0; i < temp.size(); i++){
			this.getAttacks().add(temp.get(i));
		}
		
		this.setCurrentHP(this.getMaxHP());
		return this;
	}
	
	public void setExp(int newval){
		_exp = newval;
	}

	public int getBelt(){
		return _belt;
	}
	
	public void setBelt(int newval){
		_belt = newval;
		if(_belt == 7){
			
		}
	}
	
	//To immediately identify Pokemon.
	public int getDexNum(){
		return _dexNum;
	}
	
	//May come in use for evolution. Careful though.
	public void setDexNum(int Num){
		_dexNum = Num;
	}

	//For evolution
	public void setImages(int i){
		//try {
			//SysOut.print("POKEMON NUMBER: " + i);
//			_frontImage=ImageIO.read(this.getClass().getResource("/PokemonImages/Front/"+i+".png"));
//			_backImage=ImageIO.read(this.getClass().getResource("/PokemonImages/Back/"+i+".png"));
//			_icon = new ImageIcon(this.getClass().getResource("/PokemonImages/Icon/"+i+".gif"));
//			_deadIcon = ImageIO.read(this.getClass().getResource("/PokemonImages/Icon/"+i+".gif"));
//			_animation= new ImageIcon(this.getClass().getResource("/PokemonImages/Animation/"+i+".gif"));

	//	if(i < 175){
			_frontImage = PImg.front[i];
			_backImage = PImg.back[i];
			_icon = PImg.icon[i];
			_deadIcon = PImg.faint[i];
			_animation = PImg.anim[i];
	//	}
		//else{
			//SysOut.print("URSARING WTF");
			//System.exit(0);
		//}
			//_icon = Toolkit.getDefaultToolkit().createImage(this.getclas));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	/**
	 * Frequently used method used to both do 
	 * damage to and heal your Pokemon.
	 * 
	 * @param currentHP
	 */
	public void setCurrentHP(int currentHP){		
			_currentHP = currentHP;
	}
	
	public void setNoAnimateCurrentHP(int currentHP){
		_currentHP = currentHP;
		if(_currentHP < 0){
			_currentHP = 0;
		}
	}
	
	public int getCurrentHP(){
		return _currentHP;
	}
	
	public void addNewAttackNum(int AttackNum){
		//Initiate Epic Switch Statement for Pokemon:
    	switch(AttackNum){
    	
		case 1: _attacks.add(new  Attack.Pound());
		break;

		case 2: _attacks.add(new  Attack.KarateChop());
		break;

		case 3: _attacks.add(new  Attack.DoubleSlap());
		break;

		case 4: _attacks.add(new  Attack.CometPunch());
		break;

		case 5: _attacks.add(new  Attack.MegaPunch());
		break;

		case 6: _attacks.add(new  Attack.PayDay());
		break;

		case 7: _attacks.add(new  Attack.FirePunch());
		break;

		case 8: _attacks.add(new  Attack.IcePunch());
		break;

		case 9: _attacks.add(new  Attack.ThunderPunch());
		break;

		case 10: _attacks.add(new  Attack.Scratch());
		break;

		case 11: _attacks.add(new  Attack.ViceGrip());
		break;

		case 12: _attacks.add(new  Attack.Guillotine());
		break;

		case 13: _attacks.add(new  Attack.RazorWind());
		break;

		case 14: _attacks.add(new  Attack.SwordsDance());
		break;

		case 15: _attacks.add(new  Attack.Cut());
		break;

		case 16: _attacks.add(new  Attack.Gust());
		break;

		case 17: _attacks.add(new  Attack.WingAttack());
		break;

		case 18: _attacks.add(new  Attack.Whirlwind());
		break;

		case 19: _attacks.add(new  Attack.Fly());
		break;

		case 20: _attacks.add(new  Attack.Bind());
		break;

		case 21: _attacks.add(new  Attack.Slam());
		break;

		case 22: _attacks.add(new  Attack.VineWhip());
		break;

		case 23: _attacks.add(new  Attack.Stomp());
		break;

		case 24: _attacks.add(new  Attack.DoubleKick());
		break;

		case 25: _attacks.add(new  Attack.MegaKick());
		break;

		case 26: _attacks.add(new  Attack.JumpKick());
		break;

		case 27: _attacks.add(new  Attack.RollingKick());
		break;

		case 28: _attacks.add(new  Attack.SandAttack());
		break;

		case 29: _attacks.add(new  Attack.Headbutt());
		break;

		case 30: _attacks.add(new  Attack.HornAttack());
		break;

		case 31: _attacks.add(new  Attack.FuryAttack());
		break;

		case 32: _attacks.add(new  Attack.HornDrill());
		break;

		case 33: _attacks.add(new  Attack.Tackle());
		break;

		case 34: _attacks.add(new  Attack.BodySlam());
		break;

		case 35: _attacks.add(new  Attack.Wrap());
		break;

		case 36: _attacks.add(new  Attack.TakeDown());
		break;

		case 37: _attacks.add(new  Attack.Thrash());
		break;

		case 38: _attacks.add(new  Attack.DoubleEdge());
		break;

		case 39: _attacks.add(new  Attack.TailWhip());
		break;

		case 40: _attacks.add(new  Attack.PoisonSting());
		break;

		case 41: _attacks.add(new  Attack.Twineedle());
		break;

		case 42: _attacks.add(new  Attack.PinMissile());
		break;

		case 43: _attacks.add(new  Attack.Leer());
		break;

		case 44: _attacks.add(new  Attack.Bite());
		break;

		case 45: _attacks.add(new  Attack.Growl());
		break;

		case 46: _attacks.add(new  Attack.Roar());
		break;

		case 47: _attacks.add(new  Attack.Sing());
		break;

		case 48: _attacks.add(new  Attack.Supersonic());
		break;

		case 49: _attacks.add(new  Attack.SonicBoom());
		break;

		case 50: _attacks.add(new  Attack.Disable());
		break;

		case 51: _attacks.add(new  Attack.Acid());
		break;

		case 52: _attacks.add(new  Attack.Ember());
		break;

		case 53: _attacks.add(new  Attack.Flamethrower());
		break;

		case 54: _attacks.add(new  Attack.Mist());
		break;

		case 55: _attacks.add(new  Attack.WaterGun());
		break;

		case 56: _attacks.add(new  Attack.HydroPump());
		break;

		case 57: _attacks.add(new  Attack.Surf());
		break;

		case 58: _attacks.add(new  Attack.IceBeam());
		break;

		case 59: _attacks.add(new  Attack.Blizzard());
		break;

		case 60: _attacks.add(new  Attack.Psybeam());
		break;

		case 61: _attacks.add(new  Attack.BubbleBeam());
		break;

		case 62: _attacks.add(new  Attack.AuroraBeam());
		break;

		case 63: _attacks.add(new  Attack.HyperBeam());
		break;

		case 64: _attacks.add(new  Attack.Peck());
		break;

		case 65: _attacks.add(new  Attack.DrillPeck());
		break;

		case 66: _attacks.add(new  Attack.Submission());
		break;

		case 67: _attacks.add(new  Attack.LowKick());
		break;

		case 68: _attacks.add(new  Attack.Counter());
		break;

		case 69: _attacks.add(new  Attack.SeismicToss());
		break;

		case 70: _attacks.add(new  Attack.Strength());
		break;

		case 71: _attacks.add(new  Attack.Absorb());
		break;

		case 72: _attacks.add(new  Attack.MegaDrain());
		break;

		case 73: _attacks.add(new  Attack.LeechSeed());
		break;

		case 74: _attacks.add(new  Attack.Growth());
		break;

		case 75: _attacks.add(new  Attack.RazorLeaf());
		break;

		case 76: _attacks.add(new  Attack.SolarBeam());
		break;

		case 77: _attacks.add(new  Attack.PoisonPowder());
		break;

		case 78: _attacks.add(new  Attack.StunSpore());
		break;

		case 79: _attacks.add(new  Attack.SleepPowder());
		break;

		case 80: _attacks.add(new  Attack.PetalDance());
		break;

		case 81: _attacks.add(new  Attack.StringShot());
		break;

		case 82: _attacks.add(new  Attack.DragonRage());
		break;

		case 83: _attacks.add(new  Attack.FireSpin());
		break;

		case 84: _attacks.add(new  Attack.ThunderShock());
		break;

		case 85: _attacks.add(new  Attack.Thunderbolt());
		break;

		case 86: _attacks.add(new  Attack.ThunderWave());
		break;

		case 87: _attacks.add(new  Attack.Thunder());
		break;

		case 88: _attacks.add(new  Attack.RockThrow());
		break;

		case 89: _attacks.add(new  Attack.Earthquake());
		break;

		case 90: _attacks.add(new  Attack.Fissure());
		break;

		case 91: _attacks.add(new  Attack.Dig());
		break;

		case 92: _attacks.add(new  Attack.Toxic());
		break;

		case 93: _attacks.add(new  Attack.Confusion());
		break;

		case 94: _attacks.add(new  Attack.Psychic());
		break;

		case 95: _attacks.add(new  Attack.Hypnosis());
		break;

		case 96: _attacks.add(new  Attack.Meditate());
		break;

		case 97: _attacks.add(new  Attack.Agility());
		break;

		case 98: _attacks.add(new  Attack.QuickAttack());
		break;

		case 99: _attacks.add(new  Attack.Rage());
		break;

		case 100: _attacks.add(new  Attack.Teleport());
		break;

		case 101: _attacks.add(new  Attack.NightShade());
		break;

//		case 102: _attacks.add(new  Attack.Mimic());
//		break;

		case 103: _attacks.add(new  Attack.Screech());
		break;

		case 104: _attacks.add(new  Attack.DoubleTeam());
		break;

		case 105: _attacks.add(new  Attack.Recover());
		break;

		case 106: _attacks.add(new  Attack.Harden());
		break;

		case 107: _attacks.add(new  Attack.Minimize());
		break;

		case 108: _attacks.add(new  Attack.SmokeScreen());
		break;

		case 109: _attacks.add(new  Attack.ConfuseRay());
		break;

		case 110: _attacks.add(new  Attack.Withdraw());
		break;

		case 111: _attacks.add(new  Attack.DefenseCurl());
		break;

		case 112: _attacks.add(new  Attack.Barrier());
		break;

		case 113: _attacks.add(new  Attack.LightScreen());
		break;

		case 114: _attacks.add(new  Attack.Haze());
		break;

		case 115: _attacks.add(new  Attack.Reflect());
		break;

		case 116: _attacks.add(new  Attack.FocusEnergy());
		break;

//		case 117: _attacks.add(new  Attack.Bide());
//		break;

		case 118: _attacks.add(new  Attack.Metronome());
		break;

		case 119: _attacks.add(new  Attack.MirrorMove());
		break;

		case 120: _attacks.add(new  Attack.Selfdestruct());
		break;

		case 121: _attacks.add(new  Attack.EggBomb());
		break;

		case 122: _attacks.add(new  Attack.Lick());
		break;

		case 123: _attacks.add(new  Attack.Smog());
		break;

		case 124: _attacks.add(new  Attack.Sludge());
		break;

		case 125: _attacks.add(new  Attack.BoneClub());
		break;

		case 126: _attacks.add(new  Attack.FireBlast());
		break;

		case 127: _attacks.add(new  Attack.Waterfall());
		break;

		case 128: _attacks.add(new  Attack.Clamp());
		break;

		case 129: _attacks.add(new  Attack.Swift());
		break;

		case 130: _attacks.add(new  Attack.SkullBash());
		break;

		case 131: _attacks.add(new  Attack.SpikeCannon());
		break;

		case 132: _attacks.add(new  Attack.Constrict());
		break;

		case 133: _attacks.add(new  Attack.Amnesia());
		break;

		case 134: _attacks.add(new  Attack.Kinesis());
		break;

		case 135: _attacks.add(new  Attack.Softboiled());
		break;

		case 136: _attacks.add(new  Attack.HiJumpKick());
		break;

		case 137: _attacks.add(new  Attack.Glare());
		break;

		case 138: _attacks.add(new  Attack.DreamEater());
		break;

		case 139: _attacks.add(new  Attack.PoisonGas());
		break;

		case 140: _attacks.add(new  Attack.Barrage());
		break;

		case 141: _attacks.add(new  Attack.LeechLife());
		break;

		case 142: _attacks.add(new  Attack.LovelyKiss());
		break;

		case 143: _attacks.add(new  Attack.SkyAttack());
		break;

		case 144: _attacks.add(new  Attack.Transform());
		break;

		case 145: _attacks.add(new  Attack.Bubble());
		break;

		case 146: _attacks.add(new  Attack.DizzyPunch());
		break;

		case 147: _attacks.add(new  Attack.Spore());
		break;

		case 148: _attacks.add(new  Attack.Flash());
		break;

		case 149: _attacks.add(new  Attack.Psywave());
		break;

		case 150: _attacks.add(new  Attack.Splash());
		break;

		case 151: _attacks.add(new  Attack.AcidArmor());
		break;

		case 152: _attacks.add(new  Attack.Crabhammer());
		break;

		case 153: _attacks.add(new  Attack.Explosion());
		break;

		case 154: _attacks.add(new  Attack.FurySwipes());
		break;

		case 155: _attacks.add(new  Attack.Bonemerang());
		break;

		case 156: _attacks.add(new  Attack.Rest());
		break;

		case 157: _attacks.add(new  Attack.RockSlide());
		break;

		case 158: _attacks.add(new  Attack.HyperFang());
		break;

		case 159: _attacks.add(new  Attack.Sharpen());
		break;

		case 160: _attacks.add(new  Attack.Conversion());
		break;

		case 161: _attacks.add(new  Attack.TriAttack());
		break;

		case 162: _attacks.add(new  Attack.SuperFang());
		break;

		case 163: _attacks.add(new  Attack.Slash());
		break;

//		case 164: _attacks.add(new  Attack.Substitute());
//		break;

		case 165: _attacks.add(new  Attack.Struggle());
		break;
		
		default: _attacks.add(new Attack.Pound());
 
    	}
	}
	
	public boolean hasAttack(int attackNum){
		for (int i=0; i<this.getAttacks().size();i++){
			if(this.getAttacks().get(i).getAttackNum()==attackNum){
				return true;
			}
		}
		return false;
	}
	
	
	//This is to add Attacks by name. Useful to have both ways. 
	public void addNewAttack(Attack a){
		_attacks.add(a);
	}


	public Vector<Attack> getAttacks(){
		return _attacks;
	}

	public void clearAttacks(){
		_attacks.clear();
	}
	
	public static boolean matchEitherType(Types type1, Types type2, Types match){
		if(type1 == match || type2 == match) return true;
		
		return false;
	}
	
	public static boolean isSuperEffective(Types attackType, Types receiverType1, Types receiverType2){
		
		switch(attackType){
		
		case FIGHTING:  
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.NORMAL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ICE) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DARK))
				return true;
			break;
			
		case FLYING: 
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIGHTING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.BUG) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GRASS))
				return true;
			break;
			
		case POISON:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GRASS))
				return true;
			break;
			
		case GROUND: 
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.POISON) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIRE) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ELECTRIC))
				return true;
			break;
		
		case ROCK:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FLYING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.BUG) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIRE) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ICE))
				return true;
			break;
			
		case BUG:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GRASS) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.PSYCHIC) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DARK))
				return true;
			break;
			
		case GHOST:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GHOST) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.PSYCHIC))
				return true;
			break;
			
		case STEEL:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ICE))
				return true;
			break;
			
		case FIRE:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.BUG) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GRASS) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ICE))
				return true;
			break;
			
		case WATER:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GROUND) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIRE))
				return true;
			break;
			
		case GRASS:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GROUND) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.WATER))
				return true;
			break;
			
		case ELECTRIC:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FLYING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.WATER))
				return true;
			break;
			
		case PSYCHIC:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIGHTING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.POISON))
				return true;
			break;
			
		case ICE:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FLYING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GROUND) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.WATER) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DRAGON))
				return true;
			break;
			
		case DRAGON:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.DRAGON))
				return true;
			break;
			
		case DARK:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GHOST) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.PSYCHIC))
				return true;
			break;	
		}
		
		return false;
	}
	

	public static boolean isNotVeryEffective(Types attackType, Types receiverType1, Types receiverType2){
		switch(attackType){
	
		case NORMAL:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL))
				return true;
			break;
			
		case FIGHTING:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FLYING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.POISON) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.BUG) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.PSYCHIC))
				return true;
			break;
			
		case FLYING:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ELECTRIC))
				return true;
			break;
			
		case POISON:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.POISON) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GROUND) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GHOST))
				return true;
			break;
			
		case GROUND:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.BUG) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GRASS))
				return true;
			break;
			
		case ROCK:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIGHTING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GROUND) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL))
				return true;
			break;
			
		case BUG:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIGHTING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FLYING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.POISON) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GHOST) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIRE))
				return true;
			break;
			
		case GHOST:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DARK))
				return true;
			break;
			
		case STEEL:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIRE) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.WATER) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ELECTRIC))
				return true;
			break;
			
		case FIRE:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.ROCK) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIRE) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.WATER) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DRAGON))
				return true;
			break;
			
		case WATER:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.WATER) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GRASS) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DRAGON))
				return true;
			break;
			
		case GRASS:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FLYING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.POISON) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.BUG) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIRE) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.GRASS) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DRAGON))
				return true;
			break;
			
		case ELECTRIC:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GRASS) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ELECTRIC) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DRAGON))
				return true;
			break;
			
		case PSYCHIC:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.PSYCHIC))
				return true;
			break;
			
		case ICE:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIRE) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.WATER) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.ICE))
				return true;
			break;
			
		case DRAGON:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL))
				return true;
			break;
			
		case DARK:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FIGHTING) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL) ||
					Pokemon.matchEitherType(receiverType1, receiverType2, Types.DARK))
				return true;
			break;
		}
		
		return false;
	}
	
	
	public static boolean hasNoEffect(Types attackType, Types receiverType1, Types receiverType2){
		switch(attackType){
		
		case NORMAL:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GHOST))
				return true;
			break;
			
		case FIGHTING:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GHOST))
				return true;
			break;
		
		case POISON:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.STEEL))
				return true;
			break;
			
		case GROUND:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.FLYING))
				return true;
			break;
			
		case GHOST:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.NORMAL))
				return true;
			break;
			
		case ELECTRIC:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.GROUND))
				return true;
			break;
			
		case PSYCHIC:
			if(Pokemon.matchEitherType(receiverType1, receiverType2, Types.DARK))
				return true;
			break;
		}
		
		return false;
	}

	public void heal() {
		_currentHP = _maxHP;
		for(int i = 0; i < this.getAttacks().size(); i++){
			this.getAttacks().get(i).setCurrentPP(this.getAttacks().get(i).getMaxPP());
		}
		this.setStatus(0);
	}
	
	public static Pokemon clone(Pokemon original){
		Pokemon temp = null;
		
		temp = new Pokemon(original.getDexNum(), original.getName(), original.getType1(), original.getType2(), original.getMaxHP(), original.getLevel(), original.getExp(), original.getStatus(), original.getBelt(), original.getRate(), original.getAtkStat(),original.getDefStat(), original.getSpAtkStat(), original.getSpDefStat(), original.getSpeed(), original.getAtkIV(), original.getDefIV(), original.getSpAtkIV(), original.getSpDefIV(), original.getSpdIV(), original.getHPIV(), original.getAccuracy(), original.getEvasion());
		temp.setNoAnimateCurrentHP(original.getCurrentHP());
		
		for(int i = 0; i < original.getAttacks().size(); i++){
			temp.addNewAttack(original.getAttacks().get(i));
		}
		
		return temp;
	}

	//==================================================
	//GAP==================================================
	//==================================================
	
	public static class Bulbasaur extends Pokemon{
		public Bulbasaur() {
			super(1, "Bulbasaur", Types.GRASS, Types.POISON, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=16;
			
			//Base Stats.
			this._baseExp=64;
			this._expMult=1.0;
			this._baseHP=45;
			this._baseAtk=49;
			this._baseDef=49;
			this._baseSpAtk=65;
			this._baseSpDef=65;
			this._baseSpd=45;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Growl();
			this.attackTree[7]=new Attack.LeechSeed();
			this.attackTree[13]=new Attack.VineWhip();
			this.attackTree[20]=new Attack.PoisonPowder();
			this.attackTree[27]=new Attack.RazorLeaf();
			this.attackTree[34]=new Attack.Growth();
			this.attackTree[41]=new Attack.SleepPowder();
			this.attackTree[48]=new Attack.SolarBeam();
			
			
		}
	}
	
	public static class Ivysaur extends Pokemon{
		public Ivysaur() {
			super(2, "Ivysaur", Types.GRASS, Types.POISON, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=32;
			
			this._baseExp=141;
			this._expMult=1.0;
			
			this._baseHP=60;
			this._baseAtk=62;
			this._baseDef=63;
			this._baseSpAtk=80;
			this._baseSpDef=80;
			this._baseSpd=60;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Growl();
			this.attackTree[3]=new Attack.LeechSeed();
			this.attackTree[7]=new Attack.LeechSeed();
			this.attackTree[13]=new Attack.VineWhip();
			this.attackTree[22]=new Attack.PoisonPowder();
			this.attackTree[30]=new Attack.RazorLeaf();
			this.attackTree[38]=new Attack.Growth();
			this.attackTree[46]=new Attack.SleepPowder();
			this.attackTree[54]=new Attack.SolarBeam();
		}
	}
	
	public static class Venusaur extends Pokemon{
		public Venusaur() {
			super(3, "Venusaur", Types.GRASS, Types.POISON, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			this._baseExp=208;
			this._expMult=1.0;
			
			this._baseHP=80;
			this._baseAtk=82;
			this._baseDef=83;
			this._baseSpAtk=100;
			this._baseSpDef=100;
			this._baseSpd=80;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Growl();
			this.attackTree[3]=new Attack.LeechSeed();
			this.attackTree[4]=new Attack.VineWhip();
			this.attackTree[7]=new Attack.LeechSeed();
			this.attackTree[13]=new Attack.VineWhip();
			this.attackTree[22]=new Attack.PoisonPowder();
			this.attackTree[30]=new Attack.RazorLeaf();
			this.attackTree[43]=new Attack.Growth();
			this.attackTree[55]=new Attack.SleepPowder();
			this.attackTree[65]=new Attack.SolarBeam();
		}
	}
	
    public static class Charmander extends Pokemon{
		public Charmander() {
			super(4, "Charmander", Types.FIRE, Types.NONE, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			this._evolLvl=16;
			
			//Base Stats.
			this._baseExp=65;
			this._expMult=1.0;
			
			this._baseHP=39;
			this._baseAtk=52;
			this._baseDef=43;
			this._baseSpAtk=60;
			this._baseSpDef=50;
			this._baseSpd=65;
			
			this.attackTree[1]=new Attack.Scratch();
			this.attackTree[2]=new Attack.Growl();
			this.attackTree[9]=new Attack.Ember();
			this.attackTree[15]=new Attack.Leer();
			this.attackTree[22]=new Attack.Rage();
			this.attackTree[30]=new Attack.Slash();
			this.attackTree[38]=new Attack.Flamethrower();
			this.attackTree[46]=new Attack.FireSpin();
		}
	}
    
    public static class Charmeleon extends Pokemon{
    	public Charmeleon() {
			super(5, "Charmeleon", Types.FIRE, Types.NONE, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			this._evolLvl=36;
			
			//Base Stats.
			this._baseExp=142;
			this._expMult=1.0;
			
			this._baseHP=58;
			this._baseAtk=64;
			this._baseDef=58;
			this._baseSpAtk=80;
			this._baseSpDef=65;
			this._baseSpd=80;
			
			this.attackTree[1]=new Attack.Scratch();
			this.attackTree[2]=new Attack.Growl();
			this.attackTree[3]=new Attack.Ember();
			this.attackTree[15]=new Attack.Leer();
			this.attackTree[24]=new Attack.Rage();
			this.attackTree[33]=new Attack.Slash();
			this.attackTree[42]=new Attack.Flamethrower();
			this.attackTree[56]=new Attack.FireSpin();
    	}
    }
    
    public static class Charizard extends Pokemon{
    	public Charizard() {
			super(6, "Charizard", Types.FIRE, Types.FLYING, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
		    //Base Stats.
			this._baseExp=209;
			this._expMult=1.0;
			
			this._baseHP=78;
			this._baseAtk=84;
			this._baseDef=78;
			this._baseSpAtk=109;
			this._baseSpDef=85;
			this._baseSpd=100;
			
			this.attackTree[1]=new Attack.Scratch();
			this.attackTree[2]=new Attack.Growl();
			this.attackTree[3]=new Attack.Ember();
			this.attackTree[4]=new Attack.Leer();
			this.attackTree[24]=new Attack.Rage();
			this.attackTree[36]=new Attack.Slash();
			this.attackTree[46]=new Attack.Flamethrower();
			this.attackTree[55]=new Attack.FireSpin();
    	}
    }
    
    public static class Squirtle extends Pokemon{
		public Squirtle() {
			super(7, "Squirtle", Types.WATER, Types.NONE, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			this._evolLvl=16;
			
			//Base Stats.
			this._baseExp=66;
			this._expMult=1.0;
			this._baseHP=44;
			this._baseAtk=48;
			this._baseDef=65;
			this._baseSpAtk=50;
			this._baseSpDef=64;
			this._baseSpd=43;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.TailWhip();
			this.attackTree[8]=new Attack.Bubble();
			this.attackTree[15]=new Attack.WaterGun();
			this.attackTree[22]=new Attack.Bite();
			this.attackTree[28]=new Attack.Withdraw();
			this.attackTree[35]=new Attack.SkullBash();
			this.attackTree[42]=new Attack.HydroPump();
		}
	}
    
    public static class Wartortle extends Pokemon{
    	public Wartortle() {
			super(8, "Wartortle", Types.WATER, Types.NONE, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			this._evolLvl=36;
			
			//Base Stats.
			this._baseExp=143;
			this._expMult=1.0;
			
			this._baseHP=59;
			this._baseAtk=63;
			this._baseDef=80;
			this._baseSpAtk=65;
			this._baseSpDef=80;
			this._baseSpd=58;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.TailWhip();
			this.attackTree[3]=new Attack.Bubble();
			this.attackTree[8]=new Attack.Bubble();
			this.attackTree[15]=new Attack.WaterGun();
			this.attackTree[24]=new Attack.Bite();
			this.attackTree[31]=new Attack.Withdraw();
			this.attackTree[39]=new Attack.SkullBash();
			this.attackTree[47]=new Attack.HydroPump();
    	}
    }
    
    public static class Blastoise extends Pokemon{
		public Blastoise() {
			super(9, "Blastoise", Types.WATER, Types.NONE, 8, 5, 0, 0, 1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;


			//Base Stats.
			this._baseExp=210;
			this._expMult=1.0;
			
			this._baseHP=79;
			this._baseAtk=83;
			this._baseDef=100;
			this._baseSpAtk=85;
			this._baseSpDef=105;
			this._baseSpd=78;
		
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.TailWhip();
			this.attackTree[8]=new Attack.Bubble();
			this.attackTree[15]=new Attack.WaterGun();
			this.attackTree[24]=new Attack.Bite();
			this.attackTree[31]=new Attack.Withdraw();
			this.attackTree[42]=new Attack.SkullBash();
			this.attackTree[52]=new Attack.HydroPump();
			

		}
	}
    
	
	public static class Caterpie extends Pokemon{
		public Caterpie() {
			super(10, "Caterpie", Types.BUG, Types.NONE, 8, 4, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
		
			this._evolLvl = 7;
			
			this._baseExp=53;
			this._expMult=1.0;
			this._baseHP=45;
			this._baseAtk=30;
			this._baseDef=35;
			this._baseSpAtk=20;
			this._baseSpDef=20;
			this._baseSpd=45;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.StringShot();
			
			
		}
	}
	
	public static class Metapod extends Pokemon{
		public Metapod() {
			super(11, "Metapod", Types.BUG, Types.NONE, 8, 5, 0, 0, 7, 120,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=10;
			
			//Base Stats.
			this._baseExp=72;
			this._expMult=1.0;
			
			this._baseHP=50;
			this._baseAtk=20;
			this._baseDef=55;
			this._baseSpAtk=25;
			this._baseSpDef=25;
			this._baseSpd=30;
			
			this.attackTree[1]=new Attack.Harden();
			this.attackTree[8]=new Attack.Harden();

		}
	}
	
	public static class Butterfree extends Pokemon{
		public Butterfree() {
			//Currently GRASS type, thats wrong, but its okay for now.
			super(12, "Butterfree", Types.BUG, Types.FLYING, 20, 7, 0, 0, 7, 45, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			//Base Stats.
			this._baseExp=160;
			this._expMult=1.0;
			this._baseHP=60;
			this._baseAtk=45;
			this._baseDef=50;
			this._baseSpAtk=80;
			this._baseSpDef=80;
			this._baseSpd=70;
			
			this.attackTree[1]=new Attack.Confusion();
			this.attackTree[12]=new Attack.Confusion();
			this.attackTree[15]=new Attack.PoisonPowder();
			this.attackTree[16]=new Attack.StunSpore();
			this.attackTree[17]=new Attack.SleepPowder();
			this.attackTree[21]=new Attack.Supersonic();
			this.attackTree[26]=new Attack.Whirlwind();
			this.attackTree[28]=new Attack.Gust();
			this.attackTree[32]=new Attack.Psybeam();
	
			
		
		}
	}
	public static class Weedle extends Pokemon{
		public Weedle() {
			super(13, "Weedle", Types.BUG, Types.POISON, 8, 4, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=7;
		
			//Base Stats
			this._baseExp=52;
			this._expMult=1.0;
			
			this._baseHP=40;
			this._baseAtk=35;
			this._baseDef=30;
			this._baseSpAtk=20;
			this._baseSpDef=20;
			this._baseSpd=50;
			
			this.attackTree[1]=new Attack.PoisonSting();
			this.attackTree[2]=new Attack.StringShot();
			
		}
	}
	
	public static class Kakuna extends Pokemon{
		public Kakuna() {
			super(14, "Kakuna", Types.BUG, Types.POISON, 8, 5, 0, 0, 7, 120, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			this._evolLvl=10;
			
			//Base Stats
			this._baseExp=71;
			this._expMult=1.0;
			this._baseHP=45;
			this._baseAtk=25;
			this._baseDef=50;
			this._baseSpAtk=25;
			this._baseSpDef=25;
			this._baseSpd=35;
			
			this.attackTree[1]=new Attack.Harden();
			this.attackTree[8]=new Attack.Harden();
			
		}
	}
	
	public static class Beedrill extends Pokemon{
		public Beedrill() {
			//Currently GRASS type, thats wrong, but its okay for now.
			super(15, "Beedrill", Types.BUG, Types.POISON, 20, 7, 0, 0, 7, 45, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			//Base Stats
			this._baseExp=159;
			this._expMult=1.0;
			
			this._baseHP=65;
			this._baseAtk=80;
			this._baseDef=40;
			this._baseSpAtk=45;
			this._baseSpDef=80;
			this._baseSpd=75;
			
			this.attackTree[1]=new Attack.FuryAttack();
			this.attackTree[12]=new Attack.FuryAttack();
			this.attackTree[16]=new Attack.FocusEnergy();
			this.attackTree[20]=new Attack.Twineedle();
			this.attackTree[25]=new Attack.Rage();
			this.attackTree[30]=new Attack.PinMissile();
			this.attackTree[35]=new Attack.Agility();
			
		}
	}
	
	//START FROM HERE for stats and attacks.
	
	public static class Pidgey extends Pokemon{
		public Pidgey() {
			super(16, "Pidgey", Types.NORMAL, Types.FLYING, 12, 4, 0, 0, 7, 255,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=18;
			
			//Base Stats
			this._baseExp=55;
			this._expMult=1.2;
			
			this._baseHP=40;
			this._baseAtk=45;
			this._baseDef=40;
			this._baseSpAtk=35;
			this._baseSpDef=35;
			this._baseSpd=56;
			
			
			this.attackTree[1] = new Attack.Gust();
			this.attackTree[5] = new Attack.SandAttack();
			this.attackTree[12] = new Attack.QuickAttack();
			this.attackTree[19] = new Attack.Whirlwind();
			this.attackTree[28] = new Attack.WingAttack();
			this.attackTree[36] = new Attack.Agility();
			this.attackTree[44] = new Attack.MirrorMove();
		}
	}
	public static class Pidgeotto extends Pokemon{
		public Pidgeotto() {
			super(17, "Pidgeotto",  Types.NORMAL, Types.FLYING,12, 4, 0, 0, 7, 120, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
		
			this._evolLvl=36;
			//Base Stats
			this._baseExp=113;
			this._expMult=1.2;
			
			this._baseHP=63;
			this._baseAtk=60;
			this._baseDef=55;
			this._baseSpAtk=50;
			this._baseSpDef=50;
			this._baseSpd=71;
			
			this.attackTree[1] = new Attack.Gust();
			this.attackTree[2] = new Attack.SandAttack();
			this.attackTree[12] = new Attack.QuickAttack();
			this.attackTree[21] = new Attack.Whirlwind();
			this.attackTree[31] = new Attack.WingAttack();
			this.attackTree[40] = new Attack.Agility();
			this.attackTree[49] = new Attack.MirrorMove();
		}
	}
	public static class Pidgeot extends Pokemon{
		public Pidgeot() {
			super(18, "Pidgeot", Types.NORMAL,Types.FLYING, 12, 4, 0, 0, 7, 45, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			//Base Stats
			this._baseExp=172;
			this._expMult=1.2;
			
			this._baseHP=83;
			this._baseAtk=80;
			this._baseDef=75;
			this._baseSpAtk=70;
			this._baseSpDef=70;
			this._baseSpd=91;
			
			this.attackTree[1] = new Attack.Gust();
			this.attackTree[2] = new Attack.SandAttack();
			this.attackTree[3] = new Attack.QuickAttack();
			this.attackTree[21] = new Attack.Whirlwind();
			this.attackTree[31] = new Attack.WingAttack();
			this.attackTree[44] = new Attack.Agility();
			this.attackTree[54] = new Attack.MirrorMove();
		}
	}
	
	
	public static class Rattata extends Pokemon{
		public Rattata() {
			super(19, "Rattata", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			this._evolLvl=20;
			//Base Stats
			this._baseExp=57;
			this._expMult=1.0;
			
			this._baseHP=30;
			this._baseAtk=56;
			this._baseDef=35;
			this._baseSpAtk=25;
			this._baseSpDef=35;
			this._baseSpd=72;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[7] = new Attack.QuickAttack();
			this.attackTree[14] = new Attack.HyperFang();
			this.attackTree[23] = new Attack.FocusEnergy();
			this.attackTree[34] = new Attack.SuperFang();
		}
	}
	
	public static class Raticate extends Pokemon{
		public Raticate() {
			super(20, "Raticate", Types.NORMAL, Types.NONE,16, 8, 0, 0, 7, 127, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			//Base Stats
			this._baseExp=116;
			this._expMult=1.0;
			
			this._baseHP=51;
			this._baseAtk=81;
			this._baseDef=60;
			this._baseSpAtk=50;
			this._baseSpDef=70;
			this._baseSpd=97;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[3] = new Attack.QuickAttack();
			this.attackTree[14] = new Attack.HyperFang();
			this.attackTree[27] = new Attack.FocusEnergy();
			this.attackTree[41] = new Attack.SuperFang();

		}
	}
	
	public static class Spearow extends Pokemon{
		public Spearow() {
			super(21, "Spearow", Types.NORMAL, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=20;
			this._baseExp=58;
			this._expMult=1.0;
			
			this._baseHP=40;
			this._baseAtk=60;
			this._baseDef=30;
			this._baseSpAtk=31;
			this._baseSpDef=31;
			this._baseSpd=70;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[9] = new Attack.Leer();
			this.attackTree[15] = new Attack.FuryAttack();
			this.attackTree[22] = new Attack.MirrorMove();
			this.attackTree[29] = new Attack.DrillPeck();
			this.attackTree[36] = new Attack.Agility();
		}
	}
	
	public static class Fearow extends Pokemon{
		public Fearow() {
			super(22, "Fearow", Types.NORMAL, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			this._baseExp=162;
			this._expMult=1.0;
			
			this._baseHP=65;
			this._baseAtk=90;
			this._baseDef=65;
			this._baseSpAtk=61;
			this._baseSpDef=61;
			this._baseSpd=100;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[3] = new Attack.Leer();
			this.attackTree[15] = new Attack.FuryAttack();
			this.attackTree[25] = new Attack.MirrorMove();
			this.attackTree[34] = new Attack.DrillPeck();
			this.attackTree[43] = new Attack.Agility();
		}
	}
	
	public static class Ekans extends Pokemon{
		public Ekans() {
			super(23, "Ekans", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=22;
			this._baseExp=62;
			this._expMult=1.0;
			
			this._baseHP=35;
			this._baseAtk=60;
			this._baseDef=44;
			this._baseSpAtk=40;
			this._baseSpDef=54;
			this._baseSpd=55;
			
			this.attackTree[1] = new Attack.Wrap();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[10] = new Attack.PoisonSting();
			this.attackTree[17] = new Attack.FuryAttack();
			this.attackTree[24] = new Attack.Glare();
			this.attackTree[31] = new Attack.Screech();
			this.attackTree[38] = new Attack.Acid();
		}
	}

	public static class Arbok extends Pokemon{
		public Arbok() {
			super(24, "Arbok", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			this._baseExp=147;
			this._expMult=1.0;
			
			this._baseHP=60;
			this._baseAtk=85;
			this._baseDef=69;
			this._baseSpAtk=65;
			this._baseSpDef=79;
			this._baseSpd=80;
			
			this.attackTree[1] = new Attack.Wrap();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[3] = new Attack.PoisonSting();
			this.attackTree[17] = new Attack.FuryAttack();
			this.attackTree[27] = new Attack.Glare();
			this.attackTree[36] = new Attack.Screech();
			this.attackTree[47] = new Attack.Acid();
		}
	}
	
	public static class Pikachu extends Pokemon{
		public Pikachu(){
			super(25, "Pikachu", Types.ELECTRIC, Types.NONE,15, 7, 0, 0, 7, 190, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=82;
			this._expMult=1.0;
			
			this._baseHP=35;
			this._baseAtk=55;
			this._baseDef=30;
			this._baseSpAtk=50;
			this._baseSpDef=40;
			this._baseSpd=90;
			
			this.attackTree[1] = new Attack.ThunderShock();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[6] = new Attack.TailWhip();
			this.attackTree[8] = new Attack.ThunderWave();
			this.attackTree[9] = new Attack.QuickAttack();
			this.attackTree[15] = new Attack.DoubleTeam();
			this.attackTree[20] = new Attack.Swift();
			this.attackTree[26] = new Attack.Thunderbolt();
			this.attackTree[33] = new Attack.Agility();
			this.attackTree[41] = new Attack.Thunder();
			this.attackTree[50] = new Attack.LightScreen();
			
		}
			
	}
	
	public static class Raichu extends Pokemon{
		public Raichu() {
			super(26, "Raichu", Types.ELECTRIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=122;
			this._expMult=1.0;
			
			this._baseHP=35;
			this._baseAtk=55;
			this._baseDef=30;
			this._baseSpAtk=50;
			this._baseSpDef=40;
			this._baseSpd=90;
			
			this.attackTree[1] = new Attack.ThunderShock();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[3] = new Attack.ThunderWave();

		}
	}

	public static class Sandshrew extends Pokemon{
		public Sandshrew() {
			super(27, "Sandshrew", Types.GROUND, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=22;
			//Base Stats
			this._baseExp=93;
			this._expMult=1.0;
			
			this._baseHP=50;
			this._baseAtk=75;
			this._baseDef=85;
			this._baseSpAtk=20;
			this._baseSpDef=30;
			this._baseSpd=40;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[10] = new Attack.SandAttack();
			this.attackTree[17] = new Attack.Slash();
			this.attackTree[24] = new Attack.PoisonSting();
			this.attackTree[31] = new Attack.Swift();
			this.attackTree[38] = new Attack.FurySwipes();
		}
	}
	
	public static class Sandslash extends Pokemon{
		public Sandslash() {
			super(28, "Sandslash", Types.GROUND, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=163;
			this._expMult=1.0;
			
			this._baseHP=75;
			this._baseAtk=100;
			this._baseDef=110;
			this._baseSpAtk=45;
			this._baseSpDef=55;
			this._baseSpd=65;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.SandAttack();
			this.attackTree[3] = new Attack.Slash();
			this.attackTree[27] = new Attack.PoisonSting();
			this.attackTree[36] = new Attack.Swift();
			this.attackTree[47] = new Attack.FurySwipes();
		}
	}
	
	public static class Nidoran_F extends Pokemon{
		public Nidoran_F() {
			super(29, "Nidoran \u2640", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=16;
			this._baseExp=59;
			this._expMult=1.0;
			
			this._baseHP=55;
			this._baseAtk=47;
			this._baseDef=52;
			this._baseSpAtk=40;
			this._baseSpDef=40;
			this._baseSpd=41;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Growl();
			this.attackTree[8]=new Attack.Scratch();
			this.attackTree[12]=new Attack.DoubleKick();
			this.attackTree[14]=new Attack.PoisonSting();
			this.attackTree[21]=new Attack.TailWhip();
			this.attackTree[29]=new Attack.Bite();
			this.attackTree[36]=new Attack.FurySwipes();
		}
	}
	
	public static class Nidorina extends Pokemon{
		public Nidorina() {
			super(30, "Nidorina", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			this._baseExp=117;
			this._expMult=1.0;
			this._baseHP=70;
			this._baseAtk=62;
			this._baseDef=67;
			this._baseSpAtk=55;
			this._baseSpDef=55;
			this._baseSpd=56;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Growl();
			this.attackTree[8]=new Attack.Scratch();
			this.attackTree[12]=new Attack.DoubleKick();
			this.attackTree[14]=new Attack.PoisonSting();
			this.attackTree[23]=new Attack.TailWhip();
			this.attackTree[32]=new Attack.Bite();
			this.attackTree[41]=new Attack.FurySwipes();
		}
	}
	
	public static class Nidoqueen extends Pokemon{
		public Nidoqueen() {
			super(31, "Nidoqueen", Types.POISON, Types.GROUND,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			this._baseExp=194;
			this._expMult=1.0;
			
			this._baseHP=90;
			this._baseAtk=82;
			this._baseDef=87;
			this._baseSpAtk=75;
			this._baseSpDef=85;
			this._baseSpd=76;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.TailWhip();
			this.attackTree[3]=new Attack.Scratch();
			this.attackTree[4]=new Attack.DoubleKick();
			this.attackTree[14]=new Attack.PoisonSting();
			this.attackTree[23]=new Attack.BodySlam();
		}
	}

	public static class Nidoran_M extends Pokemon{
		public Nidoran_M() {
			super(32, "Nidoran \u2642", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=16;
			this._baseExp=60;
			this._expMult=1.0;
			this._baseHP=46;
			this._baseAtk=57;
			this._baseDef=50;
			this._baseSpAtk=40;
			this._baseSpDef=40;
			this._baseSpd=50;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Leer();
			this.attackTree[8]=new Attack.HornAttack();
			this.attackTree[12]=new Attack.DoubleKick();
			this.attackTree[14]=new Attack.PoisonSting();
			this.attackTree[23]=new Attack.FocusEnergy();
			this.attackTree[29]=new Attack.FuryAttack();
			this.attackTree[36]=new Attack.HornDrill();
		}
	}
	
	public static class Nidorino extends Pokemon{
		public Nidorino() {
			super(33, "Nidorino", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			this._baseExp=118;
			this._expMult=1.0;
			
			this._baseHP=61;
			this._baseAtk=72;
			this._baseDef=57;
			this._baseSpAtk=55;
			this._baseSpDef=55;
			this._baseSpd=65;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Leer();
			this.attackTree[8]=new Attack.HornAttack();
			this.attackTree[12]=new Attack.DoubleKick();
			this.attackTree[14]=new Attack.PoisonSting();
			this.attackTree[23]=new Attack.FocusEnergy();
			this.attackTree[32]=new Attack.FuryAttack();
			this.attackTree[41]=new Attack.HornDrill();
		}
	}
	
	public static class Nidoking extends Pokemon{
		public Nidoking() {
			super(34, "Nidoking", Types.POISON, Types.GROUND,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			this._baseExp=195;
			this._expMult=1.0;
			
			this._baseHP=81;
			this._baseAtk=92;
			this._baseDef=77;
			this._baseSpAtk=85;
			this._baseSpDef=75;
			this._baseSpd=85;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.HornAttack();
			this.attackTree[3]=new Attack.DoubleKick();
			this.attackTree[4]=new Attack.PoisonSting();
			this.attackTree[23]=new Attack.Thrash();
		}
	}
	
	public static class Clefairy extends Pokemon{
		public Clefairy() {
			super(35, "Clefairy", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			//Base Stats
			this._baseExp=68;
			
			this._baseHP=70;
			this._baseAtk=45;
			this._baseDef=48;
			this._baseSpAtk=60;
			this._baseSpDef=65;
			this._baseSpd=35;
			
			this._expMult=1.25;
			
			this.attackTree[1] = new Attack.Pound();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[13] = new Attack.Sing();
			this.attackTree[18] = new Attack.DoubleSlap();
			this.attackTree[24] = new Attack.Minimize();
			this.attackTree[31] = new Attack.Metronome();
			this.attackTree[39] = new Attack.DefenseCurl();
			this.attackTree[48] = new Attack.LightScreen();
		}
	}
	
	public static class Clefable extends Pokemon{
		public Clefable() {
			super(36, "Clefable", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=129;
			
			this._baseHP=95;
			this._baseAtk=70;
			this._baseDef=73;
			this._baseSpAtk=85;
			this._baseSpDef=90;
			this._baseSpd=60;
			
			this._expMult=1.25;

			this.attackTree[1] = new Attack.Minimize();
			this.attackTree[2] = new Attack.DoubleSlap();
			this.attackTree[3] = new Attack.Sing();
			this.attackTree[4] = new Attack.Metronome();
		}
	}
	
	public static class Vulpix extends Pokemon{
		public Vulpix() {
			super(37, "Vulpix", Types.FIRE, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			//Base Stats
			this._baseExp=63;
			
			this._baseHP=38;
			this._baseAtk=41;
			this._baseDef=40;
			this._baseSpAtk=50;
			this._baseSpDef=65;
			this._baseSpd=65;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Ember();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[16] = new Attack.QuickAttack();
			this.attackTree[21] = new Attack.Roar();
			this.attackTree[28] = new Attack.ConfuseRay();
			this.attackTree[35] = new Attack.Flamethrower();
			this.attackTree[42] = new Attack.FireSpin();
		}
	}
	
	public static class Ninetales extends Pokemon{
		public Ninetales() {
			super(38, "Ninetales", Types.FIRE, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=178;
			
			this._baseHP=73;
			this._baseAtk=76;
			this._baseDef=75;
			this._baseSpAtk=81;
			this._baseSpDef=100;
			this._baseSpd=100;
			
			this._expMult=1.25;
			
			this.attackTree[1] = new Attack.Ember();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[3] = new Attack.QuickAttack();
			this.attackTree[4] = new Attack.Roar();
		}
	}

	public static class Jigglypuff extends Pokemon{
		public Jigglypuff() {
			super(39, "Jigglypuff", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			//Base Stats
			this._baseExp=76;
			
			this._baseHP=115;
			this._baseAtk=45;
			this._baseDef=20;
			this._baseSpAtk=45;
			this._baseSpDef=25;
			this._baseSpd=20;
			
			this._expMult=1.25;
			
			this.attackTree[1] = new Attack.Sing();
			this.attackTree[9] = new Attack.Pound();
			this.attackTree[14] = new Attack.Disable();
			this.attackTree[19] = new Attack.DefenseCurl();
			this.attackTree[24] = new Attack.DoubleSlap();
			this.attackTree[29] = new Attack.Rest();
			this.attackTree[34] = new Attack.BodySlam();
			this.attackTree[39] = new Attack.DoubleEdge();
		}
	}
	
	public static class Wigglytuff extends Pokemon{
		public Wigglytuff() {
			super(40, "Wigglytuff", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=109;
			
			this._baseHP=140;
			this._baseAtk=70;
			this._baseDef=45;
			this._baseSpAtk=75;
			this._baseSpDef=50;
			this._baseSpd=45;
			
			this._expMult=1.25;
			
			this.attackTree[1] = new Attack.Sing();
			this.attackTree[2] = new Attack.Disable();
			this.attackTree[3] = new Attack.DefenseCurl();
			this.attackTree[4] = new Attack.DoubleSlap();
		}
	}
	
	public static class Zubat extends Pokemon{
		public Zubat() {
			super(41, "Zubat", Types.POISON, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=22;
			
			//Base Stats
			this._baseExp=54;
			
			this._baseHP=40;
			this._baseAtk=45;
			this._baseDef=35;
			this._baseSpAtk=30;
			this._baseSpDef=40;
			this._baseSpd=55;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.LeechLife();
			this.attackTree[10] = new Attack.Supersonic();
			this.attackTree[15] = new Attack.Bite();
			this.attackTree[21] = new Attack.ConfuseRay();
			this.attackTree[28] = new Attack.WingAttack();
			this.attackTree[36] = new Attack.Haze();
		}
	}
	
	public static class Golbat extends Pokemon{
		public Golbat() {
			super(42, "Golbat", Types.POISON, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=171;
			
			this._baseHP=75;
			this._baseAtk=80;
			this._baseDef=70;
			this._baseSpAtk=65;
			this._baseSpDef=75;
			this._baseSpd=90;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Screech();
			this.attackTree[2] = new Attack.LeechLife();
			this.attackTree[10] = new Attack.Supersonic();
			this.attackTree[15] = new Attack.Bite();
			this.attackTree[21] = new Attack.ConfuseRay();
			this.attackTree[32] = new Attack.WingAttack();
			this.attackTree[43] = new Attack.Haze();
		}
	}
	
	public static class Oddish extends Pokemon{
		public Oddish() {
			super(43, "Oddish", Types.GRASS, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=21;
			this._baseExp=78;
			this._expMult=1.2;
			
			this._baseHP=45;
			this._baseAtk=50;
			this._baseDef=55;
			this._baseSpAtk=75;
			this._baseSpDef=65;
			this._baseSpd=30;
			
			this.attackTree[1]=new Attack.Absorb();
			this.attackTree[15]=new Attack.PoisonPowder();
			this.attackTree[17]=new Attack.StunSpore();
			this.attackTree[19]=new Attack.SleepPowder();
			this.attackTree[24]=new Attack.Acid();
			this.attackTree[33]=new Attack.PetalDance();
			this.attackTree[46]=new Attack.SolarBeam();
			
		}
	}
	
	public static class Gloom extends Pokemon{
		public Gloom() {
			super(44, "Gloom", Types.GRASS, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			this._baseExp=132;
			this._expMult=1.2;
			
			this._baseHP=60;
			this._baseAtk=65;
			this._baseDef=70;
			this._baseSpAtk=85;
			this._baseSpDef=75;
			this._baseSpd=40;
			
			this.attackTree[1]=new Attack.Absorb();
			this.attackTree[2]=new Attack.PoisonPowder();
			this.attackTree[3]=new Attack.StunSpore();
			this.attackTree[4]=new Attack.SleepPowder();
			this.attackTree[28]=new Attack.Acid();
			this.attackTree[38]=new Attack.PetalDance();
			this.attackTree[52]=new Attack.SolarBeam();
		}
	}
	
	public static class Vileplume extends Pokemon{
		public Vileplume() {
			super(45, "Vileplume", Types.GRASS, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			this._baseExp=184;
			this._expMult=1.2;
			
			this._baseHP=75;
			this._baseAtk=80;
			this._baseDef=85;
			this._baseSpAtk=100;
			this._baseSpDef=90;
			this._baseSpd=50;
			
			this.attackTree[1]=new Attack.PoisonPowder();
			this.attackTree[2]=new Attack.StunSpore();
			this.attackTree[3]=new Attack.SleepPowder();
			this.attackTree[15] = new Attack.PoisonPowder();
			this.attackTree[17] = new Attack.StunSpore();
			this.attackTree[19] = new Attack.SleepPowder();

		}
	}
	
	public static class Paras extends Pokemon{
		public Paras() {
			super(46, "Paras", Types.BUG, Types.GRASS,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=24;
			
			//Base Stats
			this._baseExp=70;
			
			this._baseHP=35;
			this._baseAtk=70;
			this._baseDef=55;
			this._baseSpAtk=45;
			this._baseSpDef=55;
			this._baseSpd=25;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[13] = new Attack.StunSpore();
			this.attackTree[20] = new Attack.LeechLife();
			this.attackTree[27] = new Attack.Spore();
			this.attackTree[34] = new Attack.Slash();
			this.attackTree[41] = new Attack.Growth();
		}
	}
	
	public static class Parasect extends Pokemon{
		public Parasect() {
			super(47, "Parasect", Types.BUG, Types.GRASS,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=128;
			
			this._baseHP=60;
			this._baseAtk=95;
			this._baseDef=80;
			this._baseSpAtk=60;
			this._baseSpDef=80;
			this._baseSpd=30;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.StunSpore();
			this.attackTree[3] = new Attack.LeechLife();
			this.attackTree[13] = new Attack.StunSpore();
			this.attackTree[20] = new Attack.LeechLife();
			this.attackTree[30] = new Attack.Spore();
			this.attackTree[39] = new Attack.Slash();
			this.attackTree[48] = new Attack.Growth();
		}
	}
	
	public static class Venonat extends Pokemon{
		public Venonat() {
			super(48, "Venonat", Types.BUG, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=31;
			this._baseExp=75;
			this._expMult=1.0;
			
			this._baseHP=60;
			this._baseAtk=55;
			this._baseDef=50;
			this._baseSpAtk=40;
			this._baseSpDef=55;
			this._baseSpd=45;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Disable();
			this.attackTree[11]=new Attack.Supersonic();
			this.attackTree[19]=new Attack.Confusion();
			this.attackTree[22]=new Attack.PoisonPowder();
			this.attackTree[27]=new Attack.LeechLife();
			this.attackTree[30]=new Attack.StunSpore();
			this.attackTree[35]=new Attack.Psybeam();
			this.attackTree[38]=new Attack.SleepPowder();
			this.attackTree[43]=new Attack.Psychic();
		}
	}
	
	public static class Venomoth extends Pokemon{
		public Venomoth() {
			super(49, "Venomoth", Types.BUG, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			this._baseExp=138;
			this._expMult=1.0;
			
			this._baseHP=70;
			this._baseAtk=65;
			this._baseDef=60;
			this._baseSpAtk=90;
			this._baseSpDef=75;
			this._baseSpd=90;
			
			this.attackTree[1]=new Attack.Tackle();
			this.attackTree[2]=new Attack.Disable();
			this.attackTree[3]=new Attack.Supersonic();
			this.attackTree[4]=new Attack.Confusion();
			this.attackTree[22]=new Attack.PoisonPowder();
			this.attackTree[27]=new Attack.LeechLife();
			this.attackTree[30]=new Attack.StunSpore();
			this.attackTree[38]=new Attack.Psybeam();
			this.attackTree[43]=new Attack.SleepPowder();
			this.attackTree[50]=new Attack.Psychic();

		}
	}
	
	public static class Diglett extends Pokemon{
		public Diglett() {
			super(50, "Diglett", Types.GROUND, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=26;
			
			//Base Stats
			this._baseExp=81;
			
			this._baseHP=10;
			this._baseAtk=55;
			this._baseDef=25;
			this._baseSpAtk=35;
			this._baseSpDef=45;
			this._baseSpd=95;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch(); //HOW DOES DIGLETT USE SCRATCH?!?
			this.attackTree[15] = new Attack.Growl();
			this.attackTree[19] = new Attack.Dig();
			this.attackTree[24] = new Attack.SandAttack();
			this.attackTree[31] = new Attack.Slash();
			this.attackTree[40] = new Attack.Earthquake();
		}
	}

	public static class Dugtrio extends Pokemon{
		public Dugtrio() {
			super(51, "Dugtrio", Types.GROUND, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=153;
			
			this._baseHP=35;
			this._baseAtk=80;
			this._baseDef=50;
			this._baseSpAtk=50;
			this._baseSpDef=70;
			this._baseSpd=120;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[3] = new Attack.Dig();
			this.attackTree[4] = new Attack.SandAttack();
			this.attackTree[15] = new Attack.Growl();
			this.attackTree[19] = new Attack.Dig();
			this.attackTree[24] = new Attack.SandAttack();
			this.attackTree[35] = new Attack.Slash();
			this.attackTree[47] = new Attack.Earthquake();
		}
	}

	public static class Meowth extends Pokemon{
		public Meowth() {
			super(52, "Meowth", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=28;
			
			//Base Stats
			this._baseExp=69;
			
			this._baseHP=40;
			this._baseAtk=45;
			this._baseDef=35;
			this._baseSpAtk=40;
			this._baseSpDef=40;
			this._baseSpd=90;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[12] = new Attack.Bite();
			this.attackTree[17] = new Attack.PayDay();
			this.attackTree[24] = new Attack.Screech();
			this.attackTree[33] = new Attack.FurySwipes();
			this.attackTree[44] = new Attack.Slash();
		}
	}
	
	public static class Persian extends Pokemon{
		public Persian() {
			super(53, "Persian", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=154;
			
			this._baseHP=65;
			this._baseAtk=70;
			this._baseDef=60;
			this._baseSpAtk=65;
			this._baseSpDef=65;
			this._baseSpd=150;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[3] = new Attack.Bite();
			this.attackTree[12] = new Attack.Bite();
			this.attackTree[17] = new Attack.PayDay();
			this.attackTree[24] = new Attack.Screech();
			this.attackTree[37] = new Attack.FurySwipes();
			this.attackTree[51] = new Attack.Slash();
		}
	}
	
	public static class Psyduck extends Pokemon{
		public Psyduck() {
			super(54, "Psyduck", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=33;
			
			//Base Stats
			this._baseExp=64;
			
			this._baseHP=50;
			this._baseAtk=52;
			this._baseDef=48;
			this._baseSpAtk=65;
			this._baseSpDef=50;
			this._baseSpd=55;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[28] = new Attack.TailWhip();
			this.attackTree[31] = new Attack.Disable();
			this.attackTree[36] = new Attack.Confusion();
			this.attackTree[43] = new Attack.FurySwipes();
			this.attackTree[52] = new Attack.HydroPump();
		}
	}
	
	public static class Golduck extends Pokemon{
		public Golduck() {
			super(55, "Golduck", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=175;
			
			this._baseHP=80;
			this._baseAtk=82;
			this._baseDef=78;
			this._baseSpAtk=95;
			this._baseSpDef=80;
			this._baseSpd=85;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[28] = new Attack.TailWhip();
			this.attackTree[31] = new Attack.Disable();
			this.attackTree[39] = new Attack.Confusion();
			this.attackTree[48] = new Attack.FurySwipes();
			this.attackTree[59] = new Attack.HydroPump();
		}
	}
	
	public static class Mankey extends Pokemon{
		public Mankey() {
			super(56, "Mankey", Types.FIGHTING, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=28;
			
			//Base Stats
			this._baseExp=61;
			
			this._baseHP=40;
			this._baseAtk=80;
			this._baseDef=35;
			this._baseSpAtk=35;
			this._baseSpDef=45;
			this._baseSpd=70;
			
			this._expMult=1.0;
			
			this.attackTree[1]=new Attack.Scratch();
			this.attackTree[2]=new Attack.Leer();
			this.attackTree[9]=new Attack.LowKick();
			this.attackTree[15]=new Attack.KarateChop();
			this.attackTree[21]=new Attack.FurySwipes();
			this.attackTree[27]=new Attack.FocusEnergy();
			this.attackTree[33]=new Attack.SeismicToss();
			this.attackTree[39]=new Attack.Thrash();
			this.attackTree[45]=new Attack.Screech();
		}
	}
	
	public static class Primeape extends Pokemon{
		public Primeape() {
			super(57, "Primate", Types.FIGHTING, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=159;
			
			this._baseHP=65;
			this._baseAtk=105;
			this._baseDef=60;
			this._baseSpAtk=60;
			this._baseSpDef=70;
			this._baseSpd=95;
			
			this._expMult=1.0;
			
			this.attackTree[1]=new Attack.Scratch();
//			this.attackTree[2]=new Attack.Leer();
//			this.attackTree[3]=new Attack.LowKick();
//			this.attackTree[9] = new Attack.LowKick();
//			this.attackTree[15]=new Attack.KarateChop();
//			this.attackTree[21]=new Attack.FurySwipes();
//			this.attackTree[27]=new Attack.FocusEnergy();
			this.attackTree[28]=new Attack.Rage();
//			this.attackTree[37]=new Attack.SeismicToss();
//			this.attackTree[45]=new Attack.Screech();
//			this.attackTree[46]=new Attack.Thrash();

		}
	}
	
	public static class Growlithe extends Pokemon{
		public Growlithe() {
			super(58, "Growlithe", Types.FIRE, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			//Base Stats
			this._baseExp=70;
			
			this._baseHP=55;
			this._baseAtk=70;
			this._baseDef=45;
			this._baseSpAtk=70;
			this._baseSpDef=50;
			this._baseSpd=60;
			
			this._expMult= 0.8;
			
			
			this.attackTree[1] = new Attack.Bite();
			this.attackTree[2] = new Attack.Roar();
			this.attackTree[18] = new Attack.Ember();
			this.attackTree[23] = new Attack.Leer();
			this.attackTree[30] = new Attack.TakeDown();
			this.attackTree[39] = new Attack.Agility();
			this.attackTree[50] = new Attack.Flamethrower();
		}
	}
	
	public static class Arcanine extends Pokemon{
		public Arcanine() {
			super(59, "Arcanine", Types.FIRE, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=194;
			
			this._baseHP=90;
			this._baseAtk=110;
			this._baseDef=80;
			this._baseSpAtk=100;
			this._baseSpDef=80;
			this._baseSpd=95;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Roar();
			this.attackTree[2] = new Attack.Ember();
			this.attackTree[3] = new Attack.Leer();
			this.attackTree[4] = new Attack.TakeDown();
		}
	}
	
	public static class Poliwag extends Pokemon{
		public Poliwag() {
			super(60, "Poliwag", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=25;
			
			
			//Base Stats
			this._baseExp=60;
			
			this._baseHP=40;
			this._baseAtk=50;
			this._baseDef=40;
			this._baseSpAtk=40;
			this._baseSpDef=40;
			this._baseSpd=90;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Bubble();
			this.attackTree[16] = new Attack.Hypnosis();
			this.attackTree[19] = new Attack.WaterGun();
			this.attackTree[25] = new Attack.DoubleSlap();
			this.attackTree[31] = new Attack.BodySlam();
			this.attackTree[38] = new Attack.Amnesia();
			this.attackTree[45] = new Attack.HydroPump();
			
			
		}
	}
	
	public static class Poliwhirl extends Pokemon{
		public Poliwhirl() {
			super(61, "Poliwhirl", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			//Base Stats
			this._baseExp=135;
			
			this._baseHP=65;
			this._baseAtk=65;
			this._baseDef=65;
			this._baseSpAtk=50;
			this._baseSpDef=50;
			this._baseSpd=90;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Bubble();
			this.attackTree[2] = new Attack.Hypnosis();
			this.attackTree[3] = new Attack.WaterGun();
			this.attackTree[7] = new Attack.Hypnosis();
			this.attackTree[13] = new Attack.WaterGun();
			this.attackTree[26] = new Attack.DoubleSlap();
			this.attackTree[33] = new Attack.BodySlam();
			this.attackTree[41] = new Attack.Amnesia();
			this.attackTree[49] = new Attack.HydroPump();
		}
	}
	
	public static class Poliwrath extends Pokemon{
		public Poliwrath() {
			super(62, "Poliwrath", Types.WATER, Types.FIGHTING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=225;
			
			this._baseHP=90;
			this._baseAtk=85;
			this._baseDef=95;
			this._baseSpAtk=70;
			this._baseSpDef=90;
			this._baseSpd=70;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.DoubleSlap();
			this.attackTree[2] = new Attack.BodySlam();
			this.attackTree[16] = new Attack.Hypnosis();
			this.attackTree[19] = new Attack.WaterGun();
		}
	}
	
	public static class Abra extends Pokemon{
		public Abra() {
			super(63, "Abra", Types.PSYCHIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=16;
			
			//Base Stats
			this._baseExp=62;
			
			this._baseHP=25;
			this._baseAtk=20;
			this._baseDef=15;
			this._baseSpAtk=105;
			this._baseSpDef=55;
			this._baseSpd=90;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Teleport();
		}
	}
	
	public static class Kadabra extends Pokemon{
		public Kadabra() {
			super(64, "Kadabra", Types.PSYCHIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=43;
			
			//Base Stats
			this._baseExp=140;
			
			this._baseHP=40;
			this._baseAtk=35;
			this._baseDef=30;
			this._baseSpAtk=120;
			this._baseSpDef=70;
			this._baseSpd=105;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Kinesis();
			this.attackTree[2] = new Attack.Teleport();
			this.attackTree[16] = new Attack.Confusion();
			this.attackTree[20] = new Attack.Disable();
			this.attackTree[27] = new Attack.Psybeam();
			this.attackTree[31] = new Attack.Recover();
			this.attackTree[38] = new Attack.Psychic();
			this.attackTree[42] = new Attack.Reflect();
		}
	}
	
	public static class Alakazam extends Pokemon{
		public Alakazam() {
			super(65, "Alakazam", Types.PSYCHIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=221;
			
			this._baseHP=55;
			this._baseAtk=50;
			this._baseDef=45;
			this._baseSpAtk=135;
			this._baseSpDef=85;
			this._baseSpd=120;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Teleport();
			this.attackTree[16] = new Attack.Confusion();
			this.attackTree[20] = new Attack.Disable();
			this.attackTree[27] = new Attack.Psybeam();
			this.attackTree[31] = new Attack.Recover();
			this.attackTree[38] = new Attack.Psychic();
			this.attackTree[42] = new Attack.Reflect();
		}
	}
	
	public static class Machop extends Pokemon{
		public Machop() {
			super(66, "Machop", Types.FIGHTING, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=28;
			
			//Base Stats
			this._baseExp=61;
			
			this._baseHP=70;
			this._baseAtk=80;
			this._baseDef=50;
			this._baseSpAtk=35;
			this._baseSpDef=35;
			this._baseSpd=35;
			
			this._expMult=1.2;
			
			this.attackTree[1]=new Attack.KarateChop();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[20]=new Attack.LowKick();
			//this.attackTree[25]=new Attack.Leer();
			this.attackTree[32]=new Attack.FocusEnergy();
			this.attackTree[39]=new Attack.SeismicToss();
			this.attackTree[46]=new Attack.Submission();
		}
	}
	
	public static class Machoke extends Pokemon{
		public Machoke() {
			super(67, "Machoke", Types.FIGHTING, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=53;
			
			//Base Stats
			this._baseExp=142;
			
			this._baseHP=80;
			this._baseAtk=100;
			this._baseDef=70;
			this._baseSpAtk=50;
			this._baseSpDef=60;
			this._baseSpd=45;
			
			this._expMult=1.2;
			
			this.attackTree[1]=new Attack.KarateChop();
			this.attackTree[2]=new Attack.LowKick();
			this.attackTree[3]=new Attack.Leer();
			this.attackTree[20] = new Attack.Leer();
			this.attackTree[36]=new Attack.FocusEnergy();
			this.attackTree[44]=new Attack.SeismicToss();
			this.attackTree[52]=new Attack.Submission();

		}
	}
	
	public static class Machamp extends Pokemon{
		public Machamp() {
			super(68, "Machamp", Types.FIGHTING, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=227;
			
			this._baseHP=90;
			this._baseAtk=130;
			this._baseDef=80;
			this._baseSpAtk=65;
			this._baseSpDef=85;
			this._baseSpd=55;
			
			this._expMult=1.2;
			
			this.attackTree[1]=new Attack.KarateChop();
			this.attackTree[2]=new Attack.LowKick();
			this.attackTree[3]=new Attack.Leer();
			this.attackTree[20] = new Attack.Leer();
			this.attackTree[36]=new Attack.FocusEnergy();
			this.attackTree[44]=new Attack.SeismicToss();
			this.attackTree[52]=new Attack.Submission();


		}
	}
	
	public static class Bellsprout extends Pokemon{
		public Bellsprout() {
			super(69, "Bellsprout", Types.GRASS, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			//Base Stats
			this._baseExp=60;
			
			this._baseHP=50;
			this._baseAtk=75;
			this._baseDef=35;
			this._baseSpAtk=70;
			this._baseSpDef=30;
			this._baseSpd=40;
			
			this._expMult=1.2;
			
			this.attackTree[1]=new Attack.VineWhip();
			this.attackTree[2]=new Attack.Growth();
			this.attackTree[13]=new Attack.Wrap();
			this.attackTree[15]=new Attack.PoisonPowder();
			this.attackTree[18]=new Attack.SleepPowder();
			this.attackTree[21]=new Attack.StunSpore();
			this.attackTree[26]=new Attack.Acid();
			this.attackTree[33]=new Attack.RazorLeaf();
			this.attackTree[42]=new Attack.Slam();
		}
	}
	
	public static class Weepinbell extends Pokemon{
		public Weepinbell() {
			super(70, "Weepinbell", Types.GRASS, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			//Base Stats
			this._baseExp=137;
			
			this._baseHP=65;
			this._baseAtk=90;
			this._baseDef=50;
			this._baseSpAtk=85;
			this._baseSpDef=45;
			this._baseSpd=55;
			
			this._expMult=1.2;
			
			this.attackTree[1]=new Attack.VineWhip();
			this.attackTree[2]=new Attack.Growth();
			this.attackTree[3]=new Attack.Wrap();
			this.attackTree[4]=new Attack.PoisonPowder();
			this.attackTree[13]= new Attack.Wrap();
			this.attackTree[18]=new Attack.SleepPowder();
			this.attackTree[23]=new Attack.StunSpore();
			this.attackTree[29]=new Attack.Acid();
			this.attackTree[38]=new Attack.RazorLeaf();
			this.attackTree[49]=new Attack.Slam();
		}
	}
	
	public static class Victreebel extends Pokemon{
		public Victreebel() {
			super(71, "Victreebel", Types.GRASS, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=216;
			
			this._baseHP=80;
			this._baseAtk=105;
			this._baseDef=65;
			this._baseSpAtk=100;
			this._baseSpDef=60;
			this._baseSpd=70;
			
			this._expMult=1.2;
			
			this.attackTree[13]=new Attack.Wrap();
			this.attackTree[15]=new Attack.PoisonPowder();
			this.attackTree[18]=new Attack.SleepPowder();
		}
	}
	
	public static class Tentacool extends Pokemon{
		public Tentacool() {
			super(72, "Tentacool", Types.WATER, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=30;
			
			//Base Stats
			this._baseExp=67;
			
			this._baseHP=40;
			this._baseAtk=40;
			this._baseDef=35;
			this._baseSpAtk=50;
			this._baseSpDef=100;
			this._baseSpd=70;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Acid();
			this.attackTree[7] = new Attack.Supersonic();
			this.attackTree[13] = new Attack.Wrap();
			this.attackTree[18] = new Attack.PoisonSting();
			this.attackTree[22] = new Attack.WaterGun();
			this.attackTree[27] = new Attack.Constrict();
			this.attackTree[33] = new Attack.Barrier();
			this.attackTree[40] = new Attack.Screech();
			this.attackTree[48] = new Attack.HydroPump();
 		}
	}
	
	public static class Tentacruel extends Pokemon{
		public Tentacruel() {
			super(73, "Tentacruel", Types.WATER, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=180;
			
			this._baseHP=80;
			this._baseAtk=70;
			this._baseDef=65;
			this._baseSpAtk=80;
			this._baseSpDef=120;
			this._baseSpd=100;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Acid();
			this.attackTree[2] = new Attack.Supersonic();
			this.attackTree[3] = new Attack.Wrap();
			this.attackTree[4] = new Attack.PoisonSting();
			this.attackTree[7] = new Attack.Supersonic();
			this.attackTree[13] = new Attack.Wrap();
			this.attackTree[18] = new Attack.PoisonSting();
			this.attackTree[22] = new Attack.WaterGun();
			this.attackTree[27] = new Attack.Constrict();
			this.attackTree[35] = new Attack.Barrier();
			this.attackTree[43] = new Attack.Screech();
			this.attackTree[50] = new Attack.HydroPump();
		}
	}
	
	public static class Geodude extends Pokemon{
		public Geodude() {
			super(74, "Geodude", Types.ROCK, Types.GROUND,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=25;
			
			//Base Stats
			this._baseExp=73;
			
			this._baseHP=40;
			this._baseAtk=80;
			this._baseDef=100;
			this._baseSpAtk=30;
			this._baseSpDef=30;
			this._baseSpd=20;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[11] = new Attack.DefenseCurl();
			this.attackTree[16] = new Attack.RockThrow();
			this.attackTree[21] = new Attack.Selfdestruct();
			this.attackTree[26] = new Attack.Harden();
			this.attackTree[31] = new Attack.Earthquake();
			this.attackTree[36] = new Attack.Explosion();
		}
	}
	
	public static class Graveler extends Pokemon{
		public Graveler() {
			super(75, "Graveler", Types.ROCK, Types.GROUND,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._evolLvl=44;
			
			//Base Stats
			this._baseExp=134;
			
			this._baseHP=55;
			this._baseAtk=95;
			this._baseDef=115;
			this._baseSpAtk=45;
			this._baseSpDef=45;
			this._baseSpd=35;
			
			this._expMult=1.2;
			
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.DefenseCurl();
			this.attackTree[3] = new Attack.RockThrow();
			this.attackTree[4] = new Attack.Selfdestruct();
			this.attackTree[11] = new Attack.DefenseCurl();
			this.attackTree[16] = new Attack.RockThrow();
			this.attackTree[21] = new Attack.Selfdestruct();
			this.attackTree[29] = new Attack.Harden();
			this.attackTree[36] = new Attack.Earthquake();
			this.attackTree[43] = new Attack.Explosion();
		}
	}
	
	public static class Golem extends Pokemon{
		public Golem() {
			super(76, "Golem", Types.ROCK, Types.GROUND,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=218;
			
			this._baseHP=80;
			this._baseAtk=110;
			this._baseDef=130;
			this._baseSpAtk=55;
			this._baseSpDef=65;
			this._baseSpd=45;
			
			this._expMult=1.2;
			
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.DefenseCurl();
			this.attackTree[3] = new Attack.RockThrow();
			this.attackTree[4] = new Attack.Selfdestruct();
			this.attackTree[11] = new Attack.DefenseCurl();
			this.attackTree[16] = new Attack.RockThrow();
			this.attackTree[21] = new Attack.Selfdestruct();
			this.attackTree[29] = new Attack.Harden();
			this.attackTree[36] = new Attack.Earthquake();
			this.attackTree[43] = new Attack.Explosion();
		}
	}
	
	public static class Ponyta extends Pokemon{
		public Ponyta() {
			super(77, "Ponyta", Types.FIRE, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=82;
			
			this._baseHP=50;
			this._baseAtk=85;
			this._baseDef=55;
			this._baseSpAtk=65;
			this._baseSpDef=65;
			this._baseSpd=90;
			
			this._expMult=1.0;
			
			
			this.attackTree[1] = new Attack.Ember();
			this.attackTree[30] = new Attack.TailWhip();
			this.attackTree[32] = new Attack.Stomp();
			this.attackTree[35] = new Attack.Growl();
			this.attackTree[39] = new Attack.FireSpin();
			this.attackTree[43] = new Attack.TakeDown();
			this.attackTree[48] = new Attack.Agility();
		}
	}
	
	public static class Rapidash extends Pokemon{
		public Rapidash() {
			super(78, "Rapidash", Types.FIRE, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=175;
			
			this._baseHP=65;
			this._baseAtk=100;
			this._baseDef=70;
			this._baseSpAtk=80;
			this._baseSpDef=80;
			this._baseSpd=105;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Ember();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[3] = new Attack.Stomp();
			this.attackTree[30] = new Attack.TailWhip();
			this.attackTree[32] = new Attack.Stomp();
			this.attackTree[35] = new Attack.Growl();
			this.attackTree[39] = new Attack.FireSpin();
			this.attackTree[47] = new Attack.TakeDown();
			this.attackTree[55] = new Attack.Agility();
		}
	}
	
	public static class Slowpoke extends Pokemon{
		public Slowpoke() {
			super(79, "Slowpoke", Types.WATER, Types.PSYCHIC,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=63;
			
			this._baseHP=90;
			this._baseAtk=65;
			this._baseDef=65;
			this._baseSpAtk=40;
			this._baseSpDef=40;
			this._baseSpd=15;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Confusion();
			this.attackTree[18] = new Attack.Disable();
			this.attackTree[22] = new Attack.Headbutt();
			this.attackTree[27] = new Attack.Growl();
			this.attackTree[33] = new Attack.WaterGun();
			this.attackTree[40] = new Attack.Amnesia();
			this.attackTree[48] = new Attack.Psychic();
		}
	}
	
	public static class Slowbro extends Pokemon{
		public Slowbro() {
			super(80, "Slowbro", Types.WATER, Types.PSYCHIC,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=172;
			
			this._baseHP=95;
			this._baseAtk=75;
			this._baseDef=110;
			this._baseSpAtk=100;
			this._baseSpDef=80;
			this._baseSpd=30;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Confusion();
			this.attackTree[2] = new Attack.Disable();
			this.attackTree[3] = new Attack.Headbutt();
			this.attackTree[18] = new Attack.Disable();
			this.attackTree[22] = new Attack.Headbutt();
			this.attackTree[27] = new Attack.Growl();
			this.attackTree[33] = new Attack.WaterGun();
			this.attackTree[37] = new Attack.Withdraw();
			this.attackTree[44] = new Attack.Amnesia();
			this.attackTree[55] = new Attack.Psychic();
		}
	}
	
	public static class Magnemite extends Pokemon{
		public Magnemite() {
			super(81, "Magnemite", Types.ELECTRIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=65;
			
			this._baseHP=25;
			this._baseAtk=35;
			this._baseDef=70;
			this._baseSpAtk=95;
			this._baseSpDef=55;
			this._baseSpd=45;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[21] = new Attack.SonicBoom();
			this.attackTree[25] = new Attack.ThunderShock();
			this.attackTree[29] = new Attack.Supersonic();
			this.attackTree[35] = new Attack.ThunderWave();
			this.attackTree[46] = new Attack.Swift();
			this.attackTree[47] = new Attack.Screech();
		}
	}
	
	public static class Magneton extends Pokemon{
		public Magneton() {
			super(82, "Magneton", Types.ELECTRIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=163;
			
			this._baseHP=50;
			this._baseAtk=60;
			this._baseDef=95;
			this._baseSpAtk=120;
			this._baseSpDef=70;
			this._baseSpd=70;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.SonicBoom();
			this.attackTree[3] = new Attack.ThunderShock();
			this.attackTree[4] = new Attack.Supersonic();
			this.attackTree[21] = new Attack.SonicBoom();
			this.attackTree[25] = new Attack.ThunderShock();
			this.attackTree[29] = new Attack.Supersonic();
			this.attackTree[38] = new Attack.ThunderWave();
			this.attackTree[46] = new Attack.Swift();
			this.attackTree[54] = new Attack.Screech();
		}
	}
	
	public static class Farfetchd extends Pokemon{
		public Farfetchd() {
			super(83, "Farfetch'd", Types.NORMAL, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=123;
			
			this._baseHP=52;
			this._baseAtk=65;
			this._baseDef=55;
			this._baseSpAtk=58;
			this._baseSpDef=62;
			this._baseSpd=60;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[2] = new Attack.SandAttack();
			this.attackTree[7] = new Attack.Leer();
			this.attackTree[15] = new Attack.FuryAttack();
			this.attackTree[23] = new Attack.SwordsDance();
			this.attackTree[31] = new Attack.Agility();
			this.attackTree[39] = new Attack.Slash();
		}
	}
	
	public static class Doduo extends Pokemon{
		public Doduo() {
			super(84, "Doduo", Types.NORMAL, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=62;
			
			this._baseHP=35;
			this._baseAtk=85;
			this._baseDef=45;
			this._baseSpAtk=35;
			this._baseSpDef=35;
			this._baseSpd=75;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[20] = new Attack.Growl();
			this.attackTree[24] = new Attack.FuryAttack();
			this.attackTree[30] = new Attack.DrillPeck();
			this.attackTree[36] = new Attack.Rage();
			this.attackTree[40] = new Attack.TriAttack();
			this.attackTree[44] = new Attack.Agility();
			
		}
	}
	
	public static class Dodrio extends Pokemon{
		public Dodrio() {
			super(85, "Dodrio", Types.NORMAL, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=161;
			
			this._baseHP=60;
			this._baseAtk=110;
			this._baseDef=70;
			this._baseSpAtk=60;
			this._baseSpDef=60;
			this._baseSpd=100;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[3] = new Attack.FuryAttack();
			this.attackTree[4] = new Attack.DrillPeck();
			this.attackTree[20] = new Attack.Growl();
			this.attackTree[24] = new Attack.FuryAttack();
			this.attackTree[30] = new Attack.DrillPeck();
			this.attackTree[39] = new Attack.Rage();
			this.attackTree[45] = new Attack.TriAttack();
			this.attackTree[51] = new Attack.Agility();
		}
	}
	
	public static class Seel extends Pokemon{
		public Seel() {
			super(86, "Seel", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=65;
			
			this._baseHP=65;
			this._baseAtk=45;
			this._baseDef=55;
			this._baseSpAtk=45;
			this._baseSpDef=70;
			this._baseSpd=45;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Headbutt();
			this.attackTree[30] = new Attack.Growl();
			this.attackTree[35] = new Attack.AuroraBeam();
			this.attackTree[40] = new Attack.Rest();
			this.attackTree[45] = new Attack.TakeDown();
			this.attackTree[50] = new Attack.IceBeam();
		}
	}
	
	public static class Dewgong extends Pokemon{
		public Dewgong() {
			super(87, "Dewgong", Types.WATER, Types.ICE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=166;
			
			this._baseHP=90;
			this._baseAtk=70;
			this._baseDef=80;
			this._baseSpAtk=70;
			this._baseSpDef=95;
			this._baseSpd=70;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Headbutt();
			this.attackTree[30] = new Attack.Growl();
			this.attackTree[35] = new Attack.AuroraBeam();
			this.attackTree[44] = new Attack.Rest();
			this.attackTree[50] = new Attack.TakeDown();
			this.attackTree[56] = new Attack.IceBeam();
		}
	}
	
	public static class Grimer extends Pokemon{
		public Grimer() {
			super(88, "Grimer", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=65;
			
			this._baseHP=80;
			this._baseAtk=80;
			this._baseDef=50;
			this._baseSpAtk=40;
			this._baseSpDef=55;
			this._baseSpd=25;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Pound();
			this.attackTree[2] = new Attack.Disable();
			this.attackTree[30] = new Attack.PoisonGas();
			this.attackTree[33] = new Attack.Minimize();
			this.attackTree[37] = new Attack.Sludge();
			this.attackTree[42] = new Attack.Harden();
			this.attackTree[48] = new Attack.Screech();
			this.attackTree[55] = new Attack.AcidArmor();
		}
	}
	
	public static class Muk extends Pokemon{
		public Muk() {
			super(89, "Muk", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=175;
			
			this._baseHP=105;
			this._baseAtk=105;
			this._baseDef=75;
			this._baseSpAtk=65;
			this._baseSpDef=100;
			this._baseSpd=50;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Pound();
			this.attackTree[2] = new Attack.Disable();
			this.attackTree[3] = new Attack.PoisonGas();
			this.attackTree[30] = new Attack.PoisonGas();
			this.attackTree[33] = new Attack.Minimize();
			this.attackTree[37] = new Attack.Sludge();
			this.attackTree[45] = new Attack.Harden();
			this.attackTree[53] = new Attack.Screech();
			this.attackTree[60] = new Attack.AcidArmor();
		}
	}
	
	public static class Shellder extends Pokemon{
		public Shellder() {
			super(90, "Shellder", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=61;
			
			this._baseHP=30;
			this._baseAtk=65;
			this._baseDef=100;
			this._baseSpAtk=45;
			this._baseSpDef=25;
			this._baseSpd=40;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.Withdraw();
			this.attackTree[18] = new Attack.Supersonic();
			this.attackTree[23] = new Attack.Clamp();
			this.attackTree[30] = new Attack.AuroraBeam();
			this.attackTree[39] = new Attack.Leer();
			this.attackTree[49] = new Attack.IceBeam();
		}
	}
	
	public static class Cloyster extends Pokemon{
		public Cloyster() {
			super(91, "Cloyster", Types.WATER, Types.ICE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=184;
			
			this._baseHP=50;
			this._baseAtk=95;
			this._baseDef=180;
			this._baseSpAtk=85;
			this._baseSpDef=45;
			this._baseSpd=70;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Withdraw();
			this.attackTree[2] = new Attack.Supersonic();
			this.attackTree[3] = new Attack.AuroraBeam();
			this.attackTree[4] = new Attack.Clamp();
			this.attackTree[50] = new Attack.SpikeCannon();
		}
	}
	
	public static class Gastly extends Pokemon{
		public Gastly() {
			super(92, "Gastly", Types.GHOST, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=62;
			
			this._baseHP=30;
			this._baseAtk=35;
			this._baseDef=30;
			this._baseSpAtk=100;
			this._baseSpDef=35;
			this._baseSpd=80;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Lick();
			this.attackTree[2] = new Attack.ConfuseRay();
			this.attackTree[3] = new Attack.NightShade();
			this.attackTree[27] = new Attack.Hypnosis();
			this.attackTree[35] = new Attack.DreamEater();
		}
	}
	
	public static class Haunter extends Pokemon{
		public Haunter() {
			super(93, "Haunter", Types.GHOST, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			this._evolLvl = 39;
			
			//Base Stats
			this._baseExp=142;
			
			this._baseHP=45;
			this._baseAtk=50;
			this._baseDef=45;
			this._baseSpAtk=115;
			this._baseSpDef=55;
			this._baseSpd=95;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Lick();
			this.attackTree[2] = new Attack.ConfuseRay();
			this.attackTree[3] = new Attack.NightShade();
			this.attackTree[29] = new Attack.Hypnosis();
			this.attackTree[38] = new Attack.DreamEater();
		}
	}
	
	public static class Gengar extends Pokemon{
		public Gengar() {
			super(94, "Gengar", Types.GHOST, Types.POISON,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=225;
			
			this._baseHP=60;
			this._baseAtk=65;
			this._baseDef=60;
			this._baseSpAtk=120;
			this._baseSpDef=75;
			this._baseSpd=110;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Lick();
			this.attackTree[2] = new Attack.ConfuseRay();
			this.attackTree[3] = new Attack.NightShade();
			this.attackTree[27] = new Attack.Hypnosis();
			this.attackTree[35] = new Attack.DreamEater();
		}
	}
	
	public static class Onix extends Pokemon{
		public Onix() {
			super(95, "Onix", Types.ROCK, Types.GROUND,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=108;
			
			this._baseHP=35;
			this._baseAtk=45;
			this._baseDef=160;
			this._baseSpAtk=30;
			this._baseSpDef=45;
			this._baseSpd=70;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.Screech();
			this.attackTree[15] = new Attack.Bind();
			this.attackTree[19] = new Attack.RockThrow();
			this.attackTree[25] = new Attack.Rage();
			this.attackTree[33] = new Attack.Slam();
			this.attackTree[43] = new Attack.Harden();
		}
	}
	
	public static class Drowzee extends Pokemon{
		public Drowzee() {
			super(96, "Drowzee", Types.PSYCHIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=66;
			
			this._baseHP=60;
			this._baseAtk=48;
			this._baseDef=45;
			this._baseSpAtk=43;
			this._baseSpDef=90;
			this._baseSpd=42;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Pound();
			this.attackTree[2] = new Attack.Hypnosis();
			this.attackTree[12] =  new Attack.Disable();
			this.attackTree[17] = new Attack.Confusion();
			this.attackTree[24] = new Attack.Headbutt();
			this.attackTree[29] = new Attack.PoisonGas();
			this.attackTree[32] = new Attack.Psychic();
			this.attackTree[37] = new Attack.Meditate();
		}
	}
	
	public static class Hypno extends Pokemon{
		public Hypno() {
			super(97, "Hypno", Types.PSYCHIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=169;
			
			this._baseHP=85;
			this._baseAtk=73;
			this._baseDef=70;
			this._baseSpAtk=75;
			this._baseSpDef=115;
			this._baseSpd=67;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Pound();
			this.attackTree[2] = new Attack.Hypnosis();
			this.attackTree[3] =  new Attack.Disable();
			this.attackTree[4] = new Attack.Confusion();
			this.attackTree[12] =  new Attack.Disable();
			this.attackTree[17] = new Attack.Confusion();
			this.attackTree[24] = new Attack.Headbutt();
			this.attackTree[33] = new Attack.PoisonGas();
			this.attackTree[37] = new Attack.Psychic();
			this.attackTree[43] = new Attack.Meditate();
		}
	}
	
	public static class Krabby extends Pokemon{
		public Krabby() {
			super(98, "Krabby", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=65;
			
			this._baseHP=30;
			this._baseAtk=105;
			this._baseDef=90;
			this._baseSpAtk=25;
			this._baseSpDef=25;
			this._baseSpd=50;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Bubble();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[20] = new Attack.ViceGrip();
			this.attackTree[25] = new Attack.Guillotine();
			this.attackTree[30] = new Attack.Stomp();
			this.attackTree[35] = new Attack.Crabhammer();
			this.attackTree[40] = new Attack.Harden();
		}
	}
	
	public static class Kingler extends Pokemon{
		public Kingler() {
			super(99, "Kingler", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=166;
			
			this._baseHP=55;
			this._baseAtk=130;
			this._baseDef=115;
			this._baseSpAtk=50;
			this._baseSpDef=50;
			this._baseSpd=75;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Bubble();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[3] = new Attack.ViceGrip();
			this.attackTree[4] = new Attack.Guillotine();
			this.attackTree[20] = new Attack.ViceGrip();
			this.attackTree[25] = new Attack.Guillotine();
			this.attackTree[34] = new Attack.Stomp();
			this.attackTree[42] = new Attack.Crabhammer();
			this.attackTree[49] = new Attack.Harden();
		}
	}

	public static class Voltorb extends Pokemon{
		public Voltorb() {
			super(100, "Voltorb", Types.ELECTRIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=66;
			
			this._baseHP=40;
			this._baseAtk=30;
			this._baseDef=50;
			this._baseSpAtk=55;
			this._baseSpDef=55;
			this._baseSpd=100;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.Screech();
			this.attackTree[17] = new Attack.SonicBoom();
			this.attackTree[22] = new Attack.Selfdestruct();
			this.attackTree[29] = new Attack.LightScreen();
			this.attackTree[36] = new Attack.Swift();
			this.attackTree[43] = new Attack.Explosion();
		}
	}
	
	public static class Electrode extends Pokemon{
		public Electrode() {
			super(101, "Electrode", Types.ELECTRIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=168;
			
			this._baseHP=60;
			this._baseAtk=50;
			this._baseDef=70;
			this._baseSpAtk=80;
			this._baseSpDef=80;
			this._baseSpd=140;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.Screech();
			this.attackTree[17] = new Attack.SonicBoom();
			this.attackTree[22] = new Attack.Selfdestruct();
			this.attackTree[29] = new Attack.LightScreen();
			this.attackTree[40] = new Attack.Swift();
			this.attackTree[50] = new Attack.Explosion();
		}
	}
	
	public static class Exeggcute extends Pokemon{
		public Exeggcute() {
			super(102, "Exeggcute", Types.GRASS, Types.PSYCHIC,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=65;
			
			this._baseHP=60;
			this._baseAtk=40;
			this._baseDef=80;
			this._baseSpAtk=60;
			this._baseSpDef=45;
			this._baseSpd=40;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Barrage();
			this.attackTree[2] = new Attack.Hypnosis();
			this.attackTree[25] = new Attack.Reflect();
			this.attackTree[28] = new Attack.LeechSeed();
			this.attackTree[32] = new Attack.StunSpore();
			this.attackTree[37] = new Attack.PoisonPowder();
			this.attackTree[42] = new Attack.SolarBeam();
			this.attackTree[48] = new Attack.SleepPowder();
		}
	}
	
	public static class Exeggutor extends Pokemon{
		public Exeggutor() {
			super(103, "Exeggutor", Types.GRASS, Types.PSYCHIC,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=182;
			
			this._baseHP=95;
			this._baseAtk=95;
			this._baseDef=85;
			this._baseSpAtk=125;
			this._baseSpDef=65;
			this._baseSpd=55;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Barrage();
			this.attackTree[2] = new Attack.Hypnosis();
			this.attackTree[28] = new Attack.Stomp();
		}
	}
	
	public static class Cubone extends Pokemon{
		public Cubone() {
			super(104, "Cubone", Types.GROUND, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=64;
			
			this._baseHP=50;
			this._baseAtk=50;
			this._baseDef=95;
			this._baseSpAtk=40;
			this._baseSpDef=45;
			this._baseSpd=35;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Growl();
			this.attackTree[2] = new Attack.BoneClub();
			this.attackTree[3] = new Attack.TailWhip();
			this.attackTree[18] = new Attack.Headbutt();
			this.attackTree[25] = new Attack.Leer();
			this.attackTree[31] = new Attack.FocusEnergy();
			this.attackTree[38] = new Attack.Thrash();
			this.attackTree[43] = new Attack.Bonemerang();
			this.attackTree[46] = new Attack.Rage();
		}
	}
	
	public static class Marowak extends Pokemon{
		public Marowak() {
			super(105, "Marowak", Types.GROUND, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=149;
			
			this._baseHP=60;
			this._baseAtk=80;
			this._baseDef=110;
			this._baseSpAtk=50;
			this._baseSpDef=80;
			this._baseSpd=45;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Growl();
			this.attackTree[2] = new Attack.BoneClub();
			this.attackTree[3] = new Attack.TailWhip();
			this.attackTree[4] = new Attack.Leer();
			this.attackTree[10] = new Attack.BoneClub();
			this.attackTree[18] = new Attack.Headbutt();
			this.attackTree[25] = new Attack.Leer();
			this.attackTree[33] = new Attack.FocusEnergy();
			this.attackTree[41] = new Attack.Thrash();
			this.attackTree[48] = new Attack.Bonemerang();
			this.attackTree[55] = new Attack.Rage();
		}
	}
	
	public static class Hitmonlee extends Pokemon{
		public Hitmonlee() {
			super(106, "Hitmonlee", Types.FIGHTING, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=159;
			
			this._baseHP=50;
			this._baseAtk=120;
			this._baseDef=53;
			this._baseSpAtk=35;
			this._baseSpDef=110;
			this._baseSpd=87;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.DoubleKick();
			this.attackTree[2] = new Attack.Meditate();
			this.attackTree[33] = new Attack.RollingKick();
			this.attackTree[38] = new Attack.JumpKick();
			this.attackTree[43] = new Attack.FocusEnergy();
			this.attackTree[48] = new Attack.HiJumpKick();
			this.attackTree[53] = new Attack.MegaKick();
		}
	}
	
	public static class Hitmonchan extends Pokemon{
		public Hitmonchan() {
			super(107, "Hitmonchan", Types.FIGHTING, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=159;
			
			this._baseHP=50;
			this._baseAtk=105;
			this._baseDef=79;
			this._baseSpAtk=35;
			this._baseSpDef=110;
			this._baseSpd=76;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.CometPunch();
			this.attackTree[2] = new Attack.Agility();
			this.attackTree[33] = new Attack.FirePunch();
			this.attackTree[38] = new Attack.IcePunch();
			this.attackTree[43] = new Attack.ThunderPunch();
			this.attackTree[48] = new Attack.MegaPunch();
			this.attackTree[53] = new Attack.Counter();
		}
	}
	
	public static class Lickitung extends Pokemon{
		public Lickitung() {
			super(108, "Lickitung", Types.NORMAL, Types.NONE, 14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=77;
			
			this._baseHP=90;
			this._baseAtk=55;
			this._baseDef=75;
			this._baseSpAtk=60;
			this._baseSpDef=75;
			this._baseSpd=30;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Lick(); //Knows Lick in Gen 2, but I don't care. It makes sense this way.
			this.attackTree[2] = new Attack.Supersonic();
			this.attackTree[7] = new Attack.Stomp();
			this.attackTree[15] = new Attack.Disable();
			this.attackTree[23] = new Attack.DefenseCurl();
			this.attackTree[31] = new Attack.Slam();
			this.attackTree[39] = new Attack.Screech();
		}
	}
	
	public static class Koffing extends Pokemon{
		public Koffing() {
			super(109, "Koffing", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=68;
			
			this._baseHP=40;
			this._baseAtk=65;
			this._baseDef=95;
			this._baseSpAtk=60;
			this._baseSpDef=75;
			this._baseSpd=30;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.Smog();
			this.attackTree[32] = new Attack.Sludge();
			this.attackTree[37] = new Attack.SmokeScreen();
			this.attackTree[40] = new Attack.Selfdestruct();
			this.attackTree[45] = new Attack.Haze();
			this.attackTree[48] = new Attack.Explosion();
		}
	}
	
	public static class Weezing extends Pokemon{
		public Weezing() {
			super(110, "Weezing", Types.POISON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=172;
			
			this._baseHP=65;
			this._baseAtk=90;
			this._baseDef=120;
			this._baseSpAtk=85;
			this._baseSpDef=70;
			this._baseSpd=60;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.Smog();
			this.attackTree[3] = new Attack.Sludge();
			this.attackTree[32] = new Attack.Sludge();
			this.attackTree[39] = new Attack.SmokeScreen();
			this.attackTree[43] = new Attack.Selfdestruct();
			this.attackTree[49] = new Attack.Haze();
			this.attackTree[53] = new Attack.Explosion();
		}
	}
	
	public static class Rhyhorn extends Pokemon{
		public Rhyhorn() {
			super(111, "Rhyhorn", Types.GROUND, Types.ROCK,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=69;
			
			this._baseHP=80;
			this._baseAtk=85;
			this._baseDef=95;
			this._baseSpAtk=30;
			this._baseSpDef=30;
			this._baseSpd=25;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.HornAttack();
			this.attackTree[30] = new Attack.Stomp();
			this.attackTree[35] = new Attack.TailWhip();
			this.attackTree[40] = new Attack.FuryAttack();
			this.attackTree[45] = new Attack.HornDrill();
			this.attackTree[50] = new Attack.Leer();
			this.attackTree[55] = new Attack.TakeDown();
		}
	}

	public static class Rhydon extends Pokemon{
		public Rhydon() {
			super(112, "Rhydon", Types.GROUND, Types.ROCK,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=170;
			
			this._baseHP=105;
			this._baseAtk=130;
			this._baseDef=120;
			this._baseSpAtk=45;
			this._baseSpDef=45;
			this._baseSpd=40;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.HornAttack();
			this.attackTree[30] = new Attack.Stomp();
			this.attackTree[35] = new Attack.TailWhip();
			this.attackTree[40] = new Attack.FuryAttack();
			this.attackTree[48] = new Attack.HornDrill();
			this.attackTree[55] = new Attack.Leer();
			this.attackTree[64] = new Attack.TakeDown();
		}
	}
	
	public static class Chansey extends Pokemon{
		public Chansey() {
			super(113, "Chansey", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=395;
			
			this._baseHP=250;
			this._baseAtk=5;
			this._baseDef=5;
			this._baseSpAtk=35;
			this._baseSpDef=105;
			this._baseSpd=50;
			
			this._expMult=1.25;
			
			//Gen 2 learnset (all Gen 1 attacks) makes more sense.
			this.attackTree[1] = new Attack.Pound();
			this.attackTree[5] = new Attack.Growl();
			this.attackTree[9] = new Attack.TailWhip();
			this.attackTree[13] = new Attack.Softboiled();
			this.attackTree[17] = new Attack.DoubleSlap();
			this.attackTree[23] = new Attack.Minimize();
			this.attackTree[29] = new Attack.Sing();
			this.attackTree[35] = new Attack.EggBomb();
			this.attackTree[41] = new Attack.DefenseCurl();
			this.attackTree[49] = new Attack.LightScreen();
			this.attackTree[57] = new Attack.DoubleEdge();
		}
	}
	
	public static class Tangela extends Pokemon{
		public Tangela() {
			super(114, "Tangela", Types.GRASS, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=87;
			
			this._baseHP=65;
			this._baseAtk=55;
			this._baseDef=115;
			this._baseSpAtk=100;
			this._baseSpDef=40;
			this._baseSpd=60;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Constrict();
			this.attackTree[2] = new Attack.Bind();
			this.attackTree[27] = new Attack.Absorb();
			this.attackTree[29] = new Attack.VineWhip();
			this.attackTree[32] = new Attack.PoisonPowder();
			this.attackTree[36] = new Attack.StunSpore();
			this.attackTree[39] = new Attack.SleepPowder();
			this.attackTree[45] = new Attack.Slam();
			this.attackTree[49] = new Attack.Growth();
		}
	}
	
	public static class Kangaskhan extends Pokemon{
		public Kangaskhan(){
			super(115, "Kangaskhan", Types.NORMAL, Types.NONE, 15, 7, 0, 0, 7, 190, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;

			//Base Stats
			this._baseExp=172;
			
			this._baseHP=105;
			this._baseAtk=95;
			this._baseDef=80;
			this._baseSpAtk=40;
			this._baseSpDef=80;
			this._baseSpd=90;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.CometPunch();
			this.attackTree[7] = new Attack.Leer();
			this.attackTree[13] = new Attack.Bite();
			this.attackTree[19] = new Attack.TailWhip();
			this.attackTree[25] = new Attack.MegaPunch();
			this.attackTree[31] = new Attack.Rage();
			this.attackTree[43] = new Attack.DizzyPunch();
		}
	}
	
	public static class Horsea extends Pokemon{
		public Horsea() {
			super(116, "Horsea", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=59;
			
			this._baseHP=30;
			this._baseAtk=40;
			this._baseDef=70;
			this._baseSpAtk=70;
			this._baseSpDef=25;
			this._baseSpd=60;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Bubble();
			this.attackTree[19] = new Attack.SmokeScreen();
			this.attackTree[24] = new Attack.Leer();
			this.attackTree[30] = new Attack.WaterGun();
			this.attackTree[37] = new Attack.Agility();
			this.attackTree[45] = new Attack.HydroPump();
		}
	}
	
	public static class Seadra extends Pokemon{
		public Seadra() {
			super(117, "Seadra", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=154;
			
			this._baseHP=65;
			this._baseAtk=65;
			this._baseDef=95;
			this._baseSpAtk=95;
			this._baseSpDef=45;
			this._baseSpd=85;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Bubble();
			this.attackTree[2] = new Attack.SmokeScreen();
			this.attackTree[3] = new Attack.Leer();
			this.attackTree[4] = new Attack.WaterGun();
			this.attackTree[19] = new Attack.SmokeScreen();
			this.attackTree[24] = new Attack.Leer();
			this.attackTree[30] = new Attack.WaterGun();
			this.attackTree[41] = new Attack.Agility();
			this.attackTree[52] = new Attack.HydroPump();
			
		}
	}
	
	public static class Goldeen extends Pokemon{
		public Goldeen() {
			super(118, "Goldeen", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=64;
			
			this._baseHP=45;
			this._baseAtk=67;
			this._baseDef=60;
			this._baseSpAtk=35;
			this._baseSpDef=50;
			this._baseSpd=63;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[19] = new Attack.Supersonic();
			this.attackTree[24] = new Attack.HornAttack();
			this.attackTree[30] = new Attack.FuryAttack();
			this.attackTree[37] = new Attack.Waterfall();
			this.attackTree[45] = new Attack.HornDrill();
			this.attackTree[54] = new Attack.Agility();
		}
	}
	
	public static class Seaking extends Pokemon{
		public Seaking() {
			super(119, "Seaking", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=158;
			
			this._baseHP=80;
			this._baseAtk=92;
			this._baseDef=65;
			this._baseSpAtk=65;
			this._baseSpDef=80;
			this._baseSpd=68;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[3] = new Attack.Supersonic();
			this.attackTree[4] = new Attack.HornAttack();
			this.attackTree[19] = new Attack.Supersonic();
			this.attackTree[24] = new Attack.HornAttack();
			this.attackTree[30] = new Attack.FuryAttack();
			this.attackTree[39] = new Attack.Waterfall();
			this.attackTree[48] = new Attack.HornDrill();
			this.attackTree[54] = new Attack.Agility();
		}
	}
	
	public static class Staryu extends Pokemon{
		public Staryu() {
			super(120, "Staryu", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=68;
			
			this._baseHP=30;
			this._baseAtk=45;
			this._baseDef=55;
			this._baseSpAtk=70;
			this._baseSpDef=55;
			this._baseSpd=85;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[17] = new Attack.WaterGun();
			this.attackTree[22] = new Attack.Harden();
			this.attackTree[27] = new Attack.Recover();
			this.attackTree[32] = new Attack.Swift();
			this.attackTree[37] = new Attack.Minimize();
			this.attackTree[42] = new Attack.LightScreen();
			this.attackTree[47] = new Attack.HydroPump();
		}
	}
	
	public static class Starmie extends Pokemon{
		public Starmie() {
			super(121, "Starmie", Types.WATER, Types.PSYCHIC,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=182;
			
			this._baseHP=60;
			this._baseAtk=75;
			this._baseDef=85;
			this._baseSpAtk=100;
			this._baseSpDef=85;
			this._baseSpd=115;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.WaterGun();
			this.attackTree[3] = new Attack.Harden();
		}
	}
	
	public static class Mr_Mime extends Pokemon{
		public Mr_Mime() {
			super(122, "Mr. Mime", Types.PSYCHIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=161;
			
			this._baseHP=40;
			this._baseAtk=45;
			this._baseDef=65;
			this._baseSpAtk=100;
			this._baseSpDef=120;
			this._baseSpd=90;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Confusion();
			this.attackTree[2] = new Attack.Barrier();
			//this.attackTree[12] = new Attack.Mimic(); //Really? Mr. Mime doesn't learn Mimic? Bullshit.
			this.attackTree[23] = new Attack.LightScreen();
			this.attackTree[31] = new Attack.DoubleSlap();
			this.attackTree[39] = new Attack.Meditate();
		//	this.attackTree[47] = new Attack.Substitute();
		}
	}
	
	public static class Scyther extends Pokemon{
		public Scyther() {
			super(123, "Scyther", Types.BUG, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=100;
			
			this._baseHP=70;
			this._baseAtk=110;
			this._baseDef=80;
			this._baseSpAtk=55;
			this._baseSpDef=80;
			this._baseSpd=105;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.QuickAttack();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[3] = new Attack.FocusEnergy();
			this.attackTree[24] = new Attack.DoubleTeam();
			this.attackTree[29] = new Attack.Slash();
			this.attackTree[35] = new Attack.SwordsDance();
			this.attackTree[42] = new Attack.Agility();
			this.attackTree[50] = new Attack.WingAttack();
		}
	}
	
	public static class Jynx extends Pokemon{
		public Jynx() {
			super(124, "Jynx", Types.ICE, Types.PSYCHIC,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=159;
			
			this._baseHP=65;
			this._baseAtk=50;
			this._baseDef=35;
			this._baseSpAtk=115;
			this._baseSpDef=95;
			this._baseSpd=95;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Pound();
			this.attackTree[2] = new Attack.LovelyKiss();
			this.attackTree[15] = new Attack.Sing();
			this.attackTree[18] = new Attack.Lick();
			this.attackTree[23] = new Attack.DoubleSlap();
			this.attackTree[31] = new Attack.IcePunch();
			this.attackTree[39] = new Attack.BodySlam();
			this.attackTree[47] = new Attack.Thrash();
			this.attackTree[58] = new Attack.Blizzard();
		}
	}
	
	public static class Electabuzz extends Pokemon{
		public Electabuzz() {
			super(125, "Electabuzz", Types.ELECTRIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=172;
			
			this._baseHP=65;
			this._baseAtk=83;
			this._baseDef=57;
			this._baseSpAtk=95;
			this._baseSpDef=85;
			this._baseSpd=105;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.QuickAttack();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[3] = new Attack.ThunderShock();
			this.attackTree[34] = new Attack.ThunderShock();
			this.attackTree[37] = new Attack.Screech();
			this.attackTree[42] = new Attack.ThunderPunch();
			this.attackTree[49] = new Attack.LightScreen();
			this.attackTree[54] = new Attack.Thunder();
		}
	}
	
	public static class Magmar extends Pokemon{
		public Magmar() {
			super(126, "Magmar", Types.FIRE, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=173;
			
			this._baseHP=65;
			this._baseAtk=95;
			this._baseDef=57;
			this._baseSpAtk=100;
			this._baseSpDef=85;
			this._baseSpd=93;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Ember();
			this.attackTree[36] = new Attack.Leer();
			this.attackTree[39] = new Attack.ConfuseRay();
			this.attackTree[43] = new Attack.FirePunch();
			this.attackTree[48] = new Attack.SmokeScreen();
			this.attackTree[52] = new Attack.Smog();
			this.attackTree[55] = new Attack.Flamethrower();
			
		}
	}
	
	public static class Pinsir extends Pokemon{
		public Pinsir() {
			super(127, "Pinsir", Types.BUG, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=175;
			
			this._baseHP=65;
			this._baseAtk=125;
			this._baseDef=100;
			this._baseSpAtk=55;
			this._baseSpDef=70;
			this._baseSpd=85;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.ViceGrip();
			this.attackTree[21] = new Attack.Bind();
			this.attackTree[25] = new Attack.SeismicToss();
			this.attackTree[30] = new Attack.Guillotine();
			this.attackTree[36] = new Attack.FocusEnergy();
			this.attackTree[43] = new Attack.Harden();
			this.attackTree[49] = new Attack.Slash();
			this.attackTree[54] = new Attack.SwordsDance();
		}
	}
	
	public static class Tauros extends Pokemon{
		public Tauros() {
			super(128, "Tauros", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=211;
			
			this._baseHP=75;
			this._baseAtk=100;
			this._baseDef=95;
			this._baseSpAtk=40;
			this._baseSpDef=70;
			this._baseSpd=110;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[21] = new Attack.Stomp();
			this.attackTree[28] = new Attack.TailWhip();
			this.attackTree[35] = new Attack.Leer();
			this.attackTree[44] = new Attack.Rage();
			this.attackTree[51] = new Attack.TakeDown();
		}
	}
	
	public static class Magikarp extends Pokemon{
		public Magikarp() {
			super(129, "Magikarp", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=40;
			
			this._baseHP=20;
			this._baseAtk=10;
			this._baseDef=55;
			this._baseSpAtk=15;
			this._baseSpDef=20;
			this._baseSpd=80;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Splash();
			this.attackTree[15] = new Attack.Tackle();
		}
	}
	
	public static class Gyarados extends Pokemon{
		public Gyarados() {
			super(130, "Gyarados", Types.WATER, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=189;
			
			this._baseHP=95;
			this._baseAtk=125;
			this._baseDef=79;
			this._baseSpAtk=60;
			this._baseSpDef=100;
			this._baseSpd=81;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[20] = new Attack.Bite();
			this.attackTree[25] = new Attack.DragonRage();
			this.attackTree[32] = new Attack.Leer();
			this.attackTree[41] = new Attack.HydroPump();
			this.attackTree[52] = new Attack.HyperBeam();
		}
	}
	
	public static class Lapras extends Pokemon{
		public Lapras() {
			super(131, "Lapras", Types.WATER, Types.ICE, 14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=187;
			
			this._baseHP=130;
			this._baseAtk=85;
			this._baseDef=80;
			this._baseSpAtk=85;
			this._baseSpDef=95;
			this._baseSpd=60;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.WaterGun();
			this.attackTree[2] = new Attack.Growl();
			this.attackTree[16] = new Attack.Sing();
			this.attackTree[20] = new Attack.Mist();
			this.attackTree[25] = new Attack.BodySlam();
			this.attackTree[31] = new Attack.ConfuseRay();
			this.attackTree[38] = new Attack.IceBeam();
			this.attackTree[46] = new Attack.HydroPump();
		}
	}

	public static class Ditto extends Pokemon{
		public Ditto() {
			super(132, "Ditto", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=101;
			
			this._baseHP=48;
			this._baseAtk=48;
			this._baseDef=48;
			this._baseSpAtk=48;
			this._baseSpDef=48;
			this._baseSpd=48;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Transform();
		}
	}
	
	public static class Eevee extends Pokemon{
		public Eevee() {
			super(133, "Eevee", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=65;
			
			this._baseHP=55;
			this._baseAtk=55;
			this._baseDef=50;
			this._baseSpAtk=45;
			this._baseSpDef=65;
			this._baseSpd=55;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[8] = new Attack.SandAttack();
			this.attackTree[16] = new Attack.Growl();
			this.attackTree[23] = new Attack.QuickAttack();
			this.attackTree[30] = new Attack.Bite();
			this.attackTree[36] = new Attack.FocusEnergy();
			this.attackTree[42] = new Attack.TakeDown();
 		}
	}
	
	public static class Vaporeon extends Pokemon{
		public Vaporeon() {
			super(134, "Vaporeon", Types.WATER, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=184;
			
			this._baseHP=130;
			this._baseAtk=65;
			this._baseDef=60;
			this._baseSpAtk=110;
			this._baseSpDef=95;
			this._baseSpd=65;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.TailWhip();
			this.attackTree[2] = new Attack.SandAttack();
			this.attackTree[31] = new Attack.WaterGun();
			this.attackTree[27] = new Attack.QuickAttack();
			this.attackTree[40] = new Attack.Bite();
			this.attackTree[36] = new Attack.AuroraBeam();
			this.attackTree[44] = new Attack.Haze();
			this.attackTree[48] = new Attack.Mist();
			this.attackTree[42] = new Attack.AcidArmor();
			this.attackTree[54] = new Attack.HydroPump();
		}
	}
	
	public static class Jolteon extends Pokemon{
		public Jolteon() {
			super(135, "Jolteon", Types.ELECTRIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=184;
			
			this._baseHP=65;
			this._baseAtk=65;
			this._baseDef=60;
			this._baseSpAtk=110;
			this._baseSpDef=95;
			this._baseSpd=130;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.SandAttack();
			this.attackTree[3] = new Attack.QuickAttack();
			this.attackTree[4] = new Attack.ThunderShock();
			this.attackTree[27] = new Attack.QuickAttack();
			this.attackTree[31] = new Attack.ThunderShock();
			this.attackTree[37] = new Attack.TailWhip();
			this.attackTree[40] = new Attack.ThunderWave();
			this.attackTree[42] = new Attack.DoubleKick();
			this.attackTree[44] = new Attack.Agility();
			this.attackTree[48] = new Attack.PinMissile();
			this.attackTree[54] = new Attack.Thunder();
		}
	}
	
	public static class Flareon extends Pokemon{
		public Flareon() {
			super(136, "Flareon", Types.FIRE, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=184;
			
			this._baseHP=65;
			this._baseAtk=130;
			this._baseDef=60;
			this._baseSpAtk=95;
			this._baseSpDef=110;
			this._baseSpd=65;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.SandAttack();
			this.attackTree[27] = new Attack.QuickAttack();
			this.attackTree[31] = new Attack.Ember();
			this.attackTree[37] = new Attack.TailWhip();
			this.attackTree[40] = new Attack.Bite();
			this.attackTree[42] = new Attack.Leer();
			this.attackTree[44] = new Attack.FireSpin();
			this.attackTree[48] = new Attack.Rage();
			this.attackTree[54] = new Attack.Flamethrower();
		}
	}
	
	public static class Porygon extends Pokemon{
		public Porygon() {
			super(137, "Porygon", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=79;
			
			this._baseHP=65;
			this._baseAtk=60;
			this._baseDef=70;
			this._baseSpAtk=85;
			this._baseSpDef=75;
			this._baseSpd=40;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Tackle();
			this.attackTree[2] = new Attack.Sharpen();
			this.attackTree[3] = new Attack.Conversion();
			this.attackTree[23] = new Attack.Psybeam();
			this.attackTree[28] = new Attack.Recover();
			this.attackTree[35] = new Attack.Agility();
			this.attackTree[42] = new Attack.TriAttack();
		}
	}
	
	public static class Omanyte extends Pokemon{
		public Omanyte() {
			super(138, "Omanyte", Types.ROCK, Types.WATER,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=71;
			
			this._baseHP=35;
			this._baseAtk=40;
			this._baseDef=100;
			this._baseSpAtk=90;
			this._baseSpDef=55;
			this._baseSpd=35;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.WaterGun();
			this.attackTree[2] = new Attack.Withdraw();
			this.attackTree[34] = new Attack.HornAttack();
			this.attackTree[39] = new Attack.Leer();
			this.attackTree[44] = new Attack.SpikeCannon();
			this.attackTree[49] = new Attack.HydroPump();
		}
	}
	
	public static class Omastar extends Pokemon{
		public Omastar() {
			super(139, "Omastar", Types.ROCK, Types.WATER,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=173;
			
			this._baseHP=70;
			this._baseAtk=60;
			this._baseDef=125;
			this._baseSpAtk=115;
			this._baseSpDef=70;
			this._baseSpd=55;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.WaterGun();
			this.attackTree[2] = new Attack.Withdraw();
			this.attackTree[3] = new Attack.HornAttack();
			this.attackTree[4] = new Attack.Leer();
			this.attackTree[34] = new Attack.HornAttack();
			this.attackTree[39] = new Attack.Leer();
			this.attackTree[44] = new Attack.SpikeCannon();
			this.attackTree[49] = new Attack.HydroPump();
		}
	}
	
	public static class Kabuto extends Pokemon{
		public Kabuto() {
			super(140, "Kabuto", Types.ROCK, Types.WATER,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=71;
			
			this._baseHP=30;
			this._baseAtk=80;
			this._baseDef=90;
			this._baseSpAtk=55;
			this._baseSpDef=45;
			this._baseSpd=55;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.Harden();
			this.attackTree[34] = new Attack.Absorb();
			this.attackTree[39] = new Attack.Slash();
			this.attackTree[44] = new Attack.Leer();
			this.attackTree[49] = new Attack.HydroPump();
		}
	}
	
	public static class Kabutops extends Pokemon{
		public Kabutops() {
			super(141, "Kabutops", Types.ROCK, Types.WATER,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=173;
			
			this._baseHP=60;
			this._baseAtk=115;
			this._baseDef=105;
			this._baseSpAtk=65;
			this._baseSpDef=70;
			this._baseSpd=80;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.Harden();
			this.attackTree[3] = new Attack.Absorb();
			this.attackTree[4] = new Attack.Slash();
			this.attackTree[34] = new Attack.Absorb();
			this.attackTree[39] = new Attack.Slash();
			this.attackTree[46] = new Attack.Leer();
			this.attackTree[53] = new Attack.HydroPump();
		}
	}
	
	public static class Aerodactyl extends Pokemon{
		public Aerodactyl() {
			super(142, "Aerodactyl", Types.ROCK, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=180;
			
			this._baseHP=80;
			this._baseAtk=105;
			this._baseDef=65;
			this._baseSpAtk=60;
			this._baseSpDef=75;
			this._baseSpd=130;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.WingAttack();
			this.attackTree[2] = new Attack.Agility();
			this.attackTree[33] = new Attack.Supersonic();
			this.attackTree[38] = new Attack.Bite();
			this.attackTree[45] = new Attack.TakeDown();
			this.attackTree[54] = new Attack.HyperBeam();
		}
	}
	
	public static class Snorlax extends Pokemon{
		public Snorlax() {
			super(143, "Snorlax", Types.NORMAL, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=189;
			
			this._baseHP=160;
			this._baseAtk=110;
			this._baseDef=65;
			this._baseSpAtk=65;
			this._baseSpDef=110;
			this._baseSpd=30;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Headbutt();
			this.attackTree[2] = new Attack.Amnesia();
			this.attackTree[3] = new Attack.Rest();
			this.attackTree[35] = new Attack.BodySlam();
			this.attackTree[41] = new Attack.Harden();
			this.attackTree[48] = new Attack.DoubleEdge();
			this.attackTree[56] = new Attack.HyperBeam();
		}
	}
	
	public static class Articuno extends Pokemon{
		public Articuno() {
			super(144, "Articuno", Types.ICE, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=261;
			
			this._baseHP=90;
			this._baseAtk=85;
			this._baseDef=100;
			this._baseSpAtk=95;
			this._baseSpDef=125;
			this._baseSpd=85;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[2] = new Attack.IceBeam();
			this.attackTree[51] = new Attack.Blizzard();
			this.attackTree[55] = new Attack.Agility();
			this.attackTree[60] = new Attack.Mist();
		}
	}
	
	public static class Zapdos extends Pokemon{
		public Zapdos() {
			super(145, "Zapdos", Types.ELECTRIC, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=261;
			
			this._baseHP=90;
			this._baseAtk=90;
			this._baseDef=85;
			this._baseSpAtk=125;
			this._baseSpDef=90;
			this._baseSpd=100;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.DrillPeck();
			this.attackTree[2] = new Attack.ThunderShock();
			this.attackTree[51] = new Attack.Thunder();
			this.attackTree[55] = new Attack.Agility();
			this.attackTree[60] = new Attack.LightScreen();
		}
	}
	
	public static class Moltres extends Pokemon{
		public Moltres() {
			super(146, "Moltres", Types.FIRE, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=261;
			
			this._baseHP=90;
			this._baseAtk=100;
			this._baseDef=90;
			this._baseSpAtk=125;
			this._baseSpDef=85;
			this._baseSpd=90;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Peck();
			this.attackTree[2] = new Attack.FireSpin();
			this.attackTree[51] = new Attack.Leer();
			this.attackTree[55] = new Attack.Agility();
			this.attackTree[60] = new Attack.SkyAttack();
		}
	}
	
	public static class Dratini extends Pokemon{
		public Dratini() {
			super(147, "Dratini", Types.DRAGON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=60;
			
			this._baseHP=41;
			this._baseAtk=64;
			this._baseDef=45;
			this._baseSpAtk=50;
			this._baseSpDef=50;
			this._baseSpd=50;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Wrap();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[10] = new Attack.ThunderWave();
			this.attackTree[20] = new Attack.Agility();
			this.attackTree[30] = new Attack.Slam();
			this.attackTree[40] = new Attack.DragonRage();
			this.attackTree[50] = new Attack.HyperBeam();
		}
	}
	
	public static class Dragonair extends Pokemon{
		public Dragonair() {
			super(148, "Dragonair", Types.DRAGON, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=147;
			
			this._baseHP=61;
			this._baseAtk=84;
			this._baseDef=65;
			this._baseSpAtk=70;
			this._baseSpDef=70;
			this._baseSpd=70;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Wrap();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[3] = new Attack.ThunderWave();
			this.attackTree[4] = new Attack.Agility();
			this.attackTree[10] = new Attack.ThunderWave();
			this.attackTree[20] = new Attack.Agility();
			this.attackTree[35] = new Attack.Slam();
			this.attackTree[45] = new Attack.DragonRage();
			this.attackTree[55] = new Attack.HyperBeam();
 		}
	}
	
	public static class Dragonite extends Pokemon{
		public Dragonite() {
			super(149, "Dragonite", Types.DRAGON, Types.FLYING,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			this._hasEvolution = false;
			
			//Base Stats
			this._baseExp=270;
			
			this._baseHP=91;
			this._baseAtk=134;
			this._baseDef=95;
			this._baseSpAtk=100;
			this._baseSpDef=100;
			this._baseSpd=80;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Wrap();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[3] = new Attack.ThunderWave();
			this.attackTree[4] = new Attack.Agility();
			this.attackTree[10] = new Attack.ThunderWave();
			this.attackTree[20] = new Attack.Agility();
			this.attackTree[35] = new Attack.Slam();
			this.attackTree[45] = new Attack.DragonRage();
			this.attackTree[60] = new Attack.HyperBeam();
		}
	}
	
	public static class Mewtwo extends Pokemon{
		public Mewtwo() {
			super(150, "Mewtwo", Types.PSYCHIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=306;
			
			this._baseHP=106;
			this._baseAtk=110;
			this._baseDef=90;
			this._baseSpAtk=154;
			this._baseSpDef=90;
			this._baseSpd=130;
			
			this._expMult=0.8;
			
			this.attackTree[1] = new Attack.Confusion();
			this.attackTree[2] = new Attack.Disable();
			this.attackTree[3] = new Attack.Swift();
			this.attackTree[63] = new Attack.Barrier();
			this.attackTree[66] = new Attack.Psychic();
			this.attackTree[70] = new Attack.Recover();
			this.attackTree[75] = new Attack.Mist();
			this.attackTree[81] = new Attack.Amnesia();
		}
	}
	
	public static class Mew extends Pokemon{
		public Mew() {
			super(151, "Mew", Types.PSYCHIC, Types.NONE,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			
			//Base Stats
			this._baseExp=270;
			
			this._baseHP=100;
			this._baseAtk=100;
			this._baseDef=100;
			this._baseSpAtk=100;
			this._baseSpDef=100;
			this._baseSpd=100;
			
			this._expMult=1.2;
			
			this.attackTree[1] = new Attack.Pound();
			this.attackTree[10] = new Attack.Transform();
			this.attackTree[20] = new Attack.MegaPunch();
			this.attackTree[30] = new Attack.Metronome();
			this.attackTree[40] = new Attack.Psychic();
		}
	}
	
	public static class Teddiursa extends Pokemon{
		public Teddiursa() {
			super(217, "Teddiursa", Types.ROCK, Types.GROUND,14, 3, 0, 0, 7, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
			//Intentionally numbered same as Ursaring because we don't have an image yet
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[8] = new Attack.Lick();
			this.attackTree[15] = new Attack.FurySwipes();
			this.attackTree[22] = new Attack.Bite(); //Faint Attack
			this.attackTree[29] = new Attack.Rest();
			this.attackTree[36] = new Attack.Slash();
			this.attackTree[43] = new Attack.Thrash(); //Snore
		}
	}
	
	public static class Ursaring extends Pokemon{
		public Ursaring() {
			super(217, "Bruno", Types.NORMAL, Types.NONE, 30, 15, 0, 0, 7, 60, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);
		
			//Base Stats - +10 to all stats, +100 to _baseExp
			this._baseExp=275;
			
			this._baseHP=100;
			this._baseAtk=140;
			this._baseDef=85;
			this._baseSpAtk=85;
			this._baseSpDef=85;
			this._baseSpd=65;
			
			this._expMult=1.0;
			
			this.attackTree[1] = new Attack.Scratch();
			this.attackTree[2] = new Attack.Leer();
			this.attackTree[3] = new Attack.Lick();
			this.attackTree[4] = new Attack.FurySwipes();
			this.attackTree[8] = new Attack.Lick();
			this.attackTree[15] = new Attack.FurySwipes();
			this.attackTree[22] = new Attack.Bite(); //Faint Attack
			this.attackTree[29] = new Attack.Rest();
			this.attackTree[39] = new Attack.Slash();
		//	this.attackTree[49] = new Attack.Thrash(); //Snore
			this.attackTree[59] = new Attack.HyperBeam(); //Lol
		}
	}
	
	public static class MissingNo extends Pokemon{
		public MissingNo(){
			super(152, "MissingNo", Types.NONE, Types.NONE, 0, 15, 0, 0, 7, 60, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100);

			//Base Stats
			this._baseExp=172;
			
			this._baseHP=105;
			this._baseAtk=95;
			this._baseDef=80;
			this._baseSpAtk=40;
			this._baseSpDef=80;
			this._baseSpd=90;
			
			this._expMult=1.0;
			
			this.attackTree[1]=new Attack.WaterGun();
			this.attackTree[2]=new Attack.SkyAttack();
			
		}
	}
}

