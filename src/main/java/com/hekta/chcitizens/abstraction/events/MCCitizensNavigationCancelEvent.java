package com.hekta.chcitizens.abstraction.events;

import com.hekta.chcitizens.abstraction.enums.MCCitizensCancelReason;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNavigationCancelEvent extends MCCitizensNavigationCompleteEvent {

	public MCCitizensCancelReason getCancelReason();
}