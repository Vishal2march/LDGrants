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
  
  <br/><br/>
  <a href="http://www.archives.nysed.gov/common/archives/files/grants_lgrmif_egrant_user_manual.pdf" target="_blank">Project budget help</a>
  <br/>
  
  
  <c:set var="lgtab" value="${param.p}" />
  <p class="bdgtitle"><b>Project Budget</b><br/>
  
  <c:choose >
  <c:when test="${lgtab=='1'}">
    <b>1. Professional Salaries  (Code 15)</b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
    Justify in detail the need for these positions and clearly outline the responsibilities of the positions. 
    Demonstrate why the requested number of hours is needed. Explain how the project staff will support 
    project activities and goals.    
  </c:when>
  <c:when test="${lgtab=='2'}">
    <b>2. Support Staff Salaries  (Code 16)</b>&nbsp;&nbsp; <a href="docs/lgrmif/budgetEligibility.htm" target="_blank">Eligible/
    Ineligible Expenditures and Other Required Forms</a><br/>
    Justify in detail the need for these positions and clearly outline the responsibilities of the positions. 
    Demonstrate why the requested number of hours is needed. Explain how the project staff will support project 
    activities and goals.   
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
  <html:form action="/saveLgPersExpenses" >
  
    <logic:present name="BudgetCollectionBean" property="allPersRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" >   
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>      
        <td>Name</td><td>Position/Title</td>
        <td>Rate of Pay</td><td>Hours worked</td><td>AmtRequested</td>    
      </tr>      
      <tr>
        <td><c:out value="${personalItem.name}" /></td>
        <td><c:out value="${personalItem.title}" /></td>
        <td><c:out value="${personalItem.fte}" /></td>
        <td><fmt:formatNumber value="${personalItem.salaryf}" type="currency" /></td>       
        <td><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
      </tr>     
      
      
      <tr>
        <td>InstContribution</td>        
        <td>AmtAwarded</td>
        <td>AmtAmended</td>
       </tr>
       <tr>
         <td>N/A</td>
         <td><b><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
         <td><fmt:formatNumber value="${personalItem.amtamended}" type="currency" maxFractionDigits="0"/></td>
       </tr>
                  
      
      <tr>
        <td>Beginning Date of Employment (mm/dd/yyyy)</td>
        <td>Ending Date of Employment (mm/dd/yyyy)</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <%--only display new fs10f fields starting 2014-15--%>
      <c:choose>
      <c:when test="${thisGrant.fycode>14}">
          <tr>
            <td><html:text name="personalItem" property="beginDateStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="endDateStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>   
      </c:when>
      <c:otherwise>
          <tr>
            <td></td>
            <td></td>
            <td><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
            <td><b><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>   
            <html:hidden name="personalItem" property="beginDateStr" indexed="true" />
            <html:hidden name="personalItem" property="endDateStr" indexed="true" />
      </c:otherwise>
      </c:choose>      
      
     
          <html:hidden name="personalItem" property="id" indexed="true" />
          <html:hidden name="personalItem" property="fycode" indexed="true" />
          <html:hidden name="personalItem" property="name" indexed="true" />
          <html:hidden name="personalItem" property="title" indexed="true" />
          <html:hidden name="personalItem" property="salaryrate" indexed="true" />
          <html:hidden name="personalItem" property="salaryf" indexed="true" />
          <html:hidden name="personalItem" property="fte" indexed="true" />
          <html:hidden name="personalItem" property="grantrequest" indexed="true" />
          <html:hidden name="personalItem" property="amountapproved" indexed="true" />
          <html:hidden name="personalItem" property="expapproved" indexed="true" />
          <html:hidden name="personalItem" property="amtamended" indexed="true"/>
          <html:hidden name="personalItem" property="typeCode" indexed="true" />
          <html:hidden name="personalItem" property="module" indexed="true" value="lg" /></td>          
    </table>
    
  </logic:iterate>
  </logic:present>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allPersRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="1" />
          <input type="HIDDEN" name="i" value='<c:out value="${lgtab}" />' />
          <html:submit value="Save Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>
  
  </html:form>
  
  </c:when>
  <c:otherwise >
  
  <%-- INITIAL BUDGET --%>
  
  <form method="POST" action="AddBudgetItem">    
    <p><input type="submit" value="Add"/>
       <i>Click here to initiate an entry under each budget category, 
       and save any changes before adding a new record. <br/>
       Delete any blank/unused records that have been created for a budget category code.</i>
       <INPUT type="hidden" name="tab" value="1" />
       <INPUT type="hidden" name="p" value="lg" />
       <input type="HIDDEN" name="lgtab" value='<c:out value="${lgtab}" />' /></p>
  </form>  
    
    
  <% int index =0; %>  
  <html:errors />
  <html:form action="/saveLgSalaries">
  
    <logic:present name="BudgetCollectionBean" property="allPersRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
              
      <%-- create url that allows for deletion of this record, uses the expense id --%>
      <%--<c:url value="LgDeleteBudget.do" var="deleteURL">
          <c:param name="item" value="budget" />
          <c:param name="tab" value="1" />
          <c:param name="id" value="${personalItem.id}" />
          <c:param name="desc" value="${personalItem.name}" />
          <c:param name="p" value="lg${lgtab}" />
      </c:url>--%>
      <c:url value="lgApplicant.do" var="deleteURL">
          <c:param name="i" value="confirmbdgtdelete" />
          <c:param name="tab" value="1" />
          <c:param name="id" value="${personalItem.id}" />
          <c:param name="p" value="lg${lgtab}" />
      </c:url>
    
      <table class="boxtype" width="100%" summary="for layout only">
        <tr >
          <td>Name</td>
          <td>Title</td>
          <td>Hours Worked</td>
          <td>Rate of Pay</td>    
          <td>Hours*Pay rate</td>
        </tr>        
        <tr>
          <td><html:text name="personalItem" property="name" indexed="true" maxlength="50" /></td>
          <td><html:text name="personalItem" property="title" indexed="true" maxlength="50" /></td>
          <td><html:text name="personalItem" property="fteStr" indexed="true" onchange='<%= "calcLgSalary(" + index + ");" %>' /></td>
          <td><html:text name="personalItem" property="salaryrate" indexed="true" onchange='<%= "calcLgSalary(" + index + ");" %>' /></td>
          
          <td><html:text name="personalItem" property="cost" indexed="true" readonly="true" size="6" style="background-color:silver" /></td>
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
          <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
         <%-- <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          <td><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></td>--%>
        </tr>  
        <tr>
          <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          <td colspan="3">
          <html:hidden name="personalItem" property="id" indexed="true" />
          <html:hidden name="personalItem" property="typeCode" indexed="true" />
          <html:hidden name="personalItem" property="module" indexed="true" value="lg" /></td>          
        </tr>
      </table>  
      <% index++; %>
   
       
  </logic:iterate>
  </logic:present>
  

  <logic:notEmpty name="BudgetCollectionBean" property="allPersRecords">
    <c:choose >
    <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
      <p align="center"><input type="BUTTON" value="Save Salaries" disabled="disabled" /></p>
    </c:when>
    <c:otherwise >
      <p align="center"><input type="HIDDEN" name="currtab" value="1" />
        <input type="HIDDEN" name="i" value='<c:out value="${lgtab}" />' />
        <html:submit value="Save Salaries" />
        <i><b>Remember to Save your work often.</b></i>
    </p>
    </c:otherwise>
    </c:choose>
 </logic:notEmpty>
 
 </html:form>
  
 </c:otherwise>
 </c:choose>
 
 
 <br/>
 <%-- bottom section containing totals for this expense and total expenses--%>
  <c:if test="${lgtab=='1'}">
  <table width="100%" summary="for layout only" class="boxtype">  
      <tr>
        <td colspan="4"><b>Professional Salaries Totals (Code 15)</b></td>
      </tr>
      <tr>
        <td width="25%">Amount Requested</td>
        <td width="25%">Amount Awarded</td>
        <td width="25%">Expense Submitted</td>
        <td width="25%">Expense Approved</td>
      </tr> 
    <tr>
      <td><fmt:formatNumber value="${totalsBean.proffAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.proffAmtAppr}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.proffExpSub}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.proffExpAppr}" type="currency" maxFractionDigits="0"/></td> 
    </tr>
    </table>
  </c:if>

  <c:if test="${lgtab=='2'}">
  <table width="100%" summary="for layout only" class="boxtype">  
      <tr>
        <td colspan="4"><b>Support Staff Salaries Totals (Code 16)</b></td>
      </tr>
      <tr>
        <td width="25%">Amount Requested</td>
        <td width="25%">Amount Awarded</td>
        <td width="25%">Expense Submitted</td>
        <td width="25%">Expense Approved</td>
      </tr> 
    <tr>
      <td><fmt:formatNumber value="${totalsBean.profsuppAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.profsuppAmtAppr}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.profsuppExpSub}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.profsuppExpAppr}" type="currency" maxFractionDigits="0"/></td> 
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
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0"/></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0"/></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0"/></b></td>
  </tr>
</table>
<br/>
   
  </body>
</html>
