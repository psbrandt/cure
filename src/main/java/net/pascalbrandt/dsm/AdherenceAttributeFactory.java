package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;

import weka.core.Attribute;
import weka.core.Instance;

public class AdherenceAttributeFactory {
    private static final Logger logger = LoggerFactory.getLogger(AdherenceAttributeFactory.class);

    // Pretty Numeric Attribute Names
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_TREATMENT_BREAK = "Treatment Break";

    // Pretty Categorical Attribute Names
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_PATIENT_ESTIMATED_ADHERENCE = "Patient Estimated Adherence";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_REMEMBER = "Remember";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_STOP = "Stop";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_MISSED = "Missed";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_WORST_STOP = "Worst Stop";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_NAMES = "Names";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_SIDE_EFFECTS = "Side Effects";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_DISCLOSURE = "Disclosure";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_BUDDY = "Buddy";
    public static final String ADHERENCE_PRETTY_ATTRIBUTE_COUNSELING = "Counseling";

    // Rega Attribute Names
    public static final String ADHERENCE_REGA_ATTRIBUTE_TREATMENT_BREAK_FROM = "Treatment_Break_FROM";
    public static final String ADHERENCE_REGA_ATTRIBUTE_TREATMENT_BREAK_TO = "Treatment_Break_TO";
    public static final String ADHERENCE_REGA_ATTRIBUTE_PATIENT_ESTIMATED_ADHERENCE = "Patient_estimated_adherence";
    public static final String ADHERENCE_REGA_ATTRIBUTE_REMEMBER = "Q1_remember";
    public static final String ADHERENCE_REGA_ATTRIBUTE_STOP = "Q2_stop";
    public static final String ADHERENCE_REGA_ATTRIBUTE_MISSED = "Q3_missed";
    public static final String ADHERENCE_REGA_ATTRIBUTE_WORST_STOP = "Q4_worst_stop";
    public static final String ADHERENCE_REGA_ATTRIBUTE_NAMES = "Q5_names";
    public static final String ADHERENCE_REGA_ATTRIBUTE_SIDE_EFFECTS = "Q6_side_effects";
    public static final String ADHERENCE_REGA_ATTRIBUTE_DISCLOSURE = "Q7_disclosure";
    public static final String ADHERENCE_REGA_ATTRIBUTE_BUDDY = "Q8_buddy";
    public static final String ADHERENCE_REGA_ATTRIBUTE_COUNSELING = "Q9_couseling";

    // Numeric Attributes
    public static final Set<String> ADHERENCE_NUMERIC_ATTRIBUTES = new HashSet<String>(
            Arrays.asList(new String[] { ADHERENCE_PRETTY_ATTRIBUTE_TREATMENT_BREAK }));

    // Categorical Attributes
    public static Map<String, String[]> ADHERENCE_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();

    // Pretty to Rega name map
    public static Map<String, String> ADHERENCE_ATTRIBUTES_REGA_MAP = new HashMap<String, String>();

