package net.pascalbrandt.dsm.rule.impl;

import java.util.List;

import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import weka.core.Attribute;
import weka.core.Instance;

public class MeanViralLoadRuleImpl extends Rule {

	public MeanViralLoadRuleImpl(RuleService rs) {
		super(rs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		
		List<Double> viralLoads = ruleService.getRegaService().getViralLoads(patient);
		
		double total = 0.0;
		
		for(Double d : viralLoads) {
			total += d;
		}
		
		instance.setValue(attribute, total / (double)viralLoads.size());
	}

}
