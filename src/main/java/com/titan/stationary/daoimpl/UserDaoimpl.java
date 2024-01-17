package com.titan.stationary.daoimpl;

import javax.sql.ConnectionPoolDataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.print.attribute.SetOfIntegerSyntax;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.titan.stationary.bean.Budgetmasterbean;
import com.titan.stationary.bean.BuyerIndentBean;
import com.titan.stationary.bean.HolidayMasterBean;
import com.titan.stationary.bean.IndentMasterBean;
import com.titan.stationary.bean.PoEntryBean;
import com.titan.stationary.bean.Product;
import com.titan.stationary.bean.UserBean;
import com.titan.stationary.bean.UserLoginBean;
import com.titan.stationary.bean.productMasterbean;
import com.titan.stationary.bean.smUserMasterBean;
import com.titan.stationary.dao.UserDao;
import com.titan.stationary.security.AuthenticationService;
import com.titan.util.PasswordUtils;
import com.titan.util.Validations;

@Repository
public class UserDaoimpl implements UserDao {

	Logger logger = LogManager.getLogger(UserDaoimpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveLogini(UserBean userBean) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> findloginuser(UserLoginBean userLogin, String passwords) {

		int loginFlag = 1;
		Map<String, Object> userVal = new LinkedHashMap<String, Object>();
		System.out.println("userselection" + userLogin.getUser_selection());
		String loginId = userLogin.getLogin_id().toString();
		boolean isAuthenticated = false;
		if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
			int len = userLogin.getLogin_id().toString().trim().length();
			loginId = userLogin.getLogin_id().toString().trim().substring(0, len - 3);
		}

		if (userLogin.getUser_selection().equalsIgnoreCase("Tray Manager")) {
			if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
				isAuthenticated = true;
			} else {
				String usernameDB = "";
				// String Username = "";
				String getEmpCode = "SELECT top 1 EmpCode FROM Abm_master where empcode=:empcode"; // Doubt
				Query getempcoded = entityManager.createNativeQuery(getEmpCode);
				getempcoded.setParameter("empcode", userLogin.getLogin_id().toString().trim());
				try {
					usernameDB = (String) getempcoded.getSingleResult();
					// if (userLogin.getPassword().equalsIgnoreCase(passworddec)) {
					if (loginId.equalsIgnoreCase(usernameDB)) {
						isAuthenticated = authenticationService.authenticateWithLdap(loginId, passwords);
					}
				} catch (NoResultException no) {
					userVal.put("message", "User is not available in portal, Pls contact to Portal admin.");
				}
			}
			if (!isAuthenticated) {
				userVal.put("message", "Username/Password is not correct");
			} else {
				try {

					String getUsersDetails = "SELECT EmpCode,Name,Designation,City,MobileNumber,Emailid"
							+ " FROM ABM_MASTER WHERE EmpCode=:login_id ";

					Query getUsersDetailsQuery = entityManager.createNativeQuery(getUsersDetails);
					getUsersDetailsQuery.setParameter("login_id", loginId);
					List<Object[]> usersDetailsList = getUsersDetailsQuery.getResultList();
					if (usersDetailsList.size() > 0) {
						for (Iterator iterator = usersDetailsList.iterator(); iterator.hasNext();) {
							Object[] obj = (Object[]) iterator.next();
							userVal.put("message", "SUCCESS");
							userVal.put("login_id", obj[0].toString());
							userVal.put("user_Name", obj[1].toString());
							userVal.put("Designation", obj[2].toString());
							userVal.put("mobilenumber", obj[3].toString());
							userVal.put("email_id", obj[5].toString());
							// userVal.put("Stores", obj[6].toString());
							userVal.put("role", userLogin.getUser_selection());
							System.out.println("check" + userLogin.getPassword());
							loginFlag = 0;
						}
					} else {
						userVal.put("message", "User is not available in portal, Pls contact to Portal admin.");
					}
				} catch (Exception c) {
					c.printStackTrace();
				}
			}
		} else if (userLogin.getUser_selection().equalsIgnoreCase("Indent Manager")) { // indent user

			if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
				isAuthenticated = true;
			} else {
				String passwordfromDB = "";
				String passworddec = "";
				String getPasswordQuery = "SELECT top 1 PASSWORD FROM INDENT_MANAGER where lmsid=:lmsid"; // Doubt
				Query getPassword = entityManager.createNativeQuery(getPasswordQuery);
				getPassword.setParameter("lmsid", userLogin.getLogin_id().toString().trim()); // login thru cost center

				try {
					passwordfromDB = (String) getPassword.getSingleResult();
					PasswordUtils passwordUtils = new PasswordUtils();
					passworddec = passwordUtils.decrypt(passwordfromDB);
					System.out.println("passwordfromDB : " + passworddec);
					// if (userLogin.getPassword().equalsIgnoreCase(passworddec)) {
					if (passwords.equalsIgnoreCase(passworddec)) {
						/*
						 * String getUsersDetails = "SELECT [email],[EmpName] ,[STORECODE]" +
						 * " ,[CompanyEmployee]  ,[Password],[IsActive]" +
						 * "  FROM [INDENT_MANAGER] WHERE email=:login_id ";
						 */
						String getUsersDetails = "SELECT [lmsid], lmsid as [EmpName] ,[STORECODE]"
								+ ",[IsActive],region" + " from [INDENT_MANAGER] where lmsid=:lmsid ";
						Query getUsersDetailsQuery = entityManager.createNativeQuery(getUsersDetails);
						getUsersDetailsQuery.setParameter("lmsid", userLogin.getLogin_id().toString().trim());
						List<Object[]> usersDetailsList = getUsersDetailsQuery.getResultList();
						for (Iterator iterator = usersDetailsList.iterator(); iterator.hasNext();) {
							Object[] obj = (Object[]) iterator.next();// [1078437, MAHENDER NEGI, TKBN, false, true,
																		// null]
							userVal.put("message", "SUCCESS");
							userVal.put("user_id", obj[0].toString());
							userVal.put("user_Name", obj[1].toString());
							userVal.put("storeCode", obj[2].toString());
							// userVal.put("CompanyEmployee", obj[3].toString());
							// userVal.put("CompanyEmployee", obj[3].toString());
							userVal.put("login_id", obj[0].toString());
							userVal.put("role", userLogin.getUser_selection());
							loginFlag = 0;
						}
						isAuthenticated = true;
					} else {
						loginFlag = 0;
						userVal.put("message", "Password is wrong.");
						isAuthenticated = false;
					}

				} catch (NoResultException no) {
					no.printStackTrace();
					userVal.put("message", "Enter valid User ID.");
					return userVal;

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} else if (userLogin.getUser_selection().equalsIgnoreCase("Buyer")) {
			if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
				isAuthenticated = true;
			} else {
				isAuthenticated = authenticationService.authenticateWithLdap(loginId, passwords);
			}
			if (!isAuthenticated) {
				userVal.put("message", "Username/Password is not correct");
			} else {
				String getUsersDetails = "SELECT User_id,User_Name,email_id,login_id"
						+ " FROM ER_User_Master WHERE login_id=:login_id ";
				Query getUsersDetailsQuery = entityManager.createNativeQuery(getUsersDetails);
				getUsersDetailsQuery.setParameter("login_id", loginId);
				List<Object[]> usersDetailsList = getUsersDetailsQuery.getResultList();
				if (usersDetailsList.size() > 0) {
					for (Iterator iterator = usersDetailsList.iterator(); iterator.hasNext();) {
						Object[] obj = (Object[]) iterator.next();
						userVal.put("message", "SUCCESS");
						userVal.put("user_id", obj[0].toString());
						userVal.put("user_Name", obj[1].toString());
						userVal.put("email_id", obj[2].toString());
						userVal.put("login_id", obj[3].toString());
						userVal.put("role", "Buyer");

						loginFlag = 0;
					}
				} else {
					userVal.put("message", "Admin detail is not available in portal, Pls contact to Portal admin.");
				}
			}
		}
		return userVal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> usersearch(String firstname) {
		boolean count = Validations.validation(firstname);
		if (count)
			return new ArrayList<Object>();
		List<Object> getAllSubmenuNames;
		String GET_ALL_SUBMENU_NAMES = "SELECT USER_NAME,Company_Name,email_id, login_id, USER_TYPE FROM er_user_master where user_name like '%"
				+ firstname + "%' ";
		Query getUserBySearch = entityManager.createNativeQuery(GET_ALL_SUBMENU_NAMES.toString());
		List<Object> getUsers = getUserBySearch.getResultList();
		return getUsers;
	}

