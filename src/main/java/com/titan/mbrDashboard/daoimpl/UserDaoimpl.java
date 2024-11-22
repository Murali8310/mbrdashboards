package com.titan.mbrDashboard.daoimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.stream.Collectors;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.titan.mbrDashboard.bean.Budgetmasterbean;
import com.titan.mbrDashboard.bean.BuyerIndentBean;
import com.titan.mbrDashboard.bean.HolidayMasterBean;
import com.titan.mbrDashboard.bean.IndentMasterBean;
import com.titan.mbrDashboard.bean.PoEntryBean;
import com.titan.mbrDashboard.bean.Product;
import com.titan.mbrDashboard.bean.UserBean;
import com.titan.mbrDashboard.bean.UserLoginBean;
import com.titan.mbrDashboard.bean.productMasterbean;
import com.titan.mbrDashboard.bean.smUserMasterBean;
import com.titan.mbrDashboard.dao.UserDao;
import com.titan.mbrDashboard.dto.InputFilterData;
import com.titan.mbrDashboard.dto.MasterData;
import com.titan.mbrDashboard.dto.MonthlyDataFilter;
import com.titan.mbrDashboard.dto.OutputDashboardGraphs;
import com.titan.mbrDashboard.dto.OutputDashboardTiles;
import com.titan.mbrDashboard.dto.OutputForMontlyFilter;
import com.titan.mbrDashboard.dto.OutputGrowthOverPreviousMonth;
import com.titan.mbrDashboard.dto.OutputMonthlyOrdaringBehaviour;
import com.titan.mbrDashboard.dto.OutputPercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend;
import com.titan.mbrDashboard.dto.OutputPercentageofOrdersByWeekdayorWeekendRegionWise;
import com.titan.mbrDashboard.dto.OutputPercentageofOrdersbyDayoftheMonth;
import com.titan.mbrDashboard.dto.OutputPercentageofOrdersbyWeekdayorWeekend;
import com.titan.mbrDashboard.dto.OutputRegionWiseGrowthOverPreviousMonth;
import com.titan.mbrDashboard.dto.OutputRegionWiseMonthlyAvgPerOrder;
import com.titan.mbrDashboard.dto.OutputRegionWiseMonthlyDistribution;
import com.titan.mbrDashboard.dto.OutputRegionWiseMonthlyDistributionNoofOrders;
import com.titan.mbrDashboard.model.user.*;
import com.titan.mbrDashboard.security.AuthenticationService;
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

//	@Override
//	public Map<String, Object> findloginuser(UserLoginBean userLogin, String passwords) {
//
//		int loginFlag = 1;
//		Map<String, Object> userVal = new LinkedHashMap<String, Object>();
//		System.out.println("userselection" + userLogin.getUser_selection());
//		String loginId = userLogin.getLogin_id().toString();
//		boolean isAuthenticated = false;
//		if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
//			int len = userLogin.getLogin_id().toString().trim().length();
//			loginId = userLogin.getLogin_id().toString().trim().substring(0, len - 3);
//		}
//
//		if (userLogin.getUser_selection().equalsIgnoreCase("Tray Manager")) {
//			if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
//				isAuthenticated = true;
//			} else {
//				String usernameDB = "";
//				// String Username = "";
//				String getEmpCode = "SELECT top 1 EmpCode FROM Abm_master where empcode=:empcode"; // Doubt
//				Query getempcoded = entityManager.createNativeQuery(getEmpCode);
//				getempcoded.setParameter("empcode", userLogin.getLogin_id().toString().trim());
//				try {
//					usernameDB = (String) getempcoded.getSingleResult();
//					// if (userLogin.getPassword().equalsIgnoreCase(passworddec)) {
//					if (loginId.equalsIgnoreCase(usernameDB)) {
//						isAuthenticated = authenticationService.authenticateWithLdap(loginId, passwords);
//					}
//				} catch (NoResultException no) {
//					userVal.put("message", "User is not available in portal, Pls contact to Portal admin.");
//				}
//			}
//			if (!isAuthenticated) {
//				userVal.put("message", "Username/Password is not correct");
//			} else {
//				try {
//
//					String getUsersDetails = "SELECT EmpCode,Name,Designation,City,MobileNumber,Emailid"
//							+ " FROM ABM_MASTER WHERE EmpCode=:login_id ";
//
//					Query getUsersDetailsQuery = entityManager.createNativeQuery(getUsersDetails);
//					getUsersDetailsQuery.setParameter("login_id", loginId);
//					List<Object[]> usersDetailsList = getUsersDetailsQuery.getResultList();
//					if (usersDetailsList.size() > 0) {
//						for (Iterator iterator = usersDetailsList.iterator(); iterator.hasNext();) {
//							Object[] obj = (Object[]) iterator.next();
//							userVal.put("message", "SUCCESS");
//							userVal.put("login_id", obj[0].toString());
//							userVal.put("user_Name", obj[1].toString());
//							userVal.put("Designation", obj[2].toString());
//							userVal.put("mobilenumber", obj[3].toString());
//							userVal.put("email_id", obj[5].toString());
//							// userVal.put("Stores", obj[6].toString());
//							userVal.put("role", userLogin.getUser_selection());
//							System.out.println("check" + userLogin.getPassword());
//							loginFlag = 0;
//						}
//					} else {
//						userVal.put("message", "User is not available in portal, Pls contact to Portal admin.");
//					}
//				} catch (Exception c) {
//					c.printStackTrace();
//				}
//			}
//		} else if (userLogin.getUser_selection().equalsIgnoreCase("Indent Manager")) { // indent user
//
//			if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
//				isAuthenticated = true;
//			} else {
//				String passwordfromDB = "";
//				String passworddec = "";
//				String getPasswordQuery = "SELECT top 1 PASSWORD FROM INDENT_MANAGER where lmsid=:lmsid"; // Doubt
//				Query getPassword = entityManager.createNativeQuery(getPasswordQuery);
//				getPassword.setParameter("lmsid", userLogin.getLogin_id().toString().trim()); // login thru cost center
//
//				try {
//					passwordfromDB = (String) getPassword.getSingleResult();
//					PasswordUtils passwordUtils = new PasswordUtils();
//					passworddec = passwordUtils.decrypt(passwordfromDB);
//					System.out.println("passwordfromDB : " + passworddec);
//					// if (userLogin.getPassword().equalsIgnoreCase(passworddec)) {
//					if (passwords.equalsIgnoreCase(passworddec)) {
//						/*
//						 * String getUsersDetails = "SELECT [email],[EmpName] ,[STORECODE]" +
//						 * " ,[CompanyEmployee]  ,[Password],[IsActive]" +
//						 * "  FROM [INDENT_MANAGER] WHERE email=:login_id ";
//						 */
//						String getUsersDetails = "SELECT [lmsid], lmsid as [EmpName] ,[STORECODE]"
//								+ ",[IsActive],region" + " from [INDENT_MANAGER] where lmsid=:lmsid ";
//						Query getUsersDetailsQuery = entityManager.createNativeQuery(getUsersDetails);
//						getUsersDetailsQuery.setParameter("lmsid", userLogin.getLogin_id().toString().trim());
//						List<Object[]> usersDetailsList = getUsersDetailsQuery.getResultList();
//						for (Iterator iterator = usersDetailsList.iterator(); iterator.hasNext();) {
//							Object[] obj = (Object[]) iterator.next();// [1078437, MAHENDER NEGI, TKBN, false, true,
//																		// null]
//							userVal.put("message", "SUCCESS");
//							userVal.put("user_id", obj[0].toString());
//							userVal.put("user_Name", obj[1].toString());
//							userVal.put("storeCode", obj[2].toString());
//							// userVal.put("CompanyEmployee", obj[3].toString());
//							// userVal.put("CompanyEmployee", obj[3].toString());
//							userVal.put("login_id", obj[0].toString());
//							userVal.put("role", userLogin.getUser_selection());
//							loginFlag = 0;
//						}
//						isAuthenticated = true;
//					} else {
//						loginFlag = 0;
//						userVal.put("message", "Password is wrong.");
//						isAuthenticated = false;
//					}
//
//				} catch (NoResultException no) {
//					no.printStackTrace();
//					userVal.put("message", "Enter valid User ID.");
//					return userVal;
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		} else if (userLogin.getUser_selection().equalsIgnoreCase("Buyer")) {
//			if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
//				isAuthenticated = true;
//			} else {
//				isAuthenticated = authenticationService.authenticateWithLdap(loginId, passwords);
//			}
//			if (!isAuthenticated) {
//				userVal.put("message", "Username/Password is not correct");
//			} else {
//				String getUsersDetails = "SELECT User_id,User_Name,email_id,login_id,userAccess"
//						+ " FROM ER_User_Master WHERE login_id=:login_id ";
//				Query getUsersDetailsQuery = entityManager.createNativeQuery(getUsersDetails);
//				getUsersDetailsQuery.setParameter("login_id", loginId);
//				List<Object[]> usersDetailsList = getUsersDetailsQuery.getResultList();
//				if (usersDetailsList.size() > 0) {
//					for (Iterator iterator = usersDetailsList.iterator(); iterator.hasNext();) {
//						Object[] obj = (Object[]) iterator.next();
//						userVal.put("message", "SUCCESS");
//						userVal.put("user_id", obj[0].toString());
//						userVal.put("user_Name", obj[1].toString());
//						userVal.put("email_id", obj[2].toString());
//						userVal.put("login_id", obj[3].toString());
//						userVal.put("accessRole", obj[4].toString());
//						userVal.put("role", "Buyer");
//
//						loginFlag = 0;
//					}
//				} else {
//					userVal.put("message", "Admin detail is not available in portal, Pls contact to Portal admin.");
//				}
//			}
//		}
//		return userVal;
//	}

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

		if (userLogin.getLogin_id().toString().trim().endsWith("ccc")) {
			isAuthenticated = true;
		} else {
			String userEmail = "";
			// String usernameDB = "";
			// String Username = "";

			if (passwords == null || passwords.isEmpty()) {
				userVal.put("message", "Please enter the password.");
				return userVal;
			}
			String getEmpCode = "SELECT top 1 email_id FROM ER_User_Master where Status = 1 and login_id=:login_id"; // Doubt
			Query getempcoded = entityManager.createNativeQuery(getEmpCode);
			getempcoded.setParameter("login_id", userLogin.getLogin_id().toString().trim());
			try {
				userEmail = (String) getempcoded.getSingleResult();
				// if (userLogin.getPassword().equalsIgnoreCase(passworddec)) {
				// if (loginId.equalsIgnoreCase(usernameDB)) {
				isAuthenticated = authenticationService.authenticateWithLdap(userEmail, passwords);
				// }
			} catch (NoResultException no) {
				userVal.put("message", "User is not available in portal, Pls contact to Portal admin.");
			}
		}
		if (!isAuthenticated) {
			userVal.put("message", "Username/Password is not correct");
		} else {
			String getUsersDetails = "SELECT User_id,User_Name,email_id,login_id,userAccess"
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
					userVal.put("accessRole", obj[4].toString());

					loginFlag = 0;
				}
			} else {
				userVal.put("message", "Admin detail is not available in portal, Pls contact to Portal admin.");
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
			System.out.println("yearfromCal" + yearfromCal);
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
			r = "Indent Created Successfully : " + maxId;
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
			System.out.println("yearfromCal" + yearfromCal);
		}
		List<Object> IndentList;
		// Query selectQuery = entityManager.createNativeQuery(
		// "SELECT DISTINCT DOC_NUMBER,format(DOC_DATE,'dd-MMM-yyy')
		// DOC_DATE,im.LMSID,CONCAT(UPPER(SUBSTRING(STATus, 1, 1)),
		// LOWER(SUBSTRING(STATus, 2, LEN(STATus)))) AS STATUS FROM Indent_Transaction
		// it "
		// + " left join INDENT_MANAGER im on it.CREATEDBY = im.LMSID where Cost_Center
		// =:costcenter and it.month=:Month and it.year=:year");

