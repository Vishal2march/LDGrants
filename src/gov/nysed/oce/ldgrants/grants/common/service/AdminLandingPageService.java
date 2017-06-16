package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.dao.ApprovalDao;
import gov.nysed.oce.ldgrants.grants.common.dao.GrantSubmissionDao;
import gov.nysed.oce.ldgrants.grants.common.domain.AdminLandingPage;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.enumeration.SubmitApproveVersion;
import mypackage.ReviewerAssignBean;

public class AdminLandingPageService {

	
	public AdminLandingPage buildAdminHomePage(Long fyCode, Long fcCode){
		
		AdminLandingPage landingPage = new AdminLandingPage();
		
		try{
			
			List<Grant> initialSubmit=new ArrayList<Grant>(), yr1Submit=new ArrayList<Grant>(), 
					yr2Submit=new ArrayList<Grant>(), yr3Submit =new ArrayList<Grant>();
						
			
			//get all submission records for this fund/FY
			GrantSubmissionDao gsDao = new GrantSubmissionDao();
			List<Grant> allSubmitted = gsDao.searchByFundCodeFyCode(fcCode, fyCode);
			//System.out.println("all submit size "+allSubmitted.size());
			
			//loop on submissions; get initial/finalYr1/finalYr2/etc in separate lists			
			for(Grant g: allSubmitted){
				String version = g.getGrantSubmission().getVersion();
								
				if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.Initial)
					initialSubmit.add(g);
				else if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.Final)
					yr1Submit.add(g);
				else if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.FinalYear2)
					yr2Submit.add(g);
				else if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.FinalYear3)
					yr3Submit.add(g);
			}
			
						
			initialSubmit = filterListDistinctGrant(initialSubmit);
			yr1Submit = filterListDistinctGrant(yr1Submit);
			yr2Submit = filterListDistinctGrant(yr2Submit);
			yr3Submit = filterListDistinctGrant(yr3Submit);
			
			/*System.out.println("initial submit "+initialSubmit.size());
			System.out.println("1 submit "+yr1Submit.size());
			System.out.println("2 submit  "+yr2Submit.size());
			System.out.println("3 submit  "+yr3Submit.size());           */
			
			//assign each submit list to main object landing page
			landingPage.setSubmittedList(initialSubmit);
			landingPage.setYear1Submitted(yr1Submit);
			landingPage.setYear2Submitted(yr2Submit);
			landingPage.setYear3Submitted(yr3Submit);
			
			
			
			////////////APPROVALS   //////////////////////////////////////
			
			List<Grant> initialApprove=new ArrayList<Grant>(), yr1Approve=new ArrayList<Grant>(), 
					yr2Approve=new ArrayList<Grant>(), yr3Approve =new ArrayList<Grant>();
			
			ApprovalDao aDao = new ApprovalDao();
			List<Grant> allApprove = aDao.searchByFundCodeFyCode(fcCode, fyCode);
			//System.out.println("all appr size "+allApprove.size());
			
			//loop on all approvals; split by version
			for(Grant g: allApprove){
				String version = g.getApproval().getVersion();
								
				if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.Initial)
					initialApprove.add(g);
				else if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.Final)
					yr1Approve.add(g);
				else if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.FinalYear2)
					yr2Approve.add(g);
				else if(SubmitApproveVersion.searchByVersionString(version)==SubmitApproveVersion.FinalYear3)
					yr3Approve.add(g);
			}
			
						
			/*System.out.println("initial appr "+initialApprove.size());
			System.out.println("1 appr "+yr1Approve.size());
			System.out.println("2 appr  "+yr2Approve.size());
			System.out.println("3 appr  "+yr3Approve.size());           */
			
			//assign each approval list to main object landing page
			landingPage.setApprovedList(initialApprove);
			landingPage.setYear1Approved(yr1Approve);
			landingPage.setYear2Approved(yr2Approve);
			landingPage.setYear3Approved(yr3Approve);
			
			
		}catch(Exception e){
			System.err.println("AdminLandingPageService.buildAdminHomePage() "+e.getMessage());
		}
		return landingPage;
	}
	
	
	
	/**
	 * List contains multiple Grant rows; for each GRANT_SUBMISSION. Filter list so each GRANT listed 
	 * only 1x; with most recent GRANT_SUBMISSION date.
	 * @param originalList
	 * @return
	 */
	public List<Grant> filterListDistinctGrant(List<Grant> originalList){
		
		List<Grant> newList = new ArrayList<Grant>();
		try{
						
			  //loop on all rows in original list; list should be sorted by grant id
		      for(int i=0; i<originalList.size(); i++)
		      {
		          //get grant from list
		          Grant g = originalList.get(i);
		         		                    
		          
		          //if first record from list
		          if(i==0){           
		            		                                         
		            //add to new list
		        	newList.add(g);
		          }		          
		          else{
		        	  
		        	  boolean grantFound = false;
		              //search new list for this grant id
		        	  for(int j=0; j<newList.size(); j++){
		        		  
		        		  Grant newg = newList.get(j);		        		  
		        		  
		        		  //this grant id already in list
		        		  if(newg.getId().longValue() ==  g.getId().longValue()){
		        			  
		        			  grantFound = true;		        			  
		        			  
		        			  //check submission dates; want most recent date
		        			  if(g.getGrantSubmission().getDateSubmitted().after(newg.getGrantSubmission().getDateSubmitted())){
		        				  		        				  
		        				  //replace row in new list with this most recent submission row
		        				  newList.remove(j);
		        				  newList.add(g);
		        			  }
		        		  }		        		  
		        	  }//end inner for loop
		        	  
		        	  if(grantFound == false){
	        			  //grant id not in list yet; add it
	        			  newList.add(g);
	        		  }
		          }
		          	          		          
		      }//end for loop
						
			
		}catch(Exception e){
			System.err.println("AdminLandingPageService.filterListDistinctGrant() "+e.getMessage());
		}
		return newList;
		
	}
}
