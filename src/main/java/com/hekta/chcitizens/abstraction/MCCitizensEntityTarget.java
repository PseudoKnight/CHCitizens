package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCEntity;

/**
 *
 * @author Hekta
 */
public interface MCCitizensEntityTarget extends AbstractionObject {

	public MCEntity getTarget();

	public boolean isAggressive();
}