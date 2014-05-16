package com.hekta.chcitizens.abstraction.bukkit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import com.laytonsmith.abstraction.MCEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCEntity;
import com.laytonsmith.abstraction.enums.MCEntityType;
import com.laytonsmith.abstraction.enums.bukkit.BukkitMCEntityType;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensNPCRegistry;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensNPCRegistry implements MCCitizensNPCRegistry {

	private final NPCRegistry _registry;

	public BukkitMCCitizensNPCRegistry(NPCRegistry registry) {
		_registry = registry;
	}

	@Override
	public NPCRegistry getHandle() {
		return _registry;
	}

	@Override
	public MCCitizensNPC createNPC(MCEntityType type, UUID uuid, int id, String name) {
		NPC npc = _registry.createNPC(BukkitMCEntityType.getConvertor().getConcreteEnum(type), uuid, id, name);
		return (npc != null) ? new BukkitMCCitizensNPC(npc) : null;
	}

	@Override
	public MCCitizensNPC createNPC(MCEntityType type, int id, String name) {
		return createNPC(type, UUID.randomUUID(), id, name);
	}

	@Override
	public MCCitizensNPC createNPC(MCEntityType type, String name) {
		NPC npc = _registry.createNPC(BukkitMCEntityType.getConvertor().getConcreteEnum(type), name);
		return (npc != null) ? new BukkitMCCitizensNPC(npc) : null;
	}

	@Override
	public void deregisterNPC(MCCitizensNPC npc) {
		_registry.deregister(((BukkitMCCitizensNPC) npc).getHandle());
	}

	@Override
	public Set<MCCitizensNPC> getNPCs() {
		Set<MCCitizensNPC> npcs = new HashSet<>();
		for (NPC npc : _registry) {
			npcs.add(new BukkitMCCitizensNPC(npc));
		}
		return npcs;
	}

	@Override
	public MCCitizensNPC getNPC(MCEntity entity) {
		NPC npc = _registry.getNPC(((BukkitMCEntity) entity).getHandle());
		if (npc != null) {
			return new BukkitMCCitizensNPC(npc);
		} else {
			return null;
		}
	}

	@Override
	public boolean isNPC(MCEntity entity) {
		return _registry.isNPC(((BukkitMCEntity) entity).getHandle());
	}

	@Override
	public MCCitizensNPC getNPC(int id) {
		NPC npc = _registry.getById(id);
		if (npc != null) {
			return new BukkitMCCitizensNPC(npc);
		} else {
			return null;
		}
	}

	@Override
	public MCCitizensNPC getNPC(UUID uuid) {
		NPC npc = _registry.getByUniqueId(uuid);
		if (npc != null) {
			return new BukkitMCCitizensNPC(npc);
		} else {
			return null;
		}
	}
}