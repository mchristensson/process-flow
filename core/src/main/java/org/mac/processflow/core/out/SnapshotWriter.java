package org.mac.processflow.core.out;

import org.mac.processflow.core.model.SnapshotData;

public interface SnapshotWriter {

	void writeSnapshot(SnapshotData snapshotData);

}
