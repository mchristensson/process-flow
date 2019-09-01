package org.mac.processflow.core;

class PaketDefinition implements SektionX {
	private String text;

	@Override
	public void parse(String text) {
		this.text = text;

	}@Override
	public String getText() {
		return this.text;
	}
}