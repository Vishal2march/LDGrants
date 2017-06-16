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
  
  Include only staff that are employees of the agency. Do not include consultants
  or per diem staff here.  They belong in the Purchased Services budget category. One 
  full-time equivalent (FTE) equals one person working an entire week for each week of the 
  project. Express partial FTE's in decimals, e.g., a teacher working one day per week equal .2 FTE. 
  </p>  
  <br/><br/>
    
  <c:forEach var="row" items="${fyTotals}" >
    <c:if test="${row.fycode==thisGrant.fycode || row.fycode==thisGrant.fycode +1}" >
    
        <c:if test="${row.warning=='true'}" >
          <p align="center" class="error"><c:out value="${row.litWarning}" /></p>
        </c:if>
        
    </c:if>
  </c:forEach>
    
  <html:form action="/saveLitAdminBudget">  
  <INPUT type="hidden" name="currtab" value="1">
  <input type="HIDDEN" name="p" value='<c:out value="${p}" />'/>
  <input type="HIDDEN" name="littab" value='<c:out value="${littab}"/>'/>
  
  <table width="95%" align="center" summary="for layout only" >
    <tr>
        <td><b>Admin Instructions:</b> Click the link to 'Add Salaries Approval Record' to add a record for this budget category 
        and fiscal year. Then type in the total 'AmtApproved' for this budget category and fiscal year. 
        Only 1 approval record per category per fiscal year.</td>
    </tr>
    <tr>
        <td height="20"/>
    </tr>
    <tr>
      <td><b>Budget Year 1 (<c:out value="${thisGrant.fiscalyear}" />) </b><br/>
        <c:url var="add1" value="addLitAdminAward.do">
            <c:param name="tab" value="1"/>
            <c:param name="fy" value="0"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <c:choose>
        <c:when test="${littab=='1'}">
        <a href="<c:out value='${add1}'/>">Add Professional Salaries Approval Record for Year 1</a>
        </c:when>
        <c:otherwise>
        <a href="<c:out value='${add1}'/>">Add Support Staff Salaries Approval Record for Year 1</a>
        </c:otherwise></c:choose></td>
    </tr>    
      <c:set value="fyrec" var="false"/>
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>   
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        
        <c:if test="${thisGrant.fycode==personalItem.fycode}">
        
        <c:choose>
        <c:when test="${personalItem.categoryTotal=='false'}">
        <%--this is for regular applicant budget records--%>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td width="20%">Name</td>
            <td width="20%">Title</td>
            <td width="20%">Salary/Wage</td>
            <td width="20%">FTE/Hours</td>    
            <td width="20%">Type</td>
          </tr>     
          <tr>
            <td><c:out value="${personalItem.name}" /></td>
            <td><c:out value="${personalItem.title}" /></td>
            <td><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
            <td><c:out value="${personalItem.fte}" /></td>
            <td><c:if test="${personalItem.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${personalItem.typeCode=='4'}" >
                Support Staff
              </c:if></td>
          </tr>        
          <tr>
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapprovedStr}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <%--<td><fmt:formatNumber value="${personalItem.expapprovedStr}"  type="currency" maxFractionDigits="0"/></td>--%>
          </tr>  
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>        
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="5"><b>Admin Salaries Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="20%">&nbsp;</td> <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td><%-- <td width="25%">ExpApproved</td>--%>
            <td width="20%">&nbsp;</td><td width="20%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="personalItem" property="id" indexed="true" />
                <html:hidden name="personalItem" property="categoryTotal" indexed="true" /></td>
            <td><html:text name="personalItem" property="amountapprovedStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <%--<td><html:text name="personalItem" property="expapprovedStr" indexed="true" /></td>--%>
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



  <table width="95%" align="center" summary="for layout only">
    <tr>
      <td><b>Budget Year 2 (<c:out value="${thisGrant.fiscalyear +1}" />) </b><br/>
        <c:url var="add2" value="addLitAdminAward.do">
            <c:param name="tab" value="1"/>
            <c:param name="fy" value="1"/>
            <c:param name="p" value="${p}"/>
            <c:param name="t" value="${littab}"/>
        </c:url>
        <c:choose>
        <c:when test="${littab=='1'}">
        <a href="<c:out value='${add2}'/>">Add Professional Salaries Approval Record for Year 2</a>
        </c:when>
        <c:otherwise>
        <a href="<c:out value='${add2}'/>">Add Support Staff Salaries Approval Record for Year 2</a>
        </c:otherwise></c:choose></td>
    </tr>
      <c:set var="fyrec" value="false"/>      
      <c:set var="amtreq" value="0"/>
      <c:set var="amtappr" value="0"/>
      <c:set var="expsub" value="0"/>
      <c:set var="expappr" value="0"/>
    <tr>
      <td>
        <logic:present name="BudgetCollectionBean" property="allPersRecords" >
        <logic:iterate name="BudgetCollectionBean" property="allPersRecords" id="personalItem" > 
        <c:if test="${personalItem.fycode== thisGrant.fycode+1}">
        
        <c:choose>
        <c:when test="${personalItem.categoryTotal=='false'}">
        <%--this is for regular applicant budget records--%>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr >
            <td width="20%">Name</td>
            <td width="20%">Title</td>
            <td width="20%">Salary/Wage</td>
            <td width="20%">FTE/Hours</td>  
            <td width="20%">Type</td>
          </tr>     
          <tr>
            <td><c:out value="${personalItem.name}" /></td>
            <td><c:out value="${personalItem.title}" /></td>
            <td ><fmt:formatNumber value="${personalItem.salaryf}" type="currency"  /></td>
            <td ><c:out value="${personalItem.fte}" /></td>
            <td><c:if test="${personalItem.typeCode=='3'}" >
                Professional
              </c:if>
              <c:if test="${personalItem.typeCode=='4'}" >
                Support Staff
              </c:if></td>
          </tr>        
          <tr>
            <td width="20%">AmtRequested</td>
            <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td>
            <%--<td width="25%">ExpApproved</td>--%>
          </tr>
          <tr>
            <td><fmt:formatNumber value="${personalItem.grantrequest}" type="currency" maxFractionDigits="0" /></td>
            <td><fmt:formatNumber value="${personalItem.amountapprovedStr}" type="currency" maxFractionDigits="0"/></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <%--<td><fmt:formatNumber value="${personalItem.expapprovedStr}" type="currency" maxFractionDigits="0"/></td>--%>
          </tr>           
        </table>
        </c:when>
        
        <%--this is for admin budget record for approval by category--%>
        <c:otherwise>
        <table width="100%" summary="for layout only" class="boxtype">
          <tr>
            <td colspan="5"><b>Admin Salaries Category Approval Record</b></td>            
          </tr>     
          <tr>
            <td width="20%">&nbsp;</td> <td width="20%">AmtApproved</td>
            <td width="20%">ActualExpense</td> <%--<td width="25%">ExpApproved</td>--%>
            <td width="20%">&nbsp;</td><td width="20%">&nbsp;</td>
          </tr>
          <tr>
            <td><html:hidden name="personalItem" property="id" indexed="true" />
                <html:hidden name="personalItem" property="categoryTotal" indexed="true" /></td>
            <td><html:text name="personalItem" property="amountapprovedStr" indexed="true" /></td>
            <td><fmt:formatNumber value="${personalItem.expsubmitted}" type="currency" maxFractionDigits="0" /></td>
            <%--<td><html:text name="personalItem" property="expapprovedStr" indexed="true" /></td>--%>
          </tr>  
        </table>        
        </c:otherwise>
        </c:choose>
        
        </c:if>
        </logic:iterate></logic:present>
      </td>
    </tr>
  </table>

  
  <c:if test="${lduser.adminfl=='approve'}" >
    <p align="center">
       <input type="SUBMIT" value="Save" name="btn" /><br/><br/>
   <%--<input type="SUBMIT" value="Copy Amt Requested" name="btn" />  <input type="SUBMIT" value="Copy Exp Submitted" name="btn" />--%>
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
