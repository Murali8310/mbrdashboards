package com.titan.mbrDashboard.bean;

public class productMasterbean {
	
    private String productid;	
	private String Productname;
	private String Vendor;
	private String VendorEmailId;
	private String Price;
	private String UOM;
	private String Category;
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return Productname;
	}
	public void setProductname(String productname) {
		Productname = productname;
	}
	public String getVendor() {
		return Vendor;
	}
	public void setVendor(String vendor) {
		Vendor = vendor;
	}
	public String getVendorEmailId() {
		return VendorEmailId;
	}
	public void setVendorEmailId(String vendorEmailId) {
		VendorEmailId = vendorEmailId;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getUOM() {
		return UOM;
	}
	public void setUOM(String uOM) {
		UOM = uOM;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	
	@Override
	public String toString() {
		return "productMasterbean [productid=" + productid + ", Productname=" + Productname + ", Vendor=" + Vendor
				+ ", VendorEmailId=" + VendorEmailId + ", Price=" + Price + ", UOM=" + UOM + ", Category=" + Category
				+ "]";
	}

	
}
