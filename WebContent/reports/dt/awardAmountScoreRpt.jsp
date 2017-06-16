<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>Literacy Project Amount Requested/Awarded</title>
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
      <th>Literacy Project Amount Requested/Awarded</th>
    </tr>
  </table>
  <br/><br/>
  
  
  <display:table name="sessionScope.allGrants" decorator="org.displaytag.decorator.TotalTableDecorator" id="row" requestURI="" export="true" >
   
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
       
    <display:column property="title" sortable="true" headerClass="sortable" />
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="county" title="County" />
    
    <c:set var="tbeans" value="${row.totalsBeans}" />    
    <display:column title="Year 1 Req">
      <fmt:formatNumber value="${tbeans[0].totAmtReq}" />
    </display:column>
    <display:column title="Year 2 Req">
      <fmt:formatNumber value="${tbeans[1].totAmtReq}" />
    </display:column>
    
    <display:column property="totamtreq" format="{0,number,0,000}" title="Total Requested" total="true" sortable="true" />
      
    
    <display:column title="Year 1 Appr">
      <fmt:formatNumber value="${tbeans[0].totAmtAppr}" />
    </display:column>
    <display:column title="Year 2 Appr">
      <fmt:formatNumber value="${tbeans[1].totAmtAppr}" />
    </display:column>
        
    <display:column property="totamtappr" format="{0,number,0,000}" title="Total Approved" total="true" sortable="true"/>
      
   <%-- <display:column property="score" title="Avg Score" sortable="true" headerClass="sortable"/>
    <display:column property="rating" title="Avg Rating" sortable="true" headerClass="sortable" />  --%>
  
  </display:table>  
  
  </body>
</html>
