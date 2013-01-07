package net.pascalbrandt.dsm.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pascalbrandt.dsm.AttributeFactory;
import net.pascalbrandt.dsm.RegaService;
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
        model.addAttribute("demographicAttributeList",
                AttributeFactory.getDemographicAttributeNames());
        model.addAttribute("clinicalAttributeList", AttributeFactory.getClinicalAttributeNames());
        model.addAttribute("adherenceAttributeList", AttributeFactory.getAdherenceAttributeNames());
        model.addAttribute("otherAttributeList", AttributeFactory.getOtherAttributeNames());
        return "configuresvm";
    }

    @RequestMapping(value = "**/classification", method = RequestMethod.POST, params = "submitButton=ARFF")
    public void generateARFFFile(HttpServletRequest request, HttpServletResponse response, Model model,
            @ModelAttribute("config") SVMConfigurationForm config) {
        logger.info("Generating ARFF File");

        /*
        response.setContentType("text/plain");
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_kk.mm.ss");
        String filename = "regadb_acfmc_" + df.format(new Date()) + ".arff";
        
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        SVMService ss = context.getBean(SVMService.class);
        
        try {
            // FileInputStream fileIn = new FileInputStream(file);
            //FileWriter fw = new FileWriter(file);
            ServletOutputStream out = response.getOutputStream();

           // for (int i = 0; i < 10; i++) {
           //     out.write(Integer.toString(i).getBytes());
           // }
            
           out.write(ss.generateARFFFile(config).getBytes());
            
           // out.write(file.)
            
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info("Error generating ARFF File:" + e.getMessage());
        }
        */
        
        request.getSession().setAttribute("config", config);
        
        try {
			response.sendRedirect("/dsm/arff");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       // return "home";
    }
    
    @RequestMapping(value = "**/classification", method = RequestMethod.POST, params = "submitButton=Excel")
    public void generateExcelFile(HttpServletRequest request, HttpServletResponse response, Model model,
            @ModelAttribute("config") SVMConfigurationForm config) {
        logger.info("Generating Excel File");

        request.getSession().setAttribute("config", config);
        
        try {
			response.sendRedirect("/dsm/excel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @RequestMapping(value = "**/classification", method = RequestMethod.POST, params = "submitButton=Classify")
    public String run(Model model, @ModelAttribute("config") SVMConfigurationForm config) {
        logger.info("SVM Classification");

        SVMService ss = context.getBean(SVMService.class);

        // session.getServletContext().

        // Evaluation eval = ss.classify(type, kernel, gamma, C);

        Evaluation CVeval = ss//.evaluateClassifier(config);
                .crossValidateClassifier(config, 10, new Random((long) Math.random()));

        // SVMConfigurationForm conf = (SVMConfigurationForm)model.

        logger.info("type: " + config.getType());
        logger.info("kernel: " + config.getKernel());
        logger.info("gamma: " + config.getGamma());
        logger.info("C: " + config.getC());
        logger.info("Attrs" + config.getSelectedDemographicAttributes().length);

        // Evaluation eval = ss.evaluateClassifier(config);

        // model.addAttribute("evalSummary", eval.toSummaryString("", true));

        /*
         * try { model.addAttribute("evalDetail", eval.toClassDetailsString());
         * } catch (Exception e) { logger.error(e.getMessage()); }
         * 
         * model.addAttribute("evalConfusionMatrix",
         * createConfusionMatrixString(eval.confusionMatrix()));
         */

        model.addAttribute("CVSummary", CVeval.toSummaryString("", true));

        try {
            model.addAttribute("CVDetail", CVeval.toClassDetailsString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        model.addAttribute("CVConfusionMatrix",
                createConfusionMatrixString(CVeval.confusionMatrix()));

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
