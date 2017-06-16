/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ApcntFunctionsServlet.java
 * Creation/Modification History  :
 *
 * SH   6/14/07  Created    3/16/09  Modified
 *
 * Description
 * This servlet will route the sa/co inst auth page.  Also prints pdf versions
 * of numerous pages for co/di/lit.
 *****************************************************************************/
package statutory;

import construction.ConstructionDBbean;
import construction.FinalExpenseBean;

import java.io.InputStream;
import java.util.Vector;
import mypackage.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

public class ApcntFunctionsServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession s = request.getSession();
    DBHandler dbh = new DBHandler();
    String finalTarget = "";
    
    try{        
      //check for session timeout 
      boolean userID = (s.getAttribute("lduser") != null); //attr is created during login
        if (!userID && s.isNew())
          throw new SessionTimeoutException();
      
      
      String grantnum = (String)s.getAttribute("grantid");
      long grantid = Long.parseLong(grantnum);
      
      GrantBean gb = dbh.getRecordBean(grantid);
      s.setAttribute("thisGrant", gb);
      
      String todo=request.getParameter("i");
      
      if(todo.equals("instauth"))//FOR SA AND CO
      {
        UserBean ub = (UserBean) s.getAttribute("lduser");

        OfficerDBbean odb = new OfficerDBbean();
        OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
        s.setAttribute("presOfficerBean", presOfficerBean);
        
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
        s.setAttribute("libDirectorBean", libdirectorBean);  
        
        //get any previous authorizations for this grant        
        AuthorizationsDBbean ab = new AuthorizationsDBbean();
        Vector grantAuth = ab.getGrantAuthorizations(grantid);
        request.setAttribute("grantAuth", grantAuth);
        
        //check if user represents suny (they have extra authorizations)
        //per BL 5/21/14; no longer need extra auth for suny's (commented out on jsp)
        DbName db = new DbName();
        InputStream is;
        if(db.production==false)
          is = getServletContext().getResourceAsStream("docs/sunyinstidTest.txt");
        else 
          is = getServletContext().getResourceAsStream("docs/sunyinstid.txt");     
        boolean isSuny = ab.isSunyInstitution(ub.getInstid(), is);
        request.setAttribute("isSuny", Boolean.valueOf(isSuny));   
        System.out.println("is suny "+isSuny);
      
        //Go to the institutional authorization form
        if(gb.getFccode()==6)
          finalTarget="ApcntAuthorization.do";
        else if(gb.getFccode()==7)
          finalTarget="CoInstAuth.do";        
                    
        RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
        rd.forward(request, response);       
        return;//12/12/12 added b/c illegalstateexception
      }
//=============================================================================
      else if(todo.equals("commentsPdf"))//FOR COORDINATED
      {
        ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();                
        Vector allCom = rdb.getCommentsForApcnt(grantid);    
        s.setAttribute("allComments", allCom);
        
        RatingBean rb = rdb.getCatSumScoresByGrant(grantid);  
        s.setAttribute("totScores", rb);
                
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        s.setAttribute("grStatus", asb);  
           
        response.sendRedirect("RatingsComments.pdf");
        return;//12/12/12 added b/c illegalstateexception
      }
//=======================================================================
      else if(todo.equals("scores"))//FOR DISCRETIONARY
      {
        ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();        
        Vector allCom = rdb.getCommentsForApcnt(grantid);
        s.setAttribute("allComments", allCom);
                  
        RatingBean rb = rdb.getCatSumScoresDi(grantid);
        s.setAttribute("totScores", rb);
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        s.setAttribute("grStatus", asb);
        
        response.sendRedirect("diApcntRating.pdf");
        return;//12/12/12 added b/c illegalstateexception
      }
//====================================================================
      else if(todo.equals("rating"))//pdf rating for lgrmif/lit/di/co reviewer
      {
          //get all rating scores
          String assignid= (String)s.getAttribute("assignid");   
          ReviewerAssignDBbean rab = new ReviewerAssignDBbean(); 
          RatingBean rb;
          if(gb.getFccode()==80){
            rb = rab.getGrantRatingsForLGReviewer(Long.parseLong(assignid));
            BudgetDBHandler bdh = new BudgetDBHandler(); 
            rb.setTotamtreq(bdh.calcTotalAmtRequested(rb.getGraid(), 0));   
              
            EligibilityDbBean edb = new EligibilityDbBean();
            CoversheetBean csb = edb.getLgBonusScoring(rb.getGraid());
            s.setAttribute("CoverBean", csb);
          }
          else
            rb= rab.getGrantRatingsForReviewer(Long.parseLong(assignid));
          s.setAttribute("RatingBean", rb);
               
          if(gb.getFccode()==80){
              response.sendRedirect("lgReviewerRating.pdf");
              return;//12/12/12 added b/c illegalstateexception
          }
          else if(gb.getFccode()==40 || gb.getFccode()==42){
              response.sendRedirect("liReviewerRating.pdf");  
              return;//12/12/12 added b/c illegalstateexception
          }
          else if(gb.getFccode()==7 && gb.isEducationapp()){
              response.sendRedirect("reviewerEducRating.pdf");
              return;//12/12/12 added b/c illegalstateexception
          }
          else if(gb.getFccode()==7){
              response.sendRedirect("reviewerRating.pdf");
              return;//12/12/12 added b/c illegalstateexception
          }
          else if(gb.getFccode()==5 && gb.isEducationapp()){
              response.sendRedirect("diRevEdRating.pdf");
              return;//12/12/12 added b/c illegalstateexception
          }
          else{
              response.sendRedirect("diReviewerRating.pdf");         
              return;//12/12/12 added b/c illegalstateexception
          }
      }
//=========================================================================
      else if(todo.equals("ratingadminpdf"))
      {
        //get all rating scores
        String assignid= request.getParameter("assignid");   
        ReviewerAssignDBbean rab = new ReviewerAssignDBbean(); 
        RatingBean rb= rab.getGrantRatingsForReviewer(Long.parseLong(assignid));
        s.setAttribute("RatingBean", rb);
             
        if(gb.getFccode()==5 && gb.isEducationapp()){
            response.sendRedirect("diRevEdRating.pdf");
            return;//12/12/12 added b/c illegalstateexception
        }
        else{
            response.sendRedirect("diReviewerRating.pdf");         
            return;//12/12/12 added b/c illegalstateexception
        }
                
      }      
//==========================================================================
      else if(todo.equals("diauth"))
      {     
        response.sendRedirect("diInstAuth.pdf");
        return;//12/12/12 added b/c illegalstateexception
      }
//-----------------------------------------------------------------------
      else if(todo.equals("lgauth"))
      {
        response.sendRedirect("lgAuthorization.pdf");//lgrmif auth
        return;//12/12/12 added b/c illegalstateexception
      }
//-----------------------------------------------------------------------
      else if(todo.equals("finalauth"))
      {
        response.sendRedirect("lgFinalSignoff.pdf");//lgrmif final signoff
        return;//12/12/12 added b/c illegalstateexception
      }
//-----------------------------------------------------------------------
      else if(todo.equals("microform"))
      {
        response.sendRedirect("diMicroform.pdf");
        return;//12/12/12 added b/c illegalstateexception
      }
//-----------------------------------------------------------------------
      else if(todo.equals("coopagree"))
      {
          if(request.getParameter("m")!=null && request.getParameter("m").equals("lg")){
             response.sendRedirect("lgCoopAgreement.pdf");
             return;//12/12/12 added b/c illegalstateexception
          }
          else{
             response.sendRedirect("diCoopAgreement.pdf");
             return;//12/12/12 added b/c illegalstateexception
          }
      }
//----------------------------------------------------------------------
      else if(todo.equals("finalsignoff"))
      {
        response.sendRedirect("diFinalSignoff.pdf");
        return;//12/12/12 added b/c illegalstateexception
      }
//=============================================================================
      else if(todo.equals("completion"))//for construction
      {
          //get pm info
          OfficerDBbean odb = new OfficerDBbean();
          OfficerBean pmBean = odb.getProjectManager(grantid);    
          request.setAttribute("managerBean", pmBean);       
          
          //get grant amt and proj cost data
          ConstructionDBbean cdb = new ConstructionDBbean();
          long buildingGrantId= cdb.doesGrantBuildingExist(grantid);
           
          FinalExpenseBean fb = cdb.getCompletionFormAmounts(grantid, buildingGrantId);
          s.setAttribute("finalExpenseBean", fb);    
          
          RequestDispatcher rd = request.getRequestDispatcher("construction/completionHTML.jsp");
          rd.forward(request, response); 
        return;//12/12/12 added b/c illegalstateexception
      }
//=============================================================================
      else if(todo.equals("completionPdf"))//for construction
      {
          //get pm info
          OfficerDBbean odb = new OfficerDBbean();
          OfficerBean pmBean = odb.getProjectManager(grantid);    
          s.setAttribute("managerBean", pmBean);       
                     
          response.sendRedirect("cnCompletionForm.pdf");
        return;//12/12/12 added b/c illegalstateexception
      }
//-----------------------------------------------------------------------
      else if(todo.equals("bcert"))//board certification FL/AL
      {
        response.sendRedirect("boardCertification.pdf");
        return;//12/12/12 added b/c illegalstateexception
      }
//----------------------------------------------------------------------------
      else if(todo.equals("auth"))//director auth FL/AL
      {
        OfficerDBbean odb = new OfficerDBbean();
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
        s.setAttribute("libDirectorBean", libdirectorBean); 
                           
        //get all info that can be updated using struts form
        CoversheetBean csb = new CoversheetBean();
        csb.setGrantid(grantid);        
        csb =dbh.getProjectNameReligAffil(csb);        
        csb= odb.getProjectManager(csb);  
        s.setAttribute("coversheetBean", csb);     
        
        //get total amount requested by fiscal year
        BudgetDBHandler bdh = new BudgetDBHandler();
        Vector allsums =bdh.getTotAmtReqLiFYPeriod(grantid, gb.getFycode(), gb.getFccode());
        s.setAttribute("allsums", allsums);
        
        //get the senate, congress districts, etc.
        DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
        s.setAttribute("distBean", db);
          
        PartInstDBbean pdb = new PartInstDBbean();
        Vector allParts = pdb.getPartInstAddressInfo(grantid);
        s.setAttribute("allParts", allParts);
      
        response.sendRedirect("directorAuthorization.pdf");      
        return;//12/12/12 added b/c illegalstateexception
      }
//----------------------------------------------------------------------------
      else if(todo.equals("fauth"))//director final auth FL/AL
      {
        OfficerDBbean odb = new OfficerDBbean();
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
        s.setAttribute("libDirectorBean", libdirectorBean); 
      
        OfficerBean pm = odb.getProjectManager(grantid);
        s.setAttribute("projManagerBean", pm);
                                 
        response.sendRedirect("directorFinalAuth.pdf");     
        return;//12/12/12 added b/c illegalstateexception
      }
    
    }catch(Exception e)
    {
      System.out.println("error ApcntFunctionsServlet "+ e.getMessage().toString());
      
      //needed to send exception name/description to error page -otherwise blank page
      request.setAttribute("javax.servlet.jsp.jspException",  e);
      
      RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
      rd.forward(request, response);   
    }
            
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}
