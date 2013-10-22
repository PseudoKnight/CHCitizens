package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.trait.Trait;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensTrait;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensTrait implements MCCitizensTrait {

	Trait t;

	public BukkitMCCitizensTrait(Trait trait) {
		this.t = trait;
	}

	public Trait getConcrete() {
		return t;
	}

	public String getName() {
		return t.getName();
	}

	public MCCitizensNPC getNPC() {
		return new BukkitMCCitizensNPC(t.getNPC());
	}
}