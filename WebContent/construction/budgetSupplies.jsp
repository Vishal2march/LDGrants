<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
  </head>
  <body>
  
  <!-- SuPPLIES MATERIALS --> 
 <p class="bdgtitle"><b>Project Budget<br/>
    Supplies & Materials (Code 45)</b><br/>
    List all supplies and materials to be purchased for use during the project.  Do not 
    include supplies to be purchased by your vendor--the vendor's cost estimate will include 
    the cost of materials as well as labor.  Equipment items under $5,000 should be budgeted under 
    "Supplies and Materials." <br/><br/>
    <font color="blue">* Cost is the Cost of project for which funding is being requested.</font>
   </p>


  <table width="100%" summary="for layout only">
    <tr>
      <td class="dibgt">
      <a href="BudgetSelect?tab=4&p=cn">Purchased Services</a></td>
      
      <td class="dibgtselect">Supplies & Materials</td> 
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=6&p=cn">Equipment</a></td> 
            
      <%--<td class="dibgt">
      <a href="BudgetSelect?tab=5&p=cn">III. Minor Remodeling</a></td>--%>
    </tr>
  </table>
  <br/>

 
 <!--   EXPENSES SUBMITTED -->
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >
  
 <html:errors />
 <html:form action="/saveCnSupplyExp" > 
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
        <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
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
        <html:hidden name="supplyItem" property="grantrequestStr" indexed="true" />
         <html:hidden name="supplyItem" property="unitpriceStr" indexed="true" />
    </table>  
   
    </logic:iterate>
    </logic:present>
    
    <%--removed 5/17/12 - MLT wants final expenses separate from initial budget
    <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
          <p align="center"><input type="BUTTON" value="Save Supply Records" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center">
            <input type="HIDDEN" name="currtab" value="4" />
            <input type="HIDDEN" name="i" value="5" />
            <html:submit value="Save Supply Records" /></p>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty>--%>
    </html:form>
    
  </c:when>
  <c:otherwise >
  
   <%-- THIS IS THE INITIAL BUDGET SECTION--%>
    <form method="POST" action="AddBudgetItem" >
      <p><input type="submit" value="Add"/>        
         Save any changes first before adding a new record.
         <INPUT type="hidden" name="tab" value="4">
         <INPUT type="hidden" name="t" value="5">
         <INPUT type="hidden" name="p" value="cn" /></p>
    </form>  
    
    <% int index =0; %>
    <html:errors />
    <html:form action="/saveCnSupply"> 
    <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >    
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="constructionForms.do" var="deleteURL">
        <c:param name="i" value="confirmbdgtdelete" />
        <c:param name="tab" value="4" />
        <c:param name="id" value="${supplyItem.id}" />
        <c:param name="p" value="cn5" />
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
        <td><c:if test="${supplyItem.suppmatCode=='1'}" >Supplies & Materials</c:if>
            <c:if test="${supplyItem.suppmatCode=='2'}" >Equipment</c:if>
            <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            </td>
      </tr>
      <tr>
        <td width="16%">Cost<font color="blue">*</font></td>
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
        <td>
        <html:hidden name="supplyItem" property="id" indexed="true" />
        <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>  
    <% index++; %>
    
  </logic:iterate>
  </logic:present>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
    <c:choose >
    <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Supply Records" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center">
            <input type="HIDDEN" name="currtab" value="4" />
            <input type="HIDDEN" name="i" value="5" />
            <html:submit value="Save Supply Records" /></p>
      </c:otherwise>
      </c:choose>
   </logic:notEmpty>   
  </html:form>
  
  </c:otherwise>
  </c:choose>
  
  


 <table width="100%" summary="for layout only">
  <tr>
    <td colspan="4"><font color="blue">* Cost is the Cost of project for which funding is being requested.</font></td>
  </tr>
  <tr>
    <td colspan="4"><b>Supplies & Materials (Code 45) Totals</b></td>
  </tr>
   <tr>
    <td width="25%">Cost<font color="blue">*</font></td>  <!-- amount requested -->
    <td width="25%">Amount Approved</td>
    <td width="25%">Expense Submitted</td>
    <td width="25%">Expense Approved</td>
  </tr>
  <tr>
     <td><fmt:formatNumber value="${totalsBean.supplyAmtReq}" type="currency" maxFractionDigits="0" /></td>
     <td><fmt:formatNumber value="${totalsBean.supplyAmtAppr}" type="currency" maxFractionDigits="0" /></td>
     <td><fmt:formatNumber value="${totalsBean.supplyExpSub}" type="currency" maxFractionDigits="0" /></td>
     <td><fmt:formatNumber value="${totalsBean.supplyExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="4"></td>
  </tr>
  <tr>
    <td colspan="4"><b>Total for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Cost<font color="blue">*</font></td>
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
</table>
  
  
  </body>
</html>
