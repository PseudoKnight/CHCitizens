package com.hekta.chcitizens.abstraction;

/**
 *
 * @author Hekta
 */
public interface MCCitizensPlugin {

	public MCCitizensNPCRegistry getNPCRegistry();

	public MCCitizensSpeechFactory getSpeechFactory();

	public MCCitizensTraitFactory getTraitFactory();
}