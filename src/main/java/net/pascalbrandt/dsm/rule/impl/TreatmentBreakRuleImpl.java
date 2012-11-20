package net.pascalbrandt.dsm.rule.impl;

import java.util.Date;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.AdherenceAttributeFactory;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;

public class TreatmentBreakRuleImpl extends Rule {

	public TreatmentBreakRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		
		RegaService rs = ruleService.getRegaService();
		
		PatientAttributeValue from = rs.getPatientAttributeValue(patient,
				AdherenceAttributeFactory.ADHERENCE_REGA_ATTRIBUTE_TREATMENT_BREAK_FROM);
		PatientAttributeValue to = rs.getPatientAttributeValue(patient,
				AdherenceAttributeFactory.ADHERENCE_REGA_ATTRIBUTE_TREATMENT_BREAK_TO);
		
		
		if (from != null && to != null) {
			Date toDate = new Date(Long.parseLong(to.getValue()));
			Date fromDate = new Date(Long.parseLong(from.getValue()));

			// Calculate date difference in days
			long diffInDays = (toDate.getTime() - fromDate.getTime())
					/ (1000 * 60 * 60 * 24);

			logger.info("Setting attribute value: " + attribute.name() + " -> "
					+ diffInDays);
			instance.setValue(attribute, diffInDays);
		}
	}
}
