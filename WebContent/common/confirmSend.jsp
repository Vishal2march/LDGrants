<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment:  JDeveloper 10g
 * Name of the Application:  confirmSend.jsp
 * SHusak 5/26/09 Created
 *
 * Description
 * This page shows details of selected email (subject, message, to/from, variables)
 * and contains final SEND button. Used for sa/co/di/fl/al/lg/cn admin
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <h4>Confirm Send Email</h4>
  
  <table width="100%" class="borderbox" summary="for layout only">
  <tr>
    <th>From:</th>
    <td><c:out value="${emailHelpBean.from}" /></td>
  </tr>
  <tr>
    <th>Subject:</th>
    <td><c:out value="${emailHelpBean.subject}" /></td>
  </tr>
  <tr>
    <th>Message:</th>
    <td><c:out value="${emailHelpBean.message}" /></td>
  </tr>
  <c:if test="${param.p=='lit'}">
  <tr>
    <th>Message variables:</th>
    <td><c:if test="${emailHelpBean.managerName=='true'}">
          $$managerName<br/>
        </c:if>
        <c:if test="${emailHelpBean.projectNum=='true'}">
          $$projectNum<br/>
        </c:if>
        <c:if test="${emailHelpBean.programName=='true'}">
          $$program<br/>
        </c:if>
        <c:if test="${emailHelpBean.grantNum=='true'}">
          $$grantId<br/>
        </c:if>
        <c:if test="${emailHelpBean.amtapproved=='true'}">
          $$amtApproved
        </c:if>
    </td>
  </tr>
  </c:if>
</table>


<br/><br/>

  <logic:notEmpty name="emailHelpBean" property="emailLogs" >
    <table width="100%" class="borderbox" summary="for layout only">
      <tr>
        <th colspan="2">Recipients, Message Variables and Values</th>
      </tr>
      <c:set var="elogs" value="${emailHelpBean.emailLogs}" />
      <c:set var="mvars" value="${emailHelpBean.messageVars}" />
      
        <c:if test="${empty elogs}">
            <tr>
                <td colspan="2"><font color="red">WARNING:</font> This email does not
                have any recipients.  The email cannot be sent until you add  
                email recipients (to, cc).</td>
            </tr>
        </c:if>
        
        <c:forEach var="el" items="${elogs}">
            <%--get all recipients and any email var values--%>
            <c:set var="req" value="${el.recipients}" />
            <c:set var="evars" value="${el.emailVariables}" />
            <%--print each recipient info --%>
            <c:forEach var="r" items="${req}" >            
              <tr>
                <td width="10%"><c:out value="${r.toCcBc}" /></td>
                <td><c:out value="${r.emailAddress}" /></td>
              </tr>
            </c:forEach>   
            <tr>
              <td height="20" />
            </tr>
            <%--for each email var value; print w/ corresponding mv --%>
            <c:if test="${evars!=null}">
              <tr><td colspan="2">
              <c:forEach var="v" items="${evars}" >
                  <c:forEach var="mv" items="${mvars}" >
                    <c:if test="${mv.id==v.messageVarId}">
                      <c:out value="${mv.name}" />: &nbsp;&nbsp; <c:out value="${v.value}" />
                    </c:if>
                  </c:forEach><br/>
              </c:forEach>
              <hr/></td></tr>
            </c:if>
        </c:forEach>
      </table>
      
  </logic:notEmpty>
  
  <c:choose >
  <c:when test="${param.p=='lit'}">
      <form method="POST" action="litEmailNav.do?item=sendwt">
        <p>Do you want to Send this email?</p>
        <input type="HIDDEN" name="id" value='<c:out value="${emailHelpBean.wtid}" />' />
        <input type="HIDDEN" name="prog" value='lit' />
        <input type="SUBMIT" value="Send" />
      </form>
  </c:when>
  
  <c:when test="${param.p=='cp'}">
      <form method="POST" action="cpAdminEmail.do?item=sendwt">
        <p>Do you want to Send this email?</p>
        <input type="HIDDEN" name="id" value='<c:out value="${emailHelpBean.wtid}" />' />
        <input type="HIDDEN" name="prog" value='<c:out value="${emailHelpBean.program}" />' />
        <input type="SUBMIT" value="Send" />
      </form>
  </c:when>
  
  
  <c:when test="${param.p=='lg'}">
      <form method="POST" action="adminEmailNav.do?item=sendwt">
        <p>Do you want to Send this email?</p>
        <input type="HIDDEN" name="id" value='<c:out value="${emailHelpBean.wtid}" />' />
        <input type="HIDDEN" name="prog" value='<c:out value="${emailHelpBean.program}" />' />
        <input type="SUBMIT" value="Send" />
      </form>
  </c:when>
  
  
  <c:when test="${param.p=='cn'}">
      <form method="POST" action="cnAdminEmailNav.do?item=sendwt">
        <p>Do you want to Send this email?</p>
        <input type="HIDDEN" name="id" value='<c:out value="${emailHelpBean.wtid}" />' />
        <input type="HIDDEN" name="prog" value='<c:out value="${emailHelpBean.program}" />' />
        <input type="SUBMIT" value="Send" />
      </form>
  </c:when>
  </c:choose>
  
  </body>
</html>
