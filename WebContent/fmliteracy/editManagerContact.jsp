<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
    
  <h4>Update Project Manager & Additional Contact Information</h4>
  <br/><br/>
  
  <html:errors/>
  <html:form action="/saveLitContactInfo">
    <table summary="for layout only" class="boxtype">    
      <tr>
        <th>Project Manager Information</th>
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
        <td><html:text property="email" /><html:hidden property="pmId" /></td>
      </tr>      
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <th colspan="2" align="left">Additional Contact (preferably Library Director or other responsible party)</th>
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
        <th colspan="2"><html:hidden property="grantid"/>
                        <html:hidden property="module" /><html:submit value="Save"/></th>
      </tr>
    </table>
    </html:form>  
  
  </body>
</html>