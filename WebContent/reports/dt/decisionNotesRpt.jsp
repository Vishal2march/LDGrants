<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Decision Notes</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>
  
  <h5>Decision Notes</h5>
  
  <display:table name="sessionScope.allDecNotes" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator" >
   
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 

    <display:column property="instid"/>
    <display:column property="instname" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="score" title="Score" /> 
    <display:column property="recommendation" title="Recommend" />  
    <display:column property="totamtreq" title="Requested" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="recommendamt" title="Awarded" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    
    <display:column property="decisionNotes" />
    <display:column property="name" title="PD Name" />
    <display:column property="email" title="Email" />
    <display:column property="rmoName" title="RMO Name" />
    <display:column property="rmoEmail" title="RMO Email" />
        
  </display:table>    
  
  
  
  </body>
</html>