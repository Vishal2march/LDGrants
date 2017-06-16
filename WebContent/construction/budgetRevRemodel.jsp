<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!-- MINOR REMODELING - SH not being used as of 5/3/11 per MLT-->
<html>
<head>
</head>
<body>

<p class="bdgtitle"><b>Project Budget<br/>
  III. Minor Remodeling</b><br/>
  List specific project details and expenses related to minor remodeling.  
  All expenses listed in this section must be fully described in the "Project Description."
</p>

  <table width="100%" summary="for layout only">
    <tr>   
      <td class="dibgt">
      <a href="BudgetSelect?tab=3&p=cnrev">I. Purchased Services</a></td>
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=4&p=cnrev">II. Supplies Materials & Equipment</a></td>        
            
      <td class="dibgtselect">III. Minor Remodeling</td>
    </tr>
  </table>
<br/>

    
  <c:choose >
  <c:when test="${assignBean.systemLockOut=='true'}" >  <!-- READ ONLY AFTER SUBMIT TO LD -->
  
  <html:form action="/saveCnRemodelReview" >
    <logic:present name="BudgetCollectionBean" property="allExpRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" > 
          
     <table class="boxtype" width="100%" summary="for layout only" >
      <tr>
        <td colspan="3">Description of work to be performed</td>    
      </tr>      
      <tr>
        <td colspan="3"><c:out value="${otherExpItem.description}" /></td>
      </tr>           
     <tr>
        <td width="25%">Cost</td>
        <td width="25%">AmtApproved</td>
        <td width="25%">ExpSubmitted</td>
        <td width="25%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${otherExpItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
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
 </html:form>
 
  </c:when>
  <c:otherwise >
  
  <%-- INITIAL BUDGET --%>
  
  <form method="POST" action="AddBudgetItem">    
    <p>
      <input type="submit" name="btn" value="Add"/>       
      Save any changes first before adding a new record.
      <INPUT type="hidden" name="tab" value="5">
      <INPUT type="hidden" name="p" value="cnrev" /></p>
  </form>
      
    <html:errors />
    <html:form action="/saveCnRemodelReview">
    <logic:present name="BudgetCollectionBean" property="allExpRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" > 
          
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="cnReviewNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="5" />
        <c:param name="id" value="${otherExpItem.id}" />
        <c:param name="p" value="cnrev6" />
    </c:url>    
        
    <table width="100%" align="center" summary="for layout only" class="boxtype" >
      <tr>
        <td colspan="4">Description of work to be performed</td>    
      </tr>      
      <tr>
        <td colspan="4"><html:text name="otherExpItem" property="description" indexed="true" size="50" maxlength="255" /></td>
      </tr>  
      <tr>
        <td width="25%">Cost</td>
        <td width="25%">AmtApproved</td>
        <td width="25%">ExpSubmitted</td>
        <td width="25%">ExpApproved</td>
      </tr>
      <tr>
        <td><html:text name="otherExpItem" property="grantrequestStr" indexed="true"/></td>
        <td><fmt:formatNumber value="${otherExpItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${otherExpItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${otherExpItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>       
      <tr>
        <td>
        <html:hidden name="otherExpItem" property="id" indexed="true" />    
        <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>
   
    </logic:iterate>
    </logic:present>
    
    <logic:notEmpty name="BudgetCollectionBean" property="allExpRecords">
      <c:choose >
      <c:when test="${ sgaSubmit=='true' }">
        <p align="center"><input type="BUTTON" value="Save Expenses" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="5" />
          <html:submit value="Save Expenses" /></p>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty> 
  </html:form>
  
  
 </c:otherwise>
 </c:choose> 
 

 <table width="100%" align="center" summary="for layout only">
  <tr>
    <td colspan="4"><b>Remodel Expense Totals</b></td>
  </tr>
   <tr>
    <td width="25%">Total Cost</td>
    <td width="25%">Amount Approved</td>
    <td width="25%">Expense Submitted</td>
    <td width="25%">Expense Approved</td>
  </tr>
   <tr>
    <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="4"></td>
  </tr>
  <tr>
    <td colspan="4"><b>Total Cost for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Total Cost</td>
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
