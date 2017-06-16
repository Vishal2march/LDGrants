<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diAdminCheckstatus.jsp
 * Creation/Modification History  :
 *
 *     SHusak       2/5/08     Created
 *
 * Description
 * This is DI admin page for updating status of grant.
--%>
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
  
  <h4>Check Application Status</h4>    
  
  <html:errors /><%-- error msg for validating app declined--%>

  <table width="90%" align="center" class="boxtype" summary="for layout only" >        
    <tr>             
      <td><b>Project Num</b></td>
      <td><b>Institution</b></td>
      <td ><b>Title</b></td>          
    </tr>        
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
      <td><c:out value="${thisGrant.instName}" /></td>
      <td><c:out value="${thisGrant.title}" /></td>
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
    
    <a href="PrintAppServlet?i=budget&a=true" target="_blank">View Budget - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;     
    <a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank" >View Budget - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=narr" target="_blank">View Narratives - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=narrpdf" target="_blank" >View Narratives - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=cover" target="_blank">View Cover Sheet - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=coverpdf" target="_blank">View Cover Sheet - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=app&a=true" target="_blank">View Application - HTML</a>
  </p>  <br/>
  
  
  
  <html:form action="/saveAdminReview" > 
      <input type="HIDDEN" name="p" value="di" />
      <input type="HIDDEN" name="task" value="review" />
      <table width="90%" align="center" class="boxtype" summary="for layout only">
        <tr bgcolor="#E6DDEE">
          <td colspan="2" >
            <b>Review Application - </b>
            use the following links to review the applicant's submissions.<br/>
            A check mark indicates that the item has been reviewed and accepted as complete.  
          </td>
        </tr>
        <tr>                       
          <td width="50%">
            <html:checkbox property="coversheetyn" />
            <a href="diAdminNav.do?item=coversheet" class="blacklink">Cover Sheet</a>-
            Update <a href="diAdminNav.do?item=viewProjManager&m=di">Project Manager</a> info
          </td> 
          <td>
             <html:checkbox property="instauthyn" />
             <a href="diAdminNav.do?item=attachment"  class="blacklink">Authorizations</a>
             (attached document)
          </td>          
        </tr>                
        <tr> 
           <td width="50%">
              <html:checkbox property="projdescyn" />
              <a href="DiAdminNarrative.do?m=admin" class="blacklink">Project Description</a>
           </td>            
           <td >
             <html:checkbox property="fs20yn" />
             <a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank" class="blacklink">FS-10 (PDF)</a><br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="FsFormServlet?i=fs10&fy=0" target="_blank" class="blacklink">FS-10 (HTML)</a><br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="FsFormServlet?i=fs10DiApprpdf&fy=0" target="_blank" class="blacklink">FS-10 (PDF)</a> - with Award Amounts<br/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="FsFormServlet?i=fs10DiAppr&fy=0" target="_blank" class="blacklink">FS-10 (HTML)</a> - with Award Amounts
          </td>
        </tr>                
        <tr>    
          <td width="50%">
            <html:checkbox property="amtreqyn" />
            <a href="diAdminNav.do?item=budget&m=di" class="blacklink">Project Budget</a>
            - Amount Requested
          </td>        
          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="diAdminNav.do?item=attachment" class="blacklink">View Attached Documents</a>
          </td>
        </tr> 
        <tr>
          <td height="20"/>
        </tr>
        <tr>
          <td width="50%">
            <a href="diAdminNav.do?item=mwbe" class="blacklink">M/WBE Documents</a><br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="5"/> Not Applicable<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="1"/> Full Participation<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="2"/> Partial Participation, Partial Request for Waiver<br/>
              &nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="mwbeParticipation" value="3"/> No Participation, Request for Complete Waiver
            </td>
            <td>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a href="diAdminNav.do?item=prequal" class="blacklink">Prequalification Requirement</a>
            </td>
        </tr>
        <%-- amendment checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr> 
          <td><html:checkbox property="fs10aComp"/>
          <a href="diAdminNav.do?item=fs10a" class="blacklink">Amendment Summary</a> (FS-10-A)</td>  
       </tr>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <%-- final application checklist section --%>
        <tr>                
          <td width="50%">
             <html:checkbox property="finalnarrativeyn" />
             <a href="diAdminNav.do?item=finalrpt" class="blacklink">Final Narrative</a>
          </td>                  
          <td width="50%">
            <html:checkbox property="finalsignoffyn" />
            <a href="diAdminNav.do?item=attachment" class="blacklink">Final Report Signoff</a>
          </td>
       </tr>  
       <tr>
        <td width="50%">
             <html:checkbox property="expsubyn" />    
             <a href="diAdminNav.do?item=budget&m=di" class="blacklink">Project Budget</a>
             - Expenses Submitted
        </td> 
          
        <%--SH 8/4/14 per BL; replace short fs10f with long form starting 14/15 --%>
        <c:choose>
        <c:when test="${thisGrant.fycode<15}">
            <td>
              <html:checkbox property="fs10fyn" />
              <a href="FsFormServlet?i=shortfs10fpdf&fyf=0" target="_blank" class="blacklink">FS-10-F (PDF)</a><br/>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a href="FsFormServlet?i=shortfs10f&fyf=0" target="_blank" class="blacklink">FS-10-F (HTML)</a>
            </td>
        </c:when>
        <c:otherwise>
            <td>
              <html:checkbox property="fs10fyn" />
              <a href="FsFormServlet?i=fs10fpdf&fyf=0" target="_blank" class="blacklink">FS-10-F (PDF)</a><br/>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a href="FsFormServlet?i=fs10f&fyf=0" target="_blank" class="blacklink">FS-10-F (HTML)</a>
            </td>
        </c:otherwise>
        </c:choose>
        
       </tr>        
       <tr>
        <td height="20" />
      </tr>
      <tr>
        <td colspan="2">
          <html:checkbox property="awaitingappr" />
          Awaiting Approval (this will change the application status to Complete)
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <html:checkbox property="lockapp" />
          Unlock for Correction (any items above that are not checked will be unlocked)
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <html:checkbox property="educationapp" />
          Education Proposal (a checkmark indicates that Education Reviewer Rating Form will be used)
        </td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td colspan="2" align="center"><html:submit value="Save Review" /></td>            
      </tr>
    </table>
    </html:form>

 
  <table align="center" width="90%" class="boxtype" summary="for layout only">
    <tr bgcolor="#E6DDEE">
      <td><b>Administrative Tasks</b></td>
    </tr>
    <tr>
      <td><a href="adminCommentNav.do?item=comments&p=di">Add, edit, email comment</a></td>
    </tr>
    <tr>
      <td><a href="diAdminRevNav.do?item=assignments&m=di">Assign Reviewers for this Grant</a></td>
    </tr>
    <tr>
      <td><a href="diAdminRevNav.do?item=ratings&m=di">View Scores from Reviewers for this Grant</a></td>
    </tr>
    <tr>
      <td><a href="adminEmailNav.do?item=emails&m=di">View Emails Sent regarding this Grant</a></td>
    </tr>
  </table>    
  <br/>
       
       
       
  <%-- This is the approval section  --%>
  <html:form action="/saveAdminReview" > 
      <input type="HIDDEN" name="p" value="di" />
      <input type="HIDDEN" name="task" value="approve" />
      <table width="90%" align="center" class="boxtype" summary="for layout only">
        <tr bgcolor="#E6DDEE">
          <td colspan="2" >
            <b>Approve Application - </b>
            Only the admin designated as approve may authorize the 
            approval of a new application or final report.  Use the link to send status
            emails to the Preservation Officer.</td>
        </tr>
        <tr>         
          <td><a href="diAdminNav.do?item=budget&m=di" >Approve Budget Amounts</a></td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr><td><html:checkbox property="initialappr" />Application Approved</td></tr>
        <tr><td><html:checkbox property="finalappr" />Final Report Approved</td></tr>
        <tr><td><html:checkbox property="appDenied" />Application Denied</td></tr>
        <tr><td><html:checkbox property="appDeclined" />Award Declined
                <html:hidden property="declineId" /></td></tr>
        <tr>
            <td>**The following data is required if the 'Award Declined' box is checked.<br/>
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
          <td><html:checkbox property="showscorecomm" />               
            Allow applicant to view Reviewers scores/comments and award amounts<br/>
            Preview the <a href="diAdminNav.do?item=comments">score/comment summary</a> 
            that will be available to the applicant</td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center">
            <c:if test="${lduser.admindisc=='approve'}">
              <html:submit value="Save Approval" />
            </c:if>
          </td>
        </tr> 
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center"><a href="DiAdminEmail.do">Send Application Status Emails</a></td>            
        </tr>
      </table>
   </html:form>
  
  
  </body>
</html>
