package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.speech.Talkable;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;

import com.hekta.chcitizens.abstraction.MCCitizensTalkable;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensTalkable implements MCCitizensTalkable {

	Talkable t;

	public BukkitMCCitizensTalkable(Talkable talkable) {
		this.t = talkable;
	}

	public Talkable getConcrete() {
		return t;
	}

	public MCLivingEntity getEntity() {
		return new BukkitMCLivingEntity(t.getEntity());
	}

	public String getName() {
		return t.getName();
	}
}