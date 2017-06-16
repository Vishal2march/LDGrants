/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  DiCoversheetAction.java
 * Creation/Modification History  :
 *
 * SH   1/11/08      Created
 *
 * Description
 * This action is used to save info from the DI coversheet. Saves the pm, project title,
 * and summary descrip after the struts validator checks for required fields. Then
 * redisplays all info plus the district and library director info.
 *****************************************************************************/
package discretionary;
import clobpackage.ClobBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DistrictBean;
import mypackage.EligibilityDbBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DiCoversheetAction extends Action
{
  private DBHandler dbh = new DBHandler();        
  private OfficerDBbean odb = new OfficerDBbean();
  private NarrativeDBbean ndb = new NarrativeDBbean();
        
public ActionForward execute(ActionMapping mapping, ActionForm form,
  HttpServletRequest request, HttpServletResponse response) throws Exception
  {
      int outcome = 0;
      HttpSession sess = request.getSession();
                
      try{       
        boolean userID = (sess.getAttribute("lduser") != null); 
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
          
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        CoversheetBean cb = (CoversheetBean) form;//get info user entered on coversht
                      
        //check for user permissions
       UserBean lduser = (UserBean) sess.getAttribute("lduser");
       if(lduser.getPrgdi().equals("read"))
       {
          request.setAttribute("errormsg", "User does not have access to update the application");
          return mapping.findForward("authorize");
       }
        
        //insert or update the pm
        if(cb.getPmId()==0)
          outcome = odb.insertProjectManager(grantid, lduser, cb);
        else
          outcome = odb.updateProjectManager(lduser, cb);
       

        if(cb.getProjectTitle()!=null && !cb.getProjectTitle().equals(""))
          outcome = dbh.updateProjNameReligiousAffil(lduser, grantid, cb.getProjectTitle(), cb.isReligious());
                
        
        //insert or update the summary description
       if(cb.getSummaryDesc()!=null && !cb.getSummaryDesc().equals(""))
       {
         if(cb.getNarrId()==0)//insert record first
            ndb.insertNarrativeRecord(lduser, grantid, cb.getNarrTypeId());
          
          //update record 
          ndb.updateNarrative(cb, lduser, grantid);
       }
       
       //insert or update the institutional eligibility
       EligibilityDbBean edb = new EligibilityDbBean();
       edb.handleEligiblityCheckboxes(cb, request);        
        
        //get all info and redisplay     
         AppStatusBean as = dbh.getApplicationStatus(grantid);
         request.setAttribute("appStatus", as);
         
        ClobBean clobb = new ClobBean();
        clobb.setGrantid(grantid);
        clobb.openOracleConnection();      
        clobb.getClobNarrative(1); 
        clobb.closeOracleConnection();     
        
        CoversheetBean csb = new CoversheetBean();
        csb.setSummaryDesc(clobb.getData());
        csb.setNarrId(clobb.getNarrID());
        csb.setNarrTypeId(1);
        csb.setGrantid(grantid);        
        csb = dbh.getProjectNameReligAffil(csb);  
        csb.setReligious(cb.isReligious());
        csb= odb.getProjectManager(csb);     

        csb = edb.getInstEligibilityForCoversheet(csb, as.getFccode());    
        request.setAttribute("coversheetBean", csb);           
        
        //get the senate, congress districts, etc.
        UserBean ub = (UserBean) sess.getAttribute("lduser");
        DistrictBean db = dbh.getDistrictInfo(ub.getInstid());
        request.setAttribute("distBean", db);
        
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
        request.setAttribute("libDirectorBean", libdirectorBean); 
                 
    }catch(Exception e){System.out.println("error DiCoversheet "+e.getMessage().toString());}
        
    return mapping.findForward("success");
  }
}