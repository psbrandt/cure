package net.pascalbrandt.dsm.rule;

import net.sf.regadb.db.Patient;
import weka.core.Attribute;
import weka.core.Instance;

/**
 * Rule interface
 * 
 * @author pascalbrandt
 *
 */
public interface Rule {
	
	public Attribute getAttribute();
	
	public Instance getInstance(Patient p);
	
}
