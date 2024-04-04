package com.titan.stationary.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.titan.cro.security.AuthenticationService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.titan.stationary.bean.BuyerIndentBean;
import com.titan.stationary.bean.IndentMasterBean;
import com.titan.stationary.bean.Product;
import com.titan.stationary.bean.UserLoginBean;
import com.titan.stationary.bean.smUserMasterBean;
import com.titan.stationary.service.Userservice;
import com.titan.util.AesUtil;
import com.titan.util.PasswordUtils;

@CrossOrigin(origins = "https://stationary.titan.in", allowedHeaders = "*")

@RestController
public class UserController {

	Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	Userservice userService;

	/*
	 * @Autowired private AuthenticationService authenticationService;
	 */

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model) {
		/*
		 * if (cookie != null) { // Expire the cookie by setting its max age to 0
		 * cookie.setMaxAge(0); // Add the updated cookie to the response
		 * response.addCookie(cookie);
		 * 
		 * } else { }
		 */
		
		/*
		 * HttpSession oldSession = request.getSession(false); // Get the current
		 * session without creating a new one if (oldSession != null) {
		 * oldSession.invalidate(); // Invalidate the old session } HttpSession session
		 * = request.getSession(false); if (session!= null && !session.isNew()) {
		 * session.invalidate(); }
		 */
		String randomKey = UUID.randomUUID().toString();
		// String uniqueKey = "1234567891234567";
		String uniqueKey = randomKey.substring(randomKey.length() - 17, randomKey.length() - 1);

		// System.out.println("password 2 : " + uniqueKey);
		request.getSession().setAttribute("key", uniqueKey);
		return new ModelAndView("login/login");
	}

	@RequestMapping(value = "franchiselogin", method = RequestMethod.GET)
	public ModelAndView franchise(HttpServletRequest request, HttpServletResponse response, Model model) {

		return new ModelAndView("login/offlogin");
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView newLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		String randomKey = UUID.randomUUID().toString();
		// String uniqueKey = "1234567891234567";
		
		HttpSession oldSession = request.getSession(false); // Get the old session without creating a new one
		if (oldSession != null) {
		    oldSession.invalidate();
		}
//		response.addCookie(null);
//		HttpSession session = request.getSession();
        HttpSession newSession = request.getSession(true);
		String uniqueKey = randomKey.substring(randomKey.length() - 17, randomKey.length() - 1);

		System.out.println("password 2iiiiiiiiiiiiiiii :  " + uniqueKey);
		newSession.setAttribute("key", uniqueKey);
		return new ModelAndView("login/login");
	}

	@RequestMapping(value = "landPage", method = RequestMethod.GET)
	public ModelAndView landpage(HttpServletRequest request, HttpServletResponse response, Model model) {
		/*
		 * HttpSession oldSession = request.getSession(false); // Get the old session
		 * without creating a new one if (oldSession != null) { oldSession.invalidate();
		 * }
		 */
		HttpSession session = request.getSession();
		try {
			String user = (String) session.getAttribute("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		return new ModelAndView("login/landPage");
	}

	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String userRole = null;
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			userRole = (String) userMap.get("role");
			String user = (String) session.getAttribute("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		model.addAttribute("userRole",userRole);
		return new ModelAndView("login/Dashboard");
	}

	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirect) {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);

		HttpSession session = request.getSession();

		String login_id = (String) session.getAttribute("login_id");
		int status = userService.updateLogouTIme(login_id);
		session.invalidate();
		session = request.getSession(false);
		redirect.addFlashAttribute("MESSAGE", "Logged Out Successfully.");
		return new ModelAndView("redirect:login");
	}

	/**
	 * Check login type if ABM and Admin, do AD chekc in controller if Store manager
	 * do normal login in daoimpl success login get menu items.
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userLoginBean
	 * @param redirect
	 * @param loginId
	 * @param user_selection
	 * @return
	 */
	@RequestMapping(value = "loginSubmit", method = RequestMethod.POST)
	public String loginSubmit(HttpServletRequest request, HttpServletResponse response, Model model,
			UserLoginBean userLoginBean, RedirectAttributes redirect) {
		HttpSession session = request.getSession();
		String value = (String) session.getAttribute("key");

		String passwords = "";
		String decryptedPassword = new String(java.util.Base64.getDecoder().decode(userLoginBean.getPassword()));
		AesUtil aesUtil = new AesUtil(128, 1000);
		Map map = new HashMap<>();
		if (decryptedPassword != null && decryptedPassword.split("::").length == 3) {
			passwords = aesUtil.decrypt(decryptedPassword.split("::")[1], decryptedPassword.split("::")[0], value,
					decryptedPassword.split("::")[2]);

		}
		model.addAttribute("login_id", userLoginBean.getLogin_id());
		Map<String, Object> userMap = null;

		userMap = (Map<String, Object>) userService.findloginuser(userLoginBean, passwords);
		
		userLoginBean.setLogin_id(userLoginBean.getLogin_id());

		if (userLoginBean.getLogin_id() == null) {
			System.out.println("userLoginBean.getLogin_id()"+userLoginBean.getLogin_id());
			return "login";
		}
		if (userMap.get("message").equals("SUCCESS")) {

			session = request.getSession(true);
			session.setAttribute("loginBean", userLoginBean);
			session.setAttribute("userMap", userMap);
			session.setAttribute("user_id", userMap.get("user_id"));
			session.setAttribute("user_Name", userMap.get("user_Name"));
			session.setAttribute("email_id", userMap.get("email_id"));
			session.setAttribute("login_id", userMap.get("login_id"));
			session.setAttribute("role", userMap.get("role"));
			session.setAttribute("storeCode", userMap.get("storeCode"));
			session.setAttribute("accessRole", userMap.get("accessRole"));
			// session.setAttribute("StoressSM", userMap.get("StoressSM"));
			// session.setAttribute("region", userMap.get("region"));

			return "landPage";
		} else {
			// redirect.addFlashAttribute("MESSAGE", userMap.get("message"));

			session.setAttribute("MESSAGE", userMap.get("message"));
			// return new ModelAndView("redirect:login");
			return "login";
		}
	}

	@RequestMapping(value = "manageByAdmin", method = RequestMethod.GET)
	public ModelAndView manageByAdmin(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
			 LocalDateTime now = LocalDateTime.now();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			 String formattedDateTime = now.format(formatter);
			 String getIndentorloginStatus = null;
			List<String> seventhDay = userService.get7thworkingDay();
			System.out.println("seventhDay : "+seventhDay.get(0) );
			System.out.println("Current time is ::: " + formattedDateTime);
			if (seventhDay != null && !seventhDay.isEmpty() && seventhDay.get(0).equals(formattedDateTime)) {
				//userService.sevenDayMailTrigger();
	            System.out.println("Proceeding further...");
	            getIndentorloginStatus = "blockLogin";
	        } else {
	        	 getIndentorloginStatus = "allowLogin";
	        }
			model.addAttribute("getIndentorloginStatus", getIndentorloginStatus);
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}

		return new ModelAndView("admin/manageByAdmin");
	}

	@RequestMapping(value = "usermanagementupload", method = RequestMethod.GET)
	public ModelAndView usermanagementupload(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			String user = (String) session.getAttribute("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}

		return new ModelAndView("admin/useruploadmanagement");
	}

	@RequestMapping(value = "searchbyuser", method = RequestMethod.GET)
	public String searchbyuser(@RequestParam("firstName") String firstName) {
		List<Object> users = userService.usersearch(firstName);
		System.out.println("productQualityfeedbackList : " + users);
		Gson gson = new Gson();
		String user = gson.toJson(users);

		return user;
	}

	/*
	 * String searchbyusers(String firstName) { List<Object[]> users =
	 * userService.usersearchs(firstName);
	 * System.out.println("productQualityfeedbackList : " + users); Gson gson = new
	 * GsonBuilder().setPrettyPrinting().create(); String mapToJsonObject =
	 * gson.toJson(users); return mapToJsonObject; }
	 */

	@RequestMapping(value = "searchbyusers", method = RequestMethod.GET)
	public String searchbyusers(String firstName, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<String[]> searchbyusers = userService.usersearchs(firstName);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(searchbyusers);
		// System.out.print("234" + mapJsonObject);
		return mapJsonObject;

	}

	@GetMapping(value = "downloadABMFormatFile")
	public ResponseEntity<ByteArrayResource> downloadRewardFormateFile(Model model) throws IOException {

		String BULK_USERS_MASTER_FILE_ROOT = "D:/FORMATE_FILE/";
		String BULK_USERS_MASTER_FILE_NAME = "ABMFormat.xlsx";

		String fileDirectory = BULK_USERS_MASTER_FILE_ROOT + BULK_USERS_MASTER_FILE_NAME;
		Path path = Paths.get(fileDirectory);
		byte[] bytesData = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(bytesData);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytesData.length).body(resource);

	}

	@GetMapping(value = "downloadStoreCreationFormatFile")
	public ResponseEntity<ByteArrayResource> downloadStoreCreationFormatFile(Model model) throws IOException {

		String BULK_USERS_MASTER_FILE_ROOT = "D:/FORMATE_FILE/";
		String BULK_USERS_MASTER_FILE_NAME = "StoreCreationFormat.xlsx";

		String fileDirectory = BULK_USERS_MASTER_FILE_ROOT + BULK_USERS_MASTER_FILE_NAME;
		Path path = Paths.get(fileDirectory);
		byte[] bytesData = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(bytesData);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytesData.length).body(resource);

	}

	@GetMapping(value = "downloadStoreManagerFormatFile")
	public ResponseEntity<ByteArrayResource> downloadStoreManagerFormatFile(Model model) throws IOException {

		String BULK_USERS_MASTER_FILE_ROOT = "D:/FORMATE_FILE/";
		String BULK_USERS_MASTER_FILE_NAME = "StoreManagerFormat.xlsx";

		String fileDirectory = BULK_USERS_MASTER_FILE_ROOT + BULK_USERS_MASTER_FILE_NAME;
		Path path = Paths.get(fileDirectory);
		byte[] bytesData = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(bytesData);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytesData.length).body(resource);

	}

	@GetMapping(value = "downloadStoreStaffFormatFile")
	public ResponseEntity<ByteArrayResource> downloadStoreStaffFormatFile(Model model) throws IOException {

		String BULK_USERS_MASTER_FILE_ROOT = "D:/FORMATE_FILE/";
		String BULK_USERS_MASTER_FILE_NAME = "StoreStaffFormat.xlsx";

		String fileDirectory = BULK_USERS_MASTER_FILE_ROOT + BULK_USERS_MASTER_FILE_NAME;
		Path path = Paths.get(fileDirectory);
		byte[] bytesData = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(bytesData);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytesData.length).body(resource);

	}

	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response, Model model,
			RedirectAttributes redirect) {

		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) request.getSession().getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}

		return new ModelAndView("password/changePassword");
	}

	@PostMapping(value = "changePassword")
	public ModelAndView changePwd(@RequestParam String login_id, @RequestParam String oldPwd,
			@RequestParam String newPwd, @RequestParam String conPwd, HttpServletRequest request,
			RedirectAttributes redirect) throws Exception {
		try {
			HttpSession session = request.getSession(false);
			String username = (String) session.getAttribute("login_id");
			String pwdDb = userService.getPasswordByloginId(login_id);
			logger.info("Password from DB :" + pwdDb);
			PasswordUtils utils = new PasswordUtils();
			String decryptedPwd = utils.decrypt(pwdDb);
			System.out.println("decryptedPwd:" + decryptedPwd);
			if (!login_id.equals(username)) {
				redirect.addFlashAttribute("WRONG_USER", "Please enter correct User");
				return new ModelAndView("redirect:changePassword");
			}

			if (oldPwd.equalsIgnoreCase(newPwd)) {
				redirect.addFlashAttribute("PASSWORD_WRONG", "Current password and new password should be different.");
				return new ModelAndView("redirect:changePassword");
			}
			if (oldPwd.equalsIgnoreCase(decryptedPwd)) {
				String encryptedPwd = utils.encrypt(newPwd);
				System.out.println(encryptedPwd);
				if (!newPwd.equals(conPwd)) {
					redirect.addFlashAttribute("NEW_PASSWORD_WRONG",
							"New password and confirm password should be same.");
					return new ModelAndView("redirect:changePassword");
				}

				int count = userService.updatePassword(encryptedPwd, conPwd, login_id);
				if (count < 1) {
					System.out.println("Failed to update password");
					redirect.addFlashAttribute("PASSWORD_CHANGE_FAILED", "Failed to change password");
					return new ModelAndView("redirect:changePassword");
				} else {
					System.out.println("Successfully updated password");
					redirect.addFlashAttribute("PASSWORD_CREATE_SUCCESS", "Password Changed Successfully.");
					return new ModelAndView("redirect:login");
				}
			} else {
				System.out.println("Password does not match");
				redirect.addFlashAttribute("PASSWORD_MISSMATCH", "Confirm Password and New Password should be match");
				return new ModelAndView("redirect:changePassword");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * Product catelogue land page
	 */
	@RequestMapping(value = "productCatelogue", method = RequestMethod.GET)
	public ModelAndView productCatelogue(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			String user = (String) session.getAttribute("login_id");
			List<String> AllProducts;
			List<String> CategoryList;
			List<Object> budegetDetails;
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
			String yearfromCal = year_Date.format(cal.getTime());
			
			//SimpleDateFormat month_Date = new SimpleDateFormat("MM");
			//String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));
			
			SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
			String MonthText = month.format(cal.getTime());
			
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//String formattedDate = dateFormat.format(cal.getTime());
			
			//int cFY = Integer.valueOf(yearfromCal);
			
			String str = userService.checkIndentForMonth(yearfromCal, MonthText, user);
			if(!str.equalsIgnoreCase("fine"))
			{
				model.addAttribute("message", str);
				return new ModelAndView("admin/manageByAdmin");
			}
			AllProducts = userService.getAllProducts(user);
			CategoryList = userService.getCategoryList();
			System.out.println("password 2 :  " + AllProducts);
			System.out.println("CategoryList :  " + CategoryList);
		//	model.addAttribute("budegetDetails", budegetDetails);
			model.addAttribute("AllProducts", AllProducts);
			model.addAttribute("CategoryList", CategoryList);

		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		return new ModelAndView("admin/productlist");
	}

	/**
	 * @author Masineni Krishna Sai- 22-06-2023
	 * 
	 *
	 */
	@RequestMapping(value = "UserCreation", method = RequestMethod.GET)
	public ModelAndView UserCreation(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			HttpSession session = request.getSession();
			String loginId = (String) session.getAttribute("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		

		return new ModelAndView("admin/UserCreation");
	}

	@RequestMapping(value = "UserCreation", method = RequestMethod.POST)
	public String UserCreation(HttpServletRequest request, @RequestParam("empid") String empid,
			@RequestParam("empname") String empname, @RequestParam("designation") String designation,
			@RequestParam("city") String city, @RequestParam("email") String email,

			@RequestParam("mobile") String mobile) {

		HttpSession session = request.getSession();
		Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		String loginId = (String) userMap.get("login_id");
		String generatehelioscopon = userService.userCreationByForm();
		
		Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
		String jsonData = jsonToString.toJson(generatehelioscopon);
		return jsonData;
	}

	@RequestMapping(value = "GetAllProducts", method = RequestMethod.GET)
	public String GetAllProducts(HttpServletRequest request, HttpServletResponse responsel) {
		HttpSession session = request.getSession();
		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<String> storeStaffList = userService.getAllProducts(loginId);
		// List<Object[]> stores = userService.getAllstores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(storeStaffList);
		// System.out.print("234" + mapJsonObject);
		return mapJsonObject;

	}

	@RequestMapping(value = "budgetmaster", method = RequestMethod.GET)
	public ModelAndView budgetmaster(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			HttpSession session = request.getSession();
			String loginId = (String) session.getAttribute("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}

		List<Object> designation = userService.getAllCCIDDetails();
		
		model.addAttribute("designation", designation);
		
		
		  List<Object> Year = userService.getAllyearDetails();
		  
		  model.addAttribute("years", Year);
		

		return new ModelAndView("admin/budgetmaster");
	}

	@RequestMapping(value = "getProductByCategory", method = RequestMethod.GET)
	public String GetAllProducts(HttpServletRequest request, HttpServletResponse responsel,
			@RequestParam("categoryList") String categoryList) {

		System.out.println("categoryArray" + categoryList);
		HttpSession session = request.getSession();
		String loginId = "";

		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<String> productByCategory = userService.getProductByCategory(categoryList, loginId);
		// List<Object[]> stores = userService.getAllstores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(productByCategory);
		// System.out.print("234" + mapJsonObject);
		return mapJsonObject;

	}

	/*
	 * Gokul Indent Transaction Save
	 * 
	 */
	@RequestMapping(value = "IndentCreation", method = RequestMethod.POST)
	public String IndentCreation(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestBody Product[] products) throws JsonMappingException, JsonProcessingException {

		HttpSession session = request.getSession();
		ObjectMapper objectMapper = new ObjectMapper();

		for (Product product : products) {
			// Perform any required operations with the values
			System.out.println("Product Name: " + product.getProductName());
			System.out.println("Product Price: " + product.getProductPrice());
			System.out.println("Quantity: " + product.getQuantity());
			System.out.println("Product ID: " + product.getProductID());
		}
		Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		String loginId = (String) userMap.get("login_id");
		String userId = (String) userMap.get("user_id");
		String userName = (String) userMap.get("storeCode");

		String IndentCreation = userService.IndentTransaction(products, loginId, userId, userName);
		return IndentCreation;
	}

	/*
	 * Gokul Get All Indent By Cost Center
	 */
	@RequestMapping(value = "IndentList", method = RequestMethod.GET)
	public ModelAndView IndentList(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			String costcenter = (String) session.getAttribute("login_id");
			List<Object> getIndentList;
			getIndentList = userService.getgetIndentList(costcenter);
			model.addAttribute("IndentList", getIndentList);
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		return new ModelAndView("admin/IndentList");
	}

	/*
	 * Gokul temp Indent Transaction CREATION Save
	 * 
	 */
	@RequestMapping(value = "tempCartIndentCreation", method = RequestMethod.POST)
	public String tempCartIndentCreation(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestBody Product[] products) throws JsonMappingException, JsonProcessingException {
System.err.println("murali debugger");
		HttpSession session = request.getSession();
		ObjectMapper objectMapper = new ObjectMapper();

		for (Product product : products) {
			// Perform any required operations with the values
			System.out.println("Product Name: " + product.getProductName());
			System.out.println("Product Price: " + product.getProductPrice());
			System.out.println("Quantity: " + product.getQuantity());
			System.out.println("Product ID: " + product.getProductID());
		}
		Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		String loginId = (String) userMap.get("login_id");
		String userId = (String) userMap.get("user_id");
		String userName = (String) userMap.get("storeCode");

		String IndentCreation = userService.tempCartIndentCreation(products, loginId, userId, userName);
		return IndentCreation;
	}

	/*
	 * gokul 07-07-2023 Indent Transaction update load page
	 */
	@RequestMapping(value = "indentTransactionUpdates", method = RequestMethod.GET)
	public ModelAndView indentTransactionUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {

		// System.out.print("234" + rowdata);@RequestParam("rowdata") String rowdata
		HttpSession session = request.getSession();
		try {
			String user = (String) session.getAttribute("login_id");
			// List<String> AllProducts;
			List<String> CategoryList;
			// AllProducts = userService.getAllProducts(user);
			CategoryList = userService.getCategoryList();
			// model.addAttribute("AllProducts", AllProducts);
			model.addAttribute("CategoryList", CategoryList);
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		return new ModelAndView("admin/indentTransactionUpdate");
	}

	@RequestMapping(value = "budgetbulkupload", method = RequestMethod.GET)
	public ModelAndView budgetbulkupload(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			String user = (String) session.getAttribute("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		return new ModelAndView("admin/budgetbulkupload");
	}

	/*
	 * gokul 07-07-2023
	 * 
	 * for indent update product catelogue fetch
	 */

	@RequestMapping(value = "GetAllProductsByIndent", method = RequestMethod.GET)
	public String GetAllProductsByIndent(HttpServletRequest request, HttpServletResponse responsel) {
		HttpSession session = request.getSession();
		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<String> storeStaffList = userService.getAllProductsByIndent(loginId);
		// List<Object[]> stores = userService.getAllstores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(storeStaffList);
		// System.out.print("234" + mapJsonObject);
		return mapJsonObject;

	}

	/*
	 * gokul 07-07-2023
	 * 
	 * for indent update product catelogue fetch GetAllProductsByIndentNumber
	 */

	@RequestMapping(value = "GetAllProductsByIndentNumber", method = RequestMethod.GET)
	public String GetAllProductsByIndentNumber(HttpServletRequest request, HttpServletResponse responsel,
			@RequestParam("IndentNumber") String IndentNumber) {
		System.out.print("234" + IndentNumber);
		HttpSession session = request.getSession();
		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<String> storeStaffList = userService.GetAllProductsByIndentNumber(loginId, IndentNumber);
		// List<Object[]> stores = userService.getAllstores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(storeStaffList);

		return mapJsonObject;

	}

	/*
	 * Gokul 07-07-2023 Indent Transaction quantity Save
	 */
	@RequestMapping(value = "IndentTransactionQuantitySave", method = RequestMethod.POST)
	public String IndentTransactionQuantitySave(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestBody Product[] products) throws JsonMappingException, JsonProcessingException {

		HttpSession session = request.getSession();
		ObjectMapper objectMapper = new ObjectMapper();

		for (Product product : products) {
			// Perform any required operations with the values
			System.out.println("Product Name: " + product.getProductName());
			System.out.println("Product Price: " + product.getProductPrice());
			System.out.println("Quantity: " + product.getQuantity());
			System.out.println("Product ID: " + product.getProductID());
		}
		Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		String loginId = (String) userMap.get("login_id");
		String userId = (String) userMap.get("user_id");
		String userName = (String) userMap.get("storeCode");

		String IndentCreation = userService.IndentTransactionQuantitySave(products, loginId, userId, userName);
		return IndentCreation;
	}

	/*
	 * gokul 10-07-2023 Indent Transaction add more products
	 */
	@RequestMapping(value = "AddMoreProducts", method = RequestMethod.GET)
	public ModelAndView AddMoreProducts(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			String user = (String) session.getAttribute("login_id");
			// List<String> AllProducts;
			List<String> CategoryList;
			// AllProducts = userService.getAllProducts(user);
			CategoryList = userService.getCategoryList();
			// model.addAttribute("AllProducts", AllProducts);
			model.addAttribute("CategoryList", CategoryList);
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		return new ModelAndView("admin/AddMoreProducts");
	}

	/*
	 * gokul 07-07-2023
	 * 
	 * for indent update product catelogue fetch
	 */

	@RequestMapping(value = "GetAddMoreProducts", method = RequestMethod.GET)
	public String GetAddMoreProducts(HttpServletRequest request, HttpServletResponse responsel) {
		HttpSession session = request.getSession();
		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<String> storeStaffList = userService.GetAddMoreProducts(loginId);
		// List<Object[]> stores = userService.getAllstores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(storeStaffList);
		// System.out.print("234" + mapJsonObject);
		return mapJsonObject;

	}

	@PostMapping(value = "uploadBulkbudgetExcelFile")
	public StringBuilder uploadBulkbudgetExcelFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "file") MultipartFile file, RedirectAttributes redirect, Model model) {

		HttpSession session = request.getSession();
		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new StringBuilder("Session is timeout, <a href='login' >click here</a> to login");
		}
		StringBuilder uploadedStatus = userService.uploadBulkbudgetExcelFile(file, loginId);
		redirect.addFlashAttribute("MESSAGE", uploadedStatus);
		model.addAttribute("MESSAGE", uploadedStatus);
		return uploadedStatus;
	}

	@GetMapping(value = "downloadbudgetFormatFile")
	public ResponseEntity<ByteArrayResource> downloadbudgetFormatFile(Model model) throws IOException {

		String BULK_USERS_MASTER_FILE_ROOT = "D:/FORMATE_FILE/";
		String BULK_USERS_MASTER_FILE_NAME = "COST-CENTER-BUDGET-BULK-UPLOAD.xlsx";

		String fileDirectory = BULK_USERS_MASTER_FILE_ROOT + BULK_USERS_MASTER_FILE_NAME;
		Path path = Paths.get(fileDirectory);
		byte[] bytesData = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(bytesData);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytesData.length).body(resource);

	}

	@RequestMapping(value = "hoildaybulkupload", method = RequestMethod.GET)
	public ModelAndView hoildaybulkupload(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			String user = (String) session.getAttribute("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		return new ModelAndView("admin/hoildaybulkupload");
	}

	@PostMapping(value = "uploadBulkholidayExcelFile")
	public StringBuilder uploadBulkholidayExcelFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "file") MultipartFile file, RedirectAttributes redirect, Model model) {

		HttpSession session = request.getSession();
		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new StringBuilder("Session is timeout, <a href='login' >click here</a> to login");
		}
		StringBuilder uploadedStatus = userService.uploadBulkholidayExcelFile(file, loginId);
		redirect.addFlashAttribute("MESSAGE", uploadedStatus);
		model.addAttribute("MESSAGE", uploadedStatus);
		return uploadedStatus;
	}

	/*
	 * gokul 07-07-2023
	 * 
	 * for indent update product catelogue fetch
	 */

	@RequestMapping(value = "getBudgetDetails", method = RequestMethod.GET)
	public String getBudgetDetails(HttpServletRequest request, HttpServletResponse responsel) {
		HttpSession session = request.getSession();
		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<Object> storeStaffList = userService.getBudgetDetails(loginId);
		
		// List<Object[]> stores = userService.getAllstores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(storeStaffList);
		// System.out.print("234" + mapJsonObject);
		return mapJsonObject;

	}

	/*
	 * Gokul 11-07-2023 Product by category for indent update landing page
	 */
	@RequestMapping(value = "getProductByCategoryIndentUpdate", method = RequestMethod.GET)
	public String getProductByCategoryIndentUpdate(HttpServletRequest request, HttpServletResponse responsel,
			@RequestParam("categoryList") String categoryList) {

		System.out.println("categoryArray" + categoryList);
		HttpSession session = request.getSession();
		String loginId = "";

		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<String> productByCategory = userService.getProductByCategoryIndentUpdate(categoryList, loginId);
		// List<Object[]> stores = userService.getAllstores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(productByCategory);
		// System.out.print("234" + mapJsonObject);
		return mapJsonObject;

	}

	/*
	 * Gokul 11-07-2023 Product by category for indent update add more filter
	 */
	@RequestMapping(value = "getProductByCategoryaAddMore", method = RequestMethod.GET)
	public String getProductByCategoryaAddMore(HttpServletRequest request, HttpServletResponse responsel,
			@RequestParam("categoryList") String categoryList) {

		System.out.println("categoryArray" + categoryList);
		HttpSession session = request.getSession();
		String loginId = "";

		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return "Access denied";
		}
		List<String> productByCategory = userService.getProductByCategoryaAddMore(categoryList, loginId);
		// List<Object[]> stores = userService.getAllstores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String mapJsonObject = gson.toJson(productByCategory);
		// System.out.print("234" + mapJsonObject);
		return mapJsonObject;

	}

	/*
	 * Gokul temp Indent Transaction CREATION Save
	 * 
	 */
	@RequestMapping(value = "tempIndentUpdateCreation", method = RequestMethod.POST)
	public String tempIndentUpdate(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestBody Product[] products) throws JsonMappingException, JsonProcessingException {

		HttpSession session = request.getSession();
		ObjectMapper objectMapper = new ObjectMapper();

		for (Product product : products) {
			// Perform any required operations with the values
			System.out.println("Product Name: " + product.getProductName());
			System.out.println("Product Price: " + product.getProductPrice());
			System.out.println("Quantity: " + product.getQuantity());
			System.out.println("Product ID: " + product.getProductID());
		}
		Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		String loginId = (String) userMap.get("login_id");
		String userId = (String) userMap.get("user_id");
		String userName = (String) userMap.get("storeCode");

		String IndentCreation = userService.tempIndentUpdateCreation(products, loginId, userId, userName);
		return IndentCreation;
	}

	/*
	 * Gokul Indent Transaction update from add more
	 * 
	 */
	@RequestMapping(value = "IndentTransactionUpdate", method = RequestMethod.POST)
	public String IndentTransactionUpdate(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestBody Product[] products) throws JsonMappingException, JsonProcessingException {

		HttpSession session = request.getSession();
	//	ObjectMapper objectMapper = new ObjectMapper();

		for (Product product : products) {
			// Perform any required operations with the values
			System.out.println("Product Name: " + product.getProductName());
			System.out.println("Product Price: " + product.getProductPrice());
			System.out.println("Quantity: " + product.getQuantity());
			System.out.println("Product ID: " + product.getProductID());
		}
		Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		String loginId = (String) userMap.get("login_id");
		String userId = (String) userMap.get("user_id");
		String userName = (String) userMap.get("storeCode");

		String IndentCreation = userService.IndentTransactionUpdate(products, loginId, userId, userName);
		return IndentCreation;
	}
	
	 public static int getMonthNumber(String monthName) {
	        Month month = Month.valueOf(monthName.toUpperCase());
	        return month.getValue();
	    }

	/*
	 * Created by : Gokul Created Date :12-07-2023 Description : Buyer Indent Page
	 */

	@RequestMapping(value = "BuyerIndentPage", method = RequestMethod.GET)
	public ModelAndView BuyerIndentPage(HttpServletRequest request, HttpServletResponse responsel, Model model) {
		//, 
		//@RequestParam("Year") String Year,@RequestParam("Month") String Month, @RequestParam("filter") String filter
		HttpSession session = request.getSession();
		String loginId = "";

		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		//SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		//String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		String yearfromCal1 = year_Date.format(cal.getTime());

		List<Object> BuyerList;
		List<Object> FooterList;
		List<String> finalcol = null;
		
		 finalcol = userService.getAllheader(yearfromCal,MonthText);
		
		List<String> collen = userService.getAllcolumnlength(yearfromCal,MonthText);
		System.out.println("collen"+collen);
		FooterList = userService.getBuyerFooterList(yearfromCal,MonthText,yearfromCal1);
		BuyerList = userService.getBuyerIndentList(yearfromCal,MonthText);
		model.addAttribute("BuyerList", BuyerList);
		model.addAttribute("FooterList", FooterList);
		model.addAttribute("Finalcol", finalcol);
		model.addAttribute("Collen", collen);

		return new ModelAndView("admin/BuyerIndentPage");
	}

	
	@RequestMapping(value = "getBuyerFooterListBasedOnFilter", method = RequestMethod.GET)
	public String getBuyerFooterListBasedOnFilter(HttpServletRequest request, HttpServletResponse responsel, Model model, 
			@RequestParam("Year") String Year,@RequestParam("Month") String Month) {
		HttpSession session = request.getSession();
		String loginId = "";

		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			//return new ModelAndView("login/login");
		}
		
		
		List<Object> BuyerList;
		List<Object> FooterList;
		List<String> finalcol = userService.getAllheader(Year,Month);
		List<String> collen = userService.getAllcolumnlength(Year,Month);
		
		if(collen.size() == 0) {
		     return null;
	    }
		
		FooterList = userService.getBuyerFooterList(Year,Month,null);
		BuyerList = userService.getBuyerIndentList(Year,Month);
		model.addAttribute("BuyerList", BuyerList);
		model.addAttribute("FooterList", FooterList);
		model.addAttribute("Finalcol", finalcol);
		model.addAttribute("Collen", collen);
			
		  List<Object> productByCategory =userService.getBuyerFooterListBasedOnFilter(Year, Month);
		  Gson gson = new GsonBuilder().setPrettyPrinting().create();
		  String collenList =gson.toJson(collen);
		  String mapJsonObject = gson.toJson(productByCategory) ;
		  System.out.println("Collen"+collen);
		  return mapJsonObject;
			/*
			 * // // // // List<Object> BuyerList; // List<Object> FooterList; // FooterList
			 * = userService.getBuyerFooterListBasedOnFilter(Year, Month); // BuyerList =
			 * userService.getBuyerIndentListBasedOnFilter(Year, Month); //
			 * model.addAttribute("BuyerList", BuyerList); //
			 * model.addAttribute("FooterList", FooterList); // // //ModelAndView mAV = new
			 * ModelAndView("redirect:/success.jsp"); // // return new
			 * ModelAndView("redirect:/BuyerIndentPage.jsp");
			 */
	}
	
	@RequestMapping(value = "indentreport", method = RequestMethod.GET)
	public ModelAndView indentreport(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();

		String loginId = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}

		List<Object> getdesignationreport;
