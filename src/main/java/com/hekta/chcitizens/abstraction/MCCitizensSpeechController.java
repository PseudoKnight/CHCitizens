package com.hekta.chcitizens.abstraction;

/**
 *
 * @author Hekta
 */
public interface MCCitizensSpeechController {

	public void speak(MCCitizensSpeechContext message);
	public void speak(MCCitizensSpeechContext message, String vocalChordName);
}