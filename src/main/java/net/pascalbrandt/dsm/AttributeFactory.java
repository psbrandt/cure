package net.pascalbrandt.dsm;

import java.util.List;

import weka.core.Attribute;

public class AttributeFactory {
	/*




	*/
	
	public AttributeFactory() {
		
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
