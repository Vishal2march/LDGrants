<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Public Library Construction Application</title>
  </head>
  <body>
  
  <h4>DASNY - Public Library Construction Grant Projects</h4>
  
  <p><form action="cnDasnyNav.do?task=searchDasnyGrants" method="POST">
  View Public Library Construction grant projects for fiscal year:<br/>
  Fiscal Year <select name="fycode">
                <c:forEach var="row" items="${dropDownList}">
                <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
                </c:forEach>       
              </select>
       <input type="SUBMIT" value="View" />
  </form></p>
  
  
  
  <logic:notEmpty name="allProjects">
  <br/>
  <table width="98%" align="center" summary="for layout only" class="boxtype" >
    <tr>
      <th>System</th><th>Institution</th><th>Project Number</th>
      <th>Fiscal Year</th><th>Project Title</th>
    </tr>  
    
    <c:forEach var="grantBean" items="${allProjects}">
        <c:url var="currURL" value="cnDasnyNav.do">
            <c:param name="task" value="grant"/> 
            <c:param name="id" value="${grantBean.grantid}"/>
        </c:url>
      <tr>
        <td><c:out value="${grantBean.systemName}" /></td>
        <td><c:out value="${grantBean.instName}" /></td>
        <td><a href='<c:out value="${currURL}" />' >
            03<fmt:formatNumber value="${grantBean.fccode}" />
            -<fmt:formatNumber value="${grantBean.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${grantBean.projseqnum}" minIntegerDigits="4" pattern="####" />
        </a></td>        
        <td><c:out value="${grantBean.fiscalYear}" /></td>
        <td><c:out value="${grantBean.title}" /></td>
      </tr>       
    </c:forEach>
    
   </table>
   </logic:notEmpty>
  
  
  
  </body>
</html>