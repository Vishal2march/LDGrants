/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  DiFsFormServlet.java
 * Creation/Modification History  :
 * SH   6/26/08      Created
 *
 * Description
 * This class allowed DI apcnt to fill out FS worksheet, then transfer those amts
 * to FS forms under each category. NOT BEING USED 7/22/08 -CHANGED TO LONG FORM
 *****************************************************************************/
package discretionary;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.FsBean;
import mypackage.GrantBean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.SessionTimeoutException;
import mypackage.TotalsBean;

public class DiFsFormServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession sess = request.getSession();
    DBHandler dbh = new DBHandler();
     
    
    try{    
        boolean userID = (sess.getAttribute("lduser") != null);
        if (!userID && sess.isNew())
          throw new SessionTimeoutException();
          
          
      String todo = request.getParameter("todo"); 
      String grantnum= (String)sess.getAttribute("grantid");
      long grantid = Long.parseLong(grantnum);
      
      
      if(todo.equals("fs20worksheet"))
      {        
        BudgetDBHandler bdh = new BudgetDBHandler();   
        int totalAmtReq = bdh.calcTotalAmtRequested(grantid, 0);        
        request.setAttribute("totAmtReq",new Integer(totalAmtReq));
             
        RequestDispatcher rd = request.getRequestDispatcher("DiFsWorksheet.do");
        rd.forward(request, response);  
        return;//12/12/12 added b/c illegalstateexception
      }
//------------------------------------------------------------------------------
      else if(todo.equals("fs10fworksheet"))
      {
        BudgetDBHandler bdh = new BudgetDBHandler();   
        int totalAmtAppr = bdh.calcTotalAmtApproved(grantid,0);        
        request.setAttribute("totAmtReq",new Integer(totalAmtAppr));
             
        RequestDispatcher rd = request.getRequestDispatcher("DiFsWorksheet.do");
        rd.forward(request, response);        
        return;//12/12/12 added b/c illegalstateexception
      }
//-----------------------------------------------------------------------------
      else if(todo.equalsIgnoreCase("FS 20 HTML"))
      {
          //get all info pertaining to this grant ID          
          GrantBean gb = dbh.getRecordBean(grantid);
          sess.setAttribute("thisGrant", gb);
          
          //get the library director
          OfficerDBbean odb = new OfficerDBbean();
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean); 
          
          OfficerBean pm = odb.getProjectManager(grantid);
          request.setAttribute("presOfficerBean", pm);//called presOfficer, even though actually PM
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, 0, false, asb, false, 0);
          request.setAttribute("budgetBean", bb);
          
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
          request.setAttribute("totalsBean", tb);    
          
          //get total amts for fs categories
          FsBean fs = new FsBean();               
          if(request.getParameter("professional")!=null && !request.getParameter("professional").equals(""))
              fs.setProfessional(dbh.parseCurrencyAmtNoDecimal(request.getParameter("professional")));
              
          if(request.getParameter("supportstaff")!=null && !request.getParameter("supportstaff").equals(""))
              fs.setSupportstaff(dbh.parseCurrencyAmtNoDecimal(request.getParameter("supportstaff")));
          
          if(request.getParameter("purchased")!=null && !request.getParameter("purchased").equals(""))
              fs.setPurchasedserv(dbh.parseCurrencyAmtNoDecimal(request.getParameter("purchased")));
              
          if(request.getParameter("supplies")!=null && !request.getParameter("supplies").equals(""))
              fs.setSupplies(dbh.parseCurrencyAmtNoDecimal(request.getParameter("supplies")));
          
          if(request.getParameter("travel")!=null && !request.getParameter("travel").equals(""))
              fs.setTravel(dbh.parseCurrencyAmtNoDecimal(request.getParameter("travel")));
          
          if(request.getParameter("benefits")!=null && !request.getParameter("benefits").equals(""))
              fs.setEmployeebenefits(dbh.parseCurrencyAmtNoDecimal(request.getParameter("benefits")));
          
          if(request.getParameter("remodeling")!=null && !request.getParameter("remodeling").equals(""))
              fs.setRemodeling(dbh.parseCurrencyAmtNoDecimal(request.getParameter("remodeling")));
          
          if(request.getParameter("equipment")!=null && !request.getParameter("equipment").equals(""))
              fs.setEquipment(dbh.parseCurrencyAmtNoDecimal(request.getParameter("equipment")));
          
          if(request.getParameter("reqtotal")!=null && !request.getParameter("reqtotal").equals(""))
              fs.setTotalamt(dbh.parseCurrencyAmtNoDecimal(request.getParameter("reqtotal")));    
          
          request.setAttribute("fsBean", fs);                  
          request.setAttribute("fundsource", "Conservation/Preservation Discretionary Grants"); 
         
         
          RequestDispatcher rd = request.getRequestDispatcher("/discretionary/fs20ItemHtml.jsp");
          rd.forward(request, response);   
        return;//12/12/12 added b/c illegalstateexception
      }
