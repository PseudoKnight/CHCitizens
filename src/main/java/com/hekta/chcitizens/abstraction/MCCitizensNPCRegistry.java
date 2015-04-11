package com.hekta.chcitizens.abstraction;

import java.util.Set;
import java.util.UUID;

import com.laytonsmith.abstraction.AbstractionObject;
import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.enums.MCEntityType;

/**
 *
 * @author Hekta
 */
public interface MCCitizensNPCRegistry extends AbstractionObject {

	public MCCitizensNPC createNPC(MCEntityType.MCVanillaEntityType type, UUID uuid, int id, String name);
	public MCCitizensNPC createNPC(MCEntityType.MCVanillaEntityType type, int id, String name);
	public MCCitizensNPC createNPC(MCEntityType.MCVanillaEntityType type, String name);

	public void deregisterNPC(MCCitizensNPC npc);

	public Set<MCCitizensNPC> getNPCs();
	public MCCitizensNPC getNPC(MCEntity entity);
	public boolean isNPC(MCEntity entity);
	public MCCitizensNPC getNPC(int id);
	public MCCitizensNPC getNPC(UUID uuid);
}