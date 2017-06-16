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
  as equipment.    Note that although the quantity * unit price 
  is calculated as dollars and cents, the amount entered in Amount Requested
  field MUST be rounded to whole dollars.
  <br/><br/>
  <b>NOTE:</b> You must explain any changes to the project budget in the "Budget Changes Year 1"
  section of the Final Narrative.</p>
  <br/>
  
  <a name="year1" />
  <html:errors />
  <html:form action="/saveALSuppExpenses">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>   
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${thisGrant.fycode==supplyItem.fycode}">   
          
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Vendor</td>
          </tr>          
          <tr >
            <td><c:out value="${supplyItem.quantity}" /></td>
            <td><c:out value="${supplyItem.description}" /></td>            
            <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
            <td><c:out value="${supplyItem.vendor}" /></td>
          </tr>
          <tr>
              <td colspan="2">Purchase Order Date (mm/dd/yyyy)</td>    
              <td colspan="2">Check/Journal Entry#</td>
            </tr> 
            <tr>
              <td colspan="2"><html:text name="supplyItem" property="encumbranceDateStr" indexed="true" /></td>
              <td colspan="2"><html:text name="supplyItem" property="journalEntry" indexed="true" /></td>
            </tr>   
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitprice" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
                <html:hidden name="supplyItem" property="grantrequest" indexed="true" />
                <html:hidden name="supplyItem" property="amountapproved" indexed="true" /></td>
          </tr>   
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Supplies/Equipment Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />
                <html:hidden name="supplyItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
          </tr> 
          <tr>
            <td colspan="4">Use the Save Button below to Save all budget records on this page</td>
          </tr> 
        </table>                
        </c:otherwise>
        </c:choose>
         
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
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${supplyItem.fycode==thisGrant.fycode +1}">   
                
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Vendor</td>
          </tr>          
          <tr >
            <td><c:out value="${supplyItem.quantity}" /></td>
            <td><c:out value="${supplyItem.description}" /></td>            
            <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
            <td><c:out value="${supplyItem.vendor}" /></td>
          </tr>
          <tr>
              <td colspan="2">Purchase Order Date (mm/dd/yyyy)</td>    
              <td colspan="2">Check/Journal Entry#</td>
            </tr> 
            <tr>
              <td colspan="2"><html:text name="supplyItem" property="encumbranceDateStr" indexed="true" /></td>
              <td colspan="2"><html:text name="supplyItem" property="journalEntry" indexed="true" /></td>
            </tr>   
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitprice" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
                <html:hidden name="supplyItem" property="grantrequest" indexed="true" />
                <html:hidden name="supplyItem" property="amountapproved" indexed="true" /></td>
          </tr>           
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Supplies/Equipment Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />
                <html:hidden name="supplyItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
          </tr>  
          <tr>  
            <td colspan="4">Use the Save Button below to Save all budget records on this page</td>
          </tr>
        </table>                
        </c:otherwise>
        </c:choose>
          
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>    
  </table>
  
  
  
  <c:if test="${thisGrant.fycode>13}">
  <hr/><br/> 
  <a name="year3" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b></td>
    </tr>
    
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${supplyItem.fycode==thisGrant.fycode +2}">   
                
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>
            <td>Vendor</td>
          </tr>          
          <tr >
            <td><c:out value="${supplyItem.quantity}" /></td>
            <td><c:out value="${supplyItem.description}" /></td>            
            <td><fmt:formatNumber value="${supplyItem.unitprice}" type="currency" /></td>
            <td><c:out value="${supplyItem.vendor}" /></td>
          </tr>
          <tr>
              <td colspan="2">Purchase Order Date (mm/dd/yyyy)</td>    
              <td colspan="2">Check/Journal Entry#</td>
            </tr> 
            <tr>
              <td colspan="2"><html:text name="supplyItem" property="encumbranceDateStr" indexed="true" /></td>
              <td colspan="2"><html:text name="supplyItem" property="journalEntry" indexed="true" /></td>
            </tr>   
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${supplyItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitprice" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
                <html:hidden name="supplyItem" property="grantrequest" indexed="true" />
                <html:hidden name="supplyItem" property="amountapproved" indexed="true" /></td>
          </tr>           
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Supplies/Equipment Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="categoryTotal" indexed="true" />
                <html:hidden name="supplyItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${supplyItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
          </tr>  
          <tr>  
            <td colspan="4">Use the Save Button below to Save all budget records on this page</td>
          </tr>
        </table>                
        </c:otherwise>
        </c:choose>
          
        </c:if>
        </logic:iterate>
        </logic:present>
      </td>
    </tr>    
  </table>
  </c:if>  
  
  
  <br/>    
  <logic:notEmpty name="BudgetCollectionBean" property="allSupplyRecords">
    <p align="center">  
      <c:choose >
      <c:when test="${appStatus.allowSubmitFinal3=='false'}">
        <input type="button" value="Save Expenses" disabled="disabled" />
      </c:when>
      <c:otherwise >
        <input type="HIDDEN" name="currtab" value="4"/>
        <input type="HIDDEN" name="p" value="al" />
        <input type="HIDDEN" name="i" value='<c:out value="${littab}" />' />
        <html:submit value="Save Expenses" />
      </c:otherwise>
      </c:choose>
    </p>
  </logic:notEmpty>
  
  </html:form>
  <br/><hr/><br/>
  </body>
</html>
