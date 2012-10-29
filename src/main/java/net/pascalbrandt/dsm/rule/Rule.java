package net.pascalbrandt.dsm.rule;

import net.pascalbrandt.dsm.RuleService;
import net.sf.regadb.db.Patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import weka.core.Attribute;
import weka.core.Instance;

/**
 * Abstract rule
 */
@Component
public abstract class Rule implements ApplicationContextAware {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ApplicationContext context;

    protected RuleService ruleService = null;

    public Rule(RuleService rs) {
        ruleService = rs;
    }    
    
    // Method which must be overridden for a rule to be instantiated
    public abstract void setAttributeValue(Attribute attribute, Instance instance, Patient patient);

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }
}
