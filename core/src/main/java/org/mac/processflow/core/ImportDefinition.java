package org.mac.processflow.core;

class ImportDefinition implements SektionX {
	private String text;

	@Override
	public void parse(String text) {
		this.text = text;

	}

	@Override
	public String getText() {
		return this.text;
	}
}