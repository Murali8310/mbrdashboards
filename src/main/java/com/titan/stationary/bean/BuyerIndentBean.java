package com.titan.stationary.bean;

public class BuyerIndentBean {
	 private String Documnet;
	    private String costcenter;
	    private String description;
	    private int quantity;
	    private String unitPrice;
	    private int MoqQty;
	    private String MoqValue;
	   // private String BuyerId;
	    private int BalanceTMTQty;
	    private String BalanceTMTValue;
	    private int receivedqty;
	    private String receivedvalue;
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public String getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(String unitPrice) {
			this.unitPrice = unitPrice;
		}
		public String getDocumnet() {
			return Documnet;
		}
		public void setDocumnet(String documnet) {
			Documnet = documnet;
		}
		public String getCostcenter() {
			return costcenter;
		}
		public void setCostcenter(String costcenter) {
			this.costcenter = costcenter;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getMoqQty() {
			return MoqQty;
		}
		public void setMoqQty(int moqQty) {
			MoqQty = moqQty;
		}
		public String getMoqValue() {
			return MoqValue;
		}
		public void setMoqValue(String moqValue) {
			MoqValue = moqValue;
		}
		public int getBalanceTMTQty() {
			return BalanceTMTQty;
		}
		public void setBalanceTMTQty(int balanceTMTQty) {
			BalanceTMTQty = balanceTMTQty;
		}
		public String getBalanceTMTValue() {
			return BalanceTMTValue;
		}
		public void setBalanceTMTValue(String balanceTMTValue) {
			BalanceTMTValue = balanceTMTValue;
		}
		public int getReceivedqty() {
			return receivedqty;
		}
		public void setReceivedqty(int receivedqty) {
			this.receivedqty = receivedqty;
		}
		public String getReceivedvalue() {
			return receivedvalue;
		}
		public void setReceivedvalue(String receivedvalue) {
			this.receivedvalue = receivedvalue;
		}
		
		
	   
	
	
	
}