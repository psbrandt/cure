package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.regadb.db.Patient;

import weka.core.Attribute;
import weka.core.Instance;

public class ClassAttributeFactory {
    private static final Logger logger = LoggerFactory.getLogger(ClassAttributeFactory.class);

    // Index in feature vector of the class attribute
    public static final int CLASS_ATTRIBUTE_INDEX = 0;

    // Pretty Class Attribute Names
    public static final String CLASS_ATTRIBUTE_NAME = "Class";

    // Pretty Class Attribute Names
    public static final String CLASS_ATTRIBUTE_RESISTANT = "Resistant";
    public static final String CLASS_ATTRIBUTE_NOT_RESISTANT = "Not Resistant";

    // Value to indicate class cannot be determined
    public static final String CLASS_ATTRIBUTE_UNLABELED = "Unlabeled";

    // Classes
    public static final Set<String> CLASS_ATTRIBUTES = new HashSet<String>(
            Arrays.asList(new String[] { "dummy", CLASS_ATTRIBUTE_RESISTANT, CLASS_ATTRIBUTE_NOT_RESISTANT }));

    // Get the class attribute
    public static Attribute getClassAttribute() {
        ArrayList<String> classes = new ArrayList<String>(2);

        classes.addAll(CLASS_ATTRIBUTES);

        return new Attribute(CLASS_ATTRIBUTE_NAME, classes);
    }

    // Classify the patient using the RegaService classification method
    public static Instance addAttributeValue(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {
        if (attribute.name().equals(CLASS_ATTRIBUTE_NAME)) {
            logger.info("Adding class value.");

            String label = rs.classifyPatient(patient);

            if (CLASS_ATTRIBUTES.contains(label)) {
                instance.setValue(attribute, label);
                return instance;
            }

            return null;
        }
        return null;
    }
}
