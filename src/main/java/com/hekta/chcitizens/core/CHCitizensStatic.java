package com.hekta.chcitizens.core;

import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensNPCRegistry;
import com.hekta.chcitizens.abstraction.MCCitizensPlugin;

/**
 *
 * @author Hekta
 */
public final class CHCitizensStatic {

	public static MCCitizensPlugin getCitizensPlugin(Target t) {
		MCCitizensPlugin plugin = com.hekta.chcitizens.extension.CHCitizensExtension.citizensPlugin;
		if (plugin != null) {
			return plugin;
		} else {
			throw new ConfigRuntimeException("Needed plugin Citizens not found.", ExceptionType.InvalidPluginException, t);
		}
	}

	public static MCCitizensNPCRegistry getNPCRegistry(Target t) {
		MCCitizensNPCRegistry registry = com.hekta.chcitizens.extension.CHCitizensExtension.npcRegistry;
		if (registry != null) {
			return registry;
		} else {
			throw new ConfigRuntimeException("Needed plugin Citizens not found.", ExceptionType.InvalidPluginException, t);
		}
	}

	public static MCCitizensNPC getNPC(int id, Target t) {
		MCCitizensNPC npc = getNPCRegistry(t).getNPCById(id);
		if (npc != null) {
			return npc;
		} else {
			throw new ConfigRuntimeException("There is no existing NPC with this id (" + id + ").", ExceptionType.NotFoundException, t);
		}
	}
}