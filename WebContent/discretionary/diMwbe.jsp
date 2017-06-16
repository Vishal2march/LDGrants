<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>diMwbe</title>
  </head>
  <body>
  
  
  <h4>M/WBE Requirement</h4>
  
  
  <p><b>Minority and Women-Owned Business Enterprise (M/WBE) Participation Goals Pursuant to Article 15-A of the 
  New York State Executive Law</b></p>
  
  
  <p><b>The following M/WBE requirements apply when an applicant submits an application for grant funding 
  that exceeds $25,000 for the full grant period.</b></p>
  
  
  
  <table width="100%" summary="for layout only" class="boxtype">
      <tr>
        <td colspan="4" align="center"><b>M/WBE Documents Package (original signatures required)</b>
            <br/>(Some documents available as fillable PDF)</td>
      </tr>
      <tr>
        <td height="20"/>
      </tr>
      <tr>
        <td><b>Type of Form</b></td>
        <td><b>Full Participation</b></td>
        <td><b>Request Partial Waiver</b></td>
        <td><b>Request Total Waiver</b></td>
      </tr>
      <tr>
        <td>
          <c:choose>
          <c:when test="${param.p=='lg'}">
              <a href="docs/mwbe/mwbeCalculationLgrmif.doc" target="_blank">Calculation of M/WBE Goal Amount</a>
          </c:when>
          <c:otherwise>
              <a href="docs/mwbe/mwbeCalculation.doc" target="_blank">Calculation of M/WBE Goal Amount</a>
          </c:otherwise>
          </c:choose>   
        </td>        
        
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
      </tr>
      <tr>
        <td><a href="docs/mwbe/mwbeCoverLetter.doc" target="_blank">M/WBE Cover Letter</a></td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
      </tr>
      <tr>
        <td><a href="docs/mwbe/mwbeUtilization.doc" target="_blank">M/WBE 100 Utilization Plan</a>
            &nbsp;&nbsp;&nbsp;(<a href="docs/mwbe/mwbeUtilization.pdf" target="_blank">PDF</a> format)</td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
        <td>N/A</td>
      </tr>
      <tr>
        <td><a href="docs/mwbe/mwbeIntentToParticipate.doc" target="_blank">M/WBE 102 Notice of Intent to Participate</a>
            &nbsp;&nbsp;&nbsp;(<a href="docs/mwbe/mwbeIntentToParticipate.pdf" target="_blank">PDF</a> format)</td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
        <td>N/A</td>
      </tr>
      <tr>
        <td><a href="docs/mwbe/mwbeStaffingPlan.doc" target="_blank">EEO 100 Staffing Plan and Instructions</a>
           &nbsp;&nbsp;&nbsp;(<a href="docs/mwbe/mwbeStaffingPlan.pdf" target="_blank">PDF</a> format)</td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
      </tr>
      <tr>
        <td><a href="docs/mwbe/mwbeGoodFaith.doc" target="_blank">M/WBE 105 Contractor’s Good Faith Efforts</a></td>
        <td>N/A</td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
      </tr>
       <tr>
        <td><a href="docs/mwbe/mwbeUnavailable.doc" target="_blank">M/WBE 105A Contractor Unavailable Certification</a>
        &nbsp;&nbsp;&nbsp;(<a href="docs/mwbe/mwbeUnavailable.pdf" target="_blank">PDF</a> format)</td>
        <td>N/A</td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
      </tr>
      <tr>
        <td><a href="docs/mwbe/mwbeRequestWaiver.doc" target="_blank">M/WBE 101 Request for Waiver Form and Instructions</a>
          &nbsp;&nbsp;&nbsp;(<a href="docs/mwbe/mwbeRequestWaiver.pdf" target="_blank">PDF</a> format) </td>
        <td>N/A</td>
        <td><input type="checkbox"/></td>
        <td><input type="checkbox"/></td>
      </tr>  
    </table>
    <br/><br/>
  
  
  
  <p>
    <c:choose>
    <c:when test="${param.p=='lg'}">
        <b>All forms referenced here can be found in the M/WBE Documents section at the end of the RFP</b>
    </c:when>
    <c:otherwise>
        <b>All forms referenced here can be found in the M/WBE Documents section at the end of the C/P Discretionary
        <a href="docs/guidelinesDiscretionary.doc" target="_blank">RFP</a></b>
    </c:otherwise>
    </c:choose>
  <br/>
    All applicants are required to comply with NYSED’s Minority and Women-Owned Business Enterprises (M/WBE) policy.  
    Compliance can be achieved by one of the three methods described below.  Full participation by meeting or 
    exceeding the M/WBE participation goal for this grant is the preferred method.  <br/><br/>
    
    M/WBE participation includes services, materials, or supplies purchased from minority and women-owned firms 
    certified with the NYS Division of Minority and Women Business Development.  Not-for-profit agencies are not 
    eligible for this certification.  For additional information and a listing of currently certified M/WBEs, 
    see <a href="https://ny.newnycontracts.com/FrontEnd/VendorSearchPublic.asp?TN=ny&XID=4687" target="_blank">https://ny.newnycontracts.com/FrontEnd/VendorSearchPublic.asp?TN=ny&XID=4687</a>
    <br/><br/>
    
    The M/WBE participation goal for this grant is 30% of each applicant’s total discretionary non-personal 
    service budget over the entire term of the grant.   Discretionary non-personal service budget is defined as 
    total budget, excluding the sum of funds budgeted for:<br/><br/>
    1.	direct personal services  (i.e., professional and support staff salaries) and  fringe benefits; and
    <br/>
    2.	rent, lease, utilities and indirect costs, if these items are allowable expenditures.
    
    <br/><br/>
    For multi-year grants, applicants should use the total budget for the full multi-year term of the grants in 
    the above calculation.  The M/WBE Goal Calculation Worksheet is provided for use in calculating the dollar 
    amount of the M/WBE goal for this grant application.<br/><br/>

      
      M/WBE participation does not need to be the same for each year of a multi-year grant.  
      
      <br/><br/>
      All requested information and documentation should be provided at the time of submission. If this cannot be done, 
      the applicant will have thirty days from the date of notice of award to submit the necessary documents and 
      respond satisfactorily to any follow-up questions from the Department. Failure to do so may result in loss 
      of funding. <br/></p>
      
      
      <p>
      <b>METHODS TO COMPLY</b>
      <br/>      <br/>
      
      
      <c:choose>
      <c:when test="${param.p=='lg'}">
      
            LGRMIF applicants can comply with NYSED’s M/WBE policy by one of the following methods methods:  <br/><br/>
            
            <b>1.Full Participation</b> - This is the preferred method of compliance. Full participation is 
            achieved when an applicant meets or exceeds SED’s 20% participation goal for the LGRMIF. 
            Applicants must complete forms:
            <ul><li>  M/WBE Goal Calculation Worksheet</li>
            <li>  M/WBE Cover Letter</li>
            <li>  M/WBE 100 Utilization Plan</li>
            <li>  M/WBE 102 Notice of Intent to Participate</li>
            <li>  EEO 100 Staffing Plan and Instructions</li></ul>
      
            <br/><br/>
            <b>2.  Partial Participation, Partial Request for Waiver</b> - This is acceptable only if applicants are 
            unable to achieve full participation, but can demonstrate and document a good faith effort to 
            achieve full participation. Applicants must complete forms:
            <ul><li>  M/WBE Goal Calculation Worksheet</li>
            <li>  M/WBE Cover Letter</li>
            <li>  M/WBE 100 Utilization Plan</li>
            <li>  M/WBE 101 Request for Waiver</li>
            <li>  M/WBE 102 Notice of Intent to Participate</li>
            <li>  M/WBE 105 Contractor’s (Applicant's) Good Faith Efforts</li>
            <li>  EEO 100 Staffing Plan and Instructions</li></ul>
      
            <br/><br/>
            <b>3.  No Participation, Request for Complete Waiver</b> - This is acceptable only if applicants are 
            unable to achieve any participation, but can demonstrate and document a good faith effort to 
            achieve full or partial participation. Applicants must complete forms:
            <ul><li>  M/WBE Goal Calculation Worksheet</li>
            <li>  M/WBE Cover Letter</li>
            <li>  M/WBE 101 Request for Waiver</li>
            <li>  M/WBE 105 Contractor’s (Applicant's) Good Faith Efforts</li>
            <li>  M/WBE 105A Contractor Unavailable Certification</li>
            <li>  EEO 100 Staffing Plan and Instructions</li></ul>
            
            <br/><br/>
            <b>4.  Preferred Source</b> - Use of a Preferred Source vendor under Section 162 of the State Finance 
            Law takes precedence over compliance with SED’s M/WBE policy. Applicants utilizing a 
            Preferred Source goals must complete the following:
            <ul><li>  M/WBE Goal Calculation Worksheet</li>
            <li>  Cover letter indicating no/partial participation, name of Preferred Source vendor and amount budgeted.</li>
            <li>  EEO 100 Staffing Plan and Instructions</li></ul>
                        
            <br/><br/>
            <b>5.  Deferred Compliance</b> - This is acceptable if applicants are unable to identify participating 
            M/WBE firm(s) at the time of application submission. Applicants will then have thirty days 
            from the date of notice of grant award to submit the necessary documents for one of the 
            above methods of compliance and respond satisfactorily to any follow-up questions from 
            SED. Failure to do so may result in loss of funding.
                       
            
      </c:when>
      <c:otherwise>
          
          
          An applicant can comply with NYSED’s M/WBE policy by one of the following methods methods:  <br/> <br/> 
          
          <b>1.Full Participation</b> - This is the preferred method of compliance.  Full participation is achieved 
          when an applicant meets or exceeds the participation goals for this grant.<br/><br/>
          COMPLETE FORMS:
          <br/>  M/WBE Goal Calculation Worksheet
          <br/>  M/WBE Cover Letter
          <br/>  M/WBE 100 Utilization Plan
          <br/>  M/WBE 102 Notice of Intent to Participate
    
          <br/><br/>
          <b>2.  Partial Participation, Partial Request for Waiver</b> - This is acceptable only if good faith efforts to 
          achieve full participation are made and documented, but full participation is not possible.  <br/><br/>
          COMPLETE FORMS:  
          <br/>  M/WBE Goal Calculation Worksheet
          <br/>  M/WBE Cover Letter
          <br/>  M/WBE 100 Utilization Plan
          <br/>  M/WBE 101 Request for Waiver
          <br/>  M/WBE 102 Notice of Intent to Participate
          <br/>  M/WBE 105 Contractor’s Good Faith Efforts
    
          <br/><br/>
          <b>3.  No Participation, Request for Complete Waiver</b> - This is acceptable only if good faith efforts to achieve 
          full or partial participation are made and documented, but do not result in any participation by M/WBE firm(s).<br/><br/> 
          COMPLETE FORMS:  
          <br/>  M/WBE Goal Calculation Worksheet
          <br/>  M/WBE Cover Letter
          <br/>  M/WBE 101 Request for Waiver
          <br/>  M/WBE 105 Contractor’s Good Faith Efforts
          <br/>  M/WBE 105A Contractor Unavailable Certification
      
      </c:otherwise>
      </c:choose>
            
      </p>
      
      
     <br/><br/> 
    <p>
    <b>GOOD FAITH EFFORTS</b><br/>
    Applicants must make a good faith effort to solicit NYS certified M/WBE firms as subcontractors and/or suppliers 
    to achieve the goals for this grant.  Solicitations may include, but are not limited to:  advertisements in minority 
    and women-centered publications; solicitation of vendors found in the NYS Directory of Certified Minority and 
    Women-Owned Business Enterprises (see 
    <a href="https://ny.newnycontracts.com/FrontEnd/VendorSearchPublic.asp?TN=ny&XID=4687" target="_blank">https://ny.newnycontracts.com/FrontEnd/VendorSearchPublic.asp?TN=ny&XID=4687</a> 
    and the solicitation of minority and women-oriented trade and labor organizations.  Good faith efforts include 
    actions such as setting up meetings or announcements to make M/WBEs aware of supplier and subcontracting
    opportunities, identifying logical areas of the grant project that could be subcontracted to M/WBE firms, and 
    utilizing all current lists of M/WBEs who are available for and may be interested in subcontracting or supplying 
    goods for the project. <br/><br/>
    
    Applicants should document their efforts to comply with the stated M/WBE goals and submit this with their
    applications as evidence. Examples of acceptable documentation can be found in form M/WBE 105, Contractor’s
    Good Faith Efforts. NYSED reserves the right to reject any application for failure to document “good faith efforts.”
    </p>
    
    
    <p>
    <b>REQUEST FOR WAIVER</b> <br/>
    When full participation cannot be achieved, applicants must submit a Request for Waiver (M/WBE 101).  Requests for 
    Waivers must be accompanied by documentation explaining the good faith efforts made and reasons they were 
    unsuccessful in obtaining M/WBE participation.  
    
    <br/><br/>
    NYSED reserves the right to approve the addition or deletion of subcontractors or suppliers to enable applicants 
    to comply with the M/WBE goals, provided such addition or deletion does not impact the technical proposal and/or 
    increase the total budget.
    
    <br/><br/>
    All payments to Minority and Women-Owned Business Enterprise subcontractor(s) should be reported to the NYSED M/WBE 
    Program Unit using the M/WBE 103 Quarterly M/WBE Compliance Report. This report should be submitted on a quarterly 
    basis and can be found at 
    <a href="http://www.oms.nysed.gov/fiscal/MWBE/forms.html" target="_blank">www.oms.nysed.gov/fiscal/MWBE/forms.html</a>
    
    <br/><br/>
    NYSED’s M/WBE Coordinator is available to assist applicants in meeting the M/WBE goals.  The Coordinator can be 
    reached at MWBE@nysed.gov.   
    
    <br/><br/>
    <b>Equal Employment Opportunity Reporting (EEO) Pursuant to Article 15-A of the New York State Executive Law</b> 
    
    <br/><br/>
    Applicants must complete and submit form EEO 100: Staffing Plan.
    </p>
    
    
    <c:choose>
    <c:when test="${param.p=='lg'}">
        <p>    
            <br/><br/>
            <b>Mail your completed M/WBE documentation to:</b><br/>
            New York State Archives <br/>
            Grants Administration Unit<br/>
            Room 9A81 CEC<br/>
            Albany, NY 12230<br/>
        </p>
    </c:when>
    <c:otherwise>
        <p>    
            <br/><br/>
            <b>Mail your completed M/WBE documentation to:</b><br/>
            KERRY LYNCH<BR/>
            CONSERVATION/PRESERVATION PROGRAM<br/>
            DIVISION OF LIBRARY DEVELOPMENT<br/>
            NEW YORK STATE LIBRARY<br/>
            10B41 CULTURAL EDUCATION CENTER<br/>
            222 MADISON AVENUE<br/>
            ALBANY, NY  12230<br/>
        </p>
    </c:otherwise>
    </c:choose>
    
    
  
  </body>
</html>