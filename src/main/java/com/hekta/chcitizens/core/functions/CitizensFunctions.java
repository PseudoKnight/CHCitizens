package com.hekta.chcitizens.core.functions;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.MSVersion;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREInvalidPluginException;
import com.laytonsmith.core.exceptions.CRE.CRENotFoundException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.functions.AbstractFunction;

/**
 *
 * @author Hekta
 */
public abstract class CitizensFunctions {

	protected static abstract class CitizensNPCFunction extends AbstractFunction {

		@Override
		public String getName() {
			return getClass().getSimpleName();
		}

		@Override
		public boolean isRestricted() {
			return true;
		}

		@Override
		public Boolean runAsync() {
			return false;
		}

		@Override
		public Version since() {
			return MSVersion.V3_3_1;
		}
	}

	protected static abstract class CitizensNPCGetterFunction extends CitizensNPCFunction {

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRECastException.class, CRENotFoundException.class};
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{1};
		}
	}

	protected static abstract class CitizensNPCSetterFunction extends CitizensNPCFunction {

		@Override
		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREInvalidPluginException.class, CRECastException.class, CRENotFoundException.class};
		}

		@Override
		public Integer[] numArgs() {
			return new Integer[]{2};
		}
	}
}