	private int createStoreManagerByForm(smUserMasterBean storemaster, String loginId) {
		int response = 0;
		try {

			String passwordfromDB = storemaster.getPassword();
			PasswordUtils utils = new PasswordUtils();
			String encryptedPwd = utils.encrypt(passwordfromDB);

			String animals = storemaster.getStoreCode();
			String animals_list[] = animals.split(",");
			String animal1 = animals_list[0];
			// System.out.println("array" + animal1);
			System.out.println(storemaster.toString());
			Query insertabmuser = entityManager.createNativeQuery(
					"Insert Into INDENT_MANAGER (EmpCode,Password,EmpName,MobileNumber,CompanyEmployee,IsActive,ChangedBy,ChangedOn,CreatedBy,CreatedOn,LMSID,Email,Storecode,region,abm)"
							+ " VALUES(:Empcode,:password,:empname,:mobilenumber,:comany,"
							+ ":IsActive,:ChangedBy,:ChangedOn,:CreatedBy,:CreatedOn,:lmsid,:Email,:stores,:region,:abm)");
			insertabmuser.setParameter("Empcode", storemaster.getEmpCode());
			insertabmuser.setParameter("password", encryptedPwd);
			insertabmuser.setParameter("empname", storemaster.getEmpName());
			insertabmuser.setParameter("stores", animals_list[0]);
			insertabmuser.setParameter("mobilenumber", storemaster.getMobileNumber());
			insertabmuser.setParameter("comany", storemaster.getCompanyemployee());
			insertabmuser.setParameter("IsActive", storemaster.getIsActive());
			insertabmuser.setParameter("ChangedBy", loginId);
			insertabmuser.setParameter("ChangedOn", new Date());
			insertabmuser.setParameter("CreatedBy", loginId);
			insertabmuser.setParameter("CreatedOn", new Date());
			insertabmuser.setParameter("lmsid", storemaster.getLmsid());
			insertabmuser.setParameter("Email", storemaster.getEmail());
			insertabmuser.setParameter("region", storemaster.getRegion());
			insertabmuser.setParameter("abm", loginId);

			response = insertabmuser.executeUpdate();
			if (response != 0) {
				System.out.println("Failed to update Store Manager");
				// int insertInitiateWorkPermitStatus = 0;
				int n = animals_list.length;
				for (int i = 0; i < n; i++) {
					// insertItem(array, 2*i+1, 0);
					Query storinsert = entityManager
							.createNativeQuery("Insert Into STORE_MANAGER_MAP (STORECODE,STOREMANAGER,LMSID,EMAIL)"
									+ " VALUES(:STORE,:ABM,:lms,:email)");
					storinsert.setParameter("STORE", animals_list[i]);
					storinsert.setParameter("ABM", storemaster.getEmpCode());
					storinsert.setParameter("lms", storemaster.getLmsid());
					storinsert.setParameter("email", storemaster.getEmail());
					int insertInitiateWorkPermitStatus = storinsert.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> usersearchs(String firstname) {
		boolean count = Validations.validation(firstname);
		if (count)
			return new ArrayList<String[]>();
		if (firstname.length() < 3)
			return new ArrayList<String[]>();
		String sql = "SELECT username,employee_name,designation,department,city,email FROM HR_EMP where employee_name like '%"
				+ firstname + "%' or username like '%" + firstname + "%' ";
		// productQualityFeedbackQuery.setParameter("USER_NAME", firstname);
		// productQualityFeedbackQuery.setParameter("toDate", formattedEndDate);
		Query query = entityManager.createNativeQuery(sql);
		List<String[]> response = query.getResultList();
		return response;
	}

	@Override
	public List<String> getPortalMIS(String loginID) {
		String query = "select count(*) from INDENT_MANAGER where IsActive=1" + " union all"
				+ " select count(*) from Store_Staff_Master where IsActive=1" + " union all"
				+ " select count(*) from ABM_MASTER where IsActive=1";
		Query store = entityManager.createNativeQuery(query);

		List<String> resposne = store.getResultList();
		return resposne;

	}

	@Override
	public String getPasswordbyloginId(String login_id) {
		String PASSWORD_QUERY = "select password from INDENT_MANAGER where lmsid=:login_id";
		Query getPasswordQuery = entityManager.createNativeQuery(PASSWORD_QUERY);
		getPasswordQuery.setParameter("login_id", login_id);
		getPasswordQuery.getResultList();
		String password = (String) getPasswordQuery.getSingleResult();
		// logger.info("Password : "+password);
		return password;
	}

	// confirm_password=:confirm_password

	@Override
	public int saveLoginDetails(String login_Id, HttpServletRequest request, HttpServletResponse response) {

		String INSERT_USER_LOGS_QUERY = "INSERT INTO USER_LOGS(User_Id,login_id,User_Name)VALUES(:user_Id,:login_id,:user_Name) ";
		String loginId = "";
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("user_id");

		String user_Name = (String) session.getAttribute("user_Name");
		if (login_Id.toString().trim().endsWith("ccc")) {
			int len = login_Id.toString().trim().length();
			loginId = login_Id.toString().trim().substring(0, len - 3);
		} else {
			loginId = login_Id.toString().trim();
		}
		int user_id = Integer.valueOf(userId);
		Query insertQueryUsers = entityManager.createNativeQuery(INSERT_USER_LOGS_QUERY);
		insertQueryUsers.setParameter("user_Id", userId);
		insertQueryUsers.setParameter("login_id", loginId);
		insertQueryUsers.setParameter("user_Name", user_Name);
		int status = insertQueryUsers.executeUpdate();
		return status;
	}

	@Override
	public int updateLogouTIme(String login_id) {
		String UPDATE_USER_LOGS = "UPDATE USER_LOGS SET LOGOUT_TIME=:logout_Time where log_id= (select max(log_id) from USER_LOGS where login_id=:login_id)";
		Date date = new Date();
		long time = date.getTime();
		String loginId = "";
		Timestamp logoutTime = new Timestamp(time);
		if (login_id.toString().trim().endsWith("ccc")) {
			int len = login_id.toString().trim().length();
			loginId = login_id.toString().trim().substring(0, len - 3);
		} else {
			loginId = login_id.toString().trim();
		}
		Query updateQuery = entityManager.createNativeQuery(UPDATE_USER_LOGS);
		updateQuery.setParameter("logout_Time", logoutTime);
		updateQuery.setParameter("login_id", loginId);
		int status = updateQuery.executeUpdate();
		return status;
	}

	// krishna
	@Override
	public String getEmailIdbyLoginId(String login_id) {
		String EMAIL_ID_QUERY = "select email from INDENT_MANAGER where lmsid=:login_id";
		Query getEmailIdQuery = entityManager.createNativeQuery(EMAIL_ID_QUERY);
		getEmailIdQuery.setParameter("login_id", login_id);

		String getStoreManagerDetials = (String) getEmailIdQuery.getSingleResult();
		// String email_Id = (String) getEmailIdQuery.getSingleResult();
		logger.info("email : " + getStoreManagerDetials);
		return getStoreManagerDetials;
	}

	// confirm_password=:confirm_password
	@Override
	public int updatePassword(String pwd, String cpwd, String login_id) {
		String sql = "update INDENT_MANAGER set Password=:Password where lmsid=:login_id";
		Query query = entityManager.createNativeQuery(sql);
		// Query query = session.createSQLQuery(sql);
		query.setParameter("Password", pwd);
//			query.setParameter("confirm_password", cpwd);
		query.setParameter("login_id", login_id);
		return query.executeUpdate();
	}

	/*
	 * @Override public int updatePassword1(String pwd, String oldPwd, String
	 * login_id) { String sql =
	 * "update INDENT_MANAGER set Password=:Password where lmsid=:login_id"; Query
	 * query = entityManager.createNativeQuery(sql); // Query query =
	 * session.createSQLQuery(sql); query.setParameter("Password", pwd); //
	 * query.setParameter("confirm_password", cpwd); query.setParameter("login_id",
	 * login_id); return query.executeUpdate(); //sendpasswordMail(storemaster,
	 * loginId,"Password");
	 * 
	 * }
	 */
	@Override
	public int updatePassword1(String pwd, String oldPwd, String newPwd, String login_id, String email, String email_id,
			String user_Name) {
		String sql = "update INDENT_MANAGER set Password=:Password where lmsid=:login_id";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("Password", pwd);
		query.setParameter("login_id", login_id);
		// query.setParameter("newPwd", newPwd);

		int updateCount = query.executeUpdate();
		// String senderEmail = getIndentManagerEmailFromDatabase(email);
		// String indentManagerEmail = getUserEmailFromDatabase(login_id);
		sendpasswordMail(email, email_id, user_Name, newPwd, pwd, login_id);

		return updateCount;
	}

	/*
	 * @Override public String getUserEmailFromDatabase(String login_id) { String
	 * EMAIL_ID_QUERY = "select email from ER_User_Master where login_id=:login_id";
	 * Query getEmailIdQuery = entityManager.createNativeQuery(EMAIL_ID_QUERY);
	 * getEmailIdQuery.setParameter("login_id", login_id);
	 * 
	 * String getStoreManagerDetials =(String) getEmailIdQuery.getSingleResult(); //
	 * String email_Id = (String) getEmailIdQuery.getSingleResult();
	 * logger.info("email : "+getStoreManagerDetials); return
	 * getStoreManagerDetials; }
	 * 
	 * @Override public String getIndentManagerEmailFromDatabase(String email) {
	 * String EMAIL_ID_QUERY =
	 * "select email from INDENT_MANAGER where lmsid=:login_id"; Query
	 * getEmailIdQuery = entityManager.createNativeQuery(EMAIL_ID_QUERY);
	 * getEmailIdQuery.setParameter("login_id", email);
	 * //getEmailIdQuery.setParameter("User_Name", User_Name);
	 * 
	 * String getindentManagerDetials =(String) getEmailIdQuery.getSingleResult();
	 * // String email_Id = (String) getEmailIdQuery.getSingleResult();
	 * logger.info("email : "+getindentManagerDetials); return
	 * getindentManagerDetials; }
	 */

	
	
	
	/**
	 * Gokul Get All Products for catelogue page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllProducts(String userId) {

		List<String> getStoreManagerDetials;

		Query selectQuery = entityManager.createNativeQuery(
				"select pm.PRODUCT_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom, tcit.COST_CENTER,isnull(tcit.TOTAL_USER_QTY,0) as quantity from PRODUCT_MASTER pm "
						+ " left join temp_cart_indent_Transaction tcit on pm.PROD_NAME = tcit.item and COST_CENTER=:userId order by  pm.PROD_NAME asc");

		selectQuery.setParameter("userId", userId);
		try {
			getStoreManagerDetials = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getStoreManagerDetials = null;
		}
		return getStoreManagerDetials;
	}

	/**
	 * Gokul Get Category for catelogue page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCategoryList() {

		List<String> getCategoryList;

		Query selectQuery = entityManager.createNativeQuery(
				"select DISTINCT CATEGORY,CATORDERS from PRODUCT_MASTER WHERE  CATEGORY <> '' GROUP BY CATEGORY,CATORDERS ORDER BY CATORDERS ASC");
		try {	
			getCategoryList = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getCategoryList = null;
		}
		return getCategoryList;
	}

	/**
	 * Gokul Get Products by category indent creation
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProductByCategory(String category, String userId) {

		List<String> getStoreManagerDetials;

		String[] categoryArray = convertToQuery(category);

		Query selectQuery = entityManager.createNativeQuery(
				"select DISTINCT pm.PRODUCT_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom,  tcit.COST_CENTER, "
						+ "isnull(tcit.TOTAL_USER_QTY,0) as quantity from PRODUCT_MASTER pm "
						+ "left join temp_cart_indent_Transaction tcit on pm.PROD_NAME = tcit.item and COST_CENTER=:userId where category IN( :category) "
						+ "order by quantity desc ");
		List<String> brandList = Arrays.asList(categoryArray);
		selectQuery.setParameter("category", brandList);
		selectQuery.setParameter("userId", userId);
		try {
			if (categoryArray.length != 0) {
				getStoreManagerDetials = selectQuery.getResultList();
			} else {
				// getStoreManagerDetials = null;
				getStoreManagerDetials = getAllProducts(userId);
			}
		} catch (Exception e) {

			e.printStackTrace();
			getStoreManagerDetials = null;
		}
		return getStoreManagerDetials;
	}

	public static String[] convertToQuery(String str) {
		String[] strArr = {};
		if (!str.isEmpty()) {
			strArr = (str.split(","));
			return strArr;
		}
		return strArr;
	}

	/*
	 * Gokul Indent Transaction save Call when click on submit on indent creation
	 * page
	 */

	@Override
	public String IndentTransaction(Product[] products, String loginID, String userId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder messBuilder = new StringBuilder();
		String r = "";
		int response = 0;
		String maxId = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		Query checkIndentEntry = entityManager.createNativeQuery(
				"select count(DOC_NUMBER) from Indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

		checkIndentEntry.setParameter("year", cFY);
		checkIndentEntry.setParameter("month", MonthText);
		checkIndentEntry.setParameter("cost_center", userId);
		int count = (int) checkIndentEntry.getSingleResult();

		if (count != 0) {

			Query getintentID = entityManager.createNativeQuery(
					"select DISTINCT DOC_NUMBER from Indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

			getintentID.setParameter("year", cFY);
			getintentID.setParameter("month", MonthText);
			getintentID.setParameter("cost_center", userId);
			String id = (String) getintentID.getSingleResult();
			return "For the month : " + MonthText + " already Indent is generated : " + id + " for this cost center "
					+ userId + ".";
		} else {

			maxId = getIndenttransactionId(userId, yearfromCal, monthFromCal);

			Query gettempID = entityManager.createNativeQuery(
					"select DISTINCT TempId from temp_cart_indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

			gettempID.setParameter("year", cFY);
			gettempID.setParameter("month", MonthText);
			gettempID.setParameter("cost_center", userId);
			String tempid = (String) gettempID.getSingleResult();

			Query InsertIndenttransaction = entityManager.createNativeQuery("INSERT INTO Indent_Transaction"
					+ "(DOC_NUMBER,DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,UnitPrice,CLOSE_DATE"
					+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_TMT_QTY,Received_date,PROD_NUMBER) "
					+ "select :DOC_NUMBER,:DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,UnitPrice,CLOSE_DATE, "
					+ "STATUS,null,0,TOTAL_USER_QTY,null,null,0,null,null,PROD_NUMBER from  temp_cart_indent_Transaction where TempId=:tempid");

			InsertIndenttransaction.setParameter("tempid", tempid);
			InsertIndenttransaction.setParameter("DOC_NUMBER", maxId);
			InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
			response = InsertIndenttransaction.executeUpdate();

			Query deletetempIndent = entityManager
					.createNativeQuery("delete from  temp_cart_indent_Transaction where TempId=:tempid");

			deletetempIndent.setParameter("tempid", tempid);
			deletetempIndent.executeUpdate();

			Query gettotal = entityManager.createNativeQuery("select ISNULL(SUM(VALUE), 0) from Indent_Transaction \r\n"
					+ " where COST_CENTER=:userId and TOTAL_USER_QTY<>0 and year=:cFY and month=:monthx");

			gettotal.setParameter("userId", userId);
			gettotal.setParameter("cFY", cFY);
			gettotal.setParameter("monthx", MonthText);
			BigDecimal total = (BigDecimal) gettotal.getSingleResult();

			if (MonthText.equalsIgnoreCase("July")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set july=july+:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();
			} else if (MonthText.equalsIgnoreCase("August")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set August=August+:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("September")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set September=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("October")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set October=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("November")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set November=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("December")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set December=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("January")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set January=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("February")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set February=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("March")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set March=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("April")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set April=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("May")) {
				Query updateBudgetMaster = entityManager
						.createNativeQuery("update budget_master set May=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			} else if (MonthText.equalsIgnoreCase("June")) {
				Query updateBudgetMaster = entityManager.createNativeQuery(
						"update budget_master set June=:total" + " where ccid=:userId and year=:cFY");

				updateBudgetMaster.setParameter("total", total);
				updateBudgetMaster.setParameter("userId", userId);
				updateBudgetMaster.setParameter("cFY", yearfromCal);
				updateBudgetMaster.executeUpdate();

			}
		}
		if (response != 0) {
			r = "Indent Is Created Successfully : " + maxId;
		} else {
			r = "Update failed";
		}
		// }
		return r;
	}

	/*
	 * Gokul Indent DOCUMENT NUMBER GENERATE
	 * 
	 * 
	 * public String getIndenttransactionId(String UserID, String yearfromCal,
	 * String monthFromCal) { int cFY = Integer.valueOf(yearfromCal); int cFM =
	 * Integer.valueOf(monthFromCal); int index = 1; int maxOrderID_DB = 0; String
	 * OrderIdString = ""; Query queryToGetMaximumOrderId =
	 * entityManager.createNativeQuery(
	 * "SELECT isnull(MAX(CAST(SUBSTRING(DOC_NUMBER,11,LEN(DOC_NUMBER)-9) AS INT)),0) FROM Indent_Transaction"
	 * );
	 * 
	 * Optional<Integer> maxOrderId = null; try { maxOrderID_DB = (int)
	 * queryToGetMaximumOrderId.getSingleResult(); maxOrderId =
	 * Optional.ofNullable(maxOrderID_DB); if (maxOrderId.isPresent()) {
	 * OrderIdString = Integer.toString(maxOrderId.get()); } // int to String
	 * Conversion } catch (Exception e) { e.printStackTrace(); e.getCause(); }
	 * 
	 * if (OrderIdString != null) { OrderIdString = UserID + cFY + cFM + ("00" +
	 * (maxOrderID_DB + 1)); return OrderIdString; } OrderIdString = UserID + cFY +
	 * cFM + ("00" + index);
	 * 
	 * return OrderIdString; }
	 */
	
	
	public String getIndenttransactionId(String UserID, String yearfromCal, String monthFromCal) {
	    int cFY = Integer.valueOf(yearfromCal);
	    int cFM = Integer.valueOf(monthFromCal);
	    int maxOrderID_DB = 0;
	    String OrderIdString = "";

	    Query queryToGetMaximumOrderId = entityManager.createNativeQuery(
	            "SELECT ISNULL(MAX(CAST(SUBSTRING(DOC_NUMBER, 11, LEN(DOC_NUMBER) - 10) AS INT)), 0) FROM Indent_Transaction");
 
	    try {
	        maxOrderID_DB = (int) queryToGetMaximumOrderId.getSingleResult();
	        OrderIdString = String.format("%05d", maxOrderID_DB + 1); 
	    } catch (Exception e) {
	        e.printStackTrace();
	        e.getCause();
	    }
 
	   
	    OrderIdString = UserID + cFY + cFM + OrderIdString;
 
	   
	    return OrderIdString;
	}

	/*
	 * Gokul GET All Indent lIST
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getIndentList(String costcenter) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());
		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String Month = month.format(cal.getTime());
		
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(Month) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		List<Object> IndentList;
		Query selectQuery = entityManager.createNativeQuery(
				"SELECT DISTINCT DOC_NUMBER,format(DOC_DATE,'dd-MMM-yyy') DOC_DATE,im.LMSID,CONCAT(UPPER(SUBSTRING(STATus, 1, 1)), LOWER(SUBSTRING(STATus, 2, LEN(STATus)))) AS STATUS FROM Indent_Transaction it "
						+ " left join INDENT_MANAGER im on it.CREATEDBY = im.LMSID where Cost_Center =:costcenter and it.month=:Month and it.year=:year");

		selectQuery.setParameter("Month", Month);
		;
		selectQuery.setParameter("year", yearfromCal);
		;
		selectQuery.setParameter("costcenter", costcenter);
		try {
			IndentList = selectQuery.getResultList();
		} catch (Exception e) {

			e.printStackTrace();
			IndentList = null;
		}
		return IndentList;
	}

	/*
	 * Gokul temp cart Indent Transaction creation save
	 */

	@Override
	public String tempCartIndentCreation(Product[] products, String loginID, String userId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder messBuilder = new StringBuilder();
		String r = "";
		int response = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}

		Query checkIndentTransaction = entityManager.createNativeQuery(
				"select count(doc_number) from indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

		checkIndentTransaction.setParameter("year", cFY);
		checkIndentTransaction.setParameter("month", MonthText);
		checkIndentTransaction.setParameter("cost_center", userId);
		int count11 = (int) checkIndentTransaction.getSingleResult();
		if (count11 == 0) {
			Query checkIndentEntry = entityManager.createNativeQuery(
					"select count(TempId) from temp_cart_indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

			checkIndentEntry.setParameter("year", cFY);
			checkIndentEntry.setParameter("month", MonthText);
			checkIndentEntry.setParameter("cost_center", userId);
			int count = (int) checkIndentEntry.getSingleResult();
			if (count != 0) {
				Query gettempID = entityManager.createNativeQuery(
						"select DISTINCT TempId from temp_cart_indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

				gettempID.setParameter("year", cFY);
				gettempID.setParameter("month", MonthText);
				gettempID.setParameter("cost_center", userId);
				String id = (String) gettempID.getSingleResult();
				int n = products.length;
				for (int i = 0; i < n; i++) {
					String stringValue = products[i].getProductPrice();
					stringValue = stringValue.replaceAll("[₹\\s]", "");
					float test = Float.parseFloat(stringValue);
					float total = test * products[i].getQuantity();
					String totalString = String.valueOf(total);
					Query updatetempIndenttransaction = entityManager
							.createNativeQuery("update temp_cart_indent_Transaction "
									+ " set TOTAL_USER_QTY = :TOTAL_USER_QTY ," + " BUYER_QTY = :TOTAL_USER_QTY ,"
									+ " Value =:total," + "	Created_date = :Created_date "
									+ " where year=:YEAR and month=:MONTH and cost_center=:COST_CENTER "
									//+ " and tempId=:DOC_NUMBER and ITEM=:ITEM");
									+ " and tempId=:DOC_NUMBER and PROD_NUMBER=:ITEM");
					updatetempIndenttransaction.setParameter("DOC_NUMBER", id);
					updatetempIndenttransaction.setParameter("total", totalString);
					updatetempIndenttransaction.setParameter("Created_date", formattedDate);
					updatetempIndenttransaction.setParameter("MONTH", MonthText);
					updatetempIndenttransaction.setParameter("COST_CENTER", userId);
					updatetempIndenttransaction.setParameter("YEAR", cFY);
					updatetempIndenttransaction.setParameter("ITEM", products[i].getProductID());
					//updatetempIndenttransaction.setParameter("ITEM", products[i].getProductName());
					updatetempIndenttransaction.setParameter("TOTAL_USER_QTY",
							Integer.valueOf(products[i].getQuantity()));

					response = updatetempIndenttransaction.executeUpdate();
					if (response != 0) {

						r = "Indent update SuccessFully";
					} else {
						Query InsertIndenttransaction = entityManager
								.createNativeQuery("INSERT INTO temp_cart_indent_Transaction"
										+ "(TempID,Created_date,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
										+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_QTY,Received_date,UnitPrice,PROD_NUMBER)"
										+ "VALUES(:DOC_NUMBER,:DOC_DATE,:MONTH,:COST_CENTER,:YEAR,:DEPARTMENT,:CREATEDBY,:ITEM,:TOTAL_USER_QTY,:VALUE,:CLOSE_DATE,"
										+ ":STATUS,null,0,:TOTAL_USER_QTY,null,null,0,null,null,:total,:PROD_NUMBER)");

						InsertIndenttransaction.setParameter("DOC_NUMBER", id);
						InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
						InsertIndenttransaction.setParameter("MONTH", MonthText);
						InsertIndenttransaction.setParameter("COST_CENTER", userId);
						InsertIndenttransaction.setParameter("YEAR", cFY);
						InsertIndenttransaction.setParameter("total", test);
						InsertIndenttransaction.setParameter("DEPARTMENT", userName);
						InsertIndenttransaction.setParameter("CREATEDBY", userId);
						InsertIndenttransaction.setParameter("ITEM", products[i].getProductName());
						InsertIndenttransaction.setParameter("PROD_NUMBER", products[i].getProductID());
						InsertIndenttransaction.setParameter("TOTAL_USER_QTY",
								Integer.valueOf(products[i].getQuantity()));
						InsertIndenttransaction.setParameter("VALUE", totalString);
						InsertIndenttransaction.setParameter("CLOSE_DATE", null);
						InsertIndenttransaction.setParameter("STATUS", "CREATED");
						response = InsertIndenttransaction.executeUpdate();
						r = "Indent update SuccessFully";
					}
				}

			} else {

				String maxId = gettempIndenttransactionId(userId, yearfromCal, monthFromCal);
				int n = products.length;
				for (int i = 0; i < n; i++) {
					String stringValue = products[i].getProductPrice();
					stringValue = stringValue.replaceAll("[₹\\s]", "");

					float test = Float.parseFloat(stringValue);
					float total = test * products[i].getQuantity();
					String totalString = String.valueOf(total);
					System.out.println("products   " + products[i]);
					Query InsertIndenttransaction = entityManager
							.createNativeQuery("INSERT INTO temp_cart_indent_Transaction"
									+ "(TempID,Created_date,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
									+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_QTY,Received_date,UnitPrice,PROD_NUMBER)"
									+ "VALUES(:DOC_NUMBER,:DOC_DATE,:MONTH,:COST_CENTER,:YEAR,:DEPARTMENT,:CREATEDBY,:ITEM,:TOTAL_USER_QTY,:total,:CLOSE_DATE,"
									+ ":STATUS,null,0,:TOTAL_USER_QTY,null,null,0,null,null,:VALUE,:PROD_NUMBER)");

					InsertIndenttransaction.setParameter("DOC_NUMBER", maxId);
					InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
					InsertIndenttransaction.setParameter("MONTH", MonthText);
					InsertIndenttransaction.setParameter("COST_CENTER", userId);
					InsertIndenttransaction.setParameter("YEAR", cFY);
					InsertIndenttransaction.setParameter("DEPARTMENT", userName);
					InsertIndenttransaction.setParameter("CREATEDBY", userId);
					InsertIndenttransaction.setParameter("total", total);
					InsertIndenttransaction.setParameter("ITEM", products[i].getProductName());
					InsertIndenttransaction.setParameter("PROD_NUMBER", products[i].getProductID());

					InsertIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));
					InsertIndenttransaction.setParameter("VALUE", stringValue);
					InsertIndenttransaction.setParameter("CLOSE_DATE", null);
					InsertIndenttransaction.setParameter("STATUS", "CREATED");
					response = InsertIndenttransaction.executeUpdate();
				}
			}
		} else {
			Query getintentID = entityManager.createNativeQuery(
					"select DISTINCT DOC_NUMBER from Indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

			getintentID.setParameter("year", cFY);
			getintentID.setParameter("month", MonthText);
			getintentID.setParameter("cost_center", userId);
			String id = (String) getintentID.getSingleResult();
			// return "Already Indent number generated " + id + " for this cost center " +
			// userId + " kindly update ";
			return "Indent is already generated for this cost center in current month. please modify the indent in the indent list.";

		}
		Query DeleteEmptyProducts = entityManager
				.createNativeQuery("delete from temp_cart_indent_Transaction where TOTAL_USER_QTY=0");
		DeleteEmptyProducts.executeUpdate();
		if (response != 0) {

			r = "Indent Creation SuccessFully";
		} else {
			r = "Update failed";
		}
		return r;
	}

	/*
	 * Gokul Indent DOCUMENT NUMBER GENERATE
	 */

	public String gettempIndenttransactionId(String UserID, String yearfromCal, String monthFromCal) {
		int cFY = Integer.valueOf(yearfromCal);
		int cFM = Integer.valueOf(monthFromCal);
		int index = 1;
		int maxOrderID_DB = 0;
		String OrderIdString = "";
		Query queryToGetMaximumOrderId = entityManager.createNativeQuery(
				"SELECT isnull(MAX(CAST(SUBSTRING(tempid,10,LEN(tempid)-9) AS INT)),0) FROM temp_cart_indent_Transaction");

		Optional<Integer> maxOrderId = null;
		try {
			maxOrderID_DB = (int) queryToGetMaximumOrderId.getSingleResult();
			maxOrderId = Optional.ofNullable(maxOrderID_DB);
			if (maxOrderId.isPresent()) {
				OrderIdString = Integer.toString(maxOrderId.get());
			}
			// int to String Conversion
		} catch (Exception e) {
			e.printStackTrace();
			e.getCause();
		}

		if (OrderIdString != null) {
			OrderIdString = UserID + cFY + cFM + ("00000" + (maxOrderID_DB + 1));
			return OrderIdString;
		}
		OrderIdString = UserID + cFY + cFM + ("00000" + index);

		return OrderIdString;
	}

	/**
	 * Gokul Get All Products for product Catelogue Update fetch
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> GetAllProductsByIndentNumber(String userId, String IndentNumber) {

		List<String> getProductsByIndent;

		Query selectQuery = entityManager
				.createNativeQuery("select tcit.PROD_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom, "
						+ "tcit.COST_CENTER,isnull(tcit.TOTAL_USER_QTY,0) as quantity,tcit.DOC_NUMBER from PRODUCT_MASTER pm "
						+ "inner join Indent_Transaction tcit on pm.PRODUCT_NUMBER = tcit.PROD_NUMBER"
						+ " and COST_CENTER=:userId and tcit.DOC_NUMBER = :IndentNumber  order by quantity desc");

		selectQuery.setParameter("userId", userId);
		selectQuery.setParameter("IndentNumber", IndentNumber);
		try {
			getProductsByIndent = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getProductsByIndent = null;
		}
		return getProductsByIndent;
	}

	/**
	 * Gokul Get All Products for product Catelogue Update fetch
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllProductsByIndent(String userId) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());
		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String Month = month.format(cal.getTime());
		
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(Month) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		List<String> getProductsByIndent;

		Query selectQuery = entityManager
				.createNativeQuery("select pm.PRODUCT_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom, \n"
						+ "it.COST_CENTER,isnull(it.TOTAL_USER_QTY,0)+isnull(tit.TOTAL_USER_QTY,0)  as totalquantity,it.DOC_NUMBER ,\n"
						+ "isnull(tit.TOTAL_USER_QTY,0)  as tempquantity,isnull(it.TOTAL_USER_QTY,0) quantityexists\n"
						+ "from PRODUCT_MASTER pm \n"
						+ "left join Indent_Transaction it on pm.PROD_NAME = it.item  and COST_CENTER=:userId and MONTH=:Month and YEAR=:year\n"
						+ "left join TEMP_Indent_Transaction tit on pm.PROD_NAME = tit.item  and tit.COST_CENTER=:userId  and tit.MONTH=:Month and tit.YEAR=:year order by quantityexists desc");

		selectQuery.setParameter("userId", userId);
		selectQuery.setParameter("Month", Month);
		;
		selectQuery.setParameter("year", yearfromCal);
		;
		try {
			getProductsByIndent = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getProductsByIndent = null;
		}
		return getProductsByIndent;
	}

	/*
	 * Gokul 07-07-2023 Indent Transaction save Calling on update indent not in
	 * creation.....................
	 */

	@Override
	public String IndentTransactionQuantitySave(Product[] products, String loginID, String userId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder messBuilder = new StringBuilder();
		String r = "";
		int response = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		Query checkIndentEntry = entityManager.createNativeQuery(
				"select count(DOC_NUMBER) from indent_Transaction where year=:year and month=:month and cost_center=:cost_center");
		checkIndentEntry.setParameter("year", cFY);
		checkIndentEntry.setParameter("month", MonthText);
		checkIndentEntry.setParameter("cost_center", userId);
		int count = (int) checkIndentEntry.getSingleResult();
		if (count != 0) {
			Query gettempID = entityManager.createNativeQuery(
					"select DISTINCT DOC_NUMBER from indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

			gettempID.setParameter("year", cFY);
			gettempID.setParameter("month", MonthText);
			gettempID.setParameter("cost_center", userId);
			String id = (String) gettempID.getSingleResult();
			int n = products.length;
			for (int i = 0; i < n; i++) {
				String stringValue = products[i].getProductPrice();
				stringValue = stringValue.replaceAll("[₹\\s]", "");
				float test = Float.parseFloat(stringValue);
				float total = test * products[i].getQuantity();
				String totalString = String.valueOf(total);
				Query updatetempIndenttransaction = entityManager
						.createNativeQuery("update indent_Transaction " + " set TOTAL_USER_QTY = :TOTAL_USER_QTY ,"
								+ " BUYER_QTY = :TOTAL_USER_QTY ," + " Value =:total," + "	modified_date = :DOC_DATE "
								+ " where year=:YEAR and month=:MONTH and cost_center=:COST_CENTER "
								+ " and DOC_NUMBER=:DOC_NUMBER and ITEM=:ITEM");
				updatetempIndenttransaction.setParameter("DOC_NUMBER", id);
				updatetempIndenttransaction.setParameter("total", total);
				updatetempIndenttransaction.setParameter("DOC_DATE", formattedDate);
				updatetempIndenttransaction.setParameter("MONTH", MonthText);
				updatetempIndenttransaction.setParameter("COST_CENTER", userId);
				updatetempIndenttransaction.setParameter("YEAR", cFY);
				updatetempIndenttransaction.setParameter("ITEM", products[i].getProductName());
				updatetempIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));

				response = updatetempIndenttransaction.executeUpdate();
				if (response != 0) {

					r = "Indent update SuccessFully";
				} else {
					Query InsertIndenttransaction = entityManager.createNativeQuery("INSERT INTO indent_Transaction"
							+ "(DOC_NUMBER,DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
							+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_QTY,Received_date,UnitPrice)"
							+ "VALUES(:DOC_NUMBER,:DOC_DATE,:MONTH,:COST_CENTER,:YEAR,:DEPARTMENT,:CREATEDBY,:ITEM,:TOTAL_USER_QTY,:total,:CLOSE_DATE,"
							+ ":STATUS,null,0,:TOTAL_USER_QTY,null,null,0,null,null,:VALUE)");

					InsertIndenttransaction.setParameter("DOC_NUMBER", id);
					InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
					InsertIndenttransaction.setParameter("MONTH", MonthText);
					InsertIndenttransaction.setParameter("COST_CENTER", userId);
					InsertIndenttransaction.setParameter("YEAR", cFY);
					InsertIndenttransaction.setParameter("DEPARTMENT", userName);
					InsertIndenttransaction.setParameter("CREATEDBY", userId);
					InsertIndenttransaction.setParameter("total", total);
					InsertIndenttransaction.setParameter("ITEM", products[i].getProductName());
					InsertIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));
					InsertIndenttransaction.setParameter("VALUE", stringValue);
					InsertIndenttransaction.setParameter("CLOSE_DATE", null);
					InsertIndenttransaction.setParameter("STATUS", "CREATED");
					response = InsertIndenttransaction.executeUpdate();
					r = "insert sucessfully";
				}
			}

		} else {

			String maxId = gettempIndenttransactionId(userId, yearfromCal, monthFromCal);
			int n = products.length;
			for (int i = 0; i < n; i++) {
				String stringValue = products[i].getProductPrice();
				stringValue = stringValue.replaceAll("[₹\\s]", "");
				float test = Float.parseFloat(stringValue);
				float total = test * products[i].getQuantity();
				String totalString = String.valueOf(total);
				// System.out.println("products " + products[i]);
				Query InsertIndenttransaction = entityManager.createNativeQuery("INSERT INTO indent_Transaction"
						+ "(DOC_NUMBER,DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
						+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_QTY,Received_date,UnitPrice)"
						+ "VALUES(:DOC_NUMBER,:DOC_DATE,:MONTH,:COST_CENTER,:YEAR,:DEPARTMENT,:CREATEDBY,:ITEM,:TOTAL_USER_QTY,:total,:CLOSE_DATE,"
						+ ":STATUS,null,0,:TOTAL_USER_QTY,null,null,0,null,null,:VALUE)");

				InsertIndenttransaction.setParameter("total", total);
				InsertIndenttransaction.setParameter("DOC_NUMBER", maxId);
				InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
				InsertIndenttransaction.setParameter("MONTH", MonthText);
				InsertIndenttransaction.setParameter("COST_CENTER", userId);
				InsertIndenttransaction.setParameter("YEAR", cFY);
				InsertIndenttransaction.setParameter("DEPARTMENT", userName);
				InsertIndenttransaction.setParameter("CREATEDBY", userId);
				InsertIndenttransaction.setParameter("ITEM", products[i].getProductName());
				InsertIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));
				InsertIndenttransaction.setParameter("VALUE", stringValue);
				InsertIndenttransaction.setParameter("CLOSE_DATE", null);
				InsertIndenttransaction.setParameter("STATUS", "CREATED");
				response = InsertIndenttransaction.executeUpdate();
			}
		}

		Query gettotal = entityManager.createNativeQuery("select ISNULL(SUM(VALUE), 0) from Indent_Transaction \r\n"
				+ " where COST_CENTER=:userId and TOTAL_USER_QTY<>0 and year=:cFY and month=:monthx");

		gettotal.setParameter("userId", userId);
		gettotal.setParameter("cFY", cFY);
		gettotal.setParameter("monthx", MonthText);
		BigDecimal total = (BigDecimal) gettotal.getSingleResult();

		if (MonthText.equalsIgnoreCase("July")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set july=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();
		} else if (MonthText.equalsIgnoreCase("August")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set August=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("September")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set September=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("October")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set October=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("November")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set November=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("December")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set December=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("January")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set January=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("February")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set February=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("March")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set March=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("April")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set April=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("May")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set May=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("June")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set June=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		}

		Query deletetempIndent = entityManager
				.createNativeQuery("delete from  indent_Transaction where TOTAL_USER_QTY=0");
		deletetempIndent.executeUpdate();

