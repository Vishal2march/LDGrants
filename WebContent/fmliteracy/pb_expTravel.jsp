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
 
 <p class="bdgtitle"><b>Project Budget<br/>
  Travel Expenses (Code 46)</b><br/>
  List specific project expenses that relate to Travel.  
  All expenses listed in this section must be fully described in the Application Narrative.  
 <br/><br/>
  <b>NOTE:</b> You must explain any changes to the project budget in the "Budget Changes Year 1"
  section of the Final Narrative.
 </p><br/>

<html:errors />
  <html:form action="/saveLitExpenses">
  <a name="year1" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>
    <tr>
      <td>  
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${thisGrant.fycode==travelItem.fycode}">   
       
        <c:choose>
        <c:when test="${travelItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Description</td>  
            <td>Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>     
          <tr>
            <td><c:out value="${travelItem.description}" /></td>
            <td><c:out value="${travelItem.purpose}" /></td>
            <td colspan="2"><c:out value="${travelItem.costsummary}" /></td>
          </tr>    
          <tr>
            <td>Dates of Travel</td>    
            <td>Name of Traveler</td>
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td><html:text name="travelItem" property="travelPeriod" indexed="true" /></td>
            <td><html:text name="travelItem" property="travelerName" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="journalEntry" indexed="true" /></td>
          </tr>   
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" />
                <html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" /></td>
          </tr>      
        </table>        
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Travel Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" />
                <html:hidden name="travelItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
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
 <hr/>
  
  
  
  
  <a name="year2" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b></td>
    </tr>   
    <tr>
      <td>  
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${thisGrant.fycode +1 ==travelItem.fycode}">   
       
        <c:choose>
        <c:when test="${travelItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Description</td>  
            <td>Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>     
          <tr>
            <td><c:out value="${travelItem.description}" /></td>
            <td><c:out value="${travelItem.purpose}" /></td>
            <td colspan="2"><c:out value="${travelItem.costsummary}" /></td>
          </tr>         
          <tr>
            <td>Dates of Travel</td>    
            <td>Name of Traveler</td>
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td><html:text name="travelItem" property="travelPeriod" indexed="true" /></td>
            <td><html:text name="travelItem" property="travelerName" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="journalEntry" indexed="true" /></td>
          </tr>   
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" />
                <html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" /></td>
          </tr>     
        </table>        
         </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Travel Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" />
                <html:hidden name="travelItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
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
  <hr/>
  <a name="year3" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b></td>
    </tr>   
    <tr>
      <td>  
        <logic:present name="BudgetCollectionBean" property="allTravelRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allTravelRecords" id="travelItem" >  
        <c:if test="${thisGrant.fycode +2 ==travelItem.fycode}">   
       
        <c:choose>
        <c:when test="${travelItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td>Description</td>  
            <td>Purpose</td>
            <td colspan="2">Calculation of cost</td>
          </tr>     
          <tr>
            <td><c:out value="${travelItem.description}" /></td>
            <td><c:out value="${travelItem.purpose}" /></td>
            <td colspan="2"><c:out value="${travelItem.costsummary}" /></td>
          </tr>   
          <tr>
            <td>Dates of Travel</td>    
            <td>Name of Traveler</td>
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td><html:text name="travelItem" property="travelPeriod" indexed="true" /></td>
            <td><html:text name="travelItem" property="travelerName" indexed="true" /></td>
            <td colspan="2"><html:text name="travelItem" property="journalEntry" indexed="true" /></td>
          </tr>   
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${travelItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" />
                <html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" /></td>
          </tr>     
        </table>        
         </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Travel Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="travelItem" property="id" indexed="true" />
                <html:hidden name="travelItem" property="categoryTotal" indexed="true" />
                <html:hidden name="travelItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${travelItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="travelItem" property="expsubmittedStr" indexed="true" /></td>
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
 <logic:notEmpty name="BudgetCollectionBean" property="allTravelRecords">
    <p align="center"> 
      <c:choose >
      <c:when test="${appStatus.allowSubmitFinal3=='false'}">
          <input type="button" value="Save Travel Expenses" disabled="disabled" />
      </c:when>
      <c:otherwise >
        <input type="HIDDEN" name="currtab" value="6"/>
        <input type="HIDDEN" name="p" value='<c:out value="${p}" />' />
        <input type="HIDDEN" name="i" value='7' />
        <html:submit value="Save Travel Expenses" />
      </c:otherwise>
      </c:choose>
    </p>
  </logic:notEmpty>

  </html:form>
  <br/><hr/><br/>
  </body>
</html>
