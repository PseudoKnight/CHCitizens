package com.hekta.chcitizens.abstraction.events;

import com.laytonsmith.abstraction.MCPlayer;

public interface MCCitizensNPCClickEvent extends MCCitizensNPCEvent {
    boolean isLeft();

    MCPlayer getClicker();
}
