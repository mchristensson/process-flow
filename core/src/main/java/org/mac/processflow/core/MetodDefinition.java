package org.mac.processflow.core;

import java.util.LinkedList;
import java.util.List;

class MetodDefinition implements SektionX {
	private List<String> logic = new LinkedList<>();
	private String text;

	@Override
	public void parse(String text) {
		this.text = text;

	}@Override
	public String getText() {
		return this.text;
	}
	
	public void addLogic(String handleSection) {
		this.logic.add(handleSection);
	}
}