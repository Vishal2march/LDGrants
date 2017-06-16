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
  
  <h4>Checklist</h4>
  
  <c:if test="${appStatus.dateAcceptable=='false'}">
    <font color="Red">Warning:  The due date (<fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" />)
    for this application has expired. You may not submit a new application for this fiscal year.</font>
  </c:if>
  
    
  <table align="center" summary="for layout only" width="70%" >
    <tr>
      <th width="100%" bgcolor="Silver">Application Checklist</th>
    </tr>
    <tr>
      <td>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <th>Project Number</th>
            <th>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
                    -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                    -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
            </th></tr>
        </table>
      </td>
    </tr>
        
    
    <tr>
      <td>        
        <table width="100%" border="1" summary="for layout only" class="graygrid" >
          <html:form action="/saveDiChecklist">
            <tr>                          
              <td><html:checkbox property="coversheetComp" />
                  <a href="diInitialForms.do?i=coversheet&m=di" >Coversheet</a></td>
            </tr>            
            
            <tr>             
              <td><html:checkbox property="projdescComp" />
                  <a href="diInitialForms.do?i=narrative&m=di" >Project Narrative</a></td>
            </tr>
                        
            <tr>              
              <td><html:checkbox property="budgetComp" />
                 <a href="diInitialForms.do?i=budget&m=di" >Project Budget</a></td>
            </tr>                         
            
            <tr>             
              <td><html:checkbox property="authComp" />
                  <a href="diInitialForms.do?i=auth&m=di" >Institutional Authorization Form</a></td>
            </tr> 
            
            <tr>              
              <td><html:checkbox property="fs20Comp" />
                <a href="diInitialForms.do?i=fs&m=di">FS-10 Form</a>
                3 original FS-10 Forms must be completed and mailed</td>
            </tr>
            <tr>
                <td><a href="diInitialForms.do?i=attachment&m=di">Attachments/Uploads</a></td>
            </tr>
            <tr>
                <td><a href="diInitialForms.do?i=payeeinfo&m=di">Payee Information Form</a></td>
            </tr>
            <tr>
                <td><a href="diInitialForms.do?i=coopagree&m=di">Cooperative Agreement Form</a> (if applicable)</td>
            </tr>
            <tr>
                <td><a href="diInitialForms.do?i=microform&m=di">Microform Guidelines Form</a> (if applicable)</td>
            </tr>
            <tr>
                <td><b><i>NEW: (REQUIRED) </i></b><a href="diInitialForms.do?i=prequal&m=di">Prequalification requirement 
                for not-for-profit entities applying for grants</a></td>
            </tr>
            <tr>
                <td><b><i>NEW: (REQUIRED) </i></b><a href="diInitialForms.do?i=mwbe&m=di">M/WBE Requirement</a> - 
                only for an application for grant funding that exceeds $25,000 for the full grant period.<br/>
                <html:radio property="mwbeParticipation" value="5"/> Not Applicable<br/>
                <html:radio property="mwbeParticipation" value="1"/> Full Participation<br/>
                <html:radio property="mwbeParticipation" value="2"/> Partial Participation, Partial Request for Waiver<br/>
                <html:radio property="mwbeParticipation" value="3"/> No Participation, Request for Complete Waiver
                </td>
            </tr>
            
            <tr>              
              <c:choose >
                <c:when test="${appStatus.allowSubmitInitial=='false' || lduser.prgdi=='read' || appStatus.dateAcceptable=='false'}">
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
          
            
            <form action="diSubmitApp.do?i=verifyinitial" method="POST">
            <tr >
              <td align="center">
                <c:choose >
                <c:when  test="${appStatus.allowSubmitInitial=='false' || lduser.prgdi!='submit' || appStatus.dateAcceptable=='false'}">
                  <input type="button" value="Submit" disabled="disabled" />
                </c:when>
                <c:otherwise >        
                  <input type="HIDDEN" name="fund" value="5" />
                  <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
                  <input type="submit" value="Submit" name="btn" />
                </c:otherwise>
                </c:choose>           
                
                <br/>
                <b>Due Date for new applications: <fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" /></b>
              </td>
            </tr>
            </form>
          </table>        
      </td>
    </tr>
    <tr>
      <td height="30" />
    </tr>
    
    <tr>
      <th width="100%" bgcolor="Silver">FS-10-A Budget Amendments (Optional)</th>
    </tr>
    <tr>
      <td>
        <table width="100%" align="center" class="graygrid" border="1" summary="for layout only">
          <tr>
            <th>Only if there is an amendment to the approved project budget</th>
          </tr>      
          <tr>                          
            <td><a href="diInitialForms.do?i=fs10a&m=di">Budget Amendments</a><br/></td>  
          </tr>    
          <tr>
            <td><a href="FsFormServlet?i=fs10a" target="_blank">FS-10-A Form HTML</a><br/>
                <a href="FsFormServlet?i=fs10apdf" target="_blank">FS-10-A Form PDF</a>
                (3 original FS-10-A Forms signed in blue ink and mailed)</td>
          </tr>
            
          <form action="DiConfirmSubmit.do?todo=amend" method="POST">              
          <tr>
            <td align="center">
              <c:choose >
              <c:when test="${appStatus.allowSubmitFinal=='false' || appStatus.fs10aComp=='true'}">
                <input type="BUTTON" value="Submit" disabled="disabled" />
              </c:when>
              <c:otherwise >
                <input type="submit" value="Submit" name="amend" />
              </c:otherwise>
              </c:choose>                
            </td>
          </tr>
         </form>      
        </table>
     </td>
    </tr>
    
    
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <th width="100%" bgcolor="Silver">Final Report Checklist</th>
    </tr>
    <tr>
      <td>        
        <table width="100%" border="1" summary="for layout only" class="graygrid">
          <html:form action="/saveDiChecklist" >
            <tr>      
              <td><html:checkbox property="finalnarrativeComp" />
                  <a href="diInitialForms.do?i=finalrpt&m=di" >Final Report Narrative</a></td>
            </tr>            
            
            <tr>   
              <td><html:checkbox property="finalbudgetComp" />
                  <a href="diInitialForms.do?i=budget&m=di">Project Budget</a> (Expenses Submitted)</td>
            </tr>            
                   
            <tr>                            
                <td><html:checkbox property="signoffComp" />
                    <a href="diInitialForms.do?i=finalauth&m=di" >Final Report Sign-off</a></td>
            </tr>          
            <tr>
              <td><html:checkbox property="fs10fComp" />
                  <a href="diInitialForms.do?i=fs&m=di">FS-10-F Form</a> 
                  3 original FS-10-F Forms must be completed and mailed</td>
            </tr>
            <tr>
                <td><a href="diInitialForms.do?i=attachment&m=di">Attachments/Uploads</a></td>
            </tr>
            <tr>
              <td><a href="diInitialForms.do?i=fs&m=di" target="_blank">FS-10-A Form</a>
              (Optional - 3 original FS-10-A Forms completed and mailed only if there is an amendment to the approved project budget)</td>
            </tr>
       
          <tr>            
            <c:choose >
            <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgdi=='read'}">
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
              
          <form action="diSubmitApp.do?i=verifyfinal" method="POST">
            <tr>
              <td align="center">
                <c:choose >
                <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgdi!='submit'}">
                  <input type="BUTTON" value="Submit" disabled="disabled" />
                </c:when>
                <c:otherwise >
                  <input type="HIDDEN" name="fund" value="5" />
                  <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
                  <input type="submit" value="Submit" name="btn" />
                </c:otherwise>
                </c:choose>
                
                <br/>
                <b>Due Date for final reports: <fmt:formatDate value="${appStatus.finaldueDate}" pattern="MM/dd/yyyy" /></b>
              </td>
            </tr>
          </form>            
          </table>        
      </td>
    </tr> 
    <tr>
      <td height="30" />
    </tr>
    <tr>
      <td>
        <table width="100%" class="boxtype" summary="for layout only">
          <tr>
            <td align="center"><a href="diInitialForms.do?i=appstatus&m=di">View Application Submission/Approvals</a><br/>
                
                <a href="diInitialForms.do?i=comments&m=di">View Reviewer Comment/Scores</a><br/><br/>
                
                  <b>Please use the following links to <i>print</i> or <i>save</i> your application to your desktop:</b><br/><br/>
                  
                <a href="PrintAppServlet?i=cover" target="_blank">Coversheet HTML</a>
          &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narr" target="_blank">Narratives HTML</a>
          &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budget&a=false" target="_blank">Budget HTML</a><br/><br/>
                                              
                <a href="PrintAppServlet?i=coverpdf" target="_blank">Coversheet PDF</a>
          &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narrpdf" target="_blank">Narratives PDF</a>
          &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budgetpdf&a=false" target="_blank">Budget PDF</a>
            </td>
          </tr>
        </table>
      </td>
    </tr>        
  </table>
   
  </body>
</html>
