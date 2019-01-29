package PokemonDemo;
import static java.lang.Math.pow;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**ITEMS.
 * 
 * @author Playa Sri
 */
public class Item {
	//What the item does to the user.
	private int _effect;
	//HP, PP, status, revive, etc.
	private String _type;
	//Name
	private String _name;
	private String _desc;
	//Can this be used in battle?
	private boolean _battle, _instant, _OOB=false;
	//The number remaining
	private int _remaining;
	private int _index;
	private int _buyPrice;
	private int _sellPrice;
	//These are for probability calculation and *don't* get
	//added to the item vector.
	private int _status;
	private double _a, _b;
	private double _prob;
	private boolean _abilityRequired;
	protected int[] compatibilityVector;
	private BufferedImage _itemIcon;

	public static final int
	POTION = 0,
	ORGANIC_POTION = 1,
	SUPER_POTION = 2,
	HYPER_POTION = 3,
	MAX_POTION = 4,
	BUBBLE_TEA = 5,
	FRESH_WATER = 6,
	SODA_POP = 7,
	FULL_RESTORE = 8,
	ELIXIR = 9,
	ETHER = 10,
	MAX_ELIXIR = 11,
	MAX_ETHER = 12,
	REVIVE = 13,
	MAX_REVIVE = 14,
	X_ACCURACY = 15,
	X_ATTACK = 16,
	X_DEFEND = 17,
	X_SPECIAL = 18,
	X_SPEED = 19,
	DIRE_HIT = 20,
	GUARD_SPEC = 21,
	POKEBALL = 22,
	FAIRTRADE_POKEBALL = 23,
	GREAT_BALL = 24,
	ULTRA_BALL = 25,
	MASTER_BALL = 26,
	AWAKENING = 27,
	ANTIDOTE = 28,
	HOLISTIC_ANTIDOTE = 29,
	PARALYZ_HEAL = 30,
	HOLISTIC_PARALYZ_HEAL = 31,
	BURN_HEAL = 32,
	ICE_HEAL = 33,
	FULL_HEAL = 34,
	BICYCLE = 35,
	SPRING_WEEKEND_TICKET = 36,
	SPECIAL_PACKAGE = 37,
	EXP_ALL = 38,
	CALCIUM = 39,
	CARBOS = 40,
	IRON = 41,
	PROTEIN = 42,
	ZINC = 43,
	HP_UP = 44,
	PP_UP = 45,
	RARE_CANDY = 46,
	ESCAPE_ROPE = 47,
	REPEL = 48,
	SUPER_REPEL = 49,
	MAX_REPEL = 50,
	NUGGET = 51,
	MOONSTONE=52,
	FIRESTONE=53,
	LEAFSTONE=54,
	WATERSTONE=55,
	THUNDERSTONE=56,
	FAKE_ID=57,
	TM06_TOXIC=58,
	TM14_BLIZZARD=59,
	TM22_SOLARBEAM=60,
	TM46_PSYWAVE=61,
	HM01_CUT=62,
	HMO2_FLY=63,
	HM03_SURF=64,
	HM04_STRENGTH=65,
	HM05_FLASH=66,
	DOME_FOSSIL=67,
	HELIX_FOSSIL=68,
	OLD_AMBER=69,
	TM04_WHIRLWIND=70,
	TM01_MEGAPUNCH=71,
	TM12_WATERGUN=72,
	VODKA_FIFTH=73,
	KAHLUA=74,
	TONIC_WATER=75,
	JAGER_BOMB=76,
	CREME_DE_MENTHE=77,
	MOONSHINE=78,
	SMIRNOFF_ICE=79,
	TM24_THUNDERBOLT=80,
	APPLE=81,
	TM28_DIG=82,
	TM45_THUNDERWAVE=83,
	POKEFLUTE=84,
	TM19_SEISMIC_TOSS=85,
	TM44_REST=86,
	TM08_BODY_SLAM=87,
	TM38_FIREBLAST = 88,
	DONUT = 89,
	RISD_ID =90,
	TM42_DREAMEATER = 91,
	TM43_SKY_ATTACK = 92,
	KITCHEN_KEY = 93,
	ZIPCAR_CARD = 94,
	LADDER = 95,
	TM07_HORN_DRILL=96,
	TM10_DOUBLEEDGE=97,
	TM09_TAKEDOWN=98,
	SUGGS_POTION=99,
	TM20_RAGE=100,
	STUFFED_DINO=101,
	TM21_MEGA_DRAIN=102,
	TM37_EGG_BOMB=103,
	TM32_DOUBLE_TEAM=104,
	TM40_SKULL_BASH=105,
	TM30_TELEPORT=106,
	TM23_DRAGON_RAGE=107;
	
	public Item(int index, String name, int effect, String type, boolean battle, boolean instant, int remaining, int buyprice, int sellprice, String desc, String itemIcon){
		_index=index;
		_effect = effect;
		_name = name;
		//Refers not to Pokemon type but to the type of effect (HP heal, confusion, etc.)
		_type = type;
		_remaining = remaining;
		_buyPrice=buyprice;
		_sellPrice=sellprice;
		//Can this item be used in battle?
		_battle = battle;
		_instant=instant;
		_desc=desc;
		try {
			_itemIcon=ImageIO.read(this.getClass().getResource(itemIcon));
		} catch (IOException e) {
		}
		
		_abilityRequired = false;
		if(_type.equalsIgnoreCase("Stone") || _type == "TM" || _type == "HM"){
			_abilityRequired = true;
		}
		
		compatibilityVector = null;
	}
	
