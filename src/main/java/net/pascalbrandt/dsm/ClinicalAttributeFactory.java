package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.core.Attribute;

public class ClinicalAttributeFactory {
	// Numeric Attributes
	public static String[] CLINCAL_NUMERIC_ATTRIBUTES = { "Baseline CD4",
			"Pre-Resistance Testing CD4 Count",
			"Pre-Resistance Testing Viral Load", "Time on Failing Regimen",
			"Recent Blood Creatinine Clearance", "Recent Blood ALT", "Recent Blood HB" };

	// Categortical Attributes
	public static Map<String, String[]> CLINICAL_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();

	static {
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				"Pre-Resistance Testing Immunological Failure", new String[] {
						"Yes", "No" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("Transmission Group",
				new String[] { "homosexual + IVDU", "haemophiliac",
						"occupational exposure", "unknown", "heterosexual",
						"vertical", "bisexual + IVDU", "homosexual", "IVDU",
						"heterosexual + IVDU", "bisexual", "other",
						"transfusion" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("HTLV-1 Status", new String[] {
				"positive", "negative" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("HBV Status", new String[] {
				"Not tested", "Neg", "Pos"});
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("Tuberculosis Therapy (Prior)",
				new String[] { "Regimen 1", "Regimen 2", "MDR"});
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("Tuberculosis Therapy (During)",
				new String[] { "Regimen 1", "Regimen 2", "MDR" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("Tuberculosis Therapy (Post)",
				new String[] { "Regimen 1", "Regimen 2", "MDR" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("Other Drug 1", new String[] {
				"Yes", "No" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("Other Drug 2", new String[] {
				"Yes", "No" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put("Other Drug 3", new String[] {
				"Yes", "No" });
	}

	public static List<Attribute> getClinicalAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		// Numeric Attributes
		for (String name : CLINCAL_NUMERIC_ATTRIBUTES) {
			attributes.add(new Attribute(name));
		}

		// Categorical Attributes
		for (String name : CLINICAL_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(new Attribute(name, Arrays
					.asList(CLINICAL_CATEGORITCAL_ATTRIBUTES.get(name))));
		}

		return attributes;
	}

	public static List<String> getClinicalAttributeNames() {
		ArrayList<String> attributes = new ArrayList<String>();

		// Numeric Attributes
		for (String name : CLINCAL_NUMERIC_ATTRIBUTES) {
			attributes.add(name);
		}

		// Categorical Attributes
		for (String name : CLINICAL_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(name);
		}

		return attributes;
	}
}
