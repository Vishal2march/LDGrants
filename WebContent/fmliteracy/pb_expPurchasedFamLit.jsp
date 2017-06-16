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
  Purchased Services (Code 40)</b><br/>
  List all services to be purchased for the project by service type (ie. consultants, 
  rentals, tuition, printing, communications, and other contractual services).  Attach per 
  diem rate for consultants, cost estimates, bids, or other supporting data.    
  <br/><br/>
  Consultant Services include professional and technical advice that will be provided by 
  individuals or groups of individuals.  Consultants are normally retained for a short period
  to provide advice about specific aspects of the project.  Consultants are normally expected
  to provide a report of their activities, usually at a time agreed upon before the 
  consultancy begins.  Provide the number of days the consultant is being hired for and their
  daily rate.<br/><br/>

  Contracted Services include professional or technical activities that will be performed by 
  commercial vendors or qualified individuals.  Contractual services are normally used for 
  project activities that cannot be carried out by the institution, or for those activities 
  that can be more economically performed by firms or individuals specializing in a particular
  service.<br/><br/>
  <b>NOTE:</b> You must explain any changes to the project budget in the "Budget Changes Year 1"
  section of the Final Narrative.
</p><br/>
  
  
  <a name="year1" />
  <html:errors />
  <html:form action="/saveLitContrExpenses">
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>
    
    <tr>
      <td>           
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" >  
        <c:if test="${thisGrant.fycode==contractItem.fycode}">   
            
        <c:choose>
        <c:when test="${contractItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td colspan="2">Description</td>
          </tr>     
           <tr >
            <td><c:out value="${contractItem.servicetype}" /></td>
            <td><c:out value="${contractItem.recipient}" /></td>
            <td colspan="2"><c:out value="${contractItem.servicedescr}" /></td>
          </tr>      
          <tr>
            <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
            <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
          </tr>  
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr >
            <td ><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td> <html:text name="contractItem" property="expsubmittedStr" indexed="true" />
            <html:hidden name="contractItem" property="categoryTotal" indexed="true" />
            <html:hidden name="contractItem" property="fycode" indexed="true" />
            <html:hidden name="contractItem" property="servicetype" indexed="true" />
            <html:hidden name="contractItem" property="recipient" indexed="true" />
            <html:hidden name="contractItem" property="servicedescr" indexed="true" />
            <html:hidden name="contractItem" property="grantrequest" indexed="true" />
            <html:hidden name="contractItem" property="amountapproved" indexed="true" />
            <html:hidden name="contractItem" property="id" indexed="true" /></td>
          </tr> 
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Purchased Services Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td><td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="contractItem" property="id" indexed="true" />
                <html:hidden name="contractItem" property="categoryTotal" indexed="true" />
                <html:hidden name="contractItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
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
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" > 
        <c:if test="${contractItem.fycode==thisGrant.fycode + 1}">   
            
        <c:choose>
        <c:when test="${contractItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td colspan="2">Description</td>
          </tr>     
           <tr >
            <td><c:out value="${contractItem.servicetype}" /></td>
            <td><c:out value="${contractItem.recipient}" /></td>
            <td colspan="2"><c:out value="${contractItem.servicedescr}" /></td>
          </tr>      
          <tr>
            <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
            <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
          </tr>  
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" />
            <html:hidden name="contractItem" property="categoryTotal" indexed="true" />
            <html:hidden name="contractItem" property="fycode" indexed="true" />
            <html:hidden name="contractItem" property="servicetype" indexed="true" />
            <html:hidden name="contractItem" property="recipient" indexed="true" />
            <html:hidden name="contractItem" property="servicedescr" indexed="true" />
            <html:hidden name="contractItem" property="grantrequest" indexed="true" />
            <html:hidden name="contractItem" property="amountapproved" indexed="true" />
            <html:hidden name="contractItem" property="id" indexed="true" /></td>
          </tr>    
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Purchased Services Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td><td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="contractItem" property="id" indexed="true" />
                <html:hidden name="contractItem" property="categoryTotal" indexed="true" />
                <html:hidden name="contractItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
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
  <a name="year2" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 3 (<c:out value="${thisGrant.fiscalyear +2}" />) </b></td>
    </tr>
    
    <tr>
      <td>           
        <logic:present name="BudgetCollectionBean" property="allContractRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allContractRecords" id="contractItem" > 
        <c:if test="${contractItem.fycode==thisGrant.fycode + 2}">   
            
        <c:choose>
        <c:when test="${contractItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Service Type</td>
            <td>Consultant/Vendor</td>
            <td colspan="2">Description</td>
          </tr>     
           <tr >
            <td><c:out value="${contractItem.servicetype}" /></td>
            <td><c:out value="${contractItem.recipient}" /></td>
            <td colspan="2"><c:out value="${contractItem.servicedescr}" /></td>
          </tr>  
          <tr>
            <td colspan="2">Encumbrance Date (mm/dd/yyyy)</td>    
            <td colspan="2">Check/Journal Entry#</td>
          </tr> 
          <tr>
            <td colspan="2"><html:text name="contractItem" property="encumbranceDateStr" indexed="true" /></td>
            <td colspan="2"><html:text name="contractItem" property="journalEntry" indexed="true" /></td>
          </tr>  
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <td width="25%">&nbsp;</td>
          </tr>
          <tr >
            <td><fmt:formatNumber value="${contractItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" />
            <html:hidden name="contractItem" property="categoryTotal" indexed="true" />
            <html:hidden name="contractItem" property="fycode" indexed="true" />
            <html:hidden name="contractItem" property="servicetype" indexed="true" />
            <html:hidden name="contractItem" property="recipient" indexed="true" />
            <html:hidden name="contractItem" property="servicedescr" indexed="true" />
            <html:hidden name="contractItem" property="grantrequest" indexed="true" />
            <html:hidden name="contractItem" property="amountapproved" indexed="true" />
            <html:hidden name="contractItem" property="id" indexed="true" /></td>
          </tr>    
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Purchased Services Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td><td width="25%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="contractItem" property="id" indexed="true" />
                <html:hidden name="contractItem" property="categoryTotal" indexed="true" />
                <html:hidden name="contractItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${contractItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="contractItem" property="expsubmittedStr" indexed="true" /></td>
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
  
 
 <logic:notEmpty name="BudgetCollectionBean" property="allContractRecords">
    <p align="center"> 
      <c:choose >
      <c:when test="${appStatus.allowSubmitFinal3=='false'}">
          <input type="button" value="Save Purchased Expenses" disabled="disabled" />
      </c:when>
      <c:otherwise >
          <input type="HIDDEN" name="currtab" value="3"/>
          <input type="HIDDEN" name="p" value="fl" />
          <input type="HIDDEN" name="i" value='4' />
          <input type="SUBMIT" value="Save Purchased Expenses" />
      </c:otherwise>
      </c:choose>
    </p>
 </logic:notEmpty>
 
  </html:form>
  <br/><hr/><br/>
  </body>
</html>
