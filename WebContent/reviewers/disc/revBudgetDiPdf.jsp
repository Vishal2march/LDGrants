<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="400" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Discretionary Grant Budget</title>
  </head>
  <body>
  
  <font size="1">
   <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Conservation/Preservation Program - Discretionary Grants
          <br/>Project Budget
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
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
      <th colspan="4">Personal Service Expenses<hr/></th>
    </tr>
    <tr>
      <td colspan="4">            
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
          <table width="100%">
          <tr>
            <td width="20%"><b>Name</b></td><td width="20%"><b>Title</b></td>
            <td width="20%"><b>Salary/Wage</b></td>
            <td width="20%"><b>FTE/Hours</b></td><td width="20%"><b>Type</b></td>
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
              </c:if></td>
          </tr>
          <tr>
            <td><b>Proj Total</b></td><td><b>Inst'l Contrib.</b></td>
            <td><b>AmtRequested</b></td>
            <td><b>ExpSubmitted</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${PersonalBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
          <hr/>
        </logic:iterate></logic:present>     
    </td>
  </tr>   
  
  <tr>
    <td colspan="4" ><b>Personal Service Totals</b></td>
  </tr>
  <tr>
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>AmtRequested</td>
    <td>ExpSubmitted</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.perProjTot}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perInstCont}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perExpSub}" type="currency" maxFractionDigits="0"/></td>
  </tr>
  <tr>
    <td height="40" />
  </tr>
    
    
  <tr>
    <th colspan="4">Employee Benefit Expenses<hr/></th>
  </tr>
  <tr>
    <td colspan="4">
      <logic:present name="budgetBean" property="allBenRecords" >
      <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <table width="100%">
        <tr>
          <td colspan="3"><b>Name</b></td>
        </tr>
        <tr>
          <td colspan="3"><c:out value="${BenefitsBean.name}" /></td>
        </tr>
        <tr>
          <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
          <td width="25%"><b>AmtRequested</b></td>
          <td width="25%"><b>ExpSubmitted</b></td>
        </tr>  
        <tr>
          <td><fmt:formatNumber value="${BenefitsBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${BenefitsBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        </table>
        <hr/>
      </logic:iterate></logic:present>
     </td>
    </tr>    
    
    <tr>
      <td colspan="4"><b>Employee Benefits Totals</b></td>
    </tr>
    <tr>
      <td width="25%">Project Total</td>
      <td width="25%">Inst Contrib.</td>
      <td width="25%">AmtRequested</td>
      <td width="25%">ExpSubmitted</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.benProjTot}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.benInstCont}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="40" />
    </tr>
        
    
    <tr>
     <th colspan="4">Contracted Services Expenses<hr/></th>
    </tr>
    <tr>
      <td colspan="4">                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" >
          <table width="100%">
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
            <td><fmt:formatNumber value="${ContractedBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
          <hr/>
        </logic:iterate></logic:present>
     </td>
    </tr>    
   
    <tr>
      <td colspan="4"><b>Contracted Service Totals</b></td>
    </tr>
    <tr>
      <td width="25%">Project Total</td>
      <td width="25%">Inst Contrib.</td>
      <td width="25%">AmtRequested</td>
      <td width="25%">ExpSubmitted</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.conProjTot}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.conInstCont}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="40" />
    </tr>       
  
  
    <tr>
     <th colspan="4">Supplies, Materials and Equipment Expenses<hr/></th>
    </tr>
    <tr>
      <td colspan="4">      
        <logic:present name="budgetBean" property="allSupplyRecords" >
        <logic:iterate name="budgetBean" property="allSupplyRecords" id="SupplyBean" >
          <table width="100%">
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
            <td width="25%"><b>Proj Total</b></td><td width="25%"><b>Inst'l Contrib.</b></td>
            <td width="25%"><b>AmtRequested</b></td>
            <td width="25%"><b>ExpSubmitted</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${SupplyBean.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
          <hr/>
        </logic:iterate></logic:present>     
     </td>
    </tr>
   
    <tr>
      <td colspan="4"><b>Supplies, Materials, Equipment Totals</b></td>
    </tr>
    <tr>
      <td width="25%">Project Total</td>
      <td width="25%">Inst Contrib.</td>
      <td width="25%">AmtRequested</td>
      <td width="25%">ExpSubmitted</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.suppProjTot}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.suppInstCont}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.suppAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.suppExpSub}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="40" />
    </tr>
    
    
    <tr>
     <th colspan="4">Travel Expenses<hr/></th>
    </tr>
    <tr>
      <td colspan="4">               
        <logic:present name="budgetBean" property="allTravelRecords" >
        <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" > 
          <table width="100%" class="boxtype">
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
            <td><fmt:formatNumber value="${TravelBean.projtotal}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.instcont}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${TravelBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
          </tr>
          </table>
          <hr/>
        </logic:iterate></logic:present>
     </td>
    </tr>     
   
    <tr>
      <td colspan="4"><b>Travel Expense Totals</b></td>
    </tr>
    <tr>
      <td width="25%">Project Total</td>
      <td width="25%">Inst Contrib.</td>
      <td width="25%">AmtRequested</td>
      <td width="25%">ExpSubmitted</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.travProjTot}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.travInstCont}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="40" />
    </tr>  
    
    
    <tr bgcolor="Silver"><th colspan="4">Grand Total</th></tr>
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
  </font>
  
  
  </body>
</html>


</pd4ml:transform>