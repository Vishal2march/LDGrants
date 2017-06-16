<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Panel Score Recommendation</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>
  
  <display:table name="sessionScope.allRevAssign"  requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
        
    <display:column title="ProjectNumber" group="1">
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" />
    </display:column> 
    <display:column property="instname" group="2" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="projcategory" title="Category" sortable="true" headerClass="sortable" />
    <display:column property="totamtreq" group="3" title="Requested" format="{0,number,$0,000}" />
   
    <display:column property="name" title="Reviewer" sortable="true" headerClass="sortable" />
    <display:column property="recommendation" />    
    <display:column property="recommendamt" title="Recommend Amt" format="{0,number,$0,000}"/>
    
    <display:column property="score" />   
    <display:column property="ratingcomp" title="Rating Submitted" />
  </display:table>
  
  </body>
</html>