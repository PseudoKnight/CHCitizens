package com.hekta.chcitizens.abstraction;

/**
 *
 * @author Hekta
 */
public interface CHCitizensConvertor {

	public void startup();

	public void shutdown();

	public MCCitizensPlugin getCitizens();

	public MCCitizensTrait getCorrectTrait(MCCitizensTrait trait);
}