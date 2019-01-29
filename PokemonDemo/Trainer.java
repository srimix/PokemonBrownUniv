package PokemonDemo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import PokemonDemo.TimerFactory.TimerGroup;

/**
 * ITEMS.
 * 
 * @author Playa Sri
 */
public class Trainer {

	private String trainerType, name = "";
	protected Vector<String> _dialogue = new Vector<String>();
	protected Vector<String> _postItemDialogue = new Vector<String>();
	protected Vector<String> _postBattleDialogue = new Vector<String>();
	private BufferedImage _leftImage, _rightImage, _frontImage, _backImage,
			_moveLeftImage1, _moveLeftImage2, _moveRightImage1,
			_moveRightImage2, _moveFrontImage1, _moveFrontImage2,
			_moveBackImage1, _moveBackImage2, _battleImage, _currentImage;
	private BufferedImage _NPCgrassImage;
	private Vector<Pokemon> _belt;
	private Item _gift;
	private int _xIndex, _yIndex, _xSpace, _ySpace, _direction,
			_defaultDirection, _money, adjustX=0, adjustY=0;
	protected int _homeIndX;
	protected int _homeIndY;
	private Vector<Integer> _destVectorX = new Vector<Integer>();
	private Vector<Integer> _destVectorY = new Vector<Integer>();
	private int nextDir;
	private int _currentDestIndex, _currentDestX, _currentDestY;
	private boolean _defeated, _vanishesUponDefeat = false, _interrupted, _justMoved,
			_canMove = true, pauses = true, _defeatAfterItem=false;
	@SuppressWarnings("unused")
	private int _timeCount = 0;

	// Freezing method by default.
	private String _avoidMethod = "freeze", _approachMethod = "freeze",
			_returnMethod = "freeze", _defeatDialogue;
	private int _walkSpeed = GameBoyScreen._defaultNPCSpeed;
	private int _LOS = 0;
	public int _NPCTimeCount;
	private TimerFactory.TimerGroup _tg;
	public Timer _NPCWalkTimer;
	public int _xCorrect=0, _yCorrect=0;

	public int currentAction = 7;
	private boolean _moving = false, _stationary = true;
	public static final int MOVE_LEFT = 0, MOVE_RIGHT = 1, MOVE_UP = 2,
			MOVE_DOWN = 3, FACE_LEFT = 4, FACE_RIGHT = 5, FACE_UP = 6,
			FACE_DOWN = 7;
	
	private char gender;
	protected boolean _canSurf=false, _onGrass=false;
	protected Pokemon legendary;
	protected boolean _defeatAtFinalDest=false;
	protected boolean _defeatAfterReturnTrip=false;

	/**
	 * Constructor for Item. Sets each subclasses relevant variables to match
	 * the input parameters.
	 * 
	 * @param name
	 * @param remaining
	 * @param effect
	 */
	
	public void setBattleImage(int imageNumber){
		this._battleImage = TImg.battleSprites[imageNumber];
	}
	
	public Trainer(String type, int xindex, int yindex, int xSpace, int ySpace,
			int direction, int defaultDirection, boolean defeated,
			boolean vanishesUponDefeat, int money, Item gift,
			Vector<Pokemon> belt, String frontimage,
			String backimage, String leftimage,
			String rightimage, String movefrontimage1,
			String movefrontimage2, String movebackimage1,
			String movebackimage2, String moveleftimage1,
			String moveleftimage2, String moverightimage1,
			String moverightimage2, String battleimage, char gender) {
		trainerType = type;

		this.gender = gender;
		_dialogue = new Vector<String>();
		_xIndex = xindex;
		_yIndex = yindex;
		_xSpace = xSpace;
		_ySpace = ySpace;
		_direction = direction;
		_defaultDirection = defaultDirection;
		_money = money;
		_gift = gift;
		_belt = belt;
		try{
//		if(this != null)
//			SysOut.print("I exist");
//		if(this.getClass() != null){
//			SysOut.print("class exists");
//		}
//			if(this.getClass().getResource(frontimage) != null){
//			SysOut.print("resource");
//		}
		if(battleimage != null)
			setBattleImage(ImageIO.read(this.getClass().getResource(battleimage)));
		else{
			if(gender == 'F'){
				setBattleImage(TImg.F_ACE_TRAINER);
			}
			else{
				setBattleImage(TImg.M_ACE_TRAINER);
			}
		}
		this.setFirstDest();
		
		_frontImage = ImageIO.read(this.getClass().getResource(frontimage));
		_backImage = ImageIO.read(this.getClass().getResource(backimage));
		_leftImage = ImageIO.read(this.getClass().getResource(leftimage));
		_rightImage = ImageIO.read(this.getClass().getResource(rightimage));
		_moveFrontImage1 = ImageIO.read(this.getClass().getResource(movefrontimage1));
		_moveBackImage1 = ImageIO.read(this.getClass().getResource(movebackimage1));
		_moveLeftImage1 = ImageIO.read(this.getClass().getResource(moveleftimage1));
		_moveRightImage1 = ImageIO.read(this.getClass().getResource(moverightimage1));
		_moveFrontImage1 = ImageIO.read(this.getClass().getResource(movefrontimage1));
		_moveBackImage1 = ImageIO.read(this.getClass().getResource(movebackimage1));
		_moveLeftImage1 = ImageIO.read(this.getClass().getResource(moveleftimage1));
		_moveRightImage1 = ImageIO.read(this.getClass().getResource(moverightimage1));

		_moveFrontImage2 = ImageIO.read(this.getClass().getResource(movefrontimage2));
		_moveBackImage2 = ImageIO.read(this.getClass().getResource(movebackimage2));
		_moveLeftImage2 = ImageIO.read(this.getClass().getResource(moveleftimage2));
		_moveRightImage2 = ImageIO.read(this.getClass().getResource(moverightimage2));
		_moveFrontImage2 = ImageIO.read(this.getClass().getResource(movefrontimage2));
		_moveBackImage2 = ImageIO.read(this.getClass().getResource(movebackimage2));
		_moveLeftImage2 = ImageIO.read(this.getClass().getResource(moveleftimage2));
		_moveRightImage2 = ImageIO.read(this.getClass().getResource(moverightimage2));




		}
		catch(IOException ioe){
			//ioe.printStackTrace();
		}
		catch(NullPointerException npe){
			//npe.printStackTrace();
		}
		_defeated = defeated;
		_vanishesUponDefeat = vanishesUponDefeat;

		switch (_defaultDirection) {
		case 0:
			this.setCurrentImage(_frontImage);
			break;
		case 1:
			this.setCurrentImage(_leftImage);
			break;
		case 2:
			this.setCurrentImage(_backImage);
			break;
		case 3:
			this.setCurrentImage(_rightImage);
			break;
		default:
			this.setCurrentImage(_frontImage);
		}

	}
	
	/*
	 * FOR WALKING TRAINERS ONLY
	 */
	public Trainer(String type, int xindex, int yindex, int xSpace, int ySpace,
			int direction, int defaultDirection, boolean defeated,
			boolean vanishesUponDefeat, int money, Item gift,
			Vector<Pokemon> belt, String directory, char gender) {
		
		trainerType = type;

		this.gender = gender;
		_dialogue = new Vector<String>();
		_xIndex = xindex;
		_yIndex = yindex;
		_xSpace = xSpace;
		_ySpace = ySpace;
		_direction = direction;
		_defaultDirection = defaultDirection;
		_money = money;
		_gift = gift;
		_belt = belt;
		

		try {
			
			_frontImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/DownFace.png"));
			_backImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/UpFace.png"));
			_leftImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/LeftFace.png"));
			_rightImage = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/RightFace.png"));
		} catch (IOException e1) {
			//e1.printStackTrace();
		}
		
		try{
			_moveFrontImage1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Down1.png"));
			_moveBackImage1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Up1.png"));
			_moveLeftImage1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Left1.png"));
			_moveRightImage1 = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Right1.png"));

			_moveFrontImage2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Down2.png"));
			_moveBackImage2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Up2.png"));
			_moveLeftImage2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Left2.png"));
			_moveRightImage2 = ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Right2.png"));

		}
		catch(Exception e){
			_moveFrontImage1 = _frontImage;
			_moveBackImage1 = _frontImage;
			_moveLeftImage1 = _frontImage;
			_moveRightImage1 = _frontImage;

			_moveFrontImage2 = _frontImage;
			_moveBackImage2 = _frontImage;
			_moveLeftImage2 = _frontImage;
			_moveRightImage2 = _frontImage;
			
			//e.printStackTrace();
		}

		try{
			//setBattleImage(ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Battle.png")));
			this._battleImage=ImageIO.read(this.getClass().getResource("/PokemonFiles/TrainerImages/"+directory+"/Battle.png"));
		}
		catch(Exception e){
			//e.printStackTrace();
			if(gender == 'F'){
				setBattleImage(TImg.F_ACE_TRAINER);
			}
			else{
				setBattleImage(TImg.M_ACE_TRAINER);
			}
		}
		
		this.setFirstDest();
		_defeated = defeated;
		_vanishesUponDefeat = vanishesUponDefeat;

		switch (_defaultDirection) {
		case 0:
			this.setCurrentImage(_frontImage);
			break;
		case 1:
			this.setCurrentImage(_leftImage);
			break;
		case 2:
			this.setCurrentImage(_backImage);
			break;
		case 3:
			this.setCurrentImage(_rightImage);
			break;
		default:
			this.setCurrentImage(_frontImage);
		}
	}
	
	public boolean doesPause() {
		return pauses;
	}

	public void setPause(boolean b) {
		pauses = b;
	}
	
	
	public char getGender(){
		return gender;
	}
	
	public void setGender(char gend){
		this.gender=gend;
	}

	public boolean isStationary() {
		return _stationary;
	}

	public void setStationary(boolean b) {
		_stationary = b;
		this.setNextDir(this.getDirection()+4);
	}

	public void setMoving(boolean b) {
		_moving = b;
	}

	public boolean isMoving() {
		return _moving;
	}

	public void advance(int tickNum) {
		switch (currentAction) {
		case MOVE_LEFT:
			if (!_stationary && !this.isInterrupted()) {
				if (tickNum == 1) {
					this.setDirection(1);
					this.setXIndex(this.getXIndex() - 1);
					this.setCurrentImage(this.getLeftImage());
					_moving = true;
				}
				if (tickNum == 5) {
					this.setCurrentImage(this.getMoveLeftImage1());
				}
				if (tickNum == 10) {
					this.setCurrentImage(this.getLeftImage());
				}
				if (tickNum == 15) {
					this.setCurrentImage(this.getMoveLeftImage2());
				}
				if (tickNum == 20) {
					this.setCurrentImage(this.getLeftImage());
				}
				if(this._moving)
					_xSpace--;
			}
			break;
		case MOVE_RIGHT:
			if (!_stationary && !this.isInterrupted()) {
				if (tickNum == 1) {
					this.setDirection(3);
					this.setXIndex(this.getXIndex() + 1);
					this.setCurrentImage(this.getRightImage());
					_moving = true;
				}
				if (tickNum == 5) {
					this.setCurrentImage(this.getMoveRightImage1());
				}
				if (tickNum == 10) {
					this.setCurrentImage(this.getRightImage());
				}
				if (tickNum == 15) {
					this.setCurrentImage(this.getMoveRightImage2());
				}
				if (tickNum == 20) {
					this.setCurrentImage(this.getRightImage());
				}
				if(this._moving)
					_xSpace++;
			}
			break;
		case MOVE_UP:
			if (!_stationary && !this.isInterrupted()) {
				if (tickNum == 1) {
					this.setDirection(2);
					this.setYIndex(this.getYIndex() - 1);
					this.setCurrentImage(this.getBackImage());
					_moving = true;
				}
				if (tickNum == 5) {
					this.setCurrentImage(this.getMoveBackImage1());
				}
				if (tickNum == 10) {
					this.setCurrentImage(this.getBackImage());
				}
				if (tickNum == 15) {
					this.setCurrentImage(this.getMoveBackImage2());
				}
				if (tickNum == 20) {
					this.setCurrentImage(this.getBackImage());
				}
				if(this._moving)
					_ySpace--;
			}
			break;
		case MOVE_DOWN:
			if (!_stationary && !this.isInterrupted()) {
				if (tickNum == 1) {
					this.setDirection(0);
					this.setYIndex(this.getYIndex() + 1);
					this.setCurrentImage(this.getFrontImage());
					_moving = true;
				}
				if (tickNum == 5) {
					this.setCurrentImage(this.getMoveFrontImage1());
				}
				if (tickNum == 10) {
					this.setCurrentImage(this.getFrontImage());
				}
				if (tickNum == 15) {
					this.setCurrentImage(this.getMoveFrontImage2());
				}
				if (tickNum == 20) {
					this.setCurrentImage(this.getFrontImage());
				}
				if(this._moving)
					_ySpace++;
			}
			break;
		case FACE_LEFT:
			_moving = false;
			this.setDirection(1);
			break;
		case FACE_RIGHT:
			_moving = false;
			this.setDirection(3);
			break;
		case FACE_UP:
			_moving = false;
			this.setDirection(2);
			break;
		case FACE_DOWN:
			_moving = false;
			this.setDirection(0);
			break;
		}
	}

