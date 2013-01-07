package net.pascalbrandt.dsm.rule.impl;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;

public class RecentVLGradientRuleImpl extends Rule {

	public RecentVLGradientRuleImpl(RuleService rs) {
		super(rs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		// TODO Auto-generated method stub

	}

}
