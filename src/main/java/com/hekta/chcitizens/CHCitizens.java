package com.hekta.chcitizens;

import com.hekta.chcitizens.abstraction.CHCitizensStaticLayer;
import com.hekta.chcitizens.abstraction.MCCitizensNPCRegistry;
import com.hekta.chcitizens.abstraction.MCCitizensPlugin;
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

	private static final Version VERSION = new SimpleVersion(1, 1, 8);

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
			CHCitizensStaticLayer.startup();
			Static.getLogger().info(String.format("%s %s enabled.", getName(), VERSION));
		} else {
			Static.getLogger().severe(String.format("Plugin %s seems to be missing, none of the %s functions will work.", "Citizens", getName()));
		}
	}

	@Override
	public void onShutdown() {
		if (_citizens != null) {
			CHCitizensStaticLayer.shutdown();
			_citizens = null;
			_registry = null;
			Static.getLogger().info(String.format("%s disabled.", getName()));
		}
	}

	public static MCCitizensPlugin getCitizensPlugin() {
		return _citizens;
	}

	public static MCCitizensNPCRegistry getNPCRegistry() {
		if(_registry == null && _citizens != null) {
			_registry = _citizens.getNPCRegistry();
		}
		return _registry;
	}
}