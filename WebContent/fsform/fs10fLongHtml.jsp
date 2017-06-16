<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>FS-10-F Long Form</title>
    <link href="css/FsFormStyle.css" rel="stylesheet" media="all" type="text/css" />
  </head>
  <body>
  
  <c:choose>
 <c:when test="${thisGrant.fccode==80}">
    <c:set var="fcpre" value="05"/>
 </c:when>
 <c:when test="${thisGrant.fccode==6 ||  thisGrant.fccode==7 || thisGrant.fccode==5 || thisGrant.fccode==42 || thisGrant.fccode==40  || thisGrant.fccode==20}">
    <c:set var="fcpre" value="03"/>
 </c:when>
 </c:choose>
   
  <table align="center" width="96%" summary="for layout only">
    <tr>
      <td valign="top" width="50%"><b>The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>(see instructions for mailing address)</b>
      </td>
      <td valign="top"><b>FINAL EXPENDITURE FOR A FEDERAL OR STATE PROJECT
          <br/>FS-10-F Long Form (03/15)  
          <br/><br/>
          Project Number: <c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber  value="${thisGrant.projseqnum}"  pattern="####" minIntegerDigits="4" />
          </b>
      </td>
    </tr>
  </table>  
  <br/>  
  
  <table align="center" width="96%" class="fsLarge" summary="for layout only" >
    <tr>
      <th colspan="2">Local Agency Information</th>
    </tr>        
    <tr>
      <td>Funding Source:</td>
      <td><c:out value="${fundsource}" /></td>
    </tr>
    <tr>
        <td>Fiscal Year:</td>
        <td><c:out value="${fyPeriod}"/></td>
    </tr>
    <tr>
      <td width="40%">Report Prepared By:</td>
      <td ><c:out value="${presOfficerBean.fname}" /> <c:out value="${presOfficerBean.mname}" /> 
          <c:out value="${presOfficerBean.lname}" /></td>
    </tr>
    <tr>
      <td>Agency Name:</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td valign="top">Mailing Address:</td>
      <td><c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.addr2}" /></td>
    </tr>
    <tr>
      <td valign="top">City, State:</td>
      <td><c:out value="${thisGrant.city}" /> <c:out value="${thisGrant.state}" />
      <c:out value="${thisGrant.zipcd1}" /> <c:out value="${thisGrant.zipcd2}" /></td>
    </tr>      
    <tr>
      <td>Telephone#: </td>
      <td>County: <c:out value="${thisGrant.county}" /></td>
    </tr>
    <tr>
      <td>E-Mail Address: </td>
      <td><c:out value="${presOfficerBean.email}" /></td>
    </tr>    
  </table>  
  <br/>    
  
  <table align="center" class="fsLarge" summary="for layout only" >
    <tr>
      <th>INSTRUCTIONS</th>
    </tr>   
    <tr>
      <td>
       <p>Agencies must maintain complete and accurate records and may be requested to provide additional detail to 
        support reported expenditures. <br/><br/>
       
       Submit one signed original report and 2 copies directly to the appropriate State 
       Education Department office as indicated in the application instructions for the grant 
       program for which you are applying. DO NOT submit this form to the Grants Finance.</p>
       
       <p>All encumbrances must have taken place within the approved funding dates of the project. </p>
       
       <p>Use whole dollar amounts. </p>
       
       <p>Certification on page 8 must be signed by Chief Administrative Officer or designee. </p>
       <p>High quality computer generated reproductions of this form may be used.</p>
       <p>For further information on completing the final expenditure report, please 
       refer to the Fiscal Guidelines for Federal and State Aided Grants 
       at www.oms.nysed.gov/cafe/ or contact Grants Finance at grantsweb@nysed.gov or (518) 474-4815.</p>
      </td>
    </tr>
  </table>
  
      
  <br/><div class="pageBreak" />
  
  <h3 align="center">SALARIES FOR PROFESSIONAL STAFF:  Code 15</h3>
  
  <p>Include all salaries for professional staff approved for reimbursement in budget.</p>

  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="25%">Name</th>
      <th width="25%">Position Title</th>
      <th width="25%">Beginning and Ending Dates of Employment</th>
      <th width="25%">Salary Paid</th>
    </tr>
        
    <c:set var="pertot" value="0" />
    <logic:present name="budgetBean" property="allPersRecords" >
    <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" >
      <c:choose>
      <c:when test="${PersonalBean.typeCode==0 || PersonalBean.typeCode==3}">
        <tr>
          <td><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><c:out value="${PersonalBean.beginDateStr}" /> - <c:out value="${PersonalBean.endDateStr}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <c:set var="pertot" value="${pertot + PersonalBean.expsubmitted}"/>
      </c:when>
      <c:otherwise>
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
      </c:otherwise>
      </c:choose>
    </logic:iterate>
    </logic:present>   
    <logic:empty name="budgetBean" property="allPersRecords" >
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
    </logic:empty>
    
    <tr>
      <td colspan="3" align="right">Subtotal - Code 15</td>
      <td><fmt:formatNumber value="${pertot}" type="currency"/></td>
    </tr>
  </table>



  <h3 align="center">SALARIES FOR SUPPORT STAFF:  Code 16</h3>
  
  <p>Include all salaries for support staff approved for reimbursement in budget. </p>
  
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="25%">Name</th>
      <th width="25%">Position Title</th>
      <th width="25%">Beginning and Ending Dates of Employment</th>
      <th width="25%">Salary Paid</th>
    </tr>
    
    <c:set var="pertot" value="0" />
    <logic:present name="budgetBean" property="allPersRecords" >
    <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" >
      <c:choose>
      <c:when test="${PersonalBean.typeCode==4}">
        <tr>
          <td><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><c:out value="${PersonalBean.beginDateStr}" /> - <c:out value="${PersonalBean.endDateStr}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      <c:set var="pertot" value="${pertot + PersonalBean.expsubmitted}"/>
      </c:when>
      <c:otherwise>
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
      </c:otherwise>
      </c:choose>
      
    </logic:iterate>
    </logic:present>
    <logic:empty name="budgetBean" property="allPersRecords" >
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
    </logic:empty>
    
    <tr>
      <td colspan="3" align="right">Subtotal - Code 16</td>
      <td><fmt:formatNumber value="${pertot}" type="currency"/></td>
    </tr>
  </table>
		
