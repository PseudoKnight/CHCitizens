package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.speech.SpeechFactory;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;

import com.hekta.chcitizens.abstraction.MCCitizensSpeechFactory;
import com.hekta.chcitizens.abstraction.MCCitizensTalkable;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensSpeechFactory implements MCCitizensSpeechFactory {

	SpeechFactory sf;

	public BukkitMCCitizensSpeechFactory(SpeechFactory speechFactory) {
		this.sf = speechFactory;
	}

	public SpeechFactory getConcrete() {
		return sf;
	}

	public MCCitizensTalkable newTalkableEntity(MCLivingEntity entity) {
		return new BukkitMCCitizensTalkable(sf.newTalkableEntity(((BukkitMCLivingEntity) entity).asLivingEntity()));
	}
}