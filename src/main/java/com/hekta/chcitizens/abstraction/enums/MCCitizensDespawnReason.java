package com.hekta.chcitizens.abstraction.enums;

import com.laytonsmith.annotations.MEnum;

/**
 *
 * @author Hekta
 */
@MEnum("DespawnReason")
public enum MCCitizensDespawnReason {
	CHUNK_UNLOAD,
	DEATH,
	PENDING_RESPAWN,
	PLUGIN,
	REMOVAL,
	WORLD_UNLOAD;
}