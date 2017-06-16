<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  amtApprStatutory.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       11/2/07     Created
 *
 * This admin jsp report displays all statutory grants received for selected fiscalyear
 * and total amt appr for each. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Report - Statutory Aid Amount Approved</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
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
      <th>Statutory Aid Amount Approved for selected Fiscal Year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  
  
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><b>Project Number</b></td><td><b>Institution</b></td>
      <td><b>Amount Approved</b></td>
    </tr>
    <c:forEach var="row" items="${allGrants}" >
    <tr>  
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" value="${row.fccode}" />
            -<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber  value="${row.projseqnum}" pattern="####" minIntegerDigits="4"/></td>
      <td><c:out value="${row.instName}" /></td>
      <td><fmt:formatNumber value="${row.totamtappr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    </c:forEach>    
    </table>
  
  </body>
</html>
