package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.ai.speech.SpeechFactory;

import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechContext;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechFactory;
import com.hekta.chcitizens.abstraction.MCCitizensTalkable;
import java.util.Collection;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensSpeechFactory implements MCCitizensSpeechFactory {

	private final SpeechFactory _factory;

	public BukkitMCCitizensSpeechFactory(SpeechFactory speechFactory) {
		_factory = speechFactory;
	}

	@Override
	public SpeechFactory getHandle() {
		return _factory;
	}

	@Override
	public MCCitizensTalkable newTalkableEntity(MCEntity entity) {
		return new BukkitMCCitizensTalkable(_factory.newTalkableEntity(((BukkitMCEntity) entity).getHandle()));
	}

	@Override
	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, MCCitizensTalkable recipient) {
		return new BukkitMCCitizensSpeechContext(new SpeechContext(((BukkitMCCitizensNPC) talker).getHandle(), message, ((BukkitMCLivingEntity) recipient.getEntity()).asLivingEntity()));
	}

	@Override
	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, MCCitizensTalkable[] recipients) {
		MCCitizensSpeechContext speechContext = new BukkitMCCitizensSpeechContext(new SpeechContext(((BukkitMCCitizensNPC) talker).getHandle(), message));
		speechContext.addRecipients(recipients);
		return speechContext;
	}

	@Override
	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, Iterable<MCCitizensTalkable> recipients) {
		MCCitizensSpeechContext speechContext = new BukkitMCCitizensSpeechContext(new SpeechContext(((BukkitMCCitizensNPC) talker).getHandle(), message));
		speechContext.addRecipients(recipients);
		return speechContext;
	}
}