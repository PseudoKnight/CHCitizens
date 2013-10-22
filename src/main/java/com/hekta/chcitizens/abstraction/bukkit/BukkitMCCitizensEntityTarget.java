package com.hekta.chcitizens.abstraction.bukkit;

import org.bukkit.entity.LivingEntity;

import net.citizensnpcs.api.ai.EntityTarget;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;

import com.hekta.chcitizens.abstraction.MCCitizensEntityTarget;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensEntityTarget implements MCCitizensEntityTarget {

	EntityTarget et;

	public BukkitMCCitizensEntityTarget(EntityTarget target) {
		this.et = target;
	}

	public EntityTarget getConcrete() {
		return et;
	}

	public MCLivingEntity getTarget() {
		return new BukkitMCLivingEntity((LivingEntity) et.getTarget());
	}

	public boolean isAggressive() {
		return et.isAggressive();
	}
}