<br/><div class="pageBreak" />


  <h3 align="center">PURCHASED SERVICES:  Code 40</h3>
    
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="25%">Encumbrance Date</th>
      <th width="25%">Provider of Service</th>
      <th width="25%">Check or Journal Entry #</th>
      <th width="25%">Amount Expended</th>
    </tr>
    
    <c:set var="purchtot" value="0" />
    <logic:present name="budgetBean" property="allContractRecords" >
    <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
       <c:choose>
       <c:when test="${ContractedBean.typeCode==5}">
          <tr>
            <td><c:out value="${ContractedBean.encumbranceDateStr}" /></td>
            <c:choose>
            <c:when test="${thisGrant.fccode==80}">
                <td><c:out value="${ContractedBean.providerUsed}" /></td>
            </c:when>
            <c:otherwise>
                <td><c:out value="${ContractedBean.recipient}" /></td>
            </c:otherwise>
            </c:choose>
            <td><c:out value="${ContractedBean.journalEntry}" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <c:set var="purchtot" value="${purchtot + ContractedBean.expsubmitted}"/>
       </c:when>
       <c:otherwise>
          <tr>
            <th width="25%" height="20%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
          </tr>
        </c:otherwise>
        </c:choose>
        
    </logic:iterate>
    </logic:present>
    <logic:empty name="budgetBean" property="allContractRecords" >
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
    </logic:empty>
    
    <tr>
      <td colspan="3" align="right">Subtotal - Code 40</td>
      <td><fmt:formatNumber value="${purchtot}" type="currency"/></td>
    </tr>
  </table>
  
  
  
  <h3 align="center">SUPPLIES AND MATERIALS:  Code 45</h3>
    
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="25%">Purchase Order Date</th>
      <th width="25%">Vendor</th>
      <th width="25%">Check or Journal Entry #</th>
      <th width="25%">Amount Expended</th>
    </tr>
    
    <c:set var="supptot" value="0" />
    <logic:present name="budgetBean" property="allSupplyRecords" >
    <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" >
      <c:choose>
      <c:when test="${SupplyBean.suppmatCode==1}">
          <tr>
            <td><c:out value="${SupplyBean.encumbranceDateStr}" /></td>
            <td><c:out value="${SupplyBean.vendor}" /></td>
            <td><c:out value="${SupplyBean.journalEntry}" /></td>
            <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <c:set var="supptot" value="${supptot + SupplyBean.expsubmitted}"/>
      </c:when>
      <c:otherwise>
          <tr>
            <th width="25%" height="20%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
          </tr>
      </c:otherwise>
      </c:choose>
      
    </logic:iterate>
    </logic:present>
    <logic:empty name="budgetBean" property="allSupplyRecords" >
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
    </logic:empty>
    
    <tr>
      <td colspan="3" align="right">Subtotal - Code 45</td>
      <td><fmt:formatNumber value="${supptot}" type="currency" /></td>
    </tr>
  </table>
  
  
  
  <h3 align="center">TRAVEL EXPENSES:  Code 46</h3>
    
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="20%">Dates of Travel</th>
      <th width="20%">Name of Traveler</th>
      <th width="20%">Destination and Purpose</th>
      <th width="20%">Check or Journal Entry #</th>
      <th width="20%">Amount Expended</th>
    </tr>
    
    <logic:present name="budgetBean" property="allTravelRecords" >
    <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
      <tr>
        <td><c:out value="${TravelBean.travelPeriod}" /></td>
        <td><c:out value="${TravelBean.travelerName}" /></td>
        <td><c:out value="${TravelBean.purpose}" /></td>
        <td><c:out value="${TravelBean.journalEntry}" /></td>
        <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </logic:iterate>
    </logic:present>
     <logic:empty name="budgetBean" property="allTravelRecords" >
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
    </logic:empty>
    
    <tr>
      <td colspan="4" align="right">Subtotal - Code 46</td>
      <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency"/></td>
    </tr>
  </table>


