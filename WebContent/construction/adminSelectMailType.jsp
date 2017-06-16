<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - Admin Select Email Type</title>
  </head>
  <body>
  
  <h4>Email Template</h4>
  
  <c:choose>
  <c:when test="${param.select=='true'}">
  
  <form method="POST" action="cnAdminEmailNav.do?item=createcntemplate" >
  <table border="1" class="graygrid" width="90%" align="center" summary="for layout only">
    <tr>
      <td>Select the type of email.  Then you may edit
      the email body, and later choose recipients.</td>
    </tr>
    <tr>
      <td><input type="RADIO" name="type" value="initial" checked="checked" />Application Approved Email<br/>
          <%--<input type="RADIO" name="type" value="final" />Final Report Approved Email<br/>--%>
          <input type="RADIO" name="type" value="deny" />Application Denied Email<br/>
    </tr>
    <tr>
      <td><input type="HIDDEN" name="m" value="cn" />
          <input type="SUBMIT" value="Select email" /></td>
    </tr>
    <tr>
        <td height="30"/>
    </tr>
    <tr>
        <td><a href="cnAdminEmailNav.do?item=unsentmail&pr=cn">View email templates</a> already in progress</td>
    </tr>
    <tr>
        <td><a href="CnAdminNewTemplate.do">Create</a> new, blank, email template</td>
    </tr>
  </table>
  </form>
  
  </c:when>
  <c:otherwise>
  
  
  <html:form action="/saveCnAdminMail">

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
        <td><%--loop on all message vars--%>
            <c:forEach var="row" items="${emailHelpBean.messageVars}">
              <c:out value="${row.name}"/><br/>
            </c:forEach>
            
            <html:hidden property="managerNameId" /><html:hidden property="managerName" />
            <html:hidden property="projectNumId" /><html:hidden property="projectNum" />
            <html:hidden property="amtapprovedId" /><html:hidden property="amtapproved"/>
            <html:hidden property="fiscalYearId" /><html:hidden property="fiscalYear"/>
        </td>
       <%-- <td colspan="2">
          <c:if test="${emailHelpBean.managerName}">$$managerName<br/></c:if>
          <html:hidden property="managerNameId" /><html:hidden property="managerName" />
          
          <c:if test="${emailHelpBean.projectNum}" >$$projectNum<br/></c:if>
          <html:hidden property="projectNumId" /><html:hidden property="projectNum" />
                   
          <c:if test="${emailHelpBean.amtapproved}" >$$amtApproved<br/></c:if>
          <html:hidden property="amtapprovedId" /><html:hidden property="amtapproved"/>
          
          <c:if test="${emailHelpBean.fiscalYear}" >$$fiscalYear</c:if>
          <html:hidden property="fiscalYearId" /><html:hidden property="fiscalYear"/>
          </td>--%>
      </tr>
      <tr>
        <td colspan="2"><html:hidden property="wtid" />
        <html:hidden property="program" value="cn"/><html:submit value="Save" />
        Be sure to SAVE any changes to the From, Subject, and Message fields.</td>
      </tr>
      <tr>
        <td height="30"/>
      </tr>
      <tr>
        <td colspan="2">
        <c:url var="wtrecip" value="cnAdminEmailNav.do">
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