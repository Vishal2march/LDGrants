package gov.nysed.oce.ldgrants.email.service;

import gov.nysed.oce.ldgrants.email.domain.Recipient;
import gov.nysed.oce.ldgrants.grants.address.domain.ContactType;
import gov.nysed.oce.ldgrants.grants.address.domain.GrantStaff;
import gov.nysed.oce.ldgrants.grants.address.domain.ProjectManager;
import gov.nysed.oce.ldgrants.sedref.domain.SedAdminPosition;
import gov.nysed.oce.ldgrants.sedref.domain.SedContactInfo;

/**
 * Map various objects (ProjectManager, GrantStaff, SedAdminPosition, etc) to Recipient object.
 * Used with email module.  Recipient object will store standard name/email for displaying on 
 * common "recipients" page.
 * @author shusak
 *
 */
public class RecipientMappingService {

	
	public Recipient mapGrantStaff(GrantStaff g){
		
		Recipient r = null;
		try{			
			if(g!=null){
				r = new Recipient();
				r.setName(g.getFname() +" "+ g.getLname());
				r.setTitle(g.getTitle());
				r.setEmail(g.getEmail().getContactValue());
			}
			
		}catch(Exception e){
			System.err.println("RecipientMappingService.mapGrantStaff "+e.getMessage());
		}
		return r;				
	}
	
	
	
	
	public Recipient mapProjectManager(ProjectManager pm){
		
		Recipient r = null;
		try{			
			if(pm!=null){
				r = new Recipient();
				r.setName(pm.getFname() +" "+ pm.getLname());
				r.setTitle(pm.getTitle());
				r.setEmail(pm.getEmail().getContactValue());
			}
			
		}catch(Exception e){
			System.err.println("RecipientMappingService.mapProjectManager "+e.getMessage());
		}
		return r;				
	}
	
	
	
	
	
	public Recipient mapSedAdminPosition(SedAdminPosition sap){
		
		Recipient r = null;
		try{			
			if(sap!=null){
				r = new Recipient();
				r.setName(sap.getFname() +" "+ sap.getLname());
				r.setTitle(sap.getTitle());
				
				if(sap.getContactInfos()!=null){
					
					for(SedContactInfo s: sap.getContactInfos()){
						if(ContactType.searchByContactTypeId(s.getContactTypeCode().intValue())==ContactType.EMAIL)
							r.setEmail(s.getContactValue());
					}
				}				
			}
			
		}catch(Exception e){
			System.err.println("RecipientMappingService.mapSedAdminPosition "+e.getMessage());
		}
		return r;				
	}
}
