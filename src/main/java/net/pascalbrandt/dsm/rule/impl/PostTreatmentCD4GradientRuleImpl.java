package net.pascalbrandt.dsm.rule.impl;

import java.util.List;

import net.pascalbrandt.dsm.DataService;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestResult;
import weka.core.Attribute;
import weka.core.Instance;

public class PostTreatmentCD4GradientRuleImpl extends Rule {

	public PostTreatmentCD4GradientRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		
		RegaService rs = ruleService.getRegaService();
		
		DataService ds = ruleService.getDataService();
		
		List<TestResult> postTreatmentCD4Counts = rs.getPostTreatmentCD4Counts(patient);
		
		Double leastSquaresRegressionGradient = ds.getLeastSquaresFitGradient(postTreatmentCD4Counts);
		
		logger.info("PTCD4 #: " + postTreatmentCD4Counts.size() + ", Gradient: " + leastSquaresRegressionGradient);
		
		if(leastSquaresRegressionGradient != null)
			instance.setValue(attribute, leastSquaresRegressionGradient);
	}

}
