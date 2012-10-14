package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import weka.core.Attribute;

public class ClassAttributeFactory {
	public static final int CLASS_ATTRIBUTE_INDEX = 0;
	
	public static final String CLASS_ATTRIBUTE_NAME = "Class";
	
	// Classes
	public static final Set<String> CLASS_ATTRIBUTES = new HashSet<String>(Arrays.asList(
		     new String[] {"RESISTANT", "NOT RESISTANT"}
		));
	
	public static Attribute getClassAttibute() {
		ArrayList<String> classes = new ArrayList<String>(2);
		
		classes.addAll(CLASS_ATTRIBUTES);
		
		return new Attribute(CLASS_ATTRIBUTE_NAME, classes);	
	}
}
