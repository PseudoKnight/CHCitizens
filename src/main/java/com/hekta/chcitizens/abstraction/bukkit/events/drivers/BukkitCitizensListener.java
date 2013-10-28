package com.hekta.chcitizens.abstraction.bukkit.events.drivers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;

import com.hekta.chcitizens.abstraction.bukkit.events.BukkitCitizensEvents;
import com.hekta.chcitizens.extension.CHCitizensExtension;

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
			NavigationCancelEvent.getHandlerList().unregister(cl);
			NavigationCompleteEvent.getHandlerList().unregister(cl);
			NPCDespawnEvent.getHandlerList().unregister(cl);
			NPCSpawnEvent.getHandlerList().unregister(cl);
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onNavigationComplete(NavigationCompleteEvent event) {
		BukkitCitizensEvents.BukkitMCCitizensNavigationCompleteEvent nce = new BukkitCitizensEvents.BukkitMCCitizensNavigationCompleteEvent(event);
		EventUtils.TriggerExternal(nce);
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_navigation_complete", nce);
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onNavigationCancel(NavigationCancelEvent event) {
		BukkitCitizensEvents.BukkitMCCitizensNavigationCancelEvent nce = new BukkitCitizensEvents.BukkitMCCitizensNavigationCancelEvent(event);
		EventUtils.TriggerExternal(nce);
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_navigation_cancel", nce);
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onNPCDespawn(NPCDespawnEvent event) {
		BukkitCitizensEvents.BukkitMCCitizensNPCDespawnEvent npcde = new BukkitCitizensEvents.BukkitMCCitizensNPCDespawnEvent(event);
		EventUtils.TriggerExternal(npcde);
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_despawn", npcde);
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onNPCSpawn(NPCSpawnEvent event) {
		BukkitCitizensEvents.BukkitMCCitizensNPCSpawnEvent npcse = new BukkitCitizensEvents.BukkitMCCitizensNPCSpawnEvent(event);
		EventUtils.TriggerExternal(npcse);
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_spawn", npcse);
	}
}