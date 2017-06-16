<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
 
  <h4>Interim Narratives</h4>
  
  <form method="post" action="liReadNarrative.do?item=readNarr">  
    <select name="narrType">
      <option value="52">Project Changes</option>
      <option value="53">Expended Funds</option>
      <option value="54">Anecdote</option>
    </select>
    <input type="HIDDEN" name="m" value='<c:out value="${param.m}" />' />
    <input type="hidden" name="p" value="lit" />
    <input type="SUBMIT" value="Select" />
  </form>
  
  <br/>
  <c:if test="${projNarrative != null}" >
    <table align="center" width="90%" class="boxtype" summary="for layout only">
      <tr bgcolor="Silver">
        <th><c:out value="${projNarrative.narrativeTitle}" /></th>
      </tr>
      <tr>
        <td><c:out value="${projNarrative.narrativeDescr}" /><hr/></td>
      </tr>
      <tr>
        <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
      </tr>
    </table>
  </c:if> 
  
  
  <c:if test="${param.m=='finterim'}" >
    <br/><br/>
    <c:url var="backURL" value="flAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>   
    <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:if>
  
  <c:if test="${param.m=='ainterim'}" >
    <br/><br/>
    <c:url var="backURL" value="alAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>   
    <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:if>
  
  </body>
</html>
