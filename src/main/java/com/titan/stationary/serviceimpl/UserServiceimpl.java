package com.titan.stationary.serviceimpl;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.titan.stationary.bean.Budgetmasterbean;
import com.titan.stationary.bean.BuyerIndentBean;
import com.titan.stationary.bean.HolidayMasterBean;
import com.titan.stationary.bean.IndentMasterBean;
import com.titan.stationary.bean.PoEntryBean;
import com.titan.stationary.bean.Product;
import com.titan.stationary.bean.UserLoginBean;
import com.titan.stationary.bean.productMasterbean;
import com.titan.stationary.dao.UserDao;
import com.titan.stationary.dto.MasterData;
import com.titan.stationary.dto.MonthlyDataFilter;
import com.titan.stationary.dto.OutputForMontlyFilter;
import com.titan.stationary.dto.OutputGrowthOverPreviousMonth;
import com.titan.stationary.dto.OutputRegionWiseGrowthOverPreviousMonth;
import com.titan.stationary.dto.OutputRegionWiseMonthlyDistribution;
import com.titan.stationary.service.Userservice;
import com.titan.stationary.dto.OutputDashboardTiles;
import com.titan.stationary.dto.OutputDashboardGraphs;

@Service("userService")
@Transactional()
public class UserServiceimpl implements Userservice {
	@Autowired
	UserDao userDao;

	@Override
	public int saveLoginDetails(String login_Id, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return userDao.saveLoginDetails(login_Id, request, response);
	}

	public Map<String, Object> findloginuser(UserLoginBean userLogin,String passwords) {

		return userDao.findloginuser(userLogin,passwords);
	}

	@Override
	public List<Object[]> getAllMenuNamesByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> getAllSubmenuNamesByLoginId(String loginId, int menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> usersearch(String firstname) {
		return userDao.usersearch(firstname);
	}

	@Override
	public List<String[]> usersearchs(String firstname) {
		return userDao.usersearchs(firstname);
	}
	
	

	
	// to check the cell type
	private String checkCellType(Cell cell) {
		String columnName = "";
		if (cell != null) {

			if (cell.getCellType() == cell.getCellType().NUMERIC) {

				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					columnName = cell.getDateCellValue().toString();
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date date = cell.getDateCellValue();
					columnName = df.format(date);
				}
				else {
				int division_NameInt = (int) cell.getNumericCellValue();
				columnName = String.valueOf(division_NameInt);
				}
			} else if (cell.getCellType() == cell.getCellType().STRING) {
				columnName = cell.getStringCellValue();
			}
		} else {
			columnName = "";
		}
		return columnName;
	}

	@Override
	public String getPasswordByloginId(String login_id) {
		return userDao.getPasswordbyloginId(login_id);
	}

	@Override
	public int updatePassword(String pwd, String cpwd, String login_id) {
		return userDao.updatePassword(pwd,cpwd,login_id);
	}
	@Override
	public int updatePassword1(String pwd, String oldPwd,String newPwd,String login_id,String email,String email_id, String user_Name) {
		return userDao.updatePassword1(pwd,oldPwd,newPwd,login_id,email,email_id,user_Name);
	}

	@Override
	public String getEmailIdByloginId(String email) {
		return userDao.getEmailIdbyLoginId(email);
	}



	@Override
	public int updateLogouTIme(String login_id) {
		return userDao.updateLogouTIme(login_id);
	}
	
	@Override
	public List<String> getAllProducts(String loginId) {
		List<String> AllProducts;
		AllProducts = userDao.getAllProducts(loginId);
		return AllProducts;
	}
	
	@Override
	public List<String> getCategoryList() {
		List<String> getCategoryList;
		getCategoryList = userDao.getCategoryList();
		return getCategoryList;
	}
	
	@Override
	public List<String> getProductByCategory(String category,String userId) {
		List<String> AllProducts;
		AllProducts = userDao.getProductByCategory(category,userId);
		return AllProducts;
	}
	
	@Override
	public String IndentTransaction(Product[] products, String loginID,String userId,String userName) {
		// TODO Auto-generated method stub
		return userDao.IndentTransaction(products, loginID,userId,userName);
	}
	
