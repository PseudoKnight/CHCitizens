package com.hekta.chcitizens.abstraction.traits;

import com.hekta.chcitizens.abstraction.MCCitizensTrait;

/**
 *
 * @author Hekta
 */
public interface MCCitizensOwner extends MCCitizensTrait {

	public String getOwner();
	public void setOwner(String owner);
}