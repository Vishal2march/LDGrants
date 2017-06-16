<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  revAssignments.jsp
 * Creation/Modification History  :
 *
 *     SH       8/1/07     Created
 *
 * Description
 * This page displays all grants that have been assigned to this reviewer. Info includes
 * the proj num, institution, and a link to get all data about the selected grant.
 * used for CO/DI/LIT. Lg has different assign.jsp b/c too many differences.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <c:set var="fcpre" value="03"/> 
  <h5>Reviewer's Grant Assignments</h5>  
  
  <form action="assignmentSearch.do?i=findassign" method="post">
  <p>View all grants assigned to you for fiscal year:<br/>
   <select name="fycode">
      <c:forEach var="row" items="${dropDownList}">
      <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
      </c:forEach>       
   </select>
   <input type="hidden" name="pr" value='<c:out value="${p}"/>'/>
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
      <td><b>Title</b></td>        
      <td><b>Rating Form</b></td>
      <td><b>Rating Submitted</b></td>
    </tr>      
    
    <c:forEach var="assignBean" items="${allAssigned}" >
    
      <c:choose >            
      <c:when test="${param.p=='co'}">      
          <c:url var="grantURL" value="coReviewerForms.do">
            <c:param name="item" value="grant" />
            <c:param name="id" value="${assignBean.graid}" />
            <c:param name="assign" value="${assignBean.assignid}" />
          </c:url>
        
          <c:url var="ratingURL" value="coReviewerForms.do">
            <c:param name="item" value="rating" />
            <c:param name="id" value="${assignBean.graid}" />
            <c:param name="assign" value="${assignBean.assignid}" />
            <c:param name="p" value="${param.p}" /> 
          </c:url>
      </c:when>
      
      <c:when test="${param.p=='di'}">
          <c:url var="grantURL" value="diReviewerForms.do">
            <c:param name="item" value="grant" />
            <c:param name="id" value="${assignBean.graid}" />
            <c:param name="assign" value="${assignBean.assignid}" />
          </c:url>
          
          <c:url var="ratingURL" value="diReviewerForms.do">
            <c:param name="item" value="rating" />
            <c:param name="id" value="${assignBean.graid}" />
            <c:param name="assign" value="${assignBean.assignid}" />
            <c:param name="p" value="${param.p}" />
          </c:url>      
      </c:when>
      
      <c:when test="${param.p=='li'}">
          <c:url var="grantURL" value="liReviewer.do">
            <c:param name="item" value="grant" />
            <c:param name="id" value="${assignBean.graid}" />
            <c:param name="assign" value="${assignBean.assignid}" />
          </c:url>
          
          <c:url var="ratingURL" value="liReviewer.do">
            <c:param name="item" value="rating" />
            <c:param name="id" value="${assignBean.graid}" />
            <c:param name="assign" value="${assignBean.assignid}" />
            <c:param name="p" value="${param.p}"/>
          </c:url>
      </c:when>
      </c:choose>
                
      <tr>
        <td><a href='<c:out value="${grantURL}" />' >
          <c:out value="${fcpre}"/><fmt:formatNumber value="${assignBean.fccode}" minIntegerDigits="2"/>-
          <fmt:formatNumber value="${assignBean.fycode}" minIntegerDigits="2" />-
          <fmt:formatNumber value="${assignBean.projseqnum}" pattern="####" minIntegerDigits="4" /></a></td>
        <td><c:out value="${assignBean.instname}" /></td>
        <td><c:out value="${assignBean.title}" /></td>
        <td><a href='<c:out value="${ratingURL}" />' >Form</a></td>
        <td><c:choose>
            <c:when test="${assignBean.ratingcomp=='true'}">Yes</c:when>
            <c:otherwise>No</c:otherwise></c:choose></td>
      </tr>    
    </c:forEach>    
  
  </table>    
  
  </body>
</html>
