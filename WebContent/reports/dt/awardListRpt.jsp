<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Award List</title>
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <td>New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th>Conservation/Preservation Discretionary Aid award list for fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/><br/>
  
  <display:table name="sessionScope.allGrants" requestURI="" decorator="org.displaytag.decorator.TotalTableDecorator" export="true">
   
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
          
    <display:column property="title" sortable="true" headerClass="sortable" />
    <display:column property="city" sortable="true" headerClass="sortable" />
    <display:column property="county" sortable="true" headerClass="sortable" />
    
    <display:column property="totamtappr" title="Awarded" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
        
  </display:table>
  
  </body>
</html>
