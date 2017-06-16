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
    <title>budgetAdminContracted</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
  Contracted Services</b><br/>
  List all services to be purchased for the project, arranged, as appropriate, under Consultant Services or Contracted Services.  Attach cost estimates, bids, or other supporting data in an appendix.<br/>
  <br/>- Consultant Services: include professional and technical advice that will be provided by 
  individuals or groups of individuals.  Consultants are normally retained for a short period 
  to provide advice about specific aspects of the project.  Consultants are normally expected 
  to provide a report of their activities, usually at a time agreed upon before the 
  consultancy begins.  Provide the number of days the consultant is being hired for and their 
  daily rate.<br/><br/>
  - Contracted Services: include professional or technical activities that will be performed 
  by commercial vendors or qualified individuals.  Contractual services are normally used for
  project activities that cannot be carried out by the institution, or for those activities 
  that can be more economically performed by firms or individuals specializing in a particular
  service. </p><br/><br/>
  
  <table width="100%" summary="for layout only">
    <tr>
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=1&p=staid">Personal Services</a></td>
      
      <td class="adminbgt">        
       <a href="AdminBudgetSelect?tab=2&p=staid">Employee Benefits</a></td>        
      
      <td class="adminbgtselect">Contracted Services</td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=4&p=staid">Supplies Materials & Equipment</a></td>
            
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=6&p=staid">Travel Expenses</a></td>
    </tr>
  </table><br/><br/>
  


<html:form action="/saveStaidAdminBudget">

  <INPUT type="hidden" name="currtab" value="3"><input type="HIDDEN" name="p" value="staid"/>
    
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >
       
    <table class="boxtype" width="100%" align="center" summary="for layout only">
      <tr>
        <td>Service Type</td><td>Consultant/Vendor</td>
        <td>Description</td>
      </tr>      
      <tr >
        <td><c:out value="${contractItem.servicetype}" /></td>
        <td><c:out value="${contractItem.recipient}" /></td>
        <td><c:out value="${contractItem.servicedescr}" /></td>
      </tr>      
      <tr>
        <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
        <td colspan="2">Check/Journal Entry#</td>
      </tr> 
      <tr>
        <td colspan="2"><c:out value="${contractItem.encumbranceDateStr}"/></td>
        <td colspan="2"><c:out value="${contractItem.journalEntry}"/></td>
      </tr>   
      <tr >
        <td width="20%">AmtRequested</td><td width="20%">AmtApproved</td>
        <td width="20%">AmtAmended</td>
        <td width="20%">ExpSubmitted</td><td width="20%">ExpApproved</td>
      </tr>
      <tr >
        <td><b><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
        <td><html:text name="contractItem" property="amtamendedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${contractItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="contractItem" property="expapprovedStr" indexed="true" />
            <html:hidden name="contractItem" property="id" indexed="true" /></td>
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
    <td colspan="5" ><b>Contracted Service Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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
        <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
    </td>
  </tr>
</table>

  
  
  </body>
</html>