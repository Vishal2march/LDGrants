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
  
  <p class="bdgtitle"><b>Project Budget</b><br/>
  
  <b>6. Employee Benefits (Code 80) </b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
  Justify the need for using grant funds to pay staff benefits. Provide justification for any fringe benefits 
  that exceed 35% of the cost of the salaries requested. 
  </p><br/><br/>
   
   <c:if test="${totalsBean.warning =='true'}">
    <p align="center"><font color="red"><c:out value="${totalsBean.lgMessage}" /></font></p>
  </c:if>
   
  <c:choose >
   <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >
   
   <br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Final project budget help</a>
  <br/>
  
   <%-- FINAL EXPENSES --%>
   
   <html:errors />
   <html:form action="/saveLgExpenses" >
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td>Name</td>   
        <td>Benefits Percentage (decimal)</td>
        <td>Salary*FTE</td>
        <td>BenefitsAmt</td>
      </tr>       
      <tr>
        <td><c:out value="${benefitItem.name}" /></td>
        <td><fmt:formatNumber value="${benefitItem.benpercent}"/></td>
        <td><c:out value="${benefitItem.salary}"/></td>
        <td><c:out value="${benefitItem.cost}"/></td>
      </tr>       
      <tr>
        <td width="16%">InstContribution</td>
        <td width="16%">AmtRequested</td>
        <td width="16%">AmtAwarded</td>
        <td width="16%">AmtAmended</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr>
      <tr>
        <td>N/A</td>
        <td ><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td ><b><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${benefitItem.amtamended}" type="currency" maxFractionDigits="0"/></td>
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
          <html:hidden name="benefitItem" property="amtamended" indexed="true"/>
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
          <input type="HIDDEN" name="i" value="9" />
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
      <i>Click here to initiate an entry under each budget category, 
       and save any changes before adding a new record. <br/>
       Delete any blank/unused records that have been created for a budget category code.</i>
      <INPUT type="hidden" name="tab" value="2">
      <INPUT type="hidden" name="p" value="lg" />
      <input type="HIDDEN" name="lgtab" value="9" /></p>
  </form>
   
   
    <% int index =0; %>
    <html:errors />
    <html:form action="/saveLgBenefit">
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
          
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="lgApplicant.do" var="deleteURL">
          <c:param name="i" value="confirmbdgtdelete" />
          <c:param name="tab" value="2" />
          <c:param name="id" value="${benefitItem.id}" />
          <c:param name="p" value="lg9" />
      </c:url>
      
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td>Name</td>   
        <td>Benefits Percentage (decimal)</td>
        <td>Salary*FTE</td>
        <td>BenefitsAmt</td>
      </tr>      
      <tr>
        <td><html:text name="benefitItem" property="name" indexed="true" maxlength="50" /></td>
        <td><html:text name="benefitItem" property="benpercentStr" indexed="true" onchange='<%= "calcBenefits(" + index + ");" %>' /></td>
        <td><html:text name="benefitItem" property="salary" indexed="true" readonly="true" style="background-color:silver" /></td>
        <td><html:text name="benefitItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
      </tr>        
      <tr>
        <td width="20%">InstContribution</td>
        <td width="20%">AmtRequested</td>
       <%-- <td width="20%">AmtAwarded</td>
        <td width="20%">ExpSubmitted</td>
        <td width="20%">ExpApproved</td>--%>
      </tr>
      <tr>
        <td>N/A</td>
        <td><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
        <%--<td><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>--%>
      </tr>                
      <tr>        
        <td><html:hidden name="benefitItem" property="id" indexed="true" />
        <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
     </tr>
     </table>  
      <% index++; %>
       
    </logic:iterate>
    </logic:present>
    
    <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Benefits" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="2" />
          <input type="HIDDEN" name="i" value="9" />
          <html:submit value="Save Benefits" /><i><b>Remember to Save your work often.</b></i></p>
      </c:otherwise>
      </c:choose>    
    </logic:notEmpty>
    
  </html:form>
  
 </c:otherwise>
 </c:choose>
 
 <br/><br/>
 <table width="100%" summary="for layout only" class="boxtype">
  <tr>
    <td colspan="4"><b>Employee Benefits Totals (Code 80)</b></td>
  </tr>
   <tr>
    <td width="25%">Amount Requested</td>
    <td width="25%">Amount Awarded</td>
    <td width="25%">Expense Submitted</td>
    <td width="25%">Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  </table>
  
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
