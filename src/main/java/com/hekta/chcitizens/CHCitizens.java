package com.hekta.chcitizens;

import com.hekta.chcitizens.abstraction.CHCitizensStaticLayer;
import com.hekta.chcitizens.abstraction.MCCitizensNPCRegistry;
import com.hekta.chcitizens.abstraction.MCCitizensPlugin;
import com.hekta.chcitizens.abstraction.bukkit.events.drivers.BukkitCitizensListener;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

/**
 *
 * @author Hekta
 */
@MSExtension("CHCitizens")
public class CHCitizens extends AbstractExtension {

	public static final String CITIZENS_NAME = "Citizens";

	private static final Version VERSION = new SimpleVersion(1, 1, 0);

	private static MCCitizensPlugin _citizens;
	private static MCCitizensNPCRegistry _registry;

	@Override
	public Version getVersion() {
		return VERSION;
	}

	@Override
	public void onStartup() {
		_citizens = CHCitizensStaticLayer.getCitizens();
		if (_citizens != null) {
			_registry = _citizens.getNPCRegistry();
			BukkitCitizensListener.register();
			Static.getLogger().info(String.format("%s %s loaded.", getName(), VERSION));
		} else {
			Static.getLogger().severe(String.format("Plugin Citizens seems to be missing, none of the %s functions will work.", getName()));
		}
	}

	@Override
	public void onShutdown() {
		if (_citizens != null) {
			_citizens = null;
			_registry = null;
			BukkitCitizensListener.unregister();
			Static.getLogger().info(String.format("%s unloaded.", getName()));
		}
	}

	public static MCCitizensPlugin getCitizensPlugin() {
		return _citizens;
	}

	public static MCCitizensNPCRegistry getNPCRegistry() {
		return _registry;
	}
}