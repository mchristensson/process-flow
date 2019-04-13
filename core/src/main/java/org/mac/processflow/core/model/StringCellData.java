package org.mac.processflow.core.model;

public class StringCellData implements CellData {

	private String value;
	private int groupLevel;

	public StringCellData(String t) {
		this.value = t;
	}

	public StringCellData(String t, int groupLevel) {
		this.value = t;
		setGroupLevel(groupLevel);
	}

	@Override
	public int getGroupLevel() {
		return groupLevel;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void setGroupLevel(int groupLevel) {
		this.groupLevel = groupLevel;
	}

}
