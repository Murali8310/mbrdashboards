package com.titan.stationary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.titan.stationary.bean.OTPMobileNumberBean;
import com.titan.stationary.bean.ValidateOtpBean;
import com.titan.stationary.controller.OtpController;
import com.titan.stationary.service.EmailService;
import com.titan.stationary.service.OtpService;
import com.titan.stationary.service.Userservice;
import com.titan.util.PasswordUtils;

@RestController
@CrossOrigin
public class OtpController {

	private Logger logger = LoggerFactory.getLogger(OtpController.class);

	@Autowired
	OtpService otpService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	CallSMSAPI  callSMSAPI;

	@Autowired(required=true)
	Userservice userService;
	
	

	//krishna
		@RequestMapping(value="forgotPassword", method=RequestMethod.GET)
		public ModelAndView forgotPassword(HttpServletRequest request, HttpServletResponse response,String login_id, String email, Model model, RedirectAttributes redirect)
		{
		
			HttpSession session = request.getSession();
			
			session.removeAttribute("MESSAGE");
			return new ModelAndView("password/forgotPassword");
		}
		@RequestMapping(value = "sendOTPGet", method = RequestMethod.GET)
		public ModelAndView sendOTP(HttpServletRequest request, HttpServletResponse response,
				RedirectAttributes redirect, Model model) {
			
			/*HttpSession session = request.getSession();
			String login_id =(String) session.getAttribute("login_id");
			model.addAttribute("login_id", login_id);
			System.out.println("login_id : "+login_id);*/
			
			return new ModelAndView("password/confirmOTP");
		}

		  @RequestMapping(value = "validateOTP", method = RequestMethod.GET) public
		  ModelAndView validateOtp(HttpServletRequest request, HttpServletResponse
		  response ,String email,RedirectAttributes redirect) {
		  
		  return new ModelAndView("password/confirmOTP"); }
		 
		
		 
		  @RequestMapping(value = "sendOTP", method = RequestMethod.POST)
		  public ModelAndView sendOTP(HttpServletRequest request, HttpServletResponse response, String login_id, String email, Model model, RedirectAttributes redirect) {

		      HttpSession session = request.getSession();
		      session.setAttribute("login_id", login_id);

		      try {
		          String email_Id = userService.getEmailIdByloginIdn(login_id);

		          if (email_Id != null && email.equalsIgnoreCase(email_Id)) {
		              int otp = otpService.generateOTP(login_id);
		              System.out.println("OTP: " + otp);
		              emailService.sendOtpMessage(email, "" + otp + " is the OTP on Statinery Portal for Forgot Password", String.valueOf(otp));
		              return new ModelAndView("redirect:sendOTPGet");
		          } else if (email_Id == null) {
		              redirect.addFlashAttribute("LOGINID_NOT_FOUND_MESSAGE", "Login ID not found in the system");
		          } else {
		              redirect.addFlashAttribute("LOGINID_EMAILID_MISMATCH", "Login ID does not match with the provided email ID");
		          }

		      } catch (IllegalArgumentException e) {
		          redirect.addFlashAttribute("LOGINID_NOT_FOUND_MESSAGE", "Login ID not found in the system");
		      }

		      // If both login ID and email are not found, show a combined message
		      if (redirect.getFlashAttributes().isEmpty()) {
		          redirect.addFlashAttribute("LOGINID_EMAILID_NOT_FOUND", "Login ID and email not found in the system");
		      }

		      return new ModelAndView("redirect:forgotPassword");
		  }



		
		@PostMapping(value = "createNewPassword")
		public ModelAndView createNewPassword(@RequestParam String newPwd,@RequestParam String conPwd,
				@RequestParam String login_id,RedirectAttributes redirect) {
			try {
				if(!newPwd.equals(conPwd)) {
					redirect.addFlashAttribute("PASSWORD_MISSMATCH","New Password and Confirm Password should be same.");
					return new ModelAndView("redirect:newPassword");
				}
				if (!isValidPassword(newPwd)) {
	                redirect.addFlashAttribute("ERROR_MESSAGE", "New Password does not meet complexity requirements.");
	                return new ModelAndView("redirect:/password/newPassword");
	            }

				PasswordUtils utils=new PasswordUtils();
				String encrypTedPwd=utils.encrypt(newPwd);
				int count=userService.updatePassword(encrypTedPwd, conPwd,login_id);
				
				logger.info("count Updated Password"+count);
				if(count>0) {
					redirect.addFlashAttribute("PASSWORD_CREATE_SUCCESS","New Password Created Successfully..");
					return new ModelAndView("redirect:login");
				}else {
					redirect.addFlashAttribute("PASSWORD_CREATE_FAILED","Failed to create new password");
					return new ModelAndView("redirect:forgotPassword");
				}
			}catch(Exception e) {
				redirect.addFlashAttribute("PASSWORD_CREATION_FAILED","Failed to create new password");
				return new ModelAndView("redirect:forgotPassword");
			}
		}
		@RequestMapping(value = "confirmOTP", method = RequestMethod.POST)
		public ModelAndView validateOtp(@RequestParam String otp,@RequestParam String login_id, Model model,
				RedirectAttributes redirect) {
			try {
				
			if(Integer.parseInt(otp) > 0){
				int serverOtp = otpService.getOtp(login_id);
				if(serverOtp > 0){
					if(Integer.parseInt(otp) == serverOtp){
						otpService.clearOTP(login_id);
						redirect.addFlashAttribute("SUCCESS","OTP is Validated Successfully.");
						return new ModelAndView("redirect:newPassword");
					}else {
						redirect.addFlashAttribute("INVALIDOTP","Invalid OTP");
						return new ModelAndView("redirect:validateOTP");
					}
				}else {
					redirect.addFlashAttribute("OTP_EXPIRED","Your OTP has Expired, kindly click on Forgot Password again");
					return new ModelAndView("redirect:login");
				}
			}else {
				redirect.addFlashAttribute("INVALIDOTP","Invalid OTP");
				return new ModelAndView("redirect:validateOTP");
			}}
			catch(NumberFormatException  ex) {
				redirect.addFlashAttribute("INVALIDOTP","Invalid OTP");
				return new ModelAndView("redirect:validateOTP");
			}
			
		}
			
			
		
		@RequestMapping(value = "newPassword", method = RequestMethod.GET)
		public ModelAndView newPassword(HttpServletRequest request, HttpServletResponse response,
				RedirectAttributes redirect) {
			
			return new ModelAndView("password/newPassword");
		}
		
private boolean isValidPassword(String newPwd) {
	        
	        return newPwd.length() >= 8
	                && newPwd.matches(".*[A-Z].*")
	                && newPwd.matches(".*[0-9].*")
	                && newPwd.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
	    }

		
			
		

}
