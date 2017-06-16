<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  awardListRpt.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       3/14/08     Created
 *
 * Report lists all DI awards for selected fiscal year, and amount approved for each award, 
 * sorted by inst name.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
    <title>Award List</title>
  </head>
  <body>
  
  
  <table width="100%">
    <tr>
      <td>New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th>Conservation/Preservation Discretionary Aid award list for fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/><br/>
  
  <table width="100%" summary="for layout only">
    <tr>
      <td><b>Institution</b></td><td><b>Title</b></td><td><b>City</b></td>
      <td><b>County</b></td><td><b>Award Amount</b></td>
    </tr>
    
    <c:set var="totfy" value="0" />
    <c:forEach var="row" items="${allGrants}" >
      <tr>
        <td><c:out value="${row.instName}" /></td>
        <td><c:out value="${row.title}" /></td>
        <td><c:out value="${row.city}" /></td>
        <td><c:out value="${row.county}" /></td>
        <td><fmt:formatNumber value="${row.totamtappr}" type="currency" maxFractionDigits="0" /></td>
      </tr>
      <c:set var="totfy" value="${totfy + row.totamtappr}" />
    </c:forEach>
      <tr>
        <td colspan="4"><b>Total</b></td>
        <td><b><fmt:formatNumber value="${totfy}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>
      
  </table>
    
  </body>
</html>
