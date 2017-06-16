<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  <h4>Confirm Create New Construction Application</h4>
  
  
  <p>
  <font color="Red">**Note:</font>
  You already have a Construction grant application in progress for the current 
  fiscal year.  Please choose one of the options below:
  
  <br/><br/>
  
  <ul>
   <li>Return to the Construction<a href="constructionNav.do?item=homepage&m=cn">home page</a> and click on a project number to continue a Construction grant application in progress.
   </li>
   
   <br/><br/>
  
   <li>Or, you can 
   <a href="constructionNav.do?item=confirmCreateApp&m=cn" >create</a>
   a new, blank Construction grant application.
   </li>
   </ul>
   
   </p>
  
  </body>
</html>