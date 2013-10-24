package com.hekta.chcitizens.abstraction.bukkit;

import java.util.Set;

import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.ai.speech.SpeechFactory;
import net.citizensnpcs.api.ai.speech.Talkable;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechContext;
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

	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, MCCitizensTalkable recipient) {
		return new BukkitMCCitizensSpeechContext(new SpeechContext(((BukkitMCCitizensNPC) talker).getConcrete(), message, ((BukkitMCLivingEntity) recipient.getEntity()).asLivingEntity()));
	}

	public MCCitizensSpeechContext newSpeechContext(MCCitizensNPC talker, String message, Set<MCCitizensTalkable> recipients) {
		MCCitizensSpeechContext speechContext = new BukkitMCCitizensSpeechContext(new SpeechContext(((BukkitMCCitizensNPC) talker).getConcrete(), message));
		speechContext.addRecipients(recipients);
		return speechContext;
	}
}