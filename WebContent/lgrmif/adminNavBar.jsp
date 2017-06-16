<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <table summary="for layout only" width="98%">
  <tr>
    <td align="center">
      <!--this link allows Jaws to skip over menu items-->
      <a href="#maincontent"><img border="0" src="images/bump.bmp" height="1" width="1" alt="skip navigation" /></a>
      
      <a href="welcomePage.jsp">Home</a>
    </td>
    
    <td align="center">
      <a href="lgAdminNav.do?item=home&m=80">LGRMIF Home</a>
    </td>
    
    <td align="center">
      <a href="lgAdminRevNav.do?item=reviewers&m=lg">Reviewers</a>
    </td>
    
    <td align="center">
      <a href="lgAdminPanel.do?item=loadPanel">Panels</a>
    </td>
    
    <td align="center">
      <a href="adminSearch.do?item=loadsearch&m=lg">Search</a>
    </td>
    
    <td align="center">
      <a href="LgAdminMassMail.do?select=true">Emails</a>
    </td>
   
    <td align="center">
      <a href="LgAccounts.do">User Accounts</a>
    </td>
    
    <%--12/6/12 hide app dates function, per FC--%>
    <td align="center">
      <a href="adminAppDates.do?item=viewdates&m=lg">Application Dates</a>
    </td>    
    
    <td align="center">
      <a href="lgAdminNav.do?item=reports">Reports</a>
    </td>
    
    <td align="center">
      <a href="LgAdminHelp.do">Help</a>
    </td>
  </tr>
  <a name="maincontent" id="maincontent" />
</table>
<hr style="background-color:rgb(0,0,0);" size="3"/>
  </body>
</html>
