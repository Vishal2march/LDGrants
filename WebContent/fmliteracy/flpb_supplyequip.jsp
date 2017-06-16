<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript" src="css/jsCPBudget.js" > </script>
    <script language="javascript" type="text/javascript" src="jscripts/tinymce/tinymce.js"></script>
    <script type="text/javascript">
    tinymce.init({
         selector: "textarea",
         menubar: false,
         toolbar: "bold italic underline | alignleft aligncenter alignright | bullist numlist outdent indent",
         statusbar: false,
         nowrap: false,
         width: 600
     });
    </script>
  </head>
  <body>

  <c:set var="littab" value="${param.t}" />
  <p class="bdgtitle"><b>Project Budget</b><br/>
  
  <c:choose >
  <c:when test="${littab=='5'}">
    <b>Supplies, Materials (Code 45)</b><br/>
  </c:when>
  <c:when test="${littab=='6'}">
    <b>Equipment  (Code 20)</b><br/>
  </c:when>
  </c:choose>
  <br/>
  List as supplies and materials all items to be purchased for use during the project with a 
  unit cost of less than $5,000.<br/><br/>For items with a unit cost of $5,000 or more, enter  
  as equipment.  Group budget records by vendor.  Note that although the quantity * unit 
  price is calculated as dollars and cents, the amount entered in Amount Requested
  field MUST be rounded to whole dollars.</p><br/>

  <c:forEach var="row" items="${fyWarnings}" >      
    <c:if test="${row.warning=='true'}" >
      <p align="center" class="error"><c:out value="${row.flWarning}" /></p>
    </c:if>        
  </c:forEach>
  
  
  <% int index =0; %>
  <html:form action="/saveFlSupply" >
  <a name="year1" />
  <html:errors />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b>
          <c:url var="add1" value="AddBudgetItem">
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="fl"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <a href='<c:out value="${add1}"/>' >Add Year 1 Record</a></td>
    </tr>
   
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >  
        <c:if test="${thisGrant.fycode==supplyItem.fycode}">   
                
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
                
        <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="4" />
            <c:param name="id" value="${supplyItem.id}" />
            <c:param name="p" value="fl${littab}" />
        </c:url>
          
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Quantity*Price</td>
            <td>Vendor</td>
          </tr>          
          <tr >
            <td><html:text name="supplyItem" property="quantity" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
            <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>
            
            <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
            <td><html:text name="supplyItem" property="cost" indexed="true" readonly="true" style="background-color:silver"/></td>
            
            <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
          </tr>
          <tr>
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <td width="20%">ExpApproved</td>
          </tr>
          <tr >
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr>
            <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
            <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            <html:hidden name="supplyItem" property="module" indexed="true" value="fl" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="3">Use the Save Button below to Save all budget records on this page</td>
          </tr>
        </table>
        <% index++; %>
        </c:when>
        <c:otherwise>                
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitpriceStr" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />               
                
        </c:otherwise>
        </c:choose>
        
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
        <c:url var="add2" value="AddBudgetItem">
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="fl"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <a href='<c:out value="${add2}"/>' >Add Year 2 Record</a>
      </td>
    </tr>
   
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >  
        <c:if test="${thisGrant.fycode+1 ==supplyItem.fycode}">   
    
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>  
        <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="4" />
            <c:param name="id" value="${supplyItem.id}" />
            <c:param name="p" value="fl${littab}" />
        </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Quant*Price</td>
            <td>Vendor</td>
          </tr>          
          <tr>
            <td><html:text name="supplyItem" property="quantity" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
            <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>
            
            <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
            <td><html:text name="supplyItem" property="cost" indexed="true" readonly="true" style="background-color:silver"/></td>
            
            <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
          </tr>
          <tr>
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <td width="20%">ExpApproved</td>
          </tr>
          <tr >
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr>
            <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
            <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            <html:hidden name="supplyItem" property="module" indexed="true" value="fl" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="3">Use the Save Button below to Save all budget records on this page</td>
          </tr>
        </table>
        <% index++; %>
         </c:when>
        <c:otherwise>                
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitpriceStr" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />               
                
        </c:otherwise>
        </c:choose>
        
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
        <c:url var="add3" value="AddBudgetItem">
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="2"/>
            <c:param name="p" value="fl"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <a href='<c:out value="${add3}"/>' >Add Year 3 Record</a>
      </td>
    </tr>
   
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >  
        <c:if test="${thisGrant.fycode+2 ==supplyItem.fycode}">   
    
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
    
        <%-- create url that allows for deletion of this record, uses the expense id --%>  
        <c:url value="liInitialForms.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="4" />
            <c:param name="id" value="${supplyItem.id}" />
            <c:param name="p" value="fl${littab}" />
        </c:url>
        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Quant*Price</td>
            <td>Vendor</td>
          </tr>          
          <tr>
            <td><html:text name="supplyItem" property="quantity" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
            <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>
            
            <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" onchange='<%= "calcCost(" + index + ");" %>' /></td>
            <td><html:text name="supplyItem" property="cost" indexed="true" readonly="true" style="background-color:silver"/></td>
            
            <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
          </tr>
          <tr>
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <td width="20%">ExpApproved</td>
          </tr>
          <tr >
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.expapproved}" type="currency" maxFractionDigits="0" /></td>
          </tr>
          <tr>
            <html:hidden name="supplyItem" property="id" indexed="true" />
            <html:hidden name="supplyItem" property="fycode" indexed="true" />
            <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            <html:hidden name="supplyItem" property="module" indexed="true" value="fl" />
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
            <td colspan="3">Use the Save Button below to Save all budget records on this page</td>
          </tr>
        </table>
        <% index++; %>
         </c:when>
        <c:otherwise>                
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitpriceStr" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />               
                
        </c:otherwise>
        </c:choose>
        
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>
  </table> 
  
  
  <br/><br/>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
    <p align="center"><input type="HIDDEN" name="currtab" value="4"/>
                    <input type="HIDDEN" name="i" value='<c:out value="${littab}" />' />
                    <input type="SUBMIT" value="Save Budget Records" /></p>
  </logic:notEmpty>
  
