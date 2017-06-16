<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
    <link rel="stylesheet" type="text/css" href="css/displaytagGroup.css" />
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
      <th>Public Library Construction Grant Program: DASNY submit/approve list</th>
    </tr>
  </table>
  <br/><br/>
  
  
  <display:table name="sessionScope.allApplications" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator">
    <display:column title="Row" >
      <c:out value="${row_rowNum}"/>
    </display:column>
    <display:column property="systemName" title="System" sortable="true" headerClass="sortable" group="1" />
    
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
    
    <display:column property="instName" title="Library" sortable="true" headerClass="sortable" />
    <display:column property="buildingName" title="Building" sortable="true" headerClass="sortable" />
    
    <display:column property="totalRequest" format="$ {0,number,0,000}" title="Cost of Project" sortable="true"/>
    <display:column property="totalRecommend" format="$ {0,number,0,000}" title="System Recommended" total="true" sortable="true"/>
    <display:column property="totalApproved" format="$ {0,number,0,000}" title="LD Approved" total="true" sortable="true"/>
    
    <display:column property="dasnySubmit" title="Submitted to DASNY" sortable="true" headerClass="sortable" />
    <display:column property="dasnyApprove" title="Approved by DASNY" sortable="true" headerClass="sortable" />
    <%--rmvd 1/31/12 <display:column property="bondCouncilApprove" title="Bond Council Review" sortable="true" headerClass="sortable" />--%>
   
  </display:table>    
  
  </body>
</html>