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
  
  <h4>PLS Due Date for Member Applications</h4>
  
  
  <table class="boxtype" width="100%" summary="for layout only">
    <tr>
        <th>Edit</th>
        <th>Fiscal Year</th>
        <th>System</th>
        <th>PLS Due Date</th>
        <th>LD Due Date</th>
        <th>System Allocation</th>
        <th>Total Amount Recommended</th>
    </tr>
    
    <c:forEach var="allocBean" items="${allAllocations}" >   
    
        <c:url var="editURL" value="cnReviewNav.do">
            <c:param name="item" value="duedaterecord"/>
            <c:param name="id" value="${allocBean.systemYearDetailId}"/>
        </c:url>
        
      <tr>
        <td align="center"><a href='<c:out value="${editURL}"/>'>Edit</a></td>
        <td align="center"><c:out value="${allocBean.year}" /></td>
        <td align="center"><c:out value="${allocBean.systemName}" /></td>
        <td align="center"><c:out value="${allocBean.dueDateStr}" /></td>
        <td align="center"><c:out value="${allocBean.programDueDateStr}"/></td>
        <td align="center"><fmt:formatNumber type="currency" value="${allocBean.initialAlloc}" maxFractionDigits="0" /></td>
        <td align="center"><fmt:formatNumber type="currency" value="${allocBean.tallyAmountRecommend}" maxFractionDigits="0" /></td>
      </tr>       
    </c:forEach>      
  </table>  
  
  
  </body>
</html>