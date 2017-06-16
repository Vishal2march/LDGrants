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
 
  <p class="bdgtitle"><b>Project Budget<br/>
  Minor Remodeling (Code 30) </b><br/>
  Justify the need for the particular remodeling requested and why it is essential to the project.   
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
  
  
  <html:form action="/saveLgAdminBudget">

  <INPUT type="hidden" name="currtab" value="5">
  <input type="HIDDEN" name="p" value='<c:out value="${pagemodule}"/>'/>
  <input type="HIDDEN" name="lgtab" value="4"/>
  
    <logic:present name="BudgetCollectionBean" property="allExpRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allExpRecords" id="otherExpItem" >    
    
     <table class="boxtype" align="center" width="100%" summary="for layout only">
      <tr>
        <td colspan="2">Description of work to be performed</td>    
        <td colspan="2">Calculation of Cost</td>
      </tr>      
      <tr>
        <td colspan="2"><c:out value="${otherExpItem.description}" /></td>
      </tr>
      <tr>
        <td colspan="2"><c:out value="${otherExpItem.costsummary}" /></td>
      </tr>
      <tr>
        <td width="20%">AmtRequested</td><td width="20%">AmtAwarded</td>
        <c:if test="${pagemodule=='lg'}">
           <td width="20%">AmtAmended</td>
           <td width="20%">ExpSubmitted</td><td width="20%">ExpApproved</td>
        </c:if>
      </tr>
      <tr>
        <td><b><fmt:formatNumber value="${otherExpItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="otherExpItem" property="amountapprovedStr" indexed="true" />
            <html:hidden name="otherExpItem" property="id" indexed="true" /></td>
        <c:if test="${pagemodule=='lg'}">
           <td><html:text name="otherExpItem" property="amtamendedStr" indexed="true" /></td>
           <td><b><fmt:formatNumber value="${otherExpItem.expsubmitted}"  type="currency" maxFractionDigits="0" /></b></td>
           <td><html:text name="otherExpItem" property="expapprovedStr" indexed="true" />
               </td>     
        </c:if>
      </tr>    
      <tr>
        <td colspan="4"><font color="Red">
            <c:if test="${otherExpItem.adminwarning=='true'}">
                <c:out value="${otherExpItem.warningmsg}"/>
            </c:if>
        </font></td>
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
  
  
  
  <br/><br/>
 <table width="100%" summary="for layout only">
  <tr>
    <td colspan="4" ><b>Minor Remodeling Totals (Code 30)</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.othAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.othAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <c:if test="${pagemodule=='lg'}">
        <td><fmt:formatNumber value="${totalsBean.othAmtAmend}" type="currency" maxFractionDigits="0" /></td>
       <td><fmt:formatNumber value="${totalsBean.othExpSub}" type="currency" maxFractionDigits="0" /></td>
       <td><fmt:formatNumber value="${totalsBean.othExpAppr}" type="currency" maxFractionDigits="0" /></td>    
    </c:if>
  </tr>
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
    <td><b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b></td>
    <c:if test="${pagemodule=='lg'}">
        <td><b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></b></td>
       <td><b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b></td>
       <td><b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b></td>
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
