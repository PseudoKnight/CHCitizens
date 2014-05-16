package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.NavigatorParameters;

import com.hekta.chcitizens.abstraction.MCCitizensNavigatorParameters;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensNavigatorParameters implements MCCitizensNavigatorParameters {

	private final NavigatorParameters _parameters;

	public BukkitMCCitizensNavigatorParameters(NavigatorParameters parameters) {
		_parameters = parameters;
	}

	@Override
	public NavigatorParameters getHandle() {
		return _parameters;
	}

	@Override
	public boolean getAvoidWater() {
		return _parameters.avoidWater();
	}

	@Override
	public void setAvoidWater(boolean avoidWater) {
		_parameters.avoidWater(avoidWater);
	}

	@Override
	public float getSpeed() {
		return _parameters.speed();
	}

	@Override
	public float getBaseSpeed() {
		return _parameters.baseSpeed();
	}

	@Override
	public void setBaseSpeed(float speed) {
		_parameters.baseSpeed(speed);
	}

	@Override
	public float getSpeedModifier() {
		return _parameters.speedModifier();
	}

	@Override
	public void setSpeedModifier(float modifier) {
		_parameters.speedModifier(modifier);
	}

	@Override
	public double getDistanceMargin() {
		return _parameters.distanceMargin();
	}

	@Override
	public void setDistanceMargin(double distanceMargin) {
		_parameters.distanceMargin(distanceMargin);
	}

	@Override
	public float getRange() {
		return _parameters.range();
	}

	@Override
	public void setRange(float range) {
		_parameters.range(range);
	}

	@Override
	public int getStationaryTicks() {
		return _parameters.stationaryTicks();
	}

	@Override
	public void setStationaryTicks(int ticks) {
		_parameters.stationaryTicks(ticks);
	}
}