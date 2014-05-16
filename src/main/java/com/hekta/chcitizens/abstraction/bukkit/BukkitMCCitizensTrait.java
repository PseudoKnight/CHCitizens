package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.trait.Trait;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensTrait;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensTrait implements MCCitizensTrait {

	private final Trait _trait;

	public BukkitMCCitizensTrait(Trait trait) {
		_trait = trait;
	}

	@Override
	public Trait getHandle() {
		return _trait;
	}

	@Override
	public String getName() {
		return _trait.getName();
	}

	@Override
	public MCCitizensNPC getNPC() {
		return new BukkitMCCitizensNPC(_trait.getNPC());
	}
}