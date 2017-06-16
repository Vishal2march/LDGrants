<%--
 * @author  Stefanie Husak
 * @version 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  coViewNarrative.jsp
 * Creation/Modification History  :
 *
 * SHusak       7/19/07     Created
 *
 * Description
 * This page allows the applicant to select a project descr narrative for Co
 * and view a read only version of it.  Used for co admin and co participants.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title></title>
  </head>
  <body>
    
  <h4>Project Description Narratives</h4>  
   
  <form method="post" action="coReadNarrative.do?t=readNarr">  
    <select name="narrType">
      <option value="1">Summary Description</option>
      <option value="6">Description of Materials</option>
      <option value="7">Significance of Materials</option>
      <option value="8">Participating Libraries</option>
      <option value="9">Coordination of Activities</option>
      <option value="10">Bibliographic Control</option>
      <option value="11">Use of Online Databases</option>
      <option value="12">Timetable</option>
      <option value="13">C/P Activities</option>
      <option value="14">Special Equipment</option>
      <option value="15">Personnel and Vendors</option>
      <option value="16">Environmental Conditions</option>
      <option value="17">Staff Contributions</option>
      <option value="18">Financial Contributions</option>
      <option value="66">Need for Training</option>
      <option value="67">Training Objectives</option>
      <option value="68">Publicity</option>
      <option value="34">Informaton dissemination</option>
    </select>
    <input type="HIDDEN" name="m" value='<c:out value="${param.m}" />' />
    <input type="hidden" name="p" value="co" />
    <input type="SUBMIT" value="Select" />
  </form>
  
  
  <br/><br/><br/>
  <c:if test="${projNarrative != null}" >
    <table align="center" border="1" width="90%">
      <tr bgcolor="Silver">
        <th><c:out value="${projNarrative.narrativeTitle}" /></th>
      </tr>
      <tr>
        <td><c:out value="${projNarrative.narrativeDescr}" /></td>
      </tr>
      <tr>
        <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
      </tr>
    </table>
  </c:if> 
  
  
  <br/><br/>
  <c:if test="${param.m=='admin'}" >
    <p align="center">
      <c:url var="backURL" value="coAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>
      <a href='<c:out value="${backURL}" />' >Back to Application Checklist</a>
    </p>
  </c:if>
  
  </body>
</html>
