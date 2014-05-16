package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCEntity;

/**
 *
 * @author Hekta
 */
public interface MCCitizensTalkable extends AbstractionObject {

	public MCEntity getEntity();

	public String getName();
}