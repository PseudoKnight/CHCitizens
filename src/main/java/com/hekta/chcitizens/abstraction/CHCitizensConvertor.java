package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.MCEntity;

/**
 *
 * @author Hekta
 */
public interface CHCitizensConvertor {

	void startup();

	void shutdown();

	MCCitizensPlugin getCitizens();

	MCCitizensTrait getCorrectTrait(MCCitizensTrait trait);

	MCCitizensTalkable newTalkableEntity(MCEntity entity);

	MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, MCCitizensTalkable recipient);

	MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, MCCitizensTalkable[] recipients);

	MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, Iterable<MCCitizensTalkable> recipients);

}