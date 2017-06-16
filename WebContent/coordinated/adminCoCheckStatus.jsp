<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  adminCoCheckStatus.jsp
 * Creation/Modification History  :
 *     
 *     SHusak       6/15/07     Modified
 *
 * Description
 * This page allows the admin to review the CO application and approve it,
 * also to submit comments regarding the application.  It has links to all
 * portions of the selected app (narratives, budget, etc).
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>  
  
  <h4>Check Application Status</h4>    
  

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
    <br/>
    <a href="PrintAppServlet?i=narr" target="_blank">View Narratives - HTML</a>
     &nbsp;&nbsp;&nbsp;&nbsp;
    <a href="PrintAppServlet?i=narrpdf" target="_blank" >View Narratives - PDF</a>
    <br/>
    <a href="PrintAppServlet?i=cover" target="_blank">View Cover Sheet - HTML</a>
     &nbsp;&nbsp;&nbsp;&nbsp;
    <a href="PrintAppServlet?i=coverpdf" target="_blank">View Cover Sheet - PDF</a>  
    <br/><br/>
    <a href="PrintAppServlet?i=app&a=true" target="_blank">View Complete Application - HTML</a>
  </p>
  
  
  
  <html:form action="/saveAdminReview">
      <input type="HIDDEN" name="p" value="co" />
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
            <a href="coAdminNav.do?item=coversheet" class="blacklink">Cover Sheet</a> 
            - Update <a href="coAdminNav.do?item=viewProjManager&m=co">Project Manager</a> info
          </td> 
          <td >
             <html:checkbox property="instauthyn" />
             <a href="coAdminNav.do?item=auth" class="blacklink">Authorizations</a>
          </td>          
        </tr>                
        <tr> 
           <td width="50%">
            <html:checkbox property="projdescyn" />
            <a href="AdminCoNarrative.do?m=admin" class="blacklink">Project Description</a>
          </td>            
          <td>
            <html:checkbox property="fs20yn" />
            <a href="AdminFsSelect.do" class="blacklink"> FS-10 Form</a> -using both Amount Requested
            and Amount Approved</td>
        </tr>                
        <tr>    
          <td width="50%">
            <html:checkbox property="amtreqyn" />
            <a href="coAdminNav.do?item=budget&m=co" class="blacklink">Project Budget</a>
            - Amount Requested
          </td>     
          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="coAdminNav.do?item=attachment" class="blacklink">Attached Documents</a></td>
        </tr> 
        <%-- amendment checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr> 
          <td colspan="2">
            <html:checkbox property="fs10aComp"/>
            <a href="coAdminNav.do?item=fs10a" class="blacklink">Amendment Summary</a> (FS-10-A)           
          </td>
       </tr>
        <%-- final application checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr>                
          <td width="50%">
             <html:checkbox property="finalnarrativeyn" />
             <a href="coAdminNav.do?item=finalrpt" class="blacklink">Final Narrative</a>
          </td>                  
          <td width="50%">
            <html:checkbox property="finalsignoffyn" />
            <a href="coAdminNav.do?item=auth" class="blacklink">Final Report Signoff</a>
          </td>
       </tr>  
       <tr>
        <td width="50%">
           <html:checkbox property="expsubyn" />           
           <a href="coAdminNav.do?item=budget&m=co" class="blacklink">Project Budget</a>
           - Expenses Submitted
        </td> 
          
        <td>
          <html:checkbox property="fs10fyn" />   
          <a href="AdminFsSelect.do" class="blacklink"> FS-10-F Form</a>
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
        <td colspan="2">
          <html:checkbox property="educationapp" />
          Education Proposal (a checkmark indicates that Education Reviewer Rating Form will be used)
        </td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
        <td colspan="2">Contract Number &nbsp;
        <html:text property="contractnum" /></td>
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
      <td><a href="adminCommentNav.do?item=comments&p=co">Add, edit, email comment</a></td>
    </tr>
    <tr>
      <td><a href="coAdminRevNav.do?item=assignments&m=co">Assign Reviewers for this Grant</a></td>
    </tr>
    <tr>
      <td><a href="coAdminRevNav.do?item=ratings&m=co">View Scores from Reviewers for this Grant</a></td>
    </tr>
     <tr>
      <td><a href="adminEmailNav.do?item=emails&m=co">View Sent Emails regarding this Grant</a></td>
    </tr>
  </table>
  <br/>
   
       
  <%-- This is the approval section  --%>
  <html:form action="/saveAdminReview" > 
      <input type="HIDDEN" name="task" value="approve" />
      <input type="HIDDEN" name="p" value="co" />
      <table width="90%" align="center" class="boxtype" summary="for layout only">
        <tr bgcolor="#E6DDEE">
          <td colspan="2" >
            <b>Approve Application - </b>
            Only the admin designated as approve may authorize the 
            approval of a new application or final report.  Use the link to send status
            emails to the Preservation Officer.
          </td>
        </tr>
        <tr>         
          <td><a href="coAdminNav.do?item=budget&m=co" >Approve Budget Amounts</a></td>
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
            Show summary of Reviewers scores/comments and award amounts to applicant<br/>
            View the <a href="coAdminNav.do?item=comments">score/comment summary</a> 
            that will be available to the applicant
          </td>
        </tr>
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center">
            <c:if test="${lduser.admincoor=='approve'}">
              <html:submit value="Save Approval" />
            </c:if>          
          </td>
        </tr> 
        <tr>
          <td height="15" />
        </tr>
        <tr>
          <td colspan="2" align="center">
            <a href="AdminCoEmail.do">Send Application Status Emails</a>
          </td>            
        </tr>
      </table>
    </html:form>  
    
  
  </body>
</html>
