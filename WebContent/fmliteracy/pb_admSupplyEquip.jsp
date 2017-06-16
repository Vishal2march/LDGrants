<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
    <b>Supplies & Materials (Code 45)</b><br/>
  </c:when>
  <c:when test="${littab=='6'}">
    <b>Equipment  (Code 20)</b><br/>
  </c:when>
  </c:choose>
  
  List as supplies and materials all items to be purchased for use during the project with a 
  unit cost of less than $5,000.<br/><br/>For items with a unit cost of $5,000 or more, enter  
  as Equipment.    Note that although the quantity * unit price 
  is calculated as dollars and cents, the amount entered in Amount Requested
  field MUST be rounded to whole dollars.</p><br/>

  <%--print warnings for over fund/allocation amts--%>
<c:choose>
<c:when test="${thisGrant.fycode >13}"><%--starting fy2013-14--%>
   <c:forEach var="row" items="${fyWarnings}" >
        <c:if test="${row.warning=='true'}" >
          <p align="center" class="error"><c:out value="${row.litWarning}" /></p>
        </c:if>       
   </c:forEach> 
</c:when>
<c:otherwise>
  <c:forEach var="row" items="${fyTotals}" >
    <c:if test="${row.fycode==thisGrant.fycode || row.fycode==thisGrant.fycode +1}">
        <c:if test="${row.warning=='true'}" >
          <p align="center" class="error"><c:out value="${row.litWarning}" /></p>
        </c:if>        
    </c:if>
  </c:forEach>
