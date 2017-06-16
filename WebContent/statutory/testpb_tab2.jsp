<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab2.jsp
 * Creation/Modification History  :
 *     SLowe                   Created
 *     SHusak       3/1/07     Modified
 *
 * Description
 * This is the Employee Benefits Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- EMPLOYEE BENEFITS -->
<html>
<head>
  <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
  <title></title>
</head>

<body>
<p class="bdgtitle"><b>Project Budget<br/>
  II. Employee Benefits</b><br/>
  List all persons to be employed by the project as described under "Personal Services."  
  Provide the total amount of State Aid funds to be used to provide benefits for each person and the total for all personnel.
</p>


  <table width="100%" summary="for layout only">
    <tr>
      <td class="scbgt">
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      
      <a href="BudgetSelect?tab=1&p=sa">I. Personal Services</a></td>        
      
      <td class="scbgtselect">II. Employee Benefits</td>        
      
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
      <tr >
        <td>AmtRequested</td>
        <td>AmtApproved</td>
        <td>AmtAmended</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <tr >
        <td ><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td ><b><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="benefitItem" property="amtamendedStr" indexed="true" /></td>
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
     </table>    
    
   </logic:iterate>
   </logic:present>
   
   <p><a href="saApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></p>
   
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
    <INPUT type="hidden" name="p" value="sa">
    <INPUT type="hidden" name="tab" value="2">
    <p><input type="submit" value="Add"/>
       Please save any changes first before adding a new record.</p>
  </form>
   
    <% int index =0; %>
    <html:errors />
    <html:form action="/saveBenefitBudget">
    <logic:present name="BudgetCollectionBean" property="allBenRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
          
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="saApplicantForms.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="2" />
        <c:param name="id" value="${benefitItem.id}" />
        <c:param name="p" value="sa" />
    </c:url>
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td>Name</td>   
        <td>Benefits Percentage (decimal)</td>
        <td>Salary*FTE</td>
        <td>BenefitsAmt</td>
      </tr>      
      <tr>
        <td><html:text name="benefitItem" property="name" indexed="true" /></td>
        <td><html:text name="benefitItem" property="benpercentStr" indexed="true" onchange='<%= "calcBenefits(" + index + ");" %>' /></td>
        <td><html:text name="benefitItem" property="salary" indexed="true" readonly="true" style="background-color:silver" /></td>
        <td><html:text name="benefitItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
      </tr>        
      <tr>
        <td>AmtRequested</td>
        <td>AmtApproved</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <tr>
        <td><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>                
      <tr>        
        <td><html:hidden name="benefitItem" property="id" indexed="true" />
            <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
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
    <td colspan="5" ><b>Employee Benefits Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
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
    <td colspan="4"><b>Grand Totals for all Budget Categories</b></td>
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
    <c:when test="${totalsBean.warning =='true'}">
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

