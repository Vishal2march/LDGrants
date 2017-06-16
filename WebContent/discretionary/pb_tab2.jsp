<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab2.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/17/07     Created
 *
 * Description
 * This is the Di apcnt budget page for employee benefits.  Split into 2 sections for
 * initial budget and final expenses.  Has option to add record for 1 year only. 
--%>
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
  </head>
  <body>
  
  
<p class="bdgtitle"><b>Project Budget<br/>
  II. Employee Benefits</b><br/>
  List all persons to be employed by the project as described under "Personal Services."  
  Fill in the appropriate amount of benefits to be paid for the time they will work on the project
</p>



  <table width="100%" summary="for layout only">
    <tr>
      <td class="dibgt">
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      
      <a href="BudgetSelect?tab=1&p=di">I. Personal Services</a></td>        
      
      <td class="dibgtselect">II. Employee Benefits</td>        
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=3&p=di">III. Contracted Services</a></td>        
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=4&p=di">IV. Supplies Materials & Equipment</a></td>        
            
      <td class="dibgt">
      <a href="BudgetSelect?tab=6&p=di">V. Travel Expenses</a></td>
    </tr>
  </table>
  <a name="bdgcontent" id="bdgcontent" /><br/>
  

  <c:if test="${totalsBean.warning =='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.diMessage}" /></p>
  </c:if>
        
   <c:choose >
   <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >
   
   <html:errors />
   <html:form action="/saveDiExpenses" >
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td colspan="2">Name</td>   
        <td colspan="2">Benefits Percentage</td>
      </tr>      
      <tr>
        <td colspan="2"><c:out value="${benefitItem.name}" /></td>
        <td colspan="2"><fmt:formatNumber value="${benefitItem.benpercent}"/></td>
      </tr>       
      <tr>
        <td width="14%">ProjTotal</td>
        <td width="14%">InstContrib</td>
        <td width="14%">AmtRequested</td>
        <td width="14%">AmtApproved</td>
        <td width="14%">AmtAmended</td>
        <td width="14%">ExpSubmitted</td>
        <td width="14%">ExpApproved</td>
      </tr>
      <tr>
        <td ><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
        <td ><fmt:formatNumber value="${benefitItem.instcont}" type="currency" maxFractionDigits="0" /></td>
        <td ><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td ><b><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td ><b><fmt:formatNumber value="${benefitItem.amtamended}"  type="currency" maxFractionDigits="0" /></b></td>
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
          <html:hidden name="benefitItem" property="amtamended" indexed="true" />  
     </table>    
    
   </logic:iterate>
   </logic:present>
   
   <p><a href="diInitialForms.do?i=fs10a">FS10A - Amendment to Budget Item</a></p>
   
   <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Benefit Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="2" />
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
      Save any changes first before adding a new record.
      <INPUT type="hidden" name="tab" value="2">
      <INPUT type="hidden" name="p" value="di" /></p>
  </form>
   
    <% int index =0; %>
    <html:errors />
    <html:form action="/saveDiBenefit">
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
          
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="diInitialForms.do" var="deleteURL">
        <c:param name="i" value="confirmbdgtdelete" />
        <c:param name="tab" value="2" />
        <c:param name="id" value="${benefitItem.id}" />
        <c:param name="p" value="di" />
    </c:url>
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td colspan="2">Name</td>   
        <td colspan="2">Benefits Percentage (decimal)</td>
        <td>Salary*FTE</td>
        <td>BenefitsAmt</td>
      </tr>      
      <tr>
        <td colspan="2"><html:text name="benefitItem" property="name" indexed="true" /></td>
        <td colspan="2"><html:text name="benefitItem" property="benpercentStr" indexed="true" onchange='<%= "calcBenefits(" + index + ");" %>' /></td>
        <td><html:text name="benefitItem" property="salary" indexed="true" readonly="true" style="background-color:silver" /></td>
        <td><html:text name="benefitItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
      </tr>        
      <tr>
        <td width="16%">ProjTotal</td>
        <td width="16%">InstContrib</td>
        <td width="16%">AmtRequested</td>
        <td width="16%">AmtApproved</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
        <td><html:text name="benefitItem" property="instcontStr" indexed="true" /></td>
        <td><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
        <td><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>                
      <tr>
        <html:hidden name="benefitItem" property="id" indexed="true" />
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
     </tr>
     </table>  
      <% index++; %>
       
    </logic:iterate>
    </logic:present>
    
    <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Benefit Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="2" />
          <html:submit value="Save Benefit Expenses" /></p>
      </c:otherwise>
      </c:choose>    
    </logic:notEmpty>
    
  </html:form>
  
 </c:otherwise>
 </c:choose>
 
 

<table width="100%" summary="for layout only">
  <tr>
    <td colspan="7"><b>Employee Benefits Totals</b></td>
  </tr>
   <tr>
    <td width="14%">Project Total</td>
    <td width="14%">Inst Contrib.</td>
    <td width="14%">Amount Requested</td>
    <td width="14%">Amount Approved</td>
    <td width="14%">Amount Amended</td>
    <td width="14%">Expense Submitted</td>
    <td width="14%">Expense Approved</td>
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
    <td height="15" colspan="7"></td>
  </tr>
  <tr>
    <td colspan="7"><b>Grand Totals for all Budget Categories</b></td>
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
    <td>
      <b><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency" maxFractionDigits="0"/></b>
    </td>

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
      <b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
  </tr>
</table>
  
  </body>
</html>
