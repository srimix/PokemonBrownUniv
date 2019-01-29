package PokemonDemo;

import java.util.Random;

public class NewTurnAction {
	protected NewBattleScreen nbs;
	protected String line1, line2;
	protected final static int ASLEEP = 1, POISONED = 2, PARALYZED = 3,
			BURNED = 4, FROZEN = 5, TOXIC_POISON = 6;
	
	public static boolean isPoisoned = false;

	public NewTurnAction(NewBattleScreen nbs, String line1, String line2) {
		this.nbs = nbs;
		this.line1 = line1;
		this.line2 = line2;
	}

	public void setDialogue(String l1, String l2) {
		this.line1 = l1;
		this.line2 = l2;
	}

	public void setWrapDialogue(String desc) {
		int wrap = 35;
		String l1, l2;
		if (desc.length() <= wrap) {
			l1 = desc;
			l2 = "";
		} else {
			String firstHalf = desc.substring(0, wrap);
			int breakPoint = firstHalf.lastIndexOf(" ");
			l1 = desc.substring(0, breakPoint);
			l2 = desc.substring(breakPoint + 1);

		}
		this.line1 = l1;
		this.line2 = l2;
	}

	public void execute() {
		nbs.displayText(line1, line2);
		// System.out.println("execution of text");

	}

	// Will come in use for items and confusion.
	public static String Bernoulli(double Prob) {

		Random r = new Random();
		String result;

		int rand = r.nextInt(100)+1;
		if (rand < Prob) {
			result = "Yes";
		} else {
			result = "No";
		}
		return result;
	}

	// Comes in big use when asking questions, like "do you want to switch?"
	public String getLine1() {
		return line1;
	}

	public String getLine2() {
		return line2;
	}

	// ============================

	public static class TA_BasicAttack extends NewTurnAction {
		private Pokemon user, receiver;
		private Attack attackUsed;
		private NewBattleScreen _nbs;
		private String _self, _other;

		public TA_BasicAttack(NewBattleScreen nbs, Pokemon user,
				Pokemon receiver, String who, Attack attackUsed) {
			super(nbs, who + user.getName() + " used " + attackUsed.getName()
					+ "!", "");
			this.user = user;
			this.receiver = receiver;
			this.attackUsed = attackUsed;
			_nbs = nbs;
			_self = who;
			// Define the Other one
			if (_self == "") {
				_other = "Enemy ";
			} else {
				_other = "";
			}
		}

		public void execute() {
			/**
			 * TODO - If Pokemon is asleep or frozen, or paralyzed and can't move,
			 * break from this method and display appropriate effect.
			 * (Need to check right before Pokemon attacks)
			 */
			if(user == nbs.getAlly()){
				receiver = nbs.getEnemy();
				if(attackUsed.getAttackNum() == 19){
					SysOut.print("ALLY NO LONGER FLYING");
					nbs.setAllyFlying(false);
					nbs.setNextTurnAction(null);
				}
				if(attackUsed.getAttackNum() == 91){
					nbs.setAllyDug(false);
					nbs.setNextTurnAction(null);
				}
			}
			if(user == nbs.getEnemy()){
				receiver = nbs.getAlly();
				if(attackUsed.getAttackNum() == 19){
					nbs.setEnemyFlying(false);
					nbs.setNextTurnAction(null);
				}
				SysOut.print("enemy in execute NTA " + attackUsed.getName());
				if(attackUsed.getAttackNum() == 91){
					SysOut.print("ENEMY NO LONGER DUG");
					nbs.setEnemyDug(false);
					nbs.setNextTurnAction(null);
				}
			}
			
			if(user == nbs.getAlly() && nbs.getAlly().getStatus() > 0){
				int status = nbs.getAlly().getStatus();
				if(status == 1){
					NewTurnAction allyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(_nbs, user.getName() + " is fast asleep!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, allyStat);
					return;
				}
				else if(status == 5){
					NewTurnAction allyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(_nbs, user.getName() + " is frozen solid!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, allyStat);
					return;
				}
				else if(status == 3 && Math.random()*101 > 75){
					NewTurnAction allyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(_nbs, user.getName() + " is fully paralyzed!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, allyStat);
					return;
				}
			}
			if(user == nbs.getEnemy() && nbs.getEnemy().getStatus() > 0){
				int status = nbs.getEnemy().getStatus();
				if(status == 1){
					NewTurnAction eyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(_nbs, user.getName() + " is fast asleep!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, eyStat);
					return;
				}
				else if(status == 5){
					NewTurnAction eyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(_nbs, user.getName() + " is frozen solid!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, eyStat);
					return;	
				}
				else if(status == 3 && Math.random()*101 > 75){
					NewTurnAction eyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(_nbs, user.getName() + " is fully paralyzed!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, eyStat);
					NewTurnAction eyStat2 = new NewTurnAction.TA_DisplayAutoWrapEffect(_nbs, user.getName() + " has no somatosensation!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+2, eyStat2);
					return;
				}
			}
			
			attackUsed.setCurrentPP(attackUsed.getCurrentPP() - 1);

			
			if (user == _nbs.getEnemy()) {

				_nbs.setEnemyLastAttack(attackUsed);

				receiver = _nbs.getAlly();
				if (nbs.isEnemyDisabled()) {
					nbs.decrementEDR();
				}
				if (nbs.isEnemyLightScreened()) {
					nbs.decrementELS();
				}
				if (nbs.isEnemyReflected()) {
					nbs.decrementER();
				}
			} else if (user == _nbs.getAlly()) {

				_nbs.setAllyLastAttack(attackUsed);

				receiver = _nbs.getEnemy();
				if (nbs.isAllyDisabled()) {
					nbs.decrementADR();
				}
				if (nbs.isAllyLightScreened()) {
					nbs.decrementALS();
				}
				if (nbs.isAllyReflected()) {
					nbs.decrementAR();
				}
			}

			super.execute();
			
			
			if (Pokemon.hasNoEffect(attackUsed.getType(), receiver
					.getType1(), receiver.getType2())) {
				NewTurnAction nta = new TA_Effectiveness(nbs, 2);
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 1,
						nta);
				return;
			}
			
			/*
			if(user==nbs.getEnemy()){
				nbs.GB_User=1;
			}
			else{
				nbs.GB_User=0;
			}
			
			nbs.GBSelect=attackUsed.getAttackNum();
			nbs.GBTimer.start();
			*/
			
			// attackUsed.useAttack(user, receiver, null);
			// nbs.setHPListenerChange(receiver, -1*attackUsed.getDamage(user,
			// receiver));
			


			boolean miss = false;

			if (attackUsed.getAccuracy() <= 100) {

				int astage = 1;
				int estage = 1;
				if (user == nbs.getAlly()) {
					astage = nbs.allyAccStage;
					estage = nbs.enemyEvaStage;
				}
				if (user == nbs.getEnemy()) {
					astage = nbs.enemyAccStage;
					estage = nbs.allyEvaStage;
				}

				double netAccuracy = (user.getAccuracy()
						* nbs.getAccuracyMultiplier(astage)
						* nbs.getEvasionMultiplier(estage) * attackUsed
						.getAccuracy())
						/ receiver.getEvasion();
				String s = NewTurnAction.Bernoulli(netAccuracy);

				if (s == "No") {
					miss = true;
					// nbs.setMiss(true);
				}
			}

			if (user == nbs.getAlly()
					&& ((nbs.isEnemyFlying() && attackUsed.getAttackNum() != 87) || (nbs
							.isEnemyDug() && attackUsed.getAttackNum() != 89))) {
				miss = true;
			} else if (user == nbs.getEnemy()
					&& ((nbs.isAllyFlying() && attackUsed.getAttackNum() != 87) || (nbs
							.isAllyDug() && attackUsed.getAttackNum() != 89))) {
				miss = true;
			}

			if (miss
					|| ((attackUsed.getAttackNum() == 26 || attackUsed
							.getAttackNum() == 136) && receiver.getType1() == Types.GHOST)) {// &&
																								// attackUsed.getDamage(nbs,
																								// user,
																								// receiver)
																								// !=
																								// 0){
				int extra = 0;
				NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs,
						_self + user.getName() + "'s attack missed!", "");
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 1, nta);

				if (attackUsed.getAttackNum() == 26
						|| attackUsed.getAttackNum() == 136) {
					extra++;
					SysOut.print("Damage: "
							+ attackUsed.getDamage(_nbs, user, receiver));
					NewTurnAction ntaOuch = new NewTurnAction.TA_Recoil(nbs,
							user, 0.125, attackUsed.getDamage(_nbs, user,
									receiver));
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + extra + 1,
							ntaOuch);
				}

				if (user.getStatus() == 2 || user.getStatus() == 4
						|| user.getStatus() == 6) {
					extra++;
					NewTurnAction ntaPsnBrn = new TA_PSNorBRN(nbs, user, user
							.getStatus());
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + extra + 1,
							ntaPsnBrn);
				}

				if (user == nbs.getAlly()) {
					if (nbs.isAllyLeechSeeded()) {
						extra++;
						NewTurnAction ntaLeechSeed = new TA_AuxDamage(nbs,
								user, receiver, "", "Leech Seed");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra
										+ 1, ntaLeechSeed);
					}
				} else {
					if (nbs.isEnemyLeechSeeded()) {
						extra++;
						NewTurnAction ntaLeechSeed = new TA_AuxDamage(nbs,
								user, receiver, "Enemy ", "Leech Seed");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra
										+ 1, ntaLeechSeed);
					}
				}

			} else if ((user == nbs.getAlly() && nbs.getAllyFlinch())
					|| (user == nbs.getEnemy() && nbs.getEnemyFlinch())) {
				int extra = 0;

				NewTurnAction ntaFlinch = new NewTurnAction.TA_DisplayEffect(
						nbs, _self + user.getName() + " flinched!", "");
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 1, ntaFlinch);

				if (user.getStatus() == 2 || user.getStatus() == 4
						|| user.getStatus() == 6) {
					extra++;
					NewTurnAction ntaPsnBrn = new TA_PSNorBRN(nbs, user, user
							.getStatus());
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + extra + 1,
							ntaPsnBrn);
				}

