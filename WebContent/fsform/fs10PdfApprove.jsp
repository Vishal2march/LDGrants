<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="450" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>fs10PdfApprove</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only">
    <tr>
      <td valign="top"><b>MAIL TO:
          The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>Division of Library Development			                 
          <br/>Room 10-B-41 Cultural Education Center
          <br/>Albany, NY  12230
          </b>
      </td>
      <td valign="top"><b>PROPOSED BUDGET FOR A
          <br/>FEDERAL OR STATE PROJECT
          <br/>FS-10 (3/15) 
          <br/><br/>
          Project Number: 03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
                          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                          -<fmt:formatNumber  value="${thisGrant.projseqnum}"  pattern="####"/>
          </b>
      </td>
    </tr>
  </table>  </font>
   
  
  <font size="1">
  <table align="center" width="96%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >      
    <tr>
      <td>Funding Source:</td>
      <td><c:out value="${fundsource}" /></td>
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
    <tr> 
      <td>Project Operation Dates:</td>
      <td>Start <fmt:formatDate  value="${thisGrant.startdate}" pattern="MM/dd/yyyy"/>       
               &nbsp;&nbsp;End <fmt:formatDate value="${thisGrant.enddate}" pattern="MM/dd/yyyy" /></td>
    </tr>
  </table>
  </font>
 
  
  
  <font size="1">
  <table align="center" width="96%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th>INSTRUCTIONS</th>
    </tr>   
    <tr >
      <td>
        Submit the original budget and the required number of copies along 
        with the completed application directly to the appropriate State Education Department
        office as indicated in the application instructions for the grant program for which 
        you are applying.  DO NOT submit this form to the Grants Finance.<br/>
        Enter whole dollar amounts only.<br/><br/>
        Prior approval by means of an approved budget (FS-10) or budget 
        amendment (FS-10-A) is required for:<br/>
        Personnel positions, number and type<br/>
        Beginning with the 2005-06 budgets, equipment items having a unit value 
        of $5,000 or more, number and type<br/>
        Any increase in a budget subtotal (professional salaries, purchased services, 
        travel, etc.) by more than 10 percent or $1,000, whichever is greater<br/>
        Any increase in the total budget amount.<br/><br/>
        Certification on page 8 must be signed in blue ink by Chief Administrative
        Officer or designee. High quality computer generated reproductions of this form may be used.<br/>
        For changes in agency or payee address contact the State Education 
        Department office indicated on the application instructions for the grant program 
        for which you are applying.<br/><br/>
        For further information on budgeting, please refer to the Fiscal 
        Guidelines for Federal and State Aided Grants which may be accessed at 
        www.oms.nysed.gov/cafe/ or call Grants Finance at (518) 474-4815.
      </td>
    </tr>
  </table>
  </font>
  
  <pd4ml:page.break />
  
  <font size="1">
  <p align="center"><b>SALARIES FOR PROFESSIONAL STAFF:  Code 15</b>
  <c:if test="${thisGrant.fccode==42 || thisGrant.fccode==40}">Do Not Use For Literacy Program</c:if>
 </p>
  
  Include only staff that are employees of the agency.  Do not include consultants or per 
  diem staff.  Do not include central administrative staff that are considered to be 
  indirect costs,  e.g., business office staff.  One full-time equivalent (FTE) equals 
  one person working an entire week each week of the project.  Express partial FTE's in 
  decimals, e.g., a teacher working one day per week equals .2 FTE.

  <table width="96%" border="1" summary="for layout only">
    <tr>
      <th width="25%">Specific Position Title</th>
      <th width="25%">Full-Time Equivalent</th>
      <th width="25%">Annualized Rate of Pay</th>
      <th width="25%">Project Salary</th>
    </tr>
    
    <c:set var="pertot" value="0" />
    <logic:present name="budgetBean" property="allPersRecords" >
    <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
      <c:if test="${PersonalBean.typeCode==0 || PersonalBean.typeCode==3}">
        <tr>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
          <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <c:set var="pertot" value="${pertot + PersonalBean.amountapproved}"/>
      </c:if>
    </logic:iterate>
    </logic:present>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 15</td>
      <td><fmt:formatNumber value="${pertot}" type="currency"/></td>
    </tr>
  </table>



  <p align="center"><b>SALARIES FOR SUPPORT STAFF:  Code 16</b>
  <c:if test="${thisGrant.fccode==42 || thisGrant.fccode==40}">Do Not Use For Literacy Program</c:if>
 </p>
  
  Include salaries for teacher aides, secretarial and clerical assistance, and for personnel 
  in pupil transportation and building operation and maintenance.  Do not include central 
  administrative staff that are considered to be indirect costs, e.g., account clerks.
  
  <table width="96%" border="1" summary="for layout only">
    <tr>
      <th width="25%">Specific Position Title</th>
      <th width="25%">Full-Time Equivalent</th>
      <th width="25%">Annualized Rate of Pay</th>
      <th width="25%">Project Salary</th>
    </tr>
    
    <c:set var="pertot" value="0"/>
    <logic:present name="budgetBean" property="allPersRecords" >
    <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" >
      <c:if test="${PersonalBean.typeCode==4}">
        <tr>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
          <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <c:set var="pertot" value="${pertot + PersonalBean.amountapproved}"/>
      </c:if>
    </logic:iterate>
    </logic:present>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 16</td>
      <td><fmt:formatNumber value="${pertot}" type="currency"/></td>
    </tr>
  </table>
