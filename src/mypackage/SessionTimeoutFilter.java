/******************************************************************************

 * @author  : Walter Hamm
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SessionTimeoutFilter.java
 * Creation/Modification History  :
 *
 * WH                Created
 * SH   3/20/07      Modified
 *
 * Description
 * This class will check for the userid attribute in the session.  If the attribute
 * is not found, then the session has timed out and user will be rerouted.  This class
 * is included as a filter in web.xml so that it will run when any file is called.
 * Note: sometimes gives an error 'response has already been committed', maybe related
 * to this version using tiles for all pages (?).  Added this same code to the LayoutActionBean
 * which is called for each tile layout, and the error seems to be resolved. 
 *****************************************************************************/

package mypackage;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionTimeoutFilter implements Filter 
{
  public SessionTimeoutFilter()
  {
  }
  
  private FilterConfig filterConfig = null;
  public void init(FilterConfig filterConfig) 
    throws ServletException {
    this.filterConfig = filterConfig;
  }
  public void destroy() {
    this.filterConfig = null;
  }

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
  {
  
  try
  {
       
    HttpServletRequest request = (HttpServletRequest)servletRequest;
    HttpServletResponse response = (HttpServletResponse)servletResponse;
    HttpSession session = request.getSession();    
    //System.out.println("inside sessionTimeoutFilter ");   
    
    boolean userID = (session.getAttribute("lduser") != null); //attr is created during login
    if (!userID && session.isNew())
    {
      
      System.out.println("SESSION TIMED OUT");
      response.reset();
      response.sendRedirect("/LDGrants/error.jsp?sessionTime=true");
      return; //return so that we do not chain to other filters
    }
    
    // allow others filter to be chained
    chain.doFilter(request, response);
    } 
    catch(IOException io)
    {System.out.println(io.toString());}
    catch(ServletException se)
    {System.out.println(se.toString());}
  }
}