<br/><div class="pageBreak" />


  <h3 align="center">EMPLOYEE BENEFITS:  Code 80</h3>
  <p>List only the total project salary amount for each benefit category.  Benefits may 
  only be claimed for salaries reported in Code 15 or Code 16.  Rates used for project 
  personnel must be the same as those used for other agency personnel.</p>
  
  
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="25%">Benefit</th>
      <th width="25%">Project Salaries</th>
      <th width="25%">Rate</th>
      <th width="25%">Amount Expended</th>
    </tr>
    <tr>
      <td>Teacher Retirement</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="50">Employee Retirement</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Other Retirement</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Social Security</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Worker's Compensation</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Unemployment Insurance</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Health Insurance</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
     <tr>
      <td>Other (identify)</td>
      <td>&nbsp;</td>
      <td><fmt:formatNumber value="${BenefitsBean.benpercent}" /></td>
      <td>
        <logic:present name="budgetBean" property="allBenRecords" >
        <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
          <fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /><br/>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 80</td>
      <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency"/></td>
    </tr>
  </table>
  
<br/><div class="pageBreak" />

 
  <h3 align="center">PURCHASED SERVICES WITH BOCES:  Code 49</h3>
 
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="25%">Encumbrance Date</th>
      <th width="25%">Name of BOCES</th>
      <th width="25%">Check or Journal Entry #</th>
      <th width="25%">Amount Expended</th>
    </tr>
    
    <c:set var="boces" value="0" />
    <logic:present name="budgetBean" property="allContractRecords" >
    <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
        <c:choose>
        <c:when test="${ContractedBean.typeCode==6}">
            <tr>
              <td><c:out value="${ContractedBean.encumbranceDateStr}" /></td>
              <td><c:out value="${ContractedBean.recipient}" /></td>
              <td><c:out value="${ContractedBean.journalEntry}" /></td>
              <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            </tr>
            <c:set var="bocestot" value="${bocestot + ContractedBean.expsubmitted}" />
        </c:when>
        <c:otherwise>
          <tr>
            <th width="25%" height="20%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
          </tr>
        </c:otherwise>
        </c:choose>
      
    </logic:iterate>
    </logic:present>
     <logic:empty name="budgetBean" property="allContractRecords" >
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
    </logic:empty>
    
    <tr>
      <td colspan="3" align="right">Subtotal - Code 49</td>
      <td><fmt:formatNumber value="${bocestot}" type="currency" /></td>
    </tr>
  </table>
  <br/><br/>
  
  
  <h3 align="center">MINOR REMODELING:  Code 30</h3>
  
  <p>Include expenditures for salaries, associated employee benefits, purchased services and supplies and 
  materials related to alterations to existing sites.</p>

  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="25%">Purchase Order Date or Dates of Service</th>
      <th width="25%">Provider of Service</th>
      <th width="25%">Check or Journal Entry #</th>
      <th width="25%">Amount Expended</th>
    </tr>
    
    <c:set var="othertot" value="0" />
    <logic:present name="budgetBean" property="allExpRecords" >
    <logic:iterate name="budgetBean" property="allExpRecords" id="OtherExpBean" > 
        
        <tr>
          <td><c:out value="${OtherExpBean.encumbranceDateStr}" /></td>
          <c:choose>
          <c:when test="${thisGrant.fccode==80}">
              <td><c:out value="${OtherExpBean.providerUsed}" /></td>
          </c:when>
          <c:otherwise>
              <td><c:out value="${OtherExpBean.description}" /></td>
          </c:otherwise>
          </c:choose>
          <td><c:out value="${OtherExpBean.journalEntry}" /></td>
          <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <c:set var="othertot" value="${othertot + OtherExpBean.expsubmitted}" />
        
    </logic:iterate>
    </logic:present>
     <logic:empty name="budgetBean" property="allExpRecords" >
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
    </logic:empty>
    
    <tr>
      <td colspan="3" align="right">Subtotal - Code 30</td>
      <td><fmt:formatNumber value="${othertot}" type="currency" /></td>
    </tr>
  </table>
  
  
  
 
 
 <br/><div class="pageBreak" />
  
  <h3 align="center">EQUIPMENT:  Code 20</h3>
  
  <p>Items of equipment purchased must agree in type and number with the equipment
  approved in the project budget.</p>

  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="25%">Purchase Order Date</th>
      <th width="25%">Vendor</th>
      <th width="25%">Check or Journal Entry #</th>
      <th width="25%">Amount Expended</th>
    </tr>
    
    <c:set var="equiptot" value="0" />
    <logic:present name="budgetBean" property="allSupplyRecords" >
    <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
        <c:choose>
        <c:when test="${SupplyBean.suppmatCode==2}">
            <tr>
              <td><c:out value="${SupplyBean.encumbranceDateStr}" /></td>
              <td><c:out value="${SupplyBean.vendor}" /></td>
              <td><c:out value="${SupplyBean.journalEntry}" /></td>
              <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            </tr>
            <c:set var="equiptot" value="${equiptot + SupplyBean.expsubmitted}" />
        </c:when>
        <c:otherwise>
          <tr>
            <th width="25%" height="20%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
            <th width="25%">&nbsp;</th>
          </tr>
        </c:otherwise>
        </c:choose>
      
    </logic:iterate>
    </logic:present>
    <logic:empty name="budgetBean" property="allSupplyRecords" >
        <tr>
          <th width="25%" height="20%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
          <th width="25%">&nbsp;</th>
        </tr>
    </logic:empty>
    
    <tr>
      <td colspan="3" align="right">Subtotal - Code 20</td>
      <td><fmt:formatNumber value="${equiptot}" type="currency" /></td>
    </tr>
  </table>
  
  