</c:otherwise>
</c:choose>
  
  <html:form action="/saveLitAdminBudget">
  
  <INPUT type="hidden" name="currtab" value="4">
  <input type="HIDDEN" name="p" value='<c:out value="${p}" />'/>
  <input type="HIDDEN" name="littab" value='<c:out value="${littab}"/>'/>
    
  <table width="95%" align="center" summary="for layout only" >
    <%--<tr>
        <td><b>Admin Instructions:</b> Click the link to 'Add Supplies/Equip Approval Record' to add a record for this budget category 
        and fiscal year. Then type in the total 'AmtApproved' for this budget category and fiscal year. 
        Only 1 approval record per category per fiscal year.</td>
    </tr>--%>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b><br/>
      
      <c:url var="add1" value="addLitAdminAward.do">
            <c:param name="item" value="blankrec"/>
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <a href='<c:out value="${add1}"/>' >Add Year 1 Record</a><br/>
      
      
      <c:url var="add1a" value="addLitAdminAward.do">
            <c:param name="item" value="adminrec"/>
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        
        <%--<c:choose>
        <c:when test="${littab=='5'}">
          <a href="<c:out value='${add1a}'/>">Add Supplies/Materials Approval Record for Year 1</a>
        </c:when>
        <c:otherwise>
          <a href="<c:out value='${add1a}'/>">Add Equipment Approval Record for Year 1</a>
        </c:otherwise></c:choose>--%></td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${thisGrant.fycode==supplyItem.fycode}">   
         
        <%-- create url that allows for deletion of this record, uses the expense id --%>  
        <c:url value="addLitAdminAward.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="4" />
            <c:param name="id" value="${supplyItem.id}" />
            <c:param name="p" value="${littab}" />
        </c:url>
        
        
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
        <%--this is for regular applicant budget records--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>            
            <td>Vendor</td>
          </tr>          
          <tr >
            <td><html:text name="supplyItem" property="quantity" indexed="true"  /></td>
            <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>
            
            <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" /></td>             
            <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
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
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <%--<td width="20%">ExpApproved</td>--%>
          </tr>
          <tr >
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" /></td>
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<td><fmt:formatNumber value="${supplyItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="5"><b>Admin Supplies/Equipment Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="20%">&nbsp;</td> <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td><%-- <td width="25%">ExpApproved</td>--%>
            <td width="20%">&nbsp;</td><td width="20%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitpriceStr" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            </td>
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<td><html:text name="supplyItem" property="expapprovedStr" indexed="true" /></td>--%>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>        
        </c:otherwise>
        </c:choose>        
        
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
  </table>
  <br/><br/><hr/><br/>
  
  
  
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b><br/>
      
      <c:url var="add2" value="addLitAdminAward.do">
            <c:param name="item" value="blankrec"/>
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <a href='<c:out value="${add2}"/>' >Add Year 2 Record</a><br/>
        
        
      <c:url var="add2a" value="addLitAdminAward.do">
            <c:param name="item" value="adminrec"/>
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <%--<c:choose>
        <c:when test="${littab=='5'}">
          <a href="<c:out value='${add2a}'/>">Add Supplies/Materials Approval Record for Year 2</a>
        </c:when>
        <c:otherwise>
          <a href="<c:out value='${add2a}'/>">Add Equipment Approval Record for Year 2</a>
        </c:otherwise></c:choose>--%></td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${supplyItem.fycode==thisGrant.fycode +1}">   
                
                
        <%-- create url that allows for deletion of this record, uses the expense id --%>  
        <c:url value="addLitAdminAward.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="4" />
            <c:param name="id" value="${supplyItem.id}" />
            <c:param name="p" value="${littab}" />
        </c:url>
                
                
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
        <%--this is for regular applicant budget records--%>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>            
            <td>Vendor</td>
          </tr>          
          <tr >
            <td><html:text name="supplyItem" property="quantity" indexed="true"  /></td>
            <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>
            
            <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" /></td>             
            <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
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
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />  
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" /></td>
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<td><fmt:formatNumber value="${supplyItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="5"><b>Admin Supplies/Equipment Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="20%">&nbsp;</td> <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td> <%--<td width="25%">ExpApproved</td>--%>
            <td width="20%">&nbsp;</td><td width="20%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitpriceStr" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            </td>
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
           <%-- <td><html:text name="supplyItem" property="expapprovedStr" indexed="true" /></td>--%>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>        
        </c:otherwise>
        </c:choose>
        
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
  </table>
  <br/><hr/><br/>
  
  
  <c:if test="${thisGrant.fycode>13}">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b><br/>
      
      <c:url var="add3" value="addLitAdminAward.do">
            <c:param name="item" value="blankrec"/>
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="2"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <a href='<c:out value="${add3}"/>' >Add Year 3 Record</a><br/>
      
      
      <c:url var="add3a" value="addLitAdminAward.do">
            <c:param name="item" value="adminrec"/>
            <c:param name="tab" value="4"/>
            <c:param name="fy" value="2"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
       <%-- <c:choose>
        <c:when test="${littab=='5'}">
          <a href="<c:out value='${add3a}'/>">Add Supplies/Materials Approval Record for Year 3</a>
        </c:when>
        <c:otherwise>
          <a href="<c:out value='${add3a}'/>">Add Equipment Approval Record for Year 3</a>
        </c:otherwise></c:choose>--%></td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>       
        <logic:present name="BudgetCollectionBean" property="allSupplyRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allSupplyRecords" id="supplyItem" >
        <c:if test="${supplyItem.fycode==thisGrant.fycode +2}">   
              
        <%-- create url that allows for deletion of this record, uses the expense id --%>  
        <c:url value="addLitAdminAward.do" var="deleteURL">
            <c:param name="item" value="confirmbdgtdelete" />
            <c:param name="tab" value="4" />
            <c:param name="id" value="${supplyItem.id}" />
            <c:param name="p" value="${littab}" />
        </c:url>
                                
                
        <c:choose>
        <c:when test="${supplyItem.categoryTotal=='false'}">
        <%--this is for regular applicant budget records--%>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Quantity</td>
            <td>Description</td>
            <td>UnitPrice</td>            
            <td>Vendor</td>
          </tr>          
          <tr >
            <td><html:text name="supplyItem" property="quantity" indexed="true"  /></td>
            <td><html:text name="supplyItem" property="description" indexed="true" maxlength="250" /></td>
            
            <td><html:text name="supplyItem" property="unitpriceStr" indexed="true" /></td>             
            <td><html:text name="supplyItem" property="vendor" indexed="true" maxlength="50" /></td>
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
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:text name="supplyItem" property="grantrequestStr" indexed="true" />
                <html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" /></td>
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
            <%--<td><fmt:formatNumber value="${supplyItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="5"><b>Admin Supplies/Equipment Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="20%">&nbsp;</td> <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td> <%--<td width="25%">ExpApproved</td>--%>
            <td width="20%">&nbsp;</td><td width="20%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="supplyItem" property="id" indexed="true" />
                <html:hidden name="supplyItem" property="quantity" indexed="true" />
                <html:hidden name="supplyItem" property="description" indexed="true" />
                <html:hidden name="supplyItem" property="unitpriceStr" indexed="true" />
                <html:hidden name="supplyItem" property="vendor" indexed="true" />
                <html:hidden name="supplyItem" property="fycode" indexed="true" />
                <html:hidden name="supplyItem" property="suppmatCode" indexed="true" />
            </td>
            <td><html:text name="supplyItem" property="amountapprovedStr" indexed="true" /></td>
            <td><html:text name="supplyItem" property="expsubmittedStr" indexed="true" /></td>
           <%-- <td><html:text name="supplyItem" property="expapprovedStr" indexed="true" /></td>--%>
          </tr>  
          <tr>
            <td><a href='<c:out value="${deleteURL}" />' >Delete this record</a></td>
          </tr>
        </table>        
        </c:otherwise>
        </c:choose>
        
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
  </table>
  <br/><hr/><br/>
  </c:if>
  
  
  <c:if test="${lduser.adminfl=='approve'}" >
      <p align="center"><input type="SUBMIT" value="Save" name="btn" /><br/><br/>
        <%--<input type="SUBMIT" value="Copy Amt Requested" name="btn" /> <input type="SUBMIT" value="Copy Exp Submitted" name="btn" />--%>
      </p>
  </c:if>
  
  <br/><br/>
  
  <c:forEach var="fyt" items="${fyTotals}" >
  <p><b>Total Literacy Amt Approved for FY <fmt:formatNumber value="${fyt.fycode}" minIntegerDigits="2" />:&nbsp;
    <fmt:formatNumber value="${fyt.totAmtAppr}" type="currency" maxFractionDigits="0" /></b>
  </p>
  </c:forEach>
  
  </html:form>
  <br/><br/><hr/><br/>
  
  </body>
</html>
