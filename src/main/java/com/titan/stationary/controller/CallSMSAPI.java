package com.titan.stationary.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class CallSMSAPI {
	//public static void main(String[] args) {
		
	
	 public int sendsms(String key,int otpnum){
		 try {
			/*
			 * String recipient = "TITANS"; String message =
			 * " NPS Portal - Guest User - One-Time-Password-is-"+otpnum+""; String username
			 * = "titansonnet"; String password = "zTaYNiwm"; String originator =
			 * "91'"+key+"'";
			 */
			 
			 String recipient = "TITANS";
			// String message = "Welcome to stationary Employee Portal - Your One-Time-Password is - "+otpnum+" - By TITAN COMPANY LIMITED";
			 String message =" Shubh-Aarambh Showcase - Welcome to portal - Your One-Time-Password is-"+otpnum+"-TITAN COMPANY LIMITED";
			 String username = "titansonnet";
			 String password = "kap@user!123";
			 String originator = key;
			// System.out.println("hi"+ URLEncoder.encode(username, "UTF-8"));
			/*
			 * String requestUrl = "http://107.20.199.106/api/v3/sendsms/plain?" +
			 * 
			 * "user=" + URLEncoder.encode(username, "UTF-8") + "&password=" +
			 * URLEncoder.encode(password, "UTF-8") + "&sender=" +
			 * URLEncoder.encode(recipient, "UTF-8") + "&type=longsms" + "&SMSText=" +
			 * URLEncoder.encode(message, "UTF-8") + "&GSM=" + URLEncoder.encode(originator,
			 * "UTF-8") + "&type=longSMS" ; ;
			 */
			 
			 // Old commented by Thirumalesh
//				  String requestUrl = "https://www.smsjust.com/sms/user/urlsms.php?" +
//				  
//				  "username=" + URLEncoder.encode(username, "UTF-8") + "&pass=" +
//				  URLEncoder.encode(password, "UTF-8") + "&senderid=" +
//				  
//				  URLEncoder.encode(recipient, "UTF-8") + "&dest_mobileno="+URLEncoder.encode(originator, "UTF-8") + "&message=" +
//				  URLEncoder.encode(message, "UTF-8") +
//				//  "&GSM=" + URLEncoder.encode(originator,
//				//  "UTF-8") + 
//				  "&response=longSMS" ; 
				 

			 // url change by thirumalesh on 10-03-2021
			 
			  String requestUrl = "https://www.smsjust.com/sms/user/urlsms.php?" +
					  
			  "username=" + URLEncoder.encode(username, "UTF-8") + "&pass=" +
			  URLEncoder.encode(password, "UTF-8") + "&senderid=" +
			  
			  URLEncoder.encode(recipient, "UTF-8") + "&dest_mobileno="+URLEncoder.encode(originator, "UTF-8") + "&message=" +
			  URLEncoder.encode(message, "UTF-8") +
			//  "&GSM=" + URLEncoder.encode(originator,
			//  "UTF-8") + 
			  "&response=Y" ; 

			 
			 
///https://www.smsjust.com/sms/user/urlsms.php?username=titansonnet&pass=kap@user!123&
				//  senderid=TITANS&dest_mobileno=8050084748&message=TestMessage&response=Y
		//	  String requestUrl= "http://107.20.199.106/api/v3/sendsms/plain?user=titansonnet&password=zTaYNiwm&sender=TITANS&type=longsms&SMSText=NPS Portal - Guest User - One-Time-Password-is-474812&GSM=918867666062&type=longSMS";
			 URL url = new URL(requestUrl);
			 HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			 System.out.println(uc.getResponseMessage());
			 uc.disconnect();
			 } catch(Exception ex) {
			 System.out.println(ex.getMessage());
			 }
		return 0;
		  }
//	}
}
