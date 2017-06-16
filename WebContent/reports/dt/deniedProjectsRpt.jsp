<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Denied Projects Report</title>
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
      <th>Conservation/Preservation grants denied for fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/><br/>
  
  
  <display:table name="sessionScope.allGrants" id="row" requestURI="" export="true">
   
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
    
    <display:column property="title" sortable="true" headerClass="sortable" />
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    
    <display:column property="totamtreq" title="Requested" format="{0,number,$0,000}" sortable="true" headerClass="sortable" />
       
  </display:table>
  
  </body>
</html>