	public void useItem(NewBattleScreen nbs, Pokemon user, Pokemon receiver, int parameter){
		if (_type=="PP" && _remaining>=0) {
			if(user.getAttacks().get(parameter).getCurrentPP() < user.getAttacks().get(parameter).getMaxPP()){
				if(user.getAttacks().get(parameter).getCurrentPP()+_effect > user.getAttacks().get(parameter).getMaxPP()){
					user.getAttacks().get(parameter).setCurrentPP(user.getAttacks().get(parameter).getMaxPP());
				}
				else {
					user.getAttacks().get(parameter).setCurrentPP(user.getAttacks().get(parameter).getCurrentPP()+_effect);
				}
				_remaining=_remaining-1;
			}			
		}
		
		if (_type=="Elixir" && _remaining>=0) {
			for (int i=0; i<user.getAttacks().size();i++){
				if(user.getAttacks().get(i).getCurrentPP()+_effect > user.getAttacks().get(i).getMaxPP()){
					user.getAttacks().get(i).setCurrentPP(user.getAttacks().get(i).getMaxPP());
				}
				else{
					user.getAttacks().get(i).setCurrentPP(user.getAttacks().get(i).getCurrentPP()+_effect);
				}
			}
			_remaining=_remaining-1;
		}
		
		if (_type=="HP" && _remaining>=0) {
			if(user.getCurrentHP() < user.getMaxHP()){
				if(user.getCurrentHP()+_effect > user.getMaxHP()){
					nbs.setHPListenerChange(user, user.getMaxHP()-user.getCurrentHP());
				}
				else {
				nbs.setHPListenerChange(user, _effect);
				}
				_remaining=_remaining-1;
			}			
		}
		
		if (_type=="Revive" && _remaining>=0) {
			if(user.getCurrentHP()==0){
				nbs.setHPListenerChange(user, (int)(user.getMaxHP()*(this.getEffect()/2.0)));
				_remaining=_remaining-1;
			}			
		}
		
		if (_type=="FullRestore" && _remaining>=0) {
			if((user.getCurrentHP()!=0 && user.getCurrentHP()<user.getMaxHP()) || user.getStatus()!=0){
				user.setStatus(0);
				nbs.setHPListenerChange(user, user.getMaxHP()-user.getCurrentHP());
				_remaining=_remaining-1;
			}			
		}
		
		
		if (_type=="Ball" && _remaining>=0){
			//If the person already has over 6 active, set belt to 7.
			int done=0;
			
			if (nbs.getPlayer().getAllActive().size()<6 && done<1){
				Pokemon receiver2 = Pokemon.clone(receiver);
				receiver2.setBelt(nbs.getPlayer().getAllActive().size()+1);
				nbs.getPlayer().getAllActive().add(receiver2);
				done=done+1;
			}
			
			//If still not added yet, add it last.
			//The newest Pokemon will be at the last slot of each of these vectors.
			if(nbs.getPlayer().getAllActive().size()>=6 && done<1){
				receiver.setBelt(7);
				nbs.getPlayer().getAllPC().add(receiver);
			}
				
			
			//Once done, re-sort everyone.
			//FIXME: gbs.getPlayer().sortActivePokemon();			
			
			_remaining=_remaining-1; 
		}
		
		if (_type=="Status" && _remaining>=0) {
			if(user.getStatus()==this.getEffect() || this.getEffect()==9000){
				user.setStatus(0);
				_remaining=_remaining-1;
				
			}
		}
	}
	
	public void useOOBItem(ItemInventory2 ii, Pokemon user, int parameter){
		if (_type=="PP" && _remaining>=0) {
			if(user.getAttacks().get(parameter).getCurrentPP() < user.getAttacks().get(parameter).getMaxPP()){
				if(user.getAttacks().get(parameter).getCurrentPP()+_effect > user.getAttacks().get(parameter).getMaxPP()){
					user.getAttacks().get(parameter).setCurrentPP(user.getAttacks().get(parameter).getMaxPP());
				}
				else {
					user.getAttacks().get(parameter).setCurrentPP(user.getAttacks().get(parameter).getCurrentPP()+_effect);
				}
				_remaining=_remaining-1;
			}			
		}
		
		if (_type=="Elixir" && _remaining>=0) {
			for (int i=0; i<user.getAttacks().size();i++){
				if(user.getAttacks().get(i).getCurrentPP()+_effect > user.getAttacks().get(i).getMaxPP()){
					user.getAttacks().get(i).setCurrentPP(user.getAttacks().get(i).getMaxPP());
				}
				else{
					user.getAttacks().get(i).setCurrentPP(user.getAttacks().get(i).getCurrentPP()+_effect);
				}
			}
			_remaining=_remaining-1;
		}
		
		if (_type=="HP" && _remaining>=0) {
			if(user.getCurrentHP() < user.getMaxHP()){
				if(user.getCurrentHP()+_effect > user.getMaxHP()){
					ii.setHPListenerChange(user, user.getMaxHP()-user.getCurrentHP());
				}
				else {
				ii.setHPListenerChange(user, _effect);
				}
				_remaining=_remaining-1;
			}
		}
		
		if (_type=="Revive" && _remaining>=0) {
			if(user.getCurrentHP()==0){
			ii.setHPListenerChange(user, (int)(user.getMaxHP()*(this.getEffect()/2.0)));
				_remaining=_remaining-1;
			}			
		}
		
		if (_type=="FullRestore" && _remaining>=0) {
			if((user.getCurrentHP()!=0 && user.getCurrentHP()<user.getMaxHP()) || user.getStatus()!=0){
				user.setStatus(0);
				ii.setHPListenerChange(user, user.getMaxHP()-user.getCurrentHP());
				_remaining=_remaining-1;
			}			
		}
		
		
		if (_type=="Ball" && _remaining>=0){
			//Nothing. 
		}
		
		if (_type=="Status" && _remaining>=0) {
			if(user.getStatus()==this.getEffect() || this.getEffect()==9000){
				user.setStatus(0);
				_remaining=_remaining-1;
				
			}
		}
		if (_type=="StatUp" && _remaining>=0) {
			//There is another condition that goes here to prevent maxing out stats.
			//Figure it out. TODO
			if(1==1){
				switch(this.getEffect()){
				case 1: 
					user.setSpAtkStat(user.getSpAtkStat()+1);
					break;
				case 2:
					user.setSpeed(user.getSpeed()+1);
					break;
				case 3: 
					user.setDefStat(user.getDefStat()+1);
					break;
				case 4: 
					user.setAtkStat(user.getAtkStat()+1);
					break;
				case 5: 
					user.setSpDefStat(user.getSpDefStat()+1);
					break;
				case 6: 
					user.setMaxHP((int)Math.round(105.0/100.0* (double)user.getMaxHP()));
					user.setCurrentHP((int)Math.round(105.0/100.0* (double)user.getCurrentHP()));
					break;
				}

				_remaining=_remaining-1;
				
			}
		}
		
	}
	
	public String Bernoulli(double ProbOfCatch){

		Random r = new Random();
		String result;
	
		int rand = r.nextInt(100);
		
		if(rand<ProbOfCatch) 
			result="Yes";
		else 
			result="No";
		
		return result;
	}


	public double calcProb(int rate, Pokemon receiver){
		//Set status modifier.
		int status0 = receiver.getStatus();
		
		if (status0==1)
			_status=4;
		if (status0==2 || status0==3 || status0 ==4)
			_status=3;
		else{_status=2;}
		
		//Parameters for probability
		_a = (double) ( (3*receiver.getMaxHP()-2*receiver.getCurrentHP())*receiver.getRate()*rate/(3*receiver.getMaxHP())*_status/4);
		
		double b0=(_a/(pow(2.0,8.0) -1));
		_b = (pow(2.0,16.0) -1) * pow( b0 ,0.25); 
		
		//Calculate _prob
		if (_a>=255){
			_prob=1;}
		else {
			double prob0= (_b+1) / (pow(2.0,16.0));
			_prob=pow(prob0,4.0);
			}
	
		return _prob;	
	}

