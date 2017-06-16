<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>budgetAdminSupply</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
    Supplies, Materials & Equipment</b><br/>
    List all supplies and materials to be purchased for use during the project.  Do not 
    include supplies to be purchased by your vendor--the vendor's cost estimate will include 
    the cost of materials as well as labor<br/><br/>
    List all equipment that has a unit cost of $5,000 or more that will be purchased for use
    during the project.  Equipment items under $5,000 should be budgeted under "Supplies and 
    Materials."  Include cost estimates, bids, or other supporting data as an attachment.</p>
    <br/><br/>
    
    
  <table width="100%" summary="for layout only">
  <tr>
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=1&p=staid">Personal Services</a></td>
    
    <td class="adminbgt">        
     <a href="AdminBudgetSelect?tab=2&p=staid">Employee Benefits</a></td>        
    
    <td class="adminbgt">        
    <a href="AdminBudgetSelect?tab=3&p=staid">Contracted Services</a></td>
   
    <td class="adminbgtselect">Supplies Materials & Equipment</td>
     
    <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=6&p=staid">Travel Expenses</a></td>
  </tr>
</table><br/><br/>
  
  
  

<html:form action="/saveStaidAdminBudget">

  <INPUT type="hidden" name="currtab" value="4"><input type="HIDDEN" name="p" value="staid"/>
    
    <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
      
    <table class="boxtype" align="center" width="100%" summary="for layout only">
      <tr>
        <td width="20%">Quantity</td><td width="20%">Description</td><td width="20%">UnitPrice</td>
        <td width="20%">Vendor</td><td width="20%">Type</td>     
      </tr>      
      <tr >
        <td><c:out value="${supplyItem.quantity}" /></td>
        <td><c:out value="${supplyItem.description}" /></td>
        <td><fmt:formatNumber value="${supplyItem.unitprice}" /></td>
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
        <td colspan="2">Purchase Order Date (mm/dd/yyyy)</td>    
        <td colspan="2">Check/Journal Entry#</td>
      </tr> 
      <tr>
        <td colspan="2"><c:out value="${supplyItem.encumbranceDateStr}" /></td>
        <td colspan="2"><c:out value="${supplyItem.journalEntry}" /></td>
      </tr>   
      <tr>
        <td>AmtRequested</td><td>AmtApproved</td><td>AmtAmended</td>
        <td>ExpSubmitted</td><td>ExpApproved</td>
      </tr>
      <tr >
        <td><b><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
        <td><html:text name="supplyItem" property="amtamendedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="supplyItem" property="expapprovedStr" indexed="true" />
            <html:hidden name="supplyItem" property="id" indexed="true" /></td>
      </tr>     
    </table>  
 </logic:iterate></logic:present>



 <table width="100%" align="center" summary="for layout only">
  <c:if test="${lduser.adminstat=='approve'}">
    <tr>
      <td colspan="2" align="center"><input type="SUBMIT" name="btn" value="Copy Amt Requested" /><br/></td>
      <td colspan="2" align="center"><input type="SUBMIT" name="btn" value="Copy Exp Submitted" /><br/></td>
    </tr>
    <tr>
      <td colspan="5" align="center"><input type="submit" name="btn" value="Save"/></td>
    </tr>
  </c:if>
</table>
</html:form>
  
  
<br/><br/>
<table width="100%" align="center" summary="for layout only">  
  <tr>
    <td colspan="5" ><b>Supplies, Materials, Equipment Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.suppAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="2"></td>
  </tr>
  <tr>
    <td colspan="5"><b>Grand Totals for all Budget Codes</b></td>
  </tr>
  <tr>   
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <c:choose >
    <c:when test="${totalsBean.warning=='true'}">
      <td class="error">
        <b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0" /></b>
      </td>
    </c:when>
    <c:otherwise >
      <td><b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b></td>
    </c:otherwise>
    </c:choose>    
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b></td>
    
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></b></td>
    
    <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b></td>
    
    <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b></td>
  </tr>
  <tr>
    <td height="15" colspan="5"></td>
  </tr>
  <tr>
    <td><b>Total Appropriation</b></td>
    <td><b><fmt:formatNumber value="${thisGrant.ldacAppropAmt}" type="currency" maxFractionDigits="0" /></b></td>
  </tr>
  <tr>
    <td height="15" colspan="5"></td>
  </tr>
  <tr>
    <td colspan="5" align="center">       
        <c:url var="backURL" value="staidAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
        <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
    </td>
  </tr>
</table>


  </body>
</html>