	/*
	 * Get All Indent By Cost Center
	 */
	@Override
	public List<Object> getgetIndentList(String costcenter){
		List<Object> AllProducts;
		AllProducts = userDao.getIndentList(costcenter);
		return AllProducts;
	}
	
	@Override
	public String tempCartIndentCreation(Product[] products, String loginID,String userId,String userName) {
		// TODO Auto-generated method stub
		return userDao.tempCartIndentCreation(products, loginID,userId,userName);
	}
	
	@Override
	public List<String> getAllProductsByIndent(String loginId) {
		List<String> AllProducts;
		AllProducts = userDao.getAllProductsByIndent(loginId);
		return AllProducts;
	}
	

	@Override
	public List<String> GetAllProductsByIndentNumber(String loginId,String IndentNumner) {
		List<String> AllProducts;
		AllProducts = userDao.GetAllProductsByIndentNumber(loginId,IndentNumner);
		return AllProducts;
	}
	
	@Override
	public String IndentTransactionQuantitySave(Product[] products, String loginID,String userId,String userName) {
		// TODO Auto-generated method stub
		return userDao.IndentTransactionQuantitySave(products, loginID,userId,userName);
	}
	
	
	@Override
	public List<String> GetAddMoreProducts(String loginId) {
		List<String> AllProducts;
		AllProducts = userDao.getAllProductsByIndent(loginId);
		return AllProducts;
	}
	
	@Override
	public List<Object> getBudgetDetails(String loginId) {
		List<Object> BudgetDetails;
		BudgetDetails = userDao.getBudgetDetails(loginId);
		return BudgetDetails;
	}
	@Override
	public List<Object> getholidaymasterData(String loginId) {
		List<Object> getholidaymasterData;
		getholidaymasterData = userDao.getholidaymasterData(loginId);
		return getholidaymasterData;
	}
	
	@Override
	public List<String> getProductByCategoryIndentUpdate(String category,String userId) {
		List<String> AllProducts;
		AllProducts = userDao.getProductByCategoryIndentUpdate(category,userId);
		return AllProducts;
	}
	
	
	@Override
	public List<String> getProductByCategoryaAddMore(String category,String userId) {
		List<String> AllProducts;
		AllProducts = userDao.getProductByCategoryaAddMore(category,userId);
		return AllProducts;
	}
	
	@Override
	public String tempIndentUpdateCreation(Product[] products, String loginID,String userId,String userName) {
		// TODO Auto-generated method stub
		return userDao.tempIndentUpdateCreation(products, loginID,userId,userName);
	}
	
	@Override
	public String IndentTransactionUpdate(Product[] products, String loginID,String userId,String userName) {
		// TODO Auto-generated method stub
		return userDao.IndentTransactionUpdate(products, loginID,userId,userName);
	}
	
	@Override
	public List<Object> getBuyerIndentList(String Year, String Month) {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getBuyerIndentList(Year,Month);
		return getAllUserDetails;
	}

	@Override
	public List<Object> getBuyerFooterList(String Year, String Month,String yearfromCal1) {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getBuyerFooterList(Year,Month,yearfromCal1);
		return getAllUserDetails;
	}
	
	@Override
	public String BuyerIndentUpdateSave(BuyerIndentBean[] products, String loginID,String userId,String userName) {
		// TODO Auto-generated method stub
		return userDao.BuyerIndentUpdateSave(products, loginID,userId,userName);
	}
	
	
	
	/*
	 * @Override public String userCreationByForm(IndentMasterBean storemaster,
	 * String loginId) { return userDao.userCreationByForm( storemaster, loginId); }
	 */

	@Override
	public StringBuilder uploadBulkbudgetExcelFile(MultipartFile fileData, String loginId) {

		Workbook workbook = null;
		Sheet sheet = null;
		ByteArrayInputStream bis = null;

		StringBuilder messageBuilder = new StringBuilder();

		List<Budgetmasterbean> listOfUserMasters = new ArrayList<>();

		try {

			bis = new ByteArrayInputStream(fileData.getBytes());
			if (fileData.getOriginalFilename().endsWith("xls")) {
				workbook = new HSSFWorkbook(bis);

			} else if (fileData.getOriginalFilename().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(bis);
			} else {

				messageBuilder.append("Received file does not have a standard excel extension.");
				return messageBuilder;
			}

			sheet = workbook.getSheetAt(0);

			messageBuilder = extractbudgetExcelData(sheet, listOfUserMasters);
			System.out.println(messageBuilder);

			messageBuilder = userDao.insertExcelbudgetMaster(listOfUserMasters, loginId);
			System.out.println(messageBuilder);

		} catch (Exception e) {
			e.printStackTrace();
			messageBuilder.append(e.getMessage());
		}
		return messageBuilder;

	}

