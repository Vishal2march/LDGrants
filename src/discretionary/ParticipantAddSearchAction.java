package discretionary;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.PartCollectionBean;
import mypackage.PartInstDBbean;
import mypackage.RatingBean;
import mypackage.ReviewerAssignDBbean;

import mypackage.UserBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ParticipantAddSearchAction extends DispatchAction {
    
    PartInstDBbean pdb = new PartInstDBbean();
    
    public ActionForward searchinst(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      String program ="";
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
      try{
          program = request.getParameter("p");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
                    
            //search sedref for inst by name
          String instName = request.getParameter("instName");              
          Vector results = pdb.searchForPartInst(instName);
          request.setAttribute("instResults", results);        
        
          getCurrentParticipants(grantid, program, request);        
      }
      catch(Exception e){
        System.out.println("error ParticipantAddSearchAction "+e.getMessage().toString());
      }          
      return mapping.findForward("participant"+program);          
    }
    
    
    
    public ActionForward addinst(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      String program ="";
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
      try{
          program = request.getParameter("p");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          UserBean userb = (UserBean) sess.getAttribute("lduser");
                    
          String id = request.getParameter("id"); //the inst_id       
          int outcome = pdb.insertPartLib(grantid, userb, Long.parseLong(id)); 
        
          getCurrentParticipants(grantid, program, request);        
      }
      catch(Exception e){
        System.out.println("error ParticipantAddSearchAction "+e.getMessage().toString());
      }          
      return mapping.findForward("participant"+program);          
    }
    
    
    public ActionForward deleteinst(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      String program ="";
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
      try{
          program = request.getParameter("p");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
                    
          String id = request.getParameter("id"); //the inst_id       
          int outcome = pdb.deletePartLib(Integer.parseInt(id));
        
          getCurrentParticipants(grantid, program, request);        
      }
      catch(Exception e){
        System.out.println("error ParticipantAddSearchAction "+e.getMessage().toString());
      }          
      return mapping.findForward("participant"+program);          
    }
    
    
    public void getCurrentParticipants(long grantid, String program, HttpServletRequest request){
        try{
            //get all part institutions for this grant
            Vector allParts = pdb.getPartInstAddressInfo(grantid);
            
            if(program.equals("lg")){
              PartCollectionBean pcb = pdb.getEligibleForParts(allParts);
              request.setAttribute("PartCollectionBean", pcb);
            }
            else
              request.setAttribute("allParts", allParts);
            
            DBHandler dbh = new DBHandler();
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb);
        }catch(Exception e){System.out.println("error ParticipantAddSearchAction "+e.getMessage().toString());}
    }
    
    
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT ParticipantAddSearchAction");
        timeout = true;
      }      
      return timeout;
    }
}
