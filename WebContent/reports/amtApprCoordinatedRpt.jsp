<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  amtApprCoordinatedRpt.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       11/2/07     Created
 *
 * Description
 * This jsp admin report page lists all coord grants, the total amount approved, and amt approved
 * by fiscal year for the specified fiscal year range. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Report - Coordinated Amount Approved</title>
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
      <th>Coordinated Aid amount approved for fiscal year period</th>
    </tr>
  </table>
  
  
  <table width="100%" border="1" summary="for layout only">
    <tr>
      <th width="10%">Project Number</th><th width="30%">Title</th><th width="20%">Institution</th><th>Total Appr</th>
      <c:forEach var="fycount" begin="${fybean1.fycode}" end="${fybean2.fycode}" step="1">
        <th><fmt:formatNumber value="${fycount}" minIntegerDigits="2" /></th>
      </c:forEach>
    </tr>
    
    
    
    <%--loop on all grants returned--%>
    <c:forEach var="row" items="${allGrants}">
    <tr>
      <td>030<c:out value="${row.fccode}" />-
        <fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-
        <fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
      <td><c:out value="${row.title}" /></td>
      <td><c:out value="${row.instName}" /></td>
      <td><fmt:formatNumber value="${row.totamtappr}" type="currency" maxFractionDigits="0" /></td>
      
      <%--loop over the fy columns --%>
      <c:forEach var="fycount" begin="${fybean1.fycode}" end="${fybean2.fycode}" step="1">
        <c:set var="fy" value="${fycount}"  />
        
        <td>
         <%--loop over the totalsBeans array - check if amt appr exists for this fy--%>
         <c:forEach var="totals" items="${row.totalsBeans}">
          <c:if test="${fy== totals.fycode}">
            <fmt:formatNumber value="${totals.totAmtAppr}"  />
          </c:if>
         </c:forEach>
        </td>
         
      </c:forEach>
      
    </tr>
    </c:forEach>
    
    <tr>
      <td colspan="4"><b>Totals</b></td>
      <%--loop over the fy columns --%>
      <c:forEach var="fycount" begin="${fybean1.fycode}" end="${fybean2.fycode}" step="1">
       <c:set var="fy" value="${fycount}"  />
      
      <td><b>
       <%--loop over the totals arraylist - check if total amt exists for this fy--%>
       <c:forEach var="row" items="${allTotals}">
        <c:if test="${fy== row.fycode}">
          <fmt:formatNumber value="${row.totAmtAppr}" type="currency" maxFractionDigits="0" />
        </c:if>
       </c:forEach></b>
      </td>         
    </c:forEach>
  </tr>
    
  </table>   
  
  </body>
</html>
