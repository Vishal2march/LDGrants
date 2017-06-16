<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="800" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>fs10FrontBackPdf</title>
  </head>
  <body>
  
  <c:set var="sdate" value="4/1/"/>
  <c:set var="edate" value="3/31/"/>
  <c:set var="endyear" value="${thisGrant.fycode}"/>
    
  <font size="1">
  <table align="center" width="96%" summary="for layout only">
    <tr>
      <td valign="top" width="50%"><b>
          The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>(see instructions for mailing address)
          </b>
      </td>
      <td valign="top"><b>PROPOSED BUDGET FOR A FEDERAL OR STATE PROJECT
          <br/>FS-10 (3/15) 
          </b>
      </td>
    </tr>
  </table>  
  <br/>
  
  <table align="center" width="96%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;"  summary="for layout only" >   
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
      <td>Start <c:out value="${sdate}"/><fmt:formatNumber value="${thisGrant.fycode-1}" minIntegerDigits="2"/>       
      &nbsp;&nbsp;End <c:out value="${edate}"/><c:out value="${endyear}"/></td>
    </tr>
  </table>
  
 
  
  
  <table align="center" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;"  summary="for layout only" >
    <tr>
      <th>INSTRUCTIONS</th>
    </tr>   
    <tr >
      <td>
        Submit the original budget and the required number of copies along 
        with the completed application directly to the appropriate State Education Department
        office as indicated in the application instructions for the grant program for which 
        you are applying.  DO NOT submit this form to the Grants Finance.<br/><br/>
        Enter whole dollar amounts only.<br/><br/>
        Prior approval by means of an approved budget (FS-10) or budget 
        amendment (FS-10-A) is required for:<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Personnel positions, number and type<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beginning with the 2005-06 budgets, equipment items having a unit value 
        of $5,000 or more, number and type<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Budgets for 2004-05 and earlier years equipment items having a unit value of $1,000 or more, number and type<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Minor remodeling<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Any increase in a budget subtotal (professional salaries, purchased services, 
        travel, etc.) by more than 10 percent or $1,000, whichever is greater<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Any increase in the total budget amount.<br/><br/>
        Certification on page 8 must be signed in blue ink by Chief Administrative
        Officer or designee. High quality computer generated reproductions of this form may be used.<br/><br/>
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
  
  <pd4ml:page.break/>
  
  
  <font size="1">
  <table width="96%" align="center" summary="for layout only">
    <tr>
      <td colspan="2"><b>BUDGET SUMMARY</b></td>
    </tr>
    <tr><!-- top row contains top 2 boxes - categories and agency code -->
      <td width="50%" valign="top">
      
      <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>CATEGORIES</th><TH>CODE</TH><TH>PROJECT COSTS</TH>
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
            <td>&nbsp;</td>
          </TR>
          <TR>
            <Td>Supplies and Materials</Td><td align="center">45</td>
            <td>&nbsp;</td>
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
            <Td>BOCES Services</Td><td align="center">49</td>
            <td>&nbsp;</td>
          </TR>
          <TR>
            <Td>Minor Remodeling</Td><td align="center">30</td>
            <td>&nbsp;</td>
          </TR>
          <TR>
            <Td>Equipment</Td><td align="center">20</td>
            <td>&nbsp;</td>
          </TR>
          <TR>
            <td>Grand Total</td><td></td><td><fmt:formatNumber value="${thisGrant.fundAmount}" type="currency"/></td>
          </TR>
        </table>                      
      </TD>
      
      
      <td valign="top">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; outline-color:rgb(0,0,0); border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr><td>Agency Code</td>
              <td><c:out value="${thisGrant.sedcode}" /></td>
          </tr>
          <tr><td>Project #</td>
              <td>
                  03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
                  -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                  -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/>
              </td>
          </tr>
          <tr><td>Tracking/Contract #</td>
              <td><c:out value="${thisGrant.contractNum}" /></td>
          </tr>
          <tr>
            <td colspan="2">Federal Employer ID #<br/>(New non-municipal agencies only)</td>
          </tr>
          <tr>
            <td>Agency Name</td><td><c:out value="${thisGrant.instName}" /></td>
          </tr>
        </table>
        

        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="3">For  Department Use Only</th>
          </tr>
          <tr>
            <td width="40%">Approved<br/>Funding Dates:</td>
            <td><c:out value="${sdate}"/><fmt:formatNumber value="${thisGrant.fycode-1}" minIntegerDigits="2"/> </td>      
            <td><c:out value="${edate}"/><c:out value="${endyear}"/></td>
          </tr>
          <tr>
            <td></td>
            <td width="30%"><hr/>From</td>
            <td><hr/>To</td>
          </tr>
          <tr>
            <td height="10"></td>
            <td></td>
            <td></td>
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
      </td>
    </TR>
    
    <tr>
      <td height="2"></td>
    </tr>
    
    
    <tr>
      <td>
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="2">CHIEF ADMINISTRATOR'S CERTIFICATION</th>
          </tr>
          <TR>
            <td colspan="2">By signing this report, I certify to the best of my knowledge and belief that the report is true, complete, and accurate, and the expenditures, disbursements, and cash receipts are for the purposes and objectives set forth in the terms and conditions of the Federal (or State) award. I am aware that any false, fictitious, or fraudulent information, or the omission of any material fact, may subject me to criminal, civil, or administrative penalties for fraud, false statements, false claims, or otherwise. (U.S. Code Title 18, Section 1001 and Title 31, Sections 3729-3730 and 3801-3812).
            </td>
          </TR>
          <tr>
            <td width="30%" height="35"></td><td width="70%" height="35"></td>
          </tr>
          <tr>
            <td width="30%">Date______________</td>
            <td width="70%">Signature_________________________</td>
          </tr>
          <tr>
            <td colspan="2" height="5"></td>
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
      </td>
      
      <td valign="top" width="50%">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
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
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td><td><hr/></td>
          </tr>
          <tr>
            <td height="5"></td>
          </tr>
          <tr>
            <td><hr/>Voucher#</td>
            <td colspan="2"><hr/>First Payment</td>
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