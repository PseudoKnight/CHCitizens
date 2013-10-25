package com.hekta.chcitizens.core.functions;

import java.util.HashSet;
import java.util.Set;

import com.laytonsmith.annotations.api;
import com.laytonsmith.abstraction.MCLocation;
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

import com.hekta.chcitizens.abstraction.MCCitizensEntityTarget;
import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensNavigator;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechFactory;
import com.hekta.chcitizens.abstraction.MCCitizensTalkable;
import com.hekta.chcitizens.abstraction.enums.MCCitizensTargetType;
import com.hekta.chcitizens.core.CHCitizensStatic;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.core.constructs.CDouble;

/**
 *
 * @author Hekta
 */
public class CitizensAI {

	public static String docs() {
		return "This class allows to interact with the AI of the NPCs.";
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
	public static class ctz_npc_target extends CitizensNPCFunction {

		public String getName() {
			return "ctz_npc_target";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.NotFoundException, ExceptionType.FormatException};
		}

		public String docs() {
			return "mixed {npcID, [field]} Returns the target of the given NPC. Field can be one of 'location', 'entity' or 'type', default to 'type'."
						+ " If field is 'location', the function will returns a location array, else if field is 'entity', the function fill return the entity id,"
						+ " else if field is 'type', the function will return the target type (can be " + StringUtils.Join(MCCitizensTargetType.values(), ", ", ", or ", " or ") + ")."
						+ " The function will return null if the NPC has not a target, or if the field is 'entity' and that the target type is a location.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			if ((args.length == 1) || args[1].val().equalsIgnoreCase("LOCATION")) {
				MCLocation location = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getTargetAsLocation();
				if (location != null) {
					return ObjectGenerator.GetGenerator().location(location);
				} else {
					return new CNull(t);
				}
			} else if (args[1].val().equalsIgnoreCase("ENTITY")) {
				MCCitizensEntityTarget target = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getEntityTarget();
				if (target != null) {
					return new CInt(target.getTarget().getEntityId(), t);
				} else {
					return new CNull(t);
				}
			} else if (args[1].val().equalsIgnoreCase("TYPE")) {
				return new CString(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getTargetType().toString(), t);
			} else {
				throw new ConfigRuntimeException("Invalid field:'" + args[1].val() + "'.", ExceptionType.FormatException, t);
			}
		}
	}

	@api
	public static class ctz_set_npc_target extends CitizensNPCFunction {

		public String getName() {
			return "ctz_set_npc_target";
		}

		public Integer[] numArgs() {
			return new Integer[]{2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.NotFoundException, ExceptionType.PluginInternalException};
		}

		public String docs() {
			return "void {npcID, target, [isAggressive]} Sets the target of the given NPC. target can be a location array or an entityID."
						+ " If target is an entityID, isAggressive (a boolean) will specify if the NPC will attack the targeted entity, defaut to false.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t);
			if (!npc.isSpawned()) {
				throw new ConfigRuntimeException("The NPC is not spawned.", ExceptionType.PluginInternalException, t);
			}
			if (args[1] instanceof CArray) {
				npc.getNavigator().setTarget(ObjectGenerator.GetGenerator().location(args[1], npc.getEntity().getWorld(), t));
			} else {
				boolean isAggressive;
				if (args.length == 2) {
					isAggressive = false;
				} else {
					isAggressive = Static.getBoolean(args[2]);
				}
				npc.getNavigator().setTarget(Static.getLivingEntity(Static.getInt32(args[1], t), t), isAggressive);
			}
			CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).setProtected(Static.getBoolean(args[1]));
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_is_aggressive extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_is_aggressive";
		}

		public String docs() {
			return "boolean {npcID} Returns if the given NPC is aggressive.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensEntityTarget target = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getEntityTarget();
			if (target != null) {
				return new CBoolean(target.isAggressive(), t);
			} else {
				return new CBoolean(false, t);
			}
		}
	}

	@api
	public static class ctz_npc_is_navigating extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_is_navigating";
		}

		public String docs() {
			return "boolean {npcID} Returns if the given NPC is navigating.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().isNavigating(), t);
		}
	}

	@api
	public static class ctz_cancel_npc_navigation extends CitizensNPCSetterFunction {

		public String getName() {
			return "ctz_cancel_npc_navigation";
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{0};
		}

		public String docs() {
			return "void {npcID} Cancels the current navigation of the given NPC towards a target.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().cancelNavigation();
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_avoid_water extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_avoid_water";
		}

		public String docs() {
			return "boolean {npcID} Returns if the given NPC has to avoid water.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CBoolean(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getLocalParameters().getAvoidWater(), t);
		}
	}

	@api
	public static class ctz_set_npc_avoid_water extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_set_npc_avoid_water";
		}

		public String docs() {
			return "void {npcID, boolean} Sets if the given NPC has to avoid water.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator();
			boolean avoidWater = Static.getBoolean(args[1]);
			navigator.getDefaultParameters().setAvoidWater(avoidWater);
			navigator.getLocalParameters().setAvoidWater(avoidWater);
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_speed extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_speed";
		}

		public String docs() {
			return "double {npcID} Returns the speed of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CDouble(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getLocalParameters().getSpeed(), t);
		}
	}

	@api
	public static class ctz_npc_base_speed extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_base_speed";
		}

		public String docs() {
			return "double {npcID} Returns the base speed of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CDouble(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getLocalParameters().getBaseSpeed(), t);
		}
	}

	@api
	public static class ctz_set_npc_base_speed extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_set_npc_base_speed";
		}

		public String docs() {
			return "void {npcID, double} Sets the base speed of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator();
			float baseSpeed = Static.getDouble32(args[1], t);
			navigator.getDefaultParameters().setBaseSpeed(baseSpeed);
			navigator.getLocalParameters().setBaseSpeed(baseSpeed);
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_speed_modifier extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_speed_modifier";
		}

		public String docs() {
			return "double {npcID} Returns the speed modifier of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CDouble(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getLocalParameters().getSpeedModifier(), t);
		}
	}

	@api
	public static class ctz_set_npc_speed_modifier extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_set_npc_speed_modifier";
		}

		public String docs() {
			return "void {npcID, double} Sets the speed modifier of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator();
			float speedModifier = Static.getDouble32(args[1], t);
			navigator.getDefaultParameters().setSpeedModifier(speedModifier);
			navigator.getLocalParameters().setSpeedModifier(speedModifier);
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_distance_margin extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_distance_margin";
		}

		public String docs() {
			return "double {npcID} Returns the distance margin of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CDouble(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getLocalParameters().getDistanceMargin(), t);
		}
	}

	@api
	public static class ctz_set_npc_distance_margin extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_set_npc_distance_margin";
		}

		public String docs() {
			return "void {npcID, double} Sets the distance margin of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator();
			double distanceMargin = Static.getDouble(args[1], t);
			navigator.getDefaultParameters().setDistanceMargin(distanceMargin);
			navigator.getLocalParameters().setDistanceMargin(distanceMargin);
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_range extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_range";
		}

		public String docs() {
			return "double {npcID} Returns the range of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CDouble(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getLocalParameters().getRange(), t);
		}
	}

	@api
	public static class ctz_set_npc_range extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_set_npc_range";
		}

		public String docs() {
			return "void {npcID, double} Sets the range of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator();
			float range = Static.getDouble32(args[1], t);
			navigator.getDefaultParameters().setRange(range);
			navigator.getLocalParameters().setRange(range);
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_stationary_ticks extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_npc_stationary_ticks";
		}

		public String docs() {
			return "int {npcID} Returns the stationary ticks of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			return new CDouble(CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator().getLocalParameters().getStationaryTicks(), t);
		}
	}

	@api
	public static class ctz_set_npc_stationary_ticks extends CitizensNPCGetterFunction {

		public String getName() {
			return "ctz_set_npc_stationary_ticks";
		}

		public String docs() {
			return "void {npcID, int} Sets the stationary ticks of the given NPC.";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNavigator navigator = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t).getNavigator();
			int ticks = Static.getInt32(args[1], t);
			navigator.getDefaultParameters().setStationaryTicks(ticks);
			navigator.getLocalParameters().setStationaryTicks(ticks);
			return new CVoid(t);
		}
	}

	@api
	public static class ctz_npc_speak extends CitizensNPCFunction {

		public String getName() {
			return "ctz_npc_speak";
		}

		public Integer[] numArgs() {
			return new Integer[]{2, 3};
		}

		public ExceptionType[] thrown() {
			return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.PlayerOfflineException, ExceptionType.BadEntityException};
		}

		public String docs() {
			return "void {npcID, message, [recipients]} Makes the given NPC speak a message to the given recipients."
						+ " recipients can take an array of player names and/or living entity ids, an int (living entity id), or a string (player name).";
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			MCCitizensNPC npc = CHCitizensStatic.getNPC(Static.getInt32(args[0], t), t);
			MCCitizensSpeechFactory speechFactory = CHCitizensStatic.getCitizensPlugin(t).getSpeechFactory();
			if (args.length == 2) {
				Set<MCCitizensTalkable> recipients = new HashSet<MCCitizensTalkable>();
				for (MCPlayer player : Static.getServer().getOnlinePlayers()) {
					recipients.add(speechFactory.newTalkableEntity(player));
				}
				npc.getDefaultSpeechController().speak(speechFactory.newSpeechContext(npc, args[1].val(), recipients));
			} else if (args[2] instanceof CArray) {
				CArray array = Static.getArray(args[2], t);
				if (array.inAssociativeMode()) {
					throw new ConfigRuntimeException("The array of recipients must not be associative.", ExceptionType.CastException, t);
				}
				Set<MCCitizensTalkable> recipients = new HashSet<MCCitizensTalkable>();
				for (Construct recipient : array.asList()) {
					if (recipient instanceof CInt) {
						recipients.add(speechFactory.newTalkableEntity(Static.getLivingEntity(Static.getInt32(args[2], t), t)));
					} else {
						recipients.add(speechFactory.newTalkableEntity(Static.GetPlayer(recipient.val(), t)));
					}
				}
				npc.getDefaultSpeechController().speak(speechFactory.newSpeechContext(npc, args[1].val(), recipients));
			} else if (args[2] instanceof CInt) {
				npc.getDefaultSpeechController().speak(speechFactory.newSpeechContext(npc, args[1].val(), speechFactory.newTalkableEntity(Static.getLivingEntity(Static.getInt32(args[2], t), t))));
			} else {
				npc.getDefaultSpeechController().speak(speechFactory.newSpeechContext(npc, args[1].val(), speechFactory.newTalkableEntity(Static.GetPlayer(args[2].val(), t))));
			}
			return new CVoid(t);
		}
	}
}