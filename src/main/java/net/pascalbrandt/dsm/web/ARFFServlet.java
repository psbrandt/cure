package net.pascalbrandt.dsm.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pascalbrandt.dsm.ApplicationContextProvider;
import net.pascalbrandt.dsm.SVMService;
import net.pascalbrandt.dsm.web.fbo.SVMConfigurationForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class ARFFServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory
			.getLogger(ARFFServlet.class);	

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			logger.info("In ARFF Servlet");
			
			response.setContentType("text/plain");
	
			SVMConfigurationForm config = (SVMConfigurationForm) request
					.getSession().getAttribute("config");
	
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_kk.mm.ss");
			String filename = "regadb_acfmc_" + df.format(new Date()) + ".arff";
	
			response.setHeader("Content-Disposition", "attachment;filename="
					+ filename);
			
			ApplicationContext context = ApplicationContextProvider.getApplicationContext();
	
			SVMService ss = context.getBean(SVMService.class);

			ServletOutputStream out = response.getOutputStream();

			out.write(ss.generateARFFFile(config).getBytes());

			out.flush();
			out.close();
		} catch (Exception e) {
			logger.info("Error generating ARFF File:" + e.getMessage());
			e.printStackTrace();
		}
		

	}

}