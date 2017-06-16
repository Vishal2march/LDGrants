<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  </head>
  <body>
 

  <br/><br/>
  <table align="center" width="80%" border="1" class="boxtype" summary="for layout only" > 
  <tr>
    <th bgcolor="Silver">Construction Application Checklist</th> 
  </tr>
  <tr>
      <td><b>Project Number:</b>&nbsp;
      03<fmt:formatNumber value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
  </tr>
  <tr>
     <td><b>Member Library:</b> &nbsp;<c:out value="${thisGrant.instName}" /></td>
  </tr>
  <c:if test="${appStatus.initialsubmitted=='false'}">
        <tr>
            <td><font color="red">This application has not been submitted!</font></td>
        </tr>
  </c:if>
  <tr>
    <td height="30">&nbsp;</td>
  </tr>

  <tr>
    <td>    
      <c:url var="currURL" value="cnReviewNav.do">
        <c:param name="item" value="ratingform"/>
        <c:param name="aid" value="${assignBean.assignmentId}"/>
      </c:url>    
      <a href='<c:out value="${currURL}" />' >Review/Evaluation Form</a> (to be completed by PLS)</td> 
  </tr>
  <tr>
    <td><a href="cnReviewNav.do?item=matchform">Reduced Match Justification Form</a> (to be completed by PLS, if applicable)</td>
  </tr>
   <tr>
    <td><a href="cnReviewNav.do?item=coversheet">Application Form</a> (required)</td>
  </tr>
  <tr>
    <td><a href="cnReviewNav.do?item=otherfunds">Additional Funding Sources</a> (required)</td>
  </tr>
  <tr>
    <td><a href="cnReviewNav.do?item=narrative">Project Narratives</a> (required)</td> 
  </tr>
  <tr>
    <td><a href="cnReviewNav.do?item=budget">Budget</a> (required)</td>
  </tr>   
  <tr>
    <td><b>Public Library Systems should mail the FS-10 Form and Payee Information Form
    to the Library Development address below:</b><br/>
        - <a href="cnRevFs20.do">FS-10 Form</a> (3 originals signed in blue ink)<br/>
        - <a href="cnRevPayee.do">Payee Information Form</a> (1 original signed in blue ink)<br/><br/>
        Division of Library Development<br/>
        NYS Library<br/>
        Cultural Education Center, Room 10B41<br/>
        Albany NY 12230<br/>
        Attn: Kimberly Anderson<br/>
    </td>
  </tr>
  
  <tr>
    <td><b><a href="cnReviewNav.do?item=attachment">Construction Project Attachments</a> (required)</b></td>
  </tr>
  <tr>
    <td>
        <table width="90%" align="right" summary="for layout only">
          <tr>
            <td><a href="cnReviewNav.do?item=assurance">Assurances</a> (required)</td></tr>
          <tr>
            <td><a href="cnRevAvfunds.do">Certificate/Proof of Available Funds to
            Finance Project</a> (required)</td></tr>
          <tr>
             <td><a href="cnReviewNav.do?item=seaf">Short (or Full) Environmental
             Assessment Form</a> (required)</td></tr>
          <tr>
             <td><a href="cnRevPhoto.do">Pre-Construction Building Photographs</a> 
             (required)</td></tr>
          <tr>
             <td><a href="cnReviewNav.do?item=smartgrowth">Smart Growth Form</a> (required)</td></tr>
          <tr>
             <td><a href="cnRev10yrs.do">Certificate of 10 year minimum lease/legal 
             agreement</a></td></tr>
          <tr>
             <td><a href="cnRevOfp.do">Office of Facilities Planning Certificate of 
             Project Approval</a></td></tr>
          <tr>
             <td><a href="cnReviewNav.do?item=shpo">State Historic Preservation Office (SHPO) 
             Approval Documentation</a></td>
          </tr>    
          <tr>
             <td><a href="constructionAddAttachmentReview.do">Vendor bids and/or quotes, cost estimates</a></td>
          </tr>   
          <tr>
             <td><a href="CnRevMunicipal.do">Municipal Consent for Site/Building Acquisition Projects</a> (optional)</td>
          </tr>  
          <c:if test="${thisGrant.fycode ==16}">
	          <tr>
	             <td><a href="CnRevMwbe.do">M/WBE (Minority and Women-Owned Business Enterprises) Requirement</a> (required)</td>
	          </tr>  
          </c:if>
        </table>
    </td>
  </tr> 
  <tr>
    <td height="30"></td>
  </tr>
                 
  <%--If PLS has not submitted this app to LD; they can unlock it for the appcnt--%>                  
  <tr>
    <td><c:choose>
        <c:when test="${assignBean.systemLockOut=='false'}">
            <a href="cnReviewNav.do?item=unlock">Unlock Application</a> for editing by member library
        </c:when>
        <c:otherwise>
            This application has already been submitted to the Division of Library Development. 
            The application can no longer be edited or unlocked for editing.
        </c:otherwise>
        </c:choose>
    </td> 
  </tr>  
  </table>
  
  <br/><br/>
  
  <table width="80%" align="center" class="boxtype" summary="for layout only">
    <tr>
      <td align="center"><b>Please use the following links to <i>print</i>
            or <i>save</i> the application to your desktop:</b><br/><br/>
              
      <a href="PrintAppServlet?i=cover" target="_blank">Application Form HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narr" target="_blank">Narratives HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budget&a=false" target="_blank">Budget HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=evalform" target="_blank">Evaluation & Reduced Match Form HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=otherfund" target="_blank">Additional Funds HTML</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=app&a=false" target="_blank">Complete Application HTML</a>
      <br/><br/><br/>                                 
     
      <a href="PrintAppServlet?i=coverpdf" target="_blank">Application Form PDF</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=narrpdf" target="_blank">Narratives PDF</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=budgetpdf&a=false" target="_blank">Budget PDF</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=evalformpdf" target="_blank">Evaluation & Reduced Match Form PDF</a>
      &nbsp;&nbsp;&nbsp;<a href="PrintAppServlet?i=otherfundpdf" target="_blank">Additional Funds PDF</a>
      </td>
    </tr>
  </table>

    <br/><br/><br/><br/>
    
    
    <c:if test="${thisGrant.fycode ==16}">
    
    <table border="1" align="center" width="80%" class="boxtype" summary="for layout only">
    <tr>
      <td width="100%" bgcolor="Silver" align="center"><b>M/WBE Requirement</b><br/>
        Applicants must complete and submit the following section during the period beginning February 1st through 
        thirty days after receiving an official SED grant award notification. (This requirement applies only to 
        grant awards in the amount of $25,000 and over.) </td>
    </tr>
    
    <html:form action="/saveCnRMwbe">
      <tr>
          <td>NEW: (REQUIRED) <a href="http://www.nysl.nysed.gov/libdev/construc/14m/instruct.htm#mwbe" target="_blank">M/WBE Requirement</a>
          <br/><b>All applicants requesting $25,000 or more are required to comply with NYSED’s Minority and 
        Women-Owned Business Enterprises (M/WBE) policy. One of the following must be selected:</b><br/>
          <html:radio property="mwbeParticipation" value="5"/> Not Applicable<br/>
          <html:radio property="mwbeParticipation" value="1"/> Full Participation<br/>
          <html:radio property="mwbeParticipation" value="2"/> Partial Participation, Partial Request for Waiver<br/>
          <html:radio property="mwbeParticipation" value="3"/> No Participation, Request for Complete Waiver<br/>
          <html:radio property="mwbeParticipation" value="6"/> Preferred Source<br/>
          <html:hidden property="grantid"/> <br/>
          <html:submit value="Save"/></td>
      </tr>
    </html:form>
       
    <tr>
        <td>For further information and forms see 
        <a href="http://www.nysl.nysed.gov/libdev/construc/14m/instruct.htm#mwbe" target="_blank">M/WBE Requirement</a>.
        All applicable forms should be printed, completed, signed and electronically attached
        to your grant application 
        as a pdf document. The original forms with original signatures must be kept on file, and can be requested at 
        any point in the future, should the need arise.        
        </td>
    </tr>
    </table>
    
    <br/><br/><br/><br/>
  </c:if>
  
  
  


    <table border="1" align="center" width="80%" class="boxtype" summary="for layout only">
    <tr>
      <td width="100%" bgcolor="Silver" align="center"><b>Final Report Checklist</b><br/>
        To be completed by the applicant at the conclusion of the Construction project 
        (read-only access for PLS)</td>
    </tr>
    <tr>
        <td><a href="cnReviewNav.do?item=finalexpense">Budget - Final Expenses</a>
            (required)</td> 
    </tr>
    <tr>
        <td><b><a href="cnReviewNav.do?item=attachment">Attachments</a></b></td>
    </tr>
    <tr>
        <td>
            <table width="90%" align="right">                 
                <tr>
                    <td><a href="CnRevPostProjPhoto.do">Post Project Photo</a> (required)</td> 
                </tr>
                <tr>
                    <td><a href="CnRevCertOccupancy.do">Certificate of Occupancy</a> (If Applicable)</td>
                </tr>
            </table>
        </td>
    </tr>
     <tr>
      <td><a href="CnRevFsFinal.do">FS-10-F Long Form</a> (required) 
        <br/>(Only <B>AFTER</b> the Final Expenses have been submitted
         to LD and approved by LD)</td> 
    </tr>
    <tr>
        <td><a href="CnRevAmendments.do">FS-10-A Budget Amendment Form</a> (optional)
        <br/>(Only if there is an amendment to the approved project budget)</td> 
    </tr>
    </table>

  
  </body>
</html>
