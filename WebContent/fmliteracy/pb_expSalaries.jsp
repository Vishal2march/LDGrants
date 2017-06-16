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
    
  <c:set var="littab" value="${param.t}" />
  <p class="bdgtitle"><b>Project Budget</b><br/>
  
  <c:choose >
  <c:when test="${littab=='1'}">
    <b>I. Professional Salaries  (Code 15)</b><br/>
  </c:when>
  <c:when test="${littab=='2'}">
    <b>II. Support Staff Salaries  (Code 16)</b><br/>
  </c:when>
  </c:choose>
  <br/>
  Include only staff that are employees of the agency. Do not include consultants
  or per diem staff here.  They belong in the Purchased Services budget category. One 
  full-time equivalent (FTE) equals one person working an entire week for each week of the 
  project. Express partial FTE's in decimals, e.g., a teacher working one day per week equal .2 FTE. 
  <br/><br/>
  <b>NOTE:</b> You must explain any changes to the project budget in the "Budget Changes Year 1"
  section of the Final Narrative.
  </p>  
  <br/><br/>
    
  
  <html:errors />
  <html:form action="/saveLitExpenses" >
  <a name="year1" />
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b></td>
    </tr>
    <tr>
      <td>   
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${thisGrant.fycode==personalItem.fycode}">
        
        <c:choose>
        <c:when test="${personalItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Name</td>
            <td>Title</td>
            <td>Salary</td>
            <td>FTE/</td>   
          </tr>     
          <tr>
            <td><c:out value="${personalItem.name}" /></td>
            <td><c:out value="${personalItem.title}" /></td>
            <td><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
            <td><c:out value="${personalItem.fte}" /></td>
          </tr>        
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
          </tr>  
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin approval by category & apcnt expense by category (new 1/3/11)--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Salaries Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td><td width="25%">&nbsp;</td> 
          </tr>
          <tr>
            <td width="25%"><html:hidden name="personalItem" property="id" indexed="true" />
                <html:hidden name="personalItem" property="categoryTotal" indexed="true" />
                <html:hidden name="personalItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
          </tr>  
          <tr>
            <td colspan="4">Use the Save Salaries Button below to Save all budget records on this page</td>
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
  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b></td>
    </tr>
    <tr>
      <td>
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${personalItem.fycode== thisGrant.fycode+1}">
        
        <c:choose>
        <c:when test="${personalItem.categoryTotal=='false'}">
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td>Name</td>
            <td>Title</td>
            <td>Salary</td>
            <td>FTE</td>       
          </tr>     
          <tr>
            <td><c:out value="${personalItem.name}" /></td>
            <td><c:out value="${personalItem.title}" /></td>
            <td><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
            <td><c:out value="${personalItem.fte}" /></td>
          </tr>        
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
          </tr>
         <tr>
            <td ><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0" /></td>
            <td ><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
         </tr>  
        </table>
        </c:when>
        
        <c:otherwise>
        <%--this is for admin budget record for approval by category--%> 
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Salaries Category Record</b></td>            
          </tr>     
          <tr>
            <td width="25%">&nbsp;</td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <td width="25%">&nbsp;</td> 
          </tr>
          <tr>
            <td><html:hidden name="personalItem" property="id" indexed="true" />
                <html:hidden name="personalItem" property="categoryTotal" indexed="true" />
                <html:hidden name="personalItem" property="amountapproved" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapproved}" type="currency" maxFractionDigits="0"/></td>
            <td><html:text name="personalItem" property="expsubmittedStr" indexed="true" /></td>
          </tr>  
          <tr>
            <td colspan="4">Use the Save Salaries Button below to Save all budget records on this page</td>
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
  
  <logic:notEmpty name="BudgetCollectionBean" property="allPersRecords">
      <p align="center">
      <c:choose >
      <c:when test="${appStatus.allowSubmitFinal2=='false'}">
          <input type="button" value="Save Salaries" disabled="disabled" />
      </c:when>
      <c:otherwise >
        <input type="HIDDEN" name="currtab" value="1"/>
        <input type="HIDDEN" name="p" value='<c:out value="${p}" />' />
        <input type="HIDDEN" name="i" value='<c:out value="${littab}" />' />
        <html:submit value="Save Salaries" />
      </c:otherwise>
      </c:choose>    
      </p>
  </logic:notEmpty>

  </html:form>
  <br/><hr/><br/>  
  
  </body>
</html>
