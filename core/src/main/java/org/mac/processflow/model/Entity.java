package org.mac.processflow.model;

public class Entity extends AbstractProcessData {

	public Entity(String label) {
		super(label);
	}

	public Entity setGroupLevel(int groupLevel) {
		return (Entity) super.setGroupLevel(groupLevel);
	}
	
	public Entity setState(ProcessDataState state) {
		return (Entity) super.setState(state);
	}
}
