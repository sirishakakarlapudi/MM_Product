package testingBulkData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.SecurityGroup;
import utilities.DataProviders;
import utilities.departmentData;
import utilities.securitygroupData;

public class SecurityGroup_Bulk extends BaseClassBulk {
	SecurityGroup securitygroup;

	@BeforeMethod
	public void pageObjects() {

		securitygroup = new SecurityGroup(driver);

	}

	@Test(priority = 1)
	public void enterUserNameandPassword() throws Throwable {
		securitygroup.userName("admin");
		securitygroup.passWord("admin");
		securitygroup.loginButton();

	}

	@Test(priority = 2)
	public void clickMaster() throws Throwable {

		securitygroup.click_titleMasters();

	}

	@Test(priority = 3)
	public void clickSecurityGroup() throws Throwable {

		securitygroup.masterClick("Security Groups");
		securitygroup.waitForCreateButton();

	}

	@Test(priority = 5, dataProvider = "SecurityGroupFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void createSecurityGroupWithBulkData(securitygroupData sgdata) throws Throwable {
		String sgname = sgdata.getSgName();
		String sgdesc = sgdata.getSgDesc();
		String module = sgdata.getModule();
		String privileges = sgdata.getPrivileges();

		securitygroup.Create();
		securitygroup.waitForSubmit();

		securitygroup.securityGroupName(sgname);
		System.out.println("SgName from Excel: " + sgname);
		System.out.println("SgDesc from Excel: " + sgdesc);
		securitygroup.securityGroupDesc(sgdesc);
		securitygroup.selModule(module);
		securitygroup.selPrivileges(privileges);
		securitygroup.createSubmit_editUpdate();
		securitygroup.passWord("admin");
		securitygroup.authenticateButton();
		securitygroup.waitForPageButton();

	}
	
	
	

	@Test(priority = 6, dataProvider = "SecurityGroupFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void BeforeApproveeditDepartment(securitygroupData sgdata) throws Exception {
		String sgname = sgdata.getSgName();
		String beforeappeditsgname = sgdata.get_BeforeAppEditSgName();
		String beforeappeditsgdesc = sgdata.get_BeforeAppEditSgDesc();
		String beforeappeditmodule = sgdata.get_BeforeAppEditModule();
		String befeoreappeditprivileges = sgdata.get_BeforeAppEditPrivileges();

		// Only proceed if at least one of the edit fields has a value
		if ((beforeappeditsgname != null && !beforeappeditsgname.trim().isEmpty())
				|| (beforeappeditsgdesc != null && !beforeappeditsgdesc.trim().isEmpty())
				|| (beforeappeditmodule != null && !beforeappeditmodule.trim().isEmpty())
				|| (befeoreappeditprivileges != null && !befeoreappeditprivileges.trim().isEmpty())) {
System.out.println("Editing Security Group: " + sgname);
			securitygroup.clickEdit(sgname);
			
			if (beforeappeditsgname != null && !beforeappeditsgname.trim().isEmpty()) {
				securitygroup.securityGroupName(beforeappeditsgname);
			}
			if (beforeappeditsgdesc != null && !beforeappeditsgdesc.trim().isEmpty()) {
				securitygroup.securityGroupDesc(beforeappeditsgdesc);
			}
			if (beforeappeditmodule != null && !beforeappeditmodule.trim().isEmpty()) {
				// Assuming selMasters() is the only module selection method available
				securitygroup.selModule(beforeappeditmodule);
			}
			if (befeoreappeditprivileges != null && !befeoreappeditprivileges.trim().isEmpty()) {
				securitygroup.selPrivileges(befeoreappeditprivileges);
			}

			securitygroup.createSubmit_editUpdate();
			securitygroup.passWord("admin");
			securitygroup.authenticateButton();
			securitygroup.waitForPageButton();
		} else {
			System.out.println("No edit values provided for: " + sgname + " → Skipping edit step.");
		}
	}
	
	
	@Test(priority = 7, dataProvider = "SecurityGroupFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	public void approveOrReturnDepartment(securitygroupData sgdata) throws Exception {
		
		String approvals = sgdata.getApprovals();
		String sgname = sgdata.getSgName();
		String beforeappeditsgname = sgdata.get_BeforeAppEditSgName();
	

		String finalSgName = (beforeappeditsgname != null && !beforeappeditsgname.trim().isEmpty()) ? beforeappeditsgname
				: sgname;
		securitygroup.waitForLoading();
		securitygroup.clickActions(finalSgName);
		if ("Accept".equalsIgnoreCase(approvals)) {
			
			securitygroup.waitForLoading();
			securitygroup.clickApprove();
			System.out.println("Accepting Department: " + finalSgName);
			securitygroup.waitForLoading();
			securitygroup.enterRemarks("Initial Approval");
			securitygroup.clickApprove();
			securitygroup.waitForLoading();
			securitygroup.passWord("admin");
			securitygroup.authenticateButton();
			securitygroup.toast();
			securitygroup.waitForLoading();

		} else if ("Return".equalsIgnoreCase(approvals)) {			
			securitygroup.waitForLoading();
			securitygroup.clickApprove();
			System.out.println("Returning Department: " + finalSgName);
			securitygroup.waitForLoading();
			securitygroup.enterRemarks("Initial Return");
			securitygroup.clickReturn();
			securitygroup.waitForLoading();
			securitygroup.passWord("admin");
			securitygroup.waitForLoading();
			securitygroup.authenticateButton();
			securitygroup.toast();
			securitygroup.waitForLoading();
		} else {
			System.out.println("Skipping invalid status: " + approvals);
			return;
		}

	}
	