		if (response != 0) {

			r = "Indent Creation SuccessFully";
		} else {
			r = "Update failed";
		}
		return r;
	}

	@Override
	public String IndentQtyChange(Product[] products, String loginID, String userId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder messBuilder = new StringBuilder();
		String r = "";
		int response = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);

		Query checkIndentEntry = entityManager.createNativeQuery(
				"select count(DOC_NUMBER) from indent_Transaction where year=:year and month=:month and cost_center=:cost_center");
		checkIndentEntry.setParameter("year", cFY);
		checkIndentEntry.setParameter("month", MonthText);
		checkIndentEntry.setParameter("cost_center", userId);
		int count = (int) checkIndentEntry.getSingleResult();
		if (count != 0) {
			Query gettempID = entityManager.createNativeQuery(
					"select DISTINCT DOC_NUMBER from indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

			gettempID.setParameter("year", cFY);
			gettempID.setParameter("month", MonthText);
			gettempID.setParameter("cost_center", userId);
			String id = (String) gettempID.getSingleResult();
			int n = products.length;
			for (int i = 0; i < n; i++) {
				String stringValue = products[i].getProductPrice();
				stringValue = stringValue.replaceAll("[₹\\s]", "");
				float test = Float.parseFloat(stringValue);
				float total = test * products[i].getQuantity();
				String totalString = String.valueOf(total);
				Query updatetempIndenttransaction = entityManager
						.createNativeQuery("update indent_Transaction " + " set TOTAL_USER_QTY = :TOTAL_USER_QTY ,"
								+ " BUYER_QTY = :TOTAL_USER_QTY ," + " Value =:total," + "	modified_date = :DOC_DATE "
								+ " where year=:YEAR and month=:MONTH and cost_center=:COST_CENTER "
								+ " and DOC_NUMBER=:DOC_NUMBER and ITEM=:ITEM");
				updatetempIndenttransaction.setParameter("DOC_NUMBER", id);
				updatetempIndenttransaction.setParameter("total", total);
				updatetempIndenttransaction.setParameter("DOC_DATE", formattedDate);
				updatetempIndenttransaction.setParameter("MONTH", MonthText);
				updatetempIndenttransaction.setParameter("COST_CENTER", userId);
				updatetempIndenttransaction.setParameter("YEAR", cFY);
				updatetempIndenttransaction.setParameter("ITEM", products[i].getProductName());
				updatetempIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));

				response = updatetempIndenttransaction.executeUpdate();
				if (response != 0) {

					return r = "Indent update SuccessFully";
				} else {
					Query InsertIndenttransaction = entityManager.createNativeQuery("INSERT INTO indent_Transaction"
							+ "(DOC_NUMBER,DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
							+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_QTY,Received_date,UnitPrice)"
							+ "VALUES(:DOC_NUMBER,:DOC_DATE,:MONTH,:COST_CENTER,:YEAR,:DEPARTMENT,:CREATEDBY,:ITEM,:TOTAL_USER_QTY,:total,:CLOSE_DATE,"
							+ ":STATUS,null,0,:TOTAL_USER_QTY,null,null,0,null,null,:VALUE)");

					InsertIndenttransaction.setParameter("DOC_NUMBER", id);
					InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
					InsertIndenttransaction.setParameter("MONTH", MonthText);
					InsertIndenttransaction.setParameter("COST_CENTER", userId);
					InsertIndenttransaction.setParameter("YEAR", cFY);
					InsertIndenttransaction.setParameter("DEPARTMENT", userName);
					InsertIndenttransaction.setParameter("CREATEDBY", userId);
					InsertIndenttransaction.setParameter("total", total);
					InsertIndenttransaction.setParameter("ITEM", products[i].getProductName());
					InsertIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));
					InsertIndenttransaction.setParameter("VALUE", stringValue);
					InsertIndenttransaction.setParameter("CLOSE_DATE", null);
					InsertIndenttransaction.setParameter("STATUS", "CREATED");
					response = InsertIndenttransaction.executeUpdate();
					r = "insert sucessfully";
				}
			}

		} else {

			String maxId = gettempIndenttransactionId(userId, yearfromCal, monthFromCal);
			int n = products.length;
			for (int i = 0; i < n; i++) {
				String stringValue = products[i].getProductPrice();
				stringValue = stringValue.replaceAll("[₹\\s]", "");
				float test = Float.parseFloat(stringValue);
				float total = test * products[i].getQuantity();
				String totalString = String.valueOf(total);
				// System.out.println("products " + products[i]);
				Query InsertIndenttransaction = entityManager.createNativeQuery("INSERT INTO indent_Transaction"
						+ "(DOC_NUMBER,DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
						+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_QTY,Received_date,UnitPrice)"
						+ "VALUES(:DOC_NUMBER,:DOC_DATE,:MONTH,:COST_CENTER,:YEAR,:DEPARTMENT,:CREATEDBY,:ITEM,:TOTAL_USER_QTY,:total,:CLOSE_DATE,"
						+ ":STATUS,null,0,:TOTAL_USER_QTY,null,null,0,null,null,:VALUE)");

				InsertIndenttransaction.setParameter("total", total);
				InsertIndenttransaction.setParameter("DOC_NUMBER", maxId);
				InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
				InsertIndenttransaction.setParameter("MONTH", MonthText);
				InsertIndenttransaction.setParameter("COST_CENTER", userId);
				InsertIndenttransaction.setParameter("YEAR", cFY);
				InsertIndenttransaction.setParameter("DEPARTMENT", userName);
				InsertIndenttransaction.setParameter("CREATEDBY", userId);
				InsertIndenttransaction.setParameter("ITEM", products[i].getProductName());
				InsertIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));
				InsertIndenttransaction.setParameter("VALUE", stringValue);
				InsertIndenttransaction.setParameter("CLOSE_DATE", null);
				InsertIndenttransaction.setParameter("STATUS", "CREATED");
				response = InsertIndenttransaction.executeUpdate();
			}
		}

		Query gettotal = entityManager.createNativeQuery("select ISNULL(SUM(VALUE), 0) from Indent_Transaction \r\n"
				+ " where COST_CENTER=:userId and TOTAL_USER_QTY<>0 and year=:cFY and month=:monthx");

		gettotal.setParameter("userId", userId);
		gettotal.setParameter("cFY", cFY);
		gettotal.setParameter("monthx", MonthText);
		BigDecimal total = (BigDecimal) gettotal.getSingleResult();

		if (MonthText.equalsIgnoreCase("July")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set july=july+:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();
		} else if (MonthText.equalsIgnoreCase("August")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set August=August+:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("September")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set September=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("October")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set October=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("November")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set November=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("December")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set December=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("January")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set January=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("February")) {
			Query updateBudgetMaster = entityManager.createNativeQuery(
					"update budget_master set February=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("March")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set March=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("April")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set April=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("May")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set May=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		} else if (MonthText.equalsIgnoreCase("June")) {
			Query updateBudgetMaster = entityManager
					.createNativeQuery("update budget_master set June=:total" + " where ccid=:userId and year=:cFY");

			updateBudgetMaster.setParameter("total", total);
			updateBudgetMaster.setParameter("userId", userId);
			updateBudgetMaster.setParameter("cFY", yearfromCal);
			updateBudgetMaster.executeUpdate();

		}

		/*
		 * Query deletetempIndent = entityManager
		 * .createNativeQuery("delete from  indent_Transaction where TOTAL_USER_QTY=0");
		 * deletetempIndent.executeUpdate();
		 */

		if (response != 0) {

			r = "Indent Creation SuccessFully";
		} else {
			r = "Update failed";
		}
		return r;
	}

	/**
	 * Gokul Get All Products for product Catelogue Update fetch
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> GetAddMoreProducts(String userId) {

		List<String> getProductsByIndent;

		Query selectQuery = entityManager
				.createNativeQuery("select pm.PRODUCT_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom, "
						+ "tcit.COST_CENTER,isnull(tcit.TOTAL_USER_QTY,0) as quantity,tcit.DOC_NUMBER from PRODUCT_MASTER pm "
						+ "left join Indent_Transaction tcit on pm.PROD_NAME = tcit.item "
						+ "where COST_CENTER=:userId order by quantity desc");

		selectQuery.setParameter("userId", userId);
		try {
			getProductsByIndent = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getProductsByIndent = null;
		}
		return getProductsByIndent;
	}

	/**
	 * Gokul Get budget details
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getBudgetDetails(String userId) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());
		List<Object> getBudgetDetails;
		String currentYearForPoEntry = null;
		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.parseInt(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		 currentYearForPoEntry = year_Date.format(cal.getTime());
		    System.out.println("yearfromCal"+yearfromCal);
		}
//		Query selectQuery = entityManager.createNativeQuery(
//				" SELECT bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,(bm.BudValueRsL-((\n"
//						+ "CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) \n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) +ISNULL(bm.July, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April, 0) + ISNULL(bm.MaY, 0) +ISNULL( bm.june, 0) + ISNULL(bm.July, 0) + ISNULL(bm.August , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)+ ISNULL(bm.February , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'march' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)+ ISNULL(bm.February , 0)+ ISNULL(bm.March , 0)\n"
//						+ "END) + isnull(poe.POAmount,0)) ) as BalanceValue,\n" + "(((\n"
//						+ "CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April,0)  \n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) \n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) \n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April, 0) + ISNULL(bm.MaY, 0) +ISNULL( bm.june, 0) + ISNULL(bm.July, 0) \n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'march' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)+ ISNULL(bm.February , 0)\n"
//						+ "END)  + isnull(poe.POAmount,0) + CASE\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.may , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.june  , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.July  , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.August, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.September , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.October , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.November , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.December, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.January, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.February, 0)\n"
//						+ "END)) as cumulative_budget,\n" + "(CASE\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.may , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.june  , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.July  , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.August, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.September , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.October , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.November , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.December, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.January, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.February, 0)\n"
//						+ "END) AS currentIndentValue,\n" + "(BudValueRsL/12)-((CASE\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.may , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.june  , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.July  , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.August, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.September , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.October , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.November , 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.December, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.January, 0)\n"
//						+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.February, 0)\n"
//						+ "END) + isnull(poe.POAmount,0))  AS Month_Balance,\n" + "(\n"
//						+ "        SELECT SUM(BUYER_QTY) \n" + "        FROM Indent_Transaction \n"
//						+ "        WHERE COST_CENTER = :userId  \n" + "        AND MONTH = :MonthText	\n"
//						+ "    ) AS BuyerQuantity\n" + "FROM BUDGET_MASTER bm \n"
//						+ "left join PO_Entry poe on poe.COST_CENTER=bm.CCID AND POE.Year=BM.Year and poe.MONTH=:MonthText \n"
//						+ "WHERE CCID=:userId AND bm.YEAR=:year\n"
//						+ " GROUP BY bm.ccid,bm.Year,bm.CostCenterDescription,POAmount,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July, bm.August,bm.September,\n"
//						+ "bm.October,bm.November,bm.December,January,February,March\n" + "");

		Query selectQuery = entityManager.createNativeQuery("SELECT bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,(bm.BudValueRsL-((\n"
				+ "CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) \n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) +ISNULL(bm.July, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April, 0) + ISNULL(bm.MaY, 0) +ISNULL( bm.june, 0) + ISNULL(bm.July, 0) + ISNULL(bm.August , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)+ ISNULL(bm.February , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'march' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)+ ISNULL(bm.February , 0)+ ISNULL(bm.March , 0)\n"
				+ "END) + isnull(sum(poe.POAmount),0) + isnull(sum(poforCurYr.POAmount),0)) ) as BalanceValue,\n"
				+ "(((\n"
				+ "CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) \n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) +ISNULL(bm.July, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April, 0) + ISNULL(bm.MaY, 0) +ISNULL( bm.june, 0) + ISNULL(bm.July, 0) + ISNULL(bm.August , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)+ ISNULL(bm.February , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'march' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)+ ISNULL(bm.September , 0)+ ISNULL(bm.October , 0)+ ISNULL(bm.November , 0)+ ISNULL(bm.December , 0)+ ISNULL(bm.January , 0)+ ISNULL(bm.February , 0)+ ISNULL(bm.March , 0)\n"
				+ "END) \n"
				+ "+ isnull(sum(poe.POAmount),0) + isnull(sum(poforCurYr.POAmount),0)\n"
				+ "\n"
				+ ")) as cumulative_indent,\n"
				+ "(CASE\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.may , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.june  , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.July  , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.August, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.September , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.October , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.November , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.December, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.January, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.February, 0)\n"
				+ "END) AS currentIndentValue,\n"
				+ "(BudValueRsL/12)-((CASE\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.may , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.june  , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.July  , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.August, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.September , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.October , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.November , 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.December, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.January, 0)\n"
				+ "WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.February, 0)\n"
				+ "END) + isnull(sum(poe.POAmount),0))  AS Month_Balance,\n"
				+ "(\n"
				+ "        SELECT SUM(BUYER_QTY) \n"
				+ "        FROM Indent_Transaction \n"
				+ "        WHERE COST_CENTER = :userId \n"
				+ "        AND MONTH =:MonthText	\n"
				+ "    ) AS BuyerQuantity\n"
				+ "FROM BUDGET_MASTER bm \n"
				+ "left join PO_Entry poe on poe.COST_CENTER=bm.CCID AND POE.Year=BM.Year and poe.MONTHNUMBER > 3 \n"
				+ "AND poe.MONTHNUMBER <=:decemberMOnthNum  \n"
				+ "left join PO_Entry poforCurYr on poforCurYr.COST_CENTER=bm.CCID AND poforCurYr.Year=:currentYearForPoEntry\n"
				+ "AND poforCurYr.MONTHNUMBER <=:poforCurYr \n"
				+ "WHERE CCID=:userId AND bm.YEAR=:year\n"
				+ " GROUP BY bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July, bm.August,bm.September,\n"
				+ "bm.October,bm.November,bm.December,January,February,March");
		
		selectQuery.setParameter("userId", userId);
		selectQuery.setParameter("decemberMOnthNum", getMonthNumber(MonthText));
		selectQuery.setParameter("poforCurYr", getMonthNumber(MonthText));
		selectQuery.setParameter("currentYearForPoEntry", currentYearForPoEntry);
		selectQuery.setParameter("year", yearfromCal);
		selectQuery.setParameter("MonthText", MonthText);
		;
		try {
			getBudgetDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getBudgetDetails = null;
		}
		return getBudgetDetails;
	}

	/**
	 * Gokul Get Products by category indent update
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProductByCategoryIndentUpdate(String category, String userId) {

		List<String> getStoreManagerDetials;

		String[] categoryArray = convertToQuery(category);

		Query selectQuery = entityManager.createNativeQuery(
				"select DISTINCT pm.PRODUCT_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom,  tcit.COST_CENTER, "
						+ "isnull(tcit.TOTAL_USER_QTY,0) as quantity from PRODUCT_MASTER pm "
						+ "left join indent_Transaction tcit on pm.PROD_NAME = tcit.item where COST_CENTER=:userId and category IN( :category) "
						+ "order by quantity desc ");
		List<String> brandList = Arrays.asList(categoryArray);
		selectQuery.setParameter("category", brandList);
		selectQuery.setParameter("userId", userId);
		try {
			if (categoryArray.length != 0) {
				getStoreManagerDetials = selectQuery.getResultList();
			} else {
				// getStoreManagerDetials = null;
				getStoreManagerDetials = getAllProductsByIndent(userId);
			}
		} catch (Exception e) {

			e.printStackTrace();
			getStoreManagerDetials = null;
		}
		return getStoreManagerDetials;
	}

	/**
	 * Gokul Get Products by category indent update filter search
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProductByCategoryaAddMore(String category, String userId) {

		List<String> getStoreManagerDetials;

		String[] categoryArray = convertToQuery(category);

		Query selectQuery = entityManager.createNativeQuery(
				"select DISTINCT pm.PRODUCT_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom,  tcit.COST_CENTER, "
						+ "isnull(tcit.TOTAL_USER_QTY,0) as quantity,tcit.DOC_NUMBER from PRODUCT_MASTER pm "
						+ "left join TEMP_Indent_Transaction tcit on pm.PROD_NAME = tcit.item and COST_CENTER=:userId "
						+ " where category IN( :category) AND NOT EXISTS (\n"
						+ "        SELECT 1\n"
						+ "        FROM Indent_Transaction tit\n"
						+ "        WHERE pm.PROD_NAME = tit.item and COST_CENTER = :userId\n"
						+ "    )  " + "order by quantity desc ");
		List<String> brandList = Arrays.asList(categoryArray);
		selectQuery.setParameter("category", brandList);
		selectQuery.setParameter("userId", userId);
		try {
			if (categoryArray.length != 0) {
				getStoreManagerDetials = selectQuery.getResultList();
			} else {
				// getStoreManagerDetials = null;
				getStoreManagerDetials = getAllProductsByIndent(userId);
			}
		} catch (Exception e) {

			e.printStackTrace();
			getStoreManagerDetials = null;
		}
		return getStoreManagerDetials;
	}

	/*
	 * Gokul temp cart Indent Transaction update save
	 */

	@Override
	public String tempIndentUpdateCreation(Product[] products, String loginID, String userId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder messBuilder = new StringBuilder();
		String r = "";
		int response = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}

