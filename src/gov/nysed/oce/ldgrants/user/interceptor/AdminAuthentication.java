package gov.nysed.oce.ldgrants.user.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import gov.nysed.oce.ldgrants.user.domain.User;

public class AdminAuthentication extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final String LDGRANT_USER = "user";
	private String url;
	
	public AdminAuthentication() {
	}

	public void init() {
	}

	public void destroy() {
		System.out.println("destroyed");
	}

	public String intercept(ActionInvocation invocation) throws Exception {

		// Does the user object exist, if not return to login
		Map session = ActionContext.getContext().getSession();
		User user = (User) session.get(LDGRANT_USER);
		
		 url = "/ldgrants";
		 
		if (user != null) {
			
			// IF a user object exists, has it been properly authenticated? If
			// not return to login
			if (user.getUserId() == null) {
				return "login";
			}
			//check if Admin
			if(user.isAdmin() == false){
				return "login";
			}

		} else {
			return "login";
		}

		return invocation.invoke();

	}

	public String getUrl() {
		return url;
	}
}
