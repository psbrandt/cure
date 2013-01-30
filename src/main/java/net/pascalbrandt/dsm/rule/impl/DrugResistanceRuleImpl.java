package net.pascalbrandt.dsm.rule.impl;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;

public class DrugResistanceRuleImpl extends Rule {

	public DrugResistanceRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		// TODO Auto-generated method stub
		
	}
	
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient, int num) {
		// TODO Auto-generated method stub
		
	}

}
