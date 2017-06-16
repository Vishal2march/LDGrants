/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SavePanelAction.java
 * Creation/Modification History  :
 * SH   9/20/09      Created
 *
 * Description
 * This action will insert/update panels for lgrmif admin.
 *****************************************************************************/
package lgrmif;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.DBHandler;
import mypackage.PanelBean;
import mypackage.PanelDbBean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SavePanelAction extends Action
{
 
  public ActionForward execute(ActionMapping mapping, ActionForm form,
  HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    HttpSession sess = request.getSession();
    DBHandler dbh = new DBHandler();
    PanelDbBean pdb = new PanelDbBean();
    
    try{
      boolean userID = (sess.getAttribute("lduser") != null); 
      if (!userID && sess.isNew())         
        mapping.findForward("timeout");
            
            
      UserBean userb = (UserBean) sess.getAttribute("lduser");        
      PanelBean pb = (PanelBean)form;
      
      if(pb.getId()==0)
        pdb.addPanel(pb, userb);
      else
        pdb.updatePanel(pb, userb);        
        
      ArrayList panels =pdb.getPanelsForYear(pb.getFycode());
      request.setAttribute("allPanels", panels);
  
      ArrayList allyears = dbh.dropDownFiscalYearsDesc();
      request.setAttribute("dropDownList", allyears);
  
    }catch(Exception e){System.out.println("error SavePanelAction "+e.getMessage().toString());}
        
    return mapping.findForward("success");
  }
}