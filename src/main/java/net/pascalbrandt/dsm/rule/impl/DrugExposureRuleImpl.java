package net.pascalbrandt.dsm.rule.impl;

import java.util.HashMap;
import java.util.Set;

import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.db.TherapyGeneric;
import weka.core.Attribute;
import weka.core.Instance;

public class DrugExposureRuleImpl extends Rule {

	public DrugExposureRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		
	}
	
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient, int num) {
		
		Set<Therapy> therapies = patient.getTherapies();
		
		HashMap drugs = new HashMap<String, Integer>();
		
		for (Therapy t : therapies) {
			
			Set<TherapyGeneric> therapyGenerics = t.getTherapyGenerics();
			
			for (TherapyGeneric tg : therapyGenerics) {
				String name = tg.getId().getDrugGeneric().getGenericName();
				logger.info(">> " + name);
				
				if(drugs.containsKey(name)) {
					drugs.put(name, (Integer)drugs.get(name) + 1);
				} else {
					drugs.put(name, 1);
				}
			}

		}
		
		Set<String> drugKeySet = drugs.keySet();
		
		// Make sure we have enough drugs in the set
		if (drugKeySet.size() < num)
			return;
		
		int i = 0;
		for(String s : drugKeySet) {
			if (i + 1 == num) {
				instance.setValue(attribute, s);
			}
			i++;
		}
	}
}