	/**
	 * @param sheet
	 * @param listOfUserMasterBean
	 */
	public StringBuilder extractbudgetExcelData(Sheet sheet, List<Budgetmasterbean> listOfUserMasterBean)
			throws Exception {
		Budgetmasterbean Budgetmasterbean;
		Row row;
		Set set = new HashSet<>();
		StringBuilder messageBuilder = new StringBuilder();

		try {
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Budgetmasterbean = new Budgetmasterbean();
				row = sheet.getRow(i);
				Cell cell = null;

				// System.out.println("userselection" + row.getCell(0));
				cell = row.getCell(0);
				String CCID = checkCellType(cell);

				// System.out.println("userselection" + checkCellType(cell));
				cell = row.getCell(1);
				String Year = checkCellType(cell);

				cell = row.getCell(2);
				String CostCenterDescription = checkCellType(cell);

				cell = row.getCell(3);
				String GL = checkCellType(cell);

				cell = row.getCell(4);
				String GLDescription = checkCellType(cell);

				cell = row.getCell(5);
				String Location = checkCellType(cell);

				cell = row.getCell(6);
				String CostOwner = checkCellType(cell);

				cell = row.getCell(7);
				String Department = checkCellType(cell);

				cell = row.getCell(8);
				String BudValueRsL = checkCellType(cell);

				cell = row.getCell(9);
				String Email = checkCellType(cell);

				boolean check = set.add(CCID);

				if (check != true) {
					messageBuilder.append("line number " + i + " have duplicate loginId " + CCID + "\n");
					continue;
				}

				Budgetmasterbean.setCCID(CCID); // create method to check employee in HR data...

				Budgetmasterbean.setYear(Year);
				Budgetmasterbean.setCostCenterDescription(CostCenterDescription); // create method to check employee in
																					// HR data...
				Budgetmasterbean.setGL(GL);
				Budgetmasterbean.setGLDescription(GLDescription);
				Budgetmasterbean.setLocation(Location);
				Budgetmasterbean.setCostOwner(CostOwner);
				Budgetmasterbean.setDepartment(Department);
				Budgetmasterbean.setBudValueRsL(BudValueRsL);
				Budgetmasterbean.setEmail(Email);
				// create method to check employee in HR data...

				listOfUserMasterBean.add(Budgetmasterbean);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error in excel file, check and upload again.");

		}

		return messageBuilder;
	}
	@Override
	public StringBuilder uploadBulkholidayExcelFile(MultipartFile fileData, String loginId) {

		Workbook workbook = null;
		Sheet sheet = null;
		ByteArrayInputStream bis = null;

		StringBuilder messageBuilder = new StringBuilder();

		List<HolidayMasterBean> listOfUserMasters = new ArrayList<>();

		try {

			bis = new ByteArrayInputStream(fileData.getBytes());
			if (fileData.getOriginalFilename().endsWith("xls")) {
				workbook = new HSSFWorkbook(bis);

			} else if (fileData.getOriginalFilename().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(bis);
			} else {

				messageBuilder.append("Received file does not have a standard excel extension.");
				return messageBuilder;
			}

			sheet = workbook.getSheetAt(0);

			messageBuilder = extractholidayExcelData(sheet, listOfUserMasters);
			System.out.println(messageBuilder);

			messageBuilder = userDao.insertExcelholidayMaster(listOfUserMasters, loginId);
			System.out.println(messageBuilder);

		} catch (Exception e) {
			e.printStackTrace();
			messageBuilder.append(e.getMessage());
		}
		return messageBuilder;

	}

