<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/pd4ml.tld" prefix="pd4ml" %>
<%@ page contentType="text/html;charset=ISO8859_1"%>
<pd4ml:transform screenWidth="800" pageInsets="20,20,20,20,points" encoding="default" pageFormat="LETTER" pageOrientation="portrait" enableImageSplit="false">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction - Final Expenses</title>
  </head>
  <body>
  
  <font size="1">
  <table align="center" summary="for layout only">
    <tr>
      <th colspan="2">Public Library Construction grant program
          <br/>Final Expenses
      </th>
    </tr>
    <tr>
      <td>Project Number</td>
      <td>03<fmt:formatNumber minIntegerDigits="2" value="${thisGrant.fccode}" />
          -<fmt:formatNumber value="${thisGrant.fycode}" minIntegerDigits="2" />
          -<fmt:formatNumber value="${thisGrant.projseqnum}" pattern="####" minIntegerDigits="4" />
      </td>
    </tr>
    <tr>
      <td>Institution</td>
      <td><c:out value="${thisGrant.instName}" /></td>
    </tr>
    <tr>
      <td>Project Title</td>
      <td><c:out value="${thisGrant.title}" /></td>
    </tr>
  </table>
  <br/><br/><br/>
  
   
   <table width="98%" summary="for layout only">
      <tr>  
        <td colspan="5"><b>Purchased Services Expenses - Code 40</b></td>
      </tr>
      <tr>
        <td width="20%"><b>Encumbrance Date</b></td>
        <td width="20%"><b>Provider of Service</b></td>
        <td width="20%"><b>Check or Journal Entry #</b></td>
        <td width="20%"><b>Amount Expended</b></td>
        <td width="10%"><b>Grant Fund?</b></td>
        <td width="10%"><b>Match Fund?</b></td>
      </tr> 
    </table>    
        
   <c:forEach var="row" items="${allExpenses}">    
     <c:if test="${row.expenseId==40}">
        <table width="98%"  summary="for layout only">
          <tr>      
            <td width="20%"><c:out value="${row.beginDateStr}"/></td>
            <td width="20%"><c:out value="${row.provider}"/></td>
            <td width="20%"><c:out value="${row.journalEntry}"/></td>            
            <td width="20%"><c:out value="${row.expAmountStr}"/></td>
            <c:choose>
            <c:when test="${row.grantFundYn=='true'}">
                <td width="10%">Yes</td>
                <td width="10%">&nbsp;</td>
            </c:when>
            <c:otherwise>
                <td width="10%">&nbsp;</td>
                <td width="10%">Yes</td>
            </c:otherwise>
            </c:choose>
          </tr>
        </table> 
      </c:if>
    </c:forEach>
  
  
  <table width="98%" summary="for layout only">
      <tr>
        <td width="60%"><b>Total Purchased Services Grant Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.grantFundPurchased}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="60%"><b>Total Purchased Services Matching/Other Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.matchFundPurchased}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="60%"><b>Grand Total:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.grantFundPurchased+expenseGrandTotals.matchFundPurchased}" type="currency" maxFractionDigits="0"/></b></td>
      </tr>
    </table>    
  <br/><br/><br/>
  
  
  
   <table width="98%" summary="for layout only">
      <tr>  
        <td colspan="5"><b>Supplies & Material Expenses - Code 45</b></td>
      </tr>
      <tr>
        <td width="20%"><b>Purchase Order Date</b></td>
        <td width="20%"><b>Vendor</b></td>
        <td width="20%"><b>Check or Journal Entry #</b></td>
        <td width="20%"><b>Amount Expended</b></td>
        <td width="10%"><b>Grant Fund?</b></td>
        <td width="10%"><b>Match Fund?</b></td>
      </tr> 
    </table>    
        
  <c:forEach var="row" items="${allExpenses}">    
     <c:if test="${row.expenseId==45}">    
       <table width="98%" class="boxtype" summary="for layout only">
          <tr>      
            <td width="20%"><c:out value="${row.beginDateStr}"/></td>
            <td width="20%"><c:out value="${row.provider}"/></td>
            <td width="20%"><c:out value="${row.journalEntry}"/></td>            
            <td width="20%"><c:out value="${row.expAmountStr}"/></td>  
            <c:choose>
            <c:when test="${row.grantFundYn=='true'}">
                <td width="10%">Yes</td>
                <td width="10%">&nbsp;</td>
            </c:when>
            <c:otherwise>
                <td width="10%">&nbsp;</td>
                <td width="10%">Yes</td>
            </c:otherwise>
            </c:choose>
          </tr>
       </table>     
     </c:if>
  </c:forEach>
  
  <table width="98%" summary="for layout only">
      <tr>
        <td width="60%"><b>Total Supplies & Materials Grant Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.grantFundSupply}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="60%"><b>Total Supplies & Materials Matching/Other Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.matchFundSupply}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="60%"><b>Grand Total:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.grantFundSupply+expenseGrandTotals.matchFundSupply}" type="currency" maxFractionDigits="0"/></b></td>
      </tr>
    </table>             
  <br/><br/><br/>
  
  
        
  <table width="98%" summary="for layout only">
      <tr>  
        <td colspan="5"><b>Equipment Expenses - Code 20</b></td>
      </tr>
      <tr>
        <td width="20%"><b>Purchase Order Date</b></td>
        <td width="20%"><b>Vendor</b></td>
        <td width="20%"><b>Check or Journal Entry #</b></td>
        <td width="20%"><b>Amount Expended</b></td>
        <td width="10%"><b>Grant Fund?</b></td>
        <td width="10%"><b>Match Fund?</b></td>
      </tr> 
  </table>     
  
  <c:forEach var="row" items="${allExpenses}">    
     <c:if test="${row.expenseId==20}">  
        <table width="98%" class="boxtype" summary="for layout only">
            <tr>      
              <td width="20%"><c:out value="${row.beginDateStr}"/></td>
              <td width="20%"><c:out value="${row.provider}"/></td>
              <td width="20%"><c:out value="${row.journalEntry}"/></td>            
              <td width="20%"><c:out value="${row.expAmountStr}"/></td>  
                <c:choose>
                <c:when test="${row.grantFundYn=='true'}">
                    <td width="10%">Yes</td>
                    <td width="10%">&nbsp;</td>
                </c:when>
                <c:otherwise>
                    <td width="10%">&nbsp;</td>
                    <td width="10%">Yes</td>
                </c:otherwise>
                </c:choose>
            </tr>
         </table>   
      </c:if>
   </c:forEach>
      
    <table width="98%" summary="for layout only">
      <tr>
        <td width="60%"><b>Total Equipment Grant Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.grantFundEquip}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="60%"><b>Total Equipment Matching/Other Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.matchFundEquip}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="60%"><b>Grand Total:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.grantFundEquip+expenseGrandTotals.matchFundEquip}" type="currency" maxFractionDigits="0"/></b></td>
      </tr>
    </table>      
      
    <br/><br/><br/>
    
    
    <table width="98%" summary="for layout only">
      <tr>
        <th colspan="2" bgcolor="silver">Total Project Expenses</th>
      </tr>
      <tr>
        <td width="60%"><b>Total Grant Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.grantFundTotal}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="60%"><b>Total Matching/Other Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.matchFundTotal}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="60%"><b>Total Project Expenses:</b></td>
        <td><b><fmt:formatNumber value="${expenseGrandTotals.totalExpenses}" type="currency" maxFractionDigits="0"/></b></td>
      </tr>
    </table>      
    </font>
    
    
  </body>
</html>
</pd4ml:transform>