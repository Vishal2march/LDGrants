<%--
 * @author  shusak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  individualRecipient.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       6/15/07     Modified
 *
 * Description
 * This page is for lit admin to search for recipient groups (reviewers and PM's) and
 * then choose individual people to add to the email template. 
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>individualRecipient</title>
  </head>
  <body>
  
  <form method="POST" action="litAdminEmail.do?i=matchrecipients" >
  <table width="100%" class="boxtype" summary="for layout only">
    <tr>
        <td><b>Choose a Project Manager or Additional Contact</b></td>
      </tr>
      <tr>
        <td>Program:<br/>
          <input type="RADIO" name="fc" value="40" checked="checked" />Adult Literacy
          <br/><input type="RADIO" name="fc" value="42" />Family Literacy</td>
     </tr>
     <tr>
       <td>Fiscal Year:
          <select name="fy" >
            <c:forEach var="row" items="${dropDownList}">
                <option value="<c:out value='${row.id}' />">
                <c:out value="${row.description}" /></option>
            </c:forEach>              
          </select></td>
      </tr>    
      <tr>
        <td><input type="HIDDEN" name="wtid" value='<c:out value="${id}"/>' />
        <input type="SUBMIT" value="View Recipients" /></td>
      </tr>  
  </table>
  </form>
  
  
  <logic:notEmpty name="AssignCollectionBean">  
  
  <html:form action="litAddRecipient">
  <table width="100%" class="borderbox" summary="for layout only">
    <tr>
      <th colspan="4">Recipients that match the search criteria:</th>
    </tr>
    <tr>
        <td colspan="4">The results below contain the Project Manager 
        (and Additional Contact, if applicable) of 
        all Adult or Family literacy grants submitted for the fiscal year.  The results include
        both approved, denied, and pending projects.  If you want to select only the 'Approved Group',
        please use the 
        <c:url var="wtrecip" value="litAdminEmail.do">
           <c:param name="i" value="recipientspage" />
           <c:param name="id" value="${AssignCollectionBean.workingTemplateId}" />
        </c:url>
        <a href='<c:out value="${wtrecip}" />'>Recipient Group</a> page. </td>
    </tr>
    <tr>
      <th>Select</th>
      <th>Name</th>
      <th>Email Address</th>
      <th>Institution</th>
      <th>Project Number</th>
    </tr>
    
    <logic:present name="AssignCollectionBean" property="allPotentialGrants" >
    <logic:iterate name="AssignCollectionBean" property="allPotentialGrants" id="grantItem" >   
    
      <tr>
        <td><html:checkbox name="grantItem" property="assignpanel" indexed="true"/>
            <html:hidden name="grantItem" property="grantid" indexed="true"/></td>
        <td><c:out value="${grantItem.name}"/>
            <html:hidden name="grantItem" property="name" indexed="true"/></td>
        
        <td><c:out value="${grantItem.email}"/>
           <html:hidden name="grantItem" property="email" indexed="true"/> </td>
        <td><c:out value="${grantItem.instName}"/>
            <html:hidden name="grantItem" property="instName" indexed="true"/></td>
        <td>03<fmt:formatNumber minIntegerDigits="2" value="${grantItem.fccode}" />
          -<fmt:formatNumber value="${grantItem.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${grantItem.projseqnum}" pattern="####" />
            <html:hidden name="grantItem" property="fccode" indexed="true"/>
            <html:hidden name="grantItem" property="fycode" indexed="true"/>
            <html:hidden name="grantItem" property="projseqnum" indexed="true"/>
        </td>
      </tr>
    </logic:iterate>
    </logic:present>
    
    <tr>
      <td colspan="4"><hr/>Optional CC email address: (comma separated for multiple
      email addresses) <html:text property="emailAddress"/></td>
    </tr>
    <tr>
      <td colspan="4"><html:hidden property="workingTemplateId"/>
      <html:submit value="Add" /> Recipient(s) to email</td>
    </tr>
  </table>
  </html:form>
  </logic:notEmpty>
    
  
  </body>
</html>