	private void setDirection(int i) {
		_direction=i;
	}

	public void addTimerGroup(TimerGroup tg) {
		_tg = tg;
	}

	public BufferedImage getCurrentImage() {
		return _currentImage;
	}

	public void setCurrentImage(BufferedImage newimage) {
		_currentImage = newimage;
	}

	public String getName() {
		if (name != "")
			return (trainerType + " " + name);
		else
			return trainerType;
	}

	public String getTrueName() {
		return name;
	}

	public void setType(String newtype) {
		trainerType = newtype;
	}
	
	public boolean isGymLeader(){
		if(trainerType.equalsIgnoreCase("Gym Leader")){
			return true;
		}
		return false;
	}
	
	public Trainer setName(String newname) {
		name = newname;
		return this;
	}

	public Vector<String> getDialogue() {
		return _dialogue;
	}
	
	public String getLineOfDialogue(int i){
		return _dialogue.get(i);
	}
	
	public Vector<String> getPostItemDialogue() {
		return _postItemDialogue;
	}
	
	public Vector<String> getPostBattleDialogue() {
		// TODO Auto-generated method stub
		return _postBattleDialogue;
	}
	
	public String getLineOfPostBattleDialogue(int i){
		return _postBattleDialogue.get(i);
	}
	
	public String getDefeatDialogue() {
		return _defeatDialogue;
	}

	public void setDefeatDialogue(String s) {
		_defeatDialogue = s;
	}

	public void setAvoidMethod(String s) {
		_avoidMethod = s;
	}

	public String getAvoidMethod() {
		return _avoidMethod;
	}

	public void setApproachMethod(String s) {
		_approachMethod = s;
	}

	public String getReturnMethod() {
		return _returnMethod;
	}

	public void setReturnMethod(String s) {
		_returnMethod = s;
	}

	public String getApproachMethod() {
		return _approachMethod;
	}

	public void defeat() {
		_defeated = true;
	}
	
	public void defeatAndPostBattle(){
		_defeated = true;
		if(this.getPostBattleDialogue().size()!=0){
			this._dialogue.clear();
			for(int i=0; i<this.getPostBattleDialogue().size();i++){
				this._dialogue.add(this.getPostBattleDialogue().get(i));
				this.setBelt(null);
			}
		}
		else if(this.getDefeatDialogue()!=null){
			this._dialogue.clear();
			this._dialogue.add(this.getDefeatDialogue());
		}
		else{
			//You keep the old dialogue.
		}
	}

	public void unDefeat() {
		_defeated = false;
	}

	public boolean isDefeated() {
		return _defeated;
	}

	public boolean getVanishing() {
		return _vanishesUponDefeat;
	}

	public void setVanishing(boolean vanishMode) {
		_vanishesUponDefeat = vanishMode;
	}
	
	public boolean defeatAfterItem(){
		return _defeatAfterItem;
	}
	
	public void setDefeatAfterItem(boolean b){
		_defeatAfterItem=b;
	}
	
	public void defeatAndPostItemize(){
		this.setGift(null);
		if (this.getPostItemDialogue()!=null){
			this.getDialogue().clear();
			for(int i =0; i<this.getPostItemDialogue().size();i++){
				this.getDialogue().add(this.getPostItemDialogue().get(i));
			}
		}
		if(this.defeatAfterItem()){
			this.defeat();
		}
	}

	public boolean isInterrupted() {
		return _interrupted;
	}

	public void setInterrupted(boolean b) {
		_interrupted = b;
	}

	public void setCurrentDestIndex(int i) {
		_currentDestIndex = i;
	}

	public int getCurrentDestIndex() {
		return _currentDestIndex;
	}

	public void setCurrentDestX(int i) {
		_currentDestX = i;
	}

	public int getCurrentDestX() {
		return _currentDestX;
	}

	public void setCurrentDestY(int i) {
		_currentDestY = i;
	}

	public int getCurrentDestY() {
		return _currentDestY;
	}

