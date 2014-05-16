package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.ai.speech.Talkable;

import com.laytonsmith.abstraction.bukkit.BukkitMCEntity;

import com.hekta.chcitizens.abstraction.MCCitizensSpeechContext;
import com.hekta.chcitizens.abstraction.MCCitizensTalkable;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensSpeechContext implements MCCitizensSpeechContext {

	private final SpeechContext _context;

	public BukkitMCCitizensSpeechContext(SpeechContext speechContext) {
		_context = speechContext;
	}

	@Override
	public SpeechContext getHandle() {
		return _context;
	}

	@Override
	public boolean hasRecipients() {
		return _context.hasRecipients();
	}

	@Override
	public MCCitizensTalkable[] getRecipients() {
		MCCitizensTalkable[] recipients = new MCCitizensTalkable[_context.size()];
		int i = 0;
		for (Talkable recipient : _context) {
			recipients[i] = new BukkitMCCitizensTalkable(recipient);
			i++;
		}
		return recipients;
	}

	@Override
	public void addRecipient(MCCitizensTalkable talkable) {
		_context.addRecipient(((BukkitMCEntity) talkable.getEntity()).getHandle());
	}

	@Override
	public void addRecipients(MCCitizensTalkable[] talkables) {
		for (MCCitizensTalkable recipient : talkables) {
			_context.addRecipient(((BukkitMCEntity) recipient.getEntity()).getHandle());
		}
	}

	@Override
	public void addRecipients(Iterable<MCCitizensTalkable> talkables) {
		for (MCCitizensTalkable recipient : talkables) {
			_context.addRecipient(((BukkitMCEntity) recipient.getEntity()).getHandle());
		}
	}

	@Override
	public int size() {
		return _context.size();
	}

	@Override
	public String getMessage() {
		return _context.getMessage();
	}

	@Override
	public void setMessage(String message) {
		_context.setMessage(message);
	}

	@Override
	public MCCitizensTalkable getTalker() {
		return new BukkitMCCitizensTalkable(_context.getTalker());
	}

	@Override
	public void setTalker(MCCitizensTalkable talker) {
		_context.setTalker(((BukkitMCEntity) talker.getEntity()).getHandle());
	}
}