<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revAvailAssignRpt.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       11/2/07     Created
 *
 * Description
 * This admin jsp report will list all reviewers, the number of grants accepted, and the number of
 * grants they have been  assigned for the selected fiscalyear. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Report - Reviewer Availability and Assignments</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>
  
  
  <table width="100%">
    <tr>
      <th>Reviewer Availability for Fiscal Year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  
  <c:choose>
  <c:when test="${lgrmif=='true'}">
  
  
    <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><b>Name</b></td><td><b>Interest</b></td>
      <td><b>Accepted Review?</b></td>
      <td><b>Comment</b></td>
    </tr>
    <tr>
      <td><b>Title</b></td><td><b>Affiliation</b></td>
    </tr>
    <tr>
      <td colspan="5" height="15"><hr/></td>
    </tr>
      
    
    <c:forEach var="row" items="${allRev}" >
    <tr>  
      <td><c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
      <td><c:out value="${row.interest}" /></td>
      <td><c:if test="${row.reviewerAssigns[0].numaccepted >0}" >Yes</c:if>
          <c:if test="${row.reviewerAssigns[0].numaccepted ==0}" >No</c:if></td>
      <td><c:out value="${row.reviewerAssigns[0].comment}" /></td>      
    </tr>
    <tr>
      <td><c:out value="${row.title}" /></td>
      <td><c:out value="${row.affiliation}" /></td>
    </tr>
    <tr>
      <td colspan="5" height="15"><hr/></td>
    </tr>
    </c:forEach>    
    </table>
  
  
  </c:when>
  <c:otherwise>
  
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td><b>Name</b></td><td><b>Interest</b></td>
      <td><b>Number Accepted</b></td><td><b>Number Assigned</b></td>
      <td><b>Comment</b></td>
    </tr>
    <tr>
      <td><b>Title</b></td><td><b>Affiliation</b></td>
    </tr>
    <tr>
      <td colspan="5" height="15"><hr/></td>
    </tr>
      
    
    <c:forEach var="row" items="${allRev}" >
    <tr>  
      <td><c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
      <td><c:out value="${row.interest}" /></td>
      <td><c:out value="${row.reviewerAssigns[0].numaccepted}" /></td>
      <td><c:out value="${row.reviewerAssigns[0].numassigned}" />
          <c:if test="${row.reviewerAssigns[0].numassigned > row.reviewerAssigns[0].numaccepted}" >
          <font color="Red">*Warning*</font>
          </c:if>
      </td>
      <td><c:out value="${row.reviewerAssigns[0].comment}" /></td>      
    </tr>
    <tr>
      <td><c:out value="${row.title}" /></td>
      <td><c:out value="${row.affiliation}" /></td>
    </tr>
    <tr>
      <td colspan="5" height="15"><hr/></td>
    </tr>
    </c:forEach>    
    </table>
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>
