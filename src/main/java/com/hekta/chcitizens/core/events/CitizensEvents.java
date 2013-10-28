package com.hekta.chcitizens.core.events;

import java.util.Map;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventBuilder;
import com.laytonsmith.core.events.Prefilters;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import com.laytonsmith.core.functions.Exceptions;
import com.laytonsmith.PureUtilities.Common.StringUtils;
import com.laytonsmith.PureUtilities.Version;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCDespawnEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCSpawnEvent;
import com.hekta.chcitizens.core.CHCitizensStatic;

/**
 *
 * @author Hekta
 */
public class CitizensEvents {

	public static String docs() {
		return "Contains events related to the Citizens plugin.";
	}

	@api
	public static class ctz_npc_despawn extends AbstractEvent {
			
		public String getName() {
			return "ctz_npc_despawn";
		}

		public Driver driver() {
			return Driver.EXTENSION;
		}

		public BindableEvent convert(CArray manualObject) {
			Target t = manualObject.getTarget();
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(manualObject.get("id"), t), t);
			MCCitizensDespawnReason reason;
			try {
				reason = MCCitizensDespawnReason.valueOf(manualObject.get("reason", t).val());
			} catch (IllegalArgumentException exception) {
				throw new ConfigRuntimeException(manualObject.get("reason").val() + " is not a valid despawn reason.", Exceptions.ExceptionType.FormatException, t);
			}
			return EventBuilder.instantiate(MCCitizensNPCDespawnEvent.class, npc, reason);
		}

		public boolean modifyEvent(String key, Construct value, BindableEvent event) {
			return false;
		}

		public String docs() {
			return "{reason: <macro> The reason the NPC is despawning | type: <macro> The entity type of the NPC | world: <macro>}"
					+ " Fires when a NPC despawn."
					+ " {reason: The reason the NPC is despawning (one of " + StringUtils.Join(MCCitizensDespawnReason.values(), ", ", ", or ", " or ") + ") |"
					+ " id: The NPC id | entity: the entityID of the NPC | type: The entity type of the NPC | location: The location where the NPC is}"
					+ " {}"
					+ " {}";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public boolean matches(Map<String, Construct> prefilter, BindableEvent event) throws PrefilterNonMatchException {
			if (event instanceof MCCitizensNPCDespawnEvent) {
				MCCitizensNPCDespawnEvent npcde = (MCCitizensNPCDespawnEvent) event;
				Prefilters.match(prefilter, "reason", npcde.getReason().name(), Prefilters.PrefilterType.MACRO);
				MCLivingEntity entity = npcde.getNPC().getEntity();
				Prefilters.match(prefilter, "world", entity.getWorld().getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "type", entity.getType().name(), Prefilters.PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		public Map<String, Construct> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNPCDespawnEvent) {
				MCCitizensNPCDespawnEvent npcde = (MCCitizensNPCDespawnEvent) event;
				Map<String, Construct> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = npcde.getNPC();
				mapEvent.put("id", new CInt(npc.getId(), Target.UNKNOWN));
				MCLivingEntity entity = npc.getEntity();
				mapEvent.put("entity", new CInt(entity.getEntityId(), Target.UNKNOWN));
				mapEvent.put("type", new CString(entity.getType().name(), Target.UNKNOWN));
				mapEvent.put("location", ObjectGenerator.GetGenerator().location(entity.getLocation()));
				mapEvent.put("reason", new CString(npcde.getReason().name(), Target.UNKNOWN));
				return mapEvent;
			} else {
				throw new EventException("Cannot convert event to NPCDespawnEvent.");
			}
		}
	}

	@api
	public static class ctz_npc_spawn extends AbstractEvent {
			
		public String getName() {
			return "ctz_npc_spawn";
		}

		public Driver driver() {
			return Driver.EXTENSION;
		}

		public BindableEvent convert(CArray manualObject) {
			Target t = manualObject.getTarget();
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(manualObject.get("id", t), t), t);
			MCLocation location = ObjectGenerator.GetGenerator().location(manualObject.get("location", t), npc.isSpawned() ? npc.getEntity().getWorld() : null, t);
			return EventBuilder.instantiate(MCCitizensNPCSpawnEvent.class, npc, location);
		}

		public boolean modifyEvent(String key, Construct value, BindableEvent event) {
			return false;
		}

		public String docs() {
			return "{type: <macro> The entity type of the NPC | world: <macro>}"
					+ " Fires when a NPC spawn."
					+ " {id: The NPC id | entity: the entityID of the NPC | type: The entity type of the NPC | location: The location where the NPC will spawn}"
					+ " {}"
					+ " {}";
		}

		public Version since() {
			return CHVersion.V3_3_1;
		}

		public boolean matches(Map<String, Construct> prefilter, BindableEvent event) throws PrefilterNonMatchException {
			if (event instanceof MCCitizensNPCSpawnEvent) {
				MCCitizensNPCSpawnEvent npcse = (MCCitizensNPCSpawnEvent) event;
				MCLivingEntity entity = npcse.getNPC().getEntity();
				Prefilters.match(prefilter, "world", entity.getWorld().getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "type", entity.getType().name(), Prefilters.PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		public Map<String, Construct> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNPCSpawnEvent) {
				MCCitizensNPCSpawnEvent npcse = (MCCitizensNPCSpawnEvent) event;
				Map<String, Construct> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = npcse.getNPC();
				mapEvent.put("id", new CInt(npc.getId(), Target.UNKNOWN));
				MCLivingEntity entity = npc.getEntity();
				mapEvent.put("entity", new CInt(entity.getEntityId(), Target.UNKNOWN));
				mapEvent.put("type", new CString(entity.getType().name(), Target.UNKNOWN));
				mapEvent.put("location", ObjectGenerator.GetGenerator().location(npcse.getLocation()));
				return mapEvent;
			} else {
				throw new EventException("Cannot convert event to NPCSpawnEvent.");
			}
		}
	}
}