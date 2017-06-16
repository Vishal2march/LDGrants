/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SavePartEligibleAction.java
 * Creation/Modification History  :
 * SH   6/17/09     Created
 *
 * Description
 * This action will add/update the LG participant eligiblity information into
 * GRANT_ELIGIBLE table. Validates that date entered/date valid for any
 * eligibilty=true.  Then retrieve all info and redisplay.
 *****************************************************************************/
package lgrmif;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.EligibilityDbBean;
import mypackage.PartCollectionBean;
import mypackage.PartInstBean;
import mypackage.PartInstDBbean;
import mypackage.UserBean;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SavePartEligibleAction extends Action
{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
  HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    
    HttpSession sess = request.getSession();
    PartInstDBbean pdb = new PartInstDBbean();
    
      try{
        boolean userID = (sess.getAttribute("lduser") != null); 
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        UserBean userb = (UserBean) sess.getAttribute("lduser");
          
        //cast the action form to a PartCollectionBean
        PartCollectionBean pcb = (PartCollectionBean) form;          
        
        //*****************VALIDATION**********************************
        
        List partlist = pcb.getAllPartInst();// get list of insts from form            
        if(partlist !=null)
        {
              ActionErrors ae = null;   
              int size = partlist.size();                
              for(int i=0; i<partlist.size(); i++)
              {
                PartInstBean pb = (PartInstBean)partlist.get(i);   
                ae = pb.validate(mapping, request);//validate() returns ActionErrors
                
                if(ae !=null && (ae.size() > 0))
                {
                  request.setAttribute(Globals.ERROR_KEY, ae); 
                  return (mapping.findForward("fail"));
                }                       
              }   
              //*****************************************************          
              //if we get here; all validation has passed - now save data to db
              
              EligibilityDbBean edb = new EligibilityDbBean();
              edb.handleParticipantEligibility(partlist, userb);             
          }
                    
                          
        //get all part institutions for this grant and redisplay
        Vector allParts = pdb.getPartInstAddressInfo(grantid);      
        PartCollectionBean newparts = pdb.getEligibleForParts(allParts);
        request.setAttribute("PartCollectionBean", newparts);     
        
      }catch(Exception e){System.out.println("error SavePartEligibleAction "+e.getMessage().toString());}
        
    return mapping.findForward("success");
  }
}