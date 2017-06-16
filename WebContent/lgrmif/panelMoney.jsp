<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>panelMoney</title>
  </head>
  <body>
  
  <h4>Panel Amount Available/Awarded</h4>
  
  <table summary="for layout only" width="90%">
    <tr>
        <td><b>Year</b></td><td><b>Panel</b></td><td><b>Amount Available</b></td>
        <td><b>Amount Awarded</b></td><td><b>Difference</b></td>
    </tr>    
      
    <c:forEach var="row" items="${panelAmts}">
        <tr>
            <td><c:out value="${row.fycode}"/></td>
            <td><c:out value="${row.name}"/></td>
            <td><fmt:formatNumber value="${row.amtavailable}" type="currency" minFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${row.amtapproved}" type="currency" minFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${row.amtdifference}" type="currency" minFractionDigits="0" /></td>
        </tr>
    </c:forEach>    
  </table>
  
  </body>
</html>