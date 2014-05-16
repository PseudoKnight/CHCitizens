package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNavigatorParameters extends AbstractionObject {

	public boolean getAvoidWater();
	public void setAvoidWater(boolean avoidWater);

	public float getSpeed();
	public float getBaseSpeed();
	public void setBaseSpeed(float speed);
	public float getSpeedModifier();
	public void setSpeedModifier(float modifier);

	public double getDistanceMargin();
	public void setDistanceMargin(double distanceMargin);

	public float getRange();
	public void setRange(float range);

	public int getStationaryTicks();
	public void setStationaryTicks(int ticks);
}