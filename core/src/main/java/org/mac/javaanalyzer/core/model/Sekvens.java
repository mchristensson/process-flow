package org.mac.javaanalyzer.core.model;

import java.util.Map;

public interface Sekvens {

	void addKommando(Kommando kommando);

	Iterable<Kommando> getKommandon();

	Map<String, VariabelDefinition> getVariabelMap();

}
