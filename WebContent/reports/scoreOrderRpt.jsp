<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  scoreOrderRpt.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       11/2/07     Created
 *
 * Description
 * This admin jsp report lists all coord grants received for the selected fiscalyear and
 * the average score for grant, listed in score order. 1/18/08 modified to include amtreq, amtappr. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Score order report</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
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
      <th align="center"><c:out value="${program}" /> Aid scores report for grants received during
      fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/>
  
  <table width="100%" summary="for layout only">
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Avg Score</th>
      <th>Amount Requested</th><th>Amount Approved</th>
    </tr>
    <c:set var="totreq" value="0" />
    <c:set var="totappr" value="0" />
    
    <c:forEach var="row" items="${allGrants}" >
    <tr>
      <td>030<c:out value="${row.fccode}" />-
        <fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-
        <fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
      <td><c:out value="${row.title}" /></td>
      <td><c:out value="${row.instName}" /></td>
      <td><c:out value="${row.score}" /></td>
      <td><fmt:formatNumber value="${row.totamtreq}" minFractionDigits="0" type="currency" /></td>
      <td><fmt:formatNumber value="${row.totamtappr}" minFractionDigits="0" type="currency" /></td>
    </tr>    
    
    <c:set var="totreq" value="${totreq + row.totamtreq}" />
    <c:set var="totappr" value="${totappr + row.totamtappr}" />
    </c:forEach>
    
    <tr>
      <td></td><td></td><td></td><td></td>
      <td><b><fmt:formatNumber value="${totreq}" minFractionDigits="0" type="currency" /></b></td>
      <td><b><fmt:formatNumber value="${totappr}" minFractionDigits="0" type="currency" /></b></td>
    </tr>
    
  </table>
  
  </body>
</html>
