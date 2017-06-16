<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" />
  </head>
  <body>
  
  
  <tiles:insert attribute="header" ignore="true"/>
  <tiles:insert attribute="menubar" ignore="true" />
  
  <table width="100%">
    <tr>
      <td colspan="2" height="10" />
    </tr>
    <tr>
      <td width="25%" valign="top"><tiles:insert attribute="narrmenu" ignore="true" /></td>
      <td valign="top"><tiles:insert attribute="body" ignore="true" /></td>
    </tr>
  </table>
    
  <br/><br/><br/>
  <tiles:insert attribute="footer" ignore="true" />

  </body>
</html>
