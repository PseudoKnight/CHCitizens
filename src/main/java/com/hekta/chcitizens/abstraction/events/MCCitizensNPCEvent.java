package com.hekta.chcitizens.abstraction.events;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNPCEvent extends MCCitizensEvent {

	MCCitizensNPC getNPC();
}