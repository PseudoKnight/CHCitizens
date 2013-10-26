package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.AttackStrategy;
import net.citizensnpcs.api.ai.NavigatorParameters;

import com.hekta.chcitizens.abstraction.MCCitizensNavigatorParameters;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensNavigatorParameters implements MCCitizensNavigatorParameters {

	NavigatorParameters np;

	public BukkitMCCitizensNavigatorParameters(NavigatorParameters parameters) {
		this.np = parameters;
	}

	public NavigatorParameters getConcrete() {
		return np;
	}

	public boolean getAvoidWater() {
		return np.avoidWater();
	}

	public void setAvoidWater(boolean avoidWater) {
		np.avoidWater(avoidWater);
	}

	public float getSpeed() {
		return np.speed();
	}

	public float getBaseSpeed() {
		return np.baseSpeed();
	}

	public void setBaseSpeed(float speed) {
		np.baseSpeed(speed);
	}

	public float getSpeedModifier() {
		return np.speedModifier();
	}

	public void setSpeedModifier(float modifier) {
		np.speedModifier(modifier);
	}

	public double getDistanceMargin() {
		return np.distanceMargin();
	}

	public void setDistanceMargin(double distanceMargin) {
		np.distanceMargin(distanceMargin);
	}

	public float getRange() {
		return np.range();
	}

	public void setRange(float range) {
		np.range(range);
	}

	public int getStationaryTicks() {
		return np.stationaryTicks();
	}

	public void setStationaryTicks(int ticks) {
		np.stationaryTicks(ticks);
	}
}