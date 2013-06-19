package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pascalbrandt.dsm.rule.Rule;
import net.pascalbrandt.dsm.rule.impl.BaselineCD4RuleImpl;
import net.pascalbrandt.dsm.rule.impl.OtherComorbiditiesRuleImpl;
import net.pascalbrandt.dsm.rule.impl.OtherDrugRuleImpl;
import net.sf.regadb.db.Patient;

import weka.core.Attribute;
import weka.core.Instance;

public class OtherAttributeFactory {
	private static final Logger logger = LoggerFactory
			.getLogger(OtherAttributeFactory.class);

	// Pretty Numeric Attribute Names

	// Pretty Categorical Attribute Names
	public static final String OTHER_PRETTY_ATTRIBUTE_ALCOHOL_CONSUMPTION = "Alcohol Consumption";
	public static final String OTHER_PRETTY_ATTRIBUTE_TRADITIONAL_MEDICINE = "Traditional Medicine";
	public static final String OTHER_PRETTY_ATTRIBUTE_IDENTIFIED_REASON = "Identified Virological Failure Reason";
	public static final String OTHER_PRETTY_ATTRIBUTE_OTHER_COMORBIDITIES = "Other Co-morbidities";
	public static final String OTHER_PRETTY_ATTRIBUTE_DIARRHEA_OR_VOMITING = "Diarrhea or Vomiting";
	public static final String OTHER_PRETTY_ATTRIBUTE_TB_TREATMENT_SOON = "TB Treatment Starting Soon";
	public static final String OTHER_PRETTY_ATTRIBUTE_PARTNER_ON_TREATMENT = "Partner On Treatment";
	public static final String OTHER_PRETTY_ATTRIBUTE_EXPOSURE_TO_SDNVP = "Exposure to Single Dose NVP";

	// Rega Attribute Names
	public static final String OTHER_REGA_ATTRIBUTE_ALCOHOL_CONSUMPTION = "8_Alcohol";
	public static final String OTHER_REGA_ATTRIBUTE_TRADITIONAL_MEDICINE = "7_Traditional_Medicine";
	public static final String OTHER_REGA_ATTRIBUTE_IDENTIFIED_REASON = "14_Identified_reason";
	public static final String OTHER_REGA_ATTRIBUTE_OTHER_COMORBIDITIES = "13_coMorbidities";
	public static final String OTHER_REGA_ATTRIBUTE_DIARRHEA_OR_VOMITING = "13_coMorbidities_diarrehea";
	public static final String OTHER_REGA_ATTRIBUTE_TB_TREATMENT_SOON = "13_coMorbidities_TB_start_soon";
	public static final String OTHER_REGA_ATTRIBUTE_PARTNER_ON_TREATMENT = "12_partnerARV";
	public static final String OTHER_REGA_ATTRIBUTE_EXPOSURE_TO_SDNVP = "11_sdNVP";

	// Numeric Attributes
	public static final Set<String> OTHER_NUMERIC_ATTRIBUTES = new HashSet<String>(
			Arrays.asList(new String[] {}));

	// Categorical Attributes
	public static Map<String, String[]> OTHER_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();

	// Pretty to Rega name map
	public static Map<String, String> OTHER_ATTRIBUTES_REGA_MAP = new HashMap<String, String>();

