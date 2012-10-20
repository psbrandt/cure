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

import net.sf.regadb.db.Patient;

import weka.core.Attribute;
import weka.core.Instance;

public class ClinicalAttributeFactory {
    private static final Logger logger = LoggerFactory.getLogger(ClinicalAttributeFactory.class);

    // Pretty Numeric Attribute Names
    public static final String CLINICAL_PRETTY_ATTRIBUTE_WEIGHT = "Weight";
    public static final String CLINICAL_PRETTY_ATTRIBUTE_BASELINE_CD4 = "Baseline CD4";
    public static final String CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_CD4 = "Pre-Resistance Testing CD4 Count";
    public static final String CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_VL = "Pre-Resistance Testing Viral Load";
    public static final String CLINICAL_PRETTY_ATTRIBUTE_TIME_ON_FAILING_REGIMEN = "Time on Failing Regimen";
    public static final String CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_CC = "Recent Blood Creatinine Clearance";
    public static final String CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_ALT = "Recent Blood ALT";
    public static final String CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_HB = "Recent Blood HB";

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

    // Rega Attribute Names
    public static final String CLINICAL_REGA_ATTRIBUTE_WEIGHT = "Weight";
    public static final String CLINICAL_REGA_ATTRIBUTE_TRANSMISSION_GROUP = "Transmission group";
    public static final String CLINICAL_REGA_ATTRIBUTE_HTLV1_STATUS = "HTLV1status";
    public static final String CLINICAL_REGA_ATTRIBUTE_HBV_STATUS = "9_HBV_status";

    // Numeric Attributes
    public static final Set<String> CLINICAL_NUMERIC_ATTRIBUTES = new HashSet<String>(
            Arrays.asList(new String[] { CLINICAL_PRETTY_ATTRIBUTE_WEIGHT,
                    CLINICAL_PRETTY_ATTRIBUTE_BASELINE_CD4,
                    CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_CD4,
                    CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_VL,
                    CLINICAL_PRETTY_ATTRIBUTE_TIME_ON_FAILING_REGIMEN,
                    CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_CC,
                    CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_ALT,
                    CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_HB }));

    // Categorical Attributes
    public static Map<String, String[]> CLINICAL_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();

    // Pretty to Rega name map
    public static Map<String, String> CLINICAL_ATTRIBUTES_REGA_MAP = new HashMap<String, String>();

