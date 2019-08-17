package org.mac.javaanalyzer.core.model;

public class Definition implements Kommando {

	private VariabelDefinition variabelDefinition;
	private String key;

	public Definition(String key) {
		setKey(key);
	}

	public VariabelDefinition getVariabelDefinition() {
		return this.variabelDefinition;
	}

	public String getKey() {
		return this.key;
	}

	private void setKey(String key) {
		this.key = key;
	}

	public void setVariabelDefinition(VariabelDefinition variabelDefinition) {
		this.variabelDefinition = variabelDefinition;
	}

}
