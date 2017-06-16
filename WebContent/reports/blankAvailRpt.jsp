<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
    <title>Blank Form Reviewer Availability</title>
  </head>
  <body>
  
  <table width="100%">
    <tr>
      <td>New York State Education Department<br/>
        New York State Library<br/>
        Division of Library Development<br/></td>
    </tr>
    <tr>
      <td height="20" />
    </tr>
    <tr>
      <th>Blank Form - Reviewer Availability</th>
    </tr>
  </table>
  
  
  <table width="100%" border="1" summary="for layout only">
    <tr>
      <th>Name</th><th>Interest</th><th>Num Accepted</th><th>Comment</th><th width="20%">Project</th>
    </tr>
    
    
    <c:forEach var="row" items="${allRev}" >
      <tr>
        <td height="40"><c:out value="${row.fname}" /> <c:out value="${row.lname}" /></td>
        <td height="40"><c:out value="${row.interest}" /></td>
        <td height="40"><c:out value="${row.reviewerAssigns[0].numaccepted}" /></td>
        <td height="40"><c:out value="${row.reviewerAssigns[0].comment}" /></td>
        <td height="40"></td>
      </tr>    
    </c:forEach>
  
  </table>
  
  
  </body>
</html>
