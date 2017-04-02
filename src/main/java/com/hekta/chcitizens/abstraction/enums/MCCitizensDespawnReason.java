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
	RELOAD,
	REMOVAL,
	WORLD_UNLOAD;
}