package org.mac.processflow.model;

public interface ProcessDataToString {

	public int getGroupLevel();

	void resetState();

	String writeOutput();
}
