<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>printViewHTML</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>
  
  
  <h4 align="center">State Aid Applications</h4>
  
  
  <table align="center" border="1" class="graygrid" width="95%" summary="for layout only">
  <tr>
    <th colspan="2">Application for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
  </tr>
  
  <tr>
    <td width="30%"><b>Project Number</b></td>
    <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/></td>
  </tr>  
  <tr>
    <td><b>Institution ID</b></td>
    <td><c:out value="${thisGrant.instID}" /></td>
  </tr>
  <tr>
    <td><b>Institution</b></td>
    <td><c:out value="${thisGrant.instName}" /></td>
  </tr>
  <tr>
    <td><b>Address</b></td>
    <td>
      <c:out value="${thisGrant.addr1}" />
    </td>
  </tr>
  <tr>
    <td><b>City, State</b></td>
    <td><c:out value="${thisGrant.city}" /> <c:out value="${thisGrant.state}" /> 
        <c:out value="${thisGrant.zipcd1}" /> <c:out value="${thisGrant.zipcd2}" /></td>
  </tr>
  <tr>
    <td><b>Library Director</b></td>
    <td><c:out value="${libDirectorBean.salutation}" /> <c:out value="${libDirectorBean.fname}" /> <c:out value="${libDirectorBean.lname}" /></td>
  </tr>
  </table>  
  <br/><br/>
  
  
  <table align="center" width="90%" summary="for layout only">      
    <tr bgcolor="silver">
      <th colspan="5">Assurance</th>
    </tr>
        
      <tr>
        <td width="25%"><b>Assurance Date</b></td>
        <td width="25%"><b>Name</b></td>
        <td width="25%"><b>Title</b></td>
      </tr>    
      <tr>
        <td><fmt:formatDate value="${authorizationBean.authdate}" pattern="MM/dd/yyyy"/></td>
        <td><c:out value="${authorizationBean.name}"/></td>
        <td><c:out value="${authorizationBean.title}"/></td>
      </tr>
    
    
    <tr>
      <td height="30" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5">Application Submissions</th>
    </tr>
    
    
    <c:if test="${not empty allSubmissions}" >
      <tr>
        <td width="25%"><b>Date Submitted</b></td>
        <td width="25%"><b>Submitted By</b></td>
        <td width="25%"><b>Version</b></td>
      </tr>    
      <c:forEach var="submitBean" items="${allSubmissions}" >
      <tr>
        <td><fmt:formatDate value="${submitBean.dateSubmitted}" pattern="MM/dd/yyyy"/></td>
        <td><c:out value="${submitBean.userSubmitted}"/></td>
        <td><c:out value="${submitBean.versionSubmitted}"/></td>
      </tr>
      </c:forEach>
    </c:if>
    
    
    <tr>
      <td height="30" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5">Admin Application Reviews</th>
    </tr>
    
    
    <c:if test="${not empty allApprovals}" >
      <tr>
        <td><b>Date Reviewed</b></td>
        <td><b>Admin</b></td>
        <td><b>Version</b></td>
        <td><b>Result</b></td>
      </tr>   
      <c:forEach var="appBean" items="${allApprovals}">
      <tr>
        <td><fmt:formatDate value="${appBean.dateapproved}" pattern="MM/dd/yyyy"/></td>
        <td><c:out value="${appBean.adminuser}"/></td>
        <td><c:out value="${appBean.version}"/></td>
        <td><c:out value="${appBean.approvalType}" /></td>
      </tr>
      </c:forEach> 
    </c:if>
            
            
    <tr>
      <td height="30" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5">Admin Comments During Application Review/Approval</th>
    </tr>
    
    <c:if test="${not empty allComments}" >
      <tr>
        <td><b>Comment Date</b></td>
        <td><b>Admin</b></td>
        <td><b>Comment</b></td>
      </tr>
      <c:forEach var="commBean" items="${allComments}" >
      <tr >            
        <td><fmt:formatDate value="${commBean.commentdate}" pattern="MM/dd/yyyy" /></td>
        <td><c:out value="${commBean.createdby}" /></td>
        <td><c:out value="${commBean.comment}" /></td>
      </tr>  
      <tr>
        <td height="20" />
      </tr>
      </c:forEach>
    </c:if>
  </table>  
  <br/><br/>
  
  
  
  <table align="center" width="95%" summary="for layout only">
    <tr>
      <th>Project Narratives</th>
    </tr>    
    <tr>
      <th bgcolor="Silver">Description of Activities: Describe in detail the activities you plan to accomplish this fiscal year 
    using NEW YORK STATE AID.  Indicate the specific types of activities to be performed, and how they will be accomplished.
      </th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr3" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>      
    <tr>
      <th bgcolor="Silver">Budget Narrative:  Provide a brief narrative, no more than five hundred (500) words, explaining how 
           expenditures in the proposed budget application attain the institution's service goals for the 
          funding year. </th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr93" property="narrative" filter="false"/></td>
    </tr>
    <tr>
      <td height="30"></td>
    </tr>
    
    <tr>
      <th bgcolor="Silver">Final Report Narrative</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr2" property="narrative" filter="false" /></td>
    </tr>
     <tr>
      <td height="30"></td>
    </tr>
  </table>
  
  
  
  
  <table align="center" width="95%" summary="for layout only" >
  <tr>
    <th colspan="5">Project Budget for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
  </tr>  
  
  <tr bgcolor="Silver">
    <th colspan="5">Personal Service Expenses</th>
  </tr>
  <tr>
    <td colspan="5">        
      <logic:present name="budgetBean" property="allPersRecords" >
      <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <table width="100%" class="boxtype">
        <tr>
          <td width="20%"><b>Name</b></td><td width="20%"><b>Title</b></td>
          <td width="20%"><b>Salary/Wage</b></td><td width="20%"><b>FTE/Hours</b></td>
        </tr>
        <tr>
          <td><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency" /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
        </tr>
        <tr>
          <td><b>AmtRequested</b></td><td><b>AmtApproved</b></td>
          <td><b>AmtAmended</b></td>
          <td><b>ExpSubmitted</b></td><td><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        </table>
      </logic:iterate></logic:present>
    </td>
  </tr>
       
  
  <logic:notEmpty name="budgetBean" property="allPersRecords">  
    <tr>
      <th colspan="5">Personal Services Totals</th>
    </tr>
    <tr>
      <td width="20%"><b>Amt Requested</b></td>
      <td width="20%"><b>Amt Approved</b></td>
      <td width="20%"><b>Amt Amended</b></td>
      <td width="20%"><b>Exp Submitted</b></td>
      <td width="20%"><b>Exp Approved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.perAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
  </logic:notEmpty>
    
  <tr>
    <td height="40" />
  </tr>
    
    
  <tr bgcolor="Silver">
    <th colspan="5">Employee Benefit Expenses</th>
  </tr>
  <tr>
    <td colspan="5">
    
      <logic:present name="budgetBean" property="allBenRecords" >
      <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
      <table width="100%" class="boxtype">
      <tr>
        <td colspan="2"><b>Name</b></td>
        <td colspan="2"><b>Benefits percentage</b></td>
      </tr>
      <tr>
        <td colspan="2"><c:out value="${BenefitsBean.name}" /></td>
        <td colspan="2"><c:out value="${BenefitsBean.benpercent}" /></td>
      </tr>
      <tr>
        <td width="20%"><b>AmtRequested</b></td>          
        <td width="20%"><b>AmtApproved</b></td>
        <td width="20%"><b>AmtAmended</b></td>
        <td width="20%"><b>ExpSubmitted</b></td>
        <td width="20%"><b>ExpApproved</b></td>
      </tr>  
      <tr>
        <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${BenefitsBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${BenefitsBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${BenefitsBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>
      </table>
      </logic:iterate></logic:present>
    </td>
  </tr>
        
  
  <logic:notEmpty name="budgetBean" property="allBenRecords">     
    <tr>
      <th colspan="5">Employee Benefits Totals</th>
    </tr>
    <tr>
      <td><b>Amt Requested</b></td>
      <td><b>Amt Approved</b></td>
      <td><b>Amt Amended</b></td>
      <td><b>Exp Submitted</b></td>
      <td><b>Exp Approved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>        
  </logic:notEmpty>
  
  
  <tr>
    <td height="40" />
  </tr>    
    
  <tr bgcolor="Silver">
    <th colspan="5">Contracted Services Expenses</th>
  </tr>
    <tr>
      <td colspan="5">
      
      <logic:present name="budgetBean" property="allContractRecords" >
      <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" >
        <table width="100%" class="boxtype">
        <tr>
          <td><b>Service Type</b></td><td><b>Recipient</b></td>
          <td colspan="2"><b>Description</b></td>
        </tr>
        <tr>
          <td><c:out value="${ContractedBean.servicetype}" /></td>
          <td><c:out value="${ContractedBean.recipient}" /></td>
          <td colspan="2"><c:out value="${ContractedBean.servicedescr}" /></td>
        </tr>
        <tr>
          <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
          <td width="20%"><b>AmtAmended</b></td>
          <td width="20%"><b>ExpSubmitted</b></td><td width="20%"><b>ExpApproved</b></td>
        </tr>
        <tr>          
          <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        </table>
       </logic:iterate></logic:present>
      </td>
    </tr>        
     
     
    <logic:notEmpty name="budgetBean" property="allContractRecords">  
      <tr>
        <th colspan="5">Contracted Services Totals</th>
      </tr>
      <tr>
        <td><b>Amt Requested</b></td>
        <td><b>Amt Approved</b></td>
        <td><b>Amt Amended</b></td>
        <td><b>Exp Submitted</b></td>
        <td><b>Exp Approved</b></td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency" maxFractionDigits="0" /></td>
       <td><fmt:formatNumber value="${totalsBean.conAmtAmend}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.conExpAppr}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </logic:notEmpty>
    
        
    <tr>
      <td height="40" />
    </tr>    
    
    <tr bgcolor="Silver">
      <th colspan="5">Supplies, Materials and Equipment Expenses</th>
    </tr>
    <tr>
      <td colspan="5">
                   
      <logic:present name="budgetBean" property="allSupplyRecords" >
      <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" >
        <table width="100%" class="boxtype">
         <tr>
          <td><b>Quantity</b></td><td><b>Description</b></td>
          <td><b>Unit Price</b></td><td><b>Vendor</b></td>
        </tr>
        <tr>
          <td><c:out value="${SupplyBean.quantity}" /></td>
          <td><c:out value="${SupplyBean.description}" /></td>
          <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" maxFractionDigits="0" /></td>
          <td><c:out value="${SupplyBean.vendor}" /></td>
        </tr>
        <tr>
          <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
          <td width="20%"><b>AmtAmended</b></td>
          <td width="20%"><b>ExpSubmitted</b></td><td width="20%"><b>ExpApproved</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        </table>
        </logic:iterate></logic:present>  
      </td>
    </tr>
       
    <logic:notEmpty name="budgetBean" property="allSupplyRecords">    
      <tr>
        <th colspan="5">Supplies, Materials, Equipment Totals</th>
      </tr>
      <tr>
        <td><b>Amt Requested</b></td>
        <td><b>Amt Approved</b></td>
        <td><b>Amt Amended</b></td>
        <td><b>Exp Submitted</b></td>
        <td><b>Exp Approved</b></td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${totalsBean.suppAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.suppAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.suppAmtAmend}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.suppExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.suppExpAppr}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </logic:notEmpty> 
    
        
    
    <tr>
      <td height="40" />
    </tr>    
    <tr bgcolor="Silver">
      <th colspan="5">Travel Expenses</th>
    </tr>
    
    <tr>
      <td colspan="5">          
      <logic:present name="budgetBean" property="allTravelRecords" >
      <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
        <table width="100%" class="boxtype">
        <tr>
          <td colspan="2"><b>Description</b></td>
          <td colspan="3"><b>Purpose</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${TravelBean.description}" /></td>
          <td colspan="3"><c:out value="${TravelBean.purpose}" /></td>
        </tr>
        <tr>
          <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
          <td width="20%"><b>Amtmended</b></td>
          <td width="20%"><b>ExpSubmitted</b></td><td width="20%"><b>ExpApproved</b></td>
        </tr>
         <tr>
          <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        </table>
        </logic:iterate></logic:present>
      </td>
    </tr>
       
   <logic:notEmpty name="budgetBean" property="allTravelRecords">    
      <tr>
        <th colspan="5">Travel Expenses Totals</th>
      </tr>
      <tr>
        <td><b>Amt Requested</b></td>
        <td><b>Amt Approved</b></td>
        <td><b>Amt Amended</b></td>
        <td><b>Exp Submitted</b></td>
        <td><b>Exp Approved</b></td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.travAmtAmend}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </logic:notEmpty>
    
    
          
      
    <tr>
      <td height="40" />
    </tr>         
    <tr bgcolor="Silver"><th colspan="5">Grand Totals</th></tr>
    <tr>
      <td width="20%"><b>AmtRequested</b></td>
      <td width="20%"><b>AmtApproved</b></td>
      <td width="20%"><b>AmtAmended</b></td>
      <td width="20%"><b>ExpSubmitted</b></td>
      <td width="20%"><b>ExpApproved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
        
  </table>
  
  
  
  </body>
</html>