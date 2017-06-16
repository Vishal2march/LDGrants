<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="800" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - FS-10-F Long Form</title>
  </head>
  <body>
  
  <font size="1">
   <table align="center" width="96%" summary="for layout only">
    <tr>
      <td valign="top" width="50%"><b>The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>Send to:  Kim Anderson
          <br/>Division of Library Development
          <br/>Room 10B41 Cultural Education Center
          <br/>Albany, NY 12230</b>
      </td>
      <td valign="top"><b>FINAL EXPENDITURE FOR A FEDERAL OR STATE PROJECT
          <br/>FS-10-F Long Form (03/15) 
          <br/><br/>
          Project Number: 03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber  value="${thisGrant.projseqnum}"  pattern="####" />
          </b>
      </td>
    </tr>
  </table>  
  </font>
  
  
  <font size="1">
  <table align="center" width="96%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
    <tr>
      <th colspan="2">Local Agency Information</th>
    </tr>        
    <tr>
      <td>Funding Source:</td>
      <td><c:out value="${fundsource}" /></td>
    </tr>
    <tr>
      <td width="40%">Contact Person:</td>
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
      <td>Telephone#: <c:out value="${presOfficerBean.phone}" /></td>
      <td>County: <c:out value="${thisGrant.county}" /></td>
    </tr>
    <tr>
      <td>E-Mail Address: </td>
      <td><c:out value="${presOfficerBean.email}" /></td>
    </tr>    
    <tr> 
      <td>Project Operation Dates:</td>
      <td>Start 7/1/<fmt:formatNumber value="${thisGrant.fycode-1}" minIntegerDigits="2"/>       
      &nbsp;&nbsp;End 6/30/<c:out value="${thisGrant.fycode+1}"/></td>
    </tr>
  </table>  
  </font>
  <br/>
  
  <font size="1">
  <table align="center" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th>INSTRUCTIONS</th>
    </tr>   
    <tr>
      <td>
       This form has been customized for use for the State Construction Program
       ONLY and therefore should not be used for any other purpose. 
       <br/><br/>
       Submit three sets of the FS-10-F form with original signatures in blue ink to
       Kim Anderson, Division of Library Development, Room 10B41 Cultural Education 
       Center, Albany, NY  12230.      
       <br/><br/>
       All budget items for this program should be entered in whole dollar amounts only.
       <br/><br/>
       Certification on last page must be signed in blue ink by Chief Administrative Officer or designee.
       <br/><br/>
        High quality computer generated reproductions of this form may be used.
       <br/><br/>
       For further information on completing the final expenditure report,  
       refer to the Fiscal Guidelines for Federal and State Aided Grants which 
       may be accessed at www.oms.nysed.gov/cafe/ or 
       contact Kimberly Anderson at kimberly.anderson@nysed.gov or 518-486-5252
      </td>
    </tr>
  </table>
  </font>
  
  <%--
  <pd4ml:page.break />
    
  <font size="1">
  <p align="center"><b>FS-10-F</b></p>
  
  <p align="center"><b>SALARIES FOR PROFESSIONAL STAFF:  Code 15</b></p>  
  Include all salaries for professional staff approved for reimbursement in budget.

  <table width="96%" border="1" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
    <tr>
      <th width="25%">Name</th>
      <th width="25%">Position Title</th>
      <th width="25%">Beginning and Ending Dates of Employment</th>
      <th width="25%">Salary Paid</th>
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 15</td>
      <td>&nbsp;</td>
    </tr>
  </table>



  <p align="center"><b>SALARIES FOR SUPPORT STAFF:  Code 16</b></p>  
  Include all salaries for support staff approved for reimbursement in budget.
  
  <table width="96%" border="1" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
    <tr>
      <th width="25%">Name</th>
      <th width="25%">Position Title</th>
      <th width="25%">Beginning and Ending Dates of Employment</th>
      <th width="25%">Salary Paid</th>
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 16</td>
      <td>&nbsp;</td>
    </tr>
  </table>
  </font>
      
      --%>
      
  <pd4ml:page.break />
  
  
  <font size="1">
  <p align="center"><b>FS-10-F</b></p>
  
  <p align="center"><b>PURCHASED SERVICES:  Code 40</b></p>
    
  <table width="96%" border="1" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
    <tr>
      <th width="25%">Encumbrance Date</th>
      <th width="25%">Provider of Service</th>
      <th width="25%">Check or Journal Entry #</th>
      <th width="25%">Amount Expended</th>
    </tr>
    
    <c:set var="purchtot" value="0" />
    <c:forEach var="row" items="${expenseBeans}"> 
     <c:if test="${row.expenseId==40}">
      <tr>
        <td><c:out value="${row.beginDateStr}" /></td>
        <td><c:out value="${row.provider}" /></td>
        <td><c:out value="${row.journalEntry}" /></td>
         <td><fmt:formatNumber value="${row.expAmount}" type="currency" maxFractionDigits="0" /></td>
      </tr>
      <c:set var="purchtot" value="${purchtot + row.expAmount}"/>
     </c:if>
    </c:forEach>
    <tr>
        <td height="100">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>        
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 40</td>
      <td><fmt:formatNumber value="${purchtot}" type="currency"/></td>
    </tr>
  </table>
  
  
  
  <pd4ml:page.break />
  
  <p align="center"><b>FS-10-F</b></p>
  
  <p align="center"><b>SUPPLIES AND MATERIALS:  Code 45</b></p>
    
  <table width="96%" border="1" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
    <tr>
      <th width="25%">Purchase Order Date</th>
      <th width="25%">Vendor</th>
      <th width="25%">Check or Journal Entry #</th>
      <th width="25%">Amount Expended</th>
    </tr>
    
    <c:set var="supptot" value="0" />
    <c:forEach var="row" items="${expenseBeans}"> 
     <c:if test="${row.expenseId==45}">
      <tr>
        <td><c:out value="${row.beginDateStr}" /></td>
        <td><c:out value="${row.provider}" /></td>
        <td><c:out value="${row.journalEntry}" /></td>
       <td><fmt:formatNumber value="${row.expAmount}" type="currency" maxFractionDigits="0" /></td>
      </tr>
      <c:set var="supptot" value="${supptot + row.expAmount}"/>
      </c:if>
    </c:forEach>
    <tr>
        <td height="100">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>        
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 45</td>
      <td><fmt:formatNumber value="${supptot}" type="currency" /></td>
    </tr>
  </table>
  
  <%--
  <p align="center"><b>TRAVEL EXPENSES:  Code 46</b></p>
    
  <table width="96%" border="1" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
    <tr>
      <th width="20%">Dates of Travel</th>
      <th width="20%">Name of Traveler</th>
      <th width="20%">Destination and Purpose</th>
      <th width="20%">Check or Journal Entry #</th>
      <th width="20%">Amount Expended</th>
    </tr>
    <tr>
      <td colspan="4" align="right">Subtotal - Code 46</td>
      <td>&nbsp;</td>
    </tr>
  </table>
  </font>

  <pd4ml:page.break />


  <font size="1">
  <p align="center"><b>FS-10-F Page 5</b></p>
  
  <p align="center"><b>EMPLOYEE BENEFITS:  Code 80</b></p>
  List only the total project salary amount for each benefit category.  Benefits may 
  only be claimed for salaries reported in Code 15 or Code 16.  Rates used for project 
  personnel must be the same as those used for other agency personnel.
  
  
  <table width="96%" border="1" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
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
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 80</td>
      <td>&nbsp;</td>
    </tr>
  </table>
  </font>
  
   <pd4ml:page.break />
   
   <font size="1">
   <p align="center"><b>FS-10-F Page 6</b></p>
   
    <p align="center"><b>INDIRECT COST: Code 90</b></p>
    
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <td width="60%">A. Modified Direct Cost Base - Sum of all preceding subtotals 
      (codes 15, 16, 40, 45, 46, and 80 and excludes the portion of each subcontract 
      exceeding $25,000 and any flow through funds)</td>
      <td width="40%">$_________________(A)</td>      
    </tr>
    <tr>
      <td width="60%">B. Approved Restricted Indirect Cost Rate</td>
      <td width="40%">_________________%(B)</td>      
    </tr>
    <tr>
      <td width="60%">C. (A) x (B) = Total Indirect Cost &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;Subtotal - Code 90</td>
      <td width="40%">$_________________(C)</td>      
    </tr>
   
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
  </table>
    
    
   <p align="center"><b>PURCHASED SERVICES WITH BOCES: Code 49</b></p>
    
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="20%">Encumbrance Date</th>
      <th width="20%">Name of BOCES</th>
      <th width="20%">Check or Journal Entry #</th>
      <th width="20%">Amount Expended</th>
    </tr>
   
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 49</td>
      <td>&nbsp;</td>
    </tr>
  </table>
  
  
  <p align="center"><b>MINOR REMODELING: Code 30</b></p>
    
  <p>Include expenditures for salaries, associated employee benefits, 
        purchased services and supplies and materials related to alterations to existing 
        sites.</p>  
  <table width="96%" border="1" class="fsLarge" summary="for layout only">
    <tr>
      <th width="20%">Purchase Order Date Or Dates of Service</th>
      <th width="20%">Provider of Service</th>
      <th width="20%">Check or Journal Entry #</th>
      <th width="20%">Amount Expended</th>
    </tr>
   
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 30</td>
      <td>&nbsp;</td>
    </tr>
  </table>
  </font>
   --%>
   
    <pd4ml:page.break />
  
   <font size="1">
   <p align="center"><b>FS-10-F</b></p>
   
   <p align="center"><b>EQUIPMENT:  Code 20</b></p>
  
  Beginning with the 2005-06 year, all equipment to be purchased in support of 
  this project with a unit cost of $5,000 or more should be itemized in this category.  
  Equipment items under $5,000 should be budgeted under Supplies and Materials, Code 45.  
  Repairs of equipment should be budgeted under Purchased Services, Code 40.
  <br/>

  <table width="96%" border="1" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
    <tr>
      <th width="25%">Purchase Order Date</th>
      <th width="25%">Vendor</th>
      <th width="25%">Check or Journal Entry #</th>
      <th width="25%">Amount Expended</th>
    </tr>
    
    <c:set var="equiptot" value="0" />
    <c:forEach var="row" items="${expenseBeans}"> 
        <c:if test="${row.expenseId==20}">
        <tr>
          <td><c:out value="${row.beginDateStr}" /></td>
          <td><c:out value="${row.provider}" /></td>
          <td><c:out value="${row.journalEntry}" /></td>
          <td><fmt:formatNumber value="${row.expAmount}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <c:set var="equiptot" value="${equiptot + row.expAmount}" />
        </c:if>
    </c:forEach>
    <tr>
        <td height="100">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>        
    </tr>
    <tr>
      <td colspan="3" align="right">Subtotal - Code 20</td>
      <td><fmt:formatNumber value="${equiptot}" type="currency" /></td>
    </tr>
  </table>
  </font>
  
  
  <pd4ml:page.break pageFormat="rotate" htmlwidth="600"/>
  
  <font size="1">  
  <table width="99%" align="center" summary="for layout only">
    <tr>
      <td colspan="2" align="center"><b>FS-10-F</b></td>
    </tr>
    <tr>
      <td colspan="2">FINAL EXPENDITURE</td>
    </tr>
    
    <tr>
      <td width="50%" valign="top">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>SUBTOTAL</th><TH>CODE</TH><TH>PROJECT COSTS</TH>
          </tr>
          <TR>
            <Td>Professional Salaries</Td><td align="center">15</td>
            <td>&nbsp;</td>
          </TR>
          <TR>
            <Td>Support Staff Salaries</Td><td align="center">16</td>
            <td>&nbsp;</td>
          </TR>
          <TR>
            <Td>Purchased Services</Td><td align="center">40</td>
            <td><fmt:formatNumber value="${purchtot}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Supplies and Materials</Td><td align="center">45</td>
            <td><fmt:formatNumber value="${supptot}" type="currency"/></td>
          </TR>
          <TR>
            <Td>Travel Expenses</Td><td align="center">46</td>
            <td>&nbsp;</td>
          </TR>
          <TR>
            <Td>Employee Benefits</Td><td align="center">80</td>
            <td>&nbsp;</td>
          </TR>
          <TR>
            <Td>Indirect Costs</Td><td align="center">90</td>
            <td>&nbsp;</td>
          </TR>
          <tr>
            <td>BOCES Services</td><td align="center">49</td>
            <td>&nbsp;</td>
          </tr>
          <TR>
            <Td>Minor Remodeling</Td><td align="center">30</td>
            <td>&nbsp;</td>
          </TR>
          <TR>
            <Td>Equipment</Td><td align="center">20</td>
            <td><fmt:formatNumber value="${equiptot}" type="currency" /></td>
          </TR>
          <TR>
            <td>Grand Total</td><td></td><td>
            <fmt:formatNumber value="${purchtot + supptot + equiptot}" type="currency"/></td>
          </TR>
        </table>        
          
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="2">CHIEF ADMINISTRATOR'S CERTIFICATION</th>
          </tr>
          <TR>
            <td colspan="2">By signing this report, I certify to the best of my knowledge and belief that the report is true, complete, and accurate, and the expenditures, disbursements, and cash receipts are for the purposes and objectives set forth in the terms and conditions of the Federal (or State) award. I am aware that any false, fictitious, or fraudulent information, or the omission of any material fact, may subject me to criminal, civil, or administrative penalties for fraud, false statements, false claims, or otherwise. (U.S. Code Title 18, Section 1001 and Title 31, Sections 3729-3730 and 3801-3812).
            </td>
          </TR>
          <tr>
            <td colspan="2" height="5"></td>
          </tr>
          <tr>
            <td width="30%">Date_______________</td>
            <td width="70%">Signature__________________________</td>
          </tr>      
          <tr>
            <th colspan="2">Name and Title of Chief Administrative Officer</th>
          </tr>
        </table>        
      </TD>
      
      
      <td valign="top" width="50%">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; outline-color:rgb(0,0,0); border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr><td>Agency Code</td>
              <td colspan="2"><c:out value="${thisGrant.sedcode}" /></td>
          </tr>
          <tr><td>Project #</td>
              <td colspan="2">
                  03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
                  -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                  -<fmt:formatNumber  value="${thisGrant.projseqnum}"  pattern="####"/>
              </td>
          </tr>
          <tr><td>Contract #</td>
              <td colspan="2"><c:out value="${thisGrant.contractNum}" /></td>
          </tr>
          <tr>
            <td>Agency Name</td><td colspan="2"><c:out value="${thisGrant.instName}" /></td>
          </tr>
          <tr>
            <td width="40%">Project<br/>Funding Dates</td>
            <td>7/1/<fmt:formatNumber value="${thisGrant.fycode-1}" minIntegerDigits="2"/>
                <br/><hr/>From</td>
            <td>6/30/<c:out value="${thisGrant.fycode+1}"/>
                <br/><hr/>To</td>
          </tr>
          <tr>
            <td colspan="2">Approved Budget Total</td>
            <td><fmt:formatNumber value="${totAmtAppr}" type="currency"/></td>
          </tr>
        </table>
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="3">FOR DEPARTMENT USE ONLY</th>
          </tr>
          <tr>
            <th>Fiscal Year</th><th>Amount Expended</th><th>Final Payment</th>
          </tr>
          <tr valign="bottom">
            <td height="5"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="5"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="5"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr>
            <td><hr/>Voucher#</td>
            <td colspan="2"><hr/>Final Payment</td>
          </tr>
          <tr>
            <th><hr/>Log</th>
            <th><hr/>Approved</th>
            <th><hr/>MIR</th>
          </tr>
        </table>
      </td>
    </TR>
  </table>
  </font>
  
  </body>
</html>
</pd4ml:transform>