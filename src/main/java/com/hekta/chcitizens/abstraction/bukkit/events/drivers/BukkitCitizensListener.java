package com.hekta.chcitizens.abstraction.bukkit.events.drivers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.event.NPCDespawnEvent;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.EventUtils;

import com.hekta.chcitizens.abstraction.bukkit.events.BukkitCitizensEvents;
import com.hekta.chcitizens.extension.CHCitizensExtension;
import com.laytonsmith.core.events.Driver;

/**
 *
 * @author Hekta
 */
public class BukkitCitizensListener implements Listener {

	protected static BukkitCitizensListener cl;

	@startup
	public static void register() {
		if (CHCitizensExtension.citizensPlugin != null) {
			if (cl == null) {
				cl = new BukkitCitizensListener();
			}
			CommandHelperPlugin.self.registerEvent(cl);
		}
	}

	@shutdown
	public static void unregister() {
		if (CHCitizensExtension.citizensPlugin != null) {
			NPCDespawnEvent.getHandlerList().unregister(cl);
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onNPCDespawn(NPCDespawnEvent event) {
		BukkitCitizensEvents.BukkitMCCitizensNPCDespawnEvent npcde = new BukkitCitizensEvents.BukkitMCCitizensNPCDespawnEvent(event);
		EventUtils.TriggerExternal(npcde);
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_despawn", npcde);
	}
}
