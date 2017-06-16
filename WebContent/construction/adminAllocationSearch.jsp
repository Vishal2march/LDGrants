<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
  
  <h4>Public Library System Allocation Records</h4>
  
  
  <p>View PLS Allocations by Fiscal Year<br/>
    <form method="POST" action="cnAdminNav.do?item=listAllocations">
        <select name="fycode">
          <c:forEach var="row" items="${dropDownList}">
          <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
          </c:forEach>       
       </select>
       <input type="hidden" name="fccode" value="86"/>
       <input type="SUBMIT" value="View" /> 
    </form>
  </p>
  
  
  <br/><br/>
  
  <c:if test="${recordExists=='true'}">
     <p><font color="red">An allocation record already exists for the selected 
     PLS and Fiscal Year.  Please update the existing record.</font></p>
  </c:if>
  
  <p><a href="cnAdminNav.do?item=newAlloc&fc=86">Add</a> a new Allocation Record</p>
    
  <logic:notEmpty name="allAllocations">
  
    <table width="90%" align="center" summary="for layout only" class="boxtype" >
    <tr><td>&nbsp;</td></tr>
    <tr>
      <th>Edit</th><th>System</th><th>Initial Allocation</th>
      <th>Additional Allocation</th><th>Fiscal Year</th>
    </tr>  
    
    <c:forEach var="allocBean" items="${allAllocations}" >   
    
        <c:url var="editURL" value="cnAdminNav.do">
            <c:param name="item" value="allocrecord"/>
            <c:param name="id" value="${allocBean.systemYearDetailId}"/>
        </c:url>
        
      <tr>
        <td align="center"><a href='<c:out value="${editURL}"/>'>Edit</a></td>
        <td align="center"><c:out value="${allocBean.systemName}" /></td>
        <td align="center"><fmt:formatNumber type="currency" value="${allocBean.initialAlloc}" maxFractionDigits="0"/></td>
        <td align="center"><fmt:formatNumber type="currency" value="${allocBean.additionalAlloc}" maxFractionDigits="0"/></td>
        <td align="center"><c:out value="${allocBean.year}" /></td>
      </tr>       
    </c:forEach>    
  </table>    
    
  </logic:notEmpty>
  
  </body>
</html>