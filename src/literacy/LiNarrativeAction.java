/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  LiNarrativeAction.java
 * Creation/Modification History  :
 *
 * SH   Sept 2008      Created
 *
 * Description
 * This action class will get/return selected narrative type id for al/fl initial, 
 * final, and interim narratives.  Also has item to get narratives for read only applicant.
 *****************************************************************************/
package literacy;
import clobpackage.ClobBean;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.NarrativeDBbean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LiNarrativeAction extends DispatchAction
{

  private DBHandler dbh = new DBHandler();
  private NarrativeDBbean ndb = new NarrativeDBbean(); 
  
  /**
   * This will get selected narrative for FL applicant and display for editing.
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
      String narrTypeID = request.getParameter("id");   
      String finalTarget="fnarrative";
      int expcode=0;
       
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          int narrTypeInt = Integer.parseInt(narrTypeID);
          
          AppStatusBean ab = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", ab);
          
          if(ab.getFycode()>16)
            finalTarget="fnarrativenew";//added 1/15/16 for new narrative jsp for 16-19
          
          //get the narrative from the db and set to descrBean 
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(narrTypeInt); 
          cb.closeOracleConnection();
          
          if(narrTypeInt==1){//9/27/10 'summarydesc' now has separate jsp from other narrs
            if(ab.getFycode()>16)
              finalTarget="fsummarynarrnew";
            else
              finalTarget="fsummarynarr";
          }
          else if(narrTypeInt==120){//1/19/16 new for FY16-19, 'nysl' narrative separate jsp from others per KBALSEN
              finalTarget="fnarrativenysl";
          }
          else if(narrTypeInt==38){//starting FY16-19; budget narratives will also have budget summary jsp
              expcode = 40;
              //purchased
              if(ab.getFycode()>16)                  
                finalTarget="fpurchasednarr";
          }
          else if(narrTypeInt==40){
              expcode = 46;
              //travel
              if(ab.getFycode()>16)                  
                finalTarget="ftravelnarr";
          }
          else if(narrTypeInt==39){
              expcode = 45;
              //supply
              if(ab.getFycode()>16)                  
                finalTarget="fsupplynarr";
          }
          else if(narrTypeInt==121){
              expcode = 20;
              //equip
              if(ab.getFycode()>16)                  
                finalTarget="fequipmentnarr";
          }
          
          pdb.setNarrative(cb.getData());
          pdb.setId(cb.getNarrID());
          pdb.setNarrTypeID(narrTypeInt);  
          pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrTypeInt));
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));
          pdb.setModule("flnarr");
          
          //get budget summary details for budget narrs
          if(narrTypeInt==38 || narrTypeInt==40 || narrTypeInt==39 || narrTypeInt==121){
            
            //refresh budget data and redisplay page
            BudgetSummaryDbBean bsdb = new BudgetSummaryDbBean();
            ArrayList allexp = bsdb.getBudgetSummariesForExp(grantid, expcode);
            
            BudgetCollectionBean bc = new BudgetCollectionBean();
            bc.setAllSummaryRecords(allexp);
            request.setAttribute("BudgetCollectionBean", bc);
          }
          
      }
      catch(Exception e){
        System.out.println("error LiNarrativeAction "+e.getMessage().toString());
      }      
      request.setAttribute("projNarrative", pdb);         
          
      return mapping.findForward(finalTarget);        
    }
    
    
    
  /**
   * This will get selected narrative for AL applicant and display for editing.
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
    public ActionForward anarr(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {      
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      DescriptionBean pdb = new DescriptionBean();                
      String narrTypeID = request.getParameter("id");  
      String finalTarget="anarrative";
      int expcode=0;
       
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          
          AppStatusBean ab = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", ab);
          
          if(ab.getFycode()>16)
              finalTarget="anarrativenew";//added 1/15/16 for new narrative jsp for 16-19
          
          int narrTypeInt = Integer.parseInt(narrTypeID);
          //get the narrative from the db and set to descrBean 
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(narrTypeInt); 
          cb.closeOracleConnection();
          
          if(narrTypeInt==1){
            if(ab.getFycode()>16)
              finalTarget="asummarynarrnew";
            else
              finalTarget="asummarynarr";
          }
          else if(narrTypeInt==38){//starting FY16-19; budget narratives will also have budget summary jsp
              expcode = 40;
              //purchased
              if(ab.getFycode()>16)                  
                finalTarget="apurchasednarr";
          }
          else if(narrTypeInt==40){
              expcode = 46;
              //travel
              if(ab.getFycode()>16)                  
                finalTarget="atravelnarr";
          }
          else if(narrTypeInt==39){
              expcode = 45;
              //supply
              if(ab.getFycode()>16)                  
                finalTarget="asupplynarr";
          }
          else if(narrTypeInt==121){
              expcode = 20;
              //equip
              if(ab.getFycode()>16)                  
                finalTarget="aequipmentnarr";
          }
          
          pdb.setNarrative(cb.getData());
          pdb.setId(cb.getNarrID());
          pdb.setNarrTypeID(narrTypeInt);  
          pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrTypeInt));
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));        
          pdb.setModule("alnarr");
          
          
          //get budget summary details for budget narrs
          if(narrTypeInt==38 || narrTypeInt==40 || narrTypeInt==39 || narrTypeInt==121){
            
            //refresh budget data and redisplay page
            BudgetSummaryDbBean bsdb = new BudgetSummaryDbBean();
            ArrayList allexp = bsdb.getBudgetSummariesForExp(grantid, expcode);
            
            BudgetCollectionBean bc = new BudgetCollectionBean();
            bc.setAllSummaryRecords(allexp);
            request.setAttribute("BudgetCollectionBean", bc);
          }
                     
      }
      catch(Exception e){
        System.out.println("error LiNarrativeAction "+e.getMessage().toString());
      }
      request.setAttribute("projNarrative", pdb);   
               
      return mapping.findForward(finalTarget);        
    }
    
    
  /**
   * THis will get selected final report narr for FL appcnt and display for editing.
   * Modified 2/22/16 to route to new jsps for new 16-19 finals per kbalsen
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
    public ActionForward frpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {      
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      DescriptionBean pdb = new DescriptionBean();                
      String narrTypeID = request.getParameter("id");    
      String finalTarget="";
       
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          int narrTypeInt = Integer.parseInt(narrTypeID);
          
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
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));
          pdb.setModule("flfinrpt");
          
          if(ab.getFycode()<16)
              finalTarget = "ffinalrpt";
          else{
            //starting fy 2016-19; separate page for each of 3 years of final rpts
              switch(narrTypeInt){
                case 122:
                case 125:
                case 50:
                    finalTarget= "ffinalrptyear1";
                    break;
                case 123:
                case 126:
                case 64:
                    finalTarget= "ffinalrptyear2";
                    break;
                case 124:
                case 127:
                case 105:
                case 118:
                case 47:
                    finalTarget= "ffinalrptyear3";
                    break;
              }
          }
                    
      }
      catch(Exception e){
        System.out.println("error LiNarrativeAction "+e.getMessage().toString());
      }
      
      request.setAttribute("projNarrative", pdb);   
                
      return mapping.findForward(finalTarget);        
    }
    
    
  /**
     * This will get selected final report narr for AL appcnt and display for editing.
     *Modified 2/22/16 to route to new jsps for new 16-19 finals per kbalsen
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward afrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {      
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      DescriptionBean pdb = new DescriptionBean();                
      String narrTypeID = request.getParameter("id"); 
      String finalTarget="";
       
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          int narrTypeInt = Integer.parseInt(narrTypeID);
          
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
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));  
          pdb.setModule("alfinrpt");
          
          if(ab.getFycode()<16)
              finalTarget = "afinalrpt";
          else{
            //starting fy 2016-19; separate page for each of 3 years of final rpts
              switch(narrTypeInt){
                case 122:
                case 125:
                case 50:
                    finalTarget= "afinalrptyear1";
                    break;
                case 123:
                case 126:
                case 64:
                    finalTarget= "afinalrptyear2";
                    break;
                case 124:
                case 127:
                case 105:
                case 118:
                case 47:
                    finalTarget= "afinalrptyear3";
                    break;
              }
          }                    
      }
      catch(Exception e){
        System.out.println("error LiNarrativeAction "+e.getMessage().toString());
      }      
      request.setAttribute("projNarrative", pdb);   
               
      return mapping.findForward(finalTarget);        
    }
    
    
    public ActionForward irpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {      
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      DescriptionBean pdb = new DescriptionBean();                
      String narrTypeID = request.getParameter("id");      
       
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          int narrTypeInt = Integer.parseInt(narrTypeID);
          
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
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));   
          pdb.setModule("flinterim");
          
      }
      catch(Exception e){
        System.out.println("error LiNarrativeAction "+e.getMessage().toString());
      }
      
      request.setAttribute("projNarrative", pdb);   
                
      return mapping.findForward("finterim");        
    }
    
    
  public ActionForward airpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {      
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      DescriptionBean pdb = new DescriptionBean();                
      String narrTypeID = request.getParameter("id");      
       
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          int narrTypeInt = Integer.parseInt(narrTypeID);
          
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
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));  
          pdb.setModule("alinterim");
                    
      }
      catch(Exception e){
        System.out.println("error LiNarrativeAction "+e.getMessage().toString());
      }
      
      request.setAttribute("projNarrative", pdb);   
                
      return mapping.findForward("ainterim");        
    }
    
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
  
  
    /**
     * This will get selected narrative for FL admin and display for editing. SH 8/30/13
     * @throws java.lang.Exception
     * @return 
     * @param response
     * @param request
     * @param form
     * @param mapping
     */
    public ActionForward adminnarr(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {      
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        DescriptionBean pdb = new DescriptionBean();                
        String narrTypeID = request.getParameter("id");   
        String finalTarget="fnarrative";
        int expcode=0;
         
        try{
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid= Long.parseLong(grantnum);
            int narrTypeInt = Integer.parseInt(narrTypeID);
            
            AppStatusBean ab = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", ab);
            
            if(ab.getFycode()>16)
              finalTarget="fnarrativenew";//added 1/19/16 for new narrative jsp for 16-19
            
            
            if(narrTypeInt==120){//1/19/16 new 'nysl' narrative separate jsp from others per KBALSEN
                finalTarget="fnarrativenysl";
            }
            //starting FY16-19; budget narratives will also have budget summary jsp
            else if(narrTypeInt==38){
                  expcode = 40;
                  //purchased
                  if(ab.getFycode()>16)                  
                    finalTarget="fbudgetsummarynarr";
            }
            else if(narrTypeInt==40){
                expcode = 46;
                //travel
                if(ab.getFycode()>16)                  
                  finalTarget="fbudgetsummarynarr";
            }
            else if(narrTypeInt==39){
                expcode = 45;
                //supply
                if(ab.getFycode()>16)                  
                  finalTarget="fbudgetsummarynarr";
            }
            else if(narrTypeInt==121){
                expcode = 20;
                //equip
                if(ab.getFycode()>16)                  
                  finalTarget="fbudgetsummarynarr";
            }
            
            
            
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
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));
            pdb.setModule("fladminnarr");
            
            
          //get budget summary details for budget narrs
          if(narrTypeInt==38 || narrTypeInt==40 || narrTypeInt==39 || narrTypeInt==121){
            
            //refresh budget data and redisplay page
            BudgetSummaryDbBean bsdb = new BudgetSummaryDbBean();
            ArrayList allexp = bsdb.getBudgetSummariesForExp(grantid, expcode);
            
            BudgetCollectionBean bc = new BudgetCollectionBean();
            bc.setAllSummaryRecords(allexp);
            request.setAttribute("BudgetCollectionBean", bc);
          }
            
        }
        catch(Exception e){
          System.out.println("error LiNarrativeAction "+e.getMessage().toString());
        }      
        request.setAttribute("projNarrative", pdb);         
            
        return mapping.findForward(finalTarget);        
      }
      
      
      
    /**
     * This will get selected narrative for AL admin and display for editing.
     * @throws java.lang.Exception
     * @return 
     * @param response
     * @param request
     * @param form
     * @param mapping
     */
      public ActionForward aadminnarr(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {      
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        DescriptionBean pdb = new DescriptionBean();                
        String narrTypeID = request.getParameter("id");  
        String finalTarget="anarrative";
        int expcode=0;
         
        try{
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid= Long.parseLong(grantnum);
            
            AppStatusBean ab = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", ab);
              
            int narrTypeInt = Integer.parseInt(narrTypeID);
            
            if(ab.getFycode()>16)
              finalTarget="anarrativenew";//added 1/15/16 for new narrative jsp for 16-19
            
            
              //starting FY16-19; budget narratives will also have budget summary jsp
            if(narrTypeInt==38){
                  expcode = 40;
                  //purchased
                  if(ab.getFycode()>16)                  
                    finalTarget="abudgetsummarynarr";
            }
            else if(narrTypeInt==40){
                expcode = 46;
                //travel
                if(ab.getFycode()>16)                  
                  finalTarget="abudgetsummarynarr";
            }
            else if(narrTypeInt==39){
                expcode = 45;
                //supply
                if(ab.getFycode()>16)                  
                  finalTarget="abudgetsummarynarr";
            }
            else if(narrTypeInt==121){
                expcode = 20;
                //equip
                if(ab.getFycode()>16)                  
                  finalTarget="abudgetsummarynarr";
            }
            
            
            
            
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
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));        
            pdb.setModule("aladminnarr");
            
            
          //get budget summary details for budget narrs
          if(narrTypeInt==38 || narrTypeInt==40 || narrTypeInt==39 || narrTypeInt==121){
            
            //refresh budget data and redisplay page
            BudgetSummaryDbBean bsdb = new BudgetSummaryDbBean();
            ArrayList allexp = bsdb.getBudgetSummariesForExp(grantid, expcode);
            
            BudgetCollectionBean bc = new BudgetCollectionBean();
            bc.setAllSummaryRecords(allexp);
            request.setAttribute("BudgetCollectionBean", bc);
          }
                       
        }
        catch(Exception e){
          System.out.println("error LiNarrativeAction "+e.getMessage().toString());
        }
        request.setAttribute("projNarrative", pdb);   
                 
        return mapping.findForward(finalTarget);        
      }
    
    
    
    
  /**
     *New action to get final report narrative for AL/FL admin. added 3/2/16 for the new 2016-19 FY 
     * final reports.  prior to 2016-19 uses different action.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward adminfrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {      
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      DescriptionBean pdb = new DescriptionBean();                
      String narrTypeID = request.getParameter("id"); 
      String finalTarget="";
       
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          int narrTypeInt = Integer.parseInt(narrTypeID);
          
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
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrTypeInt, ab.getFccode(), ab.getFycode()));  
                   
          
          if(ab.getFccode()==40){  //ADULT LIT
              pdb.setModule("afinal");
              finalTarget = "aadminfinal";
          }
          else{   //FAMILY LIT
              pdb.setModule("ffinal");
              finalTarget = "fadminfinal";
          }
       
                        
      }catch(Exception e){
        System.out.println("error LiNarrativeAction "+e.getMessage().toString());
      }      
      request.setAttribute("projNarrative", pdb);   
               
      return mapping.findForward(finalTarget);        
    }
    
    
    
    
        
     public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT LiNarrativeAction");
        timeout = true;
      }      
      return timeout;
    }
  
  
}