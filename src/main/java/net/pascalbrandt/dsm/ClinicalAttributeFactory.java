package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.pascalbrandt.dsm.rule.Rule;
import net.pascalbrandt.dsm.rule.impl.BaselineCD4RuleImpl;
import net.pascalbrandt.dsm.rule.impl.BaselineVLRuleImpl;
import net.pascalbrandt.dsm.rule.impl.DrugExposureCountRuleImpl;
import net.pascalbrandt.dsm.rule.impl.DrugExposureNumericRuleImpl;
import net.pascalbrandt.dsm.rule.impl.DrugExposureRuleImpl;
import net.pascalbrandt.dsm.rule.impl.MeanViralLoadRuleImpl;
import net.pascalbrandt.dsm.rule.impl.MedianViralLoadRuleImpl;
import net.pascalbrandt.dsm.rule.impl.OtherDrugRuleImpl;
import net.pascalbrandt.dsm.rule.impl.PostTreatmentCD4GradientRuleImpl;
import net.pascalbrandt.dsm.rule.impl.PreResistanceImmunoFailure;
import net.pascalbrandt.dsm.rule.impl.PreResistanceTestingCD4RuleImpl;
import net.pascalbrandt.dsm.rule.impl.PreResistanceTestingViralLoadRuleImpl;
import net.pascalbrandt.dsm.rule.impl.RecentBloodCCRuleImpl;
import net.pascalbrandt.dsm.rule.impl.RecentBloodHBRuleImpl;
import net.pascalbrandt.dsm.rule.impl.RecentCD4GradientRuleImpl;
import net.pascalbrandt.dsm.rule.impl.RecentVLGradientRuleImpl;
import net.pascalbrandt.dsm.rule.impl.TimeOnFailingRegimenRuleImpl;
import net.pascalbrandt.dsm.rule.impl.VirusEverSuppressedRuleImpl;
import net.sf.regadb.db.Patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.core.Attribute;
import weka.core.Instance;

public class ClinicalAttributeFactory {
	public static final String ATTRIBUTE_VALUE_NO = "No";
	public static final String ATTRIBUTE_VALUE_YES = "Yes";

	private static final Logger logger = LoggerFactory
			.getLogger(ClinicalAttributeFactory.class);

	// Pretty Numeric Attribute Names
	public static final String CLINICAL_PRETTY_ATTRIBUTE_WEIGHT = "Weight";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_BASELINE_CD4 = "Baseline CD4";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_BASELINE_VL = "Baseline Viral Load";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_CD4 = "Pre-Resistance Testing CD4 Count";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_VL = "Pre-Resistance Testing Viral Load";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_TIME_ON_FAILING_REGIMEN = "Time on Failing Regimen";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_CC = "Recent Blood Creatinine Clearance";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_ALT = "Recent Blood ALT";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_HB = "Recent Blood HB";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_RECENT_CD4_GRADIENT = "Recent CD4 Count Gradient";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_RECENT_VL_GRADIENT = "Recent Viral Load Gradient";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_COUNT = "Drug Exposure Count";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_MEAN_VIRAL_LOAD = "Mean Viral Load";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_MEDIAN_VIRAL_LOAD = "Median Viral Load";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_POST_TREATMENT_CD4_GRADIENT = "Post-Treatment CD4 Count Gradient";

