package gov.nysed.oce.ldgrants.shared;

import java.util.List;
import java.util.Map;


import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import gov.nysed.oce.ldgrants.budget.domain.Fs10Record;
import gov.nysed.oce.ldgrants.grants.common.domain.FormVersionBuilder;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
import gov.nysed.oce.ldgrants.user.domain.User;
import mypackage.AppStatusBean;
import mypackage.GrantBean;
import mypackage.UserBean;


public class LDGrantSessionManager extends ActionSupport implements SessionAware {

	public LDGrantSession ldSession = new LDGrantSession();
	
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		
	}
	
		
	private static final String LD_USER = "lduser";//old UserBean object
	private static final String USER = "user";//new User object
	private static final String FORM_VERSION = "curFormVersion"; 
	private static final String GRANT_BEAN = "grantBean";//old GrantBean object
	private static final String ALL_GRANTS = "allGrants";
	private static final String GRANT_ID = "grantid";//old grantId string
	private static final String GRANT = "grant";//new Grant object
	private static final String BREADCRUMB = "breadcrumbs";//for breadcrumb navigation links
	private static final String INSTITUTION = "institution";
	private static final String FORM_TYPE_ID = "curFormTypeId"; // current Form Type Id 
	
	
	public LDGrantSessionManager() {}
	
	
	

	public class LDGrantSession  {
		
		public FormVersionBuilder getFormVersion() {
			Map session = ActionContext.getContext().getSession();
			return (FormVersionBuilder) session.get(FORM_VERSION);
		}

		public void setFormVersion(FormVersionBuilder formVersion) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(FORM_VERSION, formVersion);
		}
		
		public void clearFormVersion() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(FORM_VERSION, null);
		}
		
		
		
		
		
		/**
		 * This returns new "User" object from session. 
		 * @return
		 */
		public User getUser() {			
				Map<String, Object> session = ActionContext.getContext().getSession();			
				return (User) session.get(USER);		
		}

		public void setUser(User user) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(USER, user);
		}
		
		public void clearUser() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(USER, null);
		}
		
				
		
	
		/**
		 * This returns old "UserBean" object from session. New code should be using
		 * new "User" object instead.
		 * @return
		 */
		public UserBean getLdUser() {
			Map<String, Object> session = ActionContext.getContext().getSession();	
			return (UserBean) session.get(LD_USER);
		}
		
		public void setLdUser(UserBean userBean) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(LD_USER, userBean);
		}
				
		public void clearLdUser() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(LD_USER, null);
		}
		
		
		
		
		
		
		public Grant getGrant() {
			Map session = ActionContext.getContext().getSession();
			return (Grant) session.get(GRANT);
		}

		public void setGrant(Grant grant) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(GRANT, grant);
		}
		
		public void clearGrant() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(GRANT, null);
		}
		
		
		
		
		
		
		public String getGrantId() {
			Map<String, Object> session = ActionContext.getContext().getSession();			
			return (String) session.get(GRANT_ID);
		}

		public void setGrantId(String grantId) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(GRANT_ID, grantId);
		}
		
		public void clearGrantId() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(GRANT_ID, null);
		}
		
		
		
		
		public GrantBean getGrantBean() {
			Map session = ActionContext.getContext().getSession();
			return (GrantBean) session.get(GRANT_BEAN);
		}

		public void setGrantBean(GrantBean grantBean) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(GRANT_BEAN, grantBean);
		}
		
		public void clearGrantBean() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(GRANT_BEAN, null);
		}
		
		
		
		/** Change AllGrants object for a list of MyPackage.GrantBean objects to a List of Grant objects **/
		public List<Grant> getAllGrants() {
			Map session = ActionContext.getContext().getSession();
			return (List<Grant>) session.get(ALL_GRANTS);
		}

		public void setAllGrants(List<Grant> allGrants) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(ALL_GRANTS, allGrants);
		}
		
		public void clearAllGrants() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(ALL_GRANTS, null);
		}
		
		
		
		

		public Institution getInstituiton() {
			Map session = ActionContext.getContext().getSession();
			return (Institution) session.get(INSTITUTION);
		}

		public void setInstitution(Institution institution) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(INSTITUTION, institution);
		}
		
		public void clearInstitution() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(INSTITUTION, null);
		}
		

			
		
		
		public List<Breadcrumb> getBreadcrumbs() {
			Map session = ActionContext.getContext().getSession();
			return (List<Breadcrumb>) session.get(BREADCRUMB);
		}

		public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(BREADCRUMB, breadcrumbs);
		}
		
		public void clearBreadcrumbs() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(BREADCRUMB, null);
		}
		
		public Long getFormTypeId() {
			Map<String, Object> session = ActionContext.getContext().getSession();			
			return (Long) session.get(FORM_TYPE_ID);
		}

		public void setFormTypeID(Long formTypeId) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put(FORM_TYPE_ID, formTypeId);
		}
		
		public void clearFormTypeID() {
			Map<String, ?> session = ActionContext.getContext().getSession();
			session.put(FORM_TYPE_ID, null);
		}
		

}
		

}