</font>
		
<pd4ml:page.break />
  
  
<font size="1">
  <p align="center"><b>PURCHASED SERVICES:  Code 40</b></p>
  
  Include consultants (indicate per diem rate), rentals, tuition, and other contractual 
  services.  Copies of contracts may be requested by the State Education Department. 
  Purchased Services from a BOCES, if other than applicant agency, should be budgeted 
  under Purchased Services with BOCES, Code 49.
      
  <table width="96%" border="1" summary="for layout only">
    <tr>
      <th width="25%">Description of Item</th>
      <th width="25%">Provider of Services</th>
      <th width="25%">Calculation of Cost</th>
      <th width="25%">Proposed Expenditure</th>
    </tr>
  
    
    <logic:present name="budgetBean" property="allContractRecords" >
    <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
      <tr>
        <td><c:out value="${ContractedBean.servicedescr}" /></td>
        <td><c:out value="${ContractedBean.recipient}" /></td>
        <td>&nbsp;</td>
        <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </logic:iterate>
    </logic:present>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 40</td>
      <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency"/></td>
    </tr>
  </table>
  
  
  
  <p align="center"><b>SUPPLIES AND MATERIALS:  Code 45</b></p>
  Include computer software, library books and equipment items under $5,000 per unit.
    
  <table width="96%" border="1" summary="for layout only">
    <tr>
      <th width="25%">Description of Item</th>
      <th width="25%">Quantity</th>
      <th width="25%">Unit Cost</th>
      <th width="25%">Proposed Expenditure</th>
    </tr>
  
    <c:set var="supptot" value="0" />
    <logic:present name="budgetBean" property="allSupplyRecords" >
    <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
      <c:if test="${SupplyBean.suppmatCode==1}">
      <tr>
        <td><c:out value="${SupplyBean.description}" /></td>
        <td><c:out value="${SupplyBean.quantity}" /></td>
        <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" /></td>
        <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>
      <c:set var="supptot" value="${supptot + SupplyBean.amountapproved}"/>
      </c:if>
    </logic:iterate>
    </logic:present>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 45</td>
      <td><fmt:formatNumber value="${supptot}" type="currency" /></td>
    </tr>
  </table>
</font> 
  
<pd4ml:page.break />
  
<font size="1">  
  <p align="center"><b>TRAVEL EXPENSES:  Code 46</b></p>
  Include pupil transportation, conference costs and travel of staff between instructional 
  sites.  Specify agency approved mileage rate for travel by personal car or school-owned 
  vehicle.  
  
  <table width="96%" border="1" summary="for layout only">
    <tr>
      <th width="20%">Position of Traveler</th>
      <th width="20%">Destination and Purpose</th>
      <th width="20%">Calculation of  Cost</th>
      <th width="20%">Proposed Expenditures</th>
    </tr>
    
    <logic:present name="budgetBean" property="allTravelRecords" >
    <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
      <tr>
        <td><c:out value="${TravelBean.purpose}" /></td>
        <td><c:out value="${TravelBean.description}" /></td>
        <td><c:out value="${TravelBean.costsummary}" /></td>
        <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </logic:iterate>
    </logic:present>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 46</td>
      <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency"/></td>
    </tr>
  </table>
  
  
  <p align="center"><b>EMPLOYEE BENEFITS:  Code 80</b>
  <c:if test="${thisGrant.fccode==42 || thisGrant.fccode==40}">Do Not Use For Literacy Program</c:if>
 </p>
  Rates used for project personnel must be the same as those used for other agency personnel.  
  
  <table width="96%" border="1" summary="for layout only">
    <tr>
      <th width="30%">Benefit</th>
      <th width="30%">Proposed Expenditure</th>
    </tr>
    <tr>
      <td>Social Security</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Teacher Retirement</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Employee Retirement</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Other Retirement</td>
      <td>&nbsp;</td>
    </tr>    
    <tr>
      <td>Health Insurance</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Worker's Compensation</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Unemployment Insurance</td>
      <td>&nbsp;</td>
    </tr>    
    <tr>
      <td>Other (identify)</td>
      <td>
        <logic:present name="budgetBean" property="allBenRecords" >
        <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
          <fmt:formatNumber value="${BenefitsBean.amountapproved}" type="currency" maxFractionDigits="0" /><br/>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <tr>
      <td align="right">Subtotal - Code 80</td>
      <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency"/></td>
    </tr>
  </table>