	/**
	 * @param sheet
	 * @param listOfUserMasterBean
	 */
	public StringBuilder extractholidayExcelData(Sheet sheet, List<HolidayMasterBean> listOfUserMasterBean)
			throws Exception {
		HolidayMasterBean HolidayMasterBean;
		Row row;
		Set set = new HashSet<>();
		StringBuilder messageBuilder = new StringBuilder();

		try {
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HolidayMasterBean = new HolidayMasterBean();
				row = sheet.getRow(i);
				Cell cell = null;

				// System.out.println("userselection" + row.getCell(0));
				cell = row.getCell(0);
				String Holiday_date = checkCellType(cell);

				// System.out.println("userselection" + checkCellType(cell));
				cell = row.getCell(1);
				String Holiday_day = checkCellType(cell);

				cell = row.getCell(2);
				String Occasion = checkCellType(cell);

				cell = row.getCell(3);
				String Activestatus = checkCellType(cell);

				cell = row.getCell(5);
				String month = checkCellType(cell);

				
				  cell = row.getCell(4);
				  String year = checkCellType(cell);
				  
					/*
					 * cell = row.getCell(6); String CostOwner = checkCellType(cell);
					 * 
					 * cell = row.getCell(7); String Department = checkCellType(cell);
					 * 
					 * cell = row.getCell(8); String BudValueRsL = checkCellType(cell);
					 */
				 

				boolean check = set.add(Holiday_date);

				if (check != true) {
					messageBuilder.append("line number " + i + " have duplicate loginId " + Holiday_date + "\n");
					continue;
				}

				HolidayMasterBean.setHoliday_date(Holiday_date); // create method to check employee in HR data...

				HolidayMasterBean.setHoliday_day(Holiday_day);
				HolidayMasterBean.setOccasion(Occasion); // create method to check employee in
																					// HR data...
				HolidayMasterBean.setActivestatus(Activestatus);
				HolidayMasterBean.setCostcentre(year);
				
				  HolidayMasterBean.setYear(year);
				  HolidayMasterBean.setMonth(month);
					/*
					 * HolidayMasterBean.setCostOwner(CostOwner);
					 * HolidayMasterBean.setDepartment(Department);
					 * HolidayMasterBean.setBudValueRsL(BudValueRsL);
					 */
				 
				// create method to check employee in HR data...

				listOfUserMasterBean.add(HolidayMasterBean);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error in excel file, check and upload again.");

		}

		return messageBuilder;
	}
	@Override
	public List<Object> getAllindentDetails() {
		List<Object> getAllindentDetails;
		getAllindentDetails = userDao.getAllindentDetails();
		return getAllindentDetails;
	}

	@Override
	public List<Object> getMOQIndentList() {
		List<Object> getAllUserDetails;
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat year_Date = new SimpleDateFormat("YYYY");
		String yearfromCal = year_Date.format(cal.getTime());

		SimpleDateFormat month_Date = new SimpleDateFormat("MM");
		// String monthFromCal = month_Date.format(cal.getTime());
		String monthFromCal = String.format("%02d", Integer.parseInt(month_Date.format(cal.getTime())));

		SimpleDateFormat month = new SimpleDateFormat("MMMMMMMMMM");
		String MonthText = month.format(cal.getTime());

		getAllUserDetails = userDao.getBuyerIndentList(yearfromCal,MonthText);
		return getAllUserDetails;
	}
	
	@Override
	public List<Object> getDistribuFooterList() {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getDistribuFooterList();
		return getAllUserDetails;
	}
	

	@Override
	public String DistributionPageSave(BuyerIndentBean[] products, String loginID,String userId,String userName) {
		// TODO Auto-generated method stub
		return userDao.DistributionPageSave(products, loginID,userId,userName);
	}
	
	@Override
	public List<Object> getDistributerIndentList() {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getDistributerIndentList();
		return getAllUserDetails;
	}
	
	@Override
	public List<Object> getAllIndentList() {
		List<Object> getAllIndentList;
		getAllIndentList = userDao.getAllIndentList();
		return getAllIndentList;
	}
		
	@Override
	public List<Object> getAllindentmanagerDetails(String loginId) {
		List<Object> getAllindentmanagerDetails;
		getAllindentmanagerDetails = userDao.getAllindentmanagerDetails(loginId);
		return getAllindentmanagerDetails;
	}
	@Override
	public List<Object> getAllpasswordDetails() {
		List<Object> getAllpasswordDetails;
		getAllpasswordDetails = userDao.getAllpasswordDetails();
		return getAllpasswordDetails;
	}

