package com.hekta.chcitizens.core.functions;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.core.CHCitizensStatic;
import com.laytonsmith.PureUtilities.Common.StringUtils;
import com.laytonsmith.annotations.api;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.enums.MCEntityType;
import com.laytonsmith.core.ArgumentValidation;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREBadEntityTypeException;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREFormatException;
import com.laytonsmith.core.exceptions.CRE.CREInvalidPluginException;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CRERangeException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.natives.interfaces.Mixed;

import java.util.UUID;

/**
 *
 * @author Hekta
 */
public abstract class CitizensManagement extends CitizensFunctions {

	public static String docs() {
		return "This class allows to manage the NPCs of the Citizens plugin.";
	}

	@api
	public static final class ctz_all_npcs extends CitizensNPCFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{0};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class};
		}

		@Override
		public String docs() {
			return "array {} Returns an array containing the ids of all registered NPCs.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException{
			CArray array = new CArray(t);
			for (MCCitizensNPC npc : CHCitizensStatic.getNPCRegistry(t).getNPCs()) {
				array.push(new CInt(npc.getId(), t), t);
			}
			return array;
		}
	}

	@api
	public static final class ctz_create_npc extends CitizensNPCFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{0, 1, 2, 3};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRECastException.class, CREFormatException.class, CREBadEntityTypeException.class, CRERangeException.class, CREPlayerOfflineException.class};
		}

		@Override
		public String docs() {
			return "int {[[entityType], name] | entityType, [id], name} Creates a NPC and return its id, or null if the creation failed."
						+ " THe default entityType is PLAYER. id takes a integer."
						+ " If name is not given, the name of the NPC will be the name of the player which run the function.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCCitizensNPC npc;
			if (args.length == 0) {
				npc = CHCitizensStatic.getNPCRegistry(t).createNPC(MCEntityType.MCVanillaEntityType.PLAYER, Static.getPlayer(environment, t).getName());
			} else if (args.length == 1) {
				npc = CHCitizensStatic.getNPCRegistry(t).createNPC(MCEntityType.MCVanillaEntityType.PLAYER, args[0].val());
			} else if (args.length == 2) {
				MCEntityType.MCVanillaEntityType type;
				try {
					type = MCEntityType.MCVanillaEntityType.valueOf(args[0].val().toUpperCase());
				} catch (IllegalArgumentException exception) {
					throw new CREFormatException("Bad entity type :" + args[0].val() + ".", t);
				}
				try {
					npc = CHCitizensStatic.getNPCRegistry(t).createNPC(type, args[1].val());
				} catch (IllegalArgumentException exception) {
					throw new CREBadEntityTypeException("The given entity type (" + args[0].val() + ") is not a living entity type.", t);
				}
			} else {
				int id = ArgumentValidation.getInt32(args[1], t);
				if (CHCitizensStatic.getNPCRegistry(t).getNPC(id) == null) {
					throw new CRERangeException("A NPC with this id (" + id + ") already exists.", t);
				}
				MCEntityType.MCVanillaEntityType type;
				try {
					type = MCEntityType.MCVanillaEntityType.valueOf(args[0].val().toUpperCase());
				} catch (IllegalArgumentException exception) {
					throw new CREFormatException("Bad entity type :" + args[0].val() + ".", t);
				}
				try {
					npc = CHCitizensStatic.getNPCRegistry(t).createNPC(type, id, args[2].val());
				} catch (IllegalArgumentException exception) {
					throw new CREBadEntityTypeException("The given entity type (" + args[0].val() + ") is not a living entity type.", t);
				}
			}
			if (npc != null) {
				return new CInt(npc.getId(), t);
			} else {
				return CNull.NULL;
			}
		}
	}

	@api
	public static final class ctz_remove_npc extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "void {npcID} Removes a NPC.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).destroy();
			return CVoid.VOID;
		}
	}

	@api
	public static final class ctz_entity_is_npc extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "boolean {entityID} Returns if the given living entity is a NPC.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			return CBoolean.get(CHCitizensStatic.getNPCRegistry(t).isNPC(Static.getEntity(args[0], t)));
		}
	}

	@api
	public static final class ctz_npc_id extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "int {entityID} Returns the NPC id of the given entity, or null if the entity is not a NPC.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			UUID uuid = Static.GetUUID(args[0], t);
			MCCitizensNPC npc = CHCitizensStatic.getNPCRegistry(t).getNPC(uuid);
			if (npc != null) {
				return new CInt(npc.getId(), t);
                        } else {
				return CNull.NULL;
			}
		}
	}

	@api
	public static final class ctz_npc_stored_loc extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "array {npcID} Returns the stored location of the given NPC (the last known location, or null if it has never been spawned).";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCLocation location = CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).getStoredLocation();
			if (location != null) {
				return ObjectGenerator.GetGenerator().location(location);
			} else {
				return CNull.NULL;
			}
		}
	}

	@api
	public static final class ctz_set_npc_face_loc extends CitizensNPCSetterFunction {

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRECastException.class, CRENotFoundException.class, CREFormatException.class};
		}

		@Override
		public String docs() {
			return "void {npcID, locationArray} Forces the given NPC to face the given location if it is spawned.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).faceLocation(ObjectGenerator.GetGenerator().location(args[1], null, t));
			return CVoid.VOID;
		}
	}

	@api
	public static final class ctz_npc_is_spawned extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "boolean {npcID} Returns if the given NPC is spawned.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			return CBoolean.get(CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).isSpawned());
		}
	}

	@api
	public static final class ctz_spawn_npc extends CitizensNPCSetterFunction {

		@Override
		public String docs() {
			return "boolean {npcID, [locationArray]} Spawns the given NPC at the given location, or at the current player position if not specified.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCLocation location = (args.length == 1) ? Static.getPlayer(environment, t).getLocation() : ObjectGenerator.GetGenerator().location(args[1], null, t);
			CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).spawn(location);
			return CVoid.VOID;
		}
	}

	@api
	public static final class ctz_despawn_npc extends CitizensNPCFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRECastException.class, CRENotFoundException.class, CREFormatException.class};
		}

		@Override
		public String docs() {
			return "boolean {npcID, [reason]} Despawns the given NPC, the reason can be one of " + StringUtils.Join(MCCitizensDespawnReason.values(), ", ", ", or ", " or ") + ","
					+ " PLUGIN by default. The reason is used by the ctz_npc_despawn event.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCCitizensDespawnReason reason;
			if ((args.length == 1) || (args[1] instanceof CNull)) {
				reason = MCCitizensDespawnReason.PLUGIN;
			} else {
				try {
					reason = MCCitizensDespawnReason.valueOf(args[1].val().toUpperCase());
				} catch (IllegalArgumentException exception) {
					throw new CREFormatException(args[1].val() + " is not a valid despawn reason.", t);
				}
			}
			CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).despawn(reason);
			return CVoid.VOID;
		}
	}

	@api
	public static final class ctz_npc_entity_id extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "string {npcID} Returns the entity id of the NPC, or null if the NPC is not spawned.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t);
			if(npc.isSpawned()) {
				return new CString(npc.getEntity().getUniqueId().toString(), t);
			}
			return CNull.NULL;
		}
	}

	@api
	public static final class ctz_set_npc_entity_type extends CitizensNPCSetterFunction {

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRECastException.class, CRENotFoundException.class, CREFormatException.class, CREBadEntityTypeException.class};
		}

		@Override
		public String docs() {
			return "void {npcID, string} Sets the entity type of the NPC. If the type is not a living entity type, the function will throw a BadEntityTypeException.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			MCEntityType type;
			try {
				type = MCEntityType.valueOf(args[1].val().toUpperCase());
			} catch (IllegalArgumentException exception) {
				throw new CREFormatException("Bad entity type :" + args[1].val() + ".", t);
			}
			try {
				CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).setEntityType(type);
			} catch (IllegalArgumentException exception) {
				throw new CREBadEntityTypeException("The given entity type (" + args[1].val() + ") is not a living entity type.", t);
			}
			return CVoid.VOID;
		}
	}

	@api
	public static final class ctz_npc_name extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "string {npcID} Returns the name of the NPC.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			return new CString(CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).getName(), t);
		}
	}

	@api
	public static final class ctz_set_npc_name extends CitizensNPCSetterFunction {

		@Override
		public String docs() {
			return "void {npcID, string} Sets the name of the NPC.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).setName(args[1].val());
			return CVoid.VOID;
		}
	}

	@api
	public static final class ctz_npc_full_name extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "string {npcID} Returns the full name of the NPC.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			return new CString(CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).getFullName(), t);
		}
	}

	@api
	public static final class ctz_npc_is_protected extends CitizensNPCGetterFunction {

		@Override
		public String docs() {
			return "boolean {npcID} Returns if the NPC is protected.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			return CBoolean.get(CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).isProtected());
		}
	}

	@api
	public static final class ctz_set_npc_protected extends CitizensNPCSetterFunction {

		@Override
		public String docs() {
			return "void {npcID, boolean} Sets if the NPC is protected.";
		}

		@Override
		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(ArgumentValidation.getInt32(args[0], t), t).setProtected(ArgumentValidation.getBooleanObject(args[1], t));
			return CVoid.VOID;
		}
	}
}