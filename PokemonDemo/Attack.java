package PokemonDemo;
import java.util.Random;

public class Attack {

	private static int _damage;
	private int _initialDamage, _maxPP, _currentPP, _attackNum, _modifier, _power, _accuracy, _priority, _nonDamageType, _statusAilment;
	private Types _type;
	private boolean _physical, _highCH;
	private String _name, _effect;
	private Attack _secondAttack;
	private boolean _HM = false;
	private double _statusProb;
	private static int multi_hit = 1;
	private static final int    R_ATK_DOWN_1 = 1, 
								R_DEF_DOWN_1 = 2, 
								R_SPD_DOWN_1 = 3, 
								R_ACC_DOWN_1 = 4,
								R_SPATK_DOWN_1 = 5,
								R_SPDEF_DOWN_1 = 6,
								U_ATK_UP_1 = 7,
								U_DEF_UP_1 = 8,
								U_SPD_UP_1 = 9,
								U_ACC_UP_1 = 10,
								U_SPATK_UP_1 = 11,
								U_SPDEF_UP_1 = 12,
								TWO_TURN_PREP = 13,
								CONFUSES = 14,
								LEECH_SEED = 15,
								STATUS_CHANGE = 16,
								FOCUS_ENERGY = 17,
								R_DEF_DOWN_2 = 18,
								U_ATK_UP_2 = 19,
								U_SPD_UP_2 = 20,
								U_DEF_UP_2 = 21,
								U_EVA_UP_1 = 22,
								U_SPDEF_UP_2 = 23,
								ROAR = 24,
								DISABLE = 25,
								SOFTBOILED = 26,
								REST = 27,
								HAZE = 28,
								MIST = 29,
								LIGHT_SCREEN = 30,
								REFLECT = 31,
								TRANSFORM = 32,
								MIRROR_MOVE = 33,
								METRONOME = 34,
								WHIRLWIND = 35,
								CONVERSION = 36,
								COUNTER = 37,
								SUBSTITUTE = 38,
								MIMIC = 39,
								BIDE = 40,
								TELEPORT = 41;
	
	public Attack(int AttackNum, String name, Types type, int power, int maxPP, int currentPP, int modifier, int accuracy, int priority, boolean physical, String effect, int statusAilment, double statusProb, Attack attack2){
		_power = power;
		_name = name;
		_type = type;
		_maxPP = maxPP;
		_currentPP = currentPP;
		_attackNum = AttackNum;
		_modifier = modifier;
		_physical = physical;
		_effect = effect;
		_accuracy = accuracy;
		_priority = priority;
		_secondAttack = attack2;
		_highCH = false;
		_nonDamageType = 0;
		_statusProb=statusProb;
		_statusAilment=statusAilment;
	}
	
	public boolean isHM(){
		return _HM;
	}
	
	public void setHMTrue(){
		_HM = true;
	}
	
	public void setCHRatio(boolean b){
		_highCH = b;
	}
	
	public boolean hasHighCriticalHitRatio(){
		return _highCH;
	}
	
	public void setNonDamageType(int newType){
		_nonDamageType = newType;
	}
	
	public int getNonDamageType(){
		return _nonDamageType;
	}
	
	public String getName(){
		return _name;
	}
	
	public Types getType(){
		return _type;
	}
	
	public boolean getPhysical(){
		return _physical;
	}
	
	public int getPower(){
		return _power;
	}
	public int getAccuracy(){
		return _accuracy;
	}
	public int getPriority(){
		return _priority;
	}

	public Attack getSecondAttack(){
		return _secondAttack;
	}
	
	public int getStatusAilment(){
		return _statusAilment;
	}
	
	public double getStatusProb(){
		return _statusProb;
	}
	
	public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
		int Level, AttackPower, RandomNumber;
		double STAB, TypeMult, AttackStat, DefenseStat;
		
		//Level of the user.
		Level = user.getLevel();
		
		//Atk/Def or Special Atk/Def stat of the user.
		if (this.getPhysical()){
			if(user == nbs.getAlly()){
				AttackStat=user.getAtkStat()*nbs.getMainStatMultiplier(nbs.allyAtkStage);
				DefenseStat=receiver.getDefStat()*nbs.getMainStatMultiplier(nbs.enemyDefStage);
			}
			else{
				AttackStat=user.getAtkStat()*nbs.getMainStatMultiplier(nbs.enemyAtkStage);
				DefenseStat=receiver.getDefStat()*nbs.getMainStatMultiplier(nbs.allyDefStage);
			}
		}
		else{
			if(user == nbs.getAlly()){
				AttackStat=user.getSpAtkStat()*nbs.getMainStatMultiplier(nbs.allySpAtkStage);
				DefenseStat=receiver.getSpDefStat()*nbs.getMainStatMultiplier(nbs.enemySpDefStage);
			}
			else{
				AttackStat=user.getSpAtkStat()*nbs.getMainStatMultiplier(nbs.enemySpAtkStage);
				DefenseStat=receiver.getSpDefStat()*nbs.getMainStatMultiplier(nbs.allySpDefStage);
			}
		}
		
		AttackPower = this.getPower();
		
		//STAB: if type = type of pokemon, 1.5 multiplier (multiplied by 2 to maintain integer, again divided by 2 in total)
		//Doesn't count if the type is NONE.
		if( (this.getType()!=Types.NONE) && (user.getType1()==this.getType() || user.getType2()==this.getType()) )
			STAB=1.5;
		else
			STAB=1;
		
		
		//Starting Value, Type Multiplier
		TypeMult=1;
		
		//Multiplied by 2 to maintain integer, re-divided by 2 in the grand total.
		
		//Check both types for 4x damage.
		if (Pokemon.isSuperEffective(this.getType(), receiver.getType1(), Types.NONE))
			TypeMult=TypeMult*2;
		
		if (Pokemon.isSuperEffective(this.getType(), receiver.getType2(), Types.NONE))
			TypeMult=TypeMult*2;			
		
		if (Pokemon.isNotVeryEffective(this.getType(), receiver.getType1(),Types.NONE))
			TypeMult=TypeMult/2.0;
		
		if (Pokemon.isNotVeryEffective(this.getType(), receiver.getType2(),Types.NONE))
			TypeMult=TypeMult/2.0;
		
		if (Pokemon.hasNoEffect(this.getType(), receiver.getType1(), receiver.getType2()))
			TypeMult=0;
		
		if (user.getStatusAcro()=="BRN"){
			AttackPower=(int) (AttackPower/2);
		}
		
		//Random Number between 85 and 100.
		Random r = new Random();
		int rand = r.nextInt(16);
		RandomNumber= 85+rand;
		
		//In units of HP: 
		_damage = (int) (((( ( ( (2 * Level) + 10) / 250.0) * AttackStat * AttackPower / (1.0*DefenseStat) ) ) + 2) * STAB * TypeMult * (RandomNumber/100.0));
				
		if(_damage < 1 && !Pokemon.hasNoEffect(this.getType(), receiver.getType1(), receiver.getType2()))
			_damage = 1;
		
		//Note the +2 in the damage formula. If AttackPower=0, _damage will not be zero.
		//This line prevents non-damaging moves from doing damage.
		if (AttackPower==0)
			_damage=0;
		
