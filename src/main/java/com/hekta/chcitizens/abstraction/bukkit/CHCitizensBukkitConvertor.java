package com.hekta.chcitizens.abstraction.bukkit;

import org.bukkit.plugin.Plugin;

import net.citizensnpcs.api.CitizensPlugin;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.bukkit.BukkitMCPlugin;

import com.hekta.chcitizens.abstraction.CHCitizensConvertor;
import com.hekta.chcitizens.abstraction.MCCitizensPlugin;
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
}