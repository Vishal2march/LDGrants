<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>
  
   
  <p><b>View Applications Submitted by Member Libraries for Fiscal Year:</b>
  <form method="POST" action="cnReviewNav.do?item=listMemberApps">
        <select name="fycode">
          <c:forEach var="row" items="${dropDownList}">
          <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
          </c:forEach>       
       </select>
       <input type="SUBMIT" value="View" /> 
  </form></p>
  
  
  <logic:notEmpty name="allApplications">
  <br/>
  <table width="90%" align="center" summary="for layout only" class="boxtype" >
    <tr>
      <th colspan="4">Construction Grant Applications Submitted by Member Libraries</th>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Fiscal Year</th>
      <th>Amount Requested</th>
    </tr>  
    
    <c:forEach var="GrantBean" items="${allApplications}" >
      <c:url var="currURL" value="cnReviewNav.do">
        <c:param name="item" value="checklist"/> 
        <c:param name="id" value="${GrantBean.grantid}"/>
      </c:url>
      
      <tr>
        <td><a href='<c:out value="${currURL}" />' >
        03<fmt:formatNumber value="${GrantBean.fccode}" />
        -<fmt:formatNumber value="${GrantBean.fycode}" minIntegerDigits="2" />
        -<fmt:formatNumber value="${GrantBean.projseqnum}" minIntegerDigits="4" pattern="####" /></a></td>
        <td><c:out value="${GrantBean.name}" /></td>
        <td><c:out value="${GrantBean.instName}" /></td>
        <td><c:out value="${GrantBean.fiscalYear}" /></td>
        <td><fmt:formatNumber type="currency" value="${GrantBean.amountReq}" maxFractionDigits="0" /></td>
      </tr>       
    </c:forEach>
    
  </table>    
  </logic:notEmpty>
  
  <br/><br/>
     
  </body>
</html>