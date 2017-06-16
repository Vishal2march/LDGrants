<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab5_adm.jsp
 * Creation/Modification History  :
 *
 *     SH       3/1/07     Created
 *
 * Description
 * This is the Admin Other Expense page.  It allows admin to review the
 * other/misc. expenses entered by applicants, and then approve an award amount. Also
 * displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--OTHER EXPENSES -->
<html>
  <head>
    <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"></meta>
    <title></title>
  </head>
  <body>  
  
  <h4>Project Budget</h4>


<table width="100%" summary="for layout only">
  <tr>
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=1&p=sa">I. Personal Services</a></td>
    
    <td class="adminbgt">        
    <a href="AdminBudgetSelect?tab=2&p=sa">II. Employee Benefits</a></td>        
    
    <td class="adminbgt">        
    <a href="AdminBudgetSelect?tab=3&p=sa">III. Contracted Services</a></td>
    
    <td class="adminbgt">
    <a href="AdminBudgetSelect?tab=4&p=sa">IV. Supplies Materials & Equipment</a></td>
    
    <td class="adminbgtselect">V. Other Expenses</td>
    
    <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=6&p=sa">VI. Travel Expenses</a></td>
  </tr>
</table><br/>
  
   <c:if test="${totalsBean.warning=='true'}">
    <p align="center" class="error"> <c:out value="${totalsBean.saMessage}" /></p>
  </c:if>


<html:form action="/saveSaAdminBudget">

  <INPUT type="hidden" name="currtab" value="5"><input type="HIDDEN" name="p" value="sa"/>
    
    <logic:present name="BudgetCollectionBean" property="allExpRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" >    
    
     <table class="boxtype" align="center" width="100%" summary="for layout only">
      <tr>
        <td colspan="4">Service Type</td>    
      </tr>      
      <tr>
        <td colspan="4"><c:out value="${otherExpItem.description}" /></td>
      </tr>      
      <tr>
        <td width="20%">AmtRequested</td><td width="20%">AmtApproved</td>
        <td width="20%">AmtAmended</td>
        <td width="20%">ExpSubmitted</td><td width="20%">ExpApproved</td>
      </tr>
      <tr >
        <c:choose >
        <c:when test="${lduser.adminstat=='approve'}" >
          <td><b><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
          <td><html:text name="otherExpItem" property="amountapprovedStr" indexed="true" /></td>
          <td><b><fmt:formatNumber value="${otherExpItem.amtamended}"  type="currency" maxFractionDigits="0" /></b></td>
          <td><b><fmt:formatNumber value="${otherExpItem.expsubmitted}"  type="currency" maxFractionDigits="0" /></b></td>
          <td><html:text name="otherExpItem" property="expapprovedStr" indexed="true" />
              <html:hidden name="otherExpItem" property="id" indexed="true" /></td>
        </c:when>
        <c:otherwise >
          <td><b><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><b><fmt:formatNumber value="${otherExpItem.amtamended}"  type="currency" maxFractionDigits="0" /></b></td>
          <td><b><fmt:formatNumber value="${otherExpItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
        </c:otherwise>
        </c:choose>      
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
    <td colspan="5" ><b>Other Expense Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
     <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="4"></td>
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
