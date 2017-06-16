<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revAtHomeAssign.jsp
 * Creation/Modification History  :
 *
 *     SH       11/1/09     Created
 *
 * Description
 * This page displays all grants that have been assigned to this reviewer. Info includes
 * the proj num, institution, and a link to get all data about the selected grant.
 * used for LG only.
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>    
  
  <c:set var="fcpre" value="05"/> 
  <h5>Reviewer's Grant Assignments</h5>
  
  
  <form action="lgReviewer.do?item=findassign" method="post">
  <p>View all grants assigned to you for fiscal year:<br/>
   <select name="fycode">
      <c:forEach var="row" items="${dropDownList}">
      <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
      </c:forEach>       
   </select>
   <input type="hidden" name="pr" value="lg"/>
   <input type="submit" value="Search"/>
   </p></form>
  
  <table width="95%" align="center" summary="for layout only">  
    <tr>
      <td colspan="5">Click on the Project Number to view information about this grant proposal. Click
      on the Rating/Evaluation Form to complete your evaluation for this proposal.</td>
    </tr>
    <tr>
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>       
      <td><b>Category</b></td>        
      <td><b>Evaluation Form</b></td>
      <td><b>Evaluation Submitted</b></td>
    </tr>
    
    <c:forEach var="assignBean" items="${allAssigned}" >
          
      <c:url var="grantURL" value="lgReviewer.do">
        <c:param name="item" value="grant" />
        <c:param name="id" value="${assignBean.graid}" />
        <c:param name="assign" value="${assignBean.assignid}" />
      </c:url>
    
      <c:url var="ratingURL" value="lgReviewer.do">
        <c:param name="item" value="rating" />
        <c:param name="id" value="${assignBean.graid}" />
        <c:param name="assign" value="${assignBean.assignid}" />
        <c:param name="p" value="${param.p}" /> 
      </c:url>
  
     <tr>
        <td><a href='<c:out value="${grantURL}" />' >
          <c:out value="${fcpre}"/><fmt:formatNumber value="${assignBean.fccode}" minIntegerDigits="2"/>-
          <fmt:formatNumber value="${assignBean.fycode}" minIntegerDigits="2" />-
          <fmt:formatNumber value="${assignBean.projseqnum}" pattern="####" minIntegerDigits="4" /></a></td>
        <td><c:out value="${assignBean.instname}" /></td>
        <td><c:out value="${assignBean.title}" /></td>
        <td><a href='<c:out value="${ratingURL}" />' >Form</a></td>
        <td><c:choose><c:when test="${assignBean.conflictinterest=='true'}">Conflict Interest</c:when>
            <c:when test="${assignBean.ratingcomp=='true'}">Yes</c:when>
            <c:otherwise>No</c:otherwise></c:choose></td>
      </tr>    
    </c:forEach>    
  
  </table>    
    
  </body>
</html>