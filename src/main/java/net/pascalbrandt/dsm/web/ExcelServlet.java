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
import net.pascalbrandt.dsm.DataService;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.web.fbo.SVMConfigurationForm;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class ExcelServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory
			.getLogger(ExcelServlet.class);	
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			logger.info("In Excel Servlet");
			
			response.setContentType("'application/vnd.ms-excel'");
	
			SVMConfigurationForm config = (SVMConfigurationForm) request
					.getSession().getAttribute("config");
	
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_kk.mm.ss");
			String filename = "regadb_acfmc_" + df.format(new Date()) + ".xlsx";
	
			response.setHeader("Content-Disposition", "attachment;filename="
					+ filename);
			
			ApplicationContext context = ApplicationContextProvider.getApplicationContext();
	
			DataService ds = context.getBean(DataService.class);

			ServletOutputStream out = response.getOutputStream();

			XSSFWorkbook workbook = ds.excelExport(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR, config);
			
			workbook.write(out);

			out.flush();
			out.close();
		} catch (Exception e) {
			logger.info("Error generating Excel File:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
