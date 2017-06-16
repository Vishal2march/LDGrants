<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab2.jsp
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
  <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
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
      
      <a href="BudgetSelect?tab=1&p=co">I. Personal Services</a></td>        
      
      <td class="scbgtselect">II. Employee Benefits</td>        
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=3&p=co">III. Contracted Services</a></td>        
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=4&p=co">IV. Supplies Materials & Equipment</a></td>        
      
      <td class="scbgt">
      <a href="BudgetSelect?tab=5&p=co">V. Other Expenses</a></td>
      
      <td class="scbgt">
    <a href="BudgetSelect?tab=6&p=co">VI. Travel Expenses</a></td>                
    
    </tr>
  </table>
  <a name="bdgcontent" id="bdgcontent" />
  <br/><br/>
  
  
  <% int index =0; %>
  <a name="year1" />
  <html:errors />
  <html:form action="/updateBenefits">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b>
        <a href="AddBudgetItem?tab=2&fy=0&p=co">Add Year 1 Record</a>
      </td>
    </tr>
    
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
        <c:if test="${thisGrant.fycode==benefitItem.fycode}">   
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="2" />
              <c:param name="id" value="${benefitItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Name</td>   
            <td colspan="2">Benefits Percentage (decimal)</td>
            <td>Salary*FTE</td>
            <td>BenefitsAmt</td>
          </tr>      
          <tr>
            <td colspan="2"><html:text name="benefitItem" property="name" indexed="true" /></td>
            <td colspan="2"><html:text name="benefitItem" property="benpercentStr" indexed="true" onchange='<%= "calcBenefits(" + index + ");" %>' /></td>
            <td><html:text name="benefitItem" property="salary" indexed="true" readonly="true" style="background-color:silver" /></td>
            <td><html:text name="benefitItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
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
            <td ><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="benefitItem" property="instcontStr" indexed="true" /></td>
            <td ><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
            <td ><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>                
          <tr>
            <html:hidden name="benefitItem" property="id" indexed="true" />
            <html:hidden name="benefitItem" property="fycode" indexed="true" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
         </tr>
        </table>
           <% index++; %>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
  <br/><hr/><br/>
  
  
  
  
  <a name="year2" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b>
          <a href="AddBudgetItem?tab=2&fy=1&p=co">Add Year 2 Record</a>
      </td>
    </tr>
   
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
        <c:if test="${thisGrant.fycode +1==benefitItem.fycode}">   
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="2" />
              <c:param name="id" value="${benefitItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Name</td>   
            <td colspan="2">Benefits Percentage (decimal)</td>
            <td>Salary*FTE</td>
            <td>BenefitsAmt</td>
          </tr>      
          <tr>
            <td colspan="2"><html:text name="benefitItem" property="name" indexed="true" /></td>
            <td colspan="2"><html:text name="benefitItem" property="benpercentStr" indexed="true" onchange='<%= "calcBenefits(" + index + ");" %>'/></td>
            <td><html:text name="benefitItem" property="salary" indexed="true" readonly="true" style="background-color:silver" /></td>
            <td><html:text name="benefitItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
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
            <td ><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="benefitItem" property="instcontStr" indexed="true" /></td>
            <td ><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
            <td ><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>                
          <tr>
            <html:hidden name="benefitItem" property="id" indexed="true" />
            <html:hidden name="benefitItem" property="fycode" indexed="true" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
         </tr>
        </table>
           <% index++; %>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table>
  <br/><hr/><br/>
  
  
  

  <a name="year3" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b>
          <a href="AddBudgetItem?tab=2&fy=2&p=co">Add Year 3 Record</a>
      </td>
    </tr>
    
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" >  
        <c:if test="${thisGrant.fycode+2==benefitItem.fycode}">   
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="2" />
              <c:param name="id" value="${benefitItem.id}" />
              <c:param name="p" value="co" />
          </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Name</td>   
            <td colspan="2">Benefits Percentage (decimal)</td>
            <td>Salary*FTE</td>
            <td>BenefitsAmt</td>
          </tr>      
          <tr>
            <td colspan="2"><html:text name="benefitItem" property="name" indexed="true" /></td>
            <td colspan="2"><html:text name="benefitItem" property="benpercentStr" indexed="true" onchange='<%= "calcBenefits(" + index + ");" %>'/></td>
            <td><html:text name="benefitItem" property="salary" indexed="true" readonly="true" style="background-color:silver" /></td>
            <td><html:text name="benefitItem" property="cost" indexed="true" readonly="true" style="background-color:silver" /></td>
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
            <td ><fmt:formatNumber value="${benefitItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="benefitItem" property="instcontStr" indexed="true" /></td>
            <td ><html:text name="benefitItem" property="grantrequestStr" indexed="true" /></td>
            <td ><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>                
          <tr>
            <html:hidden name="benefitItem" property="id" indexed="true" />
            <html:hidden name="benefitItem" property="fycode" indexed="true" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
         </tr>
        </table>
             <% index++; %>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
      <tr>
        <td align="center"><input type="HIDDEN" name="currtab" value="2"/>
                           <input type="SUBMIT" value="Save Benefits Records" /></td>
      </tr>
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
      <td>Exp Submitted</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value="${totalsBean.benProjTot}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.benInstCont}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.benAmtReq}" type="currency" maxFractionDigits="0"/></td>
      <td><fmt:formatNumber value="${totalsBean.benAmtAppr}" type="currency" maxFractionDigits="0"/></td>
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
        <b><fmt:formatNumber value="${totalsBean.totExpSub}" type="currency" maxFractionDigits="0"/></b>
      </td>
      <td>
       <b><fmt:formatNumber value="${totalsBean.totExpAppr}" type="currency" maxFractionDigits="0"/></b>
      </td>
    </tr>
  </table>

  
</body>
</html>

