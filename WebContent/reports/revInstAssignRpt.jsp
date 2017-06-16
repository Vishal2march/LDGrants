<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revInstAssignRpt.jsp
 * Creation/Modification History  :
 *     
 *     SHusak   3/20/08     Created
 *
 * Description
 * This admin jsp report lists all reviewers and each grant they were assigned for the
 * selected fiscalyear, sorted by institution name. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Report -Reviewer Assignments by Institution</title>
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
      <td height="15" />
    <tr>
      <th colspan="7">Reviewer Assignments for selected Fiscal Year <c:out value="${fybean.year}" /> sorted by institution</th>
    </tr>
  </table>
  <br/><br/>
  
  
  <table width="100%" border="1" class="boxType" summary="for layout only">
    <tr>
      <th>Institution</th><th>Project Number</th><th>Title</th>
      <th>Rev ID</th><th>Reviewer</th><th>Interest</th>
      <th>Rating complete</th>
    </tr>
    
    <c:forEach var="row" items="${allRev}" >
    <tr>
      <td><c:out value="${row.reviewerAssigns[0].instname}" /></td>       
      <td>030<fmt:formatNumber value="${row.reviewerAssigns[0].fccode}" />
          -<fmt:formatNumber value="${row.reviewerAssigns[0].fycode}" pattern="##" minIntegerDigits="2" />
          -<fmt:formatNumber value="${row.reviewerAssigns[0].projseqnum}" pattern="####" minIntegerDigits="4" /></td>
      <td><c:out value="${row.reviewerAssigns[0].title}" /></td>
      <td><c:out value="${row.revid}" /></td>
      <td><c:out value="${row.salutation}" /> <c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
      <td><c:out value="${row.interest}" /></td>     
      <td><c:out value="${row.reviewerAssigns[0].ratingcomp}" /></td>
    </tr>
      
    </c:forEach>
  </table>
  
  
  
  </body>
</html>