//-----------------------------------------------------------------------------
      else if(todo.equalsIgnoreCase("FS 20 PDF"))
      {
            GrantBean gb = dbh.getRecordBean(grantid);
            sess.setAttribute("thisGrant", gb);
            
            //get the library director
            OfficerDBbean odb = new OfficerDBbean();
            OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
            sess.setAttribute("libDirectorBean", libdirectorBean); 
            
            OfficerBean pm = odb.getProjectManager(grantid);
            sess.setAttribute("projManager", pm);
                               
             //get total amts for fs categories
            FsBean fs = new FsBean();
            
          if(request.getParameter("professional")!=null && !request.getParameter("professional").equals(""))
            fs.setProfessional(dbh.parseCurrencyAmtNoDecimal(request.getParameter("professional")));
              
          if(request.getParameter("supportstaff")!=null && !request.getParameter("supportstaff").equals(""))
              fs.setSupportstaff(dbh.parseCurrencyAmtNoDecimal(request.getParameter("supportstaff")));
          
          if(request.getParameter("purchased")!=null && !request.getParameter("purchased").equals(""))
              fs.setPurchasedserv(dbh.parseCurrencyAmtNoDecimal(request.getParameter("purchased")));
              
          if(request.getParameter("supplies")!=null && !request.getParameter("supplies").equals(""))
              fs.setSupplies(dbh.parseCurrencyAmtNoDecimal(request.getParameter("supplies")));
          
          if(request.getParameter("travel")!=null && !request.getParameter("travel").equals(""))
              fs.setTravel(dbh.parseCurrencyAmtNoDecimal(request.getParameter("travel")));
          
          if(request.getParameter("benefits")!=null && !request.getParameter("benefits").equals(""))
              fs.setEmployeebenefits(dbh.parseCurrencyAmtNoDecimal(request.getParameter("benefits")));
          
          if(request.getParameter("remodeling")!=null && !request.getParameter("remodeling").equals(""))
              fs.setRemodeling(dbh.parseCurrencyAmtNoDecimal(request.getParameter("remodeling")));
          
          if(request.getParameter("equipment")!=null && !request.getParameter("equipment").equals(""))
              fs.setEquipment(dbh.parseCurrencyAmtNoDecimal(request.getParameter("equipment")));
          
          if(request.getParameter("reqtotal")!=null && !request.getParameter("reqtotal").equals(""))
              fs.setTotalamt(dbh.parseCurrencyAmtNoDecimal(request.getParameter("reqtotal")));
                 
          sess.setAttribute("fsBean", fs);     
          sess.setAttribute("fundsource", "Conservation/Preservation Discretionary Grants");            
          response.sendRedirect("fs20ItemForm.pdf");  
        return;//12/12/12 added b/c illegalstateexception
      }        