<br/><div class="pageBreak" />

  <h3 align="center">Reminders</h3>  
  
  <p>Be sure to submit one original and 2 copies directly to the appropriate State 
       Education Department office as indicated in the application instructions for the grant 
       program for which you are applying. DO NOT submit this form to the Grants Finance.</p>
  
  <p>For State projects, final expenditure reports are due within 30 days after the project end date.  
Reports for federal projects are due within 90 days after the project end date. For certain programs, the 
Department program manager may impose an earlier due date. See the Grant 
Award Notice to verify the due date. </p>
  
  <p>After review by Grants Finance, a copy of the FS-10-F will be sent to the contact person at the 
address on Page 1. </p>

  <p>All encumbrances must be made within the approved project funding dates, 
  which are indicated on the approved FS-10 as well as the Grant Award Notice.
  See the Fiscal Guidelines for Federal and State Aided Grants at http://www.oms.nysed.gov/cafe/ for a detailed 
  explanation of the review process.</p>
  
  <p>Be sure to check your math and carry all subtotals forward to the Summary 
  on Page 8.  Simple mathematical errors often require Grants Finance to contact
  the local agency resulting in unnecessary delays in closeout and final payment.
  Use whole dollars only.</p>
    
  <p>Be sure to complete the Agency Code and Project # on Page 8.  For Special 
  Legislative projects and Grant Contracts, also enter the Contract #.</p>
  
  <p>Please make sure  that Page 8 faces out.</p>

