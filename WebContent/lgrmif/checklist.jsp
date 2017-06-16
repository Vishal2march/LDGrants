<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Checklist help</a>
  <br/>
  
  
  <h5>Checklist - <i>Please check the box to the left of each form below as you complete each
  section of the application.</i></h5>
  
  
  <c:if test="${appStatus.dateAcceptable=='false'}">
    <font color="Red">Warning:  The due date (<fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" />)
    for this application has expired. You may not submit a new application for this fiscal year.</font>
  </c:if>
  
    
  <table align="center" summary="for layout only" width="70%" >
    <tr>
      <th width="100%" bgcolor="Silver">Application Checklist</th>
    </tr>
     <tr>
        <td><b>Project Number:&nbsp;&nbsp; 05<fmt:formatNumber value="${thisGrant.fccode}" />
                -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" /></b>
        </td>
      </tr>       
    
    <tr>
      <td>        
        <table width="100%" border="1" summary="for layout only" class="graygrid" >
          <html:form action="/saveLgChecklist">
            <tr>                          
              <td>
                <html:checkbox property="coversheetComp" />
                <a href="lgApplicant.do?i=coversheet&m=lg" >Application Sheet</a>
              </td>              
            </tr>            
                        
            <tr>             
              <td>
                <html:checkbox property="projdescComp" />
                <a href="lgApplicant.do?i=narrative&m=lg" >Project Narrative</a>
              </td>
            </tr>
                        
            <tr>              
              <td>
                <html:checkbox property="budgetComp" />
                <a href="lgApplicant.do?i=budget&m=lg" >Project Budget</a>
              </td>
            </tr>   
            
            <tr>             
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=lgappendix" >Appendix A and Appendix A-1 G</a>
              </td>
            </tr>
            
            <tr>             
              <td><html:checkbox property="payeeyn" />
              <a href="lgApplicant.do?i=payeeinfo&m=lg" >Payee Information Form & Standard Data Capture Form</a>
              </td>              
            </tr>
            
         <%-- per DM 09/29/2016  --%>
         	<c:if test="${thisGrant.fycode<18}">  
         	<tr>              
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=lgvq" >Vendor Quote Form</a> (if applicable)</td>              
            </tr>      
            </c:if>
             
             
            <%--per FC 8/15/13, IM form not needed starting 2014-15  --%>
            <c:if test="${thisGrant.fycode<15}">
            <tr>              
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=lgim" >Imaging and Microfilming Project Information Form</a>
              (if applicable)</td>
            </tr>      
            </c:if>
            
            <tr>             
              <td>
                <html:checkbox property="authComp" />&nbsp;<a href="lgApplicant.do?i=auth&m=lg" >Institutional Authorization</a>
              </td>             
            </tr>    
            
            <tr>              
              <td>
                <html:checkbox property="fs20Comp" />&nbsp;<a href="lgApplicant.do?i=fs20&m=lg">Proposed Budget Summary</a> (FS-10)
              </td>             
            </tr>
            
            <tr>              
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=attachment&m=lg" >Attachments/Uploads</a></td>
            </tr>      
                    
            <tr>
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=lgpartinst">Participating Institutions</a> (if applicable)</td>
            </tr>
            <tr>             
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=coopagree&m=lg">Shared Services Agreement Form</a> (if applicable)</td>
            </tr>
            <tr>             
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prequalification Requirements (Not-for-Profits only)  
                    <a href="http://www.grantsreform.ny.gov" target="_blank">www.grantsreform.ny.gov</a></td>
            </tr>
            <tr>
              <td height="20%">&nbsp;</td>
            </tr>
            <tr>             
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=printlink&m=lg">Application Printouts</a></td>
            </tr>
            <tr>
              <td height="20%">&nbsp;</td>
            </tr>
            <tr>
                <td><b><i>NEW: (REQUIRED) </i></b><a href="lgApplicant.do?i=mwbe&m=lg">M/WBE Requirement</a> - 
                only for an application for grant funding that exceeds $25,000 for the full grant period.<br/>
                <html:radio property="mwbeParticipation" value="5"/> Not Applicable<br/>
                <html:radio property="mwbeParticipation" value="1"/> Full Participation<br/>
                <html:radio property="mwbeParticipation" value="2"/> Partial Participation, Partial Request for Waiver<br/>
                <html:radio property="mwbeParticipation" value="3"/> No Participation, Request for Complete Waiver<br/>
                <html:radio property="mwbeParticipation" value="6"/> Preferred Source<br/>
                <html:radio property="mwbeParticipation" value="4"/> Deferred Compliance
                </td>
            </tr>
                        
            <tr>              
              <c:choose >
              <c:when test="${appStatus.allowSubmitInitial=='false' || lduser.readaccess=='true' || appStatus.dateAcceptable=='false'}">
                <td align="center">
                  <input type="button" value="Save Progress" disabled="disabled" />
                </td>
              </c:when>
              <c:otherwise >  
                <td align="center">
                  <html:hidden property="grantid" />
                  <html:hidden property="applicationType" value="initial" />
                  <html:submit value="Save Progress"/>
                </td>
              </c:otherwise>
              </c:choose>               
            </tr>                     
          </html:form>
          
          <tr>
            <td><br/></td>
          </tr>
            
           <form action="lgSubmitApp.do?i=verifyinitial" method="POST">
            <tr >
              <td align="center">
                <c:choose >
                <c:when  test="${appStatus.allowSubmitInitial=='false' || lduser.prglg!='submit' || appStatus.dateAcceptable=='false'}">
                  <input type="button" value="Submit" disabled="disabled" style="background-color:rgb(255,255,0);"/>
                </c:when>
                <c:otherwise >                                
                 <input type="HIDDEN" name="fund" value="80" />
                 <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
                 <input type="submit" value="Submit" name="btn"
                        style="background-color:rgb(255,255,0); border-color:rgb(0,0,0);"/>                
                 <br/>
                 
                  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Help Submitting application</a>
                  <br/>
              
                </c:otherwise>
                </c:choose>             
              </td>
            </tr>
            </form>
          </table>        
      </td>
    </tr>
   <tr>
      <th>      
      Due Date for new applications, including forms required to be submitted in paper:<br/>     
      <font size="3" color="Navy"><fmt:formatDate value="${appStatus.dueDate}" /> 5 pm</font>
      <br/>
      <a href="docs/lgrmif/exceptions.htm" target="_blank">*Exception(s) to Grant Application Deadline</a></th>
    </tr>
    
    <tr>
      <td height="45" />
    </tr>
    
    <tr>
      <th width="100%" bgcolor="Silver">FS-10-A Budget Amendments (Optional)</th>
    </tr>
    <tr>
        <td>Only if there is an amendment to the approved project budget.</td>
    </tr>
    <tr>
      <td>
        <table width="100%" align="center" class="graygrid" border="1" summary="for layout only">
          <tr>                          
            <td><a href="lgApplicant.do?i=fs10a&m=lg">Budget Amendment Summary</a></td>  
          </tr>  
          <tr>                          
            <td><a href="docs/lgrmif/amendmentInstructions.doc" target="_blank">
                Instructions for Budget Amendments</a></td>  
          </tr>  
          <tr>
            <td>Amendment Form (FS-10-A) Required?  <a href="docs/lgrmif/amendmentRequirements.doc"
            target="_blank">Formal Amendment Requirements.doc</a></td>
          </tr>             
          <tr>
            <td><a href="FsFormServlet?i=fs10a" target="_blank">Amendment Form</a> FS-10-A HTML<br/>
                <a href="FsFormServlet?i=fs10apdf" target="_blank">Amendment Form</a> FS-10-A PDF
               </td>
          </tr>
            
          <form action="LgConfirmSubmit.do?t=amend" method="POST">              
          <tr>
            <td align="center">
              <c:choose >
              <c:when test="${appStatus.allowSubmitFinal=='false' || appStatus.fs10aComp=='true' || appStatus.allowSubmitInitial=='true' || appStatus.finaldateAcceptable=='false' || appStatus.amenddateAcceptable=='false'}">
                <input type="BUTTON" value="Submit" disabled="disabled" style="background-color:pink;"/>
              </c:when>
              <c:otherwise >
                <input type="submit" value="Submit" name="amend" style="background-color:pink;"/>
              </c:otherwise>
              </c:choose>                
            </td>
          </tr>
         </form>      
        </table>
     </td>
    </tr>
    <tr>
      <th>Due Date for budget amendments: 
      <font size="3" color="Navy"><fmt:formatDate value="${appStatus.interimdueDate}" /></font></th>
    </tr>
    
    
    <tr>
      <td height="45" />
    </tr>
    <tr>
      <th width="100%" bgcolor="Silver">Post-Grant Award Checklist</th>
    </tr>
    <tr>
      <td>        
        <table width="100%" border="1" summary="for layout only" class="graygrid">
          <html:form action="/saveLgChecklist" >
            <%--<tr>             
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=acceptform&m=lg" >Grant Acceptance Form</a> (if applicable)</td>
            </tr>--%>
            <tr>                          
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=fs&m=lg" >Request for Additional Funds</a> FS-25 (Optional)</td>
            </tr>                        
            <tr>             
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=fs&m=lg" >Amendment Form</a> (FS-10-A)</td>
            </tr>
            <tr>      
              <td>
                <html:checkbox property="finalnarrativeComp" />
                <a href="lgApplicant.do?i=finalrpt&m=lg" >Final Project Narrative</a>
              </td>
            </tr>            
            
            <tr>   
              <td>
                <html:checkbox property="finalbudgetComp" />
                <a href="lgApplicant.do?i=budget&m=lg" >Final Project Budget</a> (Expenses Submitted)
              </td>
            </tr>      
            
            <tr>   
              <td>
                <html:checkbox property="statisticsyn" />
                <a href="lgApplicant.do?i=statistic&m=lg" >Final Statistical Report</a></td>
            </tr>    
            
            <tr>              
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=lgfinaleduc" >Final Report for Educational Uses
              Projects</a></td>
            </tr>      
            
            <tr>
              <td>
                <html:checkbox property="fs10fComp" />&nbsp;<a href="lgApplicant.do?i=fs&m=lg">Final Expenditure Report</a> (FS-10-F)
              </td>
            </tr>
            
            <tr>                            
              <td>
                <html:checkbox property="signoffComp" />&nbsp;<a href="lgApplicant.do?i=finalauth" >Final Report Sign-off</a>
              </td>
            </tr>
            
            <tr>   
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="lgApplicant.do?i=attachment&m=lg" >Attachments/Uploads</a></td>
            </tr>           
       
          <tr>            
            <c:choose >
            <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.readaccess=='true'}">
              <td align="center">
                <input type="button" value="Save Progress" disabled="disabled" />
              </td>
            </c:when>
            <c:otherwise >    
              <td align="center">
                <html:hidden property="grantid" />
                <html:hidden property="applicationType" value="final" />
                <html:submit value="Save Progress" />
              </td>
            </c:otherwise>
            </c:choose>      
          </tr>            
        </html:form>
              
        <tr>
          <td><br/></td>
        </tr>
          
          <form action="lgSubmitApp.do?i=verifyfinal" method="POST">
            <tr>
              <td align="center">
                <c:choose ><%--changed 2/4/10 to disable finalSubmit until initialSubmit period is over per FC--%>
                <c:when test="${appStatus.allowSubmitFinal=='false' || appStatus.finaldateAcceptable=='false' || lduser.prglg!='submit' || appStatus.allowSubmitInitial=='true' }">
                  <input type="BUTTON" value="Submit" disabled="disabled" style="background-color:orange;"/>
                </c:when>
                <c:otherwise >
                <input type="HIDDEN" name="fund" value="80" />
                <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
                 <input type="submit" value="Submit" name="btn"
                        style="background-color:orange; border-color:rgb(0,0,0);"/>                
                 <br/>
                  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Help Submitting post-grant award forms</a>
                  <br/>
                </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </form>
        </table>        
      </td>
    </tr>    
    <tr>
      <th>Due Date for final reports: 
      <font size="3" color="Navy"><fmt:formatDate value="${appStatus.finaldueDate}" type="both"/></font></th>
    </tr>
    
    <tr>
       <td height="25"/>
    </tr>
    <tr>
      <td>
        <table width="100%" class="boxtype" summary="for layout only">
          <tr>
            <td align="center"><a href="lgApplicant.do?i=appstatus">View Application Submission</a><br/><br/>
               
               <a href="lgApplicant.do?i=decnotes">View Decision Notes</a><br/><br/></td>
         </tr>
        </table>
      </td>
    </tr>        
  </table>
 
  
  </body>
</html>