	public String getName(){
		return _name;
	}
	
	public String getType(){
		return _type;
	}
	
	public int getEffect(){
		return _effect;
	}
	public int getBuyPrice(){
		return _buyPrice;
	}
	public int getSellPrice(){
		return _sellPrice;
	}
	public String getDesc(){
		return _desc;
	}
	
	public BufferedImage getIcon(){
		return _itemIcon;
	}
	
	public boolean isBattleAppropriate(){
		return _battle;
	}
	
	public boolean isOOBInappropriate(){
		return _OOB;
	}
	
	public void setOOBInappropriate(boolean b){
		_OOB=b;
	}
	
	public boolean canUseInstantly(){
		return _instant;
	}
	
	public boolean requiresAbility(){
		return _abilityRequired;
	}
	
	public boolean isCompatibleWith(Pokemon p){
		if(this.compatibilityVector != null){
			for(int i = 0; i < compatibilityVector.length; i++){
				if(compatibilityVector[i] == p.getDexNum())
					return true;
			}
		}
		return false;
	}
	

	public void setCompatibility(int[] compat){
		compatibilityVector = compat;
	}
	
	public int getRemaining(){
		return _remaining;
	}
	//To Directly set value
	public void setRemaining(int remaining){
		_remaining = remaining;
	}
	//To Increase value by a set amount. Can also be used to decrease, just use negative i.
	public void increaseByi(int i){
		_remaining = _remaining+i;
	}
	
	
	//==================================================
	//GAP==================================================
	//==================================================
	
	
	public void setIndex(int _index) {
		this._index = _index;
	}

	public int getIndex() {
		return _index;
	}


	/**HP Heals
	 * */
	public static class Potion extends Item{
		public Potion() throws IOException{
			super(0,"Potion", 20, "HP", true, false, 0, 300, 150,"Restores 20HP to the user.",(("/PokemonFiles/ItemIcons/potion.gif")));
			//super(0,"Brainstem Array", 1, "HP", true, false, 0, 300,150,"An interface used to stimulate and record from the nerves.",(("/PokemonFiles/ItemIcons/fake-id.png")));
		}
	}
	public static class OrganicPotion extends Item{
		public OrganicPotion() throws IOException{
			super(1,"Organic Potion", 20, "HP", true, false, 0, 600, 350,"Restores 20HP to the user. Made of organic herbs.",(("/PokemonFiles/ItemIcons/potion.gif")));
		}
	}
	public static class SuperPotion extends Item{
		public SuperPotion() throws IOException{
			super(2,"Super Potion", 50, "HP", true, false, 0, 700,350,"Restores 50HP to the user.",(("/PokemonFiles/ItemIcons/super-potion.gif")));
		}
	}
	public static class HyperPotion extends Item{
		public HyperPotion() throws IOException{
			super(3,"Hyper Potion", 200, "HP", true, false, 0, 1200,600,"Restores 200HP to the user.",(("/PokemonFiles/ItemIcons/hyper-potion.gif")));
		}
	}
	public static class MaxPotion extends Item{
		public MaxPotion() throws IOException{
			super(4,"Max Potion", 10000, "HP", true, false, 0, 2500,1250,"Restores full HP to the user.",(("/PokemonFiles/ItemIcons/max-potion.gif")));
		}
	}
	public static class BubbleTea extends Item{
		public BubbleTea() throws IOException{
			super(5,"Bubble Tea", 80, "HP", true, false, 0, 350,175,"Restores 80HP to the user.",(("/PokemonFiles/ItemIcons/bubble-tea.png")));
		}
	}
	public static class FreshWater extends Item{
		public FreshWater() throws IOException{
			super(6,"Fresh Water", 50, "HP", true, false, 0, 200,100,"Restores 50HP to the user.",(("/PokemonFiles/ItemIcons/fresh-water.gif")));
		}
	}
	public static class SodaPop extends Item{
		public SodaPop() throws IOException{
			super(7,"Soda Pop", 80, "HP", true, false, 0, 300,150,"Restores 80HP to the user.",(("/PokemonFiles/ItemIcons/soda-pop.gif")));
		}
	}
	public static class FullRestore extends Item{
		public FullRestore() throws IOException{
			//Restores all HP and all status.
			super(8,"Full Restore", 10000, "FullRestore", true, false, 0, 3000,1500,"Restores user to full HP and healthy status.",(("/PokemonFiles/ItemIcons/full-restore.gif")));
		}
	}
	
	/**PP Heals
	 * */
	public static class Elixir extends Item{
		public Elixir() throws IOException{
			//Restores 10PP to EACH move.
			super(9,"Elixir", 10, "Elixir", true, false, 0, 0,0,"Restores 10PP to all of the user's attacks.",(("/PokemonFiles/ItemIcons/elixer.gif")));
		}
	}
	public static class Ether extends Item{
		public Ether() throws IOException{
			//Restores 10PP to ONE move.
			super(10,"Ether", 10, "PP", true, false, 0, 0,0,"Restores 10PP to one of the user's attacks.",(("/PokemonFiles/ItemIcons/ether.gif")));
		}
	}
	public static class MaxElixir extends Item{
		public MaxElixir() throws IOException{
			//Restores All PP to ALL moves.
			super(11,"Max Elixir", 1000, "Elixir", true, false, 0, 0,0,"Restores full PP to all of the user's attacks.",(("/PokemonFiles/ItemIcons/max-elixer.gif")));
		}
	}
	public static class MaxEther extends Item{
		public MaxEther() throws IOException{
			//Restores All PP to ONE move.
			super(12,"Max Ether", 1000, "PP", true, false, 0, 0,0,"Restores full PP to one of the user's attacks.",(("/PokemonFiles/ItemIcons/max-ether.gif")));
			
		}
	}
	
	/**Revives
	 * 1 = to half health
	 * 9001 = to max health
	 * */
	public static class Revive extends Item{
		public Revive() throws IOException{
			//Restores All PP to ONE move.
			super(13,"Revive", 1, "Revive", true, false, 0, 1500,750,"Revives a fainted Pokemon.",(("/PokemonFiles/ItemIcons/revive.gif")));
		}
	}
	public static class MaxRevive extends Item{
		public MaxRevive() throws IOException{
			//Restores All PP to ONE move.
			super(14,"Max Revive", 2, "Revive", true, false, 0, 0,0,"Revives a fainted Pokemon and restores full PP to all moves.",(("/PokemonFiles/ItemIcons/max-revive.gif")));
		}
	}
	
