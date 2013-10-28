package com.hekta.chcitizens.abstraction.events;

import com.laytonsmith.abstraction.MCLocation;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNPCSpawnEvent extends MCCitizensNPCEvent {

	public MCLocation getLocation();
}