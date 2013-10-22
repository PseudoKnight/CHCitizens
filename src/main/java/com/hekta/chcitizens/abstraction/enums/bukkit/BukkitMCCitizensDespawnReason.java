package com.hekta.chcitizens.abstraction.enums.bukkit;

import net.citizensnpcs.api.event.DespawnReason;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.enums.EnumConvertor;
import com.laytonsmith.annotations.abstractionenum;

import com.hekta.chcitizens.abstraction.enums.MCCitizensDespawnReason;

/**
 *
 * @author Hekta
 */
@abstractionenum(
		implementation=Implementation.Type.BUKKIT,
		forAbstractEnum=MCCitizensDespawnReason.class,
		forConcreteEnum=DespawnReason.class
		)
public class BukkitMCCitizensDespawnReason extends EnumConvertor<MCCitizensDespawnReason, DespawnReason> {

	private static BukkitMCCitizensDespawnReason instance;

	public static BukkitMCCitizensDespawnReason getConvertor() {
		if (instance == null) {
			instance = new BukkitMCCitizensDespawnReason();
		}
		return instance;
	}
}
