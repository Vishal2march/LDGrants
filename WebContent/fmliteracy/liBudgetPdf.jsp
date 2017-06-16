<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="450" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center"  summary="for layout only">
    <tr>
      <th colspan="2"><c:if test="${thisGrant.fccode==40}" >Adult</c:if>
      <c:if test="${thisGrant.fccode==42}" >Family</c:if> Literacy Library Services - Project Budget</th>
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
  <br/>  
  
  
  <table align="center" width="95%" summary="for layout only" >
  <tr>
    <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />)</b></td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="AmtAppr" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ExpAppr" value="0" />  
   
  <c:if test="${thisGrant.fycode<14}">
  <%--salaries/benefits only used before FY2013/14--%> 
  <tr>
    <th>Salary Expenses<hr/></th>
  </tr>
  <tr>
    <td>
      <table width="100%" summary="for layout only">
      <logic:present name="budgetBean" property="allPersRecords" >
      <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <c:if test="${thisGrant.fycode==PersonalBean.fycode}">        
          <tr>
            <td><b>Name</b></td><td><b>Title</b></td>
            <td><b>Salary</b></td><td><b>FTE</b></td>
            <td><b>Type</b></td>
          </tr>
          <tr>
            <td><c:out value="${PersonalBean.name}" /></td>
            <td><c:out value="${PersonalBean.title}" /></td>
            <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
            <td><c:out value="${PersonalBean.fte}" /></td>
            <td><c:if test="${PersonalBean.typeCode=='3'}" >
                  Professional
                </c:if>
                <c:if test="${PersonalBean.typeCode=='4'}" >
                  Support Staff
                </c:if> </td>
          </tr>
          <tr>
            <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
            <td width="20%"><b>ActualExp</b></td>
            <td width="20%" /><td width="20%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <%-- calculate the total for (grant amounts) of personal expenses --%>
            <c:set var="GrantReq" value="${GrantReq + PersonalBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + PersonalBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + PersonalBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + PersonalBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td></td>
            <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr><td colspan="5" height="10" /></tr>        
        </c:if>
        </logic:iterate></logic:present>
      </table>
    </td>
  </tr>   
  <tr>
    <td height="20" />
  </tr>
    
  <tr>
   <th>Employee Benefit Expenses<hr/></th>
  </tr>  
  <tr>
    <td>
      <table width="100%" summary="for layout only">
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
              <td width="25%"><b>AmtRequested</b></td><td width="25%"><b>AmtApproved</b></td>
              <td width="25%"><b>ActualExp</b></td><td width="25%"><b>ExpApproved</b></td>
            </tr>  
            <tr>
              <%-- calculate the total for (grant amounts) in benefits expenses --%>
              <c:set var="GrantReq" value="${GrantReq + BenefitsBean.grantrequest}" />
              <c:set var="AmtAppr" value="${AmtAppr + BenefitsBean.amountapproved}" />
              <c:set var="ExpSubm" value="${ExpSubm + BenefitsBean.expsubmitted}" />
              <c:set var="ExpAppr" value="${ExpAppr + BenefitsBean.expapproved}" />
              <%-- continue to output grant amounts in form of currency --%>
              <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
              <td><fmt:formatNumber value="${BenefitsBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
              <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
              <td><fmt:formatNumber value="${BenefitsBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
            </tr>
            <tr><td colspan="4" height="10" /></tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
  </c:if>
  
    
  <tr>
    <th>Purchased Services Expenses<hr/></th>
  </tr>
  <tr>
    <td>   
      <table width="100%">
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
            <td width="25%"><b>AmtRequested</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ActualExp</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>          
            <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + ContractedBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + ContractedBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr><td colspan="4" height="10" /></tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>       
  
  
  
  <tr>
    <th>Supplies, Materials and Equipment Expenses<hr/></th>
  </tr>
  <tr>
    <td>               
      <table width="100%">
        <logic:present name="budgetBean" property="allSupplyRecords" >
        <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
        <c:if test="${thisGrant.fycode==SupplyBean.fycode}">        
          <tr>
            <td><b>Quantity</b></td><td><b>Description</b></td>
            <td><b>Unit Price</b></td><td><b>Vendor</b></td>
            <td><b>Type</b></td>
          </tr>
          <tr>
            <td><c:out value="${SupplyBean.quantity}" /></td>
            <td><c:out value="${SupplyBean.description}" /></td>
            <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" /></td>
            <td><c:out value="${SupplyBean.vendor}" /></td>
            <td><c:if test="${SupplyBean.suppmatCode=='1'}" >
                    Supplies & Materials
                  </c:if>
                  <c:if test="${SupplyBean.suppmatCode=='2'}" >
                    Equipment
                  </c:if></td>
          </tr>
          <tr>
            <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
            <td colspan="2"><b>ActualExp</b></td>
            <td width="20%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + SupplyBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + SupplyBean.expapproved}" />          
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td colspan="2"><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
           
            <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr><td colspan="5" height="10" /></tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
    
    
  <tr>
    <th>Travel Expenses<hr/></th>
  </tr>
  <tr>
    <td>               
      <table width="100%">
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
            <td width="25%"><b>AmtRequested</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ActualExp</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
             <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + TravelBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + TravelBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
          </tr>
          <tr><td colspan="4" height="10" /></tr>
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
          <td><b>AmtRequested</b></td>
          <td><b>AmtApproved</b></td>
          <td><b>ActualExp</b></td>
          <td><b>ExpApproved</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAppr}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpAppr}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="30" />
  </tr>
      
  
  <tr>
    <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />)</b></td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="AmtAppr" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ExpAppr" value="0" />  
    
  <c:if test="${thisGrant.fycode<14}">
  <%--salaries/benefits only used before FY2013/14--%>  
  <tr>
    <th>Salary Expenses<hr/></th>
  </tr>
  <tr>
    <td>       
      <table width="100%" summary="for layout only">
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
        <c:if test="${thisGrant.fycode +1==PersonalBean.fycode}">      
          <tr>
            <td><b>Name</b></td><td><b>Title</b></td>
            <td><b>Salary</b></td><td><b>FTE</b></td>
            <td><b>Type</b></td>
          </tr>
          <tr>
            <td><c:out value="${PersonalBean.name}" /></td>
            <td><c:out value="${PersonalBean.title}" /></td>
            <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency"  /></td>
            <td><c:out value="${PersonalBean.fte}" /></td>
            <td><c:if test="${PersonalBean.typeCode=='3'}" >
                  Professional
                </c:if>
                <c:if test="${PersonalBean.typeCode=='4'}" >
                  Support Staff
                </c:if> </td>
          </tr>
         <tr>
            <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
            <td width="20%"><b>ActualExp</b></td>
            <td width="20%" /><td width="20%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <%-- calculate the total for (grant amounts) of personal expenses --%>
            <c:set var="GrantReq" value="${GrantReq + PersonalBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + PersonalBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + PersonalBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + PersonalBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td></td>
            <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr>
            <td colspan="5" height="10" />
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
    <th>Employee Benefit Expenses<hr/></th>
  </tr>
  <tr>
    <td>
      <table width="100%">
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
            <td width="25%"><b>AmtRequested</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ActualExp</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + BenefitsBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + BenefitsBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + BenefitsBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + BenefitsBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${BenefitsBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${BenefitsBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr><td colspan="5" height="10" /></tr>
          </c:if>
          </logic:iterate></logic:present>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
  </c:if>      
    
  <tr>
    <th>Purchased Services Expenses<hr/></th>
  </tr>
  <tr>
    <td>
      <table width="100%">
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
            <td width="25%"><b>AmtRequested</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ActualExp</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>          
            <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + ContractedBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + ContractedBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr><td colspan="4" height="10"/></tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>       
  
  
  <tr>
    <th>Supplies, Materials and Equipment Expenses<hr/></th>
  </tr>
  <tr>
    <td>
       <table width="100%">
        <logic:present name="budgetBean" property="allSupplyRecords" >
        <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
        <c:if test="${thisGrant.fycode +1==SupplyBean.fycode}">     
          <tr>
            <td><b>Quantity</b></td><td><b>Description</b></td>
            <td><b>Unit Price</b></td><td><b>Vendor</b></td>
            <td><b>Type</b></td>
          </tr>
          <tr>
            <td><c:out value="${SupplyBean.quantity}" /></td>
            <td><c:out value="${SupplyBean.description}" /></td>
            <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" /></td>
            <td><c:out value="${SupplyBean.vendor}" /></td>
            <td><c:if test="${SupplyBean.suppmatCode=='1'}" >
                      Supplies & Materials
                    </c:if>
                    <c:if test="${SupplyBean.suppmatCode=='2'}" >
                      Equipment
                    </c:if></td>
          </tr>
          <tr>
            <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
            <td colspan="2"><b>ActualExp</b></td>
            <td width="20%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + SupplyBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + SupplyBean.expapproved}" />          
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td colspan="2"><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          
            <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr><td colspan="5" height="10" /></tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
    
    
  <tr>
    <th>Travel Expenses<hr/></th>
  </tr>
  <tr>
    <td>         
      <table width="100%">
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" >
        <c:if test="${thisGrant.fycode +1==TravelBean.fycode}">      
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
            <td width="25%"><b>AmtRequested</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ActualExp</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
             <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + TravelBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + TravelBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
          </tr>
          <tr><td colspan="4" height="10" /></tr>
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
          <td><b>AmtRequested</b></td>
          <td><b>AmtApproved</b></td>
          <td><b>ActualExp</b></td>
          <td><b>ExpApproved</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAppr}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpAppr}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="30" />
  </tr>
  
  
  <%--   starting in FY 2013-14 --%>
  <c:if test="${thisGrant.fycode>13}">  
  <tr>
    <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />)</b></td>
  </tr>
    <c:set var="GrantReq" value="0" />
    <c:set var="AmtAppr" value="0" />
    <c:set var="ExpSubm" value="0" />
    <c:set var="ExpAppr" value="0" />  
   <tr>
    <th>Purchased Services Expenses<hr/></th>
  </tr>
  <tr>
    <td>
      <table width="100%">
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
            <td width="25%"><b>AmtRequested</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ActualExp</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>          
            <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + ContractedBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + ContractedBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + ContractedBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + ContractedBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr><td colspan="4" height="10"/></tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>       
  
  
  <tr>
    <th>Supplies, Materials and Equipment Expenses<hr/></th>
  </tr>
  <tr>
    <td>
       <table width="100%">
        <logic:present name="budgetBean" property="allSupplyRecords" >
        <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" > 
        <c:if test="${thisGrant.fycode +2==SupplyBean.fycode}">     
          <tr>
            <td><b>Quantity</b></td><td><b>Description</b></td>
            <td><b>Unit Price</b></td><td><b>Vendor</b></td>
            <td><b>Type</b></td>
          </tr>
          <tr>
            <td><c:out value="${SupplyBean.quantity}" /></td>
            <td><c:out value="${SupplyBean.description}" /></td>
            <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" /></td>
            <td><c:out value="${SupplyBean.vendor}" /></td>
            <td><c:if test="${SupplyBean.suppmatCode=='1'}" >
                      Supplies & Materials
                    </c:if>
                    <c:if test="${SupplyBean.suppmatCode=='2'}" >
                      Equipment
                    </c:if></td>
          </tr>
          <tr>
            <td width="20%"><b>AmtRequested</b></td><td width="20%"><b>AmtApproved</b></td>
            <td colspan="2"><b>ActualExp</b></td>
            <td width="20%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + SupplyBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + SupplyBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + SupplyBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + SupplyBean.expapproved}" />          
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td colspan="2"><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          
            <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr><td colspan="5" height="10" /></tr>
        </c:if>
        </logic:iterate></logic:present>
      </table>
    </td>
  </tr>
  <tr>
    <td height="20" />
  </tr>
    
    
  <tr>
    <th>Travel Expenses<hr/></th>
  </tr>
  <tr>
    <td>         
      <table width="100%">
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" >
        <c:if test="${thisGrant.fycode +2==TravelBean.fycode}">      
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
            <td width="25%"><b>AmtRequested</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ActualExp</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
             <%-- calculate the total for (grant amounts) in benefits expenses --%>
            <c:set var="GrantReq" value="${GrantReq + TravelBean.grantrequest}" />
            <c:set var="AmtAppr" value="${AmtAppr + TravelBean.amountapproved}" />
            <c:set var="ExpSubm" value="${ExpSubm + TravelBean.expsubmitted}" />
            <c:set var="ExpAppr" value="${ExpAppr + TravelBean.expapproved}" />
            <%-- continue to output grant amounts in form of currency --%>
            <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
          </tr>
          <tr><td colspan="4" height="10" /></tr>
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
          <td><b>AmtRequested</b></td>
          <td><b>AmtApproved</b></td>
          <td><b>ActualExp</b></td>
          <td><b>ExpApproved</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${GrantReq}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${AmtAppr}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpSubm}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${ExpAppr}" type="currency" maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  <tr>
    <td height="30" />
  </tr>  
  </c:if> <%--   end of FY2013-14 year 3 totals --%>  
   
   
   <tr>    
     <td>
      <table width="100%" summary="for layout only" >
        <tr bgcolor="Silver"><th colspan="4">Grand Total for All Years</th></tr>
        <tr>
          <td><b>AmtRequested</b></td>
          <td><b>AmtApproved</b></td>
          <td><b>ActualExp</b></td>
          <td><b>ExpApproved</b></td>
        </tr>
        <tr>
          <td><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency"  maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency"  maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency"  maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency"  maxFractionDigits="0" /></td>
        </tr>
      </table>
   </td>
  </tr>
  </table>    
  </font>
  <br/><br/> 
  
  </body>
</html>
</pd4ml:transform>