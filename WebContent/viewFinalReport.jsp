<%--
* @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  viewFinalReport.jsp
 * Creation/Modification History  :
 *
 *     SH       3/1/07     Created
 *
 * Description
 * This page allows the admin to view a read only version of the
 * final narrative information that was entered by the applicant.
 * Used for both SA and CO admin final reports and SA prior apps and
 * CO participating inst view.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>

  <h4>Final Report Narrative</h4>
 
 <c:if test="${param.m=='coa'}">
 <form method="post" action="coReadNarrative.do?t=readNarr">  
    <select name="narrType">
      <option value="2">Final Narrative (year 1)</option>
      <option value="89">Final Narrative (year 2)</option>
      <option value="90">Final Narrative (year 3)</option>
    </select>
    <input type="HIDDEN" name="m" value="adminfinal" />
    <input type="hidden" name="p" value="co" />
    <input type="SUBMIT" value="Select" />
  </form>
  </c:if>
 
  <table class="boxtype" width="90%" summary="for layout only" align="center">
      <tr bgcolor="Silver">
        <th><c:out value="${projNarrative.narrativeTitle}" /></th>
      </tr>
    <tr>
      <td align="center">
        The Final Narrative should correspond closely to the plan of work that 
        you submitted.  It should begin with a chronological timetable 
        recording the beginning date for the year; hiring dates and duration of work for 
        personnel hired with these funds; consultant's schedules and dates when their reports 
        were received; beginning and ending dates for all contractual services; and dates 
        of all other significant activities carried out during the year.  
        The second part of the narrative should provide a description 
        of every aspect of the expenditure of the funds and how they were accomplished. 
        You should note particularly any changes from your original plan of work. <hr/>
      </td>
    </tr>
    <tr>
      <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
    </tr>
  </table>
   
  
  <c:choose >
  <c:when test="${param.m=='saa'}">
      <p align="center">
      <c:url var="backURL" value="saAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>
      <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:when>
  
  <c:when test="${param.m=='coa'}">
      <p align="center">
      <c:url var="backURL" value="coAdminNav.do">
        <c:param name="id" value="${grantid}" />
        <c:param name="item" value="grant" />
      </c:url>
      <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:when>
  </c:choose>
  
  </body>
</html>
