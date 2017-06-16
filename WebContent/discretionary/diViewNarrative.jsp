<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  diViewNarrative.jsp
 * Creation/Modification History  :
 *
 *     SHusak       2/5/08     Created
 *
 * Description
 * This page lists all DI narratives in dropdown, then displays narrative from db.
 * Used for both di admin and di participating view - uses param module to differentiate
 * between the 2 layouts.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>  
  
  <h4>Project Description Narratives</h4>
  
  <form method="post" action="diReadNarrative.do?t=readNarr">  
    <select name="narrType">
      <option value="19">Size of Institution's Operation</option>
      <option value="20">Total Collection of Library Research Materials</option>
      <option value="3">C/P Activities Performed</option>
      <option value="16">Environmental Conditions</option>
      <option value="21">Preparation for Disasters</option>
      <option value="22">Security arrangements for Protecting Collections</option>
      <option value="23">Participation in Cooperative C/P Activities</option>
      <option value="24">Access policies and practices of Institution</option>
      <option value="10">Bibliographic Control</option>
      <option value="25">Ownership of Materials</option>
      <option value="6">Description of Materials</option>
      <option value="7">Significance of Materials</option>
      <option value="12">Timetable</option>
      <option value="13">C/P Activities</option>
      <option value="15">Personnel and Vendors</option>
      <option value="17">Staff Contributions</option>
      <option value="18">Financial Contributions</option>
      <option value="66">Need for proposed Training</option>
      <option value="67">Training objectives</option>
      <option value="68">Publicity</option>
      <option value="34">Information dissemination</option>
    </select>
    <input type="HIDDEN" name="m" value='<c:out value="${param.m}" />' />
    <input type="hidden" name="p" value="di" />
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
    
  
   <c:if test="${param.m=='admin'}" >
    <br/>
    <c:url var="backURL" value="diAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>
    <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:if>
    
  </body>
</html>