	/**Boost Items
	 * 1=Accuracy
	 * 2=Atk
	 * 3=Def
	 * 4=Special
	 * 5=Speed
	 * 6=Critical Hit
	 * */
	public static class XAccuracy extends Item{
		public XAccuracy() throws IOException{
			super(15,"X Accuracy", 1, "Boost", true, true, 0, 950,475,"Raises the accuracy of the user. Wears off if user switches out.",(("/PokemonFiles/ItemIcons/xaccuracy.png")));
			this.setOOBInappropriate(true);
		}
	}
	public static class XAttack extends Item{
		public XAttack() throws IOException{
			super(16,"X Attack", 2, "Boost", true, true, 0, 500,250,"Raises the attack of the user. Wears off if user switches out.",(("/PokemonFiles/ItemIcons/xattack.png")));
			this.setOOBInappropriate(true);
		}
	}
	public static class XDefend extends Item{
		public XDefend() throws IOException{
			super(17,"X Defend", 3, "Boost", true, true, 0, 550,275,"Raises the defense of the user. Wears off if user switches out.",(("/PokemonFiles/ItemIcons/xdefend.png")));
			this.setOOBInappropriate(true);
		}
	}
	public static class XSpecial extends Item{
		public XSpecial() throws IOException{
			super(18,"X Special", 4, "Boost", true, true, 0, 350,175,"Raises the special of the user. Wears off if user switches out.",(("/PokemonFiles/ItemIcons/xspecial.png")));
			this.setOOBInappropriate(true);
		}
	}
	public static class XSpeed extends Item{
		public XSpeed() throws IOException{
			super(19,"X Speed", 5, "Boost", true, true, 0, 350,175,"Raises the speed of the user. Wears off if user switches out.",(("/PokemonFiles/ItemIcons/xspeed.png")));
			this.setOOBInappropriate(true);
		}
	}
	public static class DireHit extends Item{
		public DireHit() throws IOException{
			super(20,"Dire Hit", 6, "Boost", true, true, 0, 650,325,"Raises critical-hit ratio greatly. Wears off if user switches out.",(("/PokemonFiles/ItemIcons/direhit.png")));
			this.setOOBInappropriate(true);
		}
	}
	public static class GuardSpec extends Item{
		public GuardSpec() throws IOException{
			super(21,"Guard Spec", 7, "Boost", true, true, 0, 700,350,"Guards against stat reductions. Wears off if user switches out.",(("/PokemonFiles/ItemIcons/guardspec.png")));
			this.setOOBInappropriate(true);
		}
	}
	
	
	/**PokeBalls
	 * */
	public static class PokeBall extends Item{
		public PokeBall() throws IOException{
			super(22,"Brainstem Array", 1, "Ball", true, true, 0, 300,150,"An interface used to stimulate and record from the nerves.",(("/PokemonFiles/ItemIcons/fake-id.png")));
			//super(22,"PokeBall", 2, "Ball", true, true, 0, 200,100,"Used for catching wild Pokemon.",(("/PokemonFiles/ItemIcons/pokeball.gif")));
			this.setOOBInappropriate(true);
		}
	}
	public static class FairTradePokeBall extends Item{
		public FairTradePokeBall() throws IOException{
			super(23,"Fair-Trade Ball", 2, "Ball", true, true, 0, 400,200,"Fair-trade handicraft used for catching wild Pokemon.",(("/PokemonFiles/ItemIcons/pokeball.gif")));
			this.setOOBInappropriate(true);
		}
	}
	public static class GreatBall extends Item{
		public GreatBall() throws IOException{
			super(24,"Great Ball", 3, "Ball", true, true, 0, 600,300,"High-performance ball with a higher catch rate than a Pokéball.",(("/PokemonFiles/ItemIcons/greatball.gif")));
			this.setOOBInappropriate(true);
		}
	}
	public static class UltraBall extends Item{
		public UltraBall() throws IOException{
			super(25,"Ultra Ball", 4, "Ball", true, true, 0, 1200,600,"Ultra-performance ball with a higher catch rate than a Great Ball.",(("/PokemonFiles/ItemIcons/ultraball.gif")));
			this.setOOBInappropriate(true);
		}
	}
	public static class MasterBall extends Item{
		public MasterBall() throws IOException{
			super(26,"Thesis", 1, "Ball", true, true, 0, 0,0,"The ultimate paper. It will graduate any student without fail.",(("/PokemonFiles/ItemIcons/masterball.gif")));
			this.setOOBInappropriate(true);
		}
	}
	
	/**Key for Status ailments:
	 * 0=Normal
	 * 1=Sleep
	 * 2=Poisoned
	 * 3=Burned
	 * 4=Frozen
	 * 5=Paralyzed
	 * Set item to -X to cure X ailment. The sum = 0. 
	*/
	public static class Awakening extends Item{
		public Awakening () throws IOException{
			super(27,"Awakening", 1, "Status", true, false, 0, 250,125,"Awakens a sleeping Pokemon.",(("/PokemonFiles/ItemIcons/awakening.gif")));
		}
	}
	public static class Antidote extends Item{
		public Antidote () throws IOException{
			super(28,"Antidote", 2, "Status", true, false, 0, 100,50,"Cures a poisoned Pokemon.",(("/PokemonFiles/ItemIcons/antidote.gif")));
		}
	}
	public static class HolisticAntidote extends Item{
		public HolisticAntidote () throws IOException{
			super(29,"Holistic PsnHeal", 2, "Status", true, false, 0, 200,100,"An ancient cure for a poisoned Pokemon.",(("/PokemonFiles/ItemIcons/antidote.gif")));
		}
	}
	public static class ParalyzHeal extends Item{
		public ParalyzHeal () throws IOException{
			super(30,"Paralyz Heal", 3, "Status", true, false, 0, 200,100,"Cures a paralyzed Pokemon.",(("/PokemonFiles/ItemIcons/paralyz-heal.gif")));
		}
	}
	public static class HolisticParalyzHeal extends Item{
		public HolisticParalyzHeal () throws IOException{
			super(31,"Holistic ParHeal", 3, "Status", true, false, 0, 400,200,"An ancient cure for a paralyzed Pokemon.",(("/PokemonFiles/ItemIcons/paralyz-heal.gif")));
		}
	}
	public static class BurnHeal extends Item{
		public BurnHeal () throws IOException{
			super(32,"Burn Heal", 4, "Status", true, false, 0, 250,125,"Cures a burned Pokemon.",(("/PokemonFiles/ItemIcons/burn-heal.gif")));
		}
	}
	public static class IceHeal extends Item{
		public IceHeal () throws IOException{
			super(33,"Ice Heal", 5, "Status", true, false, 0, 250,125,"Defrosts a frozen Pokemon.",(("/PokemonFiles/ItemIcons/ice-heal.gif")));
		}
	}
	
