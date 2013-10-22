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
public class BukkitMCCitizensPlugin implements MCCitizensPlugin {

	CitizensPlugin p;

	public BukkitMCCitizensPlugin() {
		try {
			p = (CitizensPlugin) ((BukkitMCPlugin) com.laytonsmith.commandhelper.CommandHelperPlugin.myServer.getPluginManager().getPlugin("Citizens")).getPlugin();
		} catch (Exception exception) {
			p = null;
		}
	}

	public CitizensPlugin getConcrete() {
		return p;
	}

	public MCCitizensPlugin get() {
		if (p != null) {
			return this;
		} else {
			return null;
		}
	}

	public MCCitizensNPCRegistry getNPCRegistry() {
		return new BukkitMCCitizensNPCRegistry(p.getNPCRegistry());
	}

	public MCCitizensSpeechFactory getSpeechFactory() {
		return new BukkitMCCitizensSpeechFactory(p.getSpeechFactory());
	}

	public MCCitizensTraitFactory getTraitFactory() {
		return new BukkitMCCitizensTraitFactory(p.getTraitFactory());
	}
}