package com.hekta.chcitizens.core.events;

import java.util.Map;

import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
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
import com.hekta.chcitizens.abstraction.MCCitizensNavigator;
import com.hekta.chcitizens.abstraction.enums.MCCitizensCancelReason;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCDespawnEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNPCSpawnEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNavigationCancelEvent;
import com.hekta.chcitizens.abstraction.events.MCCitizensNavigationCompleteEvent;
import com.hekta.chcitizens.core.CHCitizensStatic;

/**
 *
 * @author Hekta
 */
public class CitizensEvents {

	public static String docs() {
		return "Contains events related to the Citizens plugin.";
	}

	public static abstract class CitizensEvent extends AbstractEvent {

		@Override
		public Version since() {
			return CHVersion.V3_3_1;
		}

		@Override
		public Driver driver() {
			return Driver.EXTENSION;
		}

		@Override
		public boolean modifyEvent(String key, Construct value, BindableEvent event) {
			return false;
		}
	}

	@api
	public static class ctz_npc_despawn extends CitizensEvent {
			
		@Override
		public String getName() {
			return "ctz_npc_despawn";
		}

		@Override
		public String docs() {
			return "{reason: <macro> The reason the NPC is despawning | type: <macro> The entity type of the NPC | world: <macro>}"
					+ " Fires when a NPC despawn."
					+ " {reason: The reason the NPC is despawning (one of " + StringUtils.Join(MCCitizensDespawnReason.values(), ", ", ", or ", " or ") + ") |"
					+ " npc: The NPC id | entity: The entityID of the NPC | type: The entity type of the NPC | location: The location where the NPC is}"
					+ " {}"
					+ " {}";
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(manualObject.get("npc", t), t), t);
			MCCitizensDespawnReason reason;
			try {
				reason = MCCitizensDespawnReason.valueOf(manualObject.get("reason", t).val().toUpperCase());
			} catch (IllegalArgumentException exception) {
				throw new ConfigRuntimeException(manualObject.get("reason", t).val() + " is not a valid despawn reason.", Exceptions.ExceptionType.FormatException, t);
			}
			return EventBuilder.instantiate(MCCitizensNPCDespawnEvent.class, npc, reason);
		}