//-----------------------------------------------------------------------------
      else if(todo.equalsIgnoreCase("FS-10-F HTML"))
      {
        GrantBean gb = dbh.getRecordBean(grantid);
        sess.setAttribute("thisGrant", gb);
               
        //get the library director
        OfficerDBbean odb = new OfficerDBbean();
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
        request.setAttribute("libDirectorBean", libdirectorBean); 
        
        OfficerBean pm = odb.getProjectManager(grantid);
        request.setAttribute("projManager", pm);
        
        //get total amts for fs categories
        FsBean fs = new FsBean();
        
        if(request.getParameter("professional")!=null && !request.getParameter("professional").equals(""))
              fs.setProfessional(dbh.parseCurrencyAmtNoDecimal(request.getParameter("professional")));
              
          if(request.getParameter("supportstaff")!=null && !request.getParameter("supportstaff").equals(""))
              fs.setSupportstaff(dbh.parseCurrencyAmtNoDecimal(request.getParameter("supportstaff")));
          
          if(request.getParameter("purchased")!=null && !request.getParameter("purchased").equals(""))
              fs.setPurchasedserv(dbh.parseCurrencyAmtNoDecimal(request.getParameter("purchased")));
              
          if(request.getParameter("supplies")!=null && !request.getParameter("supplies").equals(""))
              fs.setSupplies(dbh.parseCurrencyAmtNoDecimal(request.getParameter("supplies")));
          
          if(request.getParameter("travel")!=null && !request.getParameter("travel").equals(""))
              fs.setTravel(dbh.parseCurrencyAmtNoDecimal(request.getParameter("travel")));
          
          if(request.getParameter("benefits")!=null && !request.getParameter("benefits").equals(""))
              fs.setEmployeebenefits(dbh.parseCurrencyAmtNoDecimal(request.getParameter("benefits")));
          
          if(request.getParameter("remodeling")!=null && !request.getParameter("remodeling").equals(""))
              fs.setRemodeling(dbh.parseCurrencyAmtNoDecimal(request.getParameter("remodeling")));
          
          if(request.getParameter("equipment")!=null && !request.getParameter("equipment").equals(""))
              fs.setEquipment(dbh.parseCurrencyAmtNoDecimal(request.getParameter("equipment")));
          
          if(request.getParameter("reqtotal")!=null && !request.getParameter("reqtotal").equals(""))
              fs.setTotalamt(dbh.parseCurrencyAmtNoDecimal(request.getParameter("reqtotal")));
          
        request.setAttribute("fsBean", fs);                      
        request.setAttribute("fundsource", "Conservation/Preservation Discretionary Grants"); 
        RequestDispatcher rd = request.getRequestDispatcher("/discretionary/fs10fItemHtml.jsp");
        rd.forward(request, response);   
        return;//12/12/12 added b/c illegalstateexception
      }
//-----------------------------------------------------------------------------
      else if(todo.equalsIgnoreCase("FS-10-F PDF"))
      {
          GrantBean gb = dbh.getRecordBean(grantid);
          sess.setAttribute("thisGrant", gb);
                 
          //get the library director
          OfficerDBbean odb = new OfficerDBbean();
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          sess.setAttribute("libDirectorBean", libdirectorBean); 
          
          OfficerBean pm = odb.getProjectManager(grantid);
          sess.setAttribute("projManager", pm);
          
          //get total amts for fs categories
          FsBean fs = new FsBean();
          
          if(request.getParameter("professional")!=null && !request.getParameter("professional").equals(""))
              fs.setProfessional(dbh.parseCurrencyAmtNoDecimal(request.getParameter("professional")));
              
          if(request.getParameter("supportstaff")!=null && !request.getParameter("supportstaff").equals(""))
              fs.setSupportstaff(dbh.parseCurrencyAmtNoDecimal(request.getParameter("supportstaff")));
          
          if(request.getParameter("purchased")!=null && !request.getParameter("purchased").equals(""))
              fs.setPurchasedserv(dbh.parseCurrencyAmtNoDecimal(request.getParameter("purchased")));
              
          if(request.getParameter("supplies")!=null && !request.getParameter("supplies").equals(""))
              fs.setSupplies(dbh.parseCurrencyAmtNoDecimal(request.getParameter("supplies")));
          
          if(request.getParameter("travel")!=null && !request.getParameter("travel").equals(""))
              fs.setTravel(dbh.parseCurrencyAmtNoDecimal(request.getParameter("travel")));
          
          if(request.getParameter("benefits")!=null && !request.getParameter("benefits").equals(""))
              fs.setEmployeebenefits(dbh.parseCurrencyAmtNoDecimal(request.getParameter("benefits")));
          
          if(request.getParameter("remodeling")!=null && !request.getParameter("remodeling").equals(""))
              fs.setRemodeling(dbh.parseCurrencyAmtNoDecimal(request.getParameter("remodeling")));
          
          if(request.getParameter("equipment")!=null && !request.getParameter("equipment").equals(""))
              fs.setEquipment(dbh.parseCurrencyAmtNoDecimal(request.getParameter("equipment")));
          
          if(request.getParameter("reqtotal")!=null && !request.getParameter("reqtotal").equals(""))
              fs.setTotalamt(dbh.parseCurrencyAmtNoDecimal(request.getParameter("reqtotal")));
                
          sess.setAttribute("fsBean", fs);          
          sess.setAttribute("fundsource", "Conservation/Preservation Discretionary Grants"); 
          response.sendRedirect("fs10fItemForm.pdf");
        return;//12/12/12 added b/c illegalstateexception
      }
 
        
    }catch(Exception e)
    {
      System.out.println("error DiFsFormServlet "+ e.getMessage().toString());
      
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