	@Test(priority = 8, dataProvider = "SecurityGroupFullData", dataProviderClass =DataProviders.class, singleThreaded = true) 
	  public void editDepartment(securitygroupData sgdata)throws Exception { 
		String sgname = sgdata.getSgName();
		String editsgname = sgdata.getEditSgName();
		String editsgdesc = sgdata.getEditSgDesc();
		String editmodule = sgdata.getEditModule();
		String editprivileges = sgdata.getEditPrivileges();
		    String approvals= sgdata.getApprovals();
		    String beforeappeditsgname = sgdata.get_BeforeAppEditSgName();
	
			  
			  String finalSgName = (beforeappeditsgname != null &&!beforeappeditsgname.trim().isEmpty()) ? beforeappeditsgname : sgname;
			  
			  if (!"Return".equalsIgnoreCase(approvals)) {
			  System.out.println("Not returned. Skipping edit for: " + finalSgName);
			  return; }
			  
			  securitygroup.clickEdit(finalSgName);
			  
			  if (editsgname != null && !editsgname.trim().isEmpty()) {
					securitygroup.securityGroupName(editsgname);
				}
				if (editsgdesc != null && !editsgdesc.trim().isEmpty()) {
					securitygroup.securityGroupDesc(editsgdesc);
				}
				if (editmodule != null && !editmodule.trim().isEmpty()) {
					// Assuming selMasters() is the only module selection method available
					securitygroup.selModule(editmodule);
				}
				if (editprivileges != null && !editprivileges.trim().isEmpty()) {
					securitygroup.selPrivileges(editprivileges);
				}
			  
				securitygroup.createSubmit_editUpdate();
				securitygroup.passWord("admin");
				securitygroup.authenticateButton();
				securitygroup.waitForPageButton();
			  
	  
	  }
	
	
	
