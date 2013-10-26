package com.hekta.chcitizens.abstraction.bukkit.events;

import net.citizensnpcs.api.event.CitizensEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCEvent;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.annotations.abstraction;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.bukkit.BukkitMCCitizensNPC;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.abstraction.enums.bukkit.BukkitMCCitizensDespawnReason;
import com.hekta.chcitizens.abstraction.events.MCCitizensEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCDespawnEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCEvent;

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

		public MCCitizensDespawnReason getReason() {
			return BukkitMCCitizensDespawnReason.getConvertor().getAbstractedEnum(npcde.getReason());
		}
	}
}
