<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab3exp.jsp
 * Creation/Modification History  :
 *     SHusak       8/10/07     Modified
 *
 * Description
 * This is the Contracted Service Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!-- contracted services -->
<html>
<head>
  <title></title>
</head>
<body>


<p class="bdgtitle"><b>Project Budget<br/>
  III. Contracted Services</b><br/>
  List all services to be purchased for the project, arranged, as appropriate, 
  under Consultants or Contractual Agreements.  Attach cost estimates, bids, 
  or other supporting data in an appendix.</p>


  <table width="100%" summary="for layout only">
    <tr>
      <td class="scbgt">
      <%--this link allows Jaws to skip over menu items--%>
      <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
      
      <a href="BudgetSelect?tab=1&p=coe">I. Personal Services</a></td>        
      
      <td class="scbgt">        
      <a href="BudgetSelect?tab=2&p=coe">II. Employee Benefits</a></td>        
      
      <td class="scbgtselect">III. Contracted Services</td>
      
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
  <html:form action="/updateFinalExpenses">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>
    
    <tr>
      <td>           
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
        <c:if test="${thisGrant.fycode==contractItem.fycode}">   
            
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td>Description</td>
          </tr>     
           <tr >
            <td><c:out value="${contractItem.servicetype}" /></td>
            <td><c:out value="${contractItem.recipient}" /></td>
            <td><c:out value="${contractItem.servicedescr}" /></td>
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
          <tr >
            <td ><fmt:formatNumber value="${contractItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${contractItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td ><html:text name="contractItem" property="amtamendedStr" indexed="true" /></td>
            <td ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>    
              <html:hidden name="contractItem" property="id" indexed="true" />
              <html:hidden name="contractItem" property="fycode" indexed="true" />
              <html:hidden name="contractItem" property="servicetype" indexed="true" />
              <html:hidden name="contractItem" property="servicedescr" indexed="true" />
              <html:hidden name="contractItem" property="recipient" indexed="true" />
              <html:hidden name="contractItem" property="projtotal" indexed="true" />
              <html:hidden name="contractItem" property="instcont" indexed="true" />
              <html:hidden name="contractItem" property="grantrequest" indexed="true" />
              <html:hidden name="contractItem" property="amountapproved" indexed="true" />
              <html:hidden name="contractItem" property="expapproved" indexed="true" />
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
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" > 
        <c:if test="${contractItem.fycode==thisGrant.fycode + 1}">   
            
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td>Description</td>
          </tr>     
           <tr >
            <td><c:out value="${contractItem.servicetype}" /></td>
            <td><c:out value="${contractItem.recipient}" /></td>
            <td><c:out value="${contractItem.servicedescr}" /></td>
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
          <tr >
            <td ><fmt:formatNumber value="${contractItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${contractItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td ><html:text name="contractItem" property="amtamendedStr" indexed="true" /></td>
            <td ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>    
              <html:hidden name="contractItem" property="id" indexed="true" />
              <html:hidden name="contractItem" property="fycode" indexed="true" />
              <html:hidden name="contractItem" property="servicetype" indexed="true" />
              <html:hidden name="contractItem" property="servicedescr" indexed="true" />
              <html:hidden name="contractItem" property="recipient" indexed="true" />
              <html:hidden name="contractItem" property="projtotal" indexed="true" />
              <html:hidden name="contractItem" property="instcont" indexed="true" />
              <html:hidden name="contractItem" property="grantrequest" indexed="true" />
              <html:hidden name="contractItem" property="amountapproved" indexed="true" />
              <html:hidden name="contractItem" property="expapproved" indexed="true" />
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
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" > 
        <c:if test="${contractItem.fycode==thisGrant.fycode +2}">   
           
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td>Description</td>
          </tr>     
           <tr >
            <td><c:out value="${contractItem.servicetype}" /></td>
            <td><c:out value="${contractItem.recipient}" /></td>
            <td><c:out value="${contractItem.servicedescr}" /></td>
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
          <tr >
            <td ><fmt:formatNumber value="${contractItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${contractItem.instcont}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><b><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
            <td ><html:text name="contractItem" property="amtamendedStr" indexed="true" /></td>
            <td ><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
            <td ><b><fmt:formatNumber value="${contractItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
          </tr>    
              <html:hidden name="contractItem" property="id" indexed="true" />
              <html:hidden name="contractItem" property="fycode" indexed="true" />
              <html:hidden name="contractItem" property="servicetype" indexed="true" />
              <html:hidden name="contractItem" property="servicedescr" indexed="true" />
              <html:hidden name="contractItem" property="recipient" indexed="true" />
              <html:hidden name="contractItem" property="projtotal" indexed="true" />
              <html:hidden name="contractItem" property="instcont" indexed="true" />
              <html:hidden name="contractItem" property="grantrequest" indexed="true" />
              <html:hidden name="contractItem" property="amountapproved" indexed="true" />
              <html:hidden name="contractItem" property="expapproved" indexed="true" />
        </table>
        
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <tr>
        <td><a href="coApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></td>
    </tr>
      <logic:notEmpty name="BudgetCollectionBean" property="allContractRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <tr>
          <td align="center"><input type="button" value="Save Contracted Expenses" disabled="disabled" /></td>
        </tr>
      </c:when>
      <c:otherwise >
        <tr>
          <td align="center"><input type="HIDDEN" name="currtab" value="3"/>
                            <input type="SUBMIT" value="Save Contracted Expenses" /></td>
        </tr>    
      </c:otherwise>
      </c:choose>
    </logic:notEmpty>
  </table>
  </html:form>
      
    
    
<br/>
<table width="95%" summary="for layout only" align="center">
  <tr>
    <td colspan="6"><b>Contracted Service Totals for all Years</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.conProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.conInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtReq}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.conAmtAmend}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.conExpSub}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.conExpAppr}" type="currency" maxFractionDigits="0"/></td>    
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

