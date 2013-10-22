package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.MCLocation;

import com.hekta.chcitizens.abstraction.enums.MCCitizensTargetType;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNavigator {

	public MCCitizensNavigatorParameters getDefaultParameters();
	public MCCitizensNavigatorParameters getLocalParameters();

	public MCCitizensEntityTarget getEntityTarget();
	public MCLocation getTargetAsLocation();
	public MCCitizensTargetType getTargetType();
	public void setTarget(MCLivingEntity target, boolean isAggressive);
	public void setTarget(MCLocation target);

	public boolean isNavigating();
	public void cancelNavigation();

	public MCCitizensNPC getNPC();
}