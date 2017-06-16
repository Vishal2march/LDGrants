<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saPrintViewPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak    6/11/07 Created   2/15/08 modified to show authorizations
 *
 * Description
 * This page allows the applicant to view a PDF version of the
 * complete grant application info that was entered by the applicant. It
 * currently uses pd4ml tags to convert jsp page to pdf.  Works in FF but
 * sometimes gives blank page in IE (?).  Tried to fix it by adding a servlet
 * mapping to web.xml to map a .pdf url to this jsp file. 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="800" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
     <title>NYS Library State Aid Application</title>
  </head>
  <body>
  
  <font size="3">
  <p align="center"><b>Conservation Preservation Program Grant Application
          <br/>The University of the State of New York 
          <br/>The State Education Department 
          <br/>Division of Library Development
          <br/>Statutory Aid Grant
  </b></p>
  </font>
  
  
  <font size="1">
  <table align="center" width="95%" border="1" summary="for layout only">
  <tr>
    <th colspan="2">Grant Application for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
  </tr>
  
  <tr>
    <td><b>Project Number</b></td>
    <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}"  pattern="####"/></td>
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
      <c:out value="${thisGrant.addr1}" /> <br/><c:out value="${thisGrant.addr2}" />
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
   <tr>
    <td><b>Preservation Administrative Officer</b></td>
    <td>
      <c:out value="${presOfficerBean.salutation}" /> <c:out value="${presOfficerBean.fname}" /> <c:out value="${presOfficerBean.lname}" />
    </td>
  </tr>
  </table>
  </font>  
  <br/><br/>
  
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only"> 
    <tr bgcolor="Silver">
      <th colspan="5">Authorizations</th>
    </tr>
    
    <c:if test="${not empty grantAuth}">
      <tr>
        <td><b>Version</b></td>
        <td><b>Date Authorized</b></td>
        <td><b>Title</b></td>
        <td><b>Name</b></td>
        <td><b>User Authorized</b></td>      
      </tr>
        
      <c:forEach var="authBean" items="${grantAuth}" >
        <tr>
          <td><c:out value="${authBean.version}" /></td>
          <td ><fmt:formatDate value="${authBean.authdate}" pattern="MM/dd/yyyy" /></td>
          <td><c:out value="${authBean.title}" /></td>
          <td><c:out value="${authBean.name}" /></td>
          <td><c:out value="${authBean.user}" /></td>
        </tr>    
      </c:forEach>
    </c:if>
    
    <tr>
      <td height="20" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5">Application Submissions</th>
    </tr>
    
    <c:if test="${not empty allSubmissions}">
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
      <td height="20" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5">Admin Application Reviews</th>
    </tr>
    
    <c:if test="${not empty allApprovals}">
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
      <td height="20" />
    </tr>
    <tr bgcolor="silver">
      <th colspan="5">Admin Comments During Application Review/Approval</th>
    </tr>
    
    <c:if test="${not empty allComments}">
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
  </font>  
  <br/><br/>
  
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only">
    <tr >
      <th>Project Description Narratives</th>
    </tr>    
    <tr>
      <th bgcolor="Silver">Summary Description of proposed preservation activities</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr1" property="narrative" filter="false" /></td>
    </tr>     
    <tr>
      <th bgcolor="Silver">A. Describe in detail the activities you plan
          to accomplish this fiscal year using NEW YORK STATE AID for conservation/preservation.  Indicate
          the specific types of activities to be performed (see "Fundable Activities" section of guidelines),
          and how they will be accomplished.
      </th>
    </tr>
    <tr>  
      <td align="left"><bean:write name="projNarr3" property="narrative" filter="false"/></td>
    </tr>    
    <tr>
      <th bgcolor="Silver">B. Describe how these activities relate
              to those planned for the institution's overall preservation program for this fiscal year.
      </th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr4" property="narrative" filter="false"/></td>
    </tr>    
    
    <c:if test="${thisGrant.fycode<16}"><%--not needed starting FY2015-16--%>
        <tr>
          <th bgcolor="Silver">C. Relate the activities to be performed with State Aid to your five-year plan.</th>
        </tr>
        <tr>
          <td align="left"><bean:write name="projNarr5" property="narrative" filter="false"/></td>
        </tr>
    </c:if>
    
    <tr>
      <th bgcolor="Silver">Final Narrative</th>
    </tr>
    <tr>
      <td align="left"><bean:write name="projNarr2" property="narrative" filter="false" /></td>
    </tr>
  </table>
  </font>
  
  <pd4ml:page.break />  
  
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only" >
  <tr>
    <th colspan="5">Project Budget for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
  </tr>
   
  <tr bgcolor="Silver">
    <th colspan="5">Personal Service Expenses</th>
  </tr>
            
  <logic:present name="budgetBean" property="allPersRecords" >
  <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
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
    <tr>
      <td height="10" />
    </tr>  
    </logic:iterate></logic:present>
        
        
    <logic:notEmpty name="budgetBean" property="allPersRecords">   
      <tr>
        <td colspan="5"><hr/></td>
      </tr>
      <tr>
        <th colspan="5">Personal Services Totals</th>
      </tr>
      <tr>
        <td><b>Amt Requested</b></td>
        <td><b>Amt Approved</b></td>
        <td><b>Amt Amended</b></td>
        <td><b>Exp Submitted</b></td>
        <td><b>Exp Approved</b></td>
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
      <td height="20" />
    </tr>
    
    <tr bgcolor="Silver">
      <th colspan="5">Employee Benefit Expenses</th>
    </tr>
                       
    <logic:present name="budgetBean" property="allBenRecords" >
    <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
    <tr>
      <td colspan="2"><b>Name</b></td>
      <td colspan="3"><b>Benefits percentage</b></td>
    </tr>
    <tr>
      <td colspan="2"><c:out value="${BenefitsBean.name}" /></td>
      <td colspan="3"><c:out value="${BenefitsBean.benpercent}" /></td>
    </tr>
    <tr>
      <td width="20%"><b>AmtRequested</b></td>  
      <td width="20%"><b>AmtAmended</b></td>
      <td width="20%"><b>AmtApproved</b></td>
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
    <tr>
      <td height="10" />
    </tr>
    </logic:iterate></logic:present>
    
        
   <logic:notEmpty name="budgetBean" property="allBenRecords">
      <tr>
        <td colspan="5"><hr/></td>
      </tr>
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
        <td><fmt:formatNumber  value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.benAmtAmend}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.benExpAppr}" type="currency" maxFractionDigits="0" /></td>
      </tr>        
    </logic:notEmpty>
  
    <tr>
      <td height="20" />
    </tr>    
    <tr bgcolor="Silver">
      <th colspan="5">Contracted Services Expenses</th>
    </tr>
                        
    <logic:present name="budgetBean" property="allContractRecords" >
    <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" >
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
    <tr>
      <td height="10" />
    </tr>
    </logic:iterate></logic:present>
     
    <logic:notEmpty name="budgetBean" property="allContractRecords">   
      <tr>
        <td colspan="5"><hr/></td>
      </tr>
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
      <td height="20" />
    </tr>
    <tr bgcolor="Silver">
      <th colspan="5">Supplies, Materials and Equipment Expenses</th>
    </tr>
                       
    <logic:present name="budgetBean" property="allSupplyRecords" >
    <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" >
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
    <tr>
      <td height="10" />
    </tr>
    </logic:iterate></logic:present>    
    
    <logic:notEmpty name="budgetBean" property="allSupplyRecords">   
      <tr>
        <td colspan="5"><hr/></td>
      </tr>
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
      <td height="20" />
    </tr>
    
    <tr bgcolor="Silver">
      <th colspan="5">Other Expenses</th>
    </tr>       
               
      <logic:present name="budgetBean" property="allExpRecords" >
      <logic:iterate name="budgetBean" property="allExpRecords" id="OtherExpBean" >
        <tr>
          <td colspan="2"><b>Description</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${OtherExpBean.description}" /></td>
        </tr>
        <tr>
          <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
          <td width="20%"><b>AmtAmended</b></td>
          <td width="20%"><b>ExpSubmitted</b></td><td width="20%"><b>ExpApproved</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        <tr>
          <td height="10" />
        </tr>
        </logic:iterate></logic:present>
       
   <logic:notEmpty name="budgetBean" property="allExpRecords" >
     <tr>
      <td colspan="5"><hr/></td>
    </tr>
    <tr>
      <th colspan="5">Other Expenses Totals</th>
    </tr>
    <tr>
      <td><b>Amt Requested</b></td>
      <td><b>Amt Approved</b></td>
      <td><b>Amt Amended</b></td>
      <td><b>Exp Submitted</b></td>
      <td><b>Exp Approved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.othAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.othExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
  </logic:notEmpty>
      
    
    
    <tr>
      <td height="20" />
    </tr>    
    <tr bgcolor="Silver">
      <th colspan="5">Travel Expenses</th>
    </tr>       
               
    <logic:present name="budgetBean" property="allTravelRecords" >
    <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
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
      <td width="20%"><b>AmtAmended</b></td>
      <td width="20%"><b>ExpSubmitted</b></td><td width="20%"><b>ExpApproved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${TravelBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    </logic:iterate></logic:present>
    
    <logic:notEmpty name="budgetBean" property="allTravelRecords">   
       <tr>
        <td colspan="5"><hr/></td>
      </tr>
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
      <td height="20" />
    </tr>    
    <tr bgcolor="Silver">
      <th colspan="5" >FS10A Budget Amendment</th>
    </tr>
    
    <c:if test="${not empty allFSRecords}">
      <tr>
        <td width="20%"><b>Budget Category</b></td><td width="20%"><b>Description</b></td>
        <td width="20%"><b>Amt Increase</b></td>
        <td width="20%"><b>Amt Decrease</b></td>
      </tr>
      <c:set var="totincr" value="0" />
      <c:set var="totdecr" value="0" />
      <c:forEach var="FS10Bean" items="${allFSRecords}" >
      <tr>
        <td ><c:out value="${FS10Bean.expname}" /></td>
        <td ><c:out value="${FS10Bean.description}" /></td>
        <td><fmt:formatNumber value="${FS10Bean.amountincr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${FS10Bean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
      </tr>
      <c:set var="totincr" value="${totincr + FS10Bean.amountincr}" />
      <c:set var="totdecr" value="${totdecr + FS10Bean.amountdecr}" />
      </c:forEach>
     
      <tr>
        <td></td>
        <td></td>
        <td><fmt:formatNumber value="${totincr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totdecr}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </c:if>
     
    <tr>
      <td height="20" />
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
  </font>  
  
  </body>
</html>
</pd4ml:transform>