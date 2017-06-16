<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab3_adm.jsp
 * Creation/Modification History  :
 *
 *     SH       3/1/07     Created
 *
 * Description
 * This is the Admin Contracted Service Expense page.  It allows admin to review the
 * contract expenses entered by applicants, and then approve an award amount. Also
 * displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- CONTRACTED SERVICES -->
<html>
  <head>
    <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"></meta>
    <title>State Aid Budget</title>
  </head>  
  <body>
  <h4>Project Budget</h4>


  <table width="100%" summary="for layout only">
    <tr>
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=1&p=sa">I. Personal Services</a></td>
      
      <td class="adminbgt">        
       <a href="AdminBudgetSelect?tab=2&p=sa">II. Employee Benefits</a></td>        
      
      <td class="adminbgtselect">III. Contracted Services</td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=4&p=sa">IV. Supplies Materials & Equipment</a></td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=5&p=sa">V. Other Expenses</a></td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=6&p=sa">VI. Travel Expenses</a></td>
    </tr>
  </table><br/>
  
  <c:if test="${totalsBean.warning=='true'}">
    <p align="center" class="error"> <c:out value="${totalsBean.saMessage}" /></p>
  </c:if>
  
  
  <form method="post" action="AddBudgetItem">    
  <p>
    <input type="submit" value="Add"/>
    Save any changes first before adding a new record.
    <INPUT type="hidden" name="tab" value="3">
    <INPUT type="hidden" name="p" value="adminsa" /></p>
  </form>


<html:form action="/saveSaAdminBudget">

  <INPUT type="hidden" name="currtab" value="3"><input type="HIDDEN" name="p" value="sa"/>
    
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >
           
    <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <c:url value="saAdminNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="3" />
        <c:param name="id" value="${contractItem.id}" />
        <c:param name="p" value="adminsa" />
    </c:url>
    
    
    <table class="boxtype" width="100%" align="center" summary="for layout only">
      <tr>
        <td colspan="2">Service Type</td><td colspan="2">Consultant/Vendor</td>
        <td colspan="2">Description</td>
      </tr>      
      <tr >
        <td colspan="2"><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>
        <td colspan="2"><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
        <td colspan="2"><html:text name="contractItem" property="servicedescr" indexed="true" maxlength="100" /></td>
      </tr>   
      <tr >
        <td width="20%">AmtRequested</td><td width="20%">AmtApproved</td>
        <td width="20%">AmtAmended</td>
        <td width="20%">ExpSubmitted</td><td width="20%">ExpApproved</td>
      </tr>
      <tr >
        <c:choose >
        <c:when test="${lduser.adminstat=='approve'}" >
          <td><html:text name="contractItem" property="grantrequestStr" indexed="true" /></td>
          <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" /></td>
          <td><html:text name="contractItem" property="amtamendedStr" indexed="true" /></td>
          <td ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
          <td><html:text name="contractItem" property="expapprovedStr" indexed="true" />
              <html:hidden name="contractItem" property="id" indexed="true" /></td>
        </c:when>
        <c:otherwise >
          <td><b><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><b><fmt:formatNumber value="${contractItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
          <td><b><fmt:formatNumber value="${contractItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </c:otherwise>
        </c:choose>      
      </tr>        
      <tr>
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>

 </logic:iterate></logic:present>
 
 

<table width="100%" align="center" summary="for layout only">
 <tr>
   <td colspan="5">
     <c:if test="${fsaSize>0}">
       <a href="saAdminNav.do?item=fs10a">FS10A Record - Budget Amendment</a>      
     </c:if>
   </td>
 </tr>
 <tr>
    <td height="10"></td>
  </tr>

  <c:if test="${lduser.adminstat=='approve'}" >
    <tr>
      <td colspan="2" align="center"><input type="SUBMIT" name="btn" value="Copy Amt Requested" /><br/></td>
      <td colspan="2" align="center"><input type="SUBMIT" name="btn" value="Copy Exp Submitted" /><br/></td>
    </tr>
    <tr>
      <td colspan="5" align="center"><input type="submit" name="btn" value="Save"/></td>
    </tr>
  </c:if>
</table>
</html:form>
  
  
<br/><br/>
<table width="100%" align="center" summary="for layout only">
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
    <td height="15" colspan="5"></td>
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
    <td><b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b></td>
        
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b></td>
    
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></b></td>
    
    <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b></td>
    
    <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b></td>
  </tr>
  
  <tr>
    <td colspan="5" align="center">        
        <c:url var="backURL" value="saAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
        <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
    </td>
  </tr>
</table>



</body>
</html>