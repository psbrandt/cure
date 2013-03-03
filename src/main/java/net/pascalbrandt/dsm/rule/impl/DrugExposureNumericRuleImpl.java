package net.pascalbrandt.dsm.rule.impl;

import java.util.HashMap;
import java.util.Set;

import net.pascalbrandt.dsm.ClinicalAttributeFactory;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.db.TherapyGeneric;
import weka.core.Attribute;
import weka.core.Instance;

public class DrugExposureNumericRuleImpl extends Rule {

	public DrugExposureNumericRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		
		String attributeDrugName = attribute.name().substring(0, 
				attribute.name().indexOf(ClinicalAttributeFactory.EXPOSURE_MATCH_STRING) - 1).toLowerCase();
		logger.info("Calculating length of exposure to: [" + attributeDrugName + "]");
		
		Set<Therapy> therapies = patient.getTherapies();
		
		double exposureLength = 0;
		
		for (Therapy t : therapies) {
			
			Set<TherapyGeneric> therapyGenerics = t.getTherapyGenerics();
			
			for (TherapyGeneric tg : therapyGenerics) {
				String drugName = tg.getId().getDrugGeneric().getGenericName();

				if(drugName.equals(attributeDrugName)) {
					exposureLength += ruleService.getRegaService().getTherapyLength(t, patient);
				}
			}

		}
		
		instance.setValue(attribute, exposureLength);
	}
}

