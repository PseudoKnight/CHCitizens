package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.EntityTarget;

import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.BukkitConvertor;

import com.hekta.chcitizens.abstraction.MCCitizensEntityTarget;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensEntityTarget implements MCCitizensEntityTarget {

	private final EntityTarget _target;

	public BukkitMCCitizensEntityTarget(EntityTarget target) {
		_target = target;
	}

	@Override
	public EntityTarget getHandle() {
		return _target;
	}

	@Override
	public MCEntity getTarget() {
		return BukkitConvertor.BukkitGetCorrectEntity(_target.getTarget());
	}

	@Override
	public boolean isAggressive() {
		return _target.isAggressive();
	}
}