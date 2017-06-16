/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SaveBgtNarrAction.java
 * Creation/Modification History  :
 * SH   4/1/09      Created
 *
 * Description
 * This action will save lg budget narrative(s) from budget tab, then retrieve
 * budget records and redisplay to page. 
 *****************************************************************************/
package lgrmif;
import clobpackage.ClobBean;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveBgtNarrAction extends Action
{
  private DBHandler dbh = new DBHandler();
  private NarrativeDBbean ndb = new NarrativeDBbean();


  public ActionForward execute(ActionMapping mapping, ActionForm form,
     HttpServletRequest request, HttpServletResponse response) throws Exception
    {    
       HttpSession sess = request.getSession();
       String finalTarget="";
       //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null); 
        if (!userID && sess.isNew())
          return mapping.findForward("timeout");
    
    
      try{
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);     
           DescriptionBean pdb = (DescriptionBean) form;        
           
           //get budget tab from submitted form
           String tab = pdb.getProgram();
           
            //check for user permissions
           UserBean lduser = (UserBean) sess.getAttribute("lduser");
           String permiss="";
           if(pdb.getModule().equals("lg"))
             permiss=lduser.getPrglg();
                       
           if(permiss.equals("read"))
           {
              request.setAttribute("errormsg", "User does not have access to edit the application");
              return mapping.findForward("authorize");
           }        
           
           
            //check if user entered text
           if(pdb.getNarrative()!=null && !pdb.getNarrative().equals(""))
           {
             if(pdb.getId()==0)//insert blank record first
                ndb.insertNarrativeRecord(lduser, grantid, pdb.getNarrTypeID());
              
              //update record w/ text
              ndb.updateNarrative(lduser, grantid, pdb);
           }
          
          
          
          //get new/updated version of the budget narrative(s)
          NarrativeDBbean ndb = new NarrativeDBbean();
          ndb.getLgBudgetNarrative(request, tab, grantid);                        
                    
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);   
          
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb); 
          
          //refresh budget records b/c same page as lg budget narrative
          BudgetCollectionBean bc = dbh.getApcntBudgetActionTab(grantid, Integer.parseInt(tab), asb);
          request.setAttribute("BudgetCollectionBean", bc);
          
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
          request.setAttribute("totalsBean", tb);     
                   
          finalTarget= "budget"+tab;       
          
          request.setAttribute("anchorSection", "anchorSection");    
      }
      catch(Exception e){
        System.out.println("error SaveBgtNarrAction "+ e.getMessage().toString());
      }
      return mapping.findForward(finalTarget);        
    }
}