<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytagGroup.css" />
    <title>Applications by region</title>
  </head>
  <body>
  
  <h5>LGRMIF applications by region</h5>
  
  <display:table name="sessionScope.allGrants"  requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
    
    <display:column property="title" title="Region" group="1" />
    
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
   
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" />
    </display:column> 
              
    <display:column property="projcategory" title="Category" sortable="true" headerClass="sortable" />
    
    <display:column property="totamtreq" title="Amount Requested" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="totamtappr" title="Amount Approved" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
   
  </display:table>
  
  
  </body>
</html>