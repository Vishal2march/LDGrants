<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>LGRMIF Project Budget</title>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css"/>
  </head>
  <body>
  
   <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Local Government Records Management Improvement Fund (LGRMIF) 
          <br/>Project Budget
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /> -<c:out value="${thisGrant.dorisname}" /></td>
    </tr>
  </table>
  <br/><br/>
  
  
  <table align="center" width="95%" summary="for layout only" >  
  <table  width="90%" style="margin-left:2%">
    <tr>
      <th colspan="6">Professional Staff Expenses</th>
    </tr>
    
    <tr>
      <td colspan="6">            
        <logic:present name="budgetBean" property="allPersRecords" >
        <logic:iterate name="budgetBean" property="allPersRecords" id="PersonalBean" > 
          <table width="100%" class="boxtype">
          <tr>
            <td><b>Name</b></td><td><b>Position/Title</b></td>
            <td><b>Rate of Pay</b></td><td><b>Hours Worked</b></td>
            <td ><b>ExpSubmitted</b></td>
           
          </tr>
          <tr>
            <td><c:out value="${PersonalBean.name}" /></td>
            <td><c:out value="${PersonalBean.title}" /></td>        
            <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency" /></td>
            <td><c:out value="${PersonalBean.fte}" /></td>  
            <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
                    
          </tr>
          <tr>
            <td width="10%"><b>Inst'l Contrib.</b></td>
            <td width="10%"><b>AmtRequested</b></td>
            <td width="10%"><b>AmtAwarded</b></td>
             <td width="10%"><b>AmtAmended</b></td>
             
            <td width="10%"><b>ExpApproved</b></td>
      
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
           
          </tr>
          </table>
        </logic:iterate>
        </logic:present>
    </td>
  </tr>   
  </table>
   <table  width="90%" style="margin-left:2%">
  <logic:notEmpty name="budgetBean" property="allPersRecords">

    <tr>
      <td colspan="6"><b>Professional Staff Totals</b></td>
    </tr>
    <tr>
      <td width="10%">Inst Contrib.</td>
      <td width="10%">AmtRequested</td>
      <td width="10%">AmtAwarded</td>
      <td width="10%">AmtAmended</td>
      <td width="10%">ExpSubmitted</td>
      <td width="10%">ExpApproved</td>

    </tr>
    <tr>
      <td>0</td>
      <td><fmt:formatNumber value="${totalsBean.proffAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.proffAmtAppr}" type="currency" maxFractionDigits="0"/></td>
       <td><fmt:formatNumber value="${totalsBean.proffAmtAmend}" type="currency" maxFractionDigits="0"/></td> 
      <td><fmt:formatNumber value="${totalsBean.proffExpSub}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.proffExpAppr}" type="currency" maxFractionDigits="0"/></td>
    </tr>

  </logic:notEmpty>
    </table>
  <tr>
    <td height="40" />
  </tr>
  
  
   <table  width="90%" style="margin-left:2%"> 
  <tr>
      <th colspan="6">Support Staff Expenses</th>
    </tr>
    
    <tr>
      <td colspan="6">            
        <logic:present name="budgetBean" property="allSupportRecords" >
        <logic:iterate name="budgetBean" property="allSupportRecords" id="PersonalBean" > 
          <table width="90%" class="boxtype">
          <tr>
            <td><b>Name</b></td><td><b>Position/Title</b></td>
            <td><b>Rate of Pay</b></td><td><b>Hours Worked</b></td>
            <td><b>ExpSubmitted</b></td>
          </tr>
          <tr>
            <td><c:out value="${PersonalBean.name}" /></td>
            <td><c:out value="${PersonalBean.title}" /></td>        
            <td><fmt:formatNumber value="${PersonalBean.salaryf}" type="currency" /></td>
            <td><c:out value="${PersonalBean.fte}" /></td>
            <td><fmt:formatNumber value="${PersonalBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>            
          </tr>
          <tr>
            <td width="10%"><b>Inst'l Contrib.</b></td>
            <td width="10%"><b>AmtRequested</b></td><td width="10%"><b>AmtAwarded</b></td>
            <td width="10%"><b>AmtAmended</b></td><td width="10%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${PersonalBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${PersonalBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
        </logic:iterate>
        </logic:present>
    </td>
  </tr>   
 </table> 
  <table  width="90%" style="margin-left:2%">
  <logic:notEmpty name="budgetBean" property="allSupportRecords">
    <tr>
      <td colspan="6"><b>Support Staff Totals</b></td>
    </tr>
    <tr>
      <td width="10%">Inst Contrib.</td>
      <td width="10%">AmtRequested</td>
      <td width="10%">AmtAwarded</td>
      <td width="10%">AmtAmended</td>
      <td width="10%">ExpSubmitted</td>
      <td width="10%">ExpApproved</td>
    </tr>
    <tr>
      <td>0</td>
      <td><fmt:formatNumber value="${totalsBean.profsuppAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.profsuppAmtAppr}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.profsuppAmtAmend}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.profsuppExpSub}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.profsuppExpAppr}" type="currency" maxFractionDigits="0"/></td> 
    </tr>
  </logic:notEmpty>
   </table> 
  <tr>
    <td height="40" />
  </tr>
  
  
    <table  width="90%" style="margin-left:2%">
   <tr>
     <th colspan="6">Equipment Expenses</th>
    </tr>
    <tr>
      <td colspan="6">      
        <logic:present name="budgetBean" property="allEquipRecords" >
        <logic:iterate name="budgetBean" property="allEquipRecords" id="SupplyBean" > 
          <table width="100%" class="boxtype">
          <tr>
            <td><b>Quantity</b></td><td><b>Description</b></td>
            <td><b>Unit Price</b></td><td><b>Vendor</b></td>
            <td><b>ExpSubmitted</b></td>
          </tr>
          <tr>
            <td><c:out value="${SupplyBean.quantity}" /></td>
            <td><c:out value="${SupplyBean.description}" /></td>
            <td><fmt:formatNumber value="${SupplyBean.unitprice}" type="currency" /></td>
            <td><c:out value="${SupplyBean.vendor}" /></td>
            <td><fmt:formatNumber value="${SupplyBean.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            
          </tr>
          <tr>
            <td width="10%"><b>Inst'l Contrib.</b></td>
            <td width="10%"><b>AmtRequested</b></td><td width="10%"><b>AmtAwarded</b></td>
            <td width="10%"><b>AmtAmended</b></td><td width="10%"><b>ExpApproved</b></td>
          </tr>  
          <tr>
            <td><fmt:formatNumber value="${SupplyBean.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.amtamended}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${SupplyBean.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          </table>
        </logic:iterate>
        </logic:present>
     </td>
    </tr>
    
    <logic:notEmpty name="budgetBean" property="allEquipRecords">
      <tr>
        <td colspan="6"><b>Equipment Totals</b></td>
      </tr>
      <tr>
        <td width="10%">Inst Contrib.</td>
        <td width="10%">AmtRequested</td>
        <td width="10%">AmtAwarded</td>
        <td width="10%">AmtAmended</td>
        <td width="10%">ExpSubmitted</td>
        <td width="10%">ExpApproved</td>
        
      </tr>
      <tr>
        <td>0</td>
        <td><fmt:formatNumber value="${totalsBean.equipAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.equipAmtAppr}" type="currency" maxFractionDigits="0" /></td>
         <td><fmt:formatNumber value="${totalsBean.equipAmtAmend}" type="currency" maxFractionDigits="0" /></td>    
        <td><fmt:formatNumber value="${totalsBean.equipExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.equipExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </tr>
    </logic:notEmpty>
    </table>
    <tr>
      <td height="40" />
    </tr>
    
    
     <table  width="90%" style="margin-left:2%">
    <tr>
      <th colspan="6">Minor Remodeling</th>
    </tr>
    <tr>
      <td colspan="6">
      
      <logic:present name="budgetBean" property="allExpRecords" >
      <logic:iterate name="budgetBean" property="allExpRecords" id="OtherExpBean" >
        <table width="100%" class="boxtype">
        <tr>
          <td colspan="2"><b>Description of work to be performed</b></td>
          <td colspan="2"><b>Calculation of Cost</b></td>
          <td width="10%"><b>ExpSubmitted</b></td>
        </tr>
        <tr>
          <td colspan="2"><c:out value="${OtherExpBean.description}" /></td>
          <td colspan="2"><c:out value="${OtherExpBean.costsummary}" /></td>
          <td><fmt:formatNumber value="${OtherExpBean.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
         
        </tr>
        <tr>
          <td width="10%"><b>Inst'l Contrib.</b></td>
          <td width="10%"><b>AmtRequested</b></td><td width="10%"><b>AmtAwarded</b></td>
          <td width="10%"><b>AmtAmended</b></td><td width="10%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
          <td><fmt:formatNumber value="${OtherExpBean.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${OtherExpBean.grantrequest}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amountapproved}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.amtamended}" type="currency" maxFractionDigits="0"/></td>
          <td><fmt:formatNumber value="${OtherExpBean.expapproved}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        </table>
        </logic:iterate></logic:present>
      </td>
    </tr>
       
    <logic:notEmpty name="budgetBean" property="allExpRecords" >    
      <tr>
        <th colspan="6">Minor Remodeling Totals</th>
      </tr>
      <tr>
        <td width="10%">Inst Contrib.</td>
        <td width="10%">AmtRequested</td>
        <td width="10%">AmtAwarded</td>
        <td width="10%">AmtAmended</td>
        <td width="10%">ExpSubmitted</td>
        <td width="10%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${totalsBean.othInstCont}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.othExpAppr}" type="currency" maxFractionDigits="0" /></td>
      </tr>
    </logic:notEmpty>
     </table>   
    <tr>
      <td height="40" />
    </tr>    
  
  
  <table  width="90%" style="margin-left:2%">
    <tr>
     <th colspan="6">Purchased Services Expenses</th>
    </tr>
    <tr>
      <td colspan="6">                
        <logic:present name="budgetBean" property="allContractRecords" >
        <logic:iterate name="budgetBean" property="allContractRecords" id="ContractedBean" > 
          <table width="100%" class="boxtype">
          <tr>
            <td><b>Service Type</b></td>
            <td colspan="2"><b>Service Provider/Name of BOCES</b></td>
            <td colspan="2"><b>Calculation of Cost</b></td>
         
          </tr>
          <tr>
            <td><c:out value="${ContractedBean.servicedescr}" /></td>
            <td colspan="2"><c:out value="${ContractedBean.recipient}" /></td>
            <td colspan="2"><c:out value="${ContractedBean.servicetype}" /></td>
             
          </tr>
          <tr>
            <td width="17%"><b>Inst'l Contrib.</b></td>
            <td width="17%"><b>AmtRequested</b></td><td width="17%"><b>AmtAwarded</b></td>
            <td width="17%"><b>AmtAmeneded</b></td><td width="17%"><b>ExpSubmitted</b></td><td width="17%"><b>ExpApproved</b></td> 
          </tr>  
          <tr>          
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
        <td colspan="6"><b>Purchased Service Totals</b></td>
      </tr>
      <tr>
        <td width="10%">Inst Contrib.</td>
        <td width="10%">AmtRequested</td>
        <td width="10%">AmtAwarded</td>
        <td width="10%">AmtAmended</td>
        <td width="10%">ExpSubmitted</td>
        <td width="10%">ExpApproved</td>
      </tr>
      <tr>
        <td>0</td>
        <td><fmt:formatNumber value="${totalsBean.purchAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.purchAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.purchAmtAmend}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.purchExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.purchExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </tr>
    </logic:notEmpty>
    </table>
    <tr>
      <td height="40" />
    </tr>       
  
  
    <table  width="90%" style="margin-left:2%">
    <tr>
     <th colspan="6">Purchased BOCES Services Expenses</th>
    </tr>
    <tr>
      <td colspan="6">                
        <logic:present name="budgetBean" property="allContBocesRecords" >
        <logic:iterate name="budgetBean" property="allContBocesRecords" id="ContractedBean" > 
          <table width="100%" class="boxtype">
          <tr>
            <td><b>Service Type</b></td><td colspan="2"><b>Service Provider/Name of BOCES</b></td>
            <td colspan="2"><b>Calculation of Cost</b></td>
          </tr>
          <tr>
            <td><c:out value="${ContractedBean.servicedescr}" /></td>
            <td colspan="2"><c:out value="${ContractedBean.recipient}" /></td>
            <td colspan="2"><c:out value="${ContractedBean.servicetype}" /></td>
            
          </tr>
          <tr>
            <td width="17%"><b>Inst'l Contrib.</b></td>
            <td width="17%"><b>AmtRequested</b></td><td width="17%"><b>AmtAwarded</b></td>
            <td width="17%"><b>AmtAmended</b></td>
            <td><b>ExpSubmitted</b></td><td width="17%"><b>ExpApproved</b></td>
          </tr>  
          <tr>          
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
        
    <logic:notEmpty name="budgetBean" property="allContBocesRecords">
      <tr>
        <td colspan="6"><b>Purchased BOCES Service Totals</b></td>
      </tr>
      <tr>
        <td width="10%">Inst Contrib.</td>
        <td width="10%">AmtRequested</td>
        <td width="10%">AmtAwarded</td>
        <td width="10%">AmtAmended</td>
        <td width="10%">ExpSubmitted</td>
        <td width="10%">ExpApproved</td>
      </tr>
      <tr>
        <td>0</td>
        <td><fmt:formatNumber value="${totalsBean.bocesAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.bocesAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.bocesAmtAmend}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.bocesExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.bocesExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </tr>
    </logic:notEmpty>
    </table>
    <tr>
      <td height="40" />
    </tr>       
    
        <table width="90%" style="margin-left:2%">
    <tr>
     <th colspan="6">Supplies, Materials Expenses</th>
    </tr>
    <tr>
      <td colspan="6">      
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
            <td width="16%"><b>Inst'l Contrib.</b></td>
            <td width="16%"><b>AmtRequested</b></td><td width="16%"><b>AmtAwarded</b></td>
           <td width="16%"><b>AmtAmended</b></td> <td width="16%"><b>ExpSubmitted</b></td><td width="16%"><b>ExpApproved</b></td>
            
          </tr>  
          <tr>
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
        <td colspan="6"><b>Supplies, Materials Totals</b></td>
      </tr>
      <tr>
        <td width="16%">Inst Contrib.</td>
        <td width="16%">AmtRequested</td>
        <td width="16%">AmtAwarded</td>
        <td width="16%">AmtAmended</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
        
      </tr>
      <tr>
        <td>0</td>
        <td><fmt:formatNumber value="${totalsBean.supplyAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.supplyAmtAppr}" type="currency" maxFractionDigits="0" /></td>
         <td><fmt:formatNumber value="${totalsBean.supplyAmtAmend}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.supplyExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.supplyExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </tr>
   
    </logic:notEmpty>
       </table>
    <tr>
      <td height="40" />
    </tr>
  
  <table  width="90%" style="margin-left:2%"> 
  <tr>
   <th colspan="6">Travel Expenses</th>
  </tr>
  <tr>
    <td colspan="6">               
      <logic:present name="budgetBean" property="allTravelRecords" >
      <logic:iterate name="budgetBean" property="allTravelRecords" id="TravelBean" >
        <table width="100%" class="boxtype">
        <tr>
          <td><b>Description</b></td>
          <td colspan="2"><b>Purpose</b></td>
          <td colspan="2"><b>Calculation of cost</b></td>
        </tr>
        <tr>
          <td><c:out value="${TravelBean.description}" /></td>
          <td colspan="2"><c:out value="${TravelBean.purpose}" /></td>
          <td colspan="2"><c:out value="${TravelBean.costsummary}" /></td>
        </tr>
        <tr>
          <td width="10%"><b>Inst'l Contrib.</b></td>
          <td width="10%"><b>AmtRequested</b></td><td width="10%"><b>AmtAwarded</b></td>
          <td width="10%"><b>AmtAmended</b></td>
          <td width="10%"><b>ExpSubmitted</b></td><td width="10%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
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
    <td colspan="6"><b>Travel Expense Totals</b></td>
  </tr>
  <tr>
    <td width="10%">Inst Contrib.</td>
    <td width="10%">AmtRequested</td>
    <td width="10%">AmtAwarded</td>
    <td width="10%">AmtAmended</td>    
    <td width="10%">ExpSubmitted</td>
    <td width="10%">ExpApproved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.travInstCont}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.travAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  </logic:notEmpty>
  </table>
  <tr>
    <td height="40" />
  </tr>  
  
  <table  width="90%" style="margin-left:2%"> 
  <tr>
    <th colspan="6">Employee Benefit Expenses</th>
  </tr>
  <tr>
    <td colspan="6">
      <logic:present name="budgetBean" property="allBenRecords" >
      <logic:iterate name="budgetBean" property="allBenRecords" id="BenefitsBean" > 
        <table width="100%" class="boxtype">
        <tr>
          <td colspan="3"><b>Name</b></td>
          <td colspan="2"><b>Benefits Percentage</b></td>
        </tr>
        <tr>
          <td colspan="3"><c:out value="${BenefitsBean.name}" /></td>
          <td colspan="2"><fmt:formatNumber value="${BenefitsBean.benpercent}"/></td>
        </tr>
        <tr>
          <td width="10%"><b>Inst'l Contrib.</b></td>
          <td width="10%"><b>AmtRequested</b></td><td width="10%"><b>AmtAwarded</b></td>
          <td width="10%"><b>AmtAmended</b></td>
          <td width="10%"><b>ExpSubmitted</b></td><td width="10%"><b>ExpApproved</b></td>
        </tr>  
        <tr>
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
        <td colspan="6"><b>Employee Benefits Totals</b></td>
      </tr>
      <tr>
        <td width="10%">Inst Contrib.</td>
        <td width="10%">AmtRequested</td>
        <td width="10%">AmtAwarded</td>
        <td width="10%">AmtAmended</td>
        <td width="10%">ExpSubmitted</td>
        <td width="10%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${totalsBean.benInstCont}" type="currency" maxFractionDigits="0"/></td>
        <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.benAmtAmend}" type="currency" maxFractionDigits="0" /></td>       
        <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${totalsBean.benExpAppr}" type="currency" maxFractionDigits="0" /></td>    
      </tr>
    </logic:notEmpty>    
    </table>
    <tr>
      <td height="40" />
    </tr>  
    
    <table  width="90%" style="margin-left:2%"> 
    <tr bgcolor="Silver"><th colspan="6">Grand Total</th></tr>
    <tr>
      <td width="10%"><b>Inst Contrib.</b></td>
      <td width="10%"><b>AmtRequested</b></td>
      <td width="10%"><b>AmtAwarded</b></td>
      <td width="10%"><b>AmtAmended</b></td>
      <td width="10%"><b>ExpSubmitted</b></td>
      <td width="10%"><b>ExpApproved</b></td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency"  maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency"  maxFractionDigits="0" /></td>
    </tr>
    </table>
  </table>    
  </body>
</html>
