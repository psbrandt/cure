package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestResult;

import weka.core.Attribute;
import weka.core.Instance;

public class ClassAttributeFactory {
	private static final Logger logger = LoggerFactory
			.getLogger(ClassAttributeFactory.class);

	// Index in feature vector of the class attribute
	public static final int CLASS_ATTRIBUTE_INDEX = 0;

	// Pretty Class Attribute Names
	public static final String CLASS_ATTRIBUTE_NAME = "Class";

	public static HashSet<String> MULTI_LABEL_DRUG_RESISTANCE_LABELS = new HashSet<String>(
			Arrays.asList(new String[] { "etravirine", "tenofovir", "abacavir",
					"didanosine", "delavirdine", "stavudine", "nevirapine",
					"efavirenz", "emtricitabine", "lamivudine", "zidovudine" }));

	/*
	 * Arrays.asList(new String[] { "zidovudine", "zalcitabine", "didanosine",
	 * "lamivudine", "stavudine", "abacavir", "emtricitabine", "tenofovir",
	 * "nevirapine", "delavirdine", "efavirenz", "etravirine", "saquinavir",
	 * "saquinavir/r", "ritonavir", "indinavir", "indinavir/r", "nelfinavir",
	 * "fosamprenavir", "fosamprenavir/r", "lopinavir/r", "atazanavir",
	 * "atazanavir/r", "tipranavir/r", "darunavir/r" }));
	 */

	// Pretty Class Attribute Names
	public static final String CLASS_ATTRIBUTE_RESISTANT = "Resistant";
	public static final String CLASS_ATTRIBUTE_NOT_RESISTANT = "Not Resistant";

	// Value to indicate class cannot be determined
	public static final String CLASS_ATTRIBUTE_UNLABELED = "Unlabeled";

	// Classes
	public static final Set<String> CLASS_ATTRIBUTES = new HashSet<String>(
			Arrays.asList(new String[] { "dummy", CLASS_ATTRIBUTE_RESISTANT,
					CLASS_ATTRIBUTE_NOT_RESISTANT }));

	// Get the labels for multi-label classification
	public static List<Attribute> getLabelAttributes() {
		ArrayList<Attribute> labels = new ArrayList<Attribute>(
				MULTI_LABEL_DRUG_RESISTANCE_LABELS.size());

		for (String label : MULTI_LABEL_DRUG_RESISTANCE_LABELS) {
			labels.add(new Attribute(label, Arrays.asList("0", "1")));
		}

		return labels;
	}

	// Get the class attribute
	public static Attribute getClassAttribute() {
		ArrayList<String> classes = new ArrayList<String>(2);

		classes.addAll(CLASS_ATTRIBUTES);

		return new Attribute(CLASS_ATTRIBUTE_NAME, classes);
	}

	// Classify the patient using the RegaService classification method
	public static Instance addAttributeValue(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		if (attribute.name().equals(CLASS_ATTRIBUTE_NAME)) {
			logger.info("Adding class value.");

			String label = rs.classifyPatient(patient);

			if (CLASS_ATTRIBUTES.contains(label)) {
				instance.setValue(attribute, label);
				return instance;
			}

			return instance;
		} else if (MULTI_LABEL_DRUG_RESISTANCE_LABELS
				.contains(attribute.name())) {

			addDrugResistanceLabelValue(attribute, instance, patient, rs,
					attribute.name());

			return instance;
		}
		return null;
	}

	public static void addDrugResistanceLabelValue(Attribute attribute,
			Instance instance, Patient patient, RegaService rs, String drug) {

		List<TestResult> results = rs.getLatestStanfordGSSTestResults(patient);

		// logger.info("Found " + results.size() +
		// " Stanford GSS Test Results.");

		if (rs.isResistantToDrug(drug, results)) {
			instance.setValue(attribute, "1");
		} else {
			instance.setValue(attribute, "0");
		}
	}
}
