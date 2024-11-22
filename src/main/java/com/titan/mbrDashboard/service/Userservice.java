package com.titan.mbrDashboard.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.titan.mbrDashboard.bean.BuyerIndentBean;
import com.titan.mbrDashboard.bean.IndentMasterBean;
import com.titan.mbrDashboard.bean.Product;
import com.titan.mbrDashboard.bean.UserLoginBean;
import com.titan.mbrDashboard.bean.smUserMasterBean;
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

public interface Userservice {

	int saveLoginDetails(String login_Id, HttpServletRequest request, HttpServletResponse response);
	

	List<Object[]> getAllMenuNamesByLoginId(String loginId);

	List<Object[]> getAllSubmenuNamesByLoginId(String loginId, int menuId);

	// Map findUser(UserLoginBean userLogin);

	Map<String, Object> findloginuser(UserLoginBean userLogin,String Passwords);


	List<Object> usersearch(String firstname);

	List<String[]> usersearchs(String firstname);


	String getPasswordByloginId(String login_id);
	
	int updatePassword(String pwd,String cpwd ,String login_id);
	int updatePassword1(String pwd,String oldPwd ,String newPwd,String login_id,String email,String email_id,String user_Name);

	String getEmailIdByloginId(String email);
	
	int updateLogouTIme(String login_id);
	
/**
 * Gokul Get All Products
 * @return
 */
	List<String> getAllProducts(String userId);
	List<String> getCategoryList();
	
	/**
	 * Gokul Get Products By Category
	 * @return
	 */
	List<String> getProductByCategory(String category,String userId);


	String IndentTransaction(Product[] products, String loginId,String userId,String userName);
	

	List<Object> getgetIndentList(String costcenter);
	
	String tempCartIndentCreation(Product[] products, String loginId,String userId,String userName);

	List<String> getAllProductsByIndent(String loginId);

	String IndentTransactionQuantitySave(Product[] products, String loginID, String userId, String userName);

	List<String> GetAddMoreProducts(String loginId);

	List<Object> getBudgetDetails(String loginId);
	List<Object> getholidaymasterData(String loginId);

	List<String> getProductByCategoryIndentUpdate(String category, String userId);

	List<String> getProductByCategoryaAddMore(String category, String userId);

	String tempIndentUpdateCreation(Product[] products, String loginID, String userId, String userName);

	String IndentTransactionUpdate(Product[] products, String loginID, String userId, String userName);

	List<Object> getBuyerIndentList(String Year, String Month);

	List<Object> getBuyerFooterList(String Year, String Month, String yearfromCal1);	
	
	List<String> GetAllProductsByIndentNumber(String loginId, String IndentNumner);

	String BuyerIndentUpdateSave(BuyerIndentBean[] products, String loginID, String userId, String userName);
	
		//String userCreationByForm(String empid, String empname, String designation, String city, String email, String mobile,String loginId);
	
	StringBuilder uploadBulkbudgetExcelFile(MultipartFile file, String loginId);
	StringBuilder uploadBulkholidayExcelFile(MultipartFile file, String loginId);
	StringBuilder uploadBulkPoentryExcelFile(MultipartFile file, String loginId);


	List<Object> getAllindentDetails();
	List<Object> getAllindentmanagerDetails(String loginId);
	List<Object> getAllpasswordDetails();


	String userCreationByForm();
	List<Object> getAllCCIDDetails();
	
	List<Object> getMOQIndentList();

	List<Object> getDistribuFooterList();


	String DistributionPageSave(BuyerIndentBean[] products, String loginID, String userId, String userName);


	List<Object> getDistributerIndentList();
	
	List<String[]> getccidmasterid(String Year);
	List<Object> getAllyearDetails();
	String budgetupdate(String descode, String yearlybudget,String budgetextension,String loginId);
	String getEmailIdByloginIdn(String email);
	//String getUserEmailFromDatabase(String email);
	//String getIndentManagerEmailFromDatabase(String email);
	
	String checkIndentForMonth(String cFY, String MonthText, String userId);
	
	public String sendToVendor(Map<String, Object> payload,String loginId);
	
	List<String[]> getreportbyid(String Month,String Year,String loginid);
	List<String[]> getreportbyidadmin(String Month,String Year,String loginid);


	List<String> getYears();
	public String sendToStore(String email, String email_id, Map<String, Object> payload);


	List<Object> getAllProductId();


	int updateIsImage(String productId,String userId);


	List<Object> getBuyerIndentListBasedOnFilter(String Year, String Month);


	List<Object> getBuyerFooterListBasedOnFilter(String Year, String Month);


	List<Object> getDistributionListBasedOnFilter(String Year, String Month);


	List<Object> getDistributionFooterListBasedOnFilter(String Year, String Month);


	List<Object> getBudgetDataforChart(String costCenter);


