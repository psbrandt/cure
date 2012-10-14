package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weka.core.Attribute;

public class AdherenceAttributeFactory {
	// Numeric Attributes
	public static final Set<String> ADHERENCE_NUMERIC_ATTRIBUTES = new HashSet<String>(Arrays.asList(
		     new String[] {"Treatment Break"}
	));	
	
	// Categortical Attributes
	public static Map<String, String[]> ADHERENCE_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();

	static {
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Gender", new String[] { "Male",
				"Female" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Patient Estimated Adherence",
				new String[] { "Less Good", "Poor", "Good", "None" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Remember", new String[] { "Yes",
				"No" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Stop", new String[] { "Yes",
				"No" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Missed", new String[] { "Yes",
				"No" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Worst Stop", new String[] {
				"Yes", "No" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Names", new String[] { "Yes",
				"No" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Side Effects", new String[] {
				"Yes", "No" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Disclosure", new String[] {
				"Yes", "No" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Buddy", new String[] { "Yes",
				"No" });
		ADHERENCE_CATEGORITCAL_ATTRIBUTES.put("Counseling", new String[] {
				"Yes", "No" });

	}

	public static List<Attribute> getAdherenceAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		// Numeric Attributes
		for (String name : ADHERENCE_NUMERIC_ATTRIBUTES) {
			attributes.add(new Attribute(name));
		}

		// Categorical Attributes
		for (String name : ADHERENCE_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(new Attribute(name, Arrays
					.asList(ADHERENCE_CATEGORITCAL_ATTRIBUTES.get(name))));
		}

		return attributes;
	}

	public static List<String> getAdherenceAttributeNames() {
		ArrayList<String> attributes = new ArrayList<String>();

		// Numeric Attributes
		for (String name : ADHERENCE_NUMERIC_ATTRIBUTES) {
			attributes.add(name);
		}

		// Categorical Attributes
		for (String name : ADHERENCE_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(name);
		}

		return attributes;
	}
	
	public static List<Attribute> createAttributes(String[] selectedAdherenceAttributes) {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		for(String attributeName : selectedAdherenceAttributes) {
			// Numeric Attributes
			if(ADHERENCE_NUMERIC_ATTRIBUTES.contains(attributeName)) {
				attributes.add(new Attribute(attributeName));
			}
			
			// Categorical Attributes
			if(ADHERENCE_CATEGORITCAL_ATTRIBUTES.containsKey(attributeName)) {
				attributes.add(new Attribute(attributeName, Arrays.asList(ADHERENCE_CATEGORITCAL_ATTRIBUTES.get(attributeName))));
			}
		}	
		
		return attributes;
	}
}
