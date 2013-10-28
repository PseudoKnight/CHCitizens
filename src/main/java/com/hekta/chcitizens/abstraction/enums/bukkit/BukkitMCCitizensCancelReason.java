package com.hekta.chcitizens.abstraction.enums.bukkit;

import net.citizensnpcs.api.ai.event.CancelReason;

import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.enums.EnumConvertor;
import com.laytonsmith.annotations.abstractionenum;

import com.hekta.chcitizens.abstraction.enums.MCCitizensCancelReason;

/**
 *
 * @author Hekta
 */
@abstractionenum(
		implementation=Implementation.Type.BUKKIT,
		forAbstractEnum=MCCitizensCancelReason.class,
		forConcreteEnum=CancelReason.class
		)
public class BukkitMCCitizensCancelReason extends EnumConvertor<MCCitizensCancelReason, CancelReason> {

	private static BukkitMCCitizensCancelReason instance;

	public static BukkitMCCitizensCancelReason getConvertor() {
		if (instance == null) {
			instance = new BukkitMCCitizensCancelReason();
		}
		return instance;
	}
}