</html:form>
<br/><hr/><br/>  
  
  
 <table width="95%" align="center" summary="for layout only">
    <tr>
      <td colspan="5" ><b>Totals for Year 1</b></td>
    </tr>
    <tr>
      <td>Allocation</td>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value='${totalsMap["1"].allocationAmt}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber type="currency" value='${totalsMap["1"].totAmtReq}' maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["1"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["1"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="15" colspan="5"><hr/></td>
    </tr>
    <tr>
      <td colspan="5"><b>Totals for Year 2</b></td>
    </tr>
    <tr>   
      <td>Allocation</td>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value='${totalsMap["2"].allocationAmt}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["2"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["2"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>
    <tr>
      <td height="15" colspan="5"><hr/></td>
    </tr>
    <tr>
      <td colspan="5"><b>Totals for Year 3</b></td>
    </tr>
    <tr>   
      <td>Allocation</td>
      <td>Amt Requested</td>
      <td>Amt Approved</td>
      <td>Actual Expense</td>
      <td>Exp Approved</td>
    </tr>
    <tr>
      <td><fmt:formatNumber value='${totalsMap["3"].allocationAmt}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totAmtReq}' type="currency" maxFractionDigits="0" /></td> 
      <td><fmt:formatNumber value='${totalsMap["3"].totAmtAppr}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totExpSub}' type="currency" maxFractionDigits="0" /></td>
      <td><fmt:formatNumber value='${totalsMap["3"].totExpAppr}' type="currency" maxFractionDigits="0" /></td>
    </tr>
  </table>
<hr/><br/><br/>

 
 <p align="center">***Remember to save your budget records first before editing the narrative***</p>
 
 <html:form action="/flSaveNarrative" >     
 <table width="95%" align="center" class="boxtype" summary="for layout only" >
    <tr>
      <th><c:out value="${projNarrative.narrativeTitle}" /></th>
    </tr>
    <tr>
      <td><c:out value="${projNarrative.narrativeDescr}" /><br/></td>
    </tr>      
    <tr>
      <td height="20" />
    </tr>           
     
    <c:choose >
    <c:when test="${lduser.prgfl=='read' || appStatus.projdescyn=='true' || appStatus.pendingReview=='true'}" > 
      <tr>   
        <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
      </tr>
    </c:when>
    <c:otherwise >
      <tr>
        <td align="center">
          <div name="myToolBars" class="mceToolbarExternal"></div>
          <html:textarea property="narrative" cols="80" rows="17" /> 
          <html:hidden property="id" /> <html:hidden property="narrTypeID" />
          <html:hidden property="module" value="flb"/><input type="hidden" name="litpge" value='<c:out value="${littab}"/>'/>
        </td>  
      </tr>            
    </c:otherwise>
    </c:choose>
    <tr>
      <td align="center"><html:submit value="Save Narrative" /></td>
    </tr>          
  </table>
 </html:form>
  
  
  </body>
</html>
