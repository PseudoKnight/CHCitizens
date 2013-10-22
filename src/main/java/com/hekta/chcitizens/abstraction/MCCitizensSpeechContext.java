package com.hekta.chcitizens.abstraction;

import java.util.Set;

/**
 *
 * @author Hekta
 */
public interface MCCitizensSpeechContext {

	public boolean hasRecipients();
	public Set<MCCitizensTalkable> getRecipients();
	public void addRecipient(MCCitizensTalkable talkable);
	public void addRecipients(Set<MCCitizensTalkable> talkables);

	public int size();

	public String getMessage();
	public void setMessage(String message);

	public MCCitizensTalkable getTalker();
	public void setTalker(MCCitizensTalkable talker);
}