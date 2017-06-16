<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab1.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/17/07     Created
 *
 * Description
 * This is the Di apcnt budget page for personal services.  Split into 2 sections for
 * initial budget and final expenses.  Has option to add record for 1 year only. 
--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
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
      <td class="dibgtselect">      
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      I. Personal Services</td>
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=2&p=di" >II. Employee Benefits</a></td>
          
      <td class="dibgt">
      <a href="BudgetSelect?tab=3&p=di">III. Contracted Services</a></td>
      
      <td class="dibgt">
      <a href="BudgetSelect?tab=4&p=di">IV. Supplies Materials & Equipment</a></td>
            
      <td class="dibgt">
      <a href="BudgetSelect?tab=6&p=di">V. Travel Expenses</a></td>
    </tr>
  </table>
  <a name="bdgcontent" id="bdgcontent" /><br/>
  
  
  <c:if test="${totalsBean.warning =='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.diMessage}" /></p>
  </c:if>

        
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >  
  
  <html:errors />
  <html:form action="/saveDiPersExpenses" >
  
    <logic:present name="BudgetCollectionBean" property="allPersRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" >   
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>      
        <td colspan="2">Name</td><td colspan="2">Title</td>
        <td>Salary/Wage</td><td>FTE/Hours<br/>ex. 1.0</td><td>Type</td>     
      </tr>      
      <tr>
        <td colspan="2"><c:out value="${personalItem.name}" /></td>
        <td colspan="2"><c:out value="${personalItem.title}" /></td>
        <td><fmt:formatNumber value="${personalItem.salaryf}" type="currency" /></td>
        <td><c:out value="${personalItem.fte}" /></td>
        <td><c:if test="${personalItem.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${personalItem.typeCode=='4'}" >
                Support Staff
              </c:if></td>
      </tr>       
      
      <%--only display new fs10f fields starting 2014-15--%>
      <c:choose>
      <c:when test="${thisGrant.fycode>14}">
          <tr>
            <td colspan="2">Beginning Date of Employment (mm/dd/yyyy)</td>
            <td colspan="2">Ending Date of Employment (mm/dd/yyyy)</td>
          </tr>
          <tr>
            <td colspan="2"><html:text name="personalItem" property="beginDateStr" indexed="true" /></td>
            <td colspan="2"><html:text name="personalItem" property="endDateStr" indexed="true" /></td>
          </tr>   
      </c:when>
      <c:otherwise>
            <html:hidden name="personalItem" property="beginDateStr" indexed="true" />
            <html:hidden name="personalItem" property="endDateStr" indexed="true" />
      </c:otherwise>
      </c:choose>
      
      
      <tr>
        <td width="14%">ProjTotal</td>
        <td width="14%">InstContrib</td>
        <td width="14%">AmtRequested</td>
        <td width="14%">AmtApproved</td>
        <td width="14%">AmtAmended</td>
        <td width="14%">ExpSubmitted</td>
        <td width="14%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${personalItem.instcont}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><b><fmt:formatNumber value="${personalItem.amtamended}"  type="currency" maxFractionDigits="0" /></b></td>
        <td><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>
          <html:hidden name="personalItem" property="id" indexed="true" />
          <html:hidden name="personalItem" property="fycode" indexed="true" />
          <html:hidden name="personalItem" property="name" indexed="true" />
          <html:hidden name="personalItem" property="title" indexed="true" />
          <html:hidden name="personalItem" property="salaryrate" indexed="true" />
          <html:hidden name="personalItem" property="salaryf" indexed="true" />
          <html:hidden name="personalItem" property="typeCode" indexed="true" />
          <html:hidden name="personalItem" property="fte" indexed="true" />
          <html:hidden name="personalItem" property="grantrequest" indexed="true" />
          <html:hidden name="personalItem" property="amountapproved" indexed="true" />
          <html:hidden name="personalItem" property="expapproved" indexed="true" />
          <html:hidden name="personalItem" property="amtamended" indexed="true" />
    </table>
    
  </logic:iterate>
  </logic:present>
  
  <p><a href="diInitialForms.do?i=fs10a">FS10A - Amendment to Budget Item</a></p>
  
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
    <p><input type="submit" value="Add"/>
       Save any changes first before adding a new record.
       <INPUT type="hidden" name="tab" value="1" />
       <INPUT type="hidden" name="p" value="di" /></p>
  </form>  
        
  <% int index =0; %>
  <html:errors />
  <html:form action="/saveDiPersonal">
  
    <logic:present name="BudgetCollectionBean" property="allPersRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <%--<c:url value="DiDeleteItem.do" var="deleteURL">
        <c:param name="item" value="budget" />
        <c:param name="tab" value="1" />
        <c:param name="id" value="${personalItem.id}" />
        <c:param name="desc" value="${personalItem.name}" />
        <c:param name="p" value="di" />
    </c:url>--%>    
    <c:url value="diInitialForms.do" var="deleteURL">
        <c:param name="i" value="confirmbdgtdelete" />
        <c:param name="tab" value="1" />
        <c:param name="id" value="${personalItem.id}" />
        <c:param name="p" value="di" />
    </c:url>
    
    <table class="boxtype" width="100%" summary="for layout only">
      <tr >
        <td>Name</td>
        <td>Title</td>
        <td>Salary/Wage</td>
        <td>FTE/Hours<br/>ex. 1.0</td>    
        <td>Salary*FTE or Wage*Hours</td>
        <td>Type</td>
      </tr>        
      <tr>
        <td><html:text name="personalItem" property="name" indexed="true" /></td>
        <td><html:text name="personalItem" property="title" indexed="true" /></td>
        <td><html:text name="personalItem" property="salaryrate" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
        <td><html:text name="personalItem" property="fteStr" indexed="true" onchange='<%= "calcSalary(" + index + ");" %>' /></td>
       
        <td><html:text name="personalItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
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
        <td width="16%">ProjTotal</td>
        <td width="16%">InstContrib</td>
        <td width="16%">AmtRequested</td>
        <td width="16%">AmtApproved</td>
        <td width="16%">ExpSubmitted</td>
        <td width="16%">ExpApproved</td>
      </tr>
      <tr>
        <td><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
        <td><html:text name="personalItem" property="instcontStr" indexed="true" /></td>
        <td><html:text name="personalItem" property="grantrequestStr" indexed="true" /></td>
        <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
      </tr>  
      <tr>
        <html:hidden name="personalItem" property="id" indexed="true" />
        <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
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
    <td colspan="7" ><b>Personal Service Totals</b></td>
  </tr>
  <tr>
    <td width="14%">Project Total</td>
    <td width="14%">Inst Contrib.</td>
    <td width="14%">Amount Requested</td>
    <td width="14%">Amount Approved</td>
    <td width="14%">Amount Amended</td>
    <td width="14%">Expense Submitted</td>
    <td width="14%">Expense Approved</td>
  </tr> 
  <tr>
    <td><fmt:formatNumber value="${totalsBean.perProjTot}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perInstCont}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.perAmtAmend}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.perExpSub}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.perExpAppr}" type="currency" maxFractionDigits="0"/></td> 
  </tr>
  <tr>
    <td height="15" colspan="7"></td>
  </tr>
  <tr>
    <td colspan="7"><b>Grand Totals for all Budget Categories</b></td>
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
    <td>
      <b><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency" maxFractionDigits="0"/></b>
    </td>

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
      <b><fmt:formatNumber value="${totalsBean.totAmtAppr}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totAmtAmend}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0"/></b>
    </td>
    <td>
      <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0"/></b>
    </td>
  </tr>
</table>
  
  
  </body>
</html>
