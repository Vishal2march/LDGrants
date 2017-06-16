<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Full/Modify/No Fund Report</title>
  </head>
  <body>
  
  
  <display:table name="sessionScope.allGrants"  requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
    
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
   
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4"/>
    </display:column> 
              
    <display:column property="projcategory" title="Category" sortable="true" headerClass="sortable" />
    <display:column property="region" sortable="true" headerClass="sortable" />
    <display:column property="recommend" sortable="true" headerClass="sortable" />
         
  </display:table>
  
  
  </body>
</html>