package org.mac.processflow.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.mac.processflow.core.model.CellData;
import org.mac.processflow.core.model.SnapshotData;
import org.mac.processflow.core.model.StringCellData;

public class PhaseBasedSnapshotData implements SnapshotData {

	private LinkedList<ProcessDataToString> groups;
	private LinkedList<ProcessDataToString> activities;
	private LinkedList<ProcessDataToString> entities;
	private boolean resetsGroupStateAfterwards;

	public PhaseBasedSnapshotData() {
		groups = new LinkedList<>();
		activities = new LinkedList<>();
		entities = new LinkedList<>();
	}

	public Activity addActivity(Activity activity) {
		activities.add(activity);
		return activity;
	}

	public Entity addEntity(Entity entity) {
		entities.add(entity);
		return entity;
	}

	public Group addGroup(Group group) {
		groups.add(group);
		return group;
	}

	@Override
	public List<CellData> getCollection() {
		final List<CellData> data = new ArrayList<>();
		groups.stream().forEachOrdered(group -> {
			data.add(new StringCellData(group.writeOutput(), group.getGroupLevel()));
			if (resetsGroupStateAfterwards) {
				group.resetState();
			}
		});
		activities.stream().forEachOrdered(t -> data.add(new StringCellData(t.writeOutput(), t.getGroupLevel())));
		entities.stream().forEachOrdered(t -> data.add(new StringCellData(t.writeOutput(), t.getGroupLevel())));

		return data;
	}

	public void setResetsGroupStateAfterwards(boolean resetsGroupStateAfterwards) {
		this.resetsGroupStateAfterwards = resetsGroupStateAfterwards;
	}

}
