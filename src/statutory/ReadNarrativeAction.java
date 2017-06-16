/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReadNarrativeAction.java
 * Creation/Modification History  :
 *
 * SH   12/24/08      Created
 *
 * Description
 * Contains dispatch action get narrative from db, and display with instructions
 * for that narrative.  Used for sa/co/di/lit admin and participant view -uses param module
 * to distinguish which tiles page to call.
 * action used to get narrative for sa/co/di/lg/cn applicant and cn reviewer for editing.
 *****************************************************************************/
package statutory;
import clobpackage.ClobBean;

import construction.ConstructionDBbean;
import construction.SystemAssignBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ReadNarrativeAction extends DispatchAction
{
  private NarrativeDBbean ndb = new NarrativeDBbean();   
  private DBHandler dbh = new DBHandler();
  
  
  /**
   * This method used to get selected narrative from db for admin or participating
   * institution read only view. Used for sa/co/di/lit/lg with diff action-mappings.
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
  public ActionForward readNarr(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      DescriptionBean pdb = new DescriptionBean();      
      String module = null;      
      
      try{        
        String grantid = (String) sess.getAttribute("grantid");
        String narrType = request.getParameter("narrType");
        int narrTypeInt = Integer.parseInt(narrType);
        String program = request.getParameter("p");
        
        //get the narrative from the db and set to descrBean
        ClobBean cb = new ClobBean();
        cb.setGrantid(Long.parseLong(grantid));
        cb.openOracleConnection();      
        cb.getClobNarrative(narrTypeInt); 
        cb.closeOracleConnection();
        
        pdb.setNarrative(cb.getData());
        pdb.setId(cb.getNarrID());
        pdb.setNarrTypeID(narrTypeInt);  
        pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrTypeInt));
        
        //get narr instructions for particular program
        if(program.equals("lg")){
          GrantBean gb = dbh.getRecordBean(Long.parseLong(grantid));
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLg(narrTypeInt, gb.getFycode()));
        }
        else if(program.equals("di"))
          pdb.setNarrativeDescr(ndb.getNarrativeInstructions(narrTypeInt)); 
        else if(program.equals("sa"))
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsSa(narrTypeInt));             
        else if(program.equals("co"))
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCo(narrTypeInt));
        else if(program.equals("lit")){
          GrantBean gb = dbh.getRecordBean(Long.parseLong(grantid));
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, gb.getFccode(), gb.getFycode()));
        }
        request.setAttribute("projNarrative", pdb);    
        
        module = request.getParameter("m");
        
      }catch(Exception e){
        System.out.println("error ReadNarrativeAction "+e.getMessage().toString());
      }
      return mapping.findForward(module);
    }
        
    
  /**
   * This method gets selected narrative from db and displays to applicant for
   * editing. Used for sa/co/di/lg/cn and cnReview and cnAdmin
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
    public ActionForward narr(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      DescriptionBean pdb = new DescriptionBean();
      String finalTarget="applicant";
      try{          
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          String narrTypeID = request.getParameter("id");
          int narrTypeInt = Integer.parseInt(narrTypeID);
          String module = request.getParameter("m");
          
          AppStatusBean ab = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", ab);        
           
          //get the narrative from the db and set to descrBean
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(narrTypeInt); 
          cb.closeOracleConnection();
          
          pdb.setNarrative(cb.getData());
          pdb.setId(cb.getNarrID());
          pdb.setNarrTypeID(narrTypeInt);  
          pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrTypeInt));
          
          //co/di/lg uses same jsp for init/final narr - determine whether init/final needs locking
          pdb.setLockNarrative(ndb.determineNarrativeLock(ab, narrTypeInt));
                  
          //get narrative instruct for program
          if(module.equals("di"))
            pdb.setNarrativeDescr(ndb.getNarrativeInstructions(narrTypeInt));     
          else if(module.equals("sa"))
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsSa(narrTypeInt)); 
          else if(module.equals("co")){
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCo(narrTypeInt)); 
            //coordinated narrative and finalnarr use same jsp, diff action mappings
            if(narrTypeInt ==2 || narrTypeInt ==89 || narrTypeInt==90)
                finalTarget+="final";
          }
          else if(module.equals("lg"))
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLg(narrTypeInt, ab.getFycode()));
          else if(module.equals("cn")){ 
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCn(narrTypeInt));
            //abstract narr on diff jsp b/c character limit
            if(narrTypeInt ==95)
                finalTarget="abstractnarr";
          }
          else if(module.equals("cnr")){
              pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCn(narrTypeInt));
              //get all info about system assignment
              ConstructionDBbean cdb = new ConstructionDBbean();
              SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
              request.setAttribute("assignBean", sab);
              
              //abstract narr on diff jsp b/c character limit
              if(narrTypeInt ==95)
                  finalTarget="abstractnarr";
          }
          else if(module.equals("admncn")){
              pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCn(narrTypeInt));
                           
              //abstract narr on diff jsp b/c character limit
              if(narrTypeInt ==95)
                  finalTarget="abstractnarr";
          }
          else if(module.equals("staid")) {
              pdb.setNarrativeDescr(ndb.getNarrativeInstructionsStaid(narrTypeInt)); 
          }
          
          request.setAttribute("projNarrative", pdb);    
                            
      }catch(Exception e){
        System.out.println("error ReadNarrativeAction "+e.getMessage().toString());
      }
      return mapping.findForward(finalTarget);        
    }
    
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null);
      if (!userID && sess.isNew())
        timeout = true;
           
      return timeout;
    }
}