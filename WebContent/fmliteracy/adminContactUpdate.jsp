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
  
  <html:errors/>
  <html:form action="litAdminUpdateContact">
  <table summary="for layout only">
      <tr>
        <th colspan="2">Additional Contact (preferably Library Director or other responsible party)</th>
      </tr>          
      <tr>
        <td width="30%">First Name</td>
        <td width="70%"><html:text property="rmofname" /></td>
      </tr>
      <tr>
        <td>Last Name</td>
        <td><html:text property="rmolname" /></td>
      </tr>
      <tr>
        <td>Title</td>
        <td><html:text property="rmotitle" maxlength="50" /></td>
      </tr>        
      <tr>
        <td>Phone (111-111-1111)</td>
        <td><html:text property="rmophone" /></td>
      </tr>
      <tr>
        <td>Phone Extension</td>
        <td><html:text property="rmophoneext" /> <html:hidden property="rmophoneextId" /></td>
      </tr>
      <tr>
        <td>Email</td>
        <td><html:text property="rmoemail" /><html:hidden property="rmoId" /></td>
      </tr>
      <tr>
        <td colspan="2"><html:hidden property="grantid"/>
                        <html:hidden property="module" /><html:submit value="Save"/></td>
      </tr>
    </table>
    </html:form>  
   
  </body>
</html>