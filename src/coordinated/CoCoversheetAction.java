package coordinated;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.NarrativeDBbean;
import mypackage.OfficerDBbean;
import mypackage.PartInstDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CoCoversheetAction extends Action
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
        //check for sess timeout
        boolean userID = (sess.getAttribute("lduser") != null);
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
                    
          
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        CoversheetBean cb = (CoversheetBean) form;//get info user entered on coversht
                      
         //check for user permissions
         UserBean lduser = (UserBean) sess.getAttribute("lduser");
         if(lduser.getPrgco().equals("read"))
         {
            request.setAttribute("errormsg", "User does not have access to update the application");
            return mapping.findForward("authorize");
         }
        
        //insert or update the pm
        if(cb.getPmId()==0)
          outcome = odb.insertProjectManager(grantid, lduser, cb);
        else
          outcome = odb.updateProjectManager(lduser, cb);
       
        //update project title
        if(cb.getProjectTitle()!=null && !cb.getProjectTitle().equals(""))
          outcome = dbh.updateProjNameReligiousAffil(lduser, grantid, cb.getProjectTitle(), false);
          
        //update participating institutions
        dbh.handleParticipatingLib(request);               
        
        //get all info to redisplay on screen  
        CoversheetBean csb = new CoversheetBean();
        csb.setGrantid(grantid);
        csb = dbh.getProjectNameReligAffil(csb);     
        csb= odb.getProjectManager(csb);        
        request.setAttribute("coversheetBean", csb);  
        
        //get the participating inst
        PartInstDBbean pdb = new PartInstDBbean();
        Vector allPartInst = pdb.getPartLibrariesCoord(request);
        sess.setAttribute("allPartInst", allPartInst);
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
          
       }catch(Exception e){System.out.println("error CoCoversheet "+e.getMessage().toString());}
       
       return mapping.findForward("success");
    }
    
}