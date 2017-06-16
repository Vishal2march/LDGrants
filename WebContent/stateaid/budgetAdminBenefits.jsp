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
    <title>budgetAdminBenefits</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
  Employee Benefits</b><br/>
  List all persons to be employed by the project as described under "Personal Services."  
  Fill in the appropriate amount of benefits to be paid for the time they will work on the project
  </p><br/><br/>
  
  
  
  <table width="100%" summary="for layout only">
  <tr>
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=1&p=staid">Personal Services</a></td>
    
    <td class="adminbgtselect">Employee Benefits</td>        
    
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=3&p=staid">Contracted Services</a></td>
   
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=4&p=staid">Supplies Materials & Equipment</a></td>
   
    <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=6&p=staid">Travel Expenses</a></td>
  </tr>
</table><br/><br/>

   
  

<html:form action="/saveStaidAdminBudget">

  <INPUT type="hidden" name="currtab" value="2"><input type="HIDDEN" name="p" value="staid"/>
    
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
  
    <table class="boxtype" align="center" width="100%" summary="for layout only">
      <tr>
        <td colspan="2">Name</td>   
        <td colspan="3">Benefits Percentage</td>
      </tr>      
      <tr>
        <td colspan="2"><c:out value="${benefitItem.name}" /></td>
        <td colspan="3"><fmt:formatNumber value="${benefitItem.benpercent}"/></td>
      </tr>      
      <tr >
        <td width="20%">AmtRequested</td><td width="20%">AmtApproved</td>
        <td width="20%">AmtAmended</td>
        <td width="20%">ExpSubmitted</td><td width="20%">ExpApproved</td>
      </tr>
      <tr >        
        <td><b><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="benefitItem" property="amountapprovedStr" indexed="true" /></td>
        <td><html:text name="benefitItem" property="amtamendedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="benefitItem" property="expapprovedStr" indexed="true" />
            <html:hidden name="benefitItem" property="id" indexed="true" /></td>   
      </tr>                   
     </table>  
 </logic:iterate></logic:present>
 
 

<table width="100%" align="center" summary="for layout only">
  <c:if test="${lduser.adminstat=='approve'}" >
    <tr>
      <td colspan="2" align="center"><input type="SUBMIT" name="btn" value="Copy Amt Requested" /><br/></td>
      <td colspan="2" align="center"><input type="SUBMIT" name="btn" value="Copy Exp Submitted" /><br/></td>
    </tr>
    <tr>
      <td height="10" />
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
    <td colspan="5" ><b>Employee Benefits Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtAmend}" type="currency" maxFractionDigits="0" /></td>
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
        <a href='<c:out value="${appURL}" />' >Back to Application Checklist</a>
    </td>
  </tr>
</table>
  
  </body>
</html>