				if (user == nbs.getAlly()) {
					if (nbs.isAllyLeechSeeded()) {
						extra++;
						NewTurnAction ntaLeechSeed = new TA_AuxDamage(nbs,
								user, receiver, "", "Leech Seed");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra
										+ 1, ntaLeechSeed);
					}
				} else {
					if (nbs.isEnemyLeechSeeded()) {
						extra++;
						NewTurnAction ntaLeechSeed = new TA_AuxDamage(nbs,
								user, receiver, "Enemy ", "Leech Seed");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra
										+ 1, ntaLeechSeed);
					}
				}
			} else {
				int extra = 0;

				if (attackUsed.getPower() > 0) {

					boolean critical = false;

					int critStage = 1;

					/*
					 * if(user == nbs.getEnemy()){ critStage = nbs.getEnemyCH();
					 * } if(user == nbs.getAlly()){ critStage = nbs.getAllyCH();
					 * }
					 */
					// For attacks like Slash
					if (attackUsed.hasHighCriticalHitRatio()) {
						critStage++;
					}

					// Checks to see if Focus Energy OR Dire Hit was used on
					// last turn.
					// Resets to false after checking
					if (user.isFocused()) {
						critStage += 2;
					}
					if (user.isDireHit()) {
						critStage++;
					}

					switch (critStage) {
					case 1:
						critical = (NewTurnAction.Bernoulli(6.25) == "Yes" ? true
								: false);
						break;
					case 2:
						critical = (NewTurnAction.Bernoulli(12.5) == "Yes" ? true
								: false);
						break;
					case 3:
						critical = (NewTurnAction.Bernoulli(25) == "Yes" ? true
								: false);
						break;
					case 4:
						critical = (NewTurnAction.Bernoulli(33.3) == "Yes" ? true
								: false);
						break;
					case 5:
						critical = (NewTurnAction.Bernoulli(50) == "Yes" ? true
								: false);
						break;
					}

					int damage = attackUsed.getDamage(nbs, user, receiver);
					double mod = 1;
					if (attackUsed.getPhysical()) { // reflect
						if ((user == nbs.getAlly() && nbs.isEnemyReflected())
								|| (user == nbs.getEnemy() && nbs
										.isAllyReflected())) {
							mod = 0.5;
						}
					} else { // light screen
						if ((user == nbs.getAlly() && nbs
								.isEnemyLightScreened())
								|| (user == nbs.getEnemy() && nbs
										.isAllyLightScreened())) {
							mod = 0.5;
						}
					}
					if (critical) {
						extra++;
						nbs.setHPListenerChange(receiver,
								(int) (-2 * mod * damage) - 1);
						NewTurnAction nta = new TA_DisplayEffect(nbs,
								"Critical Hit!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								nta);
					} else {
						nbs.setHPListenerChange(receiver,
								(int) (-1 * mod * damage) - 1);
					}

					if (Pokemon.isSuperEffective(attackUsed.getType(), receiver
							.getType1(), receiver.getType2())
							&& !Pokemon.hasNoEffect(attackUsed.getType(),
									receiver.getType1(), receiver.getType2())
							&& !Pokemon.isNotVeryEffective(
									attackUsed.getType(), receiver.getType1(),
									receiver.getType2())) {
						extra++;
						NewTurnAction nta = new TA_Effectiveness(nbs, 0);
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								nta);
					}

					if (Pokemon.isNotVeryEffective(attackUsed.getType(),
							receiver.getType1(), receiver.getType2())
							&& !Pokemon.hasNoEffect(attackUsed.getType(),
									receiver.getType1(), receiver.getType2())
							&& !Pokemon.isSuperEffective(attackUsed.getType(),
									receiver.getType1(), receiver.getType2())) {
						extra++;
						NewTurnAction nta = new TA_Effectiveness(nbs, 1);
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								nta);
					}
					
					// - fire attacks cure frozen
					if(receiver.getStatus() == 5 && attackUsed.getType() == Types.FIRE){
						SysOut.print("Frozen --> Thawed by the flames...");
						extra++;
						String who = "";
						if(receiver == nbs.getEnemy()) who = "Enemy ";
						NewTurnAction ntaThaw = new NewTurnAction.TA_DisplayAutoWrapEffect(_nbs, who + receiver.getName() + " thawed out!");
						nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + extra, ntaThaw);
					}

					// ADDING A STATUS AILMENT
					// VERSION FOR DAMAGING ATTACKS
					// If the probability works out AND it's not already a
					// different status, cause status.

					if (Bernoulli(attackUsed.getStatusProb()) == "Yes") {
						if (receiver.getStatus() == 0) {
							extra++;
							NewTurnAction nta = new TA_InduceStatusAilment(nbs,
									receiver, _other, attackUsed
											.getStatusAilment());
							nbs.getActiveTurn().getList().add(
									nbs.getActiveTurn().getCurrentStage()
											+ extra, nta);
						}
					}

				} else {
					/**
					 * DOES THIS STATEMENT EVER GET CALLED?
					 */
					SysOut.print("ELSE POWER = 0, but damaging?");

					// Place for Growl/Tail-Whip etc. to make their actions

					// /ADDING A STATUS AILMENT
					// VERSION FOR NONDAMAGING ATTACKS
					// If the probability works out AND it's not already a
					// different status, cause status.
					// ***If it fails, it needs to say that it had no effect.
					// This is the main
					// difference between damaging and nondamaging attacks that
					// cause status ailments.***///

//					if (Bernoulli(attackUsed.getStatusProb()) == "Yes") {
//						if (receiver.getStatus() == 0) {
//							extra++;
//							NewTurnAction nta = new TA_InduceStatusAilment(nbs,
//									receiver, _other, attackUsed
//											.getStatusAilment());
//							nbs.getActiveTurn().getList().add(
//									nbs.getActiveTurn().getCurrentStage()
//											+ extra, nta);
//						} else {
//							extra++;
//							NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
//									nbs, "But it had no effect!", "");
//							nbs.getActiveTurn().getList().add(
//									nbs.getActiveTurn().getCurrentStage() + 1,
//									ntaNothing);
//						}
//					}
				}

				if (attackUsed.getEffect() != "") {
					extra++;
					NewTurnAction nta = new TA_DisplayEffect(nbs, attackUsed
							.getEffect(), "");
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + extra, nta);
				}

				if (user.getStatus() == 2 || user.getStatus() == 4
						|| user.getStatus() == 6) {
					extra++;
					NewTurnAction nta = new TA_PSNorBRN(nbs, user, user
							.getStatus());
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + extra, nta);
				}
				if (user == nbs.getAlly()) {
					if (nbs.isAllyLeechSeeded()) {
						extra++;
						NewTurnAction ntaLeechSeed = new TA_AuxDamage(nbs,
								user, receiver, "", "Leech Seed");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								ntaLeechSeed);
					}
				} else {
					if (nbs.isEnemyLeechSeeded()) {
						extra++;
						NewTurnAction ntaLeechSeed = new TA_AuxDamage(nbs,
								user, receiver, "Enemy ", "Leech Seed");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								ntaLeechSeed);
					}
				}

				if (attackUsed.getSecondAttack() != null) {
					SysOut.print("Second Attack");
					NewTurnAction nta = new TA_TwoTurnAttack(nbs, user,
							receiver, attackUsed.getSecondAttack());
					if (user == nbs.getAlly())
						nbs.setNextTurnAction(nta);
					else
						nbs.setNextEnemyTurnAction(nta);
				}
				
				// else{
				// if(user == nbs.getAlly())
				// nbs.setNextTurnAction(null);
				// else
				// nbs.setNextEnemyTurnAction(null);
				// }
			}
		}
	}

	public static class TA_NonDamagingAttack extends NewTurnAction {
		private Pokemon _user, _receiver;
		private Attack _attackUsed;
		private String self, other;

		public TA_NonDamagingAttack(NewBattleScreen nbs, Pokemon user,
				Pokemon receiver, String who, Attack attackUsed) {
			super(nbs, who + user.getName() + " used " + attackUsed.getName()
					+ "!", "");
			_user = user;
			self = who;
			_receiver = receiver;
			_attackUsed = attackUsed;
			if (self == "")
				other = "Enemy ";
			else
				other = "";
		}

		public void execute() {
			
			if(_user == nbs.getAlly()){
				_receiver = nbs.getEnemy();
			}
			
			

			if(_user == nbs.getAlly() && nbs.getAlly().getStatus() > 0){
				int status = nbs.getAlly().getStatus();
				if(status == 1){
					NewTurnAction allyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(nbs, _user.getName() + " is fast asleep!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, allyStat);
					return;
				}
				else if(status == 5){
					NewTurnAction allyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(nbs, _user.getName() + " is frozen solid!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, allyStat);
					return;
				}
				else if(status == 3 && Math.random()*101 > 75){
					NewTurnAction allyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(nbs, _user.getName() + " is fully paralyzed!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, allyStat);
					return;
				}
			}
			if(_user == nbs.getEnemy() && nbs.getEnemy().getStatus() > 0){
				int status = nbs.getEnemy().getStatus();
				if(status == 1){
					NewTurnAction eyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(nbs, "Enemy " + _user.getName() + " is fast asleep!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, eyStat);
					return;
				}
				else if(status == 5){
					NewTurnAction eyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(nbs, "Enemy " + _user.getName() + " is frozen solid!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, eyStat);
					return;	
				}
				else if(status == 3 && Math.random()*101 > 75){
					NewTurnAction eyStat = new NewTurnAction.TA_DisplayAutoWrapEffect(nbs, "Enemy " + _user.getName() + " is fully paralyzed!");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, eyStat);
					return;
				}
			}
			
			super.execute();
			
			nbs.GBSelect=_attackUsed.getAttackNum();
			nbs.GBTimer.start();
			
			
			_attackUsed.setCurrentPP(_attackUsed.getCurrentPP() - 1);

			if (_user == nbs.getEnemy()) {

				nbs.setEnemyLastAttack(_attackUsed);

				_receiver = nbs.getAlly();
				if (nbs.isEnemyDisabled()) {
					nbs.decrementEDR();
				}
				if (nbs.isEnemyLightScreened()) {
					nbs.decrementELS();
				}
				if (nbs.isEnemyReflected()) {
					nbs.decrementER();
				}
			} else if (_user == nbs.getAlly()) {

				nbs.setAllyLastAttack(_attackUsed);

				_receiver = nbs.getEnemy();
				if (nbs.isAllyDisabled()) {
					nbs.decrementADR();
				}
				if (nbs.isAllyLightScreened()) {
					nbs.decrementALS();
				}
				if (nbs.isAllyReflected()) {
					nbs.decrementAR();
				}
			}

			boolean plusOne = false, plusTwo = false;
			
			
			switch (_attackUsed.getNonDamageType()) {

			case 1: // Enemy attack is lowered (ex. Growl)
				if (_user == nbs.getEnemy()) {
					if (nbs.isAllyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;
						break;
					}

					// _user == nbs.getEnemy()){
					if (nbs.allyAtkStage > -6 && !nbs.getAlly().isGuardSpec()) {
						nbs.allyAtkStage = nbs.allyAtkStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Attack fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.isEnemyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;

						break;
					}

					if (nbs.enemyAtkStage > -6 && !nbs.getEnemy().isGuardSpec()) {
						nbs.enemyAtkStage = nbs.enemyAtkStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Attack fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}

				break;

			case 2: // Enemy defense is lowered (ex. Tail Whip)
				if (_user == nbs.getEnemy()) {
					if (nbs.isAllyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;
						break;
					}

					if (nbs.allyDefStage > -6 && !nbs.getAlly().isGuardSpec()) {
						nbs.allyDefStage = nbs.allyDefStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Defense fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.isEnemyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;
						break;
					}

					if (nbs.enemyDefStage > -6 && !nbs.getEnemy().isGuardSpec()) {
						nbs.enemyDefStage = nbs.enemyDefStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s somatosensation", "was restored!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 3: // Enemy speed is lowered
				if (_user == nbs.getEnemy()) {
					if (nbs.isAllyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;
						break;
					}

					if (nbs.allySpdStage > -6 && !nbs.getAlly().isGuardSpec()) {
						nbs.allySpdStage = nbs.allySpdStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Speed fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.isEnemyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;

						break;
					}

					if (nbs.enemySpdStage > -6 && !nbs.getEnemy().isGuardSpec()) {
						nbs.enemySpdStage = nbs.enemySpdStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Speed fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 4: // Enemy accuracy is lowered (ex. Sand-Attack)
				if (_user == nbs.getEnemy()) {
					if (nbs.isAllyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;
						break;
					}

					if (nbs.allyAccStage > -6 && !nbs.getAlly().isGuardSpec()) {
						nbs.allyAccStage = nbs.allyAccStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Accuracy fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.isEnemyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;

						break;
					}

					if (nbs.enemyAccStage > -6 && !nbs.getEnemy().isGuardSpec()) {
						nbs.enemyAccStage = nbs.enemyAccStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Accuracy fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 5: // Enemy sp. attack is lowered
				if (_user == nbs.getEnemy()) {
					if (nbs.isAllyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;
						break;
					}

					if (nbs.allySpAtkStage > -6 && !nbs.getAlly().isGuardSpec()) {
						nbs.allySpAtkStage = nbs.allySpAtkStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Sp. Attack fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.isEnemyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;

						break;
					}

					if (nbs.enemySpAtkStage > -6
							&& !nbs.getEnemy().isGuardSpec()) {
						nbs.enemySpAtkStage = nbs.enemySpAtkStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Sp. Attack fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 6: // Enemy sp. defense is lowered
				if (_user == nbs.getEnemy()) {
					if (nbs.isAllyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;

						break;
					}

					if (nbs.allySpDefStage > -6 && !nbs.getAlly().isGuardSpec()) {
						nbs.allySpDefStage = nbs.allySpDefStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Sp. Defense fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.isEnemyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;

						break;
					}

					if (nbs.enemySpDefStage > -6
							&& !nbs.getEnemy().isGuardSpec()) {
						nbs.enemySpDefStage = nbs.enemySpDefStage - 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ "'s Sp. Defense fell!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 7: // Ally atk is raised
				if (_user == nbs.getEnemy()) {
					if (nbs.enemyAtkStage < 6) {
						nbs.enemyAtkStage = nbs.enemyAtkStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs,
								self + _user.getName() + "'s Attack rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allyAtkStage < 6) {
						nbs.allyAtkStage = nbs.allyAtkStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs,
								self + _user.getName() + "'s Attack rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}

				break;

			case 8: // Ally def is raised
				if (_user == nbs.getEnemy()) {
					if (nbs.enemyDefStage < 6) {
						nbs.enemyDefStage = nbs.enemyDefStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName()
										+ "'s Defense rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allyDefStage < 6) {
						nbs.allyDefStage = nbs.allyDefStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName()
										+ "'s Defense rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 9: // Ally spd is raised
				if (_user == nbs.getEnemy()) {
					if (nbs.enemySpdStage < 6) {
						nbs.enemySpdStage = nbs.enemySpdStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Speed rose!",
								"");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allySpdStage < 6) {
						nbs.allySpdStage = nbs.allySpdStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Speed rose!",
								"");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 10: // Ally acc is raised
				if (_user == nbs.getEnemy()) {
					if (nbs.enemyAccStage < 6) {
						nbs.enemyAccStage = nbs.enemyAccStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName()
										+ "'s Accuracy rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allyAccStage < 6) {
						nbs.allyAccStage = nbs.allyAccStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName()
										+ "'s Accuracy rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 11: // Ally spatk is raised
				if (_user == nbs.getEnemy()) {
					if (nbs.enemySpAtkStage < 6) {
						nbs.enemySpAtkStage = nbs.enemySpAtkStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _user.getName()
										+ "'s Sp. Attack rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allySpAtkStage < 6) {
						nbs.allySpAtkStage = nbs.allySpAtkStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName()
										+ "'s Sp. Attack. rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}

				break;

			case 12: // Ally spdef is raised
				if (_user == nbs.getEnemy()) {
					if (nbs.enemySpDefStage < 6) {
						nbs.enemySpDefStage = nbs.enemySpDefStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName()
										+ "'s Sp. Defense rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allySpDefStage < 6) {
						nbs.allySpDefStage = nbs.allySpDefStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName()
										+ "'s Sp. Defense rose!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 13: // Two-turn attack that prepares on first turn
				this.setDialogue(self + _user.getName()
						+ _attackUsed.getEffect(), "");
				//FIXME _attackUsed.getDamage(nbs, _user, _receiver); // To make sure
				
				if(_attackUsed.getAttackNum() == 91){ //DIG
					if(_user == nbs.getAlly()){
						nbs.setAllyDug(true);
						NewTurnAction ntaDug = new NewTurnAction.TA_DisplayEffect(nbs, nbs.getAlly().getName() + " went underground!", "");
						nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, ntaDug);
						
					}
					else{
						nbs.setEnemyDug(true);
						NewTurnAction ntaDug = new NewTurnAction.TA_DisplayEffect(nbs, "Enemy " + nbs.getEnemy().getName() + " went underground!", "");
						nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, ntaDug);
					}
				}
				
				if(_attackUsed.getAttackNum() == 19){ //FLY
					if(_user == nbs.getAlly()){
						nbs.setAllyFlying(true);
						NewTurnAction ntaDug = new NewTurnAction.TA_DisplayEffect(nbs, nbs.getAlly().getName() + " soared into the air!", "");
						nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, ntaDug);
					}
					else{
						nbs.setEnemyFlying(true);
						NewTurnAction ntaDug = new NewTurnAction.TA_DisplayEffect(nbs, "Enemy " + nbs.getEnemy().getName() + " soared into the air!", "");
						nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, ntaDug);
					}
				}
				
				// FLY works?
				NewTurnAction ntaNext = new NewTurnAction.TA_BasicAttack(nbs,
						_user, _receiver, self, _attackUsed.getSecondAttack());
				nbs.setNextTurnAction(ntaNext);

				plusOne = true;
				
				break;
				
				
				

			case 14: // Attack confuses opponent and does no damage.
				this.setDialogue(self + _user.getName() + " used "
						+ _attackUsed.getName() + "!", "");

				// If its the enemy using it
				if (self == "Enemy ") {// _user == nbs.getEnemy()){
					if (!nbs.isAllyConfused()) {
						nbs.setAllyConfusion(true);
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ " became confused!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				// If its the ally using it
				else {
					if (!nbs.isEnemyConfused()) {
						nbs.setEnemyConfusion(true);
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ " became confused!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 15: // Leech Seed
				this.setDialogue(self + _user.getName() + " used "
						+ _attackUsed.getName() + "!", "");

				// If its the enemy using it
				if (self == "Enemy ") {// _user == nbs.getEnemy()){
					// Doesn't work on grass type pokemon
					if (!nbs.isAllyLeechSeeded()
							&& _receiver.getType1() != Types.GRASS
							&& _receiver.getType2() != Types.GRASS) {
						nbs.setAllyLeechSeed(true);
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ " was seeded!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				// If its the ally using it
				else {
					// Doesn't work on grass type pokemon
					if (!nbs.isEnemyLeechSeeded()
							&& _receiver.getType1() != Types.GRASS
							&& _receiver.getType2() != Types.GRASS) {
						nbs.setEnemyLeechSeed(true);
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, other + _receiver.getName()
										+ " was seeded!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed!", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 16: // PoisonPowder, StunSpore, Sleep Powder

				this.setDialogue(self + _user.getName() + " used "
						+ _attackUsed.getName() + "!", "");

				NewTurnAction ntaEffect = null;

				if (_receiver.getStatus() == 0) {
					if (_attackUsed.getName() == "PoisonPowder"
							|| _attackUsed.getName() == "Poison Gas") {
						if (_receiver.getType1() != Types.POISON
								&& _receiver.getType2() != Types.POISON)
							ntaEffect = new NewTurnAction.TA_InduceStatusAilment(
									nbs, _receiver, other, 2);
						else
							ntaEffect = new NewTurnAction.TA_DisplayEffect(nbs,
									"...but nothing happened.", "");
					}

					if (_attackUsed.getName() == "Stun Spore"
							|| _attackUsed.getName() == "Thunder Wave"
							|| _attackUsed.getName() == "Glare") {
						ntaEffect = new NewTurnAction.TA_InduceStatusAilment(
								nbs, _receiver, other, 3);
					}

					if (_attackUsed.getName() == "Sleep Powder"
							|| _attackUsed.getName() == "Sing"
							|| _attackUsed.getName() == "Hypnosis"
							|| _attackUsed.getName() == "Lovely Kiss"
							|| _attackUsed.getName() == "Spore") {
						ntaEffect = new NewTurnAction.TA_InduceStatusAilment(
								nbs, _receiver, other, 1);
					}
					if (_attackUsed.getName() == "Toxic") {
						ntaEffect = new NewTurnAction.TA_InduceStatusAilment(
								nbs, _receiver, other, 6);
					}
				} else {
					ntaEffect = new NewTurnAction.TA_DisplayEffect(nbs,
							"...but nothing happened.", "");
				}

				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 1, ntaEffect);
				plusOne = true;
				break;

			case 17: // Focus Energy

				this.setDialogue(self + _user.getName() + " used "
						+ _attackUsed.getName() + "!", "");

				NewTurnAction ntaFocus = null;

				if (!_user.isFocused()) {
					_user.setFocusedTrue();
					ntaFocus = new NewTurnAction.TA_DisplayEffect(nbs, self
							+ _user.getName() + " is getting pumped!", "");
				} else {
					ntaFocus = new NewTurnAction.TA_DisplayEffect(nbs,
							"...but nothing happened.", "");
				}
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 1, ntaFocus);
				plusOne = true;
				break;

			case 18: // Lowers enemy Def by 2 ex. Screech
				if (_user == nbs.getEnemy()) {
					if (nbs.isAllyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;
						break;
					}

					if (nbs.allyDefStage > -6) {
						nbs.allyDefStage = nbs.allyDefStage - 1;
						if (nbs.allyDefStage > -6) {
							nbs.allyDefStage = nbs.allyDefStage - 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _receiver.getName() + "'s Defense",
								"sharply fell!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.isEnemyMisty()) {
						NewTurnAction ntaf = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaf);
						plusOne = true;

						break;
					}
					if (nbs.enemyDefStage > -6) {
						nbs.enemyDefStage = nbs.enemyDefStage - 1;
						if (nbs.enemyDefStage > -6) {
							nbs.enemyDefStage = nbs.enemyDefStage - 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _receiver.getName() + "'s Defense",
								"sharply fell!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 19: // Raises enemy Att by 2 ex. Swords Dance
				if (_user == nbs.getEnemy()) {
					if (nbs.enemyAtkStage < 6) {
						nbs.enemyAtkStage = nbs.enemyAtkStage + 1;
						if (nbs.enemyAtkStage < 6) {
							nbs.enemyAtkStage = nbs.enemyAtkStage + 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Attack",
								"sharply rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allyAtkStage < 6) {
						nbs.allyAtkStage = nbs.allyAtkStage + 1;
						if (nbs.allyAtkStage < 6) {
							nbs.allyAtkStage = nbs.allyAtkStage + 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Attack",
								"sharply rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 20: // Raises speed by two stages - agility
				if (self == "Enemy ") {// _user == nbs.getEnemy()){
					if (nbs.enemySpdStage < 6) {
						nbs.enemySpdStage = nbs.enemySpdStage + 1;
						if (nbs.enemySpdStage < 6) {
							nbs.enemySpdStage = nbs.enemySpdStage + 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Speed",
								"sharply rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allySpdStage < 6) {
						nbs.allySpdStage = nbs.allySpdStage + 1;
						if (nbs.allySpdStage < 6) {
							nbs.allySpdStage = nbs.allySpdStage + 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Speed",
								"sharply rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 21: // Raises defense by two stages - barrier
				if (self == "Enemy ") {// _user == nbs.getEnemy()){
					if (nbs.enemyDefStage < 6) {
						nbs.enemyDefStage = nbs.enemyDefStage + 1;
						if (nbs.enemyDefStage < 6) {
							nbs.enemyDefStage = nbs.enemyDefStage + 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Defense",
								"sharply rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allyDefStage < 6) {
						nbs.allyDefStage = nbs.allyDefStage + 1;
						if (nbs.allyDefStage < 6) {
							nbs.allyDefStage = nbs.allyDefStage + 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Defense",
								"sharply rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 22: // Increases user Evasion by 1 stage
				if (self == "Enemy ") {// _user == nbs.getEnemy()){
					if (nbs.enemyEvaStage < 6) {
						nbs.enemyEvaStage = nbs.enemyEvaStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Evasion",
								"rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allyEvaStage < 6) {
						nbs.allyEvaStage = nbs.allyEvaStage + 1;
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Evasion",
								"rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}

				if(_attackUsed.getAttackNum() == 107){
					if (_user == nbs.getAlly()) {
						nbs.setAllyMinimized(true);
					} else {
						nbs.setEnemyMinimized(true);
					}
				}

				break;

			case 23: // Increases user Sp. Def. by 2 stages
				if (self == "Enemy") {
					if (nbs.enemySpDefStage < 6) {
						nbs.enemySpDefStage = nbs.enemySpDefStage + 1;
						if (nbs.enemySpDefStage < 6) {
							nbs.enemySpDefStage = nbs.enemySpDefStage + 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Sp. Def",
								"sharply rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				} else {
					if (nbs.allySpDefStage < 6) {
						nbs.allySpDefStage = nbs.allySpDefStage + 1;
						if (nbs.allySpDefStage < 6) {
							nbs.allySpDefStage = nbs.allySpDefStage + 1;
						}
						NewTurnAction ntaMod = new NewTurnAction.TA_DisplayEffect(
								nbs, self + _user.getName() + "'s Sp. Def",
								"sharply rose!");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaMod);
						plusOne = true;
					} else {
						NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but nothing happened.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaNothing);
						plusOne = true;
					}
				}
				break;

			case 24:

				if (nbs.isWild() && _receiver.getLevel() <= _user.getLevel()) {
					NewTurnAction flee = new NewTurnAction.TA_DisplayEffect(
							nbs, _receiver.getName() + " ran away scared!", "");
					NewTurnAction over = new NewTurnAction.TA_EndBattle(nbs);
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + 1, flee);
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + 2, over);
					plusOne = true;
					plusTwo = true;
				} else if (nbs.isTrainer()) {
					if (_user == nbs.getAlly()) {
						int anyLeft = 0;
						for (int i = 0; i < nbs.getEnemyTrainer().getBelt()
								.size(); i++) {
							if (nbs.getEnemyTrainer().getBelt().get(i)
									.getCurrentHP() > 0)
								anyLeft++;
						}
						if (anyLeft >= 2) {
							Pokemon rando = null;
							while (rando == null || rando == _receiver
									|| rando.getCurrentHP() == 0) {
								rando = nbs.getEnemyTrainer().getBelt().get(
										(int) (Math.random() * nbs
												.getEnemyTrainer().getBelt()
												.size()));
							}
							NewTurnAction ntaS = new NewTurnAction.TA_EnemySwitching(
									nbs, nbs.getEnemyTrainer(), rando);
							NewTurnAction ntaD = new NewTurnAction.TA_DisplayEffect(
									nbs, _receiver.getName()
											+ " was chased away!", "");
							nbs.getActiveTurn().getList().add(
									nbs.getActiveTurn().getCurrentStage() + 1,
									ntaD);
							nbs.getActiveTurn().getList().add(
									nbs.getActiveTurn().getCurrentStage() + 2,
									ntaS);
							plusOne = true;
							plusTwo = true;
							nbs.getActiveTurn().getList().setSize(
									nbs.getActiveTurn().getCurrentStage() + 3);
						} else {
							NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
									nbs, "...but it failed!", "");
							nbs.getActiveTurn().getList().add(
									nbs.getActiveTurn().getCurrentStage() + 1,
									ntaNothing);
							plusOne = true;
						}
					} else if (_user == nbs.getAlly()) {
						int anyLeft = 0;
						for (int i = 0; i < nbs.getPlayer().getAllActive()
								.size(); i++) {
							if (nbs.getPlayer().getActivePokemon(i)
									.getCurrentHP() > 0)
								anyLeft++;
						}
						if (anyLeft >= 2) {
							Pokemon rando = null;
							while (rando == null || rando == _receiver
									|| rando.getCurrentHP() == 0) {
								rando = nbs.getPlayer().getActivePokemon(
										(int) (Math.random() * nbs.getPlayer()
												.getAllActive().size()));
							}
							NewTurnAction ntaS = new NewTurnAction.TA_SwitchPokemon2(
									nbs, rando);
							NewTurnAction ntaD = new NewTurnAction.TA_DisplayEffect(
									nbs, _receiver.getName()
											+ " was chased away!", "");
							nbs.getActiveTurn().getList().add(
									nbs.getActiveTurn().getCurrentStage() + 1,
									ntaD);
							nbs.getActiveTurn().getList().add(
									nbs.getActiveTurn().getCurrentStage() + 2,
									ntaS);
							plusOne = true;
							plusTwo = true;
							nbs.getActiveTurn().getList().setSize(
									nbs.getActiveTurn().getCurrentStage() + 3);
						} else {
							NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
									nbs, "...but it failed!", "");
							nbs.getActiveTurn().getList().add(
									nbs.getActiveTurn().getCurrentStage() + 1,
									ntaNothing);
							plusOne = true;
						}
					}
				} else {
					NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(
							nbs, "...but it failed!", "");
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + 1,
							ntaNothing);
					plusOne = true;
				}

				break;

			case 25: // Disable

				int numTurns = (int) (Math.random() * 4) + 4;
				int att = (int) (Math.random() * _receiver.getAttacks().size());
				SysOut.print("Disabled attack #" + att + " for " + numTurns
						+ " turns.");
				if (_user == nbs.getAlly()) {
					if (!nbs.isEnemyDisabled()) {
						nbs.setEnemyDisabledRemaining(numTurns);
						nbs.setEnemyDisabled(att);
						NewTurnAction ntaD = new NewTurnAction.TA_DisplayEffect(
								nbs, "Enemy " + _receiver.getName()
										+ "'s attack,", _receiver.getAttacks()
										.get(att).getName()
										+ ", was disabled!");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaD);
						plusOne = true;
					} else {
						NewTurnAction ntaD = new NewTurnAction.TA_DisplayEffect(
								nbs, _receiver.getName()
										+ " is already disabled!", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaD);
						plusOne = true;
					}
				} else {
					if (!nbs.isAllyDisabled()) {
						nbs.setAllyDisabledRemaining(numTurns);
						nbs.setAllyDisabled(att);
						NewTurnAction ntaD = new NewTurnAction.TA_DisplayEffect(
								nbs, _receiver.getName() + "'s attack,",
								_receiver.getAttacks().get(att).getName()
										+ ", was disabled!");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaD);
						plusOne = true;
					} else {
						NewTurnAction ntaD = new NewTurnAction.TA_DisplayEffect(
								nbs, _receiver.getName()
										+ " is already disabled!", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntaD);
						plusOne = true;
					}
				}

				break;

			case 26: // softboiled
				if (_user.getCurrentHP() < _user.getMaxHP()) {
					NewTurnAction ntaGain = new NewTurnAction.TA_GainHP(nbs,
							_user, _user.getName() + " regained HP!",
							(int) (_user.getMaxHP() * 0.5) + 1);
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + 1, ntaGain);
					plusOne = true;
				} else {
					NewTurnAction ntaFail = new NewTurnAction.TA_DisplayEffect(
							nbs, "...but it failed.", "");
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + 1, ntaFail);
					plusOne = true;
				}

				break;

			case 27: // rest

				NewTurnAction ntaRest = new NewTurnAction.TA_GainHP(nbs, _user,
						_user.getName() + " regained HP!", _user.getMaxHP());
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 1, ntaRest);
				plusOne = true;
				String who = "";
				if (_user == nbs.getEnemy())
					who = "Enemy ";
				NewTurnAction ntaSleep = new NewTurnAction.TA_InduceStatusAilment(
						nbs, _user, who, ASLEEP);
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 2, ntaSleep);
				plusTwo = true;
				break;

			case 28: // haze

				nbs.resetAllyStages();
				nbs.resetEnemyStages();
				nbs.setAllyMisty(false);
				nbs.setEnemyMisty(false);

				NewTurnAction ntaHaze = new NewTurnAction.TA_DisplayEffect(nbs,
						"All stats were reset!", "");
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 1, ntaHaze);
				plusOne = true;

				break;

			case 29: // mist

				if (_user == nbs.getAlly()) {
					if (!nbs.isAllyMisty())
						nbs.setAllyMisty(true);
					else {
						NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntad);
						plusOne = true;
					}
				} else {
					if (!nbs.isEnemyMisty())
						nbs.setEnemyMisty(true);
					else {
						NewTurnAction ntad = new NewTurnAction.TA_DisplayEffect(
								nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList()
								.add(nbs.getActiveTurn().getCurrentStage() + 1,
										ntad);
						plusOne = true;
					}
				}

				break;

			case 30: // light screen

				if (_user == nbs.getAlly()) {
					nbs.setAllyLightScreened(true);
				} else {
					nbs.setEnemyLightScreened(true);
				}

				break;

			case 31: // reflect

				if (_user == nbs.getAlly()) {
					nbs.setAllyReflected(true);
				} else {
					nbs.setEnemyReflected(true);
				}

				break;

			case 32: // transform

				if (_user == nbs.getAlly() && !nbs.isEnemyTransformed()) {
					nbs.setAllyTransform();
				} else if (_user == nbs.getEnemy() && !nbs.isAllyTransformed()) {
					nbs.setEnemyTransform();
				}

				break;

			case 33: // mirror move

				if (_user == nbs.getAlly() && nbs.getEnemyLastAttack() != null && nbs.getEnemyLastAttack().getAttackNum() != 119) {
					nbs.setAllyLastAttack(nbs.getEnemyLastAttack());
					NewTurnAction nta;
					if(nbs.getEnemyLastAttack().getPower() == 0){
						nta = new NewTurnAction.TA_NonDamagingAttack(nbs, _user, _receiver, "", nbs.getAllyLastAttack());
					}
					else{
						nta = new NewTurnAction.TA_BasicAttack(nbs, _user, _receiver, "", nbs.getAllyLastAttack());
					}
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, nta);
					plusOne = true;
				} 
				else if (_user == nbs.getEnemy() && nbs.getAllyLastAttack() != null && nbs.getAllyLastAttack().getAttackNum() != 119) {
					nbs.setEnemyLastAttack(nbs.getAllyLastAttack());
					NewTurnAction nta;
					if(nbs.getAllyLastAttack().getPower() == 0){
						nta = new NewTurnAction.TA_NonDamagingAttack(nbs, _user, _receiver, "Enemy ", nbs.getEnemyLastAttack());
					}
					else{
						nta = new NewTurnAction.TA_BasicAttack(nbs, _user, _receiver, "Enemy ", nbs.getEnemyLastAttack());
					}
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, nta);
					plusOne = true;
				} 
				else {
					NewTurnAction ntaFail = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed.", "");
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, ntaFail);
					plusOne = true;
				}
				
				break;
				
			case 34:
				//metronome
				
				int num = (int)(Math.random()*164);
				Attack rando = Attack.getAttackByNum(num);
				SysOut.print("RANDOM ATTACK: " + rando.getName());
				
				who = "";
				if(_user != nbs.getAlly()){who = "Enemy ";}
				NewTurnAction metro = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed.","");
				if(rando.getPower() == 0){
					metro = new NewTurnAction.TA_NonDamagingAttack(nbs, _user, _receiver, who, rando);
				}
				else{
					metro = new NewTurnAction.TA_BasicAttack(nbs, _user, _receiver, who, rando);
				}
				
				nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1, metro);
				plusOne = true;
				
				break;
				
			case 35: //whirlwind
				
				if(nbs.isWild()){
					NewTurnAction flee = new NewTurnAction.TA_DisplayEffect(nbs, _receiver.getName() + " was blown away!", "");
					NewTurnAction over = new NewTurnAction.TA_EndBattle(nbs);
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,flee);
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+2,over);
					plusOne = true;
					plusTwo = true;
				}
				else if(nbs.isTrainer()){
				  	NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed!","");
				  	nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,ntaNothing);
				  	plusOne = true;
				}
			
				break;
				
			case 36: //TODO conversion
				
				_user.setType(_receiver.getType1());
				if(_user == nbs.getAlly()){
					NewTurnAction ntaD = new NewTurnAction.TA_DisplayAutoWrapEffect(nbs, _user.getName() + " changed its' Type to " + _receiver.getType1().name());
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, ntaD);
					plusOne = true;
				}
				else{
					NewTurnAction ntaD = new NewTurnAction.TA_DisplayAutoWrapEffect(nbs, "Enemy " + _user.getName() + " changed its' Type to" + _receiver.getType1().name());
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, ntaD);
					plusOne = true;
				}
					
				
				break;
				
			case 37: //FIXME counter
				SysOut.print("Counter used...");
				if(_user == nbs.getAlly()){
					//SysOut.print("...by ally...");
					//SysOut.print(nbs.getEnemyLastAttack().getName());
					if(nbs.getEnemyLastAttack() != null && nbs.getEnemyLastAttack().getPhysical()){
						NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, _user.getName() + " countered!", "");
						nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, nta);
						nbs.A_Button();
						plusOne = true;
						int dam = 2*nbs.getLastAllyDamageReceived();
						nbs.setHPListenerChange(_receiver, (int) (dam) - 1);
						
					}
					else{
						NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed.", "");
						nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, nta);
						plusOne = true;
					}
				}
				else{
					if(nbs.getAllyLastAttack() != null && nbs.getAllyLastAttack().getPhysical()){
						NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "Enemy " + _user.getName() + " countered!", "");
						nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, nta);
						nbs.A_Button();
						plusOne = true;
						int dam = 2*nbs.getLastEnemyDamageReceived();
						nbs.setHPListenerChange(_receiver, (int) dam - 1);
					}
					else{
							NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed.", "");
							nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage() + 1, nta);
							plusOne = true;
					}
				}
				
				break;
				
			case 38: //substitute
				
				break;
			
			case 39: //mimic
				
				break;
				
			case 40: // bide
				
				break;
				
			case 41: //teleport
				if(nbs.isWild()){
					NewTurnAction flee = new NewTurnAction.TA_DisplayEffect(nbs, _user.getName() + " teleported to safety...", "");
					NewTurnAction over = new NewTurnAction.TA_EndBattle(nbs);
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,flee);
					nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+2,over);
					plusOne = true;
					plusTwo = true;
				}
				else if(nbs.isTrainer()){
				  	NewTurnAction ntaNothing = new NewTurnAction.TA_DisplayEffect(nbs, "...but it failed!","");
				  	nbs.getActiveTurn().getList().add(nbs.getActiveTurn().getCurrentStage()+1,ntaNothing);
				  	plusOne = true;
				}
				break;
			}

			
			int extra = 0;
			
			if(plusOne) extra = 1;
			if(plusTwo) extra = 2;
			
			if(_attackUsed.getNonDamageType() != 34){
				if (_user.getStatus() == 2 || _user.getStatus() == 4
						|| _user.getStatus() == 6) {
					extra++;
					NewTurnAction nta = new TA_PSNorBRN(nbs, _user, _user
							.getStatus());
					nbs.getActiveTurn().getList().add(
							nbs.getActiveTurn().getCurrentStage() + extra, nta);
				}
	
				if (_user == nbs.getAlly()) {
					if (nbs.isAllyLeechSeeded()) {
						extra++;
						NewTurnAction ntaLeechSeed = new TA_AuxDamage(nbs, _user,
								_receiver, "", "Leech Seed");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								ntaLeechSeed);
					}
				} else {
					if (nbs.isEnemyLeechSeeded()) {
						extra++;
						NewTurnAction ntaLeechSeed = new TA_AuxDamage(nbs, _user,
								_receiver, "Enemy ", "Leech Seed");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								ntaLeechSeed);
					}
				}
			}

		}
	}

	// Can be replaced with DisplayEffect?
	public static class TA_TwoTurnAttack extends NewTurnAction {
		@SuppressWarnings("unused")
		private Pokemon user, receiver;
		@SuppressWarnings("unused")
		private Attack attackUsed;

		public TA_TwoTurnAttack(NewBattleScreen nbs, Pokemon user,
				Pokemon receiver, Attack a) {
			super(nbs, "", "");

			this.user = user;
			this.receiver = receiver;
			attackUsed = a;

			if (a.getEffect() != "") {
				this.setDialogue(user.getName() + a.getEffect(), "");
			}

		}

		public void execute() {
			super.execute();

			if (user == nbs.getAlly()) {
				nbs.setNextTurnAction(null);
			} else {
				nbs.setNextEnemyTurnAction(null);
			}
		}

	}

	public static class TA_DisplayEffect extends NewTurnAction {
		public TA_DisplayEffect(NewBattleScreen nbs, String effect1,
				String effect2) {
			super(nbs, effect1, effect2);
		}
	}

	public static class TA_DisplayAutoWrapEffect extends NewTurnAction {
		public TA_DisplayAutoWrapEffect(NewBattleScreen nbs, String s) {
			super(nbs, "", "");
			this.setWrapDialogue(s);
		}
	}


	public static class TA_ResetStatus extends NewTurnAction {
		Pokemon _target;

		public TA_ResetStatus(NewBattleScreen nbs, Pokemon target, String who,
				int status) {
			super(nbs, "", "");
			_target = target;
			switch (status) {
			case 1:
				this.setDialogue(who + target.getName() + " woke up!", "");
				break;
			case 2:
				this.setDialogue(who + target.getName() + " is no ",
						"longer poisoned!");
				break;
			case 3:
				this.setDialogue(who + target.getName() + " is no ",
						"longer paralyzed!");
				break;
			case 4:
				this.setDialogue(who + target.getName() + " is no ",
						"longer burned!");
				break;
			case 5:
				this.setDialogue(who + target.getName() + " thawed out!", "");
				break;
			}
		}

		public void execute() {
			super.execute();
			_target.setStatus(0);
		}
	}

	public static class TA_UnableToAttack extends NewTurnAction {
		Pokemon _target;

		public TA_UnableToAttack(NewBattleScreen nbs, Pokemon target,
				String who, int status) {
			super(nbs, "", "");
			_target = target;
			switch (status) {
			case 1:
				this.setDialogue(who + target.getName() + " is fast asleep!",
						"");
				break;
			case 3:
				this.setDialogue(who + target.getName()
						+ " is fully paralyzed!", "");
				break;
			case 5:
				this.setDialogue(who + target.getName() + " is frozen solid!",
						"");
				break;
			}
		}

		public void execute() {
			super.execute();
		}
	}

	public static class TA_ChanceToSwitch extends NewTurnAction {
		public TA_ChanceToSwitch(NewBattleScreen nbs) {
			// ***IF YOU MODIFY THE STRING BELOW, MAKE SURE TO MODIFY the
			// "getLine1" command in NBS as well.******//
			super(nbs, "Would you like to switch Pokemon?",
					"A=Yes          B=No");
		}

		public void execute() {
			super.execute();

			NewTurnAction ntaswitch = new NewTurnAction.TA_SwitchNext(nbs);
			nbs.getActiveTurn().getList().add(
					nbs.getActiveTurn().getCurrentStage() + 1, ntaswitch);
		}
	}

	public static class TA_InduceStatusAilment extends NewTurnAction {
		private Pokemon victim;
		@SuppressWarnings("unused")
		private NewBattleScreen _nbs;
		private int _status;

		public TA_InduceStatusAilment(NewBattleScreen nbs, Pokemon victim,
				String who, int status) {
			super(nbs, "", "");
			this.victim = victim;
			_nbs = nbs;
			_status = status;

			switch (status) {
			case 1:
				this.setDialogue(who + victim.getName() + " fell asleep!", "");
				break;
			case 2:
				this.setDialogue(who + victim.getName() + " was poisoned!", "");
				break;
			case 3:
				this
						.setDialogue(
								who + victim.getName() + " was paralyzed!", "");
				break;
			case 4:
				this.setDialogue(who + victim.getName() + " was burned!", "");
				break;
			case 5:
				this.setDialogue(who + victim.getName() + " was frozen!", "");
				break;
			case 6:
				this.setDialogue(who + victim.getName()
						+ " was badly poisoned!", "");
			}
		}

		public void execute() {
			super.execute();
			SysOut.print("Induce Status");
			// Takes care of itself.
			victim.setStatus(_status);
			
			//TODO - this is where check for status goes
		}
	}

	public static class TA_PSNorBRN extends NewTurnAction {
		private Pokemon victim;
		private NewBattleScreen _nbs;
		private int _status;

		public TA_PSNorBRN(NewBattleScreen nbs, Pokemon victim, int status) {
			super(nbs, "", "");
			this.victim = victim;
			_nbs = nbs;
			_status = status;

			switch (status) {
			case 2:
				this.setDialogue(victim.getName() + " was hurt by poison!", "");
				break;
			case 4:
				this.setDialogue(victim.getName() + " was hurt by burn!", "");
				break;
			case 6:
				this.setDialogue(victim.getName()
						+ " was badly hurt by poison!", "");
			}
		}

		public void execute() {
			super.execute();

			int damage = 0;

			switch (_status) {
			case 2:
				damage = (int) (victim.getMaxHP() / 8.0);
				break;
			case 4:
				damage = (int) (victim.getMaxHP() / 8.0);
				break;
			case 6: // Toxic = badly poisoned
				break;
			}

			_nbs.setHPListenerChange(victim, -damage);

			// victim.setCurrentHP((victim.getCurrentHP())-2);
		}
	}

	public static class TA_AuxDamage extends NewTurnAction {
		private Pokemon predator;
		private Pokemon victim;
		private NewBattleScreen _nbs;
		private String _cause;
		private String _self;
		@SuppressWarnings("unused")
		private String _other;

		public TA_AuxDamage(NewBattleScreen nbs, Pokemon victim,
				Pokemon predator, String self, String cause) {
			super(nbs, "", "");
			this.victim = victim;
			this.predator = predator;
			_nbs = nbs;
			_cause = cause;
			_self = self;
			if (_self == "") {
				_other = "Enemy ";
			} else {
				_other = "";
			}

			if (_cause == "Leech Seed") {
				this.setDialogue("Leech Seed saps " + _self + victim.getName()
						+ "!", "");
			}
			// Add more here, like curse, etc.

		}

		public void execute() {
			super.execute();

			int damage = 0;

			if (_cause == "Leech Seed") {
				damage = (int) (victim.getMaxHP() / 8.0);
				// Sri: Why is this here?
				// Mat: To make it do at least a little damage
				if (damage == 0)
					damage = 1;
				// Absorb from victim
				_nbs.setHPListenerChange(victim, -damage, predator, damage);
				// Give to predator
				// _nbs.setHPListenerChange(predator, damage);
			}

		}
	}

	public static class TA_WildPokemonFaints extends NewTurnAction {
		private NewBattleScreen _nbs;

		public TA_WildPokemonFaints(NewBattleScreen nbs, Pokemon fainter) {
			super(nbs, "Enemy " + fainter.getName() + " fainted!", "");
			_nbs = nbs;
		}

		public void execute() {
			super.execute();
			_nbs.GBSelect=_nbs.GB_FAINT;
			_nbs.GBTimer.start();
			//_nbs.setEnemyVis(false);
			//SysOut.print("Executed");
			SysOut.print(_nbs.acquireFollowingEvent().line1 + "");
		}
	}

	public static class TA_LevelUp extends NewTurnAction {
		private Pokemon subject;
		private Attack temp;
		private NewBattleScreen _nbs;

		public TA_LevelUp(NewBattleScreen nbs, Pokemon leveler) {
			super(nbs, leveler.getName() + " grew to level "
					+ (leveler.getLevel() + 1) + "!", "");
			subject = leveler;
			this._nbs = nbs;
			temp = subject.levelUp();
			subject.setExp(0);
		}

		public void execute() {
			super.execute();

			M2.playFX(M2.LEVEL_UP);

			// Levels up the Pokemon and simultaneously checks to see if it
			// learns a new attack
			_nbs.setSelectedPkmn(subject);
			_nbs.setStatPanelVis(true);

			if (temp != null) {
				if (!subject.hasAttack(temp.getAttackNum())) {
					if (subject.getAttacks().size() < 4) {
						NewTurnAction ntaAttack = new NewTurnAction.TA_DisplayEffect(
								nbs, subject.getName() + " learned "
										+ temp.getName() + "!", "");

						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaAttack);

						subject.addNewAttack(temp);
					} else {
						NewTurnAction ntaShould = new NewTurnAction.TA_LearnNewAttack(
								nbs, subject, temp, -3);
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1,
								ntaShould);

					}
				}
			}

			if (subject._isEvolving) {
				nbs.getEvolutionVector().add(subject);
			}
		}
	}

	public static class TA_LearnNewAttack extends NewTurnAction {
		private Pokemon subject;
		private NewBattleScreen _nbs;
		private Attack newAtk;
		private int page;

		public TA_LearnNewAttack(NewBattleScreen nbs, Pokemon subject,
				Attack newAtk, int page) {
			super(nbs, "", "");
			this._nbs = nbs;
			this.subject = subject;
			this.page = page;
			this.newAtk = newAtk;
		}

		public void execute() {
			if (page == -3) {
				this.setDialogue(subject.getName() + " is trying to learn ",
						newAtk.getName() + "!");
				NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
						_nbs, subject, newAtk, -2);
				_nbs.getActiveTurn().getList().add(
						_nbs.getActiveTurn().getCurrentStage() + 1, nextStep);
			}
			if (page == -2) {
				this.setDialogue("...but " + subject.getName()
						+ " already knows ", "four attacks!");
				NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
						_nbs, subject, newAtk, -1);
				_nbs.getActiveTurn().getList().add(
						_nbs.getActiveTurn().getCurrentStage() + 1, nextStep);
			}
			if (page == -1) {
				_nbs.setForgettingAttack(true);
				_nbs.setCurrentRequest(true);
				this.setDialogue("Should " + subject.getName()
						+ " forget an attack?", "A=Yes     B=No");
				NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
						_nbs, subject, newAtk, 0);
				_nbs.getActiveTurn().getList().add(
						_nbs.getActiveTurn().getCurrentStage() + 1, nextStep);
			}
			if (page == 0) {
				if (_nbs.getDenials() == 1) {
					_nbs.setCurrentRequest(true);
					this.setDialogue("Stop trying to learn " + newAtk.getName()
							+ "?", "A=Yes     B=No");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, 1);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
				} else {
					_nbs.setCurrentRequest(false);
					_nbs.setDenials(0);
					this.setDialogue("Which attack should be forgotten?", "");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, 1);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
				}
			}
			if (page == 1) {
				if (_nbs.getDenials() == 1) {
					this.setDialogue(subject.getName() + " did not learn "
							+ newAtk.getName() + ".", "");
					_nbs.setForgettingAttack(false);
					_nbs.setCurrentRequest(false);
				} else if (_nbs.getDenials() == 2) {
					this.setDialogue(
							subject.getName() + " is trying to learn ", newAtk
									.getName()
									+ "!");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, -2);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
					_nbs.setForgettingAttack(false);
					_nbs.setCurrentRequest(false);
					_nbs.setDenials(0);
				} else {
					this.setDialogue("", "");
					
					//_nbs.setFightingAlly(_nbs.getAlly());
					//_nbs.setAlly(subject);
					_nbs.setAttackMenuVis(true);
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, 2);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
				}
			}
			if (page == 2) {
				_nbs.setAttackMenuVis(false);

				if (subject.getAttacks().get(_nbs.getAttacktoForget()).isHM()) {
					this.setDialogue("HM moves cannot be forgotten!", "");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, 1);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
				} else {
					_nbs.setCurrentRequest(true);
					_nbs.setForgettingAttack(true);
					this.setDialogue("Forget "
							+ subject.getAttacks()
									.get(_nbs.getAttacktoForget()).getName()
							+ "?", "A=Yes     B=No");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, 3);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
				}
			}
			if (page == 3) {
				if (_nbs.getDenials() == 1) {
					_nbs.setCurrentRequest(true);
					this.setDialogue("Stop trying to learn " + newAtk.getName()
							+ "?", "A=Yes     B=No");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, 4);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
				} else {
					_nbs.setCurrentRequest(false);
					this.setDialogue("1....2....and........poof!", "");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, 4);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
				}
			}
			if (page == 4) {
				if (_nbs.getDenials() == 1) {
					this.setDialogue(subject.getName() + " did not learn "
							+ newAtk.getName() + ".", "");
					_nbs.setForgettingAttack(false);
					_nbs.setCurrentRequest(false);
				} else if (_nbs.getDenials() == 2) {
					this.setDialogue(
							subject.getName() + " is trying to learn ", newAtk
									.getName()
									+ "!");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, -2);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
					_nbs.setForgettingAttack(false);
					_nbs.setCurrentRequest(false);
					_nbs.setDenials(0);
				} else {
					this.setDialogue(subject.getName()
							+ " forgot "
							+ subject.getAttacks()
									.get(_nbs.getAttacktoForget()).getName()
							+ "!", "");
					NewTurnAction nextStep = new NewTurnAction.TA_LearnNewAttack(
							_nbs, subject, newAtk, 5);
					_nbs.getActiveTurn().getList().add(
							_nbs.getActiveTurn().getCurrentStage() + 1,
							nextStep);
				}
			}
			if (page == 5) {
				this.setDialogue("And......", subject.getName() + " learned "
						+ newAtk.getName() + "!");
				if(_nbs.getFightingAlly() != null){
					_nbs.setAlly(_nbs.getFightingAlly());
					_nbs.setFightingAlly(null);
				}
				// Learn the new one.
				subject.addNewAttack(newAtk);
				// Forget the old one. This preserves the order.
				subject.getAttacks().remove(nbs.getAttacktoForget());
			}

			super.execute();
		}
	}

	public static class TA_AllyFaints extends NewTurnAction {
		private NewBattleScreen _nbs;

		public TA_AllyFaints(NewBattleScreen nbs, Pokemon fainter) {
			super(nbs, fainter.getName() + " fainted!", "");
			_nbs = nbs;
		}

		public void execute() {
			super.execute();
			
			_nbs.GBSelect=_nbs.GB_FAINT;
			_nbs.GBTimer.start();
			//_nbs.setAllyVis(false);
			_nbs.getAlly().setStatus(0);
			_nbs.setCurrentText("", "");
		}
	}

	public static class TA_SwitchNext extends NewTurnAction {
		private NewBattleScreen _nbs;

		public TA_SwitchNext(NewBattleScreen nbs) {
			super(nbs, "Choose a pokemon to switch", "into battle.");
			_nbs = nbs;
			SysOut.print("Switching?");
		}

		public void execute() {
			SysOut.print("NO EXECUTE!");
			_nbs.setPkmnScreenVis(true);
			super.execute();
		}
	}

	public static class TA_AllyGainsExperience extends NewTurnAction {
		private Pokemon ally, enemy;
		private double fraction;

		public TA_AllyGainsExperience(NewBattleScreen nbs, Pokemon ally,
				Pokemon enemy, double fraction) {
			super(nbs, "", "");

			this.ally = ally;
			this.enemy = enemy;
			this.fraction = fraction;
		}

		public void execute() {
			// ////FIRST: Find the EXP levels gained and needed to progress to
			// the next level.
			// Exp Gained.
			int GainedExp;
			if (nbs.isTrainer()) {
				GainedExp = (int) (enemy.getBaseExp() * enemy.getLevel() / 7
						* 3 / 2 * fraction);
				if (PokePanel2.firstBattle()) {
					GainedExp += 24;
					PokePanel2.setFirstBattle(false);
				}
			} else {
				GainedExp = (int) (enemy.getBaseExp() * enemy.getLevel() / 7 * fraction);
			}

			if (GainedExp == 0)
				GainedExp = 1;

			// Display it.
			this.setDialogue(ally.getName() + " gained " + GainedExp + " EXP.",
					"");

			super.execute();

			// How much needed to get to next level.
			int toNextLevel = NewBattleScreen.getToNextLevel(ally);
			// Multiplier* ((next level)^3 -(previous level)^3)
			// For some reason cubing the terms is being weird and wrong.

			// ////SECOND: Determine the effect of gaining said EXP amount.

			nbs.setXPListenerChange(ally, GainedExp);
			if (ally.getExp() + GainedExp < toNextLevel) {
				// Just add the EXP, no further harm done.
				// ally.incExp(GainedExp);

			} else {
				// Now we have to deal with leveling up, attacks and evolution.
				// Something like this: (copied from previous battlescreen)
				/*
				 * GainedExp = GainedExp - (toNextLevel - ally.getExp()); // Add
				 * Remainder if leveling up ally.setExp(0); ally.levelUp();
				 * 
				 * Check for attacks.Add remaining Exp if not.
				 */
			}
		}
	}

	public static class TA_Struggle extends NewTurnAction {
		private Pokemon _user, _receiver;
		private NewBattleScreen _nbs;

		public TA_Struggle(NewBattleScreen nbs, Pokemon user, Pokemon receiver) {
			super(nbs, user.getName() + " used Struggle!", "");
			_user = user;
			_receiver = receiver;
			_nbs = nbs;
		}

		public void execute() {
			if (_user == _nbs.getEnemy()) {

				_nbs.setEnemyLastAttack(new Attack.Struggle());

				_receiver = _nbs.getAlly();
				if (nbs.isEnemyDisabled()) {
					nbs.decrementEDR();
				}
				if (nbs.isEnemyLightScreened()) {
					nbs.decrementELS();
				}
				if (nbs.isEnemyReflected()) {
					nbs.decrementER();
				}
			} else if (_user == _nbs.getAlly()) {

				_nbs.setAllyLastAttack(new Attack.Struggle());

				_receiver = _nbs.getEnemy();
				if (nbs.isAllyDisabled()) {
					nbs.decrementADR();
				}
				if (nbs.isAllyLightScreened()) {
					nbs.decrementALS();
				}
				if (nbs.isAllyReflected()) {
					nbs.decrementAR();
				}
			}

			super.execute();

			Attack s = new Attack.Struggle();
			int damage = s.getDamage(nbs, _user, _receiver);

			if (_user == nbs.getAlly() && nbs.isEnemyReflected()) {
				damage = (int) (0.5 * damage) + 1;
			} else if (_user == nbs.getEnemy() && nbs.isAllyReflected()) {
				damage = (int) (0.5 * damage) + 1;
			}

			_nbs.setHPListenerChange(_receiver, -1 * damage);

			NewTurnAction recoil = new NewTurnAction.TA_Recoil(_nbs, _user, .5,
					damage);
			_nbs.getActiveTurn().getList().add(
					_nbs.getActiveTurn().getCurrentStage() + 1, recoil);
		}
	}

	public static class TA_Recoil extends NewTurnAction {
		private Pokemon _user;
		private double _percentage;
		private int _damage;

		public TA_Recoil(NewBattleScreen nbs, Pokemon user, double percentage,
				int damage) {
			super(nbs, user.getName() + " was hit with recoil!", "");
			_user = user;
			_percentage = percentage;
			_damage = damage;

		}

		public void execute() {
			super.execute();
			nbs.setHPListenerChange(_user,
					(int) (-1 * _percentage * _damage) - 1);

			if (_user.getStatus() == 2 || _user.getStatus() == 4
					|| _user.getStatus() == 6) {

				NewTurnAction nta = new TA_PSNorBRN(nbs, _user, _user
						.getStatus());
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 2, nta);
			}
		}
	}

	public static class TA_GainHP extends NewTurnAction {
		private Pokemon user;
		private int damage;

		public TA_GainHP(NewBattleScreen nbs, Pokemon u, String s, int d) {
			super(nbs, s, "");
			user = u;
			damage = d;
		}

		public void execute() {
			super.execute();
			if (user.getCurrentHP() < user.getMaxHP()) {
				nbs.setHPListenerChange(user, damage);
			}
		}
	}

	public static class TA_HurtItselfInConfusion extends NewTurnAction {
		private Pokemon _user;
		@SuppressWarnings("unused")
		private double _percentage;
		@SuppressWarnings("unused")
		private int _damage;
		private NewBattleScreen _nbs;

		public TA_HurtItselfInConfusion(NewBattleScreen nbs, Pokemon user,
				String who) {
			super(nbs, who + user.getName() + " hurt itself ", "in confusion!");
			_user = user;
			_nbs = nbs;
		}

		public void execute() {
			super.execute();
			super.execute();
			Attack HIIC = new Attack.HurtItselfInConfusion();
			int damage = HIIC.getDamage(nbs, _user, _user);
			_nbs.setHPListenerChange(_user, -1 * damage);

			if (_user.getStatus() == 2 || _user.getStatus() == 4
					|| _user.getStatus() == 6) {

				NewTurnAction nta = new TA_PSNorBRN(nbs, _user, _user
						.getStatus());
				nbs.getActiveTurn().getList().add(
						nbs.getActiveTurn().getCurrentStage() + 2, nta);
			}
		}
	}

	public static class TA_Effectiveness extends NewTurnAction {
		private static final int SUPER = 0, WEAK = 1, NONE = 2;

		public TA_Effectiveness(NewBattleScreen nbs, int effectType) {
			super(nbs, "", "");
			switch (effectType) {
			case SUPER:
				this.setDialogue("It's super effective!", "");
				break;
			case WEAK:
				this.setDialogue("It's not very effective...", "");
				break;
			case NONE:
				this.setDialogue("It doesn't have any effect.", "");
				break;
			default:
				this.setDialogue("", "");
			}
		}
	}

	// ===========================

	public static class TA_DefeatTrainer extends NewTurnAction {
		private NewBattleScreen _nbs;

		public TA_DefeatTrainer(NewBattleScreen nbs, Player p, Trainer t) {
			super(nbs,
					p.getPlayerName() + " defeated " + t.getTrueName() + "!",
					"");
			_nbs = nbs;
		}

		public void execute() {
			super.execute();

			M2.playBG(M2.BATTLE_VICTORY);

			_nbs.startBattleEnd();
		}
	}

	public static class TA_TrainerDialogue1 extends NewTurnAction {
		public TA_TrainerDialogue1(NewBattleScreen nbs, Trainer t) {
			super(nbs, t.getTrueName() + ": " + t.getDialogue().get(0), "");
		}
	}

	public static class TA_TrainerDialogue2 extends NewTurnAction {
		public TA_TrainerDialogue2(NewBattleScreen nbs, Trainer t) {
			super(nbs, "", "");
			// TODO - Add Vector<String>s back in for multiple lines of dialogue
			if (t.getDialogue() != null && t.getDialogue() != null)
				this.setWrapDialogue(t.getTrueName() + ": "
						+ t.getDefeatDialogue());
		}
	}

	public static class TA_SwitchPokemon1 extends NewTurnAction {
		@SuppressWarnings("unused")
		private Pokemon current, next;
		private NewBattleScreen _nbs;

		public TA_SwitchPokemon1(NewBattleScreen nbs, Pokemon current,
				Pokemon next) {
			super(nbs, "Come back " + current.getName() + "!", "");
			this.current = current;
			this.next = next;

			if (current.getStatus() == 6)
				current.setStatus(2);

			_nbs = nbs;
		}

		public void execute() {
			super.execute();

			_nbs.setPkmnScreenVis(false);

			NewTurnAction nta = new TA_SwitchPokemon2(nbs, next);
			_nbs.getActiveTurn().getList().add(
					nbs.getActiveTurn().getCurrentStage() + 1, nta);
		}
	}

	public static class TA_SwitchPokemon2 extends NewTurnAction {
		private Pokemon next;
		private NewBattleScreen _nbs;

		public TA_SwitchPokemon2(NewBattleScreen nbs, Pokemon next) {
			super(nbs, "", "");
			this.next = next;
			_nbs = nbs;
		}

		public void execute() {
			if (_nbs.getEnemy().getCurrentHP() < _nbs.getEnemy().getMaxHP() / 4.0
					&& nbs.getEnemy().getCurrentHP() != 0)
				this
						.setDialogue(
								"Finish them off, " + next.getName() + "!", "");
			else
				this.setDialogue("Go, " + next.getName() + "!", "");

			super.execute();
			_nbs.resetAllyStages();
			_nbs.setAllyConfusion(false);
			_nbs.setAllyMisty(false);
			_nbs.setAllyLeechSeed(false);
			_nbs.setAllyDug(false);
			_nbs.setAllyFlinch(false);
			_nbs.setAllyFlying(false);
			_nbs.setAllyMinimized(false);
			_nbs.setAllyRaging(false);
			_nbs.setAllyBound(false);
			_nbs.setNextTurnAction(null);
			_nbs.setAllyDisabled(-1);
			_nbs.setAllyDisabledRemaining(0);
			_nbs.setAllyLastAttack(null);
			_nbs.clearAllyTransform();
			_nbs.endAllyMimicry();
			_nbs.setAlly(next);
			_nbs.setAllyVis(true);
			
			//TODO=============
			M2.setBattleCry(next.getDexNum());
			M2.playFX(M2.BATTLE_CRY);
			//=============
		}
	}

	public static class TA_EnemySwitching extends NewTurnAction {
		private Pokemon next;

		public TA_EnemySwitching(NewBattleScreen nbs, Trainer t, Pokemon next) {
			super(nbs, "", "");
			this.setWrapDialogue(t.getName() + " sent out " + next.getName()
					+ "!");
			this.next = next;
		}

		public void execute() {
			super.execute();

			//SysOut.print("Execution: " + next.getName());

			nbs.resetEnemyStages();


			nbs.setNextEnemyTurnAction(null);

			// Resets Confusion
			nbs.setEnemyConfusion(false);
			nbs.setEnemyMisty(false);
			nbs.setEnemyLeechSeed(false);
			nbs.setEnemyDug(false);
			nbs.setEnemyFlinch(false);
			nbs.setEnemyFlying(false);
			nbs.setEnemyMinimized(false);
			nbs.setEnemyRaging(false);
			nbs.setEnemyBound(false);
			nbs.setNextEnemyTurnAction(null);
			nbs.setEnemyDisabled(-1);
			nbs.setEnemyDisabledRemaining(0);
			nbs.setEnemyLastAttack(null);
			nbs.clearEnemyTransform();
			nbs.endEnemyMimicry();
			
			nbs.setEnemy(next);
			nbs.setEnemyVis(true);

			// Clear the list of fought allies.
			nbs.getFoughtBelt().clear();
			
			//TODO=============
			M2.setBattleCry(next.getDexNum());
			M2.playFX(M2.BATTLE_CRY);
			//=============
		}
	}

	public static class TA_ItemPrecursor extends NewTurnAction {
		public int index;

		public TA_ItemPrecursor(NewBattleScreen nbs, Player p, int Index) {
			super(nbs, "", "");
			this.index = Index;
			if (p.getItem(Index).getType() == "Ball") {
				this.setDialogue(p.getPlayerName() + " threw a "
						+ p.getItem(Index).getName() + "!", "");
			}
			if (p.getItem(Index).getType() == "Boost") {
				this.setDialogue(p.getPlayerName() + " used a "
						+ p.getItem(Index).getName() + "!", "");
			}
		}

		public void execute() {
			super.execute();

			NewTurnAction nta = new TA_UseItem(nbs, nbs.getPlayer(), index, nbs
					.getAlly(), nbs.getEnemy());
			nbs.getActiveTurn().getList().add(
					nbs.getActiveTurn().getCurrentStage() + 1, nta);
		}
	}

	public static class TA_UseItem extends NewTurnAction {
		private Pokemon enemy, ally;
		private Item usedItem;
		private String caught;
		@SuppressWarnings("unused")
		private Player p;
		private int index;

		public TA_UseItem(NewBattleScreen nbs, Player p, int Index,
				Pokemon ally, Pokemon enemy) {
			// FIXME: Eventually needs to take in GameBoyScreen to get the real
			// player and belt.
			super(nbs, "", "");
			this.enemy = enemy;
			this.ally = ally;
			this.nbs = nbs;
			this.p = p;
			this.index = Index;
			this.usedItem = p.getItem(index);

			// Only used for Ball or Boost items. This turn action is not used
			// for HP/Revive/Restore or Status items.

			if (usedItem.getType() == "Ball") {
				// If its a trainer battle, end it right there.
				if (nbs.isTrainer()) {
					this.setDialogue("You can't catch",
							"another Trainer's Pokemon!");
				} else {
					double CatchProb = 100 * usedItem.calcProb(usedItem
							.getEffect(), enemy);

					// Calculate Probability of being caught.
					caught = Bernoulli(CatchProb);

					// Was it caught? Set appropriate dialogue.
					if (caught == "Yes") {
						this.setDialogue("You caught a " + enemy.getName()
								+ "!", "");

						// nbs.getActiveTurn()
					} else {
						if (CatchProb > 75) {
							// High probability, but missed
							this.setDialogue("Damn! It was so close too!", "");
						} else {
							// Mid to Low probability, but missed
							this.setDialogue("Wild " + enemy.getName()
									+ " ate the Thesis!", "");
						}
					}

					// Subtract one from inventory, regardless of caught or not.
					p.getItem(index).setRemaining(
							p.getItem(index).getRemaining() - 1);

				}
			}

			if (usedItem.getType() == "Boost") {
				String boosted = "";
				String boosted2 = "";
				int itemEffect = usedItem.getEffect();
				switch (itemEffect) {
				case 1:
					boosted = "'s accuracy rose!";
					break;
				case 2:
					boosted = "'s attack rose!";
					break;
				case 3:
					boosted = "'s defense rose!";
					break;
				case 4:
					boosted = "'s special attack rose!";
					break;
				case 5:
					boosted = "'s speed rose!";
					break;
				case 6:
					boosted = "'s focus rose!";
					break;
				case 7:
					boosted = " is guarded ";
					boosted2 = "from stat reductions!";
					break;
				}

				// Subtract one from inventory.
				p.getItem(index).setRemaining(
						p.getItem(index).getRemaining() - 1);

				this.setDialogue(ally.getName() + boosted, boosted2);
			}
		}

		public void execute() {
			super.execute();
			if (usedItem.getType() == "Ball") {
				if (caught == "Yes") {
					// If the person already has over 6 active, set belt to 7.
					int done = 0;

					boolean notAlreadyCaught = false;
					if (nbs.getGBS().getPlayer().getPokedex() != null) {
						notAlreadyCaught = nbs.getGBS().getPlayer()
								.getPokedex().addToCatchList(enemy);
					}

					if (nbs.getPlayer().getAllActive().size() < 6 && done < 1) {
						enemy
								.setBelt(nbs.getPlayer().getAllActive().size() + 1);

						// Pokemon enemy2 = enemy;
						Pokemon enemy2 = Pokemon.clone(enemy);
						Pokemon.generateIV(enemy2);
						Pokemon.generateNewStats(enemy2);
						nbs.getPlayer().getAllActive().add(enemy2);
						done = done + 1;

					}

					if (notAlreadyCaught) {
						NewTurnAction nta = new NewTurnAction.TA_DisplayEffect(
								nbs, enemy.getName() + "'s data was added",
								"to the Pokedex.");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + 1, nta);
					}

					// If still not added yet, add it last.
					// The newest Pokemon will be at the last slot of each of
					// these vectors.

					int extra = 1;
					if (notAlreadyCaught) {
						extra++;
					}

					// TODO: Have additional dialogue for
					// "new pokemon was sent to PC."
					if (nbs.getPlayer().getAllActive().size() >= 6 && done < 1) {
						enemy.setBelt(7);
						Pokemon enemy2 = enemy;
						Pokemon.generateIV(enemy2);
						Pokemon.generateNewStats(enemy2);
						nbs.getPlayer().getAllPC().add(enemy2);

						// Say that it was sent to PC

						NewTurnAction nta = new TA_DisplayEffect(nbs, enemy
								.getName()
								+ " was sent to the PC.", "");
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								nta);

						// End the Battle
						NewTurnAction nta2 = new TA_EndBattle(nbs);
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra
										+ 1, nta2);
					}

					else {
						NewTurnAction nta2 = new TA_EndBattle(nbs);
						nbs.getActiveTurn().getList().add(
								nbs.getActiveTurn().getCurrentStage() + extra,
								nta2);
					}

					// Once done, re-sort everyone.
					// FIXME: gbs.getPlayer().sortActivePokemon();
				}
			}

			if (usedItem.getType() == "Boost") {
				int itemEffect = usedItem.getEffect();
				switch (itemEffect) {
				case 1:
					// accuracy
					if (nbs.allyAccStage < 6) {
						nbs.allyAccStage = nbs.allyAccStage + 1;
					}
					break;
				case 2:
					// attack
					if (nbs.allyAtkStage < 6) {
						nbs.allyAtkStage = nbs.allyAtkStage + 1;
					}
					break;
				case 3:
					// defense
					if (nbs.allyDefStage < 6) {
						nbs.allyDefStage = nbs.allyDefStage + 1;
					}
					break;
				case 4:
					// special
					if (nbs.allySpAtkStage < 6) {
						nbs.allySpAtkStage = nbs.allySpAtkStage + 1;
					}
					break;
				case 5:
					// speed
					if (nbs.allySpdStage < 6) {
						nbs.allySpdStage = nbs.allySpdStage + 1;
					}
					break;
				case 6:
					// focus
					if (!ally.isDireHit()) {
						ally.setDireHitTrue();
					}
					break;
				case 7:
					// focus
					if (!ally.isGuardSpec()) {
						ally.setGuardSpecTrue();
					}
					break;
				}
			}

		}
	}

	public static class TA_GetMoney extends NewTurnAction {
		@SuppressWarnings("unused")
		private Trainer t;

		public TA_GetMoney(NewBattleScreen nbs, Player p, Trainer t) {
			super(nbs, p.getPlayerName() + " got $" + t.getMoney()
					+ " for winning!", "");
			nbs.getPlayer().setMoney(nbs.getPlayer().getMoney() + t.getMoney());
		}
	}

	public static class TA_EndBattle extends NewTurnAction {
		public TA_EndBattle(NewBattleScreen nbs) {
			super(nbs, "", "");
		}

		public void execute() {
			// nbs.restore(); //Returns all stats to normal

			nbs.getGBS().setCurrentPanel(nbs.getBattleLoc());
			if (nbs.getGBS().getSurf()) {
				M2.playBG(M2.SURF);
			} else {
				if (!nbs.getGBS().getMode())
					M2.playBG(nbs.getGBS().getCurrentPanel().song);
				else
					M2.playBG(M2.BIKE);
			}
			nbs.resetBattle();
			nbs.getGBS().repaint();
			nbs.setAllyVis(false);
			nbs.setEnemyVis(false);
			nbs.setPkmnScreenVis(false);
			nbs.resetAllMenus();
			if (nbs.getEvolutionVector().size() == 0) {
				if (nbs.isTrainer() || nbs.getGBS().getCurrentPanel().legendary) {
					nbs.getGBS().getCurrentPanel().legendary = false;
					nbs.getGBS().getCurrentPanel().afterBattle();
					// System.out.println("Step 1 is called.");
				}
			} else {
				SysOut.print("WOOO EVOLVING!!!");
				nbs.getGBS().getCurrentPanel().setEvolutionScreenVisible(true);
				nbs.getGBS().getCurrentPanel()._busy = true;

			}
		}
	}

	public static class TA_Blackout1 extends NewTurnAction {
		public TA_Blackout1(NewBattleScreen nbs, Player p) {
			super(nbs, p.getPlayerName() + " is out of usable Pokemon!", "");
		}
	}

	public static class TA_Blackout2 extends NewTurnAction {
		public TA_Blackout2(NewBattleScreen nbs, Player p) {
			super(nbs, p.getPlayerName() + " returned to work...", "");
		}
	}

	public static class TA_Blackout3 extends NewTurnAction {
		public TA_Blackout3(NewBattleScreen nbs) {
			super(nbs, "", "");
		}

		public void execute() {

			if (PokePanel2.firstBattle()) {
				nbs.getGBS().setCurrentPanel(nbs.getGBS().MEIK_ROOM);
				nbs.getGBS().getCurrentPanel().blackout();
				nbs.resetBattle();
				nbs.getGBS().getPlayer()
						.setPkmnCenter(nbs.getGBS().KEENEY_ROOM);
				// nbs.getGBS().getCurrentPanel()._fadeToBlack.start();
				//
				// Already auto heals pokemon from resetBattle Method.
			} else {
				nbs.getGBS().setCurrentPanel(
						nbs.getGBS().getPlayer().getPkmnCenter());
				nbs.getGBS().getCurrentPanel().blackout();
				nbs.resetBattle();
				// nbs.getGBS().getCurrentPanel()._fadeToBlack.start();
			}
		}

	}
}
