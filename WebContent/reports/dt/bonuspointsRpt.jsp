<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Bonus Points Report</title>
  </head>
  <body>
  
  <h5>Bonus Points for submitted applications</h5>
  
  <display:table name="sessionScope.allGrants" requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
   
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4"/>
    </display:column> 
                  
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="cooperative" sortable="true" headerClass="sortable" />
    <display:column property="inventory" sortable="true" headerClass="sortable" />
    <display:column property="electronicrecords" title="Electronic Records" sortable="true" headerClass="sortable" />
    <display:column property="emailmgmt" title="Email Mgmt" sortable="true" headerClass="sortable" />
     <display:column title="Project Manager">
        <c:out value="${row.projectManager.fname}"/> <c:out value="${row.projectManager.lname}"/>
    </display:column>
    
  </display:table>
   
  </body>
</html>