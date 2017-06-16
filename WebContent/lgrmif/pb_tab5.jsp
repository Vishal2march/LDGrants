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
    <link href="css/archivesLDGrants.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  <b>Institution:</b> <c:out value="${thisGrant.instName}"/><br/>
  <b>Project Number:</b> 05<fmt:formatNumber value="${thisGrant.fccode}" />
                    -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
                    -<fmt:formatNumber value="${thisGrant.projseqnum}" minIntegerDigits="4" pattern="####" />
  
  <br/>
  
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Project budget help</a>
  <br/>
  
   <c:set var="lgtab" value="${param.p}" />
   <p class="bdgtitle"><b>Project Budget</b><br/>
  
  <c:choose >
  <c:when test="${lgtab=='5'}">
    <b>3. Purchased Services (Code 40)</b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
    Describe how each of the purchased services supports the project’s activities and goals. Clearly explain and 
    justify the consultant or vendor’s role in and time spent on the project, and demonstrate that the consultant 
    or vendor is qualified to conduct this work. List purchased services from a BOCES under Code 49. 
  </c:when>
  <c:when test="${lgtab=='6'}">
    <b>7. Purchased Services - BOCES  (Code 49)</b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
    Describe how each of the purchased services with BOCES supports project activities and goals. Clearly explain 
    and justify the consultant or vendor’s role in and time spent on the project, and demonstrate that the BOCES 
    is qualified to conduct this work.    
  </c:when>
  </c:choose>
   </p><br/><br/>
   
   <c:if test="${totalsBean.warning =='true'}">
    <p align="center"><font color="red"><c:out value="${totalsBean.lgMessage}" /></font></p>
  </c:if>
   
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >
  
  <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Final project budget help</a>
  <br/>
  
  <html:errors />
  <html:form action="/saveLgPurchExpenses" >
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
     
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td>Description</td>
        <td>Service Provider</td>
        <td>Calculation of Cost</td>    
        <td width="16%">InstContribution</td>
        <td width="16%">AmtRequested</td>
      </tr>     
       <tr >
        <td><c:out value="${contractItem.servicedescr}" /></td>        
        <td><c:out value="${contractItem.recipient}" /></td>
        <td><c:out value="${contractItem.servicetype}" /></td>
        <td>N/A</td>
        <td ><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
      </tr>     
      
      
      <tr>
        <td width="16%">AmtAwarded</td>
        <td width="16%">AmtAmended</td>
      </tr>
      <tr>
        <td ><b><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${contractItem.amtamended}" type="currency" maxFractionDigits="0"/></td>
      </tr>
      
      
      <tr>
        <td>Encumbrance Date (mm/dd/yyyy)</td>    
        <td>Service Provider Used</td>
        <td>Check/Journal Entry#</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr> 
      <%--only display new fs10f fields starting 2014-15--%>
      <c:choose>
      <c:when test="${thisGrant.fycode>14}">      
          <tr>
            <td><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
            <td><html:text name="contractItem" property="providerUsed" indexed="true" /></td>
            <td><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
            <td ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>   
      </c:when>
      <c:otherwise>
          <tr>
            <td></td>
            <td></td>
            <td ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr> 
            <html:hidden name="contractItem" property="encumbranceDateStr" indexed="true" />
            <html:hidden name="contractItem" property="providerUsed" indexed="true" />
            <html:hidden name="contractItem" property="journalEntry" indexed="true" />
      </c:otherwise>
      </c:choose>
      
      
      
          <html:hidden name="contractItem" property="id" indexed="true" />
          <html:hidden name="contractItem" property="fycode" indexed="true" />
          <html:hidden name="contractItem" property="servicetype" indexed="true" />
          <html:hidden name="contractItem" property="servicedescr" indexed="true" />
          <html:hidden name="contractItem" property="recipient" indexed="true" />
          <html:hidden name="contractItem" property="typeCode" indexed="true" />
          <html:hidden name="contractItem" property="grantrequest" indexed="true" />
          <html:hidden name="contractItem" property="amountapproved" indexed="true" />
          <html:hidden name="contractItem" property="expapproved" indexed="true" />  
          <html:hidden name="contractItem" property="amtamended" indexed="true"/>
          <html:hidden name="contractItem" property="module" indexed="true" value="lg" />
    </table>    
    
    </logic:iterate>
    </logic:present>
  
   <logic:notEmpty name="BudgetCollectionBean" property="allContractRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}" >
          <p align="center"><input type="BUTTON" value="Save Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center"><input type="HIDDEN" name="currtab" value="3" />
            <input type="HIDDEN" name="i" value='<c:out value="${lgtab}" />' />
            <html:submit value="Save Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>
  
  </html:form>  
  
  </c:when>
  <c:otherwise >
  
  <%-- INITIAL BUDGET --%>
  <form method="post" action="AddBudgetItem">    
    <p><input type="submit" value="Add"/>
    <i>Click here to initiate an entry under each budget category, 
       and save any changes before adding a new record. <br/>
       Delete any blank/unused records that have been created for a budget category code.</i>
    <INPUT type="hidden" name="tab" value="3">
    <INPUT type="hidden" name="p" value="lg" />
    <input type="HIDDEN" name="lgtab" value='<c:out value="${lgtab}" />' /></p>
  </form>
  
  
  <html:errors />
  <html:form action="/saveLgPurchased">
   <logic:present name="BudgetCollectionBean" property="allContractRecords" >
   <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
   
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <c:url value="lgApplicant.do" var="deleteURL">
          <c:param name="i" value="confirmbdgtdelete" />
          <c:param name="tab" value="3" />
          <c:param name="id" value="${contractItem.id}" />
          <c:param name="p" value="lg${lgtab}" />
      </c:url>
    
    <table width="100%" class="boxtype" summary="for layout only">
      <tr >
        <td>Description</td>
        <td colspan="2">Service Provider</td>
        <td colspan="2">Calculation of Cost</td>     
      </tr>     
      <tr>
        <td><html:text name="contractItem" property="servicedescr" indexed="true" maxlength="100" /></td>
        <td colspan="2"><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
        <td colspan="2"><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
     </tr>        
     <tr>
        <td width="20%">InstContribution</td>
        <td width="20%">AmtRequested</td>
        <%--<td width="20%">AmtAwarded</td>
        <td width="20%">ExpSubmitted</td>
        <td width="20%">ExpApproved</td>--%>
      </tr>
      <tr >
        <td>N/A</td>
        <td><html:text name="contractItem" property="grantrequestStr" indexed="true" /></td>
       <%-- <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${contractItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></td>--%>
      </tr>    
      <tr>
        <html:hidden name="contractItem" property="id" indexed="true" />
        <html:hidden name="contractItem" property="typeCode" indexed="true" />
        <html:hidden name="contractItem" property="module" indexed="true" value="lg" />
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>  
    
  </logic:iterate>
  </logic:present>
 
  <logic:notEmpty name="BudgetCollectionBean" property="allContractRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}" >
          <p align="center"><input type="BUTTON" value="Save Purchased" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center"><input type="HIDDEN" name="currtab" value="3" />
            <input type="HIDDEN" name="i" value='<c:out value="${lgtab}" />' />
            <html:submit value="Save Purchased" /><i><b>Remember to Save your work often.</b></i></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty> 
 </html:form>
 
 
 </c:otherwise>
 </c:choose>
 
 
 
 <br/><br/>
  <c:if test="${lgtab=='5'}">
   <table width="100%" summary="for layout only" class="boxtype">  
      <tr>
        <td colspan="4"><b>Purchased Service Totals (Code 40)</b></td>
      </tr>
       <tr>
        <td width="25%">Amount Requested</td>
        <td width="25%">Amount Awarded</td>
        <td width="25%">Expense Submitted</td>
        <td width="25%">Expense Approved</td>
      </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.purchAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.purchAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.purchExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.purchExpAppr}" type="currency" maxFractionDigits="0" /></td>    
    </tr>
    </table>
  </c:if>
  
  
  <c:if test="${lgtab=='6'}">
   <table width="100%" summary="for layout only" class="boxtype">  
      <tr>
        <td colspan="4"><b>Purchased Service - BOCES Totals (Code 49)</b></td>
      </tr>
       <tr>
        <td width="25%">Amount Requested</td>
        <td width="25%">Amount Awarded</td>
        <td width="25%">Expense Submitted</td>
        <td width="25%">Expense Approved</td>
      </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.bocesAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.bocesAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.bocesExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.bocesExpAppr}" type="currency" maxFractionDigits="0" /></td>    
    </tr>
    </table>
  </c:if>  
  
  <br/><br/>
  <table width="100%" summary="for layout only" class="boxtype">  
  <tr>
    <td colspan="4"><b>Grand Totals for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Amount Requested</td>
    <td>Amount Awarded</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b></td>   
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b></td>
  </tr>  
</table> 
<br/>  
    
  </body>
</html>
