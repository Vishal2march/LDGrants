<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>C/P Coordinated Score Order Report</title>
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
      <th>C/P Coordinated Aid scores report for grants received during fiscal year <c:out value="${fybean.year}" /></th>
    </tr>
  </table>
  <br/><br/>
  
  
   <display:table name="sessionScope.allGrants" id="row" decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="" export="true">
       
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" minIntegerDigits="2" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####"  />
    </display:column> 
    
    <display:column property="title" sortable="true" headerClass="sortable" />
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />
    <display:column property="score" sortable="true" headerClass="sortable" />
    
    <display:column property="fyOneRequest" title="Year1 Request" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="fyTwoRequest" title="Year2 Request" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="fyThreeRequest" title="Year3 Request" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    
    <display:column property="totamtreq" title="Total Request" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
    <display:column property="totamtappr" title="Total Award" format="{0,number,$0,000}" total="true" sortable="true" headerClass="sortable" />
        
  </display:table>
  
  </body>
</html>