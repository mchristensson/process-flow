package org.mac.processflow.model;

public class Activity extends AbstractProcessData {

	public Activity(String label) {
		super(label);
	}

	public Activity setGroupLevel(int groupLevel) {
		return (Activity) super.setGroupLevel(groupLevel);
	}

	public Activity setState(ProcessDataState state) {
		return (Activity) super.setState(state);
	}
}
