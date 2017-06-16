/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SaveCnApplicationAction.java
 *
 * Description
 * This action saves all data on the construction application form (coversheet).
 * used for cn apcnt, sysreviewer, admin with diff save-actions in struts-config-construct
 *****************************************************************************/
package construction;

import clobpackage.ClobBean;

import javax.servlet.http.*;

import mypackage.AppStatusBean;
import mypackage.BudgetDBHandler;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DistrictBean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;

import mypackage.UserBean;

import org.apache.struts.action.*;

public class SaveCnApplicationAction extends Action
{

    private DBHandler dbh = new DBHandler();      
    private OfficerDBbean odb = new OfficerDBbean();
    private ConstructionDBbean cdb = new ConstructionDBbean();
       
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception{    
      
      int outcome = 0;
      HttpSession sess = request.getSession();
                
      try{       
        boolean userID = (sess.getAttribute("lduser") != null); 
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
         //this causes errors when user copy/pastes coversheet url into new tab; and tries to go back and work on old tab 
        //String grantnum = (String) sess.getAttribute("grantid");
        
        CnApplicationBean cb = (CnApplicationBean) form;//get info from applicationform
        long grantid = cb.getGrantId();//get grantid from form; set to session in case user opened another tab
        sess.setAttribute("grantid", String.valueOf(grantid));
          
                    
        //check for user permissions
       UserBean lduser = (UserBean) sess.getAttribute("lduser");
       String permiss = "read";
       
       if(cb.getModule()!=null &&  !cb.getModule().equals("")){
           if(cb.getModule().equals("cn"))
                permiss = lduser.getPrgconstruction();
           else if(cb.getModule().equals("cnreview")){
                if(lduser.isReviewconstruction())
                    permiss = "submit";
           }
           else if(cb.getModule().equals("admncn")){
                if(lduser.getAdmconstruction()!=null && 
                    lduser.getAdmconstruction().equals("approve"))
                    permiss = "submit";
           }
       }
       if(permiss.equals("read")){
          request.setAttribute("errormsg", "User does not have access to update the application");
          return mapping.findForward("authorize");
       }
        
        //insert or update the project manager
        if(cb.getManagerId()==0)
          outcome = odb.insertCnProjectManager(grantid, lduser, cb);
        else
          outcome = odb.updateCnProjectManager(lduser, cb);
          
        
        //new for 2016-17 per LWebb 2/24/16
        //insert or update the library director into GRANT_STAFFS table
        if(cb.getDirectorId()==0)
            outcome = odb.insertCnDirector(grantid, lduser, cb);
        else
            outcome = odb.updateCnDirector(lduser, cb);
          
        //insert or update the buildings table
        if(cb.getBuildingId()==0){
            long newBuildingId = cdb.insertBuilding(lduser, cb);     
            //get the newly inserted buildingId
            cb.setBuildingId(newBuildingId);
            //insert address
            outcome = cdb.insertBuildingAddress(lduser, cb);            
        }
        else{
            //update building and address records
            outcome = cdb.updateBuildingAndAddress(lduser, cb);
        }
          
        //insert or update grant_buildings table
        if(cb.getGrantBuildingId()==0){
            long grantBuildingId = cdb.insertGrantBuilding(lduser, grantid, cb);
            //need new buildingGrantId for inserting building_projects table
            cb.setGrantBuildingId(grantBuildingId);
        }
        else
            outcome = cdb.updateGrantBuilding(lduser, cb);
        
        //insert/delete the building_project types
        cdb.handleSaveBuildingProjectTypes(cb, grantid, lduser);
        
        //update the project title in grants table
        if(cb.getProjectTitle()!=null && !cb.getProjectTitle().equals(""))
            outcome = dbh.updateProjNameReligiousAffil(lduser, grantid, cb.getProjectTitle(), false);
          
        //update shpo exemtpion in grants table
        outcome = cdb.updateGroundDisturbanceFlag(cb.isGroundDisturb(), grantid, lduser);
                  
                  
        //insert or update building_funds table with cost and reqcost
        cdb.handleSaveCostAmounts(cb, lduser);       
          
        //new for 14-15; insert/update the shpo exempt narrative
        if(cb.getShpoExemption()!=null && !cb.getShpoExemption().equals(""))
        {
          NarrativeDBbean ndb = new NarrativeDBbean();
          if(cb.getShpoExemptionId()==0)//insert record first
             ndb.insertNarrativeRecord(lduser, grantid, cb.getShpoExemptionNarrTypeId());
           
           //update record 
           ndb.updateNarrative(lduser, grantid, cb.getShpoExemptionNarrTypeId(), cb.getShpoExemption());
        }
        
/////////////////////////////////refresh all data and redisplay page
         GrantBean gb=dbh.getRecordBean(grantid); 
         sess.setAttribute("thisGrant", gb);  
         
         OfficerDBbean odb = new OfficerDBbean();
         OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
         request.setAttribute("libDirectorBean", libdirectorBean);   
         
         //get the senate, congress districts, etc.
         DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
         request.setAttribute("distBean", db);          
          
        //get shpo flag
        AppStatusBean appbean = dbh.getGrantStatusYn(grantid);
          
          //get the shpo exemption narrative
          ClobBean clobb = new ClobBean();
          clobb.setGrantid(grantid);
          clobb.openOracleConnection();      
          clobb.getClobNarrative(117); 
          clobb.closeOracleConnection();        
        
          CnApplicationBean ab = cdb.getBuildingGrantInfo(gb.getGrantid());
          ab.setGrantId(grantid);
          //set shpo exempt narr fields
          ab.setShpoExemption(clobb.getData());
          ab.setShpoExemptionId(clobb.getNarrID());
          ab.setShpoExemptionNarrTypeId(117);
          ab.setGroundDisturb(appbean.isGroundDisturb());
          
          //set all other fields
          ab.setProjectTitle(gb.getTitle());
          ab.setGrantId(grantid);
          ab.setInstId(gb.getInstID());
          ab.setFycode(gb.getFycode());
          ab = cdb.determineCostAmts(ab, grantid);
          ab= odb.getCnProjectManager(ab);
          ab= odb.getCnDirector(ab);   
          ab= cdb.getBuildingProjectTypes(ab);//building_projects table
          request.setAttribute("applicationFormBean", ab);   
          
      }catch(Exception e){System.out.println("error SaveCnApplicationAction "
                            +e.getMessage().toString());}
   
      return(mapping.findForward("success"));  
  }
}



