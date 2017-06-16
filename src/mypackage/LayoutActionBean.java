/******************************************************************************

 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  LayoutActionBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This is a generic class that is used to map the actions in the strutsconfig file
 * for each of the pages that use tiles.  Note: Changed all of SL's pages to use tiles.
 * Also added code similar to SessionTimeOutFilter to eliminate the "Response has already
 * been committed" errors that came up with the use of tiles.  The body page would load on
 * the client, while the header and footer pages would display error message.
 *****************************************************************************/
package mypackage;


import javax.servlet.http.*;
import org.apache.struts.action.*;

public class LayoutActionBean extends Action
{
  
  public ActionForward execute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest,
    HttpServletResponse aResponse)
    {
    
      HttpSession session = aRequest.getSession();
      try
      {
        boolean userID = (session.getAttribute("lduser") != null); //attr is created during login
        if (!userID && session.isNew())
        {      
          System.out.println("SESSION TIMED OUT     layoutActionBean");
          aResponse.sendRedirect("error.jsp?sessionTime=true");
          
          return aMapping.findForward("fail"); // return so that we do not chain to other filters
        }  

      }catch(Exception e)
      {
        System.out.println("error layoutactionbean " + e.getMessage().toString());
      }
      
      return aMapping.findForward("success");
    }
}