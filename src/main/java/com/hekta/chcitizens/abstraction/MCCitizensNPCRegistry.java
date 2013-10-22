package com.hekta.chcitizens.abstraction;

import java.util.Set;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.enums.MCEntityType;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNPCRegistry {

	public MCCitizensNPC createNPC(MCEntityType type, int id, String name);
	public MCCitizensNPC createNPC(MCEntityType type, String name);

	public void deregisterNPC(MCCitizensNPC npc);

	public Set<MCCitizensNPC> getNPCs();
	public MCCitizensNPC getNPC(MCLivingEntity entity);
	public boolean isNPC(MCLivingEntity entity);
	public MCCitizensNPC getNPCById(int id);
}