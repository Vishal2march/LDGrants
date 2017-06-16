<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminProjManUpdate.jsp
 * Creation/Modification History  :
 *     
 *     SHusak  3/11/10     Modified
 *
 * Description
 * This used by co/di/al/fl/cn admin to update PM info. 
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  
  <h5>Update Project Manager Information</h5>
  
  <html:errors/>
  <html:form action="/adminUpdateProjMang">
    <table summary="for layout only">     
      <tr>
        <td width="30%">First Name</td>
        <td width="70%"><html:text property="fname" /></td>
      </tr>
      <tr>
        <td >Last Name</td>
        <td ><html:text property="lname" /></td>
      </tr>
      <tr>
        <td >Title</td>
        <td ><html:text property="title" maxlength="50" /></td>
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