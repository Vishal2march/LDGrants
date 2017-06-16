<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
    <title>CP Discretionary Reports available to applicants</title>
  </head>
  <body>
  
  
  <h4>C/P Discretionary Data</h4>  
  <p>Find information about prior year Discretionary grant projects</p>  
  
  
  <h5>Search Results</h5>
    
  <table width="100%" border="1" summary="for layout only">  
        
    <c:forEach var="row" items="${allGrants}" >
      <tr>
        <td>
          <table summary="for layout only">
            <tr>
              <td><b>Institution</b></td>
              <td><c:out value="${row.instName}" /></td>
            </tr>
            <tr>
              <td><b>Project</b></td>
              <td><c:out value="${row.title}" /></td>
            </tr>
            <tr>
              <td><b>City</b></td>
              <td><c:out value="${row.city}" /></td>
            </tr>
            <tr>
              <td><b>County</b></td>
              <td><c:out value="${row.county}" /></td>
            </tr>
            <tr>
              <td><b>Award</b></td>
              <td><fmt:formatNumber value="${row.totamtappr}" type="currency" maxFractionDigits="0" /></td>
            </tr>
            <tr>
              <td><b>Year</b></td>
              <td><c:out value="${row.fiscalyear}"/></td>
            </tr>
            <tr>
              <td><b>Project Manager</b></td>
              <td><c:out value="${row.projectManager.fname}" /> <c:out value="${row.projectManager.lname}" /></td>
            </tr>
            <tr>
              <td><b>Phone</b></td>
              <td><c:out value="${row.projectManager.phone}" /></td>
            </tr>
            <tr>
              <td><b>Email</b></td>
              <td><c:out value="${row.projectManager.email}" /></td>
            </tr>
            <tr>
              <td height="20" />
            </tr>
          </table>
        </td>
      </tr>
    </c:forEach>
  
  </table>
   
  </body>
</html>