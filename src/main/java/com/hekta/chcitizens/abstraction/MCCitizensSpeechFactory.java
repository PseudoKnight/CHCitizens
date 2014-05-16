package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCEntity;
import java.util.Collection;

/**
 *
 * @author Hekta
 */
public interface MCCitizensSpeechFactory extends AbstractionObject {

	public MCCitizensTalkable newTalkableEntity(MCEntity entity);

	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, MCCitizensTalkable recipient);
	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, MCCitizensTalkable[] recipients);
	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, Iterable<MCCitizensTalkable> recipients);
}