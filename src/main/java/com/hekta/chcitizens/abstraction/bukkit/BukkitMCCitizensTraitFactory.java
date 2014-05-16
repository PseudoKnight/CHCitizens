package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.trait.TraitFactory;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensTraitFactory;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensTraitFactory implements MCCitizensTraitFactory {

	private final TraitFactory _factory;

	public BukkitMCCitizensTraitFactory(TraitFactory traitFactory) {
		_factory = traitFactory;
	}

	@Override
	public TraitFactory getHandle() {
		return _factory;
	}

	@Override
	public void addDefaultTraits(MCCitizensNPC npc) {
		_factory.addDefaultTraits(((BukkitMCCitizensNPC) npc).getHandle());
	}
}