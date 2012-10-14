package net.pascalbrandt.dsm.web.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.pascalbrandt.dsm.DataService;
import net.pascalbrandt.dsm.HibernateSessionFactoryUtil;
import net.pascalbrandt.dsm.RegaService;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController implements ApplicationContextAware {
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	private ApplicationContext context;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "**/dashboard", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		RegaService rs = context.getBean(RegaService.class);
		
		DataService ds = context.getBean(DataService.class);
		
		Map<String, Integer> labels = ds.getDatasetLabels(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR);
		
		try {
			model.addAttribute("ACResistant", labels.get(DataService.CLASS_RESISTANT));
			model.addAttribute("ACNotResistant", labels.get(DataService.CLASS_NOT_RESISTANT));
			model.addAttribute("ACUnlabeled", labels.get(DataService.CLASS_UNLABELED));
			
			model.addAttribute("patientCount", "~6000");//rs.getPatientCount());
		} catch (Exception e) {
			model.addAttribute("patientCount", "-1");
		}
			
		return "dashboard";
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
    }
}