	// Pretty Categorical Attribute Names
	public static final String CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_IMMUNO_FAILURE = "Pre-Resistance Testing Immunological Failure";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_TRANSMISSION_GROUP = "Transmission Group";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_HTLV1_STATUS = "HTLV-1 Status";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_HBV_STATUS = "HBV Status";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_PRIOR = "Tuberculosis Therapy (Prior)";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_DURING = "Tuberculosis Therapy (During)";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_POST = "Tuberculosis Therapy (Post)";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_1 = "Other Drug 1";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_2 = "Other Drug 2";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_3 = "Other Drug 3";
	public static final String CLINICAL_PRETTY_ATTRIBUTE_EVER_SURPRESSED = "Virus Ever Suppressed";
	/*
	 * public static final String CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_1 =
	 * "Drug Exposure 1"; public static final String
	 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_2 = "Drug Exposure 2"; public
	 * static final String CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_3 =
	 * "Drug Exposure 3"; public static final String
	 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_4 = "Drug Exposure 4"; public
	 * static final String CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_5 =
	 * "Drug Exposure 5"; public static final String
	 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_6 = "Drug Exposure 6"; public
	 * static final String CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_7 =
	 * "Drug Exposure 7";
	 */

	// Drug Exposure Pretty Attribute Names
	public static final Set<String> CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE = new HashSet<String>(
			Arrays.asList(new String[] { "Lopinavir/r Exposure",
					"Zidovudine Exposure", "Lamivudine Exposure",
					"Efavirenz Exposure", "Stavudine Exposure",
					"Tenofovir Exposure", "Nevirapine Exposure",
					"Abacavir Exposure", "Ritonavir Exposure",
					"Raltegravir Exposure", "Didanosine Exposure",
					"Atazanavir Exposure" }));
	public static final String EXPOSURE_MATCH_STRING = "Exposure";

	// Rega Attribute Names
	public static final String CLINICAL_REGA_ATTRIBUTE_WEIGHT = "Weight";
	public static final String CLINICAL_REGA_ATTRIBUTE_TRANSMISSION_GROUP = "Transmission group";
	public static final String CLINICAL_REGA_ATTRIBUTE_HTLV1_STATUS = "HTLV1status";
	public static final String CLINICAL_REGA_ATTRIBUTE_HBV_STATUS = "9_HBV_status";
	public static final String CLINICAL_REGA_ATTRIBUTE_TB_THERAPY_PRIOR = "TB_therapy_Regimen_1";
	public static final String CLINICAL_REGA_ATTRIBUTE_TB_THERAPY_DURING = "TB_therapy_Regimen_2";
	public static final String CLINICAL_REGA_ATTRIBUTE_TB_THERAPY_POST = "TB_therapy_Regimen_3";
	public static final String CLINICAL_REGA_ATTRIBUTE_RECENT_BLOOD_CC = "Recent_Blood_Creatinine_Clearance";
	public static final String CLINICAL_REGA_ATTRIBUTE_RECENT_BLOOD_HB = "Recent_Blood_HB";
	public static final String CLINICAL_REGA_ATTRIBUTE_RECENT_BLOOD_ALT = "Recent_Blood_ALT";
	public static final String CLINICAL_REGA_ATTRIBUTE_OTHER_DRUG_1 = "Other_medications_1";
	public static final String CLINICAL_REGA_ATTRIBUTE_OTHER_DRUG_2 = "Other_medications_2";
	public static final String CLINICAL_REGA_ATTRIBUTE_OTHER_DRUG_3 = "Other_medications_3";

	// Numeric Attributes
	public static Set<String> CLINICAL_NUMERIC_ATTRIBUTES = new HashSet<String>(
			Arrays.asList(new String[] { CLINICAL_PRETTY_ATTRIBUTE_WEIGHT,
					CLINICAL_PRETTY_ATTRIBUTE_BASELINE_CD4,
					CLINICAL_PRETTY_ATTRIBUTE_BASELINE_VL,
					CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_CD4,
					CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_VL,
					CLINICAL_PRETTY_ATTRIBUTE_TIME_ON_FAILING_REGIMEN,
					CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_CC,
					CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_ALT,
					CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_HB,
					CLINICAL_PRETTY_ATTRIBUTE_RECENT_CD4_GRADIENT,
					// CLINICAL_PRETTY_ATTRIBUTE_RECENT_VL_GRADIENT,
					CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_COUNT,
					CLINICAL_PRETTY_ATTRIBUTE_MEAN_VIRAL_LOAD,
					CLINICAL_PRETTY_ATTRIBUTE_MEDIAN_VIRAL_LOAD,
					CLINICAL_PRETTY_ATTRIBUTE_POST_TREATMENT_CD4_GRADIENT }));

