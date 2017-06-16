/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SaveNarrativeAction.java
 * Creation/Modification History  :
 *
 * SH   1/14/08      Created
 *
 * Description
 * Action is used to save narratives and final rpt for SA/CO/DI/LG/CN, cnSysRev, CnAdmin. 
 * Checks for timeout and user permissions first.  Gets new narrative, instructions,
 * and routes back to correct page. 
 *****************************************************************************/
package discretionary;
import clobpackage.ClobBean;

import construction.ConstructionDBbean;
import construction.SystemAssignBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveNarrativeAction extends Action
{

  private DBHandler dbh = new DBHandler();
  private NarrativeDBbean ndb = new NarrativeDBbean();


  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
  {    
       HttpSession sess = request.getSession();       
       String finalTarget="";
       
       //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())
          return mapping.findForward("timeout");
          
    try{   
         //check for user permissions
         DescriptionBean pdb = (DescriptionBean) form;                
         UserBean lduser = (UserBean) sess.getAttribute("lduser");
         String permiss = "read";
         String module = pdb.getModule(); 
        
         if(module.equals("di"))
           permiss = lduser.getPrgdi();
         else if(module.equals("co"))
           permiss = lduser.getPrgco();
         else if(module.equals("sa"))
           permiss = lduser.getPrgsa();
         else if(module.equals("lg"))
           permiss = lduser.getPrglg();
        else if(module.equals("cn"))
          permiss = lduser.getPrgconstruction();
        else if(module.equals("cnreview")){//library system reviewer for construction
          if(lduser.isReviewconstruction())//check for pls reviewer attribute
              permiss ="edit";
        }
        else if(module.equals("admncn")){//cn admin
            if(lduser.getAdmconstruction()!=null 
                && lduser.getAdmconstruction().equals("approve"))
                permiss="edit";
        }
        else if(module.equals("staid")){
           permiss = lduser.getPrgNycStateaid();
        }
          
         if(permiss.equals("read")){
            request.setAttribute("errormsg", "User does not have access to update the application");
            return mapping.findForward("authorize");
         }
          
          
          
         String grantnum = (String) sess.getAttribute("grantid");     
         long grantid = Long.parseLong(grantnum);
                  
        //for construction; validate abstract narr size
        if( (module.equals("cn") || module.equals("cnreview") || module.equals("admncn"))
                && pdb.getNarrTypeID()==95)
        {            
            ActionErrors ae =null;
            ae = pdb.validateSummary(mapping, request, 150);
            if(ae !=null && (ae.size() > 0)){
              request.setAttribute(Globals.ERROR_KEY, ae); 
              return (mapping.findForward("narrative"));
            }       
        }
        
        
         //check if user entered text
         if(pdb.getNarrative()!=null && !pdb.getNarrative().equals(""))
         {
           if(pdb.getId()==0)//insert record first
              ndb.insertNarrativeRecord(lduser, grantid, pdb.getNarrTypeID());
            
            //update record 
            ndb.updateNarrative(lduser, grantid, pdb);
         }
         
         AppStatusBean asb = dbh.getApplicationStatus(grantid);
         request.setAttribute("appStatus", asb);
        
        //get new/updated version of the project narrative
        ClobBean cb = new ClobBean();
        cb.setGrantid(grantid);
        cb.openOracleConnection();      
        cb.getClobNarrative(pdb.getNarrTypeID()); 
        cb.closeOracleConnection();             
               
        DescriptionBean db = new DescriptionBean();
        db.setNarrative(cb.getData());
        db.setId(cb.getNarrID());
        db.setNarrTypeID(pdb.getNarrTypeID());
        if(module.equals("di"))
          db.setNarrativeDescr(ndb.getNarrativeInstructions(pdb.getNarrTypeID()));
        else if(module.equals("co"))
          db.setNarrativeDescr(ndb.getNarrativeInstructionsCo(pdb.getNarrTypeID()));
        else if(module.equals("sa"))
          db.setNarrativeDescr(ndb.getNarrativeInstructionsSa(pdb.getNarrTypeID())); 
        else if(module.equals("lg"))
          db.setNarrativeDescr(ndb.getNarrativeInstructionsLg(pdb.getNarrTypeID(), asb.getFycode()));
        else if(module.equals("cn") || module.equals("admncn"))//cn apcnt and admin
          db.setNarrativeDescr(ndb.getNarrativeInstructionsCn(pdb.getNarrTypeID()));
        else if(module.equals("staid"))
          db.setNarrativeDescr(ndb.getNarrativeInstructionsStaid(pdb.getNarrTypeID())); 
        else if(module.equals("cnreview")){
            db.setNarrativeDescr(ndb.getNarrativeInstructionsCn(pdb.getNarrTypeID()));
            //get all info about system assignment
            ConstructionDBbean cdb = new ConstructionDBbean();
            SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
            request.setAttribute("assignBean", sab);
        }
        
          
        db.setNarrativeTitle(ndb.getNarrativeTitle(pdb.getNarrTypeID()));
        
        //co/di/lg uses same jsp for init/final narr - determine whether init/final needs locking
        db.setLockNarrative(ndb.determineNarrativeLock(asb, pdb.getNarrTypeID()));
               
        request.setAttribute("projNarrative", db);
                        
        if(pdb.getNarrTypeID()==2)
          finalTarget="finalreport";
        else if(module.equals("co") && (pdb.getNarrTypeID()==89 || pdb.getNarrTypeID()==90))
          finalTarget="finalreport";//coord final report has 3 narr types
        else if( (module.equals("sa") || module.equals("staid"))&& pdb.getNarrTypeID()==1)
        {
          finalTarget="coversheet";
          
          OfficerDBbean odb = new OfficerDBbean();
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean);           
        }
        else
          finalTarget="narrative";
              
    }catch(Exception e){System.out.println("error SaveNarrativeAction "+e.getMessage().toString());}
        
    return mapping.findForward(finalTarget);        
  }
}