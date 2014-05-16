package com.hekta.chcitizens.abstraction.bukkit;

import org.bukkit.Location;

import net.citizensnpcs.api.ai.EntityTarget;
import net.citizensnpcs.api.ai.Navigator;

import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;

import com.hekta.chcitizens.abstraction.MCCitizensEntityTarget;
import com.hekta.chcitizens.abstraction.MCCitizensNavigator;
import com.hekta.chcitizens.abstraction.MCCitizensNavigatorParameters;
import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.enums.MCCitizensTargetType;
import com.hekta.chcitizens.abstraction.enums.bukkit.BukkitMCCitizensTargetType;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensNavigator implements MCCitizensNavigator {

	private final Navigator _navigator;

	public BukkitMCCitizensNavigator(Navigator navigator) {
		_navigator = navigator;
	}

	@Override
	public Navigator getHandle() {
		return _navigator;
	}

	@Override
	public MCCitizensNavigatorParameters getDefaultParameters() {
		return new BukkitMCCitizensNavigatorParameters(_navigator.getDefaultParameters());
	}

	@Override
	public MCCitizensNavigatorParameters getLocalParameters() {
		return new BukkitMCCitizensNavigatorParameters(_navigator.getLocalParameters());
	}

	@Override
	public MCCitizensEntityTarget getEntityTarget() {
		EntityTarget target = _navigator.getEntityTarget();
		if (target != null) {
			return new BukkitMCCitizensEntityTarget(target);
		} else {
			return null;
		}
	}

	@Override
	public MCLocation getTargetAsLocation() {
		Location location = _navigator.getTargetAsLocation();
		if (location != null) {
			return new BukkitMCLocation(location);
		} else {
			return null;
		}
	}

	@Override
	public MCCitizensTargetType getTargetType() {
		return BukkitMCCitizensTargetType.getConvertor().getAbstractedEnum(_navigator.getTargetType());
	}

	@Override
	public void setTarget(MCEntity target, boolean isAggressive) {
		_navigator.setTarget(((BukkitMCEntity) target).getHandle(), isAggressive);
	}

	@Override
	public void setTarget(MCLocation target) {
		_navigator.setTarget(((BukkitMCLocation) target).asLocation());
	}

	@Override
	public boolean isNavigating() {
		return _navigator.isNavigating();
	}

	@Override
	public void cancelNavigation() {
		_navigator.cancelNavigation();
	}

	@Override
	public MCCitizensNPC getNPC() {
		return new BukkitMCCitizensNPC(_navigator.getNPC());
	}
}