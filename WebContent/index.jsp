<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  index.jsp
 * Creation/Modification History  :
 *
 *     SLowe       Created
 *
 * Description
 * This is the page that will load after the user enters thier name/password on
 * either the ldap login screen or the temp login screen.  It is included as the start
 * file in the welcome-list in web.xml.  It will reroute to loginchecker.java.
--%>

<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>   

    <!-- SL: index page is entry point to LDGrants as defined in web.xml -->
    <jsp:forward page="LoginChecker"/>
  
  </body>
</html>
