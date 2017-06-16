<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
    IV. Supplies, Materials & Equipment</b><br/>
    List all supplies and materials to be purchased for use during the project.  Do not 
    include supplies to be purchased by your vendor--the vendor's cost estimate will include 
    the cost of materials as well as labor<br/><br/>
    List all equipment that has a unit cost of $5,000 or more that will be purchased for use
    during the project.  Equipment items under $5,000 should be budgeted under "Supplies and 
    Materials."  Include cost estimates, bids, or other supporting data as an attachment.</p>

  
<table width="100%" summary="for layout only">
  <tr>
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=1&p=di">I. Personal Services</a></td>
    
    <td class="adminbgt">        
     <a href="AdminBudgetSelect?tab=2&p=di">II. Employee Benefits</a></td>        
    
    <td class="adminbgt">        
    <a href="AdminBudgetSelect?tab=3&p=di">III. Contracted Services</a></td>
   
    <td class="adminbgtselect">IV. Supplies Materials & Equipment</td>
    
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=6&p=di">V. Travel Expenses</a></td>
  </tr>
</table><br/>
  
  <c:if test="${totalsBean.warning =='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.diMessage}" /></p>
  </c:if>
  
  <c:if test="${fyTotal.warning=='true'}">
    <p align="center" class="error"><c:out value="${fyTotal.diTotApprMessage}" /> </p>
  </c:if>
  
  
  <form method="POST" action="AddBudgetItem" >      
      <p><input type="submit" value="Add"/>        
         Save any changes first before adding a new record.
         <INPUT type="hidden" name="tab" value="4">
         <INPUT type="hidden" name="p" value="admindi" /></p>
  </form>
  
  

<html:form action="/saveDiAdminBudget">

  <INPUT type="hidden" name="currtab" value="4"><input type="HIDDEN" name="p" value="di"/>
    
    <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
    
    
     <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="diAdminNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="4" />
        <c:param name="id" value="${supplyItem.id}" />
        <c:param name="p" value="admindi" />
    </c:url>
    
      
    <table class="boxtype" align="center" width="100%" summary="for layout only">
      <tr>
        <td>Quantity</td>
        <td>Description</td>
        <td>UnitPrice</td>
        <td>Vendor</td>
        <td>Type</td>     
      </tr>    
      <tr>
        <td><html:text name="supplyItem" property="quantity" indexed="true" /></td>
        <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>        
        <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" /></td>
        
        <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
        <td><html:select name="supplyItem" property="suppmatCode" indexed="true">
            <c:if test="${supplyItem.suppmatCode=='1'}" >
              <option value="1" selected="selected">Supplies & Materials</option>
              <option value="2">Equipment</option>  
            </c:if>
            <c:if test="${supplyItem.suppmatCode=='2'}" >
              <option value="1">Supplies & Materials</option>
              <option value="2" selected="selected">Equipment</option>
            </c:if>            
            </html:select></td>
      </tr>
      
      <tr>
        <td colspan="2">Purchase Order Date (mm/dd/yyyy)</td>    
        <td colspan="2">Check/Journal Entry#</td>
      </tr> 
       <tr>
        <td colspan="2"><html:text name="supplyItem" property="encumbranceDateStr" indexed="true" /></td>
        <td colspan="2"><html:text name="supplyItem" property="journalEntry" indexed="true" /></td>
      </tr>  
      
      <tr>
        <td width="14%">ProjTotal</td><td width="14%">Inst Contrib.</td>
        <td width="14%">AmtRequested</td><td width="14%">AmtApproved</td>
        <td width="14%">AmtAmended</td>
        <td width="14%">ExpSubmitted</td><td width="14%">ExpApproved</td>
      </tr>
      <tr >
        <c:choose >
        <c:when test="${lduser.admindisc=='approve'}">
          <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><html:text name="supplyItem" property="instcontStr" indexed="true" /></td>
          <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
          <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
          <td><html:text name="supplyItem" property="amtamendedStr" indexed="true" /></td>
          <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
          <td><html:text name="supplyItem" property="expapprovedStr" indexed="true" />
              <html:hidden name="supplyItem" property="id" indexed="true" /></td>
        </c:when>
        <c:otherwise >
          <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${supplyItem.instcont}" type="currency" maxFractionDigits="0" /></td>          
          <td><b><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><b><fmt:formatNumber value="${supplyItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
          <td><b><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </c:otherwise>
        </c:choose>
      </tr>      
      
      <tr>
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>
 
 </logic:iterate></logic:present>

 <p><a href="diAdminNav.do?item=fs10a">FS10A Record - Budget Amendment</a></p>      





  <c:if test="${lduser.admindisc=='approve'}">
    <p align="center">
      <input type="SUBMIT" name="btn" value="Copy Amt Requested" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="SUBMIT" name="btn" value="Copy Exp Submitted" />
      <br/><br/>
      <input type="submit" name="btn" value="Save"/></p>
  </c:if>
</html:form>  

 <table width="100%" align="center" summary="for layout only"> 
  <tr>
    <td colspan="7" ><b>Supplies, Materials, Equipment Totals</b></td>
  </tr>
  <tr>
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
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
  <tr>
    <td height="15" colspan="7"></td>
  </tr>
  <tr>
    <td colspan="7"><b>Grand Totals for all Budget Categories</b></td>
  </tr>
  <tr>
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td >
      <b><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td >
      <b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
     <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
  </tr>
  <tr>
    <td height="15" colspan="7"></td>
  </tr>
  <tr>
    <td colspan="3"><b>Total C/P Discretionary available for FY <fmt:formatNumber value="${fyTotal.fycode}" minIntegerDigits="2" />:</b></td>
    <td><fmt:formatNumber value="${fyTotal.amtavailable}" type="currency" maxFractionDigits="0"/></td>
  </tr>
  <tr>
    <td colspan="3"><b>Total C/P Discretionary Awarded:</b></td>
    <td><fmt:formatNumber value="${fyTotal.totAmtAppr}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
    <td colspan="3"><b>Difference:</b></td>
    <td><fmt:formatNumber value="${fyTotal.amtdifference}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
    <td height="15" colspan="7"></td>
  </tr>
  
  <tr>
    <td colspan="7" align="center">       
        <c:url var="backURL" value="diAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
        <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
    </td>
  </tr>
</table>

  
  </body>
</html>
