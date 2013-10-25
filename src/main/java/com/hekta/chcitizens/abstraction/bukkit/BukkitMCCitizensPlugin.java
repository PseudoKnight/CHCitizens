package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.CitizensPlugin;

import com.laytonsmith.abstraction.bukkit.BukkitMCPlugin;

import com.hekta.chcitizens.abstraction.MCCitizensPlugin;
import com.hekta.chcitizens.abstraction.MCCitizensNPCRegistry;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechFactory;
import com.hekta.chcitizens.abstraction.MCCitizensTraitFactory;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensPlugin extends BukkitMCPlugin implements MCCitizensPlugin {

	CitizensPlugin cp;

	public BukkitMCCitizensPlugin(CitizensPlugin plugin) {
		super(plugin);
		this.cp = plugin;
	}

	public CitizensPlugin getConcrete() {
		return cp;
	}

	public MCCitizensNPCRegistry getNPCRegistry() {
		return new BukkitMCCitizensNPCRegistry(cp.getNPCRegistry());
	}

	public MCCitizensSpeechFactory getSpeechFactory() {
		return new BukkitMCCitizensSpeechFactory(cp.getSpeechFactory());
	}

	public MCCitizensTraitFactory getTraitFactory() {
		return new BukkitMCCitizensTraitFactory(cp.getTraitFactory());
	}
}