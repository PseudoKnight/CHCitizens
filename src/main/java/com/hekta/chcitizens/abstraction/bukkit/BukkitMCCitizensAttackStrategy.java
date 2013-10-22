package com.hekta.chcitizens.abstraction.bukkit;

import org.bukkit.entity.LivingEntity;

import net.citizensnpcs.api.ai.AttackStrategy;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;

import com.hekta.chcitizens.abstraction.MCCitizensAttackStrategy;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensAttackStrategy implements MCCitizensAttackStrategy {

	AttackStrategy as;

	public BukkitMCCitizensAttackStrategy(AttackStrategy strategy) {
		this.as = strategy;
	}

	public AttackStrategy getConcrete() {
		return as;
	}

	public boolean handle(MCLivingEntity attacker, MCLivingEntity target) {
		return as.handle(((BukkitMCLivingEntity) attacker).asLivingEntity(), ((BukkitMCLivingEntity) target).asLivingEntity());
	}
}