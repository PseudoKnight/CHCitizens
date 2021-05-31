package com.hekta.chcitizens.abstraction.enums;

import com.laytonsmith.annotations.MEnum;

/**
 *
 * @author Hekta
 */
@MEnum("com.hekta.chcitizens.CancelReason")
public enum MCCitizensCancelReason {
	NPC_DESPAWNED,
	PLUGIN,
	REPLACE,
	STUCK,
	TARGET_DIED,
	TARGET_MOVED_WORLD;
}