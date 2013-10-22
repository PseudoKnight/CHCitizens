package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.MCLivingEntity;

/**
 *
 * @author Hekta
 */
public interface MCCitizensAttackStrategy {

	public boolean handle(MCLivingEntity attacker, MCLivingEntity target);
}