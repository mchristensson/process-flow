package org.mac.processflow.core;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JavaFileReader {

	public static Predicate<? super String> isPackageDefinition = row -> row.matches("^package .*$");
	public static Predicate<? super String> isImportStatement = row -> row.matches("^import ");
	public static Predicate<? super String> isClassDefinition = row -> row.matches("^.* class .*\\{.*");
	public static Predicate<? super String> isMethodDefinition = row -> row
			.matches("^.*(\\w)( )+(\\()(.*)(\\))( )*\\{.*$");

	public static KlassDefinition readFromFile(File file) throws IOException {
		System.out.println("File: " + file.getAbsolutePath());

		KlassDefinition rows = readLines(file);
		return rows;

	}



	private static KlassDefinition readLines(File file) throws FileNotFoundException, IOException {

		KlassDefinition klass = new KlassDefinition();
		BufferedReader reader = null;
		try {
			boolean inCommentSection = false;
			reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String currentLine = reader.readLine();
			while (currentLine != null) {

				if (currentLine.contains("/*")) {
					inCommentSection = true;
				}

				if (!inCommentSection && currentLine.contains(";")) {
					if (!klass.hasStarted()) {
						SektionX importDefintion = new ImportDefinition();
						importDefintion.parse(handleSection(builder, currentLine, ";"));
						klass.addImportOrPackage(importDefintion);
					} else {
						klass.addLogic(handleSection(builder, currentLine, ";"), KlassDefinition.NONE);
					}

				} else if (!inCommentSection && currentLine.contains("{")) {
					if (!klass.hasStarted()) {
						klass.parse(handleSection(builder, currentLine, "\\{"));
					} else {
						klass.addLogic(handleSection(builder, currentLine, "\\{"), KlassDefinition.PUSH);
					}
				} else if (!inCommentSection && currentLine.contains("}")) {
					klass.addLogic(handleSection(builder, currentLine, "\\}"), KlassDefinition.POP);
				} 

				if (currentLine.contains("*/")) {
					inCommentSection = false;
				}

				currentLine = reader.readLine();

			}
		} finally {
			if (reader != null)
				reader.close();
		}

		return klass;
	}

	private static String handleSection(StringBuilder builder, String currentLine, String pattern) {
		String[] segments = currentLine.split(pattern);
		if (segments.length == 0) {
			return "";
		}
		builder.append(segments[0].trim());
		String section = builder.toString();
		builder.delete(0, builder.length());
		if (segments.length > 1) {
			builder.append(segments[1].trim());
		}
		return section;
	}

}
