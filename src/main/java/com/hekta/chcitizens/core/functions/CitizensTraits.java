package com.hekta.chcitizens.core.functions;

import com.laytonsmith.annotations.api;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.traits.MCCitizensOwner;
import com.hekta.chcitizens.core.CHCitizensStatic;
import com.hekta.chcitizens.core.functions.CitizensFunctions.CitizensNPCFunction;

/**
 *
 * @author Hekta
 */
public class CitizensTraits {

	public static String docs() {
		return "This class allows to manage the traits of the NPCs.";
	}

	@api
	public static class ctz_npc_traits extends CitizensNPCFunction {

		public String getName() {
			return "ctz_npc_traits";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.NotFoundException, ExceptionType.IndexOverflowException};
		}

		public String docs() {
			return "array {npcID, [traitName]} Returns an associative array representing the traits the NPC has, or representing the trait with the given name."
						+ " a trait is an associative array of one or more values, or null if the NOC does not have the trait."
						+ " Actually, supported traits are OWNER.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t);
			String trait;
			if (args.length == 1) {
				trait = null;
			} else {
				trait = args[1].val();
			}
			CArray traitArray = new CArray(t);
			//owner trait
			if ((trait == null) || trait.equalsIgnoreCase("owner")) {
				if (npc.hasTrait("owner")) {
					CArray ownerArray = new CArray(t);
					ownerArray.set("owner", new CString(((MCCitizensOwner) npc.getTrait("owner")).getOwner(), t), t);
					traitArray.set("owner", ownerArray, t);
				} else {
					traitArray.set("owner", new CNull(t), t);
				}
			}
			//return
			if (trait == null) {
				return traitArray;
			} else {
				for (String key : traitArray.keySet()) {
					return traitArray.get(key);
				}
				throw new ConfigRuntimeException("Unknown or unsupported trait: " + args[1].val() + ".", ExceptionType.IndexOverflowException, t);
			}
		}
	}

	@api
	public static class ctz_set_npc_trait extends CitizensNPCFunction {

		public String getName() {
			return "ctz_set_npc_trait";
		}

		public Integer[] numArgs() {
			return new Integer[]{3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.NotFoundException, ExceptionType.IndexOverflowException, ExceptionType.CastException};
		}

		public String docs() {
			return "array {npcID, traitName, array} Sets a NPC trait. The trait must be an associative array, in the same format that it is returned by ctz_npc_traits";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t);
			String trait = args[1].val();
			CArray array = Static.getArray(args[2], t);
			if (trait.equalsIgnoreCase("owner")) {
				if (npc.hasTrait("owner")) {
					for (String key : array.keySet()) {
						if (key.equals("owner")) {
							((MCCitizensOwner) npc.getTrait("owner")).setOwner(array.get(key).val());
						} else {
							throw new ConfigRuntimeException("Invalid key:" + key + ".", ExceptionType.IndexOverflowException, t);
						}
					}
				} else {
					throw new ConfigRuntimeException("The NPC does not have the given trait:" + args[1].val() + ".", ExceptionType.NotFoundException, t);
				}
			} else {
				throw new ConfigRuntimeException("Unknown or unsupported trait: " + trait + ".", ExceptionType.IndexOverflowException, t);
			}
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_has_trait extends CitizensNPCFunction {

		public String getName() {
			return "ctz_npc_has_trait";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.NotFoundException};
		}

		public String docs() {
			return "array {npcID, traitName} Returns if the NPC has a trait with the given name.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t);
			String trait = args[1].val();
			if (trait.equalsIgnoreCase("owner")) {
				return new CBoolean(npc.hasTrait("owner"), t);
			} else {
				throw new ConfigRuntimeException("Unknown or unsupported trait: " + trait + ".", ExceptionType.IndexOverflowException, t);
			}
		}
	}
}