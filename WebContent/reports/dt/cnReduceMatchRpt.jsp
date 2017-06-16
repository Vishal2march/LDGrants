<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - Reduced Match Report</title>
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
      <th>Public Library Construction Grant Program: Reduced Match Report</th>
    </tr>
  </table>
  <br/><br/>
  
    
  <display:table name="sessionScope.allApplications" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator">
    <display:column title="Row" >
      <c:out value="${row_rowNum}"/>
    </display:column>
    <display:column property="systemName" title="Library System Name" sortable="true" headerClass="sortable" />
    
    <display:column property="instName" title="Library Name" sortable="true" headerClass="sortable"/>
    <display:column property="city" title="Library Community" sortable="true" headerClass="sortable" />
    
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
    
    
    <display:column property="totalRequest" format="$ {0,number,0,000}" title="Project Cost For Which Funding is Being Requested" sortable="true"/>
    <display:column property="totalRecommend" format="$ {0,number,0,000}" title="Amount Award" total="true" sortable="true"/>
    <display:column property="recommendPercentStr" title="Award Amount % of Project Cost" sortable="true" headerClass="sortable" />
    
    <display:column property="summaryDescr" title="Project Description" />
    <display:column property="matchJustification" title="Justification" />
    
  </display:table>    
  
  
  
  
  
  </body>
</html>