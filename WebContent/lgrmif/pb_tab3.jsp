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
  <c:when test="${lgtab=='3'}">
   <b>9. Equipment (Code 20) </b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
    Describe how the requested equipment will be used to support project activities and goals, and demonstrate why 
    this particular equipment is critical to the project’s success. Demonstrate how such equipment will be used on 
    an ongoing basis after the grant to support records management.  
  </c:when>
  <c:when test="${lgtab=='7'}">
    <b>4. Supplies and Materials (Code 45)</b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
    Describe how all the supplies and materials requested will support the project activities and goals and why 
    they are essential to the project.   
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
  
  <%-- FINAL EXPENSES --%>
  
   <html:errors />
   <html:form action="/saveLgSuppExpenses" >
    <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        
      <table class="boxtype" width="100%" summary="for layout only">
        <tr>
          <td>Quantity</td>
          <td>Description</td>
          <td>UnitPrice</td>
          <td>Vendor</td>
          <td>AmtRequested</td>
        </tr>          
        <tr >
          <td><c:out value="${supplyItem.quantity}" /></td>
          <td><c:out value="${supplyItem.description}" /></td>            
          <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
          <td><c:out value="${supplyItem.vendor}" /></td>          
          <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        </tr>
        
        
        <tr>
          <td width="16%">InstContribution</td>        
          <td width="16%">AmtAwarded</td>
          <td width="16%">AmtAmended</td>
        </tr>
        <tr>
          <td>N/A</td>
          <td><b><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${supplyItem.amtamended}" type="currency" maxFractionDigits="0"/></td>
        </tr>
        
            
        <tr>
          <td>Purchase Order Date (mm/dd/yyyy)</td>   
          <td>Check/Journal Entry#</td>
          <td width="16%">ExpSubmitted</td>
          <td width="16%">ExpApproved</td>
        </tr> 
        <%--only display new fs10f fields starting 2014-15--%>
        <c:choose>
        <c:when test="${thisGrant.fycode>14}">      
            <tr>
              <td><html:text name="supplyItem" property="encumbranceDateStr" indexed="true" /></td>
              <td><html:text name="supplyItem" property="journalEntry" indexed="true" /></td>
              <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
              <td><b><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td> 
            </tr>   
        </c:when>
        <c:otherwise>
            <tr>
              <td></td>
              <td></td>
              <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
              <td><b><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td> 
            </tr>   
              <html:hidden name="supplyItem" property="encumbranceDateStr" indexed="true" />
              <html:hidden name="supplyItem" property="journalEntry" indexed="true" />
        </c:otherwise>
        </c:choose>
        
     
          <html:hidden name="supplyItem" property="id" indexed="true" />
          <html:hidden name="supplyItem" property="fycode" indexed="true" />
          <html:hidden name="supplyItem" property="quantity" indexed="true" />
          <html:hidden name="supplyItem" property="description" indexed="true" />
          <html:hidden name="supplyItem" property="unitprice" indexed="true" />
          <html:hidden name="supplyItem" property="vendor" indexed="true" />
          <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
          <html:hidden name="supplyItem" property="grantrequest" indexed="true" />            
          <html:hidden name="supplyItem" property="amountapproved" indexed="true" />
          <html:hidden name="supplyItem" property="expapproved" indexed="true" />    
          <html:hidden name="supplyItem" property="amtamended" indexed="true"/>
          <html:hidden name="supplyItem" property="module" indexed="true" value="lg" />
      </table>  
     
      </logic:iterate>
      </logic:present>
      
      <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
        <c:choose >
        <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
            <p align="center"><input type="BUTTON" value="Save Expenses" disabled="disabled" /></p>
        </c:when>
        <c:otherwise >
            <p align="center"><input type="HIDDEN" name="currtab" value="4" />
              <input type="HIDDEN" name="i" value='<c:out value="${lgtab}" />'/>
              <html:submit value="Save Expenses" /></p>
        </c:otherwise>
        </c:choose>
      </logic:notEmpty>
      </html:form>
      

  </c:when>
  <c:otherwise >
  
    <form method="POST" action="AddBudgetItem" >      
      <p><input type="submit" value="Add"/>        
         <i>Click here to initiate an entry under each budget category, 
       and save any changes before adding a new record. <br/>
       Delete any blank/unused records that have been created for a budget category code.</i>
         <INPUT type="hidden" name="tab" value="4">
         <INPUT type="hidden" name="p" value="lg" />
         <input type="HIDDEN" name="lgtab" value='<c:out value="${lgtab}" />' /></p>
    </form>
    
    
    <% int index =0; %>
    <html:errors />
    <html:form action="/saveLgSupplies">
    <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >    
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="lgApplicant.do" var="deleteURL">
          <c:param name="i" value="confirmbdgtdelete" />
          <c:param name="tab" value="4" />
          <c:param name="id" value="${supplyItem.id}" />
          <c:param name="p" value="lg${lgtab}" />
      </c:url>
    
    <table width="100%" class="boxtype" summary="for layout only">
      <tr>
        <td>Quantity</td>
        <td>Description</td>
        <td>UnitPrice</td>
        <td>Quantity*Price</td>
        <td>Vendor</td>
      </tr>
      
      <tr>
        <td><html:text name="supplyItem" property="quantity" indexed="true" maxlength="4" onchange='<%= "calcLgCost(" + index + ");" %>' /></td>
        <td><html:text name="supplyItem" property="description" indexed="true" maxlength="256" /></td>        
        <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" onchange='<%= "calcLgCost(" + index + ");" %>' /></td>
        <td><html:text name="supplyItem" property="cost" indexed="true" readonly="true" size="6" style="background-color:silver"/></td>
        
        <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
      </tr>
      <tr>
        <td>InstContribution</td>
        <td>AmtRequested</td>
        <%--<td>AmtAwarded</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>--%>
      </tr>
      <tr>
        <td>N/A</td>
        <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
       <%-- <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></td>--%>
      </tr>
      <tr>
        <html:hidden name="supplyItem" property="id" indexed="true" />
        <html:hidden name="supplyItem" property="module" indexed="true" value="lg" />
        <html:hidden name="supplyItem" property="lgFlag" indexed="true" value="true" />
        <html:hidden name="supplyItem" property="suppmatCode" indexed="true"/>
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>  
    <% index++; %>
    
  </logic:iterate>
  </logic:present>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
    <c:choose >
    <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Records" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="4" />
          <input type="HIDDEN" name="i" value='<c:out value="${lgtab}" />'/>
          <html:submit value="Save Records" /><i><b>Remember to Save your work often.</b></i></p>
      </c:otherwise>
      </c:choose>
   </logic:notEmpty>   
  </html:form>
  
  </c:otherwise>
  </c:choose>
  
  
  <br/>
    
  <c:if test="${lgtab=='3'}">
  <table width="100%" summary="for layout only" class="boxtype">
      <tr>
        <td colspan="4"><b>Equipment Totals (Code 20)</b></td>
      </tr>
       <tr>
        <td width="25%">Amount Requested</td>
        <td width="25%">Amount Awarded</td>
        <td width="25%">Expense Submitted</td>
        <td width="25%">Expense Approved</td>
      </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.equipAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.equipAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.equipExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.equipExpAppr}" type="currency" maxFractionDigits="0" /></td>    
    </tr>  
    </table>
  </c:if>
  
  
  
  <c:if test="${lgtab=='7'}">
  <table width="100%" summary="for layout only" class="boxtype">
      <tr>
        <td colspan="4"><b>Supplies, Materials Totals (Code 45)</b></td>
      </tr>
       <tr>
        <td width="25%">Amount Requested</td>
        <td width="25%">Amount Awarded</td>
        <td width="25%">Expense Submitted</td>
        <td width="25%">Expense Approved</td>
      </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.supplyAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.supplyAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.supplyExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.supplyExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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
