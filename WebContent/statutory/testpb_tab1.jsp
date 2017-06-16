<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab1.jsp
 * Creation/Modification History  :
 *     SLowe                   Created
 *     SHusak       3/1/07     Modified
 *
 * Description
 * This is the Personal Service Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- PERSONAL SERVICES --%>
<html>
<head>
  <title></title>
  <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
</head>
<body>


<p class="bdgtitle"><b>Project Budget<br/>
  I. Personal Services</b><br/>
  List all persons to be employed with State Aid funds and their titles.  
  After each entry indicate the full-time annual salary rate 
  (even if the position is not full-time) and FTE rate.
</p>
 
  
   <table width="100%" summary="for layout only">
    <tr>
      <td class="scbgtselect">      
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      I. Personal Services</td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=2&p=sa">II. Employee Benefits</a></td>
          
      <td class="scbgt">
      <a href="BudgetSelect?tab=3&p=sa">III. Contracted Services</a></td>
      
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
  
    <logic:present name="BudgetCollectionBean" property="allPersRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" >   
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td width="20%">Name</td><td width="20%">Title</td>
        <td width="20%">Salary/Wage</td><td width="20%">FTE/Hours</td>     
      </tr>      
      <tr>
        <td><c:out value="${personalItem.name}" /></td>
        <td><c:out value="${personalItem.title}" /></td>
        <td><fmt:formatNumber value="${personalItem.salaryf}" type="currency" /></td>
        <td><c:out value="${personalItem.fte}" /></td>
      </tr>       
      <tr>
        <td>AmtRequested</td>
        <td>AmtApproved</td>
        <td>AmtAmended</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <tr>
        <td ><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td ><b><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="personalItem" property="amtamendedStr" indexed="true" /></td>
        <td ><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
        <td ><b><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>
      
          <html:hidden name="personalItem" property="id" indexed="true" />
          <html:hidden name="personalItem" property="fycode" indexed="true" />
          <html:hidden name="personalItem" property="name" indexed="true" />
          <html:hidden name="personalItem" property="title" indexed="true" />
          <html:hidden name="personalItem" property="salaryrate" indexed="true" />
          <html:hidden name="personalItem" property="fte" indexed="true" />
          <html:hidden name="personalItem" property="grantrequest" indexed="true" />
          <html:hidden name="personalItem" property="amountapproved" indexed="true" />
          <html:hidden name="personalItem" property="expapproved" indexed="true" />
    </table>
    
  </logic:iterate>
  </logic:present>
  
  <p><a href="saApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></p>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allPersRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Personal Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="1" />
          <html:submit value="Save Personal Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>
  </html:form>
  
  
  </c:when>
  <c:otherwise >
  
  <form method="POST" action="AddBudgetItem">
    <INPUT type="hidden" name="p" value="sa">
    <INPUT type="hidden" name="tab" value="1">
    <p><input type="submit" value="Add"/>
       Please save any changes first before adding a new record.</p>
  </form>  
    
    
  <% int index =0; %>
  <html:errors />
  <html:form action="/savePersonalBudget">
  
    <logic:present name="BudgetCollectionBean" property="allPersRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <%--<c:url value="DeleteItem.do" var="deleteURL">
        <c:param name="item" value="budget" />
        <c:param name="tab" value="1" />
        <c:param name="id" value="${personalItem.id}" />
        <c:param name="desc" value="${personalItem.name}" />
        <c:param name="p" value="sa" />
    </c:url>--%>
    <c:url value="saApplicantForms.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="1" />
        <c:param name="id" value="${personalItem.id}" />
        <c:param name="p" value="sa" />
    </c:url>
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr >
        <td>Name</td>
        <td>Title</td>
        <td>Salary/Wage</td>
        <td>FTE/Hours</td>    
        <td>Salary*FTE</td>
      </tr>        
      <tr>
        <td><html:text name="personalItem" property="name" indexed="true" maxlength="50" /></td>
        <td><html:text name="personalItem" property="title" indexed="true" maxlength="50" /></td>
        <td><html:text name="personalItem" property="salaryrate" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
        <td><html:text name="personalItem" property="fteStr" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
       
        <td><html:text name="personalItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
      </tr>        
      <tr>
        <td>AmtRequested</td>
        <td>AmtApproved</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <tr>
        <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>  
      <tr>        
        <td><html:hidden name="personalItem" property="id" indexed="true" />
            <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>  
    <% index++; %>
       
  </logic:iterate>
  </logic:present>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allPersRecords">
    <c:choose >
    <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
      <p align="center"><input type="BUTTON" value="Save Personal Expenses" disabled="disabled" /></p>
    </c:when>
    <c:otherwise >
      <p align="center"><input type="HIDDEN" name="currtab" value="1" />
        <html:submit value="Save Personal Expenses" /></p>
    </c:otherwise>
    </c:choose>
 </logic:notEmpty>
 
  </html:form>
  
 </c:otherwise>
 </c:choose>




<%-- bottom section containing totals for this expense and total expenses--%>
<table width="100%" summary="for layout only">
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
    <td><fmt:formatNumber value="${totalsBean.perAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtAmend}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.perExpSub}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.perExpAppr}" type="currency" maxFractionDigits="0"/></td> 
  </tr>
  <tr>
    <td height="15" colspan="4"></td>
  </tr>
  <tr>
    <td colspan="4" ><b>Grand Totals for all Budget Categories</b></td>
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
       <td class="error" >
        <fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0" />
       </td>
    </c:when>
    <c:otherwise >
       <td><b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b></td>
    </c:otherwise>
    </c:choose>
    
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0"/></b></td>
    
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0"/></b></td>
        
    <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0"/></b></td>
    
    <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0"/></b></td>
  </tr>
</table>

</body>
</html>
