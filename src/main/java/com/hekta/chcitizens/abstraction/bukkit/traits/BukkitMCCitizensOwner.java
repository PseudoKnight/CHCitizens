package com.hekta.chcitizens.abstraction.bukkit.traits;

import net.citizensnpcs.api.trait.trait.Owner;

import com.hekta.chcitizens.abstraction.bukkit.BukkitMCCitizensTrait;
import com.hekta.chcitizens.abstraction.traits.MCCitizensOwner;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensOwner extends BukkitMCCitizensTrait implements MCCitizensOwner {

	Owner o;

	public BukkitMCCitizensOwner(Owner owner) {
		super(owner);
		this.o = owner;
	}

	@Override
	public Owner getConcrete() {
		return o;
	}

	public String getOwner() {
		return o.getOwner();
	}

	public void setOwner(String owner) {
		o.setOwner(owner);
	}
}