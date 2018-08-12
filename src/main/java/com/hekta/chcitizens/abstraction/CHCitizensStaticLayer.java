package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.PureUtilities.ClassLoading.ClassDiscovery;

import com.laytonsmith.annotations.abstraction;
import com.laytonsmith.core.InternalException;

/**
 *
 * @author Hekta
 */
public final class CHCitizensStaticLayer {

	private static final CHCitizensConvertor _convertor = getConvertorInstance();

	private CHCitizensStaticLayer() {
	}

	private static CHCitizensConvertor getConvertorInstance() {
		CHCitizensConvertor convertor = null;
		for (Class<? extends CHCitizensConvertor> clazz : ClassDiscovery.getDefaultInstance().loadClassesWithAnnotationThatExtend(abstraction.class, CHCitizensConvertor.class)) {
			if (clazz.getAnnotation(abstraction.class).type().equals(Implementation.GetServerType())) {
				if (convertor == null) {
					try {
						convertor = clazz.newInstance();
					} catch (InstantiationException | IllegalAccessException ex) {
						throw (RuntimeException) new InternalException().initCause(ex);
					}
				} else {
					throw new InternalException("More than one CHCitizensConvertor implementation detected.");
				}
			}
		}
		if (convertor != null) {
			return convertor;
		} else {
			throw new InternalException("No CHCitizensConvertor implementation detected.");
		}
	}

	public static CHCitizensConvertor getConvertor() {
		return _convertor;
	}

	public static void startup() {
		_convertor.startup();
	}

	public static void shutdown() {
		_convertor.shutdown();
	}

	public static MCCitizensPlugin getCitizens() {
		return _convertor.getCitizens();
	}

	public static MCCitizensTrait getCorrectTrait(MCCitizensTrait trait) {
		return _convertor.getCorrectTrait(trait);
	}
}