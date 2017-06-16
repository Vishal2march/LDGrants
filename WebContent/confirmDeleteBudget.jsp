<%--
 * @author  Stefanie Husak
 * @version 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  confirmDeleteBudget.jsp
 * Creation/Modification History  :
 *
 *     SHusak    12/5/11     Created
 *
 * Description
 * This page allows the applicant to confirm that they want to delete
 * the selected budget record. Diff from confirmDelete.jsp -this one uses a budgetdeletebean
 * to display the record to be deleted.  The original jsp throws server errors if the 'desc'
 * variable passed as a param contains an apostrophe (scripting error)
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  
  <h4>*Confirm - Delete Budget Record*</h4>
  
      <form method="POST" action="DeleteItemServlet"> 
  
      <table width="50%" summary="for layout only">
        <tr>
          <td>Do you want to delete the budget record for <c:out value="${deleteBean.description}" /> ?</td>
        </tr>
        <tr>
          <td><input type="SUBMIT" value="Delete"/>
              <input type="HIDDEN" name="id" value='<c:out value="${deleteBean.id}" />'  />
              <input type="HIDDEN" name="tab" value='<c:out value="${deleteBean.tab}" />' /> 
              <input type="hidden" name="p" value='<c:out value="${deleteBean.module}" />' />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
          </td>
        </tr>
      </table>        
      </form>
    
  
  </body>
</html>