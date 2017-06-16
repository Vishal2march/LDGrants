<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminCheckStatus.jsp
 * Creation/Modification History  :
 *
 *     SH       3/1/07     Created
 *
 * Description
 * This page allows the admin to review and approve the grant project selected.
 * It includes a checklist of all items that should be completed, and allows the
 * admin to email comments to the applying Institution if corrections are needed.
 * It contains entries for the initial and final application review and approval.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>

<html>
<head>
  <title>Check Application Status for Statutory Aid Grant</title>
</head>
<body>

<h4>Check Application Status</h4>
      

  <table width="90%" align="center" border="1" class="graygrid" summary="for layout only" >        
    <tr>             
      <td width="25%"><b>Project Num</b></td>
      <td><b>Institution</b></td>
      <td width="25%"><b>Program</b></td>          
    </tr>        
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2"  value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" />
      </td>
      <td ><c:out value="${thisGrant.instName}" /></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>       
    <tr>
      <td colspan="3" height="15" />
    </tr>
    <tr>
      <td><b>Date Submitted</b></td>
      <td><b>Submitted By</b></td>
      <td><b>Version</b></td>
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
    View Complete Application -<a href="PrintAppServlet?i=app&a=true" target="_blank">HTML</a>  
        (opens in new window)<br/>
    View Complete Application -<a href="PrintAppServlet?i=apppdf&a=true" target="_blank">PDF</a> 
        (opens in new window)<br/>
  </p>
  
  
  
  <html:form action="/saveAdminReview"> 
      <input type="HIDDEN" name="p" value="sa" />
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
            <a href="saAdminNav.do?item=coversheet" class="blacklink">Cover Sheet</a>
          </td> 
          <td width="50%">
             <html:checkbox property="instauthyn" />
             <a href="saAdminNav.do?item=auth" class="blacklink">Institutional Authorization</a>
          </td>          
        </tr>                
        <tr> 
           <td width="50%">
            <html:checkbox property="projdescyn" />
            <a href="AdminDescription.do?m=admin" class="blacklink">Project Description</a>
          </td>                   
          <td width="50%">
            <html:checkbox property="fs20yn" />
            <a href="FsFormServlet?i=fs20pdf" target="_blank" class="blacklink">FS-20 (PDF)</a><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="FsFormServlet?i=fs20" target="_blank" style="color:rgb(0,0,0);">FS-20 (HTML)</a>
          </td>
        </tr>                
        <tr >    
          <td width="50%">
            <html:checkbox property="amtreqyn" />
            <a href="saAdminNav.do?item=budget&m=sa" class="blacklink">Project Budget</a>
            - Amount Requested
          </td>                      
        </tr>      
        <%-- amendment checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr>                
          <td width="50%">
             <html:checkbox property="fs10aComp" />
             <a href="saAdminNav.do?item=fs10a" class="blacklink">Amendment Summary</a> (FS-10-A)
          </td>                  
       </tr>  
        <%-- final application checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr>                
          <td width="50%">
             <html:checkbox property="finalnarrativeyn" />
             <a href="saAdminNav.do?item=finalrpt" class="blacklink">Final Narrative</a>
          </td>                  
          <td width="50%">
            <html:checkbox property="finalsignoffyn" />
            <a href="saAdminNav.do?item=auth" class="blacklink">Final Report Signoff</a>
          </td>
       </tr>  
       <tr>
        <td width="50%">
           <html:checkbox property="expsubyn" />         
           <a href="saAdminNav.do?item=budget&m=sa" class="blacklink">Project Budget</a>
           - Expenses Submitted
        </td>  
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
        <td colspan="2" align="center"><html:submit value="Save Review" /></td>             
      </tr>
    </table>
   </html:form>
    
    
    
    
  <%--Corrections requested section --%>
  <form method="POST" action="adminCommentNav.do">
    <table align="center" width="90%" class="boxtype" summary="for layout only">
      <tr>
        <td align="center"><b>Corrections Requested:</b><br/>
          Enter a new comment or correction request.</td>
      </tr>
      <tr>
        <td align="center"><textarea cols="60" rows="10" name="appCorrection"></textarea></td>
      </tr>
      <tr>         
        <td align="center">
          <input type="HIDDEN" name="p" value="sa" />
          <input type="HIDDEN" name="item" value="add" />
          <input type="SUBMIT" name="btn" value="Save Comment" />
        </td>
      </tr>   
      <tr>
        <td align="center">
          <a href="adminCommentNav.do?item=comments&p=sa">Email or edit previous comments</a>
        </td>
      </tr>   
      <tr>
      <td align="center"><a href="adminEmailNav.do?item=emails&m=sa">View Emails Sent regarding this Grant</a></td>
    </tr>
    </table>
  </form>
  
    
       
  <%-- This is the approval section  --%>
  <html:form action="saveAdminReview" >
      <input type="HIDDEN" name="task" value="approve" />
      <input type="HIDDEN" name="p" value="sa" />
      <table width="90%" align="center" class="boxtype" summary="for layout only">
        <tr bgcolor="#E6DDEE">
          <td colspan="2" >
            <b>Approve Application - </b>
            Only the admin designated as approve may authorize the 
            approval of a new application or final report.  Use the link to send status emails to the Preservation Officer.
          </td>
        </tr>
        <tr>          
          <td><a href="saAdminNav.do?item=budget&m=sa" >Approve Budget Amounts</a></td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td>
            <html:checkbox property="initialappr" />              
            Application Approved
          </td>
          <td>
            <html:checkbox property="finalappr" />               
            Final Report Approved
          </td>
        </tr>
        <tr>
          <td height="10" ></td>
        </tr>
        <tr>
          <td>
            <html:checkbox property="appDenied" />              
            Application Denied
          </td>
        </tr>
        <tr>
          <td height="10" ></td>
        </tr>
        <tr>
          <td>
            <html:checkbox property="showscorecomm" />               
            Show summary of approved/denied amounts to applicant
          </td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center">
            <c:if test="${lduser.adminstat=='approve'}">
              <html:submit value="Save Approval" />
            </c:if>        
          </td>
        </tr> 
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center">
            <a href="AdminEmail.do">Grant Application Status Email</a>
          </td>            
        </tr>
    </table>
    </html:form> 


</body>
</html>

