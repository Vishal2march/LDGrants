<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>deleteBudgetSummary</title>
  </head>
  <body>
  
  <h4>*Confirm - Delete Budget Summary Record*</h4>
  
      <form method="POST" action="budgetSummaryNav.do?t=delete"> 
  
      <table width="50%" summary="for layout only">
        <tr>
          <td>Do you want to delete the budget summary record?</td>
        </tr>
        <tr>
          <td><input type="SUBMIT" value="Delete"/>
              <input type="HIDDEN" name="id" value='<c:out value="${deleteBean.id}" />'  />
              <input type="HIDDEN" name="tab" value='<c:out value="${deleteBean.tab}" />' /> 
              <input type="hidden" name="p" value='<c:out value="${deleteBean.module}" />' />
              <input type="hidden" name="narr" value='<c:out value="${deleteBean.narrativeTypeId}" />' />
              <input type="BUTTON" value="Cancel" onclick="javascript:history.go(-1);" />
          </td>
        </tr>
      </table>        
      </form>
      
  
  
  
  </body>
</html>