		return _damage;
	}
	
	public void setDamage(int newDamage){
		_damage = newDamage;
	}
	public int getinitialDamage(){
		return _initialDamage;
	}
	
	//For use in battles.
	public void setCurrentPP(int newPP){
		_currentPP=newPP;
	}
	
	public void usePP(){
		_currentPP=_currentPP-1;
	}
	
	//For Pokemon Centers/Beds.
	public void resetPP(int newPP){
		_currentPP=_maxPP;
	}
	
	//For items that increase PP.
	public void setMaxPP(int newMaxPP){
		_maxPP=newMaxPP;
	}
	
	//For battles and save/load.
	public int getCurrentPP(){
		return _currentPP;
	}
	
	public int getMaxPP(){
		return _maxPP;
	}
	
	//Modifier is default zero. If an attack hits and a further effect is 
	//done, it is set to one. This is so the BattleScreen can display these effects. 
	public int getModifier(){
		return _modifier;
	}
	
	public String getEffect(){
		return _effect;
	}
	
	public void setModifier(int mod){
		_modifier = mod;
	}
	
	public void setEffect(String effect){
		_effect =effect;
	}
	
	//Will come in use for saving/loading/teaching.
	public int getAttackNum(){
		return _attackNum;
	}
	
	public static int get2To5Chance(){
		int num = 1;
		
		int percent = (int)(Math.random()*100)+1;
		
		if(percent <= 37.5){
			num = 1;
		}
		else if(percent <= 75){
			num = 2;
		}
		else if(percent <= 87.5){
			num = 3;
		}
		else{
			num = 4;
		}
		
		return num;
	}
	
	//==================================================
	//GAP==================================================
	//==================================================
	
	/**
	 *x Pound inflicts damage and has no secondary effect.
	 */
	public static class Pound extends Attack{
		public Pound(){
			super(1, "Pound", Types.NORMAL, 40, 35, 35, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x Karate Chop deals damage and has an increased critical hit ratio.
	 */
	public static class KarateChop extends Attack{
		public KarateChop(){
			super(2, "Karate Chop", Types.FIGHTING, 50, 25, 25, 0, 100, 0, true, "", 0, 0, null);
			this.setCHRatio(true);
		}
	}
	
	/**
	 * DoubleSlap inflicts damage and hits between 2-5 times per use. 
	 */
	public static class DoubleSlap extends Attack{
		public DoubleSlap(){
			super(3, "DoubleSlap", Types.NORMAL, 15, 10, 10, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			double rand = (Math.random()*100)+1;
			
			if(rand < 37.5){
				multi_hit = 2;
			}
			else if(rand < 75){
				multi_hit = 3;
			}
			else if(rand < 87.5){
				multi_hit = 4;
			}
			else{
				multi_hit = 5;
			}
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= multi_hit; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + multi_hit + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+multi_hit+1, ntad);
			
			return multi_hit*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Comet Punch inflicts damage, hitting 2-5 times per use. 
	 */
	public static class CometPunch extends Attack{
		public CometPunch(){
			super(4, "Comet Punch", Types.NORMAL, 18, 15, 15, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			double rand = (Math.random()*100)+1;
			
			if(rand < 37.5){
				multi_hit = 2;
			}
			else if(rand < 75){
				multi_hit = 3;
			}
			else if(rand < 87.5){
				multi_hit = 4;
			}
			else{
				multi_hit = 5;
			}
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= multi_hit; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + multi_hit + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+multi_hit+1, ntad);
			
			return multi_hit*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Mega Punch deals damage and has no secondary effect.
	 */
	public static class MegaPunch extends Attack{
		public MegaPunch(){
			super(5, "Mega Punch", Types.NORMAL, 80, 20, 20, 0, 85, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x Pay Day does damage, and scatters coins on the ground with a value equal to five times
	 * the user's level for each time it's used.
	 */
	public static class PayDay extends Attack{
		public PayDay(){
			super(6, "Pay Day", Types.NORMAL, 40, 20, 20, 0, 100, 0, true, "Coins scattered everywhere!", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			nbs.incrementPayDay(user.getLevel()*5);
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Fire Punch does damage and has a 10% chance of burning the opponent.
	 */
	public static class FirePunch extends Attack{
		public FirePunch(){
			super(7, "Fire Punch", Types.FIRE, 75, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			String burned = NewTurnAction.Bernoulli(10);
			if(burned == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was burned!");
						nbs.getEnemy().setStatus(NewTurnAction.BURNED);
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was burned!");
						nbs.getAlly().setStatus(NewTurnAction.BURNED);
					}
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Ice Punch does damage and has a 10% chance of freezing the target.
	 */
	public static class IcePunch extends Attack{
		public IcePunch(){
			super(8, "Ice Punch", Types.ICE, 75, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			String iced = NewTurnAction.Bernoulli(10);
			if(iced == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was frozen!");
						nbs.getEnemy().setStatus(NewTurnAction.FROZEN);
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was frozen!");
						nbs.getAlly().setStatus(NewTurnAction.FROZEN);
					}
					else{
						this.setEffect("");
					}
				}
			}
			else{
				this.setEffect("");
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x ThunderPunch does damage and has a 10% chance of paralyzing the opponent.
	 */
	public static class ThunderPunch extends Attack{
		public ThunderPunch(){
			super(9, "ThunderPunch", Types.ELECTRIC, 75, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			String thunder = NewTurnAction.Bernoulli(10);
			if(thunder == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was paralyzed!");
						nbs.getEnemy().setStatus(NewTurnAction.PARALYZED);
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was paralyzed!");
						nbs.getAlly().setStatus(NewTurnAction.PARALYZED);
					}
					else{
						this.setEffect("");
					}
				}
			}
			else{
				this.setEffect("");
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Scratch inflicts damage and has no secondary effect.
	 */
	public static class Scratch extends Attack{
		public Scratch(){
			super(10, "Scratch", Types.NORMAL, 40, 35, 35, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x ViceGrip inflicts damage and has no secondary effect.
	 */
	public static class ViceGrip extends Attack{
		public ViceGrip(){
			super(11, "ViceGrip", Types.NORMAL, 55, 30, 30, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x Guillotine has 30% accuracy and inflicts damage equal to the target's current HP. 
	 */
	public static class Guillotine extends Attack{
		public Guillotine(){
			super(12, "Guillotine", Types.NORMAL, 9000, 5, 5, 0, 30, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(receiver.getSpeed() > user.getSpeed()){
			  	NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed!","");
			  	nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,ntaNothing);
			  	return 0;
			}
			else{
				NewTurnAction ohko = new NewTurnAction.TA_DisplayEffect(nbs, "One-Hit KO!","");
				nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ohko);
				return receiver.getCurrentHP();
			}
		}
	}

	/**
	 * Razor Wind does nothing on the turn it is selected, other than state that the user created a whirlwind. 
	 * On the following turn, Razor Wind inflicts damage, PP is deducted, and it will count as the last move used. 
	 * Once Razor Wind is selected, the user will be unable to switch out until Razor Wind has been disrupted or fully executed.
	 */
	public static class RazorWind extends Attack{
		public RazorWind(){
			super(13, "Razor Wind", Types.NORMAL, 80, 10, 10, 0, 100, 0, false, "", 0, 0, null);
			//this.setNonDamageType(TWO_TURN_PREP);
		}
	}
	public static class RazorWind2 extends Attack{
		public RazorWind2(){
			super(0, "Razor Wind", Types.NORMAL, 80, 10, 10, 0, 100, 0, true, "", 0, 0, null);
			this.setCHRatio(true);
		}
	}

	/**
	 *x Swords Dance increases the user's Attack stat by two stages.
	 */
	public static class SwordsDance extends Attack{
		public SwordsDance(){
			super(14, "Swords Dance", Types.NORMAL, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_ATK_UP_2);
		}
	}

	/**
	 *x Cut inflicts damage and has no secondary effect.
	 */
	public static class Cut extends Attack{
		public Cut(){
			super(15, "Cut", Types.NORMAL, 50, 30, 30, 0, 95, 0, true, "", 0, 0, null);
			this.setHMTrue();
		}
	}

	/**
	 *x  In Generation I, this move is Normal-type. Gust deals damage without a secondary effect.
	 */
	public static class Gust extends Attack{
		public Gust(){
			super(16, "Gust", Types.NORMAL, 40, 35, 35, 0, 100, 0, false, "", 0, 0, null);
		}
	}

	/**
	 *x Wing Attack inflicts damage and has no secondary effect.
	 */
	public static class WingAttack extends Attack{
		public WingAttack(){
			super(17, "Wing Attack", Types.FLYING, 60, 35, 35, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x Whirlwind can be used to automatically end wild Pokémon battles and has normal priority. 
	 * Even if a wild Pokémon uses the move, the battle will end. However, it has no effect in a trainer battle.
	 */
	public static class Whirlwind extends Attack{
		public Whirlwind(){
			super(18, "Whirlwind", Types.NORMAL, 0, 20, 20, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(WHIRLWIND);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			if(nbs.isWild()){
				NewTurnAction flee = new NewTurnAction.TA_DisplayEffect(nbs, receiver.getName() + " was blown away!", "");
				NewTurnAction over = new NewTurnAction.TA_EndBattle(nbs);
				nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,flee);
				nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+2,over);
			}
			else if(nbs.isTrainer()){
			  	NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed!","");
			  	nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,ntaNothing);
			}
			
			return 0;
		}
	}

	/**
	 *x On the turn that Fly is selected, the user will fly up high, where the only attacks it cannot avoid are Bide, Swift, Thunder, and Transform. 
	 * On the following turn, Fly will inflict damage, PP will be deducted from it, and it will count as the last move used. 
	 * Once Fly is selected, the user will be unable to switch out unless Fly is disrupted or fully executed.
	 */
	public static class Fly extends Attack{
		public Fly(){
			super(19, "Fly", Types.FLYING, 90, 15, 15, 0, 95, 0, true, "", 0, 0, null);
			this.setHMTrue();
			//this.setNonDamageType(TWO_TURN_PREP);
		}
	}
	public static class Fly2 extends Attack{
		public Fly2(){
			super(19, "Fly", Types.FLYING, 90, 15, 15, 0, 95, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(user == nbs.getAlly()){
				nbs.setAllyFlying(false);
				nbs.setNextTurnAction(null);
			}
			else{
				nbs.setEnemyFlying(false);
				nbs.setNextEnemyTurnAction(null);
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *  Bind does damage for 2-5 turns. During this turn duration, the target will be unable to attack.
	 */
	public static class Bind extends Attack{
		public Bind(){
			super(20, "Bind", Types.NORMAL, 15, 20, 20, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			if(user == nbs.getAlly()){
				
				SysOut.print(":"+nbs.getNumAllyMultiTurn());
				switch(nbs.getNumAllyMultiTurn()){
				case 0: 
					nbs.setEnemyBound(true);
					nbs.setNumAllyMultiTurn(Attack.get2To5Chance());
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					SysOut.print("0->:"+nbs.getNumAllyMultiTurn());
					break;
				case 4:
					nbs.setNumAllyMultiTurn(3);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("4->:"+nbs.getNumAllyMultiTurn());
					break;
				case 3:
					nbs.setNumAllyMultiTurn(2);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("3->:"+nbs.getNumAllyMultiTurn());
					break;
				case 2:
					nbs.setNumAllyMultiTurn(1);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("2->:"+nbs.getNumAllyMultiTurn());
					break;
				case 1:
					nbs.setNumAllyMultiTurn(-1);
					this.setCurrentPP(this.getCurrentPP()+1);
					nbs.setNextTurnAction(null);
					nbs.setEnemyBound(false);
					SysOut.print("BIND OVER:::"+nbs.getNumAllyMultiTurn());
					break;
				}
			}
			else{
				switch(nbs.getNumEnemyMultiTurn()){
				case 0:
					nbs.setAllyBound(true);
					nbs.setNumEnemyMultiTurn(Attack.get2To5Chance());
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					break;
				case 4:
					nbs.setNumEnemyMultiTurn(3);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 3:
					nbs.setNumEnemyMultiTurn(2);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 2:
					nbs.setNumEnemyMultiTurn(1);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 1:
					nbs.setAllyBound(false);
					nbs.setNumEnemyMultiTurn(-1);
					nbs.setNextEnemyTurnAction(null);
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}


	/**
	 *x Slam inflicts damage and has no secondary effect.
	 */
	public static class Slam extends Attack{
		public Slam(){
			super(21, "Slam", Types.NORMAL, 80, 20, 20, 0, 75, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x Vine Whip inflicts damage, has no secondary effect,
	 */
	public static class VineWhip extends Attack{
		public VineWhip(){
			super(22, "Vine Whip", Types.GRASS, 35, 15, 15, 0, 100, 0, true, "", 0, 0, null);			
		}
	}

	/**
	 *x Stomp inflicts damage and has a 30% chance of causing the target to flinch.
	 */
	public static class Stomp extends Attack{
		public Stomp(){
			super(23, "Stomp", Types.NORMAL, 65, 20, 20, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.setEnemyFlinch(true);
				}
				else{
					nbs.setAllyFlinch(true);
				}
			}
			int mod = 1;
			if(user == nbs.getAlly() && nbs.isEnemyMinimized()){
				mod = 2;
			}
			if(user == nbs.getEnemy() && nbs.isAllyMinimized()){
				mod = 2;
			}
		
			return mod*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Double Kick does damage, hitting twice per use.
	 */
	public static class DoubleKick extends Attack{
		public DoubleKick(){
			super(24, "Double Kick", Types.FIGHTING, 30, 30, 30, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= 2; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + 2 + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+3, ntad);
			
			return 2*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Mega Kick deals damage and has no secondary effect.
	 */
	public static class MegaKick extends Attack{
		public MegaKick(){
			super(25, "Mega Kick", Types.NORMAL, 120, 5, 5, 0, 75, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Jump Kick does damage. If this attack misses, the user will crash and take damage (1/8 of the damage it would've otherwise dealt).
	 * Used against a Ghost-type, it always counts as a miss.
	 */
	public static class JumpKick extends Attack{
		public JumpKick(){//pow = 100, acc = 95
			super(26, "Jump Kick", Types.FIGHTING, 1000, 10, 10, 0, 5, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x Rolling Kick does damage and has a 30% chance of causing the target to flinch.
	 */
	public static class RollingKick extends Attack{
		public RollingKick(){
			super(27, "Rolling Kick", Types.FIGHTING, 60, 15, 15, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.setEnemyFlinch(true);
				}
				else{
					nbs.setAllyFlinch(true);
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Sand-Attack decreases the target's Accuracy stat by one stage. (Normal type in Gen 1)
	 */
	public static class SandAttack extends Attack{
		public SandAttack(){
			super(28, "Sand-Attack", Types.NORMAL, 0, 15, 15, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(R_ACC_DOWN_1);
		}
	}

	/**
	 *x Headbutt does damage and has a 30% chance of causing the target to flinch.
	 */
	public static class Headbutt extends Attack{
		public Headbutt(){
			super(29, "Headbutt", Types.NORMAL, 70, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.setEnemyFlinch(true);
				}
				else{
					nbs.setAllyFlinch(true);
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Horn Attack deals damage and has no secondary effect.
	 */
	public static class HornAttack extends Attack{
		public HornAttack(){
			super(30, "Horn Attack", Types.NORMAL, 65, 25, 25, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Fury Attack inflicts damage, hitting 2-5 times per use.
	 */
	public static class FuryAttack extends Attack{
		public FuryAttack(){
			super(31, "Fury Attack", Types.NORMAL, 15, 20, 20, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			double rand = (Math.random()*100)+1;
			
			if(rand < 37.5){
				multi_hit = 2;
			}
			else if(rand < 75){
				multi_hit = 3;
			}
			else if(rand < 87.5){
				multi_hit = 4;
			}
			else{
				multi_hit = 5;
			}
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= multi_hit; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + multi_hit + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+multi_hit+1, ntad);
			
			return multi_hit*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Horn Drill has 30% accuracy and inflicts damage equal to the target's current HP.
	 */
	public static class HornDrill extends Attack{
		public HornDrill(){
			super(32, "Horn Drill", Types.NORMAL, 0, 5, 5, 0, 30, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(receiver.getSpeed() > user.getSpeed()){
			  	NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed!","");
			  	nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,ntaNothing);
			  	return 0;
			}
			else{
				NewTurnAction ohko = new NewTurnAction.TA_DisplayEffect(nbs, "One-Hit KO!","");
				nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ohko);
//				if(user == nbs.getAlly()){
					return receiver.getCurrentHP();
//				}
//				else{
//					return user.getCurrentHP();
//				}
			}
		}
	}

	/**
	 *x Tackle deals damage and has no secondary effect.
	 */
	public static class Tackle extends Attack{
		public Tackle(){
			super(33, "Tackle", Types.NORMAL, 50, 30, 30, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Body Slam inflicts damage and has a 30% chance of paralyzing the target.
	 */
	public static class BodySlam extends Attack{
		public BodySlam(){
			super(34, "Body Slam", Types.NORMAL, 85, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			String paralyzed = NewTurnAction.Bernoulli(30);
			if(paralyzed == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was paralyzed!");
						nbs.getEnemy().setStatus(NewTurnAction.PARALYZED);
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was paralyzed!");
						nbs.getAlly().setStatus(NewTurnAction.PARALYZED);
					}
					else{
						this.setEffect("");
					}
				}
			}
			else{
				this.setEffect("");
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Wrap inflicts damage for 2-5 turns. During this turn duration, the target will be unable to attack
	 */
	public static class Wrap extends Attack{
		public Wrap(){
			super(35, "Wrap", Types.NORMAL, 15, 20, 20, 0, 90, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			if(user == nbs.getAlly()){
				
				SysOut.print(":"+nbs.getNumAllyMultiTurn());
				switch(nbs.getNumAllyMultiTurn()){
				case -1: 
					nbs.setEnemyBound(true);
					nbs.setNumAllyMultiTurn(Attack.get2To5Chance());
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					SysOut.print("0->:"+nbs.getNumAllyMultiTurn());
					break;
				case 4:
					nbs.setNumAllyMultiTurn(3);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("4->:"+nbs.getNumAllyMultiTurn());
					break;
				case 3:
					nbs.setNumAllyMultiTurn(2);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("3->:"+nbs.getNumAllyMultiTurn());
					break;
				case 2:
					nbs.setNumAllyMultiTurn(1);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("2->:"+nbs.getNumAllyMultiTurn());
					break;
				case 1:
					nbs.setNumAllyMultiTurn(-1);
					this.setCurrentPP(this.getCurrentPP()+1);
					nbs.setNextTurnAction(null);
					nbs.setEnemyBound(false);
					SysOut.print("BIND OVER:::"+nbs.getNumAllyMultiTurn());
					break;
				default:
					nbs.setEnemyBound(false);
					nbs.setNumAllyMultiTurn(-1);
					nbs.setNextTurnAction(null);
					this.setCurrentPP(this.getCurrentPP()+1);
				}
			}
			else{
				switch(nbs.getNumEnemyMultiTurn()){
				case -1:
					SysOut.print("Start->:"+nbs.getNumEnemyMultiTurn());
					nbs.setAllyBound(true);
					nbs.setNumEnemyMultiTurn(Attack.get2To5Chance());
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					break;
				case 4:
					SysOut.print("4->:"+nbs.getNumEnemyMultiTurn());
					nbs.setNumEnemyMultiTurn(3);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 3:
					SysOut.print("3->:"+nbs.getNumEnemyMultiTurn());
					nbs.setNumEnemyMultiTurn(2);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 2:
					SysOut.print("2->:"+nbs.getNumEnemyMultiTurn());
					nbs.setNumEnemyMultiTurn(1);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 1:
					SysOut.print("End->:"+nbs.getNumEnemyMultiTurn());
					nbs.setAllyBound(false);
					nbs.setNumEnemyMultiTurn(-1);
					nbs.setNextEnemyTurnAction(null);
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				default:
					nbs.setAllyBound(false);
					nbs.setNumEnemyMultiTurn(-1);
					nbs.setNextEnemyTurnAction(null);
					this.setCurrentPP(this.getCurrentPP()+1);
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Take Down does damage, and the user receives recoil damage equal to 25% of the damage done to the opponent.
	 */
	public static class TakeDown extends Attack{
		public TakeDown(){
			super(36, "Take Down", Types.NORMAL, 90, 20, 20, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user,  Pokemon receiver){
			int damage = super.getDamage(nbs, user, receiver);
			
			NewTurnAction ntaR = new NewTurnAction.TA_Recoil(nbs, user, 0.25, damage);
			nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ntaR);
			
			return damage;
		}
	}

	/**
	 * Thrash has a Power of 90 and has 20 PP, it does damage for 3-4 (chosen randomly) turns.
	 */
	public static class Thrash extends Attack{
		public Thrash(){
			super(37, "Thrash", Types.NORMAL, 120, 10, 10, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(user == nbs.getAlly()){
				
				SysOut.print(":"+nbs.getNumAllyMultiTurn());
				switch(nbs.getNumAllyMultiTurn()){
				case 0: 
					nbs.setNumAllyMultiTurn((int)(Math.random()*2)+2);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					SysOut.print("0->:"+nbs.getNumAllyMultiTurn());
					break;
				case 4:
					nbs.setNumAllyMultiTurn(3);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("4->:"+nbs.getNumAllyMultiTurn());
					break;
				case 3:
					nbs.setNumAllyMultiTurn(2);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("3->:"+nbs.getNumAllyMultiTurn());
					break;
				case 2:
					nbs.setNumAllyMultiTurn(1);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("2->:"+nbs.getNumAllyMultiTurn());
					break;
				case 1:
					nbs.setNumAllyMultiTurn(0);
					this.setCurrentPP(this.getCurrentPP()+1);
					nbs.setNextTurnAction(null);
					nbs.setAllyConfusion(true);
					break;
				}
			}
			else{
				switch(nbs.getNumEnemyMultiTurn()){
				case 0:
					nbs.setNumEnemyMultiTurn((int)(Math.random()*2)+2);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					break;
				case 4:
					nbs.setNumEnemyMultiTurn(3);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 3:
					nbs.setNumEnemyMultiTurn(2);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 2:
					nbs.setNumEnemyMultiTurn(1);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 1:
					nbs.setNumEnemyMultiTurn(0);
					nbs.setNextEnemyTurnAction(null);
					this.setCurrentPP(this.getCurrentPP()+1);
					nbs.setEnemyConfusion(true);
					break;
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *Double-Edge inflicts damage and the user receives recoil damage equal to 25% of the damage done to the target. 
	 */
	public static class DoubleEdge extends Attack{
		public DoubleEdge(){
			super(38, "Double-Edge", Types.NORMAL, 120, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user,  Pokemon receiver){
			int damage = super.getDamage(nbs, user, receiver);
			
			NewTurnAction ntaR = new NewTurnAction.TA_Recoil(nbs, user, 0.25, damage);
			nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ntaR);
			
			return damage;
		}
	}

	/**
	 *x Tail Whip decreases the target's Defense stat by one stage.
	 */
	public static class TailWhip extends Attack{
		public TailWhip(){
			super(39, "Tail Whip", Types.NORMAL, 0, 30, 30, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(R_DEF_DOWN_1);
		}
	}

	/**
	 *x Poison Sting inflicts damage and has a 30% chance of poisoning the target.
	 */
	public static class PoisonSting extends Attack{
		public PoisonSting(){
			super(40, "Poison Sting", Types.POISON, 15, 35, 35, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			String poisoned = NewTurnAction.Bernoulli(30);
			if(poisoned == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was poisoned!");
						nbs.getEnemy().setStatus(NewTurnAction.POISONED);
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was poisoned!");
						nbs.getAlly().setStatus(NewTurnAction.POISONED);
					}
					else{
						this.setEffect("");
					}
				}
			}
			else{
				this.setEffect("");
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Twineedle inflicts damage, and has a 20% chance of poisoning the target. 
	 * Twineedle hits twice per use. 
	 */
	public static class Twineedle extends Attack{
		public Twineedle(){
			super(41, "Twineedle", Types.BUG, 25, 20, 20, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= 2; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + 2 + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+3, ntad);
			
			String poisoned = NewTurnAction.Bernoulli(20);
			if(poisoned == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was poisoned!");
						nbs.getEnemy().setStatus(NewTurnAction.POISONED);
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was poisoned!");
						nbs.getAlly().setStatus(NewTurnAction.POISONED);
					}
					else{
						this.setEffect("");
					}
				}
			}
			else{
				this.setEffect("");
			}
			
			return 2*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Pin Missile inflicts damage, hitting the target between 2-5 times per use.
	 */
	public static class PinMissile extends Attack{
		public PinMissile(){
			super(42, "Pin Missile", Types.BUG, 14, 20, 20, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			double rand = (Math.random()*100)+1;
			
			if(rand < 37.5){
				multi_hit = 2;
			}
			else if(rand < 75){
				multi_hit = 3;
			}
			else if(rand < 87.5){
				multi_hit = 4;
			}
			else{
				multi_hit = 5;
			}
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= multi_hit; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + multi_hit + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+multi_hit+1, ntad);
			
			return multi_hit*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Leer decreases the target's Defense stat by one stage.
	 */
	public static class Leer extends Attack{
		public Leer(){
			super(43, "Leer", Types.NORMAL, 0, 30, 30, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(R_DEF_DOWN_1);
		}
	}

	/**
	 *x Bite inflicts damage and has a 10% chance of causing the target to flinch.
	 */
	public static class Bite extends Attack{
		public Bite(){
			super(44, "Bite", Types.DARK, 60, 25, 25, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.setEnemyFlinch(true);
				}
				else{
					nbs.setAllyFlinch(true);
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Growl decreases the opponent's Attack by 1 level.
	 */
	public static class Growl extends Attack{
		public Growl(){
			super(45, "Growl", Types.NORMAL, 0, 40, 40, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(R_ATK_DOWN_1);
		}
	}

	/**
	 *x? Roar can be used to automatically end wild Pokémon battles.
	 */
	public static class Roar extends Attack{
		public Roar(){
			super(46, "Roar", Types.NORMAL, 0, 20, 20, 0, 100, -6, false, "", 0, 0, null);
			this.setNonDamageType(ROAR);
		}
	}

	/**
	 *x Sing puts the target to sleep.
	 */
	public static class Sing extends Attack{
		public Sing(){
			super(47, "Sing", Types.NORMAL, 0, 15, 15, 0, 55, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 *x Supersonic causes the target to become confused.
	 */
	public static class Supersonic extends Attack{
		public Supersonic(){
			super(48, "Supersonic", Types.NORMAL, 0, 20, 20, 0, 55, 0, false, "", 0, 0, null);
			this.setNonDamageType(CONFUSES);
		}
	}

	/**
	 *x SonicBoom deals 20 damage, regardless of the user's type, the opponent's type, and any other effects.
	 */
	public static class SonicBoom extends Attack{
		public SonicBoom(){
			super(49, "SonicBoom", Types.NORMAL, 1, 20, 20, 0, 90, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon ally, Pokemon enemy){
			return 20;
		}
	}

	/**
	 * Disable randomly selects one move that is in the target's current move set and whose 
	 * current PP is greater than 0, and gives it a randomly chosen disable duration length of 0-6, 
	 * which is reduced by 1 each time the Pokémon attempts to execute an attack. 
	 */
	public static class Disable extends Attack{
		public Disable(){
			super(50, "Disable", Types.NORMAL, 0, 20, 20, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(DISABLE);
		}
	}

	/**
	 *x Acid does damage and has a 10% chance of lowering the target's Defense by one stage.
	 */
	public static class Acid extends Attack{
		public Acid(){
			super(51, "Acid", Types.POISON, 40, 30, 30, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.enemyDefStage > -6){
						this.setEffect("Enemy " + receiver.getName() + "'s Defense fell!");
						nbs.enemyDefStage = nbs.enemyDefStage-1;
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.allyDefStage > -6){
						this.setEffect(receiver.getName() +"'s Defense fell!");
						nbs.allyDefStage = nbs.allyDefStage-1;
					}
					else{
						this.setEffect("");
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Ember does damage and has a 10% chance of burning the target.
	 */
	public static class Ember extends Attack{
		public Ember(){
			super(52, "Ember", Types.FIRE, 40, 25, 25, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(receiver.getStatus() == 0){
					receiver.setStatus(4);
					if(user == nbs.getAlly()){
						this.setEffect("Enemy "+ receiver.getName() + " was burned!");
					}
					else{
						this.setEffect(receiver.getName() + " was burned!");
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Flamethrower does damage and has a 10% chance of burning the target.
	 */
	public static class Flamethrower extends Attack{
		public Flamethrower(){
			super(53, "Flamethrower", Types.FIRE, 95, 15, 15, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(receiver.getStatus() == 0){
					receiver.setStatus(4);
					if(user == nbs.getAlly()){
						this.setEffect("Enemy "+ receiver.getName() + " was burned!");
					}
					else{
						this.setEffect(receiver.getName() + " was burned!");
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Mist prevents the effects of primary stat modifications inflicted by the opponent.
	 */
	public static class Mist extends Attack{
		public Mist(){
			super(54, "Mist", Types.ICE, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(MIST);
		}
	}

	/**
	 *x Water Gun inflicts damage and has no secondary effect.
	 */
	public static class WaterGun extends Attack{
		public WaterGun(){
			super(55, "Water Gun", Types.WATER, 40, 25, 25, 0, 100, 0, false, "", 0, 0, null);
		}
	}

	/**
	 *x Hydro Pump inflicts damage and has no secondary effect.
	 */
	public static class HydroPump extends Attack{
		public HydroPump(){
			super(56, "Hydro Pump", Types.WATER, 120, 5, 5, 0, 80, 0, false, "", 0, 0, null);
		}
	}

	/**
	 *x Surf inflicts damage and has no secondary effect.
	 */
	public static class Surf extends Attack{
		public Surf(){
			super(57, "Surf", Types.WATER, 95, 15, 15, 0, 100, 0, false, "", 0, 0, null);
			this.setHMTrue();
		}
	}

	/**
	 *x Ice Beam inflicts damage and has a 10% chance of freezing the target
	 */
	public static class IceBeam extends Attack{
		public IceBeam(){
			super(58, "Ice Beam", Types.ICE, 95, 10, 10, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(receiver.getStatus() == 0){
					receiver.setStatus(5);
					if(user == nbs.getAlly()){
						this.setEffect("Enemy "+ receiver.getName() + " was frozen!");
					}
					else{
						this.setEffect(receiver.getName() + " was frozen!");
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Blizzard does damage and has a 10% chance of freezing the target.
	 */
	public static class Blizzard extends Attack{
		public Blizzard(){
			super(59, "Blizzard", Types.ICE, 120, 5, 5, 0, 70, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(receiver.getStatus() == 0){
					receiver.setStatus(5);
					if(user == nbs.getAlly()){
						this.setEffect("Enemy "+ receiver.getName() + " was frozen!");
					}
					else{
						this.setEffect(receiver.getName() + " was frozen!");
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Psybeam does damage and has a 10% chance of confusing the target.
	 */
	public static class Psybeam extends Attack{
		public Psybeam(){
			super(60, "Psybeam", Types.PSYCHIC, 65, 20, 20, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly() && !nbs.isEnemyConfused()){
					nbs.setEnemyConfusion(true);
					this.setEffect("Enemy " + receiver.getName()+ " became confused!");
				}
				else if(user == nbs.getEnemy() && !nbs.isAllyConfused()){
					nbs.setAllyConfusion(true);
					this.setEffect(receiver.getName() + " became confused!");
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x BubbleBeam does damage and has a 10% chance of lowering the target's speed by one stage.
	 */
	public static class BubbleBeam extends Attack{
		public BubbleBeam(){
			super(61, "BubbleBeam", Types.WATER, 65, 20, 20, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly()){
					receiver = nbs.getEnemy();
					if(nbs.enemySpdStage > -6){
						this.setEffect("Enemy " + receiver.getName() + "'s Speed fell!");
						nbs.enemySpdStage = nbs.enemySpdStage-1;
					}
				}
				else{
					if(nbs.allySpdStage > -6){
						receiver = nbs.getAlly();
						this.setEffect(receiver.getName() +"'s Speed fell!");
						nbs.allySpdStage = nbs.allySpdStage-1;
					}
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Aurora Beam does damage and has a 10% chance of lowering the target's Attack stat by one stage.
	 */
	public static class AuroraBeam extends Attack{
		public AuroraBeam(){
			super(62, "Aurora Beam", Types.ICE, 65, 20, 20, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.enemyAtkStage > -6){
						this.setEffect("Enemy " + receiver.getName() + "'s Attack fell!");
						nbs.enemyAtkStage = nbs.enemyAtkStage-1;
					}
				}
				else{
					if(nbs.allyAtkStage > -6){
						this.setEffect(receiver.getName() +"'s Attack fell!");
						nbs.allyAtkStage = nbs.allyAtkStage-1;
					}
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Hyper Beam inflicts damage. A recharge turn is required on the turn after damage is done, 
	 * during which no action may be performed.
	 */
	public static class HyperBeam extends Attack{
		public HyperBeam(){
			super(63, "Hyper Beam", Types.NORMAL, 150, 5, 5, 0, 90, 0, false, "", 0, 0, new Attack.HyperBeam2());
		}
	}
	public static class HyperBeam2 extends Attack{
		public HyperBeam2(){
			super(0, "Hyper Beam", Types.NONE, 0, 0, 0, 0, 0, 0, false, " must recharge!", 0, 0, null);
				
		}
	}

	/**
	 *x Peck inflicts damage and has no secondary effect.
	 */
	public static class Peck extends Attack{
		public Peck(){
			super(64, "Peck", Types.FLYING, 35, 35, 35, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x Drill Peck inflicts damage and has no secondary effect.
	 */
	public static class DrillPeck extends Attack{
		public DrillPeck(){
			super(65, "Drill Peck", Types.FLYING, 80, 20, 20, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Submission does damage, and the user receives recoil damage 
	 * equal to 25% of the damage done to the target.
	 */
	public static class Submission extends Attack{
		public Submission(){
			super(66, "Submission", Types.FIGHTING, 80, 25, 25, 0, 80, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user,  Pokemon receiver){
			int damage = super.getDamage(nbs, user, receiver);
			
			NewTurnAction ntaR = new NewTurnAction.TA_Recoil(nbs, user, 0.25, damage);
			nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ntaR);
			
			return damage;
		}
	}

	/**
	 *x Low Kick has a power of 50 and has a 30% chance of causing the target to flinch.
	 */
	public static class LowKick extends Attack{
		public LowKick(){
			super(67, "Low Kick", Types.FIGHTING, 50, 20, 20, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.setEnemyFlinch(true);
				}
				else{
					nbs.setAllyFlinch(true);
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * If the last amount of damage done before the use of Counter is greater than 0 and
	 * was dealt by a Normal-type or Fighting-type attack, Counter will do twice as much damage to the opponent. 
	 */
	public static class Counter extends Attack{
		public Counter(){
			super(68, "Counter", Types.FIGHTING, 0, 20, 20, 0, 100, -5, true, "", 0, 0, null);
			this.setNonDamageType(COUNTER);
		}
	}

	/**
	 *x Seismic Toss inflicts damage equal to the user's level,
	 */
	public static class SeismicToss extends Attack{
		public SeismicToss(){
			super(69, "Seismic Toss", Types.FIGHTING, 1, 20, 20, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			return user.getLevel();
		}
	}

	/**
	 *x Strength inflicts damage and has no secondary effect.
	 */
	public static class Strength extends Attack{
		public Strength(){
			super(70, "Strength", Types.NORMAL, 80, 15, 15, 0, 100, 0, true, "", 0, 0, null);
			this.setHMTrue();
		}
	}

	/**
	 * Absorb does damage, and up to 50% of the damage done to the target is restored to the user, 
	 */
	public static class Absorb extends Attack{
		public Absorb(){
			super(71, "Absorb", Types.GRASS, 20, 25, 25, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int health = super.getDamage(nbs, user, receiver);
			NewTurnAction ntaGain = new NewTurnAction.TA_GainHP(nbs, user, user.getName() + " absorbed HP!", (int)(health*0.5)+1);
			nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ntaGain);
			return health;
		}
	}

	/**
	 * Mega Drain inflicts damage, and 50% of the damage dealt to the target is restored to the user.
	 */
	public static class MegaDrain extends Attack{
		public MegaDrain(){
			super(72, "Mega Drain", Types.GRASS, 40, 15, 15, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int health = super.getDamage(nbs, user, receiver);
			NewTurnAction ntaGain = new NewTurnAction.TA_GainHP(nbs, user, user.getName() + " drained HP from " + receiver.getName(), (int)(health*0.5)+1);
			nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ntaGain);
			return health;
		}
	}

	/**
	 *? Leech Seed plants a seed on the target. At the end of each turn that the target is under the effect of Leech Seed,
	 *  1/16 of the target's HP will be drained, and the same amount of HP will be restored to your active Pokémon.
	 */
	public static class LeechSeed extends Attack{
		public LeechSeed(){
			super(73, "Leech Seed", Types.GRASS, 0, 10, 10, 0, 90, 0, false, "", 0, 0, null);
			this.setNonDamageType(LEECH_SEED);
		}
	}

	/**
	 *x Growth increases the user's Special Attack stat by one stage.
	 */
	public static class Growth extends Attack{
		public Growth(){
			super(74, "Growth", Types.NORMAL, 0, 40, 40, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_SPATK_UP_1);
		}
	}

	/**
	 *x Razor Leaf deals damage and has an increased critical hit ratio.
	 */
	public static class RazorLeaf extends Attack{
		public RazorLeaf(){
			super(75, "Razor Leaf", Types.GRASS, 55, 25, 25, 0, 95, 0, true, "", 0, 0, null);
			this.setCHRatio(true);
		}
	}

	/**
	 *x SolarBeam does nothing on the turn it is selected, other than state that the user has taken in sunlight. 
	 * On the following turn, SolarBeam will inflict damage
	 */
	public static class SolarBeam extends Attack{
		public SolarBeam(){
			super(76, "Solar Beam", Types.GRASS, 120, 10, 10, 0, 100, 0, false, "", 0, 0, null);
			//this.setNonDamageType(TWO_TURN_PREP);
		}
	}
	public static class SolarBeam2 extends  Attack{
		public SolarBeam2(){
			super(76, "Solar Beam", Types.GRASS, 120, 10, 10, 0, 100, 0, false, "", 0, 0, null);
		}
	}

	/**
	 *x PoisonPowder poisons the target.
	 */
	public static class PoisonPowder extends Attack{
		public PoisonPowder(){
			super(77, "PoisonPowder", Types.POISON, 0, 35, 35, 0, 75, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 *x Stun Spore paralyzes the target.
	 */
	public static class StunSpore extends Attack{
		public StunSpore(){
			super(78, "Stun Spore", Types.GRASS, 0, 30, 30, 0, 75, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 *x Sleep Powder puts the target to sleep.
	 */
	public static class SleepPowder extends Attack{
		public SleepPowder(){
			super(79, "Sleep Powder", Types.GRASS, 0, 15, 15, 0, 75, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 * Petal Dance inflicts damage for 3 to 4 turns (chosen at random).
	 */
	public static class PetalDance extends Attack{
		public PetalDance(){
			super(80, "Petal Dance", Types.GRASS, 120, 10, 10, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(user == nbs.getAlly()){
				
				SysOut.print(":"+nbs.getNumAllyMultiTurn());
				switch(nbs.getNumAllyMultiTurn()){
				case 0: 
					nbs.setNumAllyMultiTurn((int)(Math.random()*2)+2);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					SysOut.print("0->:"+nbs.getNumAllyMultiTurn());
					break;
				case 4:
					nbs.setNumAllyMultiTurn(3);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("4->:"+nbs.getNumAllyMultiTurn());
					break;
				case 3:
					nbs.setNumAllyMultiTurn(2);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("3->:"+nbs.getNumAllyMultiTurn());
					break;
				case 2:
					nbs.setNumAllyMultiTurn(1);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("2->:"+nbs.getNumAllyMultiTurn());
					break;
				case 1:
					nbs.setNumAllyMultiTurn(0);
					this.setCurrentPP(this.getCurrentPP()+1);
					nbs.setNextTurnAction(null);
					nbs.setAllyConfusion(true);
					break;
				}
			}
			else{
				switch(nbs.getNumEnemyMultiTurn()){
				case 0:
					nbs.setNumEnemyMultiTurn((int)(Math.random()*2)+2);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					break;
				case 4:
					nbs.setNumEnemyMultiTurn(3);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 3:
					nbs.setNumEnemyMultiTurn(2);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 2:
					nbs.setNumEnemyMultiTurn(1);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 1:
					nbs.setNumEnemyMultiTurn(0);
					nbs.setNextEnemyTurnAction(null);
					this.setCurrentPP(this.getCurrentPP()+1);
					nbs.setEnemyConfusion(true);
					break;
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x String Shot decreases the target's Speed stat by one stage.
	 */
	public static class StringShot extends Attack{
		public StringShot(){
			super(81, "String Shot", Types.BUG, 0, 40, 40, 0, 95, 0, false, "", 0, 0, null);
			this.setNonDamageType(R_SPD_DOWN_1);
		}
	}

	/**
	 *x Dragon Rage inflicts damage and will always deduct 40 HP from the target's current HP. 
	 * It has no secondary effects and does not take type into account.
	 */
	public static class DragonRage extends Attack{
		public DragonRage(){
			super(82, "Dragon Rage", Types.DRAGON, 1, 10, 10, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon ally, Pokemon enemy){
			return 40;
		}
	}

	/**
	 * Fire Spin has a power of 15 and an accuracy of 70. Fire Spin inflicts damage and traps the target
	 *  for 2-5 turns, damaging the target at the end of each turn.
	 */
	public static class FireSpin extends Attack{
		public FireSpin(){
			super(83, "Fire Spin", Types.FIRE, 35, 15, 15, 0, 85, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			if(user == nbs.getAlly()){
				
				SysOut.print(":"+nbs.getNumAllyMultiTurn());
				switch(nbs.getNumAllyMultiTurn()){
				case 0: 
					nbs.setEnemyBound(true);
					nbs.setNumAllyMultiTurn(Attack.get2To5Chance());
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					SysOut.print("0->:"+nbs.getNumAllyMultiTurn());
					break;
				case 4:
					nbs.setNumAllyMultiTurn(3);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("4->:"+nbs.getNumAllyMultiTurn());
					break;
				case 3:
					nbs.setNumAllyMultiTurn(2);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("3->:"+nbs.getNumAllyMultiTurn());
					break;
				case 2:
					nbs.setNumAllyMultiTurn(1);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("2->:"+nbs.getNumAllyMultiTurn());
					break;
				case 1:
					nbs.setNumAllyMultiTurn(-1);
					this.setCurrentPP(this.getCurrentPP()+1);
					nbs.setNextTurnAction(null);
					nbs.setEnemyBound(false);
					SysOut.print("BIND OVER:::"+nbs.getNumAllyMultiTurn());
					break;
				}
			}
			else{
				switch(nbs.getNumEnemyMultiTurn()){
				case 0:
					nbs.setAllyBound(true);
					nbs.setNumEnemyMultiTurn(Attack.get2To5Chance());
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					break;
				case 4:
					nbs.setNumEnemyMultiTurn(3);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 3:
					nbs.setNumEnemyMultiTurn(2);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 2:
					nbs.setNumEnemyMultiTurn(1);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 1:
					nbs.setAllyBound(false);
					nbs.setNumEnemyMultiTurn(-1);
					nbs.setNextEnemyTurnAction(null);
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x ThunderShock inflicts damage and has a 10% chance of paralyzing the target.
	 */
	public static class ThunderShock extends Attack{
		public ThunderShock(){
			super(84, "ThunderShock", Types.ELECTRIC, 1, 30, 30, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(receiver.getStatus() == 0){
					receiver.setStatus(3);
					if(user == nbs.getAlly()){
						this.setEffect("Enemy "+ receiver.getName() + " was paralyzed!");
					}
					else{
						this.setEffect(receiver.getName() + " was paralyzed!");
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Thunderbolt does damage and has a 10% chance of paralyzing the target.
	 */
	public static class Thunderbolt extends Attack{
		public Thunderbolt(){
			super(85, "Thunderbolt", Types.ELECTRIC, 95, 15, 15, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(receiver.getStatus() == 0){
					receiver.setStatus(3);
					if(user == nbs.getAlly()){
						this.setEffect("Enemy "+ receiver.getName() + " was paralyzed!");
					}
					else{
						this.setEffect(receiver.getName() + " was paralyzed!");
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Thunder Wave paralyzes the target.
	 */
	public static class ThunderWave extends Attack{
		public ThunderWave(){
			super(86, "Thunder Wave", Types.ELECTRIC, 0, 20, 20, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 *x Thunder deals damage and has a 10% chance of paralyzing the target.
	 * Double damage if the target is using Fly.
	 */
	public static class Thunder extends Attack{
		public Thunder(){
			super(87, "Thunder", Types.ELECTRIC, 120, 10, 10, 0, 70, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(receiver.getStatus() == 0){
					receiver.setStatus(3);
					if(user == nbs.getAlly()){
						this.setEffect("Enemy "+ receiver.getName() + " was paralyzed!");
					}
					else{
						this.setEffect(receiver.getName() + " was paralyzed!");
					}
				}
			}
			int mod = 1;
			if((user == nbs.getAlly() && nbs.isEnemyFlying()) || user == nbs.getEnemy() && nbs.isAllyFlying()){
				mod = 2;
			}
			return mod*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Rock Throw deals damage and has no secondary effect.
	 */
	public static class RockThrow extends Attack{
		public RockThrow(){
			super(88, "Rock Throw", Types.ROCK, 50, 15, 15, 0, 90, 0, true, "", 0, 0, null);
		}
	}

	/**
	 *x Earthquake inflicts double damage if the target is in the underground (first turn) stage of Dig.
	 */
	public static class Earthquake extends Attack{
		public Earthquake(){
			super(89, "Earthquake", Types.GROUND, 100, 10, 10, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int mod = 1;
			if((user == nbs.getAlly() && nbs.isEnemyDug()) || user == nbs.getEnemy() && nbs.isAllyDug()){
				mod = 2;
			}
			return mod*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Fissure has 30% accuracy and inflicts damage equal to the target's current HP.
	 */
	public static class Fissure extends Attack{
		public Fissure(){
			super(90, "Fissure", Types.GROUND, 9000, 5, 5, 0, 30, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(receiver.getSpeed() > user.getSpeed()){
			  	NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed!","");
			  	nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,ntaNothing);
			  	return 0;
			}
			else{
				NewTurnAction ohko = new NewTurnAction.TA_DisplayEffect(nbs, "One-Hit KO!","");
				nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ohko);
				nbs.getActiveTurn().nextStage();
//				if(user == nbs.getAlly()){
					return receiver.getCurrentHP();
//				}
//				else{
//					return user.getCurrentHP();
//				}
			}
		}
	}

	/**
	 *x On the turn that Dig is selected, the user will dig underground, where the only attacks it cannot avoid are Bide, Swift, and Transform. 
	 * On the following turn, Dig will do damage,
	 */
	public static class Dig extends Attack{
		public Dig(){
			super(91, "Dig", Types.GROUND, /*0*/80, 10, 10, 0, 100, 0, true, "", 0, 0, null);
			//this.setNonDamageType(TWO_TURN_PREP);
		}
	}
	public static class Dig2 extends Attack{
		public Dig2(){
			super(91, "Dig", Types.GROUND, 80, 10, 10, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(user == nbs.getAlly()){
				nbs.setAllyDug(false);
				nbs.setNextTurnAction(null);
			}
			else{
				nbs.setEnemyDug(false);
				nbs.setNextEnemyTurnAction(null);
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Toxic badly poisons the target,
	 */
	public static class Toxic extends Attack{
		public Toxic(){
			super(92, "Toxic", Types.POISON, 0, 10, 10, 0, 90, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 *x Confusion does damage and has a 10% chance of confusing the target.
	 */
	public static class Confusion extends Attack{
		public Confusion(){
			super(93, "Confusion", Types.PSYCHIC, 50, 25, 25, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly() && !nbs.isEnemyConfused()){
					nbs.setEnemyConfusion(true);
					this.setEffect("Enemy " + receiver.getName()+ " became confused!");
				}
				else if(user == nbs.getEnemy() && !nbs.isAllyConfused()){
					nbs.setAllyConfusion(true);
					this.setEffect(receiver.getName()+ " became confused!");
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Psychic does damage and has a 30% chance of lowering the target's Special Defense by one stage.
	 */
	public static class Psychic extends Attack{
		public Psychic(){
			super(94, "Psychic", Types.PSYCHIC, 90, 10, 10, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.enemySpDefStage = nbs.enemySpDefStage -1;
					this.setEffect("Enemy " + receiver.getName() + "'s Sp. Def fell!");
				}
				else{
					nbs.allySpDefStage = nbs.allySpDefStage -1;
					this.setEffect(receiver.getName() + "'s Sp. Def fell!");
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 *x Hypnosis puts the target to sleep.
	 */
	public static class Hypnosis extends Attack{
		public Hypnosis(){
			super(95, "Hypnosis", Types.PSYCHIC, 0, 20, 20, 0, 60, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 *x Meditate increases the user's Attack by one level.
	 */
	public static class Meditate extends Attack{
		public Meditate(){
			super(96, "Meditate", Types.PSYCHIC, 0, 40, 40, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_ATK_UP_1);
		}
	}

	/**
	 * Agility increases the user's Speed by two stages.
	 */
	public static class Agility extends Attack{
		public Agility(){
			super(97, "Agility", Types.PSYCHIC, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_SPD_UP_2);
		}
	}

	/**
	 * Quick Attack inflicts damage, and is an increased priority move.
	 */
	public static class QuickAttack extends Attack{
		public QuickAttack(){
			super(98, "Quick Attack", Types.NORMAL, 40, 30, 30, 0, 100, 1, true, "", 0, 0, null);
		}
	}

	/**
	 * Rage deals damage and it will not be possible for the user to do anything other than let the user continue to use Rage,
	 *  and it will not stop using Rage until it faints or the battle ends. 
	 */
	public static class Rage extends Attack{
		public Rage(){
			super(99, "Rage", Types.NORMAL, 20, 20, 20, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(user == nbs.getAlly()){
				if(nbs.isAllyRaging()){
					this.setCurrentPP(this.getCurrentPP() + 1);
				}
				else{
					nbs.setAllyRaging(true);
					if(nbs.allyAtkStage < 6) nbs.allyAtkStage++;
				}
				
				nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
			}
			else{
				if(nbs.isEnemyRaging()){
					this.setCurrentPP(this.getCurrentPP() + 1);
				}
				else{
					nbs.setEnemyRaging(true);
					if(nbs.enemyAtkStage < 6) nbs.enemyAtkStage++;
				}
				
				nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Teleport can be used to flee wild Pokémon battles.
	 */
	public static class Teleport extends Attack{
		public Teleport(){
			super(100, "Teleport", Types.PSYCHIC, 0, 20, 20, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(TELEPORT);
		}
	}

	/**
	 * Night Shade inflicts damage equal to the user's level
	 */
	public static class NightShade extends Attack{
		public NightShade(){
			super(101, "Night Shade", Types.GHOST, 0, 15, 15, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			return receiver.getLevel();
		}
	}
//
//	//TODO - reset abilities already in place, have SRI do this (~learning a new move, midbattle)
//	public static class Mimic extends Attack{
//		public Mimic(){
//			super(102, "Mimic", Types.NORMAL, 0, 10, 10, 0, 100, 0, false, "", 0, 0, null);
//		}
//	}

	/**
	 * Screech decreases the target's Defense stat by two stat levels.
	 */
	public static class Screech extends Attack{
		public Screech(){
			super(103, "Screech", Types.NORMAL, 0, 40, 40, 0, 85, 0, false, "", 0, 0, null);
			this.setNonDamageType(R_DEF_DOWN_2);
		}
	}

	/**
	 * Double Team increases the user's evasion by one stage.
	 */
	public static class DoubleTeam extends Attack{
		public DoubleTeam(){
			super(104, "Double Team", Types.NORMAL, 0, 15, 15, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_EVA_UP_1);
		}
	}

	/**
	 * Recover restores up to 50% of the user's current HP,
	 */
	public static class Recover extends Attack{
		public Recover(){
			super(105, "Recover", Types.PSYCHIC, 0, 10, 10, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(SOFTBOILED);
		}
	}

	/**
	 * Harden increases the user's Defense stat by one stage.
	 */
	public static class Harden extends Attack{
		public Harden(){
			super(106, "Harden", Types.NORMAL, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_DEF_UP_1);
		}
	}

	/**
	 * Minimize raises the user's Evasion stat by one stage and replaces the user's regular image
	 * with a tiny, generic image until the user faints or is switched out, or the battle ends.
	 * (Stomp will do double damage to a Pokemon that is minimized)
	 */
	public static class Minimize extends Attack{
		public Minimize(){
			super(107, "Minimize", Types.NORMAL, 0, 20, 20, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(22);
		}
	}

	/**
	 * SmokeScreen lowers the target's Accuracy stat by one stage.
	 */
	public static class SmokeScreen extends Attack{
		public SmokeScreen(){
			super(108, "SmokeScreen", Types.NORMAL, 0, 20, 20, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(R_ACC_DOWN_1);
		}
	}

	/**
	 * Confuse Ray causes the target to become confused.
	 */
	public static class ConfuseRay extends Attack{
		public ConfuseRay(){
			super(109, "Confuse Ray", Types.GHOST, 0, 10, 10, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(CONFUSES);
		}
	}

	/**
	 * Withdraw increases the user's Defense by 1 stage.
	 */
	public static class Withdraw extends Attack{
		public Withdraw(){
			super(110, "Withdraw", Types.WATER, 0, 40, 40, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_DEF_UP_1);
		}
	}

	/**
	 * Defense Curl increases the user's Defense by 1 stage.
	 */
	public static class DefenseCurl extends Attack{
		public DefenseCurl(){
			super(111, "Defense Curl", Types.NORMAL, 0, 40, 40, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_DEF_UP_1);
		}
	}

	/**
	 * Barrier increases the user's Defense by two stages
	 */
	public static class Barrier extends Attack{
		public Barrier(){
			super(112, "Barrier", Types.PSYCHIC, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_DEF_UP_2);
		}
	}

	/**
	 *Light Screen will be in effect for 5 turns and will halve special damage done to any of 
	 *the party Pokémon on the user's side.
	 */
	public static class LightScreen extends Attack{
		public LightScreen(){
			super(113, "Light Screen", Types.PSYCHIC, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(LIGHT_SCREEN);
		}
	}

	/**
	 * Haze's effects simply resets the stat levels of both active Pokémon to 0.
	 */
	public static class Haze extends Attack{
		public Haze(){
			super(114, "Haze", Types.ICE, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(HAZE);
		}
	}

	/**
	 *Reflect will be in effect for 5 turns and will halve physical damage done to any of
	 *the party Pokémon on the user's side.
	 */
	public static class Reflect extends Attack{
		public Reflect(){
			super(115, "Reflect", Types.PSYCHIC, 0, 20, 20, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(REFLECT);
		}
	}

	/**
	 * Quadruples the probability of the user scoring a critical hit.
	 */
	public static class FocusEnergy extends Attack{
		public FocusEnergy(){
			super(116, "Focus Energy", Types.NORMAL, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(FOCUS_ENERGY);
		}
	}

//	//TODO
//	public static class Bide extends Attack{
//		public Bide(){ //Doesn't receive STAB, hits Ghosts, so Types.NONE
//			super(117, "Bide", Types.NONE, 0, 10, 10, 0, 100, 1, true, "", 0, 0, null);
//		}
//	}

	/**
	 * Metronome randomly selects a move and fully executes the attack.
	 */
	public static class Metronome extends Attack{
		public Metronome(){
			super(118, "Metronome", Types.NORMAL, 0, 10, 10, 0, 30, 0, false, "", 0, 0, null);
			this.setNonDamageType(METRONOME);
		}
	}

	/**
	 * Mirror Move causes the user to use the last move that the target used. 
	 * A move called by Mirror Move in this way counts as the last move used.
	 */
	public static class MirrorMove extends Attack{
		public MirrorMove(){
			super(119, "Mirror Move", Types.FLYING, 0, 20, 20, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(MIRROR_MOVE);
		}
	}

	/**
	 * Attack kills you.
	 */
	public static class Selfdestruct extends Attack{
		public Selfdestruct(){
			super(120, "Selfdestruct", Types.NORMAL, 200, 5, 5, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			NewTurnAction ntaDie = new NewTurnAction.TA_Recoil(nbs, user, 100, user.getCurrentHP());
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+1, ntaDie);
			
			int damage = super.getDamage(nbs, user, receiver);
			if(damage >= receiver.getCurrentHP()){
				damage = receiver.getCurrentHP()-1;
			}
			
			return damage;
		}
	}

	/**
	 * Egg Bomb inflicts damage and has no secondary effect.
	 */
	public static class EggBomb extends Attack{
		public EggBomb(){
			super(121, "Egg Bomb", Types.NORMAL, 100, 10, 10, 0, 75, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Lick inflicts damage and has a 30% chance of paralyzing the target.
	 */
	public static class Lick extends Attack{
		public Lick(){
			super(122, "Lick", Types.GHOST, 20, 30, 30, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(receiver.getStatus() == 0){
					receiver.setStatus(3);
					if(user == nbs.getAlly()){
						this.setEffect("Enemy "+ receiver.getName() + " was paralyzed!");
					}
					else{
						this.setEffect(receiver.getName() + " was paralyzed!");
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Smog inflicts damage and has a 40% chance of poisoning the target.
	 */
	public static class Smog extends Attack{
		public Smog(){
			super(123, "Smog", Types.POISON, 20, 20, 20, 0, 70, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(40) == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was poisoned!");
						nbs.getEnemy().setStatus(NewTurnAction.POISONED);
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was poisoned!");
						nbs.getAlly().setStatus(NewTurnAction.POISONED);
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Sludge does damage and has a 30% chance of poisoning the target.
	 */
	public static class Sludge extends Attack{
		public Sludge(){
			super(124, "Sludge", Types.POISON, 65, 20, 20, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was poisoned!");
						nbs.getEnemy().setStatus(NewTurnAction.POISONED);
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was poisoned!");
						nbs.getAlly().setStatus(NewTurnAction.POISONED);
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Bone Club inflicts damage and has a 10% chance of causing the target to flinch.
	 */
	public static class BoneClub extends Attack{
		public BoneClub(){
			super(125, "Bone Club", Types.GROUND, 65, 20, 20, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.setEnemyFlinch(true);
				}
				else{
					nbs.setAllyFlinch(true);
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Fire Blast inflicts damage and has a 30% chance of burning the target.
	 */
	public static class FireBlast extends Attack{
		public FireBlast(){
			super(126, "Fire Blast", Types.FIRE, 120, 5, 5, 0, 85, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was burned!");
						nbs.getEnemy().setStatus(NewTurnAction.BURNED);
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was burned!");
						nbs.getAlly().setStatus(NewTurnAction.BURNED);
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Waterfall does damage and has no secondary effect.
	 */
	public static class Waterfall extends Attack{
		public Waterfall(){
			super(127, "Waterfall", Types.WATER, 80, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Clamp inflicts damage for 2-5 turns and has an Accuracy of 75.
	 */
	public static class Clamp extends Attack{
		public Clamp(){
			super(128, "Clamp", Types.WATER, 35, 10, 10, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			if(user == nbs.getAlly()){
				
				SysOut.print(":"+nbs.getNumAllyMultiTurn());
				switch(nbs.getNumAllyMultiTurn()){
				case 0: 
					nbs.setEnemyBound(true);
					nbs.setNumAllyMultiTurn(Attack.get2To5Chance());
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					SysOut.print("0->:"+nbs.getNumAllyMultiTurn());
					break;
				case 4:
					nbs.setNumAllyMultiTurn(3);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("4->:"+nbs.getNumAllyMultiTurn());
					break;
				case 3:
					nbs.setNumAllyMultiTurn(2);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("3->:"+nbs.getNumAllyMultiTurn());
					break;
				case 2:
					nbs.setNumAllyMultiTurn(1);
					nbs.setNextTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					SysOut.print("2->:"+nbs.getNumAllyMultiTurn());
					break;
				case 1:
					nbs.setNumAllyMultiTurn(-1);
					this.setCurrentPP(this.getCurrentPP()+1);
					nbs.setNextTurnAction(null);
					nbs.setEnemyBound(false);
					SysOut.print("BIND OVER:::"+nbs.getNumAllyMultiTurn());
					break;
				}
			}
			else{
				switch(nbs.getNumEnemyMultiTurn()){
				case 0:
					nbs.setAllyBound(true);
					nbs.setNumEnemyMultiTurn(Attack.get2To5Chance());
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					break;
				case 4:
					nbs.setNumEnemyMultiTurn(3);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 3:
					nbs.setNumEnemyMultiTurn(2);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 2:
					nbs.setNumEnemyMultiTurn(1);
					nbs.setNextEnemyTurnAction(new NewTurnAction.TA_BasicAttack(nbs, user, receiver, "Enemy ", this));
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				case 1:
					nbs.setAllyBound(false);
					nbs.setNumEnemyMultiTurn(0);
					nbs.setNextEnemyTurnAction(null);
					this.setCurrentPP(this.getCurrentPP()+1);
					break;
				}
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Swift inflicts damage and is unaffected by modifications to the Accuracy stat and Evasion stat
	 */
	public static class Swift extends Attack{
		public Swift(){
			super(129, "Swift", Types.NORMAL, 60, 20, 20, 0, 101, 0, false, "", 0, 0, null);
		}
	}

	/**
	 * Skull Bash does nothing on the turn it is selected, 
	 * other than say that the user lowered its head. 
	 * On the following turn, Skull Bash will do damage, PP will be deducted from it, 
	 * and it will count as the last move used.
	 */
	public static class SkullBash extends Attack{
		public SkullBash(){
			super(130, "Skull Bash", Types.NORMAL, 100, 15, 15, 0, 100, 0, true, "", 0, 0, null);
			//this.setNonDamageType(TWO_TURN_PREP);
		}
	}
	public static class SkullBash2 extends Attack{
		public SkullBash2(){
			super(0, "Skull Bash", Types.NORMAL, 100, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Pin Missile inflicts damage, hitting the target between 2-5 times per use.
	 */
	public static class SpikeCannon extends Attack{
		public SpikeCannon(){
			super(131, "SpikeCannon", Types.NORMAL, 20, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			double rand = (Math.random()*100)+1;
			
			if(rand < 37.5){
				multi_hit = 2;
			}
			else if(rand < 75){
				multi_hit = 3;
			}
			else if(rand < 87.5){
				multi_hit = 4;
			}
			else{
				multi_hit = 5;
			}
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= multi_hit; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + multi_hit + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+multi_hit+1, ntad);
			
			return multi_hit*super.getDamage(nbs, user, receiver);
		}	
	}

	/**
	 * Constrict inflicts damage and has a 10% chance of lowering the target's Speed stat by one stat level
	 */
	public static class Constrict extends Attack{
		public Constrict(){
			super(132, "Constrict", Types.NORMAL, 10, 35, 35, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.enemySpdStage > -6){
						this.setEffect("Enemy " + receiver.getName() + "'s Speed fell!");
						nbs.enemySpdStage = nbs.enemySpdStage-1;
					}
				}
				else{
					if(nbs.allySpdStage > -6){
						this.setEffect(receiver.getName() +"'s Speed fell!");
						nbs.allySpdStage = nbs.allySpdStage-1;
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Amnesia increases the user's Special Defense by two stages.
	 */
	public static class Amnesia extends Attack{
		public Amnesia(){
			super(133, "Amnesia", Types.PSYCHIC, 0, 20, 20, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_SPDEF_UP_2);
		}
	}

	/**
	 * Kinesis decreases the target's Accuracy stat by one stage
	 */
	public static class Kinesis extends Attack{
		public Kinesis(){
			super(134, "Kinesis", Types.PSYCHIC, 0, 15, 15, 0, 80, 0, false, "", 0, 0, null);
			this.setNonDamageType(R_ACC_DOWN_1);
		}
	}

	/**
	 * Softboiled restores up to 50% of the user's current HP.
	 */
	public static class Softboiled extends Attack{
		public Softboiled(){
			super(135, "Softboiled", Types.NORMAL, 0, 10, 10, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(SOFTBOILED);
			//this.setHMTrue();
		}
	}

	/**
	 * Hi Jump Kick does damage with a Base Power of 85; however, if the attack misses, 
	 * the user will crash and lose 50% HP for crashed damage.
	 */
	public static class HiJumpKick extends Attack{
		public HiJumpKick(){
			super(136, "Hi Jump Kick", Types.FIGHTING, 130, 10, 10, 0, 90, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Glare paralyzes the target.
	 */
	public static class Glare extends Attack{
		public Glare(){
			super(137, "Glare", Types.NORMAL, 0, 30, 30, 0, 90, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 * Dream Eater only works if the target is asleep. 
	 * Dream Eater inflicts damage and 50% of the damage dealt is restored to the user as HP.
	 */
	public static class DreamEater extends Attack{
		public DreamEater(){
			super(138, "Dream Eater", Types.PSYCHIC, 100, 15, 15, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(receiver.getStatus() == 1){
				int health = super.getDamage(nbs, user, receiver);
				NewTurnAction ntaGain = new NewTurnAction.TA_GainHP(nbs, user, user.getName() + " absorbed HP!", (int)(health*0.5)+1);
				nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ntaGain);
				return health;
			}
			else{
				NewTurnAction ntaD = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed.", "");
				nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ntaD);
				return 0;
			}
		}
	}

	/**
	 * Poison Gas poisons the target
	 */
	public static class PoisonGas extends Attack{
		public PoisonGas(){
			super(139, "Poison Gas", Types.POISON, 0, 40, 40, 0, 80, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/** 
	 * Barrage inflicts damage, hitting the target 2-5 times per use.
	 */
	public static class Barrage extends Attack{
		public Barrage(){
			super(140, "Barrage", Types.NORMAL, 15, 20, 20, 0, 85, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			double rand = (Math.random()*100)+1;
			
			if(rand < 37.5){
				multi_hit = 2;
			}
			else if(rand < 75){
				multi_hit = 3;
			}
			else if(rand < 87.5){
				multi_hit = 4;
			}
			else{
				multi_hit = 5;
			}
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= multi_hit; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + multi_hit + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+multi_hit+1, ntad);
			
			return multi_hit*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Leech Life inflicts damage, and up to 50% of the damage dealt to the target is restored to the user as HP.
	 */
	public static class LeechLife extends Attack{
		public LeechLife(){
			super(141, "Leech Life", Types.BUG, 20, 15, 15, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int health = super.getDamage(nbs, user, receiver);
			NewTurnAction ntaGain = new NewTurnAction.TA_GainHP(nbs, user, user.getName() + " absorbed HP!", (int)(health*0.5)+1);
			nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, ntaGain);
			return health;
		}
	}

	/**
	 * Lovely Kiss causes the target to fall asleep.
	 */
	public static class LovelyKiss extends Attack{
		public LovelyKiss(){
			super(142, "Lovely Kiss", Types.NORMAL, 0, 10, 10, 0, 75, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 * Sky Attack says the user starts glowing on the first turn, and then attacks on the second turn.
	 */
	public static class SkyAttack extends Attack{
		public SkyAttack(){
			super(143, "Sky Attack", Types.FLYING, 140, 5, 5, 0, 90, 0, true, "", 0, 0, null);
			//this.setNonDamageType(TWO_TURN_PREP);
		}
	}
	public static class SkyAttack2 extends Attack{
		public SkyAttack2(){
			super(143, "Sky Attack", Types.FLYING, 140, 5, 5, 0, 90, 0, true, "", 0, 0, null);
		}
	}

	/**
	 * Transform changes the user's current type, current stats, current stat levels,
	 *  current moves, current species, current cry, and current catch rate to that of the target's.
	 *	Each move copied by Transform will have 5 PP.
	 */
	public static class Transform extends Attack{
		public Transform(){
			super(144, "Transform", Types.NORMAL, 0, 10, 10, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(TRANSFORM);
		}
	}

	/**
	 * Bubble inflicts damage and has a 10% chance of lowering the target's Speed by one stage.
	 */
	public static class Bubble extends Attack{
		public Bubble(){
			super(145, "Bubble", Types.WATER, 20, 30, 30, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly()){
					receiver = nbs.getEnemy();
					if(nbs.enemySpdStage > -6){
						this.setEffect("Enemy " + receiver.getName() + "'s Speed fell!");
						nbs.enemySpdStage = nbs.enemySpdStage-1;
					}
				}
				else{
					receiver = nbs.getAlly();
					if(nbs.allySpdStage > -6){
						this.setEffect(receiver.getName() +"'s Speed fell!");
						nbs.allySpdStage = nbs.allySpdStage-1;
					}
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Dizzy Punch now has a 20% chance of confusing the target
	 */
	public static class DizzyPunch extends Attack{
		public DizzyPunch(){
			super(146, "Dizzy Punch", Types.NORMAL, 70, 10, 10, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(20) == "Yes"){
				if(user == nbs.getAlly() && !nbs.isEnemyConfused()){
					nbs.setEnemyConfusion(true);
					this.setEffect("Enemy " + receiver.getName()+ " became confused!");
				}
				else if(user == nbs.getEnemy() && !nbs.isAllyConfused()){
					nbs.setAllyConfusion(true);
					this.setEffect(receiver.getName() + " became confused!");
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Spore puts the target to sleep.
	 */
	public static class Spore extends Attack{
		public Spore(){
			super(147, "Spore", Types.GRASS, 0, 15, 15, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(STATUS_CHANGE);
		}
	}

	/**
	 * Flash decreases the target's Accuracy stat by one stage
	 */
	public static class Flash extends Attack{
		public Flash(){
			super(148, "Flash", Types.NORMAL, 0, 20, 20, 0, 100, 0, false, "", 0, 0, null);
			this.setHMTrue();
			this.setNonDamageType(R_ACC_DOWN_1);
		}
	}

	/**
	 * Psywave inflicts a random amount of damage, varying between 0.5× and 1.5× the user's level.
	 * 
	 * Though it is a Psychic move, Psywave deals typeless damage, taking neither weakness nor resistance into account
	 */
	public static class Psywave extends Attack{
		public Psywave(){
			super(149, "Psywave", Types.PSYCHIC, 1, 15, 15, 0, 80, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int random = (int)(Math.random()*11);
			return (int)((random+5)*(user.getLevel()/10.0));
		}
	}

	/**
	 * Splash has no effect whatsoever.
	 */
	public static class Splash extends Attack{
		public Splash(){
			super(150, "Splash", Types.NORMAL, 0, 40, 40, 0, 101, 0, false, "No Effect!", 0, 0, null);
		}
	}

	/**
	 * Acid Armor increases the user's Defense by two stages.
	 */
	public static class AcidArmor extends Attack{
		public AcidArmor(){
			super(151, "Acid Armor", Types.POISON, 0, 40, 40, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_DEF_UP_2);
		}
	}

	/**
	 * Crabhammer deals damage and has an increased critical hit ratio. 
	 */
	public static class Crabhammer extends Attack{
		public Crabhammer(){
			super(152, "Crabhammer", Types.WATER, 90, 10, 10, 0, 90, 0, true, "", 0, 0, null);
			this.setCHRatio(true);
		}
	}

	/**
	 * Attack kills you (almost) - bigger point is the recoil.
	 */
	public static class Explosion extends Attack{
		public Explosion(){
			super(153, "Explosion", Types.NORMAL, 250, 5, 5, 0, 100, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			NewTurnAction ntaDie = new NewTurnAction.TA_Recoil(nbs, user, 100, user.getCurrentHP());
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+1, ntaDie);
			
			int damage = super.getDamage(nbs, user, receiver);
			if(damage >= receiver.getCurrentHP()){
				damage = receiver.getCurrentHP()-1;
			}
			
			return damage;
		}
	}

	/**
	 * Fury Swipes does damage, hitting 2-5 times per use.
	 */
	public static class FurySwipes extends Attack{
		public FurySwipes(){
			super(154, "Fury Swipes", Types.NORMAL, 18, 15, 15, 0, 80, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			double rand = (Math.random()*100)+1;
			
			if(rand < 37.5){
				multi_hit = 2;
			}
			else if(rand < 75){
				multi_hit = 3;
			}
			else if(rand < 87.5){
				multi_hit = 4;
			}
			else{
				multi_hit = 5;
			}
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= multi_hit; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + multi_hit + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+multi_hit+1, ntad);
			
			return multi_hit*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Bonemerang inflicts damage, striking twice per use.
	 */
	public static class Bonemerang extends Attack{
		public Bonemerang(){
			super(155, "Bonemerang", Types.GROUND, 50, 10, 10, 0, 90, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			//Create a new method, TA_DisplayAnimation(...) when attack animations are added
			for(int i = 1; i <= 2; i++){
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Hit #"+i+"...","");
				nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+i, nta);
			}
			NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(nbs, "Hit " + 2 + " times!", "");
			nbs.getActiveTurn().addTurnActionAt(nbs.getActiveTurn().getCurrentStage()+3, ntad);
			
			return 2*super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Rest causes the user to fall asleep, restoring its HP to its maximum amount.
	 *  The user will then be asleep for two turns, and wake up on the third turn. 
	 *  The sleep countdown of Rest is not reset by switching.
	 */
	public static class Rest extends Attack{
		public Rest(){
			super(156, "Rest", Types.PSYCHIC, 0, 10, 10, 0, 101, 0, false, " fell fast asleep!", 0, 0, null);
			this.setNonDamageType(REST);
		}
	}

	/**
	 * Rock Slide inflicts damage and has a 30% chance of causing the target to flinch.
	 */
	public static class RockSlide extends Attack{
		public RockSlide(){
			super(157, "Rock Slide", Types.ROCK, 75, 10, 10, 0, 90, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(30) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.setEnemyFlinch(true);
				}
				else{
					nbs.setAllyFlinch(true);
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Hyper Fang deals damage and has a 10% chance of causing the target to flinch.
	 */
	public static class HyperFang extends Attack{
		public HyperFang(){
			super(158, "Hyper Fang", Types.NORMAL, 80, 15, 15, 0, 90, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			if(NewTurnAction.Bernoulli(10) == "Yes"){
				if(user == nbs.getAlly()){
					nbs.setEnemyFlinch(true);
				}
				else{
					nbs.setAllyFlinch(true);
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Sharpen increases the user's Attack stat by one stage.
	 */
	public static class Sharpen extends Attack{
		public Sharpen(){
			super(159, "Sharpen", Types.NORMAL, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(U_ATK_UP_1);
		}
	}

	/**
	 * Conversion changes the user's current type(s) to the target's current type(s).
	 */
	public static class Conversion extends Attack{
		public Conversion(){
			super(160, "Conversion", Types.NORMAL, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(CONVERSION);
		}
	}

	/**
	 *x Tri Attack does damage, and has a 20% chance of either paralyzing, freezing, or burning the target
	 */
	public static class TriAttack extends Attack{
		public TriAttack(){
			super(161, "Tri Attack", Types.NORMAL, 80, 10, 10, 0, 100, 0, false, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			
			if(NewTurnAction.Bernoulli(20) == "Yes"){
				int random = (int)(Math.random()*3);
				switch(random){
				case 0: //Paralysis 
					if(receiver.getStatus() == 0){
						receiver.setStatus(3);
					}
					break;
				case 1: //Freezing
					if(receiver.getStatus() == 0 && receiver.getType1() != Types.ICE && receiver.getType2() != Types.ICE){
						receiver.setStatus(5);
					}
					break;
				case 2: //Burning
					if(receiver.getStatus() == 0 && receiver.getType1() != Types.FIRE && receiver.getType2() != Types.FIRE){
						receiver.setStatus(4);
					}		
					break;
				}
			}
			return super.getDamage(nbs, user, receiver);
		}
	}

	/**
	 * Super Fang does damage equal to 50% of the target's current HP.
	 */
	public static class SuperFang extends Attack{
		public SuperFang(){
			super(162, "Super Fang", Types.NORMAL, 1, 10, 10, 0, 90, 0, true, "", 0, 0, null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int damage = (int) (receiver.getCurrentHP()/2.0);
			//Set lower bound
			if (damage<1){damage=1;}
			
			return damage;
		}
	}

	/**
	 * Slash deals damage and has an increased critical hit ratio.
	 */
	public static class Slash extends Attack{
		public Slash(){
			super(163, "Slash", Types.NORMAL, 70, 20, 20, 0, 100, 0, true, "", 0, 0, null);
			this.setCHRatio(true);
		}
	}

//	//TODO SRI -> I will do this attack when you notice this comment. Lol.
//	public static class Substitute extends Attack{
//		public Substitute(){
//			super(164, "Substitute", Types.NORMAL, 1, 10, 10, 0, 101, 0, false, "", 0, 0, null);
//			this.setNonDamageType(SUBSTITUTE);
//		}
//	}

	/**
	 * Struggle deals damage to the opponent and the user receives recoil damage. 
	 * The user takes recoil damage equal to 50% of the damage the attack did to the opponent.
	 */
	public static class Struggle extends Attack{
		public Struggle(){
			super(165, "Struggle", Types.NONE, 50, 1, 1, 0, 100, 0, true, "", 0, 0, null);
		}
	}

	/*
	 */
	public static class HurtItselfInConfusion extends Attack{
		public HurtItselfInConfusion(){
			super(1000, "HurtItselfInConfusion", Types.NONE, 40, 0, 0, 0, 101, 0, false, "", 0, 0, null);		
		}
	}
	
	public static Attack getAttackByNum(int random){
		//int random = (int)(Math.random()*164)+1;
		switch(random){	

		case 1: return (new  Attack.Pound());
		
		case 2: return (new  Attack.KarateChop());

		case 3: return (new  Attack.DoubleSlap());

		case 4: return (new  Attack.CometPunch());

		case 5: return (new  Attack.MegaPunch());

		case 6: return (new  Attack.PayDay());

		case 7: return (new  Attack.FirePunch());

		case 8: return (new  Attack.IcePunch());

		case 9: return (new  Attack.ThunderPunch());

		case 10: return (new  Attack.Scratch());

		case 11: return (new  Attack.ViceGrip());

		case 12: return (new  Attack.Guillotine());

		case 13: return (new  Attack.RazorWind());

		case 14: return (new  Attack.SwordsDance());

		case 15: return (new  Attack.Cut());

		case 16: return (new  Attack.Gust());

		case 17: return (new  Attack.WingAttack());

		case 18: return (new  Attack.Whirlwind());

		case 19: return (new  Attack.Fly());

		case 20: return (new  Attack.Bind());

		case 21: return (new  Attack.Slam());

		case 22: return (new  Attack.VineWhip());

		case 23: return (new  Attack.Stomp());

		case 24: return (new  Attack.DoubleKick());

		case 25: return (new  Attack.MegaKick());

		case 26: return (new  Attack.JumpKick());

		case 27: return (new  Attack.RollingKick());

		case 28: return (new  Attack.SandAttack());

		case 29: return (new  Attack.Headbutt());

		case 30: return (new  Attack.HornAttack());

		case 31: return (new  Attack.FuryAttack());

		case 32: return (new  Attack.HornDrill());

		case 33: return (new  Attack.Tackle());

		case 34: return (new  Attack.BodySlam());

		case 35: return (new  Attack.Wrap());

		case 36: return (new  Attack.TakeDown());

		case 37: return (new  Attack.Thrash());

		case 38: return (new  Attack.DoubleEdge());

		case 39: return (new  Attack.TailWhip());

		case 40: return (new  Attack.PoisonSting());

		case 41: return (new  Attack.Twineedle());

		case 42: return (new  Attack.PinMissile());

		case 43: return (new  Attack.Leer());

		case 44: return (new  Attack.Bite());

		case 45: return (new  Attack.Growl());

		case 46: return (new  Attack.Roar());

		case 47: return (new  Attack.Sing());

		case 48: return (new  Attack.Supersonic());

		case 49: return (new  Attack.SonicBoom());

		case 50: return (new  Attack.Disable());

		case 51: return (new  Attack.Acid());

		case 52: return (new  Attack.Ember());

		case 53: return (new  Attack.Flamethrower());

		case 54: return (new  Attack.Mist());

		case 55: return (new  Attack.WaterGun());

		case 56: return (new  Attack.HydroPump());

		case 57: return (new  Attack.Surf());

		case 58: return (new  Attack.IceBeam());

		case 59: return (new  Attack.Blizzard());

		case 60: return (new  Attack.Psybeam());

		case 61: return (new  Attack.BubbleBeam());

		case 62: return (new  Attack.AuroraBeam());

		case 63: return (new  Attack.HyperBeam());

		case 64: return (new  Attack.Peck());

		case 65: return (new  Attack.DrillPeck());

		case 66: return (new  Attack.Submission());

		case 67: return (new  Attack.LowKick());

		//case 68: return (new  Attack.Counter());

		case 69: return (new  Attack.SeismicToss());

		case 70: return (new  Attack.Strength());

		case 71: return (new  Attack.Absorb());

		case 72: return (new  Attack.MegaDrain());

		case 73: return (new  Attack.LeechSeed());

		case 74: return (new  Attack.Growth());

		case 75: return (new  Attack.RazorLeaf());

		case 76: return (new  Attack.SolarBeam());

		case 77: return (new  Attack.PoisonPowder());

		case 78: return (new  Attack.StunSpore());

		case 79: return (new  Attack.SleepPowder());

		case 80: return (new  Attack.PetalDance());

		case 81: return (new  Attack.StringShot());

		case 82: return (new  Attack.DragonRage());

		case 83: return (new  Attack.FireSpin());

		case 84: return (new  Attack.ThunderShock());

		case 85: return (new  Attack.Thunderbolt());

		case 86: return (new  Attack.ThunderWave());

		case 87: return (new  Attack.Thunder());

		case 88: return (new  Attack.RockThrow());

		case 89: return (new  Attack.Earthquake());

		case 90: return (new  Attack.Fissure());

		case 91: return (new  Attack.Dig());

		case 92: return (new  Attack.Toxic());

		case 93: return (new  Attack.Confusion());

		case 94: return (new  Attack.Psychic());

		case 95: return (new  Attack.Hypnosis());

		case 96: return (new  Attack.Meditate());

		case 97: return (new  Attack.Agility());

		case 98: return (new  Attack.QuickAttack());

		case 99: return (new  Attack.Rage());

		case 100: return (new  Attack.Teleport());

		case 101: return (new  Attack.NightShade());

		//case 102: return (new  Attack.Mimic());

		case 103: return (new  Attack.Screech());

		case 104: return (new  Attack.DoubleTeam());

		case 105: return (new  Attack.Recover());

		case 106: return (new  Attack.Harden());

		case 107: return (new  Attack.Minimize());

		case 108: return (new  Attack.SmokeScreen());

		case 109: return (new  Attack.ConfuseRay());

		case 110: return (new  Attack.Withdraw());

		case 111: return (new  Attack.DefenseCurl());

		case 112: return (new  Attack.Barrier());

		case 113: return (new  Attack.LightScreen());

		case 114: return (new  Attack.Haze());

		case 115: return (new  Attack.Reflect());

		case 116: return (new  Attack.FocusEnergy());

		//case 117: return (new  Attack.Bide());

		//case 118: return (new  Attack.Metronome());

		//case 119: return (new  Attack.MirrorMove());

		case 120: return (new  Attack.Selfdestruct());

		case 121: return (new  Attack.EggBomb());

		case 122: return (new  Attack.Lick());

		case 123: return (new  Attack.Smog());

		case 124: return (new  Attack.Sludge());

		case 125: return (new  Attack.BoneClub());

		case 126: return (new  Attack.FireBlast());

		case 127: return (new  Attack.Waterfall());

		case 128: return (new  Attack.Clamp());

		case 129: return (new  Attack.Swift());

		case 130: return (new  Attack.SkullBash());

		case 131: return (new  Attack.SpikeCannon());

		case 132: return (new  Attack.Constrict());

		case 133: return (new  Attack.Amnesia());

		case 134: return (new  Attack.Kinesis());

		case 135: return (new  Attack.Softboiled());

		case 136: return (new  Attack.HiJumpKick());

		case 137: return (new  Attack.Glare());

		case 138: return (new  Attack.DreamEater());

		case 139: return (new  Attack.PoisonGas());

		case 140: return (new  Attack.Barrage());

		case 141: return (new  Attack.LeechLife());

		case 142: return (new  Attack.LovelyKiss());

		case 143: return (new  Attack.SkyAttack());

		//case 144: return (new  Attack.Transform());

		case 145: return (new  Attack.Bubble());

		case 146: return (new  Attack.DizzyPunch());

		case 147: return (new  Attack.Spore());

		case 148: return (new  Attack.Flash());

		case 149: return (new  Attack.Psywave());

		case 150: return (new  Attack.Splash());

		case 151: return (new  Attack.AcidArmor());

		case 152: return (new  Attack.Crabhammer());

		case 153: return (new  Attack.Explosion());

		case 154: return (new  Attack.FurySwipes());

		case 155: return (new  Attack.Bonemerang());

		case 156: return (new  Attack.Rest());

		case 157: return (new  Attack.RockSlide());

		case 158: return (new  Attack.HyperFang());

		case 159: return (new  Attack.Sharpen());

		case 160: return (new  Attack.Conversion());

		case 161: return (new  Attack.TriAttack());

		case 162: return (new  Attack.SuperFang());

		case 163: return (new  Attack.Slash());

		//case 164: return (new  Attack.Substitute());
		
		default: return (new Attack.Pound());
		}
	}
/**	
	public static class Absorb extends Attack{
        public Absorb(){
            super(1,"Absorb", Types.GRASS, 20, 25, 25, 0, 100, 0, false, "", 0, 0, null);
        }
    }
	
	public static class Bubble extends Attack{
		public Bubble(){
			super(16, "Bubble", Types.WATER, 20, 30, 30, 0, 100, 0, false, "", 0, 0,null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int lowerSpeed = (int)(Math.random()*10.0);
			SysOut.print("||||||| Speed: " + lowerSpeed*10 + "%");
			if(lowerSpeed == 0){
				if(user == nbs.getAlly()){
					if(nbs.enemySpdStage > -6){
						this.setEffect("Enemy " + receiver.getName() + "'s Speed fell!");
						nbs.enemySpdStage = nbs.enemySpdStage-1;
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.allySpdStage > -6){
						this.setEffect(receiver.getName() +"'s Speed fell!");
						nbs.allySpdStage = nbs.allySpdStage-1;
					}
					else{
						this.setEffect("");
					}
				}
			}
			else{
				this.setEffect("");
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}
	
	public static class Bubblebeam extends Attack{
		public Bubblebeam(){
			super(17, "Bubblebeam", Types.WATER, 65, 20, 20, 0, 100, 0, false, "", 0, 0,null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int lowerSpeed = (int)(Math.random()*10.0);
			SysOut.print("||||||| Speed: " + lowerSpeed*10 + "%");
			if(lowerSpeed == 0){
				if(user == nbs.getAlly()){
					if(nbs.enemySpdStage > -6){
						this.setEffect("Enemy " + receiver.getName() + "'s Speed fell!");
						nbs.enemySpdStage = nbs.enemySpdStage-1;
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.allySpdStage > -6){
						this.setEffect(receiver.getName() +"'s Speed fell!");
						nbs.allySpdStage = nbs.allySpdStage-1;
					}
					else{
						this.setEffect("");
					}
				}
			}
			else{
				this.setEffect("");
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}
	
//	public static class Comet_Punch extends Attack{
//		public Comet_Punch(){
//			
//		}
//	}
	
	public static class Confuse_Ray extends Attack{
		public Confuse_Ray(){
			super(20, "Confuse Ray", Types.GHOST, 0, 10, 10, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(14);
		}
	}
	public static class Cut extends Attack{
		public Cut(){
			super(26, "Cut", Types.NORMAL, 50, 30, 30, 0, 95, 0, true, "", 0, 0,null);
			this.setHMTrue();
		}
	}
	
//	public static class DoubleSlap extends Attack{
//		public DoubleSlap(){
//			
//		}
//	}
	
	public static class Dragon_Rage extends Attack{
		public Dragon_Rage(){
			super(35, "Dragon Rage", Types.DRAGON, 0, 10, 10, 0, 100, 0, false, "", 0, 0,null);
		}
		public void useAttack(Pokemon user, Pokemon receiver){
			//By definition, it always does 40HP damage.
			_damage=40;
			
			if(receiver.getCurrentHP() > _damage){
				receiver.setCurrentHP(receiver.getCurrentHP() - _damage);
			}
			else
				receiver.setCurrentHP(0);
		}
		public int getDamage(Pokemon ally, Pokemon enemy){
			return 40;
		}
	}
	
	public static class Ember extends Attack{
		public Ember(){
			super(40, "Ember", Types.FIRE, 40, 25, 25, 0, 100, 0, false, "", 4, 10, null);
			
		}
	}
	
//	public static class Fire_Punch extends Attack{
//		public Fire_Punch(){
//			super(7, "Fire Punch", Types.FIRE, 75, 15, 15, 0, 100, 0, true, "", 0, 0, null);
//		}
//	}
	
	public static class Fissure extends Attack{
		public Fissure(){
			//This is ABSOLUTELY wrong. Fix it.
			super(45, "Fissure", Types.GROUND, 90000, 5, 5, 0, 101, 0, false, "", 0, 0, null);
		}
	}
	
	public static class Focus_Energy extends Attack{
		public Focus_Energy(){
			super(49, "Focus_Energy", Types.NORMAL, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(17);
		}
	}
	
	public static class Growl extends Attack{
		public Growl(){
			super(53, "Growl", Types.NORMAL, 0, 40, 40, 0, 100, 0, false, "", 0, 0,null);
			this.setNonDamageType(1);
		}
	}
	
	public static class Growth extends Attack{
		public Growth(){
			super(54, "Growth", Types.NORMAL, 0, 40, 40, 0, 101, 0, false, "", 0, 0,null);
			this.setNonDamageType(11);
		}
	}
	
//	public static class Guillotine extends Attack{
//		public Guillotine(){
//			super(12, "Guillotine", Types.NORMAL, 0, 5, 5, 0, 30, true, "", 0, 0, null);
//			this.setNonDamageType(OHKO);
//		}
//	}
	
	public static class Gust extends Attack{
		public Gust(){
			super(56, "Gust", Types.NORMAL, 40, 35, 35, 0, 100, 0, false, "", 0, 0, null);
		}
	}
	
	public static class Harden extends Attack{
		public Harden(){
			super(57, "Harden", Types.NORMAL, 0, 30, 30, 0, 101, 0, false, "", 0, 0, null);
			this.setNonDamageType(8);
		}
	}
	
	public static class Horn_Attack extends Attack{
		public Horn_Attack(){
			super(61, "Horn Attack", Types.NORMAL, 65, 25, 25, 0, 100, 0, true, "", 0, 0,null);
		}
	}
	
	public static class Hyper_Beam extends Attack{
		public Hyper_Beam(){
			super(64, "Hyper Beam", Types.NORMAL, 150, 5, 5, 0, 90, 0, false,"", 0, 0, new Attack.Hyper_Beam2());
		}
	}

	public static class Hyper_Beam2 extends Attack{
		public Hyper_Beam2(){
			super(0, "Hyper Beam", Types.NONE, 0, 0, 0, 0, 0, 0, false, " must recharge!", 0, 0, null);
				
		}
	}
	
	public static class Hyper_Fang extends Attack{
		public Hyper_Fang(){
			super(65, "Hyper Fang", Types.NORMAL, 80, 15, 15, 0, 90, 0, true,"", 0, 0,null);
		}
	}
	
//	public static class Ice_Punch extends Attack{
//		public Ice_Punch(){
//			super(8, "Ice Punch", Types.ICE, 75, 15, 15, 0, 100, 0, true, "", 0, 0, null);
//		}
//	}
	
	public static class Karate_Chop extends Attack{
		public Karate_Chop(){
			super(70, "Karate Chop", Types.FIGHTING, 50, 25, 25, 0, 100, 0, true, "", 0, 0,null);
			this.setCHRatio(true);
		}
	}
	public static class Leech_Seed extends Attack{
		public Leech_Seed(){
			super(73, "Leech Seed", Types.GRASS, 0, 10, 10, 0, 90, 0, false, "", 0, 0,null);
			this.setNonDamageType(15);
		}
	}
	
	public static class Leer extends Attack{
		public Leer(){
			super(74, "Leer", Types.NORMAL, 0, 30, 30, 0, 100, 0, false, "", 0, 0,null);
			this.setNonDamageType(2);
		}
	}
	
	public static class Mega_Drain extends Attack{
        public Mega_Drain(){
            super(80,"Mega Drain", Types.GRASS, 40, 10, 10, 0, 100, 0, false, "", 0, 0,null);
        }
    }
	
	public static class Mega_Punch extends Attack{
		public Mega_Punch(){
			super(5, "Mega Punch", Types.NORMAL, 80, 20, 20, 0, 85, 0, true, "", 0, 0, null);
		}
	}
	
//	public static class Pay_Day extends Attack{
//		public Pay_Day(){
//			
//		}
//	}
	
	public static class Peck extends Attack{
		public Peck(){
			super(90, "Peck", Types.FLYING, 35, 35, 35, 0, 100, 0, true, "", 0, 0,null);
		}
	}
	
	public static class Poison_Sting extends Attack{
		public Poison_Sting(){
			super(94, "Poison Sting", Types.POISON, 15, 35, 35, 0, 100, 0, false, "", 0, 0,null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			String poisoned = NewTurnAction.Bernoulli(30);
			if(poisoned == "Yes"){
				if(user == nbs.getAlly()){
					if(nbs.getEnemy().getStatus()==0){
						this.setEffect("Enemy " + receiver.getName() + " was poisoned!");
						nbs.getEnemy().setStatus(NewTurnAction.POISONED);
					}
					else{
						this.setEffect("");
					}
				}
				else{
					if(nbs.getAlly().getStatus()==0){
						this.setEffect(receiver.getName() +" was poisoned!");
						nbs.getAlly().setStatus(NewTurnAction.POISONED);
					}
					else{
						this.setEffect("");
					}
				}
			}
			else{
				this.setEffect("");
			}
			
			return super.getDamage(nbs, user, receiver);
		}
	}
	
	public static class PoisonPowder extends Attack{
		public PoisonPowder(){
			super(95, "PoisonPowder", Types.POISON, 0, 35, 35, 0, 75, 0, false, "", 0, 0, null);
			this.setNonDamageType(16);
		}
	}
	
	public static class Pound extends Attack{
		public Pound(){
			super(1, "Pound", Types.NORMAL, 40, 35, 35, 0, 100, 0, true, "", 0, 0, null);
		}
	}
	
	public static class Quick_Attack extends Attack{
		public Quick_Attack(){
			super(100, "Quick Attack", Types.NORMAL, 40, 30, 30, 0, 100, 1, true, "", 0, 0,null);
		}
	}
	
	public static class Razor_Leaf extends Attack{
		public Razor_Leaf(){
			super(102, "Razor Leaf", Types.GRASS, 55, 25, 25, 0, 95, 0, true, "", 0, 0,null);
			this.setCHRatio(true);
		}
	}
	
	public static class Razor_Wind extends Attack{
		public Razor_Wind(){
			super(13, "Razor Wind", Types.NONE, 0, 10, 10, 0, 100, 0, true, " created a whirlwind.", 0, 0, new Attack.Razor_Wind2());
		}
	}
	
	public static class Razor_Wind2 extends Attack{
		public Razor_Wind2(){
			super(0, "Razor Wind", Types.NORMAL, 80, 10, 10, 0, 100, 0, true, "", 0, 0, null);
			this.setCHRatio(true);
		}
	}
	
	public static class Sand_Attack extends Attack{
		public Sand_Attack(){
			super(111, "Sand-Attack", Types.NORMAL, 0, 15, 15, 0, 100, 0, false,"", 0, 0,null);
			this.setNonDamageType(4);
		}
	}
	
	public static class Scratch extends Attack{
		public Scratch(){
			super(112, "Scratch", Types.NORMAL, 40, 35, 35, 0, 100, 0, true, "", 0, 0,null);
		}
	}
	
	public static class Selfdestruct extends Attack{
		public Selfdestruct(){
			super(115, "Selfdestruct", Types.NORMAL, 170, 5, 5, 0, 100, 0, true, "", 0, 0,null);
			}
	}
	
	public static class Slash extends Attack{
		public Slash(){
			super(121, "Slash", Types.NORMAL, 70, 20, 20, 0, 100, 0, true, "", 0, 0,null);
			this.setCHRatio(true);
		}
	}
	
	public static class Sleep_Powder extends Attack{
		public Sleep_Powder(){
			super(122, "Sleep Powder", Types.GRASS, 0, 15, 15, 0, 75, 0, false, "", 0, 0, null);
			this.setNonDamageType(16);
		}
	}
	
	public static class Softboiled extends Attack{
		public Softboiled(){
			super(126, "Softboiled", Types.NORMAL, 0, 10, 10, 0, 101, 0, false, "", 0, 0, null);
			}
	}
	
	public static class Solar_Beam extends Attack{
		public Solar_Beam(){
			super(127, "Solar Beam", Types.NONE, 0, 5, 5, 0, 90, 0, false," took in sunlight.", 0, 0, new Attack.Solar_Beam2());
			this.setNonDamageType(13);
		}
	}
	
	public static class Solar_Beam2 extends Attack{
		public Solar_Beam2(){
			super(0, "Solar Beam", Types.GRASS, 150, 0, 0, 0, 90, 0, false,"", 0, 0, null);
		}
	}
	
	public static class String_Shot extends Attack{
		public String_Shot(){
			super(134, "String Shot", Types.BUG, 0, 40, 40, 0, 95, 0, false,"", 0, 0, null);
			this.setNonDamageType(3);
		}
	}
	
	public static class Struggle extends Attack{
        public Struggle(){
            super(135,"Struggle",Types.NONE, 50, 0, 0, 0, 101, 0, false, "", 0, 0,null);
        }
	}
	
	public static class Stun_Spore extends Attack{
		public Stun_Spore(){
			super(136, "Stun Spore", Types.GRASS, 0, 30, 30, 0, 75, 0, false, "", 0, 0, null);
			this.setNonDamageType(16);
		}
	}
	
	public static class Super_Fang extends Attack{
		public Super_Fang(){
			super(139, "Super Fang", Types.NORMAL, 0, 10, 10, 0, 90, 0, true,"", 0, 0,null);
		}
		public int getDamage(NewBattleScreen nbs, Pokemon user, Pokemon receiver){
			int damage = (int) (receiver.getCurrentHP()/2.0);
			//Set lower bound
			if (damage<1){damage=1;}
			
			return damage;
		}
	}
	
	public static class Supersonic extends Attack{
		public Supersonic(){
			super(140, "Supersonic", Types.NORMAL, 0, 20, 20, 0, 55, 0, false, "", 0, 0, null);
			this.setNonDamageType(14);
		}
	}
	
	public static class Surf extends Attack{
		public Surf(){
			super(141, "Surf", Types.WATER, 95, 15, 15, 0, 100, 0, false, "", 0, 0,null);
			this.setHMTrue();
		}
	}
	public static class Tackle extends Attack{
		public Tackle(){
			super(144, "Tackle", Types.NORMAL, 30, 35, 35, 0, 95, 0, true, "", 0, 0,null);
		}
	}
	
	public static class Tail_Whip extends Attack{
		public Tail_Whip(){
			super(145, "Tail Whip", Types.NORMAL, 0, 15, 15, 0 , 95, 0, true, "", 0, 0,null);
			this.setNonDamageType(2);
		}
	}
	
	public static class Thunder_Wave extends Attack{
		public Thunder_Wave(){
			super(150, "Thunder Wave", Types.ELECTRIC, 0, 20, 20, 0, 100, 0, false, "", 0, 0, null);
			this.setNonDamageType(16);
		}
	}
	
	public static class ThunderShock extends Attack{
		public ThunderShock(){
			super(153, "Thundershock", Types.ELECTRIC, 40, 30, 30, 0, 100, 0, false, "", 3, 10, null);
			
		}
	}
	
	public static class ViceGrip extends Attack{
		public ViceGrip(){
			super(11, "ViceGrip", Types.NORMAL, 55, 30, 30, 0, 100, 0, true, "", 0, 0, null);
		}
	}
	
	public static class Vine_Whip extends Attack{
		public Vine_Whip(){
			super(159, "Vine Whip", Types.GRASS, 35, 10, 10, 0, 100, 0, true, "", 0, 100, null);
			
		}
	}

//	public static class Water_Gun extends Attack{
		public Water_Gun(){
			super(160, "Water_Gun", Types.WATER, 40, 25, 25, 0, 100, 0, false, "", 0, 0,null);
		}
	}
	
	public static class HurtItselfInConfusion extends Attack{
		public HurtItselfInConfusion(){
			super(1000, "HurtItselfInConfusion", Types.NONE, 40, 0, 0, 0, 101, 0, false, "", 0, 0, null);
			
		}
	}
	
*/
	
	
	
}
