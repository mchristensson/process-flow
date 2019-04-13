package org.mac.processflow.core.out;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mac.processflow.core.model.SnapshotData;
import org.mac.processflow.example.model.ExampleProcessResponsibleState;
import org.mac.processflow.example.model.ExampleState;
import org.mac.processflow.model.Activity;
import org.mac.processflow.model.Entity;
import org.mac.processflow.model.Group;
import org.mac.processflow.model.PhaseBasedSnapshotData;

public class TestExcelOuthandler {

	private static final String BIN_TEST_OUTPUT = "./bin/testdata/output/";
	private static final String BIN_TEST_OUTPUT_MY_FILE_XLSX = "./bin/testdata/output/myFile.xlsx";

	@BeforeClass
	public static void setupDirs() {
		File path = Paths.get(BIN_TEST_OUTPUT).toAbsolutePath().toFile();
		if (!path.exists()) {
			path.mkdirs();
		}
	}
	
	@Before
	public void cleanDirs() {
		File path = Paths.get(BIN_TEST_OUTPUT_MY_FILE_XLSX).toAbsolutePath().toFile();
		if (path.exists()) {
			path.delete();
		}
	}
	
	@Test
	public void constructor_init_shouldWork() {
		new ExcelOuthandler();
	}
	
	@Test(expected=IOException.class)
	public void writeToFile_nullpath_throwIOException() throws Exception {
		ExcelOuthandler outhandler = new ExcelOuthandler();
		outhandler.writeToFile(null);
	}
	
	@Test
	public void writeToFile_validpath_fileExists() {
		Path path = Paths.get(BIN_TEST_OUTPUT_MY_FILE_XLSX).toAbsolutePath();
		assertTrue("Invalid state of test, file should not already exist", !path.toFile().exists());
		
		ExcelOuthandler outhandler = new ExcelOuthandler();
		try {
			outhandler.writeToFile(path);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertTrue("File does not exist", path.toFile().exists());
	}
	
	@Test(expected=NullPointerException.class)
	public void writeSnapshot_nullData_throwException() {
		Path path = Paths.get(BIN_TEST_OUTPUT_MY_FILE_XLSX).toAbsolutePath();
		assertTrue("Invalid state of test, file should not already exist", !path.toFile().exists());
		
		ExcelOuthandler outhandler = new ExcelOuthandler();
		outhandler.writeSnapshot(null);
	}
	
	@Test
	public void writeSnapshot_emptyCollection_fileExists() {
		Path path = Paths.get(BIN_TEST_OUTPUT_MY_FILE_XLSX).toAbsolutePath();
		assertTrue("Invalid state of test, file should not already exist", !path.toFile().exists());
		
		ExcelOuthandler outhandler = new ExcelOuthandler();
		
		SnapshotData data = new PhaseBasedSnapshotData();
		outhandler.writeSnapshot(data);
		try {
			outhandler.writeToFile(path);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertTrue("File does not exist", path.toFile().exists());
	}
	
	@Test
	public void writeSnapshot_collectionWithData_fileExists() {
		Path path = Paths.get(BIN_TEST_OUTPUT_MY_FILE_XLSX).toAbsolutePath();
		assertTrue("Invalid state of test, file should not already exist", !path.toFile().exists());
		
		ExcelOuthandler outhandler = new ExcelOuthandler();
		
		
		
		PhaseBasedSnapshotData data = new PhaseBasedSnapshotData();

		
		Group a= data.addGroup(new Group("Min Grupp 1"));
		Group b= data.addGroup(new Group("Min Grupp 2")); 
		Group c= data.addGroup(new Group("Min Grupp 3")); 
		
		data.addActivity(new Activity("Uppgift A")); 
		data.addActivity(new Activity("Uppgift B")); 
		data.addEntity(new Entity("Bil")); 
		Entity horse = data.addEntity(new Entity("Häst")); 
		Entity dack = data.addEntity(new Entity("Däck")); 
		data.addEntity(new Entity("Bult 1").setGroupLevel(1).setState(ExampleState.ACTIVE)); 
		data.addEntity(new Entity("Bult 2").setGroupLevel(1));
		data.addEntity(new Entity("Bult 3").setGroupLevel(1));
		data.addEntity(new Entity("Bult 4").setGroupLevel(1));
		
		dack.setState(ExampleState.ACTIVE);
		
		outhandler.writeSnapshot(data);
		dack.setState(null);
		horse.setState(ExampleState.ACTIVE).setResponsible(a, ExampleProcessResponsibleState.RESPONSIBLE);
		outhandler.writeSnapshot(data);
		
		try {
			outhandler.writeToFile(path);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertTrue("File does not exist", path.toFile().exists());
	}

}
