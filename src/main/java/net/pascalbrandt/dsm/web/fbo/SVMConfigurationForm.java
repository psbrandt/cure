package net.pascalbrandt.dsm.web.fbo;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import net.pascalbrandt.dsm.AttributeFactory;

public class SVMConfigurationForm {

	Integer type; // Type of SVM
	Integer kernel; // Type of Kernel
	Double gamma; // Gamma Value
	Double C; // C Values

	String[] selectedDemographicAttributes;
	String[] selectedClinicalAttributes;
	String[] selectedAdherenceAttributes;
	String[] selectedOtherAttributes;

	public SVMConfigurationForm() {

		// Default to all demographic attributes selected
		List<String> attributeDemographicNames = AttributeFactory
				.getDemographicAttributeNames();
		selectedDemographicAttributes = attributeDemographicNames
				.toArray(new String[attributeDemographicNames.size()]);

		// Default to all demographic attributes selected
		List<String> attributeClinicalNames = AttributeFactory
				.getClinicalAttributeNames();
		selectedClinicalAttributes = attributeClinicalNames
				.toArray(new String[attributeClinicalNames.size()]);

		// Default to all demographic attributes selected
		List<String> attributeAdherenceNames = AttributeFactory
				.getAdherenceAttributeNames();
		selectedAdherenceAttributes = attributeAdherenceNames
				.toArray(new String[attributeAdherenceNames.size()]);

		// Default to all demographic attributes selected
		List<String> attributeOtherNames = AttributeFactory
				.getOtherAttributeNames();
		selectedOtherAttributes = attributeOtherNames
				.toArray(new String[attributeOtherNames.size()]);

		// Default values for the RBF parameters
		gamma = new Double(1);
		C = new Double(1);
	}

	public String[] getSelectedDemographicAttributes() {
		return selectedDemographicAttributes;
	}

	public void setSelectedDemographicAttributes(String[] selectedAttributes) {
		this.selectedDemographicAttributes = selectedAttributes;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getKernel() {
		return kernel;
	}

	public void setKernel(Integer kernel) {
		this.kernel = kernel;
	}

	public Double getGamma() {
		return gamma;
	}

	public void setGamma(Double gamma) {
		this.gamma = gamma;
	}

	public Double getC() {
		return C;
	}

	public void setC(Double c) {
		C = c;
	}

	public String[] getSelectedClinicalAttributes() {
		return selectedClinicalAttributes;
	}

	public void setSelectedClinicalAttributes(
			String[] selectedClinicalAttributes) {
		this.selectedClinicalAttributes = selectedClinicalAttributes;
	}

	public String[] getSelectedAdherenceAttributes() {
		return selectedAdherenceAttributes;
	}

	public void setSelectedAdherenceAttributes(
			String[] selectedAdherenceAttributes) {
		this.selectedAdherenceAttributes = selectedAdherenceAttributes;
	}

	public String[] getSelectedOtherAttributes() {
		return selectedOtherAttributes;
	}

	public void setSelectedOtherAttributes(String[] selectedOtherAttributes) {
		this.selectedOtherAttributes = selectedOtherAttributes;
	}
}
