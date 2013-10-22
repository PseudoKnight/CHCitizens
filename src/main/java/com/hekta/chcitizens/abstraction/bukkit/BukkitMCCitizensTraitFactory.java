package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.trait.TraitFactory;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensTraitFactory;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensTraitFactory implements MCCitizensTraitFactory {

	TraitFactory tf;

	public BukkitMCCitizensTraitFactory(TraitFactory traitFactory) {
		this.tf = traitFactory;
	}

	public TraitFactory getConcrete() {
		return tf;
	}

	public void addDefaultTraits(MCCitizensNPC npc) {
		tf.addDefaultTraits(((BukkitMCCitizensNPC) npc).getConcrete());
	}
}