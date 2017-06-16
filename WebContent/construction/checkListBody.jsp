<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  </head>
  <body>
   
  <h3>Checklist</h3>

  <c:if test="${appStatus.dateAcceptable=='false'}">
    <p><font color="Red">Warning:  The due date (<fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" />)
    for this application has expired. You may not submit a new application for this fiscal year.</font></p>
  </c:if>
  
  
  <table border="1" class="graygrid" align="center" title="Checklist" width="70%" summary="for layout only">
  <tr>
      <th width="100%" colspan="2" bgcolor="Silver">Application Checklist</th>
    </tr>
  <tr>
    <th>Project Number</th>
    <th>03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
            -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
    </th>
  </tr>
        
    
  <html:form action="/saveCnChecklist">
    <tr>
      <td width="50%" ><b>Item</b></td> 
      <td width="25%" ><b>Completed</b></td>
    </tr>
    <tr>
        <td><a href="constructionForms.do?i=coversheet&m=cn">Application Form</a> (required)</td> 
        <td><html:checkbox property="coversheetComp" /></td>
    </tr>
    
    <tr>
        <td><a href="constructionForms.do?i=otherfunds&m=cn">Additional Funding Sources</a> (required)</td>
        <td><html:checkbox property="additfundingyn" /></td>
    </tr>    
    <tr>
        <td><a href="constructionForms.do?i=narrative&m=cn">Project Narratives</a>  (required)</td>
        <td><html:checkbox property="projdescComp" /></td>  
    </tr>
    <tr>
        <td><a href="constructionForms.do?i=budget&m=cn">Budget</a>  (required)</td>
        <td><html:checkbox property="budgetComp" /></td>
    </tr>
    <tr>
        <td><a href="FS20.do">FS-10 Forms</a>  (required)<br/>(3 original forms signed in blue ink and mailed to your PLS)</td> 
        <td><html:checkbox property="fs20Comp" /></td> 
    </tr>
    <tr>
        <td><a href="Payee.do">Payee Information Form</a>  (required)<br/>(1 original form signed in blue ink and mailed to your PLS)</td> 
        <td><html:checkbox property="payeecomp" /></td> 
    </tr>
    <tr>
        <td><b><a href="constructionForms.do?i=attachment&m=cn">Attachments</a>  (required)</b></td>
        <td><%--<html:checkbox property="attachcomp" />--%></td>  
    </tr>
    <tr>
        <td colspan="2">
            <table align="right" width="95%" summary="for layout only">
                <tr>
                    <td><a href="constructionForms.do?i=assurance&m=cn">Assurances</a>  (required)</td>
                    <td><html:checkbox property="assurancesyn" /></td>
                </tr>    
                <tr>
                    <td> <a href="AVFUNDS.do">Certificate/Proof of Available Funds to Finance Project</a>  (required)</td>
                    <td><html:checkbox property="authComp" /></td> 
                </tr>
                <tr>
                    <td><a href="constructionForms.do?i=seaf&m=cn">Short (or Full) Environmental Assessment Form</a>  (required)</td>
                    <td><html:checkbox property="seafcomp" /></td> 
                </tr>    
                <tr>
                    <td><a href="Photo.do">Pre-Construction Building Photographs</a>  (required)</td> 
                    <td><html:checkbox property="photocomp" /></td>
                </tr>
                <tr>
                    <td><a href="constructionForms.do?i=smartgrowth&m=cn">Smart Growth Form</a>  (required)</td> 
                    <td><html:checkbox property="photocomp" /></td>
                </tr>
                <tr>
                    <td><a href="10yrs.do">Certificate of 10 year minimum lease/legal agreement and project approval
                        from building owner</a><br/>
                        (if building/site is leased or otherwise legally available)</td> 
                    <td><html:checkbox property="payeeyn" /></td>
                </tr>
                <tr>
                  <td><a href="OFP.do">Office of Facilities Planning approval</a><br/>
                  (if building is owned by school district
                  and project over $10,000)</td>
                  <td><html:checkbox property="appendixyn" /></td>
                </tr>
                <tr>
                  <td><a href="constructionForms.do?i=shpo&m=cn">State Historic Preservation Office (SHPO) Approval Documentation</a><br/>
                  (optional)</td>
                  <td><html:checkbox property="shpocomp" /></td>
                </tr>    
                <tr>
                  <td><a href="CnCostEstimate.do">Vendor quotes, cost estimates</a></td>
                  <td></td>
                </tr>  
                <tr>
                  <td><a href="CnMunicipal.do">Municipal Consent for Site/Building Acquisition Projects</a> (optional)</td>
                  <td></td>
                </tr>
                  
                <tr>
                  <td>
	                  <c:if test="${thisGrant.fycode ==16}">
	                  	<a href="CnMwbe.do">M/WBE (Minority and Women-Owned Business Enterprises) Requirement</a> (required)
	                  </c:if>
                  </td>
                  <td></td>
                </tr>
                  
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center"><html:hidden property="grantid" /><html:hidden property="applicationType" value="initial" />
             <html:submit value="Save Progress"/>
        </td>
    </tr>
  </html:form>
    
    <tr>
        <td colspan="2" height="20"><br/></td>
    </tr>
    <tr>   
        <td align="center" colspan="2"> 
            <form action="cnSubmitApp.do?i=verifyinitial" method="POST">
            <input type="HIDDEN" name="fund" value="86" />
            <input type="HIDDEN" name="id" value='<c:out value="${appStatus.grantid}" />' />
            <c:choose>
            <c:when test="${appStatus.allowSubmitInitial=='false' || 
                lduser.prgconstruction!='submit' || appStatus.dateAcceptable=='false'}">
                  <input type="button" value="Submit" disabled="disabled" />
            </c:when>          
            <c:otherwise >   
                  <input type="submit" value="Submit Application" />
            </c:otherwise>
            </c:choose>
            </form>
        </td>
    </tr> 
    <tr>
        <td colspan="2">
        <b>Due Date for applications to be submitted to 
            <c:out value="${appStatus.systemName}"/> is 
            <fmt:formatDate value="${appStatus.systemDueDate}" pattern="MM/dd/yyyy"/>
            <br/><br/>
            Due Date for PLS to submit applications to Library Development is 5 p.m.
            <fmt:formatDate value="${appStatus.dueDate}" pattern="MM/dd/yyyy" /></b>
        </td>
    </tr>
  </table>
  
  

    
    
    
  <!-- removed per LW 04/27/2016 may be readded at later time -->
  <c:if test="${thisGrant.fycode ==16}">
  <table border="1" class="graygrid" align="center" title="Checklist" width="70%" summary="for layout only">
    <tr>
      <td width="100%" bgcolor="Silver" align="center"><b>M/WBE Requirement</b><br/>
        Applicants must complete and submit the following section during the period beginning February 1st through 
        thirty days after receiving an official SED grant award notification. (This requirement applies only to 
        grant awards in the amount of $25,000 and over.) </td>
    </tr>
    
    
    <html:form action="/cnSaveMwbe">
        <tr>
            <td>NEW: (REQUIRED) <a href="http://www.nysl.nysed.gov/libdev/construc/14m/instruct.htm#mwbe" target="_blank">M/WBE Requirement</a>
            <br/><b>All applicants requesting $25,000 or more are required to comply with NYSED’s Minority and Women-Owned 
            Business Enterprises (M/WBE) policy. One of the following must be selected:</b><br/>
            <html:radio property="mwbeParticipation" value="5"/> Not Applicable<br/>
            <html:radio property="mwbeParticipation" value="1"/> Full Participation<br/>
            <html:radio property="mwbeParticipation" value="2"/> Partial Participation, Partial Request for Waiver<br/>
            <html:radio property="mwbeParticipation" value="3"/> No Participation, Request for Complete Waiver<br/>
            <html:radio property="mwbeParticipation" value="6"/> Preferred Source<br/>            
            <html:hidden property="grantid" />
            
            <%--5/8/14 added check for mwbe already submitted; then cannot resubmit--%>
            <c:choose >            
            <c:when test="${appStatus.mwbesubmit=='true' || lduser.prgconstruction!='submit'}">
              
              <input type="BUTTON" value="Save" disabled="disabled" />
            
            </c:when>
            <c:otherwise >               
                    <html:submit value="Save"/>            
            </c:otherwise>
            </c:choose>
          </td>
        </tr>            
    </html:form>
    
    
    <tr>
        <td>For further information and forms see 
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/instruct.htm#mwbe" target="_blank">M/WBE Requirement</a>.
        All applicable forms should be printed, completed, signed and electronically <a href="constructionForms.do?i=attachment&m=cn">attached</a>
        to your grant application 
        as a pdf document. The original forms with original signatures must be kept on file, and can be requested at 
        any point in the future, should the need arise.        
        </td>
    </tr>
            
    <form action="cnSubmitApp.do?i=verifymwbe" method="POST">
        <tr>
          <td align="center">
            After completing the above two sections and attaching all applicable forms please click<br/>
            
            <%--5/8/14 added check for mwbe already submitted; then cannot resubmit--%>
            <c:choose >            
            <c:when test="${appStatus.mwbesubmit=='true' || lduser.prgconstruction!='submit'}">
              
              <input type="BUTTON" value="Submit M/WBE Materials" disabled="disabled" />
            </c:when>
            <c:otherwise >
              <input type="HIDDEN" name="fund" value="86" />
              <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
              <input type="submit" value="Submit M/WBE Materials" name="btn" />
            </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </form>           
  </table>
