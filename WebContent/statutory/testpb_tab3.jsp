<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab3.jsp
 * Creation/Modification History  :
 *     SLowe                   Created
 *     SHusak       3/1/07     Modified
 *
 * Description
 * This is the Contracted Service Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!-- contracted services -->
<html>
<head>
  <title></title>  
</head>

<body>
<p class="bdgtitle"><b>Project Budget<br/>
  III. Contracted Services</b><br/>
  List all services to be purchased for the project. These include:<br/>
  - Consultant Services: Professional and technical advice that will be provided by individuals or groups of individuals.  Consultants are normally retained for a short period to provide advice about specific aspects of the project.  Consultants are normally expected to provide a report of their activities, usually at a time agreed upon before the consultancy begins.<br/>
  - Contractual Agreements: Professional or technical activities that will be performed by commercial vendors or qualified individuals.  Contractual services are normally used for project activities that cannot be carried out by the institution, or for those activities that can be more economically performed by firms or individuals specializing in a particular service.
</p>


  <table width="100%" summary="for layout only">
    <tr>
      <td class="scbgt">
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      
      <a href="BudgetSelect?tab=1&p=sa">I. Personal Services</a></td>        
      
      <td class="scbgt">        
      <a href="BudgetSelect?tab=2&p=sa">II. Employee Benefits</a></td>        
      
      <td class="scbgtselect">III. Contracted Services</td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=4&p=sa">IV. Supplies Materials & Equipment</a></td>        
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=5&p=sa">V. Other Expenses</a></td> 
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=6&p=sa">VI. Travel Expenses</a></td>
    </tr>
  </table>
  <a name="bdgcontent" id="bdgcontent" /><br/>
  
  
  <c:if test="${totalsBean.warning=='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.saMessage}" /></p>
  </c:if>
  
        
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >
  
  <html:errors />
  <html:form action="/saveSaExpense" >
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
     
    <table class="boxtype" width="100%" summary="for layout only">
      <tr >
        <td width="20%">Service Type</td>
        <td width="20%">Consultant/Vendor</td>
        <td width="20%">Description</td>
      </tr>     
       <tr >
        <td width="20%"><c:out value="${contractItem.servicetype}" /></td>
        <td width="20%"><c:out value="${contractItem.recipient}" /></td>
        <td width="20%"><c:out value="${contractItem.servicedescr}" /></td>
      </tr>      
      <tr>
        <td width="20%">AmtRequested</td>
        <td width="20%">AmtApproved</td>
        <td width="20%">AmtAmended</td>
        <td width="20%">ExpSubmitted</td>
        <td width="20%">ExpApproved</td>
      </tr>
      <tr>
        <td width="20%" ><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td width="20%" ><b><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td width="20%"><html:text name="contractItem" property="amtamendedStr" indexed="true" /></td>
        <td width="20%" ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
        <td width="20%" ><b><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
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
  
  <p><a href="saApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></p>
  
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
    <INPUT type="hidden" name="p" value="sa">
    <INPUT type="hidden" name="tab" value="3">
    <p><input type="submit" value="Add"/>
       Please save any changes first before adding a new record.</p>
  </form>
  
  <html:errors />
  <html:form action="/saveContractBudget">
   <logic:present name="BudgetCollectionBean" property="allContractRecords" >
   <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
   
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <c:url value="saApplicantForms.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="3" />
        <c:param name="id" value="${contractItem.id}" />
        <c:param name="p" value="sa" />
    </c:url>
    
    <table width="100%" class="boxtype" summary="for layout only">
      <tr >
        <td>Service Type</td>
        <td>Consultant/Vendor</td>
        <td>Description</td>
      </tr>     
      <tr >
        <td><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
        <td><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
        <td><html:text name="contractItem" property="servicedescr" indexed="true" maxlength="100" /></td>
      </tr>        
      <tr >
        <td>AmtRequested</td>
        <td>AmtApproved</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <tr >
        <td><html:text name="contractItem" property="grantrequestStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${contractItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>    
      <tr>        
        <td><html:hidden name="contractItem" property="id" indexed="true" />
            <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
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
    <td colspan="5" ><b>Contracted Service Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.conExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="2"></td>
  </tr>
  <tr>
    <td colspan="5"><b>Grand Totals for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
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
    
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b></td>
  </tr>  
</table>


</body>
</html>
