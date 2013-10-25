package com.hekta.chcitizens.extension;

import com.laytonsmith.annotations.shutdown;
import com.laytonsmith.annotations.startup;

import com.hekta.chcitizens.abstraction.CHCitizensStaticLayer;
import com.hekta.chcitizens.abstraction.MCCitizensPlugin;

/**
 *
 * @author Hekta
 */
public class CHCitizensExtension {

	public static MCCitizensPlugin citizensPlugin;

	@startup
	public static void onEnable() {
		citizensPlugin = CHCitizensStaticLayer.getCitizensPlugin();
		if (citizensPlugin != null) {
			System.out.println("[CommandHelper] CHCitizens 1.0 loaded.");
		} else {
			System.out.println("[CommandHelper] Plugin Citizens is missing, none of the CHCitizens functions will work.");
		}
	}

	@shutdown
	public static void onDisable() {
		if (citizensPlugin != null) {
			System.out.println("[CommandHelper] CHCitizens unloaded.");
		}
	}
}