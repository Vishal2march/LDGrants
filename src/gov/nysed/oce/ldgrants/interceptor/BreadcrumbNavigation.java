package gov.nysed.oce.ldgrants.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import gov.nysed.oce.ldgrants.shared.Breadcrumb;

/**
 * This class will put breadcrumb page navigation into session, to be displayed on jsp as
 * bootstrap breadcrumb links back to home/checklist/etc.
 * @author shusak
 *
 */
public class BreadcrumbNavigation extends AbstractInterceptor{

	private static final String HTTP_REQUEST = null;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		System.out.println("inside intercept");
				
		
		//get any existing breadcrumbs from session
		 Map session = ActionContext.getContext().getSession();
		 List<Breadcrumb> breadcrumbs = (List<Breadcrumb>)session.get("breadcrumbs");
		 
		 //if no breadcrumbs in session
		 if(breadcrumbs==null || breadcrumbs.isEmpty()){
			 System.out.println("NO breadcrumbs");
			 breadcrumbs = new ArrayList<Breadcrumb>();
			 
			 //the ROOT is always the welcome page (Home)
			 Breadcrumb root = new Breadcrumb();
			 root.setDisplayName("Home");
			 root.setUrl("welcomePage.jsp");
			 //add to list
			 breadcrumbs.add(root);			 
		 }
		 
		 
		 //get the request that invoked this interceptor
		 ActionContext context = invocation.getInvocationContext();
		 HttpServletRequest request = ServletActionContext.getRequest();
		 
		 String uri = request.getRequestURI();
		 System.out.println(uri);
		 
		 
		 //TODO: parse out the ldgrants
		 
		 
		 //TODO: can only create breadcrumbs for certain actions - those that call new page. 
		 //actions that save, or create json, etc can't have breadcrumbs .....filter those out.
		 
		 
		 
		 //create new breadcrumb
		 Breadcrumb item = new Breadcrumb();
		 item.setDisplayName("New");
		 item.setUrl(uri);
		 breadcrumbs.add(item);
		 
		 
		 
		 //set new breadcrumbs to session
		 session.put("breadcrumbs", breadcrumbs);
		
		
		return invocation.invoke();
	}

	
	
}
