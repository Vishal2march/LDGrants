<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:choose >
  <c:when test="${param.usermod=='admin'}">
  
    <table width="70%" summary="for layout only">
      <tr>
        <td valign="top">       
        <a href="AdminBudgetSelect?tab=1&p=lg">1. Professional Salaries</a><br/>
        <a href="AdminBudgetSelect?tab=2&p=lg">2. Support Staff Salaries</a><br/>      
        <a href="AdminBudgetSelect?tab=5&p=lg">3. Purchased Services</a><br/>
        <a href="AdminBudgetSelect?tab=7&p=lg">4. Supplies and Materials</a><br/>
        <a href="AdminBudgetSelect?tab=8&p=lg">5. Travel Expenses</a><br/>
        <a href="AdminBudgetSelect?tab=9&p=lg">6. Employee Benefits</a><br/>
        <a href="AdminBudgetSelect?tab=6&p=lg">7. Purchased Services (BOCES)</a><br/>
        <a href="AdminBudgetSelect?tab=4&p=lg">8. Minor Remodeling</a><br/>
        <a href="AdminBudgetSelect?tab=3&p=lg">9. Equipment</a>
        </td>             
      </tr>
    </table>
  
  </c:when>
  <c:otherwise >
  
    <table width="70%" summary="for layout only">
      <tr>
        <td valign="top">       
        <a href="AdminBudgetSelect?tab=1&p=lgr">1. Professional Salaries</a><br/>
        <a href="AdminBudgetSelect?tab=2&p=lgr">2. Support Staff Salaries</a><br/>      
        <a href="AdminBudgetSelect?tab=5&p=lgr">3. Purchased Services</a><br/>
        <a href="AdminBudgetSelect?tab=7&p=lgr">4. Supplies and Materials</a><br/>
        <a href="AdminBudgetSelect?tab=8&p=lgr">5. Travel Expenses</a><br/>
        <a href="AdminBudgetSelect?tab=9&p=lgr">6. Employee Benefits</a><br/>
        <a href="AdminBudgetSelect?tab=6&p=lgr">7. Purchased Services (BOCES)</a><br/>
        <a href="AdminBudgetSelect?tab=4&p=lgr">8. Minor Remodeling</a><br/>
        <a href="AdminBudgetSelect?tab=3&p=lgr">9. Equipment</a><br/>
        </td>             
      </tr>
    </table>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>
