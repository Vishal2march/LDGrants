<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
    I. Personal Services</b><br/>
    List all persons to be employed by the project and their titles.  
    After each entry indicate the full-time annual salary rate 
    (even if the position is not full-time) and FTE rate.<br/>"FTE" (full time equivalent) 
    is the numerical representation of full or part time activities.  A person working full 
    time is represented by an FTE of 1.0, a person working half-time is 0.5 FTE, and so on.  
  </p>
  
   <table width="100%" summary="for layout only">
    <tr>
      <td class="adminbgtselect">      
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      I. Personal Services</td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=2&p=di">II. Employee Benefits</a></td>
          
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=3&p=di">III. Contracted Services</a></td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=4&p=di">IV. Supplies Materials & Equipment</a></td>
      
      <td class="adminbgt">
      <a href="AdminBudgetSelect?tab=6&p=di">V. Travel Expenses</a></td>
    </tr>
  </table>
  <a name="bdgcontent" id="bdgcontent" />
  <br/><br/>
   
  <c:if test="${totalsBean.warning =='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.diMessage}" /></p>
  </c:if>
  
  <c:if test="${fyTotal.warning=='true'}">
    <p align="center" class="error"><c:out value="${fyTotal.diTotApprMessage}" /> </p>
  </c:if>



  <form method="POST" action="AddBudgetItem">    
    <p><input type="submit" value="Add"/>
       Save any changes first before adding a new record.
       <INPUT type="hidden" name="tab" value="1" />
       <INPUT type="hidden" name="p" value="admindi" /></p>
  </form>  
  
  
  
  <html:errors />
  <html:form action="/saveDiAdminBudget" >
  
  <INPUT type="hidden" name="currtab" value="1"><input type="HIDDEN" name="p" value="di"/>
  
  
  <logic:present name="BudgetCollectionBean" property="allPersRecords" >
  <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" >
  
  
  
  <%-- create url that allows for deletion of this record, uses the expense id --%>   
    <c:url value="diAdminNav.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="1" />
        <c:param name="id" value="${personalItem.id}" />
        <c:param name="p" value="admindi"  />
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
        <td colspan="2">Beginning Date of Employment (mm/dd/yyyy)</td>
        <td colspan="2">Ending Date of Employment (mm/dd/yyyy)</td>
      </tr>
     <tr>
        <td colspan="2"><html:text name="personalItem" property="beginDateStr" indexed="true" /></td>
        <td colspan="2"><html:text name="personalItem" property="endDateStr" indexed="true" /></td>
      </tr>  
      
      
      <tr>
        <td width="14%">ProjTotal</td><td width="14%">Inst Contrib.</td>
        <td width="14%">AmtRequested</td><td width="14%">AmtApproved</td>
        <td width="14%">AmtAmended</td>
        <td width="14%">ExpSubmitted</td><td width="14%">ExpApproved</td>
      </tr>
      <tr>        
        <c:choose >
        <c:when test="${lduser.admindisc=='approve'}" >
            <td><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="personalItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="amtamendedStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
            <td><html:text name="personalItem" property="expapprovedStr" indexed="true" />
              <html:hidden name="personalItem" property="id" indexed="true" /></td>
        </c:when>
        <c:otherwise >
            <td><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></b></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><b><fmt:formatNumber  value="${personalItem.amtamended}" type="currency" maxFractionDigits="0" /></b></td>
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

 <p><a href="diAdminNav.do?item=fs10a">FS10A Record - Budget Amendment</a></p>      



  <c:if test="${lduser.admindisc=='approve'}" >
   <p align="center">
      <input type="SUBMIT" name="btn" value="Copy Amt Requested" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="SUBMIT" name="btn" value="Copy Exp Submitted" />
      <br/><br/>
      <input type="submit" name="btn" value="Save"/>
    </p>
  </c:if>
</html:form>




 
<table width="100%" align="center" summary="for layout only">  
  <tr>
    <td colspan="7"><b>Personal Service Totals</b></td>
  </tr>
  <tr>
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.perProjTot}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perInstCont}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="4"></td>
  </tr>
  <tr>
    <td colspan="7"><b>Grand Totals for all Budget Categories</b></td>
  </tr>
  <tr>   
    <td>Project Total</td>
    <td>Inst Contrib.</td>
    <td>Amount Requested</td>
    <td >Amount Approved</td>
    <td>Amount Amended</td>
    <td >Expense Submitted</td>
    <td >Expense Approved</td>
  </tr>
  <tr>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0" /></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0" /></b>
    </td>
  </tr>
  <tr>
    <td height="15" colspan="7"></td>
  </tr>
  <tr>
    <td colspan="3"><b>Total C/P Discretionary available for FY <fmt:formatNumber value="${fyTotal.fycode}" minIntegerDigits="2" />:</b></td>
    <td><fmt:formatNumber value="${fyTotal.amtavailable}" type="currency" maxFractionDigits="0"/></td>
  </tr>
  <tr>
    <td colspan="3"><b>Total C/P Discretionary Awarded:</b></td>
    <td><fmt:formatNumber value="${fyTotal.totAmtAppr}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
    <td colspan="3"><b>Difference:</b></td>
    <td><fmt:formatNumber value="${fyTotal.amtdifference}" type="currency" maxFractionDigits="0" /></td>
  </tr>
  <tr>
    <td height="15" colspan="7"></td>
  </tr>
 

  <tr>
    <td colspan="7" align="center">    
        <c:url var="backURL" value="diAdminNav.do">
          <c:param name="id" value="${grantid}" />
          <c:param name="item" value="grant" />
        </c:url>
        <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
    </td>
  </tr>   
</table>

  
  </body>
</html>
