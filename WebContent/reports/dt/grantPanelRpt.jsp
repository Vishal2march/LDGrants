<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Grant/Panel Assignments</title>
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <th align="center">Grant/Panel Assignments for fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/>
  
  
  <display:table name="sessionScope.allGrantPanels" requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
                     
    <display:column property="affiliation" title="Panel Name" sortable="true" headerClass="sortable" />
             
    <display:column property="institution" sortable="true" headerClass="sortable" />
    <display:column property="projcategory" headerClass="sortable" />                     
    <display:column title="Project Number">
      05<c:out value="${row.fccode}" />-<c:out value="${row.year}" />-<fmt:formatNumber value="${row.projectNum}" pattern="####" minIntegerDigits="4"/>
    </display:column>      
     
  </display:table>
  
  
  </body>
</html>
