package net.pascalbrandt.dsm.rule.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;

public class MedianViralLoadRuleImpl extends Rule {

	public MedianViralLoadRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		List<Double> viralLoads = ruleService.getRegaService().getViralLoads(patient);
		
		if(viralLoads.size() == 0)
			return;
		
		instance.setValue(attribute, median(viralLoads));
	}
	
	public double median(List<Double> values) {
		Collections.sort(values);
		
		if (values.size() % 2 == 1)
			return values.get((values.size() + 1) / 2 - 1);
		else {
			double lower = values.get(values.size() / 2 - 1);
			double upper = values.get(values.size() / 2);

			return (lower + upper) / 2.0;
		}
	}


}
