<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
    
  <!-- EQUIPMENT-->   
  
<p class="bdgtitle"><b>Project Budget<br/>
    Equipment (Code 20)</b><br/>
    List all equipment that has a unit cost of $5,000 or more that will be purchased for use
    during the project.  Equipment items under $5,000 should be budgeted under "Supplies and 
    Materials."  Include cost estimates, bids, or other supporting data as an attachment.
    All equipment to be purchased should be described in the 'Budget Narrative'.
    <br/><br/>
    Equipment purchased in this category pertains to equipment purchased by the library, and
    not a contractor or vendor.  Equipment purchased by a contractor/vendor should be 
    included in the Purchased Services category.<br/>
    <font color="blue">* Cost is the Cost of project for which funding is being requested.</font></p>


  <table width="100%" summary="for layout only">
    <tr>  
      <td class="dibgt">
      <a href="AdminBudgetSelect?tab=4&p=dasny">Purchased Services</a></td>
      
      <td class="dibgt">
      <a href="AdminBudgetSelect?tab=5&p=dasny">Supplies & Materials</a></td> 
      
      <td class="dibgtselect">Equipment</td> 
    </tr>
  </table>
  <br/>

  
  <html:form action="/displayDasnyBudget"> 
  <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
  <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
      
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td>Quantity</td>
        <td>Description</td>
        <td>UnitPrice</td>
        <td>Vendor</td>
        <td>Type</td>     
      </tr>          
      <tr >
        <td><c:out value="${supplyItem.quantity}" /></td>
        <td><c:out value="${supplyItem.description}" /></td>            
        <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
        <td><c:out value="${supplyItem.vendor}" /></td>
        <td><c:if test="${supplyItem.suppmatCode=='1'}" >
              Supplies & Materials
            </c:if>
            <c:if test="${supplyItem.suppmatCode=='2'}" >
              Equipment
            </c:if>            
        </td>
      </tr>
      <tr>
        <td width="20%">Cost<font color="blue">*</font></td>
        <td width="20%">AmtApproved</td>
        <td width="20%">ExpSubmitted</td>
        <td width="20%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0"/></td>
        <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
        <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0"/></td> 
     </tr>         
    </table>  
   
    </logic:iterate>
    </logic:present>   
   </html:form>


 <table width="80%" summary="for layout only">
  <tr>
    <th colspan="2">Project Costs/Funding</th>
  </tr>
  <tr>
      <td>1. Cost of Project for Which Funding is Being Requested:</td>
      <td><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
      <td>2. Amount of Public Library Construction Program Funds requested for this project (cannot be more than
            50% of the amounted in question #1 above :</td>
      <td><fmt:formatNumber value="${totalsBean.requestCost}" type="currency" maxFractionDigits="0"/></td>
  </tr>
  <tr>
      <td>3. Maximum Award/Recommendation Amount (50% of question #1 above):</td>
      <td><fmt:formatNumber value="${totalsBean.maxRequestCost}" type="currency" maxFractionDigits="0"/></td>
  </tr>
  <tr>
      <td>4. Amount Recommended by System:</td>
      <td><fmt:formatNumber value="${totalsBean.sysAmtRecommended}" type="currency" maxFractionDigits="0" />  </td>
  </tr>
 </table>
 

  <br/>
  <table width="100%" summary="for layout only">
  <tr>
    <td colspan="5"><font color="blue">* Cost is the Cost of project for which funding is being requested.</font></td>
  </tr>
  <tr>
    <td colspan="5"><b>Equipment (Code 20) Totals</b></td>
  </tr>
   <tr>
    <td width="20%">Cost<font color="blue">*</font></td>
    <td width="20%"> </td> 
    <td width="20%">Amount Approved</td>
    <td width="20%">Expense Submitted</td>
    <td width="20%">Expense Approved</td>
  </tr>
 <tr>
    <td><fmt:formatNumber value="${totalsBean.equipAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td> </td>
    <td><fmt:formatNumber value="${totalsBean.equipAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.equipExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.equipExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>  
  <tr>
    <td height="15" colspan="5"></td>
  </tr>
  <tr>
    <td colspan="5"><b>Total for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Cost<font color="blue">*</font></td>
    <td>Max up to 75% Award</td> 
    <td>Amount Approved</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.maxRequestCost}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
     <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
  </tr>  
  </table>
  
  
  </body>
</html>