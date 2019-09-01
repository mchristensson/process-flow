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
	
	private List<MetodDefinition> metoder = new ArrayList<>();
	private MetodDefinition currentMethod;

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
		this.level++;
	}

	public void addLogic(String handleSection, int pop2) {
		switch (pop2) {
		case PUSH:
			this.logic.add(handleSection);
			if (KlassDefinition.METOD == this.getLevel()){
				MetodDefinition m= new MetodDefinition();
				m.parse(handleSection);
				this.metoder.add(m);
				this.currentMethod = m;
			} else {
				if (this.currentMethod == null ) {
					System.out.print("Level: " + this.getLevel());
				}
				this.currentMethod.addLogic(handleSection);
			}
			this.level++;
			System.out.println("Level (pushed): " + level);
			break;
		case POP:
			this.logic.add(handleSection);
			if (this.currentMethod == null ) {
				System.out.print("Level: " + this.getLevel());
			}
			this.currentMethod.addLogic(handleSection);
			if (KlassDefinition.METOD == this.getLevel()){
				this.currentMethod = null;
			} 

			this.level--;
			System.out.println("Level (popped): " + level);
			break;
		default:
			this.logic.add(handleSection);
			this.currentMethod.addLogic(handleSection);
			
		}
	}

	public List<SektionX> getImports() {
		return this.importOrPackages;
	}
	@Override
	public String getText() {
		return this.text;
	}


	public int getLevel() {
		return level;
	}


}