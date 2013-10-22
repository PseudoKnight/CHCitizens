package com.hekta.chcitizens.abstraction.enums.bukkit;

import net.citizensnpcs.api.ai.TargetType;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.enums.EnumConvertor;
import com.laytonsmith.annotations.abstractionenum;

import com.hekta.chcitizens.abstraction.enums.MCCitizensTargetType;

/**
 *
 * @author Hekta
 */
@abstractionenum(
		implementation= Implementation.Type.BUKKIT,
		forAbstractEnum=MCCitizensTargetType.class,
		forConcreteEnum=TargetType.class
		)
public class BukkitMCCitizensTargetType extends EnumConvertor<MCCitizensTargetType, TargetType> {

	private static BukkitMCCitizensTargetType instance;

	public static BukkitMCCitizensTargetType getConvertor() {
		if (instance == null) {
			instance = new BukkitMCCitizensTargetType();
		}
		return instance;
	}
}
