<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>budgetAdminPersonal</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Project Budget<br/>
    Personal Services</b><br/>
    List all persons to be employed by the project and their titles.  
    After each entry indicate the full-time annual salary rate 
    (even if the position is not full-time) and FTE rate.<br/>"FTE" (full time equivalent) 
    is the numerical representation of full or part time activities.  A person working full 
    time is represented by an FTE of 1.0, a person working half-time is 0.5 FTE, and so on.  
  </p><br/><br/>
  
  
  
  <table width="100%" summary="for layout only">
    <tr>
      <td class="adminbgtselect">Personal Services</td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=2&p=staid">Employee Benefits</a></td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=3&p=staid">Contracted Services</a></td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=4&p=staid">Supplies Materials & Equipment</a></td>
            
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=6&p=staid">Travel Expenses</a></td>
    </tr>
  </table><br/><br/>
  
    
  

  <html:errors />
  <html:form action="/saveStaidAdminBudget" >
  
   <INPUT type="hidden" name="currtab" value="1"><input type="HIDDEN" name="p" value="staid"/>
    
    <logic:present name="BudgetCollectionBean" property="allPersRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" >       
       
    <table width="100%" align="center" class="boxtype" summary="for layout only">
      <tr>
        <td>Name</td><td>Title</td><td>Salary/Wage</td>
        <td>FTE/Hours</td><td>Type</td>
      </tr>      
      <tr>
        <td><c:out value="${personalItem.name}" /></td>
        <td><c:out value="${personalItem.title}" /></td>
        <td><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
        <td><c:out value="${personalItem.fte}" /></td>
        <td><c:if test="${personalItem.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${personalItem.typeCode=='4'}" >
                Support Staff
              </c:if></td>
      </tr>    
      <tr>
        <td colspan="2">Beginning Date of Employment (mm/dd/yyyy)</td>
        <td colspan="2">Ending Date of Employment (mm/dd/yyyy)</td>
      </tr>
      <tr>
        <td colspan="2"><c:out value="${personalItem.beginDateStr}"/></td>
        <td colspan="2"><c:out value="${personalItem.endDateStr}"/></td>
      </tr>   
      <tr>
        <td>AmtRequested</td><td>AmtApproved</td><td>AmtAmended</td>
        <td>ExpSubmitted</td><td>ExpApproved</td>
      </tr>
      <tr>        
        <td><b><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="personalItem" property="amountapprovedStr" indexed="true" /></td>
        <td><html:text name="personalItem" property="amtamendedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="personalItem" property="expapprovedStr" indexed="true" />
            <html:hidden name="personalItem" property="id" indexed="true" /></td>      
      </tr>                 
    </table>
    
</logic:iterate></logic:present>



<table width="100%" align="center" summary="for layout only">    
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
    <td colspan="5"><b>Grand Totals for all Budget Codes</b></td>
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
  <tr>
    <td height="15" colspan="5"></td>
  </tr>
  <tr>
    <td><b>Total Appropriation</b></td>
    <td><b><fmt:formatNumber value="${thisGrant.ldacAppropAmt}" type="currency" maxFractionDigits="0" /></b></td>
  </tr>
  <tr>
    <td height="15" colspan="5"></td>
  </tr>
  <tr>
    <td colspan="5" align="center">    
        <c:url var="backURL" value="staidAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
        <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
    </td>
  </tr>   
</table>
  
  
  </body>
</html>