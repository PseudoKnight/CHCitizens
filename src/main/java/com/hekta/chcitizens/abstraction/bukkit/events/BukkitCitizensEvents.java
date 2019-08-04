package com.hekta.chcitizens.abstraction.bukkit.events;

import com.hekta.chcitizens.abstraction.events.MCCitizensEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCClickEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCDespawnEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCSpawnEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNavigationCancelEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNavigationCompleteEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNavigationEvent;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.ai.event.NavigationEvent;
import net.citizensnpcs.api.event.CitizensEvent;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.annotations.abstraction;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensNavigator;
import com.hekta.chcitizens.abstraction.bukkit.BukkitMCCitizensNPC;
import com.hekta.chcitizens.abstraction.bukkit.BukkitMCCitizensNavigator;
import com.hekta.chcitizens.abstraction.enums.MCCitizensCancelReason;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.abstraction.enums.bukkit.BukkitMCCitizensCancelReason;
import com.hekta.chcitizens.abstraction.enums.bukkit.BukkitMCCitizensDespawnReason;
import net.citizensnpcs.api.event.SpawnReason;

/**
 * @author Hekta
 */
public class BukkitCitizensEvents {

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensEvent implements MCCitizensEvent {

		private final CitizensEvent m_event;

		public BukkitMCCitizensEvent(CitizensEvent event) {
			m_event = event;
		}

		@Override
		public Object _GetObject() {
			return m_event;
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNavigationEvent implements MCCitizensNavigationEvent {

		private final NavigationEvent m_event;

		public BukkitMCCitizensNavigationEvent(NavigationEvent event) {
			m_event = event;
		}

		@Override
		public Object _GetObject() {
			return m_event;
		}

		@Override
		public MCCitizensNavigator getNavigator() {
			return new BukkitMCCitizensNavigator(m_event.getNavigator());
		}

		@Override
		public MCCitizensNPC getNPC() {
			return new BukkitMCCitizensNPC(m_event.getNPC());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNavigationCompleteEvent extends BukkitMCCitizensNavigationEvent implements MCCitizensNavigationCompleteEvent {

		private final NavigationCompleteEvent m_event;

		public BukkitMCCitizensNavigationCompleteEvent(NavigationCompleteEvent event) {
			super(event);
			m_event = event;
		}

		@Override
		public Object _GetObject() {
			return m_event;
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNavigationCancelEvent extends BukkitMCCitizensNavigationCompleteEvent implements MCCitizensNavigationCancelEvent {

		private final NavigationCancelEvent m_event;

		public BukkitMCCitizensNavigationCancelEvent(NavigationCancelEvent event) {
			super(event);
			m_event = event;
		}

		@Override
		public Object _GetObject() {
			return m_event;
		}

		@Override
		public MCCitizensCancelReason getCancelReason() {
			return BukkitMCCitizensCancelReason.getConvertor().getAbstractedEnum(m_event.getCancelReason());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNPCEvent extends BukkitMCCitizensEvent implements MCCitizensNPCEvent {

		private final NPCEvent m_event;

		public BukkitMCCitizensNPCEvent(NPCEvent event) {
			super(event);
			m_event = event;
		}

		@Override
		public Object _GetObject() {
			return m_event;
		}

		@Override
		public MCCitizensNPC getNPC() {
			return new BukkitMCCitizensNPC(m_event.getNPC());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNPCDespawnEvent extends BukkitMCCitizensNPCEvent implements MCCitizensNPCDespawnEvent {

		private final NPCDespawnEvent m_event;

		public BukkitMCCitizensNPCDespawnEvent(NPCDespawnEvent event) {
			super(event);
			m_event = event;
		}

		@Override
		public Object _GetObject() {
			return m_event;
		}

		public static BukkitMCCitizensNPCDespawnEvent _instantiate(MCCitizensNPC npc, MCCitizensDespawnReason reason) {
			return new BukkitMCCitizensNPCDespawnEvent(new NPCDespawnEvent(((BukkitMCCitizensNPC) npc).getHandle(), BukkitMCCitizensDespawnReason.getConvertor().getConcreteEnum(reason)));
		}

		@Override
		public MCCitizensDespawnReason getReason() {
			return BukkitMCCitizensDespawnReason.getConvertor().getAbstractedEnum(m_event.getReason());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNPCSpawnEvent extends BukkitMCCitizensNPCEvent implements MCCitizensNPCSpawnEvent {

		private final NPCSpawnEvent m_event;

		public BukkitMCCitizensNPCSpawnEvent(NPCSpawnEvent event) {
			super(event);
			m_event = event;
		}

		@Override
		public Object _GetObject() {
			return m_event;
		}

		public static BukkitMCCitizensNPCSpawnEvent _instantiate(MCCitizensNPC npc, MCLocation location) {
			return new BukkitMCCitizensNPCSpawnEvent(new NPCSpawnEvent(((BukkitMCCitizensNPC) npc).getHandle(), ((BukkitMCLocation) location).asLocation(), SpawnReason.PLUGIN));
		}

		@Override
		public MCLocation getLocation() {
			return new BukkitMCLocation(m_event.getLocation());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNPCClickEvent extends BukkitMCCitizensNPCEvent implements MCCitizensNPCClickEvent {
		private final NPCClickEvent event;
		private final boolean left;

		public BukkitMCCitizensNPCClickEvent(NPCClickEvent e, boolean left) {
			super(e);
			this.event = e;
			this.left = left;
		}

		public BukkitMCCitizensNPCClickEvent(NPCLeftClickEvent e) {
			this(e, true);
		}

		public BukkitMCCitizensNPCClickEvent(NPCRightClickEvent e) {
			this(e, false);
		}

		@Override
		public MCPlayer getClicker() {
			return new BukkitMCPlayer(event.getClicker());
		}

		@Override
		public boolean isLeft() {
			return left;
		}
	}
}