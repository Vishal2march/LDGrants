<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  selectRegionDecision.jsp
 * Description
 * This page is available to lgrmif reviewers who are also rao's (see reviewer table
 * property for is_employee).  rao's can search for all grants by fy and region, then
 * select a grant to view/update decision notes.
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h5>Grant Decisions by Region</h5>
  
  <form action="lgReviewer.do?item=regionsearch" method="post">
  <p>View grant decisions by fiscal year and region:<br/>
   <select name="fycode">
      <c:forEach var="row" items="${dropDownList}">
      <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
      </c:forEach>       
   </select>
   
   <select name="regiontype">
      <c:forEach var="row" items="${allRegions}">
      <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
      </c:forEach>       
   </select>
   <input type="submit" value="Search"/>
   </p></form>
  
  <br/><hr/><br/>
  
  
  <logic:notEmpty name="allGrants">
  <table width="90%" summary="for layout only">
    <tr>
        <td><b>Decision Notes</b></td><td><b>Project Number</b></td>
        <td><b>Institution</b></td><td><b>Category</b></td><td><b>Panel Status</b></td>
        <td><b>All At-Home Comments</b></td><td><b>All At-Home Scores</b></td>
    </tr>
  
    <c:forEach var="row" items="${allGrants}">
        <c:url var="decurl" value="lgReviewer.do">
            <c:param name="item" value="selectdecision"/>
            <c:param name="pgid" value="${row.panelgrantId}"/>
            <c:param name="id" value="${row.grantid}"/>
        </c:url>
        <c:url var="commurl" value="lgReports.do">
            <c:param name="i" value="revcomment" />
            <c:param name="gid" value="${row.grantid}"/>
        </c:url>
        <c:url var="rateurl" value="lgReports.do">
            <c:param name="i" value="revrating" />
            <c:param name="gid" value="${row.grantid}"/>
        </c:url>
        
        
        <tr>
          <td><a href='<c:out value="${decurl}"/>'>Decision Notes</a></td>
          <td>05<c:out value="${row.fccode}"/>-<c:out value="${row.fycode}"/>
                -<fmt:formatNumber value="${row.projseqnum}" minIntegerDigits="4" pattern="####"/></td>
          <td><c:out value="${row.instName}"/></td>
          <td><c:out value="${row.projcategory}"/></td>      
          <td><c:out value="${row.status}"/></td>
          
          <c:choose>
          <c:when test="${row.status=='n' || row.status=='N'}">
              <td>Comments not available</td>
              <td>Scores not available</a></td>
          </c:when>
          <c:otherwise>
            <td><a href='<c:out value="${commurl}"/>' target="_blank">Comments</a></td>
            <td><a href='<c:out value="${rateurl}"/>' target="_blank">Scores</a></td>
          </c:otherwise>
          </c:choose>
          
        </tr>    
    </c:forEach>
  </table>
  </logic:notEmpty>
  
  
  </body>
</html>