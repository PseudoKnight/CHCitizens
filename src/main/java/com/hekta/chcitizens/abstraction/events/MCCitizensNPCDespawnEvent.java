package com.hekta.chcitizens.abstraction.events;

import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNPCDespawnEvent extends MCCitizensNPCEvent {

	MCCitizensDespawnReason getReason();
}