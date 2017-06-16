<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
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
      <th>Public Library Construction Grant Program: DASNY report - 
      applications with recommended amount, start date, project description</th>
    </tr>
  </table>
  <br/><br/>
  
  
  <display:table name="sessionScope.allApplications" id="row" requestURI="" export="true" varTotals="totals" decorator="org.displaytag.decorator.TotalTableDecorator">
    <display:column title="Row" >
      <c:out value="${row_rowNum}"/>
    </display:column>
    <display:column property="systemName" title="System" sortable="true" headerClass="sortable" group="1" />
    
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
    
    <display:column property="buildingName" title="Library Name" sortable="true" headerClass="sortable" />
    
    <display:column title="Address" >
          <c:out value="${row.addr1}" /> <c:out value="${row.city}"/> <c:out value="${row.state}"/> <c:out value="${row.zipcd1}"/>
    </display:column> 
        
    
    <display:column  property="totalRecommend" format="$ {0,number,0,000}" title="Award Amount" total="true"/>
     
    <display:column property="summaryDescr" title="Project Description"/>
     
    <display:column title="Projected Start Date">
        <fmt:formatDate value="${row.startdate}" pattern="MM/dd/yyyy"/>
    </display:column>
    
   <display:column property="totalApproved" format="$ {0,number,0,000}" title="Totals" total="true" style="color:white;"/>
      
  </display:table>    
  
  
  </body>
</html>