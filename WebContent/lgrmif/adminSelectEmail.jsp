<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Budget Category Report</title>
  </head>
  <body>
  
  
  <h5>Email Template</h5>
  
  <c:choose>
  <c:when test="${param.select=='true'}">
  
  <form method="POST" action="adminEmailNav.do?item=createtemplate" >
  <table border="1" width="90%" align="center" summary="for layout only">
    <tr>
      <td>Select the type of email.  Then you may edit
      the email body, and later choose recipients.</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="type" value="initial" checked="checked" />Application Approved Email (Fund)<br/>
          <input type="RADIO" name="type" value="final" />Final Report Approved Email<br/>
          <input type="RADIO" name="type" value="deny" />Application Denied Email (No Fund)<br/>
          <input type="radio" name="type" value="particip"/>Request to Review Email<br/>
          <input type="radio" name="type" value="assign"/>Reviewer Assignments Email<br/>
          <input type="radio" name="type" value="amendment" />Budget Amendment Approved Email</td>
    </tr>
    <tr>
      <td><input type="HIDDEN" name="m" value="lg" />
          <input type="SUBMIT" value="Select email" /></td>
    </tr>
    <tr>
        <td height="30"/>
    </tr>
    <tr>
        <td><a href="adminEmailNav.do?item=unsentmail&pr=lg">View email templates</a> already in progress</td>
    </tr>
  </table>
  </form>
  
  </c:when>
  <c:otherwise>
  
  
  <html:form action="/saveLgMail">

    <p>Update the email template. All fields marked with an * are required.</p>
    <html:errors />
    <table width="100%" summary="for layout only">
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
        <td colspan="2">The following message variables will be substituted in the email:</td>
      </tr>
      <tr>
        <td colspan="2">
          <c:if test="${emailHelpBean.managerName}">$$managerName<br/></c:if>
          <html:hidden property="managerNameId" /><html:hidden property="managerName" />
          
          <c:if test="${emailHelpBean.projectNum}" >$$projectNum<br/></c:if>
          <html:hidden property="projectNumId" /><html:hidden property="projectNum" />
                   
          <c:if test="${emailHelpBean.amtapproved}" >$$amtApproved<br/></c:if>
          <html:hidden property="amtapprovedId" /><html:hidden property="amtapproved"/>
          
          <c:if test="${emailHelpBean.fiscalYear}" >$$fiscalYear</c:if>
          <html:hidden property="fiscalYearId" /><html:hidden property="fiscalYear"/>
          </td>
      </tr>
      <tr>
        <td colspan="2"><html:hidden property="wtid" />
        <html:hidden property="program" value="lg"/><html:submit value="Save" />
        Be sure to SAVE any changes to the From, Subject, and Message fields.</td>
      </tr>
      <tr>
        <td height="30"/>
      </tr>
      <tr>
        <td colspan="2">
        <c:url var="wtrecip" value="adminEmailNav.do">
            <c:param name="item" value="recipientspage" />
            <c:param name="id" value="${emailHelpBean.wtid}" />
         </c:url>
        <a href='<c:out value="${wtrecip}"/>'>Select Recipients for this email</a></td>
      </tr>
    </table>
    </html:form>

  </c:otherwise>
  </c:choose>
  
  </body>
</html>