	@Override
	public String userCreationByForm() {
	
		return null;
	}
	@Override
	public List<Object> getAllCCIDDetails() {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getAllCCIDDetails();
		return getDesignationDetails;
	}
	@Override
	public List<Object> getAllyearDetails() {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getAllyearDetails();
		return getDesignationDetails;
	}
	@Override
	public List<String[]> getccidmasterid(String Year) {
		return userDao.getccidmasterid(Year);
	}
	@Override
	public String budgetupdate(String descode, String yearlybudget,String budgetextension,String loginId) {
		// TODO Auto-generated method stub
		return userDao.budgetupdate( descode,yearlybudget, budgetextension,loginId);
	}
	@Override
	public String getEmailIdByloginIdn(String email) {
		return userDao.getEmailIdbyLoginIdn(email);
	}
	/*
	 * @Override public String getUserEmailFromDatabase(String email) { return
	 * userDao.getUserEmailFromDatabase(email); }
	 * 
	 * @Override public String getIndentManagerEmailFromDatabase(String email) {
	 * return userDao.getIndentManagerEmailFromDatabase(email); }
	 */

	@Override
	public String checkIndentForMonth(String cFY, String MonthText, String userId) {
		
		return userDao.checkIndentForMonth(cFY, MonthText, userId);
	}

	@Override
	public String sendToVendor(Map<String, Object> payload,String loginId) {
		// TODO Auto-generated method stub
		return userDao.sendToVendor(payload,loginId);
	}
	@Override
	public List<String[]> getreportbyid(String Month,String Year,String loginid) {
		return userDao.getreportbyid(Month,Year,loginid);
	}
	
	@Override
	public List<String[]> getreportbyidadmin(String Month,String Year,String loginid) {
		return userDao.getreportbyidadmin(Month,Year,loginid);
	}
	@Override
	public List<String> getYears() {
		List<String> getDesignationDetails;
		getDesignationDetails = userDao.getYears();
		return getDesignationDetails;
	}
	
	@Override
	public String sendToStore(String email, String email_id, Map<String, Object> payload) {
		// TODO Auto-generated method stub
		return userDao.sendToStore(email, email_id,payload);
	}
	
	@Override
	public List<Object> getAllProductId() {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getAllProductId();
		return getDesignationDetails;
	}
	
	@Override
	public int updateIsImage(String productId,String userId) {
		// TODO Auto-generated method stub
		return  userDao.updateIsImage(productId, userId);
	}
	
	@Override
	public List<Object> getBuyerIndentListBasedOnFilter(String Year, String Month) {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getBuyerIndentListBasedOnFilter(Year, Month);
		return getAllUserDetails;
	}
	

	@Override
	public List<Object> getBuyerFooterListBasedOnFilter(String Year, String Month) {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getBuyerFooterListBasedOnFilter(Year, Month);
		return getAllUserDetails;
	}
	
	
	@Override
	public List<Object> getDistributionListBasedOnFilter(String Year, String Month) {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getDistributionListBasedOnFilter(Year, Month);
		return getAllUserDetails;
	}
	

	@Override
	public List<Object> getDistributionFooterListBasedOnFilter(String Year, String Month) {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getDistributionFooterListBasedOnFilter(Year, Month);
		return getAllUserDetails;
	}
	

	@Override
	public List<Object> getBudgetDataforChart(String CostCenter) {
		List<Object> getAllUserDetails;
		getAllUserDetails = userDao.getBudgetDataforChart(CostCenter)	;
				return getAllUserDetails;
	}
	
	
	@Override
	public void sevenDayMailTrigger() {
		 userDao.sevenDayMailTrigger();	
				
	}
	
//	@Override
//	public List<String> get7thworkingDay() {
//		return userDao.get7thworkingDay();	
//				
//	}
	
	@Override
	public List<Object> getAllVendor() {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getAllVendor();
		return getDesignationDetails;
	}
	

	@Override
	public List<Object> getVendorByEmailId(String vendorname) {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getVendorByEmailId(vendorname);
		return getDesignationDetails;
	}
	
	
	@Override
	public String ProductMasterCreationSave(String ProductID,String ProductName,String vendor,String EmailID,String Price,String UOM,String Category,String loginId) {
	
		return userDao.ProductMasterCreationSave(ProductID,ProductName,vendor,EmailID,Price,UOM,Category,loginId);
	}
	
