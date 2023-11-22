package com.titan.stationary.bean;

public class PoEntryBean {
	 private String CCID;	
		private String Year;
		private String Month;
		private String POAmount;
		public String getCCID() {
			return CCID;
		}
		public void setCCID(String cCID) {
			CCID = cCID;
		}
		public String getYear() {
			return Year;
		}
		public void setYear(String year) {
			Year = year;
		}
		public String getMonth() {
			return Month;
		}
		public void setMonth(String month) {
			Month = month;
		}
		public String getPOAmount() {
			return POAmount;
		}
		public void setPOAmount(String pOAmount) {
			POAmount = pOAmount;
		}
		@Override
		public String toString() {
			return "PoEntryBean [CCID=" + CCID + ", Year=" + Year + ", Month=" + Month + ", POAmount=" + POAmount + "]";
		}
		
	
	
	

}
