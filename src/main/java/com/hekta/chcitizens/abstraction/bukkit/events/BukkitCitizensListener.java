package com.hekta.chcitizens.abstraction.bukkit.events;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;

import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;

/**
 * @author Hekta
 */
public class BukkitCitizensListener implements Listener {

	private static BukkitCitizensListener _listener;

	public static void register() {
		if (_listener == null) {
			_listener = new BukkitCitizensListener();
		}
		CommandHelperPlugin.self.registerEvents(_listener);
	}

	public static void unregister() {
		HandlerList.unregisterAll(_listener);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onNavigationComplete(NavigationCompleteEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_navigation_complete", new BukkitCitizensEvents.BukkitMCCitizensNavigationCompleteEvent(event));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onNavigationCancel(NavigationCancelEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_navigation_cancel", new BukkitCitizensEvents.BukkitMCCitizensNavigationCancelEvent(event));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onNPCDespawn(NPCDespawnEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_despawn", new BukkitCitizensEvents.BukkitMCCitizensNPCDespawnEvent(event));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onNPCSpawn(NPCSpawnEvent event) {
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_spawn", new BukkitCitizensEvents.BukkitMCCitizensNPCSpawnEvent(event));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onNPCLeftClick(NPCLeftClickEvent e) {
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_click", new BukkitCitizensEvents.BukkitMCCitizensNPCClickEvent(e));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onNPCRightClick(NPCRightClickEvent e) {
		EventUtils.TriggerListener(Driver.EXTENSION, "ctz_npc_click", new BukkitCitizensEvents.BukkitMCCitizensNPCClickEvent(e));
	}
}