		@Override
		public boolean matches(Map<String, Construct> prefilter, BindableEvent event) throws PrefilterNonMatchException {
			if (event instanceof MCCitizensNPCDespawnEvent) {
				MCCitizensNPCDespawnEvent npcde = (MCCitizensNPCDespawnEvent) event;
				Prefilters.match(prefilter, "reason", npcde.getReason().name(), Prefilters.PrefilterType.MACRO);
				MCEntity entity = npcde.getNPC().getEntity();
				Prefilters.match(prefilter, "world", entity.getWorld().getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "type", entity.getType().name(), Prefilters.PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Construct> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNPCDespawnEvent) {
				MCCitizensNPCDespawnEvent npcde = (MCCitizensNPCDespawnEvent) event;
				Map<String, Construct> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = npcde.getNPC();
				mapEvent.put("npc", new CInt(npc.getId(), Target.UNKNOWN));
				MCEntity entity = npc.getEntity();
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
	public static class ctz_npc_navigation_cancel extends CitizensEvent {
			
		@Override
		public String getName() {
			return "ctz_npc_navigation_cancel";
		}

		@Override
		public String docs() {
			return "{cause: <macro> The cause of the cancellation | type: <macro> The entity type of the NPC | world: <macro>}"
					+ " Fires when a NPC navigation is cancelled."
					+ " {npc: The NPC id | entity: the entityID of the NPC | type: The entity type of the NPC |"
					+ " location: The location where the NPC is (null if not spawned) |"
					+ " cause: The cause of the cancellation (one of " + StringUtils.Join(MCCitizensCancelReason.values(), ", ", ", or ", " or ") + ")}"
					+ " {}"
					+ " {}";
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			MCCitizensCancelReason reason;
			try {
				reason = MCCitizensCancelReason.valueOf(manualObject.get("reason", t).val().toUpperCase());
			} catch (IllegalArgumentException exception) {
				throw new ConfigRuntimeException(manualObject.get("reason", t).val() + " is not a valid cancel reason.", Exceptions.ExceptionType.FormatException, t);
			}
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(manualObject.get("npc", t), t), t).getNavigator();
			return EventBuilder.instantiate(MCCitizensNavigationCancelEvent.class, navigator, reason);
		}

		@Override
		public boolean matches(Map<String, Construct> prefilter, BindableEvent event) throws PrefilterNonMatchException {
			if (event instanceof MCCitizensNavigationCancelEvent) {
				MCCitizensNavigationCancelEvent nce = (MCCitizensNavigationCancelEvent) event;
				Prefilters.match(prefilter, "cause", nce.getCancelReason().name(), Prefilters.PrefilterType.MACRO);
				MCEntity entity = nce.getNPC().getEntity();
				Prefilters.match(prefilter, "world", entity.getWorld().getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "type", entity.getType().name(), Prefilters.PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Construct> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNavigationCancelEvent) {
				MCCitizensNavigationCancelEvent nce = (MCCitizensNavigationCancelEvent) event;
				Map<String, Construct> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = nce.getNPC();
				mapEvent.put("npc", new CInt(npc.getId(), Target.UNKNOWN));
				MCEntity entity = npc.getEntity();
				mapEvent.put("entity", new CInt(entity.getEntityId(), Target.UNKNOWN));
				mapEvent.put("type", new CString(entity.getType().name(), Target.UNKNOWN));
				if (npc.isSpawned()) {
					mapEvent.put("location", ObjectGenerator.GetGenerator().location(entity.getLocation()));
				} else {
					mapEvent.put("location", CNull.NULL);
				}
				mapEvent.put("reason", new CString(nce.getCancelReason().name(), Target.UNKNOWN));
				return mapEvent;
			} else {
				throw new EventException("Cannot convert event to NavigationCancelEvent.");
			}
		}
	}

	@api
	public static class ctz_npc_navigation_complete extends CitizensEvent {
			
		@Override
		public String getName() {
			return "ctz_npc_navigation_complete";
		}

		@Override
		public String docs() {
			return "{type: <macro> The entity type of the NPC | world: <macro>}"
					+ " Fires when a NPC reaches its destination."
					+ " {npc: The NPC id | entity: The entityID of the NPC | type: The entity type of the NPC | location: The location where the NPC is}"
					+ " {}"
					+ " {}";
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(manualObject.get("npc", t), t), t).getNavigator();
			return EventBuilder.instantiate(MCCitizensNavigationCompleteEvent.class, navigator);
		}

		@Override
		public boolean matches(Map<String, Construct> prefilter, BindableEvent event) throws PrefilterNonMatchException {
			if ((event instanceof MCCitizensNavigationCompleteEvent) && !(event instanceof MCCitizensNavigationCancelEvent)) {
				MCCitizensNavigationCompleteEvent nce = (MCCitizensNavigationCompleteEvent) event;
				MCEntity entity = nce.getNPC().getEntity();
				Prefilters.match(prefilter, "world", entity.getWorld().getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "type", entity.getType().name(), Prefilters.PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Construct> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNavigationCompleteEvent) {
				MCCitizensNavigationCompleteEvent nce = (MCCitizensNavigationCompleteEvent) event;
				Map<String, Construct> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = nce.getNPC();
				mapEvent.put("npc", new CInt(npc.getId(), Target.UNKNOWN));
				MCEntity entity = npc.getEntity();
				mapEvent.put("entity", new CInt(entity.getEntityId(), Target.UNKNOWN));
				mapEvent.put("type", new CString(entity.getType().name(), Target.UNKNOWN));
				mapEvent.put("location", ObjectGenerator.GetGenerator().location(entity.getLocation()));
				return mapEvent;
			} else {
				throw new EventException("Cannot convert event to NavigationCompleteEvent.");
			}
		}
	}

	@api
	public static class ctz_npc_spawn extends CitizensEvent {
			
		@Override
		public String getName() {
			return "ctz_npc_spawn";
		}

		@Override
		public String docs() {
			return "{type: <macro> The entity type of the NPC | world: <macro>}"
					+ " Fires when a NPC spawn."
					+ " {npc: The NPC id | entity: The entityID of the NPC | type: The entity type of the NPC | location: The location where the NPC will spawn}"
					+ " {}"
					+ " {}";
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(manualObject.get("npc", t), t), t);
			MCLocation location = ObjectGenerator.GetGenerator().location(manualObject.get("location", t), npc.isSpawned() ? npc.getEntity().getWorld() : null, t);
			return EventBuilder.instantiate(MCCitizensNPCSpawnEvent.class, npc, location);
		}

		@Override
		public boolean matches(Map<String, Construct> prefilter, BindableEvent event) throws PrefilterNonMatchException {
			if (event instanceof MCCitizensNPCSpawnEvent) {
				MCCitizensNPCSpawnEvent npcse = (MCCitizensNPCSpawnEvent) event;
				MCEntity entity = npcse.getNPC().getEntity();
				Prefilters.match(prefilter, "world", entity.getWorld().getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "type", entity.getType().name(), Prefilters.PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Construct> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNPCSpawnEvent) {
				MCCitizensNPCSpawnEvent npcse = (MCCitizensNPCSpawnEvent) event;
				Map<String, Construct> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = npcse.getNPC();
				mapEvent.put("npc", new CInt(npc.getId(), Target.UNKNOWN));
				MCEntity entity = npc.getEntity();
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