	public static class FullHeal extends Item{
		public FullHeal () throws IOException{
			super(34,"Full Heal", 9000, "Status", true, false, 0, 250,125,"Relieves a Pokemon of all status ailments.",(("/PokemonFiles/ItemIcons/antidote.gif")));
		}
	}
	
	
	/**NonBattle Items
	 * */
	/**KeyItems*/
	public static class Bicycle extends Item{
		public Bicycle () throws IOException{
			super(35,"Bicycle", 1, "Bike", false, false, 0, 1000000,-1,"A baller bike that allows for faster travel.",(("/PokemonFiles/ItemIcons/bike.png")));
		}
	}
	public static class SpringWeekendTicket extends Item{
		public SpringWeekendTicket () throws IOException{
			super(36,"Weekend Pass", 1, "KeyItem", false, false, 0, 17,-1,"A ticket to the Spring Weekend concert.",(("/PokemonFiles/ItemIcons/weekend-pass.png")));
		}
	}
	public static class SpecialPackage extends Item{
		public SpecialPackage () throws IOException{
			super(37,"RA's Parcel", 1, "KeyItem", false, false, 0, 0,-1,"A small package for your RA.",(("/PokemonFiles/ItemIcons/package.png")));
		}
	}
	public static class EXPAll extends Item{
		public EXPAll () throws IOException{
			super(38,"EXP. All", 1, "KeyItem", false, false, 0, 0,-1,"Shares EXP with all active Pokemon.",(("/PokemonFiles/ItemIcons/exp-share.gif")));
		}
	}
	
	/**Misc.
	 * */
	public static class Calcium extends Item{
		//Special Attack
		public Calcium () throws IOException{
			super(39,"Calcium", 1, "StatUp", false, false, 0, 9800,4900,"Raises the base special attack stat of a Pokemon.",(("/PokemonFiles/ItemIcons/calcium.gif")));
		}
	}
	public static class Carbos extends Item{
		//Speed
		public Carbos () throws IOException{
			super(40,"Carbos", 2, "StatUp", false, false, 0, 9800,4900,"Raises the base speed stat of a Pokemon.",(("/PokemonFiles/ItemIcons/carbos.gif")));
		}
	}
	public static class Iron extends Item{
		//Defense
		public Iron () throws IOException{
			super(41,"Iron", 3, "StatUp", false, false, 0, 9800,4900,"Raises the base defense stat of a Pokemon.",(("/PokemonFiles/ItemIcons/iron.gif")));
		}
	}
	public static class Protein extends Item{
		//Attack
		public Protein() throws IOException{
			super(42,"Protein", 4, "StatUp", false, false, 0, 9800,4900,"Raises the base attack stat of a Pokemon.",(("/PokemonFiles/ItemIcons/protein.gif")));
		}
	}
	public static class Zinc extends Item{
		//Special Defense
		public Zinc () throws IOException{
			super(43,"Zinc", 5, "StatUp", false, false, 0, 9800,4900,"Raises the base special defense stat of a Pokemon.",(("/PokemonFiles/ItemIcons/zinc.gif")));
		}
	}
	public static class HPUp extends Item{
		//HP Up: Is 5% increase reasonable?
		//See OOB Item for ratio.
		public HPUp () throws IOException{
			super(44,"HP Up", 6, "StatUp", false, false, 0, 9800,4900,"Raises the base HP of a Pokemon.",(("/PokemonFiles/ItemIcons/hp-up.gif")));
		}
	}
	public static class PPUp extends Item{
		//PP Up
		public PPUp () throws IOException{
			super(45,"PP Up", 1, "PPUp", false, false, 0, 0,0,"Raises the maximum PP of an attack by one.",(("/PokemonFiles/ItemIcons/pp-up.gif")));
		}
	}
	public static class RareCandy extends Item{
		//Level Up
		public RareCandy () throws IOException{
			super(46,"Rare Candy", 1, "RareCandy", false, false, 0, 0,0,"Raises a Pokemon's level by one.",(("/PokemonFiles/ItemIcons/rare-candy.gif")));
		}
	}
	public static class EscapeRope extends Item{
		//Escapes to last PokeCenter
		public EscapeRope () throws IOException{
			super(47,"Escape Rope", 1, "EscapeRope", false, false, 0, 550,275,"Use to escape instantly from a cave or a dungeon.",(("/PokemonFiles/ItemIcons/escaperope.png")));
		}
	}
	public static class Repel extends Item{
		//Repels for 100 steps.
		public Repel() throws IOException{
			super(48,"Repel", 100, "Repel", false, false, 0, 350,175,"Repels wild Pokemon for 100 steps.",(("/PokemonFiles/ItemIcons/repel.png")));
		}
	}
	public static class SuperRepel extends Item{
		//Repels for 200 steps.
		public SuperRepel () throws IOException{
			super(49,"Super Repel", 200, "Repel", false, false, 0, 500,250,"Repels wild Pokemon for 200 steps.",(("/PokemonFiles/ItemIcons/superrepel.png")));
		}
	}
	public static class MaxRepel extends Item{
		//Repels for 200 steps.
		public MaxRepel () throws IOException{
			super(50,"Max Repel", 250, "Repel", false, false, 0, 700,350,"Repels wild Pokemon for 250 steps.",(("/PokemonFiles/ItemIcons/maxrepel.png")));
		}
	}
	public static class Nugget extends Item{
		//Exists to be sold.
		public Nugget () throws IOException{
			super(51,"Nugget", 0, "Misc", false, false, 0, 2500,1250,"A nugget of pure gold. Can be sold at a high price.",(("/PokemonFiles/ItemIcons/nugget.png")));
			this.setOOBInappropriate(true);
		}
	}
	