	public boolean hasReachedDest() {
		if (_destVectorX.size() > 0 && _destVectorY.size() > 0) {
			// if(_xIndex==_destVectorX.get(_currentDestIndex) &&
			// _yIndex==_destVectorY.get(_currentDestIndex)){
			if (_xIndex == _currentDestX && _yIndex == _currentDestY) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void prepareNextDest() {
		_currentDestIndex++;
		// Reset when at the end of a list.
		if (_currentDestIndex >= _destVectorX.size()) {
			_currentDestIndex = 0;
			
			if(this.getDefeatAtFinalDest()){
				this.setVanishing(true);
				this.defeat();
			}
			
		}
		this.setCurrentDestX(_destVectorX.get(_currentDestIndex));
		this.setCurrentDestY(_destVectorY.get(_currentDestIndex));
	}

	public void setFirstDest() {
		//this.setStationary(false); //FIXME - I added this line recently. (Mat)
		if (_destVectorX.size() > 0 && _destVectorY.size() > 0) {
			_currentDestX = _destVectorX.get(0);
			_currentDestY = _destVectorY.get(0);
		}
	}

	public boolean hasJustMoved() {
		return _justMoved;
	}

	public void setJustMoved(boolean state) {
		_justMoved = state;
	}
	
	public boolean canSurf(){
		return _canSurf;
	}

	public BufferedImage getFrontImage() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass1());
		}
		return _frontImage;
		
	}

	public BufferedImage getBackImage() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass1());
		}
		return _backImage;
	}

	public BufferedImage getLeftImage() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass1());
		}
		return _leftImage;
	}

	public BufferedImage getRightImage() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass1());
		}
		return _rightImage;
	}

	public BufferedImage getMoveFrontImage1() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass2());
		}
		return _moveFrontImage1;
	}

	public BufferedImage getMoveBackImage1() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass2());
		}
		return _moveBackImage1;
	}

	public BufferedImage getMoveLeftImage1() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass2());
		}
		return _moveLeftImage1;
	}

	public BufferedImage getMoveRightImage1() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass2());
		}
		return _moveRightImage1;
	}

	public BufferedImage getMoveFrontImage2() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass3());
		}
		return _moveFrontImage2;
	}

	public BufferedImage getMoveBackImage2() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass3());
		}
		return _moveBackImage2;
	}

	public BufferedImage getMoveLeftImage2() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass3());
		}
		return _moveLeftImage2;
	}

	public BufferedImage getMoveRightImage2() {
		if(_onGrass){
			this.setGrassImage(Player.getGrass3());
		}
		return _moveRightImage2;
	}

	public BufferedImage getBattleImage() {
		return _battleImage;
	}

	public int getXIndex() {
		return _xIndex;
	}

	public int getYIndex() {
		return _yIndex;
	}

	public void setXIndex(int newindex) {
		_xIndex = newindex;
	}

	public void setYIndex(int newindex) {
		_yIndex = newindex;
	}

	public void setIndices(int newxindex, int newyindex) {
		_xIndex = newxindex;
		_yIndex = newyindex;
	}

	public void createHome(int ix, int iy, int sx, int sy, String as, String ls) {
		this.setIndices(ix, iy);
		_homeIndX = ix;
		_homeIndY = iy;
		adjustX=sx;
		adjustY=sy;
		this.setSpaces(ix * 20 + adjustX, iy * 20 + adjustY);

		this.setApproachMethod(as);
		this.setReturnMethod(ls);
		this.setAvoidMethod(this.getApproachMethod());
	}
	
	public void createHome(int ix, int iy) {
		this.setIndices(ix, iy);
		_homeIndX = ix;
		_homeIndY = iy;
		adjustX=this._xCorrect;
		adjustY=this._yCorrect;
		this.setSpaces(ix * 20 + adjustX, iy * 20 + adjustY);

		this.setApproachMethod("freeze");
		this.setReturnMethod("freeze");
		this.setAvoidMethod(this.getApproachMethod());
	}
	
	public void setHomeIndices(int hx, int hy){
		_homeIndX=hx;
		_homeIndY=hy;
	}

	public void resetHome() {
		int ix = _homeIndX;
		int iy = _homeIndY;
		this.setIndices(ix, iy);
		this.setSpaces(ix * 20+adjustX, iy * 20+adjustY);
	}

	public void sendHome() {
		this.clearDestinations();
		this.addDest(_homeIndX, _homeIndY);
		this.setFirstDest();
		this.setStationary(false);
		
		if(this.getDefeatAfterReturnTrip()){
			this.setDefeatAtFinalDest(true);
		}
	}

	public int getWalkSpeed() {
		return _walkSpeed;
	}

	public void setWalkSpeed(int speed) {
		_walkSpeed = speed;
	}

	public int getXSpace() {
		return _xSpace;
	}

	public int getYSpace() {
		return _ySpace;
	}

	public void setXSpace(int newspace) {
		_xSpace = newspace;
	}

	public void setYSpace(int newspace) {
		_ySpace = newspace;
	}

	public void setSpaces(int newx, int newy) {
		_xSpace = newx;
		_ySpace = newy;
	}

	public int getDirection() {
		return _direction;
	}

	public void setDirectionAndImage(int direction) {
		switch (direction) {
		case 0:
			_currentImage = this.getFrontImage();
			break;
		case 1:
			_currentImage = this.getLeftImage();
			break;
		case 2:
			_currentImage = this.getBackImage();
			break;
		case 3:
			_currentImage = this.getRightImage();
			break;
		}
		_direction = direction;
	}

	public BufferedImage getGrassImage(){
		return _NPCgrassImage;
	}
	
	public void setGrassImage(BufferedImage image){
		_NPCgrassImage = image;
	}
	
	public void facePlayer(int xIndex,int yIndex) {

		if((Math.abs(this.getXIndex()-xIndex) > Math.abs(this.getYIndex()-yIndex)) && this.getXIndex()>xIndex);{
			this.setDirectionAndImage(1);
		}
		if((Math.abs(this.getXIndex()-xIndex) > Math.abs(this.getYIndex()-yIndex)) && this.getXIndex()<xIndex);{
			this.setDirectionAndImage(3);
		}
		if((Math.abs(this.getXIndex()-xIndex) < Math.abs(this.getYIndex()-yIndex)) && this.getYIndex()>yIndex);{
			this.setDirectionAndImage(2);
		}
		if((Math.abs(this.getXIndex()-xIndex) < Math.abs(this.getYIndex()-yIndex)) && this.getYIndex()<yIndex);{
			this.setDirectionAndImage(0);
		}		
	}
	
	public void stepDown1() {
		_currentImage = this.getMoveFrontImage1();
	}

	public void stepDown2() {
		_currentImage = this.getMoveFrontImage2();
	}

	public void faceDown() {
		_currentImage = this.getFrontImage();
		_direction = 0;
	}

	public void stepUp1() {
		_currentImage = this.getMoveBackImage1();
	}

	public void stepUp2() {
		_currentImage = this.getMoveBackImage2();
	}

	public void faceUp() {
		_currentImage = this.getBackImage();
		_direction = 2;
	}

	public void stepLeft1() {
		_currentImage = this.getMoveLeftImage1();
	}

	public void stepLeft2() {
		_currentImage = this.getMoveLeftImage2();
	}

	public void faceLeft() {
		_currentImage = this.getLeftImage();
		_direction = 1;
	}

	public void stepRight1() {
		_currentImage = this.getMoveRightImage1();
	}

	public void stepRight2() {
		_currentImage = this.getMoveRightImage2();
	}

	public void faceRight() {
		_currentImage = this.getRightImage();
		_direction = 3;
	}

	public void setDefaultDirection(int defaultdir) {
		_defaultDirection = defaultdir;
	}

	public void resetDirection() {
		// Used to return trainers to default facing direction when you exit a
		// room.
		SysOut.print("Direction Reset: " + _defaultDirection);
		switch (_defaultDirection) {
		case 0:
			_currentImage = this.getFrontImage();
			break;
		case 1:
			_currentImage = this.getLeftImage();
			break;
		case 2:
			_currentImage = this.getBackImage();
			break;
		case 3:
			_currentImage = this.getRightImage();
			break;
		}
	}

	
	public void setTimer(Timer t) {
		_NPCWalkTimer = t;
	}

	public void NPCMove(int direction, int speed) {

		if (_NPCWalkTimer == null || !_NPCWalkTimer.isRunning()) {

			// Adjust speed for bike vs. walk. Lowered _Speed=faster.
			if (direction == 0) {
				this.clearNPCTimer();
				_NPCWalkTimer.addActionListener(_tg.getUpTimer());
			}

			if (direction == 1) {
				this.clearNPCTimer();
				_NPCWalkTimer.addActionListener(_tg.getDownTimer());
			}

			if (direction == 2) {
				this.clearNPCTimer();
				_NPCWalkTimer.addActionListener(_tg.getRightTimer());
			}

			if (direction == 3) {
				this.clearNPCTimer();
				_NPCWalkTimer.addActionListener(_tg.getLeftTimer());
			}

			_NPCWalkTimer.start();
		}

	}

	public void clearNPCTimer() {
		_NPCWalkTimer.removeActionListener(_tg.getUpTimer());
		_NPCWalkTimer.removeActionListener(_tg.getDownTimer());
		_NPCWalkTimer.removeActionListener(_tg.getLeftTimer());
		_NPCWalkTimer.removeActionListener(_tg.getRightTimer());
	}


	public int getMoney() {
		return _money;
	}

	public void setMoney(int money) {
		_money = money;
	}

	public Vector<Pokemon> getBelt() {
		return _belt;
	}

	public void setBelt(Vector<Pokemon> belt) {
		_belt = belt;
	}

	public Item getGift() {
		return _gift;
	}

	public void setGift(Item gift) {
		_gift = gift;
	}

	public void indexLeft() {
		_xIndex = _xIndex - 1;
	}

	public void indexRight() {
		_xIndex = _xIndex + 1;
	}

	public void indexUp() {
		_yIndex = _yIndex - 1;
	}

	public void indexDown() {
		_yIndex = _yIndex + 1;
	}

	public void spaceLeft() {
		_xSpace = _xSpace - 1;
	}

	public void spaceRight() {
		_xSpace = _xSpace + 1;
	}

	public void spaceUp() {
		_ySpace = _ySpace - 1;
	}

	public void spaceDown() {
		_ySpace = _ySpace + 1;
	}

	public void addDest(int dx, int dy) {
		_destVectorX.add(dx);
		_destVectorY.add(dy);
	}

	public void clearDestinations() {
		_destVectorX.clear();
		_destVectorY.clear();
	}

	public boolean hasADestination() {
		if (this._destVectorX.size() == 0) {
			return false;
		}
		return true;
	}
	
	public void setDefeatAtFinalDest(boolean b){
		this._defeatAtFinalDest=b;
	}
	public boolean getDefeatAtFinalDest(){
		return this._defeatAtFinalDest;
	}
	public void setDefeatAfterReturnTrip(boolean b){
		this._defeatAfterReturnTrip=b;
	}
	public boolean getDefeatAfterReturnTrip(){
		return this._defeatAfterReturnTrip;
	}

	/*
	 * private class UpTimer implements ActionListener{ public void
	 * actionPerformed(ActionEvent e){ _timeCount++; _ySpace++;
	 * 
	 * if(_timeCount == 5){ this.stepUp1(); } if(_timeCount == 10){
	 * this.faceUp(); if(_timeCount == 15){ this.stepUp2(); } if(_timeCount ==
	 * 20){ this.faceUp(); _walkTimer.stop(); _timeCount = 0; _yIndex--; } } }
	 */

	// ==========================================================

	public void setLOS(int LOS) {
		this._LOS = LOS;
	}

	public int getLOS() {
		return _LOS;
	}

	public boolean getCanMove() {
		return _canMove;
	}

	public void setCanMove(boolean b) {
		_canMove = b;
	}

	public void setNextDir(int nextDir) {
		this.nextDir = nextDir;
	}

	public int getNextDir() {
		return nextDir;
	}

	public void setBattleImage(BufferedImage _battleImage) {
		this._battleImage = _battleImage;
	}

	/**
	 * List of Specific Trainers
	 * */
	
	public static class DongJoo extends Trainer {
		public DongJoo(Vector<Pokemon> belt) throws IOException {
			super(
					"Elite Four",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"E4-David", 'M'); // battle
																			// image

			this.setName("DongJoo");
		}
	}
	
	public static class Mat extends Trainer{
		public Mat(Vector<Pokemon> belt) throws IOException{
			super("Elite Four", 0, 0, 0, 0, 0, 0, false, false, 100, null, belt, "E4-Mat", 'M');
			this.setName("Mat");
		}
	}
	
	public static class Sri extends Trainer{
		public Sri(Vector<Pokemon> belt) throws IOException{
			super("Elite Four", 0, 0, 0, 0, 0, 0, false, false, 100, null, belt, "E4-Sri", 'M');
			this.setName("Sri");
		}
	}

	public static class Mike extends Trainer{
		public Mike(Vector<Pokemon> belt) throws IOException{
			super("Elite Four", 0, 0, 0, 0, 0, 0, false, false, 100, null, belt, "E4-Mike", 'M');
			this.setName("Mike");
		}
	}
	
	public static class Gail extends Trainer{
		public Gail(Vector<Pokemon> belt) throws IOException{
			super("Gym Leader", 0, 0, 0, 0, 0, 0, false, false, 1386, new Item.TM06_Toxic(), belt, "Gail", 'F');
			this.setName("Gail");
		}
	}
	
	public static class NiceGail extends Trainer{
		public NiceGail(Vector<Pokemon> belt) throws IOException{
			super("Gail", 0,0,0,0,0,0,false,false,0,null,belt,"Gail-Nice", 'F');
		}
	}
	
	public static class Paradiso extends Trainer{
		public Paradiso(Vector<Pokemon> belt) throws IOException{
			super("Gym Leader", 0, 0, 0, 0, 0, 0, false, false, 2079, new Item.TM46_Psywave(), belt, "Paradiso", 'M');
			this.setName("Paradiso");
		}
	}
	
	public static class Ruth extends Trainer{
		public Ruth(Vector<Pokemon> belt) throws IOException{
			super("Ruth", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "Ruth", 'F');
			this._xCorrect=-1;
		}
	}
	
	public static class RuthGymLeader extends Trainer{
		public RuthGymLeader(Vector<Pokemon> belt) throws IOException{
			super("Gym Leader", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "Ruth", 'F');
			this.setName("Ruth");
		}
	}
	
	public static class AndyVanDam extends Trainer{
		private GameBoyScreen gbs;
		public AndyVanDam(Vector<Pokemon> belt, GameBoyScreen gbs) throws IOException{
			super("Gym Leader", 0, 0, 0, 0, 0, 0, false, false, 2376, new Item.TM24_Thunderbolt(), belt, "Andy", 'M');
			this.setName("Andy");
			this.gbs = gbs;
		}
		public Vector<String> getDialogue(){
			
			if(this.isDefeated()){
				gbs.setOld(false);
			}
			
			return super.getDialogue();
		}
	}
	
	public static class Hazeltine extends Trainer{
		public Hazeltine(Vector<Pokemon> belt) throws IOException{
			super("Gym Leader", 0, 0, 0, 0, 0, 0, false, false, 2871, new Item.TM01_MegaPunch(), belt, "Hazeltine", 'M');
			this.setName("Hazeltine");
		}
	}
	
	public static class Josiah extends Trainer{
		public Josiah(Vector<Pokemon> belt) throws  IOException{
			super("Gym Leader", 0, 0, 0, 0, 0, 0, false, false, 4653, new Item.TM38_Fireblast(), belt, "Josiah", 'M');
			this.setName("Josiah S. Carberry");
		}
	}
	
	public static class Suggs extends Trainer{
		public Suggs(Vector<Pokemon> belt) throws IOException{
			super("Gym Leader", 0,0,0,0,0,0,false,false,4257, new Item.TM21_MegaDrain(), belt, "Suggs", 'M');
			this.setName("Suggs");
		}
	}
	
	public static class Hess extends Trainer{
		public Hess(Vector<Pokemon> belt) throws IOException{
			super("",0,0,0,0,0,0,false,false,0,null, belt,"F-Hess",'F');
		}
	}
	
	public static class ParadisoTeleport extends Trainer{
		@SuppressWarnings("unused")
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		public ParadisoTeleport(){
			super("Gym Leader", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "Paradiso", '#');
			this._dialogue=new Vector<String>();			
		}
	
	}
	
	
	public static class MafiaWhite extends Trainer{
		public MafiaWhite(Vector<Pokemon> belt) throws IOException{
			super("MafiaWhite", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "M-MafiaWhite", 'M');
			this.setName("Mafia");
			this._xCorrect=2;
		}
	}
	public static class MafiaMagenta extends Trainer{
		public MafiaMagenta(Vector<Pokemon> belt) throws IOException{
			super("MafiaMagenta", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "M-MafiaMagenta", 'M');
			this.setType("Mafia");
			this._xCorrect=2;
		}
	}
	public static class MafiaGreen extends Trainer{
		public MafiaGreen(Vector<Pokemon> belt) throws IOException{
			super("MafiaGreen", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "M-MafiaGreen", 'M');
			this.setType("Mafia");
			this._xCorrect=2;
		}
	}
	
	public static class Jose extends Trainer{
		public Jose(Vector<Pokemon> belt){
			super("Cardswiper", 0, 0, 0, 0, 0, 0, false, false, 220, null, belt, "Jose", 'M');
			this.setName("Jose");
		}
	}
	
	public static class ChampGuy extends Trainer{
		public ChampGuy(){
			super("ChampGuy", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "ChampGuy", 'M');
		}
	}
	
	public static class Backpacker extends Trainer{
		public Backpacker(Vector<Pokemon> belt) throws IOException{
			super("Backpacker", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "M-Backpacker", 'M');
		}
	}
	
	public static class ShadyGlasses extends Trainer{
		public ShadyGlasses(Vector<Pokemon> belt) throws IOException{
			super("ShadyGlasses", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "M-ShadyGlasses", 'M');
		}
	}
	
	public static class PurpleHatGirl extends Trainer{
		public PurpleHatGirl(Vector<Pokemon> belt) throws IOException{
			super("PurpleHatGirl", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "F-PurpleHat", 'F');
		}
	}
	
	public static class BrownMediumHair extends Trainer{
		public BrownMediumHair(Vector<Pokemon> belt) throws IOException{
			super("BrownMediumHair", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "F-BrownMediumHair", 'F');
		}
	}
	
	public static class BlackHairBlueDress extends Trainer{
		public BlackHairBlueDress(Vector<Pokemon> belt) throws IOException{
			super("BlackHairBlueDress", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "F-BlackHairBlueDress", 'F');
		}
	}

	
	public static class Gyarados extends Trainer{
		public Gyarados(Vector<Pokemon> belt) throws IOException{
			super("Gyarados", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, "P-Gyarados", 'P');
		}
	}
	
	public static class VomitGuy extends Trainer {
        public VomitGuy(Vector<Pokemon> belt)
                throws IOException {
            super("VomitGuy", 0, 0, 0, 0, 0, 0, false, false, 0, null,
                    null, (("/PokemonFiles/TrainerImages/VomitGuy/DownFace.png")),
                    null,
                    (("/PokemonFiles/TrainerImages/VomitGuy/LeftFace.png")),
                    (("/PokemonFiles/TrainerImages/VomitGuy/RightFace.png")),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null, 'P');
        }
	}
	
	/**
	 * List of Non-Specific Trainers
	 * */
	
	public static class Female1 extends Trainer {
		public Female1(Vector<Pokemon> belt) throws IOException {
			super(
					"Beauty",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					(("/PokemonFiles/TrainerImages/F-Female1/trainerD.png")),
					(("/PokemonFiles/TrainerImages/F-Female1/trainerU.png")),
					(("/PokemonFiles/TrainerImages/F-Female1/trainerL.png")),
					(("/PokemonFiles/TrainerImages/F-Female1/trainerR.png")),
					null, null, null, null, null, null, null, null,
					(("/PokemonFiles/TrainerImages/F-Female1/battle.png")), 'F');
		}
	}

	public static class Meiklejohn extends Trainer {
		public Meiklejohn(Vector<Pokemon> belt) throws IOException {
			super(
					"Meiklejohn",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					(("/PokemonFiles/TrainerImages/Meik/meikFaceDown.png")),
					(("/PokemonFiles/TrainerImages/Meik/meikFaceUp.png")),
					(("/PokemonFiles/TrainerImages/Meik/meikFaceLeft.png")),
					(("/PokemonFiles/TrainerImages/Meik/meikFaceRight.png")),
					(("/PokemonFiles/TrainerImages/Meik/meikStepLeftDown.png")),
					(("/PokemonFiles/TrainerImages/Meik/meikStepRightDown.png")),
					(("/PokemonFiles/TrainerImages/Meik/meikUp1.png")), // move
																				// up
																				// 1
					(("/PokemonFiles/TrainerImages/Meik/meikUp2.png")), // move
																				// up
																				// 2
					(("/PokemonFiles/TrainerImages/Meik/meikLeft1.png")), // move
																				// left
																				// 1
					(("/PokemonFiles/TrainerImages/Meik/meikLeft2.png")), // move
																				// left
																				// 2
					(("/PokemonFiles/TrainerImages/Meik/meikRight1.png")), // move
																				// right
																				// 1
					(("/PokemonFiles/TrainerImages/Meik/meikRight2.png")), // move
																				// right
																				// 2
					null, 'M'); // battle image
		}

		public Item getGift() {

			return null;
		}
	}

	public static class Rival extends Trainer {
		public Rival(Vector<Pokemon> belt) throws IOException {
			super(
					"Rival",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					(("/PokemonFiles/TrainerImages/Rival/rivalFaceFront.png")),
					(("/PokemonFiles/TrainerImages/Rival/rivalFaceBack.png")), // move
																							// right
																							// 2
					(("/PokemonFiles/TrainerImages/Rival/rivalFaceLeft.png")),
					(("/PokemonFiles/TrainerImages/Rival/rivalFaceRight.png")),
					(("/PokemonFiles/TrainerImages/Rival/rivalFront1.png")),
					(("/PokemonFiles/TrainerImages/Rival/rivalFront2.png")),
					(("/PokemonFiles/TrainerImages/Rival/rivalBack1.png")), // move
																					// right
																					// 2
					(("/PokemonFiles/TrainerImages/Rival/rivalBack2.png")), // move
																					// right
																					// 2
					(("/PokemonFiles/TrainerImages/Rival/rivalLeft1.png")), // move
																					// left
																					// 1
					(("/PokemonFiles/TrainerImages/Rival/rivalLeft2.png")), // move
																					// left
																					// 2
					(("/PokemonFiles/TrainerImages/Rival/rivalRight1.png")), // move
																							// right
																							// 1
					(("/PokemonFiles/TrainerImages/Rival/rivalRight2.png")), // move
																							// right
																							// 2
					(("/PokemonFiles/PlayerImages/rivalBIG.png")), 'R');// battle
																		// image
		}
	}

	public static class BlueGirl extends Trainer {
		public BlueGirl(Vector<Pokemon> belt) throws IOException {
			super(
					"BlueGirl",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					(("/PokemonFiles/TrainerImages/F-BlueHair/DownFace.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/UpFace.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/RightFace.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/Down1.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/Down2.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/Up1.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/Up2.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/Left1.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/Left2.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/Right1.png")),
					(("/PokemonFiles/TrainerImages/F-BlueHair/Right2.png")),
					null, 'F');
		}
	}

	public static class GlassesGuy1 extends Trainer {
		public GlassesGuy1(Vector<Pokemon> belt) throws IOException {
			super(
					"GlassesGuy1",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-GlassesGuy/Right2.png")),
					null, 'M');
		}
	}
	
	public static class BlueBro extends Trainer {
		public BlueBro(Vector<Pokemon> belt) throws IOException {
			super(
					"BlueBro",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-BlueBro", 'M');
		}
	}
	
	public static class DirtyBlondeGirl extends Trainer{
		public DirtyBlondeGirl(Vector<Pokemon> belt) throws IOException{
			super(
				"DirtyBlonde",
				0,
				0,
				0,
				0,
				0,
				0,
				false,
				false,
				100,
				null,
				belt,
				"F-DirtyBlonde", 'F');
		}
	}
	
	public static class RedBandanaGirl extends Trainer{
		public RedBandanaGirl(Vector<Pokemon> belt) throws IOException{
			super("RedBandana",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"F-RedBandana", 'F');
			this._xCorrect=-2;
		}
	}
	
	public static class BlondeDude extends Trainer{
		public BlondeDude(Vector<Pokemon> belt) throws IOException {
			super("BlondeDude",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-BlondeDude", 'M');
		}
	}
	
	public static class BrownGuy extends Trainer{
		public BrownGuy(Vector<Pokemon> belt) throws IOException {
			super("BrownGuy",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-BrownGuy", 'M');
		}
	}
	
	public static class YellowHatBoy extends Trainer{
		public YellowHatBoy(Vector<Pokemon> belt) throws IOException {
			super("YellowHatBoy",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-YellowHat", 'M');
		}
	}
	
	
	
	public static class BlackHair extends Trainer {
		public BlackHair(Vector<Pokemon> belt) throws IOException {
			super(
					"BlackHair",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-BlackHair", 'M');
		}
	}
	
	public static class GlassesProfessor extends Trainer {
		public GlassesProfessor(Vector<Pokemon> belt) throws IOException {
			super(
					"GlassesProfessor",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-GlassesProfessor", 'M');
		}
	}
	
	public static class Ponytail extends Trainer {
		public Ponytail(Vector<Pokemon> belt) throws IOException {
			super(
					"Ponytail",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"F-Ponytail", 'F');
		}
	}
	
	public static class NakedGuy extends Trainer{
		public NakedGuy(Vector<Pokemon> belt) throws IOException{
			super("Naked Dude",0,0,0,0,0,0,false,false,100,null,belt,"M-NakedGuy",'M');
		}
	}
	
	public static class NakedGuy2 extends Trainer{
		public NakedGuy2(Vector<Pokemon> belt) throws IOException{
			super("Naked Dude",0,0,0,0,0,0,false,false,100,null,belt,"M-NakedGuy2",'M');
		}
	}
	
	public static class NakedGirl extends Trainer{
		public NakedGirl(Vector<Pokemon> belt) throws IOException{
			super("Naked Girl",0,0,0,0,0,0,false,false,100,null,belt,"F-NakedGirl",'F');
		}
	}
	
	public static class RedHairBike extends Trainer {
		public RedHairBike(Vector<Pokemon> belt) throws IOException {
			super(
					"RedHairBike",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"F-RedHairBike", 'F');
		}
	}
	
	public static class WhiteBeltBike extends Trainer {
		public WhiteBeltBike(Vector<Pokemon> belt) throws IOException {
			super(
					"WhiteBeltBike",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-WhiteBeltBike", 'M');
		}
	}
	public static class Ileana extends Trainer {
		public Ileana(Vector<Pokemon> belt) throws IOException {
			super(
					"Ileana",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"Ileana", 'F');
		}
	}
	
	
	public static class Pigtails extends Trainer {
		public Pigtails(Vector<Pokemon> belt) throws IOException {
			super(
					"Pigtails",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"F-Pigtails", 'F');
		}
	}
	
	public static class RedOveralls extends Trainer {
		public RedOveralls(Vector<Pokemon> belt) throws IOException {
			super(
					"RedOveralls",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-RedOveralls", 'M');
		}
	}
	
	public static class Chef extends Trainer {
		public Chef(Vector<Pokemon> belt) throws IOException {
			super(
					"Chef",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"Chef", 'M');
		}
	}
	
	public static class NicholasBrown extends Trainer {
		public NicholasBrown(Vector<Pokemon> belt) throws IOException {
			super(
					"NicholasBrown",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"NicholasBrown", 'M');
		}
	}
	
	public static class Ambika extends Trainer {
		public Ambika(Vector<Pokemon> belt) throws IOException {
			super(
					"Ambika",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"F-Ambika", 'F');
		}
	}
	public static class Elder extends Trainer {
		public Elder(Vector<Pokemon> belt) throws IOException {
			super(
					"Elder",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-Elder", 'M');
		}
	}
	public static class Photographer extends Trainer {
		public Photographer(Vector<Pokemon> belt) throws IOException {
			super(
					"Photographer",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-Photographer", 'M');
		}
	}
	public static class Swimmer extends Trainer {
		public Swimmer(Vector<Pokemon> belt) throws IOException {
			super(
					"Swimmer",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-Swimmer", 'M');
		}
	}
	public static class Lorelei extends Trainer {
		public Lorelei(Vector<Pokemon> belt) throws IOException {
			super(
					"Lorelei",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"F-Lorelei", 'F');
		}
	}
	
	public static class BookReadBoy extends Trainer {
		public BookReadBoy(Vector<Pokemon> belt) throws IOException {
			super(
					"BookReadBoy",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-BookReader", 'M');
		}
	}
	
	public static class StrawHat extends Trainer {
		public StrawHat(Vector<Pokemon> belt) throws IOException {
			super(
					"StrawHat",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-StrawHat", 'M');
		}
	}
	
	public static class Mario extends Trainer {
		public Mario(Vector<Pokemon> belt) throws IOException {
			super(
					"Mario",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"Mario", 'M');
		}
	}
	
	public static class NiceHair extends Trainer {
		public NiceHair(Vector<Pokemon> belt) throws IOException {
			super(
					"NiceHair",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-NiceHair", 'M');
		}
	}
	
	public static class MafiaDon extends Trainer {
		public MafiaDon(Vector<Pokemon> belt) throws IOException {
			super(
					"Mafia Don",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-MafiaDon", 'M');
		}
	}
	
	public static class BlackGirl extends Trainer {
		public BlackGirl(Vector<Pokemon> belt) throws IOException {
			super(
					"BlackGirl",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					(("/PokemonFiles/TrainerImages/F-BlackGirl/DownFace.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/UpFace.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/RightFace.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/Down1.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/Down2.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/Up1.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/Up2.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/Left1.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/Left2.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/Right1.png")),
					(("/PokemonFiles/TrainerImages/F-BlackGirl/Right2.png")),
					null, 'F');
		}
	}
	
	public static class ChubbyGuy extends Trainer {
		public ChubbyGuy(Vector<Pokemon> belt) throws IOException {
			super(
					"ChubbyGuy",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-ChubbyGuy/Right2.png")),
					null, 'M');
		}
	}


	
	public static class Moonwalker extends Trainer {
		public Moonwalker(Vector<Pokemon> belt)
				throws IOException {
			super("Moonwalker", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/M-Moonwalker/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-Moonwalker/Right2.png")),
					null, 'M');
		}
	}
	
	public static class Scientist extends Trainer {
		public Scientist(Vector<Pokemon> belt)
				throws IOException {
			super("Scientist", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/M-Scientist/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-Scientist/Right2.png")),
					null, 'M');
		}
	}
	
	public static class GlassesGirl extends Trainer {
		public GlassesGirl(Vector<Pokemon> belt)
				throws IOException {
			super("GlassesGirl", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/F-GlassesGirl/DownFace.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/UpFace.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/RightFace.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Down1.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Down2.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Up1.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Up2.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Left1.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Left2.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Right1.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Right2.png")),
					(("/PokemonFiles/TrainerImages/F-GlassesGirl/Battle.png")), 'F');
		}
	}
	
	public static class GraySkirt extends Trainer {
		public GraySkirt(Vector<Pokemon> belt)
				throws IOException {
			super("GraySkirt", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/F-GraySkirt/DownFace.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/UpFace.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/RightFace.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/Down1.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/Down2.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/Up1.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/Up2.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/Left1.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/Left2.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/Right1.png")),
					(("/PokemonFiles/TrainerImages/F-GraySkirt/Right2.png")),
					null, 'F');
		}
	}
	
	public static class GreenDress extends Trainer {
		public GreenDress(Vector<Pokemon> belt)
				throws IOException {
			super("GreenDress", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/F-GreenDress/DownFace.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/UpFace.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/RightFace.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/Down1.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/Down2.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/Up1.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/Up2.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/Left1.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/Left2.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/Right1.png")),
					(("/PokemonFiles/TrainerImages/F-GreenDress/Right2.png")),
					null, 'F');
		}
	}
	
	public static class RedAndYellow extends Trainer {
		public RedAndYellow(Vector<Pokemon> belt)
				throws IOException {
			super("RedAndYellow", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/F-RedAndYellow/DownFace.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/UpFace.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/RightFace.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/Down1.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/Down2.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/Up1.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/Up2.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/Left1.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/Left2.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/Right1.png")),
					(("/PokemonFiles/TrainerImages/F-RedAndYellow/Right2.png")),
					null, 'F');
		}
	}
	
	public static class BlackDude extends Trainer {
		public BlackDude(Vector<Pokemon> belt)
				throws IOException {
			super("BlackDude", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/M-BlackDude/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-BlackDude/Right2.png")),
					null, 'M');
		}
	}
	
	public static class GreenBike extends Trainer {
		public GreenBike(Vector<Pokemon> belt)
				throws IOException {
			super("GreenBike", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/M-GreenBike/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-GreenBike/Right2.png")),
					null, 'M');
		}
	}
	public static class Pirate extends Trainer {
		public Pirate(Vector<Pokemon> belt)
				throws IOException {
			super("Pirate", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/M-Pirate/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-Pirate/Right2.png")),
					null, 'M');
		}
	}
	
	
	public static class GreenHat extends Trainer {
		public GreenHat(Vector<Pokemon> belt)
				throws IOException {
			super("GreenHat", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/M-GreenHat/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Right2.png")),
					(("/PokemonFiles/TrainerImages/M-GreenHat/Battle.png")), 'M');
		}
	}
	public static class MaroonHat extends Trainer {
		public MaroonHat(Vector<Pokemon> belt)
				throws IOException {
			super("MaroonHat", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/M-MaroonHat/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Right2.png")),
					(("/PokemonFiles/TrainerImages/M-MaroonHat/Battle.png")), 'M');
			this.setVanishing(false);
		}
	}
	public static class RedstripeShirt extends Trainer {
		public RedstripeShirt(Vector<Pokemon> belt)
				throws IOException {
			super("RedstripeShirt", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					belt, (("/PokemonFiles/TrainerImages/M-RedstripeShirt/DownFace.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/UpFace.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/RightFace.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/Down1.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/Down2.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/Up1.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/Up2.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/Left1.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/Left2.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/Right1.png")),
					(("/PokemonFiles/TrainerImages/M-RedstripeShirt/Right2.png")),
					null, 'M');
		}
	}
		
	
	/**
	 * List of Pokemon "Trainers"
	 * */
	
	public static class Seadra extends Trainer {
		private PokePanel2 current;
		private int count=0;
		public Seadra(PokePanel2 panel)
				throws IOException {
			super("Seadra", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					null, (("/PokemonFiles/TrainerImages/P-Seadra/DownFace.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/UpFace.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/RightFace.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/Down1.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/DownFace.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/Up1.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/UpFace.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/Left1.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/Right1.png")),
					(("/PokemonFiles/TrainerImages/P-Seadra/RightFace.png")),
					null, 'P');
			current=panel;
		}
		public Vector<String> getDialogue(){
			if(M2.SEADRA!=null && current._NPCpage == 0 && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.SEADRA);
					count++;
				}
			}
			
			if(current._NPCpage == 1){
				count=0;
			}
			return this._dialogue;
		}
	}
	
	public static class Psyduck extends Trainer {
		private PokePanel2 current;
		private int count=0;
		public Psyduck(PokePanel2 panel)
				throws IOException {
			super("Psyduck", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					null, (("/PokemonFiles/TrainerImages/P-Psyduck/DownFace.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/UpFace.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/RightFace.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/Down1.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/DownFace.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/Up1.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/UpFace.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/Left1.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/LeftFace.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/Right1.png")),
					(("/PokemonFiles/TrainerImages/P-Psyduck/RightFace.png")),
					null, 'P');
			current=panel;
		}
		
		public Vector<String> getDialogue(){
			if(current!=null && M2.PSYDUCK!=null && current._NPCpage == 0 && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.PSYDUCK);
					count++;
				}
			}
			
			if(current!=null && current._NPCpage == 1){
				count=0;
			}
			return this._dialogue;
		}
	}
	
	public static class SnorlaxAsleep extends Trainer {
		private int count = 0;
		private PokePanel2 current;
		public SnorlaxAsleep(PokePanel2 panel) throws IOException {
			super("Snorlax", 0, 0, 0, 0, 0, 0, false, false, 0, null,
					null, "P-SnorlaxAsleep", 'P');
			current = panel;
			this._xCorrect=-8;
			this._yCorrect=-7;
		}
		public Vector<String> getDialogue(){
			if(current!=null && M2.SNORLAX!=null && current._NPCpage == 0 && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.SNORLAX);
					count++;
				}
			}
			
			if(current!=null && current._NPCpage == 1){
				count=0;
			}
			return this._dialogue;
		}
	}
	
	public static class HillelLapras extends Trainer {
		private int count=0;
		private Pokemon _pokemon;
		private GameBoyScreen _gbs;
		@SuppressWarnings("unused")
		private boolean _accepted;
		public HillelLapras(Pokemon pokemon, GameBoyScreen gbs) throws IOException {
			super(
					"Lapras",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					true,
					0,
					null,
					null,
					"P-Lapras", 'P');
			_pokemon=pokemon;
			_gbs=gbs;
		}
		public Vector<String> getDialogue(){
			PokePanel2 current=_gbs.getCurrentPanel();
			if(current!=null && M2.LAPRAS!=null && current._NPCpage == 0 && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.LAPRAS);
					count++;
				}
			}
			
			if(current!=null && current._NPCpage == 1){
				count=0;
			}
			return this._dialogue;
		}		
		public Item getGift() {
			
			int totalBadges = 0;
			for(int i=1; i<9; i++){
				if(_gbs.getPlayer().isGymLeaderDefeated(i)){
					totalBadges++;
				}
			}
			
			if (_gbs.getPlayer().getAllActive().size() < 6 && totalBadges>=3) {
				Pokemon.generateIV(_pokemon);
				Pokemon.generateNewStats(_pokemon);
				_pokemon.setCurrentHP(_pokemon.getMaxHP());
				_pokemon.setWildLevel(25);
				
				//Set the belt. This is important.
				_pokemon.setBelt(_gbs.getPlayer().getAllActive().size()+1);
				
				_gbs.getPlayer().getPokedex().addToCatchList(_pokemon.getDexNum());
				
				_gbs.getPlayer()._activePokemon.add(_pokemon);
				this.defeat();
				System.out.println("Lapras received!");
				return null;
			}
			System.out.println("Lapras not received.");
			return null;
		}
	}
	
	public static class KoreanPorygon extends Trainer{
		private Pokemon _pokemon;
		private GameBoyScreen _gbs;
		public KoreanPorygon(Pokemon porygon, GameBoyScreen gbs) throws IOException {
			super("Korean Porygon",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					null,
					"F-BlackGirl", 'F');
			_pokemon = porygon;
			_gbs=gbs;
		}
		public Item getGift() {
			if (_gbs.getPlayer().getAllActive().size() < 6 && !this.isDefeated()) {
				Pokemon.generateIV(_pokemon);
				Pokemon.generateNewStats(_pokemon);
				_pokemon.setCurrentHP(_pokemon.getMaxHP());
				_pokemon.setWildLevel(20);
				
				_gbs.getPlayer().getPokedex().addToCatchList(_pokemon.getDexNum());
				
				//Set the belt. This is important.
				_pokemon.setBelt(_gbs.getPlayer().getAllActive().size()+1);
				
				_gbs.getPlayer()._activePokemon.add(_pokemon);
				_gbs.getCurrentPanel()._receivedItem=true;
				this.defeat();
				return null;
			}
		//	System.out.println("Eevee not received.");
			return null;
		}
	}
	
	public static class KenMiller extends Trainer{
		private Pokemon _pokemon;
		private GameBoyScreen _gbs;
		public KenMiller(Pokemon eevee, GameBoyScreen gbs) throws IOException {
			super("KenMiller",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					null,
					"KenMiller", '#');
			_pokemon = eevee;
			_gbs=gbs;
		}
		public Item getGift() {
			if (_gbs.getPlayer().getAllActive().size() < 6 && !this.isDefeated()) {
				Pokemon.generateIV(_pokemon);
				Pokemon.generateNewStats(_pokemon);
				_pokemon.setCurrentHP(_pokemon.getMaxHP());
				_pokemon.setWildLevel(5);
				
				_gbs.getPlayer().getPokedex().addToCatchList(_pokemon.getDexNum());
				
				//Set the belt. This is important.
				_pokemon.setBelt(_gbs.getPlayer().getAllActive().size()+1);
				
				_gbs.getPlayer()._activePokemon.add(_pokemon);
				_gbs.getCurrentPanel()._receivedItem=true;
				this.defeat();
				SysOut.print("defeated: " + this.isDefeated());
				System.out.println("Eevee received!");
				return null;
			}
		//	System.out.println("Eevee not received.");
			return null;
		}
	}
	
	public static class GeoChemProf extends Trainer{
		private GameBoyScreen _gbs;
		public GeoChemProf(GameBoyScreen gbs){
			super("GeoChemProf", 0,0,0,0,0,0,false,false,100,null,null,"M-GlassesProfessor", 'M');
			_gbs = gbs;
			this.getDialogue().add("Hi there. I'm a Professor in the Geology department.");
			this.getDialogue().add("My department does a lot of work with Rock-type Pokemon,");
			this.getDialogue().add("but we are also very interested in fossils.");
			this.getDialogue().add("We've even developed a way to resurrect ancient Pokemon.");
			this.getDialogue().add("Come back later in future releases for updates.");
			//this.getDialogue().add("Do you have any fossils for me to see?");
		}
//		public Item getGift(){
//			
//			if(_gbs.getPlayer().getAllActive()!=null && _gbs.getPlayer().getAllActive().size() < 6 && !this.isDefeated()){
//				if(_gbs.getPlayer().getAllItems().get(Item.DOME_FOSSIL).getRemaining() > 0){
//					_gbs.getPlayer().getAllItems().get(Item.DOME_FOSSIL).setRemaining(0);
//					Pokemon fossil = new Pokemon.Kabuto().setWildLevel(25);
//					Pokemon.generateIV(fossil);
//					Pokemon.generateNewStats(fossil);
//					fossil.setCurrentHP(fossil.getMaxHP());
//					fossil.setBelt(_gbs.getPlayer().getAllActive().size()+1);
//					
//					_gbs.getPlayer().getPokedex().addToCatchList(fossil.getDexNum());
//					
//					_gbs.getPlayer()._activePokemon.add(fossil);
//					_gbs.getCurrentPanel()._receivedItem=true;
//				
//					this.getPostItemDialogue().add("What a fantastic specimen that Kabuto is!");
//					this.getPostItemDialogue().add("Please come see me again if you find any more fossils.");
//					
//					this.defeatAndPostItemize();
//					return null;
//				}
//				if(_gbs.getPlayer().getAllActive()!=null && _gbs.getPlayer().getAllItems().get(Item.HELIX_FOSSIL).getRemaining() > 0){
//					_gbs.getPlayer().getAllItems().get(Item.HELIX_FOSSIL).setRemaining(0);
//					Pokemon fossil = new Pokemon.Omanyte().setWildLevel(25);
//					Pokemon.generateIV(fossil);
//					Pokemon.generateNewStats(fossil);
//					fossil.setCurrentHP(fossil.getMaxHP());
//					fossil.setBelt(_gbs.getPlayer().getAllActive().size()+1);
//					
//					_gbs.getPlayer().getPokedex().addToCatchList(fossil.getDexNum());
//					
//					_gbs.getPlayer()._activePokemon.add(fossil);
//					_gbs.getCurrentPanel()._receivedItem=true;
//				
//					this.getPostItemDialogue().add("An Omanyte, very rare indeed!");
//					this.getPostItemDialogue().add("Please come see me again if you find any more fossils.");
//					
//					this.defeatAndPostItemize();
//					return null;
//				}
//				if(_gbs.getPlayer().getAllActive()!=null && _gbs.getPlayer().getAllItems().get(Item.OLD_AMBER).getRemaining() > 0){
//					_gbs.getPlayer().getAllItems().get(Item.OLD_AMBER).setRemaining(0);
//					Pokemon fossil = new Pokemon.Aerodactyl().setWildLevel(30);
//					Pokemon.generateIV(fossil);
//					Pokemon.generateNewStats(fossil);
//					fossil.setCurrentHP(fossil.getMaxHP());
//					fossil.setBelt(_gbs.getPlayer().getAllActive().size()+1);
//					
//					_gbs.getPlayer().getPokedex().addToCatchList(fossil.getDexNum());
//					
//					_gbs.getPlayer()._activePokemon.add(fossil);
//					_gbs.getCurrentPanel()._receivedItem=true;
//				
//					this.getPostItemDialogue().add("What a magnificient and terrifying creature Aerodactyl was!");
//					this.getPostItemDialogue().add("We have a skeleton of one hanging in the atrium, you know.");
//					this.getPostItemDialogue().add("Please come see me again if you find any more fossils.");
//					
//					this.defeatAndPostItemize();
//					return null;
//				}
//			}
//			else{
//				this.getDialogue().add("Even if you did have a fossil, it appears your belt is full.");
//			}
//			
//			return null;
//		}
	}
	
	public static class Chansey extends Trainer {
		private PokePanel2 current;
		private int count=0;
        public Chansey(PokePanel2 panel)
                throws IOException {
            super("Chansey", 0, 0, 0, 0, 0, 0, false, false, 0, null,
                    null, (("/PokemonFiles/TrainerImages/P-Chansey/DownFace.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/UpFace.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/LeftFace.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/RightFace.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/Down1.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/DownFace.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/Up1.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/UpFace.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/Left1.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/LeftFace.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/Right1.png")),
                    (("/PokemonFiles/TrainerImages/P-Chansey/RightFace.png")),
                    null, 'P');
            current=panel;
        }
		public Vector<String> getDialogue(){
			if(current!=null && M2.CHANSEY!=null && current._NPCpage == 0 && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.CHANSEY);
					count++;
				}
			}
			
			if(current!=null && current._NPCpage == 1){
				count=0;
			}
			return this._dialogue;
		}
	}

	public static class Jigglypuff extends Trainer {
		private PokePanel2 current;
		private int count=0;
        public Jigglypuff(PokePanel2 panel)
                throws IOException {
            super("Jigglypuff", 0, 0, 0, 0, 0, 0, false, false, 0, null,
                    null, (("P-Jigglypuff")),
                    'P');
            current=panel;
        }
		public Vector<String> getDialogue(){
			if(current!=null && M2.JIGGLYPUFF!=null && current._NPCpage == 0 && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.JIGGLYPUFF);
					count++;
				}
			}
			
			if(current!=null && current._NPCpage == 1){
				count=0;
			}
			return this._dialogue;
		}
	}
	
	public static class Mr_Mime extends Trainer {
		private PokePanel2 current;
		private int count=0;
        public Mr_Mime(PokePanel2 panel)
                throws IOException {
            super("Mr Mime", 0, 0, 0, 0, 0, 0, false, false, 0, null,
                    null, (("P-Mr_Mime")),
                    'P');
            current=panel;
        }
		public Vector<String> getDialogue(){
			if(current!=null && M2.MR_MIME!=null && current._NPCpage == 0 && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.MR_MIME);
					count++;
				}
			}
			
			if(current!=null && current._NPCpage == 1){
				count=0;
			}
			return this._dialogue;
		}
	}
	
	public static class Sandshrew extends Trainer {
		private PokePanel2 current;
		private int count=0;
        public Sandshrew(PokePanel2 panel)
                throws IOException {
            super("Sandshrew", 0, 0, 0, 0, 0, 0, false, false, 0, null,
                    null, (("P-Sandshrew")),
                    'P');
            current=panel;
        }
		public Vector<String> getDialogue(){
			if(current!=null && M2.SANDSHREW!=null && current._NPCpage == 0 && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.SANDSHREW);
					count++;
				}
			}
			
			if(current!=null && current._NPCpage == 1){
				count=0;
			}
			return this._dialogue;
		}
	}
	
	public static class Ninetails extends Trainer {
		public Ninetails(Vector<Pokemon> belt) throws IOException {
			super(
					"Ninetails",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"P-Ninetails", 'M');
		}
	}
	
	public static class Goku extends Trainer {
		public Goku(Vector<Pokemon> belt) throws IOException {
			super(
					"Goku",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"Goku", 'M');
		}
	}
	public static class Link extends Trainer {
		public Link(Vector<Pokemon> belt) throws IOException {
			super(
					"Link",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"Link", 'M');
		}
	}
	
	public static class Sasuke extends Trainer {
        public Sasuke(Vector<Pokemon> belt)
                throws IOException {
            super("Sasuke", 0, 0, 0, 0, 0, 0, false, false, 0, null,
                    belt,
                   "Sasuke", 'M');
        }
	}
	
	public static class Map extends Trainer {
		@SuppressWarnings("unused")
		private PokePanel2 current;
		
		public Map(PokePanel2 curr){
			super("Map", 0, 0, 0, 0, 0, 0, false, false, 0 ,null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			current = curr;
			_dialogue.add("");
		}
	}
	
	public static class StarterPokeBall extends Trainer {
		private Pokemon _pokemon;
		private GameBoyScreen _gbs;

		public StarterPokeBall(Pokemon pokemon, GameBoyScreen gbs)
				throws IOException {
			super("#Starter Pokemon", 0, 0, 0, 0, 0, 0, false, true, 0, null,
					null, (("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")), null,
					null, null, null, null, null, null, null, null, 'P');
			_pokemon = pokemon;
			_gbs = gbs;
		}

		public Item getGift() {
			if (_gbs.getPlayer().getAllActive().size() == 0) {
				Pokemon.generateIV(_pokemon);
				Pokemon.generateNewStats(_pokemon);
				_pokemon.setCurrentHP(_pokemon.getMaxHP());
				_pokemon.setWildLevel(5);
				_gbs.getPlayer()._activePokemon.add(_pokemon);
				this.defeat();
			}
			return null;
		}
	}
	
	public static class MartMenu extends Trainer{
		private PokePanel2 current;
		public MartMenu(String martLoc, PokePanel2 current){
			super("Mart", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null, '#');
			this.current = current;
			this.setStationary(true);
			this.getDialogue().add("Welcome to the " + martLoc + " PokeMart!");
			this.getDialogue().add("<Please select an option>");
		}
		
		public Vector<String> getDialogue(){
			//("MART");
			if(current._NPCpage == 1){
				current._martMenuVisible = true;
				current.repaint();
			}
			else{
				current._martMenuVisible = false;
				current.repaint();
			}
	
			return this._dialogue;
		}
	}
	
	public static class PC  extends Trainer{
		private GameBoyScreen _gbs;
		private PokePanel2 current;
		
		public PC(GameBoyScreen gbs){
			super("PC", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null, '#');
			_gbs= gbs;
			this.current = _gbs.getCurrentPanel();
			this.setStationary(true);
			this.getDialogue().add(_gbs.getPlayer().getPlayerName() + " turned on the PC.");
			this.getDialogue().add("<Please select an option>");
		}
		
		public Vector<String> getDialogue(){
			//("PC");
			if(current._NPCpage == 1){
				current._pcVisible = true;
				current.repaint();
			}
			else if(current._pcVisible){
				this.setInterrupted(false);
				current._NPCpage=0;
				current.completionCheck=false;
				current._dialogueVisible=false;
			}
			else{
				current._pcVisible = false;
				current.repaint();
			}
	
			return this._dialogue;
		}
	}
	
	public static class PokemonCenter extends Trainer{
		private Player player;
		private int loc;
		private PokePanel2 current;
		public PokemonCenter(Player p, int location, PokePanel2 current){
			super("PokemonCenter", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			this.player = p;
			this.loc = location;
			this.current = current;
			this.setStationary(true);
			this.getDialogue().add("Welcome to our Pokemon Center.");
			this.getDialogue().add("Let us heal your Pokemon for you!");
			this.getDialogue().add("...");
			this.getDialogue().add("There, all better. Your Pokemon are fully healed.");
			this.getDialogue().add("We hope to see you again :)");
		}
		public Vector<String> getDialogue(){
			if(current._NPCpage == 2 && !current._healed){
				SysOut.print("PKMN CENTER SWITCHED FROM: " + player.getPkmnCenter());
				player.setPkmnCenter(loc);
				SysOut.print("PKMN CENTER SWITCHED TO: " +loc);
				
				current._heal.start();
				current._healed = true;
				
				player.healAllActive();
				
			}
			else{
				current._healed = false;
			}

			return this._dialogue;
		}
	}
	
	public static class Bed extends Trainer{
		private Player player;
		private int loc;
		private PokePanel2 current;
		public Bed(Player p, int location, PokePanel2 current){
			super("Bed", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			this.player = p;
			this.loc = location;
			this.current = current;
			this.setStationary(true);
			this.getDialogue().add("Ahh, a nice comfy bed...");
			this.getDialogue().add("...");
			this.getDialogue().add("You feel well rested!");
			this.getDialogue().add("Your Pokemon are fully healed.");
			
		}
		public Vector<String> getDialogue(){
			if(current._NPCpage == 1 && !current._healed){
				player.setPkmnCenter(loc);
				
				current._heal.start();
				current._healed = true;
				
				player.healAllActive();
				
			}
			else{
				current._healed = false;
			}

			return this._dialogue;
		}
	}
	
	public static class RocketTruck extends Trainer{
		private Player player;
		private int loc;
		private PokePanel2 current;
		public RocketTruck(Player p, int location, PokePanel2 current){
			super("RocketTruck", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			this.player = p;
			this.loc = location;
			this.current = current;
			this.setStationary(true);
			this.getDialogue().add("Welcome to Rocket: Fine Street Food!");
			this.getDialogue().add("Here's a free promotional sample for your Pokemon.");
			this.getDialogue().add("...");
			this.getDialogue().add("(Your Pokemon are fully healed.)");
			this.getDialogue().add("If you liked the food, tell your friends.");
			this.getDialogue().add("Team Rocket's blasting off again!");
		}
		public Vector<String> getDialogue(){
			if(current._NPCpage == 2 && !current._healed){
				//SysOut.print("PKMN CENTER SWITCHED FROM: " + player.getPkmnCenter());
				player.setPkmnCenter(loc);
				//SysOut.print("PKMN CENTER SWITCHED TO: " +loc);
				
				current._heal.start();
				current._healed = true;
				
				player.healAllActive();
				
			}
			else{
				current._healed = false;
			}

			return this._dialogue;
		}
	}
	public static class MamaKims extends Trainer{
		private Player player;
		private int loc;
		private PokePanel2 current;
		public MamaKims(Player p, int location, PokePanel2 current){
			super("MamaKims", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			this.player = p;
			this.loc = location;
			this.current = current;
			this.setStationary(true);
			this.getDialogue().add("Hi, welcome to Mama Kim's!");
			this.getDialogue().add("Here's some free Bulgogi for your Pokemon.");
			this.getDialogue().add("...");
			this.getDialogue().add("(Your Pokemon are fully healed.)");
			this.getDialogue().add("We offer many varieties of Korean food.");
			this.getDialogue().add("Glad you liked it! Please come again!");
		}
		public Vector<String> getDialogue(){
			if(current._NPCpage == 2 && !current._healed){
				//SysOut.print("PKMN CENTER SWITCHED FROM: " + player.getPkmnCenter());
				player.setPkmnCenter(loc);
				//SysOut.print("PKMN CENTER SWITCHED TO: " +loc);
				
				current._heal.start();
				current._healed = true;
				
				player.healAllActive();
				
			}
			else{
				current._healed = false;
			}

			return this._dialogue;
		}
	}
	public static class WaterfireCenter extends Trainer{
		private Player player;
		private int loc;
		private PokePanel2 current;
		public WaterfireCenter(Player p, int location, PokePanel2 current){
			super("WaterfireCenter", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			this.player = p;
			this.loc = location;
			this.current = current;
			this.setStationary(true);
			this.getDialogue().add("Welcome to the Waterfire Deep Sleep Therapy Center!");
			this.getDialogue().add("My buddy Hypno will now put your Pokemon to sleep for a few moments.");
			this.getDialogue().add("...");
			this.getDialogue().add("(Your Pokemon are fully healed.)");
			this.getDialogue().add("Give it a try yourself! Works wonders for tension headaches.");
		}
		public Vector<String> getDialogue(){
			if(current._NPCpage == 2 && !current._healed){
				//SysOut.print("PKMN CENTER SWITCHED FROM: " + player.getPkmnCenter());
				player.setPkmnCenter(loc);
				//SysOut.print("PKMN CENTER SWITCHED TO: " +loc);
				
				current._heal.start();
				current._healed = true;
				
				player.healAllActive();
				
			}
			else{
				current._healed = false;
			}

			return this._dialogue;
		}
	}
	
	public static class PaulyD extends Trainer{
		private Player player;
		private int loc;
		private PokePanel2 current;
		public PaulyD(Player p, int location, PokePanel2 current){
			super("PaulyD", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			this.player = p;
			this.loc = location;
			this.current = current;
			this.setStationary(true);
			this.getDialogue().add("Pauly D: GTL! Gym, Tan & Laundry-that's the life.");
			this.getDialogue().add("Thanks for coming out to FishCo! Here's a song for your Pokemon.");
			this.getDialogue().add("...");
			this.getDialogue().add("(Your Pokemon are fully healed.)");
			this.getDialogue().add("Get back at 'em bro!");
			this.getDialogue().add("Let your friends know if you liked my jams.");
		}
		public Vector<String> getDialogue(){
			if(current._NPCpage == 2 && !current._healed){
				//SysOut.print("PKMN CENTER SWITCHED FROM: " + player.getPkmnCenter());
				player.setPkmnCenter(loc);
				//SysOut.print("PKMN CENTER SWITCHED TO: " +loc);
				
				current._heal.start();
				current._healed = true;
				
				player.healAllActive();
				
			}
			else{
				current._healed = false;
			}

			return this._dialogue;
		}
	}
	
	public static class Rabbi extends Trainer{

        private Player player;
        private int loc;
        private PokePanel2 current;
        public Rabbi(Player p, int location, PokePanel2 current){
            super("Rabbi", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');

            this.player = p;
            this.loc = location;
            this.current = current;
            this.setStationary(true);
            this.getDialogue().add("Welcome to my humble home! We don't get many visitors here.");
            this.getDialogue().add("Why don't you rest a bit?");

            this.getDialogue().add("...");
            this.getDialogue().add("(Your Pokemon are fully healed.)");
            this.getDialogue().add("I hope your journey goes well.");
            this.getDialogue().add("Please come back and visit, my children love guests.");
        }
        public Vector<String> getDialogue(){
            if(current._NPCpage == 2 && !current._healed){
                //SysOut.print("PKMN CENTER SWITCHED FROM: " + player.getPkmnCenter());
                player.setPkmnCenter(loc);
                //SysOut.print("PKMN CENTER SWITCHED TO: " +loc);
               
                current._heal.start();
                current._healed = true;
               
                player.healAllActive();
               
            }
            else{
                current._healed = false;
            }

            return this._dialogue;
        }
    }
	
	public static class SciLiBlanket extends Trainer{

        private Player player;
        private int loc;
        private PokePanel2 current;
        public SciLiBlanket(Player p, int location, PokePanel2 current){
            super("SciLiBlanket", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');

            this.player = p;
            this.loc = location;
            this.current = current;
            this.setStationary(true);
            this.getDialogue().add("You look exhausted! I've been using caffeine pills, want one?");
            this.getDialogue().add("No? That's fine. Why don't you take a nap here instead.");

            this.getDialogue().add("...");
            this.getDialogue().add("(Your Pokemon are fully healed.)");
            this.getDialogue().add("Go get 'em tiger. I've been awake for 48 hours straight,");
            this.getDialogue().add("...and you might be a figment of my imagination, but I believe in you!");
        }
        public Vector<String> getDialogue(){
            if(current._NPCpage == 2 && !current._healed){
                //SysOut.print("PKMN CENTER SWITCHED FROM: " + player.getPkmnCenter());
                player.setPkmnCenter(loc);
                //SysOut.print("PKMN CENTER SWITCHED TO: " +loc);
               
                current._heal.start();
                current._healed = true;
               
                player.healAllActive();
               
            }
            else{
                current._healed = false;
            }

            return this._dialogue;
        }
    }

	public static class Grill extends Trainer{

        private Player player;
        private int loc;
        private PokePanel2 current;
        public Grill(Player p, int location, PokePanel2 current){
            super("Grill", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');

            this.player = p;
            this.loc = location;
            this.current = current;
            this.setStationary(true);
            this.getDialogue().add("There's some delicious food on the grill.");
            this.getDialogue().add("You and your Pokemon enjoy some barbecue :)");

            this.getDialogue().add("...");
            this.getDialogue().add("(Your Pokemon are fully healed.)");
        }
        public Vector<String> getDialogue(){
            if(current._NPCpage == 2 && !current._healed){
                //SysOut.print("PKMN CENTER SWITCHED FROM: " + player.getPkmnCenter());
                player.setPkmnCenter(loc);
                //SysOut.print("PKMN CENTER SWITCHED TO: " +loc);
               
                current._heal.start();
                current._healed = true;
               
                player.healAllActive();
               
            }
            else{
                current._healed = false;
            }

            return this._dialogue;
        }
    }
	
	public static class ItemObject extends Trainer {
		public ItemObject(Item gift) throws IOException {
			super("Item", 0, 0, 0, 0, 0, 0, false, true, 0, gift, null,
					(("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")), null,
					null, null, null, null, null, null, null, null, '#');
			this.setStationary(true);
			this._xCorrect=2;
			this._yCorrect=6;
			this.getDialogue().clear();
			this.getDialogue().add("You found a " + gift.getName() + "!");
		}
		
	}

	public static class PokeBall extends Trainer {
		private Pokemon _pokemon;
		private GameBoyScreen _gbs;

		public PokeBall(Pokemon pokemon, GameBoyScreen gbs) throws IOException {
			super("Pokemon", 0, 0, 0, 0, 0, 0, false, true, 0, null, null,
					(("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")),
					(("/PokemonFiles/TrainerImages/Item/item.png")), null,
					null, null, null, null, null, null, null, null, '#');
			_pokemon = pokemon;
			_gbs = gbs;
			this.setStationary(true);
		}

		public Item getGift() {
			Pokemon.generateIV(_pokemon);
			Pokemon.generateNewStats(_pokemon);
			_pokemon.setCurrentHP(_pokemon.getMaxHP());
			_gbs.getPlayer()._activePokemon.add(_pokemon);
			this.defeat();
			return null;
		}
	}
	
	public static class Sign extends Trainer {
		public Sign(){
			super("Sign", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "Sign", '#');
			this.setStationary(true);
		}
	}

	public static class Text extends Trainer{
		public Text(){
			super("Text", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null, '#');
			this.setStationary(true);
		}
	}
	
	public static class Gambino extends Trainer{
		public Gambino(Vector<Pokemon> belt) throws IOException{
			super("Childish", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, null, null, null, null, null, null, null, null, null,null,null,null,(("/PokemonFiles/TrainerImages/Gambino/Battle.png")), 'M');
		}
	}
	
	public static class GlitchMob extends Trainer{
		public GlitchMob(Vector<Pokemon> belt) throws IOException{
			super("Glitch Mob",0,0,0,0,0,0,false,false,0,null,belt,          null, null, null, null, null, null, null, null, null,null,null,null,(("/PokemonFiles/TrainerImages/Glitch Mob/Battle.png")),'M');
		}
	}
	
	public static class InvisTrainer extends Trainer{
		public InvisTrainer(Vector<Pokemon> belt) throws IOException {
			super("InvisTrainer", 0, 0, 0, 0, 0, 0, false, false, 0, null, belt, null, null, null, null, null, null, null, null, null,null,null,null,(("/PokemonFiles/TrainerImages/InvisTrainer/battle.png")), 'M');
			this.setStationary(true);
		}
	}
	
	public static class Generator extends Trainer{
		private PokePanel2 genRoom;
		private int count=0;
		public Generator(PokePanel2 room){
			super("Generator", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			this.setStationary(true);
			genRoom = room;
		}
		public void defeat(){
			super.defeat();
			BarHolLobby.powerOn = true;
			genRoom.setFlash(false);
			//genRoom.createBaseRoom();
			if(genRoom._gbs.getCurrentPanel()._roomNum == genRoom._roomNum)
				M2.playBG(M2.BH_LIGHT);
			this.getDialogue().clear();
			this.getDialogue().add("The power is on and running smoothly :)");
		}
		public Vector<String> getDialogue(){
			if(genRoom._NPCpage == 0 && !this.isDefeated() && this.isInterrupted()){
				if(count==0){
					M2.playFX(M2.SWITCH);
					count++;
				}
			}
			if(genRoom._NPCpage == 1 && !this.isDefeated()){
				this.defeat();
				genRoom.A_Button();
			}
			return super.getDialogue();
		}
	}
	
	public static class Mailbox extends Trainer{
		private PokePanel2 jww;
		private int T1764, T8107, T7084, T9001;
		public Mailbox(PokePanel2 room, int t1764, int t8107, int t7084, int t9001){
			super("Mailbox", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '#');
			this.setStationary(true);
			jww = room;
			T1764=t1764;
			T8107=t8107;
			T7084=t7084;
			T9001=t9001;
			this.resetFirstDialogue();
		}
		public Vector<String> getDialogue(){
			int took1764=5, took7084=6, took8107=7, took9001=8;
			if(jww._NPCpage == 0 && this.isInterrupted()){
				this.resetFirstDialogue();
				jww.mailboxOpen=true;
			}
			//EVENT BASED.
			if(jww._NPCpage ==1 && jww.mailNumber.length()==4){
				

				//To the next page.
				jww._NPCpage=2;
				
				if(jww.mailNumber.compareToIgnoreCase("1764")==0 && jww.getCheckList()!=null && jww.getCheckList().get(took1764)==0){
					//You gon get a dick today.
					jww.getMovingTrainers().get(T1764).unDefeat();
					jww.getCheckList().set(took1764,1);
					if(this._dialogue.size()!=3){
						this._dialogue.add("Box "+jww.mailNumber+"...alright, your package is on the left table.");
					}
				}
				else if(jww.mailNumber.compareToIgnoreCase("7084")==0 && jww.getCheckList()!=null && jww.getCheckList().get(took7084)==0){
					//You gon get a dick today.
					jww.getMovingTrainers().get(T7084).unDefeat();
					jww.getCheckList().set(took7084,1);
					if(this._dialogue.size()!=3){
						this._dialogue.add("Box "+jww.mailNumber+"...alright, your package is on the front table.");
					}
				}
				else if(jww.mailNumber.compareToIgnoreCase("8107")==0 && jww.getCheckList()!=null && jww.getCheckList().get(took8107)==0){
					//You gon get a dick today.
					jww.getMovingTrainers().get(T8107).unDefeat();
					jww.getCheckList().set(took8107,1);
					if(this._dialogue.size()!=3){
						this._dialogue.add("Box "+jww.mailNumber+"...alright, your package is on the left table.");
					}
				}
				else if(jww.mailNumber.compareToIgnoreCase("9001")==0 && jww.getCheckList()!=null && jww.getCheckList().get(took9001)==0){
					//You gon get a dick today.
					jww.getMovingTrainers().get(T9001).unDefeat();
					jww.getCheckList().set(took9001,1);
					if(this._dialogue.size()!=3){
						this._dialogue.add("Box "+jww.mailNumber+"...alright, your package is on the front table.");
					}
				}
				else{
					//No package for you.
					if(this._dialogue.size()!=3){
						this._dialogue.add("Box "+jww.mailNumber+"...sorry, no packages here.");
					}
				}	
			}
			
			return super.getDialogue();
		}
		
		public void resetFirstDialogue(){
			this._dialogue.clear();
			this._dialogue.add("What's your box number?  (Type in 4-digit box number, then hit Enter.)"); //0
			this._dialogue.add(" "); //1
		}
	}
	
	
	public static class OrgoQuestioner extends Trainer{
		private PokePanel2 suggsGym;
		private String correctAnswer;
		public OrgoQuestioner(PokePanel2 room, String spriteDir, String correct, Vector<Pokemon> belt){
			super("OrgoQuestioner", 0, 0, 0, 0, 0, 0, false, false, 0,null,belt, spriteDir,'#');
			this.setStationary(true);
			suggsGym = room;
			this.correctAnswer=correct;
			this.resetFirstDialogue();
		}
		public Vector<String> getDialogue(){
			if(suggsGym._NPCpage == 0 && this.isInterrupted()){
				this.resetFirstDialogue();
				if(!this.isDefeated()){
					suggsGym.mailboxOpen=true;
				}
			}
			//EVENT BASED.
			if(suggsGym._NPCpage ==1 && suggsGym.mailNumber.length()==1){
				

				//To the next page.
				suggsGym._NPCpage=2;
				
				if(suggsGym.mailNumber.compareToIgnoreCase(correctAnswer)==0){
					this.defeat();
					this.setBelt(null);
					if(this._dialogue.size()!=3){
						this._dialogue.add("Well done! You may continue.");
					}
				}
				else{
					//No package for you.
					if(this._dialogue.size()!=3){
						this._dialogue.add("That is incorrect! Time for an Orgo problem session!");
					}
				}	
			}
			
			return super.getDialogue();
		}
		
		public void resetFirstDialogue(){
			this._dialogue.clear();
			if(!this.isDefeated()){
				this._dialogue.add("What's your box number?  (Type in 4-digit box number, then hit Enter.)"); //0
				this._dialogue.add(" "); //1
			}
			else{
				this._dialogue.add("Well done! You may continue.");
			}
		}
	}
	
	public static class SurfingCopOnLapras extends Trainer {
		public SurfingCopOnLapras(Vector<Pokemon> belt) throws IOException {
			super(
					"Cop",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-SurfingCopOnLapras", 'M');
			this._canSurf=true;
			_xCorrect=-7;
			_yCorrect=-13;
		}

	}
	
	public static class Cop extends Trainer {
		public Cop(Vector<Pokemon> belt) throws IOException {
			super(
					"Cop",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"Cop", 'M');
			_yCorrect=-2;
		}
	}
	
	public static class HenryBruce extends Trainer {
		public HenryBruce(Vector<Pokemon> belt) throws IOException {
			super(
					"Henry",
					0,
					0,
					0,
					0,
					0,
					0,
					false,
					false,
					100,
					null,
					belt,
					"M-Henry", 'M');
		}
	}
	
	public static class CutBush extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		private PokePanel2 current;
		public CutBush(Player p, int location, PokePanel2 current){
			super("CutBush", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "CutBush", '#');
			this.player = p;
			this.loc = location;
			this.current = current;
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add("A small, paradoxically obstructive tree stands in your way.");
			this.getDialogue().add("Would you like to use Cut?        A = Yes      B = No ");
			this.getDialogue().add(getSlave(player) + " used Cut!");
			this.getDialogue().add(getSlave(player) + " used Cut!");
			this.getDialogue().add("A Pokemon may be able to cut it down.");
			
		}
		public String getSlave(Player p){
			Pokemon subject;
			for (int i=0; i< p.getAllActive().size();i++){
				subject= p.getActivePokemon(i);
					for (int j=0; j<subject.getAttacks().size();j++){
					if(subject.getAttacks().get(j).getName()=="Cut"){
						return subject.getName();
					}
				}
			}
			return "";
		}
		public Vector<String> getDialogue(){
			if(getSlave(player)!=""){
				if(current._NPCpage == 1){
					return this._dialogue;
				}
				if(current._NPCpage == 2){
					return this._dialogue;
				}
				if(current._NPCpage == 3){
					this.defeat();
					current._cutTimer.start();
					current.cutBush=true;
					return null;
				}
			}
			else{
				if(current._NPCpage == 1){
					current._NPCpage=4;
					return this._dialogue;
				}
			}
			return this._dialogue;
			
		}
	}
	
	public static class CutCan extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		private PokePanel2 current;
		public CutCan(Player p, int location, PokePanel2 current){
			super("CutCan", 0, 0, 0, -2, 0, 0, false, false, 0, null, null, "CutCan", '#');
			this.player = p;
			this.loc = location;
			this.current = current;
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add("This trash can is just barely too tall to hurdle over.");
			this.getDialogue().add("Would you like to use Cut?        A = Yes      B = No ");
			this.getDialogue().add(getSlave(player) + " used Cut!");
			this.getDialogue().add(getSlave(player) + " used Cut!");
			this.getDialogue().add("A Pokemon may be able to cut it down.");
			
		}
		public String getSlave(Player p){
			Pokemon subject;
			for (int i=0; i< p.getAllActive().size();i++){
				subject= p.getActivePokemon(i);
					for (int j=0; j<subject.getAttacks().size();j++){
					if(subject.getAttacks().get(j).getName()=="Cut"){
						return subject.getName();
					}
				}
			}
			return "";
		}
		public Vector<String> getDialogue(){
			if(getSlave(player)!=""){
				if(current._NPCpage == 1){
					return this._dialogue;
				}
				if(current._NPCpage == 2){
					return this._dialogue;
				}
				if(current._NPCpage == 3){
					this.defeat();
					current._cutTimer.start();
					current.cutCan=true;
					return null;
				}
			}
			else{
				if(current._NPCpage == 1){
					current._NPCpage=4;
					return this._dialogue;
				}
			}
			return this._dialogue;
			
		}
	}
	
	public static class SurfGal extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private GameBoyScreen _gbs;
		private PokePanel2 current;
		private int count =0;
		public SurfGal(Player p,  GameBoyScreen gbs, PokePanel2 panel){
			super("SurfGal", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "F-BlackHairBlueDress", '#');
			this.player = p;
			this._gbs=gbs;
			this.current=panel;
			this.setStationary(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add("Wanna get in the water?");
			this.getDialogue().add("Here, I'll let you borrow my Lapras.");
			this.getDialogue().add(" ");
			this.getDialogue().add("Oh, you're already surfing. Have fun!");
			
		}

		public Vector<String> getDialogue(){
			if(current._NPCpage == 1){
				count=0;
			}
			if(current._NPCpage == 1 && _gbs.getSurf() && count==0){
				current._NPCpage=3;
			}
			if(current._NPCpage == 2){
				if(count==0 && !_gbs.getSurf()){
					_gbs.setSurf(true);
					_gbs.getPlayer().addDest(6, 17);
					_gbs.getPlayer().setAvoidMethod("horiz");
					_gbs.getPlayer().setIgnoring(true);
					current._approachTimer.start();
					M2.playBG(M2.SURF);
					_gbs.setMode(false);
					current._busy=false;
					current.completionCheck=false;
					this.setInterrupted(false);
					current._dialogueVisible=false;
					count++;
				}
				return null;
			}
			
			return this._dialogue;
		}
	}
	

	public static class Swiper extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		private PokePanel2 current;
		private int badge, barricadeNum;
		public Swiper(Player p, PokePanel2 current, int badge, int barricadeNum){
			super("Swiper", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "Swiper", '#');
			this.player = p;
			this.badge=badge;
			this.barricadeNum=barricadeNum;
			this.current = current;
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add(player.getPlayerName() + " swiped their Brown ID.");
			this.getDialogue().add("The door unlocked!");
			this.getDialogue().add("The door unlocked!");
			this.getDialogue().add("The door did not unlock. It seems you don't have card access yet.");
			
		}
		
		public Vector<String> getDialogue(){
			if(player.isGymLeaderDefeated(badge)){
				if(current._NPCpage == 1){
					return this._dialogue;
				}
				if(current._NPCpage == 2){
					this.defeat();
					M2.playFX(M2.UNLOCK);
					current.completionCheck=false;
					current._dialogueVisible=false;
					this.setInterrupted(false);
					current._busy=false;
					current._NPCpage=0;
					current.scanForAllEvents();
					//Defeats barricade for you.
					if (barricadeNum>=0){
						current.getMovingTrainers().get(barricadeNum).defeat();
					}
					return null;
				}
			}
			else{
				if(current._NPCpage == 1){
					current._NPCpage=3;
					return this._dialogue;
				}
			}
			return this._dialogue;
			
		}
	}
	public static class FenceSwiper extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		private PokePanel2 current;
		private int badge;
		public FenceSwiper(Player p, PokePanel2 current, int badge){
			super("FenceSwiper", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "FalconPunch", '#');
			this.player = p;
			this.badge=badge;
			this.current = current;
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add("The fence looks weak here...");
			this.getDialogue().add("FALCONNN PUNCHHH!!!");
			this.getDialogue().add("FALCONNN PUNCHHH!!!");
			this.getDialogue().add("Perhaps if you were a little tougher...");
			
		}
		
		public Vector<String> getDialogue(){
			if(player.isGymLeaderDefeated(badge)){
				if(current._NPCpage == 1){
					return this._dialogue;
				}
				if(current._NPCpage == 2){
					this.defeat();
					M2.playFX(M2.UNLOCK);
					current.completionCheck=false;
					current._dialogueVisible=false;
					this.setInterrupted(false);
					current._busy=false;
					current._NPCpage=0;
					current.scanForAllEvents();
					//Defeats barricade for you.
					return null;
				}
			}
			else{
				if(current._NPCpage == 1){
					current._NPCpage=3;
					return this._dialogue;
				}
			}
			return this._dialogue;
			
		}
	}
	
	public static class Barricade extends Trainer{
		public Barricade(){
			super("Barricade", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "Barricade", '#');
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add("A barricade is in your path.");
			this.getDialogue().add("A card swiper may be nearby.");
		}
	}
	
	public static class RISDSwiper extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		private PokePanel2 current;
		public RISDSwiper(Player p, PokePanel2 current){
			super("RISD Swiper", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "Swiper", '#');
			this.player = p;
			this.current = current;
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add(player.getPlayerName() + " checks their wallet.");
			this.getDialogue().add(player.getPlayerName() +" used the RISD ID. The door unlocked!");
			this.getDialogue().add(player.getPlayerName() +" used the RISD ID. The door unlocked!");
			this.getDialogue().add("It seems you don't have the right ID card yet.");
			

			
		}
		public Vector<String> getDialogue(){
			//RISD ID
			if(player.getAllItems().get(Item.RISD_ID).getRemaining()>0){
				if(current._NPCpage == 1){
					return this._dialogue;
				}
				if(current._NPCpage == 2){
					this.defeat();
					M2.playFX(M2.UNLOCK);
					current.completionCheck=false;
					current._dialogueVisible=false;
					this.setInterrupted(false);
					current._busy=false;
					current._NPCpage=0;
					current.scanForAllEvents();
					return null;
				}
			}
			else{
				if(current._NPCpage == 1){
					current._NPCpage=3;
					return this._dialogue;
				}
			}
			return this._dialogue;
			
		}
	}
	public static class IronWokSwiper extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		private PokePanel2 current;
		public IronWokSwiper(Player p, PokePanel2 current){
			super("Iron Wok Swiper", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "Swiper", '#');
			this.player = p;
			this.current = current;
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add(player.getPlayerName() + " checks the door.");
			this.getDialogue().add(player.getPlayerName() +" used the Kitchen Key. The door unlocked!");
			this.getDialogue().add(player.getPlayerName() +" used the Kitchen Key. The door unlocked!");
			this.getDialogue().add("It seems you don't have the key...");
			

			
		}
		public Vector<String> getDialogue(){
			//IronWok Key
			if(player.getAllItems().get(Item.KITCHEN_KEY).getRemaining()>0){
				if(current._NPCpage == 1){
					return this._dialogue;
				}
				if(current._NPCpage == 2){
					this.defeat();
					M2.playFX(M2.UNLOCK);
					current.completionCheck=false;
					current._dialogueVisible=false;
					this.setInterrupted(false);
					current._busy=false;
					current._NPCpage=0;
					current.scanForAllEvents();
					return null;
				}
			}
			else{
				if(current._NPCpage == 1){
					current._NPCpage=3;
					return this._dialogue;
				}
			}
			return this._dialogue;
			
		}
	}
	
	public static class PowerStGarageSwiper extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		private PokePanel2 current;
		public PowerStGarageSwiper(Player p, PokePanel2 current){
			super("Power St Garage Swiper", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "Swiper", '#');
			this.player = p;
			this.current = current;
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add(player.getPlayerName() + " checks their wallet.");
			this.getDialogue().add(player.getPlayerName() +" used the Zipcar Membership. The door unlocked!");
			this.getDialogue().add(player.getPlayerName() +" used the Zipcar Membership. The door unlocked!");
			this.getDialogue().add("It seems you don't have a membership...");
			

			
		}
		public Vector<String> getDialogue(){
			//PowerSt Key
			if(player.getAllItems().get(Item.ZIPCAR_CARD).getRemaining()>0){
				if(current._NPCpage == 1){
					return this._dialogue;
				}
				if(current._NPCpage == 2){
					this.defeat();
					M2.playFX(M2.UNLOCK);
					current.completionCheck=false;
					current._dialogueVisible=false;
					this.setInterrupted(false);
					current._busy=false;
					current._NPCpage=0;
					current.scanForAllEvents();
					return null;
				}
			}
			else{
				if(current._NPCpage == 1){
					current._NPCpage=3;
					return this._dialogue;
				}
			}
			return this._dialogue;
			
		}
	}
	
	public static class LadderSwiper extends Trainer{
		private Player player;
		@SuppressWarnings("unused")
		private int loc;
		private PokePanel2 current;
		public LadderSwiper(Player p, PokePanel2 current){
			super("LadderSwiper", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, "Swiper", '#');
			this.player = p;
			this.current = current;
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this.getDialogue().add(player.getPlayerName() + " looks up at the open window.");
			this.getDialogue().add(player.getPlayerName() +" used the telescoping ladder!");
			this.getDialogue().add(player.getPlayerName() +" used the telescoping ladder!");
			this.getDialogue().add("It's just out of reach...");
			

			
		}
		public Vector<String> getDialogue(){
			//IronWok Key
			if(player.getAllItems().get(Item.KITCHEN_KEY).getRemaining()>0){
				if(current._NPCpage == 1){
					return this._dialogue;
				}
				if(current._NPCpage == 2){
					this.defeat();
					M2.playFX(M2.UNLOCK);
					current.completionCheck=false;
					current._dialogueVisible=false;
					this.setInterrupted(false);
					current._busy=false;
					current._NPCpage=0;
					current.scanForAllEvents();
					return null;
				}
			}
			else{
				if(current._NPCpage == 1){
					current._NPCpage=3;
					return this._dialogue;
				}
			}
			return this._dialogue;
			
		}
	}
	
	public static class LegendaryOrWild extends Trainer{
		public String _cry;
		public String spriteDirectory;
		public GameBoyScreen _gbs;
		public M2 _cryFX;
		public int count=0;
		public int count2=0;
		public LegendaryOrWild(GameBoyScreen gbs, Pokemon wild, String cry, String spriteDir, M2 cryFX){
			super("LegendaryOrWild", 0, 0, 0, 0, 0, 0, false, false, 0, null, null, spriteDir,'#');
			this.setStationary(true);
			this.setVanishing(true);
			this._dialogue=new Vector<String>();
			this._dialogue.add(cry);
			this._dialogue.add(" ");
			this.legendary=wild;
			_gbs=gbs;
			_cry=cry;
			_cryFX=cryFX;
			spriteDirectory=spriteDir;
			
			if(spriteDir=="P-Magmar"){
				_xCorrect=-4;
				_yCorrect=-6;
			}
			else if(spriteDir=="P-Hitmonchan"){
				_xCorrect=0;
				_yCorrect=-1;
			}
			else if(spriteDir=="P-Hitmonlee"){
				_xCorrect=0;
				_yCorrect=-1;
			}
			else if(spriteDir=="P-Snorlax"){
				this._xCorrect=-8;
				this._yCorrect=-7;
				this._dialogue.clear();
				this._dialogue.add(_gbs.getPlayer().getPlayerName()+ " used the Pokeflute.");
				this._dialogue.add(cry);
				this._dialogue.add(" ");
			}
		}
		public Vector<String> getDialogue(){
			PokePanel2 current = _gbs.getCurrentPanel();
			if(spriteDirectory=="P-Snorlax"){
				//if you talk first time, play pokeflute.
				if(current._NPCpage == 0 && this.isInterrupted()){
					if(count==0){
						M2.playFX(M2.POKEFLUTE);
						count++;
					}
				}
				//then cry.
				if(_cryFX!=null && current._NPCpage == 1 && this.isInterrupted()){
					if(count2==0){
						M2.playFX(_cryFX);
						count2++;
					}
				}
				if(current._NPCpage == 2){
					current.completionCheck=false;
					current._dialogueVisible=false;
					this.setInterrupted(false);
					current._NPCpage=0;
					current.legendary=true;
					NewBattleScreen.forceFight=true;
					current._attack.start();
					return null;
				}
				return this._dialogue;
			}
			else{
				if(_cryFX!=null && current._NPCpage == 0 && this.isInterrupted()){
					if(count==0){
						M2.playFX(_cryFX);
						count++;
					}
				}
				
				if(current._NPCpage == 1){
					current.completionCheck=false;
					current._dialogueVisible=false;
					this.setInterrupted(false);
					current._NPCpage=0;
					current.legendary=true;
					NewBattleScreen.forceFight=true;
					current._attack.start();
					return null;
				}
				return this._dialogue;
			}
			
		}
	}
	
	
}