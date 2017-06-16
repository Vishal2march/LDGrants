<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
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
    
  <p class="bdgtitle"><b>Project Budget<br/>
  III. Employee Benefits (Code 80)</b><br/>
  List all project employees as described under "Salaries."  
  Provide the amount of funds to be used for each individual's benefits.
  <br/><br/>
  <b>NOTE:</b> You must explain any changes to the project budget in the "Budget Changes Year 1"
  section of the Final Narrative.
 </p>
 <br/><br/>
  
  <a name="year1" />
  <html:errors />
  <html:form action="/saveLitExpenses" >
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>    
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        <c:if test="${thisGrant.fycode==benefitItem.fycode}">   
         
        <c:choose>
        <c:when test="${benefitItem.categoryTotal=='false'}">
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
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td ><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          </tr>              
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Benefits Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td><td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="benefitItem" property="id" indexed="true" />
                <html:hidden name="benefitItem" property="categoryTotal" indexed="true" />
                <html:hidden name="benefitItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="benefitItem" property="expsubmittedStr" indexed="true" /></td>
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
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        <c:if test="${benefitItem.fycode ==thisGrant.fycode+1}">   
 
        <c:choose>
        <c:when test="${benefitItem.categoryTotal=='false'}">
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
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          </tr>              
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Benefits Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td><td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="benefitItem" property="id" indexed="true" />
                <html:hidden name="benefitItem" property="categoryTotal" indexed="true" />
                <html:hidden name="benefitItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${benefitItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="benefitItem" property="expsubmittedStr" indexed="true" /></td>
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
  <br/>
  
  <logic:notEmpty name="BudgetCollectionBean" property="allBenRecords">
      <p align="center">
        <c:choose >
        <c:when test="${appStatus.allowSubmitFinal2=='false'}">
            <input type="button" value="Save Benefits Expenses" disabled="disabled" />
        </c:when>
        <c:otherwise >
            <input type="HIDDEN" name="currtab" value="2"/>
            <input type="HIDDEN" name="p" value='<c:out value="${p}" />' />
            <input type="HIDDEN" name="i" value='3' />
            <input type="SUBMIT" value="Save Benefits Expenses" />
        </c:otherwise>
        </c:choose>
      </p>
  </logic:notEmpty>
  
  </html:form>
  <br/><hr/><br/>
  
  </body>
</html>