	/**Evolution Stones
	 * */
	public static class MoonStone extends Item{
		public MoonStone () throws IOException{
			super(52,"MoonStone", 1, "Stone", false, false, 0, 0,0,"Makes certain species of Pokemon evolve.",(("/PokemonFiles/ItemIcons/moonstone.gif")));
			int[] compat = {30,33,35,39};
			this.setCompatibility(compat);
		}
	}
	public static class FireStone extends Item{
		public FireStone () throws IOException{
			super(53,"Fire Stone", 1, "Stone", false, false, 0, 2100,1050,"Makes certain species of Pokemon evolve.",(("/PokemonFiles/ItemIcons/firestone.gif")));
			int[] compat = {37, 58, 133};
			this.setCompatibility(compat);
		}
	}
	public static class LeafStone extends Item{
		public LeafStone () throws IOException{
			super(54,"LeafStone", 0, "Stone", false, false, 0, 0,0,"Makes certain species of Pokemon evolve.",(("/PokemonFiles/ItemIcons/leafstone.gif")));
			int[] compat = {44,70,102};
			this.setCompatibility(compat);
		}
	}
	public static class WaterStone extends Item{
		public WaterStone () throws IOException{
			super(55,"WaterStone", 0, "Stone", false, false, 0, 2100,1050,"Makes certain species of Pokemon evolve.",(("/PokemonFiles/ItemIcons/waterstone.gif")));
			int[] compat = {61, 90, 120, 133};
			this.setCompatibility(compat);
		}
	}
	public static class Thunderstone extends Item{
		public Thunderstone () throws IOException{
			super(56,"Thunderstone", 0, "Stone", false, false, 0, 2100,1050,"Makes certain species of Pokemon evolve.",(("/PokemonFiles/ItemIcons/thunderstone.gif")));
			int[] compat = {25, 133};
			this.setCompatibility(compat);
		}
	}
	public static class FakeID extends Item{
		public FakeID () throws IOException{
			super(57,"Fake ID", 1, "KeyItem", false, false, 0, 0,-1,"A picture ID of someone over 21 that looks marginally like you.",(("/PokemonFiles/ItemIcons/fake-id.png")));
		}
	}
	
	public static class TM06_Toxic extends Item{
		public TM06_Toxic () throws IOException{
			super(58,"TM06: Toxic", 92, "TM", false, false, 0, 2100,1050,"A move that leaves the target badly poisoned.",(("/PokemonFiles/ItemIcons/poison.gif")));
			
		}
		public boolean isCompatibleWith(Pokemon p){
			return true;
		}
	}
	public static class TM14_Blizzard extends Item{
		public TM14_Blizzard () throws IOException{
			super(59,"TM14: Blizzard", 59, "TM", false, false, 0, 2100,1050,"A howling blizzard is summoned to strike the enemy.",(("/PokemonFiles/ItemIcons/ice.gif")));
			int[] compat = {7,8,9,19,20,29,30,31,32,33,34,35,36,39,40,54,55,60,61,62,72,73,79,80,86,87,90,91,98,99,104,105,108,111,112,113,115,116,117,118,119,120,121,124,128,130,131,134,137,138,139,140,141,143,144,147,148,149,150,151};
			this.setCompatibility(compat);
		}
	}
	public static class TM22_Solar_Beam extends Item{
		public TM22_Solar_Beam () throws IOException{
			super(60,"TM22: Solar Beam", 76, "TM", false, false, 0, 2100,1050,"User gathers light, then blasts a bundled beam on the 2nd turn. ",(("/PokemonFiles/ItemIcons/grass.gif")));
			int[] compat = {1,2,3,6,12,15,35,36,38,39,40,43,44,45,46,47,48,49,59,69,70,71,77,78,102,103,108,113,114,115,122,128,131,137,143,146,150,151};
			this.setCompatibility(compat);
		}
	}
	public static class TM46_Psywave extends Item{
		public TM46_Psywave () throws IOException{
			super(61,"TM46: Psywave", 149, "TM", false, false, 0, 2100,1050,"An odd psychic wave. The attack varies in intensity.",(("/PokemonFiles/ItemIcons/psychic.gif")));
			int[] compat = {12, 35, 36, 39,40,48,49,60,61,62,63,64,65,79,80,92,93,94,96,97,102,103,113,120,121,122,124,125,126,131,137,143,150,151};
			this.setCompatibility(compat);
		}
	}
	
	public static class HM01_Cut extends Item{
		public HM01_Cut () throws IOException{
			super(62,"HM01: Cut", 15, "HM", false, false, 0, 0,-1,"Cuts enemy with a scythe or a claw.",(("/PokemonFiles/ItemIcons/normal.gif")));
			int[] compat = {1,2,3,4,5,6,15,19,20,27,28,29,30,31,32,33,34,43,44,45,46,47,50,51,52,53,69,70,71,72,73,83,98,99,108,112,114,115,123,127,141,149,151};
			this.setCompatibility(compat);
		}
	}
	public static class HM02_Fly extends Item{
		public HM02_Fly () throws IOException{
			super(63,"HM02: Fly", 19, "HM", false, false, 0, 0,-1,"The user soars, then strikes its target on the second turn.",(("/PokemonFiles/ItemIcons/flying.gif")));
			int[] compat = {6,16,17,18,21,22,41,42,83,84,85,142,144,145,146,149,151};
			this.setCompatibility(compat);
		}
	}
	public static class HM03_Surf extends Item{
		public HM03_Surf () throws IOException{
			super(64,"HM03: Surf", 57, "HM", false, false, 0, 0,-1,"The enemy is swamped with a giant wave of water.",(("/PokemonFiles/ItemIcons/water.gif")));
			int[] compat = {7,8,9,31,34,54,55,60,61,62,72,73,79,80,86,87,90,91,98,99,108,112,115,116,117,118,119,120,121,128,130,131,134,138,139,140,141,143,147,148,149,151};
			this.setCompatibility(compat);
		}
	}
	public static class HM04_Strength extends Item{
		public HM04_Strength () throws IOException{
			super(65,"HM04: Strength", 70, "HM", false, false, 0, 0,-1,"The enemy is slugged with a punch thrown at maximum power.",(("/PokemonFiles/ItemIcons/fighting.gif")));
		}
	}
	public static class HM05_Flash extends Item{
		public HM05_Flash () throws IOException{
			super(66,"HM05: Flash", 148, "HM", false, false, 0, 0,-1,"The user flashes a light that cuts the enemy's accuracy.",(("/PokemonFiles/ItemIcons/normal.gif")));
			int[] compat = {1,2,3,12,15,25,26,35,36,39,40,43,44,45,46,47,48,49,52,53,54,55,63,64,65,69,70,71,79,80,81,82,96,97,100,101,102,103,109,110,113,114,120,121,122,124,125,135,137,145,150,151};
			this.setCompatibility(compat);
		}
	}
	
