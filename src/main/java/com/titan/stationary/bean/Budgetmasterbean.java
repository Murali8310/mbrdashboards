package com.titan.stationary.bean;

public class Budgetmasterbean {
	
    private String CCID;	
	private String Year;

	private String CostCenterDescription;

	private String GL;

	private String GLDescription;

	private String Location;

	private String CostOwner;
	
	private String Department;
	
	private String BudValueRsL;
	private String Email;	

	@Override
	public String toString() {
		return "Budgetmasterbean [CCID=" + CCID +", Year=" + Year + ", CostCenterDescription=" + CostCenterDescription + ", GL=" + GL + ", GLDescription="
				+ GLDescription + ", Location=" + Location + ", CostOwner=" + CostOwner + ", Department=" + Department + ", BudValueRsL=" + BudValueRsL + "]";
	}

	public String getCCID() {
		return CCID;
	}

	public String getEmail() {
		return Email;
	}

	public void setCCID(String cCID) {
		CCID = cCID;
	}
	public void setEmail(String email) {
		Email = email;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getCostCenterDescription() {
		return CostCenterDescription;
	}

	public void setCostCenterDescription(String costCenterDescription) {
		CostCenterDescription = costCenterDescription;
	}

	public String getGL() {
		return GL;
	}

	public void setGL(String gL) {
		GL = gL;
	}

	public String getGLDescription() {
		return GLDescription;
	}

	public void setGLDescription(String gLDescription) {
		GLDescription = gLDescription;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getCostOwner() {
		return CostOwner;
	}

	public void setCostOwner(String costOwner) {
		CostOwner = costOwner;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getBudValueRsL() {
		return BudValueRsL;
	}

	public void setBudValueRsL(String budValueRsL) {
		BudValueRsL = budValueRsL;
	}


	
}
