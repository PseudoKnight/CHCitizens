package com.hekta.chcitizens.abstraction.bukkit;

import com.hekta.chcitizens.CHCitizens;
import com.hekta.chcitizens.abstraction.CHCitizensConvertor;
import com.hekta.chcitizens.abstraction.MCCitizensPlugin;
import com.hekta.chcitizens.abstraction.MCCitizensTrait;
import com.hekta.chcitizens.abstraction.bukkit.traits.BukkitMCCitizensOwner;
import com.hekta.chcitizens.annotations.CHCitizensConvert;
import com.laytonsmith.abstraction.Implementation;

import com.laytonsmith.commandhelper.CommandHelperPlugin;
import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Hekta
 */
@CHCitizensConvert(type=Implementation.Type.BUKKIT)
public class CHCitizensBukkitConvertor implements CHCitizensConvertor {

	@Override
	public MCCitizensPlugin getCitizens() {
		Plugin plugin = (Plugin) CommandHelperPlugin.myServer.getPluginManager().getPlugin(CHCitizens.CITIZENS_NAME).getHandle();
		if (plugin != null) {
			return new BukkitMCCitizensPlugin((CitizensPlugin) plugin);
		} else {
			return null;
		}
	}

	@Override
	public MCCitizensTrait getCorrectTrait(MCCitizensTrait trait) {
		String name = trait.getName();
		if (name.equals("owner")) {
			return new BukkitMCCitizensOwner((Owner) trait.getHandle());
		} else {
			return trait;
		}
	}
}