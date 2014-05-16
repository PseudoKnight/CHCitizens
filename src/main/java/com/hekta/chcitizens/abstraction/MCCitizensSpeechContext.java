package com.hekta.chcitizens.abstraction;

import com.laytonsmith.abstraction.AbstractionObject;

/**
 *
 * @author Hekta
 */
public interface MCCitizensSpeechContext extends AbstractionObject {

	public boolean hasRecipients();
	public MCCitizensTalkable[] getRecipients();
	public void addRecipient(MCCitizensTalkable talkable);
	public void addRecipients(MCCitizensTalkable[] talkables);
	public void addRecipients(Iterable<MCCitizensTalkable> talkables);

	public int size();

	public String getMessage();
	public void setMessage(String message);

	public MCCitizensTalkable getTalker();
	public void setTalker(MCCitizensTalkable talker);
}