	public static class Dome_Fossil extends Item{
		public Dome_Fossil() throws IOException{
			super(67, "Dome Fossil", 0, "KeyItem", false, false, 0, 0, -1, "A mysterious, dome-shaped fossil of an ancient Pokemon.", (("/PokemonFiles/ItemIcons/bubble-tea.png")));
		}
	}
	
	public static class Helix_Fossil extends Item{
		public Helix_Fossil() throws IOException{
			super(68, "Helix Fossil", 0, "KeyItem", false, false, 0, 0, -1, "A mysterious, helix-shaped fossil of an ancient Pokemon.", (("/PokemonFiles/ItemIcons/bubble-tea.png")));
		}
	}
	
	public static class Old_Amber extends Item{
		public Old_Amber() throws IOException{
			super(69, "Old Amber", 0, "KeyItem", false, false, 0, 0, -1, "Strange fossilized sap with bones inside that look like a bird's wing.", (("/PokemonFiles/ItemIcons/bubble-tea.png")));
		}
	}
	
	
	public static class TM04_Whirlwind extends Item{
		public TM04_Whirlwind () throws IOException{
			super(70,"TM04: Whirlwind", 18, "TM", false, false, 0, 0,500,"Blows away the foe & ends battle.",(("/PokemonFiles/ItemIcons/normal.gif")));
			int[] compat = {12,16,17,18,21,22,41,42,49,83,84,85,142,144,145,146,151};
			this.setCompatibility(compat);
		}
	}
	
	public static class TM01_MegaPunch extends Item{
		public TM01_MegaPunch () throws IOException{
			super(71,"TM01: Mega Punch", 5, "TM", false, false, 0, 3000,1500,"A powerful punch thrown very hard.",(("/PokemonFiles/ItemIcons/normal.gif")));
			int[] compat = {4,5,6,7,8,9,25,26,31,34,35,36,39,40,54,55,56,57,61,62,63,64,65,66,67,68,74,75,76,80,94,96,97,104,105,106,107,108, 112, 113,115,122,124,125,126,143,150,151};
			this.setCompatibility(compat);
		}
	}
	
	public static class TM12_WaterGun extends Item{
		public TM12_WaterGun () throws IOException{
			super(72,"TM12: Water Gun", 55, "TM", false, false, 0, 0,500,"Squirts water to attack.",(("/PokemonFiles/ItemIcons/water.gif")));
			int[] compat = {7,8,9,19,20,30,31,33,34,35,36,39,40,52,53,54,55,60,61,62,72,73,79,80,86,87,90,91,98,99,104,105,108,112,113,115,116,117,118,119,120,121,124,130,131,134,138,139,140,141,143,144,147,148,149,151};
			this.setCompatibility(compat);
		}
	}

	public static class VodkaFifth extends Item{
		public VodkaFifth() throws IOException{
			super(73,"Vodka Fifth", 30, "HP", true, false, 0, 350, 175,"Straight vodka, makes your Pokemon feel ALIVE! (Restores 30HP).",(("/PokemonFiles/ItemIcons/potion.gif")));
		}
	}
	public static class Kahlua extends Item{
		public Kahlua () throws IOException{
			super(74,"Kahlua", 1, "Status", true, false, 0, 300,150,"WAKE UP SLEEPYHEAD! It's time to party! (Awakens a sleeping Pkmn).",(("/PokemonFiles/ItemIcons/awakening.gif")));
		}
	}
	public static class TonicWater extends Item{
		public TonicWater () throws IOException{
			super(75,"Tonic Water", 2, "Status", true, false, 0, 150,75,"Keeping it real classy. (Cures a poisoned Pkmn...and Malaria!).",(("/PokemonFiles/ItemIcons/antidote.gif")));
		}
	}
	public static class JagerBomb extends Item{
		public JagerBomb () throws IOException{
			super(76,"Jager Bomb", 3, "Status", true, false, 0, 250,125,"Makes you wanna dance and move all night long! (Cures paralysis).",(("/PokemonFiles/ItemIcons/paralyz-heal.gif")));
		}
	}
	public static class CremeDeMenthe extends Item{
		public CremeDeMenthe () throws IOException{
			super(77,"Creme De Menthe", 4, "Status", true, false, 0,300,150,"For that cool, soothing sensation. (Cures a burned Pokemon).",(("/PokemonFiles/ItemIcons/burn-heal.gif")));
		}
	}
	public static class Moonshine extends Item{
		public Moonshine () throws IOException{
			super(78,"Moonshine", 5, "Status", true, false, 0, 300,150,"Puts fire in your gut! (Defrosts a frozen Pokemon).",(("/PokemonFiles/ItemIcons/ice-heal.gif")));
		}
	}
	public static class SmirnoffIce extends Item{
		public SmirnoffIce() throws IOException{
			super(79,"Smirnoff Ice", 5, "HP", true, false, 0, 50, 25,"ICE TO MEET YOU! (Restores 5 HP to the user, may cause confusion?).",(("/PokemonFiles/ItemIcons/potion.gif")));
		}
	}
	
