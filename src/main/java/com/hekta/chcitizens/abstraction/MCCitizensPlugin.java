package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.MCPlugin;

/**
 *
 * @author Hekta
 */
public interface MCCitizensPlugin extends MCPlugin {

	public MCCitizensNPCRegistry getNPCRegistry();

	public MCCitizensSpeechFactory getSpeechFactory();

	public MCCitizensTraitFactory getTraitFactory();
}