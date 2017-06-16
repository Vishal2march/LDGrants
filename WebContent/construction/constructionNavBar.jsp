<!--jsp tiles page which defines the navigation bar -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>
  <br/>
  <table summary="for layout only" bgcolor="Silver" width="100%">
  <tr>
    <td width="20%" align="center" >
      <a href="#maincontent"><img border="0" src="images/bump.bmp" height="1" width="1" alt="skip navigation" /></a>
      
      <a href="welcomePage.jsp">Home</a>
    </td>
  
    <td align="center" >
      <a href="constructionNav.do?item=homepage&m=cn">Construction Home Page</a>
    </td>
    
    <td align="center" >
      <a href="constructionForms.do?i=checklist&m=cn">Construction Checklist</a>
    </td>
    
    <%--<td width="20%" align="center" >
      <a href="constructionNav.do?item=createapp&m=cn">Start a new Construction Grant</a>
    </td>--%>
    
    <td width="20%" align="center">
      <a href="constructionHelp.do">Help</a>
    </td>
  </tr>
  <a name="maincontent" id="maincontent" />
</table>

  </body>
  </html> 
  