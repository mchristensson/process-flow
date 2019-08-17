package org.mac.javaanalyzer.core.model;

import java.util.LinkedList;

public class Metod extends KommandoSekvens implements WithCallbackDefinition, WithArguments {
	private LinkedList<VariabelDefinition> arguments = new LinkedList<>();
	private Definition callbackDefinition;

	@Override
	public void addArgument(VariabelDefinition variabelDefinition) {
		this.arguments.add(variabelDefinition);
	}

	@Override
	public void setCallbackDefinition(Definition definition) {
		this.callbackDefinition = definition;
	}

	@Override
	public boolean hasCallbackDefinition() {
		return this.callbackDefinition != null;
	}
}
