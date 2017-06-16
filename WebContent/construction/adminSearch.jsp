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
  
  <h4>Search Construction Applications</h4>  
  
  <p>
  <form method="POST" action="cnAdminSearchAction.do?i=fiscalyear">
        List construction applications for fiscal year:
        <select name="fycode">
          <c:forEach var="row" items="${dropDownList}">
          <option value='<c:out value="${row.id}"/>'><c:out value="${row.description}"/></option>
          </c:forEach>       
       </select>
       <input type="SUBMIT" value="View" /> 
  </form>
  </p><br/>
    
  <p>
  <form method="POST" action="cnAdminSearchAction.do?i=systemsearch">
        List construction applications for members of PLS:
        <select name="sysInstId">
          <c:forEach var="row" items="${dropDownSystems}">
          <option value='<c:out value="${row.idLong}"/>'><c:out value="${row.description}"/></option>
          </c:forEach>       
       </select>
       <input type="SUBMIT" value="View" /> 
  </form>
  </p><br/>
  
  <p>
  <form method="POST" action="cnAdminSearchAction.do?i=instname">
        List construction applications for Institution name:
        <input type="text" name="instname"/>&nbsp;&nbsp;&nbsp;
        <input type="SUBMIT" value="View" /> 
  </form>
  </p><br/>
    
  <p>
  <form method="POST" action="cnAdminSearchAction.do?i=projnumber">
        View construction application for Project Number (last 4 digits ONLY):
        <input type="text" name="projnum"/>&nbsp;&nbsp;&nbsp;
        <input type="SUBMIT" value="View" /> 
  </form>
  </p>
  
  <hr/>  
  
  <logic:notEmpty name="allGrants">
  <br/>
  <table width="90%" align="center" summary="for layout only" class="boxtype" >
    <tr>
      <th colspan="4">Construction Grant Applications</th>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
      <th>Project Number</th><th>Institution</th><th>Fiscal Year</th><th>Project Title</th>
    </tr>  
    
    <c:forEach var="grantBean" items="${allGrants}" >      
        <c:url var="currURL" value="cnAdminNav.do">
            <c:param name="item" value="grant"/> 
            <c:param name="id" value="${grantBean.grantid}"/>
        </c:url>
      <tr>
        <td align="center"><a href='<c:out value="${currURL}" />' >
            03<fmt:formatNumber value="${grantBean.fccode}" />
            -<fmt:formatNumber value="${grantBean.fycode}" minIntegerDigits="2" />
            -<fmt:formatNumber value="${grantBean.projseqnum}" minIntegerDigits="4" pattern="####" />
        </a></td>
        <td align="center"><c:out value="${grantBean.instName}" /></td>
        <td align="center"><c:out value="${grantBean.fiscalyear}" /></td>
        <td align="center"><c:out value="${grantBean.title}" /></td>
      </tr>       
    </c:forEach>
    
  </table>      
  </logic:notEmpty>
  
  </body>
</html>