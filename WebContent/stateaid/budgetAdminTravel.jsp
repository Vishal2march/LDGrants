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
    <title>budgetAdminTravel</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
  Travel Expenses</b><br/>
  List project expenses that relate to travel. 
  All expenses listed in this section must be fully described in the Project Description.
 </p> <br/><br/>
 
  
  <table width="100%" summary="for layout only">
  <tr>
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=1&p=staid">Personal Services</a></td>
    
    <td class="adminbgt">        
    <a href="AdminBudgetSelect?tab=2&p=staid">Employee Benefits</a></td>        
    
    <td class="adminbgt">        
    <a href="AdminBudgetSelect?tab=3&p=staid">Contracted Services</a></td>
    
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=4&p=staid">Supplies Materials & Equipment</a></td>
    
    <td class="adminbgtselect">Travel Expenses</td>
  </tr>
</table><br/><br/>
  
   

<html:form action="/saveStaidAdminBudget">

  <INPUT type="hidden" name="currtab" value="6"><input type="HIDDEN" name="p" value="staid"/>
    
    <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >    
    
     <table class="boxtype" align="center" width="100%" summary="for layout only">
      <tr>
        <td colspan="2">Description</td>  
        <td colspan="2">Purpose</td>
      </tr>      
      <tr>
        <td colspan="2"><c:out value="${travelItem.description}" /></td>
        <td colspan="2"><c:out value="${travelItem.purpose}" /></td>
      </tr>      
      <tr>
        <td colspan="2">Dates of Travel</td>    
        <td colspan="2">Name of Traveler</td>
        <td>Check/Journal Entry#</td>
      </tr> 
      <tr>
        <td colspan="2"><c:out value="${travelItem.travelPeriod}" /></td>
        <td colspan="2"><c:out value="${travelItem.travelerName}" /></td>
        <td><c:out value="${travelItem.journalEntry}" /></td>
      </tr>   
      <tr>
        <td width="20%">AmtRequested</td><td width="20%">AmtApproved</td>
        <td width="20%">AmtAmended</td>
        <td width="20%">ExpSubmitted</td><td width="20%">ExpApproved</td>
      </tr>
      <tr >
        <td><b><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="travelItem" property="amountapprovedStr" indexed="true" /></td>
        <td><html:text name="travelItem" property="amtamendedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${travelItem.expsubmitted}"  type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="travelItem" property="expapprovedStr" indexed="true" />
            <html:hidden name="travelItem" property="id" indexed="true" /></td>        
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
      <td colspan="5" align="center"><input type="submit" name="btn" value="Save"/></td>
    </tr>
  </c:if>
  </table>
</html:form>


  
 <br/><br/>
<table width="100%" align="center" summary="for layout only"> 
  <tr>
    <td colspan="5" ><b>Travel Expense Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
     <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>  
  <tr>
    <td height="15" colspan="4"></td>
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