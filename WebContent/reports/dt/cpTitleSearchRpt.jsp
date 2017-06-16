<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Conservation Preservation Discretionary Report</title>
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
      <th>CP Discretionary Projects: <c:choose>
                                    <c:when test="${titlesearch=='true'}">
                                      Search by Project Title
                                    </c:when>
                                    <c:otherwise>
                                      Education Projects
                                    </c:otherwise>
                                    </c:choose>   
      </th>
    </tr>
  </table>
  <br/><br/>
  
  
  <display:table name="sessionScope.allGrants" id="row" requestURI="" export="true" >
   
    <display:column property="instName" title="Institution" sortable="true" headerClass="sortable" />          
    <display:column property="title" title="Project Title" sortable="true" headerClass="sortable" />
    <display:column property="fiscalyear" title="Year" sortable="true" headerClass="sortable"/>
    
    <display:column property="city" title="City" />
    <display:column property="county" title="County" sortable="true" headerClass="sortable"/>    
    <display:column property="totamtappr" title="Award" sortable="true" headerClass="sortable" format="$ {0,number}"/>    
    
    <display:column title="Project Manager">
       <c:out value="${row.projectManager.fname}"/> <c:out value="${row.projectManager.lname}"/>
    </display:column>
    <display:column property="projectManager.phone" title="Phone" />
    <display:column property="projectManager.email" title="Email" />
    
    
  </display:table>    
  
  
  
  </body>
</html>