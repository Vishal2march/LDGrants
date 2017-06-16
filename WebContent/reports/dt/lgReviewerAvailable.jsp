<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Reviewer Availability Report</title>
  </head>
  <body>
  
  <h5>Reviewer availability for fiscal year</h5>
  
  <display:table name="sessionScope.allRev" requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
   
    <display:column title="Reviewer" sortable="true" headerClass="sortable" sortProperty="lname">
        <c:out value="${row.fname}"/> <c:out value="${row.lname}"/>
    </display:column>
  
    <display:column property="title" />
    <display:column property="affiliation" sortable="true" headerClass="sortable" />
    
    <display:column title="Available Review" sortable="true" headerClass="sortable" sortProperty="available">
        <c:choose>
        <c:when test="${row.available==0}">No</c:when>
        <c:when test="${row.available==1}">Yes</c:when>
        <c:when test="${row.available==2}">No response</c:when>
        </c:choose>
    </display:column>
    <display:column property="comment" />
    
  </display:table>
  
  </body>
</html>