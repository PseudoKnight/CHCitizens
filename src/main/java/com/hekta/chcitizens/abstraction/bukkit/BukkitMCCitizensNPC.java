package com.hekta.chcitizens.abstraction.bukkit;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.abstraction.enums.MCEntityType;
import com.laytonsmith.abstraction.enums.bukkit.BukkitMCEntityType;

import com.hekta.chcitizens.abstraction.MCCitizensGoalController;
import com.hekta.chcitizens.abstraction.MCCitizensNavigator;
import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechController;
import com.hekta.chcitizens.abstraction.MCCitizensTrait;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.abstraction.enums.bukkit.BukkitMCCitizensDespawnReason;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensNPC implements MCCitizensNPC {

	NPC n;

	public BukkitMCCitizensNPC(NPC npc) {
		this.n = npc;
	}

	public NPC getConcrete() {
		return n;
	}

	public Set<MCCitizensTrait> getTraits() {
		Set<MCCitizensTrait> traits = new HashSet<MCCitizensTrait>();
		for (Trait trait : n.getTraits()) {
			traits.add(new BukkitMCCitizensTrait(trait));
		}
		return traits;
	}

	public void addTrait(MCCitizensTrait trait) {
		n.addTrait(((BukkitMCCitizensTrait) trait).getConcrete());
	}

	public boolean spawn(MCLocation location) {
		return n.spawn(((BukkitMCLocation) location).asLocation());
	}

	public boolean despawn(MCCitizensDespawnReason reason) {
		return n.despawn(BukkitMCCitizensDespawnReason.getConvertor().getConcreteEnum(reason));
	}

	public boolean isSpawned() {
		return n.isSpawned();
	}

	public void destroy() {
		n.destroy();
	}

	public void faceLocation(MCLocation location) {
		n.faceLocation(((BukkitMCLocation) location).asLocation());
	}

	public MCLocation getStoredLocation() {
		Location location = n.getStoredLocation();
		if (location != null) {
			return new BukkitMCLocation(location);
		} else {
			return null;
		}
	}

	public MCLivingEntity getEntity() {
		if (n.isSpawned()) {
			return new BukkitMCLivingEntity(n.getBukkitEntity());
		} else {
			return null;
		}
	}

	public void setEntityType(MCEntityType type) {
		n.setBukkitEntityType(BukkitMCEntityType.getConvertor().getConcreteEnum(type));
	}

	public MCCitizensGoalController getDefaultGoalController() {
		return new BukkitMCCitizensGoalController(n.getDefaultGoalController());
	}

	public MCCitizensNavigator getNavigator() {
		return new BukkitMCCitizensNavigator(n.getNavigator());
	}

	public MCCitizensSpeechController getDefaultSpeechController() {
		return new BukkitMCCitizensSpeechController(n.getDefaultSpeechController());
	}

	public int getId() {
		return n.getId();
	}

	public String getName() {
		return n.getName();
	}

	public void setName(String name) {
		n.setName(name);
	}

	public String getFullName() {
		return n.getFullName();
	}

	public boolean isProtected() {
		return n.isProtected();
	}

	public void setProtected(boolean isProtected) {
		n.setProtected(isProtected);
	}
}