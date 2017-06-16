<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<pd4ml:transform screenWidth="450" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>FS-20 Form</title>
  </head>
  <body>
  
    <c:set var="fcpre" value="03"/>
    <c:set var="sdate" value="4/1/"/>
    <c:set var="edate" value="3/31/"/>
    <c:set var="endyear" value="${thisGrant.fycode}"/>
    
    
  <font size="1">
    <table align="center" width="95%" summary="for layout only">
    <tr>
      <td valign="top" width="50%"><b>The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>Barbara Massago
          <br/>NYS Library
          <br/>Division of Library Development
          <br/>222 Madison Avenue, 10B41 CEC
          <br/>Albany, NY 12230
                </b>
      </td>
      <td valign="top"><b>PROPOSED BUDGET SUMMARY FOR A FEDERAL OR STATE PROJECT
          <br/>FS-20 (12/05) 
          <br/><br/>
          Project Number: <c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
          </b>
      </td>
    </tr>
    </table>  
  </font>
  
    
    
    <font size="1">
    <table align="center" width="95%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
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
      <td>Name of Applicant:</td>
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
      <td>Project Funding Dates:</td>
      <td>Start <c:out value="${sdate}"/><fmt:formatNumber value="${thisGrant.fycode-1}" minIntegerDigits="2"/>       
      &nbsp;&nbsp;End <c:out value="${edate}"/><c:out value="${endyear}"/></td>
    </tr>
  </table>
  </font>
  
  <br/><br/>
  
  
  <font size="1">
  <table align="center" width="95%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th>INSTRUCTIONS</th>
    </tr>   
    <tr >
      <td align="left">
        Submit the original FS-20 Budget Summary, and 3 copies, all with signatures in blue ink, 
        along with the completed application directly to the appropriate State Education Department 
        office as indicated in the application instructions for the grant program for which you are 
        applying. DO NOT submit this form to the Grants Finance. <br/><br/>
        Please submit the FS-20 Budget Summary as a two page form (not back-to-back on a single sheet).
        <br/><br/>
        For changes in agency or payee address contact the State Education Department 
        office indicated on the application instructions for the grant program for which you are 
        applying.<br/><br/>
        An approved copy of the FS-20 Budget Summary will be returned to the contact 
        person noted above.  A window envelope will be used; please make sure that the contact 
        information is accurate, legible and confined to the address field.<br/><br/>
        For information on budgeting, including 2005-06 REVISED guidelines for 
        equipment and supplies, refer to the Fiscal Guidelines for Federal and State Aided Grants 
        at www.oms.nysed.gov/cafe/.<br/>
      </td>
    </tr>
  </table>
  </font>
  
 <pd4ml:page.break />
   

  <font size="1">
  <table width="100%" summary="for layout only">
    <tr>
      <td colspan="2">BUDGET SUMMARY</td>
    </tr>
    <tr><!-- top row contains top 2 boxes - categories and agency code -->
      <td valign="top" width="40%" rowspan="2">
      
        <%--this section print budgt totals for statutory--%>
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>CATEGORIES</th><TH>CODE</TH><TH>PROJECT COSTS</TH>
          </tr>
          <TR>
            <Td>Professional Salaries</Td><td align="center">15</td>
            <td>XXX</td>
          </TR>
          <TR>
            <Td>Support Staff Salaries</Td><td align="center">16</td>
            <td>XXX</td>
          </TR>
          <TR>
            <Td>Purchased Services</Td><td align="center">40</td>
            <td>XXX</td>
          </TR>
          <TR>
            <Td>Supplies and Materials</Td><td align="center">45</td>
            <td>XXX</td>
          </TR>
          <TR>
            <Td>Travel Expenses</Td><td align="center">46</td>
            <td>XXX</td>
          </TR>
          <TR>
            <Td>Employee Benefits</Td><td align="center">80</td>
            <td>XXX</td>
          </TR>
          <tr>
            <td>BOCES Services</td><td align="center">49</td>
            <td>XXX</td>
          </tr>
          <TR>
            <Td>Minor Remodeling</Td><td align="center">30</td>
            <td>XXX</td>
          </TR>
          <TR>
            <Td>Equipment</Td><td align="center">20</td>
            <td>XXX</td>
          </TR>
          <TR>
            <td>Grand Total</td><td/><td><fmt:formatNumber value="${thisGrant.fundAmount}" type="currency"/></td>
          </TR>
        </table>           
      </TD>
      
      
      <td valign="top" width="60%">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; outline-color:rgb(0,0,0); border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr><th>Agency Code</th>
              <td colspan="2"><c:out value="${thisGrant.sedcode}" /></td>
          </tr>
          <tr><th>Project #</th>
              <td colspan="2">
                  <c:out value="${fcpre}" /><fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
                  -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                  -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
              </td>
          </tr>
          <tr><th>Contract #</th>
              <td colspan="2"><c:out value="${thisGrant.contractNum}" /></td>
          </tr>
          <tr>
            <th>Agency Name</th>
            <td><c:out value="${thisGrant.instName}" /></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="60%">  
        
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="3">For Department Use Only</th>
          </tr>
          <tr>
            <td width="40%">Approved<br/>Funding Dates:</td>
            <td><c:out value="${sdate}"/>
                <fmt:formatNumber value="${thisGrant.fycode-1}" minIntegerDigits="2"/></td>
            <td><c:out value="${edate}"/><c:out value="${endyear}"/></td>
          </tr>
          <tr>
            <td></td>
            <td width="30%"><hr/>From</td>
            <td><hr/>To</td>
          </tr>
          <tr>
            <td height="15"></td>
            <td></td>
            <td></td>
          </tr>
          <tr>
            <td>Program Approval:</td>
            <td colspan="2" valign="bottom"><hr/></td>
          </tr>
          <tr>
            <td height="15"></td>
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
  </table>
  </font>
  <br/>
    
  <font size="1">
  <table width="100%"  summary="for layout only">  
    <tr>
      <td valign="top" width="40%">
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="2">CHIEF ADMINISTRATOR'S CERTIFICATION</th>
          </tr>
          <TR>
            <td colspan="2">By signing this report, I certify to the best of my knowledge and belief that the report is true, complete, and accurate, and the expenditures, disbursements, and cash receipts are for the purposes and objectives set forth in the terms and conditions of the Federal (or State) award. I am aware that any false, fictitious, or fraudulent information, or the omission of any material fact, may subject me to criminal, civil, or administrative penalties for fraud, false statements, false claims, or otherwise. (U.S. Code Title 18, Section 1001 and Title 31, Sections 3729-3730 and 3801-3812).
            </td>
          </TR>
          <tr>
            <td height="35"></td><td height="35"></td>
          </tr>
          <tr>
            <td >Date___________</td>
            <td >Signature_____________________</td>
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
      
      <td valign="top" width="60%" >
        <table width="100%" style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>Fiscal Year</th><th>First Payment</th><th>Line#</th>
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
            <td><hr/>Voucher#</td>
            <td colspan="2"><hr/>First Payment</td>
          </tr>
        </table>
      </td>
    </tr>  
  </table>
  </font>
  <br/>  
  
  <font size="1">
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td width="15%">Finance:</td>
      <td width="30%">Log:___________</td>
      <td width="30%">Approved:_________</td>
      <td>MIR:_________</td>
    </tr>
  </table>
  </font>
  
  </body>
</html>
</pd4ml:transform>