package gov.nysed.oce.ldgrants.grants.common.service;

import gov.nysed.oce.ldgrants.grants.common.dao.AuthorizationDao;
import gov.nysed.oce.ldgrants.grants.common.domain.AdminPosition;
import gov.nysed.oce.ldgrants.grants.common.domain.Authorization;
import gov.nysed.oce.ldgrants.user.domain.User;


public class AuthorizationService {

	private AuthorizationDao authDao = new AuthorizationDao();
	
	
	public Long getNextAuthorizationId(){
		return authDao.getNextAuthorizationId();
	}
	
	public int insertFinalSignoffAuthorization(User user, Long grantId){
		
		int result=0;
		
		try{
			//get info for library director from sedref
			InstitutionService instService = new InstitutionService();
			AdminPosition adminPostition = instService.getAdminPositionDetail(grantId, "Library Director");
			
			//create authorization object
			Authorization auth = new Authorization();
			auth.setId(authDao.getNextAuthorizationId());
			auth.setAuthorizingUser(user.getCreatedBy());
			auth.setVersion("FinalSignoff");
			auth.setName(adminPostition.getFname() +" "+ adminPostition.getLname() );
			auth.setTitle(adminPostition.getTitle());
			auth.setGrantId(grantId);
			
			//insert final signoff record into authorizations table
			authDao.insertAuthorization(auth, user);
			
			
		}catch(Exception e){
			System.out.println("AuthorizationService.insertFinalSignoffAuthorization "+ e.getMessage());
		}
		return result;
		
	}
	
	
	
	
	
public int insertAmendmentAuthorization(User user, Long grantId){
		
		int result=0;
		
		try{
			//get info for library director from sedref
			InstitutionService instService = new InstitutionService();
			AdminPosition adminPostition = instService.getAdminPositionDetail(grantId, "Library Director");
			
			//create authorization object
			Authorization auth = new Authorization();
			auth.setId(authDao.getNextAuthorizationId());
			auth.setAuthorizingUser(user.getCreatedBy());
			auth.setVersion("Amendment");
			auth.setName(adminPostition.getFname() +" "+ adminPostition.getLname() );
			auth.setTitle(adminPostition.getTitle());
			auth.setGrantId(grantId);
			
			//insert final signoff record into authorizations table
			authDao.insertAuthorization(auth, user);
			
			
		}catch(Exception e){
			System.out.println("AuthorizationService.insertAmendmentAuthorization "+ e.getMessage());
		}
		return result;
		
	}
}