	// Add the set of drug exposure attributes
	static {
		CLINICAL_NUMERIC_ATTRIBUTES
				.addAll(CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE);
	}

	// Categorical Attributes
	public static Map<String, String[]> CLINICAL_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();

	// Pretty to Rega name map
	public static Map<String, String> CLINICAL_ATTRIBUTES_REGA_MAP = new HashMap<String, String>();

	static {
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_IMMUNO_FAILURE,
				new String[] { "dummy", ATTRIBUTE_VALUE_YES, ATTRIBUTE_VALUE_NO });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_TRANSMISSION_GROUP, new String[] {
						"dummy", "homosexual + IVDU", "haemophiliac",
						"occupational exposure", "unknown", "heterosexual",
						"vertical", "bisexual + IVDU", "homosexual", "IVDU",
						"heterosexual + IVDU", "bisexual", "other",
						"transfusion" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_HTLV1_STATUS, new String[] { "dummy",
						"positive", "negative" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_HBV_STATUS, new String[] { "dummy",
						"Not tested", "Neg", "Pos" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_PRIOR, new String[] {
						"dummy", "Regimen 1", "Regimen 2", "MDR" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_DURING, new String[] {
						"dummy", "Regimen 1", "Regimen 2", "MDR" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_POST, new String[] {
						"dummy", "Regimen 1", "Regimen 2", "MDR" });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_1, new String[] { "dummy",
						ATTRIBUTE_VALUE_YES, ATTRIBUTE_VALUE_NO });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_2, new String[] { "dummy",
						ATTRIBUTE_VALUE_YES, ATTRIBUTE_VALUE_NO });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_3, new String[] { "dummy",
						ATTRIBUTE_VALUE_YES, ATTRIBUTE_VALUE_NO });
		CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
				CLINICAL_PRETTY_ATTRIBUTE_EVER_SURPRESSED, new String[] {
						ATTRIBUTE_VALUE_YES, ATTRIBUTE_VALUE_NO });

		/*
		 * CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
		 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_1, new String[] { "dummy",
		 * "efavirenz", "stavudine", "lamivudine", "lopinavir/r", "zidovudine",
		 * "tenofovir", "nevirapine", "abacavir", "ritonavir", "raltegravir",
		 * "didanosine", "atazanavir" }); CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
		 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_2, new String[] { "dummy",
		 * "efavirenz", "stavudine", "lamivudine", "lopinavir/r", "zidovudine",
		 * "tenofovir", "nevirapine", "abacavir", "ritonavir", "raltegravir",
		 * "didanosine", "atazanavir" }); CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
		 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_3, new String[] { "dummy",
		 * "efavirenz", "stavudine", "lamivudine", "lopinavir/r", "zidovudine",
		 * "tenofovir", "nevirapine", "abacavir", "ritonavir", "raltegravir",
		 * "didanosine", "atazanavir" }); CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
		 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_4, new String[] { "dummy",
		 * "efavirenz", "stavudine", "lamivudine", "lopinavir/r", "zidovudine",
		 * "tenofovir", "nevirapine", "abacavir", "ritonavir", "raltegravir",
		 * "didanosine", "atazanavir" }); CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
		 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_5, new String[] { "dummy",
		 * "efavirenz", "stavudine", "lamivudine", "lopinavir/r", "zidovudine",
		 * "tenofovir", "nevirapine", "abacavir", "ritonavir", "raltegravir",
		 * "didanosine", "atazanavir" }); CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
		 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_6, new String[] { "dummy",
		 * "efavirenz", "stavudine", "lamivudine", "lopinavir/r", "zidovudine",
		 * "tenofovir", "nevirapine", "abacavir", "ritonavir", "raltegravir",
		 * "didanosine", "atazanavir" }); CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
		 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_7, new String[] { "dummy",
		 * "efavirenz", "stavudine", "lamivudine", "lopinavir/r", "zidovudine",
		 * "tenofovir", "nevirapine", "abacavir", "ritonavir", "raltegravir",
		 * "didanosine", "atazanavir" });
		 */