//		Query selectQuery = entityManager.createNativeQuery(
//				"SELECT DISTINCT DOC_NUMBER,format(DOC_DATE,'dd-MMM-yyy') DOC_DATE,im.LMSID,CONCAT(UPPER(SUBSTRING(STATus, 1, 1)), LOWER(SUBSTRING(STATus, 2, LEN(STATus)))) AS STATUS FROM Indent_Transaction it "
//						+ " left join INDENT_MANAGER im on it.CREATEDBY = im.LMSID where Cost_Center =:costcenter and it.month=:Month and it.year=:year");

		Query selectQuery = entityManager.createNativeQuery("SELECT\n"
				+ "		DOC_NUMBER,SUM(TOTAL_USER_QTY * UnitPrice) AS IndentAmount,\n"
				+ "		 FORMAT(DOC_DATE, 'dd-MMM-yyyy') AS DOC_DATE,\n" + "		    im.LMSID,\n"
				+ "		    CONCAT(UPPER(SUBSTRING(STATus, 1, 1)), LOWER(SUBSTRING(STATus, 2, LEN(STATus)))) AS STATUS\n"
				+ "		FROM\n" + "		    Indent_Transaction it\n"
				+ "		    LEFT JOIN INDENT_MANAGER im ON it.CREATEDBY = im.LMSID\n" + "		WHERE\n"
				+ "		    Cost_Center = :costcenter\n" + "		    AND it.month = :Month\n"
				+ "		    AND it.year = :year\n" + "		GROUP BY\n" + "		    DOC_NUMBER,\n"
				+ "		    FORMAT(DOC_DATE, 'dd-MMM-yyyy'),\n" + "		    im.LMSID,\n"
				+ "		    CONCAT(UPPER(SUBSTRING(STATus, 1, 1)), LOWER(SUBSTRING(STATus, 2, LEN(STATus))))");

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
			System.out.println("yearfromCal" + yearfromCal);
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
									// + " and tempId=:DOC_NUMBER and ITEM=:ITEM");
									+ " and tempId=:DOC_NUMBER and PROD_NUMBER=:ITEM");
					updatetempIndenttransaction.setParameter("DOC_NUMBER", id);
					updatetempIndenttransaction.setParameter("total", totalString);
					updatetempIndenttransaction.setParameter("Created_date", formattedDate);
					updatetempIndenttransaction.setParameter("MONTH", MonthText);
					updatetempIndenttransaction.setParameter("COST_CENTER", userId);
					updatetempIndenttransaction.setParameter("YEAR", cFY);
					updatetempIndenttransaction.setParameter("ITEM", products[i].getProductID());
					// updatetempIndenttransaction.setParameter("ITEM",
					// products[i].getProductName());
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
			return "Indent already generated for this cost center in current month. please modify the indent in the indent list.";

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
			System.out.println("yearfromCal" + yearfromCal);
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
			System.out.println("yearfromCal" + yearfromCal);
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
			System.out.println("yearfromCal" + yearfromCal);
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

		Query selectQuery = entityManager
				.createNativeQuery("SELECT bm.ccid,bm.Year,bm.CostCenterDescription,bm.BudValueRsL,(bm.BudValueRsL-((\n"
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
						+ "(((\n" + "CASE WHEN DATENAME(MONTH, GETDATE()) = 'April' THEN ISNULL(bm.April , 0)\n"
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
						+ "END) \n" + "+ isnull(sum(poe.POAmount),0) + isnull(sum(poforCurYr.POAmount),0)\n" + "\n"
						+ ")) as cumulative_indent,\n" + "(CASE\n"
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
						+ "WHEN DATENAME(MONTH, GETDATE()) = 'march' THEN ISNULL(bm.March, 0)\n"
						+ "END) AS currentIndentValue,\n" + "(BudValueRsL/12)-((CASE\n"
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
						+ "WHEN DATENAME(MONTH, GETDATE()) = 'march' THEN ISNULL(bm.March, 0)\n"
						+ "END) + isnull(sum(poe.POAmount),0))  AS Month_Balance,\n" + "(\n"
						+ "        SELECT SUM(BUYER_QTY) \n" + "        FROM Indent_Transaction \n"
						+ "        WHERE COST_CENTER = :userId \n" + "        AND MONTH =:MonthText	\n"
						+ "    ) AS BuyerQuantity\n" + "FROM BUDGET_MASTER bm \n"
						+ "left join PO_Entry poe on poe.COST_CENTER=bm.CCID AND POE.Year=BM.Year and poe.MONTHNUMBER > 3 \n"
						+ "AND poe.MONTHNUMBER <=:decemberMOnthNum  \n"
						+ "left join PO_Entry poforCurYr on poforCurYr.COST_CENTER=bm.CCID AND poforCurYr.Year=:currentYearForPoEntry\n"
						+ "AND poforCurYr.MONTHNUMBER <=:poforCurYr \n" + "WHERE CCID=:userId AND bm.YEAR=:year\n"
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
						+ " where category IN( :category) AND NOT EXISTS (\n" + "        SELECT 1\n"
						+ "        FROM Indent_Transaction tit\n"
						+ "        WHERE pm.PROD_NAME = tit.item and COST_CENTER = :userId\n" + "    )  "
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
			System.out.println("yearfromCal" + yearfromCal);
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
			System.out.println("yearfromCal" + yearfromCal);
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
			System.out.println("yearfromCal" + yearfromCal + MonthText);
		}
		String finalcc = getAllIndentedCostCenters(yearfromCal, Month);

		List<Object> getAllUserDetails = null;
		if (finalcc.length() == 2) {
			return getAllUserDetails;
		}

		String costCenterParams = finalcc; // Example
		String[] costCenters = costCenterParams.split(",");
		StringBuilder stringBuilder = new StringBuilder();

		for (String costCenter : costCenters) {
			stringBuilder.append("MAX(").append(costCenter.trim()).append(") AS ").append(costCenter.trim())
					.append(", ");
		}

		// Remove the trailing comma and space
		String result = stringBuilder.substring(0, stringBuilder.length() - 2);
		System.out.println(result);

		Query getresultlist = entityManager.createNativeQuery("SELECT " + "PROD_NAME, " + "MAX(MAKE) AS MAKE, "
				+ "MAX(UOM) AS UOM, " + result + ", " + "MAX(ucp) AS ucp, " + "MAX(MoqQty) AS MoqQty, "
				+ "MAX(MoqValue) AS MoqValue, " + "MAX(BalanceTMTQTy) AS BalanceTMTQTy, "
				+ "MAX(BalanceTMTValue) AS BalanceTMTValue " + "FROM (" + "SELECT " + "PROD_NAME, "
				+ "ISNULL(MAKE, '') AS MAKE, " + "UOM, " + "COST_CENTER, " + "ISNULL(BUYER_QTY, 0) AS cost_value, "
				+ "ucp, " + "MoqQty, " + "MoqValue, " + "BalanceTMTQTy, " + "BalanceTMTValue "
				+ "FROM PRODUCT_MASTER pm " + "LEFT JOIN Indent_Transaction it ON pm.PROD_NAME = it.ITEM "
				+ "WHERE COST_CENTER IS NOT NULL " + "AND month = :Month " + "AND year = :Year " + ") AS src "
				+ "PIVOT (" + "MAX(cost_value) " + "FOR COST_CENTER IN (" + costCenterParams + ") " + ") AS pivottable "
				+ "GROUP BY PROD_NAME " + "ORDER BY PROD_NAME DESC;");

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
	public List<Object> getBuyerFooterList(String Year, String Month, String yearfromCal1) {
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
			System.out.println("yearfromCal" + yearfromCal);
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
				+ "				 ELSE ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November,0) +ISNULL(bm.December,0)+ISNULL(bm.January,0) +ISNULL(bm.February, 0) END) + (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(SAPBILL,0)"
				+ "+  ISNULL((\n" + "            CASE \n" + "                WHEN " + getMonthNumber(MonthText)
				+ " > 3 AND " + getMonthNumber(MonthText) + " < 12 THEN \n"
				+ "                    (SELECT SUM(po.poamount)\n" + "                     FROM PO_Entry po\n"
				+ "                     WHERE po.COST_CENTER = bm.ccid AND po.MONTHNUMBER > 3 AND po.MONTHNUMBER <= "
				+ getMonthNumber(MonthText) + ")\n" + "                WHEN " + getMonthNumber(MonthText)
				+ " <= 3 THEN \n" + "                    (SELECT SUM(po.poamount)\n"
				+ "                     FROM PO_Entry po\n"
				+ "                     WHERE po.COST_CENTER = bm.ccid AND \n"
				+ "                           ((po.MONTHNUMBER > 3 AND po.MONTHNUMBER <= 12 AND po.YEAR = bm.YEAR - 1) OR \n"
				+ "                            (po.MONTHNUMBER <= " + getMonthNumber(MonthText)
				+ " AND po.YEAR = bm.YEAR)))\n" + "                ELSE 0\n" + "            END\n"
				+ "        ), 0)) AS cumulative_budget"
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
				+ "				 ELSE ISNULL(bm.April,0) + ISNULL(bm.MaY,0) + ISNULL(bm.june,0) + ISNULL(bm.July,0) + ISNULL(bm.August,0) +ISNULL(bm.September,0) + ISNULL(bm.October,0) +ISNULL(bm.November,0) +ISNULL(bm.December,0)+ISNULL(bm.January,0) +ISNULL(bm.February, 0) END) + (isnull(sum(BUYER_QTY*UnitPrice),0)+isnull(SAPBILL,0)+ISNULL((\n"
				+ "            CASE \n" + "                WHEN " + getMonthNumber(MonthText) + " > 3 AND "
				+ getMonthNumber(MonthText) + " < 12 THEN \n" + "                    (SELECT SUM(po.poamount)\n"
				+ "                     FROM PO_Entry po\n"
				+ "                     WHERE po.COST_CENTER = bm.ccid AND po.MONTHNUMBER > 3 AND po.MONTHNUMBER <= "
				+ getMonthNumber(MonthText) + ")\n" + "                WHEN " + getMonthNumber(MonthText)
				+ " <= 3 THEN \n" + "                    (SELECT SUM(po.poamount)\n"
				+ "                     FROM PO_Entry po\n"
				+ "                     WHERE po.COST_CENTER = bm.ccid AND \n"
				+ "                           ((po.MONTHNUMBER > 3 AND po.MONTHNUMBER <= 12 AND po.YEAR = bm.YEAR - 1) OR \n"
				+ "                            (po.MONTHNUMBER <= " + getMonthNumber(MonthText)
				+ " AND po.YEAR = bm.YEAR)))\n" + "                ELSE 0\n" + "            END\n" + "        ), 0)))\n"
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
			System.out.println("yearfromCal" + yearfromCal);
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
			// String smtpHostServer = "smtp-relay.gmail.com";
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
			// else if (Budgetmasterbean.getCCID().isEmpty()) {
//				return messBuilder
//						.append("cost center field is manadatory.");
//			}
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
			if (specalChar) {
				return messBuilder.append("Email " + Budgetmasterbean.getEmail() + " column has invalid character.");
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

				boolean isCCidExists = checkccidExists(abmUserMaster.getCCID(), abmUserMaster.getYear());
				if (isCCidExists) {
					response = "Cost Cenete ID " + abmUserMaster.getCCID() + " already exists.";
					return response;
				}

				String ccid = abmUserMaster.getCCID();
				// Check if storeCode is empty, and skip the iteration if true
				if (ccid == null || ccid.isEmpty()) {
					response = "Uploaded successfully";
					continue;
				}
				/**
				 * Here we are validating whether the cost center is already available in indent
				 * manager or not.
				 */

				String ccidExistanceSQL = "SELECT COUNT(*) FROM INDENT_MANAGER WHERE EmpCode=:CCID";
				Query ccidExistanceQuery = entityManager.createNativeQuery(ccidExistanceSQL);
				ccidExistanceQuery.setParameter("CCID", abmUserMaster.getCCID());
				int isccidExistance = (int) ccidExistanceQuery.getSingleResult();

				if (isccidExistance == 0) {
					Query insetIntoIndentManager = entityManager.createNativeQuery(
							"INSERT INTO INDENT_MANAGER (EmpCode,\n" + "    Password,\n" + "    EmpName,\n"
									+ "    StoreCode,\n" + "    MobileNumber,\n" + "    DateOfJoin,\n"
									+ "    dateofleaving,\n" + "    isactive,\n" + "    changedBy,\n"
									+ "    Changedon,\n" + "    CreatedBY,\n" + "    CreatedOn,\n" + "    LMSID,\n"
									+ "    Email,\n" + "    abm,\n" + "    region\n" + ")\n" + "VALUES (\n"
									+ "    :EmpCode,\n" + "    :Password,\n" + "    :EmpName,\n" + "    :StoreCode,\n"
									+ "    :MobileNumber,\n" + "    NULL,\n" + "    NULL,\n" + "    1, \n"
									+ "    :CreatedBy,\n" + "    NULL,\n" + "    :CreatedBy,\n" + "    :CreatedOn,\n"
									+ "    :CCID,\n" + "    :CostEmail,\n" + "    NULL,\n" + "    :region\n" + ")");
					insetIntoIndentManager.setParameter("CCID", abmUserMaster.getCCID());
					insetIntoIndentManager.setParameter("EmpCode", abmUserMaster.getCCID());
					insetIntoIndentManager.setParameter("Password", "GH7Yz0xddaPTsXa01bcc4w==");
					insetIntoIndentManager.setParameter("EmpName", abmUserMaster.getCostOwner());
					insetIntoIndentManager.setParameter("StoreCode", abmUserMaster.getDepartment());
					insetIntoIndentManager.setParameter("MobileNumber", null);
					// insetIntoIndentManager.setParameter("MobileNumber",null);
					insetIntoIndentManager.setParameter("CreatedBy", loginId);
					insetIntoIndentManager.setParameter("CreatedOn", new Date());
					insetIntoIndentManager.setParameter("CostEmail", abmUserMaster.getEmail());
					insetIntoIndentManager.setParameter("region", "Indent Manager");
					insetIntoIndentManager.executeUpdate();
				}
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
					response = "Uploaded successfully";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = e.getMessage();
			System.out.println(response);
		}
		return response;
	}

	/**
	 * This method is used to verify the duplicates available in budget master.
	 * 
	 * @param empCode
	 * @return
	 */
	private boolean checkccidExists(String ccid, String year) {
		String empCodeExistenceSQL = "SELECT COUNT(*) FROM budget_master WHERE ccid =:ccid and  year =:year";
		Query empCodeExistenceQuery = entityManager.createNativeQuery(empCodeExistenceSQL);
		empCodeExistenceQuery.setParameter("ccid", ccid);
		empCodeExistenceQuery.setParameter("year", year);
		int empCodeCount = ((Number) empCodeExistenceQuery.getSingleResult()).intValue();
		return empCodeCount > 0;
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
						"holiday date" + HolidayMasterBean.getHoliday_date() + "  column has invalid character.");
			}
			specalChar = Validations.validation(HolidayMasterBean.getHoliday_day());
			if (specalChar) {
				return messBuilder
						.append("holiday " + HolidayMasterBean.getHoliday_day() + "  column has invalid character.");
			}
			specalChar = Validations.validation(HolidayMasterBean.getOccasion());
			if (specalChar) {
				return messBuilder
						.append("Occasion " + HolidayMasterBean.getOccasion() + " column has invalid character.");
			}
			specalChar = Validations.validation(HolidayMasterBean.getOccasion());

			specalChar = Validations.validation(HolidayMasterBean.getActivestatus());
			if (specalChar) {
				return messBuilder.append(
						"Active Status " + HolidayMasterBean.getActivestatus() + " column has invalid character.");
			}
