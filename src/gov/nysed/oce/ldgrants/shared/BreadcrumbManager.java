package gov.nysed.oce.ldgrants.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class BreadcrumbManager extends LDGrantSessionManager{
	
	//order of navigation links for applicant
	public BreadcrumbPage[] applicantNavList = {BreadcrumbPage.HOME,
												 BreadcrumbPage.PROGRAM_HOME,
												 BreadcrumbPage.CHECKLIST,
												 BreadcrumbPage.FORM_CHECKLIST,
												 BreadcrumbPage.BUDGET_AMENDMENT_SUMMARY};
	
	
	
	
	public void createBreadcrumb(BreadcrumbPage page, String displayName){				
		try{			
			
			//if first possible crumb was clicked (PROGRAM_HOME); clear out any old crumbs
			if(page.equals(BreadcrumbPage.PROGRAM_HOME)){
				ldSession.clearBreadcrumbs();
			}
			
			//get any existing breadcrumbs from session
			 List<Breadcrumb> breadcrumbs = ldSession.getBreadcrumbs();
			 
			 
			 //if no breadcrumbs in session
			 if(breadcrumbs==null || breadcrumbs.isEmpty()){
				 breadcrumbs = new ArrayList<Breadcrumb>();
				 
				 //the ROOT/HOME is always the welcome page 
				 Breadcrumb root = new Breadcrumb();
				 root.setDisplayName("Home");
				 root.setUrl("welcomePage.jsp");
				 root.setBreadcrumbPage(BreadcrumbPage.HOME);
				 //add to list
				 breadcrumbs.add(root);			 
			 }
			 
			 
			 
			 //if breadcrumbs exist - check order in case links need to be removed (user backtracks)
			 boolean pageCrumbExists = false;
			 int pagePosition =0;			 
			 if(!breadcrumbs.isEmpty()){
				 
				 //does the selected link already exist?
				 for(int i=0; i<breadcrumbs.size(); i++){
									 
				 	 Breadcrumb b = breadcrumbs.get(i);
				 	 if(b.getBreadcrumbPage().equals(page)){
						 pageCrumbExists = true;
						 pagePosition = i;
				 	 }
				 }			 
			 }
				 
			 			 
			 
			//BREADCRUMB page already exists - are there any subsequent links that need to be removed?
			 if(pageCrumbExists){
				 				 
				 //if more links beyond the selected page
				 if(breadcrumbs.size()> pagePosition){
					 
					 //create temp list - copy all breadcrumbs up to the selected page
					 List<Breadcrumb> tempCrumbList = new ArrayList();
					 for(int i=0; i<=pagePosition; i++)
						 tempCrumbList.add(breadcrumbs.get(i));
					 					 
					 //set new list to breadcrumbs
					 breadcrumbs = new ArrayList();
					 breadcrumbs = tempCrumbList;
					 
					 //set new breadcrumbs to session
					 ldSession.setBreadcrumbs(breadcrumbs);
				 }
			 }		
			 //NO BREADCRUMB LINK - MUST CREATE - get url/create breadcrumb object			 
			 else if(!pageCrumbExists){
			 
				 //from request that called this method - get request url and any params sent
				 HttpServletRequest request = ServletActionContext.getRequest();						 
				 String uri = request.getRequestURI();
				 
				 String queryStr = request.getQueryString();
				 				 				 
				 //if params exist; add to url
				 if(queryStr!=null)
					 uri = uri + "?" + queryStr;
				 
				 //create new breadcrumb object
				 Breadcrumb item = new Breadcrumb();
				 item.setDisplayName(displayName);
				 item.setUrl(uri);
				 item.setBreadcrumbPage(page);
				 breadcrumbs.add(item);
				 
				 //set new breadcrumbs to session
				 ldSession.setBreadcrumbs(breadcrumbs);
			 }
		
			 			 		 			
		}catch(Exception e){
			System.err.println("error BreadcrumbManager.createBreadcrumb() " + e.toString());
		}
	}
	
	
	
	/**
	 * Get crumbs from session.  Look at PROGRAM_HOME crumb. Based on display name string; get the 
	 * program/fund.  
	 * @return
	 */
	public String determineProgramFromHomeCrumb(){
		String program = null, displayName = null;
		try{
			
			//get any breadcrumbs from session
			List<Breadcrumb> breadcrumbs = ldSession.getBreadcrumbs();
			 
			 
			 //if breadcrumbs exist
			 if(breadcrumbs!=null &&  !breadcrumbs.isEmpty()){
				 
				 //find the PROGRAM_HOME crumb
				 for(Breadcrumb b : breadcrumbs){
									 	 
				 	 if(b.getBreadcrumbPage().equals(BreadcrumbPage.PROGRAM_HOME))
						displayName = b.getDisplayName();
				 	 
				 }
				 
				 if(displayName!=null){
					 //parse out program
					 if(displayName.toLowerCase().indexOf("literacy") >-1)
						 program = "literacy";
				 }
				 
			 }
			
			
		}catch(Exception e){
			System.err.println("error BreadcrumbManager.determineProgramFromHomeCrumb() " + e.toString());
		}
		return program;
	}
	
	
	
}
