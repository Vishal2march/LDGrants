<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Reviewer Assignments Report</title>
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
    <tr>
      <th>Reviewer Assignments for selected Fiscal Year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/><br/>
  
   
  <display:table name="sessionScope.allRev" id="row" requestURI="" defaultsort="2" export="true">
   
    <display:column property="revid" title="Reviewer ID" />
    <display:column title="Reviewer" sortable="true" sortProperty="lname">
      <c:out value="${row.fname}" /> <c:out value="${row.lname}" />
    </display:column>
    
   <%-- <c:set var="rbeans" value="${row.reviewerAssigns}" />    
    
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${rbeans[0].fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${rbeans[0].fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${rbeans[0].projseqnum}"  />
    </display:column> --%>
    
    <display:column property="reviewerAssigns[0].title" title="Title" sortable="true" headerClass="sortable" />
    <display:column property="reviewerAssigns[0].instname" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="reviewerAssigns[0].ratingcomp" title="Rating Complete" />
  </display:table>
  
  
  </body>
</html>
