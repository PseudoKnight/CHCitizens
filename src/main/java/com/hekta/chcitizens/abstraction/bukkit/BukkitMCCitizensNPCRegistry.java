package com.hekta.chcitizens.abstraction.bukkit;

import java.util.HashSet;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import com.laytonsmith.abstraction.MCLivingEntity;
import com.laytonsmith.abstraction.bukkit.BukkitMCLivingEntity;
import com.laytonsmith.abstraction.enums.MCEntityType;
import com.laytonsmith.abstraction.enums.bukkit.BukkitMCEntityType;

import com.hekta.chcitizens.abstraction.MCCitizensNPC;
import com.hekta.chcitizens.abstraction.MCCitizensNPCRegistry;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensNPCRegistry implements MCCitizensNPCRegistry {

	NPCRegistry r;

	public BukkitMCCitizensNPCRegistry(NPCRegistry registry) {
		this.r = registry;
	}

	public NPCRegistry getConcrete() {
		return r;
	}

	public MCCitizensNPC createNPC(MCEntityType type, int id, String name) {
		return new BukkitMCCitizensNPC(r.createNPC(BukkitMCEntityType.getConvertor().getConcreteEnum(type), id, name));
	}

	public MCCitizensNPC createNPC(MCEntityType type, String name) {
		return new BukkitMCCitizensNPC(r.createNPC(BukkitMCEntityType.getConvertor().getConcreteEnum(type), name));
	}

	public void deregisterNPC(MCCitizensNPC npc) {
		r.deregister(((BukkitMCCitizensNPC) npc).getConcrete());
	}

	public Set<MCCitizensNPC> getNPCs() {
		Set<MCCitizensNPC> npcs = new HashSet<MCCitizensNPC>();
		for (NPC npc : r) {
			npcs.add(new BukkitMCCitizensNPC(npc));
		}
		return npcs;
	}

	public MCCitizensNPC getNPC(MCLivingEntity entity) {
		NPC npc = r.getNPC(((BukkitMCLivingEntity) entity).asLivingEntity());
		if (npc != null) {
			return new BukkitMCCitizensNPC(npc);
		} else {
			return null;
		}
	}

	public boolean isNPC(MCLivingEntity entity) {
		return r.isNPC(((BukkitMCLivingEntity) entity).asLivingEntity());
	}

	public MCCitizensNPC getNPCById(int id) {
		NPC npc = r.getById(id);
		if (npc != null) {
			return new BukkitMCCitizensNPC(npc);
		} else {
			return null;
		}
	}
}