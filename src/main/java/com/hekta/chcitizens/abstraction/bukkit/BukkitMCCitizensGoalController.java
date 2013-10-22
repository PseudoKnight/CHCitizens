package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.GoalController;

import com.hekta.chcitizens.abstraction.MCCitizensGoalController;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensGoalController implements MCCitizensGoalController {

	GoalController gc;

	public BukkitMCCitizensGoalController(GoalController goalController) {
		this.gc = goalController;
	}

	public GoalController getConcrete() {
		return gc;
	}

	public void cancelCurrentExecution() {
		gc.cancelCurrentExecution();
	}

	public void clear() {
		gc.clear();
	}

	public boolean isExecutingGoal() {
		return gc.isExecutingGoal();
	}

	public boolean isPaused() {
		return gc.isPaused();
	}

	public void setPaused(boolean isPaused) {
		gc.setPaused(isPaused);
	}
}