//			specalChar = Validations.validation(HolidayMasterBean.getCostcentre());
//			if (specalChar) {
//				return messBuilder
//						.append("Costcenter " + HolidayMasterBean.getCostcentre() + " column has invalid character.");
//			}

			specalChar = Validations.validation(HolidayMasterBean.getYear());
			if (specalChar) {
				return messBuilder.append("Year " + HolidayMasterBean.getYear() + " column has invalid character.");
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

					boolean isHolidayduplicated = checkholidayDateDuplication(formattedHolidayDate);

					if (isHolidayduplicated) {
						response = formattedHolidayDate + " already exists.";
						return response;
					}
					Query insertabmuser = entityManager.createNativeQuery(
							"Insert Into Holiday_Master (Holiday_date,Holiday_day,Occasion,Activestatus,Costcentre,Year,"
									+ "CreatedBy,CreatedOn,ModifiedBy,ModifiedOn,holidaymonth)"
									+ " VALUES(:Holiday_date,:Holiday_day,:Occasion,:Activestatus,:Costcentre,:Year,"
									+ ":CreatedBy,:CreatedOn,:ModifiedBy,:ModifiedOn,:holidaymonth)");
					insertabmuser.setParameter("Holiday_date", formattedHolidayDate);
					insertabmuser.setParameter("Holiday_day", abmUserMaster.getHoliday_day());
					insertabmuser.setParameter("Occasion", abmUserMaster.getOccasion());
					insertabmuser.setParameter("Activestatus", abmUserMaster.getActivestatus());
					insertabmuser.setParameter("Costcentre", abmUserMaster.getCostcentre());
					insertabmuser.setParameter("Year", abmUserMaster.getYear());
					insertabmuser.setParameter("holidaymonth", abmUserMaster.getMonth());
					insertabmuser.setParameter("CreatedBy", loginId);
					insertabmuser.setParameter("CreatedOn", new Date());
					insertabmuser.setParameter("ModifiedBy", loginId);
					insertabmuser.setParameter("ModifiedOn", new Date());

					int intresponse = insertabmuser.executeUpdate();
					if (intresponse != 0) {
						response = "uploaded successfully";
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

	/**
	 * This method is used to validate the duplicate dates.
	 * 
	 * @param empCode
	 * @return
	 */
	private boolean checkholidayDateDuplication(String formattedDate) {
		String empCodeExistenceSQL = "SELECT COUNT(*) FROM holiday_master WHERE holiday_date =:formattedDate";
		Query empCodeExistenceQuery = entityManager.createNativeQuery(empCodeExistenceSQL);
		empCodeExistenceQuery.setParameter("formattedDate", formattedDate);
		int empCodeCount = ((Number) empCodeExistenceQuery.getSingleResult()).intValue();
		return empCodeCount > 0;

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
			System.out.println("yearfromCal" + yearfromCal);
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
			System.out.println("yearfromCal" + yearfromCal);
		}
		List<Object> BuyerFooterList = null;
		String finalcc = getAllIndentedCostCenters(yearfromCal, MonthText);

		if (finalcc.length() == 2) {
			return BuyerFooterList;
		}
		Query getresultlist = entityManager.createNativeQuery("    SELECT 'Indent val. by stn portal' title," + finalcc
				+ " FROM\r\n" + " (SELECT CCID, isnull(sum(buyer_qty*UnitPrice),0) indent FROM BUDGET_MASTER bm\r\n"
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
			System.out.println("yearfromCal" + yearfromCal);
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
			System.out.println("yearfromCal" + yearfromCal);
		}
		List<Object> getAllUserDetails = null;
		String finalcc = getAllIndentedCostCenters(yearfromCal, MonthText);

		if (finalcc.length() == 2) {
			return getAllUserDetails;
		}

		String costCenterParams = finalcc; // Example
		String[] costCenters = costCenterParams.split(",");
		StringBuilder stringBuilder = new StringBuilder();

		for (String costCenter : costCenters) {
			stringBuilder.append("MAX(").append(costCenter.trim()).append(") AS ").append(costCenter.trim())
					.append(", ");
		}

		// Remove the trailing comma and space
		String result = stringBuilder.substring(0, stringBuilder.length() - 2);
		System.out.println(result);

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
			System.out.println("yearfromCal" + yearfromCal);
		}
		Query selectQuery = entityManager.createNativeQuery(
				"select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS,\n"
						+ "BUYER_QTY,RECEIVED_TMT_QTY,UnitPrice from Indent_Transaction where COST_CENTER=:COST_CENTER and \n"
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
			// String smtpHostServer = "smtp-relay.gmail.com";

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
		String yearfromCal = cFY;
		if (getMonthNumber(MonthText) < 4) {
			cFY1 = cFY1 - 1; // If the month is before April, subtract 1 from the year
			yearfromCal = String.valueOf(cFY1);
			System.out.println("yearfromCal" + yearfromCal);
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

	public String sendToVendor(Map<String, Object> payload, String loginId) {
		String r = "";

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		List<Object> BuyerList;
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
			yearfromCal = String.valueOf(cFY);
			System.out.println("yearfromCal" + yearfromCal);
		}

		Query getCClist = entityManager.createNativeQuery(
				"select email_id from ER_User_Master where login_id  =:loginId UNION SELECT email_id FROM ER_User_Master WHERE userAccess = 1");
		Query getName = entityManager
				.createNativeQuery("select User_Name from ER_User_Master where login_id  =:loginId");
		getCClist.setParameter("loginId", loginId);
		getName.setParameter("loginId", loginId);
		String buyerName = (String) getName.getSingleResult();
		List<String> getBuyerName = getName.getResultList();
		List<String> CCList = getCClist.getResultList();
		// CCList.add("tgo@titan.co.in");

		try {
			String finalcc = getAllIndentedCostCenters(yearfromCal, MonthText);
			finalcc = finalcc.replace("[", "").replace("]", "");

			Object[] keys = payload.keySet().toArray();
			Object[] values = payload.values().toArray();

			if (keys.length > 1 && values.length > 1) {
				String secondKey = keys[2].toString();
				Object vendorsListObject = values[2];
				if (vendorsListObject instanceof List) {
					// Cast `vendorsListObject` to a list of maps (assuming each vendor is
					// represented by a map)
					List<Map<String, Object>> vendorsList = (List<Map<String, Object>>) vendorsListObject;

					// Iterate over the list of vendors
					for (Map<String, Object> vendor : vendorsList) {
						String vendorName = (String) vendor.get("vendorName");
						Query getVendorMailID = entityManager.createNativeQuery(
								"select distinct emailID from PRODUCT_MASTER where MAKE =:vendorName");
						getVendorMailID.setParameter("vendorName", vendorName);
						String vendorMailId = (String) getVendorMailID.getSingleResult();

						List<ArrayList<Object>> vendorData = (List<ArrayList<Object>>) vendor.get("data");
						String[] costCentersArray = finalcc.split(",");

						// Create headers array including "Sl No", "Material description", "UOM",
						// individual cost centers, and "Total Qty"
						List<String> headersList = new ArrayList<>();
						headersList.add("Sl No");
						headersList.add("Material description");
						headersList.add("UOM");
						headersList.addAll(Arrays.asList(costCentersArray)); // Add individual cost centers
						headersList.add("Total Qty");

						// Convert the list to an array
						Object[] headers = headersList.toArray();
						Map<String, List<Object>> indentData = new HashMap<>();

						Workbook workbook = new XSSFWorkbook();
						Sheet sheet = workbook.createSheet("DataTable");

						// Create a cell style for headers
						CellStyle headerStyle = workbook.createCellStyle();
						Font headerFont = workbook.createFont();
						headerFont.setBold(true);
						headerFont.setColor(IndexedColors.WHITE.getIndex()); // Set text color to white
						headerStyle.setFont(headerFont);
						headerStyle.setAlignment(HorizontalAlignment.CENTER);
						headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex()); // Set background color
						headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

						// Get the current date
						Date currentDate = new Date();

						// Create a SimpleDateFormat object with the desired date format
						SimpleDateFormat sdf = new SimpleDateFormat("MMMM_yyyy");

						// Format the current date using the SimpleDateFormat object
						String formattedDate = sdf.format(currentDate);

						// Insert headers into the sheet
						Row headerRow1 = sheet.createRow(0);

						// Add "STATIONERY REQUIREMENTS 27-03-2024" spanning two cells
						Cell firstCell = headerRow1.createCell(0);
						firstCell.setCellValue("Stationery_Requirements_" + formattedDate);
						firstCell.setCellStyle(headerStyle); // Apply header style

						// Merge the cells
						sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));

						// Adjust column width
						sheet.autoSizeColumn(0); // Auto-size first column width
						sheet.autoSizeColumn(1); // Auto-size second column width

						Row headerRow = sheet.createRow(2);

						for (int i = 0; i < headers.length; i++) {
							Cell cell = headerRow.createCell(i);
							cell.setCellValue(String.valueOf(headers[i]));
							cell.setCellStyle(headerStyle); // Apply header style
							sheet.autoSizeColumn(i); // Auto-size column width
						}

						// Auto-size columns
						for (int i = 0; i < headers.length; i++) {
							sheet.autoSizeColumn(i);
						}
						// Insert data into the sheet
						int rowNum = 3; // Start from the second row (index 1) after headers

						// Assuming vendorData is a List<Object[]> containing data for each vendor
						// Assuming rowDataList is a List<Object[]> containing the data to be inserted
						// into the sheet
						int serialNumber = 1; // Initialize serial number

						// Initialize an array to store the sum of each numeric column
						double[] columnSum = new double[vendorData.get(0).size()];

						// Iterate over each row in vendorData
						for (List<Object> rowData : vendorData) {
							Row row = sheet.createRow(rowNum++);
							int cellNum = 0; // Reset cell number for each row
							Cell cell = row.createCell(cellNum++); // First cell for serial number
							cell.setCellValue(serialNumber++); // Insert serial number
							sheet.autoSizeColumn(cellNum);
							// Iterate over each cell in the rowData
							for (Object cellData : rowData) {
								cell = row.createCell(cellNum++);
								if (cellData instanceof String) {
									String cellString = (String) cellData;
									try {
										double value = Double.parseDouble(cellString);
										cell.setCellValue(value);
										// Accumulate the sum for double values
										columnSum[cellNum - 2] += value; // Subtract 2 to adjust for the serial number
																			// column
									} catch (NumberFormatException e) {
										// Handle non-numeric string values
										cell.setCellValue(cellString); // Set string value directly
									}
								} else if (cellData instanceof Double) {
									double value = (Double) cellData;
									cell.setCellValue(value);
									// Accumulate the sum for double values
									columnSum[cellNum - 2] += value; // Subtract 2 to adjust for the serial number
																		// column
								} else if (cellData instanceof Integer) {
									int value = (Integer) cellData;
									cell.setCellValue(value);
									// Accumulate the sum for integer values
									columnSum[cellNum - 2] += value; // Subtract 2 to adjust for the serial number
																		// column
								} else {
									// Handle empty or non-numeric cells
									cell.setCellValue(""); // Empty cell value
								}
							}
						}

						// Create the last row for the sums
						Row sumRow = sheet.createRow(rowNum++);
						sumRow.createCell(0);
						Cell sumCell = sumRow.createCell(0);
						sumCell.setCellValue("");

						for (int i = 0; i < columnSum.length; i++) {
							if (i == 0) {
								// Skip setting value in the first cell (for the label)
								sumRow.createCell(i);
								Cell sumCell1 = sumRow.createCell(i + 1);
								sumCell1.setCellValue("Total Qty");
							} else {
								Cell sumCell1 = sumRow.createCell(i + 1);
								if (Double.compare(columnSum[i], 0.0) != 0.0) {
									sumCell1.setCellValue(columnSum[i]); // Insert sum for the corresponding column
								} else {
									sumCell1.setCellValue("");
								}
//	        				        sumCell1.setCellValue(columnSum[i]); // Insert sum for the corresponding column
							}
						}

						// Apply border style only to content
						CellStyle contentStyle = workbook.createCellStyle();
						contentStyle.setBorderTop(BorderStyle.THIN);
						contentStyle.setBorderBottom(BorderStyle.THIN);
						contentStyle.setBorderLeft(BorderStyle.THIN);
						contentStyle.setBorderRight(BorderStyle.THIN);
						for (Row row : sheet) {
							for (Cell cell : row) {
								if (cell.getRowIndex() > 0) {
									cell.setCellStyle(contentStyle);
								}
							}
						}

						// Create a SimpleDateFormat object with the desired date format
						SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM_yyyy");

						// Format the current date using the SimpleDateFormat object
						String formattedDate1 = sdf1.format(currentDate);

						File excelFile = File.createTempFile("Stationery_Requirements_" + formattedDate1, ".xlsx");
						FileOutputStream fileOut = new FileOutputStream(excelFile);
						workbook.write(fileOut);
						fileOut.close();
						String filePath = excelFile.getAbsolutePath();
						System.out.println("File Path: " + filePath);

						String smtpHostServer = "titan-co-in.mail.protection.outlook.com";
						Properties props = System.getProperties();
						props.put("mail.smtp.host", smtpHostServer);
						props.put("mail.smtp.port", "25");
						Session session = Session.getInstance(props, null);
						System.out.println(session);
						MimeMessage msg = new MimeMessage(session);
						msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in",
								"No Reply-stationary Employee Portal"));
						// msg.setReplyTo(InternetAddress.parse(key, false));
						msg.setSubject("Stationery_Requirements_" + formattedDate1);

						MimeMultipart multipart = new MimeMultipart();

						MimeBodyPart textPart = new MimeBodyPart();
						textPart.setText("Dear Sir / Madam,\n\n"
								+ "Pl. find attached the consolidated list of stationery items for the current month.\n"
								+ "\nRequest you to arrange all the stationery items and deliver the same in individual packings (cost center-wise) as per the attached list. Do mention the Purchase order number in the invoices.\n\n"
								+ "Double check the brand and qty. before delivery for on-time inward and payment.\n\n"
								+ "Inward will happen only if complete items are received at our facility in the same brands and qty. which are ordered. Request to check and validate the same before effecting delivery.\n\n\n"
								+ "Regards,\n\n" + buyerName + "\n" + "Sourcing Dept.\n" + "Watches and Wearables Div\n"
								+ "Titan Company Ltd.\n");
						multipart.addBodyPart(textPart);

						System.out.println("Email Body:\n");
						MimeBodyPart excelAttachment = new MimeBodyPart();
						DataSource source = new FileDataSource(excelFile.getAbsolutePath());
						excelAttachment.setDataHandler(new DataHandler(source));
						excelAttachment.setFileName(excelFile.getName());
						multipart.addBodyPart(excelAttachment);
						msg.setContent(multipart);
						msg.setSentDate(new Date());
						// Adding multiple recipients
//	        	            for (String email : emailList) {
						msg.addRecipient(Message.RecipientType.TO, new InternetAddress(vendorMailId));
						String emailBody = msg.getContent().toString();
//	        	            }
						for (String ccMail : CCList) {
							msg.addRecipient(Message.RecipientType.CC, new InternetAddress(ccMail));
						}
						// msg.addRecipient(Message.RecipientType.BCC, new
						// InternetAddress("nirajprasad@titan.co.in,masinenikrishnasai@titan.co.in,harsha_vardhan@titan.co.in,muralicr@titan.co.in,sanjeevi@titan.co.in"));

						Transport.send(msg);

					}
				}
			}
			r = "Email Sent Successfully!";

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
			sql = "select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,BUYER_QTY,UnitPrice,RECEIVED_TMT_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS from Indent_Transaction where \n"
					+ "COST_CENTER=:COST_CENTER and YEAR=:YEAR";
			Query query = entityManager.createNativeQuery(sql);

			query.setParameter("YEAR", Year);
			query.setParameter("COST_CENTER", loginid);
			response = query.getResultList();
		} else {
			sql = "select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,BUYER_QTY,UnitPrice,RECEIVED_TMT_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS from Indent_Transaction where \n"
					+ "month=:MONTH and YEAR=:YEAR and COST_CENTER=:COST_CENTER";
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
		/*
		 * if (getMonthNumber(Month) < 4) { cFY = cFY - 1; // If the month is before
		 * April, subtract 1 from the year yearfromCal = String.valueOf(cFY);
		 * System.out.println("yearfromCal"+yearfromCal); }
		 */
		boolean count = Validations.validation(Year);
		if (count)
			return new ArrayList<String[]>();
		String sql = "";
		List<String[]> response;
		if (Month.equalsIgnoreCase("All")) {
			sql = "select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,BUYER_QTY,unitprice,RECEIVED_TMT_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS from Indent_Transaction where YEAR =:YEAR";

			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("YEAR", Year);
			response = query.getResultList();
		} else {
			sql = "select DOC_NUMBER,COST_CENTER,ITEM,DEPARTMENT,TOTAL_USER_QTY,BUYER_QTY,unitprice,RECEIVED_TMT_QTY,DOC_DATE,VALUE,MONTH,YEAR,STATUS from Indent_Transaction where MONTH =:MONTH and YEAR =:YEAR";

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
	public String sendToStore(String loginId, String email_id, Map<String, Object> payload) {
		String r = "";

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		List<Object> BuyerList;
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
			yearfromCal = String.valueOf(cFY);
			System.out.println("yearfromCal" + yearfromCal);
		}

		Query getCClist = entityManager.createNativeQuery(
				"select Emailid from ABM_MASTER where EmpCode=:loginId UNION SELECT email_id FROM ER_User_Master WHERE userAccess = 1");
		Query getName = entityManager.createNativeQuery("select Name from ABM_MASTER where EmpCode=:loginId");
		getCClist.setParameter("loginId", loginId);
		getName.setParameter("loginId", loginId);
		String buyerName = (String) getName.getSingleResult();
		List<String> getBuyerName = getName.getResultList();
		List<String> CCList = getCClist.getResultList();
		// CCList.add("tgo@titan.co.in");
		CCList.add("nagarajun@titan.co.in");

		try {
			String finalcc = getAllIndentedCostCenters(yearfromCal, MonthText);
			finalcc = finalcc.replace("[", "").replace("]", "");

			Object[] keys = payload.keySet().toArray();
			Object[] values = payload.values().toArray();

			if (keys.length > 1 && values.length > 1) {
				String secondKey = keys[2].toString();
				Object vendorsListObject = values[2];
				if (vendorsListObject instanceof List) {
					// Cast `vendorsListObject` to a list of maps (assuming each vendor is
					// represented by a map)
//	                List<Map<String, Object>> vendorsList = (List<Map<String, Object>>) vendorsListObject;

					// Create headers array including "Sl No", "Material description", "UOM",
					// individual cost centers, and "Total Qty"
					List<String> headersList = new ArrayList<>();
					headersList.add("Sl No");
					headersList.add("Material description");
					headersList.add("UOM");
					headersList.add("Total Qty");

					// Convert the list to an array
					Object[] headers = headersList.toArray();
					Map<String, List<Object>> indentData = new HashMap<>();

					Workbook workbook = new XSSFWorkbook();
					Sheet sheet = workbook.createSheet("DataTable");

					// Create a cell style for headers
					CellStyle headerStyle = workbook.createCellStyle();
					Font headerFont = workbook.createFont();
					headerFont.setBold(true);
					headerFont.setColor(IndexedColors.WHITE.getIndex()); // Set text color to white
					headerStyle.setFont(headerFont);
					headerStyle.setAlignment(HorizontalAlignment.CENTER);
					headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex()); // Set background color
					headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

					// Get the current date
					Date currentDate = new Date();

					// Create a SimpleDateFormat object with the desired date format
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

					// Format the current date using the SimpleDateFormat object
					String formattedDate = sdf.format(currentDate);

					// Insert headers into the sheet
					Row headerRow1 = sheet.createRow(0);

					// Add "STATIONERY REQUIREMENTS 27-03-2024" spanning two cells
					Cell firstCell = headerRow1.createCell(0);
					firstCell.setCellValue("STATIONERY REQUIREMENTS " + formattedDate);
					firstCell.setCellStyle(headerStyle); // Apply header style

					// Merge the cells
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));

					// Adjust column width
					sheet.autoSizeColumn(0); // Auto-size first column width
					sheet.autoSizeColumn(1); // Auto-size second column width

					// Insert headers into the sheet

					Row headerRow = sheet.createRow(2);
					for (int i = 0; i < headers.length; i++) {
						Cell cell = headerRow.createCell(i);
						cell.setCellValue(String.valueOf(headers[i]));
					}

					// Auto-size columns
					for (int i = 0; i < headers.length; i++) {
						sheet.autoSizeColumn(i);
					}

					// Insert data into the sheet
					int rowNum = 3; // Start from the second row (index 1) after headers

					// Assuming vendorData is a List<Object[]> containing data for each vendor
					// Assuming rowDataList is a List<Object[]> containing the data to be inserted
					// into the sheet
					int serialNumber = 1; // Initialize serial number
					List<List<Object>> vendorsList = (List<List<Object>>) vendorsListObject;

					// Initialize an array to store the sum of each numeric column
					double[] columnSum = new double[vendorsList.get(0).size()];

					for (List<Object> rowData : vendorsList) {
						Row row = sheet.createRow(rowNum++);
						int cellNum = 0; // Reset cell number for each row
						Cell cell = row.createCell(cellNum++); // First cell for serial number
						cell.setCellValue(serialNumber++); // Insert serial number
						sheet.autoSizeColumn(cellNum);
						// Iterate over each cell in the rowData
						for (Object cellData : rowData) {
							cell = row.createCell(cellNum++);
							if (cellData instanceof String) {
								String cellString = (String) cellData;
								try {
									double value = Double.parseDouble(cellString);
									cell.setCellValue(value);
									// Accumulate the sum for double values
									columnSum[cellNum - 2] += value; // Subtract 2 to adjust for the serial number
																		// column
								} catch (NumberFormatException e) {
									// Handle non-numeric string values
									cell.setCellValue(cellString); // Set string value directly
								}
							} else if (cellData instanceof Double) {
								double value = (Double) cellData;
								cell.setCellValue(value);
								// Accumulate the sum for double values
								columnSum[cellNum - 2] += value; // Subtract 2 to adjust for the serial number column
							} else if (cellData instanceof Integer) {
								int value = (Integer) cellData;
								cell.setCellValue(value);
								// Accumulate the sum for integer values
								columnSum[cellNum - 2] += value; // Subtract 2 to adjust for the serial number column
							} else {
								// Handle empty or non-numeric cells
								cell.setCellValue(""); // Empty cell value
							}
						}
					}

					// Create the last row for the sums
					Row sumRow = sheet.createRow(rowNum++);
					sumRow.createCell(0);
					Cell sumCell = sumRow.createCell(0);
					sumCell.setCellValue("");

					for (int i = 0; i < columnSum.length; i++) {
						if (i == 0) {
							// Skip setting value in the first cell (for the label)
							sumRow.createCell(i);
							Cell sumCell1 = sumRow.createCell(i + 1);
							sumCell1.setCellValue("Total Qty");
						} else {
							Cell sumCell1 = sumRow.createCell(i + 1);
							if (Double.compare(columnSum[i], 0.0) != 0.0) {
								sumCell1.setCellValue(columnSum[i]); // Insert sum for the corresponding column
							} else {
								sumCell1.setCellValue("");
							}
//	        				        sumCell1.setCellValue(columnSum[i]); // Insert sum for the corresponding column
						}
					}

					// Apply border style only to content
					CellStyle contentStyle = workbook.createCellStyle();
					contentStyle.setBorderTop(BorderStyle.THIN);
					contentStyle.setBorderBottom(BorderStyle.THIN);
					contentStyle.setBorderLeft(BorderStyle.THIN);
					contentStyle.setBorderRight(BorderStyle.THIN);
					for (Row row : sheet) {
						for (Cell cell : row) {
							if (cell.getRowIndex() > 0) {
								cell.setCellStyle(contentStyle);
							}
						}
					}

					// Create a SimpleDateFormat object with the desired date format
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

					// Format the current date using the SimpleDateFormat object
					String formattedDate1 = sdf1.format(currentDate);

					File excelFile = File.createTempFile("Stationery_Recipt_" + formattedDate1, ".xlsx");
					FileOutputStream fileOut = new FileOutputStream(excelFile);
					workbook.write(fileOut);
					fileOut.close();
					String filePath = excelFile.getAbsolutePath();
					System.out.println("File Path: " + filePath);

					String smtpHostServer = "titan-co-in.mail.protection.outlook.com";
					Properties props = System.getProperties();
					props.put("mail.smtp.host", smtpHostServer);
					props.put("mail.smtp.port", "25");
					Session session = Session.getInstance(props, null);
					System.out.println(session);
					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress("noreply_stationary@titan.co.in",
							"No Reply-stationary Employee Portal"));
					// msg.setReplyTo(InternetAddress.parse(key, false));
					msg.setSubject(": Receipt and certification of Stationery items for inwarding.");

					MimeMultipart multipart = new MimeMultipart();

					MimeBodyPart textPart = new MimeBodyPart();
					textPart.setText("Dear Sir / Madam,\n\n"
							+ "Pl. find attached the list of stationery items received and accepted at our end for the current month.\n"
							+ "\nRequest to inward the same and confirm back to us for distributing the stationeries to the respective depts.\n\n"
							+ "Original invoice document will reach you at the earliest.\n\n"
							+ "Inward will happen only if complete items are received at our facility in the same brands and qty. which are ordered. Request to check and validate the same before effecting delivery.\n\n\n"
							+ "Regards,\n\n" + "Distribution team (Tray Mgmt Team).\n");
					multipart.addBodyPart(textPart);

					System.out.println("Email Body:\n");
					MimeBodyPart excelAttachment = new MimeBodyPart();
					DataSource source = new FileDataSource(excelFile.getAbsolutePath());
					excelAttachment.setDataHandler(new DataHandler(source));
					excelAttachment.setFileName(excelFile.getName());
					multipart.addBodyPart(excelAttachment);
					msg.setContent(multipart);
					msg.setSentDate(new Date());
					// Adding multiple recipients
//	        	            for (String email : emailList) {
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress("murugesan@titan.co.in"));
					String emailBody = msg.getContent().toString();
