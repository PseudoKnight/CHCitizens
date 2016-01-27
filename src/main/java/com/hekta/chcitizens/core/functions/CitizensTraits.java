package com.hekta.chcitizens.core.functions;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.traits.MCCitizensOwner;
import com.hekta.chcitizens.core.CHCitizensStatic;
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
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREIndexOverflowException;
import com.laytonsmith.core.exceptions.CRE.CREInvalidPluginException;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;

/**
 *
 * @author Hekta
 */
public abstract class CitizensTraits extends CitizensFunctions {

	public static String docs() {
		return "This class allows to manage the traits of the NPCs.";
	}

	@api
	public static final class ctz_npc_traits extends CitizensNPCFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRENotFoundException.class, CREIndexOverflowException.class};
		}

		@Override
		public String docs() {
			return "array {npcID, [traitName]} Returns an associative array representing the traits the NPC has, or representing the trait with the given name."
						+ " a trait is an associative array of one or more values, or null if the NOC does not have the trait."
						+ " Actually, supported traits are OWNER.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException{
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
					ownerArray.set("owner", new CString(((MCCitizensOwner) npc.getTrait("owner")).getOwner().getName(), t), t);
					traitArray.set("owner", ownerArray, t);
				} else {
					traitArray.set("owner", CNull.NULL, t);
				}
			}
			//return
			if (trait == null) {
				return traitArray;
			} else {
				for (String key : traitArray.stringKeySet()) {
					return traitArray.get(key, t);
				}
				throw new CREIndexOverflowException("Unknown or unsupported trait: " + args[1].val() + ".", t);
			}
		}
	}

	@api
	public static final class ctz_set_npc_trait extends CitizensNPCFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{3};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRENotFoundException.class, CREIndexOverflowException.class, CRECastException.class};
		}

		@Override
		public String docs() {
			return "array {npcID, traitName, array} Sets a NPC trait. The trait must be an associative array, in the same format that it is returned by ctz_npc_traits";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t);
			String trait = args[1].val();
			CArray array = Static.getArray(args[2], t);
			if (trait.equalsIgnoreCase("owner")) {
				if (npc.hasTrait("owner")) {
					for (String key : array.stringKeySet()) {
						if (key.equals("owner")) {
							((MCCitizensOwner) npc.getTrait("owner")).setOwner(Static.getServer().getOfflinePlayer(array.get(key, t).val()));
						} else {
							throw new CREIndexOverflowException("Invalid key:" + key + ".", t);
						}
					}
				} else {
					throw new CRENotFoundException("The NPC does not have the given trait:" + args[1].val() + ".", t);
				}
			} else {
				throw new CREIndexOverflowException("Unknown or unsupported trait: " + trait + ".", t);
			}
			return CVoid.VOID;
		}
	}

	@api
	public static final class ctz_npc_has_trait extends CitizensNPCFunction {

		@Override
		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRENotFoundException.class};
		}

		@Override
		public String docs() {
			return "array {npcID, traitName} Returns if the NPC has a trait with the given name.";
		}

		@Override
		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t);
			String trait = args[1].val();
			if (trait.equalsIgnoreCase("owner")) {
				return CBoolean.get(npc.hasTrait("owner"));
			} else {
				throw new CREIndexOverflowException("Unknown or unsupported trait: " + trait + ".", t);
			}
		}
	}
}