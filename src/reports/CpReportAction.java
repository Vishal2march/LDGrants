/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  CpReportAction.java
 * Creation/Modification History  :
 *
 * Description
 * These dispatch actions are used to generate reports for cp/lit admin, di applicant
 * grant search, and the 2 common program reports.
 *****************************************************************************/
package reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.FiscalYearBean;

import mypackage.GrantBean;

import mypackage.ReviewerAssignDBbean;

import mypackage.TotalsBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CpReportAction extends DispatchAction {
    ReportDBbean rdb = new ReportDBbean();
    
    
  public ActionForward revcontact(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
  {                                      
      try{         
         String fc = request.getParameter("fc");
         
         //get all reviewers, addresses, phone and email
         Vector allRev = rdb.getRevContactInfo(fc, false);
         request.setAttribute("allRev", allRev);
      
      }catch(Exception e){
        System.out.println("error CpReportAction "+e.getMessage().toString());
      }
      return mapping.findForward("revcontact");        
  }
    
    
  public ActionForward availability(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
  {                                      
      try{         
          String fycode = request.getParameter("fy");
          String fc = request.getParameter("fc");
          
           //get all reviewers participation and assignments for selected fy
           String program="";
           if(fc.equals("7"))
                program = "coordinated";
           else if(fc.equals("5"))
                program = "discretionary";
                
           Vector allRev = rdb.getAcceptedAssignedForFy(fycode, program);
           request.setAttribute("allRev", allRev);          
           viewFiscalYear(request, fycode);           
                            
      }catch(Exception e){
        System.out.println("error CpReportAction "+e.getMessage().toString());
      }
      return mapping.findForward("availability");        
  }
    
    public ActionForward revassign(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();
        try{         
            String fycode = request.getParameter("fy");
            String fc = request.getParameter("fc");
            
            //get all reviewer assignments for selected fy
            Vector allRev = rdb.getReviewerAssignments(fycode, fc);          
            s.setAttribute("allRev", allRev);
                        
             viewFiscalYear(request, fycode);              
                                          
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("revassign");        
    }
    
       
    public ActionForward appdeny(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();
        try{         
            String fycode = request.getParameter("fy");         
            String fc = request.getParameter("fc");
            
            //get grants denied for fy
            Vector subGrants = rdb.getGrantsDenied(fycode, Integer.parseInt(fc));         
            //get totappr and totreq for each of these grants (totappr should be 0)
            Vector allGrants = rdb.calcAmtReqAmtAppr(subGrants);
            
            //sort by inst name
            Collections.sort(allGrants, GrantBean.InstitutionComparator);
            s.setAttribute("allGrants", allGrants);         
            
            viewFiscalYear(request, fycode);       
                                                            
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("appdeny");        
    }
    
    
    public ActionForward revscores(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        try{         
            String projnum = request.getParameter("projnum");
            String showrev = request.getParameter("showrev");
            if(showrev!=null)
              request.setAttribute("showRev", showrev);
            else
              request.setAttribute("showRev", "N");
            
             //get all info for selected grant
             GrantBean gb = rdb.getBasicGrantInfo(projnum);
             request.setAttribute("grantBean", gb);
             
             if(gb.getGrantid()!=0)//if projnum was found in db
             {
                 //get all comments for grant
                  ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
                  Vector allComments = rab.getCommentsForAdmin(gb.getGrantid());
                  request.setAttribute("allComments", allComments);
                 
                 //get scores for grant -different weights for disc/coor
                 if(gb.getFccode()==7){                 
                     Vector allScores = rab.getScoresForCoGrant(gb.getGrantid(), gb.getFycode());
                     request.setAttribute("allScores", allScores);
                 }
                 else if(gb.getFccode()==5){
                     Vector allScores = rab.getScoresForDiWeighted(gb.getGrantid());
                     request.setAttribute("allScores", allScores);
                 }
                 else if(gb.getFccode()==40 || gb.getFccode()==42){
                     //get scores for AL/FL grant              
                     Vector allScores = rab.getScoresForLit(gb.getGrantid());
                     request.setAttribute("allAssigned", allScores);
                 }
             }                 
                                                                       
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("revscores");        
    }
    
    
    public ActionForward statutoryappr(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        try{         
            String fycode = request.getParameter("fy");         
            //get submitted sa grants
            Vector allSubmitted = rdb.getInitialSubmitGrants(fycode, 6);
            //get amt approved for each
            Vector results = rdb.getSaAmtApprovedForFy(allSubmitted);
            request.setAttribute("allGrants", results);
            
            viewFiscalYear(request, fycode);
                                                            
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("statutoryappr");        
    }   
    
    
    public ActionForward coordappr(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        try{         
            String fycode1 = request.getParameter("sfycode");  
            String fycode2 = request.getParameter("efycode");
            if(fycode1==null || fycode1.equals(""))//must choose a valid fycode
             fycode1="0";
            if(fycode2==null || fycode2.equals(""))
             fycode2="0";
            if(Integer.parseInt(fycode1) > Integer.parseInt(fycode2))//fy1 must be < fy2
             fycode1="0";
             
             //get amt appr for each grant by fy
            Vector results = rdb.getCoAmtApprForFy(fycode1, fycode2);
            
             //sort by fiscal year
             Collections.sort(results, GrantBean.FiscalYearCodeComparator);       
            request.setAttribute("allGrants", results);
            
            //get total amt appr for fy
            Vector totals = rdb.calcCoAmtApprForFy();
            request.setAttribute("allTotals", totals);
            
            FiscalYearBean fb = rdb.getFiscalYearInfo(fycode1);
            FiscalYearBean fb2 = rdb.getFiscalYearInfo(fycode2);
            request.setAttribute("fybean1", fb);
            request.setAttribute("fybean2", fb2);
                                                                        
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("coordappr");        
    }     
    
    
    public ActionForward scoresrpt(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();
        try{         
            String fycode = request.getParameter("fy");         
            String fc = request.getParameter("fc");
            int fccode = Integer.parseInt(fc);
            
            //get submitted grants during fy
            Vector subGrants = rdb.getInitialSubmitGrants(fycode, fccode);
            
            if(fccode==7){            
                //get scores for each grant
                subGrants = rdb.getCoordGrantsScores(subGrants, Integer.parseInt(fycode));
                request.setAttribute("program", "Coordinated");
            }
            else if(fccode==5){
                //get the avg score for each grant
                for(int i=0; i<subGrants.size(); i++)
                {
                   GrantBean gb = (GrantBean) subGrants.get(i);
                   int avgscore = rdb.getDiscGrantScores(gb.getGrantid());
                   gb.setScore(avgscore);
                }
                Collections.sort(subGrants);//sort by score order
                request.setAttribute("program", "Discretionary");
            }
            
            //get totappr and totreq for each of these grants
            Vector allGrants = rdb.calcAmtReqAmtAppr(subGrants);
            s.setAttribute("allGrants", allGrants);
            
            viewFiscalYear(request, fycode);            
                                                                       
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("scoresrpt");        
    }    
    
    //new 11/17/11 per BL, coord score order rpt with amount req for each of 3 years.
    public ActionForward coordrequestallyears(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();
        try{         
            String fycode = request.getParameter("fy");         
            String fc = request.getParameter("fc");
            int fccode = Integer.parseInt(fc);
            
            //get submitted grants during fy
            Vector subGrants = rdb.getInitialSubmitGrants(fycode, fccode);
                              
            //get scores for each grant
            subGrants = rdb.getCoordGrantsScores(subGrants, Integer.parseInt(fycode));     
                        
            //get totappr and totreq for each of these grants
            Vector allGrants = rdb.calcAmtReqApprEachYear(subGrants, Integer.parseInt(fycode));
            s.setAttribute("allGrants", allGrants);
            
            viewFiscalYear(request, fycode);            
                                                                       
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("coordallyears");        
    }    
    
    public ActionForward newapps(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();
        try{         
            String fycode = request.getParameter("fy");         
            String fc = request.getParameter("fc");
            
            //get submitted grants during fy 
            Vector subGrants = rdb.getInitialSubmitGrants(fycode, Integer.parseInt(fc));
            Collections.sort(subGrants, GrantBean.InstitutionComparator);//sort by inst name
            
            //get totappr and totreq for each of these grants
            Vector allGrants = rdb.calcAmtReqAmtAppr(subGrants);
            s.setAttribute("allGrants", allGrants);
            
            viewFiscalYear(request, fycode);
            if(fc.equals("5"))
                request.setAttribute("program", "Discretionary");   
                                                            
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("newapps");        
    }
    
    /**
     * This dispatch action is currently used to get the discretionary award list.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward awardlist(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();
        try{         
            String fycode = request.getParameter("fy");  
             
             //get amt appr for each grant approvedthis fy, sorted by instname
            Vector results = rdb.getDiAwardList(fycode);         
            s.setAttribute("allGrants", results);         
                    
            viewFiscalYear(request, fycode);
                                                            
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("awardlist");        
    }
    
        
    public ActionForward nonsubgrants(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();
        try{         
            String fycode = request.getParameter("fy");
            String fc = request.getParameter("fc");
            
            Vector allgrants = rdb.getUnsubmittedGrants(Integer.parseInt(fycode), Integer.parseInt(fc));
            s.setAttribute("allGrants", allgrants);
             
            viewFiscalYear(request, fycode);
            
            if(fc.equals("5") || fc.equals("7") || fc.equals("40") || fc.equals("42"))
                request.setAttribute("prefc", "03");
                                                            
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("nonsubgrants");        
    }
    
    
    public ActionForward blankavail(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        try{         
            String fycode = request.getParameter("fy");
            String fc = request.getParameter("fc");
            
            String program="";
            if(fc.equals("7"))
                 program = "coordinated";
            else if(fc.equals("5"))
                 program = "discretionary";
                 
            //get all reviewer participation for selected fy
            Vector allRev = rdb.getAcceptedAssignedForFy(fycode, program);
            request.setAttribute("allRev", allRev);
                                                            
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("blankavail");        
    }
    
    
    public ActionForward blankassign(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        try{         
            String fycode = request.getParameter("fy");
            String fc = request.getParameter("fc");
            
            Vector allGrants = rdb.getInitialSubmitGrants(fycode, Integer.parseInt(fc));
            request.setAttribute("subGrants", allGrants);
                                                            
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("blankassign");        
    }
    
    
    /**
     * Method used to get amts req/appr for lit admin.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward awardamt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession(); 
        DBHandler dbh = new DBHandler();
        
        try{         
           String fycode = request.getParameter("fy");         
           String fccode = request.getParameter("fc");
            
           //get submitted grants during fy 
           Vector subGrants = rdb.getInitialSubmitGrants(fycode, Integer.parseInt(fccode));           
                                 
           //get totappr and totreq for each of these grants
           for(int i=0; i<subGrants.size(); i++)
           {
             GrantBean gb = (GrantBean) subGrants.get(i);
             //2 TotalsBean inside hashmap: 1 for each fy of project
             AppStatusBean asb = dbh.getApplicationStatus(gb.getGrantid());
                    
             HashMap amts = dbh.getProjectBudgetTotalsByFy(gb.getGrantid(), true, asb, gb.getInstID());
             TotalsBean t1 = new TotalsBean();
             TotalsBean t2 = new TotalsBean();
             if(amts.containsKey("1"))
               t1 = (TotalsBean) amts.get("1");
             if(amts.containsKey("2"))
               t2 = (TotalsBean) amts.get("2");
                              
             TotalsBean[] tbs = {t1,t2};
             gb.setTotalsBeans(tbs);             
             //set the grant totals for both years
             gb.setTotamtreq(t1.getTotAmtReq() + t2.getTotAmtReq());
             gb.setTotamtappr(t1.getTotAmtAppr() + t2.getTotAmtAppr());             
           }
           
           //get the scores/ratings from each reviewer for each grant
           //Vector results = rdb.getLitScores(subGrants);             
           s.setAttribute("allGrants", subGrants);
                           
        }catch(Exception e){
          System.out.println("error CpReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("awardamt");        
    }
    
    
    /**
     * Gets score order report for lit admin.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward scorerating(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
       HttpSession s = request.getSession(); 
       
       try{         
          String fycode = request.getParameter("fy");         
          String fccode = request.getParameter("fc");
          
          //get submitted grants during fy 
          Vector subGrants = rdb.getInitialSubmitGrants(fycode, Integer.parseInt(fccode));           
          
          //get the scores/ratings from each reviewer for each grant
          Vector results = rdb.getLitScores(subGrants);           
          s.setAttribute("allGrants", results);
          
    }catch(Exception e){
       System.out.println("error CpReportAction "+e.getMessage().toString());
     }
     return mapping.findForward("scorerating");        
    }
    
    
    public ActionForward projstatus(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
       HttpSession s = request.getSession(); 
       
       try{         
          String fycode = request.getParameter("fy");         
          String fccode = request.getParameter("fc");
          
          //get submitted grants during fy 
          Vector subGrants = rdb.getInitialSubmitGrants(fycode, Integer.parseInt(fccode));  
          
          //get app status for each grant
          Vector results = rdb.getStatusForGrants(subGrants);
          
          //get comments apcnt may have made
          Vector grantresults = rdb.getApcntComments(results);
          s.setAttribute("allGrants", grantresults);
                              
    }catch(Exception e){
       System.out.println("error CpReportAction "+e.getMessage().toString());
     }
     return mapping.findForward("projstatus");        
    }
    
    
    public ActionForward interimstatus(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
       HttpSession s = request.getSession(); 
       
       try{         
          String fycode = request.getParameter("fy");         
          String fccode = request.getParameter("fc");
          
          //get submitted grants during fy 
          Vector subGrants = rdb.getInitialSubmitGrants(fycode, Integer.parseInt(fccode));  
          
          //get app status for each grant
          Vector results = rdb.getStatusForGrants(subGrants);
          s.setAttribute("allGrants", results);          
                    
    }catch(Exception e){
       System.out.println("error CpReportAction "+e.getMessage().toString());
     }
     return mapping.findForward("interimstatus");        
    }
    
    
    public ActionForward finalstatus(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
       HttpSession s = request.getSession(); 
       
       try{         
          String fycode = request.getParameter("fy");         
          String fccode = request.getParameter("fc");
          
          //get submitted grants during fy 
          Vector subGrants = rdb.getInitialSubmitGrants(fycode, Integer.parseInt(fccode));  
          
          //get app status for each grant
          Vector results = rdb.getStatusForGrants(subGrants);
          s.setAttribute("allGrants", results);          
                    
    }catch(Exception e){
       System.out.println("error CpReportAction "+e.getMessage().toString());
     }
     return mapping.findForward("finalstatus");        
    }
    
    public void viewFiscalYear(HttpServletRequest req, String fycode){
        try{
            FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
            req.setAttribute("fybean", fb);
        }catch(Exception e){System.out.println("error CpReportAction "+e.getMessage().toString());}
    }
    
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT CpReportAction");
        timeout = true;
      }      
      return timeout;
    }
    
    
    public ActionForward reports(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
            DBHandler dbh = new DBHandler();
            ArrayList allyears = dbh.dropDownFiscalYears();
            sess.setAttribute("dropDownList", allyears);
                                
        }catch(Exception e){ log.error(e.getMessage().toString());}        
        return mapping.findForward("reports");          
    }
    
    
  /**
     *new rpt 7/17/13 per BL - search cp dg projects by title, for a fy range (previous version
     * only searched given fy)
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward titleyearsearch(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {         
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
           String title = request.getParameter("titleyear");
           String startfy = request.getParameter("stitlefy");         
           String endfy = request.getParameter("etitlefy");
            
            //get matching di grants by title
            Vector allGrants = rdb.searchDiByTitleYear(title, startfy, endfy);
          
            //get PM info for each of these grants
            Vector results = rdb.getProjectManagerInfo(allGrants);
            sess.setAttribute("allGrants", results);      
          
          request.setAttribute("titlesearch", "true");
                              
      }catch(Exception e){ log.error(e.getMessage().toString());}        
      return mapping.findForward("titleyearsearch");          
  }
  
  
  
  public ActionForward multititleyearsearch(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {         
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
           String title = request.getParameter("titleyear1");
           String title2 = request.getParameter("titleyear2");
           String operator = request.getParameter("operator");
           String startfy = request.getParameter("stitlefy");         
           String endfy = request.getParameter("etitlefy");
            
            //get matching di grants by title keyword and operator
            Vector allGrants = rdb.searchMultiKeywordYear(title,title2,operator, startfy, endfy);
          
            //get PM info for each of these grants
            Vector results = rdb.getProjectManagerInfo(allGrants);
            sess.setAttribute("allGrants", results);      
          
          request.setAttribute("titlesearch", "true");
                              
      }catch(Exception e){ log.error(e.getMessage().toString());}        
      return mapping.findForward("titleyearsearch");          
  }
  
  
  public ActionForward educproject(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {         
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
           String startfy = request.getParameter("stitlefy");         
           String endfy = request.getParameter("etitlefy");
            
           //get matching di grants where education flag = true
           Vector allGrants = rdb.searchDiEducationYear(startfy, endfy);
          
           //get PM info for each of these grants
           Vector results = rdb.getProjectManagerInfo(allGrants);
           sess.setAttribute("allGrants", results);     
          
           request.setAttribute("titlesearch", "false");
                              
      }catch(Exception e){ log.error(e.getMessage().toString());}        
      return mapping.findForward("titleyearsearch");    //reuse titleyear report jsp page (just need diff title)      
  }
  
  
    
    /**
     * Used for di applicants to search for awards by fy.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward fysearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
            String fycode = request.getParameter("fy");  
             
             //get amt appr for each grant approved this fy, sorted by instname
            //Vector allGrants = rdb.getDiAwardList(fycode); //this fn does not check whether appr amts can be shown to apcnt yet
            Vector allGrants = rdb.applicantFyApprSearch(fycode);
            //get PM info for each of these grants
            Vector results = rdb.getProjectManagerInfo(allGrants);
            request.setAttribute("allGrants", results);        
                                
        }catch(Exception e){ log.error(e.getMessage().toString());}        
        return mapping.findForward("reports");          
    }
    
    
    /**
     * used by di applicants to search keywords in title.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward titlesearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
             String title = request.getParameter("title");
            
              //get matching di grants by title
              Vector allGrants = rdb.searchDiByTitle(title);
              //get PM info for each of these grants
              Vector results = rdb.getProjectManagerInfo(allGrants);
              request.setAttribute("allGrants", results);      
                                
        }catch(Exception e){ log.error(e.getMessage().toString());}        
        return mapping.findForward("reports");          
    }
    
    
    
    /**
     * used by di applicants to search for applications/awards by institution. 1/5/11
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward instsearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
             String instname = request.getParameter("inst");
            
              //get matching di grants by institution name
              Vector allGrants = rdb.searchByInstPublicRpt(instname, 5);
              
              //get PM info for each of these grants
              Vector results = rdb.getProjectManagerInfo(allGrants);
              request.setAttribute("allGrants", results);      
                                
        }catch(Exception e){ log.error(e.getMessage().toString());}        
        return mapping.findForward("reports");          
    }
    
    /**
     * Method is used for all programs admin reports (cp/lit/lg).  Shows awards for 
     * all programs - does not filter by fc
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward allawardfy(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();         
        try{         
           String fycode = request.getParameter("fyallaw");         
                      
           //get grants submitted and approved during fy
           Vector allgrants = rdb.getInitialApprAllPrograms(Integer.parseInt(fycode), 0);
           
           Vector results =rdb.calcAmtReqAmtAppr(allgrants);
           s.setAttribute("allGrants", results);
           
     }catch(Exception e){
        System.out.println("error CpReportAction "+e.getMessage().toString());
      }
      return mapping.findForward("allawardfy");        
    }      
    
    /**
     * Method is used for all programs admin reports (cp/lit/lg).  Shows awards for 
     * all programs - does not filter by fc
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward allawardco(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession(); 
        
        try{         
           String fycode = request.getParameter("fyawco");   
           String countycode = request.getParameter("county");
                      
           //get grants submitted and approved during fy
           Vector allgrants = rdb.getInitialApprAllPrograms(Integer.parseInt(fycode), Integer.parseInt(countycode));
           
           Vector results =rdb.calcAmtReqAmtAppr(allgrants);
           s.setAttribute("allGrants", results);
           
     }catch(Exception e){
        System.out.println("error CpReportAction "+e.getMessage().toString());
      }
      return mapping.findForward("allawardco");        
    }      
    
    
    
    
  /**
     *For literacy admin, new 2/5/16 per KBALSEN. Display all literacy apps, with abstract narrative and total edlaw
     * amount for 3 years.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward litedlawtotal(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {         
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          String fycode = request.getParameter("fy");  
          String fccode = request.getParameter("fc");
           
          //get amt appr for each grant approved this fy, sorted by instname          
          ArrayList<GrantBean> results = rdb.getLitAppEdLawAbstract(Integer.parseInt(fycode), Integer.parseInt(fccode));
          sess.setAttribute("allGrants", results);        
         
                              
      }catch(Exception e){ log.error(e.getMessage().toString());}        
      return mapping.findForward("litedlawtotal");          
  }
}
