<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab5.jsp
 * Creation/Modification History  :
 *     SLowe                   Created
 *     SHusak       3/1/07     Modified
 *
 * Description
 * This is the Other/Misc. Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title></title>
</head>

<body>
<p class="bdgtitle"><b>Project Budget<br/>
  V. Other Expenses</b><br/>
  List specific project expenses that do not fit into any other budget category.  
  All entries in this section must be specific and must be fully described in the 
  "Description of State Aid Preservation Activities".
</p>


<table width="100%" summary="for layout only">
  <tr>
    <td class="scbgt"> 
    <%--this link allows Jaws to skip over menu items--%>
    <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
    
    <a href="BudgetSelect?tab=1&p=sa">I. Personal Services</a></td>                
   
    <td class="scbgt">        
    <a href="BudgetSelect?tab=2&p=sa">II. Employee Benefits</a></td>        
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=3&p=sa">III. Contracted Services</a></td>                
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=4&p=sa">IV. Supplies Materials & Equipment</a></td>                
    
    <td class="scbgtselect">V. Other Expenses</td>
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=6&p=sa">VI. Travel Expenses</a></td>
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent" /><br/>


  <c:if test="${totalsBean.warning =='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.saMessage}" /></p>
  </c:if>
 
  
    
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >  
   
  
  <html:errors />
  <html:form action="/saveSaExpense" >
    <logic:present name="BudgetCollectionBean" property="allExpRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" > 
          
     <table class="boxtype" width="100%" summary="for layout only" >
      <tr>
        <td>Service Type</td>    
      </tr>      
      <tr>
        <td colspan="4"><c:out value="${otherExpItem.description}" /></td>
      </tr>      
      <tr>
        <td>AmtRequested</td>
        <td>AmtApproved</td>
        <td>AmtAmended</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <tr >
        <td><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="otherExpItem" property="amtamendedStr" indexed="true" /></td>
        <td><html:text name="otherExpItem" property="expsubmittedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>     
      
        <html:hidden name="otherExpItem" property="description" indexed="true" />
        <html:hidden name="otherExpItem" property="grantrequest"  indexed="true"/>           
        <html:hidden name="otherExpItem" property="amountapproved"  indexed="true"/> 
        <html:hidden name="otherExpItem" property="expapproved"  indexed="true"/>  
        <html:hidden name="otherExpItem" property="id" indexed="true" />       
        <html:hidden name="otherExpItem" property="fycode" indexed="true" />    
    </table>    
    
  </logic:iterate>
  </logic:present>
  
  <p><a href="saApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></p>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allExpRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Other Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="5" />
          <html:submit value="Save Other Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>
  
 </html:form>
  
 
  </c:when>
  <c:otherwise >
  
  
  <br/><br/>
   <p><b>Notice:</b><br/>
     Due to new OSC requirements, the Other Expenses category can no longer be used.  
     Please list budget items under the budget tab categories Personal Services, 
     Employee Benefits, Contracted Services, Supplies,Materials & Equipment, or
     Travel Expenses.</p>
    <br/><br/>
  
  <%-- NO LONGER USING OTHER_EXP CATEGORY PER BL 7/25/08.  NEED TO KEEP OTHER_EXP CATEGORY
  FOR EXP_SUBMITTED FOR 08-09 GRANTS. AFTERWARDS, THIS PAGE CAN BE REMOVED COMPLETELY--%>
  
  
 </c:otherwise>
 </c:choose>
 
 

 <table width="100%" summary="for layout only">
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


