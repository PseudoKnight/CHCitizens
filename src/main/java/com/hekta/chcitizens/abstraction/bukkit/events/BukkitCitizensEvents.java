package com.hekta.chcitizens.abstraction.bukkit.events;

import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.ai.event.NavigationEvent;
import net.citizensnpcs.api.event.CitizensEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCEvent;
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
import com.hekta.chcitizens.abstraction.events.MCCitizensEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCDespawnEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCSpawnEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNavigationCancelEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNavigationCompleteEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNavigationEvent;

/**
 *
 * @author Hekta
 */
public class BukkitCitizensEvents {

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensEvent implements MCCitizensEvent {

		CitizensEvent ce;

		public BukkitMCCitizensEvent(CitizensEvent event) {
			this.ce = event;
		}

		public Object _GetObject() {
			return ce;
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNavigationEvent implements MCCitizensNavigationEvent {

		NavigationEvent ne;

		public BukkitMCCitizensNavigationEvent(NavigationEvent event) {
			this.ne = event;
		}

		public Object _GetObject() {
			return ne;
		}

		public MCCitizensNavigator getNavigator() {
			return new BukkitMCCitizensNavigator(ne.getNavigator());
		}

		public MCCitizensNPC getNPC() {
			return new BukkitMCCitizensNPC(ne.getNPC());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNavigationCompleteEvent extends BukkitMCCitizensNavigationEvent implements MCCitizensNavigationCompleteEvent {

		NavigationCompleteEvent ncpe;

		public BukkitMCCitizensNavigationCompleteEvent(NavigationCompleteEvent event) {
			super(event);
			this.ncpe = event;
		}

		@Override
		public Object _GetObject() {
			return ncpe;
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNavigationCancelEvent extends BukkitMCCitizensNavigationCompleteEvent implements MCCitizensNavigationCancelEvent {

		NavigationCancelEvent ncle;

		public BukkitMCCitizensNavigationCancelEvent(NavigationCancelEvent event) {
			super(event);
			this.ncle = event;
		}

		@Override
		public Object _GetObject() {
			return ncle;
		}

		public MCCitizensCancelReason getCancelReason() {
			return BukkitMCCitizensCancelReason.getConvertor().getAbstractedEnum(ncle.getCancelReason());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNPCEvent extends BukkitMCCitizensEvent implements MCCitizensNPCEvent {

		NPCEvent npce;

		public BukkitMCCitizensNPCEvent(NPCEvent event) {
			super(event);
			this.npce = event;
		}

		@Override
		public Object _GetObject() {
			return npce;
		}

		public MCCitizensNPC getNPC() {
			return new BukkitMCCitizensNPC(npce.getNPC());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNPCDespawnEvent extends BukkitMCCitizensNPCEvent implements MCCitizensNPCDespawnEvent {

		NPCDespawnEvent npcde;

		public BukkitMCCitizensNPCDespawnEvent(NPCDespawnEvent event) {
			super(event);
			this.npcde = event;
		}

		@Override
		public Object _GetObject() {
			return npcde;
		}
	
		public static BukkitMCCitizensNPCDespawnEvent _instantiate(MCCitizensNPC npc, MCCitizensDespawnReason reason) {
			return new BukkitMCCitizensNPCDespawnEvent(new NPCDespawnEvent(((BukkitMCCitizensNPC) npc).getConcrete(), BukkitMCCitizensDespawnReason.getConvertor().getConcreteEnum(reason)));
		}

		public MCCitizensDespawnReason getReason() {
			return BukkitMCCitizensDespawnReason.getConvertor().getAbstractedEnum(npcde.getReason());
		}
	}

	@abstraction(type = Implementation.Type.BUKKIT)
	public static class BukkitMCCitizensNPCSpawnEvent extends BukkitMCCitizensNPCEvent implements MCCitizensNPCSpawnEvent {

		NPCSpawnEvent npcse;

		public BukkitMCCitizensNPCSpawnEvent(NPCSpawnEvent event) {
			super(event);
			this.npcse = event;
		}

		@Override
		public Object _GetObject() {
			return npcse;
		}
	
		public static BukkitMCCitizensNPCSpawnEvent _instantiate(MCCitizensNPC npc, MCLocation location) {
			return new BukkitMCCitizensNPCSpawnEvent(new NPCSpawnEvent(((BukkitMCCitizensNPC) npc).getConcrete(), ((BukkitMCLocation) location).asLocation()));
		}

		public MCLocation getLocation() {
			return new BukkitMCLocation(npcse.getLocation());
		}
	}
}