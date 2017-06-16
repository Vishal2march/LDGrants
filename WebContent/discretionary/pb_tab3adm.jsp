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
  III. Contracted Services</b><br/>
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
  service. </p>


  <table width="100%" summary="for layout only">
    <tr>
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=1&p=di">I. Personal Services</a></td>
      
      <td class="adminbgt">        
       <a href="AdminBudgetSelect?tab=2&p=di">II. Employee Benefits</a></td>        
      
      <td class="adminbgtselect">III. Contracted Services</td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=4&p=di">IV. Supplies Materials & Equipment</a></td>
      
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
  
  
  <form method="post" action="AddBudgetItem">    
  <p>
    <input type="submit" value="Add"/>
    Save any changes first before adding a new record.
    <INPUT type="hidden" name="tab" value="3">
    <INPUT type="hidden" name="p" value="admindi" /></p>
  </form>
  
  

<html:form action="/saveDiAdminBudget">

  <INPUT type="hidden" name="currtab" value="3"><input type="HIDDEN" name="p" value="di"/>
    
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >
       
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <c:url value="diAdminNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="3" />
        <c:param name="id" value="${contractItem.id}" />
        <c:param name="p" value="admindi" />
    </c:url>
       
       
    <table class="boxtype" width="100%" align="center" summary="for layout only">
      <tr>
        <td colspan="2">Service Type</td>
        <td colspan="2">Consultant/Vendor</td>
        <td colspan="2">Description</td>
      </tr>      
     <tr >
        <td colspan="2"><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
        <td colspan="2"><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
        <td colspan="2"><html:text name="contractItem" property="servicedescr" indexed="true" maxlength="100" /></td>
      </tr>  
      
      <tr>
        <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
        <td colspan="2">Check/Journal Entry#</td>
      </tr> 
      <tr>
        <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
        <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
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
          <td><fmt:formatNumber value="${contractItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><html:text name="contractItem" property="instcontStr" indexed="true" /></td>
          <td><html:text name="contractItem" property="grantrequestStr" indexed="true" /></td>
          <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
          <td><html:text name="contractItem" property="amtamendedStr" indexed="true" /></td>
          <td ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
          <td><html:text name="contractItem" property="expapprovedStr" indexed="true" />
              <html:hidden name="contractItem" property="id" indexed="true" /></td>
        </c:when>
        <c:otherwise >
          <td><fmt:formatNumber value="${contractItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${contractItem.instcont}" type="currency" maxFractionDigits="0" /></td>
          <td><b><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><b><fmt:formatNumber value="${contractItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
          <td><b><fmt:formatNumber value="${contractItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
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
      <input type="submit" name="btn" value="Save"/>
    </p>
  </c:if>
</html:form>  

  
<table width="100%" align="center" summary="for layout only"> 
  <tr>
    <td colspan="7" ><b>Contracted Service Totals</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.conProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.conInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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
