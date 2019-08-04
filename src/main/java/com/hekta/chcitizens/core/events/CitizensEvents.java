package com.hekta.chcitizens.core.events;

import java.util.HashMap;
import java.util.Map;

import com.hekta.chcitizens.abstraction.bukkit.events.BukkitCitizensEvents;
import com.hekta.chcitizens.abstraction.events.*;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.MSVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventBuilder;
import com.laytonsmith.core.events.Prefilters;
import com.laytonsmith.core.exceptions.CRE.CREFormatException;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import com.laytonsmith.PureUtilities.Common.StringUtils;
import com.laytonsmith.PureUtilities.Version;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensNavigator;
import com.hekta.chcitizens.abstraction.enums.MCCitizensCancelReason;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.core.CHCitizensStatic;
import com.laytonsmith.core.natives.interfaces.Mixed;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;

/**
 *
 * @author Hekta
 */
public final class CitizensEvents {

	private CitizensEvents() {
	}

	public static String docs() {
		return "Contains events related to the Citizens plugin.";
	}

	protected static abstract class CitizensEvent extends AbstractEvent {

		@Override
		public String getName() {
			return getClass().getSimpleName();
		}

		@Override
		public Version since() {
			return MSVersion.V3_3_1;
		}

		@Override
		public Driver driver() {
			return Driver.EXTENSION;
		}

		@Override
		public boolean modifyEvent(String key, Mixed value, BindableEvent event) {
			return false;
		}
	}

