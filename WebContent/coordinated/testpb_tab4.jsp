<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  pb_tab4.jsp
 * Creation/Modification History  :
 *     SHusak       7/16/07     Modified
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
  <title></title>
</head>
<body>
<p class="bdgtitle"><b>Project Budget<br/>
  IV. Supplies, Materials & Equipment</b><br/>
  List all supplies and materials to be purchased for use during the project.  
  DO NOT include supplies to be purchased by your vendor--the vendor's cost estimate 
  will include the cost of materials as well as labor.  Note that although the quantity * unit price of an item
  is calculated as dollars and cents, the amounts entered in Institutional Contribution and Amount Requested
  fields MUST be rounded to whole dollars.</p>


<table width="100%" summary="for layout only">
  <tr>
    <td class="scbgt">
    <%--this link allows Jaws to skip over menu items--%>
    <a href="#bdgcontent"><img src="images/dummy.bmp" border="0" height="1" width="1" alt="skip budget navigation" /></a>
    
    <a href="BudgetSelect?tab=1&p=co">I. Personal Services</a></td>               
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=2&p=co">II. Employee Benefits</a></td>        
    
    <td class="scbgt">        
    <a href="BudgetSelect?tab=3&p=co">III. Contracted Services</a></td>        
    
    <td class="scbgtselect">IV. Supplies Materials & Equipment</td>
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=5&p=co">V. Other Expenses</a></td>  
    
    <td class="scbgt">
    <a href="BudgetSelect?tab=6&p=co">VI. Travel Expenses</a></td>                
    
  </tr>
</table>
<a name="bdgcontent" id="bdgcontent" />
<br/><br/>


  <% int index =0; %>
  <html:form action="/updateSupply" >
  <a name="year1" />
  <html:errors />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b>
          <a href="AddBudgetItem?tab=4&fy=0&p=co">Add Year 1 Record</a></td>
      </td>
    </tr>
   
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >  
        <c:if test="${thisGrant.fycode==supplyItem.fycode}">   
                      
        <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="4" />
              <c:param name="id" value="${supplyItem.id}" />
              <c:param name="p" value="co" />
          </c:url> 
          
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Quantity*Price</td>
            <td>Vendor</td>
            <td>Type</td>     
          </tr>          
          <tr >
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
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr>
            <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
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
        <a href="AddBudgetItem?tab=4&fy=1&p=co">Add Year 2 Record</a>
      </td>
    </tr>
   
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >  
        <c:if test="${thisGrant.fycode+1 ==supplyItem.fycode}">   
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>
         <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="4" />
              <c:param name="id" value="${supplyItem.id}" />
              <c:param name="p" value="co" />
          </c:url>                
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Quant*Price</td>
            <td>Vendor</td>
            <td>Type</td>     
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
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr>
            <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
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
        <a href="AddBudgetItem?tab=4&fy=2&p=co">Add Year 3 Record</a>
      </td>
    </tr>
    
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >  
        <c:if test="${thisGrant.fycode+2 ==supplyItem.fycode}">   
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>
         <c:url value="coApplicantForms.do" var="deleteURL">
              <c:param name="item" value="confirmbdgtdelete" />
              <c:param name="tab" value="4" />
              <c:param name="id" value="${supplyItem.id}" />
              <c:param name="p" value="co" />
          </c:url>                 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Quant*Price</td>
            <td>Vendor</td>
            <td>Type</td>     
          </tr>          
          <tr >
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
            <td width="16%">ProjTotal</td>
            <td width="16%">InstContrib</td>
            <td width="16%">AmtRequested</td>
            <td width="16%">AmtApproved</td>
            <td width="16%">ExpSubmitted</td>
            <td width="16%">ExpApproved</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.projtotal}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="instcontStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr>
            <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
          </tr>
        </table>
          <% index++; %>
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
    <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
      <tr>
        <td align="center"><input type="HIDDEN" name="currtab" value="4"/>
                           <input type="SUBMIT" value="Save Supply Records" /></td>
      </tr>
    </logic:notEmpty>
  </table>
  </html:form>
 
  
    
 <table width="95%" summary="for layout only" align="center">
  <tr>
    <td colspan="6" ><b>Supplies, Materials, Equipment Totals for all Years</b></td>
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
    <td><fmt:formatNumber value="${totalsBean.suppProjTot}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppInstCont}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtReq}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppAmtAppr}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpSub}" type="currency" maxFractionDigits="0"/></td>
    <td><fmt:formatNumber value="${totalsBean.suppExpAppr}" type="currency" maxFractionDigits="0"/></td>    
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