//		getdesignationreport = userService.getAllindentDetails();
//		model.addAttribute("userDetails", getdesignationreport);
		  
	    List<String> years = userService.getYears();
	    
	   
	    model.addAttribute("years", years);
		return new ModelAndView("admin/indentreport");
	}

	@GetMapping(value = "downloadholidayFormatFile")
	public ResponseEntity<ByteArrayResource> downloadholidayFormatFile(Model model) throws IOException {

		String BULK_USERS_MASTER_FILE_ROOT = "D:/FORMATE_FILE/";
		String BULK_USERS_MASTER_FILE_NAME = "HOLIDAY-MASTER-BULK-UPLOAD.xlsx";

		String fileDirectory = BULK_USERS_MASTER_FILE_ROOT + BULK_USERS_MASTER_FILE_NAME;
		Path path = Paths.get(fileDirectory);
		byte[] bytesData = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(bytesData);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytesData.length).body(resource);

	}

	/*
	 * Gokul Indent Transaction update from add more
	 * 
	 */
	@RequestMapping(value = "BuyerIndentUpdateSave", method = RequestMethod.POST)
	public String BuyerIndentUpdateSave(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestBody BuyerIndentBean[] products) throws JsonMappingException, JsonProcessingException {

		HttpSession session = request.getSession();
		ObjectMapper objectMapper = new ObjectMapper();

//					for (BuyerIndentBean product : products) {
//			            // Perform any required operations with the values
//			            System.out.println("Product Name: " + product.getDocumnet());
//			            System.out.println("Product Price: " + product.getCostcenter());
//			            System.out.println("Quantity: " + product.getQuantity());
//			            System.out.println("Product ID: " + product.getQuantity());
//			            System.out.println("Product ID: " + product.getUnitPrice());
//			        }
		Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		String loginId = (String) userMap.get("login_id");
		String userId = (String) userMap.get("user_id");
		String userName = (String) userMap.get("storeCode");

		String IndentCreation = userService.BuyerIndentUpdateSave(products, loginId, userId, userName);
		return IndentCreation;
	}
	
	@RequestMapping(value = "adminreports", method = RequestMethod.GET)
	public ModelAndView adminreports(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		

		return new ModelAndView("admin/adminReportsView");
	}
	
	/**
	 * Distrubuter receipt page
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "DistriReceiptPage", method = RequestMethod.GET)
	public ModelAndView DistriReceiptPage(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		List<Object> BuyerList;
		List<Object> FooterList;
		List<String> finalcol = userService.getAllheader(yearfromCal,MonthText);
		List<String> collen = userService.getAllcolumnlength(yearfromCal,MonthText);
		FooterList = userService.getDistribuFooterList();
		BuyerList = userService.getDistributerIndentList();
		model.addAttribute("BuyerList", BuyerList);
	model.addAttribute("FooterList", FooterList);
	model.addAttribute("Finalcol", finalcol);
	model.addAttribute("Collen", collen);

		return new ModelAndView("admin/DistribReceiptPage");
	}
	
	/*
	 * Product catelogue land page
	 */
	@RequestMapping(value = "viewProductCatelogue", method = RequestMethod.GET)
	public ModelAndView viewProductCatelogue(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		try {
			String user = (String) session.getAttribute("login_id");
			List<String> AllProducts;
			List<String> CategoryList;
			AllProducts = userService.getAllProducts(user);
			CategoryList = userService.getCategoryList();
			System.out.println("password 2 :  " + AllProducts);
			System.out.println("CategoryList :  " + CategoryList);
			// model.addAttribute("budegetDetails", budegetDetails);
			model.addAttribute("AllProducts", AllProducts);
			model.addAttribute("CategoryList", CategoryList);

		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		return new ModelAndView("admin/viewproductlist");
	}
	
	/*@author :Gokul
	 * CreTE
	 * 
	 * 
	 */
	@RequestMapping(value = "DistributionPageSave", method = RequestMethod.POST)
	public String DistributionPageSave(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestBody BuyerIndentBean[] products) throws JsonMappingException, JsonProcessingException {

		HttpSession session = request.getSession();
		ObjectMapper objectMapper = new ObjectMapper();

//					for (BuyerIndentBean product : products) {
//			            // Perform any required operations with the values
//			            System.out.println("Product Name: " + product.getDocumnet());
//			            System.out.println("Product Price: " + product.getCostcenter());
//			            System.out.println("Quantity: " + product.getQuantity());
//			            System.out.println("Product ID: " + product.getQuantity());
//			            System.out.println("Product ID: " + product.getUnitPrice());
//			        }
		Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		String loginId = (String) userMap.get("login_id");
		String userId = (String) userMap.get("user_id");
		String userName = (String) userMap.get("storeCode");
		System.out.println("this is for checking"+products);
		String IndentCreation = userService.DistributionPageSave(products, loginId, userId, userName);
		return IndentCreation;
	}
	
	
	@RequestMapping(value = "updatepasswordn", method = RequestMethod.POST)
	public ModelAndView updatepasswordn(@RequestParam String login_id, @RequestParam String oldPwd,
			@RequestParam String newPwd,@RequestParam String email, HttpServletRequest request, RedirectAttributes redirect) throws Exception {
			HttpSession session = request.getSession();
		try {

			System.out.println("decryptedPwd:" + login_id);
			String pwdDb = userService.getPasswordByloginId(login_id);
			logger.info("Password from DB :" + pwdDb);
			PasswordUtils utils = new PasswordUtils();
			String decryptedPwd = utils.decrypt(pwdDb);
			System.out.println("decryptedPwd:" + decryptedPwd);
			if (oldPwd.equalsIgnoreCase(decryptedPwd)) {
				String encryptedPwd = utils.encrypt(newPwd);
				System.out.println(encryptedPwd);
				if (newPwd.equals(oldPwd)) {
					redirect.addFlashAttribute("NEW_PASSWORD_WRONG",
							"New password and Old password should not  be same.");
					return new ModelAndView("redirect:changepasswordbyadmin");
				}
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				System.out.println("userMap: " + userMap);
				String user_Name = (String) userMap.get("user_Name");
				String email_id = (String) userMap.get("email_id");

				int count = userService.updatePassword1(encryptedPwd, oldPwd,newPwd,login_id,email,email_id,user_Name);
				if (count < 1) {
					System.out.println("Failed to update password");
					redirect.addFlashAttribute("PASSWORD_CHANGE_FAILED", "Failed to change password");
					return new ModelAndView("redirect:changepasswordbyadmin");
				} else {
					System.out.println("Successfully updated password");
					redirect.addFlashAttribute("PASSWORD_CREATE_SUCCESS", "Password Changed Successfully.");
					return new ModelAndView("redirect:changepasswordbyadmin");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


		@RequestMapping(value = "getccidmasterid", method = RequestMethod.GET)
    public String getccidmasterid(String descode, HttpServletRequest request, HttpServletResponse response) {
	HttpSession session = request.getSession();
	String loginId ="";
	try {
		Map<String, Object> userMap  = (Map) session.getAttribute("userMap");
        loginId = (String)userMap.get("login_id");
		}catch(Exception er)
		{
			er.printStackTrace();
			return "Access denied";
		}
        List<String[]> getdesignationmasterid = userService.getccidmasterid(descode);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String mapJsonObject = gson.toJson(getdesignationmasterid);
        //System.out.print("234" + mapJsonObject);
        return mapJsonObject;

    }


	
		@RequestMapping(value = "getbudgetupdate", method = RequestMethod.POST)
	public String budgetupdate(HttpServletRequest request, @RequestParam("descode") String descode,
			@RequestParam("yearlybudget") String yearlybudget,@RequestParam("budgetextension") String budgetextension) {
		HttpSession session = request.getSession();
		Map<String, Object> userMap  = (Map) session.getAttribute("userMap");
	        String loginId = (String)userMap.get("login_id");  
		String generatehelioscopon = userService.budgetupdate(descode, yearlybudget,budgetextension,loginId);
		// logger.info(generatehelioscopon);
		Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
		String jsonData = jsonToString.toJson(generatehelioscopon);
		return jsonData;
	}
	
		@RequestMapping(value = "indentmanagerreport", method = RequestMethod.GET)
		public ModelAndView indentmanagerreport(HttpServletRequest request, HttpServletResponse response, Model model) {
		    HttpSession session = request.getSession();

		    String loginId = "";
		    try {
		        Map<String, Object> userMap = (Map) session.getAttribute("userMap");
		        loginId = (String) userMap.get("login_id");
		    } catch (Exception er) {
		        er.printStackTrace();
		        return new ModelAndView("login/login");
		    }

		    List<Object> getdesignationreport;
		    getdesignationreport = userService.getAllindentmanagerDetails(loginId);
//		    model.addAttribute("reportdetails", getdesignationreport);

		   
		    List<String> years = userService.getYears();
		    
		   
		    model.addAttribute("years", years);

		    return new ModelAndView("admin/indentmanagerreport");
		}

	
		@RequestMapping(value = "changepasswordbyadmin", method = RequestMethod.GET)
	public ModelAndView changepasswordbyadmin(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		response.setHeader("HeaderName", "murali");
		String loginId = "";
		String userRole = "";
		try {
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
			userRole = (String) userMap.get("role");
			if(userRole != "Buyer") {
				return new ModelAndView("login/login");
			}
		} catch (Exception er) {
			er.printStackTrace();
			return new ModelAndView("login/login");
		}
		
		List<Object> getdesignationreport;
		getdesignationreport = userService.getAllpasswordDetails();
		model.addAttribute("userDetails", getdesignationreport);
		System.out.println("my response : " + response);
		return new ModelAndView("admin/changepasswordbyadmin");
	}
		
		@RequestMapping(value = "sendToVendor", method = RequestMethod.POST)
		public String sendToVendor( @RequestBody String products,HttpServletRequest request, RedirectAttributes redirect) throws Exception {
				HttpSession session = request.getSession();
				String count="";
				String loginId = "";
			try {System.out.println("header : " + products);
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			loginId = (String) userMap.get("login_id");
		
				 ObjectMapper objectMapper = new ObjectMapper();
		            Map<String, Object> payload = objectMapper.readValue(products, Map.class);
		            count = userService.sendToVendor(payload,loginId);
					
				}
			 catch (Exception e) {
				e.printStackTrace();
			}

			return count;
		}
		/*
		 * 24-08-2023
		 * Filter For Indent Report for Month for Indenter
		 */

		@RequestMapping(value = "getreportbyid", method = RequestMethod.GET)
	    public String getreportbyid(String Month,String Year,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String loginId1 ="";
		try {
			Map<String, Object> userMap  = (Map) session.getAttribute("userMap");
	        loginId1 = (String)userMap.get("login_id");
			}catch(Exception er)
			{
				er.printStackTrace();
				return "Access denied";
			}
	        List<String[]> getreportbyid = userService.getreportbyid(Month,Year,loginId1);
			/*
			 * List<String> years = userService.getYears(); model.addAttribute("years",
			 * years);
			 */
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String mapJsonObject = gson.toJson(getreportbyid);
	        //System.out.print("234" + mapJsonObject);
	        return mapJsonObject;
	        }

	    
		
		/*
		 * 24-08-2023
		 * Filter For Indent Report for Month for Admin
		 */

		@RequestMapping(value = "getreportbyidadmin", method = RequestMethod.GET)
	    public String getreportbyidadmin(String Month,String Year,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String loginId1 ="";
		try {
			Map<String, Object> userMap  = (Map) session.getAttribute("userMap");
	        loginId1 = (String)userMap.get("login_id");
			}catch(Exception er)
			{
				er.printStackTrace();
				return "Access denied";
			}
	        List<String[]> getreportbyidadmin = userService.getreportbyidadmin(Month,Year,loginId1);
	       
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String mapJsonObject = gson.toJson(getreportbyidadmin);
	        //System.out.print("234" + mapJsonObject);
	        return mapJsonObject;

	    }
		
		@RequestMapping(value = "sendToStore", method = RequestMethod.POST)
		public String sendToStore( @RequestBody String products,HttpServletRequest request, RedirectAttributes redirect) throws Exception {
				HttpSession session = request.getSession();
				String count="";
				String loginId="";

			try {System.out.println("header : " + products);
				 ObjectMapper objectMapper = new ObjectMapper();
		            Map<String, Object> payload = objectMapper.readValue(products, Map.class);

		            Map<String, Object> userMap = (Map) session.getAttribute("userMap");
					loginId = (String) userMap.get("login_id");
					 count = userService.sendToStore(loginId,"masinenikrishnasai@titan.co.in",payload);
					
				}
			 catch (Exception e) {
				e.printStackTrace();
			}

			return count;
		}
		
				
		@RequestMapping(value = "productImageUpload", method = RequestMethod.GET)
		public ModelAndView productImageUpload(HttpServletRequest request, HttpServletResponse response, Model model) {

			HttpSession session = request.getSession();
			try {
				String user = (String) session.getAttribute("login_id");
				List<Object> getAllProduct;
				getAllProduct = userService.getAllProductId();
				model.addAttribute("ProductIdList", getAllProduct);
				
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			return new ModelAndView("admin/productImageUpload");
		}
		
		@PostMapping(value = "uploadProductImageFile")
		public String uploadProductImageFile(HttpServletRequest request, HttpServletResponse response,
				 RedirectAttributes redirect, Model model) throws IOException, ServletException {
			String productId = request.getParameter("productId");  
			Part filePart = request.getPart("file");
			HttpSession session = request.getSession();
			String loginId = "";
			int uploadedStatus=0;
			String r="";
			if (productId != null && !productId.isEmpty() && filePart != null) {
				String fileName = getFileExtension(filePart, model);
				  if (fileName == null) {
			           
			            return "Invalid file type. Only .png and files are allowed"; 
			        }

			    String uploadDirectory = "D:/apache-tomcat-9.0.78/webapps/stationary/WEB-INF/classes/static/product";
//				String uploadDirectory = "D:/ECLIPSEWORK/stationary/src/main/resources/static/product";
	            File file = new File(uploadDirectory, fileName);
	            try (InputStream fileContent = filePart.getInputStream()) {
	            	Map<String, Object> userMap = (Map) session.getAttribute("userMap");
					loginId = (String) userMap.get("login_id");
	                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                uploadedStatus =  userService.updateIsImage(productId,loginId);
					if (uploadedStatus != 0) {
						r = "File Uploaded Successfully";
					} else {
						r = "Upload failed";
					}
	            } catch (IOException e) {
	                // Handle exceptions
	            	e.printStackTrace();
	            }
			}
			return r;
		}
		
		/*
		 * private String getFileExtension(Part part) { String contentDisposition =
		 * part.getHeader("content-disposition"); String[] elements =
		 * contentDisposition.split(";"); for (String element : elements) { if
		 * (element.trim().startsWith("filename")) { String fileName =
		 * element.substring(element.indexOf('=') + 1).trim().replace("\"", ""); return
		 * fileName; } } return ""; }
		 */
		private String getFileExtension(Part part, Model model) {
		    String contentDisposition = part.getHeader("content-disposition");
		    StringBuilder messageBuilder = new StringBuilder();
		    String[] elements = contentDisposition.split(";");
		    for (String element : elements) {
		        if (element.trim().startsWith("filename")) {
		            String fileName = element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
		            
		          
		            String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
		            if (fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".jpg")) {
		                return fileName; 
		            } else {
		            	model.addAttribute("errorMessage", "Invalid file type. Only .png files are allowed.");
		                return null; 
		            }
		        }
		    }
		    return null; 
		}

		
		@RequestMapping(value = "getBuyerIndentListBasedOnFilter", method = RequestMethod.GET)
		public String getBuyerIndentListBasedOnFilter(HttpServletRequest request, HttpServletResponse responsel,
				@RequestParam("Year") String Year,@RequestParam("Month") String Month,Model model) {
			HttpSession session = request.getSession();
			String loginId = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			


List<Object> BuyerList;
		List<Object> FooterList;
		List<String> finalcol = userService.getAllheader(Year,Month);
		List<String> collen = userService.getAllcolumnlength(Year,Month);
		
		if(collen.size() == 0) {
			System.out.println("getBuyerFooterListBasedOnFilter");
			return null;
		}
		
		FooterList = userService.getBuyerFooterList(Year,Month,null);
		BuyerList = userService.getBuyerIndentList(Year,Month);
		model.addAttribute("BuyerList", BuyerList);
		model.addAttribute("FooterList", FooterList);
		model.addAttribute("Finalcol", finalcol);
		model.addAttribute("Collen", collen);
		
			List<Object> productByCategory = userService.getBuyerIndentListBasedOnFilter(Year, Month);
			//BuyerList = userService.getBuyerIndentList();
			//model.addAttribute("BuyerList", productByCategory);
			//model.addAttribute("FooterList", FooterList);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(productByCategory);
			return mapJsonObject;

		}
		
		
		@RequestMapping(value = "indentTransactionView", method = RequestMethod.GET)
		public ModelAndView indentTransactionView(HttpServletRequest request, HttpServletResponse response, Model model) {

			// System.out.print("234" + rowdata);@RequestParam("rowdata") String rowdata
			HttpSession session = request.getSession();
			try {
				String user = (String) session.getAttribute("login_id");
				// List<String> AllProducts;
				List<String> CategoryList;
				// AllProducts = userService.getAllProducts(user);
				CategoryList = userService.getCategoryList();
				// model.addAttribute("AllProducts", AllProducts);
				model.addAttribute("CategoryList", CategoryList);
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			return new ModelAndView("admin/indentTransactionView");
		}
		
		
		@RequestMapping(value = "getDistributionListBasedOnFilter", method = RequestMethod.GET)
		public String getDistributionListBasedOnFilter(HttpServletRequest request, HttpServletResponse responsel,
				@RequestParam("Year") String Year,@RequestParam("Month") String Month) {
			HttpSession session = request.getSession();
			String loginId = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			List<Object> productByCategory = userService.getDistributionListBasedOnFilter(Year, Month);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(productByCategory);
			return mapJsonObject;

		}
		
		

		@RequestMapping(value = "getDistributionFooterListBasedOnFiltert", method = RequestMethod.GET)
		public String getDistributionFooterListBasedOnFiltert(HttpServletRequest request, HttpServletResponse responsel,
				@RequestParam("Year") String Year,@RequestParam("Month") String Month) {
			HttpSession session = request.getSession();
			String loginId = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			List<Object> productByCategory = userService.getDistributionFooterListBasedOnFilter(Year, Month);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(productByCategory);
			return mapJsonObject;

		}
		

		@RequestMapping(value = "getBudgetDataforChart", method = RequestMethod.GET)
		public String getBudgetDataforChart(HttpServletRequest request, HttpServletResponse responsel) {
			HttpSession session = request.getSession();
			String CostCenter = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				CostCenter = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			List<Object> productByCategory = userService.getBudgetDataforChart(CostCenter);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(productByCategory);
			return mapJsonObject;

		}
		
		@RequestMapping(value = "indentList", method = RequestMethod.GET)
		public String indentList(HttpServletRequest request, HttpServletResponse responsel) {
			HttpSession session = request.getSession();
			String CostCenter = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				CostCenter = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			List<Object> productByCategory = userService.getAllIndentList();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(productByCategory);
			return mapJsonObject;
		}
		
		@RequestMapping(value = "sevenDayMailTrigger", method = RequestMethod.GET)
		public void sevenDayMailTrigger() {
			
			 LocalDateTime now = LocalDateTime.now();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			 String formattedDateTime = now.format(formatter);
			List<String> seventhDay = userService.get7thworkingDay();
			System.out.println("seventhDay : "+seventhDay.get(0) );
			System.out.println("Current time is ::: " + formattedDateTime);
			if (seventhDay != null && !seventhDay.isEmpty() && seventhDay.get(0).equals(formattedDateTime)) {
				userService.sevenDayMailTrigger();
	            System.out.println("Proceeding further...");
	        } else {
	        	//userService.sevenDayMailTrigger();
	            System.out.println("seventhDay and formattedDateTime do not match.");
	        }
			 
			try {
			//	Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			//	CostCenter = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				//return "Access denied";
			}
			//List<Object> productByCategory = userService.getBudgetDataforChart();
			//Gson gson = new GsonBuilder().setPrettyPrinting().create();
			//String mapJsonObject = gson.toJson(productByCategory);
			//return mapJsonObject; 
		}


		@RequestMapping(value = "get7thworkingDay", method = RequestMethod.GET)
		public String get7thworkingDay(HttpServletRequest request ,
		HttpServletResponse responsel
				) {
			;
			HttpSession session = request.getSession();
			String loginId = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			List<String> productByCategory = userService.get7thworkingDay();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(productByCategory);
			return mapJsonObject;

		}

		@RequestMapping(value = "productmastercreation", method = RequestMethod.GET)
		public ModelAndView productmastercreation(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}

			List<Object> designation = userService.getAllCCIDDetails();
			model.addAttribute("designation", designation);
			List<Object> Year = userService.getAllVendor();
			model.addAttribute("vendorList", Year);
			return new ModelAndView("admin/productmastercreation");
		}
		
		@RequestMapping(value = "getVendorEmailId", method = RequestMethod.GET)
		public String getVendorEmailId(HttpServletRequest request, HttpServletResponse responsel,
				@RequestParam("vendorname") String vendorname) {
			HttpSession session = request.getSession();
			String loginId = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			List<Object> productByCategory = userService.getVendorByEmailId(vendorname);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(productByCategory);
			return mapJsonObject;

		}
		
		
		@RequestMapping(value = "ProductMasterCreationSave", method = RequestMethod.POST)
		public String ProductMasterCreationSave(HttpServletRequest request, @RequestParam("ProductID") String ProductID,
				@RequestParam("ProductName") String ProductName, @RequestParam("vendor") String vendor,
				@RequestParam("EmailID") String EmailID, @RequestParam("Price") String Price,
				@RequestParam("UOM") String UOM,
				@RequestParam("Category") String Category) {

			HttpSession session = request.getSession();
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
			String generatehelioscopon = userService.ProductMasterCreationSave(ProductID,ProductName,vendor,EmailID,Price,UOM,Category,loginId);
			
			Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
			String jsonData = jsonToString.toJson(generatehelioscopon);
			return jsonData;
		}
		
		@RequestMapping(value = "productmaster", method = RequestMethod.GET)
		public ModelAndView productmaster(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}

			List<String> ProductListt = userService.getAllProductsForList();
			model.addAttribute("ProductListt", ProductListt);
			return new ModelAndView("admin/productMaster");
		}
		
		@RequestMapping(value = "productmasterupdate", method = RequestMethod.GET)
		public ModelAndView productmasterupdate(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
				List<String> CategoryList;
				CategoryList = userService.getCategoryList();
				model.addAttribute("CategoryList", CategoryList);
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}

			List<Object> Year = userService.getAllVendor();
			model.addAttribute("vendorList", Year);
			return new ModelAndView("admin/productmasterupdate");
		}
		
		@RequestMapping(value = "getProductDetailsByID", method = RequestMethod.GET)
		public String getProductDetailsByID(HttpServletRequest request, HttpServletResponse responsel,
				@RequestParam("ProductID") String ProductID) {
			HttpSession session = request.getSession();
			String loginId = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			List<Object> productByCategory = userService.getProductDetailsByID(ProductID);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(productByCategory);
			return mapJsonObject;

		}

		@RequestMapping(value = "ProductMasterCreationUpdate", method = RequestMethod.POST)
		public String ProductMasterCreationUpdate(HttpServletRequest request, @RequestParam("ProductID") String ProductID,
				@RequestParam("ProductName") String ProductName, @RequestParam("vendor") String vendor,
				@RequestParam("EmailID") String EmailID, @RequestParam("Price") String Price,
				@RequestParam("UOM") String UOM,
				@RequestParam("Category") String Category) {

			HttpSession session = request.getSession();
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
			String generatehelioscopon = userService.ProductMasterCreationUpdate(ProductID,ProductName,vendor,EmailID,Price,UOM,Category,loginId);
			
			Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
			String jsonData = jsonToString.toJson(generatehelioscopon);
			return jsonData;
		}
		
		
		@RequestMapping(value = "BudgetMasterList", method = RequestMethod.GET)
		public ModelAndView BudgetMasterList(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}

			List<String> ProductListt = userService.getAllBudgetForList();
			model.addAttribute("ProductListt", ProductListt);
			return new ModelAndView("admin/BudgetMasterList");
		}
		

		@RequestMapping(value = "budgetmastercreation", method = RequestMethod.GET)
		public ModelAndView budgetmastercreation(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			List<Object> designation = userService.getAllBudgetCCIDDe();
			System.out.println("allccids"+designation);
			model.addAttribute("designation", designation);
			List<Object> Year = userService.getAllyearDetails();
			model.addAttribute("years", Year);
			return new ModelAndView("admin/budgetmastercreation");
		}
		
		
		@RequestMapping(value = "cccreation", method = RequestMethod.GET)
		public ModelAndView ccCreation(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			List<Object> designation = userService.getAllCCIDDe();
			model.addAttribute("designation", designation);
			List<Object> Year = userService.getAllyearDetails();
			model.addAttribute("years", Year);
			return new ModelAndView("admin/cccreation");
		}
		

		@RequestMapping(value = "BudgetMasterCreationSave", method = RequestMethod.POST)
		public String BudgetMasterCreationSave(HttpServletRequest request,
				@RequestParam("CCID") String CCID,
				@RequestParam("Year") String Year,
				@RequestParam("COSTCENTERDESC") String COSTCENTERDESC,
				@RequestParam("GL") String GL,
				@RequestParam("GLDESC") String GLDESC,
				@RequestParam("LOCATION") String LOCATION,
				@RequestParam("Department") String Department,
				@RequestParam("YEARLYBUDGET") String YEARLYBUDGET) {

			HttpSession session = request.getSession();
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
			String generatehelioscopon = userService.BudgetMasterCreationSave(CCID,Year,COSTCENTERDESC,GL,GLDESC,LOCATION,Department,YEARLYBUDGET,loginId);
			
			Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
			String jsonData = jsonToString.toJson(generatehelioscopon);
			return jsonData;
		}
		
		@RequestMapping(value = "CCCreationSave", method = RequestMethod.POST)
		public String CCMasterCreationSave(HttpServletRequest request,
				@RequestParam("CCID") String CCID,
				@RequestParam("Year") String Year,
				@RequestParam("COSTCENTERDESC") String COSTCENTERDESC,
				@RequestParam("CCCOWNER") String CCCOWNER,
				@RequestParam("GL") String GL,
				@RequestParam("GLDESC") String GLDESC,
				@RequestParam("LOCATION") String LOCATION,
				@RequestParam("Department") String Department,
				@RequestParam("YEARLYBUDGET") String YEARLYBUDGET,
				@RequestParam("COSTEMAIL") String COSTEMAIL) {
System.out.println("CCCOWNER"+CCCOWNER);
			HttpSession session = request.getSession();
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
			String generatehelioscopon = userService.ccCreationSave(CCID,Year,COSTCENTERDESC,GL,GLDESC,LOCATION,Department,CCCOWNER,YEARLYBUDGET,loginId,COSTEMAIL);
			
			Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
			String jsonData = jsonToString.toJson(generatehelioscopon);
			return jsonData;
		}
		
		@RequestMapping(value = "poEntryDataCreatioonSave", method = RequestMethod.POST)
		public String poEntryDataCreatioon(HttpServletRequest request,
				@RequestParam("YEAR") String YEAR,
				@RequestParam("MONTH") String MONTH,
				@RequestParam("COSTCENTER") String COSTCENTER,
				@RequestParam("POAMOUNT") String POAMOUNT) {
			try {
				HttpSession session = request.getSession();
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				String loginId = (String) userMap.get("login_id");
				String generatehelioscopon = userService.poEntryCreationSave(YEAR,MONTH,COSTCENTER,POAMOUNT,loginId);
				Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
				String jsonData = jsonToString.toJson(generatehelioscopon);
				return jsonData;
			} catch (Exception er) {
				er.printStackTrace();
				return "error";
			}	
		}
		
		@RequestMapping(value = "poEntryDataUpdation", method = RequestMethod.POST)
		public String poEntryDataUpdation(HttpServletRequest request,
				@RequestParam("COSTVALUE") String COSTCENTER,
				@RequestParam("POAMOUNT") String POAMOUNT,
				@RequestParam("MONTH") String MONTH,
				@RequestParam("YEAR") String YEAR,
				@RequestParam("CREATEDBY") String CREATEDBY,
				@RequestParam("CREATEDON") String CREATEDON) {
			try {
				HttpSession session = request.getSession();
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				String loginId = (String) userMap.get("login_id");
				String poEntryDataUpdation = userService.poEntryDataUpdation(COSTCENTER,POAMOUNT,MONTH,YEAR,CREATEDBY,CREATEDON,loginId);
				Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
				String jsonData = jsonToString.toJson(poEntryDataUpdation);
				return jsonData;
			} catch (Exception er) {
				er.printStackTrace();
				return "error";
			}	
		}
		
		@RequestMapping(value = "budgetmasterupdate", method = RequestMethod.GET)
		public ModelAndView budgetmasterupdate(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			List<Object> designation = userService.getAllCCIDDetails();
			model.addAttribute("designation", designation);
			List<Object> Year = userService.getAllyearDetails();
			model.addAttribute("years", Year);
			return new ModelAndView("admin/budgetmasterupdate");
		}
		
		
		@RequestMapping(value = "getBidgetDetailsByID", method = RequestMethod.GET)
	    public String getBidgetDetailsByID(String CCID,String Year, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String loginId ="";
		try {
			Map<String, Object> userMap  = (Map) session.getAttribute("userMap");
	        loginId = (String)userMap.get("login_id");
			}catch(Exception er)
			{
				er.printStackTrace();
				return "Access denied";
			}
	        List<Object> getdesignationmasterid = userService.getBidgetDetailsByID(CCID,Year);
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String mapJsonObject = gson.toJson(getdesignationmasterid);
	        //System.out.print("234" + mapJsonObject);
	        return mapJsonObject;

	    }
		
		@RequestMapping(value = "BudgetMasterUpdateByForm", method = RequestMethod.POST)
		public String BudgetMasterUpdateByForm(HttpServletRequest request, 
				@RequestParam("CCID") String CCID,
				@RequestParam("Year") String Year,
				@RequestParam("COSTCENTERDESC") String COSTCENTERDESC,
				@RequestParam("GL") String GL,
				@RequestParam("GLDESC") String GLDESC,
				@RequestParam("LOCATION") String LOCATION,
				@RequestParam("Department") String Department,
				@RequestParam("YEARLYBUDGET") String YEARLYBUDGET,
				@RequestParam("oldYEARLYBUDGET") String oldYEARLYBUDGET) {

			HttpSession session = request.getSession();
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
			String generatehelioscopon = userService.BudgetMasterUpdateByForm(CCID,Year,COSTCENTERDESC,GL,GLDESC,LOCATION,Department,YEARLYBUDGET,loginId,oldYEARLYBUDGET);
			
			Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
			String jsonData = jsonToString.toJson(generatehelioscopon);
			return jsonData;
		}
		

		@RequestMapping(value = "productmasterupload", method = RequestMethod.GET)
		public ModelAndView productmasterupload(HttpServletRequest request, HttpServletResponse response, Model model) {

			HttpSession session = request.getSession();
			try {
				String user = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}

			return new ModelAndView("admin/productmasterupload");
		}
		
		@GetMapping(value = "downloadProductMastertFormatFile")
		public ResponseEntity<ByteArrayResource> downloadProductMastertFormatFile(Model model) throws IOException {

			String BULK_USERS_MASTER_FILE_ROOT = "D:/FORMATE_FILE/";
			String BULK_USERS_MASTER_FILE_NAME = "PRODUCT-MASTER-BULK-UPLOAD.xlsx";

			String fileDirectory = BULK_USERS_MASTER_FILE_ROOT + BULK_USERS_MASTER_FILE_NAME;
			Path path = Paths.get(fileDirectory);
			byte[] bytesData = Files.readAllBytes(path);
			ByteArrayResource resource = new ByteArrayResource(bytesData);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytesData.length).body(resource);

		}
		
		
		@PostMapping(value = "ProductMasterBulkUpload")
		public StringBuilder ProductMasterBulkUpload(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value = "file") MultipartFile file, RedirectAttributes redirect, Model model) {

			HttpSession session = request.getSession();
			String loginId = "";
			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new StringBuilder("Session is timeout, <a href='login' >click here</a> to login");
			}
			StringBuilder uploadedStatus = userService.ProductMasterBulkUpload(file, loginId);
			redirect.addFlashAttribute("MESSAGE", uploadedStatus);
			model.addAttribute("MESSAGE", uploadedStatus);
			return uploadedStatus;
		}
		
		@RequestMapping(value = "poEntry", method = RequestMethod.GET)
		public ModelAndView poEntry(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
				List<Object> ccEmpsIds = userService.getAllCCIDDe();
				model.addAttribute("ccEmpsIds", ccEmpsIds);
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			return new ModelAndView("admin/budjet");
		}
		
		@RequestMapping(value = "poEntryTable", method = RequestMethod.GET)
		public ModelAndView poEntryTable(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
				List<Object> poEntryList = userService.getAllPoEntryList();
				model.addAttribute("PoEntryList", poEntryList);
				System.out.println("Product is already available." +poEntryList);
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			return new ModelAndView("admin/poEntryTable");
		}
		
		@RequestMapping(value = "poEntryUpdate", method = RequestMethod.GET)
		public ModelAndView poEntryTableUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
				//List<Object> poEntryList = userService.getAllPoEntryList();
				//model.addAttribute("PoEntryList", poEntryList);
				//System.out.println("Product is already available." +poEntryList);
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			return new ModelAndView("admin/poEntryUpdate");
		}
		
		@RequestMapping(value = "uploadBulkPoEntry", method = RequestMethod.GET)
		public ModelAndView uploadBulkPoEntry(HttpServletRequest request, HttpServletResponse response, Model model) {
			try {
				HttpSession session = request.getSession();
				String loginId = (String) session.getAttribute("login_id");
				//List<Object> poEntryList = userService.getAllPoEntryList();
				//model.addAttribute("PoEntryList", poEntryList);
				//System.out.println("Product is already available." +poEntryList);
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			return new ModelAndView("admin/uploadBulkPoEntry");
		}
		
			@RequestMapping(value = "buyerFilterPage", method = RequestMethod.GET)
		public ModelAndView buyerFilterPage(HttpServletRequest request, HttpServletResponse response, Model model,
				@RequestParam("Year") String Year,@RequestParam("Month") String Month) {

			HttpSession session = request.getSession();
			try {
				String user = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
			String yearfromCal = year_Date.format(cal.getTime());
			String yearfromCal1 = year_Date.format(cal.getTime());

			SimpleDateFormat month_Date = new SimpleDateFormat("MM");
			// String monthFromCal = month_Date.format(cal.getTime());
			String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

			SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
			String MonthText = month.format(cal.getTime());
			int cFY = Integer.valueOf(yearfromCal);
			if (getMonthNumber(MonthText) < 4) {
				cFY = cFY - 1; // If the month is before April, subtract 1 from the year
			 yearfromCal = String.valueOf(cFY);
			    System.out.println("yearfromCal"+yearfromCal);
			}
			
			List<Object> BuyerList=null;
			List<Object> FooterList = null;
			List<String> finalcol =null;
			List<String> collen = null;
			if(Year.equalsIgnoreCase("0000"))
			{
				collen = userService.getAllcolumnlength(yearfromCal,MonthText);
				FooterList = userService.getBuyerFooterList(yearfromCal,MonthText,yearfromCal1);
				BuyerList = userService.getBuyerIndentList(yearfromCal,MonthText);
			finalcol= userService.getAllheader(yearfromCal,MonthText);
			}
			else {
				collen = userService.getAllcolumnlength(Year,Month);
				FooterList = userService.getBuyerFooterList(Year,Month,Year);
				BuyerList = userService.getBuyerIndentList(Year,Month);
				finalcol= userService.getAllheader(Year,Month);

			}
			model.addAttribute("BuyerList", BuyerList);
			model.addAttribute("FooterList", FooterList);
			model.addAttribute("Finalcol", finalcol);
			model.addAttribute("Collen", collen);
			return new ModelAndView("admin/buyerFilterPage");
		}
		
			@RequestMapping(value = "buyerFilterShowPage", method = RequestMethod.GET)
		public ModelAndView buyerFilterShowPage(HttpServletRequest request, HttpServletResponse response, Model model,
				@RequestParam("Year") String Year,@RequestParam("Month") String Month) {

			HttpSession session = request.getSession();
			try {
				String user = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
			String yearfromCal = year_Date.format(cal.getTime());
			String yearfromCal1 = year_Date.format(cal.getTime());

			SimpleDateFormat month_Date = new SimpleDateFormat("MM");
			// String monthFromCal = month_Date.format(cal.getTime());
			String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

			SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
			String MonthText = month.format(cal.getTime());
			int cFY = Integer.valueOf(Year);
			if (getMonthNumber(MonthText) < 4) {
				cFY = cFY - 1; // If the month is before April, subtract 1 from the year
			 yearfromCal = String.valueOf(cFY);
			    System.out.println("yearfromCal"+yearfromCal);
			}
			
			List<Object> BuyerList=null;
			List<Object> FooterList = null;
			List<String> finalcol =null;
			List<String> collen = null;
			if(Year.equalsIgnoreCase("0000"))
			{
				finalcol= userService.getAllheader(yearfromCal,MonthText);
				collen = userService.getAllcolumnlength(yearfromCal,MonthText);
				BuyerList = userService.getBuyerIndentList(yearfromCal,MonthText);
				FooterList = userService.getBuyerFooterList(yearfromCal,MonthText,yearfromCal1);
			}
			else {
				finalcol= userService.getAllheader(yearfromCal,Month);
				collen = userService.getAllcolumnlength(yearfromCal,Month);
				BuyerList = userService.getBuyerIndentList(yearfromCal,Month);
				FooterList = userService.getBuyerFooterList(yearfromCal,Month,Year);
				System.out.println("finalcol"+finalcol);
				System.out.println("collen"+collen);
				System.out.println("BuyerList"+BuyerList);
				System.out.println("FooterList"+FooterList+Year+Month);
			}
			model.addAttribute("BuyerList", BuyerList);
			model.addAttribute("FooterList", FooterList);
			model.addAttribute("Finalcol", finalcol);
			model.addAttribute("Collen", collen);
			return new ModelAndView("admin/buyerFilterShowPage");
		}

	@RequestMapping(value = "distributerFilterPage", method = RequestMethod.GET)
		public ModelAndView distributerFilterPage(HttpServletRequest request, HttpServletResponse response, Model model) {

			HttpSession session = request.getSession();
			try {
				String user = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
			String yearfromCal = year_Date.format(cal.getTime());

			SimpleDateFormat month_Date = new SimpleDateFormat("MM");
			// String monthFromCal = month_Date.format(cal.getTime());
			String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

			SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
			String MonthText = month.format(cal.getTime());
			int cFY = Integer.valueOf(yearfromCal);
			if (getMonthNumber(MonthText) < 4) {
				cFY = cFY - 1; // If the month is before April, subtract 1 from the year
			 yearfromCal = String.valueOf(cFY);
			    System.out.println("yearfromCal"+yearfromCal);
			}
			
			List<Object> BuyerList;
			List<Object> FooterList;
			List<String> finalcol = userService.getAllheader(yearfromCal,MonthText);
			List<String> collen = userService.getAllcolumnlength(yearfromCal,MonthText);
			FooterList = userService.getDistribuFooterList();
			BuyerList = userService.getDistributerIndentList();
			model.addAttribute("BuyerList", BuyerList);
		model.addAttribute("FooterList", FooterList);
		model.addAttribute("Finalcol", finalcol);
		model.addAttribute("Collen", collen);
		
			return new ModelAndView("admin/distributerFilterPage");
		}
		
		@RequestMapping(value = "distributerFilterShowPage", method = RequestMethod.GET)
		public ModelAndView distributerFilterShowPage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("Year") String Year,@RequestParam("Month") String Month) {

			HttpSession session = request.getSession();
			try {
				String user = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
			String yearfromCal = year_Date.format(cal.getTime());

			SimpleDateFormat month_Date = new SimpleDateFormat("MM");
			// String monthFromCal = month_Date.format(cal.getTime());
			String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

			SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
			String MonthText = month.format(cal.getTime());
			int cFY = Integer.valueOf(yearfromCal);
			if (getMonthNumber(MonthText) < 4) {
				cFY = cFY - 1; // If the month is before April, subtract 1 from the year
			 yearfromCal = String.valueOf(cFY);
			    System.out.println("yearfromCal"+yearfromCal);
			}
			
			List<Object> BuyerList;
			List<Object> FooterList;
			List<String> finalcol;
			List<String> collen;
			if(Year.equalsIgnoreCase("0000"))
			{
				finalcol = userService.getAllheader(yearfromCal,MonthText);
			    collen = userService.getAllcolumnlength(yearfromCal,MonthText);
				FooterList = userService.getDistribuFooterList();
				BuyerList = userService.getDistributerIndentList();
					}
			else {
				finalcol = userService.getAllheader(Year,Month);
				collen = userService.getAllcolumnlength(Year,Month);
				FooterList = userService.getDistribuFooterList();
				BuyerList = userService.getDistributerIndentList();
																																																		}
			model.addAttribute("BuyerList", BuyerList);
		model.addAttribute("FooterList", FooterList);
		model.addAttribute("Finalcol", finalcol);
		model.addAttribute("Collen", collen);
		
			return new ModelAndView("admin/distributerFilterShowPage");
		}

		/*
		 * @ControllerAdvice public class GlobalExceptionHandler {
		 * 
		 * @ExceptionHandler(Exception.class) public ModelAndView
		 * handleException(Exception ex) { ModelAndView modelAndView = new
		 * ModelAndView("error"); modelAndView.addObject("error", "An error occurred.");
		 * modelAndView.addObject("message", ex.getMessage());
		 * 
		 * return modelAndView; } }
		 */
		
		@GetMapping(value = "downloadPoentryFile")
		public ResponseEntity<ByteArrayResource> downloadPoentryFile(Model model) throws IOException {

			String BULK_USERS_MASTER_FILE_ROOT = "D:/FORMATE_FILE/";
			String BULK_USERS_MASTER_FILE_NAME = "PO-BULK-UPLOAD.xlsx";

			String fileDirectory = BULK_USERS_MASTER_FILE_ROOT + BULK_USERS_MASTER_FILE_NAME;
			Path path = Paths.get(fileDirectory);
			byte[] bytesData = Files.readAllBytes(path);
			ByteArrayResource resource = new ByteArrayResource(bytesData);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(bytesData.length).body(resource);

		}
		
		@PostMapping(value = "uploadBulkPoentryExcelFile")
		public StringBuilder uploadBulkPoentryExcelFile(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value = "file") MultipartFile file, RedirectAttributes redirect, Model model) {

			HttpSession session = request.getSession();
			String loginId = "";
			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new StringBuilder("Session is timeout, <a href='login' >click here</a> to login");
			}
			StringBuilder uploadedStatus = userService.uploadBulkPoentryExcelFile(file, loginId);
			redirect.addFlashAttribute("MESSAGE", uploadedStatus);
			model.addAttribute("MESSAGE", uploadedStatus);
			return uploadedStatus;
		}
		
		@RequestMapping(value = "learn", method = RequestMethod.GET)
		public ModelAndView learn(HttpServletRequest request, HttpServletResponse response, Model model) {

			HttpSession session = request.getSession();
			try {
				String user = (String) session.getAttribute("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return new ModelAndView("login/login");
			}
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
			String yearfromCal = year_Date.format(cal.getTime());

			SimpleDateFormat month_Date = new SimpleDateFormat("MM");
			// String monthFromCal = month_Date.format(cal.getTime());
			String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

			SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
			String MonthText = month.format(cal.getTime());
		
			return new ModelAndView("admin/learn");
		}
		
		/**
		 * @author 832044_CNST4 Murali chari.
		 * @param request
		 * @param prodId
		 * @return
		 */
		@RequestMapping(value = "productValidation", method = RequestMethod.POST)
		public String productValidation(HttpServletRequest request, @RequestParam("prodId") String prodId
				) {

			HttpSession session = request.getSession();
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
			String generatehelioscopon = userService.productValidation(prodId,loginId);
			Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
			String jsonData = jsonToString.toJson(generatehelioscopon);
			return jsonData;
		}
		
		/**
		 * @author 832044_CNST4 murali chari
		 * @param request
		 * @param prodId
		 * @return
		 */
		@RequestMapping(value = "ccvalidation", method = RequestMethod.POST)
		public String ccIdvalidate(HttpServletRequest request, @RequestParam("CCID") String CCID
				) {

			HttpSession session = request.getSession();
			Map<String, Object> userMap = (Map) session.getAttribute("userMap");
			String loginId = (String) userMap.get("login_id");
			String generatehelioscopon = userService.ccValidation(CCID,loginId);
			Gson jsonToString = new GsonBuilder().setPrettyPrinting().create();
			String jsonData = jsonToString.toJson(generatehelioscopon);
			return jsonData;
		}
		
		/*
		 * Created by : Gokul Created Date :12-07-2023 Description : Buyer Indent Page
		 */

		@RequestMapping(value = "BuyerIndentPageUpdate", method = RequestMethod.GET)
		public List<Object> BuyerIndentPageUpdate(HttpServletRequest request, HttpServletResponse responsel, Model model) {
			//, 
			//@RequestParam("Year") String Year,@RequestParam("Month") String Month, @RequestParam("filter") String filter
			HttpSession session = request.getSession();
			String loginId = "";

			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				//return "login/login";
			}

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
			String yearfromCal = year_Date.format(cal.getTime());

			//SimpleDateFormat month_Date = new SimpleDateFormat("MM");
			// String monthFromCal = month_Date.format(cal.getTime());
			//String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

			SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
			String MonthText = month.format(cal.getTime());
			int cFY = Integer.valueOf(yearfromCal);
			if (getMonthNumber(MonthText) < 4) {
				cFY = cFY - 1; // If the month is before April, subtract 1 from the year
			 yearfromCal = String.valueOf(cFY);
			    System.out.println("yearfromCal"+yearfromCal);
			}
			String yearfromCal1 = year_Date.format(cal.getTime());

			List<Object> BuyerList;
			List<Object> FooterList;
			List<String> finalcol = null;
			
			 finalcol = userService.getAllheader(yearfromCal,MonthText);
			
			List<String> collen = userService.getAllcolumnlength(yearfromCal,MonthText);
			System.out.println("collen"+collen);
			FooterList = userService.getBuyerFooterList(yearfromCal,MonthText,yearfromCal1);
			BuyerList = userService.getBuyerIndentList(yearfromCal,MonthText);
			model.addAttribute("BuyerList", BuyerList);
			model.addAttribute("FooterList", FooterList);
			model.addAttribute("Finalcol", finalcol);
			model.addAttribute("Collen", collen);

			return FooterList;
		}
		
		/*
		 * murali chari.
		 * 
		 * for indent update product catelogue fetch
		 */

		@RequestMapping(value = "getholidaymasterData", method = RequestMethod.GET)
		public String getHolidaymasterData(HttpServletRequest request, HttpServletResponse responsel) {
			HttpSession session = request.getSession();
			String loginId = "";
			try {
				Map<String, Object> userMap = (Map) session.getAttribute("userMap");
				loginId = (String) userMap.get("login_id");
			} catch (Exception er) {
				er.printStackTrace();
				return "Access denied";
			}
			List<Object> getHolidaymasterData = userService.getholidaymasterData(loginId);
			
			// List<Object[]> stores = userService.getAllstores();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String mapJsonObject = gson.toJson(getHolidaymasterData);
			// System.out.print("234" + mapJsonObject);
			return mapJsonObject;

		}
		
		}