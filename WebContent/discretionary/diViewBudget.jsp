<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diViewBudget.jsp
 * Creation/Modification History  :
 *
 *     SHusak       1/24/08     Created
 *
 * Description
 * This is html view of the DI budget.  Currently used for DI participating view, 
 * admin and applicant print HTML view. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">    
    <title>Discretionary Grant Project Budget</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>
  <br/> 
  
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Conservation/Preservation Program - Discretionary Grants
          <br/>Project Budget
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>
          03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
      </td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  <br/><br/>
      
  
  <table align="center" width="95%" summary="for layout only" >  
    <tr>
      <th colspan="7">Personal Service Expenses</th>
    </tr>
    
    <tr>
      <td colspan="7">            
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
          <table width="100%" class="boxtype">
          <tr>
            <td colspan="2"><b>Name</b></td><td><b>Title</b></td>
            <td><b>Salary/Wage</b></td><td><b>FTE/Hours</b></td><td><b>Type</b></td>
          </tr>
          <tr>
            <td colspan="2"><c:out value="${PersonalBean.name}" /></td>
            <td><c:out value="${PersonalBean.title}" /></td>
            <%--<td><fmt:formatNumber value="${PersonalBean.salary}" type="currency" /></td>--%>
            <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency" /></td>
            <td><c:out value="${PersonalBean.fte}" /></td>
            <td><c:if test="${PersonalBean.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${PersonalBean.typeCode=='4'}" >
                Support Staff
              </c:if></td>
          </tr>
          <tr>
            <td colspan="2"><b>Beginning Date of Employment</b></td>    
            <td colspan="2"><b>Ending Date of Employment</b></td>
          </tr> 
          <tr>
            <td colspan="2"><c:out value="${PersonalBean.beginDateStr}" /></td>
            <td colspan="2"><c:out value="${PersonalBean.endDateStr}" /></td>
          </tr>  
          <tr>
            <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
            <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
            <td width="14%"><b>AmtAmended</b></td>
            <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${PersonalBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
        </logic:iterate>
        </logic:present>
    </td>
  </tr>   
  
  <logic:notEmpty name="budgetBean" property="allPersRecords">
    <tr>
      <td colspan="7" ><b>Personal Service Totals</b></td>
    </tr>
      <tr>
        <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
        <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
        <td width="14%"><b>AmtAmended</b></td>
        <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
      </tr>  
    <tr>
      <td><fmt:formatNumber value="${totalsBean.perProjTot}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perInstCont}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.perAmtAppr}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.perAmtAmend}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.perExpSub}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.perExpAppr}" type="currency" maxFractionDigits="0"/></td> 
    </tr>
  </logic:notEmpty>
    
  <tr>
    <td height="40" />
  </tr>
        
  <tr>
    <th colspan="7">Employee Benefit Expenses</th>
  </tr>
  <tr>
    <td colspan="7">
      <logic:present name="budgetBean" property="allBenRecords" >
      <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <table width="100%" class="boxtype">
        <tr>
          <td colspan="3"><b>Name</b></td>
          <td colspan="3">Benefits Percentage</td>
        </tr>
        <tr>
          <td colspan="3"><c:out value="${BenefitsBean.name}" /></td>
          <td colspan="3"><fmt:formatNumber value="${BenefitsBean.benpercent}"/></td>
        </tr>
        <tr>
            <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
            <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
            <td width="14%"><b>AmtAmended</b></td>
            <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
          </tr>  
        <tr>
          <td><fmt:formatNumber value="${BenefitsBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        </table>
      </logic:iterate>
      </logic:present>
     </td>
    </tr>    
    
    <logic:notEmpty name="budgetBean" property="allBenRecords">
      <tr>
        <td colspan="7"><b>Employee Benefits Totals</b></td>
      </tr>
      <tr>
        <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
        <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
        <td width="14%"><b>AmtAmended</b></td>
        <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
      </tr>  
      <tr>
        <td><fmt:formatNumber value="${totalsBean.benProjTot}" type="currency" maxFractionDigits="0"/></td>
        <td><fmt:formatNumber value="${totalsBean.benInstCont}" type="currency" maxFractionDigits="0"/></td>
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
            
    <tr>
     <th colspan="7">Contracted Services Expenses</th>
    </tr>
    <tr>
      <td colspan="7">                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
          <table width="100%" class="boxtype">
          <tr>
            <td colspan="2"><b>Service Type</b></td><td colspan="2"><b>Recipient</b></td>
            <td colspan="2"><b>Description</b></td>
          </tr>
          <tr>
            <td colspan="2"><c:out value="${ContractedBean.servicetype}" /></td>
            <td colspan="2"><c:out value="${ContractedBean.recipient}" /></td>
            <td colspan="2"><c:out value="${ContractedBean.servicedescr}" /></td>
          </tr>
          <tr>
            <td colspan="2"><b>Purchase Order Date</b></td>    
            <td colspan="2"><b>Check/Journal Entry#</b></td>
          </tr> 
          <tr>
            <td colspan="2"><c:out value="${ContractedBean.encumbranceDateStr}" /></td>
            <td colspan="2"><c:out value="${ContractedBean.journalEntry}" /></td>
          </tr>  
          <tr>
            <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
            <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
            <td width="14%"><b>AmtAmended</b></td>
            <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
          </tr>  
          <tr>          
            <td><fmt:formatNumber value="${ContractedBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
        </logic:iterate>
        </logic:present>
     </td>
    </tr>
        
    <logic:notEmpty name="budgetBean" property="allContractRecords">
      <tr>
        <td colspan="7"><b>Contracted Service Totals</b></td>
      </tr>
      <tr>
        <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
        <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
        <td width="14%"><b>AmtAmended</b></td>
        <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
      </tr>  
      <tr>
        <td><fmt:formatNumber value="${totalsBean.conProjTot}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.conInstCont}" type="currency" maxFractionDigits="0"/></td>
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
    
    <tr>
     <th colspan="7">Supplies, Materials and Equipment Expenses</th>
    </tr>
    <tr>
      <td colspan="7">      
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
            <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" /></td>
            <td><c:out value="${SupplyBean.vendor}" /></td>
          </tr>
          <tr>
            <td colspan="2"><b>Purchase Order Date</b></td>    
            <td colspan="2"><b>Check/Journal Entry#</b></td>
          </tr> 
          <tr>
            <td colspan="2"><c:out value="${SupplyBean.encumbranceDateStr}" /></td>
            <td colspan="2"><c:out value="${SupplyBean.journalEntry}" /></td>
          </tr>  
          <tr>
            <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
            <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
            <td width="14%"><b>AmtAmended</b></td>
            <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${SupplyBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
        </logic:iterate>
        </logic:present>
     </td>
    </tr>
    
    <logic:notEmpty name="budgetBean" property="allSupplyRecords">
      <tr>
        <td colspan="7"><b>Supplies, Materials, Equipment Totals</b></td>
      </tr>
       <tr>
        <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
        <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
        <td width="14%"><b>AmtAmended</b></td>
        <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
      </tr>  
      <tr>
        <td><fmt:formatNumber value="${totalsBean.suppProjTot}" type="currency" maxFractionDigits="0"/></td>
        <td><fmt:formatNumber value="${totalsBean.suppInstCont}" type="currency" maxFractionDigits="0"/></td>
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
                
    <tr>
     <th colspan="7">Travel Expenses</th>
    </tr>
    <tr>
      <td colspan="7">               
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" >
          <table width="100%" class="boxtype">
          <tr>
            <td colspan="2"><b>Description</b></td>
            <td colspan="2"><b>Purpose</b></td>
            <td colspan="2"><b>Calculation of cost</b></td>
          </tr>
          <tr>
            <td colspan="2"><c:out value="${TravelBean.description}" /></td>
            <td colspan="2"><c:out value="${TravelBean.purpose}" /></td>
            <td colspan="2"><c:out value="${TravelBean.costsummary}" /></td>
          </tr>
          <tr>
            <td colspan="2"><b>Dates of Travel</b></td>    
            <td colspan="2"><b>Name of Traveler</b></td>
            <td colspan="2"><b>Check/Journal Entry</b></td>
          </tr> 
          <tr>
            <td colspan="2"><c:out value="${TravelBean.travelPeriod}" /></td>
            <td colspan="2"><c:out value="${TravelBean.travelerName}" /></td>
            <td colspan="2"><c:out value="${TravelBean.journalEntry}" /></td>
          </tr>
          <tr>
            <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
            <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
            <td width="14%"><b>AmtAmended</b></td>
            <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${TravelBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.instcont}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
          </tr>
          </table>
        </logic:iterate>
        </logic:present>
     </td>
    </tr>      
    
    <logic:notEmpty name="budgetBean" property="allTravelRecords">
    <tr>
      <td colspan="7"><b>Travel Expense Totals</b></td>
    </tr>
      <tr>
        <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
        <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
        <td width="14%"><b>AmtAmended</b></td>
        <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
      </tr>  
    <tr>
      <td><fmt:formatNumber value="${totalsBean.travProjTot}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.travInstCont}" type="currency" maxFractionDigits="0" /></td>
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
    
    <tr bgcolor="Silver"><th colspan="7">Grand Total</th></tr>
      <tr>
        <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
        <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
        <td width="14%"><b>AmtAmended</b></td>
        <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
      </tr>  
    <tr>
      <td><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency"  maxFractionDigits="0" /></td>
    </tr>
  </table>    
  <br/><br/><br/>
  
  </body>
</html>
