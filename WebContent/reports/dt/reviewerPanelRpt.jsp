<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Reviewer/Panel Assignments</title>
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <th align="center">Reviewer/Panel Assignments for
      fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/>
  
  
  <display:table name="sessionScope.allRevPanels" requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
                     
    <display:column property="affiliation" title="Panel Name" sortable="true" headerClass="sortable" />
    
    <display:column property="description" headerClass="sortable" />
             
    <display:column title="Reviewer" sortable="true" sortProperty="lname">
      <c:out value="${row.fname}" /> <c:out value="${row.lname}" />
    </display:column>      
     
  </display:table>
  
  
  </body>
</html>
