package net.pascalbrandt.dsm;

import java.util.HashMap;

import net.pascalbrandt.dsm.rule.Rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("RuleService")
public class RuleService implements ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(RuleService.class);
    private ApplicationContext context;

    private HashMap<Class, Rule> rules = new HashMap<Class, Rule>();

    public Rule getRule(Class clazz) {
        if (rules.containsKey(clazz))
            return rules.get(clazz);
        return null;
    }

    public void addRule(Class clazz, Rule rule) {
        rules.put(clazz, rule);
    }
    
    public RegaService getRegaService() {
        return context.getBean(RegaService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
