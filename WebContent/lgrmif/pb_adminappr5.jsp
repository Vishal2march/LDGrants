<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
    <link href="css/archivesLDGrants.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
  <h2>LGRMIF Admin Budget</h2>
  
  <table summary="for layout only" class="boxtype">
    <tr>
      <td width="40%"><b>Project Number</b></td>
      <td>05<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
              -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
              -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4"/></td>
    </tr>
    <tr>
      <td><b>Institution</b></td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td><b>Category</b></td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
 </table>
 <br/>
 
  <c:set var="lgtab" value="${param.p}" />
   <p class="bdgtitle"><b>Project Budget</b><br/>
  
  <c:choose >
  <c:when test="${lgtab=='5'}">
    <b>Purchased Services (Code 40)</b><br/>
    Describe how each of the purchased services supports the project’s activities and goals. Clearly explain and 
    justify the consultant or vendor’s role in and time spent on the project, and demonstrate that the consultant 
    or vendor is qualified to conduct this work. List purchased services from a BOCES under Code 49. 
  </c:when>
  <c:when test="${lgtab=='6'}">
    <b>Purchased Services - BOCES  (Code 49)</b><br/>
    Describe how each of the purchased services with BOCES supports project activities and goals. Clearly explain 
    and justify the consultant or vendor’s role in and time spent on the project, and demonstrate that the BOCES 
    is qualified to conduct this work.  
  </c:when>
  </c:choose>
   </p><br/><br/>
   
   <font color="Red">
   <c:if test="${fyTotal.warning=='true'}">
    <p align="center"><c:out value="${fyTotal.lgWarning}" /> </p>
  </c:if>   
  
  <c:if test="${fyTotal.notice=='true'}">
    <p align="center"><c:out value="${fyTotal.lgNotice}" /> </p>
  </c:if>
  
  <c:if test="${totalsBean.amtover=='true'}">
    <p align="center"><c:out value="${totalsBean.amtoverWarning}" /> </p>
  </c:if> 
  </font>
   
   <form method="post" action="AddBudgetItem">    
    <p><input type="submit" value="Add"/>
    <i>Click here to initiate an entry under each budget category, 
       and save any changes before adding a new record. <br/>
       Delete any blank/unused records that have been created for a budget category code.</i>
    <INPUT type="hidden" name="tab" value="3">
    <INPUT type="hidden" name="p" value="lga" />
    <input type="HIDDEN" name="lgtab" value='<c:out value="${lgtab}" />' /></p>
  </form>
   
   <html:errors/>
   <html:form action="/saveLgAdminBudget">

  <INPUT type="hidden" name="currtab" value="3">
  <input type="HIDDEN" name="p" value='<c:out value="${pagemodule}"/>'/>
  <input type="HIDDEN" name="lgtab" value='<c:out value="${lgtab}"/>'/>
  
    <logic:present name="BudgetCollectionBean" property="allContractRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >
       
     <%-- create url that allows for deletion of this record, uses the expense id --%>   
     <c:url value="lgAdminNav.do" var="deleteURL">
          <c:param name="item" value="confirmbdgtdelete" />
          <c:param name="tab" value="3" />
          <c:param name="id" value="${contractItem.id}" />
          <c:param name="p" value="lga${lgtab}" />
      </c:url>
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td>Description</td><td>Service Provider</td>
        <td colspan="2">Calculation of Cost</td>
      </tr>      
      <tr >
        <td><html:text name="contractItem" property="servicedescr" indexed="true" maxlength="100" /></td>
        <td><html:text name="contractItem" property="recipient" indexed="true" maxlength="50" /></td>
        <td colspan="2"><html:text name="contractItem" property="servicetype" indexed="true" maxlength="50" /></td>        
      </tr>      
      <tr>
        <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
        <td colspan="2">Check/Journal Entry#</td>
        <td>Service Provider Used</td>
      </tr> 
      <tr>
        <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
        <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
        <td><html:text name="contractItem" property="providerUsed" indexed="true" /></td>
      </tr>   
      <tr >
        <td width="20%">AmtRequested</td><td width="20%">AmtAwarded</td>
        <td width="20%">AmtAmended</td>
        <td width="20%">ExpSubmitted</td><td width="20%">ExpApproved</td>
      </tr>
      <tr >
        <td><html:text name="contractItem" property="grantrequestStr" indexed="true" /></td>
        <td><html:text name="contractItem" property="amountapprovedStr" indexed="true" />
            <html:hidden name="contractItem" property="id" indexed="true" />
            <html:hidden name="contractItem" property="typeCode" indexed="true" /></td>
        <td><html:text name="contractItem" property="amtamendedStr" indexed="true"/></td>
        <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
        <td><html:text name="contractItem" property="expapprovedStr" indexed="true" /></td>     
      </tr>    
      <tr>
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
      <tr>
        <td colspan="4"><font color="Red">
            <c:if test="${contractItem.adminwarning=='true'}">
                <c:out value="${contractItem.warningmsg}"/>
            </c:if>
        </font></td>
      </tr>
    </table>
 
 </logic:iterate></logic:present>
 
    <p align="center"><input type="submit" name="btn" value="Save"/></p> 
</html:form>  



 <br/><br/>
 <table width="100%" summary="for layout only">  
  <c:if test="${lgtab=='5'}">
      <tr>
        <td colspan="4"><b>Purchased Service Totals (Code 40)</b></td>
      </tr>
       <tr>
        <td width="20%">Amount Requested</td>
        <td width="20%">Amount Awarded</td>
        <td width="20%">Amount Amended</td>
        <td width="20%">Expense Submitted</td>
        <td width="20%">Expense Approved</td>
      </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.purchAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.purchAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.purchAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.purchExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.purchExpAppr}" type="currency" maxFractionDigits="0" /></td>    
    </tr>
  </c:if>
    
  <c:if test="${lgtab=='6'}">
      <tr>
        <td colspan="4"><b>Purchased Service - BOCES Totals (Code 49)</b></td>
      </tr>
       <tr>
        <td width="20%">Amount Requested</td>
        <td width="20%">Amount Awarded</td>
        <td width="20%">Amount Amended</td>
        <td width="20%">Expense Submitted</td>
        <td width="20%">Expense Approved</td>
      </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.bocesAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.bocesAmtAppr}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.bocesAmtAmend}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.bocesExpSub}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.bocesExpAppr}" type="currency" maxFractionDigits="0" /></td>    
    </tr>
  </c:if>  
  
  <tr>
    <td height="15" colspan="4"></td>
  </tr>
  <tr>
    <td colspan="4"><b>Grand Totals for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Amount Requested</td>
    <td>Amount Awarded</td>
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
    <td height="20"/>
  </tr>
  <tr>
    <td><b>Amount available to panel:</b></td>
    <td><fmt:formatNumber value="${fyTotal.amtavailable}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
    <td><b>Amount awarded by panel:</b></td>
    <td><fmt:formatNumber value="${fyTotal.totAmtAppr}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
    <td><b>Difference:</b></td>
    <td><fmt:formatNumber value="${fyTotal.amtdifference}" type="currency" maxFractionDigits="0" /></td>
  </tr>
</table> 
 
  
  </body>
</html>