	static {
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				OTHER_PRETTY_ATTRIBUTE_ALCOHOL_CONSUMPTION, new String[] {
						"dummy", "None", "Heavy", "Average" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				OTHER_PRETTY_ATTRIBUTE_TRADITIONAL_MEDICINE, new String[] {
						"dummy", "Yes", "No" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				OTHER_PRETTY_ATTRIBUTE_IDENTIFIED_REASON, new String[] {
						"dummy", "Traditional Remedies", "Poor Adherence",
						"Treatment Breaks", "Social issues impacting Rx",
						"Malabsorption", "Drug Interactions", "Drug toxicity",
						"Single Dose NVP", "Transmitted Resistance",
						"Non-Disclosure", "Financial" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				OTHER_PRETTY_ATTRIBUTE_OTHER_COMORBIDITIES, new String[] {
						"dummy", "Yes", "No" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				OTHER_PRETTY_ATTRIBUTE_DIARRHEA_OR_VOMITING, new String[] {
						"dummy", "Yes", "No" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				OTHER_PRETTY_ATTRIBUTE_TB_TREATMENT_SOON, new String[] {
						"dummy", "Yes", "No" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				OTHER_PRETTY_ATTRIBUTE_PARTNER_ON_TREATMENT, new String[] {
						"dummy", "Yes", "No", "Unk" });
		OTHER_CATEGORITCAL_ATTRIBUTES.put(
				OTHER_PRETTY_ATTRIBUTE_EXPOSURE_TO_SDNVP, new String[] {
						"dummy", "Yes", "No" });

		OTHER_ATTRIBUTES_REGA_MAP.put(
				OTHER_PRETTY_ATTRIBUTE_ALCOHOL_CONSUMPTION,
				OTHER_REGA_ATTRIBUTE_ALCOHOL_CONSUMPTION);
		OTHER_ATTRIBUTES_REGA_MAP.put(
				OTHER_PRETTY_ATTRIBUTE_TRADITIONAL_MEDICINE,
				OTHER_REGA_ATTRIBUTE_TRADITIONAL_MEDICINE);
		OTHER_ATTRIBUTES_REGA_MAP.put(OTHER_PRETTY_ATTRIBUTE_IDENTIFIED_REASON,
				OTHER_REGA_ATTRIBUTE_IDENTIFIED_REASON);
		OTHER_ATTRIBUTES_REGA_MAP.put(
				OTHER_PRETTY_ATTRIBUTE_DIARRHEA_OR_VOMITING,
				OTHER_REGA_ATTRIBUTE_DIARRHEA_OR_VOMITING);
		OTHER_ATTRIBUTES_REGA_MAP.put(OTHER_PRETTY_ATTRIBUTE_TB_TREATMENT_SOON,
				OTHER_REGA_ATTRIBUTE_TB_TREATMENT_SOON);
		OTHER_ATTRIBUTES_REGA_MAP.put(
				OTHER_PRETTY_ATTRIBUTE_PARTNER_ON_TREATMENT,
				OTHER_REGA_ATTRIBUTE_PARTNER_ON_TREATMENT);
		OTHER_ATTRIBUTES_REGA_MAP.put(OTHER_PRETTY_ATTRIBUTE_EXPOSURE_TO_SDNVP,
				OTHER_REGA_ATTRIBUTE_EXPOSURE_TO_SDNVP);
	}

	// Get a list of the other attributes
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

	// Get a list of the other attribute names
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

	// Create attributes for the given list of attribute names
	public static List<Attribute> createAttributes(
			String[] selectedOtherAttributes, RuleService ruleService) {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		for (String attributeName : selectedOtherAttributes) {

			if (OTHER_NUMERIC_ATTRIBUTES.contains(attributeName)) {
				// Numeric Attributes
				attributes.add(new Attribute(attributeName));
			} else if (OTHER_CATEGORITCAL_ATTRIBUTES.containsKey(attributeName)) {
				// Categorical Attributes
				attributes.add(new Attribute(attributeName, Arrays
						.asList(OTHER_CATEGORITCAL_ATTRIBUTES
								.get(attributeName))));

				if (OTHER_ATTRIBUTES_REGA_MAP.containsKey(attributeName)) {
					// Do nothing if it's a simple categorical attribute
				} else {
					// Calculated Categorical Attributes
					if (attributeName
							.equals(OTHER_PRETTY_ATTRIBUTE_OTHER_COMORBIDITIES)) {
						// Other Comorbidities
						ruleService.addRule(OtherComorbiditiesRuleImpl.class,
								new OtherComorbiditiesRuleImpl(ruleService));

						logger.info("### Creating Other Co-mordbities rule!!");
					}
				}
			}
		}

		return attributes;
	}

	// Add the value to the instance for the given attribute and patient
	public static Instance addAttributeValue(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {

		if (OTHER_NUMERIC_ATTRIBUTES.contains(attribute.name())) {
			// Numeric Attributes

			// Simple Numeric Attributes
			if (OTHER_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
				AttributeFactory.setSimpleNumericAttributeValue(attribute,
						OTHER_ATTRIBUTES_REGA_MAP.get(attribute.name()),
						instance, patient, rs);
			} else {
				// Calculated Numeric Attributes

				// There are no other calculated numeric attributes
			}

			return instance;

		} else if (OTHER_CATEGORITCAL_ATTRIBUTES.containsKey(attribute.name())) {
			// Categorical Attributes

			// Simple Categorical Attributes
			if (OTHER_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
				AttributeFactory.setSimpleCategoricalAttributeValue(attribute,
						OTHER_ATTRIBUTES_REGA_MAP.get(attribute.name()),
						instance, patient, rs);
			} else {
				// Calculated Categorical Attributes

				if (attribute.name().equals(
						OTHER_PRETTY_ATTRIBUTE_OTHER_COMORBIDITIES)) {
					// Other Co-Morbidities
					setAttributeValueOtherCoMorbidities(attribute, instance,
							patient, rs);
				}
			}

			return instance;
		}

		return null;
	}

	/**
	 * Attribute specific methods to find values from patient
	 */

	// Other Co-Morbidities
	private static void setAttributeValueOtherCoMorbidities(
			Attribute attribute, Instance instance, Patient patient,
			RegaService rs) {
		Rule otherComorbiditiesRule = rs.getRuleService().getRule(
				OtherComorbiditiesRuleImpl.class);

		otherComorbiditiesRule.setAttributeValue(attribute, instance, patient);
	}
}
