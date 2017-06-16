<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <link href="css/archivesLDGrants.css" rel="stylesheet" media="screen" type="text/css" />
  </head>
  <body>
  
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
  <c:when test="${lgtab=='1'}">
    <b>Professional Salaries  (Code 15)</b><br/>
    Justify in detail the need for these positions and clearly outline the responsibilities of the positions. 
    Demonstrate why the requested number of hours is needed. Explain how the project staff will support project 
    activities and goals.     
  </c:when>
  <c:when test="${lgtab=='2'}">
    <b>Support Staff Salaries  (Code 16)</b><br/>
    Justify in detail the need for these positions and clearly outline the responsibilities of the positions. 
    Demonstrate why the requested number of hours is needed. Explain how the project staff will support project 
    activities and goals.  
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
  
  
  <html:errors />
  <html:form action="/saveLgAdminBudget" >
  
  <INPUT type="hidden" name="currtab" value="1">
  <input type="HIDDEN" name="p" value='<c:out value="${pagemodule}"/>'/>
  <input type="HIDDEN" name="lgtab" value='<c:out value="${lgtab}"/>'/>
  
  <logic:present name="BudgetCollectionBean" property="allPersRecords" >
  <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" >
         
    <table width="100%" class="boxtype" summary="for layout only">
      <tr>
        <td width="20%">Name</td><td width="20%">Title</td>
        <td width="20%">Rate of Pay</td><td width="20%">Hours Worked</td>
      </tr>      
      <tr>
        <td><c:out value="${personalItem.name}" /></td>
        <td><c:out value="${personalItem.title}" /></td>
        <td><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
        <td><c:out value="${personalItem.fte}" /></td>
      </tr>        
      <tr>
        <td>AmtRequested</td><td>AmtAwarded</td>
        <c:if test="${pagemodule=='lg'}">
          <td>AmtAmended</td>
          <td>ExpSubmitted</td><td>ExpApproved</td>
        </c:if>
      </tr>
      <tr>           
        <td><b><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="personalItem" property="amountapprovedStr" indexed="true" />
            <html:hidden name="personalItem" property="id" indexed="true" /></td>
        
        <c:if test="${pagemodule=='lg'}">
          <td><html:text name="personalItem" property="amtamendedStr" indexed="true"/></td>
          <td><b><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
          <td><html:text name="personalItem" property="expapprovedStr" indexed="true" />
              </td>              
        </c:if>
      </tr>    
      <tr>
        <td colspan="4"><font color="Red">
            <c:if test="${personalItem.adminwarning=='true'}">
                <c:out value="${personalItem.warningmsg}"/>
            </c:if>
        </font></td>
      </tr>
      <tr>
        <td colspan="4" height="25"/>
      </tr>
    </table>
    
 </logic:iterate></logic:present>
 
 
    <p align="center">    
    <c:choose>
    <c:when test="${pagemodule=='lgr' && fyTotal.status!='f'}">
            <input type="button" value="Save" disabled="disabled"/>
    </c:when>
    <c:otherwise>
            <input type="submit" name="btn" value="Save"/>
    </c:otherwise>
    </c:choose>    
    </p> 
 </html:form>


<%-- bottom section containing totals for this expense and total expenses--%>
<br/><br/>
<table width="100%" summary="for layout only">  
  <c:if test="${lgtab=='1'}">
      <tr>
        <td colspan="4"><b>Professional Salaries Totals (Code 15)</b></td>
      </tr>
      <tr>
        <td width="20%">Amount Requested</td>
        <td width="20%">Amount Awarded</td>
        <c:if test="${pagemodule=='lg'}">
          <td width="20%">Amount Amended</td>
          <td width="20%">Expense Submitted</td>
          <td width="25%">Expense Approved</td>
        </c:if>
      </tr> 
    <tr>
      <td><fmt:formatNumber value="${totalsBean.proffAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.proffAmtAppr}" type="currency" maxFractionDigits="0"/></td>
      <c:if test="${pagemodule=='lg'}">
         <td><fmt:formatNumber value="${totalsBean.proffAmtAmend}" type="currency" maxFractionDigits="0"/></td>
         <td><fmt:formatNumber value="${totalsBean.proffExpSub}" type="currency" maxFractionDigits="0"/></td>
         <td><fmt:formatNumber value="${totalsBean.proffExpAppr}" type="currency" maxFractionDigits="0"/></td> 
      </c:if>
    </tr>
  </c:if>
  
  <c:if test="${lgtab=='2'}">
      <tr>
        <td colspan="4"><b>Support Staff Salaries Totals (Code 16)</b></td>
      </tr>
      <tr>
        <td width="20%">Amount Requested</td>
        <td width="20%">Amount Awarded</td>
        <c:if test="${pagemodule=='lg'}">
           <td width="20%">Amount Amended</td>
           <td width="20%">Expense Submitted</td>
           <td width="20%">Expense Approved</td>
        </c:if>
      </tr> 
    <tr>
      <td><fmt:formatNumber value="${totalsBean.profsuppAmtReq}" type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value="${totalsBean.profsuppAmtAppr}" type="currency" maxFractionDigits="0"/></td>
      <c:if test="${pagemodule=='lg'}">
         <td><fmt:formatNumber value="${totalsBean.profsuppAmtAmend}" type="currency" maxFractionDigits="0"/></td>
         <td><fmt:formatNumber value="${totalsBean.profsuppExpSub}" type="currency" maxFractionDigits="0"/></td>
         <td><fmt:formatNumber value="${totalsBean.profsuppExpAppr}" type="currency" maxFractionDigits="0"/></td> 
      </c:if>
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
    <c:if test="${pagemodule=='lg'}">
       <td>Amount Amended</td>
       <td>Expense Submitted</td>
       <td>Expense Approved</td>
    </c:if>
  </tr>
  <tr>
    <td><b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b></td>
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0"/></b></td>
    <c:if test="${pagemodule=='lg'}">
       <td><b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0"/></b></td>
       <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0"/></b></td>
       <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0"/></b></td>
    </c:if>
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
