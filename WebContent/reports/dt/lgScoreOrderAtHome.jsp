<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Score Report</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>
  
  <h4>LGRMIF Score Report (based on At-Home evaluation score average)</h4>
  
  <display:table name="sessionScope.allRevAssign" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="" export="true">
    <display:column title="Row" >
      <c:out value="${row_rowNum}"/>
    </display:column>
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" />
    </display:column> 
    
    <display:column property="instname" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="title" title="Panel" sortable="true" headerClass="sortable" />
    <display:column property="projcategory" title="Category" sortable="true" headerClass="sortable" />
    <display:column property="score" sortable="true" headerClass="sortable" />
    <display:column property="totamtreq" title="Requested" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
        
  </display:table>
  
  </body>
</html>