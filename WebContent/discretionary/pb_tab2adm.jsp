<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
  II. Employee Benefits</b><br/>
  List all persons to be employed by the project as described under "Personal Services."  
  Fill in the appropriate amount of benefits to be paid for the time they will work on the project
</p>

  
<table width="100%" summary="for layout only">
  <tr>
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=1&p=di">I. Personal Services</a></td>
    
    <td class="adminbgtselect">II. Employee Benefits</td>        
    
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=3&p=di">III. Contracted Services</a></td>
   
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
  
  
  <form method="POST" action="AddBudgetItem">    
    <p>
      <input type="submit" value="Add"/>
      Save any changes first before adding a new record.
      <INPUT type="hidden" name="tab" value="2">
      <INPUT type="hidden" name="p" value="admindi" /></p>
  </form>
  
  
  

<html:form action="/saveDiAdminBudget">

  <INPUT type="hidden" name="currtab" value="2"><input type="HIDDEN" name="p" value="di"/>
    
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >
  
  
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="diAdminNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="2" />
        <c:param name="id" value="${benefitItem.id}" />
        <c:param name="p" value="admindi" />
    </c:url>
  
    
    <table class="boxtype" align="center" width="100%" summary="for layout only">
      <tr>
        <td colspan="2">Name</td>   
        <td colspan="2">Benefits Percentage</td>
      </tr>      
      <tr>
        <td colspan="2"><html:text name="benefitItem" property="name" indexed="true" /></td>
        <td colspan="2"><html:text name="benefitItem" property="benpercentStr" indexed="true" /></td>
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
            <td><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="benefitItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
            <td><html:text name="benefitItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="benefitItem" property="amtamendedStr" indexed="true" /></td>
            <td ><html:text name="benefitItem" property="expsubmittedStr" indexed="true" /></td>
            <td><html:text name="benefitItem" property="expapprovedStr" indexed="true" />
                <html:hidden name="benefitItem" property="id" indexed="true" /></td>
          </c:when>
          <c:otherwise >
            <td><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${benefitItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
            <td><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${benefitItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
            <td><b><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
            <td><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
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
      <input type="SUBMIT" name="btn" value="Copy Amt Requested" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="SUBMIT" name="btn" value="Copy Exp Submitted" />
      <br/><br/>
      <input type="submit" name="btn" value="Save"/>
    </p>
  </c:if>
</html:form>
  
  
<table width="100%" align="center" summary="for layout only">
  <tr>
    <td colspan="7" ><b>Employee Benefits Totals</b></td>
  </tr>
  <tr>
    <td>Project Total</td>
    <td>Inst Contrib. </td>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.benProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.benInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.benExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
 <tr>
    <td height="15" colspan="2"></td>
  </tr>
  <tr>
    <td colspan="7"><b>Grand Totals for all Budget Categories</b></td>
  </tr>
  <tr>   
     <td>Project Total</td>
     <td>Inst Contrib. </td>
     <td>Amount Requested</td>
     <td>Amount Approved</td>
     <td>Amount Amended</td>
     <td>Expense Submitted</td>
     <td>Expense Approved</td>
  </tr>
  <tr>
    <td >
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
        <c:url var="appURL" value="diAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
        <a href='<c:out value="${appURL}" />' >Back to Application Checklist</a>
    </td>
  </tr>
</table>

  </body>
</html>
