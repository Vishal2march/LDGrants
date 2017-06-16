/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  LiSaveCoversheet.java
 * Creation/Modification History  :
 *
 * SH   Sept 2008     Created
 *
 * Description
 * This action saves all info for al/fl coversheet and routes back to appropriate
 * al/fl page.  Saves project title, project manager, additional contact info.
 *****************************************************************************/
package literacy;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.BudgetDBHandler;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DistrictBean;
import mypackage.GrantBean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LiSaveCoversheet extends Action
{
    
  private DBHandler dbh = new DBHandler();
  private OfficerDBbean odb = new OfficerDBbean();
    
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {        
      int outcome = 0;
      String finalTarget="";
      HttpSession sess = request.getSession();
        
          
      try{              
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
          
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        CoversheetBean cb = (CoversheetBean) form;//get info user entered on coversht
                      
        //check for user permissions
        UserBean lduser = (UserBean) sess.getAttribute("lduser");
        String permiss = "";
        if(cb.getModule().equals("fl"))
          permiss=lduser.getPrgfl();
        else
          permiss=lduser.getPrgal();
          
       if(permiss.equals("read"))
       {
          request.setAttribute("errormsg", "User does not have access to update the application");
          return mapping.findForward("authorize");
       }
            
      
        //insert or update the pm
        if(cb.getPmId()==0)
          outcome = odb.insertProjectManager(grantid, lduser, cb);
        else
          outcome = odb.updateProjectManager(lduser, cb);    
          
          //insert or update the additional contact          
          if(cb.getRmoId()==0)
            outcome = odb.insertAdditionalContact(grantid, lduser, cb, 3);
          else
            outcome = odb.updateAdditionalContact(lduser, cb);
                  
        //update title
        if(cb.getProjectTitle()!=null && !cb.getProjectTitle().equals(""))
          outcome = dbh.updateProjNameReligiousAffil(lduser, grantid, cb.getProjectTitle(), cb.isReligious());
                           
        CoversheetBean csb = new CoversheetBean();
        csb.setGrantid(grantid);        
        csb = dbh.getProjectNameReligAffil(csb);  
        csb= odb.getProjectManager(csb);        
        csb =odb.getAdditionalContact(csb, 3);
        request.setAttribute("coversheetBean", csb);           
        
        //get the senate, congress districts, etc.
        UserBean ub = (UserBean) sess.getAttribute("lduser");
        DistrictBean db = dbh.getDistrictInfo(ub.getInstid());
        request.setAttribute("distBean", db);
        
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
        request.setAttribute("libDirectorBean", libdirectorBean); 
        
        AppStatusBean as = dbh.getApplicationStatus(grantid);
        as.setGrantprogram(cb.getModule());
        request.setAttribute("appStatus", as);
        
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb);      
      
        BudgetDBHandler bdh = new BudgetDBHandler();
        if(as.getFycode()<14){
          //get total amount requested by fiscal year
          Vector allsums =bdh.getTotAmtReqLiFYPeriod(grantid, gb.getFycode(), gb.getFccode());
          request.setAttribute("allsums", allsums);
        }
        else{
          //get allocation amounts
          Vector allocs = bdh.getYearlyAllocationForInst(gb.getFycode(), gb.getFccode(), gb.getInstID());
          request.setAttribute("allAlloc", allocs);
        }
          
          
        if(cb.getModule().equals("fl"))
          finalTarget="fcoversheet";
        else
          finalTarget="acoversheet";
              
      }catch(Exception e){
        System.out.println("error LiSaveCoversheet "+e.getMessage().toString());
      }
      
      return mapping.findForward(finalTarget);
        
    }
    
}