	@Override
	public List<String> getAllProductsForList() {
		List<String> getDesignationDetails;
		getDesignationDetails = userDao.getAllProductsForList();
		return getDesignationDetails;
	}
	

	@Override
	public List<Object> getProductDetailsByID(String ProductID) {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getProductDetailsByID(ProductID);
		return getDesignationDetails;
	}
	
	@Override
	public String ProductMasterCreationUpdate(String ProductID,String ProductName,String vendor,String EmailID,String Price,String UOM,String Category,String loginId) {
	
		return userDao.ProductMasterCreationUpdate(ProductID,ProductName,vendor,EmailID,Price,UOM,Category,loginId);
	}
	

	@Override
	public List<String> getAllBudgetForList() {
		List<String> getDesignationDetails;
		getDesignationDetails = userDao.getAllBudgetForList();
		return getDesignationDetails;
	}
	
	@Override
	public String BudgetMasterCreationSave( String CCID, String Year, String COSTCENTERDESC, String GL,  String GLDESC, String LOCATION, String Department, String YEARLYBUDGET, String loginId) {
		return userDao.BudgetMasterCreationSave(CCID,Year,COSTCENTERDESC,GL,GLDESC,LOCATION,Department,YEARLYBUDGET,loginId);
	}
	
	@Override
	public String ccCreationSave( String CCID, String Year, String COSTCENTERDESC, String GL,  String GLDESC, String LOCATION, String Department,String CCOwner, String YEARLYBUDGET, String loginId,String COSTEMAIL) {
		return userDao.ccCreationSave(CCID,Year,COSTCENTERDESC,GL,GLDESC,LOCATION,Department,CCOwner,YEARLYBUDGET,loginId, COSTEMAIL);
	}
	
	@Override
	public String poEntryCreationSave(String Year, String Month, String CostCenter,  String PoAmount,String loginId) {
		return userDao.poEntryCreationSave(Year,Month,CostCenter,PoAmount,loginId);
	}
	
	@Override
	public String poEntryDataUpdation(String costCenter, String PoAmount,String Month,String year,String createdBy,String createdOn,String loginId) {
		return userDao.poEntryDataUpdation(costCenter,PoAmount,Month,year,createdBy,createdOn,loginId);
	}
	
	@Override
	public List<Object> getAllCCIDDe() {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getAllCCIDDe();
		return getDesignationDetails;
	}
	

	@Override
	public List<Object> getBidgetDetailsByID(String CCID,String Year) {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getBidgetDetailsByID(CCID,Year);
		return getDesignationDetails;
	}
	
	
	
