package com.hekta.chcitizens.abstraction;

import java.util.Set;

import com.laytonsmith.abstraction.MCLivingEntity;

/**
 *
 * @author Hekta
 */
public interface MCCitizensSpeechFactory {

	public MCCitizensTalkable newTalkableEntity(MCLivingEntity entity);

	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, MCCitizensTalkable recipient);
	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, Set<MCCitizensTalkable> recipients);
}