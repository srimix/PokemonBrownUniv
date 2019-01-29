package PokemonDemo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

/**
 * The Player object responsible for controlling 
 * the currently displayed character image, and 
 * storing the users' Pokemon.
 * 
 * @author mreiss
 */
public class Player{
	
	private GameBoyScreen _gbs;
	public String _playerName;
	public String _rivalName;
	public Vector<Item> _allItems;
	public Vector<Integer> _destVectorX =new Vector<Integer>();
	public Vector<Integer> _destVectorY =new Vector<Integer>();
	public String _avoidMethod="freeze";
	public boolean _ignoreObstacles=false;
	private Vector<Integer> gymLeaderDefeated = new Vector<Integer>();
	public Vector<Pokemon> _activePokemon, _PCPokemon, _allPokemon, _tempAllPokemon, _tempActivePokemon, _tempPCPokemon;
	private int _lastPkmnCenterRoomNum = 2; //Keeney Room by default
	private String recentPSN = "";
	private Pokedex dex;
	private boolean hasTownMap = false;
	public final static int FAKE_ID=57, PACKAGE=37, RISD_ID=90;

	
	/*TODO
	 * WE ARE FUCKING IDIOTS. WE ARE RE-LOADING THE DAMNED PLAYER IMAGE EVERY MILLISECOND WTF.
	 */
	private BufferedImage upFace, up1, up2, downFace, down1, down2, leftFace, left1, left2, rightFace, right1, right2,
						  bike_upFace, bike_up1, bike_up2, bike_downFace, bike_down1, bike_down2, bike_leftFace, bike_left1, bike_left2, bike_rightFace, bike_right1, bike_right2,
						  surf_upFace, surf_up1, surf_up2, surf_downFace, surf_down1, surf_down2, surf_leftFace, surf_left1, surf_left2, surf_rightFace, surf_right1, surf_right2,
						   old_upFace,  old_up1,  old_up2,  old_downFace,  old_down1,  old_down2,  old_leftFace,  old_left1,  old_left2,  old_rightFace,  old_right1,  old_right2;
						   
	private static BufferedImage grass1, grass2, grass3, fall1, fall2, fall3, snow1, snow2, snow3;
	
	
	public int _money;
	
