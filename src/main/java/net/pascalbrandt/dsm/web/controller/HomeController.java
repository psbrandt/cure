package net.pascalbrandt.dsm.web.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import net.pascalbrandt.dsm.HibernateSessionFactoryUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController implements ApplicationContextAware {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private ApplicationContext context;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
    }
	
}
