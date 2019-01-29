package PokemonDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class ViaVia extends PokePanel2 {
	private final int RUTH=32, DON=31;
	private Trainer ruth;
	
	
	public ViaVia(GameBoyScreen gbs){
		super(gbs);
		
		this.initializeEventVector(33);
		
		this.createBaseRoom();
		
		_xSpace = -29;
		_ySpace = 0;
		_xIndex = 3;
		_yIndex = 4;
	
	}

	public ViaVia(GameBoyScreen gbs, int xSpace, int ySpace, int xInd, int yInd, int direction){
		super(gbs, xSpace, ySpace, xInd, yInd, direction);
		
		this.initializeEventVector(33);
		
		this.createBaseRoom();
		
	}
	
	public int checkRegion(){
		if((_xIndex<=10)){
			return 1;
		};
		if(((_xIndex>=10)&&(_xIndex<=22)) && (_yIndex>9)){
			return 2;
		};
		if(((_xIndex>=20)&&(_xIndex<=29)) && ((_yIndex>=0)&&(_yIndex<=9))){
			return 3;
		};
		return 1;
	};
	
	
	public void addTrainers(){
			this._movingTrainers = new Vector<Trainer>();
			try {

				//4,10    9,10
				//9,11   4,11
				Vector<Pokemon> belt1 = new Vector<Pokemon>();
				belt1.add(new Pokemon.Mr_Mime().setWildLevel(26));
				Trainer thug1 = new Trainer.MafiaGreen(belt1);
				thug1.createHome(4,10);
				thug1.setLOS(2);
				thug1.setStationary(false);
				thug1.setPause(false);
				thug1.addDest(9,10);
				thug1.addDest(4,10);
				thug1.setFirstDest();
				thug1.getDialogue().add("Halt! No Entry!");
				thug1.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug1.setMoney(1000);
				thug1.setVanishing(true);
				thug1.setType("Mafia");
				thug1.setName("Thug");
				this.getMovingTrainers().add(thug1);
				
				Vector<Pokemon> belt2 = new Vector<Pokemon>();
				belt2.add(new Pokemon.Sandslash().setWildLevel(26));
				belt2.add(new Pokemon.Marowak().setWildLevel(26));
				Trainer thug2 = new Trainer.MafiaMagenta(belt2);
				thug2.createHome(9,11);
				thug2.setLOS(2);
				thug2.setStationary(false);
				thug2.setPause(false);
				thug2.addDest(4,11);
				thug2.addDest(9,11);
				thug2.setFirstDest();
				thug2.getDialogue().add("Halt! No Entry!");
				thug2.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug2.setMoney(1000);
				thug2.setVanishing(true);
				thug2.setType("Mafia");
				thug2.setName("Thug");
				this.getMovingTrainers().add(thug2);
				
				
				
				//14,11  14,14
				//15,14  15,11
				//16,11  16,14
				//17,14  17,11
				//18,11  18,14
				//19,14  19,11
				//20,11  20,14
				//21,14  21,11
				Vector<Pokemon> belt3 = new Vector<Pokemon>();
				belt3.add(new Pokemon.Kadabra().setWildLevel(27));
				belt3.add(new Pokemon.Weezing().setWildLevel(27));
				Trainer thug3 = new Trainer.MafiaWhite(belt3);
				thug3.createHome(14,11);
				thug3.setLOS(2);
				thug3.setStationary(false);
				thug3.setPause(false);
				thug3.addDest(14,14);
				thug3.addDest(14,11);
				thug3.setFirstDest();
				thug3.getDialogue().add("Halt! No Entry!");
				thug3.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug3.setMoney(1000);
				thug3.setVanishing(true);
				thug3.setType("Mafia");
				thug3.setName("Thug");
				this.getMovingTrainers().add(thug3);

				
				Vector<Pokemon> belt4 = new Vector<Pokemon>();
				belt4.add(new Pokemon.Voltorb().setWildLevel(26));
				belt4.add(new Pokemon.Electabuzz().setWildLevel(26));
				belt4.add(new Pokemon.Pikachu().setWildLevel(26));
				Trainer thug4 = new Trainer.MafiaMagenta(belt4);
				thug4.createHome(15,14);
				thug4.setLOS(2);
				thug4.setStationary(false);
				thug4.setPause(false);
				thug4.addDest(15,11);
				thug4.addDest(15,14);
				thug4.setFirstDest();
				thug4.getDialogue().add("Halt! No Entry!");
				thug4.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug4.setMoney(1000);
				thug4.setVanishing(true);
				thug4.setType("Mafia");
				thug4.setName("Thug");
				this.getMovingTrainers().add(thug4);
				
				Vector<Pokemon> belt5 = new Vector<Pokemon>();
				belt5.add(new Pokemon.Zubat().setWildLevel(28));
				Trainer thug5 = new Trainer.MafiaGreen(belt5);
				thug5.createHome(16,11);
				thug5.setLOS(2);
				thug5.setStationary(false);
				thug5.setPause(false);
				thug5.addDest(16,14);
				thug5.addDest(16,11);
				thug5.setFirstDest();
				thug5.getDialogue().add("Halt! No Entry!");
				thug5.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug5.setMoney(1000);
				thug5.setVanishing(true);
				thug5.setType("Mafia");
				thug5.setName("Thug");
				this.getMovingTrainers().add(thug5);
				
				Vector<Pokemon> belt6 = new Vector<Pokemon>();
				belt6.add(new Pokemon.Magnemite().setWildLevel(26));
				belt6.add(new Pokemon.Arbok().setWildLevel(26));
				Trainer thug6 = new Trainer.MafiaMagenta(belt6);
				thug6.createHome(17,14);
				thug6.setLOS(2);
				thug6.setStationary(false);
				thug6.setPause(false);
				thug6.addDest(17,11);
				thug6.addDest(17,14);
				thug6.setFirstDest();
				thug6.getDialogue().add("Halt! No Entry!");
				thug6.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug6.setMoney(1000);
				thug6.setVanishing(true);
				thug6.setType("Mafia");
				thug6.setName("Thug");
				this.getMovingTrainers().add(thug6);
				
				Vector<Pokemon> belt7 = new Vector<Pokemon>();
				belt7.add(new Pokemon.Muk().setWildLevel(29));
				belt7.add(new Pokemon.Electrode().setWildLevel(29));
				Trainer thug7 = new Trainer.MafiaGreen(belt7);
				thug7.createHome(18,11);
				thug7.setLOS(2);
				thug7.setStationary(false);
				thug7.setPause(false);
				thug7.addDest(18,14);
				thug7.addDest(18,11);
				thug7.setFirstDest();
				thug7.getDialogue().add("Halt! No Entry!");
				thug7.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug7.setMoney(1000);
				thug7.setVanishing(true);
				thug7.setType("Mafia");
				thug7.setName("Thug");
				this.getMovingTrainers().add(thug7);
				
				Vector<Pokemon> belt8 = new Vector<Pokemon>();
				belt8.add(new Pokemon.Cloyster().setWildLevel(29));
				belt8.add(new Pokemon.Cubone().setWildLevel(29));
				Trainer thug8 = new Trainer.MafiaWhite(belt8);
				thug8.createHome(19,14);
				thug8.setLOS(2);
				thug8.setStationary(false);
				thug8.setPause(false);
				thug8.addDest(19,11);
				thug8.addDest(19,14);
				thug8.setFirstDest();
				thug8.getDialogue().add("Halt! No Entry!");
				thug8.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug8.setMoney(1000);
				thug8.setVanishing(true);
				thug8.setType("Mafia");
				thug8.setName("Thug");
				this.getMovingTrainers().add(thug8);
				
				Vector<Pokemon> belt9 = new Vector<Pokemon>();
				belt9.add(new Pokemon.Raticate().setWildLevel(26));
				belt9.add(new Pokemon.Golbat().setWildLevel(26));
				belt9.add(new Pokemon.Arbok().setWildLevel(26));
				Trainer thug9 = new Trainer.MafiaMagenta(belt9);
				thug9.createHome(20,11);
				thug9.setLOS(2);
				thug9.setStationary(false);
				thug9.setPause(false);
				thug9.addDest(20,14);
				thug9.addDest(20,11);
				thug9.setFirstDest();
				thug9.getDialogue().add("Halt! No Entry!");
				thug9.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug9.setMoney(1000);
				thug9.setVanishing(true);
				thug9.setType("Mafia");
				thug9.setName("Thug");
				this.getMovingTrainers().add(thug9);
				
				Vector<Pokemon> belt10 = new Vector<Pokemon>();
				belt10.add(new Pokemon.Grimer().setWildLevel(28));
				belt10.add(new Pokemon.Electrode().setWildLevel(28));
				Trainer thug10 = new Trainer.MafiaGreen(belt10);
				thug10.createHome(21,14);
				thug10.setLOS(2);
				thug10.setStationary(false);
				thug10.setPause(false);
				thug10.addDest(21,11);
				thug10.addDest(21,14);
				thug10.setFirstDest();
				thug10.getDialogue().add("Halt! No Entry!");
				thug10.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug10.setMoney(1000);
				thug10.setVanishing(true);
				thug10.setType("Mafia");
				thug10.setName("Thug");
				this.getMovingTrainers().add(thug10);
				
				//21,1  21,3  21,5  21,7   (first dest 21,8)
				//23,8 25,8  (first dest 27,8)
				//27,3  27,5  27,7   (first dest 27,1)
				//27,1 23,1 25,1   (first dest 21,1)				
				
				Vector<Pokemon> belt11 = new Vector<Pokemon>();
				belt11.add(new Pokemon.Drowzee().setWildLevel(26));
				belt11.add(new Pokemon.Grimer().setWildLevel(26));
				belt11.add(new Pokemon.Machop().setWildLevel(26));
				Trainer thug11 = new Trainer.MafiaMagenta(belt11);
				thug11.createHome(21,1);
				thug11.setLOS(2);
				thug11.setStationary(false);
				thug11.setPause(false);
				thug11.addDest(21, 8);
				thug11.addDest(27, 8);
				thug11.addDest(27, 1);
				thug11.addDest(21, 1);
				thug11.setFirstDest();
				thug11.getDialogue().add("Halt! No Entry!");
				thug11.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug11.setMoney(1100);
				thug11.setVanishing(true);
				thug11.setType("Mafia");
				thug11.setName("Thug");
				this.getMovingTrainers().add(thug11);
				
				Vector<Pokemon> belt12 = new Vector<Pokemon>();
				belt12.add(new Pokemon.Tauros().setWildLevel(30));
				Trainer thug12 = new Trainer.MafiaWhite(belt12);
				thug12.createHome(21,3);
				thug12.setLOS(2);
				thug12.setStationary(false);
				thug12.setPause(false);
				thug12.addDest(21, 8);
				thug12.addDest(27, 8);
				thug12.addDest(27, 1);
				thug12.addDest(21, 1);
				thug12.setFirstDest();
				thug12.getDialogue().add("Halt! No Entry!");
				thug12.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug12.setMoney(1200);
				thug12.setVanishing(true);
				thug12.setType("Mafia");
				thug12.setName("Thug");
				this.getMovingTrainers().add(thug12);

				Vector<Pokemon> belt13 = new Vector<Pokemon>();
				belt13.add(new Pokemon.Diglett().setWildLevel(24));
				belt13.add(new Pokemon.Diglett().setWildLevel(25));
				belt13.add(new Pokemon.Diglett().setWildLevel(26));
				belt13.add(new Pokemon.Diglett().setWildLevel(27));
				belt13.add(new Pokemon.Diglett().setWildLevel(28));
				Trainer thug13 = new Trainer.MafiaMagenta(belt13);
				thug13.createHome(21,5);
				thug13.setLOS(2);
				thug13.setStationary(false);
				thug13.setPause(false);
				thug13.addDest(21, 8);
				thug13.addDest(27, 8);
				thug13.addDest(27, 1);
				thug13.addDest(21, 1);
				thug13.setFirstDest();
				thug13.getDialogue().add("Halt! No Entry!");
				thug13.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug13.setMoney(1300);
				thug13.setVanishing(true);
				thug13.setType("Mafia");
				thug13.setName("Thug");
				this.getMovingTrainers().add(thug13);

				Vector<Pokemon> belt14 = new Vector<Pokemon>();
				belt14.add(new Pokemon.Magneton().setWildLevel(27));
				belt14.add(new Pokemon.Kadabra().setWildLevel(27));
				Trainer thug14 = new Trainer.MafiaGreen(belt14);
				thug14.createHome(21,7);
				thug14.setLOS(2);
				thug14.setStationary(false);
				thug14.setPause(false);
				thug14.addDest(21, 8);
				thug14.addDest(27, 8);
				thug14.addDest(27, 1);
				thug14.addDest(21, 1);
				thug14.setFirstDest();
				thug14.getDialogue().add("Halt! No Entry!");
				thug14.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug14.setMoney(1400);
				thug14.setVanishing(true);
				thug14.setType("Mafia");
				thug14.setName("Thug");
				this.getMovingTrainers().add(thug14);
				
				Vector<Pokemon> belt15 = new Vector<Pokemon>();
				belt15.add(new Pokemon.Nidoqueen().setWildLevel(31));
				Trainer thug15 = new Trainer.MafiaWhite(belt15);
				thug15.createHome(23,8);
				thug15.setLOS(2);
				thug15.setStationary(false);
				thug15.setPause(false);
				thug15.addDest(27, 8);
				thug15.addDest(27, 1);
				thug15.addDest(21, 1);
				thug15.addDest(21, 8);
				thug15.setFirstDest();
				thug15.getDialogue().add("Halt! No Entry!");
				thug15.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug15.setMoney(1500);
				thug15.setVanishing(true);
				thug15.setType("Mafia");
				thug15.setName("Thug");
				this.getMovingTrainers().add(thug15);
				
				Vector<Pokemon> belt16 = new Vector<Pokemon>();
				belt16.add(new Pokemon.Persian().setWildLevel(26));
				belt16.add(new Pokemon.Persian().setWildLevel(26));
				Trainer thug16 = new Trainer.MafiaMagenta(belt16);
				thug16.createHome(25,8);
				thug16.setLOS(2);
				thug16.setStationary(false);
				thug16.setPause(false);
				thug16.addDest(27, 8);
				thug16.addDest(27, 1);
				thug16.addDest(21, 1);
				thug16.addDest(21, 8);
				thug16.setFirstDest();
				thug16.getDialogue().add("Halt! No Entry!");
				thug16.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug16.setMoney(1600);
				thug16.setVanishing(true);
				thug16.setType("Mafia");
				thug16.setName("Thug");
				this.getMovingTrainers().add(thug16);
				
				Vector<Pokemon> belt17 = new Vector<Pokemon>();
				belt17.add(new Pokemon.Rhydon().setWildLevel(25));
				belt17.add(new Pokemon.Rhyhorn().setWildLevel(30));
				Trainer thug17 = new Trainer.MafiaWhite(belt17);
				thug17.createHome(27,3);
				thug17.setLOS(2);
				thug17.setStationary(false);
				thug17.setPause(false);
				thug17.addDest(27, 1);
				thug17.addDest(21, 1);
				thug17.addDest(21, 8);
				thug17.addDest(27, 8);
				thug17.setFirstDest();
				thug17.getDialogue().add("Halt! No Entry!");
				thug17.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug17.setMoney(1700);
				thug17.setVanishing(true);
				thug17.setType("Mafia");
				thug17.setName("Thug");
				this.getMovingTrainers().add(thug17);
				
				Vector<Pokemon> belt18 = new Vector<Pokemon>();
				belt18.add(new Pokemon.Kangaskhan().setWildLevel(31));
				Trainer thug18 = new Trainer.MafiaWhite(belt18);
				thug18.createHome(27,5);
				thug18.setLOS(2);
				thug18.setStationary(false);
				thug18.setPause(false);
				thug18.addDest(27, 1);
				thug18.addDest(21, 1);
				thug18.addDest(21, 8);
				thug18.addDest(27, 8);
				thug18.setFirstDest();
				thug18.getDialogue().add("Halt! No Entry!");
				thug18.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug18.setMoney(1800);
				thug18.setVanishing(true);
				thug18.setType("Mafia");
				thug18.setName("Thug");
				this.getMovingTrainers().add(thug18);
				
				Vector<Pokemon> belt19 = new Vector<Pokemon>();
				belt19.add(new Pokemon.Nidorina().setWildLevel(26));
				belt19.add(new Pokemon.Nidorino().setWildLevel(26));
				Trainer thug19 = new Trainer.MafiaMagenta(belt19);
				thug19.createHome(27,7);
				thug19.setLOS(2);
				thug19.setStationary(false);
				thug19.setPause(false);
				thug19.addDest(27, 1);
				thug19.addDest(21, 1);
				thug19.addDest(21, 8);
				thug19.addDest(27, 8);
				thug19.setFirstDest();
				thug19.getDialogue().add("Halt! No Entry!");
				thug19.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug19.setMoney(1900);
				thug19.setVanishing(true);
				thug19.setType("Mafia");
				thug19.setName("Thug");
				this.getMovingTrainers().add(thug19);
				
				Vector<Pokemon> belt20 = new Vector<Pokemon>();
				belt20.add(new Pokemon.Machoke().setWildLevel(29));
				belt20.add(new Pokemon.Primeape().setWildLevel(30));
				Trainer thug20 = new Trainer.MafiaGreen(belt20);
				thug20.createHome(27,1);
				thug20.setLOS(2);
				thug20.setStationary(false);
				thug20.setPause(false);
				thug20.addDest(21, 1);
				thug20.addDest(21, 8);
				thug20.addDest(27, 8);
				thug20.addDest(27, 1);
				thug20.setFirstDest();
				thug20.getDialogue().add("Halt! No Entry!");
				thug20.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug20.setMoney(2000);
				thug20.setVanishing(true);
				thug20.setType("Mafia");
				thug20.setName("Thug");
				this.getMovingTrainers().add(thug20);
				
				Vector<Pokemon> belt21 = new Vector<Pokemon>();
				belt21.add(new Pokemon.Meowth().setWildLevel(30));
				belt21.add(new Pokemon.Persian().setWildLevel(30));
				Trainer thug21 = new Trainer.MafiaWhite(belt21);
				thug21.createHome(25,1);
				thug21.setLOS(2);
				thug21.setStationary(false);
				thug21.setPause(false);
				thug21.addDest(21, 1);
				thug21.addDest(21, 8);
				thug21.addDest(27, 8);
				thug21.addDest(27, 1);
				thug21.setFirstDest();
				thug21.getDialogue().add("Halt! No Entry!");
				thug21.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug21.setMoney(2100);
				thug21.setVanishing(true);
				thug21.setType("Mafia");
				thug21.setName("Thug");
				this.getMovingTrainers().add(thug21);
				
				Vector<Pokemon> belt22 = new Vector<Pokemon>();
				belt22.add(new Pokemon.Electrode().setWildLevel(26));
				belt22.add(new Pokemon.Voltorb().setWildLevel(26));
				belt22.add(new Pokemon.Electrode().setWildLevel(26));
				Trainer thug22 = new Trainer.MafiaMagenta(belt22);
				thug22.createHome(23,1);
				thug22.setLOS(2);
				thug22.setStationary(false);
				thug22.setPause(false);
				thug22.addDest(21, 1);
				thug22.addDest(21, 8);
				thug22.addDest(27, 8);
				thug22.addDest(27, 1);
				thug22.setFirstDest();
				thug22.getDialogue().add("Halt! No Entry!");
				thug22.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug22.setMoney(2200);
				thug22.setVanishing(true);
				thug22.setType("Mafia");
				thug22.setName("Thug");
				this.getMovingTrainers().add(thug22);
				
				//22,7 24,7  (first dest 26,7)
				//26,7 26,5 26,3  (first dest 26,2)
				//23,2 25,2  (first dest, 22,2 )				
				//22,5 22,3  (first dest 22,7)
				
				Vector<Pokemon> belt23 = new Vector<Pokemon>();
				belt23.add(new Pokemon.Muk().setWildLevel(32));
				Trainer thug23 = new Trainer.MafiaMagenta(belt23);
				thug23.createHome(22,7);
				thug23.setLOS(2);
				thug23.setStationary(false);
				thug23.setPause(false);
				thug23.addDest(26,7);
				thug23.addDest(26,2);
				thug23.addDest(22,2);
				thug23.addDest(22,7);
				thug23.setFirstDest();
				thug23.getDialogue().add("Halt! No Entry!");
				thug23.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug23.setMoney(2200);
				thug23.setVanishing(true);
				thug23.setType("Mafia");
				thug23.setName("Thug");
				this.getMovingTrainers().add(thug23);
				
				Vector<Pokemon> belt24 = new Vector<Pokemon>();
				belt24.add(new Pokemon.Raticate().setWildLevel(27));
				belt24.add(new Pokemon.Golbat().setWildLevel(29));
				Trainer thug24 = new Trainer.MafiaWhite(belt24);
				thug24.createHome(24,7);
				thug24.setLOS(2);
				thug24.setStationary(false);
				thug24.setPause(false);
				thug24.addDest(26,7);
				thug24.addDest(26,2);
				thug24.addDest(22,2);
				thug24.addDest(22,7);
				thug24.setFirstDest();
				thug24.getDialogue().add("Halt! No Entry!");
				thug24.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug24.setMoney(2200);
				thug24.setVanishing(true);
				thug24.setType("Mafia");
				thug24.setName("Thug");
				this.getMovingTrainers().add(thug24);
				
				Vector<Pokemon> belt25 = new Vector<Pokemon>();
				belt25.add(new Pokemon.Koffing().setWildLevel(26));
				belt25.add(new Pokemon.Koffing().setWildLevel(26));
				belt25.add(new Pokemon.Weezing().setWildLevel(29));
				Trainer thug25 = new Trainer.MafiaWhite(belt25);
				thug25.createHome(26,7);
				thug25.setLOS(2);
				thug25.setStationary(false);
				thug25.setPause(false);
				thug25.addDest(26,2);
				thug25.addDest(22,2);
				thug25.addDest(22,7);
				thug25.addDest(26,7);
				thug25.setFirstDest();
				thug25.getDialogue().add("Halt! No Entry!");
				thug25.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug25.setMoney(2200);
				thug25.setVanishing(true);
				thug25.setType("Mafia");
				thug25.setName("Thug");
				this.getMovingTrainers().add(thug25);
				
				Vector<Pokemon> belt26 = new Vector<Pokemon>();
				belt26.add(new Pokemon.Ditto().setWildLevel(28));
				belt26.add(new Pokemon.Ditto().setWildLevel(28));
				Trainer thug26 = new Trainer.MafiaGreen(belt26);
				thug26.createHome(26,5);
				thug26.setLOS(2);
				thug26.setStationary(false);
				thug26.setPause(false);
				thug26.addDest(26,2);
				thug26.addDest(22,2);
				thug26.addDest(22,7);
				thug26.addDest(26,7);
				thug26.setFirstDest();
				thug26.getDialogue().add("Halt! No Entry!");
				thug26.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug26.setMoney(2200);
				thug26.setVanishing(true);
				thug26.setType("Mafia");
				thug26.setName("Thug");
				this.getMovingTrainers().add(thug26);
				
				Vector<Pokemon> belt27 = new Vector<Pokemon>();
				belt27.add(new Pokemon.Beedrill().setWildLevel(26));
				belt27.add(new Pokemon.Haunter().setWildLevel(26));
				Trainer thug27 = new Trainer.MafiaWhite(belt27);
				thug27.createHome(26,3);
				thug27.setLOS(2);
				thug27.setStationary(false);
				thug27.setPause(false);
				thug27.addDest(26,2);
				thug27.addDest(22,2);
				thug27.addDest(22,7);
				thug27.addDest(26,7);
				thug27.setFirstDest();
				thug27.getDialogue().add("Halt! No Entry!");
				thug27.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug27.setMoney(2200);
				thug27.setVanishing(true);
				thug27.setType("Mafia");
				thug27.setName("Thug");
				this.getMovingTrainers().add(thug27);
				
				Vector<Pokemon> belt28 = new Vector<Pokemon>();
				belt28.add(new Pokemon.Hitmonlee().setWildLevel(29));
				belt28.add(new Pokemon.Hitmonchan().setWildLevel(29));
				Trainer thug28 = new Trainer.MafiaWhite(belt28);
				thug28.createHome(23,2);
				thug28.setLOS(2);
				thug28.setStationary(false);
				thug28.setPause(false);
				thug28.addDest(22,2);
				thug28.addDest(22,7);
				thug28.addDest(26,7);
				thug28.addDest(26,2);
				thug28.setFirstDest();
				thug28.getDialogue().add("Halt! No Entry!");
				thug28.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug28.setMoney(2200);
				thug28.setVanishing(true);
				thug28.setType("Mafia");
				thug28.setName("Thug");
				this.getMovingTrainers().add(thug28);
				
				Vector<Pokemon> belt29 = new Vector<Pokemon>();
				belt29.add(new Pokemon.Vulpix().setWildLevel(20));
				belt29.add(new Pokemon.Ninetales().setWildLevel(33));
				Trainer thug29 = new Trainer.MafiaMagenta(belt29);
				thug29.createHome(25,2);
				thug29.setLOS(2);
				thug29.setStationary(false);
				thug29.setPause(false);
				thug29.addDest(22,2);
				thug29.addDest(22,7);
				thug29.addDest(26,7);
				thug29.addDest(26,2);
				thug29.setFirstDest();
				thug29.getDialogue().add("Halt! No Entry!");
				thug29.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug29.setMoney(2200);
				thug29.setVanishing(true);
				thug29.setType("Mafia");
				thug29.setName("Thug");
				this.getMovingTrainers().add(thug29);
				
				Vector<Pokemon> belt30 = new Vector<Pokemon>();
				belt30.add(new Pokemon.Rhyhorn().setWildLevel(28));
				belt30.add(new Pokemon.Onix().setWildLevel(28));
				Trainer thug30 = new Trainer.MafiaWhite(belt30);
				thug30.createHome(22,5);
				thug30.setLOS(2);
				thug30.setStationary(false);
				thug30.setPause(false);
				thug30.addDest(22,7);
				thug30.addDest(26,7);
				thug30.addDest(26,2);
				thug30.addDest(22,2);
				thug30.setFirstDest();
				thug30.getDialogue().add("Halt! No Entry!");
				thug30.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug30.setMoney(2200);
				thug30.setVanishing(true);
				thug30.setType("Mafia");
				thug30.setName("Thug");
				this.getMovingTrainers().add(thug30);
				
				Vector<Pokemon> belt31 = new Vector<Pokemon>();
				belt31.add(new Pokemon.Fearow().setWildLevel(29));
				belt31.add(new Pokemon.Dodrio().setWildLevel(29));
				Trainer thug31 = new Trainer.MafiaMagenta(belt31);
				thug31.createHome(22,3);
				thug31.setLOS(2);
				thug31.setStationary(false);
				thug31.setPause(false);
				thug31.addDest(22,7);
				thug31.addDest(26,7);
				thug31.addDest(26,2);
				thug31.addDest(22,2);
				thug31.setFirstDest();
				thug31.getDialogue().add("Halt! No Entry!");
				thug31.setDefeatDialogue("Wait 'til the boss hears about this...");
				thug31.setMoney(2200);
				thug31.setVanishing(true);
				thug31.setType("Mafia");
				thug31.setName("Thug");
				this.getMovingTrainers().add(thug31);
				
		
			Vector<Pokemon> donBelt = new Vector<Pokemon>();
			donBelt.add(new Pokemon.Nidoking().setWildLevel(34));
			donBelt.add(new Pokemon.Golem().setWildLevel(31));
			donBelt.add(new Pokemon.Blastoise().setWildLevel(31));
			donBelt.add(new Pokemon.Dugtrio().setWildLevel(31));
			donBelt.add(new Pokemon.Nidoqueen().setWildLevel(34));
			Trainer don = new Trainer.MafiaDon(donBelt);
			don.createHome(24,5,0,0,"freeze","freeze");
			don.setDirectionAndImage(FACENORTH);
			don.getDialogue().add("How cute. Bursting into the HQ to save your president.");
			don.getDialogue().add("Let me remind you " + _gbs.getPlayer().getPlayerName()+", that this is the work of adults,");
			don.getDialogue().add("and you would do well to exit the premises.");
			don.getDialogue().add("We're collecting all the rare PokeBalls of the world...");
			don.getDialogue().add("...with which we'll capture the six legendaries,");
			don.getDialogue().add("and dominate the surrounding nations.");
			don.getDialogue().add("I'll warn you one more time. Get out of my area code.");
			don.getDialogue().add("You'll never stop us. Because remember:");
			don.getDialogue().add("Not only is it difficult to catch Don...");
			don.setDefeatDialogue("I'll get you. I have people everywhere...");
			don.setMoney(4059);
			don.setVanishing(true);
			don.setType("Mafia");
			don.setName("Don");
			this.getMovingTrainers().add(don); //31
		
		ruth = new Trainer.Ruth(null);
		ruth.createHome(24,4,0,0,"freeze","freeze");
		//Her dialogue etc is entirely set inside of loadallevents
		this.getMovingTrainers().add(ruth);  //32
				
		
		//MASTERBALL
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void scanForAllEvents(){
		this.standardTrainerScan(0, 32);
		
		Trainer ruth=this.getMovingTrainers().get(RUTH);
		Trainer don=this.getMovingTrainers().get(DON);
		
		if(don.isDefeated()){
			for(int j=0; j<32;j++){
				this.getMovingTrainers().get(j).setVanishing(true);
				this.getMovingTrainers().get(j).defeat();
				this.getCheckList().set(j,1);
			}
		}
		
		if(ruth.isDefeated()){
			this.getCheckList().set(32, 1);
		}
		else{
			this.getCheckList().set(32, 0);
		}
	};
	public void loadAllEvents(){
		this.standardTrainerLoad(1, 32);
		
		Trainer ruth=this.getMovingTrainers().get(RUTH);
		if(this.getCheckList()!=null){
			if(this.getCheckList().get(DON)==0){
				ruth.getDialogue().add(_gbs.getPlayer().getPlayerName()+", help!");
				ruth.getDialogue().add("Please defeat these goons and get me out of here!");
			}
			else if(this.getCheckList().get(DON)==1){
				ruth.getDialogue().add("Goodness, thank you! I was afraid no one would find me.");
				ruth.getDialogue().add("Those mafia were after my rare Pokemon commodities,");
				ruth.getDialogue().add("so they took me hostage during my open office hours.");
				ruth.getDialogue().add("In particular, they were after this:");
				try {
					ruth.setGift(new Item.MasterBall());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ruth.getPostItemDialogue().add("I know that it's safer in your hands than mine.");
				ruth.getPostItemDialogue().add("It catches any Pokemon without fail. Use it well!");
				ruth.getPostItemDialogue().add(_gbs.getPlayer().getPlayerName()+", you're a senior now, right?");
				ruth.getPostItemDialogue().add("It's almost time for graduation. How do you feel?");
				ruth.getPostItemDialogue().add("I'll be waiting in my office for your final gym battle.");
			}
			else if(this.getCheckList().get(DON)==1 && this.getCheckList().get(RUTH)==1){
				ruth.setVanishing(true);
				ruth.defeat();
			}
		}
	};
	
	public void createBaseRoom(){
		
		this.song = M2.VIA_VIA;
		
		this.addTrainers();
		this.loadAllEvents();
		
		this.xConstant=12;
		this.yConstant=1;
		
		
		//UPDATE THIS FOR EVERY NEW ROOM. Take the time to do it now.
		this._mapX=135;
		this._mapY=235;

		this.setBikeAllow(false);
		this.setBattleBG(NewBattleScreen.VIAVIA);
		_gbs.setNextMoving(_gbs.getCurrentPanel()._movingTrainers);
		
		this.setBackground(Color.BLACK);
		this.description = "Mafia HQ";
		this._roomNum = _gbs.VIA_VIA;
		try{
			if(GameBoyScreen.finishedLoading){
				_gbs.BACKGROUND = ImageIO.read(this.getClass().getResource("/PokemonFiles/ViaVia/ViaVia Background.png"));
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(0);
		}
		this.createGrid();
	}

	public void afterBattle(){
		super.afterBattle();
		
		if(this._interruptedTrainer!=DON){
			switch(this.checkRegion()){
				case 1: this.stationaryEnterRoom(this._roomNum, 2, 12, FACEEAST);
					break;
				case 2: this.stationaryEnterRoom(this._roomNum, 12, 12, FACEEAST);
					break;
				case 3: this.stationaryEnterRoom(this._roomNum, 24, 10, FACENORTH);
					break;
				default: this.stationaryEnterRoom(this._roomNum, 2, 12, FACENORTH);
			}
		}
	}
	
	public void createGrid(){
		this._room = new Room(29,16);
		Scanner scan = new Scanner(this.getClass().getResourceAsStream("/PokemonMaps/ViaVia.cmap"));
		for(int i = 0; i < 16; i++){
			for(int i2 = 0; i2 < 29; i2++){
				this._room._roomGrid[i2][i] = scan.next().charAt(0);
			}
		}
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawOptimalImage(g2, _gbs.BACKGROUND);
		this.drawPlayer(g2);
		this.drawAllTrainers(g2, this.xConstant, this.yConstant, this._movingTrainers);

		this.drawAllGenerics(g2);
	}
	
	public void enterRoom(int xInd, int yInd){
		//Lobby
 		if((xInd == 1) && (yInd == 9)){
 			super.enterRoom(_gbs.VIAVIA_LOBBY, 8, 2, FACESOUTH);
 		}
		
	}
	

	public void A_Button(){
		if(!_menuVisible && !this.textTimer.isRunning() && !this.facingWater()){
			if(!_busy){
				this.completionCheck = false;
			}
			
			if(this._interruptedTrainer==RUTH && !ruth.isDefeated() && ruth.getGift()==null && (_NPCpage==4)){
				super.A_Button();
//				_gbs.getPlayer().clearDestinations();
//				
//				ruth.setAvoidMethod("avoid");
//				ruth.setReturnMethod("avoid");
//				ruth.setInterrupted(false);
//				this._dialogueVisible=false;
//				this._NPCpage=0;
//				this._oneShot=true;
//				ruth.setVanishing(true);
//				ruth.clearDestinations();
//				ruth.addDest(24, 13);
//				ruth.setdefea
//				//ruth.addDest(15, 13);
//				ruth.setPause(false);
//				ruth.setFirstDest();
//				this._approachTimer.start();
				this.customTeleportTo(_gbs.ROUTE_3, 28, 9);
				return;
			}
			
			this.repaint();
		}

		super.A_Button();
	}
}