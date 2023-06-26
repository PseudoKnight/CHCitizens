package com.hekta.chcitizens.abstraction.bukkit;

import com.hekta.chcitizens.abstraction.*;
import com.hekta.chcitizens.abstraction.bukkit.events.BukkitCitizensListener;
import com.hekta.chcitizens.abstraction.bukkit.traits.BukkitMCCitizensOwner;
import com.laytonsmith.abstraction.Implementation.Type;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCEntity;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCLivingEntity;
import com.laytonsmith.annotations.abstraction;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.ai.speech.TalkableEntity;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Hekta
 */
@abstraction(type = Type.BUKKIT)
public class CHCitizensBukkitConvertor implements CHCitizensConvertor {

	@Override
	public void startup() {
		BukkitCitizensListener.register();
	}

	@Override
	public void shutdown() {
		BukkitCitizensListener.unregister();
	}

	@Override
	public MCCitizensPlugin getCitizens() {
		Plugin plugin = (Plugin) CommandHelperPlugin.myServer.getPluginManager().getPlugin("Citizens").getHandle();
		if (plugin != null) {
			return new BukkitMCCitizensPlugin((CitizensPlugin) plugin);
		} else {
			return null;
		}
	}

	@Override
	public MCCitizensTrait getCorrectTrait(MCCitizensTrait trait) {
		String name = trait.getName();
		if (name.equals("owner")) {
			return new BukkitMCCitizensOwner((Owner) trait.getHandle());
		} else {
			return trait;
		}
	}

	@Override
	public MCCitizensTalkable newTalkableEntity(MCEntity entity) {
		return new BukkitMCCitizensTalkable(new TalkableEntity(((BukkitMCEntity) entity).getHandle()));
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