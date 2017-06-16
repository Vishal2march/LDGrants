<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  saViewNarrative.jsp
 * Creation/Modification History  :
 *
 *     SHusak       2/11/08     Created
 *
 * Description
 * This page lists 3 statutory narratives, when one is selected the narrative, title
 * and instructions will display.  Used for SA admin view and SA prior application view.
--%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  
  <h4>Project Description Narratives</h4>
  
  <form method="post" action="saReadNarrative.do?t=readNarr">  
    <select name="narrType">
      <option value="3">C/P Activities Performed</option>
      <option value="4">Overall Plan</option>
      <option value="5">Five year plan</option>
    </select>
    <input type="HIDDEN" name="m" value='<c:out value="${param.m}" />' />
    <input type="hidden" name="p" value="sa" />
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
  
  
  
  <br/><br/>
  
  <c:if test="${param.m=='admin'}">
    <p align="center">
    <c:url var="backURL" value="saAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>
    <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
    </p>
  </c:if>
  
  
  </body>
</html>
