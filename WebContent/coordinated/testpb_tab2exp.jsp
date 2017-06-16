<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab2exp.jsp
 * Creation/Modification History  :
 *     SHusak       8/10/07     Modified
 *
 * Description
 * This is the Employee Benefits Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!-- EMPLOYEE BENEFITS -->
<html>
<head>
</head>
<body>
<p class="bdgtitle"><b>Project Budget<br/>
  II. Employee Benefits</b><br/>
  List all persons to be employed by the project as described under "Personal Services", 
  except those affiliated with the four SUNY centers. The Project Total will be calculated based
  on the Institutional Contribution and Amount Requested.
</p>


  <table width="100%" summary="for layout only">
    <tr>
      <td class="scbgt">
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      
      <a href="BudgetSelect?tab=1&p=coe">I. Personal Services</a></td>        
      
      <td class="scbgtselect">II. Employee Benefits</td>        
      
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
  <br/><br/>
  
  
  
  <a name="year1" />
  <html:errors />
  <html:form action="/updateFinalExpenses" >
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>    
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        <c:if test="${thisGrant.fycode==benefitItem.fycode}">   
         
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Name</td>   
            <td colspan="2">Benefits Percentage</td>
          </tr>      
          <tr>
            <td colspan="2"><c:out value="${benefitItem.name}" /></td>
            <td colspan="2"><fmt:formatNumber value="${benefitItem.benpercent}"/></td>
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
            <td ><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td ><html:text name="benefitItem" property="amtamendedStr" indexed="true" /></td>            
            <td ><html:text name="benefitItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></b> </td>
          </tr>         
              <html:hidden name="benefitItem" property="id" indexed="true" />
              <html:hidden name="benefitItem" property="fycode" indexed="true" />
              <html:hidden name="benefitItem" property="name" indexed="true" />
              <html:hidden name="benefitItem" property="benpercent" indexed="true" />
              <html:hidden name="benefitItem" property="projtotal" indexed="true" />
              <html:hidden name="benefitItem" property="instcont" indexed="true" />
              <html:hidden name="benefitItem" property="grantrequest" indexed="true" />
              <html:hidden name="benefitItem" property="amountapproved" indexed="true" />
              <html:hidden name="benefitItem" property="expapproved" indexed="true" />            
        </table>
         
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
  <hr/><br/>
  
 
  <a name="year2" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b></td>
    </tr>
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        <c:if test="${benefitItem.fycode ==thisGrant.fycode+1}">   
 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Name</td>   
            <td colspan="2">Benefits Percentage</td>
          </tr>      
          <tr>
            <td colspan="2"><c:out value="${benefitItem.name}" /></td>
            <td colspan="2"><fmt:formatNumber value="${benefitItem.benpercent}"/></td>
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
            <td ><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td ><html:text name="benefitItem" property="amtamendedStr" indexed="true" /></td>          
            <td ><html:text name="benefitItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></b> </td>
          </tr>         
              <html:hidden name="benefitItem" property="id" indexed="true" />
              <html:hidden name="benefitItem" property="fycode" indexed="true" />
              <html:hidden name="benefitItem" property="name" indexed="true" />
              <html:hidden name="benefitItem" property="benpercent" indexed="true" />
              <html:hidden name="benefitItem" property="projtotal" indexed="true" />
              <html:hidden name="benefitItem" property="instcont" indexed="true" />
              <html:hidden name="benefitItem" property="grantrequest" indexed="true" />
              <html:hidden name="benefitItem" property="amountapproved" indexed="true" />
              <html:hidden name="benefitItem" property="expapproved" indexed="true" />       
        </table>
        
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
  <hr/><br/>
  
  
  
 
  <a name="year3" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b></td>
    </tr>
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        <c:if test="${benefitItem.fycode==thisGrant.fycode +2}">   
            
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Name</td>   
            <td colspan="2">Benefits Percentage</td>
          </tr>      
          <tr>
            <td colspan="2"><c:out value="${benefitItem.name}" /></td>
            <td colspan="2"><fmt:formatNumber value="${benefitItem.benpercent}"/></td>
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
            <td ><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td ><html:text name="benefitItem" property="amtamendedStr" indexed="true" /></td>          
            <td ><html:text name="benefitItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></b> </td>
          </tr>         
              <html:hidden name="benefitItem" property="id" indexed="true" />
              <html:hidden name="benefitItem" property="fycode" indexed="true" />
              <html:hidden name="benefitItem" property="name" indexed="true" />
              <html:hidden name="benefitItem" property="benpercent" indexed="true" />
              <html:hidden name="benefitItem" property="projtotal" indexed="true" />
              <html:hidden name="benefitItem" property="instcont" indexed="true" />
              <html:hidden name="benefitItem" property="grantrequest" indexed="true" />
              <html:hidden name="benefitItem" property="amountapproved" indexed="true" />
              <html:hidden name="benefitItem" property="expapproved" indexed="true" />       
          </table>
          
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <tr>
        <td><a href="coApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
        <c:choose >
        <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
          <tr>
            <td align="center"><input type="button" value="Save Benefits Expenses" disabled="disabled" /></td>
          </tr>
        </c:when>
        <c:otherwise >
          <tr>
            <td align="center"><input type="HIDDEN" name="currtab" value="2"/>
                               <input type="SUBMIT" value="Save Benefits Expenses" /></td>
          </tr>
        </c:otherwise>
        </c:choose>
    </logic:notEmpty>
  </table>
  </html:form>
 
  
    

<table width="95%" summary="for layout only" align="center">
  <tr>
    <td colspan="6"><b>Employee Benefits Totals for all Years</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.benProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.benInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.benAmtAmend}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.benExpSub}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.benExpAppr}" type="currency" maxFractionDigits="0"/></td>     
  </tr>
  <tr>
    <td height="15" colspan="6"><hr/></td>
  </tr>
  <tr>
    <td colspan="6"><b>Grand Total for all Budget Categories</b></td>
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


