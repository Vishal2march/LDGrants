<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  admincheckstatus.jsp
 * Creation/Modification History  :
 *
 *     SHusak  6/19/09     Created
 *
 * Description
 * This is LG admin page for updating status of grant.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
  
  <h5>Check Application Status</h5>    
  
    <html:errors /><%-- error msg for validating app declined--%>
  <table width="90%" align="center" border="1" class="graygrid" summary="for layout only" >        
    <tr>             
      <td><b>Project Number</b></td>
      <td><b>Institution</b></td>
    </tr>        
    <tr>
      <td>05<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>       
    <tr>
      <td colspan="3" height="15" />
    </tr>
    <tr>
      <td><b>Date Submitted</b></td>
      <td><b>Submitted By</b></td>
      <td ><b>Version</b></td>
    </tr>
    
    <c:forEach var="submitBean" items="${allSubmissions}" >
      <tr>
        <td><fmt:formatDate value="${submitBean.dateSubmitted}" pattern="MM/dd/yyyy"/></td>
        <td><c:out value="${submitBean.userSubmitted}"/></td>
        <td><c:out value="${submitBean.versionSubmitted}"/></td>
      </tr>
    </c:forEach>        
    
    <logic:empty name="allSubmissions">
      <tr>
        <td colspan="3"><font color="Red">Application has not been submitted</font></td>
      </tr>
    </logic:empty>
  </table>
    
  
  <br/>
  <p align="center">
    The following links open in a new window<br/>
    
    <a href="PrintAppServlet?i=cover" target="_blank">View Application Sheet - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=coverpdf" target="_blank">View Application Sheet - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=narr" target="_blank">View Project Narrative - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=narrpdf" target="_blank" >View Project Narrative - PDF</a>
    <br/><br/>    
    <a href="PrintAppServlet?i=budget&a=true" target="_blank">View Project Budget - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;     
    <a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank" >View Project Budget - PDF</a>
    <br/><br/>    
    <a href="PrintAppServlet?i=vq" target="_blank">View VQ Form - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=vqpdf" target="_blank">View VQ Form - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=im" target="_blank">View IM Form - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=impdf" target="_blank">View IM Form - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=app&a=true" target="_blank">View Application - HTML</a>
  </p>  <br/>
  
  
  
  <html:form action="/saveAdminReview" > 
      <input type="HIDDEN" name="p" value="lg" />
      <input type="HIDDEN" name="task" value="review" />
      <table width="90%" align="center" summary="for layout only">
        <tr bgcolor="Silver">
          <td colspan="2" ><b>Review Application - </b>
            use the following links to review the applicant's submissions.<br/>
            A check mark indicates that the item has been reviewed and accepted as complete.  
          </td>
        </tr>
        <tr>                       
          <td width="50%">
            <html:checkbox property="coversheetyn" />
            <a href="lgAdminNav.do?item=coversheet">Application Sheet</a>
            (Update <a href="lgAdminNav.do?item=bonuspts">Application Sheet Items</a>)
          </td> 
          <td><html:checkbox property="instauthyn" />Authorizations</td>          
        </tr>                
        <tr> 
           <td width="50%">
              <html:checkbox property="projdescyn" />
              <a href="lgAdminNav.do?item=narrative&p=lg">Project Narrative</a>
           </td>            
           <td >
             <html:checkbox property="fs20yn" />
             <a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">Proposed Budget Summary (FS-10 PDF)</a><br/>
             &nbsp;&nbsp;&nbsp;&nbsp;
             <a href="FsFormServlet?i=fs10&fy=0" target="_blank">Proposed Budget Summary (FS-10 HTML)</a>
          </td>
        </tr>                
        <tr>    
          <td width="50%"><html:checkbox property="amtreqyn" />
           <a href="lgAdminNav.do?item=budget&m=lg">Project Budget</a> - Amount Requested</td>            
            <td><html:checkbox property="vqimyn"/>
                Vendor Quote/Imaging Microfilming</td>
        </tr> 
        <tr>
          <td height="20" />
        </tr>
        <tr> 
          <td>
            <a href="lgAdminNav.do?item=attachment">View Attached Documents</a>
          </td>
          <td width="50%">
            <a href="lgAdminNav.do?item=mwbe" class="blacklink">M/WBE Documents</a><br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="5"/> Not Applicable<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="1"/> Full Participation<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="2"/> Partial Participation, Partial Request for Waiver<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="3"/> No Participation, Request for Complete Waiver<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="6"/> Preferred Source<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="4"/> Deferred Compliance
            </td>
       </tr>
        <%-- final application checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr>
          <td><html:checkbox property="fs10aComp"/>
                <a href="lgAdminNav.do?item=fs10a">Amendment Summary</a><br/>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="FsFormServlet?i=fs10a" target="_blank">
                                        FS10A form HTML</a><br/>
                &nbsp;&nbsp;&nbsp;&nbsp;<a href="FsFormServlet?i=fs10apdf" target="_blank">
                                        FS10A form PDF</a></td>
          <td><html:checkbox property="statisticsyn"/>
                <a href="lgAdminNav.do?item=statistics&m=lg">Final Statistical Report</a></td>
        </tr>
        <tr>                
          <td width="50%">
             <html:checkbox property="finalnarrativeyn" />
             <a href="lgReadNarrative.do?t=readNarr&narrType=2&p=lg&m=admin">Final Project Narrative</a>
          </td>                  
          <td width="50%"><html:checkbox property="finalsignoffyn" />Final Report Signoff</td>
       </tr>  
       <tr>
        <td><html:checkbox property="expsubyn" />
          <a href="lgAdminNav.do?item=budget&m=lg">Final Project Budget</a> - Expenses Submitted</td>           
        <td>
          <html:checkbox property="fs10fyn" />
            <c:choose>
            <c:when test="${thisGrant.fycode>14}">
                <a href="FsFormServlet?i=fs10fpdf&fyf=0" target="_blank">Final Expenditure Report</a> FS-10-F Long (PDF)<br/>
                &nbsp;&nbsp;&nbsp;
                <a href="FsFormServlet?i=fs10f&fyf=0" target="_blank">Final Expenditure Report</a> FS-10-F Long (HTML)
            </c:when>
            <c:otherwise>
                <a href="FsFormServlet?i=shortfs10fpdf" target="_blank">Final Expenditure Report</a> FS-10-F (PDF)<br/>
                &nbsp;&nbsp;&nbsp;
                <a href="FsFormServlet?i=shortfs10f" target="_blank">Final Expenditure Report</a> FS-10-F (HTML)
            </c:otherwise>
            </c:choose>
        </td>
       </tr>        
             
       <tr>
        <td height="20" />
      </tr>
      <tr>
        <td colspan="2">
          <html:checkbox property="awaitingappr" />
          Awaiting Approval (this will change the application status to Complete)</td>
      </tr>
      <tr>
        <td colspan="2">
          <html:checkbox property="lockapp" />
          Unlock for Correction (any items above that are not checked will be unlocked)</td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td colspan="2" align="center"><html:submit value="Save Review" /></td>            
      </tr>
    </table>
    </html:form>

 
  <table align="center" width="90%" summary="for layout only">
    <tr bgcolor="Silver">
      <td><b>Administrative Tasks</b></td>
    </tr>
    <tr>
        <td><a href="lgAdminNav.do?item=scores&m=lg">Reviewer assignments, scores</a></td>
    </tr> 
    <tr>
      <td><a href="adminEmailNav.do?item=emails&m=lg">View Emails Sent regarding this Grant</a></td>
    </tr>
  </table>    
  <br/>
       
       
       
  <%-- This is the approval section  --%>
  <html:form action="/saveAdminReview" > 
      <input type="HIDDEN" name="p" value="lg" />
      <input type="HIDDEN" name="task" value="approve" />
      <table width="90%" align="center" summary="for layout only">
        <tr bgcolor="Silver">
          <td colspan="2" ><b>Approve Application</b></td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td>
            <html:checkbox property="initialappr" />Application Approved</td>
          <td>
            <html:checkbox property="finalappr" />Final Report Approved</td>
        </tr>
        <tr>
          <td><html:checkbox property="appDeclined"/>Award Declined
                <html:hidden property="declineId"/></td>
          <td><html:checkbox property="appDenied" />Application Denied</td>
        </tr>
        <tr>
            <td colspan="2">**The Amount & Date Declined are required if the 'Award Declined' box is checked.<br/>
            Amount Declined: <html:text property="amountDeclinedStr" size="8"/><br/>
            
            Date Declined:
            <script type="text/javascript"> var cal = new CalendarPopup(); cal.showNavigationDropdowns();</script>
            <html:text property="dateDeclined" size="8" />
            <A HREF="#" onClick="cal.select(appStatus.dateDeclined,'anchor1','M/dd/yyyy'); return false;" 
            NAME="anchor1" ID="anchor1"><img src="images/calendar.png" height="30" width="30" border="0" /></A>
            <div id="testdiv1"></div></td>
        </tr>
        <tr>
          <td height="10" ></td>
        </tr>
        <tr>
          <td>
            <html:checkbox property="showscorecomm" />               
            Show decision notes and award amounts to applicant
          </td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center">
            <c:if test="${lduser.lgadmin==true}">
              <html:submit value="Save Approval" />
            </c:if>
          </td>
        </tr> 
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center">Send Application Status Emails</td>            
        </tr>
      </table>
   </html:form>
  
  
  </body>
</html>
