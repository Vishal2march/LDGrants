<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Grant Applications for Fiscal Year</title>
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <th align="center"><c:out value="${program}" /> Aid report listing new applications submitted for
      fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/>
  
  
  <display:table name="sessionScope.allGrants" requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
   
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4"/>
    </display:column> 
              
    <display:column property="title" sortable="true" headerClass="sortable" />
    
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    
    <display:column property="city" sortable="true" headerClass="sortable" />
    <display:column property="county" sortable="true" headerClass="sortable" />
    
    <display:column property="totamtreq" title="Requested" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="totamtappr" title="Awarded" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
        
  </display:table>
  
  
  </body>
</html>
