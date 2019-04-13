package org.mac.processflow.model;

public abstract class AbstractProcessData implements ProcessDataToString {

	private final String label;
	private ProcessDataState state;
	private int groupLevel;

	public AbstractProcessData(String label) {
		this.label = label;
	}

	public int getGroupLevel() {
		return groupLevel;
	}

	public ProcessDataState getState() {
		return state;
	}

	@Override
	public void resetState() {
		this.state = null;
	}

	public AbstractProcessData setGroupLevel(int groupLevel) {
		this.groupLevel = groupLevel;
		return this;
	}

	public void setResponsible(Group group, ProcessResponsibleState state) {
		group.setState(state);
	}

	public AbstractProcessData setState(ProcessDataState state) {
		this.state = state;
		return this;
	}

	@Override
	public String writeOutput() {
		return (state != null) ? label + "\n" + state : null;
	}
}