	@Override
	public String BudgetMasterUpdateByForm(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC,
			String LOCATION, String Department, String YEARLYBUDGET, String loginId,String oldYEARLYBUDGET) {
		return userDao.BudgetMasterUpdateByForm(CCID,Year,COSTCENTERDESC,GL,GLDESC,LOCATION,Department,YEARLYBUDGET,loginId,oldYEARLYBUDGET);
	}
	
	
	@Override
	public StringBuilder ProductMasterBulkUpload(MultipartFile fileData, String loginId) {

		Workbook workbook = null;
		Sheet sheet = null;
		ByteArrayInputStream bis = null;

		StringBuilder messageBuilder = new StringBuilder();

		List<productMasterbean> listOfUserMasters = new ArrayList<>();

		try {

			bis = new ByteArrayInputStream(fileData.getBytes());
			if (fileData.getOriginalFilename().endsWith("xls")) {
				workbook = new HSSFWorkbook(bis);

			} else if (fileData.getOriginalFilename().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(bis);
			} else {

				messageBuilder.append("Received file does not have a standard excel extension.");
				return messageBuilder;
			}

			sheet = workbook.getSheetAt(0);

			messageBuilder = extractProductMasterExcelData(sheet, listOfUserMasters);
			System.out.println(messageBuilder);

			messageBuilder = userDao.ProductMasterBulkUpload(listOfUserMasters, loginId);
			System.out.println(messageBuilder);

		} catch (Exception e) {
			e.printStackTrace();
			messageBuilder.append(e.getMessage());
		}
		return messageBuilder;

	}
	
	
	public StringBuilder extractProductMasterExcelData(Sheet sheet, List<productMasterbean> listOfUserMasterBean)
			throws Exception {
		productMasterbean productMasterbean;
		Row row;
		Set set = new HashSet<>();
		StringBuilder messageBuilder = new StringBuilder();

		try {
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				productMasterbean = new productMasterbean();
				row = sheet.getRow(i);
				Cell cell = null;

				cell = row.getCell(0);
				String ProductId = checkCellType(cell);

				cell = row.getCell(1);
				String ProductName = checkCellType(cell);

				cell = row.getCell(2);
				String vendor = checkCellType(cell);

				cell = row.getCell(3);
				String vendorEmailID = checkCellType(cell);

				cell = row.getCell(4);
				String Price = checkCellType(cell);

				cell = row.getCell(5);
				String UOM = checkCellType(cell);

				cell = row.getCell(6);
				String Category = checkCellType(cell);

				productMasterbean.setProductid(ProductId); 
				productMasterbean.setProductname(ProductName);
				productMasterbean.setVendor(vendor);
				productMasterbean.setVendorEmailId(vendorEmailID);
				productMasterbean.setPrice(Price);
				productMasterbean.setUOM(UOM);
				productMasterbean.setCategory(Category);
				listOfUserMasterBean.add(productMasterbean);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error in excel file, check and upload again.");

		}

		return messageBuilder;
	}
	
	@Override
	public List<Object> getAllPoEntryList() {
		List<Object> getPOEntryList;
		getPOEntryList = userDao.getAllPoEntryList();
		return getPOEntryList;
	}
	
	
	@Override
	public List<String> getAllheader(String Year, String Month) {
		
		List<String> getAllfinalcc = new ArrayList<String>();
		System.out.println("print"+Year +Month);

			String finalcc = userDao.getAllIndentedCostCenters(Year,Month);
			System.out.println("print"+finalcc);
			finalcc = finalcc.replaceAll("\\[", "<th scope='col' class=\"headerStyles\"><b>CC");
			finalcc = finalcc.replaceAll("],", "</b></th>");
			finalcc = finalcc.replaceAll("]", "</b></th>");
			
			finalcc = "<th style='text-align:center' scope='col' class=\"headerStyles\"><b>Description</b></th><th scope='col' class=\"headerStyles\"><b>Vendors</b></th>	<th scope='col' class=\"headerStyles\"><b>UOM</b></th>"
			+finalcc+			
			"<th scope='col' class=\"headerStyles\"><b>User Qty</b></th><th scope='col' class=\"headerStyles\"><b>MOQ Qty</b></th><th scope='col' class=\"headerStyles\"><b>MOQ Val(Rs)</b></th><th scope='col' class=\"headerStyles\"><b>Total Qty</b></th>"
			+ "	<th scope='col' class=\"headerStyles\"><b>Total Val(Rs)</b></th><th scope='col' class=\"headerStyles\"><b>Unit Price(Rs)</b></th><th scope='col' class=\"headerStyles\"><b>Stock At TMT (QTY)</b></th><th scope='col' class=\"headerStyles\"><b>STK Val(Rs)</b></th>";
			System.out.println(finalcc);
			getAllfinalcc.add(finalcc);
			
			return getAllfinalcc;
		}

	@Override
	public List<String> getAllcolumnlength(String Year, String Month) {
		// TODO Auto-generated method stub
		return userDao.getAllcolumnlength(Year,Month);
	}
	
	/*
	 * @Override public String userCreationByForm(IndentMasterBean storemaster,
	 * String loginId) { return userDao.userCreationByForm( storemaster, loginId); }
	 */

