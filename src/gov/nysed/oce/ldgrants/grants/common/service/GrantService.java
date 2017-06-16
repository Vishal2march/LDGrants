package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.dao.ApprovalDao;
import gov.nysed.oce.ldgrants.grants.common.dao.GrantDao;
import gov.nysed.oce.ldgrants.grants.common.dao.GrantSubmissionDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;


public class GrantService {

	private GrantDao grantDao = new GrantDao();
	private GrantSubmissionDao submissionDao = new GrantSubmissionDao();
	private ApprovalDao approvalDao = new ApprovalDao();
	private ApplicationDateService dateService = new ApplicationDateService();
	

	public Grant getGrant(Long grantId) {
		return grantDao.selectGrant(grantId);
	}
	
	public List<Grant> searchByInstId(Long id) {
		return grantDao.searchByInstId(id);
	}
	
	
	public GrantStatus getGrantStatus(Long grantId) {
		
		GrantStatus grantStatus = null;
		try{
			
		//get flags from GRANTS table
		grantStatus = grantDao.selectGrantStatusItems(grantId);
		
		//get all approval records
		List<Approval> approvals = approvalDao.searchApprovalsByGrant(grantId);
		
		//get all submission records
		List<GrantSubmission> submissions = submissionDao.searchSubmissionByGrant(grantId);
		
		//check application dates (available/due dates vs current date)
		grantStatus.setApplicationDate(dateService.getAvailableDueDateStatus(
										grantStatus.getFyCode(), grantStatus.getFcCode()));
				
		//set submit fields
		for(int i=0; i<submissions.size(); i++)
	    {
			GrantSubmission submission = (GrantSubmission)submissions.get(i);
			
	        if(submission.getVersion().equalsIgnoreCase("Initial"))
	        	grantStatus.setInitialSubmitted(true);
	        else if(submission.getVersion().equalsIgnoreCase("Final"))
	        	grantStatus.setFinalSubmitted(true);   
	        else if(submission.getVersion().equalsIgnoreCase("Amendment"))
	        	grantStatus.setAmendmentSubmitted(true);
	        else if(submission.getVersion().equalsIgnoreCase("Final Year2"))
	        	grantStatus.setFinalYr2Submitted(true);
	        else if(submission.getVersion().equalsIgnoreCase("Final Year3"))
	        	grantStatus.setFinalYr3Submitted(true);
	        else if(submission.getVersion().equalsIgnoreCase("DASNY"))
	        	grantStatus.setDasnySubmitted(true);
	        else if(submission.getVersion().equalsIgnoreCase("MWBE"))
	        	grantStatus.setMwbeSubmitted(true);        
	    }
		
		//set approve fields
		for(int i=0; i<approvals.size(); i++)
	    {
			Approval approval =approvals.get(i);
			
			if(approval.getApprovalType().equalsIgnoreCase("Approve")){
				
				if(approval.getVersion().equalsIgnoreCase("Initial"))
					grantStatus.setInitialApproved(true);
				else if(approval.getVersion().equalsIgnoreCase("Final"))
					grantStatus.setFinalApproved(true);
				else if(approval.getVersion().equalsIgnoreCase("Final Year2"))
					grantStatus.setFinalYr2Approved(true);
				else if(approval.getVersion().equalsIgnoreCase("Final Year3"))
					grantStatus.setFinalYr3Approved(true);
				else if(approval.getVersion().equalsIgnoreCase("Final Year3"))
					grantStatus.setFinalYr3Approved(true);
			}
			else if(approval.getApprovalType().equalsIgnoreCase("Denied"))
				grantStatus.setDenied(true);
			else if(approval.getApprovalType().equalsIgnoreCase("Declined"))
				grantStatus.setDeclined(true);
	    }
		
		
		//determine whether the application version may be submitted or not
	      if( (grantStatus.getInitialSubmitted() && grantStatus.getLockApp()) || 
	        (grantStatus.getInitialApproved() && grantStatus.getInitialApproved()) ||
	        (grantStatus.getDenied()) )	        
	          grantStatus.setAllowSubmitInitial(false);	        
	        else
	          grantStatus.setAllowSubmitInitial(true);
	                
	      
	      if( (grantStatus.getFinalSubmitted() && grantStatus.getLockApp()) ||
	        ( grantStatus.getFinalSubmitted() && grantStatus.getFinalApproved()) ||
	        ( grantStatus.getDenied())  ||
	        ( !grantStatus.getInitialSubmitted() && !grantStatus.getApplicationDate().isFinalDatesAcceptible())){
	          grantStatus.setAllowSubmitFinal(false);
	        }
	        else
	          grantStatus.setAllowSubmitFinal(true);  
	      
	       
	      //for literacy year 2
	      if(  (grantStatus.getFinalYr2Submitted() && grantStatus.getLockApp())  ||
	           (grantStatus.getFinalYr2Submitted() && grantStatus.getFinalYr2Approved())  ||
	           ( grantStatus.getDenied())   ||
	           ( !grantStatus.getInitialSubmitted() && !grantStatus.getApplicationDate().isFinalDatesAcceptible()))
	        
	               grantStatus.setAllowSubmitFinalYr2(false);	        
	        else
	            grantStatus.setAllowSubmitFinalYr2(true);
	      
	     	                               
	     //for literacy year 3   
	     if(  (grantStatus.getFinalYr3Submitted() && grantStatus.getLockApp())  ||
	          (grantStatus.getFinalYr3Submitted() && grantStatus.getFinalYr3Approved())  ||
	          ( grantStatus.getDenied())   ||
	          ( !grantStatus.getInitialSubmitted() && !grantStatus.getApplicationDate().isFinalDatesAcceptible()))
	       
	              grantStatus.setAllowSubmitFinalYr3(false);
	       else
	           grantStatus.setAllowSubmitFinalYr3(true);
	    
	     	      
	      //all fields must be disabled once app is submitted and waiting for
	      //administrative action (ie. either an approval or correction requested)
	      if(grantStatus.getInitialSubmitted() && grantStatus.getLockApp())
	        grantStatus.setPendingReview(true);
	      else
	        grantStatus.setPendingReview(false);
	      
	      
	      if(grantStatus.getFinalSubmitted() && grantStatus.getLockApp())
	        grantStatus.setPendingFinalReview(true);
	      else
	        grantStatus.setPendingFinalReview(false);
	      
	      
	      
		}catch(Exception e){
			System.err.println("GrantService.getGrantStatus() "+e.getMessage()); 
		}
		
		return grantStatus;
	}
	
}
