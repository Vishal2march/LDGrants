<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
  </head>
  <body>
    
  <h4>Public Library System - Literacy Allocation Records</h4>
  
  
  <c:choose>
  <c:when test="${param.p=='fl'}"><%--FAMILY LITERACY --%>
  
        <p>View PLS Allocations by Fiscal Year<br/>
        <form method="POST" action="flAdminAllocNav.do?i=listAllocations">
            <select name="fycode">
              <c:forEach var="row" items="${dropDownList}">
              <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
              </c:forEach>       
           </select>
           <input type="hidden" name="fccode" value="42"/>
           <input type="SUBMIT" value="View" /> 
        </form>    
        </p>  
      
        <br/><br/>  
        <p><a href="flAdminAllocNav.do?i=newAlloc&fc=42">Add</a> a new Allocation Record</p>
    
  </c:when>
  <c:when test="${param.p=='al'}"><%--ADULT LITERACY --%>
    
        <p>View PLS Allocations by Fiscal Year<br/>
        <form method="POST" action="alAdminAllocNav.do?i=listAllocations">
            <select name="fycode">
              <c:forEach var="row" items="${dropDownList}">
              <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
              </c:forEach>       
           </select>
           <input type="hidden" name="fccode" value="40"/>
           <input type="SUBMIT" value="View" /> 
        </form>    
        </p>  
      
        <br/><br/>  
        <p><a href="alAdminAllocNav.do?i=newAlloc&fc=40">Add</a> a new Allocation Record</p>
    
  </c:when>
  </c:choose>
  
  
  <logic:notEmpty name="allAllocations">
  
    <table width="90%" align="center" summary="for layout only" class="boxtype" >
    <tr><td>&nbsp;</td></tr>
    <tr>
      <th>Edit</th><th>System</th><th>Annual Allocation</th><th>Appropriation</th>
      <th>3-Year Allocation Total</th><th>Fiscal Year</th>
    </tr>  
    
    <c:forEach var="allocBean" items="${allAllocations}" >   
    
        <c:choose>
        <c:when test="${allocBean.fccode==40}"><%--ADULT LIT--%>
            <c:url var="editURL" value="alAdminAllocNav.do">
                <c:param name="i" value="allocrecord"/>
                <c:param name="id" value="${allocBean.systemYearDetailId}"/>
            </c:url>
        </c:when>
        <c:otherwise><%--FAMILY LIT--%>
            <c:url var="editURL" value="flAdminAllocNav.do">
                <c:param name="i" value="allocrecord"/>
                <c:param name="id" value="${allocBean.systemYearDetailId}"/>
            </c:url>
        </c:otherwise>
        </c:choose>
        
      <tr>
        <td align="center"><a href='<c:out value="${editURL}"/>'>Edit</a></td>
        <td align="center"><c:out value="${allocBean.systemName}" /></td>
        <td align="center"><fmt:formatNumber type="currency" value="${allocBean.initialAlloc}" maxFractionDigits="0"/></td>
        <td align="center"><fmt:formatNumber type="currency" value="${allocBean.finalRecommend}" maxFractionDigits="0"/></td>
        <td align="center"><fmt:formatNumber type="currency" value="${allocBean.additionalAlloc}" maxFractionDigits="0"/></td>
        <td align="center"><c:out value="${allocBean.year}" /></td>
      </tr>       
    </c:forEach>    
  </table>    
    
  </logic:notEmpty>
  
  
  </body>
</html>