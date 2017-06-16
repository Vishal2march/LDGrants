package ldgext;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.ArrayList;

import mypackage.DBHandler;

import reports.ReportDBbean;

public class DiPublicReport extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    ReportDBbean rdb = new ReportDBbean();
    HttpSession s = request.getSession();
    
    try{
      String task = request.getParameter("task");
      
      if(task==null || task.equals("")) {
          DBHandler dbh = new DBHandler();
          ArrayList allyears = dbh.dropDownFiscalYears();
          s.setAttribute("dropDownList", allyears);
      }
      else if(task.equals("rpt"))
      {
         String fycode = request.getParameter("awardfy");  
          
          //get amt appr for each grant approved this fy, sorted by instname
         //Vector allGrants = rdb.getDiAwardList(fycode);//removed 12/30/10
         Vector allGrants = rdb.applicantFyApprSearch(fycode);
         //get PM info for each of these grants
         Vector results = rdb.getProjectManagerInfo(allGrants);
         request.setAttribute("allGrants", results);         
      }
      else if(task.equals("search"))
      {
        String title = request.getParameter("title");
        
          //get matching di grants by title
          Vector allGrants = rdb.searchDiByTitle(title);
          //get PM info for each of these grants
          Vector results = rdb.getProjectManagerInfo(allGrants);
          request.setAttribute("allGrants", results);       
      }
     
      
    }catch(Exception e) {
      System.out.println("error DiPublicReport "+e.getMessage().toString());
    }
    
    RequestDispatcher rd = request.getRequestDispatcher("/ldgext/discReports.jsp?results=true");
    rd.forward(request, response);  
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}