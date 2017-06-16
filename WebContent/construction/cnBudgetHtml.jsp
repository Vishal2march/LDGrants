<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Construction Budget</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>
  <br/><br/>
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Public Library Construction grant program
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
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  <br/><br/>
  
  
  <table align="center" width="95%" summary="for layout only" >  
  <tr>
     <th colspan="4">Purchased Services Expenses</th>
    </tr>
    <tr>
      <td colspan="4">                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
          <table width="100%" class="boxtype">
          <tr>
            <td><b>Service Type</b></td><td><b>Consultant/Vendor</b></td>
            <td colspan="2"><b>Description</b></td>
          </tr>
          <tr>
            <td><c:out value="${ContractedBean.servicetype}" /></td>
            <td><c:out value="${ContractedBean.recipient}" /></td>
            <td colspan="2"><c:out value="${ContractedBean.servicedescr}" /></td>
          </tr>
          <tr>
            <td width="25%"><b>Cost</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ExpSubmitted</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>          
            <td><fmt:formatNumber value="${ContractedBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${ContractedBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
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
        <td colspan="4"><b>Purchased Service Totals</b></td>
      </tr>
      <tr>
        <td width="25%">Cost</td>
        <td width="25%">AmtApproved</td>
        <td width="25%">ExpSubmitted</td>
        <td width="25%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${totalsBean.purchAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.purchAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.purchExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.purchExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </tr>
    </logic:notEmpty>
    
    <tr>
      <td height="40" />
    </tr>       
  
  
  
    <tr>
     <th colspan="4">Supplies & Materials Expenses</th>
    </tr>
    <tr>
      <td colspan="4">      
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
            <td width="25%"><b>Cost</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ExpSubmitted</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
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
        <td colspan="4"><b>Supplies & Materials Totals</b></td>
      </tr>
      <tr>
        <td width="25%">Cost</td>
        <td width="25%">AmtApproved</td>
        <td width="25%">ExpSubmitted</td>
        <td width="25%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${totalsBean.supplyAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.supplyAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.supplyExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.supplyExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </tr>
    </logic:notEmpty>
    
    <tr>
      <td height="40" />
    </tr>
    
    
    <tr>
     <th colspan="4">Equipment Expenses</th>
    </tr>
    <tr>
      <td colspan="4">      
        <logic:present name="budgetBean" property="allEquipRecords" >
        <logic:iterate name="budgetBean" property="allEquipRecords" id="SupplyBean" > 
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
            <td width="25%"><b>Cost</b></td><td width="25%"><b>AmtApproved</b></td>
            <td width="25%"><b>ExpSubmitted</b></td><td width="25%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
        </logic:iterate>
        </logic:present>
     </td>
    </tr>
    
    <logic:notEmpty name="budgetBean" property="allEquipRecords">
      <tr>
        <td colspan="4"><b>Equipment Totals</b></td>
      </tr>
      <tr>
        <td width="25%">Cost</td>
        <td width="25%">AmtApproved</td>
        <td width="25%">ExpSubmitted</td>
        <td width="25%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${totalsBean.equipAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.equipAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.equipExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.equipExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </tr>
    </logic:notEmpty>
    
    <tr>
      <td height="40" />
    </tr>
    
    <tr bgcolor="Silver"><th colspan="4">Grand Total</th></tr>
    <tr>
      <td width="25%"><b>Cost</b></td>
      <td width="25%"><b>AmtApproved</b></td>
      <td width="25%"><b>ExpSubmitted</b></td>
      <td width="25%"><b>ExpApproved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency"  maxFractionDigits="0" /></td>
    </tr>    
  </table>    
  
  </body>
</html>