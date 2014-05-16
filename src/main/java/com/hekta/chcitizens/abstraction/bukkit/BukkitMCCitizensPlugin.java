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

	private final CitizensPlugin _citizens;

	public BukkitMCCitizensPlugin(CitizensPlugin plugin) {
		super(plugin);
		_citizens = plugin;
	}

	@Override
	public CitizensPlugin getHandle() {
		return _citizens;
	}

	@Override
	public MCCitizensNPCRegistry getNPCRegistry() {
		return new BukkitMCCitizensNPCRegistry(_citizens.getNPCRegistry());
	}

	@Override
	public MCCitizensSpeechFactory getSpeechFactory() {
		return new BukkitMCCitizensSpeechFactory(_citizens.getSpeechFactory());
	}

	@Override
	public MCCitizensTraitFactory getTraitFactory() {
		return new BukkitMCCitizensTraitFactory(_citizens.getTraitFactory());
	}
}