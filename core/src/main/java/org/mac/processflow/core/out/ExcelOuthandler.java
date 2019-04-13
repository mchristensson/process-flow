package org.mac.processflow.core.out;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mac.processflow.core.model.CellData;
import org.mac.processflow.core.model.SnapshotData;
import org.mac.processflow.core.model.StringCellData;

public class ExcelOuthandler implements SnapshotWriter {

	private final XSSFWorkbook workbook;
	private final XSSFSheet sheet;
	private final AtomicInteger rowCounter;

	public ExcelOuthandler() {
		rowCounter = new AtomicInteger(0);
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
	}

	@Override
	public void writeSnapshot(SnapshotData snapshotData) {
		if (snapshotData == null) {
			throw new NullPointerException("Invalid data " + snapshotData);
		}
		XSSFRow row = sheet.createRow(rowCounter.getAndIncrement());
		List<CellData> cellDataList = snapshotData.getCollection();

		Map<Integer, Integer> levelMap = new HashMap<>();

		IntStream.range(0, cellDataList.size()).forEach(index -> {
			CellData cd = cellDataList.get(index);
			if (cd instanceof StringCellData) {
				XSSFCell cell = row.createCell(index);
				CellData cellData = cellDataList.get(index);
				cell.setCellValue(cellData.getValue());
				int groupLevel = cellData.getGroupLevel();
				if (groupLevel == 0) {
					levelMap.put(0, index);
				} else if (groupLevel > 0) {
					sheet.groupColumn(levelMap.get(groupLevel-1), index);
				}
			}

		});

	}

	void writeToFile(Path path) throws Exception {
		if (path == null) {
			throw new IOException("Invalid path " + path);
		}
		try (OutputStream fileOut = new FileOutputStream(path.toFile())) {
			workbook.write(fileOut);
		}
	}

}
