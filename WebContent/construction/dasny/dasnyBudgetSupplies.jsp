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
  
  
  <!-- SuPPLIES MATERIALS-->   
  
<p class="bdgtitle"><b>Project Budget<br/>
    Supplies & Materials (Code 45)</b><br/>
    List all supplies and materials to be purchased for use during the project.  Do not 
    include supplies to be purchased by your vendor--the vendor's cost estimate will include 
    the cost of materials as well as labor.  Equipment items under $5,000 should be budgeted 
    under "Supplies and Materials." <br/><br/>
    <font color="blue">* Cost is the Cost of project for which funding is being requested.</font>
    </p>


  <table width="100%" summary="for layout only">
    <tr>  
      <td class="dibgt">
      <a href="AdminBudgetSelect?tab=4&p=dasny">Purchased Services</a></td>
      
      <td class="dibgtselect">Supplies & Materials</td>        
      
      <td class="dibgt">
      <a href="AdminBudgetSelect?tab=6&p=dasny">Equipment</a></td>
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
        <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
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


  <br/>
  <table width="100%" summary="for layout only">
  <tr>
    <td colspan="5"><font color="blue">* Cost is the Cost of project for which funding is being requested.</font></td>
  </tr>
  <tr>
    <td colspan="5"><b>Supplies & Materials (Code 45) Totals</b></td>
  </tr>
  <tr>
    <td width="20%">Cost<font color="blue">*</font></td>
    <td width="20%"> </td> 
    <td width="20%">Amount Approved</td>
    <td width="20%">Expense Submitted</td>
    <td width="20%">Expense Approved</td>
  </tr>
  <tr>
     <td><fmt:formatNumber value="${totalsBean.supplyAmtReq}" type="currency" maxFractionDigits="0" /></td>
     <td> </td>
     <td><fmt:formatNumber value="${totalsBean.supplyAmtAppr}" type="currency" maxFractionDigits="0" /></td>
     <td><fmt:formatNumber value="${totalsBean.supplyExpSub}" type="currency" maxFractionDigits="0" /></td>
     <td><fmt:formatNumber value="${totalsBean.supplyExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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