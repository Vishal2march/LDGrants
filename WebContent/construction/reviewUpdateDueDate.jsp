<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
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
  
  <h4>Update PLS Due Date</h4>
  
  
  <html:form action="/saveCnReviewDate">
  <table width="90%" align="center" class="boxtype" summary="for layout only">
    <tr>      
        <td>Fiscal Year</td>
        <td><c:out value="${allocBean.year}"/></td>      
    </tr>
    <tr>
        <td>System Due Date (format must be mm/dd/yyyy)</td>
        <td>
           <script type="text/javascript"> var cal = new CalendarPopup(); cal.showNavigationDropdowns();</script>
           <html:text property="dueDateStr" />
           <A HREF="#" onClick="cal.select(allocBean.dueDateStr,'anchor1','M/dd/yyyy'); return false;" 
              NAME="anchor1" ID="anchor1"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
           <div id="testdiv1"></div>   
        </td>   
    </tr>
    <tr>
        <td>Allocation</td>
        <td><fmt:formatNumber value="${allocBean.initialAlloc}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <html:hidden property="systemYearDetailId"/>
            <html:submit value="Save"/>
        </td>
    </tr> 
  </table>
  </html:form>
  
  </body>
</html>