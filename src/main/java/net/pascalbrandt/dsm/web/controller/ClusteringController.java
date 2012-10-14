package net.pascalbrandt.dsm.web.controller;

import java.util.Locale;
import java.util.Random;

import net.pascalbrandt.dsm.SVMService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import weka.classifiers.Evaluation;


public class ClusteringController implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(ClusteringController.class);
	private ApplicationContext context;

	@RequestMapping(value = "**/clustering", method = RequestMethod.GET)
	public String configure(Locale locale, Model model) {
		logger.info("In GET method");
		
		return "configuresvm";
	}
	
	
	//@RequestMapping(value = "**/clustering", method = RequestMethod.POST)
	/*public String run(Locale locale, Model model, @RequestParam("type") Integer type, 
			@RequestParam("kernel") Integer kernel, @RequestParam("gamma") Double gamma,
			@RequestParam("C") Double C) {
		logger.info("SVM Classification");
		
		SVMService ss = context.getBean(SVMService.class);
		
		//Evaluation eval = ss.classify(type, kernel, gamma, C);
		
		Evaluation CVeval = ss.crossValidate(type, kernel, gamma, C, 10, new Random((long) Math.random()));
		
		/
		model.addAttribute("evalSummary", eval.toSummaryString("", true));	
		
		try {
	        model.addAttribute("evalDetail", eval.toClassDetailsString());
        }
        catch (Exception e) {	
        	logger.error(e.getMessage());
        }
		
		model.addAttribute("evalConfusionMatrix", createConfusionMatrixString(eval.confusionMatrix()));
		
			
		model.addAttribute("CVSummary", CVeval.toSummaryString("", true));	
		
		try {
	        model.addAttribute("CVDetail", CVeval.toClassDetailsString());
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
        }
		
		model.addAttribute("CVConfusionMatrix", createConfusionMatrixString(CVeval.confusionMatrix()));
		
		
		return "displayresults";
	}
	*/
	
	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
    }
	
}
