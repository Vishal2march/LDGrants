<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%><%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab1_adm.jsp
 * Creation/Modification History  :
 *
 *     SH       3/1/07     Created
 *
 * Description
 * This is the Admin Personal Service Expense page.  It allows admin to review the
 * personal expenses entered by applicants, and then approve an award amount. Also
 * displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- PERSONAL SERVICES -->

<html>
<head>
  <title></title>
</head>
  <body>
  
  <h4>Project Budget</h4>
  
  
  <table width="100%" summary="for layout only">
    <tr>
      <td class="adminbgtselect">I. Personal Services</td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=2&p=sa">II. Employee Benefits</a></td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=3&p=sa">III. Contracted Services</a></td>
      
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


  <form method="POST" action="AddBudgetItem">    
    <p><input type="submit" value="Add"/>
       Save any changes first before adding a new record.
       <INPUT type="hidden" name="tab" value="1" />
       <INPUT type="hidden" name="p" value="adminsa" /></p>
  </form>  



  <html:errors />
  <html:form action="/saveSaAdminBudget" >
  
   <INPUT type="hidden" name="currtab" value="1"><input type="HIDDEN" name="p" value="sa"/>
    
    <logic:present name="BudgetCollectionBean" property="allPersRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" >       
    
    <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <c:url value="saAdminNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="1" />
        <c:param name="id" value="${personalItem.id}" />
        <c:param name="p" value="adminsa"  />
    </c:url>   
    
       
    <table width="100%" align="center" class="boxtype" summary="for layout only">
      <tr >
        <td colspan="2">Name</td>
        <td colspan="2">Title</td>
        <td>Salary/Wage</td>
        <td>FTE/Hours<br/>ex. 1.0</td>    
        <td>Type</td>
      </tr>         
      <tr>
        <td colspan="2"><html:text name="personalItem" property="name" indexed="true" /></td>
        <td colspan="2"><html:text name="personalItem" property="title" indexed="true" /></td>
        <td><html:text name="personalItem" property="salaryrate" indexed="true" /></td>
        <td><html:text name="personalItem" property="fteStr" indexed="true" /></td>
       
        <td><html:select name="personalItem" property="typeCode" indexed="true">
              <c:if test="${personalItem.typeCode==0}" >
                <option value="3" selected="selected">Professional</option>
                <option value="4">Support Staff</option>
              </c:if> 
              <c:if test="${personalItem.typeCode==3}" >
                <option value="3" selected="selected">Professional</option>
                <option value="4">Support Staff</option>  
              </c:if>
              <c:if test="${personalItem.typeCode==4}" >
                <option value="3">Professional</option>
                <option value="4" selected="selected">Support Staff</option>
              </c:if>                 
            </html:select></td>
      </tr>        
      <tr>
        <td>AmtRequested</td><td>AmtApproved</td><td>AmtAmended</td>
        <td>ExpSubmitted</td><td>ExpApproved</td>
      </tr>
      <tr>        
        <c:choose >
        <c:when test="${lduser.adminstat=='approve'}" >
            <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="expapprovedStr" indexed="true" />
              <html:hidden name="personalItem" property="id" indexed="true" /></td>
        </c:when>
        <c:otherwise >
          <td><b><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
          <td><b><fmt:formatNumber value="${personalItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
          <td><b><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
          <td><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></td>         
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
      <td height="10" />
    </tr>
    <tr>
      <td align="center" colspan="5"><input type="submit" name="btn" value="Save"/></td>
    </tr>
  </c:if>
 </table>
</html:form>
  

<br/><br/>
<table width="100%" align="center" summary="for layout only">
  <tr>
    <td colspan="5" ><b>Personal Service Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.perAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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