<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="org.displaytag.decorator.TotalTableDecorator" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
    <title>revInfoRpt</title>
  </head>
  <body>
  
  <h5>Active Reviewers with SSN, Vendor ID</h5>
  
  <display:table name="sessionScope.allRev" id="row" requestURI="" export="true" decorator="org.displaytag.decorator.TotalTableDecorator" >
   
    <display:column title="Name" sortable="true" sortProperty="lname">
          <c:out value="${row.fname}"/> <c:out value="${row.lname}"/>
    </display:column> 

    <display:column property="title"/>
    <display:column property="affiliation"/>
    <display:column property="ssn"/>
    <display:column property="vendornum" title="Vendor ID"/>
        
  </display:table>    
  
  
  </body>
</html>