<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
  
  <b>5. Travel Expenses (Code 46) </b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
  Explain how the proposed travel will help achieve the intended results outlined in the application and why it is essential to the project.   
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
  <html:form action="/saveLgExpenses" >
    <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" > 
          
     <table class="boxtype" width="100%" summary="for layout only" >
       <tr>
        <td>Position of Traveler</td>  
        <td>Purpose</td>
        <td>Calculation of cost</td>
        <td width="16%">InstContribution</td>
        <td width="16%">AmtRequested</td>
      </tr>         
      <tr>
        <td><c:out value="${travelItem.description}" /></td>
        <td><c:out value="${travelItem.purpose}" /></td>
        <td><c:out value="${travelItem.costsummary}" /></td>
        <td>N/A</td>
        <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
      </tr>   
      
      
      <tr>
        <td width="16%">AmtAwarded</td>
        <td width="16%">AmtAmended</td>
      </tr>
      <tr>
        <td><b><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${travelItem.amtamended}" type="currency" maxFractionDigits="0"/></td>
      </tr>
            
      
      <tr>
        <td>Name of Traveler</td>
        <td>Dates of Travel</td>       
        <td>Check/Journal Entry#</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr> 
      <%--only display new fs10f fields starting 2014-15--%>
      <c:choose>
      <c:when test="${thisGrant.fycode>14}">
          <tr>
            <td><html:text name="travelItem" property="travelerName" indexed="true" /></td>
            <td><html:text name="travelItem" property="travelPeriod" indexed="true" /></td>            
            <td><html:text name="travelItem" property="journalEntry" indexed="true" /></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr> 
      </c:when>
      <c:otherwise>
          <tr>
            <td></td>
            <td></td>            
            <td></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr> 
            <html:hidden name="travelItem" property="travelPeriod" indexed="true" />
            <html:hidden name="travelItem" property="travelerName" indexed="true" />
            <html:hidden name="travelItem" property="journalEntry" indexed="true" />
      </c:otherwise>
      </c:choose>
          
    
        <html:hidden name="travelItem" property="description" indexed="true" />
        <html:hidden name="travelItem" property="grantrequest"  indexed="true"/>           
        <html:hidden name="travelItem" property="amountapproved"  indexed="true"/> 
        <html:hidden name="travelItem" property="expapproved"  indexed="true"/>  
        <html:hidden name="travelItem" property="id" indexed="true" />       
        <html:hidden name="travelItem" property="fycode" indexed="true" />  
        <html:hidden name="travelItem" property="amtamended" indexed="true"/> 
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
          <input type="HIDDEN" name="i" value="8" />
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
      <i>Click here to initiate an entry under each budget category, 
       and save any changes before adding a new record. <br/>
       Delete any blank/unused records that have been created for a budget category code.</i>
      <INPUT type="hidden" name="tab" value="6">
      <INPUT type="hidden" name="p" value="lg" />
      <input type="HIDDEN" name="lgtab" value="8"</p>
  </form>
  
    
    <%-- INITIAL BUDGET --%>
    <html:errors />
    <html:form action="/saveLgTravel">
    <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >
          
    <%-- create url that allows for deletion of this record --%>
    <c:url value="lgApplicant.do" var="deleteURL">
          <c:param name="i" value="confirmbdgtdelete" />
          <c:param name="tab" value="6" />
          <c:param name="id" value="${travelItem.id}" />
          <c:param name="p" value="lg8" />
      </c:url>
        
    <table width="100%" align="center" summary="for layout only" class="boxtype" >
      <tr>
        <td>Position of Traveler</td>  
        <td>Purpose</td>
        <td colspan="2">Calculation of cost</td>
      </tr>      
      <tr>
        <td><html:text name="travelItem" property="description" indexed="true" maxlength="500" /></td>
        <td><html:text name="travelItem" property="purpose" indexed="true" maxlength="500" /></td>
        <td colspan="2"><html:text name="travelItem" property="costsummary" indexed="true" maxlength="100" size="50" /></td>
      </tr>      
      <tr>
        <td width="20%">InstContribution</td>
        <td width="20%">AmtRequested</td>
        <%--<td width="20%">AmtAwarded</td>
        <td width="20%">ExpSubmitted</td>
        <td width="20%">ExpApproved</td>--%>
      </tr>
      <tr>
        <td>N/A</td>
        <td><html:text name="travelItem" property="grantrequestStr" indexed="true"/></td>
        <%--<td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></td>--%>
      </tr>       
      <tr>
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a>
        <html:hidden name="travelItem" property="id" indexed="true" /> </td>
      </tr>
    </table>
   
    </logic:iterate>
    </logic:present>
    
    <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Travel" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="6" />
          <input type="HIDDEN" name="i" value="8" />
          <html:submit value="Save Travel" /><i><b>Remember to Save your work often.</b></i></p>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty> 
  </html:form>
  
  
 </c:otherwise>
 </c:choose>
 
  <br/><br/>
  <table width="100%" align="center" summary="for layout only" class="boxtype">
  <tr>
    <td colspan="4"><b>Travel Expense Totals (Code 46)</b></td>
  </tr>
   <tr>
    <td width="25%">Amount Requested</td>
    <td width="25%">Amount Awarded</td>
    <td width="25%">Expense Submitted</td>
    <td width="25%">Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  </table>
  
  <br/><br/>
  <table width="100%" align="center" summary="for layout only" class="boxtype">
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
