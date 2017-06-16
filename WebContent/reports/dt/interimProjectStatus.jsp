<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Interim Project Status</title>
  </head>
  <body>
    
  <table width="100%">
    <tr>
      <td>New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/></td>
    </tr>
    <tr>
      <td height="15" />
    </tr>
    <tr>
      <th>Literacy Interim Project Status</th>
    </tr>
  </table>
  <br/><br/>
  
  
  <display:table name="sessionScope.allGrants" decorator="org.displaytag.decorator.TotalTableDecorator" id="row" requestURI="" export="true" >
   
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
              
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="grantstatusBean.interimrptyn" title="Interim Report" sortable="true" headerClass="sortable" />
    <display:column property="grantstatusBean.interimauthyn" title="Interim Authorization" sortable="true" headerClass="sortable" />
     
  </display:table>
  
    </body>
</html>