<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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

  <table width="90%" align="center" border="1" summary="for layout only" >        
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
    
    <a href="PrintAppServlet?i=cover" target="_blank">View Cover Page - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=coverpdf" target="_blank">View Cover Page - PDF</a>
    <br/><br/>
    <a href="PrintAppServlet?i=narr" target="_blank">View Narratives - HTML</a>
    &nbsp;&nbsp;&nbsp;&nbsp;  
    <a href="PrintAppServlet?i=narrpdf" target="_blank" >View Narratives - PDF</a>
    
    <c:if test="${thisGrant.fycode<16}">
        <br/><br/>
        <a href="PrintAppServlet?i=budget&a=true" target="_blank">View Budget - HTML</a>
        &nbsp;&nbsp;&nbsp;&nbsp;     
        <a href="PrintAppServlet?i=budgetpdf&a=true" target="_blank" >View Budget - PDF</a>
        <br/><br/>    
        <a href="PrintAppServlet?i=ifnarr" target="_blank">View Interim/Final Narratives - HTML</a>
        &nbsp;&nbsp;&nbsp;&nbsp;  
        <a href="PrintAppServlet?i=ifnarrpdf" target="_blank">View Interim/Final Narratives - PDF</a>
    </c:if>
  </p>  <br/>
  
  
    
  <html:form action="/saveAdminReview" > 
      <input type="HIDDEN" name="p" value="al" />
      <input type="HIDDEN" name="task" value="review" />
      <table width="90%" align="center" class="boxtype" summary="for layout only">
        <tr bgcolor="Silver">
          <td colspan="2" >
            <b>Checklist - </b>
            use the following links to review the applicant's submissions.<br/>
            A check mark indicates that the item has been reviewed and accepted as complete.  
          </td>
        </tr>
        
        
        <c:choose>
        <c:when test="${thisGrant.fycode<16}">        
            <tr>                       
              <td width="50%">
                <html:checkbox property="coversheetyn" />
                <a href="alAdminNav.do?item=coversheet" class="blacklink">Cover Page</a><br/>
                - update <a href="alAdminNav.do?item=viewProjManager&m=al" class="blacklink">Project Manager</a> info<br/>
                - update <a href="alAdminNav.do?item=viewContact&m=al" class="blacklink">Additional Contact</a> info
              </td> 
              <td>
                 <html:checkbox property="instauthyn" />
                 Board Certification, Cover Page Director Authorization<br/>
                 (both forms mailed to LD)             
              </td>            
            </tr>                
            <tr> 
               <td width="50%">
                  <html:checkbox property="projdescyn" />
                  <a href="alAdminNav.do?item=narrative&p=al" class="blacklink">Project Narratives</a>
               </td>            
               <td>
                 <html:checkbox property="fs20yn" />
                 <a href="alAdminNav.do?item=fs" class="blacklink">FS-10 Form</a>
              </td>
            </tr>                
            <tr>    
              <td width="50%">
                <html:checkbox property="amtreqyn" />
                <a href="alAdminNav.do?item=budget&m=al" class="blacklink">Project Budget</a>
                - Amount Requested
              </td> 
              <td>
                View <a href="alAdminNav.do?item=attachment" class="blacklink">Attached Documents</a>
                <br/>
                View <a href="alAdminNav.do?item=appstatus" class="blacklink">Submission 
                Data/Applicant Comments</a> during submission
                <br/>
                View <a href="alAdminNav.do?item=verifyinitial" class="blacklink">Submission 
                Validation</a> & Submit initial application
              </td>
            </tr>      
        
        
        </c:when>
        <c:otherwise>
        <%-- this is admin checklist for initial application starting fy16-19 --%>
           <tr>                       
              <td width="50%">
                <html:checkbox property="coversheetyn" />
                <a href="alAdminNav.do?item=coversheet" class="blacklink">Cover Page</a><br/>
                - update <a href="alAdminNav.do?item=viewProjManager&m=al" class="blacklink">Project Manager</a> info<br/>
                - update <a href="alAdminNav.do?item=viewContact&m=al" class="blacklink">Additional Contact</a> info
              </td> 
              <td>
                 <html:checkbox property="instauthyn" />
                 <a href="alAdminNav.do?item=certform&p=al" class="blacklink">Certification Statement</a>
              </td>            
            </tr>                
            <tr> 
               <td width="50%">
                  <html:checkbox property="projdescyn" />
                  <a href="alAdminNav.do?item=narrative&p=al" class="blacklink">Project Narratives</a>
               </td>               
               <td>View <a href="alAdminNav.do?item=attachment" class="blacklink">Attached Documents</a></td>
            </tr>             
            <tr>
              <td height="20" />
           
         </tr>
             <tr>
              <td colspan="2" align="center"> <html:submit property="unlockapp" value="Unlock Application"/></td>
         </tr>
            
            <%-- this is final report checklist starting fy16-19 --%>
            <tr>
              <td colspan="2" height="20%"></td>
            </tr>
            <tr bgcolor="silver">
              <td colspan="2"><b>Year 1 Reporting Checklist</b></td>
            </tr>
            <tr>                
              <td width="50%">
              	 <html:checkbox property="finalnarrativeyn" />
                 <a href="literacyYr1FinalNarrativeAdmin.action" class="blacklink">Final Narrative</a>
              </td> 
              <td>
              	<html:checkbox property="finalsignoffyn" />
              	<a href="literacyYr1FinalSignoffAdmin.action" class="blacklink">Yearly Final Report Signoff</a>
              </td>                  
           </tr>  
           <tr>                
              <td width="50%">
              	 <html:checkbox property="expsubyn" /> 
                 <a href="literacyYr1ContractedServicesAdmin.action" class="blacklink">Budget</a>
              </td>                   
           </tr>  
           <tr>
              <td colspan="2" align="center"> <html:submit property="unlockapp" value="Unlock Year 1"/></td>
         	</tr>
           
            
        </c:otherwise>
        </c:choose>        
        
        
        <tr>
          <td height="20" />
        </tr>
        
    <c:if test="${thisGrant.fycode<14}">
        <%-- interim application checklist section prior to FY2013-14--%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr>                
          <td width="50%">
             <html:checkbox property="interimrptyn" />
             <a href="AlAdminInterim.do" class="blacklink">Interim Narrative</a>
          </td>                  
          <td width="50%">
            <%--<html:checkbox property="interimauthyn" />--%>
            Interim Authorization (no longer used as of 1/14/11)</td>
       </tr>       
    </c:if>
    
    
    <c:if test="${thisGrant.fycode<16}">
        <%-- final application checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
        <tr>                
          <td width="50%">
             <html:checkbox property="finalnarrativeyn" />
             <a href="AlAdminFinalNarr.do" class="blacklink">Final Narrative</a>
          </td>                  
          <td width="50%">
            <html:checkbox property="finalsignoffyn" />
            <a href="alAdminNav.do?item=attachment" class="blacklink">Final Report 
            Authorization</a><br/>(scanned & attached to online application)</td>
       </tr>  
       <tr>
          <td width="50%">
               <html:checkbox property="expsubyn" />    
               <a href="alAdminNav.do?item=budget&m=al" class="blacklink">Project Budget</a>
               - Expenses Submitted (populates FS-10-F form)
          </td>           
          <td>
            <html:checkbox property="fs10fyn" />
            <a href="alAdminNav.do?item=fs" class="blacklink">FS-10-F Form</a>
          </td>
       </tr>       
       <tr>
          <td width="50%">
             <html:checkbox property="statisticsyn" />    
             <a href="alAdminNav.do?item=statistics" class="blacklink">Final Report Statistics</a>
          </td>   
      </tr>
      <tr>
          <td height="20" />
        </tr>
    </c:if>
    
        
        
        <%-- amendment checklist section --%>
        <tr>
          <td colspan="2"><hr/></td>
        </tr>
      <tr> 
          <td><html:checkbox property="fs10aComp"/>
          <a href="adminredirect.action" class="blacklink">Amendment Summary</a> (and FS-10-A)</td>  
       </tr>
       <tr>
          <td colspan="2" align="center"> <html:submit property="unlockapp" value="Unlock Amendment"/></td>
       </tr>
       <tr>
          <td colspan="2"><hr/></td>
        </tr>
      <tr>
        <td height="20" />
      </tr>
      
      
      
      <tr>
      	<td><a href="literacyFinalSummaryAdmin.action" class="blacklink">Final Summary</a></td>
      </tr>
      <tr>
        <td height="20" />
      </tr>
      
      
      <%--not needed starting 2016-17; separate unlock buttons --%>
      <c:if test="${thisGrant.fycode<16}">
	      <tr>
	        <td colspan="2">
	          <html:checkbox property="lockapp" />
	          Unlock for Correction (any items above that are not checked will be unlocked)
	        </td>
	      </tr>
	      <tr>
	        <td height="20" />
	      </tr>
	      <tr>
	        <td colspan="2" align="center"><html:submit value="Save Review" /></td>            
	      </tr>
      </c:if>
    </table>
    </html:form>
    
    
  <br/>  
  <table align="center" width="90%" class="boxtype" summary="for layout only">
    <tr bgcolor="Silver">
      <td><b>Administrative Tasks</b></td>
    </tr>
      <c:if test="${(thisGrant.fycode<16) and (thisGrant.fycode>13)}"> 
    <tr>
      <td>
          <a href="alRevertBudget.do?i=revertbudget">Revert back to Initial Budget</a>
      </td>
    </tr>
    </c:if>
    <tr>
      <td><a href="adminCommentNav.do?item=comments&p=al">Add, edit, email comment</a></td>
    </tr>
    <tr>
      <td><a href="adminEmailNav.do?item=emails&m=al">View Emails</a> Sent regarding this Grant</td>
    </tr>
    
    <c:if test="${thisGrant.fycode<14}"><%--no scores starting fy2013-14--%>
    <tr>
      <td><a href="litAdminRevNav.do?item=ratings&m=al">View Scores</a> from Reviewers for this Grant</td>
    </tr>
    </c:if>    
  </table>    
  <br/>
  
    
    
  <%-- This is the approval section  --%>
  <html:form action="/saveAdminReview" > 
      <input type="HIDDEN" name="p" value="al" />
      <input type="HIDDEN" name="task" value="approve" />
      <table width="90%" align="center" class="boxtype" summary="for layout only">
        <tr bgcolor="Silver">
          <td colspan="3" >
            <b>Approvals - </b>
            Only SED staff with 'approval' rights may authorize the 
            approval of an application or final report.</td>
        </tr> 
        
        <c:choose>
        <c:when test="${thisGrant.fycode<14}"><%--old grants use interim, 2yr only--%>
            <tr>
              <td><html:checkbox property="initialappr" />Application Approved</td>
              <td><html:checkbox property="interimappr" />Interim Report Approved</td>          
            </tr>
            <tr>
              <td><html:checkbox property="appDenied" />Application Denied</td>
              <td><html:checkbox property="finalappr" />Final Report Year 1 Approved</td>
            </tr>
            <tr>
                <td></td>
                <td><html:checkbox property="finalappryear2" />Final Report Year 2 Approved</td>
            </tr>
        </c:when>
        <c:otherwise>
            <tr>
              <td><html:checkbox property="initialappr" />Application Approved</td>
              <td><html:checkbox property="finalappr" />Final Report Year 1 Approved</td>
              <td><html:checkbox property="amendApprYr1" />Amendment Year 1 Approved</td>
            </tr>
            <tr>
              <td></td>
              <td><html:checkbox property="finalappryear2" />Final Report Year 2 Approved</td>
              <td><html:checkbox property="amendApprYr2" />Amendment Year 2 Approved</td>          
            </tr>
            <tr> 
             <td></td>
             <td><html:checkbox property="finalappryear3" />Final Report Year 3 Approved</td>
              <td><html:checkbox property="amendApprYr3" />Amendment Year 3 Approved</td>
            </tr>
        </c:otherwise>
        </c:choose>
        
        <c:if test="${thisGrant.fycode<16}">
	        <tr>
	          <td><html:checkbox property="readyYear3" />Year 2 Complete, Ready for Year 3</td>
	          <td>
	            <html:checkbox property="showscorecomm" />               
	            Show project approval/denial status and award amounts to applicant
	          </td>
	        </tr>
        </c:if>
        
        <tr>
          <td colspan="2" align="center">
            <c:if test="${lduser.adminfl=='approve'}">
              <html:submit value="Save Approval" />
            </c:if>
          </td>
        </tr> 
      </table>
   </html:form>
    
  </body>
</html>
