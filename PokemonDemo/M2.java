package PokemonDemo;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;

public enum M2 {

	JAVABOY_INTRO,
	TITLE_SCREEN,
	RUTH_INTRO,
	KEENEY,
	RA_INTRO,
	RA_WALK,
	RA_ROOM,
	RIVAL_MUSIC,
	HEALING,
	POISON_DAMAGE,
	RECEIVED_POKEMON,
	EVOLVING,
	ROUTE_1,
	PATRIOTS,
	WAYLAND_ARCH,
	WRISTON,
	GYM,
	RATTY_ENTRANCE,
	IVY_ROOM,
	BATTLE_START,
	BATTLE,
	TRAINER_APPROACH_M,
	TRAINER_APPROACH_F,
	ITEM_RECEIVED,
	LEVEL_UP,
	POKEFLUTE,
	BADGE,
	OLIVE_PIT, 
	MAIN_GREEN, 
	REGISTRAR,
	HILLEL,
	TWC,
	ROUTE_2,
	POKECENTER,
	BIOMED,
	BIKE,
	CUT,
	SALOMON,
	ASHAMU,
	LEEDS,
	VENLAB,
	METCALF,
	LITTLEFIELD,
	REDHOUSE,
	SCIENCE_QUAD,
	LEDGE,
	WILD_POKEMON_CAUGHT,
	WILD_POKEMON_FAINTED,
	SURF, 
	EVOLVED,
	WILSON_LOBBY,
	FAUNCE,
	VDUB_LOBBY,
	VDUB_DINING_HALL,
	SPIRITUS,
	DIG, TELEPORT_BACK, TELEPORT_AWAY, FLY,
	DOOR,
	CIT,
	BH_LIGHT,
	BH_DARK,
	ELECTRODE,
	UNLOCK,
	LAB,
	SWITCH,
	SCILI,
	SCILI_FLOOR,
	ALARM,
	SCILI_ROOF,
	BATTLE_CRY, 
	PRINCE, 
	FISHCO,
	OUTSIDE_FISHCO,
	GYM_BATTLE,
	SRI,
	MIKE,
	DAVID,
	MAT,
	CHAMPION,
	SCILI_STACKS,
	MAGMAR,	URSARING,SNORLAX,HITMONLEE,HITMONCHAN,PSYDUCK,SEADRA,LAPRAS,CHANSEY,JIGGLYPUFF,MR_MIME,
	WATERFIRE_NIGHT,
	WATERFIRE_DAY,
	MAFIA_BASEMENT,
	BATTLE_VICTORY,
	SILENCE,
	QUIET_GREEN,
	RUTH_GYM,
	VIA_VIA,
	VIAVIA_LOBBY,
	CARRIE,
	SLATER,
	GRADUATION,
	CREDITS,
	JOS,
	WATSON,
	THAYER,
	SPG,
	UNIVERSITY_HALL,
	MOTOROLA,
	BAMBOO_GARDEN,
	GAMBINO_BATTLE,
	GLITCH_MOB_BATTLE,
	SPRING_WEEKEND,
	SANDSHREW;
	
	private static M2 current, currentFX;
	public static PokemonPlayer bgPlayer;
	public static PokemonPlayer fxPlayer;
	private static int dex;

	public static void setBattleCry(int dexNum){
		dex=dexNum;
	}
	
