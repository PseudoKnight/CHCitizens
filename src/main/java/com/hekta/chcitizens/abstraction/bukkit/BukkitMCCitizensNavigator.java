package com.hekta.chcitizens.abstraction.bukkit;

import org.bukkit.entity.Entity;
import org.bukkit.Location;

import net.citizensnpcs.api.ai.EntityTarget;
import net.citizensnpcs.api.ai.Navigator;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;
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

	Navigator n;

	public BukkitMCCitizensNavigator(Navigator navigator) {
		this.n = navigator;
	}

	public Navigator getConcrete() {
		return n;
	}

	public MCCitizensNavigatorParameters getDefaultParameters() {
		return new BukkitMCCitizensNavigatorParameters(n.getDefaultParameters());
	}

	public MCCitizensNavigatorParameters getLocalParameters() {
		return new BukkitMCCitizensNavigatorParameters(n.getLocalParameters());
	}

	public MCCitizensEntityTarget getEntityTarget() {
		EntityTarget target = n.getEntityTarget();
		if (target != null) {
			return new BukkitMCCitizensEntityTarget(target);
		} else {
			return null;
		}
	}

	public MCLocation getTargetAsLocation() {
		Location location = n.getTargetAsLocation();
		if (location != null) {
			return new BukkitMCLocation(location);
		} else {
			return null;
		}
	}

	public MCCitizensTargetType getTargetType() {
		return BukkitMCCitizensTargetType.getConvertor().getAbstractedEnum(n.getTargetType());
	}

	public void setTarget(MCLivingEntity target, boolean isAggressive) {
		n.setTarget(((BukkitMCLivingEntity) target).asLivingEntity(), isAggressive);
	}

	public void setTarget(MCLocation target) {
		n.setTarget(((BukkitMCLocation) target).asLocation());
	}

	public boolean isNavigating() {
		return n.isNavigating();
	}

	public void cancelNavigation() {
		n.cancelNavigation();
	}

	public MCCitizensNPC getNPC() {
		return new BukkitMCCitizensNPC(n.getNPC());
	}
}