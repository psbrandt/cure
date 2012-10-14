package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.core.Attribute;

public class OtherAttributeFactory {
	// Numeric Attributes
	public static String[] OTHER_NUMERIC_ATTRIBUTES = { };

	// Categortical Attributes
	public static Map<String, String[]> OTHER_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();

	static {
		OTHER_CATEGORITCAL_ATTRIBUTES.put("Alcohol Consumption", new String[] {
				"None", "Heavy", "Average" });

		OTHER_CATEGORITCAL_ATTRIBUTES.put("Traditional Medicine", new String[] {
				"Yes", "No" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				"Identified Virological Failure Reason", new String[] {
						"Traditional Remedies", "Poor Adherence",
						"Treatment Breaks", "Social issues impacting Rx",
						"Malabsorption", "Drug Interactions", "Drug toxicity",
						"Single Dose NVP", "Transmitted Resistance",
						"Non-Disclosure", "Financial" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put("Other Co-morbidities", new String[] { "Yes", "No" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put("Diarrhea or Vomiting", new String[] { "Yes", "No" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put("TB Treatment Starting Soon", new String[] { "Yes", "No" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put("Partner On Treatment", new String[] { "Yes", "No" });

		OTHER_CATEGORITCAL_ATTRIBUTES.put("Exposure to Single Dose NVP", new String[] { "Yes", "No" });

	}

	/*
	 * name="attrAlcohol" checked> Alcohol Consumption
	 * name="attrTraditionalMedicine" checked> Traditional Medicine
	 * name="attrIdentifiedReason" checked> Identified Virological Failure
	 * Reason name="attrOtherCM" checked> Other Co-morbidities
	 * name="attrOtherCMTBSoon" checked> TB Treatment Starting Soon
	 * name="attrOtherCMDiaVom" checked> Diarrhea or Vomiting
	 * name="attrPartnerOnTreatment" checked> Partner On Treatment
	 * name="attrPartnerOnTreatment" checked> Exposure to Single Dose NVP
	 */

	public static List<Attribute> getOtherAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		// Numeric Attributes
		for (String name : OTHER_NUMERIC_ATTRIBUTES) {
			attributes.add(new Attribute(name));
		}

		// Categorical Attributes
		for (String name : OTHER_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(new Attribute(name, Arrays
					.asList(OTHER_CATEGORITCAL_ATTRIBUTES.get(name))));
		}

		return attributes;
	}

	public static List<String> getOtherAttributeNames() {
		ArrayList<String> attributes = new ArrayList<String>();

		// Numeric Attributes
		for (String name : OTHER_NUMERIC_ATTRIBUTES) {
			attributes.add(name);
		}

		// Categorical Attributes
		for (String name : OTHER_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(name);
		}

		return attributes;
	}
}
