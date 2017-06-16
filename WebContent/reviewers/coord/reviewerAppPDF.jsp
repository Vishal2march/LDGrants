<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  reviewerAppPDF.jsp
 * Creation/Modification History  :
 *
 *     SHusak       8/2/07     Created
 *
 * Description
 * This page allows the reviewer to view a PDF version of the
 * complete CO grant application info that was entered by the applicant.
 * Includes budget and coversheet, Not the Narratives or any admin comments or approvals. 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<pd4ml:transform screenWidth="450" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
     <title>NYS Library Coordinated Grant Application</title>
  </head>
  <body>
  
  <font size="1">
  <p align="center"><b>Conservation Preservation Program Grant Application
          <br/>The University of the State of New York 
          <br/>The State Education Department 
          <br/>Division of Library Development 
          <br/>Coordinated Grants
  </b></p>
  </font>
  
  <font size="1">
  <table align="center" border="1" summary="for layout only">
  <tr >
    <th colspan="2">Grant Application for Fiscal Year <c:out value="${thisGrant.fiscalyear}" /></th>
  </tr>
  
  <tr>
    <td><b>Project Number</b></td>
    <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
        -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${thisGrant.projseqnum}" /></td>
  </tr>  
  <tr>
    <td><b>Project Title</b></td>
    <td><c:out value="${thisGrant.title}" /></td>
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
    <td><c:out value="${thisGrant.addr1}" /><br/><c:out value="${thisGrant.addr2}" /></td>
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
  <tr>
    <td><b>Project Manager</b></td>
    <td>
      <c:out value="${projManagerBean.fname}" /> <c:out value="${projManagerBean.mname}" /> <c:out value="${projManagerBean.lname}" />
    </td>
  </tr>
  <tr>
    <td><b>Project Manager Phone</b></td>
    <td><c:out value="${projManagerBean.phone}" /></td>
  </tr>
  <tr>
    <td><b>Project Manager Email</b></td>
    <td><c:out value="${projManagerBean.email}" /></td>
  </tr>
  <tr>
    <th colspan="2">Total Budget Amount Requested</th>
  </tr>
  <c:forEach var="row" items="${allsums}">
    <tr>
      <td>Total requested for fy <fmt:formatNumber value="${row.fycode}" minIntegerDigits="2" /></td>
      <td><fmt:formatNumber value="${row.totAmtReq}" type="currency"  /></td>
    </tr>
  </c:forEach>
  
  <tr>
    <th colspan="2">Participating Institutions</th>
  </tr>    
    
  <c:forEach var="partInstBean" items="${allPartInst}">
    <tr>
      <td colspan="2"><c:out value="${partInstBean.instName}" /></td>
    </tr>
  </c:forEach>  
  </table>  
  </font>
  <br/><br/> 
  
  <font size="1">
  <table align="center" width="95%" summary="for layout only" >
  <tr bgcolor="Silver">
    <th>Project Budget</th>
  </tr>
  <tr>
    <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />)</b> </td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ProjTot" value="0" />
    <c:set var="InstCont" value="0" />
  
  <tr>
    <td>
      <table width="100%" summary="for layout only">
        <tr>
          <th colspan="4">Personal Service Expenses</th>
        </tr>    
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
        
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <c:if test="${thisGrant.fycode==PersonalBean.fycode}">
        <tr>
          <td><b>Name</b></td><td><b>Title</b></td>
          <td><b>Salary/Wage</b></td><td><b>FTE/Hours</b></td>
        </tr>
        <tr>
          <td><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) of personal expenses --%>
          <c:set var="InstCont" value="${InstCont + PersonalBean.instcont}" />
          <c:set var="ProjTot" value="${ProjTot + PersonalBean.projtotal}" />
          <c:set var="GrantReq" value="${GrantReq + PersonalBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + PersonalBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${PersonalBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Employee Benefit Expenses</th>
        </tr>        
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allBenRecords" >
        <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <c:if test="${thisGrant.fycode==BenefitsBean.fycode}">
        <tr>
          <td colspan="2"><b>Name</b></td>
          <td colspan="2"><b>Benefits percentage</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${BenefitsBean.name}" /></td>
          <td colspan="2"><c:out value="${BenefitsBean.benpercent}"/></td>    
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + BenefitsBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + BenefitsBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + BenefitsBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + BenefitsBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${BenefitsBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
           <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
        
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Contracted Services Expenses</th>
        </tr>      
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" >
        <c:if test="${thisGrant.fycode==ContractedBean.fycode}">
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
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>          
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
          <c:set var="ProjTot" value="${ProjTot + ContractedBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + ContractedBean.instcont}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${ContractedBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
           <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>       
  
  
  
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Supplies, Materials and Equipment Expenses</th>
        </tr>      
        <tr>
          <td colspan="4"><hr/></td>
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
          <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" maxFractionDigits="0" /></td>
          <td><c:out value="${SupplyBean.vendor}" /></td>
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + SupplyBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + SupplyBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
     
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${SupplyBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Other Expenses</th>
        </tr>       
        <tr>
          <td colspan="4"><hr/></td>
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
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + OtherExpBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + OtherExpBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + OtherExpBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + OtherExpBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${OtherExpBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
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
      <td height="20" />
    </tr>
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Travel Expenses</th>
        </tr>       
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
        <c:if test="${thisGrant.fycode==TravelBean.fycode}">
        <tr>
          <td><b>Description</b></td>
          <td><b>Purpose</b></td>
          <td colspan="2"><b>Calculation of cost</b></td>
        </tr>
        <tr>
          <td><c:out value="${TravelBean.description}" /></td>
          <td><c:out value="${TravelBean.purpose}" /></td>
          <td colspan="2"><c:out value="${TravelBean.costsummary}" /></td>
        </tr>      
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + TravelBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + TravelBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${TravelBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
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
      <td height="20" />
    </tr>
  
  
    <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="4">Totals for Budget Year 1</th></tr>
        <tr>
          <td width="25%"><b>Project Total</b></td>
          <td width="25%"><b>Inst Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${ProjTot}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${InstCont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="50" />
  </tr>
  
  
  
  
  <tr>
    <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />)</b> </td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ProjTot" value="0" />
    <c:set var="InstCont" value="0" />
  
  <tr>
    <td>
      <table width="100%" summary="for layout only">
        <tr>
          <th colspan="4">Personal Service Expenses</th>
        </tr>    
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
        
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <c:if test="${thisGrant.fycode +1==PersonalBean.fycode}">
        <tr>
          <td><b>Name</b></td><td><b>Title</b></td>
          <td><b>Salary/Wage</b></td><td><b>FTE/Hours</b></td>
        </tr>
        <tr>
          <td><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) of personal expenses --%>
          <c:set var="InstCont" value="${InstCont + PersonalBean.instcont}" />
          <c:set var="ProjTot" value="${ProjTot + PersonalBean.projtotal}" />
          <c:set var="GrantReq" value="${GrantReq + PersonalBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + PersonalBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${PersonalBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Employee Benefit Expenses</th>
        </tr>        
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allBenRecords" >
        <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <c:if test="${thisGrant.fycode +1==BenefitsBean.fycode}">
        <tr>
          <td colspan="2"><b>Name</b></td>
          <td colspan="2"><b>Benefits percentage</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${BenefitsBean.name}" /></td>
          <td colspan="2"><c:out value="${BenefitsBean.benpercent}"/></td>    
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + BenefitsBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + BenefitsBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + BenefitsBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + BenefitsBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${BenefitsBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
        
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Contracted Services Expenses</th>
        </tr>      
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" >
        <c:if test="${thisGrant.fycode +1==ContractedBean.fycode}">
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
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>          
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
          <c:set var="ProjTot" value="${ProjTot + ContractedBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + ContractedBean.instcont}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${ContractedBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>       
  
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Supplies, Materials and Equipment Expenses</th>
        </tr>      
        <tr>
          <td colspan="4"><hr/></td>
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
          <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" maxFractionDigits="0" /></td>
          <td><c:out value="${SupplyBean.vendor}" /></td>
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + SupplyBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + SupplyBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${SupplyBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
           <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Other Expenses</th>
        </tr>       
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allExpRecords" >
        <logic:iterate name="budgetBean" property="allExpRecords" id="OtherExpBean" > 
        <c:if test="${thisGrant.fycode +1==OtherExpBean.fycode}">
        <tr>
          <td colspan="2"><b>Description</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${OtherExpBean.description}" /></td>
        </tr>
       <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + OtherExpBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + OtherExpBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + OtherExpBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + OtherExpBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${OtherExpBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
           <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
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
      <td height="20" />
    </tr>
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Travel Expenses</th>
        </tr>       
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
        <c:if test="${thisGrant.fycode +1 ==TravelBean.fycode}">
        <tr>
          <td><b>Description</b></td>
          <td><b>Purpose</b></td>
          <td colspan="2"><b>Calculation of cost</b></td>
        </tr>
        <tr>
          <td><c:out value="${TravelBean.description}" /></td>
          <td><c:out value="${TravelBean.purpose}" /></td>
          <td colspan="2"><c:out value="${TravelBean.costsummary}" /></td>
        </tr>      
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + TravelBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + TravelBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${TravelBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
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
      <td height="20" />
    </tr>
    
    
    
    <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="4">Totals for Budget Year 2</th></tr>
        <tr>
          <td width="25%"><b>Project Total</b></td>
          <td width="25%"><b>Inst Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${ProjTot}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${InstCont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="50" />
  </tr>
  
  
  <tr>
    <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />)</b> </td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ProjTot" value="0" />
    <c:set var="InstCont" value="0" />
  
  <tr>
    <td>
      <table width="100%" summary="for layout only">
        <tr>
          <th colspan="4">Personal Service Expenses</th>
        </tr>    
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
        
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <c:if test="${thisGrant.fycode +2==PersonalBean.fycode}">
        <tr>
          <td><b>Name</b></td><td><b>Title</b></td>
          <td><b>Salary/Wage</b></td><td><b>FTE/Hours</b></td>
        </tr>
        <tr>
          <td><c:out value="${PersonalBean.name}" /></td>
          <td><c:out value="${PersonalBean.title}" /></td>
          <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
          <td><c:out value="${PersonalBean.fte}" /></td>
        </tr>
       <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) of personal expenses --%>
          <c:set var="InstCont" value="${InstCont + PersonalBean.instcont}" />
          <c:set var="ProjTot" value="${ProjTot + PersonalBean.projtotal}" />
          <c:set var="GrantReq" value="${GrantReq + PersonalBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + PersonalBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${PersonalBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Employee Benefit Expenses</th>
        </tr>        
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allBenRecords" >
        <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <c:if test="${thisGrant.fycode +2==BenefitsBean.fycode}">
        <tr>
          <td colspan="2"><b>Name</b></td>
          <td colspan="2"><b>Benefits percentage</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${BenefitsBean.name}" /></td>
          <td colspan="2"><c:out value="${BenefitsBean.benpercent}"/></td>    
          
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>   
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + BenefitsBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + BenefitsBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + BenefitsBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + BenefitsBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${BenefitsBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
        
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Contracted Services Expenses</th>
        </tr>      
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" >
        <c:if test="${thisGrant.fycode +2==ContractedBean.fycode}">
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
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>          
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
          <c:set var="ProjTot" value="${ProjTot + ContractedBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + ContractedBean.instcont}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${ContractedBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>       
  
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Supplies, Materials and Equipment Expenses</th>
        </tr>      
        <tr>
          <td colspan="4"><hr/></td>
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
          <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" maxFractionDigits="0" /></td>
          <td><c:out value="${SupplyBean.vendor}" /></td>
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + SupplyBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + SupplyBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${SupplyBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
      <td height="20" />
    </tr>
    
    
    <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Other Expenses</th>
        </tr>       
        <tr>
          <td colspan="4"><hr/></td>
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
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + OtherExpBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + OtherExpBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + OtherExpBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + OtherExpBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${OtherExpBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
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
      <td height="20" />
    </tr>
  
  
  <tr>
     <td>
      <table width="100%" summary="for layout only" >
        <tr>
          <th colspan="4">Travel Expenses</th>
        </tr>       
        <tr>
          <td colspan="4"><hr/></td>
        </tr>
               
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
        <c:if test="${thisGrant.fycode +2 ==TravelBean.fycode}">
        <tr>
          <td><b>Description</b></td>
          <td><b>Purpose</b></td>
          <td colspan="2"><b>Calculation of cost</b></td>
        </tr>
        <tr>
          <td><c:out value="${TravelBean.description}" /></td>
          <td><c:out value="${TravelBean.purpose}" /></td>
          <td colspan="2"><c:out value="${TravelBean.costsummary}" /></td>
        </tr>      
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
           <%-- calculate the total for (grant amounts) in benefits expenses --%>
          <c:set var="ProjTot" value="${ProjTot + TravelBean.projtotal}" />
          <c:set var="InstCont" value="${InstCont + TravelBean.instcont}" />
          <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
          <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
          <%-- continue to output grant amounts in form of currency --%>
          <td><fmt:formatNumber value="${TravelBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.instcont}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
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
      <td height="20" />
    </tr>
  
  
  
    <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="4">Totals for Budget Year 3</th></tr>
        <tr>
          <td width="25%"><b>Project Total</b></td>
          <td width="25%"><b>Inst Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${ProjTot}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${InstCont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="50" />
  </tr>
  
    <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="4">Grand Total for All Years</th></tr>
        <tr>
          <td width="25%"><b>Project Total</b></td>
          <td width="25%"><b>Inst Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency"  maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency"  maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency"  maxFractionDigits="0" /></td>
         </tr>
      </table>
   </td>
  </tr>
  </table>  
  </font>
  
  </body>
</html>
</pd4ml:transform>