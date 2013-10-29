package com.hekta.chcitizens.abstraction.bukkit;

import org.bukkit.plugin.Plugin;

import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Owner;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.bukkit.BukkitMCPlugin;

import com.hekta.chcitizens.abstraction.CHCitizensConvertor;
import com.hekta.chcitizens.abstraction.MCCitizensPlugin;
import com.hekta.chcitizens.abstraction.MCCitizensTrait;
import com.hekta.chcitizens.abstraction.bukkit.traits.BukkitMCCitizensOwner;
import com.hekta.chcitizens.annotations.CHCitizensConvert;

/**
 *
 * @author Hekta
 */
@CHCitizensConvert(type=Implementation.Type.BUKKIT)
public class CHCitizensBukkitConvertor implements CHCitizensConvertor {

	public MCCitizensPlugin getCitizensPlugin() {
		Plugin plugin = ((BukkitMCPlugin) com.laytonsmith.commandhelper.CommandHelperPlugin.myServer.getPluginManager().getPlugin("Citizens")).getPlugin();
		if (plugin != null) {
			return new BukkitMCCitizensPlugin((CitizensPlugin) plugin);
		} else {
			return null;
		}
	}

	public MCCitizensTrait getCorrectTrait(MCCitizensTrait trait) {
		String name = trait.getName();
		if (name.equals("owner")) {
			return new BukkitMCCitizensOwner((Owner) ((BukkitMCCitizensTrait) trait).getConcrete());
		} else {
			return trait;
		}
	}
}