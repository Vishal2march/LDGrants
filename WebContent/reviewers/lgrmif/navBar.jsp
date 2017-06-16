<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <table summary="for layout only" width="95%">
  <tr>
    <td align="center" >
      <!--this link allows Jaws to skip over menu items-->
      <a href="#maincontent"><img border="0" src="images/bump.bmp" height="1" width="1" alt="skip navigation" /></a>
      
      <a href="welcomePage.jsp">Home</a>
    </td>
    
    <td align="center" >
      <a href="lgReviewer.do?item=home">Reviewer Home</a>
    </td>
    
    <%-- 7/11/14 per DM: remove participation section
    <td align="center" >
      <a href="lgReviewer.do?item=participation">Participation</a>
    </td>--%>
    
    <td align="center">
      <a href="lgReviewer.do?item=revinfo&p=lg">Contact Information</a>
    </td>   
    
    <td align="center" >
      <a href="lgReviewer.do?item=assignments&p=lg">At-Home Evaluation</a>
    </td>
    
    <td align="center">
      <a href="lgReviewer.do?item=panelsearch">Panel Deliberation</a>
    </td> 
    
    <c:if test="${lduser.israo=='true'}">
        <td align="center"><a href="lgReviewer.do?item=loadregion">Decision Notes for Region</a></td>
    </c:if>
    
    <c:if test="${lduser.isgovtemp=='true'}">
        <td align="center"><a href="lgReviewer.do?item=reports">Reports</a></td>  
    </c:if>
    
  </tr>
  <a name="maincontent" id="maincontent" />
</table>
<hr style="background-color:rgb(0,0,0);" size="3"/>
  </body>
</html>
