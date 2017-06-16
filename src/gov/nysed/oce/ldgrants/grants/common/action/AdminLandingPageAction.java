package gov.nysed.oce.ldgrants.grants.common.action;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.AdminLandingPage;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.AdminLandingPageService;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import mypackage.DropDownListBean;

public class AdminLandingPageAction extends LDGrantSessionManager{

	
private static final String ADULT_LITERACY = "adultlit";
private static final String FAMILY_LITERACY = "familylit";
    
	
 private List<DropDownListBean> fiscalYears;
 private Long fyCode;
 private Long fcCode;
 
 private AdminLandingPage adminLandingPage;
	
  




public String literacyLandingPage() throws Exception 
  {       
     
      try{                   
                   
        //get fund for this fcCode - navigate to home page
		  FundProgram fund = FundProgram.searchByFundCode(fcCode.intValue());
		  
		  if(fund == FundProgram.FAMILY_LITERACY)
			  return FAMILY_LITERACY;
		  else if(fund == FundProgram.ADULT_LITERACY)
			  return ADULT_LITERACY;
		  
                          
      }catch(Exception e){
    	  System.err.println("error AdminLandingPageAction.literacyLandingPage() "+e.getMessage().toString());
      }
    return SUCCESS;        
  }      
		    
	  
	  
  public String literacyLandingPageSearch() throws Exception 
  {             		        
      try{         
         
          //this uses new spring jdbc query, new objects
          AdminLandingPageService service = new AdminLandingPageService();
          adminLandingPage = service.buildAdminHomePage(fyCode, fcCode);
          
          
          //get fund for this fcCode - navigate to home page
		  FundProgram fund = FundProgram.searchByFundCode(fcCode.intValue());
		  
		  if(fund == FundProgram.FAMILY_LITERACY)
			  return FAMILY_LITERACY;
		  else if(fund == FundProgram.ADULT_LITERACY)
			  return ADULT_LITERACY;
		  
		  
      }catch(Exception e){
    	  System.err.println("error AdminLandingPageAction.literacyLandingPageSearch() "+e.getMessage().toString());
      }
    return SUCCESS;        
  }      
  
  




public List<DropDownListBean> getFiscalYears() {
	return fiscalYears;
}


public void setFiscalYears(List<DropDownListBean> fiscalYears) {
	this.fiscalYears = fiscalYears;
}




public Long getFyCode() {
	return fyCode;
}




public void setFyCode(Long fyCode) {
	this.fyCode = fyCode;
}





public Long getFcCode() {
	return fcCode;
}






public void setFcCode(Long fcCode) {
	this.fcCode = fcCode;
}









public AdminLandingPage getAdminLandingPage() {
	return adminLandingPage;
}



public void setAdminLandingPage(AdminLandingPage adminLandingPage) {
	this.adminLandingPage = adminLandingPage;
}


  
}