    static {
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(
                CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_IMMUNO_FAILURE,
                new String[] { "Yes", "No" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_TRANSMISSION_GROUP,
                new String[] { "homosexual + IVDU", "haemophiliac", "occupational exposure",
                        "unknown", "heterosexual", "vertical", "bisexual + IVDU", "homosexual",
                        "IVDU", "heterosexual + IVDU", "bisexual", "other", "transfusion" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_HTLV1_STATUS, new String[] {
                "positive", "negative" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_HBV_STATUS, new String[] {
                "Not tested", "Neg", "Pos" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_PRIOR,
                new String[] { "Regimen 1", "Regimen 2", "MDR" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_DURING,
                new String[] { "Regimen 1", "Regimen 2", "MDR" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_POST,
                new String[] { "Regimen 1", "Regimen 2", "MDR" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_1, new String[] {
                "Yes", "No" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_2, new String[] {
                "Yes", "No" });
        CLINICAL_CATEGORITCAL_ATTRIBUTES.put(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_3, new String[] {
                "Yes", "No" });

        CLINICAL_ATTRIBUTES_REGA_MAP.put(CLINICAL_PRETTY_ATTRIBUTE_WEIGHT,
                CLINICAL_REGA_ATTRIBUTE_WEIGHT);
        CLINICAL_ATTRIBUTES_REGA_MAP.put(CLINICAL_PRETTY_ATTRIBUTE_TRANSMISSION_GROUP,
                CLINICAL_REGA_ATTRIBUTE_TRANSMISSION_GROUP);
        CLINICAL_ATTRIBUTES_REGA_MAP.put(CLINICAL_PRETTY_ATTRIBUTE_HTLV1_STATUS,
                CLINICAL_REGA_ATTRIBUTE_HTLV1_STATUS);
        CLINICAL_ATTRIBUTES_REGA_MAP.put(CLINICAL_PRETTY_ATTRIBUTE_HBV_STATUS,
                CLINICAL_REGA_ATTRIBUTE_HBV_STATUS);
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
            attributes.add(new Attribute(name, Arrays.asList(CLINICAL_CATEGORITCAL_ATTRIBUTES
                    .get(name))));
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
    public static List<Attribute> createAttributes(String[] selectedClinicalAttributes) {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();

        for (String attributeName : selectedClinicalAttributes) {
            // Numeric Attributes
            if (CLINICAL_NUMERIC_ATTRIBUTES.contains(attributeName)) {
                attributes.add(new Attribute(attributeName));
            }

            // Categorical Attributes
            if (CLINICAL_CATEGORITCAL_ATTRIBUTES.containsKey(attributeName)) {
                attributes.add(new Attribute(attributeName, Arrays
                        .asList(CLINICAL_CATEGORITCAL_ATTRIBUTES.get(attributeName))));
            }
        }

        return attributes;
    }

    // Add the value to the instance for the given attribute and patient
    public static Instance addAttributeValue(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {

        if (CLINICAL_NUMERIC_ATTRIBUTES.contains(attribute.name())) {
            // Numeric Attributes

            // Simple Numeric Attributes
            if (CLINICAL_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
                AttributeFactory.setSimpleNumericAttributeValue(attribute,
                        CLINICAL_ATTRIBUTES_REGA_MAP.get(attribute.name()), instance, patient, rs);
            } else {
                // Calculated Numeric Attributes

                if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_BASELINE_CD4)) {
                    // Baseline CD4
                    setAttributeValueBaselineCD4(attribute, instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_CD4)) {
                    // Pre-Resistance CD4 Count
                    setAttributeValuePreResistanceCD4(attribute, instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_VL)) {
                    // Pre-Restiance Viral Load
                    setAttributeValuePreResistanceViralLoad(attribute, instance, patient, rs);
                } else if (attribute.name().equals(
                        CLINICAL_PRETTY_ATTRIBUTE_TIME_ON_FAILING_REGIMEN)) {
                    // Time on failing regiment
                    setAttributeValueTimeOnFailingRegimen(attribute, instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_CC)) {
                    // Recent Blood Creatinine Clearance
                    setAttributeValueRecentBloodCC(attribute, instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_ALT)) {
                    // Recent Blood ALT
                    setAttributeValueRecentBloodALT(attribute, instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_RECENT_BLOOD_HB)) {
                    // Recent Blood HB
                    setAttributeValueRecentBloodHB(attribute, instance, patient, rs);
                }
            }

            return instance;

        } else if (CLINICAL_CATEGORITCAL_ATTRIBUTES.containsKey(attribute.name())) {
            // Categorical Attributes

            // Simple Categorical Attributes
            if (CLINICAL_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
                AttributeFactory.setSimpleCategoricalAttributeValue(attribute,
                        CLINICAL_ATTRIBUTES_REGA_MAP.get(attribute.name()), instance, patient, rs);
            } else {
                // Calculated Categorical Attributes

                if (attribute.name()
                        .equals(CLINICAL_PRETTY_ATTRIBUTE_PRE_RESISTANCE_IMMUNO_FAILURE)) {
                    // Pre-Resistance Immunological Failure
                    setAttributeValuePreResistanceImmunoFailure(attribute, instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_PRIOR)) {
                    // Prior TB Therapy
                    setAttributeValueTBTherapy(attribute, "Prior", instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_DURING)) {
                    // During TB Therapy
                    setAttributeValueTBTherapy(attribute, "During", instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_TB_THERAPY_POST)) {
                    // Post TB Therapy
                    setAttributeValueTBTherapy(attribute, "Post", instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_1)) {
                    // Other Drug 1
                    setAttributeValueTBTherapy(attribute, "1", instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_2)) {
                    // Other Drug 2
                    setAttributeValueTBTherapy(attribute, "2", instance, patient, rs);
                } else if (attribute.name().equals(CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_3)) {
                    // Other Drug 3
                    setAttributeValueTBTherapy(attribute, "3", instance, patient, rs);
                }
            }

            return instance;
        }

        return null;
    }

    /**
     * Attribute specific methods to find values from patient
     */

    private static void setAttributeValuePreResistanceImmunoFailure(Attribute attribute,
            Instance instance, Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    private static void setAttributeValueRecentBloodHB(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    private static void setAttributeValueRecentBloodALT(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    private static void setAttributeValueRecentBloodCC(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    // Determine Time On Failing Regiment
    private static void setAttributeValueTimeOnFailingRegimen(Attribute attribute,
            Instance instance, Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    // Determine Pre-Resistance Viral Load Value
    private static void setAttributeValuePreResistanceViralLoad(Attribute attribute,
            Instance instance, Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    // Determine Baseline CD4 Value
    public static void setAttributeValueBaselineCD4(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    // Determine Pre-Resistance Testing CD4 Count Value
    public static void setAttributeValuePreResistanceCD4(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    // Determine TB Therapy Value
    public static void setAttributeValueTBTherapy(Attribute attribute, String time,
            Instance instance, Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }

    // Determine Other Drug Value
    public static void setAttributeValueOtherDrug(Attribute attribute, String number,
            Instance instance, Patient patient, RegaService rs) {
        // TODO Auto-generated method stub
    }
}
