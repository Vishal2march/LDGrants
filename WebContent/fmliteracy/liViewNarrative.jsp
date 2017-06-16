<%--
 * @author  Stefanie Husak
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the Application        :  liViewNarrative.jsp
 * Creation/Modification History  :
 *
 *     SHusak       12/24/08     Created
 *
 * Description
 * This page lists all Lit narratives in dropdown, then displays narrative from db.
 * Used for both fl/al participant view and admin
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
    
   <h4>Project Description Narratives</h4>
  
  <form method="post" action="liReadNarrative.do?item=readNarr">  
    <select name="narrType">
      <option value="1">Summary Description/Abstract</option>
      <option value="26">Project Need</option>
      <option value="5">Long-range Plan</option>
      <option value="27">Library programming</option>
      <option value="8">Involvement of Participating Libraries</option>
      <option value="28">Project Objectives</option>
      <option value="3">Project Activities</option>
      <option value="12">Timetable</option>
      <option value="29">Project Output</option>
      <option value="30">Measure Output</option>
      <option value="31">Project Outcomes</option>
      <option value="32">Measure Outcomes</option>
      <option value="33">Project Continuation</option>
      <option value="34">Distributing Information</option>      
      <option value="36">Other Funding Sources</option>
      <option value="35">Budget - Salaries</option>
      <option value="37">Budget - Benefits</option>
      <option value="38">Budget - Contracted Services</option>
      <option value="39">Budget - Supplies/Equip</option>
      <option value="40">Budget - Travel</option>
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
        <td><bean:write name="projNarrative" property="narrativeDescr" filter="false"/>
        <br/>The narrative should be <b>no more than 1 page</b> in length.<hr/></td>
      </tr>
      <tr>
        <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
      </tr>
    </table>
  </c:if> 
  
  
  <c:if test="${param.m=='fadmin'}" >
    <br/><br/>
    <c:url var="backURL" value="flAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>   
    <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:if>
  
  <c:if test="${param.m=='aadmin'}" >
    <br/><br/>
    <c:url var="backURL" value="alAdminNav.do">
      <c:param name="id" value="${grantid}" />
      <c:param name="item" value="grant" />
    </c:url>   
    <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  </c:if>
  
  </body>
</html>
