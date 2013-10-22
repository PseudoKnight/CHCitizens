package com.hekta.chcitizens.abstraction.bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.ai.speech.Talkable;

import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;

import com.hekta.chcitizens.abstraction.MCCitizensSpeechContext;
import com.hekta.chcitizens.abstraction.MCCitizensTalkable;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensSpeechContext implements MCCitizensSpeechContext {

	SpeechContext sc;

	public BukkitMCCitizensSpeechContext(SpeechContext speechContext) {
		this.sc = speechContext;
	}

	public SpeechContext getConcrete() {
		return sc;
	}

	public boolean hasRecipients() {
		return sc.hasRecipients();
	}

	public Set<MCCitizensTalkable> getRecipients() {
		Set<MCCitizensTalkable> recipients = new HashSet<MCCitizensTalkable>();
		for (Talkable recipient : sc) {
			recipients.add(new BukkitMCCitizensTalkable(recipient));
		}
		return recipients;
	}

	public void addRecipient(MCCitizensTalkable talkable) {
		sc.addRecipient(((BukkitMCLivingEntity) talkable.getEntity()).asLivingEntity());
	}

	public void addRecipients(Set<MCCitizensTalkable> talkables) {
		List<Talkable> recipients = new ArrayList<Talkable>();
		for (MCCitizensTalkable recipient : talkables) {
			recipients.add(((BukkitMCCitizensTalkable) recipient).getConcrete());
		}
		sc.addRecipients(recipients);
	}

	public int size() {
		return sc.size();
	}

	public String getMessage() {
		return sc.getMessage();
	}

	public void setMessage(String message) {
		sc.setMessage(message);
	}

	public MCCitizensTalkable getTalker() {
		return new BukkitMCCitizensTalkable(sc.getTalker());
	}

	public void setTalker(MCCitizensTalkable talker) {
		sc.setTalker(((BukkitMCLivingEntity) talker.getEntity()).asLivingEntity());
	}
}