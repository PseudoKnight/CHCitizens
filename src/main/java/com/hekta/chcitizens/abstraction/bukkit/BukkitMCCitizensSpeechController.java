package com.hekta.chcitizens.abstraction.bukkit;

import net.citizensnpcs.api.ai.speech.SpeechController;

import com.hekta.chcitizens.abstraction.MCCitizensSpeechContext;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechController;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensSpeechController implements MCCitizensSpeechController {

	private final SpeechController _controller;

	public BukkitMCCitizensSpeechController(SpeechController speechController) {
		_controller = speechController;
	}

	@Override
	public SpeechController getHandle() {
		return _controller;
	}

	@Override
	public void speak(MCCitizensSpeechContext message) {
		_controller.speak(((BukkitMCCitizensSpeechContext) message).getHandle());
	}
}