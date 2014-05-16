package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.speech.Talkable;

import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.BukkitConvertor;

import com.hekta.chcitizens.abstraction.MCCitizensTalkable;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensTalkable implements MCCitizensTalkable {

	private final Talkable _talkable;

	public BukkitMCCitizensTalkable(Talkable talkable) {
		_talkable = talkable;
	}

	@Override
	public Talkable getHandle() {
		return _talkable;
	}

	@Override
	public MCEntity getEntity() {
		return BukkitConvertor.BukkitGetCorrectEntity(_talkable.getEntity());
	}

	@Override
	public String getName() {
		return _talkable.getName();
	}
}