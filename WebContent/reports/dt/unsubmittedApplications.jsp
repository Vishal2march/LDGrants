<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Unsubmitted Applications</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <th align="center">Unsubmitted Applications for
      fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/>
  
  <display:table name="sessionScope.allGrants"  requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
    
    <display:column title="ProjectNumber" >
          <c:out value="${prefc}"/><fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" />
    </display:column> 
    
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    
    <display:column property="city" sortable="true" headerClass="sortable" />
    <display:column property="county" sortable="true" headerClass="sortable" />
    <display:column property="title" title="Created By" sortable="true" headerClass="sortable" />
        
 </display:table>
  
  </body>
</html>