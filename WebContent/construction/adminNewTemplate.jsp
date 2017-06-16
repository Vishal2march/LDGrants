<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>construction adminNewTemplate</title>
  </head>
  <body>
  
  <h4>Create email with optional message variables</h4>


    <html:form action="/cnSaveNewTemplate">
    
    <p>All fields marked with an * are required.</p>
    <html:errors />
    <table width="100%" border="0" cellpadding="5" cellspacing="5" summary="for layout only">
      <tr>
        <td>*From:</td>
        <td><html:text property="from" size="50" /></td>
      </tr>
      <tr>
        <td>*Subject:</td>
        <td><html:text property="subject" size="50" /></td>
      </tr>
      <tr>
        <td>*Message:</td>
        <td><html:textarea property="message" cols="55" rows="15"></html:textarea></td>
      </tr>
      <tr>
        <td colspan="2">(Optional) Choose message variables to substitute into the email.  These are the ONLY message 
        variables that may be substituted. </td>
      </tr>
      <tr>
        <td colspan="2">
          <html:checkbox property="managerName" />$$managerName
          <br/>
          <html:checkbox property="projectNum" />$$projectNum
          <br/>
          <html:checkbox property="projectTitle" />$$projectTitle 
          <br/>
          <html:checkbox property="amtapproved" />$$amtApproved
          <br/>
          <html:checkbox property="instName" />$$instName
          <br/>
          <html:checkbox property="buildingName" />$$buildingName
          </td>
      </tr>
      <tr>
        <td colspan="2"><html:hidden property="wtid" />
                <html:hidden property="program" value="cn"/><html:submit value="Save" /> </td>
      </tr>
    </table>
    </html:form>
    
    
    <p><font color="Red">Notice:</font> The email message above may contain variables (such as $$projectNum,
    or $$managerName).  Any item with the '$$' symbol will be interpreted as a variable.  If you want to include 
    a variable in the email Subject or email Message, you must also mark the checkbox next to the variable.  The system will
    merge the variables and values once you choose Recipients.</p>
    <br/>

  
  </body>
</html>