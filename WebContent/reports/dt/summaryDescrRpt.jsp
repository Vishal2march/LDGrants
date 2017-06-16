<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Summary Description Report</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>  
  
  <h5>Summary Description Report</h5>  
  
  <display:table name="sessionScope.allGrants" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator" >
   
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
    
    <display:column property="projcategory" title="Category" sortable="true" headerClass="sortable" />
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="summaryDescr" title="Summary" sortable="true" headerClass="sortable" />
          
  </display:table>    
  
  </body>
</html>