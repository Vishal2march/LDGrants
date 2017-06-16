<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saViewBudget.jsp
 * Creation/Modification History  :
 *
 *     SHusak       3/1/07     Created
 *
 * Description
 * This page allows the applicant to view a read only version of the
 * prior fiscal year budget information.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>View Prior State Aid Budget</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>    
  
  <h4>Project Budget</h4>
      
  
  <table width="95%" align="center" summary="for layout only">
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
          </table><br/>
        </logic:iterate></logic:present>
      </td>
    </tr>       
    
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
  </table>
  <br/><br/>
       
    
    
  <table width="95%" align="center" summary="for layout only">
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
       </table><br/>
       </logic:iterate></logic:present>
      </td>
    </tr>
            
    <tr>
      <th colspan="5">Employee Benefits Totals</th>
    </tr>
    <tr>
      <td width="20%"><b>Amt Requested</b></td>
      <td width="20%"><b>Amt Approved</b></td>
      <td width="20%"><b>Amt Amended</b></td>
      <td width="20%"><b>Exp Submitted</b></td>
      <td width="20%"><b>Exp Approved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>        
  </table>
  <br/><br/>
  
    
  <table width="95%" align="center" summary="for layout only" >
    <tr bgcolor="Silver">
      <th colspan="5">Contracted Services Expenses</th>
    </tr>
    <tr>
      <td colspan="5">
                    
      <logic:present name="budgetBean" property="allContractRecords" >
      <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" >
        <table width="100%" class="boxtype">
        <tr>
          <td colspan="2"><b>Service Type</b></td><td><b>Recipient</b></td>
          <td><b>Description</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${ContractedBean.servicetype}" /></td>
          <td><c:out value="${ContractedBean.recipient}" /></td>
          <td><c:out value="${ContractedBean.servicedescr}" /></td>
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
        </table><br/>
        </logic:iterate></logic:present>
      </td>
    </tr>        
   
    <tr>
      <th colspan="5">Contracted Services Totals</th>
    </tr>
    <tr>
      <td width="20%"><b>Amt Requested</b></td>
      <td width="20%"><b>Amt Approved</b></td>
      <td width="20%"><b>Amt Amended</b></td>
      <td width="20%"><b>Exp Submitted</b></td>
      <td width="20%"><b>Exp Approved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.conAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.conExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
  </table>
  <br/><br/>
    
    
    
 
  <table width="95%" align="center" summary="for layout only">
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
        </table><br/>
        </logic:iterate></logic:present>    
      </td>
    </tr>       
    
    <tr>
      <th colspan="5">Supplies, Materials, Equipment Totals</th>
    </tr>
    <tr>
      <td width="20%"><b>Amt Requested</b></td>
      <td width="20%"><b>Amt Approved</b></td>
      <td width="20%"><b>Amt Amended</b></td>
      <td width="20%"><b>Exp Submitted</b></td>
      <td width="20%"><b>Exp Approved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.suppAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.suppAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.suppAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.suppExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.suppExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
  </table>
  <br/><br/>
    
    
    
  <table width="95%" align="center" summary="for layout only">
    <tr bgcolor="Silver">
      <th colspan="5" >Other Expenses</th>
    </tr>
    <tr>
      <td colspan="5">                       
      <logic:present name="budgetBean" property="allExpRecords" >
      <logic:iterate name="budgetBean" property="allExpRecords" id="OtherExpBean" >
        <table width="100%" class="boxtype">
        <tr>
          <td colspan="2"><b>Description</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${OtherExpBean.description}" /></td>
        </tr>
        <tr>
          <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
          <td width="20%"><b>ExpSubmitted</b></td><td width="20%"><b>ExpApproved</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        </table>
        </logic:iterate></logic:present>
      </td>
    </tr>               
    <tr>
      <th colspan="5">Other Expenses Totals</th>
    </tr>
    <tr>
      <td width="20%"><b>Amt Requested</b></td>
      <td width="20%"><b>Amt Approved</b></td>
      <td width="20%"><b>Amt Amended</b></td>
      <td width="20%"><b>Exp Submitted</b></td>
      <td width="20%"><b>Exp Approved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.othAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.othExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
  </table>      
  <br/><br/>
  
  
  <table width="95%" align="center" summary="for layout only">
    <tr bgcolor="Silver">
      <th colspan="5" >Travel Expenses</th>
    </tr>
    <tr>
      <td colspan="5">                       
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
        <table width="100%" class="boxtype">
        <tr>
          <td colspan="2"><b>Description</b></td>
          <td colspan="2"><b>Purpose</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${TravelBean.description}" /></td>
          <td colspan="2"><c:out value="${TravelBean.purpose}" /></td>
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
        </table>
        </logic:iterate></logic:present>
      </td>
    </tr>               
    <tr>
      <th colspan="5">Travel Expenses Totals</th>
    </tr>
    <tr>
      <td width="20%"><b>Amt Requested</b></td>
      <td width="20%"><b>Amt Approved</b></td>
      <td width="20%"><b>Amt Amended</b></td>
      <td width="20%"><b>Exp Submitted</b></td>
      <td width="20%"><b>Exp Approved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.travAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
  </table>      
  <br/><br/>
  
    
  <table width="95%" align="center" summary="for layout only">
    <tr bgcolor="Silver">
      <th colspan="4" >FS10A Budget Amendment</th>
    </tr>
    <tr>
      <td><b>Budget Category</b></td><td><b>Description</b></td>
      <td><b>Amt Increase</b></td>
      <td><b>Amt Decrease</b></td>
    </tr>
    <c:set var="totincr" value="0" />
    <c:set var="totdecr" value="0" />
    <c:forEach var="FS10Bean" items="${allFSRecords}" >
    <tr>
      <td ><c:out value="${FS10Bean.expname}" /></td>
      <td ><c:out value="${FS10Bean.description}" /></td>
      <td width="25%"><fmt:formatNumber value="${FS10Bean.amountincr}" type="currency" maxFractionDigits="0" /></td>
      <td width="25%"><fmt:formatNumber value="${FS10Bean.amountdecr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    <c:set var="totincr" value="${totincr + FS10Bean.amountincr}" />
    <c:set var="totdecr" value="${totdecr + FS10Bean.amountdecr}" />
    </c:forEach>
   
    <tr>
      <td></td>
      <td></td>
      <td width="25%"><fmt:formatNumber value="${totincr}" type="currency" maxFractionDigits="0" /></td>
      <td width="25%"><fmt:formatNumber value="${totdecr}" type="currency" maxFractionDigits="0" /></td>
    </tr>
  </table>
  <br/><br/>
    
    
  <table width="95%" align="center" summary="for layout only">
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
