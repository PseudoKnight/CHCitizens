package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.MCLivingEntity;

/**
 *
 * @author Hekta
 */
public interface MCCitizensEntityTarget {

	public MCLivingEntity getTarget();

	public boolean isAggressive();
}