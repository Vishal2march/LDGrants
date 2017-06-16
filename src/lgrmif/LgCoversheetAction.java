/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  LgCoversheetAction.java
 * Creation/Modification History  :
 * SH   1/13/09      Created
 *
 * Description
 * This action will validate, save, then redisplay all info from the lgrmif apcnt coversheet 
 *****************************************************************************/
package lgrmif;
import clobpackage.ClobBean;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.BudgetDBHandler;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DistrictBean;
import mypackage.DropDownListBean;
import mypackage.EligibilityDbBean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LgCoversheetAction extends Action
{
  private DBHandler dbh = new DBHandler();        
  private OfficerDBbean odb = new OfficerDBbean();
  private NarrativeDBbean ndb = new NarrativeDBbean();
        
public ActionForward execute(ActionMapping mapping, ActionForm form,
  HttpServletRequest request, HttpServletResponse response) throws Exception
  {
      int outcome = 0;
      HttpSession sess = request.getSession();
      EligibilityDbBean edb = new EligibilityDbBean();
                
      try{       
        boolean userID = (sess.getAttribute("lduser") != null); 
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
          
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);        
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb);  
        
        CoversheetBean cb = (CoversheetBean) form;//get info user entered on coversht
                      
        //check for user permissions
       UserBean lduser = (UserBean) sess.getAttribute("lduser");
       if(lduser.getPrglg().equals("read"))
       {
          request.setAttribute("errormsg", "User does not have access to update the application");
          return mapping.findForward("authorize");
       }
        
        //insert or update the pm
        if(cb.getPmId()==0)
          outcome = odb.insertProjectManager(grantid, lduser, cb);
        else
          outcome = odb.updateProjectManager(lduser, cb);
          
        //insert or update the RMO
        if(cb.getRmoId()==0)
          outcome = odb.insertAdditionalContact(grantid, lduser, cb, 1);
        else
          outcome = odb.updateAdditionalContact(lduser, cb);
          
        //insert or update into the Govt_infos table, also doris and proj category
        if(cb.getGovtId()==0)
          outcome = edb.insertArchivesGovtInfo(grantid, lduser, cb, gb.getFycode());
        else
          outcome = edb.updateArchivesGovtInfo(lduser, cb, gb.getFycode());
       
               
        //insert or update the summary description
       if(cb.getSummaryDesc()!=null && !cb.getSummaryDesc().equals(""))
       {
         if(cb.getNarrId()==0)//insert record first
            ndb.insertNarrativeRecord(lduser, grantid, cb.getNarrTypeId());
          
          //update record 
          ndb.updateNarrative(cb, lduser, grantid);
       }
       
       //insert or update the institutional eligibility       
       edb.handleLgEligiblityItems(cb, request);        
        
    //----------------------------------------------------------------------
        //get all info and redisplay on cover page     
        ClobBean clobb = new ClobBean();
        clobb.setGrantid(grantid);
        clobb.openOracleConnection();      
        clobb.getClobNarrative(1); 
        clobb.closeOracleConnection();     
        
        CoversheetBean csb = new CoversheetBean();
        csb.setFycode(gb.getFycode());
        csb.setSedrefinstid(gb.getInstID());
        csb.setSummaryDesc(clobb.getData());
        csb.setNarrId(clobb.getNarrID());
        csb.setNarrTypeId(1);
        csb.setGrantid(grantid);                   
        csb= odb.getProjectManager(csb);
        csb= odb.getAdditionalContact(csb, 1);     
        csb = edb.getArchivesGovtInfo(csb);
        csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode()); 
        
        BudgetDBHandler bdh = new BudgetDBHandler();
        csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));   
        request.setAttribute("coversheetBean", csb);                    
        
        //get the senate, congress districts, etc.
        DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
        request.setAttribute("distBean", db);
        
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
        request.setAttribute("libDirectorBean", libdirectorBean); 
        
        AppStatusBean as = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", as);
          
        request.setAttribute("anchorSection", "anchorSection");    
              
    }catch(Exception e){System.out.println("error LgCoversheetAction "+e.getMessage().toString());}
        
    return mapping.findForward("coversheet");
  }
}