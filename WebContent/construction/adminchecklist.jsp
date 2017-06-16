<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  </head>
  <body>
  
  
  <h4>Public Library Construction Application</h4>

  <table align="center" width="80%" border="1" class="boxtype"  summary="for layout only"> 
  <tr>
      <td><b>Project Number:</b>&nbsp;
      03<fmt:formatNumber value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
  </tr>
  <tr>
     <td><b>Member Library:</b>&nbsp;<c:out value="${thisGrant.instName}" /></td>
  </tr>
  <tr>
     <td><b>Project Title:</b>&nbsp;<c:out value="${thisGrant.title}" /></td>
  </tr>
  
  <%-- test for applicant submission to pls--%>
  <c:choose>
  <c:when test="${appStatus.initialsubmitted=='false'}">
        <tr>
            <td><font color="red">This application has not yet been submitted by the member library!</font></td>
        </tr>
  </c:when>
  </c:choose>
  
  <%-- test for pls submission to ld --%>
  <c:choose>
  <c:when test="${assignBean==null || assignBean.ratingComplete=='false'}">
        <tr>
            <td><font color="red">This application has not yet been submitted to LD by the PLS!</font></td>
        </tr>
  </c:when>
  </c:choose>
  
  
  <logic:notEmpty name="allSubmissions" >
  <tr>
    <td height="30">&nbsp;</td>
  </tr>
  <tr>
    <td>
        <table width="100%">
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
        </table>
    </td>
  </tr>
  </logic:notEmpty>  
  
  
  <tr>
    <td height="30">&nbsp;</td>
  </tr>  
  <tr>
    <td align="center">
    Please use the following links to <i>print</i>
    or <i>save</i> the application to your desktop:<br/>
    
    <a href="PrintAppServlet?i=evalform" target="_blank">Evaluation & Reduced Match Form - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=evalformpdf" target="_blank">Evaluation & Reduced Match Form - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=cover" target="_blank">Application Form - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=coverpdf" target="_blank">Application Form - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=otherfund" target="_blank">Additional Funds - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=otherfundpdf" target="_blank">Additional Funds - PDF</a>
    <br/><br/>     
    <a href="PrintAppServlet?i=narr" target="_blank">Narratives - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=narrpdf" target="_blank" >Narratives - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=budget&a=true" target="_blank">Budget - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;     
    <a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank">Budget - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=finalexp" target="_blank">Final Expenses - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;     
    <a href="PrintAppServlet?i=finalexppdf" target="_blank">Final Expenses - PDF</a>
    <br/><br/>   
    <a href="PrintAppServlet?i=app&a=true" target="_blank">Complete Application HTML</a>
     <br/><br/> 
    </td>
  </tr>
  </table>
  
  <br/><br/>
  
  <table width="80%" align="center" class="boxtype" summary="for layout only"> 
  <tr>
    <th bgcolor="#7AC5CD">Admin - Construction Grant Program Checklist</th> 
  </tr>
  <tr>
    <td><a href="cnAdminNav.do?item=evalform">Evaluation Form</a></td> 
  </tr>  
  <tr>
    <td><a href="cnAdminNav.do?item=matchform">Reduced Match Justification Form</a></td> 
  </tr>  
  <tr>
    <td><a href="cnAdminNav.do?item=coversheet">Application Form</a> - 
        &nbsp;Update the <a href="cnAdminNav.do?item=viewProjManager">Project Manager</a> contact information</td>
  </tr>
  <tr>
    <td><a href="cnAdminNav.do?item=additionalfunds">Additional Funding Sources</a></td>
  </tr>  
  <tr>
    <td><a href="cnAdminNav.do?item=narrativeadmin">Project Narratives</a></td> 
  </tr>    
  <tr>
    <td><a href="cnAdminNav.do?item=budgetadmin">Budget</a></td>
  </tr>  
  
   <tr>
    <td><a href="cnAdminNav.do?item=attachment">Attachments</a></td>
  </tr> 
  <tr>
    <td>FS-10 Form &nbsp;&nbsp;&nbsp;<a href="FsFormServlet?i=fs10&fy=0" target="_blank">HTML</a> 
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank">PDF</a></td>
   </tr>
   <tr>
    <td><a href="cnAdminEmailNav.do?item=emails&m=cn">View Emails Sent for this Application</a></td>
  </tr> 
  <tr>
    <td><a href="cnAdminNav.do?item=verifyinitial">View Submission Validation Messages</a></td>
  </tr> 
  <tr>
      <td height="20"></td>
    </tr>
    
  <tr>
    <th height="10" bgcolor="#7AC5CD">Admin - Final Expense Checklist</th>
  </tr>
  <tr>
    <td><a href="cnAdminNav.do?item=finalexpense">Budget - Final Expenses</a></td>
  </tr>
  <tr>
    <td>FS-10-F Form &nbsp;&nbsp;&nbsp;<a href="FsFormServlet?i=fs10f&fyf=0" target="_blank">HTML</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="FsFormServlet?i=fs10fpdf&fyf=0" target="_blank">PDF</a>
     <br/>(To be mailed <B>AFTER</b> the Final Expenses have been submitted
     to LD and approved by LD)</td> 
   </tr>
   <%--removed 7/13/12 per MLT<tr>
      <td><a href="cnAdminNav.do?item=completion">Project Completion Form</a></td> 
   </tr>--%>
   <tr>
     <td>FS-10-A Budget Amendment Form &nbsp;&nbsp;&nbsp;
       <a href="FsFormServlet?i=fs10a" target="_blank">HTML</a> &nbsp;&nbsp;&nbsp;
       <a href="FsFormServlet?i=fs10apdf" target="_blank">PDF</a>  
       <br/>(Only if there is an amendment to the approved project budget)</td> 
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    
    <!-- per LW 04/27/2016 -->
     
