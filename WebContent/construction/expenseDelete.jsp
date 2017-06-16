<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction</title>
  </head>
  <body>
  
  <h4>*Confirm - Delete Final Expense Record*</h4>
  
      <form method="POST" action="cnFinalExpenseNav.do?i=deleteexp"> 
  
      <table width="50%" summary="for layout only">
        <tr>
          <td>Do you want to delete the expense record for <c:out value="${deleteBean.description}" /> ?</td>
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