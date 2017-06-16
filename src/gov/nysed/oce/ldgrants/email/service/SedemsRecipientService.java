package gov.nysed.oce.ldgrants.email.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nysed.oce.ldgrants.email.domain.MessageVariableValue;
import gov.nysed.oce.ldgrants.email.domain.Recipient;
import gov.nysed.oce.ldgrants.email.domain.RecipientGroup;
import gov.nysed.oce.ldgrants.grants.address.domain.Contact;
import gov.nysed.oce.ldgrants.grants.address.domain.ContactType;
import gov.nysed.oce.ldgrants.grants.address.domain.GrantStaff;
import gov.nysed.oce.ldgrants.grants.address.domain.ProjectManager;
import gov.nysed.oce.ldgrants.grants.address.domain.StaffType;
import gov.nysed.oce.ldgrants.grants.address.service.ContactService;
import gov.nysed.oce.ldgrants.grants.address.service.GrantStaffService;
import gov.nysed.oce.ldgrants.grants.address.service.ProjectManagerService;
import gov.nysed.oce.ldgrants.grants.common.dao.ApprovalDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.enumeration.ApprovalType;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.enumeration.SubmitApproveVersion;
import gov.nysed.oce.ldgrants.sedref.domain.SedAdminPosition;
import gov.nysed.oce.ldgrants.sedref.domain.SedAdminPositionType;
import gov.nysed.oce.ldgrants.sedref.service.SedAdminPositionService;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;

public class SedemsRecipientService extends LDGrantSessionManager{

	
	
	/**
	 * Method will get all GRANT objects for a given FY/Fund/ApprovalType. This will get list of all 
	 * approved/denied grants where VERSION=INITIAL.
	 * @param fyCode
	 * @param fcCode
	 * @param approveType
	 * @return
	 */
	public List<Grant> generateApplicationList(long fyCode, long fcCode, ApprovalType approveType){
		
		List<Grant> grants = new ArrayList<Grant>();
		try{
		ApprovalDao aDao = new ApprovalDao();
		
		//get all approval records for fund/FY
		List<Grant> allApprove = aDao.searchByFundCodeFyCode(fcCode, fyCode);
		System.out.println("all appr size "+allApprove.size());
		
		
		//filter for user selected recipient group - all approved/denied for FY
		for(Grant g: allApprove){
			String version = g.getApproval().getVersion();
							
			//for "initial" versions only
			if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.Initial){
				
				//filter for user selected approval type (approve/deny)
				if(ApprovalType.searchByString(g.getApproval().getApprovalType())==approveType){
					System.out.println("gra_id "+g.getGrantid());
					grants.add(g);
				}
			}			
		}		
		
		System.out.println("grant size" +grants.size());
		
		}catch(Exception e){
			System.err.println("SedemsRecipientService.generateApplicationList() "+e.getMessage());
		}
		return grants;		
	}
	
	
	
	
		
	public List<Grant> queryProjectManagerContact(List<Grant> grants){
				
		try{
		System.out.println("query size "+grants.size());
		
		ProjectManagerService pmService = new ProjectManagerService();
		ContactService cService = new ContactService();
		
		//for each grant in list; get project manager; and all contacts
		for(Grant g: grants){
			
			ProjectManager pm = pmService.select(g.getPrmId());
			
			pm.setContacts(cService.searchByProjectManagerId(pm.getId()));		
			
			g.setProjectManager(pm);
		}
		
		
		for(Grant g: grants){
			List<Contact> contacts = g.getProjectManager().getContacts();
			for(Contact c: contacts){
				if(c.getContactType()==ContactType.EMAIL){
					System.out.println("email "+c.getContactValue());
					g.getProjectManager().setEmail(c);
				}
			}
		}
		
		}catch(Exception e){
			System.err.println("SedemsRecipientService.queryProjectManagerContact() "+e.getMessage());
		}
		return grants;		
	}
	
	
	
	
	public List<Grant> queryGrantStaffContact(List<Grant> grants, StaffType staffType){
		
		try{
		System.out.println("gs query size "+grants.size());
		
		GrantStaffService gsService = new GrantStaffService();
		ContactService cService = new ContactService();
		
		//for each grant in list; get grant_staff (staff_type_id); and all contacts
		for(Grant g: grants){
			
			GrantStaff gs = gsService.searchByGrantIdStaffTypeId(g.getId(), staffType.getStaffTypeId());
			
			if(gs!=null){
				gs.setContacts(cService.searchByGrantStaffId(gs.getId()));		
			
				if(g.getGrantStaffs()==null)
					g.setGrantStaffs(new ArrayList<GrantStaff>());
			
				g.getGrantStaffs().add(gs);
			}
		}
		
		
		for(Grant g: grants){
			
			for(GrantStaff gs: g.getGrantStaffs()){
				
				if(gs.getStaffType().equals(staffType)){
					
					for(Contact c: gs.getContacts()){
						if(c.getContactType()==ContactType.EMAIL){
							System.out.println("GS email "+c.getContactValue());
							gs.setEmail(c);
						}
					}//end contacts for loop					
				}
				
			}//end grantstaffs for loop
						
		}//end grants for loop
		
		}catch(Exception e){
			System.err.println("SedemsRecipientService.queryGrantStaffContactF() "+e.getMessage());
		}
		return grants;		
	}
	
	
	
	
	
	
	
public List<Grant> querySedrefAdminPositionContact(List<Grant> grants, SedAdminPositionType adminType){
		
		try{
		System.out.println("sap query size "+grants.size());
		
		SedAdminPositionService sapService = new SedAdminPositionService();
		
		//for each grant in list; get sedref admin position and all contacts
		for(Grant g: grants){
			
			SedAdminPosition sap = sapService.selectByInstIdAdminPositionType(g.getInstId(), adminType);
			g.getAdminPositions().add(sap);
		}						
		
		}catch(Exception e){
			System.err.println("SedemsRecipientService.querySedrefAdminPositionContact() "+e.getMessage());
		}
		return grants;		
	}
	
	
	
	
	
	public List<Grant> generateGrantList(long fyCode, long fcCode, ApprovalType approveType){
		
		List<Grant> grants = null;
		try{
		
			grants = generateApplicationList(fyCode, fcCode, approveType);
					
		}catch(Exception e){
			System.err.println("SedemsRecipientService.generateGrantList() "+e.getMessage());
		}
		return grants;	
	}
	
	
	
	
public List<Grant> generatePotentialRecipientList(List<Grant> grants, RecipientGroup group){
	
	try{		
					
		switch(group){
		case PROJECT_MANAGER:
				grants = queryProjectManagerContact(grants);					
				break;
		case ADDITIONAL_CONTACT:
				grants = queryGrantStaffContact(grants, StaffType.ADDITIONAL_CONTACT);
				break;
		case LITERACY_CONTACT:
				grants = queryGrantStaffContact(grants, StaffType.LITERACY_CONTACT);
				break;
		case SEDREF_CEO:
				grants = querySedrefAdminPositionContact(grants, SedAdminPositionType.CEO);
				break;
		}
						
	}catch(Exception e){
		System.err.println("SedemsRecipientService.generatePotentialRecipientList() "+e.getMessage());
	}
	return grants;	
}
	
    
    
}
