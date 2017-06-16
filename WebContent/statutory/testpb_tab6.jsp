<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
  VI. Travel Expenses</b><br/>
  List specific project expenses relating to Travel.   
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
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=5&p=sa">V. Other Expenses</a></td>
    
    <td class="scbgtselect">VI. Travel Expenses</td>
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
    <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" > 
          
     <table class="boxtype" width="100%" summary="for layout only" >
      <tr>
        <td colspan="2">Description</td>    
        <td>Purpose</td>
      </tr>      
      <tr>
        <td colspan="2"><c:out value="${travelItem.description}" /></td>
        <td colspan="3"><c:out value="${travelItem.purpose}" /></td>
      </tr>         
      <tr>
        <td>AmtRequested</td>
        <td>AmtApproved</td>
        <td>AmtAmended</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <tr >
        <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="travelItem" property="amtamendedStr" indexed="true" /></td>
        <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>     
      
        <html:hidden name="travelItem" property="description" indexed="true" />
        <html:hidden name="travelItem" property="grantrequest"  indexed="true"/>           
        <html:hidden name="travelItem" property="amountapproved"  indexed="true"/> 
        <html:hidden name="travelItem" property="expapproved"  indexed="true"/>  
        <html:hidden name="travelItem" property="id" indexed="true" />       
        <html:hidden name="travelItem" property="fycode" indexed="true" />    
    </table>    
    
  </logic:iterate>
  </logic:present>
  
  <p><a href="saApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></p>
      
  <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Travel Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="6" />
          <html:submit value="Save Travel Expenses" /></p>
      </c:otherwise>
      </c:choose>
  </logic:notEmpty>
 </html:form>
 
  </c:when>
  <c:otherwise >
  
  <form method="POST" action="AddBudgetItem">
    <INPUT type="hidden" name="p" value="sa">
    <INPUT type="hidden" name="tab" value="6">
    <p><input type="submit" name="btn" value="Add"/>       
       Please save any changes first before adding a new record.</p>
  </form>
  
    
    <%-- INITIAL BUDGET --%>
    <html:errors />
    <html:form action="/saveTravelBudget">
    <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >
          
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="saApplicantForms.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="6" />
        <c:param name="id" value="${travelItem.id}" />
        <c:param name="p" value="sa" />
    </c:url>
        
    <table width="100%" align="center" summary="for layout only" class="boxtype" >
      <tr>
        <td colspan="2">Description</td>  
        <td colspan="2">Purpose</td>
      </tr>      
      <tr>
        <td colspan="2"><html:text name="travelItem" property="description" indexed="true" /></td>
        <td><html:text name="travelItem" property="purpose" indexed="true" /></td>
      </tr>          
      <tr>
        <td>AmtRequested</td>
        <td>AmtApproved</td>
        <td>ExpSubmitted</td>
        <td>ExpApproved</td>
      </tr>
      <tr>
        <td><html:text name="travelItem" property="grantrequestStr" indexed="true"/></td>
        <td><b><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${travelItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${travelItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>       
      <tr>        
        <td><html:hidden name="travelItem" property="id" indexed="true" />    
            <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>
   
    </logic:iterate>
    </logic:present>
    
    <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Travel Expenses" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="6" />
          <html:submit value="Save Travel Expenses" /></p>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty> 
  </html:form>
  
  
 </c:otherwise>
 </c:choose>
 
 

 <table width="100%" summary="for layout only">
  <tr>
    <td colspan="5" ><b>Travel Expense Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
   <tr>
    <td><fmt:formatNumber value="${totalsBean.travAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.travExpAppr}" type="currency" maxFractionDigits="0" /></td>    
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
