<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>budgetSupply</title>
    <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
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


<table width="100%" summary="for layout only">
  <tr>
    <td class="dibgt">
    <%--this link allows Jaws to skip over menu items--%>
    <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
    
    <a href="BudgetSelect?tab=1&p=staid">Personal Services</a></td>               
    
    <td class="dibgt">        
    <a href="BudgetSelect?tab=2&p=staid">Employee Benefits</a></td>        
    
    <td class="dibgt">        
    <a href="BudgetSelect?tab=3&p=staid">Contracted Services</a></td>        
    
    <td class="dibgtselect">Supplies Materials & Equipment</td>
       
    <td class="dibgt">
    <a href="BudgetSelect?tab=6&p=staid">Travel Expenses</a></td>
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent" /><br/>


  

 
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >
  
 <html:errors />
 <html:form action="/saveStaidSuppExp" >
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
        <td colspan="2">Purchase Order Date (mm/dd/yyyy)</td>    
        <td colspan="2">Check/Journal Entry#</td>
      </tr> 
      <tr>
        <td colspan="2"><html:text name="supplyItem" property="encumbranceDateStr" indexed="true" /></td>
        <td colspan="2"><html:text name="supplyItem" property="journalEntry" indexed="true" /></td>
      </tr>   
      
      
      <tr>
        <td width="14%">AmtRequested</td>
        <td width="14%">AmtApproved</td>
        <td width="14%">AmtAmended</td>
        <td width="14%">ExpSubmitted</td>
        <td width="14%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><b><fmt:formatNumber value="${supplyItem.amtamended}"  type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td> 
     </tr> 
        <html:hidden name="supplyItem" property="id" indexed="true" />
        <html:hidden name="supplyItem" property="fycode" indexed="true" />
        <html:hidden name="supplyItem" property="quantity" indexed="true" />
        <html:hidden name="supplyItem" property="description" indexed="true" />
        <html:hidden name="supplyItem" property="unitprice" indexed="true" />
        <html:hidden name="supplyItem" property="vendor" indexed="true" />
        <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
        <html:hidden name="supplyItem" property="grantrequest" indexed="true" />            
        <html:hidden name="supplyItem" property="amountapproved" indexed="true" />
        <html:hidden name="supplyItem" property="expapproved" indexed="true" />  
        <html:hidden name="supplyItem" property="amtamended" indexed="true" />   
    </table>  
   
    </logic:iterate>
    </logic:present>
    
    
    <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
          <p align="center"><input type="BUTTON" value="Save Supply Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center"><input type="HIDDEN" name="currtab" value="4" />
            <html:submit value="Save Supply Expenses" /></p>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty>
    </html:form>
    

  </c:when>
  <c:otherwise >
  
    <form method="POST" action="AddBudgetItem" >      
      <p><input type="submit" value="Add"/>        
         Save any changes first before adding a new record.
         <INPUT type="hidden" name="tab" value="4">
         <INPUT type="hidden" name="p" value="staid" /></p>
    </form>
    
    <% int index =0; %>
    <html:errors />
    <html:form action="/saveStaidSupply">
    <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >    
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="stateaidForms.do" var="deleteURL">
        <c:param name="i" value="confirmbdgtdelete" />
        <c:param name="tab" value="4" />
        <c:param name="id" value="${supplyItem.id}" />
        <c:param name="p" value="staid" />
    </c:url>
    
    <table width="100%" class="boxtype" summary="for layout only">
      <tr>
        <td>Quantity</td>
        <td>Description</td>
        <td>UnitPrice</td>
        <td>Quantity*Price</td>
        <td>Vendor</td>
        <td>Type</td>     
      </tr>
      
      <tr>
        <td><html:text name="supplyItem" property="quantity" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
        <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>        
        <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
        <td><html:text name="supplyItem" property="cost" indexed="true" readonly="true" style="background-color:silver"/></td>
        
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
        <td width="16%">AmtRequested</td>
        <td width="16%">AmtApproved</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr>
      <tr>
        <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
        <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>
      <tr>
        <html:hidden name="supplyItem" property="id" indexed="true" />
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>  
    <% index++; %>
    
  </logic:iterate>
  </logic:present>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
    <c:choose >
    <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Supply Expenses" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="4" />
          <html:submit value="Save Supply Expenses" /></p>
      </c:otherwise>
      </c:choose>
   </logic:notEmpty>   
  </html:form>
  
  </c:otherwise>
  </c:choose>
  
  


 <table width="100%" summary="for layout only">
  <tr>
    <td colspan="5"><b>Supplies, Materials, Equipment Totals</b></td>
  </tr>
   <tr>
    <td width="14%">Amount Requested</td>
    <td width="14%">Amount Approved</td>
    <td width="14%">Expense Submitted</td>
    <td width="14%">Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.suppAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="5"></td>
  </tr>
  <tr>
    <td colspan="5"><b>Grand Totals for all Budget Codes</b></td>
  </tr>
  <tr>   
    <td>Amount Requested</td>
    <td>Amount Approved</td>
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
  <tr>
    <td height="15" colspan="5"></td>
  </tr>
  <tr>
    <td><b>Total Appropriation</b></td>
    <td><b><fmt:formatNumber value="${thisGrant.ldacAppropAmt}" type="currency" maxFractionDigits="0" /></b></td>
  </tr>
</table>
  
  </body>
</html>