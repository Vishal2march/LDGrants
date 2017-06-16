<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction</title>
  </head>
  <body>
  
  <p class="bdgtitle"><b>Final Budget Expenses<br/>
  Purchased Services (Code 40)</b><br/>
  Final Expense entries should agree with the budget records approved in the project 
  budget.<br/><br/>All final expense entries paid with grant funds from the 3 categories (Purchased Services, 
  Supplies & Materials, Equipment) will be automatically transferred to the appropriate 
  code categories on the FS-10-F Form.  Expenses must be identified as being paid by 
  grant funds or matching funds by checking the appropriate checkbox.<br/><br/>For further questions 
  regarding Final Budget Expenses contact Kim Anderson at kimberly.anderson@nysed.gov or 518-486-5252.</p>

 <table width="100%" summary="for layout only">
    <tr>  
      <td class="dibgtselect">Purchased Services</td>
      
      <td class="dibgt">
      <a href="cnAdminExpenseNav.do?i=selectexpense&c=45">Supplies & Materials</a></td> 
      
      <td class="dibgt">
      <a href="cnAdminExpenseNav.do?i=selectexpense&c=20">Equipment</a></td> 
    </tr>
  </table>
  
  
  
      <br/>
      <form method="post" action="cnAdminExpenseNav.do?i=addexp">    
      <p>
        <input type="submit" value="Add"/>
        Save any changes first before adding a new record.
        <INPUT type="hidden" name="c" value="40"> </p>
      </form>
      
      
      <html:errors />
      <html:form action="/saveCnAdminExpense" >
    
        <table width="98%" summary="for layout only">
          <tr>
            <th width="20%">Encumbrance Date</th>
            <th width="20%">Provider of Service</th>
            <th width="20%">Check or Journal Entry #</th>
            <th width="20%">Amount Expended</th>
            <th width="10%">Grant Fund?</th>
            <th width="10%"><b>Match Fund?</b></th>
          </tr> 
        </table>    
        
        <logic:present name="BudgetCollectionBean" property="allFinalExpenses" >
        <logic:iterate name="BudgetCollectionBean" property="allFinalExpenses" id="finalExpenseItem" >   
        
          <c:url value="cnAdminExpenseNav.do" var="deleteURL">
            <c:param name="i" value="confirmdelete" />
            <c:param name="id" value="${finalExpenseItem.id}" />
            <c:param name="p" value="admincn" />
            <c:param name="fs" value="40" />
          </c:url>          
          
          <table width="98%" class="boxtype" summary="for layout only">
          <tr>      
            <td width="20%"><html:text name="finalExpenseItem" property="beginDateStr" indexed="true" />
                            <br/><a href='<c:out value="${deleteURL}" />' >Delete</a></td>
            <td width="20%"><html:text name="finalExpenseItem" property="provider" indexed="true" /></td>
            <td width="20%"><html:text name="finalExpenseItem" property="journalEntry" indexed="true" /></td>
            
            <td width="20%"><html:text name="finalExpenseItem" property="expAmountStr" indexed="true" />
                <html:hidden name="finalExpenseItem" property="id" indexed="true" />
                <html:hidden name="finalExpenseItem" property="grantId" indexed="true" />
                <html:hidden name="finalExpenseItem" property="expenseId" indexed="true" /></td>        
            <td width="10%"><html:radio name="finalExpenseItem" property="grantFundYn" indexed="true" value="true"/></td>
            <td width="10%"><html:radio name="finalExpenseItem" property="grantFundYn" indexed="true" value="false"/></td>
          </tr>
          </table>    
        
        </logic:iterate>
        </logic:present>
        
        <p align="center"><input type="hidden" name="mod" value="admincn"/>
                          <input type="hidden" name="eid" value="40"/>
                          <html:submit value="Save"/></p>        
        </html:form>
    
     <table width="80%" summary="for layout only">
      <tr>
        <td width="50%"><b>Total Grant Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseTotals.grantFundTotal}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="50%"><b>Total Matching/Other Funds:</b></td>
        <td><b><fmt:formatNumber value="${expenseTotals.matchFundTotal}" type="currency" maxFractionDigits="0"/></b></td>
      </tr> 
      <tr>
        <td width="50%"><b>Grand Total:</b></td>
        <td><b><fmt:formatNumber value="${expenseTotals.totalExpenses}" type="currency" maxFractionDigits="0"/></b></td>
      </tr>
    </table>    
        
  
  </body>
</html>