	public String getFileName(){
		String path = "/Music/";
		String b = "Background/";
		String f = "SoundFX/";
		String p = "BattleCry/";
		switch(this){
		case JAVABOY_INTRO: return path+b+"logo.mp3";
		case TITLE_SCREEN: return path+b+"RAC-Intro.mp3";
		case RUTH_INTRO: return path+b+"NewGameIntro.mp3";
		case KEENEY: return path+b+"pallet-town.mp3";
		case RA_WALK: return path+b+"GuidedWalk.mp3";
		case RA_ROOM: return path+b+"OakLab.mp3";
		case RIVAL_MUSIC: return path+b+"RivalEntry.mp3";
		case HEALING: return path+f+"heal.mp3";
		case POISON_DAMAGE: return path+f+"psn.mp3";
		case RECEIVED_POKEMON: return path+f+"itemreceived.mp3";//"NewPokemon.mp3";
		case EVOLVING: return path+f+"evolving.mp3";
		case ROUTE_1: return path+b+"route-1.mp3";
		case RA_INTRO: return path+b+"OakWait.mp3";
		case PATRIOTS: return path+b+"viridianforest.mp3";
		case WRISTON: return path+b+"wriston.mp3";
		case WAYLAND_ARCH: return path+b+"waylandarch.mp3";
		case GYM: return path+b+"gym.mp3";
		case IVY_ROOM: return path+b+"pokecenter.mp3";
		case TRAINER_APPROACH_M: return path+b+"menc.mp3";
		case TRAINER_APPROACH_F: return path+b+"fenc.mp3";
		case BATTLE_START: return path+f+"battleStart.mp3";
		case BATTLE: return path+b+"battleLoop.mp3";
		case ITEM_RECEIVED: return path+f+"itemreceived.mp3";
		case LEVEL_UP: return path+f+"LevelUp.mp3";
		case BADGE: return path+f+"Badge.mp3";
		case MAIN_GREEN: return path+b+"maingreen.mp3";
		case OLIVE_PIT: return path+b+"OlivePit.mp3";
		case TWC: return path+b+"TWC.mp3";
		case HILLEL: return path+b+"Hillel.mp3";
		case POKECENTER: return path+b+"pokecenter.mp3";
		case ROUTE_2: return path+b+"Route2.mp3";
		case BIOMED: return path+b+"BioMed.mp3";
		case BIKE: return path+b+"bike.mp3";
		case CUT: return path+f+"cut.mp3";
		case SALOMON: return path+b+"Salomon.mp3";
		case ASHAMU: return path+b+"Ashamu.mp3";
		case LEEDS: return path+b+"Leeds.mp3";
		case VENLAB: return path+b+"VENLab.mp3";
		case METCALF: return path+b+"MetcalfLab.mp3";
		case LITTLEFIELD: return path+b+"Littlefield.mp3";
		case REDHOUSE: return path+b+"RedHouse.mp3";
		case SCIENCE_QUAD: return path+b+"SciQuad.mp3";
		case LEDGE: return path+f+"ledge4.mp3";
		case REGISTRAR: return path+b+"Registrar.mp3";
		case SURF: return path+b+"Surf.mp3";
		case EVOLVED: return path+f+"evolved.mp3";
		case WILSON_LOBBY: return path+f+"evolved.mp3";
		case FAUNCE: return path+b+"faunce.mp3";
		case VDUB_DINING_HALL: return path+b+"VDubDiningHall.mp3";
		case VDUB_LOBBY: return path+b+"VDubLobby.mp3";
		case SPIRITUS: return path+b+"Spiritus.mp3";
		case DIG: return path+f+"dig.mp3";
		case FLY: return path+f+"fly.mp3";
		case TELEPORT_BACK: return path+f+"teleportBack.mp3";
		case TELEPORT_AWAY: return path+f+"teleportAway.mp3";
		case DOOR: return path+f+"door.mp3";
		case CIT: return path+b+"CIT.mp3";
		case BH_LIGHT: return path+b+"BHLight.mp3";
		case BH_DARK: return path+b+"BHDark.mp3";
		case ELECTRODE: return path+f+"electrode.mp3";
		case UNLOCK: return path+f+"unlock.mp3";
		case LAB: return path+b+"portal.mp3";
		case SWITCH: return path+f+"Switch.mp3";
		case SCILI: return path+b+"Lavender.mp3";
		case SCILI_FLOOR: return path+b+"Tower.mp3";
		case ALARM: return path+f+"alarm.mp3";
		case SCILI_ROOF: return path+b+"Roof.mp3";
		case BATTLE_CRY: return path+p+dex+".mp3";
		case PRINCE: return path+b+"princeLab.mp3";
		case FISHCO: return path+b+"FishCo.mp3";
		case OUTSIDE_FISHCO: return path+b+"OutsideFishCo.mp3";
		case SRI: return path+b+"SriBattle.mp3";
		case MIKE: return path+b+"MikeBattle.mp3";
		case DAVID: return path+b+"DavidBattle.mp3";
		case MAT: return path+b+"MatBattle.mp3";
		case CHAMPION: return path+b+"Champion.mp3";
		case POKEFLUTE: return path+f+"pokeflute.mp3";
		case GYM_BATTLE: return path+b+"GymLeaderBattle.mp3"; //just to test
		case SCILI_STACKS: return path+b+"SciliTower.mp3";
		case MAGMAR: return path+p+"126.mp3";
		case WATERFIRE_NIGHT: return path+b+"WaterfireEdit.mp3";
		case WATERFIRE_DAY: return path+b+"WaterfireDay.mp3";
		case MAFIA_BASEMENT: return path+b+"MafiaBasement.mp3";
		case BATTLE_VICTORY: return path+b+"BattleVictory.mp3";
		case SILENCE: return path+b+"Silence.mp3";
		case RUTH_GYM: return path+b+"RuthGym.mp3";
		case URSARING: return path+p+"217.mp3";
		case SNORLAX: return path+p+"143.mp3";
		case HITMONLEE: return path+p+"106.mp3";
		case HITMONCHAN: return path+p+"107.mp3";
		case CREDITS: return path+b+"credits.mp3";
		case QUIET_GREEN: return path+b+"quietGreen.mp3";
		case CARRIE: return path+b+"Carrie.mp3";
		case SLATER: return path+b+"Slater.mp3";
		case VIA_VIA: return path+b+"ViaVia.mp3";
		case VIAVIA_LOBBY: return path+b+"Americano.mp3";
		case GRADUATION: return path+b+"Graduation.mp3";
		case SPG: return path+b+"SPG.mp3";
		case JOS: return path+b+"Jos.mp3";
		case THAYER: return path+b+"Thayer.mp3";
		case WATSON: return path+b+"Watson.mp3";
		case PSYDUCK: return path+p+"54.mp3";
		case SEADRA: return path+p+"117.mp3";
		case CHANSEY: return path+p+"113.mp3";
		case JIGGLYPUFF: return path+p+"39.mp3";
		case LAPRAS: return path+p+"131.mp3";
		case MR_MIME: return path+p+"122.mp3";
		case SANDSHREW: return path+p+"27.mp3";
		case MOTOROLA: return path+b+"SaxGuy.mp3";
		case UNIVERSITY_HALL: return path+b+"UnivHall.mp3";
		case BAMBOO_GARDEN: return path+b+"BambooGarden.mp3";
		case GAMBINO_BATTLE: return path+b+"Gambino.mp3";
		case GLITCH_MOB_BATTLE: return path+b+"Glitch Mob.mp3";
		case SPRING_WEEKEND: return path+b+"Spring Weekend.mp3";
		default: return path+b+"pallet-town.mp3";
		}
	}
	
