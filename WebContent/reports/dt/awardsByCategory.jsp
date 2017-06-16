<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>LGRMIF Category Report</title>
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <th align="center">LGRMIF <c:out value="${rptname}"/> report by category for
      fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/>
  
  <display:table name="sessionScope.allGrants"  requestURI="" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
    <display:column property="projcategory" title="Category" sortable="true" headerClass="sortable" />
    <display:column property="countapp" title="Number of grants" sortable="true" headerClass="sortable" total="true" />
    <display:column property="totamtreq" title="Amount Requested" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="totamtappr" title="Amount Approved" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />        
  </display:table>
    
  </body>
</html>