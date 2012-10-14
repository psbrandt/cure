package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pascalbrandt.dsm.web.fbo.SVMConfigurationForm;

import weka.core.Attribute;

public class AttributeFactory {
	private static final Logger logger = LoggerFactory.getLogger(AttributeFactory.class);
	
	public AttributeFactory() {	}
	
	public static List<Attribute> constructAttributeList(SVMConfigurationForm config) {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		// Demographic Attributes
		attributes.addAll(DemographicAttributeFactory.createAttributes(config.getSelectedDemographicAttributes()));
		
		// Clinical Attributes
		attributes.addAll(ClinicalAttributeFactory.createAttributes(config.getSelectedClinicalAttributes()));
		
		// Adherence Attributes
		attributes.addAll(AdherenceAttributeFactory.createAttributes(config.getSelectedAdherenceAttributes()));
		
		// Other Attributes
		attributes.addAll(OtherAttributeFactory.createAttributes(config.getSelectedOtherAttributes()));		
		
		// Class Attribute
		attributes.add(ClassAttributeFactory.CLASS_ATTRIBUTE_INDEX, ClassAttributeFactory.getClassAttibute());
		
		return attributes;
	}
	
	public static void displayAttributeList(List<Attribute> attributes) {
		logger.info("Attributes [" + attributes.size() + "]:");
		
		int i = 0;
		for (Attribute a : attributes) {
			logger.info("[" + i++ + "] " + a.name() + " (" + Attribute.typeToString(a) + ")");
		}
	}
	
	public static List<Attribute> getDemographicAttributes() {
		return DemographicAttributeFactory.getDemographicAttributes();
	}
	
	public static List<String> getDemographicAttributeNames() {
		return DemographicAttributeFactory.getDemographicAttributeNames();
	}
	
	public static List<Attribute> getClinicalAttributes() {
		return ClinicalAttributeFactory.getClinicalAttributes();
	}
	
	public static List<String> getClinicalAttributeNames() {
		return ClinicalAttributeFactory.getClinicalAttributeNames();
	}
	
	public static List<Attribute> getAdherenceAttributes() {
		return AdherenceAttributeFactory.getAdherenceAttributes();
	}
	
	public static List<String> getAdherenceAttributeNames() {
		return AdherenceAttributeFactory.getAdherenceAttributeNames();
	}
	
	public static List<Attribute> getOtherAttributes() {
		return OtherAttributeFactory.getOtherAttributes();
	}
	
	public static List<String> getOtherAttributeNames() {
		return OtherAttributeFactory.getOtherAttributeNames();
	}
}
