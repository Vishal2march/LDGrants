<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  
  <p class="bdgtitle"><b>Project Budget<br/>
  VI. Travel Expenses</b><br/>
  List project expenses that relate to travel. 
  All expenses listed in this section must be fully described in the Project Description.
 </p>

  <table width="100%" summary="for layout only">
    <tr>
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=1&p=di">I. Personal Services</a></td>
      
      <td class="adminbgt">        
       <a href="AdminBudgetSelect?tab=2&p=di">II. Employee Benefits</a></td>        
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=3&p=di">III. Contracted Services</a></td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=4&p=di">IV. Supplies Materials & Equipment</a></td>
      
      <td class="adminbgtselect">V. Travel Expenses</td>
    </tr>
  </table><br/>
    
  <c:if test="${totalsBean.warning =='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.diMessage}" /></p>
  </c:if>
  
  <c:if test="${fyTotal.warning=='true'}">
    <p align="center" class="error"><c:out value="${fyTotal.diTotApprMessage}" /> </p>
  </c:if>


<form method="POST" action="AddBudgetItem">    
    <p>
      <input type="submit" name="btn" value="Add"/>       
      Save any changes first before adding a new record.
      <INPUT type="hidden" name="tab" value="6">
      <INPUT type="hidden" name="p" value="admindi" /></p>
  </form>
  
  


<html:form action="/saveDiAdminBudget">

  <INPUT type="hidden" name="currtab" value="6"><input type="HIDDEN" name="p" value="di"/>
    
    <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" > 
    
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="diAdminNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="6" />
        <c:param name="id" value="${travelItem.id}" />
        <c:param name="p" value="admindi" />
    </c:url>
    
       
    <table class="boxtype" width="100%" align="center" summary="for layout only">
      <tr>
        <td colspan="2">Description</td>
        <td colspan="2">Purpose</td>
        <td colspan="2">Calculation of cost</td>
      </tr>      
      <tr>
        <td colspan="2"><html:text name="travelItem" property="description" indexed="true" /></td>
        <td colspan="2"><html:text name="travelItem" property="purpose" indexed="true" /></td>
        <td colspan="2"><html:text name="travelItem" property="costsummary" indexed="true" /></td>
      </tr>      
      
      <tr>
        <td colspan="2">Dates of Travel</td>    
        <td colspan="2">Name of Traveler</td>
        <td colspan="2">Check/Journal Entry#</td>
      </tr> 
      <tr>
        <td colspan="2"><html:text name="travelItem" property="travelPeriod" indexed="true" /></td>
        <td colspan="2"><html:text name="travelItem" property="travelerName" indexed="true" /></td>
        <td colspan="2"><html:text name="travelItem" property="journalEntry" indexed="true" /></td>
      </tr>   
      
      <tr>
        <td width="14%">ProjTotal</td><td width="14%">Inst Contrib.</td>
        <td width="14%">AmtRequested</td><td width="14%">AmtApproved</td>
        <td width="14%">AmtAmended</td>
        <td width="14%">ExpSubmitted</td><td width="14%">ExpApproved</td>
      </tr>
      <tr >
        <c:choose >
        <c:when test="${lduser.admindisc=='approve'}" >
          <td><fmt:formatNumber value="${travelItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><html:text name="travelItem" property="instcontStr" indexed="true" /></td>
          <td><html:text name="travelItem" property="grantrequestStr" indexed="true"/></td>
          <td><html:text name="travelItem" property="amountapprovedStr" indexed="true" /></td>
          <td><html:text name="travelItem" property="amtamendedStr" indexed="true" /></td>
          <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
          <td><html:text name="travelItem" property="expapprovedStr" indexed="true" />
              <html:hidden name="travelItem" property="id" indexed="true" /></td>
        </c:when>
        <c:otherwise >
          <td><fmt:formatNumber value="${travelItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${travelItem.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><b><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><b><fmt:formatNumber value="${travelItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
          <td><b><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </c:otherwise>
        </c:choose>      
      </tr>    
      
      <tr>
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>
    
 </logic:iterate></logic:present>
 
 <p><a href="diAdminNav.do?item=fs10a">FS10A Record - Budget Amendment</a></p>      

  <c:if test="${lduser.admindisc=='approve'}" >
    <p align="center">
      <input type="SUBMIT" name="btn" value="Copy Amt Requested" />
      <input type="SUBMIT" name="btn" value="Copy Exp Submitted" />
      <br/><br/>
      <input type="submit" name="btn" value="Save"/></p>
  </c:if>
</html:form>


<table width="100%" align="center" summary="for layout only"> 
  <tr>
    <td colspan="7" ><b>Travel Expense Totals</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.travProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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
    <td >Amount Approved</td>
    <td>Amount Amended</td>
    <td >Expense Submitted</td>
    <td >Expense Approved</td>
  </tr>
  <tr>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
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
