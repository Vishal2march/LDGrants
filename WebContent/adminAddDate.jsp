<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminAddDate.jsp
 * Creation/Modification History  :
 *
 *     SHusak       11/5/07     Created
 *
 * Description
 * This page allows the admin to add a record into the App_Dates table for a new
 * start/end date by fiscalyear and grantprogram.  Action form validates the add record. 
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
      
  <h4>Add Grant application available date/due date and Fund Amount</h4>
  
    
  <html:errors />
  <html:form action="/addAppDate" >
  <table width="80%" align="center" summary="for layout only">
    <tr>
      <td colspan="2">Choose the begin and end dates that the application will be available for during
      the selected fiscal year.</td>
    </tr>
    <tr>
      <td>Grant Program</td>
      <td><%--<html:select property="fccode">
            <html:optionsCollection name="dropDownFunds" value="id" label="description"/>
          </html:select>--%><html:hidden property="module"/>
          <c:choose>
          <c:when test="${AppDatesBean.module=='lg'}">
            <html:select property="fccode">
                <option value="80">LGRMIF</option>
            </html:select>
          </c:when>
          <c:when test="${AppDatesBean.module=='lit'}">
            <html:select property="fccode">
                <option value="40">Adult Literacy</option>
                <option value="42">Family Literacy</option>
            </html:select>
          </c:when>
          <c:when test="${AppDatesBean.module=='cp'}">
            <html:select property="fccode">
                <option value="5">CP Discretionary</option>
                <option value="6">CP Statutory</option>
                <option value="7">CP Coordinated</option>
                <option value="20">State Aid: CJH & NYHS</option>
            </html:select>
          </c:when>
          <c:when test="${AppDatesBean.module=='cn'}">
            <html:select property="fccode">
                <option value="86">Library Construction $14 mill</option>
            </html:select>
          </c:when>
          </c:choose>
      </td>
    </tr>
    <tr>
      <td>Fiscal Year</td>
      <td><html:select property="fycode" >
            <html:optionsCollection name="dropDownList" value="id" label="description" />           
          </html:select>
        </td>
    </tr>
    
    <tr>
      <td>Fund Amount</td>
      <td><html:text property="totalfundStr"/> (Ex. $300,000)</td>
    </tr>    
    <tr>
      <td colspan="2"><b>*The date format is MM/DD/YY</b></td>
    </tr>
    
    <tr>
      <td valign="top">Application Available Date
          <script type="text/javascript"> var cal = new CalendarPopup(); cal.showNavigationDropdowns();</script>
          <html:text property="startdateStr" size="8" />
          <A HREF="#" onClick="cal.select(AppDatesBean.startdateStr,'anchor1','M/dd/yyyy'); return false;" 
              NAME="anchor1" ID="anchor1"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
          <div id="testdiv1"></div></td>              
    
      <td valign="top">Application Due Date
          <script type="text/javascript"> var cal2 = new CalendarPopup(); cal2.showNavigationDropdowns();</script>
          <html:text property="duedateStr" size="15" />
          <A HREF="#" onClick="cal2.select(AppDatesBean.duedateStr,'anchor2','M/dd/yyyy h:mma'); return false;" 
                  NAME="anchor2" ID="anchor2"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
          <div id="testdiv2"></div></td>                          
    </tr>
    
    <tr>
    <td valign="top">
    <%--lgrmif uses amendment due date; all others use interim rpt due date--%>
    <c:choose>         
         <c:when test="${AppDatesBean.module=='lg'}">FS-10-A Due Date</c:when>
         <c:when test="${AppDatesBean.module=='cn'}">Final Report Available Date</c:when>
         <c:otherwise>Interim Report Due Date</c:otherwise>
    </c:choose>
    
          <script type="text/javascript"> var cal4 = new CalendarPopup(); cal4.showNavigationDropdowns();</script>
          <html:text property="interimrptdateStr" size="8" />
          <A HREF="#" onClick="cal4.select(AppDatesBean.interimrptdateStr,'anchor4','M/dd/yyyy'); return false;" 
                  NAME="anchor4" ID="anchor4"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
          <div id="testdiv4"></div></td>                          
      
    <td valign="top">Final Report Due Date
          <script type="text/javascript"> var cal3 = new CalendarPopup(); cal3.showNavigationDropdowns();</script>
          <html:text property="finalrptdateStr" size="15" />
          <A HREF="#" onClick="cal3.select(AppDatesBean.finalrptdateStr,'anchor3','M/dd/yyyy h:mma'); return false;" 
                  NAME="anchor3" ID="anchor3"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
          <div id="testdiv3"></div></td>                          
    </tr>
    
    
    <tr>
        <td valign="top">At-home Review Due Date
        <script type="text/javascript"> var cal5 = new CalendarPopup(); cal5.showNavigationDropdowns();</script>
        <html:text property="reviewdateStr" size="8" />
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
      <td height="20" />
    </tr>
    <tr>
      <td colspan="2">
        <input type="HIDDEN" name="m" value='<c:out value="${AppDatesBean.module}" />' />
        <html:submit value="Save" /></td>
    </tr> 
  </table>  
  </html:form>
  
    
  </body>
</html>
