package com.hekta.chcitizens.abstraction;

/**
 *
 * @author Hekta
 */
public interface MCCitizensGoalController {

	public void cancelCurrentExecution();

	public void clear();

	public boolean isExecutingGoal();

	public boolean isPaused();
	public void setPaused(boolean isPaused);
}