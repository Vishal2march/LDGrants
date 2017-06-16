<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="600" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>FS-20</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only">
    <tr>
      <td valign="top">MAIL TO: Conservation Preservation Program 
          <br/>The University of the State of New York		
          <br/>THE STATE EDUCATION DEPARTMENT			
          <br/>Division of Library Development			                 
          <br/>Room 10-B-41 Cultural Education Center
          <br/>Albany, NY  12230
       </td>
      <td valign="top">PROPOSED BUDGET SUMMARY FOR A
          <br/>FEDERAL OR STATE PROJECT
          <br/>FS-20 (02/07) 
          <br/><br/>
          Project Number: 03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
                          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                          -<fmt:formatNumber  value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/>
       </td>
    </tr>
  </table>
  </font>
  <br/><br/>
  

  <font size="1">
  <table align="center" width="95%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th colspan="2">Conservation/Preservation Grant Applicant Information</th>
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
      <td>Name of Applicant:</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td valign="top">Mailing Address:</td>
      <td>        
        <c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.addr2}" />
      </td>
    </tr>
    <tr>
      <td valign="top">City, State:</td>
      <td><c:out value="${thisGrant.city}" /> <c:out value="${thisGrant.state}" />
      <c:out value="${thisGrant.zipcd1}" /> <c:out value="${thisGrant.zipcd2}" /></td>
    </tr>      
    <tr>
      <td>Telephone#:</td>
      <td>County: <c:out value="${thisGrant.county}" /></td>
    </tr>
    <tr>
      <td colspan="2">E-Mail Address: </td>
    </tr>    
    <tr> 
     <td>Project Funding Dates:</td>
     <td>Start 7/1/<c:out value="${thisGrant.fiscalyear-1}"/>     
               &nbsp;&nbsp;End 6/30/<c:out value="${thisGrant.fiscalyear}"/></td>
    </tr>
  </table></font>
  <br/><br/>
  
  
  <font size="1">
  <table align="center" width="95%" style="outline-style:solid; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only" >
    <tr>
      <th>INSTRUCTIONS</th>
    </tr>   
    <tr >
      <td align="left">
        Submit the original FS-20 Budget Summary with signature in blue ink and the required number of copies 
        along with the completed application directly to the appropriate State Education Department 
        office as indicated in the application instructions for the grant program for which you are 
        applying. DO NOT submit this form to the Grants Finance. <br/><br/>
        Please submit the FS-20 Budget Summary as a two page form (not back-to-back on a single sheet).<br/>
        Enter whole dollar amounts only.  The amounts must agree with the budget 
        category totals from each Budget Category and Narrative Form.<br/><br/>
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
        <table style="outline-style:solid; outline-width:1.0px; border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr>
            <th>CATEGORIES</th><TH>CODE</TH><TH >PROJECT COSTS</TH>
          </tr>
           <TR>
            <Td>Professional Salaries</Td><td align="center">15</td>
            <td align="right"><fmt:formatNumber value="${fsBean.professional}" type="currency" maxFractionDigits="0"/> </td>
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
      </TD>
      
      
      <td valign="top" width="60%">
        <table width="100%" style="outline-style:solid; outline-width:1.0px; outline-color:rgb(0,0,0); border-color:rgb(0,0,0); border-style:solid; border-width:1.0px;" summary="for layout only">
          <tr><th>Agency Code</th>
              <td colspan="2"><c:out value="${thisGrant.instID}" /></td>
          </tr>
          <tr><th>Project #</th>
              <td colspan="2">
                  03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
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
            <td>7/1/<c:out value="${thisGrant.fiscalyear-1}"/><hr/></td>
            <td>6/30/<c:out value="${thisGrant.fiscalyear}"/><hr/></td>
          </tr>
          <tr>
            <td></td>
            <td width="30%">From</td>
            <td>To</td>
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
            <td colspan="2">I hereby certify that the requested budget amounts are necessary for the implementation
            of this project and that this agency is in compliance with applicable Federal and State 
            laws and regulations.
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