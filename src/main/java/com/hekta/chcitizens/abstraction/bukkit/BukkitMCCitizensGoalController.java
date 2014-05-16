package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.GoalController;

import com.hekta.chcitizens.abstraction.MCCitizensGoalController;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensGoalController implements MCCitizensGoalController {

	private final GoalController _controller;

	public BukkitMCCitizensGoalController(GoalController goalController) {
		_controller = goalController;
	}

	@Override
	public GoalController getHandle() {
		return _controller;
	}

	@Override
	public void cancelCurrentExecution() {
		_controller.cancelCurrentExecution();
	}

	@Override
	public void clear() {
		_controller.clear();
	}

	@Override
	public boolean isExecutingGoal() {
		return _controller.isExecutingGoal();
	}

	@Override
	public boolean isPaused() {
		return _controller.isPaused();
	}

	@Override
	public void setPaused(boolean isPaused) {
		_controller.setPaused(isPaused);
	}
}