<%--
 * @author  shusak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  cpEmailEdit.jsp
 * Creation/Modification History  :
 * SHusak       6/15/07     Modified
 *
 * Description
 * This page is for c/p admin to create a new template, or to update
 * subject/message for an existing email template. Also display recipients/vars.
--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Update email template</h4>


<p>All fields marked with an * are required.</p>
<html:errors />
<table width="100%" border="0" cellpadding="5" cellspacing="5" summary="for layout only">
<html:form action="/editCpEmail">
  <tr>
    <td>*From Email Address: (must have @nysed.gov extension)</td>
    <td><html:text property="from" size="30" /></td>
  </tr>
  <tr>
    <td>*Subject:</td>
    <td><html:text property="subject" size="60" /></td>
  </tr>
  <tr>
    <td>*Message:</td>
    <td><html:textarea property="message" cols="80" rows="25"></html:textarea></td>
  </tr>
  <tr>
    <td colspan="2" align="center"><html:hidden property="wtid" />
                <html:hidden property="program" value="cp" /><html:submit value="Save Changes" /> </td>
  </tr>
</html:form>
</table>


<p class="error">Notice: The email message above may contain variables (such as $$projectNum,
or $$managerName).  Any item with the '$$' symbol is a variable, and should <b>not</b> be 
changed.  The system will merge the variables and values once the email is ready to send.</p>
<br/>

  <logic:notEmpty name="emailHelpBean" property="emailLogs" >
    <table width="100%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <th colspan="2">Recipients, Email Variables and associated Values</th>
      </tr>      
        <c:forEach var="el" items="${emailHelpBean.emailLogs}">
          <%--get all recipients --%>
          <c:set var="req" value="${el.recipients}" />
          
          <%--print each recipient info --%>
          <c:forEach var="r" items="${req}" >            
            <tr>
              <td width="10%"><c:out value="${r.toCcBc}" /></td>
              <td><c:out value="${r.emailAddress}" /></td>
            </tr>
          </c:forEach>   
                
          <logic:notEmpty name="emailHelpBean" property="messageVars">                       
            <c:set var="evq" value="${el.emailVariables}" />
            <c:forEach var="mv" items="${emailHelpBean.messageVars}">
              <c:forEach var="ev" items="${evq}">
                <c:if test="${ev.messageVarId==mv.id}">
                  <tr>
                    <td><c:out value="${mv.name}" /></td>
                    <td><c:out value="${ev.value}" /></td>
                  </tr>
                </c:if>
              </c:forEach>              
            </c:forEach>         
        </logic:notEmpty>  
        
        <tr>
          <td colspan="2"><hr/></td>
        </tr>        
        </c:forEach>
      </table>
        
  </logic:notEmpty>
  
  </body>
</html>