</font>


<pd4ml:page.break />   
   
  
  
<font size="1">  
  <p align="center"><b>EQUIPMENT:  Code 20</b></p>
  
  All equipment to be purchased in support of this project with a unit cost of $5,000 or 
  more should be itemized in this category.  Equipment items under $5,000 should be budgeted
  under Supplies and Materials, Code 45.  Repairs of equipment should be budgeted under 
  Purchased Services, Code 40.

  <table width="96%" border="1" summary="for layout only">
    <tr>
      <th width="25%">Description of Item</th>
      <th width="25%">Quantity</th>
      <th width="25%">Unit Cost</th>
      <th width="25%">Proposed Expenditure</th>
    </tr>
    
    <c:set var="equiptot" value="0" />
    <logic:present name="budgetBean" property="allSupplyRecords" >
    <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
        <c:if test="${SupplyBean.suppmatCode==2}">
        <tr>
          <td><c:out value="${SupplyBean.description}" /></td>
          <td><c:out value="${SupplyBean.quantity}" /></td>
          <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <c:set var="equiptot" value="${equiptot + SupplyBean.amountapproved}" />
        </c:if>
    </logic:iterate>
    </logic:present>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 20</td>
      <td><fmt:formatNumber value="${equiptot}" type="currency" /></td>
    </tr>
  </table>
</font>  
  
<pd4ml:page.break />

<font size="1">
<p align="center"><b>Helpful Reminders</b></p>  
  
  <p>Check for the required number of copies to be submitted, including the number of 
  original signature copies.  The number of copies may vary from program to program.  If 
  unsure, contact the State Education Department office responsible for the program for 
  which you are applying.</p>
  
  <p>An approved copy of the FS-10 will be returned to the contact person at the address 
  completed on page 1.  A window envelope will be used for the return mailing; please make
  sure that the contact information is accurate, legible, and confined to the address field.</p>
  
  <p>Be sure to check your math and carry all subtotals forward to the Summary on Page 8.  
  Simple mathematical errors often require Grants Finance to contact both the local agency 
  and other State Education Department offices, resulting in unnecessary delays in program
  approval.  And remember, use whole dollars only.</p>

<p>School districts and BOCES should use the restricted indirect cost rate that has
  been approved for the school year in which the grant will operate. Most other 
  agencies are subject to a fixed maximum rate depending on the grant program and 
  type of agency. Contact Grants Finance at (518) 474-4815 if you have any questions 
  regarding indirect costs.</p>
  
  <p>The modified direct cost used in the calculation of indirect cost must 
  exclude equipment, minor remodeling, the portion of each subcontract exceeding
  $25,000 and any flow through funds.</p>
  
  <p>Only equipment items with a unit cost of $5,000 or more should be included under 
  Equipment, Code 20.<br/><br/>
  
    Be sure to complete the Agency Code and Project # on Page 8 as well as the Project #, if pre-assigned.
  For Special 
  Legislative projects and Grant Contracts, also enter the Contract #.<br/><br/>
  
  For ease of data entry at the State Education Department, please make sure
  that Page 8 faces out.  Submit forms to the State Education Department Program Office 
  as indicated in the application instructions for the grant program for which you are 
  applying.  DO NOT submit this form to the Grants Finance Unit.</p>
</font>


<pd4ml:page.break />


