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
  III. Employee Benefits (Code 80)</b><br/>
  List all project employees as described under "Salaries."  
  Provide the amount of funds to be used for each individual's benefits.
 </p><br/>
  
  <c:forEach var="row" items="${fyTotals}" >
    <c:if test="${row.fycode==thisGrant.fycode || row.fycode==thisGrant.fycode +1}" >
    
        <c:if test="${row.warning=='true'}" >
          <p align="center" class="error"><c:out value="${row.litWarning}" /></p>
        </c:if>
        
    </c:if>
  </c:forEach>
 
 <html:form action="/saveLitAdminBudget">
  
  <INPUT type="hidden" name="currtab" value="2">
  <input type="HIDDEN" name="p" value='<c:out value="${p}" />'/>
  <input type="HIDDEN" name="littab" value="3"/>
    
  <table width="95%" align="center" summary="for layout only" >
    <tr>
        <td><b>Admin Instructions:</b> Click the link to 'Add Benefits Approval Record' to add a record for this budget category 
        and fiscal year. Then type in the total 'AmtApproved' for this budget category and fiscal year. 
        Only 1 approval record per category per fiscal year.</td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b><br/>
        <c:url var="add1" value="addLitAdminAward.do">
            <c:param name="tab" value="2"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="3"/>
        </c:url>
        <a href="<c:out value='${add1}'/>">Add Benefits Approval Record for Year 1</a>        
      </td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        <c:if test="${thisGrant.fycode==benefitItem.fycode}">   
         
        <c:choose>
        <c:when test="${benefitItem.categoryTotal=='false'}">
        <%--this is for regular applicant budget records--%>
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
            <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${benefitItem.amountapprovedStr}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <%--<td><fmt:formatNumber value="${benefitItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>      
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Admin Benefits Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="25%"></td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:hidden name="benefitItem" property="id" indexed="true" /></td>
            <td><html:text name="benefitItem" property="amountapprovedStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <%--<td><html:text name="benefitItem" property="expapprovedStr" indexed="true" /></td>--%>
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
  
  
 
  <table width="95%" align="center" summary="for layout only" >
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b><br/>
        <c:url var="add2" value="addLitAdminAward.do">
            <c:param name="tab" value="2"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="3"/>
        </c:url>
        <a href="<c:out value='${add2}'/>">Add Benefits Approval Record for Year 2</a>
      </td>
    </tr>
      <c:set var="fyrec" value="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>      
        <logic:present name="BudgetCollectionBean" property="allBenRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allBenRecords" id="benefitItem" > 
        <c:if test="${benefitItem.fycode ==thisGrant.fycode+1}">   
 
        <c:choose>
        <c:when test="${benefitItem.categoryTotal=='false'}">
        <%--this is for regular applicant budget records--%>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="2">Name</td>   
            <td colspan="2">Benefits Percentage</td>
          </tr>      
          <tr>
            <td colspan="2"><c:out value="${benefitItem.name}" /></td>
            <td colspan="2"><fmt:formatNumber  value="${benefitItem.benpercent}"/></td>
          </tr>          
          <tr>
            <td width="25%">AmtRequested</td>
            <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td>
            <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${benefitItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${benefitItem.amountapprovedStr}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <%--<td><fmt:formatNumber value="${benefitItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>                     
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="4"><b>Admin Benefits Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="25%"></td> <td width="25%">AmtApproved</td>
            <td width="25%">ActualExpense</td> <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><html:hidden name="benefitItem" property="id" indexed="true" /></td>
            <td><html:text name="benefitItem" property="amountapprovedStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${benefitItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <%--<td><html:text name="benefitItem" property="expapprovedStr" indexed="true" /></td>--%>
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
