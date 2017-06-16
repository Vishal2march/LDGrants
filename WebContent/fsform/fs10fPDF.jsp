<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  fs10fPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       8/31/07     Created
 *
 * Description
 * This page is a pdf read only view of the FS10F form. 
 * used for lgrmif/al/fl/di
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<pd4ml:transform screenWidth="800" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>FS-10-F form</title>
  </head>
  <body>   
  
  <c:choose>
 <c:when test="${thisGrant.fccode==80}">
    <c:set var="fcpre" value="05"/>
 </c:when>
 <c:when test="${thisGrant.fccode==6 || thisGrant.fccode==5 || thisGrant.fccode==40 || thisGrant.fccode==42}">
    <c:set var="fcpre" value="03"/>
 </c:when>
 </c:choose>
 
  <font size="1">
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td valign="top">The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>(see instructions for mailing address)
      </td>
      <td valign="top">FINAL EXPENDITURE REPORT FOR A
          <br/>FEDERAL OR STATE PROJECT
          <br/>FS-10-F Short Form (06/08) 
          <br/>
          Project Number: <c:out value="${fcpre}"/><fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
                          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                          -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
  </table>  
  </font>
  <br/>
  
  <font size="1">
  <table align="center" width="95%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th colspan="2">Local Agency Information</th>
    </tr>        
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
      <td>E-Mail Address:</td>
      <td><c:out value="${presOfficerBean.email}" /></td>
    </tr>    
  </table>  
  </font>   
  
  <font size="1">
  <table align="center" width="95%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th>INSTRUCTIONS</th>
    </tr>   
    <tr>
      <td>     
        Agencies should use the FS-10-F Short Form unless directed otherwise in the grant 
        application/RFP or by Department staff. It must be submitted in accordance with 
        report due dates; only the FS-10-F Long Form will be accepted after the due 
        dates.<br/><br/>
        For State projects, final expenditure reports are due within 30 days after the 
        project end date. Reports for Federal projects are due within 90 days after 
        the project end date. For certain programs, the Department program manager 
        may impose earlier due dates. See the Grant Award Notice for your project to 
        verify the due date.<br/>
        Agencies must record grant expenditure details in a manner consistent with the 
        internal pages of the FS-10-F Long Form and must maintain this information in 
        their files. These details must be readily available upon request from authorized 
        individuals, which include State, federal and local auditors and staff from 
        the Department, the Office of the State Comptroller and federal agencies.
        <br/><br/>
        Submit three signed original sets of the FS-10-F Short Form as a two page 
        form (not back-to-back on a single sheet) directly to the appropriate State 
        Education Department office as indicated in the application instructions for the grant 
        program for which you are applying. <br/>              
        For Special Legislative Projects, submit one report with original signature
        and two copies, along with a final program narrative report.<br/>
        Use whole dollar amounts.<br/>       
        All encumbrances must have taken place within the approved funding dates of the project.
        <br/>
        Certification must be signed by Chief Administrative Officer or designee.<br/>
        High quality computer generated reproductions of this form may be used.<br/>
        For further information about completing the final expenditure report, please 
        refer to the Federal and State Aided Grants at www.oms.nysed.gov/cafe/ or 
        contact Grants Finance at grantsweb@nysed.gov or (518) 474-4815.        
      </td>
    </tr>
  </table>
  </font>
    
   <pd4ml:page.break />  
  
  <font size="1">
  <table width="100%" summary="for layout only">
    <tr>
      <td colspan="2">FINAL EXPENDITURE SUMMARY</td>
    </tr>
    <tr>
      <td valign="top" width="50%">
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>SUBTOTAL</th><TH>CODE</TH><TH>PROJECT COSTS</TH>
          </tr>
          <TR>
            <Td>Professional Salaries</Td><td align="center">15</td>
            <td><fmt:formatNumber value="${totalsBean.proffExpSub}" type="currency"/> </td>
          </TR>
          <TR>
            <Td>Support Staff Salaries</Td><td align="center">16</td>
            <td><fmt:formatNumber value="${totalsBean.profsuppExpSub}" type="currency"/> </td>
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
            <td><fmt:formatNumber value="${totalsBean.equipExpSub}" type="currency"/></td>
          </TR>
          <TR>
            <td>Grand Total</td><td></td><td>
            <fmt:formatNumber value="${totalsBean.totExpSub}" type="currency"/></td>
          </TR>
        </table>       
      </TD>
      
      <td valign="top" width="50%">
        <table style="outline-style:solid; outline-width:1.0px; outline-color:rgb(0,0,0); border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
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
            <td><c:out value="${sdate}"/><fmt:formatNumber value="${thisGrant.fycode-1}" minIntegerDigits="2"/></td>
            <td><c:out value="${edate}"/><fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2"/></td>
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
  </table></font><br/>
    
  
  <font size="1">
  <table width="100%" >
    <tr>
      <td valign="top" width="50%">
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
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
            <td width="30%">Date_______________</td>
            <td width="70%">Signature__________________________</td>
          </tr>       
          <tr>
            <td height="30" colspan="2" align="center">
              <c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.mname}" /> 
              <c:out value="${libDirectorBean.lname}" />, <c:out value="${libDirectorBean.title}" /></td>
          </tr>
          <tr>
            <th colspan="2">Name and Title of Chief Administrative Officer</th>
          </tr>
        </table>
      </td>
      
      
      <td valign="top" width="50%" >
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th colspan="2">FOR DEPARTMENT USE ONLY</th>
          </tr>
          <tr>
            <th>Fiscal Year</th><th>Final Payment</th>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td>
          </tr>
          <tr valign="bottom">
            <td height="25"><hr/></td><td><hr/></td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
          <tr>
            <td align="center"><hr/>Voucher#</td>
            <td align="center"><hr/>First Payment</td>
          </tr>
          <tr>
            <td colspan="2">
              <table summary="for layout only">
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
  </font>
  
  </body>
</html>
</pd4ml:transform>