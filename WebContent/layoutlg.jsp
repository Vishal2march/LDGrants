<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <link href="css/a.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  <tiles:insert attribute="header" ignore="true"/>
  
  <div id="contentholder">

   <%-- <div id="leftmenupg">
    <div id="navlist">
      <tiles:insert attribute="archmenu" ignore="true" />
    </div>
    </div>--%>
      
    <div class="content" id="content">
  
      <tiles:insert attribute="menubar" ignore="true" />
      <tiles:insert attribute="body" ignore="true" />
      <tiles:insert attribute="body2" ignore="true" />
      <tiles:insert attribute="body3" ignore="true" />
      
    </div>
      
  </div>
  
  <tiles:insert attribute="footer" ignore="true" />
  
  </body>
</html>