</c:if>
    
  <br/><br/><br/>
    
    
    
  
  <table border="1" class="graygrid" align="center" title="Checklist" width="70%" summary="for layout only">
    <tr>
      <td width="100%" bgcolor="Silver" align="center"><b>Final Report Checklist</b><br/>
        To be completed at the conclusion of the Construction project</td>
    </tr>
    <tr>
        <td><a href="constructionForms.do?i=finalexpense&m=cn">Budget - Final Expenses</a>
            (required)</td> 
    </tr>
    <%--removed completion form 7/11/12 per MLT
    <tr>
        <td><a href="constructionForms.do?i=completion&m=cn">Project Completion Form</a> (required)</td> 
    </tr>--%>
    <tr>
        <td><b><a href="constructionForms.do?i=attachment&m=cn">Attachments</a></b></td>
    </tr>
       
    <tr>
        <td>
            <table width="90%" align="right"> 
                <tr>
                    <td><b><a href="CnCertificateOccupancy.do">Certificate of Occupancy</a> (If Applicable)</b></td>
                </tr>
                <tr>
                    <td><a href="PostProjectPhoto.do">Post Project Photo</a> (required)</td> 
                </tr>
            </table>
        </td>
    </tr>
     <tr>
        <td><a href="CnFsFinalForm.do">FS-10-F Long Form</a> (required) 
        <br/>(Only <B>AFTER</b> the Final Expenses have been submitted
         to LD and approved by LD)</td> 
    </tr>
    <tr>
        <td><a href="CnAmendments.do">FS-10-A Budget Amendment Form</a> (optional)
        <br/>(Only if there is an amendment to the approved project budget)</td> 
    </tr>
        
    <form action="cnSubmitApp.do?i=verifyfinal" method="POST">
        <tr>
          <td align="center">
            <c:choose >
            <%--5/8/14 added check for initial not appr, or current date not in range of final available/due; then cannot submit--%>
            <c:when test="${appStatus.allowSubmitFinal=='false' || lduser.prgconstruction!='submit' || 
                            appStatus.initialappr=='false' || appStatus.finaldateAcceptable=='false'}">
              
              <input type="BUTTON" value="Submit Final Report" disabled="disabled" />
            </c:when>
            <c:otherwise >
              <input type="HIDDEN" name="fund" value="86" />
              <input type="HIDDEN" name="id" value='<c:out value="${thisGrant.grantid}" />' />
              <input type="submit" value="Submit Final Report" name="btn" />
            </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </form>           
  </table>
       
  <br/><br/><br/>
  
  
 <table border="1" class="graygrid" align="center" title="Checklist" width="70%" summary="for layout only">
    <tr>
      <td width="100%" bgcolor="Silver" align="center"><b>Project Manager Update</b><br/>
        If Project Manager contact information has changed, use the link below to update 
        the Project Manager data.</td>
    </tr>
     <tr>
        <td><a href="constructionForms.do?i=viewProjManager&m=cn">Project Manager Update</a> 
        (May be completed anytime during the project)<br/>&nbsp;</td> 
    </tr>
  </table>
  
  
  
  <br/><br/>
  
  <table width="70%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td align="center"><b>Please use the following links to <i>print</i>
            or <i>save</i> the application to your desktop:</b><br/><br/>
              
      <a href="PrintAppServlet?i=cover" target="_blank">Application Form HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narr" target="_blank">Narratives HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budget&a=false" target="_blank">Budget HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=otherfund" target="_blank">Additional Funds HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=finalexp" target="_blank">Final Expenses HTML</a>
      <br/><br/>
      
      <a href="PrintAppServlet?i=coverpdf" target="_blank">Application Form PDF</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narrpdf" target="_blank">Narratives PDF</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budgetpdf&a=false" target="_blank">Budget PDF</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=otherfundpdf" target="_blank">Additional Funds PDF</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=finalexppdf" target="_blank">Final Expenses PDF</a><br/><br/>
      <a href="PrintAppServlet?i=app&a=false" target="_blank">Complete Application HTML</a>
      </td>
    </tr>
  </table>
  

  </body>
</html>