	 @Test(priority = 9, dataProvider = "SecurityGroupFullData", dataProviderClass = DataProviders.class, singleThreaded = true) 
	  public void finalApprovalDepartment(securitygroupData sgdata) throws Exception { 
		 String sgname = sgdata.getSgName();
			String editsgname = sgdata.getEditSgName();
			 String approvals= sgdata.getApprovals();
			    String beforeappeditsgname = sgdata.get_BeforeAppEditSgName();
			
			  String finalSgName = (editsgname != null && !editsgname.trim().isEmpty()) ? editsgname :
					    (beforeappeditsgname != null && !beforeappeditsgname.trim().isEmpty()) ? beforeappeditsgname :
					    	sgname;

			  
			  if (!"Return".equalsIgnoreCase(approvals)) {
			  System.out.println("Not returned. Skipping final approval for: " +
					  finalSgName); return; }
			
			  securitygroup.clickActions(finalSgName); 
			  securitygroup.waitForLoading();
			  securitygroup.clickApprove();
				System.out.println("Accepting Department: " + finalSgName);
				securitygroup.waitForLoading();
				securitygroup.enterRemarks("Final Approval");
				securitygroup.clickApprove();
				securitygroup.waitForLoading();
				securitygroup.passWord("admin");
				securitygroup.waitForLoading();
				securitygroup.authenticateButton();
				securitygroup.toast();
				securitygroup.waitForLoading();
	  }
	 
	 
	 @Test(priority = 10, dataProvider = "SecurityGroupFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
	 
	 public void updatePrivilegesAfterActive(securitygroupData sgdata) throws Throwable {
		 String sgname = sgdata.getSgName();
            String updateprivileges = sgdata.getUpdatePrivileges();
             String updateapprovals= sgdata.getUpdateApprovals();
                String editsgname = sgdata.getEditSgName();
                String beforeappeditsgname = sgdata.get_BeforeAppEditSgName();
            
              String finalSgName = (editsgname != null && !editsgname.trim().isEmpty()) ? editsgname :
                        (beforeappeditsgname != null && !beforeappeditsgname.trim().isEmpty()) ? beforeappeditsgname :
                        	sgname;

              
              if (updateprivileges == null || updateprivileges.trim().isEmpty()) {
                  System.out.println("No update privileges provided for: " + finalSgName + " → Skipping update step.");
                  return;
              }
              securitygroup.clickActions(finalSgName);      
              securitygroup.clickUpdate();
              securitygroup.selPrivileges(updateprivileges);
              securitygroup.createSubmit_editUpdate();
                securitygroup.passWord("admin");
                securitygroup.authenticateButton();
                securitygroup.waitForPageButton();
                
                
                 
                
                
	 }
	 
      @Test(priority = 11, dataProvider = "SecurityGroupFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
      public void editafterUpdatePrivileges(securitygroupData sgdata) throws Exception {
    	              String sgname = sgdata.getSgName();
    	              String afterupdateeditprivileges = sgdata.getAfterUpdateEditPrivileges();
                      String editsgname = sgdata.getEditSgName();
                      String beforeappeditsgname = sgdata.get_BeforeAppEditSgName();
                  
                    String finalSgName = (editsgname != null && !editsgname.trim().isEmpty()) ? editsgname :
                              (beforeappeditsgname != null && !beforeappeditsgname.trim().isEmpty()) ? beforeappeditsgname :
                              	sgname;

                    
                    if (afterupdateeditprivileges == null || afterupdateeditprivileges.trim().isEmpty()) {
                        System.out.println("No edit privileges provided for: " + finalSgName + " → Skipping edit step.");
                        return;
                    }
                    
                    securitygroup.clickEdit(finalSgName);
                    securitygroup.selPrivileges(afterupdateeditprivileges);
                    securitygroup.createSubmit_editUpdate();
                    securitygroup.passWord("admin");
                    securitygroup.authenticateButton();
                    securitygroup.waitForPageButton();
      }
      
      
      
      @Test(priority = 12, dataProvider = "SecurityGroupFullData", dataProviderClass = DataProviders.class, singleThreaded = true)
      public void approveOrReturnUpdatedPrivileges(securitygroupData sgdata) throws Exception {
    	      	  String sgname = sgdata.getSgName();
    	      	       	          String updateapprovals= sgdata.getUpdateApprovals();
                    String editsgname = sgdata.getEditSgName();
                    String beforeappeditsgname = sgdata.get_BeforeAppEditSgName();
                  
                 String finalSgName = (editsgname != null && !editsgname.trim().isEmpty()) ? editsgname :
                              (beforeappeditsgname != null && !beforeappeditsgname.trim().isEmpty()) ? beforeappeditsgname :
                              	sgname;
    
                 
                 if (updateapprovals == null || updateapprovals.trim().isEmpty()) {
                      System.out.println("No update approval action provided for: " + finalSgName + " → Skipping approval step.");
                      return;
      }
                    
                    
                 
                securitygroup.clickActions(finalSgName);
                if ("Accept".equalsIgnoreCase(updateapprovals)) {
                    
                    securitygroup.waitForLoading();
                    securitygroup.clickApprove();
                    System.out.println("Accepting Updated Privileges for: " + finalSgName);
                    securitygroup.waitForLoading();
                    securitygroup.enterRemarks("Updated Privileges Approval");
                    securitygroup.clickApprove();
                    securitygroup.waitForLoading();
                    securitygroup.passWord("admin");
                    securitygroup.authenticateButton();
                    securitygroup.toast();
                    securitygroup.waitForLoading();

                } else if ("Return".equalsIgnoreCase(updateapprovals)) {			
                    securitygroup.waitForLoading();
                    securitygroup.clickApprove();
                    System.out.println("Returning Updated Privileges for: " + finalSgName);
                    securitygroup.waitForLoading();
                    securitygroup.enterRemarks("Updated Privileges Return");
                    securitygroup.clickReturn();
                    securitygroup.waitForLoading();
                    securitygroup.passWord("admin");
                    securitygroup.waitForLoading();
                    securitygroup.authenticateButton();
                    securitygroup.toast();
                    securitygroup.waitForLoading();
		 
		 
	 }
                
                
			  
	
}
}