	void sevenDayMailTrigger();


	List<String> get7thworkingDay();


	List<Object> getAllVendor();


	List<Object> getVendorByEmailId(String vendorname);




	String ProductMasterCreationSave(String ProductID, String ProductName, String vendor, String EmailID, String Price,
			String UOM, String Category, String loginId);


	List<String> getAllProductsForList();


	List<Object> getProductDetailsByID(String ProductID);


	String ProductMasterCreationUpdate(String ProductID, String ProductName, String vendor, String EmailID,
			String Price, String UOM, String Category, String loginId);


	List<String> getAllBudgetForList();


	
	String BudgetMasterCreationSave(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC,
			String LOCATION, String Department, String YEARLYBUDGET, String loginId);
	String poEntryCreationSave(String Year, String Month, String costCenter, String PoAmount,String loginId);
	String poEntryDataUpdation(String costCenter, String PoAmount,String Month,String year,String createdBy,String createdOn,String loginId);
	List<Object> getAllCCIDDe();
	List<Object> getAllIndentList();

	List<Object> getBidgetDetailsByID(String CCID, String Year);


	String BudgetMasterUpdateByForm(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC,
			String LOCATION, String Department, String YEARLYBUDGET, String loginId,String oldYEARLYBUDGET);


	StringBuilder ProductMasterBulkUpload(MultipartFile fileData, String loginId);
	List<Object> getAllPoEntryList();


	List<String> getAllheader(String Year, String Month);
	
	List<String> getAllcolumnlength(String Year, String Month);


	String ccCreationSave(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC, String LOCATION,
			String Department, String CCOwner, String YEARLYBUDGET, String loginId,String COSTEMAIL);
	
	
	String ccValidation(String CCID, String loginId);
	String productValidation(String ProductID, String loginId);


	List<Object> getAllBudgetCCIDDe();
	
	List<Object> portalBlcokingMechanism(String loginId);

	List<Object> monthlyToalOrdaringData();


	MasterData GetMasterData(MonthlyDataFilter filter);


	List<OutputForMontlyFilter> MonthlyTrend(MonthlyDataFilter filter);


	List<OutputGrowthOverPreviousMonth> GrowthOverPreviousMonth(MonthlyDataFilter filter);


	List<OutputRegionWiseMonthlyDistribution> RegionWiseMonthlyDistribution(MonthlyDataFilter filter);


	List<OutputRegionWiseGrowthOverPreviousMonth> RegionWiseGrowthOverPreviousMonth(MonthlyDataFilter filter);


	List<OutputDashboardTiles> OutputDashboardTiles(MonthlyDataFilter filter);


	List<OutputDashboardGraphs> OutputDashboardGraphs(MonthlyDataFilter filter);


	MasterData getFilterData(InputFilterData data);


	MasterData getFilterData(MonthlyDataFilter filter);


	List<OutputMonthlyOrdaringBehaviour> monthlyOrdaringBehaviour(MonthlyDataFilter filter);


	List<OutputRegionWiseMonthlyDistributionNoofOrders> regionWiseMonthlyDistributionNoofOrders(MonthlyDataFilter filter);


	List<OutputRegionWiseMonthlyAvgPerOrder> regionWiseMonthlyAvgPerOrder(MonthlyDataFilter filter);


	List<OutputPercentageofOrdersbyDayoftheMonth> percentageofOrdersbyDayoftheMonth(MonthlyDataFilter filter);


	List<OutputPercentageofOrdersbyWeekdayorWeekend> percentageofOrdersbyWeekdayorWeekend(MonthlyDataFilter filter);


	List<OutputPercentageofOrdersByWeekdayorWeekendRegionWise> percentageofOrdersByWeekdayorWeekendRegionWise(
			MonthlyDataFilter filter);


	List<OutputPercentaageOfOrdersByHourOfTheDayOnWeekdayWeekend> percentaageOfOrdersByHourOfTheDayOnWeekdayWeekend(
			MonthlyDataFilter filter);


	List<OutputForMontlyFilter> MonthlyTrendRegular(MonthlyDataFilter filter);


	List<OutputGrowthOverPreviousMonth> growthOverPreviousMonthRegular(MonthlyDataFilter filter);


	List<OutputRegionWiseMonthlyDistribution> regionWiseMonthlyDistributionRegular(MonthlyDataFilter filter);


	List<OutputRegionWiseGrowthOverPreviousMonth> regionWiseGrowthOverPreviousMonthRegular(MonthlyDataFilter filter);


	List<OutputMonthlyOrdaringBehaviour> monthlyOrdaringBehaviourRegular(MonthlyDataFilter filter);


	List<OutputRegionWiseMonthlyDistributionNoofOrders> regionWiseMonthlyDistributionNoofOrdersRegular(
			MonthlyDataFilter filter);

}