//			 Query checkIndentTransaction = entityManager.
//					 createNativeQuery("select count(DOC_NUMBER) from indent_Transaction where year=:year and month=:month and cost_center=:cost_center");
//					 
//			 checkIndentTransaction.setParameter("year", cFY);
//			 checkIndentTransaction.setParameter("month", MonthText);
//			 checkIndentTransaction.setParameter("cost_center", userId); 
//			 int count11 = (int) checkIndentTransaction.getSingleResult();
//			if(count11==0) {
		String id = "";
		Query gettempID = entityManager.createNativeQuery(
				"select DISTINCT DOC_NUMBER from indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

		gettempID.setParameter("year", cFY);
		gettempID.setParameter("month", MonthText);
		gettempID.setParameter("cost_center", userId);
		id = (String) gettempID.getSingleResult();
		Query checkIndentEntry = entityManager.createNativeQuery(
				"select count(DOC_NUMBER) from temp_indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

		checkIndentEntry.setParameter("year", cFY);
		checkIndentEntry.setParameter("month", MonthText);
		checkIndentEntry.setParameter("cost_center", userId);
		int count = (int) checkIndentEntry.getSingleResult();

		if (count != 0) {

			int n = products.length;
			for (int i = 0; i < n; i++) {
				String productPrice = products[i].getProductPrice();
				productPrice = productPrice.replaceAll("[₹\\s]", "");
				String totalString = "0";
				if (productPrice != "") {
					float Price = Float.parseFloat(productPrice);
					float total = Price * products[i].getQuantity();
					totalString = String.valueOf(total);
				} else {
					totalString = "0.00";
				}
				Query updatetempIndenttransaction = entityManager
						.createNativeQuery("update temp_indent_Transaction " + " set TOTAL_USER_QTY = :TOTAL_USER_QTY ,"
								+ " BUYER_QTY = :TOTAL_USER_QTY," + "	DOC_DATE = :Created_date ," + " Value=:Value"
								+ " where year=:YEAR and month=:MONTH and cost_center=:COST_CENTER "
								+ " and DOC_NUMBER=:DOC_NUMBER and ITEM=:ITEM and PROD_NUMBER=:PROD_NUMBER ");
				updatetempIndenttransaction.setParameter("DOC_NUMBER", id);
				updatetempIndenttransaction.setParameter("Created_date", formattedDate);
				updatetempIndenttransaction.setParameter("MONTH", MonthText);
				updatetempIndenttransaction.setParameter("COST_CENTER", userId);
				updatetempIndenttransaction.setParameter("YEAR", cFY);
				updatetempIndenttransaction.setParameter("ITEM", products[i].getProductName());
				updatetempIndenttransaction.setParameter("PROD_NUMBER", products[i].getProductID());
				updatetempIndenttransaction.setParameter("TOTAL_USER_QTY", products[i].getQuantity());
				updatetempIndenttransaction.setParameter("Value", totalString);

				response = updatetempIndenttransaction.executeUpdate();
				if (response != 0) {

					r = "Indent update SuccessFully";
				} else {

					Query InsertIndenttransaction = entityManager
							.createNativeQuery("INSERT INTO temp_indent_Transaction"
									+ "(DOC_NUMBER,DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
									+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_QTY,Received_date,UnitPrice,PROD_NUMBER)"
									+ "VALUES(:DOC_NUMBER,:DOC_DATE,:MONTH,:COST_CENTER,:YEAR,:DEPARTMENT,:CREATEDBY,:ITEM,:TOTAL_USER_QTY,:VALUE,:CLOSE_DATE,"
									+ ":STATUS,null,0,:TOTAL_USER_QTY,null,null,0,null,null,:unitPrice,:PROD_NUMBER)");

					InsertIndenttransaction.setParameter("DOC_NUMBER", id);
					InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
					InsertIndenttransaction.setParameter("MONTH", MonthText);
					InsertIndenttransaction.setParameter("COST_CENTER", userId);
					InsertIndenttransaction.setParameter("YEAR", cFY);
					InsertIndenttransaction.setParameter("DEPARTMENT", userName);
					InsertIndenttransaction.setParameter("CREATEDBY", userId);
					InsertIndenttransaction.setParameter("ITEM", products[i].getProductName());
					InsertIndenttransaction.setParameter("PROD_NUMBER", products[i].getProductID());
					InsertIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));
					InsertIndenttransaction.setParameter("unitPrice", productPrice);
					InsertIndenttransaction.setParameter("VALUE", totalString);
					InsertIndenttransaction.setParameter("CLOSE_DATE", null);
					InsertIndenttransaction.setParameter("STATUS", "CREATED");
					response = InsertIndenttransaction.executeUpdate();
					r = "Indent update SuccessFully";
				}
			}

		} else {

			// String maxId = gettempIndenttransactionId(userId,yearfromCal,monthFromCal);
			int n = products.length;
			for (int i = 0; i < n; i++) {
				String productPrice = products[i].getProductPrice();
				productPrice = productPrice.replaceAll("[₹\\s]", "");
				String totalString = "0";
				if (productPrice != "") {
					float Price = Float.parseFloat(productPrice);
					float total = Price * products[i].getQuantity();
					totalString = String.valueOf(total);
				} else {
					totalString = "0.00";
				}
				Query InsertIndenttransaction = entityManager.createNativeQuery("INSERT INTO temp_indent_Transaction"
						+ "(DOC_NUMBER,DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
						+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_QTY,Received_date,UnitPrice,PROD_NUMBER)"
						+ "VALUES(:DOC_NUMBER,:DOC_DATE,:MONTH,:COST_CENTER,:YEAR,:DEPARTMENT,:CREATEDBY,:ITEM,:TOTAL_USER_QTY,:VALUE,:CLOSE_DATE,"
						+ ":STATUS,null,0,:TOTAL_USER_QTY,null,null,0,null,null,:unitPrice,:PROD_NUMBER)");

				InsertIndenttransaction.setParameter("DOC_NUMBER", id);
				InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
				InsertIndenttransaction.setParameter("MONTH", MonthText);
				InsertIndenttransaction.setParameter("COST_CENTER", userId);
				InsertIndenttransaction.setParameter("YEAR", cFY);
				InsertIndenttransaction.setParameter("DEPARTMENT", userName);
				InsertIndenttransaction.setParameter("CREATEDBY", userId);
				InsertIndenttransaction.setParameter("ITEM", products[i].getProductName());
				InsertIndenttransaction.setParameter("PROD_NUMBER", products[i].getProductID());

				InsertIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));
				InsertIndenttransaction.setParameter("VALUE", totalString);
				InsertIndenttransaction.setParameter("unitPrice", productPrice);
				InsertIndenttransaction.setParameter("CLOSE_DATE", null);
				InsertIndenttransaction.setParameter("STATUS", "CREATED");
				response = InsertIndenttransaction.executeUpdate();
			}
		}

		if (response != 0) {

			r = "Indent Creation SuccessFully";
		} else {
			r = "Update failed";
		}
		return r;
	}

	/*
	 * Gokul Indent Transaction update from add more product
	 */
	@Override
	public String IndentTransactionUpdate(Product[] products, String loginID, String userId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder messBuilder = new StringBuilder();
		String r = "";
		int response = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		Query gettempID = entityManager.createNativeQuery(
				"select DISTINCT DOC_NUMBER from temp_indent_Transaction where year=:year and month=:month and cost_center=:cost_center");

		gettempID.setParameter("year", cFY);
		gettempID.setParameter("month", MonthText);
		gettempID.setParameter("cost_center", userId);
		Object tempid = null;
		try {
			tempid = gettempID.getSingleResult();
		} catch (NoResultException er) {
			if (tempid == null) {
				r = "Pls add or delete qty to change indent.";
				return r;
			}
		}

		Query InsertIndenttransaction = entityManager.createNativeQuery("INSERT INTO Indent_Transaction"
				+ "(DOC_NUMBER,DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE"
				+ ",STATUS,BUYER_id,BUYER_CONFIRMATION,BUYER_QTY,BUYER_CONFIRMATION_DATE,TRYMNGTID,ISTRYMNGT,RECEIVED_TMT_QTY,Received_date,UnitPrice,PROD_NUMBER) "
				+ "select DOC_NUMBER,:DOC_DATE,MONTH,COST_CENTER,YEAR,DEPARTMENT,CREATEDBY,ITEM,TOTAL_USER_QTY,VALUE,CLOSE_DATE, "
				+ "STATUS,null,0,TOTAL_USER_QTY,null,null,0,null,null,UnitPrice,PROD_NUMBER from  temp_indent_Transaction where DOC_NUMBER=:tempid");

		InsertIndenttransaction.setParameter("tempid", tempid);
		InsertIndenttransaction.setParameter("DOC_DATE", formattedDate);
		response = InsertIndenttransaction.executeUpdate();

		Query deletetempIndent = entityManager
				.createNativeQuery("delete from  temp_indent_Transaction where DOC_NUMBER=:tempid");

		deletetempIndent.setParameter("tempid", tempid);
		deletetempIndent.executeUpdate();

		Query deleteIndent = entityManager.createNativeQuery("delete from  indent_Transaction where TOTAL_USER_QTY=0");
		deleteIndent.executeUpdate();

		Query gettotal = entityManager.createNativeQuery("update budget_master  set " + MonthText
				+ "=(select isnull(sum(buycc.TOTAL_USER_QTY*buycc.UnitPrice),0) from INDENT_TRANSACTION buycc "
				+ "where  buycc.year=:cFY and buycc.MONTH=:MONTH and buycc.cost_center=:userId) "
				+ "where budget_master.ccid =" + userId + " and budget_master.Year=:cFY");

		gettotal.setParameter("MONTH", MonthText);
		gettotal.setParameter("userId", userId);
		gettotal.setParameter("cFY", cFY);
		/// BigDecimal total=(BigDecimal) gettotal.getSingleResult();

		gettotal.executeUpdate();

		// }
		if (response != 0) {

			r = "Indent Updated SuccessFully";
		} else {
			r = "Update failed";
		}

		return r;
	}

	/*
	 * Created By Gokul Created On 12-07-2023 purpose get buyer indent list for
	 * buyer edit
	 */

	@Override
	public List<Object> getBuyerIndentList(String Year, String Month) {

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
		    System.out.println("yearfromCal"+yearfromCal+MonthText);
		}
		String finalcc = getAllIndentedCostCenters(yearfromCal, Month);

		List<Object> getAllUserDetails = null;
		if (finalcc.length() == 2) {
			return getAllUserDetails;
		}
		Query getresultlist = entityManager.createNativeQuery(
//					"sELECT PROD_NAME, MAKE, UOM, [1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
//					+ "		[1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
//					+ "		[1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
//					+ "		[1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
//					+ "		[1552],[1554],[1555],[1557],[1558],[1559],[7646],ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue \n"
//					+ "		FROM (\n"
//					+ "		SELECT PROD_NAME, isnull(MAKE,'') MAKE, UOM, COST_CENTER, ISNULL(BUYER_QTY , 0) AS cost_value,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
//					+ "		FROM PRODUCT_MASTER pm \n"
//					+ "		left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
//					+ "		WHERE COST_CENTER IS NOT NULL and month=DATENAME(MONTH, GETDATE()) and year= format(GETDATE(),'yyyy')\n"
//					+ "		GROUP BY PROD_NAME, MAKE, UOM, COST_CENTER,BUYER_QTY,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
//					+ "		) AS src\n"
//					+ "		PIVOT (MAX(cost_value)\n"
//					+ "		FOR COST_CENTER IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
//					+ "		[1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
//					+ "		[1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
//					+ "		[1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
//					+ "		[1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS pivottable\n"
//					+ "		order by PROD_NAME desc");

				"sELECT PROD_NAME, MAKE, UOM," + finalcc + ",ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue \n"
						+ "		FROM (\n"
						+ "		SELECT PROD_NAME, isnull(MAKE,'') MAKE, UOM, COST_CENTER, ISNULL(BUYER_QTY , 0) AS cost_value,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
						+ "		FROM PRODUCT_MASTER pm \n"
						+ "		left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
						+ "		WHERE COST_CENTER IS NOT NULL and month=:Month and year=:Year\n"
						+ "		GROUP BY PROD_NAME, MAKE, UOM, COST_CENTER,BUYER_QTY,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
						+ "		) AS src\n" + "		PIVOT (MAX(cost_value)\n" + "		FOR COST_CENTER IN (" + finalcc
						+ ")) AS pivottable\n" + "		order by PROD_NAME desc");

		getresultlist.setParameter("Month", Month);
		getresultlist.setParameter("Year", Year);

		try {
			getAllUserDetails = getresultlist.getResultList();

		} catch (HibernateException e) {

			e.printStackTrace();
			getAllUserDetails = null;
		}
		return getAllUserDetails;
	}

	@Override
	public String getAllIndentedCostCenters(String Year, String Month) {

		System.out.println("Year and month is" + Year + Month);
		List<String> getAllUserDetails;
		String finalcc = "[";
		Query getresultlist = entityManager.createNativeQuery(
				"select distinct COST_CENTER from Indent_Transaction where MONTH=:Month and YEAR=:Year");
		getresultlist.setParameter("Month", Month);
		getresultlist.setParameter("Year", Year);
		try {
			getAllUserDetails = getresultlist.getResultList();
			System.out.println("finalresult" + Year + Month);
			finalcc = String.join("],[", getAllUserDetails);
			System.out.println(finalcc);

		} catch (HibernateException e) {
			e.printStackTrace();
			getAllUserDetails = null;
		}
		return "[" + finalcc + "]";
	}

	@Override
	public List<String> getAllcolumnlength(String Year, String Month) {

		List<String> getAllUserDetails;
		String finalcc = "[";
		Query getresultlist = entityManager.createNativeQuery(
				"select distinct COST_CENTER from Indent_Transaction where MONTH=:Month and YEAR=:Year");
		getresultlist.setParameter("Month", Month);
		getresultlist.setParameter("Year", Year);
		try {
			getAllUserDetails = getresultlist.getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();
			getAllUserDetails = null;
		}
		return getAllUserDetails;
	}

	/*
	 * Created By Gokul Created On 19-07-2023 purpose get buyer indent footer list
	 * for buyer edit
	 */

	/*
	 * @Override public List<Object> getBuyerFooterList() { List<Object>
	 * BuyerFooterList; Query getresultlist = entityManager.createNativeQuery(
	 * " SELECT 'Budget Value Yearly(Rs)' title,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646]\r\n" +
	 * " FROm(SELECT CCID, BudValueRsL FROM BUDGET_MASTer ) AS SourceTable\r\n" +
	 * " PIVOT( MAX(BudValueRsL) FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable1\r\n" +
	 * " UNION ALL\r\n" +
	 * " SELECT 'Average Monthly Budget(Rs)' title,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646] FROM\r\n" +
	 * " (SELECT CCID, monthlybudget FROM BUDGET_MASTER) AS SourceTable\r\n" +
	 * " PIVOT( MAX(monthlybudget)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable2\r\n" +
	 * " UNION ALL\r\n" +
	 * " SELECT 'Indent val. by stn portal' title,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646] FROM\r\n" +
	 * " (SELECT CCID, isnull(sum(TOTAL_USER_QTY*VALUE),0) indent FROM BUDGET_MASTER bm\r\n"
	 * + " left join Indent_Transaction it on bm.CCID= it.COST_CENTER\r\n" +
	 * " and it.MONTH =  format(GETDATE(),'MMMMMMMMMMMMMMMM') and it.YEAR= format(GETDATE(),'yyyy') group by  CCID) AS SourceTable\r\n"
	 * +
	 * " PIVOT( MAX(indent)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable3\r\n" +
	 * " UNION ALL\r\n" +
	 * " SELECT 'Indent val. by PO route' title,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646] FROM\r\n" +
	 * " (SELECT CCID,isnull( SAPBILL,'0.00') SAPBILL FROM BUDGET_MASTER) AS SourceTable\r\n"
	 * +
	 * " PIVOT( MAX(SAPBILL)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable4\r\n" +
	 * " UNION ALL\r\n" +
	 * " SELECT 'Total Indent Value(Rs)' title,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646] FROM\r\n" +
	 * " (SELECT CCID, (isnull(sum(TOTAL_USER_QTY*VALUE),0)+isnull(SAPBILL,0)) totalIndent FROM BUDGET_MASTER bm\r\n"
	 * + " left join Indent_Transaction it on bm.CCID= it.COST_CENTER\r\n" +
	 * " and it.MONTH =  format(GETDATE(),'MMMMMMMMMMMMMMMM') and it.YEAR= format(GETDATE(),'yyyy') \r\n"
	 * + " group by  CCID,SAPBILL) AS SourceTable\r\n" +
	 * " PIVOT( MAX(totalIndent)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable5\r\n" +
	 * " union all\r\n" +
	 * " SELECT 'Cumulative Indent  Value(Rs)' title, [1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646]\r\n" +
	 * " FROm(SELECT CCID, (CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\r\n"
	 * + " WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April , 0)\r\n" +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April + bm.MaY , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April + bm.MaY + bm.june , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November, 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December, 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December+bm.January, 0)\r\n"
	 * +
	 * " ELSE ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November +bm.December+bm.January +bm.February, 0) END) + (isnull(sum(TOTAL_USER_QTY*VALUE),0)+isnull(SAPBILL,0))\r\n"
	 * + " AS cumulative_budget\r\n" +
	 * " FROM BUDGET_MASTER bm LEFT JOIN Indent_Transaction it ON bm.ccid = it.cost_center\r\n"
	 * +
	 * " GrOUP BY SAPBILL, bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July,bm.August,bm.September,bm.October,bm.November,bm.December,January,February ) AS SourceTable\r\n"
	 * +
	 * " PIVOT( MAX(cumulative_budget) FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable6\r\n" +
	 * " union all\r\n" +
	 * " SELECT 'Balance Budget Value(Rs)' title,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646]\r\n" +
	 * " FROm(SELECT CCID, BudValueRsL - ((CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\r\n"
	 * + " WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April , 0)\r\n" +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April + bm.MaY , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April + bm.MaY + bm.june , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October , 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November, 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December, 0)\r\n"
	 * +
	 * " WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December+bm.January, 0)\r\n"
	 * +
	 * " ELSE ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November +bm.December+bm.January +bm.February, 0) END) + (isnull(sum(TOTAL_USER_QTY*VALUE),0)+isnull(SAPBILL,0)))\r\n"
	 * + " AS cumulative_budget\r\n" +
	 * " FROM BUDGET_MASTER bm LEFT JOIN Indent_Transaction it ON bm.ccid = it.cost_center\r\n"
	 * +
	 * " GrOUP BY SAPBILL, bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July,bm.August,bm.September,bm.October,bm.November,bm.December,January,February ) AS SourceTable\r\n"
	 * +
	 * " PIVOT( MAX(cumulative_budget) FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\r\n"
	 * +
	 * " [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\r\n"
	 * +
	 * " [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\r\n"
	 * +
	 * " [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\r\n"
	 * + " [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable7");
	 * 
	 * 
	 * try { BuyerFooterList = getresultlist.getResultList(); } catch
	 * (HibernateException e) {
	 * 
	 * e.printStackTrace(); BuyerFooterList = null; } return BuyerFooterList; }
	 * 
	 */

	/*
	 * Created By Gokul Created On 19-07-2023 purpose get buyer indent footer list
	 * for buyer edit
	 */

	@Override
	public List<Object> getBuyerFooterList(String Year, String Month,String yearfromCal1) {
		List<Object> BuyerFooterList = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		String finalcc = getAllIndentedCostCenters(yearfromCal, Month);
		System.out.println("BuyerListfinalcc" + finalcc.length() + finalcc);
		if (finalcc.length() == 2) {
			System.out.println("finalcc.length()" + finalcc.length());

			return BuyerFooterList;
		}
		Query getresultlist = entityManager.createNativeQuery(" SELECT 'Budget Value Yearly(Rs)' title," + finalcc
				+ "\n"
				+ "				 FROm(SELECT CCID, CAST(REPLACE(BudValueRsL, ',', '') AS INT) BudValueRsL FROM BUDGET_MASTer  where YEAR=:YEAR  ) AS SourceTable\n"
				+ "				 PIVOT( MAX(BudValueRsL) FOR CCID IN (" + finalcc + ")) AS PivotTable1\n"
				+ "				 UNION ALL\n" + "				 SELECT 'Average Monthly Budget(Rs)' title," + finalcc
				+ " FROM\n"
				+ "				 (SELECT CCID, monthlybudget FROM BUDGET_MASTER  where YEAR=:YEAR) AS SourceTable\n"
				+ "				 PIVOT( MAX(monthlybudget)  FOR CCID IN (" + finalcc + ")) AS PivotTable2\n"
				+ "				 UNION ALL\n" + "				 SELECT 'Indent Val by STN portal' title," + finalcc
				+ " FROM\n"
				+ "				 (SELECT CCID, isnull(sum(BUYER_QTY*UnitPrice),0) indent FROM BUDGET_MASTER bm\n"
				+ "				 left join Indent_Transaction it on bm.CCID= it.COST_CENTER\n"
				+ "				 and it.MONTH =:MonthText and it.YEAR=:YEAR group by  CCID) AS SourceTable\n"
				+ "				 PIVOT( MAX(indent)  FOR CCID IN (" + finalcc + ")) AS PivotTable3\n"
				+ "				 UNION ALL\n" + "				 SELECT 'Indent Val by PO route' title," + finalcc
				+ " FROM\n"
				+ "				 (SELECT COST_CENTER,isnull( poamount,'0.00') SAPBILL FROM PO_Entry where YEAR=:yearfromCal1  and MONTH=:MonthText) AS SourceTable\n"
				+ "				 PIVOT( MAX(SAPBILL)  FOR COST_CENTER IN (" + finalcc + ")) AS PivotTable4\n"
				+ "				 UNION ALL\n" + "				 SELECT 'Total Indent Value(Rs)' title," + finalcc
				+ " FROM\n"
				+ "				 (SELECT CCID, (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(poe.POAmount,0)) totalIndent FROM BUDGET_MASTER bm\n"
				+ "				 left join Indent_Transaction it on bm.CCID= it.COST_CENTER\n"
				+ "				 left join PO_Entry poe on poe.COST_CENTER = it.COST_CENTER and it.MONTH=poe.MONTH"
				+ "				where it.MONTH =:MonthText and it.YEAR=:YEAR \n"
				+ "				 group by  CCID,POAmount) AS SourceTable\n"
				+ "				 PIVOT( MAX(totalIndent)  FOR CCID IN (" + finalcc + ")) AS PivotTable5\n"
				+ "				 union all\n"
				+ "				 SELECT 'Cumulative Indent  Value (Incl. PO, Route Rs)' title, " + finalcc + "\n"
				+ "				 FROm(SELECT CCID, (CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April,0) + ISNULL( bm.MaY,0)+ ISNULL(bm.june , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November, 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) +ISNULL( bm.june,0) + ISNULL(bm.July ,0)+ ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November,0)+ISNULL(bm.December, 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) +ISNULL( bm.june,0) +ISNULL( bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November,0)+ISNULL(bm.December,0)+ISNULL(bm.January, 0)\n"
				+ "				 ELSE ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November,0) +ISNULL(bm.December,0)+ISNULL(bm.January,0) +ISNULL(bm.February, 0) END) + (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(SAPBILL,0))\n"
				+ "				 AS cumulative_budget\n"
				+ "				 FROM BUDGET_MASTER bm LEFT JOIN Indent_Transaction it ON bm.ccid = it.cost_center and it.YEAR=:YEAR and MONTH=:MonthText\n"
				+ "				 GrOUP BY SAPBILL, bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July,bm.August,bm.September,bm.October,bm.November,bm.December,January,February ) AS SourceTable\n"
				+ "				 PIVOT( MAX(cumulative_budget) FOR CCID IN (" + finalcc + ")) AS PivotTable6\n"
				+ "				 union all\n" + "				 SELECT 'Balance Budget Value(Rs)' title," + finalcc
				+ "\n"
				+ "				 FROm(SELECT CCID, CAST(REPLACE(BudValueRsL, ',', '') AS INT) - ((CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April,0) +ISNULL( bm.MaY , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April, 0) + ISNULL(bm.MaY, 0) +ISNULL( bm.june, 0) + ISNULL(bm.July , 0) \n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October , 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November, 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November,0)+ISNULL(bm.December, 0)\n"
				+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April,0) +ISNULL( bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November,0)+ISNULL(bm.December,0)+ISNULL(bm.January, 0)\n"
				+ "				 ELSE ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November,0) +ISNULL(bm.December,0)+ISNULL(bm.January,0) +ISNULL(bm.February, 0) END) + (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(SAPBILL,0)))\n"
				+ "				 AS cumulative_budget\n"
				+ "				 FROM BUDGET_MASTER bm LEFT JOIN Indent_Transaction it ON bm.ccid = it.cost_center and it.YEAR=:YEAR and MONTH=:MonthText\n"
				+ "				 GrOUP BY SAPBILL, bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July,bm.August,bm.September,bm.October,bm.November,bm.December,January,February ) AS SourceTable\n"
				+ "				 PIVOT( MAX(cumulative_budget) FOR CCID IN (" + finalcc + ")) AS PivotTable7");

		getresultlist.setParameter("MonthText", Month);
		getresultlist.setParameter("YEAR", Year);
		getresultlist.setParameter("yearfromCal1", yearfromCal1);


		try {
			BuyerFooterList = getresultlist.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			BuyerFooterList = null;
		}
		return BuyerFooterList;
	}

	/*
	 * Gokul BuyerIndentUpdateSave
	 */

	@Override
	public String BuyerIndentUpdateSave(BuyerIndentBean[] products, String loginID, String userId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder messBuilder = new StringBuilder();
		String r = "";
		int response = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		// String stringValue = products[i].getProductPrice();
		// stringValue = stringValue.replaceAll("[₹\\s]", "");
		int n = products.length;
		for (int i = 0; i < n; i++) {

			String unitPrice = products[i].getUnitPrice();
			String stringValue1 = products[i].getBalanceTMTValue();
			stringValue1 = stringValue1 == "" ? "0.00" : products[i].getBalanceTMTValue();
			String totalString = "0";

			if (unitPrice != "") {
				float Price = Float.parseFloat(unitPrice);
				float total = Price * products[i].getBalanceTMTQty();
				totalString = String.valueOf(total);
			} else {
				totalString = "0.00";
			}
			Query updatetempIndenttransaction = entityManager.createNativeQuery("update indent_Transaction "
					+ " set BUYER_QTY = :TOTAL_USER_QTY ," + " BUYER_CONFIRMATION =1,"
					+ " BUYER_CONFIRMATION_DATE =:Created_date," + "	modified_date = :Created_date ,"
					+ "	BUYER_id = :loginID ," + " STATUS ='VERIFIED'," + " MoqQty =:MoqQty," + " MoqValue =:MoqValue,"
					+ " BalanceTMTQTy =:BalanceTMTQTy," + " BalanceTMTValue =:BalanceTMTValue"
					+ " where year=:YEAR and month=:MONTH and cost_center=:COST_CENTER " + "  and ITEM=:ITEM");
			// and DOC_NUMBER=:DOC_NUMBER
			if (products[i].getCostcenter().startsWith("CC")) {
				String output = products[i].getCostcenter().replace("CC", "");
				/*
				 * String temp = "update [BUYERCONFIRMATIONCC]" +
				 * " set BUYERQTY=:TOTAL_USER_QTY ," +
				 * products[i].getCostcenter()+"=:TOTAL_USER_QTY," //+" 1100=:TOTAL_USER_QTY," +
				 * "  ISBUYERCONFIRM=1, " + " BUYCONFIRMATIONDATE=:Created_date," +
				 * " MODIFIED_DATE =:Created_date," + "	BUYEID = :loginID ," +
				 * " BUYSTATUS ='VERIFIED'," + " MOQQTY =:MoqQty," + " MoqValue =:MoqValue," +
				 * " BalanceTMTQTy =:BalanceTMTQTy," + " BalanceTMTValue =:BalanceTMTValue" +
				 * " where year=:YEAR and month=:MONTH " + "  and PROD_NAME=:ITEM";
				 * 
				 * Query updatetempIndenttransaction = entityManager.createNativeQuery(temp);
				 */

				// updatetempIndenttransaction.setParameter("DOC_NUMBER",products[i].getDocumnet()
				// );
				// updatetempIndenttransaction.setParameter("price", stringValue);
				updatetempIndenttransaction.setParameter("Created_date", formattedDate);
				updatetempIndenttransaction.setParameter("MONTH", MonthText);
				updatetempIndenttransaction.setParameter("COST_CENTER", output);
				updatetempIndenttransaction.setParameter("YEAR", cFY);
				updatetempIndenttransaction.setParameter("ITEM", products[i].getDescription());
				updatetempIndenttransaction.setParameter("TOTAL_USER_QTY", Integer.valueOf(products[i].getQuantity()));
				updatetempIndenttransaction.setParameter("loginID", loginID);
				updatetempIndenttransaction.setParameter("MoqQty", Integer.valueOf(products[i].getMoqQty()));
				updatetempIndenttransaction.setParameter("MoqValue", totalString);
				updatetempIndenttransaction.setParameter("BalanceTMTQTy",
						Integer.valueOf(products[i].getBalanceTMTQty()));
				updatetempIndenttransaction.setParameter("BalanceTMTValue", stringValue1);

				response = updatetempIndenttransaction.executeUpdate();

				Query gettotal = entityManager.createNativeQuery("update budget_master  set " + MonthText
						+ "=(select isnull(sum(buycc.buyer_qty*buycc.UnitPrice),0) from INDENT_TRANSACTION buycc "
						+ "where  buycc.year=:cFY and buycc.MONTH=:MONTH and COST_CENTER=" + output + ") "
						+ "where budget_master.ccid =" + output + " and budget_master.Year=:cFY");

				// update budget master -----
				// Query gettotal = entityManager
				// .createNativeQuery("select ISNULL(SUM(BUYER_QTY * Unitprice), 0) from
				// Indent_Transaction \r\n"
				// + " where COST_CENTER=:userId and TOTAL_USER_QTY<>0 and year=:cFY");

				gettotal.setParameter("MONTH", MonthText);
				// gettotal.setParameter("userId", userId);
				gettotal.setParameter("cFY", cFY);
				/// BigDecimal total=(BigDecimal) gettotal.getSingleResult();

				gettotal.executeUpdate();
			}

			/*
			 * if(MonthText.equalsIgnoreCase("July")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set july=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate(); }else
			 * if(MonthText.equalsIgnoreCase("August")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set August=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("September")) { Query updateBudgetMaster
			 * = entityManager
			 * .createNativeQuery("update budget_master set September=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("October")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set October=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("November")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set November=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("December")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set December=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("January")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set January=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("February")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set February=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("March")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set March=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("April")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set April=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("May")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set May=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }else if(MonthText.equalsIgnoreCase("June")) { Query updateBudgetMaster =
			 * entityManager .createNativeQuery("update budget_master set June=:total" +
			 * " where ccid=:userId and year=:cFY");
			 * 
			 * updateBudgetMaster.setParameter("total", total);
			 * updateBudgetMaster.setParameter("userId", userId);
			 * updateBudgetMaster.setParameter("cFY", yearfromCal);
			 * updateBudgetMaster.executeUpdate();
			 * 
			 * }
			 */
//---------------budget master update--------end---
		} // for loop finish
		if (response != 0) {

			r = "Indent updated successfully";
		} else {
			r = "Update failed";
		}

		return r;
	}

	/**
	 * @author Masineni Krishna Sai- 22-06-2023
	 *
	 */

	@Override
	public String userCreationByForm(IndentMasterBean storemaster, String loginId) {
		String permitNumber = "";
		try {
			String passwordfromDB = storemaster.getPassword();
			PasswordUtils utils = new PasswordUtils();
			String encryptedPwd = utils.encrypt(passwordfromDB);

			System.out.println(permitNumber);

			String labelExistanceSQL = "SELECT COUNT(*) FROM INDENT_MANAGER WHERE LMSID=:LMSID";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
			labelExistanceQuery.setParameter("LMSID", storemaster.getLMSID());
			int isExistance = (int) labelExistanceQuery.getSingleResult();

			if (isExistance != 0) {

				return "User is already available.";
			}

			Query insertInitiateWorkPermit = entityManager
					.createNativeQuery("Insert Into INDENT_MANAGER (EmpCode,Password,EmpName,LMSID,STORECODE,"
							+ "Email,MobileNumber,CompanyEmployee,IsActive,ChangedBy,ChangedOn,CreatedBy,CreatedOn)"
							+ " VALUES(:EmpCode,:Password,:EmpName,:LMSID,:STORECODE,:Email,"
							+ ":MobileNumber,:CompanyEmployee,:IsActive,:ChangedBy,:ChangedOn,:CreatedBy,:CreatedOn)");

			insertInitiateWorkPermit.setParameter("EmpCode", storemaster.getEmpCode());

			insertInitiateWorkPermit.setParameter("Password", encryptedPwd);
			insertInitiateWorkPermit.setParameter("EmpName", storemaster.getEmpName());
			insertInitiateWorkPermit.setParameter("LMSID", storemaster.getLMSID());
			insertInitiateWorkPermit.setParameter("STORECODE", storemaster.getSTORECODE());

			insertInitiateWorkPermit.setParameter("Email", storemaster.getEmail());
			insertInitiateWorkPermit.setParameter("MobileNumber", storemaster.getMobileNumber());
			insertInitiateWorkPermit.setParameter("IsActive", 1);//
			insertInitiateWorkPermit.setParameter("ChangedBy", loginId);
			insertInitiateWorkPermit.setParameter("ChangedOn", new Date());
			insertInitiateWorkPermit.setParameter("CreatedBy", loginId);
			insertInitiateWorkPermit.setParameter("CreatedOn", new Date());
			int insertInitiateWorkPermitStatus = 0;

			insertInitiateWorkPermitStatus = insertInitiateWorkPermit.executeUpdate();
			senduserMail(storemaster, loginId, "titan@123");
			return "User Created Sucessfully";
		} catch (Exception e) {
			e.printStackTrace();
//				insertInitiateWorkPermitStatus=0;
			return "error";
		}

	}

	/**
	 * @author Masineni Krishna Sai - 26-06-2023 Used fo Sending the mail to User
	 *         for User Creation
	 */
	public void senduserMail(IndentMasterBean IndentMasterBean, String loginID, String password) {
		try {
			//String smtpHostServer = "smtp-relay.gmail.com";
			String smtpHostServer = "titan-co-in.mail.protection.outlook.com";

			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			props.put("mail.smtp.port", "25");
			Session session = Session.getInstance(props, null);
			System.out.println(session);
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in", "No Reply-stationary Employee Portal"));
			// msg.setFrom(new InternetAddress("nirajprasad@titan.co.in", "No
			// Reply-stationary
			// Employee Portal"));

			msg.setReplyTo(InternetAddress.parse(IndentMasterBean.getEmail(), false));
			msg.setSubject("User Id Login is created: " + IndentMasterBean.getEmpName(), "text/HTML");
			msg.setContent("Dear " + IndentMasterBean.getLMSID() + ", \n\n" + "Greetings!\n"
					+ "Your login is created in ISCM Stationery Portal.\n\n" + "Credentials are created by " + loginID

					+ "\nPassword : " + password
					+ "\n\nIMPORTANT: Please do not reply to this message or mail address.\n\n"
					+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should \n"
					+ "not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient \n"
					+ "acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not"
					+ "warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.\n\n\n"
					+ "Thanks & Regards, \n" + "Admin \nstationary Employee Portal.", "text/plain");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(IndentMasterBean.getEmail(), false));
			// msg.setRecipients(Message.RecipientType.TO,
			// InternetAddress.parse("nirajprasad@titan.co.in", false));

			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Masineni Krishna Sai - 30-06-2023 Used by excel bulk master upload
	 */
	@Override
	public StringBuilder insertExcelbudgetMaster(List<Budgetmasterbean> abmDetailList, String loginId) {

		List<Budgetmasterbean> abmDetailFinalList = new ArrayList<Budgetmasterbean>();

		StringBuilder messBuilder = new StringBuilder();
		for (Iterator iterator = abmDetailList.iterator(); iterator.hasNext();) {
			Budgetmasterbean Budgetmasterbean = (Budgetmasterbean) iterator.next();
			boolean specalChar = Validations.validation(Budgetmasterbean.getCCID());
			if (specalChar) {
				return messBuilder
						.append("Cost centre " + Budgetmasterbean.getCCID() + "  column has invalid character.");
			}
			specalChar = Validations.validation(Budgetmasterbean.getYear());
			if (specalChar) {
				return messBuilder.append("Year " + Budgetmasterbean.getYear() + "  column has invalid character.");
			}
			specalChar = Validations.validation(Budgetmasterbean.getCostCenterDescription());
			if (specalChar) {
				return messBuilder.append("CostCenterDescription " + Budgetmasterbean.getCostCenterDescription()
						+ " column has invalid character.");
			}
			specalChar = Validations.validation(Budgetmasterbean.getGL());

			if (specalChar) {
				return messBuilder.append("GL " + Budgetmasterbean.getGL() + " column has invalid character.");
			}
			specalChar = Validations.validation(Budgetmasterbean.getGLDescription());
			if (specalChar) {
				return messBuilder.append(
						"GLDescription " + Budgetmasterbean.getGLDescription() + " column has invalid character.");
			}

			specalChar = Validations.validation(Budgetmasterbean.getLocation());
			if (specalChar) {
				return messBuilder
						.append("Location " + Budgetmasterbean.getLocation() + " column has invalid character.");
			}
			specalChar = Validations.validation(Budgetmasterbean.getCostOwner());
			if (specalChar) {
				return messBuilder
						.append("CostOwner " + Budgetmasterbean.getCostOwner() + " column has invalid character.");
			}
			specalChar = Validations.validation(Budgetmasterbean.getDepartment());
			if (specalChar) {
				return messBuilder
						.append("Department " + Budgetmasterbean.getDepartment() + " column has invalid character.");
			}
			specalChar = Validations.validation(Budgetmasterbean.getBudValueRsL());
			if (specalChar) {
				return messBuilder
						.append("BudValueRsL " + Budgetmasterbean.getBudValueRsL() + " column has invalid character.");
			}

		}
		messBuilder.append(insertbudgetData(abmDetailList, loginId));

		return messBuilder;
	}

	/**
	 * Used by excel Budger Master upload
	 * 
	 * @param BudgetMaster Bean
	 * @param loginId
	 * @return
	 */
	private String insertbudgetData(List<Budgetmasterbean> abmDetailList, String loginId) {
		String response = "";
		try {

			for (int j = 0; j < abmDetailList.size(); j++) {
				Budgetmasterbean abmUserMaster = abmDetailList.get(j);
				// String animals = abmUserMaster.getStoreCode();
				// String animals_list[] = animals.split(",");
				// String animal1 = animals_list[0];
				// System.out.println("array" + animal1);
				System.out.println("array" + abmUserMaster.getYear() + abmUserMaster.getGL() + abmUserMaster.getCCID());
				Query insertabmuser = entityManager.createNativeQuery(
						"Insert Into BUDGET_MASTER (CCID,Year,CostCenterDescription,GL,GLDescription,Location,CostOwner,"
								+ "Department,BudValueRsL,CreatedBy,CreatedOn,ModifiedBy,ModifiedOn)"
								+ " VALUES(:CCID,:Year,:CostCenterDescription,:GL,:GLDescription,:Location,:CostOwner,:Department,"
								+ ":BudValueRsL,:CreatedBy,:CreatedOn,:ModifiedBy,:ModifiedOn)");
				insertabmuser.setParameter("CCID", abmUserMaster.getCCID());
				insertabmuser.setParameter("Year", abmUserMaster.getYear());
				insertabmuser.setParameter("CostCenterDescription", abmUserMaster.getCostCenterDescription());
				insertabmuser.setParameter("GL", abmUserMaster.getGL());
				insertabmuser.setParameter("GLDescription", abmUserMaster.getGLDescription());
				insertabmuser.setParameter("Location", abmUserMaster.getLocation());

				insertabmuser.setParameter("CostOwner", abmUserMaster.getCostOwner());
				insertabmuser.setParameter("Department", abmUserMaster.getDepartment());

				insertabmuser.setParameter("BudValueRsL", abmUserMaster.getBudValueRsL());
				insertabmuser.setParameter("CreatedBy", loginId);
				insertabmuser.setParameter("CreatedOn", new Date());
				insertabmuser.setParameter("ModifiedBy", loginId);
				insertabmuser.setParameter("ModifiedOn", new Date());

				int intresponse = insertabmuser.executeUpdate();
				if (intresponse != 0) {
					System.out.println("Failed to update password");
					System.out.println("<<<<<<<<<<< WP created successfully>>>>>>>>>>>");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = e.getMessage();
			System.out.println(response);
		}
		return response;
	}

	@Override
	public StringBuilder insertExcelholidayMaster(List<HolidayMasterBean> abmDetailList, String loginId) {

		List<HolidayMasterBean> abmDetailFinalList = new ArrayList<HolidayMasterBean>();

		StringBuilder messBuilder = new StringBuilder();
		for (Iterator iterator = abmDetailList.iterator(); iterator.hasNext();) {
			HolidayMasterBean HolidayMasterBean = (HolidayMasterBean) iterator.next();
			boolean specalChar = Validations.validation(HolidayMasterBean.getHoliday_date());
			if (specalChar) {
				return messBuilder.append(
						"Cost centre " + HolidayMasterBean.getHoliday_date() + "  column has invalid character.");
			}
			specalChar = Validations.validation(HolidayMasterBean.getHoliday_day());
			if (specalChar) {
				return messBuilder
						.append("Year " + HolidayMasterBean.getHoliday_day() + "  column has invalid character.");
			}
			specalChar = Validations.validation(HolidayMasterBean.getOccasion());
			if (specalChar) {
				return messBuilder.append(
						"CostCenterDescription " + HolidayMasterBean.getOccasion() + " column has invalid character.");
			}
			specalChar = Validations.validation(HolidayMasterBean.getOccasion());

			if (specalChar) {
				return messBuilder.append("GL " + HolidayMasterBean.getOccasion() + " column has invalid character.");
			}
			specalChar = Validations.validation(HolidayMasterBean.getOccasion());
			if (specalChar) {
				return messBuilder
						.append("GLDescription " + HolidayMasterBean.getOccasion() + " column has invalid character.");
			}

			specalChar = Validations.validation(HolidayMasterBean.getActivestatus());
			if (specalChar) {
				return messBuilder
						.append("Location " + HolidayMasterBean.getActivestatus() + " column has invalid character.");
			}
			specalChar = Validations.validation(HolidayMasterBean.getCostcentre());
			if (specalChar) {
				return messBuilder
						.append("CostOwner " + HolidayMasterBean.getCostcentre() + " column has invalid character.");
			}

			specalChar = Validations.validation(HolidayMasterBean.getYear());
			if (specalChar) {
				return messBuilder
						.append("Department " + HolidayMasterBean.getYear() + " column has invalid character.");
			}
			/*
			 * specalChar = Validations.validation(HolidayMasterBean.getBudValueRsL()); if
			 * (specalChar) { return messBuilder .append("BudValueRsL " +
			 * HolidayMasterBean.getBudValueRsL() + " column has invalid character."); }
			 */

		}
		messBuilder.append(insertholidayData(abmDetailList, loginId));

		return messBuilder;
	}

	/**
	 * Used by excel Budger Master upload
	 * 
	 * @param BudgetMaster Bean
	 * @param loginId
	 * @return
	 */
	private String insertholidayData(List<HolidayMasterBean> abmDetailList, String loginId) {
		String response = "";
		try {

			for (int j = 0; j < abmDetailList.size(); j++) {
				HolidayMasterBean abmUserMaster = abmDetailList.get(j);
				SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String holidayDateStr = abmUserMaster.getHoliday_date();
				if (holidayDateStr == null || holidayDateStr.trim().isEmpty()) {
					// Handle the case where the date string is empty
					// You can choose to skip this entry or set a default date, depending on your
					// requirements.
					continue;
				}

				try {
					Date holidayDate = inputDateFormat.parse(holidayDateStr);
					String formattedHolidayDate = outputDateFormat.format(holidayDate);
					System.out.println("array" + abmUserMaster.getHoliday_date() + abmUserMaster.getHoliday_day()
							+ abmUserMaster.getOccasion());

					Query insertabmuser = entityManager.createNativeQuery(
							"Insert Into Holiday_Master (Holiday_date,Holiday_day,Occasion,Activestatus,Costcentre,Year,"
									+ "CreatedBy,CreatedOn,ModifiedBy,ModifiedOn)"
									+ " VALUES(:Holiday_date,:Holiday_day,:Occasion,:Activestatus,:Costcentre,:Year,"
									+ ":CreatedBy,:CreatedOn,:ModifiedBy,:ModifiedOn)");
					insertabmuser.setParameter("Holiday_date", formattedHolidayDate);
					insertabmuser.setParameter("Holiday_day", abmUserMaster.getHoliday_day());
					insertabmuser.setParameter("Occasion", abmUserMaster.getOccasion());
					insertabmuser.setParameter("Activestatus", abmUserMaster.getActivestatus());
					insertabmuser.setParameter("Costcentre", abmUserMaster.getCostcentre());
					insertabmuser.setParameter("Year", abmUserMaster.getYear());
					insertabmuser.setParameter("CreatedBy", loginId);
					insertabmuser.setParameter("CreatedOn", new Date());
					insertabmuser.setParameter("ModifiedBy", loginId);
					insertabmuser.setParameter("ModifiedOn", new Date());

					int intresponse = insertabmuser.executeUpdate();
					if (intresponse != 0) {
						System.out.println("Failed to update password");
						System.out.println("<<<<<<<<<<< WP created successfully>>>>>>>>>>>");
					}
				} catch (ParseException e) {
					// Handle the case where the date string is not a valid date
					// You can choose to show an error message to the user or handle it as per your
					// requirements.
					response = "Invalid date format for Holiday_date: " + holidayDateStr;
					System.out.println(response);
					// You can also choose to log the error for further analysis.
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = e.getMessage();
			System.out.println(response);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllindentDetails() {

		List<Object> getAllindentDetails;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		Query selectQuery = entityManager.createNativeQuery(
				"select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS,\n"
						+ "BUYER_QTY,RECEIVED_TMT_QTY from Indent_Transaction where"
						+ " month=:MonthText and YEAR=:yearfromCal");
		selectQuery.setParameter("yearfromCal", yearfromCal);
		selectQuery.setParameter("MonthText", MonthText);

		try {
			getAllindentDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getAllindentDetails = null;
		}
		return getAllindentDetails;
	}

	/*
	 * Created By Gokul Created On 27-07-2023
	 */
	@Override
	public List<Object> getMOQIndentList() {
		List<Object> getAllUserDetails;
		Query getresultlist = entityManager
				.createNativeQuery("	SELECT distinct PROD_NAME,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
						+ "		FROM PRODUCT_MASTER pm \n"
						+ "		left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
						+ "		WHERE COST_CENTER IS NOT NULL and month=DATENAME(MONTH, GETDATE()) and YEAR= format(getdate(),'yyyy')\n"
						+ "		GROUP BY PROD_NAME,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue");
		try {
			getAllUserDetails = getresultlist.getResultList();

		} catch (HibernateException e) {

			e.printStackTrace();
			getAllUserDetails = null;
		}
		return getAllUserDetails;
	}

	@Override
	public List<Object> getDistribuFooterList() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		List<Object> BuyerFooterList = null;
		String finalcc = getAllIndentedCostCenters(yearfromCal, MonthText);

		if (finalcc.length() == 2) {
			return BuyerFooterList;
		}
		Query getresultlist = entityManager.createNativeQuery("    SELECT 'Indent val. by stn portal' title," + finalcc
				+ " FROM\r\n"
				+ " (SELECT CCID, isnull(sum(buyer_qty*UnitPrice),0) indent FROM BUDGET_MASTER bm\r\n"
				+ " left join Indent_Transaction it on bm.CCID= it.COST_CENTER\r\n"
				+ " and it.MONTH =  format(GETDATE(),'MMMMMMMMMMMMMMMM') and it.YEAR=:yearfromCal and BUYER_CONFIRMATION=1 group by  CCID) AS SourceTable\r\n"
				+ " PIVOT( MAX(indent)  FOR CCID IN (" + finalcc + ")) AS PivotTable3 ");
		getresultlist.setParameter("yearfromCal", yearfromCal);

		try {
			BuyerFooterList = getresultlist.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			BuyerFooterList = null;
		}
		return BuyerFooterList;

	}

	/*
	 * Gokul BuyerIndentUpdateSave
	 */

	@Override
	public String DistributionPageSave(BuyerIndentBean[] products, String loginID, String userId, String userName) {
		// TODO Auto-generated method stub
		StringBuilder messBuilder = new StringBuilder();
		String r = "";
		int response = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		int n = products.length;
		for (int i = 0; i < n; i++) {
			String unitPrice = products[i].getUnitPrice();
			String stringValue1 = products[i].getBalanceTMTValue();
			stringValue1 = stringValue1 == "" ? "0.00" : products[i].getBalanceTMTValue();
			String totalString = "0";

			if (unitPrice != "") {
				float Price = Float.parseFloat(unitPrice);
				float total = Price * products[i].getBalanceTMTQty();
				totalString = String.valueOf(total);
			} else {
				totalString = "0.00";
			}

			Query updatetempIndenttransaction = entityManager.createNativeQuery(
					"update indent_Transaction " + " set " + " ISTRYMNGT =1," + " Received_date =:Created_date,"
							+ "	modified_date = :Created_date ," + "	TRYMNGTID = :loginID ," + " STATUS ='RECEIVED',"
							+ " RECEIVED_TMT_QTY =:RECEIVED_TMT_QTY," + " RECEIVED_TMT_Value =:RECEIVED_TMT_Value,"
							+ " BalanceTMTQTy =:BalanceTMTQTy," + " BalanceTMTValue =:BalanceTMTValue"
							+ " where year=:YEAR and month=:MONTH and cost_center=:COST_CENTER " + "  and ITEM=:ITEM");
			// and DOC_NUMBER=:DOC_NUMBER
			if (products[i].getCostcenter().startsWith("CC")) {
				String output = products[i].getCostcenter().replace("CC", "");
				System.out.println("array" + output);
				System.out.println("products[i].getReceivedvalue()" + products[i].getReceivedvalue());
				System.out.println("products[i].getReceivedqty()" + products[i].getReceivedqty());
				// System.out.println("products[i].getUserQuantity()" +
				// products[i].getUserQuantity());
				System.out.println("products[i].unitPrice()" + products[i].getUnitPrice());
				System.out.println("products[i].getMoqQty()" + products[i].getMoqQty());
				System.out.println("products[i].getMoqValue()" + products[i].getMoqValue());
				// String quantityAsString = products[i].getUserQuantity();
				// String unitPrice = products[i].getUnitPrice();
				int moqQuantity = products[i].getMoqQty();
				String moqValue = products[i].getMoqValue();

				try {
					// int quantity = Integer.parseInt(quantityAsString);
					int moqTotalValue = Integer.parseInt(moqValue);
					int unitPriceAmount = Integer.parseInt(unitPrice);
					// Now, 'quantity' holds the integer value
					// if(moqQuantity > quantity) {
					// int stockQuantity = moqQuantity -quantity;
					// int stockValue = moqTotalValue - (quantity*unitPriceAmount);
					// System.out.println("murali"+stockQuantity+moqTotalValue);
					// }
				} catch (NumberFormatException e) {
					// Handle the case where 'quantityAsString' is not a valid integer
				}

				// updatetempIndenttransaction.setParameter("DOC_NUMBER",products[i].getDocumnet()
				// );
				// updatetempIndenttransaction.setParameter("price", stringValue);
				updatetempIndenttransaction.setParameter("Created_date", formattedDate);
				updatetempIndenttransaction.setParameter("MONTH", MonthText);
				updatetempIndenttransaction.setParameter("COST_CENTER", output);
				updatetempIndenttransaction.setParameter("YEAR", cFY);
				updatetempIndenttransaction.setParameter("ITEM", products[i].getDescription());
				updatetempIndenttransaction.setParameter("loginID", loginID);
				updatetempIndenttransaction.setParameter("RECEIVED_TMT_QTY",
						Integer.valueOf(products[i].getReceivedqty()));
				updatetempIndenttransaction.setParameter("RECEIVED_TMT_Value", products[i].getReceivedvalue());
				updatetempIndenttransaction.setParameter("BalanceTMTQTy",
						Integer.valueOf(products[i].getBalanceTMTQty()));
				updatetempIndenttransaction.setParameter("BalanceTMTValue", stringValue1);

				response = updatetempIndenttransaction.executeUpdate();

			}

		} // for loop finish
		if (response != 0) {

			r = "Data Updated Successfully";
		} else {
			r = "Update failed";
		}

		return r;
	}

	/*
	 * Created By Gokul Created On 27-07-2023 purpose get Distributer Indent List
	 */

	@Override
	public List<Object> getDistributerIndentList() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		List<Object> getAllUserDetails = null;
		String finalcc = getAllIndentedCostCenters(yearfromCal, MonthText);

		if (finalcc.length() == 2) {
			return getAllUserDetails;
		}

		/*
		 * Query getCostCenter = entityManager.
		 * createNativeQuery("select STUFF((SELECT DISTINCT ',[' + COST_CENTER + ']' " +
		 * " FROM Indent_Transaction FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, '')"
		 * ); List<String> costcenter = getCostCenter.getResultList();
		 */
		Query getresultlist = entityManager.createNativeQuery("sELECT PROD_NAME, MAKE, UOM, " + finalcc
				+ ",ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue,RECEIVED_TMT_QTY,RECEIVED_TMT_Value \n"
				+ "		FROM (\n"
				+ "		SELECT PROD_NAME, isnull(MAKE,'') MAKE, UOM, COST_CENTER, ISNULL(BUYER_QTY , 0) AS cost_value,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue,RECEIVED_TMT_QTY,RECEIVED_TMT_Value\n"
				+ "		FROM PRODUCT_MASTER pm \n" + "		left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
				+ "		WHERE COST_CENTER IS NOT NULL and month=DATENAME(MONTH, GETDATE()) and year=:yearfromCal and BUYER_CONFIRMATION=1 "
				+ "		GROUP BY PROD_NAME, MAKE, UOM, COST_CENTER,BUYER_QTY,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue,RECEIVED_TMT_QTY,RECEIVED_TMT_Value\n"
				+ "		) AS src\n" + "		PIVOT (MAX(cost_value)\n" + "		FOR COST_CENTER IN (" + finalcc
				+ ")) AS pivottable\n" + "		order by PROD_NAME desc");
		getresultlist.setParameter("yearfromCal", yearfromCal);

		try {
			getAllUserDetails = getresultlist.getResultList();

		} catch (HibernateException e) {

			e.printStackTrace();
			getAllUserDetails = null;
		}
		return getAllUserDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllindentmanagerDetails(String loginId) {

		List<Object> getAllindentmanagerDetails;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = dateFormat.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		Query selectQuery = entityManager.createNativeQuery(
				"select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS,\n"
						+ "BUYER_QTY,RECEIVED_TMT_QTY from Indent_Transaction where COST_CENTER=:COST_CENTER and \n"
						+ "month=format(GETDATE(),'MMMMMMMMMMMMMMMMM') and YEAR=:yearfromCal");
		selectQuery.setParameter("COST_CENTER", loginId);
		selectQuery.setParameter("yearfromCal", yearfromCal);

		try {
			getAllindentmanagerDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getAllindentmanagerDetails = null;
		}
		return getAllindentmanagerDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllpasswordDetails() {

		List<Object> getAllpasswordDetails;
		List<Object> getAlldecpassword = new ArrayList();

		Query selectQuery = entityManager
				.createNativeQuery("select LMSID,EmpName,Email,STORECODE,Password  from INDENT_MANAGER");
		try {
			getAllpasswordDetails = selectQuery.getResultList();

			for (Iterator iterator = getAllpasswordDetails.iterator(); iterator.hasNext();) {
				Object object = iterator.next();

				getAlldecpassword.add(Validations.decryptPasswordInList(object));

			}

		} catch (HibernateException e) {

			e.printStackTrace();
			getAllpasswordDetails = null;
			getAlldecpassword = null;
		}
		return getAlldecpassword;
	}

	public void sendpasswordMail(String email, String email_id, String user_Name, String pwd, String newPwd,
			String login_id) {
		try {
			//String smtpHostServer = "smtp-relay.gmail.com";
			
			String smtpHostServer = "titan-co-in.mail.protection.outlook.com";
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			props.put("mail.smtp.port", "25");
			Session session = Session.getInstance(props, null);
			System.out.println(session);
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in", "No Reply-stationary Employee Portal"));
			// msg.setFrom(new InternetAddress("nirajprasad@titan.co.in", "No
			// Reply-stationary
			// Employee Portal"));

			msg.setReplyTo(InternetAddress.parse(email, false));
			msg.setSubject("Password is changed in Stationery Portal: " + login_id, "text/HTML");
			msg.setContent("Dear User (CostCenter)" + login_id + ", \n\n" + "Greetings!\n"
					+ "Your Password is changed in ISCM Stationery Portal.\n\n" + "Password is changed  by " + user_Name
					+ "\nLogin ID : " + login_id + "\nPassword : " + pwd
					+ "\n\nIMPORTANT: Please do not reply to this message or mail address.\n\n"
					+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should \n"
					+ "not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient \n"
					+ "acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not"
					+ "warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.\n\n\n"
					+ "Thanks & Regards, \n" + "Admin \nstationary Portal.", "text/plain");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(email_id, false));

			Transport.send(msg);
			System.out.println("Email sent successfully!");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllCCIDDetails() {

		List<Object> getDesignationDetails;

		Query selectQuery = entityManager
				.createNativeQuery("SELECT CCID,Year,monthlybudget FROM BUDGET_MASTER order by 2");
		try {
			getDesignationDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getDesignationDetails = null;
		}
		return getDesignationDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllIndentList() {
		List<Object> getAllIndentList;
		Query selectQuery = entityManager.createNativeQuery("select * from Indent_Transaction");
		try {
			getAllIndentList = selectQuery.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
			getAllIndentList = null;
		}
		System.out.println("indent data restrieved successfully");
		return getAllIndentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllyearDetails() {

		List<Object> getAllyearDetails;

		Query selectQuery = entityManager.createNativeQuery("SELECT distinct Year, year FROM BUDGET_MASTER");
		try {
			getAllyearDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getAllyearDetails = null;
		}
		return getAllyearDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> getccidmasterid(String Year) {
		boolean count = Validations.validation(Year);
		if (count)
			return new ArrayList<String[]>();

		String sql = "SELECT CCID,Year,BudValueRsL,monthlybudget,SAPBILL FROM BUDGET_MASTER where CCID like '%" + Year
				+ "%' ";
		// productQualityFeedbackQuery.setParameter("USER_NAME", firstname);
		// productQualityFeedbackQuery.setParameter("toDate", formattedEndDate);
		Query query = entityManager.createNativeQuery(sql);
		List<String[]> response = query.getResultList();

		return response;
	}

	@Override
	public String budgetupdate(String descode, String yearlybudget, String budgetextension, String loginId) {

		Query updateInitiateWorkPermit = entityManager
				.createNativeQuery("UPDATE BUDGET_MASTER set Budget_Extension=:Budget_Extension where ccid=:descode");
		// + "(Name,CROTag)" + " VALUES(:desname,:crotag)");

		// Query
		// insertInitiateWorkPermit=entityManager.createNativeQuery(saveWorkPermit);
		updateInitiateWorkPermit.setParameter("descode", descode);

		updateInitiateWorkPermit.setParameter("Budget_Extension", budgetextension);

		int updateInitiateWorkPermitStatus = 0;
		try {
			updateInitiateWorkPermitStatus = updateInitiateWorkPermit.executeUpdate();
			// updateStatus = insertProductAuditDataQuery.executeUpdate();
			System.out.println("<<<<<<<<<<< WP created successfully>>>>>>>>>>>");

			return "Sucessfully Updated";

		} catch (Exception e) {
			e.printStackTrace();
//			insertInitiateWorkPermitStatus=0;
			return "error";
		}
	}

	@Override
	public String getEmailIdbyLoginIdn(String login_id) {
		String EMAIL_ID_QUERY = "select email from INDENT_MANAGER where lmsid=:login_id";
		Query getEmailIdQuery = entityManager.createNativeQuery(EMAIL_ID_QUERY);
		getEmailIdQuery.setParameter("login_id", login_id);

		try {
			String getStoreManagerDetails = (String) getEmailIdQuery.getSingleResult();
			logger.info("email : " + getStoreManagerDetails);
			return getStoreManagerDetails;
		} catch (NoResultException e) {
			logger.warn("Login ID not found in the system: " + login_id);
			return null; // or you can throw a custom exception
		} catch (Exception e) {
			logger.error("An error occurred while fetching email for login ID: " + login_id, e);
			return null; // or handle the error accordingly
		}
	}

	@Override
	public String checkIndentForMonth(String cFY, String MonthText, String userId) {
		Query getintentID = entityManager.createNativeQuery(
				"select DISTINCT DOC_NUMBER from Indent_Transaction where year=:year and month=:month and cost_center=:cost_center");
		int cFY1 = Integer.valueOf(cFY);
		String yearfromCal = null;
		if (getMonthNumber(MonthText) < 4) {
			cFY1 = cFY1 - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal  = String.valueOf(cFY1);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		getintentID.setParameter("year", yearfromCal);
		getintentID.setParameter("month", MonthText);
		getintentID.setParameter("cost_center", userId);
		String id = "";
		try {
			id = (String) getintentID.getSingleResult();
		} catch (NoResultException er) {
			return "fine";
		}
//		return "For the month of " + MonthText + " an Indent has already been generated: " + id
//				+ " for this cost center " + userId + " and please modify the indent in the indent list menu.";
		
	return "Indent is already generated for this cost center in current month. please modify the indent in the indent list.";

	}

	public String sendToVendor(Map<String, Object> payload) {
		String r = "";

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		 yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}

		try {

			Query getemailID = entityManager.createNativeQuery(
					"select pm.PRODUCT_NUMBER ,item, sum(TOTAL_USER_QTY), emailID, MAKE from Indent_Transaction  IDT inner join  PRODUCT_MASTER PM on pm.PROD_DESC=idt.ITEM\n"
							+ "where YEAR=:YEAR and MONTH=:MonthText \n"
							+ "group by item, pm.PRODUCT_NUMBER,emailID, make order by MAKE\n" + "");
			getemailID.setParameter("MonthText", MonthText);
			getemailID.setParameter("YEAR", yearfromCal);
			List<Object[]> emailIDPoduct = (List<Object[]>) getemailID.getResultList();

			Map<String, List<Object>> indentData = new HashMap<>();

			for (Object[] result : emailIDPoduct) {

				List<Object> resultdata = new ArrayList<>();
				resultdata.add(result[0]);
				resultdata.add(result[1]);
				resultdata.add(result[2]);
				// resultdata.add(result[4]);

				String key = result[3].toString();

				if (indentData.containsKey(key)) {
					// Key already exists, retrieve the existing list and add the current resultdata
					List<Object> existingList = indentData.get(key);
					existingList.addAll(resultdata);
				} else {
					// Key doesn't exist, create a new entry in the map
					indentData.put(key, resultdata);
				}
			}

			for (Map.Entry<String, List<Object>> entry : indentData.entrySet()) {
				String key = entry.getKey();
				List<Object> value = entry.getValue();

				Workbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook.createSheet("DataTable");
				// Row headerRow = sheet.createRow(0);

				// Add headers to the header row

				/*
				 * int counter = 0; int counter2 = 0;
				 * 
				 * for (Object element : value) { // Create a new Row every 3 iterations Row
				 * dataRow = sheet.createRow(1); Cell cell = dataRow.createCell(counter2);
				 * cell.setCellValue( element.toString()); System.out.println("  Element: " +
				 * counter2); counter2++; }
				 */

				int counter2 = 0;
				Row headerRow = sheet.createRow(0); // Assuming the header row is at index 0

				String[] headers = { "Product ID", "Description", "Quantity" };
				for (int i = 0; i < headers.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(headers[i]);
				}

				Row dataRow = null;

				for (Object element : value) {
					// Create a new Row every 3 iterations starting from index 1
					if (counter2 % 3 == 0) {
						dataRow = sheet.createRow(counter2 / 3 + 1); // Adjust row index based on your requirements
					}

					Cell cell = dataRow.createCell(counter2 % 3);
					cell.setCellValue(element.toString());
					System.out.println("Element: " + counter2);

					counter2++;
				}

				File excelFile = File.createTempFile("Indent_Number", ".xlsx");
				FileOutputStream fileOut = new FileOutputStream(excelFile);
				workbook.write(fileOut);
				fileOut.close();
				String filePath = excelFile.getAbsolutePath();
				System.out.println("File Path: " + filePath);

				// System.out.println("keyfor email" + key);
				// System.out.println("keyfor value" + value);

				// mail related changes

				//String smtpHostServer = "smtp-relay.gmail.com";
				String smtpHostServer = "titan-co-in.mail.protection.outlook.com";
				Properties props = System.getProperties();
				props.put("mail.smtp.host", smtpHostServer);
				props.put("mail.smtp.port", "25");
				Session session = Session.getInstance(props, null);
				System.out.println(session);
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(
						new InternetAddress("noreply_stationary@titan.co.in", "No Reply-stationary Employee Portal"));
				msg.setReplyTo(InternetAddress.parse(key, false));
				msg.setSubject("Indent Details from Buyer: text/HTML");

				MimeMultipart multipart = new MimeMultipart();

				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setText("Dear Vendor,\n\n" + "Greetings!\n"
						+ "Find buyer indent value information in the portal.\n"
						+ "\nIMPORTANT: Please do not reply to this message or mail address.\n\n"
						+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for a virus.\n\n\n"
						+ "Thanks & Regards,\n" + "Admin\n" + "Stationary Portal.");
				multipart.addBodyPart(textPart);

				System.out.println("Email Body:\n");
				MimeBodyPart excelAttachment = new MimeBodyPart();
				DataSource source = new FileDataSource(excelFile.getAbsolutePath());
				excelAttachment.setDataHandler(new DataHandler(source));
				excelAttachment.setFileName(excelFile.getName());
				multipart.addBodyPart(excelAttachment);

				msg.setContent(multipart);
				msg.setSentDate(new Date());

				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(key, false));
				Transport.send(msg);

				// Process key and value as needed
				// System.out.println("keymurali: " + key + ", Value: " + value);
			}
			r = "Email Sent By Successfully!";

			//String smtpHostServer = "smtp-relay.gmail.com";
			String smtpHostServer = "titan-co-in.mail.protection.outlook.com";

			/*
			 * Properties props = System.getProperties(); props.put("mail.smtp.host",
			 * smtpHostServer); Session session = Session.getInstance(props, null);
			 * System.out.println(session); // for (String recipientEmail : emailIDPoduct) {
			 */// MimeMessage msg = new MimeMessage(session);
//
//					msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in", "No Reply-stationary Employee Portal"));
//					msg.setReplyTo(InternetAddress.parse(recipientEmail, false));
//					msg.setSubject("Indent Details from Buyer: text/HTML");
//					MimeMultipart multipart = new MimeMultipart();
//
//		           //  Text content
//		            MimeBodyPart textPart = new MimeBodyPart();
//		           textPart.setText("Dear Vendor,\n\n" +
//		                   "Greetings!\n" +
//		                   "Find buyer indent value information in the portal.\n" +
//		                   "\nIMPORTANT: Please do not reply to this message or mail address.\n\n" +
//		                   "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for a virus.\n\n\n" +
//		                   "Thanks & Regards,\n" +
//		                  "Admin\n" +
//		                  "Stationary Portal.");
//		          multipart.addBodyPart(textPart);
//	            MimeBodyPart excelAttachment = new MimeBodyPart();
//		           DataSource source = new FileDataSource(excelFile.getAbsolutePath());
//		          excelAttachment.setDataHandler(new DataHandler(source));
//		           excelAttachment.setFileName(excelFile.getName());
//		           multipart.addBodyPart(excelAttachment);
//		            msg.setContent(multipart);
//				msg.setSentDate(new Date());
//
//					msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
//					Transport.send(msg);
//					}
//					System.out.println("Email sent successfully!");
//					r= "Email sent successfully!";
//					 excelFile.delete();
		}

		catch (Exception e) {
			e.printStackTrace();
			r = "Email Not Send Contact Admin!";
		}
		return r;

	}

	/*
	 * 24-08-2023 Filter For Indent Report for Month for Indent Manager
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> getreportbyid(String Month, String Year, String loginid) {
		boolean count = Validations.validation(Year);
		if (count)
			return new ArrayList<String[]>();
		String sql = "";
		List<String[]> response;
		if (Month.equalsIgnoreCase("All")) {
			sql = "select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,BUYER_QTY,RECEIVED_TMT_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS from Indent_Transaction where YEAR =:YEAR and COST_CENTER=:COST_CENTER ";
			Query query = entityManager.createNativeQuery(sql);

			query.setParameter("YEAR", Year);
			query.setParameter("COST_CENTER", loginid);
			response = query.getResultList();
		} else {
			sql = "select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,BUYER_QTY,RECEIVED_TMT_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS from Indent_Transaction where YEAR =:YEAR and COST_CENTER=:COST_CENTER and MONTH = :MONTH ";
			Query query = entityManager.createNativeQuery(sql);

			query.setParameter("MONTH", Month);
			query.setParameter("YEAR", Year);
			query.setParameter("COST_CENTER", loginid);
			response = query.getResultList();
		}

		return response;
	}

	/*
	 * 24-08-2023 Filter For Indent Report for Month for Admin
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> getreportbyidadmin(String Month, String Year, String loginid) {
		int cFY = Integer.valueOf(Year);
		String yearfromCal = Year;
		if (getMonthNumber(Month) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
		  yearfromCal = String.valueOf(cFY);
		    System.out.println("yearfromCal"+yearfromCal);
		}
		boolean count = Validations.validation(Year);
		if (count)
			return new ArrayList<String[]>();
		String sql = "";
		List<String[]> response;
		if (Month.equalsIgnoreCase("All")) {
			sql = "select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,BUYER_QTY,RECEIVED_TMT_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS from Indent_Transaction where YEAR =:YEAR";

			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("YEAR", Year);
			response = query.getResultList();
		} else {
			sql = "select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,BUYER_QTY,RECEIVED_TMT_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS from Indent_Transaction where MONTH =:MONTH and YEAR =:YEAR";

			Query query = entityManager.createNativeQuery(sql);

			query.setParameter("MONTH", Month);
			query.setParameter("YEAR", yearfromCal);
			
			response = query.getResultList();
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getYears() {

		List<String> getAllyearDetails;

		Query selectQuery = entityManager.createNativeQuery(" SELECT distinct YEAR FROM Indent_Transaction");
		try {
			getAllyearDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getAllyearDetails = null;
		}
		return getAllyearDetails;
	}

	/*
	 * 28-08-2023 Send the mail to Store after Receive quantity from distribution
	 * team
	 */
	public String sendToStore(String email, String email_id, Map<String, Object> payload) {
		String r = "";
		try {

			Query getemailID = entityManager.createNativeQuery(
					"select  Email from INDENT_MANAGER where Email='masinenikrishnasai@titan.co.in' ");
			String emailIDPoduct = (String) getemailID.getSingleResult();
			// List<String> emailIDPoduct = (List<String>) getemailID.getResultList();

			Map<String, Object> jsonData = new HashMap<>();
			jsonData.put("header", payload.get("header"));
			jsonData.put("data", payload.get("data"));
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("DataTable");

			// Set header and populate data
			List<String> header = (List<String>) jsonData.get("header");
			List<List<String>> data = (List<List<String>>) jsonData.get("data");

			// Create header row
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < header.size(); col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(header.get(col));
			}

			// Populate data rows
			for (int row = 0; row < data.size(); row++) {
				Row dataRow = sheet.createRow(row + 1);
				for (int col = 0; col < data.get(row).size(); col++) {
					Cell cell = dataRow.createCell(col);
					cell.setCellValue(data.get(row).get(col));
				}
			}

			File excelFile = File.createTempFile("Indent_Number", ".xlsx");
			FileOutputStream fileOut = new FileOutputStream(excelFile);
			workbook.write(fileOut);
			fileOut.close();

			//String smtpHostServer = "smtp-relay.gmail.com";
			String smtpHostServer = "titan-co-in.mail.protection.outlook.com";

			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			props.put("mail.smtp.port", "25");
			Session session = Session.getInstance(props, null);
			System.out.println(session);
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in", "No Reply-stationary Employee Portal"));
			// msg.setFrom(new InternetAddress("nirajprasad@titan.co.in", "No
			// Reply-stationary
			// Employee Portal"));

			msg.setReplyTo(InternetAddress.parse(emailIDPoduct, false));
			msg.setSubject("Indent Details from Distribution Team: text/HTML");
			MimeMultipart multipart = new MimeMultipart();

			// Text content
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText("Dear Store Team and User,\n\n" + "Greetings!\n"
					+ "Find Distribution indent value information in the portal.\n"
					+ "\nIMPORTANT: Please do not reply to this message or mail address.\n\n"
					+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for a virus.\n\n\n"
					+ "Thanks & Regards,\n" + "Admin\n" + "Stationery Portal.");
			multipart.addBodyPart(textPart);

			// Excel attachment
			MimeBodyPart excelAttachment = new MimeBodyPart();
			DataSource source = new FileDataSource(excelFile.getAbsolutePath());
			excelAttachment.setDataHandler(new DataHandler(source));
			excelAttachment.setFileName(excelFile.getName());
			multipart.addBodyPart(excelAttachment);

			// Set the content of the message
			msg.setContent(multipart);
			// msg.setContent("Dear Vendor, \n\n" + "Greetings!\n"
			// + "Find buyer indent value informations in portal"
			//
			// + "\n\nIMPORTANT: Please do not reply to this message or mail address.\n\n"
			// + "DISCLAIMER: This communication is confidential and privileged and is
			// directed to and for the use of the addressee only. The recipient if not the
			// addressee should \n"
			// + "not use this message if erroneously received, and access and use of this
			// e-mail in any manner by anyone other than the addressee is unauthorized. The
			// recipient \n"
			// + "acknowledges that Titan Company Pvt Ltd may be unable to exercise control
			// or ensure or guarantee the integrity of the text of the email message and the
			// text is not"
			// + "warranted as to completeness and accuracy. Before opening and accessing
			// the attachment, if any, please check and scan for virus.\n\n\n"
			// + "Thanks & Regards, \n" + "Admin \nstationary Portal.", "text/plain");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailIDPoduct, false));
			// msg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(email_id,
			// false));

			Transport.send(msg);
			System.out.println("Email sent successfully!");
			r = "Email Sent Successfully!";
			excelFile.delete();
		} catch (MessagingException e) {
			e.printStackTrace();
			r = "Email Not Send Contact Admin!";
		} catch (Exception e) {
			e.printStackTrace();
			r = "Email Not Send Contact Admin!";
		}
		return r;

	}

	/*
	 * Gokul 22-08-2023 Get all Product Id For Product Image upload
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllProductId() {

		List<Object> getAllProductId;

		Query selectQuery = entityManager.createNativeQuery(
				"select PRODUCT_NUMBER,PRODUCT_NUMBER+' - '+PROD_NAME as PROD_NAME from PRODUCT_MASTER");
		try {
			getAllProductId = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getAllProductId = null;
		}
		return getAllProductId;
	}

	/*
	 * Gokul 22-08-2023 Get all Product Id For Product Image upload
	 */
	public int updateIsImage(String productId, String userId) {

		int r = 0;
		Query updateProductMaster = entityManager.createNativeQuery("update product_master set is_image =1,"
				+ " modified_datetime=:date," + " modified_by =:userId" + " where product_number=:productId");

		updateProductMaster.setParameter("productId", productId);
		updateProductMaster.setParameter("userId", userId);
		updateProductMaster.setParameter("date", new Date());
		try {
			r = updateProductMaster.executeUpdate();

		} catch (HibernateException e) {

			e.printStackTrace();
			r = 0;
		}
		return r;
	}

	/*
	 * Created By Gokul Created On 12-07-2023 purpose get buyer indent list for
	 * buyer edit
	 */

	@Override
	public List<Object> getBuyerIndentListBasedOnFilter(String Year, String Month) {

		List<Object> getAllUserDetails = null;
		String finalcc = getAllIndentedCostCenters(Year, Month);
		if (finalcc.length() == 2) {
			return getAllUserDetails;
		}
		Query getresultlist = entityManager.createNativeQuery("sELECT PROD_NAME, MAKE, UOM, " + finalcc
				+ ",''userqty,MoqQty,'' totalqty,ucp,MoqValue,'' totalval ,BalanceTMTQTy,BalanceTMTValue \n"
				+ "		FROM (\n"
				+ "		SELECT PROD_NAME, isnull(MAKE,'') MAKE, UOM, COST_CENTER, ISNULL(BUYER_QTY , 0) AS cost_value,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
				+ "		FROM PRODUCT_MASTER pm \n" + "		left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
				+ "		WHERE COST_CENTER IS NOT NULL and month=:Month and year= :Year"
				+ "		GROUP BY PROD_NAME, MAKE, UOM, COST_CENTER,BUYER_QTY,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
				+ "		) AS src\n" + "		PIVOT (MAX(cost_value)\n" + "		FOR COST_CENTER IN (" + finalcc
				+ ")) AS pivottable\n" + "		order by PROD_NAME desc");

		getresultlist.setParameter("Year", Year);
		getresultlist.setParameter("Month", Month);

		try {
			getAllUserDetails = getresultlist.getResultList();

		} catch (HibernateException e) {

			e.printStackTrace();
			getAllUserDetails = null;
		}
		return getAllUserDetails;
	}

	@Override
	public List<Object> getBuyerFooterListBasedOnFilter(String Year, String Month) {

		List<Object> BuyerFooterList = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());
		String finalcc = getAllIndentedCostCenters(Year, Month);
		if (finalcc.length() == 2) {
			System.out.println("finalcc" + finalcc);
			return BuyerFooterList;
		}
		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		Query getresultlist = entityManager.createNativeQuery("SELECT 'Budget Value Yearly(Rs)' title,'' vendor,'' uom,"
				+ finalcc
				+ ",'' userqty,'' moqqty,'' totalqty,'' unitprice,'' moqvalue,''totalvalue,'' totaltmtqty,'' totaltmtvalue\n"
				+ "			FROm(SELECT CCID, CAST(REPLACE(BudValueRsL, ',', '') AS INT) BudValueRsL FROM BUDGET_MASTer  where Year=:Year  ) AS SourceTable\n"
				+ "			PIVOT( MAX(BudValueRsL) FOR CCID IN (" + finalcc + ")) AS PivotTable1\n"
				+ "			UNION ALL\n" + "			SELECT 'Average Monthly Budget(Rs)' title,'' vendor,'' uom,"
				+ finalcc
				+ ",'' userqty,'' moqqty,'' totalqty,'' unitprice,'' moqvalue,''totalvalue,'' totaltmtqty,'' totaltmtvalue FROM\n"
				+ "			(SELECT CCID, monthlybudget FROM BUDGET_MASTER  where Year=:Year) AS SourceTable\n"
				+ "			PIVOT( MAX(monthlybudget)  FOR CCID IN (" + finalcc + ")) AS PivotTable2\n"
				+ "			UNION ALL\n" + "			SELECT 'Indent val. by stn portal' title,'' vendor,'' uom,"
				+ finalcc
				+ ",'' userqty,'' moqqty,'' totalqty,'' unitprice,'' moqvalue,''totalvalue,'' totaltmtqty,'' totaltmtvalue FROM\n"
				+ "			(SELECT CCID, isnull(sum(TOTAL_USER_QTY*UnitPrice),0) indent FROM BUDGET_MASTER bm\n"
				+ "			left join Indent_Transaction it on bm.CCID= it.COST_CENTER\n"
				+ "			and it.Month=:Month and it.Year=:Year group by  CCID) AS SourceTable\n"
				+ "			PIVOT( MAX(indent)  FOR CCID IN (" + finalcc + ")) AS PivotTable3\n"
				+ "			UNION ALL\n" + "			SELECT 'Indent val. by PO route' title,'' vendor,'' uom,"
				+ finalcc
				+ ",'' userqty,'' moqqty,'' totalqty,'' unitprice,'' moqvalue,''totalvalue,'' totaltmtqty,'' totaltmtvalue FROM\n"
				+ "			(SELECT COST_CENTER,isnull( poAmount,'0.00') SAPBILL FROM PO_Entry where Year='2023' ) AS SourceTable\n"
				+ "			PIVOT( MAX(SAPBILL)  FOR COST_CENTER IN (" + finalcc + ")) AS PivotTable4\n"
				+ "			UNION ALL\n" + "			SELECT 'Total Indent Value(Rs)' title,'' vendor,'' uom,"
				+ finalcc
				+ ",'' userqty,'' moqqty,'' totalqty,'' unitprice,'' moqvalue,''totalvalue,'' totaltmtqty,'' totaltmtvalue FROM\n"
				+ "			(SELECT CCID, (isnull(sum(TOTAL_USER_QTY*UnitPrice),0)+isnull(SAPBILL,0)) totalIndent FROM BUDGET_MASTER bm\n"
				+ "			left join Indent_Transaction it on bm.CCID= it.COST_CENTER\n"
				+ "			and it.Month=:Month and it.Year=:Year\n"
				+ "			group by  CCID,SAPBILL) AS SourceTable\n"
				+ "			PIVOT( MAX(totalIndent)  FOR CCID IN (" + finalcc + ")) AS PivotTable5\n"
				+ "			union all\n" + "			SELECT 'Cumulative Indent  Value(Rs)' title,'' vendor,'' uom,"
				+ finalcc
				+ ",'' userqty,'' moqqty,'' totalqty,'' unitprice,'' moqvalue,''totalvalue,'' totaltmtqty,'' totaltmtvalue\n"
				+ "			FROm(SELECT CCID, (CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April + bm.MaY , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April + bm.MaY + bm.june , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November, 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December, 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December+bm.January, 0)\n"
				+ "			ELSE ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November +bm.December+bm.January +bm.February, 0) END) + (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(SAPBILL,0))\n"
				+ "			AS cumulative_budget\n"
				+ "			FROM BUDGET_MASTER bm LEFT JOIN Indent_Transaction it ON bm.ccid = it.cost_center and it.Year=:Year and Month=:Month\n"
				+ "			GrOUP BY SAPBILL, bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July,bm.August,bm.September,bm.October,bm.November,bm.December,January,February ) AS SourceTable\n"
				+ "			PIVOT( MAX(cumulative_budget) FOR CCID IN (" + finalcc + ")) AS PivotTable6\n"
				+ "			union all\n" + "			SELECT 'Balance Budget Value(Rs)' title,'' vendor,'' uom,"
				+ finalcc
				+ ",'' userqty,'' moqqty,'' totalqty,'' unitprice,'' moqvalue,''totalvalue,'' totaltmtqty,'' totaltmtvalue\n"
				+ "			FROm(SELECT CCID, CAST(REPLACE(BudValueRsL, ',', '') AS INT) - ((CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April + bm.MaY , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April + bm.MaY + bm.june , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April, 0) + ISNULL(bm.MaY, 0) +ISNULL( bm.june, 0) + ISNULL(bm.July , 0) \n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October , 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November, 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December, 0)\n"
				+ "			WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December+bm.January, 0)\n"
				+ "			ELSE ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November +bm.December+bm.January +bm.February, 0) END) + (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(SAPBILL,0)))\n"
				+ "			AS cumulative_budget\n"
				+ "			FROM BUDGET_MASTER bm LEFT JOIN Indent_Transaction it ON bm.ccid = it.cost_center and it.Year=:Year and Month=:Month\n"
				+ "			GrOUP BY SAPBILL, bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July,bm.August,bm.September,bm.October,bm.November,bm.December,January,February ) AS SourceTable\n"
				+ "			PIVOT( MAX(cumulative_budget) FOR CCID IN (" + finalcc + ")) AS PivotTable7");

		getresultlist.setParameter("Month", Month);
		getresultlist.setParameter("Year", Year);

		try {
			BuyerFooterList = getresultlist.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			BuyerFooterList = null;
		}
		return BuyerFooterList;
	}

	/*
	 * Created By Gokul Created On 12-07-2023 purpose get Distribution page load
	 */

	@Override
	public List<Object> getDistributionListBasedOnFilter(String Year, String Month) {

		List<Object> getAllUserDetails = null;
		String finalcc = getAllIndentedCostCenters(Year, Month);

		if (finalcc.length() == 2) {
			return getAllUserDetails;
		}
		Query getresultlist = entityManager.createNativeQuery("			 sELECT PROD_NAME, MAKE, UOM, " + finalcc
				+ ",''userqty,MoqQty,''totalqty,ucp,MoqValue,VALUE,RECEIVED_TMT_QTY,RECEIVED_TMT_Value,BalanceTMTQTy,BalanceTMTValue\n"
				+ "						FROM (\n"
				+ "						SELECT PROD_NAME, isnull(MAKE,'') MAKE, UOM, COST_CENTER, ISNULL(BUYER_QTY , 0) AS cost_value,ucp,MoqQty,VALUE,MoqValue,BalanceTMTQTy,BalanceTMTValue,RECEIVED_TMT_QTY,RECEIVED_TMT_Value\n"
				+ "						FROM PRODUCT_MASTER pm \n"
				+ "						left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
				+ "						WHERE COST_CENTER IS NOT NULL and month=:Month and year=:Year and BUYER_CONFIRMATION=1 \n"
				+ "						GROUP BY PROD_NAME, MAKE, UOM, COST_CENTER,BUYER_QTY,ucp,MoqQty,VALUE,MoqValue,BalanceTMTQTy,BalanceTMTValue,RECEIVED_TMT_QTY,RECEIVED_TMT_Value\n"
				+ "						) AS src\n" + "						PIVOT (MAX(cost_value)\n"
				+ "						FOR COST_CENTER IN (" + finalcc + ")) AS pivottable\n"
				+ "						order by PROD_NAME desc");

		getresultlist.setParameter("Year", Year);
		getresultlist.setParameter("Month", Month);

		try {
			getAllUserDetails = getresultlist.getResultList();

		} catch (HibernateException e) {

			e.printStackTrace();
			getAllUserDetails = null;
		}
		return getAllUserDetails;
	}

	@Override
	public List<Object> getDistributionFooterListBasedOnFilter(String Year, String Month) {

		List<Object> BuyerFooterList;
		Query getresultlist = entityManager.createNativeQuery(
				"SELECT 'Indent val. by stn portal' title,'' as vendor,'' uom,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "			[1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "			[1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "			[1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "			[1552],[1554],[1555],[1557],[1558],[1559],[7646],'' userQty,MoqQty,'' totalQty,'' Price,MoqValue,'' totalVal,RECEIVED_TMT_QTY,RECEIVED_TMT_Value,BalanceTMTQTy,BalanceTMTValue FROM\n"
						+ "			(SELECT CCID, isnull(sum(TOTAL_USER_QTY*UnitPrice),0) indent,MoqQty,MoqValue,\n"
						+ "			RECEIVED_TMT_QTY,RECEIVED_TMT_Value,BalanceTMTQTy,BalanceTMTValue FROM BUDGET_MASTER bm\n"
						+ "			left join Indent_Transaction it on bm.CCID= it.COST_CENTER\n"
						+ "			and it.MONTH =:Month and it.YEAR=:Year and BUYER_CONFIRMATION=1  \n"
						+ "			group by  CCID,MoqQty,MoqValue,RECEIVED_TMT_QTY,RECEIVED_TMT_Value,BalanceTMTQTy,BalanceTMTValue) AS SourceTable\n"
						+ "			PIVOT( MAX(indent)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "			[1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "			[1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "			[1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "			[1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable3 ");
		getresultlist.setParameter("Year", Year);
		getresultlist.setParameter("Month", Month);

		try {
			BuyerFooterList = getresultlist.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			BuyerFooterList = null;
		}
		return BuyerFooterList;

	}

	@Override
	public List<Object> getBudgetDataforChart(String CostCenter) {

		List<Object> BuyerFooterList;
		Query getresultlist = entityManager.createNativeQuery("select * from BUDGET_MASTER");
		// getresultlist.setParameter("Year", Year);
		// getresultlist.setParameter("Month", CostCenter);

		try {
			BuyerFooterList = getresultlist.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			BuyerFooterList = null;
		}
		return BuyerFooterList;

	}

	public void sevenDayMailTrigger() {
		String sqlquery = "sELECT PROD_NAME, MAKE, UOM, [1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
				+ "		[1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
				+ "		[1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
				+ "		[1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
				+ "		[1552],[1554],[1555],[1557],[1558],[1559],[7646],ucp \n" + "		FROM (\n"
				+ "		SELECT PROD_NAME, isnull(MAKE,'') MAKE, UOM, COST_CENTER, ISNULL(BUYER_QTY , 0) AS cost_value,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
				+ "		FROM PRODUCT_MASTER pm \n" + "		left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
				+ "		WHERE COST_CENTER IS NOT NULL and month=DATENAME(MONTH, GETDATE()) and year= format(GETDATE(),'yyyy')\n"
				+ "		GROUP BY PROD_NAME, MAKE, UOM, COST_CENTER,BUYER_QTY,ucp\n" + "		) AS src\n"
				+ "		PIVOT (MAX(cost_value)\n"
				+ "		FOR COST_CENTER IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
				+ "		[1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
				+ "		[1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
				+ "		[1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
				+ "		[1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS pivottable\n"
				+ "		order by PROD_NAME desc";

		try {
			Query getemailID = entityManager.createNativeQuery("select DISTINCT emailID from PRODUCT_MASTER ");
			List<String> emailIDPoduct = (List<String>) getemailID.getResultList();

			List<Object[]> indentList = getBuyerIndentList1(); // List of arrays, each array represents a row
			List<Object[]> footerList = getBuyerFooterList1();
			// Each list represents a row
			List<String> columnName = extractColumnNames(sqlquery);
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Buyer");
			Row headerRow = sheet.createRow(0);

			for (int colIdx = 0; colIdx < columnName.size(); colIdx++) {
				Cell cell = headerRow.createCell(colIdx);
				cell.setCellValue(columnName.get(colIdx));
			}

			for (int rowIdx = 0; rowIdx < indentList.size(); rowIdx++) {
				Object[] rowData = indentList.get(rowIdx);
				Row dataRow = sheet.createRow(rowIdx + 1);

				for (int colIdx = 0; colIdx < rowData.length; colIdx++) {
					Cell cell = dataRow.createCell(colIdx);
					Object cellValue = rowData[colIdx];
					if (cellValue != null) {
						cell.setCellValue(cellValue.toString());
					}
				}
			}
			for (int rowIdx = 0; rowIdx < footerList.size(); rowIdx++) {
				Object[] rowData = footerList.get(rowIdx);
				Row dataRow = sheet.createRow(rowIdx + 1 + indentList.size()); // Offset rows for footerList

				int offset = indentList.get(0).length; // Offset columns for footerList
				for (int colIdx = 0; colIdx < rowData.length; colIdx++) {
					Cell cell = dataRow.createCell(colIdx);
					Object cellValue = rowData[colIdx];
					if (cellValue != null) {
						cell.setCellValue(cellValue.toString());
					}
				}
			}

			File excelFile = File.createTempFile("Indent_Number", ".xlsx");
			FileOutputStream fileOut = new FileOutputStream(excelFile);
			workbook.write(fileOut);
			fileOut.close();

			//String smtpHostServer = "smtp-relay.gmail.com";
			String smtpHostServer = "titan-co-in.mail.protection.outlook.com";

			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			props.put("mail.smtp.port", "25");
			Session session = Session.getInstance(props, null);
			System.out.println(session);
			for (String recipientEmail : emailIDPoduct) {
				MimeMessage msg = new MimeMessage(session);

				msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in", "No Reply-stationary Portal"));
				msg.setReplyTo(InternetAddress.parse(recipientEmail, false));
				msg.setSubject("Indent Details from Buyer: text/HTML");
				MimeMultipart multipart = new MimeMultipart();

				// Text content
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setText("Dear Vendor,\n\n" + "Greetings!\n"
						+ "Find buyer indent value information in the portal.\n"
						+ "\nIMPORTANT: Please do not reply to this message or mail address.\n\n"
						+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for a virus.\n\n\n"
						+ "Thanks & Regards,\n" + "Admin\n" + "Stationary Portal.");
				multipart.addBodyPart(textPart);
				MimeBodyPart excelAttachment = new MimeBodyPart();
				DataSource source = new FileDataSource(excelFile.getAbsolutePath());
				excelAttachment.setDataHandler(new DataHandler(source));
				excelAttachment.setFileName(excelFile.getName());
				multipart.addBodyPart(excelAttachment);
				msg.setContent(multipart);
				msg.setSentDate(new Date());

				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
				Transport.send(msg);
			}
			System.out.println("Email sent successfully!");
			// r= "Email sent successfully!";
			excelFile.delete();
		} catch (MessagingException e) {
			e.printStackTrace();
			/// r= "Email not send contact admin!";
		} catch (Exception e) {
			e.printStackTrace();
			// r= "Email not send contact admin!";
		}
		// return r;

	}

	@Override
	public List<Object[]> getBuyerIndentList1() {
		String sqlquery = "sELECT PROD_NAME, MAKE, UOM, [1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
				+ "		[1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
				+ "		[1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
				+ "		[1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
				+ "		[1552],[1554],[1555],[1557],[1558],[1559],[7646],ucp\n" + "		FROM (\n"
				+ "		SELECT PROD_NAME, isnull(MAKE,'') MAKE, UOM, COST_CENTER, ISNULL(BUYER_QTY , 0) AS cost_value,ucp\n"
				+ "		FROM PRODUCT_MASTER pm \n" + "		left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
				+ "		WHERE COST_CENTER IS NOT NULL and month=DATENAME(MONTH, GETDATE()) and year= format(GETDATE(),'yyyy')\n"
				+ "		GROUP BY PROD_NAME, MAKE, UOM, COST_CENTER,BUYER_QTY,ucp,MoqQty,MoqValue,BalanceTMTQTy,BalanceTMTValue\n"
				+ "		) AS src\n" + "		PIVOT (MAX(cost_value)\n"
				+ "		FOR COST_CENTER IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
				+ "		[1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
				+ "		[1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
				+ "		[1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
				+ "		[1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS pivottable\n"
				+ "		order by PROD_NAME desc";

		List<Object[]> getAllUserDetails;
		Query getresultlist = entityManager.createNativeQuery(sqlquery);

		try {
			getAllUserDetails = getresultlist.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
			getAllUserDetails = null;
		}
		return getAllUserDetails;
	}

	public static List<String> extractColumnNames(String sqlQuery) {
		List<String> columnNames = new ArrayList<>();

		// Regular expression to match column names between SELECT and FROM
		Pattern pattern = Pattern.compile("SELECT\\s+(.*?)\\s+FROM", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(sqlQuery);

		if (matcher.find()) {
			String columnList = matcher.group(1);

			// Remove square brackets and split the column list by commas
			String[] columns = columnList.replaceAll("\\[|\\]", "").split(",");

			for (String column : columns) {
				columnNames.add(column.trim());
			}
		}
		return columnNames;
	}

	@Override
	public List<Object[]> getBuyerFooterList1() {
		List<Object[]> BuyerFooterList;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		Query getresultlist = entityManager.createNativeQuery(
				" SELECT 'Budget Value Yearly(Rs)' title,''vendor,''uom,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646]\n"
						+ "				 FROm(SELECT CCID, CAST(REPLACE(BudValueRsL, ',', '') AS INT) BudValueRsL FROM BUDGET_MASTer  where YEAR=:YEAR  ) AS SourceTable\n"
						+ "				 PIVOT( MAX(BudValueRsL) FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable1\n"
						+ "				 UNION ALL\n"
						+ "				 SELECT 'Average Monthly Budget(Rs)' title,''vendor,''uom,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646] FROM\n"
						+ "				 (SELECT CCID, monthlybudget FROM BUDGET_MASTER  where YEAR=:YEAR) AS SourceTable\n"
						+ "				 PIVOT( MAX(monthlybudget)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable2\n"
						+ "				 UNION ALL\n"
						+ "				 SELECT 'Indent val. by stn portal' title,''vendor,''uom,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646] FROM\n"
						+ "				 (SELECT CCID, isnull(sum(BUYER_QTY*UnitPrice),0) indent FROM BUDGET_MASTER bm\n"
						+ "				 left join Indent_Transaction it on bm.CCID= it.COST_CENTER\n"
						+ "				 and it.MONTH =  format(GETDATE(),'MMMMMMMMMMMMMMMM') and it.YEAR= format(GETDATE(),'yyyy') group by  CCID) AS SourceTable\n"
						+ "				 PIVOT( MAX(indent)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable3\n"
						+ "				 UNION ALL\n"
						+ "				 SELECT 'Indent val. by PO route' title,''vendor,''uom,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646] FROM\n"
						+ "				 (SELECT CCID,isnull( SAPBILL,'0.00') SAPBILL FROM BUDGET_MASTER where YEAR=:YEAR ) AS SourceTable\n"
						+ "				 PIVOT( MAX(SAPBILL)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable4\n"
						+ "				 UNION ALL\n"
						+ "				 SELECT 'Total Indent Value(Rs)' title,''vendor,''uom,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646] FROM\n"
						+ "				 (SELECT CCID, (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(SAPBILL,0)) totalIndent FROM BUDGET_MASTER bm\n"
						+ "				 left join Indent_Transaction it on bm.CCID= it.COST_CENTER\n"
						+ "				 and it.MONTH =  format(GETDATE(),'MMMMMMMMMMMMMMMM') and it.YEAR= format(GETDATE(),'yyyy') \n"
						+ "				 group by  CCID,SAPBILL) AS SourceTable\n"
						+ "				 PIVOT( MAX(totalIndent)  FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable5\n"
						+ "				 union all\n"
						+ "				 SELECT 'Cumulative Indent  Value(Rs)' title,''vendor,''uom, [1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646]\n"
						+ "				 FROm(SELECT CCID, (CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April + bm.MaY , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April + bm.MaY + bm.june , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November, 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December, 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December+bm.January, 0)\n"
						+ "				 ELSE ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November +bm.December+bm.January +bm.February, 0) END) + (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(SAPBILL,0))\n"
						+ "				 AS cumulative_budget\n"
						+ "				 FROM BUDGET_MASTER bm LEFT JOIN Indent_Transaction it ON bm.ccid = it.cost_center and it.YEAR=:YEAR and MONTH=:MonthText\n"
						+ "				 GrOUP BY SAPBILL, bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July,bm.August,bm.September,bm.October,bm.November,bm.December,January,February ) AS SourceTable\n"
						+ "				 PIVOT( MAX(cumulative_budget) FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable6\n"
						+ "				 union all\n"
						+ "				 SELECT 'Balance Budget Value(Rs)' title,''vendor,''uom,[1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646]\n"
						+ "				 FROm(SELECT CCID, CAST(REPLACE(BudValueRsL, ',', '') AS INT) - ((CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN 0\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'May' THEN ISNULL(bm.April , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'June' THEN ISNULL(bm.April + bm.MaY , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'July' THEN ISNULL(bm.April + bm.MaY + bm.june , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'August' THEN ISNULL(bm.April, 0) + ISNULL(bm.MaY, 0) +ISNULL( bm.june, 0) + ISNULL(bm.July , 0) \n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'September' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'October' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'November' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October , 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'December' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November, 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'January' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December, 0)\n"
						+ "				 WHEN DATENAME(MONTH, GETDATE()) = 'february' THEN ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November+bm.December+bm.January, 0)\n"
						+ "				 ELSE ISNULL(bm.April + bm.MaY + bm.june + bm.July + bm.August +bm.September + bm.October +bm.November +bm.December+bm.January +bm.February, 0) END) + (isnull(sum(TOTAL_USER_QTY*UnitPrice),0)+isnull(SAPBILL,0)))\n"
						+ "				 AS cumulative_budget\n"
						+ "				 FROM BUDGET_MASTER bm  JOIN Indent_Transaction it ON bm.ccid = it.cost_center and it.YEAR=:YEAR and MONTH=:MonthText\n"
						+ "				 GrOUP BY SAPBILL, bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,bm.April,bm.MaY,bm.june,bm.July,bm.August,bm.September,bm.October,bm.November,bm.December,January,February ) AS SourceTable\n"
						+ "				 PIVOT( MAX(cumulative_budget) FOR CCID IN ([1100],[1101],[1102],[1103],[1200],[1201],[1202],[1203],[1204],[1205],[1206],[1207],\n"
						+ "				 [1209],[1230],[1231],[1232],[1233],[1234],[1235],[1300],[1301],[1302],[1303],[1305],[1313],[1320],[1321],[1322],[1323],[1330],\n"
						+ "				 [1331],[1332],[1333],[1334],[1335],[1337],[1338],[1340],[1380],[1400],[1401],[1402],[1406],[1500],[1501],[1502],[1503],[1504],\n"
						+ "				 [1506],[1515],[1520],[1521],[1522],[1523],[1524],[1525],[1528],[1529],[1540],[1542],[1544],[1545],[1546],[1547],[1549],[1551],\n"
						+ "				 [1552],[1554],[1555],[1557],[1558],[1559],[7646])) AS PivotTable7");

		getresultlist.setParameter("MonthText", MonthText);
		getresultlist.setParameter("YEAR", yearfromCal);
		System.out.println("this is murali");

		try {
			BuyerFooterList = getresultlist.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			BuyerFooterList = null;
		}
		return BuyerFooterList;
	}

	@Override
	public List<String> get7thworkingDay() {
		List<String> BuyerFooterList;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		Query getresultlist = entityManager.createNativeQuery(" SELECT format(WorkingDay,'yyyy-MM-dd') WorkingDay\n"
				+ "FROM (\n"
				+ "    SELECT DATEADD(DAY, number, DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0)) AS WorkingDay\n"
				+ "    FROM master..spt_values\n" + "    WHERE type = 'P'\n"
				+ "      AND DATEPART(WEEKDAY, DATEADD(DAY, number, DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0))) BETWEEN 2 AND 7\n"
				+ "      AND NOT EXISTS (\n" + "        SELECT 1 FROM Holiday_Master\n"
				+ "        WHERE Holiday_date = DATEADD(DAY, number, DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0))\n"
				+ "      )\n" + ") AS WorkingDays\n" + "ORDER BY WorkingDay\n" + "OFFSET 6 ROWS\n"
				+ "FETCH NEXT 1 ROWS ONLY;");

		// getresultlist.setParameter("MonthText", MonthText);
		// getresultlist.setParameter("YEAR", yearfromCal);

		try {
			BuyerFooterList = getresultlist.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			BuyerFooterList = null;
		}
		return BuyerFooterList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllVendor() {

		List<Object> getAllyearDetails;

		Query selectQuery = entityManager.createNativeQuery("SELECT distinct VENDORNAME  FROM VENDOR_MASTER");
		try {
			getAllyearDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getAllyearDetails = null;
		}
		return getAllyearDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getVendorByEmailId(String vendorname) {

		List<Object> getAllyearDetails;

		Query selectQuery = entityManager
				.createNativeQuery("SELECT distinct EMAILID  FROM VENDOR_MASTER where VENDORNAME=:vendorname");
		selectQuery.setParameter("vendorname", vendorname);
		try {
			getAllyearDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getAllyearDetails = null;
		}
		return getAllyearDetails;
	}

	@Override
	public String ProductMasterCreationSave(String ProductID, String ProductName, String vendor, String EmailID,
			String Price, String UOM, String Category, String loginId) {
		String permitNumber = "";
		try {

			System.out.println("Product is already available." + ProductID);

			String labelExistanceSQL = "SELECT COUNT(*) FROM PRODUCT_MASTER WHERE PRODUCT_NUMBER=:ProductID";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
			labelExistanceQuery.setParameter("ProductID", ProductID);
			int isExistance = (int) labelExistanceQuery.getSingleResult();

			String getcateId = "SELECT CATORDERS FROM PRODUCT_MASTER WHERE CATEGORY=:Category";
			Query cateId = entityManager.createNativeQuery(getcateId);
			cateId.setParameter("Category", Category);
			int catID = 0;
			try {
				catID = (int) cateId.getSingleResult();
			} catch (Exception e) {
				if (catID == 0) {
					String maxid = "select max(CATORDERS)+1 from PRODUCT_MASTER";
					Query ID = entityManager.createNativeQuery(maxid);
					catID = (int) ID.getSingleResult();
				}

			}
			if (isExistance != 0) {

				return "Product is already available.";
			} else {

				Query insertInitiateWorkPermit = entityManager.createNativeQuery("INSERT INTO PRODUCT_MASTER\n"
						+ "(PRODUCT_NUMBER,PROD_NAME,PROD_DESC,MAKE,UCP,UOM,ISACTIVE,SELFLIFE\n"
						+ ",CATEGORY,PRICE_DATE,ISMOQ,IS_IMAGE,MODIFIED_DATETIME,MODIFIED_BY\n"
						+ ",CREATED_BY,CREATED_DATETIME,TO_PRICE_DATE,emailID,CATORDERS)"
						+ " VALUES(:ProductID,:ProductName,:ProductName,:vendor,:Price,:UOM,"
						+ ":IsActive,NULL,:Category,NULL,0,0,NULL,NULL,:CreatedBy,:CreatedOn,NULL,:emailID,:catID)");

				insertInitiateWorkPermit.setParameter("ProductID", ProductID);

				insertInitiateWorkPermit.setParameter("ProductName", ProductName);
				insertInitiateWorkPermit.setParameter("vendor", vendor);
				insertInitiateWorkPermit.setParameter("Price", Price);
				insertInitiateWorkPermit.setParameter("UOM", UOM);
				insertInitiateWorkPermit.setParameter("IsActive", 1);//
				insertInitiateWorkPermit.setParameter("Category", Category);//
				insertInitiateWorkPermit.setParameter("CreatedBy", loginId);
				insertInitiateWorkPermit.setParameter("CreatedOn", new Date());
				insertInitiateWorkPermit.setParameter("emailID", EmailID);
				insertInitiateWorkPermit.setParameter("catID", catID);
				int insertInitiateWorkPermitStatus = 0;

				insertInitiateWorkPermitStatus = insertInitiateWorkPermit.executeUpdate();
				return "Product master Created Sucessfully";
			}
		} catch (Exception e) {
			e.printStackTrace();
//				insertInitiateWorkPermitStatus=0;
			return "error";
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllProductsForList() {

		List<String> getStoreManagerDetials;

		Query selectQuery = entityManager.createNativeQuery(
				"select pm.PRODUCT_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom,pm.CATEGORY from PRODUCT_MASTER pm order by  pm.created_datetime desc");

		try {
			getStoreManagerDetials = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getStoreManagerDetials = null;
		}
		return getStoreManagerDetials;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getProductDetailsByID(String ProductID) {

		List<Object> getAllyearDetails;

		Query selectQuery = entityManager.createNativeQuery(
				"select pm.PRODUCT_NUMBER,pm.PROD_NAME,pm.PROD_DESC,pm.MAKE,pm.ucp,pm.uom,pm.CATEGORY,Emailid from PRODUCT_MASTER pm where PRODUCT_NUMBER=:ProductID order by  pm.created_datetime desc");
		selectQuery.setParameter("ProductID", ProductID);
		try {
			getAllyearDetails = selectQuery.getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
			getAllyearDetails = null;
		}
		return getAllyearDetails;
	}

	@Override
	public String ProductMasterCreationUpdate(String ProductID, String ProductName, String vendor, String EmailID,
			String Price, String UOM, String Category, String loginId) {
		String permitNumber = "";
		try {

			System.out.println("Product is already available." + ProductID);

			Query insertInitiateWorkPermit = entityManager.createNativeQuery("update PRODUCT_MASTER set\n"
					+ "PROD_NAME =:ProductName,PROD_DESC=:ProductName,MAKE=:vendor,UCP=:Price,UOM=:UOM\n"
					+ ",CATEGORY=:Category,MODIFIED_DATETIME=:CreatedBy,MODIFIED_BY=:CreatedOn\n"
					+ ",emailID=:emailID,CATORDERS=0 where product_number=:ProductID");

			insertInitiateWorkPermit.setParameter("ProductID", ProductID);

			insertInitiateWorkPermit.setParameter("ProductName", ProductName);
			insertInitiateWorkPermit.setParameter("vendor", vendor);
			insertInitiateWorkPermit.setParameter("Price", Price);
			insertInitiateWorkPermit.setParameter("UOM", UOM);
			insertInitiateWorkPermit.setParameter("Category", Category);//
			insertInitiateWorkPermit.setParameter("CreatedBy", loginId);
			insertInitiateWorkPermit.setParameter("CreatedOn", new Date());
			insertInitiateWorkPermit.setParameter("emailID", EmailID);
			int insertInitiateWorkPermitStatus = 0;

			insertInitiateWorkPermitStatus = insertInitiateWorkPermit.executeUpdate();
			return "Product Updated Sucessfully";
		} catch (Exception e) {
			e.printStackTrace();
			// insertInitiateWorkPermitStatus=0;
			return "error";
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllBudgetForList() {

		List<String> getStoreManagerDetials;

		Query selectQuery = entityManager.createNativeQuery(
				"select CCID,Year,CostCenterDescription,gl,GLDescription,Location,BudValueRsL,CostOwner from BUDGET_MASTER");

		try {
			getStoreManagerDetials = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getStoreManagerDetials = null;
		}
		return getStoreManagerDetials;
	}

	@Override
	public String BudgetMasterCreationSave(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC,
			String LOCATION, String Department, String YEARLYBUDGET, String loginId) {
		String permitNumber = "";
		try {

			System.out.println("Product is already available." + CCID);

			String labelExistanceSQL = "SELECT COUNT(*) FROM budget_master WHERE ccid=:CCID and Year=:Year";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
			labelExistanceQuery.setParameter("CCID", CCID);
			labelExistanceQuery.setParameter("Year", Year);
			int isExistance = (int) labelExistanceQuery.getSingleResult();

			if (isExistance != 0) {
				return "Budget for cost center : " + CCID + " is already added for this year : " + Year;
			} else {

				Query insertInitiateWorkPermit = entityManager.createNativeQuery(
						"INSERT INTO BUDGET_MASTER(CCID,Year,CostCenterDescription,GL,GLDescription,Location,CostOwner\n"
								+ "	,Department,BudValueRsL,CreatedBy,CreatedOn,ModifiedBy,ModifiedOn,\n"
								+ "	April,May,June,July,August,\n"
								+ "	September,October,November,December,January,February,March,Budget_Extension,SAPBILL,monthlybudget)\n"
								+ "	VALUES(:CCID,:Year,:COSTCENTERDESC,:GL,:GLDESC,:LOCATION,NULL,\n"
								+ "	:Department,:YEARLYBUDGET,:CreatedBy,:CreatedOn,:CreatedBy,:CreatedOn,\n"
								+ "	NULL,NULL,NULL,NULL,NULL\n"
								+ "	,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,:MONTHLY)");

				insertInitiateWorkPermit.setParameter("CCID", CCID);
				insertInitiateWorkPermit.setParameter("Year", Year);
				insertInitiateWorkPermit.setParameter("COSTCENTERDESC", COSTCENTERDESC);
				insertInitiateWorkPermit.setParameter("GL", GL);
				insertInitiateWorkPermit.setParameter("GLDESC", GLDESC);
				// insertInitiateWorkPermit.setParameter("IsActive", 1);//
				insertInitiateWorkPermit.setParameter("LOCATION", LOCATION);//
				insertInitiateWorkPermit.setParameter("CreatedBy", loginId);
				insertInitiateWorkPermit.setParameter("CreatedOn", new Date());
				insertInitiateWorkPermit.setParameter("Department", Department);
				insertInitiateWorkPermit.setParameter("YEARLYBUDGET", YEARLYBUDGET);
				insertInitiateWorkPermit.setParameter("MONTHLY", (Float.parseFloat(YEARLYBUDGET) / 12));
				int insertInitiateWorkPermitStatus = 0;

				insertInitiateWorkPermitStatus = insertInitiateWorkPermit.executeUpdate();
				return "Budget created sucessfully";
			}
		} catch (Exception e) {
			e.printStackTrace();
//				insertInitiateWorkPermitStatus=0;
			return "error";
		}

	}
	
	@Override
	public String ccCreationSave(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC,
			String LOCATION, String Department,String CCowner, String YEARLYBUDGET, String loginId,String COSTEMAIL) {
		String permitNumber = "";
		System.out.println("ccowner"+CCowner);
		try {

			System.out.println("Product is already available." + CCID);

			String labelExistanceSQL = "SELECT COUNT(*) FROM budget_master WHERE ccid=:CCID and Year=:Year";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
			labelExistanceQuery.setParameter("CCID", CCID);
			labelExistanceQuery.setParameter("Year", Year);
			int isExistance = (int) labelExistanceQuery.getSingleResult();

			
			/**
			 * Here we are validating whether the cost center is already available in indent manager or not.
			 */
			
			String ccidExistanceSQL = "SELECT COUNT(*) FROM INDENT_MANAGER WHERE EmpCode=:CCID";
			Query ccidExistanceQuery = entityManager.createNativeQuery(ccidExistanceSQL);
			ccidExistanceQuery.setParameter("CCID", CCID);
			int isccidExistance = (int) ccidExistanceQuery.getSingleResult();
			
			if (isccidExistance == 0) {
				Query insetIntoIndentManager = entityManager.createNativeQuery("INSERT INTO INDENT_MANAGER (EmpCode,\n"
						+ "    Password,\n"
						+ "    EmpName,\n"
						+ "    StoreCode,\n"
						+ "    MobileNumber,\n"
						+ "    DateOfJoin,\n"
						+ "    dateofleaving,\n"
						+ "    isactive,\n"
						+ "    changedBy,\n"
						+ "    Changedon,\n"
						+ "    CreatedBY,\n"
						+ "    CreatedOn,\n"
						+ "    LMSID,\n"
						+ "    Email,\n"
						+ "    abm,\n"
						+ "    region\n"
						+ ")\n"
						+ "VALUES (\n"
						+ "    :EmpCode,\n"
						+ "    :Password,\n"
						+ "    :EmpName,\n"
						+ "    :StoreCode,\n"
						+ "    :MobileNumber,\n"
						+ "    NULL,\n"
						+ "    NULL,\n"
						+ "    1, \n"
						+ "    :CreatedBy,\n"
						+ "    NULL,\n"
						+ "    :CreatedBy,\n"
						+ "    :CreatedOn,\n"
						+ "    :CCID,\n"
						+ "    :CostEmail,\n"
						+ "    NULL,\n"
						+ "    :region\n"
						+ ")");
				insetIntoIndentManager.setParameter("CCID", CCID);
				insetIntoIndentManager.setParameter("EmpCode", CCID);
				insetIntoIndentManager.setParameter("Password", "GH7Yz0xddaPTsXa01bcc4w==");
				insetIntoIndentManager.setParameter("EmpName",CCowner);
				insetIntoIndentManager.setParameter("StoreCode",Department);
				insetIntoIndentManager.setParameter("MobileNumber",null);
				//insetIntoIndentManager.setParameter("MobileNumber",null);
				insetIntoIndentManager.setParameter("CreatedBy", loginId);
				insetIntoIndentManager.setParameter("CreatedOn", new Date());
				insetIntoIndentManager.setParameter("CostEmail", COSTEMAIL);
				insetIntoIndentManager.setParameter("region", "Indent Manager");
				insetIntoIndentManager.executeUpdate();
			}	
			if (isExistance != 0) {
				return "cost center : " + CCID + " is already available for this year : " + Year;
			} else {

				Query insertInitiateWorkPermit = entityManager.createNativeQuery(
						"INSERT INTO BUDGET_MASTER(CCID,Year,CostCenterDescription,GL,GLDescription,Location,CostOwner\n"
								+ "	,Department,BudValueRsL,CreatedBy,CreatedOn,ModifiedBy,ModifiedOn,\n"
								+ "	April,May,June,July,August,\n"
								+ "	September,October,November,December,January,February,March,Budget_Extension,SAPBILL,monthlybudget)\n"
								+ "	VALUES(:CCID,:Year,:COSTCENTERDESC,:GL,:GLDESC,:LOCATION,:CCOWNer,\n"
								+ "	:Department,:YEARLYBUDGET,:CreatedBy,:CreatedOn,:CreatedBy,:CreatedOn,\n"
								+ "	NULL,NULL,NULL,NULL,NULL\n"
								+ "	,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,:MONTHLY)");

				insertInitiateWorkPermit.setParameter("CCID", CCID);
				insertInitiateWorkPermit.setParameter("Year", Year);
				insertInitiateWorkPermit.setParameter("COSTCENTERDESC", COSTCENTERDESC);
				insertInitiateWorkPermit.setParameter("GL", GL);
				insertInitiateWorkPermit.setParameter("GLDESC", GLDESC);
				// insertInitiateWorkPermit.setParameter("IsActive", 1);//
				insertInitiateWorkPermit.setParameter("LOCATION", LOCATION);//
				insertInitiateWorkPermit.setParameter("CCOWNer", CCowner);//
				insertInitiateWorkPermit.setParameter("CreatedBy", loginId);
				insertInitiateWorkPermit.setParameter("CreatedOn", new Date());
				insertInitiateWorkPermit.setParameter("Department", Department);
				insertInitiateWorkPermit.setParameter("YEARLYBUDGET", YEARLYBUDGET);
				insertInitiateWorkPermit.setParameter("MONTHLY", (Float.parseFloat(YEARLYBUDGET) / 12));
				int insertInitiateWorkPermitStatus = 0;

				insertInitiateWorkPermitStatus = insertInitiateWorkPermit.executeUpdate();
				return "CC created sucessfully";
			}
		} catch (Exception e) {
			e.printStackTrace();
//				insertInitiateWorkPermitStatus=0;
			return "error";
		}

	}

	
	  public static int getMonthNumber(String monthName) {
	        Month month = Month.valueOf(monthName.toUpperCase());
	        return month.getValue();
	    }
	  
	@Override
	public String poEntryCreationSave(String Year, String Month, String CostCenter, String PoAmount, String loginId) {
		try {
			System.out.println("Product is already murali available." + CostCenter);
			String labelExistanceSQL = "SELECT COUNT(*) FROM PO_Entry WHERE COST_CENTER=:CostCenter and YEAR=:year and MONTH=:Month";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
			labelExistanceQuery.setParameter("CostCenter", CostCenter);
			labelExistanceQuery.setParameter("year", Year);
			labelExistanceQuery.setParameter("Month", Month);
			labelExistanceQuery.setParameter("Month", Month);
			int isExistance = (int) labelExistanceQuery.getSingleResult();
			if (isExistance != 0) {
				return "PO Entry : " + CostCenter + " is already available for this year : " + Year;
			} else {
				String sqlInsertQuery = "INSERT INTO PO_Entry (Year, COST_CENTER, MONTH, POAmount, CreatedBy, CreatedOn,MONTHNUMBER) "
						+ "VALUES (:Year,:CostCenter,:MONTH,:PoAmount,:CreatedBy,:CreatedOn,:MONTHNUMBER)";
				Query insertInitiatePoEntryData = entityManager.createNativeQuery(sqlInsertQuery);
				insertInitiatePoEntryData.setParameter("Year", Year);
				insertInitiatePoEntryData.setParameter("CostCenter", CostCenter);
				System.out.println("Product is already Month before available." + CostCenter);
				insertInitiatePoEntryData.setParameter("MONTH", Month);
				insertInitiatePoEntryData.setParameter("PoAmount", PoAmount);
				System.out.println("Product is already Month after available." + CostCenter);
				insertInitiatePoEntryData.setParameter("CreatedBy", loginId);
				insertInitiatePoEntryData.setParameter("CreatedOn", new Date());
			    int monthNumber = getMonthNumber(Month);
			    insertInitiatePoEntryData.setParameter("MONTHNUMBER", monthNumber);
				int insertInitiatePoEntryStatus = 0;
				insertInitiatePoEntryStatus = insertInitiatePoEntryData.executeUpdate();
				return "Budget created sucessfully";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String poEntryDataUpdation(String costCenter, String PoAmount, String Month, String year, String createdBy,
			String createdOn, String loginId) {
		try {
			String labelExistanceSQL = "update po_Entry set poamount =:PoAmount where COST_CENTER =:costCenter and MONTH =:month and YEAR =:year";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
			labelExistanceQuery.setParameter("PoAmount", PoAmount);
			labelExistanceQuery.setParameter("costCenter", costCenter);
			labelExistanceQuery.setParameter("year", year);
			labelExistanceQuery.setParameter("month", Month);
			System.out.println("update api murali" + " PoAmount" + PoAmount + " costCenter" + costCenter + " year"
					+ year + " createdBy" + createdBy + "Month" + Month);

			int insertInitiatePoEntryStatus = 0;
			insertInitiatePoEntryStatus = labelExistanceQuery.executeUpdate();
			return "po Data Updated Sucessfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllCCIDDe() {

		List<Object> getDesignationDetails;

		Query selectQuery = entityManager.createNativeQuery("SELECT EMPCODE,LMSID FROM INDENT_MANAGER");
		try {
			getDesignationDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getDesignationDetails = null;
		}
		return getDesignationDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getBidgetDetailsByID(String CCID, String Year) {

		List<Object> getDesignationDetails;

		Query selectQuery = entityManager.createNativeQuery(
				"SELECT ccid,Year,CostCenterDescription,gl,GLDescription,Location,Department,BudValueRsL FROM budget_master where ccid=:CCID and Year=:Year");

		selectQuery.setParameter("CCID", CCID);
		selectQuery.setParameter("Year", Year);
		try {
			getDesignationDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getDesignationDetails = null;
		}
		return getDesignationDetails;
	}

	@Override
	public String BudgetMasterUpdateByForm(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC,
			String LOCATION, String Department, String YEARLYBUDGET, String loginId, String oldYEARLYBUDGET) {
		String permitNumber = "";
		try {

			int isExistance = 0;// (int) labelExistanceQuery.getSingleResult();

			if (isExistance != 0) {

				return "cost center" + CCID + " is already available for this year." + Year;
			} else {

				Query insertInitiateWorkPermit = entityManager
						.createNativeQuery("update BUDGET_MASTER set CostCenterDescription=:COSTCENTERDESC,"
								+ "GL=:GL,GLDescription=:GLDESC,Location=:LOCATION,"
								+ "	Department=:Department,BudValueRsL=:YEARLYBUDGET,ModifiedBy=:CreatedBy,ModifiedOn=:CreatedOn,\n"
								+ "	monthlybudget=:MONTHLY,oldYearlyBudget=:oldYEARLYBUDGET where ccid=:CCID and Year=:Year\n");

				insertInitiateWorkPermit.setParameter("CCID", CCID);
				insertInitiateWorkPermit.setParameter("Year", Year);
				insertInitiateWorkPermit.setParameter("COSTCENTERDESC", COSTCENTERDESC);
				insertInitiateWorkPermit.setParameter("GL", GL);
				insertInitiateWorkPermit.setParameter("GLDESC", GLDESC);
				insertInitiateWorkPermit.setParameter("LOCATION", LOCATION);//
				insertInitiateWorkPermit.setParameter("CreatedBy", loginId);
				insertInitiateWorkPermit.setParameter("CreatedOn", new Date());
				insertInitiateWorkPermit.setParameter("Department", Department);
				insertInitiateWorkPermit.setParameter("YEARLYBUDGET", YEARLYBUDGET);
				if (oldYEARLYBUDGET == null || oldYEARLYBUDGET.equalsIgnoreCase("null"))
					oldYEARLYBUDGET = "0";
				insertInitiateWorkPermit.setParameter("oldYEARLYBUDGET", Float.parseFloat(oldYEARLYBUDGET));
				insertInitiateWorkPermit.setParameter("MONTHLY", (Float.parseFloat(YEARLYBUDGET) / 12));
				int insertInitiateWorkPermitStatus = 0;

				insertInitiateWorkPermitStatus = insertInitiateWorkPermit.executeUpdate();
				return "Budget Updated Sucessfully";
			}
		} catch (Exception e) {
			e.printStackTrace();
//				insertInitiateWorkPermitStatus=0;
			return "error";
		}

	}

	@Override
	public StringBuilder ProductMasterBulkUpload(List<productMasterbean> abmDetailList, String loginId) {

		List<productMasterbean> abmDetailFinalList = new ArrayList<productMasterbean>();

		StringBuilder messBuilder = new StringBuilder();
		for (Iterator iterator = abmDetailList.iterator(); iterator.hasNext();) {
			productMasterbean productMasterbean = (productMasterbean) iterator.next();
			boolean specalChar = Validations.validation(productMasterbean.getProductid());
			if (specalChar) {
				return messBuilder
						.append("Product ID " + productMasterbean.getProductid() + "  column has invalid character.");
			}
			specalChar = Validations.validation(productMasterbean.getProductname());
			if (specalChar) {
				return messBuilder.append(
						"Product Name" + productMasterbean.getProductname() + "  column has invalid character.");
			}
			specalChar = Validations.validation(productMasterbean.getVendor());
			if (specalChar) {
				return messBuilder.append("vendor " + productMasterbean.getVendor() + " column has invalid character.");
			}
			specalChar = Validations.validation(productMasterbean.getVendorEmailId());

			if (specalChar) {
				return messBuilder.append(
						"Vendor Email id" + productMasterbean.getVendorEmailId() + " column has invalid character.");
			}
			specalChar = Validations.validation(productMasterbean.getPrice());
			if (specalChar) {
				return messBuilder
						.append("GLDescription " + productMasterbean.getPrice() + " column has invalid character.");
			}

			specalChar = Validations.validation(productMasterbean.getUOM());
			if (specalChar) {
				return messBuilder.append("Location " + productMasterbean.getUOM() + " column has invalid character.");
			}
			specalChar = Validations.validation(productMasterbean.getCategory());
			if (specalChar) {
				return messBuilder
						.append("CostOwner " + productMasterbean.getCategory() + " column has invalid character.");
			}

		}
		messBuilder.append(insertProductMasterData(abmDetailList, loginId));

		return messBuilder;
	}

	private String insertProductMasterData(List<productMasterbean> abmDetailList, String loginId) {
		String response = "";
		try {

			for (int j = 0; j < abmDetailList.size(); j++) {
				productMasterbean abmUserMaster = abmDetailList.get(j);

				Query productmaster = entityManager.createNativeQuery("INSERT INTO PRODUCT_MASTER\n"
						+ "(PRODUCT_NUMBER,PROD_NAME,PROD_DESC,MAKE,UCP,UOM,ISACTIVE,SELFLIFE\n"
						+ ",CATEGORY,PRICE_DATE,ISMOQ,IS_IMAGE,MODIFIED_DATETIME,MODIFIED_BY\n"
						+ ",CREATED_BY,CREATED_DATETIME,TO_PRICE_DATE,emailID,CATORDERS)"
						+ " VALUES(:ProductID,:ProductName,:ProductName,:vendor,:Price,:UOM,"
						+ ":IsActive,NULL,:Category,NULL,0,0,NULL,NULL,:CreatedBy,:CreatedOn,NULL,:emailID,0)");

				productmaster.setParameter("ProductID", abmUserMaster.getProductid());

				productmaster.setParameter("ProductName", abmUserMaster.getProductname());
				productmaster.setParameter("vendor", abmUserMaster.getVendor());
				productmaster.setParameter("Price", abmUserMaster.getPrice());
				productmaster.setParameter("UOM", abmUserMaster.getUOM());
				productmaster.setParameter("IsActive", 1);//
				productmaster.setParameter("Category", abmUserMaster.getCategory());//
				productmaster.setParameter("CreatedBy", loginId);
				productmaster.setParameter("CreatedOn", new Date());
				productmaster.setParameter("emailID", abmUserMaster.getVendorEmailId());
				int intresponse = 0;
				if (abmUserMaster.getProductid() != "" && abmUserMaster.getProductname() != ""
						&& abmUserMaster.getPrice() != "") {
					intresponse = productmaster.executeUpdate();
				} else {
					intresponse = 0;
				}

				if (intresponse != 0) {
					System.out.println("<<<<<<<<<<< WP created successfully>>>>>>>>>>>");
					response = "file upload successfully";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = e.getMessage();
			System.out.println(response);
		}
		return response;
	}

// Murali
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllPoEntryList() {
		List<Object> getPoEntryList;
		Query selectQuery = entityManager
				.createNativeQuery("select Year,COST_CENTER,MONTH,POAmount,createdBy,createdOn from PO_Entry");
		try {
			getPoEntryList = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getPoEntryList = null;
		}
		return getPoEntryList;
	}
	
	
	/**
	 * @author Murali chari - 22-11-2023 Used by excel poentry master upload
	 */
	@Override
	public StringBuilder insertExcelPoEntry(List<PoEntryBean> PoEntryBeanList, String loginId) {

		List<PoEntryBean> poEntryFinalList = new ArrayList<PoEntryBean>();
		StringBuilder messBuilder = new StringBuilder();
		for (Iterator iterator = PoEntryBeanList.iterator(); iterator.hasNext();) {
			PoEntryBean poEntryBean = (PoEntryBean) iterator.next();
			boolean specalChar = Validations.validation(poEntryBean.getCCID());
			
			if (specalChar) {
				return messBuilder
						.append("Cost centre " + poEntryBean.getCCID() + "  column has invalid character.");
			}
			
			
			specalChar = Validations.validation(poEntryBean.getYear());
			if (specalChar) {
				return messBuilder.append("Year " + poEntryBean.getYear() + "  column has invalid character.");
			}
			
			
			specalChar = Validations.validation(poEntryBean.getPOAmount());
			if (specalChar) {
				return messBuilder.append("GL " + poEntryBean.getPOAmount() + " column has invalid character.");
			}
			
			
			specalChar = Validations.validation(poEntryBean.getMonth());
			if (specalChar) {
				return messBuilder.append(
						"GLDescription " + poEntryBean.getMonth() + " column has invalid character.");
			}

		}
		messBuilder.append(insertPoEntryData(PoEntryBeanList, loginId));

		return messBuilder;
	}
	
	
	/**
	 * Used by excel PoEntry upload
	 * 
	 * @param poEntry Bean
	 * @param loginId
	 * @author 832044_CNST4 murali chari
	 * @return
	 */
	private String insertPoEntryData(List<PoEntryBean> poEntryList, String loginId) {
		String response = "";
		try {

			for (int j = 0; j < poEntryList.size(); j++) {
				PoEntryBean poEntryMaster = poEntryList.get(j);
				// String animals = abmUserMaster.getStoreCode();
				// String animals_list[] = animals.split(",");
				// String animal1 = animals_list[0];
				// System.out.println("array" + animal1);
				System.out.println("arrayeeee" + poEntryMaster.getYear() + poEntryMaster.getMonth() + poEntryMaster.getCCID());
				Query insertPoEntry = entityManager.createNativeQuery(
						"Insert Into PO_Entry (Year,cost_Center,Month,poamount,CreatedBy,CreatedOn)\n"
						+ "VALUES(:Year,:CCID,:MONTH,:POAMOUNT,:CreatedBy,:CreatedOn)");
				insertPoEntry.setParameter("CCID", poEntryMaster.getCCID());
				insertPoEntry.setParameter("Year", poEntryMaster.getYear());
				insertPoEntry.setParameter("POAMOUNT", poEntryMaster.getPOAmount());
				insertPoEntry.setParameter("MONTH", poEntryMaster.getMonth());
				insertPoEntry.setParameter("CreatedBy", loginId);
				insertPoEntry.setParameter("CreatedOn", new Date());
				int intresponse = insertPoEntry.executeUpdate();
				if (intresponse != 0) {
					System.out.println("indent updated successfully");
					System.out.println("<<<<<<<<<<< WP created successfully>>>>>>>>>>>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = e.getMessage();
			System.out.println(response);
		}
		return response;
	}
	
	@Override
	public String productValidation(String ProductID, String loginId) {
		String permitNumber = "";
		try {
			String labelExistanceSQL = "SELECT COUNT(*) FROM PRODUCT_MASTER WHERE PRODUCT_NUMBER=:ProductID";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
  			labelExistanceQuery.setParameter("ProductID", ProductID);
			int isExistance = (int) labelExistanceQuery.getSingleResult();
			if (isExistance != 0) {
				return "Product is already available.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "product is not available";
	}
	
	@Override
	public String ccValidation(String CCID, String loginId) {
		String permitNumber = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		try {
			String labelExistanceSQL = "SELECT COUNT(*) FROM BUDGET_MASTER WHERE CCID=:CCID and year=:YEAR";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
  			labelExistanceQuery.setParameter("CCID", CCID);
  			labelExistanceQuery.setParameter("YEAR", cFY);
			int isExistance = (int) labelExistanceQuery.getSingleResult();
			if (isExistance != 0) {
				return "CCID is already available.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "CCID is not available";
	}
	
	
	/**
	 * @author 832044_CNST4 murali chari.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllBudgetCCIDDe() {

		List<Object> getDesignationDetails;

Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		int cFY = Integer.valueOf(yearfromCal);
		Query selectQuery = entityManager.createNativeQuery("SELECT CCID "
				+ "    FROM BUDGET_MASTER \n");
			//	+ "    WHERE year=:YEAR and (BUDVALUERSL = '' OR BUDVALUERSL IS NULL)");
	//	selectQuery.setParameter("YEAR", cFY);
		try {
			getDesignationDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getDesignationDetails = null;
		}
		return getDesignationDetails;
	}

}