	public Player(GameBoyScreen gbs){
		_gbs = gbs;
		_allItems = new Vector<Item>();
		_PCPokemon = new Vector<Pokemon>();
		_activePokemon = new Vector<Pokemon>();
		_allPokemon = new Vector<Pokemon>();
		_tempAllPokemon = new Vector<Pokemon>();
		_tempPCPokemon = new Vector<Pokemon>();
		_tempActivePokemon = new Vector<Pokemon>();
		//Default start with no money, named Ash
		this._money=2000;
		this._playerName = "Ash";
		this._rivalName = "Gary";
		
		_lastPkmnCenterRoomNum = _gbs.KEENEY_ROOM; //Initially the player's Keeney room.
		
			//Will need to add one of every type.
		try{
			this.addItem(new Item.Potion());		//0
			this.getAllItems().get(0).setRemaining(1); // Let them start with a Potion
			this.addItem(new Item.OrganicPotion()); //1
			this.addItem(new Item.SuperPotion());	//2
			
			this.addItem(new Item.HyperPotion()); 	//3
			this.addItem(new Item.MaxPotion());	//4
			this.addItem(new Item.BubbleTea());	//5
			this.addItem(new Item.FreshWater());	//6
			this.addItem(new Item.SodaPop());		//7
			this.addItem(new Item.FullRestore());	//8
			
			this.addItem(new Item.Elixir());		//9
			this.addItem(new Item.Ether());		//10
			this.addItem(new Item.MaxElixir());	//11
			this.addItem(new Item.MaxEther());		//12
			
			this.addItem(new Item.Revive());		//13
			this.addItem(new Item.MaxRevive());	//14

			this.addItem(new Item.XAccuracy());	//15
			this.addItem(new Item.XAttack());		//16
			this.addItem(new Item.XDefend());		//17
			this.addItem(new Item.XSpecial());		//18
			this.addItem(new Item.XSpeed());		//19
			this.addItem(new Item.DireHit());		//20
			this.addItem(new Item.GuardSpec());	//21
			
			this.addItem(new Item.PokeBall());		//22
			this.addItem(new Item.FairTradePokeBall()); //23		
			this.addItem(new Item.GreatBall());	//24
			this.addItem(new Item.UltraBall());	//25
			this.addItem(new Item.MasterBall());	//26
		
			this.addItem(new Item.Awakening());	//27
			this.addItem(new Item.Antidote());		//28
			this.addItem(new Item.HolisticAntidote()); //29
			this.addItem(new Item.ParalyzHeal());	//30
			this.addItem(new Item.HolisticParalyzHeal()); //31
			this.addItem(new Item.BurnHeal());		//32
			this.addItem(new Item.IceHeal());		//33
			this.addItem(new Item.FullHeal());		//34
			
			this.addItem(new Item.Bicycle());		//35
			this.addItem(new Item.SpringWeekendTicket()); //36
			this.addItem(new Item.SpecialPackage());//37
			this.addItem(new Item.EXPAll());		//38
			
			this.addItem(new Item.Calcium());		//39
			this.addItem(new Item.Carbos());		//40
			this.addItem(new Item.Iron());			//41
			this.addItem(new Item.Protein());		//42
			this.addItem(new Item.Zinc());			//43
			this.addItem(new Item.HPUp());			//44
			this.addItem(new Item.PPUp());			//45
			this.addItem(new Item.RareCandy());	//46
			this.addItem(new Item.EscapeRope());	//47
			this.addItem(new Item.Repel());		//48
			this.addItem(new Item.SuperRepel());	//49
			this.addItem(new Item.MaxRepel());		//50
			this.addItem(new Item.Nugget());		//51
			
			this.addItem(new Item.MoonStone());	//52
			this.addItem(new Item.FireStone());	//53
			this.addItem(new Item.LeafStone());	//54
			this.addItem(new Item.WaterStone());	//55
			this.addItem(new Item.Thunderstone());	//56
			this.addItem(new Item.FakeID());//57  HAS A FINAL INT.
			this.addItem(new Item.TM06_Toxic());	//58
			this.addItem(new Item.TM14_Blizzard());//59
			this.addItem(new Item.TM22_Solar_Beam());//60
			this.addItem(new Item.TM46_Psywave());//61
			this.addItem(new Item.HM01_Cut());//62
			this.addItem(new Item.HM02_Fly());//63
			this.addItem(new Item.HM03_Surf());//64
			this.addItem(new Item.HM04_Strength());//65
			this.addItem(new Item.HM05_Flash());//66
			
			this.addItem(new Item.Dome_Fossil()); //67
			this.addItem(new Item.Helix_Fossil()); //68
			this.addItem(new Item.Old_Amber()); //69
			
			this.addItem(new Item.TM04_Whirlwind()); //70
			this.addItem(new Item.TM01_MegaPunch());//71
			this.addItem(new Item.TM12_WaterGun()); //72
			
			this.addItem(new Item.VodkaFifth()); //73
			this.addItem(new Item.Kahlua()); //74
			this.addItem(new Item.TonicWater()); //75
			this.addItem(new Item.JagerBomb()); //76
			this.addItem(new Item.CremeDeMenthe()); //77
			this.addItem(new Item.Moonshine()); //78
			this.addItem(new Item.SmirnoffIce()); //79
			
			this.addItem(new Item.TM24_Thunderbolt()); //80
			this.addItem(new Item.Apple()); //81
			this.addItem(new Item.TM28_Dig()); //82
			this.addItem(new Item.TM45_ThunderWave()); //83
			
			this.addItem(new Item.PokeFlute()); //84
			this.addItem(new Item.TM19_Seismic_Toss()); //85
			this.addItem(new Item.TM44_Rest());//86
			this.addItem(new Item.TM08_Body_Slam());//87
			this.addItem(new Item.TM38_Fireblast()); //88
			this.addItem(new Item.Donut());//89
			this.addItem(new Item.RISD_ID());//90, final int
			this.addItem(new Item.TM42_DreamEater()); //91
			this.addItem(new Item.TM43_SkyAttack()); //92
			this.addItem(new Item.KitchenKey()); //93
			this.addItem(new Item.Zipcar_Card()); //94
			this.addItem(new Item.Ladder()); //95
			this.addItem(new Item.TM07_HornDrill()); //96
			this.addItem(new Item.TM10_DoubleEdge()); //97
			this.addItem(new Item.TM09_Take_Down()); //98
			this.addItem(new Item.SuggsPotion()); //99
			this.addItem(new Item.TM20_Rage()); //100
			this.addItem(new Item.StuffedDino());//101
			this.addItem(new Item.TM21_MegaDrain()); //102
			this.addItem(new Item.TM37_EggBomb()); //103
			this.addItem(new Item.TM32_DoubleTeam()); //104
			this.addItem(new Item.TM40_SkullBash()); //105
			this.addItem(new Item.TM30_Teleport()); //106
			this.addItem(new Item.TM23_Dragon_Rage()); //107
			
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
			/*for(int i = 0; i < 15; i++){
				this.addItem(_allItems.get(i));
			}*/
		
			for(int i = 0; i < _allItems.size(); i++){
				//_allItems.get(i).setRemaining(3);//2*Math.random()));
	//			SysOut.print("ITEM Num " + i + ": " + _allItems.get(i).getName());
			}
		
		gymLeaderDefeated.add(0);//0
		gymLeaderDefeated.add(0);//1
		gymLeaderDefeated.add(0);//2
		gymLeaderDefeated.add(0);//3
		gymLeaderDefeated.add(0);//4
		gymLeaderDefeated.add(0);//5
		gymLeaderDefeated.add(0);//6
		gymLeaderDefeated.add(0);//7
			
		try {
			leftFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftFace.png"));
			left1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftStep1.png"));
			left2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftStep2.png"));
			
			rightFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rightFace.png"));
			right1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rightStep1.png"));
			right2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rightStep2.png"));
			
			downFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/downFace.png"));
			down1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/downStep1.png"));
			down2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/downStep2.png"));
			
			upFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/upFace.png"));
			up1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/upStep1.png"));
			up2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/upStep2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		try{
			bike_leftFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikeleftFace.png"));
			bike_left1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikeleftStep1.png"));
			bike_left2 = bike_left1;
			
			bike_rightFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikerightFace.png"));
			bike_right1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikerightStep1.png"));
			bike_right2 = bike_right1;
			
			bike_downFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikedownFace.png"));
			bike_down1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikedownStep1.png"));
			bike_down2 = bike_down1;
			
			bike_upFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikeupFace.png"));
			bike_up1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikeupStep1.png"));
			bike_up2 = bike_up1;
			
			
			surf_leftFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfL1.png"));
			surf_left1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfL2.png"));
			surf_left2 = surf_left1;
			
			surf_rightFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfR1.png"));
			surf_right1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfR2.png"));
			surf_right2 = surf_right1;
			
			surf_downFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfD1.png"));
			surf_down1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfD2.png"));
			surf_down2 = surf_down1;
			
			surf_upFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfU1.png"));
			surf_up1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfU2.png"));
			surf_up2 = surf_up1;
			
			
			old_leftFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftFaceOld.png"));
			old_left1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftStep1Old.png"));
			old_left2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftStep2Old.png"));
			
			old_rightFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rightFaceOld.png"));
			old_right1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rightStep1Old.png"));
			old_right2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rightStep2Old.png"));
			
			old_downFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/downFaceOld.png"));
			old_down1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/downStep1Old.png"));
			old_down2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/downStep2Old.png"));
			
			old_upFace = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/upFaceOld.png"));
			old_up1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/upStep1Old.png"));
			old_up2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/upStep2Old.png"));
			
			
			grass1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/grass1.png"));
			grass2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/grass2.png"));
			grass3 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/grass3.png"));
			
			fall1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/fall1.png"));
			fall2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/fall2.png"));
			fall3 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/fall3.png"));
			
			snow1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/snow1.png"));
			snow2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/snow2.png"));
			snow3 = ImageIO.read(this.getClass().getResource("/PokemonFiles/GrassImages/snow3.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
		
		this.faceDown();
	}
	
	public Pokedex getPokedex(){
		return dex;
	}
	
	public void givePokedex(){
		if(dex == null)
			dex = new Pokedex(this);
	}

	public boolean hasTownMap(){
		return hasTownMap;
	}
	
	public void giveTownMap(){
		hasTownMap = true;
	}
	
	/**
	 * @return "Pikachu is Player's default Pokemon"
	 */
	
	public void setPkmnCenter(int last){
		_lastPkmnCenterRoomNum = last;
	}
	
	public int getPkmnCenter(){
		return _lastPkmnCenterRoomNum;
	}
	
	public boolean hasPokemon(int dexNum){
		for(int i = 0; i < _activePokemon.size(); i++){
			if(_activePokemon.get(i).getDexNum() == dexNum)
				return true;
		}
		for(int i = 0; i < _PCPokemon.size(); i++){
			if(_PCPokemon.get(i).getDexNum() == dexNum)
				return true;
		}
		return false;
	}
	
	public boolean isGymLeaderDefeated(int leaderNum){
		if(gymLeaderDefeated.get(leaderNum-1)==1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void defeatGymLeader(int leaderNum){
		SysOut.print("DEFEAT: " + leaderNum);
		gymLeaderDefeated.set(leaderNum-1,1);
	}
	
	//Vector of all pokemon ever caught.
	public Vector<Pokemon> getAllPC(){
		return _PCPokemon;
	}
	public Vector<Pokemon> getAllActive(){
		return _activePokemon;
	}
	public Pokemon getPCPokemon(int i){
		return _PCPokemon.get(i);
	}
	public Pokemon getActivePokemon(int i){
		return _activePokemon.get(i);
	}
	
	public void swapPokemon(int first, int second){
		Pokemon p = _activePokemon.get(first);
		_activePokemon.set(first, _activePokemon.get(second));
		_activePokemon.set(second, p);
	}
	
	//This is a supervector used just during loading and saving.
	public void combinePokemonVectors(){
		_allPokemon.clear();
		for (int i=0; i<_activePokemon.size(); i++){
			_allPokemon.add(_activePokemon.get(i));
		}
		for (int i=0; i<_PCPokemon.size(); i++){
		_allPokemon.add(_PCPokemon.get(i));
		}
	}
	public Vector<Pokemon> getAllPokemon(){
		return this._allPokemon;
	}
	public Pokemon getPokemon(int i){
		return this._allPokemon.get(i);
	}
	
	//Vector of current 1-6.
	//Sorts the belt from 1-6. Called in load, PC, and menu.
	public void sortActivePokemon(){
		//Clear the vectors to avoid duplicates.
		_PCPokemon.clear();
		_activePokemon.clear();
		//Add the active.
		for (int j=0; j < 6; j++){
			for (int i=0; i < _allPokemon.size(); i++){
				if (_allPokemon.get(i).getBelt()==j+1){
					_activePokemon.add(_allPokemon.get(i));
				}
			}
		}
		
		//Add the PC.
		for (int i=0; i < _allPokemon.size(); i++){
			if (_allPokemon.get(i).getBelt()==7){
				_PCPokemon.add(_allPokemon.get(i));
			}
		}
		
		//Clear grand vector for speed.
		_allPokemon.clear();
	}
	
	
	public void depositPokemon(Pokemon p){
	//	_activePokemon.removeElement(p.getBelt());

		_activePokemon.remove(p.getBelt() - 1);
		for(int i = 0; i < _activePokemon.size(); i++){
			if(_activePokemon.get(i).getBelt() > p.getBelt()){
				_activePokemon.get(i).setBelt(_activePokemon.get(i).getBelt() - 1);
			}
		}
		p.setBelt(7);
		
		_PCPokemon.add(p);
	}
	
	public void withdrawPokemon(int choice, Pokemon p){
		
		_PCPokemon.remove(choice);
		
		p.setBelt(_activePokemon.size()+1); //TODO: kill self for not including +1 originally.
		_activePokemon.add(p);
	}
	
	public void healAllActive(){
		for(int i = 0; i < this.getAllActive().size(); i++){
			this.getActivePokemon(i).setNoAnimateCurrentHP(this.getActivePokemon(i).getMaxHP());
			this.getActivePokemon(i).setStatus(0);
			for(int j = 0; j < this.getActivePokemon(i).getAttacks().size(); j++){
				this.getActivePokemon(i).getAttacks().get(j).setCurrentPP(this.getActivePokemon(i).getAttacks().get(j).getMaxPP());
			}
		}
	}
	
	public boolean isAnyPSN(){
		for(int i = 0; i < _activePokemon.size(); i++){
			if(_activePokemon.get(i).getStatus() == 2)
				return true;
		}
		return false;
	}
	
	public int damageAllPSN(){
		for(int i = 0; i < _activePokemon.size(); i++){
			if(_activePokemon.get(i).getStatus() == 2){
				_activePokemon.get(i).setCurrentHP(_activePokemon.get(i).getCurrentHP() - 1);
				if(_activePokemon.get(i).getCurrentHP() <= 0){
					_activePokemon.get(i).setStatus(0); //No longer Poisoned
					_activePokemon.get(i).setCurrentHP(1); //Stops with 1 HP left	
					SysOut.print("PSN = 0");
				
				//THIS ENSURES POKEMON WILL ONLY FAINT IN-BATTLE.
					return i;
				}
			}
		}
		return -1;
	}
	
//	public boolean isPSNDead(){
//		for(int i = 0; i < _activePokemon.size(); i++){
//			if(_activePokemon.get(i).getStatus() == 2 && _activePokemon.get(i).getCurrentHP() == 0){
//				this.setRecentPSN(_activePokemon.get(i).getName());
//				_activePokemon.get(i).setStatus(0);
//				return true;
//			}
//		}
//		return false;
//	}
	
	public void setRecentPSN(String s){
		this.recentPSN = s;
	}
	
	public String whoDiedFromPSN(){
		return recentPSN;
	}
	
	public String getPlayerName(){
		return _playerName;
	}
	public void setPlayerName(String myname){
		_playerName= myname;
	}
	public String getRivalName(){
		return _rivalName;
	}
	public void setRivalName(String theirname){
		_rivalName= theirname;
		_gbs.getRival().setName(theirname);
	}
	
	public int getMoney(){
		return _money;
	}
	public void setMoney(int value){
		_money=value;
		if(_money > 999999){
			_money = 999999;
		}
	}
	public void incMoney(int increase){
		this.setMoney(_money+increase);
	}
	
	
	public Item getItem(int i){
		return _allItems.get(i);
	}
	
	public Vector<Item> getAllItems(){
		return _allItems;
	}
	
	public boolean hasItem(int i){
		if(_allItems.get(i).getRemaining()>0){
			return true;
		}
		return false;
	}
	
	public void addItem(Item i){
		this._allItems.add(i.getIndex(),i);
	}
	public String getAvoidMethod() {
		return _avoidMethod;
	}
	public void setAvoidMethod(String s) {
		_avoidMethod =s;
	}

	public void setIgnoring(boolean b) {
		_ignoreObstacles=b;
	}
	public boolean isIgnoring() {
		return _ignoreObstacles;
	}

	public void clearDestinations() {
		_destVectorX.clear();
		_destVectorY.clear();
	}
	public void addDest(int tx, int ty) {
		_destVectorX.add(tx);
		_destVectorY.add(ty);
	}
	public boolean hasADestination(){
		return (_destVectorX.size() > 0);
	}
	
	public BufferedImage getLeftFaceImage(){
		return leftFace;//ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftFace.png"));
	}
	public BufferedImage getLeftStep1Image(){
		return left1;
	}
	public BufferedImage getLeftStep2Image(){
		return left2;
	}
	public BufferedImage getRightFaceImage(){
		return rightFace;
	}
	public BufferedImage getRightStep1Image(){
		return right1;
	}
	public BufferedImage getRightStep2Image(){
		return right2;
	}
	public BufferedImage getUpFaceImage(){
		return upFace;
	}
	public BufferedImage getUpStep1Image(){
		return up1;
	}
	public BufferedImage getUpStep2Image(){
		return up2;
	}
	public BufferedImage getDownFaceImage(){
		return downFace;
	}
	public BufferedImage getDownStep1Image(){
		return down1;
	}
	public BufferedImage getDownStep2Image(){
		return down2;
	}
	
	public static BufferedImage getGrass1(){
		return grass1;
	}
	public static BufferedImage getGrass2(){
		return grass2;
	}
	public static BufferedImage getGrass3(){
		return grass3;
	}
	
	public static BufferedImage getFall1(){
		return fall1;
	}
	public static BufferedImage getFall2(){
		return fall2;
	}
	public static BufferedImage getFall3(){
		return fall3;
	}
	public static BufferedImage getSnow1(){
		return snow1;
	}
	public static BufferedImage getSnow2(){
		return snow2;
	}
	public static BufferedImage getSnow3(){
		return snow3;
	}
	
	public void bikeLeft(){
		if(_gbs.getCurrentPanel().getTimeCount()<=10 && _gbs.getCurrentPanel().getBikeCycle()==1){_gbs.setPlayerImage(bike_left1);}
		if(_gbs.getCurrentPanel().getTimeCount()>10 && _gbs.getCurrentPanel().getBikeCycle()==1){_gbs.setPlayerImage(bike_leftFace);}
		if(_gbs.getCurrentPanel().getTimeCount()<=10 && _gbs.getCurrentPanel().getBikeCycle()==2){_gbs.setPlayerImage(bike_left2);}
		if(_gbs.getCurrentPanel().getTimeCount()>10 && _gbs.getCurrentPanel().getBikeCycle()==2){_gbs.setPlayerImage(bike_leftFace);}
	}
	public void bikeRight(){
		if(_gbs.getCurrentPanel().getTimeCount()<=10 && _gbs.getCurrentPanel().getBikeCycle()==1){_gbs.setPlayerImage(bike_right1);}
		if(_gbs.getCurrentPanel().getTimeCount()>10 && _gbs.getCurrentPanel().getBikeCycle()==1){_gbs.setPlayerImage(bike_rightFace);}
		if(_gbs.getCurrentPanel().getTimeCount()<=10 && _gbs.getCurrentPanel().getBikeCycle()==2){_gbs.setPlayerImage(bike_right2);}
		if(_gbs.getCurrentPanel().getTimeCount()>10 && _gbs.getCurrentPanel().getBikeCycle()==2){_gbs.setPlayerImage(bike_rightFace);}
		
	}
	public void bikeUp(){
		if(_gbs.getCurrentPanel().getTimeCount()<=10 && _gbs.getCurrentPanel().getBikeCycle()==1){_gbs.setPlayerImage(bike_up1);}
		if(_gbs.getCurrentPanel().getTimeCount()>10 && _gbs.getCurrentPanel().getBikeCycle()==1){_gbs.setPlayerImage(bike_upFace);}
		if(_gbs.getCurrentPanel().getTimeCount()<=10 && _gbs.getCurrentPanel().getBikeCycle()==2){_gbs.setPlayerImage(bike_up2);}
		if(_gbs.getCurrentPanel().getTimeCount()>10 && _gbs.getCurrentPanel().getBikeCycle()==2){_gbs.setPlayerImage(bike_upFace);}
		
	}
	public void bikeDown(){
		if(_gbs.getCurrentPanel().getTimeCount()<=10 && _gbs.getCurrentPanel().getBikeCycle()==1){_gbs.setPlayerImage(bike_down1);}
		if(_gbs.getCurrentPanel().getTimeCount()>10 && _gbs.getCurrentPanel().getBikeCycle()==1){_gbs.setPlayerImage(bike_downFace);}
		if(_gbs.getCurrentPanel().getTimeCount()<=10 && _gbs.getCurrentPanel().getBikeCycle()==2){_gbs.setPlayerImage(bike_down2);}
		if(_gbs.getCurrentPanel().getTimeCount()>10 && _gbs.getCurrentPanel().getBikeCycle()==2){_gbs.setPlayerImage(bike_downFace);}
		
	}

public void faceLeft(){
		
		if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(3);	
		
		if (_gbs.getMode()){this.bikeLeft();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_leftFace);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_leftFace);}
		else{_gbs.setPlayerImage(leftFace);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 1);
	}
	
	/**
	 */
	public void stepLeft1(){
		//if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(3);
		
		if (_gbs.getMode()){this.bikeLeft();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_left1);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_left1);}
		else if(_gbs.isSpinning()){_gbs.setPlayerImage(leftFace);}
		else{_gbs.setPlayerImage(left1);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 2);
	}
	
	/**
	 */
	public void stepLeft2(){
		if (_gbs.getMode()){this.bikeLeft();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_left2);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_left2);}
		else if(_gbs.isSpinning()){_gbs.setPlayerImage(leftFace);}
		else{_gbs.setPlayerImage(left2);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 3);
	}
	
	
	//Player walking East.
	
	/**
	 */
	public void faceRight(){
		if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(1);
		
		if (_gbs.getMode()){this.bikeRight();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_rightFace);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_rightFace);}
		else{_gbs.setPlayerImage(rightFace);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 1);
	}
	
	/**
	 */
	public void stepRight1(){
		if (_gbs.getMode()){this.bikeRight();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_right1);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_right1);}
		else if(_gbs.isSpinning()){_gbs.setPlayerImage(rightFace);}
		else{_gbs.setPlayerImage(right1);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 2);
	}
	
	/**
	 */
	public void stepRight2(){
		if (_gbs.getMode()){this.bikeRight();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_right2);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_right2);}
		else if(_gbs.isSpinning()){_gbs.setPlayerImage(rightFace);}
		else{_gbs.setPlayerImage(right2);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 3);
	}
	
	
	//Player walking North.
	
	/**
	 */
	public void faceUp(){
		if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(0);
		
		if (_gbs.getMode()){this.bikeUp();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_upFace);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_upFace);}
		else{_gbs.setPlayerImage(upFace);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 1);
	}
	
	/**
	 */
	public void stepUp1(){
		//	if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(0);
			if (_gbs.getMode()){this.bikeUp();}
			else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_up1);}
			else if(_gbs.isOld()){_gbs.setPlayerImage(old_up1);}
			else if(_gbs.isSpinning()){_gbs.setPlayerImage(upFace);}
			else{_gbs.setPlayerImage(up1);}
			
			_gbs.setGrassImage(_gbs.getCurrentPanel(), 2);
	}
	
	/**
	 */
	public void stepUp2(){
		//	if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(0);
			if (_gbs.getMode()){this.bikeUp();}
			else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_up2);}
			else if(_gbs.isOld()){_gbs.setPlayerImage(old_up2);}
			else if(_gbs.isSpinning()){_gbs.setPlayerImage(upFace);}
			else{_gbs.setPlayerImage(up2);}
			
			_gbs.setGrassImage(_gbs.getCurrentPanel(), 3);
	}
	
	
	//Player walking South (default).
	
	/**
	 */
	public void faceDown(){
		if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(2);
		
		if (_gbs.getMode()){this.bikeDown();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_downFace);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_downFace);}
		else{_gbs.setPlayerImage(downFace);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 1);
	}
	
	/**
	 */
	public void stepDown1(){
		//if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(2);
		if (_gbs.getMode()){this.bikeDown();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_down1);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_down1);}
		else if(_gbs.isSpinning()){_gbs.setPlayerImage(downFace);}
		else{_gbs.setPlayerImage(down1);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 2);
	}
	
	/**
	 */
	public void stepDown2(){
		//if(_gbs.getCurrentPanel() != null) _gbs.getCurrentPanel().setPlayerDirection(2);
		if (_gbs.getMode()){this.bikeDown();}
		else if (_gbs.getSurf()){_gbs.setPlayerImage(surf_down2);}
		else if(_gbs.isOld()){_gbs.setPlayerImage(old_down2);}
		else if(_gbs.isSpinning()){_gbs.setPlayerImage(downFace);}
		else{_gbs.setPlayerImage(down2);}
		
		_gbs.setGrassImage(_gbs.getCurrentPanel(), 3);
	}
	
	public void jumpDown(){
		try{
			if (_gbs.getMode()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikedownStep1.png")));}
			else if (_gbs.getSurf()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfD1.png")));}
			else{_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/downStep1.png")));}
		}
		catch(IOException ioe){
			this.faceDown();
		}
	}
	
	public void jumpLeft(){
		try{
			if (_gbs.getMode()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikeleftStep1.png")));}
			else if (_gbs.getSurf()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfL1.png")));}
			else{_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/leftStep1.png")));}
		}
		catch(IOException ioe){
			this.faceLeft();
		}
	}
	
	public void jumpRight(){
		try{
			if (_gbs.getMode()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/bikerightStep1.png")));}
			else if (_gbs.getSurf()){_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/surfR1.png")));}
			else{_gbs.setPlayerImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/PlayerImages/rightStep1.png")));}
		}
		catch(IOException ioe){
			this.faceRight();
		}
	}

}