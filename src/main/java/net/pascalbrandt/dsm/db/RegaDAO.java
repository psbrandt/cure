package net.pascalbrandt.dsm.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.pascalbrandt.dsm.DataService;
import net.pascalbrandt.dsm.HibernateSessionFactoryUtil;
import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestResult;
import net.sf.regadb.db.TestType;
import net.sf.regadb.db.Therapy;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("RegaDAO")
public class RegaDAO {
	private static Session session = null;
	
	public RegaDAO() {
		
	}
	
	public List<Patient> getPatients() throws Exception {
		
		Query q = getSession().createQuery(
            "select new net.sf.regadb.db.Patient(patient, max(access.permissions)) " +
    		"from PatientImpl as patient " +
            "join patient.patientDatasets as patient_dataset " +
            "join patient_dataset.id.dataset as dataset " +
            "join dataset.datasetAccesses access " +
            "group by patient order by patient");

		return (List<Patient>)q.list();
	}
	
	public Integer getPatientCount() throws Exception {
		
		return getPatients().size();
	}
	
	public Date getLatestTestResultDate(List<Integer> testTypes, Integer patientId) {
		/*
		 select * from regadbschema.test_result as tr 
	 join regadbschema.test as t on t.test_ii = tr.test_ii
	 where t.test_type_ii in (45, 46) and tr.patient_ii= 6550 limit 1000
		 */
		
		StringBuffer query = new StringBuffer();
		
		query.append("select tr from TestResult as tr join tr.test.testType as tt where tt.testTypeIi in (");
				
		int i = 0;
		for (Integer tt : testTypes) {
			query.append(tt);
			
			if(i != testTypes.size() - 1)
				query.append(",");
				
			i++;	
		}
					
		query.append(") and tr.patient.patientIi = :patientId order by tr.testDate desc");	
		
		Query q = getSession().createQuery(query.toString());            

		q.setParameter("patientId", patientId);
		q.setFetchSize(1);
		
		List<TestResult> results = (List<TestResult>)q.list();
		
		if (results == null || results.size() == 0)
			return null;
		
		return results.get(0).getTestDate();
	}
	
	public List<Patient> getPatients(Dataset dataset) throws Exception {
		
        Query q = getSession().createQuery(
                "select new net.sf.regadb.db.Patient(patient, max(access.permissions)) " +
                "from PatientImpl as patient " +
                "join patient.patientDatasets as patient_dataset " +
                "join patient_dataset.id.dataset as dataset " +
                "join dataset.datasetAccesses access " +
                "where dataset = :dataset " +
                "group by patient order by patient");
        q.setParameter("dataset", dataset);
      
        return (List<Patient>)q.list();
	}
	
    public Dataset getDataset(String description) {
        Query q = getSession().createQuery("from Dataset dataset where dataset.description = :description");
        q.setParameter("description", description);
        
        return (Dataset) q.uniqueResult();
    }
    
    private Session getSession() {
    	if (session == null) {
    		session = HibernateSessionFactoryUtil.getInstance().openSession();
    	}
    	return session;
    }
}