    static {
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(
                ADHERENCE_PRETTY_ATTRIBUTE_PATIENT_ESTIMATED_ADHERENCE, new String[] { "Less Good",
                        "Poor", "Good", "None" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_REMEMBER, new String[] {
                "Yes", "No" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_STOP, new String[] {
                "Yes", "No" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_MISSED, new String[] {
                "Yes", "No" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_WORST_STOP, new String[] {
                "Yes", "No" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_NAMES, new String[] {
                "Yes", "No" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_SIDE_EFFECTS,
                new String[] { "Yes", "No" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_DISCLOSURE, new String[] {
                "Yes", "No" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_BUDDY, new String[] {
                "Yes", "No" });
        ADHERENCE_CATEGORITCAL_ATTRIBUTES.put(ADHERENCE_PRETTY_ATTRIBUTE_COUNSELING, new String[] {
                "Yes", "No" });

        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_PATIENT_ESTIMATED_ADHERENCE,
                ADHERENCE_REGA_ATTRIBUTE_PATIENT_ESTIMATED_ADHERENCE);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_REMEMBER,
                ADHERENCE_REGA_ATTRIBUTE_REMEMBER);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_STOP,
                ADHERENCE_REGA_ATTRIBUTE_STOP);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_MISSED,
                ADHERENCE_REGA_ATTRIBUTE_MISSED);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_WORST_STOP,
                ADHERENCE_REGA_ATTRIBUTE_WORST_STOP);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_NAMES,
                ADHERENCE_REGA_ATTRIBUTE_NAMES);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_SIDE_EFFECTS,
                ADHERENCE_REGA_ATTRIBUTE_SIDE_EFFECTS);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_DISCLOSURE,
                ADHERENCE_REGA_ATTRIBUTE_DISCLOSURE);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_BUDDY,
                ADHERENCE_REGA_ATTRIBUTE_BUDDY);
        ADHERENCE_ATTRIBUTES_REGA_MAP.put(ADHERENCE_PRETTY_ATTRIBUTE_COUNSELING,
                ADHERENCE_REGA_ATTRIBUTE_COUNSELING);
    }

    // Get a list of the adherence attributes
    public static List<Attribute> getAdherenceAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();

        // Numeric Attributes
        for (String name : ADHERENCE_NUMERIC_ATTRIBUTES) {
            attributes.add(new Attribute(name));
        }

        // Categorical Attributes
        for (String name : ADHERENCE_CATEGORITCAL_ATTRIBUTES.keySet()) {
            attributes.add(new Attribute(name, Arrays.asList(ADHERENCE_CATEGORITCAL_ATTRIBUTES
                    .get(name))));
        }

        return attributes;
    }

    // Get a list of the adherence attribute names
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

    // Create attributes for the given list of attribute names
    public static List<Attribute> createAttributes(String[] selectedAdherenceAttributes) {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();

        for (String attributeName : selectedAdherenceAttributes) {
            // Numeric Attributes
            if (ADHERENCE_NUMERIC_ATTRIBUTES.contains(attributeName)) {
                attributes.add(new Attribute(attributeName));
            }

            // Categorical Attributes
            if (ADHERENCE_CATEGORITCAL_ATTRIBUTES.containsKey(attributeName)) {
                attributes.add(new Attribute(attributeName, Arrays
                        .asList(ADHERENCE_CATEGORITCAL_ATTRIBUTES.get(attributeName))));
            }
        }

        return attributes;
    }

    // Add the value to the instance for the given attribute and patient
    public static Instance addAttributeValue(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {

        if (ADHERENCE_NUMERIC_ATTRIBUTES.contains(attribute.name())) {
            // Numeric Attributes

            // Simple Numeric Attributes
            if (ADHERENCE_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
                AttributeFactory.setSimpleNumericAttributeValue(attribute,
                        ADHERENCE_ATTRIBUTES_REGA_MAP.get(attribute.name()), instance, patient, rs);
            } else {
                // Calculated Numeric Attributes

                if (attribute.name().equals(ADHERENCE_PRETTY_ATTRIBUTE_TREATMENT_BREAK)) {
                    // Treatment Break
                    setAttributeValueTreatmentBreak(attribute, instance, patient, rs);
                }
            }

            return instance;

        } else if (ADHERENCE_CATEGORITCAL_ATTRIBUTES.containsKey(attribute.name())) {
            // Categorical Attributes

            // Simple Categorical Attributes
            if (ADHERENCE_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
                AttributeFactory.setSimpleCategoricalAttributeValue(attribute,
                        ADHERENCE_ATTRIBUTES_REGA_MAP.get(attribute.name()), instance, patient, rs);
            } else {
                // Calculated Categorical Attributes

                // There are no calculated adherence categorical attributes
            }

            return instance;
        }

        return null;
    }

    /**
     * Attribute specific methods to find values from patient
     */

    // Treatment Break
    public static void setAttributeValueTreatmentBreak(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {

        PatientAttributeValue from = rs.getPatientAttributeValue(patient,
                ADHERENCE_REGA_ATTRIBUTE_TREATMENT_BREAK_FROM);
        PatientAttributeValue to = rs.getPatientAttributeValue(patient,
                ADHERENCE_REGA_ATTRIBUTE_TREATMENT_BREAK_TO);

        if (from != null && to != null) {
            Date toDate = new Date(Long.parseLong(to.getValue()));
            Date fromDate = new Date(Long.parseLong(from.getValue()));

            // Calculate date difference in days
            long diffInDays = (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24);

            logger.info("Adding treatment break value: " + diffInDays + " days.");
            instance.setValue(attribute, diffInDays);
        }
    }
}
