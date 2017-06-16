<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Awards for all online grant system programs</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>
  
    
  <h5>New York State Education Department<br/>
      Awards for all available online grant system programs</h5><br/>
  
    
  <display:table name="sessionScope.allGrants" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator" >
   
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
    
    <display:column property="program" sortable="true" headerClass="sortable" group="1" />  
    <display:column property="title" sortable="true" headerClass="sortable" />
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="county" title="County" sortable="true" headerClass="sortable" />
    
    <display:column property="totamtappr" title="Awarded" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
        
  </display:table>    
  
  
  </body>
</html>
