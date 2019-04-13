package org.mac.processflow.core.model;

import java.util.List;

public interface SnapshotData {

	/**
	 * Builds up a snapshot of all current values of a set of objects
	 * 
	 * @return
	 */
	List<CellData> getCollection();

	/**
	 * Whether to reset state of group nodes after each iteration
	 * 
	 * @param resetsGroupStateAfterwards
	 */
	public void setResetsGroupStateAfterwards(boolean resetsGroupStateAfterwards);

}
