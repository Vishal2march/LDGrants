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
    <title>budgetTravel</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
  Travel Expenses</b><br/>
  List project expenses that relate to travel. 
  All expenses listed in this section must be fully described in the Project Description.
 </p> 
  
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
    
    <td class="dibgt">
    <a href="BudgetSelect?tab=4&p=staid">Supplies Materials & Equipment</a></td>                
        
    <td class="dibgtselect">Travel Expenses</td>
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent" /><br/>


  
  
    
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >  
  
  <html:errors />
  <html:form action="/saveStaidExpenses" >
    <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" > 
          
     <table class="boxtype" width="100%" summary="for layout only" >
      <tr>
        <td colspan="2">Description</td>    
        <td colspan="2">Purpose</td>
        <td colspan="2">Calculation of cost</td>
      </tr>      
      <tr>
        <td colspan="2"><c:out value="${travelItem.description}" /></td>
        <td colspan="2"><c:out value="${travelItem.purpose}" /></td>
        <td colspan="2"><c:out value="${travelItem.costsummary}" /></td>
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
        <td width="14%">AmtRequested</td>
        <td width="14%">AmtApproved</td>
        <td width="14%">AmtAmended</td>
        <td width="14%">ExpSubmitted</td>
        <td width="14%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><b><fmt:formatNumber value="${travelItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>     
        <html:hidden name="travelItem" property="description" indexed="true" />
        <html:hidden name="travelItem" property="grantrequest"  indexed="true"/>           
        <html:hidden name="travelItem" property="amountapproved"  indexed="true"/> 
        <html:hidden name="travelItem" property="amtamended"  indexed="true"/> 
        <html:hidden name="travelItem" property="expapproved"  indexed="true"/>  
        <html:hidden name="travelItem" property="id" indexed="true" />       
        <html:hidden name="travelItem" property="fycode" indexed="true" />    
    </table>    
    
  </logic:iterate>
  </logic:present>
  
  
  <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Travel Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="6" />
          <html:submit value="Save Travel Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>
 </html:form>
 
  </c:when>
  <c:otherwise >
  
  <form method="POST" action="AddBudgetItem">    
    <p>
      <input type="submit" name="btn" value="Add"/>       
      Save any changes first before adding a new record.
      <INPUT type="hidden" name="tab" value="6">
      <INPUT type="hidden" name="p" value="staid" /></p>
  </form>
  
    
    <%-- INITIAL BUDGET --%>
    <html:errors />
    <html:form action="/saveStaidTravel">
    <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >
          
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="stateaidForms.do" var="deleteURL">
        <c:param name="i" value="confirmbdgtdelete" />
        <c:param name="tab" value="6" />
        <c:param name="id" value="${travelItem.id}" />
        <c:param name="p" value="staid" />
    </c:url>
        
    <table width="100%" align="center" summary="for layout only" class="boxtype" >
      <tr>
        <td>Description</td>  
        <td>Purpose</td>
        <td>Calculation of cost</td>
      </tr>      
      <tr>
        <td><html:text name="travelItem" property="description" indexed="true" /></td>
        <td><html:text name="travelItem" property="purpose" indexed="true" /></td>
        <td><html:text name="travelItem" property="costsummary" indexed="true" /></td>
      </tr>      
      <tr>
        <td width="16%">AmtRequested</td>
        <td width="16%">AmtApproved</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr>
      <tr>
        <td><html:text name="travelItem" property="grantrequestStr" indexed="true"/></td>
        <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>       
      <tr>
        <html:hidden name="travelItem" property="id" indexed="true" />    
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>
   
    </logic:iterate>
    </logic:present>
    
    <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Travel Expenses" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="6" />
          <html:submit value="Save Travel Expenses" /></p>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty> 
  </html:form>
  
  
 </c:otherwise>
 </c:choose>
 
 

 <table width="100%" align="center" summary="for layout only">
  <tr>
    <td colspan="5"><b>Travel Expense Totals</b></td>
  </tr>
   <tr>
    <td width="14%">Amount Requested</td>
    <td width="14%">Amount Approved</td>
    <td width="14%">Expense Submitted</td>
    <td width="14%">Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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