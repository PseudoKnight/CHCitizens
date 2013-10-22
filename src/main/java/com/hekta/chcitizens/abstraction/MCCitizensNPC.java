package com.hekta.chcitizens.abstraction;

import java.util.Set;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.enums.MCEntityType;

import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNPC {

	public Set<MCCitizensTrait> getTraits();
	public void addTrait(MCCitizensTrait trait);

//	public MCCitizensMetadataStore getData();

	public boolean spawn(MCLocation location);
	public boolean despawn(MCCitizensDespawnReason reason);
	public boolean isSpawned();

	public void destroy();

	public void faceLocation(MCLocation location);
	public MCLocation getStoredLocation();

	public MCLivingEntity getEntity();
	public void setEntityType(MCEntityType type);

	public MCCitizensGoalController getDefaultGoalController();
	public MCCitizensNavigator getNavigator();
	public MCCitizensSpeechController getDefaultSpeechController();

	public int getId();

	public String getName();
	public void setName(String name);
	public String getFullName();

	public boolean isProtected();
	public void setProtected(boolean isProtected);
}