<c:if test="${thisGrant.fycode ==16}">
    <tr>
      <th height="10" bgcolor="#7AC5CD">Admin - M/WBE Requirement</th>
    </tr>
    <html:form action="/saveCnAdminMwbe">
      <tr>
          <td>NEW: (REQUIRED) <a href="http://www.nysl.nysed.gov/libdev/construc/14m/instruct.htm#mwbe" target="_blank">M/WBE Requirement</a>
          <br/>All applicants requesting $25,000 or more are required to comply with NYSED’s Minority and 
        Women-Owned Business Enterprises (M/WBE) policy. One of the following must be selected:<br/>
          <html:radio property="mwbeParticipation" value="5"/> Not Applicable<br/>
          <html:radio property="mwbeParticipation" value="1"/> Full Participation<br/>
          <html:radio property="mwbeParticipation" value="2"/> Partial Participation, Partial Request for Waiver<br/>
          <html:radio property="mwbeParticipation" value="3"/> No Participation, Request for Complete Waiver<br/>
          <html:radio property="mwbeParticipation" value="6"/> Preferred Source<br/>
          <html:hidden property="grantid"/> </td>
      </tr>    
      <tr>
        <td>For further information and forms see 
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/instruct.htm#mwbe" target="_blank">M/WBE Requirement</a>.
        All applicable forms should be printed, completed, signed and electronically attached
        to your grant application 
        as a pdf document. The original forms with original signatures must be kept on file, and can be requested at 
        any point in the future, should the need arise.        
        </td>
    </tr>
      <tr>
        <td><html:submit value="Save MWBE"/></td>
      </tr>
    </html:form>
 </c:if>
  </table>
  


  <c:if test="${assignBean!=null}">
  
  <br/>
  <%--  UNLOCKING APPLICATION --%>
  <table width="80%" align="center" class="boxtype" summary="for layout only">
  <tr>
    <td  bgcolor="#7AC5CD" colspan="2"><b>Admin Tasks</b></td>
  </tr>
  <tr>
    <td height="15" />
  </tr>
  <tr>
    <td>PLS access to edit/modify this grant application: <c:choose>
        <c:when test="${assignBean.systemLockOut=='false'}"><b>Unlocked</b></c:when>
        <c:when test="${assignBean.systemLockOut=='true'}"><b>Locked</b></c:when>
        </c:choose></td>
  </tr>
  <tr>
    <td><c:url var="unlockurl" value="cnAdminNav.do">
            <c:param name="item" value="unlock"/>
            <c:param name="lockout" value="${!assignBean.systemLockOut}"/>
        </c:url>
        <a href='<c:out value="${unlockurl}"/>'>Lock/Unlock Application</a> for editing by 
        member or system</td>
  </tr> 
  <tr>
    <td height="15"><hr/></td>
  </tr>
  <tr>
    <td>Application submitted by PLS to LD:<c:choose>
        <c:when test="${assignBean.ratingComplete=='false'}"><b>Not Submitted</b></c:when>
        <c:when test="${assignBean.ratingComplete=='true'}"><b>Submitted</b></c:when>
        </c:choose></td>
  </tr>
  <tr>
    <td><c:url var="unsuburl" value="cnAdminNav.do">
            <c:param name="item" value="unsubmitpls"/>
        </c:url>
        <a href='<c:out value="${unsuburl}"/>'>Unsubmit</a>
         the application and remove it from the Admin Home Page)</td>
  </tr>
  <tr>
    <td height="15"></td>
  </tr>
  
  </table>
  </c:if>
  
  <br/>
  
  
  <%-- This is the approval section  --%>
  <html:form action="/saveAdminReview" > 
      <input type="HIDDEN" name="p" value="cn" />
      <input type="HIDDEN" name="task" value="approve" />
      <table width="80%" align="center" class="boxtype" summary="for layout only">
        <tr>
          <td  bgcolor="#7AC5CD" colspan="2"><b>Application Status</b></td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr><td><html:checkbox property="initialappr" />Application Approved by LD</td>
            <td><html:checkbox property="finalappr" />Final Report Approved by LD</td></tr>
        
        <tr><td><html:checkbox property="dasnysubmit" />Submit Application to DASNY</td>
            <td><html:checkbox property="appDenied" />Application Denied</td>
            </tr>
        
        <tr><td><html:checkbox property="dasnyapproved" disabled="true"/>Application Approved by DASNY</td>            
            </tr>
                
       <%-- <tr><td><html:checkbox property="bondcomplete" disabled="true"/>Bond council review complete</td>            
            </tr> --%>
            
        <tr>
          <td><html:checkbox property="showscorecomm" />               
            Allow applicant to view award amounts</td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center"><html:submit value="Save Approval" /></td>
        </tr> 
      </table>
   </html:form>
     
  </body>
</html>