	@api
	public static final class ctz_npc_despawn extends CitizensEvent {

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
				throw new CREFormatException(manualObject.get("reason", t).val() + " is not a valid despawn reason.", t);
			}
			return EventBuilder.instantiate(MCCitizensNPCDespawnEvent.class, npc, reason);
		}

		@Override
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent event) throws PrefilterNonMatchException {
			if (event instanceof MCCitizensNPCDespawnEvent) {
				MCCitizensNPCDespawnEvent npcde = (MCCitizensNPCDespawnEvent) event;
				Prefilters.match(prefilter, "reason", npcde.getReason().name(), Prefilters.PrefilterType.MACRO);
				MCEntity entity = npcde.getNPC().getEntity();
				if(entity == null) {
					return false;
				}
				Prefilters.match(prefilter, "world", entity.getWorld().getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "type", entity.getType().name(), Prefilters.PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Map<String, Mixed> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNPCDespawnEvent) {
				MCCitizensNPCDespawnEvent npcde = (MCCitizensNPCDespawnEvent) event;
				Map<String, Mixed> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = npcde.getNPC();
				mapEvent.put("npc", new CInt(npc.getId(), Target.UNKNOWN));
				MCEntity entity = npc.getEntity();
				mapEvent.put("entity", new CString(entity.getUniqueId().toString(), Target.UNKNOWN));
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
	public static final class ctz_npc_navigation_cancel extends CitizensEvent {

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
				throw new CREFormatException(manualObject.get("reason", t).val() + " is not a valid cancel reason.", t);
			}
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(manualObject.get("npc", t), t), t).getNavigator();
			return EventBuilder.instantiate(MCCitizensNavigationCancelEvent.class, navigator, reason);
		}

		@Override
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent event) throws PrefilterNonMatchException {
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
		public Map<String, Mixed> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNavigationCancelEvent) {
				MCCitizensNavigationCancelEvent nce = (MCCitizensNavigationCancelEvent) event;
				Map<String, Mixed> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = nce.getNPC();
				mapEvent.put("npc", new CInt(npc.getId(), Target.UNKNOWN));
				MCEntity entity = npc.getEntity();
				mapEvent.put("entity", new CString(entity.getUniqueId().toString(), Target.UNKNOWN));
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
	public static final class ctz_npc_navigation_complete extends CitizensEvent {

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
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent event) throws PrefilterNonMatchException {
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
		public Map<String, Mixed> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNavigationCompleteEvent) {
				MCCitizensNavigationCompleteEvent nce = (MCCitizensNavigationCompleteEvent) event;
				Map<String, Mixed> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = nce.getNPC();
				mapEvent.put("npc", new CInt(npc.getId(), Target.UNKNOWN));
				MCEntity entity = npc.getEntity();
				mapEvent.put("entity", new CString(entity.getUniqueId().toString(), Target.UNKNOWN));
				mapEvent.put("type", new CString(entity.getType().name(), Target.UNKNOWN));
				mapEvent.put("location", ObjectGenerator.GetGenerator().location(entity.getLocation()));
				return mapEvent;
			} else {
				throw new EventException("Cannot convert event to NavigationCompleteEvent.");
			}
		}
	}

	@api
	public static final class ctz_npc_spawn extends CitizensEvent {

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
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent event) throws PrefilterNonMatchException {
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
		public Map<String, Mixed> evaluate(BindableEvent event) throws EventException {
			if (event instanceof MCCitizensNPCSpawnEvent) {
				MCCitizensNPCSpawnEvent npcse = (MCCitizensNPCSpawnEvent) event;
				Map<String, Mixed> mapEvent = evaluate_helper(event);
				MCCitizensNPC npc = npcse.getNPC();
				mapEvent.put("npc", new CInt(npc.getId(), Target.UNKNOWN));
				MCEntity entity = npc.getEntity();
				mapEvent.put("entity", new CString(entity.getUniqueId().toString(), Target.UNKNOWN));
				mapEvent.put("type", new CString(entity.getType().name(), Target.UNKNOWN));
				mapEvent.put("location", ObjectGenerator.GetGenerator().location(npcse.getLocation()));
				return mapEvent;
			} else {
				throw new EventException("Cannot convert event to NPCSpawnEvent.");
			}
		}
	}

	@api
	public static final class ctz_npc_click extends CitizensEvent {
		@Override
		public String docs() {
			return "{" +
					"button: <macro> Clicked button left or right | " +
					"player: <macro> | " +
					"world: <macro> | " +
					"type: <macro>} Fires when click an NPC.";
		}

		@Override
		public boolean matches(Map<String, Mixed> prefilter, BindableEvent e) throws PrefilterNonMatchException {
			if (e instanceof MCCitizensNPCClickEvent) {
				MCCitizensNPCClickEvent event = ((MCCitizensNPCClickEvent) e);
				boolean left = event.isLeft();
				MCPlayer clicker = event.getClicker();
				MCCitizensNPC npc = event.getNPC();
				MCEntity entity = npc.getEntity();
				Prefilters.match(prefilter, "button", left ? "left" : "right", Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "player", clicker.getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "world", entity.getWorld().getName(), Prefilters.PrefilterType.MACRO);
				Prefilters.match(prefilter, "type", entity.getType().name(), Prefilters.PrefilterType.MACRO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public BindableEvent convert(CArray manualObject, Target t) {
			return null;
		}

		@Override
		public Map<String, Mixed> evaluate(BindableEvent e) throws EventException {
			if (e instanceof MCCitizensNPCClickEvent) {
				Target t = Target.UNKNOWN;
				MCCitizensNPCClickEvent event = ((MCCitizensNPCClickEvent) e);
				Map<String, Mixed> ret = new HashMap<>();
				boolean left = event.isLeft();
				MCCitizensNPC npc = event.getNPC();
				MCEntity entity = npc.getEntity();
				ret.put("button", new CString(left ? "left" : "right", t));
				ret.put("player", new CString(event.getClicker().getName(), t));
				ret.put("npc", new CInt(npc.getId(), t));
				ret.put("entity", new CString(npc.getUniqueId().toString(), t));
				ret.put("type", new CString(entity.getType().name(), t));
				ret.put("location", ObjectGenerator.GetGenerator().location(new BukkitMCLocation(entity.getLocation())));
				return ret;
			}
			throw new EventException("Cannot convert event to NPCRightClickEvent.");
		}
	}
}