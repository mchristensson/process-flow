package org.mac.javaanalyzer.core;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mac.javaanalyzer.core.model.Definition;
import org.mac.javaanalyzer.core.model.Kommando;
import org.mac.javaanalyzer.core.model.VariabelDefinition;

public class TextAnalyserare {

	public static final Pattern VARIABLE_DEFINITION_PATTERN = Pattern.compile("(.*)(;)$");
	public static final Pattern VARIABLE_DEFINITION_PATTERN_CRAP = Pattern.compile("(\\w)( )(\\w)( )*("
			+ "(=)( )*(new)( )?(\\w)(\\()" + "(((\\w)( )*)|((\\w)( )*(,)))*" + "( )*(\\))" + ")?(;)");

	public Kommando parse(String s) throws AnalysisException {

		if (s == null || "".equals(s.trim())) {
			throw new AnalysisException(String.format("No input data: %s", s));
		}
		Matcher matchVariableDefinition = VARIABLE_DEFINITION_PATTERN.matcher(s);
		if (matchVariableDefinition.find()) {
			Definition definition = null;
			// TODO: Parsa rad
			definition = parseRow(matchVariableDefinition);
			/*
			 * = new Definition(matchVariableDefinition.group(3));
			 * VariabelDefinition variabelDefinition = new
			 * VariabelDefinition(matchVariableDefinition.group(3));
			 * definition.setVariabelDefinition(variabelDefinition);
			 */
			return definition;
		} else {
			throw new AnalysisException(String.format("No data was generated through analysis of ''", s));
		}
	}

	private Definition parseRow(Matcher matchVariableDefinition) {
		String eee;
		System.out.println("ROW: " + matchVariableDefinition.group(0));
		System.out.println("ROW: " + matchVariableDefinition.group(1));
		System.out.println("ROW: " + matchVariableDefinition.group(2));
		// TODO Auto-generated method stub
		return null;
	}

}
