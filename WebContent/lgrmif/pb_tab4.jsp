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
  
  <p class="bdgtitle"><b>Project Budget<br/>
  8. Minor Remodeling (Code 30) </b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
  Justify the need for the particular remodeling requested and why it is essential to the project. 
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
  <html:form action="/saveLgRemodExpenses" >
    <logic:present name="BudgetCollectionBean" property="allExpRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" > 
          
     <table class="boxtype" width="100%" summary="for layout only" >
      <tr>
        <td>Description of work</td> 
        <td>Calculation of Cost</td> 
        <td>InstContribution</td>
        <td>AmtRequested</td>        
      </tr>      
      <tr>
        <td><c:out value="${otherExpItem.description}" /></td>
        <td><c:out value="${otherExpItem.costsummary}" /></td>
        <Td>N/A</Td>
        <td><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
      </tr>    
      
      
      <tr>
        <td>AmtAwarded</td>
        <td>AmtAmended</td>
      </tr>
      <tr>
        <td><b><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${otherExpItem.amtamended}" type="currency" maxFractionDigits="0"/></td>
      </tr>
            
      
      <tr>
        <td>Purchase Order Date (mm/dd/yyyy)</td>    
        <td>Service Provider Used</td>
        <td>Check/Journal Entry#</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr> 
      <%--only display new fs10f fields starting 2014-15--%>
      <c:choose>
      <c:when test="${thisGrant.fycode>14}">
          <tr>
            <td><html:text name="otherExpItem" property="encumbranceDateStr" indexed="true" /></td>
            <td><html:text name="otherExpItem" property="providerUsed" indexed="true" /></td>
            <td><html:text name="otherExpItem" property="journalEntry" indexed="true" /></td>
            <td><html:text name="otherExpItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr> 
      </c:when>
      <c:otherwise>
          <tr>
            <td></td>
            <td></td>
            <td><html:text name="otherExpItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr> 
            <html:hidden name="otherExpItem" property="encumbranceDateStr" indexed="true" />
            <html:hidden name="otherExpItem" property="providerUsed" indexed="true" />
            <html:hidden name="otherExpItem" property="journalEntry" indexed="true" />
      </c:otherwise>
      </c:choose>
        
      
        <html:hidden name="otherExpItem" property="description" indexed="true" />
        <html:hidden name="otherExpItem" property="grantrequest"  indexed="true"/>           
        <html:hidden name="otherExpItem" property="amountapproved"  indexed="true"/> 
        <html:hidden name="otherExpItem" property="expapproved"  indexed="true"/>  
        <html:hidden name="otherExpItem" property="id" indexed="true" />       
        <html:hidden name="otherExpItem" property="fycode" indexed="true" />   
        <html:hidden name="otherExpItem" property="amtamended" indexed="true"/>
    </table>    
    
  </logic:iterate>
  </logic:present>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allExpRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="5" />
          <input type="HIDDEN" name="i" value="4" />
          <html:submit value="Save Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>
  
 </html:form> 
 
  </c:when>
  <c:otherwise >
  
  
  <%-- INITIAL BUDGET--%>
  
  <form method="POST" action="AddBudgetItem">    
    <p>
      <input type="submit" name="btn" value="Add"/>       
      <i>Click here to initiate an entry under each budget category, 
       and save any changes before adding a new record. <br/>
       Delete any blank/unused records that have been created for a budget category code.</i>
      <INPUT type="hidden" name="tab" value="5">
      <INPUT type="hidden" name="p" value="lg" />
      <input type="HIDDEN" name="lgtab" value="4"/></p>
  </form>
  
  <html:errors />
  <html:form action="/saveLgRemodeling" >
    <logic:present name="BudgetCollectionBean" property="allExpRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" > 
   
   
    <%-- create url that allows for deletion of this record --%>
    <c:url value="lgApplicant.do" var="deleteURL">
          <c:param name="i" value="confirmbdgtdelete" />
          <c:param name="tab" value="5" />
          <c:param name="id" value="${otherExpItem.id}" />
          <c:param name="p" value="lg4" />
      </c:url>
   
    <table class="boxtype" width="100%" summary="for layout only" >
      <tr>
        <td colspan="2">Description of work to be performed</td>  
        <td colspan="2">Calculation of Cost</td> 
      </tr>      
      <tr>
        <td colspan="2"><html:text name="otherExpItem" property="description" indexed="true" size="50" maxlength="255" /></td>
        <td colspan="2"><html:text name="otherExpItem" property="costsummary" indexed="true" size="50" maxlength="255" /></td>
      </tr>      
      <tr>
        <td width="20%">InstContribution</td>
        <td width="20%">AmtRequested</td>
       <%-- <td width="20%">AmtAwarded</td>
        <td width="20%">ExpSubmitted</td>
        <td width="20%">ExpApproved</td> --%>
      </tr>
      <tr >
        <td>N/A</td>
        <td><html:text name="otherExpItem" property="grantrequestStr" indexed="true"/></td>
       <%-- <td><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${otherExpItem.expsubmitted}" type="currency" maxFractionDigits="0"/></td>
        <td><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></td>--%>
      </tr>     
      <tr>
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
        <html:hidden name="otherExpItem" property="id" indexed="true" />
      </tr>
    </table>    
    
  </logic:iterate>
  </logic:present>
  
  
    <logic:notEmpty name="BudgetCollectionBean" property="allExpRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Remodeling" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="5" />
          <input type="HIDDEN" name="i" value="4" />
          <html:submit value="Save Remodeling" /><i><b>Remember to Save your work often.</b></i></p>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty> 
  </html:form>
  
 </c:otherwise>
 </c:choose>
 
 
 <br/><br/>
 <table width="100%" summary="for layout only" class="boxtype">
  <tr>
    <td colspan="4" ><b>Minor Remodeling Totals (Code 30)</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Awarded</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
   <tr>
    <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  </table>
  
  <br/><br/> 
 
  <table width="100%" summary="for layout only" class="boxtype">
  <tr>
    <td colspan="2"><b>Grand Totals for all Budget Categories</b></td>
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
