<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="450" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" width="96%" summary="for layout only">
    <tr>
      <td valign="top" width="50%"><b>The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>Grants Finance, Rm. 510W EB<br/>Albany, NY 12234</b>
      </td>
      <td valign="top"><b>REQUEST FOR FUNDS FOR A FEDERAL OR STATE PROJECT
          <br/>FS-25 (03/15)</b>
      </td>
    </tr>
  </table>  
    
  
   <table width="100%" summary="for layout only" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;">
    <tr>
      <td>Project Number:</td>
      <td>Contract Number:</td>
    </tr>
    <tr>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
      -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
      -<fmt:formatNumber  value="${thisGrant.projseqnum}"  pattern="####"/></td>
      <td></td>
    </tr>
    <tr>
      <td>Agency Code:</td>
      <td><c:out value="${thisGrant.sedcode}" /></td>
    </tr>
    <tr>
      <td>Funding Source:</td>
      <td><c:out value="${fundsource}" /></td>
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
      <td width="50%">Contact Person:
        <c:out value="${presOfficerBean.fname}" /> <c:out value="${presOfficerBean.mname}" /> 
          <c:out value="${presOfficerBean.lname}" /></td>
      <td>Telephone: <c:out value="${presOfficerBean.phone}" /></td>
    </tr>
    <tr>
      <td>E-Mail Address: <c:out value="${presOfficerBean.email}" /></td>
      <td>Month, Year:  <c:out value="${monthCurr}" />/<c:out value="${yearCurr}" /></td>
    </tr>    
  </table>  
  
  <table width="100%" summary="for layout only" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;">
    <tr>
      <th colspan="2">CHIEF ADMINISTRATOR'S CERTIFICATION</th>
    </tr>
    <tr>
      <th colspan="2">By signing this report, I certify to the best of my knowledge and belief that the report is true, complete, and accurate, and the expenditures, disbursements, and cash receipts are for the purposes and objectives set forth in the terms and conditions of the Federal (or State) award. I am aware that any false, fictitious, or fraudulent information, or the omission of any material fact, may subject me to criminal, civil, or administrative penalties for fraud, false statements, false claims, or otherwise. (U.S. Code Title 18, Section 1001 and Title 31, Sections 3729-3730 and 3801-3812).</th>
    </tr>
    <tr>
      <td width="30%" height="35">Date______________</td>
      <td width="70%" height="35">Signature_________________________</td>
    </tr>  
  </table>
  <br/>
  
  <table width="100%" summary="for layout only" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;">
    <tr>
      <td>1. Amount of Approved Budget (Include approved amendments)</td>
      <td>$______________</td>
    </tr>
    <tr>
      <td>2. Project Payments Received to Date</td>
      <td>$______________</td>
    </tr>
    <tr>
      <td>3. Project Cash Expenditures to Date</td>
      <td>$______________</td>
    </tr>
    <tr>
      <td>4. Cash Expenditures Anticipated During Next Month</td>
      <td>$______________</td>
    </tr>
    <tr>
      <td>5. Additional Funds Requested (Entries 3 plus 4 minus 2)</td>
      <td>$______________</td>
    </tr>      
  </table>
  <br/>
  
  <table width="100%" summary="for layout only" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;">
    <tr>
      <td colspan="4">FOR DEPARTMENT USE ONLY</td>
    </tr>
    <tr>
      <td/>
      <td/>
      <td>Fiscal Year</td>
      <td>Payment Split</td>
    </tr>
    <tr>
      <td>Voucher #</td>
      <td>___________</td>
      <td>____________</td>
      <td>$____________</td>
    </tr>
    <tr>
      <td>Finance:</td>
      <td>___________</td>
      <td>____________</td>
      <td>$____________</td>
    </tr>
    <tr>
      <td/>
      <td>Log  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MIR</td>
      <td>____________</td>
      <td>$____________</td>
    </tr>
    <tr>
      <td/>
      <td/>
      <td>____________</td>
      <td>$____________</td>
    </tr>
    <tr>
      <td/>
      <td/>
      <td>____________</td>
      <td>$____________</td>
    </tr>
  </table>
  
  <pd4ml:page.break />
  
  <br/>
  <table width="100%" summary="for layout only" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;">
    <tr>
      <th>Instructions</th>
    </tr>
    <tr>
      <td>Use this form to request funds from a grant approved by the State Education Department. Before 
      submitting a request to Grants Finance, local agency staff must have a clear understanding of the 
      policies and procedures regarding payments for federal and State grants. The Department will consider 
      the Chief Administrator's signature on the form to be confirmation of the agency's knowledge of and 
      agreement to meet the requirements. The requirements that must be met in order to receive funds using 
      form FS-25 are addressed in Grants Finance's Fiscal Guidelines for Federal and State Grants at 
      http://www.oms.nysed.gov/cafe/guidance/. 
      <br/><br/>
      Please review your agency's budget, amendment, expenditure and payment records prior to completing 
      the FS-25. <br/><br/></td>
    </tr>
    <tr>
      <td>Line 1 - Amount of Approved Budget: Enter the total amount of the approved budget plus any 
      approved budget amendments. <br/><br/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Line 2 - Project Payments Received to Date: Enter the total of any payments received by the agency 
      for this particular grant. <br/><br/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Line 3 - Project Cash Expenditures to Date: Enter the total amount of actual expenditures made under 
      this grant. <br/><br/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Line 4 - Cash Expenditures Anticipated During the Next Month: Request only what is needed to 
      support grant activities during the next month, minimizing the time between receipt of the funds and 
      disbursement. Enter zero if the grant program is reimbursement only or if your agency is receiving 
      payments on a reimbursement-only basis. <br/><br/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Line 5 - Additional Funds Requested: Add lines 3 and 4, then subtract 2. Enter the result in line 5. If 
      line 5 is zero or less, do not submit a form FS-25. <br/><br/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Please use whole dollar amounts. <br/><br/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Send one copy with original signature directly to Grants Finance for each grant. For Special Legislative 
      Projects, send one original and two copies to Grants Finance. </td>
    </tr>
  </table>  
  </font>
  
  </body>
</html>
</pd4ml:transform>