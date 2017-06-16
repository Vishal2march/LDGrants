<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
  
  <!--- PURCHASED SERVICES --> 

<p class="bdgtitle"><b>Project Budget<br/>
  Purchased Services (Code 40)</b><br/>
  List all services to be purchased for the project, arranged, as appropriate, under Consultant Services or Contracted Services. 
  Attach detailed cost estimates supplied by vendors, quotes and/or bids, or other supporting data in an appendix.<br/>
  <br/>- Consultant Services: include professional and technical advice that will be provided by 
  individuals or groups of individuals.  Consultants are normally retained for a short period 
  to provide advice about specific aspects of the project.  Consultants are normally expected 
  to provide a report of their activities, usually at a time agreed upon before the 
  consultancy begins.  Architectural services are not eligible.  Please see 
  <a href="http://www.nysl.nysed.gov/libdev/excerpts/finished_regs/9012.htm" target="_blank">
  Construction regulations</a> for eligible/ineligible costs.<br/><br/>
  - Contracted Services: include professional or technical activities that will be performed 
  by commercial vendors or qualified individuals.  Contractual services are normally used for
  project activities that cannot be carried out by the institution, or for those activities 
  that can be more economically performed by firms or individuals specializing in a particular
  service. 
  <br/><br/>
  <font color="blue">* Cost is the Cost of project for which funding is being requested.</font></p>

  <table width="100%" summary="for layout only">
    <tr>  
      <td class="dibgtselect">Purchased Services</td>
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=5&p=cn">Supplies & Materials</a></td> 
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=6&p=cn">Equipment</a></td> 
            
      <%--<td class="dibgt">
      <a href="BudgetSelect?tab=5&p=cn">III. Minor Remodeling</a></td>--%>
    </tr>
  </table>
  
    
  <!--   EXPENSE SUBMITTED -->       
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' ||
  appStatus.pendingReview=='true' || lduser.prgconstruction=='read'}" >
  
  <html:errors />
  <html:form action="/saveCnContractExp" >
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
     
    <table class="boxtype" width="100%" summary="for layout only">
      <tr >
        <td>Service Type</td>
        <td>Consultant/Vendor</td>
        <td colspan="2">Description</td>
      </tr>     
       <tr >
        <td><c:out value="${contractItem.servicetype}" /></td>
        <td><c:out value="${contractItem.recipient}" /></td>
        <td colspan="2"><c:out value="${contractItem.servicedescr}" /></td>
      </tr>      
      <tr>
         <td width="25%">Cost<font color="blue">*</font></td>  <!-- AmtRequested -->
        <td width="25%">AmtApproved</td>
        <td width="25%">ExpSubmitted</td>
        <td width="25%">ExpApproved</td>
      </tr>
      <tr>
        <td ><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td ><b><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
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
    </table>    
    
    </logic:iterate>
    </logic:present>
 
   <%--removed 5/17/12 - MLT wants final expenses separate from initial budget
   <logic:notEmpty name="BudgetCollectionBean" property="allContractRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}" >
          <p align="center"><input type="BUTTON" value="Save Purchased Records" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center">
            <input type="HIDDEN" name="currtab" value="3" />
            <input type="HIDDEN" name="i" value="4" />
            <html:submit value="Save Purchased Records" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>--%>
  
  </html:form>
   
  </c:when>
  <c:otherwise >
  
  <%--  THIS IS THE INITIAL BUDGET SECTION--%>
  
  <form method="post" action="AddBudgetItem">    
  <p>
    <input type="submit" value="Add"/>
    Save any changes first before adding a new record.
    <INPUT type="hidden" name="tab" value="3"> 
    <input type="hidden" name="t" value="4"/>
    <INPUT type="hidden" name="p" value="cn" /></p>
  </form>
  
  <html:errors />
  <html:form action="/saveCnContract"> 
   <logic:present name="BudgetCollectionBean" property="allContractRecords" >
   <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
   
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <%-- removed 12/2/11-error when 'desc' field has apostrophe
    <c:url value="CnDeleteItem.do" var="deleteURL">
        <c:param name="item" value="budget" />
        <c:param name="tab" value="3" />
        <c:param name="id" value="${contractItem.id}" />
        <c:param name="desc" value="${contractItem.recipient}" />
        <c:param name="p" value="cn4" />
    </c:url>--%>
    
    <c:url value="constructionForms.do" var="deleteURL">
        <c:param name="i" value="confirmbdgtdelete" />
        <c:param name="tab" value="3" />
        <c:param name="id" value="${contractItem.id}" />
        <c:param name="p" value="cn4" />
    </c:url>    
    
    <table width="100%" class="boxtype" summary="for layout only">
      <tr >
        <td>Service Type</td>
        <td>Consultant/Vendor</td>
        <td colspan="2">Description</td>
      </tr>     
      <tr >
        <td><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
        <td><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
        <td colspan="2"><html:text name="contractItem" property="servicedescr" indexed="true" maxlength="100" /></td>
      </tr>        
      <tr>
        <td width="25%">Cost<font color="blue">*</font></td>
        <td width="25%">AmtApproved</td>
        <td width="25%">ExpSubmitted</td>
        <td width="25%">ExpApproved</td>
      </tr>
      <tr >
        <td><html:text name="contractItem" property="grantrequestStr" indexed="true" /></td><!-- grantrequestStr lose the Str -->
        <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${contractItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>    
      <tr>
        <td>
        <html:hidden name="contractItem" property="id" indexed="true" />
        <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>  
    
  </logic:iterate>
  </logic:present>
 
  <logic:notEmpty name="BudgetCollectionBean" property="allContractRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}" >
          <p align="center"><input type="BUTTON" value="Save Purchased Records" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center">
            <input type="HIDDEN" name="currtab" value="3" />
            <input type="HIDDEN" name="i" value="4" />
            <html:submit value="Save Purchased Records" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty> 
 </html:form>
 
 </c:otherwise>
 </c:choose>
 

<table width="100%" summary="for layout only">
  <tr>
    <td colspan="4"><font color="blue">* Cost is the Cost of project for which funding is being requested.</font></td>
  </tr>
  <tr>
    <td colspan="4"><b>Purchased Service (Code 40) Totals</b></td>
  </tr>
   <tr>
    <td width="25%">Cost<font color="blue">*</font></td>
    <td width="25%">Amount Approved</td>
    <td width="25%">Expense Submitted</td>
    <td width="25%">Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="4"></td>
  </tr>
  <tr>
    <td colspan="4"><b>Total for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Cost<font color="blue">*</font></td>  <!-- amount requested -->
    <td>Amount Approved</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
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
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
     <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
  </tr>  
</table> 
  
    
  </body>
</html>
