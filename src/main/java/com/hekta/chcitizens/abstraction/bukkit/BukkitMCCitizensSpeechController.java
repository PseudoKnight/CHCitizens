package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.speech.SpeechController;

import com.hekta.chcitizens.abstraction.MCCitizensSpeechContext;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechController;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensSpeechController implements MCCitizensSpeechController {

	SpeechController sc;

	public BukkitMCCitizensSpeechController(SpeechController speechController) {
		this.sc = speechController;
	}

	public SpeechController getConcrete() {
		return sc;
	}

	public void speak(MCCitizensSpeechContext message) {
		sc.speak(((BukkitMCCitizensSpeechContext) message).getConcrete());
	}

	public void speak(MCCitizensSpeechContext message, String vocalChordName) {
		sc.speak(((BukkitMCCitizensSpeechContext) message).getConcrete(), vocalChordName);
	}
}