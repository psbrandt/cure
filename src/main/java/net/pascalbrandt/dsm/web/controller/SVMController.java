package net.pascalbrandt.dsm.web.controller;

import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpSession;

import net.pascalbrandt.dsm.AttributeFactory;
import net.pascalbrandt.dsm.SVMService;
import net.pascalbrandt.dsm.web.fbo.SVMConfigurationForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import weka.classifiers.Evaluation;

@Controller
public class SVMController implements ApplicationContextAware {
	
	private static final Logger logger = LoggerFactory.getLogger(SVMController.class);
	private ApplicationContext context;
	
	@RequestMapping(value = "**/classification", method = RequestMethod.GET)
	public String configure(Locale locale, Model model) {
		logger.info("SVM Configuration");
		
		SVMService ss = context.getBean(SVMService.class);
		
		// Add the fbo
		model.addAttribute("config", new SVMConfigurationForm());
		
		// Add the reference data
		model.addAttribute("svmTypes", ss.getSVMTypes());
		model.addAttribute("kernelTypes", ss.getKernelTypes());
		model.addAttribute("demographicAttributeList", AttributeFactory.getDemographicAttributeNames());
		model.addAttribute("clinicalAttributeList", AttributeFactory.getClinicalAttributeNames());
		model.addAttribute("adherenceAttributeList", AttributeFactory.getAdherenceAttributeNames());
		model.addAttribute("otherAttributeList", AttributeFactory.getOtherAttributeNames());
		return "configuresvm";
	}
	
	@RequestMapping(value = "**/classification", method = RequestMethod.POST)
	public String run(Model model, @ModelAttribute("config") SVMConfigurationForm config) {
		logger.info("SVM Classification");
		
		SVMService ss = context.getBean(SVMService.class);
		
		//session.getServletContext().
		
		//Evaluation eval = ss.classify(type, kernel, gamma, C);
		
		Evaluation CVeval = ss.crossValidateClassifier(config, 10, new Random((long) Math.random()));
		
		//SVMConfigurationForm conf = (SVMConfigurationForm)model.
		
		logger.info("type: " + config.getType());
		logger.info("kernel: " + config.getKernel());
		logger.info("gamma: " + config.getGamma());
		logger.info("C: " + config.getC());
		logger.info("Attrs" + config.getSelectedDemographicAttributes().length);
		
		//Evaluation eval = ss.evaluateClassifier(config);
		
		
		//model.addAttribute("evalSummary", eval.toSummaryString("", true));	
		
		/*
		try {
	        model.addAttribute("evalDetail", eval.toClassDetailsString());
        }
        catch (Exception e) {	
        	logger.error(e.getMessage());
        }
		
		model.addAttribute("evalConfusionMatrix", createConfusionMatrixString(eval.confusionMatrix()));
		*/	
			
		
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
	
	public String createConfusionMatrixString(double[][] confusionMatrix) {
		StringBuffer cf = new StringBuffer();
		
		for (int i = 0; i < confusionMatrix.length; i++) {
			for (int j = 0; j < confusionMatrix[i].length; j++) {
				cf.append(confusionMatrix[i][j] + " ");
			}
			cf.append("<br>");
		}
		
		return cf.toString();
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
    }
}