<font size="1">  
  <b>BUDGET SUMMARY</b>
  <table width="100%" align="center" summary="for layout only">
        
    <tr><!-- top row contains top 2 boxes - categories and agency code -->
      <td width="40%" valign="top">
      
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>CATEGORIES</th><TH>CODE</TH><TH>PROJECT COSTS</TH>
          </tr>
          <TR>
            <Td>Professional Salaries</Td><td align="center">15</td>
            <td><fmt:formatNumber value="${totalsBean.proffAmtAppr}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Support Staff Salaries</Td><td align="center">16</td>
            <td><fmt:formatNumber value="${totalsBean.profsuppAmtAppr}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Purchased Services</Td><td align="center">40</td>
            <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Supplies and Materials</Td><td align="center">45</td>
            <td><fmt:formatNumber value="${totalsBean.supplyAmtAppr}" type="currency"/> </td>
          </TR>
          <TR>
            <Td>Travel Expenses</Td><td align="center">46</td>
            <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Employee Benefits</Td><td align="center">80</td>
            <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Minor Remodeling</Td><td align="center">30</td><td> </td>
          </TR>
          <TR>
            <Td>Equipment</Td><td align="center">20</td>
            <td><fmt:formatNumber value="${totalsBean.equipAmtAppr}" type="currency"/> </td>
          </TR>
          <TR>
            <td>Grand Total</td><td></td><td>
            <fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency"/></td>
          </TR>
        </table>    
   
           
        
        <table width="60%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="2">CHIEF ADMINISTRATOR'S CERTIFICATION</th>
          </tr>
          <TR>
            <td colspan="2">By signing this report, I certify to the best of my knowledge and belief that the report is true, complete, and accurate, and the expenditures, disbursements, and cash receipts are for the purposes and objectives set forth in the terms and conditions of the Federal (or State) award. I am aware that any false, fictitious, or fraudulent information, or the omission of any material fact, may subject me to criminal, civil, or administrative penalties for fraud, false statements, false claims, or otherwise. (U.S. Code Title 18, Section 1001 and Title 31, Sections 3729-3730 and 3801-3812).
            </td>
          </TR>
          <tr>
            <td colspan="2" height="35"/>
          </tr>
          <tr>
            <td width="30%">Date______________</td>
            <td width="70%">Signature_________________________</td>
          </tr>
          <tr>
            <td colspan="2" height="15"></td>
          </tr>
          <tr>
            <th colspan="2">Name and Title of Chief Administrative Officer</th>
          </tr>
          <tr>
            <td height="35" colspan="2" align="center">
              <c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
              <c:out value="${libDirectorBean.lname}" />, <c:out value="${libDirectorBean.title}" /></td>
          </tr>
        </table>
      </TD>
      
      
      <td valign="top" width="60%">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; outline-color:rgb(0,0,0); border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr><td>Agency Code</td>
              <td><c:out value="${thisGrant.sedcode}" /></td>
          </tr>
          <tr><td>Project #</td>
              <td>
                  03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
                  -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                  -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" />
              </td>
          </tr>
          <tr><td>Tracking/Contract #</td>
              <td><c:out value="${thisGrant.contractNum}" /></td>
          </tr>
          <tr>
            <td>Federal Employer ID #</td>
            <td><c:out value="${thisGrant.federalid}" /></td>
          </tr>
          <tr>
            <td>Agency Name</td><td><c:out value="${thisGrant.instName}" /></td>
          </tr>
        </table>
        

        
     
      
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="3">For  Department Use Only</th>
          </tr>
          <tr>
            <td width="40%">Approved<br/>Funding Dates:</td>
            <td><fmt:formatDate  value="${thisGrant.startdate}" pattern="MM/dd/yyyy"/></td>
            <td><fmt:formatDate value="${thisGrant.enddate}" pattern="MM/dd/yyyy" /></td>
          </tr>
          <tr>
            <td></td>
            <td width="30%"><hr/>From</td>
            <td><hr/>To</td>
          </tr>
          <tr>
            <td>Program Approval:</td>
            <td colspan="2" valign="bottom"><hr/></td>
          </tr>
          <tr>
            <td height="10"></td>
            <td></td>
            <td></td>
          </tr>
          <tr>
            <td>Date:</td>
            <td colspan="2" valign="bottom"><hr/></td>
          </tr>
        </table>
      
      
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>Fiscal Year</th><th>Amount Budgeted</th><th>First Payment</th>
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
            <td height="10"></td>
          </tr>
          <tr>
            <td><hr/>Voucher#</td>
            <td colspan="2"><hr/>First Payment</td>
          </tr>
          <tr>
            <td height="20"></td>
          </tr>
          <tr>
            <td>Log</td>
            <td>Approved</td>
            <td>MIR</td>
          </tr>
        </table>
      </td>
    </tr>  
    
  </table>
</font>

  
  
  </body>
</html>
</pd4ml:transform>