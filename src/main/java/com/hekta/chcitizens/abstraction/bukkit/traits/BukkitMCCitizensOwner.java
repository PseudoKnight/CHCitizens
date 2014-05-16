package com.hekta.chcitizens.abstraction.bukkit.traits;

import net.citizensnpcs.api.trait.trait.Owner;

import com.laytonsmith.abstraction.MCOfflinePlayer;
import com.laytonsmith.abstraction.StaticLayer;

import com.hekta.chcitizens.abstraction.bukkit.BukkitMCCitizensTrait;
import com.hekta.chcitizens.abstraction.traits.MCCitizensOwner;

/**
 *
 * @author Hekta
 */
public class BukkitMCCitizensOwner extends BukkitMCCitizensTrait implements MCCitizensOwner {

	private final Owner m_owner;

	public BukkitMCCitizensOwner(Owner owner) {
		super(owner);
		m_owner = owner;
	}

	@Override
	public Owner getHandle() {
		return m_owner;
	}

	@Override
	public MCOfflinePlayer getOwner() {
		return StaticLayer.GetServer().getOfflinePlayer(m_owner.getOwner());
	}

	@Override
	public void setOwner(MCOfflinePlayer owner) {
		m_owner.setOwner(owner.getName());
	}
}