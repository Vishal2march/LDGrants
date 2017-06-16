<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>LGRMIF Grant Applications</title>
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <th align="center">LGRMIF <c:out value="${rptname}"/> for
      fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/>
  
  <c:choose>
  <c:when test="${cooperative=='true'}">
  
  
  <display:table name="sessionScope.allGrants"  requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
    
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
   
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" />
    </display:column> 
              
    <display:column property="projcategory" title="Category" sortable="true" headerClass="sortable" />
    <display:column property="region" sortable="true" headerClass="sortable" />
    
    <display:column property="totamtreq" title="Amount Requested" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="totamtappr" title="Amount Approved" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
          
    <display:column property="cooperatives"/>
    
  </display:table>
  
  
  </c:when>
  <c:otherwise>
  
  <display:table name="sessionScope.allGrants"  requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
    
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
   
    <display:column title="ProjectNumber" >
          05<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4"/>
    </display:column> 
              
    <display:column property="projcategory" title="Category" sortable="true" headerClass="sortable" />
    <display:column property="region" sortable="true" headerClass="sortable" />
    
    <display:column property="totamtreq" title="Amount Requested" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="totamtappr" title="Amount Approved" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
          
  </display:table>
  
  
  </c:otherwise>
  </c:choose>
  
  </body>
</html>
