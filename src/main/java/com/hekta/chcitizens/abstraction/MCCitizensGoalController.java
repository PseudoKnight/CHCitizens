package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;

/**
 *
 * @author Hekta
 */
public interface MCCitizensGoalController extends AbstractionObject {

	public void cancelCurrentExecution();

	public void clear();

	public boolean isExecutingGoal();

	public boolean isPaused();
	public void setPaused(boolean isPaused);
}