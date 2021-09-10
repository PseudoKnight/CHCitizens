package com.hekta.chcitizens.abstraction.bukkit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;

import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitConvertor;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.abstraction.enums.MCEntityType;
import com.laytonsmith.abstraction.enums.bukkit.BukkitMCEntityType;

import com.hekta.chcitizens.abstraction.CHCitizensStaticLayer;
import com.hekta.chcitizens.abstraction.MCCitizensGoalController;
import com.hekta.chcitizens.abstraction.MCCitizensNavigator;
import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensSpeechController;
import com.hekta.chcitizens.abstraction.MCCitizensTrait;
import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;
import com.hekta.chcitizens.abstraction.enums.bukkit.BukkitMCCitizensDespawnReason;
import org.bukkit.entity.Entity;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensNPC implements MCCitizensNPC {

	private final NPC _npc;

	public BukkitMCCitizensNPC(NPC npc) {
		_npc = npc;
	}

	@Override
	public NPC getHandle() {
		return _npc;
	}

	@Override
	public Set<MCCitizensTrait> getTraits() {
		Set<MCCitizensTrait> traits = new HashSet<>();
		for (Trait trait : _npc.getTraits()) {
			traits.add(CHCitizensStaticLayer.getCorrectTrait(new BukkitMCCitizensTrait(trait)));
		}
		return traits;
	}

	@Override
	public MCCitizensTrait getTrait(String name) {
		for (Trait trait : _npc.getTraits()) {
			if (trait.getName().equals(name)) {
				return CHCitizensStaticLayer.getCorrectTrait(new BukkitMCCitizensTrait(trait));
			}
		}
		return null;
	}

	@Override
	public boolean hasTrait(String name) {
		for (Trait trait : _npc.getTraits()) {
			if (trait.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean spawn(MCLocation location) {
		return _npc.spawn(((BukkitMCLocation) location).asLocation());
	}

	@Override
	public boolean despawn(MCCitizensDespawnReason reason) {
		return _npc.despawn(BukkitMCCitizensDespawnReason.getConvertor().getConcreteEnum(reason));
	}

	@Override
	public boolean isSpawned() {
		return _npc.isSpawned();
	}

	@Override
	public void destroy() {
		_npc.destroy();
	}

	@Override
	public void faceLocation(MCLocation location) {
		_npc.faceLocation(((BukkitMCLocation) location).asLocation());
	}

	@Override
	public MCLocation getStoredLocation() {
		Location location = _npc.getStoredLocation();
		if (location != null) {
			return new BukkitMCLocation(location);
		} else {
			return null;
		}
	}

	@Override
	public MCEntity getEntity() {
		Entity entity = _npc.getEntity();
		if (entity == null) {
			return null;
		}
		return BukkitConvertor.BukkitGetCorrectEntity(entity);
	}

	@Override
	public void setEntityType(MCEntityType type) {
		_npc.setBukkitEntityType(((BukkitMCEntityType) type).getConcrete());
	}

	@Override
	public MCCitizensGoalController getDefaultGoalController() {
		return new BukkitMCCitizensGoalController(_npc.getDefaultGoalController());
	}

	@Override
	public MCCitizensNavigator getNavigator() {
		return new BukkitMCCitizensNavigator(_npc.getNavigator());
	}

	@Override
	public MCCitizensSpeechController getDefaultSpeechController() {
		return new BukkitMCCitizensSpeechController(_npc.getDefaultSpeechController());
	}

	@Override
	public int getId() {
		return _npc.getId();
	}

	@Override
	public UUID getUniqueId() {
		return _npc.getUniqueId();
	}

	@Override
	public String getName() {
		return _npc.getName();
	}

	@Override
	public void setName(String name) {
		_npc.setName(name);
	}

	@Override
	public String getFullName() {
		return _npc.getFullName();
	}

	@Override
	public boolean isProtected() {
		return _npc.isProtected();
	}

	@Override
	public void setProtected(boolean isProtected) {
		_npc.setProtected(isProtected);
	}
}