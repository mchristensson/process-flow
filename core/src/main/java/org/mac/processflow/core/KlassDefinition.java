package org.mac.processflow.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class KlassDefinition implements SektionX {

	public static final int KLASS = 0;
	public static final int METOD = 1;
	public static final int PUSH = 10;
	public static final int POP = 20;
	public static final int NONE = 5;
	
	private String text;
	private List<SektionX> importOrPackages = new ArrayList<SektionX>();
	private boolean started;
	private List<String> logic = new LinkedList<>();
	private int level = KLASS;

	public void addImportOrPackage(SektionX importDefintion) {
		this.importOrPackages.add(importDefintion);
	}

	public boolean hasStarted() {
		return this.started;
	}

	@Override
	public void parse(String text) {
		this.text = text;
		this.started=true;
		push();
	}

	public void addLogic(String handleSection, int pop2) {
		switch (pop2) {
		case PUSH:
			this.logic.add(handleSection);
			push();
			break;
		case POP:
			this.logic.add(handleSection);
			pop();
			break;
		default:
			this.logic.add(handleSection);
		}
	}

	public List<SektionX> getImports() {
		return this.importOrPackages;
	}
	@Override
	public String getText() {
		return this.text;
	}

	public void push() {
		this.level++;
		System.out.println("Level (pushed): " + level);
	}

	public int getLevel() {
		return level;
	}

	public void pop() {
		this.level--;
		System.out.println("Level (popped): " + level);
	}

}