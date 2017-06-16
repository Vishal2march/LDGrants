<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>adminChecklist</title>
  </head>
  <body>
  
  <h4>Admin Checklist</h4>
      

  <table width="90%" align="center" border="1" class="graygrid" summary="for layout only" >        
    <tr>             
      <td width="25%"><b>Project Num</b></td>
      <td><b>Institution</b></td>  
    </tr>        
    <tr>
      <td>03<fmt:formatNumber minIntegerDigits="2"  value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/>
      </td>
      <td ><c:out value="${thisGrant.instName}" /></td>
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
    View Complete Application (opens in new window)<br/>
    <a href="PrintAppServlet?i=app&a=true" target="_blank">HTML</a>  |
       
     <a href="PrintAppServlet?i=apppdf&a=true" target="_blank">PDF</a> 
     
  </p>
  
  
  
  
  
  
  <html:form action="/saveAdminReview"> 
      <input type="HIDDEN" name="p" value="staid" />
       <input type="HIDDEN" name="task" value="review" />
      <table width="90%" align="center" class="boxtype" summary="for layout only">
        <tr bgcolor="gray">
          <td colspan="2" >
            <b>Review Application - </b>
            use the following links to review the applicant's submissions.<br/>
            A check mark indicates that the item has been reviewed and accepted as complete.  
          </td>
        </tr>
        <tr>                     
          <td width="50%">
            <html:checkbox property="coversheetyn" />
            <a href="staidAdminNav.do?item=coversheet" class="blacklink">Cover Sheet</a>
          </td> 
          <td width="50%">
             <html:checkbox property="instauthyn" />
             <a href="staidAdminNav.do?item=auth" class="blacklink">Assurance</a>
          </td>          
        </tr>                
        <tr> 
           <td width="50%">
            <html:checkbox property="projdescyn" />
            <a href="staidAdminNav.do?item=narrative" class="blacklink">Narrative</a>
          </td>                   
          <td width="50%">
            <html:checkbox property="fs20yn" />
            <a href="FsFormServlet?i=fs10&fy=0" target="_blank" style="color:rgb(0,0,0);">FS-10 (HTML)</a><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="FsFormServlet?i=fs10pdf&fy=0" target="_blank" class="blacklink">FS-10 (PDF)</a>
          </td>
        </tr>                
        <tr >    
          <td width="50%">
            <html:checkbox property="amtreqyn" />
            <a href="staidAdminNav.do?item=budget&m=staid" class="blacklink">Project Budget</a>
          </td>                   
          <td><a href="staidAdminNav.do?item=attachment" class="blacklink">Attached Documents</a></td>
        </tr>      
      
        <%-- final application checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr>                
          <td width="50%">
             <html:checkbox property="finalnarrativeyn" />
             <a href="staidAdminNav.do?item=narrative" class="blacklink">Final Narrative</a>
          </td>                  
          <td><a href="FsFormServlet?i=fs10a" target="_blank" class="blacklink">FS-10-A (HTML)</a><br/> 
                <a href="FsFormServlet?i=fs10apdf" target="_blank" class="blacklink">FS-10-A (PDF)</a></td>
       </tr>  
       <tr>
        <td width="50%">
           <html:checkbox property="expsubyn" />         
           <a href="staidAdminNav.do?item=budget&m=staid" class="blacklink">Project Budget</a>
           - Expenses Submitted
        </td>  
        <td><a href="FsFormServlet?i=fs10f&fyf=0" target="_blank" class="blacklink">FS-10-F (HTML)</a> <br/>
                <a href="FsFormServlet?i=fs10fpdf&fyf=0" target="_blank" class="blacklink">FS-10-F (PDF)</a></td>
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
        <td colspan="2">Contract Number &nbsp;
        <html:text property="contractnum" /></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      <tr>
         <td colspan="2"><a href="adminEmailNav.do?item=emails&m=staid">View Emails Sent regarding this Grant</a></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><html:submit value="Save Review" /></td>             
      </tr>
    </table>
   </html:form>
  
  
  
  
  
  <br/>
  <%-- This is the approval section  --%>
  <html:form action="saveAdminReview" >
      <input type="HIDDEN" name="task" value="approve" />
      <input type="HIDDEN" name="p" value="staid" />
      <table width="90%" align="center" class="boxtype" summary="for layout only">
        <tr bgcolor="gray">
          <td colspan="2" ><b>Approve Application - </b></td>
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
    </table>
    </html:form> 
  
  
  </body>
</html>