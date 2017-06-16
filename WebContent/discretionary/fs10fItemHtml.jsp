<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>FS-10-F Form</title>
  </head>
  <body>
  
   <table align="center" width="95%" summary="for layout only">
    <tr>
      <td valign="top"><b>MAIL TO: Conservation Preservation Program 
          <br/>The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>Division of Library Development			                 
          <br/>Room 10-B-41 Cultural Education Center
          <br/>Albany, NY  12230</b>
      </td>
      <td valign="top"><b>FINAL EXPENDITURE FOR A
          <br/>FEDERAL OR STATE PROJECT
          <br/>FS-10-F Short Form (08/06) 
          <br/><br/>
          Project Number: 03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
                          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                          -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/>
          </b>
      </td>
    </tr>
  </table>  
  <br/>
  
  
  <table align="center" width="96%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th colspan="2">Local Agency Information</th>
    </tr>        
    <tr>
      <td>Funding Source:</td>
      <td><c:out value="${fundsource}" /></td>
    </tr>
    <tr>
      <td width="30%">Report Prepared By:</td>
      <td ><c:out value="${projManager.fname}" /> <c:out value="${projManager.mname}" /> 
          <c:out value="${projManager.lname}" /></td>
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
      <td colspan="2">E-Mail Address: </td>
    </tr>    
  </table>  
  <br/><br/>
  
  
  
  <table align="center" width="96%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th>INSTRUCTIONS</th>
    </tr>   
    <tr>
      <td>
        <p>Upon audit, you may be requested to provide additional detail to support the reported
        expenditures or to complete the Full FS-10-F form.</p>
        <p>Submit one signed original and one copy of the FS-10-F Short Form as a two page 
        form (not back-to-back on a single sheet) directly to Library Development (address above). </p>
        <p>For State projects the final expenditure reports are due within 30 days after 
        the project end dates.  Reports for Federal projects are due within 90 days after the project 
        end dates, although for certain programs, the State Education Department program manager may 
        impose earlier due dates. See the Grant Award Notice for your project to verify the due date.</p>
        <p>Category subtotals must be reported in whole dollar amounts.</p>
        <p>To be in compliance with applicable audit requirements, complete and accurate 
        records must be maintained at the local level.</p>
        <p>All encumbrances must have taken place within the approved funding dates of the project.</p>
        <p>Certification must be signed by Chief Administrative Officer or designee.</p>
        <p>High quality computer generated reproductions of this form may be used.</p>
        <p>Beginning with the 2005-06 year, there are changes to the reporting requirements 
        for Supplies and Materials (Code 45) and Equipment (Code 20).  For further information on these 
        changes and completing the final expenditure report, please refer to the Fiscal Guidelines for 
        Federal and State Aided Grants at www.oms.nysed.gov/cafe/ or call Grants Finance at (518) 474-4815.</p>
        <p>For Special Legislative Projects only, submit one signed original report and 
        three copies.  A final narrative report must be submitted with this report.</p>
      </td>
    </tr>
  </table>
  
    
  
  <br/><br/><br/><br/>
  
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
            <td align="right"><fmt:formatNumber value="${fsBean.professional}" type="currency"/> </td>
          </TR>
          <TR>
            <Td>Support Staff Salaries</Td><td align="center">16</td>
            <td align="right"><fmt:formatNumber value="${fsBean.supportstaff}" type="currency" maxFractionDigits="0"/></td>
          </TR>
          <TR>
            <Td>Purchased Services</Td><td align="center">40</td>
            <td align="right"><fmt:formatNumber value="${fsBean.purchasedserv}" type="currency" maxFractionDigits="0"/></td>
          </TR>
          <TR>
            <Td>Supplies and Materials</Td><td align="center">45</td>
            <td align="right"><fmt:formatNumber value="${fsBean.supplies}" type="currency" maxFractionDigits="0"/></td>
          </TR>
          <TR>
            <Td>Travel Expenses</Td><td align="center">46</td>
            <td align="right"><fmt:formatNumber value="${fsBean.travel}" type="currency" maxFractionDigits="0"/></td>
          </TR>
          <TR>
            <Td>Employee Benefits</Td><td align="center">80</td>
            <td align="right"><fmt:formatNumber value="${fsBean.employeebenefits}" type="currency" maxFractionDigits="0"/></td>
          </TR>
          <TR>
            <Td>Minor Remodeling</Td><td align="center">30</td>
            <td align="right"><fmt:formatNumber value="${fsBean.remodeling}" type="currency" maxFractionDigits="0"/></td>
          </TR>
          <TR>
            <Td>Equipment</Td><td align="center">20</td>
            <td align="right"><fmt:formatNumber value="${fsBean.equipment}" type="currency" maxFractionDigits="0"/></td>
          </TR>
          <TR>
            <td>Grand Total</td><td align="right" colspan="2">
                <fmt:formatNumber value="${fsBean.totalamt}" type="currency" maxFractionDigits="0"/></td>
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
              <td colspan="2"><c:out value="${thisGrant.instID}" /></td>
          </tr>
          <tr><td>Project #</td>
              <td colspan="2">
                  03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
                  -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                  -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
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
            <td>7/1/<c:out value="${thisGrant.fiscalyear-1}"/><hr/></td>
            <td>6/30/<c:out value="${thisGrant.fiscalyear}"/><hr/></td>
          </tr>
          <tr>
            <td></td>
            <td width="30%">From</td>
            <td>To</td>
          </tr>
          <tr>
            <td>Approved Budget Total</td>
            <td colspan="2">$_______________________</td>
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
            <td colspan="2">I hereby certify that the reported expenditures have been made in accordance 
            with the provisions of applicable statute, regulation and approved project and budget; that 
            the claim is just and correct; that no part has been paid except as stated; that the balance 
            is actually due and owing; and that proper fund accounting is followed, records are retained
            for the proper period, and that records will be made available to representatives of the 
            Education Department or the Office of the State Comptroller when requested.
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
            <th colspan="2">FOR DEPARTMENT USE ONLY</th>
          </tr>
          <tr>
            <th>Fiscal Year</th><th>Final Payment</th>
          </tr>
          <tr>
            <td height="15"></td>
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
            <td height="20"></td>
          </tr>
          <tr>
            <td align="center"><hr/>Voucher#</td>
            <td align="center"><hr/>First Payment</td>
          </tr>
          <tr>
            <td colspan="2">
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
  
  <br/><br/>
  
  
  
  
  
  </body>
</html>
