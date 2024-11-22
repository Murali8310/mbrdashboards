package com.titan.mbrDashboard.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

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

@Repository
public interface UserDao {

	void saveLogini(UserBean userBean);


	List<Object> usersearch(String firstname);

	List<String[]> usersearchs(String firstname);


	Map<String, Object> findloginuser(UserLoginBean userLogin,String passwords);


	List<String> getPortalMIS(String loginID);

	String getPasswordbyloginId(String login_id);
	
	int updatePassword(String pwd,String cpwd ,String login_id);
	int updatePassword1(String pwd,String oldPwd ,String newPwd,String login_id,String email,String email_id, String user_Name);



	int saveLoginDetails(String login_Id,HttpServletRequest request, HttpServletResponse response);

	int updateLogouTIme(String login_id);
	//krishna
		public String getEmailIdbyLoginId(String email );
		
		//gokul

		List<String> getAllProducts(String userId);
		List<String> getCategoryList();
		List<String> getProductByCategory(String category,String userId);
		String IndentTransaction(Product[] products,String loginID,String userId,String userName);
		List<Object> getIndentList(String costcenter);

		String tempCartIndentCreation(Product[] products, String loginID, String userId, String userName);

		/**
		 * Gokul
		 * Get All Products for product Catelogue Update fetch
		 */
		List<String> getAllProductsByIndent(String userId);

		String IndentTransactionQuantitySave(Product[] products, String loginID, String userId, String userName);

		/**
		 * Gokul
		 * Get All Products for product Catelogue Update fetch
		 */
		List<String> GetAddMoreProducts(String userId);

		/**
		 * Gokul
		 * Get budeget details
		 */
		List<Object> getBudgetDetails(String userId);
		List<Object> getholidaymasterData(String userId);
		
		/**
		 * Gokul
		 * Get Products by category indent update
		 */
		List<String> getProductByCategoryIndentUpdate(String category, String userId);

		/**
		 * Gokul
		 * Get Products by category indent update filter search
		 */
		List<String> getProductByCategoryaAddMore(String category, String userId);

		String tempIndentUpdateCreation(Product[] products, String loginID, String userId, String userName);

		String IndentTransactionUpdate(Product[] products, String loginID, String userId, String userName);

		List<Object> getBuyerIndentList(String Year, String Month);

		List<Object> getBuyerFooterList(String Year, String Month, String yearfromCal1);

		/**
		 * Gokul
		 * Get All Products for product Catelogue Update fetch
		 */
		List<String> GetAllProductsByIndentNumber(String userId, String IndentNumber);

		String BuyerIndentUpdateSave(BuyerIndentBean[] products, String loginID, String userId, String userName);

	//String userCreationByForm(String empid, String empname, String designation, String city, String email, String mobile,String loginId);
	
	StringBuilder insertExcelbudgetMaster(List<Budgetmasterbean> listOfUserMasters, String loginId);
	StringBuilder insertExcelPoEntry(List<PoEntryBean> listOfUserMasters, String loginId);

	StringBuilder insertExcelholidayMaster(List<HolidayMasterBean> listOfUserMasters, String loginId);
	
	List<Object> getAllindentDetails();

	List<Object> getMOQIndentList();

	List<Object> getDistribuFooterList();
	
	List<Object> getAllindentmanagerDetails(String loginID);
	
	List<Object> getAllpasswordDetails();


	String DistributionPageSave(BuyerIndentBean[] products, String loginID, String userId, String userName);


	List<Object> getDistributerIndentList();
	List<Object> getAllIndentList();

	/**
	 * @author Masineni Krishna Sai- 22-06-2023
	 * 
	 *
	 */
	String userCreationByForm(IndentMasterBean storemaster, String loginId);
	
	List<Object> getAllCCIDDetails();
	List<Object> getAllyearDetails();
	List<String[]> getccidmasterid(String Year);
	String budgetupdate(String descode, String yearlybudget,String budgetextension,String loginId);


	String getEmailIdbyLoginIdn(String login_id);


	String checkIndentForMonth(String cFY, String MonthText, String userId);


	//String getUserEmailFromDatabase(String email);


	//String getIndentManagerEmailFromDatabase(String email);
	

	public String sendToVendor(Map<String, Object> payload,String loginID);


	String IndentQtyChange(Product[] products, String loginID, String userId, String userName);


	List<String[]> getreportbyid(String Month,String Year,String loginid);
	List<String[]> getreportbyidadmin(String Month,String Year,String loginid);
	List<String> getYears();
	public String sendToStore(String email, String email_id,Map<String, Object> payload);
	List<Object> getAllProductId();


	int updateIsImage(String productId,String userId);


	List<Object> getBuyerIndentListBasedOnFilter(String Year, String Month);


	List<Object> getBuyerFooterListBasedOnFilter(String Year, String Month);


	List<Object> getDistributionListBasedOnFilter(String Year, String Month);


	List<Object> getDistributionFooterListBasedOnFilter(String Year, String Month);


	List<Object> getBudgetDataforChart(String CostCenter);


	void sevenDayMailTrigger();


	List<Object[]> getBuyerIndentList1();


	List<Object[]> getBuyerFooterList1();


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


	List<Object> getBidgetDetailsByID(String CCID, String Year);


	String BudgetMasterUpdateByForm(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC,
			String LOCATION, String Department, String YEARLYBUDGET, String loginId,String oldYEARLYBUDGET);


	StringBuilder ProductMasterBulkUpload(List<productMasterbean> abmDetailList, String loginId);
	
	List<Object> getAllPoEntryList();


	String getAllIndentedCostCenters(String Year, String Month);


	List<String> getAllcolumnlength(String Year, String Month);


	String ccCreationSave(String CCID, String Year, String COSTCENTERDESC, String GL, String GLDESC, String LOCATION,
			String Department, String CCowner, String YEARLYBUDGET, String loginId,String COSTEMAIL);


	String ccValidation(String CCID, String loginId);
	String productValidation(String ProductID, String loginId);


	List<Object> getAllBudgetCCIDDe();


	List<Object> getBuyerIndentListForvendor(String Year, String Month);


	List<String> getAllHeadersList(String Year, String Month);

	List<Object> portalBlcokingMechanism(String userId);
	
	List<Object> monthlyToalOrdaringData();


	List<OutputForMontlyFilter> MonthlyTrend(MonthlyDataFilter filter);


	MasterData GetMasterData(MonthlyDataFilter filter);


	List<OutputGrowthOverPreviousMonth> GrowthOverPreviousMonth(MonthlyDataFilter filter);
	List<OutputRegionWiseMonthlyDistribution> RegionWiseMonthlyDistribution(MonthlyDataFilter filter);


	List<OutputRegionWiseGrowthOverPreviousMonth> RegionWiseGrowthOverPreviousMonth(MonthlyDataFilter filter);


	List<OutputDashboardTiles> OutputDashboardTiles(MonthlyDataFilter filter);


	List<OutputDashboardGraphs> OutputDashboardGraphs(MonthlyDataFilter filter);



	MasterData getFilterData(MonthlyDataFilter data);


	List<OutputMonthlyOrdaringBehaviour> monthlyOrdaringBehaviour(MonthlyDataFilter filter);


	List<OutputRegionWiseMonthlyDistributionNoofOrders> regionWiseMonthlyDistributionNoofOrders(
			MonthlyDataFilter filter);


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