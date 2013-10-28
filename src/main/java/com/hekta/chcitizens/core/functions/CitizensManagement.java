package com.hekta.chcitizens.core.functions;

import com.laytonsmith.annotations.api;
import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.enums.MCEntityType;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import com.laytonsmith.PureUtilities.Common.StringUtils;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.core.CHCitizensStatic;

/**
 *
 * @author Hekta
 */
public class CitizensManagement {

	public static String docs() {
		return "This class allows to manage the NPCs of the Citizens plugin.";
	}

	public static abstract class CitizensNPCFunction extends AbstractFunction {
		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public CHVersion since() {
			return CHVersion.V3_3_1;
		}
	}

	public static abstract class CitizensNPCGetterFunction extends CitizensNPCFunction {
		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.NotFoundException};
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}
	}

	public static abstract class CitizensNPCSetterFunction extends CitizensNPCFunction {
		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.NotFoundException};
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}
	}

	@api
	public static class ctz_all_npcs extends CitizensNPCFunction {

		public String getName() {
			return "ctz_all_npcs";
		}

		public Integer[] numArgs() {
			return new Integer[]{0};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException};
		}

		public String docs() {
			return "array {} Returns an array containing the ids of all registered NPCs.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CArray array = new CArray(t);
			for (MCCitizensNPC npc : CHCitizensStatic.getNPCRegistry(t).getNPCs()) {
				array.push(new CInt(npc.getId(), t));
			}
			return array;
		}
	}

	@api
	public static class ctz_create_npc extends CitizensNPCFunction {

		public String getName() {
			return "ctz_create_npc";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.FormatException, ExceptionType.BadEntityTypeException, ExceptionType.RangeException};
		}

		public String docs() {
			return "int {[entityType], name | entityType, [id], name} Creates a NPC and return its id, or null if the creation failed."
						+ " entityType must be a living entity type, default to PLAYER. id takes a integer.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc;
			if (args.length == 1) {
				npc = CHCitizensStatic.getNPCRegistry(t).createNPC(MCEntityType.PLAYER, args[0].val());
			} else if (args.length == 2) {
				MCEntityType type;
				try {
					type = MCEntityType.valueOf(args[0].val().toUpperCase());
				} catch (IllegalArgumentException exception) {
					throw new ConfigRuntimeException("Bad entity type :" + args[0].val() + ".", ExceptionType.FormatException, t);
				}
				try {
					npc = CHCitizensStatic.getNPCRegistry(t).createNPC(type, args[1].val());
				} catch (IllegalArgumentException exception) {
					throw new ConfigRuntimeException("The given entity type (" + args[0].val() + ") is not a living entity type.", ExceptionType.BadEntityTypeException, t);
				}
			} else {
				int id = Static.getInt32(args[1], t);
				if (CHCitizensStatic.getNPCRegistry(t).getNPCById(id) == null) {
					throw new ConfigRuntimeException("A NPC with this id (" + id + ") already exists.", ExceptionType.RangeException, t);
				}
				MCEntityType type;
				try {
					type = MCEntityType.valueOf(args[0].val().toUpperCase());
				} catch (IllegalArgumentException exception) {
					throw new ConfigRuntimeException("Bad entity type :" + args[0].val() + ".", ExceptionType.FormatException, t);
				}
				try {
					npc = CHCitizensStatic.getNPCRegistry(t).createNPC(type, id, args[2].val());
				} catch (IllegalArgumentException exception) {
					throw new ConfigRuntimeException("The given entity type (" + args[0].val() + ") is not a living entity type.", ExceptionType.BadEntityTypeException, t);
				}
			}
			if (npc != null) {
				return new CInt(npc.getId(), t);
			} else {
				return new CNull();
			}
		}
	}

	@api
	public static class ctz_remove_npc extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_remove_npc";
		}

		public String docs() {
			return "void {npcID} Removes a NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).destroy();
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_entity_is_npc extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_entity_is_npc";
		}

		public String docs() {
			return "boolean {entityID} Returns if the given living entity is a NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHCitizensStatic.getNPCRegistry(t).isNPC(Static.getLivingEntity(Static.getInt32(args[0], t), t)), t);
		}
	}

	@api
	public static class ctz_npc_id extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_id";
		}

		public String docs() {
			return "int {entityID} Returns the NPC id of the given living entity, or null if the entity is not a NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPCRegistry(t).getNPC(Static.getLivingEntity(Static.getInt32(args[0], t), t));
			if (npc != null) {
				return new CInt(npc.getId(), t);
                        } else {
				return new CNull(t);
			}
		}
	}

	@api
	public static class ctz_npc_stored_loc extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_stored_loc";
		}

		public String docs() {
			return "array {npcID} Returns the stored location of the given NPC (the last known location, or null if it has never been spawned).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCLocation location = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getStoredLocation();
			if (location != null) {
				return ObjectGenerator.GetGenerator().location(location);
			} else {
				return new CNull(t);
			}
		}
	}

	@api
	public static class ctz_set_npc_face_loc extends CitizensNPCSetterFunction {

		public String getName() {
			return "ctz_set_npc_face_loc";
		}

		@Override
		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.NotFoundException, ExceptionType.FormatException};
		}

		public String docs() {
			return "void {npcID, locationArray} Forces the given NPC to face the given location if it is spawned.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).faceLocation(ObjectGenerator.GetGenerator().location(args[1], null, t));
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_is_spawned extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_is_spawned";
		}

		public String docs() {
			return "boolean {npcID} Returns if the given NPC is spawned.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).isSpawned(), t);
		}
	}

	@api
	public static class ctz_spawn_npc extends CitizensNPCSetterFunction {

		public String getName() {
			return "ctz_spawn_npc";
		}

		public String docs() {
			return "boolean {npcID, locationArray} Spawns the given NPC at the given location.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).spawn(ObjectGenerator.GetGenerator().location(args[1], null, t));
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_despawn_npc extends CitizensNPCFunction {

		public String getName() {
			return "ctz_despawn_npc";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.NotFoundException, ExceptionType.FormatException};
		}

		public String docs() {
			return "boolean {npcID, [reason]} Despawns the given NPC, the reason can be one of " + StringUtils.Join(MCCitizensDespawnReason.values(), ", ", ", or ", " or ") + ","
					+ " PLUGIN by default. The reason is used by the ctz_npc_despawn event.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensDespawnReason reason;
			if ((args.length == 1) || (args[1] instanceof CNull)) {
				reason = MCCitizensDespawnReason.PLUGIN;
			} else {
				try {
					reason = MCCitizensDespawnReason.valueOf(args[1].val().toUpperCase());
				} catch (IllegalArgumentException exception) {
					throw new ConfigRuntimeException(args[1].val() + " is not a valid despawn reason.", ExceptionType.FormatException, t);
				}
			}
			CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).despawn(reason);
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_entity_id extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_entity_id";
		}

		public String docs() {
			return "int {npcID} Returns the entity id of the NPC, or null if the NPC is not spawned.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCLivingEntity entity = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getEntity();
			if (entity != null) {
				return new CInt(entity.getEntityId(), t);
			} else {
				return new CNull(t);
			}
		}
	}

	@api
	public static class ctz_set_npc_entity_type extends CitizensNPCSetterFunction {

		public String getName() {
			return "ctz_set_npc_entity_type";
		}

		@Override
		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.NotFoundException, ExceptionType.FormatException, ExceptionType.BadEntityTypeException};
		}

		public String docs() {
			return "void {npcID, string} Sets the entity type of the NPC. If the type is not a living entity type, the function will throw a BadEntityTypeException.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCEntityType type;
			try {
				type = MCEntityType.valueOf(args[1].val().toUpperCase());
			} catch (IllegalArgumentException exception) {
				throw new ConfigRuntimeException("Bad entity type :" + args[1].val() + ".", ExceptionType.FormatException, t);
			}
			try {
				CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).setEntityType(type);
			} catch (IllegalArgumentException exception) {
				throw new ConfigRuntimeException("The given entity type (" + args[1].val() + ") is not a living entity type.", ExceptionType.BadEntityTypeException, t);
			}
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_name extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_name";
		}

		public String docs() {
			return "string {npcID} Returns the name of the NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getName(), t);
		}
	}

	@api
	public static class ctz_set_npc_name extends CitizensNPCSetterFunction {

		public String getName() {
			return "ctz_set_npc_name";
		}

		public String docs() {
			return "void {npcID, string} Sets the name of the NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).setName(args[1].val());
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_full_name extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_full_name";
		}

		public String docs() {
			return "string {npcID} Returns the full name of the NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CString(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getFullName(), t);
		}
	}

	@api
	public static class ctz_npc_is_protected extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_is_protected";
		}

		public String docs() {
			return "boolean {npcID} Returns if the NPC is protected.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).isProtected(), t);
		}
	}

	@api
	public static class ctz_set_npc_protected extends CitizensNPCSetterFunction {

		public String getName() {
			return "ctz_set_npc_protected";
		}

		public String docs() {
			return "void {npcID, boolean} Sets if the NPC is protected.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).setProtected(Static.getBoolean(args[1]));
			return new CVoid(t);
		}
	}
}