	public static M2 getCurrentSong(){
		return current;
	}
	public static M2 getCurrentFX(){
		return currentFX;
	}
	
	public static void playBG(final M2 song){
		current = song;
		
		if(fxPlayer != null){
			fxPlayer.end();
		}
		if(getBgPlayer() != null){
			getBgPlayer().end();
		}
		
		new Thread(){
			public void run(){
				try {
					InputStream is = this.getClass().getResourceAsStream(song.getFileName());
					setBgPlayer(new PokemonPlayer(is, song.getFileName(), true));
					getBgPlayer().play();
				} catch (JavaLayerException e) {
				}				
			}
		}.start();
	}
	
	public static void playFX(final M2 sound){
		currentFX = sound;
		
		if(fxPlayer != null){
			fxPlayer.end();
		}
		if(getBgPlayer() != null){
			getBgPlayer().pause();
		}
		
		final Thread t = new Thread(){
			public void run(){
				try{
					InputStream is = this.getClass().getResourceAsStream(sound.getFileName());
					fxPlayer = new PokemonPlayer(is, sound.getFileName(), false);
					fxPlayer.play();					
				}catch(JavaLayerException e){}
			}
		};
		t.start();
		
		new Thread(){
			public void run(){
				try {
					t.join();
				} catch (InterruptedException e) {}
				if(getBgPlayer()!= null)
					getBgPlayer().unpause();
			}
		}.start();

	}
	
	public static void playFXNoPause(final M2 sound){
		currentFX = sound;
		
		if(fxPlayer != null){
			fxPlayer.end();
		}
		
		final Thread t = new Thread(){
			public void run(){
				try{
					InputStream is = this.getClass().getResourceAsStream(sound.getFileName());
					fxPlayer = new PokemonPlayer(is, sound.getFileName(), false);
					fxPlayer.play();					
				}catch(JavaLayerException e){}
			}
		};
		t.start();
	}
	
	public static void interrupt(){
		if(fxPlayer != null){
			fxPlayer.end();
		}
		if(getBgPlayer() != null && getBgPlayer().isPaused()){
			getBgPlayer().unpause();
		}
	}
	
	public static PokemonPlayer getBgPlayer() {
		return bgPlayer;
	}

	public static void setBgPlayer(PokemonPlayer bgPlayer) {
		M2.bgPlayer = bgPlayer;
	}

	public static class PokemonPlayer extends MusicPlayer2{
		private boolean paused = false, loop = false;
		private String streamName = "";
		public PokemonPlayer(InputStream stream, String s, boolean l) throws JavaLayerException {
			super(stream);
			streamName = s;
			loop = l;
		}
		
		public void pause(){
			paused = true;
		}
		
		public void unpause(){
			paused = false;
		}
		
		public boolean isPaused(){
			return paused;
		}
		
		public void end(){
			loop = false;
			ret = false;
			super.close();
		}
		
		public boolean play(int frames) throws JavaLayerException{
			while(frames-- > 0 && ret){
				if(!paused){
					ret = this.decodeFrame();
				}
				else{
					frames++;
				}
			}
			
			if(!ret){
				super.play(0);
			}
			
			if(this.isComplete() && loop){
				new Thread(){
					public void run(){
						try{
							InputStream is = this.getClass().getResourceAsStream(streamName);
							setBgPlayer(new PokemonPlayer(is, streamName, true));
							getBgPlayer().play();
						}catch(JavaLayerException e){}
					}
				}.start();
			}
			
			return ret;
		}
	}
}

