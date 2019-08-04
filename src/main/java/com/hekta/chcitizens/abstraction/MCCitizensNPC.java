package com.hekta.chcitizens.abstraction;

import java.util.Set;
import java.util.UUID;

import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.enums.MCEntityType;

import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNPC extends AbstractionObject {

	public Set<MCCitizensTrait> getTraits();
	public MCCitizensTrait getTrait(String name);
	public boolean hasTrait(String name);

	public boolean spawn(MCLocation location);
	public boolean despawn(MCCitizensDespawnReason reason);
	public boolean isSpawned();

	public void destroy();

	public void faceLocation(MCLocation location);
	public MCLocation getStoredLocation();

	public MCEntity getEntity();
	public void setEntityType(MCEntityType type);

	public MCCitizensGoalController getDefaultGoalController();
	public MCCitizensNavigator getNavigator();
	public MCCitizensSpeechController getDefaultSpeechController();

	public int getId();
	public UUID getUniqueId();

	public String getName();
	public void setName(String name);
	public String getFullName();

	public boolean isProtected();
	public void setProtected(boolean isProtected);
}