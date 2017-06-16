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
    <title>budgetBenefits</title>
    <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
  </head>
  <body>
  
  
  <p class="bdgtitle"><b>Project Budget<br/>
  Employee Benefits</b><br/>
  List all persons to be employed by the project as described under "Personal Services."  
  Fill in the appropriate amount of benefits to be paid for the time they will work on the project
  </p>



  <table width="100%" summary="for layout only">
    <tr>
      <td class="dibgt">
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      
      <a href="BudgetSelect?tab=1&p=staid">Personal Services</a></td>        
      
      <td class="dibgtselect">Employee Benefits</td>        
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=3&p=staid">Contracted Services</a></td>        
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=4&p=staid">Supplies Materials & Equipment</a></td>        
            
      <td class="dibgt">
      <a href="BudgetSelect?tab=6&p=staid">Travel Expenses</a></td>
    </tr>
  </table>
  <a name="bdgcontent" id="bdgcontent" /><br/>
  

  
        
   <c:choose >
   <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >
   
   <html:errors />
   <html:form action="/saveStaidExpenses" >
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td colspan="2">Name</td>   
        <td colspan="2">Benefits Percentage</td>
      </tr>      
      <tr>
        <td colspan="2"><c:out value="${benefitItem.name}" /></td>
        <td colspan="2"><fmt:formatNumber value="${benefitItem.benpercent}"/></td>
      </tr>       
      <tr>
        <td width="14%">AmtRequested</td>
        <td width="14%">AmtApproved</td>
        <td width="14%">AmtAmended</td>
        <td width="14%">ExpSubmitted</td>
        <td width="14%">ExpApproved</td>
      </tr>
      <tr>
        <td ><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td ><b><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td ><b><fmt:formatNumber value="${benefitItem.amtamended}"  type="currency" maxFractionDigits="0" /></b></td>
        <td ><html:text name="benefitItem" property="expsubmittedStr" indexed="true" /></td>
        <td ><b><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></b> </td>
      </tr>
          <html:hidden name="benefitItem" property="id" indexed="true" />
          <html:hidden name="benefitItem" property="fycode" indexed="true" />
          <html:hidden name="benefitItem" property="name" indexed="true" />
          <html:hidden name="benefitItem" property="benpercent" indexed="true" />
          <html:hidden name="benefitItem" property="grantrequest" indexed="true" />
          <html:hidden name="benefitItem" property="amountapproved" indexed="true" />
          <html:hidden name="benefitItem" property="expapproved" indexed="true" />   
          <html:hidden name="benefitItem" property="amtamended" indexed="true" />  
     </table>    
    
   </logic:iterate>
   </logic:present>
   
   
   <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Benefit Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="2" />
          <html:submit value="Save Benefit Expenses" /></p>
      </c:otherwise>
      </c:choose>
   </logic:notEmpty>
   
   </html:form>
   
   
   </c:when>
   <c:otherwise >
   
   
  <form method="POST" action="AddBudgetItem">    
    <p>
      <input type="submit" value="Add"/>
      Save any changes first before adding a new record.
      <INPUT type="hidden" name="tab" value="2">
      <INPUT type="hidden" name="p" value="staid" /></p>
  </form>
   
    <% int index =0; %>
    <html:errors />
    <html:form action="/saveStaidBenefit">
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
          
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="stateaidForms.do" var="deleteURL">
        <c:param name="i" value="confirmbdgtdelete" />
        <c:param name="tab" value="2" />
        <c:param name="id" value="${benefitItem.id}" />
        <c:param name="p" value="staid" />
    </c:url>
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td>Name</td>   
        <td>Benefits Percentage (decimal)</td>
        <td>Salary*FTE</td>
        <td>BenefitsAmt</td>
      </tr>      
      <tr>
        <td><html:text name="benefitItem" property="name" indexed="true" /></td>
        <td><html:text name="benefitItem" property="benpercentStr" indexed="true" onchange='<%= "calcBenefits(" + index + ");" %>' /></td>
        <td><html:text name="benefitItem" property="salary" indexed="true" readonly="true" style="background-color:silver" /></td>
        <td><html:text name="benefitItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
      </tr>        
      <tr>
        <td width="16%">AmtRequested</td>
        <td width="16%">AmtApproved</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr>
      <tr>
        <td><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
        <td><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>                
      <tr>
        <html:hidden name="benefitItem" property="id" indexed="true" />
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
     </tr>
     </table>  
      <% index++; %>
       
    </logic:iterate>
    </logic:present>
    
    <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Benefit Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="2" />
          <html:submit value="Save Benefit Expenses" /></p>
      </c:otherwise>
      </c:choose>    
    </logic:notEmpty>
    
  </html:form>
  
 </c:otherwise>
 </c:choose>
 
 

<table width="100%" summary="for layout only">
  <tr>
    <td colspan="5"><b>Employee Benefits Totals</b></td>
  </tr>
   <tr>
    <td width="14%">Amount Requested</td>
    <td width="14%">Amount Approved</td>
    <td width="14%">Expense Submitted</td>
    <td width="14%">Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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