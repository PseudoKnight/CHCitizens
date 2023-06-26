package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.MCPlugin;

/**
 *
 * @author Hekta
 */
public interface MCCitizensPlugin extends MCPlugin {

	MCCitizensNPCRegistry getNPCRegistry();

	MCCitizensTraitFactory getTraitFactory();
}