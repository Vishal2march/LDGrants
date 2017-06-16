<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminAppDate.jsp
 * Creation/Modification History  :
 *
 *     SHusak       11/5/07     Created
 *
 * Description
 * This page allows the admin to view all start/end application date records from the database,
 * and to select a record and update.  Modified-cannot allow admin to update fundcode 7/30/09
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>    
    <STYLE>
	  .cpYearNavigation,.cpMonthNavigation { background-color:#C0C0C0; text-align:center; vertical-align:center; text-decoration:none; color:#000000; font-weight:bold; };
	  .cpDayColumnHeader, .cpYearNavigation,.cpMonthNavigation,.cpCurrentMonthDate,.cpCurrentMonthDateDisabled,.cpOtherMonthDate,.cpOtherMonthDateDisabled,.cpCurrentDate,.cpCurrentDateDisabled,.cpTodayText,.cpTodayTextDisabled,.cpText { font-family:arial; font-size:8pt; };
	  TD.cpDayColumnHeader { text-align:right; border:solid thin #C0C0C0;border-width:0px 0px 1px 0px; };
	  .cpCurrentMonthDate, .cpOtherMonthDate, .cpCurrentDate  { text-align:right; text-decoration:none; };
	  .cpCurrentMonthDateDisabled, .cpOtherMonthDateDisabled, .cpCurrentDateDisabled { color:#D0D0D0; text-align:right; text-decoration:line-through; };
	  .cpCurrentMonthDate, .cpCurrentDate { color:#000000; };	.cpOtherMonthDate { color:#808080; };
    TD.cpCurrentDate { color:white; background-color: #C0C0C0; border-width:1px; border:solid thin #800000; };
	  TD.cpCurrentDateDisabled { border-width:1px; border:solid thin #FFAAAA; };
	  TD.cpTodayText, TD.cpTodayTextDisabled { border:solid thin #C0C0C0; border-width:1px 0px 0px 0px;};
	  A.cpTodayText, SPAN.cpTodayTextDisabled { height:20px; };
	  A.cpTodayText { color:black; };	.cpTodayTextDisabled { color:#D0D0D0; };	.cpBorder { border:solid thin #808080; };
	 </STYLE>
   <script language="javascript" src="jscripts/calendar_popup.js"></script>
  </head>
  <body>
  
   
  <c:choose >
  <c:when test="${param.t=='update'}">  
  
  <h4>Update the application available date and due date</h4>
  
  <html:errors />
  <html:form action="/updateAppDate" >
  <table width="70%" align="center" summary="for layout only">    
    <tr>
      <td>Grant Program
      <html:hidden property="id" /><html:hidden property="module"/>
      <input type="HIDDEN" name="m" value='<c:out value="${AppDatesBean.module}" />' /></td>
            
      <td><html:hidden property="fccode"/>
          <c:choose>
              <c:when test="${AppDatesBean.fccode==80}">LGRMIF</c:when>
              <c:when test="${AppDatesBean.fccode==5}">CP Discretionary</c:when>
              <c:when test="${AppDatesBean.fccode==7}">CP Coordinated</c:when>
              <c:when test="${AppDatesBean.fccode==6}">CP Statutory</c:when>
              <c:when test="${AppDatesBean.fccode==20}">State Aid: CJH & NYHS</c:when>
              <c:when test="${AppDatesBean.fccode==86}">Library Construction $14 mill</c:when>
              <c:when test="${AppDatesBean.fccode==40}">Adult Literacy</c:when>
              <c:when test="${AppDatesBean.fccode==42}">Family Literacy</c:when>
          </c:choose>
      <%--<html:select property="fccode">
            <html:optionsCollection name="dropDownFunds" value="id" label="description" />
          </html:select>--%>
      </td>
    </tr>
    
    <tr>
      <td>Fiscal Year</td>
      <td><html:select property="fycode" >
            <html:optionsCollection name="dropDownList" value="id" label="description"/>
        </html:select>
        </td>
    </tr>
    
    <tr>
      <td>Fund Amount</td>
      <td><html:text property="totalfundStr"/></td>
    </tr>
    <tr>
      <td colspan="2"><b>*The date format is MM/DD/YYYY</b></td>
    </tr>
    
    <tr>
      <td valign="top">Application Available Date
      <script type="text/javascript"> var cal = new CalendarPopup(); cal.showNavigationDropdowns();</script>
   
      <html:text property="startdateStr" size="8" />
      <A HREF="#" onClick="cal.select(AppDatesBean.startdateStr,'anchor1','M/dd/yyyy'); return false;" 
              NAME="anchor1" ID="anchor1"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
              <div id="testdiv1"></div></td>
              
      <td valign="top">Application Close Date
      <script type="text/javascript"> var cal2 = new CalendarPopup(); cal2.showNavigationDropdowns();</script>
   
      <html:text property="duedateStr" size="15" />
      <A HREF="#" onClick="cal2.select(AppDatesBean.duedateStr,'anchor2','M/dd/yyyy h:mma'); return false;" 
              NAME="anchor2" ID="anchor2"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
              <div id="testdiv2"></div></td>
    </tr>
    
    <tr>    
    <td valign="top">
    <%--lgrmif uses amendment due date; construct uses begin finalRpt date; all others use interim rpt due date--%>
    <c:choose>         
         <c:when test="${AppDatesBean.fccode==80}">FS-10-A Due Date</c:when>
         <c:when test="${AppDatesBean.fccode==86}">Final Report Available Date</c:when>
         <c:otherwise>Interim Report Close Date</c:otherwise>
    </c:choose>
          <script type="text/javascript"> var cal4 = new CalendarPopup(); cal4.showNavigationDropdowns();</script>
          <html:text property="interimrptdateStr" size="8" />
          <A HREF="#" onClick="cal4.select(AppDatesBean.interimrptdateStr,'anchor4','M/dd/yyyy'); return false;" 
                  NAME="anchor4" ID="anchor4"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
          <div id="testdiv4"></div></td>  
          
    <td valign="top">Final Report Close Date
      <script type="text/javascript"> var cal3 = new CalendarPopup(); cal3.showNavigationDropdowns();</script>
      <html:text property="finalrptdateStr" size="15" />
      <A HREF="#" onClick="cal3.select(AppDatesBean.finalrptdateStr,'anchor3','M/dd/yyyy h:mma'); return false;" 
              NAME="anchor3" ID="anchor3"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
              <div id="testdiv3"></div></td>
    </tr>    
    
    
    <tr>
        <td valign="top">At-home Review Close Date
        <script type="text/javascript"> var cal5 = new CalendarPopup(); cal5.showNavigationDropdowns();</script>
        <html:text property="reviewdateStr" size="14" />
        <A HREF="#" onClick="cal5.select(AppDatesBean.reviewdateStr,'anchor5','M/dd/yyyy h:mma'); return false;" 
        NAME="anchor5" ID="anchor5"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
        <div id="testdiv5"></div></td>
    </tr>    
    
    
    
    <tr>
        <td valign="top">Grant Period End Date (optional)
        <script type="text/javascript"> var cal6 = new CalendarPopup(); cal6.showNavigationDropdowns();</script>
        <html:text property="extensiondateStr" size="8" />
        <A HREF="#" onClick="cal6.select(AppDatesBean.extensiondateStr,'anchor6','M/dd/yyyy'); return false;" 
        NAME="anchor6" ID="anchor6"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
        <div id="testdiv6"></div>
        <br/>
        The Grant Period End Date is used to validate expense "Encumbrance Dates" if there is an extension 
        beyond the 7/1 - 6/30 grant period.<br/><br/></td>
    </tr>    
   
        
    <tr>
      <td colspan="2"><html:submit value="Save" /></td>
    </tr>
    <tr>
      <td colspan="2"/>
    </tr>
    
    <c:if test="${AppDatesBean.fccode=='20'}">
      <tr>
        <td colspan="2"><b>For State Aid: CJH & NYHS Only</b>  
          <c:url var="adminUrl" value="staidAdminAlloc.do">
            <c:param name="i" value="viewAlloc"/>
            <c:param name="fy" value="${AppDatesBean.fycode}"/>
          </c:url>
             <a href='<c:out value="${adminUrl}" />'>Set up maximum budget (appropriation) amount</a>       
          </td>
      </tr>
    </c:if>
  </table>
  </html:form>
  
  
  </c:when>
  <c:otherwise >
  
  <br/>
  <table align="center" width="90%" summary="for layout only">
    <tr>
      <th colspan="6">Grant application available date and due date</th>
    </tr>
    <tr>  
      <td colspan="6">The following list displays the application available date and due date for application 
      submissions.  The grant program for a particular fiscal year will become available to 
      the applicant on the selected date, and they can submit the application until the due date.</td>
    </tr>    
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td><b>Action</b></td>
      <td><b>Grant Program</b></td>
      <td><b>Fiscal Year</b></td>
      <td><b>Application Available Date</b></td>
      <td><b>Due Date</b></td>
      <td><b>Fund Amount</b></td>
    </tr>
    
    <c:forEach var="row" items="${allDateRecords}" >
    <c:url var="itemURL" value="adminAppDates.do">
      <c:param name="item" value="daterecord" />
      <c:param name="id" value="${row.id}" />
      <c:param name="m" value="${param.m}" />
    </c:url>
      <tr>
        <td><a href='<c:out value="${itemURL}"/>'>Update</a></td>
        <td><c:out value="${row.fundcode}" /></td>
        <td><c:out value="${row.fiscalyear}" /></td>
        <td><fmt:formatDate value="${row.startdate}" pattern="MM/dd/yyyy" /></td>
        <td><fmt:formatDate value="${row.duedate}" pattern="MM/dd/yyyy" /></td>
        <td><fmt:formatNumber value="${row.totalfund}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </c:forEach>
    
    <tr>  
      <td height="20" />
    </tr>
    <tr>
      <td colspan="6">
        <c:url var="addURL" value="adminAppDates.do">
          <c:param name="item" value="loadAdd" />
          <c:param name="m" value="${param.m}" />
        </c:url>        
        <a href="<c:out value='${addURL}' />">Add new available/due date record</a>
       </td>
    </tr>
   </table>     
  
  </c:otherwise>
  </c:choose>

  
  </body>
</html>
