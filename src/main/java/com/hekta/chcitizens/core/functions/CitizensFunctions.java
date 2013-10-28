package com.hekta.chcitizens.core.functions;

import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

/**
 *
 * @author Hekta
 */
public class CitizensFunctions {

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
}