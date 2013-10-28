package com.hekta.chcitizens.abstraction.events;

import com.laytonsmith.core.events.BindableEvent;

import com.hekta.chcitizens.abstraction.MCCitizensNavigator;
import com.hekta.chcitizens.abstraction.MCCitizensNPC;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNavigationEvent extends BindableEvent {

	public MCCitizensNavigator getNavigator();

	public MCCitizensNPC getNPC();
}