<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <link href="css/StateAid.css" rel="stylesheet" media="screen" type="text/css" />
    <title>Blank Form Assign Discretionary Reviewers</title>
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
      <th>Blank Form - Assign Discretionary Reviewers</th>
    </tr>
  </table>
  
  
  <table width="100%" border="1" summary="for layout only">
    <tr>
      <th>Project Number</th><th>Title</th><th>Institution</th><th>Reviewer 1</th><th>Reviewer 2</th>
    </tr>
    
    
    <c:forEach var="row" items="${subGrants}" >
      <tr>
        <td height="40">030<c:out value="${row.fccode}" />-
          <fmt:formatNumber value="${row.fycode}" pattern="##" minIntegerDigits="2" />-
          <fmt:formatNumber value="${row.projseqnum}" pattern="####" minIntegerDigits="4" /></td>
        <td height="40"><c:out value="${row.title}" /></td>
        <td height="40"><c:out value="${row.instName}" /></td>
        <td height="40"></td>
        <td height="40"></td>
      </tr>    
    </c:forEach>
  
  </table>  
  
  </body>
</html>
