<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab3.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/14/07     Created
 *
 * Description
 * This is the Di apcnt budget page for contracted services.  Split into 2 sections for
 * initial budget and final expenses.  Has option to add record for 1 year only. 
--%>
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
      <td class="dibgt">
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      
      <a href="BudgetSelect?tab=1&p=di">I. Personal Services</a></td>        
      
      <td class="dibgt">        
      <a href="BudgetSelect?tab=2&p=di">II. Employee Benefits</a></td>        
      
      <td class="dibgtselect">III. Contracted Services</td>
      
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
  <html:form action="/saveDiContrExpenses" >
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
     
    <table class="boxtype" width="100%" summary="for layout only">
      <tr >
        <td colspan="2">Service Type</td>
        <td colspan="2">Consultant/Vendor</td>
        <td colspan="2">Description</td>
      </tr>     
       <tr >
        <td colspan="2"><c:out value="${contractItem.servicetype}" /></td>
        <td colspan="2"><c:out value="${contractItem.recipient}" /></td>
        <td colspan="2"><c:out value="${contractItem.servicedescr}" /></td>
      </tr>      
      
      <%--only display new fs10f fields starting 2014-15--%>
      <c:choose>
      <c:when test="${thisGrant.fycode>14}">
          <tr>
            <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
            <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
          </tr>  
      </c:when>
      <c:otherwise>
            <html:hidden name="contractItem" property="encumbranceDateStr" indexed="true" />
            <html:hidden name="contractItem" property="journalEntry" indexed="true" />      
      </c:otherwise>
      </c:choose>
      
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
        <td ><fmt:formatNumber value="${contractItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
        <td ><fmt:formatNumber value="${contractItem.instcont}" type="currency" maxFractionDigits="0" /></td>
        <td ><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td ><b><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td ><b><fmt:formatNumber value="${contractItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
        <td ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
        <td ><b><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>       
          <html:hidden name="contractItem" property="id" indexed="true" />
          <html:hidden name="contractItem" property="fycode" indexed="true" />
          <html:hidden name="contractItem" property="servicetype" indexed="true" />
          <html:hidden name="contractItem" property="servicedescr" indexed="true" />
          <html:hidden name="contractItem" property="recipient" indexed="true" />
          <html:hidden name="contractItem" property="grantrequest" indexed="true" />
          <html:hidden name="contractItem" property="amountapproved" indexed="true" />
          <html:hidden name="contractItem" property="expapproved" indexed="true" /> 
          <html:hidden name="contractItem" property="amtamended" indexed="true" /> 
    </table>    
    
    </logic:iterate>
    </logic:present>
    
    <p><a href="diInitialForms.do?i=fs10a">FS10A - Amendment to Budget Item</a></p>
  
   <logic:notEmpty name="BudgetCollectionBean" property="allContractRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}" >
          <p align="center"><input type="BUTTON" value="Save Contract Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center"><input type="HIDDEN" name="currtab" value="3" />
            <html:submit value="Save Contract Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>
  
  </html:form>
  
  
  </c:when>
  <c:otherwise >
  
  <form method="post" action="AddBudgetItem">    
  <p>
    <input type="submit" value="Add"/>
    Save any changes first before adding a new record.
    <INPUT type="hidden" name="tab" value="3">
    <INPUT type="hidden" name="p" value="di" /></p>
  </form>
  
  <html:errors />
  <html:form action="/saveDiContract">
   <logic:present name="BudgetCollectionBean" property="allContractRecords" >
   <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
   
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <c:url value="diInitialForms.do" var="deleteURL">
        <c:param name="i" value="confirmbdgtdelete" />
        <c:param name="tab" value="3" />
        <c:param name="id" value="${contractItem.id}" />
        <c:param name="p" value="di" />
    </c:url>
    
    <table width="100%" class="boxtype" summary="for layout only">
      <tr >
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
        <td width="16%">ProjTotal</td>
        <td width="16%">InstContrib</td>
        <td width="16%">AmtRequested</td>
        <td width="16%">AmtApproved</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr>
      <tr >
        <td><fmt:formatNumber value="${contractItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
        <td><html:text name="contractItem" property="instcontStr" indexed="true" /></td>
        <td><html:text name="contractItem" property="grantrequestStr" indexed="true" /></td>
        <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${contractItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>    
      <tr>
        <html:hidden name="contractItem" property="id" indexed="true" />
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>  
    
  </logic:iterate>
  </logic:present>
 
  <logic:notEmpty name="BudgetCollectionBean" property="allContractRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}" >
          <p align="center"><input type="BUTTON" value="Save Contract Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center"><input type="HIDDEN" name="currtab" value="3" />
            <html:submit value="Save Contract Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty> 
 </html:form>
 
 
 </c:otherwise>
 </c:choose>
 
 
 
 
<table width="100%" summary="for layout only">
  <tr>
    <td colspan="7"><b>Contracted Service Totals</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.conProjTot}" type="currency" maxFractionDigits="0" /></td>
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
