package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.MCLivingEntity;

/**
 *
 * @author Hekta
 */
public interface MCCitizensSpeechFactory {

	public MCCitizensTalkable newTalkableEntity(MCLivingEntity entity);
}