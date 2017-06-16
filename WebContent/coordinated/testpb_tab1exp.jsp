<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab1exp.jsp
 * Creation/Modification History  :
 *     SHusak       8/10/07     Created
 *
 * Description
 * This is the Personal Service Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!-- PERSONAL SERVICES -->
<html>
<head>
  <title></title>
</head>
<body>


<p class="bdgtitle"><b>Project Budget<br/>
  I. Personal Services</b><br/>
  List all persons to be employed by the project and their titles.  
  After each entry indicate the full-time annual salary rate (even if the position is not full-time) and FTE rate.
</p>
  
   <table width="100%" summary="for layout only">
    <tr>
      <td class="scbgtselect">      
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      I. Personal Services</td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=2&p=coe">II. Employee Benefits</a></td>
          
      <td class="scbgt">
      <a href="BudgetSelect?tab=3&p=coe">III. Contracted Services</a></td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=4&p=coe">IV. Supplies Materials & Equipment</a></td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=5&p=coe">V. Other Expenses</a></td>
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=6&p=coe">VI. Travel Expenses</a></td>  
    </tr>
  </table>
  <a name="bdgcontent" id="bdgcontent" />
  <br/>
  
  
  
    
  <html:errors />
  <html:form action="/updateFinalExpenses" >
  <a name="year1" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>
    <tr>
      <td>   
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${thisGrant.fycode==personalItem.fycode}">
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td colspan="2">Name</td>
            <td>Title</td>
            <td >Salary/Wage</td>
            <td >FTE/Hours</td> 
            <td>Type</td>
          </tr>     
          <tr>
            <td colspan="2"><c:out value="${personalItem.name}" /></td>
            <td><c:out value="${personalItem.title}" /></td>
            <td ><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
            <td ><c:out value="${personalItem.fte}" /></td>
            <td><c:if test="${personalItem.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${personalItem.typeCode=='4'}" >
                Support Staff
              </c:if></td>
          </tr>        
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr>
            <td ><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${personalItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="personalItem" property="amtamendedStr" indexed="true" /></td>
            <td ><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>  
              <html:hidden name="personalItem" property="id" indexed="true" />
              <html:hidden name="personalItem" property="fycode" indexed="true" />
              <html:hidden name="personalItem" property="name" indexed="true" />
              <html:hidden name="personalItem" property="title" indexed="true" />
              <html:hidden name="personalItem" property="salaryrate" indexed="true" />
              <html:hidden name="personalItem" property="fte" indexed="true" />
              <html:hidden name="personalItem" property="projtotal" indexed="true" />
              <html:hidden name="personalItem" property="instcont" indexed="true" />
              <html:hidden name="personalItem" property="grantrequest" indexed="true" />
              <html:hidden name="personalItem" property="amountapproved" indexed="true" />
              <html:hidden name="personalItem" property="expapproved" indexed="true" />
        </table>
         
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
  <hr/><br/>

   
 
  <a name="year2" />
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b></td>
    </tr>
    <tr>
      <td>
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${personalItem.fycode== thisGrant.fycode+1}">
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td colspan="2">Name</td>
            <td>Title</td>
            <td >Salary/Wage</td>
            <td >FTE/Hours</td>       
            <td>Type</td>
          </tr>     
          <tr>
            <td colspan="2"><c:out value="${personalItem.name}" /></td>
            <td><c:out value="${personalItem.title}" /></td>
            <td ><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
            <td ><c:out value="${personalItem.fte}" /></td>
            <td><c:if test="${personalItem.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${personalItem.typeCode=='4'}" >
                Support Staff
              </c:if></td>
          </tr>        
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
         <tr>
            <td ><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${personalItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="personalItem" property="amtamendedStr" indexed="true" /></td>
            <td ><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>  
              <html:hidden name="personalItem" property="id" indexed="true" />
              <html:hidden name="personalItem" property="fycode" indexed="true" />
              <html:hidden name="personalItem" property="name" indexed="true" />
              <html:hidden name="personalItem" property="title" indexed="true" />
              <html:hidden name="personalItem" property="salaryrate" indexed="true" />
              <html:hidden name="personalItem" property="fte" indexed="true" />
              <html:hidden name="personalItem" property="projtotal" indexed="true" />
              <html:hidden name="personalItem" property="instcont" indexed="true" />
              <html:hidden name="personalItem" property="grantrequest" indexed="true" />
              <html:hidden name="personalItem" property="amountapproved" indexed="true" />
              <html:hidden name="personalItem" property="expapproved" indexed="true" />
        </table>
        
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
  <hr/><br/>
  
  
  
  <a name="year3" />
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b></td>
    </tr>
    <tr>
      <td>
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${personalItem.fycode== thisGrant.fycode+2}">
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td colspan="2">Name</td>
            <td>Title</td>
            <td >Salary/Wage</td>
            <td >FTE/Hours</td>    
            <td>Type</td>
          </tr>     
          <tr>
            <td colspan="2"><c:out value="${personalItem.name}" /></td>
            <td><c:out value="${personalItem.title}" /></td>
            <td ><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
            <td ><c:out value="${personalItem.fte}" /></td>
            <td><c:if test="${personalItem.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${personalItem.typeCode=='4'}" >
                Support Staff
              </c:if></td>
          </tr>        
          <tr>
            <td width="14%">ProjTotal</td>
            <td width="14%">InstCont</td>
            <td width="14%">AmtRequested</td>
            <td width="14%">AmtApproved</td>
            <td width="14%">AmtAmended</td>
            <td width="14%">ExpSubmitted</td>
            <td width="14%">ExpApproved</td>
          </tr>
          <tr>
            <td ><fmt:formatNumber value="${personalItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${personalItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td><html:text name="personalItem" property="amtamendedStr" indexed="true" /></td>
            <td ><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${personalItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>  
              <html:hidden name="personalItem" property="id" indexed="true" />
              <html:hidden name="personalItem" property="fycode" indexed="true" />
              <html:hidden name="personalItem" property="name" indexed="true" />
              <html:hidden name="personalItem" property="title" indexed="true" />
              <html:hidden name="personalItem" property="salaryrate" indexed="true" />
              <html:hidden name="personalItem" property="fte" indexed="true" />
              <html:hidden name="personalItem" property="projtotal" indexed="true" />
              <html:hidden name="personalItem" property="instcont" indexed="true" />
              <html:hidden name="personalItem" property="grantrequest" indexed="true" />
              <html:hidden name="personalItem" property="amountapproved" indexed="true" />
              <html:hidden name="personalItem" property="expapproved" indexed="true" />
        </table>
        
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <tr>
        <td><a href="coApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allPersRecords">
        <c:choose >
        <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
          <tr>
            <td align="center"><input type="button" value="Save Personal Expenses" disabled="disabled" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center"><input type="HIDDEN" name="currtab" value="1"/>
                              <input type="SUBMIT" value="Save Personal Expenses" /></td>
          </tr>
        </c:otherwise>
        </c:choose>
    </logic:notEmpty>
  </table>
  </html:form>
  
  
    
  <%-- bottom section containing totals for this expense and total expenses--%>
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td colspan="6" ><b>Personal Service Totals for all Years</b></td>
    </tr>
    <tr>
      <td>Project Total</td>
      <td>Inst Contrib.</td>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Amt Amended</td>
      <td>Exp Submitted</td>
      <td>Exp Approved</td>
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
      <td height="15" colspan="6"><hr/></td>
    </tr>
    <tr>
      <td colspan="6" ><b>Grand Totals for all Budget Categories</b></td>
    </tr>
    <tr>   
      <td>Project Total</td>
      <td>Inst Contrib.</td>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Amt Amended</td>
      <td>Exp Submitted</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td >
        <b><fmt:formatNumber value="${totalsBean.totProjTot}" type="currency" maxFractionDigits="0"/></b>
      </td>
      <td>
        <b><fmt:formatNumber value="${totalsBean.totInstCont}" type="currency" maxFractionDigits="0"/></b>
      </td>
      <td >
        <b><fmt:formatNumber value="${totalsBean.totAmtReq}" type="currency" maxFractionDigits="0"/></b>
      </td>
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



