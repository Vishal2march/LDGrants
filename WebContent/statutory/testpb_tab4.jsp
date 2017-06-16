<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab4.jsp
 * Creation/Modification History  :
 *     SLowe                   Created
 *     SHusak       3/1/07     Modified
 *
 * Description
 * This is the supply/material/equip Expense page.  It allows applicants to enter the info
 * required for claiming the expenses - including amounts requested, and to add/delete entries. 
 * Also displays a running total of requested/approved amounts for all 5 expense categories.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!-- supplies, materials, equipment -->
<html>
<head>
  <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
</head>

<body>
<p class="bdgtitle"><b>Project Budget<br/>
  IV. Supplies, Materials & Equipment</b><br/>
  List all supplies and materials to be purchased for use during the project.<br/>
  - Equipment items under $5,000 should be budgeted under "Supplies and Materials".<br/>
  - Items that have a unit cost of $5,000 or more that will be purchased for use during the project should be budgeted under "Equipment".
</p>


<table width="100%" summary="for layout only">
  <tr>
    <td class="scbgt">
    <%--this link allows Jaws to skip over menu items--%>
    <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
    
    <a href="BudgetSelect?tab=1&p=sa">I. Personal Services</a></td>               
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=2&p=sa">II. Employee Benefits</a></td>        
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=3&p=sa">III. Contracted Services</a></td>        
    
    <td class="scbgtselect">IV. Supplies Materials & Equipment</td>
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=5&p=sa">V. Other Expenses</a></td>    
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=6&p=sa">VI. Travel Expenses</a></td>
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent" /><br/>


  <c:if test="${totalsBean.warning =='true'}">
    <p align="center" class="error"><c:out value="${totalsBean.saMessage}" /></p>
  </c:if>

 
  <c:choose >
  <c:when test="${appStatus.amtreqyn=='true' || appStatus.pendingReview=='true'}" >
  
 <html:errors />
 <html:form action="/saveSaExpense" >
  <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
  <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
      
    <table class="boxtype" width="100%" summary="for layout only">
      <tr>
        <td width="20%">Quantity</td>
        <td width="20%">Description</td>
        <td width="20%">UnitPrice</td>
        <td width="20%">Vendor</td>
        <td width="20%">Type</td>     
      </tr>          
      <tr >
        <td width="20%"><c:out value="${supplyItem.quantity}" /></td>
        <td width="20%"><c:out value="${supplyItem.description}" /></td>            
        <td width="20%"><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
        <td width="20%"><c:out value="${supplyItem.vendor}" /></td>
        <td width="20%"><c:if test="${supplyItem.suppmatCode=='1'}" >
              Supplies & Materials
            </c:if>
            <c:if test="${supplyItem.suppmatCode=='2'}" >
              Equipment
            </c:if>            
        </td>
      </tr>
      <tr>
        <td width="20%">AmtRequested</td>
        <td width="20%">AmtApproved</td>
        <td width="20%">AmtAmended</td>
        <td width="20%">ExpSubmitted</td>
        <td width="20%">ExpApproved</td>
      </tr>
      <tr>
        <td width="20%"><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
        <td width="20%"><b><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td width="20%"><html:text name="supplyItem" property="amtamendedStr" indexed="true" /></td>
        <td width="20%"><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
        <td width="20%"><b><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td> 
     </tr> 
     
        <html:hidden name="supplyItem" property="id" indexed="true" />
        <html:hidden name="supplyItem" property="fycode" indexed="true" />
        <html:hidden name="supplyItem" property="quantity" indexed="true" />
        <html:hidden name="supplyItem" property="description" indexed="true" />
        <html:hidden name="supplyItem" property="unitprice" indexed="true" />
        <html:hidden name="supplyItem" property="vendor" indexed="true" />
        <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
        <html:hidden name="supplyItem" property="grantrequest" indexed="true" />            
        <html:hidden name="supplyItem" property="amountapproved" indexed="true" />
        <html:hidden name="supplyItem" property="expapproved" indexed="true" />      
    </table>  
   
    </logic:iterate>
    </logic:present>
    
    <p><a href="saApplicantForms.do?item=fs10a">FS10A - Amendment to Budget Item</a></p>
    
    <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
      <c:choose >
      <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
          <p align="center"><input type="BUTTON" value="Save Supply Expenses" disabled="disabled" /></p>
      </c:when>
      <c:otherwise >
          <p align="center"><input type="HIDDEN" name="currtab" value="4" />
            <html:submit value="Save Supply Expenses" /></p>
      </c:otherwise>
      </c:choose>
    </logic:notEmpty>
  </html:form>
    

  </c:when>
  <c:otherwise >
  
    <form method="POST" action="AddBudgetItem" >
      <INPUT type="hidden" name="p" value="sa">
      <INPUT type="hidden" name="tab" value="4">
      <p><input type="submit" value="Add"/>        
         Please save any changes first before adding a new record.</p>
    </form>
    
    <% int index =0; %>
    <html:errors />
    <html:form action="/saveSupplyBudget">
    <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
    <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >    
       
    <%-- create url that allows for deletion of this record, uses the expense id --%>
    <c:url value="saApplicantForms.do" var="deleteURL">
        <c:param name="item" value="confirmbdgtdelete" />
        <c:param name="tab" value="4" />
        <c:param name="id" value="${supplyItem.id}" />
        <c:param name="p" value="sa" />
    </c:url>
    
    <table width="100%" class="boxtype" summary="for layout only">
      <tr>
        <td align="center">Quantity</td>
        <td align="center">Description</td>
        <td align="center">UnitPrice</td>
        <td align="center">Quantity*Price</td>
        <td align="center">Vendor</td>
        <td align="center">Type</td>     
      </tr>
      
      <tr>
        <td><html:text name="supplyItem" property="quantity" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
        <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>        
        <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
        <td><html:text name="supplyItem" property="cost" indexed="true" readonly="true" style="background-color:silver"/></td>
        
        <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
        <td><html:select name="supplyItem" property="suppmatCode" indexed="true">
            <c:if test="${supplyItem.suppmatCode=='1'}" >
              <option value="1" selected="selected">Supplies & Materials</option>
              <option value="2">Equipment</option>  
            </c:if>
            <c:if test="${supplyItem.suppmatCode=='2'}" >
              <option value="1">Supplies & Materials</option>
              <option value="2" selected="selected">Equipment</option>
            </c:if>            
            </html:select></td>
      </tr>
      <tr>
        <td align="center">AmtRequested</td>
        <td align="center">AmtApproved</td>
        <td align="center">ExpSubmitted</td>
        <td align="center">ExpApproved</td>
      </tr>
      <tr >
        <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
        <td><b><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></b></td>
        <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
        <td><b><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></b></td>
      </tr>
      <tr>        
        <td><html:hidden name="supplyItem" property="id" indexed="true" />
            <a href='<c:out value="${deleteURL}" />' >Delete</a></td>
      </tr>
    </table>  
    <% index++; %>
    
  </logic:iterate>
  </logic:present>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
    <c:choose >
    <c:when test="${appStatus.amtreqyn=='true' && appStatus.pendingFinalRev=='true'}">
        <p align="center"><input type="BUTTON" value="Save Supply Expenses" disabled="disabled" /></p>
     </c:when>
     <c:otherwise >
        <p align="center"><input type="HIDDEN" name="currtab" value="4" />
          <html:submit value="Save Supply Expenses" /></p>
      </c:otherwise>
      </c:choose>
   </logic:notEmpty>   
   
  </html:form>
  
  </c:otherwise>
  </c:choose>
  
  


 <table width="100%" summary="for layout only">
  <tr>
    <td colspan="5" ><b>Supplies, Materials, Equipment Totals</b></td>
  </tr>
  <tr>
    <td>Amount Requested</td>
    <td>Amount Approved</td>
    <td>Amount Amended</td>
    <td>Expense Submitted</td>
    <td>Expense Approved</td>
  </tr>
  <tr>
    <td><fmt:formatNumber value="${totalsBean.suppAmtReq}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtAppr}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtAmend}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpSub}" type="currency" maxFractionDigits="0" /></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpAppr}" type="currency" maxFractionDigits="0" /></td>    
  </tr>
  <tr>
    <td height="15" colspan="2"></td>
  </tr>
  <tr>
    <td colspan="5"><b>Grand Totals for all Budget Categories</b></td>
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
</table>

</body>
</html>


