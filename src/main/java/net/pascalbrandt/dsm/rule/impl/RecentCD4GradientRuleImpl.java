package net.pascalbrandt.dsm.rule.impl;

import java.util.Date;
import java.util.List;

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
		// TODO Auto-generated constructor stub
	}

	/*
        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        while(!StdIn.isEmpty()) {
            x[n] = StdIn.readDouble();
            y[n] = StdIn.readDouble();
            sumx  += x[n];
            sumx2 += x[n] * x[n];
            sumy  += y[n];
            n++;
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
        System.out.println("y   = " + beta1 + " * x + " + beta0);	 
    */
	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
	
        RegaService rs = ruleService.getRegaService();

        List<TestResult> recentCD4Counts = rs.getRecentCD4Counts(patient);
        
        if(recentCD4Counts != null && recentCD4Counts.size() > 1) {
        	logger.info("Setting CD4 Gradient Value");
        
	        logger.info("###");
	        for (TestResult tr : recentCD4Counts) {
	        	logger.info("{" + tr.getTestDate() + ", " + tr.getValue() + "}");
	        }
	        logger.info("###");
	        
	        double[] range = new double[recentCD4Counts.size()];
	        double[] domain = new double[recentCD4Counts.size()];
	        
	        Date baseDate = recentCD4Counts.get(recentCD4Counts.size() - 1).getTestDate();
	        
	        range[0] = Double.parseDouble(recentCD4Counts.get(recentCD4Counts.size() - 1).getValue());
	        domain[0] = 0;
	        
	        int j = 1;
	        for (int i = recentCD4Counts.size() - 2; i == 0; i--) {
	        	range[j] = Double.parseDouble(recentCD4Counts.get(i).getValue());
	        	
	        	domain[j] = Math.abs((baseDate.getTime() - recentCD4Counts.get(i).getTestDate().getTime())) / (1000 * 60 * 60 * 24);
	        }
	        
	        //TODO: FIXME: Finish this up
	        
        }
	}

}
