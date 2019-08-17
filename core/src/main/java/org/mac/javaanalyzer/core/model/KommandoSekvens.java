package org.mac.javaanalyzer.core.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class KommandoSekvens implements Sekvens {

	private final Map<String, VariabelDefinition> variabelMap = new HashMap<>();
	private final LinkedList<Kommando> kommandon = new LinkedList<>();

	@Override
	public Map<String, VariabelDefinition> getVariabelMap() {
		return this.variabelMap;
	}

	@Override
	public Iterable<Kommando> getKommandon() {
		return this.kommandon;
	}

	@Override
	public void addKommando(Kommando kommando) {
		this.kommandon.addLast(kommando);
		if (kommando instanceof Definition) {
			addVariable((Definition) kommando);
		}
	}

	protected void addVariable(Definition d) {
		this.variabelMap.put(d.getKey(), d.getVariabelDefinition());
	}

}