	@Override
	public StringBuilder uploadBulkPoentryExcelFile(MultipartFile fileData, String loginId) {

		Workbook workbook = null;
		Sheet sheet = null;
		ByteArrayInputStream bis = null;

		StringBuilder messageBuilder = new StringBuilder();

		List<PoEntryBean> listOfUserMasters = new ArrayList<>();

		try {

			bis = new ByteArrayInputStream(fileData.getBytes());
			if (fileData.getOriginalFilename().endsWith("xls")) {
				workbook = new HSSFWorkbook(bis);

			} else if (fileData.getOriginalFilename().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(bis);
			} else {

				messageBuilder.append("Received file does not have a standard excel extension.");
				return messageBuilder;
			}

			sheet = workbook.getSheetAt(0);

			messageBuilder = extractPoEntryExcelData(sheet, listOfUserMasters);
			System.out.println(messageBuilder);

			messageBuilder = userDao.insertExcelPoEntry(listOfUserMasters, loginId);
			System.out.println(messageBuilder);

		} catch (Exception e) {
			e.printStackTrace();
			messageBuilder.append(e.getMessage());
		}
		return messageBuilder;

	}
	
	
	/**
	 * @param sheet
	 * @param poentrybean
	 */
	public StringBuilder extractPoEntryExcelData(Sheet sheet, List<PoEntryBean> listOfPoentryBean)
			throws Exception {
		PoEntryBean poentryBean;
		Row row;
		Set set = new HashSet<>();
		StringBuilder messageBuilder = new StringBuilder();

		try {
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				poentryBean = new PoEntryBean();
				row = sheet.getRow(i);
				Cell cell = null;

				// System.out.println("userselection" + row.getCell(0));
				cell = row.getCell(0);
				String CCID = checkCellType(cell);

				// System.out.println("userselection" + checkCellType(cell));
				cell = row.getCell(1);
				String Year = checkCellType(cell);

				cell = row.getCell(2);
				String MONTH = checkCellType(cell);

				cell = row.getCell(3);
				String POAMOUNT = checkCellType(cell);

				boolean check = set.add(CCID);

				if (check != true) {
					messageBuilder.append("line number " + i + " have duplicate loginId " + CCID + "\n");
					continue;
				}

				poentryBean.setCCID(CCID); // create method to check employee in HR data...

				poentryBean.setYear(Year);
				poentryBean.setPOAmount(POAMOUNT); // create method to check employee in
																					// HR data...
				poentryBean.setMonth(MONTH);
				
				// create method to check employee in HR data...

				listOfPoentryBean.add(poentryBean);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error in excel file, check and upload again.");

		}

		return messageBuilder;
	}
	
	@Override
	public String ccValidation(String CCID,String loginId) {
		return userDao.ccValidation(CCID,loginId);
	}
	
	@Override
	public String productValidation(String ProductID,String loginId) {
	
		return userDao.productValidation(ProductID,loginId);
	}
	
	@Override
	public List<Object> getAllBudgetCCIDDe() {
		List<Object> getDesignationDetails;
		getDesignationDetails = userDao.getAllBudgetCCIDDe();
		return getDesignationDetails;
	}
	
	@Override
	public List<Object> portalBlcokingMechanism(String loginId) {
		List<Object> getholidaymasterData;
		getholidaymasterData = userDao.portalBlcokingMechanism(loginId);
		return getholidaymasterData;
	}
	
	@Override
	public MasterData GetMasterData() {
		// TODO Auto-generated method stub
		return userDao.GetMasterData();
	}

	@Override
	public List<OutputForMontlyFilter> MonthlyTrend(MonthlyDataFilter filter) {
		return userDao.MonthlyTrend(filter);
	}

	@Override
	public List<Object> monthlyToalOrdaringData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> get7thworkingDay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OutputGrowthOverPreviousMonth> GrowthOverPreviousMonth(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		return userDao.GrowthOverPreviousMonth(filter);
	}

	@Override
	public List<OutputRegionWiseMonthlyDistribution> RegionWiseMonthlyDistribution(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		return userDao.RegionWiseMonthlyDistribution(filter);
	}

	@Override
	public List<OutputRegionWiseGrowthOverPreviousMonth> RegionWiseGrowthOverPreviousMonth(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		return userDao.RegionWiseGrowthOverPreviousMonth(filter);
	}

	@Override
	public List<OutputDashboardTiles> OutputDashboardTiles(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		return userDao.OutputDashboardTiles(filter);
	}

	@Override
	public List<OutputDashboardGraphs> OutputDashboardGraphs(MonthlyDataFilter filter) {
		// TODO Auto-generated method stub
		return userDao.OutputDashboardGraphs(filter);
	}


}
