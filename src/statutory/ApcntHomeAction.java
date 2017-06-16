/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ApcntHomeAction.java
 * Creation/Modification History  :
 *
 * SH   1/16/09      Created
 *
 * Description
 * This dispatch action will get all grants for sa/co/di/fl/al/lg/cn/stateaid applicant home page, or
 * handle creating a new sa/co/di/fl/al/lg/cn/stateaid application. 
 *****************************************************************************/
package statutory;

import construction.AllocationYearBean;
import construction.ConstructionDBbean;

import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppDatesBean;
import mypackage.AppStatusBean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ApcntHomeAction extends DispatchAction
{
  private DBHandler dbh = new DBHandler();
  private Log log = LogFactory.getLog(this.getClass());  

   public ActionForward homepage(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        int fccode=0;
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");       
                     
        try{        
            //remove old grants that the user was viewing
            sess.removeAttribute("grantid");   
            
            UserBean userb = (UserBean) sess.getAttribute("lduser");
            String module = request.getParameter("m");
            
            if(module.equals("sa"))
              fccode=6;
            else if(module.equals("co"))
            {
              fccode=7;
              userb.setReadaccess(userb.getPrgco().equals("read"));
              sess.setAttribute("lduser", userb);
            }
            else if(module.equals("di"))
            {
              fccode=5;
              userb.setReadaccess(userb.getPrgdi().equals("read"));
              sess.setAttribute("lduser", userb);
            }
            else if(module.equals("f"))
            {
              fccode=42;
              userb.setReadaccess(userb.getPrgfl().equals("read"));
              sess.setAttribute("lduser", userb);
            }
            else if(module.equals("a"))
            {
              fccode=40;
              userb.setReadaccess(userb.getPrgal().equals("read"));
              sess.setAttribute("lduser", userb);
            }
            else if(module.equals("lg"))
            {
              fccode=80;//should be 580
              userb.setReadaccess(userb.getPrglg().equals("read"));
              sess.setAttribute("lduser", userb);
            }
            else if(module.equals("cn")){
                fccode=86;
                userb.setReadaccess(userb.getPrgconstruction().equals("read"));
                sess.setAttribute("lduser", userb);
            }
            else if(module.equals("staid")){
                fccode=20;//stateaid for cjh/nyhs
                userb.setReadaccess(userb.getPrgNycStateaid().equals("read"));
                sess.setAttribute("lduser", userb);
            }
            
          
            //find all grants for this user's instID
            HashMap results =dbh.getGrantsForProgramHome(userb.getInstid(), fccode); 
            
            if(fccode==80 || fccode==86) {
                results = dbh.getSubmitStatusForHome(results);
            }
            if(results.containsKey(new Integer(1)))//curr grants exist
            {
              Vector grants = (Vector) results.get(new Integer(1));
              request.setAttribute("allGrants", grants);
            }
            if(results.containsKey(new Integer(2)))
            {
              Vector grants = (Vector) results.get(new Integer(2));
              request.setAttribute("partGrants", grants); 
            } 
                 
            AppDatesBean ab = verifyCreateAppAcceptable(fccode, userb.getInstid());
            request.setAttribute("appDates", ab);
                          
            if(fccode==7)
            {
                //get the total amount of money available for this fy
                BudgetDBHandler bdh = new BudgetDBHandler();
                TotalsBean tb = bdh.getTotalCoAmtAvailableForFY(ab.getFycode());
                request.setAttribute("totAvail", tb);
                //get total requested so far for this fy
                tb = bdh.getTotCoAmtReqForFy(ab.getFycode());
                request.setAttribute("totReq", tb);       
            }
                
            request.setAttribute("grantBean", null);
        }catch(Exception e){
        System.out.println("error ApcntHomeAction "+e.getMessage().toString());
        log.error(e.getMessage().toString());
      }      
      return mapping.findForward("homepage");      
    }
    
    
    
    public AppDatesBean verifyCreateAppAcceptable(int fccode, long instid)
    {
      AppDatesBean ab =new AppDatesBean();
      try{
      
        ab = dbh.allowCreateSubmitRecord(fccode);//may need to change for lgrmif disaster due date exception
        
        if(fccode==6 || fccode==5  ||  fccode==20)//stat/disc/stateaid can only have 1 app per year
        {
          boolean hasRecord = true;              
          if(ab.isDateAcceptable())//if dates are ok, does inst already have app?
             hasRecord = dbh.hasRecord(ab.getFycode(), instid, fccode);
                      
          if(!hasRecord)
            ab.setCanApply(true);

        }
        else if(fccode==40 || fccode==42)//al/fl starting FY13-14
        {
            ConstructionDBbean cdb = new ConstructionDBbean();
            boolean isPls = cdb.isLibrarySystem(instid);
            
            if(isPls){
                //9/24/12, starting FY13-14 only PLS can apply; check valid dates
                 ab.setPlsInstitution(true);
                 boolean hasRecord = true;              
                 if(ab.isDateAcceptable())//if dates are ok, does inst already have app?
                    hasRecord = dbh.hasRecord(ab.getFycode(), instid, fccode);
                             
                 if(!hasRecord)
                   ab.setCanApply(true);
            }
            else{
                //error message - not a PLS - cannot create app
                ab.setPlsInstitution(false);
                ab.setCanApply(false);                
            }            
        }
        else if(fccode==80)//lgrmif allows 2 apps per fy, unlimited apps for doris
        {
          boolean hasMaxRecords = true;
          if(ab.isDateAcceptable())
            hasMaxRecords = dbh.hasLgrmifMaxRecord(ab.getFycode(), instid, fccode);
            
          if(!hasMaxRecords)
            ab.setCanApply(true);
        }
        else if(fccode==7|| fccode==86){//coord & construct allows unlimited apps                  
            ab.setCanApply(ab.isDateAcceptable());              
        }
        
      }catch(Exception e){System.out.println("error ApcntHomeAction "+e.getMessage().toString());
                          log.error(e.getMessage().toString());}     
      return ab;
    }
    
    
    
    /**
     * New dispatch action 2/24/12. used for construct, if applicant already has an
     * app for FY, ask if they want to continue existing app, or create a new app. 
     * this dispatch action called if they want to create a new app (multiple apps/FY)
     * @throws Exception
     */
    public ActionForward confirmCreateApp(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        int fccode=0;
        String permiss="read";
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");       
                     
        try{     
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          String module = request.getParameter("m");
          
            if(module.equals("cn"))
            {
                fccode=86;//construction
                permiss=userb.getPrgconstruction();
            }
            ////////////////////////////////////////////////////////////////
            //check if dates and user permissions allow to create record
            AppDatesBean ab = verifyCreateAppAcceptable(fccode, userb.getInstid());
            if( (!ab.isCanApply()) || permiss.equals("read"))
              return mapping.findForward("authorize");
                   
                    
            //CREATE NEW APP
            long grantid=dbh.createRecordForProgram(userb, ab.getFycode(), fccode);
            log.warn("New grants.id= "+grantid);
            if(grantid <1)
               return mapping.findForward("error");
                         
            sess.setAttribute("grantid", String.valueOf(grantid));
            GrantBean gb =dbh.getRecordBean(grantid);
            sess.setAttribute("thisGrant", gb); 
            
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb);          
                      
        }catch(Exception e){
            System.out.println("error ApcntHomeAction "+e.getMessage().toString());
            log.error(e.getMessage().toString());
        }          
        return mapping.findForward("checklist");
    }
    
    
    
    
    public ActionForward createapp(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        int fccode=0;
        String permiss="read";
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");       
                     
        try{     
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          String module = request.getParameter("m");
          
          if(module.equals("sa"))
          {
            fccode=6;
            permiss=userb.getPrgsa();
          }
          else if(module.equals("co"))
          {
            fccode=7;
            permiss=userb.getPrgco();
          }
          else if(module.equals("di"))
          {
            fccode=5;
            permiss=userb.getPrgdi();
          }
          else if(module.equals("f"))
          {
            fccode=42;
            permiss=userb.getPrgfl();
          }
          else if(module.equals("a"))
          {
            fccode=40;
            permiss=userb.getPrgal();
          }
          else if(module.equals("lg"))
          {
            fccode=80;//should be 580
            permiss = userb.getPrglg();
          }
          else if(module.equals("cn"))
          {
              fccode=86;//construction
              permiss=userb.getPrgconstruction();
          }
          else if(module.equals("staid")){
              fccode=20;//stateaid cjh/nyhs
              permiss=userb.getPrgNycStateaid();
          }
            
            
        ////////////////////////////////////////////////////////////////
          //check if dates and user permissions allow to create record
          AppDatesBean ab = verifyCreateAppAcceptable(fccode, userb.getInstid());
          if( (!ab.isCanApply()) || permiss.equals("read"))
            return mapping.findForward("authorize");
                 
        //for construction; warn if record exists, ask if user needs multiple app
          if(fccode==86){
              boolean hasRecord = dbh.hasRecord(ab.getFycode(), userb.getInstid(), fccode);
              
              if(hasRecord)
                 return mapping.findForward("confirmnew");
          }
          
          //CREATE NEW APP
          long grantid=dbh.createRecordForProgram(userb, ab.getFycode(), fccode);
          log.warn("New grants.id= "+grantid);
          if(grantid <1)
             return mapping.findForward("error");
                       
          sess.setAttribute("grantid", String.valueOf(grantid));
          GrantBean gb =dbh.getRecordBean(grantid);
          sess.setAttribute("thisGrant", gb); 
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);       
            
          if(fccode==40 || fccode==42){
            //8/13/15 per KBALSEN; get yearly allocation and print on checklist
            BudgetDBHandler bdh = new BudgetDBHandler();
            Vector<AllocationYearBean> allalloc = bdh.getYearlyAllocationForInst(gb.getFycode(), gb.getFccode(), gb.getInstID());
            request.setAttribute("allAllocAmounts", allalloc);
          }
                        
        }catch(Exception e){
        System.out.println("error ApcntHomeAction "+e.getMessage().toString());
        log.error(e.getMessage().toString());
      }      
      return mapping.findForward("checklist");      
    }
    
  
   public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null);
      if (!userID && sess.isNew())
      {      
        timeout = true;
      }      
      return timeout;
    }
}