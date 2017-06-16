<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminAllocation</title>
  </head>
  <body>
  
  <h3>State Aid: CJH & NYHS Appropriation</h3>
  
  
  <p>
  <b>Update State Aid Final Appropriation</b><br/>
  
  
  <html:form action="/staidSaveAllocation">
  <table width="60%">
    <tr>
      <th>Project#</th><th>Institution</th><th>Year</th><th>Appropriation</th>
    </tr>
    
    <logic:present name="AssignCollectionBean" property="allStateaidApps" >
    <logic:iterate name="AssignCollectionBean" property="allStateaidApps" id="stateaidItem" >
    
      <tr>
        <td>03<c:out value="${stateaidItem.fccode}" />-<c:out value="${stateaidItem.fycode}" />-
            <fmt:formatNumber value="${stateaidItem.projseqnum}" minIntegerDigits="4" pattern="####" /></td>
        <td><c:out value="${stateaidItem.instName}" /></td>
        <td><c:out value="${stateaidItem.fiscalyear}" /></td>
        <td><html:text name="stateaidItem" property="ldacAppropAmtStr" indexed="true" />
            <html:hidden name="stateaidItem" property="grantid" indexed="true" />
            <html:hidden name="stateaidItem" property="instID" indexed="true" />
            <html:hidden name="stateaidItem" property="fycode" indexed="true" /></td>      
      </tr>
    
    </logic:iterate>
    </logic:present>
    
    <tr>
      <td colspan="4"><html:submit value="Save"/>
                      <html:hidden property="fycode"/></td>
    </tr>    
  </table>
  </html:form>
    
  
  </p>
  
  </body>
</html>