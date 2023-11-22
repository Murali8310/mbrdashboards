package com.titan.stationary.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.titan.stationary.bean.smUserMasterBean;

@Service
public class EmailService {

	//@Autowired
	//private MailSender  javaMailSender;


	public void sendStoreManagerMessage(smUserMasterBean sMUserMasterBean, String loginID) {

		
		try {
			String smtpHostServer = "smtp-relay.gmail.com";
			String emailID = sMUserMasterBean.getEmail();
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			Session session = Session.getInstance(props, null);
			System.out.println(session);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in", "No Reply-stationary Employee Portal"));
			msg.setReplyTo(InternetAddress.parse(sMUserMasterBean.getEmail(), false));
			msg.setSubject("Store Manager : "+sMUserMasterBean.getEmpName()+" is Created", "text/HTML");
			msg.setContent("Dear Sir/Madam, \n\n"
					+ "Greetings!\n"
					+ "Your login is created in stationary employee Portal.\n"
					+"Credenetails are created by " + loginID
					+"\nLogin ID : "+sMUserMasterBean.getEmail()
					+ "\nIMPORTANT: Please do not reply to this message or mail address.\n\n"
					+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should \n"
					+ "not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient \n"
					+ "acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not \n"
					+ "warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.\n\n\n"
					+ "Thanks & Regards, \n"
					+ "Admin \nstationary Employee Portal.","text/plain");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID, false));
			Transport.send(msg);  
		} catch (Exception e){
			e.printStackTrace();
		}
	}




	/*public void sendOtpMessage(String to, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); 
		simpleMailMessage.setTo(to); 
		simpleMailMessage.setSubject(subject); 
		simpleMailMessage.setText("Dear Sir/Madam, \n\n"
				+ "As you have requested for OTP on Watches Iscm Portal for forgot password , "
				+ "your OTP is "+message+"."
				+ "This OTP is valid for only 5 min. Do not share the OTP, or any other password with anyone. \n\n"
				+ "IMPORTANT: Please do not reply to this message or mail address.\n\n"
				+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should \n"
				+ "not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient \n"
				+ "acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not \n"
				+ "warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.\n\n\n"
				+ "Thanks & Regards, \n"
				+ "Titan Company Pvt Ltd.");

		javaMailSender.send(simpleMailMessage);
	}*/

	/*public void sendPasswordSuccessMessage(String to, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); 
		simpleMailMessage.setTo(to); 
		simpleMailMessage.setSubject(subject); 
		simpleMailMessage.setText("Dear Sir/Madam, \n\n"
				+"Your Updated Password on Watches Iscm Portal is "+message+". \n\n"
				+ "IMPORTANT: Please do not reply to this message or mail address.\n\n"
				+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should \n"
				+ "not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient \n"
				+ "acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not \n"
				+ "warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.\n\n\n"
				+ "Thanks & Regards, \n"
				+ "Titan Company Pvt Ltd.");

		javaMailSender.send(simpleMailMessage);
	}	 
	 */
	public void sendPasswordSuccessMessage(String tomail, String subject, String message) {
		try {
			String smtpHostServer = "smtp-relay.gmail.com";
			String emailID = tomail;
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			Session session = Session.getInstance(props, null);
			System.out.println(session);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("noreply_techportal@titan.co.in", "No Reply-Titan Care TechPortal"));
			msg.setReplyTo(InternetAddress.parse(tomail, false));
			msg.setSubject(subject, "text/HTML");
			//msg.setSubject(subject, "text/HTML");
			msg.setContent("Dear Sir/Madam, \n\n"
					+"Your Updated Password on Titan Care Tech Portal is "+message+". \n\n"
					+ "IMPORTANT: Please do not reply to this message or mail address.\n\n"
					+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should \n"
					+ "not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient \n"
					+ "acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not \n"
					+ "warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.\n\n\n"
					+ "Thanks & Regards, \n"
					+ "Admin \nTitan Care TechPortal.","text/plain");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID, false));
			Transport.send(msg);  
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public void sendmessagetoreward(String tomail, String subject, String message,String serial,String feedbacktype,String uname) {
		try {
			String smtpHostServer = "smtp-relay.gmail.com";
			String emailID = tomail;
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			Session session = Session.getInstance(props, null);
			System.out.println(session);
			MimeMessage msg = new MimeMessage(session);
//			msg.setFrom(new InternetAddress("noreply_watchesiscmportal@titan.co.in", "No Reply-WatchesIscmPortal"));
			msg.setFrom(new InternetAddress("noreply_techportal@titan.co.in", "No Reply-Titan Care TechPortal"));
			msg.setReplyTo(InternetAddress.parse(tomail, false));
			msg.setSubject(subject, "text/HTML");
			msg.setContent("Dear "+uname+", \n"
					+"Thank you for sharing your feedback/s through TCTP.\n"
					+"Please take a note on the below reason/s, as your input was not considered.\n\n"
					+feedbacktype+" serial number : "+serial+" \n"
					+"Reason : "+message+" \n\n"
					
					
					
					+ "IMPORTANT: Please do not reply to this message or mail address.\n"
					+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should \n"
					+ "not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient \n"
					+ "acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not \n"
					+ "warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.\n\n\n"
					+ "Thanks & Regards, \n"
					+ "CSF Team \nTitan Care TechPortal.","text/plain");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID, false));
			Transport.send(msg);  
		} catch (Exception e){
			e.printStackTrace();
		}
	}

//krishna
	public void sendOtpMessage(String tomail,String subject, String message) {

		try {
			String smtpHostServer = "smtp-relay.gmail.com";
			String emailID = tomail;
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			Session session = Session.getInstance(props, null);
			System.out.println(session);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("noreply_stationery@titan.co.in", "No Reply-Stationery Employee Portal"));
			msg.setReplyTo(InternetAddress.parse(tomail, false));
			msg.setSubject(subject, "text/HTML");
			msg.setContent("Dear Sir/Madam, \n\n"
					+ "As you have requested for OTP on Stationery Employee Portal for forgot password , "
					+ "your OTP is "+message+"."
					+ "This OTP is valid for only 5 min. Do not share the OTP, or any other password with anyone. \n\n"
					+ "IMPORTANT: Please do not reply to this message or mail address.\n\n"
					+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should \n"
					+ "not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient \n"
					+ "acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not \n"
					+ "warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.\n\n\n"
					+ "Thanks & Regards, \n"
					+ "Admin \nStationery Employee Portal.","text/plain");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID, false));
			Transport.send(msg);  
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}









