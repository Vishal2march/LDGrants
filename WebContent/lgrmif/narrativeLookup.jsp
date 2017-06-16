<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  
  <h5>Project Narratives</h5>
  
  <form method="post" action="lgReadNarrative.do?t=readNarr">  
    <select name="narrType">
      <option value="69">Describe records management problem</option>
      <option value="70">Identify records and previous projects</option>
      <option value="71">Explain why funding is essential</option>
      <option value="72">Identify intended result(s) and anticipated benefits</option>
      <option value="73">Describe contributions to records management program</option>
      <option value="74">Provide detailed outline and timetable for project</option>
      <option value="75">Address project category requirements</option>
      <option value="76">Explain responsibilities of project staff</option>
      <option value="77">Demonstrate contributions to project</option>
      <option value="78">Describe how the program will be maintained</option>  
      <option value="88">Describe contributions of service to public</option>  
      <option value="83">Code 15 Professional Staff Salaries</option>
      <option value="81">Code 16 Support Staff Salaries</option>
      <option value="82">Code 20 Equipment</option>
      <option value="85">Code 45 Supplies and Materials</option>
      <option value="79">Code 30 Minor Remodeling</option>
      <option value="84">Code 40 Purchased Services</option>
      <option value="80">Code 49 Purchased Services BOCES</option>
      <option value="86">Code 46 Travel</option>
      <option value="87">Code 80 Employee Benefits</option>            
      <option value="2">Final Report Narrative</option>
      </select>
    <input type="HIDDEN" name="m" value="admin" />
    <input type="hidden" name="p" value="lg" />
    <input type="SUBMIT" value="Select" />
  </form>
  

  <br/>
  <c:if test="${projNarrative != null}" >
    <table align="center" width="90%" class="boxtype" summary="for layout only">
      <tr bgcolor="Silver">
        <th><c:out value="${projNarrative.narrativeTitle}" /></th>
      </tr>
      <tr>
        <td><bean:write name="projNarrative" property="narrativeDescr" filter="false" /><hr/></td>
      </tr>
      <tr>
        <td><bean:write name="projNarrative" property="narrative" filter="false" /></td>
      </tr>
    </table>
  </c:if> 
    
  
   
  <br/>
  <c:url var="backURL" value="lgAdminNav.do">
    <c:param name="id" value="${grantid}" />
    <c:param name="item" value="grant" />
  </c:url>
  <p align="center"><a href='<c:out value="${backURL}" />' >Back to Application Checklist</a></p>
  
  
  </body>
</html>