		CLINICAL_ATTRIBUTES_REGA_MAP.put(CLINICAL_PRETTY_ATTRIBUTE_WEIGHT,
				CLINICAL_REGA_ATTRIBUTE_WEIGHT);
		CLINICAL_ATTRIBUTES_REGA_MAP.put(
				CLINICAL_PRETTY_ATTRIBUTE_TRANSMISSION_GROUP,
				CLINICAL_REGA_ATTRIBUTE_TRANSMISSION_GROUP);
		CLINICAL_ATTRIBUTES_REGA_MAP.put(
				CLINICAL_PRETTY_ATTRIBUTE_HTLV1_STATUS,
				CLINICAL_REGA_ATTRIBUTE_HTLV1_STATUS);
		CLINICAL_ATTRIBUTES_REGA_MAP.put(CLINICAL_PRETTY_ATTRIBUTE_HBV_STATUS,
				CLINICAL_REGA_ATTRIBUTE_HBV_STATUS);
		CLINICAL_ATTRIBUTES_REGA_MAP.put(
				CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_PRIOR,
				CLINICAL_REGA_ATTRIBUTE_TB_THERAPY_PRIOR);
		CLINICAL_ATTRIBUTES_REGA_MAP.put(
				CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_DURING,
				CLINICAL_REGA_ATTRIBUTE_TB_THERAPY_DURING);
		CLINICAL_ATTRIBUTES_REGA_MAP.put(
				CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_POST,
				CLINICAL_REGA_ATTRIBUTE_TB_THERAPY_POST);
		CLINICAL_ATTRIBUTES_REGA_MAP.put(
				CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_ALT,
				CLINICAL_REGA_ATTRIBUTE_RECENT_BLOOD_ALT);

	}

	// Get a list of the clinical attributes
	public static List<Attribute> getClinicalAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		// Numeric Attributes
		for (String name : CLINICAL_NUMERIC_ATTRIBUTES) {
			attributes.add(new Attribute(name));
		}

		// Categorical Attributes
		for (String name : CLINICAL_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(new Attribute(name, Arrays
					.asList(CLINICAL_CATEGORITCAL_ATTRIBUTES.get(name))));
		}

		return attributes;
	}

	// Get a list of the clinical attribute names
	public static List<String> getClinicalAttributeNames() {
		ArrayList<String> attributes = new ArrayList<String>();

		// Numeric Attributes
		for (String name : CLINICAL_NUMERIC_ATTRIBUTES) {
			attributes.add(name);
		}

		// Categorical Attributes
		for (String name : CLINICAL_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(name);
		}

		return attributes;
	}

	// Create attributes for the given list of attribute names
	public static List<Attribute> createAttributes(
			String[] selectedClinicalAttributes, RuleService ruleService) {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		for (String attributeName : selectedClinicalAttributes) {

			if (CLINICAL_NUMERIC_ATTRIBUTES.contains(attributeName)) {
				// Numeric Attributes
				attributes.add(new Attribute(attributeName));

				// Simple Numeric Attributes
				if (CLINICAL_ATTRIBUTES_REGA_MAP.containsKey(attributeName)) {
					// Do nothing if it's a simple numeric attribute
				} else {
					// Calculated Numeric Attributes

					if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_BASELINE_CD4)) {
						// Baseline CD4
						ruleService.addRule(BaselineCD4RuleImpl.class,
								new BaselineCD4RuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_BASELINE_VL)) {
						// Baseline CD4
						ruleService.addRule(BaselineVLRuleImpl.class,
								new BaselineVLRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_CD4)) {
						// Pre-Resistance CD4 Count
						ruleService
								.addRule(PreResistanceTestingCD4RuleImpl.class,
										new PreResistanceTestingCD4RuleImpl(
												ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_VL)) {
						// Pre-Restiance Viral Load
						ruleService.addRule(
								PreResistanceTestingViralLoadRuleImpl.class,
								new PreResistanceTestingViralLoadRuleImpl(
										ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_TIME_ON_FAILING_REGIMEN)) {
						// Time on failing regimen
						ruleService.addRule(TimeOnFailingRegimenRuleImpl.class,
								new TimeOnFailingRegimenRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_CC)) {
						// Recent Blood Creatinine Clearance
						ruleService.addRule(RecentBloodCCRuleImpl.class,
								new RecentBloodCCRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_HB)) {
						// Recent Blood HB
						ruleService.addRule(RecentBloodHBRuleImpl.class,
								new RecentBloodHBRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_RECENT_CD4_GRADIENT)) {
						// Recent CD4 Gradient
						ruleService.addRule(RecentCD4GradientRuleImpl.class,
								new RecentCD4GradientRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_RECENT_VL_GRADIENT)) {
						// Recent VL Gradient
						ruleService.addRule(RecentVLGradientRuleImpl.class,
								new RecentVLGradientRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_COUNT)) {
						// Drug Exposure Count
						ruleService.addRule(DrugExposureCountRuleImpl.class,
								new DrugExposureCountRuleImpl(ruleService));
					} else if (attributeName.indexOf(EXPOSURE_MATCH_STRING) > -1) {
						// Drug exposure attribute
						// We only need one of these for all the exposure rules
						if (ruleService
								.getRule(DrugExposureNumericRuleImpl.class) == null) {
							ruleService
									.addRule(DrugExposureNumericRuleImpl.class,
											new DrugExposureNumericRuleImpl(
													ruleService));
						}
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_MEAN_VIRAL_LOAD)) {
						// Mean Viral Load
						ruleService.addRule(MeanViralLoadRuleImpl.class,
								new MeanViralLoadRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_MEDIAN_VIRAL_LOAD)) {
						// Median Viral Load
						ruleService.addRule(MedianViralLoadRuleImpl.class,
								new MedianViralLoadRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_POST_TREATMENT_CD4_GRADIENT)) {
						// Post Treatment CD4 Count Gradient
						ruleService.addRule(PostTreatmentCD4GradientRuleImpl.class,
								new PostTreatmentCD4GradientRuleImpl(ruleService));
					}
				}

			} else if (CLINICAL_CATEGORITCAL_ATTRIBUTES
					.containsKey(attributeName)) {
				// Categorical Attributes
				attributes.add(new Attribute(attributeName, Arrays
						.asList(CLINICAL_CATEGORITCAL_ATTRIBUTES
								.get(attributeName))));

				if (CLINICAL_ATTRIBUTES_REGA_MAP.containsKey(attributeName)) {
					// Do nothing if it's a simple categorical attribute
				} else {
					// Calculated Categorical Attributes

					if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_IMMUNO_FAILURE)) {
						// Pre-Resistance Immunological Failure
						ruleService.addRule(PreResistanceImmunoFailure.class,
								new PreResistanceImmunoFailure(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_1)) {
						// Other Drug 1
						ruleService.addRule(OtherDrugRuleImpl.class,
								new OtherDrugRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_2)) {
						// Other Drug 2
						ruleService.addRule(OtherDrugRuleImpl.class,
								new OtherDrugRuleImpl(ruleService));
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_3)) {
						// Other Drug 3
						ruleService.addRule(OtherDrugRuleImpl.class,
								new OtherDrugRuleImpl(ruleService));
						/*
						 * } else if (attributeName
						 * .equals(CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_1)) {
						 * // Drug Exposure 1
						 * ruleService.addRule(DrugExposureRuleImpl.class, new
						 * DrugExposureRuleImpl(ruleService)); }
						 */
					} else if (attributeName
							.equals(CLINICAL_PRETTY_ATTRIBUTE_EVER_SURPRESSED)) {
						// Virus Ever Suppressed
						ruleService.addRule(VirusEverSuppressedRuleImpl.class,
								new VirusEverSuppressedRuleImpl(ruleService));
					}
				}
			}
		}

		return attributes;
	}

	// Add the value to the instance for the given attribute and patient
	public static Instance addAttributeValue(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {

		if (CLINICAL_NUMERIC_ATTRIBUTES.contains(attribute.name())) {
			// Numeric Attributes

			// Simple Numeric Attributes
			if (CLINICAL_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
				AttributeFactory.setSimpleNumericAttributeValue(attribute,
						CLINICAL_ATTRIBUTES_REGA_MAP.get(attribute.name()),
						instance, patient, rs);
			} else {
				// Calculated Numeric Attributes

				if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_BASELINE_CD4)) {
					// Baseline CD4
					setAttributeValueBaselineCD4(attribute, instance, patient,
							rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_BASELINE_VL)) {
					// Baseline Viral Load
					setAttributeValueBaselineVL(attribute, instance, patient,
							rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_CD4)) {
					// Pre-Resistance CD4 Count
					setAttributeValuePreResistanceCD4(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_VL)) {
					// Pre-Restiance Viral Load
					setAttributeValuePreResistanceViralLoad(attribute,
							instance, patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_TIME_ON_FAILING_REGIMEN)) {
					// Time on failing regiment
					setAttributeValueTimeOnFailingRegimen(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_CC)) {
					// Recent Blood Creatinine Clearance
					setAttributeValueRecentBloodCC(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_HB)) {
					// Recent Blood HB
					setAttributeValueRecentBloodHB(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_RECENT_CD4_GRADIENT)) {
					// Recent CD4 Gradient
					setAttributeValueRecentCD4Gradient(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_RECENT_VL_GRADIENT)) {
					// Recent VL Gradient
					setAttributeValueRecentVLGradient(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_COUNT)) {
					// Recent VL Gradient
					setAttributeValueDrugExposureCount(attribute, instance,
							patient, rs);
				} else if (attribute.name().indexOf(EXPOSURE_MATCH_STRING) > -1) {
					// Drug exposure attribute
					setAttributeValueDrugExposureNumeric(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_MEAN_VIRAL_LOAD)) {
					// Mean Viral Load
					setAttributeValueMeanViralLoad(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_MEDIAN_VIRAL_LOAD)) {
					// Mean Viral Load
					setAttributeValueMedianViralLoad(attribute, instance,
							patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_POST_TREATMENT_CD4_GRADIENT)) {
					// Post Treatment CD4 Gradient
					setAttributeValuePostTreatmentCD4Gradient(attribute, instance,
							patient, rs);
				}	
			}

			return instance;

		} else if (CLINICAL_CATEGORITCAL_ATTRIBUTES.containsKey(attribute
				.name())) {
			// Categorical Attributes

			// Simple Categorical Attributes
			if (CLINICAL_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
				AttributeFactory.setSimpleCategoricalAttributeValue(attribute,
						CLINICAL_ATTRIBUTES_REGA_MAP.get(attribute.name()),
						instance, patient, rs);
			} else {
				// Calculated Categorical Attributes

				if (attribute
						.name()
						.equals(CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_IMMUNO_FAILURE)) {
					// Pre-Resistance Immunological Failure
					setAttributeValuePreResistanceImmunoFailure(attribute,
							instance, patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_1)) {
					// Other Drug 1
					setAttributeValueOtherDrug(attribute, instance, patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_2)) {
					// Other Drug 2
					setAttributeValueOtherDrug(attribute, instance, patient, rs);
				} else if (attribute.name().equals(
						CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_3)) {
					// Other Drug 3
					setAttributeValueOtherDrug(attribute, instance, patient, rs);
					/*
					 * } else if (attribute.name().equals(
					 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_1)) { // Drug
					 * Exposure 1 setAttributeValueDrugExposure(attribute,
					 * instance, patient, rs, 1); } else if
					 * (attribute.name().equals(
					 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_2)) { // Drug
					 * Exposure 1 setAttributeValueDrugExposure(attribute,
					 * instance, patient, rs, 2); } else if
					 * (attribute.name().equals(
					 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_3)) { // Drug
					 * Exposure 1 setAttributeValueDrugExposure(attribute,
					 * instance, patient, rs, 3); } else if
					 * (attribute.name().equals(
					 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_4)) { // Drug
					 * Exposure 1 setAttributeValueDrugExposure(attribute,
					 * instance, patient, rs, 4); } else if
					 * (attribute.name().equals(
					 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_5)) { // Drug
					 * Exposure 1 setAttributeValueDrugExposure(attribute,
					 * instance, patient, rs, 5); } else if
					 * (attribute.name().equals(
					 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_6)) { // Drug
					 * Exposure 1 setAttributeValueDrugExposure(attribute,
					 * instance, patient, rs, 6); } else if
					 * (attribute.name().equals(
					 * CLINICAL_PRETTY_ATTRIBUTE_DRUG_EXPOSURE_7)) { // Drug
					 * Exposure 1 setAttributeValueDrugExposure(attribute,
					 * instance, patient, rs, 7);
					 */
				} else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_EVER_SURPRESSED)) {
					setAttributeValueVirusEverSuppressed(attribute, instance, patient, rs);
				}
			}

			return instance;
		}

		return null;
	}

	/**
	 * Attribute specific methods to find values from patient
	 */

	private static void setAttributeValuePreResistanceImmunoFailure(
			Attribute attribute, Instance instance, Patient patient,
			RegaService rs) {
		Rule prtImmunoFailure = rs.getRuleService().getRule(
				PreResistanceImmunoFailure.class);

		prtImmunoFailure.setAttributeValue(attribute, instance, patient);
	}

	private static void setAttributeValueRecentBloodHB(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule recentBloodHBRule = rs.getRuleService().getRule(
				RecentBloodHBRuleImpl.class);

		recentBloodHBRule.setAttributeValue(attribute, instance, patient);
	}

	// Recent CD4 Gradient
	private static void setAttributeValueRecentCD4Gradient(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule recentCD4GradientRule = rs.getRuleService().getRule(
				RecentCD4GradientRuleImpl.class);

		recentCD4GradientRule.setAttributeValue(attribute, instance, patient);
	}

	private static void setAttributeValueRecentVLGradient(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule recentBloodVLGradientRule = rs.getRuleService().getRule(
				RecentVLGradientRuleImpl.class);

		recentBloodVLGradientRule.setAttributeValue(attribute, instance,
				patient);
	}

	private static void setAttributeValueRecentBloodCC(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule recentBloodCCRule = rs.getRuleService().getRule(
				RecentBloodCCRuleImpl.class);

		recentBloodCCRule.setAttributeValue(attribute, instance, patient);
	}

	// Determine Time On Failing Regimen
	private static void setAttributeValueTimeOnFailingRegimen(
			Attribute attribute, Instance instance, Patient patient,
			RegaService rs) {
		Rule timeOnFailingRegimenRule = rs.getRuleService().getRule(
				TimeOnFailingRegimenRuleImpl.class);

		timeOnFailingRegimenRule
				.setAttributeValue(attribute, instance, patient);
	}

	// Determine Pre-Resistance Viral Load Value
	private static void setAttributeValuePreResistanceViralLoad(
			Attribute attribute, Instance instance, Patient patient,
			RegaService rs) {
		Rule preResistanceTestViralLoadRule = rs.getRuleService().getRule(
				PreResistanceTestingViralLoadRuleImpl.class);

		preResistanceTestViralLoadRule.setAttributeValue(attribute, instance,
				patient);
	}

	// Determine Baseline CD4 Value
	public static void setAttributeValueBaselineCD4(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule baselineCD4Rule = rs.getRuleService().getRule(
				BaselineCD4RuleImpl.class);

		baselineCD4Rule.setAttributeValue(attribute, instance, patient);

		// instance.setValue(attribute, Double.parseDouble(value);
	}

	// Determine Baseline Viral Load Value
	public static void setAttributeValueBaselineVL(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule baselineVLRule = rs.getRuleService().getRule(
				BaselineVLRuleImpl.class);

		baselineVLRule.setAttributeValue(attribute, instance, patient);

		// instance.setValue(attribute, Double.parseDouble(value);
	}

	// Determine Pre-Resistance Testing CD4 Count Value
	public static void setAttributeValuePreResistanceCD4(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule preResistanceTestCD4Rule = rs.getRuleService().getRule(
				PreResistanceTestingCD4RuleImpl.class);

		preResistanceTestCD4Rule
				.setAttributeValue(attribute, instance, patient);
	}

	// Determine Other Drug Value
	public static void setAttributeValueOtherDrug(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule otherDrugRule = rs.getRuleService().getRule(
				OtherDrugRuleImpl.class);

		otherDrugRule.setAttributeValue(attribute, instance, patient);
	}

	// Drug Exposure (Numeric)
	public static void setAttributeValueDrugExposureNumeric(
			Attribute attribute, Instance instance, Patient patient,
			RegaService rs) {
		DrugExposureNumericRuleImpl drugExposureNumericRule = (DrugExposureNumericRuleImpl) rs
				.getRuleService().getRule(DrugExposureNumericRuleImpl.class);

		drugExposureNumericRule.setAttributeValue(attribute, instance, patient);
	}

	// Drug Exposure
	public static void setAttributeValueDrugExposure(Attribute attribute,
			Instance instance, Patient patient, RegaService rs, int num) {
		DrugExposureRuleImpl drugExposureRule = (DrugExposureRuleImpl) rs
				.getRuleService().getRule(DrugExposureRuleImpl.class);

		drugExposureRule.setAttributeValue(attribute, instance, patient, num);
	}

	// Drug Exposure Count
	private static void setAttributeValueDrugExposureCount(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule drugExposureCountRule = rs.getRuleService().getRule(
				DrugExposureCountRuleImpl.class);

		drugExposureCountRule.setAttributeValue(attribute, instance, patient);
	}
	
	// Virus Ever Suppressed
	private static void setAttributeValueVirusEverSuppressed(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule virusEverSuppressedRule = rs.getRuleService().getRule(
				VirusEverSuppressedRuleImpl.class);
		
		virusEverSuppressedRule.setAttributeValue(attribute, instance, patient);
	}
	
	// Mean Viral Load
	private static void setAttributeValueMeanViralLoad(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule meanViralLoadRule = rs.getRuleService().getRule(
				MeanViralLoadRuleImpl.class);
		
		meanViralLoadRule.setAttributeValue(attribute, instance, patient);
	}
	
	// Median Viral Load
	private static void setAttributeValueMedianViralLoad(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule medianViralLoadRule = rs.getRuleService().getRule(
				MedianViralLoadRuleImpl.class);
		
		medianViralLoadRule.setAttributeValue(attribute, instance, patient);
	}
	
	// Post Treatment CD4 Gradient
	private static void setAttributeValuePostTreatmentCD4Gradient(Attribute attribute,
			Instance instance, Patient patient, RegaService rs) {
		Rule postTreatmentCD4GradientRule = rs.getRuleService().getRule(
				PostTreatmentCD4GradientRuleImpl.class);
		
		postTreatmentCD4GradientRule.setAttributeValue(attribute, instance, patient);
	}

}
