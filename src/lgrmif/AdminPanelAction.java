/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminPanelAction.java
 * Creation/Modification History  :
 * SH   9/13/09      Created
 *
 * Description
 * This dispatch action will handle lgrmif admin options to view panels, search for
 * grants/reviewers, add/delete grants/reviewers from a panel, etc.
 *****************************************************************************/
package lgrmif;
import java.util.ArrayList;
import java.util.Enumeration;

import java.util.Hashtable;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.PanelBean;
import mypackage.PanelDbBean;
import mypackage.RevAssignCollectionBean;
import mypackage.ReviewerAssignBean;
import mypackage.UserBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AdminPanelAction extends DispatchAction
{
  
  private DBHandler dbh = new DBHandler();
  private PanelDbBean pdb = new PanelDbBean();
  
  public ActionForward loadPanel(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{                   
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("panel");
    }
    
    
    
  /**
   * This action will load the add/update panel page with the fy drop downlist. 
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
    public ActionForward loadAdd(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{      
         request.setAttribute("PanelBean", new PanelBean());
          
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);//add to sess for validation form redisplay
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("addpanel");
    }
    
    
    public ActionForward panelrecord(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{                   
          String id=request.getParameter("id");
          
          getPanelReviewerGrantInfo(Long.parseLong(id), request);
          
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);//add to sess for validation error redisplay
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("addpanel");
    }
    
    /**
     * This dispatch action will get all panels for the requested fy_code
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward search(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{   
          String fy = request.getParameter("fyid");
          ArrayList panels =pdb.getPanelsForYear(Integer.parseInt(fy));
          request.setAttribute("allPanels", panels);
      
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          request.setAttribute("dropDownList", allyears);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("panel");
    }
    
    
    
    public ActionForward searchrevs(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{   
          String fy = request.getParameter("fid");
          String panelid = request.getParameter("pid");
          String lname = request.getParameter("txt1");
          ArrayList allreviewers = pdb.findReviewersByLname(lname, Long.parseLong(panelid), 
                                                           Integer.parseInt(fy));    
          request.setAttribute("allReviewers", allreviewers);
      
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          request.setAttribute("dropDownList", allyears);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("searchrevs");
    }
    
    
     public ActionForward grantsearch(ActionMapping mapping, ActionForm form,
              HttpServletRequest request, HttpServletResponse response) throws Exception 
     {       
       HttpSession sess = request.getSession(); 
       if(checkSessionTimeout(sess))
         return mapping.findForward("timeout");
         
       try{   
           String pcid = request.getParameter("projcategoryId");
           String fyid = request.getParameter("fy");
           String panelid = request.getParameter("pid");
           ArrayList grants =pdb.handlePotentialPanelGrants(Long.parseLong(pcid), Long.parseLong(panelid), Integer.parseInt(fyid));
           
           RevAssignCollectionBean rc= new RevAssignCollectionBean();  
           rc.setAllPotentialGrants(grants);
           request.setAttribute("AssignCollectionBean", rc);                  
       }
       catch(Exception e){ log.error(e.getMessage().toString());
         return mapping.findForward("error");
       }      
       return mapping.findForward("grantsearch");
     }
     
    
    public ActionForward addpanelrev(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{   
         UserBean ub = (UserBean) sess.getAttribute("lduser");
         String revid = request.getParameter("rid");
         String panelid = request.getParameter("pid");
         String alreadyAssigned = request.getParameter("a");
         String acceptreview = request.getParameter("r");
         
         if(alreadyAssigned!=null && acceptreview!=null)
         {
            if(acceptreview.equals("true") && alreadyAssigned.equals("false"))
                pdb.addReviewerToPanel(Integer.parseInt(revid), Long.parseLong(panelid), ub);
         }
         
         //go back to the update panel page, which lists all panel reviewers and grants
         getPanelReviewerGrantInfo(Long.parseLong(panelid), request);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("addpanel");
    }


    public ActionForward addpanelgra(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{         
         UserBean ub = (UserBean) sess.getAttribute("lduser");   
         long panelid=0;
         RevAssignCollectionBean allItems = (RevAssignCollectionBean)request.getAttribute("AssignCollectionBean");
          
          // get the list of panel-grant records 
          List grantList = allItems.getAllPotentialGrants();   
          
          if(grantList!=null)
          {
              for(int i=0; i<grantList.size(); i++)
              {
                GrantBean gb = (GrantBean)grantList.get(i);  
                panelid=gb.getPanelId();
                
                if(gb.isAssignpanel() && gb.getPanelgrantId()==0 && gb.isAssigndiffpanel()==false)              
                  pdb.addGrantToPanel(gb.getGrantid(), gb.getPanelId(), ub); //add record            
                else if(gb.isAssignpanel()==false && gb.getPanelgrantId()>0)                    
                  pdb.deleteGrantFromPanel(gb.getPanelgrantId());//delete record            
              }
          }
          
          request.removeAttribute("AssignCollectionBean");//to prevent displaying form again        
                        
         //go back to the update panel page, which lists all panel reviewers & grants
          getPanelReviewerGrantInfo(panelid, request);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("addpanel");
    }


    public ActionForward delpanelrev(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{   
         String panelrevid = request.getParameter("id");
         String panelid = request.getParameter("pid");
         
         int outcome = pdb.deleteReviewerFromPanel(Long.parseLong(panelrevid));
                        
         //go back to update panel page, which lists all panel reviewers & grants
          getPanelReviewerGrantInfo(Long.parseLong(panelid), request);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("addpanel");
    }
    
    
    public ActionForward delpanelgra(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{   
         String panelgraid = request.getParameter("id");
         String panelid = request.getParameter("pid");
         
         int outcome = pdb.deleteGrantFromPanel(Long.parseLong(panelgraid));
                        
         //go back to update panel page, which lists all panel reviewers & grants
          getPanelReviewerGrantInfo(Long.parseLong(panelid), request);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("addpanel");
    }
        
    
    public ActionForward deletepanel(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{   
         String panelid = request.getParameter("id");         
         int outcome = pdb.deletePanel(Long.parseLong(panelid));
                        
         //return to search panels page, display delete error if necessary
         request.setAttribute("deletestatus", new Integer(outcome));
         
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("panel");
    }
    
    
    public ActionForward loadgrasearch(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{                   
            ArrayList allcats = dbh.dropDownProjCategories();
            sess.setAttribute("dropDownCategories", allcats);
            
            //ArrayList allyears = dbh.dropDownFiscalYears();
            //sess.setAttribute("dropDownList", allyears);
            
            String panelid =request.getParameter("pid");
            sess.setAttribute("workpid", panelid);
            String fycode = request.getParameter("fid");
            sess.setAttribute("workfy", fycode);
        }
        catch(Exception e){ log.error(e.getMessage().toString());
          return mapping.findForward("error");
        }      
        return mapping.findForward("grantsearch");
      }
      
      
    public ActionForward moneyamts(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{      
            String fycode = request.getParameter("fy");
            
            ArrayList panelamts =pdb.getPanelMoneyAmounts(Integer.parseInt(fycode));
            request.setAttribute("panelAmts", panelamts);
        }
        catch(Exception e){ log.error(e.getMessage().toString());
          return mapping.findForward("error");
        }      
        return mapping.findForward("moneyamts");
      }
    
    /**
     * Method will get all info about panel, and all reviewers and grants that are
     * members of this panel. All info displayed on panel add/update page.
     * @param panelid
     * @param request
     */
    public void getPanelReviewerGrantInfo(long panelid, HttpServletRequest request) 
    {
        try{
            PanelBean p = pdb.getPanelRecord(panelid);          
            request.setAttribute("PanelBean", p);
            
            ArrayList panelrev = pdb.getReviewersForPanel(panelid);
            request.setAttribute("panelReviewers", panelrev);
            
            //get grants assigned to this panel
            ArrayList panelgrant = pdb.getGrantsForPanel(panelid);
            request.setAttribute("panelGrants", panelgrant);
                         
        }catch(Exception e){ log.error(e.getMessage().toString()); } 
    }
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT DiAdminRevAction");
        timeout = true;
      }      
      return timeout;
    }
    
}