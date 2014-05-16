package com.hekta.chcitizens.abstraction.traits;

import com.laytonsmith.abstraction.MCOfflinePlayer;

import com.hekta.chcitizens.abstraction.MCCitizensTrait;

/**
 *
 * @author Hekta
 */
public interface MCCitizensOwner extends MCCitizensTrait {

	public MCOfflinePlayer getOwner();
	public void setOwner(MCOfflinePlayer owner);
}