<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>home</title>
  </head>
  <body>
  
  <h4>Admin Public Library Construction</h4>
  
  <p><form action="cnAdminNav.do?item=getSubmitted" method="POST">
  View applications <i>submitted</i> to LD for Fiscal Year:<br/>
  Fiscal Year <select name="fycode">
                <c:forEach var="row" items="${dropDownList}">
                <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
                </c:forEach>       
              </select>
       <input type="SUBMIT" value="View"/>
  </form></p>
       
       
 
  
  <logic:notEmpty name="allGrants">
  <br/>
  <table width="98%" align="center" summary="for layout only" cellspacing="3" cellpadding="3" class="boxtype">
    <tr>
      <th>System</th><th>Institution</th><th>Project Number</th>
      <th>Fiscal Year</th><th>Project Title</th><th>DASNY Approved</th>
    </tr>  
    
    <c:forEach var="grantBean" items="${allGrants}">
        <c:url var="currURL" value="cnAdminNav.do">
            <c:param name="item" value="grant"/> 
            <c:param name="id" value="${grantBean.grantid}"/>
        </c:url>
      <tr>
        <td><c:out value="${grantBean.systemName}"/></td>
        <td><c:out value="${grantBean.instName}"/></td>
        <td><a href='<c:out value="${currURL}" />' >
            03<fmt:formatNumber value="${grantBean.fccode}"/>
            -<fmt:formatNumber value="${grantBean.fycode}" minIntegerDigits="2"/>
            -<fmt:formatNumber value="${grantBean.projseqnum}" minIntegerDigits="4" pattern="####"/>
        </a></td>        
        <td><c:out value="${grantBean.fiscalYear}"/></td>
        <td><c:out value="${grantBean.title}"/></td>
        <td><c:out value="${grantBean.dasnyApprove}"/></td>
      </tr>       
    </c:forEach>
    
   </table>
   </logic:notEmpty>
  
  
  
  </body>
</html>