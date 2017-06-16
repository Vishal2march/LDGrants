<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminLinkChecklist</title>
  </head>
  <body>
  
  <c:if test="${param.p=='fl'}" >
      <br/><br/>
      <c:url var="backURL" value="flAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>   
      <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
    </c:if>
    
    <c:if test="${param.p=='al'}" >
      <br/><br/>
      <c:url var="backURL" value="alAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>   
      <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
    </c:if>
    
  </body>
</html>