package gov.nysed.oce.ldgrants.email.service;

import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.user.domain.User;
import mypackage.DbName;
import sedems.AuthenticateBean;
import sedems.SEDEMSEZClass;

public class SedemsAuthenticateService extends LDGrantSessionManager{
	
    private static final String TEST_URL = "http://eservicest.nysed.gov/sedems/EmailWebService?wsdl";
    private static final String PROD_URL = "http://eservices.nysed.gov/sedems/EmailWebService?WSDL";
       
		
	
	public AuthenticateBean sedemsAuthenticateLD(boolean isProd){
	
		AuthenticateBean ab=new AuthenticateBean();
	    ab.setApplicationId(61);
	    ab.setName("LDGRANTS");
	    ab.setPassword("LDGRANTSPWD");
	    
	    if(isProd)
		       ab.setProviderURL(PROD_URL);
		    else
		        ab.setProviderURL(TEST_URL);
	    return ab;
	}
		
	
	public AuthenticateBean sedemsAuthenticateArchives(boolean isProd){
	        
	    AuthenticateBean ab=new AuthenticateBean();
	    ab.setApplicationId(121);
	    ab.setName("SAGRANTS");
	    ab.setPassword("SAGRANTSPWD");
	    if(isProd)
		       ab.setProviderURL(PROD_URL);
		    else
		        ab.setProviderURL(TEST_URL);
	    return ab;
	}
		
	
	public AuthenticateBean sedemsAuthenticateConstruction(boolean isProd){
        
	    AuthenticateBean ab =new AuthenticateBean();
	    ab.setApplicationId(142);
	    ab.setName("CONSTRUCTION");
	    ab.setPassword("CONSTRUCTIONPWD");
	    if(isProd)
	       ab.setProviderURL(PROD_URL);
	    else
	        ab.setProviderURL(TEST_URL);
	    return ab;
	}
	
	
	
	public SEDEMSEZClass authenticate(FundProgram fund){
		
		try{
			User user= ldSession.getUser();
			
			DbName db = new DbName();//old method to check tref vs pref
			
			AuthenticateBean auth =null;			
			switch(fund){
				case CONSTRUCTION: 
					auth = sedemsAuthenticateConstruction(db.isProduction());
					break;
				case LGRMIF:
					auth = sedemsAuthenticateArchives(db.isProduction());
					break;
				case ADULT_LITERACY:
				case FAMILY_LITERACY:
				case CP_DISCRETIONARY:
				case CP_STATUTORY:
					auth = sedemsAuthenticateLD(db.isProduction());
					break;
			}
			
			return new SEDEMSEZClass(user.getUserId(), auth);
			
		}catch(Exception e){
			System.err.println("SedemsAuthenticateService.authenticate() "+e.getMessage());
		}
		return null;		
	}

}
