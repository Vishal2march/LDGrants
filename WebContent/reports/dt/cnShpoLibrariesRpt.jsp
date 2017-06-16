<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>cnShpoLibrariesRpt</title>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
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
      <th>Public Library Construction Grant Program: Applications submitted to LD, Libraries subject to SHPO</th>
    </tr>
  </table>
  <br/><br/>
    
    
  <display:table name="sessionScope.allApplications" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator">
    <%--<display:setProperty name="decorator.media.excel" value="org.displaytag.sample.decorators.HssfTotalWrapper" /> 
    <display:setProperty name="decorator.media.html"  value="org.displaytag.decorator.TotalTableDecorator" />--%>
    
    
    <display:column title="Row" >
      <c:out value="${row_rowNum}"/>
    </display:column>
    <display:column property="systemName" title="System" sortable="true" headerClass="sortable" group="1" />
    
    <display:column title="ProjectNumber" >
          03<fmt:formatNumber value="${row.fccode}" />-<fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-<fmt:formatNumber value="${row.projseqnum}" pattern="####" />
    </display:column> 
    
    <display:column property="instName" title="Library" sortable="true" headerClass="sortable" />
    <display:column property="buildingName" title="Building" />
   
    <display:column property="historicLandmark" title="Historic Landmark" total="true" format="{0,number,0}" />
    <display:column property="historicDistrict" title="Historic District" total="true" format="{0,number,0}"/>
    <display:column property="over50" title="Over 50 Years Old" total="true" format="{0,number,0}"/>
    <display:column property="groundDisturb" title="Ground Disturbance" total="true" format="{0,number,0}"/> 
      
  </display:table>    
  
  </body>
</html>