<br/><div class="pageBreak" />


  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td colspan="2"><h3>FINAL EXPENDITURE SUMMARY</h3></td>
    </tr>
    
    <tr>
      <td width="50%" valign="top">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>SUBTOTAL</th><TH>CODE</TH><TH>PROJECT COSTS</TH>
          </tr>
          <TR>
            <Td>Professional Salaries</Td><td align="center">15</td>
            <td><fmt:formatNumber value="${totalsBean.proffExpSub}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Support Staff Salaries</Td><td align="center">16</td>
            <td><fmt:formatNumber value="${totalsBean.profsuppExpSub}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Purchased Services</Td><td align="center">40</td>
            <td><fmt:formatNumber value="${totalsBean.purchExpSub}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Supplies and Materials</Td><td align="center">45</td>
            <td><fmt:formatNumber value="${totalsBean.supplyExpSub}" type="currency"/> </td>
          </TR>
          <TR>
            <Td>Travel Expenses</Td><td align="center">46</td>
            <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency"/> </td>
          </TR>
          <TR>
            <Td>Employee Benefits</Td><td align="center">80</td>
            <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency"/></td>
          </TR>
          <tr>
            <td>BOCES Services</td><td align="center">49</td>
            <td><fmt:formatNumber value="${totalsBean.bocesExpSub}" type="currency"/></td>
          </tr>
          <TR>
            <Td>Minor Remodeling</Td><td align="center">30</td>
            <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency"/> </td>
          </TR>
          <TR>
            <Td>Equipment</Td><td align="center">20</td>
            <td><fmt:formatNumber value="${totalsBean.equipExpSub}" type="currency"/> </td>
          </TR>
          <TR>
            <td>Grand Total</td><td></td><td>
            <fmt:formatNumber value="${totalsBean.totExpSub}" type="currency"/></td>
          </TR>
        </table>        
        <br/>        
      </TD>
      
      
      <td valign="top" width="50%">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; outline-color:rgb(0,0,0); border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
         <tr>
           <td height="15"></td>
         </tr>
          <tr><td>Agency Code</td>
              <td colspan="2"><c:out value="${thisGrant.sedcode}" /></td>
          </tr>
          <tr><td>Project #</td>
              <td colspan="2">
                  <c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
                  -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                  -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/>
              </td>
          </tr>
          <tr><td>Contract #</td>
              <td colspan="2"><c:out value="${thisGrant.contractNum}" /></td>
          </tr>
          <tr>
            <td>Agency Name</td><td colspan="2"><c:out value="${thisGrant.instName}" /></td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
          <tr>
            <td width="40%">Project<br/>Funding Dates</td>
            <td><fmt:formatDate  value="${thisGrant.startdate}" pattern="MM/dd/yyyy"/></td>
            <td><fmt:formatDate  value="${thisGrant.enddate}" pattern="MM/dd/yyyy"/></td>
          </tr>
          <tr>
            <td></td>
            <td width="30%"><hr/>From</td>
            <td><hr/>To</td>
          </tr>
          <tr>
            <td>Approved Budget Total</td>
            <td colspan="2"><fmt:formatNumber value="${totAmtAppr}" type="currency"/></td>
          </tr>
        </table>
      </td>
    </TR>
  </table>
  <br/><br/><br/>
    
  <table width="95%" align="center" summary="for layout only"> 
    <tr>
      <td width="50%" valign="top">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="2">CHIEF ADMINISTRATOR'S CERTIFICATION</th>
          </tr>
          <TR>
            <td colspan="2">By signing this report, I certify to the best of my knowledge and belief that the report is true, complete, and accurate, and the expenditures, disbursements, and cash receipts are for the purposes and objectives set forth in the terms and conditions of the Federal (or State) award. I am aware that any false, fictitious, or fraudulent information, or the omission of any material fact, may subject me to criminal, civil, or administrative penalties for fraud, false statements, false claims, or otherwise. (U.S. Code Title 18, Section 1001 and Title 31, Sections 3729-3730 and 3801-3812).
            </td>
          </TR>
          <tr>
            <td colspan="2" height="35"></td>
          </tr>
          <tr>
            <td width="30%">Date_______________</td>
            <td width="70%">Signature__________________________</td>
          </tr>
          <tr>
            <td colspan="2" height="15"></td>
          </tr>         
          <tr>
            <td height="35" colspan="2" align="center">
              <c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
              <c:out value="${libDirectorBean.lname}" />, <c:out value="${libDirectorBean.title}" /></td>
          </tr>
          <tr>
            <th colspan="2">Name and Title of Chief Administrative Officer</th>
          </tr>
        </table>
      </td>
      
      
      <td valign="top" width="50%">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="3">FOR DEPARTMENT USE ONLY</th>
          </tr>
          <tr>
            <th>Fiscal Year</th><th>Amount Expended</th><th>Final Payment</th>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr>
            <td height="20"></td>
          </tr>
          <tr>
            <td align="center"><hr/>Voucher#</td>
            <td align="center"><hr/>First Payment</td>
          </tr>
          <tr>
            <td colspan="3">
              <table width="100%" summary="for layout only">
              <tr>
                <td>Finance:</td>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <th><hr/>Log</th>
                <th><hr/>Approved</th>
                <th><hr/>MIR</th>
              </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>  
  </table>
  

  
  </body>
</html>