	public static class TM24_Thunderbolt extends Item{
		public TM24_Thunderbolt () throws IOException{
			super(80,"TM24: Thunderbolt", 85, "TM", false, false, 0, 0,1000,"Shoots a bolt of electricity at an opponent.",(("/PokemonFiles/ItemIcons/electric.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	
	public static class Apple extends Item{
		public Apple () throws IOException{
			super(81, "Apple", 30, "HP", false, false, 0, 0, 5, "An apple!", "/PokemonFiles/ItemIcons/apple.gif");
		}
	}
	
	public static class TM28_Dig extends Item{
		public TM28_Dig () throws IOException{
			super(82,"TM28: Dig", 91, "TM", false, false, 0, 0,1000,"An attack that hits on the 2nd turn. Can also be used to exit dungeons.",(("/PokemonFiles/ItemIcons/ground.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM45_ThunderWave extends Item{
		public TM45_ThunderWave () throws IOException{
			super(83,"TM45: Thunder Wave", 86, "TM", false, false, 0, 0,1000,"A weak jolt of electricity that paralyzes the foe.",(("/PokemonFiles/ItemIcons/electric.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	
	public static class PokeFlute extends Item{
		public PokeFlute() throws IOException{
			super(84, "PokeFlute", 0, "KeyItem", false, false, 0, 0, -1, "A cool-looking Flute you got from Media Services.", "/PokemonFiles/ItemIcons/pokeflute.png"); //TODO
		}
	}
	
	public static class TM19_Seismic_Toss extends Item{
		public TM19_Seismic_Toss () throws IOException{
			super(85,"TM19: Seismic Toss", 69, "TM", false, false, 0, 0,1500,"Inflicts damage identical to the user’s level.",(("/PokemonFiles/ItemIcons/fighting.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM44_Rest extends Item{
		public TM44_Rest () throws IOException{
			super(86,"TM44: Rest", 156, "TM", false, false, 0, 0,1000,"The user sleeps for 2 turns to restore health and status.",(("/PokemonFiles/ItemIcons/psychic.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM08_Body_Slam extends Item{
		public TM08_Body_Slam () throws IOException{
			super(87,"TM08: Body Slam", 34, "TM", false, false, 0, 0,2000,"A full-body slam that may cause paralysis.",(("/PokemonFiles/ItemIcons/normal.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM38_Fireblast extends Item{
		public TM38_Fireblast () throws IOException{
			super(88,"TM38: Fire Blast", 126, "TM", false, false, 0, 0,1000,"A powerful attack that may burn the foe.",(("/PokemonFiles/ItemIcons/fire.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	
	public static class Donut extends Item{
		public Donut() throws IOException{
			super(89, "Donut", 30, "HP", false, false, 0, 0, 5, "A delicious munchkin, served to you by a kind naked person.", "/PokemonFiles/ItemIcons/nugget.png");
		}
	}
	
	public static class RISD_ID extends Item{
		public RISD_ID () throws IOException{
			super(90,"RISD ID", 1, "KeyItem", false, false, 0, 0,-1,"A RISD student ID. Used for access to RISD buildings.",(("/PokemonFiles/ItemIcons/risd-id.png")));
		}
	}
	public static class TM42_DreamEater extends Item{
		public TM42_DreamEater () throws IOException{
			super(91,"TM42: Dream Eater", 148, "TM", false, false, 0, 0,1000,"Feeds on the dreams of sleeping Pokemon. Restores HP.",(("/PokemonFiles/ItemIcons/psychic.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM43_SkyAttack extends Item{
		public TM43_SkyAttack () throws IOException{
			super(92,"TM43: Sky Attack", 143, "TM", false, false, 0, 0,2500,"A 2nd-turn attack move with a high critical-hit ratio. The foe may flinch.",(("/PokemonFiles/ItemIcons/flying.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class KitchenKey extends Item{
		public KitchenKey () throws IOException{
			super(93,"Kitchen Key", 1, "KeyItem", false, false, 0, 0,-1,"A gold key. It's warm to the touch.",(("/PokemonFiles/ItemIcons/kitchenkey.png")));
		}
	}
	public static class Zipcar_Card extends Item{
		public Zipcar_Card () throws IOException{
			super(94,"Zipcar_Card", 1, "KeyItem", false, false, 0, 0,-1,"A membership for Zipcar.",(("/PokemonFiles/ItemIcons/zipcar.png")));
		}
	}
	public static class Ladder extends Item{
		public Ladder () throws IOException{
			super(95,"EZ Ladder", 1, "KeyItem", false, false, 0, 0,-1,"A collapsible ladder.",(("/PokemonFiles/ItemIcons/ladder.png")));
		}
	}
	public static class TM07_HornDrill extends Item{
		public TM07_HornDrill () throws IOException{
			super(96,"TM07: Horn Drill", 32, "TM", false, false, 0, 2000,1000,"A one-hit KO attack that uses a horn like a drill.",(("/PokemonFiles/ItemIcons/normal.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM10_DoubleEdge extends Item{
		public TM10_DoubleEdge () throws IOException{
			super(97,"TM10: Double-Edge", 38, "TM", false, false, 0, 0,2000,"A life-risking tackle that also hurts the user.",(("/PokemonFiles/ItemIcons/normal.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM09_Take_Down extends Item{
		public TM09_Take_Down () throws IOException{
			super(98,"TM09: Take Down", 36, "TM", false, false, 0, 3000,1500,"A reckless charge attack that also hurts the user.",(("/PokemonFiles/ItemIcons/normal.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class SuggsPotion extends Item{
		public SuggsPotion () throws IOException{
			super(99,"Suggs' Potion", 1, "KeyItem", false, false, 0, 0,-1,"A strange vial stolen from the Chemistry Dept.",(("/PokemonFiles/ItemIcons/suggsPotion.png")));
		}
	}
	public static class TM20_Rage extends Item{
		public TM20_Rage () throws IOException{
			super(100,"TM20: Rage", 99, "TM", false, false, 0, 0,1000,"Raises the user's Atk Stat every time it is hit. ",(("/PokemonFiles/ItemIcons/normal.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class StuffedDino extends Item{
		public StuffedDino () throws IOException{
			super(101,"Stuffed Dino Head", 1, "KeyItem", false, false, 0, 0,-1,"A stuffed dinosaur head from Capture the Mammoth.",(("/PokemonFiles/ItemIcons/dino.png")));
		}
	}
	public static class TM21_MegaDrain extends Item{
		public TM21_MegaDrain () throws IOException{
			super(102,"TM21: Mega Drain", 72, "TM", false, false, 0, 0,1000,"Drains massive amounts of energy from an opponent.",(("/PokemonFiles/ItemIcons/grass.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM37_EggBomb extends Item{
		public TM37_EggBomb () throws IOException{
			super(103,"TM37: Egg Bomb", 121, "TM", false, false, 0, 2000,1000,"An egg is forcibly hurled at the foe.",(("/PokemonFiles/ItemIcons/normal.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM32_DoubleTeam extends Item{
		public TM32_DoubleTeam () throws IOException{
			super(104,"TM32: DoubleTeam", 104, "TM", false, false, 0, 1000,500,"Creates illusory copies to raise evasiveness.",(("/PokemonFiles/ItemIcons/grass.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	public static class TM40_SkullBash extends Item{
		public TM40_SkullBash () throws IOException{
			super(105,"TM40: Skull Bash", 130, "TM", false, false, 0, 0,2000,"Tucks in the head, then attacks on the next turn",(("/PokemonFiles/ItemIcons/normal.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	
	public static class TM30_Teleport extends Item{
		public TM30_Teleport() throws IOException{
			super(106, "TM30: Teleportation", 100, "TM", false, false, 0, 0, 1000, "Teleports user away from danger.", (("/PokemonFiles/ItemIcons/psychic.gif")));
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
	
	public static class TM23_Dragon_Rage extends Item{
		public TM23_Dragon_Rage() throws IOException{
			super(107, "TM23: Dragon Rage", 82, "TM", false, false, 0, 0, 3000, "Attacks with the fury of 100 dragons...", (("/PokemonFiles/ItemIcons/dragon.gif")));
			//int compat[] = {};
			
		}
		 public boolean isCompatibleWith(Pokemon p){
			 return true;
		 }
	}
}
