<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Awards By Region</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <th align="center">LGRMIF awards report by region for
      fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/>
  
  <display:table name="sessionScope.allGrants"  requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
    <display:column property="title" title="Region" sortable="true" headerClass="sortable" />
    <display:column property="countapp" title="Number of awards" sortable="true" headerClass="sortable" total="true" />
    <display:column property="totamtreq" title="Amount Requested" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="totamtappr" title="Amount Approved" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />        
  </display:table>  
  
  </body>
</html>