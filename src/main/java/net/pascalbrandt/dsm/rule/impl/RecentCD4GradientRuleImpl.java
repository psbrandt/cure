package net.pascalbrandt.dsm.rule.impl;

import java.util.List;

import net.pascalbrandt.dsm.DataService;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.core.Attribute;
import weka.core.Instance;

public class RecentCD4GradientRuleImpl extends Rule {
	private static final Logger logger = LoggerFactory
			.getLogger(RecentCD4GradientRuleImpl.class);

	public RecentCD4GradientRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
	
        RegaService rs = ruleService.getRegaService();
        
        DataService ds = ruleService.getDataService();

        List<TestResult> recentCD4Counts = rs.getRecentCD4Counts(patient);
        
        Double leastSquaresRegressionGradient = ds.getLeastSquaresFitGradient(recentCD4Counts);
		
		logger.info("RecentCD4 #: " + recentCD4Counts.size() + ", Gradient: " + leastSquaresRegressionGradient);
		
		if(leastSquaresRegressionGradient != null)
			instance.setValue(attribute, leastSquaresRegressionGradient);
	}

}
