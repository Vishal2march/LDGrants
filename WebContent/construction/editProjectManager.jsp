<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction</title>
  </head>
  <body>
  
  <h4>Update Project Manager Contact Information</h4>
  
  <br/>
  <html:errors/>
  <html:form action="/cnSaveProjManager">
    <table class="boxtype" width="80%" summary="for layout only">   
      <tr>
        <td colspan="2"><b>Construction Project Manager</b>
            (must be Library Staff or Board Member)</td>
      </tr>
      <tr>
        <td width="30%">First Name</td>
        <td width="70%"><html:text property="fname" /></td>
      </tr>
      <tr>
        <td >Last Name</td>
        <td ><html:text property="lname" /></td>
      </tr>    
      <tr>
        <td >Phone (format 111-111-1111)</td>
        <td ><html:text property="phone" /></td>
      </tr>
      <tr>
        <td >Phone Extension</td>
        <td ><html:text property="phoneext" /><html:hidden property="phoneextId" /></td>
      </tr>
      <tr>
        <td colspan="2">The Project Manager email address listed below will receive notifications regarding the
        application</td>
      </tr>
      <tr>
        <td>Email</td>
        <td><html:text property="email" /><html:hidden property="pmId" />
            <html:hidden property="module"/></td>
      </tr>      
      <tr>
        <td colspan="2"><html:submit value="Save"/></td>
      </tr>
  </table> 
  </html:form>
  
  
  </body>
</html>