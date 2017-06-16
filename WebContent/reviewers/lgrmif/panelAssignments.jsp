<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>panelAssignments</title>
  </head>
  <body>
  
  
  <h5>Panel Assignments</h5>
  
  <form action="lgReviewer.do?item=panelsearch" method="post">
  <p>View all grants assigned to your panel for fiscal year:<br/>
   <select name="fycode">
      <c:forEach var="row" items="${dropDownList}">
      <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
      </c:forEach>       
   </select>
   <input type="submit" value="Search"/>
   </p></form>
   
   
   <c:if test="${panel!=null}">
    The following grants were assigned to your panel: <c:out value="${panel.name}"/><br/>
    Access status for this panel is: <c:out value="${panel.status}"/>
   
  <table width="100%" summary="for layout only">
    <tr>
      <td><b>Final Deliberation Form</b></td><td><b>Project Number</b></td>
      <td><b>Institution</b></td><td><b>Category</b></td>
      <td><b>All At-Home Comments</b></td><td><b>All At-Home Scores</b></td><td><b>Amount Awarded</b></td>
    </tr>
    
    <c:forEach var="row" items="${panelGrants}">
        <c:url var="purl" value="lgReviewer.do?item=delibform">
            <c:param name="id" value="${row.grantid}"/>
            <c:param name="pgid" value="${row.panelgrantId}"/>
        </c:url>
        <c:url var="commurl" value="lgReports.do">
            <c:param name="i" value="revcomment" />
            <c:param name="gid" value="${row.grantid}"/>
        </c:url>
        <c:url var="rateurl" value="lgReports.do">
            <c:param name="i" value="revrating" />
            <c:param name="gid" value="${row.grantid}"/>
        </c:url>
        <c:url var="budgeturl" value="lgReviewer.do?item=budget">
            <c:param name="id" value="${row.grantid}"/>
        </c:url>
        
        <tr>
          <td><a href='<c:out value="${purl}"/>'>Deliberation Form</a></td>
          <td>05<c:out value="${row.fccode}"/>-<c:out value="${row.fycode}"/>
                -<fmt:formatNumber value="${row.projseqnum}" minIntegerDigits="4" pattern="####"/></td>
          <td><c:out value="${row.instName}"/></td>
          <td><c:out value="${row.projcategory}"/></td>
          
          <c:choose>
          <c:when test="${panel.status=='n' || panel.status=='N'}">
            <td>Comments not available</td>
            <td>Scores not available</td>
          </c:when>
          <c:otherwise>
            <td><a href='<c:out value="${commurl}"/>' target="_blank">Comments Report</a></td>
            <td><a href='<c:out value="${rateurl}"/>' target="_blank">Scores Report</a></td>
          </c:otherwise>
          </c:choose>
          <td><a href='<c:out value="${budgeturl}"/>'>Amount Awarded</a></td>
          <%--<td><c:out value="${row.scoreStr}"/></td>--%>
        </tr>
    </c:forEach>  
  </table>  

   </c:if>
  
  </body>
</html>