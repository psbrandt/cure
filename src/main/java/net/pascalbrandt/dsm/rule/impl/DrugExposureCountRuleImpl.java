package net.pascalbrandt.dsm.rule.impl;

import java.util.HashMap;
import java.util.Set;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.db.TherapyGeneric;

public class DrugExposureCountRuleImpl extends Rule {

	public DrugExposureCountRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		Set<Therapy> therapies = patient.getTherapies();
		
		HashMap drugs = new HashMap<String, Integer>();
		
		for (Therapy t : therapies) {
			
			Set<TherapyGeneric> therapyGenerics = t.getTherapyGenerics();
			
			
			
			for (TherapyGeneric tg : therapyGenerics) {
				String name = tg.getId().getDrugGeneric().getGenericName();
				//logger.info(">> " + name);
				
				if(drugs.containsKey(name)) {
					drugs.put(name, (Integer)drugs.get(name) + 1);
				} else {
					drugs.put(name, 1);
				}
			}
			
			
		}
		
		logger.info("Drug Exposure Count: " + drugs.size());
		
		instance.setValue(attribute, drugs.size());
		
	}

}
