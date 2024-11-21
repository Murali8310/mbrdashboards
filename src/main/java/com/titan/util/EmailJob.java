package com.titan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.titan.mbrDashboard.service.Userservice;


	public class EmailJob implements Job {

	   

		@Autowired
		Userservice userService;
		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
		    try {
		        String filePath = generateExcelContent();
		        String email_Id = "masinenikrishnasai@titan.co.in";
		        String user_Name = "Masineni Krishna Sai";
		        String login_id = "E1773945";

		        sendExcelReportToBuyer(email_Id, user_Name, login_id);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	 private String generateExcelContent() throws Exception {
 	        // Simulate generating Excel content and saving to a file
 	        String filePath = "Monthly_Report.xlsx";

 	        // Use Apache POI to create Excel content
 	        Workbook workbook = new XSSFWorkbook();
 	        Sheet sheet = workbook.createSheet("Sheet1");

 	        Row headerRow = sheet.createRow(0);
 	        headerRow.createCell(0).setCellValue("Item");
 	        headerRow.createCell(1).setCellValue("Quantity");

 	        Row dataRow = sheet.createRow(1);
 	        dataRow.createCell(0).setCellValue("Item A");
 	        dataRow.createCell(1).setCellValue(10);

 	        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
 	            workbook.write(outputStream);
 	        }

 	        return filePath;
 	    }
 	
 	
    
    /*
	 * Created By Krishna
	 * Created On 28-08-2023
	 * Consolidated report to buyer email after 7 days
	 */
	
	public void sendExcelReportToBuyer(String email_id, String user_Name, String login_id) {
	    try {
	    	
	    	Calendar cal = Calendar.getInstance();
			SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
			String yearfromCal = year_Date.format(cal.getTime());

			SimpleDateFormat month_Date = new SimpleDateFormat("MM");
			// String monthFromCal = month_Date.format(cal.getTime());
			String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

			SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
			String MonthText = month.format(cal.getTime());
		
	    	List<Object> BuyerList = userService.getBuyerIndentList(yearfromCal,MonthText );
	    	
	    	
	    	

	            // Create Excel workbook and worksheet
	            Workbook workbook = new XSSFWorkbook();
	            Sheet sheet = workbook.createSheet("DataTable");

	            // Populate Excel sheet with data from DataTable
	            for (int rowNum = 0; rowNum < BuyerList.size(); rowNum++) {
	                Row row = sheet.createRow(rowNum);
	                Object[] rowData = (Object[]) BuyerList.get(rowNum); // Assuming each row data is an Object array
	                for (int colNum = 0; colNum < rowData.length; colNum++) {
	                    Cell cell = row.createCell(colNum);
	                    cell.setCellValue(String.valueOf(rowData[colNum])); // Convert to String and set cell value
	                }
	            }
	            File excelFile = File.createTempFile("datatable", ".xlsx");
	            FileOutputStream fileOut = new FileOutputStream(excelFile);
	            workbook.write(fileOut);
	            fileOut.close();
	            
	            
	            
	            
	           // String smtpHostServer = "smtp-relay.gmail.com";
	            String smtpHostServer = "titan-co-in.mail.protection.outlook.com";
	           Properties props = System.getProperties();
	           props.put("mail.smtp.host", smtpHostServer);
	           props.put("mail.smtp.port", "25");
	            Session session = Session.getInstance(props, null);
	            System.out.println(session);
	            MimeMessage msg = new MimeMessage(session);

	           msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in", "No Reply-stationary Employee Portal"));
	          // msg.setFrom(new InternetAddress("nirajprasad@titan.co.in", "No Reply-stationary
	          // Employee Portal"));

	           msg.setReplyTo(InternetAddress.parse(email_id, false));
	            msg.setSubject("Indent Details from Buyer: text/HTML");
	           MimeMultipart multipart = new MimeMultipart();

	           //  Text content
	           MimeBodyPart textPart = new MimeBodyPart();
	           textPart.setText("Dear Buyer,\n\n" +"Greetings!\n" +
	        		   "Please find attached the Consolidated Report of indent for the month.\\n"+
	           
	           "\nIMPORTANT: Please do not reply to this message or mail address.\n\n" +
	            "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for a virus.\n\n\n" + "Thanks & Regards,\n" +
	            "Admin\n" +"Stationary Portal.");
	            multipart.addBodyPart(textPart);

	           // Excel attachment
 MimeBodyPart excelAttachment = new MimeBodyPart();
	           DataSource source = new FileDataSource(excelFile.getAbsolutePath());
	           excelAttachment.setDataHandler(new DataHandler(source));
	         excelAttachment.setFileName(excelFile.getName());
	           multipart.addBodyPart(excelAttachment);

	            // Set the content of the message
 msg.setContent(multipart);

msg.setSentDate(new Date());

	            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email_id, false));
	            //msg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(email_id, false));

	            Transport.send(msg);
				
	        System.out.println("Email sent successfully!");
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}  
	
	

}