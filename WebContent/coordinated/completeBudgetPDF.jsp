<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  completeBudgetPDF.jsp
 * Creation/Modification History  :
 *     SHusak       9/12/07     Modified
 *
 * Description
 * This is a read only spreadsheet with all budget info for the CO grant, seperated
 * by budget category and fiscal year.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<pd4ml:transform screenWidth="700" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title></title>
  </head>
  <body>  
  
  
  <font size="1">
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <th colspan="2">Conservation/Preservation Program Coordinated Grants - Project Budget</th>
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
      <td>Contract Number</td>
      <td><c:out value="${thisGrant.contractNum}" /></td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  </font>
  <br/><br/>
  
   
  <font size="1">
  <table align="center" width="95%" summary="for layout only" >
  <tr>
    <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />)</b> </td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="AmtAppr" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ExpAppr" value="0" />
    <c:set var="AmtAmend" value="0" />
    <c:set var="ProjTot" value="0" />
    <c:set var="InstCont" value="0" />
  
  <tr>
    <td>
      <table width="100%" summary="for layout only">
        <tr>
          <th colspan="7">Personal Service Expenses</th>
        </tr>    
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
        
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <c:if test="${thisGrant.fycode==PersonalBean.fycode}">
        <tr>
          <td colspan="2"><b>Name</b></td><td><b>Title</b></td>
          <td><b>Salary/Wage</b></td><td><b>FTE/Hours</b></td><td><b>Type</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
          <td><c:if test="${PersonalBean.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${PersonalBean.typeCode=='4'}" >
                Support Staff
              </c:if></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) of personal expenses --%>
          <c:set var="InstCont" value="${InstCont + PersonalBean.instcont}" />
          <c:set var="ProjTot" value="${ProjTot + PersonalBean.projtotal}" />
          <c:set var="GrantReq" value="${GrantReq + PersonalBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + PersonalBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + PersonalBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + PersonalBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + PersonalBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${PersonalBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>  
        </c:if>
        </logic:iterate>
        </logic:present>
        </table>
      </td>
    </tr>   
    <tr>
      <td height="10" />
    </tr>
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Employee Benefit Expenses</th>
        </tr>        
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allBenRecords" >
        <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <c:if test="${thisGrant.fycode==BenefitsBean.fycode}">
        <tr>
          <td colspan="3"><b>Name</b></td>
          <td colspan="3"><b>Benefits percentage</b></td>
        </tr>
        <tr>
          <td colspan="3"><c:out value="${BenefitsBean.name}" /></td>
          <td colspan="3"><c:out value="${BenefitsBean.benpercent}"/></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + BenefitsBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + BenefitsBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + BenefitsBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + BenefitsBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + BenefitsBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + BenefitsBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + BenefitsBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${BenefitsBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
        
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Contracted Services Expenses</th>
        </tr>      
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
        <c:if test="${thisGrant.fycode==ContractedBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>          
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + ContractedBean.amountapproved}" />
          <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + ContractedBean.expapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + ContractedBean.amtamended}" />
          <c:set var="ProjTot" value="${ProjTot + ContractedBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + ContractedBean.instcont}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${ContractedBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>       
  
  
  
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Supplies, Materials and Equipment Expenses</th>
        </tr>      
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allSupplyRecords" >
        <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
        <c:if test="${thisGrant.fycode==SupplyBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + SupplyBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + SupplyBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + SupplyBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + SupplyBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + SupplyBean.expapproved}" />          
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${SupplyBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>      
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Other Expenses</th>
        </tr>       
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allExpRecords" >
        <logic:iterate name="budgetBean" property="allExpRecords" id="OtherExpBean" > 
        <c:if test="${thisGrant.fycode==OtherExpBean.fycode}">
        <tr>
          <td colspan="2"><b>Description</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${OtherExpBean.description}" /></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + OtherExpBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + OtherExpBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + OtherExpBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + OtherExpBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + OtherExpBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + OtherExpBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + OtherExpBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${OtherExpBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
       </logic:iterate></logic:present>
      </table>
     </td>
    </tr>  
    <tr>
      <td height="10" />
    </tr>
  
  
  
  
   <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Travel Expenses</th>
        </tr>       
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
        <c:if test="${thisGrant.fycode==TravelBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + TravelBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + TravelBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + TravelBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + TravelBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + TravelBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${TravelBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>  
    <tr>
      <td height="10" />
    </tr>
  
  
  
    <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="7">Totals for Budget Year 1</th></tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <td><fmt:formatNumber value="${ProjTot}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${InstCont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAppr}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAmend}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpAppr}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
  
  
  
  
  <tr>
    <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />)</b> </td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="AmtAppr" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ExpAppr" value="0" />
    <c:set var="AmtAmend" value="0" />
    <c:set var="ProjTot" value="0" />
    <c:set var="InstCont" value="0" />
  
  <tr>
    <td>
      <table width="100%" summary="for layout only">
        <tr>
          <th colspan="7">Personal Service Expenses</th>
        </tr>    
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
        
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <c:if test="${thisGrant.fycode +1==PersonalBean.fycode}">
        <tr>
          <td colspan="2"><b>Name</b></td><td><b>Title</b></td>
          <td><b>Salary/Wage</b></td><td><b>FTE/Hours</b></td><td><b>Type</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
          <td><c:if test="${PersonalBean.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${PersonalBean.typeCode=='4'}" >
                Support Staff
              </c:if></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) of personal expenses --%>
          <c:set var="InstCont" value="${InstCont + PersonalBean.instcont}" />
          <c:set var="ProjTot" value="${ProjTot + PersonalBean.projtotal}" />
          <c:set var="GrantReq" value="${GrantReq + PersonalBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + PersonalBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + PersonalBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + PersonalBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + PersonalBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${PersonalBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>  
        </c:if>
        </logic:iterate>
        </logic:present>
        </table>
      </td>
    </tr>   
    <tr>
      <td height="10" />
    </tr>
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Employee Benefit Expenses</th>
        </tr>        
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allBenRecords" >
        <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <c:if test="${thisGrant.fycode +1==BenefitsBean.fycode}">
        <tr>
          <td colspan="3"><b>Name</b></td>
          <td colspan="3"><b>Benefits percentage</b></td>
        </tr>
        <tr>
          <td colspan="3"><c:out value="${BenefitsBean.name}" /></td>
          <td colspan="3"><c:out value="${BenefitsBean.benpercent}"/></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + BenefitsBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + BenefitsBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + BenefitsBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + BenefitsBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + BenefitsBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + BenefitsBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + BenefitsBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${BenefitsBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
        
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Contracted Services Expenses</th>
        </tr>      
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
        <c:if test="${thisGrant.fycode +1==ContractedBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>          
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + ContractedBean.amountapproved}" />
          <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + ContractedBean.expapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + ContractedBean.amtamended}" />
          <c:set var="ProjTot" value="${ProjTot + ContractedBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + ContractedBean.instcont}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${ContractedBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>       
  
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Supplies, Materials and Equipment Expenses</th>
        </tr>      
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allSupplyRecords" >
        <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
        <c:if test="${thisGrant.fycode +1==SupplyBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + SupplyBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + SupplyBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + SupplyBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + SupplyBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + SupplyBean.expapproved}" />          
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${SupplyBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>       
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Other Expenses</th>
        </tr>       
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allExpRecords" >
        <logic:iterate name="budgetBean" property="allExpRecords" id="OtherExpBean" > 
        <c:if test="${thisGrant.fycode +1==OtherExpBean.fycode}">
        <tr>
          <td colspan="3"><b>Description</b></td>
        </tr>
        <tr>
          <td colspan="3"><c:out value="${OtherExpBean.description}" /></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + OtherExpBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + OtherExpBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + OtherExpBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + OtherExpBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + OtherExpBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + OtherExpBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + OtherExpBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${OtherExpBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>  
    <tr>
      <td height="10" />
    </tr>
    
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Travel Expenses</th>
        </tr>       
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
        <c:if test="${thisGrant.fycode +1 ==TravelBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + TravelBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + TravelBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + TravelBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + TravelBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + TravelBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${TravelBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>  
    <tr>
      <td height="10" />
    </tr>
    
    
    
    
    <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="7">Totals for Budget Year 2</th></tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <td><fmt:formatNumber value="${ProjTot}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${InstCont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAppr}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAmend}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpAppr}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
  
  
  <tr>
    <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />)</b> </td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="AmtAppr" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ExpAppr" value="0" />
    <c:set var="AmtAmend" value="0" />
    <c:set var="ProjTot" value="0" />
    <c:set var="InstCont" value="0" />
  
  <tr>
    <td>
      <table width="100%" summary="for layout only">
        <tr>
          <th colspan="7">Personal Service Expenses</th>
        </tr>    
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
        
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <c:if test="${thisGrant.fycode +2==PersonalBean.fycode}">
        <tr>
          <td colspan="2"><b>Name</b></td><td><b>Title</b></td>
          <td><b>Salary/Wage</b></td><td><b>FTE/Hours</b></td><td><b>Type</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
          <td><c:if test="${PersonalBean.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${PersonalBean.typeCode=='4'}" >
                Support Staff
              </c:if></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) of personal expenses --%>
          <c:set var="InstCont" value="${InstCont + PersonalBean.instcont}" />
          <c:set var="ProjTot" value="${ProjTot + PersonalBean.projtotal}" />
          <c:set var="GrantReq" value="${GrantReq + PersonalBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + PersonalBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + PersonalBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + PersonalBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + PersonalBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${PersonalBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>  
        </c:if>
        </logic:iterate></logic:present>
        </table>
      </td>
    </tr>   
    <tr>
      <td height="10" />
    </tr>
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Employee Benefit Expenses</th>
        </tr>        
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allBenRecords" >
        <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <c:if test="${thisGrant.fycode +2==BenefitsBean.fycode}">
        <tr>
          <td colspan="3"><b>Name</b></td>
          <td colspan="3"><b>Benefits percentage</b></td>
        </tr>
        <tr>
          <td colspan="3"><c:out value="${BenefitsBean.name}" /></td>
          <td colspan="3"><c:out value="${BenefitsBean.benpercent}"/></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + BenefitsBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + BenefitsBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + BenefitsBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + BenefitsBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + BenefitsBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + BenefitsBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + BenefitsBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${BenefitsBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
        
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Contracted Services Expenses</th>
        </tr>      
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
        <c:if test="${thisGrant.fycode +2==ContractedBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>          
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + ContractedBean.amountapproved}" />
          <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + ContractedBean.expapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + ContractedBean.amtamended}" />
          <c:set var="ProjTot" value="${ProjTot + ContractedBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + ContractedBean.instcont}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${ContractedBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>       
  
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Supplies, Materials and Equipment Expenses</th>
        </tr>      
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allSupplyRecords" >
        <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
        <c:if test="${thisGrant.fycode +2==SupplyBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + SupplyBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + SupplyBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + SupplyBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + SupplyBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + SupplyBean.expapproved}" />          
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${SupplyBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>       
      </table>
     </td>
    </tr>
    <tr>
      <td height="10" />
    </tr>
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Other Expenses</th>
        </tr>       
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allExpRecords" >
        <logic:iterate name="budgetBean" property="allExpRecords" id="OtherExpBean" > 
        <c:if test="${thisGrant.fycode +2==OtherExpBean.fycode}">
        <tr>
          <td colspan="2"><b>Description</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${OtherExpBean.description}" /></td>
        </tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + OtherExpBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + OtherExpBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + OtherExpBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + OtherExpBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + OtherExpBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + OtherExpBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + OtherExpBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${OtherExpBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>  
    <tr>
      <td height="10" />
    </tr>
  
  
   <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="7">Travel Expenses</th>
        </tr>       
        <tr>
          <td colspan="7"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
        <c:if test="${thisGrant.fycode +2 ==TravelBean.fycode}">
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
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + TravelBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + TravelBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
          <c:set var="AmtAppr" value="${AmtAppr + TravelBean.amountapproved}" />
          <c:set var="AmtAmend" value="${AmtAmend + TravelBean.amtamended}" />
          <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
          <c:set var="ExpAppr" value="${ExpAppr + TravelBean.expapproved}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${TravelBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        <tr>
          <td height="20" />
        </tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
     </td>
    </tr>  
    <tr>
      <td height="10" />
    </tr>
  
  
    <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="7">Totals for Budget Year 3</th></tr>
        <tr>
          <td width="14%"><b>Proj Total</b></td><td width="14%"><b>Inst'l Contrib.</b></td>
          <td width="14%"><b>AmtRequested</b></td><td width="14%"><b>AmtApproved</b></td>
          <td width="14%"><b>AmtAmended</b></td>
          <td width="14%"><b>ExpSubmitted</b></td><td width="14%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <td><fmt:formatNumber value="${ProjTot}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${InstCont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAppr}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAmend}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpAppr}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
  
    <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="7">Grand Total for All Years</th></tr>
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
   </td>
  </tr>
  </table>  
  </font>
  
  
  </body>
</html>

</pd4ml:transform>