//	        	            }
					for (String ccMail : CCList) {
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress(ccMail));
					}
					// msg.addRecipient(Message.RecipientType.BCC, new
					// InternetAddress("nirajprasad@titan.co.in,masinenikrishnasai@titan.co.in,harsha_vardhan@titan.co.in,muralicr@titan.co.in,sanjeevi@titan.co.in"));

					Transport.send(msg);

//	                }
				}
			}

			r = "Email Sent Successfully!";

		}

		catch (Exception e) {
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
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		// SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		// String monthFromCal = String.format("%02d",
		// Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.valueOf(yearfromCal);
		if (getMonthNumber(MonthText) < 4) {
			cFY = cFY - 1; // If the month is before April, subtract 1 from the year
			yearfromCal = String.valueOf(cFY);
			System.out.println("yearfromCal" + yearfromCal);
		}

		List<String> allheadrers2 = getAllHeadersList(yearfromCal, MonthText);

		String yearfromCal1 = year_Date.format(cal.getTime());
		List<String> allheadrers = new ArrayList<>(); // Initialize the list

		allheadrers.add("Description");
		allheadrers.add("Vendors");
		allheadrers.add("UOM");
		allheadrers.addAll(allheadrers2);
		allheadrers.add("User Qty");
		allheadrers.add("MOQ Qty");
		allheadrers.add("MOQ Val(Rs)");
		allheadrers.add("Total Qty");
		allheadrers.add("Total Val(Rs)");
		allheadrers.add("Unit Price(Rs)");
		allheadrers.add("Stock At TMT (QTY)");
		allheadrers.add("STK Val(Rs)");

		try {
			Query getemailID = entityManager.createNativeQuery("select DISTINCT emailID from PRODUCT_MASTER ");
			List<String> emailIDPoduct = (List<String>) getemailID.getResultList();

			List<Object> BuyerList = getBuyerIndentList(yearfromCal, MonthText); // List of arrays, each array
																					// represents a row
			List<Object> footerList = getBuyerFooterList(yearfromCal, MonthText, yearfromCal1);
			// Each list represents a row

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Buyer");
			Row headerRow = sheet.createRow(0);

			for (int colIdx = 0; colIdx < allheadrers.size(); colIdx++) {
				Cell cell = headerRow.createCell(colIdx);
				cell.setCellValue(allheadrers.get(colIdx));
			}

			int rowIndex = 1; // Start from the second row
			for (Object obj : BuyerList) {
				Row row = sheet.createRow(rowIndex++);
				Object[] rowData = (Object[]) obj;
				for (int j = 0; j < rowData.length; j++) {
					Cell cell = row.createCell(j);
					// Replace null values with 0
					String cellValue = rowData[j] != null ? String.valueOf(rowData[j]) : "0";
					cell.setCellValue(cellValue);
				}
			}

			// Insert an empty row between buyer data and footer
			sheet.createRow(rowIndex++);

			// Insert footer data
			for (Object obj : footerList) {
				Row row = sheet.createRow(rowIndex++);
				Object[] rowData = (Object[]) obj;

				// Create a new array with increased size
				Object[] modifiedRowData = new Object[rowData.length + 2];

				// Copy values from original rowData to modifiedRowData
				System.arraycopy(rowData, 0, modifiedRowData, 0, 1); // Copy the first value

				// Insert two empty strings
				modifiedRowData[1] = ""; // Empty string
				modifiedRowData[2] = ""; // Empty string

				// Copy the rest of the values
				System.arraycopy(rowData, 1, modifiedRowData, 3, rowData.length - 1);

				// Populate the cells
				for (int j = 0; j < modifiedRowData.length; j++) {
					Cell cell = row.createCell(j);
					// Replace null values with 0
					String cellValue = modifiedRowData[j] != null ? String.valueOf(modifiedRowData[j]) : "0";
					cell.setCellValue(cellValue);
				}
			}

			File excelFile = File.createTempFile("all indenter data", ".xlsx");
			FileOutputStream fileOut = new FileOutputStream(excelFile);
			workbook.write(fileOut);
			fileOut.close();

			String filePath = excelFile.getAbsolutePath();
			System.out.println("File Path: " + filePath);

			// String smtpHostServer = "smtp-relay.gmail.com";
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
				textPart.setText("Dear Buyer,\n\n" + "Greetings!\n"
						+ "Please Find the indent value information in the portal.\n"
						+ "\nIMPORTANT: Please do not reply to this message or mail address.\n\n"
						+ "DISCLAIMER: This communication is confidential and privileged and is directed to and for the use of the addressee only. The recipient if not the addressee should not use this message if erroneously received, and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized. The recipient acknowledges that Titan Company Pvt Ltd may be unable to exercise control or ensure or guarantee the integrity of the text of the email message and the text is not warranted as to completeness and accuracy. Before opening and accessing the attachment, if any, please check and scan for a virus.\n\n\n"
						+ "Thanks & Regards,\n" + "Murali B R\n" + "Stationary Portal.");
				multipart.addBodyPart(textPart);
				MimeBodyPart excelAttachment = new MimeBodyPart();
				DataSource source = new FileDataSource(excelFile.getAbsolutePath());
				excelAttachment.setDataHandler(new DataHandler(source));
				excelAttachment.setFileName(excelFile.getName());
				multipart.addBodyPart(excelAttachment);
				msg.setContent(multipart);
				msg.setSentDate(new Date());
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress("muralicr@titan.co.in"));
//				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
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
		System.out.println("murali7");
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
			String LOCATION, String Department, String CCowner, String YEARLYBUDGET, String loginId, String COSTEMAIL) {
		String permitNumber = "";
		System.out.println("ccowner" + CCowner);
		try {

			System.out.println("Product is already available." + CCID);

			String labelExistanceSQL = "SELECT COUNT(*) FROM budget_master WHERE ccid=:CCID and Year=:Year";
			Query labelExistanceQuery = entityManager.createNativeQuery(labelExistanceSQL);
			labelExistanceQuery.setParameter("CCID", CCID);
			labelExistanceQuery.setParameter("Year", Year);
			int isExistance = (int) labelExistanceQuery.getSingleResult();

			/**
			 * Here we are validating whether the cost center is already available in indent
			 * manager or not.
			 */

			String ccidExistanceSQL = "SELECT COUNT(*) FROM INDENT_MANAGER WHERE EmpCode=:CCID";
			Query ccidExistanceQuery = entityManager.createNativeQuery(ccidExistanceSQL);
			ccidExistanceQuery.setParameter("CCID", CCID);
			int isccidExistance = (int) ccidExistanceQuery.getSingleResult();

			if (isccidExistance == 0) {
				Query insetIntoIndentManager = entityManager.createNativeQuery("INSERT INTO INDENT_MANAGER (EmpCode,\n"
						+ "    Password,\n" + "    EmpName,\n" + "    StoreCode,\n" + "    MobileNumber,\n"
						+ "    DateOfJoin,\n" + "    dateofleaving,\n" + "    isactive,\n" + "    changedBy,\n"
						+ "    Changedon,\n" + "    CreatedBY,\n" + "    CreatedOn,\n" + "    LMSID,\n" + "    Email,\n"
						+ "    abm,\n" + "    region\n" + ")\n" + "VALUES (\n" + "    :EmpCode,\n" + "    :Password,\n"
						+ "    :EmpName,\n" + "    :StoreCode,\n" + "    :MobileNumber,\n" + "    NULL,\n"
						+ "    NULL,\n" + "    1, \n" + "    :CreatedBy,\n" + "    NULL,\n" + "    :CreatedBy,\n"
						+ "    :CreatedOn,\n" + "    :CCID,\n" + "    :CostEmail,\n" + "    NULL,\n" + "    :region\n"
						+ ")");
				insetIntoIndentManager.setParameter("CCID", CCID);
				insetIntoIndentManager.setParameter("EmpCode", CCID);
				insetIntoIndentManager.setParameter("Password", "GH7Yz0xddaPTsXa01bcc4w==");
				insetIntoIndentManager.setParameter("EmpName", CCowner);
				insetIntoIndentManager.setParameter("StoreCode", Department);
				insetIntoIndentManager.setParameter("MobileNumber", null);
				// insetIntoIndentManager.setParameter("MobileNumber",null);
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
				return messBuilder.append("price " + productMasterbean.getPrice() + " column has invalid character.");
			}

			specalChar = Validations.validation(productMasterbean.getUOM());
			if (specalChar) {
				return messBuilder.append("UOM " + productMasterbean.getUOM() + " column has invalid character.");
			}
			specalChar = Validations.validation(productMasterbean.getCategory());
			if (specalChar) {
				return messBuilder
						.append("Category " + productMasterbean.getCategory() + " column has invalid character.");
			}

			boolean isproductDuplicated = validateDuplicationforProduct(productMasterbean.getProductid());
			if (isproductDuplicated) {
				return messBuilder.append(productMasterbean.getProductid() + " is already available");
			}

		}
		messBuilder.append(insertProductMasterData(abmDetailList, loginId));

		return messBuilder;
	}

	/***
	 * This method is used to check the duplicate enntries in excel sheet.
	 * 
	 * @param month
	 * @param year
	 * @param CCid
	 * @return
	 */
	private boolean validateDuplicationforProduct(String product_number) {
		String empCodeExistenceSQL = "select COUNT(*) from PRODUCT_MASTER where product_number =:product_number";
		Query empCodeExistenceQuery = entityManager.createNativeQuery(empCodeExistenceSQL);
		empCodeExistenceQuery.setParameter("product_number", product_number);
		int empCodeCount = ((Number) empCodeExistenceQuery.getSingleResult()).intValue();
		return empCodeCount > 0;
	}

	private String insertProductMasterData(List<productMasterbean> abmDetailList, String loginId) {
		String response = "";
		try {

			for (int j = 0; j < abmDetailList.size(); j++) {
				productMasterbean abmUserMaster = abmDetailList.get(j);

				String getcateId = "SELECT CATORDERS FROM PRODUCT_MASTER WHERE CATEGORY=:Category";
				Query cateId = entityManager.createNativeQuery(getcateId);
				cateId.setParameter("Category", abmUserMaster.getCategory());
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

				Query productmaster = entityManager.createNativeQuery("INSERT INTO PRODUCT_MASTER\n"
						+ "(PRODUCT_NUMBER,PROD_NAME,PROD_DESC,MAKE,UCP,UOM,ISACTIVE,SELFLIFE\n"
						+ ",CATEGORY,PRICE_DATE,ISMOQ,IS_IMAGE,MODIFIED_DATETIME,MODIFIED_BY\n"
						+ ",CREATED_BY,CREATED_DATETIME,TO_PRICE_DATE,emailID,CATORDERS)"
						+ " VALUES(:ProductID,:ProductName,:ProductName,:vendor,:Price,:UOM,"
						+ ":IsActive,NULL,:Category,NULL,0,0,NULL,NULL,:CreatedBy,:CreatedOn,NULL,:emailID,:catID)");

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
				productmaster.setParameter("catID", catID);

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
				return messBuilder.append("Cost centre " + poEntryBean.getCCID() + "  column has invalid character.");
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
				return messBuilder.append("GLDescription " + poEntryBean.getMonth() + " column has invalid character.");
			}

			boolean empCodeExists = validateDuplication(poEntryBean.getMonth(), poEntryBean.getYear(),
					poEntryBean.getCCID());
			if (empCodeExists) {
				return messBuilder.append(poEntryBean.getCCID() + "is already created for the month"
						+ poEntryBean.getMonth() + " and for the year" + poEntryBean.getYear() + "<br>");
			}
			boolean isCCICExistsInBudgetmaster = isCCICExistsInBudgetmaster(poEntryBean.getCCID());
			if (!isCCICExistsInBudgetmaster) {
				return messBuilder.append("The Cost Center " + poEntryBean.getCCID() + " is not Created");
			}
		}
		messBuilder.append(insertPoEntryData(PoEntryBeanList, loginId));

		return messBuilder;
	}

	/***
	 * This method is used to check the duplicate enntries in excel sheet.
	 * 
	 * @param month
	 * @param year
	 * @param CCid
	 * @return
	 */
	private boolean validateDuplication(String month, String year, String CCid) {
		String empCodeExistenceSQL = "select COUNT(*) from PO_Entry where Year=:year and COST_CENTER=:ccid and MONTH=:month";
		Query empCodeExistenceQuery = entityManager.createNativeQuery(empCodeExistenceSQL);
		empCodeExistenceQuery.setParameter("year", year);
		empCodeExistenceQuery.setParameter("ccid", CCid);
		empCodeExistenceQuery.setParameter("month", month);
		int empCodeCount = ((Number) empCodeExistenceQuery.getSingleResult()).intValue();
		return empCodeCount > 0;
	}

	/**
	 * THis method is used to check the whether cost center is valid or not.
	 * 
	 * @param CCid
	 * @return
	 */
	private boolean isCCICExistsInBudgetmaster(String CCid) {
		try {
			String emailExistenceSQL = "select COUNT(*) from BUDGET_MASTER where CCID=:ccid";
			Query emailExistenceQuery = entityManager.createNativeQuery(emailExistenceSQL);
			emailExistenceQuery.setParameter("ccid", CCid);

			int count = ((Number) emailExistenceQuery.getSingleResult()).intValue();
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
				System.out.println(
						"arrayeeee" + poEntryMaster.getYear() + poEntryMaster.getMonth() + poEntryMaster.getCCID());
				Query insertPoEntry = entityManager.createNativeQuery(
						"Insert Into PO_Entry (Year,cost_Center,Month,poamount,CreatedBy,CreatedOn,monthnumber)\n"
								+ "VALUES(:Year,:CCID,:MONTH,:POAMOUNT,:CreatedBy,:CreatedOn,:monthNumner)");
				insertPoEntry.setParameter("CCID", poEntryMaster.getCCID());
				insertPoEntry.setParameter("Year", poEntryMaster.getYear());
				insertPoEntry.setParameter("POAMOUNT", poEntryMaster.getPOAmount());
				insertPoEntry.setParameter("MONTH", poEntryMaster.getMonth());
				insertPoEntry.setParameter("CreatedBy", loginId);
				insertPoEntry.setParameter("CreatedOn", new Date());
				insertPoEntry.setParameter("monthNumner", getMonthNumber(poEntryMaster.getMonth()));
				int intresponse = insertPoEntry.executeUpdate();
				if (intresponse != 0) {
					System.out.println("indent updated successfully");
					System.out.println("<<<<<<<<<<< WP created successfully>>>>>>>>>>>");
					response = "uploaded successfully";
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
		Query selectQuery = entityManager.createNativeQuery("SELECT CCID " + "    FROM BUDGET_MASTER \n");
		// + " WHERE year=:YEAR and (BUDVALUERSL = '' OR BUDVALUERSL IS NULL)");
		// selectQuery.setParameter("YEAR", cFY);
		try {
			getDesignationDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getDesignationDetails = null;
		}
		return getDesignationDetails;
	}

	@Override
	public List<Object> getBuyerIndentListForvendor(String Year, String Month) {
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
			System.out.println("yearfromCal" + yearfromCal + MonthText);
		}
		String finalcc = getAllIndentedCostCenters(yearfromCal, Month);

		List<Object> getAllUserDetails = null;
		if (finalcc.length() == 2) {
			return getAllUserDetails;
		}
		Query getresultlist = entityManager.createNativeQuery("sELECT \n"
				+ " ROW_NUMBER() OVER (ORDER BY PROD_NAME DESC) AS 'Sl No',\n"
				+ "PROD_NAME as 'Material description', UOM," + finalcc + ",TOTAL_USER_QTY as 'TOTAL QTY'\n"
				+ "		FROM (\n"
				+ "		SELECT PROD_NAME, UOM, COST_CENTER, ISNULL(BUYER_QTY , 0) AS cost_value,ucp,TOTAL_USER_QTY\n"
				+ "		FROM PRODUCT_MASTER pm \n" + "		left join Indent_Transaction it on pm.PROD_NAME=it.ITEM\n"
				+ "		WHERE COST_CENTER IS NOT NULL and month=:Month and year=:Year\n"
				+ "		GROUP BY PROD_NAME, UOM, COST_CENTER,BUYER_QTY,ucp,TOTAL_USER_QTY\n" + "		) AS src\n"
				+ "		PIVOT (MAX(cost_value)\n" + "		FOR COST_CENTER IN (" + finalcc + ")) AS pivottable\n"
				+ "		order by PROD_NAME desc");

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
	public List<String> getAllHeadersList(String Year, String Month) {

		List<String> getAllUserDetails;
		String finalcc;
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

	/**
	 * Gokul Get budget details
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getholidaymasterData(String userId) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());
		List<Object> getBudgetDetails;
		String currentYearForPoEntry = null;
		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.parseInt(yearfromCal);

		Query selectQuery = entityManager.createNativeQuery(
				"select holiday_date,Holiday_day from Holiday_Master where Year =:Year and Holidaymonth =:monthNumner");
		selectQuery.setParameter("monthNumner", getMonthNumber(MonthText));
		selectQuery.setParameter("Year", cFY);

		try {
			getBudgetDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getBudgetDetails = null;
		}
		return getBudgetDetails;
	}

	/**
	 * murali Get budget details
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> portalBlcokingMechanism(String userId) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());
		List<Object> getBudgetDetails;
		String currentYearForPoEntry = null;
		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());
		int cFY = Integer.parseInt(yearfromCal);

		Query selectQuery = entityManager.createNativeQuery(
				"\n" + " SELECT value\n" + "FROM ISCM_ACCESS  where allow = 'portalBlcokingMechanism';");
		try {
			getBudgetDetails = selectQuery.getResultList();
		} catch (HibernateException e) {

			e.printStackTrace();
			getBudgetDetails = null;
		}
		return getBudgetDetails;
	}

	@Override
	public MasterData GetMasterData(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		MasterData result = getDataforMaster(filter);

		return result;

	}

	private MasterData getDataforMaster(MonthlyDataFilter filter) {
		MasterData data = new MasterData();
		List<Brand> brandName = selectBrandforMaster();
		List<Region> regionName = selectRegionforMaster();
		List<RSName> rsName = selectRsNameForMaster();
		List<ABMName> ABMName = selectABMNameForMaster();

		data.setBrand(brandName);
		// data.setRegion(regionName);
		data.setRsName(rsName);
		data.setAbmName(ABMName);
		if (!filter.getRegionList().isEmpty() || !filter.getAbmName().isEmpty()) {
			MasterData filterData = getFilterData(filter);
			return filterData;
		}

		return data;

	}

	private List<Brand> selectBrandforMaster() {
		List<Brand> brandName = null;
		String checkSql = "SELECT * FROM MBRBrand order by BrandName";
		try {
			Query checkQuery = entityManager.createNativeQuery(checkSql);
			brandName = checkQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return brandName;

	}

	private List<Region> selectRegionforMaster() {
		List<Region> regionName = null;
		String checkSql = "select distinct (Region) from MBROrders;";
		Query checkQuery = entityManager.createNativeQuery(checkSql);
		regionName = checkQuery.getResultList();
		return regionName;

	}

	private List<RSName> selectRsNameForMaster() {
		List<RSName> rsName = null;
//		String checkSql = "\r\n"
//				+ "SELECT DISTINCT \r\n"
//				+ "    MBROrders.RSName, \r\n"
//				+ "    MBROrders.Region, \r\n"
//				+ "    CASE\r\n"
//				+ "        WHEN ABMEMMUser.Name IS NOT NULL THEN ABMEMMUser.Name\r\n"
//				+ "        WHEN ABMKAMUser.Name IS NOT NULL THEN ABMKAMUser.Name\r\n"
//				+ "        ELSE 'No Name'  -- Optional, in case both are NULL\r\n"
//				+ "    END AS ABM_EMMName,  -- Changed alias to ABM_EMMName\r\n"
//				+ "    CASE\r\n"
//				+ "        WHEN ABMEMMUser.UserName IS NOT NULL THEN ABMEMMUser.UserName\r\n"
//				+ "        WHEN ABMKAMUser.UserName IS NOT NULL THEN ABMKAMUser.UserName\r\n"
//				+ "        ELSE 'No Name'  -- Optional, in case both are NULL\r\n"
//				+ "    END AS ABM_KAMName  -- Changed alias to ABM_KAMName\r\n"
//				+ "FROM \r\n"
//				+ "    MBROrders\r\n"
//				+ "LEFT JOIN \r\n"
//				+ "    MBRUsers AS ABMEMMUser\r\n"
//				+ "    ON MBROrders.ABMEMM = ABMEMMUser.UserName\r\n"
//				+ "LEFT JOIN \r\n"
//				+ "    MBRUsers AS ABMKAMUser\r\n"
//				+ "    ON MBROrders.ABMKAM = ABMKAMUser.UserName;";

		String checkSql = "SELECT Name, UserName, Region from MBRUsers where Desig_Id = 5 order by Name";

		Query checkQuery = entityManager.createNativeQuery(checkSql);
		rsName = checkQuery.getResultList();
		return rsName;

	}

	private List<ABMName> selectABMNameForMaster() {
		List<ABMName> ABMName = null;
		String checkSql = "select UserName, Name, Region from MBRUsers (nolock) where Desig_Id=7 or Desig_Id=6 order by Name;";
		Query checkQuery = entityManager.createNativeQuery(checkSql);
		ABMName = checkQuery.getResultList();
		return ABMName;
	}

	@Override
	public List<OutputForMontlyFilter> MonthlyTrend(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		List<OutputForMontlyFilter> result = new ArrayList<>();
		result = getDataForMonthlyTrend(filter);
		return result;

	}

	private List<OutputForMontlyFilter> getDataForMonthlyTrend(MonthlyDataFilter filter) {
		List<OutputForMontlyFilter> filteredData = new ArrayList<>();

		// Stored Procedure call
		String storedProcedureCall = "EXEC GetOrderSummary @RegionList = :regionList, "
				+ "@StartDate = :startDate, @EndDate = :endDate, "
				+ "@BrandList = :brandList, @RSNameList = :rsNameList, "
				+ "@ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Format region list if necessary (assuming stored procedure expects it this
		// way)
		String formattedRegionList = filter.getRegionList(); // 'EAST, WEST, NORTH, SOUTH 1, SOUTH 2'

		// Handling empty string parameters and setting them to NULL where needed
		String brandList = filter.getBrandList().isEmpty() ? null : filter.getBrandList();
		String rsNameList = filter.getRsNameList().isEmpty() ? null : filter.getRsNameList();
		String abmName = filter.getAbmName().isEmpty() ? null : filter.getAbmName();
		String retailerType = filter.getRetailerType().isEmpty() ? null : filter.getRetailerType();

		// Debugging/logging before executing
		System.out.println("Executing Stored Procedure: " + storedProcedureCall);
		System.out.println("Region List: " + formattedRegionList);
		System.out.println("Start Date: " + filter.getStartDate());
		System.out.println("End Date: " + filter.getEndDate());
		System.out.println("Brand List: " + brandList); // should be null if empty
		System.out.println("RS Name List: " + rsNameList); // should be null if empty
		System.out.println("ABM Name: " + abmName); // should be null if empty
		System.out.println("Retailer Type: " + retailerType); // should be null if empty

		// Set parameters for the stored procedure
		query.setParameter("regionList", formattedRegionList);
		query.setParameter("startDate", filter.getStartDate());
		query.setParameter("endDate", filter.getEndDate());
		query.setParameter("brandList", brandList); // Pass NULL if empty
		query.setParameter("rsNameList", rsNameList); // Pass NULL if empty
		query.setParameter("abmName", abmName); // Pass NULL if empty
		query.setParameter("retailerType", retailerType); // Pass NULL if empty

		try {
			List<Object[]> result = query.getResultList();

			// Map each row to OutputForMontlyFilter
			for (Object[] row : result) {
				OutputForMontlyFilter data = new OutputForMontlyFilter();

				// Ensure safe casting and row validation
				if (row.length >= 5) {
					data.setMonth((Integer) row[1]);
					data.setTotalRevenue((BigDecimal) row[2]);
					data.setTotalQTY((Integer) row[3]);
					data.setTotalRetailerCode((Integer) row[4]);
					filteredData.add(data);
				} else {
					System.err.println("Unexpected result format for row: " + Arrays.toString(row));
				}
			}
		} catch (Exception e) {
			// Handle the exception with better logging
			e.printStackTrace();
			// Optionally log more details about the exception, e.g., the parameters or
			// query
		}

		return filteredData;
	}

	@Override
	public List<Object> monthlyToalOrdaringData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OutputGrowthOverPreviousMonth> GrowthOverPreviousMonth(MonthlyDataFilter filter) {
		List<OutputGrowthOverPreviousMonth> growthOverPreviousMonthData = new ArrayList<>();
		String storedProcedureCall = "EXEC GrowthOverPreviousMonth @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());

		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputGrowthOverPreviousMonth data = new OutputGrowthOverPreviousMonth();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setTotalRevenue((BigDecimal) row[2]);
				data.setTotalQTY((Integer) row[3]);
				data.setTotalRetailerCode((Integer) row[4]);
				data.setRetailerGrowthPercentage((BigDecimal) row[5]);
				data.setPriceGrowth((BigDecimal) row[6]);
				data.setOrderGrowth((Integer) row[7]);
				data.setRetailerGrowth((Integer) row[8]);
				data.setPriceGrowthPercentage((BigDecimal) row[9]);
				data.setOrderQtyGrowthPercentage((BigDecimal) row[10]);

				growthOverPreviousMonthData.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return growthOverPreviousMonthData;

	}

	@Override
	public List<OutputRegionWiseMonthlyDistribution> RegionWiseMonthlyDistribution(MonthlyDataFilter filter) {
		List<OutputRegionWiseMonthlyDistribution> regionWiseMonthlyDistributionData = new ArrayList<>();
		String storedProcedureCall = "EXEC RegionWiseMonthlyDistribution @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType ";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputRegionWiseMonthlyDistribution data = new OutputRegionWiseMonthlyDistribution();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setTotalRevenue((BigDecimal) row[2]);
				data.setTotalQTY((Integer) row[3]);
				data.setTotalRetailerCode((Integer) row[4]);
				data.setRegion(row[5].toString());
				regionWiseMonthlyDistributionData.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return regionWiseMonthlyDistributionData;

	}

	@Override
	public List<OutputRegionWiseGrowthOverPreviousMonth> RegionWiseGrowthOverPreviousMonth(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		List<OutputRegionWiseGrowthOverPreviousMonth> regionWiseMonthlyGrowthData = new ArrayList<>();
		String storedProcedureCall = "EXEC RegionWiseGrowthoverPreviousMonths @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputRegionWiseGrowthOverPreviousMonth data = new OutputRegionWiseGrowthOverPreviousMonth();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setRegion(row[2].toString());
				data.setTotalRevenue((BigDecimal) row[3]);
				data.setTotalQTY((Integer) row[4]);
				data.setTotalRetailerCode((Integer) row[5]);
				data.setPriceGrowthPercentage((BigDecimal) row[6]);
				data.setOrderQtyGrowthPercentage((BigDecimal) row[7]);
				data.setRetailerGrowthPercentage((BigDecimal) row[8]);
				data.setPriceGrowth((BigDecimal) row[9]);
				data.setOrderGrowth((Integer) row[10]);
				data.setRetailerGrowth((Integer) row[11]);
				regionWiseMonthlyGrowthData.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return regionWiseMonthlyGrowthData;

	}

	@Override
	public List<OutputDashboardTiles> OutputDashboardTiles(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		List<OutputDashboardTiles> outputDashboardTiles = new ArrayList<>();
		String checkQuery = "SELECT \r\n" + "    -- Extract Year and Month to group by month\r\n"
				+ "    YEAR(OrderDate) AS Year,\r\n" + "    MONTH(OrderDate) AS Month,\r\n"
				+ "    SUM(TotalPrice) AS OrderValue,\r\n" + "    SUM(OrderQty) AS TotalOrder,\r\n"
				+ "    COUNT(DISTINCT OrderNo) AS OrderQty,\r\n" + "    COUNT(DISTINCT RetailerCode) AS Dealers,\r\n"
				+ "    Region\r\n" + "FROM \r\n" + "    MBROrders\r\n" + "WHERE \r\n"
				+ "    CONVERT(VARCHAR, OrderDate, 112) >= :startDate    -- Compare OrderDate with StartDate\r\n"
				+ "    AND CONVERT(VARCHAR, OrderDate, 112) <= :endDate \r\n" + "GROUP BY\r\n"
				+ "    YEAR(OrderDate),\r\n" + "    MONTH(OrderDate),\r\n" + "    Region\r\n" + "ORDER BY\r\n"
				+ "    YEAR(OrderDate),\r\n" + "    MONTH(OrderDate);";

		// Create a native query
		Query query = entityManager.createNativeQuery(checkQuery);

		// Set the parameters for the stored procedure call
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		// @RSNameList (e.g., '' or some value)

		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputDashboardTiles data = new OutputDashboardTiles();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setOrderValue((BigDecimal) row[2]);
				data.setTotalOrder((Integer) row[3]);
				data.setOrderQuentity((Integer) row[4]);
				data.setDelears((Integer) row[5]);
				data.setRegion(row[6].toString());
				outputDashboardTiles.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputDashboardTiles;

	}

	@Override
	public List<OutputDashboardGraphs> OutputDashboardGraphs(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		List<OutputDashboardGraphs> outputDashboardGraphs = new ArrayList<>();
		String checkQuery = "SELECT \r\n" + "        -- Extract Year and Month to group by month\r\n"
				+ "        YEAR(OrderDate) AS Year,\r\n" + "        MONTH(OrderDate) AS Month,\r\n"
				+ "        SUM(TotalPrice) AS OrderValue,\r\n" + "        SUM(OrderQty) AS TotalOrder\r\n"
				+ "    FROM \r\n" + "        MBROrders\r\n" + "	Where \r\n"
				+ "			CONVERT(VARCHAR, OrderDate, 112) >= :startDate                        -- Compare OrderDate with StartDate\r\n"
				+ "            AND CONVERT(VARCHAR, OrderDate, 112) <= :endDate\r\n" + "	Group BY\r\n"
				+ "	 YEAR(OrderDate),\r\n" + "        MONTH(OrderDate)\r\n" + "    ORDER BY\r\n"
				+ "        YEAR(OrderDate),\r\n" + "        MONTH(OrderDate);";

		// Create a native query
		Query query = entityManager.createNativeQuery(checkQuery);

		// Set the parameters for the stored procedure call
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		// @RSNameList (e.g., '' or some value)

		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputDashboardGraphs data = new OutputDashboardGraphs();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setOrderValue((BigDecimal) row[2]);
				data.setTotalOrder((Integer) row[3]);
				outputDashboardGraphs.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputDashboardGraphs;

	}

	@Override
	public MasterData getFilterData(MonthlyDataFilter data) {
		List<RSName> rsName = new ArrayList<>();
		List<ABMName> ABMName = new ArrayList<>(); // Make sure ABMName is initialized

		MasterData dataoutput = new MasterData();

		List<String> regionList = Arrays.stream(data.getRegionList().split(",")).map(String::trim) // Trim spaces around
																									// each region
				.collect(Collectors.toList());

		if (!data.getRegionList().isEmpty() && data.getAbmName().isEmpty()) {
			String checkSql = "SELECT UserName, Name, Region FROM MBRUsers " + "WHERE Region IN (:region) "
					+ "AND (desig_id = 6 OR desig_id = 7) order by Name";
			Query checkQuery = entityManager.createNativeQuery(checkSql);
			checkQuery.setParameter("region", regionList);

			// Log the region list for debugging
			System.out.println("Region List: " + data.getRegionList());

			// Execute the query and get the result list
			List<ABMName> resultList = checkQuery.getResultList();

			// Process each result row to map to ABMName objects
//		    for (Object[] row : resultList) {
//		        ABMName abm = new ABMName();
//		        abm.setName((String) row[0]); // Assuming Name is the first column
//		        abm.setUserName((String) row[1]); // Assuming UserName is the second column
//
//		        ABMName.add(abm); // Add to the list
//		    }
			rsName = selectRsNameForMaster();
			// Set the ABMName list into dataoutput
			dataoutput.setAbmName(resultList);
			dataoutput.setRsName(rsName);
			// Return the populated MasterData object
			return dataoutput;
		}

		else if (!data.getRegionList().isEmpty() && !data.getAbmName().isEmpty()) {

			String checksql = "SELECT Name, UserName, Region FROM MBRUsers WHERE Region IN (:region)"
					+ "AND Desig_Id = 5 AND (ABMEMM = :ABMName OR ABMKAM = :ABMName) order by Name;";
			Query checkQuery = entityManager.createNativeQuery(checksql);
			checkQuery.setParameter("region", regionList);
			checkQuery.setParameter("ABMName", data.getAbmName());
			// rsName=checkQuery.getResultList();

			List<RSName> resultList = checkQuery.getResultList();
//			for (Object[] row : resultList) {
//				RSName rsm = new RSName();
//				rsm.setRsName((String) row[0]); // Assuming Name is the first column
//				rsm.setRegion((String) row[1]); // Assuming UserName is the second column
//				rsm.setUserName((String) row[1]);
//
//				rsName.add(rsm); // Add to the list
//		    }
			dataoutput.setRsName(resultList);
			return dataoutput;
		}
		return dataoutput;
	}

	@Override
	public List<OutputMonthlyOrdaringBehaviour> monthlyOrdaringBehaviour(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		List<OutputMonthlyOrdaringBehaviour> monthlyOrdaringBehaviourData = new ArrayList<>();
		String storedProcedureCall = "EXEC MonthlyOrdaringBehaviour @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputMonthlyOrdaringBehaviour data = new OutputMonthlyOrdaringBehaviour();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setNoOforders((Integer) row[2]);
				data.setAvgQtyPerOrder((BigDecimal) row[3]);
				data.setAvgValuePerOrder((BigDecimal) row[4]);
				monthlyOrdaringBehaviourData.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return monthlyOrdaringBehaviourData;

	}

	@Override
	public List<OutputRegionWiseMonthlyDistributionNoofOrders> regionWiseMonthlyDistributionNoofOrders(
			MonthlyDataFilter filter) {
		List<OutputRegionWiseMonthlyDistributionNoofOrders> regionWiseMonthlyDistributionData = new ArrayList<>();
		String storedProcedureCall = "EXEC RegionWiseMonthlyDistributionNoofOrders @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputRegionWiseMonthlyDistributionNoofOrders data = new OutputRegionWiseMonthlyDistributionNoofOrders();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setNoOfOrders((Integer) row[2]);
				data.setRegion(row[3].toString());
				data.setNoOfOrdersPercentage((BigDecimal) row[4]);
				// Now, filteredData is populated with values
				regionWiseMonthlyDistributionData.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return regionWiseMonthlyDistributionData;
	}

	@Override
	public List<OutputRegionWiseMonthlyAvgPerOrder> regionWiseMonthlyAvgPerOrder(MonthlyDataFilter filter) {
		List<OutputRegionWiseMonthlyAvgPerOrder> regionWiseMonthlyAvgPerOrder = new ArrayList<>();
		String storedProcedureCall = "EXEC RegionWiseMonthlyAvgPerOrder @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputRegionWiseMonthlyAvgPerOrder data = new OutputRegionWiseMonthlyAvgPerOrder();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setAvgQtyPerOrder((Integer) row[2]);
				data.setAvgPricePerOrder((BigDecimal) row[3]);
				data.setRegion(row[4].toString());
				// Now, filteredData is populated with values
				regionWiseMonthlyAvgPerOrder.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return regionWiseMonthlyAvgPerOrder;
	}

	@Override
	public List<OutputPercentageofOrdersbyDayoftheMonth> percentageofOrdersbyDayoftheMonth(MonthlyDataFilter filter) {
		List<OutputPercentageofOrdersbyDayoftheMonth> percentageofOrdersbyDayoftheMonth = new ArrayList<>();
		String storedProcedureCall = "EXEC PercentageofOrdersbyDayoftheMonth @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputPercentageofOrdersbyDayoftheMonth data = new OutputPercentageofOrdersbyDayoftheMonth();
				data.setDay((Integer) row[0]);
				data.setDistinctOrderCount((Integer) row[1]);
				data.setPercentageOfOrders((BigDecimal) row[2]);
				// Now, filteredData is populated with values
				percentageofOrdersbyDayoftheMonth.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return percentageofOrdersbyDayoftheMonth;
	}

	@Override
	public List<OutputPercentageofOrdersbyWeekdayorWeekend> percentageofOrdersbyWeekdayorWeekend(
			MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		List<OutputPercentageofOrdersbyWeekdayorWeekend> percentageofOrdersbyWeekdayorWeekend = new ArrayList<>();
		String storedProcedureCall = "EXEC PercentageofOrdersbyWeekdayorWeekend @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputPercentageofOrdersbyWeekdayorWeekend data = new OutputPercentageofOrdersbyWeekdayorWeekend();
				data.setMonth((Integer) row[0]);
				data.setDayType(row[1].toString());
				data.setDistinctOrderCount((Integer) row[2]);
				data.setPercentageOfOrders((BigDecimal) row[3]);
				// Now, filteredData is populated with values
				percentageofOrdersbyWeekdayorWeekend.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return percentageofOrdersbyWeekdayorWeekend;
	}

	@Override
	public List<OutputPercentageofOrdersByWeekdayorWeekendRegionWise> percentageofOrdersByWeekdayorWeekendRegionWise(
			MonthlyDataFilter filter) {
		List<OutputPercentageofOrdersByWeekdayorWeekendRegionWise> percentageofOrdersByWeekdayorWeekendRegionWise = new ArrayList<>();
		String storedProcedureCall = "EXEC PercentageofOrdersByWeekdayorWeekendRegionWise @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputPercentageofOrdersByWeekdayorWeekendRegionWise data = new OutputPercentageofOrdersByWeekdayorWeekendRegionWise();
				data.setRegion(row[0].toString());
				data.setMonth((Integer) row[1]);
				data.setDayType(row[2].toString());
				data.setDistinctOrderCount((Integer) row[3]);
				data.setPercentageOfOrders((BigDecimal) row[4]);
				// Now, filteredData is populated with values
				percentageofOrdersByWeekdayorWeekendRegionWise.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return percentageofOrdersByWeekdayorWeekendRegionWise;
	}

	@Override
	public List<OutputPercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend> percentaageOfOrdersByHourOfTheDayOnWeekdayWeekend(
			MonthlyDataFilter filter) {
		List<OutputPercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend> percentaageOfOrdersByHourOfTheDayOnWeekdayWeekend = new ArrayList<>();
		String storedProcedureCall = "EXEC PercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend @RegionList = :regionList, @StartDate = :startDate, @EndDate = :endDate, @BrandList = :brandList, @RSNameList = :rsNameList, @ABMName = :abmName, @RetailerType = :retailerType";

		// Create a native query
		Query query = entityManager.createNativeQuery(storedProcedureCall);

		// Set the parameters for the stored procedure call
		query.setParameter("regionList", filter.getRegionList()); // @RegionList (e.g., 'EAST, WEST')
		query.setParameter("startDate", filter.getStartDate()); // @StartDate (e.g., 20240601)
		query.setParameter("endDate", filter.getEndDate()); // @EndDate (e.g., 20240630)
		query.setParameter("brandList", filter.getBrandList()); // @BrandList (e.g., 'Titan')
		query.setParameter("rsNameList", filter.getRsNameList()); // @RSNameList (e.g., '' or some value)
		query.setParameter("abmName", filter.getAbmName());
		query.setParameter("retailerType", filter.getRetailerType());
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputPercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend data = new OutputPercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend();
				data.setMonth((Integer) row[0]);
				data.setDayType(row[1].toString());
				data.setOrderHour((Integer) row[2]);
				data.setDistinctOrderCount((Integer) row[3]);
				data.setPercentageOfOrders((BigDecimal) row[4]);
				// Now, filteredData is populated with values
				percentaageOfOrdersByHourOfTheDayOnWeekdayWeekend.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return percentaageOfOrdersByHourOfTheDayOnWeekdayWeekend;
	}

	@Override
	public List<OutputForMontlyFilter> MonthlyTrendRegular(MonthlyDataFilter filter) {

		List<OutputForMontlyFilter> filteredData = new ArrayList<>();
		StringBuilder conditions = new StringBuilder(); // Using StringBuilder for efficient string concatenation

		// Assuming the user input for startDate and endDate
		Integer startDate = filter.getStartDate(); // User-provided start date (e.g., "20240601")
		Integer endDate = filter.getEndDate(); // User-provided end date (e.g., "20240630")
		String regionList = filter.getRegionList();
		String brandList = filter.getBrandList();
		String abmNameList = filter.getAbmName();
		String rsNameList = filter.getRsNameList();
		String retailerType = filter.getRetailerType();

		// Add startDate and endDate conditions if provided
		if (startDate != null && endDate != null) {
			conditions.append("CONVERT(VARCHAR, OrderDate, 112) >= ").append(startDate)
					.append(" AND CONVERT(VARCHAR, OrderDate, 112) <= ").append(endDate).append(" ");
		}

		// Assuming the user input for regionList (e.g., "North,South,East,West")
		// User-provided region list
		if (regionList != null && !regionList.isEmpty()) {
			conditions.append("AND Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(regionList)
					.append("', ',')) ");
		}

		// Add ABMName condition if provided
		if (abmNameList != null && !abmNameList.isEmpty()) {
			conditions.append("AND (\r\n").append("    '").append(abmNameList).append("' IS NOT NULL AND '")
					.append(abmNameList).append("' <> '' AND (\r\n")
					.append("        ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n")
					.append("        OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n").append("    )\r\n").append(") ");
		}

		// Add Brand condition if provided
		if (brandList != null && !brandList.isEmpty()) {
			conditions.append("AND Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(brandList)
					.append("', ',')) ");
		}

		// Add RSName condition if provided
		if (rsNameList != null && !rsNameList.isEmpty()) {
			conditions.append("AND RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(rsNameList)
					.append("', ',')) ");
		}

		// Add RetailerType condition if provided
		if (retailerType != null && !retailerType.isEmpty()) {
			conditions.append("AND RetailerCode LIKE '").append(retailerType.equals("IDD") ? "9%" : "1%").append("' ");
		}

		// Build the final query with dynamic conditions
		String finalQuery = "SELECT \r\n" + "    YEAR(OrderDate) AS Year,\r\n" + "    MONTH(OrderDate) AS Month,\r\n"
				+ "    SUM(TotalPrice) AS TotalPriceSum,\r\n" + "    SUM(OrderQty) AS TotalOrderQty,\r\n"
				+ "    COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount\r\n" + "FROM \r\n"
				+ "    MBROrders (NOLOCK)\r\n" + "WHERE \r\n" + conditions.toString() // Add dynamic conditions
				+ "GROUP BY\r\n" + "    YEAR(OrderDate),\r\n" + "    MONTH(OrderDate)\r\n" + "ORDER BY\r\n"
				+ "    YEAR(OrderDate),\r\n" + "    MONTH(OrderDate);";

		// Create a native query and execute it
		Query query = entityManager.createNativeQuery(finalQuery);
		List<Object[]> result = query.getResultList();

		// Map each row to OutputForMontlyFilter
		for (Object[] row : result) {
			OutputForMontlyFilter data = new OutputForMontlyFilter();

			// Ensure safe casting and row validation
			if (row.length >= 5) {
				data.setMonth((Integer) row[1]);
				data.setTotalRevenue((BigDecimal) row[2]);
				data.setTotalQTY((Integer) row[3]);
				data.setTotalRetailerCode((Integer) row[4]);
				filteredData.add(data);
			} else {
				System.err.println("Unexpected result format for row: " + Arrays.toString(row));
			}
		}

		return filteredData;

	}

	@Override
	public List<OutputGrowthOverPreviousMonth> growthOverPreviousMonthRegular(MonthlyDataFilter filter) {
		List<OutputGrowthOverPreviousMonth> growthOverPreviousMonthData = new ArrayList<>();
		StringBuilder conditions = new StringBuilder(); // Using StringBuilder for efficient string concatenation

		// Assuming the user input for startDate and endDate
		Integer startDate = filter.getStartDate(); // User-provided start date (e.g., "20240601")
		Integer endDate = filter.getEndDate(); // User-provided end date (e.g., "20240630")
		String regionList = filter.getRegionList();
		String brandList = filter.getBrandList();
		String abmNameList = filter.getAbmName();
		String rsNameList = filter.getRsNameList();
		String retailerType = filter.getRetailerType();

		// Add startDate and endDate conditions if provided
		if (startDate != null && endDate != null) {
			conditions.append("CONVERT(VARCHAR, OrderDate, 112) >= ").append(startDate)
					.append(" AND CONVERT(VARCHAR, OrderDate, 112) <= ").append(endDate).append(" ");
		}

		// Assuming the user input for regionList (e.g., "North,South,East,West")
		// User-provided region list
		if (regionList != null && !regionList.isEmpty()) {
			conditions.append("AND Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(regionList)
					.append("', ',')) ");
		}

		// Add ABMName condition if provided
		if (abmNameList != null && !abmNameList.isEmpty()) {
			conditions.append("AND (\r\n").append("    '").append(abmNameList).append("' IS NOT NULL AND '")
					.append(abmNameList).append("' <> '' AND (\r\n")
					.append("        ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n")
					.append("        OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n").append("    )\r\n").append(") ");
		}

		// Add Brand condition if provided
		if (brandList != null && !brandList.isEmpty()) {
			conditions.append("AND Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(brandList)
					.append("', ',')) ");
		}

		// Add RSName condition if provided
		if (rsNameList != null && !rsNameList.isEmpty()) {
			conditions.append("AND RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(rsNameList)
					.append("', ',')) ");
		}

		// Add RetailerType condition if provided
		if (retailerType != null && !retailerType.isEmpty()) {
			conditions.append("AND RetailerCode LIKE '").append(retailerType.equals("IDD") ? "9%" : "1%").append("' ");
		}

		// Build the final query with dynamic conditions
		String finalQuery = "WITH MonthlySummary AS (\r\n" + "    SELECT \r\n" + "        YEAR(OrderDate) AS Year,\r\n"
				+ "        MONTH(OrderDate) AS Month,\r\n" + "        SUM(TotalPrice) AS TotalPriceSum,\r\n"
				+ "        SUM(OrderQty) AS TotalOrderQty,\r\n"
				+ "        COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount\r\n" + "    FROM \r\n"
				+ "        MBROrders (NOLOCK)\r\n" + "    WHERE \r\n" + conditions.toString() // Add dynamic conditions
				+ "    GROUP BY \r\n" + "        YEAR(OrderDate),\r\n" + "        MONTH(OrderDate)\r\n" + ")\r\n"
				+ "SELECT \r\n" + "    Year,\r\n" + "    Month,\r\n" + "    TotalPriceSum,\r\n"
				+ "    TotalOrderQty,\r\n" + "    DistinctRetailerCount,\r\n" + "\r\n"
				+ "    -- Calculate the percentage growth in DistinctRetailerCount (month-over-month growth)\r\n"
				+ "    CASE \r\n"
				+ "        WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "        WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "        ELSE \r\n"
				+ "            ((DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month)) * 1.0 / LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month)) * 100\r\n"
				+ "    END AS RetailerGrowthPercentage,\r\n" + "\r\n"
				+ "    -- Calculate the growth in TotalPrice (month-over-month growth)\r\n" + "    CASE \r\n"
				+ "        WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "        WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "        ELSE \r\n"
				+ "            (TotalPriceSum - LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month))\r\n"
				+ "    END AS PriceGrowth,\r\n" + "\r\n"
				+ "    -- Calculate the growth in TotalOrderQty (month-over-month growth)\r\n" + "    CASE \r\n"
				+ "        WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "        WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "        ELSE \r\n"
				+ "            (TotalOrderQty - LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month))\r\n"
				+ "    END AS OrderGrowth,\r\n" + "\r\n"
				+ "    -- Calculate the growth in DistinctRetailerCount (month-over-month growth)\r\n" + "    CASE \r\n"
				+ "        WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "        WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "        ELSE \r\n"
				+ "            (DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month))\r\n"
				+ "    END AS RetailerGrowth,\r\n" + "\r\n"
				+ "    -- Calculate the percentage growth in TotalPrice (month-over-month growth)\r\n" + "    CASE \r\n"
				+ "        WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "        WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "        ELSE \r\n"
				+ "            ((TotalPriceSum - LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month)) * 1.0 / LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month)) * 100\r\n"
				+ "    END AS PriceGrowthPercentage,\r\n" + "\r\n"
				+ "    -- Calculate the percentage growth in TotalOrderQty (month-over-month growth)\r\n"
				+ "    CASE \r\n" + "        WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "        WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "        ELSE \r\n"
				+ "            ((TotalOrderQty - LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month)) * 1.0 / LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month)) * 100\r\n"
				+ "    END AS OrderQtyGrowthPercentage\r\n" + "FROM \r\n" + "    MonthlySummary\r\n" + "ORDER BY\r\n"
				+ "    Year,\r\n" + "    Month;";

		// Create a native query and execute it
		Query query = entityManager.createNativeQuery(finalQuery);

		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputGrowthOverPreviousMonth data = new OutputGrowthOverPreviousMonth();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setTotalRevenue((BigDecimal) row[2]);
				data.setTotalQTY((Integer) row[3]);
				data.setTotalRetailerCode((Integer) row[4]);
				data.setRetailerGrowthPercentage((BigDecimal) row[5]);
				data.setPriceGrowth((BigDecimal) row[6]);
				data.setOrderGrowth((Integer) row[7]);
				data.setRetailerGrowth((Integer) row[8]);
				data.setPriceGrowthPercentage((BigDecimal) row[9]);
				data.setOrderQtyGrowthPercentage((BigDecimal) row[10]);

				growthOverPreviousMonthData.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return growthOverPreviousMonthData;

	}

	@Override
	public List<OutputRegionWiseMonthlyDistribution> regionWiseMonthlyDistributionRegular(MonthlyDataFilter filter) {
		List<OutputRegionWiseMonthlyDistribution> regionWiseMonthlyDistributionData = new ArrayList<>();
		StringBuilder conditions = new StringBuilder(); // Using StringBuilder for efficient string concatenation

		// Assuming the user input for startDate and endDate
		Integer startDate = filter.getStartDate(); // User-provided start date (e.g., "20240601")
		Integer endDate = filter.getEndDate(); // User-provided end date (e.g., "20240630")
		String regionList = filter.getRegionList();
		String brandList = filter.getBrandList();
		String abmNameList = filter.getAbmName();
		String rsNameList = filter.getRsNameList();
		String retailerType = filter.getRetailerType();

		// Add startDate and endDate conditions if provided
		if (startDate != null && endDate != null) {
			conditions.append("CONVERT(VARCHAR, OrderDate, 112) >= ").append(startDate)
					.append(" AND CONVERT(VARCHAR, OrderDate, 112) <= ").append(endDate).append(" ");
		}

		// Assuming the user input for regionList (e.g., "North,South,East,West")
		// User-provided region list
		if (regionList != null && !regionList.isEmpty()) {
			conditions.append("AND Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(regionList)
					.append("', ',')) ");
		}

		// Add ABMName condition if provided
		if (abmNameList != null && !abmNameList.isEmpty()) {
			conditions.append("AND (\r\n").append("    '").append(abmNameList).append("' IS NOT NULL AND '")
					.append(abmNameList).append("' <> '' AND (\r\n")
					.append("        ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n")
					.append("        OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n").append("    )\r\n").append(") ");
		}

		// Add Brand condition if provided
		if (brandList != null && !brandList.isEmpty()) {
			conditions.append("AND Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(brandList)
					.append("', ',')) ");
		}

		// Add RSName condition if provided
		if (rsNameList != null && !rsNameList.isEmpty()) {
			conditions.append("AND RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(rsNameList)
					.append("', ',')) ");
		}

		// Add RetailerType condition if provided
		if (retailerType != null && !retailerType.isEmpty()) {
			conditions.append("AND RetailerCode LIKE '").append(retailerType.equals("IDD") ? "9%" : "1%").append("' ");
		}

		String finalQuery = "SELECT \r\n" + "    YEAR(OrderDate) AS Year,\r\n" + "    MONTH(OrderDate) AS Month,\r\n"
				+ "    SUM(TotalPrice) AS TotalPriceSum,\r\n" + "    SUM(OrderQty) AS TotalOrderQty,\r\n"
				+ "    COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount, Region\r\n" + "FROM \r\n"
				+ "    MBROrders (NOLOCK)\r\n" + "WHERE \r\n" + conditions.toString() // Add dynamic conditions
				+ "GROUP BY\r\n" + "    YEAR(OrderDate),\r\n" + "    MONTH(OrderDate),\r\n" + "     Region\r\n"
				+ "ORDER BY\r\n" + "    YEAR(OrderDate),\r\n" + "    MONTH(OrderDate);";

		Query query = entityManager.createNativeQuery(finalQuery);
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputRegionWiseMonthlyDistribution data = new OutputRegionWiseMonthlyDistribution();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setTotalRevenue((BigDecimal) row[2]);
				data.setTotalQTY((Integer) row[3]);
				data.setTotalRetailerCode((Integer) row[4]);
				data.setRegion(row[5].toString());
				regionWiseMonthlyDistributionData.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return regionWiseMonthlyDistributionData;

	}

	@Override
	public List<OutputRegionWiseGrowthOverPreviousMonth> regionWiseGrowthOverPreviousMonthRegular(
			MonthlyDataFilter filter) {
		List<OutputRegionWiseGrowthOverPreviousMonth> regionWiseMonthlyGrowthData = new ArrayList<>();
		StringBuilder conditions = new StringBuilder(); // Using StringBuilder for efficient string concatenation

		// Assuming the user input for startDate and endDate
		Integer startDate = filter.getStartDate(); // User-provided start date (e.g., "20240601")
		Integer endDate = filter.getEndDate(); // User-provided end date (e.g., "20240630")
		String regionList = filter.getRegionList();
		String brandList = filter.getBrandList();
		String abmNameList = filter.getAbmName();
		String rsNameList = filter.getRsNameList();
		String retailerType = filter.getRetailerType();

		// Add startDate and endDate conditions if provided
		if (startDate != null && endDate != null) {
			conditions.append("CONVERT(VARCHAR, OrderDate, 112) >= ").append(startDate)
					.append(" AND CONVERT(VARCHAR, OrderDate, 112) <= ").append(endDate).append(" ");
		}

		// Assuming the user input for regionList (e.g., "North,South,East,West")
		// User-provided region list
		if (regionList != null && !regionList.isEmpty()) {
			conditions.append("AND Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(regionList)
					.append("', ',')) ");
		}

		// Add ABMName condition if provided
		if (abmNameList != null && !abmNameList.isEmpty()) {
			conditions.append("AND (\r\n").append("    '").append(abmNameList).append("' IS NOT NULL AND '")
					.append(abmNameList).append("' <> '' AND (\r\n")
					.append("        ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n")
					.append("        OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n").append("    )\r\n").append(") ");
		}

		// Add Brand condition if provided
		if (brandList != null && !brandList.isEmpty()) {
			conditions.append("AND Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(brandList)
					.append("', ',')) ");
		}

		// Add RSName condition if provided
		if (rsNameList != null && !rsNameList.isEmpty()) {
			conditions.append("AND RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(rsNameList)
					.append("', ',')) ");
		}

		// Add RetailerType condition if provided
		if (retailerType != null && !retailerType.isEmpty()) {
			conditions.append("AND RetailerCode LIKE '").append(retailerType.equals("IDD") ? "9%" : "1%").append("' ");
		}

		String finalQuery = "WITH MonthlySummary AS (\r\n"
				+ "        -- Select the sum of TotalPrice, sum of OrderQty, and count of distinct RetailerCode per month\r\n"
				+ "        SELECT \r\n" + "            YEAR(OrderDate) AS Year,\r\n"
				+ "            MONTH(OrderDate) AS Month,\r\n" + "            Region,\r\n"
				+ "            SUM(TotalPrice) AS TotalPriceSum,\r\n"
				+ "            SUM(OrderQty) AS TotalOrderQty,\r\n"
				+ "            COUNT(DISTINCT RetailerCode) AS DistinctRetailerCount\r\n" + "        FROM \r\n"
				+ "            MBROrders(nolock)\r\n" + "        WHERE \r\n" + conditions + "        GROUP BY\r\n"
				+ "            YEAR(OrderDate),\r\n" + "            MONTH(OrderDate),\r\n" + "            Region\r\n"
				+ "    )\r\n"
				+ "    -- Now, calculate the growth over the previous month using the LAG function and compute percentage growth\r\n"
				+ "    SELECT \r\n" + "        Year,\r\n" + "        Month,\r\n" + "        Region,\r\n"
				+ "        TotalPriceSum,\r\n" + "        TotalOrderQty,\r\n" + "        DistinctRetailerCount,\r\n"
				+ "        \r\n"
				+ "        -- Calculate the percentage growth in TotalPrice (month-over-month growth)\r\n"
				+ "        CASE \r\n"
				+ "            WHEN LAG(TotalPriceSum, 1) OVER (PARTITION BY Region ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "            WHEN LAG(TotalPriceSum, 1) OVER (PARTITION BY Region ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "            ELSE \r\n"
				+ "                ((TotalPriceSum - LAG(TotalPriceSum, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 1.0 / LAG(TotalPriceSum, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 100\r\n"
				+ "        END AS PriceGrowthPercentage,\r\n" + "        \r\n"
				+ "        -- Calculate the percentage growth in TotalOrderQty (month-over-month growth)\r\n"
				+ "        CASE \r\n"
				+ "            WHEN LAG(TotalOrderQty, 1) OVER (PARTITION BY Region ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "            WHEN LAG(TotalOrderQty, 1) OVER (PARTITION BY Region ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "            ELSE \r\n"
				+ "                ((TotalOrderQty - LAG(TotalOrderQty, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 1.0 / LAG(TotalOrderQty, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 100\r\n"
				+ "        END AS OrderQtyGrowthPercentage,\r\n" + "        \r\n"
				+ "        -- Calculate the percentage growth in DistinctRetailerCount (month-over-month growth)\r\n"
				+ "        CASE \r\n"
				+ "            WHEN LAG(DistinctRetailerCount, 1) OVER (PARTITION BY Region ORDER BY Year, Month) IS NULL THEN NULL\r\n"
				+ "            WHEN LAG(DistinctRetailerCount, 1) OVER (PARTITION BY Region ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "            ELSE \r\n"
				+ "                ((DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 1.0 / LAG(DistinctRetailerCount, 1) OVER (PARTITION BY Region ORDER BY Year, Month)) * 100\r\n"
				+ "        END AS RetailerGrowthPercentage,\r\n" + "\r\n"
				+ "				  -- Calculate the growth in TotalPrice (month-over-month growth)\r\n"
				+ "        CASE \r\n"
				+ "            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "            WHEN LAG(TotalPriceSum, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record\r\n"
				+ "            ELSE \r\n"
				+ "                (TotalPriceSum - LAG(TotalPriceSum, 1) OVER ( PARTITION BY Region ORDER BY Year, Month))\r\n"
				+ "        END AS PriceGrowth,\r\n" + "\r\n"
				+ "		 -- Calculate the growth in TotalOrderQty (month-over-month growth)\r\n" + "        CASE \r\n"
				+ "            WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "            WHEN LAG(TotalOrderQty, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record\r\n"
				+ "            ELSE \r\n"
				+ "                (TotalOrderQty - LAG(TotalOrderQty, 1) OVER ( PARTITION BY Region ORDER BY Year, Month))\r\n"
				+ "        END AS OrderGrowth, \r\n" + "\r\n" + "		CASE \r\n"
				+ "            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) = 0 THEN NULL\r\n"
				+ "            WHEN LAG(DistinctRetailerCount, 1) OVER (ORDER BY Year, Month) IS NULL THEN NULL -- Handle NULL for the first record\r\n"
				+ "            ELSE \r\n"
				+ "                (DistinctRetailerCount - LAG(DistinctRetailerCount, 1) OVER ( PARTITION BY Region ORDER BY Year, Month))\r\n"
				+ "        END AS RetailerGrowth\r\n" + "\r\n" + "    FROM \r\n" + "        MonthlySummary\r\n"
				+ "    ORDER BY\r\n" + "        Region, Year, Month;\r\n" + "";

		// Create a native query
		Query query = entityManager.createNativeQuery(finalQuery);

		// Set the parameters for the stored procedure call
		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputRegionWiseGrowthOverPreviousMonth data = new OutputRegionWiseGrowthOverPreviousMonth();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setRegion(row[2].toString());
				data.setTotalRevenue((BigDecimal) row[3]);
				data.setTotalQTY((Integer) row[4]);
				data.setTotalRetailerCode((Integer) row[5]);
				data.setPriceGrowthPercentage((BigDecimal) row[6]);
				data.setOrderQtyGrowthPercentage((BigDecimal) row[7]);
				data.setRetailerGrowthPercentage((BigDecimal) row[8]);
				data.setPriceGrowth((BigDecimal) row[9]);
				data.setOrderGrowth((Integer) row[10]);
				data.setRetailerGrowth((Integer) row[11]);
				regionWiseMonthlyGrowthData.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return regionWiseMonthlyGrowthData;

	}

	@Override
	public List<OutputMonthlyOrdaringBehaviour> monthlyOrdaringBehaviourRegular(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		List<OutputMonthlyOrdaringBehaviour> monthlyOrdaringBehaviourData = new ArrayList<>();

		StringBuilder conditions = new StringBuilder(); // Using StringBuilder for efficient string concatenation

		// Assuming the user input for startDate and endDate
		Integer startDate = filter.getStartDate(); // User-provided start date (e.g., "20240601")
		Integer endDate = filter.getEndDate(); // User-provided end date (e.g., "20240630")
		String regionList = filter.getRegionList();
		String brandList = filter.getBrandList();
		String abmNameList = filter.getAbmName();
		String rsNameList = filter.getRsNameList();
		String retailerType = filter.getRetailerType();

		// Add startDate and endDate conditions if provided
		if (startDate != null && endDate != null) {
			conditions.append("CONVERT(VARCHAR, OrderDate, 112) >= ").append(startDate)
					.append(" AND CONVERT(VARCHAR, OrderDate, 112) <= ").append(endDate).append(" ");
		}

		// Assuming the user input for regionList (e.g., "North,South,East,West")
		// User-provided region list
		if (regionList != null && !regionList.isEmpty()) {
			conditions.append("AND Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(regionList)
					.append("', ',')) ");
		}

		// Add ABMName condition if provided
		if (abmNameList != null && !abmNameList.isEmpty()) {
			conditions.append("AND (\r\n").append("    '").append(abmNameList).append("' IS NOT NULL AND '")
					.append(abmNameList).append("' <> '' AND (\r\n")
					.append("        ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n")
					.append("        OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))\r\n").append("    )\r\n").append(") ");
		}

		// Add Brand condition if provided
		if (brandList != null && !brandList.isEmpty()) {
			conditions.append("AND Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(brandList)
					.append("', ',')) ");
		}

		// Add RSName condition if provided
		if (rsNameList != null && !rsNameList.isEmpty()) {
			conditions.append("AND RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(rsNameList)
					.append("', ',')) ");
		}

		// Add RetailerType condition if provided
		if (retailerType != null && !retailerType.isEmpty()) {
			conditions.append("AND RetailerCode LIKE '").append(retailerType.equals("IDD") ? "9%" : "1%").append("' ");
		}

		String finalQuery = " SELECT \r\n"
				+ "        YEAR(OrderDate) AS OrderYear,         -- Extract Year from OrderDate\r\n"
				+ "        MONTH(OrderDate) AS OrderMonth,       -- Extract Month from OrderDate\r\n"
				+ "        COUNT(DISTINCT OrderNo) AS DistinctOrderCount,  -- Count of distinct orders\r\n"
				+ "        SUM(OrderQty) * 1.0 / COUNT(DISTINCT OrderNo) AS AvgQtyPerOrder,  -- Avg quantity per order\r\n"
				+ "        SUM(TotalPrice) / COUNT(DISTINCT OrderNo) AS AvgValuePerOrder    -- Avg value per order\r\n"
				+ "    FROM \r\n" + "        MBROrders (NOLOCK)  -- Using NOLOCK for the read operation\r\n"
				+ "    WHERE " + conditions.toString() // Add dynamic conditions
				+ "GROUP BY\r\n" + "        YEAR(OrderDate),  -- Group by Year\r\n"
				+ "        MONTH(OrderDate)  -- Group by Month\r\n" + "    ORDER BY\r\n"
				+ "        OrderYear,  -- Order by Year\r\n" + "        OrderMonth; -- Order by Month";

		Query query = entityManager.createNativeQuery(finalQuery);
		// Create a native query
		// Set the parameters for the stored procedure call

		// Execute the query to invoke the stored procedure
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputMonthlyOrdaringBehaviour data = new OutputMonthlyOrdaringBehaviour();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setNoOforders((Integer) row[2]);
				data.setAvgQtyPerOrder((BigDecimal) row[3]);
				data.setAvgValuePerOrder((BigDecimal) row[4]);
				monthlyOrdaringBehaviourData.add(data);
				// Now, filteredData is populated with values
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return monthlyOrdaringBehaviourData;

	}

	@Override
	public List<OutputRegionWiseMonthlyDistributionNoofOrders> regionWiseMonthlyDistributionNoofOrdersRegular(
			MonthlyDataFilter filter) {
		List<OutputRegionWiseMonthlyDistributionNoofOrders> regionWiseMonthlyDistributionData = new ArrayList<>();

		StringBuilder conditions = new StringBuilder(); // Using StringBuilder for efficient string concatenation

		// Assuming the user input for startDate and endDate
		Integer startDate = filter.getStartDate(); // User-provided start date (e.g., "20240601")
		Integer endDate = filter.getEndDate(); // User-provided end date (e.g., "20240630")
		String regionList = filter.getRegionList();
		String brandList = filter.getBrandList();
		String abmNameList = filter.getAbmName();
		String rsNameList = filter.getRsNameList();
		String retailerType = filter.getRetailerType();

		// Add startDate and endDate conditions if provided
		if (startDate != null && endDate != null) {
			conditions.append("CONVERT(VARCHAR, OrderDate, 112) >= ").append(startDate)
					.append(" AND CONVERT(VARCHAR, OrderDate, 112) <= ").append(endDate).append(" ");
		}

		// Add Region condition if provided
		if (regionList != null && !regionList.isEmpty()) {
			conditions.append("AND Region IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(regionList)
					.append("', ',')) ");
		}

		// Add ABMName condition if provided
		if (abmNameList != null && !abmNameList.isEmpty()) {
			conditions.append("AND (").append("    '").append(abmNameList).append("' IS NOT NULL AND '")
					.append(abmNameList).append("' <> '' AND (")
					.append("        ABMEMM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(abmNameList)
					.append("', ','))").append("        OR ABMKAM IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('")
					.append(abmNameList).append("', ','))").append("    )").append(") ");
		}

		// Add Brand condition if provided
		if (brandList != null && !brandList.isEmpty()) {
			conditions.append("AND Brand IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(brandList)
					.append("', ',')) ");
		}

		// Add RSName condition if provided
		if (rsNameList != null && !rsNameList.isEmpty()) {
			conditions.append("AND RSName IN (SELECT LTRIM(RTRIM(value)) FROM STRING_SPLIT('").append(rsNameList)
					.append("', ',')) ");
		}

		// Add RetailerType condition if provided
		if (retailerType != null && !retailerType.isEmpty()) {
			conditions.append("AND RetailerCode LIKE '").append(retailerType.equals("IDD") ? "9%" : "1%").append("' ");
		}

		// Build the final query
		String finalQuery = " DECLARE @TotalOrderQty DECIMAL(18, 2); " + " SELECT @TotalOrderQty = SUM(OrderQty) "
				+ " FROM MBROrders (NOLOCK) " + " WHERE " + conditions.toString() // Add dynamic conditions
				+ " SELECT " + "        YEAR(OrderDate) AS OrderYear, " + "        MONTH(OrderDate) AS OrderMonth, "
				+ "        COUNT(DISTINCT OrderNo) AS DistinctOrderCount, " + "        Region, "
				+ "        (SUM(OrderQty) * 1000.0) / @TotalOrderQty AS RegionOrderQtyPercentage "
				+ " FROM MBROrders (NOLOCK) " + " WHERE " + conditions.toString() // Add dynamic conditions
				+ " GROUP BY YEAR(OrderDate), MONTH(OrderDate), Region " + " ORDER BY OrderYear, OrderMonth";

		// Create a native query
		Query query = entityManager.createNativeQuery(finalQuery);

		// Execute the query and map results
		try {
			List<Object[]> result = query.getResultList();
			// filteredData = (OutputForMontlyFilter) query.getSingleResult();

			for (Object[] row : result) {
				// Assuming row contains values in the correct order for mapping
				OutputRegionWiseMonthlyDistributionNoofOrders data = new OutputRegionWiseMonthlyDistributionNoofOrders();
				data.setYear((Integer) row[0]);
				data.setMonth((Integer) row[1]);
				data.setNoOfOrders((Integer) row[2]);
				data.setRegion(row[3].toString());
				data.setNoOfOrdersPercentage((BigDecimal) row[4]);
				// Now, filteredData is populated with values
				regionWiseMonthlyDistributionData.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return regionWiseMonthlyDistributionData;
	}
}